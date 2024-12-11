package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class UpdateSubsectionSteps {
    private final SubSectionController subsectionController;

    private final WaUiMetadataRepository waUiMetadataRepository;

    private final PageMother pageMother;

    private final ExceptionHolder exceptionHolder;

    private final UserDetailsProvider user;

    private WaUiMetadata subsectionToUpdate;

    UpdateSubsectionSteps(
        final SubSectionController subsectionController,
        final WaUiMetadataRepository waUiMetadataRepository,
        final PageMother pageMother,
        final ExceptionHolder exceptionHolder,
        final UserDetailsProvider user
    ) {
        this.subsectionController = subsectionController;
        this.waUiMetadataRepository = waUiMetadataRepository;
        this.pageMother = pageMother;
        this.exceptionHolder = exceptionHolder;
        this.user = user;
    }

    @Given("I send an update subsection request")
    public void i_send_an_update_subsection_request() {
        WaTemplate page = pageMother.one();

        subsectionToUpdate = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();
        try {
            subsectionController.updateSubSection(
                    page.getId(),
                    subsectionToUpdate.getId(),
                    new UpdateSubSectionRequest("Updated Name", false),
                    user.getCurrentUserDetails());
        } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
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
