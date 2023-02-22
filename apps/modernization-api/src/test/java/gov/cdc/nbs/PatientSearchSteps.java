package gov.cdc.nbs;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.controller.PatientController;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.graphql.filter.PatientFilter.Identification;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.TeleLocatorRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.support.PersonMother;
import gov.cdc.nbs.support.util.PersonUtil;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Sort.Direction;
import java.util.Comparator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback(false)
public class PatientSearchSteps {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PostalLocatorRepository postalLocatorRepository;
    @Autowired
    private TeleLocatorRepository teleLocatorRepository;
    @Autowired
    private PatientController patientController;
    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    private Person searchPatient;
    private List<Person> searchResults;
    private List<Person> generatedPersons;
    private Direction sortDirection;
    private String sortField;

    @Before
    public void clearAuth() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Given("there are {int} patients")
    public void there_are_patients(int patientCount) {
        // person data is randomly generated but the Ids are always the same.
        generatedPersons = PersonMother.getRandomPersons(patientCount);
        
        // make first person soundex testable
        Person soundexPerson = generatedPersons.get(0);
        soundexPerson.setFirstNm("Jon");  // soundex equivalent to John
        soundexPerson.setLastNm("Smyth");  // soundex equivalent to Smith

        generatedPersons.forEach(personRepository::delete);

        personRepository.flush();
        // create new persons

        personRepository.saveAll(generatedPersons);
        elasticsearchPersonRepository.saveAll(PersonUtil.getElasticSearchPersons(generatedPersons));
    }

    @Given("I am looking for one of them")
    public void I_am_looking_for_one_of_them() {
        // pick one of the existing patients at random
        var index = RandomUtil.getRandomInt(generatedPersons.size());
        searchPatient = generatedPersons.get(index);
    }

    @When("I search patients by {string} {string}")
    public void i_search_patients_by_field(String field, String qualifier) {
        PatientFilter filter = getPatientDataFilter(field, qualifier);
        searchResults = patientController.findPatientsByFilter(filter, new GraphQLPage(1000, 0)).getContent();
    }

    @When("I search patients by {string} {string} {string} {string} {string} {string}")
    public void i_search_patients_by_multiple_fields(String field, String qualifier, String field2, String qualifier2,
            String field3, String qualifier3) {
        PatientFilter filter = getPatientDataFilter(field, qualifier);
        updatePatientDataFilter(filter, field2, qualifier2);
        updatePatientDataFilter(filter, field3, qualifier3);
        searchResults = patientController.findPatientsByFilter(filter, new GraphQLPage(1000, 0)).getContent();
    }

    @When("I search patients using partial data {string} {string} {string} {string}")
    public void i_search_patients_using_partial_data(String field, String qualifier, String field2, String qualifier2) {
        PatientFilter filter = getPatientPartialDataFilter(field, qualifier);
        updatePatientPartialDataFilter(filter, field2, qualifier2);
        searchResults = patientController.findPatientsByFilter(filter, new GraphQLPage(1000, 0)).getContent();
    }

    @When("I search for patients sorted by {string} {string} {string} {string}")
    public void i_search_for_ordered_patients(String field, String qualifier, String aSortField,
            String aSortDirection) {
        PatientFilter filter = getPatientDataFilter(field, qualifier);
        sortDirection = aSortDirection.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
        sortField = aSortField;
        searchResults = patientController
                .findPatientsByFilter(filter, new GraphQLPage(1000, 0, sortDirection, sortField)).getContent();
    }

    @Then("I find the patient")
    public void i_find_the_patient() {
        assertNotNull(searchPatient);
        assertTrue(searchResults.size() > 0);
        assertTrue(searchResults.contains(searchPatient));
    }

    @Then("I find the patients sorted")
    public void i_find_the_sorted_patients() {
        Comparator<Person> comparator = getPersonSortComparator(sortField, sortDirection);
        List<Person> sortedPersons = generatedPersons.stream().sorted(comparator).collect(Collectors.toList());
        Assert.assertEquals(searchResults, sortedPersons);
    }

    private PatientFilter updatePatientDataFilter(PatientFilter filter, String field, String qualifier) {
        if (field == null || field.isEmpty()) {
            return filter;
        }
        switch (field) {
            case "email":
                filter.setEmail(PersonUtil.getTeleLocators(searchPatient).get(0).getEmailAddress());
                break;
            case "last name soundex":
                searchPatient = generatedPersons.get(0);
                filter.setLastName("Smith");  // finds Smyth
                break;
            case "last name":
                filter.setLastName(searchPatient.getLastNm());
                break;
            case "first name":
                filter.setFirstName(searchPatient.getFirstNm());
                break;
            case "first name soundex":
                searchPatient = generatedPersons.get(0);
                filter.setFirstName("John"); // finds Jon
                break;
            case "race":
                filter.setRace(searchPatient.getRaces().get(0).getRaceCd());
                break;
            case "identification":
                var patientId = searchPatient.getEntityIds().get(0);
                filter.setIdentification(
                        new Identification(patientId.getRootExtensionTxt(), "GA", patientId.getTypeCd()));
                break;
            case "patient id":
                filter.setId(searchPatient.getLocalId());
                break;
            case "ssn":
                filter.setSsn(searchPatient.getSsn());
                break;
            case "phone number":
                filter.setPhoneNumber(PersonUtil.getTeleLocators(searchPatient).get(0).getPhoneNbrTxt());
                break;
            case "date of birth":
                filter.setDateOfBirth(getDobByQualifier(searchPatient, qualifier));
                filter.setDateOfBirthOperator(qualifier);
                break;
            case "gender":
                filter.setGender(searchPatient.getCurrSexCd());
                break;
            case "deceased":
                filter.setDeceased(searchPatient.getDeceasedIndCd());
                break;
            case "address":
                var addressLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setAddress(addressLocator.getStreetAddr1());
                break;
            case "city":
                var cityLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setCity(cityLocator.getCityCd());
                break;
            case "state":
                var stateLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setState(stateLocator.getStateCd());
                break;
            case "country":
                var cntryLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setCountry(cntryLocator.getCntryCd());
                break;
            case "zip code":
                var zipLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setZip(zipLocator.getZipCd());
                break;
            case "ethnicity":
                filter.setEthnicity(searchPatient.getEthnicGroupInd());
                break;
            case "record status":
                filter.setRecordStatus(searchPatient.getRecordStatusCd());
                break;
            default:
                throw new IllegalArgumentException("Invalid field specified: " + field);
        }
        return filter;
    }

    private PatientFilter updatePatientPartialDataFilter(PatientFilter filter, String field, String qualifier) {
        if (field == null || field.isEmpty()) {
            return filter;
        }
        switch (field) {
            case "last name":
                filter.setLastName(RandomUtil.randomPartialDataSearchString(searchPatient.getLastNm()));
                break;
            case "first name":
                filter.setFirstName(RandomUtil.randomPartialDataSearchString(searchPatient.getFirstNm()));
                break;
            case "address":
                var addressLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setAddress(RandomUtil.randomPartialDataSearchString(addressLocator.getStreetAddr1()));
                break;
            case "city":
                var cityLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setCity(RandomUtil.randomPartialDataSearchString(cityLocator.getCityDescTxt()));
                break;
            default:
                throw new IllegalArgumentException("Invalid field specified: " + field);
        }
        return filter;
    }

    private PatientFilter getPatientDataFilter(String field, String qualifier) {
        var filter = new PatientFilter();
        return updatePatientDataFilter(filter, field, qualifier);
    }

    private PatientFilter getPatientPartialDataFilter(String field, String qualifier) {
        var filter = new PatientFilter();
        return updatePatientPartialDataFilter(filter, field, qualifier);
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

    private Comparator<Person> getPersonSortComparator(String field, Direction direction) {
        Comparator<Person> personSortComparator = (h1, h2) -> h1.getLastNm().compareTo(h2.getLastNm());
        if (direction == Direction.DESC) {
            if (field.equals("lastNm")) {
                personSortComparator = (h1, h2) -> h2.getLastNm().compareTo(h1.getLastNm());
            } else if (field.equals("birthTime")) {
                personSortComparator = (h1, h2) -> h2.getBirthTime().compareTo(h1.getBirthTime());
            }
        } else {
            if (field.equals("birthTime")) {
                personSortComparator = (h1, h2) -> h1.getBirthTime().compareTo(h2.getBirthTime());
            }
        }
        return personSortComparator;
    }
}