package com.joao.controle_despesas.service;

import com.joao.controle_despesas.model.Despesa;
import com.joao.controle_despesas.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository repository;

    // Método para cadastrar despesa
    public Despesa cadastrar(Despesa despesa) {
        return repository.save(despesa);
    }

    // Método para listar todas
    public List<Despesa> listarTodas() {
        return repository.findAll();
    }

    // Método para deletar despesa
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    // Método para calcular total do Mês atual
    public BigDecimal calcularTotalMesAtual() {
        List<Despesa> todasDespesas = repository.findAll();

        // Pegar Mês e ano atual
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();

        // Filtra despesas do mês atual e somar
        BigDecimal total = BigDecimal.ZERO;

        for (Despesa despesa : todasDespesas) {
            if (despesa.getData() != null) {
                if (despesa.getData().getMonthValue() == mesAtual &&
                        despesa.getData().getYear() == anoAtual) {
                    total = total.add(despesa.getValor());
                }
            }
        }

        return total;
    }
}
