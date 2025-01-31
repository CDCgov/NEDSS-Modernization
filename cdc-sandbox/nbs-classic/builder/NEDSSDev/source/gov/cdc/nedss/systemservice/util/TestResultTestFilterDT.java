package gov.cdc.nedss.systemservice.util;

import gov.cdc.nedss.util.*;
import java.sql.Timestamp;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TestResultTestFilterDT extends AbstractVO implements RootDTInterface
{
  private String labTestCd;
  private String testTypeCd;
  private String conditionCd;
  private String conditionShortNm;
  private String conditionDescTxt;
  private String progAreaCd;
  private String progAreaDexcTxt;
  private String drugTestInd;
  private Long indentLevel;


  private String laboratoryId;
  private String labTestDescTxt;

  private Integer versionCtrlNbr;
  private String sharedInd;


  public TestResultTestFilterDT() {
  }
  /**
   * Sets the value of the itDirty property
   * @param itDirty the new value of the itDirty property
   */
   public void setItDirty(boolean itDirty) {
     this.itDirty = itDirty;
   }

  /**
   * Access method for the itDirty property.
   * @return   the current value of the itDirty property
   */
   public boolean isItDirty() {
    return itDirty;
   }

  /**
   * Sets the value of the itNew property
   * @param itNew the new value of the itNew property
   */
   public void setItNew(boolean itNew) {
    this.itNew = itNew;
   }

  /**
   * Access method for the isItNew property.
   * @return   the current value of the isItNew property
   */
   public boolean isItNew() {
    return itNew;
   }

  /**
   * Access method for the itDelete property.
   * @return   the current value of the itDelete property
   */
   public boolean isItDelete() {
    return itDelete;
   }

  /**
   * Sets the value of the itDelete property
   * @param itDelete the new value of the itDelete property
   */
   public void setItDelete(boolean itDelete) {
     this.itDelete = itDelete;
   }
   /**
    * This method compares two objects and returns the results
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return   the result of the comparison
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((TestResultTestFilterDT)objectname1).getClass();

        NedssUtils compareObjs = new NedssUtils();

        return (compareObjs.equals(objectname1, objectname2, voClass));
    }
    /**
 * Access method for the versionCtrlNbr property.
 * @return   the current value of the versionCtrlNbr property
 */
 public Integer getVersionCtrlNbr()
 {
     return versionCtrlNbr;
 }

/**
 * Sets the value of both versionCtrlNbr and itDirty properties
 * @param aVersionCtrlNbr the new value of the versionCtrlNbr property
 */
 public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
 {
     versionCtrlNbr = aVersionCtrlNbr;
     setItDirty(true);
 }
 /**
   * Access method for the sharedInd property.
   * @return   the current value of the sharedInd property
   */
   public String getSharedInd()
   {
     return sharedInd;
   }

  /**
   * Sets the value of both sharedInd and itDirty properties
   * @param aSharedInd the new value of the sharedInd property
   */
   public void setSharedInd(String aSharedInd)
   {
     sharedInd = aSharedInd;
     setItDirty(true);
   }


   public String getLaboratoryId()
   {
     return laboratoryId;
   }

   public void setIndentLevel(Long aIndentLevel)
   {
     indentLevel = aIndentLevel;
     setItDirty(true);
   }
   public void setIndentLevel(Integer aIndentLevel)
  {
    indentLevel = new Long(aIndentLevel.longValue());
    setItDirty(true);
  }

   public Long getIndentLevel()
   {
     return indentLevel;
   }

   public void setLaboratoryId(String aLaboratoryId)
   {
     laboratoryId = aLaboratoryId;
     setItDirty(true);
   }


   public String getLabTestDescTxt()
   {
     return labTestDescTxt;
   }

   public void setLabTestDescTxt(String aLabTestDescTxt)
   {
     labTestDescTxt = aLabTestDescTxt;
     setItDirty(true);
   }

   public String getProgAreaCd()
   {
     return progAreaCd;
   }

   /**
  * @param aProgAreaCd
  */
 public void setProgAreaCd(String aProgAreaCd)
 {
   progAreaCd = aProgAreaCd;
     setItDirty(true);
 }
 public String geLabTestCd()
 {
   return labTestCd;
 }

 /**
* @param aProgAreaCd
*/
public void setLabTestCd(String aLabTestCd)
{
 labTestCd = aLabTestCd;
   setItDirty(true);
}
 public String getTestTypeCd()
 {
   return testTypeCd;
 }

 /**
* @param aTestTypeCd
*/
public void setTestTypeCd(String aTestTypeCd)
{
 testTypeCd = aTestTypeCd;
   setItDirty(true);
}
 public String getConditionCd()
 {
   return conditionCd;
 }

 /**
* @param aConditionCd
*/
public void setConditionCd(String aConditionCd)
{
 conditionCd = aConditionCd;
   setItDirty(true);
}
 public String getConditionShortNm()
 {
   return conditionShortNm;
 }

 /**
* @param aConditionShortNm
*/
public void setConditionShortNm(String aConditionShortNm)
{
 conditionShortNm = aConditionShortNm;
   setItDirty(true);
}
 public String getConditionDescTxt()
 {
   return conditionDescTxt;
 }

 /**
* @param aConditionDescTxt
*/
public void setConditionDescTxt(String aConditionDescTxt)
{
 conditionDescTxt = aConditionDescTxt;
   setItDirty(true);
}


   public String getProgAreaDexcTxt()
   {
     return progAreaDexcTxt;
   }

   public void setProgAreaDexcTxt(String aProgAreaDexcTxt)
   {
     progAreaDexcTxt = aProgAreaDexcTxt;
     setItDirty(true);
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
   public String getJurisdictionCd()
   {
    return null;
   }

   /**
    * @param aJurisdictionCd
    */
   public void setJurisdictionCd(String aJurisdictionCd)
   {

   }





   /**
    * @return java.lang.String
    */
   public String getLocalId()
   {
    return null;
   }

   /**
    * @param aLocalId
    */
   public void setLocalId(String aLocalId)
   {

   }

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
    * @return java.lang.String
    */
   public String getRecordStatusCd()
   {
    return null;
   }

   /**
    * @param aRecordStatusCd
    */
   public void setRecordStatusCd(String aRecordStatusCd)
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
    * @return java.lang.String
    */
   public String getStatusCd()
   {
    return null;
   }

   /**
    * @param aStatusCd
    */
   public void setStatusCd(String aStatusCd)
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
   public Long getUid()
   {
    return null;
   }
    public void setUid(Long aUid)
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

   public void setDrugTestInd(String aDrugTestInd){
     drugTestInd = aDrugTestInd;
   }

   public String getDrugTestInd(){
     return drugTestInd;
   }



}