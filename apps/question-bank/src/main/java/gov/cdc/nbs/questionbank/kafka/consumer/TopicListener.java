package gov.cdc.nbs.questionbank.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.kafka.exception.RequestException;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankRequest;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;
import gov.cdc.nbs.questionbank.question.QuestionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TopicListener {
    private final ObjectMapper mapper;
    private final QuestionHandler questionHandler;
    private final RequestStatusProducer statusProducer;


    public TopicListener(
            ObjectMapper mapper,
            final QuestionHandler questionHandler,
            final RequestStatusProducer statusProducer) {
        this.mapper = mapper;
        this.questionHandler = questionHandler;
        this.statusProducer = statusProducer;
    }

    @KafkaListener(
            topics = "${kafkadef.topics.questionbank.request}",
            containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(final String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        log.debug("Processing kafka message: {}", message);

        try {
            var request = mapper.readValue(message, QuestionBankRequest.class);
            log.debug("Successfully parsed message to QuestionBank. RequestId: {}", request.requestId());

            if (request instanceof QuestionRequest questionRequest) {
                questionHandler.handleQuestionRequest(questionRequest);
            } else {
                receivedInvalidRequest(key, request);
            }


        } catch (JsonProcessingException e) {
            failedParsing(key, e);
        }
    }

    private void receivedInvalidRequest(final String key, final QuestionBankRequest request) {
        statusProducer.failure(key, "Invalid EventType specified.");
        log.warn("Invalid EventType specified: {}", request.type());
        throw new RequestException("Invalid EventType specified", key);
    }

    private void failedParsing(final String key, final JsonProcessingException exception) {
        log.warn("Failed to parse kafka message. Key: {}, Message: {}", key, exception.getMessage());
        throw new RequestException("Failed to parse message into QuestionBankRequest", key);
    }

}
