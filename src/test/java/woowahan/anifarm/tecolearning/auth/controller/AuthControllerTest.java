package woowahan.anifarm.tecolearning.auth.controller;

import com.auth0.jwt.JWT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.auth.service.exception.OauthException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class AuthControllerTest extends AbstractWebTestClient {
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    private static final String LOGIN_EMAIL = "learner_duck@woowa.com";
    private static final String LOGIN_PASSWORD = "mastermaster";

    @Test
    @DisplayName("로그인 성공시 헤더에 토큰 정보가 있음")
    void login_성공() {
        Map<String, String> login = new HashMap<>();
        login.put(EMAIL_KEY, LOGIN_EMAIL);
        login.put(PASSWORD_KEY, LOGIN_PASSWORD);

        String cookie = postJsonRequest("/api/oauth/login", login)
                .getResponseHeaders()
                .getFirst("Set-Cookie");

        String token = cookie.split(";")[0].split("=")[1];

        assertThat(JWT.decode(token).getClaim(EMAIL_KEY)
                .asString())
                .isEqualTo(LOGIN_EMAIL);
    }

    @Test
    @DisplayName("로그인 실패")
    void login_실패() throws UnsupportedEncodingException {
        Map<String, String> login = new HashMap<>();
        login.put(EMAIL_KEY, LOGIN_EMAIL);
        login.put(PASSWORD_KEY, "master");

        assertThat(new String(postJsonRequest("/api/oauth/login", login).getResponseBody(), "utf-8"))
                .isEqualTo(new OauthException().getMessage());
    }
}