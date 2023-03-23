package gov.cdc.nbs.patientlistener.request;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

import javax.annotation.Nonnull;

@Slf4j
public class PatientRequestErrorHandler implements CommonErrorHandler {

  private final PatientRequestStatusProducer statusProducer;

  public PatientRequestErrorHandler(final PatientRequestStatusProducer statusProducer) {
    this.statusProducer = statusProducer;
  }

  @Override
  public void handleRecord(
      final Exception thrownException,
      @Nonnull final ConsumerRecord<?, ?> consumerRecord,
      @Nonnull final Consumer<?, ?> consumer,
      @Nonnull final MessageListenerContainer container
  ) {
    log.warn("Kafka Consumer encountered an exception: {}", thrownException.getMessage());
    var cause = thrownException.getCause();

    if (cause instanceof PatientRequestException exception) {
      statusProducer.failure(exception.getKey(), exception.getMessage());
    } else {
      statusProducer.failure((String) consumerRecord.key(), "Failed to process message");
    }

  }


}
