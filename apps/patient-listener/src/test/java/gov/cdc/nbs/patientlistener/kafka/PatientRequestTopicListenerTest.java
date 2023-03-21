package gov.cdc.nbs.patientlistener.kafka;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.message.patient.event.PatientEvent.PatientEventType;
import gov.cdc.nbs.patientlistener.exception.KafkaException;
import gov.cdc.nbs.patientlistener.service.PatientCreateRequestHandler;
import gov.cdc.nbs.patientlistener.service.PatientDeleteRequestHandler;
import gov.cdc.nbs.patientlistener.service.PatientUpdateRequestHandler;
import gov.cdc.nbs.time.json.EventSchemaJacksonModuleFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class PatientRequestTopicListenerTest {
  @Spy
  private final ObjectMapper mapper =
      new ObjectMapper()
          .setSerializationInclusion(Include.NON_NULL)
          .registerModule(EventSchemaJacksonModuleFactory.create());
  @Mock
  private PatientCreateRequestHandler createHandler;
  @Mock
  private PatientUpdateRequestHandler updateHandler;
  @Mock
  private PatientDeleteRequestHandler deleteHandler;

  @InjectMocks
  private PatientRequestTopicListener consumer;


  @Test
  void testReceivingCreateRequest() {

    String message = """
        {
          "type": "PatientRequest$Create",
          "requestId": "requestId",
          "patientId": 1234,
          "userId": 123,
          "data": {
            "patient": 2027,
            "names": [],
            "races": [],
            "addresses": [],
            "phoneNumbers": [],
            "emailAddresses": [],
            "createdBy": 2293
          }
        }                
        """;
    consumer.onMessage(message, "requestId");

    ArgumentCaptor<PatientCreateData> captor = ArgumentCaptor.forClass(PatientCreateData.class);

    verify(createHandler, times(1)).handlePatientCreate(captor.capture());

    verifyNoInteractions(updateHandler);
    verifyNoInteractions(deleteHandler);

    PatientCreateData actual = captor.getValue();

    assertThat(actual)
        .returns(2027L, PatientCreateData::patient)
        .returns(2293L, PatientCreateData::createdBy)
        ;
  }

  @Test
  void testReceivingDeleteRequest() {
    String message = """
        {
        "type":"PatientRequest$Delete",
        "requestId":"request-id",
        "patientId":2927,
        "userId":1901
        }
        """;
    consumer.onMessage(message, "message-key");

    verifyNoInteractions(createHandler);
    verifyNoInteractions(updateHandler);

    verify(deleteHandler, times(1)).handlePatientDelete("request-id", 2927L, 1901L);
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
    KafkaException ex = null;
    try {
      consumer.listenToPatientTopic("bad message", "key");
    } catch (KafkaException e) {
      ex = e;
    }

    assertNotNull(ex);
    // No handler was called
    verify(createHandler, times(0)).handlePatientCreate(Mockito.any());
    verify(updateHandler, times(0)).handlePatientGeneralInfoUpdate(Mockito.any());
    verify(updateHandler, times(0)).handlePatientMortalityUpdate(Mockito.any());
    verify(updateHandler, times(0)).handlePatientSexAndBirthUpdate(Mockito.any());
    verify(deleteHandler, times(0)).handlePatientDelete(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());
  }

}
