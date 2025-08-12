# Build stage uses Maven to ensure reproducible builds
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace
# Copy entire multi-module project to retain parent POM context
COPY .. /workspace
# Build only the app module and its dependencies
RUN mvn -B -q -DskipTests -pl app -am package

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
ARG JAR_FILE=app/target/app-0.0.1-SNAPSHOT.jar
COPY --from=build /workspace/${JAR_FILE} app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=prod"]