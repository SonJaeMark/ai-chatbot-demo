# --- Stage 1: Build the JAR ---
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

# Pre-cache Maven dependencies
COPY .mvn .mvn
COPY mvnw pom.xml ./

# ✅ Fix mvnw permission issue
RUN chmod +x mvnw

# Fetch dependencies
RUN ./mvnw dependency:go-offline

# Copy rest of the source code
COPY src ./src

# Build the JAR (Vaadin prod mode)
ENV VAADIN_PRODUCTIONMODE=true
RUN ./mvnw clean package -Pproduction -DskipTests

# --- Stage 2: Run the app ---
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Optional: Install Redis CLI
RUN apt-get update && \
    apt-get install -y redis-tools && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Copy built JAR from Stage 1
COPY --from=builder /app/target/demo-ai-chatbot-*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]
