package gov.cdc.nbs.patientlistener.request;

import gov.cdc.nbs.message.RequestStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientRequestStatusProducer {
  private final KafkaTemplate<String, RequestStatus> template;
  private final String statusTopic;

  public PatientRequestStatusProducer(
      final KafkaTemplate<String, RequestStatus> template,
      final PatientRequestProperties properties
  ) {
    this.template = template;
    this.statusTopic = properties.status();
  }


  public void successful(final String request, final String message, final long identifier) {
    var status = RequestStatus.builder()
        .successful(true)
        .requestId(request)
        .message(message)
        .entityId(identifier)
        .build();
    send(status);
  }

  public void failure(final String request, final String message) {
    var status = RequestStatus.builder()
        .successful(false)
        .requestId(request)
        .message(message)
        .build();
    send(status);
  }

  private void send(final RequestStatus status) {
    template.send(statusTopic, status);
  }
}
