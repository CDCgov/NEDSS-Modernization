package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class GroupSubsectionSteps {

    @Autowired
    private SubSectionController subsectionController;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private UserDetailsProvider user;

    ResponseEntity<String> response;

    @Given("I send a group subsection request")
    public void i_send_a_group_subsection_request() {
        WaTemplate page = pageMother.one();
        WaUiMetadata section = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1015L)
                .findFirst()
                .orElseThrow();
        try {
            response = subsectionController.groupSubSection(
                    page.getId(),
                    new GroupSubSectionRequest(
                            section.getId(),
                            "BLOCK_NAME",
                            getValidBatchList()),
                    user.getCurrentUserDetails());

        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }
    @Then("the subsection is grouped")
    public void the_subsection_is_grouped() {
        assertNotNull(response);
        assertTrue(response.getBody().contains("Grouped Successfully"));
        assertEquals(200, response.getStatusCodeValue());
    }

    List<GroupSubSectionRequest.Batch> getValidBatchList() {
        List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
        batchList.add(new GroupSubSectionRequest.Batch(101l, 'Y', "header1", 25));
        batchList.add(new GroupSubSectionRequest.Batch(102l, 'Y', "header2", 75));
        return batchList;
    }
}
