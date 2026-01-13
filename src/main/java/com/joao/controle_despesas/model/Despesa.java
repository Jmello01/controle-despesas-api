package com.joao.controle_despesas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor //
@AllArgsConstructor //
@Entity
@Table(name = "despesas")
@Schema(description = "Representação de uma despesa financeira")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 3, max = 100)
    @Schema(description = "Descrição detalhada", example = "Conta de Luz")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01")
    @Schema(description = "Valor monetário", example = "150.00")
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    @PastOrPresent(message = "Data não pode ser futura")
    @Schema(description = "Data da despesa", example = "2026-01-12")
    private LocalDate data;

    @NotBlank(message = "Categoria é obrigatória")
    @Schema(description = "Categoria", example = "Moradia")
    private String categoria;
}