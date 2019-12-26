package woowahan.anifarm.tecolearning.user.exception;

import woowahan.anifarm.tecolearning.common.exception.BadRequestException;

public class UserCreateException extends BadRequestException {
    private static final String MESSAGE = "입력값을 확인해주세요";

    public UserCreateException(Exception e) {
        super(MESSAGE);
    }
}
