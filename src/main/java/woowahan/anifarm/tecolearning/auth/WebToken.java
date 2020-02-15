package woowahan.anifarm.tecolearning.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import woowahan.anifarm.tecolearning.auth.service.exception.JWTValidException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class WebToken {

    private String issuer;
    private String secret;
    private Algorithm algorithm;

    private String token;

    public WebToken(@Value("${auth.jwt.issuer}") String issuer,
                    @Value("${auth.jwt.secret}") String secret) {
        this.issuer = issuer;
        this.secret = secret;
        this.algorithm = Algorithm.HMAC256(this.secret);
    }

    public WebToken from(String token) {
        this.token = token;
        return this;
    }

    public WebToken create(Map<String, String> withInfo) {
        this.token = createToken(withInfo);
        return this;
    }

    private String createToken(Map<String, String> withInfo) {
        try {
            JWTCreator.Builder builder = getJWTBuilder();
            for (String key : withInfo.keySet()) {
                builder.withClaim(key, withInfo.get(key));
            }

            return builder.sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException();
        }
    }

    private JWTCreator.Builder getJWTBuilder() {
        return JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(new Date());
    }

    public boolean validToken(long validSecond) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .acceptExpiresAt(validSecond)
                    .build();

            verifier.verify(this.token);
            return true;
        } catch (Exception e) {
            throw new JWTValidException(e.getCause());
        }
    }

    public Map<String, String> getPayloads(String... keys) {
        Map<String, String> result = new HashMap<>();
        for (String key : keys) {
            result.put(key, getPayload(key));
        }
        return result;
    }

    public String getPayload(String key) {
        return JWT.decode(this.token)
                .getClaim(key)
                .asString();
    }

    public WebToken renewToken(String... keys) {
        this.token = createToken(getPayloads(keys));
        return this;
    }
}
