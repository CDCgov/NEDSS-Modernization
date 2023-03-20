package gov.cdc.nbs.patientlistener.kafka;

import gov.cdc.nbs.patientlistener.exception.KafkaException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

@Slf4j
public class KafkaErrorHandler implements CommonErrorHandler {

  private final StatusProducer statusProducer;

  public KafkaErrorHandler(StatusProducer statusProducer) {
    this.statusProducer = statusProducer;
  }

  @Override
  public void handleRecord(Exception thrownException, ConsumerRecord<?, ?> consumerRecord, Consumer<?, ?> consumer,
      MessageListenerContainer container) {
    log.warn("Kafka Consumer encountered an exception: {}", thrownException.getMessage());
    var cause = thrownException.getCause();

    if (cause instanceof KafkaException exception) {
      statusProducer.failure(exception.getKey(), exception.getMessage());
    } else {
      statusProducer.failure((String) consumerRecord.key(), "Failed to process message");
    }

  }


}
