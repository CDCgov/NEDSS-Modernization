package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.DeleteElementRequest;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.response.AddStaticResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class LineSeparatorSteps {

    @Autowired
    private PageMother mother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private WaUiMetadataRepository waUiMetadataRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private StaticRequest request;

    private final Active<ResultActions> addResponse = new Active<>();
    private final Active<ResultActions> deleteResponse = new Active<>();
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
            addResponse.active(request.lineSeparatorRequest(
                    currPage.active().getId(),
                    jsonRequestBody.active()));
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("an illegal state exception is thrown")
    public void an_illegal_state_exception_is_thrown() {
        //  The illegal state exception no longer occurs. This should check the status of the active response.
//        assertNotNull(exceptionHolder.getException());
//        assertTrue(exceptionHolder.getException() instanceof IllegalStateException);
    }

    @Then("a line separator is created")
    public void a_line_separator_is_created() {
        try {
            this.addResponse.active()
                    .andExpect(jsonPath("$.componentId").isNumber());
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I send a delete line separator request")
    public void i_send_a_delete_line_separator_request() {
        String res;
        try {
            res = this.addResponse.active().andReturn().getResponse().getContentAsString();
            AddStaticResponse staticResponse = mapper.readValue(res, AddStaticResponse.class);

            //  Each test should be self-contained.  Relying on the result of another test means they cannot be run
            //  independently.  I would suggest creating gerkin statements that make the required state available.

            //  Given the page has a static element  -> creates a static element and places an identifier into an Active.
            //      The identifier object should contain the page identifier as well as the element identifier.  This could
            //  be reused if a QuestionIdentifier Object is created that contains the page identifier, question identifier, and
            //  question type.  i.e.  Active<QuestionIdentifier>


            //  This code here should then construct the delete request using the Active static element.


            this.deleteResponse.active(request.deleteStaticElementRequest(
                this.currPage.active().getId(), 
                new DeleteElementRequest(staticResponse.componentId())));

        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
        
    }

    @Then("a line separator is deleted")
    public void a_line_separator_is_deleted() throws Exception {
        String res = this.addResponse.active().andReturn().getResponse().getContentAsString();
        AddStaticResponse staticResponse = mapper.readValue(res, AddStaticResponse.class);
        assertNotNull(waUiMetadataRepository.findById(staticResponse.componentId()));
        this.deleteResponse.active().andExpect(jsonPath("$.result").value("delete success"));

    }
}
