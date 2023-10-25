package gov.cdc.nbs.questionbank.question;

import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.question.WaQuestionHist;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionHistRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.UpdateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.UpdateQuestionRequest.QuestionType;

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
     * Returns the updated question
     * 
     * @param userId
     * @param questionId
     * @param active
     * @return
     */
    public Question setStatus(final Long userId, final Long questionId, final boolean active) {
        return repository.findById(questionId).map(q -> {
            createHistoryEvent(q);
            q.statusChange(asSetStatus(userId, active));
            return questionMapper.toQuestion(repository.save(q));
        }).orElseThrow(() -> new QuestionNotFoundException("Failed to find question with id: " + questionId));
    }

    private QuestionCommand.SetStatus asSetStatus(long userId, boolean active) {
        return new QuestionCommand.SetStatus(active, userId, Instant.now());
    }

    private void createHistoryEvent(WaQuestion question) {
        WaQuestionHist history = new WaQuestionHist(question);
        histRepository.save(history);
    }

    public Question update(Long userId, Long id, UpdateQuestionRequest request) {
        return repository.findById(id).map(q -> {
            // Do not allow update of inactive questions
            if (q.getRecordStatusCd().equals(WaQuestion.INACTIVE)) {
                throw new UpdateQuestionException("Unable to update an inactive question");
            }
            createHistoryEvent(q);
            return questionMapper.toQuestion(updateQuestion(q, userId, request));
        }).orElseThrow(() -> new QuestionNotFoundException(id));
    }

    private boolean checkInUse(String identifier) {
        return !uiMetadatumRepository.findAllByQuestionIdentifier(identifier).isEmpty();
    }

    private WaQuestion updateQuestion(WaQuestion question, Long userId, UpdateQuestionRequest request) {
        boolean questionInUse = checkInUse(question.getQuestionIdentifier());
        // If the new question type doesn't match the existing type, update it first
        if (!questionInUse && !question.getDataType().equals(request.type().toString())) {
            question = setDataType(question, request.type());
        }
        question.update(asUpdate(userId, request, questionInUse, question));
        return repository.save(question);
    }

    /**
     * Updates the data type of a question. This must occur as a separate query due to single table inheritance
     * 
     * @param question
     * @param type
     * @return
     */
    private WaQuestion setDataType(WaQuestion question, QuestionType type) {
        repository.setDataType(type.toString(), question.getId());
        return repository.findById(question.getId()).orElseThrow(() -> new QuestionNotFoundException(question.getId()));
    }

    QuestionCommand.Update asUpdate(
            Long userId,
            UpdateQuestionRequest request,
            boolean isInUse,
            WaQuestion question) {
        return new QuestionCommand.Update(
                asQuestionData(request, isInUse, question.getQuestionType()),
                request.defaultValue(),
                request.mask(),
                request.fieldLength(),
                request.allowFutureDates(),
                request.minValue(),
                request.maxValue(),
                request.valueSet(),
                request.unitType(),
                request.unitValue(),
                asReportingData(request, question.getSubGroupNm()),
                asMessagingData(request),
                userId,
                Instant.now());
    }

    QuestionCommand.UpdatableQuestionData asQuestionData(UpdateQuestionRequest request, boolean isInUse,
            String questionType) {
        return new QuestionCommand.UpdatableQuestionData(
                isInUse,
                request.uniqueName(),
                request.description(),
                request.label(),
                request.tooltip(),
                request.displayControl(),
                request.adminComments(),
                managementUtil.getQuestionOid(
                        request.includedInMessage(),
                        request.codeSystem(),
                        questionType));
    }

    QuestionCommand.ReportingData asReportingData(UpdateQuestionRequest request, String subgroup) {
        return new QuestionCommand.ReportingData(
                request.defaultLabelInReport(),
                null, // never editable
                subgroup + "_" + request.rdbColumnName(),
                request.datamartColumnName());
    }

    QuestionCommand.MessagingData asMessagingData(UpdateQuestionRequest request) {
        return new QuestionCommand.MessagingData(
                request.includedInMessage(),
                request.messageVariableId(),
                request.labelInMessage(),
                request.codeSystem(),
                request.requiredInMessage(),
                request.hl7DataType());
    }

}
