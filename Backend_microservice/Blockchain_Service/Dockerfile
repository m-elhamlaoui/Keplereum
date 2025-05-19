##-- Stage 1 build: Maven/JDK base img --##
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

##-- Stage 2: JRE Runtime image --##
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=default
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]