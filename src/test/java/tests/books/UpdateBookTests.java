package tests.books;

import io.restassured.response.Response;
import models.Book;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UpdateBookTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------

    @Test
    public void shouldUpdateBookWithValidPayload() {
        int bookId = 1;

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Updated Title");
        book.setDescription("Updated description");
        book.setPageCount(200);
        book.setExcerpt("Updated excerpt");
        book.setPublishDate("2026-02-03T00:00:00Z"); // ISO 8601 with Z

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .put("/Books/" + bookId);

        // Validate HTTP status
        ResponseValidator.assertStatusCode(response, 200);
        // Validate JSON schema
        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");

        // Validate specific fields
        assertThat(response.jsonPath().getString("title"), equalTo("Updated Title"));
        assertThat(response.jsonPath().getInt("pageCount"), equalTo(200));
    }

    // -------------------- EDGE CASES --------------------

    @Test
    public void shouldUpdateBookWithoutTitle() {
        int bookId = 1;

        Book book = new Book();
        book.setId(bookId);
        book.setDescription("No title update");
        book.setPageCount(100);
        book.setExcerpt("Excerpt");
        book.setPublishDate("2026-02-03T00:00:00Z");

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .put("/Books/" + bookId);

        // FakeRestAPI allows null title
        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");
        ResponseValidator.assertJsonFieldIsNull(response, "title");
    }

    @Test
    public void shouldUpdateBookWithMinimalPayload() {
        int bookId = 1;

        Book book = new Book();
        book.setId(bookId);
        book.setPageCount(1);
        book.setPublishDate("2026-02-03T00:00:00Z");

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .put("/Books/" + bookId);

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");
    }
}
