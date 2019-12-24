package woowahan.anifarm.tecolearning.study.domain.exception;

public class StudyStatusNotFoundException extends RuntimeException {
    private static final String MESSAGE = "존재하지 않는 상태입니다.";

    public StudyStatusNotFoundException() {
        super(MESSAGE);
    }
}

