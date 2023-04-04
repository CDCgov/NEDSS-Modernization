package gov.cdc.nbs.patientlistener.request;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.patientlistener.request.create.PatientCreateRequestHandler;
import gov.cdc.nbs.patientlistener.request.delete.PatientDeleteRequestHandler;
import gov.cdc.nbs.patientlistener.request.update.PatientUpdateRequestHandler;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class PatientRequestTopicListenerTest {
  @Spy
  ObjectMapper mapper =
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
  void testReceivingUpdateGenInfoRequest() {

    String message = """
        {
           "type": "PatientRequest$UpdateGeneralInfo",
           "requestId": "requestId",
           "patientId": 1234,
           "userId": 123,
           "data": {
             "patientId":419,
             "updatedBy":547,
             "adultsInHouseNumber":661,
             "childrenInHouseNumber":811
           }
         }
         """;
    consumer.onMessage(message, "requestId");

    ArgumentCaptor<UpdateGeneralInfoData> captor = ArgumentCaptor.forClass(UpdateGeneralInfoData.class);

    verify(updateHandler, times(1)).handlePatientGeneralInfoUpdate(captor.capture());

    verifyNoInteractions(createHandler);
    verifyNoInteractions(deleteHandler);
    verifyNoMoreInteractions(updateHandler);

    UpdateGeneralInfoData actual = captor.getValue();

    assertThat(actual.patientId()).isEqualTo(419L);
    assertThat(actual.updatedBy()).isEqualTo(547L);
    assertThat(actual.adultsInHouseNumber()).isEqualTo((short) 661);
    assertThat(actual.childrenInHouseNumber()).isEqualTo((short) 811);

  }

  @Test
  void testReceivingUpdateMortRequest() {
    String message = """
        {
           "type": "PatientRequest$UpdateMortality",
           "requestId": "requestId",
           "patientId": 1234,
           "userId": 123,
           "data": {
             "patientId":419,
             "updatedBy":547,
             "asOf": "2023-03-16T19:17:07Z",
             "deceased": "Y",
             "deceasedTime": "2023-03-17T23:57:11Z"
           }
         }
         """;
    consumer.onMessage(message, "requestId");

    ArgumentCaptor<UpdateMortalityData> captor = ArgumentCaptor.forClass(UpdateMortalityData.class);

    verify(updateHandler, times(1)).handlePatientMortalityUpdate(captor.capture());

    verifyNoInteractions(createHandler);
    verifyNoInteractions(deleteHandler);
    verifyNoMoreInteractions(updateHandler);

    UpdateMortalityData actual = captor.getValue();

    assertThat(actual.patientId()).isEqualTo(419L);
    assertThat(actual.updatedBy()).isEqualTo(547L);
    assertThat(actual.asOf()).isEqualTo("2023-03-16T19:17:07Z");
    assertThat(actual.deceased()).isEqualTo(Deceased.Y);
    assertThat(actual.deceasedTime()).isEqualTo("2023-03-17T23:57:11Z");
  }

  @Test
  void testReceivingUpdateSexRequest() {
    String message = """
        {
           "type": "PatientRequest$UpdateSexAndBirth",
           "requestId": "requestId",
           "patientId": 1234,
           "userId": 123,
           "data": {
             "patientId":419,
             "updatedBy":547,
             "asOf": "2023-03-16T19:17:07Z",
             "birthGender": "M",
             "currentGender" :"U",
             "dateOfBirth": "2001-05-11"
           }
         }
         """;
    consumer.onMessage(message, "requestId");

    ArgumentCaptor<UpdateSexAndBirthData> captor = ArgumentCaptor.forClass(UpdateSexAndBirthData.class);

    verify(updateHandler, times(1)).handlePatientSexAndBirthUpdate(captor.capture());

    verifyNoInteractions(createHandler);
    verifyNoInteractions(deleteHandler);
    verifyNoMoreInteractions(updateHandler);

    UpdateSexAndBirthData actual = captor.getValue();

    assertThat(actual.patientId()).isEqualTo(419L);
    assertThat(actual.updatedBy()).isEqualTo(547L);
    assertThat(actual.asOf()).isEqualTo("2023-03-16T19:17:07Z");
    assertThat(actual.birthGender()).isEqualTo(Gender.M);
    assertThat(actual.currentGender()).isEqualTo(Gender.U);
    assertThat(actual.dateOfBirth()).isEqualTo("2001-05-11");
  }

  @Test
  void testBadData() {

    assertThatThrownBy(() -> consumer.onMessage("bad message", "key"))
        .isInstanceOf(PatientRequestException.class)
        .hasMessage("Failed to parse message into PatientRequest");

    // No handler was called
    verify(createHandler, times(0)).handlePatientCreate(Mockito.any());
    verify(updateHandler, times(0)).handlePatientGeneralInfoUpdate(Mockito.any());
    verify(updateHandler, times(0)).handlePatientMortalityUpdate(Mockito.any());
    verify(updateHandler, times(0)).handlePatientSexAndBirthUpdate(Mockito.any());
    verify(deleteHandler, times(0)).handlePatientDelete(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong());
  }

}
