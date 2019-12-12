package woowahan.anifarm.tecolearning.auth.github;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OAuthEmailDto {
    private String email;
    private boolean primary;
    private boolean verified;
    private String visibility;
}
