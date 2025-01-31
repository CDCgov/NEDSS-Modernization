package gov.cdc.nedss.webapp.nbs.action.srtadmin.util;

import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PhinVadsSystemVO;
import gov.cdc.nedss.pagemanagement.wa.dt.ConditionDT;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.srtadmin.dt.LabCodingSystemDT;
import gov.cdc.nedss.srtadmin.dt.LabResultDT;
import gov.cdc.nedss.srtadmin.dt.LabTestDT;
import gov.cdc.nedss.srtadmin.dt.LoincDT;
import gov.cdc.nedss.srtadmin.dt.SnomedDT;
import gov.cdc.nedss.srtadmin.dt.CodeSetDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao.TriggerCodeDAOImpl;
import gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT;
import gov.cdc.nedss.systemservice.util.CodeValueGeneralCachedDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.triggerCodes.TriggerCodeForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SRTAdminUtil {

	static final LogUtils logger = new LogUtils(SRTAdminUtil.class.getName());
	QueueUtil queueUtil = new QueueUtil();
	public static Object processRequest(Object[] oParams, HttpSession session) throws Exception {

		MainSessionCommand msCommand = null;
		Object obj = null;

		try {
			String sBeanJndiName = JNDINames.SRT_ADMIN_EJB;
			String sMethod = "processSRTAdminRequest";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			obj = arr.get(0);

			} catch (Exception ex) {
				logger.error("Error in SRTAdminUtil.processReq ", ex);	
				ex.printStackTrace();
				throw new Exception(ex);
			}
			return obj;	
		}	
		
	public static void handleErrors(Exception e, HttpServletRequest request, String actionType,String fileds) {
		
		//handle special scenario for codevalue general pagination after submit
		if(cvgPagination(request)) return;		
		if(e.toString().indexOf("SQLException") != -1 || e.toString().indexOf("SQLServerException") != -1
	|| e.toString().indexOf("java.sql.SQL")!=-1) {//The third condition has been added for Oracle
			if(actionType != null && actionType.equalsIgnoreCase("edit")) {
				request.setAttribute("error", "Database error while updating. Please check the values and try again.");
			} else if(actionType != null && actionType.equalsIgnoreCase("create")){
				if(e.toString().indexOf("PK")!= -1){
				   request.setAttribute("error", "A record already exists with this "+fileds+". Please enter a unique "+fileds+" to create a new record.");
				}
				else{
					request.setAttribute("error", "Database error while creating. Please check the values and try again.");
				}
			} else if(actionType != null && actionType.equalsIgnoreCase("search")) {
				
				request.setAttribute("error", "Database error while searching. Please check the values and try again.");				
			} else {
			request.setAttribute("error", e.getMessage());
		}
	  }
		if(actionType != null && actionType.equalsIgnoreCase("import")){
			if(e.toString().indexOf("MalformedURLException") != -1) 
			{
				request.setAttribute("error", "Service error occured while importing. Please try again.");
			}else
			{
				request.setAttribute("error", e.getMessage());
			}
		}
	}
	
	public static void makeLabLink(LabCodingSystemDT dt, String type) throws Exception{

		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		String laboratoryTmp = URLEncoder.encode(dt.getLaboratoryId(), "ISO-8859-1");
		parameterMap.put("laboratoryId", laboratoryTmp);
		if(type.equals("VIEW")) {			
			parameterMap.put("method", "viewLab");
			dt.setViewLink(buildHyperLink("Laboratories.do", parameterMap, "laboratory", "View"));
		} else {
			parameterMap.put("method", "editLab");
			dt.setEditLink(buildHyperLink("Laboratories.do", parameterMap, "laboratory", "Edit"));
		}		
	}	
	public static void makeLabTestLink(LabTestDT dt, String type) throws Exception {

		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		String labTestCdTmp = URLEncoder.encode(dt.getLabTestCd(), "ISO-8859-1");
		String laboratoryTmp = URLEncoder.encode(dt.getLaboratoryId(), "ISO-8859-1");
		parameterMap.put("labTestCd", labTestCdTmp);
		parameterMap.put("laboratoryId", laboratoryTmp);
		if(type.equals("VIEW")) {			
			parameterMap.put("method", "viewLab");
			dt.setViewLink(buildHyperLink("LDLabTests.do", parameterMap, "labtests", "View"));
		} else {
			parameterMap.put("method", "editLab");
			dt.setEditLink(buildHyperLink("LDLabTests.do", parameterMap, "labtests", "Edit"));
		}

	}
	public static void makeCodeValGenLink(CodeValueGeneralDT dt, String type) throws Exception{

		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		String codeTmp = URLEncoder.encode(dt.getCode(), "ISO-8859-1");
		parameterMap.put("code", codeTmp);

		if (type.equals("VIEW"))
			parameterMap.put("method", "viewCodeValGenCode");
		else
			parameterMap.put("method", "editCodeValGenCode");
		if (type.equals("VIEW")) {
			dt.setViewLink(buildHyperLink("CodeValueGeneral.do", parameterMap,
					"codeval", "View"));
		} else if(dt.getIsModifiableInd()== null ||dt.getIsModifiableInd().equals("Y")){
			dt.setEditLink(buildHyperLink("CodeValueGeneral.do", parameterMap,
					"codeval", "Edit"));
		}else if(dt.getIsModifiableInd().equals("N"))
		{
			String sb  = "<span style=\"color:#DDD; text-decoration:underline;\">" + "Edit" + "</span>";
			dt.setEditLink(sb);
		}
	}
	
	public static void makeResultLink(LabResultDT dt, String type) throws Exception{

		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		String labResultCdTmp = URLEncoder.encode(dt.getLabResultCd(),"ISO-8859-1");
		String laboratoryTmp =  URLEncoder.encode(dt.getLaboratoryId(), "ISO-8859-1");
		parameterMap.put("labResultCd", labResultCdTmp);
		parameterMap.put("laboratoryId", laboratoryTmp);
		if (type.equals("VIEW")) {
			parameterMap.put("method", "viewLab");
			dt.setViewLink(buildHyperLink(
					"ExistingLocallyDefinedLabResults.do", parameterMap,
					"labresults", "View"));
		} else {
			parameterMap.put("method", "editLab");
			dt.setEditLink(buildHyperLink(
					"ExistingLocallyDefinedLabResults.do", parameterMap,
					"labresults", "Edit"));
		}
	}
	
	public static void makeLoincSelectLink(LoincDT dt) {
		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		parameterMap.put("loinc", dt.getLoincCd());
		String url = "<a href=\"javascript:populateLoinc('" + handleSpCharForJS(dt.getLoincCd()) + "');\">" + dt.getLoincCd() + "</a>";
		dt.setSelectLink(url);		
	}
	public static void makeSnomedSelectLink(SnomedDT dt) {
		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		parameterMap.put("snomed", dt.getSnomedCd());
		String url = "<a href=\"javascript:populateSnomed('" + handleSpCharForJS(dt.getSnomedCd()) + "');\">"+dt.getSnomedCd()+"</a>";
		dt.setSelectLink(url);		
	}		
	
	public static void makeLabTestSelectLink(LabTestDT dt) {
		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		parameterMap.put("labTest", dt.getLabTestCd());
		String url = "<a href=\"javascript:populateLabTest('" + handleSpCharForJS(dt.getLabTestCd()) + "','" + handleSpCharForJS(dt.getLaboratoryId()) + "');\">" + dt.getLabTestCd() + "</a>";
		dt.setViewLink(url);
	}	

	public static void makeLabResultSelectLink(LabResultDT dt) {
		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		parameterMap.put("labResultCd", dt.getLabResultCd());
		parameterMap.put("srchLabId", dt.getLaboratoryId());
		String url = "<a href=\"javascript:populateLabResult('" + handleSpCharForJS(dt.getLabResultCd()) + "');javascript:populateLabId('" + handleSpCharForJS(dt.getLaboratoryId()) + "');\">"+dt.getLabResultCd()+"</a>";
		dt.setViewLink(url);		
	}	
	public static void makeLoincLink(LoincDT dt, String type) throws Exception{

		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		String loincCdTmp = URLEncoder.encode(dt.getLoincCd(),"ISO-8859-1");
		parameterMap.put("loinc_cd", loincCdTmp);

		if (type.equals("VIEW"))
			parameterMap.put("method", "viewLoinc");
		else
			parameterMap.put("method", "editLoinc");
		if (type.equals("VIEW")) {
			dt.setViewLink(buildHyperLink("ManageLoincs.do", parameterMap,
					"loinc", "View"));
		} else {
			dt.setEditLink(buildHyperLink("ManageLoincs.do", parameterMap,
					"loinc", "Edit"));
		}
	}
	public static void makeSnomedLink(SnomedDT dt, String type) throws Exception{

		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		String snomedCdTmp = URLEncoder.encode(dt.getSnomedCd(), "ISO-8859-1");
		parameterMap.put("SnomedCd", snomedCdTmp);
		parameterMap.put("method", "editSnomed");
		dt.setEditLink(buildHyperLink("SnomedCode.do", parameterMap, "Snomed", "Edit"));
	}	
	
	public static String laboratoryWhereClause(Map<Object,Object> searchMap) {
		StringBuffer sb = new StringBuffer("WHERE ");
		String searchCriteria = handleSearchString(searchMap.get("SRCH_CRITERIA"));
		String searchDesc = handleSearchString(searchMap.get("LAB")); 
		if(!searchDesc.equals("")) {
			if(searchCriteria.equals("CT"))
				sb.append(" upper(laboratory_system_desc_txt) like '%").append(searchDesc).append("%'");
			else if(searchCriteria.equals("="))
				sb.append(" upper(laboratory_system_desc_txt) = '").append(searchDesc).append("'");
		}		
		sb.append(" order by laboratory_system_desc_txt");
		return sb.toString();
	}
	
	public static String labTestSrchWhereClause(Map<Object,Object> searchMap) {
		StringBuffer sb = new StringBuffer("WHERE ");
		String labTestCd = handleSearchString(searchMap.get("LABTEST"));
		String searchCriteria = handleSearchString(searchMap.get("SRCH_CRITERIA"));
		String searchDesc = handleSearchString(searchMap.get("TEST_DESC"));
		String labId = handleSrch(searchMap.get("LABID")) ;
		String testType = handleSrch(searchMap.get("TEST_TYPE")) ;
		
		if(!labTestCd.equals(""))
			sb.append(" upper(lab_test_cd) = '").append(labTestCd).append("' and");
		if(!searchDesc.equals("")) {
			if(searchCriteria.equals("CT"))
				sb.append(" upper(lab_test_desc_txt) like '%").append(searchDesc).append("%' and ");
			else if(searchCriteria.equals("="))
				sb.append(" upper(lab_test_desc_txt) = '").append(searchDesc).append("' and ");
		}
		sb.append(" laboratory_id = '").append(labId).append("' and");
		sb.append(" test_type_cd = '").append(testType).append("'");
		sb.append(" order by lab_test_desc_txt");
		return sb.toString();
	}
	public static String labTestLoincSrchWhereClause(Map<Object,Object> searchMap) {
		StringBuffer sb = new StringBuffer();
		String labTestCd = handleSrch(searchMap.get("LABTEST"));
		String labId = handleSrch(searchMap.get("LABID"));		
		sb.append(" Labtest_loinc.lab_test_cd = '").append(labTestCd).append("' and");
		sb.append(" Labtest_loinc.laboratory_id = '").append(labId).append("'");
		
		return sb.toString();
	}	
	
	
	public static String labResultSrchWhereClause(Map<Object,Object> searchMap) {
		StringBuffer sb = new StringBuffer("WHERE ");
		String labTestCd = handleSearchString(searchMap.get("LABTEST"));
		String searchCriteria = handleSearchString(searchMap.get("SRCH_CRITERIA"));
		String searchDesc = handleSearchString(searchMap.get("RESULT_DESC"));
		String labId = handleSrch(searchMap.get("LABID"));
		
		if(!labTestCd.equals(""))
			sb.append(" upper(lab_result_cd) = '").append(labTestCd).append("' and");
		if(!searchDesc.equals("")) {
			if(searchCriteria.equals("CT"))
				sb.append(" upper(lab_result_desc_txt) like '%").append(searchDesc).append("%' and ");
			else if(searchCriteria.equals("="))
				sb.append(" upper(lab_result_desc_txt) = '").append(searchDesc).append("' and ");
		}
		sb.append(" laboratory_id = '").append(labId).append("'");
		sb.append(" order by lab_result_desc_txt");
		return sb.toString();
	}
	
	public static String triggerCodesSrchWhereClause(TriggerCodeForm form) {
		StringBuffer sb = new StringBuffer("WHERE ");
		String codingSystem = handleSearchString(form.getCodingSystemSelected());
		String code = handleSearchString(form.getCodeSelected());
		String displayName = handleSearchString(form.getDisplayNameSelected());
		String displayNameOperator = handleSearchString(form.getDisplayNameOperatorSelected());
		String condition = handleSrch(form.getDefaultConditionSelected());
		String query ="";
		
		TriggerCodeDAOImpl triggerCodeDaoImpl = new TriggerCodeDAOImpl();
		TriggerCodesDT dt = new TriggerCodesDT();
		String codeSystemCd = triggerCodeDaoImpl.getCodeSystemCdFromCodeValueGeneral(dt, codingSystem);
		
		
		if(displayNameOperator.equalsIgnoreCase(""))
			displayNameOperator="CT";
		
		if(!codingSystem.equals(""))
			sb.append(" cc.code_system_cd = '").append(codeSystemCd).append("' and");
		if(!code.equals(""))
			sb.append(" upper(cc.code) like '%").append(code.toUpperCase()).append("%' and");
		if(!displayName.equals("")) {
			if(displayNameOperator.equals("CT"))
				sb.append(" upper(cc.CODE_DESC_TXT ) like '%").append(displayName.toUpperCase()).append("%' and");
			else if(displayNameOperator.equals("="))
				sb.append(" upper(cc.CODE_DESC_TXT ) = '").append(displayName.toUpperCase()).append("' and");
		}
		if(!condition.equals("")){
			if(condition.equalsIgnoreCase("(NULL)"))
				sb.append(" cc.CONDITION_CD  is NULL");
			else
				sb.append(" cc.CONDITION_CD  = '").append(condition).append("'");
		}
		if(sb.lastIndexOf(" and")== sb.length()-4)
			query=sb.substring(0,sb.length()-4);
		else
			query=sb.toString();
		
		//sb.append(" order by lab_result_desc_txt");
		
		if(query!=null && query.trim().equalsIgnoreCase("WHERE"))
			query="";
		return query;
	}
	
	
	public static String labResultSnomedSrchWhereClause(Map<Object,Object> searchMap) {
		StringBuffer sb = new StringBuffer("WHERE ");
		String labResultCd = handleSrch(searchMap.get("LABRESULT"));
		String labId = handleSrch(searchMap.get("LABID"));
		
		if(!labResultCd.equals(""))
			sb.append(" lab_result_cd = '").append(labResultCd).append("' and");
		sb.append(" laboratory_id = '").append(labId).append("'");
		
		return sb.toString();
	}
	
	public static String loincSrchWhereClause(Map<Object,Object> searchMap) {
		
		StringBuffer sb = new StringBuffer("WHERE ");
		String searchCriteria = handleSearchString(searchMap.get("SRCH_CRITERIA"));
		String loincCd = handleSearchString(searchMap.get("LOINC_CD"));
		String loincDesc = handleSearchString(searchMap.get("LOINC"));
		if(!loincCd.equals(""))
			sb.append(" upper(loinc_cd) = '").append(loincCd).append("'");
		if(!loincCd.equals("") && !loincDesc.equals(""))
			sb.append(" and");
		if(!loincDesc.equals("")) {
			if(searchCriteria.equals("CT"))
				sb.append(" upper(component_name) like '%").append(loincDesc).append("%'");
			else if(searchCriteria.equals("="))
				sb.append(" upper(component_name) = '").append(loincDesc).append("'");			
		}
		sb.append(" order by component_name");
		return sb.toString();
	}

	public static String labTestPopupSrchWhereClause(Map<Object,Object> searchMap) {
		
		StringBuffer sb = new StringBuffer("AND ");
		String searchCriteria = handleSearchString(searchMap.get("SRCH_CRITERIA"));
		String searchDesc = handleSearchString(searchMap.get("LABTEST"));	
		String labId = handleSrch(searchMap.get("LABID"));
		if(searchCriteria.equals("CT"))
				sb.append(" upper(lab_test_desc_txt) like '%").append(searchDesc).append("%'");
			else if(searchCriteria.equals("="))
				sb.append(" upper(lab_test_desc_txt) = '").append(searchDesc).append("'");
		if(labId != "") {
			sb.append(" and laboratory_id = '").append(labId).append("'");	
		}
		sb.append(" order by lab_test_desc_txt");
		return sb.toString();
	}
	

	public static String loincCondSrchWhereClause(Map<Object,Object> searchMap) {
		
		StringBuffer sb = new StringBuffer(" WHERE ");		
		String loincCd = handleSrch(searchMap.get("LOINC"));	
		// insert the conditionCode searchMap attribute here 
		String conditionCd = handleSrch(searchMap.get("CONDITION"));
		if(loincCd.length()>0 && conditionCd.length()>0){
			 sb.append("  loinc_cd = '").append(loincCd).append("'");
		     sb.append("  AND condition_cd = '").append(conditionCd).append("'");
		}
		else if(loincCd.length()>0 && conditionCd.length()==0)
		 sb.append("  loinc_cd = '").append(loincCd).append("'");
		else if(conditionCd.length()>0 && loincCd.length()==0)
		 sb.append("  condition_cd = '").append(conditionCd).append("'");
		
		return sb.toString();
	}	
	
	public static String snomedSrchWhereClause(Map<Object,Object> searchMap)
	{
		StringBuffer sb = new StringBuffer("WHERE");
		String searchCriteria = handleSearchString(searchMap.get("SRCH_CRITERIA"));
		String snomedCd = handleSearchString(searchMap.get("SNOMED_CD"));
		String searchDesc=handleSearchString(searchMap.get("SNOMED"));
		if(!snomedCd.equals(""))
			sb.append(" upper(snomed_cd) = '").append(snomedCd).append("'");
		if(!snomedCd.equals("") && !searchDesc.equals(""))
			sb.append(" and");
		if(!searchDesc.equals("")) {
			if(searchCriteria.equals("CT"))
				 sb.append(" upper(snomed_desc_txt) like '%").append(searchDesc).append("%'");
			else if(searchCriteria.equals("="))
				 sb.append(" upper(snomed_desc_txt) = '").append(searchDesc).append("'");
			
		}
		
		sb.append(" order by snomed_desc_txt");
		return sb.toString();
	}
	
	public static String snomedCondSrchWhereClause(Map<Object,Object> searchMap) {

		StringBuffer sb = new StringBuffer(" WHERE ");

		String snomedCd = handleSrch(searchMap.get("SNOMED"));
		String conditionCd = handleSrch(searchMap.get("CONDITION"));
		if (snomedCd.equalsIgnoreCase("")) {
			sb.append("   condition_cd = '").append(conditionCd).append("'");
		} else if (conditionCd.equalsIgnoreCase("")) {
			sb.append("  snomed_cd = '").append(snomedCd).append("'");
		} else {
			sb.append("  snomed_cd = '").append(snomedCd).append("'");
			sb.append("  AND  condition_cd = '").append(conditionCd)
					.append("'");
		}
		return sb.toString();
	}	
	
	private static String buildHyperLink(String strutsAction, Map<Object,Object> paramMap, String jumperName, String displayNm) {
		StringBuffer url = new StringBuffer();
		StringBuffer reqParams = new StringBuffer("?");
		Iterator<Object> iter = paramMap.keySet().iterator();		
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = (String) paramMap.get(key);
			reqParams.append(key).append("=");
			reqParams.append(value);
			reqParams.append("&");
		}
		reqParams.deleteCharAt(reqParams.length() - 1);
		url.append("<a href='/nbs/");
		url.append(strutsAction);
		url.append(reqParams.toString());
		if(jumperName != null) {
			url.append("#").append(jumperName);
		}
		url.append("'>").append(displayNm).append("</a>");
		return url.toString();
	}

	private static String handleSrch(Object obj) {
		String toBeRepaced = obj == null ? "" : (String) obj;
		if(toBeRepaced.equals("")) return "";
		String specialCharacter = "'";
		String replacement = "''";
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();
		while ((e = toBeRepaced.indexOf(specialCharacter, s)) >= 0) {
			result.append(toBeRepaced.substring(s, e));
			result.append(replacement);
			s = e + specialCharacter.length();
		}
		result.append(toBeRepaced.substring(s));
		return result.toString();
	}	
	
	private static String handleSearchString(Object obj) {
		return handleSrch(obj).toUpperCase();
	}

	private static String handleSpCharForJS(String toBeRepaced) {
		String returnSt = toBeRepaced;
		
		Map<Object,Object> scMap = new HashMap<Object,Object>();
		scMap.put("'", "\\'");
		scMap.put("\"", "&quot;");
		scMap.put("\\", "\\\\");
		Iterator<Object> iter = scMap.keySet().iterator();
		while(iter.hasNext()) {
			String key = (String) iter.next();
			String replace = (String) scMap.get(key);
			StringBuffer result = new StringBuffer();
			int s = 0;
			int e = 0;
			while ((e = returnSt.indexOf(key, s)) >= 0) {
				result.append(returnSt.substring(s, e));
				result.append(replace);
				s = e + key.length();
			}
			result.append(returnSt.substring(s));
			returnSt =  result.toString();			
		}
		
		return returnSt;
	}	
	

	private static boolean cvgPagination(HttpServletRequest request) {
		boolean found = false;
		Enumeration<?> enm = request.getParameterNames();
		while(enm.hasMoreElements()) {
			String paramName = (String) enm.nextElement();
			if(paramName != null && paramName.startsWith("d-")) {
				found = true;
				break;
			}
		}
		
		return found;
	}
	// This method trims the leading and trailing spaces before inserting data in the DB
	public static void trimSpaces(Object dtObject)
	{
	 try {
		Class<?> clazzz = dtObject.getClass();
	   Object returnVal = new Object();
	   String finalVal;
	   Method methods[] = clazzz.getDeclaredMethods();
	   Method setMethod = null;
	   for (int i = 0; i < methods.length; i++) {
		   Method method = (Method) methods[i];
		   Class<?>[] paraTypes = { method.getReturnType()};
	       if (methods[i].getName().equals("isItDirty")
		   || methods[i].getName().equals("isItNew")
		   || methods[i].getName().equals("isItDelete")
		   || methods[i].getName().equals("isEqual")
		       )
	    	   continue;
		   if (methods[i].getName().startsWith("get")) {
			   returnVal = methods[i].invoke(dtObject, (Object[])null);
			   
		         if(returnVal!=null && methods[i].getReturnType().equals(String.class))
		         {
		        	 finalVal = returnVal.toString().trim();
		        	 Object[] para = { finalVal };
		        	// find the set method name 
					String setMethodName = "set" + methods[i].getName().substring(3);
					setMethod = clazzz.getMethod(setMethodName,(Class[])paraTypes );
					setMethod.invoke(dtObject, para); 
		         }	 
		       }
		   continue;
		     }
	 } 
	 catch (InvocationTargetException invk) {
       invk.printStackTrace();
     }
     catch (IllegalAccessException iacc) {
       iacc.printStackTrace();
     }catch (NoSuchMethodException nsme){     
    	 nsme.printStackTrace();
	}
		
	}
	
	public static String codeSetWhereClause(Map<Object,Object> searchMap) {
		StringBuffer sb = new StringBuffer(" WHERE ");
		String searchCriteria = handleSearchString(searchMap.get("SRCH_CRITERIA"));
		String searchDesc = handleSearchString(searchMap.get("CodeValueGen"));
		String searchCriteriaDesc = handleSearchString(searchMap.get("SRCH_CRITERIA_DESC"));
		String searchCodeDesc = handleSearchString(searchMap.get("CodeValueGenDesc")); 
		String searchCriteriaVads = handleSearchString(searchMap.get("SRCH_CRITERIA_VADS"));
		String searchVadsValCd = handleSearchString(searchMap.get("VADValueSetCd")); 
		
		
		if(!searchDesc.equals("")) {
			if(searchCriteria.equals("CT"))
				sb.append(" upper(Codeset_Group_Metadata.code_set_nm) like '%").append(searchDesc).append("%'");
			else if(searchCriteria.equals("="))
				sb.append(" upper(Codeset_Group_Metadata.code_set_nm) = '").append(searchDesc).append("'");
		}
		if(!searchDesc.equals("") && !searchCodeDesc.equals(""))
			sb.append(" and");
		if(!searchCodeDesc.equals("")) {
			if(searchCriteriaDesc.equals("CT"))
				sb.append(" upper(Codeset_Group_Metadata.code_set_desc_txt) like '%").append(searchCodeDesc).append("%'");
			else if(searchCriteriaDesc.equals("="))
				sb.append(" upper(Codeset_Group_Metadata.code_set_desc_txt) = '").append(searchCodeDesc).append("'");
		}	
		if((!searchDesc.equals("") || !searchCodeDesc.equals("")) && !searchVadsValCd.equals(""))
			sb.append(" and");
		if(!searchVadsValCd.equals("")) {
			if(searchCriteriaVads.equals("CT"))
				sb.append(" upper(Codeset_Group_Metadata.vads_value_set_code) like '%").append(searchVadsValCd).append("%'");
			else if(searchCriteriaVads.equals("="))
				sb.append(" upper(Codeset_Group_Metadata.vads_value_set_code) = '").append(searchVadsValCd).append("'");
		}
		sb.append(" and Codeset.seq_num='1' order by Codeset_Group_Metadata.code_set_nm");
		return sb.toString();
	}
	
	public static void makeCodeSetLink(CodeSetDT dt, String type) throws Exception{

		HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		String codeSetNm = URLEncoder.encode(dt.getCodeSetNm(), "ISO-8859-1");
		//String seqNum = URLEncoder.encode(dt.getSeqNum().toString(), "ISO-8859-1");
		parameterMap.put("codeSetNm", codeSetNm);
		//parameterMap.put("seqNum", seqNum);
		if(type.equals("VIEW")) {			
			parameterMap.put("method", "viewCodeSet");
			dt.setViewLink(buildHyperLink("ManageCodeSet.do", parameterMap, "CodeSet", "View"));
		} else if(dt.getIsModifiableInd()== null ||dt.getIsModifiableInd().equals("Y")){
			parameterMap.put("method", "editCodeSet");
			dt.setEditLink(buildHyperLink("ManageCodeSet.do", parameterMap, "CodeSet", "Edit"));
		}else if(dt.getIsModifiableInd().equals("N"))
		{
			String sb  = "<span style=\"color:#DDD; text-decoration:underline;\">" + "Edit" + "</span>";
			dt.setEditLink(sb);
		}
	}
	
	public static Boolean checkVADSInSystem(HttpSession session, CodeSetDT dt)throws Exception{
		MainSessionCommand msCommand = null;
		ConditionDT conditionDT  = new ConditionDT();
		Boolean returnVal = null;
		try{
			String sBeanJndiName = JNDINames.SRT_ADMIN_EJB;
			String sMethod = "checkVADSInSystem";
			Object[] oParams =new Object[] {dt};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<Object> arr =(ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if ((arr != null) && (arr.size() > 0)) {
			returnVal = (Boolean) arr.get(0);
				}
			}catch (Exception ex) {
				logger.fatal("Error in checkVADSInSystem in Action Util: ", ex);
				throw new Exception(ex.toString());
			}
			return returnVal;
	}
	
	public static void importValueSet(PhinVadsSystemVO phinVadsVo, HttpSession session)throws Exception{
		MainSessionCommand msCommand = null;
		ConditionDT conditionDT  = new ConditionDT();
		Boolean returnVal = null;
		try{
			String sBeanJndiName = JNDINames.SRT_ADMIN_EJB;
			String sMethod = "importValueSet";
			Object[] oParams =new Object[] {phinVadsVo};				
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			
			}catch (Exception ex) {
				logger.fatal("Error in importValueSet in Action Util: ", ex);
				throw new Exception(ex.toString());
			}
	}
	public ArrayList<Object> getType(Collection<Object>  codesetDTColl) {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (codesetDTColl != null) {
			java.util.Iterator<?> iter = codesetDTColl.iterator();
			while (iter.hasNext()) {
				CodeSetDT dt = (CodeSetDT) iter.next();
				if (dt.getValueSetTypeCd()!= null) {
					typeMap.put(dt.getValueSetTypeCd(), dt.getValueSetTypeCd());
							
				}
				if(dt.getValueSetTypeCd() == null || dt.getValueSetTypeCd().trim().equals("")){
					typeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_INVESTIGATOR_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}
	public ArrayList<Object> getStatusDropDowns(Collection<Object>  codesetColl) {
		Map<Object, Object> statusMap = new HashMap<Object,Object>();
		if (codesetColl != null) {
			java.util.Iterator<?> iter = codesetColl.iterator();
			while (iter.hasNext()) {
				CodeSetDT dt = (CodeSetDT) iter.next();
				if (dt.getStatusCd()!= null) {
					statusMap.put(dt.getStatusCd(), CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
							
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(statusMap);
	}
	
	public static String getSortCriteria(String sortOrder, String methodName){
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getValueSetTypeCd"))
				sortOrdrStr = "Type";
			else if(methodName.equals("getCodeSetNm"))
				sortOrdrStr = "Value Set Code";
			else if(methodName.equals("getCodeSetShortDescTxt"))
				sortOrdrStr = "Value Set Name";
			else if(methodName.equals("getStatusCd"))				
				sortOrdrStr = "Status";	
			else if(methodName.equals("getCodeSetDescTxt"))
				sortOrdrStr = "Value Set Description";
			
		} else {
				sortOrdrStr = "Value Set Code";
		}
		
		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" in descending order ";

		return sortOrdrStr;
			
	}
	
	public static void updateCodingSystemforEdit(CodeValueGeneralDT dt) throws ServletException{
		TreeMap<Object,Object> returnMap = CachedDropDowns.getStandredConceptCVGCodes();
		Collection<Object> CVGs = returnMap.values();
		if(CVGs !=null && CVGs.size()>0){
			Iterator<Object> ite  = CVGs.iterator();
			while(ite.hasNext()){
				CodeValueGeneralCachedDT dtCVGCache = (CodeValueGeneralCachedDT)ite.next();
				if(dtCVGCache.getCodeDescTxt().equals(dt.getCodeSystemCd())){
					dt.setCodeSystemCd(dtCVGCache.getCode());
					break;
				}
			}
		}
	}
	
}

