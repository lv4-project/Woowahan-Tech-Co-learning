package woowahan.anifarm.tecolearning.study.domain.exception;

import woowahan.anifarm.tecolearning.common.exception.BadRequestException;

public class NotPresenterException extends BadRequestException {
    private static final String MESSAGE = "발제자가 아닙니다.";

    public NotPresenterException() {
        super(MESSAGE);
    }
}
