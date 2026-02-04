package tests.books;

import io.restassured.response.Response;
import models.Book;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.BookFactory;
import utils.RequestBuilder;
import utils.ResponseValidator;

/**
 * Tests for PUT /Books/{id} endpoint.
 */
public class UpdateBookTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------
    @Test
    public void shouldUpdateBookWithValidPayload() {
        int bookId = 1;
        Book book = BookFactory.createFullBook("Updated Title", 200);
        book.setId(bookId);

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .put("/Books/" + bookId);

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response.getBody().asString(), "BookSchema.json");
        ResponseValidator.assertJsonFieldEquals(response, "title", "Updated Title");
        ResponseValidator.assertJsonFieldEquals(response, "pageCount", 200);
    }

    // -------------------- EDGE / NEGATIVE CASES --------------------
    @Test
    public void shouldUpdateBookWithoutTitle() {
        int bookId = 1;
        Book book = BookFactory.createBookWithoutTitle(100);
        book.setId(bookId);

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .put("/Books/" + bookId);

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response.getBody().asString(), "BookSchema.json");
        ResponseValidator.assertJsonFieldIsNull(response, "title");
    }

    @Test
    public void shouldUpdateBookWithMinimalPayload() {
        int bookId = 1;
        Book book = BookFactory.createMinimalBook();
        book.setId(bookId);

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .put("/Books/" + bookId);

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response.getBody().asString(), "BookSchema.json");
    }
}
