

package gov.cdc.nedss.act.observation.dt;




    import  gov.cdc.nedss.util.*;
    import java.sql.Timestamp;

    /**
    * Name:	    ObservationInterpDT is a class
    * Description:  This is the implementation of NEDSSDAOInterface for the
    *               observation_interp value object in the observation_interp entity bean.
    *               This class encapsulates all the JDBC calls made by the observation_interpEJB
    *               for a observation_interp object. Actual logic of
    *               inserting/reading/updating/deleting the data in relational
    *               database tables to mirror the state of observation_interpEJB is
    *               implemented here.
    * Copyright:	Copyright (c) 2001
    * Company: 	    Computer Sciences Corporation
    * @author       Nedss Team
    * @version	    1.0
    */

    public class ObservationInterpDT extends AbstractVO {
    	
    private static final long serialVersionUID = 1L;
    private Long observationUid;
    private String interpretationCd;
    private String interpretationDescTxt;
    boolean _bDirty = false;
    boolean _bNew = false;

    /**
     * Empty constructor
     */
     public ObservationInterpDT() {}



   /**
     * Construct a new dependent object instance.
     * @param Long the observationUid
     * @param String the interpretationCd
     * @param String the interpretationDescTxt
     */
    public ObservationInterpDT(Long observationUid, String interpretationCd, String interpretationDescTxt) {
        this.observationUid = observationUid;
        this.interpretationCd = interpretationCd;
        this.interpretationDescTxt = interpretationDescTxt;

    }


    /**
    * Access method for the ObservationUid.
    *
    * @return  Long the current value of the ObservationUid
    */
    public Long getObservationUid() {
        return this.observationUid;
    }

       /**
    * Access method for the InterpretationCd.
    *
    * @return String  the current value of the InterpretationCd
    */
    public String getInterpretationCd() {
        return this.interpretationCd;
    }

   /**
    * Access method for the InterpretationDescTxt.
    *
    * @return String  the current value of the InterpretationDescTxt
    */
    public String getInterpretationDescTxt() {
        return this.interpretationDescTxt;
    }

    /**
    * Sets the value of the ObservationUid.
    *
    * @param Long the val
    */
    public void  setObservationUid(Long val) {
        this.observationUid = val;
        this.setItDirty(true);
    }

   /**
    * Sets the value of the InterpretationCd.
    *
    * @param val String the new value of the InterpretationCd
    */
    public void  setInterpretationCd(String val) {
        this.interpretationCd = val;
        this.setItDirty(true);
    }

   /**
    * Sets the value of the InterpretationDescTxt.
    *
    * @param val String the new value of the InterpretationDescTxt
    */
    public void  setInterpretationDescTxt(String val) {
        this.interpretationDescTxt = val;
        this.setItDirty(true);
    }


   /**
    * Sets the value of the ItDelete property.
    *
    * @param bVal boolean the value of the boolean property
    */
   public void setItDelete(boolean bVal)
   {
    this.itDelete = bVal;
   }

   /**
    * Sets the value of the itDirty property.
    *
    * @param bVal boolean the new value of the ItDirty property
    */
   public void setItDirty(boolean bVal)
   {
    this.itDirty = bVal;
   }

     /**
    * Sets the value of the ItNew property.
    *
    * @param bVal boolean the new value of the boolean property
    */
   public void setItNew(boolean bVal)
   {
    this.itNew = bVal;
   }


  /**
    * get the value of the boolean property.
    *
    * @return ItDirty the value of the boolean value
    */
   public boolean isItDirty()
   {
    return itDirty;
    }


   /**
    * get the value of the itNew property.
    *
    * @return itNew the value of the boolean property
    */
   public boolean isItNew()
   {
    return itNew;
    }

   /**
    * gets the value of the ItDelete property.
    *
    * @return ItDelete the new value of the boolean property
    */
   public boolean isItDelete()
   {
    return this.itDelete;
    }

   /**
    * get the value of the boolean property.
    * @param objectname1 Object the object name
    * @param objectname2 Object the object name
    * @param voClass Class the class
    * @return isEqual the value of the boolean value
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
      voClass =  (( ObservationInterpDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
   }


}

