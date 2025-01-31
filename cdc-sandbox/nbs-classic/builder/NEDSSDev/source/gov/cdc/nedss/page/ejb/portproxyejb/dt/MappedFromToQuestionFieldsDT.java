package gov.cdc.nedss.page.ejb.portproxyejb.dt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMappingDT;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;

public class MappedFromToQuestionFieldsDT extends NBSConversionMappingDT{
	
	private String waUiMetadataUid;
	private String recordStatusCd;
	private String questionMappedCode="";
	private String questionMappedDesc="";
	private String statusDesc=PortPageUtil.STATUS_MAPPING_NEEDED;	//Mapping status Description
	private String statusCode=PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED;	// Mapping status code
	private boolean fieldMappingRequired;
	private boolean codeMappingRequired;
	private Long codeSetGroupId;
	private String autoMapped;
	private String answerMappedCode="";
	private String answerMappedDesc="";
	private Integer questionGroupSeqNbr; // to find out if question is batch entry.
	private Integer answerGroupSeqNbr; // For repeating block to discrete question to find out home any mapped
	private Long waPortMappingUid;
	private Long nbsConversionPageMgmtUid;
	private Long toCodeSetGroupId;
	private Long toNbsUiComponentUid;
	private String fromCodeDesc;
	private String toCodeDesc;
	private Long fromNbsUiComponentUid;
	private boolean questionEditFlag = true;
	private Integer toQuestionGroupSeqNbr; // ToSide of question's groupSequenceNumber, it used for repeating to repeating mappings
	
	public String getFromCodeDesc() {
		return fromCodeDesc;
	}
	public void setFromCodeDesc(String fromCodeDesc) {
		this.fromCodeDesc = fromCodeDesc;
	}
	public String getToCodeDesc() {
		return toCodeDesc;
	}
	public void setToCodeDesc(String toCodeDesc) {
		this.toCodeDesc = toCodeDesc;
	}
	public String getWaUiMetadataUid() {
		return waUiMetadataUid;
	}
	public void setWaUiMetadataUid(String waUiMetadataUid) {
		this.waUiMetadataUid = waUiMetadataUid;
	}
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}
	
	
	public String getQuestionMappedCode() {
		return questionMappedCode;
	}
	public void setQuestionMappedCode(String questionMappedCode) {
		this.questionMappedCode = questionMappedCode;
	}
	public String getQuestionMappedDesc() {
		return questionMappedDesc;
	}
	public void setQuestionMappedDesc(String questionMappedDesc) {
		this.questionMappedDesc = questionMappedDesc;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public boolean isFieldMappingRequired() {
		return fieldMappingRequired;
	}
	public void setFieldMappingRequired(boolean fieldMappingRequired) {
		this.fieldMappingRequired = fieldMappingRequired;
	}
	public boolean isCodeMappingRequired() {
		return codeMappingRequired;
	}
	public void setCodeMappingRequired(boolean codeMappingRequired) {
		this.codeMappingRequired = codeMappingRequired;
	}
	public Long getCodeSetGroupId() {
		return codeSetGroupId;
	}
	public void setCodeSetGroupId(Long codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
	}
	public String getAutoMapped() {
		return autoMapped;
	}
	public void setAutoMapped(String autoMapped) {
		this.autoMapped = autoMapped;
	}
	
	public String getAnswerMappedCode() {
		return answerMappedCode;
	}
	public void setAnswerMappedCode(String answerMappedCode) {
		this.answerMappedCode = answerMappedCode;
	}
	public String getAnswerMappedDesc() {
		return answerMappedDesc;
	}
	public void setAnswerMappedDesc(String answerMappedDesc) {
		this.answerMappedDesc = answerMappedDesc;
	}
	public Integer getQuestionGroupSeqNbr() {
		return questionGroupSeqNbr;
	}
	public void setQuestionGroupSeqNbr(Integer questionGroupSeqNbr) {
		this.questionGroupSeqNbr = questionGroupSeqNbr;
	}
	public Integer getAnswerGroupSeqNbr() {
		return answerGroupSeqNbr;
	}
	public void setAnswerGroupSeqNbr(Integer answerGroupSeqNbr) {
		this.answerGroupSeqNbr = answerGroupSeqNbr;
	}
	public Long getWaPortMappingUid() {
		return waPortMappingUid;
	}
	public void setWaPortMappingUid(Long waPortMappingUid) {
		this.waPortMappingUid = waPortMappingUid;
	}
	public Long getNbsConversionPageMgmtUid() {
		return nbsConversionPageMgmtUid;
	}
	public void setNbsConversionPageMgmtUid(Long nbsConversionPageMgmtUid) {
		this.nbsConversionPageMgmtUid = nbsConversionPageMgmtUid;
	}
	public Long getToCodeSetGroupId() {
		return toCodeSetGroupId;
	}
	public void setToCodeSetGroupId(Long toCodeSetGroupId) {
		this.toCodeSetGroupId = toCodeSetGroupId;
	}
	public Long getToNbsUiComponentUid() {
		return toNbsUiComponentUid;
	}
	public void setToNbsUiComponentUid(Long toNbsUiComponentUid) {
		this.toNbsUiComponentUid = toNbsUiComponentUid;
	}
	public Long getFromNbsUiComponentUid() {
		return fromNbsUiComponentUid;
	}
	public void setFromNbsUiComponentUid(Long fromNbsUiComponentUid) {
		this.fromNbsUiComponentUid = fromNbsUiComponentUid;
	}
	public boolean isQuestionEditFlag() {
		return questionEditFlag;
	}
	public void setQuestionEditFlag(boolean questionEditFlag) {
		this.questionEditFlag = questionEditFlag;
	}
	public Integer getToQuestionGroupSeqNbr() {
		return toQuestionGroupSeqNbr;
	}
	public void setToQuestionGroupSeqNbr(Integer toQuestionGroupSeqNbr) {
		this.toQuestionGroupSeqNbr = toQuestionGroupSeqNbr;
	}
	
	/**
	 * This  method will prepare the clone for the object
	 * @return deepCopy -- the clone Object
	 * @throws CloneNotSupportedException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object deepCopy() throws CloneNotSupportedException, IOException, ClassNotFoundException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object deepCopy = ois.readObject();

        return  deepCopy;
    }
}
