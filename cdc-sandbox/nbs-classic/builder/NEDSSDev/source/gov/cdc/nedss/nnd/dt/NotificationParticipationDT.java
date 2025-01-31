package gov.cdc.nedss.nnd.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import java.sql.Timestamp;

public class NotificationParticipationDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;
	private Long subjectEntityUID;
	private String typeCd;
	private String subjectClassCd;

	public Long getSubjectEntityUID() {
		return subjectEntityUID;
	}
	public void setSubjectEntityUID(Long subjectEntityUID) {
		this.subjectEntityUID = subjectEntityUID;
	}
	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub

	}
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub

	}
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub

	}
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub

	}
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub

	}
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub

	}

	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub

	}
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub

	}
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub

	}
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub

	}
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return the recordStatusCd
	 */
	public String getRecordStatusCd() {
		return null;
	}
	/**
	 * @param recordStatusCd the recordStatusCd to set
	 */
	public void setRecordStatusCd(String recordStatusCd) {
	}
	/**
	 * @return the recordStatusTime
	 */
	public Timestamp getRecordStatusTime() {
		return null;
	}
	/**
	 * @return the addReasonCd
	 */
	public String getAddReasonCd() {
		return null;
	}
	/**
	 * @param addReasonCd the addReasonCd to set
	 */
	public void setAddReasonCd(String addReasonCd) {
	}
	/**
	 * @return the addTime
	 */
	public Timestamp getAddTime() {
		return null;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Timestamp addTime) {
	}
	/**
	 * @return the addUserId
	 */
	public Long getAddUserId() {
		return null;
	}
	/**
	 * @param addUserId the addUserId to set
	 */
	public void setAddUserId(Long addUserId) {
	}
	/**
	 * @return the lastChgReasonCd
	 */
	public String getLastChgReasonCd() {
		return null;
	}
	/**
	 * @param lastChgReasonCd the lastChgReasonCd to set
	 */
	public void setLastChgReasonCd(String lastChgReasonCd) {
	}
	/**
	 * @return the lastChgTime
	 */
	public Timestamp getLastChgTime() {
		return null;
	}
	/**
	 * @param lastChgTime the lastChgTime to set
	 */
	public void setLastChgTime(Timestamp lastChgTime) {
	}
	/**
	 * @return the lastChguserId
	 */
	public Long getLastChgUserId() {
		return null;
	}
	 public void setLastChgUserId(Long aLastChgUserId){};
	 public void setRecordStatusTime(java.sql.Timestamp aRecordStatusTime){}
	public void setSubjectClassCd(String subjectClassCd) {
		this.subjectClassCd = subjectClassCd;
	}
	public String getSubjectClassCd() {
		return subjectClassCd;
	};

}
