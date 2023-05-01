package gov.cdc.nbs.patient;

import gov.cdc.nbs.Application;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.graphql.filter.PatientFilter.Identification;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.support.PersonMother;
import gov.cdc.nbs.support.util.ElasticsearchPersonMapper;
import gov.cdc.nbs.support.util.PersonUtil;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    private PatientController patientController;
    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    private Person searchPatient;
    private List<Person> searchResults;
    private List<Person> generatedPersons;
    private Direction sortDirection;
    private String sortField;
    private QueryException exception;

    @Before
    public void clearAuth() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Given("there are {int} patients")
    public void there_are_patients(int patientCount) {
        // person data is randomly generated but the Ids are always the same.
        generatedPersons = PersonMother.getRandomPersons(patientCount);

        if (patientCount >= 3)  {
            // make first 3 persons soundex,relevance/boost testable intentionally in worst order
            Person soundexPerson = generatedPersons.get(0);
            soundexPerson.setFirstNm("Jon"); // soundex equivalent to John
            soundexPerson.setLastNm("Smyth"); // soundex equivalent to Smith

            soundexPerson = generatedPersons.get(1);  // will become secondary name
            soundexPerson.setFirstNm("John");
            soundexPerson.setLastNm("Smith");

            soundexPerson = generatedPersons.get(2); // will become primary legal name
            soundexPerson.setFirstNm("John");
            soundexPerson.setLastNm("Smith");
        }

        generatedPersons.forEach(personRepository::delete);

        personRepository.flush();
        // create new persons

        personRepository.saveAll(generatedPersons);
        elasticsearchPersonRepository.saveAll(ElasticsearchPersonMapper.getElasticSearchPersons(generatedPersons));
    }

    @Given("I am looking for one of them")
    public void I_am_looking_for_one_of_them() {
        // pick one of the existing patients at random
        var index = RandomUtil.getRandomInt(generatedPersons.size());
        searchPatient = generatedPersons.get(index);
    }

    @Given("A deleted patient exists")
    public void a_deleted_patient_exists() {
        var deletedRecord = PersonMother.janeDoe_deleted();
        personRepository.save(deletedRecord);
        elasticsearchPersonRepository
                .saveAll(ElasticsearchPersonMapper.getElasticSearchPersons(Arrays.asList(deletedRecord)));
    }

    @When("I search patients by {string} {string}")
    public void i_search_patients_by_field(String field, String qualifier) {
        PatientFilter filter = getPatientDataFilter(field, qualifier);
        searchResults = patientController.findPatientsByFilter(filter, new GraphQLPage(1000, 0)).getContent();
    }

    @When("I search for a record status of {string}")
    public void i_search_for_a_record_status_of(String statusString) {
        var recordStatus = RecordStatus.valueOf(statusString);
        if (recordStatus.equals(RecordStatus.LOG_DEL)) {
            searchPatient = PersonMother.janeDoe_deleted();
        }
        PatientFilter filter = new PatientFilter(recordStatus);
        try {
            searchResults = patientController.findPatientsByFilter(filter, new GraphQLPage(1000, 0)).getContent();
        } catch (QueryException e) {
            exception = e;
        }
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

    @When("I search for a patient by {string} and there is a space at the end")
    public void i_search_for_a_patient_by_name_and_there_is_a_space_at_the_end(String field) {
        PatientFilter filter = new PatientFilter(RecordStatus.ACTIVE);
        switch (field) {
            case "first name":
                filter.setFirstName(searchPatient.getFirstNm() + " ");
                break;
            case "last name":
                filter.setLastName(searchPatient.getLastNm() + " ");
                break;
            case "address":
                var addressLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setAddress(addressLocator.getStreetAddr1() + " ");
                break;
            case "city":
                var cityLocator = PersonUtil.getPostalLocators(searchPatient).get(0);
                filter.setCity(cityLocator.getCityDescTxt() + " ");
                break;
            default:
                throw new IllegalArgumentException("Invalid value for 'field' input: " + field);
        }
        searchResults = patientController.findPatientsByFilter(filter, new GraphQLPage(10, 0)).getContent();
    }

    @Then("I find the patient")
    public void i_find_the_patient() {
        assertNotNull(searchPatient);
        assertTrue(searchResults.size() > 0);
        assertTrue(searchResults.contains(searchPatient));
    }

    @Then("I find only the expected patient")
    public void I_find_only_the_expected_patient() {
        assertNotNull(searchPatient);
        assertEquals(1, searchResults.size());
        assertTrue(searchResults.contains(searchPatient));
    }

    @Then("I find the patients sorted")
    public void i_find_the_sorted_patients() {
        Comparator<Person> comparator = getPersonSortComparator(sortField, sortDirection);
        if (comparator == null) {
            // check relevance ordering manually (ie there is no simple comparator)
            Assert.assertEquals(searchResults.get(0), generatedPersons.get(2));
            Assert.assertEquals(searchResults.get(1), generatedPersons.get(1));
            Assert.assertEquals(searchResults.get(2), generatedPersons.get(0));
            return;
        }
        List<Person> sortedPersons = generatedPersons.stream().sorted(comparator).collect(Collectors.toList());
        Assert.assertEquals(searchResults, sortedPersons);
    }

    @Then("I find patients with {string} record status")
    public void I_find_patients_with_a_specific_record_status(String statusString) {
        var recordStatus = RecordStatus.valueOf(statusString);
        assertNotNull(searchResults);
        assertFalse(searchResults.isEmpty());
        searchResults.forEach(p -> assertEquals(recordStatus, p.getRecordStatusCd()));
    }

    @Then("I dont have permissions to execute the search")
    public void I_dont_have_permissions_to_execute_the_search() {
        assertNotNull(searchPatient);
        assertNotNull(exception);
        assertNull(searchResults);
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
                filter.setLastName("Smith"); // finds Smyth
                break;
            case "last name relevance":
                filter.setLastName("Smith");
                break;
            case "first name relevance":
                filter.setFirstName("John");
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
                filter.setCity(cityLocator.getCityDescTxt());
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
                filter.setRecordStatus(Arrays.asList(searchPatient.getRecordStatusCd()));
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
            case "identification":
                var patientId = searchPatient.getEntityIds().get(0);
                filter.setIdentification(
                        new Identification(patientId.getRootExtensionTxt(), "GA", patientId.getTypeCd()));
                break;
            case "ssn":
                filter.setSsn(RandomUtil.randomPartialDataSearchString(searchPatient.getSsn()));
                break;
            case "phone number":
                var teleLocator = PersonUtil.getTeleLocators(searchPatient).get(0);
                filter.setPhoneNumber(RandomUtil.randomPartialDataSearchString(teleLocator.getPhoneNbrTxt()));
                break;
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
        var filter = new PatientFilter(RecordStatus.ACTIVE);
        return updatePatientDataFilter(filter, field, qualifier);
    }

    private PatientFilter getPatientPartialDataFilter(String field, String qualifier) {
        var filter = new PatientFilter(RecordStatus.ACTIVE);
        return updatePatientPartialDataFilter(filter, field, qualifier);
    }

    private LocalDate getDobByQualifier(Person search, String qualifier) {
        LocalDate dateOfBirth = LocalDate.ofInstant(search.getBirthTime(), ZoneOffset.UTC);
        return switch (qualifier) {
            case "before" -> dateOfBirth.plus(15, ChronoUnit.DAYS);
            case "after" -> dateOfBirth.minus(15, ChronoUnit.DAYS);
            case "equal" -> dateOfBirth;
            default -> throw new IllegalArgumentException("Invalid date of birth qualifier: " + qualifier);
        };
    }

    private Comparator<Person> getPersonSortComparator(String field, Direction direction) {
        if (field.equals("relevance")) {
            return null;
        }
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
