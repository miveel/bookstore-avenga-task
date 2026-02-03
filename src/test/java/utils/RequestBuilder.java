package utils;

import models.Book;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestBuilder {

    public static RequestSpecification baseRequest() {
        return given()
                .contentType(ContentType.JSON);
    }

    public static RequestSpecification bookRequest(Book book) {
        return baseRequest()
                .body(book);
    }
}
