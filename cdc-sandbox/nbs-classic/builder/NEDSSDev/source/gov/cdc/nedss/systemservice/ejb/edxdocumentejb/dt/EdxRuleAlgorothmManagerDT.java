package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt;


import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessCaseSummaryDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



public class EdxRuleAlgorothmManagerDT implements Serializable {
	/**
	 * Utility class to capture Rule action and behavior at algorithm level
	 * @author Pradeep Kumar Sharma
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String updateAction;
	private String nndComment;
	private String onFailureToCreateNND;
	private String dsmAlgorithmName;
	private String conditionName;

	private Map<Object,Object> edxRuleApplyDTMap;
	private Map<Object,Object> edxRuleAdvCriteriaDTMap;
	private Long dsmAlgorithmUid;
	private String onFailureToCreateInv;
	private String action;
	private Object object;
	private Collection<Object> edxActivityDetailLogDTCollection;
	private String errorText;
	private Collection<Object> sendingFacilityColl; 
	private Map<Object, Object> edxBasicCriteriaMap;
	public  enum STATUS_VAL {Success, Failure};
	private Timestamp lastChgTime;
	private Long PHCUid;
	private Long PHCRevisionUid;
	private NBSDocumentDT documentDT;
	private Long MPRUid;
	private boolean isContactRecordDoc;
	private Map<String, EDXEventProcessCaseSummaryDT> eDXEventProcessCaseSummaryDTMap = new HashMap<String, EDXEventProcessCaseSummaryDT> ();
	private boolean isUpdatedDocument;
	private boolean isLabReportDoc;
	private boolean isMorbReportDoc;
	private boolean isCaseUpdated;
	
	
	public Long getMPRUid() {
		return MPRUid;
	}
	public void setMPRUid(Long mPRUid) {
		MPRUid = mPRUid;
	}
	public boolean isContactRecordDoc() {
		return isContactRecordDoc;
	}
	public void setContactRecordDoc(boolean isContactRecordDoc) {
		this.isContactRecordDoc = isContactRecordDoc;
	}
	public Map<String, EDXEventProcessCaseSummaryDT> geteDXEventProcessCaseSummaryDTMap() {
		return eDXEventProcessCaseSummaryDTMap;
	}
	public void seteDXEventProcessCaseSummaryDTMap(
			Map<String, EDXEventProcessCaseSummaryDT> eDXEventProcessCaseSummaryDTMap) {
		this.eDXEventProcessCaseSummaryDTMap = eDXEventProcessCaseSummaryDTMap;
	}
	public NBSDocumentDT getDocumentDT() {
		return documentDT;
	}
	public void setDocumentDT(NBSDocumentDT documentDT) {
		this.documentDT = documentDT;
	}
	public Long getPHCUid() {
		return PHCUid;
	}
	public void setPHCUid(Long pHCUid) {
		PHCUid = pHCUid;
	}
	public Long getPHCRevisionUid() {
		return PHCRevisionUid;
	}
	public void setPHCRevisionUid(Long pHCRevisionUid) {
		PHCRevisionUid = pHCRevisionUid;
	}

	//private String algorithmAndOrLogic;
	
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	private EDXActivityLogDT edxActivityLogDT	= new EDXActivityLogDT();
	
	public STATUS_VAL status;
	
	public EdxRuleAlgorothmManagerDT() {
		edxActivityLogDT	= new EDXActivityLogDT();
	}
	public Collection<Object> getSendingFacilityColl() {
		return sendingFacilityColl;
	}
	public void setSendingFacilityColl(Collection<Object> sendingFacilityColl) {
		this.sendingFacilityColl = sendingFacilityColl;
	}
	public EDXActivityLogDT getEdxActivityLogDT() {
		return edxActivityLogDT;
	}
	public void setEdxActivityLogDT(EDXActivityLogDT edxActivityLogDT) {
		this.edxActivityLogDT = edxActivityLogDT;
	}
	public STATUS_VAL getStatus() {
		return status;
	}
	public void setStatus(STATUS_VAL status) {
		this.status = status;
	}
	public Map<Object, Object> getEdxBasicCriteriaMap() {
		return edxBasicCriteriaMap;
	}
	public void setEdxBasicCriteriaMap(Map<Object, Object> edxBasicCriteriaMap) {
		this.edxBasicCriteriaMap = edxBasicCriteriaMap;
	}
	public Collection<Object> getEdxActivityDetailLogDTCollection() {
		return edxActivityDetailLogDTCollection;
	}
	public void setEdxActivityDetailLogDTCollection(
			Collection<Object> edxActivityDetailLogDTCollection) {
		this.edxActivityDetailLogDTCollection = edxActivityDetailLogDTCollection;
	}
	public void addEdxActivityDetailLogDTCollection(Object edxActivityDetailLogDT) {
		if (edxActivityDetailLogDTCollection == null) {
			edxActivityDetailLogDTCollection = new ArrayList<Object>();
		}
		edxActivityDetailLogDTCollection.add(edxActivityDetailLogDT);
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	
	public Map<Object, Object> getEdxRuleApplyDTMap() {
		return edxRuleApplyDTMap;
	}
	public void setEdxRuleApplyDTMap(Map<Object, Object> edxRuleApplyDTMap) {
		this.edxRuleApplyDTMap = edxRuleApplyDTMap;
	}
	public Map<Object, Object> getEdxRuleAdvCriteriaDTMap() {
		return edxRuleAdvCriteriaDTMap;
	}
	public void setEdxRuleAdvCriteriaDTMap(
			Map<Object, Object> edxRuleAdvCriteriaDTMap) {
		this.edxRuleAdvCriteriaDTMap = edxRuleAdvCriteriaDTMap;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getOnFailureToCreateInv() {
		return onFailureToCreateInv;
	}
	public void setOnFailureToCreateInv(String onFailureToCreateInv) {
		this.onFailureToCreateInv = onFailureToCreateInv;
	}
	public String getUpdateAction() {
		return updateAction;
	}
	public void setUpdateAction(String updateAction) {
		this.updateAction = updateAction;
	}
	public String getNndComment() {
		return nndComment;
	}
	public void setNndComment(String nndComment) {
		this.nndComment = nndComment;
	}
	public String getOnFailureToCreateNND() {
		return onFailureToCreateNND;
	}
	public void setOnFailureToCreateNND(String onFailureToCreateNND) {
		this.onFailureToCreateNND = onFailureToCreateNND;
	}
	public String getDsmAlgorithmName() {
		return dsmAlgorithmName;
	}
	public void setDsmAlgorithmName(String dsmAlgorithmName) {
		this.dsmAlgorithmName = dsmAlgorithmName;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = EdxRuleAlgorothmManagerDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

	public Long getDsmAlgorithmUid() {
		return dsmAlgorithmUid;
	}
	public void setDsmAlgorithmUid(Long dsmAlgorithmUid) {
		this.dsmAlgorithmUid = dsmAlgorithmUid;
	}
	public String getConditionName() {
		return conditionName;
	}
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	//public String getAlgorithmAndOrLogic() {
	//	return algorithmAndOrLogic;
	//}
	//public void setAlgorithmAndOrLogic(String algorithmAndOrLogic) {
	//	this.algorithmAndOrLogic = algorithmAndOrLogic;
	//}	
	public boolean isUpdatedDocument() {
		return isUpdatedDocument;
	}
	public void setUpdatedDocument(boolean isUpdatedDocument) {
		this.isUpdatedDocument = isUpdatedDocument;
	}
	public boolean isLabReportDoc() {
		return isLabReportDoc;
	}
	public void setLabReportDoc(boolean isLabReportDoc) {
		this.isLabReportDoc = isLabReportDoc;
	}
	public boolean isMorbReportDoc() {
		return isMorbReportDoc;
	}
	public void setMorbReportDoc(boolean isMorbReportDoc) {
		this.isMorbReportDoc = isMorbReportDoc;
	}
	public boolean isCaseUpdated() {
		return isCaseUpdated;
	}
	public void setCaseUpdated(boolean isCaseUpdated) {
		this.isCaseUpdated = isCaseUpdated;
	}
}
