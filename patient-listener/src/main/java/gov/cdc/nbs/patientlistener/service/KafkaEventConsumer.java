package gov.cdc.nbs.patientlistener.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.patientlistener.message.PatientUpdateEvent;
import gov.cdc.nbs.patientlistener.message.PatientUpdateEventResponse;
import gov.cdc.nbs.patientlistener.message.PatientUpdateParams;
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

	@KafkaListener(topics = "${kafkadef.patient-search.topics.request.patientupdate}", groupId = "${kafka.consumer.group-id}", containerFactory = "kafkaPatientUpdateListenerContainerFactory")
	public void handlePatientUpdateEnvelopeEvent(ConsumerRecord<String, PatientUpdateEvent> message) {
		log.info("RECIEVED CONSUMER MESSAGE {}: ",  message);
		
		String requestId = message.value().getRequestId();
		boolean success = message.value().isSuccess();
		

		if (requestId.startsWith(Constants.APP_ID)) {

			log.info("Handling patientUpdate event: requestId={} success={}", requestId, success);

			log.info("Consumed message {}", message.value());

			if (success) {
				PatientUpdateParams patientParams = message.value().getParams();
				PatientUpdateEventResponse updateEventRespons =
						patientService.updatePatient(patientParams,requestId);

				responseProducerService.requestPatientUpdateEnvelope(updateEventRespons);
				log.info("Successfully Received Envelope status {}", success);
			} else {
				log.error("Error Receiving Envelope status {}", success);
			}

		}
	}

}