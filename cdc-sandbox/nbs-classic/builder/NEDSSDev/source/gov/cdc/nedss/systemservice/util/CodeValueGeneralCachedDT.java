package gov.cdc.nedss.systemservice.util;

import java.sql.Timestamp;

import gov.cdc.nedss.util.AbstractVO;

public class CodeValueGeneralCachedDT extends AbstractVO implements RootDTInterface{
	
	private static final long serialVersionUID = 1L;
	private String codeSetNm;
	private String code;
	private String codeDescTxt;
	private String codeShortDescTxt;
	private String codeSystemCd;
	private String codeSystemDescTxt;
	private String conceptCode;
	private String conceptNm;
	private String conceptPreferredNm;
	private Long nbsUid;
	
	
	
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

	public Long getNbsUid() {
		return nbsUid;
	}

	public void setNbsUid(Long nbsUid) {
		this.nbsUid = nbsUid;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
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

	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}

	public void setAddUserId(Long aAddUserId) {
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

	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}

	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}

	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}

	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}

	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}

	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}

}
