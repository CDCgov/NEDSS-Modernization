package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.EntityType;
import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.repository.CodesetRepository;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddDateQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddNumericQuestion;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.model.Question.CodedQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.DateQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.NumericQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.TextQuestion;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateCodedQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateDateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateNumericQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateTextQuestionRequest;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
class QuestionCreator {

  private final IdGeneratorService idGenerator;
  private final WaQuestionRepository repository;
  private final NbsConfigurationRepository configRepository;
  private final CodesetRepository codesetRepository;
  private final QuestionManagementUtil managementUtil;
  private final QuestionMapper questionMapper;

  public QuestionCreator(
      final IdGeneratorService idGenerator,
      final WaQuestionRepository repository,
      final NbsConfigurationRepository configRepository,
      final CodesetRepository codesetRepository,
      final QuestionManagementUtil managementUtil,
      final QuestionMapper questionMapper) {
    this.idGenerator = idGenerator;
    this.repository = repository;
    this.configRepository = configRepository;
    this.codesetRepository = codesetRepository;
    this.managementUtil = managementUtil;
    this.questionMapper = questionMapper;
  }

  public TextQuestion create(Long userId, CreateTextQuestionRequest request) {
    TextQuestionEntity question = new TextQuestionEntity(asAdd(userId, request));
    managementUtil.verifyUnique(question);
    question = repository.save(question);
    return questionMapper.toTextQuestion(question);
  }

  public DateQuestion create(Long userId, CreateDateQuestionRequest request) {
    DateQuestionEntity question = new DateQuestionEntity(asAdd(userId, request));
    managementUtil.verifyUnique(question);
    question = repository.save(question);
    return questionMapper.toDateQuestion(question);
  }

  public NumericQuestion create(Long userId, CreateNumericQuestionRequest request) {
    NumericQuestionEntity question = new NumericQuestionEntity(asAdd(userId, request));
    managementUtil.verifyUnique(question);
    question = repository.save(question);
    return questionMapper.toNumericQuestion(question);
  }

  public CodedQuestion create(Long userId, CreateCodedQuestionRequest request) {
    CodedQuestionEntity question = new CodedQuestionEntity(asAdd(userId, request));
    managementUtil.verifyUnique(question);
    verifyValueSetExists(request.getValueSet());
    question = repository.save(question);
    return questionMapper.toCodedQuestion(question);
  }

  void verifyValueSetExists(Long valueSet) {
    if (codesetRepository.findOneByCodeSetGroupId(valueSet).isEmpty()) {
      throw new CreateQuestionException("Unable to find ValueSet with id: " + valueSet);
    }
  }

  private AddNumericQuestion asAdd(Long userId, CreateNumericQuestionRequest request) {
    return new QuestionCommand.AddNumericQuestion(
        request.getMask(),
        request.getFieldLength(),
        request.getDefaultValue(),
        request.getMinValue(),
        request.getMaxValue(),
        request.getRelatedUnitsLiteral(),
        request.getRelatedUnitsValueSet(),
        asQuestionData(request),
        asReportingData(request.getDataMartInfo(), request.getSubgroup()),
        asMessagingData(request.getMessagingInfo()),
        userId,
        Instant.now());
  }

  private AddDateQuestion asAdd(Long userId, CreateDateQuestionRequest request) {
    return new QuestionCommand.AddDateQuestion(
        request.getMask(),
        request.isAllowFutureDates(),
        asQuestionData(request),
        asReportingData(request.getDataMartInfo(), request.getSubgroup()),
        asMessagingData(request.getMessagingInfo()),
        userId,
        Instant.now());
  }

  QuestionCommand.AddTextQuestion asAdd(Long userId, CreateTextQuestionRequest request) {
    return new QuestionCommand.AddTextQuestion(
        request.getMask(),
        request.getFieldLength(),
        request.getDefaultValue(),
        asQuestionData(request),
        asReportingData(request.getDataMartInfo(), request.getSubgroup()),
        asMessagingData(request.getMessagingInfo()),
        userId,
        Instant.now());
  }

  QuestionCommand.AddCodedQuestion asAdd(Long userId, CreateCodedQuestionRequest request) {
    return new QuestionCommand.AddCodedQuestion(
        request.getValueSet(),
        request.getDefaultValue(),
        asQuestionData(request),
        asReportingData(request.getDataMartInfo(), request.getSubgroup()),
        asMessagingData(request.getMessagingInfo()),
        userId,
        Instant.now());
  }

  QuestionCommand.QuestionData asQuestionData(CreateQuestionRequest request) {
    var messagingInfo = request.getMessagingInfo();
    return new QuestionCommand.QuestionData(
        request.getCodeSet(),
        getLocalId(request),
        request.getUniqueName(),
        request.getSubgroup(),
        request.getDescription(),
        request.getLabel(),
        request.getTooltip(),
        request.getDisplayControl(),
        request.getAdminComments(),
        managementUtil.getQuestionOid(
            messagingInfo.includedInMessage(), messagingInfo.codeSystem(), request.getCodeSet()));
  }

  QuestionCommand.ReportingData asReportingData(
      QuestionRequest.ReportingInfo dataMartInfo, String subgroup) {
    return new QuestionCommand.ReportingData(
        dataMartInfo.reportLabel(),
        dataMartInfo.defaultRdbTableName(),
        subgroup + "_" + dataMartInfo.rdbColumnName(),
        // Legacy appends the subgroup to the beginning of the rdbColumnName
        dataMartInfo.dataMartColumnName());
  }

  QuestionCommand.MessagingData asMessagingData(Question.MessagingInfo messagingInfo) {
    return new QuestionCommand.MessagingData(
        messagingInfo.includedInMessage(),
        messagingInfo.messageVariableId(),
        messagingInfo.labelInMessage(),
        messagingInfo.codeSystem(),
        messagingInfo.requiredInMessage(),
        messagingInfo.hl7DataType());
  }

  /**
   * If the request is of 'LOCAL' type and no Id is specified, generate the next available Id from
   * the database. Else, return the specified request.uniqueId
   *
   * @param request
   * @return
   */
  String getLocalId(CreateQuestionRequest request) {
    if (request.getCodeSet().equals(CodeSet.LOCAL)
        && (request.getUniqueId() == null || request.getUniqueId().isBlank())) {
      // Question Ids are a combination of the
      // `NBS_ODSE.NBS_configuration NBS_CLASS_CODE config value + the next valid Id
      // Ex. GA13004
      String nbsClassCode = getNbsClassCode();
      return nbsClassCode + idGenerator.getNextValidId(EntityType.NBS_QUESTION_ID_LDF).getId();
    } else {
      return request.getUniqueId().trim();
    }
  }

  String getNbsClassCode() {
    return configRepository
        .findById("NBS_CLASS_CODE")
        .orElseThrow(() -> new CreateQuestionException("Failed to lookup NBS_CLASS_CODE"))
        .getConfigValue();
  }
}
