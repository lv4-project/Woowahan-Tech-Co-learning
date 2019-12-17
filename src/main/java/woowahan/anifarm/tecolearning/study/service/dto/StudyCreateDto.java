package woowahan.anifarm.tecolearning.study.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyStatus;
import woowahan.anifarm.tecolearning.user.domain.User;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class StudyCreateDto {
    private String subject;
    private int totalNumberOfRecruitment;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String description;

    @Builder
    public StudyCreateDto(String subject, int totalNumberOfRecruitment, LocalDate startDate, LocalDate endDate, String location, String description) {
        this.subject = subject;
        this.totalNumberOfRecruitment = totalNumberOfRecruitment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.description = description;
    }

    public Study toEntity(User user) {
        return Study.builder()
                .subject(subject)
                .presenter(user)
                .totalNumberOfRecruitment(totalNumberOfRecruitment)
                .startDate(startDate)
                .location(location)
                .endDate(endDate)
                .description(description)
                .status(StudyStatus.RECRUITING)
                .build();
    }
}
