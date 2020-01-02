package woowahan.anifarm.tecolearning.study.controller;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowahan.anifarm.tecolearning.AbstractWebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@DatabaseSetup(value = {
        "/woowahan/anifarm/tecolearning/study_participant.xml",
}, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = {
        "/woowahan/anifarm/tecolearning/study_participant.xml",
}, type = DatabaseOperation.DELETE_ALL)
class StudyParticipantApiControllerTest extends AbstractWebTestClient {
    private static final String API_ONE_STUDIES_PARTICIPANTS = "/api/studies/1/participants";
    private static final String API_TWO_STUDIES_PARTICIPANTS = "/api/studies/2/participants";
    private static final String API_THREE_STUDIES_PARTICIPANTS = "/api/studies/3/participants";
    private static final String PARTICIPANT = "participant";

    @Test
    @DisplayName("스터디 참가 인원 확인")
    void countOfStudyParticipant() {
        get(API_ONE_STUDIES_PARTICIPANTS + "/size")
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .isEqualTo(2)
                .consumeWith(document("studyParticipant/get/size",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
        }

    @Test
    @DisplayName("발제자가 아닌 회원이 스터디에 참가한다.")
    void participateStudy() {
        post(API_TWO_STUDIES_PARTICIPANTS, Void.class)
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo(PARTICIPANT)
                .consumeWith(document("studyParticipant/post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("발제자가 자기 스터디에 참가 신청 요청을 보내는 경우 400 response")
    void participateStudy_ifPresenter_requestParticipate() {
        post(API_ONE_STUDIES_PARTICIPANTS, Void.class)
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("참가자가 다시 스터디에 참가 신청 요청을 보내는 경우 400 response")
    void participateStudy_ifParticipant_requestParticipate() {
        String participantStatus = post(API_TWO_STUDIES_PARTICIPANTS, Void.class)
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        assertEquals(PARTICIPANT, participantStatus);


        post(API_TWO_STUDIES_PARTICIPANTS, Void.class)
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("발제자인 경우 스터디 탈퇴 요청 시 400 response")
    void participateStudy_ifPresenter_requestWithdraw() {
        delete(API_ONE_STUDIES_PARTICIPANTS)
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("참가자가 아닌 경우 스터디를 탈퇴 요청 시 400 response")
    void participateStudy_ifNonParticipant_requestWithdraw() {
        delete(API_TWO_STUDIES_PARTICIPANTS)
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("참가자가 스터디를 탈퇴한다.")
    void participateStudy_ifParticipant_requestWithdraw() {
        delete(API_THREE_STUDIES_PARTICIPANTS)
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("studyParticipant/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}
