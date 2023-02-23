package gov.cdc.nbs.patientlistener.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import gov.cdc.nbs.message.RequestStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducer {
    private static final String STATUS = "-status";
    @Value("${kafkadef.patient-search.topics.request.patient-create}")
    private String patientCreateTopic;

    @Autowired
    private KafkaTemplate<String, RequestStatus> kafkaOperationStatusTemplate;

    public void requestPatientCreateStatusEnvelope(RequestStatus status) {
        send(kafkaOperationStatusTemplate, patientCreateTopic + STATUS, status.getRequestId(), status);
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
