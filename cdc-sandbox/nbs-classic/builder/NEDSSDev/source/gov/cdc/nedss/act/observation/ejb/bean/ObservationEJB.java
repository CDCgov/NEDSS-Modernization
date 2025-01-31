/**
 * Title: ObservationEJB  Class
 * Description: Enterprise bean class for Observation entity bean
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author Brent Chen & NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.act.observation.ejb.bean;

import gov.cdc.nedss.act.observation.ejb.dao.ObservationHistoryManager;
import gov.cdc.nedss.act.observation.ejb.dao.ObservationRootDAOImpl;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;

/**
 *
 */
public class ObservationEJB implements javax.ejb.EntityBean
{
	private static final long serialVersionUID = 1L;
    private ObservationVO obVO;
    private ObservationVO oldObVO;
    //private ObservationHistoryManager obsHistManager;
    private long obUID;
    private javax.ejb.EntityContext cntx;
    private ObservationRootDAOImpl observationRootDAO = null;

    static final LogUtils logger = new LogUtils(ObservationEJB.class.getName());


    /**
     * @roseuid 3BD05AE9028E
     * @J2EE_METHOD  --  ObservationEJB
     * Default Constructor
     */
    public ObservationEJB    ()
    {
    }

    /**
     * @roseuid 3BD05AE9032E
     * @J2EE_METHOD  --  ejbCreate
     * Matching method of the create(...) method of the bean's home interface. The container
     * invokes an ejbCreate method to create an entity object. It executes in the transaction
     * context determined by the transaction attribute of the matching create(...) method.
     * @param ObservationVO obVO
     * @return java.lang.Long
     * @throws CreateException, DuplicateKeyException, EJBException, NEDSSSystemException
     */
    public Long ejbCreate    (ObservationVO obVO) throws
            CreateException, DuplicateKeyException, EJBException,
            NEDSSSystemException

    {
        this.obVO = obVO;

        try
        {
            if(observationRootDAO == null)
            {
                observationRootDAO = (ObservationRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_ROOT_DAO_CLASS);
            }
            this.obVO.getTheObservationDT().setVersionCtrlNbr(new Integer(1));
            if(this.obVO.getTheObservationDT().getSharedInd() == null)
            this.obVO.getTheObservationDT().setSharedInd("T");
            obUID = observationRootDAO.create(this.obVO);
            this.obVO.getTheObservationDT().setObservationUid(new Long(obUID));
        }
        catch(NEDSSSystemException ndsex)
        {
        	logger.fatal("NEDSSSystemException: "+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage());
        }
        return (new Long(obUID));
    }

    /**
     * @roseuid 3BD05AE90342
     * @J2EE_METHOD  --  ejbPostCreate
     * Matching method of ejbCreate. The container invokes the matching ejbPostCreate method
     * on an instance after it invokes the ejbCreate method with the same arguments. It
     * executes in the same transaction context as that of the matching ejbCreate method.
     * @param ObservationVO obVO
     * @throws javax.ejb.CreateException, DuplicateKeyException, EJBException, NEDSSSystemException

     */
    public void ejbPostCreate    (ObservationVO obVO) throws
              javax.ejb.CreateException, DuplicateKeyException, EJBException,
              NEDSSSystemException
    {
    }

    /**
     * @roseuid 3BD05AE90356
     * @J2EE_METHOD  --  ejbActivate
     * A container invokes this method when the instance is taken out of the pool of available
     * instances to become associated with a specific EJB object. This method transitions
     * the instance to the ready state. This method executes in an unspecified transaction
     * context.
     * @throws EJBException
     */
    public void ejbActivate    () throws EJBException
    {

    }

    /**
     * @roseuid 3BD05AE90360
     * @J2EE_METHOD  --  ejbPassivate
     * A container invokes this method on an instance before the instance becomes disassociated
     * with a specific EJB object. After this method completes, the container will place
     * the instance into the pool of available instances. This method executes in an unspecified
     * transaction context.
     * @throws EJBException
     */
    public void ejbPassivate    () throws EJBException
    {
        try {
			this.observationRootDAO = null;
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			logger.fatal("ObservationEJB.ejbPassivate: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    /**
     * @roseuid 3BD05AE90374
     * @J2EE_METHOD  --  ejbLoad
     * A container invokes this method to instruct the instance to synchronize its state
     * by loading it from the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     * @throws EJBException
     */
    public void ejbLoad    () throws EJBException
    {
        try
        {
            if(observationRootDAO == null)
            {
                observationRootDAO = (ObservationRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_ROOT_DAO_CLASS);
            }
            this.obVO = (ObservationVO)observationRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
            this.obVO.setItDirty(false);
            this.obVO.setItNew(false);
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("ObservationEJB.ejbLoad: " + nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.getMessage(), nsex);
        }
    }

    /**
     * @roseuid 3BD05AE90388
     * @J2EE_METHOD  --  ejbStore
     * A container invokes this method to instruct the instance to synchronize its state
     * by storing it to the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     * @throws EJBException
     */
    public void ejbStore    () throws EJBException
    {
        try
        {
            if(observationRootDAO == null)
            {
                observationRootDAO = (ObservationRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_ROOT_DAO_CLASS);
            }
            if(this.obVO != null && this.obVO.isItDirty()) {
				try {
					observationRootDAO.store(this.obVO);

					this.obVO.setItDirty(false);
					this.obVO.setItNew(false);
                                        //Waiting on the "go ahead" to uncomment
                                        insertHistory();
				} catch(NEDSSConcurrentDataException ndcex) {
					logger.debug("Got into concurrent exception in observationEJB");
					cntx.setRollbackOnly();
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException in observationEJB"+ndcex.getMessage());
                }
            }

        }
         catch(NEDSSConcurrentDataException ndcex)
         {
        	logger.fatal("ObservationEJB.ejbStore: NEDSSConcurrentDataException: Concurrent access is not allowed: " + ndcex.getMessage(), ndcex);
            throw new NEDSSSystemException(ndcex.getMessage(), ndcex);

         }
        catch(NEDSSDAOSysException nodsysex)
        {
            logger.fatal("ObservationEJB.ejbStore: NEDSSDAOSysException: " + nodsysex.getMessage(), nodsysex);
            throw new EJBException(nodsysex.getMessage(), nodsysex);

        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("ObservationEJB.ejbStore: NEDSSSystemException: " + nsex.getMessage(), nsex);
            throw new EJBException(nsex.getMessage(), nsex);
        }
    }

    /**
     * @roseuid 3BD05AE9039C
     * @J2EE_METHOD  --  ejbRemove
     * A container invokes this method before it removes the EJB object that is currently
     * associated with the instance. It is invoked when a client invokes a remove operation
     * on the enterprise Bean's home or remote interface. It transitions the instance from
     * the ready state to the pool of available instances. It is called in the transaction
     * context of the remove operation.
     * @throws javax.ejb.RemoveException, EJBException
     */
    public void ejbRemove    () throws javax.ejb.RemoveException, EJBException
    {
        try
        {
            if(observationRootDAO == null)
            {
                observationRootDAO = (ObservationRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_ROOT_DAO_CLASS);
            }
            insertHistory();
            observationRootDAO.remove(((Long)cntx.getPrimaryKey()).longValue());

        }
        catch(NEDSSSystemException ndsex)
        {
            logger.fatal("ObservationEJB.ejbRemove: " + ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(), ndsex);
        }
    }

    /**
     * @roseuid 3BD05AE903B0
     * @J2EE_METHOD  --  setEntityContext
     * Set the associated entity context. The container invokes this method on an instance
     * after the instance has been created. This method is called in an unspecified transaction
     * context.
     * @param javax.ejb.EntityContext ctx
     */
    public void setEntityContext    (javax.ejb.EntityContext ctx)
    {
        try {
			this.cntx = ctx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ObservationEJB.ejbCreate: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    /**
     * @roseuid 3BD05AE903C4
     * @J2EE_METHOD  --  unsetEntityContext
     * Unset the associated entity context. The container calls this method before removing
     * the instance. This is the last method that the container invokes on the instance.
     * The Java garbage collector will  invoke the finalize() method on the instance. It
     * is called in an unspecified transaction context.
     */
    public void unsetEntityContext    ()
    {
        try {
			this.cntx = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ObservationEJB.ejbCreate: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
    }

    /**
     * @roseuid 3BD05AEA000E
     * @J2EE_METHOD  --  ejbFindByPrimaryKey
     * Invoked by the container on the instance when the container selects the instance to
     * execute a matching client-invoked find(...) method. It executes in the transaction
     * context determined by the transaction attribute of the matching find(...) method.
     * @param java.lang.Long primaryKey -  long value used to find EJB instance
     * @return java.lang.Long
     * @throws RemoteException, FinderException, EJBException, NEDSSSystemException
     */
    public Long ejbFindByPrimaryKey (Long primaryKey) throws RemoteException, FinderException, EJBException, NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            if(primaryKey != null)
            {
                if(observationRootDAO == null)
                {
                    observationRootDAO = (ObservationRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.OBSERVATION_ROOT_DAO_CLASS);
                }
                findPK = observationRootDAO.findByPrimaryKey(primaryKey.longValue());
            }
        }

        catch (NEDSSDAOSysException nodsex)
        {
            logger.fatal("ObservationEJB.ejbFindByPrimaryKey: NEDSSDAOSysException: " + nodsex.getMessage(), nodsex);
            throw new EJBException(nodsex.getMessage(), nodsex);

        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("ObservationEJB.ejbFindByPrimaryKey: NEDSSSystemException: " + nsex.getMessage(), nsex);
            throw new EJBException(nsex.getMessage(), nsex);

        }
        return findPK;
    }

    /**
     * @roseuid 3BD847FF0239
     * @J2EE_METHOD  --  getObservationVO
     */
    public ObservationVO getObservationVO    ()
    {
         try {
			return obVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ObservationEJB.getObservationVO: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * @roseuid 3BD847FF0276
     * @J2EE_METHOD  --  setObservationVO
     * A public method that takes a new / dirty ObservationVO as parameter and Creates / Updates it.
     * @params ObservationVO - observationVO
     * @throws NEDSSConcurrentDataException
     */
    public void setObservationVO    (ObservationVO observationVO) throws NEDSSConcurrentDataException{
     try
     {
         logger.debug("obVO.getTheObservationDT().getVersionCtrlNbr():"+obVO.getTheObservationDT().getVersionCtrlNbr());
         logger.debug("observationVO.getTheObservationDT().getVersionCtrlNbr():"+observationVO.getTheObservationDT().getVersionCtrlNbr());
         if (this.obVO.getTheObservationDT().getVersionCtrlNbr().intValue() !=
                 observationVO.getTheObservationDT().getVersionCtrlNbr().intValue() )
         {
             logger.error("Throwing NEDSSConcurrentDataException");
             throw new NEDSSConcurrentDataException
                 ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
         }
         observationVO.getTheObservationDT().setVersionCtrlNbr(new Integer(observationVO.getTheObservationDT().getVersionCtrlNbr().intValue()+1));
         logger.debug("observationVO.getTheObservationDT().getVersionCtrlNbr()after increment:"+observationVO.getTheObservationDT().getVersionCtrlNbr());
         oldObVO = this.obVO;
         this.obVO = observationVO;
     }
     catch(Exception e)
     {
         logger.debug(e.toString()+" : setObservationVO dataconcurrency catch: " + e.getClass());
         logger.debug("Exception string is: " + e.toString());
         if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
         {
             logger.fatal("ObservationEJB.setObservationVO: NEDSSConcurrentDataException: " + e.getMessage(), e);
             throw new NEDSSConcurrentDataException(e.getMessage(), e);
         }
         else
         {
             logger.fatal("ObservationEJB.setObservationVO: EJBException: " + e.getMessage(), e);
             throw new EJBException(e.getMessage(), e);
         }
     }
    }

    /**
     * Backup management of records achieved in this insertHistory method
     * @throws NEDSSSystemException
     */
    private void insertHistory() throws NEDSSSystemException{
      try {
		//If oldObVO is not null insert into history
		  if(oldObVO != null) {
		    logger.debug("ObservationEJB in ejbStore(), observationUID in obVO : " + obVO.getTheObservationDT().getObservationUid().longValue());
		    long observationUID = oldObVO.getTheObservationDT().getObservationUid().longValue();
		    short versionCtrlNbr = oldObVO.getTheObservationDT().getVersionCtrlNbr().shortValue();
		    ObservationHistoryManager obsHistoryManager = new ObservationHistoryManager(observationUID, versionCtrlNbr);
		    obsHistoryManager.store(this.oldObVO);
		    this.oldObVO = null;
		  }
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal("ObservationEJB.insertHistory: " + e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);

	}
    }
    

}
