package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Utility class to validate API responses.
 * Provides reusable methods for common response assertions such as status code, JSON fields, and schema validation.
 */
public class ResponseValidator {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Asserts that the response has the expected HTTP status code.
     *
     * @param response       the Response object returned from the API call
     * @param expectedStatus the expected HTTP status code
     */
    public static void assertStatusCode(Response response, int expectedStatus) {
        assertThat("Unexpected status code", response.getStatusCode(), equalTo(expectedStatus));
    }

    /**
     * Validates that a JSON field in the response equals the expected value.
     *
     * @param response      the API response
     * @param field         the JSON path to the field
     * @param expectedValue the expected value of the field
     */
    public static void assertJsonFieldEquals(Response response, String field, Object expectedValue) {
        assertThat("Unexpected value for field: " + field,
                response.jsonPath().get(field),
                equalTo(expectedValue));
    }

    /**
     * Validates that a JSON field in the response is null.
     *
     * @param response the API response
     * @param field    the JSON path to the field
     */
    public static void assertJsonFieldIsNull(Response response, String field) {
        assertThat("Expected null for field: " + field,
                response.jsonPath().get(field),
                nullValue());
    }

    /**
     * Validates a JSON string against a JSON schema file in src/test/resources/schemas/.
     *
     * @param json           JSON string to validate
     * @param schemaFileName JSON schema file name
     */
    public static void assertMatchesSchema(String json, String schemaFileName) {
        URL schemaUrl = ResponseValidator.class.getClassLoader().getResource("schemas/" + schemaFileName);
        if (schemaUrl == null) {
            throw new IllegalArgumentException("JSON schema not found in classpath: schemas/" + schemaFileName);
        }

        assertThat("Object does not match schema: " + schemaFileName,
                json,
                JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
    }

    /**
     * Validates a list of books in the response against the Book schema.
     * Supports empty lists.
     *
     * @param response the API response containing a list of books
     */
    public static void assertBookListMatchesSchema(Response response) {
        List<?> books = response.jsonPath().getList("$");
        assertThat("Books list should not be null", books, notNullValue());

        for (Object book : books) {
            try {
                // Convert object (Map) to JSON string for schema validation
                String json = mapper.writeValueAsString(book);
                assertMatchesSchema(json, "BookSchema.json");
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert book object to JSON", e);
            }
        }
    }
}
