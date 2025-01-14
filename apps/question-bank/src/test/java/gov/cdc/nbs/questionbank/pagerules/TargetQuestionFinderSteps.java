package gov.cdc.nbs.questionbank.pagerules;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.pagerules.request.TargetQuestionRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class TargetQuestionFinderSteps {
  private final PageMother mother;

  private final ObjectMapper mapper;

  private final Active<ResultActions> response;
  private final PageRuleRequester requester;
  private final Active<TargetQuestionRequest> jsonRequestBody = new Active<>();

  TargetQuestionFinderSteps(
      final PageMother mother,
      final ObjectMapper mapper,
      final Active<ResultActions> response,
      final PageRuleRequester requester
  ) {
    this.mother = mother;
    this.mapper = mapper;
    this.response = response;
    this.requester = requester;
  }

  @Given("I create a target question request with {string}")
  public void i_create_a_target_question_request_with_function(String function) {
    switch (function) {
      case "Enable":
        PagesQuestion question = new PagesQuestion(1l, false, false, null, null, null, 0, 0, null, null, false, "CODED",
            null, false, null, true, false, false, null, null, 0, null, null, null, null, null, null, false, null, null,
            false, null, null, "text_data", null, null);

        jsonRequestBody.active(new TargetQuestionRequest(Rule.RuleFunction.ENABLE, question, null));
        break;
      case "Date":
        PagesQuestion dateQuestion = new PagesQuestion(1l, false, false, null, null, null, 0, 0, null, null, false,
            "DATE",
            null, false, null, true, false, false, null, null, 0, null, null, null, null, null, null, false, null, null,
            false, null, null, "text_data", null, null);

        jsonRequestBody.active(new TargetQuestionRequest(Rule.RuleFunction.DATE_COMPARE, dateQuestion, null));
        break;
      case "Require":
        PagesQuestion requireQuestion = new PagesQuestion(1l, false, false, null, null, null, 0, 0, null, null, false,
            "CODED",
            null, false, null, true, false, false, null, null, 0, null, null, null, null, null, null, false, null, null,
            false, null, null, "text_data", null, null);

        jsonRequestBody.active(new TargetQuestionRequest(Rule.RuleFunction.REQUIRE_IF, requireQuestion, null));
        break;
      default:
        throw new IllegalArgumentException("Unknown target question function: " + function);
    }

  }

  @When("I send a target question request")
  public void i_send_a_target_question_request() throws Exception {
    WaTemplate temp = mother.one();
    response.active(requester.targetQuestionFinder(temp.getId(), jsonRequestBody.active()));
  }

  @Then("Target questions are returned")
  public void target_question_are_returned() throws Exception {
    String res = this.response.active().andReturn().getResponse().getContentAsString();
    PagesResponse pagesResponse = mapper.readValue(res, PagesResponse.class);
    assertNotNull(pagesResponse);
  }
}
