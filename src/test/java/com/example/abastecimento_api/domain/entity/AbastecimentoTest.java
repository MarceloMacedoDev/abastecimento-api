package com.example.abastecimento_api.domain.entity;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class AbastecimentoTest {

    private final Validator validator;

    public AbastecimentoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Abastecimento abastecimento;

    @BeforeEach
    public void setUp() {
        abastecimento = new Abastecimento();
        abastecimento.setPlaca("ABC1234");
        abastecimento.setQuilometragem(100);
        abastecimento.setDataHora(LocalDateTime.now().minusDays(1));
        abastecimento.setValorTotal(50);
    }

    @Test
    void testValidaPlacaObrigatoria() {

        Set<ConstraintViolation<Abastecimento>> violations = validator.validate(abastecimento);
        assertEquals(0, violations.size()); // Exatamente uma violação esperada
        assertFalse(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("placa")));
    }

    @Test
    void testPlacaValida() {

        Set<ConstraintViolation<Abastecimento>> violations = validator.validate(abastecimento);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidaQuilometragemPositiva() {

        abastecimento.setQuilometragem(-10);
        Set<ConstraintViolation<Abastecimento>> violations = validator.validate(abastecimento);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quilometragem")));
    }

    @Test
    void testQuilometragemPositiva() {

        Set<ConstraintViolation<Abastecimento>> violations = validator.validate(abastecimento);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidaDataHoraPassada() {

        Set<ConstraintViolation<Abastecimento>> violations = validator.validate(abastecimento);
        assertEquals(0, violations.size());
        assertFalse(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dataHora")));
    }

    @Test
    void testDataHoraValida() {

        Set<ConstraintViolation<Abastecimento>> violations = validator.validate(abastecimento);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidaValorTotalPositivo() {
        abastecimento.setValorTotal(-50);
        Set<ConstraintViolation<Abastecimento>> violations = validator.validate(abastecimento);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("valorTotal")));
    }

    @Test
    void testValorTotalPositivo() {

        Set<ConstraintViolation<Abastecimento>> violations = validator.validate(abastecimento);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testAbastecimentoValido() {
        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setPlaca("ABC-1234");
        abastecimento.setQuilometragem(1000);
        abastecimento.setDataHora(LocalDateTime.now().minusDays(1));
        abastecimento.setValorTotal(50.0);

        assertEquals("ABC-1234", abastecimento.getPlaca());
        assertEquals(1000.0, abastecimento.getQuilometragem());
        assertNotNull(abastecimento.getDataHora());
        assertEquals(50.0, abastecimento.getValorTotal());
    }

    @Test
    void testAbastecimentoComId() {
        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setId(1L);
        assertEquals(1L, abastecimento.getId());
    }

    @Test
    void testAbastecimentoSemId() {
        Abastecimento abastecimento = new Abastecimento();
        assertNull(abastecimento.getId());
    }

    @Test
    void testPlacaValidaComEspacos() {
        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setPlaca("ABC 1234"); // Placa com espaços
        assertEquals("ABC 1234", abastecimento.getPlaca());
    }

    @Test
    void testPlacaValidaComCaracteresEspeciais() {
        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setPlaca("ABC-1234"); // Placa com hífen
        assertEquals("ABC-1234", abastecimento.getPlaca());
    }

}