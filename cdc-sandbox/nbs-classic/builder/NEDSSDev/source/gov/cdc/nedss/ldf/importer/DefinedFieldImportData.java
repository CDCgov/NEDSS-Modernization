/*
 * Created on Jan 2, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

import gov.cdc.nedss.systemservice.util.ValidationResult;

import java.io.*;

/**
 * @author xzheng
 *
 * This class represents the custom defined field nodes
 * specified in the import XML file.
 *
 */
public class DefinedFieldImportData implements ImportData, Serializable {

	private static String JAVASCRIPT_VALIDATION_TEXT_CODE =
		ImportConstants.JAVASCRIPT_VALIDATION_TEXT_CODE;
		

	private String objectID;
	private String comment;
	private String actionType;
	private String NEDSSBussinessObjectName;
	private String conditionCode;
	private String categoryType;
	private String classCode;
	private String nationalIdentifier;
	private String dataType;
	private String codeSetName;
	private String displayOrder;
	private String fieldSize;
	private String labelText;
	private String validationText;
	private String requiredIndicator;
	private String pageSetID;
	private String validationJavaScriptText;
	private String stateCode;
	private String deploymentCode;
	private String NNDIndicator;
	private String importVersionNbr;

	/**
	 *
	 * @param objectID
	 * @return
	 */
	public String getObjectID() {
		return this.objectID;
	}
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getActionType() {
		return this.actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getNEDSSBussinessObjectName() {
		return this.NEDSSBussinessObjectName;
	}
	public void setNEDSSBussinessObjectName(String NEDSSBussinessObjectName) {
		this.NEDSSBussinessObjectName = NEDSSBussinessObjectName;
	}
	public String getConditionCode() {
		return this.conditionCode;
	}
	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}
	public String getCategoryType() {
		return this.categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getClassCode() {
		return this.classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getNationalIdentifier() {
		return this.nationalIdentifier;
	}
	public void setNationalIdentifier(String nationalIdentifier) {
		this.nationalIdentifier = nationalIdentifier;
	}
	public String getDataType() {
		return this.dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getCodeSetName() {
		return this.codeSetName;
	}
	public void setCodeSetName(String codeSetName) {
		this.codeSetName = codeSetName;
	}

	public String getDisplayOrder() {
		return this.displayOrder;
	}
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getFieldSize() {
		return this.fieldSize;
	}
	public void setFieldSize(String fieldSize) {
		this.fieldSize = fieldSize;
	}
	public String getLabelText() {
		return this.labelText;
	}
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}
	public String getValidationText() {
		return this.validationText;
	}
	public void setValidationText(String validationText) {
		this.validationText = validationText;
	}
	public String getRequiredIndicator() {
		return this.requiredIndicator;
	}
	public void setRequiredIndicator(String requiredIndicator) {
		this.requiredIndicator = requiredIndicator;
	}

	public String getPageSetID() {
		return this.pageSetID;
	}
	public void setPageSetID(String pageSetID) {
		this.pageSetID = pageSetID;
	}
	public String getValidationJavaScriptText() {
		return this.validationJavaScriptText;
	}
	public void setValidationJavaScriptText(String validationJavaScriptText) {
		this.validationJavaScriptText = validationJavaScriptText;
	}

	public String getStateCode() {
		return this.stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getDeploymentCode() {
		return this.deploymentCode;
	}
	public void setDeploymentCode(String deploymentCode) {
		this.deploymentCode = deploymentCode;
	}
	public String getNNDIndicator() {
		return this.NNDIndicator;
	}
	public void setNNDIndicator(String NNDIndicator) {
		this.NNDIndicator = NNDIndicator;
	}

	public String getImportVersionNbr() {
		return this.importVersionNbr;
	}
	public void setImportVersionNbr(String importVersionNbr) {
		this.importVersionNbr = importVersionNbr;
	}

	public ValidationResult validateValidationJavaScriptText() {
		if (validationText != null
			&& validationText.equalsIgnoreCase(ImportConstants.JAVASCRIPT_VALIDATION_TEXT_CODE)
			&& (validationJavaScriptText == null
				|| validationJavaScriptText.trim().equals(""))) {
			return new ValidationResult(
				false,
				ImportConstants.INVALID_JAVASCRIPT_TEXT);
		} else
			return new ValidationResult(true, "");
	}

	public ValidationResult validateLabelText() {
		if (labelText == null || labelText.length() > 300) {
			return new ValidationResult(
				false,
				ImportConstants.INVALID_LABEL_TEXT);
		} else
			return new ValidationResult(true, "");
	}

	public ValidationResult validateCodeSetName() {
		// for now do not validate whether the code set is valid
		if (dataType != null
		&& dataType.equalsIgnoreCase(ImportConstants.DATA_TYPE_CODED_VALUE_CODE)
		&& (codeSetName == null
			|| codeSetName.trim().equals(""))) {
			return new ValidationResult(
				false,
				ImportConstants.INVALID_CODE_SET_TEXT);
		} else
			return new ValidationResult(true, "");
	}

}
