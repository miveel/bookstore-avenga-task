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
cd bookstore-api-automation  # local folder name, repo on GitHub is named bookstore-avenga-task
```
### 2. Set base URL (optional, default is FakeRestAPI)
```bash
export BASE_URL=https://fakerestapi.azurewebsites.net
```
### 3. Build the project locally
```bash
./gradlew clean build
```

## Running tests 
### Run all tests locally
```bash
./gradlew test
```
### Generate Allure report locally
```bash
./gradlew allureReport
```

### Serve Allure report locally
```bash
./gradlew allureServe
```

## Docker Instructions

### Clean previous results (on host)
```bash 
rm -rf allure-results/* allure-report/*
```
### Build test image
```bash 
docker build -t bookstore-api-tests .
```
### Run tests with mounted result folders
```bash 
docker run --rm -e BASE_URL=https://fakerestapi.azurewebsites.net \
  -v $(pwd)/allure-results:/app/build/allure-results \
  -v $(pwd)/allure-report:/app/build/reports/allure-report \
  bookstore-api-tests

```
### Serve report
```bash 
docker run --rm -p 5050:5050 \
  -v $(pwd)/allure-results:/app/allure-results \
  -v $(pwd)/allure-report:/app/default-reports \
  frankescobar/allure-docker-service

```
### Open report in browser
```bash 
http://localhost:5050/allure-docker-service/latest-report
```

## Environment Variables

- `BASE_URL` – base URL for the API (default: https://fakerestapi.azurewebsites.net)

## CI/CD with GitHub Actions

* Workflow file: `.github/workflows/api-automation.yml`
* The workflow is triggered on `push` or `pull_request` to the `main` branch.

### Steps in the workflow

1. **Checkout code** – Pull repository into runner.
2. **Set up JDK 21** – Ensure Java 21 is available.
3. **Cache Gradle dependencies** – Speed up builds.
4. **Build project** – `./gradlew clean build -x test`.
5. **Run tests and generate Allure report** – `./gradlew test allureReport`.
6. **Upload Allure report as artifact** – For download or viewing in GitHub Actions.

> Note: CI/CD Integration: The project includes a GitHub Actions workflow that automatically builds the project, runs tests, and generates an Allure report on every push or pull request. Allure report artifacts are uploaded and can be reviewed in the Actions panel. 