package gov.cdc.nbs.questionbank.question;


import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest.FieldName;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;


public class ValidateQuestionSteps {

  @Autowired
  private ExceptionHolder exceptionHolder;

  @Autowired
  private QuestionControllerHelper controllerHelper;

  @Autowired
  private QuestionMother questionMother;

  QuestionValidationRequest request;

  private boolean validationResult;


  @When("I validate unique field {string}")
  public void i_validate_unique_field(String field) {
    try {
      request = prepareRequest(field);
      validationResult = controllerHelper.validate(request);
    } catch (UniqueQuestionException e) {
      exceptionHolder.setException(e);
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("return valid")
  public void return_valid() {
    assertTrue(validationResult);
  }

  @Then("return not valid")
  public void return_not_valid() {
    assertFalse(validationResult);
  }

  @Then("a validate unique question exception is thrown")
  public void a_validate_unique_question_exception_is_thrown() {
    assertNotNull(exceptionHolder.getException());
    assertTrue(exceptionHolder.getException() instanceof UniqueQuestionException);
    assertEquals("invalid unique field name", exceptionHolder.getException().getMessage());
  }

  private QuestionValidationRequest prepareRequest(String field) {
    if (field.equals(FieldName.UNIQUE_ID.getValue()))
      return new QuestionValidationRequest(field, "TEST9900001");
    if (field.equals(FieldName.UNIQUE_NAME.getValue()))
      return new QuestionValidationRequest(field, "Text Question Unique Name");
    else // invalid unique field name
      return new QuestionValidationRequest(field, "any value");
  }

}
