package gov.cdc.nbs.questionbank.question.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestionValidationRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Field field,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String value) {

  public enum Field {
    UNIQUE_ID,
    UNIQUE_NAME,
    RDB_COLUMN_NAME,
    DATA_MART_COLUMN_NAME
  }
}
