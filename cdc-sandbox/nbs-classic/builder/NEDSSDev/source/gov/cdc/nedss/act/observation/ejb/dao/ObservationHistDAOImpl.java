package gov.cdc.nedss.act.observation.ejb.dao;

import gov.cdc.nedss.act.observation.dt.ObservationDT;
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
import java.sql.Timestamp;
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

public class ObservationHistDAOImpl extends BMPBase {

  static final LogUtils logger = new LogUtils(ObservationHistDAOImpl.class.getName());

  private long observationUid = -1;
  private short versionCtrlNbr = 0;

  /**
   * Default Constructor
   */
  public ObservationHistDAOImpl() {

  }//end of constructor

  /**
   * Initializes the following class variables: observationUid, versionCtrlNbr
   */
  public ObservationHistDAOImpl(long observationUid, short versionCtrlNbr){
    this.observationUid = observationUid;
    this.versionCtrlNbr = versionCtrlNbr;
    //getNextObsHistId();
  }//end of constructor

  /**
   * Results in the addition of an ObservationDT record being added to history.
   *
   * @param obj : Object
   * @return void
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
	  try{
        ObservationDT dt = (ObservationDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ObservationDT object.");
          insertObservationHist(dt);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }

    }//end of store()

  /**
   * Results in 0.* ObservationDT object additions to history.
   *
   * @param coll : Collection
   * @return void
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
     * Loads the observationDT record from history
     *
     * @param obsUid : Long
     * @param versionCtrlNbr : Integer
     * @return ObservationDT
     * @throws NEDSSSystemException
     */
    public ObservationDT load(Long obsUid, Integer versionCtrlNbr) throws NEDSSSystemException
      {
    	try{
	        logger.info("Starts loadObject() for a observation history...");
	        ObservationDT obsDT = selectObservationHist(obsUid.longValue(),versionCtrlNbr.intValue());
	        obsDT.setItNew(false);
	        obsDT.setItDirty(false);
	        logger.info("Done loadObject() for a observation history - return: " + obsDT.toString());
	        return obsDT;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }//end of load

    /**
     * Returns the versionCtrlNbr
     * @return versionCtrlNbr : short
     */
    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

  /////////////////////////////////////private class methods////////////////////////////

  /**
   * Retrieves the specified ObservationDT object from history
   * @param obsUid : Long
   * @param versionCtrlNBr : int
   * @throws NEDSSSystemException
   */
  private ObservationDT selectObservationHist(long obsUid, int versionCtrlNbr)throws NEDSSSystemException
   {

		String SELECT_OBSERVATION_HIST = "SELECT "
									    + "observation_uid \"ObservationUid\", "
										+ "activity_duration_amt \"ActivityDurationAmt\", "
										+ "activity_duration_unit_cd \"ActivityDurationUnitCd\", "
										+ "activity_from_time \"ActivityFromTime\", "
										+ "activity_to_time \"ActivityToTime\", "
										+ "add_reason_cd \"AddReasonCd\", "
										+ "add_time \"AddTime\", "
										+ "add_user_id \"AddUserId\", "
										+ "cd \"Cd\", "
										+ "cd_desc_txt \"CdDescTxt\", "
										+ "cd_system_cd \"CdSystemCd\", "
										+ "cd_system_desc_txt \"CdSystemDescTxt\", "
										+ "confidentiality_cd \"ConfidentialityCd\", "
										+ "confidentiality_desc_txt \"ConfidentialityDescTxt\", "
										+ "ctrl_cd_display_form \"CtrlCdDisplayForm\", "
										+ "ctrl_cd_user_defined_1 \"CtrlCdUserDefined1\", "
										+ "ctrl_cd_user_defined_2 \"CtrlCdUserDefined2\", "
										+ "ctrl_cd_user_defined_3 \"CtrlCdUserDefined3\", "
										+ "ctrl_cd_user_defined_4 \"CtrlCdUserDefined4\", "
										+ "derivation_exp \"DerivationExp\", "
										+ "effective_duration_amt \"EffectiveDurationAmt\", "
										+ "effective_duration_unit_cd \"EffectiveDurationUnitCd\", "
										+ "effective_from_time EffectiveFromTime, "
										+ "effective_to_time EffectiveToTime, "
										+ "electronic_ind ElectronicInd, "
										+ "group_level_cd GroupLevelCd, "
										+ "jurisdiction_cd JurisdictionCd, "
										+ "last_chg_reason_cd LastChgReasonCd, "
										+ "last_chg_time LastChgTime, "
										+ "last_chg_user_id LastChgUserId, "
										+ "lab_condition_cd \"LabConditionCd\", "
										+ "local_id \"LocalId\", "
										+ "obs_domain_cd \"ObsDomainCd\", "
										+ "obs_domain_cd_st_1 \"ObsDomainCdSt1\", "
										//+ "org_access_permis \"OrgAccessPermis\", "
										+ "pnu_cd \"PnuCd\", "
										+ "priority_cd \"PriorityCd\", "
										+ "priority_desc_txt \"PriorityDescTxt\", "
										//+ "prog_area_access_permis \"ProgAreaAccessPermis\", "
										+ "prog_area_cd \"ProgAreaCd\", "
										+ "record_status_cd \"RecordStatusCd\", "
										+ "record_status_time \"RecordStatusTime\", "
										+ "repeat_nbr \"RepeatNbr\", "
										+ "status_cd \"StatusCd\", "
										+ "status_time \"StatusTime\", "
										+ "subject_person_uid \"SubjectPersonUid\", "
										+ "target_site_cd \"TargetSiteCd\", "
										+ "target_site_desc_txt \"TargetSiteDescTxt\", "
										+ "txt \"Txt\", "
										+ "user_affiliation_txt \"UserAffiliationTxt\", "
										+ "value_cd \"ValueCd\", "
										+ "ynu_cd \"YnuCd\" "
										+ "program_jurisdiction_oid \"programJurisdictionOid\", "
										+ "shared_ind \"sharedInd\", "
										+ "version_ctrl_nbr \"versionCtrlNbr\", "
                                                                                + "alt_cd \"altCd\", "
                                                                                + "alt_cd_desc_txt \"altCdDescTxt\", "
                                                                                + "alt_cd_system_cd \"altCdSystemCd\", "
                                                                                + "alt_cd_system_desc_txt \"altCdSystemDescTxt\", "
                                                                                + "cd_derived_ind \"cdDerivedInd\", "
                                                                                + "cd_version \"cdVersion\", "
                                                                                + "rpt_to_state_time \"rptToStateTime\" "
										+ "FROM observation_hist WITH (NOLOCK) "
										+ "WHERE observation_uid = ? "
										+ "and version_control_nbr = ?";

        ObservationDT obsDT = new ObservationDT();
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
                            "for selectObservationHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
			preparedStmt = dbConnection.prepareStatement(SELECT_OBSERVATION_HIST);
            preparedStmt.setLong(1, obsUid);
	           preparedStmt.setLong(2, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           logger.debug("ObservationDT object for Observation history: obsUid = " + obsUid);

            resultSetMetaData = resultSet.getMetaData();

            obsDT = (ObservationDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, obsDT.getClass());

            obsDT.setItNew(false);
            obsDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "observation history; id = " + obsUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "observation vo; id = " + obsUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return observationDT for observation history");

        return obsDT;


  }//end of selectObservationHist(...)

  /**
   * Results in the addition of an ObservationDT object to history
   * @param dt : ObservationDT
   * @return void
   * @throws NEDSSSystemException
   */
  private void insertObservationHist(ObservationDT dt)throws
    NEDSSSystemException {

		String INSERT_OBSERVATION_HIST = "INSERT into observation_hist ( "
									    + "observation_uid, "
                                                                            + "version_ctrl_nbr, "
										+ "activity_duration_amt, "
										+ "activity_duration_unit_cd , "
										+ "activity_from_time, "
										+ "activity_to_time, "
										+ "add_reason_cd, "
										+ "add_time, "
										+ "add_user_id, "
										+ "cd, "
										+ "cd_desc_txt, "
										+ "cd_system_cd, "
										+ "cd_system_desc_txt, "
										+ "confidentiality_cd, "
										+ "confidentiality_desc_txt, "
										+ "ctrl_cd_display_form, "
										+ "ctrl_cd_user_defined_1, "
										+ "ctrl_cd_user_defined_2, "
										+ "ctrl_cd_user_defined_3, "
										+ "ctrl_cd_user_defined_4, "
										+ "derivation_exp, "
										+ "effective_duration_amt, "
										+ "effective_duration_unit_cd, "
										+ "effective_from_time, "
										+ "effective_to_time, "
										+ "electronic_ind, "
										+ "group_level_cd, "
										+ "jurisdiction_cd, "
										+ "last_chg_reason_cd, "
										+ "last_chg_time, "
										+ "last_chg_user_id, "
										+ "lab_condition_cd, "
										+ "local_id, "
										+ "obs_domain_cd, "
										+ "obs_domain_cd_st_1, "
										//+ "org_access_permis, "
										+ "pnu_cd, "
										+ "priority_cd, "
										+ "priority_desc_txt, "
										//+ "prog_area_access_permis, "
										+ "prog_area_cd, "
										+ "record_status_cd, "
										+ "record_status_time, "
										+ "repeat_nbr, "
										+ "status_cd, "
										+ "status_time, "
										+ "subject_person_uid, "
										+ "target_site_cd, "
										+ "target_site_desc_txt, "
										+ "txt, "
										+ "user_affiliation_txt, "
										+ "value_cd, "
										+ "ynu_cd, "
										+ "program_jurisdiction_oid, "
										+ "shared_ind,"
                                                                                + "alt_cd, "
                                                                                + "ALT_CD_DESC_TXT, "
                                                                                + "ALT_CD_SYSTEM_CD, "
                                                                                + "ALT_CD_SYSTEM_DESC_TXT, "
                                                                                + "CD_DERIVED_IND, "
                                                                                + "CD_VERSION, "
                                                                                + "RPT_TO_STATE_TIME, "
                                                                                + "processing_decision_cd, "
                                                                                + "processing_decision_txt "
										+") "
										+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    if(dt.getObservationUid() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {

              dbConnection = getConnection();
              //pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBSERVATION_HIST);
			  pStmt = dbConnection.prepareStatement(INSERT_OBSERVATION_HIST);

              pStmt.setLong(i++, dt.getObservationUid().longValue());
              pStmt.setShort(i++, dt.getVersionCtrlNbr().shortValue());
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

              /**
               * During Pam porting process it was found that the CD_DESC_TXT width in observation hist table is 100
               * whereas in the observation table it is 1000. After discussion with Christi, it was decided to trim CD_DESC_TXT down to 100 characters so that the
               * process doesn't fail. 
               * "According to Christi: We know what the question labels are based on the code (ID) associated with the question that is also 
               * stored in the history. I don't think trimming of the question labels in the observation history tables for the legacy questions is an issue. 
               * 
               */
              if(dt.getCdDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else{
            	  if(dt.getCdDescTxt().length()>100){
            		  String s = "";
                	  s = dt.getCdDescTxt().substring(0, 99);
                  	  pStmt.setString(i++, s);
                  }else{
            		  pStmt.setString(i++, dt.getCdDescTxt());
            	  }
              }

              if(dt.getCdSystemCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCdSystemCd());

              if(dt.getCdSystemDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCdSystemDescTxt());

              if(dt.getConfidentialityCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfidentialityCd());

              if(dt.getConfidentialityDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getConfidentialityDescTxt());

              if(dt.getCtrlCdDisplayForm() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCtrlCdDisplayForm());

              if(dt.getCtrlCdUserDefined1() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCtrlCdUserDefined1());

              if(dt.getCtrlCdUserDefined2() == null)
                pStmt.setNull(i++,Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCtrlCdUserDefined2());

              if(dt.getCtrlCdUserDefined3() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCtrlCdUserDefined3());

              if(dt.getCtrlCdUserDefined4() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCtrlCdUserDefined4());

              if(dt.getDerivationExp() == null)
                pStmt.setNull(i++, Types.SMALLINT);
              else
                pStmt.setShort(i++, dt.getDerivationExp().shortValue());

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

              if(dt.getElectronicInd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getElectronicInd());

              if(dt.getGroupLevelCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
               pStmt.setString(i++, dt.getGroupLevelCd());

              if(dt.getJurisdictionCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getJurisdictionCd());

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

              if(dt.getLabConditionCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLabConditionCd());
              if(dt.getLocalId() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLocalId());

              if(dt.getObsDomainCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getObsDomainCd());

              if(dt.getObsDomainCdSt1() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getObsDomainCdSt1());
/*
              if(dt.getOrgAccessPermis() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getOrgAccessPermis());
*/
              if(dt.getPnuCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getPnuCd());

              if(dt.getPriorityCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getPriorityCd());

              if(dt.getPriorityDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getPriorityDescTxt());
/*
              if(dt.getProgAreaAccessPermis() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getProgAreaAccessPermis());
*/
              if(dt.getProgAreaCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getProgAreaCd());

              if(dt.getRecordStatusCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getRecordStatusTime());

              if(dt.getRepeatNbr() == null)
                pStmt.setNull(i++, Types.SMALLINT);
              else
                pStmt.setInt(i++, dt.getRepeatNbr().intValue());


                pStmt.setString(i++, dt.getStatusCd());

              if(dt.getStatusTime() == null)
                pStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
              else
                pStmt.setTimestamp(i++, dt.getStatusTime());

              if(dt.getSubjectPersonUid() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getSubjectPersonUid().longValue());

              if(dt.getTargetSiteCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTargetSiteCd());

              if(dt.getTargetSiteDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTargetSiteDescTxt());

              if(dt.getTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTxt());

              if(dt.getUserAffiliationTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getUserAffiliationTxt());

              if(dt.getValueCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getValueCd());

              if(dt.getYnuCd() == null)
                pStmt.setNull(i++, Types.CHAR);
              else
                pStmt.setString(i++, dt.getYnuCd());

			  if(dt.getProgramJurisdictionOid() == null)
				pStmt.setNull(i++, Types.BIGINT);
			  else
				pStmt.setLong(i++, dt.getProgramJurisdictionOid().longValue());

			  if(dt.getSharedInd() == null)
				pStmt.setNull(i++, Types.CHAR);
			  else
				pStmt.setString(i++, dt.getSharedInd());

            //changes for NPR Update
            if(dt.getAltCd() == null)
              pStmt.setNull(i++,Types.VARCHAR);
            else
            pStmt.setString(i++, dt.getAltCd());//53

            if(dt.getAltCdDescTxt() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getAltCdDescTxt());//54

            if(dt.getAltCdSystemCd() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getAltCdSystemCd());//55

            if(dt.getAltCdSystemDescTxt() == null)
              pStmt.setNull(i++, Types.VARCHAR);
            else
              pStmt.setString(i++, dt.getAltCdSystemDescTxt());//56

            if(dt.getCdDerivedInd() == null)
              pStmt.setNull(i++, Types.CHAR);
            else
              pStmt.setString(i++, dt.getCdDerivedInd());//57

            if(dt.getCdVersion() == null)
              pStmt.setNull(i++, Types.CHAR);
            else
              pStmt.setString(i++, dt.getCdVersion());

            if(dt.getRptToStateTime() == null)
              pStmt.setNull(i++, Types.TIMESTAMP);
            else
              pStmt.setTimestamp(i++, dt.getRptToStateTime());//58
            
            if(dt.getProcessingDecisionCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getProcessingDecisionCd());//59

            if(dt.getProcessingDecisionTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getProcessingDecisionTxt());//60
           
              resultCount = pStmt.executeUpdate();
              if ( resultCount != 1 )
              {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
            logger.fatal("SQLException ="+se.getMessage(),se);
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertObservationHist()


}//end of ObservationHistDAOImpl
