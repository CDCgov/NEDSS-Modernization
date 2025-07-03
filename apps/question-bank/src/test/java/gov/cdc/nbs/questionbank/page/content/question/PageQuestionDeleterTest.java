package gov.cdc.nbs.questionbank.page.content.question;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.question.exception.DeletePageQuestionException;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
class PageQuestionDeleterTest {

  @Mock
  private EntityManager entityManager;

  @Mock
  private JdbcTemplate template;

  @Mock
  private WaTemplate waTemplate;

  @InjectMocks
  private PageQuestionDeleter deleter;

  @Test
  void should_delete_question_from_page() {
    // Given a question
    WaUiMetadata metadata = Mockito.mock(WaUiMetadata.class);
    when(entityManager.find(WaUiMetadata.class, 2l)).thenReturn(metadata);

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

    Assertions.assertEquals(1, page.getUiMetadata().size());// before delete
    deleter.deleteQuestion(1L, 2L, 3L);
    Assertions.assertEquals(0, page.getUiMetadata().size());// after delete
  }

  @Test
  void should_not_delete_question_the_page_does_not_contain_the_question() {
    // Given a question
    WaUiMetadata metadata = Mockito.mock(WaUiMetadata.class);
    when(entityManager.find(WaUiMetadata.class, 2l)).thenReturn(metadata);

    // Given a page
    WaTemplate page = new WaTemplate();
    page.setId(1L);

    WaTemplate tempPage = new WaTemplate();
    tempPage.setId(2L);
    when(entityManager.find(WaTemplate.class, 1L)).thenReturn(page);

    page.setUiMetadata(new ArrayList<WaUiMetadata>());

    PageContentModificationException exception = assertThrows(PageContentModificationException.class,
        () -> deleter.deleteQuestion(1L, 2L, 3L));
    Assertions.assertEquals("Unable to delete a question from a page, the page does not contain the question",
        exception.getMessage());
  }

  @Test
  void should_not_delete_question_the_page_does_not_contain_the_question_id_null() {
    // Given a question
    WaUiMetadata metadata = Mockito.mock(WaUiMetadata.class);
    when(entityManager.find(WaUiMetadata.class, 2l)).thenReturn(metadata);

    // Given a page
    WaTemplate page = new WaTemplate();
    page.setId(1L);

    WaTemplate tempPage = new WaTemplate();
    tempPage.setId(2L);
    when(entityManager.find(WaTemplate.class, 1L)).thenReturn(page);

    List<WaUiMetadata> waUiMetadataList = new ArrayList<>();
    WaUiMetadata waUiMetadata = new WaUiMetadata();
    waUiMetadata.setQuestionIdentifier("q_identifier_100");
    waUiMetadata.setWaTemplateUid(tempPage);
    waUiMetadataList.add(waUiMetadata);
    page.setUiMetadata(waUiMetadataList);

    PageContentModificationException exception = assertThrows(PageContentModificationException.class,
        () -> deleter.deleteQuestion(1L, 2L, 3L));
    Assertions.assertEquals("Unable to delete a question from a page, the page does not contain the question",
        exception.getMessage());
  }

  @Test
  void should_not_delete_question_from_page_the_question_is_standard() {
    // Given a question
    WaUiMetadata metadata = Mockito.mock(WaUiMetadata.class);
    when(entityManager.find(WaUiMetadata.class, 2l)).thenReturn(metadata);

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

    PageContentModificationException exception = assertThrows(PageContentModificationException.class,
        () -> deleter.deleteQuestion(1L, 2L, 3L));
    Assertions.assertEquals("Unable to delete standard question", exception.getMessage());
  }

  @Test
  void should_not_delete_question_from_page_no_page_found() {
    // Given a question
    WaUiMetadata metadata = Mockito.mock(WaUiMetadata.class);
    when(entityManager.find(WaUiMetadata.class, 2l)).thenReturn(metadata);

    // Given a null page
    when(entityManager.find(WaTemplate.class, 1L)).thenReturn(null);

    // When a request is processed to delete a question , DeleteQuestionException is
    // thrown
    assertThrows(PageNotFoundException.class, () -> deleter.deleteQuestion(1L, 2L, 3L));
  }

  @Test
  void should_not_delete_null_question() {
    // Given a question that doesn't exist
    when(entityManager.find(WaUiMetadata.class, 2l)).thenReturn(null);

    // When a request is processed to delete a question ,
    // DeletePageQuestionException is thrown
    assertThrows(DeletePageQuestionException.class, () -> deleter.deleteQuestion(1L, 2L, 3L));
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_not_delete_question_associated_with_rule() {
    // Given a question that is associated with a rule
    WaUiMetadata metadata = Mockito.mock(WaUiMetadata.class);
    when(metadata.getQuestionIdentifier()).thenReturn("identifier2");
    when(entityManager.find(WaUiMetadata.class, 2l)).thenReturn(metadata);

    List<String> identifiersFound = new ArrayList<>();
    identifiersFound.add("identifier1,identifier2");
    when(template.query(Mockito.anyString(),
        Mockito.any(PreparedStatementSetter.class),
        Mockito.any(RowMapper.class)))
        .thenReturn(identifiersFound);

    // When a request is processed to delete a question ,
    // DeletePageQuestionException is thrown
    assertThrows(DeletePageQuestionException.class, () -> deleter.deleteQuestion(1L, 2L, 3L));
  }
}
