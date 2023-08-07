package gov.cdc.nbs.questionbank.page.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import gov.cdc.nbs.questionbank.page.PageController;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PageCreatorSteps {

	@Autowired
	private PageController pageController;

	@Autowired
	private PageMother pageMother;

	@Autowired
	private ExceptionHolder exceptionHolder;

	private PageCreateRequest request;

	private PageCreateResponse pageCreateResponse;

	@Given("I am an admin user")
	public void i_am_an_admin_user() {

		try {
			request = new PageCreateRequest("INV", Set.of("1023"), "TestPage", 10l, "HEP_Case_Map_V1.0",
					"create page steps", "dataMart");
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}
	}

	@When("I make a request to create a Page")
	public void i_make_a_request_to_create_a_page() {

		try {
			pageCreateResponse = pageController.createPage(request);
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}
	}

	@Then("A page is created")
	public void a_page_is_created() {
		assertNotNull(pageCreateResponse);
		assertTrue(pageCreateResponse.getPageId() > 0);
		assertEquals(HttpStatus.CREATED, pageCreateResponse.getStatus());
		assertEquals(PageConstants.ADD_PAGE_MESSAGE, pageCreateResponse.getMessage());
		assertEquals(request.name(), pageCreateResponse.getPageName());

	}

}
