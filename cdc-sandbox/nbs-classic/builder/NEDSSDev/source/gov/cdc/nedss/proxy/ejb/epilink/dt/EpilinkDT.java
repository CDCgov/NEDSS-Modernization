package gov.cdc.nedss.proxy.ejb.epilink.dt;



import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EpilinkDT extends AbstractVO implements RootDTInterface{

	private static final long serialVersionUID = 1L;

	
	
	private Long activityLogUid;
	
	private Date processedDate;
	
	private String firstName;
	
	private String lastName;
	
	private String oldEpilinkId;
	
	private String newEpilinkId;
	
	private Timestamp recordStatusTime;
	
	private String docType;
	
	private String docName;
	
	private String recordStatusCd;
	
	private String actionTxt;
	
	private String sourceTypeCd;
	
	private String targetTypeCd;
	
	private Long addUserId;
	
	private String name;
	
	private Integer versionCtrlNbr;
	
	private String investigationsString = "";
	
	
	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
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

	public String getOldEpilinkId() {
		return oldEpilinkId;
	}

	public void setOldEpilinkId(String oldEpilinkId) {
		this.oldEpilinkId = oldEpilinkId;
	}

	public String getNewEpilinkId() {
		return newEpilinkId;
	}

	
	public void setNewEpilinkId(String newEpilinkId) {
		this.newEpilinkId = newEpilinkId;
	}


	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Long getActivityLogUid() {
		return activityLogUid;
	}

	public void setActivityLogUid(Long activityLogUid) {
		this.activityLogUid = activityLogUid;
	}

	public String getName() {
		return firstName + " " + lastName;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public String getInvestigationsString() {
	
		return investigationsString;
	}

	public void setInvestigationsString(String investigationsString) {
		this.investigationsString = investigationsString;
	}

	

	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
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

	public String getActionTxt() {
		return actionTxt;
	}

	public void setActionTxt(String actionTxt) {
		this.actionTxt = actionTxt;
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


	public Long getAddUserId() {
		return addUserId;
	}

	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return itNew;
	}

	@Override
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
		
	}

	@Override
	public boolean isItDirty() {
		return itDirty;
	}

	@Override
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
		
	}

	@Override
	public boolean isItDelete() {
		return itDelete;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		this.itDelete= itDelete;
		
	}

	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return versionCtrlNbr;
	}

	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return activityLogUid;
	}
	
	@Override
	public String toString() {
		return "EpilinkDT [activityLogUid=" + activityLogUid
				+ ", processedDate=" + processedDate + ", firstName="
				+ firstName + ", lastName=" + lastName + ", oldEpilinkId="
				+ oldEpilinkId + ", newEpilinkId=" + newEpilinkId
				+ ", recordStatusTime=" + recordStatusTime + ", docType="
				+ docType + ", docName=" + docName + ", recordStatusCd="
				+ recordStatusCd + ", actionTxt=" + actionTxt
				+ ", sourceTypeCd=" + sourceTypeCd + ", targetTypeCd="
				+ targetTypeCd + ", addUserId=" + addUserId + ", name=" + name
				+ ", investigationsString=" + investigationsString + "]";
	}


}
