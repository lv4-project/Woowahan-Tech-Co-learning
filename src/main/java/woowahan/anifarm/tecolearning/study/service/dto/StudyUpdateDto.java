package woowahan.anifarm.tecolearning.study.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.study.domain.Study;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class StudyUpdateDto {
    private String subject;
    private int totalNumberOfParticipants;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String description;

    @Builder
    public StudyUpdateDto(String subject,
                          int totalNumberOfParticipants,
                          LocalDate startDate,
                          LocalDate endDate,
                          String location,
                          String description) {
        this.subject = subject;
        this.totalNumberOfParticipants = totalNumberOfParticipants;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.description = description;
    }

    public Study toEntity() {
        return Study.builder()
                .location(location)
                .endDate(endDate)
                .startDate(startDate)
                .totalNumberOfParticipants(totalNumberOfParticipants)
                .subject(subject)
                .description(description)
                .build();
    }
}
