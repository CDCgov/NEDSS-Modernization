package gov.cdc.nbs.questionbank.question.request;

import gov.cdc.nbs.questionbank.question.request.update.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateQuestion {
  String uniqueName; // editable if not in use
  String description;
  String type; // editable if not in use
  String label;
  String tooltip;
  Long displayControl;
  String adminComments;

  // Shared
  String defaultValue;
  String mask;

  // Text question specific fields
  Integer fieldLength;

  // Date question specific fields
  boolean allowFutureDates;

  // Coded question specific fields
  Long valueSet;

  // Numeric question specific fields
  Long minValue;
  Long maxValue;
  String relatedUnitsLiteral;
  Long relatedUnitsValueSet;

  // data mart
  String defaultLabelInReport;
  String dataMartColumnName;
  String rdbColumnName; // Editable if not 'in use'

  // messaging info - always editable
  boolean includedInMessage;
  String messageVariableId;
  String labelInMessage;
  String codeSystem;
  boolean requiredInMessage;
  String hl7DataType;

  private void setShared(UpdateQuestionRequest updateQuestionRequest) {
    this.uniqueName = updateQuestionRequest.getUniqueName();
    this.description = updateQuestionRequest.getDescription();
    this.type = updateQuestionRequest.getType().toString();
    this.label = updateQuestionRequest.getLabel();
    this.tooltip = updateQuestionRequest.getTooltip();
    this.displayControl = updateQuestionRequest.getDisplayControl();
    this.adminComments = updateQuestionRequest.getAdminComments();

    this.defaultLabelInReport = updateQuestionRequest.getDataMartInfo().reportLabel();
    this.dataMartColumnName = updateQuestionRequest.getDataMartInfo().dataMartColumnName();
    this.rdbColumnName = updateQuestionRequest.getDataMartInfo().rdbColumnName();

    this.includedInMessage = updateQuestionRequest.getMessagingInfo().includedInMessage();
    this.messageVariableId = updateQuestionRequest.getMessagingInfo().messageVariableId();
    this.labelInMessage = updateQuestionRequest.getMessagingInfo().labelInMessage();
    this.codeSystem = updateQuestionRequest.getMessagingInfo().codeSystem();
    this.requiredInMessage = updateQuestionRequest.getMessagingInfo().requiredInMessage();
    this.hl7DataType = updateQuestionRequest.getMessagingInfo().hl7DataType();
  }

  public UpdateQuestion(UpdateTextQuestionRequest updateTextQuestionRequest) {
    setShared(updateTextQuestionRequest);
    this.fieldLength = updateTextQuestionRequest.getFieldLength();
    this.mask = updateTextQuestionRequest.getMask().toString();
    this.defaultValue = updateTextQuestionRequest.getDefaultValue();
    this.type = UpdateQuestionRequest.DataType.TEXT.toString();
  }

  public UpdateQuestion(UpdateCodedQuestionRequest updateCodedQuestionRequest) {
    setShared(updateCodedQuestionRequest);
    this.valueSet = updateCodedQuestionRequest.getValueSet();
    this.defaultValue = updateCodedQuestionRequest.getDefaultValue();
    this.type = UpdateQuestionRequest.DataType.CODED.toString();
  }

  public UpdateQuestion(UpdateDateQuestionRequest updateDateQuestionRequest) {
    setShared(updateDateQuestionRequest);
    this.mask = updateDateQuestionRequest.getMask().toString();
    this.allowFutureDates = updateDateQuestionRequest.isAllowFutureDates();
    this.type = UpdateQuestionRequest.DataType.DATE.toString();
  }

  public UpdateQuestion(UpdateNumericQuestionRequest updateNumericQuestionRequest) {
    setShared(updateNumericQuestionRequest);
    this.mask = updateNumericQuestionRequest.getMask().toString();
    this.fieldLength = updateNumericQuestionRequest.getFieldLength();
    this.defaultValue = updateNumericQuestionRequest.getDefaultValue().toString();
    this.minValue = updateNumericQuestionRequest.getMinValue();
    this.maxValue = updateNumericQuestionRequest.getMaxValue();
    this.type = UpdateQuestionRequest.DataType.NUMERIC.toString();
    this.relatedUnitsLiteral = updateNumericQuestionRequest.getRelatedUnitsLiteral();
    this.relatedUnitsValueSet = updateNumericQuestionRequest.getRelatedUnitsValueSet();
  }
}
