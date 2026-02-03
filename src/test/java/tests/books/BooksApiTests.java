//package tests.books;
//
//import config.ApiTestConfig;
//import models.Book;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import io.restassured.response.Response;
//import utils.RequestBuilder;
//import utils.ResponseValidator;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//public class BooksApiTests {
//
//    @BeforeAll
//    public static void setup() {
//        ApiTestConfig.setup();
//    }
//
//    // -------------------- HAPPY PATH TESTS --------------------
//
//    @Test
//    public void testGetAllBooks() {
//        Response response = RequestBuilder.baseRequest()
//                .when()
//                .get("/Books");
//
//        ResponseValidator.assertStatusCode(response, 200);
//        ResponseValidator.assertListSize(response, "$", 0); // at least 0 books
//    }
//
//    @Test
//    public void testCreateBook() {
//        Book book = new Book();
//        book.setTitle("Test Book");
//        book.setDescription("Demo Description");
//        book.setPageCount(100);
//        book.setExcerpt("Excerpt here");
//        book.setPublishDate("2026-02-03T00:00:00");
//
//        Response response = RequestBuilder.bookRequest(book)
//                .when()
//                .post("/Books");
//
//        ResponseValidator.assertStatusCode(response, 200);
//        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");
//        ResponseValidator.assertJsonFieldEquals(response, "title", "Test Book");
//    }
//
//    @Test
//    public void testGetBookById() {
//        int id = 1; // example ID
//        Response response = RequestBuilder.baseRequest()
//                .when()
//                .get("/Books/" + id);
//
//        ResponseValidator.assertStatusCode(response, 200);
//        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");
//        assertThat(response.jsonPath().getInt("id"), equalTo(id));
//    }
//
//    @Test
//    public void testUpdateBook() {
//        int id = 1; // example ID
//        Book book = new Book();
//        book.setTitle("Updated Book");
//        book.setDescription("Updated Description");
//        book.setPageCount(150);
//        book.setExcerpt("Updated Excerpt");
//        book.setPublishDate("2026-02-03T00:00:00");
//
//        Response response = RequestBuilder.bookRequest(book)
//                .when()
//                .put("/Books/" + id);
//
//        ResponseValidator.assertStatusCode(response, 200);
//        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");
//        ResponseValidator.assertJsonFieldEquals(response, "title", "Updated Book");
//    }
//
//    @Test
//    public void testDeleteBook() {
//        int id = 1; // example ID
//
//        Response response = RequestBuilder.baseRequest()
//                .when()
//                .delete("/Books/" + id);
//
//        ResponseValidator.assertStatusCode(response, 200);
//    }
//
//    // -------------------- EDGE CASE TESTS --------------------
//
//    @Test
//    public void testGetNonExistingBook() {
//        int id = 999999;
//        Response response = RequestBuilder.baseRequest()
//                .when()
//                .get("/Books/" + id);
//
//        ResponseValidator.assertStatusCode(response, 404);
//    }
//
//    @Test
//    public void testCreateBookWithoutTitle() {
//        Book book = new Book();
//        book.setDescription("No title");
//        book.setPageCount(50);
//        book.setExcerpt("Excerpt");
//        book.setPublishDate("2026-02-03T00:00:00");
//
//        Response response = RequestBuilder.bookRequest(book)
//                .when()
//                .post("/Books");
//
//        // Fake API allows null title, schema still valid
//        ResponseValidator.assertStatusCode(response, 200);
//        ResponseValidator.assertMatchesSchema(response, "BookSchema.json");
//        ResponseValidator.assertJsonFieldIsNull(response, "title");
//    }
//}
