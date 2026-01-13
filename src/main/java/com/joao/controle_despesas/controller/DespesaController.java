package com.joao.controle_despesas.controller;

import com.joao.controle_despesas.model.Despesa;
import com.joao.controle_despesas.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/despesas")
@Tag(name = "Gestão de Despesas", description = "Endpoints para cadastro, listagem e controle financeiro")
public class DespesaController {

    @Autowired
    private DespesaService service;

    @Operation(summary = "Cadastrar nova despesa", description = "Registra uma despesa no banco de dados. O ID é gerado automaticamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Despesa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    @PostMapping
    public ResponseEntity<Despesa> cadastrar(@Valid @RequestBody Despesa despesa) {
        Despesa despesaSalva = service.cadastrar(despesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaSalva);
    }

    @Operation(summary = "Listar todas", description = "Retorna a lista completa de despesas cadastradas.")
    @GetMapping
    public ResponseEntity<List<Despesa>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(summary = "Total do Mês", description = "Calcula a soma de todas as despesas referentes ao mês atual.")
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> calcularTotal() {
        return ResponseEntity.ok(service.calcularTotalMesAtual());
    }

    @Operation(summary = "Excluir despesa", description = "Remove permanentemente uma despesa pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}