package woowahan.anifarm.tecolearning.auth.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import woowahan.anifarm.tecolearning.auth.WebToken;
import woowahan.anifarm.tecolearning.auth.github.GithubClient;
import woowahan.anifarm.tecolearning.auth.service.exception.OauthException;
import woowahan.anifarm.tecolearning.auth.service.exception.UserAccountException;
import woowahan.anifarm.tecolearning.user.domain.AccountStatus;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;
import woowahan.anifarm.tecolearning.user.dto.UserLoginDto;
import woowahan.anifarm.tecolearning.user.exception.UserNotFoundException;
import woowahan.anifarm.tecolearning.user.service.UserService;


@Log4j2
@Service
public class OauthService {
    public static final long LOGIN_VALID_SECOND = 30 * 60;

    private GithubClient githubClient;
    private UserService userService;

    public OauthService(GithubClient githubClient, UserService userService) {
        this.githubClient = githubClient;
        this.userService = userService;
    }

    public boolean oauth(String token) {
        return WebToken.from(token).validToken(LOGIN_VALID_SECOND);
    }

    public WebToken login(UserLoginDto userLoginDto) {
        try {
            User user = userService.authenticate(userLoginDto);
            UserInfoDto userInfoDto = checkStatus(user);

            return WebToken.create(userInfoDto.toMap());
        } catch (UserNotFoundException e) {
            throw new OauthException();
        }
    }

    private UserInfoDto checkStatus(User user) {
        if (user.getStatus() == AccountStatus.ACTIVE) {
            return UserInfoDto.from(user);
        }
        throw new UserAccountException();
    }

    public WebToken gitLogin(String code) {
        String token = githubClient.getToken(code);
        String email = githubClient.getUserEmail(token);
        try {
            User user = userService.findInfoDtoByEmail(email);
            UserInfoDto userInfoDto = checkStatus(user);

            return WebToken.create(userInfoDto.toMap());
        } catch (UserNotFoundException e) {
            User user = userService.saveGitTempUser(email);
            return WebToken.create(checkStatus(user).toMap());
        }
    }

    public WebToken renewLogin(String token) {
        return WebToken.from(token).renewToken(token, UserInfoDto.ID_KEY, UserInfoDto.EMAIL_KEY, UserInfoDto.NICK_NAME_KEY);
    }

}
