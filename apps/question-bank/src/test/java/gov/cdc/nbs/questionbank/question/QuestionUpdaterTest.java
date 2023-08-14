package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.QuestionOid;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionHistRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.QuestionType;
import gov.cdc.nbs.questionbank.question.request.UpdateQuestionRequest;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import gov.cdc.nbs.questionbank.support.QuestionRequestMother;

@ExtendWith(MockitoExtension.class)
class QuestionUpdaterTest {

    @Mock
    private WaQuestionRepository repository;

    @Mock
    private WaQuestionHistRepository histRepository;

    @Mock
    private QuestionManagementUtil managementUtil;

    @Mock
    private WaUiMetadataRepository metadatumRepository;

    @Spy
    private QuestionMapper questionMapper = new QuestionMapper();

    @InjectMocks
    private QuestionUpdater updater;

    @Test
    void should_set_status_inactive() {
        // given an active question and a working repository
        when(repository.findById(321L)).thenReturn(Optional.of(emptyQuestion()));
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
        when(repository.findById(321L)).thenReturn(Optional.of(emptyQuestion()));
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
        when(repository.findById(321L)).thenReturn(Optional.of(emptyQuestion()));
        ArgumentCaptor<WaQuestion> captor = ArgumentCaptor.forClass(WaQuestion.class);
        when(repository.save(captor.capture())).thenAnswer(q -> q.getArgument(0));

        // when a set status to inactive request is processed
        updater.setStatus(9L, 321L, true);

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
        UpdateQuestionRequest request = QuestionRequestMother.update(QuestionType.DATE);

        // and an existing question
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(inactiveQuestion()));

        // when i send an update then an exception is thrown
        assertThrows(UpdateQuestionException.class, () -> updater.update(1L, 2L, request));
    }

    @Test
    // Allow more than 25 assertions
    @SuppressWarnings("squid:S5961")
    void should_convert_to_update() {
        // given an update request
        UpdateQuestionRequest request = QuestionRequestMother.update();

        // and a valid oid
        when(managementUtil.getQuestionOid(true, request.codeSystem(), "LOCAL"))
                .thenReturn(new QuestionOid("oid", "oid system"));


        // and an existing question
        TextQuestionEntity question = QuestionEntityMother.textQuestion();

        // when a request is converted to a command
        QuestionCommand.Update update = updater.asUpdate(1L, request, false, question);

        // then the proper values are set
        QuestionCommand.UpdatableQuestionData data = update.questionData();
        assertEquals(request.uniqueName(), data.uniqueName());
        assertEquals(request.description(), data.description());
        assertEquals(request.label(), data.label());
        assertEquals(request.tooltip(), data.tooltip());
        assertEquals(request.displayControl(), data.displayControl());
        assertEquals(request.adminComments(), data.adminComments());
        assertEquals("oid", data.questionOid().oid());
        assertEquals("oid system", data.questionOid().system());

        assertEquals(request.defaultValue(), update.defaultValue());
        assertEquals(request.mask(), update.mask());
        assertEquals(request.fieldLength(), update.fieldLength());
        assertEquals(request.minValue(), update.minValue());
        assertEquals(request.maxValue(), update.maxValue());
        assertEquals(request.unitType(), update.unitType());
        assertEquals(request.unitValue(), update.unitValue());
        assertEquals(request.allowFutureDates(), update.allowFutureDates());
        assertEquals(request.valueSet(), update.valueSet());

        QuestionCommand.ReportingData reportingData = update.reportingData();
        assertEquals(question.getSubGroupNm() + "_" + request.rdbColumnName(), reportingData.rdbColumnName());
        assertEquals(request.defaultLabelInReport(), reportingData.reportLabel());
        assertEquals(request.datamartColumnName(), reportingData.dataMartColumnName());

        QuestionCommand.MessagingData messagingData = update.messagingData();
        assertEquals(request.includedInMessage(), messagingData.includedInMessage());
        assertEquals(request.messageVariableId(), messagingData.messageVariableId());
        assertEquals(request.labelInMessage(), messagingData.labelInMessage());
        assertEquals(request.codeSystem(), messagingData.codeSystem());
        assertEquals(request.requiredInMessage(), messagingData.requiredInMessage());
        assertEquals(request.hl7DataType(), messagingData.hl7DataType());
    }

    private WaQuestion emptyQuestion() {
        TextQuestionEntity q = new TextQuestionEntity();
        q.setId(321L);
        q.setRecordStatusCd(WaQuestion.ACTIVE);
        q.setVersionCtrlNbr(1);
        return q;
    }

    private WaQuestion inactiveQuestion() {
        TextQuestionEntity q = new TextQuestionEntity();
        q.setId(321L);
        q.setRecordStatusCd(WaQuestion.INACTIVE);
        q.setVersionCtrlNbr(1);
        return q;
    }


    @Test
    void cant_update_type_if_inuse() {
        // given an update request
        UpdateQuestionRequest request = QuestionRequestMother.update(QuestionType.DATE);

        // and an existign question
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(QuestionEntityMother.textQuestion()));

        // and a question that is in use
        when(metadatumRepository
                .findAllByQuestionIdentifier(QuestionEntityMother.textQuestion().getQuestionIdentifier()))
                        .thenReturn(Collections.singletonList(new WaUiMetadata()));

        // and the question can be saved
        when(repository.save(Mockito.any())).thenReturn(QuestionEntityMother.textQuestion());

        // when i send an update
        updater.update(1L, 2L, request);

        // then set data type is not called
        verify(repository, times(0)).setDataType(Mockito.anyString(), Mockito.anyLong());
    }

    @Test
    void can_update_type_if_not_inuse() {
        // given an update request
        UpdateQuestionRequest request = QuestionRequestMother.update(QuestionType.DATE);

        // and an existign question
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(QuestionEntityMother.textQuestion()));

        // and a question that is in use
        when(metadatumRepository
                .findAllByQuestionIdentifier(QuestionEntityMother.textQuestion().getQuestionIdentifier()))
                        .thenReturn(Collections.emptyList());

        // and the question can be saved
        when(repository.save(Mockito.any())).thenReturn(QuestionEntityMother.textQuestion());

        // when i send an update
        updater.update(1L, 2L, request);

        // then set data type is not called
        verify(repository, times(1)).setDataType(Mockito.anyString(), Mockito.anyLong());
    }

}
