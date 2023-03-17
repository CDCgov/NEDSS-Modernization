package gov.cdc.nbs.patientlistener.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.message.RequestStatus;

@Component
public class StatusProducer {
    private final KafkaTemplate<String, RequestStatus> template;

    public StatusProducer(KafkaTemplate<String, RequestStatus> template) {
        this.template = template;
    }

    @Value("${kafkadef.topics.status.patient}")
    private String statusTopic;

    public void send(boolean successful, String key, String message) {
        send(successful, key, message, null);
    }

    public void send(boolean successful, String key, String message, Long entityId) {
        var status = RequestStatus.builder()
                .successful(successful)
                .requestId(key)
                .message(message)
                .entityId(entityId)
                .build();
        template.send(statusTopic, status);
    }
}
