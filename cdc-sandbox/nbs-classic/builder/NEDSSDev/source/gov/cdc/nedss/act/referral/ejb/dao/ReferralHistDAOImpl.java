package gov.cdc.nedss.act.referral.ejb.dao;

import gov.cdc.nedss.act.referral.dt.ReferralDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
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

public class ReferralHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(ReferralHistDAOImpl.class.getName());
  private long referralUid = -1;
  private int versionCtrlNbr = 0;
  //SQL for Referral History
     public static final String SELECT_REFERRAL_HIST =
       "SELECT referral_uid \"ReferralUid\", "
     + "activity_duration_amt \"ActivityDurationAmt\", "
     + "activity_duration_unit_cd \"ActivityDurationUnitCd\", "
     + "activity_from_time \"ActivityFromTime\", "
     + "activity_to_time \"ActivityToTime\", "
     + "add_reason_cd \"AddReasonCd\", "
     + "add_time \"AddTime\", "
     + "add_user_id \"AddUserId\", "
     + "cd \"Cd\", "
     + "cd_desc_txt \"CdDescTxt\", "
     + "confidentiality_cd \"ConfidentialityCd\", "
     + "confidentiality_desc_txt \"ConfidentialityDescTxt\", "
     + "effective_duration_amt \"EffectiveDurationAmt\", "
     + "effective_duration_unit_cd \"EffectiveDurationUnitCd\", "
     + "effective_from_time \"EffectiveFromTime\", "
     + "effective_to_time \"EffectiveToTime\", "
     + "last_chg_reason_cd \"LastChgReasonCd\", "
     + "last_chg_time \"LastChgTime\", "
     + "last_chg_user_id \"LastChgUserId\", "
     + "local_id \"LocalId\", "
     // + "org_access_permis \"OrgAccessPermis\", "
     // + "prog_area_access_permis \"ProgAreaAccessPermis\", "
     + "reason_txt \"ReasonTxt\", "
     + "record_status_cd \"RecordStatusCd\", "
     + "record_status_time \"RecordStatusTime\", "
     + "referral_desc_txt \"ReferralDescTxt\", "
     + "repeat_nbr \"RepeatNbr\", "
     + "status_cd \"StatusCd\", "
     + "status_time \"StatusTime\", "
     + "txt \"Txt\", "
     + "user_affiliation_txt \"UserAffiliationTxt\" "
     + "program_jurisdiction_oid \"ProgramJurisdictionOid\" "
     + "shared_ind \"SharedInd\" "
     + "from referral_hist WHERE referral_uid = ? and version_ctrl_nbr = ?";

  public static final String INSERT_REFERRAL_HIST =
     "INSERT into referral_hist(referral_uid, "
     + "version_ctrl_nbr, "
     + "activity_duration_amt, "
     + "activity_duration_unit_cd, "
     + "activity_from_time, "
     + "activity_to_time, "
     + "add_reason_cd, "
     + "add_time, "
     + "add_user_id, "
     + "cd, "
     + "cd_desc_txt, "
     + "confidentiality_cd, "
     + "confidentiality_desc_txt, "
     + "effective_duration_amt, "
     + "effective_duration_unit_cd, "
     + "effective_from_time, "
     + "effective_to_time, "
     + "last_chg_reason_cd, "
     + "last_chg_time, "
     + "last_chg_user_id, "
     + "local_id, "
     //+ "org_access_permis, "
     //+ "prog_area_access_permis, "
     + "reason_txt, "
     + "record_status_cd, "
     + "record_status_time, "
     + "referral_desc_txt, "
     + "repeat_nbr, "
     + "status_cd, "
     + "status_time, "
     + "txt, "
     + "user_affiliation_txt), "
     + "program_jurisdiction_oid, "
     + "shared_ind, "
     + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  public static final String SELECT_REFERRAL_SEQ_NUMBER_HIST =
     "SELECT version_ctrl_nbr from referral_hist where referral_uid = ?";

  public ReferralHistDAOImpl()  {

  }//end of constructor

  public ReferralHistDAOImpl(long referralUid, short versionCtrlNbr){
    this.referralUid = referralUid;
    this.versionCtrlNbr = versionCtrlNbr;
    //getNextReferralHistId();
  }//end of constructor
  /**
   * persists the passed object into database
   * @param obj -- Object to be persisted
   * throws NEDSSSystemException
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
	  try{
		  ReferralDT dt = (ReferralDT)obj;
		  if(dt == null)
			  throw new NEDSSSystemException("Error: try to store null ReferralDT object.");
		  insertReferralHist(dt);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }

    }//end of store()
  /**
   * persists the collection of passed objects into database
   * @param obj -- collection of Objects to be persisted
   * throws NEDSSSystemException
   */
    public void store(Collection<Object> coll)
	   throws  NEDSSSystemException {
    	try{
	        Iterator<Object> iterator = null;
	        if(coll != null)
	        {
	        	iterator = coll.iterator();
		        while(iterator.hasNext())
		        {
	                  store(iterator.next());
		        }//end of while
	        }//end of if
    	}catch(Exception ex){
  		  logger.fatal("Exception  = "+ex.getMessage(), ex);
  		  throw new NEDSSSystemException(ex.toString());
  	  }
    }//end of store(Collection)

    /**
     * Load the referral object from database with specified UID and version control Number
	* @param refUid -- UID for the refferal object to be loaded
	* @param refHistSeq -- Version ctrl number for the refferal object to be loaded
	* @return refDT -- the referral object from database with specified UID and version control Number
     */
    public ReferralDT load(Long refUid, Integer refHistSeq) throws NEDSSSystemException {
    	try{
    		logger.info("Starts loadObject() for a referral history...");
	        ReferralDT refDT = selectReferralHist(refUid.longValue(),refHistSeq.intValue());
	        refDT.setItNew(false);
	        refDT.setItDirty(false);
	        logger.info("Done loadObject() for a referrals history - return: " + refDT.toString());
	        return refDT;
    	}catch(Exception ex){
  		  logger.fatal("Exception  = "+ex.getMessage(), ex);
  		  throw new NEDSSSystemException(ex.toString());
  	  	}

    }//end of load
    /**
     * get the version ctrl number
	* @return versionCtrlNbr -- version ctrl number
     */
    public int getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

  ////////////////////////////private class methods/////////////////////////
  /**
     * Load the ReferralDT object corrosponding to the passed Uid and Version Ctrl Number
	* @param refUid -- The UID for the record to be loaded
	* @param refHistSeq -- The Version Ctrl no. for the record to be loaded
	* @return ReferralDT -- The Loaded ReferralDT object
	* @throws NEDSSSystemException
	*/
  private ReferralDT selectReferralHist(long refUid, int refHistSeq)throws NEDSSSystemException {


        ReferralDT refDT = new ReferralDT();
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
                            "for selectReferralHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(ReferralHistDAOImpl.SELECT_REFERRAL_HIST);
            preparedStmt.setLong(1, refUid);
            preparedStmt.setInt(2, refHistSeq);
            resultSet = preparedStmt.executeQuery();

	           logger.debug("ReferralDT object for Referral history: referralUid = " + refUid);

            resultSetMetaData = resultSet.getMetaData();

            refDT = (ReferralDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, refDT.getClass());

            refDT.setItNew(false);
            refDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "referral history; id = " + refUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "referral vo; id = " + refUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return referralDT for referral history");

        return refDT;


  }//end of selectReferralsHist(...)
  /**
     * insert the new record for Referral
	* @param dt -- the Referral DT
	* @throws NEDSSSystemException
     */
  private void insertReferralHist(ReferralDT dt)throws
    NEDSSSystemException {
    if(dt.getReferralUid() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {
              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(ReferralHistDAOImpl.INSERT_REFERRAL_HIST);
              pStmt.setLong(i++, dt.getReferralUid().longValue());
              pStmt.setInt(i++, dt.getVersionCtrlNbr().intValue());

              if(dt.getActivityDurationAmt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getActivityDurationAmt());

              if(dt.getActivityDurationUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getActivityDurationUnitCd());

              if(dt.getActivityFromTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getActivityFromTime());

              if(dt.getActivityToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getActivityToTime());

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

              if(dt.getCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCd());

              if(dt.getCdDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCdDescTxt());

              if(dt.getConfidentialityCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfidentialityCd());

              if(dt.getConfidentialityDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfidentialityDescTxt());

              if(dt.getEffectiveDurationAmt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getEffectiveDurationAmt());

              if(dt.getEffectiveDurationUnitCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getEffectiveDurationUnitCd());

              if(dt.getEffectiveFromTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getEffectiveFromTime());

              if(dt.getEffectiveToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getEffectiveToTime());

              if(dt.getLastChgReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLastChgReasonCd());

              if(dt.getLastChgTime() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setTimestamp(i++, dt.getLastChgTime());

              if(dt.getLastChgUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getLastChgUserId().longValue());

              if(dt.getLocalId() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLocalId());

              if(dt.getReasonTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getReasonTxt());

              if(dt.getRecordStatusCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getRecordStatusTime());

              if(dt.getReferralDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getReferralDescTxt());

              if(dt.getRepeatNbr() == null)
                pStmt.setNull(i++, Types.SMALLINT);
              else
                pStmt.setShort(i++, dt.getRepeatNbr().shortValue());


              pStmt.setString(i++, dt.getStatusCd());

              pStmt.setTimestamp(i++, dt.getStatusTime());

              if(dt.getTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTxt());

              if(dt.getUserAffiliationTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getUserAffiliationTxt());

              if(dt.getProgramJurisdictionOid() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setLong(i++, dt.getProgramJurisdictionOid().longValue());

              if(dt.getSharedInd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getSharedInd());

              resultCount = pStmt.executeUpdate();
              if ( resultCount != 1 )
              {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
        	  logger.fatal("SQLException  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertNotificationHist()

}//end of ReferralHistDAOImpl
