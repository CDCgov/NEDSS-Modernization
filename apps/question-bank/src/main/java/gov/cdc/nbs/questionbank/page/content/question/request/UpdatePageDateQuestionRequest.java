package gov.cdc.nbs.questionbank.page.content.question.request;

import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.create.DateMask;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePageDateQuestionRequest(
    // general
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String label,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String tooltip,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean visible,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean enabled,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) boolean required,
    long displayControl,
    // date specific
    DateMask mask,
    boolean allowFutureDates,
    // reporting
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportingInfo dataMartInfo,
    // messaging
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) MessagingInfo messagingInfo,
    // admin
    String adminComments)
    implements UpdatePageQuestionRequest {}
