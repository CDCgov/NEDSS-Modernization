package gov.cdc.nbs;

import org.springframework.test.web.servlet.MvcResult;
import gov.cdc.nbs.authentication.AuthUser;

/**
 * Used to store data that will be shared between steps. Always call TestContext.clear() in a @Before block
 */
public class TestContext {
    public static String token;
    public static AuthUser user;
    public static MvcResult response;

    public static void clear() {
        token = null;
        user = null;
        response = null;
    }
}
