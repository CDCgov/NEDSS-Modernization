package gov.cdc.nedss.act.interview.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;
import java.sql.Timestamp;

/**
 * Title:InterviewSummaryDT - Dynamic Interview Pages are added in the NBS 4.5b release.
 * Description: Copyright: Copyright (c) 2014
 * Company:Leidos
 * @author: Greg Tucker
 * @version 1.0
 */

public class InterviewSummaryDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;


    private Long interviewUid;
    private Long intervieweeMprUid; //subject
    private Long interviewerUid;    //investigator
    private String interviewerFirstName; 
    private String interviewerLastName; 
    private String interviewerSuffix;
    private String interviewerDegree;    
    private String interviewerFullName; // investigator first + last
    private String interviewerQuickCd; // quick code
    private String intervieweeFirstName;
    private String intervieweeMiddleName;
    private String intervieweeLastName;
    private String intervieweeSuffix;
    private String intervieweeFullName; // subject first last

    private String intervieweeRoleCd;
    private String intervieweeRoleDesc;
    private Timestamp interviewDate;
    private String dateActionLink; //anchor to interview 
    private String interviewDateDisplay; 
    private String interviewTimeDisplay; 
    private String interviewTypeCd;
    private String interviewTypeDesc;
    private String interviewStatusCd;
    private String interviewStatusDesc;
    private String interviewLocCd;
    private String interviewLocDesc;
    private String localId;
    private String recordStatusCd;
    private String the900SiteId;
    private String the900SiteZipCd;
    private String the900SiteTypeCd;
    private String the900Interventions;
    private boolean touched;
    private boolean associated;
    private String checkBoxId;
    private Long publicHealthCaseUid;
    private String addReasonCd; //if a value, reflects the starting investigation for the interview


	/**
	 * Get UID for this Interview
	 * @return the interviewUid
	 */
	public Long getInterviewUid() {
		return interviewUid;
	}

	/**
	 * Set UID for this Interview
	 * @param interviewUid the interviewUid to set
	 */
	public void setInterviewUid(Long interviewUid) {
		this.interviewUid = interviewUid;
	}

	/**
	 * Get UID for the Subject of the Interview
	 * @return the intervieweeMprUid
	 */
	public Long getIntervieweeMprUid() {
		return intervieweeMprUid;
	}

	/**
	 * Set UID for the Subject of the Interview
	 * @param intervieweeMprUid the intervieweeMprUid to set
	 */
	public void setIntervieweeMprUid(Long intervieweeMprUid) {
		this.intervieweeMprUid = intervieweeMprUid;
	}

	/**
	 * Get UID for the Investigator who conducted the Interview
	 * @return the interviewerUid
	 */
	public Long getInterviewerUid() {
		return interviewerUid;
	}

	/**
	 * Set UID for the Investigator who conducted the Interview
	 * @param interviewerUid the interviewerUid to set
	 */
	public void setInterviewerUid(Long interviewerUid) {
		this.interviewerUid = interviewerUid;
	}


	/**
	 * @return the interviewerFirstName
	 */
	public String getInterviewerFirstName() {
		return interviewerFirstName;
	}

	/**
	 * @param interviewerFirstName the interviewerFirstName to set
	 */
	public void setInterviewerFirstName(String interviewerFirstName) {
		this.interviewerFirstName = interviewerFirstName;
	}

	/**
	 * @return the interviewerLastName
	 */
	public String getInterviewerLastName() {
		return interviewerLastName;
	}

	/**
	 * @param interviewerLastName the interviewerLastName to set
	 */
	public void setInterviewerLastName(String interviewerLastName) {
		this.interviewerLastName = interviewerLastName;
	}

	/**
	 * @return the interviewerSuffix
	 */
	public String getInterviewerSuffix() {
		return interviewerSuffix;
	}

	/**
	 * @param interviewerSuffix the interviewerSuffix to set
	 */
	public void setInterviewerSuffix(String interviewerSuffix) {
		this.interviewerSuffix = interviewerSuffix;
	}

	/**
	 * @return the interviewerDegree
	 */
	public String getInterviewerDegree() {
		return interviewerDegree;
	}

	/**
	 * @param interviewerDegree the interviewerDegree to set
	 */
	public void setInterviewerDegree(String interviewerDegree) {
		this.interviewerDegree = interviewerDegree;
	}

	/**
	 * @return the interviewerFullName
	 */
	public String getInterviewerFullName() {
		return interviewerFullName;
	}

	/**
	 * @param interviewerFullName the interviewerFullName to set
	 */
	public void setInterviewerFullName(String interviewerFullName) {
		this.interviewerFullName = interviewerFullName;
	}

	public String getInterviewerQuickCd() {
		return interviewerQuickCd;
	}

	public void setInterviewerQuickCd(String interviewerQuickCd) {
		this.interviewerQuickCd = interviewerQuickCd;
	}

	/**
	 * @return the intervieweeFirstName
	 */
	public String getIntervieweeFirstName() {
		return intervieweeFirstName;
	}

	/**
	 * @param intervieweeFirstName the intervieweeFirstName to set
	 */
	public void setIntervieweeFirstName(String intervieweeFirstName) {
		this.intervieweeFirstName = intervieweeFirstName;
	}

	/**
	 * @return the intervieweeMiddleName
	 */
	public String getIntervieweeMiddleName() {
		return intervieweeMiddleName;
	}

	/**
	 * @param intervieweeMiddleName the intervieweeMiddleName to set
	 */
	public void setIntervieweeMiddleName(String intervieweeMiddleName) {
		this.intervieweeMiddleName = intervieweeMiddleName;
	}

	/**
	 * @return the intervieweeLastName
	 */
	public String getIntervieweeLastName() {
		return intervieweeLastName;
	}

	/**
	 * @param intervieweeLastName the intervieweeLastName to set
	 */
	public void setIntervieweeLastName(String intervieweeLastName) {
		this.intervieweeLastName = intervieweeLastName;
	}

	/**
	 * @return the intervieweeSuffix
	 */
	public String getIntervieweeSuffix() {
		return intervieweeSuffix;
	}

	/**
	 * @param intervieweeSuffix the intervieweeSuffix to set
	 */
	public void setIntervieweeSuffix(String intervieweeSuffix) {
		this.intervieweeSuffix = intervieweeSuffix;
	}

	/**
	 * @return the intervieweeFullName
	 */
	public String getIntervieweeFullName() {
		return intervieweeFullName;
	}

	/**
	 * @param intervieweeFullName the intervieweeFullName to set
	 */
	public void setIntervieweeFullName(String intervieweeFullName) {
		this.intervieweeFullName = intervieweeFullName;
	}

	/**
	 * @return the interviewTimeDisplay
	 */
	public String getInterviewTimeDisplay() {
		return interviewTimeDisplay;
	}

	/**
	 * @param interviewTimeDisplay the interviewTimeDisplay to set
	 */
	public void setInterviewTimeDisplay(String interviewTimeDisplay) {
		this.interviewTimeDisplay = interviewTimeDisplay;
	}


	/**
	 * @return the intervieweeRoleCd
	 */
	public String getIntervieweeRoleCd() {
		return intervieweeRoleCd;
	}

	/**
	 * @param intervieweeRoleCd the intervieweeRoleCd to set
	 */
	public void setIntervieweeRoleCd(String intervieweeRoleCd) {
		this.intervieweeRoleCd = intervieweeRoleCd;
	}

	/**
	 * Get Role Desc i.e. Subject of Investigation
	 * @return the intervieweeRoleDesc
	 */
	public String getIntervieweeRoleDesc() {
		return intervieweeRoleDesc;
	}

	/**
	 * Set Role Desc
	 * @param intervieweeRoleDesc the intervieweeRoleDesc to set
	 */
	public void setIntervieweeRoleDesc(String intervieweeRoleDesc) {
		this.intervieweeRoleDesc = intervieweeRoleDesc;
	}

	/**
	 * @return the interviewDate
	 */
	public Timestamp getInterviewDate() {
		return interviewDate;
	}

	/**
	 * @param interviewDate the interviewDate to set
	 */
	public void setInterviewDate(Timestamp interviewDate) {
		this.interviewDate = interviewDate;
	}
	/**
	 * The dateActionLink is an Anchor for the Interview
	 * "<a  href=/nbs/viewInterview.do?ContextAction=<interviewUID>&interviewUID= etc.</a>";
	 * @return the dateActionLink
	 */
	public String getDateActionLink() {
		return dateActionLink;
	}

	public void setDateActionLink(String dateActionLink) {
		this.dateActionLink = dateActionLink;
	}

	/**
	 * The interview date is an Anchor for the Interview
	 * "<a  href=/nbs/viewInterview.do?ContextAction=<interviewUID>&interviewUID= etc.</a>";
	 * @return the interviewDateDisplay
	 */
	public String getInterviewDateDisplay() {
		return interviewDateDisplay;
	}

	/**
	 * @param interviewDateDisplay the interviewDateDisplay to set
	 */
	public void setInterviewDateDisplay(String interviewDateDisplay) {
		this.interviewDateDisplay = interviewDateDisplay;
	}


	/**
	 * @return the interviewTypeCd
	 */
	public String getInterviewTypeCd() {
		return interviewTypeCd;
	}

	/**
	 * @param interviewTypeCd the interviewTypeCd to set
	 */
	public void setInterviewTypeCd(String interviewTypeCd) {
		this.interviewTypeCd = interviewTypeCd;
	}

	/**
	 * Get the Interview Type Desc i.e. Initial/Original
	 * @return the interviewTypeDesc
	 */
	public String getInterviewTypeDesc() {
		return interviewTypeDesc;
	}

	/**
	 * @param interviewTypeDesc the interviewTypeDesc to set
	 */
	public void setInterviewTypeDesc(String interviewTypeDesc) {
		this.interviewTypeDesc = interviewTypeDesc;
	}

	/**
	 * @return the interviewStatusCd
	 */
	public String getInterviewStatusCd() {
		return interviewStatusCd;
	}

	/**
	 * @param interviewStatusCd the interviewStatusCd to set
	 */
	public void setInterviewStatusCd(String interviewStatusCd) {
		this.interviewStatusCd = interviewStatusCd;
	}

	/**
	 * @return the interviewStatusDesc
	 */
	public String getInterviewStatusDesc() {
		return interviewStatusDesc;
	}

	/**
	 * @param interviewStatusDesc the interviewStatusDesc to set
	 */
	public void setInterviewStatusDesc(String interviewStatusDesc) {
		this.interviewStatusDesc = interviewStatusDesc;
	}

	/**
	 * @return the interviewLocCd
	 */
	public String getInterviewLocCd() {
		return interviewLocCd;
	}

	/**
	 * @param interviewLocCd the interviewLocCd to set
	 */
	public void setInterviewLocCd(String interviewLocCd) {
		this.interviewLocCd = interviewLocCd;
	}

	/**
	 * @return the interviewLocDesc
	 */
	public String getInterviewLocDesc() {
		return interviewLocDesc;
	}

	/**
	 * @param interviewLocDesc the interviewLocDisplay to set
	 */
	public void setInterviewLocDesc(String interviewLocDesc) {
		this.interviewLocDesc = interviewLocDesc;
	}

	/**
	 * @return the the900SiteId
	 */
	public String getThe900SiteId() {
		return the900SiteId;
	}

	/**
	 * @param the900SiteId the the900SiteId to set
	 */
	public void setThe900SiteId(String the900SiteId) {
		this.the900SiteId = the900SiteId;
	}

	/**
	 * @return the the900SiteZipCd
	 */
	public String getThe900SiteZipCd() {
		return the900SiteZipCd;
	}

	/**
	 * @param the900SiteZipCd the the900SiteZipCd to set
	 */
	public void setThe900SiteZipCd(String the900SiteZipCd) {
		this.the900SiteZipCd = the900SiteZipCd;
	}

	/**
	 * @return the the900SiteTypeCd
	 */
	public String getThe900SiteTypeCd() {
		return the900SiteTypeCd;
	}

	/**
	 * @param the900SiteTypeCd the the900SiteTypeCd to set
	 */
	public void setThe900SiteTypeCd(String the900SiteTypeCd) {
		this.the900SiteTypeCd = the900SiteTypeCd;
	}

	/**
	 * @return the the900Interventions
	 */
	public String getThe900Interventions() {
		return the900Interventions;
	}

	/**
	 * @param the900Interventions the the900Interventions to set
	 */
	public void setThe900Interventions(String the900Interventions) {
		this.the900Interventions = the900Interventions;
	}
        
    
	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public String getRecordStatusCd() {
		return recordStatusCd;
	}

	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}

	public Long getLastChgUserId() {
		return null;
	}

	public void setLastChgUserId(Long aLastChgUserId) {
	}

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

	public Timestamp getLastChgTime() {
		return null;
	}

	public void setLastChgTime(Timestamp aLastChgTime) {
	}

	public Long getAddUserId() {
		return null;
	}

	public void setAddUserId(Long aAddUserId) {
	}

	public String getLastChgReasonCd() {
		return null;
	}

	public void setLastChgReasonCd(String aLastChgReasonCd) {
	}

	public Timestamp getRecordStatusTime() {
		return null;
	}

	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
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

	public void setAddTime(Timestamp aAddTime) {
	}

	public Timestamp getAddTime() {
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

	public Long getProgramJurisdictionOid() {
		return null;
	}

	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
	}

	public String getSharedInd() {
		return null;
	}

	public void setSharedInd(String aSharedInd) {
	}
	public Integer getVersionCtrlNbr() {
		return null;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		return false;
	}
	public String getCheckBoxId() {
		if(associated) {
			checkBoxId = "checked=\"true\"";	
		}
		return checkBoxId;
	}

	public void setCheckBoxId(String checkBoxId) {
		this.checkBoxId = checkBoxId;
	}
 
	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public boolean isAssociated() {
		return associated;
	}

	public void setAssociated(boolean associated) {
		this.associated = associated;
	}
	/**
	 * Get UID of the public health case associated with this Interview
	 * @return the interviewUid
	 */
	public Long getPublicHealthCaseUid() {
		return publicHealthCaseUid;
	}

	/**
	 * set public health case assoc with interview
	 * @param publicHealthCase associated with interview
	 * 
	 */
	public void setPublicHealthCaseUid(Long publicHealthCaseUid) {
		this.publicHealthCaseUid = publicHealthCaseUid;
	}
	/**
	 * Get Add Reason Cd  - if set indicates starting (not coinfection) interview.
	 * @return addReasonCd assoc with Interview
	 */
	public String getAddReasonCd() {
		return addReasonCd;
	}
	/**
	 * Get Add Reason Cd  - if set indicates starting (not coinfection) interview.
	 * @param addReasonCd assoc with Interview
	 */
	public void setAddReasonCd(String addReasonCd) {
		this.addReasonCd = addReasonCd;
	}
}
