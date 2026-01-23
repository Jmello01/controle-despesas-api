package com.joao.controle_despesas.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Nome do esquema de segurança
        final String securitySchemeName = "bearer-jwt";

        return new OpenAPI()
                .info(new Info()
                        .title("💰 API de Controle de Despesas")
                        .version("2.0.0")
                        .description("""
                                API REST para gerenciamento de despesas pessoais desenvolvida com Java e Spring Boot.
                                
                                ### 🔐 Autenticação
                                Esta API utiliza JWT (JSON Web Token) para autenticação.
                                
                                **Como usar:**
                                1. Cadastre-se em `/api/auth/register` ou faça login em `/api/auth/login`
                                2. Copie o **token** recebido na resposta
                                3. Clique no botão **"Authorize"** 🔒 no topo desta página
                                4. Cole o token no campo (formato: `Bearer SEU_TOKEN_AQUI`)
                                5. Clique em **"Authorize"** e depois **"Close"**
                                6. Agora os cadeados dos endpoints protegidos ficarão abertos! 🔓
                                
                                ### Funcionalidades
                                - 🔐 Sistema de autenticação com JWT
                                - 👤 Cadastro e login de usuários
                                - 💸 Cadastro de despesas com validações
                                - 📋 Listagem de despesas (apenas do usuário autenticado)
                                - 💰 Cálculo automático de totais
                                - 🗑️ Exclusão de despesas
                                
                                ### Tecnologias
                                - Java 21
                                - Spring Boot 4
                                - Spring Security + JWT
                                - Spring Data JPA
                                - PostgreSQL (Produção)
                                - H2 Database (Desenvolvimento)
                                - Bean Validation
                                """)
                        .contact(new Contact()
                                .name("João Ricardo")
                                .email("seu.email@exemplo.com")
                                .url("https://github.com/Jmello01"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("https://controle-despesas-api-production.up.railway.app")
                                .description("Servidor de Produção (Railway)"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento")
                ))
                // ADICIONA O ESQUEMA DE SEGURANÇA
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("Insira o token JWT no formato: Bearer {token}")
                        )
                )
                // APLICA A SEGURANÇA GLOBALMENTE PARA TODOS OS ENDPOINTS PROTEGIDOS
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}