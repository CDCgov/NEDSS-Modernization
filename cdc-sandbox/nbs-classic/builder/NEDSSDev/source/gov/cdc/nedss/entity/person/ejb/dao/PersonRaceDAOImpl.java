package gov.cdc.nedss.entity.person.ejb.dao;


import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;



/**
* Name:		PersonRaceDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               PersonRace value object in the Person entity bean.
*               This class encapsulates all the JDBC calls made by the PersonEJB
*               for a PersonRace object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of PersonEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

public class PersonRaceDAOImpl extends DAOBase
{

	static final LogUtils logger = new LogUtils(PersonRaceDAOImpl.class.getName());

	public static final String INSERT_PERSON_RACE = "INSERT INTO " + DataTables.PERSON_RACE_TABLE + "(person_uid, race_cd, add_reason_cd, add_time, add_user_id, as_of_date, last_chg_reason_cd, last_chg_time, last_chg_user_id, race_category_cd, race_desc_txt, record_status_cd, record_status_time, user_affiliation_txt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_PERSON_RACE = "UPDATE " + DataTables.PERSON_RACE_TABLE + " set add_reason_cd = ?, add_time = ?, add_user_id = ?, as_of_date = ?, last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, race_category_cd = ?, race_desc_txt = ?, record_status_cd = ?, record_status_time = ?, user_affiliation_txt = ? WHERE person_uid = ? AND race_cd = ?";
	public static final String SELECT_PERSON_RACES = "SELECT person_uid \"personUid\", race_cd \"raceCd\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", as_of_date \"asOfDate\",last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", race_category_cd \"raceCategoryCd\", race_desc_txt \"raceDescTxt\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", user_affiliation_txt \"userAffiliationTxt\" FROM " + DataTables.PERSON_RACE_TABLE + " WHERE person_uid = ?";

	public static final String SELECT_PERSON_RACE_UID =
									"SELECT person_UID \"personUid\" FROM " +
									DataTables.PERSON_RACE_TABLE +
									" WHERE person_UID = ?";

	public static final String DELETE_PERSON_RACES = "DELETE FROM " + DataTables.PERSON_RACE_TABLE + " WHERE person_uid = ?";
        public static final String DELETE_PERSON_RACE = "DELETE FROM " + DataTables.PERSON_RACE_TABLE + " WHERE person_uid = ? AND race_cd = ?";

	public PersonRaceDAOImpl()
	{
	}


	/**
	  * This method creates a new PersonRace record and returns the personUID for this person.
	  * @J2EE_METHOD  --  create
	  * @param personUID       the long
	  * @param coll            the Collection<Object>
	  * @throws NEDSSSystemException
	  **/
	public long create(long personUID, Collection<Object> coll)
		throws NEDSSSystemException
	{
		try{
			insertPersonRaces(personUID, coll);
		
			return personUID;
		}catch(Exception ex){
			logger.fatal("personUID: "+personUID+", Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	  * This method is used to update a personRace record.
	  * @J2EE_METHOD  --  store
	  * @param coll       the Collection<Object>
	  * @throws NEDSSSystemException
	  **/
	public void store(Collection<Object> coll) throws NEDSSSystemException
	{
		try{
			updatePersonRaces(coll);
		}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
	}

	/**
	  * This method is used to delete a personRace record.
	  * @J2EE_METHOD  --  remove
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  * @throws NEDSSSystemException
	  **/
	public void remove(long personUID) throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			removePersonRaces(personUID);
		}catch(Exception ex){
    		logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
	}

	 /**
	  * This method loads the PersonRaceDT objects for a given personUID.
	  * @J2EE_METHOD  --  load
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  * @throws NEDSSSystemException
	  **/
	public Collection<Object> load(long personUID) throws NEDSSDAOSysException,
		NEDSSSystemException
	{
		try{
			Collection<Object> prColl = selectPersonRaces(personUID);
			return prColl;
		}catch(Exception ex){
    		logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
	}

	  /**
	  * This method is used to determine is a personRace record exists for a given personUID.
	  * @J2EE_METHOD  --  findByPrimaryKey
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  * @throws NEDSSSystemException
	  **/
	public Long findByPrimaryKey(long personUID) throws NEDSSDAOSysException,
		NEDSSSystemException
	{
		try{
			if (personRaceExists(personUID))
				return (new Long(personUID));
			else
				logger.error("No race found for this primary key :" + personUID);
				return null;
		}catch(Exception ex){
    		logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
	}


	/**
	  * This method is used to determine if a personRace record exists for a specific person.
	  * @J2EE_METHOD  --  personRaceExists
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  * @throws NEDSSSystemException
	  **/
	@SuppressWarnings("unchecked")
	protected boolean personRaceExists (long personUID) throws NEDSSDAOSysException,
		NEDSSSystemException
	{
		boolean returnValue = false;
		PersonRaceDT personRaceDT = new PersonRaceDT();
		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		Long longPersonUid = new Long(personUID);
		arrayList.add(longPersonUid);

		try
		{
			// using the preparedStmtMethod from DAOBase
			// pass the dt in to hold the uid for comparison
			// pass in an arraylist uid for the sql statement
			// pass in the sql you wish to use
			// pass in queryType constant so it will return a RootDTInterface
			arrayList = (ArrayList<Object> )preparedStmtMethod(personRaceDT, arrayList, SELECT_PERSON_RACE_UID, NEDSSConstants.SELECT);
			if(arrayList.size()!=0)
				personRaceDT = (PersonRaceDT)arrayList.get(0);
			if(personRaceDT!=null)
				if(personRaceDT.getPersonUid()!=null)
					if (personRaceDT.getPersonUid().equals(longPersonUid))
						returnValue = true; //only true if it exist
		}
		 catch (Exception ex) {
			logger.fatal("personUID: "+personUID+" Exception in personExists():  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

		return returnValue;
	}


	/**
	  * This method is used to add a Collection<Object> of personRace objects for a specific person.
	  * @J2EE_METHOD  --  insertPersonRaces
	  * @param personUID       the long
	  * @param personRaces      the Collection<Object>
	  * @throws NEDSSSystemException
	  **/
	private void insertPersonRaces(long personUID, Collection<Object> personRaces)
		throws NEDSSSystemException
	{

	Iterator<Object> anIterator = null;

	try
	{
		 for (anIterator = personRaces.iterator(); anIterator.hasNext(); )
		 {
			PersonRaceDT personRace = (PersonRaceDT)anIterator.next();
			if (personRace != null)
			insertPersonRace(personUID, personRace);
			personRace.setPersonUid(new Long(personUID));
		 }
	 }
	 catch(NEDSSDAOSysException npduex)
	 {
		logger.fatal("personUID: "+personUID+" Update exception while inserting " +
			"person races into PERSON_RACE_TABLE: \n", npduex);
		throw new NEDSSDAOSysException(npduex.toString());
	 }
	 catch(Exception ex)
	 {
		logger.fatal("personUID: "+personUID+" Exception while inserting " +
			"person races into PERSON_RACE_TABLE: \n", ex);
		throw new NEDSSDAOSysException(ex.toString() );
	 }
	}//end of inserting person races


	/**
	  * This method is used to add a personRace entry for a specific person.
	  * @J2EE_METHOD  --  insertPersonRace
	  * @param personUID       the long
	  * @param personRace      the PersonRaceDT
	  * @throws NEDSSSystemException
	  **/
	private void insertPersonRace(long personUID, PersonRaceDT personRace)
		throws NEDSSSystemException
	{
		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		String localUid = null;

		try
		{
			// insert new person into person table
			if (personRace != null)
			{
				arrayList.add(new Long(personUID));
				arrayList.add(personRace.getRaceCd());
				arrayList.add(personRace.getAddReasonCd());
				arrayList.add(personRace.getAddTime());
				arrayList.add(personRace.getAddUserId());
				arrayList.add(personRace.getAsOfDate());
				arrayList.add(personRace.getLastChgReasonCd());
				arrayList.add(personRace.getLastChgTime());
				arrayList.add(personRace.getLastChgUserId());
				arrayList.add(personRace.getRaceCategoryCd());
				arrayList.add(personRace.getRaceDescTxt());
				arrayList.add(personRace.getRecordStatusCd());
				arrayList.add(personRace.getRecordStatusTime());
				arrayList.add(personRace.getUserAffiliationTxt());

				// using the preparedStmtMethod from DAOBase
				// pass the dt in for the update
				// pass in an arraylist (ordered list)
				//        with all the parameters for the sql statement
				// pass in the sql you wish to use
				// update is the same as insert so it uses the same opperation constant
				int resultCount = ((Integer)preparedStmtMethod(personRace, arrayList, INSERT_PERSON_RACE, NEDSSConstants.UPDATE)).intValue();
				if (resultCount != 1)
				{
					logger.error("Error: none or more than one personRace updated at a time, " + "resultCount = " + resultCount);
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("personUID: "+personUID+" Exception in updatePerson() = " + ex.getMessage(), ex);
			throw new NEDSSSystemException("Table Name : "+DataTables.PERSON_RACE_TABLE+"  For personUID"+personUID+"  "+ex.toString(), ex);
		}
	}//end of inserting a person race


	/**
	  * This method is used to update a Collection<Object> of personRace objects for a specific person.
	  * @J2EE_METHOD  --  updatePersonRaces
	  * @param personRaces       the Collection<Object>
	  * @throws NEDSSSystemException
	  **/
	private void updatePersonRaces (Collection<Object> personRaces) throws NEDSSSystemException
	{
	PersonRaceDT personRace = null;
	Iterator<Object> anIterator = null;

	if(personRaces != null)
	{
		/**
		 * Updates person races
		 */
		try
		{
		logger.debug("Updating person races");
		for(anIterator = personRaces.iterator(); anIterator.hasNext(); )
		{
			personRace = (PersonRaceDT)anIterator.next();
			if(personRace == null)
			logger.error("Error: Empty person race collection");

			if(personRace.isItNew())
			{
			  insertPersonRace((personRace.getPersonUid()).longValue(), personRace);
			  personRace.setItNew(false);
			  personRace.setItDirty(false);
			}
			else if(personRace.isItDirty())
			{
			   updatePersonRace(personRace);
			}
                        else if(personRace.isItDelete())
                        {
                          removePersonRace(personRace.getPersonUid().longValue(), personRace.getRaceCd());
                        }


		}
		}
		catch(Exception sex)
		{
		logger.fatal("Exception while updating " +
			"person races, \n", sex);
		throw new NEDSSDAOSysException(sex.toString());
		}
	   }
	}//end of updating person race table

	/**
	  * This method is used to update a personRace object for a specific person.
	  * @J2EE_METHOD  --  updatePersonRace
	  * @param personRace       the PersonRaceDT
	  * @throws NEDSSSystemException
	  **/
	private void updatePersonRace(PersonRaceDT personRace) throws NEDSSSystemException
	{
		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		Long personUid = new Long(0);
		String localUid = null;

		try
		{
			// insert new person into person table
			if (personRace != null)
			{
				arrayList.add(personRace.getAddReasonCd());//3
				arrayList.add(personRace.getAddTime());//4
				arrayList.add(personRace.getAddUserId());//5
				arrayList.add(personRace.getAsOfDate());//6
				arrayList.add(personRace.getLastChgReasonCd());//7
				arrayList.add(personRace.getLastChgTime());//8
				arrayList.add(personRace.getLastChgUserId());//9
				arrayList.add(personRace.getRaceCategoryCd());//10
				arrayList.add(personRace.getRaceDescTxt());//11
				arrayList.add(personRace.getRecordStatusCd());//12
				arrayList.add(personRace.getRecordStatusTime());//13
				arrayList.add(personRace.getUserAffiliationTxt());//14
				arrayList.add(personRace.getPersonUid());//1
				arrayList.add(personRace.getRaceCd());//2

				// using the preparedStmtMethod from DAOBase
				// pass the dt in for the update
				// pass in an arraylist (ordered list)
				//        with all the parameters for the sql statement
				// pass in the sql you wish to use
				// update is the same as insert so it uses the same opperation constant
				int resultCount = ((Integer)preparedStmtMethod(personRace, arrayList, UPDATE_PERSON_RACE, NEDSSConstants.UPDATE)).intValue();
				if (resultCount != 1)
				{
					logger.error("Error: none or more than one personRace updated at a time, " + "resultCount = " + resultCount);
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in updatePerson() = " + ex.getMessage(), ex);
			throw new NEDSSSystemException("Table Name : "+DataTables.PERSON_RACE_TABLE+"  "+ex.toString(), ex);
		}
	}//end of updating a person race

	/**
	  * This method is used to retrieve personRace objects for a specific person.
	  * @J2EE_METHOD  --  selectPersonRaces
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  **/
	  @SuppressWarnings("unchecked")
	private Collection<Object> selectPersonRaces (long personUID) throws NEDSSDAOSysException
	{
		PersonRaceDT personRace = new PersonRaceDT();
		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		arrayList.add(new Long(personUID));
		try
		{
			// using the preparedStmtMethod from DAOBase
			// pass the dt in for the update
			// pass in an arraylist (ordered list)
			//        with all the parameters for the sql statement
			// pass in the sql you wish to use
			// pass in queryType constant so it will return a RootDTInterface
			return arrayList = (ArrayList<Object> )preparedStmtMethod(personRace, arrayList, SELECT_PERSON_RACES, NEDSSConstants.SELECT);
		}
		 catch (Exception ex) {
			logger.fatal("personUID: "+personUID+" Exception in selectPerson:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}//end of selecting person races

	/**
	  * This method is used to remove a personRace object from a specific person.
	  * @J2EE_METHOD  --  removePersonRaces
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  **/
	private void removePersonRaces (long personUID) throws NEDSSDAOSysException
	{
		PersonRaceDT personRace = new PersonRaceDT();
		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		arrayList.add(new Long(personUID));

		try
		{
			// using the preparedStmtMethod from DAOBase
			// pass the dt in for the update
			// pass in an arraylist (ordered list)
			// with all the parameters for the sql statement
			// pass in the sql you wish to use
			// pass in queryType constant so it will return a RootDTInterface
			preparedStmtMethod(personRace, arrayList, DELETE_PERSON_RACES, NEDSSConstants.UPDATE);
		}
		 catch (Exception ex) {
			logger.fatal("personUID: "+personUID+" Exception in removePersonRaces:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}//end of removing person races

        private void removePersonRace (long personUID, String raceCd) throws NEDSSDAOSysException
        {
                PersonRaceDT personRace = new PersonRaceDT();
                ArrayList<Object>  arrayList = new ArrayList<Object> ();
                arrayList.add(new Long(personUID));
                arrayList.add(raceCd);

                try
                {
                        // using the preparedStmtMethod from DAOBase
                        // pass the dt in for the update
                        // pass in an arraylist (ordered list)
                        // with all the parameters for the sql statement
                        // pass in the sql you wish to use
                        // pass in queryType constant so it will return a RootDTInterface
                        preparedStmtMethod(personRace, arrayList, DELETE_PERSON_RACE, NEDSSConstants.UPDATE);
                }
                 catch (Exception ex) {
                        logger.fatal("personUID: "+personUID+" Exception in removePersonRaces:  ERROR = " + ex.getMessage(), ex);
                        throw new NEDSSSystemException(ex.toString());
                }
        }//end of removing person race

/*
	public static void main(String[] strg)
	{
		logger.setLogLevel(6);

		PersonRaceDAOImpl personRaceDAOImpl = new PersonRaceDAOImpl();
		PersonRaceDT personRace = new PersonRaceDT();

		long personUidLong = 0;
		try
		{
			//**** sql server personuid
			personUidLong = 470032066;
			//int versionctrlnbr = 5;

			//**** oracle server personuid
			//personUidLong = 310326053;

//			  Long returnedvalue = personDAOImpl.matchingSubject(personDT, personNameDT);

//			  logger.debug("returnedvalue = " + returnedvalue);


//			  if(personUidLong != 0)
			{
				logger.debug("-----------start-----------------");

				logger.debug("personRaceDAOImpl.personRaceExists(personUidLong) = " + personRaceDAOImpl.personRaceExists(personUidLong));

				ArrayList<Object>  arrayList = (ArrayList<Object> )personRaceDAOImpl.selectPersonRaces(personUidLong);
				if(arrayList.size()!=0)
				{
			   //     personDT = (PersonDT)personDAOImpl.selectPerson(personUidLong);
			   //     logger.debug(personDAOImpl.checkDeletePerson(new Long(personUidLong)));
			   //     logger.debug(personDAOImpl.checkDeletePerson(new Long(personUidLong + 5)));

				for(int i=0;i<arrayList.size();i++)
				{
					personRace = (PersonRaceDT)arrayList.get(i);

					logger.debug("----------------------------");

					logger.debug("personRace.getRaceCd() = " + personRace.getRaceCd());
					logger.debug("personRace.getAddReasonCd() = " + personRace.getAddReasonCd());
					logger.debug("personRace.getAddTime() = " + personRace.getAddTime());
					logger.debug("personRace.getAddUserId() = " + personRace.getAddUserId());
					logger.debug("personRace.getLastChgReasonCd() = " + personRace.getLastChgReasonCd());
					logger.debug("personRace.getRecordStatusCd() = " + personRace.getRecordStatusCd());

					logger.debug("----------------------------");
				}

				personRace.setPersonUid(new Long(personUidLong));
				personRace.setRaceCd("rcCD3");
				personRace.setAddReasonCd("Chg");
				personRace.setAddTime(new Timestamp(new java.util.Date().getTime()));
				personRace.setAddUserId(new Long(2003));
				personRace.setLastChgReasonCd("NewTestRecord");
				personRace.setLastChgTime(new Timestamp(new java.util.Date().getTime()));
				personRace.setLastChgUserId(new Long("2003"));
				personRace.setRaceCategoryCd("raceCategory");
				personRace.setRaceDescTxt("UpdatedInfo");
				personRace.setRecordStatusCd("A");
				personRace.setRecordStatusTime(new Timestamp(new java.util.Date().getTime()));
				personRace.setUserAffiliationTxt("CDC");
				personRace.setItDirty(true);
				personRace.setItNew(false);
				personRaceDAOImpl.updatePersonRace(personRace);


//				  personRaceDAOImpl.removePersonRaces(personUidLong);

//					  personDT.setPersonUid(new Long(personUidLong));
//					  personDT.setFirstNm("John");
//					  personDT.setLastNm("Paul");
//					  personDAOImpl.updatePerson(personDT);
				}
				logger.debug("-----------finish-----------------");
			}

		}
		catch (Exception ex)
		{
			logger.fatal("Exception in main():  ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}// end of main - for testing
*/
}//end of PersonDAOImpl class
