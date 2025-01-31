/**
* Name:		PersonHistRootDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for inserting
*               Person value object into Person related history table.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Ning Peng
* @version	1.0
*/

package gov.cdc.nedss.entity.person.ejb.dao;


import java.util.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.entityid.dao.*;
import gov.cdc.nedss.locator.dao.*;
import gov.cdc.nedss.association.dao.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.locator.dt.*;



public class PersonHistoryManager
{
   static final LogUtils logger = new LogUtils(PersonHistoryManager.class.getName());

    private short versionCtrlNbr = -1;
    private PersonVO pvoHist = null;
    private long personUid;
    private PersonHistDAOImpl personDAO = null;
    private PersonNameHistDAOImpl personNameDAO = null;
    private PersonRaceHistDAOImpl personRaceDAO = null;
    private PersonEthnicGroupHistDAOImpl personEthnicGroupDAO = null;
    private EntityIdHistDAOImpl entityIdDAO = null;
    private EntityLocatorParticipationHistDAOImpl entityLocatorParticipationDAO = null;
    private PhysicalLocatorHistDAOImpl physicalLocatorHistDAO = null;
    private PostalLocatorHistDAOImpl postalLocatorHistDAO = null;
    private TeleLocatorHistDAOImpl teleLocatorHistDAO = null;
    private RoleHistDAOImpl roleDAO = null;
    private ParticipationHistDAOImpl participationDAO = null;

    /**
   *
   * This is the default constructor
   */
    public PersonHistoryManager()
    {
    }

    /**
     * Creates and populates the class attribute child objects with the versionCtrlNbr.
     * @param uid : long
     * @param versionCtrlNbr : short
     */
    public PersonHistoryManager(long uid, short versionCtrlNbr) throws NEDSSDAOSysException, NEDSSSystemException
    {
        personUid = uid;
        personDAO = new PersonHistDAOImpl(uid, versionCtrlNbr);
        this.versionCtrlNbr = versionCtrlNbr;
        personNameDAO = new PersonNameHistDAOImpl(versionCtrlNbr);
        personRaceDAO = new PersonRaceHistDAOImpl(versionCtrlNbr);
        personEthnicGroupDAO = new PersonEthnicGroupHistDAOImpl(versionCtrlNbr);
        entityIdDAO = new EntityIdHistDAOImpl(versionCtrlNbr);
        entityLocatorParticipationDAO = new EntityLocatorParticipationHistDAOImpl(versionCtrlNbr);
        physicalLocatorHistDAO = new PhysicalLocatorHistDAOImpl(versionCtrlNbr);
        postalLocatorHistDAO = new PostalLocatorHistDAOImpl(versionCtrlNbr);
        teleLocatorHistDAO = new TeleLocatorHistDAOImpl(versionCtrlNbr);
        roleDAO = new RoleHistDAOImpl(versionCtrlNbr);
        participationDAO = new ParticipationHistDAOImpl(versionCtrlNbr);


        //this.nextSeqNumber = personDAO.getSeqNumber();
    }

  /**
   * Description:
   *    This function takes a old PersonVO and stores them into history tables
   * @param obj : Object  The object is a PersonVO object
   * @return void
   */
    public void store(Object obj) throws NEDSSDAOSysException, NEDSSSystemException
    {
        pvoHist = (PersonVO)obj;
        if ( pvoHist == null) return;
        /**
         * Insert person to history
         */

        if(pvoHist.getThePersonDT() != null )
        {
            insertPerson(pvoHist.getThePersonDT());
            pvoHist.getThePersonDT().setItNew(false);
        }

        /**
        * Insert person names collection
        */

        if (pvoHist.getThePersonNameDTCollection() != null)
        {
            insertPersonNames(pvoHist);
        }

        /**
        * Insert person races collection
        */

        if (pvoHist.getThePersonRaceDTCollection() != null)
        {
            insertPersonRaces(pvoHist);
        }

        /**
        * Insert person ethnic groups collection
        */
        if (pvoHist.getThePersonEthnicGroupDTCollection() != null)
        {
            insertPersonEthnicGroups(pvoHist);
        }

        /**
         * Insert entity ids collection
         */
        if(pvoHist.getTheEntityIdDTCollection() != null)
        {
            insertEntityIDs(pvoHist);
        }

        /**
        * Insert entity locator participations collection
        */
        if (pvoHist.getTheEntityLocatorParticipationDTCollection() != null)
        {
            insertEntityLocatorParticipations(pvoHist);
        }

        /*
        * Insert roles collection

       if (pvoHist.getTheRoleDTCollection() != null)
        {
            insertRoles(pvoHist);
        } */

        /*
        * Insert participation collection

      if (pvoHist.getTheParticipationDTCollection() != null)
        {
            insertParticipations(pvoHist);
        }*/

    }

    /**
     * Loads the PersonVO from history
     * @param personUid : Long
     * @param versionCtrlNbr : Integer
     */
    public PersonVO load(Long personUid, Integer versionCtrlNbr)
	 throws NEDSSSystemException, NEDSSSystemException
    {

        logger.info("Starts loadObject() for a personvo for the history...");

        PersonVO pvo = new PersonVO();
	personDAO = new PersonHistDAOImpl();
        personNameDAO = new PersonNameHistDAOImpl();
        personRaceDAO = new PersonRaceHistDAOImpl();
        personEthnicGroupDAO = new PersonEthnicGroupHistDAOImpl();
        entityIdDAO = new EntityIdHistDAOImpl();
        entityLocatorParticipationDAO = new EntityLocatorParticipationHistDAOImpl();

        /**
        *  Selects PersonDT object
        */

        PersonDT pDT =personDAO.load(personUid, versionCtrlNbr);
        pvo.setThePersonDT(pDT);

        /**
        * Selects PersonNameDT Collection<Object><Object>
        */

        Collection<Object> pnColl = personNameDAO.load(personUid, versionCtrlNbr);
        pvo.setThePersonNameDTCollection(pnColl);

        /**
        * Selects PersonRaceDT Collection<Object><Object>
        */

        Collection<Object> prColl = personRaceDAO.load(personUid, versionCtrlNbr);
        pvo.setThePersonRaceDTCollection(prColl);

        /**
        * Selects PersonEthnicGroupDT collection
        */

        Collection<Object> pegColl = personEthnicGroupDAO.load(personUid, versionCtrlNbr);
        pvo.setThePersonEthnicGroupDTCollection(pegColl);

        /**
        * Selects EntityIdDT collection
        */

        Collection<Object> idColl = entityIdDAO.load(personUid, versionCtrlNbr);
        pvo.setTheEntityIdDTCollection(idColl);

        /**
        * Selects EntityLocatorParticipationDT collection
        */

        Collection<Object> elpColl = entityLocatorParticipationDAO.load(personUid, versionCtrlNbr);
	ArrayList<Object>  newElpColl = new ArrayList<Object> ();

	for(Iterator<Object> anIterator = elpColl.iterator(); anIterator.hasNext(); )
	{
	    EntityLocatorParticipationDT elpdt = (EntityLocatorParticipationDT)anIterator.next();
		if(elpdt != null)
                {
		    Long locatorUid = elpdt.getLocatorUid();
		    physicalLocatorHistDAO = new PhysicalLocatorHistDAOImpl();
		    postalLocatorHistDAO = new PostalLocatorHistDAOImpl();
		    teleLocatorHistDAO = new TeleLocatorHistDAOImpl();
		    Collection<Object> physicalLocColl = physicalLocatorHistDAO.load(locatorUid, versionCtrlNbr);
		    Collection<Object> postalLocColl = postalLocatorHistDAO.load(locatorUid, versionCtrlNbr);
		    Collection<Object> teleLocColl = teleLocatorHistDAO.load(locatorUid, versionCtrlNbr);

		    for(Iterator<Object> it = physicalLocColl.iterator(); it.hasNext(); )
		    {
			PhysicalLocatorDT pldt = (PhysicalLocatorDT)it.next();
			elpdt.setThePhysicalLocatorDT(pldt);
		    }
		    for(Iterator<Object> it = postalLocColl.iterator(); it.hasNext(); )
		    {
			PostalLocatorDT pldt = (PostalLocatorDT)it.next();
			elpdt.setThePostalLocatorDT(pldt);
		    }
		    for(Iterator<Object> it = teleLocColl.iterator(); it.hasNext(); )
		    {
			TeleLocatorDT pldt = (TeleLocatorDT)it.next();
			elpdt.setTheTeleLocatorDT(pldt);
		    }
                }
		newElpColl.add(elpdt);

	}
        pvo.setTheEntityLocatorParticipationDTCollection(elpColl);

        logger.info("Done loadObject() for a personvo for history - return: " + pvo);
        return pvo;
    }

    /**
     * Results in the insertion of a person record into person history
     * @param personDt : PersonDT   The record to be inserted
     * @return void
     */
    private void insertPerson(PersonDT personDt) throws NEDSSDAOSysException, NEDSSSystemException
    {
        if(personDt == null)
          throw new NEDSSSystemException("Error: insert null personDt into personHist.");
        personDAO.store(personDt);

    }

    /**
     * Results in the insertion of a person name history record
     * @param pvo : PersonVO
     * @return void
     */
    private void insertPersonNames(PersonVO pvo) throws NEDSSDAOSysException, NEDSSSystemException
    {
        personNameDAO.store(pvo.getThePersonNameDTCollection());
    }

    /**
     * Results in the insertion of a person race history record
     * @param pvo : PersonVO
     * @return void
     */
    private void insertPersonRaces(PersonVO pvo) throws NEDSSDAOSysException, NEDSSSystemException
    {
        personRaceDAO.store(pvo.getThePersonRaceDTCollection());
    }

    /**
     * Results in the insertion of a person ethnic group history record
     * @param pvo : PersonVO
     * @return void
     */
    private void insertPersonEthnicGroups(PersonVO pvo) throws NEDSSDAOSysException, NEDSSSystemException
    {
        personEthnicGroupDAO.store(pvo.getThePersonEthnicGroupDTCollection());
    }

    /**
     * Results in the insertion of a entity id history record
     * @param pvo : PersonVO
     * @return void
     */
    private void insertEntityIDs(PersonVO pvo) throws NEDSSDAOSysException, NEDSSSystemException
    {
        entityIdDAO.store(pvo.getTheEntityIdDTCollection());

    }

   /**
     * Results in the insertion of physical, postal, tele locator history record
     * @param pvo : PersonVO
     * @return void
     */
   private void insertEntityLocatorParticipations(PersonVO pvo) throws NEDSSDAOSysException, NEDSSSystemException
    {
        Collection<Object> elpColl = pvo.getTheEntityLocatorParticipationDTCollection();
        physicalLocatorHistDAO = new PhysicalLocatorHistDAOImpl(versionCtrlNbr);
        postalLocatorHistDAO = new PostalLocatorHistDAOImpl(versionCtrlNbr);
        teleLocatorHistDAO = new TeleLocatorHistDAOImpl(versionCtrlNbr);
	entityLocatorParticipationDAO.store(elpColl);
	for(Iterator<Object> anIterator = elpColl.iterator(); anIterator.hasNext(); )
        {
                EntityLocatorParticipationDT elpdt = (EntityLocatorParticipationDT)anIterator.next();
		if(elpdt != null)
                {
		    PhysicalLocatorDT pldt = elpdt.getThePhysicalLocatorDT();
		    PostalLocatorDT poldt = elpdt.getThePostalLocatorDT();
		    TeleLocatorDT tldt = elpdt.getTheTeleLocatorDT();

		    physicalLocatorHistDAO.store(pldt);
		    postalLocatorHistDAO.store(poldt);
		    teleLocatorHistDAO.store(tldt);
                }

        }

    }


    /**
     * Results in the insertion of a role history record
     * @param pvo : PersonVO
     * @return void
     */
    private void insertRoles(PersonVO pvo) throws NEDSSDAOSysException, NEDSSSystemException
    {
        roleDAO.store(pvo.getTheRoleDTCollection());
    }

    /**
     * Results in the insertion of a participation history record
     * @param pvo : PersonVO
     * @return void
     */
    private void insertParticipations(PersonVO pvo) throws NEDSSDAOSysException, NEDSSSystemException
    {
        participationDAO.store(pvo.getTheParticipationDTCollection());
    }


}
