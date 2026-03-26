package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.UpdateStaticRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.response.AddStaticResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ReadOnlyParticipantsListSteps {
  private final StaticRequest request;

  private final ObjectMapper mapper;

  private final PageMother mother;

  private final ExceptionHolder exceptionHolder;

  private final Active<ResultActions> response = new Active<>();
  private final Active<ResultActions> updateResponse = new Active<>();
  private final Active<StaticContentRequests.AddDefault> jsonRequestBody = new Active<>();
  private final Active<UpdateStaticRequests.UpdateDefault> updateRequest = new Active<>();
  private final Active<WaTemplate> currPage = new Active<>();

  ReadOnlyParticipantsListSteps(
      final StaticRequest request,
      final ObjectMapper mapper,
      final PageMother mother,
      final ExceptionHolder exceptionHolder) {
    this.request = request;
    this.mapper = mapper;
    this.mother = mother;
    this.exceptionHolder = exceptionHolder;
  }

  @Given("I create a read only participants list request with {string}")
  public void i_create_a_read_only_participants_list_request(String adminComments) {
    WaTemplate temp = mother.one();
    WaUiMetadata subsection =
        temp.getUiMetadata().stream()
            .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
            .findFirst()
            .orElseThrow();

    currPage.active(temp);

    jsonRequestBody.active(new StaticContentRequests.AddDefault(adminComments, subsection.getId()));
  }

  @When("I send a read only participants list request")
  public void i_send_a_read_only_participants_list_request() {
    try {
      this.response.active(
          request.readOnlyParticipantsListRequest(
              currPage.active().getId(), jsonRequestBody.active()));
    } catch (Exception e) {
      exceptionHolder.setException(e);
    }
  }

  @When("I update a participants list with {string} of {string}")
  public void i_update_a_participants_list_of(String key, String value) {
    if (key.equals("adminComments")) {
      this.updateRequest.active(new UpdateStaticRequests.UpdateDefault(value));
    }
  }

  @Then("I send an update participants list request")
  public void i_send_an_update_participants_list_request() throws Exception {
    String res = this.response.active().andReturn().getResponse().getContentAsString();
    AddStaticResponse staticResponse = mapper.readValue(res, AddStaticResponse.class);

    this.updateResponse.active(
        request.updateParticipantsListRequest(
            updateRequest.active(), currPage.active().getId(), staticResponse.componentId()));
  }

  @Then("the participants list should have {string} of {string}")
  public void the_participants_list_should_have(String key, String value) throws Exception {
    if (key.equals("adminComments")) {
      this.updateResponse.active().andExpect(jsonPath("$.adminComments").value(value));
    }
  }

  @Then("a read only participants list element is created")
  public void a_read_only_participants_list_element_is_created() {
    try {
      this.response.active().andExpect(jsonPath("$.componentId").isNumber());
    } catch (Exception e) {
      exceptionHolder.setException(e);
    }
  }
}
