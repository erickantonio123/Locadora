# 🎬 Locadora de Filmes - CRUD com Spring Boot

Sistema completo de gerenciamento de uma locadora de filmes, com operações de cadastro, consulta, atualização e remoção de registros (CRUD), utilizando **Java com Spring Boot**.

---

## 📋 Descrição do Sistema

Este sistema permite o gerenciamento eficiente de uma locadora de filmes, incluindo funcionalidades para cadastrar, listar, editar e remover filmes do catálogo. Possui autenticação e autorização baseadas em roles (ADMIN e USER) utilizando Spring Security com JWT. A interface web é construída com Thymeleaf, e a persistência é feita em banco de dados relacional (H2, MySQL ou PostgreSQL).

---

## ✅ Funcionalidades

- Cadastro de novos filmes
- Listagem de todos os filmes cadastrados
- Busca de filme por ID
- Edição das informações de um filme
- Exclusão de filme do catálogo
- Controle de acesso com roles (ADMIN, USER)
- Autenticação via JWT (JSON Web Token)
- Interface web responsiva com Thymeleaf
- Integração com banco de dados relacional (H2, MySQL, PostgreSQL)

---

## 🛠 Tecnologias Utilizadas

- Java 17
- Spring Boot
  - Spring Web
  - Spring Data JPA
  - Spring Security
- Thymeleaf
- Banco de dados: H2, MySQL ou PostgreSQL
- Maven
- Git/GitHub

---

## 🚀 Como Executar o Projeto

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/LocadoraCrud.git
cd LocadoraCrud

# Execute com Maven (Linux/Mac)
./mvnw spring-boot:run

# Ou no Windows (PowerShell)
mvnw.cmd spring-boot:run

# Acesse no navegador
http://localhost:8080
