package gov.cdc.nedss.page.ejb.portproxyejb.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;

public class AnswerMappingDT extends AbstractVO implements RootDTInterface{
	
	private String status=PortPageUtil.STATUS_MAPPING_NEEDED;
	//From code related
	private String questionIdentifier;
	private String questionLabel;
	private String codeSetNm;
	private String code;
	private String codeDescTxt;
	private long codeSetGroupId;
	
	private String mapped;
	private String mappedDesc;
	
	//To Code related
	private String toCode;
	private String toCodeDescTxt;
	
	private String autoMapped;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQuestionIdentifier() {
		return questionIdentifier;
	}

	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}

	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
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

	public long getCodeSetGroupId() {
		return codeSetGroupId;
	}

	public void setCodeSetGroupId(long codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
	}

	public String getMapped() {
		return mapped;
	}

	public void setMapped(String mapped) {
		this.mapped = mapped;
	}

	public String getToCode() {
		return toCode;
	}

	public void setToCode(String toCode) {
		this.toCode = toCode;
	}

	public String getToCodeDescTxt() {
		return toCodeDescTxt;
	}

	public void setToCodeDescTxt(String toCodeDescTxt) {
		this.toCodeDescTxt = toCodeDescTxt;
	}

	public String getAutoMapped() {
		return autoMapped;
	}

	public void setAutoMapped(String autoMapped) {
		this.autoMapped = autoMapped;
	}

	public String getMappedDesc() {
		return mappedDesc;
	}

	public void setMappedDesc(String mappedDesc) {
		this.mappedDesc = mappedDesc;
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
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
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
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
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
		return null;
	}

	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
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
		return null;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

}
