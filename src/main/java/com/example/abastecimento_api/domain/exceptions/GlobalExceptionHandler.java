package com.example.abastecimento_api.domain.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException thrown by the service layer when
     * a fueling amount is invalid. The exception message is returned as
     * a JSON response with a 400 status code.
     * 
     * @param ex the exception to handle
     * @return a ResponseEntity containing the error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleFuelingValidationException(IllegalArgumentException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles FuelingValidationException thrown by the service layer when
     * a fueling amount is invalid. The exception message is returned as
     * a JSON response with a 400 status code.
     * 
     * @param ex the exception to handle
     * @return a ResponseEntity containing the error message
     */
    @ExceptionHandler(FuelingValidationException.class)
    public ResponseEntity<Object> handleFuelingValidationException(FuelingValidationException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException thrown by Spring when a request
     * contains invalid data. The exception is transformed into a JSON response
     * with a 400 status code and a body containing the error messages for each
     * invalid field.
     * 
     * @param ex the exception to handle
     * @return a ResponseEntity containing the error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all uncaught exceptions by returning a generic error message
     * in a JSON response with a 500 status code. The error message includes
     * the exception's message.
     * 
     * @param ex the exception to handle
     * @return a ResponseEntity containing the error message
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Erro - Formato Invalido ou Dados Invalidos - " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
