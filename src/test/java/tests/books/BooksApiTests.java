package tests.books;

import config.ApiTestConfig;
import models.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import utils.RequestBuilder;
import utils.ResponseValidator;

import io.qameta.allure.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BooksApiTests {

    @BeforeAll
    public static void setup() {
        // Configure RestAssured base URI, base path, and optional logging
        ApiTestConfig.setup();
    }

    // -------------------- HAPPY PATH TESTS --------------------

    @Test
    public void testGetAllBooks() {
        // Send GET request to retrieve all books
        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Books");

        // Validate status code and that list has 0 or more items
        ResponseValidator.assertStatusCode(response, 200);
        assertThat(response.jsonPath().getList("$").size(), greaterThanOrEqualTo(0));
    }

    @Test
    public void testCreateBook() {
        // Prepare a new book object
        Book book = new Book();
        book.setTitle("Test Book");
        book.setDescription("Demo Description");
        book.setPageCount(100);
        book.setExcerpt("Excerpt here");
        book.setPublishDate("2026-02-03T00:00:00");

        // Send POST request to create book
        Response response = RequestBuilder.bookRequest(book)
                .when()
                .post("/Books");

        // Validate status code and that the title matches
        ResponseValidator.assertStatusCode(response, 200);
        assertThat(response.jsonPath().getString("title"), equalTo("Test Book"));
    }

    @Test
    public void testGetBookById() {
        int id = 1; // Example existing book ID
        // Send GET request for a specific book
        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Books/" + id);

        // Validate status code and ID
        ResponseValidator.assertStatusCode(response, 200);
        assertThat(response.jsonPath().getInt("id"), equalTo(id));
    }

    @Test
    public void testUpdateBook() {
        int id = 1; // Example book ID
        // Prepare updated book object
        Book book = new Book();
        book.setTitle("Updated Book");
        book.setDescription("Updated Description");
        book.setPageCount(150);
        book.setExcerpt("Updated Excerpt");
        book.setPublishDate("2026-02-03T00:00:00");

        // Send PUT request to update the book
        Response response = RequestBuilder.bookRequest(book)
                .when()
                .put("/Books/" + id);

        // Validate status code and updated title
        ResponseValidator.assertStatusCode(response, 200);
        assertThat(response.jsonPath().getString("title"), equalTo("Updated Book"));
    }

    @Test
    public void testDeleteBook() {
        int id = 1; // Example book ID

        // Send DELETE request for the book
        Response response = RequestBuilder.baseRequest()
                .when()
                .delete("/Books/" + id);

        // Validate status code
        ResponseValidator.assertStatusCode(response, 200);
    }

    // -------------------- EDGE CASE TESTS --------------------

    @Test
    public void testGetNonExistingBook() {
        int id = 999999; // Non-existing book ID
        // Send GET request
        Response response = RequestBuilder.baseRequest()
                .when()
                .get("/Books/" + id);

        // Validate 404 status
        ResponseValidator.assertStatusCode(response, 404);
    }

    @Test
    public void testCreateBookWithoutTitle() {
        // Prepare book object without title
        Book book = new Book();
        book.setDescription("No title");
        book.setPageCount(50);
        book.setExcerpt("Excerpt");
        book.setPublishDate("2026-02-03T00:00:00");

        // Send POST request
        Response response = RequestBuilder.bookRequest(book)
                .when()
                .post("/Books");

        // Validate that API returns 200 and title is null (Fake API behavior)
        ResponseValidator.assertStatusCode(response, 200);
        assertThat(response.jsonPath().getString("title"), nullValue());
    }
}
