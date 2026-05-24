# =========================================================================
# STAGE 1: Compilation & Packaging (Maven Build Container)
# =========================================================================
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

# Set the building directory inside the container
WORKDIR /build

# Copy Maven pom.xml and configuration files to leverage layer caching for dependencies
COPY pom.xml ./
COPY .mvn/ .mvn/
COPY mvnw ./

# Ensure mvnw has executable permission and correct line endings
RUN chmod +x mvnw

# Pre-fetch and cache all Maven dependencies so that subsequent builds are super fast
RUN ./mvnw dependency:go-offline -B

# Copy the actual Spring Boot application source code
COPY src/ ./src/

# Package the application. This compiles code, runs all JUnit tests, and builds the executable fat jar
RUN ./mvnw clean package

# =========================================================================
# STAGE 2: Lightweight Runtime (Temurin JRE Alpine Container)
# =========================================================================
FROM eclipse-temurin:17-jre-alpine

# Set working directory inside the thin runtime container
WORKDIR /app

# Copy the generated JAR file from the builder stage
COPY --from=builder /build/target/*.jar app.jar

# Create logs directory automatically inside the container
RUN mkdir -p logs

# Expose port 8080 for Spring Boot REST API
EXPOSE 8080

# Run the executable fat jar directly
ENTRYPOINT ["java", "-jar", "app.jar"]
