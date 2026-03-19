package gov.cdc.nbs.questionbank.page.content.question.model;

import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record EditableQuestion(
    // general
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) CodeSet codeSet,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String uniqueName,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String uniqueId,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String subgroup,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String description,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String label,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String tooltip,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long displayControl,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean visible,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean enabled,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean required,
    String adminComments,
    // type specific
    String questionType,
    String defaultValue,
    Integer fieldLength,
    String mask,
    boolean allowFutureDates,
    Long valueSet,
    Long minValue,
    Long maxValue,
    String relatedUnitsLiteral,
    Long relatedUnitsValueSet,
    // reporting
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportingInfo dataMartInfo,
    // messaging
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) MessagingInfo messagingInfo) {}
