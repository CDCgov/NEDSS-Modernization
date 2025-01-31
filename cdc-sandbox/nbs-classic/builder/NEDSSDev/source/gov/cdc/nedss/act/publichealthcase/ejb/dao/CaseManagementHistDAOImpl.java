package gov.cdc.nedss.act.publichealthcase.ejb.dao;

import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementHistDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
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

public class CaseManagementHistDAOImpl extends BMPBase{

	static final LogUtils logger = new LogUtils(CaseManagementHistDAOImpl.class.getName());

	public static final String INSERT_CASE_MANAGEMENT_HIST =
			"INSERT INTO " + DataTables.CASE_MANAGEMENT_HIST_TABLE+
			 " (public_health_case_uid "+
			",case_management_uid"+		
			", version_ctrl_nbr"+
			", status_900" +
			", ehars_id"+ 
			", epi_link_id "+
			", field_foll_up_ooj_outcome "+
			", field_record_number "+
			", fld_foll_up_dispo "+
			", fld_foll_up_dispo_date "+
			", fld_foll_up_exam_date "+
			", fld_foll_up_expected_date "+
			", fld_foll_up_expected_in "+
			", fld_foll_up_internet_outcome "+
			", fld_foll_up_notification_plan "+
			", fld_foll_up_prov_diagnosis "+
			", fld_foll_up_prov_exm_reason "+
			", init_foll_up "+
			", init_foll_up_clinic_code "+
			", init_foll_up_closed_date "+
			", init_foll_up_notifiable "+
			", internet_foll_up "+
			", ooj_agency "+
			", ooj_due_date "+
			", ooj_number "+
			", pat_intv_status_cd "+
			", subj_complexion "+
			", subj_hair "+
			", subj_height "+
			", subj_oth_idntfyng_info "+
			", subj_size_build "+
			", surv_closed_date "+
			", surv_patient_foll_up "+
			", surv_prov_diagnosis "+
			", surv_prov_exm_reason "+
			", surv_provider_contact "+
			", act_ref_type_cd "+
			", initiating_agncy "+
			", ooj_initg_agncy_outc_due_date "+
			", ooj_initg_agncy_outc_snt_date "+
			", ooj_initg_agncy_recd_date "+
			", case_review_status "+
			", surv_assigned_date"+
			", foll_up_assigned_date"+
			", init_foll_up_assigned_date"+
			", interview_assigned_date"+
			", init_interview_assigned_date"+
			", case_closed_date) "+
			"   VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String SELECT_CASE_MANAGEMENT_HIST =
			"SELECT  public_health_case_uid \"publicHealthCaseUid\","
					+ "case_management_uid \"caseManagementUid\", "
					+ "version_ctrl_nbr \"versionCtrlNbr\","
					+ " status_900 \"status900\", "	
					+ " ehars_id \"eharsId\", "	
					+ " epi_link_id \"epiLinkId\", "	
					+ " field_foll_up_ooj_outcome \"fieldFollUpOojOutcome\", "	
					+ " field_record_number \"fieldRecordNumber\", "	
					+ " fld_foll_up_dispo \"fldFollUpDispo\", "	
					+ " fld_foll_up_dispo_date \"fldFollUpDispoDate\", "	
					+ " fld_foll_up_exam_date \"fldFollUpExamDate\", "	
					+ " fld_foll_up_expected_date \"fldFollUpExpectedDate\", "	
					+ " fld_foll_up_expected_in \"fldFollUpExpectedIn\", "	
					+ " fld_foll_up_internet_outcome \"fldFollUpInternetOutcome\", "	
					+ " fld_foll_up_notification_plan \"fldFollUpNotificationPlan\", "	
					+ " fld_foll_up_prov_diagnosis \"fldFollUpProvDiagnosis\", "	
					+ " fld_foll_up_prov_exm_reason \"fldFollUpProvExmReason\", "	
					+ " init_foll_up \"initFollUp\", "	
					+ " init_foll_up_clinic_code \"initFollUpClinicCode\", "	
					+ " init_foll_up_closed_date \"initFollUpClosedDate\", "	
					+ " init_foll_up_notifiable \"initFollUpNotifiable\", "	
					+ " internet_foll_up \"internetFollUp\", "	
					+ " ooj_agency \"oojAgency\", "	
					+ " ooj_due_date \"oojDueDate\", "	
					+ " ooj_number \"oojNumber\", "	
					+ " pat_intv_status_cd \"patIntvStatusCd\", "	
					+ " subj_complexion \"subjComplexion\", "	
					+ " subj_hair \"subjHair\", "	
					+ " subj_height \"subjHeight\", "	
					+ " subj_oth_idntfyng_info \"subjOthIdntfyngInfo\", "	
					+ " subj_size_build \"subjSizeBuild\", "	
					+ " surv_closed_date \"survClosedDate\", "	
					+ " surv_patient_foll_up \"survPatientFollUp\", "	
					+ " surv_prov_diagnosis \"survProvDiagnosis\", "	
					+ " surv_prov_exm_reason \"survProvExmReason\", "	
					+ " surv_provider_contact \"survProviderContact\", "	
					+ " act_ref_type_cd \"actRefTypeCd\", "	
					+ " initiating_agncy \"initiatingAgncy\", "	
					+ " ooj_initg_agncy_outc_due_date \"oojInitgAgncyOutcDueDate\", "	
					+ " ooj_initg_agncy_outc_snt_date \"oojInitgAgncyOutcSntDate\", "	
					+ " ooj_initg_agncy_recd_date \"oojInitgAgncyRecdDate\", "
					+ " case_review_status \"caseReviewStatus\", "
					+ " surv_assigned_date \"survAssignedDate\", "
					+ " foll_up_assigned_date \"follUpAssignedDate\", "
					+ " init_foll_up_assigned_date \"initFollUpAssignedDate\", "
					+ " interview_assigned_date \"interviewAssignedDate\", "
					+ " init_interview_assigned_date \"initInterviewAssignedDate\", "
					+ " case_closed_date \"caseClosedDate\" "
					+ " FROM "					
					+ DataTables.CASE_MANAGEMENT_HIST_TABLE
					+ " WHERE public_health_case_uid = ?";

	

	


	public void insertCaseManagementHistDT (CaseManagementDT caseManagementDT,PublicHealthCaseVO publicHealthCaseVO)throws  NEDSSSystemException
	{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_CASE_MANAGEMENT_HIST="+INSERT_CASE_MANAGEMENT_HIST);
		try{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_CASE_MANAGEMENT_HIST);
			int versionCtrlNbr = publicHealthCaseVO.getThePublicHealthCaseDT().getVersionCtrlNbr();
			setPreparedStatement(preparedStmt, versionCtrlNbr, caseManagementDT );
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount in insertcaseManagementHistDT is " + resultCount);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while inserting " +
					"caseManagementHistDT into caseManagementHistDT:"+caseManagementDT.toString(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting into caseManagementHistDT, caseManagementHistDT="+ caseManagementDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}//end of insert method


	


	private  PreparedStatement setPreparedStatement(PreparedStatement preparedStmt, int versionCtrlNbr, CaseManagementDT caseManagementHistDT){
		try {
			int i =1;
			preparedStmt.setLong(i++, caseManagementHistDT.getPublicHealthCaseUid());//1
			preparedStmt.setLong(i++, caseManagementHistDT.getCaseManagementUid());//2
			preparedStmt.setInt(i++, versionCtrlNbr);//3
			
			preparedStmt.setString(i++, caseManagementHistDT.getStatus900());//4
			preparedStmt.setString(i++, caseManagementHistDT.getEharsId());//5
			preparedStmt.setString(i++, caseManagementHistDT.getEpiLinkId());//6
			preparedStmt.setString(i++, caseManagementHistDT.getFieldFollUpOojOutcome());//7
			preparedStmt.setString(i++, caseManagementHistDT.getFieldRecordNumber());//8
			preparedStmt.setString(i++, caseManagementHistDT.getFldFollUpDispo());//9

			if(caseManagementHistDT.getFldFollUpDispoDate()==null)//10
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getFldFollUpDispoDate());//1

			if(caseManagementHistDT.getFldFollUpExamDate()==null)//11
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getFldFollUpExamDate());//11

			if(caseManagementHistDT.getFldFollUpExpectedDate()==null)//12
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getFldFollUpExpectedDate());//12

			preparedStmt.setString(i++, caseManagementHistDT.getFldFollUpExpectedIn());//13
			preparedStmt.setString(i++, caseManagementHistDT.getFldFollUpInternetOutcome());//14
			preparedStmt.setString(i++, caseManagementHistDT.getFldFollUpNotificationPlan());//15
			preparedStmt.setString(i++, caseManagementHistDT.getFldFollUpProvDiagnosis());//16
			preparedStmt.setString(i++, caseManagementHistDT.getFldFollUpProvExmReason());//17
			preparedStmt.setString(i++, caseManagementHistDT.getInitFollUp());//18
			preparedStmt.setString(i++, caseManagementHistDT.getInitFollUpClinicCode());//19


			if(caseManagementHistDT.getInitFollUpClosedDate()==null)//20
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getInitFollUpClosedDate());//20

			preparedStmt.setString(i++, caseManagementHistDT.getInitFollUpNotifiable());//21
			preparedStmt.setString(i++, caseManagementHistDT.getInternetFollUp());//22
			preparedStmt.setString(i++, caseManagementHistDT.getOojAgency());//23



			if(caseManagementHistDT.getOojDueDate()==null)//24
			preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
			preparedStmt.setTimestamp(i++, caseManagementHistDT.getOojDueDate());//24

			preparedStmt.setString(i++, caseManagementHistDT.getOojNumber());//25
			preparedStmt.setString(i++, caseManagementHistDT.getPatIntvStatusCd());//26
			preparedStmt.setString(i++, caseManagementHistDT.getSubjComplexion());//27
			preparedStmt.setString(i++, caseManagementHistDT.getSubjHair());//28			
			preparedStmt.setString(i++, caseManagementHistDT.getSubjHeight());//29

			preparedStmt.setString(i++, caseManagementHistDT.getSubjOthIdntfyngInfo());//30
			preparedStmt.setString(i++, caseManagementHistDT.getSubjSizeBuild());//31

			if(caseManagementHistDT.getSurvClosedDate()==null)//32
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getSurvClosedDate());//32

			preparedStmt.setString(i++, caseManagementHistDT.getSurvPatientFollUp());//33
			preparedStmt.setString(i++, caseManagementHistDT.getSurvProvDiagnosis());//34
			preparedStmt.setString(i++, caseManagementHistDT.getSurvProvExmReason());//35
			preparedStmt.setString(i++, caseManagementHistDT.getSurvProviderContact());//36
			preparedStmt.setString(i++, caseManagementHistDT.getActRefTypeCd());//37
			preparedStmt.setString(i++, caseManagementHistDT.getInitiatingAgncy());//38

			if(caseManagementHistDT.getOojInitgAgncyOutcDueDate()==null)//39
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getOojInitgAgncyOutcDueDate());//39
				
			if(caseManagementHistDT.getOojInitgAgncyOutcSntDate()==null)//40
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getOojInitgAgncyOutcSntDate());//40

			if(caseManagementHistDT.getOojInitgAgncyRecdDate()==null)//41
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getOojInitgAgncyRecdDate());//41
			
			preparedStmt.setString(i++, caseManagementHistDT.getCaseReviewStatus());//42
			
			if(caseManagementHistDT.getSurvAssignedDate()==null)//43
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getSurvAssignedDate());//43
			
			if(caseManagementHistDT.getFollUpAssignedDate()==null)//44
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getFollUpAssignedDate());//44
			
			if(caseManagementHistDT.getInitFollUpAssignedDate()==null)//45
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getInitFollUpAssignedDate());//45
			
			if(caseManagementHistDT.getInterviewAssignedDate()==null)//46
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getInterviewAssignedDate());//46
			
			if(caseManagementHistDT.getInitInterviewAssignedDate()==null)//47
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getInitInterviewAssignedDate());//47
			
			if(caseManagementHistDT.getCaseClosedDate()==null)//48
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				preparedStmt.setTimestamp(i++, caseManagementHistDT.getCaseClosedDate());//48
			
		} catch (SQLException e) {
			logger.fatal("SQLException while Setting PreparedStatement Values for case_management: \n"+e.getMessage(), e);
			throw new NEDSSDAOSysException( e.toString() );
		}
		return preparedStmt;
	}



	public CaseManagementHistDT selectcaseManagementHistDT(long phcUID) throws NEDSSSystemException
	{
		CaseManagementHistDT caseManagementHistDT = new CaseManagementHistDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();


		/**
		 * Selects a caseManagementHistDT from Case_ManagementDT table
		 */
		try
		{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_CASE_MANAGEMENT_HIST);
			preparedStmt.setLong(1, phcUID);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			caseManagementHistDT = (CaseManagementHistDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, caseManagementHistDT.getClass());
			caseManagementHistDT.setItNew(false);
			caseManagementHistDT.setItDirty(false);
			caseManagementHistDT.setItDelete(false);
		}
		catch(SQLException e)
		{
			logger.fatal("SQLException while selecting " +
					"a CaseManagementDT ; uid = " + phcUID, e);
			throw new NEDSSDAOSysException( e.getMessage());
		}
		catch(Exception exception)
		{
			logger.fatal("Exception while selecting " +
					"a CaseManagementDT; uid = " + phcUID, exception);
			throw new NEDSSDAOSysException( exception.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("return a CaseManagementDT object");
		return caseManagementHistDT;
	}//end of selecting a caseManagementHistDT

}

