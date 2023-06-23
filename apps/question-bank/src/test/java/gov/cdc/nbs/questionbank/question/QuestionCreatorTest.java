package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.NbsConfiguration;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.repository.CodesetRepository;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.QuestionCreatedEventProducer;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.support.QuestionRequestMother;

@ExtendWith(MockitoExtension.class)
class QuestionCreatorTest {

    @Mock
    private IdGeneratorService idGenerator;

    @Mock
    private WaQuestionRepository repository;

    @Mock
    private CodeValueGeneralRepository codeValueGeneralRepository;

    @Mock
    private QuestionCreatedEventProducer.EnabledProducer producer;

    @Mock
    private NbsConfigurationRepository configRepository;

    @Mock
    private CodesetRepository codesetRepository;

    @InjectMocks
    private QuestionCreator creator;

    @Test
    void should_return_proper_local_id() {
        // given the idGenerator will return a generated Id
        when(idGenerator.getNextValidId(Mockito.any())).thenReturn(new GeneratedId(1000L, "PREFIX","SUFFIX"));
        // and the configRepository will return a NBS_CLASS_CODE
        NbsConfiguration configEntry = new NbsConfiguration();
        configEntry.setConfigValue("GA");
        when(configRepository.findById("NBS_CLASS_CODE")).thenReturn(Optional.of(configEntry));

        // given a "LOCAL" create question request
        CreateQuestionRequest.Text request = QuestionRequestMother.localTextRequest();

        // when I generate the localId
        String localId = creator.getLocalId(request);

        // then I am returned the datbaases next available Id
        assertEquals(configEntry.getConfigValue() + "1000", localId);
    }

    @Test
    void should_return_proper_phin_id() {
        // given a "PHIN" create question request
        CreateQuestionRequest.Text request = QuestionRequestMother.phinTextRequest();

        // when I generate the localId
        String localId = creator.getLocalId(request);

        // then I am returned the datbaases next available Id
        assertEquals(request.uniqueId(), localId);
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
        QuestionCommand.ReportingData data = creator.asReportingData(info, request.subgroup());

        // then the fields are set properly
        assertEquals(info.reportLabel(), data.reportLabel());
        assertEquals(info.defaultRdbTableName(), data.defaultRdbTableName());
        assertEquals(request.subgroup() + "_" + info.rdbColumnName(), data.rdbColumnName());
        assertEquals(info.dataMartColumnName(), data.dataMartColumnName());
    }

    @Test
    void should_convert_request() {
        // given a create question request
        CreateQuestionRequest.Text request = QuestionRequestMother.phinTextRequest(false);

        // when i convert to an Add command
        QuestionCommand.AddTextQuestion command = creator.asAdd(123L, request);

        // then the fields are set properly
        assertEquals(request.mask(), command.mask());
        assertEquals(request.fieldLength(), command.fieldLength());
        assertEquals(request.defaultValue(), command.defaultValue());

        assertEquals(123L, command.userId());

        QuestionCommand.QuestionData questionData = command.questionData();
        assertNotNull(questionData.questionOid());
        assertEquals(request.codeSet(), questionData.codeSet());
        assertEquals(request.uniqueId(), questionData.localId());
        assertEquals(request.uniqueName(), questionData.uniqueName());
        assertEquals(request.subgroup(), questionData.subgroup());
        assertEquals(request.description(), questionData.description());
        assertEquals(request.label(), questionData.label());
        assertEquals(request.tooltip(), questionData.tooltip());
        assertEquals(request.displayControl(), questionData.displayControl());
        assertEquals(request.adminComments(), questionData.adminComments());
        assertNotNull(command.reportingData());
        assertNotNull(command.messagingData());
    }

    @Test
    void should_save_to_db() {
        // given the database will return an entity with an Id
        TextQuestionEntity tq = new TextQuestionEntity();
        tq.setId(999L);
        when(repository.save(Mockito.any())).thenReturn(tq);

        // given a create question request
        CreateQuestionRequest.Text request = QuestionRequestMother.phinTextRequest(false);

        // when a question is created
        Long id = creator.create(123L, request);

        // then the id of the new question is returned
        assertEquals(tq.getId(), id);
    }

    @Test
    void should_post_created_event() {
        // given the database will return an entity with an Id and add time
        Instant now = Instant.now();
        TextQuestionEntity tq = new TextQuestionEntity();
        tq.setId(999L);
        tq.setAddTime(now);
        when(repository.save(Mockito.any())).thenReturn(tq);

        // given a create question request
        CreateQuestionRequest.Text request = QuestionRequestMother.phinTextRequest(false);

        // when a question is created
        Long id = creator.create(123L, request);

        // then a question created event is sent
        ArgumentCaptor<QuestionCreatedEvent> eventCaptor = ArgumentCaptor.forClass(QuestionCreatedEvent.class);
        verify(producer).send(eventCaptor.capture());
        QuestionCreatedEvent event = eventCaptor.getValue();
        assertEquals(id.longValue(), event.id());
        assertEquals(123L, event.createdBy());
        assertEquals(now, event.createdAt());
    }

    @Test
    void should_throw_exception_because_not_unique() {
        // given a conflicting question
        when(repository.findAllByUniqueFields(
            Mockito.anyString(), 
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyString()))
            .thenReturn(Collections.singletonList(new TextQuestionEntity()));

        // given a create question request
        CreateQuestionRequest.Text request = QuestionRequestMother.phinTextRequest(false);

         // when a question is created then an exception is thrown
        assertThrows(CreateQuestionException.class, () -> creator.create(123L, request));
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
        assertThrows(CreateQuestionException.class, ()-> creator.verifyValueSetExists(123L));
    }

    @Test
    void should_throw_exception_if_class_code_values_not_found() {
        // given NBS_CLASS_CODE value set does not exist
        when(configRepository.findById("NBS_CLASS_CODE")).thenReturn(Optional.empty());

        // when retrieving the codes, an exception is thrown
        assertThrows(CreateQuestionException.class,() ->creator.getNbsClassCode());
    }
}
