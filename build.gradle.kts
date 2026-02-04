plugins {
    java
    id("io.qameta.allure") version "2.11.2"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // JUnit 5 for unit testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    // RestAssured for API testing
    testImplementation("io.rest-assured:rest-assured:5.3.0")
    testImplementation("io.rest-assured:json-path:5.3.0")
    testImplementation("io.rest-assured:json-schema-validator:5.3.0")

    // Hamcrest matchers
    testImplementation("org.hamcrest:hamcrest:2.2")

    // Allure JUnit5 adapter
    testImplementation("io.qameta.allure:allure-junit5:2.22.0")

    // Optional: SLF4J logging for RestAssured
    testImplementation("org.slf4j:slf4j-simple:2.0.9")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("allure.results.directory", "$buildDir/allure-results")

    // Log summary after test execution
    doLast {
        println("====================================")
        println("✅ Test execution finished!")
        println("Allure results directory: $buildDir/allure-results")
        println("Generate HTML report: './gradlew allureReport'")
        println("Serve HTML report locally: './gradlew allureServe'")
        println("====================================")
    }
}

// Task to log after generating Allure HTML report
tasks.register("logAllureReport") {
    doLast {
        println("====================================")
        println("✅ Allure HTML report generated!")
        println("Open report at: $buildDir/reports/allure-report/index.html")
        println("Or serve it locally: './gradlew allureServe'")
        println("====================================")
    }
}

// Ensure logAllureReport runs after allureReport
tasks.named("allureReport") {
    finalizedBy("logAllureReport")
}

// Allure plugin configuration
allure {
    version.set("2.22.0")
}
