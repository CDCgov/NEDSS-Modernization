package gov.cdc.nedss.act.notification.ejb.dao;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.notification.vo.*;
import gov.cdc.nedss.act.notification.dt.*;
import gov.cdc.nedss.act.notification.ejb.dao.*;
import gov.cdc.nedss.act.actid.dao.*;
import gov.cdc.nedss.locator.dao.*;
import gov.cdc.nedss.association.dao.*;
import gov.cdc.nedss.exception.*;
//import gov.cdc.nedss.locator.dt.*;

/**
* Name:		NotificationHistoryManager.java
* DescriptioDescription:
*	        This class serves as a root for other notification history DAOs.
*               It will call NotificationHistDAOImpl, ActIdHistDAOImpl, ActivityLocatorParticipationHistDAOImpl,
*               and also ActRelationshipHistDAOImpl and ParticipationHistDAOImpl for the relationships between
*               the units of notification history.
* Copyright:	Copyright (c) 2002
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.1
*/

public class NotificationHistoryManager {
  static final LogUtils logger = new LogUtils(NotificationHistoryManager.class.getName());
  private long notificationUid;
  private NotificationHistDAOImpl notificationHistDAO;
  private short versionCtrlNbr = -1;
  private NotificationVO notVoHist;
  private ActIdHistDAOImpl actIdHistDAO;
  private ActivityLocatorParticipationHistDAOImpl activityLocatorParticipationHistDAOImpl;
  private ActRelationshipHistDAOImpl actRelationshipHist;
  private ParticipationHistDAOImpl participationHistDAOImpl;

  /**
   * To be used only when calling the load(...) method
   */
  public NotificationHistoryManager() {
  }//end of construtor

  public NotificationHistoryManager(long notificationUid, short versionCtrlNbr)throws  NEDSSSystemException {
        this.notificationUid = notificationUid;
        notificationHistDAO = new NotificationHistDAOImpl(notificationUid, versionCtrlNbr);
        this.versionCtrlNbr = notificationHistDAO.getVersionCtrlNbr();
        logger.debug("NotificationHistoryManager--versionCtrlNbr: " + versionCtrlNbr);
        //this.actIdHistDAO = new ActIdHistDAOImpl((short)versionCtrlNbr);
        //this.activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl((short)versionCtrlNbr);
        //this.actRelationshipHist = new ActRelationshipHistDAOImpl((short)versionCtrlNbr);
        //this.participationHistDAOImpl = new ParticipationHistDAOImpl(versionCtrlNbr);
  }//end of constructor


     /**
     * @methodname store
     * This function takes a old NotificationVO and stores them into history tables
     * @param Object a notificationVO object
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    public void store(Object obj) throws  NEDSSSystemException
    {

        notVoHist = (NotificationVO)obj;
        if ( notVoHist == null) return;
        /**
         * Insert notification to history
         */

        if(notVoHist.getTheNotificationDT() != null )
        {
            insertNotification(notVoHist.getTheNotificationDT());
            notVoHist.getTheNotificationDT().setItNew(false);
        }//end of if

        /*if(notVoHist.getTheActIdDTCollection() != null)
          insertActIdDTColl(notVoHist.getTheActIdDTCollection());

        if(notVoHist.getTheActivityLocatorParticipationDTCollection() != null) {
          insertActivityLocatorParticipationDTCollection(notVoHist.getTheActivityLocatorParticipationDTCollection());
        }

        if(notVoHist.getTheActRelationshipDTCollection() != null)  {
          insertActRelationshipDTCollection(notVoHist.getTheActRelationshipDTCollection());
        }

        if(notVoHist.getTheParticipationDTCollection() != null) {
          insertParticipationDTCollection(notVoHist.getTheParticipationDTCollection());
        }*/
    }//end of store

    /**
     * @methodname load
     * This function loads notification history data from history tables by notificationUid and versionCtrlNbr
     * @param Long notificationUid
     * @param Integer versionCtrlNbr
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    public NotificationVO load(Long notificationUid, Integer versionCtrlNbr)
	 throws NEDSSSystemException
    {

        logger.info("Starts loadObject() for a notification vo for the history...");

        NotificationVO notvo = new NotificationVO();
        notificationHistDAO = new NotificationHistDAOImpl();
        //actIdHistDAO = new ActIdHistDAOImpl();
        //activityLocatorParticipationHistDAOImpl = new ActivityLocatorParticipationHistDAOImpl();
        //actRelationshipHist = new ActRelationshipHistDAOImpl();
        //participationHistDAOImpl = new ParticipationHistDAOImpl();

        /**
        *  Selects NotificationDT object
        */

        NotificationDT notDT = notificationHistDAO.load(notificationUid, versionCtrlNbr);
        notvo.setTheNotificationDT(notDT);

        /*Collection<Object>  actIdHistColl = actIdHistDAO.load(notificationUid, versionCtrlNbr);
        notvo.setTheActIdDTCollection(actIdHistColl);*/

        //waiting on determination of logic for activityLocatorParticipationHistDAOImpl.load(...):should it return a collection or dt object
        //Collection<Object>  actLocatorParticipationDTColl = activityLocatorParticipationHistDAOImpl.load(actIdUid, versionCtrlNbr);
        //cdvo.setTheActivityLocatorParticipationDTCollection(actLocatorParticipationDTColl);
//waiting on determination of logic for actRelationshipHist.load(...):should it return a collection or dt object
        //Collection<Object>  actRelationshipColl = actRelationshipHist.load(targetActUid, sourceActUid, typeCd, versionCtrlNbr);
        //cdvo.setTheActRelationshipDTCollection(actRelationshipColl);

        //Collection<Object>  participationDTColl = participationHistDAOImpl.load();
        //cdvo.setTheParticipationDTCollection(participationDTColl);

        logger.info("Done loadObject() for a notificationVo for history - return: " + notvo);
        return notvo;
    }//end of load

      /**
     * @methodname insertNotification
     * This is a private method. It insert notification history data to history tables
     * @param NotificationDT
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
   private void insertNotification(NotificationDT dt)
   throws NEDSSSystemException
   {
      if(dt == null)
          throw new NEDSSSystemException("Error: insert null notificationsDt into notificationHist.");
      notificationHistDAO.store(dt);
    }//end of insertNotification()

   /**
     * @methodname insertActIdDTColl
     * This is a private method. It inserts ActIdDT Collection<Object>  history data to history tables
     * @param Collection<Object>  ActIdDT Collection
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
  private void insertActIdDTColl(Collection<Object> coll) throws NEDSSSystemException{
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null actIdDT collection into act id history.");
      actIdHistDAO.store(coll);
    }

     /**
     * @methodname insertActivityLocatorParticipationDTCollection
     * This is a private method. It inserts ActivityLocatorParticipationDT Collection<Object>  history data to history tables
     * @param Collection<Object>  ActivityLocatorParticipationDT Collection
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private void insertActivityLocatorParticipationDTCollection(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ActivityLocatorParticipationDTCollection  into activity locator participation hist.");
      activityLocatorParticipationHistDAOImpl.store(coll);
    }

     /**
     * @methodname insertActRelationshipDTCollection
     * This is a private method. It inserts ActRelationshipDT Collection<Object>  history data to history tables
     * @param Collection<Object>  ActRelationshipDTCollection
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private void insertActRelationshipDTCollection(Collection<Object> coll)throws  NEDSSSystemException{
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ActRelationshipDTCollection  into act relationship hist.");
      actRelationshipHist.store(coll);
    }

    /**
     * @methodname insertParticipationDTCollection
     * This is a private method. It inserts ParticipationDT Collection<Object>  history data to history tables
     * @param Collection<Object>  ParticipationDTCollection
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private void insertParticipationDTCollection(Collection<Object> coll)throws  NEDSSSystemException {
      if(coll == null)
        throw new NEDSSSystemException("Error: insert null ParticipationDTCollection  into act participation hist.");
      participationHistDAOImpl.store(coll);
    }

}//end of class
