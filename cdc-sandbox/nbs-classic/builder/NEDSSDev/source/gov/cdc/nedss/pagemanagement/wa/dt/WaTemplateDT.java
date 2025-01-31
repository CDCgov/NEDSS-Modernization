package gov.cdc.nedss.pagemanagement.wa.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

public class WaTemplateDT extends AbstractVO  implements RootDTInterface {
	
	private static final long serialVersionUID = 1L;
	private Long waTemplateUid;
	private String templateType;
	private String xmlPayload;
	private Integer publishVersionNbr;
	private String formCd;
	private String conditionCd;
	private String busObjType;
	private String dataMartNm;
	private String recStatusCd;	
	private Timestamp recStatusTime;	
	private Timestamp lastChgTime;
	private Long lastChgUserId;	
	private String messageProfileId;
	private String descTxt;
	private String pageNm;
	private Long waTemplateRefUid;
	private String templateNm;
	private String editLink;
	private String viewLink;
	private String version;
	private String conditionCdDesc;
	private Timestamp addTime;
	private Long addUserId;	
	private String publishIndCd;
	private String portReqIndCd;
	private String messageId;
	private String firstLastName;
	private String nndEntityIdentifier;
	private String lastChgUserNm;	
	private Object exportTemplate;
	private String sourceNm;
	private String versionNote;
	private String relatedConditions;
	private String lastChgDate;
	public String getRelatedConditions() {
		return relatedConditions;
	}

	public void setRelatedConditions(String relatedConditions) {
		this.relatedConditions = relatedConditions;
	}

	public String getRelatedConditionsForPrint() {
		return relatedConditionsForPrint;
	}

	public void setRelatedConditionsForPrint(String relatedConditionsForPrint) {
		this.relatedConditionsForPrint = relatedConditionsForPrint;
	}

	private String relatedConditionsForPrint;
	

	public String getSourceNm() {
		return sourceNm;
	}

	public void setSourceNm(String sourceNm) {
		this.sourceNm = sourceNm;
	}

	public Object getExportTemplate() {
		return exportTemplate;
	}

	public void setExportTemplate(Object exportTemplate) {
		this.exportTemplate = exportTemplate;
	}

	public String getLastChgUserNm() {
		return lastChgUserNm;
	}

	public void setLastChgUserNm(String lastChgUserNm) {
		this.lastChgUserNm = lastChgUserNm;
	}

	public String getNndEntityIdentifier() {
		return nndEntityIdentifier;
	}

	public void setNndEntityIdentifier(String nndEntityIdentifier) {
		this.nndEntityIdentifier = nndEntityIdentifier;
	}

	public String getMessageProfileId() {
		return messageProfileId;
	}

	public void setMessageProfileId(String messageProfileId) {
		this.messageProfileId = messageProfileId;
	}

	public String getDescTxt() {
		return descTxt;
	}

	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}

	public String getPageNm() {
		return pageNm;
	}

	public void setPageNm(String pageNm) {
		this.pageNm = pageNm;
	}

	//Not persisted by DAO - used to pass template uid to use to start a new page with
	public Long getWaTemplateRefUid() {
		return waTemplateRefUid;
	}

	public void setWaTemplateRefUid(Long waTemplateRefUid) {
		this.waTemplateRefUid = waTemplateRefUid;
	}

	


	public Long getWaTemplateUid() {
		return waTemplateUid;
	}

	public void setWaTemplateUid(Long waTemplateUid) {
		this.waTemplateUid = waTemplateUid;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getXmlPayload() {
		return xmlPayload;
	}

	public void setXmlPayload(String xmlPayload) {
		this.xmlPayload = xmlPayload;
	}

	public Integer getPublishVersionNbr() {
		return publishVersionNbr;
	}

	public void setPublishVersionNbr(Integer publishVersionNbr) {
		this.publishVersionNbr = publishVersionNbr;
	}

	public String getFormCd() {
		return formCd;
	}

	public void setFormCd(String formCd) {
		this.formCd = formCd;
	}

	public String getConditionCd() {
		return conditionCd;
	}

	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}

	public String getBusObjType() {
		return busObjType;
	}

	public void setBusObjType(String busObjType) {
		this.busObjType = busObjType;
	}

	public String getDataMartNm() {
		return dataMartNm;
	}

	public void setDataMartNm(String dataMartNm) {
		this.dataMartNm = dataMartNm;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	/**
	 * @param objectname1
	 * @param objectname2
	 * @param voClass
	 * @return boolean
	 */
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		return true;
	}

	/**
	 * @param itDirty
	 */
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}

	/**
	 * @return boolean
	 */
	public boolean isItDirty() {
		return this.itDirty;
	}

	/**
	 * @param itNew
	 */
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}

	/**
	 * @return boolean
	 */
	public boolean isItNew() {
		return this.itNew;
	}

	/**
	 * @param itDelete
	 */
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}

	/**
	 * @return boolean
	 */
	public boolean isItDelete() {
		return this.itDelete;
	}
	
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}

	public Long getLastChgUserId() {
		return lastChgUserId;
	}

	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}	
	
	

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLocalId(String localId) {
		// TODO Auto-generated method stub
		
	}

	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}

	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	public void setRecordStatusCd(String recordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		// TODO Auto-generated method stub
		
	}

	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub
		
	}

	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub
		
	}

	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}

	public String getRecStatusCd() {
		return recStatusCd;
	}

	public void setRecStatusCd(String recStatusCd) {
		this.recStatusCd = recStatusCd;
	}

	public Timestamp getRecStatusTime() {
		return recStatusTime;
	}

	public void setRecStatusTime(Timestamp recStatusTime) {
		this.recStatusTime = recStatusTime;
	}
	
	public Integer getVersionCtrlNbr() {
		return null;
	}

	public String getTemplateNm() {
		return templateNm;
	}

	public void setTemplateNm(String templateNm) {
		this.templateNm = templateNm;
	}

	public String getEditLink() {
		return editLink;
	}

	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}

	public String getViewLink() {
		return viewLink;
	}

	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getConditionCdDesc() {
		return conditionCdDesc;
	}

	public void setConditionCdDesc(String conditionCdDesc) {
		this.conditionCdDesc = conditionCdDesc;
	}

	public void setPublishIndCd(String publishIndCd) {
		this.publishIndCd = publishIndCd;
	}

	public String getPublishIndCd() {
		return publishIndCd;
	}

	public String getPortReqIndCd() {
		return portReqIndCd;
	}

	public void setPortReqIndCd(String portReqIndCd) {
		this.portReqIndCd = portReqIndCd;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getFirstLastName() {
		return firstLastName;
	}

	public void setFirstLastName(String firstLastName) {
		this.firstLastName = firstLastName;
	}
	
	public String getVersionNote() {
		return versionNote;
	}

	public void setVersionNote(String versionNote) {
		this.versionNote = versionNote;
	}

	public String getLastChgDate() {
		return lastChgDate;
	}

	public void setLastChgDate(String lastChgDate) {
		this.lastChgDate = lastChgDate;
	}
}
