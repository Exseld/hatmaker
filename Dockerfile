FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace/app
COPY src /workspace/app/src
COPY pom.xml /workspace/app
RUN mvn -f /workspace/app/pom.xml install

FROM --platform=linux/amd64 eclipse-temurin:21-jdk-jammy
COPY --from=build /workspace/app/target/maker-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]