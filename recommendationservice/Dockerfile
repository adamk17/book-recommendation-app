FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw && ./mvnw -q dependency:go-offline

COPY src src
RUN ./mvnw -q package -DskipTests

EXPOSE 8083
CMD ["java", "-jar", "target/recommendationservice-0.0.1-SNAPSHOT.jar"]
