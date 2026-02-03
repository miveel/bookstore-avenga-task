package utils;

import models.Book;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * Utility class to build HTTP requests for Bookstore API tests.
 * Provides base request configuration and methods for creating requests with payloads.
 */
public class RequestBuilder {

    /**
     * Returns a base request specification with JSON content type.
     * Can be used for GET, DELETE, or requests without a body.
     */
    public static RequestSpecification baseRequest() {
        return given()
                .contentType(ContentType.JSON);
    }

    /**
     * Returns a request specification with a Book object as the request body.
     * Used for POST and PUT requests to create or update books.
     *
     * @param book the Book object to be serialized to JSON
     */
    public static RequestSpecification bookRequest(Book book) {
        return baseRequest()
                .body(book);
    }
}
