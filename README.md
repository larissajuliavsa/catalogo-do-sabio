## Como rodar o projeto

### Pré-requisitos
- Java 21
- Maven
- Docker e Docker Compose

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/catalogo-do-sabio.git
cd catalogo-do-sabio

# 2. Suba os containers do banco e do Redis
docker-compose up -d

# 3. Rode a aplicação
./mvnw spring-boot:run
