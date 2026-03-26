package gov.cdc.nbs.questionbank.pagerules;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse;
import gov.cdc.nbs.questionbank.pagerules.request.SourceQuestionRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SourceQuestionFinderSteps {
  @Autowired private PageMother mother;

  @Autowired private ObjectMapper mapper;

  private final Active<ResultActions> response;
  private final PageRuleRequester requester;
  private final Active<SourceQuestionRequest> jsonRequestBody = new Active<>();

  public SourceQuestionFinderSteps(
      final Active<ResultActions> response, final PageRuleRequester requester) {
    this.response = response;
    this.requester = requester;
  }

  @Given("I create a source question request with {string}")
  public void i_create_a_source_question_request(String function) {
    jsonRequestBody.active(new SourceQuestionRequest(mappingRuleFunction(function)));
  }

  @When("I send a source question request")
  public void i_send_a_source_question_request() throws Exception {
    WaTemplate temp = mother.one();

    response.active(requester.sourceQuestionFinder(temp.getId(), jsonRequestBody.active()));
  }

  @Then("Source questions are returned")
  public void source_questions_are_returned() throws Exception {
    String res = this.response.active().andReturn().getResponse().getContentAsString();
    PagesResponse pagesResponse = mapper.readValue(res, PagesResponse.class);
    assertNotNull(pagesResponse);
  }

  private Rule.RuleFunction mappingRuleFunction(String function) {
    switch (function) {
      case "Enable":
        return Rule.RuleFunction.ENABLE;
      case "Disable":
        return Rule.RuleFunction.DISABLE;
      case "Date":
        return Rule.RuleFunction.DATE_COMPARE;
      case "Require":
        return Rule.RuleFunction.REQUIRE_IF;
      case "Hide":
        return Rule.RuleFunction.HIDE;
      case "Unhide":
        return Rule.RuleFunction.UNHIDE;
      default:
        return Rule.RuleFunction.ENABLE;
    }
  }
}
