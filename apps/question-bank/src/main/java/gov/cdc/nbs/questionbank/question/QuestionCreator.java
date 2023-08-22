package gov.cdc.nbs.questionbank.question;

import java.time.Instant;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.EntityType;
import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.repository.CodesetRepository;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.QuestionCreatedEventProducer;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddDateQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddNumericQuestion;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;

@Component
class QuestionCreator {

    private final IdGeneratorService idGenerator;
    private final WaQuestionRepository repository;
    private final QuestionCreatedEventProducer eventProducer;
    private final NbsConfigurationRepository configRepository;
    private final CodesetRepository codesetRepository;
    private final QuestionManagementUtil managementUtil;

    public QuestionCreator(
            IdGeneratorService idGenerator,
            WaQuestionRepository repository,
            QuestionCreatedEventProducer eventProducer,
            NbsConfigurationRepository configRepository,
            CodesetRepository codesetRepository,
            QuestionManagementUtil managementUtil) {
        this.idGenerator = idGenerator;
        this.repository = repository;
        this.eventProducer = eventProducer;
        this.configRepository = configRepository;
        this.codesetRepository = codesetRepository;
        this.managementUtil = managementUtil;
    }

    public long create(Long userId, CreateQuestionRequest request) {
        if (request instanceof CreateQuestionRequest.Text t) {
            return this.create(userId, t);
        } else if (request instanceof CreateQuestionRequest.Date d) {
            return this.create(userId, d);
        } else if (request instanceof CreateQuestionRequest.Numeric n) {
            return this.create(userId, n);
        } else if (request instanceof CreateQuestionRequest.Coded c) {
            return this.create(userId, c);
        } else {
            throw new CreateQuestionException("Failed to determine question type");
        }
    }

    public long create(Long userId, CreateQuestionRequest.Text request) {
        WaQuestion question = new TextQuestionEntity(asAdd(userId, request));
        managementUtil.verifyUnique(question);
        question = repository.save(question);
        sendCreateEvent(question.getId(), userId, question.getAddTime());
        return question.getId();
    }

    public long create(Long userId, CreateQuestionRequest.Date request) {
        WaQuestion question = new DateQuestionEntity(asAdd(userId, request));
        managementUtil.verifyUnique(question);
        question = repository.save(question);
        sendCreateEvent(question.getId(), userId, question.getAddTime());
        return question.getId();
    }

    public long create(Long userId, CreateQuestionRequest.Numeric request) {
        WaQuestion question = new NumericQuestionEntity(asAdd(userId, request));
        managementUtil.verifyUnique(question);
        question = repository.save(question);
        sendCreateEvent(question.getId(), userId, question.getAddTime());
        return question.getId();
    }

    public long create(Long userId, CreateQuestionRequest.Coded request) {
        WaQuestion question = new CodedQuestionEntity(asAdd(userId, request));
        managementUtil.verifyUnique(question);
        verifyValueSetExists(request.valueSet());
        question = repository.save(question);
        sendCreateEvent(question.getId(), userId, question.getAddTime());
        return question.getId();
    }

    void verifyValueSetExists(Long valueSet) {
        if (codesetRepository.findOneByCodeSetGroupId(valueSet).isEmpty()) {
            throw new CreateQuestionException("Unable to find ValueSet with id: " + valueSet);
        }
    }

    private void sendCreateEvent(Long id, Long user, Instant createTime) {
        eventProducer.send(new QuestionCreatedEvent(id, user, createTime));
    }

    private AddNumericQuestion asAdd(Long userId, CreateQuestionRequest.Numeric request) {
        return new QuestionCommand.AddNumericQuestion(
                request.mask(),
                request.fieldLength(),
                request.defaultValue(),
                request.minValue(),
                request.maxValue(),
                request.unitTypeCd().toString(),
                request.unitValue(),
                asQuestionData(request),
                asReportingData(request.dataMartInfo(), request.subgroup()),
                asMessagingData(request.messagingInfo()),
                userId,
                Instant.now());
    }

    private AddDateQuestion asAdd(Long userId, CreateQuestionRequest.Date request) {
        return new QuestionCommand.AddDateQuestion(
                request.mask(),
                request.allowFutureDates(),
                asQuestionData(request),
                asReportingData(request.dataMartInfo(), request.subgroup()),
                asMessagingData(request.messagingInfo()),
                userId,
                Instant.now());
    }

    QuestionCommand.AddTextQuestion asAdd(Long userId, CreateQuestionRequest.Text request) {
        return new QuestionCommand.AddTextQuestion(
                request.mask(),
                request.fieldLength(),
                request.defaultValue(),
                asQuestionData(request),
                asReportingData(request.dataMartInfo(), request.subgroup()),
                asMessagingData(request.messagingInfo()),
                userId,
                Instant.now());
    }

    QuestionCommand.AddCodedQuestion asAdd(Long userId, CreateQuestionRequest.Coded request) {
        return new QuestionCommand.AddCodedQuestion(
                request.valueSet(),
                request.defaultValue(),
                asQuestionData(request),
                asReportingData(request.dataMartInfo(), request.subgroup()),
                asMessagingData(request.messagingInfo()),
                userId,
                Instant.now());
    }

    QuestionCommand.QuestionData asQuestionData(CreateQuestionRequest request) {
        var messagingInfo = request.messagingInfo();
        return new QuestionCommand.QuestionData(
                request.codeSet(),
                getLocalId(request),
                request.uniqueName(),
                request.subgroup(),
                request.description(),
                request.label(),
                request.tooltip(),
                request.displayControl(),
                request.adminComments(),
                managementUtil.getQuestionOid(
                        messagingInfo.includedInMessage(),
                        messagingInfo.codeSystem(),
                        request.codeSet()));
    }

    QuestionCommand.ReportingData asReportingData(CreateQuestionRequest.ReportingInfo dataMartInfo, String subgroup) {
        return new QuestionCommand.ReportingData(
                dataMartInfo.reportLabel(),
                dataMartInfo.defaultRdbTableName(),
                subgroup + "_" + dataMartInfo.rdbColumnName(), // Legacy appends the subgroup to the beginning of the rdbColumnName
                dataMartInfo.dataMartColumnName());
    }

    QuestionCommand.MessagingData asMessagingData(CreateQuestionRequest.MessagingInfo messagingInfo) {
        return new QuestionCommand.MessagingData(
                messagingInfo.includedInMessage(),
                messagingInfo.messageVariableId(),
                messagingInfo.labelInMessage(),
                messagingInfo.codeSystem(),
                messagingInfo.requiredInMessage(),
                messagingInfo.hl7DataType());
    }


    /**
     * If the request is of 'LOCAL' type and no Id is specified, generate the next available Id from the database. Else,
     * return the specified request.uniqueId
     * 
     * @param request
     * @return
     */
    String getLocalId(CreateQuestionRequest request) {
        if (request.codeSet().equals("LOCAL") && (request.uniqueId() == null || request.uniqueId().isBlank())) {
            // Question Ids are a combination of the 
            // `NBS_ODSE.NBS_configuration NBS_CLASS_CODE config value + the next valid Id 
            // Ex. GA13004
            String nbsClassCode = getNbsClassCode();
            return nbsClassCode + idGenerator.getNextValidId(EntityType.NBS_QUESTION_ID_LDF).getId();
        } else {
            return request.uniqueId().trim();
        }
    }

    String getNbsClassCode() {
        return configRepository.findById("NBS_CLASS_CODE")
                .orElseThrow(() -> new CreateQuestionException("Failed to lookup NBS_CLASS_CODE"))
                .getConfigValue();
    }

}
