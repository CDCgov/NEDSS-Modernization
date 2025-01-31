package gov.cdc.nedss.alert.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class UserEmailDT  extends AbstractVO implements RootDTInterface{
	/**
	 * Title: UserEmailDT class. 
	 * Description: Provides data transfer Object for string alert information in User_Email table
	 * Copyright: Copyright (c) 2008 Company: CSC
	 *
	 * @author Sita Siram
	 * @version
	 */
	private static final long serialVersionUID = 1L;
	private Long userEmailUid;
	private Integer seqNbr;
	private Long nedssEntryId;
	private String emailId;

	private Timestamp addTime;
	private Timestamp lastChgTime;
	private String recordStatusCd;
	private Long addUserId;
	private Long lastChgUserId;
	public Long getUserEmailUid() {
		return userEmailUid;
	}
	public void setUserEmailUid(Long userEmailUid) {
		this.userEmailUid = userEmailUid;
	}
	public Integer getSeqNbr() {
		return seqNbr;
	}
	public void setSeqNbr(Integer seqNbr) {
		this.seqNbr = seqNbr;
	}
	public Long getNedssEntryId() {
		return nedssEntryId;
	}
	public void setNedssEntryId(Long nedssEntryId) {
		this.nedssEntryId = nedssEntryId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}
	public Long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	public Long getLastChgUserId() {
		return lastChgUserId;
	}
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
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
	
	public String stringValue() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + ":");
			Field[] f = UserEmailDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "  :::: ");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
}