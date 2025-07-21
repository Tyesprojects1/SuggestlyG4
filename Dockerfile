FROM openjdk:17
COPY . /app
WORKDIR /app
RUN javac Vault.java VaultServer.java VaultApplication.java
CMD ["java", "VaultApplication"]
