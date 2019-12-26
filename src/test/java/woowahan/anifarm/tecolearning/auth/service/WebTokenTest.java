package woowahan.anifarm.tecolearning.auth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.auth.WebToken;
import woowahan.anifarm.tecolearning.auth.service.exception.JWTValidException;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static woowahan.anifarm.tecolearning.user.dto.UserInfoDto.EMAIL_KEY;
import static woowahan.anifarm.tecolearning.user.dto.UserInfoDto.ID_KEY;

class WebTokenTest {
    private static final long LOGIN_VALID_SECOND = 2;

    private static final String ID = "moomin";
    private static final String EMAIL = "moomin@Woowa.com";

    @Test
    @DisplayName("바르게 토큰이 생성되는지 확인")
    void createToken() {
        Map<String, String> innerTokenInfo = new HashMap<>();
        innerTokenInfo.put(ID_KEY, ID);
        innerTokenInfo.put(EMAIL_KEY, EMAIL);

        WebToken token = WebToken.create(innerTokenInfo);

        for (String key : innerTokenInfo.keySet()) {
            assertThat(token.getPayload(key)).isEqualTo(innerTokenInfo.get(key));
        }
    }

    @Test
    @DisplayName("시간이 지나서 유효하지 않은 토큰 확인")
    void validToken() throws InterruptedException {
        Map<String, String> innerTokenInfo = new HashMap<>();
        innerTokenInfo.put(ID_KEY, ID);
        innerTokenInfo.put(EMAIL_KEY, EMAIL);

        WebToken token = WebToken.create(innerTokenInfo);

        Thread.sleep(4000);
        assertThrows(JWTValidException.class, () -> token.validToken(LOGIN_VALID_SECOND));
    }

    @Test
    @DisplayName("토큰의 생성시간을 갱신")
    void renewToken() throws InterruptedException {
        Map<String, String> innerTokenInfo = new HashMap<>();
        innerTokenInfo.put(ID_KEY, ID);
        innerTokenInfo.put(EMAIL_KEY, EMAIL);

        WebToken token = WebToken.create(innerTokenInfo);
        Thread.sleep(4000);

        token = token.renewToken(ID_KEY, EMAIL_KEY);
        assertThat(token.validToken(LOGIN_VALID_SECOND)).isTrue();
    }
}