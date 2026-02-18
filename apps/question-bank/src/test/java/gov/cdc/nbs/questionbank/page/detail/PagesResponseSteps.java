package gov.cdc.nbs.questionbank.page.detail;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

public class PagesResponseSteps {

  private final Active<PageIdentifier> page;

  private final PagesRequest request;

  private final Active<ResultActions> response = new Active<>();

  PagesResponseSteps(final Active<PageIdentifier> page, final PagesRequest request) {
    this.page = page;
    this.request = request;
  }

  @Before("@page-components")
  public void reset() {
    response.reset();
  }

  @When("I view the components of a page")
  public void i_view_the_components_of_a_page() throws Exception {
    response.active(request.request(this.page.active().id()));
  }

  @Then("the components of a page are not accessible")
  public void the_components_of_a_page_are_not_accessible() throws Exception {
    this.response.active().andExpect(status().isForbidden());
  }

  @Then("the page should have a component root")
  public void the_page_should_have_a_component_root() throws Exception {
    this.response.active().andExpect(jsonPath("$.root").isNumber());
  }

  @Then("the page should have a(n) {string} equal to {string}")
  public void the_page_should_have_property_equal_to_value(
      final String property, final String value) throws Exception {
    JsonPathResultMatchers pathMatcher = resolvePageProperty(property);

    this.response.active().andExpect(pathMatcher.value(containsStringIgnoringCase(value)));
  }

  private JsonPathResultMatchers resolvePageProperty(final String property) {
    return switch (property.toLowerCase()) {
      case "name" -> jsonPath("$.name");
      case "status" -> jsonPath("$.status");
      case "description" -> jsonPath("$.description");
      default -> throw new AssertionError("Unexpected Page property %s".formatted(property));
    };
  }

  @Given("the page should have a {string} {string} with a(n) {string} of {string}")
  public void the_page_should_have_a_child_with_a_property_equal_to_string(
      final String name, final String type, final String property, final String value)
      throws Exception {
    JsonPathResultMatchers pathMather = resolveChildProperty(name, type, property);

    this.response
        .active()
        .andDo(print())
        .andExpect(pathMather.value(hasItem(containsStringIgnoringCase(value))));
  }

  @Given("the page should have a {string} named {string} with a(n) {string} of {int}")
  public void the_page_should_have_a_child__named_with_a_property_equal_to_number(
      final String child, final String name, final String property, final int value)
      throws Exception {
    JsonPathResultMatchers pathMather = resolveChildProperty(name, child, property);

    this.response.active().andDo(print()).andExpect(pathMather.value(hasItem(equalTo(value))));
  }

  @Given("the page should have a {string} named {string} that is {string}")
  public void the_page_should_have_a_type_named_that_is(
      final String type, final String name, final String property) throws Exception {
    JsonPathResultMatchers pathMather = resolveChildProperty(name, type, property);

    this.response.active().andDo(print()).andExpect(pathMather.value(hasItem(equalTo(true))));
  }

  @Given("the page should have a {string} named {string} that is not {string}")
  public void the_page_should_have_a_type_named_that_is_not(
      final String type, final String name, final String property) throws Exception {
    JsonPathResultMatchers pathMather = resolveChildProperty(name, type, property);

    this.response.active().andDo(print()).andExpect(pathMather.value(hasItem(not(equalTo(true)))));
  }

  private JsonPathResultMatchers resolveChildProperty(
      final String name, final String child, final String property) {

    String childPath =
        switch (child.toLowerCase()) {
          case "tab" -> "$.tabs[?(@.name=='%s')]";
          case "section" -> "$.tabs[*].sections[?(@.name=='%s')]";
          case "subsection", "sub section", "sub-section" ->
              "$.tabs[*].sections[*].subSections[?(@.name=='%s')]";
          case "content", "question" ->
              "$.tabs[*].sections[*].subSections[*].questions[?(@.name=='%s')]";
          default -> throw new AssertionError("Unexpected Page child %s".formatted(property));
        };

    return jsonPath("%s.%s", childPath.formatted(name), property);
  }
}
