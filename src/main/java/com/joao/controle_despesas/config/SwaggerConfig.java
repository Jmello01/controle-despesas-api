package com.joao.controle_despesas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Controle de Despesas")
                        .version("1.0.0")
                        .description("""
                                API REST para gerenciamento de despesas pessoais desenvolvida com Java e Spring Boot.
                                
                                ### Funcionalidades
                                - Cadastro de despesas com validações
                                - Listagem completa de registros
                                - Cálculo automático de totais mensais
                                - Exclusão de despesas
                                
                                ### Tecnologias
                                - Java 21
                                - Spring Boot 3.4
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
                ));
    }
}