package woowahan.anifarm.tecolearning.auth.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import woowahan.anifarm.tecolearning.auth.service.exception.GitSignUpException;
import woowahan.anifarm.tecolearning.auth.service.exception.JWTValidException;
import woowahan.anifarm.tecolearning.auth.service.exception.OauthException;
import woowahan.anifarm.tecolearning.auth.service.exception.UserAccountException;
import woowahan.anifarm.tecolearning.study.service.exception.StudyParticipantNotFoundException;

@ControllerAdvice(annotations = Controller.class)
public class RestControllerExceptionHandler {

    @ExceptionHandler({OauthException.class, GitSignUpException.class,
            JWTValidException.class, UserAccountException.class})
    public ResponseEntity handleOauthException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({StudyParticipantNotFoundException.class})
    public ResponseEntity handleInvalidParticipatingException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
