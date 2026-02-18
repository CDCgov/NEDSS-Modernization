package gov.cdc.nbs.questionbank.question;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.question.WaQuestionHist;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.QuestionOid;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionHistRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.UpdateQuestion;
import gov.cdc.nbs.questionbank.question.request.update.UpdateQuestionRequest;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import gov.cdc.nbs.questionbank.support.QuestionRequestMother;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuestionUpdaterTest {

  @Mock private WaQuestionRepository repository;

  @Mock private WaQuestionHistRepository histRepository;

  @Mock private QuestionManagementUtil managementUtil;

  @Mock private WaUiMetadataRepository metadatumRepository;

  @Spy private QuestionMapper questionMapper = new QuestionMapper();

  @InjectMocks private QuestionUpdater updater;

  @Test
  void should_set_status_inactive() {
    // given an active question and a working repository
    WaQuestion empty = emptyQuestion();
    when(repository.findById(321L)).thenReturn(Optional.of(empty));
    ArgumentCaptor<WaQuestion> captor = ArgumentCaptor.forClass(WaQuestion.class);
    when(repository.save(captor.capture())).thenAnswer(q -> q.getArgument(0));

    // when a set status to inactive request is processed
    updater.setStatus(9L, 321L, false);

    // then the question should have inactive status
    WaQuestion question = captor.getValue();
    assertNotNull(question);
    assertEquals("Inactive", question.getRecordStatusCd());
  }

  @Test
  void should_increment_version() {
    // given an active question and a working repository
    WaQuestion empty = emptyQuestion();
    when(repository.findById(321L)).thenReturn(Optional.of(empty));
    ArgumentCaptor<WaQuestion> captor = ArgumentCaptor.forClass(WaQuestion.class);
    when(repository.save(captor.capture())).thenAnswer(q -> q.getArgument(0));

    // when a set status to inactive request is processed
    updater.setStatus(9L, 321L, false);

    // then the question should have incremented version control number
    WaQuestion question = captor.getValue();
    assertNotNull(question);
    assertEquals(2, question.getVersionCtrlNbr().intValue());
  }

  @Test
  void should_set_status_active() {
    // given an active question and a working repository
    WaQuestion empty = emptyQuestion();
    when(repository.findById(321L)).thenReturn(Optional.of(empty));
    ArgumentCaptor<WaQuestion> captor = ArgumentCaptor.forClass(WaQuestion.class);
    when(repository.save(captor.capture())).thenAnswer(q -> q.getArgument(0));

    // when a set status to inactive request is processed
    updater.setStatus(9L, 321L, true);

    verify(histRepository).save(any(WaQuestionHist.class));

    // then the question should have inactive status
    WaQuestion question = captor.getValue();
    assertNotNull(question);
    assertEquals("Active", question.getRecordStatusCd());
  }

  @Test
  void should_throw_not_found() {
    // given no question exists for id

    // when a set status request is processed, then a QuestionNotFoundException should be thrown
    assertThrows(QuestionNotFoundException.class, () -> updater.setStatus(1L, 222L, false));
  }

  @Test
  void should_not_allow_update_inactive() {
    // given an update request
    UpdateQuestionRequest request = QuestionRequestMother.updateNumericQuestionRequest();

    // and an existing question
    WaQuestion inactive = inactiveQuestion();
    when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(inactive));

    // when i send an update then an exception is thrown
    assertThrows(UpdateQuestionException.class, () -> updater.update(1L, 2L, request));
  }

  @Test
  // Allow more than 25 assertions
  @SuppressWarnings("squid:S5961")
  void should_convert_to_update_text_question() {
    // given an update request
    UpdateQuestion request =
        questionMapper.toUpdateQuestion(QuestionRequestMother.updateTextQuestionRequest());

    // and a valid oid
    when(managementUtil.getQuestionOid(true, "PH_ACCEPTAPPLICATION", null))
        .thenReturn(new QuestionOid("oid", "oid system"));

    // and an existing question
    TextQuestionEntity question = QuestionEntityMother.textQuestion();

    // when a request is converted to a command
    QuestionCommand.Update update = updater.asUpdate(1L, request, false, question);

    // then the proper values are set
    QuestionCommand.UpdatableQuestionData data = update.questionData();
    assertEquals(request.getUniqueName(), data.uniqueName());
    assertEquals(request.getDescription(), data.description());
    assertEquals(request.getLabel(), data.label());
    assertEquals(request.getTooltip(), data.tooltip());
    assertEquals(request.getDisplayControl(), data.displayControl());
    assertEquals(request.getAdminComments(), data.adminComments());
    assertEquals("oid", data.questionOid().oid());
    assertEquals("oid system", data.questionOid().system());
    assertEquals(request.getDefaultValue(), update.defaultValue());

    QuestionCommand.ReportingData reportingData = update.reportingData();
    assertEquals(
        question.getSubGroupNm() + "_" + request.getRdbColumnName(), reportingData.rdbColumnName());
    assertEquals(request.getDefaultLabelInReport(), reportingData.reportLabel());
    assertEquals(request.getDataMartColumnName(), reportingData.dataMartColumnName());

    QuestionCommand.MessagingData messagingData = update.messagingData();
    assertEquals(request.isIncludedInMessage(), messagingData.includedInMessage());
    assertEquals(request.getMessageVariableId(), messagingData.messageVariableId());
    assertEquals(request.getLabelInMessage(), messagingData.labelInMessage());
    assertEquals(request.getCodeSystem(), messagingData.codeSystem());
    assertEquals(request.isRequiredInMessage(), messagingData.requiredInMessage());
    assertEquals(request.getHl7DataType(), messagingData.hl7DataType());
  }

  @Test
  void should_convert_to_update_date_question() {
    UpdateQuestion request =
        questionMapper.toUpdateQuestion(QuestionRequestMother.updateDateQuestionRequest());

    // and a valid oid
    when(managementUtil.getQuestionOid(true, "PH_ACCEPTAPPLICATION", null))
        .thenReturn(new QuestionOid("oid", "oid system"));

    DateQuestionEntity question = QuestionEntityMother.dateQuestion();

    QuestionCommand.Update update = updater.asUpdate(1L, request, false, question);
    QuestionCommand.UpdatableQuestionData data = update.questionData();
    assertEquals(request.getUniqueName(), data.uniqueName());
    assertEquals(request.getDescription(), data.description());
    assertEquals(request.getLabel(), data.label());
    assertEquals(request.getTooltip(), data.tooltip());
    assertEquals(request.getDisplayControl(), data.displayControl());
    assertEquals(request.isAllowFutureDates(), update.allowFutureDates());
  }

  @Test
  void should_convert_to_update_coded_Question() {
    UpdateQuestion request =
        questionMapper.toUpdateQuestion(QuestionRequestMother.updateCodedQuestionRequest());

    // and a valid oid
    when(managementUtil.getQuestionOid(true, "PH_ACCEPTAPPLICATION", null))
        .thenReturn(new QuestionOid("oid", "oid system"));

    CodedQuestionEntity question = QuestionEntityMother.codedQuestion();

    QuestionCommand.Update update = updater.asUpdate(1L, request, false, question);
    QuestionCommand.UpdatableQuestionData data = update.questionData();
    assertEquals(request.getUniqueName(), data.uniqueName());
    assertEquals(request.getDescription(), data.description());
    assertEquals(request.getLabel(), data.label());
    assertEquals(request.getTooltip(), data.tooltip());
    assertEquals(request.getDisplayControl(), data.displayControl());
    assertEquals(request.getDefaultValue(), update.defaultValue());
  }

  @Test
  void should_convert_to_update_numeric_Question() {
    UpdateQuestion request =
        questionMapper.toUpdateQuestion(QuestionRequestMother.updateNumericQuestionRequest());

    // and a valid oid
    when(managementUtil.getQuestionOid(true, "PH_ACCEPTAPPLICATION", null))
        .thenReturn(new QuestionOid("oid", "oid system"));

    NumericQuestionEntity question = QuestionEntityMother.numericQuestion();

    QuestionCommand.Update update = updater.asUpdate(1L, request, false, question);
    QuestionCommand.UpdatableQuestionData data = update.questionData();
    assertEquals(request.getUniqueName(), data.uniqueName());
    assertEquals(request.getDescription(), data.description());
    assertEquals(request.getLabel(), data.label());
    assertEquals(request.getTooltip(), data.tooltip());
    assertEquals(request.getDisplayControl(), data.displayControl());

    assertEquals(request.getDefaultValue(), update.defaultValue());
    assertEquals(request.getMask(), update.mask());
    assertEquals(request.getFieldLength().toString(), update.fieldLength());
    assertEquals(request.getMinValue(), update.minValue());
    assertEquals(request.getMaxValue(), update.maxValue());
    assertEquals(request.getRelatedUnitsLiteral(), update.relatedUnitsLiteral());
    assertEquals(request.getRelatedUnitsValueSet(), update.relatedUnitsValueSet());
  }

  private WaQuestion emptyQuestion() {
    return QuestionEntityMother.textQuestion();
  }

  private WaQuestion inactiveQuestion() {
    TextQuestionEntity q = QuestionEntityMother.textQuestion();
    q.statusChange(new QuestionCommand.SetStatus(false, 0, Instant.now()));
    return q;
  }

  @Test
  void cant_update_type_if_inuse() {
    // given an update request
    UpdateQuestionRequest request = QuestionRequestMother.updateTextQuestionRequest();
    DateQuestionEntity spy = QuestionEntityMother.dateQuestion();
    // and an existign question
    when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(spy));

    // and a question that is in use
    when(metadatumRepository.findAllByQuestionIdentifier(spy.getQuestionIdentifier()))
        .thenReturn(Collections.singletonList(new WaUiMetadata()));

    // and the question can be saved
    when(repository.save(Mockito.any())).thenReturn(spy);

    // when i send an update
    updater.update(1L, 2L, request);

    // then set data type is not called
    verify(repository, times(0)).setDataType(Mockito.anyString(), Mockito.anyLong());
  }

  @Test
  void can_update_type_if_not_inuse() {
    // given an update request
    UpdateQuestionRequest request = QuestionRequestMother.updateTextQuestionRequest();
    // and an existign question
    DateQuestionEntity spy = QuestionEntityMother.dateQuestion();
    when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(spy));

    // and a question that is in use
    when(metadatumRepository.findAllByQuestionIdentifier(spy.getQuestionIdentifier()))
        .thenReturn(Collections.emptyList());

    // and the question can be saved
    when(repository.save(Mockito.any())).thenReturn(spy);

    // when i send an update
    updater.update(1L, 2L, request);

    // then set data type is not called
    verify(repository, times(1)).setDataType(Mockito.anyString(), Mockito.anyLong());
  }
}
