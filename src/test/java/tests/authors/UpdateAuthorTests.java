package tests.authors;

import io.restassured.response.Response;
import models.Author;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

/**
 * Specification tests for PUT /Authors/{id} endpoint.
 *
 * Illustrates how updating authors would work if FakeRestAPI supported it.
 *
 * Note:
 * - FakeRestAPI currently returns 400 Bad Request for any PUT /Authors/{id}
 */
@Tag("specification")
public class UpdateAuthorTests extends BaseApiTest {

    @Test
    public void putAuthorWithFullPayload() {
        Author author = buildFullAuthor();

        Response response = RequestBuilder.authorRequest(author)
                .when()
                .put("/Authors/1");

        // FakeRestAPI does not support update, returns 400
        // In a real API, this would return 200 OK for a successful update
        ResponseValidator.assertStatusCode(response, 400);
    }

    @Test
    public void putAuthorWithoutNames() {
        Author author = buildAuthorWithoutNames();

        Response response = RequestBuilder.authorRequest(author)
                .when()
                .put("/Authors/1");

        // FakeRestAPI returns 400 due to missing required fields
        // In a real API, this could be a valid edge-case update → 200 OK
        ResponseValidator.assertStatusCode(response, 400);
    }

    // -------------------- Helper Methods --------------------

    private Author buildFullAuthor() {
        Author author = new Author();
        author.setIdBook(1);
        author.setFirstName("Updated");
        author.setLastName("Author");
        return author;
    }

    private Author buildAuthorWithoutNames() {
        Author author = new Author();
        author.setIdBook(1);
        return author;
    }
}
