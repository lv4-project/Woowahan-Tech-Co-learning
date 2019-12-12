package woowahan.anifarm.tecolearning.auth.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import woowahan.anifarm.tecolearning.auth.service.OauthService;
import woowahan.anifarm.tecolearning.auth.service.exception.JWTValidException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        String token = getToken(request);

        try {
            return oauthService.oauth(token);
        } catch (JWTValidException e) {
            writeInResponse(response, e.getMessage());

            return false;
        }
    }

    private void writeInResponse(HttpServletResponse response, String message) throws IOException {
        log.error("{}", message);

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
        String token = null;

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(TOKEN)) {
                token = cookie.getValue();
            }
        }

        return token;
    }

    public static Cookie createJWTCookie(String token) {
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}
