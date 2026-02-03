package tests.books;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class GetBooksTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------

    @Test
    public void shouldReturnAllBooks() {
        // Send GET request to /Books endpoint
        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Books");

        // Validate HTTP status code
        ResponseValidator.assertStatusCode(response, 200);

        // Validate that at least 0 books are returned
        assertThat(response.jsonPath().getList("$").size(), greaterThanOrEqualTo(0));
    }

    // -------------------- EDGE CASES --------------------

}
