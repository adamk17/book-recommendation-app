# Review Service

This microservice handles **review management** for books. It is a part of the **Book Recommendation App**.

## 📦 Features

- RESTful API for managing reviews
- PostgreSQL database integration (via Spring Data JPA)
- DTO pattern for request/response mapping
- Input validation with Jakarta Validation
- Feign Clients for inter-service communication (`user-service`, `book-service`)
- Centralized exception handling with `@ControllerAdvice`
- Swagger UI for API documentation
- Unit and integration tests with MockMvc

## 🚀 Run Locally

### Requirements

- Java 17+
- PostgreSQL (running locally)
- Maven

### Run

```bash
./mvnw spring-boot:run
```

Application will be available at: `http://localhost:8082`

## 🐳 Run with Docker Compose

When using Docker Compose from the root project:

```bash
docker compose up reviewservice --build
```

This will build and start the **reviewservice** container (together with PostgreSQL and other requirements services). Reviewservice will be available at:

```bash
http://localhost:8082
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
http://localhost:8082/swagger-ui/index.html
```

## 🧪 Testing

Run tests with:

```bash
./mvnw test
```

Test coverage includes:
- Unit tests (`ReviewServiceTest`)
- Integration tests (`ReviewControllerTest`)

## 🗂 API Endpoints

- `POST /api/v1/reviews`
- `GET /api/v1/reviews`
- `GET /api/v1/reviews/{id}`
- `PUT /api/v1/reviews/{id}`
- `DELETE /api/v1/reviews/{id}`
- `GET /api/v1/reviews/users/{id}`