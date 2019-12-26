package woowahan.anifarm.tecolearning.web.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowahan.anifarm.tecolearning.study.service.exception.StudyNotFoundException;

@RestControllerAdvice
public class ApiControllerAdvice {
    @ExceptionHandler({StudyNotFoundException.class})
    public ResponseEntity<String> notFound() {
        return ResponseEntity.notFound().build();
    }

}
