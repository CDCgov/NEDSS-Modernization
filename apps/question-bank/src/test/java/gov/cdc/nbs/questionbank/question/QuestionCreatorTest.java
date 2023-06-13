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
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.question.TextQuestion;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.producer.QuestionCreatedEventProducer;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.QuestionOid;
import gov.cdc.nbs.questionbank.question.exception.QuestionCreateException;
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

    @InjectMocks
    private QuestionCreator creator;

    @Test
    void should_return_proper_local_id() {
        // given the idGenerator will return a generated Id
        when(idGenerator.getNextValidId(Mockito.any())).thenReturn(new GeneratedId(1000L, "PREFIX","SUFFIX"));

        // given a "LOCAL" create question request
        CreateQuestionRequest.Text request = QuestionRequestMother.localTextRequest();

        // when I generate the localId
        String localId = creator.getLocalId(request);

        // then I am returned the datbaases next available Id
        assertEquals("PREFIX1000SUFFIX", localId);
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
    void should_return_oid_for_included_request() {
        // given the codeValueGeneralRepository will return code system info
        CodeValueGeneral cvg = new CodeValueGeneral();
        cvg.setCodeDescTxt("2.16.840.1.113883.12.78");
        cvg.setCodeShortDescTxt("Abnormal flags (HL7)");
        when(codeValueGeneralRepository.findByCode(Mockito.anyString())).thenReturn(Optional.of(cvg));

        // given a request with messaging included
        CreateQuestionRequest.Text request = QuestionRequestMother.phinTextRequest();

        // when I generate the question oid
        QuestionOid oid = creator.getQuestionOid(request);

        // then I am returned the proper code system info
        assertEquals(cvg.getCodeDescTxt(), oid.oid());
        assertEquals(cvg.getCodeShortDescTxt(), oid.system());
    }

    @Test
    void should_return_oid_for_not_included_local_request() {
        // given a request with messaging not included
        CreateQuestionRequest.Text request = QuestionRequestMother.localTextRequest(false);

        // when I generate the question oid
        QuestionOid oid = creator.getQuestionOid(request);

        // then I am returned the proper code system info
        assertEquals("L", oid.oid());
        assertEquals("Local", oid.system());
    }

    @Test
    void should_return_oid_for_not_included_phin_request() {
        // given a request with messaging not included
        CreateQuestionRequest.Text request = QuestionRequestMother.phinTextRequest(false);

        // when I generate the question oid
        QuestionOid oid = creator.getQuestionOid(request);

        // then I am returned the proper code system info
        assertEquals("2.16.840.1.114222.4.5.232", oid.oid());
        assertEquals("PHIN Questions", oid.system());
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
        assertEquals(request.codeSet(), command.codeSet());
        assertEquals(request.uniqueId(), command.localId());
        assertEquals(request.uniqueName(), command.uniqueName());
        assertEquals(request.subgroup(), command.subgroup());
        assertEquals(request.description(), command.description());
        assertEquals(request.label(), command.label());
        assertEquals(request.tooltip(), command.tooltip());
        assertEquals(request.displayControl(), command.displayControl());
        assertEquals(request.adminComments(), command.adminComments());
        assertEquals(123L, command.userId());

        assertNotNull(command.questionOid());
        assertNotNull(command.reportingData());
        assertNotNull(command.messagingData());
    }

    @Test
    void should_save_to_db() {
        // given the database will return an entity with an Id
        TextQuestion tq = new TextQuestion();
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
        TextQuestion tq = new TextQuestion();
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
            .thenReturn(Collections.singletonList(new TextQuestion()));

        // given a create question request
        CreateQuestionRequest.Text request = QuestionRequestMother.phinTextRequest(false);

         // when a question is created then an exception is thrown
        assertThrows(QuestionCreateException.class, () -> creator.create(123L, request));
    }
}
