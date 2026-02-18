package gov.cdc.nbs.questionbank.pagerules;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import gov.cdc.nbs.questionbank.pagerules.request.SourceQuestionRequest;
import gov.cdc.nbs.questionbank.pagerules.request.TargetQuestionRequest;
import gov.cdc.nbs.questionbank.pagerules.request.TargetSubsectionRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class PageRuleRequester {
  private final Authenticated authenticated;
  private final MockMvc mvc;
  private final ObjectMapper mapper;

  PageRuleRequester(
      final Authenticated authenticated, final MockMvc mvc, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  ResultActions createBusinessRule(final long page, RuleRequest requests) throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(post("/api/v1/pages/{page}/rules", page))
            .content(mapper.writeValueAsString(requests))
            .contentType(MediaType.APPLICATION_JSON));
  }

  ResultActions request(final long page, final long ruleId) throws Exception {
    return mvc.perform(
        this.authenticated.withUser(get("/api/v1/pages/{page}/rules/{ruleId}", page, ruleId)));
  }

  public void deleteBusinessRule(final long page, final long ruleId) {
    try {
      mvc.perform(
              this.authenticated.withUser(
                  delete("/api/v1/pages/{page}/rules/{ruleId}", page, ruleId)))
          .andExpect(status().isOk());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Page Rule Delete request", exception);
    }
  }

  public ResultActions sourceQuestionFinder(final long id, SourceQuestionRequest request)
      throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(post("/api/v1/pages/{id}/rules/source/questions", id))
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON));
  }

  public ResultActions targetQuestionFinder(final long id, TargetQuestionRequest request)
      throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(post("/api/v1/pages/{id}/rules/target/questions", id))
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON));
  }

  public ResultActions targetSubsectionFinder(final long id, TargetSubsectionRequest request)
      throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(post("/api/v1/pages/{id}/rules/target/subsections", id))
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON));
  }
}
