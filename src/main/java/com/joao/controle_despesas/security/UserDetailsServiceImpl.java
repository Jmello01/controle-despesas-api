package com.joao.controle_despesas.security;

import com.joao.controle_despesas.model.Usuario;
import com.joao.controle_despesas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. BUSCAR usuário no banco pelo email
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuário não encontrado com email: " + username
                ));

        // 2. CONVERTER Usuario (nossa entidade) para UserDetails (do Spring Security)
        return User.builder()
                .username(usuario.getEmail())           // Email como username
                .password(usuario.getSenha())           // Senha criptografada
                .authorities(getAuthorities(usuario))   // Permissões (ROLE_USER ou ROLE_ADMIN)
                .accountExpired(false)                  // Conta não expirada
                .accountLocked(false)                   // Conta não bloqueada
                .credentialsExpired(false)              // Credenciais não expiradas
                .disabled(false)                        // Conta não desabilitada
                .build();
    }
    private java.util.Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name())
        );
    }
}