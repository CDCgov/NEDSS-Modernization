package gov.cdc.nbs.questionbank.question;

import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.EntityType;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
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
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.QuestionOid;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;

@Component
class QuestionCreator {

    private final IdGeneratorService idGenerator;
    private final WaQuestionRepository repository;
    private final CodeValueGeneralRepository codeValueGeneralRepository;
    private final QuestionCreatedEventProducer eventProducer;
    private final NbsConfigurationRepository configRepository;
    private final CodesetRepository codesetRepository;

    public QuestionCreator(
            IdGeneratorService idGenerator,
            WaQuestionRepository repository,
            CodeValueGeneralRepository codeValueGeneralRepository,
            QuestionCreatedEventProducer eventProducer,
            NbsConfigurationRepository configRepository,
            CodesetRepository codesetRepository) {
        this.idGenerator = idGenerator;
        this.repository = repository;
        this.codeValueGeneralRepository = codeValueGeneralRepository;
        this.eventProducer = eventProducer;
        this.configRepository = configRepository;
        this.codesetRepository = codesetRepository;
    }

    public long create(Long userId, CreateQuestionRequest.Text request) {
        WaQuestion question = new TextQuestionEntity(asAdd(userId, request));
        verifyUnique(question);
        question = repository.save(question);
        sendCreateEvent(question.getId(), userId, question.getAddTime());
        return question.getId();
    }

    public long create(Long userId, CreateQuestionRequest.Date request) {
        WaQuestion question = new DateQuestionEntity(asAdd(userId, request));
        verifyUnique(question);
        question = repository.save(question);
        sendCreateEvent(question.getId(), userId, question.getAddTime());
        return question.getId();
    }

    public long create(Long userId, CreateQuestionRequest.Numeric request) {
        WaQuestion question = new NumericQuestionEntity(asAdd(userId, request));
        verifyUnique(question);
        question = repository.save(question);
        sendCreateEvent(question.getId(), userId, question.getAddTime());
        return question.getId();
    }

    public long create(Long userId, CreateQuestionRequest.Coded request) {
        WaQuestion question = new CodedQuestionEntity(asAdd(userId, request));
        verifyUnique(question);
        verifyValueSetExists(request.valueSet());
        question = repository.save(question);
        sendCreateEvent(question.getId(), userId, question.getAddTime());
        return question.getId();
    }

    /**
     * Verifies the new question will not conflict with any existing questions.
     * 
     * The following fields must be unique but are not defined as unique in the databse:
     * 
     * questionNm, questionIdentifier, userDefinedColmnNm, rdbColumnNm
     * 
     * @param question
     */
    void verifyUnique(WaQuestion question) {
        List<WaQuestion> conflicts = repository.findAllByUniqueFields(
                question.getQuestionNm(),
                question.getQuestionIdentifier(),
                question.getUserDefinedColumnNm(),
                question.getRdbColumnNm());
        if (!conflicts.isEmpty()) {
            throw new CreateQuestionException(
                    "One of the following fields was not unique: questionNm, questionIdentifier, userDefinedColmnNm, rdbColumnNm");
        }
    }

    void verifyValueSetExists(Long valueSet) {
        codesetRepository.findOneByCodeSetGroupId(valueSet)
                .orElseThrow(() -> new CreateQuestionException("Unable to find ValueSet with id: " + valueSet));
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
                getQuestionOid(request));
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
     * If the request is of 'LOCAL' type, generate the next available Id from the database. Else, return the specified
     * request.uniqueId
     * 
     * @param request
     * @return
     */
    String getLocalId(CreateQuestionRequest request) {
        if (request.codeSet().equals("LOCAL")) {
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


    QuestionOid getQuestionOid(CreateQuestionRequest request) {
        if (request.messagingInfo().includedInMessage()) {
            return codeValueGeneralRepository.findByCode(
                    request.messagingInfo().codeSystem())
                    .stream()
                    .map(cvg -> new QuestionOid(
                            cvg.getCodeDescTxt(),
                            cvg.getCodeShortDescTxt()))
                    .findFirst()
                    .orElseThrow(() -> new CreateQuestionException(
                            "Failed to find 'CODE_SYSTEM' for code: " + request.messagingInfo().codeSystem()));
        } else {
            return switch (request.codeSet()) {
                case "PHIN" -> new QuestionOid("2.16.840.1.114222.4.5.232", "PHIN Questions");
                case "LOCAL" -> new QuestionOid("L", "Local");
                default -> null;
            };
        }
    }

}
