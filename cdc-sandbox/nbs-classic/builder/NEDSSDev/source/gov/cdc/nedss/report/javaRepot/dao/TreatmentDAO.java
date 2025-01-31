package gov.cdc.nedss.report.javaRepot.dao;


import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dt.TreatmentDT;
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

/**
 * TreatmentDAO for custom Report Generation
 * @author Pradeep Kumar Sharma
 *
 */
public class TreatmentDAO  extends DAOBase {
	   private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	   static final LogUtils logger = new LogUtils(TreatmentDAO.class.getName());
    static String dbTypeSeperator = " RDB..";    
	
	 
	 private final String  TREATMENT_COLLECTION = "select D_PROVIDER.PROVIDER_FIRST_NAME \"providerFirstName\", D_PROVIDER.PROVIDER_LAST_NAME \"providerLastName\", D_PROVIDER.PROVIDER_NAME_SUFFIX \"providerSuffixName\", "
	 		+ " TREATMENT_PHYSICIAN_KEY \"treatmentProviderKey\", TREATMENT_NM \"treatmentName\", investigation_key \"treatmentInvestigationKey\", treatment_drug \"tretmentDrug\", "
			+ "	TREATMENT_DOSAGE_STRENGTH \"treatmentDosageStrength\", TREATMENT_DOSAGE_STRENGTH_UNIT \"treatmentDosageUnit\", TREATMENT_FREQUENCY \"treatmentFrequency\", TREATMENT_DURATION \"treatmentDuration\", TREATMENT_DURATION_UNIT \"treatmentDurationUnit\", CUSTOM_TREATMENT \"customTreatment\" from "
			+ dbTypeSeperator+"TREATMENT_EVENT inner join "
			+ dbTypeSeperator+"treatment "
			+ " on TREATMENT_EVENT.TREATMENT_KEY= TREATMENT.TREATMENT_KEY "
			+ " left outer join"
			+ dbTypeSeperator+"D_PROVIDER "
			+ " on D_PROVIDER.PROVIDER_KEY =  TREATMENT_PHYSICIAN_KEY  "
			+ " where INVESTIGATION_KEY =? ";
	 
	 
	 public Collection<Object> getTreatments(Long investigationKey) throws NEDSSSystemException
	    {
		 	TreatmentDT treatmentDT = new TreatmentDT();
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
	        PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        ResultSetMetaData resultSetMetaData = null;
	        ResultSetUtils resultSetUtils = new ResultSetUtils();
	        ArrayList<Object> retval = new ArrayList<Object> ();
            try
	        {
		        preparedStmt = dbConnection.prepareStatement(TREATMENT_COLLECTION);
		        preparedStmt.setLong(1,investigationKey);
		        resultSet = preparedStmt.executeQuery();
	            resultSetMetaData = resultSet.getMetaData();
	            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, treatmentDT.getClass(), retval);
	        }catch (Exception ex)
	        {
	            logger.fatal("Exception in getTreatments:  ERROR = " + ex.getMessage(), ex);
	            throw new NEDSSSystemException(ex.toString());
	        }
            finally {
            	closeResultSet(resultSet);
	            closeStatement(preparedStmt);
	            releaseConnection(dbConnection);
	        }

	        return retval;
	    }

}
