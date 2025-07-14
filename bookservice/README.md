# Book Service

This microservice handles CRUD operations for books. It is a part of the Book Recommendation App.

## 📦 Features

- RESTful API for managing books
- PostgreSQL database integration (via Spring Data JPA)
- Input validation
- Swagger UI documentation
- Unit and integration tests

## 🚀 Run Locally

Requirements:
- Java 17+
- PostgreSQL (running locally)
- Maven

```bash
./mvnw spring-boot:run
```

App will be available at `http://localhost:8080`

## 📚 API Documentation (Swagger)

Available at:

```bash
http://localhost:8080/swagger-ui/index.html
```

## 🧪 Testing

Run tests with:

```bash
./mvnw test
```

Includes:
- Unit tests (`BookServiceTest`)
- Integration tests (`BookControllerIntegrationTest`)

## 🗂 Endpoints (Example)

- `POST /api/v1/books`
- `GET /api/v1/books`
- `GET /api/v1/books/{id}`
- `PUT /api/v1/books/{id}`
- `DELETE /api/v1/books/{id}`

## ⚙️ Database Configuration

Default PostgreSQL config in `application.yaml`: