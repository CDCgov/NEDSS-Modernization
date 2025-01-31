package gov.cdc.nedss.pagemanagement.wa.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class PageCondMappingDT extends AbstractVO  implements RootDTInterface{
	
	private Long pageCondMappingUid;
	private Long waTemplateUid;
	private String conditionCd;
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String conditionDesc;
	public String getConditionDesc() {
		return conditionDesc;
	}

	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}

	public String getPortReqIndCd() {
		return portReqIndCd;
	}

	public void setPortReqIndCd(String portReqIndCd) {
		this.portReqIndCd = portReqIndCd;
	}

	private String portReqIndCd;

	public Long getPageCondMappingUid() {
		return pageCondMappingUid;
	}

	public void setPageCondMappingUid(Long pageCondMappingUid) {
		this.pageCondMappingUid = pageCondMappingUid;
	}

	public Long getWaTemplateUid() {
		return waTemplateUid;
	}

	public void setWaTemplateUid(Long waTemplateUid) {
		this.waTemplateUid = waTemplateUid;
	}

	public String getConditionCd() {
		return conditionCd;
	}

	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
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
		return null;
	}

	@Override
	public Timestamp getRecordStatusTime() {
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
		return itDelete;
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return itDirty;
	}

	@Override
	public boolean isItNew() {
		
		return itNew;
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
		this.itDelete = itDelete;
		
	}

	@Override
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
		
	}

	@Override
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
		
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
		lastChgUserId = aLastChgUserId;
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
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

}
