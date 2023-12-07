package gov.cdc.nbs.questionbank.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.question.PageQuestionController;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

@Transactional
public class AddQuestionToPageSteps {

  @Autowired
  private ExceptionHolder exceptionHolder;

  @Autowired
  private PageQuestionController pageQuestionController;

  @Autowired
  private WaUiMetadataRepository repository;

  @Autowired
  private QuestionMother questionMother;

  @Autowired
  private PageMother pageMother;

  @Autowired
  private UserDetailsProvider user;

  private AddQuestionResponse response;

  @Before
  public void clearExceptions() {
    exceptionHolder.clear();
  }

  @Given("I add a question to a page")
  public void i_add_a_question_to_a_page() {
    WaQuestion question = questionMother.one();
    WaTemplate page = pageMother.one();
    WaUiMetadata subsection = page.getUiMetadata().stream()
        .filter(ui -> ui.getNbsUiComponentUid() == 1016l)
        .findFirst()
        .orElseThrow();
    var request = new AddQuestionRequest(Collections.singletonList(question.getId()), subsection.getId());
    try {
      response = pageQuestionController.addQuestionToPage(page.getId(), request, user.getCurrentUserDetails());
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("the question is added to the page")
  public void the_question_is_added_to_the_page() {
    assertNull(exceptionHolder.getException());
    assertNotNull(response.ids());
    WaUiMetadata metadata = repository.findById(response.ids().get(0))
        .orElseThrow(() -> new RuntimeException("Failed to find inserted metadata"));
    assertEquals(5, metadata.getOrderNbr().intValue());
  }
}
