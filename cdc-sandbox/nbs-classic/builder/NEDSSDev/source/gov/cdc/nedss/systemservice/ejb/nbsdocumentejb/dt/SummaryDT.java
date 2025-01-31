package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt;

import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

public class SummaryDT extends AbstractVO implements ReportSummaryInterface,RootDTInterface{
	private static final long serialVersionUID = 1L;
	private Long nbsDocumentUid;
	private String docPayload;
	private String docPayloadDerived;
	private String payloadViewIndCd;
	private String docType;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private Long addUserID;
	private String txt;
	private Long MPRUid;
	private String personLocalId;
	private String jurisdiction;
	private String jurisdictionCd;
	private String programArea;
	private String type;
	private Timestamp dateReceived;
	private String localId;
	private String cd;
	private String cdDescTxt;
	private String firstName;
	private String lastName;
	private Timestamp addTime;
	private Timestamp lastChgTime;
	private String docPurposeCd;
	private String docStatusCd;
	private String docTypeConditionDescTxt;
	private String docPurposeCdConditionDescTxt;
	private String docStatusCdConditionDescTxt;
	private boolean isTouched;
	private boolean isAssociated;
    private String checkBoxId;
    private Map<Object,Object> associationMap;  
	private String sendingFacilityNm;
	private Integer externalVersionCtrlNbr;
	private String currSexCd;
	private Timestamp birthTime;
	private String docTypeCd;
	private Long nbsDocumentMetadataUid;
	private String eventType;
	private String docEventTypeCd;
	private String providerFirstName;
	private String providerLastName;
	private String providerPrefix;
	private String providerSuffix;
	private String providerDegree;
	private String providerUid;
	private String reportingFacility;	
	private String description;
	private String electronicInd;
	private String sharedInd;
	Collection<Object> invSummaryVOs;
	private String processingDecisionCd;
	private String progAreaCd;

	
	
	
	public String getProgAreaCd() {
		return progAreaCd;
	}


	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}


	public String getProcessingDecisionCd() {
		return processingDecisionCd;
	}

	
	public void setProcessingDecisionCd(String processingDecisionCd) {
		this.processingDecisionCd = processingDecisionCd;
	}
	public String getDocTypeCd() {
		return docTypeCd;
	}
	public void setDocTypeCd(String docTypeCd) {
		this.docTypeCd = docTypeCd;
	}
	public Long getNbsDocumentMetadataUid() {
		return nbsDocumentMetadataUid;
	}
	public void setNbsDocumentMetadataUid(Long nbsDocumentMetadataUid) {
		this.nbsDocumentMetadataUid = nbsDocumentMetadataUid;
	}
	public Integer getExternalVersionCtrlNbr() {
		return externalVersionCtrlNbr;
	}
	public void setExternalVersionCtrlNbr(Integer externalVersionCtrlNbr) {
		this.externalVersionCtrlNbr = externalVersionCtrlNbr;
	}
	
	public Map<Object, Object> getAssociationMap() {
		return associationMap;
	}

	public void setAssociationMap(Map<Object, Object> associationMap) {
		this.associationMap = associationMap;
	}

	private String actionLink;



	public String getSendingFacilityNm() {
		return sendingFacilityNm;
	}

	public void setSendingFacilityNm(String sendingFacilityNm) {
		this.sendingFacilityNm = sendingFacilityNm;
	}

	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public Timestamp getActivityFromTime() {
		// TODO Auto-generated method stub
		return null;
	}


	public Long getObservationUid() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setActivityFromTime(Timestamp activityFromTime) {
		// TODO Auto-generated method stub

	}

	public void setItAssociated(boolean associated) {
		// TODO Auto-generated method stub

	}

	public void setItTouched(boolean touched) {
		// TODO Auto-generated method stub

	}

	public void setObservationUid(Long observationUid) {
		// TODO Auto-generated method stub

	}

	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return addTime;
	}

	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalId (){
		return localId;
	}


	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return recordStatusCd;
	}

	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return recordStatusTime;
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

	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;

	}

	public void setAddUserId(Long addUserId) {
		// TODO Auto-generated method stub

	}

	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub

	}

	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub

	}

	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub

	}

	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub

	}

	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;

	}

	public void setLastChgUserId(Long lastChgUserId) {
		// TODO Auto-generated method stub

	}

	public void setLocalId (String aLocalId){
		localId = aLocalId;
	}

	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub

	}

	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		this.recordStatusCd = aRecordStatusCd;

	}

	public void setRecordStatusTime(java.sql.Timestamp recordStatusTime) {
		// TODO Auto-generated method stub
		this.recordStatusTime = recordStatusTime;
	}

	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub

	}

	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub

	}

	public Long getNbsDocumentUid() {
		return nbsDocumentUid;
	}

	public void setNbsDocumentUid(Long nbsDocumentUid) {
		this.nbsDocumentUid = nbsDocumentUid;
	}

	public String getDocPayload() {
		return docPayload;
	}

	public void setDocPayload(String docPayload) {
		this.docPayload = docPayload;
	}

	public String getDocPayloadDerived() {
		return docPayloadDerived;
	}

	public void setDocPayloadDerived(String docPayloadDerived) {
		this.docPayloadDerived = docPayloadDerived;
	}

	public String getPayloadViewIndCd() {
		return payloadViewIndCd;
	}

	public void setPayloadViewIndCd(String payloadViewIndCd) {
		this.payloadViewIndCd = payloadViewIndCd;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Long getAddUserID() {
		return addUserID;
	}

	public void setAddUserID(Long addUserID) {
		this.addUserID = addUserID;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Long getMPRUid() {
		return MPRUid;
	}

	public void setMPRUid(Long uid) {
		MPRUid = uid;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getProgramArea() {
		return programArea;
	}

	public void setProgramArea(String programArea) {
		this.programArea = programArea;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Timestamp dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getCdDescTxt() {
		return cdDescTxt;
	}

	public void setCdDescTxt(String cdDescTxt) {
		this.cdDescTxt = cdDescTxt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDocPurposeCd() {
		return docPurposeCd;
	}

	public void setDocPurposeCd(String docPurposeCd) {
		this.docPurposeCd = docPurposeCd;
	}

	public String getDocStatusCd() {
		return docStatusCd;
	}

	public void setDocStatusCd(String docStatusCd) {
		this.docStatusCd = docStatusCd;
	}

	public String getActionLink() {
		return actionLink;
	}

	public void setActionLink(String actionLink) {
		this.actionLink = actionLink;
	}

	public String getDocTypeConditionDescTxt() {
		return docTypeConditionDescTxt;
	}

	public void setDocTypeConditionDescTxt(String docTypeConditionDescTxt) {
		this.docTypeConditionDescTxt = docTypeConditionDescTxt;
	}

	public String getDocPurposeCdConditionDescTxt() {
		return docPurposeCdConditionDescTxt;
	}

	public void setDocPurposeCdConditionDescTxt(String docPurposeCdConditionDescTxt) {
		this.docPurposeCdConditionDescTxt = docPurposeCdConditionDescTxt;
	}

	public String getDocStatusCdConditionDescTxt() {
		return docStatusCdConditionDescTxt;
	}

	public void setDocStatusCdConditionDescTxt(String docStatusCdConditionDescTxt) {
		this.docStatusCdConditionDescTxt = docStatusCdConditionDescTxt;
	}

	public boolean getIsTouched()
	{
		return isTouched;
	}

	public void setIsTouched(boolean aIsTouched)
	{
		isTouched = aIsTouched;
	}

	public boolean getIsAssociated()
	{
		return isAssociated;
	}

	public void setIsAssociated(boolean aIsAssociated)
	{
		isAssociated = aIsAssociated;
	}
	
	public String getCheckBoxId() {
		if(isAssociated) {
			checkBoxId = "checked=\"true\"";
		}
		return checkBoxId;
	}

	public void setCheckBoxId(String checkBoxId) {
		this.checkBoxId = checkBoxId;
	}
	public String getLocalIdForUpdatedAndNewDoc(boolean displayNew){
		if(getExternalVersionCtrlNbr()!=null && getExternalVersionCtrlNbr().intValue()>1)
			return "<font color=\"#006600\">"+this.getLocalId()+" (Update)</font>";
		else if(displayNew)
			return this.getLocalId()+" (New)";
		else
			return this.getLocalId();
		
	}
	public String getLocalIdForUpdatedAndNewDocPrint(boolean displayNew){
		if(getExternalVersionCtrlNbr()!=null && getExternalVersionCtrlNbr().intValue()>1)
			return this.getLocalId()+" (Update)";
		else if(displayNew)
			return this.getLocalId()+" (New)";
		else
			return this.getLocalId();
		
	}
	
	public String getLocalIdForUpdatedAndNewDoc(){
		return getLocalIdForUpdatedAndNewDoc(false);
	}
	public String getLocalIdForUpdatedAndNewDocPrint(){
		return getLocalIdForUpdatedAndNewDocPrint(false);
	}
	/**
	 * @return the currSexCd
	 */
	public String getCurrSexCd() {
		return currSexCd;
	}
	/**
	 * @param currSexCd the currSexCd to set
	 */
	public void setCurrSexCd(String currSexCd) {
		this.currSexCd = currSexCd;
	}
	/**
	 * @return the birthTime
	 */
	public Timestamp getBirthTime() {
		return birthTime;
	}
	/**
	 * @param birthTime the birthTime to set
	 */
	public void setBirthTime(Timestamp birthTime) {
		this.birthTime = birthTime;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getDocEventTypeCd() {
		return docEventTypeCd;
	}
	public void setDocEventTypeCd(String docEventTypeCd) {
		this.docEventTypeCd = docEventTypeCd;
	}


	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProviderFirstName() {
		return providerFirstName;
	}
	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}
	public String getProviderLastName() {
		return providerLastName;
	}
	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}
	public String getProviderPrefix() {
		return providerPrefix;
	}
	public void setProviderPrefix(String providerPreffix) {
		this.providerPrefix = providerPreffix;
	}
	public String getProviderSuffix() {
		return providerSuffix;
	}
	public void setProviderSuffix(String providerSuffix) {
		this.providerSuffix = providerSuffix;
	}
	public String getProviderDegree() {
		return providerDegree;
	}
	public void setProviderDegree(String providerDegree) {
		this.providerDegree = providerDegree;
	}
	public String getProviderUid() {
		return providerUid;
	}
	public void setProviderUid(String providerUid) {
		this.providerUid = providerUid;
	}
	public String getReportingFacility() {
		return reportingFacility;
	}
	public void setReportingFacility(String reportingFacility) {
		this.reportingFacility = reportingFacility;
	}
	public String getElectronicInd() {
		return electronicInd;
	}
	public void setElectronicInd(String electronicInd) {
		this.electronicInd = electronicInd;
	}
	public Collection<Object> getInvSummaryVOs() {
		return invSummaryVOs;
	}
	public void setInvSummaryVOs(Collection<Object> invSummaryVOs) {
		this.invSummaryVOs = invSummaryVOs;
	}
	public String getPersonLocalId() {
		return personLocalId;
	}
	public void setPersonLocalId(String personLocalId) {
		this.personLocalId = personLocalId;
	}
	public String getSharedInd() {
		return sharedInd;
	}
	public void setSharedInd(String sharedInd) {
		this.sharedInd = sharedInd;
	}
	public String getJurisdictionCd() {
		return jurisdictionCd;
	}
	public void setJurisdictionCd(String jurisdictionCd) {
		this.jurisdictionCd = jurisdictionCd;
	}
}
