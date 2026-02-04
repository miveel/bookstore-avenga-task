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
> Note: If using Docker (recommended), the container handles running tests and serving the report automatically.

## Docker Instructions

### 1. Build Docker image
```bash
docker build -t bookstore-api-tests .
```
### 2. Run tests and serve Allure report automatically
```bash  
docker run --rm \
  -p 8080:8080 \
  -e BASE_URL=https://fakerestapi.azurewebsites.net \
  -v $(pwd)/allure-results:/app/build/allure-results \
  bookstore-api-tests
```

* Test execution logs are printed in the console and saved to `/app/build/test-logs.txt` inside the container.
* Allure report is automatically served and available at: [http://localhost:8080](http://localhost:8080)

### 3. View Allure report (Open your broser and navigate to)
```bash  
http://localhost:8080/allureReport/index.html
```
## Running specific tests

### Run a single test class locally
```bash 
./gradlew test --tests BooksApiTests
```
### Run a specific test method locally
```bash 
./gradlew test --tests BooksApiTests.shouldCreateBookWithValidPayload
```
### Run a single test class inside Docker
```bash 
docker run --rm -e BASE_URL=https://fakerestapi.azurewebsites.net bookstore-api-tests \
./gradlew test --tests BooksApiTests
```

## Environment Variables

- `BASE_URL` – base URL for the API (default: https://fakerestapi.azurewebsites.net)
- `ALLURE_RESULTS_DIR` – optional custom directory for Allure results (default: build/allure-results)


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

> Note: The Docker container can also be used in CI/CD pipelines to run tests and serve reports consistently.

---

## Notes / Best Practices

* Follow clean code principles and SOLID design.
* All tests are organized in `src/test/java` and reusable utilities are in `src/test/java/utils`.
* Allure report provides visual summary of all API tests (pass/fail, steps, logs).
* Docker ensures consistent test environment and automatic report generation.
* Use descriptive test method names and comments for happy path vs edge/negative cases.

---