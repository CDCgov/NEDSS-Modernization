package gov.cdc.nbs.questionbank.page.content.question;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequiredRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.springframework.test.web.servlet.ResultActions;

public class UpdatePageQuestionRequiredSteps {
  private final UpdatePageRequiredRequester requester;
  private final Active<ResultActions> response;
  private final PageMother pageMother;

  public UpdatePageQuestionRequiredSteps(
      final UpdatePageRequiredRequester requester,
      final Active<ResultActions> response,
      final PageMother pageMother) {
    this.requester = requester;
    this.response = response;
    this.pageMother = pageMother;
  }

  @When("I mark a question not required on a page")
  public void set_question_not_required() throws Exception {
    WaTemplate page = pageMother.one();
    List<WaUiMetadata> content = pageMother.pageContent();
    long id = content.get(content.size() - 1).getId();
    response.active(requester.send(page.getId(), id, new UpdatePageQuestionRequiredRequest(false)));
  }

  @Then("the question is not required")
  public void question_is_not_required() throws Exception {
    response.active().andExpect(status().isOk()).andExpect(jsonPath("$.required", equalTo(false)));
  }
}
