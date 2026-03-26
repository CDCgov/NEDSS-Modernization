package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.entity.question.*;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionHistRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.UpdateQuestion;
import gov.cdc.nbs.questionbank.question.request.update.*;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class QuestionUpdater {

  private final WaQuestionHistRepository histRepository;
  private final WaQuestionRepository repository;
  private final QuestionMapper questionMapper;
  private final WaUiMetadataRepository uiMetadatumRepository;
  private final QuestionManagementUtil managementUtil;

  public QuestionUpdater(
      WaQuestionHistRepository histRepository,
      WaQuestionRepository repository,
      QuestionMapper questionMapper,
      WaUiMetadataRepository uiMetadatumRepository,
      QuestionManagementUtil managementUtil) {
    this.histRepository = histRepository;
    this.repository = repository;
    this.questionMapper = questionMapper;
    this.uiMetadatumRepository = uiMetadatumRepository;
    this.managementUtil = managementUtil;
  }

  /**
   * Sets the status of the specified question to either 'Active' or 'Inactive'.
   *
   * <p>Returns the updated question
   *
   * @param userId
   * @param questionId
   * @param active
   * @return
   */
  public Question setStatus(final Long userId, final Long questionId, final boolean active) {
    return repository
        .findById(questionId)
        .map(
            q -> {
              createHistoryEvent(q);
              q.statusChange(asSetStatus(userId, active));
              return questionMapper.toQuestion(repository.save(q));
            })
        .orElseThrow(
            () -> new QuestionNotFoundException("Failed to find question with id: " + questionId));
  }

  private QuestionCommand.SetStatus asSetStatus(long userId, boolean active) {
    return new QuestionCommand.SetStatus(active, userId, Instant.now());
  }

  private void createHistoryEvent(WaQuestion question) {
    WaQuestionHist history = new WaQuestionHist(question);
    histRepository.save(history);
  }

  public Question update(Long userId, Long id, UpdateQuestionRequest request) {
    return repository
        .findById(id)
        .map(
            q -> {
              // Do not allow update of inactive questions
              if (q.getRecordStatusCd().equals(WaQuestion.INACTIVE)) {
                throw new UpdateQuestionException("Unable to update an inactive question");
              }
              createHistoryEvent(q);

              UpdateQuestion updateQuestion = questionMapper.toUpdateQuestion(request);
              return questionMapper.toQuestion(updateQuestion(q, userId, updateQuestion));
            })
        .orElseThrow(() -> new QuestionNotFoundException(id));
  }

  private boolean checkInUse(String identifier) {
    return !uiMetadatumRepository.findAllByQuestionIdentifier(identifier).isEmpty();
  }

  private WaQuestion updateQuestion(WaQuestion question, Long userId, UpdateQuestion request) {
    boolean questionInUse = checkInUse(question.getQuestionIdentifier());
    // If the new question type doesn't match the existing type, update it first
    if (!questionInUse && !question.getDataType().equals(request.getType())) {
      question = setDataType(question, request.getType());
    }
    question.update(asUpdate(userId, request, questionInUse, question));
    return repository.save(question);
  }

  /**
   * Updates the data type of a question. This must occur as a separate query due to single table
   * inheritance
   *
   * @param question
   * @param type
   * @return
   */
  private WaQuestion setDataType(WaQuestion question, String type) {
    repository.setDataType(type, question.getId());
    return repository
        .findById(question.getId())
        .orElseThrow(() -> new QuestionNotFoundException(question.getId()));
  }

  QuestionCommand.Update asUpdate(
      Long userId, UpdateQuestion request, boolean isInUse, WaQuestion question) {
    return new QuestionCommand.Update(
        asQuestionData(request, isInUse),
        request.getDefaultValue(),
        request.getMask(),
        request.getFieldLength() != null ? request.getFieldLength().toString() : null,
        request.isAllowFutureDates(),
        request.getValueSet(),
        request.getMinValue(),
        request.getMaxValue(),
        request.getRelatedUnitsLiteral(),
        request.getRelatedUnitsValueSet(),
        asReportingData(request, question.getSubGroupNm()),
        asMessagingData(request),
        userId,
        Instant.now());
  }

  QuestionCommand.UpdatableQuestionData asQuestionData(UpdateQuestion request, boolean isInUse) {
    return new QuestionCommand.UpdatableQuestionData(
        isInUse,
        request.getUniqueName(),
        request.getDescription(),
        request.getLabel(),
        request.getTooltip(),
        request.getDisplayControl(),
        request.getAdminComments(),
        managementUtil.getQuestionOid(
            request.isIncludedInMessage(), request.getCodeSystem(), null)); // Cannot update codeset
  }

  QuestionCommand.ReportingData asReportingData(UpdateQuestion request, String subgroup) {
    return new QuestionCommand.ReportingData(
        request.getDefaultLabelInReport(),
        null, // never editable
        subgroup + "_" + request.getRdbColumnName(),
        request.getDataMartColumnName());
  }

  QuestionCommand.MessagingData asMessagingData(UpdateQuestion request) {
    return new QuestionCommand.MessagingData(
        request.isIncludedInMessage(),
        request.getMessageVariableId(),
        request.getLabelInMessage(),
        request.getCodeSystem(),
        request.isRequiredInMessage(),
        request.getHl7DataType());
  }
}
