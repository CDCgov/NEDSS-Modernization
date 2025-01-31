/**
* Name:		EJB class for Person Enterprise Bean
* Description:	The bean is an entity bean
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.entity.person.ejb.bean;

import java.util.Collection;
import java.util.Date;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.RemoveException;

import java.sql.SQLException;

import javax.ejb.FinderException;













import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.ejb.dao.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.systemservice.util.*;


/**
 * The PersonEJB is used for accessing, entering, and
 * updating person data.
 */
public class PersonEJB implements EntityBean
{
	private static final long serialVersionUID = 1L;
    /**
     * An instance of the PersonVO object
     */
    private PersonVO pvo;
    /**
     * This represents and old PersonVO object.
     */
    private PersonVO oldPvo;
    /**
     * A long that represent a personUID value.
     */
    private long personUID;
    /**
     * An instance of an EntityContext.
     */
    private EntityContext cntx;
    /**
     * An instance of the PersonRootDAOImpl.
     */
    private PersonRootDAOImpl personRootDAO = null;
    //private PersonHistoryManager personHistoryManager = null;
    /**
     * An instance of the LogUtils class.
     */
    static final LogUtils logger = new LogUtils((PersonEJB.class).getName());


    /**
     * This is the constructor for the PersonEJB
     * class.
     */
    public PersonEJB ()
    {
    }

    /**
     * This method is used to retrieve a person's PersonVO.
     *
     * @return PersonVO
     */
    public PersonVO getPersonVO()
    {
        return pvo;
    }

    /**
     * This method is used to store the data in a PersonVO to the database.
     *
     * @exception NEDSSConcurrentDataException
     * @param pvo PersonVO
     */
    public void setPersonVO(PersonVO pvo) throws NEDSSConcurrentDataException
    {
        try
        {
            logger.debug("Old version control number is: " + this.pvo.getThePersonDT().getVersionCtrlNbr());
            logger.debug("new version control number is: " + pvo.getThePersonDT().getVersionCtrlNbr());
            if (this.pvo.getThePersonDT().getVersionCtrlNbr().intValue() !=
                    pvo.getThePersonDT().getVersionCtrlNbr().intValue() )
            {
               // cntx.setRollbackOnly();
                logger.error("Throwing NEDSSConcurrentDataException");
                throw new NEDSSConcurrentDataException
                    ( "NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
            }
            pvo.getThePersonDT().setVersionCtrlNbr(new Integer(pvo.getThePersonDT().getVersionCtrlNbr().intValue()+1));
            oldPvo = this.pvo;
            this.pvo = pvo;
        }
        catch(Exception e)
        {
            logger.debug(e.toString()+" : setPersonVO dataconcurrency catch: " + e.getClass());
            logger.debug("Exception string is: " + e.toString());
            if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
            {
               // cntx.setRollbackOnly();
                logger.fatal("PersonEJB.setPersonVO: NEDSSConcurrentDataException: " + e.getMessage(), e);
                throw new NEDSSConcurrentDataException(e.getMessage(), e);
            }
            else
            {
                logger.fatal("PersonEJB.setPersonVO: Exception: " + e.getMessage(), e);
                throw new EJBException(e.getMessage(), e);
            }
        }
    }



    /**
     * This method is used to retrieve a person's personDT info.
     *
     * @return PersonDT
     */
    public PersonDT getPersonInfo()
    {
        try {
			return pvo.getThePersonDT();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("PersonEJB.getPersonInfo: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * This method is used to retrieve a person's personNames.
     *
     * @return Collection<Object>
     */
    public Collection<Object> getPersonNames()
    {
        try {
			return pvo.getThePersonNameDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("PersonEJB.getPersonNames: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * This method is used to retrieve a person's personRaces.
     *
     * @return Collection<Object>
     */
    public Collection<Object> getPersonRaces()
    {
        try {
			return pvo.getThePersonRaceDTCollection() ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("PersonEJB.getPersonRaces: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * This method is used to retrieve a person's EthnicGroups.
     *
     * @return Collection<Object>
     */
    public Collection<Object> getPersonEthnicGroups()
    {
        try {
			return pvo.getThePersonEthnicGroupDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("PersonEJB.getPersonEthnicGroups: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * This method is used to retrieve a person's EntityIDs.
     *
     * @return Collection<Object>
     */
    public Collection<Object> getPersonIDs()
    {
        try {
			return pvo.getTheEntityIdDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("PersonEJB.getPersonIDs: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * This method is used to retrieve a person's locators.  ie Tele, postal.
     *
     * @return Collection<Object>
     */
    public Collection<Object> getLocators()
    {
        try {
			return pvo.getTheEntityLocatorParticipationDTCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("PersonEJB.getLocators: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * This method is used get a context for the PersonEJB.
     *
     * @return EntityContext
     */
    public EntityContext getEntityContext()
    {
        try {
			return cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("PersonEJB.getEntityContext: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * Sets the entity context.
     *
     * @param cntx EntityContext
     */
    public void setEntityContext(EntityContext cntx)
    {
        try {
			this.cntx = cntx;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("PersonEJB.setEntityContext: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * Unsets the entity context.
     */
    public void unsetEntityContext()
    {
        try {
			cntx = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("PersonEJB.unsetEntityContext: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * This method is used to create a new person record and return their personUID.
     *
     * @exception RemoteException
     * @exception CreateException
     * @exception DuplicateKeyException
     * @exception EJBException
     * @exception NEDSSSystemException
     * @param pvo PersonVO
     * @return Long
     */
    public Long ejbCreate(PersonVO pvo)
          throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
        logger.info("Starts ejbCreate()...");

        this.pvo = pvo;

        try
        {
            if(personRootDAO == null)
            {
                personRootDAO = (PersonRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ROOT_DAO_CLASS);
            }

            this.pvo.getThePersonDT().setVersionCtrlNbr(new Integer(1));
            //if(this.pvo.getThePersonDT().getSharedInd() == null)
           // this.pvo.getThePersonDT().setSharedInd("T");

            personUID = personRootDAO.create(this.pvo);
            this.pvo.getThePersonDT().setPersonUid(new Long(personUID));
        }
        catch(NEDSSSystemException ndsex)
        {
             logger.fatal("PersonEJB.ejbCreate: NEDSSSystemException: Can not create a person, " + ndsex.getMessage(), ndsex);
             throw new NEDSSSystemException(ndsex.getMessage(), ndsex);
        }
        catch(Exception ex)
        {
         logger.fatal("PersonEJB.ejbCreate: Exception: Can not create a person, " + ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(), ex);
        }
        logger.info("Done ejbCreate() - return: " + personUID);
        return (new Long(personUID));
    }

    /**
     * This method is for bringing a PersonEJB out of the passivated state.
     *
     * @exception EJBException
     */
    public void ejbActivate() throws EJBException
    {
    }


    /**
     * This method is used to set the personRootDAO object to null and make the PersonEJB object
     * available for use by someone else.  This releases the EJB.
     *
     * @exception EJBException
     */
    public void ejbPassivate() throws EJBException
    {
        try {
			this.personRootDAO = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("PersonEJB.ejbPassivate: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);

		}
    }

    /**
     * This method is for deleting a Person and updating the history.
     *
     * @exception RemoveException
     * @exception EJBException
     */
    public void ejbRemove() throws RemoveException, EJBException
    {
        logger.info("Starts ejbRemove()...");
        try
        {

            if(personRootDAO == null)
            {
                personRootDAO = (PersonRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ROOT_DAO_CLASS);
            }
            insertHistory();
            personRootDAO.remove(((Long)cntx.getPrimaryKey()).longValue());
            //insertHistory();
        }
        catch(NEDSSSystemException ndsex)
        {
            logger.fatal("PersonEJB.ejbRemove: NEDSSSystemException: Can not remove a person record, " + ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage(),ndsex);
        }
        logger.info("Done ejbRemove() - return: void");
    }

    /**
     * This method is for storing a PersonEJB Object.
     *
     * @exception RemoteException
     */
    public void ejbStore() throws RemoteException
    {
        logger.info("Starts ejbStore()...");

        try
        {
            if(personRootDAO == null)
            {
                personRootDAO = (PersonRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ROOT_DAO_CLASS);
            }
            if(this.pvo != null && this.pvo.isItDirty() && this.pvo.getRole()==null)
            {
              
                    personRootDAO.store(this.pvo);
                    this.pvo.setItDirty(false);
                    this.pvo.setItNew(false);
                    //Waiting on the "go ahead" to uncomment the following code
                    insertHistory();
             
             }

       }
       catch(NEDSSConcurrentDataException ndcex)
       {
               logger.fatal("PersonEJB.ejbStore: NEDSSConcurrentDataException: Concurrent access is not allowed:" + ndcex.getMessage(),ndcex);
               throw new NEDSSSystemException(ndcex.getMessage(), ndcex);

       }
       catch(NEDSSDAOSysException npdsysex)
       {
            logger.fatal("PersonEJB.ejbStore: NEDSSDAOSysException: Person update failed, " + npdsysex.getMessage(),npdsysex);
            throw new NEDSSDAOSysException(npdsysex.getMessage(),npdsysex);

       }
       catch(NEDSSSystemException nsex)
       {
            logger.fatal("PersonEJB.ejbStore: NEDSSSystemException: Person update failed, " + nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.getMessage(), nsex);

       }
       catch(Exception ex)
       {
         logger.fatal("PersonEJB.ejbStore: Exception: " + ex.getMessage(), ex);
         throw new EJBException(ex.getMessage(), ex);
       }
        logger.info("Done ejbStore() - return: void");
    }

    /**
     * This method is used to populate the PersonVO with the person data.
     *
     * @exception EJBException
     */
    public void ejbLoad() throws EJBException
    {
        logger.info("Starts ejbLoad()...");
        try
        {
            if(personRootDAO == null)
            {
                personRootDAO = (PersonRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ROOT_DAO_CLASS);
            }
            this.pvo = (PersonVO)personRootDAO.loadObject(((Long)cntx.getPrimaryKey()).longValue());
            this.pvo.setItDirty(false);
            this.pvo.setItNew(false);
        }
        catch(NEDSSSystemException npdaex)
        {
            logger.fatal("PersonEJB.ejbLoad: Cannot retrieve a person, " + npdaex.getMessage(), npdaex);
            throw new NEDSSSystemException(npdaex.getMessage(), npdaex);
        }
        catch(Exception ex)
        {
        	logger.fatal("PersonEJB.ejbLoad: Cannot retrieve a person, " + ex.getMessage(), ex);
            throw new EJBException(ex.getMessage(), ex);
        }
        logger.info("Done ejbLoad() - return: void");
    }


    /**
     * This method has no implementation.
     *
     * @exception RemoteException
     * @exception CreateException
     * @exception DuplicateKeyException
     * @exception EJBException
     * @exception NEDSSSystemException
     * @param pvo PersonVO
     */
    public void ejbPostCreate(PersonVO pvo)
            throws RemoteException, CreateException,
          DuplicateKeyException, EJBException,
          NEDSSSystemException
    {
    }

    public Collection<Object> ejbFindByGroup(Long groupNbr) throws RemoteException, FinderException, EJBException, NEDSSSystemException {
       Collection<Object> personUidColl = null;
       try {
         if(groupNbr != null) {
                  if(personRootDAO == null)
                          personRootDAO = (PersonRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ROOT_DAO_CLASS);
                  personUidColl = personRootDAO.findByGroup(groupNbr.longValue());
                  if(personUidColl.size() == 0)
                    throw new FinderException();
                  logger.debug("found "+personUidColl.size()+ " person_uid values for groupNbr = "+groupNbr);
          }//end of if
        } catch(NEDSSSystemException nsex)
           {
                   logger.fatal("PersonEJB.ejbFindByGroup: Cannot find a person by primary key, " + nsex.getMessage(), nsex);
                   throw new EJBException(nsex.getMessage(), nsex);
           }
           catch(Exception ex)
           {
             if(ex instanceof FinderException)
               throw (FinderException)ex;
             logger.fatal("PersonEJB.ejbFindByGroup: Cannot find a person by primary key, " + ex.getMessage(), ex);
             throw new EJBException(ex.getMessage(), ex);
           }
           logger.info("Done ejbFindByPersonParentUid() - return collection size: " + personUidColl.size());
       return personUidColl;

    }//end of method

    /**
     * This will return a collection of person_uid values
     * based on the person_parent_uid to the container.
     * @param personParentUid
     * @return Collection<Object>
     * @throws RemoteException
     * @throws FinderException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public Collection<Object> ejbFindByPersonParentUid(Long personParentUid) throws RemoteException, FinderException, EJBException, NEDSSSystemException {

    	logger.info("Starts ejbFindByPersonParentUid()...");
    	Collection<Object> personUidColl = null;
    	try {
    		if(personParentUid != null) {
    			if(personRootDAO == null)
    				personRootDAO = (PersonRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ROOT_DAO_CLASS);
				personUidColl = personRootDAO.findByPersonParentUid(personParentUid.longValue());
				logger.debug("found "+personUidColl.size()+ " person_uid values for personParentUid = "+personParentUid);
                                if(personUidColl == null || personUidColl.size() == 0)
                                  throw new FinderException();
    		}

    	}catch (NEDSSDAOSysException npdsex)
		{
    		logger.fatal("PersonEJB.ejbFindByGroup: NEDSSDAOSysException: Cannot find a person by primary key, " + npdsex.getMessage(), npdsex);
            throw new NEDSSDAOSysException(npdsex.getMessage(), npdsex);
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("PersonEJB.ejbFindByGroup: NEDSSSystemException: Cannot find a person by primary key, " + nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		catch(Exception ex)
		{
                  if(ex instanceof FinderException)
                    throw (FinderException)ex;
                  logger.fatal("PersonEJB.ejbFindByGroup: Exception: Cannot find a person by primary key, " + ex.getMessage(), ex);
                  throw new EJBException(ex.getMessage(), ex);
		}
		logger.info("Done ejbFindByPersonParentUid() - return collection size: " + personUidColl.size());
    	return personUidColl;
    }
//done till here
    /**
     * This method is used to find a person using their primary key.
     *
     * @exception RemoteException
     * @exception FinderException
     * @exception EJBException
     * @exception NEDSSSystemException
     * @param pk Long
     */
    public Long ejbFindByPrimaryKey(Long pk) throws RemoteException,
        FinderException, EJBException, NEDSSSystemException
    {
        logger.info("Starts ejbFindByPrimaryKey()...");
        Long findPK = null;
		Collection<Object> personUidColl = null;
        try
        {
            if(pk != null)
            {
                if(personRootDAO == null)
                {
                    personRootDAO = (PersonRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ROOT_DAO_CLASS);
                }
                findPK = personRootDAO.findByPrimaryKey(pk.longValue());

                logger.debug("Return findpk: " + findPK + " for PersonUID: " + pk.toString());

				//This call below should not have been done. The J2EE container will
				//call ejbLoad(pk) after recieving the pk back from this method, on a
				//totally different PersonEJB object. JLD
                //this.pvo = (PersonVO)personRootDAO.loadObject(pk.longValue());

            }
        }
        catch (NEDSSDAOSysException npdsex)
        {
        	logger.fatal("PersonEJB.ejbFindByGroup: NEDSSDAOSysException: Cannot find a person by primary key, " + npdsex.getMessage(), npdsex);
            throw new NEDSSDAOSysException(npdsex.getMessage(), npdsex);
        }
        catch(NEDSSSystemException nsex)
        {
        	logger.fatal("PersonEJB.ejbFindByGroup: NEDSSSystemException: Cannot find a person by primary key, " + nsex.getMessage(), nsex);
            throw new NEDSSSystemException(nsex.getMessage(), nsex);
        }
        catch(Exception ex)
        {
        	logger.fatal("PersonEJB.ejbFindByGroup: Exception: Cannot find a person by primary key, " + ex.getMessage(), ex);
            throw new EJBException(ex.getMessage(), ex);
        }
        logger.info("Done ejbFindByPrimaryKey() - return: " + findPK);
        return findPK;
    }

    /**
     * Makes an entry to the person history tables.
     *
     * @exception NEDSSSystemException
     */
    private void insertHistory()throws NEDSSSystemException {
      try {
		if( oldPvo != null )
		  {
		    logger.debug("PersonEJB in ejbStore(), personUID in pvo : " + pvo.getThePersonDT().getPersonUid().longValue());
		    long personHistUID = oldPvo.getThePersonDT().getPersonUid().longValue();
		    short versionCtrlNbr = oldPvo.getThePersonDT().getVersionCtrlNbr().shortValue();
		    PersonHistoryManager personHistoryManager = new PersonHistoryManager(personHistUID, versionCtrlNbr);
		    personHistoryManager.store(this.oldPvo);
		    this.oldPvo = null;
		  } else {
		    logger.info("Attempt to insert Person history failed because oldPvo == null in PersonEJB");
		  }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("PersonEJB.insertHistory: " + e.getMessage(), e);
		throw new EJBException(e.getMessage(), e);
	}
    }
}// end of PersonEJB bean class
