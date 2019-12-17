package woowahan.anifarm.tecolearning.studyoutput.service.exception;

public class OutputNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당하는 산출물을 찾을 수 없습니다.";

    public OutputNotFoundException() {
        super(MESSAGE);
    }
}
