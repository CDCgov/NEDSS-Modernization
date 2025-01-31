package gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

public class NBSConversionErrorDT extends AbstractVO implements RootDTInterface{
	private Long  nbsConversionErrorUid;
	private String errorCd;
	private String errorMessageTxt;
	private Long conditionCdGroupId;
	private Long nbsConversionMappingUid;
	
	private Long nbsConversionMasterUid;
	private static final long serialVersionUID = 1L;
	public Long getNbsConversionErrorUid() {
		return nbsConversionErrorUid;
	}
	public void setNbsConversionErrorUid(Long nbsConversionErrorUid) {
		this.nbsConversionErrorUid = nbsConversionErrorUid;
	}
	public String getErrorCd() {
		return errorCd;
	}
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
	public String getErrorMessageTxt() {
		return errorMessageTxt;
	}
	public void setErrorMessageTxt(String errorMessageTxt) {
		this.errorMessageTxt = errorMessageTxt;
	}
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
	public void setAddTime(Timestamp addTime) {
		// TODO Auto-generated method stub
		
	}
	public void setAddUserId(Long addUserId) {
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
	public Long getNbsConversionMasterUid() {
		return nbsConversionMasterUid;
	}
	public void setNbsConversionMasterUid(Long nbsConversionMasterUid) {
		this.nbsConversionMasterUid = nbsConversionMasterUid;
	}
	public Long getConditionCdGroupId() {
		return conditionCdGroupId;
	}
	public void setConditionCdGroupId(Long conditionCdGroupId) {
		this.conditionCdGroupId = conditionCdGroupId;
	}
	public Long getNbsConversionMappingUid() {
		return nbsConversionMappingUid;
	}
	public void setNbsConversionMappingUid(Long nbsConversionMappingUid) {
		this.nbsConversionMappingUid = nbsConversionMappingUid;
	}
	
}
