package gov.cdc.nedss.systemservice.dt;


import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EDXActivityLogDT  extends AbstractVO  implements RootDTInterface,  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long edxActivityLogUid;
	private Long sourceUid;
	private Long targetUid;
	private String docType;
	private String recordStatusCd;
	private String recordStatusCdHtml;
	private Timestamp recordStatusTime;
	private String exception;
	private String impExpIndCd;
	private String impExpIndCdDesc;
	private String sourceTypeCd;
	private String targetTypeCd;
	private String businessObjLocalId;
	private String docName;
	private String srcName;
	private String viewLink;
	private String exceptionShort;
	private Collection<Object> EDXActivityLogDTWithVocabDetails;
	private Collection<Object> EDXActivityLogDTWithQuesDetails;
	private Collection<Object> EDXActivityLogDTDetails = new ArrayList();;
	private Map<Object,Object> newaddedCodeSets = new HashMap<Object,Object>();
	private boolean logDetailAllStatus = false;
	private String algorithmAction;
	private String actionId;
	private String messageId;
	private String entityNm;
	private String accnNbr;
	
	

	private String algorithmName;
	
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	
	public String getAlgorithmAction() {
		return algorithmAction;
	}
	public void setAlgorithmAction(String algorithmAction) {
		this.algorithmAction = algorithmAction;
	}
	public String getAlgorithmName() {
		return algorithmName;
	}
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	public EDXActivityLogDT() {
		setLogDetailAllStatus(false);
	}
	public boolean isLogDetailAllStatus() {
		return logDetailAllStatus;
	}
	public void setLogDetailAllStatus(boolean logDetailAllStatus) {
		this.logDetailAllStatus = logDetailAllStatus;
	}
	public Map<Object, Object> getNewaddedCodeSets() {
		return newaddedCodeSets;
	}
	public void setNewaddedCodeSets(Map<Object, Object> newaddedCodeSets) {
		this.newaddedCodeSets = newaddedCodeSets;
	}
	public Collection<Object> getEDXActivityLogDTDetails() {
		return EDXActivityLogDTDetails;
	}
	public void setEDXActivityLogDTDetails(
			Collection<Object> eDXActivityLogDTDetails) {
		EDXActivityLogDTDetails = eDXActivityLogDTDetails;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getSrcName() {
		return srcName;
	}
	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}
		
	public String getViewLink() {
		return viewLink;
	}
	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}	
	public String getExceptionShort() {
		return exceptionShort;
	}
	public void setExceptionShort(String exceptionShort) {
		this.exceptionShort = exceptionShort;
	}
	public Collection<Object> getEDXActivityLogDTWithVocabDetails() {
		return EDXActivityLogDTWithVocabDetails;
	}
	public void setEDXActivityLogDTWithVocabDetails(
			Collection<Object> eDXActivityLogDTWithVocabDetails) {
		EDXActivityLogDTWithVocabDetails = eDXActivityLogDTWithVocabDetails;
	}
	public Collection<Object> getEDXActivityLogDTWithQuesDetails() {
		return EDXActivityLogDTWithQuesDetails;
	}
	public void setEDXActivityLogDTWithQuesDetails(
			Collection<Object> eDXActivityLogDTWithQuesDetails) {
		EDXActivityLogDTWithQuesDetails = eDXActivityLogDTWithQuesDetails;
	}

	private boolean itNew;
	private boolean itDelete;
	private boolean itDirty;
	
	public Long getEdxActivityLogUid() {
		return edxActivityLogUid;
	}
	public void setEdxActivityLogUid(Long edxActivityLogUid) {
		this.edxActivityLogUid = edxActivityLogUid;
	}
	public Long getSourceUid() {
		return sourceUid;
	}
	public void setSourceUid(Long sourceUid) {
		this.sourceUid = sourceUid;
	}
	public Long getTargetUid() {
		return targetUid;
	}
	public void setTargetUid(Long targetUid) {
		this.targetUid = targetUid;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
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
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getImpExpIndCd() {
		return impExpIndCd;
	}
	public void setImpExpIndCd(String impExpIndCd) {
		this.impExpIndCd = impExpIndCd;
	}
	public String getSourceTypeCd() {
		return sourceTypeCd;
	}
	public void setSourceTypeCd(String sourceTypeCd) {
		this.sourceTypeCd = sourceTypeCd;
	}
	public String getTargetTypeCd() {
		return targetTypeCd;
	}
	public void setTargetTypeCd(String targetTypeCd) {
		this.targetTypeCd = targetTypeCd;
	}
	public String getBusinessObjLocalId() {
		return businessObjLocalId;
	}
	public void setBusinessObjLocalId(String businessObjLocalId) {
		this.businessObjLocalId = businessObjLocalId;
	}
	
	public String getImpExpIndCdDesc() {
		return impExpIndCdDesc;
	}
	public void setImpExpIndCdDesc(String impExpIndCdDesc) {
		this.impExpIndCdDesc = impExpIndCdDesc;
	}
	public boolean isItNew() {
		return itNew;
	}
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}
	public boolean isItDelete() {
		return itDelete;
	}
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}
	public boolean isItDirty() {
		return itDirty;
	}
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}
	public String getRecordStatusCdHtml() {
		return recordStatusCdHtml;
	}
	public void setRecordStatusCdHtml(String recordStatusCdHtml) {
		this.recordStatusCdHtml = recordStatusCdHtml;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getEntityNm() {
		return entityNm;
	}
	public void setEntityNm(String entityNm) {
		this.entityNm = entityNm;
	}
	public String getAccessionNbr() {
		return accnNbr;
	}
	public void setAccessionNbr(String accessionNbr) {
		this.accnNbr = accessionNbr;
	}
	
	
	
	

}
