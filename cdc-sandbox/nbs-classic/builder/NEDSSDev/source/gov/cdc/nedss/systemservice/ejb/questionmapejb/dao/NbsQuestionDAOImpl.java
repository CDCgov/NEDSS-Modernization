package gov.cdc.nedss.systemservice.ejb.questionmapejb.dao;

import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.AggregateSummaryDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.RulesDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceFieldDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceValueDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.TargetFieldDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.TargetValueDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.dt.PrePopMappingDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class NbsQuestionDAOImpl extends DAOBase {
	static final LogUtils logger = new LogUtils(NbsQuestionDAOImpl.class
			.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	public static final String GET_INV_FORMCODE = "SELECT distinct NBSM.Investigation_form_cd "
		+ "FROM NBS_UI_metadata NBSM, NBS_Question NBSQ "
		+ "WHERE "
		+ "NBSM.nbs_question_uid = NBSQ.nbs_question_uid and "
		+ "NBSQ.question_identifier in ";


	public Collection<Object>  retrievePamOuestions()
			throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for pam questions: NbsQuestionDAOImpl", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
	      //  logic to check if code has seperate table
	      {
	    	  sqlQuery = WumSqlQuery.PAM_QUESTION_OID_METADATA_SQL;
	      }
	      try {
			preparedStmt = dbConnection
					.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> questionsList = new ArrayList<Object> ();
			if(resultSet!=null){
			resultSetMetaData = resultSet.getMetaData();


			questionsList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
					resultSetMetaData, NbsQuestionMetadata.class, questionsList);

			logger.debug("returned questions list");
			}
			return questionsList;
		} catch (SQLException se) {
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
					.fatal(
							"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
							reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	private Collection<Object>  retrieveRules()
	        throws NEDSSSystemException {

			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			ResultSet resultSet = null;
			ResultSetMetaData resultSetMetaData = null;
			ResultSetUtils resultSetUtils = new ResultSetUtils();
			String rulesSqlQuery = null;

			try {
				dbConnection = getConnection();
			} catch (NEDSSSystemException nsex) {
				logger.fatal("SQLException while obtaining database connection "
			+ "for rules: NbsQuestionDAOImpl", nsex);
				throw new NEDSSSystemException(nsex.getMessage(), nsex);
			}
			//  logic to check if code has separate table
			{
				rulesSqlQuery = WumSqlQuery.RULES_METADATA_SQL;
			}
			try {
				preparedStmt = dbConnection
				.prepareStatement(rulesSqlQuery);
				resultSet = preparedStmt.executeQuery();
				ArrayList<Object> rulesList = new ArrayList<Object> ();
				if(resultSet!=null){
					resultSetMetaData = resultSet.getMetaData();
					rulesList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
							resultSetMetaData, RulesDT.class, rulesList);
					logger.debug("returned questions list");
				}
				return rulesList;
			} catch (SQLException se) {
				throw new NEDSSDAOSysException("SQLException while selecting "
			+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
			} catch (ResultSetUtilsException reuex) {
				logger.fatal(
					"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
					reuex);
				throw new NEDSSDAOSysException(reuex.toString(), reuex);
			} finally {
				closeResultSet(resultSet);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
	}

	private Collection<Object>  retrieveRuleSources()
    	throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String ruleSourceQuery = null;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for rules: NbsQuestionDAOImpl", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has separate table
		{
			ruleSourceQuery = WumSqlQuery.RULES_METADATA_SOURCE_SQL;
		}
		try {
			preparedStmt = dbConnection
			.prepareStatement(ruleSourceQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> rulesList = new ArrayList<Object> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();
				rulesList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, SourceFieldDT.class, rulesList);
				logger.debug("returned questions list");
			}
			return rulesList;
		} catch (SQLException se) {
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger.fatal(
					"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
			reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	private Collection<Object>  retrieveRuleSourcesValues()
	throws NEDSSSystemException {

	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	ResultSet resultSet = null;
	ResultSetMetaData resultSetMetaData = null;
	ResultSetUtils resultSetUtils = new ResultSetUtils();
	String ruleSourceValueQuery = null;

	try {
		dbConnection = getConnection();
	} catch (NEDSSSystemException nsex) {
		logger.fatal("SQLException while obtaining database connection "
				+ "for rules: NbsQuestionDAOImpl", nsex);
		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	}
	//  logic to check if code has separate table
	{
		ruleSourceValueQuery = WumSqlQuery.RULES_METADATA_SOURCE_VALUE_SQL;
	}
	try {
		preparedStmt = dbConnection
		.prepareStatement(ruleSourceValueQuery);
		resultSet = preparedStmt.executeQuery();
		ArrayList<Object> rulesList = new ArrayList<Object> ();
		if(resultSet!=null){
			resultSetMetaData = resultSet.getMetaData();
			rulesList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
					resultSetMetaData, SourceValueDT.class, rulesList);
			logger.debug("returned questions list");
		}
		return rulesList;
	} catch (SQLException se) {
		throw new NEDSSDAOSysException("SQLException while selecting "
				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	} catch (ResultSetUtilsException reuex) {
		logger.fatal(
				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
		reuex);
		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	} finally {
		closeResultSet(resultSet);
		closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	}
}

	private Collection<Object>  retrieveRuleTargets()
					throws NEDSSSystemException {

	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	ResultSet resultSet = null;
	ResultSetMetaData resultSetMetaData = null;
	ResultSetUtils resultSetUtils = new ResultSetUtils();
	String ruleTargetQuery = null;

	try {
		dbConnection = getConnection();
	} catch (NEDSSSystemException nsex) {
		logger.fatal("SQLException while obtaining database connection "
				+ "for rules: NbsQuestionDAOImpl", nsex);
		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	}
	//  logic to check if code has separate table
	{
		ruleTargetQuery = WumSqlQuery.RULES_METADATA_TARGET_SQL;
	}
	try {
		preparedStmt = dbConnection
		.prepareStatement(ruleTargetQuery);
		resultSet = preparedStmt.executeQuery();
		ArrayList<Object> rulesList = new ArrayList<Object> ();
		if(resultSet!=null){
			resultSetMetaData = resultSet.getMetaData();
			rulesList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
					resultSetMetaData, TargetFieldDT.class, rulesList);
			logger.debug("returned questions list");
		}
		return rulesList;
	} catch (SQLException se) {
		throw new NEDSSDAOSysException("SQLException while selecting "
				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	} catch (ResultSetUtilsException reuex) {
		logger.fatal(
				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
		reuex);
		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	} finally {
		closeResultSet(resultSet);
		closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	}
}

	private Collection<Object>  retrieveRuleTargetsValues() throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String ruleTargetValQuery = null;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for rules: NbsQuestionDAOImpl", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		// logic to check if code has separate table
		{
			ruleTargetValQuery = WumSqlQuery.RULES_METADATA_TARGET_VALUE_SQL;
		}
		try {
			preparedStmt = dbConnection.prepareStatement(ruleTargetValQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> rulesList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				rulesList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, TargetValueDT.class,
						rulesList);
				logger.debug("returned questions list");
			}
			return rulesList;
		} catch (SQLException se) {
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "pam questions collection: NbsQuestionDAOImpl "
					+ se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
					.fatal(
							"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
							reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	/*
	 * get Rules collection from the various collection of rules related data
	 */
	public Collection<Object>  getRuleCollection() {

		Collection<Object>  ruleColl = this.retrieveRules();
		Collection<Object>  ruleSourceColl = this.retrieveRuleSources();
		Collection<Object>  ruleSourceValueColl = this.retrieveRuleSourcesValues();
		// initialize the Collection<Object>  to be returned
		Collection<Object>  rulesColl = new ArrayList<Object> ();
		
		try{
			// null check
			if (ruleColl != null && ruleColl.size() > 0) {
				Iterator<Object> ruleIter = ruleColl.iterator();
	
				while (ruleIter.hasNext()) {
					RulesDT rulesDT = (RulesDT) ruleIter.next();
					rulesDT.setSourceColl(new ArrayList<Object>());
					long ruleInstanceId = rulesDT.getRuleInstanceUid().longValue();
					if (ruleSourceColl != null && ruleSourceColl.size() > 0) {
						Iterator<Object> ruleSourceIter = ruleSourceColl.iterator();
						while (ruleSourceIter.hasNext()) {
							SourceFieldDT sfDT = (SourceFieldDT) ruleSourceIter
									.next();
							sfDT.setSourceValueColl(new ArrayList<Object>());
							long sourceFieldUid = sfDT.getSourceFieldUid()
									.longValue();
							if (ruleSourceValueColl != null
									&& ruleSourceValueColl.size() > 0) {
								Iterator<Object> ruleSourceValueIter = ruleSourceValueColl
										.iterator();
								while (ruleSourceValueIter.hasNext()) {
									SourceValueDT svDT = (SourceValueDT) ruleSourceValueIter
											.next();
									if (svDT.getSourceFieldUid()!=null && (svDT.getSourceFieldUid().longValue() == sourceFieldUid)) {
										sfDT.getSourceValueColl().add(svDT);
									}
								}
							}
							if (sfDT.getRuleInstanceUid()!=null && (sfDT.getRuleInstanceUid().longValue() == ruleInstanceId)) {
								rulesDT.getSourceColl().add(sfDT);
								rulesDT.setQuestionIdentifer(sfDT
										.getQuestionIdentifier());
								rulesDT.setQuestionLabel(sfDT.getQuestionLabel());
								// we might not need this - commented after the new DB changes 
								//rulesDT.setFormCode(sfDT.getInvestigationFormCd());
							}
						}
					}
					rulesColl.add(rulesDT);
	
				}
	
			}
			this.getRuleTargets(rulesColl);
		}catch(Exception ex){
			logger.fatal("Exception ="+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return rulesColl;
	}



	/*
	 * setting the targets to the Rules
	 */


	private Collection<Object>  getRuleTargets(Collection<Object> ruleColl){

		try{
			Collection<Object>  ruleTargetColl = this.retrieveRuleTargets();
			Collection<Object>  ruleTargetValueColl = this.retrieveRuleTargetsValues();
	
			if (ruleColl != null && ruleColl.size() > 0) {
				Iterator<Object> ruleIter = ruleColl.iterator();
	
				while (ruleIter.hasNext()) {
					RulesDT rulesDT = (RulesDT) ruleIter.next();
					rulesDT.setTargetColl(new ArrayList<Object>());
					long ruleInstanceId = rulesDT.getRuleInstanceUid().longValue();
					if (ruleTargetColl != null && ruleTargetColl.size() > 0) {
						Iterator<Object> ruleTargetIter = ruleTargetColl.iterator();
						while (ruleTargetIter.hasNext()) {
							TargetFieldDT tfDT = (TargetFieldDT) ruleTargetIter
									.next();
							tfDT.setTargetValueColl(new ArrayList<Object>());
							long targetFieldUid=0;
							if(tfDT.getTargetFieldUid()  !=null)
								 targetFieldUid = tfDT.getTargetFieldUid()
									.longValue();
							if (ruleTargetValueColl != null
									&& ruleTargetValueColl.size() > 0) {
								Iterator<Object> ruleTargetValueIter = ruleTargetValueColl
										.iterator();
								while (ruleTargetValueIter.hasNext()) {
									TargetValueDT tvDT = (TargetValueDT) ruleTargetValueIter
											.next();
									if (tvDT.getTargetFieldUid()!=null && (tvDT.getTargetFieldUid().longValue() == targetFieldUid)) {
										tfDT.getTargetValueColl().add(tvDT);
									}
								}
							}
							if (tfDT.getRuleInstanceUid() != null && (tfDT.getRuleInstanceUid().longValue() == ruleInstanceId)) {
								rulesDT.getTargetColl().add(tfDT);
	
	
							}
						}
					}
				}
	
			}

		}catch(Exception ex){
			logger.fatal("Exception ="+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return ruleColl;
	}
     
	/***************Start of methods added for Rule Admin UI Tools  *************************************/
	 
	 	 public ArrayList<Object> getRuleList() throws NEDSSSystemException {

	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	String ruleTargetQuery = null;

	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nsex) {
	    		logger.fatal("SQLException while obtaining database connection "
	    				+ "for rules: NbsQuestionDAOImpl", nsex);
	    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	    	}
	    	//  logic to check if code has separate table
	    	{
	    		ruleTargetQuery = WumSqlQuery.RULES_LIST_SQL;
	    	}
	    	try {
	    		preparedStmt = dbConnection
	    		.prepareStatement(ruleTargetQuery);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> rulesList = new ArrayList<Object> ();
	    		if(resultSet!=null){
	    			resultSetMetaData = resultSet.getMetaData();
	    			rulesList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
	    					resultSetMetaData, RulesDT.class, rulesList);
	    			logger.debug("returned questions list");
	    		}
	    		return rulesList;
	    	} catch (SQLException se) {
	    		logger.fatal("SQLException while selecting pam questions collection: NbsQuestionDAOImpl"+se.getMessage(), se);
	    		throw new NEDSSDAOSysException("SQLException while selecting "
	    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	    	} catch (ResultSetUtilsException reuex) {
	    		logger.fatal(
	    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
	    		reuex);
	    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	    	} finally {
	    		closeResultSet(resultSet);
	    		closeStatement(preparedStmt);
	    		releaseConnection(dbConnection);
	    	}
			}

		public ArrayList<Object> findRuleInstances(String whereclause) throws NEDSSSystemException {

	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	String ruleTargetQuery = null;

	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nsex) {
	    		logger.fatal("SQLException while obtaining database connection "
	    				+ "for rules: NbsQuestionDAOImpl", nsex);
	    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	    	}
	    	//  logic to check if code has separate table
	    	{
	    		ruleTargetQuery = WumSqlQuery.RULES_INSTANCES_LIST_SQL + whereclause;
	    	}
	    	try {
	    		preparedStmt = dbConnection
	    		.prepareStatement(ruleTargetQuery);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> rulesList = new ArrayList<Object> ();
	    		if(resultSet!=null){
	    			resultSetMetaData = resultSet.getMetaData();
	    			rulesList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
	    					resultSetMetaData, RulesDT.class, rulesList);
	    			logger.debug("returned questions list");
	    		}
	    		return rulesList;
	    	} catch (SQLException se) {
	    		logger.fatal("SQLException ="+se.getMessage(), se);
	    		throw new NEDSSDAOSysException("SQLException while selecting "
	    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	    	} catch (ResultSetUtilsException reuex) {
	    		logger.fatal(
	    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
	    		reuex);
	    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	    	} finally {
	    		closeResultSet(resultSet);
	    		closeStatement(preparedStmt);
	    		releaseConnection(dbConnection);
	    	}
			}
		

		public ArrayList<Object> findSourceFields(String whereclause) throws NEDSSSystemException {

	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	String ruleTargetQuery = null;

	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nsex) {
	    		logger.fatal("SQLException while obtaining database connection "
	    				+ "for rules: NbsQuestionDAOImpl", nsex);
	    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	    	}
	    	//  logic to check if code has separate table
	    	{
	    		ruleTargetQuery = WumSqlQuery.SOURCE_FIELDS_LIST_SQL + whereclause;
	    	}
	    	try {
	    		preparedStmt = dbConnection
	    		.prepareStatement(ruleTargetQuery);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> sourceFList = new ArrayList<Object> ();
	    		if(resultSet!=null){
	    			resultSetMetaData = resultSet.getMetaData();
	    			sourceFList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
	    					resultSetMetaData, SourceFieldDT.class, sourceFList);
	    			logger.debug("returned questions list");
	    		}
	    		return sourceFList;
	    	} catch (SQLException se) {
	    		logger.fatal("SQLException ="+se.getMessage(), se);
	    		throw new NEDSSDAOSysException("SQLException while selecting "
	    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	    	} catch (ResultSetUtilsException reuex) {
	    		logger.fatal(
	    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
	    		reuex);
	    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	    	} finally {
	    		closeResultSet(resultSet);
	    		closeStatement(preparedStmt);
	    		releaseConnection(dbConnection);
	    	}
			}

		public ArrayList<Object> findTargetFields(String whereclause) throws NEDSSSystemException {

	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	String ruleTargetQuery = null;

	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nsex) {
	    		logger.fatal("SQLException while obtaining database connection "
	    				+ "for rules: NbsQuestionDAOImpl", nsex);
	    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	    	}
	    	//  logic to check if code has separate table
	    	{
	    		ruleTargetQuery = WumSqlQuery.TARGET_FIELDS_LIST_SQL + whereclause;
	    	}
	    	try {
	    		preparedStmt = dbConnection
	    		.prepareStatement(ruleTargetQuery);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> targetFList = new ArrayList<Object> ();
	    		if(resultSet!=null){
	    			resultSetMetaData = resultSet.getMetaData();
	    			targetFList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
	    					resultSetMetaData, TargetFieldDT.class, targetFList);
	    			logger.debug("returned questions list");
	    		}
	    		return targetFList;
	    	} catch (SQLException se) {
	    		logger.fatal("SQLException ="+se.getMessage(), se);
	    		throw new NEDSSDAOSysException("SQLException while selecting "
	    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	    	} catch (ResultSetUtilsException reuex) {
	    		logger.fatal(
	    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
	    		reuex);
	    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	    	} finally {
	    		closeResultSet(resultSet);
	    		closeStatement(preparedStmt);
	    		releaseConnection(dbConnection);
	    	}
			}

		public ArrayList<Object> findSourceValues(String whereclause) throws NEDSSSystemException {

	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	String ruleTargetQuery = null;

	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nsex) {
	    		logger.fatal("SQLException while obtaining database connection "
	    				+ "for rules: NbsQuestionDAOImpl", nsex);
	    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	    	}
	    	//  logic to check if code has separate table
	    	{
	    		ruleTargetQuery = WumSqlQuery.SOURCE_VALUES_LIST_SQL + whereclause;
	    	}
	    	try {
	    		preparedStmt = dbConnection
	    		.prepareStatement(ruleTargetQuery);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> sourceVList = new ArrayList<Object> ();
	    		if(resultSet!=null){
	    			resultSetMetaData = resultSet.getMetaData();
	    			sourceVList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
	    					resultSetMetaData, SourceValueDT.class, sourceVList);
	    			logger.debug("returned questions list");
	    		}
	    		return sourceVList;
	    	} catch (SQLException se) {
	    		logger.fatal("SQLException ="+se.getMessage(), se);
	    		throw new NEDSSDAOSysException("SQLException while selecting "
	    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	    	} catch (ResultSetUtilsException reuex) {
	    		logger.fatal(
	    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
	    		reuex);
	    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	    	} finally {
	    		closeResultSet(resultSet);
	    		closeStatement(preparedStmt);
	    		releaseConnection(dbConnection);
	    	}
			}

		public ArrayList<Object> findTargetValues(String whereclause) throws NEDSSSystemException {

	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	String ruleTargetQuery = null;

	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nsex) {
	    		logger.fatal("SQLException while obtaining database connection "
	    				+ "for rules: NbsQuestionDAOImpl", nsex);
	    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	    	}
	    	//  logic to check if code has separate table
	    	{
	    		ruleTargetQuery = WumSqlQuery.TARGET_VALUES_LIST_SQL + whereclause;
	    	}
	    	try {
	    		preparedStmt = dbConnection
	    		.prepareStatement(ruleTargetQuery);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> targetVList = new ArrayList<Object> ();
	    		if(resultSet!=null){
	    			resultSetMetaData = resultSet.getMetaData();
	    			targetVList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
	    					resultSetMetaData, TargetValueDT.class, targetVList);
	    			logger.debug("returned questions list");
	    		}
	    		return targetVList;
	    	} catch (SQLException se) {
	    		logger.fatal("SQLException ="+se.getMessage(), se);
	    		throw new NEDSSDAOSysException("SQLException while selecting "
	    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	    	} catch (ResultSetUtilsException reuex) {
	    		logger.fatal(
	    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
	    		reuex);
	    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	    	} finally {
	    		closeResultSet(resultSet);
	    		closeStatement(preparedStmt);
	    		releaseConnection(dbConnection);
	    	}
			}

		public ArrayList<Object> getConseqInd() throws NEDSSSystemException {

	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	String ruleTargetQuery = null;

	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nsex) {
	    		logger.fatal("SQLException while obtaining database connection "
	    				+ "for rules: NbsQuestionDAOImpl", nsex);
	    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	    	}
	    	//  logic to check if code has separate table
	    	{
	    		ruleTargetQuery = WumSqlQuery.CONSEQ_IND_LIST_SQL;
	    	}
	    	try {
	    		preparedStmt = dbConnection
	    		.prepareStatement(ruleTargetQuery);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> conseqList = new ArrayList<Object> ();
	    		if(resultSet!=null){
	    			resultSetMetaData = resultSet.getMetaData();
	    			conseqList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
	    					resultSetMetaData, RulesDT.class, conseqList);
	    			logger.debug("returned questions list");
	    		}
	    		return conseqList;
	    	} catch (SQLException se) {
	    		logger.fatal("SQLException ="+se.getMessage(), se);
	    		throw new NEDSSDAOSysException("SQLException while selecting "
	    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	    	} catch (ResultSetUtilsException reuex) {
	    		logger.fatal(
	    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
	    		reuex);
	    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	    	} finally {
	    		closeResultSet(resultSet);
	    		closeStatement(preparedStmt);
	    		releaseConnection(dbConnection);
	    	}
			}

		public ArrayList<Object> getErrorMessages() throws NEDSSSystemException {

	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	String ruleTargetQuery = null;

	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nsex) {
	    		logger.fatal("SQLException while obtaining database connection "
	    				+ "for rules: NbsQuestionDAOImpl", nsex);
	    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	    	}
	    	//  logic to check if code has separate table
	    	{
	    		ruleTargetQuery = WumSqlQuery.ERR_MESSAGE_LIST_SQL;
	    	}
	    	try {
	    		preparedStmt = dbConnection
	    		.prepareStatement(ruleTargetQuery);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> errMessageList = new ArrayList<Object> ();
	    		if(resultSet!=null){
	    			resultSetMetaData = resultSet.getMetaData();
	    			errMessageList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
	    					resultSetMetaData, RulesDT.class, errMessageList);
	    			logger.debug("returned questions list");
	    		}
	    		return errMessageList;
	    	} catch (SQLException se) {
	    		logger.fatal("SQLException ="+se.getMessage(), se);
	    		throw new NEDSSDAOSysException("SQLException while selecting "
	    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	    	} catch (ResultSetUtilsException reuex) {
	    		logger.fatal(
	    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
	    		reuex);
	    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	    	} finally {
	    		closeResultSet(resultSet);
	    		closeStatement(preparedStmt);
	    		releaseConnection(dbConnection);
	    	}
			}

		public ArrayList<Object> getOperatorTypes() throws NEDSSSystemException {

	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	String ruleTargetQuery = null;

	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nsex) {
	    		logger.fatal("SQLException while obtaining database connection "
	    				+ "for rules: NbsQuestionDAOImpl", nsex);
	    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	    	}
	    	//  logic to check if code has separate table
	    	{
	    		ruleTargetQuery = WumSqlQuery.OPERATOR_TYPE_LIST_SQL;
	    	}
	    	try {
	    		preparedStmt = dbConnection
	    		.prepareStatement(ruleTargetQuery);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> opTypeList = new ArrayList<Object> ();
	    		if(resultSet!=null){
	    			resultSetMetaData = resultSet.getMetaData();
	    			opTypeList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
	    					resultSetMetaData, RulesDT.class, opTypeList);
	    			logger.debug("returned questions list");
	    		}
	    		return opTypeList;
	    	} catch (SQLException se) {
	    		logger.fatal("SQLException ="+se.getMessage(), se);
	    		throw new NEDSSDAOSysException("SQLException while selecting "
	    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	    	} catch (ResultSetUtilsException reuex) {
	    		logger.fatal(
	    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
	    		reuex);
	    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	    	} finally {
	    		closeResultSet(resultSet);
	    		closeStatement(preparedStmt);
	    		releaseConnection(dbConnection);
	    	}
			}

		public ArrayList<Object> getPAMLabels() throws NEDSSSystemException {

	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ResultSetMetaData resultSetMetaData = null;
	    	ResultSetUtils resultSetUtils = new ResultSetUtils();
	    	String ruleTargetQuery = null;

	    	try {
	    		dbConnection = getConnection();
	    	} catch (NEDSSSystemException nsex) {
	    		logger.fatal("SQLException while obtaining database connection "
	    				+ "for rules: NbsQuestionDAOImpl", nsex);
	    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	    	}
	    	//  logic to check if code has separate table
	    	{
	    		ruleTargetQuery = WumSqlQuery.PAM_LABELS_LIST_SQL;
	    	}
	    	try {
	    		preparedStmt = dbConnection
	    		.prepareStatement(ruleTargetQuery);
	    		resultSet = preparedStmt.executeQuery();
	    		ArrayList<Object> pamList = new ArrayList<Object> ();
	    		if(resultSet!=null){
	    			resultSetMetaData = resultSet.getMetaData();
	    			pamList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
	    					resultSetMetaData, RulesDT.class, pamList);
	    			logger.debug("returned questions list");
	    		}
	    		return pamList;
	    	} catch (SQLException se) {
	    		logger.fatal("SQLException ="+se.getMessage(), se);
	    		throw new NEDSSDAOSysException("SQLException while selecting "
	    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
	    	} catch (ResultSetUtilsException reuex) {
	    		logger.fatal(
	    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
	    		reuex);
	    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
	    	} finally {
	    		closeResultSet(resultSet);
	    		closeStatement(preparedStmt);
	    		releaseConnection(dbConnection);
	    	}
			}



		 public String deleteRuleIns(String str_RuleInstanceUID){
				String l_RuleInstanceUID = "";
				l_RuleInstanceUID = str_RuleInstanceUID;
				String status="";

				String deleteTargetValue = "";
				String deleteTargetfield = "";
				String deleteSourceValue = "";
				String deleteSourceField = "";
				String deleteRuleInstance = "";
				String deleteFrmCodeRuleIns ="";

			        Connection dbConnection = null;
			    	PreparedStatement preparedStmt = null;


				    try
				    {
				        dbConnection = getConnection();
				    //  logic to check if code has separate table
		    	    {
		    	    		deleteTargetValue = WumSqlQuery.DELETE_TARGET_VALUE_SQL ;
		    	    	}
				        preparedStmt = dbConnection.prepareStatement(deleteTargetValue);
				        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
				        preparedStmt.setInt(1, Integer.parseInt(l_RuleInstanceUID)); //1

				        preparedStmt.executeUpdate();

				        status = "deletesuccess";


				    }
			        catch (Exception e)
			        {
			        	status = "deletefailedatTargetSource";
			        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.toString(),e);

			        }
			            try
				            {
				        	    if (preparedStmt != null)
			                	    preparedStmt.close();
				        	    if (dbConnection != null && !dbConnection.isClosed())
				                {
				        	    	dbConnection.close();

				                }
				            }
				            catch (Exception e)
				            {
				            	logger.error("Exception occurred while closing result set, statement and connection :"+e.toString());
				            }

				         if(status.equalsIgnoreCase("deletesuccess"))  {
				            try
						    {
				            	dbConnection = getConnection();
				            //  logic to check if code has separate table
				      	    	{
				      	    		deleteTargetfield = WumSqlQuery.DELETE_TARGET_FIELD_SQL ;
				      	    	}
						        preparedStmt = dbConnection.prepareStatement(deleteTargetfield);
						        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
						        preparedStmt.setInt(1, Integer.parseInt(l_RuleInstanceUID)); //1

						        preparedStmt.executeUpdate();

						        status = "deletesuccess";


						    }
					        catch (Exception e)
					        {
					        	status = "deletefailedatTargetField";
					        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

					        }
					          try
						            {
						        	    if (preparedStmt != null)
					                	    preparedStmt.close();
						        	    if (dbConnection != null && !dbConnection.isClosed())
						                {
						        	    	dbConnection.close();

						                }
						            }
						            catch (Exception e)
						            {
						            	logger.fatal("Exception occurred while closing result set, statement and connection :"+e.toString());
						            }
			               }
				         if(status.equalsIgnoreCase("deletesuccess"))  {
					            try
							    {
					            	dbConnection = getConnection();
					            	{
					            		deleteSourceValue = WumSqlQuery.DELETE_SOURCE_VALUE_SQL ;
					            	}
							        preparedStmt = dbConnection.prepareStatement(deleteSourceValue);
							        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
							        preparedStmt.setInt(1, Integer.parseInt(l_RuleInstanceUID)); //1

							        preparedStmt.executeUpdate();

							        status = "deletesuccess";


							    }
						        catch (Exception e)
						        {
						        	status = "deletefailedatSourceValue";
						        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

						        }
						          try
							            {
							        	    if (preparedStmt != null)
						                	    preparedStmt.close();
							        	    if (dbConnection != null && !dbConnection.isClosed())
							                {
							        	    	dbConnection.close();

							                }
							            }
							            catch (Exception e)
							            {
							            	logger.fatal("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
							            }
				               }

				         if(status.equalsIgnoreCase("deletesuccess"))  {
					            try
							    {
					            	dbConnection = getConnection();
					            //  logic to check if code has separate table
					    	    	{
					    	    		deleteSourceField = WumSqlQuery.DELETE_SOURCE_FIELD_SQL ;
					    	    	}
							        preparedStmt = dbConnection.prepareStatement(deleteSourceField);
							        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
							        preparedStmt.setInt(1, Integer.parseInt(l_RuleInstanceUID)); //1

							        preparedStmt.executeUpdate();

							        status = "deletesuccess";


							    }
						        catch (Exception e)
						        {
						        	status = "deletefailedatSourceField";
						        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

						        }
						          try
							            {
							        	    if (preparedStmt != null)
						                	    preparedStmt.close();
							        	    if (dbConnection != null && !dbConnection.isClosed())
							                {
							        	    	dbConnection.close();

							                }
							            }
							            catch (Exception e)
							            {
							            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
							            }
				               }
				         
				         if(status.equalsIgnoreCase("deletesuccess"))  {
					            try
							    {
					            	dbConnection = getConnection();
					            //  logic to check if code has separate table
					    	    	{
					    	    		deleteFrmCodeRuleIns = WumSqlQuery.DELETE_FRM_CODE_SQL ;
					    	    	}
							        preparedStmt = dbConnection.prepareStatement(deleteFrmCodeRuleIns);
							        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
							        preparedStmt.setInt(1, Integer.parseInt(l_RuleInstanceUID)); //1

							        preparedStmt.executeUpdate();

							        status = "deletesuccess";


							    }
						        catch (Exception e)
						        {
						        	status = "deletefailedatSourceField";
						        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

						        }
						          try
							            {
							        	    if (preparedStmt != null)
						                	    preparedStmt.close();
							        	    if (dbConnection != null && !dbConnection.isClosed())
							                {
							        	    	dbConnection.close();

							                }
							            }
							            catch (Exception e)
							            {
							            	logger.fatal("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
							            }
				               }


				         if(status.equalsIgnoreCase("deletesuccess"))  {
					            try
							    {
					            	dbConnection = getConnection();
					            //  logic to check if code has separate table
					    	    	{
					    	    		deleteRuleInstance = WumSqlQuery.DELETE_RULE_INSTANCE_SQL ;
					    	    	}
							        preparedStmt = dbConnection.prepareStatement(deleteRuleInstance);
							        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
							        preparedStmt.setInt(1, Integer.parseInt(l_RuleInstanceUID)); //1

							        preparedStmt.executeUpdate();

							        status = "deletesuccess";
							    //    dbConnection.commit();


							    }
						        catch (Exception e)
						        {
						        	status = "deletefailedatRuleInstance";
						        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

						        }
						          finally
						           {
						        	try

							            {
							        	    if (preparedStmt != null)
						                	    preparedStmt.close();
							        	    if (dbConnection != null && !dbConnection.isClosed())
							                {
							        	    	dbConnection.close();

							                }
							            }
							            catch (Exception e)
							            {
							            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
							            }
						           }
				               }



		   return status;

		   }
		 
		 public boolean deleteSourceField(String str_srcFieldUID){
				String l_SourceFieldUID = "";
				l_SourceFieldUID = str_srcFieldUID;
				boolean status=false;			
				String deleteSourceValue = "";
				String deleteSourceField = "";			

			        Connection dbConnection = null;
			    	PreparedStatement preparedStmt = null;			   		
					            try
							    {
					            	dbConnection = getConnection();
					            	{
					            		deleteSourceValue = WumSqlQuery.DELETE_SOURCE_VALUE_SF_SQL ;
					            	}
							        preparedStmt = dbConnection.prepareStatement(deleteSourceValue);
							        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
							        preparedStmt.setInt(1, Integer.parseInt(l_SourceFieldUID)); //1

							        preparedStmt.executeUpdate();

							        status = true;


							    }
						        catch (Exception e)
						        {
						        	status = false;
						        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

						        }
						          try
							            {
							        	    if (preparedStmt != null)
						                	    preparedStmt.close();
							        	    if (dbConnection != null && !dbConnection.isClosed())
							                {
							        	    	dbConnection.close();

							                }
							            }
							            catch (Exception e)
							            {
							            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
							            }
				              

				         if(status)  {
					            try
							    {
					            	dbConnection = getConnection();
					            //  logic to check if code has separate table
					    	    	{
					    	    		deleteSourceField = WumSqlQuery.DELETE_SOURCE_FIELD_SF_SQL ;
					    	    	}
							        preparedStmt = dbConnection.prepareStatement(deleteSourceField);
							        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
							        preparedStmt.setInt(1, Integer.parseInt(l_SourceFieldUID)); //1

							        preparedStmt.executeUpdate();

							        status = true;


							    }
						        catch (Exception e)
						        {
						        	status = false;
						        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);
						        }
						          try
							            {
							        	    if (preparedStmt != null)
						                	    preparedStmt.close();
							        	    if (dbConnection != null && !dbConnection.isClosed())
							                {
							        	    	dbConnection.close();

							                }
							            }
							            catch (Exception e)
							            {
							            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
							            }
				               }

				            
						        	try

							            {
							        	    if (preparedStmt != null)
						                	    preparedStmt.close();
							        	    if (dbConnection != null && !dbConnection.isClosed())
							                {
							        	    	dbConnection.close();

							                }
							            }
							            catch (Exception e)
							            {
							            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
							            }
							            return status;

		   }
		 
		 public boolean deleteTargetField(String str_RuleInstanceUID){
				String l_RuleInstanceUID = "";
				l_RuleInstanceUID = str_RuleInstanceUID;
				boolean status=false;

				String deleteTargetValue = "";
				String deleteTargetfield = "";
			        Connection dbConnection = null;
			    	PreparedStatement preparedStmt = null;


				    try
				    {
				        dbConnection = getConnection();
				    //  logic to check if code has separate table
		    	    	{
		    	    		deleteTargetValue = WumSqlQuery.DELETE_TARGET_VALUE_TF_SQL ;
		    	    	}
				        preparedStmt = dbConnection.prepareStatement(deleteTargetValue);
				        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
				        preparedStmt.setInt(1, Integer.parseInt(l_RuleInstanceUID)); //1

				        preparedStmt.executeUpdate();

				        status = true;


				    }
			        catch (Exception e)
			        {
			        	status = false;
			        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

			        }
			            try
				            {
				        	    if (preparedStmt != null)
			                	    preparedStmt.close();
				        	    if (dbConnection != null && !dbConnection.isClosed())
				                {
				        	    	dbConnection.close();

				                }
				            }
				            catch (Exception e)
				            {
				            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
				            }

				         if(status)  {
				            try
						    {
				            	dbConnection = getConnection();
				            //  logic to check if code has separate table
				      	    	{
				      	    		deleteTargetfield = WumSqlQuery.DELETE_TARGET_FIELD_TF_SQL ;
				      	    	}
						        preparedStmt = dbConnection.prepareStatement(deleteTargetfield);
						        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
						        preparedStmt.setInt(1, Integer.parseInt(l_RuleInstanceUID)); //1

						        preparedStmt.executeUpdate();

						        status = true;


						    }
					        catch (Exception e)
					        {
					        	status = false;
					        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);
					        }
					          try
						            {
						        	    if (preparedStmt != null)
					                	    preparedStmt.close();
						        	    if (dbConnection != null && !dbConnection.isClosed())
						                {
						        	    	dbConnection.close();

						                }
						            }
						            catch (Exception e)
						            {
						            	logger.fatal("Exception occurred while closing result set, statement and connection :"+e.toString());
						            }
			               }
				             
						        	try

							            {
							        	    if (preparedStmt != null)
						                	    preparedStmt.close();
							        	    if (dbConnection != null && !dbConnection.isClosed())
							                {
							        	    	dbConnection.close();

							                }
							            }
							            catch (Exception e)
							            {
							            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
							            }    

		   return status;

		   }
		 
		 
		 
		 public String addRuleInstance(RulesDT rulesDT){
		     String l_RuleInstanceUID = "";
		     String status="";
		     String ruleInstanceInsertQuery = null;
		     String findmaxRuleIns = null;
	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	ArrayList<Object> aryl_rules = new ArrayList<Object> ();
	    	
		    	try
			    {
			        dbConnection = getConnection();
			  
	    	    
	    	    	ruleInstanceInsertQuery = WumSqlQuery.ADD_RULE_INSTANCE_SQL ;
	    	    	preparedStmt = dbConnection.prepareStatement(ruleInstanceInsertQuery);			    
			        if(rulesDT.getConseqIndUID().toString().equalsIgnoreCase("")){
			        	preparedStmt.setInt(1,0); //1
			        }else{
			        	   preparedStmt.setInt(1, (rulesDT.getConseqIndUID()).intValue()); //1
			        }
			        
			        preparedStmt.setInt(2, rulesDT.getRuleUid().intValue()); //3
				    preparedStmt.setString(3, rulesDT.getComments()); //4

			        preparedStmt.executeUpdate();
			        status = "insertsuccess";
			    }
		        catch (Exception e)
		        {
		        	status = "insertfailedatRuleIns";
		        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);
		        }
	            try
		            {
		        	    if (preparedStmt != null)
	                	    preparedStmt.close();
		        	    if (dbConnection != null && !dbConnection.isClosed())
		                {
		        	    	dbConnection.close();

		                }
		            }
		            catch (Exception e)
		            {
		            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
		            }
		            
		            if(status.equalsIgnoreCase("insertsuccess")){				 
					   				 
					    dbConnection = null;
					    preparedStmt = null;
					    try {
				    		dbConnection = getConnection();
				    	} catch (NEDSSSystemException nsex) {
				    		logger.fatal("SQLException while obtaining database connection "
				    				+ "for rules: NbsQuestionDAOImpl", nsex);
				    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
				    	}
				    	//  logic to check if code has separate table
				    	{
				    		findmaxRuleIns = WumSqlQuery.MAX_RULE_INSTANCEUID_SQL;
				    	}
				    	try {
				    		preparedStmt = dbConnection.prepareStatement(findmaxRuleIns);
				    		resultSet = preparedStmt.executeQuery();
				    		 while (resultSet.next())
						        { l_RuleInstanceUID = Integer.toString(resultSet.getInt("RuleInsUid"));			
						        }
				    				    		
				    	} catch (SQLException se) {
				    		logger.fatal("SQLException ="+se.getMessage(), se);
				    		throw new NEDSSDAOSysException("SQLException while selecting "
				    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
				    	} catch (Exception e) {
				    		logger.fatal(
				    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
				    		e);
				    		throw new NEDSSDAOSysException(e.toString(), e);
				    	} finally {
				    		closeResultSet(resultSet);
				    		closeStatement(preparedStmt);
				    		releaseConnection(dbConnection);
				    	}
				    	
						}
		            return l_RuleInstanceUID;
	 }

		 
		 public String addSourceField(SourceFieldDT srcFieldDT){
				     String l_SFInstanceUID = "";
				     String status="";
				     String srcFInstanceInsertQuery = null;
				     String findmaxSFIns = null;
	                Connection dbConnection = null;
			    	PreparedStatement preparedStmt = null;
			    	ResultSet resultSet = null;
			    	
			    	
				    	try
					    {
					        dbConnection = getConnection();
					  
			    	    	
			    	    	srcFInstanceInsertQuery = WumSqlQuery.ADD_SF_INSTANCE_SQL ;
					        preparedStmt = dbConnection.prepareStatement(srcFInstanceInsertQuery);			    
					        if(srcFieldDT.getPamQuestionUID().toString().equalsIgnoreCase("")){
					        	preparedStmt.setInt(1,0); //1
					        }else{
					        	preparedStmt.setInt(1, srcFieldDT.getPamQuestionUID().intValue()); //1
					        }
					        
					        preparedStmt.setInt(2, srcFieldDT.getRuleInstanceUid().intValue()); //2
							
					        preparedStmt.executeUpdate();
					        status = "insertsuccess";
					    }
				        catch (Exception e)
				        {
				        	status = "insertfailedatSF";
				        	logger.fatal("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);
				        }
			            try
				            {
				        	    if (preparedStmt != null)
			                	    preparedStmt.close();
				        	    if (dbConnection != null && !dbConnection.isClosed())
				                {
				        	    	dbConnection.close();

				                }
				            }
				            catch (Exception e)
				            {
				            	logger.fatal("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
				            }
				            
				            if(status.equalsIgnoreCase("insertsuccess")){				 
							   				 
							    dbConnection = null;
							    preparedStmt = null;
							    try {
						    		dbConnection = getConnection();
						    	} catch (NEDSSSystemException nsex) {
						    		logger.fatal("SQLException while obtaining database connection "
						    				+ "for rules: NbsQuestionDAOImpl", nsex);
						    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
						    	}
						    	//  logic to check if code has separate table
							    {
						    		findmaxSFIns = WumSqlQuery.MAX_SF_INSTANCEUID_SQL;
						    	}
						    	try {
						    		preparedStmt = dbConnection.prepareStatement(findmaxSFIns);
						    		resultSet = preparedStmt.executeQuery();
						    		 while (resultSet.next())
								        { l_SFInstanceUID = Integer.toString(resultSet.getInt("SourceFUID"));			
								        }
						    				    		
						    	} catch (SQLException se) {
						    		logger.fatal("SQLException  = "+se.getMessage(), se);
						    		throw new NEDSSDAOSysException("SQLException while selecting "
						    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
						    	} catch (Exception e) {
						    		logger.fatal(
						    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
						    		e);
						    		throw new NEDSSDAOSysException(e.toString(), e);
						    	} finally {
						    		closeResultSet(resultSet);
						    		closeStatement(preparedStmt);
						    		releaseConnection(dbConnection);
						    	}
						    	
								}
				            return l_SFInstanceUID;
			 }

		 public String addTargetField(TargetFieldDT tarFieldDT){
		     String l_TFInstanceUID = "";
		     String status="";
		     String tarFInstanceInsertQuery = null;
		     String findmaxTFIns = null;
	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null; 	
	    	
		    	try
			    {
			        dbConnection = getConnection();
			  
	    	    	tarFInstanceInsertQuery = WumSqlQuery.ADD_TF_INSTANCE_SQL ;
	    	    	preparedStmt = dbConnection.prepareStatement(tarFInstanceInsertQuery);			    
			        if(tarFieldDT.getPamQuestionUID().toString().equalsIgnoreCase("")){
			        	preparedStmt.setInt(1,0); //1
			        }else{
			        	preparedStmt.setInt(1, tarFieldDT.getPamQuestionUID().intValue()); //1
			        }
			        
			        preparedStmt.setInt(2, tarFieldDT.getRuleInstanceUid().intValue()); //2			    

			        preparedStmt.executeUpdate();
			        status = "insertsuccess";
			    }
		        catch (Exception e)
		        {
		        	status = "insertfailedatSF";
		        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

		        }
	            try
		            {
		        	    if (preparedStmt != null)
	                	    preparedStmt.close();
		        	    if (dbConnection != null && !dbConnection.isClosed())
		                {
		        	    	dbConnection.close();

		                }
		            }
		            catch (Exception e)
		            {
		            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
		            }
		            
		            if(status.equalsIgnoreCase("insertsuccess")){				 
					   				 
					    dbConnection = null;
					    preparedStmt = null;
					    try {
				    		dbConnection = getConnection();
				    	} catch (NEDSSSystemException nsex) {
				    		logger.fatal("SQLException while obtaining database connection "
				    				+ "for rules: NbsQuestionDAOImpl", nsex);
				    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
				    	}
				    	//  logic to check if code has separate table
				    	{
				    		findmaxTFIns = WumSqlQuery.MAX_TF_INSTANCEUID_SQL;
				    	}
				    	try {
				    		preparedStmt = dbConnection.prepareStatement(findmaxTFIns);
				    		resultSet = preparedStmt.executeQuery();
				    		 while (resultSet.next())
						        { l_TFInstanceUID = Integer.toString(resultSet.getInt("TargetFUID"));			
						        }
				    				    		
				    	} catch (SQLException se) {
				    		logger.fatal("Exception  = "+se.getMessage(), se);
				    		throw new NEDSSDAOSysException("SQLException while selecting "
				    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
				    	} catch (Exception e) {
				    		logger.fatal(
				    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
				    		e);
				    		throw new NEDSSDAOSysException(e.toString(), e);
				    	} finally {
				    		closeResultSet(resultSet);
				    		closeStatement(preparedStmt);
				    		releaseConnection(dbConnection);
				    	}
				    	
						}
		            return l_TFInstanceUID;
	 }

		 
		 public String addSourceValue(SourceValueDT srcValueDT){
		     String l_SVInstanceUID = "";
		     String status="";
		     String SVInstanceInsertQuery = null;
		     String findmaxSVIns = null;
	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	
		    	try
			    {
			        dbConnection = getConnection();
			  
	    	    	
	    	    	SVInstanceInsertQuery = WumSqlQuery.ADD_SV_INSTANCE_SQL ;
	    	    	
			        preparedStmt = dbConnection.prepareStatement(SVInstanceInsertQuery);			    
			       
			        preparedStmt.setInt(1, srcValueDT.getSourceFieldUid().intValue()); //1
			        preparedStmt.setString(2, srcValueDT.getSourceValue()); //4
			        preparedStmt.setInt(3, srcValueDT.getOperatorTypeUid().intValue()); //3
				   

			        preparedStmt.executeUpdate();
			        status = "insertsuccess";
			    }
		        catch (Exception e)
		        {
		        	status = "insertfailedatSVIns";
		        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);
		        }
	            try
		            {
		        	    if (preparedStmt != null)
	                	    preparedStmt.close();
		        	    if (dbConnection != null && !dbConnection.isClosed())
		                {
		        	    	dbConnection.close();

		                }
		            }
		            catch (Exception e)
		            {
		            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
		            }
		            
		            if(status.equalsIgnoreCase("insertsuccess")){				 
					   				 
					    dbConnection = null;
					    preparedStmt = null;
					    try {
				    		dbConnection = getConnection();
				    	} catch (NEDSSSystemException nsex) {
				    		logger.fatal("SQLException while obtaining database connection "
				    				+ "for rules: NbsQuestionDAOImpl", nsex);
				    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
				    	}
				    	//  logic to check if code has separate table
				    	{
				    		findmaxSVIns = WumSqlQuery.MAX_SV_INSTANCEUID_SQL;
				    	}
				    	try {
				    		preparedStmt = dbConnection.prepareStatement(findmaxSVIns);
				    		resultSet = preparedStmt.executeQuery();
				    		 while (resultSet.next())
						        { l_SVInstanceUID = Integer.toString(resultSet.getInt("SourceVUID"));			
						        }
				    				    		
				    	} catch (SQLException se) {
				    		logger.fatal("Exception  = "+se.getMessage(), se);
				    		throw new NEDSSDAOSysException("SQLException while selecting "
				    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
				    	} catch (Exception e) {
				    		logger.fatal(
				    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
				    		e);
				    		throw new NEDSSDAOSysException(e.toString(), e);
				    	} finally {
				    		closeResultSet(resultSet);
				    		closeStatement(preparedStmt);
				    		releaseConnection(dbConnection);
				    	}
				    	
						}
		            return l_SVInstanceUID;
	 }

		 
		 public String addTargetValue(TargetValueDT tarValueDT){
		     String l_TVInstanceUID = "";
		     String status="";
		     String tarVInstanceInsertQuery = null;
		     String findmaxTVIns = null;
	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;   	
		    	try
			    {
			        dbConnection = getConnection();
			  
	    	    	tarVInstanceInsertQuery = WumSqlQuery.ADD_TV_INSTANCE_SQL ;
	    	    	
			        preparedStmt = dbConnection.prepareStatement(tarVInstanceInsertQuery);			    
			      
			       	preparedStmt.setInt(1, tarValueDT.getTargetFieldUid().intValue()); //1 	        
			        preparedStmt.setInt(4, tarValueDT.getConseqIndUid().intValue()); //3
				    preparedStmt.setString(2, tarValueDT.getTargetValue()); //4
				    preparedStmt.setInt(3, tarValueDT.getErrormessageUid().intValue());
				    preparedStmt.setInt(5, tarValueDT.getOperatorTypeUid().intValue());
			        preparedStmt.executeUpdate();
			        status = "insertsuccess";
			    }
		        catch (Exception e)
		        {
		        	status = "insertfailedatTVIns";
		        	logger.fatal("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);
		            e.printStackTrace();

		        }
	            try
		            {
		        	    if (preparedStmt != null)
	                	    preparedStmt.close();
		        	    if (dbConnection != null && !dbConnection.isClosed())
		                {
		        	    	dbConnection.close();

		                }
		            }
		            catch (Exception e)
		            {
		            	logger.fatal("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
		            }
		            
		            if(status.equalsIgnoreCase("insertsuccess")){				 
					   				 
					    dbConnection = null;
					    preparedStmt = null;
					    try {
				    		dbConnection = getConnection();
				    	} catch (NEDSSSystemException nsex) {
				    		logger.fatal("SQLException while obtaining database connection "
				    				+ "for rules: NbsQuestionDAOImpl", nsex);
				    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
				    	}
				    	//  logic to check if code has separate table
				    	{
				    		findmaxTVIns = WumSqlQuery.MAX_TV_INSTANCEUID_SQL;
				    	}
				    	try {
				    		preparedStmt = dbConnection.prepareStatement(findmaxTVIns);
				    		resultSet = preparedStmt.executeQuery();
				    		 while (resultSet.next())
						        { l_TVInstanceUID = Integer.toString(resultSet.getInt("TargetVUID"));			
						        }
				    				    		
				    	} catch (SQLException se) {
				    		logger.fatal("Exception  = "+se.getMessage(), se);
				    		throw new NEDSSDAOSysException("SQLException while selecting "
				    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
				    	} catch (Exception e) {
				    		logger.fatal(
				    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
				    		e);
				    		throw new NEDSSDAOSysException(e.toString(), e);
				    	} finally {
				    		closeResultSet(resultSet);
				    		closeStatement(preparedStmt);
				    		releaseConnection(dbConnection);
				    	}
				    	
						}
		            return l_TVInstanceUID;
	 }
		 public boolean updateSourceValue(SourceValueDT srcValueDT){
		   
		     boolean status=false;
		     String SVInstanceInsertQuery = null;
		     String findmaxSVIns = null;
	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;
	    	
		    	try
			    {
			        dbConnection = getConnection();
			  
	    	    	{
	    	    		SVInstanceInsertQuery = WumSqlQuery.UPDATE_SV_INSTANCE_SQL ;
	    	    	}
			        preparedStmt = dbConnection.prepareStatement(SVInstanceInsertQuery);			    
			       
			        preparedStmt.setInt(3, srcValueDT.getSourceValueUid().intValue()); //1
			        preparedStmt.setString(1, srcValueDT.getSourceValue()); //4
			        preparedStmt.setInt(2, srcValueDT.getOperatorTypeUid().intValue()); //3
				   

			        preparedStmt.executeUpdate();
			        status = true;
			    }
		        catch (Exception e)
		        {
		        	status = false;
		        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

		        }
	            try
		            {
		        	    if (preparedStmt != null)
	                	    preparedStmt.close();
		        	    if (dbConnection != null && !dbConnection.isClosed())
		                {
		        	    	dbConnection.close();

		                }
		            }
		            catch (Exception e)
		            {
		            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
		            }
		            
		           finally {
				    		closeResultSet(resultSet);
				    		closeStatement(preparedStmt);
				    		releaseConnection(dbConnection);
				    	}
				    	
					
		            return status;
	 }
		 
		 public boolean updateTargetValue(TargetValueDT tarValueDT){
		   
		     boolean status=false;
		     String tarVInstanceUpdateQuery = null;	    
	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;   	
		    	try
			    {
			        dbConnection = getConnection();
			  
	    	    	{
	    	    		tarVInstanceUpdateQuery = WumSqlQuery.UPDATE_TV_INSTANCE_SQL ;
	    	    	}
			        preparedStmt = dbConnection.prepareStatement(tarVInstanceUpdateQuery);			    
			      
			       	preparedStmt.setInt(4, tarValueDT.getTargetValueUid().intValue()); //1 	        
			        preparedStmt.setInt(2, tarValueDT.getConseqIndUid().intValue()); //3
				    preparedStmt.setString(1, tarValueDT.getTargetValue()); //4
				    preparedStmt.setInt(3, tarValueDT.getErrormessageUid().intValue());
			        preparedStmt.executeUpdate();
			        status = true;
			    }
		        catch (Exception e)
		        {
		        	status = false;
		        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

		        }
	            try
		            {
		        	    if (preparedStmt != null)
	                	    preparedStmt.close();
		        	    if (dbConnection != null && !dbConnection.isClosed())
		                {
		        	    	dbConnection.close();

		                }
		            }
		            catch (Exception e)
		            {
		            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
		            }
		            
		           finally {
				    		closeResultSet(resultSet);
				    		closeStatement(preparedStmt);
				    		releaseConnection(dbConnection);
				    	}		    	
					
		            return status;
	 }
		 
		 public ArrayList<Object> getFormCode(Collection<Object> questionIdentColl){
			 	String whereClause = "(";
			 	String whereRClause = "";
			 	Connection dbConnection = null;
		    	PreparedStatement preparedStmt = null;
		    	ResultSet resultSet = null;
		    	ResultSetMetaData resultSetMetaData = null;
		    	ResultSetUtils resultSetUtils = new ResultSetUtils();
		    	String retrieveInvFormCode = null;
		    	ArrayList<Object> rulesFormLst = new ArrayList<Object> ();
		    	int count =1;
		    	
			    if(questionIdentColl != null){
			    	Iterator<Object> iter = questionIdentColl.iterator();
			    	while(iter.hasNext()){
			    		String questId = (String)iter.next();
			    		whereClause = "(" +  "'"+questId+"'" + ")" + whereRClause +" ;";
			    		whereRClause = "";
			    		
			    		try{
				    		dbConnection = getConnection();
				    	  }catch (NEDSSSystemException nsex) {
				    		logger.fatal("SQLException while obtaining database connection "
				    				+ "for rules: NbsQuestionDAOImpl", nsex);
				    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
				    	}
				    	//  logic to check if code has separate table
				    	{
				    		retrieveInvFormCode = WumSqlQuery.GET_INV_FORMCODE;
				    	}
				    	retrieveInvFormCode = retrieveInvFormCode+whereClause;
				    	try {
				    		preparedStmt = dbConnection
				    		.prepareStatement(retrieveInvFormCode);
				    		resultSet = preparedStmt.executeQuery();
				    		
				    		if(resultSet!=null){
				    			whereRClause = whereRClause + " and NBSM.Investigation_form_cd in (";
				    			while (resultSet.next())
						        {
				    				whereRClause = whereRClause + "'" + HTMLEncoder.encodeHtml(resultSet.getString("formCode").toString())+ "'" + "," ;	
				    				if(count == questionIdentColl.size()){
				    					rulesFormLst.add(HTMLEncoder.encodeHtml(resultSet.getString("formCode").toString()));
				    				}
					    			logger.debug("returned questions list"); 			    					
						        }
				    			whereRClause = whereRClause.substring(0, whereRClause.length()-1);
				    			whereRClause = whereRClause + ")";
			    			logger.debug("returned questions list");
			    		      }		    		
				    		if(resultSet == null){
				    			/*resultSetMetaData = resultSet.getMetaData();
				    			rulesFormLst = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
				    					resultSetMetaData, RulesDT.class, rulesFormLst);
				    			logger.debug("returned questions list");*/
				    			return rulesFormLst;
				    			
				    		}
				    		count++;
				    		closeResultSet(resultSet);
				    		closeStatement(preparedStmt);
				    		releaseConnection(dbConnection);
				    		
				    	} catch (SQLException se) {
				    		logger.fatal("Exception  = "+se.getMessage(), se);
				    		throw new NEDSSDAOSysException("SQLException while selecting "
				    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
				    	} 
			    	//whereClause = whereClause.substring(0, (whereClause.length()-1))+")";
			    	
			       } 
			     }
			    try{
			    if(resultSet != null){    			
	    			logger.debug("returned questions list");    			
			    }return rulesFormLst;
	    		}	    	
		     finally {
		    		closeResultSet(resultSet);
		    		closeStatement(preparedStmt);
		    		releaseConnection(dbConnection);
		    	}
			 
		 }
		 
		 public boolean associateFormCode(ArrayList<Object>  dDT){
			   
		     boolean status=false;
		     String frmCodeInsertQuery = null;	    
	        Connection dbConnection = null;
	    	PreparedStatement preparedStmt = null;
	    	ResultSet resultSet = null;  
	    	String  deletefrmCode = null;
	    	String strRuleInsUid = null;
	    	int i =0;
	    	try{
		    	dbConnection = getConnection();
			    //  logic to check if code has separate table
	    	    	{
	    	    		deletefrmCode = WumSqlQuery.DELETE_FRM_CODE_SQL ;
	    	    	}
			        preparedStmt = dbConnection.prepareStatement(deletefrmCode);
			        //preparedStmt.setLong(1, Long.getLong(l_strConseqUID).longValue()); //1
			        if(dDT != null && i<dDT.size()){		         
			        	Iterator<Object> iter = dDT.iterator();
						while (iter.hasNext()) {						
							RulesDT dt = (RulesDT)iter.next();
							strRuleInsUid = dt.getRuleInstanceUid().toString();
							break;
						}
			        }
			        preparedStmt.setInt(1, Integer.parseInt(strRuleInsUid)); //1

			        preparedStmt.executeUpdate();

			        status = true;


			    }
		        catch (Exception e)
		        {
		        	status = false;
		        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(), e);

		        }
		            try
			            {
			        	    if (preparedStmt != null)
		                	    preparedStmt.close();
			        	    if (dbConnection != null && !dbConnection.isClosed())
			                {
			        	    	dbConnection.close();

			                }
			            }
			            catch (Exception e)
			            {
			            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
			            }


		     if(status){
	    	  try
			    {
			        dbConnection = getConnection();
			        if(dDT != null && i<dDT.size()){		         
			        	Iterator iter = dDT.iterator();
						while (iter.hasNext()) {
							
							RulesDT dt = (RulesDT)iter.next();	
							
			    	    	{
			    	    		frmCodeInsertQuery = WumSqlQuery.INSERT_FRM_CODE_SQL ;
			    	    	}
					        preparedStmt = dbConnection.prepareStatement(frmCodeInsertQuery);			    
					      
					       	preparedStmt.setInt(1, dt.getRuleInstanceUid().intValue()); //1 	        
					        preparedStmt.setString(2, dt.getFormCode()); //3
						    preparedStmt.setString(3, "Active"); //4
						   
					        preparedStmt.executeUpdate();
					        status = true;
			        
			             }i=i+1;
			        }
			    }
		        catch (Exception e)
		        {
		        	status = false;
		        	logger.error("Exception occurred in retrieveRules method of RuleEngineAdminDB class :"+e.getMessage(),e);
		        }
		      }
	            try
		            {
		        	    if (preparedStmt != null)
	                	    preparedStmt.close();
		        	    if (dbConnection != null && !dbConnection.isClosed())
		                {
		        	    	dbConnection.close();

		                }
		            }
		            catch (Exception e)
		            {
		            	logger.error("Exception occurred while closing result set, statement and connection :"+e.getMessage(), e);
		            }
		            
		           finally {
				    		closeResultSet(resultSet);
				    		closeStatement(preparedStmt);
				    		releaseConnection(dbConnection);
				    	}		    	
					
		            return status;
	 }

		 public ArrayList<Object> findfrmCodes(String strRuleInsUid) throws NEDSSSystemException {

		        Connection dbConnection = null;
		    	PreparedStatement preparedStmt = null;
		    	ResultSet resultSet = null;
		    	ResultSetMetaData resultSetMetaData = null;
		    	ResultSetUtils resultSetUtils = new ResultSetUtils();
		    	String ruleTargetQuery = null;
		    	String deletefrmCode = null;
		    	boolean status=false;
		    	ArrayList<Object> frmList = new ArrayList<Object> ();
		    	           
		    	try {
		    		dbConnection = getConnection();
		    	} catch (NEDSSSystemException nsex) {
		    		logger.fatal("SQLException while obtaining database connection "
		    				+ "for rules: NbsQuestionDAOImpl", nsex);
		    		throw new NEDSSSystemException(nsex.getMessage(), nsex);
		    	}
		    	//  logic to check if code has separate table
		    	{
		    		ruleTargetQuery = WumSqlQuery.FRM_CODE_LIST_SQL;
		    	}
		    	try {
		    		preparedStmt = dbConnection
		    		.prepareStatement(ruleTargetQuery);
		    		preparedStmt.setInt(1, Integer.parseInt(strRuleInsUid)); //1
		    		resultSet = preparedStmt.executeQuery();
		    		
		    		if(resultSet!=null){
		    			resultSetMetaData = resultSet.getMetaData();
		    			frmList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
		    					resultSetMetaData, RulesDT.class, frmList);
		    			logger.debug("returned questions list");
		    		}
		    		
		    	} catch (SQLException se) {
		    		logger.fatal("SQLException  = "+se.getMessage(), se);
		    		throw new NEDSSDAOSysException("SQLException while selecting "
		    				+ "pam questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
		    	} catch (ResultSetUtilsException reuex) {
		    		logger.fatal(
		    				"Error in result set handling while selecting pam questions: NbsQuestionDAOImpl.",
		    		reuex);
		    		throw new NEDSSDAOSysException(reuex.toString(), reuex);
		    	}          
	           finally {
		    		closeResultSet(resultSet);
		    		closeStatement(preparedStmt);
		    		releaseConnection(dbConnection);
		    	}
	            return frmList;
				}

		 /**
		  * retrieveQuestionRequiredNnd retrieves NND Required Fields 
		  * @param formCd
		  * @return ArrayList
		  * @throws NEDSSDAOSysException
		  * @throws NEDSSSystemException
		  */
		 @SuppressWarnings("unchecked")
		public Collection<Object>  retrieveQuestionRequiredNnd(String formCd) throws NEDSSDAOSysException, NEDSSSystemException {

			NbsQuestionMetadata dt = new NbsQuestionMetadata();
			ArrayList<Object> paramList = new ArrayList<Object> ();
			paramList.add(formCd);
			String sql = "SELECT distinct num.nbs_question_uid  nbsQuestionUid ," +
						"nnd.question_identifier questionIdentifier ,"+
						"num.question_label  questionLabel ,"+
						"num.data_location  dataLocation  "+
						"FROM NND_Metadata nnd INNER JOIN NBS_UI_Metadata num " +
						"ON nnd.nbs_ui_metadata_uid = num.nbs_ui_metadata_uid " +
						"WHERE " +
						"(nnd.question_required_nnd = 'R') and (num.standard_nnd_ind_cd is null or num.standard_nnd_ind_cd='F')"+ 
						"AND (nnd.investigation_form_cd = ?)"; 
			

			try {
				paramList = (ArrayList<Object> ) preparedStmtMethod(dt, paramList, sql, NEDSSConstants.SELECT);
	
			} catch (Exception ex) {
				logger.fatal("Exception in retrieveQuestionRequiredNnd: ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			return paramList;

	}
		 
		 /**
		  * retrieveQuestionRequiredNnd retrieves NND Required Fields 
		  * @param formCd
		  * @return ArrayList
		  * @throws NEDSSDAOSysException
		  * @throws NEDSSSystemException
		  */
		 @SuppressWarnings("unchecked")
		public Collection<Object>  retrieveQuestionRequiredNndForDMB(String formCd) throws NEDSSDAOSysException, NEDSSSystemException {

			NbsQuestionMetadata dt = new NbsQuestionMetadata();
			ArrayList<Object> paramList = new ArrayList<Object> ();
			paramList.add(formCd);
			String sql = "SELECT distinct num.nbs_question_uid  nbsQuestionUid ," +
						" nnd.question_identifier questionIdentifier ,"+
						" num.question_label  questionLabel ,"+
						" num.data_use_cd  dataUseCd ,"+
						" num.data_location  dataLocation  "+
						" FROM NND_Metadata nnd INNER JOIN NBS_UI_Metadata num" +
						" ON nnd.nbs_ui_metadata_uid = num.nbs_ui_metadata_uid " +
						" WHERE " +
						" (nnd.question_required_nnd = 'R')"+ 
						" AND (nnd.investigation_form_cd = ?)"+
						" AND num.standard_nnd_ind_cd='F'"; 
			
			
			try {
				paramList = (ArrayList<Object> ) preparedStmtMethod(dt, paramList, sql, NEDSSConstants.SELECT);
	
			} catch (Exception ex) {
				logger.fatal("Exception in retrieveQuestionRequiredNndForDMB: ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			return paramList;

	}

	/**
	 * retrieveAggregateData retrieves Collection<Object>  of Summary Relevant data from NBS_INDICATOR, NBS_AGGREGATE, NBS_TABLE_METADATA, NBS_TABLE, NBS_QUESTION_METADATA ods tables.
	 * @return
	 * @throws NEDSSSystemException
	 */	 
	public Collection<Object>  retrieveAggregateData() throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		
		String sqlQuery = WumSqlQuery.AGGREGATE_METADATA_QUERY;

  	    try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for Aggregate Data: NbsQuestionDAOImpl", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		try {
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> aggSummaryList = new ArrayList<Object> ();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();
				aggSummaryList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, AggregateSummaryDT.class,
						aggSummaryList);

				logger.debug("returned questions list");
			}
			return aggSummaryList;
		} catch (SQLException se) {
			logger.fatal("SQLException  = "+se.getMessage(), se);
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "aggregate summary collection: NbsQuestionDAOImpl "
					+ se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger.fatal("Error in result set handling while selecting aggregate summary collection: NbsQuestionDAOImpl.",reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}		 
	public Collection<Object>  retrieveContactOuestions() throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for contact questions: NbsQuestionDAOImpl", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has seperate table
		{
			sqlQuery = WumSqlQuery.CONTACT_QUESTION_OID_METADATA_SQL;
		}
		try {
			preparedStmt = dbConnection
			.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> questionsList = new ArrayList<Object> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();


				questionsList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, NbsQuestionMetadata.class, questionsList);

				logger.debug("returned questions list");
			}
			return questionsList;
		} catch (SQLException se) {
			logger.fatal("SQLException  = "+se.getMessage(), se);
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "contact questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
			.fatal(
					"Error in result set handling while selecting contact questions: NbsQuestionDAOImpl.",
					reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
}
	
	public Collection<Object>  retrieveDMBOuestions()throws NEDSSSystemException {

			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			ResultSet resultSet = null;
			ResultSetMetaData resultSetMetaData = null;
			ResultSetUtils resultSetUtils = new ResultSetUtils();
			String sqlQuery = null;
			
			try {
				dbConnection = getConnection();
			} catch (NEDSSSystemException nsex) {
				logger.fatal("SQLException while obtaining database connection "
						+ "for dmb questions: NbsQuestionDAOImpl", nsex);
				throw new NEDSSSystemException(nsex.getMessage(), nsex);
			}
			  //  logic to check if code has seperate table
			 {
				  sqlQuery = WumSqlQuery.DMB_QUESTION_OID_METADATA_SQL;
			  }
			  try {
				preparedStmt = dbConnection
						.prepareStatement(sqlQuery);
				resultSet = preparedStmt.executeQuery();
				ArrayList<Object> questionsList = new ArrayList<Object> ();
				if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();
			
			
				questionsList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, NbsQuestionMetadata.class, questionsList);
			
				logger.debug("returned questions list");
				}
				return questionsList;
			} catch (SQLException se) {
				logger.fatal("SQLException  = "+se.getMessage(), se);
				throw new NEDSSDAOSysException("SQLException while selecting "
						+ "dmb questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
			} catch (ResultSetUtilsException reuex) {
				logger
						.fatal(
								"Error in result set handling while selecting dmb questions: NbsQuestionDAOImpl.",
								reuex);
				throw new NEDSSDAOSysException(reuex.toString(), reuex);
			} finally {
				closeResultSet(resultSet);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
}
	
	public Collection<Object>  retrieveGenericTemplateOuestions() throws NEDSSSystemException {


		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;
		String SRT = null;
		
		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for generic questions: NbsQuestionDAOImpl", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		  //  logic to check if code has separate table
		  {
			  sqlQuery = WumSqlQuery.GENERIC_QUESTION_OID_METADATA_SQL;
		  }
		  try {
			preparedStmt = dbConnection
					.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> questionsList = new ArrayList<Object> ();
			if(resultSet!=null){
			resultSetMetaData = resultSet.getMetaData();
		
		
			questionsList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
					resultSetMetaData, NbsQuestionMetadata.class, questionsList);
		
			logger.debug("returned questions list");
			}
			return questionsList;
		} catch (SQLException se) {
			logger.fatal("SQLException  = "+se.getMessage(), se);
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "generic questions collection: NbsQuestionDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger
					.fatal(
							"Error in result set handling while selecting generic questions: NbsQuestionDAOImpl.",
							reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}
	
 /**
	 * ******************** End of Methods added for Rule Engine UI Tool
	 * ***********************************
	 */

	
	@SuppressWarnings("unchecked")
	public Collection<Object>  getCoinfectionQuestionListForFormCd(String formCd) throws NEDSSSystemException {

		ArrayList<Object>  list =new ArrayList<Object>();
		String sqlQuery = null;
		
		 sqlQuery = "select question_identifier \"key\", nbs_question_uid, question_group_seq_nbr \"intValue\", nbs_question_uid \"longKey\", data_location \"altValue\" from nbs_ui_metadata where investigation_form_cd=? and coinfection_ind_cd='T'";
		  try {
			ArrayList<Object>  arrayList = new ArrayList<Object> ();
		      DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		      if (formCd != null) {
		         arrayList.add(formCd);
		      }
		      list = (ArrayList<Object> ) preparedStmtMethod(dropDownCodeDT, arrayList,
		    		  sqlQuery, NEDSSConstants.SELECT);
		
			logger.debug("returned questions list");
			}catch(Exception ex) {
				logger.fatal("Error executing SQL string "+ sqlQuery);
				logger.fatal("Exception raised in getCoinfectionQuestionListForFormCd:"+ex.getMessage(), ex);
				throw new NEDSSSystemException("Exception raised in getCoinfectionQuestionListForFormCd:", ex);
			}
		  
			return list;
	} 
	
	public Collection<Object> retrievePrePopMapping() throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = WumSqlQuery.PRE_POP_MAPPING_SQL;

		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal(
					"SQLException while obtaining database connection " + "for pre-pop mapping: NbsQuestionDAOImpl",
					nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}

		try {
			preparedStmt = dbConnection.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object> prePopMappingList = new ArrayList<Object>();
			if (resultSet != null) {
				resultSetMetaData = resultSet.getMetaData();

				prePopMappingList = (ArrayList<Object>) resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData,
						PrePopMappingDT.class, prePopMappingList);

				logger.debug("returned Mapping DT list");
			}
			return prePopMappingList;
		} catch (SQLException se) {
			logger.fatal("SQLException  = " + se.getMessage(), se);
			throw new NEDSSDAOSysException(
					"SQLException while selecting prePopMappingList: NbsQuestionDAOImpl " + se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger.fatal("Error in result set handling while retrievePrePopMapping: NbsQuestionDAOImpl.", reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
}
