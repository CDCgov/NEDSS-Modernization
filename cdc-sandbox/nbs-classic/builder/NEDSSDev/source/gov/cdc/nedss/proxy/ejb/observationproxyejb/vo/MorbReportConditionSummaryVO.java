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

public class MorbReportConditionSummaryVO
    extends AbstractVO
    implements ReportSummaryInterface, RootDTInterface {

  private String localId;
  private boolean isTouched;
  private boolean isAssociated;
  private Timestamp dateReceived;
  private String condition;
  private Timestamp activityFromTime;
  private String recordStatusCd;
  private String reportType;
  private String type;
  private String programArea;
  private String jurisdiction;
  private String jurisdictionCd;
  private String status;
  private Long observationUid;
  private String sharedInd;
  private Long personUid;
  private String lastNm;
  private String firstNm;
  private Long personParentUid;
  private String currSexCd;
  private String personLocalId;
  private Timestamp birthTime;
  private String conditionDscTxt;

	public Timestamp getBirthTime() {
		return birthTime;
	}

	public void setBirthTime(Timestamp birthTime) {
		this.birthTime = birthTime;
	}

	public MorbReportConditionSummaryVO() {
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

  public void setDateReceived(Timestamp aDateReceived) {
    dateReceived = aDateReceived;
  }

	public String getCondition(){
		return condition;
  }

	public void setCondition(String condition){
		this.condition = condition;
	}
	public String getReportType(){
		return this.reportType;
	}

	public void setReportType(String reportType){
		this.reportType = reportType;
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
      * Access method for the jurisdictionCd property.
      *
      * @return   the current value of the jurisdictionCd property
      */
     public String getJurisdictionCd()
     {
    	 return this.jurisdictionCd;
     }


     /**
      * Sets the value of the jurisdictionCd property.
      *
      * @param aJurisdictionCd the new value of the jurisdictionCd property
      */
     public void setJurisdictionCd(String jurisdictionCd)
     {
 		this.jurisdictionCd = jurisdictionCd;
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
 * @return java.lang.Long
 */
public Long getUid()
{
 return null;
}
 public void setUid(Long aUid)
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
 /**
  * @return java.lang.String
  */
 public String getProgAreaCd()
 {
    return null;
 }

 /**
  * @param aProgAreaCd
  */
 public void setProgAreaCd(String aProgAreaCd)
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
  public Long getObservationUid() {
    return observationUid;
  }
  public void setObservationUid(Long observationUid) {
    this.observationUid = observationUid;
  }

public String getSharedInd() {
	return sharedInd;
}

public void setSharedInd(String sharedInd) {
	this.sharedInd = sharedInd;
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

public String getCurrSexCd() {
	return currSexCd;
}

public void setCurrSexCd(String currSexCd) {
	this.currSexCd = currSexCd;
}

public String getPersonLocalId() {
	return personLocalId;
}

public void setPersonLocalId(String personLocalId) {
	this.personLocalId = personLocalId;
}

public String getConditionDscTxt() {
	return conditionDscTxt;
}

public void setConditionDscTxt(String conditionDscTxt) {
	this.conditionDscTxt = conditionDscTxt;
}



 /**
  * Sets the value of the jurisdictionCd property.
  *
  * @param aMPRUid the new value of the MPRUid property
  */
	

}