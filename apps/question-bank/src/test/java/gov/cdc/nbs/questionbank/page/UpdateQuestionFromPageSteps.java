package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.question.PageQuestionController;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequest;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;



import static org.junit.Assert.*;

@Transactional
public class UpdateQuestionFromPageSteps {

  @Autowired
  private ExceptionHolder exceptionHolder;

  @Autowired
  private PageQuestionController pageQuestionController;

  @Autowired
  private PageMother pageMother;

  @Autowired
  private UserDetailsProvider user;

  private PagesQuestion response;

  private UpdatePageQuestionRequest request;

  @Before
  public void clearExceptions() {
    exceptionHolder.clear();
  }

  @Given("I update a question from a page")
  public void i_update_a_question_from_a_page() {
    WaTemplate page = pageMother.one();
    WaUiMetadata waUiMetadata = page.getUiMetadata().stream().findFirst()
        .orElseThrow(() -> new PageContentModificationException("the page does not contain questions"));
    waUiMetadata.setOrderNbr(3);
    waUiMetadata.setDataType("TEXT");
    waUiMetadata.setWaTemplateUid(page);
    request = prepareUpdatePageQuestionRequest();
    try {
      response = pageQuestionController.updatePageQuestion(page.getId(), waUiMetadata.getId(), request,
          user.getCurrentUserDetails());
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("the question is updated from the page")
  public void the_question_is_updated_from_the_page() {
    assertNull(exceptionHolder.getException());
    assertEquals(request.adminComments(), response.adminComments());
    assertEquals(request.questionLabel(), response.name());
    boolean required = request.required().equals("T");
    boolean display = request.required().equals("T");
    boolean enabled = request.required().equals("T");
    assertEquals(required, response.required());
    assertEquals(display, response.display());
    assertEquals(enabled, response.enabled());
  }

  private UpdatePageQuestionRequest prepareUpdatePageQuestionRequest() {
    return new UpdatePageQuestionRequest("updatedQuestionLbl", "updatedTooltip",
        "T", "T", "T", "", "100",
        null, null, "updatedAdminComments");
  }
}
