package gov.cdc.nbs.patient.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, PatientEvent> kafkaPatientEventTemplate;

    @Value("${kafkadef.topics.request.patient}")
    private String patientTopic;

    public void requestPatientEventEnvelope(PatientEvent request) {
        send(kafkaPatientEventTemplate, patientTopic, request.requestId(), request);
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
