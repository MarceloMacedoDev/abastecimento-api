package com.example.abastecimento_api.infrastructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.abastecimento_api.domain.entity.Abastecimento;

@Repository
public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long> {

    public Page<Abastecimento> findByPlaca(Pageable pageable, String placa);

}
