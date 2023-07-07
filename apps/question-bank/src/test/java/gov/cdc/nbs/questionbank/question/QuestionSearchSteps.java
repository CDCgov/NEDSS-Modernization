package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.request.FindQuestionRequest;
import gov.cdc.nbs.questionbank.question.response.GetQuestionResponse;
import gov.cdc.nbs.questionbank.question.util.QuestionSearchHolder;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class QuestionSearchSteps {

    @Autowired
    private QuestionMother questionMother;

    @Autowired
    private QuestionSearchHolder searchHolder;

    @Autowired
    private QuestionController controller;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Given("A date question exists")
    public void a_date_question_exists() {
        questionMother.dateQuestion();
    }

    @Given("I get all questions")
    public void i_get_all_questions() {
        try {
            Page<Question> results = controller.findAllQuestions(PageRequest.ofSize(20));
            searchHolder.setQuestionResults(results);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("I get a question")
    public void i_get_a_question() {
        try {
            GetQuestionResponse response = controller.getQuestion(questionMother.one().getId());
            searchHolder.setGetQuestionResponse(response);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("I search for questions")
    public void i_search_for_questions() {
        try {
            Page<Question> results = controller.findQuestions(new FindQuestionRequest(""), PageRequest.ofSize(20));
            searchHolder.setQuestionResults(results);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("questions are returned")
    public void questions_are_returned() {
        Page<Question> results = searchHolder.getQuestionResults();
        assertNotNull(results);
        assertTrue(results.getSize() > 0);

    }

    @When("I search for a question by {string}")
    public void i_search_for_question_by(String field) {
        WaQuestion searchQuestion = questionMother.one();
        String search;
        switch (field) {
            case "name":
                search = searchQuestion.getQuestionNm();
                break;
            case "id":
                search = searchQuestion.getId().toString();
                break;
            case "local id":
                search = searchQuestion.getQuestionIdentifier();
                break;
            case "label":
                search = searchQuestion.getQuestionLabel();
                break;
            default:
                throw new IllegalArgumentException("Invalid search type specified");
        }
        try {
            Page<Question> results = controller.findQuestions(new FindQuestionRequest(search), PageRequest.ofSize(20));
            searchHolder.setQuestionResults(results);
            searchHolder.setSearchQuestion(searchQuestion);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("I find the question")
    public void i_fiend_the_question_by_field() {
        WaQuestion searchQuestion = searchHolder.getSearchQuestion();
        Page<Question> searchResults = searchHolder.getQuestionResults();
        assertNotNull(searchQuestion);
        assertNotNull(searchResults);
        assertTrue(searchResults.get().anyMatch(q -> q.id() == searchQuestion.getId()));
    }

    @Then("the question is returned")
    public void the_question_is_returned() {
        GetQuestionResponse response = searchHolder.getGetQuestionResponse();
        assertEquals(questionMother.one().getId().longValue(), response.question().id());
    }
}
