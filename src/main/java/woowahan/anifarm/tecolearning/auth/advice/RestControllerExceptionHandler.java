package woowahan.anifarm.tecolearning.auth.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import woowahan.anifarm.tecolearning.auth.service.exception.GitSignUpException;
import woowahan.anifarm.tecolearning.auth.service.exception.JWTValidException;
import woowahan.anifarm.tecolearning.auth.service.exception.OauthException;
import woowahan.anifarm.tecolearning.auth.service.exception.UserAccountException;

@ControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler {

    @ExceptionHandler({OauthException.class, GitSignUpException.class,
            JWTValidException.class, UserAccountException.class})
    public ResponseEntity handleOauthException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
