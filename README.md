# Spring Security Auth JWT Demo

A REST API demo built with Spring Boot, Spring Security, JWT, and PostgreSQL for secure authentication and role-based authorization.

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- PostgreSQL
- Maven
- Docker & Docker Compose
- Lombok
- Springdoc OpenAPI (Swagger UI)

## Getting Started

### Prerequisites

- Java 21
- Maven or Maven Wrapper
- Docker Desktop

### 1. Clone the Repository

```bash
git clone https://github.com/sembunly/spring_security_auth_jwt_demo.git
cd spring_security_auth_jwt_demo
```

### 2. Start PostgreSQL

```bash
docker compose up -d
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

Or use `mvn spring-boot:run`.

### 4. Access Services

- API: [http://localhost:8080](http://localhost:8080)
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- pgAdmin: [http://localhost:5050](http://localhost:5050)

## Configuration

| Variable             | Description            |
| -------------------- | ---------------------- |
| `JWT_SECRET`         | JWT signing secret     |
| `DB_PASSWORD`        | PostgreSQL password    |
| `APP_ADMIN_USERNAME` | Initial admin username |
| `APP_ADMIN_EMAIL`    | Initial admin email    |
| `APP_ADMIN_PASSWORD` | Initial admin password |

```bash
export APP_ADMIN_USERNAME=sembunly
export APP_ADMIN_EMAIL=sembunly2005@gmail.com
export APP_ADMIN_PASSWORD='0000'
export DB_PASSWORD='0000'
```

## Reference Documentation

- [Spring Boot](https://docs.spring.io/spring-boot/index.html)
- [Spring Security](https://docs.spring.io/spring-security/reference/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/)
- [Springdoc OpenAPI](https://springdoc.org/)
- [Maven](https://maven.apache.org/guides/index.html)
- [Docker](https://docs.docker.com/)
