package gov.cdc.nbs.questionbank.question;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.NbsConfiguration;
import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.repository.CodesetRepository;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.QuestionOid;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.model.Question.CodedQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.DateQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.model.Question.NumericQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.TextQuestion;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.create.CreateCodedQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateDateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateNumericQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateTextQuestionRequest;
import gov.cdc.nbs.questionbank.support.QuestionRequestMother;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuestionCreatorTest {

  @Mock private IdGeneratorService idGenerator;

  @Mock private WaQuestionRepository repository;

  @Mock private CodeValueGeneralRepository codeValueGeneralRepository;

  @Mock private NbsConfigurationRepository configRepository;

  @Mock private CodesetRepository codesetRepository;

  @Mock private QuestionManagementUtil managementUtil;

  @Mock private QuestionMapper mapper;

  @InjectMocks private QuestionCreator creator;

  @Test
  void should_return_user_specified_local_id() {
    // given a "LOCAL" create question request with an Id specified
    CreateTextQuestionRequest request = QuestionRequestMother.localTextRequest();

    // when I generate the localId
    String localId = creator.getLocalId(request);

    // then I am returned the specified Id
    assertEquals(request.getUniqueId(), localId);
  }

  @Test
  void should_return_generated_local_id_null() {
    // given the idGenerator will return a generated Id
    when(idGenerator.getNextValidId(Mockito.any()))
        .thenReturn(new GeneratedId(1000L, "PREFIX", "SUFFIX"));

    // and the configRepository will return a NBS_CLASS_CODE
    NbsConfiguration configEntry = new NbsConfiguration();
    configEntry.setConfigValue("GA");
    when(configRepository.findById("NBS_CLASS_CODE")).thenReturn(Optional.of(configEntry));

    // given a "LOCAL" create question request with null uniqueId
    CreateTextQuestionRequest request = QuestionRequestMother.localWithUniqueId(null);

    // when I generate the localId
    String localId = creator.getLocalId(request);

    // then I am returned the datbaases next available Id
    assertEquals(configEntry.getConfigValue() + "1000", localId);
  }

  @Test
  void should_return_generated_local_id_empty() {
    // given the idGenerator will return a generated Id
    when(idGenerator.getNextValidId(Mockito.any()))
        .thenReturn(new GeneratedId(1000L, "PREFIX", "SUFFIX"));

    // and the configRepository will return a NBS_CLASS_CODE
    NbsConfiguration configEntry = new NbsConfiguration();
    configEntry.setConfigValue("GA");
    when(configRepository.findById("NBS_CLASS_CODE")).thenReturn(Optional.of(configEntry));

    // given a "LOCAL" create question request with null uniqueId
    CreateTextQuestionRequest request = QuestionRequestMother.localWithUniqueId("  ");

    // when I generate the localId
    String localId = creator.getLocalId(request);

    // then I am returned the datbaases next available Id
    assertEquals(configEntry.getConfigValue() + "1000", localId);
  }

  @Test
  void should_return_proper_phin_id() {
    // given a "PHIN" create question request
    CreateTextQuestionRequest request = QuestionRequestMother.phinTextRequest();

    // when I generate the localId
    String localId = creator.getLocalId(request);

    // then I am returned the datbaases next available Id
    assertEquals(request.getUniqueId(), localId);
  }

  @Test
  void should_convert_messaging() {
    // given messaging info
    MessagingInfo info = QuestionRequestMother.messagingInfo(true);

    // when I convert to messaging data
    QuestionCommand.MessagingData data = creator.asMessagingData(info);

    // Then the fields are set properly
    assertEquals(info.includedInMessage(), data.includedInMessage());
    assertEquals(info.messageVariableId(), data.messageVariableId());
    assertEquals(info.labelInMessage(), data.labelInMessage());
    assertEquals(info.codeSystem(), data.codeSystem());
    assertEquals(info.requiredInMessage(), data.requiredInMessage());
    assertEquals(info.hl7DataType(), data.hl7DataType());
  }

  @Test
  void should_convert_reporting() {
    // given reporting info
    ReportingInfo info = QuestionRequestMother.reportingInfo();
    CreateQuestionRequest request = QuestionRequestMother.localTextRequest();

    // when i convert to reporting data
    QuestionCommand.ReportingData data = creator.asReportingData(info, request.getSubgroup());

    // then the fields are set properly
    assertEquals(info.reportLabel(), data.reportLabel());
    assertEquals(info.defaultRdbTableName(), data.defaultRdbTableName());
    assertEquals(request.getSubgroup() + "_" + info.rdbColumnName(), data.rdbColumnName());
    assertEquals(info.dataMartColumnName(), data.dataMartColumnName());
  }

  @Test
  void should_convert_request() {
    // given a create question request
    CreateTextQuestionRequest request = QuestionRequestMother.phinTextRequest(false);

    // and a valid oid
    when(managementUtil.getQuestionOid(false, "ABNORMAL_FLAGS_HL7", CodeSet.PHIN))
        .thenReturn(new QuestionOid("test", "test"));

    // when i convert to an Add command
    QuestionCommand.AddTextQuestion command = creator.asAdd(123L, request);

    // then the fields are set properly
    assertEquals(request.getMask(), command.mask());
    assertEquals(request.getFieldLength(), command.fieldLength());
    assertEquals(request.getDefaultValue(), command.defaultValue());

    assertEquals(123L, command.userId());

    QuestionCommand.QuestionData questionData = command.questionData();
    assertNotNull(questionData.questionOid());
    assertEquals(request.getCodeSet(), questionData.codeSet());
    assertEquals(request.getUniqueId(), questionData.localId());
    assertEquals(request.getUniqueName(), questionData.uniqueName());
    assertEquals(request.getSubgroup(), questionData.subgroup());
    assertEquals(request.getDescription(), questionData.description());
    assertEquals(request.getLabel(), questionData.label());
    assertEquals(request.getTooltip(), questionData.tooltip());
    assertEquals(request.getDisplayControl(), questionData.displayControl());
    assertEquals(request.getAdminComments(), questionData.adminComments());
    assertNotNull(command.reportingData());
    assertNotNull(command.messagingData());
  }

  @Test
  void should_save_to_db() {
    // given the database will return an entity with an Id
    TextQuestionEntity tq = Mockito.mock(TextQuestionEntity.class);
    when(tq.getId()).thenReturn(999l);
    when(repository.save(Mockito.any())).thenReturn(tq);

    // and that entity will be mapped to a question
    when(mapper.toTextQuestion(tq))
        .thenReturn(
            new TextQuestion(
                999l, null, null, null, null, null, null, null, null, null, null, null, null, 1l,
                null, null, null));

    // given a create question request
    CreateTextQuestionRequest request = QuestionRequestMother.phinTextRequest(false);

    // when a question is created
    Question question = creator.create(123L, request);

    // then the id of the new question is returned
    assertEquals(tq.getId().longValue(), question.id());
  }

  @Test
  void should_verify_valueset_exists() {
    // given a value set that exists
    Codeset mockCodeset = Mockito.mock(Codeset.class);
    when(codesetRepository.findOneByCodeSetGroupId(123L)).thenReturn(Optional.of(mockCodeset));

    // when querying for the value set then no exception is thrown
    creator.verifyValueSetExists(123L);
  }

  @Test
  void should_throw_exception_if_valueset_not_exists() {
    // given a value set that does not
    when(codesetRepository.findOneByCodeSetGroupId(123L)).thenReturn(Optional.empty());

    // when querying for the value set then an exception is thrown
    assertThrows(CreateQuestionException.class, () -> creator.verifyValueSetExists(123L));
  }

  @Test
  void should_throw_exception_if_class_code_values_not_found() {
    // given NBS_CLASS_CODE value set does not exist
    when(configRepository.findById("NBS_CLASS_CODE")).thenReturn(Optional.empty());

    // when retrieving the codes, an exception is thrown
    assertThrows(CreateQuestionException.class, () -> creator.getNbsClassCode());
  }

  @Test
  void should_create_date_question() {
    // given the database will return an entity with an Id
    DateQuestionEntity dq = Mockito.mock(DateQuestionEntity.class);
    when(dq.getId()).thenReturn(999l);
    when(repository.save(Mockito.any())).thenReturn(dq);

    // and that entity will be mapped to a question
    when(mapper.toDateQuestion(dq))
        .thenReturn(
            new DateQuestion(
                999l, null, false, null, null, null, null, null, null, null, null, null, null, null,
                null, null));

    // given a create question request
    CreateDateQuestionRequest request = QuestionRequestMother.dateRequest();

    // when a question is created
    Question question = creator.create(123L, request);

    // then the id of the new question is returned
    assertEquals(dq.getId().longValue(), question.id());
  }

  @Test
  void should_create_numeric_question() {
    // given the database will return an entity with an Id
    NumericQuestionEntity nq = Mockito.mock(NumericQuestionEntity.class);
    when(nq.getId()).thenReturn(999l);
    when(repository.save(Mockito.any())).thenReturn(nq);

    // and that entity will be mapped to a question
    when(mapper.toNumericQuestion(nq))
        .thenReturn(
            new NumericQuestion(
                999l, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null));

    // given a create question request
    CreateNumericQuestionRequest request = QuestionRequestMother.numericRequest();

    // when a question is created
    Question question = creator.create(123L, request);

    // then the id of the new question is returned
    assertEquals(nq.getId().longValue(), question.id());
  }

  @Test
  void should_create_coded_question() {
    // given the database will return an entity with an Id
    CodedQuestionEntity cq = Mockito.mock(CodedQuestionEntity.class);
    when(cq.getId()).thenReturn(999l);
    when(repository.save(Mockito.any())).thenReturn(cq);

    // and an existing value set
    when(codesetRepository.findOneByCodeSetGroupId(123l)).thenReturn(Optional.of(new Codeset()));

    // and that entity will be mapped to a question
    when(mapper.toCodedQuestion(cq))
        .thenReturn(
            new CodedQuestion(
                999l, 123l, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null));

    // given a create question request
    CreateCodedQuestionRequest request = QuestionRequestMother.codedRequest(123l);

    // when a question is created
    Question question = creator.create(123L, request);

    // then the id of the new question is returned
    assertEquals(cq.getId().longValue(), question.id());
  }
}
