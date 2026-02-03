package tests;

import config.ApiTestConfig;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    @BeforeAll
    static void globalSetup() {
        ApiTestConfig.setup();
    }
}
