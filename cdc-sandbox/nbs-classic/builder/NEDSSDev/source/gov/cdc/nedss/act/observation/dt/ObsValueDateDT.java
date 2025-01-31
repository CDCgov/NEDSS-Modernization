

package gov.cdc.nedss.act.observation.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;


   /**
    * Title:            ObsValueDateDT is a class
    * Description:	This is a class which sets/gets(retrieves)
    *                   all the fields to the database table
    * Copyright:	Copyright (c) 2001
    * Company: 	        Computer Sciences Corporation
    * @author           NEDSS TEAM
    * @version	        1.0
    */

public class ObsValueDateDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;

    private Long observationUid;

    private Integer obsValueDateSeq;

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
    * @return   the current value of the ObservationUid
    */
    public Long getObservationUid()
    {
        return observationUid;
    }

    /**
    * Sets the value of the ObservationUid.
    *
    * @param Long the aObservationUid
    */
    public void setObservationUid(Long aObservationUid)
    {
        observationUid = aObservationUid;
        setItDirty(true);
    }

   /**
    * Access method for the ObsValueDateSeq.
    *
    * @return   the current value of the ObsValueDateSeq
    */
    public Integer getObsValueDateSeq()
    {
        return obsValueDateSeq;
    }

    /**
    * Sets the value of the ObsValueDateSeq.
    *
    * @return  Interger the current value of the aObsValueDateSeq
    */
    public void setObsValueDateSeq(Integer aObsValueDateSeq)
    {
        obsValueDateSeq = aObsValueDateSeq;
        setItDirty(true);
    }

   /**
    * Access method for the DurationAmt.
    *
    * @return  String the current value of the DurationAmt
    */
    public String getDurationAmt()
    {
        return durationAmt;
    }

    /**
    * Sets the value of the DurationAmt.
    *
    * @return  String the current value of the aDurationAmt
    */
    public void setDurationAmt(String aDurationAmt)
    {
        durationAmt = aDurationAmt;
        setItDirty(true);
    }

   /**
    * Access method for the DurationUnitCd.
    *
    * @return  String the current value of the DurationUnitCd
    */
    public String getDurationUnitCd()
    {
        return durationUnitCd;
    }

    /**
    * Sets the value of the DurationUnitCd.
    *
    * @param String the aDurationUnitCd
    */
    public void setDurationUnitCd(String aDurationUnitCd)
    {
        durationUnitCd = aDurationUnitCd;
        setItDirty(true);
    }

   /**
    * Access method for the FromTime.
    *
    * @return  Timestamp the current value of the FromTime
    */
    public Timestamp getFromTime()
    {
        return fromTime;
    }

    /**
    * Sets the value of the FromTime.
    *
    * @param Timestamp the aFromTime
    */
    public void setFromTime(Timestamp aFromTime)
    {
        fromTime = aFromTime;
        setItDirty(true);
    }

   /**
    * Sets the value of the FromTime_s.
    *
    * @param String the FromTime_s
    */
   public void setFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the ToTime.
    *
    * @return Timestamp  the current value of the ToTime
    */
    public Timestamp getToTime()
    {
        return toTime;
    }

    /**
    * Sets the value of the ToTime.
    *
    * @param ToTime the aToTime
    */
    public void setToTime(Timestamp aToTime)
    {
        toTime = aToTime;
        setItDirty(true);
    }

    /**
    * Sets the value of the ToTime_s.
    *
    * @param Long the strTime
    */
   public void setToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    * Access method for the ProgAreaCd.
    *
    * @return  String the current value of the ProgAreaCd
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
    * Access method for the JurisdictionCd .
    *
    * @return  String the current value of the JurisdictionCd
    */
       public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }

  /**
    * Sets the value of the JurisdictionCd .
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
    * @return String  the current value of the ProgramJurisdictionOid
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
    * @return  String the current value of the SharedInd
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
    voClass =  (( ObsValueDateDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }


     /**
    * Sets the value of the itDirty property.
    *
    * @param itDirty the new value of the ItDirty property
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
    * @param itNew the new value of the boolean property
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
    * @param itDelete the value of the boolean property
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }



}
