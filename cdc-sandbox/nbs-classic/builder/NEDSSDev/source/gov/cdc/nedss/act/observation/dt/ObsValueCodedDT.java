

package gov.cdc.nedss.act.observation.dt;

import java.sql.Timestamp;
import java.util.Collection;
import  gov.cdc.nedss.util.*;


   /**
    * Title:            ObsValueCodeDT is class
    * Description:	This is a class which sets/gets(retrieves)
    *                   all the fields to the database table
    * Copyright:	Copyright (c) 2001
    * Company: 	        Computer Sciences Corporation
    * @author           NEDSS TEAM
    * @version	        1.0
    */

public class ObsValueCodedDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;

    private Long observationUid;

    private String altCd;

    private String altCdDescTxt;

    private String altCdSystemCd;

    private String altCdSystemDescTxt;

    private String code;

    private String codeDerivedInd;

    private String codeSystemCd;

    private String codeSystemDescTxt;

    private String codeVersion;

    private String displayName;

    private String originalTxt;

     private String progAreaCd = null;

     private String jurisdictionCd = null;

     private Long programJurisdictionOid = null;

     private String sharedInd = null;

    private boolean itDirty = false;

    private boolean itNew = false;

    private boolean itDelete = false;

    private Collection<Object> theObsValueCodedModDTCollection;

    private String searchResultRT;

    private String cdSystemCdRT;

		private String hiddenCd;

    /**
     * this is empty constructor
     *
     */
    public ObsValueCodedDT() {


   }

   /**
    * this is parameter constructor
    * Long the observationUid
    * String the altCd
    * String the altCdDescTxt
    * String the altCdSystemDescTxt
    * String the code
    * String codeDerivedInd
    * String the codeSystemCd the
    * String codeSystemDescTxt
    * String the codeVersion
    * String the displayName
    * String the originalTxt
    * String the progAreaCd
    * String the jurisdictionCd
    * String the programJurisdictionOid
    * String the sharedInd
    * boolean the itDirty
    * boolean the itNew
    * boolean the itDelete
    * Collection<Object>  the theObsValueCodedModDTCollection
    */
    public ObsValueCodedDT(
     Long observationUid,

    String altCd,

    String altCdDescTxt,

    String altCdSystemCd,

    String altCdSystemDescTxt,

    String code,

    String codeDerived,

    String codeSystemCd,

    String codeSystemDescTxt,

    String codeVersion,

    String displayName,

    String originalTxt,

    String progAreaCd,

    String jurisdictionCd,

    Long programJurisdictionOid,

    String sharedInd ,

    boolean itDirty,

    boolean itNew,

    boolean itDelete,

    Collection<Object> theObsValueCodedModDTCollection

    )
    {
      this.observationUid = observationUid;

      this.altCd = altCd;

      this.altCdDescTxt = altCdDescTxt;

      this.altCdSystemCd = altCdSystemCd;

      this.altCdSystemDescTxt = altCdSystemDescTxt;

      this.code =code;

      this.codeDerivedInd = codeDerivedInd;

      this.codeSystemCd = codeSystemCd;

      this.codeSystemDescTxt = codeSystemDescTxt;

      this.codeVersion = codeVersion;

      this.displayName = displayName;

      this.originalTxt = originalTxt;

      this.progAreaCd =  progAreaCd;

      this.jurisdictionCd = jurisdictionCd;

      this.programJurisdictionOid =  programJurisdictionOid;

      this.sharedInd = sharedInd;

      this.itDirty =  itDirty;

      this.itNew  =  itNew;

      this.itDelete = itDelete;

      this.theObsValueCodedModDTCollection  =  theObsValueCodedModDTCollection;


    }

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
	 * gets AltCd
	 * @return String
	 */
	public String getAltCd(){
		return this.altCd;
	}

	/**
	 * sets AltCd
	 * @param aAltCd
	 */
	public void setAltCd(String aAltCd){
		this.altCd = aAltCd;
		setItDirty(true);
	}


	/**
	 * gets AltCdDescTxt
	 * @return String
	 */
	public String getAltCdDescTxt(){
		return this.altCdSystemDescTxt;
	}


	/**
	 * sets AltCdDescTxt
	 * @param aAltCdDescTxt
	 */
	public void setAltCdDescTxt(String aAltCdDescTxt){
		this.altCdSystemDescTxt = aAltCdDescTxt;
		setItDirty(true);
	}

	/**
	 * gets AltCdSystemCd
	 * @return String
	 */
	public String getAltCdSystemCd(){
		return this.altCdSystemCd;
	}


	/**
	 * sets AltCdSystemCd
	 * @param aAltCdSystemCd
	 */
	public void setAltCdSystemCd(String aAltCdSystemCd){
		this.altCdSystemCd = aAltCdSystemCd;
		setItDirty(true);
	}

	/**
	 * gets AltCdSystemTxt
	 *@return String
	 */
	public String getAltCdSystemDescTxt(){
		return this.altCdSystemDescTxt;
	}


	/**
	 * sets AltCdSystemDescTxt
	 * @param aAltCdSystemDescTxt
	 */
	public void setAltCdSystemDescTxt(String aAltCdSystemDescTxt){
		this.altCdSystemDescTxt = aAltCdSystemDescTxt;
		setItDirty(true);
	}

    /**
    * Access method for the Code.
    *
    * @return  String the current value of the Code
    */
    public String getCode()
    {
        return code;
    }

/**
    * Sets the value of the Code.
    *
    * @param String the aCode
    */
    public void setCode(String aCode)
    {
        code = aCode;
        setItDirty(true);
    }

	/**
	 * gets CodeDerivedInd
	 * @return String
	 */
	public String getCodeDerivedInd(){
		return this.codeDerivedInd;
	}

	/**
	 * sets CodeDerivedInd
	 * @param aCodeDerivedInd
	 */
	public void setCodeDerivedInd(String aCodeDerivedInd){
		this.codeDerivedInd = aCodeDerivedInd;
		setItDirty(true);
	}

 /**
    * Access method for the CodeSystemCd.
    *
    * @return   String the current value of the CodeSystemCd
    */
    public String getCodeSystemCd()
    {
        return codeSystemCd;
    }

/**
    * Sets the value of the CodeSystemCd.
    *
    * @param String the aCodeSystemCd
    */
    public void setCodeSystemCd(String aCodeSystemCd)
    {
        codeSystemCd = aCodeSystemCd;
        setItDirty(true);
    }

    /**
    * Access method for the CodeSystemDescTxt.
    *
    * @return   String the current value of the CodeSystemDescTxt
    */
    public String getCodeSystemDescTxt()
    {
        return codeSystemDescTxt;
    }

    /**
    * Sets the value of the CodeSystemDescTxt.
    *
    * @param String the aCodeSystemDescTxt
    */
    public void setCodeSystemDescTxt(String aCodeSystemDescTxt)
    {
        codeSystemDescTxt = aCodeSystemDescTxt;
        setItDirty(true);
    }

    /**
    * Access method for the CodeVersion.
    *
    * @return  String the current value of the CodeVersion
    */
    public String getCodeVersion()
    {
        return codeVersion;
    }

    /**
    * Sets the value of the CodeVersion.
    *
    * @param String the aCodeVersion
    */
    public void setCodeVersion(String aCodeVersion)
    {
        codeVersion = aCodeVersion;
        setItDirty(true);
    }
/**
    * Access method for the DisplayName.
    *
    * @return  String the current value of the DisplayName
    */

    public String getDisplayName()
    {
        return displayName;
    }

    /**
    * Sets the value of the DisplayName.
    *
    * @param String the aDisplayName
    */
    public void setDisplayName(String aDisplayName)
    {
        displayName = aDisplayName;
        setItDirty(true);
    }

    /**
    * Access method for the OriginalTxt.
    *
    * @return  String the current value of the OriginalTxt
    */
    public String getOriginalTxt()
    {
        return originalTxt;
    }

    /**
    * Sets the value of the OriginalTxt.
    *
    * @param string the aOriginalTxt
    */
    public void setOriginalTxt(String aOriginalTxt)
    {
        originalTxt = aOriginalTxt;
        setItDirty(true);
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
    * Access method for the JurisdictionCd.
    *
    * @return String  the current value of the JurisdictionCd
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
    * @return  String the current value of the ProgramJurisdictionOid
    */
       public Long getProgramJurisdictionOid ()
   {
      return programJurisdictionOid;
   }

   /**
    * Sets the value of the ProgramJurisdictionOid.
    *
    * @param String the aProgramJurisdictionOid
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
    voClass =  (( ObsValueCodedDT) objectname1).getClass();
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

   /**
    * gets the value of the ObsValueCodedModDTCollection.
    *
    * @return ObsValueCodedModDTCollection  the Collection
    */
  public Collection<Object> getTheObsValueCodedModDTCollection() {
    return theObsValueCodedModDTCollection;
  }

   /**
    * Sets the value of the ObsValueCodedModDTCollection.
    *
    * @param Collection<Object>  the theObsValueCodedModDTCollection
    */
  public void setTheObsValueCodedModDTCollection(Collection<Object> theObsValueCodedModDTCollection) {
    this.theObsValueCodedModDTCollection  = theObsValueCodedModDTCollection;
  }

  /**
    * Access method for the SharedInd.
    *
    * @return  String the current value of the SharedInd
    */
       public String getSearchResultRT()
   {
      return searchResultRT;
   }

   /**
    * Sets the value of the SharedInd.
    *
    * @param String the aSharedInd
    */
      public void setSearchResultRT(String aSearchResultRT)
   {
      searchResultRT = aSearchResultRT;
      setItDirty(true);
   }

   public String getCdSystemCdRT()
   {
       return cdSystemCdRT;
   }

/**
    * sets cdVersion
    * @param aCdVersion : String value
    */
   public void setCdSystemCdRT(String aCdSystemCdRT)
   {
       cdSystemCdRT = aCdSystemCdRT;
       setItDirty(true);
   }

	  public String getHiddenCd()
   {
       return hiddenCd;
   }

/**
    * sets cdVersion
    * @param aCdVersion : String value
    */
   public void setHiddenCd(String aHiddenCd)
   {
       hiddenCd = aHiddenCd;
       setItDirty(true);
   }




}
