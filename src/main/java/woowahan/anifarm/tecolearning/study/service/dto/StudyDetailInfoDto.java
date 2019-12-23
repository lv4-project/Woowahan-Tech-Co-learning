package woowahan.anifarm.tecolearning.study.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.study.domain.StudyStatus;
import woowahan.anifarm.tecolearning.studyoutput.service.dto.StudyOutputDto;
import woowahan.anifarm.tecolearning.user.dto.UserInfoDto;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
@ToString
public class StudyDetailInfoDto {
    private long id;
    private UserInfoDto presenter;
    private String subject;
    private int totalNumberOfRecruitment;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String description;
    private StudyStatus status;
    private String studyParticipantStatus;
    private List<StudyOutputDto> studyOutput;

    @Builder
    public StudyDetailInfoDto(long id,
                              UserInfoDto presenter,
                              String subject,
                              int totalNumberOfRecruitment,
                              LocalDate startDate,
                              LocalDate endDate,
                              String location,
                              String description,
                              StudyStatus status,
                              String studyParticipantStatus, List<StudyOutputDto> studyOutput) {
        this.id = id;
        this.presenter = presenter;
        this.subject = subject;
        this.totalNumberOfRecruitment = totalNumberOfRecruitment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.description = description;
        this.status = status;
        this.studyParticipantStatus = studyParticipantStatus;
        this.studyOutput = studyOutput;
    }

    public static StudyDetailInfoDto of(Study study, String participatingStatus) {
        UserInfoDto presenter = UserInfoDto.from(study.getPresenter());
        List<StudyOutputDto> outputs = study.getStudyOutputs().stream()
                .map(StudyOutputDto::from)
                .collect(toList());

        return StudyDetailInfoDto.builder()
                .id(study.getId())
                .presenter(presenter)
                .subject(study.getSubject())
                .totalNumberOfRecruitment(study.getTotalNumberOfRecruitment())
                .startDate(study.getStartDate())
                .endDate(study.getEndDate())
                .location(study.getLocation())
                .description(study.getDescription())
                .status(study.getStatus())
                .studyParticipantStatus(participatingStatus)
                .studyOutput(outputs)
                .build();
    }
}