# Book Service

This microservice handles CRUD operations for books. It is a part of the Book Recommendation App.

## 📦 Features

- RESTful API for managing books
- PostgreSQL database integration (via Spring Data JPA)
- Input validation
- Swagger UI documentation
- Unit and integration tests

---

## 🚀 Run Locally

Requirements:
- Java 17+
- PostgreSQL (running locally)
- Maven

```bash
./mvnw spring-boot:run
```

App will be available at `http://localhost:8080`

## 🐳 Run with Docker Compose

When using Docker Compose from the root project:

```bash
docker compose up bookservice --build
```

This will build and start the **bookservice** container (together with PostgreSQL and other requirements services). Bookservice will be available at:

```bash
http://localhost:8080
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

## 🗂 Endpoints

- `POST /api/v1/books`
- `GET /api/v1/books`
- `GET /api/v1/books/{id}`
- `PUT /api/v1/books/{id}`
- `DELETE /api/v1/books/{id}`