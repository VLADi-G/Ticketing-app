# Ticketing System – Backend for Public Transport

This project is a lightweight backend system for managing ticketing in a public transport company. It was developed as part of a technical assessment for Unicard and supports vehicle registration, ticket issuing and validation, system health checks, runtime metrics, and log monitoring.

---

## Technologies Used

- Java 8 (OpenJDK 1.8.0_212)
- Spring Boot
- Spring Data JPA
- PostgreSQL (or H2 for development)
- Maven
- Docker and Docker Compose
- OpenAPI (via Springdoc)
- Logging with Logback

---

## Features

### Domain Functionality

- Register vehicles with type, unique registration number, and capacity
- Issue tickets:
  - Each ticket has a unique code
  - Includes passenger name and timestamp
  - Associated with a specific vehicle
- Validate tickets:
  - Only once
  - Stores the validation timestamp
- List tickets by vehicle

### Monitoring and Observability

- Health check endpoint: `/monitor/health`
- Metrics endpoint: `/monitor/metrics`
  - Total number of tickets
  - Number of validated tickets
  - Number of registered vehicles
- Log monitoring: `/monitor/logs?page=0&size=100`
  - Returns paginated application log entries from `logs/application.log`

---

## OpenAPI and Swagger

- OpenAPI definition: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## How to Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/ticketing-system.git
cd ticketing-system
```

### 2. Build the Application

```bash
./mvnw clean package
```

### 3. Run with Docker Compose (Recommended)

```bash
docker-compose down -v --remove-orphans
docker-compose up --build
```

This command builds and starts both the backend application and the PostgreSQL database.

### 4. Or Run with H2 In-Memory Database (Development Mode)

```bash
./mvnw spring-boot:run
```

No additional setup is required.

---

## API Endpoints Overview

### Vehicle Endpoints

**Register a vehicle**  
`POST /vehicles`  
Request body:
```json
{
  "type": "BUS",
  "registrationNumber": "CA1234TX",
  "capacity": 50
}
```

**List tickets for a vehicle**  
`GET /vehicles/{vehicleId}/tickets`

---

### Ticket Endpoints

**Issue a ticket**  
`POST /tickets`  
Request body:
```json
{
  "passengerName": "Alice",
  "vehicleId": 1
}
```

**Validate a ticket**  
`POST /tickets/{ticketId}/validate`

---

### Monitoring Endpoints

**Health check**  
`GET /monitor/health`

**Metrics**  
`GET /monitor/metrics`

**Log output (paginated)**  
`GET /monitor/logs?page=0&size=100`

---

## Configuration

Main configuration is in `src/main/resources/application.properties`, including:
- Database settings
- Logging configuration
- OpenAPI settings

---

## Project Structure

```
ticketing-system/
├── src/
│   └── main/
│       └── java/... (controllers, services, entities)
│       └── resources/
│           └── application.properties
├── create.sql
├── Dockerfile
├── docker-compose.yml
├── logs/
│   └── application.log
├── pom.xml
```

---

## About This Project

This project was developed as part of a backend developer technical assessment for Unicard.