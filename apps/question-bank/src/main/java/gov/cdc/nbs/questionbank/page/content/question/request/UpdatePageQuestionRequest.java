package gov.cdc.nbs.questionbank.page.content.question.request;

import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;

public sealed interface UpdatePageQuestionRequest permits UpdatePageTextQuestionRequest,
    UpdatePageNumericQuestionRequest, UpdatePageDateQuestionRequest, UpdatePageCodedQuestionRequest {
  // general
  String label();

  String tooltip();

  boolean visible();

  boolean enabled();

  boolean required();

  long displayControl();

  // reporting
  ReportingInfo dataMartInfo();

  // messaging
  MessagingInfo messagingInfo();

  // admin
  String adminComments();
}
