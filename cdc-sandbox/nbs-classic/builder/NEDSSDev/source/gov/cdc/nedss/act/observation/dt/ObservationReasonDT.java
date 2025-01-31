
package gov.cdc.nedss.act.observation.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

   /**
    * Title:	    ObservationReasonDT is a class
    * Description:  This is a class which sets/gets(retrieves)
    *               all the fields to the database table
    * Copyright:    Copyright (c) 2001
    * Company: 	    Computer Sciences Corporation
    * @author       NEDSS TEAM
    * @version	    1.0
    */

public class ObservationReasonDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;

    private Long observationUid;

    private String reasonCd;

    private String reasonDescTxt;

     private String progAreaCd = null;

     private String jurisdictionCd = null;

     private Long programJurisdictionOid = null;

     private String sharedInd = null;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;

    /**
    * Access method for the ObservationUid.
    *
    * @return Long the current value of the ObservationUid
    */
    public Long getObservationUid()
    {
        return observationUid;
    }


    /**
    * Sets the value of the ObservationUid.
    *
    * @param Long the val
    */
    public void setObservationUid(Long aObservationUid)
    {
        observationUid = aObservationUid;
        setItDirty(true);
    }


   /**
    * Access method for the ReasonCd.
    *
    * @return  String the current value of the ReasonCd
    */
    public String getReasonCd()
    {
        return reasonCd;
    }


    /**
    * Sets the value of the ReasonCd.
    *
    * @param String the aReasonCd
    */
    public void setReasonCd(String aReasonCd)
    {
        reasonCd = aReasonCd;
        setItDirty(true);
    }

      /**
    * Access method for the ReasonDescTxt.
    *
    * @return String  the current value of the ReasonDescTxt
    */
    public String getReasonDescTxt()
    {
        return reasonDescTxt;
    }

       /**
    * Sets the value of the ReasonDescTxt.
    *
    * @param String the aReasonDescTxt
    */
    public void setReasonDescTxt(String aReasonDescTxt)
    {
        reasonDescTxt = aReasonDescTxt;
        setItDirty(true);
    }

      /**
    * Access method for the ProgAreaCd.
    *
    * @return String  the current value of the ProgAreaCd
    */
       public String getProgAreaCd()
   {
      return progAreaCd;
   }

    /**
    * Sets the value of the ProgAreaCd.
    *
    * @param String the aProgAreaCd
    */
      public void setProgAreaCd(String aProgAreaCd)
   {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
   }

     /**
    * Access method for the JurisdictionCd.
    *
    * @return  String  the current value of the JurisdictionCd
    */
       public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }

     /**
    * Sets the value of the JurisdictionCd.
    *
    * @param String the aJurisdictionCd
    */
      public void setJurisdictionCd (String aJurisdictionCd )
   {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
   }



   /**
    * Access method for the ProgramJurisdictionOid.
    *
    * @return Long  the current value of the ProgramJurisdictionOid
    */
       public Long getProgramJurisdictionOid ()
   {
      return programJurisdictionOid;
   }

   /**
    * Sets the value of the ProgramJurisdictionOid.
    *
    * @param Long the aProgramJurisdictionOid
    */
      public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
   {
      programJurisdictionOid = aProgramJurisdictionOid;
      setItDirty(true);
   }


   /**
    * Access method for the SharedInd.
    *
    * @return String  the current value of the SharedInd
    */
       public String getSharedInd()
   {
      return sharedInd;
   }


   /**
    * Sets the value of the SharedInd.
    *
    * @param String the aSharedInd
    */
      public void setSharedInd(String aSharedInd)
   {
      sharedInd = aSharedInd;
      setItDirty(true);
   }


     /**
    * get the value of the boolean property.
    * @param objectname1 Object the object name
    * @param objectname2 Object the object name
    * @param voClass Class the class
    * @return isEqual the value of the boolean value
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( ObservationReasonDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }


  /**
    * Sets the value of the itDirty property.
    *
    * @param boolean itDirty the new value of the ItDirty property
    */
   public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }



  /**
    * get the value of the boolean property.
    *
    * @return ItDirty the value of the boolean value
    */
   public boolean isItDirty() {
     return itDirty;
   }


     /**
    * Sets the value of the ItNew property.
    *
    * @param itNew boolean the new value of the boolean property
    */
   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }


   /**
    * get the value of the itNew property.
    *
    * @return itNew the value of the boolean property
    */
   public boolean isItNew() {
     return itNew;
   }

   /**
    * Sets the value of the ItDelete property.
    *
    * @param itDelete boolean the value of the boolean property
    */
   public boolean isItDelete() {
     return itDelete;
   }

   /**
    * gets the value of the ItDelete property.
    *
    * @return ItDelete the new value of the boolean property
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }


}
