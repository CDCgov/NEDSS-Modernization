package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.questionbank.support.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

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

  @Given("the page has a(n) {string} of {string}")
  public void the_page_has_a_property_with_the_value(final String property, final String value) {

    PageIdentifier active = page.active();

    switch (property.toLowerCase()) {
      case "description" -> mother.withDescription(active, value);
      case "name" -> mother.withName(active, value);
      case "eventtype", "event type" -> mother.withEventType(active, resolveEventType(value));

      default -> throw new IllegalStateException("Unexpected Page value: " + property);
    }
  }

  private EventType resolveEventType(final String value) {
    return switch (value.toLowerCase()) {
      case "contact" -> EventType.CONTACT;
      case "interview" -> EventType.INTERVIEW;
      case "lab isolate tracking" -> EventType.LAB_ISOLATE_TRACKING;
      case "lab report" -> EventType.LAB_REPORT;
      case "lab susceptibility" -> EventType.LAB_SUSCEPTIBILITY;
      case "vaccination" -> EventType.VACCINATION;
      default -> throw new IllegalStateException("Unexpected Event Type value: " + value);
    };
  }

  @Given("the page is (a ){string}")
  public void the_page_has_the_status_of(final String status) {

    String value = switch (status.toLowerCase()) {
      case "draft", "published with draft", "initial draft" -> "Draft";
      case "published" -> "Published";
      case "template" -> "Template";
      default -> throw new IllegalStateException("Unexpected Page Status value: " + status.toLowerCase());
    };

    this.page.maybeActive().ifPresent(page -> mother.withStatus(page, value));
  }

  @Given("the page is tied to the {condition} condition")
  public void the_page_is_tied_to_the_condition(final String condition) {
    this.page.maybeActive().ifPresent(page -> mother.withCondition(page, condition));
  }

}
