package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.page.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CreateTabSteps {

    @Autowired
    private TabController controller;

    @Autowired
    private WaUiMetadataRepository waUiMetadataRepository;

    @Autowired
    private PageMother pageMother;

    private Tab response;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private UserDetailsProvider userDetailsProvider;

    @Given("I send an add tab request with {string}")
    public void i_send_an_add_tab_request(String visibility) throws JsonProcessingException {
        WaTemplate template = pageMother.one();
        CreateTabRequest createTabRequest = new CreateTabRequest("Local Tab", visibility.equals("T"));
        try {
            response = controller.createTab(
                    template.getId(),
                    createTabRequest,
                    userDetailsProvider.getCurrentUserDetails());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the tab is created with {string}")
    public void the_tab_created_successfully(String visibility) {
        WaUiMetadata metadata = waUiMetadataRepository.findById(response.id()).orElseThrow();
        assertEquals(1010L, metadata.getNbsUiComponentUid().longValue());
        assertEquals("Local Tab", metadata.getQuestionLabel());
        assertEquals(visibility, metadata.getDisplayInd());
    }

}
