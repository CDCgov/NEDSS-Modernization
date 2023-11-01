package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.lu.an;

@Transactional
public class LineSeparatorSteps {

    @Autowired
    private PageMother mother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private StaticRequest request;

    private final Active<ResultActions> response = new Active<>();
    private final Active<StaticContentRequests.AddDefault> jsonRequestBody = new Active<>();
    private final Active<WaTemplate> currPage = new Active<>();

    @Given("I create an add line separator request with {string}")
    public void i_create_an_add_line_separator_request(String comments) {
        WaTemplate temp = mother.one();
        WaUiMetadata subsection = temp.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();

        currPage.active(temp);
        jsonRequestBody.active(new StaticContentRequests.AddDefault(comments, subsection.getId()));
    }

    @When("I send an add line separator request")
    public void i_send_an_add_line_separator_request() {
        try {
            response.active(request.lineSeparatorRequest(
                    currPage.active().getId(),
                    jsonRequestBody.active()));
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("an illegal state exception is thrown")
    public void an_illegal_state_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof IllegalStateException);
    }

    @Then("a line separator is created")
    public void a_line_separator_is_created() {
        try {
            this.response.active()
                    .andExpect(jsonPath("$.componentId").isNumber());
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }
}
