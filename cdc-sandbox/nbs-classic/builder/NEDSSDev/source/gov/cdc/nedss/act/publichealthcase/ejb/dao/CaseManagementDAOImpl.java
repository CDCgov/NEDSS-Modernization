package gov.cdc.nedss.act.publichealthcase.ejb.dao;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * CaseManagementDAOImpl is the DAO class to read, write, and update CaseManagement data(new Object since Release 4.5)
 * @author Pradeep Kumar Sharma
 *
 */
public class CaseManagementDAOImpl extends BMPBase{

	static final LogUtils logger = new LogUtils(CaseManagementDAOImpl.class.getName());

	public static final String INSERT_CASE_MANAGEMENT =
			"INSERT INTO " + DataTables.CASE_MANAGEMENT_TABLE+
			 " (status_900" +
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
			", ooj_initg_agncy_recd_date"+
			", case_review_status"+
			", surv_assigned_date"+
			", foll_up_assigned_date"+
			", init_foll_up_assigned_date"+
			", interview_assigned_date"+
			", init_interview_assigned_date"+
			", case_closed_date"+
			", case_review_status_date" +
			", public_health_case_uid)"+
			"   VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String SELECT_CASE_MANAGEMENT =
			"SELECT  case_management_uid \"caseManagementUid\", "
					+ " public_health_case_uid \"publicHealthCaseUid\", "
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
					+ " case_closed_date \"caseClosedDate\", "
					+ " case_review_status_date \"caseReviewStatusDate\" "
					+ " FROM "
					+ DataTables.CASE_MANAGEMENT_TABLE
					+ " WHERE public_health_case_uid = ?";


	public static final String SELECT_CASE_MANAGEMENT_TABLE_UID =
			"SELECT public_health_case_uid FROM "
					+ DataTables.CASE_MANAGEMENT_TABLE
					+ " WHERE public_health_case_uid = ?  ";

	public static final String UPDATE_CASE_MANAGEMENT=
			"UPDATE " + DataTables.CASE_MANAGEMENT_TABLE+" set "
			+" status_900= ?,"
			+" ehars_id= ?,"
			+" epi_link_id= ?,"
			+" field_foll_up_ooj_outcome= ?,"
			+" field_record_number= ?,"
			+" fld_foll_up_dispo= ?,"
			+" fld_foll_up_dispo_date= ?,"
			+" fld_foll_up_exam_date= ?,"
			+" fld_foll_up_expected_date= ?,"
			+" fld_foll_up_expected_in= ?,"
			+" fld_foll_up_internet_outcome= ?,"
			+" fld_foll_up_notification_plan= ?,"
			+" fld_foll_up_prov_diagnosis= ?,"
			+" fld_foll_up_prov_exm_reason= ?,"
			+" init_foll_up= ?,"
			+" init_foll_up_clinic_code= ?,"
			+" init_foll_up_closed_date= ?,"
			+" init_foll_up_notifiable= ?,"
			+" internet_foll_up= ?,"
			+" ooj_agency= ?,"
			+" ooj_due_date= ?,"
			+" ooj_number= ?,"
			+" pat_intv_status_cd= ?,"
			+" subj_complexion= ?,"
			+" subj_hair= ?,"
			+" subj_height= ?,"
			+" subj_oth_idntfyng_info= ?,"
			+" subj_size_build= ?,"
			+" surv_closed_date= ?,"
			+" surv_patient_foll_up= ?,"
			+" surv_prov_diagnosis= ?,"
			+" surv_prov_exm_reason= ?,"
			+" surv_provider_contact= ?,"
			+" act_ref_type_cd= ?,"
			+" initiating_agncy= ?,"
			+" ooj_initg_agncy_outc_due_date= ?,"
			+" ooj_initg_agncy_outc_snt_date= ?,"
			+" ooj_initg_agncy_recd_date= ?,"
			+" case_review_status=?, "
			+" surv_assigned_date=?, "
			+" foll_up_assigned_date=?, "
			+" init_foll_up_assigned_date=?, "
			+" interview_assigned_date=?, "
			+" init_interview_assigned_date=?, "
			+" case_closed_date=?, "
			+" case_review_status_date=? "
			+" WHERE PUBLIC_HEALTH_CASE_UID= ?";

	public static final String UPDATE_CASE_MANAGEMENT_EPILINK=
			"UPDATE " + DataTables.CASE_MANAGEMENT_TABLE+" set "
			+" epi_link_id= ? "
			+" WHERE epi_link_id= ?";


	//Only logical delete is allowed
	public static final String LOGICALLY_DELETE_CASE_MANAGEMENT ="";
	public static final String PHYSICALLY_DELETE_CASE_MANAGEMENT="delete from " + DataTables.CASE_MANAGEMENT_TABLE+" where public_health_case_uid=?";

	public static final String SELECT_CASE_MANAGEMENT_EPILINK =
			"SELECT  case_management_uid \"caseManagementUid\", "
					+ " c.public_health_case_uid \"publicHealthCaseUid\", "
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
					+ " case_closed_date \"caseClosedDate\" , "
					+ " case_review_status_date \"caseReviewStatusDate\" , "
					+ " phc.local_id \"localId\" "
					+ " FROM "
					+ DataTables.CASE_MANAGEMENT_TABLE
					+" c,  " + DataTables.PUBLIC_HEALTH_CASE_TABLE + " phc "
					+ " WHERE epi_link_id = ? and c.public_health_case_uid = phc.public_health_case_uid";


    public void logDelCaseManagementDT(CaseManagementDT caseManagementDT, PublicHealthCaseVO publicHealthCaseVO)
            throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        logger.debug(" LOGICALLY_DELETE_NBS_ANSWER=" + LOGICALLY_DELETE_CASE_MANAGEMENT);
        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for logDelCaseManagementDT ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        try
        {
            preparedStmt = dbConnection.prepareStatement(LOGICALLY_DELETE_CASE_MANAGEMENT);
            int k = 1;
            preparedStmt.setTimestamp(k++, publicHealthCaseVO.getThePublicHealthCaseDT().getLastChgTime()); // 1
            preparedStmt.setLong(k++, caseManagementDT.getLastChgUserId().longValue());// 2

            preparedStmt.setString(k++, publicHealthCaseVO.getThePublicHealthCaseDT().getRecordStatusCd()); // 3
            preparedStmt.setTimestamp(k++, publicHealthCaseVO.getThePublicHealthCaseDT().getRecordStatusTime()); // 4
            preparedStmt.setLong(k++, caseManagementDT.getCaseManagementUid().longValue()); // 5

            resultCount = preparedStmt.executeUpdate();
            logger.debug("resultCount is " + resultCount);
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException while running " + LOGICALLY_DELETE_CASE_MANAGEMENT);
            logger.fatal("SQLException while updateCaseManagementDT " + "caseManagementDT into CASE_MANAGEMENT:"
                    + caseManagementDT.toString(), sqlex);
            throw new NEDSSDAOSysException(sqlex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Exception while running " + LOGICALLY_DELETE_CASE_MANAGEMENT);
            logger.fatal("Error while update into CASE_MANAGEMENT, caseManagementDT=" + caseManagementDT.toString(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }// end of log delete method


    private void insertCaseManagementDT(CaseManagementDT caseManagementDT) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        logger.debug("INSERT_CASE_MANAGEMENT=" + INSERT_CASE_MANAGEMENT);
        try
        {
        	updateCaseManagementWithEPIIDandFRNum(caseManagementDT);
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(INSERT_CASE_MANAGEMENT);
            setPreparedStatement(preparedStmt, caseManagementDT);
            resultCount = preparedStmt.executeUpdate();
            logger.debug("resultCount in insertcaseManagementDT is " + resultCount);
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException while running " + INSERT_CASE_MANAGEMENT);
            logger.fatal(
                    "SQLException while inserting " + "caseManagementDT into caseManagementDT:"
                            + caseManagementDT.toString(), sqlex);
            throw new NEDSSDAOSysException(sqlex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Exception while running " + INSERT_CASE_MANAGEMENT);
            logger.fatal(
                    "Error while inserting into caseManagementDT, caseManagementDT=" + caseManagementDT.toString(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }// end of insert method

    protected boolean caseManagementExists(long phcUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(CaseManagementDAOImpl.SELECT_CASE_MANAGEMENT_TABLE_UID);
            logger.debug("phcUID = " + phcUID);
            preparedStmt.setLong(1, phcUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                phcUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch (SQLException exception)
        {
            logger.fatal("SQLException while running " + SELECT_CASE_MANAGEMENT_TABLE_UID);
            logger.fatal("SQLException while checking for an"
                    + " existing CaseManagementDT's uid in CASE_MANAGEMENT table -> " + phcUID, exception);
            throw new NEDSSDAOSysException(exception.getMessage());
        }
        catch (NEDSSSystemException nexception)
        {
            logger.fatal("Exception while running " + SELECT_CASE_MANAGEMENT_TABLE_UID);
            logger.fatal("Exception while getting dbConnection for checking for an"
                    + " existing caseManagement's uid -> " + phcUID, nexception);
            throw new NEDSSDAOSysException(nexception.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return returnValue;
    }

    public void removeCaseManagementDT(Long phcuid)
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        try
        {
            dbConnection = getConnection();
        }
        catch (NEDSSSystemException nexception)
        {
            logger.fatal("Error obtaining dbConnection " + "for removing CaseManagementDT ", nexception);
            throw new NEDSSSystemException(nexception.toString());
        }
        /**
         * Remove CaseManagementDTCaseManagementDT methods
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(CaseManagementDAOImpl.PHYSICALLY_DELETE_CASE_MANAGEMENT);
            int i = 1;
            preparedStmt.setLong(i++, phcuid.longValue());
            preparedStmt.executeUpdate();
            logger.debug("Done deleting a CaseManagementDT");
        }
        catch (SQLException sqlException)
        {
            logger.fatal("SQLException while running " + PHYSICALLY_DELETE_CASE_MANAGEMENT);
            logger.fatal("SQLException while deleting CaseManagementDT, \n", sqlException);
            throw new NEDSSDAOSysException(sqlException.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    public void store(PublicHealthCaseVO phcVO)
    {
    	try{
	        CaseManagementDT caseManagementDT = phcVO.getTheCaseManagementDT();
	        Long phcuid = phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid();
	        caseManagementDT.setPublicHealthCaseUid(phcuid);
	        boolean isExistingCaseManagement = caseManagementExists(phcuid);
	        if (isExistingCaseManagement)
	        {
	            updateCaseManagementDT(caseManagementDT);
	        }
	        else
	        {
	            insertCaseManagementDT(caseManagementDT);
	        }
    	}catch(Exception ex){
    		logger.fatal("Exception ="+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString(), ex);
    	}
    }

    private void updateCaseManagementDT(CaseManagementDT caseManagementDT) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        
        //For some reason in Alabama data is getting lost on occasion
        //If field record number is null, we need to abort and the user will loose their changes..
        if (caseManagementDT.getFieldRecordNumber() == null) {
        	logger.error("********************************FIELD RECORD NUMBER IS NULL************************************");
            logger.error("Case Management Update: ERROR Field Record Number is null ");
            if (caseManagementDT.getPublicHealthCaseUid() != null) {
            	logger.error(">>>>>>>>>>>>>> publicHealthCaseUid=" + caseManagementDT.getPublicHealthCaseUid().toString() + "<<<<<<<<<<<<<<<<");
            }
            logger.error("      CaseManagement.toString="+caseManagementDT.toString());
			//throw new NEDSSAppException("CaseManagementDAOImpl:updateCaseManagementDT- Error missing Field Record Number - UPDATE NOT SAVED");
        }
        
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(UPDATE_CASE_MANAGEMENT);
            setPreparedStatement(preparedStmt, caseManagementDT);
            resultCount = preparedStmt.executeUpdate();
            logger.debug("resultCount in updateCaseManagementDT is " + resultCount);

        }
        catch (SQLException ex)
        {
            logger.fatal("SQLException while running " + UPDATE_CASE_MANAGEMENT);
            logger.fatal("SQLException in updatecaseManagementDT: ERROR = " + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Exception while running " + UPDATE_CASE_MANAGEMENT);
            logger.fatal("Exception in updatecaseManagementDT: ERROR = " + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }// end of update method

    private PreparedStatement setPreparedStatement(PreparedStatement preparedStmt, CaseManagementDT caseManagementDT)
    {
        int k = 1;
        try
        {
            preparedStmt.setString(k++, caseManagementDT.getStatus900());// 1
            preparedStmt.setString(k++, caseManagementDT.getEharsId());// 2
            preparedStmt.setString(k++, caseManagementDT.getEpiLinkId());// 3
            preparedStmt.setString(k++, caseManagementDT.getFieldFollUpOojOutcome());// 4
            preparedStmt.setString(k++, caseManagementDT.getFieldRecordNumber());// 5
            preparedStmt.setString(k++, caseManagementDT.getFldFollUpDispo());// 6

            setTimestamp(preparedStmt, k++, caseManagementDT.getFldFollUpDispoDate());// 7
            setTimestamp(preparedStmt, k++, caseManagementDT.getFldFollUpExamDate());// 8
            setTimestamp(preparedStmt, k++, caseManagementDT.getFldFollUpExpectedDate());// 9

            preparedStmt.setString(k++, caseManagementDT.getFldFollUpExpectedIn());// 10
            preparedStmt.setString(k++, caseManagementDT.getFldFollUpInternetOutcome());// 11
            preparedStmt.setString(k++, caseManagementDT.getFldFollUpNotificationPlan());// 12
            preparedStmt.setString(k++, caseManagementDT.getFldFollUpProvDiagnosis());// 13
            preparedStmt.setString(k++, caseManagementDT.getFldFollUpProvExmReason());// 14
            preparedStmt.setString(k++, caseManagementDT.getInitFollUp());// 15
            preparedStmt.setString(k++, caseManagementDT.getInitFollUpClinicCode());// 16

            setTimestamp(preparedStmt, k++, caseManagementDT.getInitFollUpClosedDate());// 17

            preparedStmt.setString(k++, caseManagementDT.getInitFollUpNotifiable());// 18
            preparedStmt.setString(k++, caseManagementDT.getInternetFollUp());// 19
            preparedStmt.setString(k++, caseManagementDT.getOojAgency());// 20

            setTimestamp(preparedStmt, k++, caseManagementDT.getOojDueDate());// 21

            preparedStmt.setString(k++, caseManagementDT.getOojNumber());// 22
            preparedStmt.setString(k++, caseManagementDT.getPatIntvStatusCd());// 23
            preparedStmt.setString(k++, caseManagementDT.getSubjComplexion());// 24
            preparedStmt.setString(k++, caseManagementDT.getSubjHair());// 25
            preparedStmt.setString(k++, caseManagementDT.getSubjHeight());// 26

            preparedStmt.setString(k++, caseManagementDT.getSubjOthIdntfyngInfo());// 27
            preparedStmt.setString(k++, caseManagementDT.getSubjSizeBuild());// 28

            setTimestamp(preparedStmt, k++, caseManagementDT.getSurvClosedDate());// 29

            preparedStmt.setString(k++, caseManagementDT.getSurvPatientFollUp());// 30
            preparedStmt.setString(k++, caseManagementDT.getSurvProvDiagnosis());// 31
            preparedStmt.setString(k++, caseManagementDT.getSurvProvExmReason());// 32
            preparedStmt.setString(k++, caseManagementDT.getSurvProviderContact());// 33
            preparedStmt.setString(k++, caseManagementDT.getActRefTypeCd());// 34
            preparedStmt.setString(k++, caseManagementDT.getInitiatingAgncy());// 35

            setTimestamp(preparedStmt, k++, caseManagementDT.getOojInitgAgncyOutcDueDate());// 36
            setTimestamp(preparedStmt, k++, caseManagementDT.getOojInitgAgncyOutcSntDate());// 37
            setTimestamp(preparedStmt, k++, caseManagementDT.getOojInitgAgncyRecdDate());// 38

            preparedStmt.setString(k++, caseManagementDT.getCaseReviewStatus());// 39

            setTimestamp(preparedStmt, k++, caseManagementDT.getSurvAssignedDate());// 40
            setTimestamp(preparedStmt, k++, caseManagementDT.getFollUpAssignedDate());// 41
            setTimestamp(preparedStmt, k++, caseManagementDT.getInitFollUpAssignedDate());// 42
            setTimestamp(preparedStmt, k++, caseManagementDT.getInterviewAssignedDate());// 43
            setTimestamp(preparedStmt, k++, caseManagementDT.getInitInterviewAssignedDate());// 44
            setTimestamp(preparedStmt, k++, caseManagementDT.getCaseClosedDate());// 45
            setTimestamp(preparedStmt, k++, caseManagementDT.getCaseReviewStatusDate());// 46

            preparedStmt.setLong(k++, caseManagementDT.getPublicHealthCaseUid());// 47
        }
        catch (SQLException e)
        {
            logger.fatal("SQLException while Setting PreparedStatement Values for case_management: \n"+e.getMessage(), e);
            throw new NEDSSDAOSysException(e.toString());
        }
        return preparedStmt;
    }

    public CaseManagementDT selectCaseManagementDT(long phcUID) throws NEDSSSystemException
    {
        CaseManagementDT caseManagementDT = new CaseManagementDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();

        /**
         * Selects a caseManagementDT from Case_ManagementDT table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_CASE_MANAGEMENT);
            preparedStmt.setLong(1, phcUID);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            caseManagementDT = (CaseManagementDT) resultSetUtils.mapRsToBean(resultSet, resultSetMetaData,
                    caseManagementDT.getClass());
            caseManagementDT.setItNew(false);
            caseManagementDT.setItDirty(false);
            caseManagementDT.setItDelete(false);
        }
        catch (SQLException e)
        {
            logger.fatal("SQLException while selecting " + "a CaseManagementDT ; uid = " + phcUID, e);
            throw new NEDSSDAOSysException(e.getMessage());
        }
        catch (Exception exception)
        {
            logger.fatal("Exception while selecting " + "a CaseManagementDT ; uid = " + phcUID, exception);
            throw new NEDSSDAOSysException(exception.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.debug("return a CaseManagementDT object");
        return caseManagementDT;
    }// end of selecting a CaseManagementDT

    public boolean caseManagementEpilinkExists(String epilinkId) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(CaseManagementDAOImpl.SELECT_CASE_MANAGEMENT_EPILINK);
            logger.debug("caseManagementEpilinkExists - epilinkId = " + epilinkId);
            preparedStmt.setString(1, epilinkId);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                returnValue = true;
            }
        }
        catch (SQLException exception)
        {
            logger.fatal("SQLException while running " + SELECT_CASE_MANAGEMENT_EPILINK);

            logger.fatal("SQLException while checking for an" + " existing epilink in CASE_MANAGEMENT table -> "
                    + epilinkId, exception);
            throw new NEDSSDAOSysException(exception.getMessage());
        }
        catch (NEDSSSystemException nexception)
        {
            logger.fatal("Exception while running " + SELECT_CASE_MANAGEMENT_EPILINK);
            logger.fatal("Exception while getting dbConnection for checking for an" + " existing epilink -> "
                    + epilinkId, nexception);
            throw new NEDSSDAOSysException(nexception.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return returnValue;
    }

    public ArrayList<Object> getEpilinkDTCollection(String epilinkId) throws NEDSSSystemException, NEDSSAppException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> caseManagementEpilinkDTColl = new ArrayList<Object>();

        /**
         * Selects a caseManagementDT from Case_ManagementDT table
         */
        try
        {
            if (!caseManagementEpilinkExists(epilinkId))
            {
               // throw new NEDSSAppException("Current Epi Link ID");
            	return caseManagementEpilinkDTColl;
            }
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_CASE_MANAGEMENT_EPILINK);
            preparedStmt.setString(1, epilinkId);
            resultSet = preparedStmt.executeQuery();

            if (resultSet != null)
            {
                resultSetMetaData = resultSet.getMetaData();
                caseManagementEpilinkDTColl = (ArrayList<Object>) resultSetUtils.mapRsToBeanList(resultSet,
                        resultSetMetaData, CaseManagementDT.class, caseManagementEpilinkDTColl);
            }
        }
        catch (SQLException e)
        {
            logger.fatal("SQLException while selecting " + "a CaseManagementDT ; epilinkId = " + epilinkId, e);
            throw new NEDSSDAOSysException(e.getMessage());
        }
        catch (Exception exception)
        {
            logger.fatal("Exception while selecting " + "a CaseManagementDT ; epilinkId = " + epilinkId, exception);
            throw new NEDSSDAOSysException(exception.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.debug("return a CaseManagementDT collection");
        return caseManagementEpilinkDTColl;
    }// end of selecting a CaseManagementDT

	private void updateCaseManagementWithEPIIDandFRNum(
			CaseManagementDT caseManagementDT) throws NEDSSSystemException {
		// generate EPI Link Id (Lot Nbr) and field record number if not present

		try {
			if (caseManagementDT.getEpiLinkId() == null
					&& caseManagementDT.getFieldRecordNumber() == null) {
				UidGeneratorHelper uidGen = new UidGeneratorHelper();
				SimpleDateFormat sdf = new SimpleDateFormat("yy"); // Just the year, with 2 digits
				String twoDigitYear = sdf.format(Calendar.getInstance()
						.getTime());
				String epiLinkId = uidGen
						.getLocalID(UidClassCodes.EPI_LINK_ID_CLASS_CODE);
				String lotNum = PropertyUtil.getInstance().getNBS_STATE_CODE()
						+ epiLinkId.substring(2, epiLinkId.length()-2)
						+ twoDigitYear;
				caseManagementDT.setEpiLinkId(lotNum);
				caseManagementDT.setFieldRecordNumber(lotNum);
				logger.debug("New epiLinkId and field record number for PHC is: "
						+ lotNum);
			} else if (caseManagementDT.getEpiLinkId() != null
					&& caseManagementDT.getFieldRecordNumber() == null) {
				UidGeneratorHelper uidGen = new UidGeneratorHelper();
				SimpleDateFormat sdf = new SimpleDateFormat("yy"); // Just the year, with 2 digits
				String twoDigitYear = sdf.format(Calendar.getInstance()
						.getTime());
				String epiLinkId = uidGen
						.getLocalID(UidClassCodes.EPI_LINK_ID_CLASS_CODE);
				String fieldRecordNumber = PropertyUtil.getInstance()
						.getNBS_STATE_CODE()
						+ epiLinkId.substring(2, epiLinkId.length()-2)
						+ twoDigitYear;
				caseManagementDT.setFieldRecordNumber(fieldRecordNumber);
				logger.debug("New field record number for PHC is: "
						+ fieldRecordNumber);
			}

		} catch (Exception e) {
			logger.fatal("UID Generator Class Code Not setup for STD EPILinkId??"+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage());
		}
	}

}
