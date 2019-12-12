package woowahan.anifarm.tecolearning.user.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.RestDocumentationContextProvider;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

public class UserControllerTest extends AbstractWebTestClient {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NICK_NAME = "nickName";
    private static final String INTRODUCTION = "introduction";
    private static final String API_USERS = "/api/users";

    private UserInfoDto testUser;

    @Override
    @BeforeEach
    protected void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp(restDocumentation);
        testUser = getRequest(API_USERS + "/" + 1, UserInfoDto.class);
    }

    @Override
    @AfterEach
    protected void tearDown() {
        super.tearDown();
    }

    @Test
    @DisplayName("적절한 입력 시 회원가입 성공")
    void createUser() {
        Map<String, String> params = new HashMap<>();
        params.put(EMAIL, "test@email.com");
        params.put(PASSWORD, "testtest");
        params.put(NICK_NAME, "test");

        post(API_USERS + "/signup", params)
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("users/post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("존재하는 유저의 id 에 대해 유저의 정보를 조회")
    void readUser() {
        get(API_USERS + "/" + testUser.getId())
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("users/get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("로그인 유저의 id 에 대해 유저의 정보를 갱신")
    void update() {
        Map<String, String> params = new HashMap<>();
        params.put(NICK_NAME, "moo");
        params.put(INTRODUCTION, "hi");

        assertThat(putJsonRequest(API_USERS, params).getStatus().is2xxSuccessful()).isTrue();
        assertThat(getRequest(API_USERS + "/" + testUser.getId(), UserInfoDto.class).getNickName()).isEqualTo("moo");
    }

    @Test
    @DisplayName("유저 삭제 요청시 로그인 된 유저를 비활성화")
    void delete() {
        assertThat(deleteRequest(API_USERS).getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    @DisplayName("적절한 이메일과 패스워드 입력 시 로그인 성공")
    void login() {
        Map<String, String> params = new HashMap<>();
        params.put(EMAIL, testUser.getEmail());
        params.put(PASSWORD, "mastermaster");
        assertThat(postJsonRequest(API_USERS + "/login", params)
                .getStatus()
                .is2xxSuccessful()).isTrue();
    }
}
