package com.example.abastecimento_api.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.abastecimento_api.domain.entity.Abastecimento;
import com.example.abastecimento_api.domain.exceptions.FuelingValidationException;
import com.example.abastecimento_api.infrastructure.repository.AbastecimentoRepository;

@Service
public class AbastecimentoService {
    @Autowired
    private AbastecimentoRepository abastecimentoRepository;

    public List<Abastecimento> listarTodos() {
        try {
            return abastecimentoRepository.findAll();
        } catch (Exception e) {
            throw new FuelingValidationException("Erro ao listar todos os abastecimentos: " + e.getMessage());
        }
    }

    public List<Abastecimento> listarPorPlaca(String placa) {
        if (placa == null || placa.isEmpty()) {
            throw new FuelingValidationException("Placa é obrigatória");
        }
        try {
            return abastecimentoRepository.findByPlaca(placa);
        } catch (Exception e) {
            throw new FuelingValidationException("Erro ao listar abastecimentos por placa: " + e.getMessage());
        }
    }

    public Optional<Abastecimento> buscarPorId(Long id) {
        if (id == null) {
            throw new FuelingValidationException("ID é obrigatório");
        }
        try {
            return abastecimentoRepository.findById(id);
        } catch (Exception e) {
            throw new FuelingValidationException("Erro ao buscar abastecimento por ID: " + e.getMessage());
        }
    }

    public Abastecimento salvar(Abastecimento abastecimento) {
        if (abastecimento == null) {
            throw new FuelingValidationException("Abastecimento é obrigatório");
        }
        try {
            return abastecimentoRepository.save(abastecimento);
        } catch (Exception e) {
            throw new FuelingValidationException("Erro ao salvar abastecimento: " + e.getMessage());
        }
    }

    public void remover(Long id) {
        if (id == null) {
            throw new FuelingValidationException("ID é obrigatório");
        }
        try {
            abastecimentoRepository.deleteById(id);
        } catch (Exception e) {
            throw new FuelingValidationException("Erro ao remover abastecimento: " + e.getMessage());
        }
    }
}