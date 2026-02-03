# Bookstore API Automation
## Project Overview

This project contains automated API tests for the Online Bookstore demo API (FakeRestAPI).  
Tests are written in **Java 21**, **Gradle Kotlin DSL**, **RestAssured**, and **JUnit 5**.  
Allure is used for reporting, and Docker & GitHub Actions are configured for CI/CD.

## Requirements

- Java 21
- Gradle 8+
- Docker (optional for containerized tests)
- Internet connection for dependencies

## Project Structure
```bash
bookstore-api-automation/
│
├── src/
│ ├── main/java/ # Models and configuration classes
│ └── test/java/ # Test classes (BooksApiTests, utils, etc.)
├── build.gradle.kts # Gradle Kotlin DSL build file
├── settings.gradle.kts
├── Dockerfile # Docker build instructions
├── README.md # Project instructions
└── .github/workflows/ # GitHub Actions workflow
└── api-automation.yml
```
## Setup & Local Execution

### 1. Clone the repository
```bash
git clone <repo-url>
cd bookstore-api-automation
```
### 2. Set base URL (optional, default is FakeRestAPI)
```bash
export BASE_URL=https://fakerestapi.azurewebsites.net
```
### 3. Build the project

./gradlew clean build

### 4. Run all tests
./gradlew test

### 5. Generate Allure report
./gradlew allureReport

### 6. Serve Allure report locally
./gradlew allureServe


## Running Specific Tests

You can run a single test class or even a specific test method using Gradle:

### Run a single test class - POPRAVI
```bash
./gradlew test --tests BooksApiTests
```

### Run a specific test method -POPRAVI
```bash
./gradlew test --tests BooksApiTests.testCreateBook
```

## Docker Instructions

### 1. Build Docker image
```bash
docker build -t bookstore-api-tests .
```
### 2. Run tests and serve Allure report on host machine
```bash  
docker run --rm \
  -p 8080:8080 \
  -e BASE_URL=https://fakerestapi.azurewebsites.net \
  -v $(pwd)/allure-results:/app/build/allure-results \
  bookstore-api-tests
```
### 3. View Allure report (Open your broser and navigate to)
```bash  
http://localhost:8080/allureReport/index.html
```
## Environment Variables

- `BASE_URL` – base URL for the API (default: https://fakerestapi.azurewebsites.net)
- `ALLURE_RESULTS_DIR` – optional custom directory for Allure results (default: build/allure-results)


## CI/CD with GitHub Actions

- Workflow file: `.github/workflows/api-automation.yml`
- The workflow is triggered on `push` or `pull_request` to the `main` branch.

### Steps in the workflow

1. **Checkout code**  
   Pulls the repository code into the runner.

2. **Set up JDK 21**  
   Ensures the runner uses Java 21 for building and running tests.

3. **Cache Gradle dependencies**  
   Speeds up subsequent builds by caching `.gradle` and Gradle wrapper files.

4. **Build project**  
   Runs `./gradlew clean build -x test` to compile the code.

5. **Run tests and generate Allure report**  
   Executes `./gradlew test allureReport` to run all API tests and generate reports.

6. **Upload Allure report as artifact**  
   Saves the Allure results so they can be downloaded or viewed later in GitHub Actions.

## Notes / Best Practices

- Follow clean code principles and SOLID design.
- All tests are organized in `src/test/java` and reusable utilities are in `src/test/java/utils`.
- Allure report provides visual summary of all API tests (pass/fail, steps, logs).
- Docker ensures consistent test environment.
- CI/CD workflow automatically runs tests on push/PR to main branch.
