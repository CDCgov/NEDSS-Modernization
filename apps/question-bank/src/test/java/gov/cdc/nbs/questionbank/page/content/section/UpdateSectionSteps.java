package gov.cdc.nbs.questionbank.page.content.section;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class UpdateSectionSteps {

    private final SectionController sectionController;

    private final WaUiMetadataRepository waUiMetadataRepository;

    private final PageMother pageMother;

    private final ExceptionHolder exceptionHolder;

    private final UserDetailsProvider user;

    private WaUiMetadata sectionToUpdate;

    UpdateSectionSteps(
        final SectionController sectionController, WaUiMetadataRepository waUiMetadataRepository,
        PageMother pageMother, ExceptionHolder exceptionHolder, UserDetailsProvider user) {
        this.sectionController = sectionController;
        this.waUiMetadataRepository = waUiMetadataRepository;
        this.pageMother = pageMother;
        this.exceptionHolder = exceptionHolder;
        this.user = user;
    }

    @Given("I send an update section request")
    public void i_send_an_update_section_request() {
        WaTemplate page = pageMother.one();

        List<WaUiMetadata> sections = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1015L)
                .toList();

        sectionToUpdate = sections.getLast();

        try {
            sectionController.updateSection(
                    page.getId(),
                    sectionToUpdate.getId(),
                    new UpdateSectionRequest("Updated Name", false),
                    user.getCurrentUserDetails());
        } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the section is updated")
    public void the_section_is_updated() {
        assertNotNull(sectionToUpdate);
        WaUiMetadata updatedSection = waUiMetadataRepository.findById(sectionToUpdate.getId()).orElseThrow();

        assertEquals("Updated Name", updatedSection.getQuestionLabel());
        assertEquals("F", updatedSection.getDisplayInd());
    }
}
