package gov.cdc.nbs.patient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.concurrent.TimeUnit;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.service.KafkaTestConsumer;
import gov.cdc.nbs.support.PersonMother;
import gov.cdc.nbs.support.util.PersonUtil;
import gov.cdc.nbs.support.util.UserUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PatientCreateSteps {

    @Autowired
    private PatientController patientController;

    @Autowired
    private KafkaTestConsumer consumer;

    @Autowired
    private ObjectMapper mapper;

    private PatientEventResponse createPersonRequestId;
    private PatientInput input;
    private AccessDeniedException accessDeniedException;

    @When("I send a create patient request")
    public void i_send_a_create_patient_request() {
        input = PersonUtil.convertToPatientInput(PersonMother.generateRandomPerson(1234));
        try {
            createPersonRequestId = patientController.createPatient(input);
        } catch (AccessDeniedException e) {
            accessDeniedException = e;
        }
    }

    @Then("I get an access denied exception")
    public void i_get_an_access_denied_exception() {
        assertNotNull(accessDeniedException);
        assertNull(createPersonRequestId);
    }

    @Then("the patient create request is posted to kafka")
    public void the_patient_create_request_is_posted_to_kafka()
            throws InterruptedException, JsonMappingException, JsonProcessingException {
        assertNull(accessDeniedException);
        assertNotNull(createPersonRequestId);
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

        assertTrue(messageConsumed);

        var key = consumer.getKey();
        assertEquals(createPersonRequestId.getRequestId(), key);

        var payload = mapper.readValue((String) consumer.getPayload(), PatientCreateData.class);
        assertNotNull(payload);

        assertEquals(createPersonRequestId.getRequestId(), payload.request());
        var currentUserId = UserUtil.getCurrentUserId();

        assertThat(payload.createdBy()).isEqualTo(currentUserId);

        assertThat(payload.ssn()).isEqualTo(input.getSsn());
        assertThat(payload.dateOfBirth()).isEqualTo(input.getDateOfBirth());
        assertThat(payload.birthGender()).isEqualTo(input.getBirthGender());
        assertThat(payload.currentGender()).isEqualTo(input.getCurrentGender());

        assertThat(payload.deceased()).isEqualTo(input.getDeceased());
        assertThat(payload.deceasedTime()).isEqualTo(input.getDeceasedTime());

        assertThat(payload.ethnicity()).isEqualTo(input.getEthnicityCode());
    }
}
