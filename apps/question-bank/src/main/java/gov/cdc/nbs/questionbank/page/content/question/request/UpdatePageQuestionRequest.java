package gov.cdc.nbs.questionbank.page.content.question.request;

public record UpdatePageQuestionRequest(
    String questionLabel,
    String tooltip,
    String display,
    String enabled,
    String required,
    String defaultValue,
    String fieldLength,
    String defaultLabelInReport,
    String dataMartColumnName,
    String adminComments
) {

}
