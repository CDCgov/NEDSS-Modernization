package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

@Transactional
public class UpdateSectionSteps {
    @Autowired
    private SectionController sectionController;

    @Autowired
    private WaUiMetadataRepository waUiMetadataRepository;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private WaUiMetadata sectionToUpdate;

    @Given("I send an update section request")
    public void i_send_an_update_section_request() {
        WaTemplate page = pageMother.one();

        List<WaUiMetadata> sections = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1015l)
                .toList();

        sectionToUpdate = sections.get(sections.size() - 1);

        try {
            sectionController.updateSection(
                    page.getId(),
                    sectionToUpdate.getId(),
                    new UpdateSectionRequest("Updated Name", false));
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
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
