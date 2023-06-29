package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.QuestionType;
import gov.cdc.nbs.questionbank.question.request.UpdateQuestionRequest;
import gov.cdc.nbs.questionbank.question.util.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import gov.cdc.nbs.questionbank.support.QuestionRequestMother;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateQuestionSteps {

    @Autowired
    private QuestionController controller;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private WaQuestionRepository questionRepository;

    @Autowired
    private QuestionMother questionMother;

    private UpdateQuestionRequest request;

    @When("I send an update question request")
    public void i_send_an_update_question_request() {
        request = QuestionRequestMother.update();
        try {
            controller.updateQuestion(questionMother.one().getId(), request);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I send an update question request that changes the question type")
    public void i_send_a_type_update() {
        request = QuestionRequestMother.update(QuestionType.DATE);
        try {
            controller.updateQuestion(questionMother.one().getId(), request);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I send an update question request for a question that doesn't exist")
    public void i_send_an_update_for_bad_question() {
        request = QuestionRequestMother.update();
        try {
            controller.updateQuestion(-1234L, request);
        } catch (QuestionNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the question is updated")
    public void the_question_is_updated() {
        WaQuestion question = questionMother.one();
        WaQuestion actual = questionRepository.findById(question.getId()).orElseThrow();

        assertEquals(request.adminComments(), actual.getAdminComment());
    }

    @Then("the question type is updated")
    public void the_question_type_is_updated() {
        WaQuestion question = questionMother.one();
        WaQuestion actual = questionRepository.findById(question.getId()).orElseThrow();

        assertEquals(request.type().toString(), actual.getDataType());
    }

    @Then("a question not found exception is thrown")
    public void question_not_found_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof QuestionNotFoundException);
    }
}
