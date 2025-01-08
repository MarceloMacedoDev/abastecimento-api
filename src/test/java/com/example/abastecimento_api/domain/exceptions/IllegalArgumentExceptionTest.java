package com.example.abastecimento_api.domain.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class IllegalArgumentExceptionTest {
    @Test
    void testGetMessage() {
        String message = "Fuel amount must be positive.";
        IllegalArgumentException exception = new IllegalArgumentException(message);
        assertEquals(message, exception.getMessage());
    }
}
