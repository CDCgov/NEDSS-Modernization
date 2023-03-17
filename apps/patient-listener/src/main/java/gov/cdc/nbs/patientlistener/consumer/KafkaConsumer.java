package gov.cdc.nbs.patientlistener.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.RequestStatus;
import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.patientlistener.service.PatientCreateRequestHandler;
import gov.cdc.nbs.patientlistener.service.PatientDeleteRequestHandler;
import gov.cdc.nbs.patientlistener.service.PatientUpdateRequestHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumer {

    @Value("${kafkadef.topics.status.patient}")
    private String statusTopic;

    private final ObjectMapper mapper;
    private final PatientCreateRequestHandler createHandler;
    private final PatientUpdateRequestHandler updateHandler;
    private final PatientDeleteRequestHandler deleteHandler;
    private final KafkaTemplate<String, RequestStatus> statusTemplate;

    public KafkaConsumer(ObjectMapper mapper,
            PatientCreateRequestHandler createHandler,
            PatientUpdateRequestHandler updateHandler,
            PatientDeleteRequestHandler deleteHandler,
            KafkaTemplate<String, RequestStatus> statusTemplate) {
        this.mapper = mapper;
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
        this.statusTemplate = statusTemplate;
    }


    @KafkaListener(topics = "patient", groupId = "group")
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
                    deleteHandler.handlePatientDelete(event.patientId(), event.userId());
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
                    sendStatusMessage(false, "Invalid EventType specified.", key);
                    log.warn("Invalid EventType specified: {}", event.eventType());
                    throw new IllegalArgumentException("Invalid EventType specified");
            }
        } catch (JsonProcessingException e) {
            log.warn("Failed to parse kafka message: {}", e.getMessage());
            sendStatusMessage(false, "Failed to parse kafka message", key);
        }
    }

    private void sendStatusMessage(boolean success, String message, String requestId) {
        statusTemplate.send(
                statusTopic,
                RequestStatus
                        .builder()
                        .successful(success)
                        .message(message)
                        .requestId(requestId)
                        .build());
    }
}
