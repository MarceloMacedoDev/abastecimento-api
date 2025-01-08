package com.example.abastecimento_api.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.abastecimento_api.domain.entity.Abastecimento;
import com.example.abastecimento_api.web.dto.AbastecimentoDTO;

@Mapper(componentModel = "spring")
public interface AbastecimentoMapper {
    AbastecimentoDTO toDTO(Abastecimento abastecimento);

    Abastecimento toEntity(AbastecimentoDTO abastecimentoDTO);

    List<AbastecimentoDTO> toDTOs(List<Abastecimento> abastecimentos);

    List<Abastecimento> toEntities(List<AbastecimentoDTO> abastecimentoDTOs);

}
