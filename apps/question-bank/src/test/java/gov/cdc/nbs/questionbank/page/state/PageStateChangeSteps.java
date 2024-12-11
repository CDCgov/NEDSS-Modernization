package gov.cdc.nbs.questionbank.page.state;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.PageController;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class PageStateChangeSteps {

  private final PageController pageController;

  private final PageMother pageMother;

  private final ExceptionHolder exceptionHolder;

  private PageStateResponse pageStatedResponse;
  private Long requestId;

  PageStateChangeSteps(
      final PageController pageController,
      final PageMother pageMother,
      final ExceptionHolder exceptionHolder
  ) {
    this.pageController = pageController;
    this.pageMother = pageMother;
    this.exceptionHolder = exceptionHolder;
  }

  @Given("I save as draft a page that does not exist")
  public void i_save_as_draft_a_page_that_does_not_exist() {
    try {
      requestId = 1L;
      pageStatedResponse = pageController.savePageDraft(requestId);
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException | PageUpdateException e) {
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

    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException | PageUpdateException e) {
      exceptionHolder.setException(e);
    }
  }

  @When("I save a page as draft")
  public void i_save_a_page_as_draft() {
    try {
      pageStatedResponse = pageController.savePageDraft(pageMother.one().getId());
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException | PageUpdateException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("A page update exception should be thrown")
  public void a_page_update_exception_should__be_thrown() {
    assertNotNull(exceptionHolder.getException());
    assertInstanceOf(PageUpdateException.class, exceptionHolder.getException());

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
