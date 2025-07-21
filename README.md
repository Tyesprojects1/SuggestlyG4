# SuggestifyG4Plus

A minimal Java-based vault example that runs from the command line or in Docker.

The included Docker Compose setup provides an Nginx reverse proxy with a self-signed
certificate for `suggestify.local`. This demonstrates basic DNS and SSL usage.

## Building

Compile and run directly with Java:

```bash
javac Vault.java VaultServer.java VaultApplication.java
java VaultApplication
```

You can also run `./run.sh` to build and start the application in one step.

The application now also exposes an HTTP API on port `8080` with `/store` and `/get` endpoints.

When started, the application provides a simple interactive prompt:

```
> store admin s3cr3t
Stored.
> get admin
s3cr3t
> exit
```

The HTTP server stops automatically when you exit.

## Docker

Build the container and run the application:

```bash
docker build -t vault-app .
docker run --rm -it vault-app
```

## Docker Compose

Launch the database and application together:

```bash
./generate_cert.sh
sudo sh -c 'echo "127.0.0.1 suggestify.local" >> /etc/hosts'
docker-compose up
```

Once running, visit `https://suggestify.local` in your browser. The certificate is self-signed so you may need to accept the warning.
