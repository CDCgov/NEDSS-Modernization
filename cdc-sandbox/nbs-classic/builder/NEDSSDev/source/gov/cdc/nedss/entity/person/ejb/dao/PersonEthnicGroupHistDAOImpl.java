

/**
* Name:		PersonEthnicGroupHistDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for old the
*               PersonEthnicGroupHist collection inserting into PersonEthnicGroupHist table.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Ning Peng
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
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PersonEthnicGroupHistDAOImpl extends BMPBase
{
    static final LogUtils logger = new LogUtils(PersonEthnicGroupHistDAOImpl.class.getName());
    private int versionCtrlNbr = -1;
    private long defaultChangeUserId = 0;

    private final String INSERT_PERSON_ETHNIC_GROUP_HIST
      = "INSERT INTO Person_ethnic_group_hist ( "
      + "person_uid, "
      + "ethnic_group_cd, "
      + "version_ctrl_nbr, "
      + "add_reason_cd, "
      + "add_time, "
      + "add_user_id, "
      + "ethnic_group_desc_txt, "
      + "last_chg_reason_cd, "
      + "last_chg_time, "
      + "last_chg_user_id, "
      + "record_status_cd, "
      + "record_status_time, "
      + "user_affiliation_txt "
      + " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
       private final String SELECT_PERSON_ETHNIC_GROUPS_HIST = "SELECT person_uid \"personUid\", ethnic_group_cd \"ethnicGroupCd\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", ethnic_group_desc_txt \"ethnicGroupDescTxt\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", user_affiliation_txt \"userAffiliationTxt\" FROM " + DataTables.PERSON_ETHNIC_GROUP_TABLE_HIST + " WHERE person_uid = ? AND version_ctrl_nbr = ? ";
/*
    //for testing
    NedssUtils nu = new NedssUtils();
    Connection dbConnection = nu.getTestConnection();
*/
    /**
     * Default Constructor
     */
     public PersonEthnicGroupHistDAOImpl()
      throws NEDSSDAOSysException, NEDSSSystemException
    {
    }
    /**
     * Constructor to be used when initializing object with versionCtrlNbr to be used.
     * @param versionCtrlNbr  The versionCtrlNbr to be used when inserting a Person Ethnic Group History Record
     *
     */
    public PersonEthnicGroupHistDAOImpl(int versionCtrlNbr)
      throws NEDSSDAOSysException, NEDSSSystemException
    {
    	this.versionCtrlNbr = versionCtrlNbr;
    }

  /**
   * Description:
   *    This function takes a collection of DTs and stores them into history table
   * @param coll   A collection of DTs
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
   * @param obj  The DT object to be stored.
   * @return void
   */
    public void store(Object obj)
      throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        PersonEthnicGroupDT dt = (PersonEthnicGroupDT)obj;
	        if(dt == null)
	           throw new NEDSSSystemException("Error: try to store null PersonEthnicGroupDT object.");
	          insertPersonEthnicGroupHist(dt);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Loads the record represented by the personUid and versionCtrlNbr passed in.
     * @param personUid  The person uid to be searched for.
     * @param versionCtrlNbr  The versionCtrlNbr associated with the person uid.
     * @return Collection<Object>   Collection<Object> representing the Person Ethnic Group representing this person uid
     */
    public Collection<Object> load(Long personUid, Integer versionCtrlNbr)
	throws NEDSSSystemException, NEDSSSystemException
    {
    	try{
	        Collection<Object> pegColl = selectPersonEthnicGroupsHist(personUid.longValue(), versionCtrlNbr.intValue());
	        return pegColl;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Private method to be used by the load(...) method.
     * @param personUid  The person uid to be searched for.
     * @param versionCtrlNbr  The versionCtrlNbr representing the history seq.
     * @return Collection<Object>  Returns a collection of PersonEthnicGroupDT objects.
     */
    private Collection<Object> selectPersonEthnicGroupsHist (long personUID, int versionCtrlNbr)
	throws NEDSSSystemException, NEDSSSystemException
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
                            "for selectPersonEthnicGroups hist ", nsex);
            throw new NEDSSSystemException( nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(this.SELECT_PERSON_ETHNIC_GROUPS_HIST);
            preparedStmt.setLong(1, personUID);
	    preparedStmt.setLong(2, versionCtrlNbr);
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
        	logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSSystemException("SQLException while selecting " +
                            "person race collection hist; id = " + personUID + " :\n" + se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Exception while handling result set in selecting " +
                            "person race collection hist; id = " + personUID, rsuex);
            throw new NEDSSSystemException( rsuex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "person race collection hist; id = " + personUID , ex);
            throw new NEDSSSystemException( ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }


    /**
     * Private method to be used to insert the PersonEthnicGroupDT dt object.
     * @param dt  The PersonEthnicGroupDT to be inserted.
     * @return void
     */
    private void insertPersonEthnicGroupHist(PersonEthnicGroupDT dt)
      throws NEDSSDAOSysException, NEDSSSystemException
    {
     //  if( dt.getPersonUid() != null)
      // {
          int resultCount = 0;
          Connection dbConnection = null;
          PreparedStatement preparedStmt = null;


           int i = 1;
           try
          {

            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(this.INSERT_PERSON_ETHNIC_GROUP_HIST);
            preparedStmt.setLong(i++, dt.getPersonUid().longValue()); //1
            /**
             * Fix for https://nbscentral.sramanaged.com//redmine/issues/12747
             * 
             */
            if(dt.getEthnicGroupCd()==null) {
            	logger.debug("EthnicGroupCd was null and updated to empty value so that merge patient does not break. This is for patient uid:-"+dt.getPersonUid());
            	dt.setEthnicGroupCd("");
            }
            
            preparedStmt.setString(i++, dt.getEthnicGroupCd()); //2
    	    preparedStmt.setInt(i++, versionCtrlNbr);//3
            preparedStmt.setString(i++, dt.getAddReasonCd()); //4
            if( dt.getAddTime()==null)
                preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getAddTime() ); //5
              if( dt.getAddUserId()==null)
                  preparedStmt.setNull(i++, Types.BIGINT );
              else
                  preparedStmt.setLong(i++, dt.getAddUserId().longValue() ); //6
           //   preparedStmt.setString(i++, dt.getLastChgReasonCd());
            //  preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
              preparedStmt.setString(i++, dt.getEthnicGroupDescTxt());//7
              preparedStmt.setString(i++, dt.getLastChgReasonCd()); //8
              if( dt.getLastChgTime()==null)
                  preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
              else
                  preparedStmt.setTimestamp(i++, dt.getLastChgTime() );//9
             // preparedStmt.setLong(i++, defaultChangeUserId);

              if( dt.getLastChgUserId()==null)
                  preparedStmt.setNull(i++, Types.BIGINT );
              else
                  preparedStmt.setLong(i++, dt.getLastChgUserId().longValue() ); //10
              preparedStmt.setString(i++, dt.getRecordStatusCd()); //11
              if( dt.getRecordStatusTime()==null)
                  preparedStmt.setNull(i++, Types.TIMESTAMP);
              else
                  preparedStmt.setTimestamp(i++, dt.getRecordStatusTime() ); //12
              preparedStmt.setString(i++, dt.getUserAffiliationTxt()); //13
              resultCount = preparedStmt.executeUpdate();
              if ( resultCount != 1 )
             {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
             }
          }
          catch(SQLException se)
          {
        	  logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          }
          finally
          {
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);
          }
       // }
    }

}

