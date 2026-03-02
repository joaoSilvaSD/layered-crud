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

## Request Flow

Example: Creating a new user via `POST /api/v1/users`

```
1. UserController (Presentation Layer)
   ↓ Receives HTTP request with UserCreateRequest
   ↓ Validates request using @Valid annotation
   
2. UserService (Business Layer)
   ↓ Receives UserCreateRequest from controller
   ↓ Uses UserMapper to convert DTO → Entity
   
3. UserRepository (Persistence Layer)
   ↓ Receives UserEntity
   ↓ Saves to database using Spring Data JPA
   ↓ Returns saved UserEntity with generated ID
   
4. UserService (Business Layer)
   ↑ Receives saved UserEntity
   ↑ Uses UserMapper to convert Entity → DTO
   
5. UserController (Presentation Layer)
   ↑ Receives UserResponse
   ↑ Returns HTTP 201 Created with UserResponse body
```

### Flow Diagram
```
HTTP Request
     ↓
[UserController] ──────────────────┐
     ↓                             │
     ↓ UserCreateRequest           │
     ↓                             │
[UserService] ─────────────────┐   │
     ↓                         │   │
     ↓ UserEntity              │   │
     ↓                         │   │
[UserRepository]               │   │
     ↓                         │   │
[Database (H2)]                │   │
     ↑                         │   │
[UserRepository]               │   │
     ↑                         │   │
     ↑ UserEntity              │   │
     ↑                         │   │
[UserService] ─────────────────┘   │
     ↑                             │
     ↑ UserResponse                │
     ↑                             │
[UserController] ──────────────────┘
     ↑
HTTP Response
```

### Key Components in Flow

- **UserController**: Entry point, handles HTTP, delegates to service
- **UserService**: Business logic, orchestrates operations, uses mapper
- **UserMapper**: Converts between DTOs and Entities (MapStruct)
- **UserRepository**: Data access, extends JpaRepository
- **UserEntity**: JPA entity, maps to database table

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
