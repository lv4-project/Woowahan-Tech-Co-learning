package woowahan.anifarm.tecolearning.study.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.study.service.dto.StudyCreateDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudySummaryDto;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static woowahan.anifarm.tecolearning.study.controller.StudyApiControllerTest.API_STUDIES;

class StudySummaryApiControllerTest extends AbstractWebTestClient {
    private static final String API_STUDY_SUMMARY = "/api/studies/summary";

    @Test
    @DisplayName("page단위로 모집중인 study 요약 정보를 조회한다.")
    void readOnePageOfStudySummary() {
        // Given
        int numberOfStudy = 20;
        int pageOffset = 0;
        int pageSize = 10;
        String requestUrl = createRequestUrl(pageOffset, pageSize);

        addStudies(numberOfStudy);

        // When, Then
        get(requestUrl)
                .expectStatus().isOk()
                .expectBodyList(StudySummaryDto.class)
                .hasSize(pageSize);
    }

    private String createRequestUrl(int pageOffset, int pageSize) {
        String sort = "createdDate,asc";

        return API_STUDY_SUMMARY + "?studyStatus=recruiting"
                + "&page=" + pageOffset
                + "&size=" + pageSize
                + "&sort=" + sort;
    }

    private void addStudies(int numberOfStudy) {
        for (long i = 0; i < numberOfStudy; i++) {
            StudyCreateDto studyCreateDto = new StudyCreateDto(
                    "spring",
                    6,
                    LocalDate.of(2019, 12, 10),
                    LocalDate.of(2020, 1, 10),
                    "송파구",
                    "스터디 모집 합니다."
            );
            postJsonRequest(API_STUDIES, studyCreateDto, StudyInfoDto.class);
        }
    }

    @Test
    @DisplayName("특정 유저가 참여한 모든 study 조회")
    void readAllStudyByUserId() {
        List<StudyInfoDto> studyInfosDto = get("/api/users/1/studies")
                .expectStatus().isOk()
                .expectBodyList(StudyInfoDto.class)
                .hasSize(2)
                .returnResult()
                .getResponseBody();

        StudyInfoDto actual = studyInfosDto.get(0);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getLocation()).isEqualTo("판교");
    }

    @Test
    @DisplayName("모집중인 study 중, 특정 키워드가 들어간 study 검색")
    void search() {
        String keyword = "ko";
        List<StudySummaryDto> responseBody = get("/api/studies/search/recruiting?keyword=" + keyword + "&page=" + 0
                + "&size=" + 5)
                .expectStatus().isOk()
                .expectBodyList(StudySummaryDto.class)
                .hasSize(2)
                .returnResult()
                .getResponseBody();
    }
}