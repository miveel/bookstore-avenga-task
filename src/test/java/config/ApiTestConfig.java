package config;

import io.restassured.RestAssured;

/**
 * Global RestAssured configuration for API tests.
 * Sets base URI, base path, and enables logging on validation failures.
 */
public class ApiTestConfig {

    public static void setup() {
        // Set base URL from environment variable or use default
        RestAssured.baseURI = System.getenv().getOrDefault("BASE_URL", "https://fakerestapi.azurewebsites.net");
        RestAssured.basePath = "/api/v1";

        // Optional: enable logging for requests/responses if validation fails
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
