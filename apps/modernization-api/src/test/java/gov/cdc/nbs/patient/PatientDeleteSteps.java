package gov.cdc.nbs.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.cdc.nbs.authorization.TestActiveUser;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.service.KafkaTestConsumer;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PatientDeleteSteps {

    @Autowired
    TestActiveUser activeUser;

    @Autowired
    TestPatientIdentifier patients;

    @Autowired
    PatientController patientController;

    @Autowired
    KafkaTestConsumer consumer;

    @Before
    public void reset() {
        consumer.reset();
        response = null;
        accessDeniedException = null;
    }

    private PatientEventResponse response;
    private AccessDeniedException accessDeniedException;

    @When("I send a patient delete request")
    public void i_send_a_patient_delete_request() {

        long patient = patients.one().id();

        try {
            response = patientController.deletePatient(patient);
        } catch (AccessDeniedException e) {
            accessDeniedException = e;
        }
    }

    @Then("the delete request is posted to kafka")
    public void the_delete_request_is_posted_to_kafka()
        throws JsonProcessingException, InterruptedException {
        assertNull(accessDeniedException);
        assertNotNull(response);

        assertThat(consumer.consumed()).isTrue();

        var key = consumer.getKey();

        assertThat(response.getRequestId()).isEqualTo(key);

        var payload = consumer.payload(PatientRequest.Delete.class);

        assertThat(payload.requestId()).isEqualTo(response.getRequestId());
        assertThat(payload.patientId()).isEqualTo(response.getPatientId());
        assertThat(payload.userId()).isEqualTo(activeUser.active().id());

    }

    @Then("I get an access denied exception for patient delete")
    public void I_get_an_access_denied_exception_for_patient_delete() {
        assertNull(response);
        assertNotNull(accessDeniedException);
    }
}
