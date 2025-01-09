package com.example.abastecimento_api.usecase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.abastecimento_api.domain.entity.Abastecimento;
import com.example.abastecimento_api.domain.exceptions.IllegalArgumentException;
import com.example.abastecimento_api.domain.service.AbastecimentoService;
import com.example.abastecimento_api.infrastructure.mapper.AbastecimentoMapper;
import com.example.abastecimento_api.web.dto.AbastecimentoDTO;

@ExtendWith(MockitoExtension.class)
public class GerenciarAbastecimentoUseCaseTest {
    @Mock
    private AbastecimentoService abastecimentoService;

    @Mock
    private AbastecimentoMapper abastecimentoMapper;

    @InjectMocks
    private GerenciarAbastecimentoUseCase gerenciarAbastecimentoUseCase;

    @Test
    void listarAbastecimentos_semPlaca() {
        // Arrange
        Page<Abastecimento> abastecimentos = new PageImpl<>(new ArrayList<>());
        Page<AbastecimentoDTO> abastecimentosDTO = new PageImpl<>(new ArrayList<>());

        when(abastecimentoService.listarTodos(any(Pageable.class))).thenReturn(abastecimentos);
        when(abastecimentoMapper.toDTOs(abastecimentos.getContent())).thenReturn(abastecimentosDTO.getContent());

        // Act
        Page<AbastecimentoDTO> resultado = gerenciarAbastecimentoUseCase.listarAbastecimentos(null,
                PageRequest.of(0, 10));

        // Assert
        assertEquals(abastecimentosDTO, resultado);
        verify(abastecimentoService).listarTodos(any(Pageable.class));
        verify(abastecimentoMapper).toDTOs(abastecimentos.getContent());
    }

    @Test
    void adicionarAbastecimento_valido() {
        // Arrange
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setPlaca("ABC-1234");
        abastecimentoDTO.setQuilometragem(100);
        abastecimentoDTO.setDataHora(LocalDateTime.now().minusDays(1)); // Data no passado
        abastecimentoDTO.setValorTotal(50.0);

        Abastecimento abastecimento = new Abastecimento(); // Substitua Abastecimento pela sua classe de entidade
        abastecimento.setId(1L);

        when(abastecimentoMapper.toEntity(abastecimentoDTO)).thenReturn(abastecimento);
        when(abastecimentoService.salvar(abastecimento)).thenReturn(abastecimento);
        when(abastecimentoMapper.toDTO(abastecimento)).thenReturn(abastecimentoDTO);

        // Act
        AbastecimentoDTO resultado = gerenciarAbastecimentoUseCase.adicionarAbastecimento(abastecimentoDTO);

        // Assert
        assertEquals(abastecimentoDTO, resultado);
        verify(abastecimentoService).salvar(abastecimento);
        verify(abastecimentoMapper).toEntity(abastecimentoDTO);
        verify(abastecimentoMapper).toDTO(abastecimento);
    }

    @Test
    void adicionarAbastecimento_quilometragemInvalida() {
        // Arrange
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setQuilometragem(0);

        // Act & Assert
        verify(abastecimentoService, never()).salvar(any());
        verify(abastecimentoMapper, never()).toEntity(any());
    }

    @Test
    void removerAbastecimento_idValido() {
        // Arrange
        Long id = 1L;

        // Act
        gerenciarAbastecimentoUseCase.removerAbastecimento(id);

        // Assert
        verify(abastecimentoService).remover(id);
    }

    @Test
    void listarAbastecimentos_placaValida() {
        String placa = "ABC-1234";
        Abastecimento abastecimento = new Abastecimento();
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        Page<Abastecimento> abastecimentos = new PageImpl<>(List.of(abastecimento));
        Page<AbastecimentoDTO> abastecimentosDTO = new PageImpl<>(List.of(abastecimentoDTO));

        when(abastecimentoService.listarPorPlaca(any(Pageable.class), eq(placa))).thenReturn(abastecimentos);
        when(abastecimentoMapper.toDTOs(abastecimentos.getContent())).thenReturn(abastecimentosDTO.getContent());

        Page<AbastecimentoDTO> resultado = gerenciarAbastecimentoUseCase.listarAbastecimentos(placa,
                PageRequest.of(0, 10));

        assertEquals(abastecimentosDTO, resultado);
        verify(abastecimentoService).listarPorPlaca(any(Pageable.class), eq(placa));
        verify(abastecimentoMapper).toDTOs(abastecimentos.getContent());
    }

    @Test
    void listarAbastecimentos_placaNula() {
        String placa = null;
        Page<Abastecimento> abastecimentos = new PageImpl<>(List.of(new Abastecimento()));
        Page<AbastecimentoDTO> abastecimentosDTO = new PageImpl<>(
                List.of(abastecimentoMapper.toDTO(new Abastecimento())));

        when(abastecimentoService.listarTodos(any(Pageable.class))).thenReturn(abastecimentos);
        when(abastecimentoMapper.toDTOs(abastecimentos.getContent())).thenReturn(abastecimentosDTO.getContent());

        Page<AbastecimentoDTO> resultado = gerenciarAbastecimentoUseCase.listarAbastecimentos(placa,
                PageRequest.of(0, 10));

        assertEquals(abastecimentosDTO, resultado);
        verify(abastecimentoService).listarTodos(any(Pageable.class));
        verify(abastecimentoMapper).toDTOs(abastecimentos.getContent());
    }

    @Test
    void listarAbastecimentos_placaVazia() {
        String placa = "";
        Page<Abastecimento> abastecimentos = new PageImpl<>(List.of(new Abastecimento()));
        Page<AbastecimentoDTO> abastecimentosDTO = new PageImpl<>(
                List.of(abastecimentoMapper.toDTO(new Abastecimento())));

        when(abastecimentoService.listarTodos(any(Pageable.class))).thenReturn(abastecimentos);
        when(abastecimentoMapper.toDTOs(abastecimentos.getContent())).thenReturn(abastecimentosDTO.getContent());

        Page<AbastecimentoDTO> resultado = gerenciarAbastecimentoUseCase.listarAbastecimentos(placa,
                PageRequest.of(0, 10));

        assertEquals(abastecimentosDTO, resultado);
        verify(abastecimentoService).listarTodos(any(Pageable.class));
        verify(abastecimentoMapper).toDTOs(abastecimentos.getContent());
    }

    @Test
    void adicionarAbastecimento_sucesso() {
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setPlaca("ABC-1234");
        abastecimentoDTO.setQuilometragem(100);
        abastecimentoDTO.setDataHora(LocalDateTime.now());
        abastecimentoDTO.setValorTotal(50.0);

        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setId(1L);

        when(abastecimentoMapper.toEntity(abastecimentoDTO)).thenReturn(abastecimento);
        when(abastecimentoService.salvar(abastecimento)).thenReturn(abastecimento);
        when(abastecimentoMapper.toDTO(abastecimento)).thenReturn(abastecimentoDTO);

        AbastecimentoDTO resultado = gerenciarAbastecimentoUseCase.adicionarAbastecimento(abastecimentoDTO);

        assertEquals(abastecimentoDTO, resultado);
        verify(abastecimentoMapper).toEntity(abastecimentoDTO);
        verify(abastecimentoService).salvar(abastecimento);
        verify(abastecimentoMapper).toDTO(abastecimento);
    }

    @Test
    void adicionarAbastecimento_placaInvalida() {
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setPlaca("ABC1234");
        assertThrows(IllegalArgumentException.class,
                () -> gerenciarAbastecimentoUseCase.adicionarAbastecimento(abastecimentoDTO));
    }

    @Test
    void adicionarAbastecimento_dataFutura() {
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setDataHora(LocalDateTime.now().plusDays(1));
        assertThrows(IllegalArgumentException.class,
                () -> gerenciarAbastecimentoUseCase.adicionarAbastecimento(abastecimentoDTO));
    }

    @Test
    void adicionarAbastecimento_valorInvalido() {
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setValorTotal(0);
        assertThrows(IllegalArgumentException.class,
                () -> gerenciarAbastecimentoUseCase.adicionarAbastecimento(abastecimentoDTO));
    }

    @Test
    void removerAbastecimento_sucesso() {
        Long id = 1L;
        gerenciarAbastecimentoUseCase.removerAbastecimento(id);
        verify(abastecimentoService).remover(id);
    }

    @Test
    void validarAbastecimento_quilometragemInvalida() {
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setQuilometragem(0);
        abastecimentoDTO.setPlaca("ABC-1234");
        abastecimentoDTO.setDataHora(LocalDateTime.now().minusDays(1));
        abastecimentoDTO.setValorTotal(50.0);

        assertThrows(com.example.abastecimento_api.domain.exceptions.IllegalArgumentException.class,
                () -> gerenciarAbastecimentoUseCase.adicionarAbastecimento(abastecimentoDTO),
                "Quilometragem deve ser maior que zero.");
    }

    @Test
    void validarAbastecimento_placaInvalida() {
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setQuilometragem(100);
        abastecimentoDTO.setPlaca("ABC1234233"); // Placa sem hífen
        abastecimentoDTO.setDataHora(LocalDateTime.now().minusDays(1));
        abastecimentoDTO.setValorTotal(50.0);

        assertThrows(com.example.abastecimento_api.domain.exceptions.IllegalArgumentException.class,
                () -> gerenciarAbastecimentoUseCase.adicionarAbastecimento(abastecimentoDTO),
                "Placa inválida.");
    }

    @Test
    void validarAbastecimento_dataFutura() {
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setQuilometragem(100);
        abastecimentoDTO.setPlaca("ABC-1234");
        abastecimentoDTO.setDataHora(LocalDateTime.now().plusDays(1));
        abastecimentoDTO.setValorTotal(50.0);

        assertThrows(com.example.abastecimento_api.domain.exceptions.IllegalArgumentException.class,
                () -> gerenciarAbastecimentoUseCase.adicionarAbastecimento(abastecimentoDTO),
                "Data e hora não podem ser no futuro.");
    }

    @Test
    void validarAbastecimento_valorTotalInvalido() {
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setQuilometragem(100);
        abastecimentoDTO.setPlaca("ABC-1234");
        abastecimentoDTO.setDataHora(LocalDateTime.now().minusDays(1));
        abastecimentoDTO.setValorTotal(0);

        assertThrows(com.example.abastecimento_api.domain.exceptions.IllegalArgumentException.class,
                () -> gerenciarAbastecimentoUseCase.adicionarAbastecimento(abastecimentoDTO),
                "Valor total deve ser maior que zero.");
    }

    @Test
    void validarAbastecimento_dadosValidos() {
        AbastecimentoDTO abastecimentoDTO = new AbastecimentoDTO();
        abastecimentoDTO.setQuilometragem(100);
        abastecimentoDTO.setPlaca("ABC-1234");
        abastecimentoDTO.setDataHora(LocalDateTime.now().minusDays(1));
        abastecimentoDTO.setValorTotal(50.0);

        Abastecimento abastecimento = new Abastecimento();
        when(abastecimentoMapper.toEntity(abastecimentoDTO)).thenReturn(abastecimento);
        when(abastecimentoService.salvar(abastecimento)).thenReturn(abastecimento);
        when(abastecimentoMapper.toDTO(abastecimento)).thenReturn(abastecimentoDTO);

        AbastecimentoDTO resultado = gerenciarAbastecimentoUseCase.adicionarAbastecimento(abastecimentoDTO);

        assertNotNull(resultado);
        verify(abastecimentoMapper).toEntity(abastecimentoDTO);
        verify(abastecimentoService).salvar(abastecimento);
    }

}