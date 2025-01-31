package gov.cdc.nedss.ldf.importer;

import gov.cdc.nedss.systemservice.util.ValidationResult;

import java.io.Serializable;

/**
 *
 * <p>Title: SubformElement</p>
 * <p>Description: Describes each form element of subform </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC </p>
 * @author nmallela
 * @version 1.0
 */
public class SubformElement implements ImportData, Serializable {

	private String id;
	private String name;
	private String dataType;
	private String labelText;
	private String codeSetName;
	private String source;
	private String cdcnationalId;
	private String comment;
	private String nndInd;
	private String objectId;

	/**
	 * @return
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getLabelText() {
		return labelText;
	}

	/**
	 * @param string
	 */
	public void setDataType(String string) {
		dataType = string;
	}

	/**
	 * @param string
	 */
	public void setId(String string) {
		id = string;
	}

	/**
	 * @param string
	 */
	public void setLabelText(String string) {
		labelText = string;
	}

	/**
	 * @return
	 */
	public String getCdcnationalId() {
		return cdcnationalId;
	}

	/**
	 * @return
	 */
	public String getCodeSetName() {
		return codeSetName;
	}

	/**
	 * @return
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param string
	 */
	public void setCdcnationalId(String string) {
		cdcnationalId = string;
	}

	/**
	 * @param string
	 */
	public void setCodeSetName(String string) {
		codeSetName = string;
	}

	/**
	 * @param string
	 */
	public void setSource(String string) {
		source = string;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param string
	 */
	public void setComment(String string) {
		comment = string;
	}

	/**
	 * @return
	 */
	public String getNndInd() {
		return nndInd;
	}

	/**
	 * @param string
	 */
	public void setNndInd(String string) {
		nndInd = string;
	}

	public ValidationResult validateId() {
		if (id == null || id.trim().length() == 0) {

			return new ValidationResult(
				false,
				ImportConstants.NULL_EMBED_DF_ID_TEXT);

		}
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
			&& dataType.equalsIgnoreCase(
				ImportConstants.DATA_TYPE_CODED_VALUE_CODE)
			&& (codeSetName == null || codeSetName.trim().equals(""))) {
			return new ValidationResult(
				false,
				ImportConstants.INVALID_CODE_SET_TEXT);
		} else
			return new ValidationResult(true, "");
	}

	public ValidationResult validateNndInd() {
		if (nndInd == null || nndInd.trim().length() == 0) {

			return new ValidationResult(
				false,
				ImportConstants.NULL_DF_NND_IND_TEXT);

		}
		if (!nndInd.equalsIgnoreCase(ImportConstants.YES)
			&& !nndInd.equalsIgnoreCase(ImportConstants.NO)) {

			return new ValidationResult(
				false,
				ImportConstants.INVALID_DF_NND_IND_TEXT);

		}
		return new ValidationResult(true, "");

	}

	public ValidationResult validateObjectId() {
		if (objectId == null || objectId.trim().length() ==0) {
			return new ValidationResult(
				false,
				ImportConstants.NULL_DF_OBJECT_ID_TEXT);
		} else
			return new ValidationResult(true, "");
	}

	/**
	 * @return
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * @param string
	 */
	public void setObjectId(String string) {
		objectId = string;
	}

}
