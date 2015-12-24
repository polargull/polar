package polarbear.acceptance;

import static org.junit.Assert.assertNotNull;

public abstract class AbstractAcceptanceTest {
    protected String url;

    protected void setUp(String url) throws Exception {
        this.url = url;
        assertNotNull("server.url not set", url);
    }
}
