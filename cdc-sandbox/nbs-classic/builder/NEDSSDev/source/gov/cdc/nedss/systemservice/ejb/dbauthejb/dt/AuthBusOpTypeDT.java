package gov.cdc.nedss.systemservice.ejb.dbauthejb.dt;

/**
 *@Name:  AuthBusOpTypeDT.java
 *@Description: Refacored code
 * @Company: 	SAIC
 * @author	Pradeep K Sharma/2012
 * @version	4.4.1
 * @OriginalName: SecureBusinessOperationTypeDT.java
 * Description: DT for the Bus Operation Type - i.e. View, View Workup, Assign Security, Create, Merge, Delete, etc.
 *    used in the new database based security.
 * Copyright:	Copyright (c) 2011
 * Company: 	CDC
 * @author	Gregory Tucker
 * @version	1.0
 */

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class AuthBusOpTypeDT extends AbstractVO implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	private Long authBusOpTypeUid;
	private String busOpNm;
	private String busOpDispNm;
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private Integer operationSequence;

	public Long getAuthBusOpTypeUid() {
		return authBusOpTypeUid;
	}

	public void setAuthBusOpTypeUid(Long authBusOpTypeUid) {
		this.authBusOpTypeUid = authBusOpTypeUid;
	}

	public String getBusOpNm() {
		return busOpNm;
	}

	public void setBusOpNm(String busOpNm) {
		this.busOpNm = busOpNm;
	}

	public String getBusOpDispNm() {
		return busOpDispNm;
	}

	public void setBusOpDispNm(String busOpDispNm) {
		this.busOpDispNm = busOpDispNm;
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

	public Integer getOperationSequence() {
		return operationSequence;
	}

	public void setOperationSequence(Integer operationSequence) {
		this.operationSequence = operationSequence;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = AuthBusOpTypeDT.class.getDeclaredFields();
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
		throw new java.lang.UnsupportedOperationException(
				"Method getJurisdictionCd() not yet implemented.");
	}

	public void setJurisdictionCd(String aJurisdictionCd) {
		throw new java.lang.UnsupportedOperationException(
				"Method setJurisdictionCd() not yet implemented.");
	}

	public String getProgAreaCd() {
		throw new java.lang.UnsupportedOperationException(
				"Method getProgAreaCd() not yet implemented.");
	}

	public void setProgAreaCd(String aProgAreaCd) {
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

	public Integer getVersionCtrlNbr() {
		throw new java.lang.UnsupportedOperationException(
				"Method getVersionCtrlNbr() not yet implemented.");
	}

	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		return false;
	}

	

}
