package gov.cdc.nbs.patient;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.message.patient.event.PatientEvent.PatientEventType;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.message.patient.input.GeneralInfoInput;
import gov.cdc.nbs.message.patient.input.MortalityInput;
import gov.cdc.nbs.message.patient.input.SexAndBirthInput;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.service.KafkaTestConsumer;
import gov.cdc.nbs.support.PersonMother;
import gov.cdc.nbs.support.util.PersonUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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
                case "general info":
                    input = PersonUtil.convertToGeneralInput(PersonMother.generateRandomPerson(123L));
                    response = patientController.updatePatientGeneralInfo((GeneralInfoInput) input);
                    break;
                case "sex and birth":
                    input = PersonUtil.convertToSexAndBirthInput(PersonMother.generateRandomPerson(123L));
                    response = patientController.updatePatientSexBirth((SexAndBirthInput) input);
                    break;
                case "mortality":
                    input = createMortalityInput();
                    response = patientController.updateMortality((MortalityInput) input);
                    break;
            }
        } catch (AccessDeniedException e) {
            accessDeniedException = e;
        }
    }

    @Then("the {string} update request is posted to kafka")
    public void the_general_info_update_request_is_posted_to_kafka(String updateType)
            throws InterruptedException, JsonMappingException, JsonProcessingException {
        assertNull(accessDeniedException);
        assertNotNull(response);
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

        assertTrue(messageConsumed);

        var key = consumer.getKey();
        assertEquals(response.getRequestId(), key);

        var payload = mapper.readValue((String) consumer.getPayload(), PatientEvent.class);

        assertEquals(response.getRequestId(), payload.requestId());
        assertEquals(response.getPatientId(), payload.patientId());
        assertEquals(getCurrentUserId(), payload.userId());

        switch (updateType) {
            case "general info":
                assertEquals(PatientEventType.UPDATE_GENERAL_INFO, payload.eventType());
                validateGeneralInfo((UpdateGeneralInfoData) payload.data());
                break;
            case "sex and birth":
                assertEquals(PatientEventType.UPDATE_SEX_AND_BIRTH, payload.eventType());
                validateSexAndBirthInfo((UpdateSexAndBirthData) payload.data());
                break;
            case "mortality":
                assertEquals(PatientEventType.UPDATE_MORTALITY, payload.eventType());
                validateMortalityInfo((UpdateMortalityData) payload.data());
                break;
        }


    }

    private MortalityInput createMortalityInput() {
        var input = new MortalityInput();
        input.setPatientId(1234L);
        input.setAsOf(Instant.now());
        input.setDeceased(Deceased.Y);
        input.setDeceasedTime(Instant.now());
        input.setCityOfDeath("Deceased City");
        input.setStateOfDeath("Deceased State");
        input.setCountyOfDeath("Deceased County");
        input.setCountryOfDeath("Deceased Country");
        return input;
    }


    private void validateSexAndBirthInfo(UpdateSexAndBirthData data) {
        var sexAndBirthInput = (SexAndBirthInput) input;
        assertEquals(sexAndBirthInput.getAsOf().getEpochSecond(), data.asOf().getEpochSecond());
        assertEquals(sexAndBirthInput.getPatientId(), data.patientId());
        assertEquals(sexAndBirthInput.getDateOfBirth().getEpochSecond(), data.dateOfBirth().getEpochSecond());
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

    private void validateGeneralInfo(UpdateGeneralInfoData data) {
        var generalInfoInput = (GeneralInfoInput) input;
        assertEquals(generalInfoInput.getPatientId(), data.patientId());
        assertEquals(response.getRequestId(), data.requestId());
        assertEquals(getCurrentUserId(), data.updatedBy());
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

    private void validateMortalityInfo(UpdateMortalityData data) {
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

    private Long getCurrentUserId() {
        return ((NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getId();
    }
}
