package gov.cdc.nbs.questionbank.page.content.question.request;

import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;

@SuppressWarnings(
    //  Sealed classes require the implementing classes be listed if not in the same file
    "javaarchitecture:S7027")
public sealed interface UpdatePageQuestionRequest
    permits UpdatePageTextQuestionRequest,
        UpdatePageNumericQuestionRequest,
        UpdatePageDateQuestionRequest,
        UpdatePageCodedQuestionRequest {
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
