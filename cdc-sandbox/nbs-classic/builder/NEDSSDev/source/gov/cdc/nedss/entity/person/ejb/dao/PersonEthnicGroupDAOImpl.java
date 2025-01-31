/**
* Name:		PersonEthnicGroupDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               PersonEthnicGroup value object in the Person entity bean.
*               This class encapsulates all the JDBC calls made by the PersonEJB
*               for a PersonEthnicGroup object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of PersonEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.entity.person.ejb.dao;


import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;




public class PersonEthnicGroupDAOImpl extends BMPBase
{
    static final LogUtils logger = new LogUtils(PersonEthnicGroupDAOImpl.class.getName());

    public static final String INSERT_PERSON_ETHNIC_GROUP = "INSERT INTO " + DataTables.PERSON_ETHNIC_GROUP_TABLE + "(person_uid, ethnic_group_cd, add_reason_cd, add_time, add_user_id, ethnic_group_desc_txt, last_chg_reason_cd, last_chg_time, last_chg_user_id, record_status_cd, record_status_time, user_affiliation_txt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_PERSON_ETHNIC_GROUP = "UPDATE " + DataTables.PERSON_ETHNIC_GROUP_TABLE + " set add_reason_cd = ?, add_time = ?, add_user_id = ?, ethnic_group_desc_txt = ?, last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, record_status_cd = ?, record_status_time = ?, user_affiliation_txt = ? WHERE person_uid = ? AND ethnic_group_cd = ?";
    public static final String SELECT_PERSON_ETHNIC_GROUPS = "SELECT person_uid \"personUid\", ethnic_group_cd \"ethnicGroupCd\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", ethnic_group_desc_txt \"ethnicGroupDescTxt\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", user_affiliation_txt \"userAffiliationTxt\" FROM " + DataTables.PERSON_ETHNIC_GROUP_TABLE + " WHERE person_uid = ?";
    public static final String SELECT_PERSON_ETHNIC_GROUP_UID = "SELECT person_UID FROM " + DataTables.PERSON_ETHNIC_GROUP_TABLE + " WHERE person_UID = ?";
    public static final String DELETE_PERSON_ETHNIC_GROUPS = "DELETE FROM " + DataTables.PERSON_ETHNIC_GROUP_TABLE + " WHERE person_uid = ?";

    public PersonEthnicGroupDAOImpl()
    {
    }

    /**
      * This method creates a new EthnicGroup record and returns the personUID for this person.
      * @J2EE_METHOD  --  create
      * @param personUID       the long
      * @param coll            the Collection<Object>
      * @throws NEDSSSystemException
      **/
    public long create(long personUID, Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
			insertPersonEthnicGroups(personUID, coll);
		
			return personUID;
    	}catch(Exception ex){
    		logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method is used to update a personEthnicGroup record.
      * @J2EE_METHOD  --  store
      * @param coll       the Collection<Object>
      * @throws NEDSSSystemException
      **/
    public void store(Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
    		updatePersonEthnicGroups(coll);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method is used to delete a personEthnicGroup record.
      * @J2EE_METHOD  --  remove
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    public void remove(long personUID) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
    		removePersonEthnicGroups(personUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method loads the PersonEthnicGroupDT objects for a given personUID.
      * @J2EE_METHOD  --  load
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    public Collection<Object> load(long personUID) throws NEDSSDAOSysException,
        NEDSSSystemException
    {
    	try{
			Collection<Object> pegColl = selectPersonEthnicGroups(personUID);
			return pegColl;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method is used to determine if an EthnicGroup record exists for a given personUID.
      * @J2EE_METHOD  --  findByPrimaryKey
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    public Long findByPrimaryKey(long personUID) throws NEDSSDAOSysException,
		NEDSSSystemException
    {
    	try{
			if (personEthnicGroupExists(personUID))
			    return (new Long(personUID));
			else
			    logger.error("No ethnic group found for THIS primary key :" + personUID);
			    return null;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }


    /**
      * This method is used to determine if a personEthicGroup record exists for a specific person.
      * @J2EE_METHOD  --  personEthnicGroupExists
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    protected boolean personEthnicGroupExists (long personUID) throws NEDSSSystemException
    {
	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	ResultSet resultSet = null;
	boolean returnValue = false;

	try
	{
	    dbConnection = getConnection();
	    preparedStmt = dbConnection.prepareStatement(SELECT_PERSON_ETHNIC_GROUP_UID);
	    logger.debug("personID = " + personUID);
	    preparedStmt.setLong(1, personUID);
	    resultSet = preparedStmt.executeQuery();

	    if (!resultSet.next())
	    {
		returnValue = false;
	    }
	    else
	    {
		personUID = resultSet.getLong(1);
		returnValue = true;
	    }
	}
	catch(SQLException sex)
	{
	    logger.fatal("SQLException while checking for an"
			    + " existing person's uid in ethnic group table-> " + personUID , sex);
	    throw new NEDSSDAOSysException( sex.toString());
	}
	catch(NEDSSSystemException nsex)
	{
	    logger.fatal("Exception while getting dbConnection for checking for an"
			    + " existing person's uid -> " + personUID , nsex);
	    throw new NEDSSDAOSysException( nsex.toString());
	}
	finally
	{
	    closeResultSet(resultSet);
	    closeStatement(preparedStmt);
	    releaseConnection(dbConnection);
	}
	return returnValue;
    }


    /**
      * This method is used to add a Collection<Object> of personEthnicGroup objects for a specific person.
      * @J2EE_METHOD  --  insertPersonEthnicGroups
      * @param personUID       the long
      * @param personEthnicGroups      the Collection<Object>
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    private void insertPersonEthnicGroups(long personUID, Collection<Object> personEthnicGroups)
		throws NEDSSDAOSysException, NEDSSSystemException
    {
	Iterator<Object> anIterator = null;

	try
	{
	    /**
	     * Inserts person ethnic groups
	     */

	     for (anIterator = personEthnicGroups.iterator(); anIterator.hasNext(); )
	     {
		PersonEthnicGroupDT personEthnicGroup = (PersonEthnicGroupDT)anIterator.next();
		if (personEthnicGroup != null) {
		//personEthnicGroup.setRecordStatusCd("A");
		insertPersonEthnicGroup(personUID, personEthnicGroup);
		}
		//throw new NEDSSPersonDAOAppException("Empty person ethnic group collection");
		personEthnicGroup.setPersonUid(new Long(personUID));
//                personEthnicGroup.setItNew(false);
  //              personEthnicGroup.setItDirty(false);
	      }
	}
	catch(Exception ex)
	{
	    logger.fatal("personUID: "+personUID+" Exception while inserting " +
			"person EthnicGroups into PERSON_ETHNIC_GROUP_TABLE: \n", ex);
	    throw new NEDSSDAOSysException(ex.toString());
	}
	logger.debug("Done inserting person ethnic groups");
    }//end of inserting person EthnicGroups

    /**
      * This method is used to add a personEthnicGroup entry for a specific person.
      * @J2EE_METHOD  --  insertPersonEthnicGroup
      * @param personUID       the long
      * @param personEthnicGroup      the PersonEthnicGroupDT
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    private void insertPersonEthnicGroup(long personUID, PersonEthnicGroupDT personEthnicGroup)
		throws NEDSSDAOSysException, NEDSSSystemException
    {
	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	int resultCount = 0;

	try
	{
	    dbConnection = getConnection();
	}
	catch(NEDSSSystemException nsex)
	{
	    logger.fatal("Error obtaining db connection " +
		"while inserting person ethnic groups", nsex);
	    throw new NEDSSSystemException(nsex.toString());
	}

	try
	{
	    /**
	     * Inserts a person ethnic group
	     */

	     preparedStmt = dbConnection.prepareStatement(INSERT_PERSON_ETHNIC_GROUP);

	     int i = 1;

    logger.debug("person ethnic group = " + personEthnicGroup);
	     preparedStmt.setLong(i++, personUID);
	     preparedStmt.setString(i++, personEthnicGroup.getEthnicGroupCd());
	     preparedStmt.setString(i++, personEthnicGroup.getAddReasonCd());
	     preparedStmt.setTimestamp(i++, personEthnicGroup.getAddTime());
	     if(personEthnicGroup.getAddUserId() == null)
		preparedStmt.setNull(i++, Types.INTEGER);
	     else
		preparedStmt.setLong(i++, (personEthnicGroup.getAddUserId()).longValue());
	     preparedStmt.setString(i++, personEthnicGroup.getEthnicGroupDescTxt());
	     preparedStmt.setString(i++, personEthnicGroup.getLastChgReasonCd());
	     preparedStmt.setTimestamp(i++, personEthnicGroup.getLastChgTime());
	     if(personEthnicGroup.getLastChgUserId() == null)
		preparedStmt.setNull(i++, Types.INTEGER);
	     else
		preparedStmt.setLong(i++, (personEthnicGroup.getLastChgUserId()).longValue());
	     preparedStmt.setString(i++, personEthnicGroup.getRecordStatusCd());
	     preparedStmt.setTimestamp(i++, personEthnicGroup.getRecordStatusTime());
	     preparedStmt.setString(i++, personEthnicGroup.getUserAffiliationTxt());

	     resultCount = preparedStmt.executeUpdate();

	     if (resultCount != 1)
		    logger.error
			    ("Error: none or more than one person ethnic groups inserted at a time, " +
			     "resultCount = " + resultCount);
	    else
	    {
		personEthnicGroup.setItNew(false);
		personEthnicGroup.setItDirty(false);
	    }
	}
	catch(SQLException sex)
	{
		logger.fatal("SQLException while inserting " +
			"person EthnicGroups into PERSON_ETHNIC_GROUP_TABLE: \n", sex);
		throw new NEDSSDAOSysException("Table Name : "+ DataTables.PERSON_ETHNIC_GROUP_TABLE + "  For personUID: "+personUID+"  "+sex.toString(), sex );
	}
	finally
	{
	    //closeResultSet(resultSet);
	    closeStatement(preparedStmt);
	    releaseConnection(dbConnection);
	}
logger.debug("Done inserting a person ethnic group");
    }//end of inserting a person EthnicGroup


    /**
      * This method is used to update a Collection<Object> of personEthnicGroup objects for a specific person.
      * @J2EE_METHOD  --  updatePersonEthnicGroups
      * @param personEthnicGroups       the Collection<Object>
      * @throws NEDSSSystemException
      **/
    private void updatePersonEthnicGroups (Collection<Object> personEthnicGroups) throws NEDSSSystemException
    {
	PersonEthnicGroupDT personEthnicGroup = null;
	Iterator<Object> anIterator = null;

	if(personEthnicGroups != null)
	{
	    /**
	     * Updates person ethnic groups
	     */
	    try
	    {
	    	logger.debug("Updating person ethinic groups");
	    	for(anIterator = personEthnicGroups.iterator(); anIterator.hasNext(); )
			{
			    personEthnicGroup = (PersonEthnicGroupDT)anIterator.next();
			    if(personEthnicGroup == null)
			    logger.error("Error: Empty person ethnic group collection");
	
			    if(personEthnicGroup.isItNew()) {
			      //personEthnicGroup.setRecordStatusCd("A");
			      insertPersonEthnicGroup((personEthnicGroup.getPersonUid()).longValue(), personEthnicGroup);
			    }
			    if(personEthnicGroup.isItDirty()){
			      //personEthnicGroup.setRecordStatusCd("A");
			      updatePersonEthnicGroup(personEthnicGroup);
			    }
			    if(personEthnicGroup.isItDelete()){
			      //personEthnicGroup.setRecordStatusCd("I"); // Change to ststus cd once database supports it.
			      updatePersonEthnicGroup(personEthnicGroup);
			    }
			}
	    }
	    catch(Exception ex)
	    {
		logger.fatal("Exception while updating " +
		    "person ethnic groups, \n", ex);
		throw new NEDSSDAOSysException(ex.toString());
	    }
	}
    }//end of updating person ethnic group table

    /**
      * This method is used to update a personEthnicGroup object for a specific person.
      * @J2EE_METHOD  --  updatePersonEthnicGroup
      * @param personEthnicGroup       the PersonEthnicGroupDT
      * @throws NEDSSSystemException
      **/
    private void updatePersonEthnicGroup(PersonEthnicGroupDT personEthnicGroup)
	      throws NEDSSSystemException
    {
	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	int resultCount = 0;

	/**
	 * Updates a person ethnic group
	 */

	if(personEthnicGroup != null)
	{
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating a person ethnic group", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

	    try
	    {
		preparedStmt = dbConnection.prepareStatement(UPDATE_PERSON_ETHNIC_GROUP);

		int i = 1;

		logger.debug("person ethnic group = " + personEthnicGroup.getEthnicGroupCd());
		preparedStmt.setString(i++, personEthnicGroup.getAddReasonCd());
		preparedStmt.setTimestamp(i++, personEthnicGroup.getAddTime());
		if (personEthnicGroup.getAddUserId() == null)
		  preparedStmt.setNull(i++, Types.INTEGER);
		else
		  preparedStmt.setLong(i++, (personEthnicGroup.getAddUserId()).longValue());
		preparedStmt.setString(i++, personEthnicGroup.getEthnicGroupDescTxt());
		preparedStmt.setString(i++, personEthnicGroup.getLastChgReasonCd());
		preparedStmt.setTimestamp(i++, personEthnicGroup.getLastChgTime());
		if (personEthnicGroup.getLastChgUserId() == null)
		  preparedStmt.setNull(i++, Types.INTEGER);
		else
		  preparedStmt.setLong(i++, (personEthnicGroup.getLastChgUserId()).longValue());
		preparedStmt.setString(i++, personEthnicGroup.getRecordStatusCd());
		preparedStmt.setTimestamp(i++, personEthnicGroup.getRecordStatusTime());
		preparedStmt.setString(i++, personEthnicGroup.getUserAffiliationTxt());
		if (personEthnicGroup.getPersonUid() == null)
		  preparedStmt.setNull(i++, Types.INTEGER);
		else
		  preparedStmt.setLong(i++, (personEthnicGroup.getPersonUid()).longValue());
		preparedStmt.setString(i++, personEthnicGroup.getEthnicGroupCd());

		resultCount = preparedStmt.executeUpdate();
		logger.debug("Done updating a person ethnic group");
		if (resultCount != 1)
		logger.error
			    ("Error: none or more than one person ethnic group updated at a time, " +
			      "resultCount = " + resultCount);
	    }
	    catch(SQLException sex)
	    {
		logger.fatal("SQLException while updating " +
		    " a person ethnic group, \n", sex);
		throw new NEDSSDAOSysException("Table Name : "+ DataTables.PERSON_ETHNIC_GROUP_TABLE +"  "+sex.toString(), sex);
	    }
	    catch(Exception ex)
	    {
		logger.fatal("Exception while updating " +
		    " a person ethnic group, \n", ex);
		throw new NEDSSDAOSysException( ex.toString() );
	    }
	    finally
	    {
		closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	    }
	}
    }//end of updating a person ethnic group

    /**
      * This method is used to retrieve personEthnicGroup objects for a specific person.
      * @J2EE_METHOD  --  selectPersonEthnicGroups
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      **/
    private Collection<Object> selectPersonEthnicGroups (long personUID) throws NEDSSDAOSysException
    {

	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	ResultSet resultSet = null;
	ResultSetMetaData resultSetMetaData = null;
	ResultSetUtils resultSetUtils = new ResultSetUtils();
	PersonEthnicGroupDT personEthnicGroup = new PersonEthnicGroupDT();

	try
	{
	    dbConnection = getConnection();
	}
	catch(NEDSSSystemException nsex)
	{
	    logger.fatal("SQLException while obtaining database connection " +
			    "for selectPersonEthnicGroups ", nsex);
	    throw new NEDSSSystemException( nsex.toString());
	}

	/**
	 * Selects person ethnic groups
	 */
	try
	{
	    preparedStmt = dbConnection.prepareStatement(SELECT_PERSON_ETHNIC_GROUPS);
	    preparedStmt.setLong(1, personUID);
	    resultSet = preparedStmt.executeQuery();
	    resultSetMetaData = resultSet.getMetaData();
	    ArrayList<Object>  pegList = new ArrayList<Object> ();
	    ArrayList<Object>  reSetList = new ArrayList<Object> ();

	    pegList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, personEthnicGroup.getClass(), pegList);

	    for(Iterator<Object> anIterator = pegList.iterator(); anIterator.hasNext(); )
	    {
		PersonEthnicGroupDT reSetEthnicGroup = (PersonEthnicGroupDT)anIterator.next();
		reSetEthnicGroup.setItNew(false);
		reSetEthnicGroup.setItDirty(false);

		reSetList.add(reSetEthnicGroup);
	    }
	    logger.debug("return person ethnic group collection");
	    return reSetList;
	}
	catch(SQLException se)
	{
		logger.fatal("Exception  = "+se.getMessage(), se);
	    throw new NEDSSDAOSysException("SQLException while selecting " +
			    "person race collection; id = " + personUID + " :\n" + se.getMessage());
	}
	catch(ResultSetUtilsException rsuex)
	{
	    logger.fatal("Exception while handling result set in selecting " +
			    "person race collection; id = " + personUID, rsuex);
	    throw new NEDSSDAOSysException( rsuex.toString());
	}
	catch(Exception ex)
	{
	    logger.fatal("Exception while selecting " +
			    "person race collection; id = " + personUID , ex);
	    throw new NEDSSDAOSysException( ex.toString());
	}
	finally
	{
	    closeResultSet(resultSet);
	    closeStatement(preparedStmt);
	    releaseConnection(dbConnection);
	}
    }//end of selecting person ethnic groups

    /**
      * This method is used to remove a personEthnicGroup object from a specific person.
      * @J2EE_METHOD  --  removePersonEthnicGroups
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      **/
    private void removePersonEthnicGroups (long personUID) throws NEDSSDAOSysException
    {
	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	int resultCount = 0;

	try
	{
	    dbConnection = getConnection();
	}
	catch(NEDSSSystemException nsex)
	{
	    logger.fatal("SQLException while obtaining database connection " +
			    "for deleting person ethnic groups " , nsex);
	    throw new NEDSSSystemException( nsex.toString());
	}

	/**
	 * Deletes person ethnic groups
	 */
	try
	{
	    preparedStmt = dbConnection.prepareStatement(DELETE_PERSON_ETHNIC_GROUPS);
	    preparedStmt.setLong(1, personUID);
	    resultCount = preparedStmt.executeUpdate();

	    if (resultCount != 1)
	    {
		logger.error
		("Error: cannot delete person ethnic groups from PERSON_ETHNIC_GROUP_TABLE!! resultCount = " +
		 resultCount);
	    }
	}
	catch(SQLException sex)
	{
	    logger.fatal("SQLException while removing " +
			    "person ethnic groups; id = " + personUID , sex);
	    throw new NEDSSDAOSysException( sex.toString());
	}
	finally
	{
	    closeStatement(preparedStmt);
	    releaseConnection(dbConnection);
	}
    }//end of removing person races


}//end of PersonEthnicGroupDAOImpl class
