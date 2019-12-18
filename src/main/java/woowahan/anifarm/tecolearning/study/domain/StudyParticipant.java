package woowahan.anifarm.tecolearning.study.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import woowahan.anifarm.tecolearning.user.domain.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
public class StudyParticipant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "participant_id", foreignKey = @ForeignKey(name = "fk_participant_study"))
    private User participant;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "study_id", foreignKey = @ForeignKey(name = "fk_study_participant"))
    private Study study;

    @Builder
    public StudyParticipant(Long id, User participant, Study study) {
        this.id = id;
        this.participant = participant;
        this.study = study;
    }

    @Builder
    public StudyParticipant(User participant, Study study) {
        this.participant = participant;
        this.study = study;
    }
}

