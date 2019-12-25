package woowahan.anifarm.tecolearning.study.domain.exception;

import woowahan.anifarm.tecolearning.common.exception.BadRequestException;

public class PresenterException extends BadRequestException {
    public PresenterException(String message) {
        super(message);
    }
}
