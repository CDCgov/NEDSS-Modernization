package gov.cdc.nedss.webapp.nbs.action.alert.emailAlert.clientVO;

import java.sql.Timestamp;

public class EmailAlertClientVO {
	private String emailId1;
	private String emailId2;
	private String emailId3;
	private long nedssEntryId;
	private Long addUserId;
	private Timestamp addTime;
	
	
	public String getEmailId1() {
		return emailId1;
	}
	public void setEmailId1(String emailId1) {
		this.emailId1 = emailId1;
	}
	public String getEmailId2() {
		return emailId2;
	}
	public void setEmailId2(String emailId2) {
		this.emailId2 = emailId2;
	}
	public String getEmailId3() {
		return emailId3;
	}
	public void setEmailId3(String emailId3) {
		this.emailId3 = emailId3;
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
	public long getNedssEntryId() {
		return nedssEntryId;
	}
	public void setNedssEntryId(long nedssEntryId) {
		this.nedssEntryId = nedssEntryId;
	}

}
