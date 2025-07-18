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

Application will be available at: `http://localhost:8082` (or as configured in `application.yaml`)

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

## ⚙️ Database Configuration

Default PostgreSQL settings are stored in `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/reviewservice_db
    username: your_user
    password: your_password
```