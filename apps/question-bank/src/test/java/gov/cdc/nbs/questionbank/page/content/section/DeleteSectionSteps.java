package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DeleteSectionSteps {
    @Autowired
    private SectionController sectionController;

    @Autowired
    private WaUiMetaDataRepository waUiMetadataRepository;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private WaUiMetadata sectionToDelete;

    @Given("I send a delete section request")
    public void i_send_a_delete_section_request() {
        WaTemplate page = pageMother.one();

        List<WaUiMetadata> sections = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1015l)
                .toList();

        // the last section is empty, so it can be deleted
        sectionToDelete = sections.get(sections.size() - 1);

        try {
            sectionController.deleteSection(page.getId(), sectionToDelete.getId());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }


    @Then("the section is deleted")
    public void the_section_is_deleted() {
        assertNotNull(sectionToDelete);
        assertTrue(waUiMetadataRepository.findById(sectionToDelete.getId()).isEmpty());
    }
}
