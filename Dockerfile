# Build stage
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy Maven wrapper and pom first to leverage Docker cache for dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Grant execution rights on the maven wrapper
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -DskipTests

# Copy source and build
COPY src src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

# Create a non-root user for better security
RUN addgroup --system app && adduser --system --ingroup app app

WORKDIR /app

# Copy the built jar
COPY --from=builder /app/target/*.jar app.jar

# Set ownership
RUN chown app:app /app/app.jar

# Switch to non-root user
USER app

EXPOSE 8080

ENV JAVA_TOOL_OPTIONS="-Djava.security.egd=file:/dev/./urandom -Xms256m -Xmx512m"

CMD ["sh", "-c", "exec java $JAVA_TOOL_OPTIONS -jar /app/app.jar"]
