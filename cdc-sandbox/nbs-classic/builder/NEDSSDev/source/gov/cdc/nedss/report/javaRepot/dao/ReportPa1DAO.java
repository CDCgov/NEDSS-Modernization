package gov.cdc.nedss.report.javaRepot.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dt.ReportPatientDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

public class ReportPa1DAO extends DAOBase {
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	   static final LogUtils logger = new LogUtils(ReportPatientDAO.class.getName());
   static String dbTypeSeperator = " RDB..";    

	 
	private String  PA1_COLLECTION = "select "
	+" INTERVIEWER_QUICK_CODE \"interviewerQEC\", "
	+" INTERVIEWER_NAME  \"interviewerName\", "
	+" INVESTIGATOR_NAME \"investigatorName\", "
	+" INTERVIEWER_QUICK_CODE \"investigatorQEC\", "
	+" INVESTIGATOR_KEY \"investigatorKey\", "
	+" HIV_900_TEST_IND \"hivNineHundredTestInd\", "
	+" STD_HIV_DATAMART.INV_CASE_STATUS \"invCaseStatus\", "
	+" HIV_900_RESULT \"hivNineHundredResult\", "
	+" ADM_ReferralBasisOOJ \"referralOOJ\", "
	+" INIT_FUP_INITIAL_FOLL_UP_CD \"initialFollowUpCd\", "
	+" EPI_LINK_ID \"lotNumber\", "
	+" HIV_POST_TEST_900_COUNSELLING \"hivPostTestNineHundredCounselling\", "
	+" CA_INTERVIEWER_ASSIGN_DT \"intervewerAssignmentDate\" , "
	+" CA_INIT_INTVWR_ASSGN_DT \"initialInterviewerAssignmentDate\" , "
	+" CA_PATIENT_INTV_STATUS \"patientInterviewStatus\", "
	+" CC_CLOSED_DT \"dateClosed\", "
	+" STD_HIV_DATAMART.DIAGNOSIS \"dx\", "
	+" STD_HIV_DATAMART.SOURCE_SPREAD \"sourceSpread\", "
	+" FL_FUP_DISPOSITION_DESC \"ffupDispoDesc\", "
	+" STD_HIV_DATAMART.REFERRAL_BASIS \"referralBasis\", "
	+" CONFIRMATION_DATE \"confirmationDate\", "
	+" STD_HIV_DATAMART.INVESTIGATION_KEY \"investigationKey\", "
	+" INTERVIEWER_KEY \"interviewProviderKey\", "
	+" STD_PRTNRS_PRD_FML_TTL \"femalePartners\", "
	+" STD_PRTNRS_PRD_MALE_TTL \"malePartners\", "
	+" STD_PRTNRS_PRD_TRNSGNDR_TTL \"transgenderPartners\"," 
	+" ADI_900_STATUS_CD \"nineHundredStatus\", "
	+" D_INTERVIEW.d_interview_key \"interviewKey\", "
	+" IX_LOCATION \"interviewLocation\", "
	+" IX_TYPE \"interviewType\", "
	+" D_INTERVIEW.interview_date \"interviewDate\", "
	+" D_CONTACT_RECORD.PROCESSING_DECSN_DESCRIPTION  \"contactProcessingDecisionDesc\", "
	+" D_CONTACT_RECORD.DISPOSITION_DESCRIPTION  \"contactDispositionDesc\", "
	+" D_CONTACT_RECORD.DISPOSITION_DATE  \"contactDispositionDate\", "
	+" D_CONTACT_RECORD.SOURCE_SPREAD  \"contactSourceSpread\", "
	+" contact_referral_basis  \"contactReferralBasis\", "
	+ "ContactInvestigation.INV_START_DT \"contactInvestigationStartDate\", "
	+" F_CONTACT_RECORD_CASE.D_CONTACT_RECORD_KEY \"contactRecordKey\", "
	+" F_CONTACT_RECORD_CASE.CONTACT_INTERVIEW_KEY  \"contactInterviewKey\" "
	+" from "
	+ "STD_HIV_DATAMART left outer join "
	+ "f_interview_case "
	+" on "
	+" f_interview_case.INVESTIGATION_KEY=STD_HIV_DATAMART.INVESTIGATION_KEY "
	+" left outer join "
	+ "d_interview "
	+" on "
	+" f_interview_case.d_interview_key=d_interview.d_interview_key "
	+" left outer join "
	+" F_CONTACT_RECORD_CASE "
	+" on F_CONTACT_RECORD_CASE.SUBJECT_INVESTIGATION_KEY = STD_HIV_DATAMART.investigation_key "
	+" left outer join "
	+" d_contact_record "
	+" ON  "
	+" d_contact_record.d_contact_record_key=F_CONTACT_RECORD_CASE.d_contact_record_key "
	+" left outer join "
	+" Investigation ContactInvestigation "
	+" ON  "
	+"ContactInvestigation.INVESTIGATION_KEY=F_CONTACT_RECORD_CASE.CONTACT_INVESTIGATION_KEY";
	
	private String  PA1_COLLECTION_INTERVIEW_DATE = PA1_COLLECTION+"  where  CA_INTERVIEWER_ASSIGN_DT >=? and  CA_INTERVIEWER_ASSIGN_DT <=? and DIAGNOSIS_CD in (DXLIST) ";
	private String  PA1_COLLECTION_CLOSE_DATE = PA1_COLLECTION+" where  CC_CLOSED_DT >=? and  CC_CLOSED_DT <=? and DIAGNOSIS_CD in (DXLIST) ";
	//order by Interviewer Name for Assign Date and Investigator Name for Close Date
	private String ORDER_BY = " order by INVESTIGATOR_NAME, STD_HIV_DATAMART.INVESTIGATION_KEY  ";
	
	/**
	 * 
	 * @param fromTime
	 * @param toTime
	 * @param diagnosisList
	 * @param reportType - AssignedDate or ClosedDate
	 * @param nbsSecurityObj
	 * @return
	 */
	public Collection<Object> getCasesContactsInterviewsWithinDateRangeAndDiagList(Timestamp fromTime, Timestamp toTime, String diagnosisList, String reportType, NBSSecurityObj nbsSecurityObj) {
		 	Connection dbConnection = null;
	        try
	        {
	           dbConnection = getConnection(NEDSSConstants.RDB);
	        }
	        catch(NEDSSSystemException nsex)
	        {
	            logger.fatal("SQLException while obtaining RDB database connection " + nsex);
	            throw new NEDSSSystemException(nsex.toString());
	        }
	        String dataAccessJurisWhereClause = nbsSecurityObj.getDataAccessWhereClause(
			            NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
	    	if(dataAccessJurisWhereClause.contains("program_jurisdiction_oid"))
	    		dataAccessJurisWhereClause= dataAccessJurisWhereClause.replace("program_jurisdiction_oid", "STD_HIV_DATAMART.program_jurisdiction_oid");
	    	
			logger.debug(" dataAccessJurisWhereClause = " + dataAccessJurisWhereClause);
			if (dataAccessJurisWhereClause == null) 
			    dataAccessJurisWhereClause = "";
			else  dataAccessJurisWhereClause = " AND " + dataAccessJurisWhereClause;

			String reportPA1Query = "";
			if (reportType.contains("Close Date"))
				reportPA1Query = PA1_COLLECTION_CLOSE_DATE + dataAccessJurisWhereClause;
			else 
				reportPA1Query = PA1_COLLECTION_INTERVIEW_DATE + dataAccessJurisWhereClause;
			
	    	if(reportPA1Query.contains("DXLIST"))
	    		reportPA1Query= reportPA1Query.replace("DXLIST", diagnosisList);
	    		
		    ReportPatientDT reportPatientDT = new ReportPatientDT();
	      
		    PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        ArrayList<Object> retval = new ArrayList<Object> ();
            try	{
            	preparedStmt = dbConnection.prepareStatement(reportPA1Query);
            	int i = 1;
            	if(fromTime!=null)
            		preparedStmt.setTimestamp(i++, fromTime);
            	if(toTime!=null)
            		preparedStmt.setTimestamp(i++, toTime);
            	resultSet = preparedStmt.executeQuery();
            	resultSetMetaData = resultSet.getMetaData();
            	retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportPatientDT.getClass(), retval);
            }catch (Exception ex) {
	            logger.fatal("Exception in ReportPa1DAO.getCasesContactsInterviewsWithinDateRangeAndDiagList:  ERROR = " + ex.getMessage(), ex);
	            throw new NEDSSSystemException(ex.toString());
	        }
            finally
            {
    			closeResultSet(resultSet);
            	closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
	        
            return retval;
	}
}