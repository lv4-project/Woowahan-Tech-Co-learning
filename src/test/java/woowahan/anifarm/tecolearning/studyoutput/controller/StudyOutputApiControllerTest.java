package woowahan.anifarm.tecolearning.studyoutput.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;
import woowahan.anifarm.tecolearning.studyoutput.service.dto.StudyOutputDto;

import static org.assertj.core.api.Assertions.assertThat;


class StudyOutputApiControllerTest extends AbstractWebTestClient {
    private static final String API_OUTPUTS = "/api/outputs";

    @Test
    @DisplayName("발제자와 같은 회원이 정상적으로 산출물 작성")
    void create() {
        StudyOutputDto outputCreateDto =
                StudyOutputDto.builder()
                        .title("python 1장")
                        .contents("# 이게 이렇게 된다고?")
                        .build();
        // TODO: 2019-12-16 post method 에 status check 추가?
        StudyOutputDto studyOutputDto =
                postJsonRequest("/api/studies/1/outputs", outputCreateDto, StudyOutputDto.class)
                        .getResponseBody();

        assertThat(studyOutputDto.getTitle()).isEqualTo(outputCreateDto.getTitle());
        assertThat(studyOutputDto.getContents()).isEqualTo(outputCreateDto.getContents());
    }

    @Test
    @DisplayName("발제자와 다른 회원이 작성을 시도할 경우, Error message?")
    void create_fail() {
        StudyOutputDto outputCreateDto = StudyOutputDto.builder()
                .title("php 1장")
                .contents("# 이게 안 된다고?")
                .build();
        // TODO: 2019-12-16 exception 바꾸기, RestControllerAdvice convention 정하기
        postWithoutLogin("/api/studies/1/outputs" + "/" + 1, outputCreateDto)
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("로그인한 사용자가 원하는 id의 산출물을 조회")
    void read() {
        StudyOutputDto studyOutputDto = get(API_OUTPUTS + "/" + 1)
                .expectStatus().isOk()
                .expectBody(StudyOutputDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(studyOutputDto.getTitle()).isEqualTo("Sample studyOutput");
        assertThat(studyOutputDto.getContents()).isEqualTo("Sample studyOutput's contents~");
    }

    @Test
    @DisplayName("비로그인 사용자는 산출물 조회 안됨^^")
    void read_fail() {
        // TODO: 2019-12-16  
    }

    @Test
    @DisplayName("발제자와 같은 회원이 산출물 수정")
    void update() {
        StudyOutputDto studyOutputDto = StudyOutputDto.builder()
                .title("Updated title")
                .contents("updated contents~!~!")
                .build();
        StudyOutputDto updatedDto = put(API_OUTPUTS + "/" + 1, studyOutputDto)
                .expectStatus().isOk()
                .expectBody(StudyOutputDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(updatedDto.getTitle()).isEqualTo(studyOutputDto.getTitle());
        assertThat(updatedDto.getContents()).isEqualTo(studyOutputDto.getContents());
    }

    @Test
    @DisplayName("발제자와 다른 회원이 산출물 수정할 경우, 안됨^^")
    void update_fail() {
        StudyOutputDto studyOutputDto = StudyOutputDto.builder()
                .title("Updated title")
                .contents("updated contents~!~!")
                .build();
        putWithoutLogin(API_OUTPUTS + "/" + 1, studyOutputDto)
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("스터디에 해당하는 모든 산출물 조회")
    void readAll() {
        StudyOutputDto sampleOutput = StudyOutputDto.builder()
                .title("python 1장")
                .contents("# 이게 이렇게 된다고?")
                .build();

        StudyOutputDto studyOutputDto = postJsonRequest("/api/studies/1/outputs",
                sampleOutput,
                StudyOutputDto.class)
                .getResponseBody();

        get("/api/studies/1/outputs")
                .expectStatus().isOk()
                .expectBodyList(StudyOutputDto.class)
                .hasSize(2)
                .contains(studyOutputDto)
                .returnResult()
                .getResponseBody();
    }

    @Test
    @DisplayName("Output id 에 해당하는 산출물 삭제")
    void delete() {
        delete(API_OUTPUTS + "/" + 1)
                .expectStatus().isOk();
    }
}