

package gov.cdc.nedss.act.observation.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

   /**
    * Title:            ObsValueDateHistDT is a class
    * Description:	This is a class which sets/gets(retrieves)
    *                   all the fields to the database table
    * Copyright:	Copyright (c) 2001
    * Company: 	        Computer Sciences Corporation
    * @author           NEDSS TEAM
    * @version	        1.0
    */

public class ObsValueDateHistDT extends AbstractVO
{

	private static final long serialVersionUID = 1L;
    private Long observationUid;

    private Integer obsValueDateSeq;

    private Integer versionCtrlNbr;

    private String durationAmt;

    private String durationUnitCd;

    private Timestamp fromTime;

    private Timestamp toTime;

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
    * @return Long  the current value of the ObservationUid
    */
    public Long getObservationUid()
    {
        return observationUid;
    }
    public void setObservationUid(Long aObservationUid)
    {
        observationUid = aObservationUid;
        setItDirty(true);
    }

   /**
    * Access method for the ObservationUid.
    *
    * @return Long  the current value of the ObservationUid
    */
    public Integer getObsValueDateSeq()
    {
        return obsValueDateSeq;
    }
    public void setObsValueDateSeq(Integer aObsValueDateSeq)
    {
        obsValueDateSeq = aObsValueDateSeq;
        setItDirty(true);
    }

    /**
    * Access method for the ObservationUid.
    *
    * @return Long  the current value of the ObservationUid
    */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }


   /**
    * Access method for the ObservationUid.
    *
    * @return Long  the current value of the ObservationUid
    */
    public String getDurationAmt()
    {
        return durationAmt;
    }
    public void setDurationAmt(String aDurationAmt)
    {
        durationAmt = aDurationAmt;
        setItDirty(true);
    }

   /**
    * Access method for the ObservationUid.
    *
    * @return Long  the current value of the ObservationUid
    */
    public String getDurationUnitCd()
    {
        return durationUnitCd;
    }
    public void setDurationUnitCd(String aDurationUnitCd)
    {
        durationUnitCd = aDurationUnitCd;
        setItDirty(true);
    }

   /**
    * Access method for the ObservationUid.
    *
    * @return Long  the current value of the ObservationUid
    */
    public Timestamp getFromTime()
    {
        return fromTime;
    }
    public void setFromTime(Timestamp aFromTime)
    {
        fromTime = aFromTime;
        setItDirty(true);
    }

   public void setFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the ObservationUid.
    *
    * @return Long  the current value of the ObservationUid
    */
    public Timestamp getToTime()
    {
        return toTime;
    }
    public void setToTime(Timestamp aToTime)
    {
        toTime = aToTime;
        setItDirty(true);
    }

   public void setToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * Access method for the ObservationUid.
    *
    * @return Long  the current value of the ObservationUid
    */
       public String getProgAreaCd()
   {
      return progAreaCd;
   }
      public void setProgAreaCd(String aProgAreaCd)
   {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
   }


     /**
    * Access method for the ObservationUid.
    *
    * @return Long  the current value of the ObservationUid
    */
       public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }
      public void setJurisdictionCd (String aJurisdictionCd )
   {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
   }

   /**
    * Access method for the ObservationUid.
    *
    * @return Long  the current value of the ObservationUid
    */
       public Long getProgramJurisdictionOid ()
   {
      return programJurisdictionOid;
   }
      public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
   {
      programJurisdictionOid = aProgramJurisdictionOid;
      setItDirty(true);
   }


     /**
    * Access method for the ObservationUid.
    *
    * @return Long  the current value of the ObservationUid
    */
       public String getSharedInd()
   {
      return sharedInd;
   }
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
    voClass =  (( ObsValueDateHistDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }


      /**
    * Sets the value of the itDirty property.
    *
    * @param itDirty boolean the new value of the ItDirty property
    */
   public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }


   /**
    * get the value of the boolean property.
    *
    * @return ItDirty the value of the boolean vlue
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
    * gets the value of the ItDelete property.
    *
    * @return ItDelete the new value of the boolean property
    */
   public boolean isItDelete() {
     return itDelete;
   }


   /**
    * Sets the value of the ItDelete property.
    *
    * @param itDelete boolean the value of the boolean property
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }

}
