/**
* Name:		ParticipationTypeVO.java
* Description:	This class is for the Participation Types
*               found in the SRT Participation Type table and used by PageBuilder
* Copyright:	Copyright (c) 2013
* Company: 	Leidos
* @author	Gregory Tucker
* @version	1.0
*/


package gov.cdc.nedss.systemservice.vo;


import java.util.*;
import java.io.*;
import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

import gov.cdc.nedss.systemservice.util.*;

public class ParticipationTypeVO implements Serializable, RootDTInterface, Comparable<ParticipationTypeVO>
{

    private String actClassCd;
    private String subjectClassCd;
    private String typeCd;
    private String typeDescTxt;
    private String questionIdentifier;


    public ParticipationTypeVO()
    {
    }

    public ParticipationTypeVO(String actClassCd, String subjectClassCd, String typeCd, String typeDescTxt, String questionIdentifier)
    {
        this.actClassCd = actClassCd; //Case
        this.subjectClassCd = subjectClassCd;//PSN or ORG
        this.typeCd = typeCd; //InvestgrOfPHC, HospOfADT, etc.
        this.typeDescTxt = typeDescTxt; //Organization as Admitting Hospital  in Investigation
        this.questionIdentifier = questionIdentifier; //INV184
    }
    /**
     * Get the Act Class Code i.e. CASE
	* @return actClassCd
     */
    public String getActClassCd()
    {
        return actClassCd;
    }
     /**
     * Set the Act Class Code
     *
     */
    public void setActClassCd(String aActClassCd)
    {
         actClassCd = aActClassCd;
    }

    /**
     * Get the Subject Class Code i.e. ORG or PSN
	* @return actClassCd
     */
    public String getSubjectClassCd()
    {
        return subjectClassCd;
    }
     /**
     * Set the Subject Class Code
     *
     */
    public void setSubjectClassCd(String aSubjectClassCd)
    {
    	subjectClassCd = aSubjectClassCd;
    }
    
    /**
     * Get the Type Cd i.e. InvestgrOfPHC
	* @return typeCd
     */

    public String getTypeCd()
    {
        return typeCd;
    }
     /**
     * Set the typeCd
     *
     */

    public void setTypeCd( String aTypeCd)
    {
        typeCd = aTypeCd;
    }

    /**
     * Get typeDescTxt i.e. Person as  Investigator in Investigation
	* @return typeDescTxt
     */
    public String getTypeDescTxt()
    {
        return typeDescTxt;
    }
    /**
     * Set typeDescTxt
     *
     */
    public void setTypeDescTxt(String aTypeDescTxt)
    {
         typeDescTxt = aTypeDescTxt;
    }
    /**
     * Get Question Identifier i.e. INV180
	* @return questionIdentifier
     */
    public String getQuestionIdentifier()
    {
        return questionIdentifier;
    }
    /**
     * Set Question Identifier
     *
     */
    public void setQuestionIdentifier(String aQuestionIdentifier)
    {
        questionIdentifier = aQuestionIdentifier;
    }
    /**
     * Get Investigation Form Code
	* @return investigationFormCd
     */
    public String getInvestigationFormCd()
    {
        return null;
    }
    /**
     * Set Investigation Form Code
     *
     */
    public void  setInvestigationFormCd(String aInvestigationFormCd)
    {

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

   public int compareTo(ParticipationTypeVO o) {
           return getTypeCd().compareTo( o.getTypeCd() );
       }


}//end of ParticipationTypesVO class
