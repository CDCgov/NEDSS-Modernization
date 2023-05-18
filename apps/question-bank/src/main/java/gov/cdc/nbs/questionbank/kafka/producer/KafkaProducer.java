package gov.cdc.nbs.questionbank.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, QuestionBankRequest> kafkaPatientEventTemplate;

    @Value("${kafkadef.topics.questionbank.request}")
    private String questionBankTopic;

    public void requestEventEnvelope(final QuestionBankRequest request) {

        kafkaPatientEventTemplate.send(questionBankTopic, request.requestId(), request).addCallback(
                new ListenableFutureCallback<>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Failed to send message key={} message={} error={}", request.requestId(), request,
                                ex);
                    }

                    @Override
                    public void onSuccess(SendResult<String, QuestionBankRequest> result) {
                        log.info("Sent message key={} message={} result={}", request.requestId(), request, result);
                    }
                });
    }

}
