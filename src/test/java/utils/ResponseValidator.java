package utils;

import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseValidator {

    public static void assertStatusCode(Response response, int expectedStatus) {
        assertThat(response.getStatusCode(), equalTo(expectedStatus));
    }

    // Ovde kasnije možemo dodati JSON schema validaciju ili check specific fields
}
