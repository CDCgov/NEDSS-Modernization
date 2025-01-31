
package gov.cdc.nedss.act.tb.ejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TBDAOImpl extends BMPBase {

	
	/*private static final String SELECT_CASE_NUMBER_FROM_TB_INVESTIGATIONS = "SELECT distinct ROOT_EXTENSION_TXT FROM nbs_odse..ACT_ID WHERE type_cd = 'STATE' AND act_uid IN (SELECT public_health_case_uid from public_health_case WHERE public_health_case_uid <> ? AND charindex(cd+','," 
			+"(select config_value+',' from nbs_odse..nbs_configuration where config_key = 'TB_CONDITION_CODES'))>0 OR cd ='10220') AND root_extension_txt = ?";
	
	*/
	
	
	
	private static final String SELECT_CASE_NUMBER_FROM_TB_INVESTIGATIONS = "SELECT DISTINCT ROOT_EXTENSION_TXT FROM nbs_odse..ACT_ID WITH (NOLOCK) "+
			"INNER JOIN public_health_case WITH (NOLOCK) ON ACT_ID.act_uid = public_health_case.public_health_case_uid AND ACT_ID.type_cd = 'STATE' "+
			"AND ACT_ID.root_extension_txt = ? ";
			
			
			
			
	// For logging
	static final LogUtils logger = new LogUtils(TBDAOImpl.class.getName());

	public TBDAOImpl() {
	}

	
	/**
	 * selectCaseNumber: this method executes a query to find existing state case number ids in the table with same value than the one received
	 * as a parameter
	 * @param caseNumber
	 * @return
	 * @throws NEDSSSystemException
	 */
	   @SuppressWarnings("unused")
	public String selectCaseNumber(String caseNumber, String publicHealthCaseUid, String tbConditions) throws NEDSSSystemException
	  {

	        Connection dbConnection = null;
	        PreparedStatement preparedStmt = null;
	        ResultSet resultSet = null;
	        String caseNumberFound = null;
	        try
	        {	   List<String> strList = new ArrayList<String>(Arrays.asList(tbConditions.split(",")));
	        	   String appendClause=" AND public_health_case.cd IN (%s)";
	        	   String formatedClause = String.format(appendClause, preparePlaceHolders(strList.size()));
	        	   String query =  SELECT_CASE_NUMBER_FROM_TB_INVESTIGATIONS + formatedClause;
	           	   dbConnection = getConnection();
	               if(publicHealthCaseUid==null || publicHealthCaseUid.isEmpty()){//public health case uid is not available from Add 
	            	   preparedStmt = dbConnection.prepareStatement(query);
	            	   preparedStmt.setString(1,caseNumber);
	            	   setPreparedValues(preparedStmt,strList,1);
	               }
	               else{//public health case uid is available from Edit
	            	   query +=" WHERE public_health_case_uid <> ?";
	            	   preparedStmt = dbConnection.prepareStatement(query);
	            	   preparedStmt.setString(1,caseNumber);
	            	   setPreparedValues(preparedStmt,strList,1);
		               preparedStmt.setString(strList.size()+2,publicHealthCaseUid);
	               }   
	                resultSet = preparedStmt.executeQuery();
	                if(resultSet.next())
	                {
	                	caseNumberFound = new String(resultSet.getString(1));
	                   logger.info("\n\n  - state case number found = " + caseNumber);
	                 }

	              logger.debug("Return the State case number "+caseNumberFound);
	              return caseNumberFound;
	        }
	        catch(SQLException se)
	        {
	                logger.fatal("SQLException while selecting case Number: ", se);
	                throw new NEDSSDAOSysException(se.toString());
	        }
	        catch(Exception ex)
	        {
	                logger.fatal("Exception while selection " +
	                          "Case Number = " , ex);
	                throw new NEDSSDAOSysException(ex.toString());
	        }
	        finally
	        {
	                closeResultSet(resultSet);
	                closeStatement(preparedStmt);
	                releaseConnection(dbConnection);
	        }
	}


}// end of TBDAOImpl class
