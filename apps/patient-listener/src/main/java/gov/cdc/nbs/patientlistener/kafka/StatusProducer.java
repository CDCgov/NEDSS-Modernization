package gov.cdc.nbs.patientlistener.kafka;

import gov.cdc.nbs.message.RequestStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StatusProducer {
  private final KafkaTemplate<String, RequestStatus> template;
  private final String statusTopic;

  public StatusProducer(
      final KafkaTemplate<String, RequestStatus> template,
      final String statusTopic
  ) {
    this.template = template;
    this.statusTopic = statusTopic;
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
