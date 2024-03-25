FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/reembolso-api-0.0.1-SNAPSHOT.jar /app/reembolso-api.jar

ENTRYPOINT ["java", "-jar", "reembolso-api.jar"]