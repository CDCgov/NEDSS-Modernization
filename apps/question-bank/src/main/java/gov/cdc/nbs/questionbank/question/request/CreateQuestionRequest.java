package gov.cdc.nbs.questionbank.question.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CreateQuestionRequest {
    private String codeSet;
    private String uniqueId;
    private String uniqueName;
    private String subgroup;
    private String description;
    private String label;
    private String tooltip;
    private Long displayControl;
    private ReportingInfo dataMartInfo;
    private MessagingInfo messagingInfo;
    private String adminComments;
    private QuestionType type;

    public record ReportingInfo(
            String reportLabel,
            String defaultRdbTableName,
            String rdbColumnName,
            String dataMartColumnName) {
    }

    public record MessagingInfo(
            boolean includedInMessage,
            String messageVariableId,
            String labelInMessage,
            String codeSystem,
            boolean requiredInMessage,
            String hl7DataType) {
    }

}
