package woowahan.anifarm.tecolearning.study.domain;

import lombok.Getter;

@Getter
public enum StudyParticipantStatus {
    PRESENTER("presenter"),
    PARTICIPANT("participant"),
    NON_PARTICIPANT("nonParticipant");

    private String status;

    StudyParticipantStatus(String status) {
        this.status = status;
    }
}
