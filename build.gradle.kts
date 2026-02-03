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
    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    // RestAssured
    testImplementation("io.rest-assured:rest-assured:5.3.0")
    testImplementation("io.rest-assured:json-path:5.3.0")
    testImplementation("io.rest-assured:xml-path:5.3.0")
    testImplementation("io.rest-assured:json-schema-validator:5.3.0")
    testImplementation("org.hamcrest:hamcrest:2.2")

    // Allure JUnit5 adapter
    testImplementation("io.qameta.allure:allure-junit5:2.22.0")

    // Optional: SLF4J logging for RestAssured
    testImplementation("org.slf4j:slf4j-simple:2.0.9")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("allure.results.directory", "$buildDir/allure-results")

    // Dodatni log posle testova
    doLast {
        println("====================================")
        println("✅ Test execution finished!")
        println("Allure results are in: $buildDir/allure-results")
        println("Run './gradlew allureReport' to generate the HTML report")
        println("====================================")
    }
}

// Task za log nakon generisanja HTML reporta
tasks.register("logAllureReport") {
    doLast {
        println("====================================")
        println("✅ Allure HTML report generated!")
        println("You can open it at: $buildDir/reports/allure-report/index.html")
        println("Or serve it locally with './gradlew allureServe'")
        println("====================================")
    }
}

// Hook da se logAllureReport pokrene nakon allureReport taska
tasks.named("allureReport") {
    finalizedBy("logAllureReport")
}

allure {
    version.set("2.22.0")
}
