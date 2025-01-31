package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class NBSDocumentDT extends AbstractVO implements RootDTInterface {
  
	private static final long serialVersionUID = 1L;
	private Long nbsquestionuid;
	private String invFormCode;
	private String questionIdentifier;
	private String questionLabel;
	private String codeSetName;
	private String dataType;
	private Long nbsDocumentUid;
	private Blob docPayload;
	private Blob phdcDocDerived;
	private String payloadViewIndCd;
	private String docTypeCd;
	private Long nbsDocumentMetadataUid;
	private String localId;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private Long addUserId;
	private Timestamp addTime;
	private String progAreaCd;
	private String jurisdictionCd;
	private String txt;
	private Long programJurisdictionOid;
	private String sharedInd;
	private Integer versionCtrlNbr;
	private String cd;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String docPurposeCd;
	private String docStatusCd;
	private String payLoadTxt;
	private String phdcDocDerivedTxt;
	private String cdDescTxt; 
	private String sendingFacilityNm;
	private String sendingFacilityOID;
	private Long nbsInterfaceUid;
	private String sendingAppPatientId;
	private String sendingAppEventId;
	private boolean itDirty = false;
	private boolean itNew = true;
	private boolean itDelete = false;
	private String superclass; 
	private String xmldocPayload;
	private Integer externalVersionCtrlNbr;
	private Map<Object, Object> eventIdMap = new HashMap<Object, Object>();
	private Object documentObject;
	private String docEventTypeCd;
	private String processingDecisionCd;
	private String processingDecisiontxt;
	private Timestamp effectiveTime;
	
	public Timestamp getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Timestamp effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public Object getDocumentObject() {
		return documentObject;
	}
	public void setDocumentObject(Object documentObject) {
		this.documentObject = documentObject;
	}
	public Map<Object, Object> getEventIdMap() {
		return eventIdMap;
	}
	public void setEventIdMap(Map<Object, Object> eventIdMap) {
		this.eventIdMap = eventIdMap;
	}
	public Integer getExternalVersionCtrlNbr() {
		return externalVersionCtrlNbr;
	}
	public void setExternalVersionCtrlNbr(Integer externalVersionCtrlNbr) {
		this.externalVersionCtrlNbr = externalVersionCtrlNbr;
	}
	public String getXmldocPayload() {
		return xmldocPayload;
	}
	public void setXmldocPayload(String xmldocPayload) {
		this.xmldocPayload = xmldocPayload;
	}
	public Long getNbsInterfaceUid() {
		return nbsInterfaceUid;
	}
	// this is for the RootDTInterface
	public Long getUid(){
		return getNbsDocumentUid();
	  }
	public void setNbsInterfaceUid(Long nbsInterfaceUid) {
		this.nbsInterfaceUid = nbsInterfaceUid;
	}
	public String getSendingAppPatientId() {
		return sendingAppPatientId;
	}
	public void setSendingAppPatientId(String sendingAppPatientId) {
		this.sendingAppPatientId = sendingAppPatientId;
	}
	public Long getNbsquestionuid() {
		return nbsquestionuid;
	}
	public void setNbsquestionuid(Long nbsquestionuid) {
		this.nbsquestionuid = nbsquestionuid;
	}
	public String getInvFormCode() {
		return invFormCode;
	}
	public void setInvFormCode(String invFormCode) {
		this.invFormCode = invFormCode;
	}
	public String getQuestionIdentifier() {
		return questionIdentifier;
	}
	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}
	public String getQuestionLabel() {
		return questionLabel;
	}
	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}
	public String getCodeSetName() {
		return codeSetName;
	}
	public void setCodeSetName(String codeSetName) {
		this.codeSetName = codeSetName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Long getNbsDocumentUid() {
		return nbsDocumentUid;
	}
	public void setNbsDocumentUid(Long nbsDocumentUid) {
		this.nbsDocumentUid = nbsDocumentUid;
	}
	public Blob getDocPayload() {
		return docPayload;
	}
	public void setDocPayload(Blob docPayload) {
		this.docPayload = docPayload;
	}
	public void setPhdcDocDerived(Blob phdcDocDerived) {
		this.phdcDocDerived = phdcDocDerived;
	}
	public Blob getPhdcDocDerived() {
		return phdcDocDerived;
	}
	public void setPayloadViewIndCd(String payloadViewIndCd) {
		this.payloadViewIndCd = payloadViewIndCd;
	}
	public String getPayloadViewIndCd() {
		return payloadViewIndCd;
	}
	public String getDocTypeCd() {
		return docTypeCd;
	}
	public void setDocTypeCd(String docTypeCd) {
		this.docTypeCd = docTypeCd;
	}
	public void setNbsDocumentMetadataUid(Long nbsDocumentMetadataUid) {
		this.nbsDocumentMetadataUid = nbsDocumentMetadataUid;
	}
	public Long getNbsDocumentMetadataUid() {
		return nbsDocumentMetadataUid;
	}
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}
	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
	}
	public Long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public String getProgAreaCd() {
		return progAreaCd;
	}
	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}
	public String getJurisdictionCd() {
		return jurisdictionCd;
	}
	public void setJurisdictionCd(String jurisdictionCd) {
		this.jurisdictionCd = jurisdictionCd;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public Long getProgramJurisdictionOid() {
		return programJurisdictionOid;
	}
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		this.programJurisdictionOid = programJurisdictionOid;
	}
	public String getSharedInd() {
		return sharedInd;
	}
	public void setSharedInd(String sharedInd) {
		this.sharedInd = sharedInd;
	}
	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}
	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
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
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	public String getLastChgReasonCd() {
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
		return this.superclass;
	}
	public void setSuperclass(String superclass) {
		// TODO Auto-generated method stub
		  this.superclass = superclass;
	}
	
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return itDelete;
	}
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return itDirty;
	}
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return itNew;
	}
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
		
	}
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
		
	}
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
		
	}
	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	public void setStatusCd(String statusCd) {
	
		
	}
	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}
	public String getPayLoadTxt() {
		return payLoadTxt;
	}
	public void setPayLoadTxt(String payLoadTxt) {
		this.payLoadTxt = payLoadTxt;
	}
	public String getPhdcDocDerivedTxt() {
		return phdcDocDerivedTxt;
	}
	public void setPhdcDocDerivedTxt(String phdcDocDerivedTxt) {
		this.phdcDocDerivedTxt = phdcDocDerivedTxt;
	}
	public String getCdDescTxt() {
		return cdDescTxt;
	}
	public void setCdDescTxt(String cdDescTxt) {
		this.cdDescTxt = cdDescTxt;
	}
	public String getSendingFacilityNm() {
		return sendingFacilityNm;
	}
	public void setSendingFacilityNm(String sendingFacilityNm) {
		this.sendingFacilityNm = sendingFacilityNm;
	}
	public String getSendingAppEventId() {
		return sendingAppEventId;
	}
	public void setSendingAppEventId(String sendingAppEventId) {
		this.sendingAppEventId = sendingAppEventId;
	}
	public String getDocEventTypeCd() {
		return docEventTypeCd;
	}
	public void setDocEventTypeCd(String docEventTypeCd) {
		this.docEventTypeCd = docEventTypeCd;
	}
	public String getSendingFacilityOID() {
		return sendingFacilityOID;
	}
	public void setSendingFacilityOID(String sendingFacilityOID) {
		this.sendingFacilityOID = sendingFacilityOID;
	}
	public String getProcessingDecisionCd() {
		return processingDecisionCd;
	}
	public void setProcessingDecisionCd(String processingDecisionCd) {
		this.processingDecisionCd = processingDecisionCd;
	}
	public String getProcessingDecisiontxt() {
		return processingDecisiontxt;
	}
	public void setProcessingDecisiontxt(String processingDecisiontxt) {
		this.processingDecisiontxt = processingDecisiontxt;
	}
	
	 	 
}
