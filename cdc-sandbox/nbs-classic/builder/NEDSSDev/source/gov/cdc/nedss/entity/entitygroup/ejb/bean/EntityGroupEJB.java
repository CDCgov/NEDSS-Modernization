package gov.cdc.nedss.entity.entitygroup.ejb.bean;

// Import Statements
import java.rmi.RemoteException;
import java.sql.Connection;

import javax.ejb.*;
import javax.sql.DataSource;



// gov.cdc.nedss.* imports
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.entitygroup.vo.*;
import gov.cdc.nedss.entity.entitygroup.ejb.dao.*;
import gov.cdc.nedss.systemservice.util.*;
/**
* Name:		EntityGroupEJB.java
* Description:	EntityGroupEJB is a Entity bean. It implements all the
*               EntityGroup remote interface methods, and methods corresponding
*               to EntityGroupHome interface(according to EJB specs). Additionally
*               it also implements standard EJB callback methods.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @version	1.0
*/
public class EntityGroupEJB implements javax.ejb.EntityBean
{
   /*
       Attributes declaration
    */

    /**
     * Description: For logging
     */
     static final LogUtils logger = new LogUtils(EntityGroupEJB.class.getName());

    /**
     * Description: EJB Context of EntityGroupEJB
     */
    private EntityContext eJBContext;

    /**
     * Description: The Value Object that EntityGroupEJB uses to compare if changes have been made
     * to the EntityGroupVO
     */
    private EntityGroupVO oldEntityGroupVO;
    /**
     * Description: Value Object that EntityGroupEJB uses
     */
    private EntityGroupVO theEntityGroupVO;

    /**
     * Description: EntityGroup UID
     */
    private long entityGroupUid;

    /**
     * Description: EntityGroup Root DAO that EntityGroup EJB uses
     */
    private EntityGroupRootDAOImpl grpRootDAOImpl = null;

    /**
     * Description: constructor for EntityGroupEJB.
     * @roseuid 3BD4A60C0262
     * @J2EE_METHOD  --  EntityGroupEJB
     */
    public EntityGroupEJB    ()
    {

    }

    /**
     * Description: A container invokes this method when the instance is taken out of the pool of available
     * instances to become associated with a specific EJB object. This method transitions
     * the instance to the ready state. This method executes in an unspecified transaction
     * context.
     * @roseuid 3BCF43110124
     * @J2EE_METHOD  --  ejbActivate
     */
    public void ejbActivate    ()
    {

    }

    /**
     * Description: A container invokes this method on an instance before the instance becomes disassociated
     * with a specific EJB object. After this method completes, the container will place
     * the instance into the pool of available instances. This method executes in an unspecified
     * transaction context.
     * @roseuid 3BCF4311012E
     * @J2EE_METHOD  --  ejbPassivate
     */
    public void ejbPassivate    ()
    {
        this.grpRootDAOImpl = null;
    }

    /**
     * Description: A container invokes this method to instruct the instance to synchronize its state
     * by loading it from the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     * @throws EJBException
     * @roseuid 3BCF43110142
     * @J2EE_METHOD  --  ejbLoad
     */
    public void ejbLoad() throws EJBException
    {
      logger.debug("EntityGroupEJB load is called");
      try
      {
        if(grpRootDAOImpl==null)
          grpRootDAOImpl = (EntityGroupRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITYGROUP_ROOT_DAO_CLASS);
        this.theEntityGroupVO =  (EntityGroupVO)grpRootDAOImpl.loadObject(((Long)eJBContext.getPrimaryKey()).longValue());
        this.theEntityGroupVO.setItDirty(false);
        this.theEntityGroupVO.setItNew(false);
      }
      catch(NEDSSSystemException ndsex)
      {
          logger.fatal("Fails to retrieve entity group", ndsex.getMessage(),ndsex);
          throw new EJBException(ndsex.getMessage(),ndsex);
      }

    }

    /**
     * Description: A container invokes this method to instruct the instance to synchronize its state
     * by storing it to the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     * @throws EJBException
     * @roseuid 3BCF43110156
     * @J2EE_METHOD  --  ejbStore
     */
    public void ejbStore() throws EJBException

    {
      logger.debug("EntityGroupEJB store is called");
      try
      {
        if(grpRootDAOImpl==null){
          logger.debug("EntityGroupEJB.ejbStore() - grpRootDAOImpl==null");
          grpRootDAOImpl = (EntityGroupRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITYGROUP_ROOT_DAO_CLASS);
        }
        logger.debug("EntityGroupEJB.ejbStore() grpRootDAOImpl="+ grpRootDAOImpl.toString());
        logger.debug("EntityGroupEJB.ejbStore() theEntityGroupVO="+ theEntityGroupVO.toString());
        if(this.theEntityGroupVO != null && this.theEntityGroupVO.isItDirty())
        {
			try
			{
				grpRootDAOImpl.store(this.theEntityGroupVO);

				this.theEntityGroupVO.setItDirty(false);
				this.theEntityGroupVO.setItNew(false);
                                //Waiting on the "go ahead" to uncomment
                                //insertHistory();Not required as of 6/27/02
				logger.debug("EntityGroupEJB.ejbStore().theEntityGroupVO = " + theEntityGroupVO.toString());
			}
			catch(NEDSSConcurrentDataException ndcex)
			{
				logger.debug("Got into concurrent exception in EntityGroupEJB");
				eJBContext.setRollbackOnly();
				throw new EJBException("NEDSSConcurrentDataException in EntityGroupEJB" + ndcex.getMessage(),ndcex);
			}
        }
      }
      catch(NEDSSSystemException ndsex)
      {
          logger.fatal("Fails to store entity group"+ ndsex.getMessage(),ndsex);
          throw new EJBException(ndsex.getMessage(),ndsex);
      }

    }

    /**
     * Description: A container invokes this method before it removes the EJB object that is currently
     * associated with the instance. It is invoked when a client invokes a remove operation
     * on the enterprise Bean's home or remote interface. It transitions the instance from
     * the ready state to the pool of available instances. It is called in the transaction
     * context of the remove operation.
     * @throws RemoveException
     * @roseuid 3BCF4311016A
     * @J2EE_METHOD  --  ejbRemove
     */
     public void ejbRemove ()
      throws
        RemoveException
      {
        try {
            if(grpRootDAOImpl == null) {
              grpRootDAOImpl = (EntityGroupRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITYGROUP_ROOT_DAO_CLASS);
            }
            //insertHistroy();
            grpRootDAOImpl.remove(((Long)eJBContext.getPrimaryKey()).longValue());

        } catch(NEDSSSystemException ndsex) {
            logger.fatal("Fails to remove entity group" + ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString(),ndsex);

        }

    }

    /**
     * Description: Unset the associated entity context. The container calls this method before removing
     * the instance. This is the last method that the container invokes on the instance.
     * The Java garbage collector will  invoke the finalize() method on the instance. It
     * is called in an unspecified transaction context.
     * @roseuid 3BCF43110192
     * @J2EE_METHOD  --  unsetEntityContext
     */
    public void unsetEntityContext    ()
    {
        eJBContext = null;
    }

    /**
     * Description: Set the associated entity context. The container invokes this method on an instance
     * after the instance has been created. This method is called in an unspecified transaction
     * context.
     * @param entitycontext : The EntityContext for the EntityGroupEJB
     * @throws EJBException
     * @throws RemoteException
     * @roseuid 3BCF4B5102E3
     * @J2EE_METHOD  --  setEntityContext
     */
    public void setEntityContext    (EntityContext entitycontext) throws EJBException,RemoteException
    {
        eJBContext = entitycontext;
    }

    /**
     * Description: This method returns the EntityContext used by the EntityGroupEJB
     * @return EntityContext
     */
    public EntityContext getEntityContext ()
    {
        return eJBContext;
    }

    /**
     * Description: Invoked by the container on the instance when the container selects the instance to
     * execute a matching client-invoked find(...) method. It executes in the transaction
     * context determined by the transaction attribute of the matching find(...) method.
     * @param pk  Long
     * @return Long
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSSystemException
     * @roseuid 3BD02F4E009A
     * @J2EE_METHOD  --  ejbFindByPrimaryKey
     */
    public Long ejbFindByPrimaryKey(Long pk)
                  throws RemoteException, FinderException, EJBException, NEDSSSystemException
    {
        logger.debug("EntityGroup EjbFindByPrimaryKey is called - pk = " + pk);
        Long findPK = null;
        try
        {
            if(pk != null)
            {
                logger.debug("not null - pk = " + pk);
                if(grpRootDAOImpl == null)
                {
                    logger.debug("JNDINames.ENTITYGROUP_ROOT_DAO_CLASS = " + JNDINames.ENTITYGROUP_ROOT_DAO_CLASS);
                    grpRootDAOImpl = (EntityGroupRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITYGROUP_ROOT_DAO_CLASS);
                }
                findPK = grpRootDAOImpl.findByPrimaryKey(pk.longValue());
                logger.debug("return findpk: " + findPK);
                //this.theEntityGroupVO = (EntityGroupVO)grpRootDAOImpl.loadObject(pk.longValue());
            }
        }
        catch (NEDSSSystemException napex)
        {
            logger.fatal("Exception in find by primary key" + napex.getMessage(), napex);
            throw new EJBException(napex.toString(),napex);
        }
        catch (EJBException ejbex)
        {
            logger.fatal("Exception in find by primary key" + ejbex.getMessage(), ejbex);
            throw new EJBException(ejbex.toString(),ejbex);
        }
        return findPK;
    }

    /**
     * Description: Matching method of the create(...) method of the bean's home interface. The container
     * invokes an ejbCreate method to create an entity object. It executes in the transaction
     * context determined by the transaction attribute of the matching create(...) method.
     * @param entityGroupVO  EntityGroupVo
     * @return Long
     * @throws RemoteException
     * @throws CreateException
     * @throws DuplicateKeyException
     * @throws EJBException
     * @throws NEDSSSystemException
     * @roseuid 3BD02F4E016C
     * @J2EE_METHOD  --  ejbCreate
     */
    public Long ejbCreate (EntityGroupVO entityGroupVO) throws RemoteException,
                                                          CreateException, DuplicateKeyException,
                                                          EJBException, NEDSSSystemException
    {
        Long entityGroupUID = null;

        logger.debug("EntityGroupEJB create is called");
        this.theEntityGroupVO = entityGroupVO;

        try
        {
          if(grpRootDAOImpl==null)
            grpRootDAOImpl = (EntityGroupRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITYGROUP_ROOT_DAO_CLASS);
            this.theEntityGroupVO.getTheEntityGroupDT().setVersionCtrlNbr(new Integer(1));
            entityGroupUID = new Long(grpRootDAOImpl.create(this.theEntityGroupVO));
        }
        catch(NEDSSSystemException ndsex)
        {
            logger.fatal("Exception in creating an entity group" + ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.toString(),ndsex);
        }

        return entityGroupUID;
    }

    /**
     * Description: Matching method of ejbCreate. The container invokes the matching ejbPostCreate method
     * on an instance after it invokes the ejbCreate method with the same arguments. It
     * executes in the same transaction context as that of the matching ejbCreate method.
     * @param entityGroupVO  EntityGroupVO
     * @throws CreateException
     * @roseuid 3BD02F4E0176
     * @J2EE_METHOD  --  ejbPostCreate
     */
    public void ejbPostCreate(EntityGroupVO entityGroupVO) throws CreateException
    {

    }

    /**
     * Description: This is a business method in the EntityGroupEJB. This method gets the
     * EntityGroupVO and return it to the EntityGroup Remote Object.
     * @return EntityGroupVO
     * @roseuid 3BD02F4E01E4
     * @J2EE_METHOD  --  getEntityGroupVO
     */
    public EntityGroupVO getEntityGroupVO()
    {
        return theEntityGroupVO;
    }

    /**
     * Description: This is a business method in the EntityGroupEJB. This method sets the
     * EntityGroupVO and return it to the EntityGroup Remote Object.
     * @param entityGroupVO  EntityGroupVO
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BD02F4E020C
     */
    public void setEntityGroupVO(EntityGroupVO entityGroupVO) throws NEDSSConcurrentDataException
    {
        try
        {
           if (this.theEntityGroupVO.getTheEntityGroupDT().getVersionCtrlNbr().intValue() !=
                    entityGroupVO.getTheEntityGroupDT().getVersionCtrlNbr().intValue() )
            {
               logger.error("Throwing NEDSSConcurrentDataException");
               throw new NEDSSConcurrentDataException
                    ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
            }
            entityGroupVO.getTheEntityGroupDT().setVersionCtrlNbr(new Integer(entityGroupVO.getTheEntityGroupDT().getVersionCtrlNbr().intValue()+1));
            oldEntityGroupVO = this.theEntityGroupVO;
            this.theEntityGroupVO = entityGroupVO;
        }
        catch(Exception e)
        {
            logger.debug(e.toString()+" : setPersonVO dataconcurrency catch: " + e.getClass());
            logger.debug("Exception string is: " + e.toString(),e);
            if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
            {
               // cntx.setRollbackOnly();
                logger.fatal("Throwing NEDSSConcurrentDataException" + e.getMessage(),e);
                throw new NEDSSConcurrentDataException(  e.getMessage(),e);
            }
            else
            {
                logger.fatal("Throwing generic Exception" + e.getMessage(),e);
                throw new EJBException(e.toString(),e);
            }
        }
    }

    /**
     * Description: Backup management of records achieved in this insertHistory method
     * @throws NEDSSSystemException
     */
    private void insertHistory()throws NEDSSSystemException{
      try {
		//If oldEntityGroupVO is not null insert into history
		  if(oldEntityGroupVO != null) {
		    logger.debug("EntityGroupEJB in ejbStore(), EntityGroupUID in oldEntityGroupVO : " + oldEntityGroupVO.getTheEntityGroupDT().getEntityGroupUid().longValue());
		    long oldEGUID = oldEntityGroupVO.getTheEntityGroupDT().getEntityGroupUid().longValue();
		    short versionCtrlNbr = oldEntityGroupVO.getTheEntityGroupDT().getVersionCtrlNbr().shortValue();
		    EntityGroupHistoryManager entityGroupHistoryManager = new EntityGroupHistoryManager(oldEGUID, versionCtrlNbr);
		    entityGroupHistoryManager.store(this.oldEntityGroupVO);
		    this.oldEntityGroupVO = null;
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(),e);
        throw new EJBException(e.getMessage(),e);
	}
  }//end of insertHistory()
}
