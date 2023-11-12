FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn dependency:go-offline -f /home/app/pom.xml
RUN mvn -f  /home/app/pom.xml clean test package

FROM openjdk:latest
COPY --from=build /home/app/target/backend-order-service.jar ./backend-order-service.jar
ENTRYPOINT ["java", "-jar","backend-order-service.jar"]