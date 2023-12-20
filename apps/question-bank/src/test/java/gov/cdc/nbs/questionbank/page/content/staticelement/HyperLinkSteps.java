package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.UpdateStaticRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.response.AddStaticResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class HyperLinkSteps {
    @Autowired
    private StaticRequest request;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PageMother mother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private final Active<ResultActions> response = new Active<>();
    private final Active<ResultActions> updateResponse = new Active<>();
    private final Active<StaticContentRequests.AddHyperlink> jsonRequestBody = new Active<>();
    private final Active<UpdateStaticRequests.UpdateHyperlink> updateRequest = new Active<>();
    private final Active<WaTemplate> currPage = new Active<>();

    @Before("@update_hyperlink")
    public void reset() {
        updateRequest.active(new UpdateStaticRequests.UpdateHyperlink(null, null, null));
    }


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

    @When("I update a hyperlink with {string} of {string}")
    public void i_update_a_hyperlink_of(String key, String value) {
        switch(key) {
            case("label") -> this.updateRequest.active(UpdateStaticRequestHelper.withLabel(this.updateRequest.active(), value));
            case("link") -> this.updateRequest.active(UpdateStaticRequestHelper.withLink(this.updateRequest.active(), value));
        }
    }

    @Then("I send an update hyperlink request")
    public void i_send_an_update_hyperlink_request() throws Exception {
        String res = this.response.active().andReturn().getResponse().getContentAsString();
        AddStaticResponse staticResponse = mapper.readValue(res, AddStaticResponse.class);

        this.updateResponse.active(
            request.updateHyperlinkRequest(updateRequest.active(), currPage.active().getId(), staticResponse.componentId()));
    }

    @Then("the hyperlink should have {string} of {string}")
    public void the_hyperlink_should_have(String key, String value) throws Exception {
        switch(key) {
            case "label" -> this.updateResponse.active().andExpect(jsonPath("$.label").isString());
            case "link" -> this.updateResponse.active().andExpect(jsonPath("$.linkUrl").isString());
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
