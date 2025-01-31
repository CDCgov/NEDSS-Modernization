package gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util;

import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.AnswerMappingDT;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.ManagePageDT;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.WaTemplateVO;
import gov.cdc.nedss.pagemanagement.wa.dt.ConditionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.CodeValueGeneralDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionConditionDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionErrorDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMappingDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.util.PBtoPBConverterProcessor;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.portpage.PortPageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.upload.FormFile;
import org.apache.xmlbeans.XmlOptions;

import gov.cdc.nedss.page.port.mapping.MappedElement;
import gov.cdc.nedss.page.port.mapping.MappingDocument;
import gov.cdc.nedss.page.port.mapping.MappingDocument.Mapping;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
/**
 * @author PatelDh
 *
 */

public class PortPageUtil {
	
	static final LogUtils logger = new LogUtils(PortPageUtil.class.getName());
	public final static String MAPPING_PAGE_QUEUE_SIZE = "10";
	public final static String REVIEW_PAGE_QUEUE_SIZE = "20";
	public final static String STATUS_AUTO_MAPPED = "Auto Mapped";
	public final static String STATUS_COMPLETE = "Complete";
	public final static String STATUS_MAPPING_NEEDED = "Mapping Needed";
	public final static String STATUS_COMPLETE_REPEATING_BLOCK = "Complete (R)";
	public final static String STATUS_LEGACY_AUTOMAP = "Legacy Auto-Mapped";
	
	public final static String FILTER_QUESTION_STATUS = "STATUS";
	public final static String FILTER_QUESTION_MAP = "MAP";
	public final static String FILTER_QUESTION_FRMDATATYPE = "FRMDATATYPE";
	public final static String FILTER_QUESTION_TODATATYPE = "TODATATYPE";
	
	public final static String FILTER_ANSWER_STATUS = "STATUS";
	public final static String FILTER_ANSWER_MAP = "MAP";
	
	public final static String DATA_TYPE_CODED = "CODED";
	public final static String DATA_TYPE_NUMERIC = "NUMERIC";
	public final static String DATA_TYPE_TEXT = "TEXT";
	public final static String DATA_TYPE_DATE = "DATE";
	public final static String DATA_TYPE_DATETIME = "DATETIME";
	public final static String DATA_TYPE_PART = "PART";
	
	public final static String NBS_PAGE_MAPPING_STATUS_CODE_SET = "NBS_PAGE_MAPPING_STATUS";
	public final static String NBS_QA_MAPPING_STATUS_CODE_SET = "NBS_QA_MAPPING_STATUS";
	public final static String BUS_OBJ_TYPE_CODE_SET = "BUS_OBJ_TYPE";
	public final static String CODE_SET_YN = "YN";
	
	//public final static String NBS_PAGE_MAPPING_STATUS_IN_PROGRESS = "IN_PROGRESS";
	public final static String NBS_PAGE_MAPPING_STATUS_COMPLETE = "COMPLETE";
	public final static String NBS_PAGE_MAPPING_STATUS_MAPPING_IN_PROGRESS = "MAPPING_IN_PROGRESS";
	public final static String NBS_PAGE_MAPPING_STATUS_PORTING_IN_PROGRESS = "PORTING_IN_PROGRESS";
	
	public final static String NBS_QA_MAPPING_STATUS_AUTO_MAPPED = "AUTOMAP";
	public final static String NBS_QA_MAPPING_STATUS_COMPLETE = "COMPLETE";
	public final static String NBS_QA_MAPPING_STATUS_MAPPING_NEEDED = "INCOMPLETE";
	public final static String NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R = "INCOMPLETE_R";
	public final static String NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK = "COMPLETE_R";
	public final static String NBS_QA_MAPPING_STATUS_CORE = "CORE";
	public final static String NBS_QA_MAPPING_STATUS_UNMAPPED_TO_QUE = "UNMAPPED_TO_QUE";
	public final static String NBS_QA_MAPPING_STATUS_LEGACY_COMPLETE = "LEGACY_COMPLETE";
	
	public final static String MAPPED_QA_YN_Y = "Y";
	public final static String MAPPED_QA_YN_N = "N";
	
	public final static String ANSWER_MAPPED_YES = "Yes";
	public final static String ANSWER_MAPPED_NO = "No";
	
	public final static String PORT_LEGACY = "LEGACY";
	public final static String PORT_PAGEBUILDER = "PAGEBUILDER";
	
	public final static String UNMAPPED_TO_QUESTIONS = "UNMAPPED_TO_QUESTIONS";
	
	public final static String RUN_TYPE_PRERUN = "PreRun";
	public final static String RUN_TYPE_PRODUCTION = "Production";
	
	public final static String TEMPLATE_TYPE_PUBLISHED_WITH_DRAFT = "Published With Draft";
	public final static String TEMPLATE_TYPE_DRAFT = "Draft";
	public final static String TEMPLATE_TYPE_INITIAL_DRAFT = "Initial Draft";
	public final static String TEMPLATE_TYPE_LEGACY = "LEGACY";
	
	public final static String CASE_CONVERSION_STATUS_CODE_PASS = "Pass";
	public final static String CASE_CONVERSION_STATUS_CODE_FAIL = "Fail";
	
	public final static String PROD_RUN_COMPLETE = "Complete";
	public final static String PROD_RUN_PARTIAL = "Partial";
	public final static String PROD_ERROR = "Error";
	public final static String PRE_RUN_COMPLETE = "Complete";
	public final static String PRE_RUN_ERROR = "Error";
	
	//For Import Export EDX Activity Log insert
	public final static String DOC_TYPE_MAPPING = "Page Mapping Doc";
	public final static String IMPORT_EXPORT_STATUS_SUCCESS = "Success";
	public final static String IMPORT_EXPORT_STATUS_FAILURE = "Failure";
	
	public final static String CONTEXT_LOCK_MAPPING = "LockMapping";
	
	public final static String PORT_REQ_IND_F="F";
	
	public final static String BUS_OBJ_TYPE_INVESTIGATION = "Investigation";
	
	public final static String MAPPING_LEGACY = "LEGACY";
	public final static String MAPPING_PAGEBUILDER = "PAGEBUILDER";
	public final static String MAPPING_HYBRID = "HYBRID";
	
	public final static String LEGACY_EVENT_DUMMY_CONDITION_CD = "ALL";
	public final static String LEGACY_PAGE_START_WITH = "LEGACY_";
	
	public final static String STRUCTURE_NUMERIC_DUMMY_QUE_SUFFIX = "_SYS_UNIT";
	public final static String STRUCTURE_NUMERIC_DUMMY_QUE_LABEL_SUFFIX= " Sys Unit";
	
	public final static String CODED_WITH_OTHER_DUMMY_QUE_SUFFIX = "_SYS_OTH";
	public final static String CODED_WITH_OTHER_DUMMY_QUE_LABEL_SUFFIX= " Sys Other";
	
	//Use for repeating to repeating constraint check
	public final static String MAPPING_TYPE_DISCRETE_TO_REPEATING = "DISCRETE_TO_REPEATING";
	public final static String MAPPING_TYPE_REPEATING_TO_REPEATING = "REPEATING_TO_REPEATING";
	public final static String MAPPING_TYPE_DISCRETE_TO_DISCRETE = "DISCRETE_TO_DISCRETE";
	
	public final static String QUESTION_TYPE_DISCRETE = "DISCRETE";
	public final static String QUESTION_TYPE_REPEATING = "REPEATING";
	
	public final static String BLOCK_ID_NBR_FOR_REP_TO_REP_MAPPING = "1";
	
	public final static String TRUE_FLAG_STR = "T";
	public final static String FALSE_FLAG_STR = "F";
	
	public final static String VARICELLA_CONDIION_CD = "10030";
	public final static String VARICELLA_INV_FORM_CD = "INV_FORM_VAR";
	
	private final static String OTHER = "OTH";
	
	public final static String MULTI_TO_REP_SINGLE_MAPPING = "MultiToRepeatSingle";
	public final static String NBS_CASE_ANS_TO_CORE_MAPPING = "NBSCaseAnsToCore";
	public final static String ONE_IND_TO_MANY_QUES_MAPPING = "OneIndToManyQues";
	public final static String TWO_DISCRETE_TO_ONE_CODED_MAPPING = "TwoCodedToOneCoded";
	
	/**
	 * @param list
	 * @return
	 * @throws NEDSSAppException 
	 */
	public static String[] getFilterList(ArrayList list) throws NEDSSAppException{
		try{
			int arrayInd=0;
			String[] filterDropdownItems = new String[list.size()];
			for(int i=0;i<list.size();i++){
				 DropDownCodeDT cdDT = (DropDownCodeDT) list.get(i);
				 if(cdDT!=null){
					 filterDropdownItems[arrayInd++]=cdDT.getValue();
				 }
			}
			return filterDropdownItems;
		}catch(Exception ex){
			logger.fatal("Exception in getFilterList: "+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.toString());
		}
	}
	
	/**
	 * @return
	 * @throws NEDSSAppException
	 */
	public static Timestamp getCurrentTimestamp() throws NEDSSAppException{
		try{
			Date dateTime = new Date();
			Timestamp currentTime = new Timestamp(dateTime.getTime());
			return currentTime;
		}catch(Exception ex){
			logger.fatal("Exception in PortPageUtil.getCurrentTimestamp "+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.toString());
		}
	}
	
	/*To convert NULL to "" Blank to sort blank fields in Question Mapping Page*/
	/**
	 * @param fromFieldDT
	 * @throws Exception
	 */
	public static void nullToBlank(MappedFromToQuestionFieldsDT fromFieldDT) throws Exception{
		try{
			if(fromFieldDT.getToQuestionId()==null){
				fromFieldDT.setToQuestionId("");
			}
			if(fromFieldDT.getToLabel()==null){
				fromFieldDT.setToLabel("");
			}
			if(fromFieldDT.getToDataType()==null){
				fromFieldDT.setToDataType("");
			}
		}catch(Exception ex){
			logger.fatal("Exception in PortPageUtil.nullToBlank "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	/*To convert NULL to "" Blank to sort blank fields in Answer Mapping Page*/
	/**
	 * @param fromAnsDT
	 * @throws Exception
	 */
	public static void nullToBlankAns(AnswerMappingDT fromAnsDT) throws Exception{
		try{
			if(fromAnsDT.getToCode()==null){
				fromAnsDT.setToCode("");
			}
		}catch(Exception ex){
			logger.fatal("Exception in PortPageUtil.nullToBlankAns "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	/*To get the sort criteria for Question Mapping Page*/
	/**
	 * @param sortOrder
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static String getSortCriteriaQuestion(String sortOrder, String methodName) throws Exception{
		try{
			logger.debug("sortOrder: "+sortOrder+", methodName: "+methodName);
			String sortOrdrStr = null;
			if(methodName != null) {
				if(methodName.equals("getStatusDesc"))
					sortOrdrStr = "Status";
				else if(methodName.equals("getFromQuestionId"))
					sortOrdrStr = "From ID";
				else if(methodName.equals("getFromLabel"))
					sortOrdrStr = "From Label";
				else if(methodName.equals("getFromDataType"))				
					sortOrdrStr = "From Data Type";	
				else if(methodName.equals("getMapped"))
					sortOrdrStr = "Map";
				else if(methodName.equals("getQuestionMappedDesc"))
					sortOrdrStr = "Map";
				else if(methodName.equals("getToQuestionId"))
					sortOrdrStr = "To ID";
				else if(methodName.equals("getToLabel"))
					sortOrdrStr = "To Label";
				else if(methodName.equals("getToDataType"))
					sortOrdrStr = "To Data Type";
				else 
					sortOrdrStr = "Status";
			
			} else {
					sortOrdrStr = "Status";
			}
			
			if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
				sortOrdrStr = sortOrdrStr+" in ascending order ";
			else if(sortOrder != null && sortOrder.equals("2"))
				sortOrdrStr = sortOrdrStr+" in descending order ";
	
			return sortOrdrStr;
		}catch (Exception ex) {
			logger.fatal("Error in getSortCriteriaQuestion in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		
		}
			
	}
	
	/*To get sort criteria for answer mapping page*/
	/**
	 * @param sortOrder
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static String getSortCriteriaAnswer(String sortOrder, String methodName) throws Exception{
		try{
			logger.debug("sortOrder: "+sortOrder+", methodName: "+methodName);
			
			String sortOrdrStr = null;
			if(methodName != null) {
				if(methodName.equals("getStatus"))
					sortOrdrStr = "Status";
				else if(methodName.equals("getQuestionIdentifier"))
					sortOrdrStr = "From ID";
				else if(methodName.equals("getQuestionLabel"))
					sortOrdrStr = "From Label";
				else if(methodName.equals("getCode"))				
					sortOrdrStr = "From Answer";	
				else if(methodName.equals("getMapped"))
					sortOrdrStr = "Map";
				else if(methodName.equals("getToCode"))
					sortOrdrStr = "To Answer";
				else
					sortOrdrStr = "Status";
			} else {
					sortOrdrStr = "Status";
			}
			
			if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
				sortOrdrStr = sortOrdrStr+" in ascending order ";
			else if(sortOrder != null && sortOrder.equals("2"))
				sortOrdrStr = sortOrdrStr+" in descending order ";
	
			return sortOrdrStr;
		}catch (Exception ex) {
			logger.fatal("Error in getSortCriteriaAnswer in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		
		}
			
	}
	

	
	/*Filtering Methods for Question Mapping Page*/
	
	//To get the filtered list based on STATUS  in Question Mapping Page.
	
	/**
	 * @param filterQuestList
	 * @param statusMap
	 * @return
	 */
	public static Collection<Object>  filterByStatus(Collection<Object>  filterQuestList, Map<Object,Object> statusMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		try{
			if (filterQuestList != null) {
				Iterator<Object> iter = filterQuestList.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
					if (dt.getStatusDesc() != null&& statusMap != null&& statusMap.containsKey(dt.getStatusDesc())) {
						newStatusColl.add(dt);
					}
					if(dt.getStatusDesc() == null || dt.getStatusDesc().equals("")){
						if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newStatusColl.add(dt);
						}
					}
	
				}
			}
		}catch(Exception ex){
			logger.fatal("Error filtering the filterByStatus in PortPageUtil : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return newStatusColl;
	}

	//To get the filtered list based on From Data Type in Question Mapping Page.
	/**
	 * @param filterQuestList
	 * @param frmDtMap
	 * @return
	 */
	public static Collection<Object>  filterByFrmDT(Collection<Object>  filterQuestList, Map<Object,Object> frmDtMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		try{
			if (filterQuestList != null) {
				Iterator<Object> iter = filterQuestList.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
					if (dt.getFromDataType() != null&& frmDtMap != null&& frmDtMap.containsKey(dt.getFromDataType())) {
						newStatusColl.add(dt);
					}
					if(dt.getFromDataType() == null || dt.getFromDataType().equals("")){
						if(frmDtMap != null && frmDtMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newStatusColl.add(dt);
						}
					}
	
				}
			}
		}catch(Exception ex){
			logger.fatal("Error filtering the filterByFrmDT in PortPageUtil : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return newStatusColl;
	}
	
	//To get the filtered list based on To Data Type in Question Mapping Page.
	/**
	 * @param filterQuestList
	 * @param toDtMap
	 * @return
	 */
	public static Collection<Object>  filterByToDT(Collection<Object>  filterQuestList, Map<Object,Object> toDtMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		try{
		if (filterQuestList != null) {
			Iterator<Object> iter = filterQuestList.iterator();
			while (iter.hasNext()) {
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
				if (dt.getToDataType() != null&& toDtMap != null&& toDtMap.containsKey(dt.getToDataType())) {
					newStatusColl.add(dt);
				}
				if(dt.getToDataType() == null || dt.getToDataType().equals("")){
					if(toDtMap != null && toDtMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newStatusColl.add(dt);
					}
				}

			}
		}
		}catch(Exception ex){
			 logger.fatal("Error filtering the filterByToDT in PortPageUtil : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage());
			}
		return newStatusColl;
	}
	
	//To get the filtered list based on MAP in Question Mapping Page.
	/**
	 * @param filterQuestList
	 * @param mapMap
	 * @return
	 */
	public static Collection<Object>  filterByMap(Collection<Object>  filterQuestList, Map<Object,Object> mapMap) {
		Collection<Object>  newMapColl = new ArrayList<Object> ();
		try{
			if (filterQuestList != null) {
				Iterator<Object> iter = filterQuestList.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
					if (dt.getQuestionMappedCode() !=null && mapMap != null&& mapMap.containsKey(dt.getQuestionMappedCode())) {
						newMapColl.add(dt);
					}
					if(dt.getQuestionMappedCode()==null||dt.getQuestionMappedCode().equals("")){
					if(mapMap != null && mapMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newMapColl.add(dt);
						}
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Error filtering the filterByMap in PortPageUtil : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return newMapColl;
	}
	
	//To get the filtered list based on the text search for From ID,From Label,From CodeSetName,To ID,To Label and To Code Set Name in Question Mapping Page.
	/**
	 * @param fromPageQuestList
	 * @param filterByText
	 * @param column
	 * @return
	 */
	public static Collection<Object>  filterByText(Collection<Object>  fromPageQuestList, String filterByText,String column) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
			if (fromPageQuestList != null) {
				Iterator<Object> iter = fromPageQuestList.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
					if(column.equals(NEDSSConstants.UNIQUE_ID) && dt.getFromQuestionId() != null && dt.getFromQuestionId().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.LABEL) && dt.getFromLabel()!= null && dt.getFromLabel().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.CODE_SET_NM) && dt.getFromCodeSetNm()!= null && dt.getFromCodeSetNm().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.TO_UNIQUE_ID) && dt.getToQuestionId() != null && dt.getToQuestionId().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.TO_LABEL) && dt.getToLabel()!= null && dt.getToLabel().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.TO_CODE_SET_NM) && dt.getToCodeSetNm()!= null && dt.getToCodeSetNm().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
				}
			}
		}catch(Exception ex){
			 logger.fatal("Error filtering the filterByText in PortPageUtil : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}
	
	/*Filtering Methods for Answer Mapping Page  */

	//To get filtered list based on text search  for From ID,From Label,From Code,From CodeDesc ,To Code,To CodeDesc in Answer Mapping Page. 
	/**
	 * @param fromPageAnsList
	 * @param filterByText
	 * @param column
	 * @return
	 */
	public static Collection<Object>  filterAnsByText(Collection<Object>  fromPageAnsList, String filterByText,String column) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
			if (fromPageAnsList != null) {
				Iterator<Object> iter = fromPageAnsList.iterator();
				while (iter.hasNext()) {
					 AnswerMappingDT dt = (AnswerMappingDT) iter.next();
					if(column.equals(NEDSSConstants.UNIQUE_ID) && dt.getQuestionIdentifier() != null && dt.getQuestionIdentifier().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.LABEL) && dt.getQuestionLabel() != null && dt.getQuestionLabel().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.FROM_CODE) && dt.getCode() != null  && dt.getCodeDescTxt() != null && ((dt.getCode()+" : "+dt.getCodeDescTxt()).toUpperCase().contains(filterByText.toUpperCase().trim()))){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.TO_CODE) && dt.getToCode() != null && dt.getToCodeDescTxt() != null && ((dt.getToCode()+" : "+dt.getToCodeDescTxt()).toUpperCase().contains(filterByText.toUpperCase().trim()))){
						newTypeColl.add(dt);
					}
				}
			}
		}catch(Exception ex){
			 logger.fatal("Error filtering the filterAnsByText : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}
	
	//To get the filtered list based on the STATUS in Answer Mapping Page.
	/**
	 * @param fromPageAnsList
	 * @param statusMap
	 * @return
	 */
	public static Collection<Object>  filterAnsByStatus(Collection<Object>  fromPageAnsList, Map<Object,Object> statusMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		try{
			if (fromPageAnsList != null) {
				Iterator<Object> iter = fromPageAnsList.iterator();
				while (iter.hasNext()) {
					AnswerMappingDT dt = (AnswerMappingDT) iter.next();
					if (dt.getStatus() != null&& statusMap != null&& statusMap.containsKey(dt.getStatus())) {
						newStatusColl.add(dt);
					}
					if(dt.getStatus() == null || dt.getStatus().equals("")){
						if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newStatusColl.add(dt);
						}
					}
	
				}
			}
		}catch(Exception ex){
			 logger.fatal("Error filtering the filterAnsByStatus : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newStatusColl;
	}
	
	//To get the filtered list based on the MAP in Answer Mapping Page.
	/**
	 * @param fromPageAnsList
	 * @param mapMap
	 * @return
	 */
	public static Collection<Object>  filterAnsByMap(Collection<Object>  fromPageAnsList, Map<Object,Object> mapMap) {
		Collection<Object>  newMapColl = new ArrayList<Object> ();
		try{
			if (fromPageAnsList != null) {
				Iterator<Object> iter = fromPageAnsList.iterator();
				while (iter.hasNext()) {
					AnswerMappingDT dt = (AnswerMappingDT) iter.next();
					if (mapMap != null&& mapMap.containsKey(dt.getMapped())) {
						newMapColl.add(dt);
					}
					if(!PortPageUtil.MAPPED_QA_YN_N.equals(dt.getMapped()) && !PortPageUtil.MAPPED_QA_YN_Y.equals(dt.getMapped())){
						if(mapMap != null && mapMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newMapColl.add(dt);
						}
					}
				}
			}
		}catch(Exception ex){
			 logger.fatal("Error filtering the filterAnsByMap : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newMapColl;
	}
	
    /**
     * @param dtMap
     * @return
     * @throws Exception
     */
    private static Collection<Object>  convertInvMaptoColl(Map<Object, Object>  dtMap)throws Exception{
		Collection<Object>  dtColl = new ArrayList<Object> ();
	    try{
	         if(dtMap !=null && dtMap.size()>0){
		          Collection<Object>  dtKeyColl = dtMap.keySet();
		          Iterator<Object>  iter = dtKeyColl.iterator();
		                while(iter.hasNext()){
			                Long dtUidKey = (Long)iter.next();
			                dtColl.add(dtMap.get(dtUidKey));
		                }
	          }
	     }catch(Exception ex){
	    	 logger.fatal("Error in accessing the convertInvMaptoColl"+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage());
	     }
		 return dtColl;
	}
	
	/*To return filtered list after mapping a question ,if there are any filters applied before mapping a question*/
	/**
	 * @param fieldFilterMap
	 * @param toQueDT
	 * @param isFieldMappingRequired
	 * @return
	 */
	public  static Map<Object,Object> updateDropDowns(Map<Object,Object> fieldFilterMap,MappedFromToQuestionFieldsDT toQueDT,boolean isFieldMappingRequired){

		try{
			//========= STATUS DD===========
			String[] ansObj= (String[])fieldFilterMap.get(FILTER_QUESTION_STATUS);
			
			ArrayList<String> arrStrList = new ArrayList<String>();
			if(ansObj!=null){
				boolean statusCompletePresent=false;
				boolean statusComplete_R_Present=false;
				for(int i = 0 ; i < ansObj.length ; i++){
					if(ansObj[i].equals(STATUS_COMPLETE)){
						statusCompletePresent=true;
					}else if(ansObj[i].equals(STATUS_COMPLETE_REPEATING_BLOCK)){
						statusComplete_R_Present=true;
					}
					arrStrList.add(ansObj[i]);
				}
				if(!statusCompletePresent){
					arrStrList.add(STATUS_COMPLETE);
				}
				if(!statusComplete_R_Present){
					arrStrList.add(STATUS_COMPLETE_REPEATING_BLOCK);
				}
				ansObj=(String[])arrStrList.toArray(new String[0]);
				fieldFilterMap.put(FILTER_QUESTION_STATUS, ansObj);
			}
			//============= MAP DD==================
	
			String[] mapObj= (String[])fieldFilterMap.get(FILTER_QUESTION_MAP);
			
			if(mapObj!=null){
				arrStrList = new ArrayList<String>();
				
				boolean mapPresent=false;
				String mapVal="";
				for(int i = 0 ; i < mapObj.length ; i++){
					if(mapObj[i].equals("BLNK")){
						arrStrList.add(mapObj[i]);
						//continue;
					}
					if((isFieldMappingRequired)){
						if(!mapObj[i].equals("Y")){
							mapPresent=true;
							mapVal="Y";	
						}
					}else if((!isFieldMappingRequired )){
						if(!mapObj[i].equals("N")){
							mapPresent=true;
							mapVal="N";
						}
					}
					arrStrList.add(mapObj[i]);
				}
				if(mapPresent){
					arrStrList.add(mapVal);
				}
				mapObj=(String[])arrStrList.toArray(new String[0]);
				fieldFilterMap.put(FILTER_QUESTION_MAP, mapObj);
			}
			//=============== TO DATA TYPE DD===========
			
			String[] tdtObj= (String[])fieldFilterMap.get(FILTER_QUESTION_TODATATYPE);
	
			if(tdtObj!=null&&toQueDT!=null){
				arrStrList = new ArrayList<String>();
				boolean tdtPresent=false;
				String tdtVal=toQueDT.getFromDataType();
				for(int i = 0 ; i < tdtObj.length ; i++){
					if(tdtObj[i].equals("BLNK")){
						arrStrList.add(tdtObj[i]);
						continue;
					}
					if((isFieldMappingRequired)){
						if(tdtObj[i].equals(tdtVal)){
							tdtPresent=true;
						}
					}
					arrStrList.add(tdtObj[i]);
				}
				if(!tdtPresent){
					arrStrList.add(tdtVal);
				}
				if(!isFieldMappingRequired&&!arrStrList.contains("BLNK")){
					arrStrList.add("BLNK");
				}
				tdtObj=(String[])arrStrList.toArray(new String[0]);
		
				fieldFilterMap.put(FILTER_QUESTION_TODATATYPE, tdtObj);	
			}
		}catch(Exception ex){
			 logger.fatal("Error in updateDropDowns in PortPageUtil  : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return fieldFilterMap;
		
	}
/*To return filtered list after mapping an answer if there are any filters applied before mapping an answer*/
	/**
	 * @param ansFilterMap
	 * @param isCodeMappingRequired
	 * @return
	 */
	public static Map<Object,Object> updateAnsDropDowns(Map<Object, Object> ansFilterMap, boolean isCodeMappingRequired) {
		
		try{
			//=====Filter with STATUS====
			String[] ansObj= (String[])ansFilterMap.get(FILTER_ANSWER_STATUS);
	
			ArrayList<String> arrStrList = new ArrayList<String>();
			if(ansObj!=null){
				boolean statusPresent=false;
				for(int i = 0 ; i < ansObj.length ; i++){
					if(ansObj[i].equals(STATUS_COMPLETE)){
						statusPresent=true;
					}
					arrStrList.add(ansObj[i]);
				}
				if(!statusPresent){
					arrStrList.add(STATUS_COMPLETE);
				}
				ansObj=(String[])arrStrList.toArray(new String[0]);
				ansFilterMap.put(FILTER_ANSWER_STATUS, ansObj);
			}
			
			//======Filter with MAP======
	
			String[] mapObj= (String[])ansFilterMap.get(FILTER_ANSWER_MAP);
			arrStrList = new ArrayList<String>();
			if(mapObj!=null){
				boolean mapPresent=false;
				String mapVal="";
				for(int i = 0 ; i < mapObj.length ; i++){
					if(mapObj[i].equals("BLNK")){
						arrStrList.add(mapObj[i]);
						//continue;
					}
					if((isCodeMappingRequired)){
						if(!mapObj[i].equals("Y")){
							mapPresent=true;
							mapVal="Y";	
						}
					}else if((!isCodeMappingRequired )){
						if(!mapObj[i].equals("N")){
							mapPresent=true;
							mapVal="N";
						}
					}
					arrStrList.add(mapObj[i]);
				}
				if(mapPresent){
					arrStrList.add(mapVal);
				}
				mapObj=(String[])arrStrList.toArray(new String[0]);
				ansFilterMap.put(FILTER_ANSWER_MAP, mapObj);
			}
		}catch(Exception ex){
			 logger.fatal("Error in updateAnsDropDowns in PortPageUtil  : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return ansFilterMap;
		
	}

	//get participation type questions subject class cd. It used to populate toQuestion dropdown based on from question subject class cd
	/**
	 * @param questionIdentifier
	 * @return
	 * @throws NEDSSAppException
	 */
	public static String getParticipationSubjectClassCd(String questionIdentifier) throws NEDSSAppException{
		try{
			logger.debug("questionIdentifier: "+questionIdentifier);
			TreeMap<Object, Object> participationTypeCaseMap = CachedDropDowns.getParticipationTypeList();
		    String subjectClassCd="";
			ParticipationTypeVO parTypeVO = null;
	    	Iterator parTypeIter = participationTypeCaseMap.entrySet().iterator();
	    	while (parTypeIter.hasNext()) {
	    		Map.Entry mapPair = (Map.Entry)parTypeIter.next();
	    		String keyVal = (String) mapPair.getKey();
	    		parTypeVO = (ParticipationTypeVO) mapPair.getValue();
	    		if (parTypeVO.getQuestionIdentifier().equalsIgnoreCase(questionIdentifier) && NEDSSConstants.CASE.equals(parTypeVO.getActClassCd())) {
	    			subjectClassCd = parTypeVO.getSubjectClassCd();
	    		}
	    	}
	    	return subjectClassCd;
		}catch(Exception ex){
			 logger.fatal("Error getParticipationSubjectClassCd : "+ex.getMessage(),ex);
			 throw new NEDSSAppException(ex.getMessage());
		}
    	
	}
	
	
	/**
	 * @param mappingStatus
	 * @param mappingList
	 * @return
	 * @throws NEDSSAppException
	 */
	public static int getCountByMappingStatus(String mappingStatus, ArrayList<Object> mappingList) throws NEDSSAppException{
		try{
			int count = 0;
			for(int i=0;i<mappingList.size();i++){
				MappedFromToQuestionFieldsDT fromFieldDT = (MappedFromToQuestionFieldsDT) mappingList.get(i);
				if(mappingStatus.equalsIgnoreCase(fromFieldDT.getStatusCode())){
					count++;
				}
			}
			
			return count;
		}catch(Exception ex){
			 logger.fatal("Error getCountByMappingStatus mappingStatus: "+mappingStatus+", Exception: "+ex.getMessage(),ex);
			 throw new NEDSSAppException(ex.getMessage());
		}
    	
	}
	
	/**
	 * @param pageList
	 * @return
	 * @throws NEDSSAppException
	 */
	public static Long getWaTemplateUidForPublishedOrDraftPage(ArrayList<Object> pageList) throws NEDSSAppException{
		try{
			Long waTemplateUid = null;
			for(int i=0;i<pageList.size();i++){
				WaTemplateDT waTemplateDT =  (WaTemplateDT) pageList.get(i);
				if(waTemplateDT!=null && "Published".equals(waTemplateDT.getTemplateType())){
					waTemplateUid = waTemplateDT.getWaTemplateUid();
					break;
				}else if(waTemplateDT!=null && "Draft".equals(waTemplateDT.getTemplateType())){
					waTemplateUid = waTemplateDT.getWaTemplateUid();
					break;
				}
			}
			
			return waTemplateUid;
		}catch(Exception ex){
			 logger.fatal("Error getWaTemplateUidForPublishedOrDraftPage : "+ex.getMessage(),ex);
			 throw new NEDSSAppException(ex.getMessage());
		}
	}
	
	
	/**
	 * @param dt
	 * @return boolean based on from and to datatype.
	 * As per requirement data will be direct move for following cases.
	 * In UI QustionMapping page Map Answer default to No and be disabled
	 * @throws NEDSSAppException
	 */
	public static boolean isDirectDataMove(MappedFromToQuestionFieldsDT dt) throws NEDSSAppException{
		boolean isDirectMove = false;
		try{
			if(PortPageUtil.DATA_TYPE_PART.equals(dt.getFromDataType()) && PortPageUtil.DATA_TYPE_PART.equals(dt.getToDataType())){
				isDirectMove = true;
			}else if(PortPageUtil.DATA_TYPE_DATE.equals(dt.getFromDataType()) && PortPageUtil.DATA_TYPE_DATE.equals(dt.getToDataType())){
				isDirectMove = true;
			}else if(PortPageUtil.DATA_TYPE_NUMERIC.equals(dt.getFromDataType()) && PortPageUtil.DATA_TYPE_NUMERIC.equals(dt.getToDataType())){
				isDirectMove = true;
			}else if(PortPageUtil.DATA_TYPE_TEXT.equals(dt.getFromDataType()) && PortPageUtil.DATA_TYPE_TEXT.equals(dt.getToDataType())){
				isDirectMove = true;
			}else if(PortPageUtil.DATA_TYPE_NUMERIC.equals(dt.getFromDataType()) && PortPageUtil.DATA_TYPE_TEXT.equals(dt.getToDataType())){
				isDirectMove = true;
			}else if(PortPageUtil.DATA_TYPE_CODED.equals(dt.getFromDataType()) && PortPageUtil.DATA_TYPE_TEXT.equals(dt.getToDataType())){
				isDirectMove = true;
			}
		}catch(Exception ex){
			 logger.fatal("Error isDirectDataMove : "+ex.getMessage(),ex);
			 throw new NEDSSAppException(ex.getMessage());
		}
		return isDirectMove;
	}
	
	/**
	 * @param dt
	 * @return boolean based on from and to datatype.
	 * As per requirement data will be direct move for Coded to Coded.
	 * In UI QustionMapping page Map Answer default to No, if user want then can change it to Yes and map.
	 * @throws NEDSSAppException
	 */
	public static boolean isDirectDataMoveForCoded(MappedFromToQuestionFieldsDT dt) throws NEDSSAppException{
		boolean isDirectMoveCoded = false;
		try{
			if(PortPageUtil.DATA_TYPE_CODED.equals(dt.getFromDataType()) && PortPageUtil.DATA_TYPE_CODED.equals(dt.getToDataType()) 
					&& dt.getFromCodeSetNm()!=null && dt.getToCodeSetNm()!=null && dt.getFromCodeSetNm().equals(dt.getToCodeSetNm())){
				isDirectMoveCoded = true;
			}
		}catch(Exception ex){
			 logger.fatal("Error isDirectDataMoveForCoded : "+ex.getMessage(),ex);
			 throw new NEDSSAppException(ex.getMessage());
		}
		return isDirectMoveCoded;
	}
	
	/*Filtering methods for Review Page*/
	//Returns the filtered review list
	/**
	 * @param reviewList
	 * @param searchCriteriaMap
	 * @param portPageForm
	 * @return
	 * @throws Exception
	 */
	public static Collection<Object>  getFilteredReviewPageList(Collection<Object>  reviewList,Map<Object, Object> searchCriteriaMap,PortPageForm portPageForm) throws Exception{
            QueueUtil queueUtil = new QueueUtil();
      try{
	    	String[] fromDataType = (String[]) searchCriteriaMap.get("FRMDATATYPE");
	    	String[] mapQuestion = (String[]) searchCriteriaMap.get("MAPQUESTION");
			String[] toDataType = (String[]) searchCriteriaMap.get("TODATATYPE");
			String[] mapAnswer = (String[]) searchCriteriaMap.get("MAPANSWER");
			String fromId = null;
			String fromLabel = null;
			String toId = null;
			String toLabel = null;
			String fromAnswer = null;
			String toAnswer = null;
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				fromId = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				fromLabel = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
				toId = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText4_FILTER_TEXT")!=null){
				toLabel = (String) searchCriteriaMap.get("SearchText4_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText5_FILTER_TEXT")!=null){
				fromAnswer = (String) searchCriteriaMap.get("SearchText5_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText6_FILTER_TEXT")!=null){
				toAnswer = (String) searchCriteriaMap.get("SearchText6_FILTER_TEXT");
			}
			
		   	Map<Object, Object> fromDataTypeMap = new HashMap<Object,Object>();
			Map<Object, Object> mapQuestionMap = new HashMap<Object,Object>();
			Map<Object, Object> toDataTypeMap = new HashMap<Object,Object>();
			Map<Object, Object> mapAnswerMap = new HashMap<Object,Object>();
	
				if (fromDataType != null && fromDataType.length >0)
					fromDataTypeMap = queueUtil.getMapFromStringArray(fromDataType);
				
				if (mapQuestion != null && mapQuestion.length >0)
					mapQuestionMap = queueUtil.getMapFromStringArray(mapQuestion);
				
				if (toDataType != null && toDataType.length >0)
					toDataTypeMap = queueUtil.getMapFromStringArray(toDataType);
				
				if (mapAnswer != null && mapAnswer.length >0)
					mapAnswerMap = queueUtil.getMapFromStringArray(mapAnswer);
				
				/* Following methods are helping for page sorting */
				
				if(fromDataType != null && fromDataTypeMap != null && fromDataTypeMap.size()>0){
					reviewList = filterReviewByFromDataType(reviewList, fromDataTypeMap);
				}
				if(mapQuestion != null && mapQuestionMap != null && mapQuestionMap.size()>0){
					reviewList = filterReviewByMapQuest(reviewList, mapQuestionMap);
				}
				if (toDataType != null && toDataTypeMap != null && toDataTypeMap.size()>0)
					reviewList = filterReviewByToDataType(reviewList, toDataTypeMap);
	
				if (mapAnswer != null && mapAnswerMap != null && mapAnswerMap.size()>0)
					reviewList = filterReviewByMapAnswer(reviewList, mapAnswerMap);
	
				if(fromId!= null){
					reviewList = filterReviewLibByText(reviewList, fromId, NEDSSConstants.UNIQUE_ID);
				}
				if(fromLabel!= null){
					reviewList =filterReviewLibByText(reviewList, fromLabel, NEDSSConstants.LABEL);
				}
				if(toId!= null){
					reviewList = filterReviewLibByText(reviewList, toId, NEDSSConstants.TO_UNIQUE_ID);
				}
				if(toLabel!= null){
					reviewList = filterReviewLibByText(reviewList, toLabel, NEDSSConstants.TO_LABEL);
				}
				if(fromAnswer!= null){
					reviewList =filterReviewLibByText(reviewList, fromAnswer, NEDSSConstants.FROM_CODE);
				}
				if(toAnswer!= null){
					reviewList = filterReviewLibByText(reviewList, toAnswer, NEDSSConstants.TO_CODE);
				}
        }catch (Exception e) {
			logger.fatal("Error in  getFilteredPortPageList in PortPageUtil class: "+ e.toString(), e);
			throw new Exception(e.getMessage());
		}
		return reviewList;
	}
	
	//To get the filtered list based on From Data Type in Review Mapping Page.
	/**
	 * @param reviewList
	 * @param fromDataTypeMap
	 * @return
	 */
	public static Collection<Object>  filterReviewByFromDataType(Collection<Object>  reviewList, Map<Object,Object> fromDataTypeMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		try{
		if (reviewList != null) {
			Iterator<Object> iter = reviewList.iterator();
			while (iter.hasNext()) {
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
				if (dt.getFromDataType() != null&& fromDataTypeMap != null&& fromDataTypeMap.containsKey(dt.getFromDataType())) {
					newStatusColl.add(dt);
				}
				
				if(dt.getFromDataType() == null || dt.getFromDataType().equals("")){
					if(fromDataTypeMap != null && fromDataTypeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newStatusColl.add(dt);
					}
				}

			}
		}
		}catch(Exception ex){
			logger.fatal("Error filtering the filterReviewByFromDataType in PortPageUtil : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return newStatusColl;
	}
	
	//To get the filtered list based on MAP QUESTION? in Review Mapping Page.
	/**
	 * @param reviewList
	 * @param mapMap
	 * @return
	 */
	public static Collection<Object>  filterReviewByMapQuest(Collection<Object>  reviewList, Map<Object,Object> mapMap) {
		Collection<Object>  newMapColl = new ArrayList<Object> ();
		try{
			if (reviewList != null) {
				Iterator<Object> iter = reviewList.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
					if (dt.getQuestionMappedCode() !=null && mapMap != null&& mapMap.containsKey(dt.getQuestionMappedCode())) {
						newMapColl.add(dt);
					}
					if(dt.getQuestionMappedCode()==null||dt.getQuestionMappedCode().equals("")){
					if(mapMap != null && mapMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newMapColl.add(dt);
						}
					}
				}
			}
		}catch(Exception ex){
			 logger.fatal("Error filtering the filterReviewByMapQuest in PortPageUtil : "+ex.getMessage(),ex);
			 throw new NEDSSSystemException(ex.getMessage());
		 }
		return newMapColl;
	}
		
	//To get the filtered list based on To Data Type in Review Mapping Page.
	/**
	 * @param reviewList
	 * @param toDataTypeMap
	 * @return
	 */
	public static Collection<Object>  filterReviewByToDataType(Collection<Object>  reviewList, Map<Object,Object> toDataTypeMap) {
		Collection<Object>  newStatusColl = new ArrayList<Object> ();
		 try{
			if (reviewList != null) {
				Iterator<Object> iter = reviewList.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
					if (dt.getToDataType() != null&& toDataTypeMap != null&& toDataTypeMap.containsKey(dt.getToDataType())) {
						newStatusColl.add(dt);
					}
					if(dt.getToDataType() == null || dt.getToDataType().equals("")){
						if(toDataTypeMap != null && toDataTypeMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newStatusColl.add(dt);
						}
					}
	
				}
			}
		 }catch(Exception ex){
			 logger.fatal("Error filtering the filterReviewByToDataType in PortPageUtil : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage());
		 }
		return newStatusColl;
	}
	
	//To get the filtered list based on MAP QUESTION? in Review Mapping Page.
	/**
	 * @param reviewList
	 * @param mapMap
	 * @return
	 */
	public static Collection<Object>  filterReviewByMapAnswer(Collection<Object>  reviewList, Map<Object,Object> mapMap) {
		  Collection<Object>  newMapColl = new ArrayList<Object> ();
		  try{
		       if (reviewList != null) {
				  Iterator<Object> iter = reviewList.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
					if (dt.getAnswerMappedCode() !=null && mapMap != null&& mapMap.containsKey(dt.getAnswerMappedCode())) {
						newMapColl.add(dt);
					}
					if(dt.getAnswerMappedCode()==null||dt.getAnswerMappedCode().equals("")){
					    if(mapMap != null && mapMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newMapColl.add(dt);
						}
					}
				}
		       }
		  }catch(Exception ex){
			  logger.fatal("Error filtering the filterReviewByMapAnswer in PortPageUtil : "+ex.getMessage(), ex);
			  throw new NEDSSSystemException(ex.getMessage());
		  }
	      return newMapColl;
	}
	
	//To get the filtered list based on the text search for From ID,From Label,To ID,To Label,From Answer and To Answer in Review Mapping Page.
	/**
	 * @param reviewList
	 * @param filterByText
	 * @param column
	 * @return
	 */
	public static Collection<Object>  filterReviewLibByText(Collection<Object>  reviewList, String filterByText,String column) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
			if (reviewList != null) {
				Iterator<Object> iter = reviewList.iterator();
				while (iter.hasNext()) {
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
					if(column.equals(NEDSSConstants.UNIQUE_ID) && dt.getFromQuestionId() != null && dt.getFromQuestionId().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.LABEL) && dt.getFromLabel()!= null && dt.getFromLabel().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.TO_UNIQUE_ID) && dt.getToQuestionId() != null && dt.getToQuestionId().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.TO_LABEL) && dt.getToLabel()!= null && dt.getToLabel().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.FROM_CODE) && dt.getFromCode()!= null && dt.getFromCodeDesc()!= null && (dt.getFromCode()+" : "+dt.getFromCodeDesc()).toUpperCase().contains(filterByText.toUpperCase().trim())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.TO_CODE) && dt.getToCode()!= null && dt.getToCodeDesc()!= null && (dt.getToCode()+" : "+ dt.getToCodeDesc()).toUpperCase().contains(filterByText.toUpperCase().trim())){
						newTypeColl.add(dt);
					}
				}
			}
		}catch(Exception ex){
			 logger.fatal("Error filtering the filterReviewLibByText in PortPageUtil : "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}
	
	/**
	 * @param sortOrder
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static String getSortReviewPageLib(String sortOrder, String methodName) throws Exception{
		try{
			String sortOrdrStr = null;
			if(methodName != null) {
				if(methodName.equals("getFromQuestionId"))
					sortOrdrStr = "From ID";
				else if(methodName.equals("getFromLabel"))
					sortOrdrStr = "From Label";
				else if(methodName.equals("getFromDataType"))				
					sortOrdrStr = "From Data Type";	
				else if(methodName.equals("getQuestionMappedDesc"))
					sortOrdrStr = "Map Question?";
				else if(methodName.equals("getToQuestionId"))
					sortOrdrStr = "To ID";
				else if(methodName.equals("getToLabel"))
					sortOrdrStr = "To Label";
				else if(methodName.equals("getToDataType"))
					sortOrdrStr = "To Data Type";
				else if(methodName.equals("getFromCode"))				
					sortOrdrStr = "From Answer";	
				else if(methodName.equals("getAnswerMappedDesc"))
					sortOrdrStr = "Map Answer?";
				else if(methodName.equals("getToCode"))
					sortOrdrStr = "To Answer";
			} else {
					sortOrdrStr = "From ID";
			}
			
			if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
				sortOrdrStr = sortOrdrStr+" in ascending order ";
			else if(sortOrder != null && sortOrder.equals("2"))
				sortOrdrStr = sortOrdrStr+" in descending order ";
	
			return sortOrdrStr;
		}catch (Exception ex) {
			logger.fatal("Error in getSortCriteriaQuestion in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		
		}
	}
	
	/**
	 * @param sortOrder
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static String getSortCriteriaMasterLogs(String sortOrder, String methodName) throws Exception{
		try{
			String sortOrdrStr = null;
			if(methodName != null) {
				if(methodName.equals("getProcessTypeInd"))
					sortOrdrStr = "Process";
				else if(methodName.equals("getProcessMessageTxt"))
					sortOrdrStr = "Message";
				else if(methodName.equals("getStatusCd"))				
					sortOrdrStr = "Status";	
				else if(methodName.equals("getStartTime"))
					sortOrdrStr = "Start Time";
				else if(methodName.equals("getEndTime"))
					sortOrdrStr = "End Time";
			} else {
					sortOrdrStr = "Start Time";
			}
			
			if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
				sortOrdrStr = sortOrdrStr+" in ascending order ";
			else if(sortOrder != null && sortOrder.equals("2"))
				sortOrdrStr = sortOrdrStr+" in descending order ";
	
			return sortOrdrStr;
		}catch (Exception ex) {
			logger.fatal("Error in getSortCriteriaMasterLogs in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		
		}
			
	}
	
	/**
	 * @param mappedQuestionMap
	 * @return
	 */
	public ArrayList<Object> getMappedQuestionListFromMap(HashMap<String, ArrayList<Object>> mappedQuestionMap){
		ArrayList<Object> mappedQuestions = new ArrayList<Object>();
		try{
			ArrayList<Object> allQuestions = new ArrayList<Object>();
			for ( String key : mappedQuestionMap.keySet() ) {
				ArrayList<Object> questionList = mappedQuestionMap.get(key);
				allQuestions.addAll(questionList);
			}
			
			for(int i=0;i<allQuestions.size();i++){
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) allQuestions.get(i);
				if(dt!=null && (NBS_QA_MAPPING_STATUS_COMPLETE.equals(dt.getStatusCode()) || NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK.equals(dt.getStatusCode()) || NBS_QA_MAPPING_STATUS_LEGACY_COMPLETE.equals(dt.getStatusCode())) 
						&& (!DATA_TYPE_CODED.equals(dt.getToDataType()) || (DATA_TYPE_CODED.equals(dt.getToDataType()) && (MAPPED_QA_YN_Y.equals(dt.getAnswerMappedCode()) || (MAPPED_QA_YN_N.equals(dt.getAnswerMappedCode()) && (dt.getFromCode() == null || "".equals(dt.getFromCode()))))))){ // Added check to add only coded to answers for which  answer_mapped is Y.
					mappedQuestions.add(dt);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception getMappedQuestionListFromMap :"+ex.getMessage(),ex);
		}
		return mappedQuestions;
	}
	
	/**
	 * @param conditionCd
	 * @param NbsConvPgMgmtUid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Long getConditionCdGrpIdByCond(String conditionCd,Long NbsConvPgMgmtUid,HttpServletRequest request) throws Exception{
		Long conditionCdGroupId;
		try{
			logger.debug("conditionCd: "+conditionCd+", NbsConvPgMgmtUid: "+NbsConvPgMgmtUid);
			
		//Check if condition exist in NBS_Conversion_condition table, if exist then don't create it.
        
		    String portProxySBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
    	    String portProxySMethod = "findConditionCdGroupIdByCondition";
		    Object[] portProxySParams = new Object[] {conditionCd, NbsConvPgMgmtUid};
		    Object conditionCdGroupIdObj = CallProxyEJB.callProxyEJB(portProxySParams, portProxySBeanJndiName, portProxySMethod, request.getSession());
		    conditionCdGroupId =  (Long) conditionCdGroupIdObj;
		
		   if(conditionCdGroupId == null){
			//Create Condition in NBS_Conversion_condition table, it gives condtion_cd_group_id
			
			   String createConversionConditionSMethod = "createNBSConversionCondition";
			   Object[] createConversionConditionSParams = new Object[] {conditionCd, NbsConvPgMgmtUid};
			   conditionCdGroupIdObj = CallProxyEJB.callProxyEJB(createConversionConditionSParams, portProxySBeanJndiName, createConversionConditionSMethod, request.getSession());
			   conditionCdGroupId =  (Long) conditionCdGroupIdObj;
		   }
	    }catch (Exception ex) {
			logger.fatal("Error in getConditionCdGrpIdByCond in  PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return conditionCdGroupId;
	}
	
	/**
	 * @param mappedQuestionList
	 * @param conditionCdGroupId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean insertIntoNbsConvMapping(ArrayList mappedQuestionList,Long conditionCdGroupId,HttpServletRequest request) throws Exception{
		boolean isMapInserted = false;
		//Store mappings into DB NBS_Conversion_mapping
		try{
			logger.debug("conditionCdGroupId: "+conditionCdGroupId);
					
		    String portProxySBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
		    String insertIntoNbsConvMapSMethod = "insertMappingForConversion";
		    Object[] insertIntoNbsConvMapSParams = new Object[] {mappedQuestionList, conditionCdGroupId};
		    Object nbsConvObj = CallProxyEJB.callProxyEJB(insertIntoNbsConvMapSParams, portProxySBeanJndiName, insertIntoNbsConvMapSMethod, request.getSession());
		    isMapInserted =  (boolean) nbsConvObj;
		
	    }catch (Exception ex) {
		     logger.fatal("Error in insertIntoNbsConvMapping in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
	    }
		return isMapInserted;
    }
	
	/**
	 * @param conditionCdGroupId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Object> getNbsConvMapping(Long conditionCdGroupId,HttpServletRequest request) throws Exception{
		ArrayList<Object> nbsConvMapList = new ArrayList<Object>();;
		
		try{
			logger.debug("conditionCdGroupId: "+conditionCdGroupId);
			
		    String SBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
    	    String getNbsConvMapSMethod = "getNBSConversionMappings";
		    Object[] getNbsConvMapSParams = new Object[] {conditionCdGroupId};
		    Object nbsConvMapListObj = CallProxyEJB.callProxyEJB(getNbsConvMapSParams,SBeanJndiName,getNbsConvMapSMethod,request.getSession());
		    nbsConvMapList=(ArrayList<Object>) nbsConvMapListObj;
		}catch (Exception ex) {
		     logger.fatal("Error in getNbsConvMapping in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
	    }
		return nbsConvMapList;
	}
	
	/**
	 * @param conditionCdGroupId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public  boolean removeMapFromNbsConvMapping(Long conditionCdGroupId,HttpServletRequest request) throws Exception{
		boolean isRemoved = false;
		try{
			 logger.debug("conditionCdGroupId: "+conditionCdGroupId);
			
		     String SBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
	         String removeMapSMethod = "removeMappingsCreatedForConversion";
	         Object[] removeMapSParams = new Object[] {conditionCdGroupId};
	         Object removeMapObj = CallProxyEJB.callProxyEJB(removeMapSParams,SBeanJndiName,removeMapSMethod,request.getSession());
	         isRemoved=(boolean) removeMapObj;
		}catch (Exception ex) {
		     logger.fatal("Error in removeMapFromNbsConvMapping in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
	    return isRemoved;
	}
	
	/**
	 * @param codeSetNm
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Object> getCodeListByCodeSetNm(String codeSetNm) throws Exception{
        ArrayList<Object> codeList = new ArrayList<Object>();
        CodeValueGeneralDAOImpl cvgDAO = new CodeValueGeneralDAOImpl();
        logger.debug("codeSetNm: "+codeSetNm);
        try{ 
            codeList= (ArrayList<Object>) cvgDAO.retrieveCodeSetValGenFields(codeSetNm);
		}catch(Exception ex){
		     logger.fatal("Error in getCodeListByCodeSetNm in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
        return codeList;
	}
	

	//Checks if all the codes of from question is present in to question valueset.
	/**
	 * @param fromCodeList
	 * @param toCodeList
	 * @return
	 * @throws Exception 
	 */
	public ArrayList checkIfCodesExist(ArrayList<Object> fromCodeList,ArrayList<Object> toCodeList) throws Exception{
		ArrayList codeList = new ArrayList();
		ArrayList toList = new ArrayList();
		try{
			for(int i=0;i<toCodeList.size();i++){
				CodeValueGeneralDT cvgDT = (CodeValueGeneralDT)toCodeList.get(i);
				toList.add(cvgDT.getCode());
			}
			for(int i=0;i<fromCodeList.size();i++){
				CodeValueGeneralDT cvgDT = (CodeValueGeneralDT)fromCodeList.get(i);
				if(!toList.contains(cvgDT.getCode())){
					codeList.add(cvgDT.getCode()+"-"+cvgDT.getCodeShortDescTxt());
				}
			}
			return codeList;
		}catch(Exception ex){
		     logger.fatal("Error in checkIfCodesExist in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
	}
	
	/**
	 * @param nbsConvList
	 * @return
	 * @throws Exception 
	 */
	public  ArrayList<Object> checkIfDuplicateBlockId(ArrayList<Object> nbsConvList) throws Exception{
		ArrayList<Object> errorColl = new ArrayList<Object>();
		
		 try{
			for(int i=0;i<nbsConvList.size();i++){
				NBSConversionMappingDT nbsConvMapDT1 = (NBSConversionMappingDT)nbsConvList.get(i);
				
				if(nbsConvMapDT1.getBlockIdNbr()!=null && nbsConvMapDT1.getBlockIdNbr()>0){
					for(int j=i+1;j<nbsConvList.size();j++){
						NBSConversionMappingDT nbsConvMapDT2 = (NBSConversionMappingDT)nbsConvList.get(j);
						   
						if(nbsConvMapDT2.getBlockIdNbr()!=null && nbsConvMapDT2.getBlockIdNbr()>0){
							 if(nbsConvMapDT1.getToQuestionId().equals(nbsConvMapDT2.getToQuestionId()) && 
									 !nbsConvMapDT1.getFromQuestionId().equals(nbsConvMapDT2.getFromQuestionId()) && nbsConvMapDT1.getBlockIdNbr().equals(nbsConvMapDT2.getBlockIdNbr())){
								         String errorMessage= "For discrete to repeating mapping, repeating block number should be different for same To Question. "
								        		 +"To Question "+nbsConvMapDT1.getToQuestionId()
								         		+ " Please check NBS_conversion_mapping table for NBS_conversion_mapping_uid="+nbsConvMapDT1.getNbsConversionMappingUid()
								         		+" and NBS_conversion_mapping_uid="+nbsConvMapDT2.getNbsConversionMappingUid();
								         NBSConversionErrorDT nbsConvErrorDT = setNBSConversionMappingDT("REPEATING_BLOCK_ERROR",errorMessage,nbsConvMapDT1);
								         errorColl.add(nbsConvErrorDT);
							 }
						}
					}
				}
			}
		 }catch(Exception ex){
			 logger.fatal("Error in checkIfCodesExist in  PortPageUtil: "+ex.getMessage(), ex);
			 throw new Exception(ex.toString());
		 }
		return errorColl;
		
	}
	
	/**
	 * @param errorCd
	 * @param errorMessage
	 * @param nbsConversionMappingDT
	 * @return
	 * @throws Exception 
	 */
	public static NBSConversionErrorDT setNBSConversionMappingDT(String errorCd, String errorMessage, NBSConversionMappingDT nbsConversionMappingDT ) throws Exception{
		NBSConversionErrorDT nBSConversionErrorDT =  new NBSConversionErrorDT();
		try{
			nBSConversionErrorDT.setErrorCd(errorCd);
			nBSConversionErrorDT.setErrorMessageTxt(errorMessage);
			nBSConversionErrorDT.setConditionCdGroupId(nbsConversionMappingDT.getConditionCdGroupId());
			nBSConversionErrorDT.setNbsConversionMappingUid(nbsConversionMappingDT.getNbsConversionMappingUid());
		}catch(Exception ex){
			logger.fatal("errorCd: "+errorCd+", errorMessage: "+errorMessage+" Exception = "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return nBSConversionErrorDT;
		
	}
	
	/**
	 * @param portPageForm
	 * @param request
	 * @throws Exception 
	 */
	public static void setPageAndConditionDetailsToDisplay(PortPageForm portPageForm, HttpServletRequest request) throws Exception{
		try{
			PageManagementActionUtil util = new PageManagementActionUtil();
			ArrayList<Object> pageList=new ArrayList<Object>();
			HttpSession session = request.getSession();

			pageList = (ArrayList<Object>)util.getPageSummaries(session);
	    	
	    	for(int i=0;i<pageList.size();i++){
				WaTemplateDT waDT = (WaTemplateDT)pageList.get(i);
				if("Investigation".equals(waDT.getBusObjType())){
					if(portPageForm.getFromPageWaTemplateUid().longValue() == waDT.getWaTemplateUid().longValue()){
						portPageForm.getAttributeMap().put("FromPageName", waDT.getTemplateNm());
					}else if(portPageForm.getToPageWaTemplateUid().longValue() == waDT.getWaTemplateUid().longValue()){
						portPageForm.getAttributeMap().put("ToPageName", waDT.getTemplateNm());
					}
				}
	    	}
	    	// Get total condition associated with from page to display on review screen information bar.
	    	ArrayList conditionList=new ArrayList();
	    	ArrayList<Object> conditionListWithPortInd = getConditionByTemplateFormCd(portPageForm.getFromPageFormCd(), request);
	    	
	    	for(int i=0;i<conditionListWithPortInd.size();i++){
	    		ConditionDT condDT = (ConditionDT) conditionListWithPortInd.get(i); 
	    		if(PortPageUtil.PORT_REQ_IND_F.equals(condDT.getPortReqIndCd())){            //skipping the conditions which are required to port from Legacy to Page Builder page.
	    			conditionList.add(condDT);
	    		}
	    	}
	    	
	    	portPageForm.getAttributeMap().put("FromPageTotalAssociatedConditions",conditionList.size());
	    	
	    	//Hybrid / Legacy mapping where page builder page does not exist
	    	
	    	if(conditionList.size()==0){
	    		if(conditionListWithPortInd.size()>0){
		    		ConditionDT conditionDT = (ConditionDT) conditionListWithPortInd.get(0); 
		    		
		    		String mappingType = PortPageUtil.getMappingType(portPageForm.getFromPageFormCd(), conditionDT.getConditionCd());
		    		if(PortPageUtil.MAPPING_HYBRID.equals(mappingType)){
		    			
		    			WaTemplateVO waTemplateVO =  util.getPageDetails(portPageForm.getFromPageWaTemplateUid(), request.getSession());
		    			WaTemplateDT templateDT = waTemplateVO.getWaTemplateDT();
		    			
		    			if(templateDT!=null)
		    				portPageForm.getAttributeMap().put("FromPageName", templateDT.getTemplateNm());
		    			portPageForm.getAttributeMap().put("FromPageTotalAssociatedConditions",conditionListWithPortInd.size());
		    		}
	    		}
	    	}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}

	/**
	 * @param mappedQuestions
	 * @param nbsConversionPageMgmtUid
	 * @param request
	 * @return
	 */
	public static boolean storeMappingInWAConversionMapping(HashMap<String, ArrayList<Object>> mappedQuestions, Long nbsConversionPageMgmtUid, HttpServletRequest request){
		boolean isSuccess = false;
		try{
			ArrayList<Object> questionsToPersist = new ArrayList<Object>();
	
			for ( String key : mappedQuestions.keySet() ) {
				ArrayList<Object> questionList = mappedQuestions.get(key);
				questionsToPersist.addAll(questionList);
			}
			// Save mapped questions into database
			Object[] oParamsMappedQue = new Object[] {questionsToPersist, nbsConversionPageMgmtUid};
			String sBeanJndiNameMappedQue = JNDINames.PORT_PAGE_PROXY_EJB;
			String sMethodMappedQue = "insertQuestionAnswers";
			Object questionAnswerListObj = CallProxyEJB.callProxyEJB(oParamsMappedQue, sBeanJndiNameMappedQue, sMethodMappedQue, request.getSession());
			return isSuccess;
		}catch(Exception ex){
			logger.fatal(" Exception = "+ex.getMessage(), ex);
		}
		return isSuccess;
	}
	
	/**
	 * @param fromPageWaTemplateUid
	 * @param toPageWaTemplateUid
	 * @param nbsConversionPageMgmtUid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Object> getMappedQuestionAnswers(Long fromPageWaTemplateUid,Long toPageWaTemplateUid, Long nbsConversionPageMgmtUid, String mappingType, HttpServletRequest request) throws Exception{
		ArrayList<Object> mappedQuestionAnswersList = new ArrayList<Object>();
		logger.debug("fromPageWaTemplateUid: "+fromPageWaTemplateUid+", toPageWaTemplateUid: "+toPageWaTemplateUid+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", mappingType: "+mappingType);
		try{
			// Load Mapped Questions from database.
			Object[] oParams = new Object[] {fromPageWaTemplateUid, toPageWaTemplateUid, nbsConversionPageMgmtUid, mappingType};
			String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
			String sMethod = "viewPortPageMapping";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			mappedQuestionAnswersList = (ArrayList) obj;
			return mappedQuestionAnswersList;
		}catch (Exception ex) {
		     logger.fatal("Error in getMappedQuestionAnswers in  PortPageUtil: "+ex.getMessage(), ex);
		   throw new Exception(ex.toString());
	    }
	}
	
	/**
	 * @param mappedQuestionAnswersList
	 * @param portPageForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String exportMappingToXML(ArrayList<Object> mappedQuestionAnswersList, PortPageForm portPageForm, HttpServletRequest request) throws Exception{
		String mappingSourceSystem = "";
		try{
			logger.debug("Number of Questions to Export: "+mappedQuestionAnswersList.size());
			
			logger.debug("Exporting Map Name:"+portPageForm.getMapName()+", FromPageFormCd: "+portPageForm.getFromPageFormCd()+", ToPageFormCd: "+portPageForm.getToPageFormCd());
			PropertyUtil propertyUtil = PropertyUtil.getInstance();
			mappingSourceSystem = propertyUtil.getMsgSendingFacility();
			
			MappingDocument mappingDoc = MappingDocument.Factory.newInstance();
			Mapping mapping = mappingDoc.addNewMapping();
			
			mapping.setMappingName(portPageForm.getMapName());
			mapping.setFromPageFormCd(portPageForm.getFromPageFormCd());
			mapping.setToPageFormCd(portPageForm.getToPageFormCd());
			mapping.setExportFromState(mappingSourceSystem);
			mapping.setExportDateTime(Calendar.getInstance());
			
			for(int i=0;i<mappedQuestionAnswersList.size();i++){
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) mappedQuestionAnswersList.get(i);
				PortPageUtil.populateDummyQuestionDetails(portPageForm, dt);
				if(dt!=null){
					MappedElement element = mapping.addNewMappedElement();
					if(dt.getFromQuestionId()!=null)
						element.setFromQuestionUid(dt.getFromQuestionId());
					else
						element.setFromQuestionUid("");
					
					if(dt.getFromCode()!=null)
						element.setFromAnswer(dt.getFromCode());
					else
						element.setFromAnswer("");
					
					if(dt.getFromDataType()!=null)
						element.setFromDataType(dt.getFromDataType());
					else
						element.setFromDataType("");
					
					if(dt.getFromCodeSetNm()!=null)
						element.setFromCodeSetNm(dt.getFromCodeSetNm());
					else
						element.setFromCodeSetNm("");
					
					if(dt.getFromNbsUiComponentUid()!=null)
						element.setFromNbsUiComponentUid(dt.getFromNbsUiComponentUid());
					/*else
						element.setFromNbsUiComponentUid(0);*/
					
					if(dt.getToQuestionId()!=null)
						element.setToQuestionId(dt.getToQuestionId());
					else
						element.setToQuestionId("");
					
					if(dt.getToCode()!=null)
						element.setToAnswer(dt.getToCode());
					else
						element.setToAnswer("");
					
					if(dt.getToCodeSetNm()!=null)
						element.setToCodeSetNm(dt.getToCodeSetNm());
					else
						element.setToCodeSetNm("");
					
					if(dt.getToDataType()!=null)
						element.setToDataType(dt.getToDataType());
					else
						element.setToDataType("");
					
					if(dt.getToNbsUiComponentUid()!=null)
						element.setToNbsUiComponentUid(dt.getToNbsUiComponentUid());
					/*else
						element.setToNbsUiComponentUid(0);*/
					
					
					element.setMappingStatus(dt.getStatusCode());
					
					if(dt.getQuestionMappedCode()!=null)
						element.setQuestionMapped(dt.getQuestionMappedCode());
					else
						element.setQuestionMapped("");
					
					if(dt.getAnswerMappedCode()!=null)
						element.setAnswerMapped(dt.getAnswerMappedCode());
					else
						element.setAnswerMapped("");
					
					if(dt.getBlockIdNbr()!=null)
						element.setBlockIdNbr(dt.getBlockIdNbr());
					
					if(dt.getAnswerGroupSeqNbr()!=null)
						element.setAnswerGroupSeqNbr(dt.getAnswerGroupSeqNbr());
					
					if(dt.getConversionType()!=null)
						element.setConversionType(dt.getConversionType());
					
					mapping.setMappedElementArray(i, element);
				}
			}
			
			
			String xmlStr = mappingDoc.toString();
			
			EDXActivityLogDT dt = populateEdxActivityLogDT(portPageForm.getNbsConversionPageMgmtUid(), portPageForm.getMapName(), IMPORT_EXPORT_STATUS_SUCCESS, "E", propertyUtil.getMsgSendingFacility(),null);
			createEdxActivityLog(dt, request);
			
			return xmlStr;
		}catch (Exception ex) {
			logger.fatal("Error in exportMappingToXML in  PortPageUtil: "+ex.getMessage(), ex);
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			String exceptionMessage = errors.toString();
			EDXActivityLogDT dt = populateEdxActivityLogDT(portPageForm.getNbsConversionPageMgmtUid(), portPageForm.getMapName(), IMPORT_EXPORT_STATUS_FAILURE, "E", mappingSourceSystem, exceptionMessage);
			createEdxActivityLog(dt, request);
			throw new Exception(ex.toString());
	    }
	}
	
	public boolean validateImportedXML(PortPageForm portPageForm, HttpServletRequest request) throws Exception {
		boolean isXmlValid = false;
		try{
			FormFile file = portPageForm.getImportFile(); 
			
			MappingDocument mappingDoc = MappingDocument.Factory.parse(file.getInputStream());
			//Convert algorithm to string to transform it to new format
	        String inputXML = mappingDoc.copy().xmlText();
	        //Parse transformed algorithm
	        mappingDoc = MappingDocument.Factory.parse(inputXML);
	        
	        ArrayList<Object> validationErrors = new ArrayList<Object> ();
			XmlOptions validationOptions = new XmlOptions();
			validationOptions.setErrorListener(validationErrors);
			
	        isXmlValid = mappingDoc.validate(validationOptions);
	        if(!isXmlValid){
	        	String errString="";
	        	Iterator<Object>  iter = validationErrors.iterator();
			    while (iter.hasNext()) {
			    	errString += "\n>> " + iter.next();
			    }
			    EDXActivityLogDT dt = populateEdxActivityLogDT(null, portPageForm.getMapName(), IMPORT_EXPORT_STATUS_FAILURE, "I", null,errString);
				createEdxActivityLog(dt, request);
	        	//throw new XmlException("Mapping XML Validation failed."+errString);
	        }
		}catch (Exception ex) {
			logger.fatal("Error in validateImportedXML in  PortPageUtil: "+ex.getMessage(), ex);
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			String exceptionMessage = errors.toString();
			EDXActivityLogDT dt = populateEdxActivityLogDT(null, portPageForm.getMapName(), IMPORT_EXPORT_STATUS_FAILURE, "I", null,exceptionMessage);
			createEdxActivityLog(dt, request);
	    }
		return isXmlValid;
	}
	
	/**
	 * @param portPageForm
	 * @param request
	 * @throws Exception
	 */
	public void importMapping(PortPageForm portPageForm, HttpServletRequest request) throws Exception{
		String mappingSourceSystem="";
		try{
			boolean isError = false;
			FormFile file = portPageForm.getImportFile(); 
			MappingDocument mappingDoc = MappingDocument.Factory.parse(file.getInputStream());
			//Convert algorithm to string to transform it to new format
	        String inputXML = mappingDoc.copy().xmlText();
	        //Parse transformed algorithm
	        mappingDoc = MappingDocument.Factory.parse(inputXML);
	        
	        Mapping mapping = mappingDoc.getMapping();
	        String mapName = mapping.getMappingName();
	        mappingSourceSystem = mapping.getExportFromState();
	        //From Page's questions to validate against XML elements, do not add Structure Numeric dummy question (_Unit one) in list - pass Boolean.FALSE
	        Map<String, MappedFromToQuestionFieldsDT> fromPageQuestionMap = getQuestionsForValidationByTemplateUid(portPageForm.getFromPageWaTemplateUid(), Boolean.FALSE, request);
	        //To Page's questions to validate against XML elements, add Structure Numeric dummy question (_Unit one) in list - pass Boolean.TRUE
	        Map<String, MappedFromToQuestionFieldsDT> toPageQuestionMap = getQuestionsForValidationByTemplateUid(portPageForm.getToPageWaTemplateUid(), Boolean.TRUE, request);
	        
	        Map<String, MappedFromToQuestionFieldsDT> toPageCoreQuestionMap = getCoreQuestionsByPage(portPageForm.getToPageWaTemplateUid(), request);
	        
	        //Making copies of fromPage and toPage question Maps.Which can be used further.
	        Map<String,MappedFromToQuestionFieldsDT> refFromPageMap = new HashMap<String,MappedFromToQuestionFieldsDT>();
	        refFromPageMap.putAll(fromPageQuestionMap);
	        
	        Map<String,MappedFromToQuestionFieldsDT> refToPageMap = new HashMap<String,MappedFromToQuestionFieldsDT>();
	        refToPageMap.putAll(toPageQuestionMap);
	        
	        //map to hold repeating to repeating mapped questions. It used to remove repeating to repeating mapped questions from ToQuestionID dropdown.
	        Map<String,MappedFromToQuestionFieldsDT> repeatToRepeatMap = new HashMap<String,MappedFromToQuestionFieldsDT>();
			
	        Collection<Object> eDXActivityLogDTDetails = new ArrayList<Object>();
	        //Setting the required fields of EDX_Activity_LOG_DT
	        EDXActivityLogDT edxActivityLogDT = populateEdxActivityLogDT(null, portPageForm.getMapName(), IMPORT_EXPORT_STATUS_SUCCESS, "I", mappingSourceSystem,null);
        	
			ArrayList<Object> mapList = new ArrayList<Object>();
			Map<String,MappedFromToQuestionFieldsDT> repeatToDiscreteMap = new HashMap<String,MappedFromToQuestionFieldsDT>();
	        MappedElement[] mappingElementArray = mapping.getMappedElementArray();
	        for(int i=0;i<mappingElementArray.length;i++){
	        	String fromQuestionId = mappingElementArray[i].getFromQuestionUid();
	        	String fromAnswer = mappingElementArray[i].getFromAnswer();
	        	String fromCodeSetNm = mappingElementArray[i].getFromCodeSetNm();
	        	String fromDataType = mappingElementArray[i].getFromDataType();
	        	Long fromNbsUiComponentUid = mappingElementArray[i].getFromNbsUiComponentUid();
	        	String toQuestionId = mappingElementArray[i].getToQuestionId();
	        	String toAnswer = mappingElementArray[i].getToAnswer();
	        	String toCodeSetNm = mappingElementArray[i].getToCodeSetNm();
	        	String toDataType = mappingElementArray[i].getToDataType();
	        	Long toNbsUiComponentUid = mappingElementArray[i].getToNbsUiComponentUid();
	        	String mappingStatus = mappingElementArray[i].getMappingStatus();
	        	String questionMapped = mappingElementArray[i].getQuestionMapped();
	        	String answerMapped = mappingElementArray[i].getAnswerMapped();
	        	int blockIdNbr = mappingElementArray[i].getBlockIdNbr();
	        	int answerGroupSeqNbr = mappingElementArray[i].getAnswerGroupSeqNbr();
	        	String conversionType = mappingElementArray[i].getConversionType();
	        	
	        	MappedFromToQuestionFieldsDT mappedFromToQuestionDTFromXML = new MappedFromToQuestionFieldsDT();
	        	mappedFromToQuestionDTFromXML.setFromQuestionId(fromQuestionId);
	        	mappedFromToQuestionDTFromXML.setFromCode(fromAnswer);
	        	mappedFromToQuestionDTFromXML.setFromCodeSetNm(fromCodeSetNm);
	        	mappedFromToQuestionDTFromXML.setFromDataType(fromDataType);
	        	mappedFromToQuestionDTFromXML.setFromNbsUiComponentUid(fromNbsUiComponentUid);
	        	mappedFromToQuestionDTFromXML.setToQuestionId(toQuestionId);
	        	mappedFromToQuestionDTFromXML.setToCode(toAnswer);
	        	mappedFromToQuestionDTFromXML.setToCodeSetNm(toCodeSetNm);
	        	mappedFromToQuestionDTFromXML.setToDataType(toDataType);
	        	mappedFromToQuestionDTFromXML.setToNbsUiComponentUid(toNbsUiComponentUid);
	        	mappedFromToQuestionDTFromXML.setStatusCode(mappingStatus);
	        	mappedFromToQuestionDTFromXML.setQuestionMappedCode(questionMapped);
	        	mappedFromToQuestionDTFromXML.setAnswerMappedCode(answerMapped);
	        	mappedFromToQuestionDTFromXML.setBlockIdNbr(blockIdNbr);
	        	mappedFromToQuestionDTFromXML.setAnswerGroupSeqNbr(answerGroupSeqNbr);
	        	mappedFromToQuestionDTFromXML.setConversionType(conversionType);
	        	
	        	//If conversionType is NBSCaseAnsToCore then set it in to toPageQuestionMap, this map has only non core questions so add core question in it.
	        	
	        	if(NBS_CASE_ANS_TO_CORE_MAPPING.equals(conversionType)){
	        		if(!toPageQuestionMap.containsKey(toQuestionId))
	        			toPageQuestionMap.put(toQuestionId, toPageCoreQuestionMap.get(toQuestionId));
	        	}
	        	
	        	
	        	// look up fromPageQuestionMap for fromQuestionId as key, which gives MappedFromToQuestionFieldsDT
	        	//validate fromAnswer, fromCodeSetNm, fromDataType, fromNbsUiComponentUid against MappedFromToQuestionFieldsDT
	        	
        		boolean isFromMatch = true;
        		boolean isToMatch = true;
        		boolean isCodeNotExist = false;
        		StringBuffer buff=new StringBuffer();
	        	if(fromPageQuestionMap.containsKey(fromQuestionId)){        //checks for fromQuestionId if it is present  in selected FROM PAGE.If not,ignores that map.
	        		//checks for null and empty and avoids checking CORE questions in map.
	        		MappedFromToQuestionFieldsDT fromPageDT1 = (MappedFromToQuestionFieldsDT)fromPageQuestionMap.get(fromQuestionId);
	        		MappedFromToQuestionFieldsDT fromPageDT = (MappedFromToQuestionFieldsDT)fromPageDT1.deepCopy();//created deepcopy() to avoid cross referencing as this one is get added in Map and list.
	        		MappedFromToQuestionFieldsDT toPageDT = (MappedFromToQuestionFieldsDT)toPageQuestionMap.get(toQuestionId);
	        		if(fromQuestionId!=null && fromQuestionId.length()>0 && !fromQuestionId.trim().equalsIgnoreCase("") && !NBS_QA_MAPPING_STATUS_CORE.equalsIgnoreCase(mappingStatus)){
	        			if(!fromPageDT.getFromDataType().equals(fromDataType)){
	        				buff.append("FROM_DATA_TYPE_MISMATCH ");
	        				isFromMatch=false;
	        			}
	        			if(!fromPageDT.getFromNbsUiComponentUid().equals(fromNbsUiComponentUid)){
	        				buff.append("FROM_NBS_COMPONENT_UID_MISMATCH ");
	        				isFromMatch=false;
	        			}
	        			if(DATA_TYPE_CODED.equals(fromPageDT.getFromDataType()) && DATA_TYPE_CODED.equals(fromDataType)){
	        				if(!fromPageDT.getFromCodeSetNm().equals(fromCodeSetNm)){
	        					buff.append("FROM_CODE_SET_NM_MISMATCH ");
	        					isFromMatch=false;
	        				}else if(!NBS_QA_MAPPING_STATUS_AUTO_MAPPED.equals(mappingStatus) && MAPPED_QA_YN_Y.equals(questionMapped) && fromAnswer.length()>0){
		        				ArrayList<Object> fromCodeList = (ArrayList<Object>) getCodeListByCodeSetNm(fromPageDT.getFromCodeSetNm());
		        				ArrayList codeList = new ArrayList();
		        				
		        				for(int j=0;j<fromCodeList.size();j++){
		        					CodeValueGeneralDT cvgDT = (CodeValueGeneralDT)fromCodeList.get(j);
		        					codeList.add(cvgDT.getCode());
		        				}
	        				     //checks if fromAnswer contains in selected from page.
		        				//Skipping map with convversion type TWO_DISCRETE_TO_ONE_CODED_MAPPING because the answers are modified by CorrectDataBeforeConversion stored procedure. (Called before conversion starts.)
		        				if(!codeList.contains(fromAnswer) && !TWO_DISCRETE_TO_ONE_CODED_MAPPING.equals(conversionType)){
	        					     buff.append("The From Code "+fromAnswer+" in the Imported Map doesn't exist in Selected From Page.");
	        					     isFromMatch=false;
	        					     isCodeNotExist=true;                               
		        				}
	        				}
	        			}
	        			
	        			if(answerGroupSeqNbr>0 && fromPageDT.getAnswerGroupSeqNbr()!=null && fromPageDT.getAnswerGroupSeqNbr()<0){
	        				buff.append("REPEATING_TO_DISCRETE_CONFLICT");
	        			}
	        			
	        			//checks From Page Question matching or not ,
	        		    if(isFromMatch && toQuestionId != null && toPageQuestionMap.containsKey(toQuestionId)){
	        			    if(!toPageDT.getFromDataType().equals(toDataType)){ // as we are reusing getQuestionsToBeMappedByWaTemplateUid method, its populating From side of MappedFieldQuestionAnswerDT. 
	        			    	buff.append("TO_DATA_TYPE_MISMATCH ");
	        			    	isToMatch = false;
	        				
	        			    }
	        			
	        			    if(!toPageDT.getFromNbsUiComponentUid().equals(toNbsUiComponentUid)){
	        			    	buff.append("TO_NBS_COMPONENT_UID_MISMATCH");
	        			    	isToMatch = false;
	        			    }
	        			
	        			    if(toPageDT.getQuestionGroupSeqNbr()==null && blockIdNbr>0){
	        			    	buff.append("DISCRETE_TO_REPEATING_CONFLICT");
	        			    }
	        			    
	        			if(DATA_TYPE_CODED.equals(toPageDT.getFromDataType()) && DATA_TYPE_CODED.equals(toDataType)){
	        				if(!toPageDT.getFromCodeSetNm().equals(toCodeSetNm)){
	        					buff.append("TO_CODE_SET_NM_MISMATCH ");
	        					isToMatch = false;
	        				}else if(!NBS_QA_MAPPING_STATUS_AUTO_MAPPED.equals(mappingStatus) && MAPPED_QA_YN_Y.equals(questionMapped) && toAnswer.length()>0){
	        						ArrayList<Object> toCodeList = (ArrayList<Object>) getCodeListByCodeSetNm(toPageDT.getFromCodeSetNm());
	        						ArrayList codeList = new ArrayList();
		        				
		        				for(int j=0;j<toCodeList.size();j++){
		        					CodeValueGeneralDT cvgDT = (CodeValueGeneralDT)toCodeList.get(j);
		        					codeList.add(cvgDT.getCode());
		        				}
	        					//checks if toAnswer in Imported map is present in selected To Page.
		        				
		        				//In case of preloaded map by script if Answer contains OTH^negative then extract only OTH
		        				if(toAnswer.startsWith(OTHER+"^")){
		        					toAnswer = OTHER;
		        				}
	        					if(!codeList.contains(toAnswer)){
	        						buff.append("The To Code "+toAnswer+" in the Imported Map doesn't exist in selected To Page.");
	        						isToMatch = false;
	        						isCodeNotExist=true;
	        					}
	        				}
	        			}
	        		    }else if (!toPageQuestionMap.containsKey(toQuestionId) && toQuestionId.length()>0){  
	        		    	buff.append("TO_QUESTION_MISSING_IN_TO_PAGE");
	        		    	isToMatch = false;                               //if to question contains in map and not contains in To page
	        		    }
	        			
	        		    //If conversion_type is NBSCaseAnsToCore or TwoCodedToOneCoded then dont lookup mapping based on question. Use conversion_type comes in Map
	        		    if(NBS_CASE_ANS_TO_CORE_MAPPING.equals(conversionType) || TWO_DISCRETE_TO_ONE_CODED_MAPPING.equals(conversionType)){
	        		    	fromPageDT.setConversionType(mappedFromToQuestionDTFromXML.getConversionType());
	        		    }else{
		        		    //set conversion type if it matches based on standard scenario otherwise display as mapping needed. Also for custom mapping set conversionType whatever comes in map.
		        		    String conversionTypeFromQuestions = getConversionType(fromPageDT,toPageDT);
		        		    if(conversionTypeFromQuestions == null || (conversionTypeFromQuestions!=null && conversionTypeFromQuestions.equals(mappedFromToQuestionDTFromXML.getConversionType()))){
		        		    	fromPageDT.setConversionType(mappedFromToQuestionDTFromXML.getConversionType());
		        		    }else{
		        		    	isFromMatch = false;
		        		    	isToMatch = false;
		        		    }
		        		}
	        		    
	        		    //Storing in mappedQuestionsMap only if From Question And To Question matches with Imported Map.
	        		    if(isFromMatch && isToMatch){
	    	        		if(NBS_QA_MAPPING_STATUS_AUTO_MAPPED.equals(mappingStatus)){
	    	        			fromPageDT.setStatusDesc(STATUS_AUTO_MAPPED);
	    	        			fromPageDT.setStatusCode(mappingStatus);
	    	        		}else if(NBS_QA_MAPPING_STATUS_COMPLETE.equals(mappingStatus)){
	    	        			fromPageDT.setStatusDesc(STATUS_COMPLETE);
	    	        			fromPageDT.setStatusCode(mappingStatus);
	    	        		}else if(NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK.equals(mappingStatus)){
	    	        			fromPageDT.setStatusDesc(STATUS_COMPLETE_REPEATING_BLOCK);
	    	        			fromPageDT.setStatusCode(mappingStatus);
	    	        		}
	    	        		fromPageDT=populateFromToMappedDT(fromPageDT, toPageDT,mappedFromToQuestionDTFromXML);
	    	        		
	    	        		ArrayList<Object> fromFieldList = new ArrayList<Object>();
	    	        		//MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) fromPageDT.deepCopy();
	    	        		//Here only for CODED to CODED questions ,adding the dt's in the MappedQuestionsMap, inorder to populate the answer dt's based on the codes on answer page.
	    	        		//For other scenario's coded-text,coded-numeric,text-coded,text-numeric etc are stored as single dt . 
	    	        		if(toPageDT != null && DATA_TYPE_CODED.equals(fromPageDT.getFromDataType()) && DATA_TYPE_CODED.equals(toPageDT.getFromDataType())){
	    	        			if(portPageForm.getMappedQuestionsMap().containsKey(fromQuestionId)){
	    	        				fromFieldList=portPageForm.getMappedQuestionsMap().get(fromQuestionId);
	    	        			}
	    	        		    	fromFieldList.add(fromPageDT);
	    	        		    
	    	        			//If the fromQuestion is repeating ,then adding the dt's into the mappedQuestionsMap without replacing the previous question dt which has same fromQuestionID
	    	        		}else if(fromPageDT.getAnswerGroupSeqNbr()!=null && fromPageDT.getAnswerGroupSeqNbr()>0){
	    	        			//setting fromCode as "" blank if it is null.Because of "" checks in the mapAnswer method in PortPageAction.
	    	        			if(fromPageDT.getFromCode()==null){
	    	        				fromPageDT.setFromCode("");
	    	        			}
	    	        			
	    	        			if(portPageForm.getMappedQuestionsMap().containsKey(fromQuestionId)){
	    	        				
	    	        				if(!repeatToDiscreteMap.containsKey(fromPageDT.getFromQuestionId()+"|"+fromPageDT.getAnswerGroupSeqNbr())){
	    	        					fromFieldList=portPageForm.getMappedQuestionsMap().get(fromQuestionId);
                                        fromFieldList.add(fromPageDT);
	    	        				}else{
	    	        					fromFieldList=portPageForm.getMappedQuestionsMap().get(fromQuestionId);
	    	        				}
	    	        			}else{
	    	        				fromFieldList.add(fromPageDT);
	    	        			}
                          }else{
                        	  
                        	  if(ONE_IND_TO_MANY_QUES_MAPPING.equals(fromPageDT.getConversionType()) && portPageForm.getMappedQuestionsMap().containsKey(fromQuestionId)){ //Added for varicella mapping VAR207 - mapped to date and coded question
                        		  fromFieldList=portPageForm.getMappedQuestionsMap().get(fromQuestionId);
                        	  }
                        	  fromFieldList.add(fromPageDT);
                          }

	    	        		portPageForm.getMappedQuestionsMap().put(fromPageDT.getFromQuestionId(), fromFieldList);
	    	        		
	    	        		if(toPageDT != null && fromPageDT.getBlockIdNbr()<1){   
	    	        			refToPageMap.remove(toPageDT.getFromQuestionId());    //removing the dt after storing in the mappedQuestionsMap.And also skipping the questions which are repeating on To Page to be available to map for any fromQuestion.
	    	        		}else if(fromPageDT.getBlockIdNbr()>0 && toPageDT !=null){   //here, if the mapped question has block id number ,then setting the block id number for that TO Question ,so that after importing if the user tries to map that question then it will prepopulate the highest block id number.
	    	        			MappedFromToQuestionFieldsDT toDT = refToPageMap.get(toPageDT.getFromQuestionId());
	    	        		     if(toDT.getBlockIdNbr()==null ||toDT.getBlockIdNbr()<fromPageDT.getBlockIdNbr()){
	    	        		    	 toDT.setBlockIdNbr(fromPageDT.getBlockIdNbr());
	    	        		     }
	    	        		}
	    	        	
	        		    }else if(!isCodeNotExist){            //If From Code or To Code doesn't exist in selected From page and To Pages,then skipping that from or to codes adding into the answer page.
	        		    	if(fromPageDT.getQuestionGroupSeqNbr()!=null && fromPageDT.getQuestionGroupSeqNbr()>0){
	        		    		fromPageDT.setStatusCode(NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R);
                                fromPageDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
	        		    	}else{
	        		    		fromPageDT.setStatusCode(NBS_QA_MAPPING_STATUS_MAPPING_NEEDED);
	        		    		fromPageDT.setStatusDesc(STATUS_MAPPING_NEEDED);
	        		    	}
	    	        		EDXActivityDetailLogDT detailDT=populateEDXActivityDetailLogDT(fromPageDT.getFromQuestionId(),"NBS Question", fromPageDT.getFromLabel(), "Vocab","The following are conflicts between Imported Map "
	    	        				                                                            + "and Selected From and To pages: "+ buff.toString());
	    	        		eDXActivityLogDTDetails.add(detailDT);
	    	        		isError=true;
	    	        	}
	        		    //Checking if fromPageDT is already into mapList or not.If not ,then adds into mapList,otherwise do not add. 
	        		    if(refFromPageMap.get(fromPageDT.getFromQuestionId())!=null && (fromPageDT.getAnswerGroupSeqNbr()==null || fromPageDT.getAnswerGroupSeqNbr()<1)){
	        		    	mapList.add(fromPageDT);
	        		    }else if(fromPageDT.getAnswerGroupSeqNbr()!=null && fromPageDT.getAnswerGroupSeqNbr()>0){ //Here,for repeating to discrete type of mapping adding into Separate Map.
	        		       if(!repeatToDiscreteMap.containsKey(fromPageDT.getFromQuestionId()+"|"+fromPageDT.getAnswerGroupSeqNbr())){
	        		    	   //MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) fromPageDT.deepCopy();
	        		    	   repeatToDiscreteMap.put(fromPageDT.getFromQuestionId()+"|"+fromPageDT.getAnswerGroupSeqNbr(),fromPageDT); 
	        		       }
	        		    }
	        			refFromPageMap.remove(fromPageDT.getFromQuestionId());
	        		}
	        			//if it is core removing that dt from reference map.
                    if(NBS_QA_MAPPING_STATUS_CORE.equalsIgnoreCase(mappingStatus)){
	        		    refFromPageMap.remove(fromPageDT.getFromQuestionId());
	        		    refToPageMap.remove(toPageDT.getFromQuestionId());
	        		}
                    
                    //Add repeating to repeating mapped questions in repeatToRepeatMap
                    if(fromPageDT!=null && toPageDT!=null && fromPageDT.getQuestionGroupSeqNbr()!=null && toPageDT.getQuestionGroupSeqNbr()!=null){
                    	repeatToRepeatMap.put(fromPageDT.getFromQuestionId(), fromPageDT);
                    }
                    
	        	      logger.debug("fromQuestionId: "+fromQuestionId+", toQuestionId"+toQuestionId+", mappingStatus: "+mappingStatus);
	            }
	        }
	        
	        //Auto-Mapping the remaining questions on From Page and To Page if they match the Auto-Map criteria.
	        if(refFromPageMap!=null){
	        	String queId = null;
	        	Collection<String> fromQueIdList= refFromPageMap.keySet();
	        	Iterator<String> iter = fromQueIdList.iterator();
	        	while(iter.hasNext()){
	        		queId = iter.next();
	        		if(refToPageMap.containsKey(queId)){
	        			MappedFromToQuestionFieldsDT fromDT = refFromPageMap.get(queId);
	        			MappedFromToQuestionFieldsDT toDT = refToPageMap.get(queId);
	        			boolean autoMapped = isAutoMappedForImport(fromDT,toDT);
	        			if(autoMapped){
	        				fromDT.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_AUTO_MAPPED);
	        				fromDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_AUTO_MAPPED, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
	        				fromDT.setQuestionMappedCode(PortPageUtil.MAPPED_QA_YN_Y);
	        				fromDT.setQuestionMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.CODE_SET_YN));
	        				fromDT.setFieldMappingRequired(true);
							
							if(toDT!=null){
								fromDT.setToQuestionId(toDT.getFromQuestionId());
								fromDT.setToLabel(toDT.getFromLabel());
								fromDT.setToDataType(toDT.getFromDataType());
								fromDT.setToCodeSetNm(toDT.getFromCodeSetNm());
								fromDT.setToCodeSetGroupId(toDT.getCodeSetGroupId());
								fromDT.setToNbsUiComponentUid(toDT.getFromNbsUiComponentUid());
								fromDT.setToDbLocation(toDT.getFromDbLocation());
								//Removing automapped From and To questions ,from refFromPageMap and refToPagemap.So,no longer there will be duplicates in MapList.
								iter.remove();
								refFromPageMap.remove(fromDT.getFromQuestionId());
			        		    refToPageMap.remove(toDT.getFromQuestionId());
							}
	
							//Add automapped question in mapped question list
							ArrayList<Object> fromFieldDTList = new ArrayList<Object>();
							fromFieldDTList.add(fromDT);
							portPageForm.getMappedQuestionsMap().put(fromDT.getFromQuestionId(), fromFieldDTList);
							mapList.add(fromDT);
	        			}
	        		}
	        		
	        	}
	        }
	        mapList.addAll(repeatToDiscreteMap.values());
	        //Iterating through remaining elements in refFromPageMap and modifying the status to Mapping Needed(R) if it is a repeating block question.
	        ArrayList<MappedFromToQuestionFieldsDT> updatedFromList=updateRepeatingBlockStatus(refFromPageMap.values());
	        
	        //Remove repeating to repeating mapped questions from ToQuestionID dropdown.
	        Collection<String> fromQueIdList= repeatToRepeatMap.keySet();
        	Iterator<String> iter1 = fromQueIdList.iterator();
        	while(iter1.hasNext()){
        		String queId = iter1.next();
        		MappedFromToQuestionFieldsDT fromDT = repeatToRepeatMap.get(queId);
        		if(refToPageMap.containsKey(fromDT.getToQuestionId())){
        			refToPageMap.remove(fromDT.getToQuestionId());
        		}
        	}
        	
	        mapList.addAll(updatedFromList);                            //adding all the questions left in reference map to mapList.i.e.,questions which are extra in selected from page.
	        portPageForm.setFromPageQuestions(mapList);
	        portPageForm.getToPageQuestions().addAll(refToPageMap.values());
	        if(toPageQuestionMap!=null)
	        	portPageForm.getDisplayToQuestionsMap().putAll(toPageQuestionMap);    //copying all the selected To Page Questions to displayToQuestionsMap whether the question is already mapped or not.
	        if(isError){
	        	Long edxActivityLogUid = createEdxActivityLog(edxActivityLogDT, request);
				edxActivityLogDT.setEdxActivityLogUid(edxActivityLogUid);
				Iterator iter = eDXActivityLogDTDetails.iterator();
				while(iter.hasNext()){
					EDXActivityDetailLogDT detailDT = (EDXActivityDetailLogDT) iter.next();
					detailDT.setEdxActivityLogUid(edxActivityLogUid);
				}
	        	edxActivityLogDT.setEDXActivityLogDTWithQuesDetails(eDXActivityLogDTDetails);
	        	createEdxActivityDetailsLog(edxActivityLogDT, request);
	        }else{
	        	Long edxActivityLogUid = createEdxActivityLog(edxActivityLogDT, request);
	        }
	        
	        // Create record in NBS_Conversion_port_mgmt where insert xml payload.
	        // get primary key from NBS_Converison_port_mgmt and use populated list of MappedFromToQuestionFieldsDT, insert into wa_conversion_mapping
		}catch (Exception ex) {
			logger.fatal("Error in importMapping in  PortPageUtil: "+ex.getMessage(), ex);
			//Extract exception details
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			String exceptionMessage = errors.toString();
			String errorMsgToStore = exceptionMessage.substring(0,Math.min(exceptionMessage.length(), 2000));
			EDXActivityLogDT dt = populateEdxActivityLogDT(null, portPageForm.getMapName(), IMPORT_EXPORT_STATUS_FAILURE, "I", mappingSourceSystem,errorMsgToStore);
			createEdxActivityLog(dt, request);
			throw new Exception(ex.toString());
	    }
	}
	
	/**
	 * @param edxActivityLogDT
	 * @param request
	 * @return edx_activity_log_uid, set it in edxActivityLogUid of EdxActivityLogDT while updating EdxActivityDetailLog
	 * @throws Exception
	 */
	public Long createEdxActivityLog(EDXActivityLogDT edxActivityLogDT, HttpServletRequest request) throws Exception{
		try{
		   	Object[] oParams = new Object[] {edxActivityLogDT};
			String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
			String sMethod = "insertEDXActivityLog";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			Long edxActivityLogUid = (Long) obj;
			return edxActivityLogUid;
		}catch (Exception ex) {
			logger.fatal("Error in updateEdxActivityLog in  PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
	    }
	}
	
	
	/**
	 * @param edxActivityLogDT
	 * @param request
	 * @throws Exception
	 */
	public void createEdxActivityDetailsLog(EDXActivityLogDT edxActivityLogDT, HttpServletRequest request) throws Exception{
		try{
		   	Object[] oParams = new Object[] {edxActivityLogDT};
			String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
			String sMethod = "insertEDXActivityDetailsLog";
			CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
		}catch (Exception ex) {
			logger.fatal("Error in updateEdxActivityLog in  PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
	    }
	}
	
	/**
	 * @param nbsConversionPageMgmtUid
	 * @param mappingName
	 * @param processStatus
	 * @param impExpInd
	 * @param msgSendingFacility
	 * @param exceptionStr
	 * @return
	 * @throws Exception
	 */
	public EDXActivityLogDT populateEdxActivityLogDT(Long nbsConversionPageMgmtUid, String mappingName, String processStatus, String impExpInd, String msgSendingFacility, String exceptionStr) throws Exception{
		try{
			EDXActivityLogDT dt = new EDXActivityLogDT();
			dt.setSourceUid(nbsConversionPageMgmtUid);
		   	dt.setTargetUid(null);
		   	dt.setDocType(DOC_TYPE_MAPPING);
		   	dt.setDocName(mappingName);
		   	dt.setRecordStatusCd(processStatus);
		   	dt.setRecordStatusTime(new Timestamp(new Date().getTime()));
		   	dt.setException(exceptionStr);
		   	dt.setImpExpIndCd(impExpInd);
		   	dt.setSourceTypeCd(null);
		   	dt.setSrcName(msgSendingFacility);
		   	dt.setTargetTypeCd(DOC_TYPE_MAPPING);
		   	dt.setBusinessObjLocalId(null);	
		   	return dt;
		}catch (Exception ex) {
			logger.fatal("Error in populateEdxActivityLogDT in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
	    }
	}

	/**
	 * @param waTemplateUid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, MappedFromToQuestionFieldsDT> getQuestionsForValidationByTemplateUid(Long waTemplateUid, Boolean addSNDummyQue, HttpServletRequest request) throws Exception{
		try{
			Object[] oParams = new Object[] {waTemplateUid, addSNDummyQue};
			String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
			String sMethod = "getQuestionsToBeMappedByWaTemplateUid";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			Map<String, MappedFromToQuestionFieldsDT> questionMap = (Map<String, MappedFromToQuestionFieldsDT>) obj;
			return questionMap;
		}catch (Exception ex) {
			logger.fatal("Error in getQuestionsForValidationByTemplateUid in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
	    }
	}
	
	public static EDXActivityDetailLogDT populateEDXActivityDetailLogDT(String recordId, String recordType, String recordName, String logType, String comment ){
		EDXActivityDetailLogDT detailLogDT =  new EDXActivityDetailLogDT();
		try{
			detailLogDT.setRecordId(recordId);
			detailLogDT.setRecordType(recordType);
			detailLogDT.setRecordName(recordName);
			detailLogDT.setLogType(logType);
			detailLogDT.setComment(comment);
		}catch(Exception ex){
			logger.error("recordId: "+recordId+", recordType: "+recordType+", recordName: "+recordName+" Exception = "+ex.getMessage(), ex);
		}
		return detailLogDT;
		
	}
	
	public static MappedFromToQuestionFieldsDT populateFromToMappedDT(MappedFromToQuestionFieldsDT fromPageDT,MappedFromToQuestionFieldsDT toPageDT,MappedFromToQuestionFieldsDT mappedFromToQuestionDTFromXML) throws Exception{
		
		try{
		
			fromPageDT.setAnswerGroupSeqNbr(mappedFromToQuestionDTFromXML.getAnswerGroupSeqNbr());
	
			if(mappedFromToQuestionDTFromXML.getAnswerMappedCode().equals(MAPPED_QA_YN_Y)){
				fromPageDT.setCodeMappingRequired(true);
			}else{
				fromPageDT.setCodeMappingRequired(false);
			}
			
			fromPageDT.setBlockIdNbr(mappedFromToQuestionDTFromXML.getBlockIdNbr());
			if(mappedFromToQuestionDTFromXML.getAnswerGroupSeqNbr()==null || mappedFromToQuestionDTFromXML.getAnswerGroupSeqNbr()==0){ // if question is non repeating block set questonGroupSeq number null, for repeating block no need to change it.
				//fromPageDT.setQuestionGroupSeqNbr(null);
			    fromPageDT.setAnswerGroupSeqNbr(null);                 
			}
			fromPageDT.setQuestionMappedCode(mappedFromToQuestionDTFromXML.getQuestionMappedCode());
			
			if(mappedFromToQuestionDTFromXML.getQuestionMappedCode().equals(MAPPED_QA_YN_Y)){
				fromPageDT.setFieldMappingRequired(true);
			}else{
				fromPageDT.setFieldMappingRequired(false);
			}
			
			fromPageDT.setQuestionMappedDesc(CachedDropDowns.getCodeDescTxtForCd(mappedFromToQuestionDTFromXML.getQuestionMappedCode(), CODE_SET_YN));
			fromPageDT.setFromCodeDesc(CachedDropDowns.getCodeDescTxtForCd(mappedFromToQuestionDTFromXML.getFromCode(),fromPageDT.getFromCodeSetNm()));
			fromPageDT.setFromCode(mappedFromToQuestionDTFromXML.getFromCode());
	
			
			if(toPageDT!=null){
				fromPageDT.setToQuestionId(toPageDT.getFromQuestionId());
				
				fromPageDT.setToQuestionGroupSeqNbr(toPageDT.getQuestionGroupSeqNbr());
				
				if(fromPageDT.getBlockIdNbr() != null && fromPageDT.getBlockIdNbr()>0){
					//fromPageDT.setToLabel(toPageDT.getFromLabel()+" ("+fromPageDT.getBlockIdNbr()+")");
					//setting the to question label based on block id number.
					PortPageUtil.updateToQueLabelForRepeatingTypesMapping(fromPageDT,fromPageDT.getBlockIdNbr(),toPageDT.getFromLabel());
				}else{
					fromPageDT.setToLabel(toPageDT.getFromLabel());
				}
				
				fromPageDT.setToDataType(toPageDT.getFromDataType());
				fromPageDT.setToCodeSetNm(toPageDT.getFromCodeSetNm());
				fromPageDT.setToCodeSetGroupId(toPageDT.getCodeSetGroupId());
				fromPageDT.setToNbsUiComponentUid(toPageDT.getFromNbsUiComponentUid());
				fromPageDT.setToDbLocation(toPageDT.getFromDbLocation());
				// Only for coded to coded map answers. As every system has different answers, for text to coded, text to numeric and numeric to coded type let user map. So ignore mapped answers from imported XML.
				if(DATA_TYPE_CODED.equals(fromPageDT.getFromDataType()) && DATA_TYPE_CODED.equals(toPageDT.getFromDataType()) || ONE_IND_TO_MANY_QUES_MAPPING.equals(fromPageDT.getConversionType())){
					fromPageDT.setToCodeDesc(CachedDropDowns.getCodeDescTxtForCd(mappedFromToQuestionDTFromXML.getToCode(),toPageDT.getFromCodeSetNm()));
					fromPageDT.setToCode(mappedFromToQuestionDTFromXML.getToCode());
					
				}else{//Don't use incoming value from XML, get from DB for current system. 
					fromPageDT.setFromCode(null);
					fromPageDT.setFromCodeDesc(null);
				}
				
				fromPageDT.setAnswerMappedCode(mappedFromToQuestionDTFromXML.getAnswerMappedCode());
				
				if(fromPageDT.getAnswerMappedCode().length()<0 && fromPageDT != null){
					fromPageDT.setAnswerMappedDesc(mappedFromToQuestionDTFromXML.getAnswerMappedDesc());
				}else{
					fromPageDT.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(mappedFromToQuestionDTFromXML.getAnswerMappedCode(), CODE_SET_YN));
				}
				
			}
			return fromPageDT;
		}catch(Exception ex){
			logger.fatal("Exception occured while populating From PageDT with From Question Id:"+fromPageDT.getFromQuestionId()+" and To Question Id:"+toPageDT.getFromQuestionId()+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		
	}
	
	public static AnswerMappingDT populateAnswerDT(MappedFromToQuestionFieldsDT fromPageDT) throws Exception{
		try{
			AnswerMappingDT ansDT = new AnswerMappingDT();
			
			ansDT.setQuestionIdentifier(fromPageDT.getFromQuestionId());
			ansDT.setQuestionLabel(fromPageDT.getFromLabel());
			ansDT.setCodeSetGroupId(fromPageDT.getCodeSetGroupId());
			ansDT.setCodeSetNm(fromPageDT.getFromCodeSetNm());
			ansDT.setCode(fromPageDT.getFromCode());
			ansDT.setCodeDescTxt(fromPageDT.getFromCodeDesc());
			
			ansDT.setToCode(fromPageDT.getToCode());
			ansDT.setToCodeDescTxt(fromPageDT.getToCodeDesc());
			
			ansDT.setMapped(fromPageDT.getAnswerMappedCode());
			ansDT.setMappedDesc(fromPageDT.getAnswerMappedDesc());
			ansDT.setStatus(fromPageDT.getStatusDesc());
			return ansDT;
		}catch(Exception ex){
			logger.fatal("Exception populateAnswerDT:"+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}

	public static int getMaxAnswerGroupSequenceNumber(ArrayList fromQueList) throws Exception{
		int answerGroupSequnceNumber = 0;
		try{
			for(int i=0;i<fromQueList.size();i++){
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) fromQueList.get(i);
				if(dt!=null && dt.getAnswerGroupSeqNbr()!=null && dt.getAnswerGroupSeqNbr().intValue() > answerGroupSequnceNumber)
					answerGroupSequnceNumber = dt.getAnswerGroupSeqNbr();
			}
			
			return answerGroupSequnceNumber;
		}catch (Exception ex) {
			logger.fatal("Error in getMaxAnswerGroupSequenceNumber in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
	    }
	}
	
	/**
	 * @param questionList
	 * @param methodName
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static String getQuestionFieldValue(ArrayList questionList, String methodName, String className) throws Exception{
		String value = null;
		try{
			Class myClass = Class.forName(className);
			Method method = myClass.getMethod(methodName, new Class[] {});
			for(int i=0;i<questionList.size();i++){
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) questionList.get(i);
				Object obj = method.invoke(dt, new Object[] {});
				if(obj!=null){
					 value = (String)obj;
					 if(value.length()>0){
						 return value;
					 }
				}
			}
		}catch(Exception ex){
			logger.fatal("Error in getQuestionFieldValue in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return value;
	}
	
	/**
	 * @param fromPageDT
	 * @param toPageDT
	 * @return
	 * @throws Exception
	 */
	private static boolean isAutoMappedForImport(MappedFromToQuestionFieldsDT fromPageDT, MappedFromToQuestionFieldsDT toPageDT) throws Exception{
		boolean isAutoMapped = false;
		try{
			if(fromPageDT.getFromQuestionId().equals(toPageDT.getFromQuestionId()) 
					&& fromPageDT.getFromDataType().equals(toPageDT.getFromDataType())
					&& fromPageDT.getFromNbsUiComponentUid().longValue()==toPageDT.getFromNbsUiComponentUid().longValue()
					&& ((fromPageDT.getFromCodeSetNm()==null && toPageDT.getFromCodeSetNm()==null) || (fromPageDT.getFromCodeSetNm().equals(toPageDT.getFromCodeSetNm())))
					&& ((fromPageDT.getQuestionGroupSeqNbr()==null && toPageDT.getQuestionGroupSeqNbr()==null) ||(fromPageDT.getQuestionGroupSeqNbr()!=null && toPageDT.getQuestionGroupSeqNbr()!=null))){
				isAutoMapped = true;
			}
		}catch(Exception ex){
			logger.fatal("Error in isAutoMappedForImport in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return isAutoMapped;
	}
	
	/**
	 * @param fromList
	 * @return
	 * @throws Exception
	 */
	private static ArrayList<MappedFromToQuestionFieldsDT> updateRepeatingBlockStatus(Collection<MappedFromToQuestionFieldsDT> fromList ) throws Exception{
		ArrayList<MappedFromToQuestionFieldsDT> fromList1 = new ArrayList<MappedFromToQuestionFieldsDT>();
		try{
			fromList1.addAll(fromList);
			for(int i=0;i<fromList.size();i++){
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) fromList1.get(i);
				
				if(dt.getQuestionGroupSeqNbr()!=null && dt.getQuestionGroupSeqNbr()>0){
					dt.setStatusCode(NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R);
					dt.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
				}
			}
		}catch(Exception ex){
			logger.fatal("Error in updateRepeatingBlockStatus in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return  fromList1;
	}
	
	/**
	 * @param questionList
	 * @param toQuestionIdentifier
	 * @return
	 * @throws Exception
	 */
	public static boolean isToQuestionMapped(ArrayList questionList, String toQuestionIdentifier) throws Exception{
		boolean isMapped = false;
		try{
			for(int i=0;i<questionList.size();i++){
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) questionList.get(i);
				if(NBS_QA_MAPPING_STATUS_COMPLETE.equals(dt.getStatusCode()) || NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK.equals(dt.getStatusCode())){
					if(toQuestionIdentifier!=null && toQuestionIdentifier.equals(dt.getToQuestionId())){
						isMapped = true;
						break;
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Error in updateRepeatingBlockStatus in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return isMapped;
	}
	
	public static ArrayList<Object> removeIfDuplicateToQuestion(ArrayList toQuestiondropDownList,String toID ) throws Exception{
		try{
	    	ArrayList<Object> toQueList = new ArrayList<Object>();
	    	for(int j=0;j<toQuestiondropDownList.size();j++){
	    		DropDownCodeDT ddDT1=(DropDownCodeDT) toQuestiondropDownList.get(j);
	    		if(!toID.equals(ddDT1.getKey())){
	    			toQueList.add(ddDT1);
	    		}
	    	}
			return toQueList;
		}catch(Exception ex){
			logger.fatal("Error in removeIfDuplicateToQuestion in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	/**
	 * @param fromQueList
	 * @param fromQueDT
	 * @param toQueDT
	 * @param mapAnotherInstance
	 * @return
	 * @throws Exception
	 */
	public static boolean validateRepeatingInstanceDatatype(ArrayList<Object> fromQueList, MappedFromToQuestionFieldsDT fromQueDT, MappedFromToQuestionFieldsDT toQueDT, String mapAnotherInstance) throws Exception{
		boolean isValidDataType = true;
		try{
			
			if(!"Yes".equalsIgnoreCase(mapAnotherInstance) && fromQueList.size()==1){ // If editing first instance dont check for datatype.
				isValidDataType = true;
				return isValidDataType;
			}
			String toDataType = fromQueDT.getToDataType();
			if(toDataType == null || toDataType.length()==0){
				toDataType = PortPageUtil.getQuestionFieldValue(fromQueList, "getToDataType","gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT");
			}
			
			//Validation repeating block instances should mapped to same datatypes of to questions.
			if(toDataType!=null && toDataType.length()>0 && (!toDataType.equals(toQueDT.getFromDataType()) || (PortPageUtil.DATA_TYPE_CODED.equals(toDataType) && fromQueDT.getToCodeSetGroupId()!=null && 
					toQueDT.getCodeSetGroupId()!=null && fromQueDT.getToCodeSetGroupId().longValue()!=toQueDT.getCodeSetGroupId().longValue()))) {
				isValidDataType = false;
			}
			return isValidDataType;
		}catch(Exception ex){
			logger.fatal("Error in validateRepeatingInstanceDatatype in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	
	/**
	 * While editing existing mapping for repeating to discrete, if new instance is added or edited from No to Yes then automap answers based on existing answers.
	 * @param repeatingMappedQueList
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Object> mapRepeatingBlockInstances(ArrayList<Object> repeatingMappedQueList) throws Exception{
		try{
			Map<String, String> answerMap = new HashMap<String, String>();
			ArrayList <Object> questionToSetAnswerList = new ArrayList<Object>();
			for(int i=0;i<repeatingMappedQueList.size();i++){
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) repeatingMappedQueList.get(i);
				if(dt.getFromCode()!=null && dt.getFromCode().length()>0 && dt.getToCode()!=null && dt.getToCode().length()>0)
					answerMap.put(dt.getFromCode(), dt.getToCode()); //add unique fromCode and related toCode in map for repeating block mapping
				
				if("Y".equals(dt.getQuestionMappedCode()) && 
						(dt.getFromCode()==null && dt.getToCode()==null || (dt.getFromCode()!=null && dt.getFromCode().length()==0 && dt.getToCode()!=null && dt.getToCode().length()==0))){
					questionToSetAnswerList.add(dt); // Add the newly added or edited (Mapping no to yes) question in list
				}
			}
			
			ArrayList<Object> repeatingMappedQueToAddList = new ArrayList<Object>();
			for(int i=0;i<questionToSetAnswerList.size();i++){ // loop through newly added question and set answers for it.
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) questionToSetAnswerList.get(i);
				boolean updateExisting = true;
				for (Map.Entry<String, String> entry : answerMap.entrySet()) {
				    String fromAnswer = entry.getKey();
				    String toAnswer = entry.getValue();
				    if(updateExisting){ //Update question in map.
				    	dt.setFromCode(fromAnswer);
				    	dt.setToCode(toAnswer);
				    	updateExisting = false;
				    }else{
				    	MappedFromToQuestionFieldsDT dt1 = (MappedFromToQuestionFieldsDT) dt.deepCopy(); // Create new instances of question/answer and add into list.
				    	dt1.setFromCode(fromAnswer);
				    	dt1.setToCode(toAnswer);
				    	repeatingMappedQueToAddList.add(dt1);
				    	repeatingMappedQueList.add(dt1);
				    }
				}
			}
			return repeatingMappedQueToAddList;
		}catch(Exception ex){
			logger.fatal("Error in mapRepeatingBlockInstances in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	
	/**
	 * Used in case of repeating to discrete mapping, when user edit already mapped repeating instance it should remove previously mapped answer instances from MappedQuestionsMap
	 * 
	 * @param unmappedToQuestion
	 * @param fromQuestionID
	 * @param fromQueDT
	 * @param portPageForm
	 * @throws Exception
	 */
	public static void removeUnmappedToQuestionFromList(String unmappedToQuestion, String fromQuestionID, MappedFromToQuestionFieldsDT fromQueDT, PortPageForm portPageForm) throws Exception{
		try{
			ArrayList<Object> fromFieldDTList = portPageForm.getMappedQuestionsMap().get(fromQuestionID);
			if(fromQueDT.getQuestionGroupSeqNbr()!=null && fromFieldDTList!=null && unmappedToQuestion!=null && unmappedToQuestion.length()>0){
				Iterator<Object> iter = fromFieldDTList.iterator();
				while(iter.hasNext()){
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) iter.next();
					if(dt!=null && dt.getToQuestionId() !=null && dt.getToQuestionId().equals(unmappedToQuestion)){
						iter.remove();
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Error in removeUnmappedQuestionFromList in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	
	public static ArrayList<Object> getConditionByTemplateFormCd(String fromPageFormCd, HttpServletRequest request) throws Exception{
		try{
			Object[] oParams = new Object[] {fromPageFormCd};
			String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
			String sMethod = "getConditionByTemplateFormCd";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			ArrayList<Object> conditionList = (ArrayList<Object>) obj;
			return conditionList;
		}catch (Exception ex) {
			logger.fatal("Error in getConditionByTemplateFormCd in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
	    }
	}
	
	
	public static void clearPortPageAttributes(PortPageForm portPageForm) throws Exception{
		try{
			portPageForm.setMapName(null);
	    	portPageForm.setToPageFormCd(null);
	    	portPageForm.setFromPageFormCd(null);
	    	portPageForm.setToPageWaTemplateUid(null);
	    	portPageForm.setFromPageWaTemplateUid(null);
	    	portPageForm.setImportFile(null);
	    	portPageForm.setFromPageAnswerList(new ArrayList<Object>());
		}catch (Exception ex) {
			logger.fatal("Error in clearPortPageAttributes in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
	    }
	}
	
	public static void populatePortPageDropdowns(PortPageForm portPageForm, HttpServletRequest request) throws Exception{
		try{
			ArrayList<Object> pageList=new ArrayList<Object>();
			PageManagementActionUtil util = new PageManagementActionUtil();
	    	HttpSession session = request.getSession();
	    	
    		pageList = (ArrayList<Object>)util.getPageSummaries(session);
    		
    		//Get Fake Legacy page list
    		
    		ArrayList<Object> legacyPageList = PortPageUtil.findPageByTemplateType(TEMPLATE_TYPE_LEGACY, request);
    		
    		for(int i=0;i<legacyPageList.size();i++){
				WaTemplateDT waDT = (WaTemplateDT)legacyPageList.get(i);
				waDT.setBusObjType(CachedDropDowns.getCodeDescTxtForCd(waDT.getBusObjType(), "BUS_OBJ_TYPE"));
    		}
    		
    		// Add Legacy page list into Page Builder page list
    		pageList.addAll(legacyPageList);
    		
    		Collections.sort( pageList, new Comparator()
	        {
	        public int compare( Object a, Object b )
	           {
	            return(String.valueOf(((WaTemplateDT)a).getTemplateNm())).compareTo( String.valueOf(((WaTemplateDT) b).getTemplateNm()));
	           }
	        } );

	    	ArrayList<Object> fromPageDD = new ArrayList<Object>();
	    	ArrayList<Object> toPageDD = new ArrayList<Object>();
		
	    	DropDownCodeDT dDT = new DropDownCodeDT();
	    	dDT.setKey(""); dDT.setValue("");
	    	fromPageDD.add(dDT);
	    	toPageDD.add(dDT);
	    	
	    	for(int i=0;i<pageList.size();i++){
				WaTemplateDT waDT = (WaTemplateDT)pageList.get(i);
				if(PortPageUtil.BUS_OBJ_TYPE_INVESTIGATION.equalsIgnoreCase(waDT.getBusObjType()) && !PortPageUtil.TEMPLATE_TYPE_INITIAL_DRAFT.equals(waDT.getTemplateType())){
					 DropDownCodeDT dDownDT = new DropDownCodeDT();
	    			 dDownDT.setKey(waDT.getWaTemplateUid().toString()+"|"+waDT.getFormCd());
	    			 dDownDT.setValue(waDT.getTemplateNm());
	    			 fromPageDD.add(dDownDT);
			     }
				if(PortPageUtil.BUS_OBJ_TYPE_INVESTIGATION.equalsIgnoreCase(waDT.getBusObjType()) && !PortPageUtil.TEMPLATE_TYPE_LEGACY.equals(waDT.getTemplateType())){ // Legacy page should not be displayed in "To Page Name:" drop down.
					 DropDownCodeDT dDownDT = new DropDownCodeDT();
	    			 dDownDT.setKey(waDT.getWaTemplateUid().toString()+"|"+waDT.getFormCd());
	    			 dDownDT.setValue(waDT.getTemplateNm());
	    			 toPageDD.add(dDownDT);
			     }
	    	}
	    	
	    	request.setAttribute("fromPageDD", fromPageDD);
	    	request.setAttribute("toPageDD",toPageDD);
	    	portPageForm.setFromPageListDD(fromPageDD);
	    	portPageForm.setToPageListDD(toPageDD);
		}catch (Exception ex) {
			logger.fatal("Error in populatePortPageDropdowns in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
	    }
	}
	
	
	public static boolean isNumeric(String str)
	{
	    return str.matches("-?\\d+(.\\d+)?");
	}
	
	/**
	 * @param busObjType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private static int getTotalEventRecordCount(String busObjType, String conditionCd, HttpServletRequest request) throws Exception{
		
		logger.debug("busObjType: "+busObjType+", conditionCd: "+conditionCd);
		int count = 0;
		/*String eventType = NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE;
		
		if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(busObjType)){
			eventType = NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE;
		}//Extend it for other events i.e. Treatment, lab, morb
		*/
		try{
			Object[] oParams = new Object[] {busObjType, conditionCd};
			String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
			String sMethod = "getEventCountByEventType";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			count = (Integer) obj;
		}catch(Exception ex){
			logger.fatal("Error in getEventRecordCount in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return count;
	}
	
	/**
	 * @param busObjType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private static int getConvertedRecordCount(Long nbsConversionPageMgmtUid, String conditionCd, HttpServletRequest request) throws Exception{
		Integer convertedRecordCount = 0;
		logger.debug("nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", conditionCd: "+conditionCd);
		try{
			NBSConversionConditionDT nbsConvCondDT = PBtoPBConverterProcessor. getNbsConversionConditionDTByCondition( conditionCd,  nbsConversionPageMgmtUid,  request);
			if(nbsConvCondDT!=null){
				ArrayList<Object> convertedCaseList = PBtoPBConverterProcessor.getConvertedCasesFromNbsConversionMaster(nbsConvCondDT.getConditionCdGroupId(), PortPageUtil.CASE_CONVERSION_STATUS_CODE_PASS, request);
				if(convertedCaseList!=null){
					convertedRecordCount = convertedCaseList.size();
				}
			}
		}catch(Exception ex){
			logger.fatal("Error in getConvertedRecordCount in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return convertedRecordCount;
	}
	
	
	/**
	 * @param busObjType
	 * @param mappingType
	 * @param nbsConversionPageMgmtUid
	 * @param conditionCd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static int getRecordsToConvertCount(String busObjType, Long nbsConversionPageMgmtUid, String conditionCd, HttpServletRequest request) throws Exception{
		Integer remainingRecordCount = null;
		logger.debug("busObjType: "+busObjType+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", conditionCd: "+conditionCd);
		try{
			int totalRecordCount = getTotalEventRecordCount(busObjType, conditionCd, request);
			int convertedRecordCount = getConvertedRecordCount(nbsConversionPageMgmtUid, conditionCd, request);
			
			remainingRecordCount = totalRecordCount - convertedRecordCount;
		}catch(Exception ex){
			logger.fatal("Error in getRecordsToConvertCount in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return remainingRecordCount;
	}
	
	
	/**
	 * @param portPageForm
	 * @param request
	 * @throws Exception
	 */
	public static void setPortConditionPageDetailsForLegacyVaccination(PortPageForm portPageForm, HttpServletRequest request) throws Exception{
		Integer remainingRecordCount = null;
		try{
    		int legacyRecordCountToConvert = PortPageUtil.getRecordsToConvertCount(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE,  portPageForm.getNbsConversionPageMgmtUid(), PortPageUtil.LEGACY_EVENT_DUMMY_CONDITION_CD, request);
    		request.setAttribute("legacyRecordCountToConvert", legacyRecordCountToConvert);
    		//Legacy Events like vaccination, treatment doesn't have any condition associated. Associate dummy condition for conversion process.
    		portPageForm.setSelectedConditionCode(PortPageUtil.LEGACY_EVENT_DUMMY_CONDITION_CD);
    		
    		request.setAttribute("section1Header", "Port Page: Port Legacy Data");
	    	request.setAttribute("section2Header", "Port Legacy Data");
	    	request.setAttribute("subSec4Header", "Validate Mapping & Convert Legacy Records");
	    	request.setAttribute("PageTitle", "Manage Pages: Port Legacy Data");
		}catch(Exception ex){
			logger.fatal("Error in setPortConditionPageDetailsForLegacyVaccination in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	/**
	 * @param portPageForm
	 * @param request
	 * @throws Exception
	 */
	public static void setPortConditionPageDetailsForPageBuilderConversion(PortPageForm portPageForm, HttpServletRequest request) throws Exception{
		try{
			request.setAttribute("section1Header", "Port Page: Port Condition");
			request.setAttribute("section2Header", "Port Condition");
			request.setAttribute("subSec4Header", "Validate Mapping & Convert Condition");
			request.setAttribute("PageTitle", "Manage Pages: Port Condition");
		}catch(Exception ex){
			logger.fatal("Error in setPortConditionPageDetailsForPageBuilderConversion in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	/**
	 * @param waTemplateUid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getBusinessObjectTypeForPage(Long waTemplateUid, HttpServletRequest request) throws Exception{
		String busObjType="";
		try{
			logger.debug("waTemplateUid: "+waTemplateUid);
			
			PageManagementActionUtil util = new PageManagementActionUtil();
			WaTemplateVO waTemplateVO =  util.getPageDetails(waTemplateUid, request.getSession());
			WaTemplateDT templateDT = waTemplateVO.getWaTemplateDT();
			busObjType = templateDT.getBusObjType();
		}catch(Exception ex){
			logger.fatal("Error in getBusinessObjectTypeForPage in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
		return busObjType;
	}
	
	/**
	 * @param nbsConversionPageMgmtUid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static ManagePageDT getNBSConversionPageMgmtByUid(Long nbsConversionPageMgmtUid, HttpServletRequest request) throws Exception{
		try{
			logger.debug("nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid);
			
			Object[] oParams = new Object[] {nbsConversionPageMgmtUid};
			String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
			String sMethod = "getNBSConversionPageMgmtByUid";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			ManagePageDT managePageDT = (ManagePageDT) obj;
			return managePageDT;
		}catch(Exception ex){
			logger.fatal("Exception PortPageUtil.getNBSConversionPageMgmtByUid, nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	
	
	/**
	 * @param toPageFormCd
	 * @param fromPageFormCd
	 * @param conditionCd
	 * @param request
	 * @throws Exception
	 */
	public static void updateToPageFormCdInNbsConversionPageMgmt(String toPageFormCd, String fromPageFormCd, String conditionCd, HttpServletRequest request) throws Exception{
		try{
			 logger.debug("toPageFormCd: "+toPageFormCd+", fromPageFormCd: "+fromPageFormCd+", conditionCd: "+conditionCd);
			 
		     String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
	         String sMethod = "updateToPageFormCdInNbsConversionPageMgmt";
	         Object[] sParams = new Object[] {toPageFormCd, fromPageFormCd, conditionCd};
	         CallProxyEJB.callProxyEJB(sParams,sBeanJndiName,sMethod,request.getSession());
		}catch (Exception ex) {
		     logger.fatal("Error in updateToPageFormCdInNbsConversionPageMgmt in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
	}
	
	
	/**
	 * @param fromPageFormCd
	 * @param conditionCd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static boolean isLegacyEventPortedToNewPage(String fromPageFormCd, String conditionCd, HttpServletRequest request) throws Exception{
		boolean isLegacyEventPorted = false;
		try{
			 logger.debug("fromPageFormCd: "+fromPageFormCd+", conditionCd: "+conditionCd);
			
		     String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
	         String sMethod = "isLegacyEventPortedToNewPage";
	         Object[] oParams = new Object[] {fromPageFormCd, conditionCd};
	         Object obj = CallProxyEJB.callProxyEJB(oParams,sBeanJndiName,sMethod,request.getSession());
	         isLegacyEventPorted=(boolean) obj;
		}catch (Exception ex) {
		     logger.fatal("Error in isLegacyEventPortedToNewPage in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
	    return isLegacyEventPorted;
	}
	
	
	/**
	 * @param busObjType
	 * @param request
	 * @throws Exception
	 */
	public static void restrictPagePublishUntillPortingCompleted(String busObjType, HttpServletRequest request) throws Exception{
		try{
			String fromPageFormCd = PortPageUtil.LEGACY_PAGE_START_WITH+busObjType;
    		boolean isLegacyEventPorted = PortPageUtil.isLegacyEventPortedToNewPage(fromPageFormCd, PortPageUtil.LEGACY_EVENT_DUMMY_CONDITION_CD, request);
    		if(!isLegacyEventPorted){
    			request.setAttribute("isDisablePublish", true);
    			if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(busObjType)){
	    			String messageInd = "PORT";
		    		String portBeforeUsingPageMessage = "Vaccination Page has been successfully added. Please note the legacy vaccination data must be ported before the vaccination page can be used."+
		    		" <a href=\"/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true\"> Click Here</a> to proceed to data porting.";
		    		request.setAttribute("portBeforeUsingPageMessage", portBeforeUsingPageMessage);
		    		request.setAttribute("messageInd", messageInd);
    			}
    		}
		}catch (Exception ex) {
		     logger.fatal("Error in restrictPagePublishUntillPortingCompleted in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
	}
	
	
	/**
	 * @param mappedQuestion
	 * @param questionList
	 * @param request
	 * @throws Exception
	 */
	public static void removeMappedQuestionFromList(String mappedQuestion, ArrayList <Object> questionList, HttpServletRequest request) throws Exception{
		try{
			Iterator<Object> iter = questionList.iterator();
			//Remove already mapped to questions from list
			while(iter.hasNext()){
					MappedFromToQuestionFieldsDT fieldDT = (MappedFromToQuestionFieldsDT) iter.next();
					if(mappedQuestion!=null && mappedQuestion.equals(fieldDT.getFromQuestionId())){
						iter.remove();
						break;
					}
			}
		}catch (Exception ex) {
		     logger.fatal("Error in removeMappedQuestionFromList in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
	}
	
	/**
	 * @param portPageForm
	 * @param fromFieldList
	 * @param toFieldList
	 * @param request
	 * @throws Exception
	 */
	public static void setSearchAttributeForQuestionMapping(PortPageForm portPageForm, ArrayList fromFieldList, ArrayList toFieldList, HttpServletRequest request) throws Exception{
		try{
			portPageForm.getAttributeMap().put("fromFieldList", fromFieldList);
			portPageForm.getAttributeMap().put("status",new Integer(portPageForm.getPortStatus().size()));
			portPageForm.getAttributeMap().put("frmdatatype",new Integer(portPageForm.getFrmDataType().size()));
			portPageForm.getAttributeMap().put("map",new Integer(portPageForm.getMap().size()));
			portPageForm.getAttributeMap().put("todatatype",new Integer(portPageForm.getToDataType().size()));
			portPageForm.getAttributeMap().put("fromQuestionPage","true");
			if(portPageForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			
			portPageForm.getAttributeMap().put("queueCount",String.valueOf(fromFieldList.size()));
			request.setAttribute("totalCount",portPageForm.getFromPageQuestions().size());            //sets the count of total questions 
			request.setAttribute("mapReqCount",portPageForm.getMapNeededCount());                    //sets the count of Mapping Req Questions
			request.setAttribute("queueCount",String.valueOf(fromFieldList.size()));                
			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			request.setAttribute("fromFieldList", fromFieldList);
			request.setAttribute("toFieldList", toFieldList);
			
			portPageForm.initializeDropDowns();
		}catch(Exception ex){
			logger.fatal("Error in setSearchAttributeForQuestionMapping in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
	}
	
	
	/**
	 * Populate various data structures (mappedQuestionsMap, fromFieldList and displayFromQuestionMap) while user clicks on view mapping.
	 * 
	 * @param portPageForm
	 * @param mappedQuestionAnswersList
	 * @param fromFieldList
	 * @param toFieldList
	 * @param repeatingBlockQuestions
	 * @param repeatingBlockQuestionList
	 * @throws Exception
	 */
	public static void populateQuestionListsFromMappedQuestionAnswerList(PortPageForm portPageForm, ArrayList mappedQuestionAnswersList, ArrayList fromFieldList, ArrayList toFieldList, HashMap<String, Integer> repeatingBlockQuestions, ArrayList repeatingBlockQuestionList) throws Exception{
		try{
			logger.info("Populating data structres to render questions on mapping screen while viewing mapping from Page Porting Management screen.");
			
			String prevQuestion=null;
			ArrayList <Object> questionList = new ArrayList<Object>();
			for(int i=0;i<mappedQuestionAnswersList.size();i++){
				MappedFromToQuestionFieldsDT fromFieldDT = (MappedFromToQuestionFieldsDT) mappedQuestionAnswersList.get(i);
				PortPageUtil.nullToBlank(fromFieldDT);
				
				if(!PortPageUtil.NBS_QA_MAPPING_STATUS_CORE.equals(fromFieldDT.getStatusCode()) && fromFieldDT.getFromQuestionId() != null){
					
					PortPageUtil.populateDummyQuestionDetails(portPageForm, fromFieldDT);
					
					logger.info("Processing fromFieldDT.getFromQuestionId() : "+fromFieldDT.getFromQuestionId());
					
					if(fromFieldDT.getFromQuestionId().equals(prevQuestion) || prevQuestion==null){
						questionList.add(fromFieldDT);
						
				
						if(prevQuestion==null){
							fromFieldList.add(fromFieldDT);
							portPageForm.getDisplayFromQuestionsMap().put(fromFieldDT.getFromQuestionId(),fromFieldDT);
					
						}
					}else{
						portPageForm.getMappedQuestionsMap().put(prevQuestion, questionList);
						portPageForm.getDisplayFromQuestionsMap().put(fromFieldDT.getFromQuestionId(), fromFieldDT);
						questionList = new ArrayList<Object>();
						questionList.add(fromFieldDT);
						fromFieldList.add(fromFieldDT);
					
					}
					prevQuestion = fromFieldDT.getFromQuestionId();
					
					//Put repeating block questions with block id number and question id 
					//If repeating block questions then set id and block number accordingly.
					if(fromFieldDT.getBlockIdNbr()!= null && fromFieldDT.getBlockIdNbr() > 0){
						Integer tempBlockId = repeatingBlockQuestions.get(fromFieldDT.getToQuestionId());
						repeatingBlockQuestions.put(fromFieldDT.getToQuestionId(), fromFieldDT.getBlockIdNbr());
						
						if(tempBlockId!= null && (tempBlockId > fromFieldDT.getBlockIdNbr())){
							repeatingBlockQuestions.put(fromFieldDT.getToQuestionId(), tempBlockId);
						}
					}
					
					
					Iterator<Object> iter = toFieldList.iterator();
					//Remove already mapped to questions from list
					//Do not remove any repeating block questions from list : used questionGroupSeqNbr or blockidnumber
					while(iter.hasNext()){
						
						MappedFromToQuestionFieldsDT toFieldDT = (MappedFromToQuestionFieldsDT) iter.next();
						
						if(toFieldDT.getQuestionGroupSeqNbr()!= null && toFieldDT.getQuestionGroupSeqNbr() > 0 && toFieldDT.getFromQuestionId()!= null && repeatingBlockQuestions.containsKey(toFieldDT.getFromQuestionId())){
							Integer blockId = repeatingBlockQuestions.get(toFieldDT.getFromQuestionId());
							toFieldDT.setBlockIdNbr(blockId);
						}
						
						//For repeating to repeating mapping. If From and To mapped questions are repeating then remove it from ToQuestionId dropdown
						if(fromFieldDT.getToQuestionId().equals(toFieldDT.getFromQuestionId()) && fromFieldDT.getQuestionGroupSeqNbr()!=null && toFieldDT.getQuestionGroupSeqNbr()!=null){
							iter.remove();
							break;
						}else if(fromFieldDT.getToQuestionId()!=null && fromFieldDT.getToQuestionId().equals(toFieldDT.getFromQuestionId()) && !(fromFieldDT.getBlockIdNbr()!= null && fromFieldDT.getBlockIdNbr() > 0 )){
							iter.remove();
							break;
						}
						
					}
					
					// Change the to repeating question label.
					if(fromFieldDT.getBlockIdNbr()!= null && fromFieldDT.getBlockIdNbr() > 0){
						String questionLabel = fromFieldDT.getToLabel();
						PortPageUtil.updateToQueLabelForRepeatingTypesMapping(fromFieldDT,fromFieldDT.getBlockIdNbr(),questionLabel);
					}
					
					if(fromFieldDT.getAnswerGroupSeqNbr()!=null){
						repeatingBlockQuestionList.add(fromFieldDT.getFromQuestionId());
						fromFieldDT.setFromLabel(fromFieldDT.getFromLabel()+" ("+fromFieldDT.getAnswerGroupSeqNbr()+")");
					}
				
				
					// Reusing answer_mapped column for codeSetMapping required and answer_mapped values.
					if(PortPageUtil.MAPPED_QA_YN_Y.equals(fromFieldDT.getAnswerMappedCode()) || (!PortPageUtil.NBS_QA_MAPPING_STATUS_AUTO_MAPPED.equals(fromFieldDT.getStatusCode()) && !PortPageUtil.isDirectDataMove(fromFieldDT) && fromFieldDT.getToQuestionId()!=null &&  (!PortPageUtil.isDirectDataMoveForCoded(fromFieldDT))))
						fromFieldDT.setCodeMappingRequired(true);
					else
						fromFieldDT.setCodeMappingRequired(false);
					
					if(PortPageUtil.MAPPED_QA_YN_N.equals(fromFieldDT.getQuestionMappedCode()))
						fromFieldDT.setFieldMappingRequired(false);
					else
						fromFieldDT.setFieldMappingRequired(true);
				}
				
				//Add list element in Map
				if(i==mappedQuestionAnswersList.size()-1){
				
					portPageForm.getMappedQuestionsMap().put(prevQuestion, questionList);
				}
				
			}
		
			
		}catch(Exception ex){
			logger.fatal("Error in populateQuestionListsFromMappedQuestionAnswerList in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
	}
	
	/**
	 * @param portPageForm
	 * @param fromAnswerList
	 * @param toAnswerList
	 * @param forceEJBcall
	 * @param request
	 * @throws Exception
	 */
	public static void popuplateAnswersListForLegacyEvents(PortPageForm portPageForm, ArrayList fromAnswerList, ArrayList toAnswerList, boolean forceEJBcall, HttpServletRequest request) throws Exception{
		try{
			if(forceEJBcall){
				int mappingNeededCount=0;
				ArrayList toQuestionList = new ArrayList();
				
				for ( String key : portPageForm.getMappedQuestionsMap().keySet() ) {
					if(key !=null && !PortPageUtil.UNMAPPED_TO_QUESTIONS.equals(key)){
						ArrayList<Object> fieldDTList = portPageForm.getMappedQuestionsMap().get(key);
						if(fieldDTList!=null && fieldDTList.size()>0){
							for(int j=0;j<fieldDTList.size();j++){
								MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) fieldDTList.get(j);
								AnswerMappingDT answerDT = new AnswerMappingDT();
								
								
								if(DATA_TYPE_CODED.equals(dt.getFromDataType()) && (MAPPED_QA_YN_Y.equals(dt.getAnswerMappedCode()) || MAPPED_QA_YN_N.equals(dt.getAnswerMappedCode()))){
									
									
									answerDT.setQuestionIdentifier(dt.getFromQuestionId());
									answerDT.setQuestionLabel(dt.getFromLabel());
									answerDT.setCodeSetNm(dt.getFromCodeSetNm());
									answerDT.setCode(dt.getFromCode());
									answerDT.setCodeDescTxt(dt.getFromCodeDesc());
									answerDT.setAutoMapped("Y");
									
									if((dt.getToCode()!=null && dt.getToCode().length()>0) || MAPPED_QA_YN_N.equals(dt.getAnswerMappedCode())){
										if(dt.getToCode()!=null){
											answerDT.setToCode(dt.getToCode());
											answerDT.setToCodeDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getToCode(), dt.getToCodeSetNm()));
										}
										
										answerDT.setMapped(dt.getAnswerMappedCode());
										answerDT.setMappedDesc(CachedDropDowns.getCodeDescTxtForCd(dt.getAnswerMappedCode(), PortPageUtil.CODE_SET_YN));
										answerDT.setStatus(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCode(), PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
									}else if(dt.getToCode()==null || dt.getToCode().length()==0){
			    						answerDT.setStatus(PortPageUtil.STATUS_MAPPING_NEEDED);
			    						answerDT.setMapped("");
			    						mappingNeededCount++;
									}
									
									fromAnswerList.add(answerDT);
								}
								
								toQuestionList.add(dt.getToQuestionId());
							}
						}
					}
				}
				
				Object[] oParams1 = new Object[] {toQuestionList, toQuestionList, portPageForm.getToPageWaTemplateUid()};
				String sBeanJndiName1 = JNDINames.PORT_PAGE_PROXY_EJB;
				String sMethod1 = "getAnswersToMap";
				Object answerListObj1 = CallProxyEJB.callProxyEJB(oParams1, sBeanJndiName1, sMethod1, request.getSession());
				toAnswerList = (ArrayList) answerListObj1;
				
				
				portPageForm.setAnsMapNeededCount(mappingNeededCount);
				portPageForm.setToPageAnswerList(toAnswerList);
			}else{
				//if(portPageForm.getAttributeMap().get("fromAnswerList")!=null)
				//	fromAnswerList.addAll((ArrayList<Object>)portPageForm.getAttributeMap().get("fromAnswerList"));
				
				if(fromAnswerList == null || fromAnswerList.size()==0)
					fromAnswerList.addAll(portPageForm.getFromPageAnswerList());

				portPageForm.getAttributeMap().put("PageNumber","1");
				if(toAnswerList==null || toAnswerList.size()==0)
					toAnswerList.addAll(portPageForm.getToPageAnswerList());
			}
			
			portPageForm.setFromPageAnswerList(fromAnswerList);
			
			
			//portPageForm.getAttributeMap().put("fromAnswerList", fromAnswerList);
			portPageForm.getAttributeMap().put("answerStatus",new Integer(portPageForm.getAnswerStatus().size()));
    		portPageForm.getAttributeMap().put("answerMap",new Integer(portPageForm.getAnswerMap().size()));
		}catch(Exception ex){
			logger.fatal("Error in popuplateAnswersListForLegacyEvents in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
	}
	
	/**
	 * @param questionDT
	 * @param mappingType
	 * @throws Exception
	 */
	public static void updateQuestionEditFlag(MappedFromToQuestionFieldsDT questionDT, String mappingType) throws Exception{
		try{
			if(PortPageUtil.MAPPING_LEGACY.equals(mappingType)){
				if(questionDT.getToDbLocation()!=null && !questionDT.getToDbLocation().contains("answer_txt")){
					questionDT.setQuestionEditFlag(false);
				}
			}
		}catch(Exception ex){
			logger.fatal("Error in updateQuestionEditFlag in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.toString());
		}
	}
	
	/**
	 * @param busObjType
	 * @param request
	 * @throws ServletException
	 */
	public static void blockPageAccessUntilConversionIsComplete(String busObjType, HttpServletRequest request) throws ServletException{
		try{
			if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(busObjType)){
				String fromPageFormCd = PortPageUtil.LEGACY_PAGE_START_WITH+busObjType;
				boolean isLegacyEventPorted = PortPageUtil.isLegacyEventPortedToNewPage(fromPageFormCd, PortPageUtil.LEGACY_EVENT_DUMMY_CONDITION_CD, request);
				if(!isLegacyEventPorted){
					throw new Exception("<br/><br/><B>Before using "+ NEDSSConstants.VACCINATION_BUSINESS_OBJECT_DESC+" module, port all the records for business object "+busObjType+"</b><br/><br/>");
				}
			}
		}catch(Exception ex){
			throw new ServletException(ex.getMessage());
		}
	}
	
	/**
	 * @param snMappedQueList
	 * @param snQueAnswerList
	 * @throws NEDSSAppException
	 */
	public static void addAnswerForSNQuestions(ArrayList<Object> snMappedQueList, ArrayList<Object> snQueAnswerList) throws NEDSSAppException{
		try{
			logger.info("Adding Answers for Structured Numeric questions.");
			
			for(int i=0;i<snMappedQueList.size();i++){
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) snMappedQueList.get(i);
				if(dt!=null){
					
					ArrayList<Object> codeList = new CachedDropDownValues().getCodedValuesList(dt.getToCodeSetNm());
					for(int j=0;j<codeList.size();j++){
						AnswerMappingDT toAnsDT = new AnswerMappingDT();
						DropDownCodeDT dropDownCodeDT = (DropDownCodeDT) codeList.get(j);
						
						if(dropDownCodeDT.getKey()!=null && dropDownCodeDT.getKey().length()>0){
							toAnsDT.setQuestionIdentifier(dt.getToQuestionId());
							toAnsDT.setQuestionLabel(dt.getToLabel());
							toAnsDT.setCode(dropDownCodeDT.getKey());
							toAnsDT.setCodeDescTxt(dropDownCodeDT.getValue());
							toAnsDT.setCodeSetNm(dt.getToCodeSetNm());
							toAnsDT.setCodeSetGroupId(dt.getToCodeSetGroupId());
							
							if(NBS_QA_MAPPING_STATUS_AUTO_MAPPED.equals(dt.getStatusCode())){
								toAnsDT.setAutoMapped("Y");
							}else{
								toAnsDT.setAutoMapped("N");
							}
							snQueAnswerList.add(toAnsDT);
						}
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception : "+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage());
		}
	}
	
	
	/**
	 * Used to populated dummy questions for 'Structured Numeric' and 'Coded with Other' mappings
	 * 
	 * @param portPageForm
	 * @param questionDT
	 * @throws NEDSSAppException
	 */
	public static void populateDummyQuestionDetails(PortPageForm portPageForm, MappedFromToQuestionFieldsDT questionDT) throws NEDSSAppException{
		try{
			// Set the question label, data_location and data_type for discrete to structure numeric dummy question (question which ends with _SYS_UNIT)
			//It use for two Discrete questions on from page to one structured numeric question which has CODED units.
			if(questionDT.getToQuestionId()!=null && questionDT.getToQuestionId().contains(STRUCTURE_NUMERIC_DUMMY_QUE_SUFFIX)){
				MappedFromToQuestionFieldsDT dt = portPageForm.getDisplayToQuestionsMap().get(questionDT.getToQuestionId());
				if(dt!=null){
					questionDT.setToLabel(dt.getFromLabel());
					questionDT.setToDataType(dt.getFromDataType());
					questionDT.setToDbLocation(dt.getFromDbLocation());
					
					//Structure numeric mapping - as the dummy unit question does not exist in WA_UI_Metadata set codeSetName for dummy question
					if(questionDT.getToCodeSetGroupId()!=null){
						CachedDropDownValues cdv = new CachedDropDownValues();
						questionDT.setToCodeSetNm(cdv.getTheCodeSetNm(questionDT.getToCodeSetGroupId()));
					}
				}
			}else if(questionDT.getToQuestionId()!=null && questionDT.getToQuestionId().contains(CODED_WITH_OTHER_DUMMY_QUE_SUFFIX)){	//Coded with Other scenario
				MappedFromToQuestionFieldsDT dt = portPageForm.getDisplayToQuestionsMap().get(questionDT.getToQuestionId());
				if(dt!=null){
					questionDT.setToLabel(dt.getFromLabel());
					questionDT.setToDataType(dt.getFromDataType());
					questionDT.setToDbLocation(dt.getFromDbLocation());
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception : "+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage());
		}
	}
	
	
	/**
	 * @param portPageForm
	 * @param fromQuestionId
	 * @param toQuestionDropDownList
	 * @throws NEDSSAppException
	 */
	public static void updateToQuestionIdDropdownForRepeatingMapping(PortPageForm portPageForm, String fromQuestionId, ArrayList<Object> toQuestionDropDownList) throws NEDSSAppException{
		try{
			ArrayList<Object> mappedQueDTList = portPageForm.getMappedQuestionsMap().get(fromQuestionId);
			//If from page question is mapped
			if(mappedQueDTList!=null && mappedQueDTList.size()>0){
				MappedFromToQuestionFieldsDT mappedQueDT = (MappedFromToQuestionFieldsDT) mappedQueDTList.get(0);
				
				//If first instance of repeating to discrete is unmapped and remaining instances are mapped then set mappedQueDT with the first mapped instance.
				if(!mappedQueDT.isFieldMappingRequired()){
					for(int j=1;j<mappedQueDTList.size();j++){
						mappedQueDT = (MappedFromToQuestionFieldsDT) mappedQueDTList.get(j);
						
						if(mappedQueDT.isFieldMappingRequired()){
							break;
						}
					}
				}
				//If From page's repeating block question mapped to To page's discrete question, filter out repeating block questions from toQuestionDropDownList.
				
				if(mappedQueDT.getQuestionGroupSeqNbr()!=null && mappedQueDT.getToQuestionGroupSeqNbr()==null){
					//Iterator<Object> iter =portPageForm.getToPageQuestions().iterator();// toQuestionDropDownList.iterator();
					Iterator<Object> iter = toQuestionDropDownList.iterator();
					while(iter.hasNext()){
						DropDownCodeDT ddDT = (DropDownCodeDT) iter.next();
						
						if(ddDT.getIntValue()!=null && MAPPED_QA_YN_Y.equals(mappedQueDT.getQuestionMappedCode())){// !mappedQueDT.getStatusCode().contains(NBS_QA_MAPPING_STATUS_MAPPING_NEEDED)){
							iter.remove();
						}
					}
				}
				
				// If From Page's discrete question is mapped to To page's repeating block question, filter To page's that repeating question to map with other From page's repeating question.
				
                PortPageUtil.populateToQuestionMappingType(portPageForm);
                
				if(mappedQueDT.getQuestionGroupSeqNbr()!=null){
					Iterator<Object> iter = toQuestionDropDownList.iterator();
					while(iter.hasNext()){
						DropDownCodeDT ddDT = (DropDownCodeDT) iter.next();
						
						if(MAPPING_TYPE_DISCRETE_TO_REPEATING.equals(portPageForm.getToQuestionMappingTypesMap().get(ddDT.getKey()))){
							iter.remove();
						}
					}
				}
				
			}else{
				//If from page question is not mapped.
				MappedFromToQuestionFieldsDT questionDT = portPageForm.getDisplayFromQuestionsMap().get(fromQuestionId);
				if(questionDT!=null && questionDT.getQuestionGroupSeqNbr()!=null && questionDT.getQuestionGroupSeqNbr()>0){
					PortPageUtil.populateToQuestionMappingType(portPageForm);
					Iterator<Object> iter = toQuestionDropDownList.iterator();
					while(iter.hasNext()){
						DropDownCodeDT ddDT = (DropDownCodeDT) iter.next();
						
						if(MAPPING_TYPE_DISCRETE_TO_REPEATING.equals(portPageForm.getToQuestionMappingTypesMap().get(ddDT.getKey()))){
							iter.remove();
						}
					}
				}
				
			}
		}catch(Exception ex){
			logger.fatal("Exception : "+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage());
		}
	}
	
	/**
	 * This method populates toQuestionMappingTypeMap which is used to check 
	 * Once the ‘TO” page repeating block question is mapped to ‘FROM’ page discrete questions, then that ‘TO” page repeating block question 
	 * won’t be available for mapping with other repeating block questions.
	 * 
	 * @param portPageForm
	 * @throws NEDSSAppException
	 */
	public static void populateToQuestionMappingType(PortPageForm portPageForm) throws NEDSSAppException{
		try{
			portPageForm.getToQuestionMappingTypesMap().clear();
			
			for ( String key : portPageForm.getMappedQuestionsMap().keySet() ) {
				ArrayList<Object> fieldDTList = portPageForm.getMappedQuestionsMap().get(key);
				if(fieldDTList!=null && fieldDTList.size()>0){
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) fieldDTList.get(0);
					if(dt.getToQuestionId()!=null && dt.getToQuestionId().length()>0){
						
						if(dt.getQuestionGroupSeqNbr()==null && dt.getToQuestionGroupSeqNbr()!=null){
							//DiscreteToRepeating Mapping
							portPageForm.getToQuestionMappingTypesMap().put(dt.getToQuestionId(), MAPPING_TYPE_DISCRETE_TO_REPEATING);
						}else if(dt.getQuestionGroupSeqNbr()!=null && dt.getToQuestionGroupSeqNbr()!=null){
							//Repeating to Repeating Mapping
							portPageForm.getToQuestionMappingTypesMap().put(dt.getToQuestionId(), MAPPING_TYPE_REPEATING_TO_REPEATING);
						}else if(dt.getQuestionGroupSeqNbr()==null && dt.getToQuestionGroupSeqNbr()==null){
							//Discrete to Discrete Mapping
							portPageForm.getToQuestionMappingTypesMap().put(dt.getToQuestionId(), MAPPING_TYPE_DISCRETE_TO_DISCRETE);
						}
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception : "+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage());
		}
	}
	
	/**
	 * Change toQuestionId label for DiscreteToRepeating (blockIdNbr) and RepeatingToRepeating Mapping (R)
	 * @param questionDT
	 * @param blockIdNbr
	 * @param toLabel
	 * @throws NEDSSAppException
	 */
	public static void updateToQueLabelForRepeatingTypesMapping(MappedFromToQuestionFieldsDT questionDT, Integer blockIdNbr, String toLabel) throws NEDSSAppException{
		try{
			logger.info("Updating lables for repeating type mappings");
			//If from and to question's questionGroupSeqNbr is not null and greater then 0 then both are repeating block questions.
			if(questionDT.getQuestionGroupSeqNbr()!=null && questionDT.getQuestionGroupSeqNbr()>0 && questionDT.getToQuestionGroupSeqNbr()!=null && questionDT.getToQuestionGroupSeqNbr()>0)
				questionDT.setToLabel(toLabel+"(R)");
		    else
		    	questionDT.setToLabel(toLabel+"("+blockIdNbr+")");
		}catch(Exception ex){
			logger.fatal("Exception : "+ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage());
		}
	}
	
	
	/**
	 * @param templateType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Object> findPageByTemplateType(String templateType, HttpServletRequest request) throws Exception{
		try{
			logger.debug("templateType: " +templateType);
			
			Object[] oParams = new Object[] {templateType};
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "findPageByTemplateType";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			ArrayList<Object> pageList = (ArrayList<Object>) obj;
			
			return pageList;
		}catch (Exception ex) {
			logger.fatal("Error in findPageByTemplateType in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	    }
	}
	
	/**
	 * Setting converstionType based on mappings
	 * @param questionDT
	 * @throws Exception
	 */
	public static void setConversionType(MappedFromToQuestionFieldsDT questionDT) throws Exception{
		try{
			if(questionDT == null)
				throw new Exception("MappedFromToQuestionFieldsDT is null");
			
			questionDT.setConversionType(getConversionType(questionDT));
			
			/*if(questionDT.getFromNbsUiComponentUid()!=null && questionDT.getFromNbsUiComponentUid().longValue() == 1013 && questionDT.getQuestionGroupSeqNbr() == null
					&& questionDT.getToNbsUiComponentUid()!=null && questionDT.getToNbsUiComponentUid().longValue() == 1007 && questionDT.getToQuestionGroupSeqNbr() != null){
				
				questionDT.setConversionType(MULTI_TO_REP_SINGLE_MAPPING);
			}else if(questionDT.getBlockIdNbr()!=null && questionDT.getAnswerGroupSeqNbr()!=null){
				//RepeatingToRepeating
			}else if(questionDT.getBlockIdNbr() == null && questionDT.getAnswerGroupSeqNbr() != null){
				//RepeatingToDiscrete
			}else if(questionDT.getBlockIdNbr() != null && questionDT.getAnswerGroupSeqNbr() == null){
				//DiscreteToRepeating
			}else if(questionDT.getBlockIdNbr() == null && questionDT.getAnswerGroupSeqNbr() == null){
				//DiscreteToDiscrete
			}else{
				logger.debug("Unrecongnize Mapping ");
			}*/
		}catch (Exception ex) {
			logger.fatal("Error in setConversionType in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	    }
	}
	
	public static String getConversionType(MappedFromToQuestionFieldsDT questionDT) throws Exception{
		try{
			if(questionDT == null)
				throw new Exception("MappedFromToQuestionFieldsDT is null");
			
			if(questionDT.getFromNbsUiComponentUid()!=null && questionDT.getFromNbsUiComponentUid().longValue() == 1013 && questionDT.getQuestionGroupSeqNbr() == null
					&& questionDT.getToNbsUiComponentUid()!=null && questionDT.getToNbsUiComponentUid().longValue() == 1007 && questionDT.getToQuestionGroupSeqNbr() != null){
				
				return MULTI_TO_REP_SINGLE_MAPPING;
			}else if(questionDT.getBlockIdNbr()!=null && questionDT.getAnswerGroupSeqNbr()!=null){
				//RepeatingToRepeating
			}else if(questionDT.getBlockIdNbr() == null && questionDT.getAnswerGroupSeqNbr() != null){
				//RepeatingToDiscrete
			}else if(questionDT.getBlockIdNbr() != null && questionDT.getAnswerGroupSeqNbr() == null){
				//DiscreteToRepeating
			}else if(questionDT.getBlockIdNbr() == null && questionDT.getAnswerGroupSeqNbr() == null){
				//DiscreteToDiscrete
			}else{
				logger.debug("Unrecongnize Mapping ");
			}
		}catch (Exception ex) {
			logger.fatal("Error in setConversionType in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	    }
		//Special mapping it return conversionType null so that whatever comes in mapp
		return null;
	}
	
	public static String getConversionType(MappedFromToQuestionFieldsDT fromQueDT, MappedFromToQuestionFieldsDT toQueDT) throws Exception{
		try{
			if(fromQueDT == null || toQueDT == null)
				return null;
			
			if(fromQueDT.getFromNbsUiComponentUid()!=null && fromQueDT.getFromNbsUiComponentUid().longValue() == 1013 && fromQueDT.getQuestionGroupSeqNbr() == null
					&& toQueDT.getFromNbsUiComponentUid()!=null && toQueDT.getToNbsUiComponentUid().longValue() == 1007 && toQueDT.getQuestionGroupSeqNbr() != null){
				
				return MULTI_TO_REP_SINGLE_MAPPING;
			}else if(fromQueDT.getQuestionGroupSeqNbr()!=null && toQueDT.getQuestionGroupSeqNbr()!=null){
				//RepeatingToRepeating
			}else if(fromQueDT.getQuestionGroupSeqNbr()!=null && toQueDT.getQuestionGroupSeqNbr()==null){
				//RepeatingToDiscrete
			}else if(fromQueDT.getQuestionGroupSeqNbr()==null && toQueDT.getQuestionGroupSeqNbr()!=null){
				//DiscreteToRepeating
			}else if(fromQueDT.getQuestionGroupSeqNbr()==null && toQueDT.getQuestionGroupSeqNbr()==null){
				//DiscreteToDiscrete
			}else{
				logger.debug("Unrecongnize Mapping ");
			}
			
		}catch (Exception ex) {
			logger.fatal("Error in setConversionType in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
	    }
		//Special mapping it return conversionType null so that whatever comes in mapp
		return null;
	}
	
	/**
	 * @param pamProxyVO
	 * @return
	 * @throws Exception
	 */
	public static PageActProxyVO createPageActProxyVOfromPamProxyVo(PamProxyVO pamProxyVO) throws Exception{
		PageActProxyVO pageActProxyVO = new PageActProxyVO();
		
		logger.debug("Create PageActProxyVO from PamProxyVO");
		try{
			if(pamProxyVO == null)
				return null;
						
			pageActProxyVO.setPublicHealthCaseVO(pamProxyVO.getPublicHealthCaseVO());
			pageActProxyVO.setThePersonVOCollection(pamProxyVO.getThePersonVOCollection());
			pageActProxyVO.setPageVO(pamProxyVO.getPamVO());
			pageActProxyVO.setTheVaccinationSummaryVOCollection(pamProxyVO.getTheVaccinationSummaryVOCollection());
			pageActProxyVO.setTheTreatmentSummaryVOCollection(pamProxyVO.getTheTreatmentSummaryVOCollection());
			pageActProxyVO.setTheLabReportSummaryVOCollection(pamProxyVO.getTheLabReportSummaryVOCollection());
			pageActProxyVO.setTheMorbReportSummaryVOCollection(pamProxyVO.getTheMorbReportSummaryVOCollection());
			pageActProxyVO.setTheParticipationDTCollection(pamProxyVO.getTheParticipationDTCollection());
			pageActProxyVO.setTheInvestigationAuditLogSummaryVOCollection(pamProxyVO.getTheInvestigationAuditLogSummaryVOCollection());
			pageActProxyVO.setTheOrganizationVOCollection(pamProxyVO.getTheOrganizationVOCollection());
			pageActProxyVO.setTheNotificationVOCollection(pamProxyVO.getTheNotificationVOCollection());
			pageActProxyVO.setAssociatedNotificationsInd(pamProxyVO.getAssociatedNotificationsInd());
			pageActProxyVO.setTheNotificationSummaryVOCollection(pamProxyVO.getTheNotificationSummaryVOCollection());

			// private NotificationVO  theNotificationVO; - no setter found in pageActProxyVO
			pageActProxyVO.setNotificationVO_s(pamProxyVO.getNotificationVO_s());
			
			pageActProxyVO.setTheDocumentSummaryVOCollection(pamProxyVO.getTheDocumentSummaryVOCollection());
			pageActProxyVO.setOOSystemInd(pamProxyVO.isOOSystemInd());
			pageActProxyVO.setOOSystemPendInd(pamProxyVO.isOOSystemPendInd());
			pageActProxyVO.setTheCTContactSummaryDTCollection(pamProxyVO.getTheCTContactSummaryDTCollection());
			pageActProxyVO.setNbsAttachmentDTColl(pamProxyVO.getNbsAttachmentDTColl());
			pageActProxyVO.setNbsNoteDTColl(pamProxyVO.getNbsNoteDTColl());
			pageActProxyVO.setUnsavedNote(pamProxyVO.isUnsavedNote());
			pageActProxyVO.setExportReceivingFacilityDT(pamProxyVO.getExportReceivingFacilityDT());
			
			return pageActProxyVO;
		}catch(Exception ex){
			logger.fatal("Error in createPageActProxyVOfromPamProxyVo in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	/**
	 * @param formCd
	 * @param conditionCd
	 * @return
	 * @throws Exception
	 */
	public static String getMappingType(String formCd, String conditionCd) throws Exception{
		try{
			logger.debug("formCd: "+formCd+", conditionCd: "+conditionCd);
			
			String mappingType = PortPageUtil.MAPPING_PAGEBUILDER;
			if(VARICELLA_INV_FORM_CD.equals(formCd) && VARICELLA_CONDIION_CD.equals(conditionCd)){
				mappingType = PortPageUtil.MAPPING_HYBRID;
			}
			return mappingType;
		}catch(Exception ex){
			logger.fatal("Error in getMappingType in PortPageUtil: "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * @param fromPageInvFormCd
	 * @param fromPageTemplateUid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static boolean insertLDFFromNbsUiMetadataToWaUiMetadata(String fromPageInvFormCd, Long fromPageTemplateUid, HttpServletRequest request) throws Exception{
		boolean isSuccessfulInsert = false;
		try{
			 logger.debug("fromPageInvFormCd: "+fromPageInvFormCd+", fromPageTemplateUid: "+fromPageTemplateUid);
			 if(VARICELLA_INV_FORM_CD.equals(fromPageInvFormCd)){
			     String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
		         String sMethod = "insertLDFFromNbsUiMetadataToWaUiMetadata";
		         Object[] oParams = new Object[] {fromPageInvFormCd, fromPageTemplateUid};
		         Object obj = CallProxyEJB.callProxyEJB(oParams,sBeanJndiName,sMethod,request.getSession());
		         isSuccessfulInsert=(boolean) obj;
			 }
		}catch (Exception ex) {
		     logger.fatal("Error in insertLDFFromNbsUiMetadataToWaUiMetadata in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.getMessage());
		}
	    return isSuccessfulInsert;
	}
	
	
	/**
	 * @param fromPageInvFormCd
	 * @param fromPageTemplateUid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Object> getNewlyAddedLDFsForHybridPage(String fromPageInvFormCd, Long fromPageTemplateUid, HttpServletRequest request) throws Exception{
		boolean isSuccessfulInsert = false;
		ArrayList<Object> newlyAddedLDFList = new ArrayList<Object>();
		
		try{
			 logger.debug("fromPageInvFormCd: "+fromPageInvFormCd+", fromPageTemplateUid: "+fromPageTemplateUid);
			 if(VARICELLA_INV_FORM_CD.equals(fromPageInvFormCd)){
			     String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
		         String sMethod = "getNewlyAddedLDFsForHybridPage";
		         Object[] oParams = new Object[] {fromPageInvFormCd, fromPageTemplateUid};
		         Object obj = CallProxyEJB.callProxyEJB(oParams,sBeanJndiName,sMethod,request.getSession());
		         newlyAddedLDFList = (ArrayList<Object>) obj;
			 }
		}catch (Exception ex) {
		     logger.fatal("Error in getNewlyAddedLDFsForHybridPage in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.getMessage());
		}
	    return newlyAddedLDFList;
	}
	
	
	/**
	 * @param wa_tempalate_uid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private static Map<String, MappedFromToQuestionFieldsDT> getCoreQuestionsByPage(Long wa_tempalate_uid, HttpServletRequest request) throws Exception{
		try{
			Object[] oParamsFromPageCoreQuestion = new Object[] {wa_tempalate_uid};
			String sBeanJndiNameCoreQuestion = JNDINames.PORT_PAGE_PROXY_EJB;
			String sMethodCoreQuestion = "findCoreQuestionsByPage";
			Object coreQuestionsObj = CallProxyEJB.callProxyEJB(oParamsFromPageCoreQuestion, sBeanJndiNameCoreQuestion, sMethodCoreQuestion, request.getSession());
			ArrayList<Object> coreQuestionsList =  (ArrayList<Object>) coreQuestionsObj;
			
			Map<String, MappedFromToQuestionFieldsDT> coreQueMap = new HashMap<>();
			for(int i=0;i<coreQuestionsList.size();i++){
				MappedFromToQuestionFieldsDT questionDT = (MappedFromToQuestionFieldsDT) coreQuestionsList.get(i);
				if(questionDT!=null){
					coreQueMap.put(questionDT.getFromQuestionId(), questionDT);
				}
			}
			return coreQueMap;
		}catch (Exception ex) {
		     logger.fatal("Error in getCoreQuestionsByPage in  PortPageUtil: "+ex.getMessage(), ex);
		     throw new Exception(ex.getMessage());
		}
	}
	
}