package woowahan.anifarm.tecolearning.study.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudyParticipantApiControllerTest extends AbstractWebTestClient {
    private static final String API_STUDIES_PARTICIPANTS = "/api/studies/1/participants";
    private static final String API_ANOTHER_STUDIES_PARTICIPANTS = "/api/studies/2/participants";

    @Test
    @DisplayName("스터디 참가 인원 확인")
    void countOfStudyParticipant() {
        Integer participantSize = get(API_STUDIES_PARTICIPANTS + "/size")
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .returnResult()
                .getResponseBody();

        assertEquals(2, participantSize);
    }

    @Test
    @DisplayName("발제자가 아닌 회원이 스터디에 참가한다.")
    void participateStudy() {
        String studyParticipantStatus = post(API_ANOTHER_STUDIES_PARTICIPANTS, Void.class)
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertThat(studyParticipantStatus).isEqualTo("participant");
    }

    @Test
    @DisplayName("발제자가 자기 스터디에 참가 신청 요청을 보내는 경우 400 response")
    void participateStudy_ifPresenter_requestParticipate() {
        post(API_STUDIES_PARTICIPANTS, Void.class)
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("참가자가 다시 스터디에 참가 신청 요청을 보내는 경우 400 response")
    void participateStudy_ifParticipant_requestParticipate() {
        String participantStatus = post(API_ANOTHER_STUDIES_PARTICIPANTS, Void.class)
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertEquals("participant", participantStatus);


        post(API_ANOTHER_STUDIES_PARTICIPANTS, Void.class)
                .expectStatus()
                .isBadRequest();
    }
}
