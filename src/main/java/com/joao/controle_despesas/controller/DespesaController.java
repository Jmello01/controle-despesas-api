package com.joao.controle_despesas.controller;

import com.joao.controle_despesas.model.Despesa;
import com.joao.controle_despesas.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    @Autowired
    private DespesaService service;

    @PostMapping
    public ResponseEntity<Despesa> cadastrar(@Valid @RequestBody Despesa despesa) {
        Despesa despesaSalva = service.cadastrar(despesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaSalva);
    }

    @GetMapping
    public ResponseEntity<List<Despesa>> listarTodas() {
        List<Despesa> despesas = service.listarTodas();
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> calcularTotal() {
        BigDecimal total = service.calcularTotalMesAtual();
        return ResponseEntity.ok(total);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}