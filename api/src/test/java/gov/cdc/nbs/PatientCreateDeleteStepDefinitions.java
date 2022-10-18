package gov.cdc.nbs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import gov.cdc.nbs.controller.PatientController;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.graphql.input.PatientInput;
import gov.cdc.nbs.graphql.input.PatientInput.Name;
import gov.cdc.nbs.graphql.input.PatientInput.PhoneNumber;
import gov.cdc.nbs.graphql.input.PatientInput.PostalAddress;
import gov.cdc.nbs.graphql.input.PatientInput.PhoneType;
import gov.cdc.nbs.graphql.searchFilter.PatientFilter;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.TeleLocatorRepository;
import gov.cdc.nbs.support.PersonMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PatientCreateDeleteStepDefinitions {

    @Test
    public void debug() {
    }

    @Autowired
    PersonRepository personRepository;
    @Autowired
    TeleLocatorRepository teleLocatorRepository;
    @Autowired
    PostalLocatorRepository postalLocatorRepository;
    @Autowired
    private PatientController patientController;

    private Person person;

    @Given("A patient does not exist")
    public void a_patient_does_not_exist() {
        person = PersonMother.johnDoe();
        var pl = (PostalLocator) person.getNBSEntity().getEntityLocatorParticipations().stream()
                .filter(elp -> elp.getClassCd().equals("PST")).findFirst().get().getLocator();
        var tl = (TeleLocator) person.getNBSEntity().getEntityLocatorParticipations().stream()
                .filter(elp -> elp.getClassCd().equals("TELE")).findFirst().get().getLocator();
        var filter = new PatientFilter();
        filter.setFirstName(person.getFirstNm());
        filter.setLastName(person.getLastNm());
        filter.setAddress(pl.getStreetAddr1());
        filter.setPhoneNumber(tl.getPhoneNbrTxt());
        var existing = patientController.findPatientsByFilter(filter, null);
        if (existing.size() > 0) {
            personRepository.deleteAll(existing);
        }
    }

    @When("I send a create patient request")
    public void i_send_a_create_patient_request() {
        var input = new PatientInput();
        input.setName(new Name(person.getFirstNm(), person.getMiddleNm(), person.getLastNm(), person.getNmSuffix()));
        input.setPhoneNumbers(Arrays.asList(
                new PhoneNumber(person.getHmPhoneNbr(), null, PhoneType.HOME)));
        input.setSsn(person.getSsn());
        input.setDateOfBirth(person.getBirthTime());
        input.setBirthGender(person.getBirthGenderCd());
        input.setCurrentGender(person.getBirthGenderCd());
        input.setDeceased(person.getDeceasedIndCd());
        // phone numbers
        List<EntityLocatorParticipation> teleElpList = person.getNBSEntity().getEntityLocatorParticipations().stream()
                .filter(elp -> elp.getClassCd().equals("TELE")).collect(Collectors.toList());
        if (teleElpList.size() > 0) {
            var phoneNumbers = new ArrayList<PhoneNumber>();
            teleElpList.forEach(elp -> {
                var locator = (TeleLocator) elp.getLocator();
                PhoneType phoneType;
                switch (elp.getUseCd()) {
                    case "MC":
                        phoneType = PhoneType.CELL;
                        break;
                    case "H":
                        phoneType = PhoneType.HOME;
                        break;
                    case "WP":
                        phoneType = PhoneType.WORK;
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Unable to derive phone type from EntityLocatorParticipation.useCd: " + elp.getUseCd());
                }
                phoneNumbers.add(
                        new PhoneNumber(locator.getPhoneNbrTxt(), locator.getExtensionTxt(), phoneType));
            });
            input.setPhoneNumbers(phoneNumbers);
        }

        // address
        var pa = (PostalLocator) person.getNBSEntity().getEntityLocatorParticipations().stream()
                .filter(elp -> elp.getClassCd().equals("PST")).findFirst().get().getLocator();
        input.setAddresses(Arrays.asList(new PostalAddress(pa.getStreetAddr1(),
                pa.getStreetAddr2(),
                pa.getCityDescTxt(),
                pa.getStateCd(),
                pa.getCntyCd(),
                pa.getCntryCd(),
                pa.getZipCd(),
                pa.getCensusTract())));
        input.setEthnicity(person.getEthnicGroupInd());
        patientController.createPatient(input);
    }

    @Then("I can find the patient")
    public void i_can_find_the_patient() {
        var pl = (PostalLocator) person.getNBSEntity().getEntityLocatorParticipations().stream()
                .filter(elp -> elp.getClassCd().equals("PST")).findFirst().get().getLocator();
        var tl = (TeleLocator) person.getNBSEntity().getEntityLocatorParticipations().stream()
                .filter(elp -> elp.getClassCd().equals("TELE")).findFirst().get().getLocator();
        var filter = new PatientFilter();
        filter.setFirstName(person.getFirstNm());
        filter.setLastName(person.getLastNm());
        filter.setAddress(pl.getStreetAddr1());
        filter.setPhoneNumber(tl.getPhoneNbrTxt());

        var patientSearch = patientController.findPatientsByFilter(filter, null);
        assertTrue(patientSearch.size() > 0);

        var patient = patientSearch.get(0);
        assertEquals(patient.getLastNm(), person.getLastNm());
        assertEquals(patient.getFirstNm(), person.getFirstNm());
        assertEquals(patient.getSsn(), person.getSsn());
        assertEquals(patient.getBirthTime(), person.getBirthTime());
        assertEquals(patient.getBirthGenderCd(), person.getBirthGenderCd());
        assertEquals(patient.getDeceasedIndCd(), person.getDeceasedIndCd());
        assertEquals(patient.getEthnicityGroupCd(), person.getEthnicityGroupCd());
    }
}
