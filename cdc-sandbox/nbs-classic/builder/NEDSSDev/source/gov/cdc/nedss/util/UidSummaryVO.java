package gov.cdc.nedss.util;

import java.sql.*;
import java.util.*;

import gov.cdc.nedss.systemservice.util.*;
/**
 * <p>Title:UidSummaryVO</p>
 * <p>Description: UidSummaryVO class</p>
 * <p>Copyright: CSC Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Chris Hanson
 * @version 1.0
 * @update: Pradeep Kumar Sharma
 * @version: 4.5
 */

public class UidSummaryVO
    extends AbstractVO
    implements  RootDTInterface {
 private Long uid;
 private Timestamp addTime;
 private Long linkingUid;
 private String uniqueMapKey;
 
 private Timestamp statusTime;//(Release 4.5) extended to populate Investigation create time
 private String addReasonCd;
  public UidSummaryVO() {
  }

 public Long getUid()
 {
   return uid;
 }
 public void setUid (Long aUid)
 {
   uid= aUid;
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
       return null;
     }


     /**
      * Sets the value of the jurisdictionCd property.
      *
      * @param aJurisdictionCd the new value of the jurisdictionCd property
      */
     public void setJurisdictionCd(String aJurisdictionCd)
     {
     }

     /**
      */
     public Integer getVersionCtrlNbr()
     {
        return null;
     }
     /**
    * @return java.lang.String
    */
   public String getSharedInd()
   {
    return null;
   }

   /**
    * @param aSharedInd
    */
   public void setSharedInd(String aSharedInd)
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
* A setter for add time
*/
public void setAddTime(java.sql.Timestamp aAddTime)
{
 this.addTime=aAddTime;
}

/**
* A getter for add time
*/
public Timestamp getAddTime()
{
 return this.addTime;
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
	 this.statusTime = aStatusTime;
 }
 /**
  * @param aStatusTime
  */
 public Timestamp getStatusTime()
 {
   return statusTime;
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
   return null;
 }
 public void setRecordStatusCd (String aRecordStatusCd){

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
 public String getLocalId()
 {
   return null;
 }

 public void setLocalId(String aLocalId)
 {

 }

/**
 * @return the addReasonCd
 */
public String getAddReasonCd() {
	return addReasonCd;
}

/**
 * @param addReasonCd the addReasonCd to set
 */
public void setAddReasonCd(String addReasonCd) {
	this.addReasonCd = addReasonCd;
}

public Long getLinkingUid() {
	return linkingUid;
}

public void setLinkingUid(Long linkingUid) {
	this.linkingUid = linkingUid;
}

public String getUniqueMapKey() {
	return uniqueMapKey;
}

public void setUniqueMapKey(String uniqueMapKey) {
	this.uniqueMapKey = uniqueMapKey;
}




}
