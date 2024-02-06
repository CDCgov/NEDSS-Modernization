package gov.cdc.nbs.questionbank.question.request;

import io.swagger.annotations.ApiModelProperty;

public record QuestionValidationRequest(
    @ApiModelProperty(required = true) Field field,
    @ApiModelProperty(required = true) String value) {

  public enum Field {
    UNIQUE_ID,
    UNIQUE_NAME,
    RDB_COLUMN_NAME,
    DATA_MART_COLUMN_NAME
  }

}
