package woowahan.anifarm.tecolearning.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.user.domain.User;

@NoArgsConstructor
@Getter
@ToString
public class UserUpdateDto {
    private String nickName;
    private String introduction;

    @Builder
    public UserUpdateDto(String nickName, String introduction) {
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public User toEntity() {
        return User.builder()
                .nickName(nickName)
                .introduction(introduction)
                .build();
    }
}
