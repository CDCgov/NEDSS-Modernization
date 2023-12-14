package gov.cdc.nbs.questionbank.page.content.question;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.exception.DeleteQuestionException;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class PageQuestionDeleterTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PageQuestionDeleter deleter;


    @Test
    void should_delete_question_from_page() {
        // Given a page
        WaTemplate page = new WaTemplate();
        when(entityManager.find(WaTemplate.class, 1L)).thenReturn(page);

        List<WaUiMetadata> waUiMetadataList = new ArrayList<>();
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setQuestionIdentifier("q_identifier");
        waUiMetadataList.add(waUiMetadata);
        page.setUiMetadata(waUiMetadataList);

        TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
        textQuestion.setQuestionIdentifier("q_identifier");
        when(entityManager.find(WaQuestion.class, 2L)).thenReturn(textQuestion);

        Assertions.assertEquals(1, page.getUiMetadata().size());//before delete
        deleter.deleteQuestion(1L, 2L, 3L);
        Assertions.assertEquals(0, page.getUiMetadata().size());//after delete
    }

    @Test
    void should_not_delete_question_the_page_does_not_contain_the_question() {
        // Given a page
        WaTemplate page = new WaTemplate();
        when(entityManager.find(WaTemplate.class, 1L)).thenReturn(page);

        List<WaUiMetadata> waUiMetadataList = new ArrayList<>();
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setQuestionIdentifier("q_identifier_100");
        waUiMetadataList.add(waUiMetadata);
        page.setUiMetadata(waUiMetadataList);

        TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
        textQuestion.setQuestionIdentifier("q_identifier_200");
        when(entityManager.find(WaQuestion.class, 2L)).thenReturn(textQuestion);

        PageContentModificationException exception = assertThrows(PageContentModificationException.class, () -> deleter.deleteQuestion(1L, 2L, 3L));
        Assertions.assertEquals("Unable to delete a question from a page, the page does not contain the question"
                , exception.getMessage());
    }

    @Test
    void should_not_delete_question_from_page_the_question_is_standard() {
        // Given a page
        WaTemplate page = new WaTemplate();
        when(entityManager.find(WaTemplate.class, 1L)).thenReturn(page);

        List<WaUiMetadata> waUiMetadataList = new ArrayList<>();
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setQuestionIdentifier("q_identifier_100");
        waUiMetadataList.add(waUiMetadata);
        page.setUiMetadata(waUiMetadataList);

        TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
        textQuestion.setQuestionIdentifier("q_identifier_100");
        textQuestion.setStandardQuestionIndCd('T');
        when(entityManager.find(WaQuestion.class, 2L)).thenReturn(textQuestion);

        PageContentModificationException exception = assertThrows(PageContentModificationException.class, () -> deleter.deleteQuestion(1L, 2L, 3L));
        Assertions.assertEquals("Unable to delete standard question", exception.getMessage());
    }


    @Test
    void should_not_delete_question_from_page_no_page_found() {
        // Given a null page
        when(entityManager.find(WaTemplate.class, 1L)).thenReturn(null);

        // When a request is processed to delete a question , DeleteQuestionException is thrown
        assertThrows(DeleteQuestionException.class, () -> deleter.deleteQuestion(1L, 2L, 3L));
    }

    @Test
    void should_not_delete_question_from_page_no_question_found() {
        // Given a page
        WaTemplate page = Mockito.mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1L)).thenReturn(page);
        // And a null question
        when(entityManager.find(WaQuestion.class, 2L)).thenReturn(null);
        assertThrows(QuestionNotFoundException.class, () -> deleter.deleteQuestion(1L, 2L, 3L));
    }

}
