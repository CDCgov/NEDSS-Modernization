

package gov.cdc.nedss.act.publichealthcase.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

public class ConfirmationMethodDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;
	
    private Long publicHealthCaseUid;

    private String confirmationMethodCd;

    private String confirmationMethodDescTxt;

    private Timestamp confirmationMethodTime;

     private String progAreaCd = null;

     private String jurisdictionCd = null;

     private Long programJurisdictionOid = null;

     private String sharedInd = null;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;


  /**
   * getter method for aPublicHealthCaseUid
   * @return aPublicHealthCaseUid Long value
   */
    public Long getPublicHealthCaseUid()
    {
        return publicHealthCaseUid;
    }

   /**
    * setter method for PublicHealthCaseUid
    * @param aPublicHealthCaseUid Long value
    */
    public void setPublicHealthCaseUid(Long aPublicHealthCaseUid)
    {
        publicHealthCaseUid = aPublicHealthCaseUid;
        setItDirty(true);
    }

  /**
   * getter method for ConfirmationMethodCd
   * @return confirmationMethodCd String value
   */
    public String getConfirmationMethodCd()
    {
        return confirmationMethodCd;
    }

   /**
    * setter method for ConfirmationMethodCd
    * @param aConfirmationMethodCd String
    */
    public void setConfirmationMethodCd(String aConfirmationMethodCd)
    {
        confirmationMethodCd = aConfirmationMethodCd;
        setItDirty(true);
    }

  /**
   * getter method for ConfirmationMethodDescTxt
   * @return confirmationMethodDescTxt String value
   */
    public String getConfirmationMethodDescTxt()
    {
        return confirmationMethodDescTxt;
    }

   /**
    * setter method for ConfirmationMethodDescTxt
    * @param aConfirmationMethodDescTxt String value
    */
    public void setConfirmationMethodDescTxt(String aConfirmationMethodDescTxt)
    {
        confirmationMethodDescTxt = aConfirmationMethodDescTxt;
        setItDirty(true);
    }

  /**
   * getter method for ConfirmationMethodTime
   * @return Timestamp
   */
    public Timestamp getConfirmationMethodTime()
    {
        return confirmationMethodTime;
    }

    /**
    * setter method for ConfirmationMethodTime
    * @param aConfirmationMethodTime Timestamp
    */
    public void setConfirmationMethodTime(Timestamp aConfirmationMethodTime)
    {
        confirmationMethodTime = aConfirmationMethodTime;
        setItDirty(true);
    }

   /**
    * convenient struts setter method for ConfirmationMethodTime
    * @param strTime String value
    */
   public void setConfirmationMethodTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setConfirmationMethodTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

  /**
   * getter method for ProgAreaCd
   * @return progAreaCd String value
   */
   public String getProgAreaCd()
   {
      return progAreaCd;
   }

   /**
    * setter method for ProgAreaCd
    * @param ProgAreaCd String value
    */
    public void setProgAreaCd(String aProgAreaCd)
   {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
   }

  /**
   * getter method for JurisdictionCd
   * @return JurisdictionCd String value
   */
    public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }

   /**
    * setter method for JurisdictionCd
    * @param aJurisdictionCd String
    */
    public void setJurisdictionCd (String aJurisdictionCd )
   {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
   }

  /**
   * getter method for ProgramJurisdictionOid
   * @return Long
   */
    public Long getProgramJurisdictionOid ()
   {
      return programJurisdictionOid;
   }

   /**
    * setter method for ProgramJurisdictionOid
    * @param aProgramJurisdictionOid Long
    */
    public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
   {
      programJurisdictionOid = aProgramJurisdictionOid;
      setItDirty(true);
   }

  /**
   * getter method for SharedInd
   * @return String
   */
   public String getSharedInd()
   {
      return sharedInd;
   }

   /**
    * setter method for SharedInd
    * @param aSharedInd boolean
    */
    public void setSharedInd(String aSharedInd)
   {
      sharedInd = aSharedInd;
      setItDirty(true);
   }


    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( ConfirmationMethodDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }


   /**
    * setter method for itDirty
    * @param itDirty boolean
    */
    public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }


  /**
   * getter method for itDirty
   * @return boolean
   */
   public boolean isItDirty() {
     return itDirty;
   }


   /**
    * setter method for itNew
    * @param itDelete boolean
    */
    public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }


  /**
   * getter method for itNew
   * @return boolean
   */
   public boolean isItNew() {
     return itNew;
   }

  /**
   * getter method for itDelete
   * @return boolean
   */
   public boolean isItDelete() {
     return itDelete;
   }


    /**
    * setter method for itDelete
    * @param itDelete boolean
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }


}
