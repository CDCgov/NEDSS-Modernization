package gov.cdc.nbs.questionbank.kafka.exception;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestErrorHandler implements CommonErrorHandler {

    private final RequestStatusProducer statusProducer;

    public RequestErrorHandler(final RequestStatusProducer statusProducer) {
        this.statusProducer = statusProducer;
    }

    @Override
    public void handleRecord(
            final Exception thrownException,
            final ConsumerRecord<?, ?> consumerRecord,
            final Consumer<?, ?> consumer,
            final MessageListenerContainer container) {
        log.warn("Kafka Consumer encountered an exception: {}", thrownException.getMessage());
        var cause = thrownException.getCause();

        if (cause instanceof RequestException exception) {
            statusProducer.failure(exception.getKey(), exception.getMessage());
        } else {
            statusProducer.failure((String) consumerRecord.key(), "Failed to process message");
        }

    }


}
