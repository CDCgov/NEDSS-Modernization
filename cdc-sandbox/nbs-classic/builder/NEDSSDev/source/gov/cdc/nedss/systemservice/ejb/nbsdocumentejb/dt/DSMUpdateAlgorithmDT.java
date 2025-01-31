package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class DSMUpdateAlgorithmDT extends AbstractVO implements RootDTInterface {
	
	private static final long serialVersionUID = 1L;
	
	private Long dsmUpdateAlgorithmUid;
	private String conditionCd;
	private String sendingSystemNm;
	private String updateIndCd;
	private String updateClosedBehaviour;
	private String updateMultiClosedBehaviour;
	private String updateMultiOpenBehaviour;
	private String updateIgnoreList;
	private String updateTimeframe;
	private String adminComment;
	private String statusCd;
	private Timestamp statusTime;
	private Long addUserId;
	private Timestamp addTime;
	private Long lastChgUserId;
	private Timestamp lastChgTime;
	private String dsmUpdateAlgorithmMapKey;
	
	public String getAdminComment() {
		return adminComment;
	}

	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}
	
	public String getDsmUpdateAlgorithmMapKey() {
		return dsmUpdateAlgorithmMapKey;
	}

	public void setDsmUpdateAlgorithmMapKey(String dsmUpdateAlgorithmMapKey) {
		this.dsmUpdateAlgorithmMapKey = dsmUpdateAlgorithmMapKey;
	}

	public Timestamp getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	public Long getLastChgUserId() {
		return lastChgUserId;
	}

	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}

	public Long getDsmUpdateAlgorithmUid() {
		return dsmUpdateAlgorithmUid;
	}

	public void setDsmUpdateAlgorithmUid(Long dsmUpdateAlgorithmUid) {
		this.dsmUpdateAlgorithmUid = dsmUpdateAlgorithmUid;
	}

	public String getConditionCd() {
		return conditionCd;
	}

	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}

	public String getSendingSystemNm() {
		return sendingSystemNm;
	}

	public void setSendingSystemNm(String sendingSystemNm) {
		this.sendingSystemNm = sendingSystemNm;
	}

	public String getUpdateIndCd() {
		return updateIndCd;
	}

	public void setUpdateIndCd(String updateIndCd) {
		this.updateIndCd = updateIndCd;
	}

	public String getUpdateClosedBehaviour() {
		return updateClosedBehaviour;
	}

	public void setUpdateClosedBehaviour(String updateClosedBehaviour) {
		this.updateClosedBehaviour = updateClosedBehaviour;
	}

	public String getUpdateMultiClosedBehaviour() {
		return updateMultiClosedBehaviour;
	}

	public void setUpdateMultiClosedBehaviour(String updateMultiClosedBehaviour) {
		this.updateMultiClosedBehaviour = updateMultiClosedBehaviour;
	}

	public String getUpdateMultiOpenBehaviour() {
		return updateMultiOpenBehaviour;
	}

	public void setUpdateMultiOpenBehaviour(String updateMultiOpenBehaviour) {
		this.updateMultiOpenBehaviour = updateMultiOpenBehaviour;
	}

	public String getUpdateIgnoreList() {
		return updateIgnoreList;
	}

	public void setUpdateIgnoreList(String updateIgnoreList) {
		this.updateIgnoreList = updateIgnoreList;
	}

	public String getUpdateTimeframe() {
		return updateTimeframe;
	}

	public void setUpdateTimeframe(String updateTimeframe) {
		this.updateTimeframe = updateTimeframe;
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
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocalId(String aLocalId) {
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
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

}
