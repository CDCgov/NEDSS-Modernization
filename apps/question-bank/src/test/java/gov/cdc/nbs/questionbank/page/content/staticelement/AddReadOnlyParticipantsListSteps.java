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
public class AddReadOnlyParticipantsListSteps {
    @Autowired
    private StaticRequest request;

    @Autowired
    private PageMother mother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private final Active<ResultActions> response = new Active<>();
    private final Active<StaticContentRequests.AddDefault> jsonRequestBody = new Active<>();
    private final Active<WaTemplate> currPage = new Active<>();

    @Given("I create a read only participants list request")
    public void i_create_a_read_only_participants_list_request() {
        WaTemplate temp = mother.one();
        WaUiMetadata subsection = temp.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();

        currPage.active(temp);

        jsonRequestBody.active(new StaticContentRequests.AddDefault(null, subsection.getId()));
    }

    @When("I send a read only participants list request")
    public void i_send_a_read_only_participants_list_request() {
        try {
            this.response.active(request.readOnlyParticipantsListRequest(currPage.active().getId(), jsonRequestBody.active()));
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("a read only participants list element is created")
    public void a_read_only_participants_list_element_is_created() {
        try {
            this.response.active()
                    .andExpect(jsonPath("$.componentId").isNumber());
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }
}
