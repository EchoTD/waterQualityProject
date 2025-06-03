# --- Stage 1: Build the application ---
# Use Eclipse Temurin’s Java 21 JDK image as the base. This image is multi-arch.
FROM eclipse-temurin:21-jdk AS build

# Update package lists and install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory
WORKDIR /app

# Copy the entire project (adjust if needed)
COPY . .

# (Optional) You can use the Maven wrapper if desired:
# RUN chmod +x mvnw && ./mvnw clean package -DskipTests
#
# Otherwise, build with the installed Maven:
RUN mvn clean package -DskipTests

# --- Stage 2: Create the runtime image ---
FROM eclipse-temurin:21-jdk

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY --from=build /app/${JAR_FILE} app.jar

# Expose the port used by your Spring Boot application
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
