package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * <p>Title:LabReportSummaryVO</p>
 * <p>Description: LabReportSummaryVO class</p>
 * <p>Copyright: CSC Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

public class LabReportSummaryVO extends AbstractVO implements ReportSummaryInterface, RootDTInterface,Comparable {
	private static final long serialVersionUID = 1L;
  private boolean isTouched;
  private boolean isAssociated;
  private Timestamp dateReceived;
  private String dateReceivedS;
  private Integer versionCtrlNbr;
  private Timestamp dateCollected;
  private Timestamp activityFromTime;
  private String type;
  private String programArea;
  private String jurisdiction;
  private String jurisdictionCd;
  private String status;
  private String recordStatusCd;
  private Long observationUid;
  private String patientFirstName;
  private String patientLastName;
  private String personLocalId;
  private Collection<Object> theResultedTestSummaryVOCollection;
  private Collection<Object> invSummaryVOs;
  private String orderedTest;
  private Long MPRUid;
  private String cdSystemCd;
  private String actionLink;
  private String resultedTestString;
  private String reportingFacility;
  private String specimenSource;
  private String[] selectedcheckboxIds;
  private String checkBoxId;
  // Added these fields for ER 16368 - Start
  private String providerFirstName = "";
  private String providerLastName = "";
  private String providerSuffix = "";
  private String providerPrefix = "";
  private String providerDegree = "";
  private String providerUid = "";
  private String degree ;
  private String accessionNumber ;
  private boolean isLabFromMorb = false;
  private boolean isReactor = false;
  private String electronicInd;
  private Map<Object,Object> associationsMap;
  private String processingDecisionCd;
  private String disabled = "";
  private ProviderDataForPrintVO providerDataForPrintVO;
  private boolean isLabFromDoc;
  private Long uid;
  private String sharedInd;
  private String progAreaCd;
  private String localId;
  private Long personUid;
  private String lastNm;
  private String firstNm;
  private Long personParentUid;
  private String currSexCd;
  private String orderingFacility;


  

public String getProgAreaCd() {
	return progAreaCd;
}

public void setProgAreaCd(String progAreaCd) {
	this.progAreaCd = progAreaCd;
}

public ProviderDataForPrintVO getProviderDataForPrintVO() {
		return providerDataForPrintVO;
	}

	public void setProviderDataForPrintVO(ProviderDataForPrintVO providerDataForPrintVO) {
		this.providerDataForPrintVO = providerDataForPrintVO;
	}
public String getCurrSexCd() {
	return currSexCd;
}

public void setCurrSexCd(String currSexCd) {
	this.currSexCd = currSexCd;
}


public Timestamp getBirthTime() {
	return birthTime;
}

public void setBirthTime(Timestamp birthTime) {
	this.birthTime = birthTime;
}

private Timestamp birthTime;
  
  public String getElectronicInd() {
	return electronicInd;
}

public void setElectronicInd(String electronicInd) {
	this.electronicInd = electronicInd;
}

  public Map<Object, Object> getAssociationsMap() {
	return associationsMap;
}

public void setAssociationsMap(Map<Object, Object> associationsMap) {
	this.associationsMap = associationsMap;
}

public LabReportSummaryVO() {
  }

  public String getLocalId (){
    return localId;
  }
  public void setLocalId (String aLocalId){
    localId = aLocalId;
  }

  public String getProgramArea (){
    return programArea;
  }
  public void setProgramArea (String aProgramArea){
    programArea = aProgramArea;
  }

  public String getJurisdiction (){
    return jurisdiction;
  }

  public void setJurisdiction (String aJurisdiction){
    jurisdiction = aJurisdiction;
  }

  public String getRecordStatusCd (){
    return recordStatusCd;
  }
  public void setRecordStatusCd (String aRecordStatusCd){
    recordStatusCd = aRecordStatusCd;
  }

  public String getStatus (){
    return status;
  }
  public void setStatus(String aStatus){
    status = aStatus;
  }

  public String getType (){
    return type;
  }
  public void setType (String aType){
    type = aType;
  }

  public String getOrderedTest (){
    return orderedTest;
  }
  public void setOrderedTest(String aOrderedTest){
    this.orderedTest = aOrderedTest;
  }

  public Collection<Object> getTheResultedTestSummaryVOCollection() {
    return theResultedTestSummaryVOCollection;
  }

  public void setTheResultedSummaryTestVOCollection(Collection<Object> aTheResultedTestSummaryVOCollection) {
    theResultedTestSummaryVOCollection  = aTheResultedTestSummaryVOCollection;
  }
  public boolean getIsTouched() {
    return isTouched;
  }

  public void setItTouched(boolean touched) {
    isTouched = touched;
  }

  public boolean getIsAssociated() {
    return isAssociated;
  }

  public void setItAssociated(boolean associated) {
    isAssociated = associated;
  }

  public Long getObservationUid() {
    return observationUid;
  }

  public void setObservationUid(Long aObservationUid) {
    observationUid = aObservationUid;
  }

  public void setPatientFirstName(String pFirstName) {
          patientFirstName = pFirstName;
  }

  public String getPatientFirstName(){
                      return this.patientFirstName;
  }

  public void setPatientLastName(String pLastName) {
          patientLastName = pLastName;
  }

  public String getPatientLastName(){
                      return this.patientLastName;
  }


  public Timestamp getDateReceived() {
    return dateReceived;
  }

  public String getDateReceivedS() {
	    return dateReceivedS;
	  }

	    
  public void setDateReceived(Timestamp aDateReceived) {
    dateReceived = aDateReceived;
  }
  
  public void setDateReceived(String aDateReceived) {
	    dateReceivedS = aDateReceived;
	  }
	  
  public Timestamp getDateCollected() {
    return dateCollected;
  }

  public void setDateCollected(Timestamp aDateCollected) {
    dateCollected = aDateCollected;
  }

  public Timestamp getActivityFromTime() {
    return activityFromTime;
  }

  public void setActivityFromTime(Timestamp aActivityFromTime) {
   activityFromTime = aActivityFromTime;
  }

  public boolean isEqual(java.lang.Object objectname1,
                         java.lang.Object objectname2, Class<?> voClass) {
    return true;
  }

  public void setItDirty(boolean itDirty) {
  }

  public boolean isItDirty() {
    return true;
  }

  public void setItNew(boolean itNew) {
  }

  public boolean isItNew() {
    return true;
  }

  public void setItDelete(boolean itDelete) {
  }

  public boolean isItDelete() {
    return true;
  }
  /**
 
      /**
   * A setter for add time
   */
   public void setAddTime(java.sql.Timestamp aAddTime)
   {

   }

   /**
   * A getter for add time
   */
   public Timestamp getAddTime()
   {
    return null;
   }
      /**
   * Access method for the lastChgTime property.
   *
   * @return   the current value of the lastChgTime property
   */
  public Timestamp getLastChgTime()
  {
     return null;
  }

  /**
   * Sets the value of the lastChgTime property.
   *
   * @param aLastChgTime the new value of the lastChgTime property
   */
  public void setLastChgTime(Timestamp aLastChgTime)
  {

  }


/**
 * @return java.lang.Long
 */
public Long getProgramJurisdictionOid()
{
 return null;
}

/**
 * @param aProgramJurisdictionOid
 */
public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
{

}

/**
 * @return java.sql.Timestamp
 */
public Timestamp getRecordStatusTime()
{
 return null;
}

/**
 * @param aRecordStatusTime
 */
public void setRecordStatusTime(Timestamp aRecordStatusTime)
{

}


/**
 * @return java.sql.Timestamp
 */
public Timestamp getStatusTime()
{
 return null;
}

/**
 * @param aStatusTime
 */
public void setStatusTime(Timestamp aStatusTime)
{

}

/**
 * @return java.lang.String
 */
public String getSuperclass()
{
 return null;
}

/**
 * @return java.lang.Long
 */

 public Long getLastChgUserId()
 {
  return null;
 }

 /**
  * @param aLastChgUserId
  */
 public void setLastChgUserId(Long aLastChgUserId)
 {

 }


 /**
  * @return java.lang.String
  */
 /**
  * @return java.lang.Long
  */
 public Long getAddUserId()
 {
  return null;
 }

 /**
  * @param aAddUserId
  */
 public void setAddUserId(Long aAddUserId)
 {

 }

 /**
  * @return java.lang.String
  */
 public String getLastChgReasonCd()
 {
  return null;
 }

 /**
  * @param aLastChgReasonCd
  */
 public void setLastChgReasonCd(String aLastChgReasonCd)
 {

 }
 /**
  * Access method for the statusCd property.
  *
  * @return   the current value of the statusCd property
  */
 public String getStatusCd()
 {
   return null;
 }

 /**
  * Sets the value of the statusCd property.
  *
  * @param aStatusCd the new value of the statusCd property
  */
 public void setStatusCd(String aStatusCd)
 {
 }


    public Long getMPRUid()
    {
      return MPRUid;
    }


    /**
     * Sets the value of the jurisdictionCd property.
     *
     * @param aMPRUid the new value of the MPRUid property
     */
    public void setMPRUid(Long aMPRUid)
    {
      this.MPRUid = aMPRUid;
    }
  public String getCdSystemCd() {
    return cdSystemCd;
  }
  public void setCdSystemCd(String cdSystemCd) {
    this.cdSystemCd = cdSystemCd;
  }

public String getActionLink() {
	return actionLink;
}

public void setActionLink(String actionLink) {
	this.actionLink = actionLink;
}

public String getResultedTestString() {
	return resultedTestString;
}

public void setResultedTestString(String resultedTestString) {
	this.resultedTestString = resultedTestString;
}

public String getReportingFacility() {
	return reportingFacility;
}

public void setReportingFacility(String reportingFacility) {
	this.reportingFacility = reportingFacility;
}

public String getSpecimenSource() {
	return specimenSource;
}

public void setSpecimenSource(String specimenSource) {
	this.specimenSource = specimenSource;
}

public String[] getSelectedcheckboxIds() {
	return selectedcheckboxIds;
}

public void setSelectedcheckboxIds(String[] selectedcheckboxIds) {
	this.selectedcheckboxIds = selectedcheckboxIds;
}

public String getCheckBoxId() {
	if(isAssociated) {
		checkBoxId = "checked=\"true\"";
	}
	return checkBoxId;
}

public void setCheckBoxId(String checkBoxId) {
	this.checkBoxId = checkBoxId;
}

public String getProviderFirstName() {
	return providerFirstName;
}

public void setProviderFirstName(String providerFirstName) {
	this.providerFirstName = providerFirstName;
}

public String getProviderLastName() {
	return providerLastName;
}

public void setProviderLastName(String providerLastName) {
	this.providerLastName = providerLastName;
}

public String getProviderSuffix() {
	return providerSuffix;
}

public void setProviderSuffix(String providerSuffix) {
	this.providerSuffix = providerSuffix;
}

public String getProviderPrefix() {
	return providerPrefix;
}

public void setProviderPrefix(String providerPrefix) {
	this.providerPrefix = providerPrefix;
}

public String getAccessionNumber() {
	return accessionNumber;
}

public void setAccessionNumber(String accessionNumber) {
	this.accessionNumber = accessionNumber;
}

public String getProviderFullName(){
	StringBuffer sb = new StringBuffer();
	if(this.providerPrefix !=null && this.providerPrefix.trim().length()>0){
		sb.append(this.providerPrefix);
		sb.append(" ");
	}
	if(this.providerFirstName !=null && this.providerFirstName.trim().length()>0){
		sb.append(this.providerFirstName);
		sb.append(" ");
	}
	if(this.providerLastName !=null && this.providerLastName.trim().length()>0  ){
		sb.append(this.providerLastName);
		sb.append(" ");
	}
	if(this.degree !=null && this.degree.trim().length()>0){
		sb.append(this.degree);
	}
	return sb.toString() ;
}

public String getDegree() {
	return degree;
}

public void setDegree(String degree) {
	this.degree = degree;
}

public boolean isLabFromMorb() {
	return this.isLabFromMorb;
}

public void setLabFromMorb(boolean isLabFromMorb) {
	this.isLabFromMorb = isLabFromMorb;
}

/**
 * @return the processingDecisionCd
 */
public String getProcessingDecisionCd() {
	return processingDecisionCd;
}

/**
 * @param processingDecisionCd the processingDecisionCd to set
 */
public void setProcessingDecisionCd(String processingDecisionCd) {
	this.processingDecisionCd = processingDecisionCd;
}

/**
 * @return the isReactor
 */
public boolean isReactor() {
	return isReactor;
}

/**
 * @param isReactor the isReactor to set
 */
public void setReactor(boolean isReactor) {
	this.isReactor = isReactor;
}

/**
 * @return the disabled
 */
public String getDisabled() {
	return disabled;
}

/**
 * @param disabled the disabled to set
 */
public void setDisabled(String disabled) {
	this.disabled = disabled;
}
public String getProviderFullNameForPrint(){
	StringBuffer sb = new StringBuffer();
	if(providerPrefix != null && !providerPrefix.isEmpty()){
		sb.append(providerPrefix);
		sb.append(" ");
	}
	if(this.providerFirstName !=null && this.providerFirstName.trim().length()>0){
		sb.append(this.providerFirstName);
		sb.append(" ");
	}
	
	if(this.providerLastName !=null && this.providerLastName.trim().length()>0  ){
		sb.append(this.providerLastName);
		if(providerSuffix != null && !providerSuffix.isEmpty()){
			sb.append(" ");
			sb.append(providerSuffix);
		}
		sb.append("/");
	}
	
	if(this.degree !=null && this.degree.trim().length()>0){
		sb.append(this.degree);
	}
	
	return sb.toString() ;
}

@Override
public int compareTo(Object arg0) {
	int returnInt = 0;
	if(arg0 instanceof LabReportSummaryVO){
		if(this.getDateCollected() != null && ((LabReportSummaryVO)arg0).getDateCollected() != null){
		returnInt = this.getDateCollected().compareTo(((LabReportSummaryVO)arg0).getDateCollected());
		}else if(this.getDateCollected() != null && ((LabReportSummaryVO)arg0).getDateCollected() == null){
			returnInt=-1;
		}else if(this.getDateCollected() == null && ((LabReportSummaryVO)arg0).getDateCollected() != null){
			returnInt= 1;
		}else if(this.getDateCollected() == null && ((LabReportSummaryVO)arg0).getDateCollected() == null
				){
			returnInt = this.getDateReceived().compareTo(((LabReportSummaryVO)arg0).getDateReceived());
		}
	}
	return returnInt; 
}

public boolean isLabFromDoc() {
	return isLabFromDoc;
}

public void setLabFromDoc(boolean isLabFromDoc) {
	this.isLabFromDoc = isLabFromDoc;
}

public Long getUid() {
	return uid;
}

public void setUid(Long uid) {
	this.uid = uid;
}

public String getProviderDegree() {
	return providerDegree;
}

public void setProviderDegree(String providerDegree) {
	this.providerDegree = providerDegree;
}

public String getProviderUid() {
	return providerUid;
}

public void setProviderUid(String providerUid) {
	this.providerUid = providerUid;
}

public Collection<Object> getInvSummaryVOs() {
	return invSummaryVOs;
}

public void setInvSummaryVOs(Collection<Object> invSummaryVOs) {
	this.invSummaryVOs = invSummaryVOs;
}

public String getPersonLocalId() {
	return personLocalId;
}

public void setPersonLocalId(String personLocalId) {
	this.personLocalId = personLocalId;
}

public void setVersionCtrlNbr(Integer versionCtrlNbr) {
	this.versionCtrlNbr = versionCtrlNbr;
}
public Integer getVersionCtrlNbr()
{
   return versionCtrlNbr;
}
	
public String getLocalIdForUpdatedAndNewDoc(boolean displayNew){
	if(getVersionCtrlNbr()!=null && getVersionCtrlNbr().intValue()>1)
		return "<font color=\"#006600\">"+this.getLocalId()+" (Update)</font>";
	else if(displayNew)
		return this.getLocalId()+" (New)";
	else
		return this.getLocalId();
	
}

public String getLocalIdForUpdatedAndNewDocPrint(boolean displayNew){
	if(getVersionCtrlNbr()!=null && getVersionCtrlNbr().intValue()>1)
		return this.getLocalId()+" (Update)";
	else if(displayNew)
		return this.getLocalId()+" (New)";
	else
		return this.getLocalId();
	
}


public String getLocalIdForUpdatedAndNewDoc(){
	return getLocalIdForUpdatedAndNewDoc(false);
}

public String getLocalIdForUpdatedAndNewDocPrint(){
	return getLocalIdForUpdatedAndNewDocPrint(false);
}
public String getSharedInd() {
	return sharedInd;
}

public void setSharedInd(String sharedInd) {
	this.sharedInd = sharedInd;
}

public String getJurisdictionCd() {
	return jurisdictionCd;
}

public void setJurisdictionCd(String jurisdictionCd) {
	this.jurisdictionCd = jurisdictionCd;
}

public String getPersonlocalId() {
	return personLocalId;
}

public void setPersonlocalId(String personlocalId) {
	personLocalId = personlocalId;
}

public Long getPersonUid() {
	return personUid;
}

public void setPersonUid(Long personUid) {
	this.personUid = personUid;
}

public String getLastNm() {
	return lastNm;
}

public void setLastNm(String lastNm) {
	this.lastNm = lastNm;
}

public String getFirstNm() {
	return firstNm;
}

public void setFirstNm(String firstNm) {
	this.firstNm = firstNm;
}

public Long getPersonParentUid() {
	return personParentUid;
}

public void setPersonParentUid(Long personParentUid) {
	this.personParentUid = personParentUid;
}

public String getOrderingFacility() {
	return orderingFacility;
}

public void setOrderingFacility(String orderingFacility) {
	this.orderingFacility = orderingFacility;
}
}