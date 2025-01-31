package gov.cdc.nedss.act.ctcontact.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * CTContactAttachmentDT
 * Description: Copyright: Copyright (c) 2009
 * Company:CSC
 * @author: NBS project team
 * @version 3.1 ContactTracing
 */
public class CTContactNoteDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;

	private Long ctContactNoteUid;
	private Long ctContactUid;
	private String note;
	private Timestamp addTime;
	private Long addUserId;
	private String lastChgUserNm;
	private Timestamp recordStatusTime;
	private String recordStatusCd;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String privateIndCd;

	private boolean itDirty = false;
	private boolean itNew = true;
	private boolean itDelete = false;


	public Long getCtContactNoteUid() {
		return ctContactNoteUid;
	}

	public void setCtContactNoteUid(Long ctContactNoteUid) {
		this.ctContactNoteUid = ctContactNoteUid;
	}

	public Long getCtContactUid() {
		return ctContactUid;
	}

	public void setCtContactUid(Long ctContactUid) {
		this.ctContactUid = ctContactUid;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	
	public String getLastChgUserNm() {
		return lastChgUserNm;
	}

	public void setLastChgUserNm(String lastChgUserNm) {
		this.lastChgUserNm = lastChgUserNm;
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

	public String getPrivateIndCd() {
		return privateIndCd;
	}

	public void setPrivateIndCd(String privateIndCd) {
		this.privateIndCd = privateIndCd;
	}

	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
	}

	public String getRecordStatusCd() {
		return recordStatusCd;
	}

	public void setRecordStatusCd(String code) {
		this.recordStatusCd = code;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CTContactHistDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
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
	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocalId(String localId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}


}