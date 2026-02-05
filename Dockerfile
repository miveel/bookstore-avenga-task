# ============================
# STEP 1: Base image
FROM eclipse-temurin:21-jdk-jammy

# STEP 2: Working directory
WORKDIR /app

# STEP 3: Copy Gradle wrapper and build files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

# STEP 4: Copy source code
COPY src src

# STEP 5: Make Gradle wrapper executable
RUN chmod +x ./gradlew

# STEP 6: Build project (without running tests)
RUN ./gradlew clean build -x test --no-daemon

# STEP 7: Install Allure CLI (Optional)
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.22.0/allure-2.22.0.zip && \
    unzip allure-2.22.0.zip -d /opt/ && \
    ln -s /opt/allure-2.22.0/bin/allure /usr/bin/allure && \
    rm allure-2.22.0.zip

# ============================
# Default CMD — generate results
CMD ./gradlew test allureReport --no-daemon
