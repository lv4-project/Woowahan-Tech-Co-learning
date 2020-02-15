package woowahan.anifarm.tecolearning.auth.advice;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowahan.anifarm.tecolearning.auth.WebToken;
import woowahan.anifarm.tecolearning.auth.service.exception.TokenNotFoundException;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static woowahan.anifarm.tecolearning.auth.advice.LoggedInInterceptor.TOKEN;

public class LoggedInUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final WebToken webToken;

    public LoggedInUserArgumentResolver(WebToken webToken) {
        this.webToken = webToken;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoggedInUser.class);
    }

    @Override
    public UserInfoDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String token = getToken(request);

        return UserInfoDto.from(webToken.from(token));
    }

    private String getToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(TOKEN))
                .findFirst()
                .orElseThrow(TokenNotFoundException::new).getValue();
    }
}
