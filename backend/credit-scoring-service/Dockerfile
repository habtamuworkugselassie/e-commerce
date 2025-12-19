# Build stage
FROM gradle:8.14.3-jdk21 AS build

WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./
COPY gradlew.bat ./

# Copy source code
COPY src/ src/

# Build the application
RUN ./gradlew clean build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

# Install curl for healthchecks
RUN apk add --no-cache curl netcat-openbsd

WORKDIR /app

# Database build arguments
ARG DB_HOST=localhost
ARG DB_PORT=5432
ARG DB_NAME=credit_scoring_db
ARG DB_USERNAME=sa
ARG DB_PASSWORD=password
ARG DB_DRIVER=org.h2.Driver

# Database environment variables
ENV DB_HOST=${DB_HOST}
ENV DB_PORT=${DB_PORT}
ENV DB_NAME=${DB_NAME}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_DRIVER=${DB_DRIVER}

# Create a non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

