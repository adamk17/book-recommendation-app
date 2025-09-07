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
- 🔐 JWT-based authentication and role-based authorization (Admin/User)

## 🚀 Run Locally

### Requirements

- Java 17+
- PostgreSQL (running locally)
- Maven

### Run

```bash
./mvnw spring-boot:run
```

Application will be available at: `http://localhost:8081`

## 🐳 Run with Docker Compose

When using Docker Compose from the root project:

```bash
docker compose up userservice --build
```

This will build and start the **userservice** container (together with PostgreSQL and other required services). Userservice will be available at:

```bash
http://localhost:8081
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

### Authentication
- `POST /auth/login` → login, returns JWT token
- `POST /auth/register` → register new user

### User Management
- `GET /api/v1/users/{id}` → accessible for Admin and the user itself
- `POST /api/v1/users` → Admin only
- `PUT /api/v1/users/{id}` → Admin only
- `DELETE /api/v1/users/{id}` → Admin only

## 🔑 JWT Structure

Example JWT payload:
```json
{
  "sub": "1",
  "username": "alice",
  "roles": ["ROLE_USER"],
  "exp": 1736348390
}
```

- `sub`: User ID
- `username`: Username
- `roles`: User roles from DB
- `exp`: Expiration timestamp

## 💡 Tips

- Use `docker compose logs -f userservice` to debug container logs
- Default credentials are seeded via `data.sql`
- Update `application.yaml` or use environment variables to configure DB and JWT secret
