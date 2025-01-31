

package gov.cdc.nedss.elr.helper;

import  gov.cdc.nedss.util.*;
import java.sql.Timestamp;
import java.util.*;
import gov.cdc.nedss.systemservice.util.*;


public class ElrActivityLogDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;
    private Long msgObservationUid;

    private Integer elrActivityLogSeq;

    private String fillerNbr;

    private String id;

    private String odsObservationUid;

    private String statusCd;

    private Timestamp processTime;

    private String processCd;

    private String subjectNm;

    private String reportingFacNm;

    private String detailTxt;


    //attributes required by RootDTInterface
    private String electronicInd;
    private Integer versionCtrlNbr;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private Timestamp addTime = null;
    private Long addUserId = null;
    private Long uid;
    private Timestamp statusTime = null;
    private Timestamp recordStatusTime = null;
    private String recordStatusCd;
    private Timestamp lastChgTime;
    private String lastChgReasonCd;
    private String localId;
    private Long lastChgUserId;



    /**
   * Collection<Object>  of messages as Strings generated while migrating this ELR.
   */
   private Collection<Object>  processMessageCollection;


    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

    public ElrActivityLogDT()
    {
    }
    /**
     * This has been added to satisfy an requirment for Jurisdiction
     * assignment failure.  The Use Case for viewing the log
     * indicates that if this failes that additional details should be displayed.
     * At this point, the only code that should be calling this is the Jurisdiction Assignment Service.
     *
     * @param details
     */
    public void setDetailTxt(String detailTxt) {
      this.detailTxt = detailTxt;
    }

    public String getDetailTxt() {
      return detailTxt;
    }
    public Long getMsgObservationUid()
    {
        return msgObservationUid;
    }

    public void setMsgObservationUid(Long aMsgObservationUid)
    {
        msgObservationUid = aMsgObservationUid;
        setItDirty(true);
    }


    public Integer getElrActivityLogSeq()
    {
        return elrActivityLogSeq;
    }

    public void setElrActivityLogSeq(Integer aElrActivityLogSeq)
    {
        elrActivityLogSeq = aElrActivityLogSeq;
        setItDirty(true);
    }


    public String getFillerNbr()
    {
        return fillerNbr;
    }

    public void setFillerNbr(String aFillerNbr)
    {
        fillerNbr = aFillerNbr;
        setItDirty(true);
    }


    public String getId()
    {
        return id;
    }

    public void setId(String aId)
    {
        id = aId;
        setItDirty(true);
    }


    public String getOdsObservationUid()
    {
        return odsObservationUid;
    }

    public void setOdsObservationUid(String aOdsObservationLocalUid)
    {
        odsObservationUid = aOdsObservationLocalUid;
        setItDirty(true);
    }


    public String getStatusCd()
    {
        return statusCd;
    }

    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }


    public Timestamp getProcessTime()
    {
        return processTime;
    }

    public void setProcessTime(Timestamp aProcessTime)
    {
        processTime = aProcessTime;
        setItDirty(true);
    }


    public String getProcessCd()
    {
        return processCd;
    }

    public void setProcessCd(String aProcessCd)
    {
        processCd = aProcessCd;
        setItDirty(true);
    }


     public String getSubjectNm()
    {
        return subjectNm;
    }

    public void setSubjectNm(String aSubjectNm)
    {
        subjectNm = aSubjectNm;
        setItDirty(true);
    }

    public void setReportingFacNm(String reportingFacNm) {
      this.reportingFacNm = reportingFacNm;
    }

    public String getReportingFacNm() {
      return reportingFacNm;
    }
    public void setProcessMessageCollection(Collection<Object> aCollection){
      processMessageCollection  = aCollection;
    }
    public Collection<Object>  getProcessMessageCollection(){
      return processMessageCollection;
    }



     public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    return true;
   }


   public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }


   public boolean isItDirty() {
     return itDirty;
   }


   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }


   public boolean isItNew() {
     return itNew;
   }
   public boolean isItDelete() {
     return itDelete;
   }


    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }


/**************methods required by RootDTInterface**************************/
   /**
    *
    * @return  Integer
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
    * @return  String
    */
   public String getProgAreaCd() {

       return progAreaCd;
   }

   /**
    *
    * @param aProgAreaCd
    */
   public void setProgAreaCd(String aProgAreaCd) {
       progAreaCd = aProgAreaCd;
       setItDirty(true);
   }

   /**
    *
    * @return  String
    */
   public String getJurisdictionCd() {

       return jurisdictionCd;
   }

   /**
    *
    * @param aJurisdictionCd
    */
   public void setJurisdictionCd(String aJurisdictionCd) {
       jurisdictionCd = aJurisdictionCd;
       setItDirty(true);
   }

   /**
    *
    * @return  Long
    */
   public Long getProgramJurisdictionOid() {

       return programJurisdictionOid;
   }

   /**
    *
    * @param aProgramJurisdictionOid
    */
   public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
       programJurisdictionOid = aProgramJurisdictionOid;
       setItDirty(true);
   }

   /**
    *
    * @return  String
    */
   public String getSharedInd() {

       return sharedInd;
   }

   /**
    *
    * @param aSharedInd
    */
   public void setSharedInd(String aSharedInd) {
       sharedInd = aSharedInd;
       setItDirty(true);
   }
   /**
 *
 * @return  Timestamp
 */
public Timestamp getAddTime() {

    return addTime;
}

  /**
   *
   * @param aAddTime
   */
  public void setAddTime(Timestamp aAddTime) {
      addTime = aAddTime;
      setItDirty(true);
  }

  /**
   *
   * @param strTime
   */
  public void setAddTime_s(String strTime) {

      if (strTime == null)

          return;

      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
  }

  /**
   *
   * @return  Long
   */
  public Long getAddUserId() {

      return addUserId;
  }

  /**
   *
   * @param aAddUserId
   */
  public void setAddUserId(Long aAddUserId) {
      addUserId = aAddUserId;
      setItDirty(true);
  }

  /**
     *
     * @return  Long
     */
    public Long getUid() {

        return uid;
    }
    public void setUid(Long aUid){
      uid = aUid;
    }

    /**
       *
       * @return  String
       */
      public String getSuperclass() {

          return NEDSSConstants.CLASSTYPE_ENTITY;
      }

   public void setStatusTime(Timestamp aStatusTime){
     statusTime = aStatusTime;
   }
   /**
 *
 * @return  Timestamp
 */
public Timestamp getStatusTime() {

    return statusTime;
}
  /**
    *
    * @return  Timestamp
    */
   public Timestamp getRecordStatusTime() {

       return recordStatusTime;
   }

   /**
    *
    * @param aRecordStatusTime
    */
   public void setRecordStatusTime(Timestamp aRecordStatusTime) {
       recordStatusTime = aRecordStatusTime;
       setItDirty(true);
   }
   /**
   *
   * @param aRecordStatusCd
   */
  public void setRecordStatusCd(String aRecordStatusCd) {
      recordStatusCd = aRecordStatusCd;
      setItDirty(true);
  }
  /**
 *
 * @return  String
 */
public String getRecordStatusCd() {

    return recordStatusCd;
}

  /**
   *
   * @return  String
   */
  public String getLastChgReasonCd() {

      return lastChgReasonCd;
  }

  /**
   *
   * @param aLastChgReasonCd
   */
  public void setLastChgReasonCd(String aLastChgReasonCd) {
      lastChgReasonCd = aLastChgReasonCd;
      setItDirty(true);
  }

  /**
   *
   * @return  Timestamp
   */
  public Timestamp getLastChgTime() {

      return lastChgTime;
  }

  /**
   *
   * @param aLastChgTime
   */
  public void setLastChgTime(Timestamp aLastChgTime) {
      lastChgTime = aLastChgTime;
      setItDirty(true);
  }

  /**
    *
    * @return  String
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
   /**
     *
     * @return  Long
     */
    public Long getLastChgUserId() {

        return lastChgUserId;
    }

    /**
     *
     * @param aLastChgUserId
     */
    public void setLastChgUserId(Long aLastChgUserId) {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }


}
