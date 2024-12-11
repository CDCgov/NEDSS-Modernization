package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.subsection.model.SubSection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class CreateSubsectionSteps {

    private final SubSectionController subsectionController;

    private final WaUiMetadataRepository repository;

    private final PageMother pageMother;

    private final ExceptionHolder exceptionHolder;

    private final UserDetailsProvider user;

    private SubSection subsection;

    CreateSubsectionSteps(
        final SubSectionController subsectionController,
        final WaUiMetadataRepository repository,
        final PageMother pageMother,
        final ExceptionHolder exceptionHolder,
        final UserDetailsProvider user
    ) {
        this.subsectionController = subsectionController;
        this.repository = repository;
        this.pageMother = pageMother;
        this.exceptionHolder = exceptionHolder;
        this.user = user;
    }

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
                    new CreateSubSectionRequest(
                            section.getId(),
                            "new subsection",
                            true),
                    user.getCurrentUserDetails());
        } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
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
