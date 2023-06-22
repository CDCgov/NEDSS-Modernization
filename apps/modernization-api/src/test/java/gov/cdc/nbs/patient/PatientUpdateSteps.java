package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.patient.input.AdministrativeInput;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class PatientUpdateSteps {

    @Autowired
    PatientController patientController;

    @Autowired
    TestPatientIdentifier patients;

    private PatientEventResponse response;
    private AccessDeniedException accessDeniedException;
    private Object input;

    @When("I send a {string} update request")
    public void i_send_a_update_general_info_patient_request(final String updateType) {
        PatientIdentifier patient = patients.one();
        try {
            if (updateType.equals("administrative")) {
                input = createAdministrativeInput(patient.id());
                response = patientController.updateAdministrative((AdministrativeInput) input);
            }
        } catch (AccessDeniedException e) {
            accessDeniedException = e;
        }
    }

    @Then("the request has a response")
    public void the_general_info_update_request_is_posted_to_kafka() {
        assertNull(accessDeniedException);
        assertNotNull(response);
    }

    @Then("I get an access denied exception for patient update")
    public void I_get_an_access_denied_exception_for_patient_update() {
        assertNull(response);
        assertNotNull(accessDeniedException);
    }

    private AdministrativeInput createAdministrativeInput(final long patient) {
        var input = new AdministrativeInput();
        input.setPatientId(patient);
        input.setDescription("Description 1");
        return input;
    }
}
