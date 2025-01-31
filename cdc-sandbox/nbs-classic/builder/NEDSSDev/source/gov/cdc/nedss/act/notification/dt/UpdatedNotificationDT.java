/**
 * Data Object for UPDATED_NOTIFICATION table
 */
package gov.cdc.nedss.act.notification.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

public class UpdatedNotificationDT
	extends AbstractVO
	implements RootDTInterface {
	private static final long serialVersionUID = 1L;

	private Long notificationUid;
	private boolean caseStatusChg = false;
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private Integer versionCtrlNbr;
	private String statusCd;
	private String caseClassCd;

	/**
	 * Sets the value of the versionCtrlNbr property.
	 * @param aVersionCtrlNbr version control number 
	 */
	public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
		versionCtrlNbr = aVersionCtrlNbr;
	}

	/**
	 * Access method for the versionCtrlNbr property
	 * @return version control number
	 */
	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}

	/**
	 * Constructor for UpdatedNotificationDT
	 *
	 */
	public UpdatedNotificationDT() {
	}

	/**
	Access method for the addTime property.
	
	@return   the current value of the addTime property
	 */
	public Timestamp getAddTime() {
		return addTime;
	}

	/**
	Sets the value of the addTime property.
	
	@param aAddTime the new value of the addTime property
	 */
	public void setAddTime(Timestamp aAddTime) {
		addTime = aAddTime;
		setItDirty(true);
	}

	/**
	Access method for the addUserId property.
	
	@return   the current value of the addUserId property
	 */
	public Long getAddUserId() {
		return addUserId;
	}

	/**
	Sets the value of the addUserId property.
	
	@param aAddUserId the new value of the addUserId property
	 */
	public void setAddUserId(Long aAddUserId) {
		addUserId = aAddUserId;
		setItDirty(true);
	}

	/**
	 * Access method for lastChgTime property
	 * 
	 * @return last Change Time
	 */
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	/**
		* Sets the value for lastChgTime property
		* 
		* @param aLastChgTime Last Change Time Property
		*/
	public void setLastChgTime(Timestamp aLastChgTime) {
		lastChgTime = aLastChgTime;
		setItDirty(true);
	}

	/**
		* Access method for lastChgUserId property
		* 
		* @return last change user id
		*/
	public Long getLastChgUserId() {
		return lastChgUserId;
	}

	/**
	 * Setter method for lastChgUserId property
	 * 
	 * @param aLastChgUserId last change user id
	 */
	public void setLastChgUserId(Long aLastChgUserId) {
		lastChgUserId = aLastChgUserId;
		setItDirty(true);
	}

	public String getJurisdictionCd() {
		/**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
		throw new java.lang.UnsupportedOperationException(
			"Method getJurisdictionCd() not yet implemented.");
	}
	public void setJurisdictionCd(String aJurisdictionCd) {
		/**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
		throw new java.lang.UnsupportedOperationException(
			"Method setJurisdictionCd() not yet implemented.");
	}
	public String getProgAreaCd() {
		/**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
		throw new java.lang.UnsupportedOperationException(
			"Method getProgAreaCd() not yet implemented.");
	}
	public void setProgAreaCd(String aProgAreaCd) {
		/**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
		throw new java.lang.UnsupportedOperationException(
			"Method setProgAreaCd() not yet implemented.");
	}
	public String getLocalId() {
		throw new java.lang.UnsupportedOperationException(
			"Method getLocalId() not yet implemented.");
	}
	public void setLocalId(String aLocalId) {
		throw new java.lang.UnsupportedOperationException(
			"Method setLocalId() not yet implemented.");
	}
	public String getLastChgReasonCd() {
		throw new java.lang.UnsupportedOperationException(
			"Method getLastChgReasonCd() not yet implemented.");
	}
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		throw new java.lang.UnsupportedOperationException(
			"Method setLastChgReasonCd() not yet implemented.");
	}
	public String getRecordStatusCd() {
		throw new java.lang.UnsupportedOperationException(
			"Method getRecordStatusCd() not yet implemented.");
	}
	public void setRecordStatusCd(String aRecordStatusCd) {
		throw new java.lang.UnsupportedOperationException(
			"Method setRecordStatusCd() not yet implemented.");
	}
	public Timestamp getRecordStatusTime() {
		throw new java.lang.UnsupportedOperationException(
			"Method getRecordStatusTime() not yet implemented.");
	}
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		throw new java.lang.UnsupportedOperationException(
			"Method setRecordStatusTime() not yet implemented.");
	}
	public String getStatusCd() {
		return this.statusCd;
	}
	public void setStatusCd(String aStatusCd) {
		this.statusCd = aStatusCd;
	}
	public Timestamp getStatusTime() {
		throw new java.lang.UnsupportedOperationException(
			"Method getStatusTime() not yet implemented.");
	}
	public void setStatusTime(Timestamp aStatusTime) {
		throw new java.lang.UnsupportedOperationException(
			"Method setStatusTime() not yet implemented.");
	}
	public String getSuperclass() {
		throw new java.lang.UnsupportedOperationException(
			"Method getSuperclass() not yet implemented.");
	}
	public Long getUid() {
		throw new java.lang.UnsupportedOperationException(
			"Method getUid() not yet implemented.");
	}
	public boolean isItNew() {
		return this.itNew;
	}
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}
	public boolean isItDirty() {
		return this.itDirty;
	}
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}
	public boolean isItDelete() {
		return itDelete;
	}
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}
	public Long getProgramJurisdictionOid() {
		throw new java.lang.UnsupportedOperationException(
			"Method getProgramJurisdictionOid() not yet implemented.");
	}
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		throw new java.lang.UnsupportedOperationException(
			"Method setProgramJurisdictionOid() not yet implemented.");
	}
	public String getSharedInd() {
		throw new java.lang.UnsupportedOperationException(
			"Method getSharedInd() not yet implemented.");
	}
	public void setSharedInd(String aSharedInd) {
		throw new java.lang.UnsupportedOperationException(
			"Method setSharedInd() not yet implemented.");
	}
	/**
	 * Accessor method for caseStatusChg attribute
	 * @return caseStatusChg
	 */
	public boolean isCaseStatusChg() {
		return caseStatusChg;
	}

	/**
	 * Accessor method for notificationUid attribute
	 * 
	 * @return notificationUid
	 */
	public Long getNotificationUid() {
		return notificationUid;
	}

	/**
	 * Setter method for attribute caseStatusChg
	 * @param b caseStatusChg
	 */
	public void setCaseStatusChg(boolean b) {
		caseStatusChg = b;
	}

	/**
	 * Setter method for attribute notificationUid
	 * 
	 * @param long1 NotificationUid
	 */
	public void setNotificationUid(Long long1) {
		notificationUid = long1;
	}

	/**
	 * Not Implemented
	 */
	public boolean isEqual(
		Object objectname1,
		Object objectname2,
		Class<?> voClass) {
		return false;
	}

	/**
	 * @return caseClassCd
	 */
	public String getCaseClassCd() {
		return caseClassCd;
	}

	/**
	 * @param string caseStatusCd
	 */
	public void setCaseClassCd(String aCaseStatusCd) {
		caseClassCd = aCaseStatusCd;
	}

}