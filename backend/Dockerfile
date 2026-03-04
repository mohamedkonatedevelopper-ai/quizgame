# Étape 1 : Build avec Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Run avec Java
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE $PORT

ENTRYPOINT ["java","-jar","app.jar"]
