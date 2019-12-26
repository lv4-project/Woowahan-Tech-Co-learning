package woowahan.anifarm.tecolearning.study.service.exception;

import woowahan.anifarm.tecolearning.common.exception.BadRequestException;

public class CannotParticipateException extends BadRequestException {
    private static final String MESSAGE = "스터디에 참여할 수 없습니다.";

    public CannotParticipateException() {
        super(MESSAGE);
    }
}
