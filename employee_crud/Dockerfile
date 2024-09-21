FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install

FROM openjdk:17-slim

WORKDIR /app

VOLUME /tmp

# COPY target/employee-0.0.1-SNAPSHOT.jar app.jar
COPY --from=builder /app/target/employee-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
