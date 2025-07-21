FROM openjdk:17
COPY . /app
WORKDIR /app
RUN javac Vault.java VaultApplication.java
CMD ["java", "VaultApplication"]
