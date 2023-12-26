package gov.cdc.nbs.questionbank.page.content.question;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.exception.DeleteQuestionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @Mock
    private WaTemplate waTemplate;

    @InjectMocks
    private PageQuestionDeleter deleter;


    @Test
    void should_delete_question_from_page() {
        // Given a page
        WaTemplate page = new WaTemplate();
        page.setId(1L);
        when(entityManager.find(WaTemplate.class, 1L)).thenReturn(page);

        List<WaUiMetadata> waUiMetadataList = new ArrayList<>();
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setId(2L);
        waUiMetadata.setQuestionIdentifier("q_identifier");
        waUiMetadata.setStandardQuestionIndCd('F');
        waUiMetadata.setOrderNbr(3);
        waUiMetadata.setWaTemplateUid(page);
        waUiMetadataList.add(waUiMetadata);
        page.setUiMetadata(waUiMetadataList);

        Assertions.assertEquals(1, page.getUiMetadata().size());//before delete
        deleter.deleteQuestion(1L, 2L, 3L);
        Assertions.assertEquals(0, page.getUiMetadata().size());//after delete
    }

    @Test
    void should_not_delete_question_the_page_does_not_contain_the_question() {
        // Given a page
        WaTemplate page = new WaTemplate();
        page.setId(1L);

        WaTemplate tempPage = new WaTemplate();
        tempPage.setId(2L);
        when(entityManager.find(WaTemplate.class, 1L)).thenReturn(page);

        List<WaUiMetadata> waUiMetadataList = new ArrayList<>();
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setId(2L);
        waUiMetadata.setQuestionIdentifier("q_identifier_100");
        waUiMetadata.setWaTemplateUid(tempPage);
        waUiMetadataList.add(waUiMetadata);
        page.setUiMetadata(new ArrayList<WaUiMetadata>());

        PageContentModificationException exception =
                assertThrows(PageContentModificationException.class, () -> deleter.deleteQuestion(1L, 2L, 3L));
        Assertions.assertEquals("Unable to delete a question from a page, the page does not contain the question",
                exception.getMessage());
    }

    @Test
    void should_not_delete_question_from_page_the_question_is_standard() {
        // Given a page
        WaTemplate page = new WaTemplate();
        page.setId(1L);
        when(entityManager.find(WaTemplate.class, 1L)).thenReturn(page);

        List<WaUiMetadata> waUiMetadataList = new ArrayList<>();
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setId(2L);
        waUiMetadata.setQuestionIdentifier("q_identifier_100");
        waUiMetadata.setStandardQuestionIndCd('T');
        waUiMetadata.setWaTemplateUid(page);
        waUiMetadataList.add(waUiMetadata);
        page.setUiMetadata(waUiMetadataList);

        PageContentModificationException exception =
                assertThrows(PageContentModificationException.class, () -> deleter.deleteQuestion(1L, 2L, 3L));
        Assertions.assertEquals("Unable to delete standard question", exception.getMessage());
    }

    @Test
    void should_not_delete_question_from_page_no_page_found() {
        // Given a null page
        when(entityManager.find(WaTemplate.class, 1L)).thenReturn(null);

        // When a request is processed to delete a question , DeleteQuestionException is thrown
        assertThrows(DeleteQuestionException.class, () -> deleter.deleteQuestion(1L, 2L, 3L));
    }
}
