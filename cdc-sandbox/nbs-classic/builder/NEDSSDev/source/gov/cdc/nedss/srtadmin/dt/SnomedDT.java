package gov.cdc.nedss.srtadmin.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class SnomedDT extends AbstractVO implements RootDTInterface{
	private static final long serialVersionUID = 1L;
	private String snomedCd;
	private String snomedDescTx;
	private String selectLink;
	private String sourceConceptId;
	private String sourceVersionId;
	private String snomedCdTmp;
	private String paDerivationExcludeCd;
	private String viewLink;
	private String editLink;
	
	public String getViewLink() {
		return viewLink;
	}
	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}
	public String getEditLink() {
		return editLink;
	}
	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}
	public String getSourceConceptId() {
		return sourceConceptId;
	}
	public void setSourceConceptId(String sourceConceptId) {
		this.sourceConceptId = sourceConceptId;
	}
	public String getSourceVersionId() {
		return sourceVersionId;
	}
	public void setSourceVersionId(String sourceVersionId) {
		this.sourceVersionId = sourceVersionId;
	}
	public String getSnomedCd() {
		return snomedCd;
	}
	public void setSnomedCd(String snomedCd) {
		this.snomedCd = snomedCd;
	}
	public String getSelectLink() {
		return selectLink;
	}
	public void setSelectLink(String selectLink) {
		this.selectLink = selectLink;
	}
	
		/*
		these all had to be implemented for teh RootDTInterface.
	 */
	private String localId;
	private String recordStatusCd;
	
	public String getLocalId() {
		return localId;
	}
	
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	
	public void setRecordStatusCd(String newRecordStatusCd) {
		recordStatusCd = newRecordStatusCd;
	}
	
	/**
	 * @param objectname1
	 * @param objectname2
	 * @param voClass
	 * @return boolean
	 */
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		return true;
	}
	
	/**
	 * @param itDirty
	 */
	public void setItDirty(boolean itDirty) {
	
	}
	
	/**
	 * @return boolean
	 */
	public boolean isItDirty() {
		return false;
	}
	
	/**
	 * @param itNew
	 */
	public void setItNew(boolean itNew) {
	
	}
	
	/**
	 * @return boolean
	 */
	public boolean isItNew() {
		return false;
	}
	
	/**
	 * @param itDelete
	 */
	public void setItDelete(boolean itDelete) {
	
	}
	
	/**
	 * @return boolean
	 */
	public boolean isItDelete() {
		return false;
	}
	
	/**
	 * @return java.lang.Long
	 */
	public Long getLastChgUserId() {
		return null;
	}
	
	/**
	 * @param aLastChgUserId
	 */
	public void setLastChgUserId(Long aLastChgUserId) {
	
	}
	
	/**
	 * @return java.lang.String
	 */
	public String getJurisdictionCd() {
		return null;
	}
	
	/**
	 * @param aJurisdictionCd
	 */
	public void setJurisdictionCd(String aJurisdictionCd) {
	
	}
	
	/**
	 * @return java.lang.String
	 */
	public String getProgAreaCd() {
		return null;
	}
	
	/**
	 * @param aProgAreaCd
	 */
	public void setProgAreaCd(String aProgAreaCd) {
	}
	
	/**
	 * @return java.lang.Long
	 */
	public Long getAddUserId() {
		return null;
	}
	
	/**
	 * @param aAddUserId
	 */
	public void setAddUserId(Long aAddUserId) {
	
	}
	
	/**
	 * @return java.lang.String
	 */
	public String getLastChgReasonCd() {
		return null;
	}
	
	/**
	 * @param aLastChgReasonCd
	 */
	public void setLastChgReasonCd(String aLastChgReasonCd) {
	
	}
	
	/**
	 * @return java.lang.String
	 */
	public String getStatusCd() {
		return null;
	}
	
	/**
	 * @param aStatusCd
	 */
	public void setStatusCd(String aStatusCd) {
	
	}
	
	/**
	 * @return java.sql.Timestamp
	 */
	public Timestamp getStatusTime() {
		return null;
	}
	
	/**
	 * @param aStatusTime
	 */
	public void setStatusTime(Timestamp aStatusTime) {
	
	}
	
	/**
	 * @return java.lang.String
	 */
	public String getSuperclass() {
		return null;
	}
	
	/**
	 * @return java.lang.Long
	 */
	public Long getUid() {
		return null;
	}
	
	public void setUid(Long aUid) {
	}
	
	/**
	 * @return java.lang.Long
	 */
	public Long getProgramJurisdictionOid() {
		return null;
	}
	
	/**
	 * @param aProgramJurisdictionOid
	 */
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
	
	}
	
	/**
	 * @return java.lang.String
	 */
	public String getSharedInd() {
		return null;
	}
	
	/**
	 * @param aSharedInd
	 */
	public void setSharedInd(String aSharedInd) {
	
	}
	
	/**
	 */
	public Integer getVersionCtrlNbr() {
		return null;
	}
	
	/**
	 * A setter for add time
	 */
	public void setAddTime(java.sql.Timestamp aAddTime) {
	
	}
	
	/**
	 * A getter for add time
	 */
	public Timestamp getAddTime() {
		return null;
	}
	
	/**
	 * Access method for the lastChgTime property.
	 *
	 * @return   the current value of the lastChgTime property
	 */
	public Timestamp getLastChgTime() {
		return null;
	}
	
	/**
	 * Sets the value of the lastChgTime property.
	 *
	 * @param aLastChgTime the new value of the lastChgTime property
	 */
	public void setLastChgTime(Timestamp aLastChgTime) {
	
	}
	
	/**
	 * @return java.sql.Timestamp
	 */
	public Timestamp getRecordStatusTime() {
		return null;
	}
	
	/**
	 * @param aRecordStatusTime
	 */
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
	
	}
	public String getSnomedDescTx() {
		return snomedDescTx;
	}
	public void setSnomedDescTx(String snomedDescTx) {
		this.snomedDescTx = snomedDescTx;
	}
	public String getSnomedCdTmp() {
		return snomedCdTmp;
	}
	public void setSnomedCdTmp(String snomedCdTmp) {
		this.snomedCdTmp = snomedCdTmp;
	}
	public String getPaDerivationExcludeCd() {
		return paDerivationExcludeCd;
	}
	public void setPaDerivationExcludeCd(String paDerivationExcludeCd) {
		this.paDerivationExcludeCd = paDerivationExcludeCd;
	}
	
	}
