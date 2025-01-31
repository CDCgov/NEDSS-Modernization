package gov.cdc.nedss.entity.person.dt;
import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;
import java.io.*;
import gov.cdc.nedss.systemservice.util.*;

public class PersonConditionDT extends AbstractVO
implements RootDTInterface {	

		private static final long serialVersionUID = 1L;
	    private Long personUid;	    
	    private String addReasonCd;
	    private Timestamp addTime;
	    private Long addUserId;
	    private Timestamp asOfDate;
	    private String lastChgReasonCd;
	    private Timestamp lastChgTime;
	    private Long lastChgUserId;	   
	    private String recordStatusCd;
	    private Timestamp recordStatusTime;
	    private String userAffiliationTxt;
	    private String progAreaCd = null;
	    private String jurisdictionCd = null;
	    private Long programJurisdictionOid = null;
	    private String sharedInd = null;
	    private boolean itDirty = false;
	    private boolean itNew = true;
	    private boolean itDelete = false;
	    private Integer versionCtrlNbr;
	    private Timestamp statusTime;
	    private String statusCd;
	    private String localId;
	    private String condition;

	    public String getCondition() {
			return condition;
		}

		public void setCondition(String condition) {
			this.condition = condition;
		}

		/**
	     * returns an Object that is a complete copy of this object
	     * @return Object deepCopy
	     */
	    public Object deepCopy() throws CloneNotSupportedException, IOException, ClassNotFoundException
	    {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(baos);
	        oos.writeObject(this);
	        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	        ObjectInputStream ois = new ObjectInputStream(bais);
	        Object deepCopy = ois.readObject();

	        return  deepCopy;
	    }

	   /**
	   Access method for the personUid property.

	   @return   the current value of the personUid property
	    */
	    public Long getPersonUid()
	    {
	        return personUid;
	    }

	   /**
	   Sets the value of the personUid property.

	   @param aPersonUid the new value of the personUid property
	    */
	    public void setPersonUid(Long aPersonUid)
	    {
	        personUid = aPersonUid;
	        setItDirty(true);
	    }

	  
	   /**
	   Access method for the addReasonCd property.

	   @return   the current value of the addReasonCd property
	    */
	    public String getAddReasonCd()
	    {
	        return addReasonCd;
	    }

	   /**
	   Sets the value of the addReasonCd property.

	   @param aAddReasonCd the new value of the addReasonCd property
	    */
	    public void setAddReasonCd(String aAddReasonCd)
	    {
	        addReasonCd = aAddReasonCd;
	        setItDirty(true);
	    }

	   /**
	   Access method for the addTime property.

	   @return   the current value of the addTime property
	    */
	    public Timestamp getAddTime()
	    {
	        return addTime;
	    }

	   /**
	   Sets the value of the addTime property.

	   @param aAddTime the new value of the addTime property
	    */
	    public void setAddTime(Timestamp aAddTime)
	    {
	        addTime = aAddTime;
	        setItDirty(true);
	    }

	   /**
	   Sets the value of the addTime property.
	    takes a String and converts to Timestamp
	   @param aAddTime the new value of the addTime property
	    */
	   public void setAddTime_s(String strTime)
	   {
	      if (strTime == null) return;
	      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
	   }

	   /**
	   Access method for the addUserId property.

	   @return   the current value of the addUserId property
	    */
	    public Long getAddUserId()
	    {
	        return addUserId;
	    }

	   /**
	   Sets the value of the addUserId property.

	   @param aAddUserId the new value of the addUserId property
	    */
	    public void setAddUserId(Long aAddUserId)
	    {
	        addUserId = aAddUserId;
	        setItDirty(true);
	    }

		/**
		 * gets asOfDate
		 * @return Timestamp
		 */
		public Timestamp getAsOfDate(){
			return this.asOfDate;
		}


		/**
		 * set asOfDate
		 * @param aAsOfDate
		 */
		public void setAsOfDate(Timestamp aAsOfDate){
			this.asOfDate = aAsOfDate;
			setItDirty(true);
		}

	        public void setAsOfDate_s(String strTime)
	         {
	            if (strTime == null) return;
	            this.setAsOfDate(StringUtils.stringToStrutsTimestamp(strTime));
	         }

	   /**
	   Access method for the lastChgReasonCd property.

	   @return   the current value of the lastChgReasonCd property
	    */
	    public String getLastChgReasonCd()
	    {
	        return lastChgReasonCd;
	    }

	   /**
	   Sets the value of the lastChgReasonCd property.

	   @param aLastChgReasonCd the new value of the lastChgReasonCd property
	    */
	    public void setLastChgReasonCd(String aLastChgReasonCd)
	    {
	        lastChgReasonCd = aLastChgReasonCd;
	        setItDirty(true);
	    }

	   /**
	   Access method for the lastChgTime property.

	   @return   the current value of the lastChgTime property
	    */
	    public Timestamp getLastChgTime()
	    {
	        return lastChgTime;
	    }

	   /**
	   Sets the value of the lastChgTime property.

	   @param aLastChgTime the new value of the lastChgTime property
	    */
	    public void setLastChgTime(Timestamp aLastChgTime)
	    {
	        lastChgTime = aLastChgTime;
	        setItDirty(true);
	    }

	   /**
	   Sets the value of the lastChgTime property.
	    takes a String and converts it to Timestamp
	   @param aLastChgTime the new value of the lastChgTime property
	    */
	   public void setLastChgTime_s(String strTime)
	   {
	      if (strTime == null) return;
	      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
	   }

	   /**
	   Access method for the lastChgUserId property.

	   @return   the current value of the lastChgUserId property
	    */
	    public Long getLastChgUserId()
	    {
	        return lastChgUserId;
	    }

	   /**
	   Sets the value of the lastChgUserId property.

	   @param aLastChgUserId the new value of the lastChgUserId property
	    */
	    public void setLastChgUserId(Long aLastChgUserId)
	    {
	        lastChgUserId = aLastChgUserId;
	        setItDirty(true);
	    }

	   

	  

	   /**
	   Access method for the recordStatusCd property.

	   @return   the current value of the recordStatusCd property
	    */
	    public String getRecordStatusCd()
	    {
	        return recordStatusCd;
	    }

	   /**
	   Sets the value of the recordStatusCd property.

	   @param aRecordStatusCd the new value of the recordStatusCd property
	    */
	    public void setRecordStatusCd(String aRecordStatusCd)
	    {
	        recordStatusCd = aRecordStatusCd;
	        setItDirty(true);
	    }

	   /**
	   Access method for the recordStatusTime property.

	   @return   the current value of the recordStatusTime property
	    */
	    public Timestamp getRecordStatusTime()
	    {
	        return recordStatusTime;
	    }

	   /**
	   Sets the value of the recordStatusTime property.

	   @param aRecordStatusTime the new value of the recordStatusTime property
	    */
	    public void setRecordStatusTime(Timestamp aRecordStatusTime)
	    {
	        recordStatusTime = aRecordStatusTime;
	        setItDirty(true);
	    }

	   /**
	   Sets the value of the recordStatusTime property.
	    takes String and converts to a Timestring
	   @param aRecordStatusTime the new value of the recordStatusTime property
	    */
	   public void setRecordStatusTime_s(String strTime)
	   {
	      if (strTime == null) return;
	      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
	   }

	   /**
	   Access method for the userAffiliationTxt property.

	   @return   the current value of the userAffiliationTxt property
	    */
	    public String getUserAffiliationTxt()
	    {
	        return userAffiliationTxt;
	    }

	   /**
	   Sets the value of the userAffiliationTxt property.

	   @param aUserAffiliationTxt the new value of the userAffiliationTxt property
	    */
	    public void setUserAffiliationTxt(String aUserAffiliationTxt)
	    {
	        userAffiliationTxt = aUserAffiliationTxt;
	        setItDirty(true);
	    }

	   /**
	   Access method for the progAreaCd property.

	   @return   the current value of the progAreaCd property
	    */
	   public String getProgAreaCd()
	   {
	      return progAreaCd;
	   }

	   /**
	   Sets the value of the progAreaCd property.

	   @param aProgAreaCd the new value of the progAreaCd property
	    */
	   public void setProgAreaCd(String aProgAreaCd)
	   {
	      progAreaCd = aProgAreaCd;
	      setItDirty(true);
	   }

	   /**
	   Access method for the jurisdictionCd property.

	   @return   the current value of the jurisdictionCd property
	    */
	   public String getJurisdictionCd ()
	   {
	      return jurisdictionCd ;
	   }

	   /**
	   Sets the value of the jurisdictionCd property.

	   @param aJurisdictionCd the new value of the jurisdictionCd property
	    */
	   public void setJurisdictionCd (String aJurisdictionCd )
	   {
	      jurisdictionCd = aJurisdictionCd;
	      setItDirty(true);
	   }

	   /**
	   Access method for the programJurisdictionOid property.

	   @return   the current value of the programJurisdictionOid property
	    */
	   public Long getProgramJurisdictionOid ()
	   {
	      return programJurisdictionOid;
	   }

	   /**
	   Sets the value of the programJurisdictionOid property.

	   @param aProgramJurisdictionOid the new value of the programJurisdictionOid property
	    */
	   public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
	   {
	      programJurisdictionOid = aProgramJurisdictionOid;
	      setItDirty(true);
	   }

	    /**
	     * Access method for sharedInd
	     * @return String sharedInd
	     */
	    public String getSharedInd() {

	        return sharedInd;
	    }

	    /**
	     * Sets the value of sharedInd
	     * @param String sharedInd
	     */
	    public void setSharedInd(String aSharedInd) {
	        sharedInd = aSharedInd;
	        setItDirty(true);
	    }

	    /**
	     *
	     * @param objectname1
	     * @param objectname2
	     * @param voClass
	     * @return boolean
	     */
	    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
	        voClass =  (( PersonRaceDT) objectname1).getClass();
	        NedssUtils compareObjs = new NedssUtils();
	        return (compareObjs.equals(objectname1,objectname2,voClass));
	     }

	    /**
	     * Marks this object as a class that has been modified.
	     * @param itDirty
	     */
	    public void setItDirty(boolean itDirty) {
	      this.itDirty = itDirty;
	    }

	    /**
	     * Returns boolean indicating if this object has been modified.
	     * @return boolean itDirty
	     */
	    public boolean isItDirty() {

	        return itDirty;
	    }

	    /**
	     * Sets boolean value indicating if this object is new and that it does not
	     * exist in the database.
	     * @param itNew
	     */
	    public void setItNew(boolean itNew) {
	      this.itNew = itNew;
	    }

	    /**
	     * Returns boolean indicating if this is a new object.
	     * @return itNew  boolean
	     */
	    public boolean isItNew() {

	        return itNew;
	    }

	    /**
	     * Marks this object for deletion from the database.s
	     * @param itDelete
	     */
	    public void setItDelete(boolean itDelete) {
	      this.itDelete = itDelete;
	    }

	    /**
	     * Returns boolean indicating if object is marked for deletion from
	     * database.
	     * @return itDelete boolean
	     */
	    public boolean isItDelete() {

	        return itDelete;
	    }

	    /**
	     *
	     * @return Long
	     */
	    public Long getUid() {

	        return personUid;
	    }

	    /**
	     *
	     * @return String
	     */
	    public String getSuperclass() {

	        return NEDSSConstants.CLASSTYPE_ENTITY;
	    }

	    /**
	     *
	     * @return Integer
	     */
	    public Integer getVersionCtrlNbr() {

	        return versionCtrlNbr;
	    }

	    /**
	     *
	     * @param aVersionCtrlNbr
	     */
	    public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
	        versionCtrlNbr = aVersionCtrlNbr;
	        setItDirty(true);
	    }

	    /**
	     *
	     * @return String
	     */
	    public Timestamp getStatusTime() {

	        return statusTime;
	    }

	    /**
	     *
	     * @param aStatusTime
	     */
	    public void setStatusTime(Timestamp aStatusTime) {
	        statusTime = aStatusTime;
	        setItDirty(true);
	    }

	    /**
	     *
	     * @param strTime
	     */
	    public void setStatusTime_s(String strTime) {

	        if (strTime == null)

	            return;

	        this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
	    }

	    /**
	     *
	     * @return String
	     */
	    public String getStatusCd() {

	        return statusCd;
	    }

	    /**
	     *
	     * @param aStatusCd
	     */
	    public void setStatusCd(String aStatusCd) {
	        statusCd = aStatusCd;
	        setItDirty(true);
	    }

	    /**
	     *
	     * @return String
	     */
	    public String getLocalId() {

	        return localId;
	    }

	    /**
	     *
	     * @param aLocalId
	     */
	    public void setLocalId(String aLocalId) {
	        localId = aLocalId;
	        setItDirty(true);
	    }

	}


