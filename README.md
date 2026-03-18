# 📦 Sistema de Gerenciamento de Estoque

API REST desenvolvida em **Java 17** com **Spring Boot** para gerenciamento de produtos e categorias, aplicando arquitetura em camadas, boas práticas de desenvolvimento backend, testes automatizados e containerização com Docker.

Este projeto foi desenvolvido como parte do meu processo de evolução como desenvolvedor backend, consolidando conhecimentos em APIs REST, persistência de dados, testes de software e ambiente containerizado.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL 8
- Docker
- Docker Compose
- Swagger (OpenAPI)
- JUnit 5
- Mockito

---

## 🏗️ Arquitetura

O projeto segue o padrão de **arquitetura em camadas**, promovendo separação de responsabilidades:

- **Controller** → exposição dos endpoints REST
- **Service** → centralização das regras de negócio
- **Repository** → persistência de dados com Spring Data JPA
- **DTO** → separação entre modelo de domínio e camada de comunicação externa
- **Exception** → tratamento global de erros com padrão de resposta estruturado

---

## 📂 Estrutura do Projeto

```
src/
 ├── controller
 ├── dto
 ├── entity
 ├── exception
 ├── repository
 └── service
```

---

## 🧪 Testes Automatizados

Foram implementados **testes unitários para a camada de Service**, garantindo o correto funcionamento das regras de negócio.

Os testes foram desenvolvidos utilizando:

- **JUnit 5**
- **Mockito**

Os testes simulam o comportamento dos repositórios, permitindo validar a lógica do serviço de forma isolada.

### Cenários testados no `ProductService`

- Criação de produto
- Listagem de produtos
- Busca de produto por ID
- Busca de produtos por categoria
- Atualização de produto
- Remoção de produto

Durante os testes são verificados:

- conversão correta entre **Entity → DTO**
- interação correta com os **Repositories**
- comportamento esperado das regras de negócio

Exemplo de verificação realizada nos testes:

```java
verify(productRepository).save(any(Product.class));
```

Isso garante que o serviço realmente executou a operação esperada.

---

## 🐳 Executando com Docker

### 📌 Pré-requisitos

- Docker instalado

### 🔥 Subindo a aplicação

```bash
docker compose up --build
```

Após subir os containers:

**API:**  
http://localhost:8080

**Swagger (Documentação da API):**  
http://localhost:8080/swagger-ui/index.html

---

## 🗄️ Banco de Dados

- MySQL 8
- Banco: `estoque`
- Executado via container Docker
- Persistência utilizando volume Docker
- Integração via Spring Data JPA

---

## 📌 Funcionalidades

- Cadastro de produtos e categorias
- Listagem de produtos e categorias
- Atualização de produtos e categorias
- Remoção de produtos e categorias
- Persistência em banco de dados relacional

---

## 📚 Objetivo do Projeto

Este projeto tem como objetivo consolidar conhecimentos em:

- Desenvolvimento de APIs REST
- Organização de código com arquitetura em camadas
- Integração com banco de dados relacional
- Testes unitários em aplicações backend
- Containerização de aplicações com Docker
- Estruturação de projetos backend para portfólio

---

## 🔮 Melhorias Futuras

- Implementação de autenticação com JWT
- Paginação e filtros dinâmicos
- Versionamento de banco com Flyway
- Deploy em ambiente cloud

---

## 👨‍💻 Autor

Desenvolvido por **Gabriel Oliveira**

Projeto pessoal focado na evolução técnica como desenvolvedor backend.
