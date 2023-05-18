package gov.cdc.nbs.questionbank.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.RequestStatus;

@Component
public class RequestStatusProducer {
    private final KafkaTemplate<String, RequestStatus> template;
    private final String statusTopic;

    public RequestStatusProducer(
            final KafkaTemplate<String, RequestStatus> template,
            final RequestProperties properties) {
        this.template = template;
        this.statusTopic = properties.status();
    }


    public void successful(final String request,
            final String message,
            final long identifier) {
        var status = new RequestStatus(
                true,
                message,
                request,
                identifier);
        send(status);
    }

    public void failure(final String request, final String message) {
        var status = new RequestStatus(
                false,
                message,
                request,
                null);
        send(status);
    }

    private void send(final RequestStatus status) {
        template.send(statusTopic, status);
    }
}
