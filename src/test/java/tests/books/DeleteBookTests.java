package tests.books;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

/**
 * Tests for DELETE /Books endpoint.
 *
 * Adapted to FakeRestAPI behavior:
 * - Returns 200 OK for non-existing and negative IDs
 * - Returns 400 Bad Request for invalid ID format (non-integer)
 */
public class DeleteBookTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------

    @Test
    public void shouldReturn200WhenDeletingExistingBook() {
        int existingId = 1; // Assume this book exists in FakeRestAPI

        Response response = RequestBuilder.baseRequest()
                .when()
                .delete("/Books/" + existingId);

        ResponseValidator.assertStatusCode(response, 200);
    }

    // -------------------- EDGE / NEGATIVE CASES --------------------

    @Test
    public void shouldReturn200WhenDeletingNonExistingBook() {
        int nonExistingId = 999999; // ID that does not exist

        Response response = RequestBuilder.baseRequest()
                .when()
                .delete("/Books/" + nonExistingId);

        // FakeRestAPI returns 200 OK even if the resource does not exist
        ResponseValidator.assertStatusCode(response, 200);
    }

    @Test
    public void shouldReturn200WhenDeletingWithNegativeId() {
        int negativeId = -10;

        Response response = RequestBuilder.baseRequest()
                .when()
                .delete("/Books/" + negativeId);

        // FakeRestAPI allows negative IDs and still returns 200 OK
        ResponseValidator.assertStatusCode(response, 200);
    }

    @Test
    public void shouldReturn400WhenDeletingWithInvalidIdFormat() {
        String invalidId = "abc";

        Response response = RequestBuilder.baseRequest()
                .when()
                .delete("/Books/" + invalidId);

        // API rejects non-integer ID with 400 Bad Request
        ResponseValidator.assertStatusCode(response, 400);
    }
}
