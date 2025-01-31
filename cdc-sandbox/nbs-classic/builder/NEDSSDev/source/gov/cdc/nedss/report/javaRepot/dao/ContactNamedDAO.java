package gov.cdc.nedss.report.javaRepot.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dt.ReportContactDT;
import gov.cdc.nedss.report.javaRepot.dt.TreatmentDT;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * ContactNamedDAO for custom Report Generation
 * @author Pradeep Kumar Sharma
 *
 */
public class ContactNamedDAO extends DAOBase{
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	static final LogUtils logger = new LogUtils(ContactNamedDAO.class.getName());
	static String dbTypeSeperator = " RDB..";    

	private  String  CONTACT_EPI_LINK_BASED_COLLECTION = "SELECT d_contact_record.CONTACT_REFERRAL_BASIS \"contactReferralBasis\", SUBJECT_ENTITY_EPI_LINK_ID \"subjectEpiLinkId\",  CONTACT_ENTITY_EPI_LINK_ID \"contactEpiLinkId\", d_contact_record.CONTACT_FIRST_SEX_EXP_DT \"contactFirstSexExpoDate\", CONTACT_SEX_EXP_FREQ \"contactSexExpFreq\",  CONTACT_LAST_SEX_EXP_DT \"contactLastSexExpDate\", DISPOSITION_DESCRIPTION \"dispoDescrition\", disposition_date \"dispositionDate\",  D_PATIENT.PATIENT_LAST_NAME  \"contactLastName\",  D_PATIENT.PATIENT_FIRST_NAME \"contactFirstName\",D_PATIENT.PATIENT_CURRENT_SEX \"patientCurrentSex\",  D_PATIENT.PATIENT_MIDDLE_NAME \"contactMiddleName\", F_CONTACT_RECORD_CASE.CONTACT_INVESTIGATION_KEY \"contactInvestigationKey\", F_CONTACT_RECORD_CASE.SUBJECT_INVESTIGATION_KEY \"subjectInvestigationKey\","
			+ " investigation.INV_LOCAL_ID \"contactInvestigationCase\""
			+ " from "+ dbTypeSeperator+"F_CONTACT_RECORD_CASE"
			+ " inner join"+ dbTypeSeperator+"D_PATIENT"
			+ " On F_CONTACT_RECORD_CASE.CONTACT_KEY = D_PATIENT.PATIENT_KEY "
			+ " inner join"+ dbTypeSeperator+"d_contact_record  "
			+ " ON d_contact_record.D_CONTACT_RECORD_KEY = F_CONTACT_RECORD_CASE.D_CONTACT_RECORD_KEY  "
			+ " left outer join"+ dbTypeSeperator+"investigation "
			+ " on F_CONTACT_RECORD_CASE.contact_investigation_key= investigation.investigation_key"
			+ " where CONTACT_ENTITY_EPI_LINK_ID =?";
	private  String  SUBJECT_EPI_LINK_BASED_COLLECTION = "SELECT d_contact_record.CONTACT_REFERRAL_BASIS \"contactReferralBasis\", SUBJECT_ENTITY_EPI_LINK_ID \"subjectEpiLinkId\",  CONTACT_ENTITY_EPI_LINK_ID \"contactEpiLinkId\", d_contact_record.CONTACT_FIRST_SEX_EXP_DT \"contactFirstSexExpoDate\", CONTACT_SEX_EXP_FREQ \"contactSexExpFreq\",  CONTACT_LAST_SEX_EXP_DT \"contactLastSexExpDate\", DISPOSITION_DESCRIPTION \"dispoDescrition\", disposition_date \"dispositionDate\",  D_PATIENT.PATIENT_LAST_NAME  \"contactLastName\",  D_PATIENT.PATIENT_FIRST_NAME \"contactFirstName\",D_PATIENT.PATIENT_CURRENT_SEX \"patientCurrentSex\",   D_PATIENT.PATIENT_MIDDLE_NAME \"contactMiddleName\", F_CONTACT_RECORD_CASE.CONTACT_INVESTIGATION_KEY \"contactInvestigationKey\", F_CONTACT_RECORD_CASE.SUBJECT_INVESTIGATION_KEY \"subjectInvestigationKey\","
			+ " investigation.INV_LOCAL_ID \"subjectInvestigationCase\""
			+ " from "+ dbTypeSeperator+"F_CONTACT_RECORD_CASE"
			+ " inner join"+ dbTypeSeperator+"D_PATIENT"
			+ " On F_CONTACT_RECORD_CASE.CONTACT_KEY = D_PATIENT.PATIENT_KEY "
			+ " inner join"+ dbTypeSeperator+"d_contact_record  "
			+ " ON d_contact_record.D_CONTACT_RECORD_KEY = F_CONTACT_RECORD_CASE.D_CONTACT_RECORD_KEY  "
			+ " left outer join"+ dbTypeSeperator+"investigation "
			+ " on F_CONTACT_RECORD_CASE.contact_investigation_key= investigation.investigation_key"
			+ " WHERE SUBJECT_ENTITY_EPI_LINK_ID =?";

	

	public Collection<Object> getSubjectEntityLinkCollection(String subjectEntityEpiLinkId,NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
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

		SUBJECT_EPI_LINK_BASED_COLLECTION = SUBJECT_EPI_LINK_BASED_COLLECTION+dataAccessWhereClause;
		Connection dbConnection = null;
		try
		{
			dbConnection = getConnection(NEDSSConstants.RDB);
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("SQLException while obtaining database connection " + nsex);
			throw new NEDSSSystemException(nsex.toString());
		}   
		ReportContactDT reportContactDT = new ReportContactDT();
		ArrayList<Object> subjectEpiLinkCollection = new ArrayList<Object>();
		subjectEpiLinkCollection.add(subjectEntityEpiLinkId);
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
			retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportContactDT.getClass(), retval);

		}
		catch (Exception ex)
		{
			logger.fatal("Exception in getSubjectEntityLinkCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

		return retval;
	}
	public Collection<Object> getContactEntityLinkCollection(String contactEntityEpiLinkId,NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
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
		CONTACT_EPI_LINK_BASED_COLLECTION = CONTACT_EPI_LINK_BASED_COLLECTION+dataAccessWhereClause;
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
		ReportContactDT reportContactDT = new ReportContactDT();
		ArrayList<Object> contactEpiLinkCollection = new ArrayList<Object>();
		contactEpiLinkCollection.add(contactEntityEpiLinkId);
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object> retval = new ArrayList<Object> ();
		try
		{
			preparedStmt = dbConnection.prepareStatement(CONTACT_EPI_LINK_BASED_COLLECTION);
			preparedStmt.setString(1,contactEntityEpiLinkId);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportContactDT.getClass(), retval);

		}
		catch (Exception ex)
		{
			logger.fatal("Exception in getContactEntityLinkCollection:  ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}

		return retval;
	}
	
	 private  String  MARGINAL_CONTACT_NAMED=" select "
										+" D_PATIENT.PATIENT_FIRST_NAME \"contactFirstName\","
										+" D_PATIENT.PATIENT_LAST_NAME \"contactLastName\", "
										+" D_PATIENT.PATIENT_MPR_UID \"contactMprUid\", "
										+" D_CONTACT_RECORD.CONTACT_FIRST_SEX_EXP_DT \"contactFirstSexExpoDate\", "
										+" D_CONTACT_RECORD.CONTACT_SEX_EXP_FREQ \"contactSexExpFreq\",  "
										+" D_CONTACT_RECORD.CONTACT_LAST_SEX_EXP_DT \"contactLastSexExpDate\", "
										+" D_CONTACT_RECORD.CONTACT_NDLSHARE_EXP_FREQ \"contactNdlShareExpFreq\","
										+" D_CONTACT_RECORD.CONTACT_LAST_NDLSHARE_EXP_DT \"contactFirstNdlShareExpoDate\", "
										+" D_CONTACT_RECORD.CONTACT_FIRST_NDLSHARE_EXP_DT \"contactLastNdlShareExpDate\","
										+" D_CONTACT_RECORD.CONTACT_REFERRAL_BASIS  \"contactReferralBasis\""
										+" from F_CONTACT_RECORD_CASE "
										+" inner join D_PATIENT on "
										+" F_CONTACT_RECORD_CASE.CONTACT_KEY =D_PATIENT.PATIENT_KEY "
										+" left outer join D_CONTACT_RECORD "
										+" on D_CONTACT_RECORD.D_CONTACT_RECORD_KEY=F_CONTACT_RECORD_CASE.D_CONTACT_RECORD_KEY "
										+" left outer join D_INTERVIEW "
										+" on F_CONTACT_RECORD_CASE.CONTACT_INTERVIEW_KEY =D_INTERVIEW.D_INTERVIEW_KEY "
										+" where "
										+" D_CONTACT_RECORD.PROCESSING_DECSN_DESCRIPTION= 'Insufficient Info' and SUBJECT_INVESTIGATION_KEY=? ";
	 public Collection<Object> getContactBySubjectInvestigationKey(Long investigationKey, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
	    {
		 Connection dbConnection = null;
	        try
	        {
	           dbConnection = getConnection(NEDSSConstants.RDB);
	        }
	        catch(NEDSSSystemException nsex)
	        {
	            logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(),nsex);
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
		        MARGINAL_CONTACT_NAMED = MARGINAL_CONTACT_NAMED+dataAccessWhereClause;
		 
		    ReportContactDT reportContactDT = new ReportContactDT();
	        
		    
		    PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        ArrayList<Object> retval = new ArrayList<Object> ();
      try
      {
	        preparedStmt = dbConnection.prepareStatement(MARGINAL_CONTACT_NAMED);
	        preparedStmt.setLong(1,investigationKey);
	        resultSet = preparedStmt.executeQuery();
	        resultSetMetaData = resultSet.getMetaData();
	        retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportContactDT.getClass(), retval);
      }catch (Exception ex)
      {
	            logger.fatal("Exception in getSubjectEntityLinkCollection:  ERROR = " + ex.getMessage(), ex);
	            throw new NEDSSSystemException(ex.toString());
      }
	     return retval;
	    }
}
