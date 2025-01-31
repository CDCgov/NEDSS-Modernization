package gov.cdc.nedss.act.actid.dao;

import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.sql.SQLException;
import java.sql.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.exception.*;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * 
 * @version 1.0
 */

public class ActIdHistDAOImpl extends BMPBase {

  /**
   * An instance of the LogUtils class.
   */
  static final LogUtils logger = new LogUtils(ActIdHistDAOImpl.class.getName());
  /**
   * Variable for the actIdUid field.
   */
  private long actIdUid = -1;
  /**
   * Variable for the actIdSeq field.
   */
  private long actIdSeq = -1;
  /**
   * Variable for the versionCtrlNbr field.
   */
  private short versionCtrlNbr = 0;
  /**
   * A SELECT statement for actId history.
   */
  private final String SELECT_ACT_ID_HIST =
    "select act_uid \"actUid\", "+
    "act_id_seq \"actIdSeq\", "+
    "version_ctrl_nbr \"versionCtrlNbr\", "+
    "add_reason_cd \"addReasonCd\", "+
    "add_time \"addTime\", "+
    "add_user_id \"addUserId\", "+
    "assigning_authority_cd \"assigningAuthorityCd\", "+
    "assigning_authority_desc_txt \"assigningAuthorityDescTxt\", "+
    "duration_amt \"durationAmt\", "+
    "duration_unit_cd \"durationUnitCd\", "+
    "last_chg_reason_cd \"lastChgReasonCd\", "+
    "last_chg_time \"lastChgTime\", "+
    "last_chg_user_id \"lastChgUserId\", "+
    "record_status_cd \"recordStatusCd\", "+
    "record_status_time \"recordStatusTime\", "+
    "root_extension_txt \"rootExtensionTxt\", "+
    "status_cd \"statusCd\", "+
    "status_time \"statusTime\", "+
    "type_cd \"typeCd\", "+
    "type_desc_txt \"typeDescTxt\", "+
    "user_affiliation_txt \"userAffiliationTxt\", "+
    "valid_from_time \"validFromTime\", "+
    "valid_to_time \"validToTime\" "+
    "from act_id_hist where act_uid = ? "+
    //and act_id_seq = ?
    "and version_ctrl_nbr = ?";

  /**
   * An INSERT statement for ActId history.
   */
  private final String INSERT_ACT_ID_HIST =
    "insert into act_id_hist("+
    "act_uid, "+
    "act_id_seq, "+
    "version_ctrl_nbr, "+
    "add_reason_cd, "+
    "add_time, "+
    "add_user_id, "+
    "assigning_authority_cd, "+
    "assigning_authority_desc_txt, "+
    "duration_amt, "+
    "duration_unit_cd, "+
    "last_chg_reason_cd, "+
    "last_chg_time, "+
    "last_chg_user_id, "+
    "record_status_cd, "+
    "record_status_time, "+
    "root_extension_txt, "+
    "status_cd, "+
    "status_time, "+
    "type_cd, "+
    "type_desc_txt, "+
    "user_affiliation_txt, "+
    "valid_from_time, "+
    "valid_to_time) "+
    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  /**
   * A SELECT statement for ActIdSeq history
   */
  private final String SELECT_ACT_ID_SEQ_NUMBER_HIST =
    "SELECT version_ctrl_nbr from act_id_hist where act_uid = ? and act_id_seq = ?";

  /**
   * Default constructor for ActIdHistDAOImpl class.
   */
  public ActIdHistDAOImpl() {

  }//end of constructor

  /**
   * Sets the class attribute versionCtrlNbr to the value passed into
   * this constructor.
   * 
   * @param versionCtrlNbr : short value
   */
  public ActIdHistDAOImpl(short versionCtrlNbr) {
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

  /**
   * Constructor that results in the initialization of the following class attributes: actIdUid, actIdSeq
   *  and versionCtrlNbr
   * 
   * @exception NEDSSSystemException 
   * @param actIdUid : long value
   * @param actIdSeq : long value
   */
  public ActIdHistDAOImpl(long actIdUid, long actIdSeq)throws
  NEDSSSystemException {
    this.actIdUid = actIdUid;
    this.actIdSeq = actIdSeq;
    getNextActIdHistId();
  }//end of constructor

  /**
   * Results in the addtion of an ActIdDT object to history
   * 
   * @param obj : Object
   * @exception NEDSSSystemException 
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
	  ActIdDT dt = null;
	  	try{
	        dt = (ActIdDT)obj;
	        if(dt == null)
	           throw new NEDSSSystemException("Error: try to store null ActIdDT object.");
	        insertActIdHist(dt);
	  	}catch(NEDSSSystemException e){
	  		logger.fatal("Exception occured while processing ActIdDT actUid"+dt.getActUid() , e);
	  		throw e;
	  	}
    }//end of store()

    /**
     * Results in the addition of 0.* ActIdDT objects to history
     * 
     * @param coll : Collection<Object>
     * @exception NEDSSSystemException 
     */
    public void store(Collection<Object> coll)
	   throws  NEDSSSystemException {
        Iterator<Object> iterator = null;
        try{
	        if(coll != null)
	        {
	        	iterator = coll.iterator();
		        while(iterator.hasNext())
		        {
	                  store(iterator.next());
		        }//end of while
	        }//end of if
        }catch(NEDSSSystemException ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
    }//end of store(Collection<Object>)

    /**
     * Loads a Collection<Object> of ActIdDt objects from history
     * 
     * @param actIdUid : Long
     * @param versionCtrlNbr : Integer
     * @exception NEDSSSystemException 
     */
    public Collection<Object> load(Long actIdUid, Integer versionCtrlNbr) throws NEDSSSystemException
      {
	       logger.info("Starts loadObject() for a act id history...");
        Collection<Object> coll = null;
        try{
        	coll = selectActIdHist(actIdUid.longValue(), versionCtrlNbr.shortValue());
        }catch(NEDSSSystemException ex){
        	logger.fatal("NEDSSSystemException "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
        logger.info("Done loadObject() for a act id history - return: " + coll);

        return coll;
    }//end of load

    /**
     * Returns the versionCtrlNbr
     * 
     * @return versionCtrlNbr : short
     */
    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

  ///////////////////////////////////////private class mehtods/////////////////////

  /**
   * Returns a Collection<Object> of ActIdDt objects from history
   * 
   * @param actIdUid : long
   * @param versionCtrlNbr : short
   * @exception NEDSSSystemException 
   * @return Collection<Object>
   */
  private Collection<Object> selectActIdHist(long actIdUid, short versionCtrlNbr)throws NEDSSSystemException
   {


        ActIdDT actIdDT = new ActIdDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectActIdHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_ACT_ID_HIST);
            preparedStmt.setLong(1, actIdUid);
            //preparedStmt.setLong(2, actIdSeq);
            preparedStmt.setLong(3, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();
            ArrayList<Object>  pnList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();
	           logger.debug("ActIdDT object for Act Id history: ActIdUid = " + actIdUid +" ActIdSeq = "+actIdSeq);

            resultSetMetaData = resultSet.getMetaData();

            pnList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, actIdDT.getClass(), pnList);
            for(Iterator<Object> anIterator = pnList.iterator(); anIterator.hasNext(); )
            {
                ActIdDT reSetName = (ActIdDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                reSetList.add(reSetName);
            }
            return reSetList;
            //actIdDT = (ActIdDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, actIdDT.getClass());

            //actIdDT.setItNew(false);
            //actIdDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "act id history; actIdUid = " + actIdUid +" actIdSeq = "+actIdSeq, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "act id history; actIdUid = " + actIdUid +" actIdSeq = "+actIdSeq, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }


  }//end of selectActIdHist(...)


  /**
   * Results in the addtion of an ActIdDT object to history
   * 
   * @param dt : ActIdDT
   * @exception NEDSSSystemException 
   */
  private void insertActIdHist(ActIdDT dt)throws
    NEDSSSystemException {
    if(dt.getActUid() != null && dt.getActIdSeq() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement ps = null;
          try
          {
              dbConnection = getConnection();
              ps = dbConnection.prepareStatement(INSERT_ACT_ID_HIST);
              ps.setLong(i++, dt.getActUid().longValue());
              ps.setShort(i++, dt.getActIdSeq().shortValue());
              ps.setShort(i++, versionCtrlNbr);

              if(dt.getAddReasonCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getAddReasonCd());

              if(dt.getAddTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getAddTime());

              if(dt.getAddUserId() == null)
                ps.setNull(i++, Types.BIGINT);
              else
                ps.setLong(i++, dt.getAddUserId().longValue());

              if(dt.getAssigningAuthorityCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getAssigningAuthorityCd());

              if(dt.getAssigningAuthorityDescTxt() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getAssigningAuthorityDescTxt());

              if(dt.getDurationAmt() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getDurationAmt());

              if(dt.getDurationUnitCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getDurationUnitCd());

              if(dt.getLastChgReasonCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getLastChgReasonCd());

              if(dt.getLastChgTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getLastChgTime());

              if(dt.getLastChgUserId() == null)
                ps.setNull(i++, Types.BIGINT);
              else
                ps.setLong(i++, dt.getLastChgUserId().longValue());

              if(dt.getRecordStatusCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getRecordStatusTime());

              if(dt.getRootExtensionTxt() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getRootExtensionTxt());

              ps.setString(i++, dt.getStatusCd());

              ps.setTimestamp(i++, dt.getStatusTime());

              if(dt.getTypeCd() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getTypeCd());

              if(dt.getTypeDescTxt() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getTypeDescTxt());

              if(dt.getUserAffiliationTxt() == null)
                ps.setNull(i++, Types.VARCHAR);
              else
                ps.setString(i++, dt.getUserAffiliationTxt());

              if(dt.getValidFromTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getValidFromTime());

              if(dt.getValidToTime() == null)
                ps.setNull(i++, Types.TIMESTAMP);
              else
                ps.setTimestamp(i++, dt.getValidToTime());


              resultCount = ps.executeUpdate();
              if ( resultCount != 1 )
              {
            	  logger.fatal("Error: none or more than one entity inserted at a time, resultCount = "+ resultCount);
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
        	  logger.fatal("Error: SQLException while inserting = "+ se.getMessage(), se);
        	  throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
        	  closeStatement(ps);
        	  releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertPatientEncounterHist()

  /**
   * Determines what the next versionCtrlNbr will be and assignes it to the
   * class variable versionCtrlNbr attribute.
   */
  private void getNextActIdHistId() {
    ResultSet resultSet = null;
    short seqTemp = -1;
    Connection dbConnection = null;
    PreparedStatement pStmt = null;

    try
    {
        dbConnection = getConnection();
        pStmt = dbConnection.prepareStatement(SELECT_ACT_ID_SEQ_NUMBER_HIST);
        pStmt.setLong(1, actIdUid);
        pStmt.setLong(2, actIdSeq);
        resultSet = pStmt.executeQuery();
        while ( resultSet.next() )
        {
            seqTemp = resultSet.getShort(1);
            logger.debug("ActIdHistDAOImpl--seqTemp: " + seqTemp);
            if ( seqTemp > versionCtrlNbr ) versionCtrlNbr = seqTemp;
        }
        ++versionCtrlNbr;
        logger.debug("ActIdHistDAOImpl--versionCtrlNbr: " + versionCtrlNbr);
    } catch(SQLException se) {
        throw new NEDSSDAOSysException("Error: SQLException while selecting \n" + se.getMessage(), se);
    } finally {
        closeResultSet(resultSet);
        closeStatement(pStmt);
        releaseConnection(dbConnection);
    }//end of finally
  }//end of getNextActIdHistId()

}//ActivityIdHistDAOImpl
