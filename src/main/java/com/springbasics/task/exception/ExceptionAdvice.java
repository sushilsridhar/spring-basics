package com.springbasics.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    /* To handle exceptions globally, exception thrown by any controller can be
     * handled here
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> taskNotFoundExceptionHandler(TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> invalidInputExceptionHandler(InvalidInputException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
