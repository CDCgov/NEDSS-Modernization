package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import java.sql.*;
import java.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;
/**
 * <p>Title:MorbReportSummaryVO</p>
 * <p>Description: MorbReportSummaryVO class</p>
 * <p>Copyright: CSC Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Chris Hanson
 * @version 1.0
 */

public class MorbReportSummaryVO
    extends AbstractVO
    implements ReportSummaryInterface, RootDTInterface {

  private String localId;
  private boolean isTouched;
  private boolean isAssociated;
  private Timestamp dateReceived;
  private String dateReceivedS;
  private String condition;
  private String conditionDescTxt;
  private Timestamp activityFromTime;
  private Timestamp reportDate;
  private String recordStatusCd;
  private String reportType;
  private String reportTypeDescTxt;
  private String type;
  private String programArea;
  private String jurisdiction;
  private String jurisdictionCd;
  private String status;
  private Long observationUID;
  private String patientFirstName;
  private String patientLastName;
  private String personLocalId;
  private Collection<Object>  theLabReportSummaryVOColl;
  private Collection<Object>  theTreatmentSummaryVOColl;
  private Long MPRUid;
  private String actionLink;
  private String checkBoxId;
  private String providerFirstName = "";
  private String providerLastName = "";
  private String providerSuffix = "";
  private String providerPrefix = "";
  private String providerDegree = "";
  private String providerUid = "";
  private String reportingFacility = "";
  private String sharedInd;
  
  private String degree ;
  private Map<Object,Object> associationsMap;
  private String currSexCd;
  private Timestamp birthTime;
  private String processingDecisionCd;
  private boolean isReactor;
  private String disabled = "";
  private String electronicInd;
  private boolean isMorbFromDoc;
  private Long uid;
  private String progAreaCd;
  
 
  public String getProgAreaCd() {
	return progAreaCd;
}

public void setProgAreaCd(String progAreaCd) {
	this.progAreaCd = progAreaCd;
}

private ProviderDataForPrintVO providerDataForPrintVO;
  

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

public Map<Object, Object> getAssociationsMap() {
	return associationsMap;
}

public void setAssociationsMap(Map<Object, Object> associationsMap) {
	this.associationsMap = associationsMap;
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

public String getDegree() {
	return degree;
}

public void setDegree(String degree) {
	this.degree = degree;
}

  public MorbReportSummaryVO() {
  }

  public String getLocalId(){
		return this.localId;
	}

	public void setLocalId(String localId){
		this.localId = localId;
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

	public Timestamp getDateReceived() {
    return dateReceived;
  }
	public String getDateReceivedS() {
	    return dateReceivedS;
	}

  public void setDateReceived(Timestamp aDateReceived) {
    dateReceived = aDateReceived;
  }
  public void setDateReceived(String  aDateReceived) {
	    dateReceivedS = aDateReceived;
	  }

	public String getCondition(){
		return condition;
  }

	public void setCondition(String condition){
		this.condition = condition;
	}


        public String getConditionDescTxt(){
                return conditionDescTxt;
  }

        public void setConditionDescTxt(String conditionDescTxt){
                this.conditionDescTxt = conditionDescTxt;
        }

	public Timestamp getReportDate(){
		return this.reportDate;
	}

	public void setReportDate(Timestamp reportDate){
		this.reportDate = reportDate;
	}

	public String getReportType(){
		return this.reportType;
	}

	public void setReportType(String reportType){
		this.reportType = reportType;
	}

	public String getReportTypeDescTxt(){
					return this.reportTypeDescTxt;
	}

	public void setReportTypeDescTxt(String aReportTypeDescTxt){
					this.reportTypeDescTxt = aReportTypeDescTxt;
	}

	public String getType(){
		return this.type;
	}

	public void setType(String aType){
		this.type = aType;
	}

	public String getProgramArea(){
		return this.programArea;
	}

	public void setProgramArea(String programArea){
		this.programArea = programArea;
	}

	public String getJurisdiction(){
		return this.jurisdiction;
	}

	public void setJurisdiction(String jurisdiction){
		this.jurisdiction = jurisdiction;
	}

	public String getStatus(){
		return this.status;
	}

	public void setStatus(String status){
		this.status = status;
	}

  public Long getObservationUid() {
    return observationUID;
  }

  public void setObservationUid(Long observationUid) {
    observationUID = observationUid;
  }

	public Collection<Object> getTheLabReportSummaryVOColl(){
		return this.theLabReportSummaryVOColl;
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


	public void setTheLabReportSummaryVOColl(Collection<Object> theLabReportSummaryVOColl){
		this.theLabReportSummaryVOColl = theLabReportSummaryVOColl;
	}

	public Collection<Object>  getTheTreatmentSummaryVOColl(){
		return this.theTreatmentSummaryVOColl;
	}

	public void setTheTreatmentSummaryVOColl(Collection<Object> theTreatmentSummaryVOColl){
		this.theTreatmentSummaryVOColl = theTreatmentSummaryVOColl;
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



     /**
      */
     public Integer getVersionCtrlNbr()
     {
        return null;
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
  * @return java.lang.String
  */
 public String getSuperclass()
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
  * @param aStatusTime
  */
 public Timestamp getStatusTime()
 {
   return null;
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
 public String getRecordStatusCd (){
   return this.recordStatusCd;
 }
 public void setRecordStatusCd (String aRecordStatusCd){
   this.recordStatusCd = aRecordStatusCd;
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

public String getActionLink() {
	return actionLink;
}

public void setActionLink(String actionLink) {
	this.actionLink = actionLink;
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

/**
 * @return the currSexCd
 */
public String getCurrSexCd() {
	return currSexCd;
}

/**
 * @param currSexCd the currSexCd to set
 */
public void setCurrSexCd(String currSexCd) {
	this.currSexCd = currSexCd;
}

/**
 * @return the birthTime
 */
public Timestamp getBirthTime() {
	return birthTime;
}

/**
 * @param birthTime the birthTime to set
 */
public void setBirthTime(Timestamp birthTime) {
	this.birthTime = birthTime;
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
	
	if(this.providerFirstName !=null && this.providerFirstName.trim().length()>0){
		sb.append(this.providerFirstName);
		sb.append(" ");
	}
	if(this.providerLastName !=null && this.providerLastName.trim().length()>0  ){
		sb.append(this.providerLastName);
		sb.append("/");
	}
	if(this.degree !=null && this.degree.trim().length()>0){
		sb.append(this.degree);
	}
	return sb.toString() ;
}

public ProviderDataForPrintVO getProviderDataForPrintVO() {
	return providerDataForPrintVO;
}
public void setProviderDataForPrintVO(ProviderDataForPrintVO providerDataForPrintVO) {
	this.providerDataForPrintVO = providerDataForPrintVO;
}
public String getElectronicInd() {
	return electronicInd;
}
public void setElectronicInd(String electronicInd) {
	this.electronicInd = electronicInd;
}

public boolean isMorbFromDoc() {
	return isMorbFromDoc;
}

public void setMorbFromDoc(boolean isMorbFromDoc) {
	this.isMorbFromDoc = isMorbFromDoc;
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

public String getReportingFacility() {
	return reportingFacility;
}

public void setReportingFacility(String providerReportingFacility) {
	this.reportingFacility = providerReportingFacility;
}


public String getPersonLocalId() {
	return personLocalId;
}

public void setPersonLocalId(String personLocalId) {
	this.personLocalId = personLocalId;
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
}