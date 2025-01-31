package gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.dt.UpdatedNotificationDT;
import gov.cdc.nedss.act.notification.ejb.dao.NotificationDAOImpl;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxy;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyHome;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.dao.NotificationProxyDaoImpl;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.dao.UpdatedNotificationDAOImpl;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.RejectedNotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.UpdatedNotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util. Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;

/**
 * Title: NotificationProxyEJB
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class NotificationProxyEJB
    implements javax.ejb.SessionBean {
	
	private static final long serialVersionUID = 1L;
  /*
     Attributes declaration
   */
  public javax.ejb.SessionContext EJB_Context = null;
  static final LogUtils logger = new LogUtils(NotificationProxyEJB.class.
                                              getName());
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  // public static LogUtils logger =  (LogUtils) LogUtils.getLoggerInstance(NotificationProxyEJB.class.getName());

  /**
   * @roseuid 3C43680C03DE
   * @J2EE_METHOD  --  NotificationProxyEJB
   */
  public NotificationProxyEJB() {
  }

  /**
   * @roseuid 3C43680D003C
   * @J2EE_METHOD  --  ejbRemove
   * A container invokes this method before it ends the life of the session object. This
   * happens as a result of a client's invoking a remove operation, or when a container
   * decides to terminate the session object after a timeout. This method is called with
   * no transaction context.
   */
  public void ejbRemove() {
  }

  /**
   * @roseuid 3C43680D0050
   * @J2EE_METHOD  --  ejbActivate
   * The activate method is called when the instance is activated from its 'passive' state.
   * The instance should acquire any resource that it has released earlier in the ejbPassivate()
   * method. This method is called with no transaction context.
   */
  public void ejbActivate() {
  }

  /**
   * @roseuid 3C43680D0065
   * @J2EE_METHOD  --  ejbPassivate
   * The passivate method is called before the instance enters the 'passive' state. The
   * instance should release any resources that it can re-acquire later in the ejbActivate()
   * method. After the passivate method completes, the instance must be in a state that
   * allows the container to use the Java Serialization protocol to externalize and store
       * away the instance's state. This method is called with no transaction context.
   */
  public void ejbPassivate() {
  }

  /**
   * @roseuid 3C437F5001F8
   * @J2EE_METHOD  --  setSessionContext
   * Set the associated session context. The container calls this method after the instance
   * creation. The enterprise Bean instance should store the reference to the context
   * object in an instance variable. This method is called with no transaction context.
   * @param SessionContext
   */
  public void setSessionContext(SessionContext sessioncontext) throws
      EJBException, RemoteException {
  }

  /**
   * @roseuid 3C446CD103DB
   * @J2EE_METHOD  --  ejbCreate
   * Called by the container to create a session bean instance. Its parameters typically
   * contain the information the client uses to customize the bean instance for its use.
   * It requires a matching pair in the bean class and its home interface.
   */
  public void ejbCreate() {
  }

  /**
   * @roseuid 3D6CE1270111
   * @J2EE_METHOD  --  setSummaryNotificationProxy
   * @param NotificationProxyVO
   * @param NBSSecurityObj
   * @return Long
   */
  public Long setSummaryNotificationProxy(NotificationProxyVO
                                          notificationProxyVO,
                                          NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException, NEDSSSystemException,
      NEDSSConcurrentDataException {

   Iterator<Object>  anIterator = null;
    Long publicHealthCaseUid = null;
    Long notificationUid = null;
    ActRelationshipDAOImpl actRelationshipDAOImpl = null;
    String permissionFlag = "";
    Collection<Object>  act2 = new ArrayList<Object> ();

    try {

      if (notificationProxyVO == null) {
        logger.error("NotificationProxyEJB, setSummaryNotificationProxy(): notificationproxyVO is null. ");
        throw new NEDSSSystemException("notificationproxyVO is null ");

        // throw exception
      }

      String programAreaCode = notificationProxyVO.getThePublicHealthCaseVO().
          getThePublicHealthCaseDT().getProgAreaCd();
      String jurisdictionCode = ProgramAreaJurisdictionUtil.ANY_JURISDICTION;

      if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
                                        NEDSSConstants.
                                        CREATE_SUMMARY_NOTIFICATION,
                                        programAreaCode,
                                        jurisdictionCode)) {
        logger.info("no add permissions for setSummaryNotificationProxy");
        throw new NEDSSSystemException(
            "NO Create PERMISSIONS for setSummaryNotificationProxy");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      logger.fatal(e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage(), e);
    }
    NotificationVO notifVO = notificationProxyVO.getTheNotificationVO();

    if (notifVO == null) {
      logger.error(
          "NotificationProxyEJB, setSummaryNotificationProxy(): notificationVO is null. ");
      throw new NEDSSSystemException("notificationVO is null ");
    }

    NotificationDT notifDT = notifVO.getTheNotificationDT();
    notifDT.setCaseConditionCd(notificationProxyVO.getThePublicHealthCaseVO().
                               getThePublicHealthCaseDT().getCd());
    notifDT.setCaseClassCd(notificationProxyVO.getThePublicHealthCaseVO().
                           getThePublicHealthCaseDT().getCaseClassCd());
    notifDT.setProgAreaCd(notificationProxyVO.getThePublicHealthCaseVO().
                          getThePublicHealthCaseDT().getProgAreaCd());

    notifDT.setCd(NEDSSConstants.NOTIFICATION_CD);

    String boLookup = NBSBOLookup.NOTIFICATION;
    String triggerCd = NEDSSConstants.NOT_CR_APR;
    String tableName = DataTables.NOTIFICATION_TABLE;
    String moduleCd = NEDSSConstants.BASE;

    // prepare the VO
    PrepareVOUtils pre = new PrepareVOUtils();

    try {
      notifDT = (NotificationDT) pre.prepareVO(notifDT, boLookup, triggerCd,
                                               tableName, moduleCd,
                                               nbsSecurityObj);
      logger.info("setSummaryNotificationProxy - LocalId = " +
                  notifDT.getLocalId());
      notifVO.setTheNotificationDT(notifDT);

      //initialize the activity controller
      ActController actController = null;
      NedssUtils nedssUtils = new NedssUtils();
      Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
      logger.debug("ActController lookup = " + object.toString());

      ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
          narrow(object, ActControllerHome.class);
      logger.debug("Found ActControllerHome: " + acthome);
      actController = acthome.create();

      Long falseUid = null;
      Long realUid = null;
      realUid = actController.setNotification(notifVO, nbsSecurityObj);
      notificationUid = realUid;
      falseUid = notifVO.getTheNotificationDT().getNotificationUid();

      if (notifVO.isItNew()) {

        ActRelationshipDAOImpl actRelDao = new ActRelationshipDAOImpl();
        ActRelationshipDT actRelDT = null;
        actRelDT = setFalseToNew(notificationProxyVO, falseUid, realUid);
        notifDT.setNotificationUid(realUid);
        notifVO.setTheNotificationDT(notifDT);
        notificationProxyVO.setTheNotificationVO(notifVO);
        act2.add(actRelDT);
        notificationProxyVO.setTheActRelationshipDTCollection(act2);
        actRelDao.store(actRelDT);
      }
    }
    catch (NEDSSConcurrentDataException e) {
      logger.fatal(
          "The entity cannot be updated as concurrent access is not allowed!"+e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage(), e);
    }
    catch (Exception e) {
      logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
      e.printStackTrace();
      throw new NEDSSSystemException(e.getMessage(), e);
    }
    //} // end of if new or dirty
    return notificationUid;
  }

  /**
 * @param notificationUID
 * @param nbsSecurityObj
 * @return
 * @throws javax.ejb.EJBException
 * @throws NEDSSSystemException
 */
  public NotificationProxyVO getNotificationProxyForMerge(Long notificationUID,
          NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,NEDSSSystemException {
	  try{
		  NotificationProxyVO notificationProxyVO= getNotificationProxy(notificationUID,nbsSecurityObj);
		  return notificationProxyVO;
	  }catch(Exception ex){
		  logger.fatal("Exception: "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.getMessage(), ex);
	  }
  }

  /**
   * @roseuid 3C46274003B7
   * @J2EE_METHOD  --  getNotificationProxy
   * @param Long notificationUI
   * @param NBSSecurityObj
   * @return NotificationProxyVO
   */
  public NotificationProxyVO getNotificationProxy(Long notificationUID,
                                                  NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      NEDSSSystemException {
    try {
		logger.info("getNotificationProxy-----------------");

		NotificationProxyDaoImpl notificationProxyDaoImpl =
		    new NotificationProxyDaoImpl();
		NotificationProxyVO notificationProxyVO = notificationProxyDaoImpl.
		    getNotificationProxy(
		    notificationUID,
		    nbsSecurityObj);

		return notificationProxyVO;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }

  /**
   * @roseuid 3C462741005B
   * @J2EE_METHOD  --  setNotificationProxy
   * @param NotificationProxyVO
   * @param NBSSecurityObj
   * @return Long
   */
  public Long setNotificationProxy(NotificationProxyVO notificationProxyVO,
                                   NBSSecurityObj nbsSecurityObj) throws java.
      rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException,
      NEDSSConcurrentDataException

  {

    try {
      NotificationProxyDaoImpl notificationProxyDaoImpl = new
          NotificationProxyDaoImpl();
      Long notificationUid = notificationProxyDaoImpl.setNotificationProxy(
          notificationProxyVO,
          nbsSecurityObj);
      return notificationUid;
    }
    catch (Exception ex) {
      logger.fatal("setNotificationProxy: " + ex.getMessage(), ex);
      ex.printStackTrace();
      throw new NEDSSSystemException(ex.getMessage(),ex);
    }
  }

  /**
   * This method checks for the negative uid value for any ACT & ENTITY DT then compare them
   * with respective negative values in ActRelationshipDT and ParticipationDT as received from
   * the notificationProxyVO(determined in the addnotification method).
   * As it has also got the actualUID (determined in the addnotification method) it replaces them accordingly.
   * @param NotificationProxyVO
   * @param Long falseUid
   * @param Long actualUid
   * @return ActRelationshipDT
   */
  private ActRelationshipDT setFalseToNew(NotificationProxyVO
                                          notificationProxyVO,
                                          Long falseUid, Long actualUid) {

   try {
	Iterator<Object>  anIterator = null;
	    ActRelationshipDAOImpl actRelationshipDAOImpl = null;
	    ActRelationshipDT actRelationshipDT = null;
	    Collection<Object>  actRelationShipColl = (ArrayList<Object> ) notificationProxyVO.
	        getTheActRelationshipDTCollection();
	    Collection<Object>  act2 = new ArrayList<Object> ();
	
	    if (actRelationShipColl != null) {
	
	      for (anIterator = actRelationShipColl.iterator();
	           anIterator.hasNext(); ) {
	        actRelationshipDT = (ActRelationshipDT) anIterator.next();
	        actRelationshipDT.setSourceActUid(actualUid);
	        act2.add(actRelationshipDT);
	      }
	    }
	
	    return actRelationshipDT;
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	logger.fatal(e.getMessage(), e);
	throw new NEDSSSystemException(e.getMessage(), e);
}
  }

  /**
   * @roseuid 3C46274100E7
   * @J2EE_METHOD  --  rejectNotification
   * @param NotificationSummaryVO
   * @param NBSSecurityObj
   */
  public void rejectNotification(NotificationSummaryVO notificationSummaryVO,
                                 NBSSecurityObj nbsSecurityObj) throws javax.
      ejb.EJBException,
      NEDSSSystemException,
      NEDSSConcurrentDataException

  {
    logger.debug("In rejectNotification method-------");

    try {

      if (notificationSummaryVO == null) {
        logger.error(
            "NotificationProxyEJB, rejectnotificationProxy(): notifcationSummaryVO is null. ");
        throw new NEDSSSystemException("notifcationSummaryVO is null ");

        // throw exception
      }

      NotificationProxyVO notificationProxyVO = this.getNotificationProxy(
          notificationSummaryVO.getNotificationUid(),
          nbsSecurityObj);
      String programeAreaCode = notificationProxyVO.getThePublicHealthCaseVO()
          .getThePublicHealthCaseDT().getProgAreaCd();
      String jurisdictionCode = notificationProxyVO.getThePublicHealthCaseVO()
          .getThePublicHealthCaseDT().getJurisdictionCd();
      String shared = notificationProxyVO.getThePublicHealthCaseVO().
          getThePublicHealthCaseDT()
          .getSharedInd();

      if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
                                        NBSOperationLookup.REVIEW,
                                        programeAreaCode,
                                        jurisdictionCode, shared)) {
        logger.info("no review permissions for notification");
        throw new NEDSSSystemException("NO REVIEW PERMISSIONS for Notification");
      }
      else {
        // get the notification rootDT
        Long notificationUid = notificationSummaryVO.getNotificationUid();
        ActController actController = null;
        NedssUtils nedssUtils = new NedssUtils();
        Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
        ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
            narrow(object, ActControllerHome.class);
        actController = acthome.create();
        NotificationDT notificationDT = actController.getNotificationInfo(
            notificationUid, nbsSecurityObj);
        notificationDT.setTxt(notificationSummaryVO.getTxt());
        notificationDT.setItDirty(true);
        notificationDT.setItDelete(false);
        notificationDT.setItNew(false);

        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
        NotificationDT newNotificationDT = (NotificationDT) prepareVOUtils.
            prepareVO(
            notificationDT,
            NBSBOLookup.NOTIFICATION,
            NEDSSConstants.NOT_REJ,
            DataTables.NOTIFICATION_TABLE,
            NEDSSConstants.BASE,
            nbsSecurityObj);

        if (newNotificationDT == null) {
          logger.error("newNotificationDT that gets from preparedVO is null");
          throw new NEDSSSystemException(
              "newNotificationDT that gets from preparedVO is null");
        }
        else {
          newNotificationDT.setItDirty(true);
          newNotificationDT.setItNew(false);
          actController.setNotificationInfo(newNotificationDT, nbsSecurityObj);
        }
      }
    }
    catch (NEDSSConcurrentDataException ex) {
      logger.fatal(
          "The entity cannot be updated as concurrent access is not allowed!"+ex.getMessage(), ex);
      throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
    }

    catch (Exception e) {
      logger.fatal("Error in reject Notification." + e.getMessage(),e);
      e.printStackTrace();
      throw new javax.ejb.EJBException(e.getMessage(),e);
    }
  }

  /**
   * @roseuid 3C462741017D
   * @J2EE_METHOD  --  approveNotification
   * @param NotificationSummaryVO
   * @param nbsSecurityObj
   * @return Boolean
   */
  public Boolean approveNotification(NotificationSummaryVO
                                     notificationSummaryVO,
                                     NBSSecurityObj nbsSecurityObj) throws java.
      rmi.RemoteException,
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      NEDSSSystemException,
      NEDSSConcurrentDataException

  {
    try {
      NotificationProxyDaoImpl notificationProxyDaoImpl = new
          NotificationProxyDaoImpl();
      return notificationProxyDaoImpl.approveNotification(notificationSummaryVO,
          nbsSecurityObj);
    }
    catch (NEDSSConcurrentDataException ex) {
      logger.fatal(
          "The entity cannot be updated as concurrent access is not allowed!"+ex.getMessage(),ex);
      throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
    }
    catch (Exception e) {
      e.printStackTrace();
      logger.fatal("error in NotificationProxyEJB - completeNotification - " + e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage(),e);
    }
  }

  /**
   * @roseuid 3C462741021E
   * @J2EE_METHOD  --  getNotificationVOs
   * @param NBSSecurityObj
   * @return Collection
   */
  public Collection<Object>  getNotificationsForApproval(NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      NEDSSSystemException {
    try {
		NotificationProxyDaoImpl notificationProxyDaoImpl = new
		    NotificationProxyDaoImpl();
		ArrayList<Object>  notificationProxyVOCollection  = (ArrayList<Object> )
		    notificationProxyDaoImpl.getNotificationsForApproval(nbsSecurityObj);

		/*NBSAuthHelper helper = new NBSAuthHelper();

		for(int i= 0; i < notificationProxyVOCollection.size(); i++){

			NotificationSummaryVO vo = (NotificationSummaryVO) notificationProxyVOCollection.get(i);

			vo.setAddUserName(helper.getUserName(vo.getAddUserId()));
		}*/

		return notificationProxyVOCollection;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }

/*
  private User getUser(Long userId) throws
	NEDSSSystemException, javax.ejb.EJBException{

		NedssUtils nedssUtils = new NedssUtils();
		Object obj = nedssUtils.lookupBean(JNDINames.SECURITY_EJB);
		logger.debug("ObservationEJB lookup = " + obj.toString());

		NBSAuthHome home = (NBSAuthHome)PortableRemoteObject.narrow(
									   obj, NBSAuthHome.class);
		logger.debug("Found AuthHome: " + home);

		User user = null;
		try {
			NBSAuth security = home.create();

			user = security.getUser(userId);
		} catch (RemoteException e) {
			throw new EJBException(e);
		} catch (EJBException e) {
			throw new EJBException(e);
		} catch (NEDSSSystemException e) {
			throw e;
		} catch (CreateException e) {
			throw new EJBException(e);
		}

		return user;
  }
*/

  /**
   * @roseuid 3C4627410282
   * @J2EE_METHOD  --  getPublicHealthCaseVO
   * @param Long publicHealthCaseUID
   * @param NBSSecurityObj
   * @return PublicHealthCaseDT
   */
  public PublicHealthCaseDT getPublicHealthCaseDT(Long publicHealthCaseUID,
                                                  NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      NEDSSSystemException {
    NedssUtils nedssUtils = new NedssUtils();
    PublicHealthCaseDT pubDT = new PublicHealthCaseDT();
    try {
      ActController actController = null;
      nedssUtils = new NedssUtils();

      Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);

      ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
          narrow(object, ActControllerHome.class);
      actController = acthome.create();

      //replace the notifDT with the root dt that was returned
      pubDT = actController.getPublicHealthCaseInfo(publicHealthCaseUID,
          nbsSecurityObj);

      if (pubDT == null) {
        logger.error(
            "NotificationProxyEJB, getpublichealthcasevo() returns is null. ");
        throw new NEDSSSystemException("publicHealthCaseVO is null ");
      }

      // check if the user has access to see the data
      if (!nbsSecurityObj.checkDataAccess(pubDT, NBSBOLookup.INVESTIGATION,
                                          NBSOperationLookup.VIEW)) {
        logger.info("no investigation permission for getPHCVO");
        throw new NEDSSSystemException("NotificationProxyEJB: getPHCVO: User does not have permission for investigation");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      logger.fatal(e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage(), e);
    }
    return pubDT;
  }

  /**
   * @roseuid 3D6E32180217
   * @J2EE_METHOD  --  validateNNDIndividualRequiredFields
   * @param Long
   * @param NBSSecurityObj
   * @return Collection
   */
  public Collection<Object>  validateNNDIndividualRequiredFields(Long
      publicHealthCaseUid,
      NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
    try {
		PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
		NedssUtils nedssUtils = new NedssUtils();
		ArrayList<Object> nndSummaryCol = new ArrayList<Object> ();

		Collection<Object>  actIDColl = null;
		PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();

		try {
		  ActController actController = null;
		  Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		  ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
		      narrow(object, ActControllerHome.class);
		  actController = acthome.create();

		  publicHealthCaseDT = actController.getPublicHealthCaseInfo(
		      publicHealthCaseUid, nbsSecurityObj);

		  publicHealthCaseVO = actController.getPublicHealthCase(
		      publicHealthCaseUid, nbsSecurityObj);

		  String programAreaCode = publicHealthCaseDT.getProgAreaCd();
		  String jurisdictionCode = publicHealthCaseDT.getJurisdictionCd();
		  String shared = publicHealthCaseDT.getSharedInd();
		  if (publicHealthCaseDT == null) {
		    logger.error(
		        "NotificationProxyEJB, validateNNDIndividualRequiredFields() returns is null. ");
		    throw new NEDSSSystemException("publicHealthCaseDT is null ");
		  }

		  if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
		                                    NBSOperationLookup.CREATE,
		                                    programAreaCode,
		                                    jurisdictionCode, shared)) { //System.out.println("value of first check was false");
		    if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
		                                      NBSOperationLookup.
		                                      CREATENEEDSAPPROVAL,
		                                      programAreaCode,
		                                      jurisdictionCode, shared))

		    {
		      logger.info(
		          "no review permissions for validateNNDIndividualRequiredFields");
		      throw new NEDSSSystemException("NO CREATE or CREATE NEEDS APPROVAL PERMISSIONS for NotificcationProxyEJB - validateNNDIndividualRequiredFields");
		    }

		  }
		}
		catch (Exception e) {
		  e.printStackTrace();
		  logger.fatal("error in NotificationProxyEJB - validateNNDSummaryRequiredFields - " +e.getMessage(), e);
		  throw new NEDSSSystemException(e.getMessage(),e);
		}
//checking to see if the core validation values exist in the DT
//if missing, the VOCAB is inlcuded in the NND Collection

		//MMWR Week and Year are required for nonRVCT Cases.
		if(!publicHealthCaseDT.getCd().equals("10220")) {
		    if ( (publicHealthCaseDT.getMmwrWeek() == null)
		        || (publicHealthCaseDT.getMmwrWeek().trim().equals(""))) {
		      // add missing required field name
		      nndSummaryCol.add("MMWR Week");
		    }
		    if ( (publicHealthCaseDT.getMmwrYear() == null)
		        || (publicHealthCaseDT.getMmwrYear().trim().equals(""))) {
		      nndSummaryCol.add("MMWR Year");
		    }
		}
		/* Comment out 'Number of Cases' - field does not exists in any of INV page specs.
		    if (publicHealthCaseDT.getGroupCaseCnt() == null) {
		      nndSummaryCol.add("Number of Cases");
		    }
		 */
		if ( (publicHealthCaseDT.getCd() == null)
		    || (publicHealthCaseDT.getCd().trim().equals(""))) {
		  nndSummaryCol.add("Condition");
		}
		if ( (publicHealthCaseDT.getCaseClassCd() == null)
		    || (publicHealthCaseDT.getCaseClassCd().trim().equals(""))) {
		  nndSummaryCol.add("Case Status");
		}

//checking for Hepatatis specific missing fields, by calling a stored procedure, and then
// appending appropriate Vocab to the NND Collection<Object>  for the specified values
//System.out.println("Value of publicHealthCaseDT.getCd() = " + publicHealthCaseDT.getCd());
		if (publicHealthCaseDT.getCd().equals("999999") ||
		    publicHealthCaseDT.getCd().equals("10100") ||
		    publicHealthCaseDT.getCd().equals("10101") ||
		    publicHealthCaseDT.getCd().equals("10102") ||
		    publicHealthCaseDT.getCd().equals("10104") ||
		    publicHealthCaseDT.getCd().equals("10105") ||
		    publicHealthCaseDT.getCd().equals("10106") ||
		    publicHealthCaseDT.getCd().equals("10110")) {
		  //System.out.println("We are inside the heptatitis Logic");
		  NotificationProxyDaoImpl notificationProxyDaoImpl = new
		      NotificationProxyDaoImpl();
		  ArrayList<Object> returnedValues = new ArrayList<Object> ();

		  if (publicHealthCaseDT.getCd().equals("999999"))
		  {
		    String diagnosis = ("Diagnosis");
		    ArrayList<Object> validatedFields = new ArrayList<Object> ();
		    validatedFields.add(diagnosis);
		    nndSummaryCol.addAll(validatedFields);
		  }
		  else{
		    returnedValues = (ArrayList<Object> ) notificationProxyDaoImpl.
		      validateHepatitisRequiredFields(publicHealthCaseUid);
		  }

		  if (returnedValues != null) {
		    // add names of any hepatitis missing required fields
		    nndSummaryCol.addAll(returnedValues);
		  }
		}
//checking for Bmird specific missing fields, by calling a stored procedure, and then
// appending appropriate Vocab to the NND Collection<Object>  for the specified values

		if (publicHealthCaseDT.getCd().equals("10650") ||
		    publicHealthCaseDT.getCd().equals("11710") ||
		    publicHealthCaseDT.getCd().equals("11715") ||
		    publicHealthCaseDT.getCd().equals("10590") ||
		    publicHealthCaseDT.getCd().equals("10150") ||
		    publicHealthCaseDT.getCd().equals("11716") ||
		    publicHealthCaseDT.getCd().equals("11717")) {

		  //check if there is an ABCs State Case ID value
		 /* actIDColl = publicHealthCaseVO.getTheActIdDTCollection();
		  ActIdDT actIdDT = new ActIdDT();
		  boolean isABCsCase = false;
		  for (Iterator<Object> iter = actIDColl.iterator(); iter.hasNext(); ) {
		    actIdDT = (ActIdDT) iter.next();

		    if (actIdDT.getRootExtensionTxt() != null && !actIdDT.getRootExtensionTxt().trim().equals("")){
		      //&& actIdDT.getAssigningAuthorityCd().equals(NEDSSConstants.ABCS)) {
		      isABCsCase = true;
		    }
		  }

		  if (!isABCsCase) {
		    nndSummaryCol.add("State-Assigned ABCs Case ID");
		  }
		  //check if there is a value assigned to first positive culture date
		  if (publicHealthCaseDT.getDiagnosisTime() == null) {
		    nndSummaryCol.add("Date first positive culture obtained");
		  }
*/
		  //System.out.println("We are inside the BMIRD Logic");
		  /*
		         NotificationProxyDaoImpl notificationProxyDaoImpl = new NotificationProxyDaoImpl();
		         ArrayList<Object> returnedValues = (ArrayList<Object> )notificationProxyDaoImpl.validateBMIRDRequiredFields(publicHealthCaseUid);
		         if(returnedValues != null)
		    //add names of any BMIRD required fields
		    nndSummaryCol.addAll(returnedValues);*/
		  String abcIndicator = propertyUtil.getABCSTATE();
		  if (abcIndicator != null &&
		      abcIndicator.equalsIgnoreCase("ABCSTATE_ABCQUESTION")) {

		    NotificationProxyDaoImpl notificationProxyDaoImpl = new
		        NotificationProxyDaoImpl();
		    ArrayList<Object> returnedValues = (ArrayList<Object> ) notificationProxyDaoImpl.
		        validateBMIRDRequiredFields(publicHealthCaseUid);
		     nndSummaryCol.addAll(returnedValues);
		  }
		}
		if (nndSummaryCol.size() == 0) {
		  return null; //if nndSummaryCol is null, then all required fields have values
		}
		else {
		  return nndSummaryCol;
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}

  } //end of validateNNDSummaryRequiredFields

  /**
   * @roseuid 3D6E321802C2
   * @J2EE_METHOD  --  validateNNDSummaryRequiredFields
   * @param Long
   * @param NBSSecurityObj
   * @return Collection
   */
  public Collection<Object>  validateNNDSummaryRequiredFields(Long publicHealthCaseUid,
      NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
    try {
		//System.out.println("Inside validateNNDSummaryRequiredFields method on PRoxy");
		PublicHealthCaseDT publicHealthCaseDT = new PublicHealthCaseDT();
		NedssUtils nedssUtils = new NedssUtils();
		ArrayList<Object> nndSummaryCol = null;

		try {
		  ActController actController = null;
		  Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		  ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
		      narrow(object, ActControllerHome.class);
		  actController = acthome.create();

		  publicHealthCaseDT = actController.getPublicHealthCaseInfo(
		      publicHealthCaseUid, nbsSecurityObj);
		  String programAreaCode = publicHealthCaseDT.getProgAreaCd();
		  //String jurisdictionCode = publicHealthCaseDT.getJurisdictionCd();
		  //String shared = publicHealthCaseDT.getSharedInd();
		  if (publicHealthCaseDT == null) {
		    logger.error(
		        "NotificationProxyEJB, validateNNDSummaryRequiredFields() returns is null. ");
		    throw new NEDSSSystemException("publicHealthCaseDT is null ");
		  }
		  if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
		                                    NBSOperationLookup.
		                                    CREATESUMMARYNOTIFICATION,
		                                    programAreaCode,
		                                    ProgramAreaJurisdictionUtil.
		                                    ANY_JURISDICTION)) { //, shared))
		    logger.info(
		        "no review permissions for validateNNDSummaryRequiredFields");
		    throw new NEDSSSystemException("NO CREATE or CREATE NEEDS APPROVAL PERMISSIONS for NotificcationProxyEJB - validateNNDSummaryRequiredFields");
		  }
		}
		catch (Exception e) {
		  e.printStackTrace();
		  logger.fatal("error in NotificationProxyEJB - validateNNDSummaryRequiredFields - " +e.getMessage(),e);
		  throw new NEDSSSystemException(e.getMessage(),e);
		}
//checking to see if the core validation values exist in the DT
//if missing, the VOCAB is inlcuded in the NND Collection

		if (publicHealthCaseDT.getMmwrWeek() == null ||
		    publicHealthCaseDT.getMmwrWeek().trim().equals("")) {
		  nndSummaryCol = new ArrayList<Object> ();
		  nndSummaryCol.add("MMWR Week");
		}
		if (publicHealthCaseDT.getMmwrYear() == null ||
		    publicHealthCaseDT.getMmwrYear().trim().equals("")) {
		  if (nndSummaryCol == null) {
		    nndSummaryCol = new ArrayList<Object> ();
		  }
		  nndSummaryCol.add("MMWR Year");
		}
		if (publicHealthCaseDT.getGroupCaseCnt() == null) {
		  if (nndSummaryCol == null) {
		    nndSummaryCol = new ArrayList<Object> ();
		  }
		  /* Comment out 'Number of Cases' - field does not exists in any of INV page specs.
		        nndSummaryCol.add("Number of Cases");
		   */
		}
		if (publicHealthCaseDT.getCd() == null ||
		    publicHealthCaseDT.getCd().trim().equals("")) {
		  if (nndSummaryCol == null) {
		    nndSummaryCol = new ArrayList<Object> ();
		  }
		  nndSummaryCol.add("Condition");
		}
		if (publicHealthCaseDT.getCaseClassCd() == null ||
		    publicHealthCaseDT.getCaseClassCd().trim().equals("")) {
		  if (nndSummaryCol == null) {
		    nndSummaryCol = new ArrayList<Object> ();
		  }
		  nndSummaryCol.add("Case Status");
		}
		//System.out.println("Returning nndSummaryCOL: " );
		return nndSummaryCol; //if nndSummaryCol is null, then all required fields have values
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  } //end of validateNNDSummaryRequiredFields

  /**
   * This method returns a collection of all Updated Notifications that need
   * to be audited
   *
   * @param nbsSecurityObj
   * @throws javax.ejb.EJBException
   */
  public Collection<Object>  getUpdatedNotificationsForAudit (NBSSecurityObj nbsSecurityObj)
	  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException{

		UpdatedNotificationDAOImpl impl = new UpdatedNotificationDAOImpl();

		ArrayList<Object> list = new ArrayList<Object> ();

		NBSAuthHelper helper = new NBSAuthHelper();

		try {
			list.addAll(impl.selectAllActiveUpdatedNotifications(nbsSecurityObj));

			UpdatedNotificationSummaryVO vo = new UpdatedNotificationSummaryVO();

			for(int i = 0; i < list.size(); i++){
				vo = (UpdatedNotificationSummaryVO) list.get(i);

				vo.setAddUserName(helper.getUserName(vo.getAddUserId()));
				if(vo.isCaseStatusChg())
				{
					vo.setCaseClassCdTxt("*" + StringUtils.replaceNull(vo.getCaseClassCdTxt()));
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
		}

	  	return list;
	}

  /**
   * This method removes Updated Notifications from the Updated Notifications
   * Queue
   *
   * @param Collection<Object>  updated Notifications
   * @param nbsSecurityObj
   * @throws javax.ejb.EJBException
   */
  public void removeUpdatedNotifications(Collection<Object> updatedNotifications,
	  NBSSecurityObj nbsSecurityObj)
	  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException{

	  	if((updatedNotifications == null) ||
	  		(updatedNotifications.size() == 0)){
	  			return;
	  	}

	  	Iterator<Object> i = updatedNotifications.iterator();
	  	UpdatedNotificationDAOImpl impl = new UpdatedNotificationDAOImpl();

		try {
			while(i.hasNext()){
				UpdatedNotificationSummaryVO vo = (UpdatedNotificationSummaryVO)i.next();

				String programeAreaCode = vo.getProgAreaCd();
				String jurisdictionCode = vo.getJurisdictionCd();
				String shared = vo.getSharedInd();

				if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
												  NBSOperationLookup.REVIEW,
												  programeAreaCode,
												  jurisdictionCode, shared))
				{
					logger.info("no review permissions for approveNotification");
					throw new NEDSSSystemException("NO AUDIT PERMISSIONS for setNotificationProxy");
				}

				UpdatedNotificationDT dt = new UpdatedNotificationDT();
				dt.setNotificationUid(vo.getNotificationUid());
				dt.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
				dt.setLastChgTime(new Timestamp(System.currentTimeMillis()));
				dt.setVersionCtrlNbr(vo.getVersionCtrlNbr());
				dt.setCaseClassCd(vo.getCaseClassCd());

				impl.updateUpdatedNotification(dt);

			}
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

  public Collection<Object>  getRejectedNotifications(NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			NEDSSSystemException {
		try {
			NotificationProxyDaoImpl notificationProxyDaoImpl = new NotificationProxyDaoImpl();
			ArrayList<Object>  rejectednotificationVOCollection  = (ArrayList<Object> ) notificationProxyDaoImpl
					.getRejectedNotificationsQueue(nbsSecurityObj);

			NBSAuthHelper helper = new NBSAuthHelper();

			for (int i = 0; i < rejectednotificationVOCollection.size(); i++) {

				RejectedNotificationSummaryVO rejectedNotificationSummaryVO = (RejectedNotificationSummaryVO) rejectednotificationVOCollection.get(i);

				if(rejectedNotificationSummaryVO.getRejectedByUserId()!=null)
					rejectedNotificationSummaryVO.setRejectedByUserName(helper.
							getUserName(rejectedNotificationSummaryVO.getRejectedByUserId()));
				else
					logger.error("Notification rejected by User only should appear " +
							"in this queue:InvestigationUid-"+rejectedNotificationSummaryVO.getPublicHealthCaseUid());


			}

			return rejectednotificationVOCollection;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

  /**
   * Returns the list of Fields that are required (and not filled) to Create Notification from PAM Cases
   * @param publicHealthCaseUid
   * @param reqFields
   * @param nbsSecurityObj
   * @return
   * @throws javax.ejb.EJBException
   */
  public  Map<Object, Object>  validatePAMNotficationRequiredFields(Long publicHealthCaseUid,  Map<Object, Object>  reqFields,String formCd, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
	  Map<Object, Object>  missingFields = new TreeMap<Object, Object>();
	  try {
		  missingFields = validatePAMNotficationRequiredFieldsGivenPageProxy(null, publicHealthCaseUid,  reqFields, formCd, nbsSecurityObj);
	  }catch (Exception e) {
	        e.printStackTrace();
	        throw new NEDSSSystemException("error in NotificationProxyEJB - validatePAMNotficationRequiredFields - " +e);
	      }
	  return missingFields;
	  }
  /**
   * Returns the list of Fields that are required (and not filled) to Create Notification from PAM Cases
   * @param publicHealthCaseUid
   * @param reqFields
   * @param nbsSecurityObj
   * @return
   * @throws javax.ejb.EJBException
   */
  public  Map<Object, Object>  validatePAMNotficationRequiredFieldsGivenPageProxy(Object pageObj, Long publicHealthCaseUid,  Map<Object, Object>  reqFields,String formCd, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException {
	    
	     Map<Object, Object>  missingFields = new TreeMap<Object, Object>();
	  
	    try {
	    	PamVO pamVO=null;
	    	Collection<Object> participationDTCollection  = null;
	    	PublicHealthCaseDT publicHealthCaseDT = null;
	    	Collection<Object> personVOCollection = null;
	    	Map<Object, Object>  answerMap = null;
	        NedssUtils nedssUtils = new NedssUtils();
	        Collection<Object>  actIdColl = null;
	        if(formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)||formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR)){
	        	 PamProxyVO proxyVO = new PamProxyVO(); 
	        	if(pageObj == null || pageObj instanceof  PublicHealthCaseVO){
		        Object object = nedssUtils.lookupBean(JNDINames.PAM_PROXY_EJB);
		        PamProxyHome home = (PamProxyHome) PortableRemoteObject.narrow(object, PamProxyHome.class);
		        PamProxy pamproxy = home.create();	 
		        proxyVO =  pamproxy.getPamProxy(publicHealthCaseUid, nbsSecurityObj);
	        	}else
	        		proxyVO = (PamProxyVO) pageObj;
			    pamVO = proxyVO.getPamVO();
			    answerMap = pamVO.getPamAnswerDTMap();
			    if(pageObj == null || pageObj instanceof  PublicHealthCaseVO)
			    participationDTCollection  = proxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
			    else
			    	participationDTCollection = proxyVO.getTheParticipationDTCollection();
				personVOCollection  = proxyVO.getThePersonVOCollection();
				publicHealthCaseDT = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
				actIdColl = proxyVO.getPublicHealthCaseVO().getTheActIdDTCollection();	
	        }else{
	        	 PageProxyVO pageProxyVO  = null;
	        	 if(pageObj == null  || pageObj instanceof  PublicHealthCaseVO){
	        	Object object = nedssUtils.lookupBean(JNDINames.PAGE_PROXY_EJB);
		        PageProxyHome home = (PageProxyHome) PortableRemoteObject.narrow(object, PageProxyHome.class);
		        PageProxy pageproxy = home.create();	 
		         pageProxyVO =  pageproxy.getPageProxyVO(NEDSSConstants.CASE, publicHealthCaseUid, nbsSecurityObj);
	        	 }else{
	        		 pageProxyVO = (PageProxyVO) pageObj;
	        	 }
		        PageActProxyVO pageActProxyVO=(PageActProxyVO)pageProxyVO;
		        pamVO=pageActProxyVO.getPageVO();
		        answerMap = ((PageActProxyVO)pageProxyVO).getPageVO().getPamAnswerDTMap();
		        if(pageObj == null || pageObj instanceof  PublicHealthCaseVO)
		        participationDTCollection  = pageActProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
		        else
		        	participationDTCollection = pageActProxyVO.getTheParticipationDTCollection();
				personVOCollection  = pageActProxyVO.getThePersonVOCollection();
				publicHealthCaseDT = pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
				actIdColl = pageActProxyVO.getPublicHealthCaseVO().getTheActIdDTCollection();	
	        }
	        
		     
	        PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT, participationDTCollection,personVOCollection );
	        PersonDT personDT = personVO.getThePersonDT();
	        
	                
	        String programAreaCode = publicHealthCaseDT.getProgAreaCd();
	        String jurisdictionCode = publicHealthCaseDT.getJurisdictionCd();
	        String shared = publicHealthCaseDT.getSharedInd();
	        if (publicHealthCaseDT == null) {
	          logger.error("NotificationProxyEJB, validatePAMNotficationRequiredFieldsGivenPageProxy() returns is null. ");
	          throw new NEDSSSystemException("publicHealthCaseDT is null ");
	        }

	        if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
	                                          NBSOperationLookup.CREATE,
	                                          programAreaCode,
	                                          jurisdictionCode, shared)) {
	          if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
	                                            NBSOperationLookup.
	                                            CREATENEEDSAPPROVAL,
	                                            programAreaCode,
	                                            jurisdictionCode, shared))

	          {
	            logger.info(
	                "no review permissions for validateRvctNotficationRequiredFields");
	            throw new NEDSSSystemException("NO CREATE or CREATE NEEDS APPROVAL PERMISSIONS for NotificcationProxyEJB - validatePAMNotficationRequiredFieldsGivenPageProxy");
	          }

	        }
	        //Iterate through the reqFields  Map<Object, Object>  and find missing NND Req questions answered by looking @ datalocation
	       Iterator<Object>  kIter = reqFields.keySet().iterator();
	        while(kIter.hasNext()) {
	        	 Long key = (Long) kIter.next();
	        	NbsQuestionMetadata metaData = (NbsQuestionMetadata) reqFields.get(key);
	        	String dLocation = metaData.getDataLocation() == null ? "" : metaData.getDataLocation() ; 
	        	String label = metaData.getQuestionLabel() == null ? "" : metaData.getQuestionLabel();
	        	Long nbsQueUid = metaData.getNbsQuestionUid();
	        	//String dType = metaData.getDataType() == null ? "" : metaData.getDataType();
	        	if(!dLocation.equals("")) {	        		
	        		if(dLocation.startsWith("NBS_Answer.")) {
	        			if(answerMap.get(key) == null) {
	        				missingFields.put(metaData.getQuestionIdentifier(), metaData.getQuestionLabel());
	        			}	        			
	        		} else if(dLocation.toLowerCase().startsWith("public_health_case.")) {
	        			String attrToChk = dLocation.substring(dLocation.indexOf(".")+1);
	        			
	        			String getterNm = createGetterMethod(attrToChk);
	        			 Map<Object, Object>  methodMap = getMethods(publicHealthCaseDT.getClass());			
	        			Method method = (Method)methodMap.get(getterNm.toLowerCase());
	        			Object obj = method.invoke(publicHealthCaseDT,  (Object[])null);	        			
	        			checkObject(obj, missingFields, metaData);
	        		} else if(dLocation.toLowerCase().startsWith("person.")) {
	        			String attrToChk = dLocation.substring(dLocation.indexOf(".")+1);
	        			String getterNm = createGetterMethod(attrToChk);
	        			 Map<Object, Object>  methodMap = getMethods(personDT.getClass());			
	        			Method method = (Method)methodMap.get(getterNm.toLowerCase());
	        			Object obj = method.invoke(personDT,  (Object[])null);
	        			checkObject(obj, missingFields, metaData);	
	        		}else if(dLocation.toLowerCase().startsWith("postal_locator.")) {
	        			String attrToChk = dLocation.substring(dLocation.indexOf(".")+1);
	        			String getterNm = createGetterMethod(attrToChk);
	        			PostalLocatorDT postalLocator = new PostalLocatorDT();
	        			Map<Object, Object>  methodMap = getMethods(postalLocator.getClass());			
	        			Method method = (Method)methodMap.get(getterNm.toLowerCase());
	        			if(personVO!=null && personVO.getTheEntityLocatorParticipationDTCollection()!=null && personVO.getTheEntityLocatorParticipationDTCollection().size()>0){
	        				Iterator<Object> eIterator = personVO.getTheEntityLocatorParticipationDTCollection().iterator();
	        				while (eIterator.hasNext()){
	        					EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)eIterator.next();
	        					if(elp.getThePostalLocatorDT()!=null){
        							//check if this is the correct entity locator to check
        							if (elp.getUseCd() != null && 
        								metaData.getDataUseCd() != null &&
        								metaData.getDataUseCd().equalsIgnoreCase(elp.getUseCd())) {
        									postalLocator = elp.getThePostalLocatorDT();
        									Object obj = method.invoke(postalLocator,  (Object[])null);
        									checkObject(obj, missingFields, metaData);
        							}
	        					}else if(elp.getClassCd()!=null && elp.getClassCd().equals("PST") && elp.getThePostalLocatorDT()==null){
	        						checkObject(null, missingFields, metaData);
	        					}
	        				}
	        			}else
    						checkObject(null, missingFields, metaData);
	        		} else if(dLocation.toLowerCase().startsWith("person_race.")) {
	        			String attrToChk = dLocation.substring(dLocation.indexOf(".")+1);
	        			String getterNm = createGetterMethod(attrToChk);
	        			PersonRaceDT personRace = new PersonRaceDT();
	        			Map<Object, Object>  methodMap = getMethods(personRace.getClass());			
	        			Method method = (Method)methodMap.get(getterNm.toLowerCase());
	        			if(personVO!=null && personVO.getThePersonRaceDTCollection()!=null && personVO.getThePersonRaceDTCollection().size()>0){
	        				Iterator<Object> rIterator = personVO.getThePersonRaceDTCollection().iterator();
	        				while (rIterator.hasNext()){
	        						personRace = (PersonRaceDT)rIterator.next();
	        						Object obj = method.invoke(personRace,  (Object[])null);
	    		        			checkObject(obj, missingFields, metaData);
	        				}
	        			}else
    						checkObject(null, missingFields, metaData);
	        		}else if(dLocation.toLowerCase().startsWith("act_id.")) {
	        			String attrToChk = dLocation.substring(dLocation.indexOf(".")+1);
	        			String getterNm = createGetterMethod(attrToChk);
		      			if(actIdColl != null && actIdColl.size() > 0) {
		      				Iterator iter = actIdColl.iterator();
		      				while(iter.hasNext()) {
	    					  ActIdDT adt = (ActIdDT) iter.next();
	    					  String typeCd = adt.getTypeCd() == null ? "" : adt.getTypeCd();
	    					  String value = adt.getRootExtensionTxt() == null ? "" : adt.getRootExtensionTxt();
	    					  if(typeCd.equalsIgnoreCase(NEDSSConstants.ACT_ID_STATE_TYPE_CD) && value.equals("") && (label.toLowerCase().indexOf("state") != -1)) {	    						  
			        			 Map<Object, Object>  methodMap = getMethods(adt.getClass());			
			        			Method method = (Method)methodMap.get(getterNm.toLowerCase());
			        			Object obj = method.invoke(adt,  (Object[])null);			        			
			        			checkObject(obj, missingFields, metaData);	    						  
	    					  } else if(typeCd.equalsIgnoreCase(NEDSSConstants.ACT_ID_STATE_TYPE_CD) && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT) && (label.toLowerCase().indexOf("state") != -1)) {	    						  
				        			 Map<Object, Object>  methodMap = getMethods(adt.getClass());			
					        			Method method = (Method)methodMap.get(getterNm.toLowerCase());
					        			Object obj = method.invoke(adt,  (Object[])null);			        			
					        			checkObject(obj, missingFields, metaData);	    						  
			    			  } 
	    					  else if(typeCd.equalsIgnoreCase("CITY") && value.equals("") && (label.toLowerCase().indexOf("city") != -1)) {	    						  
				        			Map<Object, Object>  methodMap = getMethods(adt.getClass());			
				        			Method method = (Method)methodMap.get(getterNm.toLowerCase());
				        			Object obj = method.invoke(adt,  (Object[])null);
				        			checkObject(obj, missingFields, metaData);
	    					  }
		      				}
		    			}else if(formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT) && (label.toLowerCase().indexOf("state") != -1)) {	    						  
		    					 missingFields.put(metaData.getQuestionIdentifier(), metaData.getQuestionLabel());						  
		    			}
	        		}else if(dLocation.toLowerCase().startsWith("nbs_case_answer.") && !(formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)
	        					||formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR))){
	        			 if(answerMap == null || answerMap.size() == 0 || (answerMap.get(nbsQueUid)==null && answerMap.get(metaData.getQuestionIdentifier())==null))
	        				 missingFields.put(metaData.getQuestionIdentifier(), metaData.getQuestionLabel());
	        		}else if(dLocation.toLowerCase().startsWith("nbs_case_answer.") && (formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT))){
        			 if(answerMap == null || answerMap.size() == 0 ||  (answerMap.get(nbsQueUid)==null && answerMap.get(metaData.getQuestionIdentifier())==null))
        				 missingFields.put(metaData.getQuestionIdentifier(), metaData.getQuestionLabel());
        			 }
        			
	        	}
	        }   
	      } catch (Exception e) {
	        e.printStackTrace();
	        logger.fatal("error in NotificationProxyEJB - validatePAMNotficationRequiredFieldsGivenPageProxy - " +e.getMessage(), e);
	        throw new NEDSSSystemException(e.getMessage(),e);
	      }
	        if (missingFields.size() == 0)
	            return null;
	        else
	        	return missingFields;	      
  	}
  
  private String createGetterMethod(String attrToChk) {
      
      try {
		StringTokenizer tokenizer = new StringTokenizer(attrToChk,"_");
		  String methodName = "";
		  while (tokenizer.hasMoreTokens()){
		      String token = tokenizer.nextToken();
		      methodName = methodName + Character.toUpperCase(token.charAt(0)) +
		                   token.substring(1).toLowerCase();
		   
		  }
		  return "get" + methodName;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }


  	private void checkObject(Object obj,  Map<Object, Object>  missingFields, NbsQuestionMetadata metaData) {
  		
		try {
			String value = obj == null ? "" : obj.toString();
			if(value == null || (value != null && value.trim().length() == 0)) {
				missingFields.put(metaData.getQuestionIdentifier(), metaData.getQuestionLabel());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
  		
  	}
  
	private static PersonVO getPersonVO(String type_cd, Collection<Object> participationDTCollection,  Collection<Object> personVOCollection) {
		try {
			ParticipationDT participationDT = null;
			PersonVO personVO = null;
			if (participationDTCollection  != null) {
				Iterator<Object> anIterator1 = null;
				Iterator<Object> anIterator2 = null;
				for (anIterator1 = participationDTCollection.iterator(); anIterator1.hasNext();) {
					participationDT = (ParticipationDT) anIterator1.next();
					if (participationDT.getTypeCd() != null && (participationDT.getTypeCd()).compareTo(type_cd) == 0) {
						for (anIterator2 = personVOCollection.iterator(); anIterator2.hasNext();) {
							personVO = (PersonVO) anIterator2.next();
							if (personVO.getThePersonDT().getPersonUid().longValue() == participationDT
									.getSubjectEntityUid().longValue()) {
								return personVO;
							} else
								continue;
						}
					} else
						continue;
				}
			}
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}  

	  public static  Map<Object, Object>  getMethods(Class beanClass) {
		     try {
				logger.info("Starts getMethods()...");
				 Method[] gettingMethods = beanClass.getMethods();
				  Map<Object, Object>  resultMap = new HashMap<Object, Object>();
				 for(int i = 0; i < gettingMethods.length; i++) {
				      Method method = (Method)gettingMethods[i];
				      String methodName = method.getName().toLowerCase();
				      resultMap.put(methodName, method);
				 }
				 logger.info("Done getMethods() - return: " + resultMap.toString());
				 return resultMap;
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.fatal(e.getMessage(), e);
				throw new NEDSSSystemException(e.getMessage(), e);
			}
		  }	
}


