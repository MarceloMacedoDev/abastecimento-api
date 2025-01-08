package com.example.abastecimento_api.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.abastecimento_api.domain.entity.Abastecimento;

@Repository
public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long> {

    public List<Abastecimento> findByPlaca(String placa);

}
