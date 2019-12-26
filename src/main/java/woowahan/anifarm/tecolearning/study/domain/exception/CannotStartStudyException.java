package woowahan.anifarm.tecolearning.study.domain.exception;

public class CannotStartStudyException extends RuntimeException {
    private static final String MESSAGE = "스터디를 시작할 수 없습니다.";

    public CannotStartStudyException() {
        super(MESSAGE);
    }
}
