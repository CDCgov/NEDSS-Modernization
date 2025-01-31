/**
* Name:		NotificationRootDAOImpl.java
* DescriptioDescription:
*	        As its name, this class serves as a root for other notification DAOs.
*               It will call NotificationDAOImpl, ActivityIdDAOImpl, ActivityLocatorParticipationDAOImpl,
*               and also ActRelationshipDAOImpl and ParticipationDAOImpl for the relationships between
*               the units of notifications.
* Copyright:	Copyright (c) 2002
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.1
*/

package gov.cdc.nedss.act.notification.ejb.dao;


import gov.cdc.nedss.act.actid.dao.ActivityIdDAOImpl;
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.ActivityLocatorParticipationDAOImpl;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.dao.UpdatedNotificationDAOImpl;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;

public class NotificationRootDAOImpl extends BMPBase
{
    //For logging
    static final LogUtils logger = new LogUtils(NotificationRootDAOImpl.class.getName());

    private NotificationVO pvo = null;
    private long notificationUID;
    private  NotificationDAOImpl notificationDAO = null;
    private UpdatedNotificationDAOImpl updatedNotificationDAO = null;
    private  ActivityIdDAOImpl activityIdDAO = null;
    private  ActivityLocatorParticipationDAOImpl activityLocatorParticipationDAO = null;
    private  ActRelationshipDAOImpl notificationActRelationshipDAOImpl = null;
    private  ParticipationDAOImpl notificationParticipationDAOImpl = null;

    public NotificationRootDAOImpl() throws  NEDSSSystemException
    {
    }

      /**
     * @methodname create
     * This method inserts a notificationVO object into database. It gets notificationDT, entityIdDT,
     * ActivityLocatorParticipationDT from notificationVO and inserts these records one by one.
     * @param Object a notificationVO object
     * @return  long a positive value if action is successful
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        this.pvo = (NotificationVO)obj;
	
	        /**
	        *  Inserts NotificationDT object
	        */
	
	        if (this.pvo != null)
	        notificationUID = insertNotification(this.pvo);
	        logger.debug("Notification UID = " + notificationUID);
	        (this.pvo.getTheNotificationDT()).setNotificationUid(new Long(notificationUID));
	
			/**
	        * Inserts EntityIdDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheActIdDTCollection() != null)
	        insertActivityIDs(this.pvo);
	
	        /**
	        * Inserts ActivityLocatorParticipationDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheActivityLocatorParticipationDTCollection() != null)
	        {
	        	insertActivityLocatorParticipations(this.pvo);
			}
	
	        this.pvo.setItNew(false);
	        this.pvo.setItDirty(false);
	        return ((((NotificationVO)obj).getTheNotificationDT().getNotificationUid()).longValue());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

      /**
     * @methodname store
     * This method update a notificationVO object into database. It gets notificationDT, entityIdDT,
     * ActivityLocatorParticipationDT from notificationVO and update these records one by one.
     * @param Object a notificationVO object
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     * @throws NEDSSConcurrentDataException
     */
    public void store(Object obj) throws NEDSSConcurrentDataException,NEDSSSystemException
    {
    	try{
	        this.pvo = (NotificationVO)obj;
	
	        /**
	        *  Updates NotificationDT object
	        */
	        if(this.pvo.getTheNotificationDT() != null && this.pvo.getTheNotificationDT().isItNew())
	        {
	            insertNotification(this.pvo);
	            this.pvo.getTheNotificationDT().setItNew(false);
	            this.pvo.getTheNotificationDT().setItDirty(false);
	        }
	        else if(this.pvo.getTheNotificationDT() != null && this.pvo.getTheNotificationDT().isItDirty())
	        {
	            updateNotification(this.pvo);
	            this.pvo.getTheNotificationDT().setItDirty(false);
	            this.pvo.getTheNotificationDT().setItNew(false);
	        }
	
	        /**
	         * Updates activity ids collection
	         */
	        if(this.pvo.getTheActIdDTCollection() != null)
	        {
	            updateActivityIDs(this.pvo);
	        }
	
	        /**
	         * Updates ActivityLocatorParticipationDT collection
	         */
	        if(this.pvo.getTheActivityLocatorParticipationDTCollection() != null)
	        {
	            updateActivityLocatorParticipations(this.pvo);
	        }
    	}catch(NEDSSConcurrentDataException ex){
    		logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
    		throw new NEDSSConcurrentDataException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * @methodname remove
     * This method remove a notificationVO object into database by its notification uid.
     * It removes NotificationEthnicGroupDT collection NotificationRaceDT and NotificationDT
     * ActivityLocatorParticipationDT from notificationVO and update these records one by one.
     * @param long notificationUID
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    public void remove(long notificationUID) throws NEDSSSystemException
    {
    	try{
	        /**
	        * Removes NotificationEthnicGroupDT collection
	        */
	
	        removeActivityIDs(notificationUID);
	
	        /**
	        * Removes NotificationRaceDT Collection
	        */
	
	        removeNotification(notificationUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = notificationUID "+notificationUID+" : "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }


    /**
     * @methodname loadObject
     * This method load a notificationVO object from database by notification uid
     * @param Object a notificationVO object
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    public Object loadObject(long notificationUID)
    	throws
    NEDSSSystemException
    {
    	try{
	        this.pvo = new NotificationVO();
			logger.debug( "New NotificationVO instance created" );
	        /**
	        *  Selects NotificationDT object
	        */
	
	        NotificationDT pDT = selectNotification(notificationUID);
	        this.pvo.setTheNotificationDT(pDT);
	
	        /**
	        * Selects ActivityIdDT collection
	        */
	
	        Collection<Object> idColl = selectActivityIDs(notificationUID);
	        this.pvo.setTheActIdDTCollection(idColl);
	
	        /**
	        * Selects ActivityLocatorParticipationDT collection
	        */
	
	        Collection<Object> activityLocatorParticipationColl = selectActivityLocatorParticipations(notificationUID);
	        this.pvo.setTheActivityLocatorParticipationDTCollection(activityLocatorParticipationColl);
	
	        // Selects ActRelationshiopDTcollection
	        Collection<Object> actColl = selectActRelationshipDTCollection(notificationUID);
	        this.pvo.setTheActRelationshipDTCollection(actColl);
	
	        // SelectsParticipationDTCollection
	        Collection<Object> parColl = selectParticipationDTCollection(notificationUID);
	        this.pvo.setTheParticipationDTCollection(parColl);
	
	        this.pvo.setItNew(false);
	        this.pvo.setItDirty(false);
	        return this.pvo;
    	}catch(Exception ex){
    		logger.fatal("Exception  notificationUID "+notificationUID+" : "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

     /**
     * @methodname findByPrimaryKey
     * This method search a notification object  by its notification uid.
     * @param long notificationUID
     * @return Long notification UID
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    public Long findByPrimaryKey(long notificationUID) throws
    NEDSSSystemException
	{
    	try{
			/**
			 * Finds notification object
			 */
			Long notificationPK = findNotification(notificationUID);
	
			logger.debug("Done find by primarykey!");
			return notificationPK;
    	}catch(Exception ex){
    		logger.fatal("Exception  notificationUID "+notificationUID+" : "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
	}


    /**
     * @methodname insertNotification
     * This method inserts a notificationVO object into database.
     * @param Object a notificationVO object
     * @return  long a positive value if action is successful
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private long insertNotification(NotificationVO pvo) throws  NEDSSSystemException
    {
        try
        {
            if(notificationDAO == null)
            {
                notificationDAO = (NotificationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NOTIFICATION_DAO_CLASS);
            }
            logger.debug("Notification DT = " + pvo.getTheNotificationDT());
            notificationUID = notificationDAO.create(pvo.getTheNotificationDT());
            logger.debug("Notification root uid = " + notificationUID);
            pvo.getTheNotificationDT().setNotificationUid(new Long(notificationUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertNotification() "+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertNotification() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return notificationUID;
    }

  /**
     * @methodname insertActivityIDs
     * This is a private method. This method inserts a ActivityId collection into database.
     * @param Object a notificationVO object
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private void insertActivityIDs( NotificationVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            notificationUID = activityIdDAO.create((pvo.getTheNotificationDT().getNotificationUid()).longValue(), pvo.getTheActIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertActivityIDs() "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertActivityIDs() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * @methodname insertActivityLocatorParticipations
     * This is a private method. This method inserts a activityLocatorParticipation collection into database.
     * @param Object a notificationVO object
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private void insertActivityLocatorParticipations(NotificationVO notVO) throws NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
              activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
          	notificationUID = activityLocatorParticipationDAO.create((notVO.getTheNotificationDT().getNotificationUid()).longValue(), notVO.getTheActivityLocatorParticipationDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertActivityLocatorParticipations() "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertActivityLocatorParticipations() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * @methodname selectNotification
     * This is a private method. This method load notificationDT from database.
     * @param long notificationUID
     * @return NotificationDT
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */

    private NotificationDT selectNotification(long notificationUID) throws NEDSSSystemException
    {
        try
        {
            if(notificationDAO == null)
            {
                notificationDAO = (NotificationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NOTIFICATION_DAO_CLASS);
            }
            return ((NotificationDT)notificationDAO.loadObject(notificationUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectNotification() "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectNotification() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * @methodname selectActivityIDs
     * This is a private method. This method load a collection of activityIdDTs from database.
     * @param long notificationUID
     * @return Collection<Object>  a collection of activityIdDTs
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private Collection<Object> selectActivityIDs(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
               activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            return (activityIdDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectActivityIDs() "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectActivityIDs() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

     /**
     * @methodname selectActivityLocatorParticipations
     * This is a private method. This method load a collection of activityLocatorParticipations from database.
     * @param long notificationUID
     * @return Collection<Object>  a collection of activityLocatorParticipations
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private Collection<Object> selectActivityLocatorParticipations(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            return (activityLocatorParticipationDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectActivityLocatorParticipations() "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectActivityLocatorParticipations() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * @methodname removeNotification
     * This is a private method. This method removes notification record from database.
     * @param long notificationUID
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private void removeNotification(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(notificationDAO == null)
            {
                notificationDAO = (NotificationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.INTERVENTION_DAO_CLASS);
            }
            notificationDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }
    /**
     * @methodname removeActivityIDs
     * This is a private method. This method removes activityID record from database.
     * @param long notificationUID
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private void removeActivityIDs(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            activityIdDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            logger.fatal("NEDSSDAOSysException  = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("NEDSSSystemException  = "+ndapex.getMessage(), ndapex);
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }

    /**
     * @methodname updateNotification
     * This is a private method. This method update notification record in database.
     * @param NotificationVO pvo
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     * @throws NEDSSConcurrentDataException
     */
    private void updateNotification(NotificationVO pvo) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
        try
        {
            if(notificationDAO == null)
            {
                notificationDAO = (NotificationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NOTIFICATION_DAO_CLASS);
            }
            notificationDAO.store(pvo.getTheNotificationDT());

			// Initialize the DAO if it is not already initialized
            if(updatedNotificationDAO == null){
            	updatedNotificationDAO = (UpdatedNotificationDAOImpl)
            		NEDSSDAOFactory.getDAO(JNDINames.UPDATED_NOTIFICATION_DAO_CLASS);
            }
            
            // If the UpdatedNotificationDT is not null then persist
             
            if(pvo.getTheUpdatedNotificationDT() != null){
            	updatedNotificationDAO.insertUpdatedNotification(
            		pvo.getTheUpdatedNotificationDT());
            }
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateNotification() "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSConcurrentDataException ncdaex)
        {
            logger.fatal("Fails updateNotification() due to concurrent access! "+ncdaex.getMessage(), ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateNotification() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }

    }

    /**
     * @methodname updateActivityIDs
     * This is a private method. This method update ActivityIDs record in database.
     * @param NotificationVO pvo
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private void updateActivityIDs(NotificationVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            activityIdDAO.store(pvo.getTheActIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateActivityIDs() "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityIDs() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * @methodname updateActivityLocatorParticipations
     * This is a private method. This method update ActivityLocatorParticipations record in database.
     * @param NotificationVO pvo
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private void updateActivityLocatorParticipations(NotificationVO notVO)
              throws NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            activityLocatorParticipationDAO.store(notVO.getTheActivityLocatorParticipationDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateActivityLocatorParticipations() "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityLocatorParticipations() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
     * @methodname findNotification
     * This is a private method. This method search a notification record by notificationUID in database.
     * @param long notificationUID
     * @return Long a notificationUID
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private Long findNotification(long notificationUID) throws NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            if(notificationDAO == null)
            {
                notificationDAO = (NotificationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NOTIFICATION_DAO_CLASS);
            }
            findPK = notificationDAO.findByPrimaryKey(notificationUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findNotification() notificationUID "+notificationUID+" "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findNotification() notificationUID "+notificationUID+" "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }

    /**
     * @methodname findActivityIDs
     * This is a private method. This method search a activityID record by notificationUID in database.
     * @param long notificationUID
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
    private void findActivityIDs(long notificationUID) throws NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            Long findPK = activityIdDAO.findByPrimaryKey(notificationUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findActivityIDs() notificationUID "+notificationUID+" "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findActivityIDs() notificationUID "+notificationUID+" "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

     /**
     * @methodname selectActRelationshipDTCollection
     * This is a private method. This method gets collection of ActRelationship from ActRelationshipDAOImpl
     * @param long notificationUID
     * @return Collection<Object>  a collection of actRelationships
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
 private Collection<Object> selectActRelationshipDTCollection(long aUID)
      throws
        NEDSSSystemException
    {
        try
        {
            if(notificationActRelationshipDAOImpl == null)
            {
               notificationActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            logger.debug("aUID in selectActRelationshipDTCollection  = " + aUID);
            return (notificationActRelationshipDAOImpl.loadSource(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectActRelationshipDTCollection() aUID "+aUID+" "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectActRelationshipDTCollection() aUID "+aUID+" "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


    /**
     * @methodname selectParticipationDTCollection
     * This is a private method. This method gets collection of Participation  from ParticipationDAOImpl
     * @param long notificationUID
     * @return Collection<Object>  a collection of Participations
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
 private Collection<Object> selectParticipationDTCollection(long aUID)
      throws
        NEDSSSystemException
    {
        try  {
            if(notificationParticipationDAOImpl == null)
            {
                notificationParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (notificationParticipationDAOImpl.loadAct(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectParticipationDTCollection() aUID "+aUID+" "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectParticipationDTCollection() aUID "+aUID+" "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

   /**
     * @methodname setParticipation
     * This is a private method. This method sets a collection of participationDT
     * @param Collection<Object>  participationDTs
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
 private void setParticipation(Collection<Object> partDTs)
      throws
        NEDSSSystemException
    {
        Collection<Object> newPartDTs = new ArrayList<Object> ();
        try
        {
            if(notificationParticipationDAOImpl == null)
            {
                notificationParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            Iterator<Object> iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                notificationParticipationDAOImpl.store(partDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setParticipation() "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setParticipation() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

   /**
     * @methodname setActRelationship
     * This is a private method. This method sets a collection of ActRelationships
     * @param Collection<Object>  ActRelationshipDTs
     * @throws NEDSSSystemException This is an exception thrown in the event of application errors.
     */
 private void setActRelationship(Collection<Object> actRelDTs)
      throws
        NEDSSSystemException
    {
        Collection<Object> newActRelDTs = new ArrayList<Object> ();
        try
        {
            if(notificationActRelationshipDAOImpl == null)
            {
                notificationActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            Iterator<Object> iter = actRelDTs.iterator();
            while(iter.hasNext())
            {
                ActRelationshipDT actRelDT = (ActRelationshipDT)iter.next();
                logger.debug("Calling store on ActRelationshipDAOImpl");
                notificationActRelationshipDAOImpl.store(actRelDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setActRelationship() "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setActRelationship() "+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

}//end of NotificationRootDAOImpl class
