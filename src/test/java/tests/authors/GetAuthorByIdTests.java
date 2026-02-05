package tests.authors;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

/**
 * Tests for GET /Authors/{id} endpoint.
 *
 * Covers:
 * - Happy path: retrieving an existing author by ID
 * - Edge / negative cases: retrieving by non-existing or invalid ID
 */
public class GetAuthorByIdTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------
    @Test
    public void shouldGetAuthorById() {
        int authorId = 1; // Known existing author ID in FakeRestAPI

        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Authors/" + authorId);

        ResponseValidator.assertStatusCode(response, 200);

        // Validate the single author response against Author schema
        ResponseValidator.assertSingleAuthorMatchesSchema(response);
    }

    // -------------------- EDGE / NEGATIVE CASES --------------------
    @Test
    public void shouldReturn404ForNonExistingAuthor() {
        int nonExistingId = 99999;

        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Authors/" + nonExistingId);

        ResponseValidator.assertStatusCode(response, 404);
    }

    @Test
    public void shouldReturn400ForInvalidAuthorId() {
        String invalidId = "abc";

        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Authors/" + invalidId);

        ResponseValidator.assertStatusCode(response, 400);
    }
}
