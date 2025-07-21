# SuggestifyG4Plus

Single-service deployment with Spring Boot and Docker

## Building

Compile and run directly with Java:

```bash
javac VaultApplication.java
java VaultApplication
```

## Docker

Build the container and run the application:

```bash
docker build -t vault-app .
docker run --rm vault-app
```

## Docker Compose

Launch the database and application together:

```bash
docker-compose up
```

