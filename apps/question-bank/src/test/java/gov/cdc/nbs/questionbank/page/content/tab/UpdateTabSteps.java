package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class UpdateTabSteps {
    @Autowired
    private TabController tabController;

    @Autowired
    private WaUiMetadataRepository waUiMetadataRepository;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private Boolean visibility = RandomUtils.nextBoolean();
    private Tab response;

    @Given("I send an update tab request")
    public void i_send_an_update_tab_request() {
        WaTemplate template = pageMother.one();
        WaUiMetadata tab = template.getUiMetadata().stream()
                .filter(t -> t.getNbsUiComponentUid() == 1010l)
                .findFirst()
                .orElseThrow();
        UpdateTabRequest request = new UpdateTabRequest("Updated tab name", visibility);
        try {
            response = tabController.updateTab(template.getId(), tab.getId(), request);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the tab is updated")
    public void the_tab_updated_successfully() {
        // response validation
        assertEquals("Updated tab name", response.name());
        assertEquals(visibility, response.visible());

        // database validation
        WaUiMetadata metadata = waUiMetadataRepository.findById(response.id()).orElseThrow();
        assertEquals(1010L, metadata.getNbsUiComponentUid().longValue());
        assertEquals("Updated tab name", metadata.getQuestionLabel());
        assertEquals(visibility ? "T" : "F", metadata.getDisplayInd());
        assertEquals(metadata.getId(), response.id());
    }

}
