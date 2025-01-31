package gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class ExportTriggerDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;
	private Long exportTriggerUid;
	private Long exportAlgorithmUid;
	private Timestamp addTime;
	private Long 	addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private Long 	nbsQuestionUid;
	private String triggerField;
	private String triggerFilter;
	private String triggerLogic;
	private String triggerFieldDesc;
	private String triggerFilterDesc;
	private String triggerLogicDesc;
	private String recordStatusCd;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTriggerField() {
		return triggerField;
	}
	public void setTriggerField(String triggerField) {
		this.triggerField = triggerField;
	}
	public Long getExportTriggerUid() {
		return exportTriggerUid;
	}
	public void setExportTriggerUid(Long exportTriggerUid) {
		this.exportTriggerUid = exportTriggerUid;
	}
	public Long getExportAlgorithmUid() {
		return exportAlgorithmUid;
	}
	public void setExportAlgorithmUid(Long exportAlgorithmUid) {
		this.exportAlgorithmUid = exportAlgorithmUid;
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
	public Long getNbsQuestionUid() {
		return nbsQuestionUid;
	}
	public void setNbsQuestionUid(Long nbsQuestionUid) {
		this.nbsQuestionUid = nbsQuestionUid;
	}
	public String getTriggerFilter() {
		return triggerFilter;
	}
	public void setTriggerFilter(String triggerFilter) {
		this.triggerFilter = triggerFilter;
	}
	public String getTriggerLogic() {
		return triggerLogic;
	}
	public void setTriggerLogic(String triggerLogic) {
		this.triggerLogic = triggerLogic;
	}
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
	public void setLocalId(String localId) {
		// TODO Auto-generated method stub
		
	}
	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
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
	public String getTriggerFieldDesc() {
		return triggerFieldDesc;
	}
	public void setTriggerFieldDesc(String triggerFieldDesc) {
		this.triggerFieldDesc = triggerFieldDesc;
	}
	public String getTriggerFilterDesc() {
		return triggerFilterDesc;
	}
	public void setTriggerFilterDesc(String triggerFilterDesc) {
		this.triggerFilterDesc = triggerFilterDesc;
	}
	public String getTriggerLogicDesc() {
		return triggerLogicDesc;
	}
	public void setTriggerLogicDesc(String triggerLogicDesc) {
		this.triggerLogicDesc = triggerLogicDesc;
	}
	
}
