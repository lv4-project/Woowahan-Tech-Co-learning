package woowahan.anifarm.tecolearning.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.study.domain.StudyStatus;
import woowahan.anifarm.tecolearning.study.service.StudyCreateDto;
import woowahan.anifarm.tecolearning.study.service.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.StudyUpdateDto;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static woowahan.anifarm.tecolearning.user.controller.UserControllerTest.SAMPLE_USER_ID;

@Slf4j
public class StudyApiControllerTest extends AbstractWebTestClient {
    private static final String API_STUDIES = "/api/studies";
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
        assertThat(studyInfoDto.getTotalNumberOfParticipants()).isEqualTo(studyCreateDto.getTotalNumberOfParticipants());
        assertThat(studyInfoDto.getStartDate()).isEqualTo(studyCreateDto.getStartDate());
        assertThat(studyInfoDto.getEndDate()).isEqualTo(studyCreateDto.getEndDate());
        assertThat(studyInfoDto.getLocation()).isEqualTo(studyCreateDto.getLocation());
        assertThat(studyInfoDto.getDescription()).isEqualTo(studyCreateDto.getDescription());
        assertThat(studyInfoDto.getStatus()).isEqualTo(StudyStatus.RECRUITING);
    }

    @Test
    @DisplayName("스터디를 조회한다")
    void findStudy() {
        StudyInfoDto study = getRequest(API_STUDIES + "/" + SAMPLE_STUDY_ID, StudyInfoDto.class);

        assertThat(study.getId()).isEqualTo(SAMPLE_STUDY_ID);
        assertThat(study.getStartDate()).isEqualTo(LocalDate.of(2019, 12, 12));
        assertThat(study.getSubject()).isEqualTo("spring");
        assertThat(study.getPresenter().getId()).isEqualTo(SAMPLE_USER_ID);
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
                .subject("응 스프링 안해~")
                .totalNumberOfParticipants(5)
                .location("은평구")
                .startDate(LocalDate.of(2029, 12, 25))
                .endDate(LocalDate.of(2030, 12, 25))
                .description("뭐하지?")
                .build();

        StudyInfoDto updatedInfoDto = put(API_STUDIES + "/" + SAMPLE_STUDY_ID, updateDto)
                .expectStatus()
                .isOk()
                .expectBody(StudyInfoDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(updatedInfoDto.getDescription()).isEqualTo(updateDto.getDescription());
        assertThat(updatedInfoDto.getEndDate()).isEqualTo(updateDto.getEndDate());
        assertThat(updatedInfoDto.getLocation()).isEqualTo(updateDto.getLocation());
        assertThat(updatedInfoDto.getStartDate()).isEqualTo(updateDto.getStartDate());
        assertThat(updatedInfoDto.getSubject()).isEqualTo(updateDto.getSubject());
        assertThat(updatedInfoDto.getTotalNumberOfParticipants()).isEqualTo(updateDto.getTotalNumberOfParticipants());
    }

    // TODO: 2019-12-12 로그인 하지 않은 유저에 대한 TEST
    // TODO: 2019-12-12 발제자가 다른 경우
}