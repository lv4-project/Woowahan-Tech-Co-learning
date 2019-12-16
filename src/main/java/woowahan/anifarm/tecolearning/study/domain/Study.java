package woowahan.anifarm.tecolearning.study.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import woowahan.anifarm.tecolearning.common.BaseEntity;
import woowahan.anifarm.tecolearning.user.domain.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@ToString
public class Study extends BaseEntity {
    private static final int SUBJECT_LENGTH_UTF_8 = 200;
    private static final int PRESENTER_LENGTH_UTF_8 = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id", nullable = false)
    private Long id;

    @Column(name = "subject",
            nullable = false,
            length = SUBJECT_LENGTH_UTF_8)
    private String subject;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "presenter_id", foreignKey = @ForeignKey(name = "fk_study_user"))
    private User presenter;

    @Column(name = "total_number_of_participants",
            nullable = false)
    private Integer totalNumberOfParticipants;

    // TODO: 2019-12-10 participants user -> many to many

    @Column(name = "start_date",
            nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date",
            nullable = false)
    private LocalDate endDate;

    // TODO: 2019-12-10 지역 정보
    @Column(name = "location")
    private String location;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StudyStatus status;

    @Builder
    public Study(Long id,
                 String subject,
                 User presenter,
                 Integer totalNumberOfParticipants,
                 LocalDate startDate,
                 LocalDate endDate,
                 String location,
                 String description,
                 StudyStatus status) {
        this.id = id;
        this.subject = subject;
        this.presenter = presenter;
        this.totalNumberOfParticipants = totalNumberOfParticipants;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.description = description;
        this.status = status;
    }

    public Study update(Study newStudy) {
        this.location = newStudy.location;
        this.endDate = newStudy.endDate;
        this.startDate = newStudy.startDate;
        this.totalNumberOfParticipants = newStudy.totalNumberOfParticipants;
        this.subject = newStudy.subject;
        this.description = newStudy.description;

        return this;
    }
}
