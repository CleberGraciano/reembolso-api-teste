FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY . .
#RUN mvn clean install -Dmaven.test.skip=true
EXPOSE 8080
CMD ["java", "-jar", "./target/reembolso-api-0.0.1-SNAPSHOT.jar"]