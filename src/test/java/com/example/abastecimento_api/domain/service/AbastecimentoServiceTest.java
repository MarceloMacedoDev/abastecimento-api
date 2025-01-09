package com.example.abastecimento_api.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.abastecimento_api.domain.entity.Abastecimento;
import com.example.abastecimento_api.domain.exceptions.FuelingValidationException;
import com.example.abastecimento_api.infrastructure.repository.AbastecimentoRepository;

@ExtendWith(MockitoExtension.class)
public class AbastecimentoServiceTest {

    @Mock
    private AbastecimentoRepository abastecimentoRepository;

    @InjectMocks
    private AbastecimentoService abastecimentoService;

    @Test
    void testListarTodos_empty() {
        Page<Abastecimento> abastecimentos = new PageImpl<>(new ArrayList<>());
        when(abastecimentoRepository.findAll(any(Pageable.class))).thenReturn(abastecimentos);
        assertEquals(abastecimentos, abastecimentoService.listarTodos(PageRequest.of(0, 10)));
    }

    @Test
    void testListarTodos_notEmpty() {
        Abastecimento a1 = new Abastecimento();
        a1.setId(1L);
        Abastecimento a2 = new Abastecimento();
        a2.setId(2L);
        Page<Abastecimento> abastecimentos = new PageImpl<>(List.of(a1, a2));
        when(abastecimentoRepository.findAll(any(Pageable.class))).thenReturn(abastecimentos);
        assertEquals(abastecimentos, abastecimentoService.listarTodos(PageRequest.of(0, 10)));
    }

    @Test
    void testListarPorPlaca_validPlaca() {
        String placa = "ABC-1234";
        Page<Abastecimento> abastecimentos = new PageImpl<>(new ArrayList<>());
        when(abastecimentoRepository.findByPlaca(any(Pageable.class), eq(placa))).thenReturn(abastecimentos);
        assertEquals(abastecimentos, abastecimentoService.listarPorPlaca(PageRequest.of(0, 10), placa));
    }

    @Test
    void testListarPorPlaca_invalidPlaca() {
        assertThrows(FuelingValidationException.class,
                () -> abastecimentoService.listarPorPlaca(PageRequest.of(0, 10), null));
        assertThrows(FuelingValidationException.class,
                () -> abastecimentoService.listarPorPlaca(PageRequest.of(0, 10), ""));
    }

    @Test
    void testBuscarPorId_validId() {
        Long id = 1L;
        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setId(id);
        Optional<Abastecimento> abastecimentoOptional = Optional.of(abastecimento);
        when(abastecimentoRepository.findById(id)).thenReturn(abastecimentoOptional);
        assertEquals(abastecimentoOptional, abastecimentoService.buscarPorId(id));
    }

    @Test
    void testBuscarPorId_invalidId() {
        Long id = 1L;
        when(abastecimentoRepository.findById(id)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), abastecimentoService.buscarPorId(id));

        assertThrows(FuelingValidationException.class, () -> abastecimentoService.buscarPorId(null));
    }

    @Test
    void testSalvar_validAbastecimento() {
        Abastecimento abastecimento = new Abastecimento();
        when(abastecimentoRepository.save(abastecimento)).thenReturn(abastecimento);
        assertEquals(abastecimento, abastecimentoService.salvar(abastecimento));
    }

    @Test
    void testSalvar_invalidAbastecimento() {
        assertThrows(FuelingValidationException.class, () -> abastecimentoService.salvar(null));
    }

    @Test
    void testRemover_validId() {
        Long id = 1L;
        abastecimentoService.remover(id);
        verify(abastecimentoRepository, times(1)).deleteById(id);
    }

    @Test
    void testRemover_invalidId() {
        assertThrows(FuelingValidationException.class, () -> abastecimentoService.remover(null));
    }

    @Test
    void testBuscarPorId_IdValido() {
        // Arrange
        Long idValido = 1L;
        Abastecimento abastecimentoEsperado = new Abastecimento();
        abastecimentoEsperado.setId(idValido);
        when(abastecimentoRepository.findById(idValido)).thenReturn(Optional.of(abastecimentoEsperado));

        // Act
        Optional<Abastecimento> abastecimentoObtido = abastecimentoService.buscarPorId(idValido);

        // Assert
        assertTrue(abastecimentoObtido.isPresent());
        assertEquals(abastecimentoEsperado, abastecimentoObtido.get());
        verify(abastecimentoRepository, times(1)).findById(idValido);
    }

    @Test
    void testBuscarPorId_IdInvalido_Null() {
        assertThrows(FuelingValidationException.class, () -> abastecimentoService.buscarPorId(null));
        verify(abastecimentoRepository, never()).findById(anyLong());
    }

    @Test
    void testBuscarPorId_IdInvalido_NaoExistente() {
        Long idInvalido = 2L;
        when(abastecimentoRepository.findById(idInvalido)).thenReturn(Optional.empty());
        Optional<Abastecimento> abastecimentoObtido = abastecimentoService.buscarPorId(idInvalido);
        assertFalse(abastecimentoObtido.isPresent());
        verify(abastecimentoRepository, times(1)).findById(idInvalido);
    }

    @Test
    void testBuscarPorId_ErroRepositorio() {
        Long idValido = 1L;
        doThrow(new RuntimeException("Erro no repositório")).when(abastecimentoRepository).findById(idValido);
        assertThrows(FuelingValidationException.class, () -> abastecimentoService.buscarPorId(idValido));
        verify(abastecimentoRepository, times(1)).findById(idValido);
    }
}