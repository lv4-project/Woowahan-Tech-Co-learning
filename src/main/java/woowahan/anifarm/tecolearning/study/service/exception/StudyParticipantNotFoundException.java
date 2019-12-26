package woowahan.anifarm.tecolearning.study.service.exception;

public class StudyParticipantNotFoundException extends RuntimeException {
    private static final String MESSAGE = "잘못된 요청입니다.";

    public StudyParticipantNotFoundException() {
        super(MESSAGE);
    }

    public StudyParticipantNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
