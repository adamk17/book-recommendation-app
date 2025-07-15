# User Service

This microservice handles user management operations. It is a part of the **Book Recommendation App**.

## 📦 Features

- RESTful API for managing users
- PostgreSQL database integration (via Spring Data JPA)
- DTO pattern for request/response mapping
- Input validation with Jakarta Validation
- Centralized exception handling
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

Application will be available at: `http://localhost:8081` (or as configured in `application.yaml`)

## 📚 API Documentation (Swagger)

Swagger UI available at:

```bash
http://localhost:8081/swagger-ui/index.html
```

## 🧪 Testing

Run tests with:

```bash
./mvnw test
```

Test coverage includes:
- Unit tests (`UserServiceTest`)
- Integration tests (`UserControllerTest`)

## 🗂 API Endpoints

- `POST /api/v1/users`
- `GET /api/v1/users`
- `GET /api/v1/users/{id}`
- `PUT /api/v1/users/{id}`
- `DELETE /api/v1/users/{id}`

## ⚙️ Database Configuration

Default PostgreSQL settings are stored in `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/userservice_db
    username: your_user
    password: your_password
```