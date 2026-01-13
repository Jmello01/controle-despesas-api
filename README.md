#  API de Controle de Despesas

API REST desenvolvida para gerenciamento de despesas pessoais. O projeto demonstra a construção de um backend robusto utilizando **Java 21** e **Spring Boot 3**, aplicando boas práticas como validação de dados, tratamento de exceções, arquitetura em camadas e documentação automática.

##  Tecnologias Utilizadas

- **Java 21** (LTS)
- **Spring Boot 3.4**
- **Spring Data JPA** (Persistência de dados)
- **H2 Database** (Banco de dados em memória)
- **Lombok** (Redução de código boilerplate)
- **Bean Validation** (Validação de regras de negócio)
- **SpringDoc OpenAPI / Swagger** (Documentação interativa)
- **Maven** (Gerenciamento de dependências)

##  Funcionalidades

- **Cadastrar Despesa:** Criação de registros com validações rigorosas.
- **Listar Despesas:** Consulta de todos os registros salvos.
- **Dashboard Financeiro:** Endpoint que calcula o **total gasto no mês atual**.
- **Remover Despesa:** Exclusão de registros por ID.
- **Documentação API:** Interface visual para testar os endpoints.

---

##  Documentação Interativa (Swagger)

O projeto possui documentação automática via Swagger UI.
Para acessar, rode a aplicação e abra o link abaixo no navegador:

 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

---

## 🛠️ Como Rodar o Projeto

### Pré-requisitos
- Java JDK 21 instalado
- Git instalado
- Maven (ou usar o wrapper incluso no projeto)

### Passo a Passo

1. Clone o repositório:
```bash
git clone [https://github.com/Jmello01/controle-despesas-api.git](https://github.com/Jmello01/controle-despesas-api.git)
```
2. Entre na pasta do projeto:
```bash
cd controle-despesas-api 
```
3. Execute a aplicação (Windows):
```bash
./mvnw.cmd spring-boot:run
```
(Ou abra o projeto no IntelliJ e clique no "Play" na classe ControleDespesasApplication).

4. A API estará disponível em http://localhost:8080.

## Endpoints Principais
**1) Cadastrar Despesa**

**POST** /api/despesas

JSON Exemplo: 
```bash 
{
  "descricao": "Assinatura Streaming",
  "valor": 55.90,
  "data": "2026-01-12",
  "categoria": "Lazer"
}
```
Obs: Não envie o campo id, ele é gerado automaticamente.

2) Listar Todas

   **GET** /api/despesas


3) Consultar Total do Mês

   **GET** /api/despesas/total


4) Deletar Despesa

   **DELETE** /api/despesas/{id}

## Regras de Validação
O sistema rejeitará requisições que não atendam aos critérios:

Descrição: Obrigatória (mínimo 3 caracteres).

Valor: Obrigatório e deve ser positivo.

Data: Obrigatória e não pode ser futura.

Categoria: Obrigatória.

## Autor
Desenvolvido por João Ricardo. Estudante de Ciência da Computação focado em desenvolvimento Backend com Java e Spring.