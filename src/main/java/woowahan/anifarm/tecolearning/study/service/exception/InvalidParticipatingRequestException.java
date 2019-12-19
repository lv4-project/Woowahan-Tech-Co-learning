package woowahan.anifarm.tecolearning.study.service.exception;

public class InvalidParticipatingRequestException extends RuntimeException {
    private static final String MESSAGE = "잘못된 참가 요청입니다.";

    public InvalidParticipatingRequestException() {
        super(MESSAGE);
    }

    public InvalidParticipatingRequestException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
