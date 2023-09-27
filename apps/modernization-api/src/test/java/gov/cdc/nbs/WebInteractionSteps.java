package gov.cdc.nbs;

import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

public class WebInteractionSteps {

    @Autowired
    TestActive<MockHttpServletResponse> activeResponse;

    @Before("@web-interaction")
    public void reset() {
        activeResponse.reset();
    }

}
