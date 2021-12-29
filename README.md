# Products Microservice

Simple microservice that handle product related endpoints in an e-commerce app

## :toolbox: Stack
- Kotlin
- Spring Boot
- Webflux
- R2DBC
- Postgres
- Swagger
- Testcontainers

## :sparkles: Features

- REST API using Webflux functional endpoints
- R2DBC repositories
- Coroutines support
- Integration tests with Testcontainers
- Liquibase migrations
- Swagger documentation
- TODO:
  - CI/CD
  - Test Coverage

---

## :rocket: Running the app
- Requirements:
  - Java (openjdk 11)
  - Recommended IDE: IntelliJ IDEA

Build the app (run the command inside the project dir):
```shell
./gradew build
```

Run via command line:
```shell
./gradew bootRun
```

or via Docker 🐳:
```shell
docker-compose up
```

---

## :books: Docs
The API documentation is available on Swagger, you can access it on `http://localhost:8080/swagger-ui.html`