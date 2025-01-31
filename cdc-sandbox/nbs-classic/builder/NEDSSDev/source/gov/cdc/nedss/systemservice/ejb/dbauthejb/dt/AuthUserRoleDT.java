package gov.cdc.nedss.systemservice.ejb.dbauthejb.dt;

/**
 *@Name:  AuthUserRoleDT.java
 *@Description: Refactored code
 *@Company: 	SAIC
 *@author	Pradeep K Sharma
 *@version	4.4.1
 *@OriginalName:  SecureUserRoleDT.java
 *@Description: Realized Role
 *One for each User/Jurisdiction/Program Area/Permission Set combination.
 *@Copyright:	Copyright (c) 2011
 *@Company: 	CSC
 *@author	Gregory Tucker
 *@version	1.0
 */

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class AuthUserRoleDT extends AbstractVO implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	private Long authUserRoleUid;
	private String authRoleNm;
	private String progAreaCd;
	private String jurisdictionCd;
	private Long authUserUid;
	private Long authPermSetUid;
	private String roleGuestInd;
	private String readOnlyInd;
	private Integer dispSeqNbr;
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private String permSetNm;

	public String getPermSetNm() {
		return permSetNm;
	}

	public void setPermSetNm(String permSetNm) {
		this.permSetNm = permSetNm;
	}

	public String getProgAreaCd() {
		return progAreaCd;
	}

	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}

	public String getJurisdictionCd() {
		return jurisdictionCd;
	}

	public void setJurisdictionCd(String jurisdictionCd) {
		this.jurisdictionCd = jurisdictionCd;
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

	public Long getAuthUserRoleUid() {
		return authUserRoleUid;
	}

	public void setAuthUserRoleUid(Long authUserRoleUid) {
		this.authUserRoleUid = authUserRoleUid;
	}

	public String getAuthRoleNm() {
		return authRoleNm;
	}

	public void setAuthRoleNm(String authRoleNm) {
		this.authRoleNm = authRoleNm;
	}

	public Long getAuthUserUid() {
		return authUserUid;
	}

	public void setAuthUserUid(Long authUserUid) {
		this.authUserUid = authUserUid;
	}

	public Long getAuthPermSetUid() {
		return authPermSetUid;
	}

	public void setAuthPermSetUid(Long authPermSetUid) {
		this.authPermSetUid = authPermSetUid;
	}

	public String getRoleGuestInd() {
		return roleGuestInd;
	}

	public void setRoleGuestInd(String roleGuestInd) {
		this.roleGuestInd = roleGuestInd;
	}

	public String getReadOnlyInd() {
		return readOnlyInd;
	}

	public void setReadOnlyInd(String readOnlyInd) {
		this.readOnlyInd = readOnlyInd;
	}

	public Integer getDispSeqNbr() {
		return dispSeqNbr;
	}

	public void setDispSeqNbr(Integer dispSeqNbr) {
		this.dispSeqNbr = dispSeqNbr;
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
			Field[] f = AuthUserRoleDT.class.getDeclaredFields();
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

	public String getStatusCd() {
		throw new java.lang.UnsupportedOperationException(
				"Method getStatusCd() not yet implemented.");
	}

	public void setStatusCd(String aStatusCd) {
		throw new java.lang.UnsupportedOperationException(
				"Method setStatusCd() not yet implemented.");
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
		throw new java.lang.UnsupportedOperationException(
				"Method isItNew() not yet implemented.");
	}

	public void setItNew(boolean itNew) {
		throw new java.lang.UnsupportedOperationException(
				"Method setItNew() not yet implemented.");
	}

	public boolean isItDirty() {
		throw new java.lang.UnsupportedOperationException(
				"Method isItDirty() not yet implemented.");
	}

	public void setItDirty(boolean itDirty) {
		throw new java.lang.UnsupportedOperationException(
				"Method setItDirty() not yet implemented.");
	}

	public boolean isItDelete() {
		throw new java.lang.UnsupportedOperationException(
				"Method isItDelete() not yet implemented.");
	}

	public void setItDelete(boolean itDelete) {
		throw new java.lang.UnsupportedOperationException(
				"Method setItDelete() not yet implemented.");
	}

	public String getSharedInd() {
		throw new java.lang.UnsupportedOperationException(
				"Method getSharedInd() not yet implemented.");
	}

	public void setSharedInd(String aSharedInd) {
		throw new java.lang.UnsupportedOperationException(
				"Method setSharedInd() not yet implemented.");
	}

	public Integer getVersionCtrlNbr() {
		throw new java.lang.UnsupportedOperationException(
				"Method getVersionCtrlNbr() not yet implemented.");
	}

	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		return false;
	}

	public Long getProgramJurisdictionOid() {
		throw new java.lang.UnsupportedOperationException(
				"Method getProgramJurisdictionOid() not yet implemented.");
	}

	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		throw new java.lang.UnsupportedOperationException(
				"Method setProgramJurisdictionOid() not yet implemented.");
	}
}
