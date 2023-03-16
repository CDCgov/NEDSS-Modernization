package gov.cdc.nbs.patientlistener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import gov.cdc.nbs.message.PatientUpdateEventResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaRequestProducerService {


    @Autowired
    private KafkaTemplate<String, PatientUpdateEventResponse> kafkaPatientUpdateTemplate;

    @Value("${kafkadef.patient-search.topics.event.patientupdate}")
    private String patientUpdateResponseTopic;

    public void requestPatientUpdateEnvelope(PatientUpdateEventResponse kafkaMessage) {
        try {
            send(kafkaPatientUpdateTemplate, patientUpdateResponseTopic, kafkaMessage.getRequestId(), kafkaMessage);
        } catch (Exception e) {
            log.error("Error sending patientUpdateResponse Kafka message", e);
        }
    }

    private <K, V> void send(KafkaTemplate<K, V> template, String topic, K key, V event) {
        ListenableFuture<SendResult<K, V>> future = template.send(topic, key, event);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Failed to send message key={} message={} error={}", key, event, ex);
            }

            @Override
            public void onSuccess(SendResult<K, V> result) {
                log.info("Sent message key={} message={} result={}", key, event, result);
            }

        });

    }


}
