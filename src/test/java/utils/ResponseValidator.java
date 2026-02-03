package utils;

import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Utility class to validate API responses.
 * Provides reusable methods for common response assertions.
 */
public class ResponseValidator {

    /**
     * Asserts that the response has the expected HTTP status code.
     *
     * @param response       the Response object returned from the API call
     * @param expectedStatus the expected HTTP status code
     */
    public static void assertStatusCode(Response response, int expectedStatus) {
        assertThat(response.getStatusCode(), equalTo(expectedStatus));
    }

    /**
     * Placeholder for future validations.
     * Can be extended with:
     * - JSON schema validation
     * - Specific field checks
     * - Response time assertions
     */
}
