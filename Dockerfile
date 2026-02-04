# -------------------- STEP 1: Base Image --------------------
# Use Eclipse Temurin JDK 21 as base image
FROM eclipse-temurin:21-jdk-jammy

# -------------------- STEP 2: Working Directory --------------------
WORKDIR /app

# -------------------- STEP 3: Copy Gradle Wrapper and Build Files --------------------
# Copy Gradle wrapper and build files first (for dependency caching)
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

# -------------------- STEP 4: Copy Source Code --------------------
COPY src src

# -------------------- STEP 5: Make Gradle Wrapper Executable --------------------
RUN chmod +x ./gradlew

# -------------------- STEP 6: Build Project Without Running Tests --------------------
RUN ./gradlew clean build -x test --no-daemon

# -------------------- STEP 7: Install Allure CLI --------------------
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.22.0/allure-2.22.0.zip && \
    unzip allure-2.22.0.zip -d /opt/ && \
    ln -s /opt/allure-2.22.0/bin/allure /usr/bin/allure && \
    rm allure-2.22.0.zip

# -------------------- STEP 8: Expose Allure Report Port --------------------
EXPOSE 8080

# -------------------- STEP 9: Run Tests, Log Output, and Serve Allure Report --------------------
# This command executes tests automatically on container start
# Logs are saved to /app/build/test-logs.txt
# Allure report is served on http://localhost:8080
CMD ./gradlew clean test allureReport --no-daemon | tee /app/build/test-logs.txt && \
    allure open /app/build/reports/allure-report --host 0.0.0.0 --port 8080
