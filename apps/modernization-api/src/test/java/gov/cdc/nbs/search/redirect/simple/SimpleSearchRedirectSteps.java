package gov.cdc.nbs.search.redirect.simple;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.encryption.DecryptionRequester;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class SimpleSearchRedirectSteps {

  private static final Pattern ENCRYPTED_LOCATION = Pattern.compile(".*/(.+)");

  private final SimpleSearchRedirectRequester requester;
  private final Active<ResultActions> response;

  private final DecryptionRequester decryptionRequester;
  private final Active<ResultActions> decrypted;

  private MultiValueMap<String, String> criteria;

  SimpleSearchRedirectSteps(
      final SimpleSearchRedirectRequester requester,
      final Active<ResultActions> response,
      final DecryptionRequester decryptionRequester) {
    this.requester = requester;
    this.response = response;
    this.decryptionRequester = decryptionRequester;
    this.decrypted = new Active<>();
  }

  @Before("@simple-search-redirect")
  public void clean() {
    this.criteria = new LinkedMultiValueMap<>();
    this.decrypted.reset();
  }

  @Given("I want a simple search for a(n) {string} of {string}")
  public void i_perform_a_simple_search_with_property_of(
      final String property, final String value) {
    String resolved = resolveCriteria(property);
    this.criteria.add(resolved, value);
  }

  @Given("I want a simple search for a(n) {eventTypeId} with the ID {string}")
  public void i_want_a_simple_search_for_event_with_the_id(
      final String eventType, final String id) {
    this.criteria.add("patientSearchVO.actType", eventType);
    this.criteria.add("patientSearchVO.actId", id);
  }

  private String resolveCriteria(final String property) {
    return switch (property.toLowerCase()) {
      case "date of birth" -> "patientSearchVO.birthTime";
      case "first name" -> "patientSearchVO.firstName";
      case "last name" -> "patientSearchVO.lastName";
      case "gender" -> "patientSearchVO.currentSex";
      case "patient id" -> "patientSearchVO.localID";
      default ->
          throw new IllegalStateException(
              "Unexpected Simple Search criteria value: " + property.toLowerCase());
    };
  }

  @When("I perform a search from the NBS Home screen")
  public void i_perform_a_search_from_the_nbs_home_screen() {
    ResultActions result = this.requester.request(this.criteria);
    this.response.active(result);

    String location = result.andReturn().getResponse().getRedirectedUrl();

    if (location != null) {
      Matcher matcher = ENCRYPTED_LOCATION.matcher(location);

      if (matcher.find()) {
        String encrypted = matcher.group(1);

        this.decrypted.active(this.decryptionRequester.request(encrypted));
      }
    }
  }

  @Then("the search parameters include a(n) {string} of {string}")
  public void the_search_parameters_include(final String property, final String value)
      throws Exception {
    JsonPathResultMatchers path = matchingPath(property);

    this.decrypted.active().andExpect(path.value(equalToIgnoringCase(value)));
  }

  @Then("the search parameters include a date of birth equal to {localDate}")
  public void the_search_parameters_include(final LocalDate bornOn) throws Exception {

    this.decrypted
        .active()
        .andDo(print())
        .andExpect(jsonPath("$.bornOn.equals.month").value(bornOn.getMonthValue()))
        .andExpect(jsonPath("$.bornOn.equals.day").value(bornOn.getDayOfMonth()))
        .andExpect(jsonPath("$.bornOn.equals.year").value(bornOn.getYear()));
  }

  @Then("the search parameters include a(n) {string} that starts with {string}")
  public void the_search_parameters_include_the_criteria_that_starts_with(
      final String property, final String value) throws Exception {
    JsonPathResultMatchers path = criteriaMatchingPath(property, "startsWith");

    this.decrypted.active().andDo(print()).andExpect(path.value(equalToIgnoringCase(value)));
  }

  @Then("the search parameters include a(n) {string} that contains {string}")
  public void the_search_parameters_include_the_criteria_that_contains(
      final String property, final String value) throws Exception {
    JsonPathResultMatchers path = criteriaMatchingPath(property, "contains");

    this.decrypted.active().andDo(print()).andExpect(path.value(equalToIgnoringCase(value)));
  }

  private JsonPathResultMatchers criteriaMatchingPath(final String field, final String property) {
    return switch (field.toLowerCase()) {
      case "first name" -> jsonPath("$.name.first.%s", property);
      case "last name" -> jsonPath("$.name.last.%s", property);
      default ->
          throw new IllegalStateException(
              "Unexpected simple search parameter value: " + field.toLowerCase());
    };
  }

  @Then("the search parameters include a(n) {eventTypeId} with the ID {string}")
  public void the_search_parameters_include_an_event_with_the_id(
      final String eventType, final String identifier) throws Exception {

    String type =
        switch (eventType) {
          case "P10000" -> "abcCase";
          case "P10008" -> "cityCountyCase";
          case "P10001" -> "investigation";
          case "P10013" -> "notification";
          case "P10004" -> "stateCase";
          case "P10009" -> "accessionNumber";
          case "P10002" -> "labReport";
          case "P10006" -> "vaccination";
          case "P10010" -> "document";
          case "P10003" -> "morbidity";
          case "P10005" -> "treatment";
          default -> throw new IllegalStateException("Unexpected value: " + eventType);
        };

    this.decrypted
        .active()
        .andDo(print())
        .andExpect(jsonPath("$.%s", type).value(equalToIgnoringCase(identifier)));
  }

  @Then("the search type is {searchType}")
  public void the_search_type_is(final String searchType) throws Exception {
    this.response
        .active()
        .andExpect(header().string("Location", containsString("/search/simple/" + searchType)));
  }

  private JsonPathResultMatchers matchingPath(final String field) {
    return switch (field.toLowerCase()) {
      case "gender" -> jsonPath("$.gender.value");
      case "patient id" -> jsonPath("$.id");
      default ->
          throw new IllegalStateException(
              "Unexpected simple search parameter value: " + field.toLowerCase());
    };
  }

  @ParameterType(name = "searchType", value = "(?i)(patients|investigations|lab-reports)")
  public String searchType(final String value) {
    return value;
  }

  @ParameterType(name = "eventTypeId", value = ".*")
  public String eventTypeId(final String value) {
    return switch (value.toLowerCase()) {
      case "abcs case id" -> "P10000";
      case "accession number id" -> "P10009";
      case "city/county case id", "city county" -> "P10008";
      case "document id" -> "P10010";
      case "investigation id" -> "P10001";
      case "lab id" -> "P10002";
      case "morbidity report id" -> "P10003";
      case "notification id" -> "P10013";
      case "state case id" -> "P10004";
      case "treatment id" -> "P10005";
      case "vaccination id" -> "P10006";
      default -> value;
    };
  }
}
