package gov.cdc.nedss.webapp.nbs.action.summary.dt;
import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.StringUtils;


public class AggregateSummaryResultsDT extends AbstractVO implements RootDTInterface{
	
	private String selectLink;
	private String viewLink;
	private String editLink;
	private Long phc_UID;
	private String mmwrWeek;
	private String mmwrYear;
	//private String cd;
	private String rptCntyCD;
	private Timestamp rptFormCmpltTIME;
	private String caseClassCD;
	private String statusCD;
	private String weeklyTotal;
	private String cumulative;
	private Timestamp lastChgTIME;
	private String cd;
	private String cdDescTxt;
	private String txt;
	private Long caseCount;
	private String rptSentDate;
	private String grpInterval;
	private String conditionCd;
	/*
	these all had to be implemented for teh RootDTInterface.
    */
   private String localId;
   private String recordStatusCd;
	
	public String getSelectLink() {
		return selectLink;
	}
	public void setSelectLink(String selectLink) {
		this.selectLink = selectLink;
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
	
	/*public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}*/
			
	public Long getPhc_UID() {
		return phc_UID;
	}
	public void setPhc_UID(Long phc_UID) {
		this.phc_UID = phc_UID;
	}
	
	public String getRptCntyCD() {
		return rptCntyCD;
	}
	public void setRptCntyCD(String rptCntyCD) {
		this.rptCntyCD = rptCntyCD;
	}
	public Timestamp getRptFormCmpltTIME() {
		return rptFormCmpltTIME;
	}
	public void setRptFormCmpltTIME(Timestamp rptFormCmpltTIME) {
		this.rptFormCmpltTIME = rptFormCmpltTIME;
	}
	public String getRptFormCmpltTIME_s() {

		if (getRptFormCmpltTIME() == null)
			return null;
		return StringUtils.formatDate(getRptFormCmpltTIME());
	}

	public void setRptFormCmpltTIME_s(String strTime) {

		if (strTime == null)

			return;

		this.setRptFormCmpltTIME(StringUtils.stringToStrutsTimestamp(strTime));
	}
	public String getCaseClassCD() {
		return caseClassCD;
	}
	public void setCaseClassCD(String caseClassCD) {
		this.caseClassCD = caseClassCD;
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
	public String getStatusCd() {
		return null;
	}

	/**
	 * @param aStatusCd
	 */
	public void setStatusCd(String aStatusCd) {

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
	public Long getAddUserId() {
		return null;
	}

	/**
	 * @param aAddUserId
	 */
	public void setAddUserId(Long aAddUserId) {

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
	
	public String getRecordStatusCd() {
		return recordStatusCd;
	}

	public void setRecordStatusCd(String newRecordStatusCd) {
		recordStatusCd = newRecordStatusCd;
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
	 */
	public Integer getVersionCtrlNbr() {
		return null;
	}
	
	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
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
	public String getMmwrWeek() {
		return mmwrWeek;
	}
	public void setMmwrWeek(String mmwrWeek) {
		this.mmwrWeek = mmwrWeek;
	}
	public String getMmwrYear() {
		return mmwrYear;
	}
	public void setMmwrYear(String mmwrYear) {
		this.mmwrYear = mmwrYear;
	}
	public String getStatusCD() {
		return statusCD;
	}
	public void setStatusCD(String statusCD) {
		this.statusCD = statusCD;
	}
	
	public Timestamp getLastChgTIME() {
		return lastChgTIME;
	}
	public void setLastChgTIME(Timestamp lastChgTIME) {
		this.lastChgTIME = lastChgTIME;
	}
	public String getWeeklyTotal() {
		return weeklyTotal;
	}
	
	public String getLastChgTIME_s() {

		if (getLastChgTIME() == null)
			return null;
		return StringUtils.formatDate(getLastChgTIME());
	}

	public void setLastChgTIME_s(String strTime) {

		if (strTime == null)

			return;

		this.setLastChgTIME(StringUtils.stringToStrutsTimestamp(strTime));
	}
	public void setWeeklyTotal(String weeklyTotal) {
		this.weeklyTotal = weeklyTotal;
	}
	public String getCumulative() {
		return cumulative;
	}
	public void setCumulative(String cumulative) {
		this.cumulative = cumulative;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getCdDescTxt() {
		return cdDescTxt;
	}
	public void setCdDescTxt(String cdDescTxt) {
		this.cdDescTxt = cdDescTxt;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public Long getCaseCount() {
		return caseCount == null ? new Long(0) : caseCount;
	}
	public void setCaseCount(Long caseCount) {
		this.caseCount = caseCount;
	}
	public String getRptSentDate() {
		return rptSentDate;
	}
	public void setRptSentDate(String rptSentDate) {
		this.rptSentDate = rptSentDate;
	}
	public String getGrpInterval() {
		return grpInterval;
	}
	public void setGrpInterval(String grpInterval) {
		this.grpInterval = grpInterval;
	}
	public String getConditionCd() {
		return conditionCd;
	}
	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}
	


}
