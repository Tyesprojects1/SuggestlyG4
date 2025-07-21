# SuggestifyG4Plus

A minimal Java-based vault example that runs from the command line or in Docker.

## Building

Compile and run directly with Java:

```bash
javac Vault.java VaultApplication.java
java VaultApplication
```

When started, the application provides a simple interactive prompt:

```
> store admin s3cr3t
Stored.
> get admin
s3cr3t
> exit
```

## Docker

Build the container and run the application:

```bash
docker build -t vault-app .
docker run --rm -it vault-app
```

## Docker Compose

Launch the database and application together:

```bash
docker-compose up
```
