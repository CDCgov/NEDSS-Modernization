package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class AddReadOnlyCommentsSteps {
    @Autowired
    private StaticRequest request;

    @Autowired
    private PageMother mother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private final Active<ResultActions> response = new Active<>();
    private final Active<StaticContentRequests.AddReadOnlyComments> jsonRequestBody = new Active<>();
    private final Active<WaTemplate> currPage = new Active<>();

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
            this.response.active(request.readOnlyCommentsRequest(this.currPage.active().getId(), this.jsonRequestBody.active()));
        } catch (Exception e) {
            exceptionHolder.setException(e);
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
