package gov.cdc.nbs.questionbank.page.content.question.request;

import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePageCodedQuestionRequest(
    // general
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String label,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String tooltip,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean visible,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean enabled,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean required,
    long displayControl,
    // coded specific
    Long valueSet,
    String defaultValue,
    // reporting
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportingInfo dataMartInfo,
    // messaging
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) MessagingInfo messagingInfo,
    // admin
    String adminComments)
    implements UpdatePageQuestionRequest {}
