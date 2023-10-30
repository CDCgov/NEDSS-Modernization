package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.questionbank.support.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class PageSteps {

  private static final String DEFAULT_OBJECT = "INV";
  private static final String DEFAULT_MAPPING_GUIDE = "GEN_Case_Map_v2.0";

  @Autowired
  PageMother mother;

  @Autowired
  Active<PageIdentifier> page;

  @Before
  public void reset() {
    this.mother.clean();
  }

  @Given("pages exist")
  public void pages_exist() {
    mother.brucellosis();
    mother.asepticMeningitis();
  }

  @Given("I have a page")
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

    if (property.equalsIgnoreCase("description")) {
      mother.withDescription(active, value);
    }

  }

  @Given("the page has a(nother) tab")
  public void the_page_has_a_tab() {
    PageIdentifier active = page.active();

    mother.withTab(active);
  }

  @Given("the page has a tab named {string}")
  public void the_page_has_a_named_tab(final String name) {
    PageIdentifier active = page.active();

    mother.withTab(active, name);
  }

  @Given("^the page has a section named \"(.*)\" in the (\\d+)(?:st|nd|rd|th) tab$")
  public void the_page_has_a_named_section_in_the_nth_tab(final String section, final int tab) {
    PageIdentifier active = page.active();

    mother.withSectionIn(active, section, tab);
  }

  @Given("^the page has a section in the (\\d+)(?:st|nd|rd|th) tab$")
  public void the_page_has_a_section_in_the_nth_tab(final int tab) {
    PageIdentifier active = page.active();

    mother.withSectionIn(active, tab);
  }

  @Given("the page has a section named {string} in the {string} tab")
  public void the_page_has_a_section_in_the_named_tab(final String section, final String tab) {
    PageIdentifier active = page.active();

    mother.withSectionIn(active, section, tab);
  }

  @Given("^the page has a(?:n|nother)? sub-section in the (\\d+)(?:st|nd|rd|th) section")
  public void the_page_has_a_sub_section_in_the_nth_section(final int section) {
    PageIdentifier active = page.active();

    mother.withSubSectionIn(active, section);
  }

  @Given("^the page has a sub-section named \"(.*)\" in the (\\d+)(?:st|nd|rd|th) section")
  public void the_page_has_a_named_sub_section_in_the_nth_section(final String subSection, final int section) {
    PageIdentifier active = page.active();

    mother.withSubSectionIn(active, subSection, section);
  }

  @Given("the page has a sub-section named {string} in the {string} section")
  public void the_page_has_a_named_sub_section_in_the_section_named(final String subSection, final String section) {
    PageIdentifier active = page.active();

    mother.withSubSectionIn(active, subSection, section);
  }
  
  @Given("^the page has a question named \"(.*)\" in the (\\d+)(?:st|nd|rd|th) sub-section")
  public void the_page_has_a_named_question_in_the_nth_sub_section(final String subSection, final int section) {
    PageIdentifier active = page.active();

    mother.withContentIn(active, subSection, section);
  }

  @Given("the page has a question named {string} in the {string} sub-section")
  public void the_page_has_a_named_question_in_the_sub_section_named(final String subSection, final String section) {
    PageIdentifier active = page.active();

    mother.withContentIn(active, subSection, section);
  }

}
