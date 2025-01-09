package com.example.abastecimento_api.web.controller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        Page<AbastecimentoDTO> abastecimentos = new PageImpl<>(
                List.of(new AbastecimentoDTO(1L, "XYZ-5678", 40.0, null, 200.0)));
        when(abastecimentoService.listarAbastecimentos(eq(placa), any(Pageable.class))).thenReturn(abastecimentos);

        ResponseEntity<Page<AbastecimentoDTO>> response = abastecimentoController.listarAbastecimentos(placa, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(abastecimentos, response.getBody());
        verify(abastecimentoService, times(1)).listarAbastecimentos(eq(placa), any(Pageable.class));
    }

    @Test
    void testListarAbastecimentos_SemPlaca() {
        Page<AbastecimentoDTO> abastecimentos = new PageImpl<>(
                List.of(new AbastecimentoDTO(2L, "ABC-1234", 50.0, null, 250.0)));
        when(abastecimentoService.listarAbastecimentos(isNull(), any(Pageable.class))).thenReturn(abastecimentos);

        ResponseEntity<Page<AbastecimentoDTO>> response = abastecimentoController.listarAbastecimentos(null, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(abastecimentos, response.getBody());
        verify(abastecimentoService, times(1)).listarAbastecimentos(isNull(), any(Pageable.class));
    }

    @Test
    void testListarAbastecimentos_Vazio() {
        Page<AbastecimentoDTO> abastecimentos = new PageImpl<>(new ArrayList<>());
        when(abastecimentoService.listarAbastecimentos(isNull(), any(Pageable.class))).thenReturn(abastecimentos);

        ResponseEntity<Page<AbastecimentoDTO>> response = abastecimentoController.listarAbastecimentos(null, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(abastecimentoService, times(1)).listarAbastecimentos(isNull(), any(Pageable.class));
    }
}