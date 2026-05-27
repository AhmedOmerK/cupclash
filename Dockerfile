# Stage 1: build the JAR using Maven + JDK 17
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn package -DskipTests -q

# Stage 2: run the JAR using a lightweight JRE image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/cupclash-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
