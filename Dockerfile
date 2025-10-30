FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /qr_redirect_microservice
COPY --from=builder /app/target/Qr_management_microservice-0.0.1-SNAPSHOT.jar /qr_redirect_microservice/Qr_management_microservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "Qr_management_microservice-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]