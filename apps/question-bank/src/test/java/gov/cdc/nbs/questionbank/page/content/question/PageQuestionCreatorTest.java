package gov.cdc.nbs.questionbank.page.content.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;

@ExtendWith(MockitoExtension.class)
class PageQuestionCreatorTest {

  @Mock
  private EntityManager entityManager;

  @Mock
  private WaUiMetadataCreator uiCreator;

  @InjectMocks
  private PageQuestionCreator creator;

  @Test
  void should_add_question_to_page() {
    // Given a page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);

    // And a question id
    List<Long> questions = Collections.singletonList(3l);

    // And a work uiCreator
    WaUiMetadata metadata = new WaUiMetadata();
    metadata.setId(97l);
    when(uiCreator.createUiMetadata(questions, page, 2l, 3l))
        .thenReturn(Collections.singletonList(metadata));

    // When a request is processed to add a question 
    AddQuestionResponse response = creator.addQuestions(
        1l,
        2l,
        new AddQuestionRequest(questions),
        3l);

    // Then the question is created
    verify(uiCreator).createUiMetadata(questions, page, 2l, 3l);
    assertEquals(97l, response.ids().get(0).longValue());
    assertEquals(1, response.ids().size());
  }

  @Test
  void should_not_add_missing_page() {
    // Given a page does not exist
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // And a request to add a question
    AddQuestionRequest request = new AddQuestionRequest(Collections.singletonList(1l));

    // When a request is processed to add a question 
    // Then an exception is thrown
    assertThrows(AddQuestionException.class, () -> creator.addQuestions(
        1l,
        2l,
        request,
        3l));
  }

  @Test
  void should_not_add_bad_request() {
    // Given a null request to add a question
    AddQuestionRequest request = null;

    // When a request is processed to add a question 
    // Then an exception is thrown
    assertThrows(AddQuestionException.class, () -> creator.addQuestions(
        1l,
        2l,
        request,
        3l));
  }


}
