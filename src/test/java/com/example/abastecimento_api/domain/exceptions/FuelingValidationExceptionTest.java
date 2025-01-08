package com.example.abastecimento_api.domain.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FuelingValidationExceptionTest {

    @Test
    void testFuelingValidationExceptionMessage() {
        String message = "Fuel amount must be positive.";
        FuelingValidationException exception = new FuelingValidationException(message);
        assertEquals(message, exception.getMessage());
    }
}
