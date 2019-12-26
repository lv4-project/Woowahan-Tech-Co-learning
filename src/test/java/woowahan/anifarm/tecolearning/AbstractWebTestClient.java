package woowahan.anifarm.tecolearning;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import woowahan.anifarm.tecolearning.auth.advice.LoggedInInterceptor;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@AutoConfigureWebTestClient
@TestExecutionListeners({
        DbUnitTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
})
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
@DatabaseSetup(value = {
        "/woowahan/anifarm/tecolearning/user.xml",
        "/woowahan/anifarm/tecolearning/study.xml",
        "/woowahan/anifarm/tecolearning/study_output.xml",
        "/woowahan/anifarm/tecolearning/study_location.xml",
        "/woowahan/anifarm/tecolearning/study_participant.xml"
}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {
        "/woowahan/anifarm/tecolearning/user.xml",
        "/woowahan/anifarm/tecolearning/study.xml",
        "/woowahan/anifarm/tecolearning/study_output.xml",
        "/woowahan/anifarm/tecolearning/study_location.xml",
        "/woowahan/anifarm/tecolearning/study_participant.xml"
}, type = DatabaseOperation.DELETE_ALL)
@Import(TestConfig.class)
@ExtendWith(RestDocumentationExtension.class)
@TestPropertySource("classpath:application_test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractWebTestClient {
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    private static final String LOGIN_EMAIL = "learner_duck@woowa.com";
    private static final String LOGIN_PASSWORD = "mastermaster";

    private static final String CONTENT_TYPE = "Content-Type";

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private String token;

    @BeforeEach
    protected void setUp(RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .defaultHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                .filter(documentationConfiguration(restDocumentation))
                .build();

        Map<String, String> login = new HashMap<>();
        login.put(EMAIL_KEY, LOGIN_EMAIL);
        login.put(PASSWORD_KEY, LOGIN_PASSWORD);

        String cookie = postJsonRequest("/api/oauth/login", login)
                .getResponseHeaders()
                .getFirst("Set-Cookie");

        token = cookie.split(";")[0].split("=")[1];

    }

    @AfterEach
    protected void tearDown() {
    }

    protected <T> T getRequest(String uri, Class<T> bodyType) {
        return get(uri)
                .expectBody(bodyType)
                .returnResult()
                .getResponseBody();
    }

    protected EntityExchangeResult<byte[]> getRequest(String uri) {
        return get(uri)
                .expectBody()
                .returnResult();
    }

    protected WebTestClient.ResponseSpec get(String uri) {
        return webTestClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .cookie(LoggedInInterceptor.TOKEN, token)
                .exchange();
    }

    protected WebTestClient.ResponseSpec getWithoutLogin(String uri) {
        return webTestClient.get()
                .uri(uri)
                .exchange();
    }

    protected EntityExchangeResult<byte[]> postJsonRequest(String uri, Map<String, String> params) {
        return post(uri, params)
                .expectBody()
                .returnResult();
    }

    protected <T, R> EntityExchangeResult<R> postJsonRequest(String uri, T dto, Class<R> returnType) {
        return post(uri, dto)
                .expectStatus()
                .isOk()
                .expectBody(returnType).returnResult();
    }

    protected <T> T postJsonRequest(String uri, Map<String, String> params, Class<T> bodyType) {
        return post(uri, params)
                .expectBody(bodyType)
                .returnResult()
                .getResponseBody();
    }

    protected WebTestClient.ResponseSpec post(String uri, Map<String, String> params) {
        return webTestClient.post()
                .uri(uri)
                .cookie(LoggedInInterceptor.TOKEN, token)
                .body(Mono.just(params), Map.class)
                .exchange();
    }

    protected <T> WebTestClient.ResponseSpec post(String uri, T dto) {
        return webTestClient.post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .cookie(LoggedInInterceptor.TOKEN, token)
                .body(Mono.just(dto), dto.getClass())
                .exchange();
    }

    protected <T> WebTestClient.ResponseSpec postWithoutLogin(String uri, T dto) {
        return webTestClient.post()
                .uri(uri)
                .body(Mono.just(dto), dto.getClass())
                .exchange();
    }


    protected EntityExchangeResult<byte[]> putJsonRequest(String uri, Map<String, String> params) {
        return put(uri, params)
                .expectBody()
                .returnResult();
    }

    protected <T> T putJsonRequest(String uri, Map<String, String> params, Class<T> bodyType) {
        return put(uri, params)
                .expectBody(bodyType)
                .returnResult()
                .getResponseBody();
    }

    private WebTestClient.ResponseSpec put(String uri, Map<String, String> params) {
        return webTestClient.put()
                .uri(uri)
                .cookie(LoggedInInterceptor.TOKEN, token)
                .body(Mono.just(params), Map.class)
                .exchange();
    }

    protected <T> WebTestClient.ResponseSpec put(String uri, T dto) {
        return webTestClient.put()
                .uri(uri)
                .cookie(LoggedInInterceptor.TOKEN, token)
                .body(Mono.just(dto), dto.getClass())
                .exchange();
    }

    protected <T> WebTestClient.ResponseSpec putWithoutLogin(String uri, T dto) {
        return webTestClient.put()
                .uri(uri)
                .body(Mono.just(dto), dto.getClass())
                .exchange();
    }

    protected <T> WebTestClient.ResponseSpec patch(String uri, T dto) {
        return webTestClient.patch()
                .uri(uri)
                .cookie(LoggedInInterceptor.TOKEN, token)
                .body(Mono.just(dto), dto.getClass())
                .exchange();
    }

    protected <T> WebTestClient.ResponseSpec patchWithoutLogin(String uri, T dto) {
        return webTestClient.patch()
                .uri(uri)
                .body(Mono.just(dto), dto.getClass())
                .exchange();
    }

    protected WebTestClient.ResponseSpec delete(String uri) {
        return webTestClient.delete()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .cookie(LoggedInInterceptor.TOKEN, token)
                .exchange();
    }

    protected EntityExchangeResult<byte[]> deleteRequest(String uri) {
        return webTestClient.delete()
                .uri(uri)
                .cookie(LoggedInInterceptor.TOKEN, token)
                .exchange()
                .expectBody()
                .returnResult();
    }

    protected void removeToken() {
        token = null;
    }
}

