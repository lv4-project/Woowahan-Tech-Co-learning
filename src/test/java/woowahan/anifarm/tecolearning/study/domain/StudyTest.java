package woowahan.anifarm.tecolearning.study.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.study.domain.exception.CannotFinishStudyException;
import woowahan.anifarm.tecolearning.study.domain.exception.CannotStartStudyException;
import woowahan.anifarm.tecolearning.study.domain.exception.NotPresenterException;
import woowahan.anifarm.tecolearning.user.domain.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static woowahan.anifarm.tecolearning.study.domain.StudyStatus.*;

class StudyTest {
    private static final Long STUDY_ID = 1L;
    private static final Long PRESENTER_USER_ID = 1L;
    private static final Long ANOTHER_USER_ID = 2L;

    private User presenter;
    private User otherUser;

    @BeforeEach
    void setUp() {
        presenter = User.builder().id(PRESENTER_USER_ID).build();
        otherUser = User.builder().id(ANOTHER_USER_ID).build();
    }

    @Test
    @DisplayName("스터디 정보를 수정한다.")
    void update() {
        Study oldStudy = Study.builder()
                .id(STUDY_ID)
                .description("qweqwe")
                .subject("spring")
                .startDate(LocalDate.of(2019, 12, 12))
                .endDate(LocalDate.of(2019, 12, 23))
                .location("송파")
                .totalNumberOfRecruitment(5)
                .status(StudyStatus.RECRUITING)
                .build();

        Study newStudy = Study.builder()
                .id(STUDY_ID)
                .description("ewqeqw")
                .subject("springggggg")
                .startDate(LocalDate.of(2019, 12, 13))
                .endDate(LocalDate.of(2019, 12, 24))
                .location("은평")
                .totalNumberOfRecruitment(6)
                .status(StudyStatus.RECRUITING)
                .build();

        Study updatedStudy = oldStudy.update(newStudy);

        assertThat(updatedStudy.getDescription()).isEqualTo(newStudy.getDescription());
        assertThat(updatedStudy.getEndDate()).isEqualTo(newStudy.getEndDate());
        assertThat(updatedStudy.getLocation()).isEqualTo(newStudy.getLocation());
        assertThat(updatedStudy.getStartDate()).isEqualTo(newStudy.getStartDate());
        assertThat(updatedStudy.getTotalNumberOfRecruitment()).isEqualTo(newStudy.getTotalNumberOfRecruitment());
        assertThat(updatedStudy.getSubject()).isEqualTo(newStudy.getSubject());
    }

    @Test
    @DisplayName("발제한 회원이 아닐경우 Exception 발생")
    void checkPermission() {
        Study study = Study.builder()
                .id(STUDY_ID)
                .presenter(presenter)
                .build();

        assertThatThrownBy(() -> study.checkNotPresenter(ANOTHER_USER_ID)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("스터디 발제자를 확인한다.")
    void checkIfPresenter() {
        Study study = Study.builder().id(STUDY_ID).presenter(presenter).build();

        assertTrue(study.isCreatedBy(presenter));
    }

    @Test
    @DisplayName("스터디 발제자인 경우 에러를 발생하지 않는다.")
    void check_IfPresenter() {
        Study study = Study.builder().id(STUDY_ID).presenter(presenter).build();

        assertDoesNotThrow(() -> study.checkNotPresenter(PRESENTER_USER_ID));
    }

    @Test
    @DisplayName("스터디 발제자가 아닌 경우 NotPresenterException을 발생한다.")
    void check_IfNotPresenter() {
        Study study = Study.builder().id(STUDY_ID).presenter(presenter).build();

        assertThrows(NotPresenterException.class, () -> study.checkNotPresenter(ANOTHER_USER_ID));
    }

    @Test
    @DisplayName("스터디가 진행중인지 확인한다.")
    void checkIfStudyIsOngoing() {
        Study study = Study.builder()
                .id(STUDY_ID)
                .presenter(presenter)
                .status(ONGOING).build();

        assertTrue(study.isOngoing());
    }

    @Test
    @DisplayName("스터디를 시작하면 스터디가 진행중인 상태로 바뀐다.")
    void startStudy() {
        Study study = Study.builder().id(STUDY_ID).presenter(presenter)
                .status(RECRUITING).build();

        Study startedStudy = study.start(presenter.getId());

        assertTrue(startedStudy.isOngoing());
    }

    @Test
    @DisplayName("발제자가 아닌 사람이 스터디를 시작하려는 경우 NotPresenterException이 발생한다.")
    void cannotStartStudy_ifNotPresenter() {
        Study study = Study.builder().id(STUDY_ID).presenter(presenter).build();

        assertThrows(NotPresenterException.class, () -> study.start(otherUser.getId()));
    }

    @Test
    @DisplayName("모집중인 상태가 아닌 스터디를 시작하려고 하는 경우 CannotStartStudy예외가 발생한다.")
    void cannotStartStudy_ifStudyIsNotInRecruiting() {
        Study study = Study.builder().id(STUDY_ID).presenter(presenter)
                .status(ONGOING).build();

        assertThrows(CannotStartStudyException.class, () -> study.start(presenter.getId()));
    }

    @Test
    @DisplayName("발제자가 스터디를 스터디 완료 상태로 바꾼다.")
    void endStudy() {
        Study study = Study.builder().id(STUDY_ID).presenter(presenter)
                .status(ONGOING).build();

        study.finish(presenter.getId());

        assertThat(study.getStatus()).isEqualTo(FINISHED);
    }

    @Test
    @DisplayName("발제자가 아닌 사람이 스터디를 완료시키는 경우 NotPresenterException이 발생한다.")
    void cannotFinishStudy_ifNotPresenter() {
        Study study = Study.builder().id(STUDY_ID).presenter(presenter).build();

        assertThrows(NotPresenterException.class, () -> study.finish(otherUser.getId()));
    }

    @Test
    @DisplayName("진행중인 상태가 아닌 스터디를 시작하려고 하는 경우 CannotStartStudy예외가 발생한다.")
    void cannotFinishStudy_ifStudyIsNotInRecruiting() {
        Study study = Study.builder().id(STUDY_ID).presenter(presenter)
                .status(FINISHED).build();

        assertThrows(CannotFinishStudyException.class, () -> study.finish(presenter.getId()));
    }

}