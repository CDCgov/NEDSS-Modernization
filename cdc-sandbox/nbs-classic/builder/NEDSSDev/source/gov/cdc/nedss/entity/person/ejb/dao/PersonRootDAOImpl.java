/**
* Name:		PersonRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               Person value object in the Person entity bean.
*               This class encapsulates all the JDBC calls made by the PersonEJB
*               for a Person object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of PersonEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.entity.person.ejb.dao;


import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dao.RoleDAOImpl;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entityid.dao.EntityIdDAOImpl;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.EntityLocatorParticipationDAOImpl;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.EJBException;

import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;




public class PersonRootDAOImpl extends BMPBase
{
    private PersonVO pvo = null;
    private long personUID;
    private  PersonDAOImpl personDAO = null;
    private  PersonNameDAOImpl personNameDAO = null;
    private  PersonRaceDAOImpl personRaceDAO = null;
    private  PersonEthnicGroupDAOImpl personEthnicGroupDAO = null;
    private  EntityIdDAOImpl entityIdDAO = null;
    private  EntityLocatorParticipationDAOImpl entityLocatorParticipationDAO = null;
    //role and participation DAOs for place
    private  RoleDAOImpl roleDAOImpl = null;
    private  ParticipationDAOImpl personParticipationDAOImpl = null;

    //For logging
    static final LogUtils logger = new LogUtils(PersonRootDAOImpl.class.getName());

    public PersonRootDAOImpl()
    {
    }

    /**
      * This method creates a new person record and returns the personUID for this person.
      * @J2EE_METHOD  --  create
      * @param obj       the Object
      * @throws NEDSSSystemException
      **/
    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        logger.info("Starts create() for a new person vo...");
	
	        this.pvo = (PersonVO)obj;
	
	        /**
	        *  Inserts PersonDT object
	        */
	
	        if (this.pvo != null)
	        personUID = insertPerson(this.pvo);
	        logger.debug("Person UID = " + personUID);
	        (this.pvo.getThePersonDT()).setPersonUid(new Long(personUID));
	
	
	        /**
	        * Inserts PersonNameDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getThePersonNameDTCollection() != null)
	        insertPersonNames(this.pvo);
	
	        /**
	        * Inserts PersonRaceDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getThePersonRaceDTCollection() != null)
	        insertPersonRaces(this.pvo);
	
	        /**
	        * Inserts PersonEthnicGroupDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getThePersonEthnicGroupDTCollection() != null)
	        insertPersonEthnicGroups(this.pvo);
	
	        /**
	        * Inserts EntityIdDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheEntityIdDTCollection() != null)
	        insertEntityIDs(this.pvo);
	
	        /**
	        * Inserts EntityLocatorParticipationDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheEntityLocatorParticipationDTCollection() != null)
	        insertEntityLocatorParticipations(this.pvo);
	
	        /**
	        * Inserts RoleDT collection
	        */
	
	        if (this.pvo != null && this.pvo.getTheRoleDTCollection() != null)
	          insertRoles(this.pvo);
	
	
	        this.pvo.setItNew(false);
	        this.pvo.setItDirty(false);
	        logger.info("Done create() for a new person vo - return personUID = " + personUID);
	        return ((((PersonVO)obj).getThePersonDT().getPersonUid()).longValue());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method is used to update a person record.
      * @J2EE_METHOD  --  store
      * @param obj       the Object
      * @throws NEDSSSystemException
      * @throws NEDSSConcurrentDataException
      **/
    public void store(Object obj) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
    	try{
	        logger.info("Starts store() for a person vo...");
	
	        this.pvo = (PersonVO)obj;
	
	        /**
	        *  Updates PersonDT object
	        */
	        if(this.pvo.getThePersonDT() != null && this.pvo.getThePersonDT().isItNew())
	        {
	            insertPerson(this.pvo);
	            this.pvo.getThePersonDT().setItNew(false);
	            this.pvo.getThePersonDT().setItDirty(false);
	        }
	        else if(this.pvo.getThePersonDT() != null && this.pvo.getThePersonDT().isItDirty())
	        {
	            updatePerson(this.pvo);
	            this.pvo.getThePersonDT().setItDirty(false);
	            this.pvo.getThePersonDT().setItNew(false);
	        }
	
	        /**
	        * Updates person names collection
	        */
	
	        if (this.pvo.getThePersonNameDTCollection() != null)
	        {
	            updatePersonNames(this.pvo);
	
	        }
	
	
	        /**
	        * Updates person races collection
	        */
	
	        if (this.pvo.getThePersonRaceDTCollection() != null)
	        {
	            updatePersonRaces(this.pvo);
	
	        }
	
	        /**
	        * Updates person ethnic groups collection
	        */
	        if (this.pvo.getThePersonEthnicGroupDTCollection() != null)
	        {
	            updatePersonEthnicGroups(this.pvo);
	        }
	
	        /**
	         * Updates entity ids collection
	         */
	        if(this.pvo.getTheEntityIdDTCollection() != null)
	        {
	            updateEntityIDs(this.pvo);
	        }
	
	        /**
	        * Updates entity locator participations collection
	        */
	        if (this.pvo.getTheEntityLocatorParticipationDTCollection() != null)
	        {
	            updateEntityLocatorParticipations(this.pvo);
	
	        }
	        /**
	         * Update roles
	         */
	         if (this.pvo.getTheRoleDTCollection() != null)
	        {
	            insertRoles(this.pvo);
	
	        }
	
		/**
	         * Update participations
	         */
	         if (this.pvo.getTheParticipationDTCollection() != null)
	        {
	            insertParticipations(this.pvo);
	
	        }
	        logger.info("Done store() for a person vo- return: void");
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method is used to delete a person record.
      * @J2EE_METHOD  --  remove
      * @param personUID       the long
      * @throws NEDSSSystemException
      **/
    public void remove(long personUID) throws NEDSSSystemException
    {
    	try{
	        /**
	        * Removes EntityLocatorParticipationDT collection
	        */
	
	        removeEntityLocatorParticipations(personUID);
	
	        /**
	        * Removes PersonEthnicGroupDT collection
	        */
	
	        removePersonEthnicGroups(personUID);
	
	        /**
	        * Removes EntityIdDT Collection<Object>
	        */
	
	        removeEntityIDs(personUID);
	
	        /**
	        * Removes PersonRaceDT Collection<Object>
	        */
	
	        removePersonRaces(personUID);
	
	        /**
	        * Removes PersonNameDT Collection<Object>
	        */
	
	        removePersonNames(personUID);
	
	        /**
	        *  Removes PersonDT
	        */
	
	        removePerson(personUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method loads a PersonDT object for a given personUID.
      * @J2EE_METHOD  --  loadObject
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    public Object loadObject(long personUID) throws NEDSSDAOSysException,
         NEDSSSystemException
    {
    	try{
	        logger.info("Starts loadObject() for a person vo...");
	
	        this.pvo = new PersonVO();
	
	        /**
	        *  Selects PersonDT object
	        */
	
	        PersonDT pDT = selectPerson(personUID);
	        this.pvo.setThePersonDT(pDT);
	
	        /**
	        * Selects PersonNameDT Collection<Object>
	        */
	
	        Collection<Object> pnColl = selectPersonNames(personUID);
	        this.pvo.setThePersonNameDTCollection(pnColl);
	
	        /**
	        * Selects PersonRaceDT Collection<Object>
	        */
	
	        Collection<Object> prColl = selectPersonRaces(personUID);
	        this.pvo.setThePersonRaceDTCollection(prColl);
	
	        /**
	        * Selects PersonEthnicGroupDT collection
	        */
	
	        Collection<Object> pegColl = selectPersonEthnicGroups(personUID);
	        this.pvo.setThePersonEthnicGroupDTCollection(pegColl);
	
	        /**
	        * Selects EntityIdDT collection
	        */
	
	        Collection<Object> idColl = selectEntityIDs(personUID);
	        this.pvo.setTheEntityIdDTCollection(idColl);
	
	        /**
	        * Selects EntityLocatorParticipationDT collection
	        */
	
	        Collection<Object> elpColl = selectEntityLocatorParticipations(personUID);
	        this.pvo.setTheEntityLocatorParticipationDTCollection(elpColl);
	
	        //Selects RoleDTcollection
	        Collection<Object> roleColl = selectRoleDTCollection(personUID);
	        this.pvo.setTheRoleDTCollection(roleColl);
	
	        //SelectsParticipationDTCollection
	        //Collection<Object> parColl = selectParticipationDTCollection(personUID);
	        //this.pvo.setTheParticipationDTCollection(parColl);
	
	        this.pvo.setItNew(false);
	        this.pvo.setItDirty(false);
	        logger.info("Done loadObject() for a person vo - return: " + pvo);
	        return this.pvo;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }
    /**
    * Will return a collection of person uid associated with the groupNbr
    * @param groupNbr
    * @return
    * @throws NEDSSDAOSysException
    * @throws NEDSSSystemException
    */
   public Collection<Object> findByGroup(long groupNbr) throws NEDSSDAOSysException,NEDSSSystemException{
		 try{
		     if(personDAO == null)
		       personDAO = (PersonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_DAO_CLASS);
		     return personDAO.findByGroup(groupNbr);
		 }catch(Exception ex){
	 		logger.fatal("Exception  = "+ex.getMessage(), ex);
	 		throw new NEDSSSystemException(ex.toString());
	 	 }
   }

	public Collection<Object> findByPersonParentUid(long personParentUid)throws NEDSSDAOSysException,
	NEDSSSystemException {
		try{
			if(personDAO == null)
				personDAO = (PersonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_DAO_CLASS);
	
			return personDAO.findByPersonParentUid(personParentUid);
		}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
	}

    public HashMap<Object,Object> findAllDistinctGroups() throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        if(personDAO == null) personDAO = (PersonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_DAO_CLASS);
	
	        return personDAO.findAllDistinctGroups();
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method is used to determine is a person record exists for a given personUID.
      * @J2EE_METHOD  --  findByPrimaryKey
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    public Long findByPrimaryKey(long personUID) throws NEDSSDAOSysException,
		NEDSSSystemException
    {
    	try{
	        logger.info("Starts findByPrimaryKey() for a person...");
	        /**
	         * Finds person object
	         */
	        Long personPK = findPerson(personUID);
	
	        logger.info("Done findByPrimaryKey() - return personPK = " + personPK);
	        return personPK;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method is used to insert the personDT onto the PersonVO.
      * @J2EE_METHOD  --  insertPerson
      * @param pvo       the PersonVO
      * @throws EJBException
      * @throws NEDSSSystemException
      **/
    private long insertPerson(PersonVO pvo) throws EJBException, NEDSSSystemException
    {
        try
        {
            if(personDAO == null)
            {
                personDAO = (PersonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_DAO_CLASS);
            }
            logger.debug("Person DT = " + pvo.getThePersonDT());
            personUID = personDAO.create(pvo.getThePersonDT());
            logger.debug("Person root uid = " + personUID);
            pvo.getThePersonDT().setPersonUid(new Long(personUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while adding a person", ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while adding a person", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return personUID;
    }

    /**
      * This method is used to insert the PersonNameDT objects onto the PersonVO.
      * @J2EE_METHOD  --  insertPersonNames
      * @param pvo       the PersonVO
      * @throws EJBException
      * @throws NEDSSSystemException
      **/
    private void insertPersonNames(PersonVO pvo) throws EJBException, NEDSSSystemException
    {
        try
        {
            if(personNameDAO == null)
            {
                personNameDAO = (PersonNameDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_NAME_DAO_CLASS);
            }
            personUID = personNameDAO.create((pvo.getThePersonDT().getPersonUid()).longValue(), pvo.getThePersonNameDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while adding person names", ndsex);
            throw new EJBException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while adding person names", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to insert the PersonRaceDT objects onto the PersonVO.
      * @J2EE_METHOD  --  insertPersonRaces
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void insertPersonRaces(PersonVO pvo)
            throws NEDSSSystemException
    {
        try
        {
            if(personRaceDAO == null)
            {
                personRaceDAO = (PersonRaceDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_RACE_DAO_CLASS);
            }
            
            Collection<Object> personRaceColl = pvo.getThePersonRaceDTCollection();
			boolean isMPR = false;
			if (pvo.getThePersonDT().getPersonUid().longValue() == pvo
					.getThePersonDT().getPersonParentUid().longValue())
				isMPR = true;
			filterNullFlavorRaces(personRaceColl, isMPR);
            personUID = personRaceDAO.create((pvo.getThePersonDT().getPersonUid()).longValue(), personRaceColl);
        }
        catch(NEDSSDAOSysException npdsex)
        {
            logger.fatal("Error while adding person races", npdsex);
            throw new NEDSSDAOSysException(npdsex.toString());
        }

    }

    /**
      * This method is used to insert the Person Ethnic Group info onto the PersonVO.
      * @J2EE_METHOD  --  insertPersonEthnicGroups
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void insertPersonEthnicGroups(PersonVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(personEthnicGroupDAO == null)
            {
                personEthnicGroupDAO = (PersonEthnicGroupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ETHNIC_GROUP_DAO_CLASS);
            }
            personUID = personEthnicGroupDAO.create((pvo.getThePersonDT().getPersonUid()).longValue(), pvo.getThePersonEthnicGroupDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while adding person ethnic groups", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while adding person ethnic groups", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to insert the entityID on the PersonVO.
      * @J2EE_METHOD  --  insertEntityIDs
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void insertEntityIDs(PersonVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            personUID = entityIdDAO.create((pvo.getThePersonDT().getPersonUid()).longValue(), pvo.getTheEntityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while adding person entity ids", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while adding person entity ids", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to insert the entity locator participations onto the PersonVO.
      * @J2EE_METHOD  --  insertEntityLocatorParticipations
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void insertEntityLocatorParticipations(PersonVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            long success = entityLocatorParticipationDAO.create((pvo.getThePersonDT().getPersonUid()).longValue(), pvo.getTheEntityLocatorParticipationDTCollection());
            //pvo.getTheEntityLocatorParticipationDTCollection().setPersonUid(new Long(personUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while adding person entity locator participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while adding person entity locator participations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to insert the RoleDT objects onto the PersonVO.
      * @J2EE_METHOD  --  insertRoles
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void insertRoles(PersonVO pvo) throws NEDSSSystemException
    {
        logger.debug("*********************** - inside the insertRoles method");
        try
        {
            if(roleDAOImpl == null)
            {
                roleDAOImpl = (RoleDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ROLE_DAO_CLASS);

           }
          Long personUID = pvo.getThePersonDT().getPersonUid();
          logger.debug ("remove all old roles");
         // roleDAOImpl.remove(personUID.longValue());// remove all role for this person before adding new one.
           if(pvo.getTheRoleDTCollection()!=null){
                    Iterator<Object> itrRoles = pvo.getTheRoleDTCollection().iterator();
                    while(itrRoles.hasNext()){
                      RoleDT roleDt = (RoleDT) itrRoles.next();
                      if (roleDt != null){
                        roleDt.setSubjectEntityUid(personUID);
                        roleDAOImpl.store(roleDt);
                      }
                    }

                }
           // }
            //personUID = roleDAOImpl.create((pvo.getThePersonDT().getPersonUid()).longValue(), pvo.getTheRoleDTCollection());
            //pvo.getTheEntityLocatorParticipationDTCollection().setPersonUid(new Long(personUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while adding person role ", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while adding person role ", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        catch(Exception e){
        	logger.fatal("Exception = "+e.getMessage(), e);
            throw new NEDSSSystemException(e.toString());
        }
    }


    /**
      * This method is used to insert the participations onto the PersonVO.
      * @J2EE_METHOD  --  insertParticipations
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void insertParticipations(PersonVO pvo) throws NEDSSSystemException
    {
        logger.debug("*********************** - inside the insertParticipations method");
        try
        {
            if(personParticipationDAOImpl == null)
            {
                personParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_PARTICIPATION_DAO_CLASS);

           }
          Long personUID = pvo.getThePersonDT().getPersonUid();
          logger.debug ("remove all old participations");
         // participationDAOImpl.remove(personUID.longValue());// remove all participations for this person before adding new one.
           if(pvo.getTheParticipationDTCollection()!=null){
                    Iterator<Object> itrParticipations = pvo.getTheParticipationDTCollection().iterator();
                    while(itrParticipations.hasNext()){
                      ParticipationDT participationDt = (ParticipationDT) itrParticipations.next();
                      if (participationDt != null){
                        participationDt.setSubjectEntityUid(personUID);
                        personParticipationDAOImpl.store(participationDt);
                      }
                    }

                }
           // }
            //personUID = participationDAOImpl.create((pvo.getThePersonDT().getPersonUid()).longValue(), pvo.getTheParticipationDTCollection());
            //pvo.getTheEntityLocatorParticipationDTCollection().setPersonUid(new Long(personUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while adding person participation ", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while adding person participation ", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        catch(Exception e){
        	logger.fatal("Exception = "+e.getMessage(), e);
            throw new NEDSSSystemException(e.toString());
        }
    }


    /**
      * This method is used to retrieve a person's PesonDT object.
      * @J2EE_METHOD  --  selectPerson
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private PersonDT selectPerson(long personUID) throws NEDSSSystemException
    {
        try
        {
            if(personDAO == null)
            {
                personDAO = (PersonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_DAO_CLASS);
            }
            return ((PersonDT)personDAO.loadObject(personUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while retrieving person object", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while retrieving person object", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to retrieve a person's names.
      * @J2EE_METHOD  --  selectPersonNames
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private Collection<Object> selectPersonNames(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(personNameDAO == null)
            {
                personNameDAO = (PersonNameDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_NAME_DAO_CLASS);
            }
            return (personNameDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while retrieving person names", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while retrieving person names", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to retrieve a person's races.
      * @J2EE_METHOD  --  selectPersonRaces
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private Collection<Object> selectPersonRaces(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(personRaceDAO == null)
            {
                personRaceDAO = (PersonRaceDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_RACE_DAO_CLASS);
            }
            return (personRaceDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while retrieving person races", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while retrieving person races", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to retrieve a person's Ethnic Groups.
      * @J2EE_METHOD  --  selectPersonEthnicGroups
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private Collection<Object> selectPersonEthnicGroups(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(personEthnicGroupDAO == null)
            {
                personEthnicGroupDAO = (PersonEthnicGroupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ETHNIC_GROUP_DAO_CLASS);
            }
            return (personEthnicGroupDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while retrieving person ethnic groups", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while retrieving person ethnic groups", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to retrieve a person's entityIDs.
      * @J2EE_METHOD  --  selectEntityIDs
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private Collection<Object> selectEntityIDs(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            return (entityIdDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while retrieving person entity ids", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while retrieving person entity ids", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to retrieve a person's Entity Locator Participations.
      * @J2EE_METHOD  --  selectEntityLocatorParticipations
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private Collection<Object> selectEntityLocatorParticipations(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            return (entityLocatorParticipationDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while retrieving person entity locator participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while retrieving person entity locator participations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to remove Person from the person table.
      * @J2EE_METHOD  --  removePerson
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private void removePerson(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(personDAO == null)
            {
                personDAO = (PersonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_DAO_CLASS);
            }
            personDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while removing person object", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while removing person object", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to remove a person name from a person.
      * @J2EE_METHOD  --  removePersonNames
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private void removePersonNames(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(personNameDAO == null)
            {
                personNameDAO = (PersonNameDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_NAME_DAO_CLASS);
            }
            personNameDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while removing person names", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while removing person names", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to remove a person race from a person.
      * @J2EE_METHOD  --  removePersonRaces
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private void removePersonRaces(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(personRaceDAO == null)
            {
                personRaceDAO = (PersonRaceDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_RACE_DAO_CLASS);
            }
            personRaceDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while removing person races", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while removing person races", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to remove a person's specific Ethnic Group.
      * @J2EE_METHOD  --  removePersonEthnicGroups
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private void removePersonEthnicGroups(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(personEthnicGroupDAO == null)
            {
                personEthnicGroupDAO = (PersonEthnicGroupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ETHNIC_GROUP_DAO_CLASS);
            }
            personEthnicGroupDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while removing person ethnic groups", ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while removing person ethnic groups", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to remove an EntityID.
      * @J2EE_METHOD  --  removeEntityIDs
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private void removeEntityIDs(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            entityIdDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while removing person entity ids", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while removing person entity ids", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to remove an Entity Locator Participation.
      * @J2EE_METHOD  --  removeEntityLocatorParticipations
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
    private void removeEntityLocatorParticipations(long aUID) throws NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            entityLocatorParticipationDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while removing person entity locator participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while removing person entity locator participations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to update a person's PersonDT object.
      * @J2EE_METHOD  --  updatePerson
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void updatePerson(PersonVO pvo) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
        try
        {
            if(personDAO == null)
            {
                personDAO = (PersonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_DAO_CLASS);
            }
            personDAO.store(pvo.getThePersonDT());
        }
        catch(NEDSSConcurrentDataException ncdaex)
        {
            logger.fatal("Fails updatePerson() due to concurrent access! ", ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while updating person object", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while updating person object", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }

    }

    /**
      * This method is used to update a person's names.
      * @J2EE_METHOD  --  updatePersonNames
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void updatePersonNames(PersonVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(personNameDAO == null)
            {
                personNameDAO = (PersonNameDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_NAME_DAO_CLASS);
            }
            personNameDAO.store(pvo.getThePersonNameDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while updating person names", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while updating person names", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to update a person's races.
      * @J2EE_METHOD  --  updatePersonRaces
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void updatePersonRaces(PersonVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(personRaceDAO == null)
            {
                personRaceDAO = (PersonRaceDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_RACE_DAO_CLASS);
            }
            Collection<Object> personRaceColl = pvo.getThePersonRaceDTCollection();
			boolean isMPR = false;
			if (pvo.getThePersonDT().getPersonUid().longValue() == pvo
					.getThePersonDT().getPersonParentUid().longValue())
				isMPR = true;
			filterNullFlavorRaces(personRaceColl, isMPR);
            personRaceDAO.store(personRaceColl);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while updating person races", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while updating person races", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to update a person's Ethnic Groups.
      * @J2EE_METHOD  --  updatePersonEthnicGroups
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void updatePersonEthnicGroups(PersonVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(personEthnicGroupDAO == null)
            {
                personEthnicGroupDAO = (PersonEthnicGroupDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ETHNIC_GROUP_DAO_CLASS);
            }
            personEthnicGroupDAO.store(pvo.getThePersonEthnicGroupDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while updating person ethnic groups", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while updating person ethnic groups", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to update a person's EntityIDs.
      * @J2EE_METHOD  --  updateEntityIDs
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void updateEntityIDs(PersonVO pvo) throws NEDSSSystemException
    {
        try
        {
            if(entityIdDAO == null)
            {
                entityIdDAO = (EntityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_ID_DAO_CLASS);
            }
            entityIdDAO.store(pvo.getTheEntityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while updating person entity ids", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while updating person entity ids", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to update a person's Entity Locator Participations.
      * @J2EE_METHOD  --  updateEntityLocatorParticipations
      * @param pvo       the PersonVO
      * @throws NEDSSSystemException
      **/
    private void updateEntityLocatorParticipations(PersonVO pvo)
              throws NEDSSSystemException
    {
        try
        {
            if(entityLocatorParticipationDAO == null)
            {
                entityLocatorParticipationDAO = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            entityLocatorParticipationDAO.store(pvo.getTheEntityLocatorParticipationDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while updating person entity locator participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while updating person entity locator participations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    /**
      * This method is used to determine if a person exists for a given personUID, and
      * return the uid as a Long if successful.
      * @J2EE_METHOD  --  findPerson
      * @param personUID       the long
      * @throws NEDSSSystemException
      **/
    private Long findPerson(long personUID) throws NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            if(personDAO == null)
            {
                personDAO = (PersonDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_DAO_CLASS);
            }
            findPK = personDAO.findByPrimaryKey(personUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Error while finding person", ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Error while finding person", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }
    //get collection of RoleDT from RoleDAOImpl entered by John Park
 /**
      * This method is used to retrieve a person's collection of roles.
      * @J2EE_METHOD  --  selectRoleDTCollection
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
 private Collection<Object> selectRoleDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(roleDAOImpl == null) {
                roleDAOImpl = (RoleDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ROLE_DAO_CLASS);
            }
            logger.debug("aUID in selectRoleDTCollection  = " + aUID);
            return (roleDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Error while retrieving person roles", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Error while retrieving person roles", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
 /**
      * This method is used to set collection of RoleDTs and return
      * them with role_seq values assigned.
      * @J2EE_METHOD  --  setRoleDTCollection
      * @param roleDTs       the Collection<Object>
      * @throws NEDSSSystemException
      **/
 public Collection<Object> setRoleDTCollection(Collection<Object> roleDTs)
      throws NEDSSSystemException
    {
        ArrayList<Object>  returnRoles = new ArrayList<Object> ();
        //ArrayList<Object>  rolesDTArray = (ArrayList<Object> )roleDTs;
        //logger.debug("Size of array after cast is: " + rolesDTArray.size());
        try
        {
            if(roleDAOImpl == null)
            {
                roleDAOImpl = (RoleDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ROLE_DAO_CLASS);
            }

            returnRoles = (ArrayList<Object> )roleDAOImpl.setRoleDTCollection(roleDTs);
            logger.debug("Size of the collection returned is: " + returnRoles.size());
            return returnRoles;


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Error while updating person roles", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Error while updating person roles", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


     /**
      * This method is used to set the participation for the
      * person and return the participationDT with a sequence.
      * @J2EE_METHOD  --  setParticipation
      * @param partDTs       the Collection<Object>
      * @throws NEDSSSystemException
      **/
 public void setParticipation(Collection<Object> partDTs)
      throws
        NEDSSSystemException
    {
        Collection<Object> newParts = new ArrayList<Object> ();
        try
        {
            if(personParticipationDAOImpl == null)
            {
                personParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_PARTICIPATION_DAO_CLASS);
            }
            Iterator<Object> iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                personParticipationDAOImpl.store(partDT);
            }


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Error while updating person participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Error while updating person participations", ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


    /**
      * This method is used to retrieve a person's Participations.
      * @J2EE_METHOD  --  selectParticipationDTCollection
      * @param aUID       the long
      * @throws NEDSSSystemException
      **/
 private Collection<Object> selectParticipationDTCollection(long aUID)
      throws NEDSSSystemException
    {
        try  {
            if(personParticipationDAOImpl == null) {
                personParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (personParticipationDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Error while retrieving person participations", ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Error while retrieving person participations");
            throw new NEDSSSystemException(ndapex.toString());
        }
    }
 
	private void filterNullFlavorRaces(
			Collection<Object> raceCollection, boolean isMPR) {
		if (raceCollection == null || raceCollection.size() == 0 || !isMPR)
			return;

		ArrayList<Object> raceList = CachedDropDowns
				.getNullFlavorCodedValueForType("PHVS_RACECATEGORY_CDC_NULLFLAVOR");
		Map<String, DropDownCodeDT> raceMap = new HashMap<String, DropDownCodeDT>();
		if (raceList != null & raceList.size() > 0) {
			for (Object obj : raceList) {
				DropDownCodeDT ddcDT = (DropDownCodeDT) obj;
				raceMap.put(ddcDT.getKey(), ddcDT);
			}
			/* add 'Refused to answer' in null flavor list as well */
			raceMap.put(NEDSSConstants.REFUSED_TO_ANSWER, new DropDownCodeDT());
		}

		/* check to see the different variation of null flavor */
		/* 1. Does the collection has any races other than null flavor? */
		boolean nonNullRace = false;
		boolean unkRace = false;
		Long updUserId = null;
		Timestamp lastChgTime = null;
		Long personUid = null;
		for (Object obj : raceCollection) {
			PersonRaceDT prDT = (PersonRaceDT) obj;
			updUserId = prDT.getLastChgUserId();
			lastChgTime = prDT.getLastChgTime();
			personUid = prDT.getPersonUid();
			if (!raceMap.containsKey(prDT.getRaceCd()) && !prDT.isItDelete()) {
				nonNullRace = true;
			}
			if (prDT.getRaceCd() != null
					&& prDT.getRaceCd().equals(NEDSSConstants.UNKNOWN)
					&& !prDT.isItDelete())
				unkRace = true;

		}
		/* 2. if there is a non null flavor race, remove any null flavor races */
		if (nonNullRace) {
			Collection<Object> removeColl = new ArrayList<Object>();
			if (raceCollection != null & raceCollection.size() > 0) {
				for (Object obj : raceCollection) {
					PersonRaceDT prDT = (PersonRaceDT) obj;
					if (raceMap.containsKey(prDT.getRaceCd())) {
						if (prDT.isItNew())
							removeColl.add(prDT);
						else if (prDT.isItDirty()) {
							prDT.setItDelete(true);
							prDT.setItDirty(false);
							prDT.setItNew(false);
						}
					}
				}
			}
			raceCollection.removeAll(removeColl);
		}
		/* 3. if all races are null flavor, delete all except UNK */
		else {
			Collection<Object> removeColl = new ArrayList<Object>();
			if (unkRace && raceCollection != null & raceCollection.size() > 1) {
				for (Object obj : raceCollection) {
					PersonRaceDT prDT = (PersonRaceDT) obj;
					if (!prDT.getRaceCd().equals(NEDSSConstants.UNKNOWN)) {
						if (prDT.isItNew())
							removeColl.add(prDT);
						else if (prDT.isItDirty()) {
							prDT.setItDelete(true);
							prDT.setItDirty(false);
							prDT.setItNew(false);
						}
					}
				}
				raceCollection.removeAll(removeColl);
			}
			/*Just one unknown race*/
			else if (unkRace && raceCollection != null
					& raceCollection.size() == 1) {
				//do nothing.
			}
			else if (raceCollection != null & raceCollection.size() > 0) {
				for (Object obj : raceCollection) {
					PersonRaceDT prDT = (PersonRaceDT) obj;
					if (prDT.isItNew())
						removeColl.add(prDT);
					else if (prDT.isItDirty()) {
						prDT.setItDelete(true);
						prDT.setItDirty(false);
						prDT.setItNew(false);
					}
				}
				raceCollection.removeAll(removeColl);
				PersonRaceDT unkRaceDT = new PersonRaceDT();
				unkRaceDT.setPersonUid(personUid);
				unkRaceDT.setItNew(true);
				unkRaceDT.setItDirty(false);
				unkRaceDT.setItDelete(false);
				unkRaceDT.setRaceCd(NEDSSConstants.UNKNOWN);
				unkRaceDT.setRaceCategoryCd(NEDSSConstants.UNKNOWN);
				unkRaceDT
						.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				unkRaceDT.setRecordStatusTime(lastChgTime);
				unkRaceDT.setAddTime(lastChgTime);
				unkRaceDT.setLastChgTime(lastChgTime);
				unkRaceDT.setAddUserId(updUserId);
				unkRaceDT.setLastChgUserId(updUserId);
				unkRaceDT.setAsOfDate(new Timestamp(new Date().getTime()));
				raceCollection.add(unkRaceDT);
			}
		}

	}

}//end of PersonRootDAOImpl class


