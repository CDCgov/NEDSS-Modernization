package gov.cdc.nbs.questionbank.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PageStateChangeSteps {
	@Autowired
	private WaTemplateRepository templateRepository;

	@Autowired
	private PageController pageController;

	@Autowired
	private ExceptionHolder exceptionHolder;

	private PageStateResponse pageStatedResponse;
	private Long requetId;

	@Given("I save as draft a page that does not exist")
	public void i_save_as_draft_a_page_that_does_not_exist() {

		try {
			requetId = 1l;
			pageStatedResponse = pageController.savePageDraft(requetId);
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}
	}
	
	@Given("I am an admin user and page exists")
	public void i_am_an_admin_user_and_page_exists() {

		try {
			
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}
	}

	@When("I save a page as draft")
	public void i_save_a_page_as_draft() {

		try {
			requetId = 1l;
			pageStatedResponse = pageController.savePageDraft(requetId);

		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}
	}

	@Then("A page state should not change")
	public void a_page_state_should__not_change() {
		assertNotNull(pageStatedResponse);
		assertEquals(HttpStatus.NOT_FOUND, pageStatedResponse.getStatus());
		assertEquals(PageConstants.SAVE_DRAFT_FAIL, pageStatedResponse.getMessage());

	}

	@Then("A page state should change")
	public void a_page_state_should_change() {
		assertNotNull(pageStatedResponse);
		assertEquals(HttpStatus.OK, pageStatedResponse.getStatus());
		assertEquals(requetId + PageConstants.SAVE_DRAFT_SUCCESS, pageStatedResponse.getMessage());
	}

}
