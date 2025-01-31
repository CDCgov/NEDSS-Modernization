/**
 * Title:        MaterialEJB
 * Description:  Material Entity EJB
 *
 * Copyright:    Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author       11/06/2001 Sohrab Jahani & NEDSS Development Team
 * @modified     11/30/2001 Sohrab Jahani
 * @version      1.0.0
 */

//
// Original code was made by:
// -- Java Code Generation Process --

package gov.cdc.nedss.entity.material.ejb.bean;

// Import Statements
import java.rmi.RemoteException;
import java.sql.Connection;

import javax.ejb.*;
import javax.sql.DataSource;



// gov.cdc.nedss.* imports
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.entity.material.ejb.dao.*;
import gov.cdc.nedss.systemservice.util.*;

public class MaterialEJB implements EntityBean {
    /*
       Attributes declaration
    */

    /**
     * For logging
     */
     static final LogUtils logger = new LogUtils(MaterialEJB.class.getName());
    /**
     * EJB Context of MaterialEJB
     */
    private EntityContext eJBContext;

    /**
     * Value Object that Material EJB uses
     */
    private MaterialVO theMaterialVO;
    private MaterialVO oldMaterialVO;

    /**
     * Material UID
     */
    private long materialUid;

    /**
     * Material Root DAO that Material EJB uses
     */
    private MaterialRootDAOImpl matRootDAO = null;

    /**
     * @roseuid 3BD4A60C0262
     * @J2EE_METHOD  --  MaterialEJB
     */
    public MaterialEJB () {

    }

    /**
     * NOT_VALID_AT_SIGN_roseuid 3BD6BD9B0135
     * @J2EE_METHOD  --  getMaterialVO
     *
     * Gets the value of materialVO
     *
     * @return            Material value object that was set
     *
     * @exception RemoteException If a remote exception happens.
     */
    public EntityContext getEntityContext() {
        return eJBContext;
    }

    /**
     * @roseuid 3BD4A60C037A
     * @J2EE_METHOD  --  setEntityContext
     * Set the associated entity context. The container invokes this method on an instance
     * after the instance has been created. This method is called in an unspecified transaction
     * context.
     *
     * @param ctx Material EJB Entity Context
     */
    public void setEntityContext (EntityContext ctx) {
        eJBContext = ctx;
    }

    /**
     * @roseuid 3BD4A60C038E
     * @J2EE_METHOD  --  unsetEntityContext
     * Unset the associated entity context. The container calls this method before removing
     * the instance. This is the last method that the container invokes on the instance.
     * The Java garbage collector will  invoke the finalize() method on the instance. It
     * is called in an unspecified transaction context.
     */
    public void unsetEntityContext () {
        eJBContext = null;
    }

    /**
     * @roseuid 3BD4A60C0320
     * @J2EE_METHOD  --  ejbActivate
     * A container invokes this method when the instance is taken out of the pool of available
     * instances to become associated with a specific EJB object. This method transitions
     * the instance to the ready state. This method executes in an unspecified transaction
     * context.
     */
    public void ejbActivate () {

    }

    /**
     * @roseuid 3BD4A60C0334
     * @J2EE_METHOD  --  ejbPassivate
     * A container invokes this method on an instance before the instance becomes disassociated
     * with a specific EJB object. After this method completes, the container will place
     * the instance into the pool of available instances. This method executes in an unspecified
     * transaction context.
     */
    public void ejbPassivate () {
        this.matRootDAO = null;
    }

    /**
     * @roseuid 3BD4A60C0348
     * @J2EE_METHOD  --  ejbLoad
     * A container invokes this method to instruct the instance to synchronize its state
     * by loading it from the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     *
     * @exception EJBException EJB Exception
     */
    public void ejbLoad () throws EJBException {

      logger.debug("Material EJB load is called"); // debuggin
      try {
        if(matRootDAO==null)
          matRootDAO = (MaterialRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_ROOT_DAO_CLASS);
        this.theMaterialVO =  (MaterialVO)matRootDAO.loadObject(((Long)eJBContext.getPrimaryKey()).longValue());
        this.theMaterialVO.setItDirty(false);
        this.theMaterialVO.setItNew(false);

      } catch(NEDSSSystemException ndsex) {
          logger.fatal("Fails ejbLoad()"+ndsex.getMessage(), ndsex);
          throw new EJBException(ndsex.getMessage(),ndsex);

      }
    }

    /**
     * @roseuid 3BD4A60C0352
     * @J2EE_METHOD  --  ejbStore
     * A container invokes this method to instruct the instance to synchronize its state
     * by storing it to the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     *
     * @exception EJBException EJB Exception
     */
    public void ejbStore ()
      throws EJBException{

      logger.debug("MaterialEJB store is called"); // debugging
      try {
        if(matRootDAO==null) {
          logger.debug("MaterialEJB.ejbStore() - matRootDAO==null");
          matRootDAO = (MaterialRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_ROOT_DAO_CLASS);
        }
        logger.debug("MaterialEJB.ejbStore() matRootDAO="+ matRootDAO.toString());
        logger.debug("MaterialEJB.ejbStore() theMaterialVO="+ theMaterialVO.toString());
        if(this.theMaterialVO != null && this.theMaterialVO.isItDirty())
        {
           try
           {
              matRootDAO.store(this.theMaterialVO);

              this.theMaterialVO.setItDirty(false);
              this.theMaterialVO.setItNew(false);
              //Waiting on the "go ahead" to uncomment the following code
              insertHistory();
              logger.debug("MaterialEJB.ejbStore().theMaterialVO = " + theMaterialVO.toString());
           }
           catch(NEDSSConcurrentDataException ndcex)
          {
              logger.fatal("NEDSSConcurrentDataException in MaterialEJB"+ndcex.getMessage(), ndcex);
              //eJBContext.setRollbackOnly();
              throw new NEDSSConcurrentDataException(ndcex.getMessage(),ndcex);
          }

        }

      }
       catch(NEDSSConcurrentDataException ndcex)
      {
          logger.fatal("NEDSSConcurrentDataException in MaterialEJB"+ndcex.getMessage(), ndcex);
          //eJBContext.setRollbackOnly();
          throw new NEDSSSystemException(ndcex.getMessage(),ndcex);
      }
      catch(NEDSSSystemException ndsex) {
          logger.fatal("Fails ejbStore()"+ndsex.getMessage(), ndsex);
          throw new EJBException(ndsex.getMessage(),ndsex);

      }
    }

    /**
     * @roseuid 3BD4A60C0366
     * @J2EE_METHOD  --  ejbRemove
     * A container invokes this method before it removes the EJB object that is currently
     * associated with the instance. It is invoked when a client invokes a remove operation
     * on the enterprise Bean's home or remote interface. It transitions the instance from
     * the ready state to the pool of available instances. It is called in the transaction
     * context of the remove operation.
     *
     * @exception RemoveException EJB Remove Exception
     *
     */
    public void ejbRemove ()
      throws
        RemoveException
      {
        try {
            if(matRootDAO == null) {
              matRootDAO = (MaterialRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_ROOT_DAO_CLASS);
            }
            insertHistory();
            matRootDAO.remove(((Long)eJBContext.getPrimaryKey()).longValue());

        } catch(NEDSSSystemException ndsex) {
            logger.fatal("Fails ejbRemove()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);

        }

    }

    /**
     * @roseuid 3BD6BD9A0396
     * @J2EE_METHOD  --  ejbFindByPrimaryKey
     * Invoked by the container on the instance when the container selects the instance to
     * execute a matching client-invoked find(...) method. It executes in the transaction
     * context determined by the transaction attribute of the matching find(...) method.
     *
     * @param materialUid Primary Key to search for Material
     *
     * @exception EJBException general EJB Exception
     * @exception FinderException EJB Finder Exception
     * @exception NEDSSSystemException EJB NEDSS Applictation Exception
     * @exception RemoteException EJB Remote Exception
     *
     * @return    Uid of the Material value object
     */
    public Long ejbFindByPrimaryKey (Long pk)
      throws
        EJBException,
        FinderException,
        NEDSSSystemException,
        RemoteException
    {
      logger.debug("Material EjbFindByPrimaryKey is called - pk = " + pk); // debugging
        Long findPK = null;
        try {
            if(pk != null) {
                logger.debug("not null - pk = " + pk);
                if(matRootDAO == null) {
                    logger.debug("JNDINames.MATERIAL_ROOT_DAO_CLASS = " + JNDINames.MATERIAL_ROOT_DAO_CLASS);
                    matRootDAO = (MaterialRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_ROOT_DAO_CLASS);
                }
                findPK = matRootDAO.findByPrimaryKey(pk.longValue());
                logger.debug("return findpk: " + findPK);
                //this.theMaterialVO = (MaterialVO)matRootDAO.loadObject(pk.longValue());
            }

        } catch (NEDSSSystemException napex) {
            logger.fatal("Exception in find by primary key"+napex.getMessage(), napex);
            throw new EJBException(napex.getMessage(),napex);

        } catch (EJBException ejbex) {
            logger.fatal("Exception in find by primary key"+ejbex.getMessage(), ejbex);
            throw new EJBException(ejbex.getMessage(), ejbex);

        }
        return findPK;
    }

    /**
     * @roseuid 3BD6BD9B0058
     * @J2EE_METHOD  --  ejbCreate
     * Matching method of the create(...) method of the bean's home interface. The container
     * invokes an ejbCreate method to create an entity object. It executes in the transaction
     * context determined by the transaction attribute of the matching create(...) method.
     *
     * @param materialVO Material Value Object
     *
     * @exception CreateException EJB Create Exception
     * @exception DuplicateKeyException EJB Duplicate Key Exception
     * @exception EJBException general EJB Exception
     * @exception FinderException EJB Finder Exception
     * @exception NEDSSSystemException EJB NEDSS Applictation Exception
     * @exception RemoteException EJB Remote Exception
     *
     * @return    Uid of the Material value object
     */
    public Long ejbCreate (MaterialVO materialVO)
      throws
        CreateException,
        DuplicateKeyException,
        EJBException,
        NEDSSSystemException,
        RemoteException
    {
        Long materialUID = null;

        logger.debug("MaterialEJB create is called");
        this.theMaterialVO = materialVO;

        try {
          if(matRootDAO==null)
            matRootDAO = (MaterialRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.MATERIAL_ROOT_DAO_CLASS);
          this.theMaterialVO.getTheMaterialDT().setVersionCtrlNbr(new Integer(1));
          materialUID = new Long(matRootDAO.create(this.theMaterialVO));

        } catch(NEDSSSystemException ndsex) {
            logger.fatal("Fails ejbCreate()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);

        }
        return materialUID;
    }

    /**
     * @roseuid 3BD6BD9B0077
     * @J2EE_METHOD  --  ejbPostCreate
     * Matching method of ejbCreate. The container invokes the matching ejbPostCreate method
     * on an instance after it invokes the ejbCreate method with the same arguments. It
     * executes in the same transaction context as that of the matching ejbCreate method.
     *
     * @exception CreateException EJB Create Exception
     */
    public void ejbPostCreate (MaterialVO materialVO)
      throws CreateException {
    }

    /**
     * @roseuid 3BD6BD9B00D1
     * @J2EE_METHOD  --  setMaterialVO
     *
     * Sets the value of materialVO
     *
     * @param materialVO Material value object
     *
     * @return            Uid of the Material value object that was set
     */
    public void setMaterialVO (MaterialVO materialVO) throws NEDSSConcurrentDataException
    {
      try
      {
          if (this.theMaterialVO.getTheMaterialDT().getVersionCtrlNbr().intValue() !=
                  theMaterialVO.getTheMaterialDT().getVersionCtrlNbr().intValue() )
          {
             // cntx.setRollbackOnly();
              logger.error("Throwing NEDSSConcurrentDataException");
              throw new NEDSSConcurrentDataException
                  ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
          }
          materialVO.getTheMaterialDT().setVersionCtrlNbr(new Integer(materialVO.getTheMaterialDT().getVersionCtrlNbr().intValue()+1));
          oldMaterialVO = this.theMaterialVO;
          this.theMaterialVO = materialVO;
      }
      catch(Exception e)
      {
          logger.debug(e.toString()+" : setMaterial dataconcurrency catch: " + e.getClass());
          logger.debug("Exception string is: " + e.toString());
          if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
          {
             // cntx.setRollbackOnly();
              logger.fatal("Throwing NEDSSConcurrentDataException"+e.getMessage(),e);
              throw new NEDSSConcurrentDataException( e.getMessage(),e);
          }
          else
          {
              logger.fatal("Throwing generic Exception"+e.getMessage(),e);
              throw new EJBException(e.getMessage(),e);
          }
      }
    }

    /**
     * @roseuid 3BD6BD9B0135
     * @J2EE_METHOD  --  getMaterialVO
     *
     * Gets the value of materialVO
     *
     * @return            Material value object that was set
     *
     * @exception RemoteException If a remote exception happens.
     */
    public MaterialVO getMaterialVO () {
      return theMaterialVO;
    }

    private void insertHistory() throws NEDSSSystemException{
      try {
		if( oldMaterialVO != null )
		  {
		    logger.debug("MaterialEJB in ejbStore(), materialUID in oldMaterialVO : " + oldMaterialVO.getTheMaterialDT().getMaterialUid().longValue());
		    long materialUID = oldMaterialVO.getTheMaterialDT().getMaterialUid().longValue();
		    short versionCtrlNbr = oldMaterialVO.getTheMaterialDT().getVersionCtrlNbr().shortValue();
		    MaterialHistoryManager materialHistoryManager = new MaterialHistoryManager(materialUID, versionCtrlNbr);
		    materialHistoryManager.store(this.oldMaterialVO);
		    this.oldMaterialVO = null;
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("Throwing generic Exception"+e.getMessage(),e);
        throw new EJBException(e.getMessage(),e);
	}
    }

}
