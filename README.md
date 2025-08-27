# Book Recommendation Project

Microservice system for managing and recommending books

## Microservices

- `bookservice` – primary CRUD for the books
- `userservice` – primary CRUD for the users
- `reviewservice` – primary CRUD for the reviews
- `recommendationservice` – recommendation engine based on reviews

## Requirements

- Java 17
- Maven
- PostgreSQL
- Docker & Docker Compose

---

## 🚀 Running with Docker Compose

The easiest way to start the whole system (all services + PostgreSQL) is with **Docker Compose**.

### 1. Build and start all containers

```bash
docker compose up --build
```

This will:
- Start a PostgreSQL container with initial databases
- Build and run all four microservices
- Expose them on ports:
  - **Bookservice** → `http://localhost:8080`
  - **Userservice** → `http://localhost:8081`
  - **Reviewservice** → `http://localhost:8082`
  - **Recommendationservice** → `http://localhost:8083`

### 2. Stopping the system

```bash
docker compose down
```

This will stop and remove all containers, but **keep the database volume** (`pgdata`) so that data persists.

### 3. Full reset (containers + database)

```bash
docker compose down -v
```

This also removes the PostgreSQL volume (all data will be lost).

## 📚 API Documentation

Each service provides Swagger UI documentation:
- Bookservice → `http://localhost:8080/swagger-ui/index.html`
- Userservice → `http://localhost:8081/swagger-ui/index.html`
- Reviewservice → `http://localhost:8082/swagger-ui/index.html`
- Recommendationservice → `http://localhost:8083/swagger-ui/index.html`