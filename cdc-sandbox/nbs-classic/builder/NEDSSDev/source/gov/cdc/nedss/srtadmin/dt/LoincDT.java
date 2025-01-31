package gov.cdc.nedss.srtadmin.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * 
 * @author nmallela
 *
 */
public class LoincDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;
	private String loincCd;
	private String componentName;
	private String selectLink;
	private String viewLink;
	private String editLink;
	
	private String property;
	private String time_aspect;
	private String system_cd;
	private String scale_type;
	private String method_type;
	private String display_name;
	private String related_class_cd;
	private String loincCdTmp;
	private String paDerivationExcludeCd;
	
	
	public String getPaDerivationExcludeCd() {
		return paDerivationExcludeCd;
	}

	public void setPaDerivationExcludeCd(String paDerivationExcludeCd) {
		this.paDerivationExcludeCd = paDerivationExcludeCd;
	}

	public String getLoincCdTmp() {
		return loincCdTmp;
	}

	public void setLoincCdTmp(String loincCdTmp) {
		this.loincCdTmp = loincCdTmp;
	}

	public String getLoincCd() {
		return loincCd;
	}

	public void setLoincCd(String loincCd) {
		this.loincCd = loincCd;
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

	public String getSelectLink() {
		return selectLink;
	}

	public void setSelectLink(String selectLink) {
		this.selectLink = selectLink;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getTime_aspect() {
		return time_aspect;
	}

	public void setTime_aspect(String time_aspect) {
		this.time_aspect = time_aspect;
	}

	public String getSystem_cd() {
		return system_cd;
	}

	public void setSystem_cd(String system_cd) {
		this.system_cd = system_cd;
	}

	public String getScale_type() {
		return scale_type;
	}

	public void setScale_type(String scale_type) {
		this.scale_type = scale_type;
	}

	public String getMethod_type() {
		return method_type;
	}

	public void setMethod_type(String method_type) {
		this.method_type = method_type;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getRelated_class_cd() {
		return related_class_cd;
	}

	public void setRelated_class_cd(String related_class_cd) {
		this.related_class_cd = related_class_cd;
	}

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
	
	

}
