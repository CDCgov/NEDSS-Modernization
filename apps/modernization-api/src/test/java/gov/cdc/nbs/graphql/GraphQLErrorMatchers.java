package gov.cdc.nbs.graphql;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.web.servlet.ResultMatcher;

public class GraphQLErrorMatchers {

  public static ResultMatcher error(final String message) {
    return result -> jsonPath("$.errors[*].message").value(hasItem(message));
  }

  public static ResultMatcher accessDenied() {
    return error("Access is denied");
  }

  private GraphQLErrorMatchers() {}
}
