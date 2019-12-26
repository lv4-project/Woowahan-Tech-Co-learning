package woowahan.anifarm.tecolearning.study.domain.exception;

public class CannotFinishStudyException extends RuntimeException {
    private static final String MESSAGE = "스터디를 종료할 수 없습니다.";

    public CannotFinishStudyException() {
        super(MESSAGE);
    }
}
