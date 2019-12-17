package woowahan.anifarm.tecolearning.study.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

@Getter
@NoArgsConstructor
@ToString
public class StudySummaryDto {
    private long id;
    private String presenterName;
    private String subject;
    private int totalNumberOfRecruitment;
    private int numberOfParticipants;
    private String location;
    private String summary;

    @Builder
    public StudySummaryDto(long id,
                           String presenterName,
                           String subject,
                           int totalNumberOfRecruitment,
                           int numberOfParticipants,
                           String location,
                           String summary) {
        this.id = id;
        this.presenterName = presenterName;
        this.subject = subject;
        this.totalNumberOfRecruitment = totalNumberOfRecruitment;
        this.numberOfParticipants = numberOfParticipants;
        this.location = location;
        this.summary = summary;
    }

    public static StudySummaryDto from(Study study) {
        UserInfoDto presenter = UserInfoDto.from(study.getPresenter());

        return StudySummaryDto.builder()
                .id(study.getId())
                .presenterName(presenter.getNickName())
                .subject(study.getSubject())
                .totalNumberOfRecruitment(study.getTotalNumberOfRecruitment())
                .numberOfParticipants(1) // TODO: 2019-12-15  Study 도메인 객체에 participants 필드 만들 것.
                .location(study.getLocation())
                .summary(study.getDescription()) // TODO: 2019-12-15 도메인 객체에 summary 필드 만들 것.
                .build();
    }
}
