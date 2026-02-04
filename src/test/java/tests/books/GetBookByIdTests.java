package tests.books;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

/**
 * Tests for GET /Books/id endpoint.
 * Includes happy path and edge cases.
 */
public class GetBookByIdTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------

    @Test
    public void shouldReturnBookWhenIdExists() {
        int bookId = 1; // existing ID in FakeRestAPI

        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Books/" + bookId);

        ResponseValidator.assertStatusCode(response, 200);

        ResponseValidator.assertMatchesSchema(response.asString(), "BookSchema.json");

        ResponseValidator.assertJsonFieldEquals(response, "id", bookId);
    }

    // -------------------- EDGE / NEGATIVE CASES --------------------

    @Test
    public void shouldReturn404WhenBookDoesNotExist() {
        int nonExistingId = 999999; // ID that does not exist

        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Books/" + nonExistingId);

        ResponseValidator.assertStatusCode(response, 404);
    }
}
