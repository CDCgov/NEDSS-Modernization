package gov.cdc.nbs.questionbank.pagerules;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class PageRuleRequest {
  private final Authenticated authenticated;
  private final MockMvc mvc;

  PageRuleRequest(
      final Authenticated authenticated,
      final MockMvc mvc,
      final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions createBusinessRule(final long page, CreateRuleRequest requests) {
    try {
      return mvc.perform(
          this.authenticated.withUser(post("/api/v1/pages/{page}/rules", page))
              .content(asJsonString(requests))
              .contentType(MediaType.APPLICATION_JSON));
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Page Rule Create request", exception);
    }
  }

  ResultActions request(final long page, final long ruleId) {
    try {
      return mvc.perform(
          this.authenticated
              .withUser(get("/api/v1/pages/{page}/rules/{ruleId}", page, ruleId)));
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute page rule request", exception);
    }
  }

  private static String asJsonString(final Object obj) throws Exception {
    return new ObjectMapper().writeValueAsString(obj);
  }

  public void deleteBusinessRule(final long page, final long ruleId) {
    try {
      mvc.perform(
          this.authenticated
              .withUser(delete("/api/v1/pages/{page}/rules/{ruleId}", page, ruleId)))
          .andExpect(status().isOk());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Page Rule Delete request", exception);
    }
  }
}
