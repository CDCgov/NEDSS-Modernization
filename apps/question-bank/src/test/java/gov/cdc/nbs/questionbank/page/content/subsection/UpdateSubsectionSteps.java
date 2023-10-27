package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

@Transactional
public class UpdateSubsectionSteps {
    @Autowired
    private SubSectionController subsectionController;

    @Autowired
    private WaUiMetadataRepository waUiMetadataRepository;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private WaUiMetadata subsectionToUpdate;

    @Given("I send an update subsection request")
    public void i_send_an_update_subsection_request() {
        WaTemplate page = pageMother.one();

        subsectionToUpdate = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1016l)
                .findFirst()
                .orElseThrow();
        try {
            subsectionController.updateSubSection(
                    page.getId(),
                    subsectionToUpdate.getId(),
                    new UpdateSubSectionRequest("Updated Name", false));
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the subsection is updated")
    public void the_subsection_is_updated() {
        assertNotNull(subsectionToUpdate);
        WaUiMetadata updatedSubsection = waUiMetadataRepository.findById(subsectionToUpdate.getId()).orElseThrow();

        assertEquals("Updated Name", updatedSubsection.getQuestionLabel());
        assertEquals("F", updatedSubsection.getDisplayInd());
    }
}
