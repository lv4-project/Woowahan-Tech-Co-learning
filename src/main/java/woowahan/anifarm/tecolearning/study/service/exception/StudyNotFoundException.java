package woowahan.anifarm.tecolearning.study.service.exception;

public class StudyNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당하는 스터디를 찾을 수 없습니다.";

    public StudyNotFoundException() {
        super(MESSAGE);
    }
}
