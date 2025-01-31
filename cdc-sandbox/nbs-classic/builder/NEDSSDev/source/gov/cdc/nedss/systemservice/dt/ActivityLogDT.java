package gov.cdc.nedss.systemservice.dt;


import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityLogDT  extends AbstractVO  implements RootDTInterface,  Serializable{
	
	/**
	 * 
	 */

	
	private Long activityLogUid;
	
	private String docType;
	
	private String docName;
	
	private String recordStatusCd;
	
	private Timestamp recordStatusTime;
	
	private String messageTxt = "";
	
	private String messageStatus;
	
	private String actionTxt;
	
	private Long addUserId;
	
	private Timestamp addTime;
	
	private Integer versionCtrlNbr;
	


	public Long getActivityLogUid() {
		return activityLogUid;
	}

	public void setActivityLogUid(Long activityLogUid) {
		this.activityLogUid = activityLogUid;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
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

	public String getMessageTxt() {
		return messageTxt;
	}

	public void setMessageTxt(String messageTxt) {
		this.messageTxt = messageTxt;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getActionTxt() {
		return actionTxt;
	}

	public void setActionTxt(String actionTxt) {
		this.actionTxt = actionTxt;
	}

	public Long getAddUserId() {
		return addUserId;
	}

	@Override
	public void setAddTime(Timestamp addTime) {
		// TODO Auto-generated method stub
		this.addTime = addTime;
	}

	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return addTime;
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
	
	
	

}
