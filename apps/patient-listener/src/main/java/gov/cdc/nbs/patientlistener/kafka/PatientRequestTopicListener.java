package gov.cdc.nbs.patientlistener.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.patientlistener.exception.KafkaException;
import gov.cdc.nbs.patientlistener.service.PatientCreateRequestHandler;
import gov.cdc.nbs.patientlistener.service.PatientDeleteRequestHandler;
import gov.cdc.nbs.patientlistener.service.PatientUpdateRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PatientRequestTopicListener {
    private final ObjectMapper mapper;
    private final PatientCreateRequestHandler createHandler;
    private final PatientUpdateRequestHandler updateHandler;
    private final PatientDeleteRequestHandler deleteHandler;
    private final StatusProducer statusProducer;

    public PatientRequestTopicListener(
            ObjectMapper mapper,
            PatientCreateRequestHandler createHandler,
            PatientUpdateRequestHandler updateHandler,
            PatientDeleteRequestHandler deleteHandler,
            StatusProducer statusProducer
    ) {
        this.mapper = mapper;
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
        this.statusProducer = statusProducer;
    }

    @KafkaListener(topics = "${kafkadef.topics.request.patient}", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(final String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        log.debug("Processing kafka message: {}", message);

        try {
            var request = mapper.readValue(message, PatientRequest.class);
            log.debug("Successfully parsed message to PatientEvent. RequestId: {}", request.requestId());

            if (request instanceof PatientRequest.Create create) {
                createHandler.handlePatientCreate(create.data());
            } else if (request instanceof PatientRequest.Delete delete) {
                deleteHandler.handlePatientDelete(delete.requestId(), delete.patientId(), delete.userId());

            } else {
                receivedInvalidRequest(key, request);
            }

        } catch (JsonProcessingException e) {
            failedParsing(key, e);
        }
    }

    private void receivedInvalidRequest(final String key, final PatientRequest request) {
        statusProducer.failure(key, "Invalid EventType specified.");
        log.warn("Invalid EventType specified: {}", request.type());
        throw new KafkaException("Invalid EventType specified", key);
    }

    private void failedParsing(final String key, final JsonProcessingException exception) {
      log.warn("Failed to parse kafka message. Key: {}, Message: {}", key, exception.getMessage());
      throw new KafkaException("Failed to parse message into PatientRequest", key);
    }

    @KafkaListener(topics = "${kafkadef.topics.request.patient}", containerFactory = "kafkaListenerContainerFactory")
    public void listenToPatientTopic(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        log.debug("Processing kafka message: {}", message);
        try {
            var event = mapper.readValue(message, PatientEvent.class);
            log.debug("Successfully parsed message to PatientEvent. RequestId: {}", event.requestId());
            switch (event.eventType()) {
                case CREATE:
                    createHandler.handlePatientCreate((PatientCreateData) event.data());
                    break;
                case DELETE:
                    deleteHandler.handlePatientDelete(event.requestId(), event.patientId(), event.userId());
                    break;
                case UPDATE_GENERAL_INFO:
                    updateHandler.handlePatientGeneralInfoUpdate((UpdateGeneralInfoData) event.data());
                    break;
                case UPDATE_MORTALITY:
                    updateHandler.handlePatientMortalityUpdate((UpdateMortalityData) event.data());
                    break;
                case UPDATE_SEX_AND_BIRTH:
                    updateHandler.handlePatientSexAndBirthUpdate((UpdateSexAndBirthData) event.data());
                    break;
                default:
                    statusProducer.failure(key, "Invalid EventType specified.");
                    log.warn("Invalid EventType specified: {}", event.eventType());
                    throw new KafkaException("Invalid EventType specified", key);
            }
        } catch (JsonProcessingException e) {
            log.warn("Failed to parse kafka message. Key: {}, Message: {}", key, e.getMessage());
            throw new KafkaException("Failed to parse message into PatientEvent", key);
        }
    }
}
