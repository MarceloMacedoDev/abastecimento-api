package com.example.abastecimento_api.web.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class AbastecimentoDTOTest {

    @Test
    void testAllGetters() {
        AbastecimentoDTO abastecimento = new AbastecimentoDTO(1L, "ABC-1234", 1000.5, LocalDateTime.now(), 500.0);
        assertEquals(1L, abastecimento.getId());
        assertEquals("ABC-1234", abastecimento.getPlaca());
        assertEquals(1000.5, abastecimento.getQuilometragem());
        assertNotNull(abastecimento.getDataHora());
        assertEquals(500.0, abastecimento.getValorTotal());
    }

    @Test
    void testAllSetters() {
        AbastecimentoDTO abastecimento = new AbastecimentoDTO();
        abastecimento.setId(1L);
        abastecimento.setPlaca("XYZ-9876");
        abastecimento.setQuilometragem(2000.0);
        abastecimento.setDataHora(LocalDateTime.of(2024, 1, 1, 10, 0, 0));
        abastecimento.setValorTotal(600.0);

        assertEquals(1L, abastecimento.getId());
        assertEquals("XYZ-9876", abastecimento.getPlaca());
        assertEquals(2000.0, abastecimento.getQuilometragem());
        assertEquals(LocalDateTime.of(2024, 1, 1, 10, 0, 0), abastecimento.getDataHora());
        assertEquals(600.0, abastecimento.getValorTotal());
    }

    @Test
    void testAbastecimentoDTOCreation() {
        LocalDateTime dataHora = LocalDateTime.of(2024, 10, 27, 10, 0, 0);
        AbastecimentoDTO abastecimento = new AbastecimentoDTO(1L, "XYZ-5678", 2000.0, dataHora, 600.0);
        assertEquals(1L, abastecimento.getId());
        assertEquals("XYZ-5678", abastecimento.getPlaca());
        assertEquals(2000.0, abastecimento.getQuilometragem());
        assertEquals(dataHora, abastecimento.getDataHora());
        assertEquals(600.0, abastecimento.getValorTotal());
    }
}
