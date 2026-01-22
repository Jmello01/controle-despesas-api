package com.joao.controle_despesas.repository;

import com.joao.controle_despesas.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByUsuarioId(Long usuarioId);
}
