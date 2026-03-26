package gov.cdc.nbs.questionbank.pagerules;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesSubSection;
import gov.cdc.nbs.questionbank.pagerules.request.TargetSubsectionRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TargetSubsectionFinderSteps {
  @Autowired private PageMother mother;

  @Autowired private ObjectMapper mapper;

  private final Active<ResultActions> response;
  private final PageRuleRequester requester;
  private final Active<TargetSubsectionRequest> jsonRequestBody = new Active<>();

  public TargetSubsectionFinderSteps(
      final Active<ResultActions> response, final PageRuleRequester requester) {
    this.response = response;
    this.requester = requester;
  }

  @Given("I create a target subsection request with {string}")
  public void i_create_target_subsection_request(String orderNum) {
    jsonRequestBody.active(new TargetSubsectionRequest(Integer.parseInt(orderNum), null));
  }

  @When("I send a target subsection request")
  public void i_send_a_target_subsection_request() throws Exception {
    WaTemplate temp = mother.one();
    response.active(requester.targetSubsectionFinder(temp.getId(), jsonRequestBody.active()));
  }

  @Then("Target subsections are returned")
  public void target_subsections_are_returned() throws Exception {
    String res = this.response.active().andReturn().getResponse().getContentAsString();
    List<PagesSubSection> pagesResponse =
        mapper.readValue(
            res,
            mapper.getTypeFactory().constructCollectionType(List.class, PagesSubSection.class));
    assertNotNull(pagesResponse);
  }
}
