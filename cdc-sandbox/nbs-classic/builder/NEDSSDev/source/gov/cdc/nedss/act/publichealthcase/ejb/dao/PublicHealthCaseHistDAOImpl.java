package gov.cdc.nedss.act.publichealthcase.ejb.dao;

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
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
 * @updatedByAuthor Pradeep Sharma
 * @company: SAIC
 * @version 4.5
 */

public class PublicHealthCaseHistDAOImpl extends BMPBase{

  public final String SELECT_PUBLIC_HEALTH_CASE_HIST =
    " SELECT public_health_case_uid \"PublicHealthCaseUid\", "
    		+" version_ctrl_nbr \"versionCtrlNbr\" , " 
    + " activity_duration_amt \"ActivityDurationAmt\", "
    + " activity_duration_unit_cd \"ActivityDurationUnitCd\", "
    + " activity_from_time \"ActivityFromTime\", "
    + " activity_to_time \"ActivityToTime\", "
    + " add_reason_cd \"AddReasonCd\", "
    + " add_time \"AddTime\", "
    + " add_user_id \"AddUserId\", "
    + " cd \"Cd\", "
    + " cd_desc_txt \"CdDescTxt\", "
    + " cd_system_cd \"CdSystemCd\", "
    + " cd_system_desc_txt \"CdSystemDescTxt\", "
    + " case_class_cd \"CaseClassCd\", "
    + " confidentiality_cd \"ConfidentialityCd\", "
    + " confidentiality_desc_txt \"ConfidentialityDescTxt\", "
    + " detection_method_cd \"DetectionMethodCd\", "
    + " detection_method_desc_txt \"DetectionMethodDescTxt\", "
    + " diagnosis_time \"DiagnosisDate\", "
    + " disease_imported_cd \"DiseaseImportedCd\", "
    + " disease_imported_desc_txt \"DiseaseImportedDescTxt\", "
    + " effective_duration_amt \"EffectiveDurationAmt\", "
    + " effective_duration_unit_cd \"EffectiveDurationUnitCd\", "
    + " effective_from_time \"EffectiveFromTime\", "
    + " effective_to_time \"EffectiveToTime\", "
    + " group_case_cnt \"GroupCaseCnt\", "
    + " investigation_status_cd \"InvestigationStatusCd\", "
    + " jurisdiction_cd \"JurisdictionCd\", "
    + " last_chg_reason_cd \"LastChgReasonCd\", "
    + " last_chg_time \"LastChgTime\", "
    + " last_chg_user_id \"LastChgUserId\", "
    + " local_id \"LocalId\", "
    + " mmwr_year \"MmwrYear\", "
    + " mmwr_week \"MmwrWeek\", "
  //  + " org_access_permis \"OrgAccessPermis\", "
    + " outbreak_ind \"OutbreakInd\", "
    + " outbreak_from_time \"OutbreakFromTime\", "
    + " outbreak_to_time \"OutbreakToTime\", "
    + " outbreak_name \"OutbreakName\", "
    + " outcome_cd \"OutcomeCd\", "
    + " pat_age_at_onset \"PatAgeAtOnset\", "
    + " pat_age_at_onset_unit_cd \"PatAgeAtOnsetUnitCd\", "
    + " patient_group_id \"PatientGroupId\", "
  //  + " prog_area_access_permis \"ProgAreaAccessPermis\", "
    + " prog_area_cd \"ProgAreaCd\", "
    + " record_status_cd \"RecordStatusCd\", "
    + " record_status_time \"RecordStatusTime\", "
    + "  repeat_nbr \"RepeatNbr\", "
    + " rpt_cnty_cd \"RptCntyCd\", "
    + " rpt_form_cmplt_time \"RptFormCmpltTime\","
    + " rpt_source_cd \"RptSourceCd\", "
    + " rpt_source_cd_desc_txt \"RptSourceDescTxt\", "
    + " rpt_to_county_time \"RptToCountyTime\", "
    + " rpt_to_state_time \"RptToStateTime\", "
    + " status_cd \"StatusCd\", "
    + " status_time \"StatusTime\", "
    + " transmission_mode_cd \"TransmissionModeCd\", "
    + " transmission_mode_desc_txt \"TransmissionModeDescTxt\", "
    + " txt \"Txt\", "
    + " user_affiliation_txt \"UserAffiliationTxt\" , "
    + " case_type_cd \"caseTypeCd\" , "
    +" investigator_assigned_time \"investigatorAssignedTime\" ,"
    +" hospitalized_ind_cd \"hospitalizedIndCD\" ,"
    +" hospitalized_admin_time \"hospitalizedAdminTime\" ,"
    +" hospitalized_discharge_time \"hospitalizedDischargeTime\" ,"
    +" hospitalized_duration_amt \"hospitalizedDurationAmt\" ,"
    +" pregnant_ind_cd \"pregnantIndCD\" ,"
   // +" die_from_illness_ind_cd \"dieFromIllnessIndCD\" ,"
    +" day_care_ind_cd \"dayCareIndCD\" ,"
    +" food_handler_ind_cd \"foodHandlerIndCD\" ,"
    +" imported_country_cd \"importedCountryCD\" ,"
    +" imported_state_cd \"importedStateCD\" ,"
    +" imported_city_desc_txt \"importedCityDescTxt\" ,"
    +" imported_county_cd \"importedCountyCD\" ,"
    +" deceased_time \"deceasedTime\" ,"
    +" count_interval_cd \"countIntervalCd\","
    +" priority_cd \"priorityCd\","
    +" infectious_from_date \"infectiousFromDate\","
    +" infectious_to_date \"infectiousToDate\","
    +" contact_inv_status_cd \"contactInvStatus\","
    +" contact_inv_txt \"contactInvTxt\", "
    +" referral_basis_cd \"referralBasisCd\", "
	+" curr_process_state_cd \"currProcessStateCd\", "
	+" inv_priority_cd \"invPriorityCd\" "
    + " FROM public_health_case_hist "
    + " WHERE public_health_case_uid = ? and version_ctrl_nbr = ?";

public final String INSERT_PUBLIC_HEALTH_CASE_HIST =
    " INSERT into "
    + " public_health_case_hist "
    + " (public_health_case_uid,"
    + " version_ctrl_nbr, "
    + " activity_duration_amt, "
    + " activity_duration_unit_cd,"
    + " activity_from_time, "
    + " activity_to_time, "
    + " add_reason_cd, "
    + " add_time, "
    + " add_user_id, "
    + " cd, "
    + " cd_desc_txt, "
    + " cd_system_cd, "
    + " cd_system_desc_txt, "
    + " case_class_cd, "
    + " confidentiality_cd, "
    + " confidentiality_desc_txt, "
    + " detection_method_cd, "
    + " detection_method_desc_txt, "
    + " diagnosis_time, "
    + " disease_imported_cd, "
    + " disease_imported_desc_txt, "
    + " effective_duration_amt, "
    + " effective_duration_unit_cd,"
    + " effective_from_time, "
    + " effective_to_time, "
    + " group_case_cnt, "
    + " investigation_status_cd, "
    + " jurisdiction_cd, "
    + " last_chg_reason_cd, "
    + " last_chg_time, "
    + " last_chg_user_id, "
    + " local_id, "
    + " mmwr_year, "
    + " mmwr_week, "
  //  + " org_access_permis, "
    + " outbreak_ind, "
    + " outbreak_from_time, "
    + " outbreak_to_time, "
    + " outbreak_name, "
    + " outcome_cd, "
    + " pat_age_at_onset, "
    + " pat_age_at_onset_unit_cd,"
    + " patient_group_id, "
 //   + " prog_area_access_permis, "
    + " prog_area_cd, "
    + " record_status_cd, "
    + " record_status_time, "
    + " repeat_nbr, "
    + " rpt_cnty_cd, "
    + " rpt_form_cmplt_time, "
    + " rpt_source_cd, "
    + " rpt_source_cd_desc_txt, "
    + " rpt_to_county_time, "
    + " rpt_to_state_time, "
    + " status_cd, "
    + " status_time, "
    + " transmission_mode_cd, "
    + " transmission_mode_desc_txt, "
    + " txt, user_affiliation_txt, program_jurisdiction_oid, shared_ind, case_type_cd," 
    +" investigator_assigned_time,"
    +" hospitalized_ind_cd,"
    +" hospitalized_admin_time,"
    +" hospitalized_discharge_time,"
    +" hospitalized_duration_amt,"
    +" pregnant_ind_cd,"
   // +" die_from_illness_ind_cd,"
    +" day_care_ind_cd,"
    +" food_handler_ind_cd,"
    +" imported_country_cd,"
    +" imported_state_cd,"
    +" imported_city_desc_txt,"
    +" imported_county_cd,"
    +" deceased_time,"
    +" count_interval_cd,"
    +" priority_cd,"
    +" infectious_from_date,"
    +" infectious_to_date,"
    +" contact_inv_status_cd,"
    +" contact_inv_txt,"
    + "referral_basis_cd, "
    + "curr_process_state_cd, "
    + "inv_priority_cd)"
    + " VALUES(?,?,?,?,?,?,?,?,?,?,"
    + " ?,?,?,?,?,?,?,?,?,?,"
    + " ?,?,?,?,?,?,?,?,?,?,"
    + " ?,?,?,?,?,?,?,?,?,?,"
    + " ?,?,?,?,?,?,?,?,?,?,"
    + " ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


  static final LogUtils logger = new LogUtils(PublicHealthCaseHistDAOImpl.class.getName());
  private long phcUID = -1;
  private short versionCtrlNbr = 0;

  public PublicHealthCaseHistDAOImpl(long phcUID, short versionCtrlNbr)throws NEDSSDAOSysException, NEDSSSystemException {
    this.phcUID = phcUID;
    this.versionCtrlNbr = versionCtrlNbr;
//    getNextPHCHistId();
  }//end of constructor

  public PublicHealthCaseHistDAOImpl()throws NEDSSDAOSysException, NEDSSSystemException {
  }//end of constructor

  public void store(Object obj)
      throws NEDSSDAOSysException, NEDSSSystemException {
	  	try{
	        PublicHealthCaseDT dt = (PublicHealthCaseDT)obj;
	        if(dt == null)
	           throw new NEDSSSystemException("Error: try to store null PublicHealthCaseDT object.");
	        insertPublicHealthCaseHist(dt);
	  	}catch(Exception ex){
	  		logger.fatal("Exception  = "+ex.getMessage(), ex);
	  		throw new NEDSSSystemException(ex.toString());
	  	}
    }//end of store()

  public void store(Collection<Object> coll)
	   throws NEDSSDAOSysException, NEDSSSystemException {
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

  public PublicHealthCaseDT load(Long phcUid, Integer phcHistSeq) throws NEDSSSystemException,
     NEDSSSystemException {
	    logger.info("Starts loadObject() for a public health case history...");

	    try{
	        PublicHealthCaseDT phcDT = selectPublicHealthCaseHist(phcUid.longValue(), phcHistSeq.intValue());
	        phcDT.setItNew(false);
	        phcDT.setItDirty(false);
	        logger.info("Done loadObject() for a public health case history - return: " + phcDT.toString());
	        return phcDT;
	    }catch(Exception ex){
	    	logger.fatal("Exception  = "+ex.getMessage(), ex);
	  		throw new NEDSSSystemException(ex.toString());
	    }

    }//end of load

  public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

  /////////////////////////////////PRIVATE CLASS METHODS///////////////////////

  private PublicHealthCaseDT selectPublicHealthCaseHist(long phcUid, int versionCtrlNbr)throws NEDSSSystemException,
  NEDSSSystemException {


        PublicHealthCaseDT phcDT = new PublicHealthCaseDT();
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
                            "for selectPublicHealthCaseHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_PUBLIC_HEALTH_CASE_HIST);
            preparedStmt.setLong(1, phcUid);
	           preparedStmt.setLong(2, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           logger.debug("PublicHealthCaseDT object for Public Health Case history: obsUid = " + phcUid);

            resultSetMetaData = resultSet.getMetaData();

            phcDT = (PublicHealthCaseDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, phcDT.getClass());

            phcDT.setItNew(false);
            phcDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "public health case history; id = " + phcUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "public health case vo; id = " + phcUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return phcDT for public health case history");

        return phcDT;


  }//end of selectPublicHealthCaseHist(...)



  private void insertPublicHealthCaseHist(PublicHealthCaseDT dt)throws NEDSSDAOSysException,
    NEDSSSystemException {
    if(dt.getPublicHealthCaseUid() != null) {
      int resultCount = 0;
          //PreparedStatement preparedStmt = getPreparedStatement(NEDSSSqlQuery.INSERT_PERSON_HIST);
         // int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {

              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(INSERT_PUBLIC_HEALTH_CASE_HIST);
              pStmt.setLong(1, dt.getPublicHealthCaseUid().longValue());
              pStmt.setInt(2, dt.getVersionCtrlNbr().intValue());

              if(dt.getActivityDurationAmt() == null)
                pStmt.setNull(3, Types.VARCHAR);
              else
                pStmt.setString(3, dt.getActivityDurationAmt());

              if(dt.getActivityDurationUnitCd() == null)
                pStmt.setNull(4, Types.VARCHAR);
              else
                pStmt.setString(4, dt.getActivityDurationUnitCd());

              if(dt.getActivityFromTime() == null)
                pStmt.setNull(5, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(5, dt.getActivityFromTime());

              if(dt.getActivityToTime() == null)
                pStmt.setNull(6, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(6, dt.getActivityToTime());

              if(dt.getAddReasonCd() == null)
                pStmt.setNull(7, Types.VARCHAR);
              else
                pStmt.setString(7, dt.getAddReasonCd());

              if(dt.getAddTime() == null)
                pStmt.setNull(8, Types.VARCHAR);
              else
                pStmt.setTimestamp(8, dt.getAddTime());

              if(dt.getAddUserId() == null)
                pStmt.setNull(9, Types.BIGINT);
              else
                pStmt.setLong(9, dt.getAddUserId().longValue());

              if(dt.getCd() == null)
                pStmt.setNull(10, Types.VARCHAR);
              else
                pStmt.setString(10, dt.getCd());

              if(dt.getCdDescTxt() == null)
                pStmt.setNull(11, Types.VARCHAR);
              else
                pStmt.setString(11, dt.getCdDescTxt());

              if(dt.getCdSystemCd() == null)
                pStmt.setNull(12, Types.VARCHAR);
              else
                pStmt.setString(12, dt.getCdSystemCd());

              if(dt.getCdSystemDescTxt() == null)
                pStmt.setNull(13, Types.VARCHAR);
              else
                pStmt.setString(13, dt.getCdSystemDescTxt());

              if(dt.getCaseClassCd() == null)
                pStmt.setNull(14, Types.VARCHAR);
              else
                pStmt.setString(14, dt.getCaseClassCd());

              if(dt.getConfidentialityCd() == null)
                pStmt.setNull(15, Types.VARCHAR);
              else
                pStmt.setString(15, dt.getConfidentialityCd());

              if(dt.getConfidentialityDescTxt() == null)
                pStmt.setNull(16, Types.VARCHAR);
              else
                pStmt.setString(16, dt.getConfidentialityDescTxt());

              if(dt.getDetectionMethodCd() == null)
                pStmt.setNull(17, Types.VARCHAR);
              else
                pStmt.setString(17, dt.getDetectionMethodCd());

              if(dt.getDetectionMethodDescTxt() == null)
                pStmt.setNull(18, Types.VARCHAR);
              else
                pStmt.setString(18, dt.getDetectionMethodDescTxt());

              if(dt.getDiagnosisTime() == null)
                pStmt.setNull(19, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(19, dt.getDiagnosisTime());

              if(dt.getDiseaseImportedCd() == null)
                pStmt.setNull(20, Types.VARCHAR);
              else
                pStmt.setString(20, dt.getDiseaseImportedCd());

              if(dt.getDiseaseImportedDescTxt() == null)
                pStmt.setNull(21, Types.VARCHAR);
              else
                pStmt.setString(21, dt.getDiseaseImportedDescTxt());

              if(dt.getEffectiveDurationAmt() == null)
                pStmt.setNull(22, Types.VARCHAR);
              else
                pStmt.setString(22, dt.getEffectiveDurationAmt());

              if(dt.getEffectiveDurationUnitCd() == null)
                pStmt.setNull(23, Types.VARCHAR);
              else
                pStmt.setString(23, dt.getEffectiveDurationUnitCd());

              if(dt.getEffectiveFromTime() == null)
                pStmt.setNull(24, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(24, dt.getEffectiveFromTime());

              if(dt.getEffectiveToTime() == null)
                pStmt.setNull(25, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(25, dt.getEffectiveToTime());

              if(dt.getGroupCaseCnt() == null)
                pStmt.setNull(26, Types.SMALLINT);
              else
                pStmt.setShort(26, dt.getGroupCaseCnt().shortValue());

              if(dt.getInvestigationStatusCd() == null)
                pStmt.setNull(27, Types.VARCHAR);
              else
                pStmt.setString(27, dt.getInvestigationStatusCd());

              if(dt.getJurisdictionCd() == null)
                pStmt.setNull(28, Types.VARCHAR);
              else
                pStmt.setString(28, dt.getJurisdictionCd());

              if(dt.getLastChgReasonCd() == null)
                pStmt.setNull(29, Types.VARCHAR);
              else
                pStmt.setString(29, dt.getLastChgReasonCd());

              if(dt.getLastChgTime() == null)
                pStmt.setNull(30, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(30, dt.getLastChgTime());

              if(dt.getLastChgUserId() == null)
                pStmt.setNull(31, Types.BIGINT);
              else
                pStmt.setLong(31, dt.getLastChgUserId().longValue());

              if(dt.getLocalId() == null)
                pStmt.setNull(32, Types.VARCHAR);
              else
                pStmt.setString(32, dt.getLocalId());

              if(dt.getMmwrYear() == null)
                pStmt.setNull(33, Types.VARCHAR);
              else
                pStmt.setString(33, dt.getMmwrYear());

              if(dt.getMmwrWeek() == null)
                pStmt.setNull(34, Types.VARCHAR);
              else
                pStmt.setString(34, dt.getMmwrWeek());

        /*      if(dt.getOrgAccessPermis() == null)
                pStmt.setNull(35, Types.VARCHAR);
              else
                pStmt.setString(35, dt.getOrgAccessPermis());

          */
              if(dt.getOutbreakInd() == null)
                pStmt.setNull(35, Types.VARCHAR);
              else
                pStmt.setString(35, dt.getOutbreakInd());

              if(dt.getOutbreakFromTime() == null)
                pStmt.setNull(36, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(36, dt.getOutbreakFromTime());

              if(dt.getOutbreakToTime() == null)
                pStmt.setNull(37, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(37, dt.getOutbreakToTime());

              if(dt.getOutbreakName() == null)
                pStmt.setNull(38, Types.VARCHAR);
              else
                pStmt.setString(38, dt.getOutbreakName());

              if(dt.getOutcomeCd() == null)
                pStmt.setNull(39, Types.VARCHAR);
              else
                pStmt.setString(39, dt.getOutcomeCd());

              if(dt.getPatAgeAtOnset() == null)
                pStmt.setNull(40, Types.VARCHAR);
              else
                pStmt.setString(40, dt.getPatAgeAtOnset());

              if(dt.getPatAgeAtOnsetUnitCd() == null)
                pStmt.setNull(41, Types.VARCHAR);
              else
                pStmt.setString(41, dt.getPatAgeAtOnsetUnitCd());

              if(dt.getPatientGroupId() == null)
                pStmt.setNull(42, Types.BIGINT);
              else
                pStmt.setLong(42, dt.getPatientGroupId().longValue());

          /*    if(dt.getProgAreaAccessPermis() == null)
                pStmt.setNull(44, Types.VARCHAR);
              else
                pStmt.setString(44, dt.getProgAreaAccessPermis());

            */
              if(dt.getProgAreaCd() == null)
                pStmt.setNull(43, Types.VARCHAR);
              else
                pStmt.setString(43, dt.getProgAreaCd());

              if(dt.getRecordStatusCd() == null)
                pStmt.setNull(44, Types.VARCHAR);
              else
                pStmt.setString(44, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                pStmt.setNull(45, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(45, dt.getRecordStatusTime());

              if(dt.getRepeatNbr() == null)
                pStmt.setNull(46, Types.SMALLINT);
              else
                pStmt.setShort(46, dt.getRepeatNbr().shortValue());

              if(dt.getRptCntyCd() == null)
                pStmt.setNull(47, Types.VARCHAR);
              else
                pStmt.setString(47, dt.getRptCntyCd());

              if(dt.getRptFormCmpltTime() == null)
                pStmt.setNull(48, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(48, dt.getRptFormCmpltTime());

              if(dt.getRptSourceCd() == null)
                pStmt.setNull(49, Types.VARCHAR);
              else
                pStmt.setString(49, dt.getRptSourceCd());

              if(dt.getRptSourceCdDescTxt() == null)
                pStmt.setNull(50, Types.VARCHAR);
              else
                pStmt.setString(50, dt.getRptSourceCdDescTxt());

              if(dt.getRptToCountyTime() == null)
                pStmt.setNull(51, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(51, dt.getRptToCountyTime());

              if(dt.getRptToStateTime() == null)
                pStmt.setNull(52, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(52, dt.getRptToStateTime());

              pStmt.setString(53, dt.getStatusCd());

              pStmt.setTimestamp(54, dt.getStatusTime());

              if(dt.getTransmissionModeCd() == null)
                pStmt.setNull(55, Types.VARCHAR);
              else
                pStmt.setString(55, dt.getTransmissionModeCd());

              if(dt.getTransmissionModeDescTxt() == null)
                pStmt.setNull(56, Types.VARCHAR);
              else
                pStmt.setString(56, dt.getTransmissionModeDescTxt());

              if(dt.getTxt() == null)
                pStmt.setNull(57, Types.VARCHAR);
              else
                pStmt.setString(57, dt.getTxt());

              if(dt.getUserAffiliationTxt() == null)
                pStmt.setNull(58, Types.VARCHAR);
              else
                pStmt.setString(58, dt.getUserAffiliationTxt());

              if(dt.getProgramJurisdictionOid() == null)
                pStmt.setNull(59, Types.BIGINT);
              else
                pStmt.setLong(59, dt.getProgramJurisdictionOid().longValue());

              pStmt.setString(60, dt.getSharedInd());

              if(dt.getCaseTypeCd()== null)
                pStmt.setNull(61, Types.VARCHAR);
              else
                pStmt.setString(61, dt.getCaseTypeCd());
              
              if(dt.getInvestigatorAssignedTime() == null)
                  pStmt.setNull(62, Types.TIMESTAMP);
                else
                	pStmt.setTimestamp(62, dt.getInvestigatorAssignedTime());
              
              if(dt.getHospitalizedIndCd() == null)
                  pStmt.setNull(63, Types.VARCHAR);
                else
                	pStmt.setString(63, dt.getHospitalizedIndCd());
              
              if(dt.getHospitalizedAdminTime() == null)
                  pStmt.setNull(64, Types.TIMESTAMP);
                else
                	pStmt.setTimestamp(64, dt.getHospitalizedAdminTime());
              
              if(dt.getHospitalizedDischargeTime() == null)
                  pStmt.setNull(65, Types.TIMESTAMP);
                else
                	pStmt.setTimestamp(65, dt.getHospitalizedDischargeTime());
              
              if(dt.getHospitalizedDurationAmt() == null)
                  pStmt.setNull(66, Types.NUMERIC);
                else
                	pStmt.setInt(66, dt.getHospitalizedDurationAmt().intValue());
              
              if(dt.getPregnantIndCd() == null)
                  pStmt.setNull(67, Types.VARCHAR);
                else
                	pStmt.setString(67, dt.getPregnantIndCd());
              
             /* if(dt.getDieFromIllnessIndCD() == null)
                  pStmt.setNull(68, Types.VARCHAR);
                else
                	pStmt.setString(68, dt.getDieFromIllnessIndCD());*/
                          
              if(dt.getDayCareIndCd() == null)
                  pStmt.setNull(68, Types.VARCHAR);
                else
                	pStmt.setString(68, dt.getDayCareIndCd());
              
              if(dt.getFoodHandlerIndCd() == null)
                  pStmt.setNull(69, Types.VARCHAR);
                else
                	pStmt.setString(69, dt.getFoodHandlerIndCd());
              
              if(dt.getImportedCountryCd() == null)
                  pStmt.setNull(70, Types.VARCHAR);
                else
                	pStmt.setString(70, dt.getImportedCountryCd());
                          
              if(dt.getImportedStateCd() == null)
                  pStmt.setNull(71, Types.VARCHAR);
                else
                	pStmt.setString(71, dt.getImportedStateCd());  
              
              if(dt.getImportedCityDescTxt() == null)
                  pStmt.setNull(72, Types.VARCHAR);
                else
                	pStmt.setString(72, dt.getImportedCityDescTxt());
              
              if(dt.getImportedCountyCd() == null)
                  pStmt.setNull(73, Types.VARCHAR);
                else
                	pStmt.setString(73, dt.getImportedCountyCd());  
              
              if(dt.getDeceasedTime() == null)
            	  pStmt.setNull(74, Types.TIMESTAMP);
                else
                	pStmt.setTimestamp(74, dt.getDeceasedTime());

              if(dt.getCountIntervalCd() == null)
                  pStmt.setNull(75, Types.VARCHAR);
                else
                	pStmt.setString(75, dt.getCountIntervalCd());  
              
            //added for contact tracing
              if(dt.getPriorityCd() == null)
            	  pStmt.setNull(76, Types.VARCHAR);
                else
                	pStmt.setString(76, dt.getPriorityCd()); 
              
              if(dt.getInfectiousFromDate() == null)
            	  pStmt.setNull(77, Types.TIMESTAMP);
                else
                	pStmt.setTimestamp(77, dt.getInfectiousFromDate());
              
              if(dt.getInfectiousToDate() == null)
            	  pStmt.setNull(78, Types.TIMESTAMP);
                else
                	pStmt.setTimestamp(78, dt.getInfectiousToDate());
              
              if(dt.getContactInvStatus() == null)
            	  pStmt.setNull(79, Types.VARCHAR);
                else
                	pStmt.setString(79, dt.getContactInvStatus());
              
              if(dt.getContactInvTxt() == null)
            	  pStmt.setNull(80, Types.VARCHAR);
                else
                	pStmt.setString(80, dt.getContactInvTxt()); 
              
              
              if(dt.getReferralBasisCd()== null)
            	  pStmt.setNull(81, Types.VARCHAR);
                else
                	pStmt.setString(81, dt.getReferralBasisCd()); 
              if(dt.getCurrProcessStateCd()== null)
            	  pStmt.setNull(82, Types.VARCHAR);
                else
                	pStmt.setString(82, dt.getCurrProcessStateCd()); 
              if(dt.getInvPriorityCd() == null)
            	  pStmt.setNull(83, Types.VARCHAR);
                else
                	pStmt.setString(83, dt.getInvPriorityCd()); 

              
              resultCount = pStmt.executeUpdate();
              if ( resultCount != 1 )
              {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
            se.printStackTrace();
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertPublicHealthCasesHist()

}//end of class
