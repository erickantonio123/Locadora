# 🎬 Sistema de Locadora

Sistema completo de gerenciamento para locadora de filmes desenvolvido em Java Spring Boot.

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Maven**
- **H2 Database** (desenvolvimento)
- **MySQL** (produção)

## 📁 Estrutura do Projeto
Locadora/
├── src/
│ └── main/
│ └── java/
│ └── com/
│ └── locadora/
│ ├── controller/ # Controladores REST
│ ├── model/ # Entidades JPA
│ ├── repository/ # Interfaces de dados
│ ├── service/ # Lógica de negócio
│ └── config/ # Configurações
├── src/main/resources/
│ ├── application.properties
│ └── data.sql # Dados iniciais
└── pom.xml

text

## 🎯 Funcionalidades

### 📊 Gestão de Catálogo
- Cadastro de filmes
- Controle de gêneros
- Gestão de plataformas (Netflix, Amazon Prime, Disney+, etc.)
- Classificação etária

### 🔄 Operações de Locação
- Aluguel de filmes
- Devolução
- Histórico de locações
- Controle de disponibilidade por plataforma

### 👥 Gestão de Clientes
- Cadastro de clientes
- Histórico de locações
- Preferências por gênero e plataforma

## 🏗️ Modelo de Dados

### Principais Entidades:

- **Filme**: Informações dos filmes (título, diretor, ano, duração, plataforma)
- **Gênero**: Categorias de filmes (Ação, Comédia, Drama, etc.)
- **Plataforma**: Streaming disponível (Netflix, Amazon Prime, Disney+, HBO Max, etc.)
- **Cliente**: Dados dos clientes da locadora
- **Locação**: Registro de aluguéis e devoluções

## 🎥 Plataformas Suportadas

- **Netflix**
- **Amazon Prime Video**
- **Disney+**
- **HBO Max**
- **Apple TV+**
- **Paramount+**
- **Star+**
- **Globoplay**

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- MySQL (opcional para produção)

### Execução Local
```bash
# Clone o repositório
git clone https://github.com/erickantonio123/Locadora.git

# Entre no diretório
cd Locadora

# Execute a aplicação
mvn spring-boot:run
Configuração
properties
# application.properties
spring.datasource.url=jdbc:h2:mem:locadora
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
📚 API Endpoints
Filmes
GET /api/filmes - Lista todos os filmes

POST /api/filmes - Cadastra novo filme

GET /api/filmes/{id} - Busca filme por ID

PUT /api/filmes/{id} - Atualiza filme

GET /api/filmes/platform/{plataforma} - Busca filmes por plataforma

Clientes
GET /api/clientes - Lista clientes

POST /api/clientes - Cadastra cliente

Locações
POST /api/locacoes/alugar - Realiza locação

POST /api/locacoes/devolver - Registra devolução

GET /api/locacoes/cliente/{id} - Histórico de locações do cliente

Plataformas
GET /api/plataformas - Lista todas as plataformas

POST /api/plataformas - Cadastra nova plataforma

🗃️ Banco de Dados
Desenvolvimento
H2 Database (em memória)

Acesso console: http://localhost:8080/h2-console

Produção
MySQL configurável

Scripts DDL automáticos

🔧 Desenvolvimento
Compilar projeto
bash
mvn clean compile
Executar testes
bash
mvn test
Gerar pacote
bash
mvn clean package
👥 Desenvolvedor
Erick Antonio
https://img.shields.io/badge/GitHub-erickantonio123-blue

📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para detalhes.

⭐ Se este projeto foi útil, deixe uma estrela no repositório!

text

## 🎯 Melhorias adicionadas:

1. **🎥 Seção específica de "Plataformas Suportadas"**
2. **📋 Lista completa das principais streamings**
3. **🔍 Endpoint específico para buscar filmes por plataforma**
4. **📊 Gestão de plataformas na API**
5. **👥 Preferências por plataforma nos clientes**
