# Recommendation Service

This microservice generates personalized book recommendations based on user reviews. It is a part of the **Book Recommendation App** ecosystem.

## 📦 Features

- RESTful API for generating book recommendations
- Integration with **Book Service** and **Review Service** via FeignClient
- DTO pattern for clean request/response mapping
- Centralized exception handling
- Swagger UI for API documentation
- Unit and integration tests with JUnit + MockMvc

## 🚀 Run Locally

### Requirements

- Java 17+
- Maven
- Running instances of:
    - **Book Service** (default: `http://localhost:8080`)
    - **Review Service** (default: `http://localhost:8082`)

### Run

```bash
./mvnw spring-boot:run
```

Application will be available at: `http://localhost:8083`

## 🐳 Run with Docker Compose

When using Docker Compose from the root project:

```bash
docker compose up recommendationservice --build
```

This will build and start the **recommendationservice** container (together with PostgreSQL and other requirements services). Recommendationservice will be available at:

```bash
http://localhost:8083
```

### Stopping

```bash
docker compose down
```

### Reset with database removal

```bash
docker compose down -v
```

## 📚 API Documentation (Swagger)

Swagger UI available at:

```bash
http://localhost:8083/swagger-ui/index.html
```

## 🧪 Testing

Run tests with:

```bash
./mvnw test
```

Test coverage includes:
- Unit tests (`RecommendationServiceTest`)
- Integration tests (`RecommendationControllerTest`)

## 🗂 API Endpoints

- `GET /api/v1/recommendations/{userId}` → returns a list of recommended books the user hasn't reviewed yet