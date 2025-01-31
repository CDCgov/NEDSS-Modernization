

package gov.cdc.nedss.act.observation.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

   /**
    * Title:            ObsValueTxtHistDT is a class
    * Description:	This is a class which sets/gets(retrieves)
    *                   all the fields to the database table
    * Copyright:	Copyright (c) 2001
    * Company: 	        Computer Sciences Corporation
    * @author           NEDSS TEAM
    * @version	        1.0
    */

public class ObsValueTxtHistDT extends AbstractVO
{

	private static final long serialVersionUID = 1L;
    private Long observationUid;

    private Integer obsValueTxtSeq;

    private Integer versionCtrlNbr;

    private String dataSubtypeCd;

    private String encodingTypeCd;

    private String txtTypeCd;

    private byte[] valueImageTxt;

    private String valueTxt;

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
    * Access method for the ObsValueTxtSeq.
    *
    * @return Integer  the current value of the ObsValueTxtSeq
    */
    public Integer getObsValueTxtSeq()
    {
        return obsValueTxtSeq;
    }

    /**
    * Sets the value of the ObsValueTxtSeq.
    *
    * @param Integer the aObsValueTxtSeq
    */
    public void setObsValueTxtSeq(Integer aObsValueTxtSeq)
    {
        obsValueTxtSeq = aObsValueTxtSeq;
        setItDirty(true);
    }

    /**
    * Access method for the VersionCtrlNbr.
    *
    * @return Integer  the current value of the VersionCtrlNbr
    */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
    * Sets the value of the VersionCtrlNbr.
    *
    * @param Integer the aVersionCtrlNbr
    */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
    * Access method for the DataSubtypeCd.
    *
    * @return String  the current value of the DataSubtypeCd
    */
    public String getDataSubtypeCd()
    {
        return dataSubtypeCd;
    }

    /**
    * Sets the value of the DataSubtypeCd.
    *
    * @param String the aDataSubtypeCd
    */
    public void setDataSubtypeCd(String aDataSubtypeCd)
    {
        dataSubtypeCd = aDataSubtypeCd;
        setItDirty(true);
    }

    /**
    * Access method for the EncodingTypeCd.
    *
    * @return String  the current value of the EncodingTypeCd
    */
    public String getEncodingTypeCd()
    {
        return encodingTypeCd;
    }

    /**
    * Sets the value of the EncodingTypeCd.
    *
    * @param String the aEncodingTypeCd
    */
    public void setEncodingTypeCd(String aEncodingTypeCd)
    {
        encodingTypeCd = aEncodingTypeCd;
        setItDirty(true);
    }

    /**
    * Access method for the TxtTypeCd.
    *
    * @return String  the current value of the TxtTypeCd
    */
    public String getTxtTypeCd()
    {
        return txtTypeCd;
    }

    /**
    * Sets the value of the TxtTypeCd.
    *
    * @param String the aTxtTypeCd
    */
    public void setTxtTypeCd(String aTxtTypeCd)
    {
        txtTypeCd = aTxtTypeCd;
        setItDirty(true);
    }

    /**
    * Access method for the ValueImageTxt.
    *
    * @return String  the current value of the ValueImageTxt
    */
    public byte[] getValueImageTxt()
    {
        return valueImageTxt;
    }

    /**
    * Sets the value of the TxtTypeCd.
    *
    * @param String the aTxtTypeCd
    */
    public void setValueImageTxt(byte[] aValueImageTxt)
    {
        valueImageTxt = aValueImageTxt;
        setItDirty(true);
    }

    /**
    * Access method for the ValueTxt.
    *
    * @return String  the current value of the ValueTxt
    */
    public String getValueTxt()
    {
        return valueTxt;
    }

    /**
    * Access method for the ValueTxt.
    *
    * @return String  the current value of the aValueTxt
    */
    public void setValueTxt(String aValueTxt)
    {
        valueTxt = aValueTxt;
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
    * Access method for the SharedInd.
    *
    * @return String  the current value of the SharedInd
    */
       public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }

   /**
    * Sets the value of the jurisdictionCd.
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
    voClass =  (( ObsValueTxtHistDT) objectname1).getClass();
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
