package woowahan.anifarm.tecolearning.auth.service.exception;

public class TokenNotFoundException extends RuntimeException {
    private static final String MESSAGE = "토큰이 없습니다.";

    public TokenNotFoundException() {
        super(MESSAGE);
    }
}
