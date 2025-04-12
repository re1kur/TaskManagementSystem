package re1kur.verificationservice.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import re1kur.verificationservice.exception.VerificationException;

@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(VerificationException.class)
    public ResponseEntity<String> handleVerificationException(VerificationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
