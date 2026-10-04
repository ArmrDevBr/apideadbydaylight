# 🩸 Dead by Daylight API (ApiDeadByDaylight)

Uma API RESTful desenvolvida em **Java** com o framework **Spring Boot** para fornecer e gerenciar dados completos do universo de **Dead by Daylight (DBD)**, jogo multiplayer assimétrico de terror da *Behaviour Interactive*.

---

## 📌 Visão Geral

O objetivo principal desta API é servir como uma fonte centralizada e estruturada de dados sobre os elementos do jogo:
* **Sobreviventes e Assassinos** com suas características, atributos físicos, biografia (*lore*) e armas/habilidades.
* **Vantagens (Perks)** categorizadas por lado (`SURVIVOR`/`KILLER`), donos originais ou vantagens gerais, e seus efeitos de estado.
* **Itens e Complementos (Add-ons)** associados por tipo de item ou poder de assassino, abrangendo itens normais e de evento.
* **Oferendas (Offerings)** com filtros por raridade, lado e mecânica de cartas secretas.
* **Efeitos de Estado (Status Effects)** detalhando *Buffs* e *Debuffs* do jogo.
* **Reinos (Realms), Mapas e Capítulos (DLCs)** relacionando o conteúdo de cada lançamento.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17+ (compatível com Java 21)
* **Framework:** Spring Boot
* **Persistência de Dados:** Spring Data JPA / Hibernate
* **Banco de Dados:** SQLite (`sqlite-jdbc` com `hibernate-community-dialects`)
* **Validação:** Jakarta Bean Validation (`spring-boot-starter-validation`)
* **Produtividade:** Project Lombok
* **Gerenciador de Dependências:** Maven

---

## 🏛️ Arquitetura e Decisões de Projeto

A aplicação adota boas práticas de arquitetura em camadas para APIs REST:

1. **Separação Estrita de DTOs:**
   * **`*RequestDTO`:** Java Records imutáveis para entrada de dados (`POST`/`PUT`), com anotações de validação (`@NotBlank`, `@NotNull`, etc.) e referências relacionais por `UUID`.
   * **`*ResponseDTO`:** Java Records imutáveis para saída de dados (`GET`), evitando recursão infinita e loops de serialização JSON do Jackson. Possuem método estático `fromEntity()` para conversão limpa.
2. **Tratamento Global de Erros (`@RestControllerAdvice`):**
   * Interceptação padronizada de exceções (`ResourceNotFoundException`, `MethodArgumentNotValidException`, etc.).
   * Respostas de erro consistentes em formato JSON com status HTTP, descrição, rota e detalhes dos campos com falha de validação.
3. **Mapeamento Relacional Fiel ao Jogo:**
   * Relações `@ManyToOne` e `@OneToMany(mappedBy = "...")` garantindo integridade sem tabelas intermediárias indesejadas.
   * Add-ons vinculados ao tipo de item (`ItemType`) permitindo que itens normais e de evento compartilhem os mesmos complementos sem duplicação de dados.
   * Tipagem segura de enums no banco usando `@Enumerated(EnumType.STRING)`.
   * Textos longos de biografia e descrição mapeados como `TEXT` para não limitar o tamanho.

---

## 🗂️ Estrutura do Domínio

| Entidade | Descrição | Principais Relacionamentos |
| :--- | :--- | :--- |
| **`Survivor`** | Sobreviventes jogáveis | Pertence a uma `Dlc`, possui lista de `Perk` |
| **`Killer`** | Assassinos caçadores | Pertence a uma `Dlc`, possui listas de `Perk` e `Addon` |
| **`Perk`** | Vantagens do jogo | Vinculada a um `Survivor` ou `Killer` (ou geral), aplica `StatusEffect` |
| **`Item`** | Itens equipáveis pelos sobreviventes | Classificado por `ItemType` e `Rarity` |
| **`Addon`** | Complementos de itens ou poderes | Vinculado a um `Killer` ou a um `ItemType` |
| **`Offering`** | Oferendas consumíveis | Pertence a uma `Dlc` (opcional), filtrada por `Role` |
| **`StatusEffect`** | Buffs e Debuffs do jogo | Classificado por `StatusType` e `Role` |
| **`Dlc`** | Capítulos de lançamento | Agrupa `Survivor`, `Killer`, `MapGame` e `Offering` |
| **`RealmDBD`** | Reinos do universo DBD | Possui lista de `MapGame`, pertence a uma `Dlc` |
| **`MapGame`** | Mapas jogáveis de cada arena | Pertence a um `RealmDBD` e a uma `Dlc` |

---

## 📁 Estrutura de Pacotes

```text
br.com.armrdev.apideadbydaylight
 ├── entity/          # Entidades JPA (@Entity)
 │    └── enums/      # Enums de domínio (Role, Rarity, Difficult, ItemType, etc.)
 ├── repository/      # Interfaces Spring Data JPA
 ├── dto/             # Records de Request e Response para cada entidade
 ├── exception/       # Exceções customizadas e GlobalExceptionHandler (@RestControllerAdvice)
 ├── service/         # (Em desenvolvimento) Camada de regras de negócio
 └── controller/      # (Próximo passo) Endpoints REST da API
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
* Java JDK 17 ou superior instalado e configurado no `PATH`.
* Git (opcional, para controle de versão).

### Passos para Execução

1. **Clone ou abra o repositório no seu ambiente:**
   ```bash
   cd ApiDeadByDaylight
   ```

2. **Compilar e executar os testes de validação:**
   * No Windows (PowerShell/CMD):
     ```powershell
     .\mvnw.cmd clean test
     ```
   * No Linux/macOS:
     ```bash
     ./mvnw clean test
     ```

3. **Iniciar a aplicação:**
   * No Windows:
     ```powershell
     .\mvnw.cmd spring-boot:run
     ```
   * No Linux/macOS:
     ```bash
     ./mvnw spring-boot:run
     ```

A API inicializará e conectará automaticamente ao banco local SQLite (`dbd.db`), criando e atualizando as tabelas conforme necessário.

---

## 🗺️ Roadmap de Desenvolvimento

- [x] Modelagem das 10 Entidades e seus Enums
- [x] Criação dos 10 Repositórios Spring Data JPA com métodos de busca customizados
- [x] Criação de todos os DTOs de Entrada (`RequestDTO`) e Saída (`ResponseDTO`)
- [x] Tratamento Global de Erros com `@RestControllerAdvice`
- [x] Implementação da Camada de **Services**
- [x] Implementação dos **Controllers** REST
- [ ] Carga inicial de dados (Data Seeder / Migrations)
- [ ] Documentação com Swagger / OpenAPI

---

## 👤 Autor

Desenvolvido por **ARMRDEV** (`br.com.armrdev`).
