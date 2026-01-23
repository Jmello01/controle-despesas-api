package com.joao.controle_despesas.config;

import com.joao.controle_despesas.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // DESABILITAR CSRF
                // APIs REST não precisam de CSRF porque usam tokens
                // CSRF é para proteger formulários HTML com cookies
                .csrf(csrf -> csrf.disable())

                // CONFIGURAR AUTORIZAÇÃO DE ROTAS
                .authorizeHttpRequests(auth -> auth
                        // ROTAS PÚBLICAS (qualquer um pode acessar)
                        .requestMatchers("/api/auth/**").permitAll()          // Login e cadastro
                        .requestMatchers("/swagger-ui/**").permitAll()        // Swagger UI
                        .requestMatchers("/v3/api-docs/**").permitAll()       // API Docs
                        .requestMatchers("/swagger-ui.html").permitAll()      // Swagger página
                        .requestMatchers("/api-docs/**").permitAll()          // API Docs alternativo
                        .requestMatchers("/").permitAll()                     // Página inicial

                        // ROTAS PRIVADAS (precisa estar autenticado)
                        .requestMatchers("/api/despesas/**").authenticated()  // Todas as despesas

                        // QUALQUER OUTRA ROTA precisa de autenticação
                        .anyRequest().authenticated()
                )

                // CONFIGURAR SESSÃO STATELESS
                // Não cria sessão no servidor
                // Cada requisição é independente
                // Autenticação via JWT token apenas
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ADICIONAR O FILTRO JWT
                // Antes de validar username/password, valida o token JWT
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}