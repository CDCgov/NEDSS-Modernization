package gov.cdc.nedss.webapp.nbs.action.ruleadmin;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.ruleadmin.util.RuleAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.ruleadmin.util.RuleAdminUtil;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.ruleadmin.RuleAdminForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.RuleAdminHelper;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.*;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;

public class RuleAdminAction extends DispatchAction {
	static final LogUtils logger = new LogUtils(RuleAdminAction.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	static final String VIEWEDIT = "VIEW/EDIT";
	static final String DELETE = "DELETE";
	static final String PREVIEW = "PREVIEW";
	static final String DELETEADD = "DELETE&ADD";
	static final String ADD = "ADD";
	
	public static final String TABLE_START = "<table role=\"presentation\" border=1>";

	public static final String TABLE_END = "</table>";

	public static final String TR_START = "<tr>";

	public static final String TR_END = "</tr>";

	public static final String TD_START = "<td>";

	public static final String TD_END = "</td>";

	public static final String TD_ALIGN_RIGHT = "<td align='right'>";

	public static final String TD_ALIGN_LEFT = "<td align='left'>";

	public static final String BOLD_START = "<b>";

	public static final String BOLD_END = "</b>";

	public static final String TAGCLOSER = ">";

	public ActionForward manageRuleAdmin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try {
			RuleAdminForm manageForm = (RuleAdminForm) form;
			manageForm.setActionMode(null);

			manageForm.getAttributeMap().clear();

		} catch (Exception e) {
			logger.error("Exception in searchLoincSubmit: " + e.getMessage());
			RuleAdminUtil.handleErrors(e, request, "search", "");
			return (mapping.findForward("manageRuleAdmin"));
		} finally {

		}

		return mapping.findForward("manageRuleAdmin");
	}

	public ActionForward searchRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			RuleAdminForm manageForm = (RuleAdminForm) form;
			String searchCriteria ="";String searchCriteria1 ="";
			manageForm.setActionMode("VIEWRULES");
			String whereClause = RuleAdminUtil.RuleSrchWhereClause(manageForm
					.getSearchMap());
			if(manageForm.getSearchMap().get("RULE") != null)
			 searchCriteria = manageForm.getSearchMap().get("RULE").toString();
			if(manageForm.getSearchMap().get("PAMQ") != null)
			 searchCriteria1 = manageForm.getSearchMap().get("PAMQ").toString();
			if((searchCriteria == null || searchCriteria.equalsIgnoreCase(""))&&(searchCriteria1 == null || searchCriteria1.equalsIgnoreCase(""))){
				request.setAttribute("error", "Please select either a Rule or NBS question to Continue");
			}
			manageForm.getAttributeMap().clear();
			RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
			ArrayList<Object> dtList = ruleAdminHelper.findRuleInstances(whereClause);
			if (dtList != null && dtList.size() > 0) {
				Iterator<Object>  iter = dtList.iterator();
				while (iter.hasNext()) {
					RulesDT dt = (RulesDT) iter.next();
					RuleAdminUtil.makeRuleInsLink(dt, VIEWEDIT);
					RuleAdminUtil.makeRuleInsLink(dt, EDIT);
					RuleAdminUtil.makeRuleInsLink(dt, DELETE);
					RuleAdminUtil.makeRuleInsLink(dt, PREVIEW);
				}
			} else {
				manageForm.getAttributeMap().put("NORESULT", "NORESULT");
			}

			manageForm.setManageList(dtList);
			manageForm.setRuleInsList(dtList);
			request.setAttribute("manageList", manageForm.getManageList());

		} catch (Exception e) {
			logger.error("Exception in searchRule: " + e.getMessage());
			RuleAdminUtil.handleErrors(e, request, "search", "");
			return (mapping.findForward("manageRuleAdmin"));
		} finally {
			request.setAttribute(RuleAdminConstants.PAGE_TITLE,
					RuleAdminConstants.MANAGE_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));

	}

	public ActionForward addRuleInstance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			RuleAdminForm manageForm = (RuleAdminForm) form;
			String RuleUID ="";  
			manageForm.resetSelection();
			RulesDT rulesDT = new RulesDT();
			manageForm.setSelection(rulesDT);
			RulesDT dt = (RulesDT) manageForm.getSelection();
            if(manageForm.getSearchMap().get("RULE") != null) 
			RuleUID = manageForm.getSearchMap().get("RULE").toString();
			if(RuleUID==null || RuleUID.equalsIgnoreCase("")){
				request.setAttribute("error", "Please select a Rule to add a new Rule Instance");
			}
			String RuleName = "";
			ArrayList<Object> ruleList = manageForm.getManageList();
			if (ruleList != null && ruleList.size() > 0) {
				Iterator<Object>  iter = ruleList.iterator();
				while (iter.hasNext()) {
					RulesDT dt1 = (RulesDT) iter.next();
					String strRuleID = dt1.getRuleUid().toString();
					if (strRuleID.equals(RuleUID)) {
						RuleName = dt1.getRuleName();
						break;
					}
				}
			}
			
			request.setAttribute("manageList", manageForm.getManageList());
			dt.setRuleUid(new Long(Integer.parseInt(manageForm
					.getSearchCriteria("RULE").toString())));
			dt.setRuleName(RuleName);
			manageForm.setActionMode("ADDRULEINSTANCE");

		} catch (Exception e) {
			logger.error("Exception in searchRule: " + e.getMessage());
			RuleAdminUtil.handleErrors(e, request, "search", "");
			return (mapping.findForward("manageRuleAdmin"));
		} finally {
			request.setAttribute(RuleAdminConstants.PAGE_TITLE,
					RuleAdminConstants.MANAGE_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));

	}

	public ActionForward createRuleInstance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RuleAdminForm manageForm = (RuleAdminForm) form;
		RulesDT dt=null;
		String conseqUID="";
		try {
            
			 dt = (RulesDT) manageForm.getSelection();
			RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
			
			//chnage it later
			if(manageForm.getSearchMap().get("CONSEQIND") != null) 
				conseqUID = manageForm.getSearchMap().get("CONSEQIND").toString();
			if(conseqUID==null || conseqUID.equalsIgnoreCase("")){
				request.setAttribute("error", "Please select a Conseq indicator to add a new Rule Instance");
			}else{
				
			dt.setConseqIndUID(new Long((manageForm.getSearchMap().get("CONSEQIND")).toString()));

			String strRuleInsUID = ruleAdminHelper.addRuleInstance(dt);
			if (strRuleInsUID.equalsIgnoreCase("")) {
				dt = (RulesDT) manageForm.getSelection();
				manageForm.setActionMode("ADDRULEINSTANCE");
				ArrayList<Object> conseqList = RuleAdminHelper.getConseqInd();
				DropDownCodeDT ddDT = new DropDownCodeDT();
				if (conseqList.size() > 0) {
					for (int i = 0; i < conseqList.size(); i += 1) {
						ddDT = (DropDownCodeDT) conseqList.get(i);
						if (ddDT.getKey().equalsIgnoreCase(
								manageForm.getSearchCriteria("CONSEQIND"))) {
							dt.setConseqIndtxt(ddDT.getValue());
							break;
						}
					}
				}
				manageForm.setSelection(dt);
				request.setAttribute("error",
						"error while adding the new Rule Instance");
			} else {
				dt = (RulesDT) manageForm.getSelection();
							dt.setRuleInstanceUid(new Long(strRuleInsUID));
							//chnage it later
			  	dt.setConseqIndUID(new Long(manageForm.getSearchCriteria(
						"CONSEQIND").toString()));
				ArrayList<Object> conseqList = RuleAdminHelper.getConseqInd();
				DropDownCodeDT ddDT = new DropDownCodeDT();
				if (conseqList.size() > 0) {
					for (int i = 0; i < conseqList.size(); i += 1) {
						ddDT = (DropDownCodeDT) conseqList.get(i);
						if (ddDT.getKey().equalsIgnoreCase(
								manageForm.getSearchCriteria("CONSEQIND"))) {
							dt.setConseqIndtxt(ddDT.getValue());
							break;
						}
					}
				}

				manageForm.resetSelection();
				manageForm.setOldDT(dt);
				manageForm.setOldSFDT(null);
				manageForm.setOldTFDT(null);
				manageForm.setOldSVDT(null);
				manageForm.setOldTVDT(null);
				manageForm.setSrcFieldList(null);
				manageForm.setTarFieldList(null);
				manageForm.setSrcValueList(null);
				manageForm.setTarValueList(null);
				manageForm.setFrmCodeList(null);
				
				request.setAttribute("manageListold", manageForm.getOldDT());
				manageForm.setActionMode("ADDSRCTARS");
			}
			request.setAttribute("manageList", manageForm.getManageList());
			}

		} catch (Exception e) {
			logger.error("Exception in createRuleInstance: " + e.getMessage());
			e.printStackTrace();
			RuleAdminUtil.handleErrors(e, request, "search", "");
			manageForm.setActionMode("ADDRULEINSTANCE");
			return (mapping.findForward("manageRuleAdmin"));
		} finally {
			request.setAttribute(RuleAdminConstants.PAGE_TITLE,
					RuleAdminConstants.MANAGE_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));

	}

	public ActionForward addSourceField(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RuleAdminForm manageForm = (RuleAdminForm) form;
		try {
            String ruleInstanceUid ="";
			SourceFieldDT dt = new SourceFieldDT();
			RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
			String pamQ="";
			if(manageForm.getSearchMap().get("PAMSFQ") != null)
				pamQ = manageForm.getSearchMap().get("PAMSFQ").toString();
			if(pamQ == null || pamQ.equalsIgnoreCase("")){
				request.setAttribute("error","Please select a NBS Question to add a new Source Field.");	
				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageListold", manageForm.getOldDT());
				request.setAttribute("manageTFList", manageForm.getTarFieldList());
				request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
			}else{
				
			dt.setPamQuestionUID(new Long((manageForm.getSearchMap()
					.get("PAMSFQ")).toString()));
			RulesDT rulesDT = (RulesDT) manageForm.getOldDT();
			dt.setRuleInstanceUid(rulesDT.getRuleInstanceUid());
			ruleInstanceUid = rulesDT.getRuleInstanceUid().toString();
			String srcFieldUID = ruleAdminHelper.addSourceField(dt);

			if (srcFieldUID.equalsIgnoreCase("")) {
				dt = (SourceFieldDT) manageForm.getSelection();
				manageForm.setActionMode("ADDSRCTARS");
				ArrayList<Object> pamList = RuleAdminHelper.getPAMLabels();
				DropDownCodeDT ddDT = new DropDownCodeDT();
				if (pamList.size() > 0) {
					for (int i = 0; i < pamList.size(); i += 1) {
						ddDT = (DropDownCodeDT) pamList.get(i);
						if (ddDT.getKey() != null && ddDT.getKey().equalsIgnoreCase(manageForm.getSearchMap().get("PAMSFQ").toString())) {
							dt.setQuestionLabel(ddDT.getValue());
							break;
						}
					}
				}
				manageForm.setSelection(dt);
				request.setAttribute("error",
						"error while adding the new Rule Instance");
			} else {
			
				dt.setSourceFieldUid(new Long(srcFieldUID));
				dt.setPamQuestionUID(new Long((manageForm.getSearchMap()
						.get("PAMSFQ")).toString()));
				ArrayList<Object> pamList = RuleAdminHelper.getPAMLabels();
				DropDownCodeDT ddDT = new DropDownCodeDT();
				if (pamList.size() > 0) {
					for (int i = 0; i < pamList.size(); i += 1) {
						ddDT = (DropDownCodeDT) pamList.get(i);
						if (ddDT.getKey() != null && ddDT.getKey().equalsIgnoreCase(
								(manageForm.getSearchMap().get("PAMSFQ"))
										.toString())) {
							dt.setQuestionIdentifier(ddDT.getValue());
							break;
						}
					}
				}
 
				String whereClause = RuleAdminUtil.RuleSFWhereClause(ruleInstanceUid); 
				ArrayList<Object> dtSFList = ruleAdminHelper.findSourceFields(whereClause);
				
				if(manageForm.getActionMode().equalsIgnoreCase("ADDSRCTARS")){
				manageForm.setOldSFDT(dt);	
				manageForm.setSrcFieldList(dtSFList);
				
				if(manageForm.getOldSVDT() != null)
				manageForm.setOldSVDT(null);
				if(manageForm.getSrcValueList() != null)
					manageForm.setSrcValueList(null);
				
				
				
				if(manageForm.getTarFieldList()!= null)
				request.setAttribute("manageTFList", manageForm.getTarFieldList());
				
				request.setAttribute("manageListold", manageForm.getOldDT());
				//request.setAttribute("manageListSF", manageForm.getOldSFDT());
				request.setAttribute("manageListSF", manageForm.getSrcFieldList());
				
				//if(manageForm.getOldTVDT()!= null)
					//request.setAttribute("manageListTV", manageForm.getOldTVDT());
				if(manageForm.getTarValueList()!= null)
					request.setAttribute("manageListTV", manageForm.getTarValueList());
				
				manageForm.setActionMode("ADDSRCTARVALUES");
				}else{
					
					if (dtSFList != null && dtSFList.size() > 0) {
						manageForm.getAttributeMap().remove("NORESULTSF");
						Iterator<Object>  iter1 = dtSFList.iterator();
						while (iter1.hasNext()) {
							SourceFieldDT dt1 = (SourceFieldDT) iter1.next();
							RuleAdminUtil.makeSourceFieldLink(dt1, VIEW);
							RuleAdminUtil.makeSourceFieldLink(dt1, DELETE);
							RuleAdminUtil.makeSourceFieldLink(dt1,ADD);
						}
					} else {
						manageForm.getAttributeMap().put("NORESULTSF", "NORESULT");
					}

					whereClause = RuleAdminUtil.RuleTFWhereClause(ruleInstanceUid);
					ArrayList<Object> dtTFList = ruleAdminHelper.findTargetFields(whereClause);

					if (dtTFList != null && dtTFList.size() > 0) {
						Iterator<Object>  iter2 = dtTFList.iterator();
						while (iter2.hasNext()) {
							TargetFieldDT dt2 = (TargetFieldDT) iter2.next();
							RuleAdminUtil.makeTargetFieldLink(dt2, VIEW);
							RuleAdminUtil.makeTargetFieldLink(dt2, DELETE);
							RuleAdminUtil.makeTargetFieldLink(dt2,ADD);
						}
					}

				

				else {
					manageForm.getAttributeMap().put("NORESULTTF", "NORESULT");
				}

				manageForm.setManageList(dtSFList);
				manageForm.setSrcFieldList(dtSFList);
				manageForm.setTarFieldList(dtTFList);
				manageForm.setOldSFDT(dt);

				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageListold", manageForm.getOldDT());
				if(manageForm.getTarFieldList()!= null)
					request.setAttribute("manageTFList", manageForm.getTarFieldList());
				request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
				request.setAttribute("manageListSF", manageForm.getSrcFieldList());
				if(manageForm.getTarValueList()!= null)
					request.setAttribute("manageListTV", manageForm.getTarValueList());
				if(manageForm.getActionMode().equalsIgnoreCase("VIEWSRCTARFORMCODE"))
					manageForm.setActionMode("ADDSRCTARVALUES");
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewRuleIns&context=cancel#result");

					
					
				}
			}
			request.setAttribute("manageList", manageForm.getManageList());
			}

		} catch (Exception e) {
			logger.error("Exception in addSourceField: " + e.getMessage());
			e.printStackTrace();
			RuleAdminUtil.handleErrors(e, request, "search", "");
			manageForm.setActionMode("ADDSRCTARS");
			return (mapping.findForward("manageRuleAdmin"));
		} finally {
			request.setAttribute(RuleAdminConstants.PAGE_TITLE,
					RuleAdminConstants.MANAGE_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));

	}
	
	public ActionForward addTargetField(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RuleAdminForm manageForm = (RuleAdminForm) form;
		try {
            String ruleInstanceUid ="";
			TargetFieldDT dt = new TargetFieldDT();
			RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
			String pamQ ="";
			if(manageForm.getSearchMap().get("PAMTFQ") != null)
				pamQ = manageForm.getSearchMap().get("PAMTFQ").toString();
			if(pamQ == null || pamQ.equalsIgnoreCase("")){
				request.setAttribute("error","Please select a NBS Question to add a new Target field.");	
				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageListold", manageForm.getOldDT());
				request.setAttribute("manageTFList", manageForm.getTarFieldList());
				request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
			}else{
			dt.setPamQuestionUID(new Long((manageForm.getSearchMap()
					.get("PAMTFQ")).toString()));
			RulesDT rulesDT = (RulesDT) manageForm.getOldDT();
			dt.setRuleInstanceUid(rulesDT.getRuleInstanceUid());
			ruleInstanceUid = rulesDT.getRuleInstanceUid().toString();
			String srcFieldUID = ruleAdminHelper.addTargetField(dt);

			if (srcFieldUID.equalsIgnoreCase("")) {
				dt = (TargetFieldDT) manageForm.getSelection();
				manageForm.setActionMode("ADDSRCTARS");
				ArrayList<Object> pamList = RuleAdminHelper.getPAMLabels();
				DropDownCodeDT ddDT = new DropDownCodeDT();
				if (pamList.size() > 0) {
					for (int i = 0; i < pamList.size(); i += 1) {
						ddDT = (DropDownCodeDT) pamList.get(i);
						if (ddDT.getKey() != null && ddDT.getKey().equalsIgnoreCase(manageForm.getSearchMap().get("PAMTFQ").toString())) {
							dt.setQuestionLabel(ddDT.getValue());
							break;
						}
					}
				}
				manageForm.setSelection(dt);
				request.setAttribute("error",
						"error while adding the new Rule Instance");
			} else {
				
				dt.setTargetFieldUid(new Long(srcFieldUID));
				dt.setPamQuestionUID(new Long((manageForm.getSearchMap()
						.get("PAMTFQ")).toString()));
				ArrayList<Object> pamList = RuleAdminHelper.getPAMLabels();
				DropDownCodeDT ddDT = new DropDownCodeDT();
				if (pamList.size() > 0) {
					for (int i = 0; i < pamList.size(); i += 1) {
						ddDT = (DropDownCodeDT) pamList.get(i);
						if (ddDT.getKey() != null &&  ddDT.getKey().equalsIgnoreCase(
								(manageForm.getSearchMap().get("PAMTFQ"))
										.toString())) {
							dt.setQuestionIdentifier(ddDT.getValue());
							break;
						}
					}
				}

				String whereClause = RuleAdminUtil.RuleTFWhereClause(ruleInstanceUid);
				ArrayList<Object> dtTFList = ruleAdminHelper.findTargetFields(whereClause); 
			    if(manageForm.getActionMode().equalsIgnoreCase("ADDSRCTARS")){
				manageForm.setOldTFDT(dt);
				manageForm.setTarFieldList(dtTFList);
				
				if(manageForm.getOldTVDT() != null)
				manageForm.setOldTVDT(null);
				if(manageForm.getTarValueList() != null)
				manageForm.setTarValueList(null);
				
				//if(manageForm.getOldSFDT()!= null)
				//request.setAttribute("manageListSF", manageForm.getOldSFDT());
				
				if(manageForm.getSrcFieldList()!= null)
				request.setAttribute("manageListSF", manageForm.getSrcFieldList());
				
				request.setAttribute("manageListold", manageForm.getOldDT());
				
				request.setAttribute("manageTFList", manageForm.getTarFieldList());
				//if(manageForm.getOldSVDT()!= null)
					//request.setAttribute("manageListSV", manageForm.getOldSVDT());
				if(manageForm.getSrcValueList()!= null)
					request.setAttribute("manageListSV", manageForm.getSrcValueList());
				
				manageForm.setActionMode("ADDSRCTARVALUES");
			    }else{
			    	 whereClause = RuleAdminUtil
						.RuleSFWhereClause(ruleInstanceUid);
						ArrayList<Object> dtSFList = ruleAdminHelper.findSourceFields(whereClause);

						if (dtSFList != null && dtSFList.size() > 0) {
							Iterator<Object>  iter1 = dtSFList.iterator();
							manageForm.getAttributeMap().remove("NORESULTTF");
							while (iter1.hasNext()) {
								SourceFieldDT dt1 = (SourceFieldDT) iter1.next();
								RuleAdminUtil.makeSourceFieldLink(dt1, VIEW);
								RuleAdminUtil.makeSourceFieldLink(dt1, DELETE);
								RuleAdminUtil.makeSourceFieldLink(dt1,ADD);
							}
						} else {
							manageForm.getAttributeMap().put("NORESULTTF", "NORESULT");
						}
	

						if (dtTFList != null && dtTFList.size() > 0) {
							Iterator<Object>  iter2 = dtTFList.iterator();
							while (iter2.hasNext()) {
								TargetFieldDT dt2 = (TargetFieldDT) iter2.next();
								RuleAdminUtil.makeTargetFieldLink(dt2, VIEW);
								RuleAdminUtil.makeTargetFieldLink(dt2, DELETE);
								RuleAdminUtil.makeTargetFieldLink(dt2,ADD);
							}
						}		

					else {
						manageForm.getAttributeMap().put("NORESULTTF", "NORESULT");
					  }
					
					manageForm.setManageList(dtSFList);
					manageForm.setSrcFieldList(dtSFList);
					manageForm.setTarFieldList(dtTFList);
					manageForm.setOldTFDT(dt);

					request.setAttribute("manageList", manageForm.getManageList());
					request.setAttribute("manageListold", manageForm.getOldDT());
					request.setAttribute("manageTFList", manageForm.getTarFieldList());
					request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
					if(manageForm.getSrcFieldList()!= null)
						request.setAttribute("manageListSF", manageForm.getSrcFieldList());
					if(manageForm.getSrcValueList()!= null)
						request.setAttribute("manageListSV", manageForm.getSrcValueList());
					if(manageForm.getTarValueList() != null)
						manageForm.setTarValueList(null);
					if(manageForm.getActionMode().equalsIgnoreCase("VIEWSRCTARFORMCODE"))
						manageForm.setActionMode("ADDSRCTARVALUES");

					manageForm
							.getAttributeMap()
							.put("cancel",
									"/nbs/RuleAdmin.do?method=viewRuleIns&context=cancel#result");
			    	
			    }
			}
			request.setAttribute("manageList", manageForm.getManageList());
		  }

		} catch (Exception e) {
			logger.error("Exception in addTargetField: " + e.getMessage());
			e.printStackTrace();
			RuleAdminUtil.handleErrors(e, request, "search", "");
			manageForm.setActionMode("ADDSRCTARS");
			return (mapping.findForward("manageRuleAdmin"));
		} finally {
			request.setAttribute(RuleAdminConstants.PAGE_TITLE,
					RuleAdminConstants.MANAGE_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));

	}
	
	public ActionForward addSourceValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RuleAdminForm manageForm = (RuleAdminForm) form;
		try {
            String srcFieldUid ="";
			SourceValueDT dt = new SourceValueDT();
			RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				request.setAttribute("manageList", manageForm.getSrcValueList());
				request.setAttribute("manageListold", manageForm.getOldSFDT());
				request.setAttribute("manageRList", manageForm.getOldDT());
				request.setAttribute("manageSFDT", manageForm.getOldSFDT());
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewSourceField&context=cancel#result");
				manageForm.setActionMode("VIEWSF");
				return (mapping.findForward("manageRuleAdmin"));
			}
			if(manageForm.getActionMode().equalsIgnoreCase("VIEWSF")){
				dt.setOperatorTypeUid(new Long((manageForm.getSearchMap()
						.get("opSFType")).toString()));
				dt.setSourceValue(manageForm.getSearchCriteria("SRCSFVAL"));
				
			}else{
			dt.setOperatorTypeUid(new Long((manageForm.getSearchMap()
					.get("opType")).toString()));
			dt.setSourceValue(manageForm.getSearchCriteria("SRCVAL"));
			}
			SourceFieldDT sourceFieldDT = (SourceFieldDT) manageForm.getOldSFDT();
			dt.setSourceFieldUid(sourceFieldDT.getSourceFieldUid());
			srcFieldUid = sourceFieldDT.getSourceFieldUid().toString();
			String srcValueUID = ruleAdminHelper.addSourceValue(dt);

			if (srcValueUID == null || srcValueUID.equalsIgnoreCase("")) {
				
				manageForm.setActionMode("ADDSRCTARS");
				ArrayList<Object> pamList = RuleAdminHelper.getOperatorTypes();
				DropDownCodeDT ddDT = new DropDownCodeDT();
				if (pamList.size() > 0) {
					for (int i = 0; i < pamList.size(); i += 1) {
						ddDT = (DropDownCodeDT) pamList.get(i);
						if (ddDT.getKey().equalsIgnoreCase(manageForm.getSearchMap().get("opType").toString())) {
							dt.setOperatorTypeDesc(ddDT.getValue());
							break;
						}
					}
				}
				manageForm.setSelection(dt);
				request.setAttribute("error",
						"error while adding the new Rule Instance");
			} else {
				
				dt.setSourceValueUid(new Long(srcValueUID));
				if(manageForm.getActionMode().equalsIgnoreCase("VIEWSF")){
					dt.setOperatorTypeUid(new Long((manageForm.getSearchMap()
							.get("opSFType")).toString()));
					dt.setSourceValue(manageForm.getSearchCriteria("SRCSFVAL"));
					ArrayList<Object> opList = RuleAdminHelper.getOperatorTypes();
					DropDownCodeDT ddDT = new DropDownCodeDT();
					if (opList.size() > 0) {
						for (int i = 0; i < opList.size(); i += 1) {
							ddDT = (DropDownCodeDT) opList.get(i);
							if (ddDT.getKey().equalsIgnoreCase(
									(manageForm.getSearchMap().get("opSFType"))
											.toString())) {
								dt.setOperatorTypeDesc(ddDT.getValue());
								break;
							}
						}
					}
					
				}else{
				dt.setOperatorTypeUid(new Long((manageForm.getSearchMap()
						.get("opType")).toString()));
				dt.setSourceValue(manageForm.getSearchCriteria("SRCVAL"));
				ArrayList<Object> opList = RuleAdminHelper.getOperatorTypes();
				DropDownCodeDT ddDT = new DropDownCodeDT();
				if (opList.size() > 0) {
					for (int i = 0; i < opList.size(); i += 1) {
						ddDT = (DropDownCodeDT) opList.get(i);
						if (ddDT.getKey().equalsIgnoreCase(
								(manageForm.getSearchMap().get("opType"))
										.toString())) {
							dt.setOperatorTypeDesc(ddDT.getValue());
							break;
						}
					}
				}
				}
				
				String whereClause = RuleAdminUtil.RuleSVWhereClause(srcFieldUid);
				ArrayList<Object> dtSVList = ruleAdminHelper.findSourceValues(whereClause);
				
				if(dtSVList == null || dtSVList.size() == 0){
            		manageForm.getAttributeMap().put("NORESULTSV", "NORESULTSV");
            	}
				else{
					manageForm.getAttributeMap().remove("NORESULTSV");
				}
                if(manageForm.getActionMode().equalsIgnoreCase("VIEWSF")){
                	if (dtSVList != null && dtSVList.size() > 0) {
    					Iterator<Object>  iter1 = dtSVList.iterator();
    					while (iter1.hasNext()) {
    						SourceValueDT dt1 = (SourceValueDT) iter1.next();
    						
    						RuleAdminUtil.makeSourceValueLink(dt1, EDIT);
    					    RuleAdminUtil.makeSourceValueLink(dt1,ADD);
    					}
    				}
                	
                	manageForm.setManageList(dtSVList);
        			manageForm.setSrcValueList(dtSVList);
        			manageForm.setSelection(dt);
        			request.setAttribute("manageList", manageForm.getManageList());
        			request.setAttribute("manageListold", manageForm.getOldSFDT());
        			request.setAttribute("manageRList", manageForm.getOldDT());
        			
                    
                   	manageForm.setActionMode("VIEWNEWSRCVAL");
                }else{			
				
				manageForm.setOldSVDT(dt);
				manageForm.setSrcValueList(dtSVList);
				

				if(manageForm.getTarValueList()!= null)
				request.setAttribute("manageListTV", manageForm.getTarValueList());
				

				request.setAttribute("manageListSF", manageForm.getSrcFieldList());
				
				request.setAttribute("manageListold", manageForm.getOldDT());
				
	
				if(manageForm.getTarFieldList()!= null)
				request.setAttribute("manageTFList", manageForm.getTarFieldList());
				request.setAttribute("manageListSV", manageForm.getSrcValueList());
				request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
				
				manageForm.setActionMode("ADDSRCTARVALUES");
                }
			}
			manageForm
			.getAttributeMap()
			.put("cancel",
					"/nbs/RuleAdmin.do?method=addSourceValue&context=cancel#result"); 
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageFrmList", manageForm.getFrmCodeList());

		} catch (Exception e) {
			logger.error("Exception in searchRule: " + e.getMessage());
			RuleAdminUtil.handleErrors(e, request, "search", "");
			manageForm.setActionMode("ADDSRCTARS");
			return (mapping.findForward("manageRuleAdmin"));
		} finally {
			request.setAttribute(RuleAdminConstants.PAGE_TITLE,
					RuleAdminConstants.MANAGE_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));

	}
	
	public ActionForward addTargetValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RuleAdminForm manageForm = (RuleAdminForm) form;
		try {
            String srcTargetUid ="";
			TargetValueDT dt = new TargetValueDT();
			RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				request.setAttribute("manageList", manageForm.getTarValueList());
				request.setAttribute("manageListold", manageForm.getOldTFDT());
				request.setAttribute("manageRList", manageForm.getOldDT());
				request.setAttribute("manageTFDT", manageForm.getOldTFDT());
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewTargetField&context=cancel#result");
				manageForm.setActionMode("VIEWTF");
				return (mapping.findForward("manageRuleAdmin"));
			}
			dt.setErrormessageUid(new Long((manageForm.getSearchMap()
					.get("errMessage")).toString()));
			dt.setConseqIndUid(new Long((manageForm.getSearchMap()
					.get("conseqTVInd")).toString()));
			dt.setOperatorTypeUid(new Long((manageForm.getSearchMap()
					.get("opTFType")).toString()));
			dt.setTargetValue(manageForm.getSearchCriteria("TARVAL"));
			TargetFieldDT targetFieldDT = (TargetFieldDT) manageForm.getOldTFDT();
			dt.setTargetFieldUid(targetFieldDT.getTargetFieldUid());
			srcTargetUid = targetFieldDT.getTargetFieldUid().toString();
			String tarValueUID = ruleAdminHelper.addTargetValue(dt);

			if (tarValueUID.equalsIgnoreCase("")) {
				
				manageForm.setActionMode("ADDSRCTARS");
				
				request.setAttribute("error",
						"error while adding the new Rule Instance");
			} else {
				
				dt.setTargetValueUid(new Long(tarValueUID));
				dt.setErrormessageUid(new Long((manageForm.getSearchMap()
						.get("errMessage")).toString()));
				dt.setConseqIndUid(new Long((manageForm.getSearchMap()
						.get("conseqTVInd")).toString()));
				dt.setOperatorTypeUid(new Long((manageForm.getSearchMap()
						.get("opTFType")).toString()));
				dt.setTargetValue(manageForm.getSearchCriteria("TARVAL"));
				ArrayList<Object> errList = RuleAdminHelper.getErrorMessages();
				DropDownCodeDT ddDT = new DropDownCodeDT();
				if (errList.size() > 0) {
					for (int i = 0; i < errList.size(); i += 1) {
						ddDT = (DropDownCodeDT) errList.get(i);
						if (ddDT.getKey().equalsIgnoreCase(
								(manageForm.getSearchMap().get("errMessage"))
										.toString())) {
							dt.setError_cd(ddDT.getValue());
							break;
						}
					}
				}
				ArrayList<Object> conseqList = RuleAdminHelper.getConseqInd();
				DropDownCodeDT ddDT1 = new DropDownCodeDT();
				if (conseqList.size() > 0) {
					for (int i = 0; i < conseqList.size(); i += 1) {
						ddDT1 = (DropDownCodeDT) conseqList.get(i);
						if (ddDT1.getKey().equalsIgnoreCase(
								(manageForm.getSearchMap().get("conseqTVInd"))
										.toString())) {
							dt.setConseqIndtxt(ddDT1.getValue());
							break;
						}
					}
				}
				ArrayList<Object> opList = RuleAdminHelper.getOperatorTypes();
				DropDownCodeDT ddDT2 = new DropDownCodeDT();
				if (opList.size() > 0) {
					for (int i = 0; i < opList.size(); i += 1) {
						ddDT2 = (DropDownCodeDT) opList.get(i);
						if (ddDT2.getKey().equalsIgnoreCase(
								(manageForm.getSearchMap().get("opTFType"))
										.toString())) {
							dt.setOperatorTypeDesc(ddDT2.getValue());
							break;
						}
					}
				}
				
				String whereClause = RuleAdminUtil.RuleTVWhereClause(srcTargetUid);
				ArrayList<Object> dtTVList = ruleAdminHelper.findTargetValues(whereClause);
				if(dtTVList == null || dtTVList.size() == 0){
					manageForm.getAttributeMap().put("NORESULTTV", "NORESULTTV");
				}else{
					manageForm.getAttributeMap().remove("NORESULTTV");
				}
				
				if(manageForm.getActionMode().equalsIgnoreCase("VIEWTF")){
					if (dtTVList != null && dtTVList.size() > 0) {
						Iterator<Object>  iter1 = dtTVList.iterator();
						while (iter1.hasNext()) {
							TargetValueDT dt1 = (TargetValueDT) iter1.next();
							// RuleAdminUtil.makeSourceValueLink(dt,VIEW);
							RuleAdminUtil.makeTargetValueLink(dt1, EDIT);
						    RuleAdminUtil.makeTargetValueLink(dt1,ADD);
						}
					}
					
					manageForm.setManageList(dtTVList);
					manageForm.setTarValueList(dtTVList);
					manageForm.setSelection(dt);
					request.setAttribute("manageList", manageForm.getManageList());
					request.setAttribute("manageListold", manageForm.getOldTFDT());
					request.setAttribute("manageRList", manageForm.getOldDT());
					manageForm.setActionMode("VIEWNEWTARVAL");	
					
				}else{
				
				manageForm.setOldTVDT(dt);
				manageForm.setTarValueList(dtTVList);				
				
				if(manageForm.getSrcValueList()!= null)
				request.setAttribute("manageListSV", manageForm.getSrcValueList());	
				if(manageForm.getSrcFieldList()!= null)
				request.setAttribute("manageListSF", manageForm.getSrcFieldList());
				
				request.setAttribute("manageListold", manageForm.getOldDT());
				
				request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
				 request.setAttribute("manageTFList", manageForm.getTarFieldList());				
				 request.setAttribute("manageListTV", manageForm.getTarValueList());
				manageForm.setActionMode("ADDSRCTARVALUES");
			    }
			
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
			manageForm
			.getAttributeMap()
			.put("cancel",
					"/nbs/RuleAdmin.do?method=addTargetValue&context=cancel#result");
			
			
			}
		} catch (Exception e) {
			logger.error("Exception in addSourceValue: " + e.getMessage());
			e.printStackTrace();
			RuleAdminUtil.handleErrors(e, request, "search", "");
			manageForm.setActionMode("ADDSRCTARS");
			return (mapping.findForward("manageRuleAdmin"));
		} finally {
			request.setAttribute(RuleAdminConstants.PAGE_TITLE,
					RuleAdminConstants.MANAGE_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));

	}
	
	
	


	public ActionForward createSrcTarF(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RuleAdminForm manageForm = (RuleAdminForm) form;
         try{
				request.setAttribute("manageListold", manageForm.getOldDT());
				manageForm.setActionMode("ADDSRCTARS");		
			

		} catch (Exception e) {
			logger.error("Exception in createSrcTarF: " + e.getMessage());
			e.printStackTrace();
			RuleAdminUtil.handleErrors(e, request, "search", "");
			manageForm.setActionMode("ADDRULEINSTANCE");
			return (mapping.findForward("manageRuleAdmin"));
		} finally {
			request.setAttribute(RuleAdminConstants.PAGE_TITLE,
					RuleAdminConstants.MANAGE_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));

	}


	public ActionForward viewRuleIns(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtSFList = new ArrayList<Object> ();
		ArrayList<Object> dtTFList = new ArrayList<Object> ();
		ArrayList<Object> dtFrmCodeList = new ArrayList<Object> ();
		try {
			manageForm.setActionMode("VIEWRULEINSTANCE");
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getRuleInsList());
				manageForm.setManageList(manageForm.getRuleInsList());
				manageForm.setActionMode("VIEWRULES");
				return (mapping.findForward("manageRuleAdmin"));
			}
			String ruleInstanceUid = request.getParameter("ruleInstanceUid");

			if (ruleInstanceUid != null && ruleInstanceUid.length() > 0) {

				
				
				String whereClause = RuleAdminUtil.RuleInsSrchWhereClause(ruleInstanceUid);
				manageForm.getAttributeMap().clear();
				RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();				
				ArrayList<Object> testList = ruleAdminHelper.findRuleInstances(whereClause);
				Iterator<Object>  iter = testList.iterator();
				while (iter.hasNext()) {
					RulesDT dt = (RulesDT) iter.next();
					String strRuleInsUid = dt.getRuleInstanceUid().toString();
					if (strRuleInsUid.equalsIgnoreCase(ruleInstanceUid)) {
						manageForm.setOldDT(dt);
						break;
					}
				}

				 whereClause = RuleAdminUtil
				.RuleSFWhereClause(ruleInstanceUid);
				dtSFList = ruleAdminHelper.findSourceFields(whereClause);

				if (dtSFList!= null && dtSFList.size() > 0) {
					Iterator<Object>  iter1 = dtSFList.iterator();
					while (iter1.hasNext()) {
						SourceFieldDT dt = (SourceFieldDT) iter1.next();
						RuleAdminUtil.makeSourceFieldLink(dt, VIEW);
						RuleAdminUtil.makeSourceFieldLink(dt, DELETE);
						RuleAdminUtil.makeSourceFieldLink(dt,ADD);
					}
				} else {
					manageForm.getAttributeMap().put("NORESULTSF", "NORESULT");
				}

				whereClause = RuleAdminUtil.RuleTFWhereClause(ruleInstanceUid);
				dtTFList = ruleAdminHelper.findTargetFields(whereClause);

				if (dtTFList != null && dtTFList.size() > 0) {
					Iterator<Object>  iter2 = dtTFList.iterator();
					while (iter2.hasNext()) {
						TargetFieldDT dt = (TargetFieldDT) iter2.next();
						RuleAdminUtil.makeTargetFieldLink(dt, VIEW);
						RuleAdminUtil.makeTargetFieldLink(dt, DELETE);
						RuleAdminUtil.makeTargetFieldLink(dt,ADD);
					}
				}		

			else {
				manageForm.getAttributeMap().put("NORESULTTF", "NORESULT");
			  }
			
				dtFrmCodeList = ruleAdminHelper.findfrmCodes(ruleInstanceUid);				
				
			}
			manageForm.setManageList(dtSFList);
			manageForm.setSrcFieldList(dtSFList);
			manageForm.setTarFieldList(dtTFList);
			manageForm.setFrmCodeList(dtFrmCodeList);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldDT());
			request.setAttribute("manageTFList", manageForm.getTarFieldList());
			request.setAttribute("manageFrmList", manageForm.getFrmCodeList());

			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=viewRuleIns&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in viewRuleIns: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}

	public ActionForward viewSourceField(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtSVList = new ArrayList<Object> ();

		try {
			manageForm.setActionMode("VIEWSF");
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getSrcFieldList());
				manageForm.setManageList(manageForm.getSrcFieldList());
				manageForm.setOldDT(manageForm.getOldDT());
				request.setAttribute("manageTFList", manageForm
						.getTarFieldList());
				request.setAttribute("manageListold", manageForm.getOldDT());
				request.setAttribute("manageFrmList",manageForm.getFrmCodeList());
				manageForm.setActionMode("VIEWRULEINSTANCE");
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewRuleIns&context=cancel#result");
				return (mapping.findForward("manageRuleAdmin"));
			}
			String srcFieldUid = request.getParameter("sourceFieldUid");

			if (srcFieldUid != null && srcFieldUid.length() > 0) {

				String whereClause = RuleAdminUtil
						.RuleSVWhereClause(srcFieldUid);
				manageForm.getAttributeMap().clear();
				RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
				ArrayList<Object> testList = manageForm.getManageList();
				Iterator<Object>  iter = testList.iterator();
				while (iter.hasNext()) {
					SourceFieldDT dt = (SourceFieldDT) iter.next();
					String strSrcFieldUid = dt.getSourceFieldUid().toString();
					if (strSrcFieldUid.equalsIgnoreCase(srcFieldUid)) {
						manageForm.setOldSFDT(dt);
						break;
					}
				}

				dtSVList = ruleAdminHelper.findSourceValues(whereClause);

				if (dtSVList != null && dtSVList.size() > 0) {
					Iterator<Object>  iter1 = dtSVList.iterator();
					while (iter1.hasNext()) {
						SourceValueDT dt = (SourceValueDT) iter1.next();
						// RuleAdminUtil.makeSourceValueLink(dt,VIEW);
						RuleAdminUtil.makeSourceValueLink(dt, EDIT);
					    RuleAdminUtil.makeSourceValueLink(dt,ADD);
					}
				}


				else {
					manageForm.getAttributeMap().put("NORESULTSV", "NORESULTSV");
				}
			}

			manageForm.setManageList(dtSVList);
			manageForm.setSrcValueList(dtSVList);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldSFDT());
			request.setAttribute("manageRList", manageForm.getOldDT());
			request.setAttribute("manageSFDT", manageForm.getOldSFDT());
			
			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=viewSourceField&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in viewSourceField: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}
	
		
	public ActionForward deleteSourceField(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtSVList = new ArrayList<Object> ();
        boolean deletestatus = false;
		try {
			manageForm.setActionMode("VIEWSF");
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getSrcFieldList());
				manageForm.setManageList(manageForm.getSrcFieldList());
				manageForm.setOldDT(manageForm.getOldDT());
				request.setAttribute("manageTFList", manageForm
						.getTarFieldList());
				request.setAttribute("manageListold", manageForm.getOldDT());
				request.setAttribute("manageFrmList",manageForm.getFrmCodeList());
				manageForm.setActionMode("VIEWRULEINSTANCE");
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewRuleIns&context=cancel#result");
				return (mapping.findForward("manageRuleAdmin"));
			}
			String srcFieldUid = request.getParameter("sourceFieldUid");

			if (srcFieldUid != null && srcFieldUid.length() > 0) {

				
				RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
				
			  	deletestatus = ruleAdminHelper.deleteSourceField(srcFieldUid); 	
				
				}		

				else {
					manageForm.getAttributeMap().put("NORESULT", "NORESULT");
				}
			
            if(deletestatus){
            	manageForm.setManageList(null);
    			manageForm.setSrcValueList(null);
    			manageForm.setOldSFDT(null);
    			
    			
				manageForm.getAttributeMap().clear();
				RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();				
				
					RulesDT dt = (RulesDT)manageForm.getOldDT();
					String strRuleInsUid = dt.getRuleInstanceUid().toString();
							
				 String whereClause = RuleAdminUtil
				.RuleSFWhereClause(strRuleInsUid);
				ArrayList<Object> dtSFList = ruleAdminHelper.findSourceFields(whereClause);
				if (dtSFList.size() > 0) {
					Iterator<Object>  iter1 = dtSFList.iterator();
					while (iter1.hasNext()) {
						SourceFieldDT dt1 = (SourceFieldDT) iter1.next();
						RuleAdminUtil.makeSourceFieldLink(dt1, VIEW);
						RuleAdminUtil.makeSourceFieldLink(dt1, DELETE);
						RuleAdminUtil.makeSourceFieldLink(dt1,ADD);
					}
				} else {
					manageForm.getAttributeMap().put("NORESULTSF", "NORESULT");
				}
				manageForm.setSrcFieldList(dtSFList);
    			request.setAttribute("manageList", manageForm.getManageList());
    			request.setAttribute("manageListold", manageForm.getOldSFDT());
    			request.setAttribute("manageRList", manageForm.getOldDT());
            	
            }else{
			manageForm.setManageList(dtSVList);
			manageForm.setSrcValueList(dtSVList);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldSFDT());
			request.setAttribute("manageRList", manageForm.getOldDT());
			
            }
            request.setAttribute("manageSFDT", manageForm.getOldSFDT());
            
			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=deleteSourceField&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in deleteSourceField: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}


	public ActionForward viewTargetField(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtTVList = new ArrayList<Object> ();
		try {
			manageForm.setActionMode("VIEWTF");
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getTarFieldList());
				manageForm.setManageList(manageForm.getSrcFieldList());
				manageForm.setOldDT(manageForm.getOldDT());

				manageForm.setActionMode("VIEWRULEINSTANCE");
				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageTFList", manageForm
						.getTarFieldList());
				request.setAttribute("manageListold", manageForm.getOldDT());
				request.setAttribute("manageFrmList",manageForm.getFrmCodeList());
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewRuleIns&context=cancel#result");
				return (mapping.findForward("manageRuleAdmin"));
			}
			String srcTargetUid = request.getParameter("targetFieldUid");

			if (srcTargetUid != null && srcTargetUid.length() > 0) {

				String whereClause = RuleAdminUtil
						.RuleTVWhereClause(srcTargetUid);
				manageForm.getAttributeMap().clear();
				RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
				ArrayList<Object> testList = manageForm.getTarFieldList();
				Iterator<Object>  iter = testList.iterator();
				while (iter.hasNext()) {
					TargetFieldDT dt = (TargetFieldDT) iter.next();
					String strTarFieldUid = dt.getTargetFieldUid().toString();
					if (strTarFieldUid.equalsIgnoreCase(srcTargetUid)) {
						manageForm.setOldTFDT(dt);
						break;
					}
				}
				
				dtTVList = ruleAdminHelper.findTargetValues(whereClause);

				if (dtTVList != null && dtTVList.size() > 0) {
					Iterator<Object>  iter1 = dtTVList.iterator();
					while (iter1.hasNext()) {
						TargetValueDT dt = (TargetValueDT) iter1.next();
						// RuleAdminUtil.makeSourceValueLink(dt,VIEW);
						RuleAdminUtil.makeTargetValueLink(dt, EDIT);
					    RuleAdminUtil.makeTargetValueLink(dt,ADD);
					}
				}
			

				else {
					manageForm.getAttributeMap().put("NORESULTTV", "NORESULTTV");
				}
			}
			manageForm.setManageList(dtTVList);
			manageForm.setTarValueList(dtTVList);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldTFDT());
			request.setAttribute("manageRList", manageForm.getOldDT());
			request.setAttribute("manageTFDT", manageForm.getOldTFDT());
			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=viewTargetField&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in viewTargetField: " + e.getMessage());
			e.printStackTrace();
		} finally {

			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}
	
	public ActionForward deleteTargetField(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtTVList = new ArrayList<Object> ();
		boolean updatestatus= false;
		try {
			manageForm.setActionMode("VIEWTF");
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getTarFieldList());
				manageForm.setManageList(manageForm.getSrcFieldList());
				manageForm.setOldDT(manageForm.getOldDT());

				manageForm.setActionMode("VIEWRULEINSTANCE");
				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageTFList", manageForm
						.getTarFieldList());
				request.setAttribute("manageListold", manageForm.getOldDT());
				request.setAttribute("manageFrmList",manageForm.getFrmCodeList());
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewRuleIns&context=cancel#result");
				return (mapping.findForward("manageRuleAdmin"));
			}
			String srcTargetUid = request.getParameter("targetFieldUid");

			if (srcTargetUid != null && srcTargetUid.length() > 0) {
				
				manageForm.getAttributeMap().clear();
				RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
					
				updatestatus = ruleAdminHelper.deleteTargetField(srcTargetUid);				

			   }else {
					manageForm.getAttributeMap().put("NORESULT", "NORESULT");
					
				}
			if(updatestatus){
				manageForm.setManageList(null);
				manageForm.setTarValueList(null);
				manageForm.setOldTFDT(null);
				RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();			
				RulesDT dt = (RulesDT)manageForm.getOldDT();
				String strRuleInsUid = dt.getRuleInstanceUid().toString();
				String whereClause = RuleAdminUtil.RuleTFWhereClause(strRuleInsUid);
				ArrayList<Object> dtTFList = ruleAdminHelper.findTargetFields(whereClause);
				if (dtTFList.size() > 0) {
					Iterator<Object>  iter2 = dtTFList.iterator();
					while (iter2.hasNext()) {
						TargetFieldDT dt2 = (TargetFieldDT) iter2.next();
						RuleAdminUtil.makeTargetFieldLink(dt2, VIEW);
						RuleAdminUtil.makeTargetFieldLink(dt2, DELETE);
						RuleAdminUtil.makeTargetFieldLink(dt2,ADD);
					}
				}		
				manageForm.setTarFieldList(dtTFList);
				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageListold", manageForm.getOldTFDT());
				request.setAttribute("manageRList", manageForm.getOldDT());
				
			}else{
				manageForm.setManageList(dtTVList);
				manageForm.setTarValueList(dtTVList);
				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageListold", manageForm.getOldTFDT());
				request.setAttribute("manageRList", manageForm.getOldDT());
			}
			request.setAttribute("manageTFDT", manageForm.getOldTFDT());
			

			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=deleteTargetField&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in deleteTargetField: " + e.getMessage());
			e.printStackTrace();
		} finally {

			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}

	
	public ActionForward editSourceValue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtSVList = new ArrayList<Object> ();

		try {
			manageForm.setActionMode("EDITSV");
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				
				request.setAttribute("manageList", manageForm.getSrcValueList());
				request.setAttribute("manageListold", manageForm.getOldSFDT());
				request.setAttribute("manageRList", manageForm.getOldDT());
				request.setAttribute("manageSFDT", manageForm.getOldSFDT());
				manageForm.setActionMode("VIEWSF");
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewSourceField&context=cancel#result");
				return (mapping.findForward("manageRuleAdmin"));
			}
			String srcValueUid = request.getParameter("sourceValueUid");

			if (srcValueUid != null && srcValueUid.length() > 0) {

				manageForm.getAttributeMap().clear();
				
				ArrayList<Object> testList = manageForm.getManageList();
				Iterator<Object>  iter = testList.iterator();
				while (iter.hasNext()) {
					SourceValueDT dt = (SourceValueDT) iter.next();
					String strSrcValueUid = dt.getSourceValueUid().toString();
					if (strSrcValueUid.equalsIgnoreCase(srcValueUid)) {
						manageForm.setOldSVDT(dt);
						manageForm.setSelection(dt);
						break;
					}
				}			

			}
			
			dtSVList.add(manageForm.getOldSVDT());			
			manageForm.setManageList(dtSVList);
			request.setAttribute("manageSVList", manageForm.getSrcValueList());
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldSFDT());
			request.setAttribute("manageRList", manageForm.getOldDT());
		
			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=editSourceValue&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in editSourceValue: " + e.getMessage());
			e.printStackTrace();
		} finally {
			
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}
	
	public ActionForward updateSourceValue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtSVList = new ArrayList<Object> ();
		boolean updatestatus = false;

		try {
			manageForm.setActionMode("VIEWUPDATEDSV");
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				
				request.setAttribute("manageList", manageForm.getSrcValueList());
				request.setAttribute("manageListold", manageForm.getOldSFDT());
				request.setAttribute("manageRList", manageForm.getOldDT());
				request.setAttribute("manageSFDT", manageForm.getOldSFDT());
				manageForm.setActionMode("VIEWSF");
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewSourceField&context=cancel#result");
				return (mapping.findForward("manageRuleAdmin"));
			}
			
			SourceValueDT dt = (SourceValueDT) manageForm.getSelection();
			RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
			updatestatus = ruleAdminHelper.updateSourceValue(dt);	
			if(updatestatus){
				manageForm.setSelection(dt);
			    manageForm.setOldSVDT(dt);
			    dtSVList.add(manageForm.getOldSVDT());
			    manageForm.setManageList(dtSVList);
			    dtSVList = new ArrayList<Object> ();
			    String whereClause = RuleAdminUtil.RuleSVWhereClause(dt.getSourceFieldUid().toString());				
					dtSVList = ruleAdminHelper.findSourceValues(whereClause);
					if (dtSVList != null && dtSVList.size() > 0) {
    					Iterator<Object>  iter1 = dtSVList.iterator();
    					while (iter1.hasNext()) {
    						SourceValueDT dt1 = (SourceValueDT) iter1.next();
    						// RuleAdminUtil.makeSourceValueLink(dt,VIEW);
    						RuleAdminUtil.makeSourceValueLink(dt1, EDIT);
    					    RuleAdminUtil.makeSourceValueLink(dt1,ADD);
    					}
    				}
					manageForm.setSrcValueList(dtSVList);						    
			}
			
			request.setAttribute("manageSVList", manageForm.getSrcValueList());
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldSFDT());
			request.setAttribute("manageRList", manageForm.getOldDT());

			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=updateSourceValue&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in updSourceValue: " + e.getMessage());
			e.printStackTrace();
		} finally {
			
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}
	
	public ActionForward editTargetValue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtTVList = new ArrayList<Object> ();

		try {
			manageForm.setActionMode("EDITTV");
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				
				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageListold", manageForm.getOldTFDT());
				request.setAttribute("manageRList", manageForm.getOldDT());
				request.setAttribute("manageTFDT", manageForm.getOldTFDT());
				manageForm.setActionMode("VIEWTF");
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewTargetField&context=cancel#result");
				return (mapping.findForward("manageRuleAdmin"));
			}
			String tarValueUid = request.getParameter("targetValueUid");

			if (tarValueUid != null && tarValueUid.length() > 0) {

				manageForm.getAttributeMap().clear();
				
				ArrayList<Object> testList = manageForm.getManageList();
				Iterator<Object>  iter = testList.iterator();
				while (iter.hasNext()) {
					TargetValueDT dt = (TargetValueDT) iter.next();
					String strTarValueUid = dt.getTargetValueUid().toString();
					if (strTarValueUid.equalsIgnoreCase(tarValueUid)) {
						manageForm.setOldTVDT(dt);
						manageForm.setSelection(dt);
						break;
					}
				}			

			}
			
			dtTVList.add(manageForm.getOldTVDT());			
			request.setAttribute("manageTVList", manageForm.getTarValueList());
			request.setAttribute("manageList", manageForm.getManageList());			
			request.setAttribute("manageListold", manageForm.getOldTFDT());
			request.setAttribute("manageRList", manageForm.getOldDT());

			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=editTargetValue&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in editTargetValue: " + e.getMessage());
			e.printStackTrace();
		} finally {
			
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}
	
	public ActionForward updateTargetValue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtTVList = new ArrayList<Object> ();
		boolean updatestatus = false;

		try {
			manageForm.setActionMode("VIEWUPDATEDTV");
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {	
				
				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageListold", manageForm.getOldTFDT());
				request.setAttribute("manageRList", manageForm.getOldDT());
				request.setAttribute("manageTFDT", manageForm.getOldTFDT());
				manageForm.setActionMode("EDITTV");
				
				manageForm
						.getAttributeMap()
						.put("cancel",
								"/nbs/RuleAdmin.do?method=viewTargetField&context=cancel#result");
				return (mapping.findForward("manageRuleAdmin"));
			}
			
			TargetValueDT dt = (TargetValueDT) manageForm.getSelection();
			RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
			updatestatus = ruleAdminHelper.updateTargetValue(dt);
			if(updatestatus){
				ArrayList<Object> errList = new ArrayList<Object> ();
				errList = RuleAdminHelper.getErrorMessages();				
				DropDownCodeDT ddDT = new DropDownCodeDT();
				if (errList.size() > 0) {
					for (int i = 0; i < errList.size(); i += 1) {
						ddDT = (DropDownCodeDT) errList.get(i);
						if (ddDT.getKey().equalsIgnoreCase(
								dt.getErrormessageUid().toString())) {
							dt.setError_cd(ddDT.getValue());
							break;
						}
					}
				}
				ArrayList<Object> conseqList = RuleAdminHelper.getConseqInd();
				DropDownCodeDT ddDT1 = new DropDownCodeDT();
				if (conseqList.size() > 0) {
					for (int i = 0; i < conseqList.size(); i += 1) {
						ddDT1 = (DropDownCodeDT) conseqList.get(i);
						if (ddDT1.getKey().equalsIgnoreCase(
								dt.getConseqIndUid().toString())) {
							dt.setConseqIndtxt(ddDT1.getValue());
							break;
						}
					}
				}
				manageForm.setSelection(dt);
			    manageForm.setOldTVDT(dt);
			    dtTVList.add(manageForm.getOldTVDT());
			    manageForm.setManageList(dtTVList);
			    dtTVList = new ArrayList<Object> ();
			    String whereClause = RuleAdminUtil.RuleTVWhereClause(dt.getTargetFieldUid().toString());				
			    dtTVList = ruleAdminHelper.findTargetValues(whereClause);
			    if (dtTVList != null && dtTVList.size() > 0) {
					Iterator<Object>  iter1 = dtTVList.iterator();
					while (iter1.hasNext()) {
						TargetValueDT dt1 = (TargetValueDT) iter1.next();
						// RuleAdminUtil.makeSourceValueLink(dt,VIEW);
						RuleAdminUtil.makeTargetValueLink(dt1, EDIT);
					    RuleAdminUtil.makeTargetValueLink(dt1,ADD);
					}
				}
					manageForm.setTarValueList(dtTVList);						    
			}
						
			request.setAttribute("manageTVList", manageForm.getTarValueList());
			request.setAttribute("manageList", manageForm.getManageList());			
			request.setAttribute("manageListold", manageForm.getOldTFDT());
			request.setAttribute("manageRList", manageForm.getOldDT());

			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=updateTargetValue&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in updateTargetValue: " + e.getMessage());
			e.printStackTrace();
		} finally {
			
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}


	public ActionForward addFormCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtSFList = new ArrayList<Object> ();
		ArrayList<Object> dtTFList = new ArrayList<Object> ();
		RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
		try {
			  
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				if(manageForm.getActionMode().equalsIgnoreCase("ADDSRCTARFORMCODE")){
				    if(manageForm.getTarValueList()!= null)
					request.setAttribute("manageListTV", manageForm.getTarValueList());					
				    if(manageForm.getSrcFieldList()!= null)	
					request.setAttribute("manageListSF", manageForm.getSrcFieldList());					
					request.setAttribute("manageListold", manageForm.getOldDT());					
					if(manageForm.getTarFieldList()!= null)
					request.setAttribute("manageTFList", manageForm.getTarFieldList());
					if(manageForm.getSrcValueList()!= null)
					request.setAttribute("manageListSV", manageForm.getSrcValueList());
					manageForm.setActionMode("ADDSRCTARVALUES");					
					request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
				
				}else{
				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageListold", manageForm.getOldDT());
				request.setAttribute("manageTFList", manageForm.getTarFieldList());
				request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
				manageForm.setActionMode("VIEWRULEINSTANCE");
				}
				return (mapping.findForward("manageRuleAdmin"));
			}
							
			dtSFList =  manageForm.getSrcFieldList();		
			dtTFList =  manageForm.getTarFieldList();	
			Collection<Object>  pamQuestions = new ArrayList<Object> ();
			boolean addtocollection = true;
			if (dtSFList != null && dtSFList.size() > 0) {
				Iterator<Object>  iter = dtSFList.iterator();
				while (iter.hasNext()) {
					SourceFieldDT dt = (SourceFieldDT)iter.next();
					pamQuestions.add(dt.getQuestionIdentifier());
				}
					
				}
			
			if (dtTFList != null && dtTFList.size() > 0) {
				Iterator<Object>  iter = dtTFList.iterator();
				while (iter.hasNext()) {
					TargetFieldDT dt = (TargetFieldDT)iter.next();
					Iterator<Object>  iterator = pamQuestions.iterator();
					while (iterator.hasNext()) {
					  Object element = iterator.next();
					  if (dt.getQuestionIdentifier().equals(element)) {
						  addtocollection = false;						 
					  }
					}
					if(addtocollection)
					pamQuestions.add(dt.getQuestionIdentifier());
				}
					
				}
			
			ArrayList<Object> formList = new ArrayList<Object> ();
			
			formList = ruleAdminHelper.getFormCode(pamQuestions);
			ArrayList<Object> formcodelist = new ArrayList<Object> ();
			if (formList != null && formList.size() > 0) {
				Iterator<Object>  iter = formList.iterator();
				while (iter.hasNext()) {					
					formcodelist.add(iter.next().toString());
				}
			}		
			if(manageForm.getActionMode().equalsIgnoreCase("ADDSRCTARVALUES")){
				    if(manageForm.getTarValueList()!= null)
					request.setAttribute("manageListTV", manageForm.getTarValueList());					
				    if(manageForm.getSrcFieldList()!= null)	
					request.setAttribute("manageListSF", manageForm.getSrcFieldList());					
					request.setAttribute("manageListold", manageForm.getOldDT());					
					if(manageForm.getTarFieldList()!= null)
					request.setAttribute("manageTFList", manageForm.getTarFieldList());
					if(manageForm.getSrcValueList()!= null)
					request.setAttribute("manageListSV", manageForm.getSrcValueList());
					manageForm.setActionMode("ADDSRCTARFORMCODE");
					request.setAttribute("formcodeList", formcodelist);
					request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
				
			}else{
			manageForm.setActionMode("ADDFORMCODE");
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldDT());
			request.setAttribute("manageTFList", manageForm.getTarFieldList());
			request.setAttribute("formcodeList", formcodelist);
			request.setAttribute("manageFrmList", manageForm.getFrmCodeList());
			}
			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=addFormCode&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in addFormCode: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}
	
	public ActionForward associateFormCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
		int c=0;
		
		ArrayList<Object> rulesDTList = new ArrayList<Object> ();
		try {
			
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				request.setAttribute("manageList", manageForm.getManageList());
				request.setAttribute("manageListold", manageForm.getOldDT());
				request.setAttribute("manageTFList", manageForm.getTarFieldList());
				request.setAttribute("manageFrmList",manageForm.getFrmCodeList());
				manageForm.setActionMode("VIEWRULEINSTANCE");
				return (mapping.findForward("manageRuleAdmin"));
			}
			RulesDT rulesDT = (RulesDT) manageForm.getOldDT();			
			
			String[] frmcodes=request.getParameterValues("cmbFrmCode");
			  if (frmcodes != null)
			   {
			      for (int p = 0; p < frmcodes.length; ++p)
			       {
			         String testParam = frmcodes[p];
			         RulesDT Rulesdt = new RulesDT();
			         Rulesdt.setRuleInstanceUid(rulesDT.getRuleInstanceUid());
			         Rulesdt.setFormCode(testParam);
			         rulesDTList.add(Rulesdt);			        
			       }
			   }
			  else{
					request.setAttribute("error","Please select a Form Code to continue.");	
					if(manageForm.getActionMode().equalsIgnoreCase("ADDSRCTARFORMCODE")){
						
					}else{
					manageForm.setActionMode("VIEWRULEINSTANCE");
					}
				}
			if(rulesDTList != null && rulesDTList.size()>0) { 
		  	boolean associatestatus = 	ruleAdminHelper.associateFormCode(rulesDTList);	
			if(!associatestatus){
				request.setAttribute("error","Error while associating the form code.");	
				rulesDTList =null;
			}			
			manageForm.setFrmCodeList(rulesDTList);	
			}
			if(manageForm.getActionMode().equalsIgnoreCase("ADDSRCTARFORMCODE")){
			    if(manageForm.getTarValueList()!= null)
				request.setAttribute("manageListTV", manageForm.getTarValueList());					
			    if(manageForm.getSrcFieldList()!= null)	
				request.setAttribute("manageListSF", manageForm.getSrcFieldList());					
				request.setAttribute("manageListold", manageForm.getOldDT());					
				if(manageForm.getTarFieldList()!= null)
				request.setAttribute("manageTFList", manageForm.getTarFieldList());
				if(manageForm.getSrcValueList()!= null)
				request.setAttribute("manageListSV", manageForm.getSrcValueList());
				manageForm.setActionMode("VIEWSRCTARFORMCODE");	
				request.setAttribute("manageFrmList",manageForm.getFrmCodeList());
				request.setAttribute("frmCodes", rulesDTList);	
			}else{
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldDT());
			request.setAttribute("manageTFList", manageForm.getTarFieldList());
			request.setAttribute("manageFrmList",manageForm.getFrmCodeList());
			request.setAttribute("frmCodes", rulesDTList);
			manageForm.setActionMode("VIEWFORMCODE");
			}
			
			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=addFormCode&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in assocFormCode: " + e.getMessage());
			e.printStackTrace();

		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}



	public ActionForward deleteRuleIns(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		try {
			manageForm.setActionMode("VIEWRULEINSTANCE");
			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getRuleInsList());
				manageForm.setManageList(manageForm.getRuleInsList());
				request.setAttribute("manageFrmList",manageForm.getFrmCodeList());
				manageForm.setActionMode("VIEWRULEINSTANCE");
				return (mapping.findForward("manageRuleAdmin"));
			}
			String ruleInstanceUid = request.getParameter("ruleInstanceUid");

			if (ruleInstanceUid != null && ruleInstanceUid.length() > 0) {

				RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
				String status = ruleAdminHelper.deleteRuleIns(ruleInstanceUid);
				ArrayList<Object> testList = manageForm.getRuleInsList();

				if (status.equalsIgnoreCase("deletesuccess")) {

					ArrayList<Object> newList = new ArrayList<Object> ();
					Iterator<Object>  iter = testList.iterator();
					while (iter.hasNext()) {
						RulesDT dt = (RulesDT) iter.next();
						String strRuleInsUid = dt.getRuleInstanceUid()
								.toString();
						if (strRuleInsUid.equalsIgnoreCase(ruleInstanceUid)) {
							continue;
						} else {
							newList.add(dt);
						}
					}
					manageForm.setManageList(newList);
					manageForm.setRuleInsList(newList);
				} else {

					manageForm.setManageList(testList);
					manageForm.setRuleInsList(testList);
					request.setAttribute("error", status);

				}
				request.setAttribute("manageFrmList",manageForm.getFrmCodeList());

			}

		} catch (Exception e) {
			logger.error("Exception in delRuleIns: " + e.getMessage());
			e.printStackTrace();
		} finally {
			manageForm.setActionMode("VIEWRULES");
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}

	public ActionForward previewRuleIns(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		PamLoadUtil pamLoadUtil = new PamLoadUtil();
		
		ArrayList<Object> dtSFList = new ArrayList<Object> ();
		ArrayList<Object> dtTFList = new ArrayList<Object> ();
		String strRuleName = "";
		try {
			manageForm.setActionMode("PREVIEW");
			String ruleInstanceUid = request.getParameter("ruleInstanceUid");

			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getRuleInsList());
				manageForm.setManageList(manageForm.getRuleInsList());
				manageForm.setActionMode("VIEWRULES");
				return (mapping.findForward("manageRuleAdmin"));
			}

	

			if (ruleInstanceUid != null && ruleInstanceUid.length() > 0) {

				String whereClause = RuleAdminUtil.RuleInsSrchWhereClause(ruleInstanceUid);
				manageForm.getAttributeMap().clear();
				RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
				
				ArrayList<Object> dtList = ruleAdminHelper.findRuleInstances(whereClause);
				if (dtList.size() > 0) {
					Iterator<Object>  iter = dtList.iterator();
					while (iter.hasNext()) {
						RulesDT dt = (RulesDT) iter.next();
					String strRuleInsUid = dt.getRuleInstanceUid().toString();
					if (strRuleInsUid.equalsIgnoreCase(ruleInstanceUid)) {
						manageForm.setOldDT(dt);
						strRuleName = dt.getRuleName();
						break;
					}
				   }
				}
                
				 whereClause = RuleAdminUtil
				.RuleSFWhereClause(ruleInstanceUid);
				dtSFList = ruleAdminHelper.findSourceFields(whereClause);
				StringBuffer buf1 = new StringBuffer("");

				buf1
						.append("<INPUT TYPE=\"hidden\" NAME=\"hdnRuleInsId\" VALUE=\"");
				buf1.append(ruleInstanceUid);
				buf1.append("\">");
				buf1.append("<fieldset id=\"srcFlds\"> ");
				buf1
						.append("<legend class=\"boldTenDkBlue\">Source  Field List<Object> </legend>");
				buf1.append(TABLE_START);

				String invFormCd = "INV_FORM_RVCT";
				Map<?,?> questionMap = (Map<?,?>) QuestionsCache.getQuestionMap().get(
						invFormCd);
				
				manageForm.setPamFormCd(invFormCd);
				int j = 0;
				if (dtSFList.size() > 0) {
					Iterator<Object>  iter1 = dtSFList.iterator();
					while (iter1.hasNext()) {
						SourceFieldDT dt = (SourceFieldDT) iter1.next();

						NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
								.get(dt.getQuestionIdentifier());
						String datatype = metaData.getDataType();
						String label = metaData.getQuestionLabel();
						buf1.append(TR_START);
						buf1.append(TD_ALIGN_LEFT);

						buf1.append(label);
						buf1.append(TD_END);
						buf1.append(TD_ALIGN_LEFT);
						if (datatype.equalsIgnoreCase("Coded")) {
							ArrayList<?> aList = (ArrayList<?> ) CachedDropDowns
									.getCodedValueForType(
											metaData.getCodeSetNm()).clone();
							DropDownCodeDT ddDT = new DropDownCodeDT();

							// buf1.append("<SELECT NAME= \"cmbSrcRules\" ");
							buf1.append("<SELECT NAME= \"");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("\"");
							buf1.append(" onchange=\"previewRule(");
							buf1.append("'");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("'");
							buf1.append(")\">");

							int intSize1 = aList.size();
							if (intSize1 > 0) {
								for (int i = 0; i < intSize1; i += 1) {
									ddDT = (DropDownCodeDT) aList.get(i);
									buf1.append("<OPTION VALUE=");
									buf1.append(ddDT.getKey());
									buf1.append(">");
									buf1.append(ddDT.getValue());
									buf1.append(" </OPTION>");
								}
							}
							buf1.append("</SELECT>");

						}
						if (datatype.equalsIgnoreCase("Date")) {

							// buf1.append("<input type =\"text\"
							// name=\"dateSrcRules\" ");
							buf1.append("<input type =\"text\"  name=\"");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("\" ");buf1.append(" id=\"");
							buf1.append(dt.getQuestionIdentifier());buf1.append("\"");

							buf1
									.append(" title=\"Enter a Date\" onkeyup=\"DateMask(this,null,event)\" size=\"10\">");
							buf1
									.append("<img src=\"calendar.gif\" id=\"");
							buf1.append(dt.getQuestionIdentifier());						
							buf1.append("Icon\"");
							buf1.append("onclick=\"getCalDate('");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("','");buf1.append(dt.getQuestionIdentifier());						
							buf1.append("Icon'); return false;");

							buf1.append(" onkeypress =\"showCalendarEnterKey('");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("','");buf1.append(dt.getQuestionIdentifier());						
							buf1.append("Icon',event);\">");
							
						}
						if (datatype.equalsIgnoreCase("Numeric")
								|| datatype.equalsIgnoreCase("Text")) {

							// buf1.append("<input type =\"text\"
							// name=\"numSrcRules\" ");
							buf1.append("<input type =\"text\"  name=\"");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("\" id=\"");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("\"");
							buf1.append(" onchange=\"previewTextRule(");
							buf1.append("'");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("'");
							buf1.append(")\"");
							buf1.append(" size=\"10\">");
							
							

						}

						// if(datatype.equalsIgnoreCase("MultiSelect") ){
						if (dt.getQuestionIdentifier().toString()
								.equalsIgnoreCase("TUB106")) {

							ArrayList<?> aList = (ArrayList<?> ) CachedDropDowns
									.getCodedValueForType(
											metaData.getCodeSetNm()).clone();
							DropDownCodeDT ddDT = new DropDownCodeDT();

							// buf1.append("<SELECT NAME= \"cmbSrcRules\" ");
							buf1.append("<SELECT NAME= \"");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("\"");
							buf1.append(" onblur=\"previewMultiSelectRule(");
							buf1.append("this.form.");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append(".options,");
							buf1.append("'");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("'");
							buf1.append(")\" id=\"mulSrcF\" MULTIPLE>");

							int intSize1 = aList.size();
							if (intSize1 > 0) {
								for (int i = 0; i < intSize1; i += 1) {
									ddDT = (DropDownCodeDT) aList.get(i);
									buf1.append("<OPTION VALUE=");
									buf1.append(ddDT.getKey());
									buf1.append(">");
									buf1.append(ddDT.getValue());
									buf1.append(" </OPTION>");
								}
							}
							buf1.append("</SELECT>");

						}
						buf1.append(TD_END);
						buf1.append(TR_END);

					}
				} else {
					buf1.append(TR_START);
					buf1.append(TD_ALIGN_LEFT);
					buf1
							.append("No Source Field exists for the selected rule instance");
					buf1.append(TD_END);
					buf1.append(TR_END);
				}
				buf1.append(TR_START);
				buf1.append(TD_ALIGN_LEFT);
				buf1.append("&nbsp;");
				buf1.append(TD_END);
				buf1.append(TR_END);
				buf1.append(TABLE_END);
				buf1.append("</fieldset>");
				buf1.append("<fieldset> ");
				buf1
						.append("<legend class=\"boldTenDkBlue\">Target  Field List<Object> </legend>");
				buf1.append(TABLE_START);
				whereClause = RuleAdminUtil.RuleTFWhereClause(ruleInstanceUid);
				dtTFList = ruleAdminHelper.findTargetFields(whereClause);

				if (dtTFList.size() > 0) {
					Iterator<Object>  iter2 = dtTFList.iterator();
					while (iter2.hasNext()) {
						TargetFieldDT dt = (TargetFieldDT) iter2.next();
						NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
								.get(dt.getQuestionIdentifier());
						String datatype = metaData.getDataType();
						String label = metaData.getQuestionLabel();
						buf1.append(TR_START);
						buf1.append(TD_ALIGN_LEFT);
						buf1.append(label);
						buf1.append(TD_END);
						buf1.append(TD_ALIGN_LEFT);
						if (datatype.equalsIgnoreCase("Coded")) {
							ArrayList<?> aList = (ArrayList<?> ) CachedDropDowns
									.getCodedValueForType(
											metaData.getCodeSetNm()).clone();

							DropDownCodeDT ddDT = new DropDownCodeDT();

							buf1.append("<SELECT NAME= \"cmbTarRules\" ");
							if (strRuleName.equalsIgnoreCase("enable")
									|| strRuleName.equalsIgnoreCase("disable"))
								buf1.append("disabled");
							buf1.append(" >");
							int intSize1 = aList.size();
							if (intSize1 > 0) {
								for (int i = 0; i < intSize1; i += 1) {
									ddDT = (DropDownCodeDT) aList.get(i);
									buf1.append("<OPTION VALUE=");
									buf1.append(ddDT.getKey());
									buf1.append(">");
									buf1.append(ddDT.getValue());
									buf1.append(" </OPTION>");
								}
							}
							buf1.append("</SELECT>");

						}

						if (datatype.equalsIgnoreCase("Date")) {

							buf1
									.append("<input type =\"text\"  name=\"dateTarRules\" disabled id=\"");
							buf1.append(dt.getQuestionIdentifier());buf1.append("\"");
							buf1
									.append(" title=\"Enter a Date\" onkeyup=\"DateMask(this,null,event)\" size=\"10\">");
							buf1
									.append("<img src=\"calendar.gif\" id=\"");
							buf1.append(dt.getQuestionIdentifier());						
							buf1.append("Icon\"");
							buf1.append("onclick=\"getCalDate('");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("','");buf1.append(dt.getQuestionIdentifier());						
							buf1.append("Icon'); return false;");

							buf1.append(" onkeypress =\"showCalendarEnterKey('");
							buf1.append(dt.getQuestionIdentifier());
							buf1.append("','");buf1.append(dt.getQuestionIdentifier());						
							buf1.append("Icon',event);\">");
							
						}
						if (datatype.equalsIgnoreCase("Numeric")
								|| datatype.equalsIgnoreCase("Text")) {
							if (strRuleName.equalsIgnoreCase("enable")
									|| strRuleName.equalsIgnoreCase("disable"))
							buf1.append("<input type =\"text\"  name=\"numSrcRules\" disabled ");
							else
								buf1.append("<input type =\"text\"  name=\"numSrcRules\"  ");
							buf1.append(" size=\"10\">");
							
						}
						if (datatype.equalsIgnoreCase("Boolean")) {
							ArrayList<?> aList = (ArrayList<?> ) CachedDropDowns
									.getCodedValueForType(
											metaData.getCodeSetNm()).clone();

							DropDownCodeDT ddDT = new DropDownCodeDT();

							buf1.append("<SELECT NAME= \"bolTarRules\" ");

							if (strRuleName.equalsIgnoreCase("enable")
									|| strRuleName.equalsIgnoreCase("disable"))
								buf1.append("disabled");

							buf1.append(" >");

							int intSize1 = aList.size();
							if (intSize1 > 0) {
								for (int i = 0; i < intSize1; i += 1) {
									ddDT = (DropDownCodeDT) aList.get(i);
									buf1.append("<OPTION VALUE=");
									buf1.append(ddDT.getKey());
									buf1.append(">");
									buf1.append(ddDT.getValue());
									buf1.append(" </OPTION>");
								}
							}
							buf1.append("</SELECT>");

						}

						buf1.append(TD_END);
						buf1.append(TR_END);

					}
				}

				else {
					buf1.append(TR_START);
					buf1.append(TD_ALIGN_LEFT);
					buf1
							.append("No Target Field exists for the selected rule instance");
					buf1.append(TD_END);
					buf1.append(TR_END);
				}
				buf1.append(TR_START);
				buf1.append(TD_ALIGN_LEFT);
				buf1.append("&nbsp;");
				buf1.append(TD_END);
				buf1.append(TR_END);

				buf1.append(TABLE_END);
				buf1.append("</fieldset>");
				buf1.append(TABLE_START);
				buf1.append(TR_START);
				buf1.append(TD_ALIGN_RIGHT);
				buf1
						.append("<input type=\"button\" name=\"submit6\" id=\"submit6\" value=\"Cancel\" onClick=\"return cancelPreview();\"/>");
				buf1.append(TD_END);
				buf1.append(TR_END);
				buf1.append(TABLE_END);
				request.setAttribute("previewR", buf1.toString());

			}

			manageForm.setManageList(dtSFList);
			manageForm.setSrcFieldList(dtSFList);
			manageForm.setTarFieldList(dtTFList);

			try {
				pamLoadUtil.viewRuleAdminLoadUtil(manageForm, request);
			} catch (Exception e) {
				logger.error("Error while loading PAM Case: " + e.toString());
				throw new ServletException("Error while loading PAM Case: "+e.getMessage(),e);
			}

			manageForm.setActionMode("PREVIEW");
			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=viewRuleIns&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in previewRule: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageFormMap", manageForm.getFormFieldMap());
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldDT());
			request.setAttribute("manageTFList", manageForm.getTarFieldList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}

	public ActionForward firepreviewRule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RuleAdminForm manageForm = (RuleAdminForm) form;
		ArrayList<Object> dtSFList = new ArrayList<Object> ();
		ArrayList<Object> dtTFList = new ArrayList<Object> ();
		String srcValue = "";
		String strRuleName = "";
		String srcFldVal = "";
		String srcIdentifier = "";

		String value = "";

		List<Object> valList = new ArrayList<Object> ();
		String invFormCd = "";
		HashMap<Object,Object> formMap = new HashMap<Object,Object>();
		boolean isMultipleSelect = false;

		try {
			manageForm.setActionMode("PREVIEW");
			RulesDT rulesDT = (RulesDT) manageForm.getOldDT();
			strRuleName = rulesDT.getRuleName();

			manageForm
					.setReturnToLink("<a href=\"/nbs/RuleAdmin.do?method=searchRule\">Return To Rules Results</a> ");
			String cnxt = request.getParameter("context");
			if (cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getRuleInsList());
				manageForm.setManageList(manageForm.getRuleInsList());
				manageForm.setActionMode("VIEWRULES");
				return (mapping.findForward("manageRuleAdmin"));
			}
			dtSFList = manageForm.getManageList();
			dtTFList = manageForm.getTarFieldList();

			String ruleInstanceUid = request.getParameter("hdnRuleInsId")
					.toString();

			formMap = (HashMap<Object,Object>) manageForm.getFormFieldMap();
			invFormCd = "INV_FORM_RVCT";
			Map<?,?> questionMap = (Map<?,?>) QuestionsCache.getQuestionMap().get(
					invFormCd);

			if (request.getParameter("srcIden") != null) {
				srcIdentifier = request.getParameter("srcIden").toString();
				srcValue = request.getParameter("srcIdenVal").toString();


				FormField formField = (FormField) formMap.get(srcIdentifier);

				formField.setValue(srcValue);
			}

			if (request.getParameter("srcMulSelIden") != null) {

				srcIdentifier = request.getParameter("srcMulSelIden")
						.toString();
				srcValue = request.getParameter("srcIdenVal").toString();
				if (srcValue.equalsIgnoreCase(",,"))
					srcValue = "";
				else
					srcValue = srcValue.substring(0, srcValue.length() - 1);

				FormField formField = (FormField) formMap.get(srcIdentifier);

				formField.getState().getMultiSelVals().clear();

				if (srcValue != null && srcValue.indexOf(",") == 0
						&& srcValue.lastIndexOf(",") == 0) {
					String[] vals = srcValue.split(",");
					value = vals[1];
				} else {
					value = srcValue;
				}
				if ("abcxyz".equals(value)) {
					value = null;
				}

				if (value != null && value.lastIndexOf(",") > 0) {
					String[] values = value.split(",");
					if (values != null && values.length > 0) {
						for (int i = 0; i < values.length; i++) {
							if (values[i] != null && !values[i].equals("")) {
								valList.add(values[i]);

							}
						}
					}
					formField.getState().setMultiSelVals(valList);
					isMultipleSelect = true;
				} else if ((valList == null)
						|| (valList != null && valList.size() == 0)) {
					formField.setValue(value);
				}

			}

			StringBuffer buf1 = new StringBuffer("");

			buf1.append("<fieldset id=\"srcFlds\"> ");
			buf1
					.append("<legend class=\"boldTenDkBlue\">Source  Field List<Object> </legend>");
			buf1.append(TABLE_START);

			questionMap = (Map<?,?>) QuestionsCache.getQuestionMap().get(invFormCd);

			if (dtSFList.size() > 0) {
				Iterator<Object>  iter1 = dtSFList.iterator();
				while (iter1.hasNext()) {
					SourceFieldDT dt = (SourceFieldDT) iter1.next();

					NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
							.get(dt.getQuestionIdentifier());
					String datatype = metaData.getDataType();
					String label = metaData.getQuestionLabel();
					buf1.append(TR_START);
					buf1.append(TD_ALIGN_LEFT);
					buf1.append(label);
					buf1.append(TD_END);
					buf1.append(TD_ALIGN_LEFT);
					if (request.getParameter(dt.getQuestionIdentifier()) != null)
						srcFldVal = request.getParameter(
								dt.getQuestionIdentifier()).toString();
					else
						srcFldVal = "";

					if (srcFldVal.equalsIgnoreCase("SELECTED"))
						srcFldVal = "";

					if (datatype.equalsIgnoreCase("Coded")) {
						ArrayList<?> aList = (ArrayList<?> ) CachedDropDowns
								.getCodedValueForType(metaData.getCodeSetNm())
								.clone();
						DropDownCodeDT ddDT = new DropDownCodeDT();

						// buf1.append("<SELECT NAME= \"cmbSrcRules\"");
						buf1.append("<SELECT NAME= \"");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("\"");
						buf1.append(" onchange=\"previewRule(");
						buf1.append("'");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("'");
						buf1.append(")\">");
						int intSize1 = aList.size();
						if (intSize1 > 0) {
							for (int i = 0; i < intSize1; i += 1) {
								ddDT = (DropDownCodeDT) aList.get(i);
								if (ddDT.getKey().equals(srcFldVal)) {
									buf1.append("<OPTION VALUE=");
									buf1.append(ddDT.getKey());
									buf1.append(" SELECTED>");
									buf1.append(ddDT.getValue());
									buf1.append(" </OPTION>");
								} else {
									buf1.append("<OPTION VALUE=");
									buf1.append(ddDT.getKey());
									buf1.append(">");
									buf1.append(ddDT.getValue());
									buf1.append(" </OPTION>");
								}
							}
						}
						buf1.append("</SELECT>");

					}
					if (datatype.equalsIgnoreCase("Date")) {

												
						buf1.append("<input type =\"text\"  name=\"");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("\" ");buf1.append(" id=\"");
						buf1.append(dt.getQuestionIdentifier());buf1.append("\"");

						buf1
								.append(" title=\"Enter a Date\" onkeyup=\"DateMask(this,null,event)\" size=\"10\">");
						buf1
								.append("<img src=\"calendar.gif\" id=\"");
						buf1.append(dt.getQuestionIdentifier());						
						buf1.append("Icon\"");
						buf1.append("onclick=\"getCalDate('");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("','");buf1.append(dt.getQuestionIdentifier());						
						buf1.append("Icon'); return false;");

						buf1.append(" onkeypress =\"showCalendarEnterKey('");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("','");buf1.append(dt.getQuestionIdentifier());						
						buf1.append("Icon',event);\">");
						
					}
					if (datatype.equalsIgnoreCase("Numeric")
							|| datatype.equalsIgnoreCase("Text")) {

						// buf1.append("<input type =\"text\"
						// name=\"numSrcRules\" ");
						/*buf1.append("<input type =\"text\"  name=\"");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("\" ");
						buf1.append(" size=\"10\">");*/
						
						buf1.append("<input type =\"text\"  name=\"");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("\" id=\"");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("\"");
						if((srcValue != null && !srcValue.equalsIgnoreCase("")) && srcIdentifier.equalsIgnoreCase(dt.getQuestionIdentifier())){
						buf1.append(" value = ");
						buf1.append(srcValue );		
						}
						buf1.append(" onchange=\"previewTextRule(");
						buf1.append("'");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("'");
						buf1.append(")\"");
						buf1.append(" size=\"10\">");

					}

					if (srcIdentifier.equalsIgnoreCase("TUB106")) {
						ArrayList<?> aList = (ArrayList<?> ) CachedDropDowns
								.getCodedValueForType(metaData.getCodeSetNm())
								.clone();
						DropDownCodeDT ddDT = new DropDownCodeDT();

						// buf1.append("<SELECT NAME= \"cmbSrcRules\"");
						buf1.append("<SELECT NAME= \"");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("\"");
						buf1.append(" onblur=\"previewMultiSelectRule(");
						buf1.append("this.form.");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append(".options,");
						buf1.append("'");
						buf1.append(dt.getQuestionIdentifier());
						buf1.append("'");
						buf1.append(")\" id=\"mulSrcF\" MULTIPLE>");

						int intSize1 = aList.size();
						boolean mulSelected = false;
						if (intSize1 > 0) {
							for (int i = 0; i < intSize1; i += 1) {
								ddDT = (DropDownCodeDT) aList.get(i);
								if (isMultipleSelect) {
									for (int srcFCount = 0; srcFCount < valList
											.size(); srcFCount++) {
										if (ddDT.getKey().equals(
												valList.get(srcFCount))) {
											buf1.append("<OPTION VALUE=");
											buf1.append(ddDT.getKey());
											buf1.append(" SELECTED>");
											buf1.append(ddDT.getValue());
											buf1.append(" </OPTION>");
											mulSelected = true;
										}
									}
								} else {
									if (ddDT.getKey().equals(value)) {
										buf1.append("<OPTION VALUE=");
										buf1.append(ddDT.getKey());
										buf1.append(" SELECTED>");
										buf1.append(ddDT.getValue());
										buf1.append(" </OPTION>");
										mulSelected = true;
									}
								}
								if (!mulSelected) {
									buf1.append("<OPTION VALUE=");
									buf1.append(ddDT.getKey());
									buf1.append(">");
									buf1.append(ddDT.getValue());
									buf1.append(" </OPTION>");
								}
								mulSelected = false;
							}
						}
						buf1.append("</SELECT>");

					}
					buf1.append(TD_END);
					buf1.append(TR_END);

				}
			} else {
				buf1.append(TR_START);
				buf1.append(TD_ALIGN_LEFT);
				buf1
						.append("No Source Field exists for the selected rule instance");
				buf1.append(TD_END);
				buf1.append(TR_END);
			}
			buf1.append(TR_START);
			buf1.append(TD_ALIGN_LEFT);
			buf1.append("&nbsp;");
			buf1.append(TD_END);
			buf1.append(TR_END);

			buf1.append(TABLE_END);
			buf1.append("</fieldset>");
			buf1.append("<fieldset> ");
			buf1
					.append("<legend class=\"boldTenDkBlue\">Target  Field List<Object> </legend>");
			buf1.append(TABLE_START);
			RulesEngineUtil util = new RulesEngineUtil();

			Collection<Object>  formfieldColl = util.fireRules(srcIdentifier, manageForm, formMap);

			if (dtTFList.size() > 0) {
				Iterator<Object>  iter2 = dtTFList.iterator();
				while (iter2.hasNext()) {
					TargetFieldDT dt = (TargetFieldDT) iter2.next();
					NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
							.get(dt.getQuestionIdentifier());
					String datatype = metaData.getDataType();
					String label = metaData.getQuestionLabel();
					if (formfieldColl != null) {
						Iterator<Object>  iter = formfieldColl.iterator();
						while (iter.hasNext()) {
							FormField fField = (FormField) iter.next();
							if (fField.getFieldId().toString()
									.equalsIgnoreCase(
											dt.getQuestionIdentifier()
													.toString())) {
								// for Enable and disable rules
								if (strRuleName.equalsIgnoreCase("filter")) {

									buf1.append(TR_START);
									buf1.append(TD_ALIGN_LEFT);
									buf1.append(label);
									buf1.append(TD_END);
									buf1.append(TD_ALIGN_LEFT);
									if (datatype.equalsIgnoreCase("Coded")) {
										ArrayList<Object> aList = (ArrayList<Object> ) fField
												.getState().getValues();

										DropDownCodeDT ddDT = new DropDownCodeDT();

										buf1
												.append("<SELECT NAME= \"cmbTarRules\" ");

										// buf1.append(fField.getState().isEnabled());
										buf1.append(">");

										int intSize1 = aList.size();
										if (intSize1 > 0) {
											for (int i = 0; i < intSize1; i += 1) {
												ddDT = (DropDownCodeDT) aList
														.get(i);
												buf1.append("<OPTION VALUE=");
												buf1.append(ddDT.getKey());
												buf1.append(">");
												buf1.append(ddDT.getValue());
												buf1.append(" </OPTION>");
											}
										}
										buf1.append("</SELECT>");

									}
									if (datatype.equalsIgnoreCase("Date")) {

										buf1
												.append("<input type =\"text\"  name=\"dateTarRules\" ");
										buf1.append("id=\"");
										buf1.append(dt.getQuestionIdentifier());buf1.append("\"");

										buf1
												.append(" title=\"Enter a Date\" onkeyup=\"DateMask(this,null,event)\" size=\"10\">");
										buf1
												.append("<img src=\"calendar.gif\" id=\"");
										buf1.append(dt.getQuestionIdentifier());						
										buf1.append("Icon\"");
										buf1.append("onclick=\"getCalDate('");
										buf1.append(dt.getQuestionIdentifier());
										buf1.append("','");buf1.append(dt.getQuestionIdentifier());						
										buf1.append("Icon'); return false;");

										buf1.append(" onkeypress =\"showCalendarEnterKey('");
										buf1.append(dt.getQuestionIdentifier());
										buf1.append("','");buf1.append(dt.getQuestionIdentifier());						
										buf1.append("Icon',event);\">");
										
									}
									if (datatype.equalsIgnoreCase("Numeric")
											|| datatype
													.equalsIgnoreCase("Text")) {
										if (strRuleName.equalsIgnoreCase("enable")
												|| strRuleName.equalsIgnoreCase("disable"))
										buf1
												.append("<input type =\"text\"  name=\"numSrcRules\" disabled ");
										else
											buf1
											.append("<input type =\"text\"  name=\"numSrcRules\" ");
										buf1.append(" size=\"10\">");

									}
									if (datatype.equalsIgnoreCase("Boolean")) {
										ArrayList<Object> aList = (ArrayList<Object> ) fField
												.getState().getValues();

										DropDownCodeDT ddDT = new DropDownCodeDT();

										buf1
												.append("<SELECT NAME= \"bolTarRules\" ");

										// buf1.append(fField.getState().isEnabled());
										buf1.append(">");

										int intSize1 = aList.size();
										if (intSize1 > 0) {
											for (int i = 0; i < intSize1; i += 1) {
												ddDT = (DropDownCodeDT) aList
														.get(i);
												buf1.append("<OPTION VALUE=");
												buf1.append(ddDT.getKey());
												buf1.append(">");
												buf1.append(ddDT.getValue());
												buf1.append(" </OPTION>");
											}
										}
										buf1.append("</SELECT>");

									}

									buf1.append(TD_END);
									buf1.append(TR_END);
								}// filter ends
								// for Enable and disable rules
								if (strRuleName.equalsIgnoreCase("enable")
										|| strRuleName
												.equalsIgnoreCase("disable")) {

									buf1.append(TR_START);
									buf1.append(TD_ALIGN_LEFT);
									buf1.append(label);
									buf1.append(TD_END);
									buf1.append(TD_ALIGN_LEFT);

									if (datatype.equalsIgnoreCase("Coded")) {
										ArrayList<?> aList = (ArrayList<?> ) CachedDropDowns
												.getCodedValueForType(
														metaData.getCodeSetNm())
												.clone();

										DropDownCodeDT ddDT = new DropDownCodeDT();

										buf1
												.append("<SELECT NAME= \"cmbTarRules\" ");
										if (!fField.getState().isEnabled())
											buf1.append("disabled ");
										// buf1.append(fField.getState().isEnabled());
										buf1.append(">");

										int intSize1 = aList.size();
										if (intSize1 > 0) {
											for (int i = 0; i < intSize1; i += 1) {
												ddDT = (DropDownCodeDT) aList
														.get(i);
												buf1.append("<OPTION VALUE=");
												buf1.append(ddDT.getKey());
												buf1.append(">");
												buf1.append(ddDT.getValue());
												buf1.append(" </OPTION>");
											}
										}
										buf1.append("</SELECT>");

									}
									if (datatype.equalsIgnoreCase("Date")) {

										buf1
												.append("<input type =\"text\"  name=\"dateTarRules\" ");
										if (!fField.getState().isEnabled())
											buf1.append("disabled ");
										buf1.append("id=\"");
										buf1.append(dt.getQuestionIdentifier());buf1.append("\"");
										buf1
												.append(" title=\"Enter a Date\" onkeyup=\"DateMask(this,null,event)\" size=\"10\">");
										buf1
										.append("<img src=\"calendar.gif\" id=\"");
										buf1.append(dt.getQuestionIdentifier());						
										buf1.append("Icon\"");
										buf1.append("onclick=\"getCalDate('");
										buf1.append(dt.getQuestionIdentifier());
										buf1.append("','");buf1.append(dt.getQuestionIdentifier());						
										buf1.append("Icon'); return false;");
										
										buf1.append(" onkeypress =\"showCalendarEnterKey('");
										buf1.append(dt.getQuestionIdentifier());
										buf1.append("','");buf1.append(dt.getQuestionIdentifier());						
										buf1.append("Icon',event);\">");
										
									}
									if (datatype.equalsIgnoreCase("Numeric")
											|| datatype
													.equalsIgnoreCase("Text")) {

										buf1
												.append("<input type =\"text\"  name=\"numSrcRules\" ");
										if (!fField.getState().isEnabled())
											buf1.append("disabled ");
										buf1.append(" size=\"10\">");

									}
									if (datatype.equalsIgnoreCase("Boolean")) {
										ArrayList<?> aList = (ArrayList<?> ) CachedDropDowns
												.getCodedValueForType(
														metaData.getCodeSetNm())
												.clone();

										DropDownCodeDT ddDT = new DropDownCodeDT();

										buf1
												.append("<SELECT NAME= \"bolTarRules\" ");
										if (!fField.getState().isEnabled())
											buf1.append("disabled ");
										// buf1.append(fField.getState().isEnabled());
										buf1.append(">");

										int intSize1 = aList.size();
										if (intSize1 > 0) {
											for (int i = 0; i < intSize1; i += 1) {
												ddDT = (DropDownCodeDT) aList
														.get(i);
												buf1.append("<OPTION VALUE=");
												buf1.append(ddDT.getKey());
												buf1.append(">");
												buf1.append(ddDT.getValue());
												buf1.append(" </OPTION>");
											}
										}
										buf1.append("</SELECT>");

									}

									buf1.append(TD_END);
									buf1.append(TR_END);
								}// enable/disable ends
							}
						}
					}
				}
				if (formfieldColl.size() == 0) {
					buf1.append(TR_START);
					buf1.append(TD_ALIGN_LEFT);
					buf1
							.append("No Target Field exists for the selected source field");
					buf1.append(TD_END);
					buf1.append(TR_END);

				}

			} else {
				buf1.append(TR_START);
				buf1.append(TD_ALIGN_LEFT);
				buf1
						.append("No Target Field exists for the selected rule instance");
				buf1.append(TD_END);
				buf1.append(TR_END);
			}
			buf1.append(TR_START);
			buf1.append(TD_ALIGN_LEFT);
			buf1.append("&nbsp;");
			buf1.append(TD_END);
			buf1.append(TR_END);

			buf1
					.append("<INPUT TYPE=\"hidden\" NAME=\"hdnRuleInsId\" VALUE=\"");
			buf1.append(ruleInstanceUid);
			buf1.append("\">");
			buf1.append(TABLE_END);
			buf1.append("</fieldset>");

			buf1.append(TABLE_START);
			buf1.append(TR_START);
			buf1.append(TD_ALIGN_RIGHT);
			buf1
					.append("<input type=\"button\" name=\"submit6\" id=\"submit6\" value=\"Cancel\" onClick=\"return cancelPreview();\"/>");
			buf1.append(TD_END);
			buf1.append(TR_END);
			buf1.append(TABLE_END);

			isMultipleSelect = false;
			request.setAttribute("previewR", buf1.toString());
			manageForm.setManageList(dtSFList);
			manageForm.setSrcFieldList(dtSFList);
			manageForm.setTarFieldList(dtTFList);

			try {
				PamLoadUtil pamLoadUtil = new PamLoadUtil();
				pamLoadUtil.viewRuleAdminLoadUtil(manageForm, request);
			} catch (Exception e) {
				logger.error("Error while loading PAM Case: " + e.toString());
				throw new ServletException("Error while loading PAM Case: "+e.getMessage(),e);
			}

			request.setAttribute("manageFormMap", manageForm.getFormFieldMap());
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldDT());
			request.setAttribute("manageTFList", manageForm.getTarFieldList());
			manageForm.setActionMode("PREVIEW");
			manageForm
					.getAttributeMap()
					.put("cancel",
							"/nbs/RuleAdmin.do?method=viewRuleIns&context=cancel#result");

		} catch (Exception e) {
			logger.error("Exception in firePreviewRule: " + e.getMessage());
			e.printStackTrace();

		} finally {
			request.setAttribute("manageFormMap", manageForm.getFormFieldMap());
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("manageListold", manageForm.getOldDT());
			request.setAttribute("manageTFList", manageForm.getTarFieldList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE,
					SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("manageRuleAdmin"));
	}

}
