//Source file: C:\\All NEDSS Related\\scratch\\gov\\cdc\\nedss\\elr\\helper\\ELRActivityLogSearchDT.java

package gov.cdc.nedss.elr.helper;

import java.util.* ;
import java.io.*;

import java.sql.* ;
import gov.cdc.nedss.util.*;

public class ELRActivityLogSearchDT implements Serializable
{
   private Long msgObservationUid;
   private String fillerNbr;
   private String odsObservationUid;
   private Collection<Object>  statusCd;
   private Timestamp processStartTime;
   private String subjectNm;
   private String reportingFacNm;
   private Timestamp processEndTime;
   private String sortOrder;

   /**
    * @roseuid 3F16F8AC037D
    */
   public ELRActivityLogSearchDT()
   {

   }

   /**
    * Access method for the msgObservationUid property.
    *
    * @return   the current value of the msgObservationUid property
    */
   public Long getMsgObservationUid()
   {
      return msgObservationUid;
   }

   /**
    * Sets the value of the msgObservationUid property.
    *
    * @param aMsgObservationUid the new value of the msgObservationUid property
    */
   public void setMsgObservationUid(Long aMsgObservationUid)
   {
      msgObservationUid = aMsgObservationUid;
   }

   /**
    * Access method for the fillerNbr property.
    *
    * @return   the current value of the fillerNbr property
    */
   public String getFillerNbr()
   {
      return fillerNbr;
   }

   /**
    * Sets the value of the fillerNbr property.
    *
    * @param aFillerNbr the new value of the fillerNbr property
    */
   public void setFillerNbr(String aFillerNbr)
   {
      fillerNbr = aFillerNbr;
   }

   /**
    * Access method for the odsObservationUid property.
    *
    * @return   the current value of the odsObservationUid property
    */
   public String getOdsObservationUid()
   {
      return odsObservationUid;
   }

   /**
    * Sets the value of the odsObservationUid property.
    *
    * @param aOdsObservationUid the new value of the odsObservationUid property
    */
   public void setOdsObservationUid(String aOdsObservationUid)
   {
      odsObservationUid = aOdsObservationUid;
   }

   /**
    * Access method for the statusCd property.
    *
    * @return   the current value of the statusCd property
    */
   public Collection<Object>  getStatusCd()
   {
      return statusCd;
   }

   /**
    * Sets the value of the statusCd property.
    *
    * @param aStatusCd the new value of the statusCd property
    */
   public void setStatusCd(Collection<Object> aStatusCd)
   {
      statusCd = aStatusCd;
   }

   /**
    * Access method for the processStartTime property.
    *
    * @return   the current value of the processStartTime property
    */
   public Timestamp getProcessStartTime()
   {
      return processStartTime;
   }

   /**
    * Sets the value of the processStartTime property.
    *
    * @param aProcessStartTime the new value of the processStartTime property
    */
   public void setProcessStartTime(Timestamp aProcessStartTime)
   {
      processStartTime = aProcessStartTime;
   }

   /**
    * Access method for the subjectNm property.
    *
    * @return   the current value of the subjectNm property
    */
   public String getSubjectNm()
   {
      return subjectNm;
   }

   /**
    * Sets the value of the subjectNm property.
    *
    * @param aSubjectNm the new value of the subjectNm property
    */
   public void setSubjectNm(String aSubjectNm)
   {
      subjectNm = aSubjectNm;
   }

   /**
    * Access method for the Reportinglab_nm property.
    *
    * @return   the current value of the Reportinglab_nm property
    */
   public String getReportingFacNm()
   {
      return reportingFacNm;
   }

   /**
    * Sets the value of the Reportinglab_nm property.
    *
    * @param aReportinglab_nm the new value of the Reportinglab_nm property
    */
   public void setReportingFacNm(String aReportingFacNm)
   {
      reportingFacNm = aReportingFacNm;
   }

   /**
    * Access method for the processEndTime property.
    *
    * @return   the current value of the processEndTime property
    */
   public Timestamp getProcessEndTime()
   {
      return processEndTime;
   }

   /**
    * Sets the value of the processEndTime property.
    *
    * @param aProcessEndTime the new value of the processEndTime property
    */
   public void setProcessEndTime(Timestamp aProcessEndTime)
   {
      processEndTime = aProcessEndTime;
   }

   /**
    * Access method for the sortOrder property.
    *
    * @return   the current value of the sortOrder property
    */
   public String getSortOrder()
   {
      return sortOrder;
   }

   /**
    * Sets the value of the sortOrder property.
    *
    * @param aSortOrder the new value of the sortOrder property
    */
   public void setSortOrder(String aSortOrder)
   {
      sortOrder = aSortOrder;
   }
}
/**
 *
 * void ELRActivityLogSearchDT.setSortOrder(int){
 * sortOrder = aSortOrder;
 * }
 *
 */
