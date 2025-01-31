package gov.cdc.nedss.srtadmin.dt;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class CodeSetDT extends AbstractVO implements RootDTInterface{
	private static final long serialVersionUID = 1L;
	private String codeSetNm;
	private String assigningAuthorityCd;
	private String assigningAuthorityDescTxt;
	private String codeSetDescTxt;
	private String classCD;
	private java.util.Date effectiveFromTime;
	private java.util.Date effectiveToTime;
	private String isModifiableInd;
	private Integer nbsUid;
	private String sourceVersionTxt;
	private String sourceDomainNm;
	private String statusCd;
	private java.util.Date statusTime;
	private Long codeSetGroupId;
	private String viewLink;
	private String editLink;
    private String ldfPickListInd;
    //private String vadsValueSetCode;
    private String codeSetShortDescTxt;
    private String phinStadValueSetInd;
    private String statusCdDescTxt;
    
    private String aComments;
    private String valueSetNm;
    private String ldfPicklistIndCd;
    private String valueSetCode;
    private String valueSetTypeCd;
    private String valueSetOid;
    private String valueSetStatusCd;
    private String valueSetStatusTime;
    private String parentIsCd;
    private String addTime;
    private Long addUserId;
	
    
    
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
 	
 	
 	
	
    public String getAdminComments() {
		return aComments;
	}
	public void setAdminComments(String adminComments) {
		this.aComments = adminComments;
	}
	public String getValueSetNm() {
		return valueSetNm;
	}
	public void setValueSetNm(String valueSetNm) {
		this.valueSetNm = valueSetNm;
	}
	public String getLdfPicklistIndCd() {
		return ldfPicklistIndCd;
	}
	public void setLdfPicklistIndCd(String ldfPicklistIndCd) {
		this.ldfPicklistIndCd = ldfPicklistIndCd;
	}
	public String getValueSetCode() {
		return valueSetCode;
	}
	public void setValueSetCode(String valueSetCode) {
		this.valueSetCode = valueSetCode;
	}
	public String getValueSetTypeCd() {
		return valueSetTypeCd;
	}
	public void setValueSetTypeCd(String valueSetTypeCd) {
		this.valueSetTypeCd = valueSetTypeCd;
	}
	public String getValueSetOid() {
		return valueSetOid;
	}
	public void setValueSetOid(String valueSetOid) {
		this.valueSetOid = valueSetOid;
	}
	public String getValueSetStatusCd() {
		return valueSetStatusCd;
	}
	public void setValueSetStatusCd(String valueSetStatusCd) {
		this.valueSetStatusCd = valueSetStatusCd;
	}
	public String getValueSetStatusTime() {
		return valueSetStatusTime;
	}
	public void setValueSetStatusTime(String valueSetStatusTime) {
		this.valueSetStatusTime = valueSetStatusTime;
	}
	public String getParentIsCd() {
		return parentIsCd;
	}
	public void setParentIsCd(String parentIsCd) {
		this.parentIsCd = parentIsCd;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
	public String getStatusCdDescTxt() {
		return statusCdDescTxt;
	}
	public void setStatusCdDescTxt(String statusCdDescTxt) {
		this.statusCdDescTxt = statusCdDescTxt;
	}
    public String getPhinStadValueSetInd() {
		return phinStadValueSetInd;
	}
	public void setPhinStadValueSetInd(String phinStadValueSetInd) {
		this.phinStadValueSetInd = phinStadValueSetInd;
	}
	/*public String getVadsValueSetCode() {
		return vadsValueSetCode;
	}

	public void setVadsValueSetCode(String vadsValueSetCode) {
		this.vadsValueSetCode = vadsValueSetCode;
	}*/

	public String getCodeSetShortDescTxt() {
		return codeSetShortDescTxt;
	}

	public void setCodeSetShortDescTxt(String codeSetShortDescTxt) {
		this.codeSetShortDescTxt = codeSetShortDescTxt;
	}

	public String getCodeSetNm() {
		return codeSetNm;
	}

	public void setCodeSetNm(String codeSetNm) {
		this.codeSetNm = codeSetNm;
	}

	public String getAssigningAuthorityCd() {
		return assigningAuthorityCd;
	}

	public void setAssigningAuthorityCd(String assigningAuthorityCd) {
		this.assigningAuthorityCd = assigningAuthorityCd;
	}

	public String getAssigningAuthorityDescTxt() {
		return assigningAuthorityDescTxt;
	}

	public void setAssigningAuthorityDescTxt(String assigningAuthorityDescTxt) {
		this.assigningAuthorityDescTxt = assigningAuthorityDescTxt;
	}

	public String getCodeSetDescTxt() {
		return codeSetDescTxt;
	}

	public void setCodeSetDescTxt(String codeSetDescTxt) {
		this.codeSetDescTxt = codeSetDescTxt;
	}

	public String getClassCD() {
		return classCD;
	}

	public void setClassCD(String classCD) {
		this.classCD = classCD;
	}

	public java.util.Date getEffectiveFromTime() {
		return effectiveFromTime;
	}

	public void setEffectiveFromTime(java.util.Date effectiveFromTime) {
		this.effectiveFromTime = effectiveFromTime;
	}

	public java.util.Date getEffectiveToTime() {
		return effectiveToTime;
	}

	public void setEffectiveToTime(java.util.Date effectiveToTime) {
		this.effectiveToTime = effectiveToTime;
	}

	public String getIsModifiableInd() {
		return isModifiableInd;
	}

	public void setIsModifiableInd(String isModifiableInd) {
		this.isModifiableInd = isModifiableInd;
	}

	

	public String getSourceVersionTxt() {
		return sourceVersionTxt;
	}

	public void setSourceVersionTxt(String sourceVersionTxt) {
		this.sourceVersionTxt = sourceVersionTxt;
	}

	public String getSourceDomainNm() {
		return sourceDomainNm;
	}

	public void setSourceDomainNm(String sourceDomainNm) {
		this.sourceDomainNm = sourceDomainNm;
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
		 return addUserId;
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
		return statusCd;
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
		this.addUserId = addUserId;
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
		this.statusCd = statusCd;
		
	}

	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}

	public String getViewLink() {
		return viewLink;
	}

	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}

	public String getEditLink() {
		return editLink;
	}

	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}

	

	public void setStatusTime(java.util.Date statusTime) {
		this.statusTime = statusTime;
	}

	public Integer getNbsUid() {
		return nbsUid;
	}

	public void setNbsUid(Integer nbsUid) {
		this.nbsUid = nbsUid;
	}

	public Long getCodeSetGroupId() {
		return codeSetGroupId;
	}

	public void setCodeSetGroupId(Long codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
	}

	public String getLdfPickListInd() {
		return ldfPickListInd;
	}

	public void setLdfPickListInd(String ldfPickListInd) {
		this.ldfPickListInd = ldfPickListInd;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CodeSetDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
}