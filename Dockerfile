FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests -X && \
    echo "=== Checking build output ===" && \
    ls -la target/ && \
    ls -la target/classes/com/example/bookMgment/controller/ && \
    echo "=== Checking JAR contents ===" && \
    jar tf target/*.jar | grep BookController

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN echo "=== App JAR size ===" && ls -la app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]