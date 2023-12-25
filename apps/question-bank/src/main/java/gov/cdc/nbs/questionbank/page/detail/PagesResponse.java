package gov.cdc.nbs.questionbank.page.detail;

import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.List;

public record PagesResponse(
    @ApiModelProperty(required = true)
    long id,
    @ApiModelProperty(required = true)
    String name,
    String description,
    long root,
    Collection<PagesTab> tabs,
    Collection<PageRule> rules
    ) {

  PagesResponse(
      long id,
      String name,
      String description,
      long root,
      Collection<PageRule> rules
  ) {
    this(
        id,
        name,
        description,
        root,
        List.of(),
        rules
        );
  }

  public record PagesTab(
      long id,
      String name,
      int order,
      boolean visible,
      Collection<PagesSection> sections
  ) {
  }


  public record PagesSection(
      long id,
      String name,
      int order,
      boolean visible,
      Collection<PagesSubSection> subSections
  ) {
  }


  public record PagesSubSection(
      long id,
      String name,
      int order,
      boolean visible,
      Collection<PagesQuestion> questions
  ) {
  }

  public record PagesQuestion(
      long id,
      boolean isStandard,
      String standard,
      String question,
      String name,
      int order,
      String subGroup,
      String description,
      boolean coInfection,
      String dataType,
      String mask,
      boolean allowFutureDates,
      String tooltip,
      boolean display,
      boolean enabled,
      boolean required,
      String defaultValue,
      String valueSet,
      long displayComponent
  ) {
  }

  public record PageRule(
      long id,
      long page,
      String logic,
      String values,
      String function,
      String sourceField,
      String targetField
  ) {}
}
