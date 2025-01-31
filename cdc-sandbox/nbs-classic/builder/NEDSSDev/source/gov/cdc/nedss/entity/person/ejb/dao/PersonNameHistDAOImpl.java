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
 * Name:		PersonNameHisDAOImpl.java
 * Description:	This is the implementation of NEDSSDAOInterface for old the
 *              PersonName value object inserting into PersonNameHist table.
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author      NEDSS Development Team
 * @version	    1.0
 **/
public class PersonNameHistDAOImpl extends DAOBase
{
    static final LogUtils logger = new LogUtils(PersonNameHistDAOImpl.class.getName());

    private int versionCtrlNbr = -1;

    private final String INSERT_PERSON_NAME_HIST
      = "INSERT INTO Person_name_hist ( "
      + "person_uid, "
      + "person_name_seq, "
      + "version_ctrl_nbr, "
      + "add_reason_cd, "
      + "add_time, "
      + "add_user_id, "
      + "default_nm_ind, "
      + "duration_amt, "
      + "duration_unit_cd, "
      + "first_nm, "
      + "first_nm_sndx, "
      + "from_time, "
      + "last_chg_reason_cd, "
      + "last_chg_time, "
      + "last_chg_user_id, "
      + "last_nm, "
      + "last_nm_sndx, "
      + "last_nm2, "
      + "last_nm2_sndx, "
      + "middle_nm, "
      + "middle_nm2, "
      + "nm_degree, "
      + "nm_prefix, "
      + "nm_suffix, "
      + "nm_use_cd, "
      + "record_status_cd, "
      + "record_status_time, "
      + "status_cd, "
      + "status_time, "
      + "to_time, "
      + "user_affiliation_txt, "
      + "as_of_date "
      + " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private final String SELECT_PERSON_NAMES_HIST = "SELECT person_uid \"personUid\", person_name_seq \"personNameSeq\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\", first_nm \"firstNm\", first_nm_sndx \"firstNmSndx\", from_time \"fromTime\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", last_nm \"lastNm\", last_nm_sndx \"lastNmSndx\", last_nm2 \"lastNm2\", last_nm2_sndx \"lastNm2Sndx\", middle_nm \"middleNm\", middle_nm2 \"middleNm2\", nm_degree \"nmDegree\", nm_prefix \"nmPrefix\", nm_suffix \"nmSuffix\", nm_use_cd \"nmUseCd\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", status_cd \"statusCd\", status_time \"statusTime\", to_time \"toTime\", user_affiliation_txt \"userAffiliationTxt\", as_of_date \"asOfDate\" FROM " + DataTables.PERSON_NAME_TABLE_HIST + " WHERE person_uid = ? AND version_ctrl_nbr = ? ";
    //for the time being, using default change_user_id for testing
    private long defaultChangeUserId = 0;
/*
    //for testing
    NedssUtils nu = new NedssUtils();
    Connection dbConnection = nu.getTestConnection();
*/
    /**
     * Default Constructor
     */
    public PersonNameHistDAOImpl()
      throws NEDSSDAOSysException, NEDSSSystemException
    {
    }
    /**
     * Contructor to initialize the class attribute versionCtrlNbr
     * @param versionCtrlNbr : int  The versionCtrlNbr to be used to
     *                              initialize the versionCtrlNbr class attribute.
     */
    public PersonNameHistDAOImpl(int versionCtrlNbr)
      throws NEDSSDAOSysException, NEDSSSystemException
    {
       this.versionCtrlNbr = versionCtrlNbr;
    }

    /**
     * Loads a Collection<Object> of PersonNames
     * @param personUid : Long  PersonUid to be searched for.
     * @param versionCtrlNbr : Integer  VersionCtrlNbr representing the version of the record to be selected.
     */
    public Collection<Object> load(Long personUid, Integer personHistSeq)
        throws NEDSSSystemException, NEDSSSystemException
    {
    	try{
	        Collection<Object> pnColl = selectPersonNamesHist(personUid.longValue(), personHistSeq.intValue());
	        return pnColl;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

  /**
   * Description:
   *    This function takes a collection of DTs and stores them into history table
   * @param
   *    Collection<Object>: a collection of DTs
   * @return
   *    void
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
   * @param
   *    Object: a DT
   * @return
   *    void
   */
    public void store(Object obj)
      throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        PersonNameDT dt = (PersonNameDT)obj;
	        if(dt == null)
	           throw new NEDSSSystemException("Error: try to store null PersonNameDT object.");
	          insertPersonNameHist(dt);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}

    }

    /**
     * Returns a collection of PersonNameDt objects
     * @param personUid : long The person uid to be used in loading the personNameDt objects
     * @param versionCtrlNbr : int The versionCtrlNbr representing the record to be selected.
     * @return Collection<Object>  The collection of PersonNameDT objects.
     */
    @SuppressWarnings("unchecked")
	private Collection<Object> selectPersonNamesHist (long personUID, int versionCtrlNbr) throws NEDSSDAOSysException,
		NEDSSSystemException, NEDSSSystemException
    {
        PersonNameDT personName = new PersonNameDT();
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        arrayList.add(new Long(personUID));
        arrayList.add(new Integer(versionCtrlNbr));
        try
        {
            // using the preparedStmtMethod from DAOBase
            // pass the dt in for the update
            // pass in an arraylist (ordered list)
            //        with all the parameters for the sql statement
            // pass in the sql you wish to use
            // pass in queryType constant so it will return a RootDTInterface
            return arrayList = (ArrayList<Object> )preparedStmtMethod(personName, arrayList, SELECT_PERSON_NAMES_HIST, NEDSSConstants.SELECT);
        }
         catch (Exception ex) {
            logger.fatal("Exception in selectPerson:  ERROR = " + ex.getMessage(),ex);
            throw new NEDSSSystemException(ex.toString());
        }
/*
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        PersonNameDT personName = new PersonNameDT();
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
           dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectPersonNamesHist ", nsex);
            throw new NEDSSSystemException( nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(this.SELECT_PERSON_NAMES_HIST);
            preparedStmt.setLong(1, personUID);
	    preparedStmt.setLong(2, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  pnList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            pnList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, personName.getClass(), pnList);
            for(Iterator<Object> anIterator = pnList.iterator(); anIterator.hasNext(); )
            {
                PersonNameDT reSetName = (PersonNameDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                reSetList.add(reSetName);
            }

            logger.debug("return person name history collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            throw new NEDSSSystemException("SQLException while selecting " +
                            "person name history collection; uid = " + personUID + " :\n" + se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while selecting person names.", rsuex);
            throw new NEDSSSystemException(rsuex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection " +
                  "person names; uid = " + personUID, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
*/
    }

    /**
     * Inserts a record into the person name table.
     * @param dt : PeronNameDT
     * @return void
     */
    private void insertPersonNameHist(PersonNameDT personName)
      throws NEDSSDAOSysException, NEDSSSystemException
    {
        if (personName.getPersonUid() != null)
        {


        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        try
        {
            // insert new person into person table
            if (personName != null)
            {
                arrayList.add(personName.getPersonUid()); //1
                arrayList.add(personName.getPersonNameSeq());//2
                arrayList.add(new Long(versionCtrlNbr));//3
                // In absence of any business rule I am putting default value as add
                if (personName.getAddReasonCd() == null)//4
                  arrayList.add("Add");
                else
                  arrayList.add(personName.getAddReasonCd());

                arrayList.add(personName.getAddTime());//5
                arrayList.add(personName.getAddUserId());//6
                arrayList.add(personName.getDefaultNmInd());//7
                arrayList.add(personName.getDurationAmt());//8
                arrayList.add(personName.getDurationUnitCd());//9
                arrayList.add(personName.getFirstNm());//10
                arrayList.add(personName.getFirstNm()); //11 for soundex
                arrayList.add(personName.getFromTime());//12
                arrayList.add(personName.getLastChgReasonCd());//13
                arrayList.add(personName.getLastChgTime());//14
                arrayList.add(personName.getLastChgUserId());//15
                arrayList.add(personName.getLastNm());//16
                arrayList.add(personName.getLastNm()); //17 for soundex
                arrayList.add(personName.getLastNm2());//18
                arrayList.add(personName.getLastNm2()); //19 for soundex
                arrayList.add(personName.getMiddleNm());//20
                arrayList.add(personName.getMiddleNm2());//21
                arrayList.add(personName.getNmDegree());//22
                arrayList.add(personName.getNmPrefix());//23
                arrayList.add(personName.getNmSuffix());//24
                arrayList.add(personName.getNmUseCd());//25
                arrayList.add(personName.getRecordStatusCd());//26
                arrayList.add(personName.getRecordStatusTime());//27

                if(personName.getStatusCd() == null)//28
                    arrayList.add("A");
                else
                    arrayList.add(personName.getStatusCd());

                if(personName.getStatusTime()==null)//29
                     arrayList.add(new Timestamp(new java.util.Date().getTime()));
                else
                    arrayList.add(personName.getStatusTime());

                arrayList.add(personName.getToTime());//30
                arrayList.add(personName.getUserAffiliationTxt());//31
                arrayList.add(personName.getAsOfDate());//32
                // using the preparedStmtMethod from DAOBase
                // pass the dt in for the update
                // pass in an arraylist (ordered list)
                //        with all the parameters for the sql statement
                // pass in the sql you wish to use
                // update is the same as insert so it uses the same opperation constant
                int resultCount = ((Integer)preparedStmtMethod(personName, arrayList, INSERT_PERSON_NAME_HIST, NEDSSConstants.UPDATE)).intValue();
                if (resultCount != 1)
                {
                    logger.error("Error: none or more than one person updated at a time, " + "resultCount = " + resultCount);
                    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
                }
            }
        }
         catch (Exception ex) {
            logger.fatal("Exception in updatePerson() = " + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }

/*
          int resultCount = 0;
        // PreparedStatement preparedStmt = getPreparedStatement(this.INSERT_PERSON_NAME_HIST);
          Connection dbConnection = null;
          PreparedStatement preparedStmt = null;
          try
          {
              dbConnection = getConnection();
              preparedStmt = dbConnection.prepareStatement(this.INSERT_PERSON_NAME_HIST);
              // setPreparedStatement(preparedStmt, dt);
              int i = 1;
1              preparedStmt.setLong(i++, dt.getPersonUid().longValue());
2              preparedStmt.setInt(i++, dt.getPersonNameSeq().shortValue());
3              preparedStmt.setInt(i++, versionCtrlNbr);
4              preparedStmt.setString(i++, dt.getAddReasonCd());
5              if( dt.getAddTime()==null)
                preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
              else
                preparedStmt.setTimestamp(i++, dt.getAddTime() );
6              if( dt.getAddUserId()==null)
                preparedStmt.setNull(i++, Types.BIGINT );
              else
                preparedStmt.setLong(i++, dt.getAddUserId().longValue() );

7              if(dt.getDefaultNmInd() == null)
                preparedStmt.setNull(i++, Types.VARCHAR);
              else
                preparedStmt.setString(i++, dt.getDefaultNmInd());

8              preparedStmt.setString(i++, dt.getDurationAmt());
9              preparedStmt.setString(i++, dt.getDurationUnitCd());
10              preparedStmt.setString(i++, dt.getFirstNm());
11              preparedStmt.setString(i++, dt.getFirstNmSndx());
12             if( dt.getFromTime()==null)
                preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
             else
                preparedStmt.setTimestamp(i++, dt.getFromTime() );
13             preparedStmt.setString(i++, dt.getLastChgReasonCd());
14            if( dt.getLastChgTime()==null)
                preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getLastChgTime() );
15            if( dt.getLastChgUserId()==null)
                preparedStmt.setNull(i++, Types.BIGINT );
            else
                preparedStmt.setLong(i++, dt.getLastChgUserId().longValue() );
16            preparedStmt.setString(i++, dt.getLastNm());
17            preparedStmt.setString(i++, dt.getLastNmSndx());
18            preparedStmt.setString(i++, dt.getLastNm2());
19            preparedStmt.setString(i++, dt.getLastNm2Sndx());
20            preparedStmt.setString(i++, dt.getMiddleNm());
21            preparedStmt.setString(i++, dt.getMiddleNm2());
22            preparedStmt.setString(i++, dt.getNmDegree());
23            preparedStmt.setString(i++, dt.getNmPrefix());
24            preparedStmt.setString(i++, dt.getNmSuffix());
25            preparedStmt.setString(i++, dt.getNmUseCd());
26            preparedStmt.setString(i++, dt.getRecordStatusCd());
27            if( dt.getRecordStatusTime()==null)
                preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getRecordStatusTime() );
28            preparedStmt.setString(i++, dt.getStatusCd());
29            if( dt.getStatusTime()==null)
                preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getStatusTime() );
30            if( dt.getToTime()==null)
                preparedStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getToTime() );
31            preparedStmt.setString(i++, dt.getUserAffiliationTxt());

            resultCount = preparedStmt.executeUpdate();
            if ( resultCount != 1 )
            {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
            }
          }
          catch(SQLException se)
          {
            se.printStackTrace();
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          }
          finally
          {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
          }
*/
        }
    }

}

