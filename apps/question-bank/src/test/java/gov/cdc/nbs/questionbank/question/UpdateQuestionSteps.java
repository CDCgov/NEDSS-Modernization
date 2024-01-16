package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import gov.cdc.nbs.questionbank.question.request.update.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
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

    Long questionId;

    private UpdateTextQuestionRequest updateTextQuestionRequest;
    private UpdateCodedQuestionRequest updateCodedQuestionRequest;
    private UpdateDateQuestionRequest updateDateQuestionRequest;
    private UpdateNumericQuestionRequest updateNumericQuestionRequest;

    @When("I send an update text question request")
    public void i_send_an_text_update_question_request() {
        updateTextQuestionRequest = QuestionRequestMother.updateTextQuestionRequest();
        try {
            controller.updateTextQuestion(questionMother.one().getId(), updateTextQuestionRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the text question is updated")
    public void the_text_question_is_updated() {
        WaQuestion question = questionMother.one();
        WaQuestion actual = questionRepository.findById(question.getId()).orElseThrow();
        assertEquals(updateTextQuestionRequest.getAdminComments(), actual.getAdminComment());
    }

    @When("I send an update date question request")
    public void i_send_an_update_date_question_request() {
        updateDateQuestionRequest = QuestionRequestMother.updateDateQuestionRequest();
        try {
            controller.updateDateQuestion(questionMother.one().getId(), updateDateQuestionRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }


    @Then("the date question is updated")
    public void the_date_question_is_updated() {
        WaQuestion question = questionMother.one();
        WaQuestion actual = questionRepository.findById(question.getId()).orElseThrow();
        assertEquals(updateDateQuestionRequest.getAdminComments(), actual.getAdminComment());
    }


    @When("I send an update coded question request")
    public void i_send_an_update_coded_question_request() {
        updateCodedQuestionRequest = QuestionRequestMother.updateCodedQuestionRequest();
        try {
            controller.updateCodedQuestion(questionMother.one().getId(), updateCodedQuestionRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the coded question is updated")
    public void the_coded_question_is_updated() {
        WaQuestion question = questionMother.one();
        WaQuestion actual = questionRepository.findById(question.getId()).orElseThrow();
        assertEquals(updateCodedQuestionRequest.getAdminComments(), actual.getAdminComment());
    }


    @When("I send an update numeric question request")
    public void i_send_an_update_numeric_question_request() {
        updateNumericQuestionRequest = QuestionRequestMother.updateNumericQuestionRequest();
        try {
            controller.updateNumericQuestion(questionMother.one().getId(), updateNumericQuestionRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the numeric question is updated")
    public void the_numeric_question_is_updated() {
        WaQuestion question = questionMother.one();
        WaQuestion actual = questionRepository.findById(question.getId()).orElseThrow();
        assertEquals(updateNumericQuestionRequest.getAdminComments(), actual.getAdminComment());
    }



    @When("I send an update question request that changes the question type")
    public void i_send_a_type_update() {
        updateTextQuestionRequest = QuestionRequestMother.updateTextQuestionRequest();
        try {
            questionId = questionMother.dateQuestion().getId();
            controller.updateTextQuestion(questionId, updateTextQuestionRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the question type is updated")
    public void the_question_type_is_updated() {
        WaQuestion actual = questionRepository.findById(questionId).orElseThrow();
        assertEquals("TEXT", actual.getDataType());
    }


    @When("I send an update question request for a question that doesn't exist")
    public void i_send_an_update_for_bad_question() {
        updateTextQuestionRequest = QuestionRequestMother.updateTextQuestionRequest();
        try {
            controller.updateTextQuestion(-1234L, updateTextQuestionRequest);
        } catch (QuestionNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("a question not found exception is thrown")
    public void question_not_found_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof QuestionNotFoundException);
    }
}
