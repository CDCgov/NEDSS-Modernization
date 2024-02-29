package gov.cdc.nbs.questionbank.page.content.question.request;

import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.create.CreateDateQuestionRequest.DateMask;
import io.swagger.annotations.ApiModelProperty;

public record UpdatePageDateQuestionRequest(
    // general
    @ApiModelProperty(required = true) String label,
    @ApiModelProperty(required = true) String tooltip,
    @ApiModelProperty(required = true) boolean visible,
    @ApiModelProperty(required = true) boolean enabled,
    @ApiModelProperty(required = true) boolean required,
    long displayControl,
    // date specific
    DateMask mask,
    boolean allowFutureDates,
    // reporting
    @ApiModelProperty(required = true) ReportingInfo datamartInfo,
    // messaging
    @ApiModelProperty(required = true) MessagingInfo messagingInfo,
    // admin
    String adminComments) implements UpdatePageQuestionRequest {

}
