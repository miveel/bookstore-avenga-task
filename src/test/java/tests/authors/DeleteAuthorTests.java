package tests.authors;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

/**
 * Tests for DELETE /Authors/{id} endpoint.
 *
 * FakeRestAPI behavior:
 * - Returns 200 OK for any numeric ID
 * - Returns 400 for invalid ID format
 */
public class DeleteAuthorTests extends BaseApiTest {

    @Test
    public void shouldReturn200WhenDeletingExistingAuthor() {
        int authorId = 1; // any existing ID, existence doesn't matter in FakeRestAPI

        Response response = RequestBuilder.baseRequest()
                .when()
                .delete("/Authors/" + authorId);

        ResponseValidator.assertStatusCode(response, 200);
    }

    @Test
    public void shouldReturn200WhenDeletingNonExistingAuthor() {
        int authorId = 999_999; // ID that does not exist

        Response response = RequestBuilder.baseRequest()
                .when()
                .delete("/Authors/" + authorId);

        ResponseValidator.assertStatusCode(response, 200);
    }

    @Test
    public void shouldReturn400ForInvalidAuthorId() {
        String invalidId = "abc"; // invalid format

        Response response = RequestBuilder.baseRequest()
                .when()
                .delete("/Authors/" + invalidId);

        ResponseValidator.assertStatusCode(response, 400);
    }
}
