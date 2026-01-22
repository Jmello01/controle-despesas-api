package com.joao.controle_despesas.service;

import com.joao.controle_despesas.dto.auth.AuthResponse;
import com.joao.controle_despesas.dto.auth.LoginRequest;
import com.joao.controle_despesas.dto.auth.RegisterRequest;
import com.joao.controle_despesas.model.Usuario;
import com.joao.controle_despesas.repository.UsuarioRepository;
import com.joao.controle_despesas.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        // 1. VALIDAR: Email já existe?
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }

        // 2. CRIAR novo usuário
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());

        // 3. CRIPTOGRAFAR senha (NUNCA salva em texto puro!)
        // Entrada: "123456"
        // Saída: "$2a$10$abcd1234..." (hash BCrypt)
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));

        // 4. DEFINIR role padrão
        usuario.setRole(Usuario.Role.USER);

        // 5. SALVAR no banco
        usuario = usuarioRepository.save(usuario);

        // 6. GERAR token JWT
        String token = jwtTokenProvider.generateToken(usuario.getEmail());

        // 7. RETORNAR resposta
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .role(usuario.getRole().name())
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        // 1. AUTENTICAR (validar email + senha)
        // Spring Security faz automaticamente:
        // - Chama UserDetailsService.loadUserByUsername(email)
        // - Compara senha digitada com senha do banco (BCrypt)
        // - Se OK: retorna Authentication
        // - Se ERRO: lança BadCredentialsException
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        // 2. BUSCAR usuário completo do banco
        // Se chegou aqui, autenticação foi OK!
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 3. GERAR token JWT
        String token = jwtTokenProvider.generateToken(usuario.getEmail());

        // 4. RETORNAR resposta
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .role(usuario.getRole().name())
                .build();
    }
}