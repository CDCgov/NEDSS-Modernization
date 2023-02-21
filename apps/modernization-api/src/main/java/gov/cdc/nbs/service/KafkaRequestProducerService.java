package gov.cdc.nbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import gov.cdc.nbs.message.EnvelopeRequest;
import gov.cdc.nbs.message.PatientDeleteRequest;
import gov.cdc.nbs.message.PatientUpdateRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaRequestProducerService {

	@Autowired
	private KafkaTemplate<String, EnvelopeRequest> kafkaEnvelopeTemplate;

	@Autowired
	private KafkaTemplate<String, PatientUpdateRequest> kafkaPatientUpdateTemplate;

	@Autowired
	private KafkaTemplate<String, PatientDeleteRequest> kafkaPatientDeleteTemplate;

	@Value("${kafkadef.patient-search.topics.request.patient}")
	private String patientSearchTopic;

	@Value("${kafkadef.patient-search.topics.request.patientupdate}")
	private String patientUpdateTopic;

	@Value("${kafkadef.patient-search.topics.request.patientdelete}")
	private String patientDeleteTopic;

	public void requestEnvelope(EnvelopeRequest kafkaMessage) {
		send(kafkaEnvelopeTemplate, patientSearchTopic, kafkaMessage.getRequestId(), kafkaMessage);

	}

	public void requestPatientUpdateEnvelope(PatientUpdateRequest kafkaMessage) {
		try {
		send(kafkaPatientUpdateTemplate, patientUpdateTopic, kafkaMessage.getRequestId(), kafkaMessage);
		}
		catch(Exception e) {
			log.error("Error sending patientUpdate Kafka message", e);
		}
	}

	public void requestPatientDeleteEnvelope(PatientDeleteRequest kafkaMessage) {
		send(kafkaPatientDeleteTemplate, patientDeleteTopic, kafkaMessage.getRequestId(), kafkaMessage);
	}

	private <K, V> void send(KafkaTemplate<K, V> template, String topic, K key, V event) {
		ListenableFuture<SendResult<K, V>> future = template.send(topic, key, event);
		future.addCallback(new ListenableFutureCallback<>() {
			@Override
			public void onFailure(Throwable ex) {
				log.error("Failed to send message key={} message={} error={}", key, event, ex);
			}

			@Override
			public void onSuccess(SendResult<K, V> result) {
				log.info("Sent message key={} message={} result={}", key, event, result);
			}

		});

	}

}
