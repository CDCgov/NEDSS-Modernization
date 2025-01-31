package gov.cdc.nedss.entity.person.ejb.dao;

import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
* Name:		PersonNameDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               PersonName value object in the Person entity bean.
*               This class encapsulates all the JDBC calls made by the PersonEJB
*               for a PersonName object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of PersonEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/
public class PersonNameDAOImpl extends DAOBase
{
	static final LogUtils logger = new LogUtils(PersonNameDAOImpl.class.getName());

	public static final String INSERT_PERSON_NAME =
									"INSERT INTO " +
									DataTables.PERSON_NAME_TABLE +
									"(person_uid, " +
									"person_name_seq, " +
									"add_reason_cd, " +
									"add_time, " +
									"add_user_id, " +
									"as_of_date, " +
									"duration_amt, " +
									"duration_unit_cd, " +
									"first_nm, " +
									"first_nm_sndx, " +
									"from_time, " +
									"last_chg_reason_cd, " +
									"last_chg_time, " +
									"last_chg_user_id,	" +
									"last_nm, " +
									"last_nm_sndx, " +
									"last_nm2, " +
									"last_nm2_sndx, " +
									"middle_nm, " +
									"middle_nm2, " +
									"nm_degree, " +
									"nm_prefix, " +
									"nm_suffix, " +
									"nm_use_cd, " +
									"record_status_cd, " +
									"record_status_time, " +
									"status_cd, " +
									"status_time, " +
									"to_time, " +
									"user_affiliation_txt) " +
									"VALUES (" +
									"?, ?, ?, ?, ?, ?, " +
									"?, ?, ?, soundex(?), ?, " +
									"?, ?, ?, ?, soundex(?), " +
									"?, soundex(?), ?, ?, ?, " +
									"?, ?, ?, ?, ?, " +
									"?, ?, ?, ?)";

	public static final String UPDATE_PERSON_NAME =
									"UPDATE " +
									DataTables.PERSON_NAME_TABLE +
									" set add_reason_cd = ?,  " +
									"add_time = ?,  " +
									"add_user_id = ?,  " +
									"as_of_date = ?,  " +
									"duration_amt = ?,  " +
									"duration_unit_cd = ?,  " +
									"first_nm = ?,  " +
									"first_nm_sndx = soundex(?),  " +
									"from_time = ?,  " +
									"last_chg_reason_cd = ?,  " +
									"last_chg_time = ?,  " +
									"last_chg_user_id = ?,  " +
									"last_nm = ?,  " +
									"last_nm_sndx = soundex(?),  " +
									"last_nm2 = ?,  " +
									"last_nm2_sndx = soundex(?),  " +
									"middle_nm = ?,  " +
									"middle_nm2 = ?,  " +
									"nm_degree = ?,  " +
									"nm_prefix = ?,  " +
									"nm_suffix = ?,  " +
									"nm_use_cd = ?,  " +
									"record_status_cd = ?,  " +
									"record_status_time = ?,  " +
									"status_cd = ?,  " +
									"status_time = ?,  " +
									"to_time = ?,  " +
									"user_affiliation_txt = ?  " +
									"WHERE person_uid = ?  " +
									"AND person_name_seq = ?";
	public static final String SELECT_PERSON_NAMES =
									"SELECT person_uid \"personUid\", " +
									"person_name_seq \"personNameSeq\", " +
									"add_reason_cd \"addReasonCd\", " +
									"add_time \"addTime\", " +
									"add_user_id \"addUserId\", " +
									"as_of_date \"asOfDate\", " +
									"duration_amt \"durationAmt\", " +
									"duration_unit_cd \"durationUnitCd\", " +
									"first_nm \"firstNm\", " +
									"first_nm_sndx \"firstNmSndx\", " +
									"from_time \"fromTime\", " +
									"last_chg_reason_cd \"lastChgReasonCd\", " +
									"last_chg_time \"lastChgTime\", " +
									"last_chg_user_id \"lastChgUserId\", " +
									"last_nm \"lastNm\", " +
									"last_nm_sndx \"lastNmSndx\", " +
									"last_nm2 \"lastNm2\", " +
									"last_nm2_sndx \"lastNm2Sndx\", " +
									"middle_nm \"middleNm\", " +
									"middle_nm2 \"middleNm2\", " +
									"nm_degree \"nmDegree\", " +
									"nm_prefix \"nmPrefix\", " +
									"nm_suffix \"nmSuffix\", " +
									"nm_use_cd \"nmUseCd\", " +
									"record_status_cd \"recordStatusCd\", " +
									"record_status_time \"recordStatusTime\", " +
									"status_cd \"statusCd\", " +
									"status_time \"statusTime\", " +
									"to_time \"toTime\", " +
									"user_affiliation_txt \"userAffiliationTxt\" " +
									"FROM " + DataTables.PERSON_NAME_TABLE + " " +
									"WHERE person_uid = ?";
	public static final String SELECT_PERSON_NAME_UID = "SELECT person_UID \"personUid\" FROM " + DataTables.PERSON_NAME_TABLE + " WHERE person_UID = ?";
	public static final String DELETE_PERSON_NAMES = "DELETE FROM " + DataTables.PERSON_NAME_TABLE + " WHERE person_uid = ?";

	public PersonNameDAOImpl()
	{
	}

	/**
	  * This method creates a new PersonName record and returns the personUID for this person.
	  * @J2EE_METHOD  --  create
	  * @param personUID       the long
	  * @param coll            the Collection<Object>
	  * @throws NEDSSSystemException
	  **/
	public long create(long personUID, Collection<Object> coll) throws NEDSSSystemException
	{
		try{
			insertPersonNames(personUID, coll);
	
	
			return personUID;
		}catch(Exception ex){
			logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	  * This method is used to update a personName record.
	  * @J2EE_METHOD  --  store
	  * @param coll       the Collection<Object>
	  * @throws NEDSSSystemException
	  **/
	public void store(Collection<Object> coll) throws NEDSSSystemException
	{
		try{
			updatePersonNames(coll);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	  * This method loads the PersonNameDT objects for a given personUID.
	  * @J2EE_METHOD  --  load
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  * @throws NEDSSSystemException
	  **/
	public Collection<Object> load(long personUID) throws NEDSSDAOSysException,
		NEDSSSystemException
	{
		try{
			Collection<Object> pnColl = selectPersonNames(personUID);
			return pnColl;
		}catch(Exception ex){
			logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	  * This method is used to delete a personName record.
	  * @J2EE_METHOD  --  remove
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  * @throws NEDSSSystemException
	  **/
	public void remove(long personUID) throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			removePersonNames(personUID);
		}catch(Exception ex){
			logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	  * This method is used to determine is a personName record exists for a given personUID.
	  * @J2EE_METHOD  --  findByPrimaryKey
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  * @throws NEDSSSystemException
	  **/
	public Long findByPrimaryKey(long personUID) throws NEDSSDAOSysException,
		NEDSSSystemException
	{
		try{
			if (personNameExists(personUID))
				return (new Long(personUID));
			else
				logger.error("Primary key not found in PERSON_NAME_TABLE:"
				+ personUID);
				return null;
		}catch(Exception ex){
			logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}


	/**
	  * This method is used to determine if a personName record exists for a specific person.
	  * @J2EE_METHOD  --  personNameExists
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  * @throws NEDSSSystemException
	  **/
	@SuppressWarnings("unchecked")
	protected boolean personNameExists (long personUID)
						throws  NEDSSDAOSysException,
								NEDSSSystemException
	{
		boolean returnValue = false;
		PersonNameDT personNameDT = new PersonNameDT();
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
			arrayList = (ArrayList<Object> )preparedStmtMethod(personNameDT, arrayList, SELECT_PERSON_NAME_UID, NEDSSConstants.SELECT);
			if(arrayList.size()!=0)
				personNameDT = (PersonNameDT)arrayList.get(0);
			if(personNameDT!=null)
				if(personNameDT.getPersonUid()!=null)
					if (personNameDT.getPersonUid().equals(longPersonUid))
						returnValue = true; //only true if it exist
		}
		 catch (Exception ex) {
			logger.fatal("personUID: "+personUID+" Exception in personNameExists():  ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return returnValue;
	}


	/**
	  * This method is used to add a Collection<Object> of personName objects for a specific person.
	  * @J2EE_METHOD  --  insertPersonNames
	  * @param personUID       the long
	  * @param personNames      the Collection<Object>
	  * @throws NEDSSSystemException
	  **/
	private void insertPersonNames(long personUID, Collection<Object> personNames)
				throws NEDSSSystemException
	{
		Iterator<Object> anIterator = null;
		ArrayList<Object>  personList = (ArrayList<Object> )personNames;

		try
		{
			anIterator = personList.iterator();

			while(anIterator.hasNext())
			{
				logger.debug("Number of elements: " + personList.size());
				PersonNameDT personName = (PersonNameDT)anIterator.next();

				if (personName != null)
				{
				  insertPersonName(personUID, personName);
				  personName.setPersonUid(new Long(personUID));
				}
			}
		}
		catch(Exception ex)
		{
			logger.fatal("personUID: "+personUID+" Exception while inserting " +
					"person names into PERSON_NAME_TABLE: \n", ex);
			throw new NEDSSDAOSysException( ex.toString());
		}
		logger.debug("Done inserting all person names");
	}//end of inserting person names

	/**
	  * This method is used to add a personName entry for a specific person.
	  * @J2EE_METHOD  --  insertPersonName
	  * @param personUID       the long
	  * @param PersonNameDT      the PersonNameDT
	  * @throws NEDSSSystemException
	  **/
	private void insertPersonName(long personUID, PersonNameDT personName)
			throws NEDSSDAOSysException
	{

		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		try
		{
			// insert new person into person table
			if (personName != null)
			{
				arrayList.add(new Long(personUID));
				arrayList.add(personName.getPersonNameSeq());

				// In absence of any business rule I am putting default value as add
				if (personName.getAddReasonCd() == null)
				  arrayList.add("Add");
				else
				  arrayList.add(personName.getAddReasonCd());

				arrayList.add(personName.getAddTime());
				arrayList.add(personName.getAddUserId());
				arrayList.add(personName.getAsOfDate());
				arrayList.add(personName.getDurationAmt());
				arrayList.add(personName.getDurationUnitCd());
				arrayList.add(personName.getFirstNm());
				arrayList.add(personName.getFirstNm()); // for soundex
				arrayList.add(personName.getFromTime());
				arrayList.add(personName.getLastChgReasonCd());
				arrayList.add(personName.getLastChgTime());
				arrayList.add(personName.getLastChgUserId());
				arrayList.add(personName.getLastNm());
				arrayList.add(personName.getLastNm()); // for soundex
				arrayList.add(personName.getLastNm2());
				arrayList.add(personName.getLastNm2()); // for soundex
				arrayList.add(personName.getMiddleNm());
				arrayList.add(personName.getMiddleNm2());
				arrayList.add(personName.getNmDegree());
				arrayList.add(personName.getNmPrefix());
				arrayList.add(personName.getNmSuffix());
				arrayList.add(personName.getNmUseCd());
				arrayList.add(personName.getRecordStatusCd());
				arrayList.add(personName.getRecordStatusTime());

				if(personName.getStatusCd() == null)
					arrayList.add("A");
				else
					arrayList.add(personName.getStatusCd());

				if(personName.getStatusTime()==null)
					 arrayList.add(new Timestamp(new java.util.Date().getTime()));
				else
					arrayList.add(personName.getStatusTime());

				arrayList.add(personName.getToTime());
				arrayList.add(personName.getUserAffiliationTxt());

				// using the preparedStmtMethod from DAOBase
				// pass the dt in for the update
				// pass in an arraylist (ordered list)
				//        with all the parameters for the sql statement
				// pass in the sql you wish to use
				// update is the same as insert so it uses the same opperation constant
				int resultCount = ((Integer)preparedStmtMethod(personName, arrayList, INSERT_PERSON_NAME, NEDSSConstants.UPDATE)).intValue();
				if (resultCount != 1)
				{
					logger.error("Error: none or more than one person updated at a time, " + "resultCount = " + resultCount);
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("personUID: "+personUID+" Exception in updatePerson() = " + ex.getMessage(),ex);
			throw new NEDSSSystemException("Table Name : "+DataTables.PERSON_NAME_TABLE+ "  "+ex.toString(), ex);
		}
	}


	/**
	  * This method is used to update a Collection<Object> of personName objects for a specific person.
	  * @J2EE_METHOD  --  updatePersonNames
	  * @param personNames       the Collection<Object>
	  * @throws NEDSSSystemException
	  **/
	private void updatePersonNames(Collection<Object> personNames) throws NEDSSSystemException
	{
		Iterator<Object> anIterator = null;


		/**
		 * Updates person names
		 */

		if(personNames != null)
		{
			try
			{
				for(anIterator = personNames.iterator(); anIterator.hasNext(); )
				{
					PersonNameDT personName = (PersonNameDT)anIterator.next();
					if(personName == null)
					  logger.error("Error: Empty person name collection");
					logger.debug("Check dirty markers for Store person name: new = " + personName.isItNew() + ", dirty = " + personName.isItDirty());
					logger.debug("Store person name: UID = " + personName.getPersonUid() + "last name:" + personName.getLastNm());
					if(personName.isItNew()){
					  insertPersonName((personName.getPersonUid()).longValue(), personName);
					  personName.setItNew(false);
					  personName.setItDirty(false);
					}if(personName.isItDirty())
					  updatePersonName(personName);
					if (personName.isItDelete()){
					  personName.setStatusCd ("I");
					  updatePersonName(personName);
					}

				}
			}
			catch(Exception ex)
			{
				logger.fatal("Exception while updating " +
					"person names, \n", ex);
				throw new NEDSSDAOSysException(ex.toString());
			}
		}
	}//end of updating person name table

	/**
	  * This method is used to update a personName object for a specific person.
	  * @J2EE_METHOD  --  updatePersonName
	  * @param personName       the PersonNameDT
	  * @throws NEDSSSystemException
	  **/
	private void updatePersonName(PersonNameDT personName) throws NEDSSSystemException
	{

		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		 try
		{
			if (personName != null)
			{
				logger.debug("updating personName  ----- personName.getPersonUid() = " + personName.getPersonUid());
				arrayList.add(personName.getAddReasonCd());
				arrayList.add(personName.getAddTime());
				arrayList.add(personName.getAddUserId());
				arrayList.add(personName.getAsOfDate());
				arrayList.add(personName.getDurationAmt());
				arrayList.add(personName.getDurationUnitCd());
				arrayList.add(personName.getFirstNm());
				arrayList.add(personName.getFirstNm()); // for soundex
				arrayList.add(personName.getFromTime());
				arrayList.add(personName.getLastChgReasonCd());
				arrayList.add(personName.getLastChgTime());
				arrayList.add(personName.getLastChgUserId());
				arrayList.add(personName.getLastNm());
				arrayList.add(personName.getLastNm()); // for soundex
				arrayList.add(personName.getLastNm2());
				arrayList.add(personName.getLastNm2()); // for soundex
				arrayList.add(personName.getMiddleNm());
				arrayList.add(personName.getMiddleNm2());
				arrayList.add(personName.getNmDegree());
				arrayList.add(personName.getNmPrefix());
				arrayList.add(personName.getNmSuffix());
				arrayList.add(personName.getNmUseCd());
				arrayList.add(personName.getRecordStatusCd());
				arrayList.add(personName.getRecordStatusTime());

				if(personName.getStatusCd() == null)
					arrayList.add("A");
				else
					arrayList.add(personName.getStatusCd());

				if(personName.getStatusTime()==null)
					 arrayList.add(new Timestamp(new java.util.Date().getTime()));
				else
					arrayList.add(personName.getStatusTime());

				arrayList.add(personName.getToTime());
				arrayList.add(personName.getUserAffiliationTxt());
				arrayList.add(personName.getPersonUid());
				arrayList.add(personName.getPersonNameSeq());

			   // using the preparedStmtMethod from DAOBase
			   // pass the dt in for the update
			   // pass in an arraylist (ordered list)
			   //        with all the parameters for the sql statement
			   // pass in the sql you wish to use
			   // update is the same as insert so it uses the same opperation constant
			   int resultCount = ((Integer)preparedStmtMethod(personName, arrayList, UPDATE_PERSON_NAME, NEDSSConstants.UPDATE)).intValue();
				if (resultCount != 1)
				{
					logger.error("Error: none or more than one person updated at a time, " + "resultCount = " + resultCount);
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in updatePerson() = " + ex.getMessage(),ex);
			throw new NEDSSSystemException("Table Name : "+DataTables.PERSON_NAME_TABLE+ "  "+ex.toString(), ex);
		}
	}//end of updating person name table


	/**
	  * This method is used to retrieve personName objects for a specific person.
	  * @J2EE_METHOD  --  selectPersonNames
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  **/
	@SuppressWarnings("unchecked")
	private Collection<Object> selectPersonNames (long personUID) throws NEDSSDAOSysException
	{
		PersonNameDT personName = new PersonNameDT();
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
			return arrayList = (ArrayList<Object> )preparedStmtMethod(personName, arrayList, SELECT_PERSON_NAMES, NEDSSConstants.SELECT);
		}
		 catch (Exception ex) {
			logger.fatal("personUID: "+personUID+" Exception in selectPerson:  ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	  * This method is used to remove a personName object from a specific person.
	  * @J2EE_METHOD  --  removePersonNames
	  * @param personUID       the long
	  * @throws NEDSSDAOSysException
	  **/
	private void removePersonNames (long personUID) throws NEDSSDAOSysException
	{
		PersonNameDT personNameDT = new PersonNameDT();
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
			preparedStmtMethod(personNameDT, arrayList, DELETE_PERSON_NAMES, NEDSSConstants.UPDATE);
		}
		 catch (Exception ex) {
			logger.fatal("personUID: "+personUID+" Exception in removePerson:  ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
/*
	public static void main(String[] strg)
	{
		logger.setLogLevel(6);

		PersonNameDAOImpl personNameDAOImpl = new PersonNameDAOImpl();
		PersonNameDT personNameDT = new PersonNameDT();
		long personUidLong = 0;
		try
		{
			//**** sql server personuid
			personUidLong = 470006000;
			//int versionctrlnbr = 5;

			//**** oracle server personuid
			//personUidLong = 310326053;

//			  personNameDT.setFirstNm("Bla");
//			  personNameDT.setLastNm("Blabla");
//			  personNameDT.setPersonNameSeq(new Integer(1));
//			  personNameDT.setStatusCd("A");
//			  personNameDT.setStatusTime(new Timestamp(new java.util.Date().getTime()));

			logger.debug("-----------start-----------------");
			//personNameDAOImpl.insertPersonName(personUidLong, personNameDT);
//			  personNameDAOImpl.removePersonNames(personUidLong);
			ArrayList<Object>  arrayList = (ArrayList<Object> )personNameDAOImpl.selectPersonNames(personUidLong);
			logger.debug("arrayList.size() = " + arrayList.size());
			Iterator<Object> iterator = arrayList.iterator();
			while(iterator.hasNext())
			{
				personNameDT = (PersonNameDT)iterator.next();
				logger.debug("----------------------------");
				logger.debug("personNameDT.getPersonUid() = " + personNameDT.getPersonUid());
				logger.debug("personNameDT.getFirstNm() = " + personNameDT.getFirstNm());
				logger.debug("personNameDT.getLastNm() = " + personNameDT.getLastNm());
				logger.debug("personNameDT.getAddTime() = " + personNameDT.getAddTime());
				logger.debug("personNameDT.getRecordStatusTime() = " + personNameDT.getRecordStatusCd());
				logger.debug("personNameDT.getLocalId() = " + personNameDT.getPersonNameSeq());
				logger.debug("----------------------------");
			}

				personNameDT.setAddReasonCd("Add");
				personNameDT.setAddTime(new Timestamp(new java.util.Date().getTime()));
				personNameDT.setStatusTime(new Timestamp(new java.util.Date().getTime()));

				personNameDT.setFirstNm("John");
				personNameDT.setLastNm("Paul");
				personNameDAOImpl.updatePersonName(personNameDT);
			logger.debug("-----------finish-----------------");

		}
		 catch (Exception ex) {
			logger.fatal("Exception in main():  ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}// end of main - for testing
*/
}//end of PersonNameDAOImpl class
