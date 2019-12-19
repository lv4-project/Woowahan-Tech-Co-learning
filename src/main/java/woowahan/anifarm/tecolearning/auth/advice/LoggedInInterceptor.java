package woowahan.anifarm.tecolearning.auth.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import woowahan.anifarm.tecolearning.auth.service.OauthService;
import woowahan.anifarm.tecolearning.auth.service.exception.JWTValidException;
import woowahan.anifarm.tecolearning.auth.service.exception.TokenNotFoundException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Slf4j
@Component
public class LoggedInInterceptor extends HandlerInterceptorAdapter {
    public static final String TOKEN = "token";

    private OauthService oauthService;

    public LoggedInInterceptor(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = getToken(request);
            return oauthService.oauth(token);
        } catch (JWTValidException | TokenNotFoundException e) {
            writeInResponse(response, e.getMessage());
            return false;
        }
    }

    private void writeInResponse(HttpServletResponse response, String message) throws IOException {
        log.error("{}", message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.write(message);
        out.flush();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String token = getToken(request);

        response.addCookie(createJWTCookie(oauthService.renewLogin(token).getToken()));
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

    public static Cookie createJWTCookie(String token) {
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}
