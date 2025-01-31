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
* Name:		PersonRaceHistDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for old the
*               PersonRace collection inserting into PersonRaceHist table.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/

public class PersonRaceHistDAOImpl extends DAOBase
{
static final LogUtils logger = new LogUtils(PersonRaceHistDAOImpl.class.getName());

	private int versionCtrlNbr = -1;

	private final String INSERT_PERSON_RACE_HIST
	  = "INSERT INTO Person_race_hist ( "
	  + "person_uid, "
	  + "race_cd, "
	  + "version_ctrl_nbr, "
	  + "add_reason_cd, "
	  + "add_time, "
	  + "add_user_id, "
	  + "as_of_date, "
	  + "last_chg_reason_cd, "
	  + "last_chg_time, "
	  + "last_chg_user_id, "
	  + "race_category_cd, "
	  + "race_desc_txt, "
	  + "record_status_cd, "
	  + "record_status_time, "
	  + "user_affiliation_txt "
	  + " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	  private final String SELECT_PERSON_RACES_HIST = "SELECT person_uid \"personUid\", race_cd \"raceCd\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", as_of_date \"asOfDate\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", race_category_cd \"raceCategoryCd\", race_desc_txt \"raceDescTxt\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", user_affiliation_txt \"userAffiliationTxt\" FROM " + DataTables.PERSON_RACE_TABLE_HIST + " WHERE person_uid = ? AND version_ctrl_nbr = ? ";

	//for the time being, using default change_user_id for testing
	private long defaultChangeUserId = 0;
/*
	//for testing
	NedssUtils nu = new NedssUtils();
	Connection dbConnection = nu.getTestConnection();
*/
	/**
	 * Default Contructor
	 */
	public PersonRaceHistDAOImpl()
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
	}

	/**
	 * Initializes the class attribute versionCtrlNbr.
	 * @param versionCtrlNbr : int
	 */
	public PersonRaceHistDAOImpl(int versionCtrlNbr)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
		this.versionCtrlNbr = versionCtrlNbr;
	}

	/**
	 * Searchs for and loads the person race dt object associated with
	 * the person uid and versionCtrlNbr.
	 * @param personUid : Long
	 * @param versionCtrlNbr : Integer
	 * @return Collection<Object>  Collection<Object> of personRaceDt objects
	 */
	public Collection<Object> load(Long personUID, Integer versionCtrlNbr)
	throws NEDSSSystemException, NEDSSSystemException
	{
		try{
			Collection<Object> prColl = selectPersonRacesHist(personUID.longValue(), versionCtrlNbr.intValue());
			return prColl;
		}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
	}

  /**
   * Description:
   *    This function takes a collection of DTs and stores them into history table
   * @param : Collection<Object>   A collection of DTs
   *
   * @return void
   */
	public void store(Collection<Object> coll)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			Iterator<Object> iterator = null;
			if(coll != null)
			{
			  iterator = coll.iterator();
			  while(iterator.hasNext())
			  {
				store(iterator.next());
			  }
			}
		}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
	}
  /**
   * Description:
   *    This function takes a single DT and stores them into history table
   * @param obj : Object   a DT object to be stored.
   * @return void
   */
	public void store(Object obj)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			PersonRaceDT dt = (PersonRaceDT)obj;
			if(dt == null)
			   throw new NEDSSSystemException("Error: try to store null PersonRaceDT object.");
			  insertPersonRaceHist(dt);
		}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}

	}

	/**
	 * Private method that selects and returns a collection of personRaceDT objects
	 * @param personUid : long
	 * @param versionCtrlNbr : int
	 * @return Collection<Object>  Returns a collection of personRaceDT objects
	 */
	@SuppressWarnings("unchecked")
	private Collection<Object> selectPersonRacesHist (long personUID, int versionCtrlNbr)
	throws NEDSSSystemException, NEDSSSystemException
	{
		PersonRaceDT personRace = new PersonRaceDT();
		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		arrayList.add(new Long(personUID));
		arrayList.add(new Long(versionCtrlNbr));
		try
		{
			// using the preparedStmtMethod from DAOBase
			// pass the dt in for the update
			// pass in an arraylist (ordered list)
			//        with all the parameters for the sql statement
			// pass in the sql you wish to use
			// pass in queryType constant so it will return a RootDTInterface
			return arrayList = (ArrayList<Object> )preparedStmtMethod(personRace, arrayList, SELECT_PERSON_RACES_HIST, NEDSSConstants.SELECT);
		}
		 catch (Exception ex) {
			logger.fatal("Exception in selectPerson:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Inserts a person race history record
	 * @param dt : PersonRaceDT
	 * @return void
	 */
	private void insertPersonRaceHist(PersonRaceDT personRace)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{

		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		String localUid = null;

		try
		{
			// insert new person into person table
			if (personRace != null)
			{
				arrayList.add(personRace.getPersonUid());//1
				arrayList.add(personRace.getRaceCd());//2
				arrayList.add(new Long(versionCtrlNbr));
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

				// using the preparedStmtMethod from DAOBase
				// pass the dt in for the update
				// pass in an arraylist (ordered list)
				//        with all the parameters for the sql statement
				// pass in the sql you wish to use
				// update is the same as insert so it uses the same opperation constant
				int resultCount = ((Integer)preparedStmtMethod(personRace, arrayList, INSERT_PERSON_RACE_HIST, NEDSSConstants.UPDATE)).intValue();
				if (resultCount != 1)
				{
					logger.error("Error: none or more than one personRace updated at a time, " + "resultCount = " + resultCount);
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in updatePerson() = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
}