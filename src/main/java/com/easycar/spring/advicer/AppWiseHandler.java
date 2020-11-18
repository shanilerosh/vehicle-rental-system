package com.easycar.spring.advicer;

import com.easycar.spring.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWiseHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity handleException(RuntimeException rs) {
        StandardResponse error = new StandardResponse(500, "Error", rs.getLocalizedMessage());
        return new ResponseEntity(error, HttpStatus.OK);
    }
}
