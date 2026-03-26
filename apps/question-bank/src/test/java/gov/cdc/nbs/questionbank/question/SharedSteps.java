package gov.cdc.nbs.questionbank.question;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import gov.cdc.nbs.questionbank.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class SharedSteps {

  private final UserMother userMother;
  private final QuestionMother questionMother;
  private final ExceptionHolder exceptionHolder;

  public SharedSteps(
      final UserMother userMother,
      final QuestionMother questionMother,
      final ExceptionHolder exceptionHolder) {
    this.userMother = userMother;
    this.questionMother = questionMother;
    this.exceptionHolder = exceptionHolder;
  }

  @Given("I am an admin user")
  public void i_am_admin_user() {
    userMother.adminUser();
  }

  @Given("No questions exist")
  public void no_questions_exist() {
    questionMother.clean();
  }

  @Given("I am a user without permissions")
  public void I_am_user_without_permissions() {
    userMother.noPermissions();
  }

  @Then("a no credentials found exception is thrown")
  public void a_not_authorized_exception_is_thrown() {
    assertNotNull(exceptionHolder.getException());
    assertTrue(
        exceptionHolder.getException() instanceof AuthenticationCredentialsNotFoundException);
  }

  @Then("an accessdenied exception is thrown")
  public void a_access_denied_exception_is_thrown() {
    assertNotNull(exceptionHolder.getException());
    assertTrue(exceptionHolder.getException() instanceof AccessDeniedException);
  }
}
