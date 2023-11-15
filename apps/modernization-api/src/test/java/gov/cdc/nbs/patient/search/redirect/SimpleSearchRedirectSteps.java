package gov.cdc.nbs.patient.search.redirect;

import gov.cdc.nbs.patient.search.DecryptionRequester;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class SimpleSearchRedirectSteps {

  private static final Pattern ENCRYPTED_LOCATION = Pattern.compile("^/.+\\?q=(?<encrypted>[^&]+)(?:&.+)?$");

  private final SimpleSearchRedirectRequester requester;
  private final Active<ResultActions> response;

  private final DecryptionRequester decryptionRequester;
  private final Active<ResultActions> decrypted;

  private MultiValueMap<String, String> criteria;

  public SimpleSearchRedirectSteps(
      final SimpleSearchRedirectRequester requester,
      final Active<ResultActions> response,
      final DecryptionRequester decryptionRequester
  ) {
    this.requester = requester;
    this.response = response;
    this.decryptionRequester = decryptionRequester;
    this.decrypted = new Active<>();
  }

  @Before("@simple-search-redirect")
  public void clean() {
    this.criteria = new LinkedMultiValueMap<>();
    this.response.reset();
    this.decrypted.reset();
  }


  @Given("I want a simple search for a(n) {string} of {string}")
  public void i_perform_a_simple_search_with_property_of(final String property, final String value) {
    String criteria = resolveCriteria(property);
    this.criteria.add(criteria, value);
  }

  @Given("I want a simple search for a(n) {eventTypeId} with the ID {string}")
  public void i_want_a_simple_search_for_event_with_the_id(final String eventType, final String id) {
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
      default -> throw new IllegalStateException("Unexpected Simple Search criteria value: " + property.toLowerCase());
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

        String decoded = URLDecoder.decode(encrypted, Charset.defaultCharset());

        this.decrypted.active(this.decryptionRequester.request(decoded));
      }
    }
  }

  @Then("the search parameters include a(n) {string} of {string}")
  public void the_search_parameters_include_date_of_birth_as(final String property, final String value)
      throws Exception {
    JsonPathResultMatchers path = matchingPath(property);

    this.decrypted.active().andExpect(path.value(equalToIgnoringCase(value)));
  }

  @Then("the search parameters include a(n) {eventTypeId} with the ID {string}")
  public void the_search_parameters_include_an_event_with_the_id(
      final String eventType,
      final String identifier
  ) throws Exception {

    String investigationEventType = switch (eventType) {
      case "P10000" -> "ABCS_CASE_ID";
      case "P10008" -> "CITY_COUNTY_CASE_ID";
      case "P10001" -> "INVESTIGATION_ID";
      case "P10013" -> "NOTIFICATION_ID";
      case "P10004" -> "STATE_CASE_ID";
      default -> null;

    };

    if (investigationEventType != null) {

      this.decrypted.active()
          .andExpect(jsonPath("$.eventId.investigationEventType").value(equalToIgnoringCase(investigationEventType)))
          .andExpect(jsonPath("$.eventId.id").value(equalToIgnoringCase(identifier)));
      return;
    }

    String labEventType = switch (eventType) {
      case "P10009" -> "ACCESSION_NUMBER";
      case "P10002" -> "LAB_ID";
      default -> throw new IllegalArgumentException("Unexpected Simple Search Event Type " + eventType);
    };

    this.decrypted.active()
        .andExpect(jsonPath("$.eventId.labEventType").value(equalToIgnoringCase(labEventType)))
        .andExpect(jsonPath("$.eventId.labEventId").value(equalToIgnoringCase(identifier)));
  }

  @Then("the search type is {searchType}")
  public void the_search_type_is(final String searchType) throws Exception {
    this.response.active()
        .andExpect(header().string("Location", containsString("&type=" + searchType)));
  }

  private JsonPathResultMatchers matchingPath(final String field) {
    return switch (field.toLowerCase()) {
      case "date of birth" -> jsonPath("$.dateOfBirth");
      case "first name" -> jsonPath("$.firstName");
      case "last name" -> jsonPath("$.lastName");
      case "gender" -> jsonPath("$.gender");
      case "patient id" -> jsonPath("$.id");
      default -> throw new IllegalStateException("Unexpected Page Summary value: " + field.toLowerCase());
    };
  }

  @ParameterType(name = "searchType", value = "(?i)(investigation|lab report)")
  public String searchType(final String value) {
    return Objects.equals(value.toLowerCase(), "lab report") ? "labReport" : value;
  }

  @ParameterType(name = "eventTypeId", value = ".*")
  public String eventTypeId(final String value) {
    return switch (value.toLowerCase()) {
      case "abcs case id" -> "P10000";
      case "accession number id" -> "P10009";
      case "city/county case id","city county" -> "P10008";
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
