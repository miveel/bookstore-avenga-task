package tests.books;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetBookByIdTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------

    @Test
    public void shouldReturnBookWhenIdExists() {
        int bookId = 1; // existing ID in FakeRestAPI

        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Books/" + bookId);

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");

        assertThat(response.jsonPath().getInt("id"), equalTo(bookId));
    }

    // -------------------- EDGE CASE --------------------

    @Test
    public void shouldReturn404WhenBookDoesNotExist() {
        int nonExistingId = 999999;

        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Books/" + nonExistingId);

        ResponseValidator.assertStatusCode(response, 404);
    }
}
