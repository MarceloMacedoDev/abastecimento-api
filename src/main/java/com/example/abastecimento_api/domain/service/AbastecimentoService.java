package com.example.abastecimento_api.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.abastecimento_api.domain.entity.Abastecimento;
import com.example.abastecimento_api.domain.exceptions.FuelingValidationException;
import com.example.abastecimento_api.infrastructure.repository.AbastecimentoRepository;

@Service
public class AbastecimentoService {
    @Autowired
    private AbastecimentoRepository abastecimentoRepository;

    /**
     * Retrieves a list of all fuel refueling records from the repository.
     *
     * @return a list of Abastecimento entities representing all refueling records
     * @throws FuelingValidationException if an error occurs while fetching the
     *                                    records
     */

    public Page<Abastecimento> listarTodos(Pageable pageable) {
        try {
            return abastecimentoRepository.findAll(pageable);
        } catch (Exception e) {
            throw new FuelingValidationException("Erro ao listar todos os abastecimentos: " + e.getMessage());
        }
    }

    public Page<Abastecimento> listarPorPlaca(Pageable pageable, String placa) {
        if (placa == null || placa.isEmpty()) {
            throw new FuelingValidationException("Placa é obrigatória");
        }
        try {
            return abastecimentoRepository.findByPlaca(pageable, placa);
        } catch (Exception e) {
            throw new FuelingValidationException("Erro ao listar abastecimentos por placa: " + e.getMessage());
        }
    }

    /**
     * Retrieves a single Abastecimento record from the repository, by its ID.
     * 
     * @param id the ID of the Abastecimento to retrieve
     * @return an Optional containing the Abastecimento if found, or empty if not
     *         found
     * @throws FuelingValidationException if an error occurs while fetching the
     *                                    record
     */
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

    /**
     * Saves a new or updates an existing Abastecimento record in the repository.
     * 
     * @param abastecimento the Abastecimento to save or update
     * @return the saved Abastecimento
     * @throws FuelingValidationException if an error occurs while saving the record
     */
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