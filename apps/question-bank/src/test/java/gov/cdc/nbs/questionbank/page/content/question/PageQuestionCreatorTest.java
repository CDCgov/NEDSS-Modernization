package gov.cdc.nbs.questionbank.page.content.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;

@ExtendWith(MockitoExtension.class)
class PageQuestionCreatorTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PageQuestionCreator creator;

    @Test
    void should_add_question_to_page() {
        // Given a page
        WaTemplate page = Mockito.mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);

        // And a working WaTemplate
        WaUiMetadata meta = new WaUiMetadata();
        meta.setId(97l);
        when(page.addQuestion(Mockito.any())).thenReturn(meta);

        // And a question
        TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
        when(entityManager.find(WaQuestion.class, 3l)).thenReturn(textQuestion);

        // When a request is processed to add a question 
        AddQuestionResponse response = creator.addQuestion(
                1l,
                new AddQuestionRequest(3l, 4l),
                2l);

        // Then the question is created
        ArgumentCaptor<PageContentCommand.AddQuestion> captor =
                ArgumentCaptor.forClass(PageContentCommand.AddQuestion.class);
        verify(page).addQuestion(captor.capture());
        assertEquals(textQuestion.getQuestionIdentifier(), captor.getValue().question().getQuestionIdentifier());
        assertEquals(4l, captor.getValue().subsection());
        assertEquals(97l, response.componentId().longValue());
    }

    @Test
    void should_not_question_to_page_no_page_found() {
        // Given a null page
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

        // When a request is processed to add a question 
        // Then an exception is thrown
        AddQuestionRequest request = new AddQuestionRequest(3l, 4l);
        assertThrows(AddQuestionException.class, () -> creator.addQuestion(
                1l,
                request,
                2l));
    }

    @Test
    void should_not_question_to_page_no_question_found() {
        // Given a page
        WaTemplate page = Mockito.mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);

        // And a null question
        when(entityManager.find(WaQuestion.class, 3l)).thenReturn(null);

        // When a request is processed to add a question 
        // Then an exception is thrown
        AddQuestionRequest request = new AddQuestionRequest(3l, 4l);
        assertThrows(QuestionNotFoundException.class, () -> creator.addQuestion(
                1l,
                request,
                2l));
    }

}
