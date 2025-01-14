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
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
public class OriginalElectronicDocListSteps {
  private final PageMother mother;

  private final ObjectMapper mapper;

  private final ExceptionHolder exceptionHolder;

  private final StaticRequest request;

  OriginalElectronicDocListSteps(
      final PageMother mother,
      final ObjectMapper mapper,
      final ExceptionHolder exceptionHolder,
      final StaticRequest request
  ) {
    this.mother = mother;
    this.mapper = mapper;
    this.exceptionHolder = exceptionHolder;
    this.request = request;
  }

  private final Active<ResultActions> response = new Active<>();
  private final Active<ResultActions> updateResponse = new Active<>();
  private final Active<StaticContentRequests.AddDefault> requestBody = new Active<>();
  private final Active<UpdateStaticRequests.UpdateDefault> updateRequest = new Active<>();
  private final Active<WaTemplate> currPage = new Active<>();

  @Given("I create an original electronic document list with {string}")
  public void i_create_an_original_electronic_document_list(String adminComments) {
    WaTemplate temp = mother.one();
    WaUiMetadata subsection = temp.getUiMetadata().stream()
        .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
        .findFirst()
        .orElseThrow();

    currPage.active(temp);

    requestBody.active(new StaticContentRequests.AddDefault(adminComments, subsection.getId()));
  }


  @When("I send a original electronic document list request")
  public void i_send_a_original_electronic_document_list_request() {
    try {
      this.response.active(request.originalElecDocListRequest(currPage.active().getId(), requestBody.active()));
    } catch (Exception e) {
      exceptionHolder.setException(e);
    }
  }

  @When("I update a electronic doc list with {string} of {string}")
  public void i_update_a_electronic_doc_list_of(String key, String value) {
    if (key.equals("adminComments")) {
      this.updateRequest.active(new UpdateStaticRequests.UpdateDefault(value));
    }
  }

  @Then("I send an update electronic doc list request")
  public void i_send_an_update_electronic_doc_list_request() throws Exception {
    String res = this.response.active().andReturn().getResponse().getContentAsString();
    AddStaticResponse staticResponse = mapper.readValue(res, AddStaticResponse.class);

    this.updateResponse.active(
        request.updateElecDocListRequest(updateRequest.active(), currPage.active().getId(),
            staticResponse.componentId()));
  }

  @Then("the electronic doc list should have {string} of {string}")
  public void the_electronic_doc_list_should_have(String key, String value) throws Exception {
    if (key.equals("adminComments")) {
      this.updateResponse.active().andExpect(jsonPath("$.adminComments").value(value));
    }
  }

  @Then("a original electronic document list element is created")
  public void a_original_electronic_document_list_element_is_created() {
    try {
      this.response.active()
          .andExpect(jsonPath("$.componentId").isNumber());
    } catch (Exception e) {
      exceptionHolder.setException(e);
    }
  }

}
