package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.exception.BadRequestException;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional
public class UnGroupSubsectionSteps {

    @Autowired
    private SubSectionController subsectionController;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private UserDetailsProvider user;


    @Given("I send a ungroup subsection request")
    public void i_send_a_un_group_subsection_request() {
        WaTemplate page = pageMother.one();

        WaUiMetadata section = page.getUiMetadata().stream()
            .filter(u -> u.getNbsUiComponentUid() == 1016l)
            .findFirst()
            .orElseThrow();
        try {
            subsectionController.unGroupSubSection(
                page.getId(),
                section.getId(),
                user.getCurrentUserDetails());

        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        } catch (BadRequestException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the subsection is ungrouped")
    public void the_subsection_is_un_grouped() {
        assertNull(exceptionHolder.getException());
    }
}
