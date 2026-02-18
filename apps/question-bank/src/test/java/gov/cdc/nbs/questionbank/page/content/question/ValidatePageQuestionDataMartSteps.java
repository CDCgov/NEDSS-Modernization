package gov.cdc.nbs.questionbank.page.content.question;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.List;
import org.springframework.test.web.servlet.ResultActions;

public class ValidatePageQuestionDataMartSteps {

  private final ValidatePageQuestionDataMartRequester requester;
  private final PageMother pageMother;
  private final Active<ResultActions> response;

  public ValidatePageQuestionDataMartSteps(
      final ValidatePageQuestionDataMartRequester requester,
      final PageMother pageMother,
      final Active<ResultActions> response) {
    this.requester = requester;
    this.pageMother = pageMother;
    this.response = response;
  }

  @Given("I send a request to validate a Data Mart Column Name for a page")
  public void send_validate_page_datamart() throws Exception {
    WaTemplate page = pageMother.one();
    List<WaUiMetadata> pageContent = pageMother.pageContent();
    WaUiMetadata question = pageContent.get(pageContent.size() - 1);
    response.active(requester.send(page.getId(), question.getId(), "SomeUnusedDmart"));
  }

  @Given("I send a request to validate a Data Mart Column Name that is already in use for a page")
  public void send_validate_page_datamart_duplicate() throws Exception {
    WaTemplate page = pageMother.one();
    List<WaUiMetadata> pageContent = pageMother.pageContent();
    WaUiMetadata question = pageContent.get(pageContent.size() - 1);
    response.active(
        requester.send(
            page.getId(),
            question.getId() + 1,
            question.getWaRdbMetadatum().getUserDefinedColumnNm()));
  }

  @Then("The validation {string}")
  public void the_validation_passes_fails(String passFail) throws Exception {
    response
        .active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isValid", equalTo(passFail.contains("pass") ? true : false)));
  }
}
