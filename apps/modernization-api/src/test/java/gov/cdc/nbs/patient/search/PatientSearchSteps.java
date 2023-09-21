package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.Authenticated;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.graphql.filter.PatientFilter.Identification;
import gov.cdc.nbs.patient.PatientController;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class PatientSearchSteps {

    record SortCriteria(Direction direction, String field) {
    }


    @Autowired
    TestActive<PatientIdentifier> patient;

    @Autowired
    SearchablePatientMother mother;

    @Autowired
    PatientShortIdentifierResolver resolver;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    PatientController patientController;

    @Autowired
    Authenticated authenticated;

    private List<Person> available;
    private QueryException exception;

    private final TestActive<Person> target = new TestActive<>();
    private final TestActive<PatientFilter> filter = new TestActive<>();
    private final TestActive<SortCriteria> sorting = new TestActive<>();
    private final TestActive<List<Person>> results = new TestActive<>();


    @Before("@patient_search")
    public void reset() {
        mother.reset();
        target.reset();
        filter.active(new PatientFilter(RecordStatus.ACTIVE));
        sorting.reset();
        results.reset();

        exception = null;
        available = new ArrayList<>();
    }

    @Given("the patient is searchable")
    public void the_patient_is_searchable() {
        this.available.add(this.mother.searchable(this.patient.active()));
    }

    @Given("there are {int} patients available for search")
    public void there_are_patients(int patientCount) {

        for (int i = 0; i < patientCount; i++) {

            this.available.add(this.mother.searchable());
        }

    }

    @Given("I am looking for one of them")
    public void I_am_looking_for_one_of_them() {
        // pick one of the available patients at random
        target.active(RandomUtil.getRandomFromArray(available));
    }

    @Given("I add the patient criteria for a {string} {string} {string}")
    public void i_add_the_patient_criteria_for_a_field_that_is_value(
        final String field,
        final String qualifier,
        final String value
    ) {

        if (field == null || field.isEmpty()) {
            return;
        }
        PatientFilter filter = this.filter.active();

        switch (field.toLowerCase()) {
            case "first name" -> filter.setFirstName(value);
            case "last name" -> filter.setLastName(value);
            default -> throw new IllegalStateException(
                String.format("Unexpected search criteria %s %s %s", field, qualifier, value));
        }

    }

    @Given("I add the patient criteria {string} {string}")
    public void i_add_the_patient_criteria(final String field, final String qualifier) {

        if (field == null || field.isEmpty()) {
            return;
        }
        PatientFilter filter = this.filter.active();
        Person target = this.target.active();

        switch (field) {
            case "email" -> filter.setEmail(target.emailAddresses().get(0).getLocator().getEmailAddress());
            case "last name" -> filter.setLastName(target.getLastNm());
            case "last name soundex" -> filter.setLastName("Smith"); // finds Smyth
            case "last name relevance" -> filter.setLastName("Smith");
            case "first name" -> filter.setFirstName(target.getFirstNm());
            case "first name soundex" -> filter.setFirstName("John"); // finds Jon
            case "first name relevance" -> filter.setFirstName("John");
            case "race" -> filter.setRace(target.getRaces().get(0).getRaceCategoryCd());
            case "identification" -> {
                var patientId = target.identifications().get(0);
                filter.setIdentification(
                    new Identification(patientId.getRootExtensionTxt(), "GA", patientId.getTypeCd()));
            }
            case "patient id" -> filter.setId(target.getLocalId());
            case "patient short id" ->
                resolver.resolve(target.getLocalId()).ifPresent(id -> filter.setId(String.valueOf(id)));
            case "ssn" -> filter.setSsn(target.getSsn());
            case "phone number" -> filter.setPhoneNumber(target.phoneNumbers().get(0).getLocator().getPhoneNbrTxt());
            case "date of birth" -> {
                filter.setDateOfBirth(resolveDateOfBirth(target, qualifier));
                filter.setDateOfBirthOperator(qualifier);
            }
            case "gender" -> filter.setGender(target.getCurrSexCd());
            case "deceased" -> filter.setDeceased(target.getDeceasedIndCd());
            case "address" -> {
                var locator = target.addresses().get(0).getLocator();
                filter.setAddress(locator.getStreetAddr1());
            }
            case "city" -> {
                var locator = target.addresses().get(0).getLocator();
                filter.setCity(locator.getCityDescTxt());
            }
            case "state" -> {
                var locator = target.addresses().get(0).getLocator();
                filter.setState(locator.getStateCd());
            }
            case "country" -> {
                var locator = target.addresses().get(0).getLocator();
                filter.setCountry(locator.getCntryCd());
            }
            case "zip code" -> {
                var locator = target.addresses().get(0).getLocator();
                filter.setZip(locator.getZipCd());
            }
            case "ethnicity" -> filter.setEthnicity(target.getEthnicity().ethnicGroup());
            case "record status" -> filter.setRecordStatus(List.of(target.getRecordStatusCd()));
            default -> throw new IllegalArgumentException("Invalid field specified: " + field);
        }

    }

    private LocalDate resolveDateOfBirth(Person search, String qualifier) {
        LocalDate dateOfBirth = LocalDate.ofInstant(search.getBirthTime(), ZoneOffset.UTC);
        return switch (qualifier) {
            case "before" -> dateOfBirth.plusDays(15);
            case "after" -> dateOfBirth.minusDays(15);
            case "equal", "equals" -> dateOfBirth;
            default -> throw new IllegalArgumentException("Invalid date of birth qualifier: " + qualifier);
        };
    }

    @Given("I add the patient criteria {string} with a space")
    public void i_add_the_patient_criteria_with_a_space(final String field) {

        Person target = this.target.active();
        PatientFilter filter = this.filter.active();
        switch (field) {
            case "first name" -> filter.setFirstName(target.getFirstNm() + " ");
            case "last name" -> filter.setLastName(target.getLastNm() + " ");
            case "address" -> {
                var locator = target.addresses().get(0).getLocator();
                filter.setAddress(locator.getStreetAddr1() + " ");
            }
            case "city" -> {
                var locator = target.addresses().get(0).getLocator();
                filter.setCity(locator.getCityDescTxt() + " ");
            }
            default -> throw new IllegalArgumentException("Invalid value for 'field' input: " + field);
        }
    }

    @Given("I add the partial patient criteria {string}")
    public void i_add_the_partial_patient_criteria(final String field) {
        if (field == null || field.isEmpty()) {
            return;
        }

        PatientFilter filter = this.filter.active();
        Person target = this.target.active();

        switch (field.toLowerCase()) {
            case "identification" -> {
                var identification = target.identifications().get(0);
                filter.setIdentification(
                    new Identification(
                        RandomUtil.randomPartialDataSearchString(identification.getRootExtensionTxt()),
                        identification.getAssigningAuthorityCd(),
                        identification.getTypeCd()
                    )
                );
            }
            case "ssn" -> filter.setSsn(RandomUtil.randomPartialDataSearchString(target.getSsn()));
            case "phone number" -> {
                var locator = target.phoneNumbers().get(0).getLocator();
                filter.setPhoneNumber(RandomUtil.randomPartialDataSearchString(locator.getPhoneNbrTxt()));
            }
            case "last name" -> filter.setLastName(RandomUtil.randomPartialDataSearchString(target.getLastNm()));
            case "first name" -> filter.setFirstName(RandomUtil.randomPartialDataSearchString(target.getFirstNm()));
            case "address" -> {
                var locator = target.addresses().get(0).getLocator();
                filter.setAddress(RandomUtil.randomPartialDataSearchString(locator.getStreetAddr1()));
            }
            case "city" -> {
                var locator = target.addresses().get(0).getLocator();
                filter.setCity(RandomUtil.randomPartialDataSearchString(locator.getCityDescTxt()));
            }
            case "zip code" -> {
                var locator = target.addresses().get(0).getLocator();
                filter.setZip(RandomUtil.randomPartialDataSearchString(locator.getZipCd()));
            }
            default -> throw new IllegalArgumentException("Invalid field specified: " + field);
        }

    }


    @Given("I would like patients that are {string}")
    public void i_add_the_partial_patient_criteria_record_status_of(final String status) {
        RecordStatus recordStatus = resolveStatus(status);

        this.filter.active(new PatientFilter(recordStatus));
    }

    private static RecordStatus resolveStatus(final String status) {
        return switch (status.toLowerCase()) {
            case "inactive" -> RecordStatus.INACTIVE;
            case "deleted" -> RecordStatus.LOG_DEL;
            case "superceded" -> RecordStatus.SUPERCEDED;
            default -> RecordStatus.ACTIVE;
        };
    }

    @When("I search for patients")
    public void i_search_for_patients() {
        try {
            SortCriteria criteria = this.sorting.active();
            GraphQLPage page = new GraphQLPage(15, 0, criteria.direction(), criteria.field());

            Page<Person> found = authenticated.perform(() ->
                patientController.findPatientsByFilter(
                    this.filter.active(),
                    page
                )
            );

            this.results.active(found.getContent());
        } catch (QueryException e) {
            exception = e;
        }
    }

    @Given("I want patients sorted by {string} {string}")
    public void i_want_patients_sorted_by(final String sortBy, final String direction) {
        Direction sortDirection = direction.equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;

        String field = switch (sortBy.toLowerCase()) {
            case "last name" -> "lastNm";
            case "birthday" -> "birthTime";
            default -> sortBy.toLowerCase();
        };

        this.sorting.active(new SortCriteria(sortDirection, field));
    }

    @Then("I find the patient")
    public void i_find_the_patient() {

        assertThat(this.results.active()).contains(target.active());

    }

    @Then("I find only the expected patient when searching by {string}")
    public void I_find_only_the_expected_patient(final String field) {
        // Verify all results match search criteria
        PatientFilter filter = this.filter.active();

        //  Some of the criteria check is on lazy loaded sub-entities, a managed Person is required.
        List<Person> found = this.results.active()
            .stream()
            .map(Person::getId)
            .map(this.personRepository::findById)
            .flatMap(Optional::stream)
            .toList();

        switch (field) {
            case "first name" -> {
                String search = filter.getFirstName().trim();

                assertThat(found).allMatch(
                    result -> result.getFirstNm().contains(search)
                );

            }
            case "last name" -> {
                String search = filter.getLastName().trim();
                assertThat(found).allMatch(
                    result -> result.getLastNm().contains(search)
                );
            }
            case "address" -> {
                String search = filter.getAddress().trim();

                assertThat(found).allSatisfy(
                    result -> assertThat(result.addresses())
                        .anySatisfy(addresses ->
                            assertThat(addresses.getLocator().getStreetAddr1()).contains(search))
                );

            }
            case "city" -> {
                String search = filter.getCity().trim();

                assertThat(found).allSatisfy(
                    result -> assertThat(result.addresses())
                        .anySatisfy(addresses ->
                            assertThat(addresses.getLocator().getCityDescTxt()).contains(search))
                );

            }
            default -> throw new IllegalArgumentException("Invalid value for 'field' input: " + field);
        }
    }

    @Then("I find the patients sorted")
    public void i_find_the_sorted_patients() {
        List<Person> found = this.results.active();

        SortCriteria criteria = this.sorting.active();

        Comparator<Person> comparator = getPersonSortComparator(criteria);

        assertThat(found).isSortedAccordingTo(comparator);
    }

    private Comparator<Person> getPersonSortComparator(final SortCriteria criteria) {

        String field = criteria.field();
        Direction direction = criteria.direction();

        Comparator<Person> comparator = switch (field) {
            case "lastNm" -> Comparator.comparing(Person::getLastNm);
            case "birthTime" -> Comparator.comparing(Person::getBirthTime);
            default -> Comparator.comparing(Person::getId);
        };

        return direction == Direction.DESC ? comparator.reversed() : comparator;
    }

    @Then("I find {string} patients")
    public void I_find_patients_with_a_specific_record_status(final String status) {
        RecordStatus recordStatus = resolveStatus(status);

        assertThat(this.results.active())
            .anySatisfy(
                result -> assertThat(result.getRecordStatusCd()).isEqualTo(recordStatus)
            );


    }

    @Then("I am not able to execute the search")
    public void i_am_not_able_to_execute_the_search() {
        assertThat(exception).hasMessage("User does not have permission to search by the specified RecordStatus");

        assertThat(this.results.maybeActive()).isEmpty();
    }

    @Then("search result {int} has an {string} of {string}")
    @Then("search result {int} has a {string} of {string}")
    public void search_result_n_has_a_x_of_y(
        final int position,
        final String field,
        final String value
    ) {

        Person found = this.results.active().get(position - 1);

        switch (field.toLowerCase()) {
            case "first name" ->
                assertThat(found.getNames()).anySatisfy(name -> assertThat(name.getFirstNm()).isEqualTo(value));
            case "last name" ->
                assertThat(found.getNames()).anySatisfy(name -> assertThat(name.getLastNm()).isEqualTo(value));

            default ->
                throw new IllegalStateException(String.format("Unexpected property check %s is %s", field, value));
        }

    }

}
