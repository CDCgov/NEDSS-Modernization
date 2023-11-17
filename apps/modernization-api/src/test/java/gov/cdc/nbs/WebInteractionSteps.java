package gov.cdc.nbs;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;

public class WebInteractionSteps {

  private final Active<MockHttpServletResponse> activeResponse;
  private final Active<ResultActions> activeAction;

  public WebInteractionSteps(
      final Active<MockHttpServletResponse> activeResponse,
      final Active<ResultActions> activeAction
  ) {
    this.activeResponse = activeResponse;
    this.activeAction = activeAction;
  }


  @Before("@web-interaction")
  public void reset() {
    activeResponse.reset();
    activeAction.reset();
  }

}
