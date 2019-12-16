package woowahan.anifarm.tecolearning.study.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.study.service.dto.StudyCreateDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudyInfoDto;
import woowahan.anifarm.tecolearning.study.service.dto.StudySummaryDto;

import java.time.LocalDate;

import static woowahan.anifarm.tecolearning.study.controller.StudyApiControllerTest.API_STUDIES;

class StudySummaryApiControllerTest extends AbstractWebTestClient {
    private static final String API_STUDY_SUMMARY = "/api/studies/summary";

    @Test
    @DisplayName("page단위로 study 요약 정보를 조회한다.")
    void readOnePageOfStudySummary() {
        int numberOfStudy = 20;
        addStudies(numberOfStudy);

        int pageOffset = 0;
        int pageSize = 10;
        String sort = "createdDate,asc";
        String requestUrl = API_STUDY_SUMMARY + "?page=" + pageOffset
                + "&size=" + pageSize
                + "&sort=" + sort;

        get(requestUrl)
                .expectStatus().isOk()
                .expectBodyList(StudySummaryDto.class)
                .hasSize(pageSize);
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
}