package woowahan.anifarm.tecolearning.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.user.domain.User;

@NoArgsConstructor
@Getter
@ToString
public class UserInfoDto {
    private Long id;
    private String email;
    private String nickName;
    private String introduction;

    @Builder
    public UserInfoDto(Long id, String email, String nickName, String introduction) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.introduction = introduction;
    }

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .introduction(user.getIntroduction())
                .build();
    }
}
