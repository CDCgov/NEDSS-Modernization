package gov.cdc.nbs.patientlistener.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.patientlistener.message.PatientUpdateEvent;
import gov.cdc.nbs.patientlistener.message.PatientUpdateEventResponse;
import gov.cdc.nbs.patientlistener.message.TemplateInput;
import gov.cdc.nbs.patientlistener.odse.PatientInput;
import gov.cdc.nbs.patientlistener.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaEventConsumer {

	@Autowired
	private PatientService patientService;
	@Autowired
	private KafkaRequestProducerService responseProducerService;

	@KafkaListener(topics = "patientSearchTopic", groupId = "group_id")
	public void consume(String message) {
		log.info("message {} ", message);
	}

	@KafkaListener(topics = "patientDeleteTopic", groupId = "group_id")
	public void deleteConsume(String message) {
		log.info("message = {}", message);
	}

	@KafkaListener(topics = "${kafkadef.patient-search.topics.request.patientupdate}", groupId = "${kafka.consumer.group-id}", containerFactory = "kafkaPatientUpdateListenerContainerFactory")
	public void handlePatientUpdateEnvelopeEvent(ConsumerRecord<String, PatientUpdateEvent> message) {

		if (message != null && message.value() != null) {
			String requestId = message.key();

			if (requestId.startsWith(Constants.APP_ID)) {

				PatientInput input = message.value().getParams().getInput();
				Long personId = message.value().getParams().getPersonId();
				List<TemplateInput> templateInputs = message.value().getParams().getTemplateInputs();

				PatientUpdateEventResponse updateEventResponse = patientService.updatePatient(requestId, personId,
						input, templateInputs);

				responseProducerService.requestPatientUpdateEnvelope(updateEventResponse);

				log.info("Successfully received & processed envelope message.");

			}

		} else {
			log.error("Error Receiving Envelope status");
		}
	}

}