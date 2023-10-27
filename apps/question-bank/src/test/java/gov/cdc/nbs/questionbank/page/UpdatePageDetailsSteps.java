package gov.cdc.nbs.questionbank.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.page.request.UpdatePageDetailsRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdatePageDetailsSteps {

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private PageController pageController;

    private PageSummary response;

    @When("I send an update page details request")
    public void send_an_update_page_details_request() {
        WaTemplate page = pageMother.one();
        try {
            response = pageController.updatePageDetails(page.getId(), updateRequest());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the page details are updated")
    public void the_page_details_are_udpated() {
        assertNotNull(response);
        UpdatePageDetailsRequest request = updateRequest();
        assertEquals(request.name(), response.name());
    }

    private UpdatePageDetailsRequest updateRequest() {
        return new UpdatePageDetailsRequest(
                "updated name",
                "updated mmg",
                "updated dmart",
                "updated description",
                Collections.singleton("updated condition"));
    }

}
