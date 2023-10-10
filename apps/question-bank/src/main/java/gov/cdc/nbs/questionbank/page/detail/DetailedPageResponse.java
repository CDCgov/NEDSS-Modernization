package gov.cdc.nbs.questionbank.page.detail;

import java.util.Collection;
import java.util.List;

public record DetailedPageResponse(
    long id,
    String name,
    String pageDescription,
    Collection<Tab> pageTabs,
    Collection<Rule> rules
    ) {

  DetailedPageResponse(
      long id,
      String name,
      String pageDescription,
      Collection<Rule> rules
  ) {
    this(
        id,
        name,
        pageDescription,
        List.of(),
        rules
        );
  }

  record Tab(
      long id,
      String name,
      boolean visible,
      Collection<Section> tabSections
  ) {
  }


  record Section(
      long id,
      String name,
      boolean visible,
      Collection<SubSection> sectionSubSections
  ) {
  }


  record SubSection(
      long id,
      String name,
      boolean visible,
      Collection<Question> pageQuestions
  ) {
  }

  record Question(
      long id,
      String questionType,
      String questionIdentifier,
      String name,
      String subGroup,
      String description,
      boolean coInfection,
      String dataType,
      String mask,
      boolean allowFutureDates,
      String labelOnScreen,
      String questionToolTip,
      boolean display,
      boolean enabled,
      boolean required,
      String valueSet
  ) {
  }

  record Rule (
      long id,
      long pageId,
      String logic,
      String values,
      String function,
      String sourceField,
      String targetField
  ) {}
}
