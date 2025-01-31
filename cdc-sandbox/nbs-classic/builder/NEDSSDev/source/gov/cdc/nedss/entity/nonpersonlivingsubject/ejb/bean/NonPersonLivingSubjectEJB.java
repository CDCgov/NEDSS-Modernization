/**
* Name:		EJB class for NonPersonLivingSubject Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/


package gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.bean;

// Import Statements
import java.sql.Connection;
import java.rmi.RemoteException;

import javax.sql.DataSource;
import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.nonpersonlivingsubject.vo.*;
import gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.dao.*;
import gov.cdc.nedss.systemservice.util.*;

public class NonPersonLivingSubjectEJB implements javax.ejb.EntityBean
{
    /*
       Attributes declaration
    */
    public javax.ejb.EntityContext EJB_Context;
    public java.sql.Connection EJB_Connection = null;
    public javax.sql.DataSource EJB_Datasource = null;
    private NonPersonLivingSubjectVO theNonPersonLivingSubjectVO;
    private NonPersonLivingSubjectVO oldNonPersonLivingSubjectVO;
    private NonPersonLivingSubjectRootDAOImpl nplsRootDAO;

    /**
     * For logging
     */
     static final LogUtils logger = new LogUtils(NonPersonLivingSubjectEJB.class.getName());

    /**
     * @roseuid 3BD41BFF022E
     * @J2EE_METHOD  --  NonPersonLivingSubjectEJB
     */
    public NonPersonLivingSubjectEJB    ()
    {

    }

    /**
     * @roseuid 3BD41BFF0350
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
     * @roseuid 3BD41BFF0364
     * @J2EE_METHOD  --  ejbPassivate
     * A container invokes this method on an instance before the instance becomes disassociated
     * with a specific EJB object. After this method completes, the container will place
     * the instance into the pool of available instances. This method executes in an unspecified
     * transaction context.
     */
    public void ejbPassivate    ()
    {
      this.nplsRootDAO = null;
    }

    /**
     * @roseuid 3BD41BFF0378
     * @J2EE_METHOD  --  ejbLoad
     * A container invokes this method to instruct the instance to synchronize its state
     * by loading it from the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     * @throws EJBException
     */
    public void ejbLoad() throws EJBException
    {
      logger.debug("NonPersonLivingSubjectEJB load is called");
      try
      {
        if(nplsRootDAO==null)
          nplsRootDAO = (NonPersonLivingSubjectRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_ROOT_DAO_CLASS);
        this.theNonPersonLivingSubjectVO =  (NonPersonLivingSubjectVO)nplsRootDAO.loadObject(((Long)EJB_Context.getPrimaryKey()).longValue());
        this.theNonPersonLivingSubjectVO.setItDirty(false);
        this.theNonPersonLivingSubjectVO.setItNew(false);
      }
      catch(NEDSSSystemException ndsex)
      {
          logger.fatal("Fails ejbLoad()"+ndsex.getMessage(), ndsex);
          throw new EJBException(ndsex.getMessage(),ndsex);
      }
      /*catch(NEDSSAppException ndapex)
      {
          logger.fatal("Fails ejbLoad()", ndapex);
          throw new EJBException(ndapex.toString());
      }*/
    }

    /**
     * @roseuid 3BD41BFF0382
     * @J2EE_METHOD  --  ejbStore
     * A container invokes this method to instruct the instance to synchronize its state
     * by storing it to the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     * @throws javax.ejb.EJBException
     */
    public void ejbStore    () throws javax.ejb.EJBException
    {
      logger.debug("NonPersonLivingSubjectEJB store is called");
      try
      {
        if(nplsRootDAO==null){
          nplsRootDAO = (NonPersonLivingSubjectRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_ROOT_DAO_CLASS);
        }
        try{
         if(this.theNonPersonLivingSubjectVO != null && this.theNonPersonLivingSubjectVO.isItDirty())
         {
            nplsRootDAO.store(this.theNonPersonLivingSubjectVO);

            this.theNonPersonLivingSubjectVO.setItDirty(false);
            this.theNonPersonLivingSubjectVO.setItNew(false);
            //Waiting on the "go ahead" to uncomment the following code
            //insertHistory();Not required as of 6/27/02
         }
        }
        catch(NEDSSConcurrentDataException ndcex)
        {
          logger.fatal("NEDSSConcurrentDataException thrown in NONPersonLivingSubjectEJB"+ndcex.getMessage(),ndcex);
          EJB_Context.setRollbackOnly();
          throw new NEDSSSystemException(ndcex.getMessage(),ndcex);
        }
      }
      catch(NEDSSSystemException ndsex)
      {
          logger.fatal("Fails ejbStore()"+ndsex.getMessage(), ndsex);
          throw new EJBException(ndsex.getMessage(),ndsex);
      }
      /*catch(NEDSSAppException ndapex)
      {
          logger.fatal("Fails ejbStore()", ndapex);
          throw new EJBException(ndapex.toString());
      }*/

    }

    /**
     * @roseuid 3BD41BFF0396
     * @J2EE_METHOD  --  ejbRemove
     * A container invokes this method before it removes the EJB object that is currently
     * associated with the instance. It is invoked when a client invokes a remove operation
     * on the enterprise Bean's home or remote interface. It transitions the instance from
     * the ready state to the pool of available instances. It is called in the transaction
     * context of the remove operation.
     * @throws javax.ejb.RemoveException
     */
    public void ejbRemove    () throws javax.ejb.RemoveException
    {
      // insertHistory();
    }

    /**
     * @roseuid 3BD41BFF03BE
     * @J2EE_METHOD  --  unsetEntityContext
     * Unset the associated entity context. The container calls this method before removing
     * the instance. This is the last method that the container invokes on the instance.
     * The Java garbage collector will  invoke the finalize() method on the instance. It
     * is called in an unspecified transaction context.
     */
    public void unsetEntityContext    ()
    {
        EJB_Context = null;
    }

    /**
     * @roseuid 3BD41BFF03AA
     * @J2EE_METHOD  --  setEntityContext
     * Set the associated entity context. The container invokes this method on an instance
     * after the instance has been created. This method is called in an unspecified transaction
     * context.
     * @param entitycontext EntityContext
     * @throws RemoveException
     * @throws EJBException
     */
    public void setEntityContext(EntityContext entitycontext) throws EJBException,RemoteException
    {
        EJB_Context = entitycontext;
    }

     /**
     * @roseuid 3BD49B7A03DE
     * @J2EE_METHOD  --  ejbFindByPrimaryKey
     * Invoked by the container on the instance when the container selects the instance to
     * execute a matching client-invoked find(...) method. It executes in the transaction
     * context determined by the transaction attribute of the matching find(...) method.
     * @param pk    the Long
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSAppException
     */
    public Long ejbFindByPrimaryKey(Long pk)
                      throws RemoteException, FinderException, EJBException, NEDSSAppException
    {
        logger.debug("NonPersonLivingSubject EjbFindByPrimaryKey is called - pk = " + pk);
        Long findPK = null;
        try
        {
            if(pk != null)
            {
                logger.debug("NonPersonLivingSubject not null - pk = " + pk);
                if(nplsRootDAO == null)
                {
                    logger.debug("JNDINames.ORGANIZATION_ROOT_DAO_CLASS = " + JNDINames.NONPERSONLIVINGSUBJECT_ROOT_DAO_CLASS);
                    nplsRootDAO = (NonPersonLivingSubjectRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_ROOT_DAO_CLASS);
                }
                findPK = nplsRootDAO.findByPrimaryKey(pk.longValue());
                logger.debug("NonPersonLivingSubject return findpk: " + findPK);
                //see comments by JLD in clear case
                //this.theNonPersonLivingSubjectVO = (NonPersonLivingSubjectVO)nplsRootDAO.loadObject(pk.longValue());
            }
        }
        /*catch (NEDSSAppException napex)
        {
            logger.fatal("NonPersonLivingSubject - Exception in find by primary key", napex);
             throw new EJBException(napex.toString());
        }*/
        catch (EJBException ejbex)
        {
            logger.fatal("NonPersonLivingSubject - Exception in find by primary key"+ejbex.getMessage(), ejbex);
            throw new EJBException(ejbex.getMessage(),ejbex);
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("NonPersonLivingSubject - Exception in find by primary key"+nsex.getMessage(), nsex);
            throw new EJBException(nsex.getMessage(),nsex);
        }
        return findPK;
    }

    /**
     * @roseuid 3BD49B7B00AA
     * @J2EE_METHOD  --  ejbCreate
     * Matching method of the create(...) method of the bean's home interface. The container
     * invokes an ejbCreate method to create an entity object. It executes in the transaction
     * context determined by the transaction attribute of the matching create(...) method.
     * @param nonPersonLivingSubjectVO    the NonPersonLivingSubjectVO
     * @throws RemoteException
     * @throws CreateException
     * @throws DuplicateKeyException
     * @throws EJBException
     * @throws NEDSSAppException
     */
    public java.lang.Long ejbCreate    (NonPersonLivingSubjectVO nonPersonLivingSubjectVO) throws RemoteException,
                                                          CreateException, DuplicateKeyException,
                                                          EJBException, NEDSSAppException
    {
        Long nonPersonUID = null;

        logger.debug("NonPersonLivingSubjectEJB create is called");
        this.theNonPersonLivingSubjectVO = nonPersonLivingSubjectVO;

        try
        {
          if(nplsRootDAO==null)
          nplsRootDAO = (NonPersonLivingSubjectRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.NONPERSONLIVINGSUBJECT_ROOT_DAO_CLASS);
          this.theNonPersonLivingSubjectVO.getTheNonPersonLivingSubjectDT().setVersionCtrlNbr(new Integer(1));
          nonPersonUID = new Long(nplsRootDAO.create(this.theNonPersonLivingSubjectVO));
        }
        catch(NEDSSSystemException ndsex)
        {
            logger.fatal("Fails ejbCreate()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);
        }
        /*catch(NEDSSAppException ndapex)
        {
            logger.fatal("Fails ejbCreate()", ndapex);
            throw new EJBException(ndapex.toString());
        }*/
        return nonPersonUID;
    }

    /**
     * @roseuid 3BD49B7B00B4
     * @J2EE_METHOD  --  ejbPostCreate
     * Matching method of ejbCreate. The container invokes the matching ejbPostCreate method
     * on an instance after it invokes the ejbCreate method with the same arguments. It
     * executes in the same transaction context as that of the matching ejbCreate method.
     * @param nonPersonLivingSubjectVO    the NonPersonLivingSubjectVO
     * @throws javax.ejb.CreateException
     */
    public void ejbPostCreate    (NonPersonLivingSubjectVO nonPersonLivingSubjectVO) throws javax.ejb.CreateException
    {

    }

    /**
     * @roseuid 3BD49B7B010E
     * @J2EE_METHOD  --  getNonPersonLivingSubjectVO
     */
    public NonPersonLivingSubjectVO getNonPersonLivingSubjectVO    ()
    {
        return theNonPersonLivingSubjectVO;
    }

    /**
     * @roseuid 3BD49B7B0122
     * This method is used to store the data in a NonPersonLivingSubjectVO to the database.
     * @J2EE_METHOD  --  setNonPersonLivingSubjectVO
     * @param nonPersonLivingSubjectVO    the NonPersonLivingSubjectVO
     * @throws NEDSSConcurrentDataException
     */
    public void setNonPersonLivingSubjectVO
                    (NonPersonLivingSubjectVO nonPersonLivingSubjectVO)
                    throws NEDSSConcurrentDataException
    {
        try
        {
           if (this.theNonPersonLivingSubjectVO.getTheNonPersonLivingSubjectDT().getVersionCtrlNbr().intValue() !=
                    nonPersonLivingSubjectVO.getTheNonPersonLivingSubjectDT().getVersionCtrlNbr().intValue() )
            {
               logger.error("Throwing NEDSSConcurrentDataException");
               throw new NEDSSConcurrentDataException
                    ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
            }
            nonPersonLivingSubjectVO.getTheNonPersonLivingSubjectDT().setVersionCtrlNbr(new Integer(nonPersonLivingSubjectVO.getTheNonPersonLivingSubjectDT().getVersionCtrlNbr().intValue()+1));
            oldNonPersonLivingSubjectVO = this.theNonPersonLivingSubjectVO;
            this.theNonPersonLivingSubjectVO = nonPersonLivingSubjectVO;
        }
        catch(Exception e)
        {
            logger.debug(e.toString()+" : setNonLivingPersonVO dataconcurrency catch: " + e.getClass());
            logger.debug("Exception string is: " + e.toString());
            if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
            {
               // cntx.setRollbackOnly();
                logger.fatal("Throwing NEDSSConcurrentDataException");
                throw new NEDSSConcurrentDataException( e.getMessage(),e);
            }
            else
            {
                logger.fatal("Throwing generic Exception");
                throw new EJBException(e.getMessage(),e);
            }
        }

    }
    /**
      * Makes an entry to the nonPersonLivingSubject history tables.
      * @J2EE_METHOD  --  insertHistory
      * @throws NEDSSDAOAppException
    **/
    private void insertHistory() throws NEDSSDAOAppException{
      try {
		if( oldNonPersonLivingSubjectVO != null )
		  {
		    logger.debug("PlaceEJB in ejbStore(), placeUID in oldNonPersonLivingSubjectVO : " + oldNonPersonLivingSubjectVO.getTheNonPersonLivingSubjectDT().getNonPersonUid().longValue());
		    long nonPersonUID = oldNonPersonLivingSubjectVO.getTheNonPersonLivingSubjectDT().getNonPersonUid().longValue();
		    short versionCtrlNbr = oldNonPersonLivingSubjectVO.getTheNonPersonLivingSubjectDT().getVersionCtrlNbr().shortValue();
		    NonPersonLivingSubjectHistoryManager nplsHistoryManager = new NonPersonLivingSubjectHistoryManager(nonPersonUID, versionCtrlNbr);
		    nplsHistoryManager.store(this.oldNonPersonLivingSubjectVO);
		    this.oldNonPersonLivingSubjectVO = null;
		  }
	} catch (NEDSSDAOSysException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
    }
}