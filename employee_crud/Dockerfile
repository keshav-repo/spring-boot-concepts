FROM openjdk:17-slim

WORKDIR /app

VOLUME /tmp

COPY target/employee-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
