package woowahan.anifarm.tecolearning.study.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.study.domain.exception.StudyStatusNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudyStatusTest {

    @Test
    @DisplayName("문자열 값에 해당하는 StudyStatus enum을 리턴한다.")
    void of() {
        assertThat(StudyStatus.of("recruiting")).isEqualTo(StudyStatus.RECRUITING);
        assertThat(StudyStatus.of("ongoing")).isEqualTo(StudyStatus.ONGOING);
        assertThat(StudyStatus.of("finished")).isEqualTo(StudyStatus.FINISHED);
    }

    @Test
    @DisplayName("잘못된 문자열 이름으로 StudyStatus를 찾는 경우 StudyStatusNotFound 예외를 던진다.")
    void failToFindStudyStatus_ifWrongStudyStatusName() {
        assertThrows(StudyStatusNotFoundException.class, () -> StudyStatus.of("wrong"));
    }
}