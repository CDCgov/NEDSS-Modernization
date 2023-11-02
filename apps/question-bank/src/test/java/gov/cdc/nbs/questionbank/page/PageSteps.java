package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.questionbank.support.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;

import java.util.function.Consumer;

public class PageSteps {

  private static final String DEFAULT_OBJECT = "INV";
  private static final String DEFAULT_MAPPING_GUIDE = "GEN_Case_Map_v2.0";

  private final PageMother mother;
  private final Active<PageIdentifier> page;

  public PageSteps(
      final PageMother mother,
      final Active<PageIdentifier> page
  ) {
    this.mother = mother;
    this.page = page;
  }

  @Before
  public void reset() {
    this.mother.clean();
  }

  @Given("pages exist")
  public void pages_exist() {
    mother.brucellosis();
    mother.asepticMeningitis();
  }

  @Given("I have a(nother) page")
  public void i_have_a_page() {
    this.mother.create(DEFAULT_OBJECT, "Automated Test Page", DEFAULT_MAPPING_GUIDE);
  }

  @Given("I have a page named {string}")
  public void i_have_a_page_component_named(final String name) {
    this.mother.create(DEFAULT_OBJECT, name, DEFAULT_MAPPING_GUIDE);
  }

  @Given("the page is for a(n) {eventType}")
  public void the_page_is_for(final String eventType) {
    mother.withEventType(page.active(), EventType.resolve(eventType));
  }

  @Given("the page has a(n) {string} of {string}")
  public void the_page_has_a_property_with_the_value(final String property, final String value) {

    PageIdentifier active = page.active();

    switch (property.toLowerCase()) {
      case "description" -> mother.withDescription(active, value);
      case "name" -> mother.withName(active, value);
      default -> throw new IllegalStateException("Unexpected Page value: " + property);
    }
  }

  @ParameterType("(?i)(draft|published with draft|initial draft|published|template)")
  public String pageStatus(final String value) {
    return value;
  }

  @Given("the page is (a ){pageStatus}")
  public void the_page_has_the_status_of(final String status) {

    Consumer<PageIdentifier> consumer = switch (status.toLowerCase()) {
      case "draft", "initial draft" -> mother::draft;
      case "published with draft" -> ((Consumer<PageIdentifier>) mother::published).andThen(mother::draft);
      case "published" -> mother::published;
      case "template" -> mother::template;
      default -> page -> {
      };  //  NOOP
    };

    this.page.maybeActive().ifPresent(consumer);
  }

  @Given("the page is tied to the {condition} condition")
  public void the_page_is_tied_to_the_condition(final String condition) {
    this.page.maybeActive().ifPresent(page -> mother.withCondition(page, condition));
  }

}
