package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.DSMUpdateAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessCaseSummaryDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class NBSDocumentVO extends AbstractVO implements ReportSummaryInterface, RootDTInterface{


/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NBSDocumentDT nbsDocumentDT =  new NBSDocumentDT();
	private EDXActivityLogDT eDXActivityLogDT=new EDXActivityLogDT();
	private ParticipationDT participationDT = new ParticipationDT();
	private PersonVO patientVO= new PersonVO();
	Collection<Object> actRelColl = new ArrayList<Object> (); 
	private boolean isFromSecurityQueue =false; 
	private Boolean isExistingPatient =false; 
	private Boolean isMultiplePatFound =false;
	private boolean conditionFound;
	private String  conditionName;
	private boolean isAssociatedInv=false;
	private String originalPHCRLocalId;
	private Map<String, EDXEventProcessDT> eDXEventProcessDTMap = new HashMap<String, EDXEventProcessDT>();
	private boolean isContactRecordDoc;
	private boolean isLabReportDoc;
	private boolean isCaseReportDoc;
	private boolean isMorbReportDoc;
	private boolean isOngoingCase = true;
	private boolean isDocumentUpdate = false;
	private boolean jurisdictionDerivedFromPreviousDoc = false;
	private ArrayList<Object> assoSummaryCaseList = new ArrayList<Object>();
	private ArrayList<Object> summaryCaseListWithInTimeFrame = new ArrayList<Object>();
	private DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT;
	

	private Map<String, EDXEventProcessCaseSummaryDT> eDXEventProcessCaseSummaryDTMap = new HashMap<String, EDXEventProcessCaseSummaryDT> ();
	
	public Map<String, EDXEventProcessCaseSummaryDT> geteDXEventProcessCaseSummaryDTMap() {
		return eDXEventProcessCaseSummaryDTMap;
	}

	public void seteDXEventProcessCaseSummaryDTMap(
			Map<String, EDXEventProcessCaseSummaryDT> eDXEventProcessCaseSummaryDTMap) {
		this.eDXEventProcessCaseSummaryDTMap = eDXEventProcessCaseSummaryDTMap;
	}

	public boolean isContactRecordDoc() {
		return isContactRecordDoc;
	}

	public void setContactRecordDoc(boolean isContactRecordDoc) {
		this.isContactRecordDoc = isContactRecordDoc;
	}

	public Map<String, EDXEventProcessDT> getEDXEventProcessDTMap() {
		return eDXEventProcessDTMap;
	}

	public void setEDXEventProcessDTMap(
			Map<String, EDXEventProcessDT> eDXEventProcessDTMap) {
		this.eDXEventProcessDTMap = eDXEventProcessDTMap;
	}

	public String getOriginalPHCRLocalId() {
		return originalPHCRLocalId;
	}

	public void setOriginalPHCRLocalId(String originalPHCRLocalId) {
		this.originalPHCRLocalId = originalPHCRLocalId;
	}

	public boolean isAssociatedInv() {
		return isAssociatedInv;
	}

	public void setAssociatedInv(boolean isAssociatedInv) {
		this.isAssociatedInv = isAssociatedInv;
	}

	public String getConditionName() {
		return conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	public boolean isConditionFound() {
		return conditionFound;
	}

	public void setConditionFound(boolean conditionFound) {
		this.conditionFound = conditionFound;
	}

	private String docName;
	public Boolean getIsMultiplePatFound() {
		return isMultiplePatFound;
	}

	public void setIsMultiplePatFound(Boolean isMultiplePatFound) {
		this.isMultiplePatFound = isMultiplePatFound;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Boolean getIsExistingPatient() {
		return isExistingPatient;
	}

	public void setIsExistingPatient(Boolean isExistingPatient) {
		this.isExistingPatient = isExistingPatient;
	}

	private Map<Object, Object> nbsCaseAnswerDTMap;

	public Map<Object, Object> getNbsCaseAnswerDTMap() {
		return nbsCaseAnswerDTMap;
	}

	public void setNbsCaseAnswerDTMap(Map<Object, Object> nbsCaseAnswerDTMap) {
		this.nbsCaseAnswerDTMap = nbsCaseAnswerDTMap;
	}
	
	public DSMUpdateAlgorithmDT getDsmUpdateAlgorithmDT() {
		return dsmUpdateAlgorithmDT;
	}

	public void setDsmUpdateAlgorithmDT(DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT) {
		this.dsmUpdateAlgorithmDT = dsmUpdateAlgorithmDT;
	}

	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public Timestamp getActivityFromTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getIsAssociated() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getIsTouched() {
		// TODO Auto-generated method stub
		return false;
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
		return null;
	}

	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getLastChgUserId() {
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
		// TODO Auto-generated method stub
		
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

	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgUserId(Long lastChgUserId) {
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

	public NBSDocumentDT getNbsDocumentDT() {
		return nbsDocumentDT;
	}

	public void setNbsDocumentDT(NBSDocumentDT nbsDocumentDT) {
		this.nbsDocumentDT = nbsDocumentDT;
	}

	public EDXActivityLogDT getEDXActivityLogDT() {
		return eDXActivityLogDT;
	}

	public void setEDXActivityLogDT(EDXActivityLogDT activityLogDT) {
		eDXActivityLogDT = activityLogDT;
	}

	public ParticipationDT getParticipationDT() {
		return participationDT;
	}

	public void setParticipationDT(ParticipationDT participationDT) {
		this.participationDT = participationDT;
	}

	public PersonVO getPatientVO() {
		return patientVO;
	}

	public void setPatientVO(PersonVO patientVO) {
		this.patientVO = patientVO;
	}

	public Collection<Object> getActRelColl() {
		return actRelColl;
	}

	public void setActRelColl(Collection<Object> actRelColl) {
		this.actRelColl = actRelColl;
	}

	public boolean isFromSecurityQueue() {
		return isFromSecurityQueue;
	}

	public void setFromSecurityQueue(boolean isFromSecurityQueue) {
		this.isFromSecurityQueue = isFromSecurityQueue;
	}

	public boolean isLabReportDoc() {
		return isLabReportDoc;
	}

	public void setLabReportDoc(boolean isLabReportDoc) {
		this.isLabReportDoc = isLabReportDoc;
	}

	public boolean isCaseReportDoc() {
		return isCaseReportDoc;
	}

	public void setCaseReportDoc(boolean isCaseReportDoc) {
		this.isCaseReportDoc = isCaseReportDoc;
	}

	public boolean isOngoingCase() {
		return isOngoingCase;
	}

	public void setOngoingCase(boolean isOngoingCase) {
		this.isOngoingCase = isOngoingCase;
	}

	public boolean isMorbReportDoc() {
		return isMorbReportDoc;
	}

	public void setMorbReportDoc(boolean isMorbReportDoc) {
		this.isMorbReportDoc = isMorbReportDoc;
	}

	public ArrayList<Object> getAssoSummaryCaseList() {
		return assoSummaryCaseList;
	}

	public void setAssoSummaryCaseList(ArrayList<Object> assoSummaryCaseList) {
		this.assoSummaryCaseList = assoSummaryCaseList;
	}

	public ArrayList<Object> getSummaryCaseListWithInTimeFrame() {
		return summaryCaseListWithInTimeFrame;
	}

	public void setSummaryCaseListWithInTimeFrame(ArrayList<Object> summaryCaseListWithInTimeFrame) {
		this.summaryCaseListWithInTimeFrame = summaryCaseListWithInTimeFrame;
	}

	public boolean isDocumentUpdate() {
		return isDocumentUpdate;
	}

	public void setDocumentUpdate(boolean isDocumentUpdate) {
		this.isDocumentUpdate = isDocumentUpdate;
	}

	public boolean isJurisdictionDerivedFromPreviousDoc() {
		return jurisdictionDerivedFromPreviousDoc;
	}

	public void setJurisdictionDerivedFromPreviousDoc(
			boolean jurisdictionDerivedFromPreviousDoc) {
		this.jurisdictionDerivedFromPreviousDoc = jurisdictionDerivedFromPreviousDoc;
	}
}
