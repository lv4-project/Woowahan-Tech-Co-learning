package woowahan.anifarm.tecolearning.studyoutput.service.dto;

import lombok.*;
import woowahan.anifarm.tecolearning.study.domain.Study;
import woowahan.anifarm.tecolearning.studyoutput.domain.StudyOutput;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class StudyOutputDto {
    private long id;
    private String title;
    private String contents;

    @Builder
    public StudyOutputDto(long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public static StudyOutputDto from(StudyOutput save) {
        return new StudyOutputDto(save.getId(), save.getTitle(), save.getContents());
    }

    public StudyOutput toEntity() {
        return StudyOutput.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .build();
    }

    public StudyOutput toEntity(Study study) {
        return StudyOutput.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .study(study)
                .build();
    }
}
