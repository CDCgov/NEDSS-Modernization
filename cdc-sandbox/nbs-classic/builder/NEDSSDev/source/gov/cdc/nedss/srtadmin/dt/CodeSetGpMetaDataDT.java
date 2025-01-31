package gov.cdc.nedss.srtadmin.dt;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class CodeSetGpMetaDataDT extends AbstractVO implements RootDTInterface{
	private static final long serialVersionUID = 1L;
	private String codeSetNm;
	private Long codeSetGroupId;
	private String vads_value_set_code;
	private String codeSetDescTxt;
	private String codeSetShortDescTxt;
	private String ldfPicklistIndCd;
	private String phinStadValueSetInd;
	
	
	
	
	
 // added by jayasudha
	
	

 	private String longDisplayName	;
 	private String shortDisplayName;
 	private java.util.Date localEffectiveFromTime	;
 	private java.util.Date localEffectiveToTime	;
 	private String statusCode;
 	private String admComments	;
 	
 	
 	
 	private String localCode	;
 	public String getLocalCode() {
 		return localCode;
 	}
 	public void setLocalCode(String localCode) {
 		this.localCode = localCode;
 	}
 	public String getLongDisplayName() {
 		return longDisplayName;
 	}
 	public void setLongDisplayName(String longDisplayName) {
 		this.longDisplayName = longDisplayName;
 	}
 	public String getShortDisplayName() {
 		return shortDisplayName;
 	}
 	public void setShortDisplayName(String shortDisplayName) {
 		this.shortDisplayName = shortDisplayName;
 	}
 	public java.util.Date getLocalEffectiveFromTime() {
 		return localEffectiveFromTime;
 	}
 	public void setLocalEffectiveFromTime(java.util.Date localEffectiveFromTime) {
 		this.localEffectiveFromTime = localEffectiveFromTime;
 	}
 	public java.util.Date getLocalEffectiveToTime() {
 		return localEffectiveToTime;
 	}
 	public void setLocalEffectiveToTime(java.util.Date localEffectiveToTime) {
 		this.localEffectiveToTime = localEffectiveToTime;
 	}
 	public String getStatusCode() {
 		return statusCode;
 	}
 	public void setStatusCode(String statusCode) {
 		this.statusCode = statusCode;
 	}
 	public String getAdministrativeComments() {
 		return admComments;
 	}
 	public void setAdministrativeComments(String administrativeComments) {
 		this.admComments = administrativeComments;
 	}
 	
	
	public String getPhinStadValueSetInd() {
		return phinStadValueSetInd;
	}
	public void setPhinStadValueSetInd(String phinStadValueSetInd) {
		this.phinStadValueSetInd = phinStadValueSetInd;
	}
	public String getCodeSetDescTxt() {
		return codeSetDescTxt;
	}
	public void setCodeSetDescTxt(String codeSetDescTxt) {
		this.codeSetDescTxt = codeSetDescTxt;
	}
	public String getCodeSetShortDescTxt() {
		return codeSetShortDescTxt;
	}
	public void setCodeSetShortDescTxt(String codeSetShortDescTxt) {
		this.codeSetShortDescTxt = codeSetShortDescTxt;
	}
	public String getLdfPicklistIndCd() {
		return ldfPicklistIndCd;
	}
	public void setLdfPicklistIndCd(String ldfPicklistIndCd) {
		this.ldfPicklistIndCd = ldfPicklistIndCd;
	}
	public String getCodeSetNm() {
		return codeSetNm;
	}
	public void setCodeSetNm(String codeSetNm) {
		this.codeSetNm = codeSetNm;
	}
	
	public String getVads_value_set_code() {
		return vads_value_set_code;
	}
	public void setVads_value_set_code(String vads_value_set_code) {
		this.vads_value_set_code = vads_value_set_code;
	}
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
	public Long getCodeSetGroupId() {
		return codeSetGroupId;
	}
	public void setCodeSetGroupId(Long codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CodeSetGpMetaDataDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	
}