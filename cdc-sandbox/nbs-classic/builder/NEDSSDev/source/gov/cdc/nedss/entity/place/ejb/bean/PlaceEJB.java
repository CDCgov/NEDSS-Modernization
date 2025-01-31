//
// -- Java Code Generation Process --

package gov.cdc.nedss.entity.place.ejb.bean;

// Import Statements
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import java.sql.Connection;

import javax.sql.DataSource;
import javax.ejb.EJBException;

import java.rmi.RemoteException;

import javax.ejb.*;

import gov.cdc.nedss.entity.place.vo.*;
import gov.cdc.nedss.entity.place.dt.*;
import gov.cdc.nedss.entity.place.ejb.dao.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.util.*;

public class PlaceEJB implements javax.ejb.EntityBean
{
    /*
       Attributes declaration
    */
    public javax.ejb.EntityContext EJB_Context;
    public java.sql.Connection EJB_Connection = null;
    public javax.sql.DataSource EJB_Datasource = null;
    public PlaceVO thePlaceVO;
    public PlaceVO oldPlaceVO;

    //For logging
    static final LogUtils logger = new LogUtils(PlaceEJB.class.getName());


    private long placeUID;


    private PlaceRootDAOImpl placeRootDAO = null;

    /**
     * default constructor
     * @roseuid 3BD0261B021D
     * @J2EE_METHOD  --  PlaceEJB
     */
    public PlaceEJB()
    {
    }

    /**
     * Sets Place's attributes to the values passed in as attributes
     * of the value object.
	* @param PlaceVO -- value object containing the information related to place
	* @throws RemoteException
	* @throws NEDSSConcurrentDataException
	*/

    public void setPlaceVO(PlaceVO pvo)
          throws RemoteException, NEDSSConcurrentDataException
    {
        try
        {
            if (this.thePlaceVO.getThePlaceDT().getVersionCtrlNbr().intValue() !=
                    pvo.getThePlaceDT().getVersionCtrlNbr().intValue() )
            {
                logger.error("Throwing NEDSSConcurrentDataException");
                throw new NEDSSConcurrentDataException
                    ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
            }
            pvo.getThePlaceDT().setVersionCtrlNbr(new Integer(pvo.getThePlaceDT().getVersionCtrlNbr().intValue()+1));
            oldPlaceVO = this.thePlaceVO;
            this.thePlaceVO = pvo;
        }
        catch(Exception e)
        {
            logger.debug(e.toString()+" : setPlaceVO dataconcurrency catch: " + e.getClass());
            logger.debug("Exception string is: " + e.toString());
            if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
            {
                logger.fatal("Throwing NEDSSConcurrentDataException"+e.getMessage(), e);
                throw new NEDSSConcurrentDataException( e.getMessage(),e);
            }
            else
            {
                logger.fatal("Throwing generic Exception"+e.getMessage(), e);
                throw new EJBException(e.getMessage(),e);
            }
        }

    }
    /**
     * get all the information from place table
	* @return PlaceDT -- PlaceDT represents place table in database
	* @throws RemoteException
     */
    public PlaceDT getPlaceInfo()
      throws
        RemoteException
    {
        return thePlaceVO.getThePlaceDT();
    }
    /**
     * A container invokes this method when the instance is taken out of the pool of available
     * instances to become associated with a specific EJB object. This method transitions
     * the instance to the ready state. This method executes in an unspecified transaction
     * context.
     * @roseuid 3BD0261B02EF
     * @J2EE_METHOD  --  ejbActivate
     *
     */
    public void ejbActivate    ()
    {

    }

    /**
     * A container invokes this method on an instance before the instance becomes disassociated
     * with a specific EJB object. After this method completes, the container will place
     * the instance into the pool of available instances. This method executes in an unspecified
     * transaction context.
     * @roseuid 3BD0261B0303
     * @J2EE_METHOD  --  ejbPassivate
     */
    public void ejbPassivate    ()
    {

    }

    /**
     * A container invokes this method to instruct the instance to synchronize its state
     * by loading it from the underlying database. This method always executes in the transaction
     * context determined by the value of the transaction attribute in the deployment descriptor.
     * @roseuid 3BD0261B030D
     * @J2EE_METHOD  --  ejbLoad
     */
    public void ejbLoad    ()
    {
      logger.debug("Place EJB load is called"); // debuggin
      try {
        if(placeRootDAO==null)
          placeRootDAO = (PlaceRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PLACE_ROOT_DAO_CLASS);
        this.thePlaceVO =  (PlaceVO)placeRootDAO.loadObject(((Long)EJB_Context.getPrimaryKey()).longValue());
        this.thePlaceVO.setItDirty(false);
        this.thePlaceVO.setItNew(false);

      } catch(NEDSSSystemException ndsex) {
          logger.fatal("Fails ejbLoad()"+ndsex.getMessage(), ndsex);
          throw new EJBException(ndsex.getMessage(),ndsex);

      }
    }
     /**
	 * A container invokes this method to instruct the instance
	 * to synchronize its state by storing it to the underlying database
	 * @throws EJBException
	 */
     public void ejbStore ()
      throws EJBException{

      logger.debug("PlaceEJB store is called"); // debugging
      try {
        if(placeRootDAO==null) {
          logger.debug("PlaceEJB.ejbStore() - placeRootDAO==null");
          placeRootDAO = (PlaceRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PLACE_ROOT_DAO_CLASS);
        }
        logger.debug("PlaceEJB.ejbStore() placeRootDAO="+ placeRootDAO.toString());
        logger.debug("PlaceEJB.ejbStore() thePlaceVO="+ thePlaceVO.toString());
        if(this.thePlaceVO != null && this.thePlaceVO.isItDirty())
        {
          try
          {
            placeRootDAO.store(this.thePlaceVO);

            this.thePlaceVO.setItDirty(false);
            this.thePlaceVO.setItNew(false);
            //Waiting on the "go ahead" to uncomment the following code
            insertHistory();
            logger.debug("PlaceEJB.ejbStore().thePlaceVO = " + thePlaceVO.toString());
          }
          catch (NEDSSConcurrentDataException ndcex)
          {
          logger.fatal("NEDSSConcurrentDataExceptionn in placeEJB"+ndcex.getMessage(),ndcex);
          throw new NEDSSSystemException(ndcex.getMessage(),ndcex);
          }
        }

      } catch(NEDSSSystemException ndsex) {
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
            if(placeRootDAO == null) {
              placeRootDAO = (PlaceRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PLACE_ROOT_DAO_CLASS);
            }
            //insertHistory();
            placeRootDAO.remove(((Long)EJB_Context.getPrimaryKey()).longValue());

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
     * @param placeUid Primary Key to search for Place
     *
     * @exception EJBException general EJB Exception
     * @exception FinderException EJB Finder Exception
     * @exception NEDSSSystemException EJB NEDSS Applictation Exception
     * @exception RemoteException EJB Remote Exception
     */
    public Long ejbFindByPrimaryKey (Long pk)
      throws
	RemoteException,
	FinderException,
	EJBException,
	NEDSSSystemException    {
      logger.debug("Place EjbFindByPrimaryKey is called - pk = " + pk); // debugging
        Long findPK = null;
        try {
            if(pk != null) {
                logger.debug("not null - pk = " + pk);
                if(placeRootDAO == null) {
                    logger.debug("JNDINames.JNDINames.PLACE_ROOT_DAO_CLASS = " + JNDINames.PLACE_ROOT_DAO_CLASS );
                    placeRootDAO = (PlaceRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PLACE_ROOT_DAO_CLASS );
                }
                findPK = placeRootDAO.findByPrimaryKey(pk.longValue());
                logger.debug("return findpk: " + findPK);
                //see notes in clearcase by JLD
                //this.thePlaceVO = (PlaceVO)placeRootDAO.loadObject(pk.longValue());
                //thePlaceVO.getThePlaceDT().setPlaceUid( pk );
            }

        } catch (NEDSSSystemException napex) {
            logger.fatal("NEDSSSystemException in find by primary key"+napex.getMessage(), napex);
            napex.printStackTrace();
            throw new EJBException(napex.getMessage(), napex);
        } catch (EJBException ejbex) {
            logger.fatal("EJBException in find by primary key"+ejbex.getMessage(), ejbex);
            ejbex.printStackTrace();
            throw new EJBException(ejbex.getMessage(), ejbex);
        }
        return findPK;
    }
   /**
    *  This method is the implementation of create method
    *  from EJBHome interface in bean class
    *  @param PlaceVO -- Value object for place
    *  @return placeUID -- the actual UID form place table
    *  @throws RemoteException
    *  @throws CreateException
    *  @throws DuplicateKeyException
    *  @throws EJBException
    *  @throws NEDSSSystemException
    */
    public Long ejbCreate(PlaceVO placeVO)
		throws
		RemoteException,
		CreateException,
		DuplicateKeyException,
		EJBException,
		NEDSSSystemException
    {
        Long placeUID = null;

        logger.debug("PlaceEJB create is called");
        this.thePlaceVO = placeVO;

        try {
          if(placeRootDAO==null)
            placeRootDAO = (PlaceRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PLACE_ROOT_DAO_CLASS);
          placeVO.getThePlaceDT().setVersionCtrlNbr(new Integer(1));
          placeUID = new Long(placeRootDAO.create(this.thePlaceVO));

        } catch(NEDSSSystemException ndsex) {
            logger.fatal("Fails ejbCreate()"+ndsex.getMessage(), ndsex);
            throw new EJBException(ndsex.getMessage(),ndsex);

        }
        return placeUID;
    }

    /**
     * This method is called by container after ejbCreate method
	* @param PlaceVO -- Place Value Object
	* @throws CreateException
     */
    public void ejbPostCreate  (PlaceVO placeVO) throws javax.ejb.CreateException
    {

    }

    /**
     * get the current context of PlaceVO
     * @roseuid 3BD048410312
     * @J2EE_METHOD  --  getPlaceVO
	* @return PlaceVO
	* @throws RemoteException
     */
    public PlaceVO getPlaceVO()
      throws
        RemoteException
    {
        return thePlaceVO;
    }

    /**
     * set the container-provided runtime context of an entity enterprise Bean instance
	* @param EntityContext
     */
    public void setEntityContext (EntityContext ctx) {
        EJB_Context = ctx;
    }
    /**
     * reset the container-provided runtime context of an entity enterprise Bean instance
     */
    public void unsetEntityContext () {
        EJB_Context = null;
    }
    /**
     * Store the oldPlaceVO in the database with version control number
	* @throws NEDSSSystemException
     */
    private void insertHistory() throws NEDSSSystemException {
      try {
		if( oldPlaceVO != null )
		        {
		          logger.debug("PlaceEJB in ejbStore(), placeUID in oldPlaceVO : " + oldPlaceVO.getThePlaceDT().getPlaceUid().longValue());
		          long placeUID = oldPlaceVO.getThePlaceDT().getPlaceUid().longValue();
		          short versionCtrlNbr = oldPlaceVO.getThePlaceDT().getVersionCtrlNbr().shortValue();
		          PlaceHistoryManager placeHistoryManager = new PlaceHistoryManager(placeUID, versionCtrlNbr);
		          placeHistoryManager.store(this.oldPlaceVO);
		          this.oldPlaceVO = null;
		        }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
    }
}
