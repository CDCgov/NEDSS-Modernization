package gov.cdc.nbs.patientlistener.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.patient.event.PatientRequest;
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

    private final PatientUpdateRequestHandler updateHandler;
    private final PatientRequestStatusProducer statusProducer;

    public PatientRequestTopicListener(
        ObjectMapper mapper,
        PatientUpdateRequestHandler updateHandler,
        PatientRequestStatusProducer statusProducer) {
        this.mapper = mapper;
        this.updateHandler = updateHandler;
        this.statusProducer = statusProducer;
    }

    @KafkaListener(topics = "${kafkadef.topics.patient.request}", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(final String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        log.debug("Processing kafka message: {}", message);

        try {
            var request = mapper.readValue(message, PatientRequest.class);
            log.debug("Successfully parsed message to PatientEvent. RequestId: {}", request.requestId());

            if (request instanceof PatientRequest.UpdateGeneralInfo update) {
                updateHandler.handlePatientGeneralInfoUpdate(update.data());
            } else if (request instanceof PatientRequest.UpdateMortality update) {
                updateHandler.handlePatientMortalityUpdate(update.data());
            } else if (request instanceof PatientRequest.UpdateSexAndBirth update) {
                updateHandler.handlePatientSexAndBirthUpdate(update.data());
            } else if (request instanceof PatientRequest.UpdateAdministrative update) {
                updateHandler.handlePatientAdministrativeUpdate(update.data());
            } else if (request instanceof PatientRequest.AddName update) {
                updateHandler.handlePatientNameUpdate(update.data());
            } else if (request instanceof PatientRequest.UpdateName update) {
                updateHandler.handlePatientNameUpdate(update.data());
            } else if (request instanceof PatientRequest.DeleteName delete) {
                updateHandler.handlePatientNameDelete(delete.requestId(), delete.patientId(), delete.personNameSeq(),
                    delete.userId());
            } else if (request instanceof PatientRequest.AddAddress update) {
                updateHandler.handlePatientAddressAdd(update.data());
            } else if (request instanceof PatientRequest.UpdateAddress update) {
                updateHandler.handlePatientAddressUpdate(update.data());
            } else if (request instanceof PatientRequest.DeleteAddress delete) {
                updateHandler.handlePatientAddressDelete(delete.requestId(), delete.patientId(), delete.id(),
                    delete.userId());

            } else if (request instanceof PatientRequest.AddEmail update) {
                updateHandler.handlePatientEmailAdd(update.data());
            } else if (request instanceof PatientRequest.UpdateEmail update) {
                updateHandler.handlePatientEmailUpdate(update.data());
            } else if (request instanceof PatientRequest.DeleteEmail delete) {
                updateHandler.handlePatientEmailDelete(delete.requestId(), delete.patientId(), delete.id(),
                    delete.userId());

            } else if (request instanceof PatientRequest.AddIdentification update) {
                updateHandler.handlePatientIdentificationAdd(update.data());
            } else if (request instanceof PatientRequest.UpdateIdentification update) {
                updateHandler.handlePatientIdentificationUpdate(update.data());
            } else if (request instanceof PatientRequest.DeleteIdentification delete) {
                updateHandler.handlePatientIdentificationDelete(delete.requestId(), delete.patientId(), delete.id(),
                    delete.userId());

            } else if (request instanceof PatientRequest.AddPhone update) {
                updateHandler.handlePatientPhoneAdd(update.data());
            } else if (request instanceof PatientRequest.UpdatePhone update) {
                updateHandler.handlePatientPhoneUpdate(update.data());
            } else if (request instanceof PatientRequest.DeletePhone delete) {
                updateHandler.handlePatientPhoneDelete(delete.requestId(), delete.patientId(), delete.id(),
                    delete.userId());
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
        throw new PatientRequestException("Invalid EventType specified", key);
    }

    private void failedParsing(final String key, final JsonProcessingException exception) {
        log.warn("Failed to parse kafka message. Key: {}, Message: {}", key, exception.getMessage());
        throw new PatientRequestException("Failed to parse message into PatientRequest", key);
    }
}
