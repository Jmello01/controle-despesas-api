package com.joao.controle_despesas.controller;

import com.joao.controle_despesas.model.Despesa;
import com.joao.controle_despesas.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/despesas")
@RequiredArgsConstructor
@Tag(name = "Gestão de Despesas", description = "Endpoints para gerenciar despesas pessoais")
@SecurityRequirement(name = "bearer-jwt")
public class DespesaController {

    private final DespesaService despesaService;


    @PostMapping
    @Operation(summary = "Cadastrar despesa", description = "Cadastra uma nova despesa para o usuário autenticado")
    public ResponseEntity<Despesa> cadastrar(
            @Valid @RequestBody Despesa despesa,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        Despesa despesaSalva = despesaService.salvar(despesa, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaSalva);
    }

    @GetMapping
    @Operation(summary = "Listar despesas", description = "Lista todas as despesas do usuário autenticado")
    public ResponseEntity<List<Despesa>> listarTodas(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        List<Despesa> despesas = despesaService.listarTodas(email);
        return ResponseEntity.ok(despesas);
    }


    @GetMapping("/total")
    @Operation(summary = "Calcular total", description = "Calcula o total de despesas do usuário autenticado")
    public ResponseEntity<BigDecimal> calcularTotal(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        BigDecimal total = despesaService.calcularTotal(email);
        return ResponseEntity.ok(total);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar despesa", description = "Deleta uma despesa do usuário autenticado")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        despesaService.deletar(id, email);
        return ResponseEntity.noContent().build();
    }
}