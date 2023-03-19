package gov.cdc.nbs.patientlistener.kafka;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.message.patient.event.PatientEvent.PatientEventType;
import gov.cdc.nbs.patientlistener.service.PatientCreateRequestHandler;
import gov.cdc.nbs.patientlistener.service.PatientDeleteRequestHandler;
import gov.cdc.nbs.patientlistener.service.PatientUpdateRequestHandler;

class KafkaConsumerTest {
    @Spy
    private final ObjectMapper mapper =
            new ObjectMapper()
                    .setSerializationInclusion(Include.NON_NULL)
                    .registerModule(new JavaTimeModule());
    @Mock
    private PatientCreateRequestHandler createHandler;
    @Mock
    private PatientUpdateRequestHandler updateHandler;
    @Mock
    private PatientDeleteRequestHandler deleteHandler;
    @Mock
    private StatusProducer statusProducer;

    @InjectMocks
    private KafkaConsumer consumer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceivingCreateRequest() throws JsonProcessingException {
        var event = new PatientEvent("requestId", 1234L, 123L, PatientEventType.CREATE, null);
        var message = mapper.writeValueAsString(event);
        consumer.listenToPatientTopic(message, "requestId");

        verify(createHandler, times(1)).handlePatientCreate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientGeneralInfoUpdate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientMortalityUpdate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientSexAndBirthUpdate(Mockito.any());
        verify(deleteHandler, times(0)).handlePatientDelete(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void testReceivingDeleteRequest() throws JsonProcessingException {
        var event = new PatientEvent("requestId", 1234L, 123L, PatientEventType.DELETE, null);
        var message = mapper.writeValueAsString(event);
        consumer.listenToPatientTopic(message, "requestId");

        verify(createHandler, times(0)).handlePatientCreate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientGeneralInfoUpdate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientMortalityUpdate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientSexAndBirthUpdate(Mockito.any());
        verify(deleteHandler, times(1)).handlePatientDelete(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void testReceivingUpdateGenInfoRequest() throws JsonProcessingException {
        var event = new PatientEvent("requestId", 1234L, 123L, PatientEventType.UPDATE_GENERAL_INFO, null);
        var message = mapper.writeValueAsString(event);
        consumer.listenToPatientTopic(message, "requestId");

        verify(createHandler, times(0)).handlePatientCreate(Mockito.any());
        verify(updateHandler, times(1)).handlePatientGeneralInfoUpdate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientMortalityUpdate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientSexAndBirthUpdate(Mockito.any());
        verify(deleteHandler, times(0)).handlePatientDelete(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void testReceivingUpdateMortRequest() throws JsonProcessingException {
        var event = new PatientEvent("requestId", 1234L, 123L, PatientEventType.UPDATE_MORTALITY, null);
        var message = mapper.writeValueAsString(event);
        consumer.listenToPatientTopic(message, "requestId");

        verify(createHandler, times(0)).handlePatientCreate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientGeneralInfoUpdate(Mockito.any());
        verify(updateHandler, times(1)).handlePatientMortalityUpdate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientSexAndBirthUpdate(Mockito.any());
        verify(deleteHandler, times(0)).handlePatientDelete(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void testReceivingUpdateSexRequest() throws JsonProcessingException {
        var event = new PatientEvent("requestId", 1234L, 123L, PatientEventType.UPDATE_SEX_AND_BIRTH, null);
        var message = mapper.writeValueAsString(event);
        consumer.listenToPatientTopic(message, "requestId");

        verify(createHandler, times(0)).handlePatientCreate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientGeneralInfoUpdate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientMortalityUpdate(Mockito.any());
        verify(updateHandler, times(1)).handlePatientSexAndBirthUpdate(Mockito.any());
        verify(deleteHandler, times(0)).handlePatientDelete(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void testBadData() {
        consumer.listenToPatientTopic("bad message", "key");

        // No handler was called
        verify(createHandler, times(0)).handlePatientCreate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientGeneralInfoUpdate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientMortalityUpdate(Mockito.any());
        verify(updateHandler, times(0)).handlePatientSexAndBirthUpdate(Mockito.any());
        verify(deleteHandler, times(0)).handlePatientDelete(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());

        // A failure status was send
        verify(statusProducer).send(eq(false), eq("key"), Mockito.anyString());
    }

}
