package gov.cdc.nedss.srtadmin.dt;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.StringUtils;
import org.apache.log4j.Logger;

public class CodeValueGeneralDT extends AbstractVO implements RootDTInterface{
	private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(CodeValueGeneralDT.class);
	private String codeSetNm;
	
	private String code;;
	private String codeDescTxt;
	private String codeShortDescTxt;
	private String codeSystemCd;
	private String codeSystemDescTxt;
	private java.util.Date effectiveFromTime;
	private java.util.Date effectiveToTime;
	private Integer indentLevelNbr;
	private String isModifiableInd;
	private String parentIsCd;
	private Long nbsUid;
	private String sourceConceptId;
	private String superCodeSetNm;
	private String superCode;
	private String statusCd;
	private String statusTime;
	private String codeTmp;
	private String viewLink;
	private String editLink;
	private String conceptTypeCd;
	private String conceptCode;
	private String statusCdDescTxt;
	private String valueSetOid;
	
	private String conceptNm;
	private String conceptPreferredNm;
	private String conceptStatusCd;
	private java.util.Date conceptStatusTime;
	private String codeSystemVersionNbr;
	private String conceptOrderNbr;
	private String adminComments;
	private java.util.Date addTime;
	private Long addUserId;
	
	
	
	// added by jayasudha
	
	

	private String longDisplayName;
	private String shortDisplayName;
	private Timestamp localEffectiveFromTime;
	private Timestamp localEffectiveToTime;
	private String statusCode;
	private String administrativeComments;
	
	
	
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
	
	public String getStatusCode() {
		
		 String status = getStatusCd();
		 String retrunStatus = "";
		 if(status!=null && status.equalsIgnoreCase("A")){
			 retrunStatus  = "Active";
		 }else{
			 retrunStatus  = "Inactive";
		 }
		return retrunStatus;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getAdministrativeComments() {
		return administrativeComments;
	}
	public void setAdministrativeComments(String administrativeComments) {
		this.administrativeComments = administrativeComments;
	}
		
	public String getConceptTypeCd() {
		return conceptTypeCd;
	}
	public void setConceptTypeCd(String conceptTypeCd) {
		this.conceptTypeCd = conceptTypeCd;
	}
	public String getConceptCode() {
		return conceptCode;
	}
	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}
	public String getConceptNm() {
		return conceptNm;
	}
	public void setConceptNm(String conceptNm) {
		this.conceptNm = conceptNm;
	}
	public String getConceptPreferredNm() {
		return conceptPreferredNm;
	}
	public void setConceptPreferredNm(String conceptPreferredNm) {
		this.conceptPreferredNm = conceptPreferredNm;
	}
	public String getConceptStatusCd() {
		return conceptStatusCd;
	}
	public void setConceptStatusCd(String conceptStatusCd) {
		this.conceptStatusCd = conceptStatusCd;
	}
	public java.util.Date getConceptStatusTime() {
		return conceptStatusTime;
	}
	public void setConceptStatusTime(java.util.Date conceptStatusTime) {
		this.conceptStatusTime = conceptStatusTime;
	}
	public String getCodeSystemVersionNbr() {
		return codeSystemVersionNbr;
	}
	public void setCodeSystemVersionNbr(String codeSystemVersionNbr) {
		this.codeSystemVersionNbr = codeSystemVersionNbr;
	}
	public String getConceptOrderNbr() {
		return conceptOrderNbr;
	}
	public void setConceptOrderNbr(String conceptOrderNbr) {
		this.conceptOrderNbr = conceptOrderNbr;
	}
	public String getAdminComments() {
		return adminComments;
	}
	public void setAdminComments(String adminComments) {
		this.adminComments = adminComments;
	}
	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	public String getValueSetOid() {
		return valueSetOid;
	}
	public void setValueSetOid(String valueSetOid) {
		this.valueSetOid = valueSetOid;
	}
	public String getStatusCdDescTxt() {
		return statusCdDescTxt;
	}
	public void setStatusCdDescTxt(String statusCdDescTxt) {
		this.statusCdDescTxt = statusCdDescTxt;
	}
	
	public String getCodeSetNm() {
		return codeSetNm;
	}
	public void setCodeSetNm(String codeSetNm) {
		this.codeSetNm = codeSetNm;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCodeDescTxt() {
		return codeDescTxt;
	}
	public void setCodeDescTxt(String codeDescTxt) {
		this.codeDescTxt = codeDescTxt;
	}
	public String getCodeShortDescTxt() {
		return codeShortDescTxt;
	}
	public void setCodeShortDescTxt(String codeShortDescTxt) {
		this.codeShortDescTxt = codeShortDescTxt;
	}
	public String getCodeSystemCd() {
		return codeSystemCd;
	}
	public void setCodeSystemCd(String codeSystemCd) {
		this.codeSystemCd = codeSystemCd;
	}
	public String getCodeSystemDescTxt() {
		return codeSystemDescTxt;
	}
	public void setCodeSystemDescTxt(String codeSystemDescTxt) {
		this.codeSystemDescTxt = codeSystemDescTxt;
	}
	public String getEffectiveFromTime() {
		
		if(effectiveFromTime != null){
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			String currentDate = dateFormat.format(effectiveFromTime);
			return currentDate;
		}else {
			return "";
		}
	}
	public void setEffectiveFromTime(java.util.Date effectiveFromTime) {
		this.effectiveFromTime = effectiveFromTime;
	}
	public void setEffectiveFromTime(java.lang.String strEffectiveFromTime) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			java.util.Date effFromTime = dateFormat.parse(strEffectiveFromTime);
			setEffectiveFromTime(effFromTime);
		} catch (ParseException ex) {
			logger.error("Parse exception on effectiveFromTime", ex);
		}
		
	}
	public String getEffectiveToTime() {
		
		if(effectiveToTime != null){
		
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			String currentEffectiveToTime = dateFormat.format(effectiveToTime);
			return currentEffectiveToTime;
		} else {
			return "";
		}
	}
	
	public void setEffectiveToTime(java.lang.String  strEffectiveToTime) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			java.util.Date effToTime = dateFormat.parse(strEffectiveToTime);
			setEffectiveToTime(effToTime);
		} catch (ParseException ex) {
			logger.error("Parse exception on effectiveToTime", ex);
		}
	}
	public void setEffectiveToTime(java.util.Date effectiveToTime) {
		this.effectiveToTime = effectiveToTime;
	}

	public Integer getIndentLevelNbr() {
		return indentLevelNbr;
	}
	public void setIndentLevelNbr(Integer indentLevelNbr) {
		this.indentLevelNbr = indentLevelNbr;
	}
	public String getIsModifiableInd() {
		return isModifiableInd;
	}
	public void setIsModifiableInd(String isModifiableInd) {
		this.isModifiableInd = isModifiableInd;
	}
	public String getParentIsCd() {
		return parentIsCd;
	}
	public void setParentIsCd(String parentIsCd) {
		this.parentIsCd = parentIsCd;
	}
	public Long getNbsUid() {
		return nbsUid;
	}
	public void setNbsUid(Long nbsUid) {
		this.nbsUid = nbsUid;
	}
	public String getSourceConceptId() {
		return sourceConceptId;
	}
	public void setSourceConceptId(String sourceConceptId) {
		this.sourceConceptId = sourceConceptId;
	}
	public String getSuperCodeSetNm() {
		return superCodeSetNm;
	}
	public void setSuperCodeSetNm(String superCodeSetNm) {
		this.superCodeSetNm = superCodeSetNm;
	}
	public String getSuperCode() {
		return superCode;
	}
	public void setSuperCode(String superCode) {
		this.superCode = superCode;
	}
	public void setStatusTime(String statusTime) {
		this.statusTime = statusTime;
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
	public String getCodeTmp() {
		return codeTmp;
	}
	public void setCodeTmp(String codeTmp) {
		this.codeTmp = codeTmp;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f =CodeValueGeneralDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public Timestamp getLocalEffectiveFromTime() {
		return localEffectiveFromTime;
	}
	public void setLocalEffectiveFromTime(String localEffectiveFromTime) {
		
		  this.localEffectiveFromTime  =  StringUtils.stringToStrutsTimestamp(localEffectiveFromTime);
	}
	public Timestamp getLocalEffectiveToTime() {
		return localEffectiveToTime;
	}
	public void setLocalEffectiveToTime(String localEffectiveToTime) {
		
		this.localEffectiveToTime  =  StringUtils.stringToStrutsTimestamp(localEffectiveToTime);
	}

}
