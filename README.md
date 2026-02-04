# Hamix

<img src="imagem/hamix.png" alt="Hamster DJ" width="300" height="500"/>




## 📝 Dados da inscrição

- **Nome:** Victor Hugo Duarte da Silva
- **E-mail:** victorhugod.s@hotmail.com
- **N° Inscrição:** 16340


## 💼 Vaga

- **Processo Seletivo:** CONJUNTO Nº 001/2026/SEPLAG e demais Órgãos
- **Cargo:** Analista de Tecnologia da Informação
- **Local:** Secretaria de Estado de Planejamento e Gestão
- **Perfil:** Engenheiro da Computação - Sênior

## 🐹 O projeto Hamix

O projeto Hamix foi concebido como uma forma de dar concretude à ideia de um sistema de backend voltado para a gestão de álbuns e artistas, indo além da implementação técnica para assumir uma identidade própria. 

A inspiração surgiu da junção entre um hamster, animal simpático e querido que transmite proximidade, e a mixagem, elemento que remete diretamente ao universo musical. 

Dessa combinação nasceu o Hamix, uma marca que não apenas nomeia o sistema, mas também orienta sua visão, objetivos e narrativa. Essa identidade foi fundamental para guiar o rumo do desenvolvimento, pois trouxe clareza às escolhas de infraestrutura, ajudou a estruturar requisitos e casos de uso e transformou o projeto em algo mais tangível e significativo. 

Ao longo de todo o processo, cada decisão foi tomada com a marca Hamix como eixo central, alinhando o sistema às demandas do processo seletivo e reforçando a ideia de que software não é apenas tecnologia, mas também propósito e direção.

## 📌 Visão Geral

O **Hamix** é uma API desenvolvida para gerenciar informações de artistas e seus álbuns, incluindo o armazenamento e a consulta de dados e imagens.  

Seu objetivo é oferecer uma base sólida para aplicações que precisem organizar e disponibilizar conteúdos musicais de forma estruturada e acessível.


## 🛠️ Tecnologias

- Java 21
- Spring Boot
- PostgreSQL
- Flyway
- MinIO
- Docker / Docker Compose

## ✅ Requisitos

- Java 21 (para rodar local)
- Maven Wrapper (`./mvnw`)
- Docker (para rodar via containers)

## ⚙️ Configuração

As configurações sensíveis e variáveis por ambiente ficam no arquivo `.env`.

1. Copie o arquivo de exemplo:

```
copy .env.example .env
```

2. Ajuste os valores conforme seu ambiente.

3. Salve o arquivo como `.env`

## 🚀 Como executar

### Com Docker (recomendado)

```
docker compose up -d --build
```

- Aplicação: `http://localhost:8081/hamix/api/v1`  
- Swagger: `http://localhost:8081/hamix/api/v1/swagger-ui/index.html`

### Local (sem Docker)

```
./mvnw spring-boot:run
```

## 🔐 Autenticação

### Registrar usuário

`POST /hamix/api/v1/auth/register`

### Login

`POST /hamix/api/v1/auth/login`

### Refresh

`POST /hamix/api/v1/auth/refresh`

> Use o botão **Authorize** no Swagger com `Bearer <token>`.

## 🩺 Health Checks

- `GET /hamix/api/v1/actuator/health`
- `GET /hamix/api/v1/actuator/health/liveness`
- `GET /hamix/api/v1/actuator/health/readiness`

## 🧪 Testes

```
./mvnw -DskipTests=false test
```

## 🗂️ Estrutura do projeto (resumo)

- `src/main/java` — código da aplicação
- `src/main/resources` — configurações e migrations
- `src/test/java` — testes

 **Obs.:** A imagem utilizada neste README foi gerada com auxílio de ferramentas de inteligência artificial, servindo apenas como recurso ilustrativo. Todo o código, lógica e desenvolvimento do sistema são integralmente autorais.

