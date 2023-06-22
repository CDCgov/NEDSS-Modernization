package gov.cdc.nbs.patientlistener.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.patientlistener.request.update.PatientUpdateRequestHandler;
import gov.cdc.nbs.time.json.EventSchemaJacksonModuleFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class PatientRequestTopicListenerTest {

    @Spy
    ObjectMapper mapper =
        new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(EventSchemaJacksonModuleFactory.create());

    @Mock
    private PatientUpdateRequestHandler updateHandler;
    @InjectMocks
    private PatientRequestTopicListener consumer;

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

        verifyNoMoreInteractions(updateHandler);

        UpdateSexAndBirthData actual = captor.getValue();

        assertThat(actual.patientId()).isEqualTo(419L);
        assertThat(actual.updatedBy()).isEqualTo(547L);
        assertThat(actual.asOf()).isEqualTo("2023-03-16T19:17:07Z");
        assertThat(actual.birthGender()).isEqualTo(Gender.M);
        assertThat(actual.currentGender()).isEqualTo(Gender.U);
        assertThat(actual.dateOfBirth()).isEqualTo("2001-05-11");
    }

}
