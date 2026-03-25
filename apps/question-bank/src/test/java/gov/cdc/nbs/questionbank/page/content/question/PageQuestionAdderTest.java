package gov.cdc.nbs.questionbank.page.content.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import jakarta.persistence.EntityManager;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageQuestionAdderTest {

  @Mock private EntityManager entityManager;

  @InjectMocks private PageQuestionAdder adder;

  @Test
  void should_add_question_to_page() {
    // Given a page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    WaUiMetadata meta = new WaUiMetadata();
    meta.setId(97l);
    when(page.addQuestion(Mockito.any())).thenReturn(meta);

    // And a question
    TextQuestionEntity textQuestion = QuestionEntityMother.textQuestion();
    when(entityManager.find(WaQuestion.class, 3l)).thenReturn(textQuestion);

    // And a request to add a question to the page
    AddQuestionRequest request = new AddQuestionRequest(Collections.singletonList(3l));

    // When the request is processed
    var response = adder.addQuestions(1l, 2l, request, 3l);

    // Then the question is added to the page
    ArgumentCaptor<PageContentCommand.AddQuestion> captor =
        ArgumentCaptor.forClass(PageContentCommand.AddQuestion.class);
    verify(page).addQuestion(captor.capture());
    assertEquals(
        textQuestion.getQuestionIdentifier(), captor.getValue().question().getQuestionIdentifier());
    assertEquals(2l, captor.getValue().subsection());
    assertEquals(97l, response.ids().get(0).longValue());
  }

  @Test
  void should_not_question_to_page_null_request() {
    // Given a null request
    AddQuestionRequest request = null;

    // When a request is processed to add a question
    // Then an exception is thrown
    assertThrows(AddQuestionException.class, () -> adder.addQuestions(1l, 2l, request, 3l));
  }

  @Test
  void should_not_question_to_page_no_page_found() {
    // Given a null page
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // When a request is processed to add a question
    // Then an exception is thrown
    AddQuestionRequest request = new AddQuestionRequest(Collections.singletonList(3l));
    assertThrows(PageNotFoundException.class, () -> adder.addQuestions(1l, 2l, request, 3l));
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
    AddQuestionRequest request = new AddQuestionRequest(Collections.singletonList(3l));
    assertThrows(QuestionNotFoundException.class, () -> adder.addQuestions(1l, 2l, request, 3l));
  }
}
