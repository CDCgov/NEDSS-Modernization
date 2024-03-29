package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.page.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

@Transactional
public class DeleteSubsectionSteps {
    @Autowired
    private SubSectionController subsectionController;

    @Autowired
    private WaUiMetadataRepository waUiMetadataRepository;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private UserDetailsProvider user;

    private WaUiMetadata subsectionToDelete;

    @Given("I send a delete subsection request")
    public void i_send_a_delete_subsection_request() {
        WaTemplate page = pageMother.one();

        List<WaUiMetadata> subsections = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1016l)
                .toList();

        subsectionToDelete = subsections.get(subsections.size() - 1);

        try {
            subsectionController.deleteSubSection(
                    page.getId(),
                    subsectionToDelete.getId(),
                    user.getCurrentUserDetails());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }


    @Then("the subsection is deleted")
    public void the_subsection_is_deleted() {
        assertNotNull(subsectionToDelete);
        assertTrue(waUiMetadataRepository.findById(subsectionToDelete.getId()).isEmpty());
    }
}
