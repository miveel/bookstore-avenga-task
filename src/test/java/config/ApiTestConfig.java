package config;

import io.restassured.RestAssured;

public class ApiTestConfig {

    public static void setup() {
        // Base URL može doći iz env varijable ili default
        RestAssured.baseURI = System.getenv().getOrDefault("BASE_URL", "https://fakerestapi.azurewebsites.net");
        RestAssured.basePath = "/api/v1";

        // Opcionalno: global logging
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
