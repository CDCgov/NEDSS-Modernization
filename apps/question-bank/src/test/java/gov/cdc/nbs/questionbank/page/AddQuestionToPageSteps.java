package gov.cdc.nbs.questionbank.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.model.AddQuestionResponse;
import gov.cdc.nbs.questionbank.page.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class AddQuestionToPageSteps {

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private PageController pageController;

    @Autowired
    private WaUiMetadataRepository repository;

    @Autowired
    private QuestionMother questionMother;

    @Autowired
    private PageMother pageMother;

    private AddQuestionResponse response;

    @Before
    public void clearExceptions() {
        exceptionHolder.clear();
    }

    @Given("No questions are in use")
    public void no_questions_are_in_use() {
        repository.deleteAll();
    }

    @Given("I add a question to a page")
    public void i_add_a_question_to_a_page() {
        WaQuestion question = questionMother.one();
        WaTemplate page = pageMother.one();
        var request = new AddQuestionRequest(question.getId(), 1);
        try {
            response = pageController.addQuestionToPage(page.getId(), request);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the question is added to the page")
    public void the_question_is_added_to_the_page() {
        assertNull(exceptionHolder.getException());
        assertNotNull(response.componentId());
        WaUiMetadata metadata = repository.findById(response.componentId())
                .orElseThrow(() -> new RuntimeException("Failed to find inserted metadata"));
        assertEquals(1, metadata.getOrderNbr().intValue());
    }
}
