# Ticketing System – Public Transport (Unicard Task)

This project is a lightweight backend system for managing ticketing in a public transport company. It supports vehicle registration, ticket issuing and validation, health checks, metrics, and log monitoring.

---

## Technologies Used

- Java 8
- Spring Boot
- Spring Data JPA
- H2 or PostgreSQL
- Maven
- Docker and Docker Compose
- OpenAPI (via Springdoc)
- Logging with Logback

---

## Features

### Domain Functionality

- Register vehicles with type, registration number, and passenger capacity
- Issue tickets with unique codes, passenger name, and timestamp
- Validate tickets only once, with a validation timestamp
- List tickets by vehicle

### Monitoring and Observability

- Health check endpoint: `/monitor/health`
- Metrics endpoint: `/monitor/metrics`
- Log listing with pagination: `/monitor/logs?page=0&size=100`

### Logging

- Logs are saved to `logs/application.log`
- Configured in `application.properties`
- Default level: INFO

---

## OpenAPI and Swagger

- OpenAPI definition: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## How to Run

### 1. Clone the repository
```bash
git clone <your-repo-url>
cd ticketing-system
```

### 2. Run with Docker Compose
```bash
docker-compose up --build
```

### 3. Or run manually with Maven and H2
```bash
mvn clean install
mvn spring-boot:run
```

---

## API Endpoints Overview

Method: POST  
Endpoint: /vehicles  
Description: Register a new vehicle  

Method: GET  
Endpoint: /vehicles/{id}/tickets  
Description: List tickets for a vehicle  

Method: POST  
Endpoint: /tickets  
Description: Issue a new ticket  

Method: POST  
Endpoint: /tickets/{id}/validate  
Description: Validate a ticket  

Method: GET  
Endpoint: /monitor/health  
Description: System health check  

Method: GET  
Endpoint: /monitor/metrics  
Description: View ticket and vehicle statistics  

Method: GET  
Endpoint: /monitor/logs?page=0&size=100  
Description: View paginated application logs  

---

## Configuration

The application configuration is in `src/main/resources/application.properties`:
- Database configuration
- Logging output path and level
- OpenAPI and Swagger settings

---

## Project Structure

```
ticketing-system/
├── src/
│   ├── main/
│   │   └── java/...
│   └── resources/
│       └── application.properties
├── create.sql
├── Dockerfile
├── docker-compose.yml
├── logs/
│   └── application.log
├── pom.xml
```

---

## Notes

- This project is compatible with Wildfly 18 standards.
- Container-managed transactions are handled via Spring's `@Transactional`.
- Logs are stored on the file system but can be extended to external systems or monitoring tools.

---

## License

This project was developed for a technical assignment and is not intended for production use.
=======
# Ticketing-app
Simple ticketing app
