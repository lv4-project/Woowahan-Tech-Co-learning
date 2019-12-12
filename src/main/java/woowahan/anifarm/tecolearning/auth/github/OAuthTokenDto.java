package woowahan.anifarm.tecolearning.auth.github;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class OAuthTokenDto {
    private String access_token;
    private String scope;
}
