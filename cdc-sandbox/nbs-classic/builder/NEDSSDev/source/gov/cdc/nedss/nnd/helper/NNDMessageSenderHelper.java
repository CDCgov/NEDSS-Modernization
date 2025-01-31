//Source file: C:\\rational_rose_dev\\source\\gov\\cdc\\nedss\\nnd\\helper\\NNDMessageSenderHelper.java

//Source file: C:\\NBS1.1_Rel_Development\\development\\source\\gov\\cdc\\nedss\\nnd\\helper\\NNDMessageSenderHelper.java

//Source file: C:\\NBS1.1_Devel_Development\\development\\source\\gov\\cdc\\nedss\\nnd\\helper\\NNDMessageSenderHelper.java

package gov.cdc.nedss.nnd.helper;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.dt.UpdatedNotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.summaryreport.vo.SummaryReportProxyVO;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NNDActivityLogDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NNDAutoResendDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.rmi.PortableRemoteObject;


public class NNDMessageSenderHelper
{
   static final LogUtils logger = new LogUtils (NNDMessageSenderHelper.class.getName());
   private static gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper instance = new NNDMessageSenderHelper ();

   /**
    * @roseuid 3EB809990331
    */
   private NNDMessageSenderHelper()
   {

   }
   /**
    * @param sourceClassCd
    * @param sourceActUid
    * @param vo
    * @return java.lang.Boolean
    * @throws gov.cdc.nedss.exception.NEDSSAppException
    * @throws gov.cdc.nedss.exception.NEDSSSystemException
    * @roseuid 3EB7B7940003
    */
   public Boolean checkForExistingNotifications(AbstractVO vo) throws NEDSSAppException, NEDSSSystemException
   {
		if (!(vo instanceof VaccinationProxyVO)
			&& !(vo instanceof MorbidityProxyVO)
			&& !(vo instanceof LabResultProxyVO))
			throw new NEDSSSystemException("NND MessageSenderHelper: VO not of type Vacc,Morb,LabReport");
		NNDAutoResendDAOImpl nndARDAO = new NNDAutoResendDAOImpl();
		int count =
			nndARDAO.getCountOfExistingNotifications(
				getActClassCd(vo),
				getRootUid(vo), vo);

		if (count > 0)
			return new Boolean(true);
		else
			return new Boolean(false);
   }

   /**
    * @param vo
    * @param nbsSecurityObj
    * @throws gov.cdc.nedss.exception.NEDSSAppException
    * @throws gov.cdc.nedss.exception.NEDSSConcurrentDataException
    * @roseuid 3EB94C79034B
    */
   public void updateAutoResendNotifications(AbstractVO vo, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException, NEDSSConcurrentDataException
   {
     logger.info("enter NNDMessageSenderHelper.updateAutoResendNotifications--------------");
     if(!(vo instanceof VaccinationProxyVO)&&!(vo instanceof LabResultProxyVO)&&!(vo instanceof MorbidityProxyVO)&&!(vo instanceof InvestigationProxyVO) &&!(vo instanceof PageActProxyVO) &&!(vo instanceof PamProxyVO)&&!(vo instanceof SummaryReportProxyVO))
       throw new NEDSSAppException("vo not instance of VaccinationProxyVO,LabResultProxyVO, or MorbidityProxyVO,PamProxyVO, SummaryReportProxyVO");
     Collection<Object>  notSumVOColl =null;
     PublicHealthCaseDT phcDT = null;
     if(vo instanceof InvestigationProxyVO || vo instanceof PamProxyVO||  vo instanceof PageActProxyVO||  vo instanceof SummaryReportProxyVO ){
    	 if((vo instanceof InvestigationProxyVO)){
    	       InvestigationProxyVO invVO = (InvestigationProxyVO)vo;
    	        phcDT = invVO.thePublicHealthCaseVO.getThePublicHealthCaseDT();
    	        notSumVOColl = invVO.getTheNotificationSummaryVOCollection();
    	 }
    	 else if(vo instanceof PamProxyVO	){
   		  PamProxyVO pamVO = (PamProxyVO)vo;
  	        phcDT = pamVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
  	        notSumVOColl = pamVO.getTheNotificationSummaryVOCollection();
   	 }else if(vo instanceof LabResultProxyVO){
	       NNDAutoResendDAOImpl nndAutoResendDAO = new NNDAutoResendDAOImpl();
	       Collection<Object>  theNotificationCollection  = nndAutoResendDAO.getAutoResendNotificationSummaries(getActClassCd(vo), getTypeCd(vo), getRootUid(vo));
	      Iterator<Object>  notIter = theNotificationCollection.iterator();
	       while(notIter.hasNext()){
	         NotificationSummaryVO notSumVO = (NotificationSummaryVO)notIter.next();
	         updateNotification(false, notSumVO.getNotificationUid(),notSumVO.getCd(),notSumVO.getCaseClassCd(),notSumVO.getProgAreaCd(),notSumVO.getJurisdictionCd(),notSumVO.getSharedInd(), false, nbsSecurityObj);
	       }
	     }
    	 else if(vo instanceof PageActProxyVO	){
    		 PageActProxyVO pageActProxyVO= (PageActProxyVO)vo;
  	        phcDT = pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
  	        notSumVOColl = pageActProxyVO.getTheNotificationSummaryVOCollection();
   	 }
    	 else if(vo instanceof SummaryReportProxyVO	){
    		 SummaryReportProxyVO summaryReportProxyVO = (SummaryReportProxyVO)vo;
  	        phcDT = summaryReportProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
  	        notSumVOColl = summaryReportProxyVO.getTheNotificationVOCollection();
  	     Iterator<Object>  notSumIter =  notSumVOColl.iterator();
  	       while(notSumIter.hasNext()){
  	         NotificationVO notVO = (NotificationVO)notSumIter.next();
  	           Long notificationUid = notVO.getTheNotificationDT().getNotificationUid();
  	           String phcCd = phcDT.getCd();
  	           String phcClassCd = phcDT.getCaseClassCd();
  	           String progAreaCd = phcDT.getProgAreaCd();
  	           String jurisdictionCd = phcDT.getJurisdictionCd();
  	           String sharedInd = phcDT.getSharedInd();
  	           // retrieve the status change
  	           boolean caseStatusChange = phcDT.isCaseStatusDirty();
  	           updateNotification(true, notificationUid,phcCd,phcClassCd,progAreaCd,jurisdictionCd,sharedInd, caseStatusChange, nbsSecurityObj);
  	       }
   	 }
    if(vo instanceof InvestigationProxyVO || vo instanceof PamProxyVO || vo instanceof PageActProxyVO)	 
    {
		if(notSumVOColl!=null && notSumVOColl.size()>0){
      Iterator<Object>  notSumIter =  notSumVOColl.iterator();
       while(notSumIter.hasNext()){
         NotificationSummaryVO notSummaryVO = (NotificationSummaryVO)notSumIter.next();
         if(notSummaryVO.getIsHistory().equals("F") && !notSummaryVO.getAutoResendInd().equals("F")){
           Long notificationUid = notSummaryVO.getNotificationUid();
           String phcCd = phcDT.getCd();
           String phcClassCd = phcDT.getCaseClassCd();
           String progAreaCd = phcDT.getProgAreaCd();
           String jurisdictionCd = phcDT.getJurisdictionCd();
           String sharedInd = phcDT.getSharedInd();

           // retrieve the status change
           boolean caseStatusChange = phcDT.isCaseStatusDirty();
           updateNotification(false, notificationUid,phcCd,phcClassCd,progAreaCd,jurisdictionCd,sharedInd, caseStatusChange, nbsSecurityObj);

         }
       }
     }
	}

     }
     else if(vo instanceof VaccinationProxyVO || vo instanceof MorbidityProxyVO){
       NNDAutoResendDAOImpl nndAutoResendDAO = new NNDAutoResendDAOImpl();
       Collection<Object>  theNotificationCollection  = nndAutoResendDAO.getAutoResendNotificationSummaries(getActClassCd(vo), getTypeCd(vo), getRootUid(vo));
      Iterator<Object>  notIter = theNotificationCollection.iterator();
       while(notIter.hasNext()){
         NotificationSummaryVO notSumVO = (NotificationSummaryVO)notIter.next();
         updateNotification(false, notSumVO.getNotificationUid(),notSumVO.getCd(),notSumVO.getCaseClassCd(),notSumVO.getProgAreaCd(),notSumVO.getJurisdictionCd(),notSumVO.getSharedInd(), false, nbsSecurityObj);
       }
     }
     logger.info("finish NNDMessageSenderHelper.updateAutoResendNotifications--------------");
   }

   /**
    * @param vo
    * @return java.lang.String
    * @roseuid 3EBAA9B40399
    */
   public String getActClassCd(AbstractVO vo)
   {
		if (vo == null)
			return null;

		/*Both lab and morb class codes are OBS */
		if (vo instanceof MorbidityProxyVO) {
			// return "OBS"
			return NEDSSConstants.CLASS_CD_OBS;
		} else if (vo instanceof LabResultProxyVO) {
			// return "OBS"
			return NEDSSConstants.CLASS_CD_OBS;
		} else if (vo instanceof VaccinationProxyVO) {
			// return "INTV"
			return NEDSSConstants.CLASS_CD_INTV;
		}
		return null;
   }

   /**
    * @param vo
    * @return java.lang.String
    *
    */
   public String getTypeCd(AbstractVO vo)
   {
                if (vo == null)
                        return null;

                if (vo instanceof MorbidityProxyVO) {
                        // return "MorbReport"
                        return NEDSSConstants.MORBIDITY_REPORT;
                } else if (vo instanceof LabResultProxyVO) {
                        // return "LabReport"
                        return NEDSSConstants.LAB_REPORT;
                } else if (vo instanceof VaccinationProxyVO) {
                        // return "1180"
                        return NEDSSConstants.TYPE_CD;
                }
                return null;
   }

   /**
    * @param vo
    * @return java.lang.Long
    * @roseuid 3EBAA9B50000
    */
   public Long getRootUid(AbstractVO vo)
   {
		if (vo == null)
			return null;

		// for now only implement three
		if (vo instanceof MorbidityProxyVO) {
			// the root Morb observation UID out of the observation collection
			Collection<ObservationVO>  obsColl =
				((MorbidityProxyVO) vo).getTheObservationVOCollection();
			Iterator<ObservationVO> iter = obsColl.iterator();
			while (iter.hasNext()) {
				ObservationVO observationVO = (ObservationVO) iter.next();
				String ctrlCdDisplayForm =
					observationVO.getTheObservationDT().getCtrlCdDisplayForm();
				if (ctrlCdDisplayForm != null
					&& ctrlCdDisplayForm.equalsIgnoreCase(
						NEDSSConstants.MOB_CTRLCD_DISPLAY))
					return observationVO
						.getTheObservationDT()
						.getObservationUid();
			}
		} else if (vo instanceof LabResultProxyVO) {
			// the root Lab observation UID out of the observation collection
			Collection<ObservationVO>  obsColl =
				((LabResultProxyVO) vo).getTheObservationVOCollection();
			Iterator<ObservationVO> iter = obsColl.iterator();
			while (iter.hasNext()) {
				ObservationVO observationVO = (ObservationVO) iter.next();
				String obsDomainCdSt1 =
					observationVO.getTheObservationDT().getObsDomainCdSt1();
				String obsCtrlCdDisplayForm =
					observationVO.getTheObservationDT().getCtrlCdDisplayForm();
				if (obsDomainCdSt1 != null
					&& obsDomainCdSt1.equalsIgnoreCase(
						NEDSSConstants.ORDERED_TEST_OBS_DOMAIN_CD)
					&& obsCtrlCdDisplayForm != null
					&& obsCtrlCdDisplayForm.equalsIgnoreCase(
						NEDSSConstants.LAB_REPORT)) {
					return observationVO
						.getTheObservationDT()
						.getObservationUid();
				}
			}

		} else if (vo instanceof VaccinationProxyVO) {
			// the vaccination UID
			return ((VaccinationProxyVO) vo)
				.theInterventionVO
				.getTheInterventionDT()
				.getInterventionUid();
		}
		return null;
   }

   /**
    * @return gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper
    * @roseuid 3EC8DC8D001F
    */
   public static NNDMessageSenderHelper getInstance()
   {
		return instance;
   }


   /**
    * @param vo
    * @param nbsSecurityObj
    * @throws gov.cdc.nedss.exception.NEDSSAppException
    * @throws gov.cdc.nedss.exception.NEDSSConcurrentDataException
    * @roseuid 3EC8DC8D03D8
    */
   public void updateAutoResendNotificationsAsync(AbstractVO vo,
			NBSSecurityObj nbsSecurityObj) throws NEDSSAppException,
			NEDSSConcurrentDataException {
		logger.info("enter NNDMessageSenderHelper.updateAutoResendNotificationsAsync--------------");
		try{
				    updateAutoResendNotifications(vo,nbsSecurityObj);
			        logger.info("finish NNDMessageSenderHelper.updateAutoResendNotificationsAsync()---------------------");
			      }
			      catch(NEDSSConcurrentDataException nce){
			        logger.error("NEDSSConcurrentDataException thrown in NNDMessageSenderHelper"+nce.getMessage());
			        throw new NEDSSConcurrentDataException("The Person you are trying to create Investigation no Longer exists");
			      }
			      catch(NEDSSAppException nae){
		  	        logger.error("NEDSSAppException occurred while calling the updateAutoResendNotificationsAsync"+nae.getMessage());
		  	        throw new NEDSSAppException("Error in calling updateAutoResendNotifications()" + nae.toString());
	      		  }

			      catch(Exception e){
			        logger.error("Exception occurred while calling the updateAutoResendNotificationsAsync"+e.getMessage());
			        e.printStackTrace();
	      }
	}
   /**
    * @param notificationUid
    * @param phcCd
    * @param phcClassCd
    * @param progAreaCd
    * @param jurisdictionCd
    * @param sharedInd
    * @param nbsSecurityObj
    * @throws gov.cdc.nedss.exception.NEDSSAppException
    * @throws gov.cdc.nedss.exception.NEDSSConcurrentDataException
    * @roseuid 3EC8E66300EA
    */
   public void updateNotification(boolean isSummaryCase, Long notificationUid, String phcCd,
   		String phcClassCd, String progAreaCd, String jurisdictionCd,
   		String sharedInd, boolean caseStatusChange,
   		NBSSecurityObj nbsSecurityObj) throws NEDSSAppException, NEDSSConcurrentDataException
   {
     logger.info("begin NNDMessageSenderHelper.updateNotification() for notificationuid="+notificationUid);
     boolean checkNotificationPermission = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE,progAreaCd,jurisdictionCd,sharedInd);
     boolean checkNotificationPermission1 = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL,progAreaCd,jurisdictionCd,sharedInd);
     String businessTriggerCd = null;
     if(isSummaryCase){
    	   businessTriggerCd = NEDSSConstants.NOT_CR_APR;
     }
     else if(!checkNotificationPermission && !checkNotificationPermission1){
       logger.info("No create notification permissions for updateNotification");
                throw new NEDSSSystemException("NO CREATE NOTIFICATION PERMISSIONS for updateNotification");
     }
     else
     {
       // In auto resend scenario, the change to investigation or
       // any associated object puts the notification in APPROVED queue

       businessTriggerCd = NEDSSConstants.NOT_CR_APR;
     }

     //logger.debug("BusinessTriggerCd is = " + businessTriggerCd);
     NedssUtils nedssUtils = new NedssUtils();
     Collection<Object>  notificationVOCollection  = null;
     Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
     try
     {
       ActControllerHome ecHome = (ActControllerHome) PortableRemoteObject.narrow(theLookedUpObject,ActControllerHome.class);
       ActController actController = ecHome.create();
       NotificationVO notificationVO = actController.getNotification(notificationUid,nbsSecurityObj);
       PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
       NotificationDT newNotificationDT = null;
       NotificationDT notificationDT = notificationVO.getTheNotificationDT();
       notificationDT.setProgAreaCd(progAreaCd);
       notificationDT.setJurisdictionCd(jurisdictionCd);
       notificationDT.setCaseConditionCd(phcCd);
       notificationDT.setSharedInd(sharedInd);
       notificationDT.setCaseClassCd(phcClassCd);
       notificationVO.setItDirty(true);
       notificationDT.setItDirty(true);

       //retreive the new NotificationDT generated by PrepareVOUtils
       newNotificationDT = (NotificationDT) prepareVOUtils.prepareVO(
       notificationDT, NBSBOLookup.NOTIFICATION, businessTriggerCd,
       DataTables.NOTIFICATION_TABLE, NEDSSConstants.BASE, nbsSecurityObj);

       //replace old NotificationDT in NotificationVO with the new NotificationDT
       notificationVO.setTheNotificationDT(newNotificationDT);

       // If the user has "NEEDS APPROVAL" permissions and the notification
       // is in AUTO_RESEND status, new record is created for review.
       // This record is visible in Updated Notifications Queue

       if(checkNotificationPermission1 &&
       	(notificationDT.getAutoResendInd().equalsIgnoreCase("T"))){
       		UpdatedNotificationDT updatedNotification =
       			new UpdatedNotificationDT();

       		updatedNotification.setAddTime(new Timestamp(System.currentTimeMillis()));
       		updatedNotification.setAddUserId(
       			new Long(Long.parseLong(nbsSecurityObj.getEntryID())));
       		updatedNotification.setCaseStatusChg(caseStatusChange);
       		updatedNotification.setItNew(true);
       		updatedNotification.setNotificationUid(notificationDT.getNotificationUid());
       		updatedNotification.setStatusCd("A");
       		updatedNotification.setCaseClassCd(notificationDT.getCaseClassCd());
       		notificationVO.setTheUpdatedNotificationDT(updatedNotification);
       	}

       Long newNotficationUid = actController.setNotification(notificationVO,nbsSecurityObj);
     }
        catch (Exception e)
        {
               e.printStackTrace();
               logger.error("Error calling ActController.setNotification() " + e.getMessage());
               throw new NEDSSSystemException("Error in calling ActControllerEJB.setNotification()" + e.toString());
        }

      logger.info("updateNotification on NNDMessageSenderHelper complete");
  }//updateNotification



   /**
    * @param nndActivityLogDT
    * @param nbsSecurityOBJ
    * @roseuid 3ECCE42E0137
    */
   public void persistNNDActivityLog(NNDActivityLogDT nndActivityLogDT, NBSSecurityObj nbsSecurityOBJ)throws  NEDSSSystemException
   {
     try
     {
       NNDActivityLogDAOImpl nndActivityLogDAOImpl = new NNDActivityLogDAOImpl();
       nndActivityLogDT.setNndActivityLogSeq(new Integer(1));// default to 1
       nndActivityLogDT.setRecordStatusCd("AUTO_RESEND_ERROR");
       nndActivityLogDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
       nndActivityLogDT.setStatusCd("E");
       nndActivityLogDT.setStatusTime(new Timestamp( new Date().getTime() ) );

       long uid = 0;//dummy
       nndActivityLogDAOImpl.create(uid, nndActivityLogDT);
      }
    catch (Exception e)
    {
      throw new NEDSSSystemException(e.toString());
    }

   }
   
   
   /**
	 * @param actClassCd
	 * @param typeCd
	 * @param eventUid
	 * @param nbsSecurityObj
	 * @throws NEDSSAppException
	 * @throws NEDSSConcurrentDataException
	 */
   public void updateAutoResendNotificationsByCdsAndUid(String actClassCd, String typeCd, Long eventUid, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException, NEDSSConcurrentDataException
   {
	   logger.debug("actClassCd: "+actClassCd+", typeCd:"+typeCd+", rootUid: "+eventUid);
	   try{
		   NNDAutoResendDAOImpl nndAutoResendDAO = new NNDAutoResendDAOImpl();
	       Collection<Object>  theNotificationCollection  = nndAutoResendDAO.getAutoResendNotificationSummaries(actClassCd, typeCd, eventUid);
	       Iterator<Object>  notIter = theNotificationCollection.iterator();
	       while(notIter.hasNext()){
		       NotificationSummaryVO notSumVO = (NotificationSummaryVO)notIter.next();
		       updateNotification(false, notSumVO.getNotificationUid(),notSumVO.getCd(),notSumVO.getCaseClassCd(),notSumVO.getProgAreaCd(),notSumVO.getJurisdictionCd(),notSumVO.getSharedInd(), false, nbsSecurityObj);
	       }
	   }catch(Exception ex){
		   logger.error("Exception updateAutoResendNotifications:  actClassCd: "+actClassCd+", typeCd:"+typeCd+", eventUid: "+eventUid+", Exception: " + ex.getMessage());
           throw new NEDSSSystemException("Exception updateAutoResendNotifications:  actClassCd: "+actClassCd+", typeCd:"+typeCd+", eventUid: "+eventUid+", Exception: " + ex.toString());
	   }
   }
   
   
   /**
	 * @param actClassCd
	 * @param typeCd
	 * @param eventUid
	 * @return
	 * @throws NEDSSAppException
	 * @throws NEDSSSystemException
	 */
   public Boolean checkForExistingNotificationsByCdsAndUid(String actClassCd, String typeCd, Long eventUid) throws NEDSSAppException, NEDSSSystemException
   {
	   logger.debug("actClassCd: "+actClassCd+", typeCd:"+typeCd+", eventUid: "+eventUid);
	   try{
			NNDAutoResendDAOImpl nndARDAO = new NNDAutoResendDAOImpl();
			int count =	nndARDAO.getCountOfExistingNotificationsByCdsAndUid(actClassCd, typeCd, eventUid );
	
			if (count > 0)
				return new Boolean(true);
			else
				return new Boolean(false);
	   }catch(Exception ex){
		   logger.error("Exception checkForExistingNotifications:  actClassCd: "+actClassCd+", typeCd:"+typeCd+", eventUid: "+eventUid+", Exception: " + ex.getMessage());
           throw new NEDSSSystemException("Exception checkForExistingNotifications:  actClassCd: "+actClassCd+", typeCd:"+typeCd+", eventUid: "+eventUid+", Exception: " + ex.toString());
	   }
   }
   
   
   /**
    * checkForExistingNotificationsByUid: returns true if there's any notification associated to the investigation uid
    * @param eventUid
    * @return
    * @throws NEDSSAppException
    * @throws NEDSSSystemException
    */
   public Boolean checkForExistingNotificationsByUid(Long eventUid) throws NEDSSAppException, NEDSSSystemException
   {
	   logger.debug("eventUid: "+eventUid);
	   try{
			NNDAutoResendDAOImpl nndARDAO = new NNDAutoResendDAOImpl();
			int count =	nndARDAO.getCountOfExistingNotificationsByUid(eventUid );
	
			if (count > 0)
				return new Boolean(true);
			else
				return new Boolean(false);
	   }catch(Exception ex){
		   logger.error("Exception checkForExistingNotifications:  actClassCd: eventUid: "+eventUid+", Exception: " + ex.getMessage());
           throw new NEDSSSystemException("Exception checkForExistingNotifications:  actClassCd: eventUid: "+eventUid+", Exception: " + ex.toString());
	   }
   }
   
   
}
