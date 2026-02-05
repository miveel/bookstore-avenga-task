package utils;

import models.Author;
import models.Book;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * Utility class to build HTTP requests for Bookstore API tests.
 * Provides base request configuration and methods for creating requests with payloads.
 */
public class RequestBuilder {

    private static final String DEFAULT_ACCEPT = "application/json";

    /**
     * Returns a base request specification with JSON content type.
     * Can be used for GET, DELETE, or requests without a body.
     * No Accept header is set explicitly.
     */

    public static RequestSpecification baseRequest() {
        return given()
                .contentType(ContentType.JSON);
    }

    /**
     * Returns a base request specification with JSON content type.
     * Accept header defaults to application/json if not specified.
     *
     * @param acceptType optional Accept type; if null, defaults to application/json
     */
    public static RequestSpecification baseRequestWithAccept(String acceptType) {
        return given()
                .contentType(ContentType.JSON)
                .accept(acceptType != null ? acceptType : DEFAULT_ACCEPT);
    }

    /**
     * Returns a request specification with a Book object as the request body.
     * Used for POST and PUT requests to create or update books.
     *
     * @param book the Book object to be serialized to JSON
     */
    public static RequestSpecification bookRequest(Book book) {
        return baseRequestWithAccept(DEFAULT_ACCEPT)
                .body(book);
    }

    /**
     * Returns a request specification with an Author object as the request body.
     *
     * @param author Author object to be serialized to JSON
     */
    public static RequestSpecification authorRequest(Author author) {
        return baseRequestWithAccept(DEFAULT_ACCEPT)
                .body(author);
    }
}
