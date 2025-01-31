/**
 * Name:		FindPersonDAOImpl.java
 * Description:	This is a class determine the DAO implementation based on
 *               the information provided in the deployment descriptor.
 * Copyright:	Copyright (c) 2001In
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

package gov.cdc.nedss.proxy.ejb.entityproxyejb.dao;

import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PersonSearchResultTmp;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationPersonInfoVO;
import java.util.Iterator;
import java.util.Map;
import java.util.Collection;
import gov.cdc.nedss.util.StringUtils;


public class FindInvestigationDAOImpl extends SearchResultDAOImpl

{

	// For logging
	static final LogUtils logger = new LogUtils(
			FindInvestigationDAOImpl.class.getName());
	static final int MAX_CACHE_COUNT = 105;
	PropertyUtil propertyUtil = PropertyUtil.getInstance();
	
	public FindInvestigationDAOImpl() throws NEDSSSystemException {
	}

	/**
	 * Formats a String by removing specified characters with another set of
	 * specified characters
	 *
	 * @param toBeRepaced
	 *            is the characters to be replaced
	 * @param specialCharacter
	 * @param replacement
	 *            are the characters to replace the characters being removed
	 * @return String with characters replaced.
	 */
	private String replaceCharacters(String toBeRepaced,
			String specialCharacter, String replacement) {
		int s = 0;
		int e = 0;
			StringBuffer result = new StringBuffer();
	
			try{
			while ((e = toBeRepaced.indexOf(specialCharacter, s)) >= 0) {
				result.append(toBeRepaced.substring(s, e));
				result.append(replacement);
				s = e + specialCharacter.length();
			}
			result.append(toBeRepaced.substring(s));
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result.toString();
	}


	private int getReturnedPatientNumber() {
		if (propertyUtil.getNumberOfRows() != 0) {
			return propertyUtil.getNumberOfRows();
		} else
			throw new NEDSSSystemException(
					"Expected the number of patient search "
							+ "results returned to the caller defined in the property file.");
	}

	// ###################################################################/
	// END: findPatientsByKeyWords(PersonSearchVO find, int cacheNumber, int
	// fromIndex)
	// ###################################################################/

	public static void main(String[] args) {
		try {

			FindInvestigationDAOImpl search = new FindInvestigationDAOImpl();
			/*
			 * PersonSearchVO searchVO = new PersonSearchVO();
			 * searchVO.setLastName("LastName0");
			 * searchVO.setLastNameOperator("=");
			 * searchVO.setFirstName("Williams");
			 * searchVO.setFirstNameOperator("=");
			 * searchVO.setStreetAddr1("StreetAddr1");
			 * searchVO.setStreetAddr1Operator("=");
			 * searchVO.setBirthTime("10/13/1988");
			 * searchVO.setBirthTimeOperator("=");
			 * searchVO.setRootExtensionTxt("Root ext text");
			 * searchVO.setRootExtensionTxtOperator("=");
			 * searchVO.setPhoneNbrTxt("404-417-3151");
			 * searchVO.setPhoneNbrTxtOperator("="); searchVO.setActive(true);
			 * ArrayList<Object> arr =
			 * search.findPersonsByKeyWords(searchVO,0,0); StringBuffer
			 * testQuery = new StringBuffer(" ");
			 * testQuery.append(" ORDER BY 1");
			 * logger.debug("Complete Query is: " + testQuery.toString());
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	


	public ArrayList<Object> findPatientsByKeyWords(PatientSearchVO find,
			int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:findPatientsByKeyWords4 : START");

		String finalQuery="";
		ArrayList<Object> result = new ArrayList<Object>();
		try{
			HashMap<Object, Object> revisionsWithMprs = new HashMap<Object, Object>(); // key
																						// is
																						// person_uid,
																						// value
																						// is
																						// person_parent_uid
	
			if (cacheNumber <= 0) {
				// if cacheNumber if not specified by the client, set it to the max
				// number of records to be
				// returned by the SQL select statement
				cacheNumber = this.getReturnedPatientNumber();
			}
	
			ArrayList<Object> queries;
			//if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I"))//Investigation
				queries= buildSearchQueryInvestigation(find, cacheNumber, nbsSecurityObj);
		//	else if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("LR"))//Laboratory Report
			//	queries= buildSearchQueryLaboratoryReport(find, cacheNumber, nbsSecurityObj);
		//	else
			//	queries= buildSearchQuery(find, cacheNumber);
			//String countSearchQuery = "";
			String searchQuery = "";
	
			if (queries != null && queries.size() > 1) {
				searchQuery = (String) queries.get(1);
			} 
	
			// nclogger.info("SEARCH QUERY: " + searchQuery);
			// nclogger.info("COUNT SEARCH QUERY: " + countSearchQuery);
			PersonSearchResultTmp searchResultName = new PersonSearchResultTmp();
//			Integer intTotalCount = (Integer) preparedStmtMethod(searchResultName,
//					null, countSearchQuery, NEDSSConstants.SELECT_COUNT);
			// nclogger.info("COUNT :" + intTotalCount);
	
			ArrayList<Object> personVOs = null;
	
			/*if (intTotalCount == null || intTotalCount.intValue() <= 0) {
				// if count query returned less than one item, there is
				// no need to waiste time performing the full search/retrieval
				personVOs = new ArrayList<Object>();
				intTotalCount = new Integer(0);
			} else {*/
				
				if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I"))
						finalQuery=searchQuery;//buildRetrievalQueryInvestigation(searchQuery);
					else	
						finalQuery = buildRetrievalQuery(searchQuery);
	
				 logger.debug("FINAL QUERY: " + finalQuery);

				ArrayList<?> searchResult = (ArrayList<?>) preparedStmtMethod(
						searchResultName, null, finalQuery, NEDSSConstants.SELECT);
	
				// assemble VOs into the form expected by the client (mprs, include
				// revisions, etc)

				if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I")){
					//personVOs= assembleInvestigationData(searchResult);
					personVOs= assembleInvestigationData(searchResult,finalQuery);
				}
			//}
	
			DisplayPersonList displayPersonList = new DisplayPersonList(
					personVOs.size(), personVOs, fromIndex,
					personVOs.size());
			result.add(displayPersonList);
			if(finalQuery!=null && finalQuery.equalsIgnoreCase(""))//No results
				finalQuery = searchQuery;
			result.add(finalQuery);//To have access to the query from outside of the EJB and then being able to use it to save the dynamic query from Event Search Investigation (search results page).
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	/**
	 * findPatientsByQuery: this method is similar to findPatientsByKeyWords but we don't need to build the query based on the VO
	 * because we already have the query retrieved from the DB. This method is used from custom queues from Home Page.
	 * @param find
	 * @param finalQuery
	 * @param cacheNumber
	 * @param fromIndex
	 * @param nbsSecurityObj
	 * @return
	 */
	
	public ArrayList<Object> findPatientsByQuery(PatientSearchVO find, String finalQuery,
			int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) {
		
	
		//Add the select part of the the query to the finalQuery. It was removed because SQL cannot have a column with more than 8000 characters
		
		//String selectQuery = "select distinct top 100 person.last_nm investigatorLastName, person.first_nm investigatorFirstName, person.person_uid investigatorUid, person.local_id investigatorLocalId, Public_health_case.investigation_status_cd  investigationStatusCd , code1.code_short_desc_txt  investigationStatusDescTxt, Public_health_case.local_id  localId , Public_health_case.activity_from_time  activityFromTime , Public_health_case.cd  cd , Public_health_case.cd_desc_txt conditionCodeText, Public_health_case.case_class_cd  caseClassCd , Public_health_case.add_user_id  addUserId , Public_health_case.last_chg_user_id lastChgUserId , code3.code_short_desc_txt caseClassCodeTxt, Public_health_case.jurisdiction_cd  jurisdictionCd, ISNULL(person1.last_nm,'No Last') patientLastName, ISNULL(person1.first_nm,'No First') patientFirstName, person1.person_parent_uid MPRUid, code4.code_desc_txt   jurisdictionDescTxt, Public_health_case.public_health_case_uid publicHealthCaseUid, Public_health_case.status_cd  statusCd, Public_health_case.record_status_cd recordStatusCd, Notification.record_status_cd  notifRecordStatusCd, Notification.local_id  notifLocalId ";
	   String selectQuery="";
		//Adding the oids
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
  	    String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);  	
		finalQuery = selectQuery+finalQuery+" AND "+dataAccessWhereClause;
	
		ArrayList<Object> queries = new ArrayList<Object>();

		
		ArrayList<Object> result = new ArrayList<Object>();
		try{
			HashMap<Object, Object> revisionsWithMprs = new HashMap<Object, Object>(); // key
																						// is
																						// person_uid,
																						// value
																						// is
																						// person_parent_uid
	
			if (cacheNumber <= 0) {
				// if cacheNumber if not specified by the client, set it to the max
				// number of records to be
				// returned by the SQL select statement
				cacheNumber = this.getReturnedPatientNumber();
			}
	
			//if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I"))//Investigation
			queries.add("Select COUNT(*) from ("+finalQuery+")countQuery");	
			queries.add(finalQuery); //buildSearchQueryInvestigation(find, cacheNumber, nbsSecurityObj);
		//	else if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("LR"))//Laboratory Report
			//	queries= buildSearchQueryLaboratoryReport(find, cacheNumber, nbsSecurityObj);
		//	else
			//	queries= buildSearchQuery(find, cacheNumber);
			String countSearchQuery = "";
			String searchQuery = "";
	
			if (queries != null && queries.size() > 1) {
				countSearchQuery = (String) queries.get(0);
				searchQuery = (String) queries.get(1);
			} else {
				countSearchQuery = "SELECT COUNT(*) from PERSON WHERE 1=0";
			}
	
			// nclogger.info("SEARCH QUERY: " + searchQuery);
			// nclogger.info("COUNT SEARCH QUERY: " + countSearchQuery);
			PersonSearchResultTmp searchResultName = new PersonSearchResultTmp();
			Integer intTotalCount = (Integer) preparedStmtMethod(searchResultName,
					null, countSearchQuery, NEDSSConstants.SELECT_COUNT);
			// nclogger.info("COUNT :" + intTotalCount);
	
			ArrayList<Object> personVOs = null;
	
			if (intTotalCount == null || intTotalCount.intValue() <= 0) {
				// if count query returned less than one item, there is
				// no need to waist time performing the full search/retrieval
				personVOs = new ArrayList<Object>();
				intTotalCount = new Integer(0);
			} else {
				
				if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I"))
						finalQuery=searchQuery;//buildRetrievalQueryInvestigation(searchQuery);
					else	
						finalQuery = buildRetrievalQuery(searchQuery);
	
				 logger.debug("FINAL QUERY: " + finalQuery);

				ArrayList<?> searchResult = (ArrayList<?>) preparedStmtMethod(
						searchResultName, null, finalQuery, NEDSSConstants.SELECT);
	
			
		
				// assemble VOs into the form expected by the client (mprs, include
				// revisions, etc)

				if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I")){
					personVOs= assembleInvestigationData(searchResult,finalQuery);
					//personVOs= assembleInvestigationData(searchResult);
				}
			}
	
			DisplayPersonList displayPersonList = new DisplayPersonList(
					intTotalCount.intValue(), personVOs, fromIndex,
					personVOs.size());
			result.add(displayPersonList);
			
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	/**
	 * buildSearchQueryInvestigation:
	 * @param find
	 * @param cacheNumber
	 * @param nbsSecurityObj
	 * @return
	 */
	
	
	private ArrayList<Object> buildSearchQueryInvestigation(PatientSearchVO find,
			int cacheNumber, NBSSecurityObj nbsSecurityObj) {
		ArrayList<Object> result = new ArrayList<Object>();
		try{
			ArrayList<Object> specifiedSearchCriteriaTypes = computeSearchCriteriaTypes(find);
	
			if (specifiedSearchCriteriaTypes != null
					&& !specifiedSearchCriteriaTypes.isEmpty()) {
				int sizeSC = specifiedSearchCriteriaTypes.size();
	
				String nestedQuery = "";
				String countQuery = "";
	
				for (int i = 0; i < sizeSC; i++) {
					int curSearchDataType = ((Integer) specifiedSearchCriteriaTypes
							.get(i)).intValue();
	
					if (i == 0)// first set of search criteria
					{
						nestedQuery = buildFirstNestedQueryInvestigation(curSearchDataType, find, nbsSecurityObj);
					} else// other than first set of search criteria
					{
						nestedQuery = buildNonFirstNestedQueryInvestigation(curSearchDataType,
								find, nestedQuery, i);
					}
					
					if (i == sizeSC - 1)// last set of search criteria
					{
						String temp = nestedQuery;
						nestedQuery = finishBuildingSearchQueryInvestigation(nestedQuery,
								cacheNumber);
						countQuery = finishBuildingSearchCountQueryInvestigation(temp);
					}
				}
	
				result.add(countQuery);
				result.add(nestedQuery);
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}
	
	private String finishBuildingSearchQueryInvestigation(String nestedQuery, int cacheNumber) {
		StringBuffer query = new StringBuffer();
		try{
			//query.append("select distinct p.person_uid \"personUid\", p.person_parent_uid \"personParentUid\" from person p, ");
	
			
				query.append(" select distinct top ");
				query.append(cacheNumber); 
				query.append(nestedQuery.substring(nestedQuery.indexOf("select")+"select".length()));

			//query.append(" where qq.\"personParentUid\" = p.person_parent_uid ");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return query.toString();
	}
	
	/**
	 * finishBuildingSearchCountQueryInvestigation:
	 * @param nestedQuery
	 * @return
	 */

	private String finishBuildingSearchCountQueryInvestigation(String nestedQuery) {
		StringBuffer query = new StringBuffer();
		try{
			// SQL server
				query.append(" select count(*) from ");
				//query.append("(select distinct \"personParentUid\" from ");
				query.append(" ( ");
				query.append(nestedQuery);
				query.append(") derivedTable");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return query.toString();
	}
	
	/**
	 * This method buils second, third, etc. subqueries for patient search. All
	 * subqueries except the first one are joins between the table indicated byt
	 * the search criteria of a specific type and a derived table defined by the
	 * nesedQuery.
	 *
	 * @param searchDataType
	 *            Indicates type of search criteris, which dictates which tables
	 *            have to queried.
	 * @param find
	 *            Represents all search criteria specified by the client.
	 * @param nestedQuery
	 *            Represents a nested query to be used to defined a derived
	 *            table. The nestedQuery has to have "personParentUid" column in
	 *            its SELECT statement.
	 * @param seqNumber
	 *            This value is used to create unique name for the derived
	 *            table.
	 * @return Returns input nestedQuery wrapped into the query, which
	 *         corresponds to the set of search criteria represented by
	 *         searchDataType.
	 */


	private String buildNonFirstNestedQueryInvestigation(int searchDataType,
			PatientSearchVO find, String nestedQuery, int seqNumber) {
		String result = "";
		try{
			String nqDerivedTableName = "nq" + seqNumber;
			String wqDerivedTableName = "wq" + seqNumber;
			String wrappedNestedQuery = wrapNestedQueryInvestigation(nestedQuery,
					nqDerivedTableName);//TODO: change this
	
			switch (searchDataType) {
			case DT_INVESTIGATION_CRITERIA:
				result = buildInvestigationCriteriaNestedQueryInvestigation(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_EVENT_CREATED_UPDATED_BY_USER:
				result = buildCreatedUpdatedUserNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;
			case DT_REPORTING_FACILITY:
				result = buildReportingFacilityNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;
			case DT_REPORTING_PROVIDER:
				result = buildReportingProviderNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;
			case DT_ABC_CASE_ID:
				result = buildABCCaseNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;
			case DT_STATE_CASE_ID:
				result = buildStateCaseNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;
			case DT_CITY_COUNTY_CASE_ID:
				result = buildCityCaseNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;
			case DT_NOTIFICATION:
				result = buildNotificationIDNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;
			case DT_DATE_REPORT:
				result = buildDateReportNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;	
			case DT_INVESTIGATION_CLOSED_DATE:
				result = buildInvestigationClosedDateNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;	
			case DT_INVESTIGATION_CREATE_DATE:
				result = buildInvestigationCreateDateNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;	
			case DT_INVESTIGATION_LAST_UPDATE_DATE:
				result = buildInvestigationlastUpdateDateNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;	
			case DT_INVESTIGATION_START_DATE:
				result = buildInvestigationStartDateNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;	
			case DT_NOTIFICATION_CREATE_DATE:
				result = buildNotificationCreateDateNestedQueryInvestigation(find, wrappedNestedQuery, wqDerivedTableName);
				break;
			default:
				// raise exception
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}
	
	/**
	 * buildNestedQuerySelectInvestigation:
	 * @param wqDerivedTableName
	 * @return
	 */
	private String buildNestedQuerySelectInvestigation(String wqDerivedTableName) {
		StringBuffer select = new StringBuffer("");
		
		select.append(" select ");
		select.append(wqDerivedTableName);
		select.append(".\"investigatorLastName\", ");
		select.append(wqDerivedTableName);
		select.append(".\"investigatorFirstName\", ");
		select.append(wqDerivedTableName);
		select.append(".\"investigatorUid\", ");
		select.append(wqDerivedTableName);
		select.append(".\"investigatorLocalId\", ");
		select.append(wqDerivedTableName);
		select.append(".\"investigationStatusCd\", ");
		select.append(wqDerivedTableName);
		select.append(".\"investigationStatusDescTxt\", ");
        select.append(wqDerivedTableName);
		select.append(".\"localId\", ");
		select.append(wqDerivedTableName);
		select.append(".\"activityFromTime\", ");
		select.append(wqDerivedTableName);
		select.append(".\"cd\", ");
		select.append(wqDerivedTableName);
		select.append(".\"conditionCodeText\", ");
		select.append(wqDerivedTableName);
		select.append(".\"caseClassCd\", ");
		select.append(wqDerivedTableName);
		select.append(".\"addUserId\", ");
		select.append(wqDerivedTableName);
		select.append(".\"lastChgUserId\", ");
		select.append(wqDerivedTableName);
		select.append(".\"caseClassCodeTxt\", ");
		select.append(wqDerivedTableName);
        select.append(".\"jurisdictionCd\", ");
		select.append(wqDerivedTableName);
		select.append(".\"patientLastName\", ");
		select.append(wqDerivedTableName);
		select.append(".\"patientFirstName\", ");
		select.append(wqDerivedTableName);
		select.append(".\"MPRUid\", ");
		select.append(wqDerivedTableName);
		select.append(".\"currentSex\", ");
		select.append(wqDerivedTableName);
		select.append(".\"birthTime\", ");
		select.append(wqDerivedTableName);
		select.append(".\"personLocalID\", ");
		
		
		select.append(wqDerivedTableName);
		select.append(".\"jurisdictionDescTxt\", ");
        select.append(wqDerivedTableName);
		select.append(".\"publicHealthCaseUid\", ");
		select.append(wqDerivedTableName);
		select.append(".\"statusCd\", ");
		select.append(wqDerivedTableName);
		select.append(".\"recordStatusCd\", ");
		select.append(wqDerivedTableName);
		select.append(".\"notifRecordStatusCd\", ");
		select.append(wqDerivedTableName);
		select.append(".\"notifLocalId\" ");
		
		return select.toString();
	}

	/**
	 * buildInvestigationCriteriaFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	private String buildInvestigationCriteriaFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
	        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildInvestigationCriteriaSearchWhereClause(find)+")");
		return query.toString();
	}
	
	/**
	 * buildNotificationIDFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	private String buildNotificationIDFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
        
       query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildNotificationIDSearchWhereClause(find)+")");
		return query.toString();
	}
	
	/**
	 * buildDateReportFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	private String buildDateReportFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildDateReportSearchWhereClause(find)+")");

		return query.toString();
	}
	
	/**
	 * buildInvestigationClosedDateFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	private String buildInvestigationClosedDateFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildInvestigationClosedDateSearchWhereClause(find)+")");

		return query.toString();
	}
	/**
	 * buildInvestigationCreateDateFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	private String buildInvestigationCreateDateFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
        
       query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildInvestigationCreateDateSearchWhereClause(find)+")");

		return query.toString();
	}
	/**
	 * buildInvestigationlastUpdateDateFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	private String buildInvestigationlastUpdateDateFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
        
        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildInvestigationLastUpdateDateSearchWhereClause(find)+")");


		return query.toString();
	}
	/**
	 * buildInvestigationStartDateFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	private String buildInvestigationStartDateFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
        
        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildInvestigationStartDateSearchWhereClause(find)+")");

		return query.toString();
	}
	/**
	 * buildNotificationCreateDateFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	private String buildNotificationCreateDateFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
        
       query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildNotificationCreateDateSearchWhereClause(find)+")");
       		return query.toString();
	}
	
	/**
	 * buildCreatedUpdatedUserFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	
	private String buildCreatedUpdatedUserFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildEventCreatedUpdatedSearchWhereClause(find)+")");
       return query.toString();
	}
	
	/**
	 * buildReportingFacilityFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	private String buildReportingFacilityFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildReportingFacilitySearchWhereClause(find)+")");
       return query.toString();
	}
	/**
	 * buildReportingProviderFirstQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	private String buildReportingProviderFirstQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
	                "where ("+
	                "Public_health_case.record_status_cd = 'OPEN'"+
	                " AND Public_health_case.case_type_cd = 'I'"+
	                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
	                " and " + dataAccessWhereClause+" and ");
	    	query.append(buildReportingProviderSearchWhereClause(find)+")");
        return query.toString();
	}
	
		StringBuffer query = new StringBuffer();
			
		/**
		 * buildABCCaseQueryInvestigation:
		 * @param find
		 * @param nbsSecurityObj
		 * @return
		 */
		private String buildABCCaseQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
	        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
	        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
	        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
		                "where ("+
		                "Public_health_case.record_status_cd = 'OPEN'"+
		                " AND Public_health_case.case_type_cd = 'I'"+
		                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
		                " and " + dataAccessWhereClause+" and ");
		    	query.append(buildABCCaseSearchWhereClause(find)+")");
	       return query.toString();
		}
		/**
		 * buildCityCaseQueryInvestigation:
		 * @param find
		 * @param nbsSecurityObj
		 * @return
		 */
		private String buildCityCaseQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
	        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
	        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
	        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
		                "where ("+
		                "Public_health_case.record_status_cd = 'OPEN'"+
		                " AND Public_health_case.case_type_cd = 'I'"+
		                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
		                " and " + dataAccessWhereClause+" and ");
		    	query.append(buildCityCaseSearchWhereClause(find)+")");

	        return query.toString();
		}
		
		/**
		 * buildStateCaseQueryInvestigation:
		 * @param find
		 * @param nbsSecurityObj
		 * @return
		 */
		
		private String buildStateCaseQueryInvestigation(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
	        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
	        String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
	        query.append(WumSqlQuery.SELECT_MY_INVESTIGATIONS_EVENT_SEARCH_SQL +
		                "where ("+
		                "Public_health_case.record_status_cd = 'OPEN'"+
		                " AND Public_health_case.case_type_cd = 'I'"+
		                //" AND Public_health_case.record_status_cd <> '" + NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE +
		                " and " + dataAccessWhereClause+" and ");
		    	query.append(buildStateCaseSearchWhereClause(find)+")");
	       
	        return query.toString();
		}
		
		
	
	/**
	 * buildInvestigationCriteriaNestedQueryInvestigation(): program area, jurisdiction, condition
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	
	private String buildInvestigationCriteriaNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from public_health_case phc with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("phc.public_health_case_uid and ");
		query.append(buildInvestigationCriteriaSearchWhereClause(find)+"'");
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
		private String buildNotificationIDNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from Notification with (nolock), Act_relationship act with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("act.target_act_uid and ");
		query.append("act.source_act_uid=Notification.notification_uid and");

		
		query.append(buildNotificationIDSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	/**
	 * buildDateReportNestedQueryInvestigation:
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	private String buildDateReportNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from public_health_case with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		
		query.append(buildDateReportSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	/**
	 * buildInvestigationClosedDateNestedQueryInvestigation:
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	private String buildInvestigationClosedDateNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from public_health_case with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		query.append(buildInvestigationClosedDateSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	/**
	 * buildInvestigationCreateDateNestedQueryInvestigation:
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	private String buildInvestigationCreateDateNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from public_health_case with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		query.append(buildInvestigationCreateDateSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	/**
	 * buildInvestigationlastUpdateDateNestedQueryInvestigation:
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	private String buildInvestigationlastUpdateDateNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from public_health_case with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		query.append(buildInvestigationLastUpdateDateSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	/**
	 * buildInvestigationStartDateNestedQueryInvestigation:
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	private String buildInvestigationStartDateNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from public_health_case with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		query.append(buildInvestigationStartDateSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	/**
	 * buildNotificationCreateDateNestedQueryInvestigation:
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	private String buildNotificationCreateDateNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from Notification with (nolock), Act_relationship act with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"notifLocalId\" =");
		query.append("Notification.local_id and ");
		
		
		query.append(buildNotificationCreateDateSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	/**
	 * buildCreatedUpdatedUserNestedQueryInvestigation:
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	
	private String buildCreatedUpdatedUserNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from auth_user auth with (nolock),auth_user auth2 with (nolock), public_health_case with (nolock),( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		
		query.append(wqDerivedTableName);
		query.append(".\"addUserId\" = ");
		query.append("auth.nedss_entry_id and ");
		query.append(wqDerivedTableName);
		query.append(".\"lastChgUserId\" = ");
		query.append("auth2.nedss_entry_id and ");
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		
		query.append(buildEventCreatedUpdatedSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	/**
	 * buildReportingFacilityNestedQueryInvestigation:
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	private String buildReportingFacilityNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from public_health_case with (nolock), person with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		query.append(buildReportingFacilitySearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	/**
	 * buildReportingProviderNestedQueryInvestigation:
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	private String buildReportingProviderNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from public_health_case with (nolock), person with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		query.append(buildReportingProviderSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
		
	/**
	 * buildABCCaseNestedQueryInvestigation:
	 * @param find
	 * @param nestedQuery
	 * @param wqDerivedTableName
	 * @return
	 */
	private String buildABCCaseNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from public_health_case with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
				
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		
		query.append(buildABCCaseSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	private String buildCityCaseNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from auth_user auth with (nolock),auth_user auth2 with (nolock), public_health_case with (nolock), person with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
				
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		
		query.append(buildCityCaseSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	
	private String buildStateCaseNestedQueryInvestigation(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelectInvestigation(wqDerivedTableName));//Everything we need to show

		query.append(" from auth_user auth with (nolock),auth_user auth2 with (nolock), public_health_case with (nolock), person with (nolock), ( ");
		query.append(nestedQuery);//The nested query contains everything that we need to show
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
				
		query.append(wqDerivedTableName);
		query.append(".\"publicHealthCaseUid\" = ");
		query.append("public_health_case.public_health_case_uid and ");
		
		query.append(buildStateCaseSearchWhereClause(find));
		//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

		return query.toString();
	}
	
	
	/**
	 * buildInvestigationCriteriaSearchWhereClause():
	 * Condition, program area, jurisdiction
	 * @param find
	 * @return
	 */
	private String buildInvestigationCriteriaSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;

			//Condition
			if ((find.getConditionSelected() != null && find.getConditionSelected().length > 1)
					|| (find.getConditionSelected() != null && find.getConditionSelected().length == 1
							&& !find.getConditionSelected()[0].trim().equals(""))) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";

				String[] condition = find.getConditionSelected();

				strbResult.append(and);

				String result = ("" + Arrays.asList(condition)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'");

				strbResult.append(" Public_health_case.cd  in (" + result + ")");
			}
			//Program Area
			if ((find.getProgramAreaInvestigationSelected() != null && find.getProgramAreaInvestigationSelected().length > 1)
					|| (find.getProgramAreaInvestigationSelected() != null && find.getProgramAreaInvestigationSelected().length == 1
					&& !find.getProgramAreaInvestigationSelected()[0].trim().equals(""))) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";

				String[] programArea = find.getProgramAreaInvestigationSelected();

				strbResult.append(and);

				String result = ("" + Arrays.asList(programArea)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'");

				strbResult.append(" Public_health_case.prog_area_cd  in (" + result + ")");

			}
			
			//Jurisdiction
			if ((find.getJurisdictionSelected() != null && find.getJurisdictionSelected().length > 1)
					|| (find.getJurisdictionSelected() != null && find.getJurisdictionSelected().length == 1
					&& !find.getJurisdictionSelected()[0].trim().equals(""))) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";

				String[] juris = find.getJurisdictionSelected();

				strbResult.append(and);

				String result = ("" + Arrays.asList(juris)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'");

				strbResult.append(" Public_health_case.jurisdiction_cd  in (" + result + ")");

			}
			
			//Investigation ID
			if ((find.getActType() != null)
					 && find.getActType().equalsIgnoreCase("P10001")) {
				oper = find.getDocOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String investigation = find.getActId().trim();
				String specialCharacter;
	
				if (investigation.indexOf("'") > 0) {
					specialCharacter = "'";
					investigation = replaceCharacters(investigation,
							specialCharacter, "''");
				}
				
				if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
					strbResult.append(and + " upper(public_health_case.local_id)"+oper
							+ "   '" + investigation.toUpperCase() + "'");
					else
						if(oper.equalsIgnoreCase("CT"))
							strbResult.append(and + " upper(public_health_case.local_id) like '%"+
									 investigation.toUpperCase() + "%'");
			}

			//Investigation Status
			if ((find.getInvestigationStatusSelected() != null)
					&& (find.getInvestigationStatusSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String investigationStatus = find.getInvestigationStatusSelected().trim();
				String specialCharacter;
	
				if (investigationStatus.indexOf("'") > 0) {
					specialCharacter = "'";
					investigationStatus = replaceCharacters(investigationStatus,
							specialCharacter, "''");
				}
					strbResult.append(and + " (upper( Public_health_case.investigation_status_cd) " + oper
							+ "   '" + investigationStatus.toUpperCase() + "')");
				
			}

			//Pregnancy Status
			if ((find.getPregnantSelected() != null)
					&& (find.getPregnantSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String pregnant = find.getPregnantSelected().trim();
				String specialCharacter;
	
				if (pregnant.indexOf("'") > 0) {
					specialCharacter = "'";
					pregnant = replaceCharacters(pregnant,
							specialCharacter, "''");
				}
					strbResult.append(and + "( (upper( Public_health_case.pregnant_ind_cd) " + oper
							+ "   '" + pregnant.toUpperCase() + "')");
					
					String legacyQueryOld="select public_health_case_uid from Public_health_case where public_health_case_uid in (select target_act_uid from Act_relationship"+
" where source_act_uid in( select target_act_uid from Act_relationship where source_act_uid in "+
 "(select observation_uid from obs_value_coded with (nolock) where code ='"+pregnant.toUpperCase()+"' and observation_uid in(select observation_uid from "+
 " observation where cd ='INV178'))))";
					
					String legacyQuery="";
					
					 legacyQuery="SELECT public_health_case.public_health_case_uid "+
					"FROM Obs_value_coded with (nolock) INNER JOIN "+
					"Observation ON Obs_value_coded.observation_uid = Observation.observation_uid INNER JOIN "+
					"Act_relationship ON Obs_value_coded.observation_uid = Act_relationship.source_act_uid INNER JOIN "+
					"Observation AS Observation_1 ON Act_relationship.target_act_uid = Observation_1.observation_uid INNER JOIN "+
					"Act_relationship act2 ON Act_relationship.target_act_uid = act2.source_act_uid INNER JOIN "+
					"public_health_case ON act2.target_act_uid = public_health_case.public_health_case_uid "+
					"Where Observation_1.cd like 'inv_f%' and observation.cd ='INV178' and obs_value_coded.code='"+pregnant.toUpperCase()+"'";
			   
					strbResult.append("OR Public_health_case.public_health_case_uid in ("+legacyQuery+"))");
				
			}
			
			//Investigator
			if(find.getInvestigatorSelected()!=null && !find.getInvestigatorSelected().isEmpty()){
				
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String investigator = find.getInvestigatorSelected().trim();
				String specialCharacter;
	
				if (investigator.indexOf("'") > 0) {
					specialCharacter = "'";
					investigator = replaceCharacters(investigator,
							specialCharacter, "''");
				}
					strbResult.append(and + " (upper( person.person_uid) " + oper
							+ "   '" + investigator.toUpperCase() + "')");
					
				
			}
						
			//Notification status
			if (((find.getNotificationCodedValuesSelected() != null)
					&& (find.getNotificationCodedValuesSelected().trim().length() != 0))
					|| find.getNotificationValuesSelected().contains("UNASSIGNED")) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String notification = find.getNotificationCodedValuesSelected().trim();
				String specialCharacter;
	
				if(!((find.getNotificationCodedValuesSelected() != null)
						&& (find.getNotificationCodedValuesSelected().trim().length() != 0))
						&& find.getNotificationValuesSelected().contains("UNASSIGNED"))
					notification+="UNASSIGNED";
				else
					if(find.getNotificationValuesSelected().contains("UNASSIGNED"))
						notification+=",UNASSIGNED";
				
				String[] notificationArray = notification.split(",");
				
				if(notificationArray.length>0)
					strbResult.append(and);
				
		
				//CachedDropDownValues cdv = new CachedDropDownValues();
				
				//TreeMap<?, ?> notificationCodes = cdv.getCodedValuesAsTreeMap("REC_STAT_NOT_UI");
				
				for(int i=0; i<notificationArray.length; i++){
					
					String notif = notificationArray[i];
					
					//if(!notif.equalsIgnoreCase("UNASSIGNED"))
					//	notif=(String)notificationCodes.get(notif);
					
					if (notif.indexOf("'") > 0) {
						specialCharacter = "'";
						notif = replaceCharacters(notif,
								specialCharacter, "''");
					}
		
					if(i==0){
						if(notif.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "( (upper( Notification.record_status_cd)  is NULL) or (Notification.record_status_cd = '')");
						
						else
							strbResult.append( "( (upper( Notification.record_status_cd) " + oper + "   '"
								+ notif.toUpperCase() + "')");
					}
					else{
						if(notif.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "OR (upper( Notification.record_status_cd)  is NULL or (Notification.record_status_cd = ''))");
						else
						strbResult.append( "OR  (upper( Notification.record_status_cd) " + oper + "   '"
								+ notif.toUpperCase() + "')");
					}
						
						
						
				}
				
				if(notificationArray.length>0)
					strbResult.append(")");
				
			}
			
			//Event Status
			if ((find.getEventStatusInitialSelected() != null)
					|| (find.getEventStatusUpdateSelected() != null)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String newInitial = find.getEventStatusInitialSelected().trim();
				String specialCharacter;
	
				if (newInitial.indexOf("'") > 0) {
					specialCharacter = "'";
					newInitial = replaceCharacters(newInitial,
							specialCharacter, "''");
				}
				
				String update = find.getEventStatusUpdateSelected().trim();

				if (update.indexOf("'") > 0) {
					specialCharacter = "'";
					update = replaceCharacters(update,
							specialCharacter, "''");
				}
				
				
				if (newInitial.equalsIgnoreCase("true") && update.equalsIgnoreCase("true"))
					strbResult.append(and + " upper(public_health_case.version_ctrl_nbr) >='1'");
				else
					if (newInitial.equalsIgnoreCase("true"))
						strbResult.append(and + " upper(public_health_case.version_ctrl_nbr) = '1'");
					else
						if (update.equalsIgnoreCase("true"))
							strbResult.append(and + " upper(public_health_case.version_ctrl_nbr) >'1'");
			}
			
			
			//Outbreak Name
			if ((find.getOutbreakNameSelected() != null && find.getOutbreakNameSelected().length > 1)
					|| (find.getOutbreakNameSelected() != null && find.getOutbreakNameSelected().length == 1
					&& !find.getOutbreakNameSelected()[0].trim().equals(""))) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";

				String[] obName = find.getOutbreakNameSelected();

				strbResult.append(and);

				String result = ("" + Arrays.asList(obName)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'");

				strbResult.append(" Public_health_case.OUTBREAK_NAME  in (" + result + ")");

			}
			
			//Case status
			if (((find.getCaseStatusCodedValuesSelected() != null)
					&& (find.getCaseStatusCodedValuesSelected().trim().length() != 0)
					) || find.getCaseStatusListValuesSelected().contains("UNASSIGNED")
					) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String caseStatus = find.getCaseStatusCodedValuesSelected().trim();
				
				if(find.getCaseStatusListValuesSelected().contains("UNASSIGNED") && 
						!((find.getCaseStatusCodedValuesSelected() != null)
								&& (find.getCaseStatusCodedValuesSelected().trim().length() != 0)
								))
					caseStatus+="UNASSIGNED";
				else
					if(find.getCaseStatusListValuesSelected().contains("UNASSIGNED"))
						caseStatus+=",UNASSIGNED";
				String[] caseStatusArray = caseStatus.split(",");
				
				//CachedDropDownValues cdv = new CachedDropDownValues();
				//TreeMap<?, ?> caseStatusCodes = cdv.getCodedValuesAsTreeMap("PHC_CLASS");
				if(caseStatusArray.length>0)
					strbResult.append(and);
				
				
				for(int i=0; i<caseStatusArray.length; i++){
					
					String caseStatusValue = caseStatusArray[i];
					
					//if(!caseStatusValue.equalsIgnoreCase("UNASSIGNED"))
					//	caseStatusValue=(String)caseStatusCodes.get(caseStatusValue);
					String specialCharacter;
					if (caseStatusValue.indexOf("'") > 0) {
						specialCharacter = "'";
						caseStatusValue = replaceCharacters(caseStatusValue,
								specialCharacter, "''");
					}
		
					if(i==0){
						if(caseStatusValue.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "( (upper( Public_health_case.CASE_CLASS_CD)  is NULL) or (Public_health_case.CASE_CLASS_CD = '')");
						
						else
							strbResult.append( "( (upper( Public_health_case.CASE_CLASS_CD) " + oper + "   '"
								+ caseStatusValue.toUpperCase() + "')");
					}
					else{
						if(caseStatusValue.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "OR (upper( Public_health_case.CASE_CLASS_CD)  is NULL or (Public_health_case.CASE_CLASS_CD = ''))");
						else
						strbResult.append( "OR  (upper( Public_health_case.CASE_CLASS_CD) " + oper + "   '"
								+ caseStatusValue.toUpperCase() + "')");
					}
					
				}
				
				if(caseStatusArray.length>0)
					strbResult.append(")");
				
			}
			
			//Current Processing Status
			if (((find.getCurrentProcessCodedValuesSelected() != null)
					&& (find.getCurrentProcessCodedValuesSelected().trim().length() != 0))
					|| find.getCurrentProcessStateValuesSelected().contains("UNASSIGNED")) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
				
				
				String currentProcessStatus = find.getCurrentProcessCodedValuesSelected().trim();
				
				if ((!(find.getCurrentProcessCodedValuesSelected() != null)
						&& (find.getCurrentProcessCodedValuesSelected().trim().length() != 0))
						&& find.getCurrentProcessStateValuesSelected().contains("UNASSIGNED"))
					currentProcessStatus+=",UNASSIGNED";
				else
				if(find.getCurrentProcessStateValuesSelected().contains("UNASSIGNED"))
					currentProcessStatus+=",UNASSIGNED";
				String[] currentProcessStatusArray = currentProcessStatus.split(",");
				
				//CachedDropDownValues cdv = new CachedDropDownValues();
				//TreeMap<?, ?> currentProcessStatusCodes = cdv.getCodedValuesAsTreeMap("CM_PROCESS_STAGE");
				if(currentProcessStatusArray.length>0)
					strbResult.append(and);
				
				
				for(int i=0; i<currentProcessStatusArray.length; i++){
					
					String currentProcessStatusValue = currentProcessStatusArray[i];
					
					//if(!currentProcessStatusValue.equalsIgnoreCase("UNASSIGNED"))
					//	currentProcessStatusValue=(String)currentProcessStatusCodes.get(currentProcessStatusValue);
					String specialCharacter;
					if (currentProcessStatusValue.indexOf("'") > 0) {
						specialCharacter = "'";
						currentProcessStatusValue = replaceCharacters(currentProcessStatusValue,
								specialCharacter, "''");
					}
		
					if(i==0){
						if(currentProcessStatusValue.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "( (upper(  Public_health_case.CURR_PROCESS_STATE_CD)  is NULL) or (Public_health_case.CURR_PROCESS_STATE_CD = '')");
						
						else
							strbResult.append( "( (upper(  Public_health_case.CURR_PROCESS_STATE_CD) " + oper + "   '"
								+ currentProcessStatusValue.toUpperCase() + "')");
					}
					else{
						if(currentProcessStatusValue.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "OR (upper(  Public_health_case.CURR_PROCESS_STATE_CD)  is NULL or (Public_health_case.CURR_PROCESS_STATE_CD = ''))");
						else
						strbResult.append( "OR  (upper(  Public_health_case.CURR_PROCESS_STATE_CD) " + oper + "   '"
								+ currentProcessStatusValue.toUpperCase() + "')");
					}
					
				}
				
				if(currentProcessStatusArray.length>0)
					strbResult.append(")");
				
			}
			
			
			
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}

	private String buildNotificationIDSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			//Condition
			if ((find.getActId() != null)
					&& (find.getActId() .trim().length() != 0)) {
				//oper = "=";
				oper=find.getDocOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String notif = find.getActId().trim();
				String specialCharacter;
	
					
					if (notif.indexOf("'") > 0) {
						specialCharacter = "'";
						notif = replaceCharacters(notif,
								specialCharacter, "''");
					}
		
					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
						strbResult.append( "(upper( Notification.local_id) " + oper + "   '"
							+ notif.toUpperCase() + "')");
					else//Contains
						strbResult.append( "(upper( Notification.local_id) like '%"
								+ notif.toUpperCase() + "%')");

			}
			
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildDateReportSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			//Condition
			if ((find.getDateFrom() != null)
					&& (find.getDateFrom() .trim().length() != 0)) {
				//oper = "=";
				oper=find.getDateOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String dateFrom = find.getDateFrom().trim();
				String dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
				
				String specialCharacter;
	
					
				if (dateFrom.indexOf("'") > 0) {
					specialCharacter = "'";
					dateFrom = replaceCharacters(dateFrom,
							specialCharacter, "''");
				}
				if (dateTo!=null && dateTo.indexOf("'") > 0) {
					specialCharacter = "'";
					dateTo = replaceCharacters(dateTo,
							specialCharacter, "''");
				}
		

				if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
						strbResult.append( "(format(public_health_case.RPT_FORM_CMPLT_TIME,'MM/dd/yyyy','en-US' )  " + oper + "   '"
							+ dateFrom.toUpperCase() + "')");
					else//Between
						strbResult.append( "( CAST(public_health_case.RPT_FORM_CMPLT_TIME as DATE ) between '"
								+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");
			}
			
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildInvestigationClosedDateSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			//Condition
			if ((find.getDateFrom() != null)
					&& (find.getDateFrom() .trim().length() != 0)) {
				//oper = "=";
				oper=find.getDateOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String dateFrom = find.getDateFrom().trim();
				String dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
				
				String specialCharacter;
	
					
				if (dateFrom.indexOf("'") > 0) {
					specialCharacter = "'";
					dateFrom = replaceCharacters(dateFrom,
							specialCharacter, "''");
				}
				if (dateTo!=null && dateTo.indexOf("'") > 0) {
					specialCharacter = "'";
					dateTo = replaceCharacters(dateTo,
							specialCharacter, "''");
				}
		
				
					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
						strbResult.append( "(format(public_health_case.ACTIVITY_TO_TIME,'MM/dd/yyyy','en-US' )  " + oper + "   '"
							+ dateFrom.toUpperCase() + "')");
					else//Between
						strbResult.append( "( CAST(public_health_case.ACTIVITY_TO_TIME as DATE ) between '"
								+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");
			}
			
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildInvestigationCreateDateSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			//Investigation Create Date
			if ((find.getDateFrom() != null)
					&& (find.getDateFrom() .trim().length() != 0)) {
				//oper = "=";
				oper=find.getDateOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String dateFrom = find.getDateFrom().trim();
				String dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
				
				String specialCharacter;
	
					
				if (dateFrom.indexOf("'") > 0) {
					specialCharacter = "'";
					dateFrom = replaceCharacters(dateFrom,
							specialCharacter, "''");
				}
				if (dateTo!=null && dateTo.indexOf("'") > 0) {
					specialCharacter = "'";
					dateTo = replaceCharacters(dateTo,
							specialCharacter, "''");
				}
		
				
				if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
					strbResult.append( "(format(public_health_case.ADD_TIME,'MM/dd/yyyy','en-US' )  " + oper + "   '"
						+ dateFrom.toUpperCase() + "')");
				else//Between
					strbResult.append( "( CAST(public_health_case.ADD_TIME as DATE ) between '"
							+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");

			}
			
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildInvestigationLastUpdateDateSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			//Condition
			if ((find.getDateFrom() != null)
					&& (find.getDateFrom() .trim().length() != 0)) {
				//oper = "=";
				oper=find.getDateOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String dateFrom = find.getDateFrom().trim();
				String dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
				
				String specialCharacter;
	
					
				if (dateFrom.indexOf("'") > 0) {
					specialCharacter = "'";
					dateFrom = replaceCharacters(dateFrom,
							specialCharacter, "''");
				}
				if (dateTo!=null && dateTo.indexOf("'") > 0) {
					specialCharacter = "'";
					dateTo = replaceCharacters(dateTo,
							specialCharacter, "''");
				}
		
				
				
					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
						strbResult.append( "(format(public_health_case.LAST_CHG_TIME,'MM/dd/yyyy','en-US' )  " + oper + "   '"
							+ dateFrom.toUpperCase() + "')");
					else//Between
						strbResult.append( "( CAST(public_health_case.LAST_CHG_TIME as DATE ) between '"
								+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");
			}
			
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	

	private String buildInvestigationStartDateSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			//Condition
			if ((find.getDateFrom() != null)
					&& (find.getDateFrom() .trim().length() != 0)) {
				//oper = "=";
				oper=find.getDateOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String dateFrom = find.getDateFrom().trim();
				String dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
				
				String specialCharacter;
	
					
				if (dateFrom.indexOf("'") > 0) {
					specialCharacter = "'";
					dateFrom = replaceCharacters(dateFrom,
							specialCharacter, "''");
				}
				if (dateTo!=null && dateTo.indexOf("'") > 0) {
					specialCharacter = "'";
					dateTo = replaceCharacters(dateTo,
							specialCharacter, "''");
				}
		
				
					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
						strbResult.append( "(format(public_health_case.activity_from_time,'MM/dd/yyyy','en-US' )  " + oper + "   '"
							+ dateFrom.toUpperCase() + "')");
					else//Between
						strbResult.append( "( CAST(public_health_case.activity_from_time as DATE ) between '"
								+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");
			}
			
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildNotificationCreateDateSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			//Condition
			if ((find.getDateFrom() != null)
					&& (find.getDateFrom() .trim().length() != 0)) {
				//oper = "=";
				oper=find.getDateOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String dateFrom = find.getDateFrom().trim();
				String dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
				
				String specialCharacter;
	
					
				if (dateFrom.indexOf("'") > 0) {
					specialCharacter = "'";
					dateFrom = replaceCharacters(dateFrom,
							specialCharacter, "''");
				}
				if (dateTo!=null && dateTo.indexOf("'") > 0) {
					specialCharacter = "'";
					dateTo = replaceCharacters(dateTo,
							specialCharacter, "''");
				}
		
				strbResult.append("Notification.local_id in (select local_id from Notification where ");

				
					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
						strbResult.append( "(format(add_time,'MM/dd/yyyy','en-US' )  " + oper + "   '"
							+ dateFrom.toUpperCase() + "')");
					else//Between
						strbResult.append( "( CAST(add_time as DATE ) between '"
								+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");
			}
			strbResult.append(")");
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildEventCreatedUpdatedSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			
			//Document Created by
			if ((find.getDocumentCreateSelected() != null)
					&& (find.getDocumentCreateSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String documentCreate = find.getDocumentCreateSelected().trim();
				String specialCharacter;
	
				if (documentCreate.indexOf("'") > 0) {
					specialCharacter = "'";
					documentCreate = replaceCharacters(documentCreate,
							specialCharacter, "''");
				}
					strbResult.append(and + " (upper( Public_health_case.add_user_id  ) in (select nedss_entry_id from auth_user where upper(user_id)" + oper
							+ "   '" + documentCreate.toUpperCase() + "'))");
				
			}
			
			//Document Last Updated by
			if ((find.getDocumentUpdateSelected() != null)
					&& (find.getDocumentUpdateSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String documentUpdate = find.getDocumentUpdateSelected().trim();
				String specialCharacter;
	
				if (documentUpdate.indexOf("'") > 0) {
					specialCharacter = "'";
					documentUpdate = replaceCharacters(documentUpdate,
							specialCharacter, "''");
				}
					strbResult.append(and + " (upper( Public_health_case.last_chg_user_id  ) in (select nedss_entry_id from auth_user where upper(user_id)" + oper
							+ "   '" + documentUpdate.toUpperCase() + "'))");
				
			}
				
			
			
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildReportingFacilitySearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			
			//Reporting Facility
			if ((find.getReportingFacilitySelected() != null)
					&& (find.getReportingFacilitySelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String reportingFacility = find.getReportingFacilitySelected().trim();
				String specialCharacter;
	
				if (reportingFacility.indexOf("'") > 0) {
					specialCharacter = "'";
					reportingFacility = replaceCharacters(reportingFacility,
							specialCharacter, "''");
				}
					strbResult.append(and + " public_health_case.public_health_case_uid in"
							+ "(select participation.act_uid from participation where participation.type_cd='OrgAsReporterOfPHC' and participation.subject_entity_uid" + oper
							+ "   '" + reportingFacility.toUpperCase()+"')");// + "' and person.person_uid = participation.subject_entity_uid)");

			}

			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	/**
	 * buildReportingProviderSearchWhereClause:
	 * @param find
	 * @return
	 */
	
	private String buildReportingProviderSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			
			//Reporting provider
			if ((find.getReportingProviderSelected() != null)
					&& (find.getReportingProviderSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String reportingFacility = find.getReportingProviderSelected().trim();
				String specialCharacter;
	
				if (reportingFacility.indexOf("'") > 0) {
					specialCharacter = "'";
					reportingFacility = replaceCharacters(reportingFacility,
							specialCharacter, "''");
				}
					strbResult.append(and + " public_health_case.public_health_case_uid in"
							+ "(select participation.act_uid from participation where participation.type_cd='PerAsReporterOfPHC' and participation.subject_entity_uid" + oper
							+ "   '" + reportingFacility.toUpperCase() + "' and person.person_uid = participation.subject_entity_uid)");
			}

			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	/**
	 * buildABCCaseSearchWhereClause:
	 * @param find
	 * @return
	 */
	
	private String buildABCCaseSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			
			//Reporting provider
			if ((find.getActType() != null)
					&& (find.getActType().trim().length() != 0)) {
				oper = find.getDocOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String abcCase = find.getActId().trim();
				String specialCharacter;
	
				if (abcCase.indexOf("'") > 0) {
					specialCharacter = "'";
					abcCase = replaceCharacters(abcCase,
							specialCharacter, "''");
				}
				
				if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
					strbResult.append(and + " upper(public_health_case.public_health_case_uid) in (select upper(act_uid) from act_id where act_id_seq ='2'"
							+ " and root_extension_txt "+oper
							+ "   '" + abcCase.toUpperCase() + "')");
				else//contains
					strbResult.append(and + " upper(public_health_case.public_health_case_uid) in (select upper(act_uid) from act_id where act_id_seq ='2'"
							+ " and root_extension_txt like '%" + abcCase.toUpperCase() + "%')");
					
			}

			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	/**
	 * buildCityCaseSearchWhereClause:
	 * @param find
	 * @return
	 */
	
	private String buildCityCaseSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			
			//Reporting provider
			if ((find.getActType() != null)
					&& (find.getActType().trim().length() != 0)) {
				oper = find.getDocOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String abcCase = find.getActId().trim();
				String specialCharacter;
	
				if (abcCase.indexOf("'") > 0) {
					specialCharacter = "'";
					abcCase = replaceCharacters(abcCase,
							specialCharacter, "''");
				}
				if(oper.equalsIgnoreCase("=") ||oper.equalsIgnoreCase("!="))
					strbResult.append(and + " upper(public_health_case.public_health_case_uid) in (select upper(act_uid) from act_id where act_id_seq ='2'"
							+ " and type_cd='CITY'"
							+ " and root_extension_txt "+oper
							+ "   '" + abcCase.toUpperCase() + "')");
				else
					strbResult.append(and + " upper(public_health_case.public_health_case_uid) in (select upper(act_uid) from act_id where act_id_seq ='2'"
							+ " and type_cd='CITY'"
							+ " and root_extension_txt like '%" + abcCase.toUpperCase() + "%')");
			}

			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	/**
	 * buildStateCaseSearchWhereClause:
	 * @param find
	 * @return
	 */
	
	private String buildStateCaseSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			
			//Reporting provider
			if ((find.getActType() != null)
					&& (find.getActType().trim().length() != 0)) {
				oper = find.getDocOperator();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String abcCase = find.getActId().trim();
				String specialCharacter;
	
				if (abcCase.indexOf("'") > 0) {
					specialCharacter = "'";
					abcCase = replaceCharacters(abcCase,
							specialCharacter, "''");
				}
				
				if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
					strbResult.append(and + " upper(public_health_case.public_health_case_uid) in (select upper(act_uid) from act_id where act_id_seq ='1'"
							+ " and type_cd='STATE'"
							+ " and root_extension_txt "+oper
							+ "   '" + abcCase.toUpperCase() + "')");
				else
					strbResult.append(and + " upper(public_health_case.public_health_case_uid) in (select upper(act_uid) from act_id where act_id_seq ='1'"
							+ " and type_cd='STATE'"
							+ " and root_extension_txt like '%" + abcCase.toUpperCase() + "%')");
			}

			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	/**
	 * This method wraps nestedQuery into a query, which will retrieve all MPR
	 * and revision person_uid, which correpond to the collection of
	 * person_parent_uid from the nestedQuery. This is needed so that on every
	 * step of the search query, we have an updated list of all person_uid
	 * necessary for the next query (this includes MPRs and revisions).
	 *
	 * @param nestedQuery
	 *            Represents the query defining derived table. Has to contains
	 *            the following column names in SELECT clause:
	 *            "personParentUid", "parentLastName", "parentFirstName",
	 *            "parentBrithTimeCalc"
	 * @param nqDerivedTableName
	 *            Represents a table name for the derived table defined by the
	 *            nestedQuery.
	 * @return SQL select query, which retrieves all MPR and revision person_uid
	 *         that correspond to the person_parent_uid defined by the
	 *         nestedQuery.
	 */

	
	private String wrapNestedQueryInvestigation(String nestedQuery, String nqDerivedTableName) {
		StringBuffer query = new StringBuffer("");
		query.append(" select distinct ");
		query.append(nqDerivedTableName);
		query.append(".\"investigatorLastName\", ");
		query.append(nqDerivedTableName);
		query.append(".\"investigatorFirstName\", ");
		query.append(nqDerivedTableName);
		query.append(".\"investigatorUid\", ");
		query.append(nqDerivedTableName);
		query.append(".\"investigatorLocalId\", ");
		query.append(nqDerivedTableName);
		query.append(".\"investigationStatusCd\", ");
		query.append(nqDerivedTableName);
		query.append(".\"investigationStatusDescTxt\", ");
        query.append(nqDerivedTableName);
		query.append(".\"localId\", ");
		query.append(nqDerivedTableName);
		query.append(".\"activityFromTime\", ");
		query.append(nqDerivedTableName);
		query.append(".\"cd\", ");
		query.append(nqDerivedTableName);
		query.append(".\"conditionCodeText\", ");
		query.append(nqDerivedTableName);
		query.append(".\"caseClassCd\", ");
		query.append(nqDerivedTableName);
		query.append(".\"addUserId\", ");
		query.append(nqDerivedTableName);
		query.append(".\"lastChgUserId\", ");
		query.append(nqDerivedTableName);
		query.append(".\"caseClassCodeTxt\", ");
        query.append(nqDerivedTableName);
		query.append(".\"jurisdictionCd\", ");
		query.append(nqDerivedTableName);
		query.append(".\"patientLastName\", ");
		query.append(nqDerivedTableName);
		query.append(".\"patientFirstName\", ");
		query.append(nqDerivedTableName);
		query.append(".\"MPRUid\", ");
		query.append(nqDerivedTableName);
		query.append(".\"currentSex\", ");
		query.append(nqDerivedTableName);
		query.append(".\"birthTime\", ");
		query.append(nqDerivedTableName);
		query.append(".\"personLocalID\", ");
		query.append(nqDerivedTableName);
		query.append(".\"jurisdictionDescTxt\", ");
        query.append(nqDerivedTableName);
		query.append(".\"publicHealthCaseUid\", ");
		query.append(nqDerivedTableName);
		query.append(".\"statusCd\", ");
		query.append(nqDerivedTableName);
		query.append(".\"recordStatusCd\", ");
		query.append(nqDerivedTableName);
		query.append(".\"notifRecordStatusCd\", ");
		query.append(nqDerivedTableName);
		query.append(".\"notifLocalId\" ");
		
	
	query.append(" from public_health_case phc with (nolock), ");
	query.append(" ( ");
	query.append(nestedQuery);
	query.append(" ) ");
	query.append(nqDerivedTableName);
	query.append(" where ");
	query.append(nqDerivedTableName);
	query.append(".\"publicHealthCaseUid\" = phc.public_health_case_uid ");
	
	
	return query.toString();
}


	/**
	 * This method buils first subquery for patient search. All first subqueries
	 * are joined with PERSON table unless the first subquery is against PERSON
	 * table itself.
	 *
	 * @param searchDataType
	 *            Indicates the type of data specified by the search criteria.
	 * @param find
	 *            Represents all search criteria specified by the user.
	 * @return Returns the first subquery using the first set of search
	 *         criteria.
	 */


	private String buildFirstNestedQueryInvestigation(int searchDataType,
			PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		String result = "";
		try{
			switch (searchDataType) {
			case DT_INVESTIGATION_CRITERIA:
				result = buildInvestigationCriteriaFirstQueryInvestigation(find, nbsSecurityObj);
				break;
			case DT_EVENT_CREATED_UPDATED_BY_USER:
				result = buildCreatedUpdatedUserFirstQueryInvestigation(find, nbsSecurityObj);
				break;
			case DT_REPORTING_FACILITY:
				result = buildReportingFacilityFirstQueryInvestigation(find, nbsSecurityObj);
				break;
			case DT_REPORTING_PROVIDER:
				result = buildReportingProviderFirstQueryInvestigation(find, nbsSecurityObj);
				break;
			case DT_ABC_CASE_ID:
				result =  buildABCCaseQueryInvestigation(find, nbsSecurityObj);
				break;
			case DT_STATE_CASE_ID:
				result =  buildStateCaseQueryInvestigation(find, nbsSecurityObj);
				break;
			case DT_CITY_COUNTY_CASE_ID:
				result =  buildCityCaseQueryInvestigation(find, nbsSecurityObj);
				break;
			case DT_NOTIFICATION:
				result = buildNotificationIDFirstQueryInvestigation(find, nbsSecurityObj);
				break;	
			case DT_DATE_REPORT:
				result = buildDateReportFirstQueryInvestigation(find, nbsSecurityObj);
				break;	
			case DT_INVESTIGATION_CLOSED_DATE:
				result = buildInvestigationClosedDateFirstQueryInvestigation(find, nbsSecurityObj);
				break;	
			case DT_INVESTIGATION_CREATE_DATE:
				result = buildInvestigationCreateDateFirstQueryInvestigation(find, nbsSecurityObj);
				break;	
			case DT_INVESTIGATION_LAST_UPDATE_DATE:
				result = buildInvestigationlastUpdateDateFirstQueryInvestigation(find, nbsSecurityObj);
				break;	
			case DT_INVESTIGATION_START_DATE:
				result = buildInvestigationStartDateFirstQueryInvestigation(find, nbsSecurityObj);
				break;	
			case DT_NOTIFICATION_CREATE_DATE:
				result = buildNotificationCreateDateFirstQueryInvestigation(find, nbsSecurityObj);
				break;

			default:
				// raise exception
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}
	
	/**
	 * This method builds retrieval portion of the query using UNION between
	 * select statements against PERSON, PERSON_NAME, PERSON_RACE,
	 * POSTAL_LOCATOR, TELE_LOCATOR tables. Each SELECT statement is built using
	 * a join to the derived table defined by the subQuery. subQuery returns
	 * person_uids for all MPRs and revisions, for which data needs to be
	 * retrieved.
	 *
	 * @param subQuery
	 *            Contains person_uid in SELECT clause.
	 * @return UNION select query retrieving all VO data for all person_uids
	 *         specified in subQuery at once. Each select query contains
	 *         dataType fake column, which indicates the type of data in any
	 *         particular row.
	 */
	private String buildRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();

		query.append("( ");
		query.append(buildNameRetrievalQuery(subQuery));
		query.append(" ) UNION ( ");
		query.append(buildPersonRetrievalQuery(subQuery));
		query.append(" ) UNION ( ");
		query.append(buildIdsRetrievalQuery(subQuery));
		query.append(" ) UNION ( ");
		query.append(buildRaceRetrievalQuery(subQuery));
		query.append(" ) UNION ( ");
		query.append(buildAddressRetrievalQuery(subQuery));
		query.append(" ) UNION ( ");
		query.append(buildTeleRetrievalQuery(subQuery));
		query.append(" ) ");
		return query.toString();
	}
	
	private String buildNameRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();

		query.append("select distinct ");

		query.append("'NAME' \"dataType\", ");
		query.append("pn.person_uid \"personUid\", ");
		query.append("sq.\"personParentUid\", ");
		query.append("pn.last_nm \"lastName\", ");
		query.append("pn.middle_nm \"nmMiddle\", ");
		query.append("pn.first_nm \"firstName\", ");
		query.append("pn.record_status_cd \"recordStatusCd\", ");
		query.append("pn.nm_use_cd \"nameUseCd\", ");
		query.append("pn.nm_suffix \"nmSuffix\", ");
		query.append("pn.nm_degree \"nmDegree\", ");
		query.append("null \"localId\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"asOfDateAdmin\", ");
		query.append("null \"ageReported\", ");
		query.append("CONVERT(integer,0) \"versionCtrlNbr\", ");

		query.append("null \"ageUnit\", ");
		query.append("null \"ssn\", ");
		query.append("null \"maritalStatusCd\", ");
		query.append("null \"ethnicGroupInd\", ");
		query.append("null \"dob\", ");
		query.append("null \"currentSex\", ");
		query.append("CONVERT(datetime,'') \"deceasedTime\", ");
		
		query.append("null \"eiRootExtensionTxt\",");
		query.append("null \"eiTypeDesc\", ");
		query.append("null \"raceCd\", ");
		query.append("null \"raceDescTxt\", ");
		query.append("null \"classCd\", ");
		query.append("null \"locatorTypeCdDesc\", ");
		query.append("null \"locatorUseCd\", ");
		query.append("null \"locatorCd\", ");
		query.append("null \"streetAddr1\", ");
		query.append("null \"streetAddr2\", ");
		query.append("null \"city\", ");
		query.append("null \"zip\", ");
		query.append("null \"cntyCd\", ");
		query.append("null \"state\", ");
		query.append("null \"cntryCd\", ");
		query.append("CONVERT(integer,null) \"locatorUid\", ");
		
		query.append("null \"telephoneNbr\", ");
		query.append("null \"extensionTxt\", ");
		query.append("null \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from person_name pn, ");
		query.append("( ");
		query.append(subQuery);
		query.append(" ) sq ");
		query.append("where pn.person_uid = sq.\"personUid\" ");
		query.append("and pn.record_status_cd='ACTIVE' ");

		return query.toString();
	}

	private String buildPersonRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();
		
		query.append("select distinct ");

		query.append("'PERSON' \"dataType\", ");
		query.append("p.person_uid \"personUid\", ");
		query.append("p.person_parent_uid \"personParentUid\", ");
		query.append("null \"lastName\", ");
		query.append("null \"nmMiddle\", ");
		query.append("null \"firstName\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"nameUseCd\", ");
		query.append("null \"nmSuffix\", ");
		query.append("null \"nmDegree\", ");
		query.append("p.local_id \"localId\", ");
		query.append("p.record_status_cd \"recordStatusCd\", ");
		query.append("CONVERT(char, p.as_of_date_admin) \"asOfDateAdmin\" , ");
		query.append("p.age_reported \"ageReported\", ");
		query.append("p.version_ctrl_nbr \"versionCtrlNbr\", ");
		query.append("p.age_reported_unit_cd \"ageUnit\", ");
		query.append("p.ssn \"ssn\", ");
		query.append("p.marital_status_cd \"maritalStatusCd\", ");
		query.append("p.ethnic_group_ind \"ethnicGroupInd\", ");
		query.append("CONVERT(varchar, p.birth_time, 101)  \"dob\", ");
		query.append("p.curr_sex_cd \"currentSex\", ");
		query.append("p.deceased_time \"deceasedTime\", ");
		query.append("null \"eiRootExtensionTxt\",");
		query.append("null \"eiTypeDesc\", ");
		query.append("null \"raceCd\", ");
		query.append("null \"raceDescTxt\", ");
		query.append("null \"classCd\", ");
		query.append("null \"locatorTypeCdDesc\", ");
		query.append("null \"locatorUseCd\", ");
		query.append("null \"locatorCd\", ");
		query.append("null \"streetAddr1\", ");
		query.append("null \"streetAddr2\", ");
		query.append("null \"city\", ");
		query.append("null \"zip\", ");
		query.append("null \"cntyCd\", ");
		query.append("null \"state\", ");
		query.append("null \"cntryCd\", ");
		query.append("CONVERT(integer,null) \"locatorUid\", ");
		query.append("null \"telephoneNbr\", ");
		query.append("null \"extensionTxt\", ");
		query.append("null \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from person p, ");
		query.append("( ");
		query.append(subQuery);
		query.append(" ) sq ");
		query.append("where p.person_uid = sq.\"personUid\" ");

		return query.toString();
	}

	private String buildIdsRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct ");

		query.append("'ID' \"dataType\", ");
		query.append("ei.entity_uid \"personUid\", ");
		query.append("sq.\"personParentUid\", ");
		query.append("null \"lastName\", ");
		query.append("null \"nmMiddle\", ");
		query.append("null \"firstName\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"nameUseCd\", ");
		query.append("null \"nmSuffix\", ");
		query.append("null \"nmDegree\", ");
		query.append("null \"localId\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"asOfDateAdmin\", ");
		query.append("null \"ageReported\", ");
		query.append("CONVERT(integer,0) \"versionCtrlNbr\", ");
		query.append("null \"ageUnit\", ");
		query.append("null \"ssn\", ");
		query.append("null \"maritalStatusCd\", ");
		query.append("null \"ethnicGroupInd\", ");
		query.append("null \"dob\", ");
		query.append("null \"currentSex\", ");
		query.append("CONVERT(datetime,'') \"deceasedTime\", ");
		query.append("ei.root_extension_txt \"eiRootExtensionTxt\",");
		query.append("ei.type_cd \"eiTypeDesc\", ");
		query.append("null \"raceCd\", ");
		query.append("null \"raceDescTxt\", ");
		query.append("null \"classCd\", ");
		query.append("null \"locatorTypeCdDesc\", ");
		query.append("null \"locatorUseCd\", ");
		query.append("null \"locatorCd\", ");
		query.append("null \"streetAddr1\", ");
		query.append("null \"streetAddr2\", ");
		query.append("null \"city\", ");
		query.append("null \"zip\", ");
		query.append("null \"cntyCd\", ");
		query.append("null \"state\", ");
		query.append("null \"cntryCd\", ");
		query.append("CONVERT(integer,null) \"locatorUid\", ");
		query.append("null \"telephoneNbr\", ");
		query.append("null \"extensionTxt\", ");
		query.append("null \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from entity_id ei, ");
		query.append("( ");
		query.append(subQuery);
		query.append(" ) sq ");
		query.append("where ei.entity_uid = sq.\"personUid\" ");
		query.append("and ei.status_cd='A' ");

		return query.toString();
	}

	private String buildRaceRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct ");

		query.append("'RACE' \"dataType\", ");
		query.append("pr.person_uid \"personUid\", ");
		query.append("sq.\"personParentUid\", ");
		query.append("null \"lastName\", ");
		query.append("null \"nmMiddle\", ");
		query.append("null \"firstName\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"nameUseCd\", ");
		query.append("null \"nmSuffix\", ");
		query.append("null \"nmDegree\", ");
		query.append("null \"localId\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"asOfDateAdmin\", ");
		query.append("null \"ageReported\", ");
		query.append("CONVERT(integer,0) \"versionCtrlNbr\", ");
		query.append("null \"ageUnit\", ");
		query.append("null \"ssn\", ");
		query.append("null \"maritalStatusCd\", ");
		query.append("null \"ethnicGroupInd\", ");
		query.append("null \"dob\", ");
		query.append("null \"currentSex\", ");
		query.append("CONVERT(datetime,'') \"deceasedTime\", ");
		query.append("null \"eiRootExtensionTxt\",");
		query.append("null \"eiTypeDesc\", ");
		query.append("pr.race_cd \"raceCd\", ");
		query.append("pr.race_desc_txt \"raceDescTxt\", ");
		query.append("null \"classCd\", ");
		query.append("null \"locatorTypeCdDesc\", ");
		query.append("null \"locatorUseCd\", ");
		query.append("null \"locatorCd\", ");
		query.append("null \"streetAddr1\", ");
		query.append("null \"streetAddr2\", ");
		query.append("null \"city\", ");
		query.append("null \"zip\", ");
		query.append("null \"cntyCd\", ");
		query.append("null \"state\", ");
		query.append("null \"cntryCd\", ");
		query.append("CONVERT(integer,null) \"locatorUid\", ");
		query.append("null \"telephoneNbr\", ");
		query.append("null \"extensionTxt\", ");
		query.append("null \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from person_race pr, ");
		query.append("( ");
		query.append(subQuery);
		query.append(" ) sq ");
		query.append("where pr.person_uid = sq.\"personUid\" ");
		query.append("and pr.record_status_cd='ACTIVE' ");
		return query.toString();
	}

	private String buildAddressRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();

				query.append("select distinct ");

		query.append("'ADDRESS' \"dataType\", ");
		query.append("elp.entity_uid \"personUid\", ");
		query.append("sq.\"personParentUid\", ");
		query.append("null \"lastName\", ");
		query.append("null \"nmMiddle\", ");
		query.append("null \"firstName\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"nameUseCd\", ");
		query.append("null \"nmSuffix\", ");
		query.append("null \"nmDegree\", ");
		query.append("null \"localId\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"asOfDateAdmin\", ");
		query.append("null \"ageReported\", ");
		query.append("to_number(0) \"versionCtrlNbr\", ");
		query.append("CONVERT(integer,0) \"versionCtrlNbr\", ");
		query.append("null \"ageUnit\", ");
		query.append("null \"ssn\", ");
		query.append("null \"maritalStatusCd\", ");
		query.append("null \"ethnicGroupInd\", ");
		query.append("null \"dob\", ");
		query.append("null \"currentSex\", ");
		query.append("CONVERT(datetime,'') \"deceasedTime\", ");
		query.append("null \"eiRootExtensionTxt\",");
		query.append("null \"eiTypeDesc\", ");
		query.append("null \"raceCd\", ");
		query.append("null \"raceDescTxt\", ");
		query.append("elp.class_Cd \"classCd\", ");
		query.append("elp.cd \"locatorTypeCdDesc\", ");
		query.append("elp.use_cd \"locatorUseCd\", ");
		query.append("elp.cd \"locatorCd\", ");
		query.append("pl.street_addr1 \"streetAddr1\", ");
		query.append("pl.street_addr2 \"streetAddr2\", ");
		query.append("pl.city_desc_txt \"city\", ");
		query.append("pl.zip_cd \"zip\", ");
		query.append("pl.cnty_cd \"cntyCd\", ");
		query.append("pl.state_cd \"state\", ");
		query.append("pl.cntry_cd \"cntryCd\", ");
		query.append("CONVERT(integer,null) \"locatorUid\", ");
		query.append("null \"telephoneNbr\", ");
		query.append("null \"extensionTxt\", ");
		query.append("null \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from entity_locator_participation elp, postal_locator pl, ");
		query.append("( ");
		query.append(subQuery);
		query.append(" ) sq ");

		query.append("where elp.entity_uid = sq.\"personUid\" ");
		query.append("and elp.locator_uid = pl.postal_locator_uid  ");
		query.append("and elp.class_cd = 'PST' and elp.status_cd = 'A' ");
		

		return query.toString();
	}

	private String buildTeleRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();
		
		query.append("select distinct ");

		query.append("'TELE' \"dataType\", ");
		query.append("elp.entity_uid \"personUid\", ");
		query.append("sq.\"personParentUid\", ");
		query.append("null \"lastName\", ");
		query.append("null \"nmMiddle\", ");
		query.append("null \"firstName\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"nameUseCd\", ");
		query.append("null \"nmSuffix\", ");
		query.append("null \"nmDegree\", ");
		query.append("null \"localId\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"asOfDateAdmin\", ");
		query.append("null \"ageReported\", ");
		query.append("CONVERT(integer,0) \"versionCtrlNbr\", ");
		
		query.append("null \"ageUnit\", ");
		query.append("null \"ssn\", ");
		query.append("null \"maritalStatusCd\", ");
		query.append("null \"ethnicGroupInd\", ");
		query.append("null \"dob\", ");
		query.append("null \"currentSex\", ");
		query.append("CONVERT(datetime,'') \"deceasedTime\", ");
		
		query.append("null \"eiRootExtensionTxt\",");
		query.append("null \"eiTypeDesc\", ");
		query.append("null \"raceCd\", ");
		query.append("null \"raceDescTxt\", ");
		query.append("elp.class_cd \"classCd\", ");
		query.append("null \"locatorTypeCdDesc\", ");
		query.append("elp.use_cd \"locatorUseCd\", ");
		query.append("elp.cd \"locatorCd\", ");
		query.append("null \"streetAddr1\", ");
		query.append("null \"streetAddr2\", ");
		query.append("null \"city\", ");
		query.append("null \"zip\", ");
		query.append("null \"cntyCd\", ");
		query.append("null \"state\", ");
		query.append("null \"cntryCd\", ");
		query.append("tl.tele_locator_uid \"locatorUid\", ");
		query.append("tl.phone_nbr_txt \"telephoneNbr\", ");
		query.append("tl.extension_txt \"extensionTxt\", ");
		query.append("tl.email_address \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from entity_locator_participation elp, tele_locator tl, ");
		query.append("( ");
		query.append(subQuery);
		query.append(" ) sq ");

		query.append("where elp.entity_uid = sq.\"personUid\" ");
		query.append("and elp.locator_uid = tl.tele_locator_uid  ");
		query.append("and elp.class_cd = 'TELE' and elp.status_cd = 'A' ");

		return query.toString();
	}

	@SuppressWarnings("unlikely-arg-type")
	public ArrayList<Object> assembleInvestigationData(ArrayList<?> allRetrievedData, String finalQuery) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:assemblePersonData(ArrayList<Object> ) : START");

		ArrayList<Object> result = new ArrayList<Object>();
		InvestigationPersonInfoVO iPersonInfo=null;
		HashMap<Long, Object> personListMap=null;
		try{
			if(null != finalQuery && !finalQuery.contains("person1.curr_sex_cd")) { //old investigation custom queues
		        personListMap = (HashMap<Long, Object>) getPersonInfoForExistingQueues();
		}

			if (allRetrievedData != null) {
				int size = allRetrievedData.size();

	
				PersonSearchResultTmp curInvestigation = null;

				PatientSrchResultVO investigation;
				CachedDropDownValues cache = new CachedDropDownValues();
				
				for (int i = 0; i < size; i++) {
					curInvestigation = (PersonSearchResultTmp) allRetrievedData.get(i);
					investigation=new PatientSrchResultVO();
					
					investigation.setCondition(curInvestigation.getConditionCodeText());
					investigation.setNotification(curInvestigation.getNotifRecordStatusCd());
					
					if(curInvestigation.getCaseClassCodeTxt()==null){ //Oracle
						String caseClassDescription ="";

						caseClassDescription = cache.getDescForCode("PHC_CLASS", curInvestigation.getCaseClassCd());
						investigation.setCaseStatus(caseClassDescription);
					}
					else //SQL
						investigation.setCaseStatus(curInvestigation.getCaseClassCodeTxt());
					investigation.setPersonUID(curInvestigation.getMPRUid());
					
					if(curInvestigation.getJurisdictionDescTxt()==null){//Oracle
						String jurisdiction ="";
						jurisdiction = cache.getJurisdictionDesc(curInvestigation.getJurisdictionCd());
						investigation.setJurisdiction(jurisdiction);
					}
					else //SQL
						investigation.setJurisdiction(curInvestigation.getJurisdictionDescTxt());
					
					investigation.setInvestigatorFirstName(curInvestigation.getInvestigatorFirstName());
					investigation.setInvestigatorLastName(curInvestigation.getInvestigatorLastName());
					investigation.setStartDate(curInvestigation.getActivityFromTime());
					investigation.setPersonFirstName(curInvestigation.getPatientFirstName());
					investigation.setPersonLastName(curInvestigation.getPatientLastName());
					investigation.setPublicHealthCaseUid(curInvestigation.getPublicHealthCaseUid()+"");
					//investigation.setCaseStatus(curInvestigation.getCaseClassCodeTxt());
					investigation.setPersonUID(curInvestigation.getMPRUid());
					investigation.setCd(curInvestigation.getCd());
					investigation.setCaseStatusCd(curInvestigation.getCaseClassCd());
					investigation.setJurisdictionCd(curInvestigation.getJurisdictionCd());
					//investigation.setJurisdiction(curInvestigation.getJurisdictionDescTxt());
					investigation.setNotificationCd(curInvestigation.getNotifRecordStatusCd());
					investigation.setLocalId(curInvestigation.getLocalId());
					if(  null != personListMap &&
							(null == curInvestigation.getCurrentSex() || "".equals(curInvestigation.getCurrentSex()))  
						&&  (null == curInvestigation.getBirthTime() || "".equals(curInvestigation.getBirthTime()))
						&&  (null == curInvestigation.getPersonLocalID() ||"".equals(curInvestigation.getPersonLocalID()))
						) {
							Collection<Object> personList = personListMap.values();
							if(personList != null) {
								Iterator<Object> it = personList.iterator();
								while(it.hasNext()) {
									iPersonInfo=(InvestigationPersonInfoVO)it.next();
									 if (iPersonInfo.getPublicHealthCaseUid().compareTo(curInvestigation.getPublicHealthCaseUid()) == 0) {
										 	investigation.setCurrentSex(iPersonInfo.getCurrSexCd());
										 	investigation.setPersonLocalID(iPersonInfo.getPersonLocalId());
										 	if(iPersonInfo.getBirthTime() !=null)	investigation.setPersonDOB(StringUtils.formatDate(iPersonInfo.getBirthTime()));
								      }
							     }
							}
					} else {
						investigation.setCurrentSex(curInvestigation.getCurrentSex());
						investigation.setPersonLocalID(curInvestigation.getPersonLocalID());
						if(curInvestigation.getBirthTime() !=null)	investigation.setPersonDOB(StringUtils.formatDate(curInvestigation.getBirthTime()));
					}
					
					result.add(investigation);
					
		
				}
				
			}
				}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	private Map<Long, Object> getPersonInfoForExistingQueues(){
	  HashMap<Long, Object> investigationPersonInfoVOMap = new HashMap<Long, Object>();
	  try{
		
		  String inversigationPersonInfoQuery = 
			" SELECT  Public_health_case.Public_health_case_uid \"publicHealthCaseUid\", "
	        + " ISNULL(pnm.last_nm,'No Last') \"lastNm\","
	        + " ISNULL(pnm.first_nm,'No First') \"firstNm\","
	        + " p.local_id \"personLocalId\","
	        + " p.person_parent_uid \"personParentUid\"," 
	        + " p.curr_sex_cd \"currSexCd\", "
	        + " p.birth_time \"birthTime\" " 
	        + " FROM Person p Left outer join Person_name pnm on p.person_uid=pnm.person_uid, Public_health_case, participation"
	        + " WHERE ((Public_health_case.Public_health_case_uid = participation.act_uid) "
	        + " AND (participation.type_cd ='SubjOfPHC')  "
	        + " AND (p.person_uid = participation.subject_entity_uid) "
	        + " AND (pnm.nm_use_cd = 'L' or pnm.nm_use_cd IS NULL) "
	        +")"
	        + " order by Public_health_case.Public_health_case_uid";
		    logger.info("investgation Person Info for Review Query - "+inversigationPersonInfoQuery);
            InvestigationPersonInfoVO investigationPersonInfoVO = new InvestigationPersonInfoVO();
	        investigationPersonInfoVOMap =(HashMap)preparedStmtMethodForMap(investigationPersonInfoVO, null,
	            		inversigationPersonInfoQuery, NEDSSConstants.SELECT, "getPublicHealthCaseUid");
     
	  }
	  catch(Exception e)
	  {
		  logger.error("Error in fetching person Information data for Lab Report(Observations) for Review "); 
	      throw new NEDSSSystemException(e.toString());
	  }
	  return investigationPersonInfoVOMap;
	 }


}
