package gov.cdc.nbs.patient.event;

import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.kafka.PatientKafkaTestConsumer;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

public class PatientEventSteps {

  @Autowired
  Active<ActiveUser> activeUser;

  @Autowired
  Available<PatientIdentifier> patients;

  @Autowired
  PatientKafkaTestConsumer consumer;

  @Before
  public void reset() {
    consumer.reset();
  }

  @Then("a patient event is not emitted")
  public void a_patient_event_is_not_emitted() {
    consumer.isEmpty();
  }

  @Then("the patient create event is emitted")
  public void the_patient_create_event_is_emitted() {
    PatientIdentifier patient = patients.one();
    ActiveUser user = activeUser.active();

    consumer.satisfies(
        actual -> assertThat(actual).satisfiesExactly(
            actual_event -> assertThat(actual_event)
                .returns(patient.local(), PatientKafkaTestConsumer.Message::key)
                .extracting(PatientKafkaTestConsumer.Message::event)
                .asInstanceOf(type(PatientEvent.Created.class))
                .returns(patient.id(), PatientEvent::patient)
                .returns(patient.local(), PatientEvent::localId)
                .returns(user.id(), PatientEvent.Created::createdBy)
        )
    );
  }

  @Then("the patient delete event is emitted")
  public void the_patient_delete_event_is_emitted() {

    PatientIdentifier patient = patients.one();
    ActiveUser user = activeUser.active();

    consumer.satisfies(
        actual -> assertThat(actual).satisfiesExactly(
            actual_event -> assertThat(actual_event)
                .returns(patient.local(), PatientKafkaTestConsumer.Message::key)
                .extracting(PatientKafkaTestConsumer.Message::event)
                .asInstanceOf(type(PatientEvent.Deleted.class))
                .returns(patient.id(), PatientEvent.Deleted::patient)
                .returns(patient.local(), PatientEvent.Deleted::localId)
                .returns(user.id(), PatientEvent.Deleted::deletedBy)
        )
    );

  }

  @Then("the patient ethnicity changed event is emitted")
  public void the_patient_ethnicity_changed_event_is_emitted() {

    PatientIdentifier patient = patients.one();
    ActiveUser user = activeUser.active();

    consumer.satisfies(
        actual -> assertThat(actual).satisfiesExactly(
            actual_event -> assertThat(actual_event)
                .returns(patient.local(), PatientKafkaTestConsumer.Message::key)
                .extracting(PatientKafkaTestConsumer.Message::event)
                .asInstanceOf(type(PatientEvent.EthnicityChanged.class))
                .returns(patient.id(), PatientEvent::patient)
                .returns(patient.local(), PatientEvent::localId)
                .returns(user.id(), PatientEvent.EthnicityChanged::changedBy)
        )
    );


  }

}
