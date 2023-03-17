package gov.cdc.nbs.patientlistener.service;

import gov.cdc.nbs.message.*;
import gov.cdc.nbs.message.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

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

  @KafkaListener(
      topics = "${kafkadef.patient-search.topics.request.patientupdate}",
      groupId = "${kafka.consumer.group-id}",
      containerFactory = "concurrentKafkaListenerContainerFactory"
  )
  public void handlePatientUpdateEnvelopeEvent(ConsumerRecord<String, PatientUpdateEvent> message) {

    if (message != null && message.value() != null) {
      String requestId = message.key();

      if (requestId.startsWith(Constants.APP_ID)) {

        PatientInput input = message.value().getParams().getInput();

        UpdateMortality mortalityInput = message.value().getParams().getMortalityInput();

        UpdateSexAndBirth inputSexAndBirth = message.value().getParams().getSexAndBirthInput();

        List<TemplateInput> vars = message.value().getParams().getTemplateInputs();
        Long personId = message.value().getParams().getPersonId();

        PatientUpdateEventResponse updateEventResponse = patientService.updatePatient(requestId, personId,
            input, mortalityInput, inputSexAndBirth, vars);

        responseProducerService.requestPatientUpdateEnvelope(updateEventResponse);

        log.info("Successfully received & processed envelope message.");

      }

    } else {
      log.error("Error Receiving Envelope status");
    }
  }

}
