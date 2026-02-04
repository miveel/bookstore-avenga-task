package tests.books;

import io.restassured.response.Response;
import models.Book;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.BookFactory;
import utils.RequestBuilder;
import utils.ResponseValidator;

/**
 * Tests for POST /Books endpoint.
 */
public class CreateBookTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------
    @Test
    public void shouldCreateBookWithValidPayload() {
        Book book = BookFactory.createFullBook("API Test Book", 120);

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .post("/Books");

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response.getBody().asString(), "BookSchema.json");
        ResponseValidator.assertJsonFieldEquals(response, "title", "API Test Book");
        ResponseValidator.assertJsonFieldEquals(response, "pageCount", 120);
    }

    // -------------------- EDGE / NEGATIVE CASES --------------------
    @Test
    public void shouldCreateBookWithoutTitle() {
        Book book = BookFactory.createBookWithoutTitle(50);

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .post("/Books");

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response.getBody().asString(), "BookSchema.json");
        ResponseValidator.assertJsonFieldIsNull(response, "title");
    }

    @Test
    public void shouldCreateBookWithMinimalPayload() {
        Book book = BookFactory.createMinimalBook();

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .post("/Books");

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response.getBody().asString(), "BookSchema.json");
        ResponseValidator.assertJsonFieldIsNull(response, "title");
        ResponseValidator.assertJsonFieldIsNull(response, "description");
        ResponseValidator.assertJsonFieldIsNull(response, "excerpt");
    }

    @Test
    public void shouldFailForInvalidPublishDateFormat() {
        Book book = new Book();
        book.setTitle("Invalid Date Book");
        book.setPageCount(10);
        book.setPublishDate("03-02-2026"); // Invalid ISO format

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .post("/Books");

        // Expected 400 Bad Request → true negative case
        ResponseValidator.assertStatusCode(response, 400);
    }
}
