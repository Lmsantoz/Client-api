# 🏦 Client API

API RESTful para cadastro e gerenciamento de clientes, construída com **Spring Boot 4**, **PostgreSQL** e **Flyway** — focada em CRUD completo com validação de dados, paginação e documentação automática via Swagger.

>  **Projeto em desenvolvimento ativo** — novas funcionalidades estão sendo adicionadas progressivamente.

---

## 📌 Sobre o projeto

Uma API REST que gerencia operações de cadastro de clientes:

- **Criar cliente** com nome, e-mail, CPF e data de nascimento (ID gerado automaticamente via UUID)
- **Listar clientes** com paginação e ordenação configuráveis
- **Buscar cliente** por ID (UUID)
- **Atualizar cliente** com validação de todos os campos
- **Deletar cliente** por ID
- **Validação automática** de entrada com respostas de erro padronizadas
- **Documentação interativa** via Swagger UI

---

## 🛠️ Tecnologias e conceitos utilizados

| Tecnologia / Conceito | Onde foi aplicado |
|---|---|
| **Java 21** | Linguagem principal do projeto |
| **Spring Boot 4.0.6** | Framework web, injeção de dependência e configuração automática |
| **Spring Data JPA** | Abstração de persistência — repositórios sem SQL manual |
| **Spring Validation** | Validação de entrada via Bean Validation (Jakarta) nos endpoints |
| **PostgreSQL 15.7** | Banco de dados relacional (rodando via Docker) |
| **Flyway** | Versionamento e migração do schema do banco de dados |
| **Lombok** | Redução de boilerplate — `@Data`, `@RequiredArgsConstructor` |
| **SpringDoc OpenAPI 3.0.1** | Geração automática de documentação Swagger UI |
| **Docker Compose** | Orquestração do container PostgreSQL |
| **Maven** | Gerenciamento de build e dependências |
| **Arquitetura em camadas** | Controller → Service → Repository (separação de responsabilidades) |
| **UUID como PK** | Identificadores únicos sem dependência de sequência do banco |
| **Tratamento global de exceções** | `@RestControllerAdvice` para respostas de erro consistentes |

---

## 🏗️ Arquitetura

O projeto segue a arquitetura em camadas padrão do Spring:

```
Cliente HTTP  →  Controller  →  Service  →  Repository  →  PostgreSQL
                    ↓                          ↓
              Validação                  Spring Data JPA
              (Jakarta)                  (Hibernate)
```

| Camada | Responsabilidade |
|---|---|
| **Controller** | Recebe requisições HTTP, valida input com `@Valid`, delega para o service |
| **Service** | Regras de negócio, orquestração de operações, lançamento de exceções |
| **Repository** | Acesso ao banco via JPA — queries derivadas do nome do método |
| **Entity** | Mapeamento objeto-relacional com validações de campo |
| **Exception Handler** | Intercepta erros de validação e retorna JSON padronizado |

---

## 📁 Estrutura do projeto

```
clientapi/
├── src/
│   ├── main/
│   │   ├── java/com/lucasmarques/clientapi/
│   │   │   ├── ClientapiApplication.java          # Ponto de entrada da aplicação
│   │   │   ├── config/
│   │   │   │   └── SwaggerApiConfig.java          # Configuração do Swagger/OpenAPI
│   │   │   ├── controller/
│   │   │   │   └── ClientController.java          # Endpoints REST (CRUD)
│   │   │   ├── entity/
│   │   │   │   └── Client.java                    # Entidade JPA + validações
│   │   │   ├── exceptions/
│   │   │   │   └── GlobalExceptionHandler.java    # Handler global de erros
│   │   │   ├── repository/
│   │   │   │   └── ClientRepository.java          # Interface JPA Repository
│   │   │   └── service/
│   │   │       └── ClientService.java             # Lógica de negócio
│   │   └── resources/
│   │       ├── application.properties             # Config principal
│   │       ├── application-local.properties       # Config local (credenciais)
│   │       └── db/migrations/
│   │           └── V1__create_table-clients.sql   # Migration: criação da tabela
│   └── test/
│       └── java/com/lucasmarques/clientapi/
│           └── ClientapiApplicationTests.java     # Teste de contexto
├── docker-compose.yml                             # Container PostgreSQL
├── pom.xml                                        # Configuração Maven
├── .env                                           # Variáveis de ambiente (Docker)
├── .gitignore
├── LICENSE                                        # MIT License
└── README.md
```

---

## 🗄️ Modelo de dados

### Tabela `clients`

| Coluna | Tipo | Restrições | Descrição |
|---|---|---|---|
| `id` | `UUID` | PK, auto-gerado | Identificador único do cliente |
| `name` | `VARCHAR(100)` | NOT NULL | Nome completo |
| `email` | `VARCHAR(100)` | NOT NULL, UNIQUE | E-mail válido |
| `cpf` | `VARCHAR(11)` | NOT NULL, UNIQUE | CPF (apenas números) |
| `birth_date` | `DATE` | NOT NULL | Data de nascimento |

### Validações aplicadas (Bean Validation)

| Campo | Regras |
|---|---|
| `name` | `@NotBlank` — obrigatório, não pode ser vazio |
| `email` | `@Email` + `@NotBlank` — obrigatório e formato de e-mail válido |
| `cpf` | `@NotBlank` — obrigatório, não pode ser vazio |
| `birthDate` | `@NotNull` — obrigatório |

---

## 🔌 Endpoints da API

Base URL: `http://localhost:8080/api/clients`

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| `GET` | `/api/clients?page=0&size=10&sort=id` | Lista todos os clientes (paginado) | `200 OK` |
| `GET` | `/api/clients/{id}` | Busca cliente por ID | `200 OK` / `404 Not Found` |
| `POST` | `/api/clients` | Cria novo cliente | `201 Created` / `400 Bad Request` |
| `PUT` | `/api/clients/{id}` | Atualiza cliente existente | `200 OK` / `404 Not Found` |
| `DELETE` | `/api/clients/{id}` | Remove cliente | `204 No Content` / `404 Not Found` |

### Exemplo — Criar cliente

**Request:**
```http
POST /api/clients
Content-Type: application/json
```
```json
{
  "name": "Maria Oliveira",
  "email": "maria@email.com",
  "cpf": "98765432100",
  "birthDate": "1985-11-20"
}
```

**Response:** `201 Created`
```json
{
  "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
  "name": "Maria Oliveira",
  "email": "maria@email.com",
  "cpf": "98765432100",
  "birthDate": "1985-11-20"
}
```

### Exemplo — Erro de validação

**Response:** `400 Bad Request`
```json
{
  "name": "must not be blank",
  "email": "must be a well-formed email address"
}
```

---

## 🚀 Como rodar

### Pré-requisitos

- **Java 21** (ou superior) instalado → [Download](https://adoptium.net/)
- **Maven** instalado → ou use o wrapper `./mvnw` incluso no projeto
- **Docker** e **Docker Compose** → [Download](https://docs.docker.com/get-docker/)

Verifique se estão instalados:

```bash
java --version
mvn --version
docker --version
```

### Passo a passo

1. **Clone o repositório**

```bash
git clone https://github.com/Lmsantoz/Client-api.git
cd Client-api
```

2. **Configure as variáveis de ambiente**

Crie um arquivo `.env` na raiz do projeto:

```env
POSTGRES_USER=seu_user
POSTGRES_PASSWORD=sua_senha_aqui
POSTGRES_DB=seu_banco
```

3. **Suba o banco de dados com Docker**

```bash
docker compose up -d
```

> Isso inicia um container PostgreSQL 15.7 na porta `5432`.

4. **Configure o perfil local**

Crie o arquivo `src/main/resources/application-local.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu-banco
spring.datasource.username=seu_user
spring.datasource.password=sua_senha_aqui
```

> ⚠️ Este arquivo está no `.gitignore` e **não deve ser commitado** — contém credenciais do banco.

5. **Execute a aplicação**

```bash
# Com o Maven wrapper
./mvnw spring-boot:run

# Ou com Maven global
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

6. **Execute os testes**

```bash
./mvnw test
```

---

## 📖 Documentação interativa (Swagger)

Com a aplicação rodando, acesse:

| Recurso | URL |
|---|---|
| **Swagger UI** | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| **OpenAPI JSON** | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) |

A documentação é gerada automaticamente pelo **SpringDoc OpenAPI** a partir das annotations do controller.

---

## 🔄 Migrations (Flyway)

O schema do banco é gerenciado pelo Flyway. O Hibernate opera em modo `validate` — ele **verifica** se o schema bate com as entidades, mas **nunca altera** o banco automaticamente. Todas as mudanças de schema passam pelo Flyway.

### Migrations existentes

| Versão | Arquivo | O que faz |
|---|---|---|
| V1 | `V1__create_table-clients.sql` | Cria a tabela `clients` com `id`, `name`, `email`, `cpf` e `birth_date` |

### Como criar novas migrations

Crie um arquivo SQL em `src/main/resources/db/migrations/` seguindo o padrão:

```
V{numero}__descricao.sql
```

Exemplo: `V2__add_phone_column.sql`

Ao reiniciar a aplicação, o Flyway aplica automaticamente.

---

## 🚨 Tratamento de erros

| Situação | Status HTTP | Formato da resposta |
|---|---|---|
| Campo inválido ou ausente | `400 Bad Request` | `{ "campo": "mensagem de erro" }` |
| Cliente não encontrado | `404 Not Found` | `{ "status": 404, "message": "Client not found" }` |

O `GlobalExceptionHandler` usa `@RestControllerAdvice` para interceptar `MethodArgumentNotValidException` e retornar um mapa campo → mensagem de erro.

---

## 🗺️ Roadmap

- [ ] DTOs de request/response (separar entidade da API)
- [ ] Validação customizada de CPF (dígito verificador)
- [ ] Busca por nome e e-mail via query params
- [x] Testes unitários do service
- [ ] Testes de integração (controller, repository)
- [ ] Spring Security com autenticação JWT
- [ ] Dockerfile para containerizar a aplicação completa
- [ ] CI/CD com GitHub Actions
- [ ] Cache com Redis
- [ ] Logs estruturados

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👤 Autor

**Lucas Marques**
- ✉️ lucaamarques2406@gmail.com
- 🐙 GitHub: [@Lmsantoz](https://github.com/Lmsantoz)
