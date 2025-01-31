//Source file: C:\\NBS1.1_Devel_Development\\development\\source\\gov\\cdc\\nedss\\nnd\\ejb\\nndmessageprocessorejb\\dao\\NNDAutoResendDAOImpl.java

package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Collection;

public class NNDAutoResendDAOImpl extends DAOBase
{

	static final LogUtils logger = new LogUtils(NNDAutoResendDAOImpl.class.getName());
  static final String SELECT_COUNT_EXIST_NOT = " "+
   "select count(*) "+
   "from notification n, "+
            " act a, "+
            " act_relationship ar "+
   " where "+
            " ar.source_act_uid = a.act_uid and "+
            " a.act_uid = n.notification_uid and "+
            " (n.record_status_cd in('"+NEDSSConstants.NOTIFICATION_APPROVED_CODE+"','"+NEDSSConstants.NOTIFICATION_PENDING_CODE+"') OR n.AUTO_RESEND_IND='T' ) and "+
            " ar.source_class_cd = 'NOTF' and "+
            " ar.target_class_cd = 'CASE' and "+
            " ar.RECORD_STATUS_CD = 'ACTIVE' and "+
            " ar.target_act_uid in(select target_act_uid "+
                                 " from act_relationship "+
                                 " where source_act_uid = ? and "+
                                      " source_class_cd = ? and "+
                                      " target_class_cd = 'CASE' and "+
                                      " record_status_cd = 'ACTIVE')";



public final String SELECT_COUNT_EXIST_NOT_FOR_OBS = " " +
      "select COUNT(*)" +
      " from act_relationship ar, notification" +
      " where target_act_uid" +
      " IN (select target_act_uid" +
      " from act_relationship" +
      " where source_act_uid =  ? " +
      " and source_class_cd =  ? " +
      " and target_class_cd = 'CASE'" +
      " and record_status_cd = 'ACTIVE')" +
      " AND TYPE_CD = 'Notification'" +
      " AND  notification.NOTIFICATION_UID = ar.source_act_uid" +
      " AND (notification.RECORD_STATUS_CD IN ('APPROVED', 'PEND_APPR') OR notification.AUTO_RESEND_IND='T')";


static final String SELECT_COUNT_EXIST_NOT_FOR_INV = " "+
		   "select count(*) "+
		   "from notification n, "+
		            " act a, "+
		            " act_relationship ar "+
		   " where "+
		            " ar.source_act_uid = a.act_uid and "+
		            " a.act_uid = n.notification_uid and "+
		            " (n.record_status_cd in('"+NEDSSConstants.NOTIFICATION_APPROVED_CODE+"','"+NEDSSConstants.NOTIFICATION_PENDING_CODE+"') OR n.AUTO_RESEND_IND='T' ) and "+
		            " ar.source_class_cd = 'NOTF' and "+
		            " ar.target_class_cd = 'CASE' and "+
		            " ar.RECORD_STATUS_CD = 'ACTIVE' and "+
		            " ar.target_act_uid in(select target_act_uid "+
		                                 " from act_relationship "+
		                                 " where target_act_uid = ? and "+
		                                      " target_class_cd = 'CASE' and "+
		                                      " record_status_cd = 'ACTIVE')";



   /**
    * @roseuid 3EB809F902FD
    */
   public NNDAutoResendDAOImpl()
   {

   }

   /**
    * @param sourceClassCd
    * @param sourceActUid
    * @param vo
    * @return int
    * @roseuid 3EB7B696013F
    */
   public int getCountOfExistingNotifications(String sourceClassCd, Long sourceUid, AbstractVO vo) throws NEDSSAppException
   {

     int count = 0;
     try{
	     ArrayList<Object> arrayList = null;
	     if (vo instanceof VaccinationProxyVO){
		      InterventionDT dt = new InterventionDT();
		      arrayList = new ArrayList<Object> ();
		       arrayList.add(sourceUid);
		       arrayList.add(sourceClassCd);
		       count =  ((Integer) preparedStmtMethod(dt, arrayList, SELECT_COUNT_EXIST_NOT, NEDSSConstants.SELECT_COUNT)).intValue();
	      } else if(vo instanceof LabResultProxyVO || vo instanceof MorbidityProxyVO){
		       ObservationDT dt = new ObservationDT();
		       arrayList = new ArrayList<Object> ();
		       arrayList.add(sourceUid);
		       arrayList.add(sourceClassCd);
		       count = ((Integer) preparedStmtMethod(dt, arrayList, SELECT_COUNT_EXIST_NOT_FOR_OBS, NEDSSConstants.SELECT_COUNT)).intValue();
	      }

       return count;
     }catch(Exception ex){
    	 logger.fatal("Exception  = "+ex.getMessage(), ex);
    	 throw new NEDSSAppException(ex.toString());
     }


   }

   /**
    * @param String sourceClassCd
    * @param String typeCd
    * @param Long sourceUid
    * @return java.util.Collection
    * @roseuid 3EB95290006E
    */
   public Collection<Object>  getAutoResendNotificationSummaries(String sourceClassCd, String typeCd, Long sourceUid)  throws NEDSSAppException
   {
	   try{
		    RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
		    return retrieveSummaryVO.retrieveAutoResendSummaries(sourceClassCd, typeCd, sourceUid);
	   }catch(Exception ex){
	    	 logger.fatal("Exception  = "+ex.getMessage(), ex);
	    	 throw new NEDSSAppException(ex.toString());
	     }

   }
   
   
   /**
	 * @param sourceClassCd
	 * @param typeCd
	 * @param sourceUid
	 * @return
	 * @throws NEDSSAppException
	 */
   public int getCountOfExistingNotificationsByCdsAndUid(String sourceClassCd, String typeCd, Long sourceUid) throws NEDSSAppException
   {
	     logger.debug("sourceClassCd: "+sourceClassCd+", typeCd:"+typeCd+", sourceUid: "+sourceUid);
	     int count = 0;
	     try{
		     ArrayList<Object> arrayList = null;
		     if (NEDSSConstants.TYPE_CD.equals(typeCd)){
			      InterventionDT dt = new InterventionDT();
			      arrayList = new ArrayList<Object> ();
			       arrayList.add(sourceUid);
			       arrayList.add(sourceClassCd);
			       count =  ((Integer) preparedStmtMethod(dt, arrayList, SELECT_COUNT_EXIST_NOT, NEDSSConstants.SELECT_COUNT)).intValue();
		      } else if(NEDSSConstants.LAB_REPORT.equals(typeCd) || NEDSSConstants.MORBIDITY_REPORT.equals(typeCd)){
			       ObservationDT dt = new ObservationDT();
			       arrayList = new ArrayList<Object> ();
			       arrayList.add(sourceUid);
			       arrayList.add(sourceClassCd);
			       count = ((Integer) preparedStmtMethod(dt, arrayList, SELECT_COUNT_EXIST_NOT_FOR_OBS, NEDSSConstants.SELECT_COUNT)).intValue();
		      }
	
		      return count;
	     }catch(Exception ex){
	    	 logger.fatal("Exception  getCountOfExistingNotifications: sourceClassCd: "+sourceClassCd+", typeCd:"+typeCd+", sourceUid: "+sourceUid+", Exception: "+ex.getMessage(), ex);
	    	 throw new NEDSSAppException(ex.toString());
	     }
     
   }
   
   /**
    * getCountOfExistingNotificationsByUid: returns the number of notifications associated to the investigation.
    * @param sourceUid
    * @return
    * @throws NEDSSAppException
    */
   public int getCountOfExistingNotificationsByUid(Long sourceUid) throws NEDSSAppException
   {
	     logger.debug("sourceUid: "+sourceUid);
	     int count = 0;
	     try{
		     ArrayList<Object> arrayList = null;
		          PublicHealthCaseDT dt = new PublicHealthCaseDT();
			      arrayList = new ArrayList<Object> ();
			      arrayList.add(sourceUid);
			      count = ((Integer) preparedStmtMethod(dt, arrayList, SELECT_COUNT_EXIST_NOT_FOR_INV, NEDSSConstants.SELECT_COUNT)).intValue();
		      
	
		      return count;
	     }catch(Exception ex){
	    	 logger.fatal("Exception  getCountOfExistingNotificationsByUid: sourceClassCd: sourceUid: "+sourceUid+", Exception: "+ex.getMessage(), ex);
	    	 throw new NEDSSAppException(ex.toString());
	     }
     
   }
}
