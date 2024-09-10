# Use an official OpenJDK 21 image as the base image
FROM eclipse-temurin:21-jdk as builder

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and project files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

# Grant permission to gradlew
RUN chmod +x ./gradlew

# Build the project using Gradle
RUN ./gradlew build --no-daemon

FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the built jar file from the builder stage
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expose the application's port 
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "/app/app.jar"]

