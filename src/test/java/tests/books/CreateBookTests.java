package tests.books;

import config.ApiTestConfig;
import io.restassured.response.Response;
import models.Book;
import org.junit.jupiter.api.Test;
import tests.BaseApiTest;
import utils.RequestBuilder;
import utils.ResponseValidator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateBookTests extends BaseApiTest {

    // -------------------- HAPPY PATH --------------------

    @Test
    public void shouldCreateBookWithValidPayload() {
        Book book = new Book();
        book.setTitle("API Test Book");
        book.setDescription("Created via API test");
        book.setPageCount(120);
        book.setExcerpt("Short excerpt");
        book.setPublishDate("2026-02-03T00:00:00Z"); // Dodato Z za ISO format

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .post("/Books");

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");

        assertThat(response.jsonPath().getString("title"), equalTo("API Test Book"));
        assertThat(response.jsonPath().getInt("pageCount"), equalTo(120));
    }

    // -------------------- EDGE CASES --------------------

    @Test
    public void shouldCreateBookWithoutTitle() {
        Book book = new Book();
        book.setDescription("No title book");
        book.setPageCount(50);
        book.setExcerpt("Excerpt");
        book.setPublishDate("2026-02-03T00:00:00Z");

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .post("/Books");

        // FakeRestAPI allows null title
        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");
        ResponseValidator.assertJsonFieldIsNull(response, "title");
    }

    @Test
    public void shouldCreateBookWithMinimalPayload() {
        Book book = new Book();
        book.setPageCount(1);
        book.setPublishDate("2026-02-03T00:00:00Z"); // ISO compliant

        Response response = RequestBuilder.bookRequest(book)
                .when()
                .post("/Books");

        ResponseValidator.assertStatusCode(response, 200);
        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");

        // Optional: assert nullable fields are null
        ResponseValidator.assertJsonFieldIsNull(response, "title");
        ResponseValidator.assertJsonFieldIsNull(response, "description");
        ResponseValidator.assertJsonFieldIsNull(response, "excerpt");
    }
}
