package woowahan.anifarm.tecolearning.user.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "사용자를 찾지 못했습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
