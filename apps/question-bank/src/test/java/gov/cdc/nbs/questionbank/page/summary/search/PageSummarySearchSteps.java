package gov.cdc.nbs.questionbank.page.summary.search;

import gov.cdc.nbs.questionbank.filter.Filter;
import gov.cdc.nbs.questionbank.filter.MultiValueFilter;
import gov.cdc.nbs.questionbank.filter.SingleValueFilter;
import gov.cdc.nbs.questionbank.filter.ValueFilter;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PageSummarySearchSteps {

  private final PageSummarySearchRequester requester;
  private final Active<ResultActions> response;
  private final Active<PageSummaryRequest> criteria;
  private final Active<PageRequest> pageable;

  PageSummarySearchSteps(final PageSummarySearchRequester requester) {
    this.requester = requester;
    this.pageable = new Active<>();
    this.response = new Active<>();
    this.criteria = new Active<>();
  }

  @Before("@page-summary-search")
  public void reset() {
    response.reset();
    criteria.active(new PageSummaryRequest(null));
    pageable.active(PageRequest.of(0, 25));
  }

  @Given("I am looking for page summaries that contain {string}")
  public void i_am_looking_for_page_summaries_for(final String value) {
    this.criteria.active(existing -> existing.withSearch(value));
  }

  @Given("I am looking for page summaries sorted by {string} {direction}")
  public void i_am_looking_for_page_summaries_sorted_by(final String property, final Sort.Direction direction) {

    String sortOn = normalizeProperty(property);

    this.pageable.active(this.pageable.active().withSort(Sort.by(direction, sortOn)));
  }

  private String normalizeProperty(final String property) {
    return switch (property.toLowerCase()) {
      case "event type", "eventtype" -> "eventType";
      default -> property;
    };
  }

  @Then("I filter page summaries by {string} {operator} {string}")
  public void i_filter_page_summaries_by_property_operator_value(
      final String property,
      final ValueFilter.Operator operator,
      final String value
  ) {

    Filter filter = new SingleValueFilter(
        normalizeProperty(property),
        operator,
        value
    );

    this.criteria.active(existing -> existing.withFilter(filter));

  }

  @Then("I filter page summaries by {string} {operator}")
  public void i_filter_page_summaries_by_property_operator_values(
      final String property,
      final ValueFilter.Operator operator,
      final List<String> values
  ) {

    Filter filter = new MultiValueFilter(
        normalizeProperty(property),
        operator,
        values
    );

    this.criteria.active(existing -> existing.withFilter(filter));

  }

  @When("I search for page summaries")
  public void i_search_for_page_summaries() throws Exception {
    response.active(
        requester.request(
            pageable.active(),
            criteria.active()
        )
    );
  }

  @Then("the page summaries are not searchable")
  public void the_summaries_of_a_page_are_not_searchable() throws Exception {
    this.response.active()
        .andExpect(status().isForbidden());
  }

  @Then("there are no page summaries found")
  public void there_are_no_page_summaries_found() throws Exception {
    found(0);
  }

  @Then("there is only one page summary found")
  public void there_is_only_one_page_summary_found() throws Exception {
    found(1);
  }

  @Then("there are {int} page summaries found")
  public void there_are_n_page_summary_found(int n) throws Exception {
    found(n);
  }

  private void found(int total) throws Exception {
    this.response.active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(total)));
  }

  @Then("there are page summaries found")
  public void there_are_page_summary_found() throws Exception {
    this.response.active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", not(hasValue(0))));
  }

  @Then("the found page summaries contain a page with a(n) {string} equal to {string}")
  @Then("the found page summaries contain a page with the {string} {string}")
  public void the_found_page_summaries_contain_a_page_with_thn_property_having_the_value(
      final String property,
      final String value
  ) throws Exception {
    JsonPathResultMatchers pathMatcher = matchingPath(property, "*");

    this.response.active()
        .andExpect(pathMatcher.value(hasItem(equalToIgnoringCase(value))));
  }

  @Then("the found page summaries do not contain a page with a(n) {string} equal to {string}")
  @Then("the found page summaries do not contain a page with the {string} {string}")
  public void the_found_page_summaries_do_not_contain_a_page_with_thn_property_having_the_value(
      final String property,
      final String value
  ) throws Exception {
    JsonPathResultMatchers pathMatcher = matchingPath(property, "*");

    this.response.active()
        .andExpect(pathMatcher.value(not(matchingValue(property, value))));
  }

  @Then("the {nth} found page summary has a(n) {string} equal to {string}")
  @Then("the {nth} found page summary has the {string} {string}")
  public void the_found_nth_page_summaries_contain_a_page_with_nth_property_having_the_value(
      final int position,
      final String property,
      final String value
  ) throws Exception {
    int index = position - 1;
    JsonPathResultMatchers pathMatcher = matchingPath(property, String.valueOf(index));

    this.response.active()
        .andExpect(pathMatcher.value(matchingValue(property, value)));
  }

  @Then("the {int} page summary has a(n) {string} equal to {string}")
  @Then("the {int} page summary has the {string} {string}")
  public void and_page_summaries_x_have_a_page_with_the_property_having_the_value(
      final int position,
      final String property,
      final String value
  ) throws Exception {
    int index = position - 1;

    JsonPathResultMatchers pathMatcher = matchingPath(property, String.valueOf(index));

    this.response.active()
        .andExpect(pathMatcher.value(matchingValue(property, value)));
  }

  private JsonPathResultMatchers matchingPath(final String field, final String position) {
    return switch (field.toLowerCase()) {
      case "name" -> jsonPath("$.content[%s].name", position);
      case "description" -> jsonPath("$.content[%s].description", position);
      case "status" -> jsonPath("$.content[%s].status", position);
      case "lastupdate", "last updated", "last update" -> jsonPath("$.content[%s].lastUpdate", position);
      case "lastupdatedby" -> jsonPath("$.content[%s].lastUpdateBy", position);
      case "eventtype", "event type" -> jsonPath("$.content[%s].eventType.name", position);
      case "mmg", "messagemappingguide", "message mapping guide" ->
          jsonPath("$.content[%s].messageMappingGuide.name", position);
      case "condition" -> jsonPath("$.content[%s].conditions[*].name", position);
      default -> throw new IllegalStateException("Unexpected Page Summary value: " + field.toLowerCase());
    };
  }

  private Matcher<?> matchingValue(final String property, final String value) {
    return switch (property.toLowerCase()) {
      case "condition", "conditions" -> hasItem(equalToIgnoringCase(value));
      default -> equalToIgnoringCase(value);
    };
  }
}
