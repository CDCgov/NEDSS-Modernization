package gov.cdc.nbs;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import gov.cdc.nbs.controller.PatientController;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.searchFilter.PatientFilter;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.TeleLocatorRepository;
import gov.cdc.nbs.support.PersonMother;
import gov.cdc.nbs.support.util.PersonUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PatientCreateSteps {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    TeleLocatorRepository teleLocatorRepository;
    @Autowired
    PostalLocatorRepository postalLocatorRepository;
    @Autowired
    private PatientController patientController;

    private Person person;
    private Person createdPerson;

    @Given("A patient does not exist")
    public void a_patient_does_not_exist() {
        person = PersonMother.johnDoe();
        var pl = PersonUtil.getPostalLocators(person).get(0);
        var tl = PersonUtil.getTeleLocators(person).get(0);
        var filter = new PatientFilter();
        filter.setFirstName(person.getFirstNm());
        filter.setLastName(person.getLastNm());
        filter.setAddress(pl.getStreetAddr1());
        filter.setPhoneNumber(tl.getPhoneNbrTxt());
        var existing = patientController.findPatientsByFilter(filter, null);
        if (existing.getSize() > 0) {
            personRepository.deleteAll(existing);
        }
    }

    @When("I send a create patient request")
    public void i_send_a_create_patient_request() {
        var input = PersonUtil.convertToPatientInput(person);
        createdPerson = patientController.createPatient(input);
    }

    @Then("the patient is inserted in the database")
    public void i_can_find_the_patient() {
        assertEquals(person.getLastNm(), createdPerson.getLastNm());
        assertEquals(person.getFirstNm(), createdPerson.getFirstNm());
        assertEquals(person.getSsn(), createdPerson.getSsn());
        assertEquals(person.getBirthTime(), createdPerson.getBirthTime());
        assertEquals(person.getBirthGenderCd(), createdPerson.getBirthGenderCd());
        assertEquals(person.getDeceasedIndCd(), createdPerson.getDeceasedIndCd());
        assertEquals(person.getEthnicityGroupCd(), createdPerson.getEthnicityGroupCd());
    }
}
