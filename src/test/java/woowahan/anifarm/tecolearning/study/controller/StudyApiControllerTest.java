package woowahan.anifarm.tecolearning.study.controller;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.study.domain.StudyStatus;
import woowahan.anifarm.tecolearning.study.service.dto.StudyCreateDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyDetailInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyUpdateDto;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static woowahan.anifarm.tecolearning.study.domain.StudyStatus.FINISHED;
import static woowahan.anifarm.tecolearning.study.domain.StudyStatus.ONGOING;
import static woowahan.anifarm.tecolearning.user.controller.UserControllerTest.SAMPLE_USER_ID;

@DatabaseSetup(value = {
        "/woowahan/anifarm/tecolearning/study.xml",
}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {
        "/woowahan/anifarm/tecolearning/study.xml",
}, type = DatabaseOperation.DELETE_ALL)
public class StudyApiControllerTest extends AbstractWebTestClient {
    static final String API_STUDIES = "/api/studies";
    private static final long SAMPLE_STUDY_ID = 1L;

    @Test
    @DisplayName("새로운 Study를 생성한다.")
    void createStudy() {
        StudyCreateDto studyCreateDto = new StudyCreateDto(
                "spring",
                6,
                LocalDate.of(2019, 12, 10),
                LocalDate.of(2020, 1, 10),
                "송파구",
                "스터디 모집 합니다."
        );

        StudyInfoDto studyInfoDto = postJsonRequest(
                API_STUDIES,
                studyCreateDto,
                StudyInfoDto.class
        ).getResponseBody();

        assertThat(studyInfoDto.getSubject()).isEqualTo(studyCreateDto.getSubject());
        assertThat(studyInfoDto.getTotalNumberOfRecruitment()).isEqualTo(studyCreateDto.getTotalNumberOfRecruitment());
        assertThat(studyInfoDto.getStartDate()).isEqualTo(studyCreateDto.getStartDate());
        assertThat(studyInfoDto.getEndDate()).isEqualTo(studyCreateDto.getEndDate());
        assertThat(studyInfoDto.getLocation()).isEqualTo(studyCreateDto.getLocation());
        assertThat(studyInfoDto.getDescription()).isEqualTo(studyCreateDto.getDescription());
        assertThat(studyInfoDto.getStatus()).isEqualTo(StudyStatus.RECRUITING);
        assertThat(studyInfoDto.getStudyParticipantStatus()).isEqualTo("presenter");
    }

    @Test
    @DisplayName("스터디를 조회한다")
    void findStudy() {
        StudyDetailInfoDto study = getRequest(API_STUDIES + "/" + SAMPLE_STUDY_ID, StudyDetailInfoDto.class);

        assertThat(study.getId()).isEqualTo(SAMPLE_STUDY_ID);
        assertThat(study.getStartDate()).isEqualTo(LocalDate.of(2019, 12, 12));
        assertThat(study.getSubject()).isEqualTo("spring");
        assertThat(study.getPresenter().getId()).isEqualTo(SAMPLE_USER_ID);
    }

    @Test
    @DisplayName("발제자가 스터디를 조회하는 경우, studyParticipantStatus값은 presenter다.")
    void findStudy_ifUserIsPresenter() {
        StudyDetailInfoDto study = getRequest(API_STUDIES + "/" + SAMPLE_STUDY_ID, StudyDetailInfoDto.class);

        assertThat(study.getStudyParticipantStatus()).isEqualTo("presenter");
    }

    @Test
    @DisplayName("회원이고 비참가자가 스터디를 조회하는 경우, studyParticipantStatus값은 nonParticipant다.")
    void findStudy_ifUserIsMemberAndNonParticipant() {
        StudyDetailInfoDto study = getRequest(API_STUDIES + "/2", StudyDetailInfoDto.class);

        assertThat(study.getStudyParticipantStatus()).isEqualTo("nonParticipant");
    }

    @Test
    @DisplayName("회원이고 참가자가 조회하는 경우, studyParticipantStatus값은 participant다.")
    void findStudy_ifUserIsMemberAndParticipant() {
        post(API_STUDIES + "/2/participants", Void.class).expectStatus().isOk();
        StudyDetailInfoDto study = getRequest(API_STUDIES + "/2", StudyDetailInfoDto.class);

        assertThat(study.getStudyParticipantStatus()).isEqualTo("participant");
    }

    @Test
    @DisplayName("찾으려는 스터디가 DB에 없는 경우 404 response code를 되돌려준다.")
    void findStudy_ifNoIdInDB() {
        long notExistingId = 999L;

        get(API_STUDIES + "/" + notExistingId)
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("스터디를 수정한다.")
    void put() {
        StudyUpdateDto updateDto = StudyUpdateDto.builder()
                .subject("spring")
                .totalNumberOfRecruitment(5)
                .location("은평구")
                .startDate(LocalDate.of(2029, 12, 25))
                .endDate(LocalDate.of(2030, 12, 25))
                .description("뭐하지?")
                .build();

        StudyDetailInfoDto updatedInfoDto = put(API_STUDIES + "/" + SAMPLE_STUDY_ID, updateDto)
                .expectStatus()
                .isOk()
                .expectBody(StudyDetailInfoDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(updatedInfoDto.getDescription()).isEqualTo(updateDto.getDescription());
        assertThat(updatedInfoDto.getEndDate()).isEqualTo(updateDto.getEndDate());
        assertThat(updatedInfoDto.getLocation()).isEqualTo(updateDto.getLocation());
        assertThat(updatedInfoDto.getStartDate()).isEqualTo(updateDto.getStartDate());
        assertThat(updatedInfoDto.getSubject()).isEqualTo(updateDto.getSubject());
        assertThat(updatedInfoDto.getTotalNumberOfRecruitment()).isEqualTo(updateDto.getTotalNumberOfRecruitment());
    }

    // TODO: 2019-12-12 로그인 하지 않은 유저에 대한 TEST
    // TODO: 2019-12-12 발제자가 다른 경우

    @Test
    @DisplayName("발제자가 자기 스터디를 폭파한다.")
    void removeStudy_ifPresenter() {
        String url = "/1/participants/size";

        Integer beforeDelete = get(API_STUDIES + url)
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .returnResult()
                .getResponseBody();

        assertEquals(2, beforeDelete);

        delete(API_STUDIES + "/" + SAMPLE_STUDY_ID)
                .expectStatus()
                .isOk();

        Integer afterDelete = get(API_STUDIES + url)
                .expectStatus()
                .isOk()
                .expectBody(Integer.class)
                .returnResult()
                .getResponseBody();

        assertEquals(0, afterDelete);
    }

    @Test
    @DisplayName("발제자가 아닌 경우 스터리르 폭파 시도할 시 BadRequest")
    void removeStudy_ifNotPresenter() {
        delete(API_STUDIES + "/2")
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("발제가가 스터디를 시작하면 스터디 상태가 ongoing으로 바뀐다.")
    void startStudy() {
        StudyDetailInfoDto responseBody = patch(API_STUDIES + "/" + SAMPLE_STUDY_ID + "/start", Void.class)
                .expectStatus().isOk()
                .expectBody(StudyDetailInfoDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody.getStudyStatus()).isEqualTo(ONGOING.getName());
    }

    @Test
    @DisplayName("발제자가 스터디를 완료시키면 스터디 상태가 finished로 바뀐다.")
    void endStudy() {
        // Given
        patch(API_STUDIES + "/" + SAMPLE_STUDY_ID + "/start", Void.class)
                .expectStatus().isOk();

        // When
        StudyDetailInfoDto responseBody = patch(API_STUDIES + "/" + SAMPLE_STUDY_ID + "/finish", Void.class)
                .expectStatus().isOk()
                .expectBody(StudyDetailInfoDto.class)
                .returnResult()
                .getResponseBody();

        // Then
        assertThat(responseBody.getStudyStatus()).isEqualTo(FINISHED.getName());
    }
}