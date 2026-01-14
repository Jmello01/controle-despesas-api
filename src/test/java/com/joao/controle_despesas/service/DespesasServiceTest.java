package com.joao.controle_despesas.service;

import com.joao.controle_despesas.model.Despesa;
import com.joao.controle_despesas.repository.DespesaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static  org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class DespesasServiceTest {

    @Mock
    private DespesaRepository repository;

    @InjectMocks
    private DespesaService service;

    private Despesa despesaValida;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks((this));

        //Criar uma despesa válida para usar nos testes
        despesaValida = new Despesa();
        despesaValida.setId(1L);
        despesaValida.setDescricao("Conta de Luz");
        despesaValida.setValor(new BigDecimal("150.50"));
        despesaValida.setData(LocalDate.now());
        despesaValida.setCategoria("Moradia");
    }

    @Test
    @DisplayName("Deve cadastrar uma despesa com sucesso")
    void deveCadastrarDespesaComSucesso() {
        // Arrange (Preparar)
        when(repository.save(any(Despesa.class))).thenReturn(despesaValida);

        // Act (Agir)
        Despesa resultado = service.cadastrar(despesaValida);

        // Assert (Verificar)
        assertNotNull(resultado);
        assertEquals("Conta de Luz", resultado.getDescricao());
        assertEquals(new BigDecimal("150.50"), resultado.getValor());
        verify(repository, times(1)).save(any(Despesa.class));
    }

    @Test
    @DisplayName("Deve listar todas as despesas")
    void deveListarTodasDespesas() {
        // Arrange
        when(repository.findAll()).thenReturn(java.util.List.of(despesaValida));

        // Act
        var resultado = service.listarTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve deletar uma despesa")
    void deveDeletarDespesa() {
        // Arrange
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        // Act
        service.deletar(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
    }
}

