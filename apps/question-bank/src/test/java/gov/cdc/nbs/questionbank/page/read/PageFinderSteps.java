package gov.cdc.nbs.questionbank.page.read;

import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.PageController;
import gov.cdc.nbs.questionbank.page.response.PageDetailResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import gov.cdc.nbs.questionbank.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PageFinderSteps {

	@Autowired
	private UserMother userMother;

	@Autowired
	private PageMother pageMother;

	@Autowired
	private PageController pageController;

	@Autowired
	private ExceptionHolder exceptionHolder;

	private Long pageId;

	private PageDetailResponse.PagedDetail pageDetailResponse;

	@Given("I am an admin user make a request for page details")
	public void i_am_an_admin_user_make_a_request_for_page_details() {
		userMother.adminUser();
	}

	@When("I make a request to get page details")
	public void i_make_a_request_to_get_page_details() {
		try {
			WaTemplate page = pageMother.asepticMeningitis();
			pageId = page.getId();
			pageDetailResponse = pageController.getPageDetails(pageId);
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}

	}

	@Then("Page Details are returned")
	public void page_details_are_returned() {
		assertNotNull(pageDetailResponse);
		assertNotNull(pageDetailResponse.pageTabs());
		assertNotNull(pageDetailResponse.pageTabs().get(0).tabSections());
		assertNotNull(pageDetailResponse.pageTabs().get(0).tabSections().get(0).sectionSubSections());
		assertNotNull(
				pageDetailResponse.pageTabs().get(0).tabSections().get(0).sectionSubSections().get(0).pageQuestions());
		assertNotNull(pageDetailResponse.pageRules());
		pageMother.clean();
	}

}
