//
// -- Java Code Generation Process --

package gov.cdc.nedss.act.publichealthcase.ejb.bean;

/**
* Name:		EJB class for PublicHealthCase entity bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseDAOImpl;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseHistoryManager;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseRootDAOImpl;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pam.dao.NbsHistoryDAO;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.rmi.RemoteException;

import javax.ejb.EJBException;

public class PublicHealthCaseEJB implements javax.ejb.EntityBean
{
    /*
       Attributes declaration
    */
	private static final long serialVersionUID = 1L;
    private PublicHealthCaseVO phcVO;
    private PublicHealthCaseVO oldPhcVO;
    private long phcUID;
    private PublicHealthCaseRootDAOImpl phcRootDAO = null;
    private javax.ejb.EntityContext cntx;

    //For logging
    static final LogUtils logger = new LogUtils(PublicHealthCaseEJB.class.getName());

    /**
     * @roseuid 3BD0271003D8
     * @J2EE_METHOD  --  PublicHealthCaseEJB
     */
    public PublicHealthCaseEJB()
    {

    }

    /**
     * @roseuid 3BD0271100A4
     * @J2EE_METHOD  --  ejbActivate
     * A container invokes this method when the instance is taken out of the pool of available
     * instances to become associated with a specific EJB object. This method transitions
     * the instance to the ready state. This method executes in an unspecified transaction
     * context.
     */
    public void ejbActivate()
    {

    }

    /**
     * @roseuid 3BD0271100B8
     * @J2EE_METHOD  --  ejbPassivate
     * A container invokes this method on an instance before the instance becomes disassociated
     * with a specific EJB object. After this method completes, the container will place
     * the instance into the pool of available instances. This method executes in an unspecified
     * transaction context.
     */
    public void ejbPassivate()
    {
        this.phcRootDAO = null;
    }

    /**
     * @roseuid 3BD0271100C2
     * @J2EE_METHOD  --  ejbLoad
     * A container invokes this method to instruct the instance to synchronize its state
     * by loading it from the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     */
    public void ejbLoad() throws EJBException
    {
logger.debug("PHC EjbLoad is called");
        try
        {
            if(phcRootDAO == null)
            {
                phcRootDAO = (PublicHealthCaseRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PUBLIC_HEALTH_CASE_ROOT_DAO_CLASS);
            }

            this.phcVO = (PublicHealthCaseVO)phcRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
            this.phcVO.setItDirty(false);
            this.phcVO.setItNew(false);
        }

        catch(NEDSSSystemException nsex)
        {
            logger.fatal("PublicHealthCaseEJB.ejbLoad: " + nsex.getMessage(),nsex);
            throw new EJBException(nsex.getMessage(),nsex);
        }
    }

    /**
     * @roseuid 3BD0271100D6
     * @J2EE_METHOD  --  ejbStore
     * A container invokes this method to instruct the instance to synchronize its state
     * by storing it to the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     */
    public void ejbStore() throws EJBException
    {
logger.debug("PHC EjbStore is called");

        try
        {
            if(phcRootDAO == null)
            {
                phcRootDAO = (PublicHealthCaseRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PUBLIC_HEALTH_CASE_ROOT_DAO_CLASS);
            }
            if(this.phcVO != null && this.phcVO.isItDirty())
            {
                phcRootDAO.store(this.phcVO);
                this.phcVO.setItDirty(false);
                this.phcVO.setItNew(false);
                //Waiting on the "go ahead" to uncomment
                insertHistory();
            }


        }
        catch(NEDSSConcurrentDataException ndcex)
        {
           logger.debug("Got into concurrent exception in interventionEJB");
           logger.fatal("PublicHealthCaseEJB.ejbStore: NEDSSConcurrentDataException: " + ndcex.getMessage(),ndcex);
            throw new NEDSSSystemException(ndcex.getMessage(),ndcex);
        }
        catch(NEDSSSystemException napex)
        {
        	logger.fatal("PublicHealthCaseEJB.ejbStore: NEDSSSystemException: " + napex.getMessage(),napex);
            throw new EJBException(napex.getMessage(),napex);
        }
		catch(Exception ex)
        {
        	logger.fatal("PublicHealthCaseEJB.ejbStore: Exception: " + ex.getMessage(),ex);
            throw new EJBException(ex.getMessage(),ex);
        }
    }

    /**
     * @roseuid 3BD0271100EA
     * @J2EE_METHOD  --  ejbRemove
     * A container invokes this method before it removes the EJB object that is currently
     * associated with the instance. It is invoked when a client invokes a remove operation
     * on the enterprise Bean's home or remote interface. It transitions the instance from
     * the ready state to the pool of available instances. It is called in the transaction
     * context of the remove operation.
     */
    public void ejbRemove() throws javax.ejb.RemoveException
    {
      //waiting on the "Go ahead" to uncomment
      /*insertHistory();*/
    }

    /**
     * @roseuid 3BD0271100FE
     * @J2EE_METHOD  --  setEntityContext
     * Set the associated entity context. The container invokes this method on an instance
     * after the instance has been created. This method is called in an unspecified transaction
     * context.
     */
    public void setEntityContext(javax.ejb.EntityContext ctx)
    {
        this.cntx = ctx;
    }

    /**
     * @roseuid 3BD027110112
     * @J2EE_METHOD  --  unsetEntityContext
     * Unset the associated entity context. The container calls this method before removing
     * the instance. This is the last method that the container invokes on the instance.
     * The Java garbage collector will  invoke the finalize() method on the instance. It
     * is called in an unspecified transaction context.
     */
    public void unsetEntityContext()
    {
        this.cntx = null;
    }

    /**
     * @roseuid 3BD039860005
     * @J2EE_METHOD  --  ejbFindByPrimaryKey
     * Invoked by the container on the instance when the container selects the instance to
     * execute a matching client-invoked find(...) method. It executes in the transaction
     * context determined by the transaction attribute of the matching find(...) method.
     */
    public Long ejbFindByPrimaryKey(Long primaryKey) throws javax.ejb.FinderException,
          EJBException
    {
logger.debug("EjbFindByPrimaryKey is called");
        Long findPK = null;
        try
        {
            if(primaryKey != null)
            {
                if(phcRootDAO == null)
                {
                    phcRootDAO = (PublicHealthCaseRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PUBLIC_HEALTH_CASE_ROOT_DAO_CLASS);
                }
                findPK = phcRootDAO.findByPrimaryKey(primaryKey.longValue());
logger.debug("return findpk: " + findPK);
                //this.phcVO = (PublicHealthCaseVO)phcRootDAO.loadObject(primaryKey.longValue());
            }
        }

        catch (NEDSSDAOSysException nphcdsex)
        {
            logger.fatal("Exception in PHC find by primary key", nphcdsex);
            logger.fatal("PublicHealthCaseEJB.ejbFindByPrimaryKey: NEDSSDAOSysException: " + nphcdsex.getMessage(), nphcdsex);
            throw new EJBException (nphcdsex.getMessage(),nphcdsex);
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("PublicHealthCaseEJB.ejbFindByPrimaryKey: NEDSSSystemException: " + nsex.getMessage(), nsex);
            throw new EJBException(nsex.getMessage(),nsex);
        }
		catch(Exception nsex)
        {
            logger.fatal("PublicHealthCaseEJB.ejbFindByPrimaryKey: Exception: " + nsex.getMessage(), nsex);
            throw new EJBException(nsex.getMessage(),nsex);
        }
        return findPK;
    }

    /**
     * @roseuid 3BD0398600B9
     * @J2EE_METHOD  --  ejbCreate
     * Matching method of the create(...) method of the bean's home interface. The container
     * invokes an ejbCreate method to create an entity object. It executes in the transaction
     * context determined by the transaction attribute of the matching create(...) method.
     */
    public Long ejbCreate(PublicHealthCaseVO phcVO) throws RemoteException,
                javax.ejb.CreateException,  EJBException
    {
logger.debug("PHC EjbCreate is called");

        this.phcVO = phcVO;

        try
        {
            if(phcRootDAO == null)
            {
                phcRootDAO = (PublicHealthCaseRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PUBLIC_HEALTH_CASE_ROOT_DAO_CLASS);
            }
            this.phcVO.getThePublicHealthCaseDT().setVersionCtrlNbr(new Integer(1));
            if(this.phcVO.getThePublicHealthCaseDT().getSharedInd() == null)
            this.phcVO.getThePublicHealthCaseDT().setSharedInd("T");
            phcUID = phcRootDAO.create(this.phcVO);
            this.phcVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(new Long(phcUID));
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("PublicHealthCaseEJB.ejbCreate: " + ndapex.getMessage(), ndapex);
            throw new EJBException(ndapex.getMessage(),ndapex);
        }
		catch(Exception ndapex)
        {
            logger.fatal("PublicHealthCaseEJB.ejbCreate: " + ndapex.getMessage(), ndapex);
            throw new EJBException(ndapex.getMessage(),ndapex);
        }
        return (new Long(phcUID));
    }

    /**
     * @roseuid 3BD0398600C3
     * @J2EE_METHOD  --  ejbPostCreate
     * Matching method of ejbCreate. The container invokes the matching ejbPostCreate method
     * on an instance after it invokes the ejbCreate method with the same arguments. It
     * executes in the same transaction context as that of the matching ejbCreate method.
     */
    public void ejbPostCreate(PublicHealthCaseVO phcVO) throws javax.ejb.CreateException
    {

    }

    /**
     * @roseuid 3BD039860128
     * @J2EE_METHOD  --  getPublicHealthCaseVO
     */
    public PublicHealthCaseVO getPublicHealthCaseVO()
    {
        return phcVO;
    }

    /**
     * @roseuid 3BD039860146
     * @J2EE_METHOD  --  setPublicHealthCaseVO
     */
    public void setPublicHealthCaseVO(PublicHealthCaseVO publicHealthCaseVO) throws NEDSSConcurrentDataException

    {
       try
      {
          if (this.phcVO.getThePublicHealthCaseDT().getVersionCtrlNbr().intValue() !=
                  publicHealthCaseVO.getThePublicHealthCaseDT().getVersionCtrlNbr().intValue() )
          {
              logger.error("Throwing NEDSSConcurrentDataException");
              throw new NEDSSConcurrentDataException
                  ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
          }
          publicHealthCaseVO.getThePublicHealthCaseDT().setVersionCtrlNbr(new Integer(publicHealthCaseVO.getThePublicHealthCaseDT().getVersionCtrlNbr().intValue()+1));
          oldPhcVO = this.phcVO;
          this.phcVO = publicHealthCaseVO;
      }
      catch(Exception e)
      {
          logger.debug(e.toString()+" : setPublicHealthCaseVO dataconcurrency catch: " + e.getClass());
          logger.debug("Exception string is: " + e.toString());
          if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
          {
              logger.fatal("PublicHealthCaseEJB.setPublicHealthCaseVO: NEDSSConcurrentDataException: " + e.getMessage(), e);
              throw new NEDSSConcurrentDataException(e.getMessage(),e);
          }
          else
          {
              logger.fatal("PublicHealthCaseEJB.setPublicHealthCaseVO: EJBException: " + e.getMessage(), e);
              throw new EJBException(e.getMessage(),e);
          }
      }
    }    
    
    private void insertHistory() throws NEDSSSystemException {
      try {
		//If oldPhcVO is not null insert into history
		  if(oldPhcVO != null) {
		    logger.debug("PublicHealthCaseEJB in ejbStore(), publicHealthCaseUID in phcVO : " + phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid().longValue());
		    long phcUID = oldPhcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid().longValue();
		    short versionCtrlNbr = oldPhcVO.getThePublicHealthCaseDT().getVersionCtrlNbr().shortValue();
		    PublicHealthCaseHistoryManager phcHistoryManager = new PublicHealthCaseHistoryManager(phcUID, versionCtrlNbr);
		    phcHistoryManager.store(this.oldPhcVO);
		    //gst simplified logic to always call history dao
		    NbsHistoryDAO nbsHistory = new NbsHistoryDAO();
		 	nbsHistory.insertPamHistory(phcVO,oldPhcVO);
		    
		  this.oldPhcVO = null;
		   }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("PublicHealthCaseEJB.insertHistory: " + e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}

      }
    
   

}
