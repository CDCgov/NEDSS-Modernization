package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.response.CreateQuestionResponse;
import gov.cdc.nbs.questionbank.support.QuestionRequestMother;
import gov.cdc.nbs.questionbank.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateQuestionSteps {
    @Autowired
    private UserMother userMother;

    @Autowired
    private QuestionController controller;

    @Autowired
    private WaQuestionRepository questionRepository;

    private CreateQuestionRequest.Text request;

    private CreateQuestionResponse response;

    private Exception exception;

    @Given("No questions exist")
    public void no_questions_exist() {
        questionRepository.deleteAll();
    }

    @Given("I am an admin user")
    public void i_am_admin_user() {
        userMother.adminUser();
    }

    @Given("I am a user without permissions")
    public void I_am_user_without_permissions() {
        userMother.noPermissions();
    }

    @Given("I am not logged in")
    public void i_am_not_logged_in() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @When("I send a create text question request")
    public void I_send_a_create_text_question_request() {
        request = QuestionRequestMother.localTextRequest();
        try {
            response = controller.createTextQuestion(request);
        } catch (AccessDeniedException e) {
            exception = e;
        } catch (AuthenticationCredentialsNotFoundException e) {
            exception = e;
        }
    }

    @Then("the text question is created")
    public void the_text_question_is_created() {
        assertNotNull(response);
    }

    @Then("a not authorized exception is thrown")
    public void a_not_authorized_exception_is_thrown() {
        assertNotNull(exception);
    }

}
