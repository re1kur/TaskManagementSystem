package re1kur.userservice.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import re1kur.userservice.exception.UserLoginException;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<String> handleUserLoginException(UserLoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
