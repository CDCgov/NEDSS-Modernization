package gov.cdc.nedss.page.ejb.pageproxyejb.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class NBSNoteDT extends AbstractVO implements RootDTInterface{
	
	private Long nbsNoteUid;
	private Long noteParentUid;
	private Timestamp addTime;
	private Long addUserId;
	private String recordStatusCode;
	private Timestamp recordStatusTime;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String note;
	private String privateIndCd;
	private String typeCd;
	private String lastChgUserNm;

    
	


	public Long getNbsNoteUid() {
		return nbsNoteUid;
	}

	public void setNbsNoteUid(Long nbsNoteUid) {
		this.nbsNoteUid = nbsNoteUid;
	}

	public Long getNoteParentUid() {
		return noteParentUid;
	}

	public void setNoteParentUid(Long noteParentUid) {
		this.noteParentUid = noteParentUid;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Timestamp getAddTime() {		
		return addTime;
	}

	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return addUserId;
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
		return lastChgTime;
	}

	@Override
	public Long getLastChgUserId() {		
		return lastChgUserId;
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
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return recordStatusCode;
	}

	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return recordStatusTime;
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
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAddTime(Timestamp aAddTime) {
		this.addTime = aAddTime;
		
	}

	@Override
	public void setAddUserId(Long aAddUserId) {
		this.addUserId = aAddUserId;
		
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItNew(boolean itNew) {
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
		this.lastChgTime = aLastChgTime;
		
	}

	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		this.lastChgUserId = aLastChgUserId;
		
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
	public void setRecordStatusCd(String aRecordStatusCd) {
		this.recordStatusCode = aRecordStatusCd;
		
	}

	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		this.recordStatusTime = aRecordStatusTime;
		
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

	public String getRecordStatusCode() {
		return recordStatusCode;
	}

	public void setRecordStatusCode(String recordStatusCode) {
		this.recordStatusCode = recordStatusCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPrivateIndCd() {
		return privateIndCd;
	}

	public void setPrivateIndCd(String privateIndCd) {
		this.privateIndCd = privateIndCd;
	}

	public String getTypeCd() {
		return typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public String getLastChgUserNm() {
		return lastChgUserNm;
	}

	public void setLastChgUserNm(String lastChgUserNm) {
		this.lastChgUserNm = lastChgUserNm;
	}


	
	

}
