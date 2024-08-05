package com.monitor.expense.service.exception;

import com.monitor.expense.service.dto.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Object> exceptionHandler(TransactionException ex) {
        ApplicationException exception = new ApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        log.error("Transaction Exception | exception: {}, status:{}", ex.getMessage(), exception.getStatus());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApplicationException> exceptionHandler(RuntimeException ex) {
        ApplicationException exception = new ApplicationException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        log.error("Generic exception | exception: {}, status: {}", ex.getMessage(), exception.getStatus());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
