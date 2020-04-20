package woowahan.anifarm.tecolearning.study.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

@Getter
@NoArgsConstructor
@ToString
public class StudySummaryDto {
    public static final int MAX_SUMMARY_DESCRIPTION_LENGTH = 40;
    public static final String SUMMARY_DESCRIPTION_SUFFIX = "...";
    private static final String EMPTY_DESCRIPTION = "";

    private Long id;
    private String presenterName;
    private String subject;
    private Integer totalNumberOfRecruitment;
    private Integer numberOfParticipants;
    private String location;
    private String summary;
    private String studyStatus;

    @Builder
    public StudySummaryDto(Long id,
                           String presenterName,
                           String subject,
                           Integer totalNumberOfRecruitment,
                           Integer numberOfParticipants,
                           String location,
                           String summary,
                           String studyStatus) {
        this.id = id;
        this.presenterName = presenterName;
        this.subject = subject;
        this.totalNumberOfRecruitment = totalNumberOfRecruitment;
        this.numberOfParticipants = numberOfParticipants;
        this.location = location;
        this.summary = summary;
        this.studyStatus = studyStatus;
    }

    public static StudySummaryDto from(Study study, int numberOfParticipants) {
        UserInfoDto presenter = UserInfoDto.from(study.getPresenter());

        return StudySummaryDto.builder()
                .id(study.getId())
                .presenterName(presenter.getNickName())
                .subject(study.getSubject())
                .totalNumberOfRecruitment(study.getTotalNumberOfRecruitment())
                .numberOfParticipants(numberOfParticipants)
                .location(study.getLocation())
                .summary(summarize(study.getDescription())) // TODO: 2019-12-15 도메인 객체에 summary 필드 만들 것.
                .studyStatus(study.getStatus().getName())
                .build();
    }

    private static String summarize(String description) {
        if (StringUtils.isEmpty(description)) {
            return EMPTY_DESCRIPTION;
        }

        if (description.length() <= MAX_SUMMARY_DESCRIPTION_LENGTH) {
            return description;
        }

        return description.substring(0, MAX_SUMMARY_DESCRIPTION_LENGTH) + SUMMARY_DESCRIPTION_SUFFIX;
    }
}
