package com.example.abastecimento_api.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.abastecimento_api.usecase.GerenciarAbastecimentoUseCase;
import com.example.abastecimento_api.web.dto.AbastecimentoDTO;

@ExtendWith(MockitoExtension.class)
public class AbastecimentoControllerTest {

    @Mock
    private GerenciarAbastecimentoUseCase abastecimentoService;

    @InjectMocks
    private AbastecimentoController abastecimentoController;

    @Test
    void testCriarAbastecimento() {
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO(null, "ABC-1234", 50.0, null, 10.0);

        when(abastecimentoService.adicionarAbastecimento(abastecimentoDTO)).thenReturn(abastecimentoDTO);

        ResponseEntity<AbastecimentoDTO> response = abastecimentoController.criarAbastecimento(abastecimentoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ABC-1234", response.getBody().getPlaca());
        verify(abastecimentoService, times(1)).adicionarAbastecimento(abastecimentoDTO);
    }

    @Test
    void testDeletarAbastecimento() {
        Long id = 1L;
        abastecimentoController.deletarAbastecimento(id);
        verify(abastecimentoService, times(1)).removerAbastecimento(id);
    }

    @Test
    void testListarAbastecimentos_ComPlaca() {
        String placa = "XYZ-5678";
        List<AbastecimentoDTO> abastecimentos = List.of(new AbastecimentoDTO(1L, "XYZ-5678", 40.0, null, 200.0));
        when(abastecimentoService.listarAbastecimentos(placa)).thenReturn(abastecimentos);

        ResponseEntity<List<AbastecimentoDTO>> response = abastecimentoController.listarAbastecimentos(placa);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(abastecimentos, response.getBody());
        verify(abastecimentoService, times(1)).listarAbastecimentos(placa);
    }

    @Test
    void testListarAbastecimentos_SemPlaca() {
        List<AbastecimentoDTO> abastecimentos = List.of(new AbastecimentoDTO(2L, "ABC-1234", 50.0, null, 250.0));
        when(abastecimentoService.listarAbastecimentos(null)).thenReturn(abastecimentos);

        ResponseEntity<List<AbastecimentoDTO>> response = abastecimentoController.listarAbastecimentos(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(abastecimentos, response.getBody());
        verify(abastecimentoService, times(1)).listarAbastecimentos(null);
    }

    @Test
    void testListarAbastecimentos_Vazio() {
        when(abastecimentoService.listarAbastecimentos(null)).thenReturn(List.of());
        ResponseEntity<List<AbastecimentoDTO>> response = abastecimentoController.listarAbastecimentos(null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(abastecimentoService, times(1)).listarAbastecimentos(null);
    }
}