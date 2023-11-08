package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
public class HyperLinkSteps {
    @Autowired
    private StaticRequest request;

    @Autowired
    private PageMother mother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private final Active<ResultActions> response = new Active<>();
    private final Active<StaticContentRequests.AddHyperlink> jsonRequestBody = new Active<>();
    private final Active<WaTemplate> currPage = new Active<>();


    @Given("I create a hyperlink request with {string} and {string}")
    public void i_create_a_hyperlink_request(String label, String link) {
        WaTemplate temp = mother.one();
        WaUiMetadata subsection = temp.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();

        currPage.active(temp);

        jsonRequestBody.active(
                new StaticContentRequests.AddHyperlink(
                        label,
                        link,
                        null,
                        subsection.getId()));

    }


    @When("I send a hyperlink request")
    public void i_send_a_hyperlink_request() {
        try {
            this.response.active(request.hyperlinkRequest(
                    currPage.active().getId(),
                    this.jsonRequestBody.active()));
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("a hyperlink is created")
    public void a_hyperlink_is_created() {
        try {
            this.response.active()
                    .andExpect(jsonPath("$.componentId").isNumber());
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }
}
