package gov.cdc.nbs.questionbank.question;

import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.EntityType;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.question.DateQuestion;
import gov.cdc.nbs.questionbank.entity.question.TextQuestion;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.QuestionCreatedEventProducer;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddDateQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.QuestionOid;
import gov.cdc.nbs.questionbank.question.exception.QuestionCreateException;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.Date;

@Component
class QuestionCreator {

    private final IdGeneratorService idGenerator;
    private final WaQuestionRepository repository;
    private final CodeValueGeneralRepository codeValueGeneralRepository;
    private final QuestionCreatedEventProducer eventProducer;
    private final NbsConfigurationRepository configRepository;

    public QuestionCreator(
            IdGeneratorService idGenerator,
            WaQuestionRepository repository,
            CodeValueGeneralRepository codeValueGeneralRepository,
            QuestionCreatedEventProducer eventProducer,
            NbsConfigurationRepository configRepository) {
        this.idGenerator = idGenerator;
        this.repository = repository;
        this.codeValueGeneralRepository = codeValueGeneralRepository;
        this.eventProducer = eventProducer;
        this.configRepository = configRepository;
    }

    public long create(Long userId, CreateQuestionRequest.Text request) {
        WaQuestion question = new TextQuestion(asAdd(userId, request));
        verifyUnique(question);
        question = repository.save(question);
        sendCreateEvent(question.getId(), userId, question.getAddTime());
        return question.getId();
    }

    public long create(Long userId, CreateQuestionRequest.Date request) {
        WaQuestion question = new DateQuestion(asAdd(userId, request));
        verifyUnique(question);
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
            throw new QuestionCreateException(
                    "One of the following fields was not unique: questionNm, questionIdentifier, userDefinedColmnNm, rdbColumnNm");
        }
    }

    private void sendCreateEvent(Long id, Long user, Instant createTime) {
        eventProducer.send(new QuestionCreatedEvent(id, user, createTime));
    }

    private AddDateQuestion asAdd(Long userId, Date request) {
        QuestionCommand.MessagingData messagingData = asMessagingData(request.messagingInfo());

        QuestionCommand.ReportingData dataMartData = asReportingData(request.dataMartInfo(), request.subgroup());
        return new QuestionCommand.AddDateQuestion(
                request.mask(),
                request.allowFutureDates(),
                request.codeSet(),
                getLocalId(request),
                request.uniqueName(),
                request.subgroup(),
                request.description(),
                request.label(),
                request.tooltip(),
                request.displayControl(),
                request.adminComments(),
                getQuestionOid(request),
                dataMartData,
                messagingData,
                userId,
                Instant.now());
    }

    QuestionCommand.AddTextQuestion asAdd(Long userId, CreateQuestionRequest.Text request) {
        QuestionCommand.MessagingData messagingData = asMessagingData(request.messagingInfo());

        QuestionCommand.ReportingData dataMartData = asReportingData(request.dataMartInfo(), request.subgroup());

        return new QuestionCommand.AddTextQuestion(
                request.mask(),
                request.fieldLength(),
                request.defaultValue(),
                request.codeSet(),
                getLocalId(request),
                request.uniqueName(),
                request.subgroup(),
                request.description(),
                request.label(),
                request.tooltip(),
                request.displayControl(),
                request.adminComments(),
                getQuestionOid(request),
                dataMartData,
                messagingData,
                userId,
                Instant.now());
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
                .orElseThrow(() -> new QuestionCreateException("Failed to lookup NBS_CLASS_CODE"))
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
                    .orElseThrow(() -> new QuestionCreateException(
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
