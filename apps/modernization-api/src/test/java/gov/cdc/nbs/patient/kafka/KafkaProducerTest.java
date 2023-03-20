package gov.cdc.nbs.patient.kafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.message.patient.event.PatientEvent.PatientEventType;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;

@ExtendWith(MockitoExtension.class)
class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, PatientEvent> kafkaEnvelopeTemplate;

    @Mock
    ElasticsearchPersonRepository elasticPersonRepository;

    @InjectMocks
    private KafkaProducer producer;

    public KafkaProducerTest() {
        MockitoAnnotations.openMocks(this);
        producer = new KafkaProducer();
    }

    @Test
    void testPatientSearchEvent() {
        var message = new PatientEvent("Request-ID", 1L, 2L, PatientEventType.CREATE, null);
        ListenableFuture<SendResult<String, PatientEvent>> future =
                new SettableListenableFuture<SendResult<String, PatientEvent>>();
        Mockito.when(kafkaEnvelopeTemplate.send(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(future);

        producer.requestPatientEventEnvelope(message);

        ArgumentCaptor<PatientEvent> envelopeEventArgumentCaptor = ArgumentCaptor.forClass(PatientEvent.class);
        verify(kafkaEnvelopeTemplate).send(eq(null), eq("Request-ID"), envelopeEventArgumentCaptor.capture());

        PatientEvent actualRecord = envelopeEventArgumentCaptor.getValue();
        assertThat(actualRecord.requestId()).isEqualTo("Request-ID");
        assertThat(actualRecord.patientId()).isEqualTo(1L);
        assertThat(actualRecord.userId()).isEqualTo(2L);
        assertThat(actualRecord.eventType()).isEqualTo(PatientEventType.CREATE);
        assertThat(actualRecord.data()).isNull();

        verifyNoMoreInteractions(kafkaEnvelopeTemplate);
    }
}
