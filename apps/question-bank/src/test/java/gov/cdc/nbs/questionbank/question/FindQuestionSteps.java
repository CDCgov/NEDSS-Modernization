package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FindQuestionSteps {

    @Autowired
    private FindQuestionResolver resolver;

    @Autowired
    private QuestionMother questionMother;

    private Page<Questionnaire.Question> results;
    private Exception exception;

    @Given("{int} questions exist")
    public void questions_exist(int count) {
        questionMother.clean();
        for (int i = 1; i <= count; i++) {
            if (i % 4 == 0) {
                questionMother.dropdownQuestion("dropdown question label");
            } else if (i % 3 == 0) {
                questionMother.dateQuestion("date question label");
            } else if (i % 2 == 0) {
                questionMother.numericQuestion("numeric question label");
            } else {
                questionMother.textQuestion("text question label");
            }
        }
    }


    @When("I search for a question")
    public void i_search_for_a_question() {
        try {
            results = resolver.findQuestions(new QuestionFilter(""), null);
        } catch (Exception e) {
            exception = e;
        }

    }

    @When("I search for a question with page size of {int} and a page number of {int}")
    public void i_search_for_a_question_paging(int pageSize, int pageNumber) {
        results = resolver.findQuestions(new QuestionFilter(""), new QuestionPage(pageSize, pageNumber));
    }

    @Then("I find {int} questions")
    public void i_find_questions(int count) {
        assertNull(exception);
        assertNotNull(results);
        assertEquals(count, results.getContent().size());
    }

    @Then("I receive a page of {int} results with {int} total results")
    public void i_find_questions(int pageCount, int total) {
        assertNull(exception);
        assertNotNull(results);
        assertEquals(pageCount, results.getContent().size());
        assertEquals(total, results.getTotalElements());
    }

    @Then("an exception is thrown")
    public void exception_thrown() {
        assertNotNull(exception);
        assertNull(results);
    }


}
