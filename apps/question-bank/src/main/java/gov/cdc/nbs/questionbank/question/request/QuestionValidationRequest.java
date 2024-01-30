package gov.cdc.nbs.questionbank.question.request;

public record QuestionValidationRequest(String fieldName, String value) {

  public enum FieldName {
    UNIQUE_ID("uniqueId"),
    UNIQUE_NAME("uniqueName"),
    RDB_TABLE_NAME("rdbTableName"),
    DATA_MART_COLUMN_NAME("dataMartColumnName");

    private final String value;

    FieldName(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

}
