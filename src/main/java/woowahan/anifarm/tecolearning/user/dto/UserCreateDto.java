package woowahan.anifarm.tecolearning.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.user.domain.User;

@NoArgsConstructor
@Getter
@ToString
public class UserCreateDto {
    private String email;
    private String password;
    private String nickName;

    @Builder
    public UserCreateDto(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .build();
    }
}
