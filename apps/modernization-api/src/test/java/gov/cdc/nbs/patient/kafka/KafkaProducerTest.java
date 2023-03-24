package gov.cdc.nbs.patient.kafka;

import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, PatientRequest> kafkaEnvelopeTemplate;

    @Mock
    ElasticsearchPersonRepository elasticPersonRepository;

    @InjectMocks
    private KafkaProducer producer;

    public KafkaProducerTest() {
        producer = new KafkaProducer();
    }

    @Test
    void testPatientSearchEvent() {
        var message = new PatientRequest.Create("Request-ID", 1L, 2L, null);
        ListenableFuture<SendResult<String, PatientRequest>> future = new SettableListenableFuture<>();
        Mockito.when(kafkaEnvelopeTemplate.send(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(future);

        producer.requestPatientEventEnvelope(message);

        ArgumentCaptor<PatientRequest> envelopeEventArgumentCaptor = ArgumentCaptor.forClass(PatientRequest.class);
        verify(kafkaEnvelopeTemplate).send(nullable(String.class), eq("Request-ID"), envelopeEventArgumentCaptor.capture());

        PatientRequest actualRecord = envelopeEventArgumentCaptor.getValue();
        assertThat(actualRecord.requestId()).isEqualTo("Request-ID");
        assertThat(actualRecord.patientId()).isEqualTo(1L);
        assertThat(actualRecord.userId()).isEqualTo(2L);

        verifyNoMoreInteractions(kafkaEnvelopeTemplate);
    }
}
