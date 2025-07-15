# 📚 Catálogo do Sábio - API REST

API desenvolvida para uma livraria fictícia com o objetivo de listar e consultar livros utilizando tecnologias modernas como Spring Boot, PostgreSQL, Redis e Docker.

---

## I. Arquitetura de Solução e Arquitetura Técnica

### Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.2.5**
    - Spring Web
    - Spring Data JPA
    - Spring Cache (Redis)
- **PostgreSQL** como banco de dados relacional
- **Redis** para cache de dados
- **Maven** como gerenciador de dependências
- **Docker e Docker Compose** para orquestração dos serviços
- **Lombok** para reduzir boilerplate
- **DataFaker** para geração de dados
- **JUnit 5 e Mockito** para testes unitários

### Arquitetura

A arquitetura foi desenvolvida com base em:

- **Controller**: Camada de entrada, REST API
- **Service**: Regras de negócio e lógica de cache
- **Repository**: Interface com o banco de dados via Spring Data JPA
- **Model**: Entidade
- **Exception**: Tratamento global de erros
- **Cache (Redis)**: Implementado com anotações `@Cacheable` para melhorar performance

---

## II. Plano de Implementação

### Funcionalidades

- `GET /books`: Lista todos os livros paginados, com parâmetros de ordenação.
- `GET /books/{id}`: Retorna um livro por id, retorna erro 404 caso não exista.
- `GET /books/genre/{genre}`: Busca livros por gênero contendo o texto informado, ignora maiúsculas/minúsculas.
- `GET /books/author/{author}`: Busca livros por autor contendo o texto informado.
- Dados são populados automaticamente com 50 livros gerados via DataFaker.

### Lógica de Negócio

- As buscas por gênero e autor utilizam métodos `findByGenreContainingIgnoreCase` e `findByAuthorContainingIgnoreCase` no `BookRepository`;
- Caso o resultado seja vazio, uma exceção `ResourceNotFoundException` é lançada e tratada por `GlobalExceptionHandler`, retornando mensagem clara e código HTTP 404;
- A listagem principal é paginada com parâmetros padrão: `page=0`, `size=10`, `sortBy=id`;

### Testes

- Testes unitários foram implementados para:
    - `BookController` com `MockMvc`
    - `BookService` com `Mockito`
    - Cobrem cenários de sucesso e falha (404 e 500)
- Execução dos testes via Maven:

```bash
./mvnw test
```

## Como rodar o projeto

### Tecnologias Utilizadas
- Java 21
- Maven
- Docker e Docker Compose

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/larissajuliavsa/catalogo-do-sabio.git
cd catalogo-do-sabio

# 2. Suba os containers do banco e do Redis
docker-compose up -d

# 3. Rode a aplicação
./mvnw spring-boot:run
```