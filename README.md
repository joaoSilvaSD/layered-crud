# Layered Architecture CRUD

A Spring Boot REST API implementing a User CRUD application using the traditional Layered Architecture pattern.

## Architecture

This project follows the classic three-tier layered architecture:

### Layers

1. **Presentation Layer** (`presentation`)
   - Controllers: Handle HTTP requests and responses
   - Exception handlers: Manage error responses

2. **Business Layer** (`business`)
   - Services: Contain business logic
   - DTOs: Data Transfer Objects for API communication
   - Mappers: Convert between entities and DTOs

3. **Persistence Layer** (`persistence`)
   - Repositories: Data access using Spring Data JPA
   - Entities: JPA entities representing database tables

## Technology Stack

- Java 17
- Spring Boot 2.5.4
- Spring Data JPA
- H2 Database (in-memory)
- Lombok
- MapStruct
- Maven

## API Endpoints

Base URL: `http://localhost:8081/api/v1/users`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Get all users |
| POST | `/` | Create a new user |
| GET | `/{userId}` | Get user by ID |
| PATCH | `/{userId}` | Update user by ID |
| DELETE | `/{userId}` | Delete user by ID |

## Request/Response Examples

### Create User
```json
POST /api/v1/users
{
  "firstName": "John",
  "lastName": "Doe"
}
```

### Update User
```json
PATCH /api/v1/users/1
{
  "firstName": "Jane",
  "lastName": "Smith"
}
```

### Response
```json
{
  "id": 1,
  "firstName": "Jane",
  "lastName": "Smith"
}
```

## Running the Application

```bash
mvn spring-boot:run
```

The application will start on port 8081.

## H2 Console

Access the H2 database console at: `http://localhost:8081/h2-console`

- JDBC URL: `jdbc:h2:file:./layered-crud-db`
- Username: `sa`
- Password: `password`

## Project Structure

```
layered-crud/
├── src/main/java/com/layered/
│   ├── Application.java
│   └── crud/
│       ├── presentation/
│       │   ├── controller/
│       │   │   └── UserController.java
│       │   └── exception/
│       │       └── NotFoundException.java
│       ├── business/
│       │   ├── service/
│       │   │   └── UserService.java
│       │   ├── dto/
│       │   │   ├── UserCreateRequest.java
│       │   │   ├── UserUpdateRequest.java
│       │   │   └── UserResponse.java
│       │   └── mapper/
│       │       └── UserMapper.java
│       └── persistence/
│           ├── repository/
│           │   └── UserRepository.java
│           └── entity/
│               └── UserEntity.java
└── src/main/resources/
    └── application.properties
```

## Benefits of Layered Architecture

- Simple and easy to understand
- Clear separation of concerns
- Each layer has a specific responsibility
- Easy to test individual layers
- Good for small to medium-sized applications

## Drawbacks

- Tight coupling between layers
- Changes in one layer may affect others
- Can lead to anemic domain models
- Less flexible than hexagonal or clean architecture
