package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.message.patient.input.AddressInput;
import gov.cdc.nbs.message.patient.input.AdministrativeInput;
import gov.cdc.nbs.message.patient.input.EmailInput;
import gov.cdc.nbs.message.patient.input.GeneralInfoInput;
import gov.cdc.nbs.message.patient.input.IdentificationInput;
import gov.cdc.nbs.message.patient.input.MortalityInput;
import gov.cdc.nbs.message.patient.input.NameInput;
import gov.cdc.nbs.message.patient.input.PatientInput.PhoneType;
import gov.cdc.nbs.message.patient.input.PhoneInput;
import gov.cdc.nbs.message.patient.input.SexAndBirthInput;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.PersonMother;
import gov.cdc.nbs.support.util.PersonUtil;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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
            switch (updateType) {
                case "general info" -> {
                    input = PersonUtil.convertToGeneralInput(PersonMother.generateRandomPerson(patient.id()));
                    response = patientController.updatePatientGeneralInfo((GeneralInfoInput) input);
                }
                case "sex and birth" -> {
                    input = PersonUtil.convertToSexAndBirthInput(PersonMother.generateRandomPerson(patient.id()));
                    response = patientController.updatePatientSexBirth((SexAndBirthInput) input);
                }
                case "mortality" -> {
                    input = createMortalityInput(patient.id());
                    response = patientController.updateMortality((MortalityInput) input);
                }
                case "administrative" -> {
                    input = createAdministrativeInput(patient.id());
                    response = patientController.updateAdministrative((AdministrativeInput) input);
                }
                case "name" -> {
                    input = createNameInput(patient.id());
                    response = patientController.updatePatientName((NameInput) input);
                }
                case "address" -> {
                    input = createAddressInput(patient.id());
                    response = patientController.updatePatientAddress((AddressInput) input);
                }
                case "email" -> {
                    input = createEmailInput(patient.id());
                    response = patientController.updatePatientEmail((EmailInput) input);
                }
                case "identification" -> {
                    input = createIdentificationInput(patient.id());
                    response = patientController.updatePatientIdentification((IdentificationInput) input);
                }
                case "phone" -> {
                    input = createPhoneInput(patient.id());
                    response = patientController.updatePatientPhone((PhoneInput) input);
                }

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

    private MortalityInput createMortalityInput(final long patient) {
        var input = new MortalityInput();
        input.setPatientId(patient);
        input.setAsOf(RandomUtil.getRandomDateInPast());
        input.setDeceased(Deceased.Y);
        input.setDeceasedTime(Instant.now());
        input.setCityOfDeath("Deceased City");
        input.setStateOfDeath("Deceased State");
        input.setCountyOfDeath("Deceased County");
        input.setCountryOfDeath("Deceased Country");
        return input;
    }

    private AdministrativeInput createAdministrativeInput(final long patient) {
        var input = new AdministrativeInput();
        input.setPatientId(patient);
        input.setDescription("Description 1");
        return input;
    }

    private NameInput createNameInput(final long patient) {
        var input = new NameInput();
        input.setPatientId(patient);
        input.setPersonNameSeq((short) 1);
        input.setFirstName("First Name");
        input.setLastName("Last Name");
        input.setMiddleName("Middle Name");
        input.setNameUseCd("L");
        input.setSuffix(Suffix.III);
        return input;
    }

    private AddressInput createAddressInput(final long patient) {
        var input = new AddressInput();
        input.setPatientId(patient);
        input.setStreetAddress1("SA1");
        input.setStreetAddress2("SA2");
        input.setCity("City");
        input.setStateCode("State");
        input.setCountyCode("County");
        input.setCountryCode("840");
        input.setZip("Zip");
        input.setCensusTract("Census Tract");
        return input;
    }

    private EmailInput createEmailInput(final long patient) {
        var input = new EmailInput();
        input.setPatientId(patient);
        input.setId((short) 1);
        input.setEmailAddress("First Email");
        return input;
    }

    private IdentificationInput createIdentificationInput(final long patient) {
        var input = new IdentificationInput();
        input.setPatientId(patient);
        input.setId((short) 1);
        input.setAssigningAuthority("assigning authority");
        input.setIdentificationNumber("id number");
        input.setIdentificationType("id type");
        return input;
    }

    private PhoneInput createPhoneInput(final long patient) {
        var input = new PhoneInput();
        input.setPatientId(patient);
        input.setId((short) 1);
        input.setNumber("3145551212");
        input.setExtension("123");
        input.setPhoneType(PhoneType.CELL);
        return input;
    }

}
