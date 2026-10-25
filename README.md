# Spring Security Auth JWT Demo

A REST API demo built with Spring Boot, Spring Security, JWT, and PostgreSQL to demonstrate secure authentication and role-based authorization.

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JSON Web Token (JWT)
- PostgreSQL
- Maven
- Docker & Docker Compose
- Lombok

## Getting Started

### Prerequisites

- Java 21
- Maven
- Docker Desktop

### 1. Clone the Repository

```bash
git clone <repository-url>
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

Or use Maven:

```bash
mvn spring-boot:run
```

### 4. Access the Application

- API: `http://localhost:8080`
- pgAdmin: `http://localhost:5050`



### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.1.2/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.1.2/maven-plugin/build-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.1.2/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Security](https://docs.spring.io/spring-boot/4.1.2/reference/web/spring-security.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.1.2/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

