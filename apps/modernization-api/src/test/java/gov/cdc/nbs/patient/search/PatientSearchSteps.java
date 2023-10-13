package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.graphql.filter.PatientFilter.Identification;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
public class PatientSearchSteps {

  @Autowired
  Available<PatientIdentifier> patients;

  @Autowired
  Active<PatientIdentifier> patient;

  @Autowired
  SearchablePatientMother mother;

  @Autowired
  PatientShortIdentifierResolver resolver;

  @Autowired
  PatientSearchRequest request;

  private final List<Person> available = new ArrayList<>();
  private final Active<Person> target = new Active<>();
  private final Active<PatientFilter> criteria = new Active<>();
  private final Active<SortCriteria> sorting = new Active<>();
  private final Active<ResultActions> results = new Active<>();

  @Before("@patient-search")
  public void reset() {
    criteria.active(new PatientFilter(RecordStatus.ACTIVE));
    mother.reset();
    target.reset();
    sorting.reset();

    available.clear();
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

  @Given("I add the patient criteria for a(n) {string} equal to {string}")
  public void i_add_the_patient_criteria_for_a_field_that_is_value(
      final String field,
      final String value
  ) {

    if (field == null || field.isEmpty()) {
      return;
    }
    PatientFilter filter = this.criteria.active();

    switch (field.toLowerCase()) {
      case "first name" -> filter.setFirstName(value);
      case "last name" -> filter.setLastName(value);
      case "phone number" -> filter.setPhoneNumber(value);
      case "email", "email address" -> filter.setEmail(value);
      case "city" -> filter.setCity(value);
      case "address" -> filter.setAddress(value);
      case "identification type" -> filter.getIdentification().setIdentificationType(value);
      case "identification value" -> filter.getIdentification().setIdentificationNumber(value);
      default -> throw new IllegalStateException(
          String.format("Unexpected search criteria %s equal %s", field, value));
    }

  }

  @Given("I add the patient criteria {string} {string}")
  public void i_add_the_patient_criteria(final String field, final String qualifier) {

    if (field == null || field.isEmpty()) {
      return;
    }
    PatientFilter filter = this.criteria.active();
    Person target = this.target.active();

    switch (field) {
      case "email" -> filter.setEmail(target.emailAddresses().get(0).getLocator().getEmailAddress());
      case "last name" -> filter.setLastName(target.getLastNm());
      case "first name" -> filter.setFirstName(target.getFirstNm());
      case "race" -> filter.setRace(target.getRaces().get(0).getRaceCategoryCd());
      case "patient short id" ->
          resolver.resolve(target.getLocalId()).ifPresent(id -> filter.setId(String.valueOf(id)));
      case "phone number" -> filter.setPhoneNumber(target.phoneNumbers().get(0).getLocator().getPhoneNbrTxt());
      case "birthday" -> {
        filter.setDateOfBirth(resolveDateOfBirth(target, qualifier));
        filter.setDateOfBirthOperator(qualifier.toUpperCase());
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
      default -> throw new IllegalArgumentException("Invalid field specified: " + field);
    }

  }

  private LocalDate resolveDateOfBirth(final Person search, final String qualifier) {
    LocalDate dateOfBirth = LocalDate.ofInstant(search.getBirthTime(), ZoneOffset.UTC);
    return switch (qualifier) {
      case "before" -> dateOfBirth.plusDays(15);
      case "after" -> dateOfBirth.minusDays(15);
      case "equal", "equals" -> dateOfBirth;
      default -> throw new IllegalArgumentException("Invalid birthday qualifier: " + qualifier);
    };
  }

  @Given("I add the partial patient criteria {string}")
  public void i_add_the_partial_patient_criteria(final String field) {
    if (field == null || field.isEmpty()) {
      return;
    }

    PatientFilter filter = this.criteria.active();
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

    this.criteria.active(new PatientFilter(recordStatus));
  }

  private static RecordStatus resolveStatus(final String status) {
    return switch (status.toLowerCase()) {
      case "deleted", "inactive" -> RecordStatus.LOG_DEL;
      case "superceded" -> RecordStatus.SUPERCEDED;
      default -> RecordStatus.ACTIVE;
    };
  }

  @When("I search for patients")
  public void i_search_for_patients_() throws Exception {

    //  make all patients searchable
    this.patients.all().map(this.mother::searchable).forEach(this.available::add);

    results.active(
        this.request.search(
            this.criteria.active(),
            this.sorting.active()
        )
    );

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
  public void i_find_the_patient() throws Exception {
    this.results.active()
        .andDo(print())
        .andExpect(
            jsonPath("$.data.findPatientsByFilter.content[*].patient")
                .value(hasItem(this.target.active().getId().intValue()))
        );

  }

  @Then("the patient search results contain(s) the Patient ID")
  public void the_patient_search_results_contain_patient_id() throws Exception {
    this.results.active()
        .andExpect(
            jsonPath("$.data.findPatientsByFilter.content[*].shortId")
                .value(hasItem((int) this.patient.active().shortId()))
        );
  }

  @Then("I am not able to execute the search")
  public void i_am_not_able_to_execute_the_search() throws Exception {
    this.results.active()
        .andExpect(jsonPath("$.errors[*].message")
            .value(
                hasItem(
                    "User does not have permission to search by the specified RecordStatus"
                )
            )
        );
  }

  @Then("search result {int} has a(n) {string} of {string}")
  public void search_result_n_has_a_x_of_y(
      final int position,
      final String field,
      final String value
  ) throws Exception {

    int index = position - 1;

    JsonPathResultMatchers pathMatcher = matchingPath(field, String.valueOf(index));

    this.results.active()
        .andDo(print())
        .andExpect(pathMatcher.value(matchingValue(field, value)));
  }

  @Then("the search results have a patient with a(n) {string} equal to {string}")
  public void search_results_have_a_patient_with_a(final String field, final String value) throws Exception {

    JsonPathResultMatchers pathMatcher = matchingPath(field, "*");

    this.results.active()
        .andDo(print())
        .andExpect(pathMatcher.value(matchingValue(field, value)));

  }

  @Then("the search results have a patient without a(n) {string}")
  public void search_results_have_a_patient_without_a(final String field) throws Exception {

    JsonPathResultMatchers pathMatcher = matchingPath(field, "*");

    this.results.active()
        .andExpect(pathMatcher.isEmpty());

  }

  private JsonPathResultMatchers matchingPath(final String field, final String position) {
    return switch (field.toLowerCase()) {
      case "status" -> jsonPath("$.data.findPatientsByFilter.content[%s].status", position);
      case "birthday" -> jsonPath("$.data.findPatientsByFilter.content[%s].birthday", position);
      case "first name" -> jsonPath("$.data.findPatientsByFilter.content[%s].names[*].first", position);
      case "last name" -> jsonPath("$.data.findPatientsByFilter.content[%s].names[*].last", position);
      case "legal first name" -> jsonPath("$.data.findPatientsByFilter.content[%s].legalName.first", position);
      case "legal middle name" -> jsonPath("$.data.findPatientsByFilter.content[%s].legalName.middle", position);
      case "legal last name" -> jsonPath("$.data.findPatientsByFilter.content[%s].legalName.last", position);
      case "legal name suffix" -> jsonPath("$.data.findPatientsByFilter.content[%s].legalName.suffix", position);
      case "phone number" -> jsonPath(
          "$.data.findPatientsByFilter.content[%s].phones[*]",
          position
      );
      case "email", "email address" -> jsonPath(
          "$.data.findPatientsByFilter.content[%s].emails[*]",
          position
      );
      case "identification type" ->
          jsonPath("$.data.findPatientsByFilter.content[%s].identification[*].type", position);
      case "identification value" ->
          jsonPath("$.data.findPatientsByFilter.content[%s].identification[*].value", position);
      default -> throw new AssertionError(String.format("Unexpected property check %s", field));
    };
  }

  private Matcher<?> matchingValue(final String field, final String value) {
    return switch (field.toLowerCase()) {
      case "birthday" -> equalTo(value);
      case "status" -> hasItem(resolveStatus(value).name());
      default -> hasItem(value);
    };
  }

  @Then("the patient is in the search result(s)")
  public void the_patient_is_in_the_search_results() throws Exception {
    this.results.active()
        .andDo(print())
        .andExpect(
            jsonPath(
                "$.data.findPatientsByFilter.content[?(@.patient=='%s')]",
                String.valueOf(this.patient.active().id())
            )
                .exists()
        );
  }

  @Then("the patient is not in the search result(s)")
  public void the_patient_is_not_in_the_search_results() throws Exception {
    this.results.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientsByFilter.content[?(@.patient=='%s')]",
                String.valueOf(this.patient.active().id())
            )
                .doesNotExist()
        );
  }

  @Then("there is only one patient search result(s)")
  public void there_is_only_one_patient_search_result() throws Exception {
    this.results.active()
        .andExpect(jsonPath("$.data.findPatientsByFilter.total").value(1));


  }

  @Then("the Patient Search Results are not accessible")
  public void the_patient_search_results_are_not_accessible() throws Exception {
    this.results.active().andExpect(accessDenied());
  }
}
