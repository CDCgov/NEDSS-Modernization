/**
* Name:		ProgramAreaVO.java
* Description:	This abstract class provides a list of objects returned to the web tier
*               in response to a search request.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Shailender Rachamalla & NEDSS Development Team
* @version	1.0
*/


package gov.cdc.nedss.systemservice.nbssecurity;


import java.util.*;
import java.io.*;
import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

import gov.cdc.nedss.systemservice.util.*;

public class ProgramAreaVO implements Serializable, RootDTInterface, Comparable
{

    private String conditionCd;
    private String conditionShortNm;
    private String stateProgAreaCode;
    private String stateProgAreaCdDesc;
    private String investigationFormCd;

    public ProgramAreaVO()
    {
    }

    public ProgramAreaVO(String conditionCd, String conditionShortNm, String stateProgAreaCode, String stateProgAreaCdDesc, String investigationFormCd)
    {
        this.conditionCd = conditionCd;
        this.conditionShortNm = conditionShortNm;
        this.stateProgAreaCode = stateProgAreaCode;
        this.stateProgAreaCdDesc = stateProgAreaCdDesc;
        this.investigationFormCd = investigationFormCd;
    }
    /**
     * Get the Condition Code
	* @return conditionCd
     */
    public String getConditionCd()
    {
        return conditionCd;
    }
     /**
     * Set the Condition Code
     *
     */
    public void setConditionCd(String aConditionCd)
    {
         conditionCd = aConditionCd;
    }

    /**
     * Get the Condition Short Name
	* @return conditionShortNm
     */

    public String getConditionShortNm()
    {
        return conditionShortNm;
    }
     /**
     * Set the Condition Short Name
     *
     */

    public void setConditionShortNm( String aConditionShortNm)
    {
        conditionShortNm = aConditionShortNm;
    }

    /**
     * Get State porgrame Area Code
	* @return stateProgAreaCode
     */
    public String getStateProgAreaCode()
    {
        return stateProgAreaCode;
    }
    /**
     * Set State porgrame Area Code
     *
     */
    public void setStateProgAreaCode(String aStateProgAreaCode)
    {
         stateProgAreaCode = aStateProgAreaCode;
    }
    /**
     * Get State Programe Area Code Description
	* @return stateProgAreaCdDesc
     */
    public String getStateProgAreaCdDesc()
    {
        return stateProgAreaCdDesc;
    }
    /**
     * Set State Programe Area Code Description
     *
     */
    public void setStateProgAreaCdDesc(String aStateProgAreaCdDesc)
    {
        stateProgAreaCdDesc = aStateProgAreaCdDesc;
    }
    /**
     * Get Investigation Form Code
	* @return investigationFormCd
     */
    public String getInvestigationFormCd()
    {
        return investigationFormCd;
    }
    /**
     * Set Investigation Form Code
     *
     */
    public void  setInvestigationFormCd(String aInvestigationFormCd)
    {
        investigationFormCd = aInvestigationFormCd ;
    }

       /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    */
   public boolean isItDirty()
   {
    return false;
   }

   /**
    * @param itNew
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    */
   public boolean isItNew()
   {
    return false;
   }

   /**
    * @param itDelete
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    */
   public boolean isItDelete()
   {
    return false;
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
    */
   public Integer getVersionCtrlNbr()
   {
      return null;
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

   public int compareTo(Object o) {
           return getConditionShortNm().compareTo( ((ProgramAreaVO) o).getConditionShortNm() );
       }


}//end of ProgramAreaVO class
