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
 * <p>
 * Provides reusable methods for common response assertions, including:
 * <ul>
 *     <li>Status code validation</li>
 *     <li>JSON list and field validations</li>
 *     <li>Schema validation for books and authors</li>
 * </ul>
 * <p>
 * All methods are static and designed to be used directly in API tests.
 */
public class ResponseValidator {

    private static final ObjectMapper mapper = new ObjectMapper();

    // -------------------- COMMON --------------------

    /**
     * Asserts that the response status code matches the expected value.
     *
     * @param response       The API response to validate.
     * @param expectedStatus The expected HTTP status code.
     */
    public static void assertStatusCode(Response response, int expectedStatus) {
        assertThat("Unexpected status code", response.getStatusCode(), equalTo(expectedStatus));
    }

    /**
     * Asserts that a JSON list at the specified JSONPath is not null.
     *
     * @param response The API response containing the JSON list.
     * @param jsonPath The JSONPath expression pointing to the list.
     */
    public static void assertListIsNotNull(Response response, String jsonPath) {
        List<?> list = response.jsonPath().getList(jsonPath);
        assertThat("List at path '" + jsonPath + "' should not be null", list, notNullValue());
    }

    /**
     * Validates that a JSON string matches the JSON schema file.
     *
     * @param json           The JSON string to validate.
     * @param schemaFileName The name of the JSON schema file (in 'resources/schemas/').
     * @throws IllegalArgumentException If the schema file is not found in the classpath.
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

    // -------------------- JSON FIELD ASSERTIONS --------------------

    /**
     * Asserts that a JSON field in the response is null.
     *
     * @param response The API response containing the field.
     * @param field    The name of the field to validate.
     */
    public static void assertJsonFieldIsNull(Response response, String field) {
        assertThat("Expected null for field: " + field,
                response.jsonPath().get(field),
                nullValue());
    }

    /**
     * Asserts that a JSON field in the response equals the expected value.
     *
     * @param response      The API response containing the field.
     * @param field         The name of the field to validate.
     * @param expectedValue The expected value of the field.
     */
    public static void assertJsonFieldEquals(Response response, String field, Object expectedValue) {
        assertThat("Unexpected value for field: " + field,
                response.jsonPath().get(field),
                equalTo(expectedValue));
    }

    // -------------------- BOOKS --------------------

    /**
     * Validates that each book in the response list matches the 'BookSchema.json' schema.
     *
     * @param response The API response containing the list of books.
     * @throws RuntimeException If any book cannot be converted to JSON or does not match the schema.
     */
    public static void assertBookListMatchesSchema(Response response) {
        List<?> books = response.jsonPath().getList("$");
        assertThat("Books list should not be null", books, notNullValue());

        for (Object book : books) {
            try {
                String json = mapper.writeValueAsString(book);
                assertMatchesSchema(json, "BookSchema.json");
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert book object to JSON", e);
            }
        }
    }

    // -------------------- AUTHORS --------------------

    /**
     * Validates that each author in the response list matches the 'AuthorSchema.json' schema.
     *
     * @param response The API response containing the list of authors.
     * @throws RuntimeException If any author cannot be converted to JSON or does not match the schema.
     */
    public static void assertAuthorListMatchesSchema(Response response) {
        List<?> authors = response.jsonPath().getList("$");
        assertThat("Authors list should not be null", authors, notNullValue());

        for (Object author : authors) {
            try {
                String json = mapper.writeValueAsString(author);
                assertMatchesSchema(json, "AuthorSchema.json");
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert author object to JSON", e);
            }
        }
    }

    /**
     * Validates that a single author in the response matches the 'AuthorSchema.json' schema.
     *
     * @param response The API response containing a single author object.
     */
    public static void assertSingleAuthorMatchesSchema(Response response) {
        assertMatchesSchema(response.getBody().asString(), "AuthorSchema.json");
    }
}
