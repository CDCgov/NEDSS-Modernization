package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class LineSeparatorSteps {

    @Autowired
    private PageMother mother;

    @Autowired
    private ExceptionHolder exceptionHolder;



    @Autowired
    private StaticRequest request;

    private final Active<ResultActions> response = new Active<>();

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @When("I send an add line separator request with {string}")
    public void i_send_an_add_tab_request(String comments) {
        // create request

        WaTemplate temp = mother.one();
        WaUiMetadata subsection = temp.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();

        try {
            response.active(request.lineSeparatorRequest(
                    temp.getId(),
                    asJsonString(
                            new StaticContentRequests.AddDefault(
                                    comments,
                                    subsection.getId()))));
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (IllegalArgumentException e) {
            exceptionHolder.setException(e);
        } catch (Exception e) {
            exceptionHolder.setException(e);
            e.printStackTrace();
        }
    }

    @Then("a line separator is created")
    public void a_line_separator_is_created() {
        try {
            this.response.active()
                    .andExpect(jsonPath("$.componentId").isNumber());
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }


        // assertNotNull(lineSeparatorId);
        // WaUiMetadata lineSeparatorEnt = waUiMetaDataRepository.findById(lineSeparatorId).orElseThrow();
        // assertEquals(lineSeparatorId, lineSeparatorEnt.getId());
        // assertEquals(1012L, lineSeparatorEnt.getNbsUiComponentUid().longValue());
    }
}
