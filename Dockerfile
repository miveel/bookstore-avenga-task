# Step 1: Use Eclipse Temurin JDK 21 as base image
FROM eclipse-temurin:21-jdk-jammy

# Step 2: Set working directory inside the container
WORKDIR /app

# Step 3: Copy Gradle wrapper and build files first (for dependency caching)
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

# Step 4: Copy the rest of the project source code
COPY src src

# Step 5: Make Gradle wrapper executable
RUN chmod +x ./gradlew

# Step 6: Build project without running tests
RUN ./gradlew clean build -x test --no-daemon

# Step 7: Run tests and generate Allure results
RUN ./gradlew test allureReport --no-daemon

# Step 8: Install Allure CLI to serve the report
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.22.0/allure-2.22.0.zip && \
    unzip allure-2.22.0.zip -d /opt/ && \
    ln -s /opt/allure-2.22.0/bin/allure /usr/bin/allure && \
    rm allure-2.22.0.zip

# Step 9: Expose port 8080 for Allure server
EXPOSE 8080

# Step 10: Default command to serve Allure report mapped to host
CMD ["allure", "open", "/app/build/reports/allure-report", "--host", "0.0.0.0", "--port", "8080"]

