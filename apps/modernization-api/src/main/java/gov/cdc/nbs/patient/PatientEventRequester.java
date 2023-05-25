package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.patient.kafka.KafkaProducer;
import org.springframework.stereotype.Component;

@Component
public class PatientEventRequester {

    private final KafkaProducer producer;

    public PatientEventRequester(final KafkaProducer producer) {
        this.producer = producer;
    }

    public PatientEventResponse request(final PatientRequest request) {
        producer.requestPatientEventEnvelope(request);
        return new PatientEventResponse(request.requestId(), request.patientId());
    }

}
