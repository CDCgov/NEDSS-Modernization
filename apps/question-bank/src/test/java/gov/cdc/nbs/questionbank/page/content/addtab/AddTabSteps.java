package gov.cdc.nbs.questionbank.page.content.addtab;

import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.tab.TabController;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.CreateTabResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class AddTabSteps {

    @Autowired
    private TabController tabController;

    @Autowired
    private WaUiMetaDataRepository waUiMetadataRepository;

    @Autowired
    private PageMother pageMother;

    private CreateTabResponse response;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Given("I send an add tab request with {string}")
    public void i_send_an_add_tab_request(String visibility) {
        WaTemplate template = pageMother.one();
        CreateTabRequest createTabRequest = new CreateTabRequest("Local Tab", visibility.equals("T"));
        try {
            response = tabController.createTab(template.getId(), createTabRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the tab is created with {string}")
    public void the_tab_created_successfully(String visibility) {
        WaUiMetadata metadata = waUiMetadataRepository.findById(response.uid()).orElseThrow();
        assertEquals(1010L, metadata.getNbsUiComponentUid().longValue());
        assertEquals("Local Tab", metadata.getQuestionLabel());
        assertEquals(visibility, metadata.getDisplayInd());
    }

}
