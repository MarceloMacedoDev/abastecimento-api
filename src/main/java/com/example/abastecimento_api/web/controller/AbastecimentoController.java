package com.example.abastecimento_api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.abastecimento_api.usecase.GerenciarAbastecimentoUseCase;
import com.example.abastecimento_api.web.dto.AbastecimentoDTO;

@RestController
@RequestMapping("/abastecimentos")
public class AbastecimentoController {

    private final GerenciarAbastecimentoUseCase abastecimentoService;

    public AbastecimentoController(
            GerenciarAbastecimentoUseCase abastecimentoService) {
        this.abastecimentoService = abastecimentoService;
    }

    @GetMapping
    public ResponseEntity<List<AbastecimentoDTO>> listarAbastecimentos(@RequestParam(required = false) String placa) {

        return ResponseEntity.ok(abastecimentoService.listarAbastecimentos(placa));
    }

    @PostMapping
    public ResponseEntity<AbastecimentoDTO> criarAbastecimento(@RequestBody AbastecimentoDTO abastecimentoDTO) {

        final AbastecimentoDTO abastecimentoDTOCriado = abastecimentoService.adicionarAbastecimento(abastecimentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(abastecimentoDTOCriado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAbastecimento(@PathVariable Long id) {
        abastecimentoService.removerAbastecimento(id);
        return ResponseEntity.noContent().build();
    }
}