package gov.cdc.nbs;

import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import gov.cdc.nbs.controller.PatientController;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.searchFilter.PatientFilter;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.support.PersonMother;
import gov.cdc.nbs.support.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientSearchStepDefinitions {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    PatientController patientController;

    private Person searchPatient;
    private List<Person> searchResults;
    private List<Person> generatedPersons;

    @Given("there are {int} patients")
    public void there_are_patients(int patientCount) {
        // person data is randomly generated but the Ids are always the same.
        generatedPersons = PersonMother.getRandomPersons(patientCount);

        // clean out data
        var existingPersons = personRepository
                .findAllById(generatedPersons.stream().map(p -> p.getId()).collect(Collectors.toList()));
        personRepository.deleteAll(existingPersons);

        // create clean data
        personRepository.saveAll(generatedPersons);
    }

    @Given("I am looking for one of them")
    public void I_am_looking_for_one_of_them() {
        // pick one of the existing patients at random
        var index = TestUtil.getRandomInt(generatedPersons.size());
        searchPatient = generatedPersons.get(index);
    }

    @When("I search patients by {string} {string}")
    public void i_search_patients_by_field(String field, String qualifier) {
        // generate a filter based on field / value
        PatientFilter filter = getFilterByField(field, qualifier);
        filter.setPage(new GraphQLPage(1000, 0));
        searchResults = patientController.findPatientsByFilter(filter);
    }

    @Then("I find the patient")
    public void i_find_the_patient() {
        assertTrue("Search patient is not null", searchPatient != null);
        assertTrue("Search results are not empty", searchResults.size() > 0);
        assertTrue("Search results contains patient: " + searchPatient.getId(), searchResults.contains(searchPatient));
    }

    private PatientFilter getFilterByField(String field, String qualifier) {
        var filter = new PatientFilter();
        switch (field) {
            case "last name":
                filter.setLastName(searchPatient.getLastNm());
                break;
            case "first name":
                filter.setFirstName(searchPatient.getFirstNm());
                break;
            case "patient id":
                filter.setId(searchPatient.getId());
                break;
            case "ssn":
                filter.setSsn(searchPatient.getSsn());
                break;
            case "phone number":
                filter.setPhoneNumber(searchPatient.getHmPhoneNbr());
                break;
            case "date of birth":
                filter.setDateOfBirth(getDobByQualifier(searchPatient, qualifier));
                filter.setDateOfBirthOperator(qualifier);
                break;
            case "gender":
                filter.setGender(searchPatient.getBirthGenderCd());
                break;
            case "deceased":
                filter.setDeceased(searchPatient.getDeceasedIndCd());
                break;
            case "address":
                filter.setAddress(searchPatient.getHmStreetAddr1());
                break;
            case "city":
                filter.setCity(searchPatient.getHmCityCd());
                break;
            case "state":
                filter.setState(searchPatient.getHmStateCd());
                break;
            case "country":
                filter.setCountry(searchPatient.getHmCntryCd());
                break;
            case "zip code":
                filter.setZip(searchPatient.getHmZipCd());
                break;
            case "ethnicity":
                filter.setEthnicity(searchPatient.getEthnicityGroupCd());
                break;
            case "record status":
                filter.setRecordStatus(searchPatient.getRecordStatusCd());
                break;
            default:
                throw new IllegalArgumentException("Invalid field specified: " + field);
        }
        return filter;
    }

    private Instant getDobByQualifier(Person search, String qualifier) {
        switch (qualifier) {
            case "before":
                return search.getBirthTime().plus(15, ChronoUnit.DAYS);
            case "after":
                return search.getBirthTime().minus(15, ChronoUnit.DAYS);
            case "equal":
                return search.getBirthTime();
            default:
                throw new IllegalArgumentException("Invalid date of birth qualifier: " + qualifier);
        }
    }
}