FROM openjdk:17
COPY . /app
WORKDIR /app
RUN javac VaultApplication.java
CMD java VaultApplication