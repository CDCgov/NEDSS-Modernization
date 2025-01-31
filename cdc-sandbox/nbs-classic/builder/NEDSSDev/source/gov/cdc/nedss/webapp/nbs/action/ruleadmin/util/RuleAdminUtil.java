package gov.cdc.nedss.webapp.nbs.action.ruleadmin.util;


import gov.cdc.nedss.srtadmin.dt.LoincDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RuleAdminUtil {

	static final LogUtils logger = new LogUtils(RuleAdminUtil.class.getName());
	
	public static Object processRequest(Object[] oParams, HttpSession session) throws Exception {

		MainSessionCommand msCommand = null;
		Object obj = null;

		try {
			String sBeanJndiName = JNDINames.QUESTION_MAP_EJB;
			String sMethod = "processRuleAdminRequest";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			obj = arr.get(0);

			} catch (Exception ex) {
				logger.error("Exception in RuleAdminUtil.processRequest calling processRuleAdminRequest: ", ex);	
				throw new Exception(ex);
			}
			return obj;	
		}	
		
	public static void handleErrors(Exception e, HttpServletRequest request, String actionType,String fileds) {
		
		//handle special scenario for codevalue general pagination after submit
		if(cvgPagination(request)) return;		
		if(e.toString().indexOf("SQLException") != -1 || e.toString().indexOf("SQLServerException") != -1) {
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
		
		Map<Object, Object> scMap = new HashMap<Object,Object>();
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
	
   public static String RuleSrchWhereClause(Map<Object, Object> searchMap) {
		
	    StringBuffer sb = new StringBuffer("");
		String searchCriteria ="";
		String searchCriteria1 ="";
		searchCriteria = handleSearchString(searchMap.get("RULE"));
		searchCriteria1 = handleSearchString(searchMap.get("PAMQ"));
		
		if((searchCriteria1 == null || searchCriteria1.equalsIgnoreCase("")) && !(searchCriteria == null || searchCriteria.equalsIgnoreCase(""))){
		 sb.append("FROM ");
		 sb.append("dbo.[rule] A,consequence_indicator B,rule_instance C");
		 sb.append(" where ");
		 sb.append("  A.rule_uid = '").append(searchCriteria).append("'");
		 sb.append("  AND C.rule_uid = A.rule_uid and C.conseq_ind_uid = B.conseq_ind_uid order by A.rule_name");
		 
		 }else if((searchCriteria == null || searchCriteria.equalsIgnoreCase("")) && !(searchCriteria1 == null || searchCriteria1.equalsIgnoreCase(""))){ 	
			 sb.append("FROM ");
			 sb.append("dbo.[rule] A,consequence_indicator B,rule_instance C, Source_field D ");
			 sb.append(" where ");
			 sb.append("  d.nbs_metadata_rule_uid = '").append(searchCriteria1).append("'");
			 sb.append(" and C.rule_instance_uid = D.rule_instance_uid AND C.rule_uid =");
			 sb.append(" A.rule_uid and C.conseq_ind_uid = B.conseq_ind_uid order by A.rule_name");
		
		 }else{
			 sb.append("FROM ");
			 sb.append("dbo.[rule] A,consequence_indicator B,rule_instance C, Source_field D ");
			 sb.append(" where ");
			 sb.append("  d.nbs_metadata_rule_uid = '").append(searchCriteria1).append("'");
			 sb.append(" and C.rule_instance_uid = D.rule_instance_uid AND C.rule_uid =");
			 sb.append(" A.rule_uid and C.conseq_ind_uid = B.conseq_ind_uid ");
			 sb.append(" and A.rule_uid = '").append(searchCriteria).append("'");
			 sb.append(" order by A.rule_name");
			 
		
		 }
		 return sb.toString();
	}
   
   public static String RuleInsSrchWhereClause(String strRuleInsUid) {
	    StringBuffer sb = new StringBuffer("");
	     sb.append("FROM ");
		 sb.append("dbo.[rule] A,consequence_indicator B,rule_instance C");
		 sb.append(" where ");	     		
		 sb.append("  C.rule_instance_uid = '").append(strRuleInsUid).append("'");
		 sb.append("  AND C.rule_uid = A.rule_uid and C.conseq_ind_uid = B.conseq_ind_uid order by A.rule_name");
		 return sb.toString();
	}
   
   public static String RuleSFWhereClause(String strRuleInsUid) {
		
		 StringBuffer sb = new StringBuffer(" where");			 
		 sb.append(" A.nbs_question_uid = B.component_uid  and ");		 
		 sb.append(" C.nbs_metadata_rule_uid = B.nbs_metadata_rule_uid and ");
		 sb.append(" C.rule_instance_uid = '").append(strRuleInsUid).append("'");
		 sb.append(" order by C.source_field_uid desc");
		 return sb.toString();
	}
   
   public static String RuleTFWhereClause(String strRuleInsUid) {
		
		 StringBuffer sb = new StringBuffer(" where");	 
		 sb.append(" A.nbs_question_uid = B.component_uid  and ");
		 sb.append(" C.nbs_metadata_rule_uid = B.nbs_metadata_rule_uid and ");
		 sb.append(" C.rule_instance_uid = '").append(strRuleInsUid).append("'");
		 sb.append(" order by C.target_field_uid desc");
		 return sb.toString();
	}
   
   public static String RuleSVWhereClause(String strSourceFieldUid) {
		
		 StringBuffer sb = new StringBuffer(" where");		
		 sb.append(" A.source_field_uid = '").append(strSourceFieldUid).append("'");
		 sb.append(" and A.operator_type_uid = B.operator_type_uid ");
		 sb.append(" order by A.source_value_uid desc");
		 return sb.toString();
	}
   
   public static String RuleTVWhereClause(String strSourceFieldUid) {
		
		 StringBuffer sb = new StringBuffer(" where");		
		 sb.append(" A.target_field_uid = '").append(strSourceFieldUid).append("'");
		 sb.append(" and A.error_message_uid = B.error_message_uid ");
		 sb.append(" and A.conseq_ind_uid = C.conseq_ind_uid ");
		 sb.append(" and A.operator_type_uid = D.operator_type_uid ");
		 sb.append(" order by A.target_value_uid desc");
		 
		 return sb.toString();
	}
   
   public static void makeRuleInsLink(RulesDT dt, String type) throws Exception{
	    HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		parameterMap.put("ruleInstanceUid", dt.getRuleInstanceUid().toString());
				
		if (type.equals("VIEW/EDIT"))
			parameterMap.put("method", "viewRuleIns");
		else if (type.equals("DELETE"))
			parameterMap.put("method", "deleteRuleIns");
		else if (type.equals("PREVIEW"))
			parameterMap.put("method", "previewRuleIns");
		else
			parameterMap.put("method", "editRuleIns");
		if (type.equals("VIEW/EDIT")) {
			dt.setViewLink(buildHyperLink("RuleAdmin.do", parameterMap,	"rule", "View/Edit"));
		}
        else if (type.equals("DELETE")) {
				dt.setDeleteLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "Delete"));
		}
        else if (type.equals("PREVIEW")) {
			dt.setPreviewLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "Preview"));
	    }else {
			dt.setEditLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "Edit"));
		}
	}
   
   public static void makeSourceFieldLink(SourceFieldDT dt, String type) throws Exception{
	    HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		parameterMap.put("sourceFieldUid", dt.getSourceFieldUid().toString());
				
		if (type.equals("VIEW"))
			parameterMap.put("method", "viewSourceField");
		else if (type.equals("DELETE"))
			parameterMap.put("method", "deleteSourceField");
		
		if (type.equals("VIEW")) {
			dt.setViewLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "View"));
		}
       else if (type.equals("DELETE")) {
				dt.setDeleteLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "Delete"));
		} 
      
	}
   
   public static void makeTargetFieldLink(TargetFieldDT dt, String type) throws Exception{
	    HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		parameterMap.put("targetFieldUid", dt.getTargetFieldUid().toString());
				
		if (type.equals("VIEW"))
			parameterMap.put("method", "viewTargetField");
		else if (type.equals("DELETE"))
			parameterMap.put("method", "deleteTargetField");
		
		if (type.equals("VIEW")) {
			dt.setViewLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "View"));
		}
      else if (type.equals("DELETE")) {
				dt.setDeleteLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "Delete"));
		}     
	}
   
   public static void makeSourceValueLink(SourceValueDT dt, String type) throws Exception{
	    HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		parameterMap.put("sourceValueUid", dt.getSourceValueUid().toString());
				
		if (type.equals("VIEW"))
			parameterMap.put("method", "viewSourceValue");
		else if (type.equals("DELETE"))
			parameterMap.put("method", "deleteSourceValue");
		else if (type.equals("ADD"))
			parameterMap.put("method", "addNewSourceValue");
		else
			parameterMap.put("method", "editSourceValue");
		if (type.equals("VIEW")) {
			dt.setViewLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "View"));
		}
      else if (type.equals("DELETE")) {
				dt.setDeleteLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "Delete"));
		} 
      else if (type.equals("ADD")) {
			dt.setDeleteLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "Add"));
	  }else {
			dt.setEditLink(buildHyperLink("RuleAdmin.do", parameterMap,
					"rule", "Edit"));
		}
	}
   
   public static void makeTargetValueLink(TargetValueDT dt, String type) throws Exception{
	    HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
		parameterMap.put("targetValueUid", dt.getTargetValueUid().toString());
				
		if (type.equals("VIEW"))
			parameterMap.put("method", "viewTargetValue");
		else if (type.equals("DELETE"))
			parameterMap.put("method", "deleteTargetValue");
		else if (type.equals("ADD"))
			parameterMap.put("method", "addNewTargetValue");
		else
			parameterMap.put("method", "editTargetValue");
		if (type.equals("VIEW")) {
			dt.setViewLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "View"));
		}
      else if (type.equals("DELETE")) {
				dt.setDeleteLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "Delete"));
		}
      else if (type.equals("ADD")) {
			dt.setDeleteLink(buildHyperLink("RuleAdmin.do", parameterMap,"rule", "Add"));
	}else {
			dt.setEditLink(buildHyperLink("RuleAdmin.do", parameterMap,
					"rule", "Edit"));
		}
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
   

}
