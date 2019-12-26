package woowahan.anifarm.tecolearning.studyoutput.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowahan.anifarm.tecolearning.common.BaseEntity;
import woowahan.anifarm.tecolearning.study.domain.Study;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class StudyOutput extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "output_id", nullable = false)
    private Long id;

    @Column(name = "title", length = 255)
    private String title;

    @Lob
    @Column(name = "contents")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "study_id", foreignKey = @ForeignKey(name = "fk_output_study"))
    private Study study;

    @Builder
    public StudyOutput(Long id, String title, String contents, Study study) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.study = study;
    }

    public StudyOutput update(StudyOutput newStudyOutput) {
        this.title = newStudyOutput.title;
        this.contents = newStudyOutput.contents;
        return this;
    }

    public void authenticate(long id) {
        study.checkNotPresenter(id);
    }
}
