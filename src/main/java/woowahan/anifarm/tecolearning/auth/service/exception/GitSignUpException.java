package woowahan.anifarm.tecolearning.auth.service.exception;

public class GitSignUpException extends RuntimeException {
    private static final String MESSAGE = "필수정보 입력이 필요합니다.";

    public GitSignUpException() {
        super(MESSAGE);
    }
}
