package tests.books;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

public class DeleteBookTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------

    @Test
    public void shouldDeleteExistingBook() {
        int id = 1;

        Response response = RequestBuilder.baseRequest()
                .when()
                .delete("/Books/" + id);

        ResponseValidator.assertStatusCode(response, 200);
    }

    // -------------------- EDGE CASE --------------------

    @Test
    public void shouldReturn200WhenDeletingNonExistingBook() {
        int id = 999999;

        Response response = RequestBuilder.baseRequest()
                .when()
                .delete("/Books/" + id);

        // FakeRestAPI returns 200 even if the book does not exist
        ResponseValidator.assertStatusCode(response, 200);
    }
}
