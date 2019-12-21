package woowahan.anifarm.tecolearning.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowahan.anifarm.tecolearning.common.exception.BadRequestException;

@RestControllerAdvice
public class CommonRestControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity responseBadRequest(BadRequestException badRequestException) {
        return ResponseEntity.badRequest().body(badRequestException.getMessage());
    }
}
