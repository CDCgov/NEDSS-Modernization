package gov.cdc.nbs.questionbank.question;


import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest.Field;
import gov.cdc.nbs.questionbank.question.response.QuestionValidationResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


public class ValidateQuestionSteps {

  private final ExceptionHolder exceptionHolder;

  private final QuestionControllerHelper controllerHelper;

  QuestionValidationRequest request;

  private QuestionValidationResponse validationResult;

  ValidateQuestionSteps(
      final ExceptionHolder exceptionHolder,
      final QuestionControllerHelper controllerHelper
  ) {
    this.exceptionHolder = exceptionHolder;
    this.controllerHelper = controllerHelper;
  }

  @When("I validate unique field {string}")
  public void i_validate_unique_field(String field) {
    try {
      request = prepareRequest(field);
      validationResult = controllerHelper.validate(request);
    } catch (UniqueQuestionException | AuthenticationCredentialsNotFoundException | AccessDeniedException e) {
      exceptionHolder.setException(e);
    }
  }

  @When("I validate rdbColumnName with invalid subgroup")
  public void i_validate_rdbColumnName_with_invalid_subgroup() {
    try {
      request = new QuestionValidationRequest(Field.RDB_COLUMN_NAME, "XXX_RDB_COL_NMM");
      validationResult = controllerHelper.validate(request);
    } catch (UniqueQuestionException | AccessDeniedException e) {
      exceptionHolder.setException(e);
    }
  }


  @Then("return valid")
  public void return_valid() {
    assertTrue(validationResult.isValid());
  }

  @Then("return not valid")
  public void return_not_valid() {
    assertFalse(validationResult.isValid());
  }

  @Then("a validate unique question exception is thrown")
  public void a_validate_unique_question_exception_is_thrown() {
    assertNotNull(exceptionHolder.getException());
    assertInstanceOf(UniqueQuestionException.class, exceptionHolder.getException());
  }

  private QuestionValidationRequest prepareRequest(String field) {
    if ("uniqueId".equals(field))
      return new QuestionValidationRequest(Field.UNIQUE_ID, "TEST9900001");
    if ("uniqueName".equals(field))
      return new QuestionValidationRequest(Field.UNIQUE_NAME, "Text Question Unique Name");
    if ("rdbColumnName".equals(field))
      return new QuestionValidationRequest(Field.RDB_COLUMN_NAME, "ADM_RDB_COL_NM");
    if ("dataMartColumnName".equals(field))
      return new QuestionValidationRequest(Field.DATA_MART_COLUMN_NAME, "DATA_MRT_COL_NM");
    else // invalid unique field name
      return new QuestionValidationRequest(null, "any value");
  }

}
