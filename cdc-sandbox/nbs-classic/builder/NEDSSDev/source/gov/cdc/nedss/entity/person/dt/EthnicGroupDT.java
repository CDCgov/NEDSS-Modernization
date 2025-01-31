/**
 * Title: EthnicGroupDT helper class.
 * Description: A helper class for ethnic group data table object
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.person.dt;
import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

public class EthnicGroupDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;
   private String progAreaCd = null;
   private String jurisdictionCd = null;
   private Long programJurisdictionOid = null;
   private String sharedInd = null;
   private boolean itDirty = false;
   private boolean itNew = true;
   private boolean itDelete = false;


   /**
    * Access method for the progAreaCd property.
    * @return   the current value of the progAreaCd property
    */
   public String getProgAreaCd()
   {
      return progAreaCd;
   }

   /**
    * Sets the value of both progAreaCd and itDirty properties
    * @param aProgAreaCd the new value of the progAreaCd property
    */
   public void setProgAreaCd(String aProgAreaCd)
   {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
   }

   /**
    * Access method for the jurisdictionCd property.
    * @return   the current value of the jurisdictionCd property
    */
   public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }

   /**
    * Sets the value of both jurisdictionCd and itDirty properties
    * @param aJurisdictionCd the new value of the jurisdictionCd property
    */
   public void setJurisdictionCd (String aJurisdictionCd )
   {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
   }

   /**
    * Access method for the programJurisdictionOid property.
    * @return   the current value of the programJurisdictionOid property
    */
   public Long getProgramJurisdictionOid ()
   {
      return programJurisdictionOid;
   }

   /**
    * Sets the value of both programJurisdictionOid and itDirty properties
    * @param aProgramJurisdictionOid the new value of the programJurisdictionOid property
    */
   public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
   {
      programJurisdictionOid = aProgramJurisdictionOid;
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

   /**
    * This method compares two objects and returns the results
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return   the result of the comparison
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( EthnicGroupDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
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


}
