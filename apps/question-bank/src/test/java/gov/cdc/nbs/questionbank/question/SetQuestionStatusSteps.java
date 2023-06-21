package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.QuestionStatusRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SetQuestionStatusSteps {

    @Autowired
    private QuestionController controller;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private WaQuestionRepository repository;

    @Autowired
    private QuestionMother questionMother;


    @Given("A text question exists")
    public void a_question_exists() {
        questionMother.clean();
        questionMother.textQuestion();
    }

    @When("I update a question's status to {string}")
    public void I_update_a_questions_status(String status) {
        WaQuestion question = questionMother.one();
        try {
            controller.setQuestionStatus(question.getId(),
                    new QuestionStatusRequest("Active".equals(status) ? true : false));
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the question's status is set to {string}")
    public void the_question_status_is_set_to(String status) {
        WaQuestion originalQuestion = questionMother.one();
        WaQuestion updatedQuestion = repository.findById(originalQuestion.getId()).get();
        assertEquals(status, updatedQuestion.getRecordStatusCd());
        assertTrue(originalQuestion.getRecordStatusTime().compareTo(updatedQuestion.getRecordStatusTime()) < 0);
    }
}
