package tests;

import config.ApiTestConfig;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base class for all API tests.
 * Handles global setup such as configuring RestAssured with the base URL and logging.
 * All endpoint-specific test classes should extend this class to inherit the setup.
 */
public abstract class BaseApiTest {

    @BeforeAll
    static void globalSetup() {
        ApiTestConfig.setup();
    }
}


