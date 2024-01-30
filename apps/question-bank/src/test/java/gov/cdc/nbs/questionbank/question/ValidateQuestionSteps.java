package gov.cdc.nbs.questionbank.question;


import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
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

  QuestionValidationRequest request;

  private boolean validationResult;


  @When("I validate unique field {string}")
  public void i_validate_unique_field(String field) {
    try {
      if (field.equals(FieldName.UNIQUE_ID.getValue())) {
        request = new QuestionValidationRequest(field, "TEST9900001");
      } else {// invalid unique field name
        request = new QuestionValidationRequest(field, "value");
      }
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


}
