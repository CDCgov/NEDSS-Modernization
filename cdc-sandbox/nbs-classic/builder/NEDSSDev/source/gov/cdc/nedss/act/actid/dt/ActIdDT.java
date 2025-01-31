package gov.cdc.nedss.act.actid.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;
import  gov.cdc.nedss.act.actdt.*;
import gov.cdc.nedss.systemservice.util.*;

/**
 * Description: A helper class for ActId table data.
 * 
 * @author Nedss Development Team
 * @version 1.0
 */
public class ActIdDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;
    private Long actUid;
    private Integer actIdSeq;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String assigningAuthorityCd;
    private String assigningAuthorityDescTxt;
    private String durationAmt;
    private String durationUnitCd;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String rootExtensionTxt;
    private String statusCd;
    private Timestamp statusTime;
    private String typeCd;
    private String typeDescTxt;
    private String userAffiliationTxt;
    private Timestamp validFromTime;
    private Timestamp validToTime;
    private Integer versionCtrlNbr;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;
    /**
     * Overloaded constructor other than default.
     * 
     * @param actIdSeq Integer
     * @param addReasonCd String
     * @param addTime Timestamp
     * @param addUserId Long
     * @param assigningAuthorityCd String
     * @param assigningAuthorityDescTxt String
     * @param durationAmt String
     * @param durationUnitCd String
     * @param lastChgReasonCd String
     * @param lastChgTime Timestamp
     * @param lastChgUserId Long
     * @param recordStatusCd String
     * @param recordStatusTime Timestamp
     * @param rootExtensionTxt String
     * @param statusCd String
     * @param statusTime Timestamp
     * @param typeCd String
     * @param typeDescTxt String
     * @param userAffiliationTxt String
     * @param validFromTime Timestamp
     * @param validToTime Long
     * @param actUid Long
     */
    public ActIdDT ( Integer actIdSeq,
    String addReasonCd,
    Timestamp addTime,
    Long addUserId,
    String assigningAuthorityCd,
    String assigningAuthorityDescTxt,
    String durationAmt,
    String durationUnitCd,
    String lastChgReasonCd,
    Timestamp lastChgTime,
    Long lastChgUserId,
    String recordStatusCd,
    Timestamp recordStatusTime,
    String rootExtensionTxt,
    String statusCd,
    Timestamp statusTime,
    String typeCd,
    String typeDescTxt,
    String userAffiliationTxt,
    Timestamp validFromTime,
    Timestamp validToTime,
    Long actUid)
   {
       this.actIdSeq = actIdSeq;
       this.addReasonCd = addReasonCd;
       this.addTime = addTime;
       this.addUserId = addUserId;
       this.assigningAuthorityCd = assigningAuthorityCd;
       this.assigningAuthorityDescTxt = assigningAuthorityDescTxt;
       this.durationAmt = durationAmt;
       this.durationUnitCd = durationUnitCd;
       this.lastChgReasonCd = lastChgReasonCd;
       this.lastChgTime = lastChgTime;
       this.lastChgUserId = lastChgUserId;
       this.recordStatusCd = recordStatusCd;
       this.recordStatusTime = recordStatusTime;
       this.rootExtensionTxt = rootExtensionTxt;
       this.statusCd = statusCd;
       this.statusTime = statusTime;
       this.typeCd = typeCd;
       this.typeDescTxt = typeDescTxt;
       this.userAffiliationTxt = userAffiliationTxt;
       this.validFromTime = validFromTime;
       this.validToTime = validToTime;
       this.actUid = actUid;
       setItNew(true);
   }

   /**
    * Default constructor
    */
   public ActIdDT()
   {
   }

    /**
     * gets ActUid
     * 
     * @return : actUid Long value
     */
    public Long getActUid()
    {
        return actUid;
    }

    /**
     * sets ActUid
     * 
     * @param aActUid : Long value
     */
    public void setActUid(Long aActUid)
    {
        actUid = aActUid;
        setItDirty(true);
    }

    /**
     * gets ActIdSeq
     * 
     * @return : actIdSeq Integer value
     */
    public Integer getActIdSeq()
    {
        return actIdSeq;
    }

    /**
     * sets ActIdSeq
     * 
     * @param aActIdSeq : Integer value
     */
    public void setActIdSeq(Integer aActIdSeq)
    {
        actIdSeq = aActIdSeq;
        setItDirty(true);
    }

    /**
     * Access method for the addReasonCd property.
     * 
     * @return the current value of the addReasonCd property
     */
    public String getAddReasonCd()
    {
        return addReasonCd;
    }

    /**
     * Sets the value of both addReasonCd and itDirty properties
     * 
     * @param aAddReasonCd the new value of the addReasonCd property
     */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     * Access method for the addTime property.
     * 
     * @return the current value of the addTime property
     */
    public Timestamp getAddTime()
    {
        return addTime;
    }

    /**
     * Sets the value of both addTime and itDirty properties
     * 
     * @param aAddTime the new value of the addTime property
     */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     * Sets the value of both addTime and itDirty properties
     * takes a String and converts to a Timestamp
     * 
     * @param strTime the new value of the addTime property
     */
    public void setAddTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * Access method for the addUserId property.
     * 
     * @return the current value of the addUserId property
     */
    public Long getAddUserId()
    {
        return addUserId;
    }

    /**
     * Sets the value of both addUserId and itDirty properties
     * 
     * @param aAddUserId the new value of the addUserId property
     */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     * gets AssigningAuthorityCd
     * 
     * @return : assigningAuthorityCd String value
     */
    public String getAssigningAuthorityCd()
    {
        return assigningAuthorityCd;
    }

    /**
     * sets AssigningAuthorityCd
     * 
     * @param aAssigningAuthorityCd : String value
     */
    public void setAssigningAuthorityCd(String aAssigningAuthorityCd)
    {
        assigningAuthorityCd = aAssigningAuthorityCd;
        setItDirty(true);
    }

    /**
     * gets AssigningAuthorityDescTxt
     * 
     * @return : assigningAuthorityDescTxt String value
     */
    public String getAssigningAuthorityDescTxt()
    {
        return assigningAuthorityDescTxt;
    }

    /**
     * sets AssigningAuthorityDescTxt
     * 
     * @param aAssigningAuthorityDescTxt : String value
     */
    public void setAssigningAuthorityDescTxt(String aAssigningAuthorityDescTxt)
    {
        assigningAuthorityDescTxt = aAssigningAuthorityDescTxt;
        setItDirty(true);
    }

    /**
     * Access method for the durationAmt property.
     * 
     * @return the current value of the durationAmt property
     */
    public String getDurationAmt()
    {
        return durationAmt;
    }

    /**
     * Sets the value of both durationAmt and itDirty properties
     * 
     * @param aDurationAmt the new value of the durationAmt property
     */
    public void setDurationAmt(String aDurationAmt)
    {
        durationAmt = aDurationAmt;
        setItDirty(true);
    }

    /**
     * Access method for the durationUnitCd property.
     * 
     * @return the current value of the durationUnitCd property
     */
    public String getDurationUnitCd()
    {
        return durationUnitCd;
    }

    /**
     * Sets the value of both durationUnitCd and itDirty properties
     * 
     * @param aDurationUnitCd the new value of the durationUnitCd property
     */
    public void setDurationUnitCd(String aDurationUnitCd)
    {
        durationUnitCd = aDurationUnitCd;
        setItDirty(true);
    }

    /**
     * Access method for the lastChgReasonCd property.
     * 
     * @return the current value of the lastChgReasonCd property
     */
    public String getLastChgReasonCd()
    {
        return lastChgReasonCd;
    }

    /**
     * Sets the value of both lastChgReasonCd and itDirty properties
     * 
     * @param aLastChgReasonCd the new value of the lastChgReasonCd property
     */
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     * Access method for the lastChgTime property.
     * 
     * @return the current value of the lastChgTime property
     */
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }

    /**
     * Sets the value of both lastChgTime and itDirty properties
     * 
     * @param aLastChgTime the new value of the lastChgTime property
     */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     * Sets the value of both lastChgTime and itDirty properties
     * takes a String and converts to Timestamp
     * 
     * @param strTime the new value of the lastChgTime property
     */
    public void setLastChgTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * Access method for the lastChgUserId property.
     * 
     * @return the current value of the lastChgUserId property
     */
    public Long getLastChgUserId()
    {
        return lastChgUserId;
    }

    /**
     * Sets the value of both lastChgUserId and itDirty properties
     * 
     * @param aLastChgUserId the new value of the lastChgUserId property
     */
    public void setLastChgUserId(Long aLastChgUserId)
    {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }
    /**
     * Access method for the localId property.
     * 
     * @return the current value of the localId property
     */
    public String getLocalId()
    {
        return localId;
    }

    /**
     * Sets the value of both localId and itDirty properties
     * 
     * @param aLocalId the new value of the localId property
     */
    public void setLocalId(String aLocalId)
    {
        localId = aLocalId;
        setItDirty(true);
    }

    /**
     * Access method for the recordStatusCd property.
     * 
     * @return the current value of the recordStatusCd property
     */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }

    /**
     * Sets the value of both recordStatusCd and itDirty properties
     * 
     * @param aRecordStatusCd the new value of the recordStatusCd property
     */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     * Access method for the recordStatusTime property.
     * 
     * @return the current value of the recordStatusTime property
     */
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }

    /**
     * Sets the value of both recordStatusTime and itDirty properties
     * 
     * @param aRecordStatusTime the new value of the recordStatusTime property
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
     * Sets the value of both recordStatusTime and itDirty properties
     * takes a String and converts to Timestamp
     * 
     * @param strTime the new value of the recordStatusTime property
     */
    public void setRecordStatusTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets RootExtensionTxt
     * 
     * @return : rootExtensionTxt String value
     */
    public String getRootExtensionTxt()
    {
        return rootExtensionTxt;
    }

    /**
     * sets RootExtensionTxt
     * 
     * @param aRootExtensionTxt : String value
     */
    public void setRootExtensionTxt(String aRootExtensionTxt)
    {
        rootExtensionTxt = aRootExtensionTxt;
        setItDirty(true);
    }

    /**
     * Access method for the statusCd property.
     * 
     * @return the current value of the statusCd property
     */
    public String getStatusCd()
    {
        return statusCd;
    }

    /**
     * Sets the value of both statusCd and itDirty properties
     * 
     * @param aStatusCd the new value of the statusCd property
     */
    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     * Access method for the statusTime property.
     * 
     * @return the current value of the statusTime property
     */
    public Timestamp getStatusTime()
    {
        return statusTime;
    }

    /**
     * Sets the value of both statusTime and itDirty properties
     * 
     * @param aStatusTime the new value of the statusTime property
     */
    public void setStatusTime(Timestamp aStatusTime)
    {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
     * Sets the value of both statusTime and itDirty properties
     * 
     * @param strTime the new value of the statusTime property
     */
    public void setStatusTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets TypeCd
     * 
     * @return : typeCd String value
     */
    public String getTypeCd()
    {
        return typeCd;
    }

    /**
     * sets TypeCd
     * 
     * @param aTypeCd : String value
     */
    public void setTypeCd(String aTypeCd)
    {
        typeCd = aTypeCd;
        setItDirty(true);
    }

    /**
     * gets TypeDescTxt
     * 
     * @return : typeDescTxt String value
     */
    public String getTypeDescTxt()
    {
        return typeDescTxt;
    }

    /**
     * sets TypeDescTxt
     * 
     * @param aTypeDescTxt : String value
     */
    public void setTypeDescTxt(String aTypeDescTxt)
    {
        typeDescTxt = aTypeDescTxt;
        setItDirty(true);
    }

    /**
     * Access method for the userAffiliationTxt property.
     * 
     * @return the current value of the userAffiliationTxt property
     */
    public String getUserAffiliationTxt()
    {
        return userAffiliationTxt;
    }

    /**
     * Sets the value of both userAffiliationTxt and itDirty properties
     * 
     * @param aUserAffiliationTxt the new value of the userAffiliationTxt 
     *     property
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     * gets ValidFromTime
     * 
     * @return : validFromTime Timestamp value
     */
    public Timestamp getValidFromTime()
    {
        return validFromTime;
    }

    /**
     * sets ValidFromTime
     * 
     * @param aValidFromTime : Timestamp value
     */
    public void setValidFromTime(Timestamp aValidFromTime)
    {
        validFromTime = aValidFromTime;
        setItDirty(true);
    }

   /**
    * sets ValidFromTime ( convenient struts method)
    * 
    * @param strTime String
    */
   public void setValidFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setValidFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * gets ValidToTime
     * 
     * @return : validToTime Timestamp value
     */
    public Timestamp getValidToTime()
    {
        return validToTime;
    }

    /**
     * sets ValidToTime
     * 
     * @param aValidToTime : Timestamp value
     */
    public void setValidToTime(Timestamp aValidToTime)
    {
        validToTime = aValidToTime;
        setItDirty(true);
    }

   /**
    * sets itDirty ( convenient struts method)
    * 
    * @param strTime String
    */
   public void setValidToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setValidToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * Access method for the versionCtrlNbr property.
     * 
     * @return the current value of the versionCtrlNbr property
     */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
     * Sets the value of both versionCtrlNbr and itDirty properties
     * 
     * @param aVersionCtrlNbr the new value of the versionCtrlNbr property
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     * Access method for the progAreaCd property.
     * 
     * @return the current value of the progAreaCd property
     */
    public String getProgAreaCd()
    {
      return progAreaCd;
    }

    /**
     * Sets the value of both progAreaCd and itDirty properties
     * 
     * @param aProgAreaCd the new value of the progAreaCd property
     */
    public void setProgAreaCd(String aProgAreaCd)
    {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
    }

    /**
     * Access method for the jurisdictionCd property.
     * 
     * @return the current value of the jurisdictionCd property
     */
    public String getJurisdictionCd ()
    {
      return jurisdictionCd ;
    }

    /**
     * Sets the value of both jurisdictionCd and itDirty properties
     * 
     * @param aJurisdictionCd the new value of the jurisdictionCd property
     */
    public void setJurisdictionCd (String aJurisdictionCd )
    {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
    }

    /**
     * Access method for the programJurisdictionOid property.
     * 
     * @return the current value of the programJurisdictionOid property
     */
    public Long getProgramJurisdictionOid ()
    {
      return programJurisdictionOid;
    }

    /**
     * Sets the value of both programJurisdictionOid and itDirty properties
     * 
     * @param aProgramJurisdictionOid the new value of the 
     *     programJurisdictionOid property
     */
    public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
    {
      programJurisdictionOid = aProgramJurisdictionOid;
      setItDirty(true);
    }

    /**
     * Access method for the sharedInd property.
     * 
     * @return the current value of the sharedInd property
     */
    public String getSharedInd()
    {
      return sharedInd;
    }

    /**
     * Sets the value of both sharedInd and itDirty properties
     * 
     * @param aSharedInd the new value of the sharedInd property
     */
    public void setSharedInd(String aSharedInd)
    {
      sharedInd = aSharedInd;
      setItDirty(true);
    }

    /**
     * This method compares two objects and returns the results
     * 
     * @param objectname1 java.lang.Object
     * @param objectname2 java.lang.Object
     * @param voClass Class
     * @return the result of the comparison
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( ActIdDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }

    /**
     * Sets the value of the itDirty property
     * 
     * @param itDirty the new value of the itDirty property
     */
    public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
    }

    /**
     * Access method for the itDirty property.
     * 
     * @return the current value of the itDirty property
     */
    public boolean isItDirty() {
     return itDirty;
    }

    /**
     * Sets the value of the itNew property
     * 
     * @param itNew the new value of the itNew property
     */
    public void setItNew(boolean itNew) {
     this.itNew = itNew;
    }

    /**
     * Access method for the isItNew property.
     * 
     * @return the current value of the isItNew property
     */
    public boolean isItNew() {
     return itNew;
    }

    /**
     * Access method for the itDelete property.
     * 
     * @return the current value of the itDelete property
     */
    public boolean isItDelete() {
     return itDelete;
    }

    /**
     * Sets the value of the itDelete property
     * 
     * @param itDelete the new value of the itDelete property
     */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
    }

    /**
     * Gets the Uid.
     * 
     * @return Long
     */
    public Long getUid() {

        return actUid;
    }

    /**
     * Gets the superclass.
     * 
     * @return String
     */
    public String getSuperclass() {

        return NEDSSConstants.CLASSTYPE_ENTITY;
    }
}
