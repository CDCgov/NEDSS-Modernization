package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.question.PageQuestionController;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.Optional;
import static org.junit.Assert.*;

@Transactional
public class DeleteQuestionFromPageSteps {

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private PageQuestionController pageQuestionController;

    @Autowired
    private WaUiMetadataRepository repository;

    @Autowired
    private QuestionMother questionMother;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private UserDetailsProvider user;

    private Long questionId;

    @Before
    public void clearExceptions() {
        exceptionHolder.clear();
    }

    @Given("I delete a question from a page")
    public void i_delete_a_question_from_a_page() {
        WaQuestion question = questionMother.one();
        this.questionId = question.getId();
        WaTemplate page = pageMother.one();
        WaUiMetadata waUiMetadata = page.getUiMetadata().stream().findFirst()
                .orElseThrow(() -> new PageContentModificationException("the page does not contain questions"));
        waUiMetadata.setQuestionIdentifier(question.getQuestionIdentifier());
        waUiMetadata.setStandardQuestionIndCd('F');
        waUiMetadata.setOrderNbr(3);

        try {
            pageQuestionController.deleteQuestion(page.getId(), question.getId(), user.getCurrentUserDetails());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the question is deleted from the page")
    public void the_question_is_deleted_from_the_page() {
        assertNull(exceptionHolder.getException());
        Optional<WaUiMetadata> metadata = repository.findById(this.questionId).stream().findFirst();
        assertTrue(metadata.isEmpty());
    }
}
