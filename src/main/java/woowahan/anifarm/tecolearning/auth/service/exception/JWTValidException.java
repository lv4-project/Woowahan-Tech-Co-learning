package woowahan.anifarm.tecolearning.auth.service.exception;

public class JWTValidException extends RuntimeException {
    private static final String MESSAGE = "유효하지 않은 토큰입니다.";

    public JWTValidException(Throwable cause) {
        super(MESSAGE, cause);
    }

    public JWTValidException() {
        super(MESSAGE);
    }
}
