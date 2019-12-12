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
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;


@AutoConfigureWebTestClient
@Import(TestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({
        DbUnitTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
})
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
@DatabaseSetup(value = {"/woowahan/anifarm/tecolearning/user.xml"}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {"/woowahan/anifarm/tecolearning/user.xml"}, type = DatabaseOperation.DELETE_ALL)
@ExtendWith(RestDocumentationExtension.class)
public class AbstractWebTestClient {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    protected void setUp(RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .filter(documentationConfiguration(restDocumentation))
                .build();
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
                .exchange();
    }

    protected EntityExchangeResult<byte[]> postJsonRequest(String uri, Map<String, String> params) {
        return post(uri, params)
                .expectBody()
                .returnResult();
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
                .body(Mono.just(params), Map.class)
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
                .body(Mono.just(params), Map.class)
                .exchange();
    }

    protected EntityExchangeResult<byte[]> deleteRequest(String uri) {
        return webTestClient.delete()
                .uri(uri)
                .exchange()
                .expectBody()
                .returnResult();
    }
}