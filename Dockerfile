# -----------------------------------------------------------------------------
# STAGE 1: Build the entire project (Shared Kernel + All Services)
# -----------------------------------------------------------------------------
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copy the entire project structure (Parent POM, Kernel, Services)
COPY . .

# Build everything from the root.
# This ensures shared-kernel is built first and available in the reactor.
RUN mvn clean package -DskipTests

# -----------------------------------------------------------------------------
# STAGE 2: Create the final lightweight image
# -----------------------------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Define a build argument to specify which module we are creating the image for
ARG MODULE_NAME

# Copy only the specific JAR for this module from the builder stage
COPY --from=builder /app/${MODULE_NAME}/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]