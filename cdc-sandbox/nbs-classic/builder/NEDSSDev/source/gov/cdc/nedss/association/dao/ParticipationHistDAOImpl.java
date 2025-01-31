package gov.cdc.nedss.association.dao;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.ParticipationHistItemVO;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
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

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ParticipationHistDAOImpl extends BMPBase{

  static final LogUtils logger = new LogUtils(ParticipationHistDAOImpl.class.getName());
  private long subjectEntityUid = -1;
  private long actUid = -1;
  private String typeCd = "";
  private short versionCtrlNbr = 0;

  private final String SELECT_PARTICIPATION_HIST =
  "select subject_entity_uid \"subjectEntityUid\", "+
  "act_uid \"actUid\", version_ctrl_nbr \"versionCtrlNbr\", "+
  "type_cd \"typeCd\", act_class_cd \"actClassCd\", "+
  "add_reason_cd \"addReasonCd\", add_time \"addTime\", "+
  "add_user_id \"addUserId\", awareness_cd \"awarenessCd\", "+
  "awareness_desc_txt \"awarenessDescTxt\", cd \"cd\", "+
  "duration_amt \"durationAmt\", "+
  "duration_unit_cd \"durationUnitCd\", from_time \"fromTime\", "+
  "last_chg_reason_cd \"lastChgReasonCd\", "+
  "last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", "+
  "record_status_cd \"recordStatusCd\", "+
  "record_status_time \"recordStatusTime\", role_seq \"roleSeq\", "+
  "status_cd \"statusCd\", status_time \"statusTime\", "+
  "subject_class_cd \"subjectClassCd\", "+
  "to_time \"toTime\", type_desc_txt \"typeDescTxt\", "+
  "user_affiliation_txt \"userAffiliationTxt\" "+
  "from participation_hist where subject_entity_uid = ? "+
  "and act_uid = ? and type_cd = ? and version_ctrl_nbr = ?";

  private final String INSERT_PARTICIPATION_HIST =
  "insert into participation_hist(subject_entity_uid, "+
  "act_uid, type_cd, version_ctrl_nbr, "+
  "act_class_cd, add_reason_cd, "+
  "add_time, add_user_id, "+
  "awareness_cd, awareness_desc_txt, "+
  "cd, duration_amt, duration_unit_cd, "+
  "from_time, last_chg_reason_cd, "+
  "last_chg_time, last_chg_user_id, "+
  "record_status_cd, record_status_time, "+
  "role_seq, status_cd, status_time, "+
  "subject_class_cd, to_time, type_desc_txt, user_affiliation_txt) "+
  "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  private final String SELECT_PARTICIPATION_SEQ_NUMBER_HIST =
  "SELECT version_ctrl_nbr from participation_hist where subject_entity_uid = ? and act_uid = ? and type_cd = ?";

  /**
   * Default Constructor
   */
  public ParticipationHistDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException {

  }//end of constructor

  /**
   * Sets the class variable versionCtrlNbr.
   * @param versionCtrlNbr : int
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
  public ParticipationHistDAOImpl(int versionCtrlNbr) throws NEDSSDAOSysException, NEDSSSystemException {
    this.versionCtrlNbr = (short)versionCtrlNbr;
  }//end of constructor

  /**
   * Initializes the following class variables: subjectEntityUid, actUid, typeCd, versionCtrlNbr
   * @param subjectEntityUid : long
   * @param actUid : long
   * @param typeCd : String
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
  public ParticipationHistDAOImpl(long subjectEntityUid, long actUid, String typeCd)throws NEDSSDAOSysException,
  NEDSSSystemException {
	  try{
	    this.subjectEntityUid = subjectEntityUid;
	    this.actUid = actUid;
	    this.typeCd = typeCd;
	    getNextParticipationHistId();
	  }catch(NEDSSDAOSysException ex){
		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		  throw new NEDSSDAOSysException(ex.toString());
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }//end of constructor

  /**
   * Stores the ParticipationDT object passed in.
   * @param obj : Object
   * @return void
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
  public void store(Object obj)
      throws NEDSSDAOSysException, NEDSSSystemException {
	  try{
        ParticipationDT dt = (ParticipationDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ParticipationDT object.");
        insertParticipationHist(dt);
	  }catch(NEDSSDAOSysException ex){
		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		  throw new NEDSSDAOSysException(ex.toString());
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
    }//end of store()

    /**
     * Results in the insertion of multilple ParticipationDT
     * @param coll : Collection
     * @return void
     * @throws NEDSSDAOSysException, NEDSSSystemException
     */
    public void store(Collection<Object> coll)
	   throws NEDSSDAOSysException, NEDSSSystemException {
    	try{
	       Iterator<Object>  iterator = null;
	        if(coll != null)
	        {
	        	iterator = coll.iterator();
		        while(iterator.hasNext())
		        {
	                  store(iterator.next());
		        }//end of while
	        }//end of if
    	}catch(NEDSSDAOSysException ex){
  		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
  		  throw new NEDSSDAOSysException(ex.toString());
  	  	}catch(Exception ex){
  		  logger.fatal("Exception  = "+ex.getMessage(), ex);
  		  throw new NEDSSSystemException(ex.toString());
  	  	}
    }//end of store(Collection)

    /**
     * Loads the ParticipationDT object associated with the parameters passed in.
     * @param subjectEntityUid : Long
     * @param actUid : Long
     * @param typeCd : String
     * @param versionCtrlNbr : Integer
     * @return ParticipationDT
     * @throws NEDSSDAOSysException, NEDSSSystemException
     */
    public ParticipationDT load(Long subjectEntityUid, Long actUid, String typeCd, Integer versionCtrlNbr) throws NEDSSSystemException {
    	try{
	        logger.info("Starts loadObject() for a participation history...");
	        ParticipationDT pDT = selectParticipationHist(subjectEntityUid.longValue(), actUid.longValue(), typeCd, versionCtrlNbr.shortValue());
	        pDT.setItNew(false);
	        pDT.setItDirty(false);
	        logger.info("Done loadObject() for a participation history - return: " + pDT.toString());
	        return pDT;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
	  	}
    }//end of load

    /**
     * @return versionCtrlNbr
     */
    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

    /**
     * return a collection of ParticipationHistItemVOs
     * @param subjectEntityUid : Long
     * @param actUid : Long
     * @param typeCd : String
     * @param sqlQuery : String
     * @return Collection
     * @throws NEDSSSystemException
     */
    public Collection<Object>  getParticipationHistItemColl(Long subjectEntityUid, Long actUid, String typeCd, String sqlQuery)
	throws NEDSSSystemException
    {
	       Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ParticipationHistItemVO vo = new ParticipationHistItemVO();
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for ParticipationHistItemVO", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(sqlQuery);
            preparedStmt.setLong(1, subjectEntityUid.longValue());
            preparedStmt.setLong(2, actUid.longValue());
            preparedStmt.setString(3, typeCd);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  idList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();
            idList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, vo.getClass(), idList);

            for(Iterator<Object> anIterator = idList.iterator(); anIterator.hasNext(); )
            {
                ParticipationHistItemVO reSetName = (ParticipationHistItemVO)anIterator.next();
                reSetList.add(reSetName);
            }
            logger.debug("Return ParticipationHistItemVO collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "ParticipationHistItemVO collection;  SubjectEntityUid = " + subjectEntityUid + ", actUid = "+actUid+", typeCd = "+typeCd, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Error in result set handling while selecting ParticipationHistItemVO.", rsuex);
            throw new NEDSSSystemException(rsuex.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selection " +
                  "ParticipationHistItemVO; subjectEntityUid = " + subjectEntityUid + ", actUid = "+actUid+", typeCd = "+typeCd, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }//end of finally
    }//end of getParticipationHistItemColl()

    /**
     * Populates a ParticipationHistItemVO object
     * @param subjectEntityUid
     * @param actUid
     * @param typeCd
     * @param sqlQuery
     * @return ParticipationHistItemVO
     */
    public ParticipationHistItemVO getParticipationHistItem(Long subjectEntityUid, Long actUid, String typeCd, String sqlQuery)
    throws NEDSSSystemException
    {
	       ParticipationHistItemVO pHistItemVO = new ParticipationHistItemVO();
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
                            "for selectParticipationHistItem ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(sqlQuery);
            preparedStmt.setLong(1, subjectEntityUid.longValue());
            preparedStmt.setLong(2, actUid.longValue());
            preparedStmt.setString(3, typeCd);
            resultSet = preparedStmt.executeQuery();

	           logger.debug("ParticipationHistItemVO object for participation history: SubjectEntityUid = " + subjectEntityUid + ", actUid = "+actUid+", typeCd = "+typeCd);

            resultSetMetaData = resultSet.getMetaData();

            pHistItemVO = (ParticipationHistItemVO)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, pHistItemVO.getClass());
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "ParticipationHistItemVO: subjectEntityUid = " + subjectEntityUid + ", actUid = "+actUid+", typeCd = "+typeCd, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "ParticipationHistItemVO; subjectEntityUid = " + subjectEntityUid + ", actUid = "+actUid+", typeCd = "+typeCd, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return ParticipationHistItemVO for Participation history");

        return pHistItemVO;
    }


  /////////////////////////////////Private class methods///////////////////////////////////

  /**
   * Populates a ParticipationDT object with data from history.
   * @param subjectEntityUid : long
   * @param actUid : long
   * @param typeCd : String
   * @param versionCtrlNbr : short
   * @return ParticipationDT
   * @throws NEDSSSystemException
   */
  private ParticipationDT selectParticipationHist(long subjectEntityUid, long actUid, String typeCd, short versionCtrlNbr)throws NEDSSSystemException
   {


        ParticipationDT pDT = new ParticipationDT();
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
                            "for selectParticipationHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_PARTICIPATION_HIST);
            preparedStmt.setLong(1, subjectEntityUid);
            preparedStmt.setLong(2, actUid);
            preparedStmt.setString(3, typeCd);
            preparedStmt.setLong(4, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           logger.debug("ParticipationDT object for Participation History: subjectEntityUid = " + subjectEntityUid + ", actUid = "+actUid+", typeCd = "+typeCd);

            resultSetMetaData = resultSet.getMetaData();

            pDT = (ParticipationDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, pDT.getClass());

            pDT.setItNew(false);
            pDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "participation history; subjectEntityUid = " + subjectEntityUid + ", actUid = "+actUid+", typeCd = "+typeCd, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "participation dt; subjectEntityUid = " + subjectEntityUid + ", actUid = "+actUid+", typeCd = "+typeCd, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return ParticipationDT for Participation history");

        return pDT;


  }//end of selectParticipationHist(...)

  /**
   * Results in the insertion of a Participation record into history
   * @param dt : ParticipationDT
   * @return void
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
  private void insertParticipationHist(ParticipationDT dt)throws NEDSSDAOSysException,
    NEDSSSystemException {
    if(dt.getSubjectEntityUid() != null && dt.getActUid() != null && dt.getTypeCd() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {
              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(INSERT_PARTICIPATION_HIST);
              pStmt.setLong(i++, dt.getSubjectEntityUid().longValue());
              pStmt.setLong(i++, dt.getActUid().longValue());
              pStmt.setString(i++, dt.getTypeCd());
              pStmt.setShort(i++, versionCtrlNbr);

              if(dt.getActClassCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getActClassCd());

              if(dt.getAddReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getAddReasonCd());

              if(dt.getAddTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getAddTime());

              if(dt.getAddUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getAddUserId().longValue());

              if(dt.getAwarenessCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getAwarenessCd());

              if(dt.getAwarenessDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getAwarenessDescTxt());

              if(dt.getCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCd());

              if(dt.getDurationAmt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getDurationAmt());

              if(dt.getDurationUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getDurationUnitCd());

              if(dt.getFromTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getFromTime());

              if(dt.getLastChgReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLastChgReasonCd());

              if(dt.getLastChgTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getLastChgTime());

              if(dt.getLastChgUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getLastChgUserId().longValue());

              if(dt.getRecordStatusCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getRecordStatusTime());

              if(dt.getRoleSeq() == null)
                pStmt.setNull(i++, Types.SMALLINT);
              else
                pStmt.setShort(i++, dt.getRoleSeq().shortValue());

              pStmt.setString(i++, dt.getStatusCd());

              if(dt.getStatusTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getStatusTime());

              if(dt.getSubjectClassCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getSubjectClassCd());

              if(dt.getToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getToTime());

              if(dt.getTypeDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTypeDescTxt());

              if(dt.getUserAffiliationTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getUserAffiliationTxt());


              resultCount = pStmt.executeUpdate();
              if ( resultCount != 1 )
              {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
        	  logger.fatal("Exception  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertPatientEncounterHist()

  
  public void insertParticipationHist(int versionControlNumber, ParticipationDT dt)throws NEDSSDAOSysException,
  NEDSSSystemException {
  if(dt.getSubjectEntityUid() != null && dt.getActUid() != null && dt.getTypeCd() != null) {
    int resultCount = 0;

        int i = 1;
        Connection dbConnection = null;
        PreparedStatement pStmt = null;
        try
        {
            dbConnection = getConnection();
            pStmt = dbConnection.prepareStatement(INSERT_PARTICIPATION_HIST);
            pStmt.setLong(i++, dt.getSubjectEntityUid().longValue());
            pStmt.setLong(i++, dt.getActUid().longValue());
            pStmt.setString(i++, dt.getTypeCd());
            pStmt.setInt(i++, versionControlNumber);

            if(dt.getActClassCd() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getActClassCd());

            if(dt.getAddReasonCd() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getAddReasonCd());

            if(dt.getAddTime() == null)
              pStmt.setNull(i++, Types.TIMESTAMP);
            else
              pStmt.setTimestamp(i++, dt.getAddTime());

            if(dt.getAddUserId() == null)
              pStmt.setNull(i++, Types.BIGINT);
            else
              pStmt.setLong(i++, dt.getAddUserId().longValue());

            if(dt.getAwarenessCd() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getAwarenessCd());

            if(dt.getAwarenessDescTxt() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getAwarenessDescTxt());

            if(dt.getCd() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getCd());

            if(dt.getDurationAmt() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getDurationAmt());

            if(dt.getDurationUnitCd() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getDurationUnitCd());

            if(dt.getFromTime() == null)
              pStmt.setNull(i++, Types.TIMESTAMP);
            else
              pStmt.setTimestamp(i++, dt.getFromTime());

            if(dt.getLastChgReasonCd() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getLastChgReasonCd());

            if(dt.getLastChgTime() == null)
              pStmt.setNull(i++, Types.TIMESTAMP);
            else
              pStmt.setTimestamp(i++, dt.getLastChgTime());

            if(dt.getLastChgUserId() == null)
              pStmt.setNull(i++, Types.BIGINT);
            else
              pStmt.setLong(i++, dt.getLastChgUserId().longValue());

            if(dt.getRecordStatusCd() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getRecordStatusCd());

            if(dt.getRecordStatusTime() == null)
              pStmt.setNull(i++, Types.TIMESTAMP);
            else
              pStmt.setTimestamp(i++, dt.getRecordStatusTime());

            if(dt.getRoleSeq() == null)
              pStmt.setNull(i++, Types.SMALLINT);
            else
              pStmt.setShort(i++, dt.getRoleSeq().shortValue());

            pStmt.setString(i++, dt.getStatusCd());

            if(dt.getStatusTime() == null)
              pStmt.setNull(i++, Types.TIMESTAMP);
            else
              pStmt.setTimestamp(i++, dt.getStatusTime());

            if(dt.getSubjectClassCd() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getSubjectClassCd());

            if(dt.getToTime() == null)
              pStmt.setNull(i++, Types.TIMESTAMP);
            else
              pStmt.setTimestamp(i++, dt.getToTime());

            if(dt.getTypeDescTxt() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getTypeDescTxt());

            if(dt.getUserAffiliationTxt() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getUserAffiliationTxt());


            resultCount = pStmt.executeUpdate();
            if ( resultCount != 1 )
            {
              throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
            }

        } catch(SQLException se) {
        	logger.fatal("versionControlNumber: "+versionControlNumber+" Exception  = "+se.getMessage(), se);
          throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
        } finally {
          closeStatement(pStmt);
          releaseConnection(dbConnection);
        }//end of finally
  }//end of if
}//end of insertPatientEncounterHist()

  /**
   * Determines what the next versionCtrlNbr should be.
   * @return void
   * @throws NEDSSDAOSysException
   */
  private void getNextParticipationHistId() {
    ResultSet resultSet = null;
    short seqTemp = -1;
    Connection dbConnection = null;
    PreparedStatement pStmt = null;

    try
    {
        dbConnection = getConnection();
        pStmt = dbConnection.prepareStatement(SELECT_PARTICIPATION_SEQ_NUMBER_HIST);
        pStmt.setLong(1, subjectEntityUid);
        pStmt.setLong(2, actUid);
        pStmt.setString(3, typeCd);
        resultSet = pStmt.executeQuery();
        while ( resultSet.next() )
        {
            seqTemp = resultSet.getShort(1);
            logger.debug("ParticipationHistDAOImpl--seqTemp: " + seqTemp);
            if ( seqTemp > versionCtrlNbr ) versionCtrlNbr = seqTemp;
        }
        ++versionCtrlNbr;
        logger.debug("ParticipationHistDAOImpl--versionCtrlNbr: " + versionCtrlNbr);
    } catch(SQLException se) {
    	logger.fatal("Exception  = "+se.getMessage(), se);
        throw new NEDSSDAOSysException("Error: SQLException while selecting \n" + se.getMessage());
    } finally {
        closeResultSet(resultSet);
        closeStatement(pStmt);
        releaseConnection(dbConnection);
    }//end of finally
  }//end of getNextParticipationHistId()
}//ParticipationHistDAOImpl
