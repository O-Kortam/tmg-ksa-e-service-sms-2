# Build stage
FROM maven:3.6.3-openjdk-17-slim AS build

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY . .

RUN mvn package -Dmaven.test.skip=true

# Debug: list target files to verify jar name
RUN ls -l /build/target

# Runnable stage
FROM openjdk:17-slim

RUN apt-get update && apt-get install -y fontconfig libfreetype6 && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Use exact path here:
COPY --from=build /build/target/tmg-ksa-e-service-sms-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8025

ENTRYPOINT ["java", "-jar", "app.jar"]
