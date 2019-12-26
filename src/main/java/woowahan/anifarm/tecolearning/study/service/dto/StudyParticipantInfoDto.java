package woowahan.anifarm.tecolearning.study.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StudyParticipantInfoDto {
    private Long id;
    private String nickname;
    private String email;

    @Builder
    public StudyParticipantInfoDto(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

    public static StudyParticipantInfoDto from(long id, String nickName, String email) {
        return StudyParticipantInfoDto.builder()
                .id(id)
                .nickname(nickName)
                .email(email)
                .build();
    }
}
