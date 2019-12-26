package woowahan.anifarm.tecolearning.auth.github;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@Getter
@Setter
public class GithubClient {
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String CODE = "code";

    @Value("${oauth.client.github.client-id}")
    private String clientId;
    @Value("${oauth.client.github.client-secret}")
    private String clientSecret;

    @Value("${oauth.client.github.token-url}")
    private String tokenUrl;
    @Value("${oauth.client.github.user-info-url}")
    private String userInfoUrl;
    @Value("${oauth.client.github.user-email-url}")
    private String userEmailUrl;

    public String getToken(String code) {
        ClientResponse response = WebClient.create()
                .post().uri(tokenUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(new OAuthDto(code, clientId, clientSecret)), OAuthDto.class)
                .exchange()
                .block();

        if (response.statusCode() != HttpStatus.OK) {
            throw new RuntimeException("There are unstable that internet connection status or github server.. " +
                    "Plz try again later.");
        }
        return response.bodyToMono(OAuthTokenDto.class).block().getAccess_token();
    }

    public String getUserEmail(String accessToken) {
        return connectWithAuthorization(accessToken, userEmailUrl)
                .bodyToFlux(OAuthEmailDto.class)
                .blockFirst().getEmail();
    }

    private WebClient.ResponseSpec connectWithAuthorization(String accessToken, String url) {
        return WebClient.create()
                .get().uri(url)
                .header("Authorization", String.format("token %s", accessToken))
                .retrieve();
    }
}
