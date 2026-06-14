# Task MANAGEMENT API

A RESTful CRUD API for Task Management built with **Spring Boot 3** and **in-memory storage** using `ConcurrentHashMap`. No database setup required — runs out of the box.

---

## Prerequisites

Make sure the following are installed on your machine before running the project.

```
 Java 17 or higher.
 Maven 3.8 or higher.
```

### Verify Installation

```bash
java -version
# java version "17.x.x"

mvn -version
# Apache Maven 3.x.x
```

---

## Tech Stack & Dependencies

### Spring Boot Parent

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.4</version>
</parent>
```

### Dependencies

#### 1. Spring Boot Starter Web
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

#### 2. Spring Boot Starter Validation
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

#### 3. Lombok
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

#### 4. Spring Boot Starter Test
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

## Project Structure

```
task-api/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/example/taskapi/
        │   ├── TaskApiApplication.java       ← Entry point
        │   ├── controller/
        │   │   └── TaskController.java       ← REST endpoints
        │   ├── service/
        │   │   ├── TaskService.java          ← Service interface
        │   │   └── TaskServiceImpl.java      ← Business logic
        │   ├── repository/
        │   │   ├── TaskRepository.java       ← Repository interface
        │   │   └── InMemoryTaskRepository.java ← ConcurrentHashMap store
        │   ├── model/
        │   │   └── Task.java                 ← Entity class
        │   ├── dto/
        │   │   ├── CreateTaskRequest.java    ← Create request body
        │   │   ├── UpdateTaskRequest.java    ← Update request body
        │   │   ├── TaskResponse.java         ← API response body
        │   │   └── ApiResponse.java          ← Generic response wrapper
        │   ├── enums/
        │   │   └── TaskStatus.java           ← PENDING | IN_PROGRESS | DONE
        │   └── exception/
        │       ├── TaskNotFoundException.java
        │       └── GlobalExceptionHandler.java
        └── resources/
            └── application.properties
```

---

## How to Run

### Step 1 — Clone or download the project

```bash
git clone <repository-url>
cd task-api
```

### Step 2 — Build the project

```bash
mvn clean install
```

### Step 3 — Run the application

```bash
mvn spring-boot:run
```

Or run the JAR directly after build:

```bash
java -jar target/task-api-1.0.0.jar
```

### Step 4 — Confirm it is running

Open your browser or Postman and hit:

```
GET http://localhost:8080/api/v1/tasks
```

Expected response:
```json
{
  "status": "success",
  "message": "Tasks fetched successfully",
  "data": []
}
```

---

## Configuration

Located at `src/main/resources/application.properties`:

```properties
server.port=8081
spring.application.name=task-api
```

---

## API Endpoints

Base URL: `http://localhost:8081/api/v1`


| Method | Endpoint             | Description | Status Code      |
|--------|----------------------|-------------|------------------|
| `POST` | `/tasks`             | Create a new task | `201 Created`    |
| `GET` | `/tasks`             | Get all tasks | `200 OK`         |
| `GET` | `/tasks?status=DONE` | Filter tasks by status | `200 OK`         |
| `GET` | `/tasks/{id}`        | Get task by ID | `200 OK`         |
| `PUT` | `/tasks/{id}`        | Update a task | `200 OK`         |
| `DELETE` | `/tasks/{id}`        | Delete a task | `204 No Content` |

---

## Request & Response Examples

### Create Task — `POST /api/v1/tasks`

**Request Body:**
```json
{
  "title": "Complete project documentation",
  "description": "Write API docs and README",
  "status": "PENDING",
  "dueDate": "2027-06-30"
}
```

> `title` and `dueDate` are **mandatory**. `dueDate` must be a **future date**. `status` defaults to `PENDING` if not provided.

**Response — `201 Created`:**
```json
{
  "status": "success",
  "message": "Task created successfully",
  "data": {
    "uniqueId": "550e8400-e29b-41d4-a716-446655440000",
    "title": "Complete project documentation",
    "description": "Write API docs and README",
    "status": "PENDING",
    "dueDate": "2027-06-30"
  }
}
```

### Validation Error — `400 Bad Request`

```json
{
  "status": "failed",
  "message": "Validation failed",
  "data": {
    "title": "Title is mandatory",
    "dueDate": "Due date must be in the future"
  }
}
```

### Not Found Error — `404 Not Found`

```json
{
  "status": "failed",
  "message": "Task not found with id: abc-123",
  "data": null
}
```

## Notes

- Data is stored **in memory** — all tasks are lost when the server restarts
- No database or external service is required