package gov.cdc.nbs.questionbank.page.content.question.request;

import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import io.swagger.annotations.ApiModelProperty;

public record UpdatePageTextQuestionRequest(
    // general
    @ApiModelProperty(required = true) String label,
    @ApiModelProperty(required = true) String tooltip,
    @ApiModelProperty(required = true) boolean visible,
    @ApiModelProperty(required = true) boolean enabled,
    @ApiModelProperty(required = true) boolean required,
    long displayControl,
    // text specific
    String defaultValue,
    Integer fieldLength,
    // reporting
    @ApiModelProperty(required = true) ReportingInfo dataMartInfo,
    // messaging
    @ApiModelProperty(required = true) MessagingInfo messagingInfo,
    // admin
    String adminComments) implements UpdatePageQuestionRequest {

}
