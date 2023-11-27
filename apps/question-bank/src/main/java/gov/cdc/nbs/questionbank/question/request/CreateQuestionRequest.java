package gov.cdc.nbs.questionbank.question.request;

import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CreateQuestionRequest {
    private CodeSet codeSet;
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
