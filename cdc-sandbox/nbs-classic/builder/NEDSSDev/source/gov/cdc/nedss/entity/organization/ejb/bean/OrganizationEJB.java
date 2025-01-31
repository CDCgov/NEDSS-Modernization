//
// -- Java Code Generation Process --
//
/**
* Name:		EJB class for Organization Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2002
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/
package gov.cdc.nedss.entity.organization.ejb.bean;

// Import Statements
import gov.cdc.nedss.entity.organization.ejb.dao.OrganizationHistoryManager;
import gov.cdc.nedss.entity.organization.ejb.dao.OrganizationRootDAOImpl;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;

public class OrganizationEJB implements javax.ejb.EntityBean
{
    //For log4J logging
    static final LogUtils logger = new LogUtils(OrganizationEJB.class.getName());
    /*
       Attributes declaration
    */
   // private javax.ejb.EntityContext EJB_Context;
    private java.sql.Connection EJB_Connection = null;
    private javax.sql.DataSource EJB_Datasource = null;
    private EntityContext cntx;
    private OrganizationVO theOrganizationVO;
    private OrganizationVO oldOrganizationVO;
    private OrganizationRootDAOImpl orgRootDAOImpl = null;

    /**
     * @roseuid 3BCF431003DF
     * @J2EE_METHOD  --  OrganizationEJB
     */
    public OrganizationEJB    ()
    {

    }

    /**
     * @roseuid 3BCF43110124
     * @J2EE_METHOD  --  ejbActivate
     * A container invokes this method when the instance is taken out of the pool of available
     * instances to become associated with a specific EJB object. This method transitions
     * the instance to the ready state. This method executes in an unspecified transaction
     * context.
     */
    public void ejbActivate    ()
    {

    }

    /**
     * @roseuid 3BCF4311012E
     * @J2EE_METHOD  --  ejbPassivate
     * A container invokes this method on an instance before the instance becomes disassociated
     * with a specific EJB object. After this method completes, the container will place
     * the instance into the pool of available instances. This method executes in an unspecified
     * transaction context.
     */
    public void ejbPassivate    ()
    {
        try {
			this.orgRootDAOImpl = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("OrganizationEJB.ejbPassivate: " + e.getMessage(), e);
		      throw new javax.ejb.EJBException(e.getMessage(), e);
		}
    }

    /**
     * @roseuid 3BCF43110142
     * @J2EE_METHOD  --  ejbLoad
     * A container invokes this method to instruct the instance to synchronize its state
     * by loading it from the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     */
    public void ejbLoad() throws EJBException
    {
      logger.debug("OrganizationEJB load is called");
      try
      {
        if(orgRootDAOImpl==null)
          orgRootDAOImpl = (OrganizationRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_ROOT_DAO_CLASS);
        this.theOrganizationVO =  (OrganizationVO)orgRootDAOImpl.loadObject(((Long)cntx.getPrimaryKey()).longValue());
        this.theOrganizationVO.setItDirty(false);
        this.theOrganizationVO.setItNew(false);
      }
      catch(NEDSSSystemException ndsex)
      {
          logger.fatal("OrganizationEJB.ejbLoad: " + ndsex.getMessage(), ndsex);
          throw new NEDSSSystemException(ndsex.getMessage(), ndsex);
      }
    }

    /**
     * @roseuid 3BCF43110156
     * @J2EE_METHOD  --  ejbStore
     * A container invokes this method to instruct the instance to synchronize its state
     * by storing it to the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     */
    public void ejbStore() throws EJBException
    {
      logger.debug("OrganizationEJB store is called");
      try
      {
        if(orgRootDAOImpl==null){
          logger.debug("OrganizationEJB.ejbStore() - orgRootDAOImpl==null");
          orgRootDAOImpl = (OrganizationRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_ROOT_DAO_CLASS);
        }
        logger.debug("OrganizationEJB.ejbStore() orgRootDAOImpl="+ orgRootDAOImpl.toString());
        logger.debug("OrganizationEJB.ejbStore() theOrganizationVO="+ theOrganizationVO.toString());
        if(this.theOrganizationVO != null && this.theOrganizationVO.isItDirty())
        {
        try
        {
            orgRootDAOImpl.store(this.theOrganizationVO);
            this.theOrganizationVO.setItDirty(false);
            this.theOrganizationVO.setItNew(false);
            //Waiting on the "go ahead" to uncomment the following code
            insertHistory();
            logger.debug("OrganizationEJB.ejbStore().theOrganizationVO = " + theOrganizationVO.toString());
          }
           catch(NEDSSConcurrentDataException ndcex)
                {
                    logger.fatal("OrganizationEJB.ejbStore: Concurrent access is not allowed: " + ndcex.getMessage(), ndcex);
                    throw new NEDSSConcurrentDataException(ndcex.getMessage(), ndcex);
                }
        }
      }
      catch(NEDSSSystemException ndsex)
      {
    	  logger.fatal("OrganizationEJB.ejbStore: NEDSSSystemException: " + ndsex.getMessage(), ndsex);
          throw new NEDSSSystemException(ndsex.getMessage(), ndsex);
      }
      catch (Exception ex)
      {
        logger.fatal("OrganizationEJB.ejbStore: Exception: " + ex.getMessage(), ex);
        throw new EJBException(ex.getMessage(), ex);
      }
    }

    /**
     * @roseuid 3BCF4311016A
     * @J2EE_METHOD  --  ejbRemove
     * A container invokes this method before it removes the EJB object that is currently
     * associated with the instance. It is invoked when a client invokes a remove operation
     * on the enterprise Bean's home or remote interface. It transitions the instance from
     * the ready state to the pool of available instances. It is called in the transaction
     * context of the remove operation.
     */
    public void ejbRemove    () throws javax.ejb.RemoveException
    {
            logger.info("Starts ejbRemove()...");
        try
        {

            if(orgRootDAOImpl == null)
            {
                orgRootDAOImpl = (OrganizationRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_DAO_CLASS);
            }
            insertHistory();
            orgRootDAOImpl.remove(((Long)cntx.getPrimaryKey()).longValue());
            //insertHistory();
        }
       
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("OrganizationEJB.ejbRemove: Cannot remove a organization record, " + ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.getMessage(), ndapex);

        }
        logger.info("Done ejbRemove() - return: void");
    }


    /**
     * @roseuid 3BD02F4E009A
     * @J2EE_METHOD  --  ejbFindByPrimaryKey
     * Invoked by the container on the instance when the container selects the instance to
     * execute a matching client-invoked find(...) method. It executes in the transaction
     * context determined by the transaction attribute of the matching find(...) method.
     */
    public Long ejbFindByPrimaryKey(Long pk)
                  throws RemoteException, FinderException, EJBException, NEDSSSystemException
    {
        logger.debug("Organization EjbFindByPrimaryKey is called - pk = " + pk);
        Long findPK = null;
        try
        {
            if(pk != null)
            {
                logger.debug("not null - pk = " + pk);
                if(orgRootDAOImpl == null)
                {
                    logger.debug("JNDINames.ORGANIZATION_ROOT_DAO_CLASS = " + JNDINames.ORGANIZATION_ROOT_DAO_CLASS);
                    orgRootDAOImpl = (OrganizationRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_ROOT_DAO_CLASS);
                }
                findPK = orgRootDAOImpl.findByPrimaryKey(pk.longValue());
                logger.debug("return findpk: " + findPK);
                //see comments in clear case by JLD
                //this.theOrganizationVO = (OrganizationVO)orgRootDAOImpl.loadObject(pk.longValue());
            }
        }
        catch (NEDSSSystemException napex)
        {
            logger.fatal("OrganizationEJB.ejbFindByPrimaryKey: NEDSSSystemException: " + napex.getMessage(), napex);
            throw new NEDSSSystemException(napex.getMessage(), napex);

        }
        catch (EJBException e)
        {
            logger.fatal("OrganizationEJB.ejbFindByPrimaryKey: EJBException: " + e.getMessage(), e);
            throw new javax.ejb.EJBException(e.getMessage(), e);

        }
        
        return findPK;
    }

    /**
     * @roseuid 3BD02F4E016C
     * @J2EE_METHOD  --  ejbCreate
     * Matching method of the create(...) method of the bean's home interface. The container
     * invokes an ejbCreate method to create an entity object. It executes in the transaction
     * context determined by the transaction attribute of the matching create(...) method.
     */
    public Long ejbCreate (OrganizationVO organizationVO) throws RemoteException,
                                                          CreateException, DuplicateKeyException,
                                                          EJBException, NEDSSSystemException
    {
        Long organizationUID = null;

        logger.debug("OrganizationEJB create is called");
        this.theOrganizationVO = organizationVO;

        try
        {
          if(orgRootDAOImpl==null)
            orgRootDAOImpl = (OrganizationRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ORGANIZATION_ROOT_DAO_CLASS);

            this.theOrganizationVO.getTheOrganizationDT().setVersionCtrlNbr(new Integer(1));

          organizationUID = new Long(orgRootDAOImpl.create(this.theOrganizationVO));
        }
        catch(NEDSSSystemException ndsex)
        {
            logger.fatal("OrganizationEJB.ejbCreate: " + ndsex.getMessage(), ndsex);
            throw new javax.ejb.EJBException(ndsex.getMessage(),ndsex);

        }
        
        return organizationUID;
    }

    /**
     * @roseuid 3BD02F4E0176
     * @J2EE_METHOD  --  ejbPostCreate
     * Matching method of ejbCreate. The container invokes the matching ejbPostCreate method
     * on an instance after it invokes the ejbCreate method with the same arguments. It
     * executes in the same transaction context as that of the matching ejbCreate method.
     */
    public void ejbPostCreate(OrganizationVO organizationVO) throws javax.ejb.CreateException
    {

    }

    /**
     * @roseuid 3BD02F4E01E4
     * @J2EE_METHOD  --  getOrganizationVO
     */
    public OrganizationVO getOrganizationVO    ()
    {
        try {
			return theOrganizationVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("OrganizationEJB.getOrganizationVO: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);

		}
    }

    /**
     * @roseuid 3BD02F4E020C
     * @J2EE_METHOD  --  setOrganizationVO
     */
    public void setOrganizationVO(OrganizationVO organizationVO) throws NEDSSConcurrentDataException, java.rmi.RemoteException
    {
      try
      {
          if (this.theOrganizationVO.getTheOrganizationDT().getVersionCtrlNbr().intValue() !=
                  organizationVO.getTheOrganizationDT().getVersionCtrlNbr().intValue() )
          {
              logger.error("Throwing NEDSSConcurrentDataException");
              throw new NEDSSConcurrentDataException
                  ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
          }
          organizationVO.getTheOrganizationDT().setVersionCtrlNbr(new Integer(organizationVO.getTheOrganizationDT().getVersionCtrlNbr().intValue()+1));
          oldOrganizationVO = this.theOrganizationVO;
          this.theOrganizationVO = organizationVO;
      }
      catch(Exception e)
      {
          logger.debug(e.toString()+" : setOrganizationVO dataconcurrency catch: " + e.getClass());
          logger.debug("Exception string is: " + e.toString());
          if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
          {
        	  logger.fatal("OrganizationEJB.setOrganizationVO: NEDSSConcurrentDataException: " + e.getMessage(), e);
              throw new NEDSSConcurrentDataException(e.getMessage(), e);

          }
          else
          {
              logger.fatal("OrganizationEJB.setOrganizationVO: Exception: " + e.getMessage(), e);
              throw new javax.ejb.EJBException(e.getMessage(), e);

          }
      }
    }

        public EntityContext getEntityContext()
    {
        try {
			return cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("OrganizationEJB.getEntityContext: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);

		}
    }

    public void setEntityContext(EntityContext cntx)
    {
        try {
			this.cntx = cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("OrganizationEJB.setEntityContext: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);

		}
    }

    public void unsetEntityContext()
    {
        try {
			cntx = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("OrganizationEJB.unsetEntityContext: " + e.getMessage(), e);
			throw new javax.ejb.EJBException(e.getMessage(), e);

		}
    }

  /**
  * This method will take the oldOrganizationVO and call the method to store that in the History
  * @throws NEDSSSystemException
  */
  private void insertHistory() throws NEDSSSystemException {
    try {
		if( oldOrganizationVO != null )
		{
		  logger.debug("OrganizationEJB in ejbStore(), organizationUID in oldOrganizationVO : " + oldOrganizationVO.getTheOrganizationDT().getOrganizationUid().longValue());
		  long placeUID = oldOrganizationVO.getTheOrganizationDT().getOrganizationUid().longValue();
		  short versionCtrlNbr = oldOrganizationVO.getTheOrganizationDT().getVersionCtrlNbr().shortValue();
		  OrganizationHistoryManager orgHistoryManager = new OrganizationHistoryManager(placeUID, versionCtrlNbr);
		  orgHistoryManager.store(this.oldOrganizationVO);
		  this.oldOrganizationVO = null;
		} else {
		    logger.info("Attempt to insert Organization history failed because oldOrganizationVO == null in OrganizationEJB");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("OrganizationEJB.insertHistory: " + e.getMessage(), e);
		throw new javax.ejb.EJBException(e.getMessage(), e);

	}
  }

}
