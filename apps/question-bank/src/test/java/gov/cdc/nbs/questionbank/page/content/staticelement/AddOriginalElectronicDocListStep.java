package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class AddOriginalElectronicDocListStep {
    @Autowired
    private PageMother mother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private StaticRequest request;

    private final Active<ResultActions> response = new Active<>();
    private final Active<StaticContentRequests.AddDefault> requestBody = new Active<>();
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
