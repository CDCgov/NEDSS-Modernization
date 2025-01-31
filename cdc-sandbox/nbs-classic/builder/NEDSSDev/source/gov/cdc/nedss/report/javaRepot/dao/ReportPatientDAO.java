package gov.cdc.nedss.report.javaRepot.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dt.ReportPatientDT;
import gov.cdc.nedss.report.javaRepot.dt.ReportPlaceDT;
import gov.cdc.nedss.report.javaRepot.dt.ReportSignsAndSymptomDT;
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
/**
 * ReportPatientDAO for custom Report Generation
 * @author Pradeep Kumar Sharma
 *
 */
public class ReportPatientDAO extends DAOBase{
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	   static final LogUtils logger = new LogUtils(ReportPatientDAO.class.getName());
static String dbTypeSeperator = " RDB..";    

	 
	 private String GET_SIGNS_AND_SYMPTOMS="select STD_SIGN_SX_SOURCE \"signSymptoms\" , STD_SIGN_SX_ANATOMIC_SITE \"anatomicalSite\", "
	+" STD_SIGN_OR_SX_OBSERVED \"signOrSiteObserved\", STD_SIGN__SX_DURATION_IN_DAYS \"duration\", STD_SIGN_SX_OBVTN_ONSET_DT \"obsOnsetDate\", STD_SIGN_SX_SOURCE \"source\", "
	+ " STD_HIV_DATAMART.INVESTIGATION_KEY \"investigationKey\" from "
	 				+ dbTypeSeperator+"D_INVESTIGATION_REPEAT "
	+" inner join "
	 				+ dbTypeSeperator+"F_STD_PAGE_CASE "
	+" on D_INVESTIGATION_REPEAT.D_INVESTIGATION_REPEAT_key = F_STD_PAGE_CASE.D_INVESTIGATION_REPEAT_key "
	+" inner join "
	 				+ dbTypeSeperator+"STD_HIV_DATAMART "
	+" on STD_HIV_DATAMART.INVESTIGATION_KEY = F_STD_PAGE_CASE.INVESTIGATION_KEY "
	+" where STD_SIGN_SX_SOURCE is not null and STD_HIV_DATAMART.EPI_LINK_ID=? and STD_HIV_DATAMART.investigation_key=?";
	 
	 private String  SUBJECT_EPI_LINK_BASED_COLLECTION = "select   d_interview.d_interview_key \"interviewKey\","
					+" EPI_LINK_ID \"epiLinkId\","
					+" IX_LOCATION \"interviewLocation\",IX_TYPE \"interviewType\", "
					+" interview_date \"originalInterviewDate\", "
					+" f_interview_case.provider_key \"interviewProviderKey\" , "
					+" STD_HIV_DATAMART.INVESTIGATION_KEY \"investigationKey\","
					+" STD_HIV_DATAMART.EPI_LINK_ID \"lotNumber\","
					+" STD_HIV_DATAMART.PATIENT_NAME \"patientName\","
					+" STD_HIV_DATAMART.INV_LOCAL_ID \"caseID\","
					+" STD_HIV_DATAMART.CC_CLOSED_DT \"dateClosed\","
					+" STD_HIV_DATAMART.PATIENT_MARITAL_STATUS \"maritalStatus\","
					+" STD_HIV_DATAMART.STATUS_900 \"nineHundredStatus\","
					+" STD_HIV_DATAMART.DIAGNOSIS_CD \"dx\","
					+" STD_HIV_DATAMART.PATIENT_PREGNANT_IND \"pregnantIndicator\","
					+" STD_HIV_DATAMART.FL_FUP_DISPO_DT \"dispositionDate\","
					+" STD_HIV_DATAMART.PATIENT_PREFERRED_GENDER \"transgenderIdentity\","
					+" STD_HIV_DATAMART.PATIENT_CURRENT_SEX \"patientCurrentSex\","
					+" STD_HIV_DATAMART.PATIENT_CURR_SEX_UNK_RSN \"currSexUnknReason\""

					+" from "
	 				+ dbTypeSeperator+"STD_HIV_DATAMART "
					+" left outer join "
					+ dbTypeSeperator+"f_interview_case "
					+" on "
					+" STD_HIV_DATAMART.INVESTIGATION_KEY = f_interview_case.INVESTIGATION_KEY "
					+" left outer join "
					+ dbTypeSeperator+"d_interview "
					+" on "
					+" f_interview_case.d_interview_key=d_interview.d_interview_key "
					+ " where EPI_LINK_ID =? and  (IX_TYPE is null OR IX_TYPE ='Initial/Original') ";

	 public Collection<Object> getPatientByEpiLinkId(String subjectEntityEpiLinkId,NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
	    {
		 Connection dbConnection = null;
	        try
	        {
	           dbConnection = getConnection(NEDSSConstants.RDB);
	        }
	        catch(NEDSSSystemException nsex)
	        {
	            logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(), nsex);
	            throw new NEDSSSystemException(nsex.toString());
	        }
	        
	        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
		            NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
		        logger.debug(" dataAccessWhereClause = " + dataAccessWhereClause);
		        if (dataAccessWhereClause == null) {
		          dataAccessWhereClause = "";
		        }
		        else {
		          dataAccessWhereClause = " AND " + dataAccessWhereClause;

		        }
		        SUBJECT_EPI_LINK_BASED_COLLECTION = SUBJECT_EPI_LINK_BASED_COLLECTION+dataAccessWhereClause;
		 
		    ReportPatientDT reportPatientDT = new ReportPatientDT();
	        
		    
		    PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        ArrayList<Object> retval = new ArrayList<Object> ();
            try
	        {
		        preparedStmt = dbConnection.prepareStatement(SUBJECT_EPI_LINK_BASED_COLLECTION);
		        preparedStmt.setString(1,subjectEntityEpiLinkId);
		        resultSet = preparedStmt.executeQuery();
	            resultSetMetaData = resultSet.getMetaData();
		        retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportPatientDT.getClass(), retval);
	        }catch (Exception ex)
	        {
	            logger.fatal("Exception in getSubjectEntityLinkCollection:  ERROR = " + ex.getMessage(), ex);
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

	 public Collection<Object> getSignsAndSymptomsInformation(String epiLinkId, Long investigationKey, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
	    {
		 		String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
		            NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
		        logger.debug(" dataAccessWhereClause = " + dataAccessWhereClause);
		        if (dataAccessWhereClause == null) {
		          dataAccessWhereClause = "";
		        }
		        else {
		          dataAccessWhereClause = " AND " + dataAccessWhereClause;

		        }
		        Connection dbConnection = null;
		        try
		        {
		           dbConnection = getConnection(NEDSSConstants.RDB);
		        }
		        catch(NEDSSSystemException nsex)
		        {
		            logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(), nsex);
		            throw new NEDSSSystemException(nsex.toString());
		        }
		        GET_SIGNS_AND_SYMPTOMS = GET_SIGNS_AND_SYMPTOMS+dataAccessWhereClause;
		 ReportSignsAndSymptomDT reportSignsAndSymptomDT = new ReportSignsAndSymptomDT();
	        PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        ArrayList<Object> retval = new ArrayList<Object> ();
            try
	        {
		        preparedStmt = dbConnection.prepareStatement(GET_SIGNS_AND_SYMPTOMS);
		        preparedStmt.setString(1,epiLinkId);
		        preparedStmt.setLong(2,investigationKey);
		        resultSet = preparedStmt.executeQuery();
	            resultSetMetaData = resultSet.getMetaData();
	            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportSignsAndSymptomDT.getClass(), retval);
	        }
	        catch (Exception ex)
	        {
	            logger.fatal("Exception in signsAndSymptomCollection:  ERROR = " + ex.getMessage(), ex);
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

	 
	 private  String  MARGINAL_NAMED=" select inv_local_id \"caseID\", "
							+" STD_HIV_DATAMART.diagnosis_cd \"dx\",  STD_HIV_DATAMART.PATIENT_NAME \"patientName\", STD_HIV_DATAMART.epi_link_id \"epiLinkId\","
							+" STD_HIV_DATAMART.PATIENT_CURRENT_SEX patientCurrentSex, "
							+" STD_HIV_DATAMART.RSK_TRANSGENDER_IDENTITY \"transgenderIdentity\","
							+" STD_HIV_DATAMART.PATIENT_SEX \"patientSex\" , PATIENT_CURR_SEX_UNK_RSN \"currSexUnknReason\","
							+" STD_HIV_DATAMART.INVESTIGATION_KEY \"investigationKey\""
							+" from "
							+ dbTypeSeperator+"STD_HIV_DATAMART"
							+" where  EPI_LINK_ID=? ";
	 public Collection<Object> getPatientByEpiLinkIdAndDiagnosis(String subjectEntityEpiLinkId,String diagnosisList, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
	    {
		 	Connection dbConnection = null;
	        try
	        {
	           dbConnection = getConnection(NEDSSConstants.RDB);
	        }
	        catch(NEDSSSystemException nsex)
	        {
	            logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(), nsex);
	            throw new NEDSSSystemException(nsex.toString());
	        }
	        
		 String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
		            NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
		        logger.debug(" dataAccessWhereClause = " + dataAccessWhereClause);
		        if (dataAccessWhereClause == null) {
		          dataAccessWhereClause = "";
		        }
		        else {
		          dataAccessWhereClause = " AND " + dataAccessWhereClause;

		        }
		        if(diagnosisList!=null && diagnosisList.length()>0)
		        	MARGINAL_NAMED = MARGINAL_NAMED+" and DIAGNOSIS_CD in ("+diagnosisList+")"+dataAccessWhereClause;
		        else
		        	MARGINAL_NAMED = MARGINAL_NAMED+" "+dataAccessWhereClause;
		        
		 
		        ReportPatientDT reportPatientDT = new ReportPatientDT();
	        
		    
		    PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        ArrayList<Object> retval = new ArrayList<Object> ();
         try
         {
	        preparedStmt = dbConnection.prepareStatement(MARGINAL_NAMED);
	        preparedStmt.setString(1,subjectEntityEpiLinkId);
	        resultSet = preparedStmt.executeQuery();
	        resultSetMetaData = resultSet.getMetaData();
	        retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportPatientDT.getClass(), retval);
         }catch (Exception ex)
         {
	            logger.fatal("Exception in getSubjectEntityLinkCollection:  ERROR = " + ex.getMessage(), ex);
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

	 
	 
	 private  String  ASSOCIATED_HANGOUTS_WITHIN_DATERANGE=" select distinct INV_PLACE_REPEAT_DATAMART.place_uid \"placeUid\", INV_PLACE_REPEAT_DATAMART.place_key \"placeKey\",  PLACE_NAME \"placeName\",  place_type_description \"placeTypeDescription\", place_address \"placeAddress\" "
	 		+ " from " 
				+ dbTypeSeperator+"INV_PLACE_REPEAT_DATAMART "
				+ " inner join "
				+ dbTypeSeperator+"STD_HIV_DATAMART on "
				+ " INV_PLACE_REPEAT_DATAMART.investigation_key= STD_HIV_DATAMART.investigation_key "
				+" where  CONFIRMATION_DATE >=? and  CONFIRMATION_DATE <=?  ";
	 public Collection<Object> getAssociatedHangoutsWithinDateRange(
			 Timestamp fromConfirmTime, Timestamp toConfirmTime, String hangoutValues, String diseaseValues,NBSSecurityObj nbsSecurityObj) {
		 Connection dbConnection = null;
		 try
		 {
			 dbConnection = getConnection(NEDSSConstants.RDB);
		 }
		 catch(NEDSSSystemException nsex)
		 {
			 logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(), nsex);
			 throw new NEDSSSystemException(nsex.toString());
		 }

		 String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
				 NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
		 logger.debug(" dataAccessWhereClause = " + dataAccessWhereClause);
		 if (dataAccessWhereClause == null) {
			 dataAccessWhereClause = "";
		 }
		 else {
			 dataAccessWhereClause = " AND " + dataAccessWhereClause;
 
		 }
		 if(hangoutValues!=null && hangoutValues.length()>0)
			 ASSOCIATED_HANGOUTS_WITHIN_DATERANGE = ASSOCIATED_HANGOUTS_WITHIN_DATERANGE+" and PLACE_UID in ("+hangoutValues+")";
		 if(diseaseValues!=null && diseaseValues.length()>0)
					 ASSOCIATED_HANGOUTS_WITHIN_DATERANGE = ASSOCIATED_HANGOUTS_WITHIN_DATERANGE+" and DIAGNOSIS_CD in ("+diseaseValues+")";

		 ASSOCIATED_HANGOUTS_WITHIN_DATERANGE =ASSOCIATED_HANGOUTS_WITHIN_DATERANGE+dataAccessWhereClause+" order by place_key ";

		 ReportPlaceDT reportPlaceDT = new ReportPlaceDT();


		 PreparedStatement preparedStmt = null;
		 ResultSet resultSet = null;
		 ResultSetMetaData resultSetMetaData = null;
		 ResultSetUtils resultSetUtils = new ResultSetUtils();
		 ArrayList<Object> retval = new ArrayList<Object> ();
		 try
		 {
			 preparedStmt = dbConnection.prepareStatement(ASSOCIATED_HANGOUTS_WITHIN_DATERANGE);
			 preparedStmt.setTimestamp(1,fromConfirmTime);
			 preparedStmt.setTimestamp(2,toConfirmTime);
			 resultSet = preparedStmt.executeQuery();
			 resultSetMetaData = resultSet.getMetaData();
			 retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportPlaceDT.getClass(), retval);
		 }catch (Exception ex)
		 {
			 logger.fatal("Exception in getAssociatedHangoutsWithinDateRange:  ERROR = " + ex.getMessage(), ex);
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

	
	 private  String CASES_WITH_HANGOUT ="select STD_HIV_DATAMART.FL_FUP_DISPO_DT \"dispositionDate\","
	 			+" STD_HIV_DATAMART.FL_FUP_DISPOSITION_DESC ,STD_HIV_DATAMART.FL_FUP_DISPOSITION_CD,"
				 +" STD_HIV_DATAMART.RSK_TRANSGENDER_IDENTITY \"transgenderIdentity\", "
				 +" STD_HIV_DATAMART.PATIENT_CURRENT_SEX \"patientCurrentSex\", "
				 +" STD_HIV_DATAMART.PATIENT_CURR_SEX_UNK_RSN \"currSexUnknReason\", "
				 + " PATIENT_NAME \"patientName\", DIAGNOSIS_CD \"Dx\", "
				 + " INV_LOCAL_ID \"caseID\", status_900 \"nineHundredStatus\", REFERRAL_BASIS \"referralBasis\","
				 + "STD_HIV_DATAMART.Investigation_key \"investigationKey\" " 
				 +" from "
				 + dbTypeSeperator+"INV_PLACE_REPEAT_DATAMART"
				 +"  inner join  "
				 + dbTypeSeperator+"STD_HIV_DATAMART "
				 +" on  INV_PLACE_REPEAT_DATAMART.investigation_key= STD_HIV_DATAMART.investigation_key "
 				 +"  where  CONFIRMATION_DATE >=? and  CONFIRMATION_DATE <=? and INV_PLACE_REPEAT_DATAMART.place_key=? ";
		
	public Collection<Object> getAssociatedHangoutsCasesWithinDateRange(Timestamp fromConfirmTime, Timestamp toConfirmTime, Long placeKey, NBSSecurityObj nbsSecurityObj) {
		 Connection dbConnection = null;
		 try
		 {
			 dbConnection = getConnection(NEDSSConstants.RDB);
		 }
		 catch(NEDSSSystemException nsex)
		 {
			 logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(), nsex);
			 throw new NEDSSSystemException(nsex.toString());
		 }

		 String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
				 NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
		 logger.debug(" dataAccessWhereClause = " + dataAccessWhereClause);
		 if (dataAccessWhereClause == null) {
			 dataAccessWhereClause = "";
		 }
		 else {
			 dataAccessWhereClause = " AND " + dataAccessWhereClause;

		 }
		 CASES_WITH_HANGOUT= CASES_WITH_HANGOUT+" "+dataAccessWhereClause;


		 ReportPatientDT reportPatientDT = new ReportPatientDT();


		 PreparedStatement preparedStmt = null;
		 ResultSet resultSet = null;
		 ResultSetMetaData resultSetMetaData = null;
		 ResultSetUtils resultSetUtils = new ResultSetUtils();
		 ArrayList<Object> retval = new ArrayList<Object> ();
		 try
		 {  preparedStmt = dbConnection.prepareStatement(CASES_WITH_HANGOUT);
		 	preparedStmt.setTimestamp(1,fromConfirmTime);
		 	preparedStmt.setTimestamp(2,toConfirmTime);
		 	preparedStmt.setLong(3,placeKey);
			resultSet = preparedStmt.executeQuery();
			 resultSetMetaData = resultSet.getMetaData();
			 retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportPatientDT.getClass(), retval);
		 }catch (Exception ex)
		 {
			 logger.fatal("Exception in getAssociatedHangoutsCasesWithinDateRange:  ERROR = " + ex.getMessage(), ex);
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
