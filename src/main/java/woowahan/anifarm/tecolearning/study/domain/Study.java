package woowahan.anifarm.tecolearning.study.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import woowahan.anifarm.tecolearning.common.BaseEntity;
import woowahan.anifarm.tecolearning.study.domain.exception.CannotFinishStudyException;
import woowahan.anifarm.tecolearning.study.domain.exception.CannotStartStudyException;
import woowahan.anifarm.tecolearning.study.domain.exception.NotPresenterException;
import woowahan.anifarm.tecolearning.study.domain.exception.PresenterException;
import woowahan.anifarm.tecolearning.study.service.exception.CannotParticipateException;
import woowahan.anifarm.tecolearning.studyoutput.domain.StudyOutput;
import woowahan.anifarm.tecolearning.user.domain.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static woowahan.anifarm.tecolearning.study.domain.StudyStatus.*;

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

    @Column(name = "total_number_of_recruitment",
            nullable = false)
    private Integer totalNumberOfRecruitment;

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

    @OneToMany(mappedBy = "study", cascade = CascadeType.REMOVE)
    private List<StudyOutput> studyOutputs;

    @Builder
    public Study(Long id,
                 String subject,
                 User presenter,
                 Integer totalNumberOfRecruitment,
                 LocalDate startDate,
                 LocalDate endDate,
                 String location,
                 String description,
                 StudyStatus status,
                 List<StudyOutput> studyOutputs) {
        this.id = id;
        this.subject = subject;
        this.presenter = presenter;
        this.totalNumberOfRecruitment = totalNumberOfRecruitment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.description = description;
        this.status = status;
        this.studyOutputs = studyOutputs;
    }

    public Study update(Study newStudy) {
        this.location = newStudy.location;
        this.endDate = newStudy.endDate;
        this.startDate = newStudy.startDate;
        this.totalNumberOfRecruitment = newStudy.totalNumberOfRecruitment;
        this.subject = newStudy.subject;
        this.description = newStudy.description;

        return this;
    }

    public boolean isCreatedBy(User user) {
        return presenter.equals(user);
    }

    public void checkNotPresenter(long id) {
        if (!presenter.authenticate(id)) {
            throw new NotPresenterException();
        }
    }

    public void checkPresenter(long id) {
        if (presenter.authenticate(id)) {
            throw new PresenterException("발제자는 스터디 탈퇴를 할 수 없습니다.");
        }
    }

    public boolean isOngoing() {
        return ONGOING.equals(status);
    }

    public Study start(long presenterId) {
        checkNotPresenter(presenterId);
        checkStartable();

        status = ONGOING;
        return this;
    }

    private void checkStartable() {
        if (!RECRUITING.equals(status)) {
            throw new CannotStartStudyException();
        }
    }

    public Study finish(long presenterId) {
        checkNotPresenter(presenterId);
        checkFinishable();

        status = FINISHED;
        return this;
    }

    private void checkFinishable() {
        if (!ONGOING.equals(status)) {
            throw new CannotFinishStudyException();
        }
    }

    public void isAvailable(int count) {
        if (totalNumberOfRecruitment <= count) {
            throw new CannotParticipateException();
        }
    }
}

