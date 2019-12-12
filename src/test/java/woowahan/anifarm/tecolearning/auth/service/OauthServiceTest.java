package woowahan.anifarm.tecolearning.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;
import woowahan.anifarm.tecolearning.user.dto.UserLoginDto;

import static org.assertj.core.api.Assertions.assertThat;

class OauthServiceTest extends AbstractWebTestClient {
    private static final String LOGIN_EMAIL = "learner_duck@woowa.com";
    private static final String LOGIN_PASSWORD = "mastermaster";

    @Autowired
    private OauthService oauthService;

    @Test
    @DisplayName("존재하는 유저로 로그인시 토큰을 반환한다.")
    void login() {
        DecodedJWT jwt = JWT.decode(oauthService.login(new UserLoginDto(LOGIN_EMAIL, LOGIN_PASSWORD)).getToken());
        assertThat(jwt.getClaim(UserInfoDto.EMAIL_KEY).asString()).isEqualTo(LOGIN_EMAIL);
    }
}