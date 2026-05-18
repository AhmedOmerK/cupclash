# CupClash — Project Specification & Roadmap
### 2026 FIFA World Cup Prediction App

---

## Tech Stack

| Layer        | Technology                              |
|--------------|-----------------------------------------|
| Language     | Java 17+                                |
| Framework    | Spring Boot 3.x (Web, DevTools)         |
| Frontend     | Thymeleaf (server-side HTML templates)  |
| ORM          | Spring Data JPA (Hibernate)             |
| DB (local)   | H2 In-Memory Database                   |
| DB (prod)    | PostgreSQL on AWS RDS                   |
| Build        | Maven                                   |
| Server       | Embedded Tomcat (via Spring Boot)       |

---

## Game Modes

### 1. Game-by-Game Mode
- User selects a single match from a list
- App displays both teams, their Elo ratings, and computed win/draw/loss probabilities
- User submits a prediction (Team A win / Draw / Team B win)
- Prediction is saved to the database

### 2. Full Bracket Mode
- Interactive Thymeleaf HTML tournament bracket for all 104 matches
- Covers: Group Stage (48 games) → Round of 32 → Round of 16 → QF → SF → Final
- User fills in predicted winners by clicking; bracket auto-advances
- Entire bracket saved as a single JSON block in the database

---

## Prediction Engine (Elo Math)

Win probability formula (Elo-based):

```
E_a = 1 / (1 + 10^((Elo_B - Elo_A) / 400))
```

- `E_a` = expected win probability for Team A
- `E_b` = 1 - E_a (expected win probability for Team B)

**Host Boost Rule:** Add +100 Elo to any match involving **USA, Mexico, or Canada** when they are playing at home.

---

## Database Strategy

- **Phase 1 (Local Dev):** H2 in-memory DB. Zero setup, resets on restart. Ideal for building and testing.
- **Phase 2 (Production):** AWS RDS PostgreSQL. Switch by activating the `prod` Spring profile (`application-prod.properties`).

---

## Project Milestones

### MILESTONE 1 — Maven Project Skeleton
- [ ] `pom.xml` — all dependencies declared (Spring Boot, Thymeleaf, JPA, H2, PostgreSQL driver)
- [ ] `src/main/resources/application.properties` — H2 datasource, JPA DDL auto-create, H2 console
- [ ] `CupClashApplication.java` — Spring Boot entry point

**Goal:** `mvn spring-boot:run` starts successfully, H2 console accessible at `/h2-console`.

---

### MILESTONE 2 — Data Model (JPA Entities)
- [ ] `Team.java` — entity: id, name, eloRating, confederation, isHost (boolean)
- [ ] `Match.java` — entity: id, teamA, teamB, groupName, matchDate, stage
- [ ] `Prediction.java` — entity: id, match, predictedWinner, confidence, createdAt

---

### MILESTONE 3 — Seed Data
- [ ] `DataSeeder.java` — `CommandLineRunner` that populates all 32 teams with real 2026 Elo ratings
- [ ] Seed all Group Stage matches (48 games with correct group assignments)

---

### MILESTONE 4 — Repository & Service Layer
- [ ] `TeamRepository.java`, `MatchRepository.java`, `PredictionRepository.java`
- [ ] `EloService.java` — win probability calculation with host boost logic
- [ ] `PredictionService.java` — save/retrieve predictions

---

### MILESTONE 5 — Game-by-Game Mode (Web UI)
- [ ] `GameController.java` — `GET /game` (show match list), `POST /game/predict` (save prediction)
- [ ] `game.html` (Thymeleaf) — match selector dropdown, probability display, prediction form
- [ ] `result.html` (Thymeleaf) — confirmation page showing saved prediction

---

### MILESTONE 6 — Full Bracket Mode (Web UI)
- [ ] `BracketController.java` — `GET /bracket`, `POST /bracket/save`
- [ ] `bracket.html` (Thymeleaf) — full interactive 104-match tournament tree
- [ ] `BracketService.java` — serialize/deserialize bracket as JSON

---

### MILESTONE 7 — Polish & Error Handling
- [ ] Global exception handler (`@ControllerAdvice`)
- [ ] Input validation (`@Valid`, `BindingResult`)
- [ ] User-friendly error pages (`error.html`)

---

### MILESTONE 8 — AWS RDS Production Switch
- [ ] `application-prod.properties` — PostgreSQL RDS connection string
- [ ] Environment variable configuration for DB credentials (no hardcoded secrets)
- [ ] Test full app against live RDS instance

---

## Directory Structure (Target)

```
cupclash/
├── pom.xml
├── spec.md
└── src/
    └── main/
        ├── java/com/cupclash/
        │   ├── CupClashApplication.java
        │   ├── controller/
        │   │   ├── GameController.java
        │   │   └── BracketController.java
        │   ├── model/
        │   │   ├── Team.java
        │   │   ├── Match.java
        │   │   └── Prediction.java
        │   ├── repository/
        │   │   ├── TeamRepository.java
        │   │   ├── MatchRepository.java
        │   │   └── PredictionRepository.java
        │   ├── service/
        │   │   ├── EloService.java
        │   │   ├── PredictionService.java
        │   │   └── BracketService.java
        │   └── seeder/
        │       └── DataSeeder.java
        └── resources/
            ├── application.properties
            ├── application-prod.properties
            └── templates/
                ├── index.html
                ├── game.html
                ├── result.html
                ├── bracket.html
                └── error.html
```

---

*Last updated: 2026-05-18*
