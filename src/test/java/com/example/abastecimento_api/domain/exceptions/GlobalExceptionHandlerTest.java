package com.example.abastecimento_api.domain.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class GlobalExceptionHandlerTest {

    @Test
    void testHandleAllExceptions() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new Exception("Internal Server Error");
        ResponseEntity<Object> response = handler.handleAllExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Erro - Formato Invalido ou Dados Invalidos - Internal Server Error", body.get("message"));
    }

    @Test
    void testHandleFuelingValidationException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        IllegalArgumentException ex = new IllegalArgumentException("Invalid Fueling Data");
        ResponseEntity<Object> response = handler.handleFuelingValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Invalid Fueling Data", body.get("message"));
    }

    @Test
    void testHandleFuelingValidationException2() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        FuelingValidationException ex = new FuelingValidationException("Fueling Validation Error");
        ResponseEntity<Object> response = handler.handleFuelingValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("Fueling Validation Error", body.get("message"));
    }

    @Test
    void testHandleValidationExceptions() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        BindingResult bindingResult = mock(BindingResult.class);
        List<FieldError> fieldErrors = new ArrayList<>();
        FieldError fieldError1 = mock(FieldError.class);
        when(fieldError1.getField()).thenReturn("field1");
        when(fieldError1.getDefaultMessage()).thenReturn("error1");
        fieldErrors.add(fieldError1);
        FieldError fieldError2 = mock(FieldError.class);
        when(fieldError2.getField()).thenReturn("field2");
        when(fieldError2.getDefaultMessage()).thenReturn("error2");
        fieldErrors.add(fieldError2);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<Object> response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("error1", body.get("field1"));
        assertEquals("error2", body.get("field2"));
    }

}
