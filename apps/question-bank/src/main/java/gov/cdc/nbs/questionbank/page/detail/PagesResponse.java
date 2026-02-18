package gov.cdc.nbs.questionbank.page.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.List;

public record PagesResponse(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long id,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String status,
    String description,
    long root,
    Collection<PagesTab> tabs,
    Collection<BusinessRule> rules) {

  PagesResponse(
      long id,
      String name,
      String status,
      String description,
      long root,
      Collection<BusinessRule> rules) {
    this(id, name, status, description, root, List.of(), rules);
  }

  public record PagesTab(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long id,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) int order,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean visible,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Collection<PagesSection> sections) {}

  public record PagesSection(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long id,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) int order,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean visible,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
          Collection<PagesSubSection> subSections) {}

  public record PagesSubSection(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long id,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) int order,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean visible,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean isGrouped,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean isGroupable,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String questionIdentifier,
      String blockName,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Collection<PagesQuestion> questions) {}

  public record PagesQuestion(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long id,
      boolean isStandardNnd,
      boolean isStandard,
      String standard,
      String question,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) int order,
      int questionGroupSeq,
      String subGroup,
      String description,
      boolean coInfection,
      String dataType,
      String mask,
      boolean allowFutureDates,
      String tooltip,
      boolean visible,
      boolean enabled,
      boolean required,
      String defaultValue,
      String valueSet,
      long displayComponent,
      String adminComments,
      String fieldLength,
      String defaultRdbTableName,
      String rdbColumnName,
      String defaultLabelInReport,
      String dataMartColumnName,
      boolean isPublished,
      String blockName,
      Integer dataMartRepeatNumber,
      boolean appearsInBatch,
      String batchLabel,
      Integer batchWidth,
      String componentBehavior,
      String componentName,
      String classCode) {}

  public record BusinessRule(
      long id,
      long page,
      String logic,
      String values,
      String function,
      String sourceField,
      String targetField) {}
}
