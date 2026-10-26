package com.sembunly.exception;

import com.sembunly.dto.ResponseErrorTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class CustomGlobalErrorException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomMessageException.class)
    public ResponseEntity<ResponseErrorTemplate> handleErrorException(CustomMessageException ex) {
        int statusCode;
        try {
            statusCode = Integer.parseInt(ex.getCode());
        } catch (NumberFormatException ignored) {
            statusCode = 500;
        }
        var status = org.springframework.http.HttpStatusCode.valueOf(statusCode);
        return ResponseEntity.status(status).body(
                new ResponseErrorTemplate(ex.getMessage(), ex.getCode(), new Object())
        );
    }

}
