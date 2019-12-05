package woowahan.anifarm.tecolearning.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.user.domain.User;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest extends AbstractWebTestClient {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NICK_NAME = "nickName";
    private static final String STATUS = "status";
    private static final String API_USERS = "/api/users";

    private static final User TEST_USER = User.builder()
            .id(1L)
            .email("learner_duck@woowahan.com")
            .password("master")
            .nickName("learner duck")
            .build();

    @Test
    @DisplayName("적절한 입력 시 회원가입 성공")
    void createUser() {
        Map<String, String> params = new HashMap<>();
        params.put(EMAIL, "test@email.com");
        params.put(PASSWORD, "test");
        params.put(NICK_NAME, "test");

        assertThat(postJsonRequest(API_USERS + "/signup", params).getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    @DisplayName("존재하는 유저의 id 에 대해 유저의 정보를 조회")
    void readUser() {
        UserInfoDto userInfo = getRequest(API_USERS + "/" + TEST_USER.getId(), UserInfoDto.class);
        assertThat(Objects.requireNonNull(userInfo).getId()).isEqualTo(TEST_USER.getId());
        assertThat(Objects.requireNonNull(userInfo).getEmail()).isEqualTo(TEST_USER.getEmail());
        assertThat(Objects.requireNonNull(userInfo).getNickName()).isEqualTo(TEST_USER.getNickName());
    }

    @Test
    @DisplayName("로그인 유저의 id 에 대해 유저의 정보를 갱신")
    void update() {
        Map<String, String> params = new HashMap<>();
        params.put(NICK_NAME, "moo");

        assertThat(putJsonRequest(API_USERS, params).getStatus().is2xxSuccessful()).isTrue();
        assertThat(getRequest(API_USERS + "/" + TEST_USER.getId(), UserInfoDto.class).getNickName()).isEqualTo("moo");
    }

    @Test
    @DisplayName("유저 삭제 요청시 로그인 된 유저를 비활성화")
    void delete() {
        assertThat(deleteRequest(API_USERS).getStatus().is2xxSuccessful()).isTrue();
    }

}
