#!/bin/bash
./mvnw spring-boot:run \
 -Dspring-boot.run.arguments="--server.port=8080 --spring.docker.compose.enabled=false --spring.datasource.url=jdbc:mysql://localhost:3307/book_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC --spring.datasource.username=bookuser --spring.datasource.password=bookpassword"
