package gov.cdc.nedss.pagemanagement.wa.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.xml.util.JspBeautifier;
import gov.cdc.nedss.systemservice.dao.EDXActivityLogDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DsmLogSearchDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRConstants;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TemplatesDAOImpl extends DAOBase{
	
	private static final LogUtils logger = new LogUtils(TemplatesDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	
	private static final String SELECT_TEMPLATES = "SELECT tem.wa_template_uid \"waTemplateUid\", "
		+ "tem.template_type \"templateType\", "
		+ "tem.template_nm \"templateNm\", "
		+ "tem.publish_version_nbr \"publishVersionNbr\", "
		+ "tem.form_cd \"formCd\", "
		+ "tem.condition_cd \"conditionCd\", "
		+ "tem.bus_obj_type \"busObjType\", "
		+ "tem.datamart_nm \"dataMartNm\", "
		+ "tem.last_chg_user_id \"lastChgUserId\", "
		+ "tem.last_chg_time \"lastChgTime\", "
		+ "tem.record_status_cd \"recStatusCd\", "
		+ "tem.record_status_time \"recStatusTime\","
		+ "tem.desc_txt \"descTxt\","
		+ "tem.publish_ind_cd \"publishIndCd\","
		+ "tem.publish_version_nbr \"version\", "
		+ "tem.source_nm \"sourceNm\","
		+ "tem.add_time \"addTime\","
		+ "tem.add_user_id \"addUserId\" "
		+ "FROM WA_template tem WHERE tem.template_type='TEMPLATE'";
	
	private static final String SELECT_ACTIVITY_DETAIL_VOCAB_LOG = "SELECT edx.edx_activity_log_uid \"edxActivityLogUid\", "
		+ "edx.record_id \"recordId\", "
		+ "edx.record_type \"recordType\", "
		+ "edx.record_nm \"recordName\", "
		+ "edx.log_type \"logType\", "
		+ "edx.log_comment \"comment\" "		
		+ "FROM edx_activity_detail_log edx WHERE edx.edx_activity_log_uid=? and edx.log_type='Vocab'";
	
	private static final String SELECT_ACTIVITY_DETAIL_QUES_LOG = "SELECT edx.edx_activity_log_uid \"edxActivityLogUid\", "
		+ "edx.record_id \"recordId\", "
		+ "edx.record_type \"recordType\", "
		+ "edx.record_nm \"recordName\", "
		+ "edx.log_type \"logType\", "
		+ "edx.log_comment \"comment\" "		
		+ "FROM edx_activity_detail_log edx WHERE edx.edx_activity_log_uid=?  and edx.log_type='Question'";
	
	private static final String SELECT_ACTIVITY_LOG = "SELECT edx.edx_activity_log_uid \"edxActivityLogUid\", "
		+ "edx.record_status_time \"recordStatusTime\", "
		+ "edx.imp_exp_ind_cd \"impExpIndCd\", "
		+ "edx.doc_nm \"docName\", "
		+ "edx.source_nm \"srcName\", "
		+ "edx.record_status_cd \"recordStatusCd\", "	
		+ "edx.exception_txt \"exception\" "	
		+ "FROM edx_activity_log edx where edx.doc_type = 'Template'";
	
	private static final String SELECT_DSM_ACTIVITY_LOG = "SELECT edx.edx_activity_log_uid \"edxActivityLogUid\", "
			+ "edx.record_status_time \"recordStatusTime\", "
			+ "edx.imp_exp_ind_cd \"impExpIndCd\", "
			+ "edx.doc_type \"docType\", "
			+ "edx.algorithm_name \"algorithmName\", "
			+ "edx.algorithm_action \"actionId\", "
			+ "edx.record_status_cd \"recordStatusCd\", "	
			+ "edx.exception_txt \"exception\" "	
			+ "FROM edx_activity_log edx where edx.doc_type = '"+NEDSSConstants.PHC_236+"' and imp_exp_ind_cd ='I'";
	
	private static final String UPDATE_TEMPLATE_STATUS = "UPDATE WA_template SET record_status_cd=?, "
			+ "record_status_time=? "
			+ "WHERE "
			+ "wa_template_uid=?";
	
	 private static final String SELECT_ACTIVITY_LOGS =
	 "SELECT edx.edx_activity_log_uid \"edxActivityLogUid\", "
		+ "edx.record_status_time \"recordStatusTime\", "
		+ "edx.imp_exp_ind_cd \"impExpIndCd\", "
		+ "edx.doc_type \"docType\", "
		+ "edx.algorithm_name \"algorithmName\", "
		+ "edx.algorithm_action \"actionId\", "
		+ "edx.record_status_cd \"recordStatusCd\", "
		+ "edx.target_uid \"targetUid\", "
		+ "edx.Accession_nbr \"accessionNbr\", "
		+ "edx.Message_id \"messageId\", "
		+ "edx.Entity_nm \"entityNm\", "
		+ "edx.source_nm \"srcName\", "
		+ "edx.exception_txt \"exception\", "
		+ "edx.business_obj_localId \"businessObjLocalId\" "
		+ " FROM edx_activity_log edx where edx.doc_type = ? and imp_exp_ind_cd ='I' "
	    + "and record_status_time > ? and record_status_time < ? ";
	 
	 private static final String SELECT_ACTIVITY_LOGS_BY_ALGORITHMNAME =
			 " edx.edx_activity_log_uid \"edxActivityLogUid\", "
				+ "edx.record_status_time \"recordStatusTime\", "
				+ "edx.imp_exp_ind_cd \"impExpIndCd\", "
				+ "edx.doc_type \"docType\", "
				+ "edx.algorithm_name \"algorithmName\", "
				+ "edx.algorithm_action \"actionId\", "
				+ "edx.record_status_cd \"recordStatusCd\", "
				+ "edx.target_uid \"targetUid\", "
				+ "edx.exception_txt \"exception\" "	
				+ " FROM edx_activity_log edx ";
	 
	 private static final String SELECT_ACTIVITY_LOGS_BY_ALGORITHMNAME_SQL = "Select TOP 1 "+ SELECT_ACTIVITY_LOGS_BY_ALGORITHMNAME + " where edx.doc_type = '"+NEDSSConstants.LAB_11648804+"' or edx.doc_type = '"+NEDSSConstants.PHC_236+"' and imp_exp_ind_cd ='I' "
			    + "and algorithm_name = ? order by record_status_time desc";
	private static String SELET_DSM_DETAIL_LOGS_BY_UID=
		"SELECT " +
				"EDX_ACTIVITY_DETAIL_LOG_UID 	\"edxActivityLogUid\", "		+ 
				"LOG_TYPE 						\"logType\", "					+
				"LOG_COMMENT 					\"comment\" "			    	+
			"FROM " 															+
				"EDX_ACTIVITY_DETAIL_LOG " 										+
			"WHERE " 															+
				"EDX_ACTIVITY_LOG_UID =? "										+
			"AND "																+
				"RECORD_TYPE NOT IN " 											+
			"(" 															+
				"'"+EdxPHCRConstants.MSG_TYPE.Provider.toString()+"', " 	+
				"'"+EdxPHCRConstants.MSG_TYPE.Organization.toString()+"'" +
			")";
	
	private static final String SELECT_CODE_SET_NM_COLL_SQL = "SELECT  distinct CODE_SET_NM \"codesetNm\",code_set_group_id \"value\" FROM  NBS_SRTE..CODESET ";

	/**
	 * Method: getTemplateDTCollection
	 * gets the WaTemplateDT Collection<Object> Object for a given Template or all templates
	 * @return Collection<Object> of WaTemplateDT 
	 */
	
@SuppressWarnings("unchecked")
public ArrayList<Object> getTemplateDTCollection() throws NEDSSDAOSysException, NEDSSSystemException {
	
		WaTemplateDT waTemplateDt = new WaTemplateDT();
		String codeSeql = SELECT_TEMPLATES;
		ArrayList<Object>  waTemplateDtCollection = new ArrayList<Object> ();
		try {			
			waTemplateDtCollection = (ArrayList<Object>) preparedStmtMethod(waTemplateDt, waTemplateDtCollection, SELECT_TEMPLATES, NEDSSConstants.SELECT);
			
		} catch (Exception ex) {
			logger.debug("Select SQL = " + SELECT_TEMPLATES);
			logger.fatal("Error while creating Templates Library WaTemplateDT = " + waTemplateDt.toString(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return waTemplateDtCollection;
		
	} //waTemplateDtCollection

/**
 * method inactivateTemplate -
 *  Set status is set to I for inactive.
 * @param templateUid
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
public void inactivateTemplate(Long templateUid) throws NEDSSDAOSysException, NEDSSSystemException {
	try{
		logger.debug(" in Template DAO - inactivating template ..");
		updateTemplateRecordStatus( templateUid, gov.cdc.nedss.util.NEDSSConstants.RECORD_STATUS_Inactive);
	}catch(NEDSSDAOSysException ex){
		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		throw new NEDSSDAOSysException(ex.getMessage(), ex);
	}catch(Exception ex){
		logger.fatal("Exception  = "+ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.getMessage(), ex);
	}

}

/**
 * method updateTemplateStatus -
 *  To Activate, status is set to Active.
 * @param templateUid
 * @param newStatus - either I or A
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
private void updateTemplateRecordStatus(Long templateUid, String newStatus) throws NEDSSDAOSysException, NEDSSSystemException {

	logger.debug(" in Template DAO - updating Template status to " + newStatus + "for " + templateUid);

	String codeSql =UPDATE_TEMPLATE_STATUS;
	ArrayList<Object>  paramList = new ArrayList<Object> ();
	paramList.add(newStatus);
	paramList.add (new Timestamp(new Date().getTime()));
	paramList.add(templateUid);

	try {
		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE);

	} catch (Exception ex) {
		logger.fatal("Exception in Update Template Status: ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.getMessage(), ex);
	}

	logger.debug(" ...leaving Template DAO - updated Template status ..");
}
/**
 * method inactivateTemplate -
 *  Set status is set to I for inactive.
 * @param templateUid
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
public void activateTemplate(Long templateUid) throws NEDSSDAOSysException, NEDSSSystemException {
	try{
		logger.debug(" in Template DAO - activating template ..");
		updateTemplateRecordStatus( templateUid, gov.cdc.nedss.util.NEDSSConstants.RECORD_STATUS_Active);
	}catch(NEDSSDAOSysException ex){
		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		throw new NEDSSDAOSysException(ex.getMessage(), ex);
	}catch(Exception ex){
		logger.fatal("Exception  = "+ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.getMessage(), ex);
	}
}

/**
 * Method: getTemplateDTCollection
 * gets the WaTemplateDT Collection<Object> Object for a given Template or all templates
 * @return Collection<Object> of WaTemplateDT 
 */

@SuppressWarnings("unchecked")
public ArrayList<Object> getActivityLogCollection() throws NEDSSDAOSysException, NEDSSSystemException {

	EDXActivityDetailLogDT edxActivityLogDTDetails = new EDXActivityDetailLogDT();
	EDXActivityLogDT edxActivityLogDT = new EDXActivityLogDT();
	String codeSeql = SELECT_ACTIVITY_LOG;
     
	ArrayList<Object>  edxActivityLogDTDetailsCollection = new ArrayList<Object> ();
	ArrayList<Object>  edxActivityLogDTCollection = new ArrayList<Object> ();
	ArrayList<Object>  retActivityLogDTCollection = new ArrayList<Object> ();
	
	try {			
		edxActivityLogDTCollection = (ArrayList<Object>) preparedStmtMethod(edxActivityLogDT, edxActivityLogDTCollection, SELECT_ACTIVITY_LOG, NEDSSConstants.SELECT);
		
	} catch (Exception ex) {
		logger.debug("Select SQL = " + SELECT_ACTIVITY_LOG);
		logger.fatal("Error while creating Activity Log Library EDXActivityLogDT = " + edxActivityLogDT.toString(), ex);
		throw new NEDSSSystemException(ex.getMessage(), ex);
	}
	
	try {		
		
		
		if(edxActivityLogDTCollection!=null){
		  Iterator it = edxActivityLogDTCollection.iterator();
		  while(it.hasNext()){
			   EDXActivityLogDT dt = (EDXActivityLogDT)it.next();
			   edxActivityLogDTDetailsCollection.add(dt.getEdxActivityLogUid());
		      
			   edxActivityLogDTDetailsCollection = (ArrayList<Object>) preparedStmtMethod(edxActivityLogDTDetails, edxActivityLogDTDetailsCollection, SELECT_ACTIVITY_DETAIL_VOCAB_LOG, NEDSSConstants.SELECT);
		       dt.setEDXActivityLogDTWithVocabDetails(edxActivityLogDTDetailsCollection);
		       codeSeql = SELECT_ACTIVITY_DETAIL_VOCAB_LOG;
		       edxActivityLogDTDetailsCollection = new ArrayList();
		       
		       edxActivityLogDTDetailsCollection.add(dt.getEdxActivityLogUid());
		       edxActivityLogDTDetailsCollection = (ArrayList<Object>) preparedStmtMethod(edxActivityLogDTDetails, edxActivityLogDTDetailsCollection, SELECT_ACTIVITY_DETAIL_QUES_LOG, NEDSSConstants.SELECT);
		       dt.setEDXActivityLogDTWithQuesDetails(edxActivityLogDTDetailsCollection);
		       codeSeql = SELECT_ACTIVITY_DETAIL_QUES_LOG;
		       edxActivityLogDTDetailsCollection = new ArrayList();		       
		       retActivityLogDTCollection.add(dt);
		    }
		}
		
	} catch (Exception ex) {
		logger.debug("Select SQL = " + codeSeql);
		logger.fatal("Error while creating Activity Log Library EDXActivityLogDTDetails = " + edxActivityLogDTDetails.toString(), ex);
		throw new NEDSSSystemException(ex.getMessage(), ex);
	}
	return retActivityLogDTCollection;
	
} //waTemplateDtCollection

@SuppressWarnings("unchecked")
public ArrayList<Object> getAllDsmActivityLogCollection() throws NEDSSDAOSysException, NEDSSSystemException {

	EDXActivityLogDT edxActivityLogDT = new EDXActivityLogDT();   
	ArrayList<Object>  edxActivityLogDTCollection = new ArrayList<Object> ();
	
	try {			
		edxActivityLogDTCollection = (ArrayList<Object>) preparedStmtMethod(edxActivityLogDT, edxActivityLogDTCollection, SELECT_DSM_ACTIVITY_LOG, NEDSSConstants.SELECT);
		
	} catch (Exception ex) {
		logger.debug("Select SQL = " + SELECT_DSM_ACTIVITY_LOG);
		logger.fatal("Error while creating Activity Log Library EDXActivityLogDT = " + edxActivityLogDT.toString(), ex);
		throw new NEDSSSystemException(ex.getMessage(), ex);
	}
	
	return edxActivityLogDTCollection;	
} 

@SuppressWarnings("unchecked")
public ArrayList<Object> getDsmActivityLogCollection(DsmLogSearchDT dsmLogSearchDT) throws NEDSSDAOSysException, NEDSSSystemException {	
	ArrayList<Object>  edxActivityLogDTCollection = new ArrayList<Object> ();
	ArrayList<Object>  returnEdxActivityLogDTCollection = new ArrayList<Object> ();
	EDXActivityLogDT edxActivityLogDT = new EDXActivityLogDT();
	String docType = dsmLogSearchDT.getDocType();
	String selectSqlString = SELECT_ACTIVITY_LOGS;
	 	try {
	         java.util.Date fromDate = dsmLogSearchDT.getFromDateTime();
	         java.util.Date toDate = dsmLogSearchDT.getToDateTime();      
	         List<String> statuses = dsmLogSearchDT.getProcessStatus();
	        
	         edxActivityLogDTCollection.add(docType);
	         edxActivityLogDTCollection.add(new Timestamp(fromDate.getTime()));
	         edxActivityLogDTCollection.add(new Timestamp(toDate.getTime()));
	        
	         if(statuses != null && statuses.size()==1)
	         {
	        	 selectSqlString = selectSqlString + " and record_status_cd = ? ";
	        	 edxActivityLogDTCollection.add(statuses.get(0));
	         }
	         if(statuses != null && statuses.size()==2)
	         {
	        	 selectSqlString = selectSqlString + " and (record_status_cd = ? or record_status_cd = ?) ";
	        	 edxActivityLogDTCollection.add(statuses.get(0));
	        	 edxActivityLogDTCollection.add(statuses.get(1));
	         }
	         
	         returnEdxActivityLogDTCollection = (ArrayList<Object>) preparedStmtMethod(edxActivityLogDT, edxActivityLogDTCollection, selectSqlString, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.debug("Select SQL = " + selectSqlString);
			logger.fatal("Error while creating Activity Log Library"+ex.getMessage() , ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		
		return returnEdxActivityLogDTCollection;	
	} 

@SuppressWarnings("unchecked")
public EDXActivityLogDT getLatestDsmActivityLog(String algorithmName) throws NEDSSDAOSysException, NEDSSSystemException {	
	ArrayList<Object>  edxActivityLogDTCollection = new ArrayList<Object> ();
	ArrayList<Object>  returnEdxActivityLogDTCollection;
	EDXActivityLogDT edxActivityLogDT = new EDXActivityLogDT();
	EDXActivityLogDT returnEdxActivityLogDT = null;
	String selectSqlString = SELECT_ACTIVITY_LOGS_BY_ALGORITHMNAME;
	 	try {
	 		 selectSqlString = SELECT_ACTIVITY_LOGS_BY_ALGORITHMNAME_SQL;
			 edxActivityLogDTCollection.add(algorithmName);
	         
	         returnEdxActivityLogDTCollection = (ArrayList<Object>) preparedStmtMethod(edxActivityLogDT, edxActivityLogDTCollection, selectSqlString, NEDSSConstants.SELECT);
	         if(returnEdxActivityLogDTCollection == null || returnEdxActivityLogDTCollection.size()==0)
	        	 return null;
	         else
	        	 returnEdxActivityLogDT = (EDXActivityLogDT)returnEdxActivityLogDTCollection.get(0);
	        	 
		} catch (Exception ex) {
			logger.debug("Select SQL = " + selectSqlString);
			logger.fatal("Error while getting latest Activity Log by algorithm name"+ex.getMessage() , ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		
		return returnEdxActivityLogDT;	
	} 

@SuppressWarnings("unchecked")
public ArrayList<Object> getDsmActivityLogDetailCollection(Long activityUid) throws NEDSSDAOSysException, NEDSSSystemException {
    ArrayList<Object> logDetails = new ArrayList<Object>();
    ArrayList<Object> returnLogDetails = new ArrayList<Object>();
    EDXActivityDetailLogDT detailLogDT = new EDXActivityDetailLogDT();

    try
    {  	
    	logDetails.add(activityUid);
    	returnLogDetails = (ArrayList<Object>) preparedStmtMethod(detailLogDT, logDetails, SELET_DSM_DETAIL_LOGS_BY_UID, NEDSSConstants.SELECT);
    }
    catch(Exception ex)
    {
    	logger.debug("Select SQL = " + SELET_DSM_DETAIL_LOGS_BY_UID);
        logger.fatal("Exception while selecting " +
                        "a activityLog ; uid = " + activityUid.toString()+", Exception: "+ex.getMessage(), ex);
        throw new NEDSSSystemException( ex.getMessage(), ex);
    }
  
    return returnLogDetails;
} 

/**
 * Returns Collection of WaTemplateDT's
 * 
 * @param waQuestionIdentifier
 * @return WaQuestionDT
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */	

public Map<Object,Object>  fetchInsertSql(String strTableName,String colName, ArrayList<Object> IdList,String waTemplateUid,String templateNm,Map<Object,Object> codeSetNmColl)
throws NEDSSDAOSysException,NEDSSSystemException {

	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	ResultSet rs = null;
	ResultSetMetaData rsmd = null;
	EDXActivityLogDT dt = new EDXActivityLogDT();
	EDXActivityDetailLogDT ddt = new EDXActivityDetailLogDT();
	EDXActivityLogDAOImpl  edxActivityLogDAOImpl = new EDXActivityLogDAOImpl();
	StringWriter writerStr = new StringWriter();
    PrintWriter myPrinter = new PrintWriter(writerStr);
  try{
	
	String sqlQuery = null;			
	String qIDList = "";
	int index=0;
	int waNNdIndex=0;
	int waRdbIndex=0;
	int waTempUidRdbIndex=0;
	int waTempUidNndIndex=0;
	int waTempUidUiMetaIndex=0;
	int waTempUidRuleMetaIndex=0;
	int nbsUidIndex=0;
	int count =0;
	int templatePayLoadIndex =0;
	int keycount =0;
	int codeSetGroupMetadataIdIndex=0;
	int addUserIdIndex=0;
	int chgUserIdIndex=0;
	int quesTypeIndex=0;
	int quesUniqueNmIndex=0;
	int valSetNmIndex=0;
	int codeTypeIndex=0;
	int srcNmIndex=0;
	int codeSetGrpIdIndex=0;
	int unitValueIndex=0;
	int templatePubIndCdIndex=0;
	int jsFunctionIndex=0;
	
	
	String previousKey="";
	ArrayList<Object> ValList = new ArrayList<Object>();
	Map<Object,Object> map = new HashMap<Object,Object>();
	for(int i=0;i<IdList.size();i++){
		if(i<IdList.size()-1 ){					
		 if(IdList.get(i) != null)	
		 qIDList = qIDList + "'" +IdList.get(i).toString()+ "',";
		}else{
		  if(IdList.get(i) != null)
		  qIDList = qIDList + "'" +IdList.get(i).toString()+ "'";
		 else
			 qIDList = qIDList.substring(0, qIDList.length()-1);
		}
	}
		
	//String insertSql="Insert into "+strTableName +" (";

	try {
		 if(strTableName.equalsIgnoreCase("codeset_group_metadata") || 
    			  strTableName.equalsIgnoreCase("codeset") ||
    			  strTableName.equalsIgnoreCase("code_value_general"))
		dbConnection = getConnection(NEDSSConstants.SRT);
		 else
			 dbConnection = getConnection();
	} catch (NEDSSSystemException nsex) {
		logger.fatal("SQLException while obtaining database connection "
				+ "for fetchInsertSql : TemplatesDAOImpl"+nsex.getMessage(), nsex);
		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	}
      //  logic to check if code has seperate table
     
    	  if(strTableName.equalsIgnoreCase("wa_template") || strTableName.equalsIgnoreCase("wa_rule_metadata"))
	    	  sqlQuery = "Select * from "+strTableName+ " where wa_template_uid = "+waTemplateUid ;
    	  else if(strTableName.equalsIgnoreCase("wa_question"))
	    	  sqlQuery = "Select * from "+strTableName+ " where "+ colName +" in (" + qIDList +")" ;
    	  else if(strTableName.equalsIgnoreCase("codeset_group_metadata") || 
    			  strTableName.equalsIgnoreCase("codeset"))
    	  sqlQuery = "Select * from "+strTableName+ " where code_set_group_id in (" + qIDList +") order by code_set_nm" ;
    	  
    	  else if(strTableName.equalsIgnoreCase("code_value_general"))
    	  sqlQuery = "Select code_value_general.* from code_value_general,codeset where codeset.code_set_group_id in (" + qIDList +") and codeset.code_set_nm = code_value_general.code_set_nm order by code_value_general.code_set_nm" ;
	      
    	  else{
    	        if(strTableName.equalsIgnoreCase("wa_rdb_metadata") || strTableName.equalsIgnoreCase("wa_nnd_metadata"))
    	        	 sqlQuery = "Select * from "+strTableName+ " where wa_template_uid = "+waTemplateUid+" order by question_identifier,wa_ui_metadata_uid " ;		    		  
    	        else
    	             sqlQuery = "Select * from "+strTableName+ " where wa_template_uid = "+waTemplateUid ;
    	  }
      try {
		preparedStmt = dbConnection
				.prepareStatement(sqlQuery);
		rs = preparedStmt.executeQuery();
		
		if(rs!=null){
			rsmd = rs.getMetaData();
		
		int numColumns = rsmd.getColumnCount();
        int[] columnTypes = new int[numColumns];
        String columnNames = "";
        if(strTableName.equalsIgnoreCase("codeset_group_metadata") || 
    			  strTableName.equalsIgnoreCase("codeset") ||
    			  strTableName.equalsIgnoreCase("code_value_general")){
        for (int i = 0; i < numColumns; i++) {
            columnTypes[i] = rsmd.getColumnType(i + 1);
            if (i != 0) {
            	if(!rsmd.getColumnName(i + 1).equalsIgnoreCase("nbs_uid"))
                columnNames += ",";
            }
            if(!rsmd.getColumnName(i + 1).equalsIgnoreCase("nbs_uid"))
            columnNames += rsmd.getColumnName(i + 1);
            else
            	nbsUidIndex=i;
            	
            if(rsmd.getColumnName(i + 1).equalsIgnoreCase(colName)){
            	index = i;
            }
            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("code_set_group_id") && (strTableName.equalsIgnoreCase("codeset_group_metadata") || 
	    			  strTableName.equalsIgnoreCase("codeset"))){
            	codeSetGroupMetadataIdIndex = i;
            }
            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("value_set_code") &&  (strTableName.equalsIgnoreCase("codeset") || strTableName.equalsIgnoreCase("codeset_group_metadata"))){
            	valSetNmIndex = i;
    			
            }
            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("value_set_type_cd") &&  (strTableName.equalsIgnoreCase("codeset") || strTableName.equalsIgnoreCase("codeset_group_metadata"))){
            	codeTypeIndex = i;
            }
            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("add_user_id")){
            	addUserIdIndex = i;
            }
           
        }
        }else{
        	 for (int i = 1; i < numColumns; i++) {
		            columnTypes[i] = rsmd.getColumnType(i + 1);
		            if (i != 1) {
		            	if(!rsmd.getColumnName(i + 1).equalsIgnoreCase("xml_payload"))
		                columnNames += ",";
		            }
		            if(!rsmd.getColumnName(i + 1).equalsIgnoreCase("xml_payload"))
		            columnNames += rsmd.getColumnName(i + 1);
		            else
		            	templatePayLoadIndex = i;	
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase(colName)){
		            	index = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("wa_ui_metadata_uid") && strTableName.equalsIgnoreCase("wa_nnd_metadata")){
		            	waNNdIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("wa_ui_metadata_uid") && strTableName.equalsIgnoreCase("wa_rdb_metadata") ){
		            	waRdbIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("wa_template_uid") && strTableName.equalsIgnoreCase("wa_nnd_metadata")){
		            	waTempUidNndIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("wa_template_uid") && strTableName.equalsIgnoreCase("wa_rdb_metadata") ){
		            	waTempUidRdbIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("wa_template_uid") && strTableName.equalsIgnoreCase("wa_ui_metadata") ){
		            	waTempUidUiMetaIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("wa_template_uid") && strTableName.equalsIgnoreCase("wa_rule_metadata") ){
		            	waTempUidRuleMetaIndex = i;
		            }//
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("question_nm") && strTableName.equalsIgnoreCase("wa_question") ){
		            	quesUniqueNmIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("question_oid_system_txt") && strTableName.equalsIgnoreCase("wa_question") ){
		            	quesTypeIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("source_nm") && strTableName.equalsIgnoreCase("wa_question") ){
		            	srcNmIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("source_nm") && strTableName.equalsIgnoreCase("wa_template") ){
		            	srcNmIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("add_user_id")){
		            	addUserIdIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("last_chg_user_id")){
		            	chgUserIdIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("code_set_group_id") && strTableName.equalsIgnoreCase("wa_ui_metadata") ){
		            	codeSetGrpIdIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("code_set_group_id") && strTableName.equalsIgnoreCase("wa_question") ){//unitTypeCdIndex
		            	codeSetGrpIdIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("publish_ind_cd") && strTableName.equalsIgnoreCase("wa_template") ){
		            	templatePubIndCdIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("javascript_function") && strTableName.equalsIgnoreCase("wa_rule_metadata") ){
		            	jsFunctionIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("unit_value") && strTableName.equalsIgnoreCase("wa_question") ){
		            	unitValueIndex = i;
		            }
		            if(rsmd.getColumnName(i + 1).equalsIgnoreCase("unit_value") && strTableName.equalsIgnoreCase("wa_ui_metadata") ){
		            	unitValueIndex = i;
		            }
		        }
        }

        java.util.Date d = null; 	
        String key="";
        String waUiMetadataId="";
        String quesUniqueNm="";
        String quesType="";
        String valSetNm="";
        String codeType="";
        String prevValue="";
        while (rs.next()) {
            String columnValues = "";
            if(strTableName.equalsIgnoreCase("codeset_group_metadata") || 
    			  strTableName.equalsIgnoreCase("codeset") ||
    			  strTableName.equalsIgnoreCase("code_value_general")){
	            for (int i = 0; i < numColumns; i++) {                      
	                if (i != 0) {
	                	 if(!(!strTableName.equalsIgnoreCase("Codeset_Group_Metadata")&& nbsUidIndex==i)){		                        	
	                		 columnValues += ",";
	                        }	
	                   
	                }
	           
                switch (columnTypes[i]) {
                    case Types.BIGINT:
                    case Types.BIT:
                    case Types.BOOLEAN:
                    case Types.DECIMAL:
                    case Types.DOUBLE:
                    case Types.FLOAT:
                    case Types.INTEGER:
                    case Types.NUMERIC:
                    case Types.SMALLINT:
                    case Types.TINYINT:		                    	
                        String v = rs.getString(i + 1);
                        if(!(!strTableName.equalsIgnoreCase("Codeset_Group_Metadata")&& nbsUidIndex==i)){	
                        	if((strTableName.equalsIgnoreCase("codeset_group_metadata") ||strTableName.equalsIgnoreCase("codeset")) && codeSetGroupMetadataIdIndex==i){
	                        	columnValues += "ReplaceWithCodeSetGroupID";
	                        	
	                        }
                        	else if(addUserIdIndex != 0 && addUserIdIndex==i){
	                        	columnValues += "ReplaceCurrentUserId";			                        	
	                        }	                        	
                        	
                        	else
                        	columnValues += v;
                        }		                        
                      //  else columnValues += v;
                        if(index==i){
                        	key = rs.getString(i + 1);
                        }
                       
                        break;

                    case Types.DATE:
                        d = rs.getDate(i + 1); 
                    case Types.TIME:
                        if (d == null) d = rs.getTime(i + 1);
                    case Types.TIMESTAMP:
                        if (d == null) d = rs.getTimestamp(i + 1);

                        if (d == null) {
                         //   columnValues += "null";//date field might be populated by current date at time of import
                        	columnValues += "ReplaceWithCurrentDate";
                        }
                        else {
                        	//date field might be populated by current date at time of import
                           // columnValues += "TO_DATE('"
                                  //    + dateFormat.format(d)
                                  //    + "', 'YYYY/MM/DD HH24:MI:SS')";
                        	columnValues += "ReplaceWithCurrentDate";
                        }
                        break;

                    default:
                        v = rs.getString(i + 1);
                        if(index==i){
                        	key = rs.getString(i + 1);
                        }
                        if (v != null ) {
                        	if(!(!strTableName.equalsIgnoreCase("Codeset_Group_Metadata")&& nbsUidIndex==i)){	
                        		if((strTableName.equalsIgnoreCase("codeset_group_metadata") ||strTableName.equalsIgnoreCase("codeset")) && codeSetGroupMetadataIdIndex==i){
		                        	columnValues += "ReplaceWithCodeSetGroupID";
                        		}
                        		else if(addUserIdIndex != 0 && addUserIdIndex==i){
		                        	columnValues += "ReplaceCurrentUserId";
		                        	
		                        }
                        		else if((strTableName.equalsIgnoreCase("codeset") || strTableName.equalsIgnoreCase("codeset_group_metadata")) && valSetNmIndex==i){
                        			valSetNm = v;
                        			columnValues += "'" + v.replaceAll("'", "''") + "'";
		                        	
		                        }
                        		else if((strTableName.equalsIgnoreCase("codeset") || strTableName.equalsIgnoreCase("codeset_group_metadata")) && codeTypeIndex==i){
		                        	codeType = v;	
		                        	columnValues += "'" + v.replaceAll("'", "''") + "'";
		                        	
		                        }
		                       	else
                        		 columnValues += "'" + v.replaceAll("'", "''") + "'";
	                        }			                        
	                       // else
                           // columnValues += "'" + v.replaceAll("'", "''") + "'";
                        }
                        else {
                            	if(addUserIdIndex != 0 && addUserIdIndex==i){
	                        	  columnValues += "ReplaceCurrentUserId";
	                        	
	                            }else
                                   columnValues += "null";
                        }
                        break;
                }
            }
            }else{     
            	 if(strTableName.equalsIgnoreCase("wa_ui_metadata"))
            		 waUiMetadataId =  rs.getString(1);
	            
	            for (int i = 1; i < numColumns; i++) {                      
	                if (i != 1) {
	                	if(!(strTableName.equalsIgnoreCase("wa_template")&& templatePayLoadIndex==i))
	                    columnValues += ",";
	                }
	           
                switch (columnTypes[i]) {
                    case Types.BIGINT:
                    case Types.BIT:
                    case Types.BOOLEAN:
                    case Types.DECIMAL:
                    case Types.DOUBLE:
                    case Types.FLOAT:
                    case Types.INTEGER:
                    case Types.NUMERIC:
                    case Types.SMALLINT:
                    case Types.TINYINT:		                    	
                        String v = rs.getString(i + 1);
                       
                        if(strTableName.equalsIgnoreCase("wa_nnd_metadata")&& waNNdIndex==i){
                        	waUiMetadataId = v;
                        	columnValues += "ReplaceWaUiMetadataUid";
                        	
                        }
                        else if(strTableName.equalsIgnoreCase("wa_rdb_metadata")&& waRdbIndex==i){
                        	waUiMetadataId = v;
                        	columnValues += "ReplaceWaUiMetadataUid";
                        	
                        }
                        else if(strTableName.equalsIgnoreCase("wa_rdb_metadata")&& waTempUidRdbIndex==i){
                        	columnValues += "ReplaceTemplateUid";
                        	
                        }
                        else if(strTableName.equalsIgnoreCase("wa_nnd_metadata")&& waTempUidNndIndex==i){
                        	columnValues += "ReplaceTemplateUid";
                        	
                        }
                        else if(strTableName.equalsIgnoreCase("wa_ui_metadata")&& waTempUidUiMetaIndex==i){
                        	columnValues += "ReplaceTemplateUid";
                        	
                        }
                        else if(strTableName.equalsIgnoreCase("wa_rule_metadata")&& waTempUidRuleMetaIndex==i){
                        	columnValues += "ReplaceTemplateUid";
                        	
                        }
                        
                        //codeSetGrpIdIndex
                        else if(strTableName.equalsIgnoreCase("wa_ui_metadata")&& codeSetGrpIdIndex==i){
                        	if(v != null){
                        		if(v.equalsIgnoreCase(""))
                        			columnValues += null;
                        		else {
	                        	Iterator<?> it = codeSetNmColl.entrySet().iterator();
	                        	while(it.hasNext()){
	                        		 Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
	                        		 if(pairs.getValue().toString().equals(v)){
	                        			 columnValues += "$$CDSETNM" +pairs.getKey().toString()+"##CDSETNM";
	                        			 break;
	                        		 }
	                        	 }
                        		}//else if ends
                        	}else//if v is null
                        	 columnValues += v;
                        	
                        	
                        }
                        //unitValueIndex
                        else if(strTableName.equalsIgnoreCase("wa_ui_metadata")&& unitValueIndex==i){
                        	if(v != null && prevValue != null && prevValue.equals("CODED")){
                        		if(v.equalsIgnoreCase(""))
                        			columnValues += null;
                        		else {
	                        	Iterator<?> it = codeSetNmColl.entrySet().iterator();
	                        	while(it.hasNext()){
	                        		 Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
	                        		 if(pairs.getValue().toString().equals(v)){
	                        			 columnValues += "$$CDSETNM" +pairs.getKey().toString()+"##CDSETNM";
	                        			 break;
	                        		 }
	                        	 }
                        		}//else if ends
                        	}else//if v is null
                        	columnValues += "'" + v.replaceAll("'", "''") + "'";
                        	
                        	
                        }
                        else if(strTableName.equalsIgnoreCase("wa_question")&& codeSetGrpIdIndex==i){
                        	if(v != null){
                    		if(v.equalsIgnoreCase(""))
                    			columnValues += null;
                    		else {	
	                        	Iterator<?> it = codeSetNmColl.entrySet().iterator();
	                        	while(it.hasNext()){
	                        		 Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
	                        		 if(pairs.getValue().toString().equals(v)){
	                        			 columnValues += "$$CDSETNM" +pairs.getKey().toString()+"##CDSETNM";
	                        			 break;
	                        		 }
	                        	}
                    		}
                        	//columnValues += "ReplaceTemplateUid";
                        	}else//if v is null
                           	 columnValues += v;
                        	
                        }
                        
                      //unitValueIndex
                        else if(strTableName.equalsIgnoreCase("wa_question")&& unitValueIndex==i){
                        	if(v != null && prevValue != null && prevValue.equals("CODED")){
                        		if(v.equalsIgnoreCase(""))
                        			columnValues += null;
                        		else {
	                        	Iterator<?> it = codeSetNmColl.entrySet().iterator();
	                        	while(it.hasNext()){
	                        		 Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
	                        		 if(pairs.getValue().toString().equals(v)){
	                        			 columnValues += "$$CDSETNM" +pairs.getKey().toString()+"##CDSETNM";
	                        			 break;
	                        		 }
	                        	 }
                        		}//else if ends
                        	}else//if v is null
                        	 columnValues += "'" + v.replaceAll("'", "''") + "'";
                        	
                        	
                        }
                        
                        else if(addUserIdIndex==i){
                        	columnValues += "ReplaceCurrentUserId";
                        	
                        }
                        else if(chgUserIdIndex==i){
                        	columnValues += "ReplaceCurrentUserId";
                        	
                        }
                        else columnValues += v;
                        if(index==i){
                        	key = rs.getString(i + 1);
                        }
                        prevValue =rs.getString(i + 1);
                        break;

                    case Types.DATE:
                        d = rs.getDate(i + 1); 
                    case Types.TIME:
                        if (d == null) d = rs.getTime(i + 1);
                    case Types.TIMESTAMP:
                        if (d == null) d = rs.getTimestamp(i + 1);

                        if (d == null) {
                         //   columnValues += "null";//date field might be populated by current date at time of import
                        	columnValues += "ReplaceWithCurrentDate";
                        }
                        else {
                        	//date field might be populated by current date at time of import
                           // columnValues += "TO_DATE('"
                                  //    + dateFormat.format(d)
                                  //    + "', 'YYYY/MM/DD HH24:MI:SS')";
                        	columnValues += "ReplaceWithCurrentDate";
                        }
                        break;
                        
                    case Types.CLOB: 
                    	java.sql.Clob clob  = (java.sql.Clob)rs.getClob(i + 1); 
                    	BufferedReader is = new BufferedReader(
            					new InputStreamReader(clob.getAsciiStream()));
            			String tempStr = null;
            			StringBuffer sb = new StringBuffer();
            			while ((tempStr = is.readLine()) != null) {
            				sb.append(tempStr);
            			}
            			if(strTableName.equalsIgnoreCase("wa_rule_metadata")&& jsFunctionIndex==i)
            				columnValues += "'$$JSFUN" + sb.toString().replaceAll("'", "''") + "##JSFUN'";
                    	//columnValues += sb.toString();
            			else
            				columnValues += "'" + sb.toString().replaceAll("'", "''") + "'";
                    	 break;

                    default:
                    	if(!(strTableName.equalsIgnoreCase("wa_template")&& templatePayLoadIndex==i)){
	                        v = rs.getString(i + 1);
	                        if(index==i){
	                        	key = rs.getString(i + 1);
	                        }
	                    //    if(index==0)
	                    //    	key = new Integer(i).toString();
	                        
	                       
	                        
	                        if (v != null) {
	                        	if(strTableName.equalsIgnoreCase("wa_nnd_metadata")&& waNNdIndex==i){
	                        		waUiMetadataId = v;
		                        	columnValues += "ReplaceWaUiMetadataUid";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_rdb_metadata")&& waRdbIndex==i){
		                        	waUiMetadataId = v;
                                	columnValues += "ReplaceWaUiMetadataUid";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_rdb_metadata")&& waTempUidRdbIndex==i){
                                	columnValues += "ReplaceTemplateUid";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_nnd_metadata")&& waTempUidNndIndex==i){
                                	columnValues += "ReplaceTemplateUid";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_ui_metadata")&& waTempUidUiMetaIndex==i){
                                	columnValues += "ReplaceTemplateUid";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_rule_metadata")&& waTempUidRuleMetaIndex==i){
                                	columnValues += "ReplaceTemplateUid";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_question")&& quesTypeIndex==i){
		                        	quesType = v;
		                        	 columnValues += "'" + v.replaceAll("'", "''") + "'";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_question")&& quesUniqueNmIndex==i){
		                        	quesUniqueNm = v;
		                        	 columnValues += "'" + v.replaceAll("'", "''") + "'";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_question")&& srcNmIndex==i){
                                	columnValues += "ReplaceSrcName";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_template")&& srcNmIndex==i){
                                	columnValues += "ReplaceSrcName";
		                        }
                                else if(strTableName.equalsIgnoreCase("wa_rule_metadata")&& jsFunctionIndex==i){
                        				columnValues += "'$$JSFUN" + v.replaceAll("'", "''") + "##JSFUN'";
		                        }
	                        	 //codeSetGrpIdIndex
		                        else if(strTableName.equalsIgnoreCase("wa_ui_metadata")&& codeSetGrpIdIndex==i){
		                        	if(v.equalsIgnoreCase(""))
		                    			columnValues += null;
		                        	else{
			                        	Iterator<?> it = codeSetNmColl.entrySet().iterator();
			                        	while(it.hasNext()){
			                        		 Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
			                        		 if(pairs.getValue().toString().equals(v)){
			                        			 columnValues += "$$CDSETNM" +pairs.getKey().toString()+"##CDSETNM";
			                        			 break;
			                        		 }
			                        	}
		                        	}
		                        
		                        	
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_question")&& codeSetGrpIdIndex==i){
		                        	
		                        	if(v.equalsIgnoreCase(""))
		                    			columnValues += null;
		                        	else{
		                        	Iterator<?> it = codeSetNmColl.entrySet().iterator();
		                        	while(it.hasNext()){
		                        		 Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
		                        		 if(pairs.getValue().toString().equals(v)){
		                        			 columnValues += "$$CDSETNM" +pairs.getKey().toString()+"##CDSETNM";
		                        			 break;
		                        		 }
		                        	 }
		                        	}
		                        	//columnValues += "ReplaceTemplateUid";
		                        	
		                        	
		                        }
	                        	  //unitValueIndex
		                        else if(strTableName.equalsIgnoreCase("wa_ui_metadata")&& unitValueIndex==i){
		                        	if(v != null && prevValue != null && prevValue.equals("CODED")){
		                        		if(v.equalsIgnoreCase(""))
		                        			columnValues += null;
		                        		else {
			                        	Iterator<?> it = codeSetNmColl.entrySet().iterator();
			                        	while(it.hasNext()){
			                        		 Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
			                        		 if(pairs.getValue().toString().equals(v)){
			                        			 columnValues += "$$CDSETNM" +pairs.getKey().toString()+"##CDSETNM";
			                        			 break;
			                        		 }
			                        	 }
		                        		}//else if ends
		                        	}else//if v is null
		                        	 columnValues += "'" + v.replaceAll("'", "''") + "'";
		                        	
		                        	
		                        }
	                        	//unitValueIndex
		                        else if(strTableName.equalsIgnoreCase("wa_question")&& unitValueIndex==i){
		                        	if(v != null && prevValue != null && prevValue.equals("CODED")){
		                        		if(v.equalsIgnoreCase(""))
		                        			columnValues += null;
		                        		else {
			                        	Iterator<?> it = codeSetNmColl.entrySet().iterator();
			                        	while(it.hasNext()){
			                        		 Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
			                        		 if(pairs.getValue().toString().equals(v)){
			                        			 columnValues += "$$CDSETNM" +pairs.getKey().toString()+"##CDSETNM";
			                        			 break;
			                        		 }
			                        	 }
		                        		}//else if ends
		                        	}else//if v is null
		                        	 columnValues += "'" + v.replaceAll("'", "''") + "'";
		                        	
		                        	
		                        }
		                        else if(addUserIdIndex==i){
		                        	columnValues += "ReplaceCurrentUserId";
		                        	
		                        }
		                        else if(chgUserIdIndex==i){
		                        	columnValues += "ReplaceCurrentUserId";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_template")&& templatePubIndCdIndex==i){
	 	                        	columnValues += "SetPublishIndCdtoF";
	 	                        }
		                        else
	                            columnValues += "'" + v.replaceAll("'", "''") + "'";
	                        }
	                        else {
	                        	 if(strTableName.equalsIgnoreCase("wa_question")&& srcNmIndex==i){
                                	columnValues += "ReplaceSrcName";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_template")&& srcNmIndex==i){
                                	columnValues += "ReplaceSrcName";
		                        	
		                        }
		                        else if(addUserIdIndex==i){
		                        	columnValues += "ReplaceCurrentUserId";
		                        	
		                        }
		                        else if(chgUserIdIndex==i){
		                        	columnValues += "ReplaceCurrentUserId";
		                        	
		                        }
		                        else if(strTableName.equalsIgnoreCase("wa_template")&& templatePubIndCdIndex==i){
	 	                        	columnValues += "SetPublishIndCdtoF";
	 	                        }
		                        else
		                        	columnValues += "null";
	                        }
	                        prevValue =rs.getString(i + 1);
	                        break;
                    	}
                    	
                }
              } 
            }
            
            logger.debug(String.format("INSERT INTO %s (%s) values (%s)", 
            	                	strTableName,
                                    columnNames,
                                    columnValues));
            if(strTableName.equalsIgnoreCase("code_value_general")|| strTableName.equalsIgnoreCase("wa_nnd_metadata") || strTableName.equalsIgnoreCase("wa_rdb_metadata")){
            	if(strTableName.equalsIgnoreCase("wa_rdb_metadata") || strTableName.equalsIgnoreCase("wa_nnd_metadata"))
            		key = key + "-" + waUiMetadataId ;
            	if(count == 0 || key.toString().equalsIgnoreCase(previousKey)){
            		ValList.add(String.format("INSERT INTO %s (%s) values (%s)", 
            	                	strTableName,
                                    columnNames,
                                    columnValues));	            		
            	}
            	
            	
            
	            if(count != 0 && !key.toString().equalsIgnoreCase(previousKey)){
	            	
	            	
	            	map.put(previousKey,ValList);
	            	ValList =  new ArrayList<Object>();
	            	ValList.add(String.format("INSERT INTO %s (%s) values (%s)", 
    	                	strTableName,
                            columnNames,
                            columnValues));	 
	            	
	            }

	            previousKey = key;
                  count++;
            }else if(strTableName.equalsIgnoreCase("wa_rule_metadata")){
            	map.put(keycount,String.format("INSERT INTO %s (%s) values (%s)", 
	                	strTableName,
                        columnNames,
                        columnValues));	
            	keycount++;
            	
            }
            
            else{
            	
            	if(strTableName.equalsIgnoreCase("wa_ui_metadata"))
            		key = key + "-" + waUiMetadataId ;
            	
            	
            	else if(strTableName.equalsIgnoreCase("wa_question")){
            		if (quesType.equals("NULL"))
            			quesType = " ";
            		if (quesUniqueNm.equals("NULL"))
            			quesUniqueNm = " ";
            		key = key + ":QT:" + quesType + ":QN:" +quesUniqueNm;
            	}
            	else if(strTableName.equalsIgnoreCase("codeset")){
            		if (codeType.equals("NULL"))
            			codeType = " ";
            		if (valSetNm.equals("NULL"))
            			valSetNm = " ";
            		key = key + ":VSN:" + valSetNm + ":CT:" +codeType;
            	}
            	
            	map.put(key,String.format("INSERT INTO %s (%s) values (%s)", 
	                	strTableName,
                        columnNames,
                        columnValues));	
            	
            }
        }
        if(strTableName.equalsIgnoreCase("code_value_general"))
         map.put(key,ValList);	
        if(strTableName.equalsIgnoreCase("wa_rdb_metadata") || strTableName.equalsIgnoreCase("wa_nnd_metadata")){
        	map.put(key,ValList);	
        }

		logger.debug("returned insert sql query map"+map);
		}
		return map;
	} catch (SQLException se) {	
		  logger.fatal("SQLException  = "+se.getMessage(), se);
		  se.printStackTrace(myPrinter);
          String stackTraceStr = writerStr.toString();
    	  dt.setSourceUid(null);
    	  dt.setTargetUid(new Long(waTemplateUid));
    	  dt.setDocType("Template");
    	  dt.setDocName(templateNm);
    	  dt.setRecordStatusCd("Failure");
    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
    	  dt.setException("error occured  during the export process. Exception occurred is : " +stackTraceStr);
    	  dt.setImpExpIndCd("E");
    	  dt.setSourceTypeCd(null);
    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
    	  dt.setTargetTypeCd("Template");
    	  dt.setBusinessObjLocalId(null);	
    	  //dt.setEDXActivityLogDTDetails(eDXActivityLogDTDetails);	
    	  edxActivityLogDAOImpl.insertExportEDXActivityLog(dt);
		throw new NEDSSDAOSysException("SQLException while fetching records "
				+ "fetchInsertSql: TemplatesDAOImpl sql Query is :"+sqlQuery +"Exception is :" +se.getMessage(), se);
	}
 }catch (Exception se) {
	  logger.fatal("Exception  = "+se.getMessage(), se);
	  se.printStackTrace(myPrinter);
      String stackTraceStr = writerStr.toString();
	  dt.setSourceUid(null);
	  dt.setTargetUid(new Long(waTemplateUid));
	  dt.setDocType("Template");
	  dt.setDocName(templateNm);
	  dt.setRecordStatusCd("Failure");
	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
	  dt.setException("error occured  during the export process. Exception occurred is : " +stackTraceStr);
	  dt.setImpExpIndCd("E");
	  dt.setSourceTypeCd(null);
	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
	  dt.setTargetTypeCd("Template");
	  dt.setBusinessObjLocalId(null);	
	 // dt.setEDXActivityLogDTDetails(eDXActivityLogDTDetails);	
	  edxActivityLogDAOImpl.insertExportEDXActivityLog(dt);
		throw new NEDSSDAOSysException("SQLException while fetching records "
				+ "fetchInsertSql: TemplatesDAOImpl " +se.getMessage(), se);
	}         
 finally {
		closeResultSet(rs);				
		closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	}
}
   /**
	 * Returns Collection of WaTemplateDT's
	 * 
	 * @param waQuestionIdentifier
	 * @return WaQuestionDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */	
	
	public EDXActivityLogDT InsertImportSql(ArrayList<Object> aList,String templateUid,Long currentUser,Long activityLogUid,String templateNm,EDXActivityLogDT dt,Map<Object,Object> codeSetNmCollMap,Map<Object,Object> newlyAddedCodeSet )
	throws NEDSSDAOSysException,NEDSSSystemException {
   
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		//ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		Map<Object,Object> MetaUidMap = new HashMap<Object,Object>();
		Map<Object,Object> TempUidMap = new HashMap<Object,Object>();
		boolean UiInsert = true;
		//EDXActivityLogDT dt = new EDXActivityLogDT();
		EDXActivityDetailLogDT ddt = new EDXActivityDetailLogDT();
		Collection<Object> eDXActivityLogDTDetails = new ArrayList<Object>();
		EDXActivityLogDAOImpl  edxActivityLogDAOImpl = new EDXActivityLogDAOImpl();
		int s = 0;
	    int e1 = 0;
	    String exceptionSql="";
	    StringWriter writerStr = new StringWriter();
        PrintWriter myPrinter = new PrintWriter(writerStr);
        Statement newstatement = null;
		 ResultSet newresultSet = null;
	
		
		try {
			dbConnection = getConnection();
			
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for InsertImportSql : TemplatesDAOImpl"+nsex.getMessage(), nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
	      
	      try {
	    	  
	    	 for(int i=0;i<aList.size();i++) { 		 
	    	 
	        Iterator<?> it = ((Map<?, ?>) aList.get(i)).entrySet().iterator();
	  	    while (it.hasNext()) {
	  	        Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
	  	      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
	  		    if (pairs == null) {
	  		   		logger.warn("Marshaller: Unexpected null ? ");
	  	    		break;
	  		    }
	  		    if(i==5){
	  		    	String insertSql1[] = (String[])pairs.getValue();
			  		String sql="";
			  		PreparedStatement preparedStmt2= null;
					int resultCount =0;
					Long  waRuleMetadataDTUid = null;
					
			  		
				  		for(int len =0;len< insertSql1.length;len++){  
				  			String  jsFunction = null;
				  			sql = insertSql1[len];
				  			if(sql.indexOf("$$JSFUN")!= -1){
				  				jsFunction  = sql.substring(sql.indexOf("$$JSFUN")+7,sql.indexOf("##JSFUN"));
				  			}
				  				sql=  JspBeautifier.replace(sql,"ReplaceWithCurrentDate", "GETDATE()");	
				  				sql= JspBeautifier.replace(sql,"$$JSFUN"+jsFunction+"##JSFUN", jsFunction);
				  			sql=	JspBeautifier.replace(sql,"ReplaceTemplateUid", templateUid);
				  			sql=  JspBeautifier.replace(sql,"ReplaceCurrentUserId", currentUser.toString());
				  			
				  			try{
						  		preparedStmt = dbConnection.prepareStatement(sql);					
								int res = preparedStmt.executeUpdate();
								
								   eDXActivityLogDTDetails.add(ddt);
								closeStatement(preparedStmt);

								}catch(SQLException e){
									if(i==5){
										if(e.getMessage().contains("duplicate key")){
											   closeStatement(preparedStmt);
											   logger.warn("unique key constraint while adding a record into wa_rule_metadata table table "+sql);
											   	   
										       continue;
										   }
										   else	{ 
											   e.printStackTrace(myPrinter);
							                    String stackTraceStr = writerStr.toString();
										   logger.fatal("fatal error occured while inserting following code set to wa_rule_metadata table. rolling back the whole transaction "+e.getMessage(), e);
										     dt.setEdxActivityLogUid(activityLogUid);
									    	  dt.setSourceUid(null);
									    	  dt.setTargetUid(new Long(templateUid));
									    	  dt.setDocType("Template");
									    	  dt.setDocName(templateNm);
									    	  dt.setRecordStatusCd("Failure");
									    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
									    	  dt.setException("fatal error occured while inserting following sql statement "+ sql +" to wa_rule_metadata table. Exception occurred is : " +stackTraceStr);
									    	  dt.setImpExpIndCd("I");
									    	  dt.setSourceTypeCd(null);
									    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
									    	  dt.setTargetTypeCd("Template");
									    	  dt.setBusinessObjLocalId(null);	
									    	  dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);	
									    	//  edxActivityLogDAOImpl.insertEDXActivityLog(dt);
										   return dt;

										   }	
										
									}else{
										logger.error("SQLException  = "+e.getMessage(), e);
									}
									
				  		       }
	  		    	
	  		    	
	  		            }
	  		    }else if(i!=5) {			  		    	
	  		     if(i==3 || i==4){	
	  		    	String insertSql1[] = (String[])pairs.getValue();
	  		    	
	  		       for(int len =0;len< insertSql1.length;len++){  
	  		    	 String insertSql = "" ;
	  		    	   insertSql =  insertSql1[len];
	  		    	   insertSql = insertSql.substring(1, insertSql.length()-1);
		  		       insertSql=  JspBeautifier.replace(insertSql,"ReplaceWithCurrentDate", "GETDATE()");
				  		
		  		       insertSql=  JspBeautifier.replace(insertSql,"ReplaceCurrentUserId", currentUser.toString());
				  		  if(i>=2){					  			
					  	    	// if(Uidpairs.getKey().equals(pairs.getKey())){
					  	    		insertSql=	JspBeautifier.replace(insertSql,"ReplaceTemplateUid", templateUid); 
					  	    		//break;
					  	    	//  }	    	
					  	     	  			
				  		//}
				  		    if(i>2){
				  		    	Iterator<?> UidMap = MetaUidMap.entrySet().iterator();
							  	    while (UidMap.hasNext()) {
							  	    	 Map.Entry<?,?> Uidpairs = (Map.Entry<?,?>)UidMap.next();
							  	    	 if(Uidpairs.getKey().equals(pairs.getKey())){
							  	    		insertSql=	JspBeautifier.replace(insertSql,"ReplaceWaUiMetadataUid", Uidpairs.getValue().toString()); 
							  	    		break;
							  	    	 }  	    	
							  	    }		    	
				  		    }
				  		}
				  		    
						try{
							  
							  
							while ((e1 = insertSql.indexOf(", INSERT INTO", s)) >= 0) {       
							        preparedStmt = dbConnection.prepareStatement(insertSql.substring(s, e1));	
							        exceptionSql = insertSql.substring(s, e1);
							        s=e1+", ".length();
							       
									int res = preparedStmt.executeUpdate();
									//closeResultSet(res);				
									closeStatement(preparedStmt);
								    }
							      if (!insertSql.isEmpty()) { //gst: NND could be empty
							    	  preparedStmt = dbConnection.prepareStatement(insertSql.substring(s));	
							    	  exceptionSql = insertSql.substring(s);
							    	  int res = preparedStmt.executeUpdate();
							    	  //closeResultSet(res);				
							    	  closeStatement(preparedStmt);
							      }
				  		
						}catch(SQLException e){
							 closeStatement(preparedStmt);
							if(i==3){
								if(e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")){
									  
									   logger.warn("unique key constraint while adding a record into wa_nnd_metadata  table "+insertSql);
								       continue;
								   }
								   else	{  
									   e.printStackTrace(myPrinter);
					                    String stackTraceStr = writerStr.toString();
									   dt.setEdxActivityLogUid(activityLogUid);
									   logger.fatal("fatal error occured while inserting following code set to wa_nnd_metadata table. rolling back the whole transaction "+e.getMessage(), e);
								      dt.setSourceUid(null);
							    	  dt.setTargetUid(new Long(templateUid));
							    	  dt.setDocType("Template");
							    	  dt.setDocName(templateNm);
							    	  dt.setRecordStatusCd("Failure");
							    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
							    	  dt.setException("fatal error occured while inserting following sql statement "+ exceptionSql +" to wa_nnd_metadata table. Exception occurred is : " +stackTraceStr);
							    	 
							    	  dt.setImpExpIndCd("I");
							    	  dt.setSourceTypeCd(null);
							    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
							    	  dt.setTargetTypeCd("Template");
							    	  dt.setBusinessObjLocalId(null);	
							    	  dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);
							    	 // edxActivityLogDAOImpl.insertEDXActivityLog(dt);
								      return dt;

								   }
								
							}else{
								logger.error("SQLException  = "+e.getMessage(), e);
							}
							if(i==4){
								if(e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")){											
									   logger.warn("unique key constraint while adding a record into wa_rdb_metadata  table "+insertSql);
								       continue;
								   }
								   else	{  
									   e.printStackTrace(myPrinter);
					                    String stackTraceStr = writerStr.toString();
								   logger.fatal("fatal error occured while inserting following code set to wa_rdb_metadata table. rolling back the whole transaction "+e.getMessage(), e);
								   dt.setEdxActivityLogUid(activityLogUid);   
								   dt.setSourceUid(null);
							    	  dt.setTargetUid(new Long(templateUid));
							    	  dt.setDocType("Template");
							    	  dt.setDocName(templateNm);
							    	  dt.setRecordStatusCd("Failure");
							    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
							    	  dt.setException("fatal error occured while inserting following sql statement "+ exceptionSql +" to wa_rdb_metadata table. Exception occurred is : " +stackTraceStr);
							    	
							    	  dt.setImpExpIndCd("I");
							    	  dt.setSourceTypeCd(null);
							    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
							    	  dt.setTargetTypeCd("Template");
							    	  dt.setBusinessObjLocalId(null);	
							    	  dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);	
							    	//  edxActivityLogDAOImpl.insertEDXActivityLog(dt);
								      return dt;

								   }
								
							}else{
								logger.error("SQLException  = "+e.getMessage(), e);
							}
							
						}

	  		       }//for ends	
	  		       s=0;
	  		      }else{//i==2,1,0 starts
	  		       String insertSql = pairs.getValue().toString();
	  		      
	  		    	 insertSql=  JspBeautifier.replace(insertSql,"ReplaceWithCurrentDate", "GETDATE()");
			  		 insertSql=  JspBeautifier.replace(insertSql,"ReplaceCurrentUserId", currentUser.toString());//SetPublishIndCdtoF
			  		 if(i==1)
			  		 insertSql=  JspBeautifier.replace(insertSql,"SetPublishIndCdtoF", "'F'");
			  		 if(i<2)
			  		 insertSql=  JspBeautifier.replace(insertSql,"ReplaceSrcName", "'"+propertyUtil.getMsgSendingFacility()+"'");
			  		  if(i>=2){					  			
				  	    	// if(Uidpairs.getKey().equals(pairs.getKey())){
				  	    		insertSql=	JspBeautifier.replace(insertSql,"ReplaceTemplateUid", templateUid); 
				  	    		//break;
				  	    	//  }	    	
				  	     	  			
			  		//}
			  		    if(i>2){
			  		    	Iterator<?> UidMap = MetaUidMap.entrySet().iterator();
						  	    while (UidMap.hasNext()) {
						  	    	 Map.Entry<?,?> Uidpairs = (Map.Entry<?,?>)UidMap.next();
						  	    	 if(Uidpairs.getKey().equals(pairs.getKey())){
						  	    		insertSql=	JspBeautifier.replace(insertSql,"ReplaceWaUiMetadataUid", Uidpairs.getValue().toString()); 
						  	    		break;
						  	    	 }  	    	
						  	    }		    	
			  		    }
			  		}
			  		  if(i==0 || i==2){
			  			 
			  			  if(insertSql.indexOf("$$CDSETNM")!= -1){
			  			  String  codeSetNm = insertSql.substring(insertSql.indexOf("$$CDSETNM")+9,insertSql.indexOf("##CDSETNM"));				  			
			  			  Iterator<?> it1 = codeSetNmCollMap.entrySet().iterator();
	                    	while(it1.hasNext()){
	                    		 Map.Entry<?,?> pairs1 = (Map.Entry<?,?>)it1.next();
	                    		 if(pairs1.getKey().toString().equals(codeSetNm)){	                    			 
	                    			 insertSql= JspBeautifier.replace(insertSql,"$$CDSETNM"+codeSetNm+"##CDSETNM", pairs1.getValue().toString()); 
	                    			 break;
	                    		 }
	                    	}
			  			  }
			  			  
			  			if(insertSql.indexOf("$$CDSETNM")!= -1){
				  			  String  codeSetNm = insertSql.substring(insertSql.indexOf("$$CDSETNM")+9,insertSql.indexOf("##CDSETNM"));				  			
				  			  Iterator<?> it1 = newlyAddedCodeSet.entrySet().iterator();
		                    	while(it1.hasNext()){
		                    		 Map.Entry<?,?> pairs1 = (Map.Entry<?,?>)it1.next();
		                    		 if(pairs1.getKey().toString().equals(codeSetNm)){	                    			 
		                    			 insertSql= JspBeautifier.replace(insertSql,"$$CDSETNM"+codeSetNm+"##CDSETNM", pairs1.getValue().toString()); 
		                    			 break;
		                    		 }
		                    	}
				  			  }
			  			  
			  		  }
			  		    
					try{
			  		preparedStmt = dbConnection.prepareStatement(insertSql);					
					int res = preparedStmt.executeUpdate();
					//closeResultSet(rs);				
					closeStatement(preparedStmt);
					if(i==0){
					   ddt = new EDXActivityDetailLogDT();
					   ddt.setEdxActivityLogUid(activityLogUid);
					   ddt.setRecordId(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":QT:")));
					   ddt.setRecordType(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":QT:")+4,pairs.getKey().toString().indexOf(":QN:")));
					   ddt.setRecordName(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":QN:")+4));
					   ddt.setLogType("Question");
					   ddt.setComment("New (Imported)");
					   eDXActivityLogDTDetails.add(ddt);
					  }
					}catch(SQLException e){
						 closeStatement(preparedStmt);
						if(i==0){
							if(e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint") ){
								 
								   logger.warn("unique key constraint while adding a record into wa_question  table "+insertSql);
								   ddt = new EDXActivityDetailLogDT();
								   ddt.setEdxActivityLogUid(activityLogUid);
								   ddt.setRecordId(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":QT:")));
								   ddt.setRecordType(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":QT:")+4,pairs.getKey().toString().indexOf(":QN:")));
								   ddt.setRecordName(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":QN:")+4));
								   ddt.setLogType("Question");
								   ddt.setComment("Existing (Not Imported)");
								   eDXActivityLogDTDetails.add(ddt);
							       continue;
							   }
							   else	{   
								   e.printStackTrace(myPrinter);
				                    String stackTraceStr = writerStr.toString();
							   logger.fatal("fatal error occured while inserting following code set to wa_question table. rolling back the whole transaction "+e.getMessage(), e);
							   dt.setEdxActivityLogUid(activityLogUid);   
							   dt.setSourceUid(null);
						    	  dt.setTargetUid(null);
						    	  dt.setDocType("Template");
						    	  dt.setDocName(templateNm);
						    	  dt.setRecordStatusCd("Failure");
						    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
						    	  dt.setException("fatal error occured while inserting following sql statement "+ insertSql +" to wa_question table. Exception occurred is : " +stackTraceStr);
						    	 
						    	  dt.setImpExpIndCd("I");
						    	  dt.setSourceTypeCd(null);
						    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
						    	  dt.setTargetTypeCd("Template");
						    	  dt.setBusinessObjLocalId(null);	
						    	  dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);	
						    	//  edxActivityLogDAOImpl.insertEDXActivityLog(dt);
							   return dt;

							   }
							
						}else{
							logger.error("SQLException = "+e.getMessage(), e);
						}
						if(i==1){
							if(e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")){
								   //closeStatement(preparedStmt);
								   logger.warn("unique key constraint while adding a record into wa_template  table "+insertSql);
							       
								   continue;
							   }
							   else	{  
								   e.printStackTrace(myPrinter);
				                    String stackTraceStr = writerStr.toString();
							   logger.fatal("fatal error occured while inserting following code set to wa_template table. rolling back the whole transaction "+e.getMessage(), e);
							   dt.setEdxActivityLogUid(activityLogUid);  
							   dt.setSourceUid(null);
						    	  dt.setTargetUid(null);
						    	  dt.setDocType("Template");
						    	  dt.setDocName(templateNm);
						    	  dt.setRecordStatusCd("Failure");
						    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
						    	  dt.setException("fatal error occured while inserting following sql statement "+ insertSql +" to wa_template table. Exception occurred is : " +stackTraceStr);
						    	  dt.setImpExpIndCd("I");
						    	  dt.setSourceTypeCd(null);
						    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
						    	  dt.setTargetTypeCd("Template");
						    	  dt.setBusinessObjLocalId(null);	
						    	  dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);	
						    	//  edxActivityLogDAOImpl.insertEDXActivityLog(dt);
							   return dt;

							   }
							
						}else{
							logger.error("SQLException = "+e.getMessage(), e);
						}
						if(i==2){
							
							if(e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")){
								   logger.warn("unique key constraint while adding a record into wa_ui_metadata  table "+insertSql);
								   UiInsert = false;
								   continue;
							   }
							   else	{   
								   e.printStackTrace(myPrinter);
				                    String stackTraceStr = writerStr.toString();
							   logger.fatal("fatal error occured while inserting following code set to wa_ui_metadata table. rolling back the whole transaction "+e.getMessage(), e);
							   dt.setEdxActivityLogUid(activityLogUid);   
							   dt.setSourceUid(null);
						    	  dt.setTargetUid(new Long(templateUid));
						    	  dt.setDocType("Template");
						    	  dt.setDocName(templateNm);
						    	  dt.setRecordStatusCd("Failure");
						    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
						    	  dt.setException("fatal error occured while inserting following sql statement "+ insertSql +" to wa_ui_metadata table. Exception occurred is : " +stackTraceStr);
						    	  dt.setImpExpIndCd("I");
						    	  dt.setSourceTypeCd(null);
						    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
						    	  dt.setTargetTypeCd("Template");
						    	  dt.setBusinessObjLocalId(null);	
						    	  dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);	
						    	 // edxActivityLogDAOImpl.insertEDXActivityLog(dt);
							   return dt;

							   }							
							
						}else{
							logger.error("SQLException = "+e.getMessage(), e);
						}
						
					}
	  		     }//i=2 ends
	  		    }// i!=5 ends
				if(i==2 && UiInsert){
					
					 //Connection newdbConnection = null;
					
					 Long waUimetadataUid = null;
					 try{
					 //newdbConnection = getConnection();
					 newstatement = dbConnection.createStatement();
					 newstatement.execute("select max(wa_ui_metadata_uid) from wa_ui_metadata");
					 newresultSet = newstatement.getResultSet();
					 while (newresultSet.next()) {
						 waUimetadataUid = new Long(newresultSet.getLong(1));
					 }
					 }catch(SQLException e){
						 logger.fatal("SQLException  = "+e.getMessage(), e);
						 e.printStackTrace(myPrinter);
		                    String stackTraceStr = writerStr.toString();
						 logger.fatal("error occured while fetching wa_ui_metadata_uid  during the import process. rolling back the whole process");
						 newstatement.close();
						 //newresultSet.close();
						 dt.setEdxActivityLogUid(activityLogUid);
						  dt.setSourceUid(null);
				    	  dt.setTargetUid(new Long(templateUid));
				    	  dt.setDocType("Template");
				    	  dt.setDocName(templateNm);
				    	  dt.setRecordStatusCd("Failure");
				    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
				    	  dt.setException("error occured while fetching wa_ui_metadata_uid  during the import process. Exception occurred is : " +stackTraceStr);
				    	  dt.setImpExpIndCd("I");
				    	  dt.setSourceTypeCd(null);
				    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
				    	  dt.setTargetTypeCd("Template");
				    	  dt.setBusinessObjLocalId(null);	
				    	  dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);	
				    	 // edxActivityLogDAOImpl.insertEDXActivityLog(dt);
						 return dt;
					 }
					// releaseConnection(dbConnection);
					 newstatement.close();
					 newresultSet.close();
					
					MetaUidMap.put(pairs.getKey(), waUimetadataUid);						
				}
				if(i==1){
					
					 //Connection newdbConnection = null;
					
					 Long waTemplateUid = null;
					
					 //newdbConnection = getConnection();
					 try{
					 newstatement = dbConnection.createStatement();
					 newstatement.execute("select max(wa_template_uid) from wa_template");
					 newresultSet = newstatement.getResultSet();
					 while (newresultSet.next()) {
						 templateUid = new Long(newresultSet.getLong(1)).toString();
					 }
					 }catch(SQLException e){
						  logger.fatal("SQLException  = "+e.getMessage(), e);
						  e.printStackTrace(myPrinter);
		                    String stackTraceStr = writerStr.toString();
						  logger.fatal("error occured while fetching template uid during the import process. rolling back the whole process");
						  newstatement.close();
						  dt.setEdxActivityLogUid(activityLogUid);
						  dt.setSourceUid(null);
				    	  dt.setTargetUid(null);
				    	  dt.setDocType("Template");
				    	  dt.setDocName(templateNm);
				    	  dt.setRecordStatusCd("Failure");
				    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
				    	  dt.setException("error occured while fetching template uid  during the import process. Exception occurred is : " +stackTraceStr);
				    	  dt.setImpExpIndCd("I");
				    	  dt.setSourceTypeCd(null);
				    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
				    	  dt.setTargetTypeCd("Template");
				    	  dt.setBusinessObjLocalId(null);	
				    	  dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);	
				    	//  edxActivityLogDAOImpl.insertEDXActivityLog(dt);
						 //newresultSet.close();
						 return dt;
						 
					 }
					// releaseConnection(dbConnection);
					 newstatement.close();
					 newresultSet.close();
					
					 					
				}
	  	    }//while ends
	  	  UiInsert = true;
			
	      }//outer try ends
	      }catch (Exception se) {
	    	  	 logger.fatal("Exception  = "+se.getMessage(), se);
	    	     se.printStackTrace(myPrinter);
                 String stackTraceStr = writerStr.toString();
	    	      dt.setEdxActivityLogUid(activityLogUid);
	    	      dt.setSourceUid(null);
		    	  dt.setTargetUid(null);
		    	  dt.setDocType("Template");
		    	  dt.setDocName(templateNm);
		    	  dt.setRecordStatusCd("Failure");
		    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
		    	  dt.setException("error occured  during the import process. Exception occurred is : " +stackTraceStr);
		    	  dt.setImpExpIndCd("I");
		    	  dt.setSourceTypeCd(null);
		    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
		    	  dt.setTargetTypeCd("Template");
		    	  dt.setBusinessObjLocalId(null);	
		    	  dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);	
		    	//  edxActivityLogDAOImpl.insertEDXActivityLog(dt);
	    	  return dt;  
							 
		}         
     finally {
			//closeResultSet(rs);
    	
    	 closeResultSet(newresultSet);
    	 closeStatement(newstatement) ;
		 closeStatement(preparedStmt);
		 releaseConnection(dbConnection);
	}
      dt.setEdxActivityLogUid(activityLogUid);   	 ;
	  dt.setTargetUid(new Long(templateUid));
	  dt.setDocType("Template");
	  dt.setDocName(templateNm);
	  dt.setRecordStatusCd("Success");
	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
	  dt.setException(null);
	  dt.setImpExpIndCd("I");
	  dt.setSourceTypeCd(null);
	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
	  dt.setTargetTypeCd("Template");
	  dt.setBusinessObjLocalId(null);    
   
      dt.setSourceUid(new Long(templateUid));
      
      dt.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);	         
	 // edxActivityLogDAOImpl.insertEDXActivityLog(dt);
      
     return dt;
}
	
	  /**
	 * Returns Collection of WaTemplateDT's
	 * 
	 * @param waQuestionIdentifier
	 * @return WaQuestionDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */	
	
	public EDXActivityLogDT  InsertVocabSql(ArrayList<Object> aList,String templateUid,Long currentUser,Long activityLogUid, String templateNm, Map<Object, Object> codeSetNmMap)
	throws NEDSSDAOSysException,NEDSSSystemException {
   
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		EDXActivityLogDT dt = new EDXActivityLogDT();	
		EDXActivityDetailLogDT ddt = new EDXActivityDetailLogDT();
		Collection<Object> eDXActivityLogDTDetails = new ArrayList<Object>();
		StringWriter writerStr = new StringWriter();
        PrintWriter myPrinter = new PrintWriter(writerStr);
        Statement newstatement = null;
		ResultSet newresultSet = null;
		Map<Object,Object> newlyAddedCodeSet = new HashMap<Object,Object>();
	
		
		try {
			dbConnection = getConnection(NEDSSConstants.SRT);
			
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for InsertVocabSql : TemplatesDAOImpl"+nsex.getMessage(), nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
	      
	      try {
	    	  
	    	    
				 Long cdGrpMetadataID = null;
				
				 //newdbConnection = getConnection();
				 newstatement = dbConnection.createStatement();
				 newstatement.execute("select max(code_set_group_id) from codeset_group_metadata nolock");
				 newresultSet = newstatement.getResultSet();
				 while (newresultSet.next()) {
					 cdGrpMetadataID = new Long(newresultSet.getLong(1));
				 }
				// releaseConnection(dbConnection);
				 newresultSet.close();
				 newstatement.close();
				 
	    	
	    	 Map<?,?> cdMap = ((Map<?, ?>) aList.get(0));
	    	 Map<?,?> cdGrpMetaMap = ((Map<?, ?>) aList.get(1));
	    	 Map<?,?> cdValGen = ((Map<?, ?>) aList.get(2));
	    	 int i=0;
	    	
	    	 //for(int i=0;i<cdMap.size();i++) {
		    	 Iterator<?> it = ((Map<?, ?>) aList.get(1)).entrySet().iterator();//codeset collection 
	    	 
			  	    while (it.hasNext()) {
			  	    	boolean vocabExists = false;
			  	        Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
			  	      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
			  		    if (pairs == null) {
			  		   		logger.warn("Marshaller: Unexpected null ? ");
			  	    		break;
			  		    }
			  		    String insertSql = pairs.getValue().toString();
			  		    
			  		  insertSql=  JspBeautifier.replace(insertSql,"ReplaceWithCurrentDate", "GETDATE()");  
			  		
			  		  cdGrpMetadataID = cdGrpMetadataID+10;
			  		 insertSql=  JspBeautifier.replace(insertSql,"ReplaceWithCodeSetGroupID", cdGrpMetadataID.toString());
			  		 insertSql=  JspBeautifier.replace(insertSql,"ReplaceCurrentUserId", currentUser.toString());
			  		
					
					try{
						if(pairs.getKey()!=null){
							String codeSetNm = pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:"));
				  		    if(codeSetNmMap.containsKey(codeSetNm)){
				  		    	throw new SQLException("duplicate key");
				  		    }
						}
			  		    	
						preparedStmt = dbConnection.prepareStatement(insertSql);
					    int res = preparedStmt.executeUpdate();	
					    closeStatement(preparedStmt);
					
					    Iterator<?> it1 = ((Map<?, ?>) aList.get(0)).entrySet().iterator();//codeset_group_metadata collection
			    	 
					  	    while (it1.hasNext()) {
					  	        Map.Entry<?,?> pairs1 = (Map.Entry<?,?>)it1.next();
					  	      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
					  		    if (pairs1 == null) {
					  		   		logger.warn("Marshaller: Unexpected null ? ");
					  	    		break;
					  		    }
					  		    if(pairs1.getKey().equals(pairs.getKey())){
					  		    insertSql = pairs1.getValue().toString();
					  		  
					  			insertSql=  JspBeautifier.replace(insertSql,"ReplaceWithCurrentDate", "GETDATE()");	  
					  		    insertSql=  JspBeautifier.replace(insertSql,"ReplaceWithCodeSetGroupID", cdGrpMetadataID.toString());
					  		    insertSql=  JspBeautifier.replace(insertSql,"ReplaceCurrentUserId", currentUser.toString());
					  		
							     preparedStmt = dbConnection.prepareStatement(insertSql);
						         try{
							     	 res = preparedStmt.executeUpdate();
							     	closeStatement(preparedStmt);
							     	
							     	Iterator<?> it2 = ((Map<?, ?>) aList.get(2)).entrySet().iterator();//code_value_general collection
							    	 
							  	    while (it2.hasNext()) {
							  	        Map.Entry<?,?> pairs2 = (Map.Entry<?,?>)it2.next();
							  	      //  System.out.println(pairs.getKey() + " = " + pairs.getValue());
							  		    if (pairs2 == null) {
							  		   		logger.warn("Marshaller: Unexpected null ? ");
							  	    		break;
							  		    }				  		    

							  		    if(pairs1.getKey().equals(pairs2.getKey())){    
								  		String insertSql1[] = (String[])pairs2.getValue();
								  		String sql="";
									  		for(int len =0;len< insertSql1.length;len++){  
									  			sql=  JspBeautifier.replace(insertSql1[len],"ReplaceWithCurrentDate", "GETDATE()");		 
									  			sql=  JspBeautifier.replace(sql,"ReplaceCurrentUserId", currentUser.toString());
									  		
										     	preparedStmt = dbConnection.prepareStatement(sql);
											   try{
											     	 res = preparedStmt.executeUpdate();
											     	 closeStatement(preparedStmt);
											   }catch(SQLException e){
												   closeStatement(preparedStmt);
												   if(e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")){
													   logger.warn("unique key constraint while adding following code set to code_value_general table "+sql);
													   ddt = new EDXActivityDetailLogDT();
													   ddt.setEdxActivityLogUid(activityLogUid);
													   ddt.setRecordId(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:")));
													   ddt.setRecordName(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":VSN:")+5,pairs.getKey().toString().indexOf(":CT:")));
													   ddt.setRecordType(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":CT:")+4));
													   ddt.setLogType("Vocab");
													   ddt.setComment("Existing (Not Imported)");
													   eDXActivityLogDTDetails.add(ddt);
													   vocabExists  = true;
												       break;
												   }
												   else	{   
													   
									                    e.printStackTrace(myPrinter);
									                    String stackTraceStr = writerStr.toString();

												   logger.fatal("fatal error occured while inserting following code set to code_value_general table. rolling back the whole transaction "+e.getMessage(), e);
												      dt.setEdxActivityLogUid(activityLogUid);
											    	  dt.setSourceUid(null);
											    	  dt.setTargetUid(null);
											    	  dt.setDocType("Template");
											    	  dt.setDocName(templateNm);
											    	  dt.setRecordStatusCd("Failure");
											    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
											    	  dt.setException("fatal error occured while inserting following sql statement "+ sql +" to code_value_general table. Exception occurred is : " +stackTraceStr);
											    	  dt.setImpExpIndCd("I");
											    	  dt.setSourceTypeCd(null);
											    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
											    	  dt.setTargetTypeCd("Template");
											    	  dt.setBusinessObjLocalId(null);	
											    	  dt.setEDXActivityLogDTWithVocabDetails(eDXActivityLogDTDetails);
											    	//  edxActivityLogDAOImpl.insertEDXActivityLog(dt);
												   return dt;

												   }													
													
												}
											  // break;
											
									  		}
									  		break;
							  	    	}
								
						  	       }
						         }catch(SQLException e){
						        	 closeStatement(preparedStmt);
									   if(e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")){
										   logger.warn("unique key constraint while adding following code set to codeset table "+insertSql);
										   ddt = new EDXActivityDetailLogDT();
										   ddt.setEdxActivityLogUid(activityLogUid);
										   ddt.setRecordId(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:")));
										   ddt.setRecordName(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":VSN:")+5,pairs.getKey().toString().indexOf(":CT:")));
										   ddt.setRecordType(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":CT:")+4));
										   ddt.setLogType("Vocab");
										   ddt.setComment("Existing (Not Imported)");
										   eDXActivityLogDTDetails.add(ddt);
										   vocabExists  = true;
										   break;
									   }
									   else	{   
										     e.printStackTrace(myPrinter);
						                    String stackTraceStr = writerStr.toString();
									   logger.fatal("fatal error occured while inserting following code set to codeset_group_metadata table. rolling back the whole transaction "+e.getMessage(), e);
									      dt.setEdxActivityLogUid(activityLogUid);
								    	  dt.setSourceUid(null);
								    	  dt.setTargetUid(null);
								    	  dt.setDocType("Template");
								    	  dt.setDocName(templateNm);
								    	  dt.setRecordStatusCd("Failure");
								    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
								    	  dt.setException("fatal error occured while inserting following sql statement "+ insertSql +" to codeset_group_metadata table. Exception occurred is : " +stackTraceStr);
								    	  dt.setImpExpIndCd("I");
								    	  dt.setSourceTypeCd(null);
								    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
								    	  dt.setTargetTypeCd("Template");
								    	  dt.setBusinessObjLocalId(null);	
								    	  dt.setEDXActivityLogDTWithVocabDetails(eDXActivityLogDTDetails);	
								    	// edxActivityLogDAOImpl.insertEDXActivityLog(dt);
									   return dt;

									   }													
										
									}
							     break;
					  		    }
							
			  	           }
					  	    
					  	  
					}catch(SQLException e){
						closeStatement(preparedStmt);
						   if(e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")){
							   logger.warn("unique key constraint while adding following code set to codeset_group_metadata table "+insertSql);
							   ddt = new EDXActivityDetailLogDT();
							   ddt.setEdxActivityLogUid(activityLogUid);
							   ddt.setRecordId(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:")));
							   ddt.setRecordName(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":VSN:")+5,pairs.getKey().toString().indexOf(":CT:")));
							   ddt.setRecordType(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":CT:")+4));
							   ddt.setLogType("Vocab");
							   ddt.setComment("Existing (Not Imported)");
							   eDXActivityLogDTDetails.add(ddt);
							   vocabExists  = true;
						       continue;
						   }
						   else	{  
							   e.printStackTrace(myPrinter);
			                    String stackTraceStr = writerStr.toString();
						   logger.fatal("fatal error occured while inserting following code set to codeset_group_metadata table. rolling back the whole transaction "+e.getMessage(), e);
						     dt.setEdxActivityLogUid(activityLogUid);
					    	  dt.setSourceUid(null);
					    	  dt.setTargetUid(null);
					    	  dt.setDocType("Template");
					    	  dt.setDocName(templateNm);
					    	  dt.setRecordStatusCd("Failure");
					    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
					    	  dt.setException("fatal error occured while inserting following sql statement "+ insertSql +" to codeset table. Exception occurred is : " +stackTraceStr);
					    	  dt.setImpExpIndCd("I");
					    	  dt.setSourceTypeCd(null);
					    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
					    	  dt.setTargetTypeCd("Template");
					    	  dt.setBusinessObjLocalId(null);	
					    	  dt.setEDXActivityLogDTWithVocabDetails(eDXActivityLogDTDetails);
					    	 // edxActivityLogDAOImpl.insertEDXActivityLog(dt);
						   return dt;

						   }													
							
						}
						   if(!vocabExists){
					       ddt = new EDXActivityDetailLogDT();
						   ddt.setEdxActivityLogUid(activityLogUid);
						   /*ddt.setRecordId(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":QT:")));
						   ddt.setRecordName(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":QT:")+4,pairs.getKey().toString().indexOf(":QN:")));
						   ddt.setRecordType(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":QN:")+4));*/
						   ddt.setRecordId(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:")));
						   ddt.setRecordName(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":VSN:")+5,pairs.getKey().toString().indexOf(":CT:")));
						   ddt.setRecordType(pairs.getKey().toString().substring(pairs.getKey().toString().indexOf(":CT:")+4));
						   ddt.setLogType("Vocab");
						   ddt.setComment("New (Imported)");
						   eDXActivityLogDTDetails.add(ddt);
						   }
						   vocabExists = false;
						   newlyAddedCodeSet.put(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:")), cdGrpMetadataID);
						   codeSetNmMap.put(pairs.getKey().toString().substring(0, pairs.getKey().toString().indexOf(":VSN:")), cdGrpMetadataID);
						   closeStatement(preparedStmt);
						   
	                
			  	    }			     
	      }catch (Exception se) {
    	  logger.error("fatal error occured while inserting the vocab. rolling back the whole transaction "+se.getMessage(), se);
    	  se.printStackTrace(myPrinter);
          String stackTraceStr = writerStr.toString();
	    	  dt.setEdxActivityLogUid(activityLogUid);
	    	  dt.setSourceUid(null);
	    	  dt.setTargetUid(null);
	    	  dt.setDocType("Template");
	    	  dt.setDocName(templateNm);
	    	  dt.setRecordStatusCd("Failure");
	    	  dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
	    	  dt.setException("fatal error occured while inserting the vocab. Exception occurred is : " +stackTraceStr);
	    	  dt.setImpExpIndCd("I");
	    	  dt.setSourceTypeCd(null);
	    	  dt.setSrcName(propertyUtil.getMsgSendingFacility());
	    	  dt.setTargetTypeCd("Template");
	    	  dt.setBusinessObjLocalId(null);	
	    	  dt.setEDXActivityLogDTWithVocabDetails(eDXActivityLogDTDetails);	
	    	//  edxActivityLogDAOImpl.insertEDXActivityLog(dt);
	    	  return dt;
			
		}         
     finally {
    	    closeResultSet(newresultSet);
			closeResultSet(rs);	
    	    closeStatement(newstatement);
    	    closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
     dt.setEDXActivityLogDTWithVocabDetails(eDXActivityLogDTDetails);
     dt.setNewaddedCodeSets(newlyAddedCodeSet);
     return dt;
}
	
	/**
	 * Returns Collection of WaTemplateDT's
	 * 
	 * @param waQuestionIdentifier
	 * @return WaQuestionDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */	
	
	public Collection<Object>  fetchQIdentifiers4Template(String templateUid)
	throws NEDSSDAOSysException,NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		String sqlQuery = null;
		ArrayList<Object> waUiMetadataDTCollection = new ArrayList<Object>();	
	
	
		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for  fetchQIdentifiers4Template  : TemplatesDAOImpl"+nsex.getMessage(), nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
	      //  logic to check if code has seperate table
	        sqlQuery = " Select question_identifier,code_set_group_id from wa_ui_metadata where wa_template_uid = ?";
	      try {
			preparedStmt = dbConnection
					.prepareStatement(sqlQuery);
			preparedStmt.setLong(1, Long.valueOf(templateUid).longValue());
			resultSet = preparedStmt.executeQuery();
			
			
			
			if(resultSet!=null){
			resultSetMetaData = resultSet.getMetaData();
			 while (resultSet.next()){
				 WaUiMetadataDT dt = new WaUiMetadataDT();
				 dt.setQuestionIdentifier(resultSet.getString("question_identifier"));
				 if(resultSet.getString("code_set_group_id")!= null)
				 dt.setCodeSetGroupId(new Long(resultSet.getString("code_set_group_id")));
				 waUiMetadataDTCollection.add(dt);
	
			     logger.debug("returned questions list");
			}
			}
			return waUiMetadataDTCollection;
		} catch (SQLException se) {
			logger
			.fatal(
					"Error in result set handling while selecting jsp files: TemplatesDAOImpl.",
					se.getMessage(), se);
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "fetchQIdentifiers4Template: TemplatesDAOImpl " +se.getMessage(), se);
		} catch (Exception reuex) {
			logger
					.fatal(
							"Error in result set handling while selecting jsp files: TemplatesDAOImpl."+reuex.getMessage(),
							reuex);
			throw new NEDSSDAOSysException(reuex.getMessage(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
}

	/**
	 * Returns Collection of WaTemplateDT's
	 * 
	 * @param waQuestionIdentifier
	 * @return WaQuestionDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */	
	
	public int  checkforTemplateNm(Object temMap)
	throws NEDSSDAOSysException,NEDSSSystemException {

		
		String templateName="";
		
		 int count = 0;
		 Integer intCount = null;
			String codeSql = "";
			ArrayList<Object>  paramList = new ArrayList<Object> ();
			WaTemplateDT dt = new WaTemplateDT();
	
		try{
		Iterator<?> it = ((Map<?, ?>) temMap).entrySet().iterator();
  	    while (it.hasNext()) {
  	        Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
  		    if (pairs == null) {
  		   		logger.warn("Marshaller: Unexpected null ? ");
  	    		break;
  		    }  
  		  if(pairs.getKey()!= null ) 
  		  templateName = pairs.getKey().toString();
  	    }		
		 codeSql = "select count(*) from wa_template where template_nm  = ? ";
		 paramList.add(templateName);
		 intCount =  (Integer) preparedStmtMethod(dt, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
		 count =intCount.intValue();
		
         return count;
		} catch (Exception se) {
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "jsp files collection: TemplatesDAOImpl " +se.getMessage(), se);
		} 
		 finally {					 
			
		}
}
	
	public Map<Object,Object> getCodeSetNmColl() throws NEDSSDAOSysException, NEDSSSystemException {

		Connection dbConnection = null;
	    PreparedStatement preparedStmt = null;
	    ResultSet resultSet = null;
	    String conSrtNm = null;
	    Map<Object,Object> codeSetNmColl = new HashMap<Object,Object>();

	    try
	    {
	    	String codeSql =SELECT_CODE_SET_NM_COLL_SQL;
		    dbConnection = getConnection();
	        preparedStmt = dbConnection.prepareStatement(codeSql);
	        
	        resultSet = preparedStmt.executeQuery();

	        
	        while (resultSet.next())
	        {
	        	codeSetNmColl.put(resultSet.getString(1), resultSet.getString(2)) ;
	        }
	    }

	    catch (Exception ex) {
			logger.fatal("Exception in getCodeSetNmColl: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	    finally
	    {
	        closeResultSet(resultSet);
	        closeStatement(preparedStmt);
	        releaseConnection(dbConnection);
	    }
		return codeSetNmColl;

		} //getConditionShortNmCollection
	
	/**
	 * Returns Collection of WaTemplateDT's
	 * 
	 * @param waQuestionIdentifier
	 * @return WaQuestionDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */	
	
	public ArrayList<Object>  fetchAdditonalCodeSets(String templateUid)
	throws NEDSSDAOSysException,NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		String sqlQuery = null;
		ArrayList<Object> CodeSetColl = new ArrayList<Object>();	
		Long templateID = Long.valueOf(templateUid);
	
		//nbsPageDTCollection.add(invFormCd);
	
		try {
			dbConnection = getConnection();
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection "
					+ "for  fetchQIdentifiers4Template  : TemplatesDAOImpl"+nsex.getMessage(), nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
	      //  logic to check if code has seperate table
	      sqlQuery =  "Select code_set_group_id from wa_question where question_identifier in(select question_identifier from wa_ui_metadata where wa_template_uid = ?) EXCEPT  select code_set_group_id from wa_ui_metadata where wa_template_uid = ?";
	      try {
			preparedStmt = dbConnection
					.prepareStatement(sqlQuery);
			preparedStmt.setLong(1,templateID.longValue());
			preparedStmt.setLong(2,templateID.longValue());
			resultSet = preparedStmt.executeQuery();
			
			if(resultSet!=null){
			resultSetMetaData = resultSet.getMetaData();
			 while (resultSet.next()){
				
				 CodeSetColl.add(new Long(resultSet.getString("code_set_group_id")));
	
			     logger.debug("returned questions list");
			  }
			}
			return CodeSetColl;
		} catch (SQLException se) {
			logger
			.fatal(
					"Error in result set handling while selecting jsp files: TemplatesDAOImpl.",
					se.getMessage(), se);
			throw new NEDSSDAOSysException("SQLException while selecting "
					+ "fetchAdditonalCodeSets: TemplatesDAOImpl " +se.getMessage(), se);
		} catch (Exception reuex) {
			logger
					.fatal(
							"Error in result set handling while selecting jsp files: TemplatesDAOImpl.",
							reuex);
			throw new NEDSSDAOSysException(reuex.getMessage(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
}


/**
 * Returns Collection of WaTemplateDT's
 * 
 * @param waQuestionIdentifier
 * @return WaQuestionDT
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */	

public ArrayList<Object>  fetchAdditonalCodeSetsforStructNumQs(String templateUid)
throws NEDSSDAOSysException,NEDSSSystemException {

	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	ResultSet resultSet = null;
	ResultSetMetaData resultSetMetaData = null;
	String sqlQuery = null;
	ArrayList<Object> CodeSetColl = new ArrayList<Object>();	
	Long templateId = Long.valueOf(templateUid);
	//nbsPageDTCollection.add(invFormCd);

	try {
		dbConnection = getConnection();
	} catch (NEDSSSystemException nsex) {
		logger.fatal("SQLException while obtaining database connection "
				+ "for  fetchQIdentifiers4Template  : TemplatesDAOImpl"+nsex.getMessage(), nsex);
		throw new NEDSSSystemException(nsex.getMessage(), nsex);
	}
      //  logic to check if code has seperate table
     
    	  sqlQuery =  "(select unit_value from wa_ui_metadata where wa_template_uid =  ? and unit_type_cd = 'CODED'  EXCEPT ( select code_set_group_id from wa_ui_metadata where wa_template_uid = ?  UNION Select code_set_group_id from wa_question where question_identifier in(select question_identifier from  wa_ui_metadata where wa_template_uid =  ? )))";
    	  sqlQuery = sqlQuery + " UNION (select unit_value from wa_question where wa_question_uid in (select wa_question_uid from wa_ui_metadata where wa_template_uid = ?) and unit_type_cd='CODED' except ( select unit_value from wa_ui_metadata where wa_template_uid =  ? and unit_type_cd='CODED' union  select code_set_group_id from wa_ui_metadata where wa_template_uid =  ?  UNION Select code_set_group_id from wa_question where question_identifier in (select question_identifier from  wa_ui_metadata where wa_template_uid =  ? )))";
      try {
		preparedStmt = dbConnection
				.prepareStatement(sqlQuery);
		preparedStmt.setLong(1, templateId.longValue()); 
		preparedStmt.setLong(2, templateId.longValue());
		preparedStmt.setLong(3, templateId.longValue());
		preparedStmt.setLong(4, templateId.longValue());
		preparedStmt.setLong(5, templateId.longValue());
		preparedStmt.setLong(6, templateId.longValue());
		preparedStmt.setLong(7, templateId.longValue());
		resultSet = preparedStmt.executeQuery();
		
		if(resultSet!=null){
		resultSetMetaData = resultSet.getMetaData();
		 while (resultSet.next()){
			
			 CodeSetColl.add(new Long(resultSet.getString("unit_value")));

		     logger.debug("returned questions list");
		  }
		}
		return CodeSetColl;
	} catch (SQLException se) {
		throw new NEDSSDAOSysException("SQLException while selecting "
				+ "fetchAdditonalCodeSets: TemplatesDAOImpl " +se.getMessage(), se);
	} catch (Exception reuex) {
		logger
				.fatal(
						"Error in result set handling while selecting jsp files: TemplatesDAOImpl."+reuex.getMessage(),
						reuex);
		throw new NEDSSDAOSysException(reuex.getMessage(), reuex);
	} finally {
		closeResultSet(resultSet);
		closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	}
}


	/**
	 * @param templateNm
	 * @return 
	 */
	public boolean correctMetadataAfterTemplateImport(String templateNm) throws NEDSSDAOSysException{
		logger.debug("templateNm: "+templateNm);
		Connection conn = null;
		CallableStatement cs = null;
		boolean success = false;
		try{
			conn = getConnection();
			cs = conn.prepareCall("{call CorrectMetadataForTempImport(?)}");
	        cs.setString(1, templateNm);
	        cs.execute();
	        success = true;
		}catch (Exception ex) {
			logger.fatal("Error while correcting Metadata for Template: "+templateNm+", Exception: "+ex.getMessage(),ex);
			throw new NEDSSDAOSysException(ex.toString(), ex);
		} finally {
			closeCallableStatement(cs);
			releaseConnection(conn);
		}
		return success;
	}
}
