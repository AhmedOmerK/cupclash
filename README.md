<div align="center">

# ⚽ CupClash

### 2026 FIFA World Cup Prediction App

[![Live App](https://img.shields.io/badge/Live%20App-cupclash.onrender.com-brightgreen?style=for-the-badge&logo=render)](https://cupclash.onrender.com)
[![GitHub](https://img.shields.io/badge/GitHub-AhmedOmerK%2Fcupclash-181717?style=for-the-badge&logo=github)](https://github.com/AhmedOmerK/cupclash)

![Java](https://img.shields.io/badge/Java%2017-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot%203-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=thymeleaf&logoColor=white)

*Predict every match of the 2026 FIFA World Cup using Elo ratings.*
*Pick game-by-game or fill out the entire bracket — the math decides the odds.*

</div>

---

## 🎮 Game Modes

<table>
<tr>
<td width="50%">

### ⚽ Game-by-Game Mode
Browse all **72 official group stage matches** across 12 groups. Select any match to view Elo-based win/draw/loss probability bars and lock in your prediction.

</td>
<td width="50%">

### 📊 Full Bracket Mode
Fill out the complete knockout bracket from the **Round of 32 through the Final**. Your bracket is saved as JSON to the database and reloads on every visit.

</td>
</tr>
</table>

---

## 🧮 Elo Prediction Engine

Win probabilities are calculated using the **World Football Elo rating formula**:

```
P(A wins) = 1 / (1 + 10^((Elo_B - Elo_A) / 400))
```

> 🏠 **Host Nation Boost:** USA, Mexico, and Canada receive a **+100 Elo adjustment** in home matches — reflecting the statistical significance of home advantage in international football.

---

## 🛠 Tech Stack

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

---

## 🏗 Architecture

Clean 6-layer Spring Boot architecture:

```
src/main/java/com/cupclash/
│
├── 🎮 controller/       HTTP request handlers
│   ├── HomeController
│   ├── GameController   (6 endpoints)
│   └── BracketController
│
├── ⚙️  service/          Business logic
│   ├── EloService       (Elo math engine)
│   ├── PredictionService
│   └── BracketService   (JSON serialization)
│
├── 🗄  repository/       Spring Data JPA interfaces
│   ├── TeamRepository
│   ├── MatchRepository
│   ├── PredictionRepository
│   └── BracketRepository
│
├── 📦 model/             JPA entities → database tables
│   ├── Team
│   ├── Match
│   ├── Prediction
│   └── Bracket
│
├── 🌱 seeder/            Startup data population
│   └── DataSeeder       (CommandLineRunner)
│
└── ⚠️  exception/        Global error handling
    └── GlobalExceptionHandler (@ControllerAdvice)
```

---

## 📊 By the Numbers

| Metric | Value |
|---|---|
| Java classes | 17 |
| Lines of production Java | ~850 |
| World Cup teams seeded | 48 (all 6 confederations) |
| Group stage matches | 72 (12 groups × 6 games) |
| Knockout bracket slots | 31 (R32 → Final) |
| HTTP endpoints | 6 |
| Cold start time | ~2 seconds |
| Page response time | 8–15ms |
| Deployable JAR size | 49MB |

---

## 🚀 Running Locally

**Prerequisites:** Java 17+, Maven

```bash
# Clone the repo
git clone https://github.com/AhmedOmerK/cupclash.git
cd cupclash

# Start the app
mvn spring-boot:run
```

| URL | Description |
|---|---|
| `http://localhost:8080` | Home page |
| `http://localhost:8080/game` | Game-by-Game mode |
| `http://localhost:8080/bracket` | Full Bracket mode |
| `http://localhost:8080/h2-console` | Database inspector |

**H2 Console login:**
- JDBC URL: `jdbc:h2:mem:cupclashdb`
- Username: `sa` · Password: *(blank)*

---

## 🌍 Deployment

Deployed via **Docker multi-stage build** on [Render](https://render.com) with PostgreSQL.

Spring Boot **profiles** handle environment switching automatically:
- `default` → H2 in-memory (local dev, zero setup)
- `prod` → PostgreSQL on Render (activated via `SPRING_PROFILES_ACTIVE=prod`)

**Required environment variables in production:**

```
PGHOST        PostgreSQL host
PGPORT        PostgreSQL port
PGDATABASE    Database name
PGUSER        Database username
PGPASSWORD    Database password
PORT          Server port (auto-assigned by Render)
```

---

<div align="center">

Built with Java · Spring Boot · PostgreSQL

⚽ [Play CupClash](https://cupclash.onrender.com)

</div>
