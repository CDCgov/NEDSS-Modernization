package gov.cdc.nbs.patientlistener.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.patientlistener.request.create.PatientCreateRequestHandler;
import gov.cdc.nbs.patientlistener.request.delete.PatientDeleteRequestHandler;
import gov.cdc.nbs.patientlistener.request.update.PatientUpdateRequestHandler;
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
    private final PatientRequestStatusProducer statusProducer;

    public PatientRequestTopicListener(
            ObjectMapper mapper,
            PatientCreateRequestHandler createHandler,
            PatientUpdateRequestHandler updateHandler,
            PatientDeleteRequestHandler deleteHandler,
            PatientRequestStatusProducer statusProducer
    ) {
        this.mapper = mapper;
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
        this.statusProducer = statusProducer;
    }

    @KafkaListener(topics = "${kafkadef.topics.patient.request}", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(final String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        log.debug("Processing kafka message: {}", message);

        try {
            var request = mapper.readValue(message, PatientRequest.class);
            log.debug("Successfully parsed message to PatientEvent. RequestId: {}", request.requestId());

            if (request instanceof PatientRequest.Create create) {
                createHandler.handlePatientCreate(create.data());
            } else if (request instanceof PatientRequest.Delete delete) {
                deleteHandler.handlePatientDelete(delete.requestId(), delete.patientId(), delete.userId());
            } else if (request instanceof PatientRequest.UpdateGeneralInfo update) {
                updateHandler.handlePatientGeneralInfoUpdate(update.data());
            }else if (request instanceof PatientRequest.UpdateMortality update) {
                updateHandler.handlePatientMortalityUpdate(update.data());
            }else if (request instanceof PatientRequest.UpdateSexAndBirth update) {
                updateHandler.handlePatientSexAndBirthUpdate(update.data());
            }
            else {
                receivedInvalidRequest(key, request);
            }

        } catch (JsonProcessingException e) {
            failedParsing(key, e);
        }
    }

    private void receivedInvalidRequest(final String key, final PatientRequest request) {
        statusProducer.failure(key, "Invalid EventType specified.");
        log.warn("Invalid EventType specified: {}", request.type());
        throw new PatientRequestException("Invalid EventType specified", key);
    }

    private void failedParsing(final String key, final JsonProcessingException exception) {
      log.warn("Failed to parse kafka message. Key: {}, Message: {}", key, exception.getMessage());
      throw new PatientRequestException("Failed to parse message into PatientRequest", key);
    }

}
