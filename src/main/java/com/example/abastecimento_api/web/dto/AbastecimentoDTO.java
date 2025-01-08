package com.example.abastecimento_api.web.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Builder
public class AbastecimentoDTO {
    private Long id;
    private String placa;
    private double quilometragem;
    private LocalDateTime dataHora;
    private double valorTotal;

    public AbastecimentoDTO() {
    }

    public AbastecimentoDTO(Long id, String placa, double quilometragem, LocalDateTime dataHora, double valorTotal) {
        this.id = id;
        this.placa = placa;
        this.quilometragem = quilometragem;
        this.dataHora = dataHora;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

}
