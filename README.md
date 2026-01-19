# Modular Trading Engine
**Trabalho de Conclusão de Curso | Universidade de São Paulo (USP)**

Este repositório contém o artefato de software desenvolvido para a monografia **"De monólitos modulares a microsserviços: um guia para facilitar o desenvolvimento de software escalável"**.

O projeto consiste em uma implementação de referência de um sistema de negociação de ativos, utilizado como laboratório controlado para validar a hipótese de que o desacoplamento lógico é um pré-requisito mandatório para a distribuição física.

---

## 1. O Experimento

O software foi desenvolvido seguindo uma abordagem evolutiva, dividida em três fases distintas para isolar variáveis de acoplamento e infraestrutura. O código está segregado em branches que representam "snapshots" arquiteturais dessa evolução:

### Fase I: Monólito Modular Síncrono
* **Branch:** `modular-monolith-interfaces`
* **Arquitetura:** Módulos separados com a utilização dos recursos do Spring Modulith, mas acoplados via injeção de dependência direta de interfaces de serviço.
* **Problema Identificado:** Alto acoplamento temporal e risco de ciclos de dependência entre domínios.

### Fase II: Monólito Modular Orientado a Eventos
* **Branch:** `modular-monolith-events`
* **Arquitetura:** A comunicação síncrona foi substituída por eventos de domínio (*Domain Events*) em memória.
* **Técnica:** Aplicação rigorosa de DDD para garantir que os módulos compartilhem apenas contratos de eventos (DTOs), e não interfaces de implementação.

### Fase III: Microsserviços Distribuídos
* **Branch:** `microservices`
* **Arquitetura:** Extração física dos módulos para microsserviços isolados.
* **Infraestrutura:** Substituição do barramento de eventos em memória por um Broker externo (**RabbitMQ**), introduzindo complexidade de rede e consistência eventual.

---

## 2. Conclusão e Diretrizes

O estudo concluiu que a "Monoliticidade" e a "Distributividade" são ortogonais. Com base nos dados, o trabalho propõe o **Modelo Modular-First**:

1.  **Primazia Lógica:** Projetos devem nascer como Monólitos Modulares (Fase II), utilizando testes automatizados de arquitetura e eventos de domínio para garantir fronteiras rígidas.
2.  **Opcionalidade:** A arquitetura distribuída (Fase III) deve ser encarada como uma "opção" a ser exercida apenas em casos de escalabilidade assimétrica, e não como padrão inicial.
3.  **Matriz de Decisão:** Não se deve extrair microsserviços se o Acoplamento Eferente ($C_e$) do módulo for alto, sob risco de criar um "Monólito Distribuído".

---

## 3. Stack Tecnológica

O projeto utiliza tecnologias que favorecem a modularidade e a governança arquitetural:

* **Java 21:** Linguagem base.
* **Spring Boot 3.2:** Framework base.
* **Spring Modulith:** Para implementação de fronteiras lógicas, barramento de eventos e testes de arquitetura.
* **PostgreSQL:** Banco de dados relacional.
* **RabbitMQ:** Broker de mensageria (exclusivo da Fase III).
* **Docker & Docker Compose:** Orquestração do ambiente distribuído (excusivo da Fase III).

---

## 4. Instruções de Execução

Para reproduzir o ambiente da **Fase III (Microsserviços)**:

### Pré-requisitos
* Docker e Docker Compose
* JDK 21
* Maven

### Passo a Passo

1.  **Checkout da branch distribuída:**
    ```bash
    git checkout microservices
    ```


2.  **Orquestração dos contêineres:**
    ```bash
    docker-compose up -d --build
    ```
    *Isso iniciará: Postgres, RabbitMQ, Trading Service (8080), Portfolio Service (8081) e Catalog Service (8082).*

---

Trabalho desenvolvido no âmbito do Bacharelado em Ciência da Computação da Universidade de São Paulo (USP).

Página da Monografia: https://www.linux.ime.usp.br/~arraisgbr/
