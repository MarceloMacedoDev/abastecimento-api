package com.example.abastecimento_api.usecase;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.abastecimento_api.domain.entity.Abastecimento;
import com.example.abastecimento_api.domain.service.AbastecimentoService;
import com.example.abastecimento_api.infrastructure.mapper.AbastecimentoMapper;
import com.example.abastecimento_api.web.dto.AbastecimentoDTO;

@Service
public class GerenciarAbastecimentoUseCase {

    @Autowired
    private AbastecimentoService abastecimentoService;

    @Autowired
    private AbastecimentoMapper abastecimentoMapper;

    public Page<AbastecimentoDTO> listarAbastecimentos(String placa, Pageable pageable) {
        Page<Abastecimento> abastecimentos;
        if (placa != null && !placa.isEmpty()) {
            abastecimentos = abastecimentoService.listarPorPlaca(pageable, placa);
        } else {
            abastecimentos = abastecimentoService.listarTodos(pageable);
        }

        return new PageImpl<>(
                abastecimentos.getContent().stream()
                        .map(abastecimentoMapper::toDTO)
                        .toList(),
                pageable,
                abastecimentos.getTotalElements());
    }

    public AbastecimentoDTO adicionarAbastecimento(AbastecimentoDTO abastecimentoDTO) {
        validarAbastecimento(abastecimentoDTO);
        Abastecimento abastecimento = abastecimentoMapper.toEntity(abastecimentoDTO);
        abastecimento = abastecimentoService.salvar(abastecimento);
        return abastecimentoMapper.toDTO(abastecimento);
    }

    public void removerAbastecimento(Long id) {
        abastecimentoService.remover(id);
    }

    private void validarAbastecimento(AbastecimentoDTO abastecimentoDTO) {
        if (abastecimentoDTO.getQuilometragem() <= 0) {
            throw new com.example.abastecimento_api.domain.exceptions.IllegalArgumentException(
                    "Quilometragem deve ser maior que zero.");
        }
        if (!abastecimentoDTO.getPlaca().matches("^[A-Z]{3}-?\\d{4}$")) {
            throw new com.example.abastecimento_api.domain.exceptions.IllegalArgumentException("Placa inválida.");
        }
        if (abastecimentoDTO.getDataHora().isAfter(LocalDateTime.now())) {
            throw new com.example.abastecimento_api.domain.exceptions.IllegalArgumentException(
                    "Data e hora não podem ser no futuro.");
        }
        if (abastecimentoDTO.getValorTotal() <= 0) {
            throw new com.example.abastecimento_api.domain.exceptions.IllegalArgumentException(
                    "Valor total deve ser maior que zero.");
        }
    }
}
