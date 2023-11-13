package gov.cdc.nbs.questionbank.page.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.PageController;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.page.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PageStateChangeSteps {

    @Autowired
    private PageController pageController;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private PageStateResponse pageStatedResponse;
    private Long requestId;

    @Given("I save as draft a page that does not exist")
    public void i_save_as_draft_a_page_that_does_not_exist() {
        try {
            requestId = 1l;
            pageStatedResponse = pageController.savePageDraft(requestId);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        } catch (PageUpdateException e) {
            exceptionHolder.setException(e);
        }
    }
    
	@Given("I am an admin user and page draft exists")
	public void i_am_an_admin_user_and_page_draft_exists() {
		try {
			WaTemplate origPage = pageMother.one();
			origPage.setTemplateType(PageConstants.PUBLISHED_WITH_DRAFT);
			pageMother.createPageDraft(origPage);
			requestId = origPage.getId();

		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		} catch (PageUpdateException e) {
			exceptionHolder.setException(e);
		}
	}

    @When("I save a page as draft")
    public void i_save_a_page_as_draft() {
        try {
            pageStatedResponse = pageController.savePageDraft(pageMother.one().getId());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        } catch (PageUpdateException e) {
            exceptionHolder.setException(e);
        }
    }
    
	@When("I delete a page draft")
	public void i_delete_a_page_draft() {
		try {
			pageStatedResponse = pageController.deletePageDraft(requestId);
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		} catch (PageUpdateException e) {
			exceptionHolder.setException(e);
		}
	}

    @Then("A page update exception should be thrown")
    public void a_page_update_exception_should__be_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof PageUpdateException);

    }

    @Then("A page state should change")
    public void a_page_state_should_change() {
        assertNotNull(pageStatedResponse);
        assertEquals(PageConstants.SAVE_DRAFT_SUCCESS, pageStatedResponse.getMessage());
        pageMother.clean();
    }
    
    @Then("A page draft should delete")
    public void a_page_draft_should_delete() {
    	assertNotNull(pageStatedResponse);
    	assertTrue(pageStatedResponse.getMessage().contains(PageConstants.DRAFT_DELETE_SUCCESS));
    }

}
