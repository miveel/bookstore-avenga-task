package tests.books;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

/**
 * Tests for GET /Books endpoint.
 * Includes happy path and accept type checks.
 */
public class GetBooksTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------

    @Test
    public void shouldReturnAllBooks() {
        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Books");

        ResponseValidator.assertStatusCode(response, 200);

        // Assert that the books list is not null, using ResponseValidator for consistency
        ResponseValidator.assertListIsNotNull(response, "$");

        // Validate each book in the response against the Book schema
        ResponseValidator.assertBookListMatchesSchema(response);
    }

    // -------------------- ACCEPT TYPE TESTS --------------------
    @Test
    public void shouldReturn200ForSupportedAcceptTypes() {
        String[] acceptTypes = {
                "application/json",
                "text/json",
                "text/plain" // API supports these;
        };

        for (String acceptType : acceptTypes) {
            Response response = RequestBuilder.baseRequestWithAccept(acceptType)
                    .when()
                    .get("/Books");

            ResponseValidator.assertStatusCode(response, 200);

            // Only validate schema for JSON responses
            if ("application/json".equals(acceptType) || "text/json".equals(acceptType)) {
                ResponseValidator.assertBookListMatchesSchema(response);
            }
        }
    }
}
