package woowahan.anifarm.tecolearning.user.exception;

public class UserCreateException extends RuntimeException {
    private static final String MESSAGE = "사용자 생성 실패";

    public UserCreateException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
