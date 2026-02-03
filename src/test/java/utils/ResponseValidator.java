//package utils;
//
//import io.restassured.response.Response;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//
///**
// * Utility class to validate API responses.
// * Provides reusable methods for common response assertions.
// */
//public class ResponseValidator {
//
//    /**
//     * Asserts that the response has the expected HTTP status code.
//     *
//     * @param response       the Response object returned from the API call
//     * @param expectedStatus the expected HTTP status code
//     */
//    public static void assertStatusCode(Response response, int expectedStatus) {
//        assertThat(response.getStatusCode(), equalTo(expectedStatus));
//    }
//
//    /**
//     * Placeholder for future validations.
//     * Can be extended with:
//     * - JSON schema validation
//     * - Specific field checks
//     * - Response time assertions
//     */
//}
package utils;

import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResponseValidator {

    public static void assertStatusCode(Response response, int expectedStatus) {
        assertThat("Unexpected status code", response.getStatusCode(), equalTo(expectedStatus));
    }

    public static void assertJsonFieldEquals(Response response, String field, Object expectedValue) {
        assertThat("Unexpected value for field: " + field,
                response.jsonPath().get(field),
                equalTo(expectedValue));
    }

    public static void assertJsonFieldIsNull(Response response, String field) {
        assertThat("Expected null for field: " + field,
                response.jsonPath().get(field),
                nullValue());
    }

    public static void assertListSize(Response response, String jsonPath, int minSize) {
        assertThat("Unexpected list size at: " + jsonPath,
                response.jsonPath().getList(jsonPath).size(),
                greaterThanOrEqualTo(minSize));
    }

    // New: Validate response against a JSON schema file in src/test/resources/schemas/
    public static void assertMatchesSchema(Response response, String schemaFileName) {
        assertThat("Response does not match JSON schema: " + schemaFileName,
                response.getBody().asString(),
                JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
    }
}
