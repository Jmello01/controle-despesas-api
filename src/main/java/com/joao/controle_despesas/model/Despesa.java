package com.joao.controle_despesas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "despesas")
@Schema(description = "Modelo de dados para uma despesa financeira")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da despesa", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 3, max = 100, message = "Descrição deve ter entre 3 e 100 caracteres")
    @Schema(description = "Descrição detalhada da despesa", example = "Conta de Luz")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    @Schema(description = "Valor monetário da despesa", example = "150.50")
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    @PastOrPresent(message = "Data não pode ser futura")
    @Schema(description = "Data em que a despesa ocorreu", example = "2026-01-14")
    private LocalDate data;

    @NotBlank(message = "Categoria é obrigatória")
    @Schema(description = "Categoria da despesa", example = "Moradia",
            allowableValues = {"Moradia", "Alimentação", "Transporte", "Saúde", "Lazer", "Educação", "Outros"})
    private String categoria;
}