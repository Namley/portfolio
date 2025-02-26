# Use Eclipse Temurin JDK 21 as the base image
FROM maven:3.8.4-openjdk-17 AS builder



# Set the working directory inside the container
WORKDIR /app
RUN mkdir -p /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package

FROM openjdk:17-jdk-slim
# Copy the JAR file into the container
COPY --from=builder /app/target/portfolio-0.0.1-SNAPSHOT.jar app.jar

RUN chmod +x /app/app.jar
# Expose the application port (adjust based on your Spring Boot configuration)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]