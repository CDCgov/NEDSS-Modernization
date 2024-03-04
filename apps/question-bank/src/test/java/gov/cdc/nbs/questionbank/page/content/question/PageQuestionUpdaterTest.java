package gov.cdc.nbs.questionbank.page.content.question;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.content.question.exception.UpdatePageQuestionException;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageTextQuestionRequest;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;

@ExtendWith(MockitoExtension.class)
class PageQuestionUpdaterTest {

  @Mock
  private EntityManager entityManager;
  @Mock
  private ConceptFinder conceptFinder;
  @Mock
  private EditableQuestionFinder finder;

  @InjectMocks
  private PageQuestionUpdater updater;

  @Test
  void should_validate_request() {
    // given a null request
    UpdatePageQuestionRequest request = null;

    // Then an exception will be thrown
    assertThrows(UpdatePageQuestionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_validate_request_label() {
    // given a null request
    UpdatePageQuestionRequest request = new UpdatePageTextQuestionRequest(
        null,
        "tooltip",
        false,
        false,
        false,
        1008l,
        "default",
        50,
        null,
        null,
        null);

    // Then an exception will be thrown
    assertThrows(UpdatePageQuestionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_validate_request_tooltip() {
    // given a null request
    UpdatePageQuestionRequest request = new UpdatePageTextQuestionRequest(
        "label",
        null,
        false,
        false,
        false,
        1008l,
        "default",
        50,
        null,
        null,
        null);

    // Then an exception will be thrown
    assertThrows(UpdatePageQuestionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_validate_request_datamart() {
    // given a null request
    UpdatePageQuestionRequest request = new UpdatePageTextQuestionRequest(
        "label",
        "tooltip",
        false,
        false,
        false,
        1008l,
        "default",
        50,
        null,
        new MessagingInfo(false, null, null, null, false, null),
        null);

    // Then an exception will be thrown
    assertThrows(UpdatePageQuestionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_validate_request_datamart_report_label() {
    // given a null request
    UpdatePageQuestionRequest request = new UpdatePageTextQuestionRequest(
        "label",
        "tooltip",
        false,
        false,
        false,
        1008l,
        "default",
        50,
        new ReportingInfo(null, "rdbTable", "rdb", "dmart"),
        new MessagingInfo(false, null, null, null, false, null),
        null);

    // Then an exception will be thrown
    assertThrows(UpdatePageQuestionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_validate_request_datamart_report_label_empty() {
    // given a null request
    UpdatePageQuestionRequest request = new UpdatePageTextQuestionRequest(
        "label",
        "tooltip",
        false,
        false,
        false,
        1008l,
        "default",
        50,
        new ReportingInfo("", "rdbTable", "rdb", "dmart"),
        new MessagingInfo(false, null, null, null, false, null),
        null);

    // Then an exception will be thrown
    assertThrows(UpdatePageQuestionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_validate_request_messaging() {
    // given a null request
    UpdatePageQuestionRequest request = new UpdatePageTextQuestionRequest(
        "label",
        "tooltip",
        false,
        false,
        false,
        1008l,
        "default",
        50,
        new ReportingInfo("value", "rdbTable", "rdb", "dmart"),
        null,
        null);

    // Then an exception will be thrown
    assertThrows(UpdatePageQuestionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_fail_to_find_page() {
    // given a valid request
    UpdatePageQuestionRequest request = new UpdatePageTextQuestionRequest(
        "label",
        "tooltip",
        false,
        false,
        false,
        1008l,
        "default",
        50,
        new ReportingInfo("value", "rdbTable", "rdb", "dmart"),
        new MessagingInfo(false, null, null, null, false, null),
        null);

    // and an invalid page
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // Then an exception will be thrown
    assertThrows(UpdatePageQuestionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

}
