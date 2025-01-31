package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dao;
/**
 * Name:		WaRuleMetadataDaoImpl.java
 * Description:DAO class to Insert/Update/Select	and Delete rules
 * Copyright:	Copyright (c) 2010
 * Company: 	CSC
 * @author	Pradeep Sharma
 * @version	4.0
 */

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaRuleMetadataDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaRuleSummaryDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaUiMetadataSummaryDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.helper.RuleParserUtil;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class WaRuleMetadataDaoImpl extends BMPBase{
	//For logging
    static final LogUtils logger = new LogUtils(WaRuleMetadataDaoImpl.class.getName());
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
    Collection<Object> questionSummaryList = null;
	
    //1. Insert group
	public  void insertWaRuleMetadataDTColl(Collection<Object> waRuleMetadataDTColl, boolean indicator)
	throws NEDSSSystemException{
		try{
			if(waRuleMetadataDTColl!=null ){
				Iterator<Object> it = waRuleMetadataDTColl.iterator();
				while(it.hasNext()){
					WaRuleMetadataDT waRuleMetadataDT = (WaRuleMetadataDT)it.next();
					insertWaRuleMetadataDT(waRuleMetadataDT,indicator);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	//1.1 Insert DT
	public  WaRuleMetadataDT insertWaRuleMetadataDT(WaRuleMetadataDT waRuleMetadataDT,boolean indicator)
	throws NEDSSSystemException{
		/**
		 * Starts inserting a new Rule 
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		ResultSet rss = null;
		Statement statement = null;
		PreparedStatement preparedStmt2= null;
		int resultCount =0;
		Long  waRuleMetadataDTUid = null;
		
		if(indicator == false){
			Long maxRuleId = maxRuleId(DataTables.WA_RULE_METADATA);			
			waRuleMetadataDT.setWaRuleMetadataUid(maxRuleId);
			waRuleMetadataDT.setUserRuleId("Rule"+maxRuleId);
		}
		// Do this only if we are inserting the rule from the UI .. Not for creating a draft/template
		if(!indicator){
			if(waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Date Compare")){
				RuleParserUtil.dateCompareJavaScript(waRuleMetadataDT);
				
			}else if( waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Require If")) {
				if (questionSummaryList == null)
					questionSummaryList = (Collection)getUiMetatdataElementsDropDown(waRuleMetadataDT.getWaTemplateUid(), "PART");
				RuleParserUtil.requireRuleJavaScript(waRuleMetadataDT, getComponentMapForQuestions(questionSummaryList));
				
			}else if( waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Enable")
					||waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Disable")){ 
				if (questionSummaryList == null)
					questionSummaryList = (Collection)getUiMetatdataElementsDropDown(waRuleMetadataDT.getWaTemplateUid(), "PART");
				RuleParserUtil.enableDisableCompareJavaScript(waRuleMetadataDT, getComponentMapForQuestions(questionSummaryList));
			}else if( waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Hide")
					||waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Unhide")){ 
				if (questionSummaryList == null)
					questionSummaryList = (Collection)getUiMetatdataElementsDropDown(waRuleMetadataDT.getWaTemplateUid(), "PART");
				RuleParserUtil.hideUnhideCompareJavaScript(waRuleMetadataDT, getComponentMapForQuestions(questionSummaryList));

			}else{
				logger.fatal("ERROR:the rule supports only \"Date Compare\", \"Enable\" or \"Disable\" or \"Require If\" or \"Hide/Show\"");
				logger.fatal("ERROR:The page doesn't support rule of type "+waRuleMetadataDT.getRuleCd());
			}
		}
		
		String INSERT_RULE_METATADATA_SQL="INSERT INTO WA_rule_metadata(wa_template_uid,rule_cd,rule_expression,err_msg_txt,source_question_identifier," +
		"target_question_identifier ,record_status_cd,record_status_time,add_time,add_user_id,last_chg_time,last_chg_user_id,rule_desc_txt, " +
		"javascript_function, javascript_function_nm, source_values,logic, user_rule_id,target_type) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		int i = 1;			
			try{
				dbConnection = getConnection();	
				preparedStmt = dbConnection.prepareStatement(INSERT_RULE_METATADATA_SQL);
				if(waRuleMetadataDT.getWaTemplateUid()==null){
					preparedStmt.setNull(i++, Types.INTEGER);
				}
				else{
					preparedStmt.setLong(i++, waRuleMetadataDT.getWaTemplateUid().longValue()); //1
				}
				preparedStmt.setString(i++,waRuleMetadataDT.getRuleCd());//2
				preparedStmt.setString(i++,waRuleMetadataDT.getRuleExpression());//3
				preparedStmt.setString(i++,waRuleMetadataDT.getErrMsgTxt());//4
				preparedStmt.setString(i++,waRuleMetadataDT.getSourceQuestionIdentifierString());//5
				preparedStmt.setString(i++,waRuleMetadataDT.getTargetQuestionIdentifierString());//6
				preparedStmt.setString(i++,waRuleMetadataDT.getRecordStatusCd());//7
				if(waRuleMetadataDT.getRecordStatusTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else	
					preparedStmt.setTimestamp(i++, waRuleMetadataDT.getRecordStatusTime());//8
	
				if(waRuleMetadataDT.getAddTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else	
					preparedStmt.setTimestamp(i++, waRuleMetadataDT.getAddTime());//9
				if(waRuleMetadataDT.getAddUserId()==null)
					preparedStmt.setNull(i++, Types.INTEGER);
				else
					preparedStmt.setLong(i++, waRuleMetadataDT.getAddUserId().longValue());//10
				if(waRuleMetadataDT.getLastChgTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else	
					preparedStmt.setTimestamp(i++, waRuleMetadataDT.getLastChgTime());//11
				if(waRuleMetadataDT.getLastChgUserId()==null)
					preparedStmt.setNull(i++, Types.INTEGER);
				else
					preparedStmt.setLong(i++, waRuleMetadataDT.getLastChgUserId().longValue());//12
				preparedStmt.setString(i++,waRuleMetadataDT.getRuleDescTxt());//13
	
				preparedStmt.setAsciiStream(i++,new ByteArrayInputStream(waRuleMetadataDT.getJavascriptFunction().getBytes()),
						waRuleMetadataDT.getJavascriptFunction().length());//14
				preparedStmt.setString(i++, waRuleMetadataDT.getJavascriptFunctionNm());//15
				preparedStmt.setString(i++, waRuleMetadataDT.getSourceValues());//16 sourceValues
				preparedStmt.setString(i++, waRuleMetadataDT.getLogicValues());//17 logic 
				preparedStmt.setString(i++, waRuleMetadataDT.getUserRuleId());//18 User_rule_id
				preparedStmt.setString(i++, waRuleMetadataDT.getTargetType());//19 Target Type
				
				resultCount = preparedStmt.executeUpdate();
				logger.debug("done insert a new Rule_meta_data! "+waRuleMetadataDT.toString());
				
				preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(WA_rule_metadata_uid) from WA_rule_metadata");
				
				rs = preparedStmt2.executeQuery();
				if (rs.next()) {
					logger.debug("WA_rule_metadata_uid = " + rs.getLong(1));
					waRuleMetadataDTUid=new Long( rs.getLong(1));
					waRuleMetadataDT.setWaRuleMetadataUid(waRuleMetadataDTUid);
				}
			}catch(SQLException sqlex)
			{
				logger.fatal("SQLException while inserting " +
						"a new entry in Rule_meta_data table: \n"+sqlex.getMessage(), sqlex);
				logger.fatal("waRuleMetadataDT DT is "+waRuleMetadataDT.toString());
				logger.fatal("waRuleMetadataDT resultCount is "+resultCount);
				throw new NEDSSDAOSysException( sqlex.toString() );
			}
			catch(Exception ex)
			{
				logger.fatal("Error while inserting into Rule_meta_data TABLE, exception = " + ex.getMessage(), ex);
				logger.fatal("waRuleMetadataDT DT is "+waRuleMetadataDT.toString());
				logger.fatal("waRuleMetadataDT resultCount is "+resultCount);
				throw new NEDSSSystemException(ex.toString());
			}

			finally
			{
				closeResultSet(rs);			
				closeStatement(preparedStmt);
				closeStatement(preparedStmt2);
				releaseConnection(dbConnection);
			}
		return waRuleMetadataDT;
	}

	//2. Update Group
	//1.1 Insert DT
	public  WaRuleMetadataDT updateWaRuleMetadataDT(WaRuleMetadataDT waRuleMetadataDT)
	throws NEDSSSystemException{
		/**
		 * Starts inserting a new Rule 
		 */
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet rss=null;
		int resultCount =0;
		if(waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Date Compare")){
			
			//For resolving issue on dates require element, the date labels were showing Null
			
			if (questionSummaryList == null)
				questionSummaryList = (Collection)getUiMetatdataElementsDropDown(waRuleMetadataDT.getWaTemplateUid(), "PART");
			
			Map<String, String> mapLabel = getComponentMapForQuestionsLabel(questionSummaryList);
			if(waRuleMetadataDT.getLableMap()==null || waRuleMetadataDT.getLableMap().size()==0 )
				waRuleMetadataDT.setLableMap(mapLabel);

			RuleParserUtil.dateCompareJavaScript(waRuleMetadataDT);
		}else if( waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Enable")
				||waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Disable")){ 
			if (questionSummaryList == null)
				questionSummaryList = (Collection)getUiMetatdataElementsDropDown(waRuleMetadataDT.getWaTemplateUid(), "PART");
			RuleParserUtil.enableDisableCompareJavaScript(waRuleMetadataDT, getComponentMapForQuestions(questionSummaryList));
		}else if( waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Require If")){ 
			if (questionSummaryList == null)
				questionSummaryList = (Collection)getUiMetatdataElementsDropDown(waRuleMetadataDT.getWaTemplateUid(), "PART");
			RuleParserUtil.requireRuleJavaScript(waRuleMetadataDT, getComponentMapForQuestions(questionSummaryList));
		}else if( waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Hide")
				|| waRuleMetadataDT.getRuleCd().equalsIgnoreCase("Unhide")){ 
			if (questionSummaryList == null)
				questionSummaryList = (Collection)getUiMetatdataElementsDropDown(waRuleMetadataDT.getWaTemplateUid(), "PART");
			RuleParserUtil.hideUnhideCompareJavaScript(waRuleMetadataDT, getComponentMapForQuestions(questionSummaryList));

		}else{
			logger.fatal("ERROR Updating Page Rule:the rule supports only \"Date Compare\", \"Enable\" or \"Disable\" or \"Require If\" or \"Hide/Show\"");
			logger.fatal("ERROR Updating Page Rule:The page doesn't support rule of type "+waRuleMetadataDT.getRuleCd());
		}
		
			String UPDATE_RULE_METATADATA_SQL="UPDATE WA_rule_metadata set wa_template_uid=?, rule_cd=?, rule_expression=?, err_msg_txt=?, source_question_identifier=?, " +
			"target_question_identifier=?, record_status_cd=?, record_status_time=?, add_time=?, add_user_id=?, last_chg_time=?, last_chg_user_id=?, " +
			"rule_desc_txt=?, javascript_function=?, javascript_function_nm=?, source_values=?, logic=?, target_type=?  WHERE WA_rule_metadata_uid = ? ";

				try
				{
					dbConnection = getConnection();
					preparedStmt = dbConnection.prepareStatement(UPDATE_RULE_METATADATA_SQL);
					int i = 1;
					if(waRuleMetadataDT.getWaTemplateUid()==null)//1
						preparedStmt.setNull(i++, Types.INTEGER);
					else
						preparedStmt.setLong(i++, waRuleMetadataDT.getWaTemplateUid().longValue());
					preparedStmt.setString(i++,waRuleMetadataDT.getRuleCd());//2
					preparedStmt.setString(i++,waRuleMetadataDT.getRuleExpression());//3
					preparedStmt.setString(i++,waRuleMetadataDT.getErrMsgTxt());//4
					preparedStmt.setString(i++,waRuleMetadataDT.getSourceQuestionIdentifierString());//5
					preparedStmt.setString(i++,waRuleMetadataDT.getTargetQuestionIdentifierString());//6
					preparedStmt.setString(i++,waRuleMetadataDT.getRecordStatusCd());//7
					if(waRuleMetadataDT.getRecordStatusTime() == null)//8
						preparedStmt.setNull(i++, Types.TIMESTAMP);
					else	
						preparedStmt.setTimestamp(i++, waRuleMetadataDT.getRecordStatusTime());
	
					if(waRuleMetadataDT.getAddTime() == null)//9
						preparedStmt.setNull(i++, Types.TIMESTAMP);
					else	
						preparedStmt.setTimestamp(i++, waRuleMetadataDT.getAddTime());
					if(waRuleMetadataDT.getAddUserId()==null)//10
						preparedStmt.setNull(i++, Types.INTEGER);
					else
						preparedStmt.setLong(i++, waRuleMetadataDT.getAddUserId().longValue());
					if(waRuleMetadataDT.getLastChgTime() == null)//11
						preparedStmt.setNull(i++, Types.TIMESTAMP);
					else	
						preparedStmt.setTimestamp(i++, waRuleMetadataDT.getLastChgTime());
					if(waRuleMetadataDT.getLastChgUserId()==null)//12
						preparedStmt.setNull(i++, Types.INTEGER);
					else
						preparedStmt.setLong(i++, waRuleMetadataDT.getLastChgUserId().longValue());
					preparedStmt.setString(i++,waRuleMetadataDT.getRuleDescTxt());//13
					preparedStmt.setAsciiStream(i++,new ByteArrayInputStream(waRuleMetadataDT.getJavascriptFunction().getBytes()),
												waRuleMetadataDT.getJavascriptFunction().length());//14
					preparedStmt.setString(i++,waRuleMetadataDT.getJavascriptFunctionNm());//15		
					preparedStmt.setString(i++, waRuleMetadataDT.getSourceValues());//16 sourceValues
					preparedStmt.setString(i++, waRuleMetadataDT.getLogicValues());//17 logic
					preparedStmt.setString(i++, waRuleMetadataDT.getTargetType()); //18 target Type
					if(waRuleMetadataDT.getWaRuleMetadataUid()==null)//19
						preparedStmt.setNull(i++, Types.INTEGER);
					else
						preparedStmt.setLong(i++, waRuleMetadataDT.getWaRuleMetadataUid().longValue());
					
					
					resultCount = preparedStmt.executeUpdate();
					logger.debug("done insert a new Rule_meta_data! "+waRuleMetadataDT.toString());
				}catch(SQLException sqlex)
				{
					logger.fatal("SQLException while inserting " +
							"a new entry in Rule_meta_data table: \n"+sqlex.getMessage(), sqlex);
					logger.fatal("waRuleMetadataDT DT is "+waRuleMetadataDT.toString());
					logger.fatal("waRuleMetadataDT resultCount is "+resultCount);
					throw new NEDSSDAOSysException( sqlex.toString() );
				}
				catch(Exception ex)
				{
					logger.fatal("Error while inserting into Rule_meta_data TABLE, id = " + ex.getMessage(), ex);
					logger.fatal("waRuleMetadataDT DT is "+waRuleMetadataDT.toString());
					logger.fatal("waRuleMetadataDT resultCount is "+resultCount);
					throw new NEDSSSystemException(ex.toString());
				}

				finally
				{
					
					closeStatement(preparedStmt);
					releaseConnection(dbConnection);
				}
		return waRuleMetadataDT;
	}
	public Collection<Object>  selectRuleMetatdataForTemplate(Long templateUid) throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery ="select  rule_cd \"ruleCd\", wa_rule_metadata_uid \"waRuleMetadataUid\", "+
						 "source_question_identifier \"sourceField\",target_question_identifier \"targetField\", target_type \"targetType\" ," +
						 "logic \"logicValues\", source_values \"sourceValues\", user_rule_id \"userRuleId\" ," +
						 " rule_desc_txt \"ruleDescTxt\",template_type \"templateType\" from wa_rule_metadata wa_rule, wa_template wa_temp "+
						 " where wa_rule.wa_template_uid = ? and wa_rule.wa_template_uid = wa_temp.wa_template_uid ";
		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for WaRuleMetadataDaoImpl.selectRuleMetatdataForTemplate: ", nedssEx);
			throw new NEDSSSystemException(nedssEx.getMessage());
		}
		try {
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			preparedStmt.setLong(1, templateUid);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> quesSummaryList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				quesSummaryList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, WaRuleSummaryDT.class,
						quesSummaryList);
				logger.debug("returned questions list");
			}
			return quesSummaryList;
		} catch (SQLException sqlEx) {
			throw new NEDSSSystemException("SQLException while selecting "
					+ "WaRuleMetadataDaoImpl.selectRuleMetatdataForTemplate "
					+ sqlEx.getMessage(), sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("Error in result set handling while selecting summary collection: WaRuleMetadataDaoImpl.selectRuleMetatdataForTemplate.",resultSetUtilEx);
			throw new NEDSSSystemException(resultSetUtilEx.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}		 
	public WaRuleMetadataDT selectWaRuleMetadataDT(long WaRuleMetadataUid) throws NEDSSSystemException
    {
		WaRuleMetadataDT waRuleMetadataDT = new WaRuleMetadataDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        String SELET_RULE_METADATA_UID="SELECT wa_template_uid \"waTemplateUid\",  rule_cd \"ruleCd\", rule_expression \"ruleExpression\", err_msg_txt \"errMsgTxt\", target_type \"targetType\" , source_question_identifier \"sourceQuestionIdentifierString\", target_question_identifier \"targetQuestionIdentifierString\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\","
        	+" add_time \"addTime\", add_user_id \"addUserId\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", rule_desc_txt \"ruleDescTxt\", javascript_function \"javascriptFunction\", javascript_function_nm \"javascriptFunctionNm\","
        	+" wa_rule_metadata_uid \"waRuleMetadataUid\", user_rule_id \"userRuleId\", logic \"logicValues\", source_values\"sourceValues\"  FROM WA_rule_metadata where wa_rule_metadata_uid=?";

        /**
         * Selects a PublicHealthCase from PublicHealthCase table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELET_RULE_METADATA_UID);
            preparedStmt.setLong(1, WaRuleMetadataUid);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            waRuleMetadataDT = (WaRuleMetadataDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, waRuleMetadataDT.getClass());
            waRuleMetadataDT.setItNew(false);
            waRuleMetadataDT.setItDirty(false);
            waRuleMetadataDT.setItDelete(false);
            RuleParserUtil.metaDataCreater(waRuleMetadataDT, waRuleMetadataDT.getRuleExpression());
        }
        catch(SQLException sqlException)
        {
            logger.fatal("SQLException while selecting " +
                            "a WaRuleMetadataDT ; uid = " + WaRuleMetadataUid, sqlException);
            throw new NEDSSDAOSysException( sqlException.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "a WaRuleMetadataDT ; uid = " + WaRuleMetadataUid, ex);
            throw new NEDSSDAOSysException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.debug("return a waRuleMetadataDT object");
        return waRuleMetadataDT;
    }//end of selecting a PublicHealthCase


	public Collection<Object>  selectRuleSummaryMetatdata(Long templateUid) throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = "SELECT wa_template_uid \"waTemplateUid\",  rule_cd \"ruleCd\", rule_expression \"ruleExpression\", err_msg_txt \"errMsgTxt\", source_question_identifier \"sourceQuestionIdentifierString\", target_question_identifier \"targetQuestionIdentifierString\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\","
    	+" add_time \"addTime\", add_user_id \"addUserId\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", rule_desc_txt \"ruleDescTxt\", javascript_function \"javascriptFunction\", javascript_function_nm \"javascriptFunctionNm\", "
    	+" wa_rule_metadata_uid \"waRuleMetadataUid\" , user_rule_id \"userRuleId\", logic \"logicValues\", source_values\"sourceValues\" FROM WA_rule_metadata where wa_template_uid=?";
		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for WaRuleMetadataDaoImpl.selectRuleMetatdataForTemplate: ", nedssEx);
			throw new NEDSSSystemException(nedssEx.getMessage());
		}
		try {
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			preparedStmt.setLong(1, templateUid);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> ruleSummayList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				ruleSummayList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, WaRuleSummaryDT.class,
						ruleSummayList);

				logger.debug("returned questions list");
			}
			return ruleSummayList;
		} catch (SQLException sqlEx) {
			throw new NEDSSSystemException("SQLException while selecting "
					+ "WaRuleMetadataDaoImpl.selectRuleMetatdataForTemplate "
					+ sqlEx.getMessage(), sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("Error in result set handling while selecting summary collection: WaRuleMetadataDaoImpl.selectRuleMetatdataForTemplate.",resultSetUtilEx);
			throw new NEDSSSystemException(resultSetUtilEx.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}		 

	public void deleteWaRuleMetadataForTemplate(Long  waTemplateUid)	throws NEDSSDAOSysException, NEDSSSystemException {

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;
		String DELETE_WA_RULE_METADATA="DELETE FROM WA_RULE_METADATA where wa_template_uid="+waTemplateUid;
		try {
			logger.debug("$$$$###Delete DELETE_WA_RULE_METADATA being called :"+ DELETE_WA_RULE_METADATA);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_WA_RULE_METADATA);
			preparedStmt.executeUpdate();
		} catch (SQLException se) {
		    logger.fatal("SQLException while removeNbsCaseEntity " +waTemplateUid.toString(), se);
			throw new NEDSSDAOSysException("Error: SQLException while deleting\n" + se.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	public Long deleteWaRuleMetadataDT(Long  waRuleMetadataDTUid)	throws NEDSSDAOSysException, NEDSSSystemException {

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;
		String DELETE_WA_RULE_METADATA="DELETE FROM WA_RULE_METADATA where wa_rule_metadata_uid=?";
		try {
			logger.debug("$$$$###Delete DELETE_WA_RULE_METADATA being called :"+ DELETE_WA_RULE_METADATA);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_WA_RULE_METADATA);
			preparedStmt.setLong(1,waRuleMetadataDTUid.longValue());
			preparedStmt.executeUpdate();
		} catch (SQLException se) {
		    logger.fatal("SQLException while removeNbsCaseEntity " +waRuleMetadataDTUid.toString(), se);
			throw new NEDSSDAOSysException("Error: SQLException while deleting\n" + se.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return waRuleMetadataDTUid;
	}

	//5. Publish 
	public void publishWaRuleMetadata(Collection<WaRuleMetadataDT> waRuleMetadataDTColl){
		//publish code
	}

	//6. Create Draft
	public void createDraftWaRuleMetadata(Collection<Object> waRuleMetadataDTColl){
		try{
			if(waRuleMetadataDTColl!= null){
				Iterator<Object> iter = waRuleMetadataDTColl.iterator();
				while(iter.hasNext()){
					WaRuleMetadataDT waRuleMetaDT = (WaRuleMetadataDT)iter.next();
					insertWaRuleMetadataDT(waRuleMetaDT, true);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	public Collection<Object>  getUiMetatdataElementsDropDown(Long templateUid, String type) throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String whereclause="";
		String dbServer="";
		dbServer=", nbs_srte..codeset cs ";
		
		 
		if(type.equalsIgnoreCase("DATE")){
			whereclause= " and data_type in ('DATE', 'DATETIME') and standard_nnd_ind_cd='F' and display_ind='T' and component_behavior like '%_data'";
		}
		else if(type.equalsIgnoreCase("CODED")){
			//whereclause= " and upper(cs.class_cd) = upper('code_value_general') and data_type in ('CODED') and standard_nnd_ind_cd='F' and display_ind='T' and nbs_UI_component_uid in (1001,1006,1007,1008,1009,1013,1017)";
			whereclause= " and data_type in ('CODED') and standard_nnd_ind_cd='F' and component_behavior like '%_data'";
		}
		else{
			//whereclause= " and standard_nnd_ind_cd='F' and display_ind='T' and nbs_UI_component_uid in (1001,1006,1007,1008,1009,1013,1017)";
			whereclause= " and standard_nnd_ind_cd!='T' and (component_behavior like '%_data' or component_behavior ='Static')";
		}  

		String sqlQuery="";

		if(type.equalsIgnoreCase("CODED"))
		{
			sqlQuery ="select distinct wa_ui_metadata.code_set_group_id \"codeSetGroupId\", order_nbr \"orderNbr\", local_id \"localId\", question_identifier \"questionIdentifier\", wa_ui_metadata.nbs_UI_component_uid  \"nbsUIComponentUid\",  nbs_ui_component.type_cd_desc \"componentName\" , question_label \"questionLabel\", " +
			" question_group_seq_nbr \"questionGroupSeqNbr\" from wa_ui_metadata, nbs_ui_component " + dbServer + " where wa_template_uid= ? and (question_identifier = 'INV169' or (upper(cs.class_cd) = upper('code_value_general') and wa_ui_metadata.code_set_group_id = cs.code_set_group_id)) and wa_ui_metadata.nbs_ui_component_uid = nbs_ui_component.nbs_ui_component_uid " +whereclause + " order by  question_label";
		}
		else{
			sqlQuery ="select wa_ui_metadata.code_set_group_id \"codeSetGroupId\", order_nbr \"orderNbr\", local_id \"localId\", question_identifier \"questionIdentifier\", wa_ui_metadata.nbs_UI_component_uid \"nbsUIComponentUid\", nbs_ui_component.type_cd_desc \"componentName\" , question_label \"questionLabel\", " +
				" question_group_seq_nbr \"questionGroupSeqNbr\" from wa_ui_metadata, nbs_ui_component  where wa_template_uid= ? and wa_ui_metadata.nbs_ui_component_uid = nbs_ui_component.nbs_ui_component_uid " +whereclause + " order by  question_label";
		}			

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for Question UiMetadata: WaRuleMetadataDaoImpl", nedssEx);
			throw new NEDSSSystemException(nedssEx.getMessage());
		}
		try {
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			preparedStmt.setLong(1, templateUid);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> ruleSummaryList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				ruleSummaryList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, WaUiMetadataSummaryDT.class,
						ruleSummaryList);

				logger.debug("returned questions list");
			}
			ArrayList<Object> returnSummaryList = new ArrayList<Object> ();
			WaUiMetadataSummaryDT sumDt = new WaUiMetadataSummaryDT();
			sumDt.setWaUiMetadataUid(-1L);
			sumDt.setQuestionIdentifier("");
			sumDt.setQuestionLabelIdentifier("");
			returnSummaryList.add(sumDt);
			returnSummaryList.addAll(ruleSummaryList);
			return returnSummaryList;
		} catch (SQLException sqlEx) {
			throw new NEDSSSystemException("SQLException while selecting "
					+ "summary collection: WaRuleMetadataDaoImpl "
					+ sqlEx.getMessage(), sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("Error in result set handling while selecting summary collection: WaRuleMetadataDaoImpl.",resultSetUtilEx);
			throw new NEDSSSystemException(resultSetUtilEx.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}	
	
	
	public boolean findSourceIsRepeatingBlock (Long templateUid, String source) throws NEDSSSystemException
	{
		
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		boolean flag= false;

		try
		{
			String state = PropertyUtil.getInstance().getNBS_STATE_CODE();
			String sql="select question_group_seq_nbr from wa_ui_metadata  where wa_template_uid=? and question_identifier =?";
			
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(sql);
			preparedStmt.setLong(1, templateUid);
			preparedStmt.setString(2, source);
			
			resultSet = preparedStmt.executeQuery();
			if (resultSet.next())
			{
				String seqNum= resultSet.getString(1);
				if(seqNum != null){
					flag = true;
				}
				else{
					flag = false;
				}
			}
		}
		catch(SQLException sqlExcepton)
		{	
			logger.fatal("SQLException  = "+sqlExcepton.getMessage(), sqlExcepton);
			throw new NEDSSDAOSysException( sqlExcepton.getMessage());
		}
		catch(NEDSSSystemException nedssSysException)
		{	
			logger.fatal("NEDSSSystemException  = "+nedssSysException.getMessage(), nedssSysException);
			throw new NEDSSDAOSysException( nedssSysException.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return flag;
	}
	
	
	public Collection<Object>  getUiMetaQuestionDropDown(Long templateUid, String source, String mode, String ruleId, String ruleCode) throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;
		String previousSeletedTarget =null;
		String additionalPredicate = "";
		
		if (ruleCode != null && ruleCode.equalsIgnoreCase("Require If"))
			additionalPredicate = " and required_ind = 'F' and component_behavior like '%_data' ";
		
		try{
			previousSeletedTarget = getPreviousSelectedTargetString(templateUid, mode, ruleId, ruleCode);
		}catch(Exception ex){
			logger.error("Exception  = "+ex.getMessage(), ex);
		}
		if(previousSeletedTarget != null && previousSeletedTarget.length() >0 ){
			previousSeletedTarget = " and question_identifier not in (" + previousSeletedTarget + ") order by  question_label";
		}else{
			previousSeletedTarget = " order by  question_label";
		}
		boolean flag = findSourceIsRepeatingBlock(templateUid, source);
		if(flag){
		   sqlQuery="select order_nbr \"orderNbr\", local_id \"localId\", question_identifier \"questionIdentifier\"," +
			        " nbs_UI_component_uid  \"nbsUIComponentUid\", question_label \"questionLabel\"  from wa_ui_metadata  " +
					" where question_group_seq_nbr in (select question_group_seq_nbr from wa_ui_metadata " + 
					" where question_identifier ='"+ source + "' and wa_template_uid="+ templateUid +") and "+ 
					" wa_template_uid="+templateUid +" and question_identifier not in ('"+ source +"') "+
					" and nbs_ui_component_uid not in (1016)" +
					" order by  question_label";
		}else{
			sqlQuery="select order_nbr \"orderNbr\", local_id \"localId\", question_identifier \"questionIdentifier\"," +
			        " wa_ui_metadata.nbs_UI_component_uid  \"nbsUIComponentUid\", nbs_ui_component.type_cd_desc \"componentName\" , question_label \"questionLabel\"  from wa_ui_metadata , nbs_ui_component " +					 
					" where wa_ui_metadata.nbs_ui_component_uid = nbs_ui_component.nbs_ui_component_uid and wa_template_uid="+templateUid +" and question_identifier not in ('"+ source +"') "+
					//" and nbs_ui_component_uid not in (1016)" +
					" and standard_nnd_ind_cd!='T' " + 
					" and (component_behavior like '%_data' or component_behavior='Static') " +
					" and question_group_seq_nbr is null "+
					additionalPredicate + previousSeletedTarget;
		}

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(sqlQuery);			
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> ruleSummaryList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				ruleSummaryList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, WaUiMetadataSummaryDT.class,
						ruleSummaryList);

				logger.debug("returned questions list");
			}
			ArrayList<Object> returnSummaryList = new ArrayList<Object> ();
			WaUiMetadataSummaryDT sumDt = new WaUiMetadataSummaryDT();
			sumDt.setWaUiMetadataUid(-1L);
			sumDt.setQuestionIdentifier("");
			sumDt.setQuestionLabelIdentifier("");
			returnSummaryList.add(sumDt);
			returnSummaryList.addAll(ruleSummaryList);
			return returnSummaryList;
		} catch (SQLException sqlEx) {
			throw new NEDSSSystemException("SQLException while selecting "
					+ "summary collection: WaRuleMetadataDaoImpl "
					+ sqlEx.getMessage(), sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("Error in result set handling while selecting summary collection: WaRuleMetadataDaoImpl.",resultSetUtilEx);
			throw new NEDSSSystemException(resultSetUtilEx.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}	
	
	
	
	public Collection<Object>  getUiMetaSubsectionDropDown(Long templateUid, String source, String mode, String ruleId) throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		
		String previousSeletedTarget =null;
		
		try{
			previousSeletedTarget = getPreviousSelectedTargetString(templateUid, mode, ruleId, null);
		}catch(Exception ex){
			logger.error("Exception  = "+ex.getMessage(), ex);
		}
		if(previousSeletedTarget != null && previousSeletedTarget.length() >0 ){
			previousSeletedTarget = "and question_identifier not in (" + previousSeletedTarget + ") order by  question_label";
		}else{
			previousSeletedTarget = " order by  question_label";
		}
		
		String sqlQuery = new String();
		if(source != null && source.length()>0){
			sqlQuery=" select order_nbr \"orderNbr\", local_id \"localId\", question_identifier \"questionIdentifier\", nbs_UI_component_uid  \"nbsUIComponentUid\", question_label \"questionLabel\" "+
						" from wa_ui_metadata  where nbs_ui_component_uid = 1016 and wa_template_uid="+ templateUid +
						" and order_nbr not in( " +
						" select max(order_nbr) from wa_ui_metadata where order_nbr < ( "+
						" select order_nbr from wa_ui_metadata where question_identifier ='"+source+"' " +
						" and wa_template_uid="+ templateUid  +")" +
						" and wa_template_uid="+ templateUid  +"and nbs_UI_component_uid =1016 )"+
						  previousSeletedTarget;
		}else{
			sqlQuery=" select order_nbr \"orderNbr\", local_id \"localId\", question_identifier \"questionIdentifier\", nbs_UI_component_uid  \"nbsUIComponentUid\", question_label \"questionLabel\" "+
			" from wa_ui_metadata  where nbs_ui_component_uid = 1016 and wa_template_uid="+ templateUid +
			previousSeletedTarget;
		}
		
		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nedssEx) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for Question UiMetadata: WaRuleMetadataDaoImpl", nedssEx);
			throw new NEDSSSystemException(nedssEx.getMessage());
		}
		try {
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> ruleSummaryList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				ruleSummaryList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, WaUiMetadataSummaryDT.class,
						ruleSummaryList);

				logger.debug("returned questions list");
			}
			ArrayList<Object> returnSummaryList = new ArrayList<Object> ();
			WaUiMetadataSummaryDT sumDt = new WaUiMetadataSummaryDT();
			sumDt.setWaUiMetadataUid(-1L);
			sumDt.setQuestionIdentifier("");
			sumDt.setQuestionLabelIdentifier("");
			returnSummaryList.add(sumDt);
			returnSummaryList.addAll(ruleSummaryList);
			return returnSummaryList;
		} catch (SQLException sqlEx) {
			throw new NEDSSSystemException("SQLException while selecting "
					+ "summary collection: WaRuleMetadataDaoImpl "
					+ sqlEx.getMessage(), sqlEx);
		} catch (ResultSetUtilsException resultSetUtilEx) {
			logger.fatal("Error in result set handling while selecting summary collection: WaRuleMetadataDaoImpl.",resultSetUtilEx);
			throw new NEDSSSystemException(resultSetUtilEx.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	
	public Long  maxRuleId ( String tableName) throws NEDSSSystemException
	{
		String LocalId= "";
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		Long returnValue = null;

		try
		{
			String state = PropertyUtil.getInstance().getNBS_STATE_CODE();
			String sql="";
			sql="select top 1 "+tableName+"_uid from "+tableName+" order by "+tableName+"_uid desc";
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(sql);
			logger.debug("LocalId" + LocalId);
			resultSet = preparedStmt.executeQuery();

			if (!resultSet.next())
			{
				returnValue = new Long(1);
			}
			else
			{
				Long maxUid= resultSet.getLong(1);
				returnValue=new Long(maxUid.longValue()+1);
				logger.debug("The  max Int Value in Table "+tableName+" is "+(maxUid.toString()));
				logger.debug("The  new Int Value in Table "+tableName+" will be is "+(returnValue.toString()));
						}
		}
		catch(SQLException sqlExcepton)
		{
			logger.fatal("localUidGenerator: SQLException while generating  max Int Value in Table "+tableName+"-> "
					+ returnValue, sqlExcepton);
			throw new NEDSSDAOSysException( sqlExcepton.getMessage());
		}
		catch(NEDSSSystemException nedssSysException)
		{
			logger.fatal("localUidGenerator: SQLException while generating  max Int Value in Table "+tableName+"-> "
					+ returnValue, nedssSysException);
			throw new NEDSSDAOSysException( nedssSysException.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return returnValue;
	}
	
	/**
	 * selectPageUidsFromWaRuleMetada(): this method returns all the different page_uid on the table WA_rule_metadata. it has been created for updating all the rules from all the pages.
	 * @return
	 */
	
	public ArrayList<Long> selectPageUidsFromWaRuleMetada(){
		
		Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ArrayList<Long> pageUidList = new ArrayList<Long>();
        String SELET_RULE_METADATA_UID="SELECT distinct wa_template_uid from WA_rule_metadata";

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELET_RULE_METADATA_UID);
            resultSet = preparedStmt.executeQuery();		
			
            while(resultSet.next()){
            	pageUidList.add(resultSet.getLong(1));
            }
			logger.debug("returned page uid list");
        }
      
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting all the page uid from WA_tule_metadata", ex);
            throw new NEDSSDAOSysException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.debug("return a waRuleMetadataDT object");
        return pageUidList;
	}
	
	public Collection<Object> selectWaRuleMetadataDTByTemplate(long waTemplateUid) throws NEDSSSystemException
    {
		Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> rulemetadataDTList = new ArrayList<Object>();
        String SELET_RULE_METADATA_UID="SELECT wa_template_uid \"waTemplateUid\",  rule_cd \"ruleCd\", rule_expression \"ruleExpression\", err_msg_txt \"errMsgTxt\", source_question_identifier \"sourceQuestionIdentifierString\", target_question_identifier \"targetQuestionIdentifierString\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\","
        	+" add_time \"addTime\", add_user_id \"addUserId\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", rule_desc_txt \"ruleDescTxt\", javascript_function \"javascriptFunction\", javascript_function_nm \"javascriptFunctionNm\", "
        	+" wa_rule_metadata_uid \"waRuleMetadataUid\" , user_rule_id \"userRuleId\", logic \"logicValues\", source_values\"sourceValues\", target_type \"targetType\"    FROM WA_rule_metadata where wa_template_uid=?";

        /**
         * Selects a PublicHealthCase from PublicHealthCase table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELET_RULE_METADATA_UID);
            preparedStmt.setLong(1, waTemplateUid);
            resultSet = preparedStmt.executeQuery();		
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				rulemetadataDTList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, WaRuleMetadataDT.class,
						rulemetadataDTList);
				
				if(rulemetadataDTList!= null){
					Iterator<Object> iter = rulemetadataDTList.iterator();
					while(iter.hasNext()){
						WaRuleMetadataDT waRuleMetaDT = (WaRuleMetadataDT)iter.next();
						RuleParserUtil.metaDataCreater(waRuleMetaDT, waRuleMetaDT.getRuleExpression());
					}
				}
				logger.debug("returned Rule Metadata DT list");
			}
        }
        catch(SQLException sqlException)
        {
            logger.fatal("SQLException while selecting " +
                            "a WaRuleMetadataDT ; uid = " + waTemplateUid, sqlException);
            throw new NEDSSDAOSysException( sqlException.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "a WaRuleMetadataDT ; uid = " + waTemplateUid, ex);
            throw new NEDSSDAOSysException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        logger.debug("return a waRuleMetadataDT object");
        return rulemetadataDTList;
    }
		
	
	public void setwaRuleMetadata(long waTemplateUid, Long waTemplateUidNew) throws NEDSSSystemException{
		Collection<Object> ruleMetadataColl = new ArrayList<Object>();
		try{
			 ruleMetadataColl = selectWaRuleMetadataDTByTemplate(waTemplateUid);
		}catch(Exception ex){
			String errorString =  "waRuleMetadataDao :  Error while retrieving waRuleMetadataDTColl for WaTemplate uid:  " + waTemplateUid + " - " + ex.getMessage();
			logger.error("Error in getting the rule Metadata DT Collection "+ex.getMessage());
			throw new NEDSSSystemException(errorString + ex.getMessage());
		}
		Collection<Object> ruleColl = new ArrayList<Object>();
			if(ruleMetadataColl!= null){
				Iterator<Object> iter = ruleMetadataColl.iterator();
				while(iter.hasNext()){
					WaRuleMetadataDT waRuleMetaDT = (WaRuleMetadataDT)iter.next();
					waRuleMetaDT.setWaTemplateUid(waTemplateUidNew);
					ruleColl.add(waRuleMetaDT);
				}
			}
		
		try{
			insertWaRuleMetadataDTColl(ruleMetadataColl,true);
		}catch(Exception ex){
			String errorString =  "waRuleMetadataDao :  Error while Setting waRuleMetadataDTColl for WaTemplate uid:  " + waTemplateUid + " - " + ex.getMessage();
			logger.error("Error in setting the rule Metadata DT Collection "+ex.getMessage());
			throw new NEDSSSystemException(errorString + ex.getMessage());
		}	
	}
	
	/**
     * insertWaRulesHist - Insert the passed rule into either SQL Server or Oracle history
     * @param waRuleMetadataDT 
     */
	
	public void insertWaRulesHist(WaRuleMetadataDT waRuleMetadataDT) throws NEDSSSystemException
	{
		try{
			 insertWaRulesHistSqlServer(waRuleMetadataDT);	 
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());

		}
	}
	
	public void insertWaRulesHistSqlServer(WaRuleMetadataDT waRuleMetadataDT) throws NEDSSSystemException
	{

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet  rs =null;
		int resultCount =0;
		try
		{
			String INSERT_RULE_METADATA_HIST="INSERT INTO WA_rule_metadata_hist(wa_rule_metadata_uid,wa_template_uid,rule_cd,rule_expression,err_msg_txt,source_question_identifier," +
					"target_question_identifier ,record_status_cd,record_status_time,add_time,add_user_id,last_chg_time,last_chg_user_id,rule_desc_txt, " +
					"javascript_function, javascript_function_nm, user_rule_id, logic, source_values,target_type) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_RULE_METADATA_HIST);
			int i = 1;
			preparedStmt.setLong(i++, waRuleMetadataDT.getWaRuleMetadataUid().longValue()); //0
			if(waRuleMetadataDT.getWaTemplateUid()==null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, waRuleMetadataDT.getWaTemplateUid().longValue()); //1
			preparedStmt.setString(i++,waRuleMetadataDT.getRuleCd());//2
			preparedStmt.setString(i++,waRuleMetadataDT.getRuleExpression());//3
			preparedStmt.setString(i++,waRuleMetadataDT.getErrMsgTxt());//4
			preparedStmt.setString(i++,waRuleMetadataDT.getSourceQuestionIdentifierString());//5
			preparedStmt.setString(i++,waRuleMetadataDT.getTargetQuestionIdentifierString());//6
			preparedStmt.setString(i++,waRuleMetadataDT.getRecordStatusCd());//7
			if(waRuleMetadataDT.getRecordStatusTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else	
				preparedStmt.setTimestamp(i++, waRuleMetadataDT.getRecordStatusTime());//8

			if(waRuleMetadataDT.getAddTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else	
				preparedStmt.setTimestamp(i++, waRuleMetadataDT.getAddTime());//9
			if(waRuleMetadataDT.getAddUserId()==null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, waRuleMetadataDT.getAddUserId().longValue());//10
			if(waRuleMetadataDT.getLastChgTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP);
			else	
				preparedStmt.setTimestamp(i++, waRuleMetadataDT.getLastChgTime());//11
			if(waRuleMetadataDT.getLastChgUserId()==null)
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, waRuleMetadataDT.getLastChgUserId().longValue());//12
			preparedStmt.setString(i++,waRuleMetadataDT.getRuleDescTxt());//13

			preparedStmt.setString(i++, waRuleMetadataDT.getJavascriptFunction());//14
			preparedStmt.setString(i++, waRuleMetadataDT.getJavascriptFunctionNm());//15
			preparedStmt.setString(i++, waRuleMetadataDT.getUserRuleId());//16
			preparedStmt.setString(i++, waRuleMetadataDT.getLogicValues());//17
			preparedStmt.setString(i++, waRuleMetadataDT.getSourceValues());//18
			preparedStmt.setString(i++, waRuleMetadataDT.getTargetType());//19
			
			
			
			resultCount = preparedStmt.executeUpdate();
			logger.debug("done insert a new Rule_meta_data! "+waRuleMetadataDT.toString());
			
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while inserting " +
					"a new entry in Rule_meta_data table: \n"+sqlex.getMessage(), sqlex);
			logger.fatal("waRuleMetadataDT DT is "+waRuleMetadataDT.toString());
			logger.fatal("waRuleMetadataDT resultCount is "+resultCount);
			throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting into Rule_meta_data TABLE, exception = " + ex.getMessage(), ex);
			logger.fatal("waRuleMetadataDT DT is "+waRuleMetadataDT.toString());
			logger.fatal("waRuleMetadataDT resultCount is "+resultCount);
			throw new NEDSSSystemException(ex.toString());
		}

		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
	}
	
	
	/**
     * getComponentMapForQuestions - get the component type for each question
     * Participations that are the target of enable/disable require special logic.
     * @param collection of WaUiMetadataSummaryDT 
     * @return map of (Question Id, Component Uid)
     */
	
	Map<String, Integer> getComponentMapForQuestions(Collection<Object> questionSummaryList) {
		Map<String,Integer> ruleComponentMap = new HashMap<String, Integer>();
		try{
			WaUiMetadataSummaryDT summaryDT = null;
			if (questionSummaryList == null)
				return ruleComponentMap;
			Iterator sumIter = questionSummaryList.iterator();
			while (sumIter.hasNext()) {
				summaryDT = (WaUiMetadataSummaryDT) sumIter.next();
				String quesId = summaryDT.getQuestionIdentifier();
				if (quesId != null && !quesId.isEmpty() && summaryDT.getNbsUIComponentUid() != null)
					ruleComponentMap.put(quesId, summaryDT.getNbsUIComponentUid().intValue());
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return ruleComponentMap;
	}
	
	/**
	 * getComponentMapForQuestionsLabel: get the question identifier - question label map
	 * @param questionSummaryList
	 * @return
	 */
	
	Map<String, String> getComponentMapForQuestionsLabel(Collection<Object> questionSummaryList) {
		Map<String,String> ruleComponentMap = new HashMap<String, String>();
		try{
			WaUiMetadataSummaryDT summaryDT = null;
			if (questionSummaryList == null)
				return ruleComponentMap;
			Iterator sumIter = questionSummaryList.iterator();
			while (sumIter.hasNext()) {
				summaryDT = (WaUiMetadataSummaryDT) sumIter.next();
				String quesId = summaryDT.getQuestionIdentifier();
				if (quesId != null && !quesId.isEmpty() && summaryDT.getNbsUIComponentUid() != null)
					ruleComponentMap.put(quesId, summaryDT.getQuestionLabel());
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return ruleComponentMap;
	}
	
	
	
	
	/**
     * getPreviousSeletedTargets - get the target of other rules of the same type.
     *    Because you don't want two disable/enable, hide/show or two require ifs targeting the same field
     * On the other hand, it is okay if a require if and an enable or disable have the 
     * same target.
     * @param templateUID - page id
     * @param mode - create edit
     * @param ruleId - null if new or ruleId if the rule exists
     * @param ruleCode - Enable, Disable, Require If, Hide or Unhide (this isn't called for Date Compare)
     * @return arrayList of Question Identifiers
     */	
	public Collection<String> getPreviousSeletedTargets (Long templateUid, String mode, String ruleId, String ruleCode) throws NEDSSSystemException
	{
		
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;		
		ArrayList<String> list = new ArrayList<String>(); 
		String sql = null;
		String ruleCodeStr = "";
		
		if (ruleCode.equals("Require If"))
			ruleCodeStr = "'Require If'";
		else if (ruleCode.equals("Hide"))
			ruleCodeStr = "'Hide','Unhide'";
		else if (ruleCode.equals("Unhide"))
			ruleCodeStr = "'Hide','Unhide'";
		else
			ruleCodeStr = "'Enable','Disable'";
		try
		{		
			sql="select target_question_identifier from wa_rule_metadata where rule_cd in (" + ruleCodeStr + ") and wa_template_uid=?";
			
			if("Edit".equalsIgnoreCase(mode) || "Update".equalsIgnoreCase(mode))
				sql="select target_question_identifier from wa_rule_metadata where rule_cd in (" + ruleCodeStr + ") and wa_template_uid=?" +
			    " and wa_rule_metadata_uid not in (" + ruleId + ")";					
			
			
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(sql);
			preparedStmt.setLong(1, templateUid);
			
			resultSet = preparedStmt.executeQuery();
			while (resultSet.next())
			{
				String data= resultSet.getString(1);
				convertStringToArray(data, list);
			}			
			
		}
		catch(SQLException sqlExcepton)
		{	
			logger.fatal("SQLException  = "+sqlExcepton.getMessage(), sqlExcepton);
			throw new NEDSSDAOSysException( sqlExcepton.getMessage());
		}
		catch(NEDSSSystemException nedssSysException)
		{	
			logger.fatal("NEDSSSystemException  = "+nedssSysException.getMessage(), nedssSysException);
			throw new NEDSSDAOSysException( nedssSysException.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
		if("Edit".equalsIgnoreCase(mode)){
			try
			{	
				sql="select target_question_identifier from wa_rule_metadata where rule_cd in  (" + ruleCodeStr + ")  and wa_template_uid=?" +
					    " and wa_rule_metadata_uid in (" + ruleId + ")";
				
				dbConnection = getConnection();
				preparedStmt = dbConnection.prepareStatement(sql);
				preparedStmt.setLong(1, templateUid);
				
				resultSet = preparedStmt.executeQuery();
				while (resultSet.next())
				{
					String data= resultSet.getString(1);
					//convertStringToArray(data, list);
					removeStringToArray(data, list);
				}			
				
			}
			catch(SQLException sqlExcepton)
			{	
				logger.fatal("SQLException  = "+sqlExcepton.getMessage(), sqlExcepton);
				throw new NEDSSDAOSysException( sqlExcepton.getMessage());
			}
			catch(NEDSSSystemException nedssSysException)
			{	
				logger.fatal("NEDSSSystemException  = "+nedssSysException.getMessage(), nedssSysException);
				throw new NEDSSDAOSysException( nedssSysException.getMessage());
			}
			finally
			{
				closeResultSet(resultSet);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
		}
		return list;
	}
	
	private void convertStringToArray(String sourceVal, ArrayList<String> inList) {
		try{
			String[] sourceValArray = sourceVal.split(",");
			for( String s: sourceValArray){
				if(!inList.contains(s))
					inList.add(s);
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	private void removeStringToArray(String sourceVal, ArrayList<String> inList) {
		try{
			String[] sourceValArray = sourceVal.split(",");
			for( String s: sourceValArray){
				if(inList.contains(s))
					inList.remove(s);
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	private String getPreviousSelectedTargetString(Long templateUid, String mode, String ruleId, String ruleCode) {
		Collection<String> inList = null;
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		
		try{
			if("View".equalsIgnoreCase(mode) || "Update".equalsIgnoreCase(mode) ){
				return null;
			}
			
			//We want to allow the same destination to be used by Enable/Disable and Require If
			inList = this.getPreviousSeletedTargets(templateUid, mode, ruleId, ruleCode);
	
			
			for (Iterator<String> it = inList.iterator(); it.hasNext();) 
			{
				if(flag){
					sb.append(",");
				}
				sb.append("'").append((String)it.next()).append("'");
				
				flag = true;
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		return sb.toString();
	}
}