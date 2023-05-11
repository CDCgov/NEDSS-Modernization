package gov.cdc.nbs.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateAdministrativeData;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.message.patient.input.AdministrativeInput;
import gov.cdc.nbs.message.patient.input.GeneralInfoInput;
import gov.cdc.nbs.message.patient.input.MortalityInput;
import gov.cdc.nbs.message.patient.input.NameInput;
import gov.cdc.nbs.message.patient.input.SexAndBirthInput;
import gov.cdc.nbs.message.patient.input.PatientInput.NameUseCd;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.service.KafkaTestConsumer;
import gov.cdc.nbs.support.PersonMother;
import gov.cdc.nbs.support.util.PersonUtil;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.support.util.UserUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PatientUpdateSteps {
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
    private Object input;

    @When("I send a {string} update request")
    public void i_send_a_update_general_info_patient_request(final String updateType) {
        try {
            switch (updateType) {
                case "general info" -> {
                    input = PersonUtil.convertToGeneralInput(PersonMother.generateRandomPerson(123L));
                    response = patientController.updatePatientGeneralInfo((GeneralInfoInput) input);
                }
                case "sex and birth" -> {
                    input = PersonUtil.convertToSexAndBirthInput(PersonMother.generateRandomPerson(123L));
                    response = patientController.updatePatientSexBirth((SexAndBirthInput) input);
                }
                case "mortality" -> {
                    input = createMortalityInput(123L);
                    response = patientController.updateMortality((MortalityInput) input);
                }
                case "administrative" -> {
                    input = createAdministrativeInput(123L);
                    response = patientController.updateAdministrative((AdministrativeInput) input);
                }
                case "name" -> {
                    input = createNameInput(123L);
                    response = patientController.updatePatientName((NameInput) input);
                }
            }
        } catch (AccessDeniedException e) {
            accessDeniedException = e;
        }
    }

    @Then("the {string} update request is posted to kafka")
    public void the_general_info_update_request_is_posted_to_kafka(String updateType)
            throws InterruptedException, JsonProcessingException {
        assertNull(accessDeniedException);
        assertNotNull(response);
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

        assertTrue(messageConsumed);

        var key = consumer.getKey();
        assertEquals(response.getRequestId(), key);

        var payload = mapper.readValue((String) consumer.getPayload(), PatientRequest.class);

        assertEquals(response.getRequestId(), payload.requestId());
        assertEquals(response.getPatientId(), payload.patientId());
        assertEquals(UserUtil.getCurrentUserId(), payload.userId());

        switch (updateType) {
            case "general info" -> {
                validateGeneralInfo(payload);
            }
            case "sex and birth" -> {
                validateSexAndBirthInfo(payload);
            }
            case "mortality" -> {
                validateMortalityInfo(payload);
            }
        }
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
        input.setPersonNameSeq((short)1);
        input.setFirstName("First Name");
        input.setLastName("Last Name");
        input.setMiddleName("Middle Name");
        input.setNameUseCd("L");
        input.setSuffix(Suffix.III);
        return input;
    }

    private void validateSexAndBirthInfo(final PatientRequest request) {

        assertThat(request).isInstanceOf(PatientRequest.UpdateSexAndBirth.class);

        if(request instanceof PatientRequest.UpdateSexAndBirth update) {

            UpdateSexAndBirthData data = update.data();
            var sexAndBirthInput = (SexAndBirthInput) input;
            assertEquals(sexAndBirthInput.getAsOf().getEpochSecond(), data.asOf().getEpochSecond());
            assertEquals(sexAndBirthInput.getPatientId(), data.patientId());
            assertEquals(sexAndBirthInput.getDateOfBirth(), data.dateOfBirth());
            assertEquals(sexAndBirthInput.getBirthGender(), data.birthGender());
            assertEquals(sexAndBirthInput.getCurrentGender(), data.currentGender());
            assertEquals(sexAndBirthInput.getAdditionalGender(), data.additionalGender());
            assertEquals(sexAndBirthInput.getTransGenderInfo(), data.transGenderInfo());
            assertEquals(sexAndBirthInput.getBirthCity(), data.birthCity());
            assertEquals(sexAndBirthInput.getBirthCntry(), data.birthCntry());
            assertEquals(sexAndBirthInput.getBirthState(), data.birthState());
            assertEquals(sexAndBirthInput.getBirthOrderNbr(), data.birthOrderNbr());
            assertEquals(sexAndBirthInput.getMultipleBirth(), data.multipleBirth());
            assertEquals(sexAndBirthInput.getSexUnknown(), data.sexUnknown());
            assertEquals(sexAndBirthInput.getCurrentAge(), data.currentAge());
            assertEquals(sexAndBirthInput.getAgeReportedTime().getEpochSecond(), data.ageReportedTime().getEpochSecond());
        }
    }

    private void validateGeneralInfo(final PatientRequest request) {

        assertThat(request).isInstanceOf(PatientRequest.UpdateGeneralInfo.class);

        if(request instanceof PatientRequest.UpdateGeneralInfo update) {

            UpdateGeneralInfoData data = update.data();

            var generalInfoInput = (GeneralInfoInput) input;
            assertEquals(generalInfoInput.getPatientId(), data.patientId());
            assertEquals(response.getRequestId(), data.requestId());
            assertEquals(UserUtil.getCurrentUserId(), data.updatedBy());
            assertEquals(generalInfoInput.getAsOf().getEpochSecond(), data.asOf().getEpochSecond());
            assertEquals(generalInfoInput.getMaritalStatus(), data.maritalStatus());
            assertEquals(generalInfoInput.getMothersMaidenName(), data.mothersMaidenName());
            assertEquals(generalInfoInput.getAdultsInHouseNumber(), data.adultsInHouseNumber());
            assertEquals(generalInfoInput.getChildrenInHouseNumber(), data.childrenInHouseNumber());
            assertEquals(generalInfoInput.getOccupationCode(), data.occupationCode());
            assertEquals(generalInfoInput.getEducationLevelCode(), data.educationLevelCode());
            assertEquals(generalInfoInput.getPrimaryLanguageCode(), data.primaryLanguageCode());
            assertEquals(generalInfoInput.getSpeaksEnglishCode(), data.speaksEnglishCode());
            assertEquals(generalInfoInput.getEharsId(), data.eharsId());
        }
    }

    private void validateMortalityInfo(final PatientRequest request) {

        assertThat(request).isInstanceOf(PatientRequest.UpdateMortality.class);

        if (request instanceof PatientRequest.UpdateMortality update) {

            UpdateMortalityData data = update.data();

            var mortalityInput = (MortalityInput) input;
            assertEquals(mortalityInput.getPatientId(), data.patientId());
            assertEquals(mortalityInput.getAsOf().getEpochSecond(), data.asOf().getEpochSecond());
            assertEquals(mortalityInput.getDeceased(), data.deceased());
            assertEquals(mortalityInput.getDeceasedTime().getEpochSecond(), data.deceasedTime().getEpochSecond());
            assertEquals(mortalityInput.getCityOfDeath(), data.cityOfDeath());
            assertEquals(mortalityInput.getStateOfDeath(), data.stateOfDeath());
            assertEquals(mortalityInput.getCountyOfDeath(), data.countyOfDeath());
            assertEquals(mortalityInput.getCountryOfDeath(), data.countryOfDeath());
        }
    }
}
