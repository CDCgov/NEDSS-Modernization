package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import java.time.Instant;
import java.util.function.Consumer;

public class PageSteps {

  private static final String DEFAULT_EVENT_TYPE = "INV";
  private static final String DEFAULT_MAPPING_GUIDE = "GEN_Case_Map_v2.0";

  private final PageMother mother;
  private final Active<PageIdentifier> activePage;

  public PageSteps(final PageMother mother, final Active<PageIdentifier> activePage) {
    this.mother = mother;
    this.activePage = activePage;
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
    this.mother.create(DEFAULT_EVENT_TYPE, "Automated Test Page", DEFAULT_MAPPING_GUIDE);
  }

  @Given("I have a page named {string}")
  public void i_have_a_page_component_named(final String name) {
    this.mother.create(DEFAULT_EVENT_TYPE, name, DEFAULT_MAPPING_GUIDE);
  }

  @Given("I have a(n) {eventType} page named {string}")
  public void i_have_an_event_type_page_named(final String eventType, final String name) {
    this.mother.create(eventType, name, DEFAULT_MAPPING_GUIDE);
  }

  @Given("I have a(n) {eventType} page named {string} mapped by {messageMappingGuide}")
  public void i_have_an_event_type_page_named_mapped_by(
      final String eventType, final String name, final String mmg) {
    this.mother.create(eventType, name, mmg);
  }

  @Given("the page has a(n) {string} of {string}")
  public void the_page_has_a_property_with_the_value(final String property, final String value) {

    PageIdentifier active = activePage.active();

    switch (property.toLowerCase()) {
      case "description" -> mother.withDescription(active, value);
      case "name" -> mother.withName(active, value);
      case "datamart" -> mother.withDatamart(active, value);
      default -> throw new IllegalStateException("Unexpected Page value: " + property);
    }
  }

  @Given("{user} changed the page name to {string}")
  public void the_user_changed_the_page_property(final ActiveUser user, final String value) {
    Instant when = Instant.now();
    this.activePage
        .maybeActive()
        .ifPresent(active -> mother.withName(active, value, user.nedssEntry(), when));
  }

  @Given("{user} changed the page name to {string} {past}")
  public void the_user_changed_the_page_property(
      final ActiveUser user, final String value, final Instant when) {
    this.activePage
        .maybeActive()
        .ifPresent(active -> mother.withName(active, value, user.nedssEntry(), when));
  }

  @ParameterType("(?i)(draft|published with draft|initial draft|published|template|legacy)")
  public String pageStatus(final String value) {
    return value;
  }

  @Given("the page is (a ){pageStatus}")
  public void the_page_has_the_status_of(final String status) {

    Consumer<PageIdentifier> consumer =
        switch (status.toLowerCase()) {
          case "draft", "initial draft" -> mother::draft;
          case "published with draft" ->
              ((Consumer<PageIdentifier>) mother::published).andThen(mother::draft);
          case "published" -> mother::published;
          case "template" -> mother::template;
          case "legacy" -> mother::legacy;
          default -> page -> {}; //  NOOP
        };

    this.activePage.maybeActive().ifPresent(consumer);
  }

  @Given("the page is tied to the {condition} condition")
  @Given("the page is associated with the {condition} condition")
  public void the_page_is_tied_to_the_condition(final String condition) {
    this.activePage.maybeActive().ifPresent(page -> mother.withCondition(page, condition));
  }
}
