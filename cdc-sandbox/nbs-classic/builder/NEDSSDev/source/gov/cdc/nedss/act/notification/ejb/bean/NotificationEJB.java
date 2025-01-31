//
// -- Java Code Generation Process --
package gov.cdc.nedss.act.notification.ejb.bean;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.notification.vo.*;
import gov.cdc.nedss.act.notification.ejb.dao.*;
import gov.cdc.nedss.act.notification.dt.*;
import gov.cdc.nedss.systemservice.util.*;

import java.rmi.RemoteException;
import java.sql.Connection;

import javax.ejb.*;
import javax.sql.DataSource;

/**
* Name:		NotificationEJB
* Description:	The bean is an entity bean for notification. It implements EntityBean,
* provides the business logic for getting and setting notification methods,
* and implements EntityBean methods for the Bean and setting the session context.
* Copyright:    Copyright (c) 2002
* Company:      Computer Sciences Corporation
* @author:      NEDSS Development Team
* @version      NBS1.1
*/

public class NotificationEJB
    implements javax.ejb.EntityBean
{

    /*
       Attributes declaration
    */
	private static final long serialVersionUID = 1L;
    private EntityContext cntx;
    public NotificationVO notificationVO;
    private NotificationVO oldNotificationVO;
    private long notificationUID;
    private NotificationRootDAOImpl notificationRootDAO = null;

    //For logging
    static final LogUtils logger = new LogUtils(NotificationEJB.class.getName());

    public NotificationEJB()
    {
    }

    /**
     * A setting method to set NotificationVO. This method sets Notification's attributes
     * to the values passed in as attributes of the value object.
     * @return NotificationVO
     * @throws NEDSSConcurrentDataException
     */
    public void setNotificationVO(NotificationVO notificationVO)
                           throws NEDSSConcurrentDataException
    {
      try
      {
          if (this.notificationVO.getTheNotificationDT().getVersionCtrlNbr().intValue() !=
                  notificationVO.getTheNotificationDT().getVersionCtrlNbr().intValue() )
          {
              logger.error("Throwing NEDSSConcurrentDataException");
              throw new NEDSSConcurrentDataException
                  ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
          }
          notificationVO.getTheNotificationDT().setVersionCtrlNbr(new Integer(notificationVO.getTheNotificationDT().getVersionCtrlNbr().intValue()+1));
          oldNotificationVO = this.notificationVO;
          this.notificationVO = notificationVO;
      }
      catch(Exception e)
      {
          logger.debug(e.toString()+" : setnotificationVO dataconcurrency catch: " + e.getClass());
          logger.debug("Exception string is: " + e.toString());
          if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
          {
              logger.fatal("NotificationEJB.setNotificationVO: NEDSSConcurrentDataException: " + e.getMessage(), e);
              throw new NEDSSConcurrentDataException(e.getMessage(), e);

          }
          else
          {
              logger.fatal("NotificationEJB.setNotificationVO: EJBException: " + e.getMessage(), e);
              throw new EJBException(e.getMessage(), e);
          }
      }
    }

     /**
     * A getting method to get NotificationDT
     * @return NotificationDT
     */
    public NotificationDT getNotificationInfo()
    {
        try {
			return notificationVO.getTheNotificationDT();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("NotificationEJB.getNotificationInfo: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
    }

    /**
     * implements EntityBean required method
     */
    public void ejbActivate()
    {
    }

    /**
     * implements EntityBean required method
     */
    public void ejbPassivate()
    {
    }

    /**
     * implements EntityBean required method
     * A container invokes this method to load the EJB object that is currently
     * associated with the instance. It is invoked when a client invokes a load operation
     * on the enterprise Bean's home or remote interface. It transitions the instance from
     * the ready state to the pool of available instances. It is called in the transaction
     * context of the load operation.
     */
    public void ejbLoad()
    {

        try
        {

            if (notificationRootDAO == null)
            {
                notificationRootDAO = (NotificationRootDAOImpl)NEDSSDAOFactory.getDAO(
                                              JNDINames.NOTIFICATION_ROOT_DAO_CLASS);

                if (notificationRootDAO == null)
                {
                    throw new EJBException("NEDSSDAOFactory.getDAO(JNDINames.NOTIFICATION_ROOT_DAO_CLASS) returned NULL!");
                }
            }

            this.notificationVO = (NotificationVO)notificationRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
            this.notificationVO.setItDirty(false);
            this.notificationVO.setItNew(false);
        }
        catch (NEDSSSystemException npdfex)
        {
            logger.fatal("NotificationEJB.ejbLoad: " + npdfex.getMessage(), npdfex);
            throw new javax.ejb.EJBException(npdfex.getMessage(), npdfex);
        }

        catch (Exception ex)
        {
            logger.fatal("NotificationEJB.ejbLoad: " + ex.getMessage(), ex);
            throw new javax.ejb.EJBException(ex.getMessage(), ex);
        }
    }

    /**
     * implements EntityBean required method
     * A container invokes this method to find the EJB object that is currently
     * associated with the instance. It is invoked when a client invokes a find operation
     * on the enterprise Bean's home or remote interface. It transitions the instance from
     * the ready state to the pool of available instances. It is called in the transaction
     * context of the find operation.
     * @param Long primaryKey
     * @return Long primaryKey
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSSystemException
     */

    public Long ejbFindByPrimaryKey(Long pk)
                             throws RemoteException, FinderException,
                                    EJBException, NEDSSSystemException
    {

        Long findPK = null;

        try
        {

            if (pk != null)
            {

                if (notificationRootDAO == null)
                {
                    notificationRootDAO = (NotificationRootDAOImpl)NEDSSDAOFactory.getDAO(
                                                  JNDINames.NOTIFICATION_ROOT_DAO_CLASS);
                }

                findPK = notificationRootDAO.findByPrimaryKey(pk.longValue());
                //see notes in clearcase by JLD
                //this.notificationVO = (NotificationVO)notificationRootDAO.loadObject(pk.longValue());
            }
        }
        catch (Exception ex)
        {
            logger.fatal("NotificationEJB.ejbFindByPrimaryKey: " + ex.getMessage(), ex);
            throw new javax.ejb.EJBException(ex.getMessage(), ex);
        }

        return findPK;
    }

    /**
     * implements EntityBean required method
     * A container invokes this method to store the EJB object that is currently
     * associated with the instance. It is invoked when a client invokes a store operation
     * on the enterprise Bean's home or remote interface. It transitions the instance from
     * the ready state to the pool of available instances. It is called in the transaction
     * context of the store operation.
     */
    public void ejbStore()
    {

        try
        {

            if (notificationRootDAO == null)
            {
                notificationRootDAO = (NotificationRootDAOImpl)NEDSSDAOFactory.getDAO(
                                              JNDINames.NOTIFICATION_ROOT_DAO_CLASS);
            }

            if (this.notificationVO != null &&
                this.notificationVO.isItDirty())
            {
                try
		{
		    notificationRootDAO.store(this.notificationVO);

                    this.notificationVO.setItDirty(false);
                    this.notificationVO.setItNew(false);
                    //Waiting on the "go ahead" to uncomment
                    insertHistory();
		}
                catch(NEDSSConcurrentDataException ndcex)
                {
                    logger.debug("Got into concurrent exception in NotificationEJB");
                    cntx.setRollbackOnly();
                     throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException in NotificationEJB");
                }

            }

        }
        catch (NEDSSSystemException npdupex)
        {
            logger.fatal("NotificationEJB.setNotificationVO: NEDSSSystemException: " + npdupex.getMessage(),npdupex);
            throw new NEDSSSystemException(npdupex.getMessage(), npdupex);
        }
        catch (Exception ex)
        {
            logger.fatal("NotificationEJB.setNotificationVO: Exception: " + ex.getMessage(), ex);
            throw new EJBException(ex.getMessage(), ex);
        }
    }

    /**
     * A container invokes this method before it removes the EJB object that is currently
     * associated with the instance. It is invoked when a client invokes a remove operation
     * on the enterprise Bean's home or remote interface. It transitions the instance from
     * the ready state to the pool of available instances. It is called in the transaction
     * context of the remove operation.
     * @throws javax.ejb.RemoveException
     */
    public void ejbRemove()
                   throws javax.ejb.RemoveException
    {
      //Waiting on the "Go Ahead" to uncomment;
      /*insertHistory();*/
    }

    /**
     * Unset the associated entity context. The container calls this method before removing
     * the instance. This is the last method that the container invokes on the instance.
     * The Java garbage collector will  invoke the finalize() method on the instance. It
     * is called in an unspecified transaction context.
     */
    public void unsetEntityContext()
    {
        try {
			cntx = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("NotificationEJB.unsetEntityContext: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
    }

    /**
     * Matching method of the create(...) method of the bean's home interface. The container
     * invokes an ejbCreate method to create an entity object. It executes in the transaction
     * context determined by the transaction attribute of the matching create(...) method.
     */
    public Long ejbCreate(NotificationVO notificationVO)
                   throws RemoteException, CreateException,
                          DuplicateKeyException, EJBException,
                          NEDSSSystemException
    {
        this.notificationVO = notificationVO;

        try
        {

            if (notificationRootDAO == null)
            {
                notificationRootDAO = (NotificationRootDAOImpl)NEDSSDAOFactory.getDAO(
                                              JNDINames.NOTIFICATION_ROOT_DAO_CLASS);
            }
            this.notificationVO.getTheNotificationDT().setVersionCtrlNbr(new Integer(1));
            if(this.notificationVO.getTheNotificationDT().getSharedInd() == null)
            this.notificationVO.getTheNotificationDT().setSharedInd("T");
            notificationUID = notificationRootDAO.create(this.notificationVO);
            this.notificationVO.getTheNotificationDT().setNotificationUid(new Long(notificationUID));
        }
        catch (NEDSSSystemException ndsex)
        {
            logger.fatal("NotificationEJB.ejbCreate: NEDSSSystemException: " + ndsex.getMessage(),ndsex);
            throw new NEDSSSystemException(ndsex.getMessage(), ndsex);
        }
        catch (Exception ex)
        {
            logger.fatal("NotificationEJB.ejbCreate: " + ex.getMessage(), ex);
            throw new javax.ejb.EJBException(ex.getMessage(), ex);
        }

        return (new Long(notificationUID));
    }

    /**
     * @J2EE_METHOD  --  ejbPostCreate
     * Matching method of ejbCreate. The container invokes the matching ejbPostCreate method
     * on an instance after it invokes the ejbCreate method with the same arguments. It
     * executes in the same transaction context as that of the matching ejbCreate method.
     * @throws javax.ejb.CreateException
     */
    public void ejbPostCreate(NotificationVO notificationVO)
                       throws javax.ejb.CreateException
    {
    }

    /**
     * @J2EE_METHOD  --  getNotificationVO
     *
     * A getting method to get NotificationVO
     * @return NotificationVO
     */
    public NotificationVO getNotificationVO()
    {

        try {
			return notificationVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("NotificationEJB.getNotificationVO: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
    }

    /**
     * @J2EE_METHOD  --  setEntityContext
     * Set the associated entity context. The container invokes this method on an instance
     * after the instance has been created. This method is called in an unspecified transaction
     * context.
     * @param EntityContext entitycontext
     * @throws EJBException
     * @throws RemoteException
     */
    public void setEntityContext(EntityContext entitycontext)
                          throws EJBException, RemoteException
    {
        try {
			this.cntx = entitycontext;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("NotificationEJB.setEntityContext: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
    }

    /**
     * @J2EE_METHOD  --  getEntityContext
     * get the associated entity context. This method is called in an unspecified transaction
     * context.
     * @return EntityContext
     */
    public EntityContext getEntityContext()
    {

        try {
			return cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("NotificationEJB.getEntityContext: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
    }

    private void insertHistory()throws NEDSSSystemException {
      try {
		//If oldNotificationVO is not null insert into history
		  if(oldNotificationVO != null) {
		    logger.debug("NotificationEJB in ejbStore(), NotificationUID in notificationVO : " + oldNotificationVO.getTheNotificationDT().getNotificationUid().longValue());
		    long oldNotificationUID = oldNotificationVO.getTheNotificationDT().getNotificationUid().longValue();
		    short versionCtrlNbr = oldNotificationVO.getTheNotificationDT().getVersionCtrlNbr().shortValue();
		    NotificationHistoryManager notificationHistoryManager = new NotificationHistoryManager(oldNotificationUID, versionCtrlNbr);
		    notificationHistoryManager.store(this.oldNotificationVO);
		    this.oldNotificationVO = null;
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("NotificationEJB.insertHistory: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);
	}
    }//end of insertHistory
}
