package woowahan.anifarm.tecolearning.user.domain.exception;

public class UserAuthenticationFailException extends RuntimeException {
    private static final String MESSAGE = "수정권한이 없는 유저입니다.";

    public UserAuthenticationFailException() {
        super(MESSAGE);
    }
}
