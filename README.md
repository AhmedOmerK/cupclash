# ⚽ CupClash — 2026 FIFA World Cup Prediction App

> Predict every match of the 2026 FIFA World Cup using Elo ratings.
> Pick game-by-game or fill out the entire bracket — the math decides the odds.

**Live App:** https://cupclash.onrender.com

---

## Features

### Game-by-Game Mode
Browse all 72 official group stage matches across 12 groups (A–L). Select any match to view Elo-based win/draw/loss probability bars, then lock in your prediction. Every pick is saved to a PostgreSQL database.

### Full Bracket Mode
Fill out the complete knockout bracket from the Round of 32 through to the Final. Your entire bracket is serialized as a JSON document and persisted to the database — it reloads exactly as you left it on every visit.

### Elo Prediction Engine
Win probabilities are calculated using the World Football Elo rating formula:

```
P(A wins) = 1 / (1 + 10^((Elo_B - Elo_A) / 400))
```

A **+100 Elo host-nation boost** is applied to matches involving USA, Mexico, and Canada — the three 2026 World Cup co-hosts.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Frontend | Thymeleaf (server-side templating) |
| ORM | Spring Data JPA + Hibernate |
| Database (local) | H2 In-Memory |
| Database (prod) | PostgreSQL on Render |
| JSON | Jackson ObjectMapper |
| Build | Maven |
| Deployment | Docker → Render |
| Version Control | Git + GitHub |

---

## Architecture

```
src/main/java/com/cupclash/
├── controller/        # HTTP request handlers (MVC)
│   ├── HomeController.java
│   ├── GameController.java
│   └── BracketController.java
├── service/           # Business logic layer
│   ├── EloService.java
│   ├── PredictionService.java
│   └── BracketService.java
├── repository/        # Spring Data JPA interfaces
│   ├── TeamRepository.java
│   ├── MatchRepository.java
│   ├── PredictionRepository.java
│   └── BracketRepository.java
├── model/             # JPA entities
│   ├── Team.java
│   ├── Match.java
│   ├── Prediction.java
│   └── Bracket.java
├── seeder/            # Startup data seeding
│   └── DataSeeder.java
└── exception/         # Global error handling
    └── GlobalExceptionHandler.java
```

---

## Data

- **48 teams** across 6 confederations (official 2026 World Cup qualifiers)
- **72 group stage matches** seeded on startup via `CommandLineRunner`
- **31 knockout match slots** (Round of 32 → Final)
- Elo ratings sourced from [World Football Elo Ratings](https://www.eloratings.net)

---

## Running Locally

**Prerequisites:** Java 17+, Maven

```bash
git clone https://github.com/AhmedOmerK/cupclash.git
cd cupclash
mvn spring-boot:run
```

App starts at `http://localhost:8080`

H2 database console at `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:cupclashdb`
- Username: `sa` | Password: *(blank)*

---

## Performance

| Metric | Value |
|---|---|
| Cold start time | ~2 seconds |
| Page response time | 8–15ms |
| Deployable JAR size | 49MB |
| Lines of Java | ~850 |

---

## Environment Variables (Production)

| Variable | Description |
|---|---|
| `PGHOST` | PostgreSQL host |
| `PGPORT` | PostgreSQL port |
| `PGDATABASE` | Database name |
| `PGUSER` | Database username |
| `PGPASSWORD` | Database password |
| `PORT` | Server port (assigned by Render) |
