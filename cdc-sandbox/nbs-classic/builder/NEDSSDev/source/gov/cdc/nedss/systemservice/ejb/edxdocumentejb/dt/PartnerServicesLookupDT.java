package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt;


import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * PartnerServicesLookupDT - Used by DAO for query values.
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: CSRA for CDC</p>
 * Dec 22nd, 2016
 * @version 0.9
 */

public class PartnerServicesLookupDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;

	private Boolean drivingCase; //added or changed during time period
	private Boolean isPartner; //if true is partner, if false is not partner
	private Boolean isIndex; //if true is index, if false is not index
	private Boolean isContactRcd; //if true, contact without any investigation
	private Long publicHealthCaseUid;
	private String progAreaCd;
	private String localId;
	private String personLocalId;
	private Long interviewUid;
	private String fldFollUpDispo;
	private String referralBasisCd;
	private Timestamp activityFromTime;
	private Timestamp lastChgTime;
	private Collection<Object> contactSummaryColl;
	private Collection<Object> interviewSummaryColl;
	private String legacyCaseNo; //INV200
	private String indexCaseLocalId; //associated index case if partner case
	private Long ctContactUid; 
	private Timestamp contactInterviewDate;
	private CTContactProxyVO ctContactProxyVO;
	
	public Boolean getDrivingCase() {
		return drivingCase;
	}
	public void setDrivingCase(Boolean drivingCase) {
		this.drivingCase = drivingCase;
	}	
	public String getIndexCaseLocalId() {
		return indexCaseLocalId;
	}
	public Boolean getIsPartner() {
		return isPartner;
	}
	public void setIsPartner(Boolean isPartner) {
		this.isPartner = isPartner;
	}
	public Boolean getIsIndex() {
		return isIndex;
	}
	public void setIsIndex(Boolean isIndex) {
		this.isIndex = isIndex;
	}
	/**
	 * @return the publicHealthCaseUid
	 */
	public Long getPublicHealthCaseUid() {
		return publicHealthCaseUid;
	}
	/**
	 * @param publicHealthCaseUid the publicHealthCaseUid to set
	 */
	public void setPublicHealthCaseUid(Long publicHealthCaseUid) {
		this.publicHealthCaseUid = publicHealthCaseUid;
	}
	public String getProgAreaCd() {
		return progAreaCd;
	}
	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}
	/**
	 * @return the Initial interviewUid
	 */
	public Long getInterviewUid() {
		return interviewUid;
	}
	/**
	 * @param interviewUid the interviewUid to set
	 */
	public void setInterviewUid(Long interviewUid) {
		this.interviewUid = interviewUid;
	}
	/**
	 * @return the contactSummaryDT Collection
	 */
	public Collection<Object> getContactSummaryColl() {
		return contactSummaryColl;
	}
	/**
	 * @param contactSummaryColl the contactSummaryDT Collection to set
	 */
	public void setContactSummaryColl(Collection<Object> contactSummaryColl) {
		this.contactSummaryColl = contactSummaryColl;
	}	
	/**
	 * Interview Summary is needed for the Site ID
	 */
	public Collection<Object> getInterviewSummaryColl() {
		return interviewSummaryColl;
	}
	public void setInterviewSummaryColl(Collection<Object> interviewSummaryColl) {
		this.interviewSummaryColl = interviewSummaryColl;
	}
	/**
	 * Use first Legacy Case No if present
	 * @return
	 */
	public String getLegacyCaseNo() {
		return legacyCaseNo;
	}

	public void setLegacyCaseNo(String legacyCaseNo) {
		this.legacyCaseNo = legacyCaseNo;
	}
	/**
	 * @return the disposition
	 */
	public String getFldFollUpDispo() {
		return fldFollUpDispo;
	}
	/**
	 * @set the disposition
	 */
	public void setFldFollUpDispo(String fldFollUpDispo) {
		this.fldFollUpDispo = fldFollUpDispo;
	}
	/**
	 * LocalId is used as the PS Case Id if legacy id or migrated id not present
	 */
	public String getLocalId() {
		return localId;
	}
	/**
	 * Set the local id
	 */
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	/**
	 * Get the patient local id
	 */
	public String getPersonLocalId() {
		return personLocalId;
	}
	/**
	 * Set the patient local id
	 */
	public void setPersonLocalId(String personLocalId) {
		this.personLocalId = personLocalId;
	}
	/**
	 * This is the Index case this partner is connected to.
	 * @param indexCaseLocalId
	 */
	public void setIndexCaseLocalId(String indexCaseLocalId) {
		this.indexCaseLocalId = indexCaseLocalId;
	}

	public Boolean getIsContactRcd() {
		return isContactRcd;
	}
	public void setIsContactRcd(Boolean isContactRcd) {
		this.isContactRcd = isContactRcd;
	}
	public Long getCtContactUid() {
		return ctContactUid;
	}
	public void setCtContactUid(Long ctContactUid) {
		this.ctContactUid = ctContactUid;
	}
	public CTContactProxyVO getCtContactProxyVO() {
		return ctContactProxyVO;
	}
	public void setCtContactProxyVO(CTContactProxyVO ctContactProxyVO) {
		this.ctContactProxyVO = ctContactProxyVO;
	}
	public Timestamp getContactInterviewDate() {
		return contactInterviewDate;
	}
	public void setContactInterviewDate(Timestamp contactInterviewDate) {
		this.contactInterviewDate = contactInterviewDate;
	}
	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getReferralBasisCd() {
		return referralBasisCd;
	}
	public void setReferralBasisCd(String referralBasisCd) {
		this.referralBasisCd = referralBasisCd;
	}
	public Timestamp getActivityFromTime() {
		return activityFromTime;
	}
	public void setActivityFromTime(Timestamp activityFromTime) {
		this.activityFromTime = activityFromTime;
	}
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
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
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAddUserId(Long aAddUserId) {
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
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatusTime(Timestamp aStatusTime) {
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
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
	}
	@Override
	public Timestamp getAddTime() {
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
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
