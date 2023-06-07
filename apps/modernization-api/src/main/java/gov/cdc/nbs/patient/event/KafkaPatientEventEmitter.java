package gov.cdc.nbs.patient.event;

import gov.cdc.nbs.message.patient.event.PatientEvent;
import org.springframework.kafka.core.KafkaTemplate;

class KafkaPatientEventEmitter implements PatientEventEmitter {

    private final String topic;
    private final KafkaTemplate<String, PatientEvent> template;

    KafkaPatientEventEmitter(
        final String topic,
        final KafkaTemplate<String, PatientEvent> template
    ) {
        this.topic = topic;
        this.template = template;
    }

    @Override
    public void emit(final PatientEvent event) {
        template.send(topic, event.localId(), event);
    }
}
