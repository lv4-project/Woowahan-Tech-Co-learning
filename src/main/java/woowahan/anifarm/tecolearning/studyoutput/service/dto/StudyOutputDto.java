package woowahan.anifarm.tecolearning.studyoutput.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.studyoutput.domain.StudyOutput;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class StudyOutputDto {
    private String title;
    private String contents;

    public StudyOutputDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public static StudyOutputDto from(StudyOutput save) {
        return new StudyOutputDto(save.getTitle(), save.getContents());
    }

    public StudyOutput toEntity() {
        return StudyOutput.builder()
                .title(title)
                .contents(contents)
                .build();
    }

    public StudyOutput toEntity(Study study) {
        return StudyOutput.builder()
                .title(title)
                .contents(contents)
                .study(study)
                .build();
    }
}
