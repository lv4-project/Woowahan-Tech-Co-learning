package woowahan.anifarm.tecolearning.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.Getter;
import woowahan.anifarm.tecolearning.auth.service.exception.JWTValidException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
public class WebToken {
    private static final String ISSUER = "TechCoLearning";
    private static final String SECRET = "cbd6639c357448292870c001908fb757ec18c839";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    private final String token;

    private WebToken(String token) {
        this.token = token;
    }

    public static WebToken from(String token) {
        return new WebToken(token);
    }

    public static WebToken create(Map<String, String> withInfo) {
        return new WebToken(createToken(withInfo));
    }

    private static String createToken(Map<String, String> withInfo) {
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

    private static JWTCreator.Builder getJWTBuilder() {
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(new Date());
    }

    public boolean validToken(long validSecond) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
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
        return new WebToken(createToken(getPayloads(keys)));
    }
}
