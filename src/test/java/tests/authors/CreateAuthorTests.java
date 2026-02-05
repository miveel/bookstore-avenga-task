package tests.authors;

import io.restassured.response.Response;
import models.Author;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

/**
 * Specification tests for POST /Authors endpoint.
 *
 * Illustrates how creation of authors would work if FakeRestAPI supported it.
 *
 * Note:
 * - FakeRestAPI currently returns 400 Bad Request for any POST /Authors
 */
@Tag("specification")
public class CreateAuthorTests extends BaseApiTest {

    @Test
    public void postAuthorWithFullPayload() {
        Author author = buildFullAuthor();

        Response response = RequestBuilder.authorRequest(author)
                .when()
                .post("/Authors");

        // FakeRestAPI does not support author creation, returns 400
        // In a real API, this would return 201 Created
        ResponseValidator.assertStatusCode(response, 400);
    }

    @Test
    public void postAuthorWithMinimalPayload() {
        Author author = buildMinimalAuthor();

        Response response = RequestBuilder.authorRequest(author)
                .when()
                .post("/Authors");

        // FakeRestAPI returns 400 due to unsupported creation
        // In a real API, minimal valid payload would return 201 Created
        ResponseValidator.assertStatusCode(response, 400);
    }

    @Test
    public void postAuthorWithoutNames() {
        Author author = buildAuthorWithoutNames();

        Response response = RequestBuilder.authorRequest(author)
                .when()
                .post("/Authors");

        // FakeRestAPI returns 400 due to missing required fields
        // In a real API, this could be a valid edge-case happy path if names are optional
        ResponseValidator.assertStatusCode(response, 400);
    }

    // -------------------- Helper Methods --------------------

    private Author buildFullAuthor() {
        Author author = new Author();
        author.setIdBook(1);
        author.setFirstName("John");
        author.setLastName("Doe");
        return author;
    }

    private Author buildMinimalAuthor() {
        Author author = new Author();
        author.setIdBook(1);
        return author;
    }

    private Author buildAuthorWithoutNames() {
        Author author = new Author();
        author.setIdBook(1);
        return author;
    }
}
