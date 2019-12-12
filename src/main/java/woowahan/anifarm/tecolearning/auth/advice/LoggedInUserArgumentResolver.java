package woowahan.anifarm.tecolearning.auth.advice;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowahan.anifarm.tecolearning.auth.WebToken;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static woowahan.anifarm.tecolearning.auth.advice.LoggedInInterceptor.TOKEN;

public class LoggedInUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoggedInUser.class);
    }

    @Override
    public UserInfoDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String token = null;

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(TOKEN)) {
                token = cookie.getValue();
            }
        }

        return UserInfoDto.from(WebToken.from(token));
    }
}
