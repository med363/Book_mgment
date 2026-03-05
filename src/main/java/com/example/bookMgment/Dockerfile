# Dockerfile
# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS build

# Set working directory
WORKDIR /app

# Copy pom.xml first (for better layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-slim

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create a non-root user to run the application
RUN groupadd -r spring -g 1000 && useradd -r -g spring -u 1000 spring

# Set working directory
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Copy wait-for-it script if needed (for database dependency)
COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh

# Change ownership to non-root user
RUN chown -R spring:spring /app

# Switch to non-root user
USER spring:spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/api/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]