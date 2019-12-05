package woowahan.anifarm.tecolearning.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowahan.anifarm.tecolearning.user.domain.User;

@NoArgsConstructor
@Getter
public class UserUpdateDto {
    private String nickName;

    @Builder
    public UserUpdateDto(String nickName) {
        this.nickName = nickName;
    }

    public User toEntity() {
        return User.builder()
                .nickName(nickName)
                .build();
    }
}
