package gov.cdc.nbs.patient.file.demographics.name;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.hamcrest.Matcher;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileNameDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileNameDemographicVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then(
      "the patient file name demographics includes a {nameUse} name {string} {string} {string}, {nameSuffix} as of {localDate}")
  public void includes(
      final String type,
      final String first,
      final String middle,
      final String last,
      final String suffix,
      final LocalDate asOf
  ) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.asOf=='%s' && @.type.value=='%s' &&  @.first=='%s' &&  @.middle=='%s' &&  @.last=='%s' &&  @.suffix.value=='%s')]",
                asOf, type, first, middle, last, suffix
            ).exists()
        );
  }

  @Given("the patient file name demographics as of {localDate} contains the prefix {namePrefix}")
  public void containsPrefix(final LocalDate asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.asOf=='%s')].prefix.value", asOf)
                .value(value)
        );
  }

  @Given("the patient file name demographics as of {localDate} contains the first name {string}")
  public void containsFirst(final LocalDate asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.asOf=='%s')].first", asOf)
                .value(value)
        );
  }

  @Given("the patient file name demographics as of {localDate} contains the middle name {string}")
  public void containsMiddle(final LocalDate asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.asOf=='%s')].middle", asOf)
                .value(value)
        );
  }

  @Given("the patient file name demographics as of {localDate} contains the second middle name {string}")
  public void containsSecondMiddle(final LocalDate asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.asOf=='%s')].secondMiddle", asOf)
                .value(value)
        );
  }

  @Given("the patient file name demographics as of {localDate} contains the last name {string}")
  public void containsLast(final LocalDate asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.asOf=='%s')].last", asOf)
                .value(value)
        );
  }

  @Given("the patient file name demographics as of {localDate} contains the second last name {string}")
  public void containsSecondLast(final LocalDate asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.asOf=='%s')].secondLast", asOf)
                .value(value)
        );
  }

  @Given("the patient file name demographics as of {localDate} contains the suffix {nameSuffix}")
  public void containsSuffix(final LocalDate asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.asOf=='%s')].suffix.value", asOf)
                .value(value)
        );
  }

  @Given("the patient file name demographics as of {localDate} contains the degree {degree}")
  public void containsEducationLevel(final LocalDate asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.asOf=='%s')].degree.value", asOf)
                .value(value)
        );
  }

  @Then("the {nth} patient file name demographic has the as of date {localDate}")
  public void nthAsOf(
      final int position,
      final LocalDate value
  ) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$[%s].asOf", position - 1).value(value.toString()));
  }

  @Then("the {nth} patient file name demographic has the first name {string}")
  public void nthFirstName(
      final int position,
      final String value
  ) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$[%s].first", position - 1).value(value));
  }

  @Then("the {nth} name has a(n) {string} of {string}")
  public void the_nth_name_has_a_x_of_y(
      final int position,
      final String field,
      final String value) throws Exception {
    int index = position - 1;

    JsonPathResultMatchers pathMatcher = matchingPath(field, String.valueOf(index));

    this.response.active()
        .andExpect(pathMatcher.value(matchingValue(field, value)));
  }

  private Matcher<?> matchingValue(final String field, final String value) {
    return switch (field.toLowerCase()) {
      case "asof", "first", "middle", "last", "typevalue", "typename", "suffixvalue", "suffixname", "degreevalue",
           "degreename" -> equalTo(
          value);
      default -> hasItem(value);
    };
  }

  private JsonPathResultMatchers matchingPath(final String field, final String position) {
    return switch (field.toLowerCase()) {
      case "asof" -> jsonPath("$[%s].asOf", position);
      case "first" -> jsonPath("$[%s].first", position);
      case "middle" -> jsonPath("$[%s].middle", position);
      case "last" -> jsonPath("$[%s].last", position);
      case "typename" -> jsonPath("$[%s].type.name", position);
      case "typevalue" -> jsonPath("$[%s].type.value", position);
      case "suffixname" -> jsonPath("$[%s].suffix.name", position);
      case "suffixvalue" -> jsonPath("$[%s].suffix.value", position);
      case "degreename" -> jsonPath("$[%s].degree.name", position);
      case "degreevalue" -> jsonPath("$[%s].degree.value", position);
      default -> throw new AssertionError("Unexpected Patient Name property %s".formatted(field));
    };
  }
}
