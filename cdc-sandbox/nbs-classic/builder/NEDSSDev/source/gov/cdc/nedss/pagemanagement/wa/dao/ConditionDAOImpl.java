package gov.cdc.nedss.pagemanagement.wa.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.LdfPageSetDT;
import gov.cdc.nedss.pagemanagement.wa.dt.ConditionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;


/**
* Name:		ConditionDAO.java
* Description:	DAO for insert and update of Condition_Code table. Created to
*              support Manage Condition UI.
*              ConditionCd id the primary key for the srte.Condition_Code table.
*              It can be updated.
* Copyright:	Copyright (c) 2009
* Company: 	CSC
* @author:	Gregory Tucker
*/


public class ConditionDAOImpl extends DAOBase{

	private static final LogUtils logger = new LogUtils(ConditionDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	private static final String SELECT_PUBLISHED_CONDITION_SQL = "select wt.publish_ind_cd \"publishIndCd\" " 
	+ " from nbs_srte..condition_code cc, page_cond_mapping pcm, wa_template wt "
 + "where cc.condition_cd = pcm.condition_cd and pcm.wa_template_uid = wt.wa_template_uid and wt.publish_ind_cd='T' and cc.condition_cd=?";

	private static final String SELECT_PUBLISHED_CONDITION_ORA = "select wt.publish_ind_cd \"publishIndCd\" " 
			+ " from  nbs_srte.condition_code cc, page_cond_mapping pcm, wa_template wt "
		 + "where cc.condition_cd = pcm.condition_cd and pcm.wa_template_uid = wt.wa_template_uid and wt.publish_ind_cd='T' and cc.condition_cd=?";

	private static final String SELECT_CONDITION_SQL ="SELECT  cc.condition_cd \"conditionCd\" "
		+", condition_codeset_nm \"conditionCodesetNm\" "
		+",condition_seq_num \"conditionSeqNum\""
		+",assigning_authority_cd \"assigningAuthorityCd\""
		+",assigning_authority_desc_txt \"assigningAuthorityDescTxt\""
		+",code_system_cd \"codeSystemCd\""
		+",code_system_desc_txt \"codeSystemDescTxt\""
		+",condition_desc_txt \"conditionDescTxt\""
		+",condition_short_nm \"conditionShortNm\""
		+",effective_from_time \"effectiveFromTime\""
		+",effective_to_time \"effectiveToTime\" "
		+",indent_level_nbr \"indentLevelNbr\""
		+",investigation_form_cd \"investigationFormCd\""
		+",is_modifiable_ind \"isModifiableInd\""
		+", nbs_uid \"nbsUid\""
		+", nnd_ind \"nndInd\""
		+",parent_is_cd \"parentIsCd\""
		+",prog_area_cd \"progAreaCd\""
		+",reportable_morbidity_ind \"reportableMorbidityInd\""
		+",reportable_summary_ind \"reportableSummaryInd\""
		+",status_cd \"statusCd\" "
		+",status_time \"statusTime\" "
		+",nnd_entity_identifier \"nndEntityId\""
		+",nnd_summary_entity_identifier \"nndSummaryEntityIdentifier\" "
		+",summary_investigation_form_cd \"summaryInvestigationFormCd\""
		+",contact_tracing_enable_ind \"contactTracingEnableInd\" "
		+",PORT_REQ_IND_CD \"PortReqIndCd\" "
		+",family_cd \"familyCd\" "
		+",coinfection_grp_cd \"coInfGroup\" "
		+",isnull(cond_mapping.template_nm,cc.investigation_form_cd) \"pageNm\" "
		+"from nbs_srte..condition_code cc left outer join (select  wt.template_nm, pcm.condition_cd " 
		+"from wa_template wt, page_cond_mapping pcm "  
		+"where wt.wa_template_uid = pcm.wa_template_uid "
		+"and pcm.add_time in(select min(add_time) from page_cond_mapping group by condition_cd) "
		+"group by template_nm,pcm.condition_cd) cond_mapping on "
		+"cc.condition_cd = cond_mapping.condition_cd ORDER BY condition_short_nm";
	
	private static final String CREATE_CONDITION_SQL = "INSERT INTO  nbs_srte..Condition_Code(";

	private static final String CREATE_CONDITION_ORA = "INSERT INTO  nbs_srte.Condition_Code(";

	private static final String CREATE_CONDITION =
		" condition_cd "
		+",condition_codeset_nm "
		+",condition_seq_num "
		+",assigning_authority_cd "
		+",assigning_authority_desc_txt "
		+",code_system_cd "
		+",code_system_desc_txt "
		+",condition_desc_txt "
		+",condition_short_nm "
		+",effective_from_time "
		+",effective_to_time "
		+",indent_level_nbr "
		+",investigation_form_cd "
		+",is_modifiable_ind "
		+",nbs_uid "
		+",nnd_ind "
		+",parent_is_cd "
		+",prog_area_cd "
		+",reportable_morbidity_ind "
		+",reportable_summary_ind "
		+",status_cd "
		+",status_time "
		+",nnd_entity_identifier "
		+",nnd_summary_entity_identifier "
		+",summary_investigation_form_cd "
		+",contact_tracing_enable_ind "
		+",vaccine_enable_ind "
		+",treatment_enable_ind "
		+",lab_report_enable_ind "
		+",port_req_ind_cd "
		+ ",morb_report_enable_ind "
		+ ",family_cd "
		+ ",coinfection_grp_cd) "
		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_CONDITION_SQL = "UPDATE nbs_srte..Condition_Code ";

	private static final String UPDATE_CONDITION_ORA = "UPDATE nbs_srte.Condition_Code ";

	private static final String UPDATE_CONDITION = "SET condition_desc_txt=?, "
		+ "condition_short_nm=?, "
		+ "nnd_ind=?, " + "parent_is_cd=?, "
		+ "reportable_summary_ind=?, "
		+ "reportable_morbidity_ind=?, "
		+ "status_cd=?, "
		+ "indent_level_nbr=?, "
		+ "contact_tracing_enable_ind=?, "
		+ "nnd_entity_identifier=?, "
		+ "status_time=?, "
		+ "coinfection_grp_cd=?, "
		+ "family_cd=? "
		+ "WHERE "
		+ "condition_cd=?";

	private static final String SET_CONDITION_INV_FORM_CODE_AND_NND_ENTITY_IDENTIFIER = " SET investigation_form_cd=?, nnd_entity_identifier=?,status_cd=? where condition_cd=?";

	private static final String SELECT_CONDITION_SHORT_NM_SQL = "SELECT   CONDITION_CD \"key\",  CONDITION_SHORT_NM \"value\" FROM  NBS_SRTE..CONDITION_CODE WHERE indent_level_nbr='1'AND STATUS_CD='A'";
	private static final String SELECT_CONDITION_SHORT_NM_ORA = "SELECT   CONDITION_CD \"key\",  CONDITION_SHORT_NM \"value\" FROM  NBS_SRTE.CONDITION_CODE WHERE indent_level_nbr='1'AND STATUS_CD='A'";

	private static final String SELECT_CONDITION_SHORT_NM_DESC_SQL = "SELECT   CONDITION_SHORT_NM \"value\" FROM  NBS_SRTE..CONDITION_CODE WHERE indent_level_nbr='1'AND  condition_cd=?";
	private static final String SELECT_CONDITION_SHORT_NM_DESC_ORA = "SELECT   CONDITION_SHORT_NM \"value\" FROM NBS_SRTE.CONDITION_CODE WHERE indent_level_nbr='1'AND condition_cd=?";


	private static final String SELECT_CODE_SYSTEM_SQL ="SELECT "
		+ "code_short_desc_txt \"codeSystemDescTxt\" FROM "
		+ "NBS_SRTE..CODE_VALUE_GENERAL WHERE CODE_SET_NM ='CODE_SYSTEM' "
		+"AND CODE_DESC_TXT = ?";

	private static final String SELECT_CODE_SYSTEM_ORA ="SELECT "
		+ "code_short_desc_txt \"codeSystemDescTxt\" FROM "
		+ "NBS_SRTE.CODE_VALUE_GENERAL WHERE CODE_SET_NM ='CODE_SYSTEM' "
		+"AND CODE_DESC_TXT = ?";

	private static final String UPDATE_CONDITION_STATUS = "SET status_cd=?, "
		+ "status_time=? "
		+ "WHERE "
		+ "condition_cd=?";

	private static final String SELECT_MAX_NBS_UID_SQL = "SELECT MAX(nbs_uid)+2 FROM NBS_SRTE..CONDITION_CODE";

	private static final String SELECT_COUNT_SQL = "select count(*) from  NBS_SRTE..CONDITION_CODE WHERE CONDITION_CD=? AND CONDITION_SHORT_NM=?";

	private static final String SELECT_COUNT_CD_SQL = "select count(*) from  NBS_SRTE..CONDITION_CODE WHERE CONDITION_CD=?";

	private static final String SELECT_COUNT_NM_SQL = "select count(*) from  NBS_SRTE..CONDITION_CODE WHERE CONDITION_SHORT_NM=?";

	private static final String SELECT_COUNT_NM_UPDATE_SQL = "select count(*) from  NBS_SRTE..CONDITION_CODE WHERE CONDITION_SHORT_NM=? AND CONDITION_CD<>?";


	private static final String INSERT_LDF_PAGE_SET_SQL = "INSERT INTO  NBS_SRTE..LDF_page_set(ldf_page_id,business_object_nm,condition_cd, ui_display,indent_level_nbr, parent_is_cd,code_set_nm,seq_num,code_version, nbs_uid,effective_from_time, effective_to_time, status_cd , code_short_desc_txt, display_row, display_column) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,?)";

	private static final String UPDATE_LDF_PAGE_SET_SQL = "UPDATE  NBS_SRTE..LDF_page_set SET code_short_desc_txt =? WHERE CONDITION_CD=?";

	private static final String SELECT_COUNT_PUBLISHED_PAGE_SQL = "select count(*) from wa_template WHERE  form_cd=? and TEMPLATE_TYPE in ('Published','Published With Draft')";
	
	private static final String SELECT_COND_STATUS_SQL = "select count(*) from  NBS_SRTE..CONDITION_CODE WHERE  condition_cd=? and status_cd = 'P'";
	private static final String SELECT_COND_PORTIND_SQL = "select port_req_ind_cd from  NBS_SRTE..CONDITION_CODE WHERE  condition_cd=?";
	
	
	
	/**
	 * Method: getConditionDTCollection
	 * gets the ConditionDT Collection<Object> Object for a given Condition or all conditions
	 * @return Collection<Object> of ConditionDT
	 */

@SuppressWarnings("unchecked")
public Collection<Object> getConditionDTCollection(String whereClause) throws NEDSSDAOSysException, NEDSSSystemException {


		String codeSql ="";

		logger.debug(" getting Condition Collection from DAO ..");

        codeSql = SELECT_CONDITION_SQL;


		ConditionDT conditionDT = new ConditionDT();
		ArrayList<Object>  conditionDTCollection = new ArrayList<Object> ();

		try {
			conditionDTCollection = (ArrayList<Object>) preparedStmtMethod(conditionDT, conditionDTCollection, codeSql, NEDSSConstants.SELECT);

		} catch (Exception ex) {
			logger.debug("Select SQL = " + codeSql);
			logger.fatal("Error while creating Condition Library ConditionDT = " + conditionDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

		logger.debug(" ...leaving Condition Collection from DAO ..");

		return conditionDTCollection;

	} //getConditionDTCollection



@SuppressWarnings("unchecked")
public Collection<Object> getConditionShortNmCollection() throws NEDSSDAOSysException, NEDSSSystemException {


		String codeSql =null;
		logger.debug(" getting Condition Collection from DAO ..");

        codeSql = SELECT_CONDITION_SHORT_NM_SQL;


        DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
		try {
			arrayList = (ArrayList<Object>) preparedStmtMethod(dropDownCodeDT, arrayList, codeSql, NEDSSConstants.SELECT);

		} catch (Exception ex) {
			logger.debug("Select SQL = " + codeSql);
			logger.fatal("Error on getConditionShortNmCollection() DropDownCodeDT = " + dropDownCodeDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

		logger.debug(" ...leaving Condition Collection from DAO ..");

		return arrayList;

	} //getConditionShortNmCollection


public String getConditionShortNm(String conditionCd) throws NEDSSDAOSysException, NEDSSSystemException {

	Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    String conSrtNm = null;

    try
    {
    	String codeSql =SELECT_CONDITION_SHORT_NM_DESC_SQL;
	    dbConnection = getConnection();
        preparedStmt = dbConnection.prepareStatement(codeSql);
        preparedStmt.setString(1, conditionCd);
        resultSet = preparedStmt.executeQuery();

        if (!resultSet.next())
        {
        	conSrtNm = null;
        }
        else
        {
        	conSrtNm = resultSet.getString(1);
        }
    }

    catch (Exception ex) {
		logger.fatal("Exception in getConditionShortNm: ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}
    finally
    {
        closeResultSet(resultSet);
        closeStatement(preparedStmt);
        releaseConnection(dbConnection);
    }
		logger.debug(" ...leaving Condition Collection from DAO ..");

		return conSrtNm;

	} //getConditionShortNmCollection
/**
 * Method createCondition inserts a new condition into the Condition_Code table
 * @param conditionDT
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
public Map<Object, Object> createCondition(ConditionDT conditionDT) throws NEDSSDAOSysException, NEDSSSystemException {

	logger.debug(" in Condition DAO - creating new condition ..");

	//Throw exception if duplicate combination of conditionCd and conditionNm entered
	int countRet = 0;
	int count = 0;
	Map<Object, Object> errorMap = new HashMap<Object, Object>();
	boolean isError=false;
	try{
		countRet = uniqueConditionSet(conditionDT);
		if(countRet >= 1) {
			errorMap.put(new Integer(count++), " The combination of this Condition Code and this Condition Name already exists. Please enter a unique combination of Condition Code and Condition Name to create a new record. ");
			isError=true;
		}
		//Throw exception if duplicate conditionCd is entered
		countRet = uniqueConditionCd(conditionDT);
		if(countRet >= 1)
		{
			errorMap.put(new Integer(count++), " A record already exists with this Condition Code. Please enter a unique Condition Code to create a new record. ");
		   	isError=true;
		}
		//Throw exception if duplicate condition_short_nm is entered
		countRet = uniqueConditionNm(conditionDT);
		if(countRet >= 1)
		{
			errorMap.put(new Integer(count++), " A record already exists with this Condition Name. Please enter a unique Condition Name to create a new record. ");
		   	isError=true;
		}
		if(isError!=true){
	
			//1st Insert into Condition_code table
			// get the max nbs_uid+2 of the condition_code table
			Long nbsUid = retreviveMaxNbsUidSet();
			//check if it is a parent condition_cd.
			String parentIsCd = conditionDT.getParentIsCd();
			if(parentIsCd == null)
			{
				conditionDT.setIndentLevelNbr(1);
			}
			else//This scenario should not be happening
			{
				conditionDT.setIndentLevelNbr(2);
			}
			//first part of clause differs from MSQL to Oracle
			String codeSql =CREATE_CONDITION_SQL;
		    
		    //body of select
		    codeSql = codeSql + CREATE_CONDITION;
	
		    //prepare statement
			ArrayList<Object>  paramList = new ArrayList<Object> ();
			paramList.add(conditionDT.getConditionCd()); // 1st Condition_Cd is primary key
			 //the next field is not really used .. default to meaningless PHC_TYPE
			String conditionCodesetNm = conditionDT.getConditionCodesetNm()== null ? "PHC_TYPE" : conditionDT.getConditionCodesetNm(); ;
		    paramList.add(conditionCodesetNm);//2nd
	
			String conditionSeqNum = conditionDT.getConditionSeqNum()== null ? "1" : conditionDT.getConditionSeqNum(); ;
		    paramList.add(conditionSeqNum); //3rd legacy - default to 1
	
		    //default assigning authority to CDC if not present
		    String assigingAuthorityCd = conditionDT.getAssigningAuthorityCd()== null ? "2.16.840.1.114222" : conditionDT.getAssigningAuthorityCd(); ;
		    paramList.add(assigingAuthorityCd);//4th
		    String assigingAuthorityDescTxt = conditionDT.getAssigningAuthorityDescTxt()== null ? "Centers for Disease Control" : conditionDT.getAssigningAuthorityDescTxt(); ;
		    paramList.add(assigingAuthorityDescTxt);//5th
	
			paramList.add(conditionDT.getCodeSystemCd());		 //6th
			paramList.add(conditionDT.getCodeSystemDescTxt());   //7th
			paramList.add(conditionDT.getConditionDescTxt());	 //8th
			paramList.add(conditionDT.getConditionShortNm());    //9th
	
	
			//if from time null set to current time
			if (conditionDT.getEffectiveFromTime() != null)//10th
				paramList.add(conditionDT.getEffectiveFromTime());
			else
				paramList.add (new Timestamp(new Date().getTime()));
	
			paramList.add(conditionDT.getEffectiveToTime());     //11th Oracle: 1/1/2003 Sql: 1/1/2003 12:00:00 AM
			//String indentLevelNbr = conditionDT.getIndentLevelNbr()== null ? "1" : conditionDT.getIndentLevelNbr(); ;
		    paramList.add(conditionDT.getIndentLevelNbr()); //12th legacy - default to 1
			paramList.add(null); //13th INV_FORM_GEN, DMB_FORM_CONDITION
			paramList.add('N');//14th conditionDT.getIsModifiableInd()
			paramList.add(nbsUid);//15th
	 		paramList.add(conditionDT.getNndInd());//16th
			paramList.add(conditionDT.getParentIsCd());	//17th		//99999 for Hep - usually blank
			paramList.add(conditionDT.getProgAreaCd());	//18th		 //GCD, HEP, VPD, BMIRD
	
			//default Reportable Morbidity Indicator to 'N' if Null
			String reportableMorbidityIndicator = conditionDT.getReportableMorbidityInd() == null ? "N" : conditionDT.getReportableMorbidityInd(); ;
			if(reportableMorbidityIndicator.equals("1") || reportableMorbidityIndicator.equals("Y"))  //19th
				paramList.add('Y');
			else
				paramList.add('N');
	
			//default Reportable Summary Indicator to 'N' if Null
			String reportableSummaryIndicator = conditionDT.getReportableSummaryInd() == null ? "N" : conditionDT.getReportableSummaryInd(); ;
			if(reportableSummaryIndicator.equals("1") || reportableSummaryIndicator.equals("Y"))  //20th
				paramList.add('Y');
			else
				paramList.add('N');
	
			paramList.add('A');  //21th  conditionDT.getStatusCd() either A or I
			//default status time if status code passed
			java.util.Date dateTime = new java.util.Date();
			Timestamp systemTime = new Timestamp(dateTime.getTime());
			paramList.add(systemTime);//Status time
			paramList.add(conditionDT.getNndEntityId()); //23th not used but tb_case_map
			paramList.add(conditionDT.getNndSummaryEntityIdentifier()); //24th not used - nia_case_map
			paramList.add(conditionDT.getSummaryInvestigationFormCd()); //25th
			paramList.add(conditionDT.getContactTracingEnableInd());//26th
			if(conditionDT.getVaccineModuleEnableInd()!=null && conditionDT.getVaccineModuleEnableInd().equals("Y")) //27th
				paramList.add("Y");
			else
				paramList.add("N");
			//28th
			if(conditionDT.getTreatmentModuleEnableInd()!=null && conditionDT.getTreatmentModuleEnableInd().equals("Y"))
				paramList.add("Y");
			else
				paramList.add("N");
			//29th
			if(conditionDT.getLabReportModuleEnableInd()!=null && conditionDT.getLabReportModuleEnableInd().equals("Y"))
				paramList.add("Y");
			else
				paramList.add("N");
	
			paramList.add("F");//port_req_ind_cd
			//30th
			if(conditionDT.getMorbReportModuleEnableInd()!=null && conditionDT.getMorbReportModuleEnableInd().equals("Y"))
				paramList.add("Y");
			else
				paramList.add("N");
			paramList.add(conditionDT.getFamilyCd());
			paramList.add(conditionDT.getCoInfGroup());
			try {
				
			int inserted = (Integer) preparedStmtMethod(conditionDT, paramList, codeSql, NEDSSConstants.UPDATE,NEDSSConstants.SRT);
			 if(inserted == 1){
				//2nd Insert into LDF_PAGE_SET table
				 insertLDFPageSet(conditionDT);
			 }
			} catch (Exception ex) {
				logger.fatal("Error on createCondition() ConditionDT = " + conditionDT.toString(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
	
		}else {
			return errorMap;
		}
	
	}catch(NEDSSDAOSysException ex){
		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}catch(NEDSSSystemException ex){
		logger.fatal("NEDSSSystemException  = "+ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}
	logger.debug(" ...leaving Condition DAO - creating new condition ..");
	return errorMap;

} //createCondition

public void updateConditionForPublishedPage(String conditionCd, String investigationFormCd, String entityIdentifier, String statusCd) throws NEDSSDAOSysException, NEDSSSystemException {
	String codeSql = UPDATE_CONDITION_SQL+SET_CONDITION_INV_FORM_CODE_AND_NND_ENTITY_IDENTIFIER;
    
    if(statusCd==null)
    	codeSql = codeSql.replace(",status_cd=?", "");

	logger.debug("ConditionDAOImpl.updateInvestigationFormCode: "+codeSql);

	ArrayList<Object>  paramList = new ArrayList<Object> ();
	paramList.add(investigationFormCd);
	paramList.add(entityIdentifier);
	if(statusCd!=null)
		paramList.add(statusCd);
	paramList.add(conditionCd);

	try {
		preparedStmtMethod(null, paramList,codeSql,NEDSSConstants.UPDATE,NEDSSConstants.SRT);
	} catch (Exception ex) {
		logger.fatal("Exception in ConditionDAOImpl.updateInvestigationFormCode: ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}
}

/**
 * method updateCondition -
 * Only some of the fields can be updated.
 * @param conditionDT
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
public Map<Object, Object> updateCondition(ConditionDT conditionDT) throws NEDSSDAOSysException, NEDSSSystemException {

	logger.debug(" in Condition DAO - updating condition ..");
		
	//Throw exception if duplicate combination of conditionCd and conditionNm entered
	int countRet = 0;
	int count = 0;
	Map<Object, Object> errorMap = new HashMap<Object, Object>();
	boolean isError=false;
	//countRet = uniqueConditionSet(conditionDT);
	if(countRet > 1) {
		errorMap.put(new Integer(count++), " The combination of this Condition Code and this Condition Name already exists. Please enter a unique combination of Condition Code and Condition Name to create a new record. ");
		isError=true;
	}
	//Throw exception if duplicate condition_short_nm is entered
	countRet = uniqueConditionNmForUpdate(conditionDT);
	if(countRet >= 1)
	{
		errorMap.put(new Integer(count++), " A record already exists with this Condition Name. Please enter a unique Condition Name to create a new record. ");
	   	isError=true;
	}

	if(isError!=true){

		//1st update condition_code Table
		String codeSql =UPDATE_CONDITION_SQL;
	   codeSql = codeSql + UPDATE_CONDITION;
	   // check if there is parentisCd
		
		//Removing the code after our meeting on 07/27/2018 regarding the defect in NBS Central #11284
		//We are not updating the indent level nbr.
		/*if(conditionDT.getParentIsCd()== null || conditionDT.getParentIsCd().isEmpty() ||
				conditionDT.getParentIsCd().equalsIgnoreCase("NULL"))
			conditionDT.setIndentLevelNbr("1");
		else
			conditionDT.setIndentLevelNbr("2");
*/
		ArrayList<Object>  paramList = new ArrayList<Object> ();
		paramList.add(conditionDT.getConditionDescTxt());
		paramList.add(conditionDT.getConditionShortNm());
		paramList.add(conditionDT.getNndInd());
		paramList.add(conditionDT.getParentIsCd());
		paramList.add(conditionDT.getReportableSummaryInd());
		paramList.add(conditionDT.getReportableMorbidityInd());
		paramList.add(conditionDT.getStatusCd());
		paramList.add(conditionDT.getIndentLevelNbr());
		paramList.add(conditionDT.getContactTracingEnableInd());
		paramList.add(conditionDT.getNndEntityId());
		//Status_time
		java.util.Date dateTime = new java.util.Date();
		Timestamp systemTime = new Timestamp(dateTime.getTime());
		paramList.add(systemTime);//Status time
		paramList.add(conditionDT.getCoInfGroup());
		paramList.add(conditionDT.getFamilyCd());
		//Note- can not update the conditionCd
		paramList.add(conditionDT.getConditionCd());

		try {
			preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);

		} catch (Exception ex) {
			logger.fatal("Error while updateCondition() into conditionDT = " + conditionDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}


		//2nd update LDF_PAGE_SET table

		updateLDFPageSet(conditionDT);
	}
	else {
		return errorMap;
	}
	logger.debug(" ...leaving Condition DAO - updating condition ..");
	return errorMap;

} //updateCondition

private void updateLDFPageSet(ConditionDT conditionDT)
{
	String codeSql =UPDATE_LDF_PAGE_SET_SQL;

	ArrayList<Object>  paramList = new ArrayList<Object> ();
	paramList.add(conditionDT.getConditionShortNm());
	paramList.add(conditionDT.getConditionCd());

	try {
		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE,NEDSSConstants.SRT);

	} catch (Exception ex) {
		logger.fatal("Error while updateLDFPageSet() into conditionDT = " + conditionDT.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	}
}

public Collection<Object> getCodingSysFields(String codeSys) throws Exception {

	String codeSql = null;
	ConditionDT dt=null;
	ArrayList<Object>  list = new ArrayList<Object>();

	
	codeSql = SELECT_CODE_SYSTEM_SQL;
	
	if (codeSys == null) {
		logger.error("The code cannot be null: getCodingSysFields: Input code = " +
				codeSys);
	}
	// else, code is in common table
	else {
		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		ConditionDT conditionDT = new ConditionDT();
		arrayList.add(codeSys);
		try {
			list = (ArrayList<Object> ) preparedStmtMethod(conditionDT, arrayList, codeSql, NEDSSConstants.SELECT);

			for (int count = 0; count < list.size(); count++) {
				dt = (ConditionDT) list.get(count);
				dt.setCodeSystemCd(codeSys);
			}
		}catch(Exception ex){
			logger.fatal("Error while getCodingSysFields() into conditionDT = " + conditionDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	return list;
}

private Long retreviveMaxNbsUidSet(){
	String codeSql = "";
	Long nbsUid= null;
	Connection dbConnection = null;
	try{
		dbConnection = getConnection();
	}catch(NEDSSSystemException nsex){
		logger.fatal("SQLException while obtaining database connection for retreviveMaxNbsUidSet ", nsex);
		throw new NEDSSSystemException(nsex.toString());
	}
	PreparedStatement preparedStmt = null;
	// insert a new record  into CodeSet table by retrieving seq_number first
	codeSql = SELECT_MAX_NBS_UID_SQL;
	ResultSet rs1 = null;
    
    try {
		preparedStmt = dbConnection.prepareStatement(codeSql);

		rs1 = preparedStmt.executeQuery();
		if (rs1.next()) {
	        logger.debug("GroupID = " + rs1.getLong(1));
	        nbsUid=new Long( rs1.getLong(1));
		}
	}catch (Exception ex) {
		logger.fatal("Exception while getting max of nbs_uid in retreviveMaxNbsUidSet() : ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}finally
	{
		closeResultSet(rs1);
        closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	}
	return nbsUid;
}

/**
 * method activateCondition -
 *  To Activate, status is set to Active.
 * @param conditionCd
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
public void activateCondition(String conditionCd) throws NEDSSDAOSysException, NEDSSSystemException {

	logger.debug(" in Condition DAO - activating condition ..");
	updateConditionStatus( conditionCd, gov.cdc.nedss.util.NEDSSConstants.STATUS_ACTIVE);

} //activateCondition

/**
 * method inactivateCondition -
 *  Set status is set to I for inactive.
 * @param conditionCd
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
public void inactivateCondition(String conditionCd) throws NEDSSDAOSysException, NEDSSSystemException {

	logger.debug(" in Condition DAO - inactivating condition ..");
	updateConditionStatus( conditionCd, gov.cdc.nedss.util.NEDSSConstants.STATUS_INACTIVE);

} //inactivateCondition


private int uniqueConditionSet(ConditionDT condDT) {

	ArrayList<Object>  paramList = new ArrayList<Object> ();
	int count =0;
	Integer intCount = null;
	String codeSql = "";
	codeSql = SELECT_COUNT_SQL;
   
	try {
		paramList.add(condDT.getConditionCd());
		paramList.add(condDT.getConditionShortNm());
		intCount =  (Integer) preparedStmtMethod(condDT, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
		count = intCount.intValue();
	} catch (Exception ex) {
		logger.fatal("Exception while getting count of uniqueConditionSet: ERROR = " + condDT.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	} finally {
		paramList = new ArrayList<Object> ();
	}
	return count;
}

private int uniqueConditionCd(ConditionDT condDT) {

	ArrayList<Object>  paramList = new ArrayList<Object> ();
	int count =0;
	Integer intCount = null;
	String codeSql = "";
	codeSql = SELECT_COUNT_CD_SQL;
    try {
		paramList.add(condDT.getConditionCd());
		intCount =  (Integer) preparedStmtMethod(condDT, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
		count = intCount.intValue();
	} catch (Exception ex) {
		logger.fatal("Exception while getting count of uniqueConditionSet: ERROR = " + condDT.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	} finally {
		paramList = new ArrayList<Object> ();
	}
	return count;
}

private int uniqueConditionNm(ConditionDT condDT) {

	ArrayList<Object>  paramList = new ArrayList<Object> ();
	int count =0;
	Integer intCount = null;
	String codeSql = "";
	codeSql = SELECT_COUNT_NM_SQL;
    try {
		paramList.add(condDT.getConditionShortNm());
		intCount =  (Integer) preparedStmtMethod(condDT, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
		count = intCount.intValue();
	} catch (Exception ex) {
		logger.fatal("Exception while getting count of uniqueConditionNm: ERROR = " + condDT.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	} finally {
		paramList = new ArrayList<Object> ();
	}
	return count;
}

private int uniqueConditionNmForUpdate(ConditionDT condDT) {

	ArrayList<Object>  paramList = new ArrayList<Object> ();
	int count =0;
	Integer intCount = null;
	String codeSql = "";
	codeSql = SELECT_COUNT_NM_UPDATE_SQL;
    try {
		paramList.add(condDT.getConditionShortNm());
		paramList.add(condDT.getConditionCd());
		intCount =  (Integer) preparedStmtMethod(condDT, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
		count = intCount.intValue();
	} catch (Exception ex) {
		logger.fatal("Exception while getting count of uniqueConditionNmForUpdate: ERROR = " + condDT.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	} finally {
		paramList = new ArrayList<Object> ();
	}
	return count;
}
private void insertLDFPageSet(ConditionDT conditionDT)
{

	// get the max ldfPageId+1
	String ldfPageId = retreviveMaxLDFpageId();
	
	 
	// get the max nbs_uid+1
	 
	Long nbsUid= retreviveMaxNbsUid();

	// get the max nbs_uid+1
	int displayRow= retreviveMaxDisplayRow();
	//check if it is a parent condition_cd.
	String parentIsCd = conditionDT.getParentIsCd();
	if(parentIsCd == null)
	{
		conditionDT.setIndentLevelNbr(1);
	}
	else
	{
		conditionDT.setIndentLevelNbr(2);
	}

	//ldf_page_id,business_object_nm,condition_cd, ui_display,indent_level_nbr, parent_is_cd,code_set_nm,seq_num,
	//code_version, nbs_uid,effective_from_time, effective_to_time, status_cd , code_short_desc_txt, display_row,
	//display_column
	//first part of clause differs from MSQL to Oracle
	String codeSql =INSERT_LDF_PAGE_SET_SQL;
    
    //prepare statement
	ArrayList<Object>  paramList = new ArrayList<Object> ();
	paramList.add(ldfPageId); // 1st Condition_Cd is primary key
    paramList.add("PHC");//2nd
    paramList.add(conditionDT.getConditionCd()); //3rd legacy - default to 1
    paramList.add("Link");//4th
    paramList.add("2");//5th
	paramList.add("30");		 //6th
	paramList.add("LDF_PAGE_SET");   //7th
	paramList.add("1");	 //8th
	paramList.add("1");    //9th
	paramList.add(nbsUid);//10th
	paramList.add (new Timestamp(new Date().getTime()));//11th
	paramList.add(new Timestamp(new Date().getTime()));     //12th Oracle: 1/1/2003 Sql: 1/1/2003 12:00:00 AM
    paramList.add("I"); //13th l
	paramList.add(conditionDT.getConditionShortNm()); //14th
	paramList.add(displayRow);//15th
	paramList.add("2");//16th

	try {
		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE,NEDSSConstants.SRT);

	} catch (Exception ex) {
		logger.fatal("Error on insertLDFPageSet() ConditionDT = " + conditionDT.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	}

}


private String retreviveMaxLDFpageId()
{
	String newLdfId = null;
	try {
		logger.debug(" getting retreviveMaxLDFpageId from DAO ..");
		String codeSql = "";
		PreparedStatement preparedStmt = null;

		// select if there is an entry in ldf_page_id starts with current year

		GregorianCalendar currentDate;
		currentDate = new GregorianCalendar();
		int currentYear = 0;
		currentYear = currentDate.get(Calendar.YEAR);

		//String codeSql_LdfPageId_Coll_Sql = "select ldf_page_id from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..LDF_page_set where ldf_page_id like '"+ currentYear+"%'";
		//String codeSql_LdfPageId_Coll_Ora = "select ldf_page_id from "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".LDF_page_set where ldf_page_id like '"+ currentYear+"%'";

		codeSql = "select ldf_page_id \"ldfPageId\" from  NBS_SRTE..LDF_page_set where ldf_page_id like '"+ currentYear+"%'";


		LdfPageSetDT dt = new LdfPageSetDT();
		ArrayList<Object>  ldfPageIdCollection = new ArrayList<Object> ();
		TreeSet<Object> ldfTreeSet = new TreeSet<Object>();
		String maxldfId = null;

		try {
			ldfPageIdCollection = (ArrayList<Object>) preparedStmtMethod(dt, ldfPageIdCollection, codeSql, NEDSSConstants.SELECT);
			if(ldfPageIdCollection != null && ldfPageIdCollection.size()>0)
			{
				Iterator<Object>  iter = ldfPageIdCollection.iterator();
				while(iter.hasNext()){
					LdfPageSetDT ldfDt = (LdfPageSetDT)iter.next();
					if(ldfDt.getLdfPageId()!= null)
					{
						ldfTreeSet.add(Integer.parseInt(ldfDt.getLdfPageId()));
					}
				}
				maxldfId =ldfTreeSet.last().toString();
			}
		} catch (Exception ex) {
			logger.debug("Select SQL = " + codeSql);
			logger.fatal("Exception in retreviveMaxLDFpageId(): ERROR = " + dt.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally
		{
		    closeStatement(preparedStmt);
		}


		if(maxldfId !=null && !maxldfId.trim().equals("") && !maxldfId.trim().equals(Integer.toString(currentYear)))
		{
			String str1 = maxldfId.substring(Integer.toString(currentYear).length(), maxldfId.length());
			String lastIndx = Integer.toString(Integer.parseInt(str1)+1);
			if(lastIndx.length()==1)
				newLdfId = Integer.toString(currentYear)+("00"+lastIndx);
			else if(lastIndx.length()==2)
				newLdfId = Integer.toString(currentYear)+("0"+lastIndx);
			else
				newLdfId = Integer.toString(currentYear)+(lastIndx);
		}else
		{
			newLdfId = Integer.toString(currentYear)+("001");
		}

	} catch (NumberFormatException e) {
		logger.fatal("Exception while getting max of nbs_uid in retreviveMaxNbsUid() : ERROR = " + e.getMessage(),e);
		throw new NEDSSSystemException(e.toString());
	} catch (NEDSSSystemException e) {
		logger.fatal("Exception while getting max of nbs_uid in retreviveMaxNbsUid() : ERROR = " + e.getMessage(),e);
		throw new NEDSSSystemException(e.toString());
	}
	return newLdfId;
}

private int retreviveMaxDisplayRow()
{

	String codeSql = "";
	int displayRow= 0;
	Integer displayRowInt = null;
	Connection dbConnection = null;
	try{
		dbConnection = getConnection();
	}catch(NEDSSSystemException nsex){
		logger.fatal("SQLException while obtaining database connection for retreviveMaxDisplayRow ", nsex);
		throw new NEDSSSystemException(nsex.toString());
	}
	PreparedStatement preparedStmt = null;
	ResultSet rs1 = null;
	// insert a new record  into CodeSet table by retrieving seq_number first
	codeSql = "SELECT MAX(display_row)+1 FROM NBS_SRTE..LDF_page_set";
    
    try {
		preparedStmt = dbConnection.prepareStatement(codeSql);

		rs1 = preparedStmt.executeQuery();
		if (rs1.next()) {
	        logger.debug("GroupID = " + rs1.getLong(1));
	        displayRowInt= (Integer)rs1.getInt(1);
	        displayRow = displayRowInt.intValue();
		}
	}catch (Exception ex) {
		logger.fatal("Exception while getting max of nbs_uid in retreviveMaxNbsUid() : ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}finally
	{
		closeResultSet(rs1);
        closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	}
	return displayRow;

}

private Long retreviveMaxNbsUid()
{

	String codeSql = "";
	Long nbs_uid= null;
	Connection dbConnection = null;
	try{
		dbConnection = getConnection();
	}catch(NEDSSSystemException nsex){
		logger.fatal("SQLException while obtaining database connection for retreviveMaxNbsUid ", nsex);
		throw new NEDSSSystemException(nsex.toString());
	}
	PreparedStatement preparedStmt = null;
	ResultSet rs1 = null;
	// insert a new record  into CodeSet table by retrieving seq_number first
	codeSql = "SELECT MAX(nbs_uid)+1 FROM  NBS_SRTE..LDF_page_set";
    try {
		preparedStmt = dbConnection.prepareStatement(codeSql);

		rs1 = preparedStmt.executeQuery();
		if (rs1.next()) {
	        logger.debug("GroupID = " + rs1.getLong(1));
	        nbs_uid=new Long( rs1.getLong(1));
		}
	}catch (Exception ex) {
		logger.fatal("Exception while getting max of nbs_uid in retreviveMaxNbsUid() : ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}finally
	{
		closeResultSet(rs1);
        closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	}
	return nbs_uid;

}
/**
 * method updateConditionStatus -
 *  To Activate, status is set to Active.
 * @param conditionCd
 * @param newStatus - either I or A
 * @throws NEDSSDAOSysException
 * @throws NEDSSSystemException
 */
private void updateConditionStatus(String conditionCd, String newStatus) throws NEDSSDAOSysException, NEDSSSystemException {

	logger.debug(" in Condition DAO - updating condition status to " + newStatus + "for " + conditionCd);

	String codeSql =UPDATE_CONDITION_SQL;
    
	codeSql = codeSql + UPDATE_CONDITION_STATUS;
	ArrayList<Object>  paramList = new ArrayList<Object> ();
	paramList.add(newStatus);
	paramList.add (new Timestamp(new Date().getTime()));
	paramList.add(conditionCd);

	try {
		preparedStmtMethod(null, paramList, codeSql, NEDSSConstants.UPDATE, NEDSSConstants.SRT);

	} catch (Exception ex) {
		logger.fatal("Exception in Update Condition Status: ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}

	logger.debug(" ...leaving Condition DAO - updated condition status ..");
} //updateConditionStatus


public boolean pagePublishedBefore(String conditionCd) {

	ArrayList<Object>  paramList = new ArrayList<Object> ();
	int count =0;
	Integer intCount = null;
	String codeSql = "";
	boolean ret = false;
	WaTemplateDT dt = new WaTemplateDT();
	codeSql = SELECT_COUNT_PUBLISHED_PAGE_SQL;
    try {	
		
		paramList.add(conditionCd);
		intCount =  (Integer) preparedStmtMethod(dt, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
		count = intCount.intValue();
		if(count>0)
		ret = true;
	} catch (Exception ex) {
		logger.fatal("Exception while getting count of pagePublishedBefore: ERROR = " + dt.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	} finally {
		paramList = new ArrayList<Object> ();
	}
	return ret;
}

public boolean conditionPublishedBefore(String conditionCd) {

	ArrayList<Object>  paramList = new ArrayList<Object> ();
	int count =0;
	Integer intCount = null;
	String codeSql = "";
	boolean ret = false;
	WaTemplateDT dt = new WaTemplateDT();
	
	codeSql = SELECT_COND_STATUS_SQL;
	
	try {			
		paramList.add(conditionCd);
		intCount =  (Integer) preparedStmtMethod(dt, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
		count = intCount.intValue();
		if(count>0)
		ret = true;
	} catch (Exception ex) {
		logger.fatal("Exception while getting count of conditionPublishedBefore: ERROR = " + dt.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	} finally {
		paramList = new ArrayList<Object> ();
	}
	return ret;
}


public String conditionPortInd(String conditionCd) {

	ArrayList<Object>  paramList = new ArrayList<Object> ();
	int count =0;
	Integer intCount = null;
	String codeSql = "";
	boolean ret = false;
	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	WaTemplateDT dt = new WaTemplateDT();
	
	ResultSet rs= null;
	String portReqIndCd = null;
	
	codeSql = SELECT_COND_PORTIND_SQL; 
	try {	
		
			dbConnection = getConnection();
			preparedStmt = dbConnection
					.prepareStatement(codeSql);
			preparedStmt.setString(1,conditionCd);
			rs = preparedStmt.executeQuery();
			
			if(rs!=null && rs.next()){
				portReqIndCd = rs.getString(1);
			}
	
	} catch (Exception ex) {
		logger.fatal("Exception while getting count of conditionPublishInd: ERROR = " + dt.toString(), ex);
		throw new NEDSSSystemException(ex.toString());
	} finally {
		closeResultSet(rs);
		closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	}
	return portReqIndCd;
	
}

public String getPublishedCondition(String conditionCd) throws NEDSSDAOSysException, NEDSSSystemException {

	Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    String conSrtNm = null;

    try
    {
    	String codeSql =SELECT_PUBLISHED_CONDITION_SQL;
	   
        dbConnection = getConnection();
        preparedStmt = dbConnection.prepareStatement(codeSql);
        preparedStmt.setString(1, conditionCd);
        resultSet = preparedStmt.executeQuery();

        if (!resultSet.next())
        {
        	conSrtNm = null;
        }
        else
        {
        	conSrtNm = resultSet.getString(1);
        }
    }

    catch (Exception ex) {
		logger.fatal("Exception in getConditionShortNm: ERROR = " + ex.getMessage(), ex);
		throw new NEDSSSystemException(ex.toString());
	}
    finally
    {
        closeResultSet(resultSet);
        closeStatement(preparedStmt);
        releaseConnection(dbConnection);
    }
		logger.debug(" ...leaving Condition Collection from DAO ..");

		return conSrtNm;

	} //getConditionShortNmCollection


} //Class
