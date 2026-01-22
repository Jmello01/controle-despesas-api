package com.joao.controle_despesas.service;

import com.joao.controle_despesas.model.Despesa;
import com.joao.controle_despesas.model.Usuario;
import com.joao.controle_despesas.repository.DespesaRepository;
import com.joao.controle_despesas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final UsuarioRepository usuarioRepository;

    public Despesa salvar(Despesa despesa, String emailUsuario) {
        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Vincula a despesa ao usuário
        despesa.setUsuario(usuario);

        // Salva no banco
        return despesaRepository.save(despesa);
    }

    public List<Despesa> listarTodas(String emailUsuario) {
        // Busca o usuário
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Busca apenas despesas deste usuário
        return despesaRepository.findByUsuarioId(usuario.getId());
    }

    public BigDecimal calcularTotal(String emailUsuario) {
        // Busca o usuário
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Busca despesas do usuário
        List<Despesa> despesas = despesaRepository.findByUsuarioId(usuario.getId());

        // Calcula total
        return despesas.stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void deletar(Long id, String emailUsuario) {
        // Busca o usuário
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Busca a despesa
        Despesa despesa = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        // VERIFICA SE A DESPESA PERTENCE AO USUÁRIO
        if (!despesa.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Você não tem permissão para deletar esta despesa");
        }

        // Deleta
        despesaRepository.deleteById(id);
    }
}