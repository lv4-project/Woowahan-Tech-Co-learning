package woowahan.anifarm.tecolearning.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowahan.anifarm.tecolearning.user.domain.User;

@NoArgsConstructor
@Getter
public class UserInfoDto {
    private Long id;
    private String email;
    private String nickName;

    @Builder
    public UserInfoDto(Long id, String email, String nickName) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
    }

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();
    }
}
