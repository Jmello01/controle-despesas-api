package com.joao.controle_despesas.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // PASSO 1: EXTRAIR TOKEN DO HEADER
        // Header esperado: "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        String token = extractTokenFromRequest(request);

        // PASSO 2: VERIFICAR SE TOKEN EXISTE
        if (token == null) {
            // Sem token, deixa continuar
            // Spring Security vai bloquear rotas privadas depois
            filterChain.doFilter(request, response);
            return;
        }

        // PASSO 3: VALIDAR TOKEN
        if (!jwtTokenProvider.validateToken(token)) {
            // Token inválido ou expirado
            // Deixa continuar sem autenticar
            filterChain.doFilter(request, response);
            return;
        }

        // PASSO 4: EXTRAIR EMAIL DO TOKEN
        String email = jwtTokenProvider.getEmailFromToken(token);

        // PASSO 5: VERIFICAR SE JÁ ESTÁ AUTENTICADO
        // Se já tem autenticação no contexto, não precisa fazer de novo
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // PASSO 6: CARREGAR USUÁRIO DO BANCO
            // Usa o UserDetailsService que criamos
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // PASSO 7: CRIAR OBJETO DE AUTENTICAÇÃO
            // Este objeto representa um usuário autenticado
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,              // Principal (usuário)
                            null,                     // Credentials (senha - não precisa)
                            userDetails.getAuthorities() // Authorities (ROLE_USER, ROLE_ADMIN)
                    );

            // PASSO 8: ADICIONAR DETALHES DA REQUISIÇÃO
            // IP, session ID, etc (para auditoria)
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // PASSO 9: SETAR AUTENTICAÇÃO NO CONTEXTO DO SPRING SECURITY
            // A partir daqui, Spring Security sabe quem é o usuário
            // Controllers podem acessar via @AuthenticationPrincipal
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // PASSO 10: CONTINUAR A CADEIA DE FILTROS
        // Passa para o próximo filtro ou Controller
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        // Pega o header "Authorization"
        String bearerToken = request.getHeader("Authorization");

        // Verifica se existe e começa com "Bearer "
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Remove "Bearer " e retorna só o token
            // "Bearer abc123" → "abc123"
            return bearerToken.substring(7);
        }

        // Sem token válido
        return null;
    }
}