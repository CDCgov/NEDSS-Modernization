package gov.cdc.nbs.patient;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.TimeUnit;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.service.KafkaTestConsumer;
import gov.cdc.nbs.support.util.UserUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PatientDeleteSteps {

    @Autowired
    private PatientController patientController;

    @Autowired
    private KafkaTestConsumer consumer;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void resetConsumer() {
        consumer.resetLatch();
    }

    private PatientEventResponse response;
    private AccessDeniedException accessDeniedException;
    private long patientId = 123L;

    @When("I send a patient delete request")
    public void i_send_a_patient_delete_request() {
        try {
            response = patientController.deletePatient(patientId);
        } catch (AccessDeniedException e) {
            accessDeniedException = e;
        }
    }

    @Then("the delete request is posted to kafka")
    public void the_delete_request_is_posted_to_kafka()
            throws JsonMappingException, JsonProcessingException, InterruptedException {
        assertNull(accessDeniedException);
        assertNotNull(response);
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

        assertTrue(messageConsumed);

        var key = consumer.getKey();
        assertEquals(response.getRequestId(), key);

        var payload = mapper.readValue((String) consumer.getPayload(), PatientEvent.class);

        assertEquals(response.getRequestId(), payload.requestId());
        assertEquals(response.getPatientId(), payload.patientId());
        assertEquals(UserUtil.getCurrentUserId(), payload.userId());
    }

    @Then("I get an access denied exception for patient delete")
    public void I_get_an_access_denied_exception_for_patient_delete() {
        assertNull(response);
        assertNotNull(accessDeniedException);
    }
}
