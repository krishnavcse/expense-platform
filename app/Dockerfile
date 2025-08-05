# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace
COPY .. /workspace
RUN ./mvnw -q -e -DskipTests package || mvn -q -DskipTests package

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
ARG JAR_FILE=app/target/app-0.0.1-SNAPSHOT.jar
COPY --from=build /workspace/${JAR_FILE} app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=prod"]