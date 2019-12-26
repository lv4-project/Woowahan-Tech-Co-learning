package woowahan.anifarm.tecolearning.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.user.domain.User;

@NoArgsConstructor
@Getter
@ToString
public class UserLoginDto {

    private String email;
    private String password;

    @Builder
    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}
