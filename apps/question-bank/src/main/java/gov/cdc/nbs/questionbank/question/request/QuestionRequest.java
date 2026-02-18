package gov.cdc.nbs.questionbank.question.request;

import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {

  private String uniqueName;
  private String description;
  private String label;
  private String tooltip;
  private Long displayControl;
  private ReportingInfo dataMartInfo;
  private MessagingInfo messagingInfo;
  private String adminComments;

  public record ReportingInfo(
      String reportLabel,
      String defaultRdbTableName,
      String rdbColumnName,
      String dataMartColumnName) {}
}
