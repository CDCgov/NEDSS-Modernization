package gov.cdc.nedss.systemservice.dt;


import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.io.Serializable;
import java.sql.Timestamp;

public class EDXActivityDetailLogDT extends AbstractVO  implements RootDTInterface,  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long edxActivityLogUid;	
	private String recordId;
	private String recordType;	
	private String recordName;
	private String logType;
	private String comment;
	private String logTypeHtml;
	private String commentHtml;
	
    private boolean itNew;
	private boolean itDelete;
	private boolean itDirty;
	private Long lastChgUserId;	
	private Timestamp lastChgTime;
	private Long addUserId;	
	private Timestamp addTime;
	private Integer publishVersionNbr;
	
	public EDXActivityDetailLogDT(){
		
	}
	
	public EDXActivityDetailLogDT(String recordId, String recordType, String recordName,String logType, String comment,
		Long lastChgUserId, Timestamp lastChgTime, Long addUserId, Timestamp addTime,Integer publishVersionNbr){
		this.itNew=true;
		this.recordId=recordId;
		this.recordType=recordType;
		this.recordName=recordName;
		this.logType=logType;
		this.comment=comment;
		this.lastChgUserId=lastChgUserId;
		this.addUserId=addUserId;
		this.addTime=addTime;
		this.publishVersionNbr=publishVersionNbr;
		
	}
	public Long getEdxActivityLogUid() {
		return edxActivityLogUid;
	}
	public void setEdxActivityLogUid(Long edxActivityLogUid) {
		this.edxActivityLogUid = edxActivityLogUid;
	}
	
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}
	
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	
	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	
	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}
	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Integer getPublishVersionNbr() {
		return publishVersionNbr;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
	public Timestamp getRecordStatusTime() {
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
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
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

	public String getLogTypeHtml() {
		return logTypeHtml;
	}

	public void setLogTypeHtml(String logTypeHtml) {
		this.logTypeHtml = logTypeHtml;
	}

	public String getCommentHtml() {
		return commentHtml;
	}

	public void setCommentHtml(String commentHtml) {
		this.commentHtml = commentHtml;
	}


	
	
	

}

