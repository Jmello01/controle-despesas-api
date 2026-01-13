# API de Controle de Despesas

API REST desenvolvida para gerenciamento de despesas pessoais. O projeto permite o cadastro, consulta, exclusão e cálculo total de despesas mensais, aplicando boas práticas de desenvolvimento backend.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database** (Banco em memória)
- **Lombok** (Boilerplate code reduction)
- **Bean Validation** (Validação de dados)
- **Maven** (Gerenciamento de dependências)

## ⚙ Funcionalidades

- **Cadastrar Despesa:** Criação de novos registros com validação de dados.
- **Listar Despesas:** Retorna todas as despesas cadastradas.
- **Calcular Total:** Endpoint inteligente que soma todas as despesas do mês atual.
- **Deletar Despesa:** Remoção de registros por ID.
- **Validações:**
    - Descrição obrigatória.
    - Valor deve ser positivo.
    - Data não pode ser futura.
    - Categoria obrigatória.

## 🛠 Como Rodar o Projeto

1. Clone o repositório:
```bash
git clone [https://github.com/Jmello01/controle-despesas-api.git](https://github.com/SEU-USUARIO/controle-despesas-api.git)