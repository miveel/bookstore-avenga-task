package tests.authors;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

/**
 * Tests for GET /Authors endpoint.
 *
 * Covers:
 * - Happy path: retrieving the list of authors
 * - Edge / accept type cases
 * - Schema validation for each author
 */
public class GetAllAuthorsTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------
    @Test
    public void shouldGetAllAuthors() {
        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Authors");

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertListIsNotNull(response, "$");

        // Validate each author against Author schema
        ResponseValidator.assertAuthorListMatchesSchema(response);
    }

    // -------------------- EDGE / ACCEPT TYPE CASES --------------------
    @Test
    public void shouldReturn200ForSupportedAcceptTypes() {
        String[] acceptTypes = {"application/json", "text/json", "text/plain"};

        for (String acceptType : acceptTypes) {
            Response response = RequestBuilder.baseRequestWithAccept(acceptType)
                    .when()
                    .get("/Authors");

            ResponseValidator.assertStatusCode(response, 200);

            // Only validate schema for JSON responses
            if ("application/json".equals(acceptType) || "text/json".equals(acceptType)) {
                ResponseValidator.assertAuthorListMatchesSchema(response);
            }
        }
    }
}
