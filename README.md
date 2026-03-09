# 🚀 ForumHub API

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.11-brightgreen.svg?logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg?logo=java)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-blue.svg?logo=postgresql)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Este projeto é uma **API RESTful** desenvolvida em **Spring Boot** para gerenciar um sistema de fóruns e tópicos de discussão. Construído como parte de um desafio técnico (Alura), ele aplica as melhores práticas de Arquitetura em Camadas, Segurança com JWT e organização limpa.

---

## 📑 Índice

- [Arquitetura e Padrões](#-arquitetura-e-padrões)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Regras de Negócio](#-regras-de-negócio)
- [Segurança & JWT](#-segurança--jwt)
- [Tratamento de Erros](#-tratamento-de-erros)
- [Como Executar o Projeto](#-como-executar-o-projeto)
- [Fluxo de Requisições da API](#-fluxo-de-requisições-da-api)

---

## Arquitetura e Padrões

A aplicação adota uma arquitetura em camadas (***Layered Architecture***) utilizando o padrão **MVC**, garantindo separação de responsabilidades (Controller, Service, Repository, Entity, DTO) para facilitar testes, escalabilidade e manutenção.

### 🌟 Principais Padrões Aplicados:

- **DTO (Data Transfer Object):** Isolamento total entre as instâncias do banco de dados (`Entities`) e os dados trafegados nas requisições JSON.
- **Repository (Spring Data JPA):** Persistência transparente com consultas customizadas baseadas em interfaces.
- **Service Layer:** Centralização da regra de negócio e validações antes das operações do DB.
- **Exception Hierarchy:** Exceções globais padronizadas herdando de uma `ApiException` central.
- **Autenticação Stateless:** Uso de **Spring Security** com **JWT** de forma totalmente descentralizada (sem sessões no backend).
- **Soft Delete:** Exclusões de Entidades são tratadas como `UPDATE active = false` no banco, mantendo histórico e auditoria de dados íntegros.

---

## 🛠 Tecnologias Utilizadas

- **Java 21:** Versão mais recente e otimizada da linguagem.
- **Spring Boot 3.x:** Framework central (Web, Data JPA, Security, Validation).
- **PostgreSQL:** Banco de dados relacional.
- **Flyway:** Migrations de banco de dados (`/src/main/resources/db/migration`).
- **Aut0 JWT:** Geração e Validação de tokens JWT.
- **Lombok:** Produtividade ao reduzir boilerplate (Getters, Setters, Builders).
- **Dotenv:** Gerenciamento local de variáveis de ambiente.

---

## 📌 Regras de Negócio

### Fóruns
- Criação limitada por permissões: Fóruns fechados ou focados em cursos só podem ser criados por admins. Usuários `STUDENT` só podem criar até 1 fórum público (`OPEN_DISCUSSION`).
- Fóruns podem ser listados (com paginação) e visualizados individualmente dependendo do nível de acesso.
- **Soft delete:** Apenas a flag `active` muda de valor para inativar o fórum.

### Tópicos
- Pertencem obrigatoriamente a um Fórum e são originados por um Usuário logado via Token.
- Apresentam um status natural do ciclo de vida: *Não Respondido, Resolvido, Fechado, etc.*

---

## 🔐 Segurança & JWT

A segurança do sistema ocorre de maneira **STATELESS**, usando tokens JWT gerados na rota `/auth/login`. O Token deve ser enviado obrigatoriamente na aba *Headers* para toda e qualquer rota sensível.

**Exemplo do Header:**
`Authorization: Bearer <seu_token_aqui>`

#### Filtros:
O **`JwtAuthenticationFilter`** assina o controle da request verificando validade e expiração. Handlers de resposta interceptam os status **401 Unauthorized** e **403 Forbidden** convertendo numa mensagem JSON customizada nativamente.

---

## Tratamento de Erros

Em vez das stack traces padrão (feias), criamos exceptions específicas para cada situação previsível, capturadas e transformadas em JSON claro e compreensível:

Exemplo de Exceção Convertida (`ForumAlreadyExistsException`):
```json
{
  "status": 409,
  "error": "CONFLICT",
  "message": "A forum with that name already exists."
}
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- JDK 21 Instalado
- Banco de dados PostgreSQL rodando na porta `5432` com uma base nomeada `forumdbv3` ou similar.
- Variáveis de Ambiente configuradas (baseadas no arquivo `.env.example`).

### Passos:
1. Faça o clone do projeto.
2. Crie no diretorio raiz o arquivo `.env` com base no `.env.example`. Você precisará configurar a URL do banco e o Secret e Time Expiration do JWT.
3. Use o Maven wrapper para compilar o projeto:
   ```bash
   ./mvnw clean compile
   ```
4. Suba o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```
A primeira inicialização já rodará os *arquivos V_*.sql utilizando o **Flyway**, criando as tabelas na base de dados de maneira totalmente automática.

---

## 📥 Fluxo de Requisições da API

Abaixo seguem os requests básicos (Curl) das rotas liberadas após iniciar o servidor.

### 🔑 Autenticação e Conta base

#### Registrar:
```bash
curl -X POST http://localhost:8080/dev-forumhub/v3/api/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"name":"Alice","email":"alice@example.com","password":"pass123"}'
```

#### Login (Gerar Token):
```bash
curl -X POST http://localhost:8080/dev-forumhub/v3/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"alice@example.com","password":"pass123"}'
```

### 🏛 Gerenciamento de Fóruns

#### Criar um Fórum:
```bash
curl -X POST http://localhost:8080/dev-forumhub/v3/api/forums \
  -H "Authorization: Bearer <SEU_TOKEN_AQUI>" \
  -H 'Content-Type: application/json' \
  -d '{"name":"Java Avançado","description":"Discuta recursos extras da versão 21!","type":"OPEN_DISCUSSION"}'
```

#### Listar Fóruns Paginados:
```bash
curl http://localhost:8080/dev-forumhub/v3/api/forums?page=0&size=10
```

#### Listar Tópicos de um Fórum (ID do Tópico ou Forum):
Rotas associadas ao cadastro de `/topics` mantêm validações idênticas aplicando ID nas URLs ou corpo (`body`).

---
🌟 **Desenvolvido com o objetivo de demonstrar conhecimento e consolidação em ecossistema Spring Framework.**

