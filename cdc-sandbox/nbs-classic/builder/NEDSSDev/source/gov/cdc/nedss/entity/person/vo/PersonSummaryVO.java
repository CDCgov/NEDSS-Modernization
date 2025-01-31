
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author: pradeep sharma
 * @version 1.0
 */
package gov.cdc.nedss.entity.person.vo;


import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

public class PersonSummaryVO extends AbstractVO
{
   private String localId;
   private String firstName;
   private String lastName;
   private Timestamp DOB;
   private String currentSex;
   private Long personUid;
   private String defaultJurisdictionCd;
   private String countryOfBirth;
   /**
    * @roseuid 3D1203D00136
    */
   public PersonSummaryVO()
   {

   }

   /**
    * @return Long
    * @roseuid 3D10BCEE00F1
    */
   public Long getPersonUid()
   {
    return personUid;
   }

   public void setPersonUid(Long aPersonUid)
   {
    this.personUid = aPersonUid;
    setItDirty(true);
   }


   /**
    * @return String
    * @roseuid 3D10BD420214
    */
   public String getLocalId()
   {
    return localId;
   }

   public void setLocalId(String aLocalId)
   {
    this.localId = aLocalId;
    setItDirty(true);
   }


   /**
    * @return String
    * @roseuid 3D10BDA30047
    */
   public String getFirstName()
   {
    return firstName;
   }

   public void setFirstName(String aFirstName)
   {
    this.firstName = aFirstName;
    setItDirty(true);
   }

   /**
    * @return String
    * @roseuid 3D10BDC10344
    */
   public String getLastName()
   {
    return lastName;
   }

   public void setLastName(String aLastName)
   {
    this.lastName = aLastName;
    setItDirty(true);
   }


   /**
    * @return Timestamp
    * @roseuid 3D10BDCB02EE
    */
   public Timestamp getDOB()
   {
    return DOB;
   }

   public void setDOB(Timestamp aDOB)
   {
    this.DOB = aDOB;
   }

   /**
    * @return String
    * @roseuid 3D10BDE302B6
    */
   public String getCurrentSex()
   {
    return currentSex;
   }

   public void setCurrentSex(String aCurrentSex)
   {
    this.currentSex = aCurrentSex;
   }


   /**
    * @return String
    * @roseuid 3D10DA0903A8
    */
   public String getDefaultJurisdictionCd()
   {
    return defaultJurisdictionCd;
   }

   public void setDefaultJurisdictionCd(String aDefaultJurisdictionCd)
   {
    this.defaultJurisdictionCd = aDefaultJurisdictionCd;
   }


   public String getCountryOfBirth()
   {
    return countryOfBirth;
   }

   public void setCountryOfBirth(String aCountryOfBirth)
   {
    this.countryOfBirth = aCountryOfBirth;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3D120C45016B
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3D120C45017F
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D120C45018A
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3D120C450193
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D120C45019D
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3D120C45019E
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3D120C4501A8
    */
   public boolean isItDelete()
   {
    return true;
   }
}