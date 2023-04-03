package gov.cdc.nbs.patientlistener.request;

import gov.cdc.nbs.message.RequestStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PatientRequestStatusProducerTest {

  @Mock
  private KafkaTemplate<String, RequestStatus> template;

  @Captor
  private ArgumentCaptor<RequestStatus> statusCaptor;

  private PatientRequestStatusProducer statusProducer;

  @BeforeEach
  void setup() {
    statusProducer = new PatientRequestStatusProducer(template, new PatientRequestProperties("request", "status"));
  }

  @Test
  void sendSuccessTest() {

    statusProducer.successful("RequestId", "Message", 123L);

    verify(template, times(1)).send(eq("status"), statusCaptor.capture());

    RequestStatus actual = statusCaptor.getValue();

    assertThat(actual.isSuccessful()).isTrue();
    assertThat(actual.getRequestId()).isEqualTo("RequestId");
    assertThat(actual.getMessage()).isEqualTo("Message");
    assertThat(actual.getEntityId()).isEqualTo(123L);
  }

  @Test
  void sendFailTest() {
    statusProducer.failure("RequestId", "Message");

    verify(template, times(1)).send(eq("status"), statusCaptor.capture());

    RequestStatus actual = statusCaptor.getValue();

    assertThat(actual.isSuccessful()).isFalse();
    assertThat(actual.getRequestId()).isEqualTo("RequestId");
    assertThat(actual.getMessage()).isEqualTo("Message");
    assertThat(actual.getEntityId()).isNull();
  }

}
