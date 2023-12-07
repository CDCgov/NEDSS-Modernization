package gov.cdc.nbs.questionbank.page.content.question;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;

@ExtendWith(MockitoExtension.class)
class WaUiMetadataCreatorTest {

  @Mock
  private EntityManager entityManager;

  @InjectMocks
  private WaUiMetadataCreator creator;

  @Test
  void should_create_ui_metadata() {
    // Given a question
    TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
    when(entityManager.find(WaQuestion.class, 3l)).thenReturn(textQuestion);

    // And a page
    WaTemplate template = Mockito.mock(WaTemplate.class);

    // When a request to add the question to the ui metadata is processed
    creator.createUiMetadata(
        Collections.singletonList(3l),
        template,
        4l,
        5l);

    // Then the question is added 
    ArgumentCaptor<PageContentCommand.AddQuestion> captor =
        ArgumentCaptor.forClass(PageContentCommand.AddQuestion.class);
    verify(template).addQuestion(captor.capture());
    assertEquals(textQuestion.getQuestionIdentifier(), captor.getValue().question().getQuestionIdentifier());
    assertEquals(4l, captor.getValue().subsection());

  }

  @Test
  void should_not_create_ui_metadata_no_question() {
    // Given a question does not exist
    when(entityManager.find(WaQuestion.class, 3l)).thenReturn(null);

    // And a request to add that question
    List<Long> questions = Collections.singletonList(3l);

    // When a request to add the question to the ui metadata is processed
    // Then an exception is thrown
    assertThrows(QuestionNotFoundException.class, () -> creator.createUiMetadata(
        questions,
        null,
        4l,
        5l));

  }
}
