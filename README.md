🎬 Sistema de Locadora - Spring Boot & Java

https://img.shields.io/badge/Java-17+-red?logo=java

Sistema completo de gerenciamento para locadora de filmes desenvolvido em Java Spring Boot com arquitetura moderna, segurança robusta e interface responsiva.

🚀 Tecnologias Utilizadas
Backend

Java 17+ – Linguagem principal

Spring Boot 3.x – Framework principal

Spring Security – Autenticação e autorização

Spring Data JPA – Persistência de dados

JWT – Tokens de autenticação

Hibernate – ORM

Maven – Gerenciamento de dependências

Frontend

Thymeleaf – Template engine

Bootstrap 4 – Interface responsiva

HTML5/CSS3 – Estrutura e estilização

JavaScript – Interatividade

Banco de Dados

Oracle XE – Banco de dados principal

JPA/Hibernate – Mapeamento objeto-relacional

Segurança

Spring Security com JWT

BCrypt para hash de senhas

Role-based Authorization (ADMIN, USER)

CSRF Protection

📁 Arquitetura do Projeto
Locadora/
├── src/main/java/com/LocadoraFilmes/
│   ├── controller/          # Controladores MVC e REST
│   ├── model/               # Entidades JPA
│   ├── repository/          # Interfaces Spring Data JPA
│   ├── service/             # Lógica de negócio
│   └── config/              # Configurações do Spring Security, JWT e handlers
├── src/main/resources/
│   ├── templates/           # Páginas Thymeleaf
│   ├── static/css/          # Recursos estáticos
│   └── application.properties
└── pom.xml

🎯 Funcionalidades Principais
🎥 Gestão de Catálogo

Cadastro completo de filmes

Controle de gêneros (Ação, Comédia, Drama, Terror, Romance, Suspense, Ficção)

Gestão de plataformas (Netflix, Amazon Prime, Disney+, HBO Max etc.)

Validação de dados com Bean Validation

Busca avançada com paginação

🔐 Sistema de Autenticação

Login seguro com JWT

Autorização baseada em roles (ROLE_ADMIN, ROLE_USER)

Senhas criptografadas com BCrypt

Redirecionamento inteligente pós-login

Proteção CSRF

👥 Gestão de Usuários

Cadastro de usuários com roles

Admin: Acesso completo (CRUD)

User: Apenas consulta e busca

Sessões seguras com JWT

🔄 Operações CRUD Completas

Create, Read, Update, Delete de filmes com validação e segurança

🌐 API RESTful

Endpoints REST para integração

JSON responses padronizadas

Tratamento global de exceções

Validação de payloads

🏗️ Modelo de Dados

Locadora (Filme)

Long id;
String nome;
Genero genero;
Plataforma plataforma;


Genero

Long id;
String nome;
List<Locadora> filmes;


Plataforma

Long id;
String nome;
List<Locadora> filmes;


Usuario

Long id;
String username;
String senha;
Set<Role> roles;


Role

Long id;
String nome; // ROLE_ADMIN, ROLE_USER

🎥 Plataformas Suportadas

Netflix

Amazon Prime Video

Disney+

HBO Max

Star+

Apple TV+

Paramount+

Globoplay

🔧 Configuração e Execução
Pré-requisitos

Java 17 ou superior

Maven 3.6+

Oracle Database XE

Configuração do Banco
# application.properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username=locadora
spring.datasource.password=senha123
spring.jpa.hibernate.ddl-auto=update

Execução
# Clone o repositório
git clone https://github.com/erickantonio123/Locadora.git

# Entre no diretório
cd Locadora

# Execute a aplicação
mvn spring-boot:run

Acesso

Aplicação: http://localhost:8080

Login Admin: admin / 123456

Login User: cliente / 123456

📚 Endpoints da API
Autenticação

POST /Top/login – Login com JWT

POST /Top/register – Registrar novo usuário

Filmes (REST API)

GET /api/filmes – Listar todos os filmes

POST /api/filmes – Adicionar novo filme

PUT /api/filmes/{id} – Atualizar filme

DELETE /api/filmes/{id} – Excluir filme

GET /api/filmes/buscar?nome={nome} – Buscar por nome

Interface Web

GET / – Página principal (Admin)

GET /buscarfilmes – Página de busca (User)

GET /login – Página de login

🛡️ Características de Segurança

Autenticação JWT

Controle de Acesso baseado em roles

Redirecionamento Inteligente após login

Proteção CSRF

BCrypt para senhas

🎨 Interface do Usuário
Página de Login

Estilo Netflix com overlay escuro

Background cinematográfico

Validação em tempo real

Dashboard Administrativo

Tabela responsiva de filmes

Formulários CRUD com validação

Navegação intuitiva

Página de Busca

Cartões responsivos

Busca em tempo real via AJAX

Filtros por gênero e plataforma

🔥 Destaques Técnicos

Arquitetura Limpa – MVC, DI, separação de concerns

Tratamento de Erros Global – @RestControllerAdvice

Validação Robusta – @NotBlank, @Size

Paginação e Performance – Pageable + Sort

Segurança Avançada – JWT, Role-based, CSRF, BCrypt

🚀 Como Contribuir

Fork o repositório

Crie uma branch: git checkout -b feature/nova-funcionalidade

Commit suas mudanças: git commit -m 'Adiciona nova funcionalidade'

Push para a branch: git push origin feature/nova-funcionalidade

Abra um Pull Request

👨‍💻 Desenvolvedor

Erick Antonio




📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para detalhes.

⭐ Habilidades Demonstradas

Backend: Spring Boot 3.x, Spring Security, JPA/Hibernate, REST APIs, JWT, Oracle Database

Frontend: Thymeleaf, Bootstrap, JavaScript, HTML5/CSS3, Responsive Design

Arquitetura: MVC Pattern, Dependency Injection, Exception Handling, Data Validation, Security Implementation

DevOps & Tools: Maven, Git, Oracle XE, Bean Validation, Pagination, Filter Chains

Disponível para oportunidades como Desenvolvedor Java Spring Boot! 🚀
