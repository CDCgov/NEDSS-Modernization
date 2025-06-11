package gov.cdc.nbs.questionbank.page.create;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.page.PageController;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PageCreatorSteps {

    @Autowired
    private UserMother userMother;

    @Autowired
    private PageController pageController;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private PageCreateRequest request;

    private PageCreateResponse pageCreateResponse;

    @Given("I am an admin user make an add page request")
    public void i_am_an_admin_user_make_an_add_page_request() {
        try {
            userMother.adminUser();
            pageCreateResponse = new PageCreateResponse(null, null, null);
            request = new PageCreateRequest(
                    "INV",
                    Arrays.asList("1023"),
                    "TestPage",
                    10l,
                    "HEP_Case_Map_V1.0",
                    "create page steps",
                    "dataMart");
        } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I make a request to create a Page")
    public void i_make_a_request_to_create_a_page() {
        try {
            pageCreateResponse = pageController.createPage(request);
        } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("A page is created")
    public void a_page_is_created() {
        assertNotNull(pageCreateResponse);
        assertTrue(pageCreateResponse.pageId() > 0);
        assertEquals(request.name() + PageConstants.ADD_PAGE_MESSAGE, pageCreateResponse.message());
        assertEquals(request.name(), pageCreateResponse.pageName());
    }

}
