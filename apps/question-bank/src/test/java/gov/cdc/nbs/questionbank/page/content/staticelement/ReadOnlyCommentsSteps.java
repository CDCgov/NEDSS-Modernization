package gov.cdc.nbs.questionbank.page.content.staticelement;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.UpdateStaticRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.response.AddStaticResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
public class ReadOnlyCommentsSteps {
  private final StaticRequest request;

  private final ObjectMapper mapper;

  private final PageMother mother;

  private final ExceptionHolder exceptionHolder;

  ReadOnlyCommentsSteps(
      final StaticRequest request,
      final ObjectMapper mapper,
      final PageMother mother,
      final ExceptionHolder exceptionHolder) {
    this.request = request;
    this.mapper = mapper;
    this.mother = mother;
    this.exceptionHolder = exceptionHolder;
  }

  private final Active<ResultActions> response = new Active<>();
  private final Active<ResultActions> updateResponse = new Active<>();
  private final Active<StaticContentRequests.AddReadOnlyComments> jsonRequestBody = new Active<>();
  private final Active<UpdateStaticRequests.UpdateReadOnlyComments> updateRequest = new Active<>();
  private final Active<WaTemplate> currPage = new Active<>();

  @Before("@update_read_only_comments")
  public void reset() {
    updateRequest.active(new UpdateStaticRequests.UpdateReadOnlyComments(null, null));
  }


  @Given("I create a read only comments element request with {string}")
  public void i_create_a_read_only_comments_element_request(String comments) {
    WaTemplate temp = mother.one();
    WaUiMetadata subsection = temp.getUiMetadata().stream()
        .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
        .findFirst()
        .orElseThrow();

    currPage.active(temp);

    jsonRequestBody.active(new StaticContentRequests.AddReadOnlyComments(comments, null, subsection.getId()));
  }


  @When("I send a read only comments element request")
  public void i_send_a_read_only_comments_element_request() {
    try {
      this.response.active(
          request.readOnlyCommentsRequest(this.currPage.active().getId(), this.jsonRequestBody.active()));
    } catch (Exception e) {
      exceptionHolder.setException(e);
    }
  }

  @When("I update a read only comments with {string} of {string}")
  public void i_update_a_read_only_comments_of(String key, String value) {
    switch (key) {
      case ("commentsText") -> this.updateRequest
          .active(UpdateStaticRequestHelper.withComments(updateRequest.active(), value));
      case ("adminComments") -> this.updateRequest.active(
          UpdateStaticRequestHelper.withReadOnlyCommentsAdminComments(updateRequest.active(), value));
      default -> throw new IllegalArgumentException("Unsupported key for step");
    }
  }

  @Then("I send an update read only comments request")
  public void i_send_an_update_read_only_comments_request() throws Exception {
    String res = this.response.active().andReturn().getResponse().getContentAsString();
    AddStaticResponse staticResponse = mapper.readValue(res, AddStaticResponse.class);

    this.updateResponse.active(
        request.updateReadOnlyCommentsRequest(updateRequest.active(), currPage.active().getId(),
            staticResponse.componentId()));
  }

  @Then("the read only comments should have {string} of {string}")
  public void the_read_only_comments_should_have(String key, String value) throws Exception {
    switch (key) {
      case "commentsText" -> this.updateResponse.active().andExpect(jsonPath("$.commentsText").value(value));
      case "adminComments" -> this.updateResponse.active().andExpect(jsonPath("$.adminComments").value(value));
      default -> throw new IllegalArgumentException("Unknown comment property: " + key);
    }
  }

  @Then("a read only comments element is created")
  public void a_read_only_comments_element_is_created() {
    try {
      this.response.active()
          .andExpect(jsonPath("$.componentId").isNumber());
    } catch (Exception e) {
      exceptionHolder.setException(e);
    }
  }
}
