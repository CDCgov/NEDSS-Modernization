package gov.cdc.nbs.questionbank.page;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.question.PageQuestionController;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

@Transactional
public class DeleteQuestionFromPageSteps {

  private final ExceptionHolder exceptionHolder;
  private final PageQuestionController pageQuestionController;
  private final WaUiMetadataRepository repository;
  private final PageMother pageMother;
  private final UserDetailsProvider user;

  public DeleteQuestionFromPageSteps(
      final ExceptionHolder exceptionHolder,
      final PageQuestionController pageQuestionController,
      final WaUiMetadataRepository repository,
      final PageMother pageMother,
      final UserDetailsProvider user) {
    this.exceptionHolder = exceptionHolder;
    this.pageQuestionController = pageQuestionController;
    this.repository = repository;
    this.pageMother = pageMother;
    this.user = user;
  }

  private Long questionId;

  @Before
  public void clearExceptions() {
    exceptionHolder.clear();
  }

  @Given("I delete a question from a page")
  public void i_delete_a_question_from_a_page() {
    WaTemplate page = pageMother.one();
    WaUiMetadata waUiMetadata = page.getUiMetadata().stream().findFirst()
        .orElseThrow(() -> new PageContentModificationException("the page does not contain questions"));
    waUiMetadata.setStandardQuestionIndCd('F');
    waUiMetadata.setOrderNbr(3);
    waUiMetadata.setWaTemplateUid(page);
    this.questionId = waUiMetadata.getId();

    try {
      pageQuestionController.deleteQuestion(page.getId(), waUiMetadata.getId(), user.getCurrentUserDetails());
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("the question is deleted from the page")
  public void the_question_is_deleted_from_the_page() {
    assertNull(exceptionHolder.getException());
    Optional<WaUiMetadata> metadata = repository.findById(this.questionId).stream().findFirst();
    assertTrue(metadata.isEmpty());
  }
}
