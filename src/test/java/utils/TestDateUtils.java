package utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for working with dates in tests.
 *
 * Provides methods to generate current timestamps or formatted dates
 * for API requests and validations.
 */
public class TestDateUtils {

    private static final DateTimeFormatter ISO_FORMATTER =
            DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);

    /**
     * Returns the current UTC date-time in ISO 8601 format.
     * Example: "2026-02-04T12:34:56Z"
     * for POST/PUT requests
     * @return current date-time as ISO 8601 string
     */
    public static String getTodayIsoDate() {
        return ISO_FORMATTER.format(Instant.now());
    }
}
