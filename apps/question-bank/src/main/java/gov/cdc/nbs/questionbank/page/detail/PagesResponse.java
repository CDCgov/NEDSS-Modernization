package gov.cdc.nbs.questionbank.page.detail;

import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.List;

public record PagesResponse(
    @ApiModelProperty(required = true)
    long id,
    @ApiModelProperty(required = true)
    String name,
    @ApiModelProperty(required = true)
    String status,
    String description,
    long root,
    Collection<PagesTab> tabs,
    Collection<PageRule> rules
    ) {

  PagesResponse(
      long id,
      String name,
      String status,
      String description,
      long root,
      Collection<PageRule> rules
  ) {
    this(
        id,
        name,
        status,
        description,
        root,
        List.of(),
        rules
        );
  }

  public record PagesTab(
      @ApiModelProperty(required = true)
      long id,
      @ApiModelProperty(required = true)
      String name,
      @ApiModelProperty(required = true)
      int order,
      @ApiModelProperty(required = true)
      boolean visible,
      @ApiModelProperty(required = true)
      Collection<PagesSection> sections
  ) {
  }


  public record PagesSection(
      @ApiModelProperty(required = true)
      long id,
      @ApiModelProperty(required = true)
      String name,
      @ApiModelProperty(required = true)
      int order,
      @ApiModelProperty(required = true)
      boolean visible,
      @ApiModelProperty(required = true)
      Collection<PagesSubSection> subSections
  ) {
  }


  public record PagesSubSection(
      @ApiModelProperty(required = true)
      long id,
      @ApiModelProperty(required = true)
      String name,
      @ApiModelProperty(required = true)
      int order,
      @ApiModelProperty(required = true)
      boolean visible,
      @ApiModelProperty(required = true)
      Collection<PagesQuestion> questions
  ) {
  }

  public record PagesQuestion(
      @ApiModelProperty(required = true)
      long id,
      boolean isStandard,
      String standard,
      String question,
      @ApiModelProperty(required = true)
      String name,
      @ApiModelProperty(required = true)
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
      long displayComponent,
      String adminComments
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
