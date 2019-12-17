package woowahan.anifarm.tecolearning.study.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyStatus;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class StudyInfoDto {
    private long id;
    private UserInfoDto presenter;
    private String subject;
    private int totalNumberOfRecruitment;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String description;
    private StudyStatus status;

    @Builder
    public StudyInfoDto(long id,
                        UserInfoDto presenter,
                        String subject,
                        int totalNumberOfRecruitment,
                        LocalDate startDate,
                        LocalDate endDate,
                        String location,
                        String description,
                        StudyStatus status) {
        this.id = id;
        this.presenter = presenter;
        this.subject = subject;
        this.totalNumberOfRecruitment = totalNumberOfRecruitment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.description = description;
        this.status = status;
    }

    public static StudyInfoDto from(Study study) {
        UserInfoDto presenter = UserInfoDto.from(study.getPresenter());

        return StudyInfoDto.builder()
                .id(study.getId())
                .presenter(presenter)
                .subject(study.getSubject())
                .totalNumberOfRecruitment(study.getTotalNumberOfRecruitment())
                .startDate(study.getStartDate())
                .endDate(study.getEndDate())
                .location(study.getLocation())
                .description(study.getDescription())
                .status(study.getStatus())
                .build();
    }
}
