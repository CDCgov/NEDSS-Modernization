package gov.cdc.nedss.systemservice.ejb.dbauthejb.dt;

/**
 *@Name:  AuthUserDT.java
 *@Description: Refacored code
 *@Company: 	SAIC
 *@author	Pradeep K Sharma
 *@version	4.4.1
 *@OriginalName: SecureUserDT.java
 *@Description: NBS User Table (replacing SunONE LDAP)
 * 	One for each User.
 *@Copyright:	Copyright (c) 2011
 *@Company: 	CSC
 * @author	Gregory Tucker
 * @version	1.0
 */

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class AuthUserDT extends AbstractVO implements RootDTInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long authUserUid;
	private String userId;
	private String userType;
	private String userTitle;
	private String userDepartment;
	private String userFirstNm;
	private String userLastNm;
	private String userWorkEmail;
	private String userWorkPhone;
	private String userMobilePhone;
	private String masterSecInd;
	private String progAreaInd;
	private Long nedssEntryId;
	private Long externalOrgUid;
	private Long providerUid;
	private String userPassword;
	private String userComments;
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private String JurisdictionDerivationInd;

	

	public Long getAuthUserUid() {
		return authUserUid;
	}

	public void setAuthUserUid(Long authUserUid) {
		this.authUserUid = authUserUid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserTitle() {
		return userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	public String getUserDepartment() {
		return userDepartment;
	}

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}

	public String getUserFirstNm() {
		return userFirstNm;
	}

	public void setUserFirstNm(String userFirstNm) {
		this.userFirstNm = userFirstNm;
	}

	public String getUserLastNm() {
		return userLastNm;
	}

	public void setUserLastNm(String userLastNm) {
		this.userLastNm = userLastNm;
	}

	public String getUserWorkEmail() {
		return userWorkEmail;
	}

	public void setUserWorkEmail(String userWorkEmail) {
		this.userWorkEmail = userWorkEmail;
	}

	public String getUserWorkPhone() {
		return userWorkPhone;
	}

	public void setUserWorkPhone(String userWorkPhone) {
		this.userWorkPhone = userWorkPhone;
	}

	public String getUserMobilePhone() {
		return userMobilePhone;
	}

	public void setUserMobilePhone(String userMobilePhone) {
		this.userMobilePhone = userMobilePhone;
	}

	public String getMasterSecAdminInd() {
		return masterSecInd;
	}

	public void setMasterSecAdminInd(String masterSecAdminInd) {
		this.masterSecInd = masterSecAdminInd;
	}

	public String getProgAreaAdminInd() {
		return progAreaInd;
	}

	public void setProgAreaAdminInd(String progAreaAdminInd) {
		this.progAreaInd = progAreaAdminInd;
	}

	public Long getNedssEntryId() {
		return nedssEntryId;
	}

	public void setNedssEntryId(Long nedssEntryId) {
		this.nedssEntryId = nedssEntryId;
	}

	public Long getExternalOrgUid() {
		return externalOrgUid;
	}

	public void setExternalOrgUid(Long externalOrgUid) {
		this.externalOrgUid = externalOrgUid;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserComments() {
		return userComments;
	}

	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}

	public Long getLastChgUserId() {
		return lastChgUserId;
	}

	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}

	public String getRecordStatusCd() {
		return recordStatusCd;
	}

	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}

	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = AuthUserDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

	// ///////////////////## Unimplemented Methods in RootDTInterface
	// ##///////////////////////////////
	public String getJurisdictionCd() {
		return null;
	}

	public void setJurisdictionCd(String aJurisdictionCd) {
	}

	public String getProgAreaCd() {
		return null;
	}

	public void setProgAreaCd(String aProgAreaCd) {
	}

	public String getLocalId() {
		return null;
	}

	public void setLocalId(String aLocalId) {
	}

	public String getLastChgReasonCd() {
		return null;
	}

	public void setLastChgReasonCd(String aLastChgReasonCd) {
	}

	public String getStatusCd() {
		return null;
	}

	public void setStatusCd(String aStatusCd) {
	}

	public Timestamp getStatusTime() {
		return null;
	}

	public void setStatusTime(Timestamp aStatusTime) {
	}

	public String getSuperclass() {
		return null;
	}

	public Long getUid() {
		return null;
	}

	public boolean isItNew() {
		return false;
	}

	public void setItNew(boolean itNew) {
	}

	public boolean isItDirty() {
		return false;
	}

	public void setItDirty(boolean itDirty) {
	}

	public boolean isItDelete() {
		return false;
	}

	public void setItDelete(boolean itDelete) {
	}

	public String getSharedInd() {
		return null;
	}

	public void setSharedInd(String aSharedInd) {
	}

	public Integer getVersionCtrlNbr() {
		return null;
	}

	public Long getProgramJurisdictionOid() {
		return null;
	}

	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
	}

	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		return false;
	}

	/**
	 * @return the providerUid
	 */
	public Long getProviderUid() {
		return providerUid;
	}

	/**
	 * @param providerUid the providerUid to set
	 */
	public void setProviderUid(Long providerUid) {
		this.providerUid = providerUid;
	}

	public String getJurisdictionDerivationInd() {
		return JurisdictionDerivationInd;
	}

	public void setJurisdictionDerivationInd(String jurisdictionDerivationInd) {
		JurisdictionDerivationInd = jurisdictionDerivationInd;
	}
}