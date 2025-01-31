package gov.cdc.nedss.systemservice.ejb.dbauthejb.dao;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.NBSConfigurationDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * NBSConfigurationDAO - Retrieves configuration data from database for NEDSS.property
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: CSRA for CDC</p>
 * Feb 10th, 2017
 * @version 0.9
 */


public class NBSConfigurationDAO extends DAOBase {
	static final LogUtils logger = new LogUtils(NBSConfigurationDAO.class.getName());
	private static final String UPDATE_CONFIG_VALUE= "Update "+ DataTables.NBS_CONFIGURATION + " SET config_value = ?"
            +",last_chg_time = ? , last_chg_user_id = ?, status_time = ?, version_ctrl_nbr = ? where config_key = ?";
	private static final String SELECT_CONFIG_DT = "SELECT config_key \"configKey\" , config_value \"configValue\",  short_name \"shortName\", desc_txt \"descTxt\", default_value \"defaultValue\", valid_values \"validValues\" , category \"category\",  add_release \"addRelease\", version_ctrl_nbr \"versionCtrlNbr\", add_user_id \"addUserId\",add_time \"addTime\",last_chg_user_id \"lastChgUserId\",last_chg_time \"lastChgTime\", status_cd \"statusCd\", status_time \"statusTime\", admin_comment \"adminComment\", system_usage \"systemUsage\" from "+ DataTables.NBS_CONFIGURATION;
	private String INSERT_MISSING_CONFIG_VALUE = "INSERT INTO "
			+ DataTables.NBS_CONFIGURATION
			+ "(config_key, config_value,version_ctrl_nbr,add_user_id, add_time, last_chg_user_id, last_chg_time, status_cd, status_time, admin_comment) VALUES(?,?,?,?,?,?,?,?,?,?)";

	/**
	 * This function reads all active configuration from the NBS_configuration table
	 * @return map of configuration key/val
	 */
    public Map<String, String> getConfigList()
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null; 
        ResultSet rs = null;

        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for NBSConfigurationDAO - Unable to read configuration!! ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        Map<String,String> configMap = new HashMap<String,String>();
        try
        {
           
            String query="SELECT config_key, config_value from " +DataTables.NBS_CONFIGURATION + " where status_cd = 'A'";
      
            preparedStmt = dbConnection.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            
            while(rs.next()) {
            	String configKey = rs.getString("config_key");
            	if (configKey == null) {
            		logger.error("NBS_configuration table: config_key is null??");
            		continue;
            	}
            	String configValue =  rs.getString("config_value");
            	if (configValue == null)
            		configValue = "";
                configMap.put(configKey, configValue);
            }

            
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException in selecting from NBS_configuration :"+sqlex.getMessage(), sqlex);
            throw new NEDSSDAOSysException(sqlex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Error while selecting from NBS_configuration "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
        	closeResultSet(rs);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.debug("Retrieved NBS Configuration map with "+configMap.size()+" keys/values");
        return configMap;
    }
    
	/**
	 * This function reads active and inactive configuration from the NBS_configuration table
	 * @return map of configuration key/val
	 */
    public Map<String, String> getAllConfigList()
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null; 
        ResultSet rs = null;
        PropertyUtil propUtil = PropertyUtil.getInstance();

        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for the NBSConfigurationDAO - Unable to read configuration!! ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        Map<String,String> configMap = new HashMap<String,String>();
        try
        {
           
            String query="SELECT config_key, config_value from " +DataTables.NBS_CONFIGURATION;
      
            preparedStmt = dbConnection.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            while(rs.next()) {
            	String configKey = rs.getString("config_key");
            	if (configKey == null) {
            		logger.error("NBS_configuration table: config_key is null??");
            		continue;
            	}
            	String configValue =  rs.getString("config_value");
            	if (configValue == null)
            		configValue = "";
                configMap.put(configKey, configValue);
            }

            
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException in selecting all from NBS_configuration :"+sqlex.getMessage(), sqlex);
            throw new NEDSSDAOSysException(sqlex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Error while selecting all from NBS_configuration "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
        	closeResultSet(rs);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.debug("Retrieved NBS All Configuration map with "+configMap.size()+" keys/values");
        return configMap;
    }    
    
	public String  updateConfigValue(String configKey, String configVal, Long entryId, Integer nextVersionCtrlNbr) throws NEDSSSystemException {
		logger.debug("UPDATE NBS_CONFIGURATION=" + UPDATE_CONFIG_VALUE);
		logger.debug("Key="+configKey+" Value="+configVal);
	 	Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        String errFeedbackMsg = "";
        int resultCount = 0;
        try{
			dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
            logger.fatal("SQLException while obtaining database connection for updateConfig ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }
        try {
			preparedStmt = dbConnection.prepareStatement(UPDATE_CONFIG_VALUE);
			int i = 1;
			preparedStmt.setString(i++, configVal); 									  // 1 new value
			preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new Date().getTime())); // 2 last chg time
			preparedStmt.setLong(i++, entryId.longValue()); 							  // 3 last chg user
			preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new Date().getTime())); // 4 status time
			preparedStmt.setInt(i++, nextVersionCtrlNbr.intValue()); 					  // 5 new version
			preparedStmt.setString(i++, configKey); 									  // 6 where key=
	
			resultCount = preparedStmt.executeUpdate();
            if (resultCount != 1) {
            	if (resultCount == 0) {
            		logger.error("The expected property " + configKey + " was not in the configuration table. Please check with support.");
            		errFeedbackMsg = "The expected property " + configKey + " was not in the configuration table. Please check with support.";
            	} else if (resultCount > 1) {
            		logger.error("Error: Multible entries for "+configKey+ " in the NBS_configuration table???");
            		errFeedbackMsg = "The property " + configKey + " is in the configuration table multible times. Please check with support.";
            	}       
            }
        } catch(SQLException auid)    {
                logger.fatal("SQLException while updating " +
                    "configuration value \n", auid);
                throw new NEDSSDAOSysException( auid.toString() );
        }
        finally {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
        }
        return errFeedbackMsg; //empty String if successful
        
	}// end of update method
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getConfigDTCollection(boolean onlyActive)
			throws NEDSSSystemException {

		NBSConfigurationDT configDT = new NBSConfigurationDT();
		ArrayList<Object>  configDTCollection  = new ArrayList<Object> ();
		StringBuffer sbf = new StringBuffer();
		sbf.append(SELECT_CONFIG_DT);
		if (onlyActive) {
			sbf.append(" where status_cd = 'A' ");
		}
	    sbf.append(" order by config_key ");
		try {
			configDTCollection  = (ArrayList<Object> ) preparedStmtMethod(configDT,
					configDTCollection, sbf.toString(), NEDSSConstants.SELECT);

		} catch (Exception ex) {
			logger.fatal("Exception in getConfigDTCollection:  ERROR = "
					+ ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return configDTCollection;
	}

	public String insertMissingConfigValue(String configKey, String configVal, Long entryId) {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			String retErrMsg = "";
			
			logger.debug("INSERT into NBS_CONFIGURATION=" + INSERT_MISSING_CONFIG_VALUE);
			logger.debug("Key="+configKey+" Value="+configVal);
			try{
				dbConnection = getConnection();
	        }catch(NEDSSSystemException nsex){
	            logger.fatal("SQLException while obtaining database connection for insertMissingConfigValue ", nsex);
	            throw new NEDSSSystemException(nsex.toString());
	        }
			
			int resultCount = 0;
			try {
				preparedStmt = dbConnection.prepareStatement(INSERT_MISSING_CONFIG_VALUE);
				int i = 1;
				preparedStmt.setString(i++, configKey); 										// 1 key
				preparedStmt.setString(i++, configVal); 										// 2 value
				preparedStmt.setInt(i++, new Integer(1).intValue()); 							// 3 version
				preparedStmt.setLong(i++, entryId.longValue()); 								// 4 add user
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new Date().getTime()));	// 5 add time
				preparedStmt.setLong(i++, entryId.longValue()); 								// 6 last chg user
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new Date().getTime()));   // 7 last chg time
				preparedStmt.setString(i++, "A"); 												// 8 status cd
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(new Date().getTime()));   // 9 status time
				preparedStmt.setString(i++, "Inserted from property file"); 				    // 10 admin_comment

				resultCount = preparedStmt.executeUpdate();
				if (resultCount!=1) {
					logger.error("NBSConfigurationDAO unexpected result count of "+resultCount+" for Insert. Key="+configKey+" Value="+configVal);
					retErrMsg = "NBSConfigurationDAO unexpected result count of "+resultCount+" for Insert. Key="+configKey+" Value="+configVal;
				}	
					

			} catch (SQLException sqlex) {
				logger.fatal("SQLException while inserting "
						+ "missing configuration item:" + configKey + "\n ",
						sqlex);
				throw new NEDSSDAOSysException(sqlex.toString());
			} catch (Exception ex) {
				logger.fatal("Error while inserting into NBS_configuration "
						+ configKey + "\n ", ex);
				throw new NEDSSSystemException(ex.toString());
			} finally {
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
			return retErrMsg;
	}// end of insert missing config method

	
	

} //end class
