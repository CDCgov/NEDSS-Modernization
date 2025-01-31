package gov.cdc.nedss.pam.act;

import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;

import java.lang.reflect.Field;
/**
* Name:		NBSCaseAnswer.java
* Description:	DT for NBS CASE answers.
* Copyright:	Copyright (c) 2008
* Company: 	Computer Sciences Corporation
* @author	Pradeep Sharma
* Modified by Ashok kumar
*/
public class NbsCaseAnswerDT extends NbsAnswerDT {
	private static final long serialVersionUID = 1L;
	private Long nbsCaseAnswerUid;
	private Long nbsTableMetadataUid;
	private String code;
	private String value;
	private String type;
	private String OtherType;
	private boolean updateNbsQuestionUid;
	
	public NbsCaseAnswerDT() {
	}

	public NbsCaseAnswerDT(NbsAnswerDT answerDT) {
		super(answerDT);
		if (answerDT.getNbsAnswerUid() != null)
			nbsCaseAnswerUid = answerDT.getNbsAnswerUid();
	}
	
	/**
	 * @deprecated This variable is used for pam conversion process only
	 */
	public String getOtherType() {
		return OtherType;
	}
	/**
	 * @deprecated This variable is used for pam conversion process only
	 */
	public void setOtherType(String otherType) {
		OtherType = otherType;
	}
	/**
	 * @deprecated This variable is used for pam conversion process only
	 */
	public String getType() {
		return type;
	}
	/**
	 * @deprecated This variable is used for pam conversion process only
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @deprecated This variable is used for pam conversion process only
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @deprecated This variable is used for pam conversion process only
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @deprecated This variable is used for pam conversion process only
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @deprecated This variable is used for pam conversion process only
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @deprecated This variable is used for pam conversion process only
	 */
	private Long legacyUid;
	
	/**
	 * @deprecated <b>WARNING!<b> This variable is used for pam conversion process only. Please do not use in your code!!!
	 */
	public Long getLegacyUid() {
		return legacyUid;
	}
	/**
	 * @deprecated <b>WARNING!<b> This variable is used for pam conversion process only
	 */
	public void setLegacyUid(Long legacyUid) {
		this.legacyUid = legacyUid;
	}
	
	/**
	 * @deprecated <b>WARNING!<b> This variable is used for pam conversion process only
	 */
	private String questionIdentifier; 
	
	/**
	 * @deprecated <b>WARNING!<b> This variable is used for pam conversion process only
	 */
	public String getQuestionIdentifier() {
		return questionIdentifier;
	}
	/**
	 * @deprecated <b>WARNING!<b> This variable is used for pam conversion process only
	 */
	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}
	
	/**
	 * @deprecated <b>WARNING!<b> This variable is used for PageBuilder to PageBuilder conversion process only
	 */
	public boolean isUpdateNbsQuestionUid() {
		return updateNbsQuestionUid;
	}

	/**
	 * @deprecated <b>WARNING!<b> This variable is used for PageBuilder to PageBuilder conversion process only
	 */
	public void setUpdateNbsQuestionUid(boolean updateNbsQuestionUid) {
		this.updateNbsQuestionUid = updateNbsQuestionUid;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = NbsCaseAnswerDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public Long getNbsCaseAnswerUid() {
		return nbsCaseAnswerUid;
	}
	public void setNbsCaseAnswerUid(Long nbsCaseAnswerUid) {
		this.nbsCaseAnswerUid = nbsCaseAnswerUid;
		super.setNbsAnswerUid(nbsCaseAnswerUid);
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Long getNbsTableMetadataUid() {
		return nbsTableMetadataUid;
	}
	public void setNbsTableMetadataUid(Long nbsTableMetadataUid) {
		this.nbsTableMetadataUid = nbsTableMetadataUid;
	}
	


}