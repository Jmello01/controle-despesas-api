package com.joao.controle_despesas.controller;

import com.joao.controle_despesas.model.Despesa;
import com.joao.controle_despesas.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Gestão de Despesas", description = "Endpoints para gerenciar despesas pessoais")
public class DespesaController {

    @Autowired
    private DespesaService service;

    @Operation(
            summary = "Cadastrar nova despesa",
            description = "Cria uma nova despesa no sistema com validações de dados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Despesa cadastrada com sucesso",
                    content = @Content(schema = @Schema(implementation = Despesa.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos - Verifique as validações"
            )
    })
    @PostMapping
    public ResponseEntity<Despesa> cadastrar(
            @Parameter(description = "Dados da despesa a ser cadastrada", required = true)
            @Valid @RequestBody Despesa despesa) {
        Despesa despesaSalva = service.cadastrar(despesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaSalva);
    }

    @Operation(
            summary = "Listar todas as despesas",
            description = "Retorna lista completa de todas as despesas cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso"
    )
    @GetMapping
    public ResponseEntity<List<Despesa>> listarTodas() {
        List<Despesa> despesas = service.listarTodas();
        return ResponseEntity.ok(despesas);
    }

    @Operation(
            summary = "Calcular total do mês atual",
            description = "Retorna a soma de todas as despesas do mês vigente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Total calculado com sucesso"
    )
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> calcularTotal() {
        BigDecimal total = service.calcularTotalMesAtual();
        return ResponseEntity.ok(total);
    }

    @Operation(
            summary = "Deletar despesa",
            description = "Remove uma despesa específica do sistema pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Despesa deletada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Despesa não encontrada"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da despesa a ser deletada", required = true, example = "1")
            @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}