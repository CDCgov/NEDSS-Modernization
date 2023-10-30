package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

@Transactional
public class DeleteSectionSteps {
    @Autowired
    private SectionController sectionController;

    @Autowired
    private WaUiMetadataRepository repository;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private UserDetailsProvider user;

    private WaUiMetadata sectionToDelete;

    @Given("I send a delete section request")
    public void i_send_a_delete_section_request() {
        WaTemplate page = pageMother.one();

        sectionToDelete = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1015l)
                .findFirst()
                .orElseThrow();

        try {
            sectionController.deleteSection(page.getId(), sectionToDelete.getId(), user.getCurrentUserDetails());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the section is deleted")
    public void the_section_is_deleted() {
        assertNotNull(sectionToDelete);
        assertTrue(repository.findById(sectionToDelete.getId()).isEmpty());
    }
}
