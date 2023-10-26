package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.subsection.model.Subsection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubsectionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

@Transactional
public class CreateSubsectionSteps {

    @Autowired
    private SubsectionController subsectionController;

    @Autowired
    private WaUiMetadataRepository repository;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private Subsection subsection;

    @Given("I send a create subsection request")
    public void i_send_a_create_subsection_request() {
        WaTemplate page = pageMother.one();

        WaUiMetadata section = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1015L)
                .findFirst()
                .orElseThrow();
        try {
            subsection = subsectionController.createSubsection(
                    page.getId(),
                    new CreateSubsectionRequest(
                            section.getId(),
                            "new subsection",
                            true));
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }


    @Then("the subsection is created")
    public void the_subsection_is_created() {
        assertNotNull(subsection);
        assertEquals("new subsection", subsection.name());
        assertTrue(subsection.visible());

        WaUiMetadata subsectionMeta = repository.findById(subsection.id()).orElseThrow();
        assertEquals("new subsection", subsectionMeta.getQuestionLabel());
        assertEquals("T", subsectionMeta.getDisplayInd());
    }
}
