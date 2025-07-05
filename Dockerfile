# --- Stage 1: Build the JAR ---
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

# Pre-cache Maven dependencies
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copy rest of the source code
COPY src ./src

# Build the JAR
ENV VAADIN_PRODUCTIONMODE=true
RUN ./mvnw clean package -Pproduction -DskipTests

# --- Stage 2: Run the app ---
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Install Redis CLI (optional if used only externally)
RUN apt-get update && \
    apt-get install -y redis-tools && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Copy JAR from builder stage
COPY --from=builder /app/target/demo-ai-chatbot-*.jar app.jar

# Expose app port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
