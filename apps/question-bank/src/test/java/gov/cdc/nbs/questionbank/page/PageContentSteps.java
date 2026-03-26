package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class PageContentSteps {

  private final PageContentMother mother;
  private final Active<PageIdentifier> page;

  PageContentSteps(final PageContentMother mother, final Active<PageIdentifier> page) {
    this.mother = mother;
    this.page = page;
  }

  @Given("the page has a(nother) tab")
  public void the_page_has_a_tab() {
    PageIdentifier active = page.active();

    mother.withTab(active);
  }

  @Given("the page has a(nother) rule")
  public void the_page_has_a_rule() {
    PageIdentifier active = page.active();
    mother.withRule(active);
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
  public void the_page_has_a_named_sub_section_in_the_nth_section(
      final String subSection, final int section) {
    PageIdentifier active = page.active();

    mother.withSubSectionIn(active, subSection, section);
  }

  @Given("the page has a sub-section named {string} in the {string} section")
  public void the_page_has_a_named_sub_section_in_the_section_named(
      final String subSection, final String section) {
    PageIdentifier active = page.active();

    mother.withSubSectionIn(active, subSection, section);
  }

  @Given("^the page has a question named \"(.*)\" in the (\\d+)(?:st|nd|rd|th) sub-section")
  public void the_page_has_a_named_question_in_the_nth_sub_section(
      final String subSection, final int section) {
    PageIdentifier active = page.active();

    mother.withContentIn(active, subSection, section, 1008L, "DEM107", "CODED");
  }

  @Given("^the page has a static element named \"(.*)\" in the (\\d+)(?:st|nd|rd|th) sub-section")
  public void the_has_a_static_element_in_the_nth_sub_section(
      final String name, final int subsection) {
    PageIdentifier active = page.active();

    mother.withContentIn(active, name, subsection, 1011L, null, null);
  }

  @Given("^the page has a date question named \"(.*)\" in the (\\d+)(?:st|nd|rd|th) sub-section")
  public void the_page_has_a_date_question_named_in_the_nth_sub_section(
      final String subSection, final int section) {
    PageIdentifier active = page.active();

    mother.withContentIn(active, subSection, section, 1008L, "INV124", "DATE");
  }

  @Given("the page has a question named {string} in the {string} sub-section")
  public void the_page_has_a_named_question_in_the_sub_section_named(
      final String subSection, final String section) {
    PageIdentifier active = page.active();

    mother.withContentIn(active, subSection, section);
  }
}
