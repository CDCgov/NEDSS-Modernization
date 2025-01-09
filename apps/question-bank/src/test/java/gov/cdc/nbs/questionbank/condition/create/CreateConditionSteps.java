package gov.cdc.nbs.questionbank.condition.create;

import gov.cdc.nbs.questionbank.condition.ConditionController;
import gov.cdc.nbs.questionbank.condition.exception.ConditionCreateException;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.util.ConditionHolder;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.exception.BadRequestException;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.UserMother;
import gov.cdc.nbs.questionbank.support.condition.ConditionMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


public class CreateConditionSteps {

  private final UserMother userMother;

  private final ConditionController conditionController;

  private final ExceptionHolder exceptionHolder;

  private final ConditionMother conditionMother;

  private final ConditionHolder conditionHolder;

  private CreateConditionRequest request;
  private Condition response;

  CreateConditionSteps(
      final UserMother userMother,
      final ConditionController conditionController,
      final ExceptionHolder exceptionHolder,
      final ConditionMother conditionMother,
      final ConditionHolder conditionHolder
  ) {
    this.userMother = userMother;
    this.conditionController = conditionController;
    this.exceptionHolder = exceptionHolder;
    this.conditionMother = conditionMother;
    this.conditionHolder = conditionHolder;
  }


  @Given("ConditionCd already exists")
  public void a_conditioncd_already_exists() {
    try {
      userMother.adminUser();
      ConditionCode val = conditionMother.createCondition();
      conditionHolder.setConditionCode(val);
      request = new CreateConditionRequest(
          "T1234567",
          "Test1234",
          "Test1234",
          "Test",
          'Y',
          'Y',
          'Y',
          'Y',
          "Test",
          "Test");
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException | BadRequestException e) {
      exceptionHolder.setException(e);
    }
  }

  @Given("A condition name already exists")
  public void a_conditionnm_already_exists() {
    try {
      userMother.adminUser();
      ConditionCode val = conditionMother.createCondition();
      conditionHolder.setConditionCode(val);
      request = new CreateConditionRequest(
          "T1234567",
          "Test1234",
          "Sample Text",
          "Test",
          'Y',
          'Y',
          'Y',
          'Y',
          "Test",
          "Test");
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException | BadRequestException e) {
      exceptionHolder.setException(e);
    }
  }

  @Given("I am an admin user and a condition does not exist")
  public void i_am_an_admin_user_and_a_condition_does_not_exist() {
    userMother.adminUser();
    conditionHolder.setConditionCode(null);
    request = new CreateConditionRequest(
        "A1234567",
        "Test1234",
        "Sample",
        "STD",
        'Y',
        'Y',
        'Y',
        'Y',
        "Test",
        "Test");
  }

  @When("I send a create condition request")
  public void create_condition() {
    try {
      response = conditionController.createCondition(request);
      conditionHolder.setCreateConditionResponse(response);
    } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException | BadRequestException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("the condition is created")
  public void the_condition_is_created() {
    assertNull(conditionHolder.getConditionCode());
    assertNotNull(response);
  }

  @Then("A condition creation exception is thrown")
  public void a_condition_creation_exception_is_thrown() {
    assertNull(response);
    assertInstanceOf(ConditionCreateException.class, exceptionHolder.getException());
  }
}
