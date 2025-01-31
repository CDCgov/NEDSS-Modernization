package gov.cdc.nedss.webapp.nbs.action.pagemanagement.managequestion;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageElementVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managepage.PageElementForm;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managequestion.ManageQuestionsForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

public class SearchQuestionsAction extends DispatchAction {

static final LogUtils logger = new LogUtils(SearchQuestionsAction.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	String editLinkEnabledIcon = "page_white_edit.gif", editLinkDisabledIcon = "page_white_edit_disabled.gif";
	PropertyUtil properties = PropertyUtil.getInstance();
	QueueUtil queueUtil = new QueueUtil();

	public ActionForward searchQuestionsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		try {
			ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
			manageQuestionsForm.clearSelections();
			manageQuestionsForm.setSelection(new WaQuestionDT());
			manageQuestionsForm.setSearchCriteria("questionIdentifier", "");
			manageQuestionsForm.setSearchCriteria("questionNm", "");
			manageQuestionsForm.setSearchCriteria("group", "");
			manageQuestionsForm.setSearchCriteria("subGroup", "");			
			manageQuestionsForm.setSearchCriteria("label", "");
			manageQuestionsForm.setSearchCriteria("recordStatusCd", "N");
			
			manageQuestionsForm.setPageTitle("Manage Questions: Search Question", request);
			manageQuestionsForm.setActionMode(SRTAdminConstants.MANAGE);
			manageQuestionsForm.getAttributeMap().put("cancel", "/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&actionMode=Manage&context=cancel");
			manageQuestionsForm.setReturnToLink("<a href=\"/nbs/SearchManageQuestions.do?method=searchQuestionsSubmit\">Return To Search Results</a> ");
		} catch (Exception e) {
			logger.error("Exception in searchQuestionsLoad: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		} finally {
			
		}
		return (mapping.findForward("searchQuestionsLoad"));

	}

	@SuppressWarnings("unchecked")
	public ActionForward searchQuestionsSubmit(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request, HttpServletResponse response) 
	{
	    ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
		manageQuestionsForm.setActionMode(null);
		ArrayList<Object> questionLibraryList = new ArrayList<Object> ();
		PageManagementActionUtil util = new PageManagementActionUtil();
		String identifier = HTMLEncoder.encodeHtml((String)request.getParameter("identifier"));
		StringBuffer searchConfirm = new StringBuffer();
		
		ArrayList<Object> questionCollList = null;
		
		//Filter code starts here
		boolean forceEJBcall = false;
		boolean initLoad = request.getParameter("initLoad") == null ? 
		        false : Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
		
		if (initLoad && !PaginationUtil._dtagAccessed(request)) {
			manageQuestionsForm.clearAll();
			forceEJBcall = true;
			// get the number of records to be displayed per page
			Integer queueSize = properties.getQueueSize(NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS_QUEUE_SIZE);
			manageQuestionsForm.getAttributeMap().put("queueSize", queueSize);
		}
		
		try {
			String scString = this.buildSearchCriteriaString(manageQuestionsForm.getSearchMap());
			if (forceEJBcall) 
			{
			    questionLibraryList = util.searchWaQuestions(request.getSession(), manageQuestionsForm.getSearchMap(),
			            "searchQuestionsSubmit",PageManagementActionUtil.INVOCATION_CONTEXT_NON_PAGE_BUILDER);
			
			    // if the search result is null reload the search page again.
			    if (questionLibraryList == null || questionLibraryList.size()== 0) {
			        String clickHereLn = "/nbs/LoadManageQuestions.do?method=addQuestionLoad";
			        searchConfirm.append("Your Search Criteria:").append(scString).append(" resulted in 0 possible matches.")
			            .append(" Refine your search criteria and resubmit the search, Cancel to return to the Question Library, or ");
			        
			        request.setAttribute("searchConfirmMsgNoResult", searchConfirm.toString());
			        request.setAttribute("clickHereLk", clickHereLn);
			        manageQuestionsForm.getAttributeMap().put("cancel", "/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&actionMode=Manage&context=cancel");
			        NBSContext.store(request.getSession(true), NBSConstantUtil.DSManageList,questionLibraryList);
			        NBSContext.store(request.getSession(true), NBSConstantUtil.DSManageListFull,questionLibraryList);
			        //manageQuestionsForm.getAttributeMap().put("manageList", questionCollList);
			        return (mapping.findForward("searchQuestionsLoad"));
			    }
			    
			    request.setAttribute("manageList", questionLibraryList);
			    
				//Set Coll as property of Form first time since this list is used for distinct dropdown values in the form
			    NBSContext.store(request.getSession(true), NBSConstantUtil.DSManageList,questionLibraryList);
			    NBSContext.store(request.getSession(true), "DSManageListFull",questionLibraryList);
				manageQuestionsForm.initializeDropDowns(questionLibraryList);
				manageQuestionsForm.getAttributeMap().put("type",new Integer(manageQuestionsForm.getType().size()));
				manageQuestionsForm.getAttributeMap().put("group",new Integer(manageQuestionsForm.getGroup().size()));
				manageQuestionsForm.getAttributeMap().put("subGroup",new Integer(manageQuestionsForm.getSubGroup().size()));
				manageQuestionsForm.getAttributeMap().put("statusCount",new Integer(manageQuestionsForm.getStatus().size()));
				Iterator<Object> iter = questionLibraryList.iterator();
				while(iter.hasNext()) {
					WaQuestionDT dt = (WaQuestionDT) iter.next();
					String questionUid = dt.getWaQuestionUid().toString();
					request.setAttribute("QuestionUid", questionUid);
					String viewUrl = "<a href=\"javascript:viewQuestion('" + questionUid + "');\"><img src=\"page_white_text.gif\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
					dt.setViewLink(viewUrl);
					
					String editUrl = "";
					if (dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Active) || 
					        dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						if(dt.getCoInfQuestion()!= null && dt.getCoInfQuestion().equals("T")){
                    		editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
                    	}else
					    editUrl = "<a href=\"javascript:editQuestion('" + questionUid + "');\"><img src=\"" + editLinkEnabledIcon + "\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>"; 
					}
					else {
					    editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
					}
					
					dt.setEditLink(editUrl);
				}
				}else {
					questionLibraryList =(ArrayList<Object>) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSManageList);
				}
			
			    boolean existing = request.getParameter("existing") == null ? false : true;
		
			
				sortManageQuestions(manageQuestionsForm, questionLibraryList, existing, request);
				//Filter return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
				if(!existing) {
					Iterator<Object> iter = questionLibraryList.iterator();
					while(iter.hasNext()) {
						WaQuestionDT dt = (WaQuestionDT) iter.next();
						String questionUid = dt.getWaQuestionUid().toString();
						request.setAttribute("QuestionUid", questionUid);
						String viewUrl = "<a href=\"javascript:viewQuestion('" + questionUid + "');\"><img src=\"page_white_text.gif\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
						dt.setViewLink(viewUrl);
						
						String editUrl = "";
	                    if (dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Active) || 
	                            dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
	                    	if(dt.getCoInfQuestion()!= null && dt.getCoInfQuestion().equals("T")){
	                    		editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
	                    	}else
	                        editUrl = "<a href=\"javascript:editQuestion('" + questionUid + "');\"><img src=\"" + editLinkEnabledIcon + "\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>"; 
	                    }
	                    else {
	                        editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
	                    }
	                    
						dt.setEditLink(editUrl);
						}	
				}else {
					filterQuestionLib(manageQuestionsForm, request);
					Iterator<Object> iter = questionLibraryList.iterator();
					while(iter.hasNext()) {
						WaQuestionDT dt = (WaQuestionDT) iter.next();
						String questionUid = dt.getWaQuestionUid().toString();
						request.setAttribute("QuestionUid", questionUid);
						String viewUrl = "<a href=\"javascript:viewQuestion('" + questionUid + "');\"><img src=\"page_white_text.gif\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
						dt.setViewLink(viewUrl);

                        String editUrl = "";
                        if (dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Active) || 
                                dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
                        	if(dt.getCoInfQuestion()!= null && dt.getCoInfQuestion().equals("T")){
	                    		editUrl = "<img src=\"" + editLinkDisabledIcon + "\"title = \"Edit disabled\" alt = \"Edit disabled\" />";
	                    	}else
                            editUrl = "<a href=\"javascript:editQuestion('" + questionUid + "');\"><img src=\"" + editLinkEnabledIcon + "\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>"; 
                        }
                        else {
                            editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
                        }
                       
                        dt.setEditLink(editUrl);
						}	
				}
				//To make sure SelectAll is checked, see if no criteria is applied
				if(manageQuestionsForm.getSearchCriteriaArrayMap().size() == 0)
					request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));	
				
				manageQuestionsForm.getAttributeMap().put("queueCount", String.valueOf(questionLibraryList.size()));
				request.setAttribute("queueCount", String.valueOf(questionLibraryList.size()));
				//manageQuestionsForm.setManageList(questionLibraryList);
				// confirm message
				request.setAttribute("SearchCriteria", scString);
				
				//searchConfirm.append("Your Search Criteria:").append(scString).append(" resulted in <b>")
				//.append(String.valueOf(questionLibraryList.size())).append("</b> possible matches.");
				//request.setAttribute("searchConfirmMsg", searchConfirm.toString());
				
				
				String searchConfirmMsg = searchConfirm.append("Your Search Criteria:").append(scString).append(" resulted in ").toString();
				String searchConfirmMsg1 =String.valueOf(questionLibraryList.size());
				String searchConfirmMsg2="possible matches.";
				
				request.setAttribute("searchConfirmMsg", searchConfirmMsg.toString());
				request.setAttribute("searchConfirmMsg1", searchConfirmMsg1.toString());
				request.setAttribute("searchConfirmMsg2", searchConfirmMsg2.toString());
				
				// refine search Link
				request.setAttribute("RefineSearchLink", "/nbs/SearchManageQuestions.do?method=refineSearch&identifier="+identifier);
		} catch (Exception e) {
			logger.error("Exception in searchQuestionsSubmit: " + e.getMessage());
			e.printStackTrace();
			return (mapping.findForward("searchQuestionsLoad"));
		} finally {
			
		}
		return PaginationUtil.paginate(manageQuestionsForm, request, "searchQuestionsResult",mapping);
	}
	
	public ActionForward searchQuestionsLoadFromPageBuilder(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		try {
			ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
			String subSectionPageElementId = request.getParameter("SubsectionId");
			String childEltsCount = request.getParameter("childEltsCount");
			
			manageQuestionsForm.clearSelections();
			manageQuestionsForm.setSelection(new WaQuestionDT());
			manageQuestionsForm.setSearchCriteria("phinDefinedInd", "N");
            manageQuestionsForm.setSearchCriteria("questionIdentifier", "");
            manageQuestionsForm.setSearchCriteria("questionNm", "");
            manageQuestionsForm.setSearchCriteria("group", "");
            manageQuestionsForm.setSearchCriteria("subGroup", "");          
            manageQuestionsForm.setSearchCriteria("label", "");
            manageQuestionsForm.setSearchCriteria("recordStatusCd", "N");
			
			manageQuestionsForm.setPageTitle("Search Question", request);
			manageQuestionsForm.setActionMode(SRTAdminConstants.MANAGE);
			manageQuestionsForm.setReturnToLink("<a href=\"/nbs/SearchManageQuestions.do?method=searchQuestionsSubmitFromPageBuilder\">Return To Search Results</a> ");
			
			// Preset the page element form to handle
			// static elements.
			PageElementForm peForm = new PageElementForm();
            PageElementVO peVo = new PageElementVO();
            peVo.setPageElementUid(new Long(0));
            peVo.setWaUiMetadataDT(new WaUiMetadataDT());
            peVo.getWaUiMetadataDT().setIsSecured("F");
            peVo.getWaUiMetadataDT().setDisplayInd("T");
            peVo.getWaUiMetadataDT().setEnableInd("T");
            peForm.setPageEltVo(peVo);
            request.getSession().setAttribute("pageElementForm", peForm);
            request.getSession().setAttribute("SubsectionId", HTMLEncoder.encodeHtml(subSectionPageElementId));
            request.getSession().setAttribute("childEltsCount", HTMLEncoder.encodeHtml(childEltsCount));
           // check to see if the subsection is a repeating block. If so filter out all the questions that don't go to CASE_ANSWER table
        	PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
        	PageElementVO pageElementVO = new PageElementVO();
    	 	Integer  subSectionGroupUid = null;
    	 	Long subSectionEltId = new Long(subSectionPageElementId);
    	 	for (Object pVo : pmpVo.getThePageElementVOCollection()) {
    			 Long val1 = ((PageElementVO) pVo).getPageElementUid();
    			 if (val1 != null && subSectionEltId != null &&  val1.equals(subSectionEltId)) {
    				 pageElementVO = (PageElementVO) pVo;
    				 subSectionGroupUid = pageElementVO.getWaUiMetadataDT().getQuestionGroupSeqNbr();
    				 break;
    			 }
    		 }
        	request.getSession().setAttribute("batchUidForAdd", subSectionGroupUid);
           
		} catch (Exception e) {
			logger.error("Exception in searchQuestionsLoadFromPageBuilder: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		} finally {
			
		}
		return (mapping.findForward("loadQuestionsSearch"));

	}
	
	public ActionForward searchQuestionsSubmitFromPageBuilder(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		try {
			ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
			manageQuestionsForm.setActionMode(null);
			PageManagementActionUtil util = new PageManagementActionUtil();
			String identifier = HTMLEncoder.encodeHtml(request.getParameter("identifier"));
			String subSectionPageElementId = HTMLEncoder.encodeHtml(request.getParameter("SubsectionId"));
			ArrayList<?> dtList = util.searchWaQuestions(request.getSession(), 
			        manageQuestionsForm.getSearchMap(),
			        "searchQuestionsSubmitFromPageBuilder",
			        PageManagementActionUtil.INVOCATION_CONTEXT_PAGE_BUILDER);
 			if(dtList.size() == 0){
				manageQuestionsForm.getAttributeMap().put("NORESULT","NORESULT");
				ActionMessages messages = new ActionMessages();				
				messages.add(NBSPageConstants.INFO_MESSAGES_PROPERTY,
						new ActionMessage(NBSPageConstants.INFO_SUCCESS_MESSAGE_KEY));
				request.setAttribute("info_messages", messages);
				return (mapping.findForward("loadQuestionsSearch"));
 			}
 			NBSContext.store(request.getSession(true), NBSConstantUtil.DSManageList,dtList);
		    NBSContext.store(request.getSession(true), "DSManageListFull",dtList);
			request.setAttribute("manageList", dtList);
			request.setAttribute("ResultsCount", HTMLEncoder.encodeHtml(String.valueOf(dtList.size())));
			manageQuestionsForm.getAttributeMap().put("queueCount", String.valueOf(dtList.size()));
			request.setAttribute("queueCount", HTMLEncoder.encodeHtml(String.valueOf(dtList.size())));
			String scString = this.buildSearchCriteriaString(manageQuestionsForm.getSearchMap());
			request.setAttribute("SearchCriteria", scString);
			request.setAttribute("RefineSearchLink", "SearchManageQuestions.do?method=refineSearchFromPageBuilder&identifier="+identifier+"&SubsectionId="+subSectionPageElementId);
			request.setAttribute("NewSearchLink", "SearchManageQuestions.do?method=searchQuestionsLoadFromPageBuilder&identifier="+identifier+"&SubsectionId="+subSectionPageElementId);
			Integer queueSize = properties.getQueueSize(NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS_QUEUE_SIZE);
			manageQuestionsForm.getAttributeMap().put("queueSize", HTMLEncoder.encodeHtml(String.valueOf(queueSize)));

		} catch (Exception e) {
			logger.error("Exception in searchQuestionsSubmitFromPageBuilder: " + e.getMessage());
			e.printStackTrace();
			return (mapping.findForward("default"));
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,"Search Question Results");
		}
		return (mapping.findForward("viewResults"));

	}
	
	 private String buildSearchCriteriaString(Map<Object,Object> searchMap) throws ServletException {
		    //  build the criteria string
		StringBuffer sb = new StringBuffer("");
		String sQuerynew = "";
		try {
			CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();	
			PageManagementActionUtil util = new PageManagementActionUtil();
			String questionId = handleSrch(searchMap.get("questionIdentifier"));
			String questionName = handleSrch(searchMap.get("questionNm"));
			String group = handleSrch(searchMap.get("group"));
			String subGroup = handleSrch(searchMap.get("subGroup"));
			String label = handleSrch(searchMap.get("label"));
			String questionType = handleSrch(searchMap.get("questionType"));
			
			if(questionType.length()>0 ){
				 sb.append("Question Type equals ").append(
				            "'" + cachedDropDownValues.getDescForCode("NBS_QUESTION_TYPE",questionType) + "'").append(", ");
			}
			
			if(questionId.length()>0 ){
				 sb.append("Unique Identifier contains ").append(
				            "'" + questionId + "'").append(", ");
			}
			if(questionName.length()>0 ){
				 sb.append("Unique Name contains ").append(
				            "'" + questionName + "'").append(", ");
			}
			if(group.length()>0 ){
				 sb.append("Group equals ").append(
				            "'" + cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_GROUP,group) + "'").append(", ");
			}
			if(subGroup.length()>0 ){
				 sb.append("Subgroup equals ").append(
				            "'" + cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_SUBGROUP,subGroup) + "'").append(", ");
			}
			if(label.length()>0 ){
				 sb.append("Label contains ").append(
				            "'" + label + "'").append(", ");
			}

			if (sb != null && sb.length() > 2) {
			    sQuerynew = sb.substring(0, sb.length()-2);
			}
		} catch (Exception e) {
			logger.error("Error Exception:", e.getMessage());
			throw new ServletException("Error while buildSearchCriteriaString: "+e.getMessage(),e);
		}
	    
		return sQuerynew;

		} 
	 
	 public ActionForward refineSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException {
			try {
				String identifier = HTMLEncoder.encodeHtml(request.getParameter("identifier"));
				request.setAttribute("identifier", identifier);
				ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
				manageQuestionsForm.getAttributeMap().put("cancel", "/nbs/SearchManageQuestions.do?method=loadQuestionLibrary&actionMode=Manage&context=cancel");
			} catch (Exception e) {
				logger.error("Error Exception:", e.getMessage());
				throw new ServletException("Error while refineSearch: "+e.getMessage(),e);
			}
			return (mapping.findForward("searchQuestionsLoad"));
		}
	 
	 public ActionForward refineSearchFromPageBuilder(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException {
			try {
				String identifier = HTMLEncoder.encodeHtml(request.getParameter("identifier"));
				request.setAttribute("identifier", identifier);
				ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
			
			} catch (Exception e) {
				logger.error("Error Exception:", e.getMessage());
				throw new ServletException("Error while refineSearch: "+e.getMessage(),e);
			}
			return (mapping.findForward("loadQuestionsSearch"));
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
		@SuppressWarnings("unchecked")
		public ActionForward loadQuestionLibrary(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException,
	    ServletException {
			ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
			
			try {
				manageQuestionsForm.setActionMode(null);
				manageQuestionsForm.setSearchMap(new HashMap<Object, Object>());
				PageManagementActionUtil util = new PageManagementActionUtil();
				String identifier = (String)request.getParameter("identifier");
				
				ArrayList<Object> questionLibraryList = new ArrayList<Object> ();
				HttpSession session = request.getSession(true);
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
			    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.LDFADMINISTRATION);
			    if (securityCheck != true) {
			      session.setAttribute("error", "Failed at security checking. OR Page Management permission");
			      throw new ServletException("Failed at security checking. OR Page Management permission");
			    }
				boolean forceEJBcall = false;
				boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
				if (initLoad && !PaginationUtil._dtagAccessed(request)) {
					manageQuestionsForm.clearAll();
					forceEJBcall = true;
					// get the number of records to be displayed per page
					Integer queueSize = properties.getQueueSize(NEDSSConstants.PAGE_MANAGEMENT_QUESTION_LIBRARY_QUEUE_SIZE);
					manageQuestionsForm.getAttributeMap().put("queueSize", queueSize);
				}
				// This is for cancel button and the return to Link
				String contextAction = request.getParameter("context");
				if (contextAction != null) {
					if(contextAction.equals("cancel"))
						forceEJBcall = false;
					else if(contextAction.equals("ReturnToManage"))
						forceEJBcall = true;
					else if(contextAction.equals("question"))
						forceEJBcall = true;
				}
				
				try{
					if(forceEJBcall){
					questionLibraryList = util.searchWaQuestions(request.getSession(), null,
					        "searchQuestionsSubmit", PageManagementActionUtil.INVOCATION_CONTEXT_PAGE_LIBRARY);
					request.setAttribute("manageList", questionLibraryList);
					//Set InvSummaryVOColl as property of Form first time since this list is used for distinct dropdown values in the form
					NBSContext.store(session, NBSConstantUtil.DSManageList, questionLibraryList);
				    NBSContext.store(request.getSession(true), NBSConstantUtil.DSManageListFull,questionLibraryList);
					manageQuestionsForm.initializeDropDowns(questionLibraryList);
					manageQuestionsForm.getAttributeMap().put("type",new Integer(manageQuestionsForm.getType().size()));
					manageQuestionsForm.getAttributeMap().put("group",new Integer(manageQuestionsForm.getGroup().size()));
					manageQuestionsForm.getAttributeMap().put("subGroup",new Integer(manageQuestionsForm.getSubGroup().size()));
					manageQuestionsForm.getAttributeMap().put("statusCount",new Integer(manageQuestionsForm.getStatus().size()));
					Iterator<Object> iter = questionLibraryList.iterator();
					while(iter.hasNext()) {
						WaQuestionDT dt = (WaQuestionDT) iter.next();
						
						String questionUid = HTMLEncoder.encodeHtml(dt.getWaQuestionUid().toString());
						request.setAttribute("QuestionUid", questionUid);
						String viewUrl = "<a href=\"javascript:viewQuestion('" + questionUid + "');\"><img src=\"page_white_text.gif\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
						dt.setViewLink(viewUrl);
						
						String editUrl = "";
	                    if (dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Active) || 
	                            dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
	                    	if(dt.getCoInfQuestion()!= null && dt.getCoInfQuestion().equals("T")){
	                    		editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
	                    	}else
	                        editUrl = "<a href=\"javascript:editQuestion('" + questionUid + "');\"><img src=\"" + editLinkEnabledIcon + "\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>"; 
	                    }
	                    else {
	                        editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
	                    }
	                    
						dt.setEditLink(editUrl);
						}
					
					
					}else {
						questionLibraryList = (ArrayList<Object>)NBSContext.retrieve(session, NBSConstantUtil.DSManageList);
					}
					
					boolean existing = request.getParameter("existing") == null ? false : true;
					
					if(contextAction != null && (contextAction.equalsIgnoreCase("ReturnToManage")|| contextAction.equalsIgnoreCase("question"))) {
						Iterator<Object> iter = questionLibraryList.iterator();
						while(iter.hasNext()) {
							WaQuestionDT dt = (WaQuestionDT) iter.next();
							String questionUid = HTMLEncoder.encodeHtml(dt.getWaQuestionUid().toString());
							request.setAttribute("QuestionUid", questionUid);
							dt.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
							String viewUrl = "<a href=\"javascript:viewQuestion('" + questionUid + "');\"><img src=\"page_white_text.gif\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
							dt.setViewLink(viewUrl);
							
							String editUrl = "";
	                        if (dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Active) || 
	                                dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
	                        	if(dt.getCoInfQuestion()!= null && dt.getCoInfQuestion().equals("T")){
		                    		editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
		                    	}else
	                            editUrl = "<a href=\"javascript:editQuestion('" + questionUid + "');\"><img src=\"" + editLinkEnabledIcon + "\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>"; 
	                        }
	                        else {
	                            editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
	                        }
	                       
	                        dt.setEditLink(editUrl);
							}	
						//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
						Collection<Object>  filteredColl = filterQuestionLib(manageQuestionsForm, request);
						if(filteredColl != null)
							questionLibraryList = (ArrayList<Object> ) filteredColl;
						sortManageQuestions(manageQuestionsForm, questionLibraryList, existing, request);
					} else {
						sortManageQuestions(manageQuestionsForm, questionLibraryList, existing, request);
					//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
						if(!existing) {
							Iterator<Object> iter = questionLibraryList.iterator();
							while(iter.hasNext()) {
								WaQuestionDT dt = (WaQuestionDT) iter.next();
								String questionUid = HTMLEncoder.encodeHtml(dt.getWaQuestionUid().toString());
								request.setAttribute("QuestionUid", questionUid);
								String viewUrl = "<a href=\"javascript:viewQuestion('" + questionUid + "');\"><img src=\"page_white_text.gif\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
								dt.setViewLink(viewUrl);
								
	                            String editUrl = "";
	                            if (dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Active) || 
	                                    dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
	                            	if(dt.getCoInfQuestion()!= null && dt.getCoInfQuestion().equals("T")){
	    	                    		editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
	    	                    	}else
	                                editUrl = "<a href=\"javascript:editQuestion('" + questionUid + "');\"><img src=\"" + editLinkEnabledIcon + "\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>"; 
	                            }
	                            else {
	                                editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
	                            }
	                           
	                            
								dt.setEditLink(editUrl);
								}	
						}else {
							filterQuestionLib(manageQuestionsForm, request);
							Iterator<Object> iter = questionLibraryList.iterator();
							while(iter.hasNext()) {
								WaQuestionDT dt = (WaQuestionDT) iter.next();
								String questionUid = HTMLEncoder.encodeHtml(dt.getWaQuestionUid().toString());
								request.setAttribute("QuestionUid", questionUid);
								String viewUrl = "<a href=\"javascript:viewQuestion('" + questionUid + "');\"><img src=\"page_white_text.gif\" class=\"cursorHand\" title=\"View\" alt=\"View\"/></a>";
								dt.setViewLink(viewUrl);

                                String editUrl = "";
                                if (dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Active) || 
                                        dt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
                                	if(dt.getCoInfQuestion()!= null && dt.getCoInfQuestion().equals("T")){
        	                    		editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
        	                    	}else
                                    editUrl = "<a href=\"javascript:editQuestion('" + questionUid + "');\"><img src=\"" + editLinkEnabledIcon + "\" class=\"cursorHand\" title=\"Edit\" alt=\"Edit\"/></a>"; 
                                }
                                else {
                                    editUrl = "<img src=\"" + editLinkDisabledIcon + "\" title = \"Edit disabled\" alt = \"Edit disabled\"/>";
                                }
                                
                                dt.setEditLink(editUrl);
								}	
						}
					}
					
					
					//To make sure SelectAll is checked, see if no criteria is applied
					if(manageQuestionsForm.getSearchCriteriaArrayMap().size() == 0)
						request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));	
					
					manageQuestionsForm.getAttributeMap().put("queueCount", String.valueOf(questionLibraryList.size()));
					request.setAttribute("queueCount", HTMLEncoder.encodeHtml(String.valueOf(questionLibraryList.size())));
					//manageQuestionsForm.setManageList(questionLibraryList);
					request.setAttribute("manageList", questionLibraryList);
					
					request.setAttribute("ResultsCount", HTMLEncoder.encodeHtml(String.valueOf(questionLibraryList.size())));
					String scString = "";
					request.setAttribute("SearchCriteria", scString);
					request.setAttribute("ReturntoSysManageLink", "");				  
					String pageLib = "PageLibrary";
					request.setAttribute("PageLibrary",pageLib);
					//request.setAttribute("RefineSearchLink", "/nbs/SearchManageQuestions.do?method=refineSearch&identifier="+identifier);

			} catch (Exception e) {
				logger.error("Exception in searchQuestionsSubmit: " + e.getMessage());
				//SRTAdminUtil.handleErrors(e, request, "search", "");
				return (mapping.findForward("default"));
			} finally {
				request.setAttribute(SRTAdminConstants.PAGE_TITLE ,"Manage Questions: Question Library");
			}
				
		 	}catch (Exception e) {
				logger.error("Exception in Search Questions Load Lib: " + e.getMessage());
				e.printStackTrace();
				throw new ServletException("Error occurred in  Search Questions Load library : "+e.getMessage());
			} 				
				
			return PaginationUtil.paginate(manageQuestionsForm, request, "searchQuestionsResult",mapping);

		}
		
		
		@SuppressWarnings("unchecked")
		private Collection<Object>  filterQuestionLib(ManageQuestionsForm manageForm, HttpServletRequest request) {
			
			Collection<Object>  manageCondlist = new ArrayList<Object> ();
			
			String srchCriteriaType = null;
			String srchCriteriaStatus = null;
			String srchCriteriaGroup = null;
			String srchCriteriaSubgroup = null;
			String sortOrderParam = null;
			try {
				
				Map<Object, Object> searchCriteriaMap = manageForm.getSearchCriteriaArrayMap();
				ArrayList<Object> waQuestionDtColl = null;
				try {
				     waQuestionDtColl = (ArrayList<Object>)NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSManageListFull);
				}catch(Exception ex) {
					logger.debug(NBSConstantUtil.DSManageListFull +"  is null in filterQuestionLib");
					waQuestionDtColl = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true),  NBSConstantUtil.DSManageList);
				}
				
				manageCondlist = getFilteredCodesetLib(waQuestionDtColl, searchCriteriaMap);
				NBSContext.store(request.getSession(true) , NBSConstantUtil.DSManageList,manageCondlist);
				String[] type = (String[]) searchCriteriaMap.get("TYPE");
				String[] group = (String[]) searchCriteriaMap.get("GROUP");
				String[] subGroup = (String[]) searchCriteriaMap.get("SUBGROUP");
				String[] status = (String[]) searchCriteriaMap.get("STATUS");
				String filterByUniqueId = null;
				String filterByUniqueName = null;
				String filterByLabel = null;
				
				if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
					filterByUniqueId = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
				}
				if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
					filterByUniqueName = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
				}
				if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
					filterByLabel = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
				}

				Integer typeCount = new Integer(type == null ? 0 : type.length);
				Integer groupCount = new Integer(group == null ? 0 : group.length);
				Integer subGroupCount = new Integer(subGroup == null ? 0 : subGroup.length);
				Integer statusCount = new Integer(status == null ? 0 : status.length);
				

				// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
				if(typeCount.equals((manageForm.getAttributeMap().get("type"))) &&
						(statusCount.equals(manageForm.getAttributeMap().get("statusCount"))) && 
						groupCount.equals((manageForm.getAttributeMap().get("group"))) && 
						subGroupCount.equals((manageForm.getAttributeMap().get("subGroup"))) &&
						filterByUniqueId == null && filterByUniqueName == null && filterByLabel == null)
					 {
					
					String sortMethod = getSortMethod(request, manageForm);
					String direction = getSortDirection(request, manageForm);			
					if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
						Map<?,?> sColl =  manageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<?,?>) manageForm.getAttributeMap().get("searchCriteria");
						sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
					} else {
						sortOrderParam = PageManagementActionUtil.getSortCriteriaQuestion(direction, sortMethod);
					}				
					Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
					searchCriteriaColl.put("sortSt", sortOrderParam);
					manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
					return null;				
				}
				ArrayList<Object> typeList = manageForm.getType();
				ArrayList<Object> groupList = manageForm.getGroup();
				ArrayList<Object> subgroupList = manageForm.getSubGroup();
				ArrayList<Object> statusList = manageForm.getStatus();
				
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				String sortMethod = getSortMethod(request, manageForm);
				String direction = getSortDirection(request, manageForm);			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<Object, Object> sColl =  manageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<Object, Object>) manageForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = PageManagementActionUtil.getSortCriteriaQuestion(direction, sortMethod);
				}
				
				srchCriteriaType = queueUtil.getSearchCriteria(typeList, type, NEDSSConstants.FILTERBYSUBMITTEDBY);
				srchCriteriaGroup = queueUtil.getSearchCriteria(groupList, group, NEDSSConstants.FILTERBYSUBMITTEDBY);
				srchCriteriaSubgroup = queueUtil.getSearchCriteria(subgroupList, subGroup, NEDSSConstants.FILTERBYSUBMITTEDBY);
				srchCriteriaStatus = queueUtil.getSearchCriteria(statusList, status, NEDSSConstants.FILTERBYSUBMITTEDBY);
				

				
				//set the error message to the form
				if(sortOrderParam != null)
					searchCriteriaColl.put("sortSt", sortOrderParam);
				if(srchCriteriaType != null)
					searchCriteriaColl.put("INV111", srchCriteriaType);
				if(srchCriteriaGroup != null)
					searchCriteriaColl.put("INV222", srchCriteriaGroup);
				if(srchCriteriaSubgroup != null)
					searchCriteriaColl.put("INV333", srchCriteriaSubgroup);
				if(srchCriteriaStatus != null)
					searchCriteriaColl.put("INV444", srchCriteriaStatus);
				if(filterByUniqueId != null)
					searchCriteriaColl.put("INV001", filterByUniqueId);
				if(filterByUniqueName != null)
					searchCriteriaColl.put("INV002", filterByUniqueName);
				if(filterByLabel != null)
					searchCriteriaColl.put("INV003", filterByLabel);
				
				manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error while filtering the filterQuestionLib: "+ e.toString());
				
			} 
			return manageCondlist;
		}
		
		@SuppressWarnings("unchecked")
		public ActionForward filterQuestionLibSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

			ManageQuestionsForm manageForm  = (ManageQuestionsForm) form;
			Collection<Object>  waQuestionDtColl = filterQuestionLib(manageForm, request);
			if(waQuestionDtColl != null){
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
			//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
			}else{
				try {
				waQuestionDtColl = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), NBSConstantUtil.DSManageListFull);
				}catch(Exception ex) {
					logger.debug(NBSConstantUtil.DSManageListFull +"  is null in filterQuestionLibSubmit");
					waQuestionDtColl = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true),  NBSConstantUtil.DSManageList);
				}
			}
			
			//manageForm.getAttributeMap().put("manageList",waQuestionDtColl);
			//manageForm.getAttributeMap().put("queueList",waQuestionDtColl);
			String identifier = HTMLEncoder.encodeHtml((String)request.getParameter("identifier"));
			request.setAttribute("RefineSearchLink", "/nbs/SearchManageQuestions.do?method=refineSearch&identifier="+identifier);
			
			request.setAttribute("queueList", waQuestionDtColl);
			sortManageQuestions(manageForm, waQuestionDtColl, true, request);
			request.setAttribute("manageList", waQuestionDtColl);
			request.setAttribute("queueCount", String.valueOf(waQuestionDtColl.size()));
			manageForm.getAttributeMap().put("queueCount", String.valueOf(waQuestionDtColl.size()));
			request.setAttribute("PageTitle","Manage Questions: Question Library");
			manageForm.getAttributeMap().put("PageNumber", "1");

			return PaginationUtil.paginate(manageForm, request, "searchQuestionsResult",mapping);
			
		} 
		
		
		private void sortManageQuestions(ManageQuestionsForm manageForm, Collection<Object>  codesetList, boolean existing, HttpServletRequest request) throws Exception{

			// Retrieve sort-order and sort-direction from displaytag params
			String sortMethod = getSortMethod(request, manageForm);
			String direction = getSortDirection(request, manageForm);

			boolean invDirectionFlag = true;
			if (direction != null && direction.equals("2"))
				invDirectionFlag = false;
			
			NedssUtils util = new NedssUtils();
			
			//Read from properties file to determine default sort order
			if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				sortMethod = "getQuestionIdentifier";
				invDirectionFlag = true;
			}
			
			if (sortMethod != null && codesetList != null && codesetList.size() > 0) {
				//updateListBeforeSort(recFacilityList);
				util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) codesetList, invDirectionFlag);
				//updateListAfterSort(recFacilityList);
			}
			if(!existing) {
				//Finally put sort criteria in form
				String sortOrderParam = PageManagementActionUtil.getSortCriteriaQuestion(invDirectionFlag == true ? "1" : "2", sortMethod);
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			}
			}
		
		
		public Collection<Object>  getFilteredCodesetLib(Collection<Object>  waQuestionDTColl,
				Map<Object, Object> searchCriteriaMap) {
			
	    	String[] type = (String[]) searchCriteriaMap.get("TYPE");
	    	String[] group = (String[]) searchCriteriaMap.get("GROUP");
	    	String[] subgroup = (String[]) searchCriteriaMap.get("SUBGROUP");
			String[] status = (String[]) searchCriteriaMap.get("STATUS");
			String filterByUniqueId = null;
			String filterByUniqueName = null;
			String filterByLabel = null;
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				filterByUniqueId = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				filterByUniqueName = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
				filterByLabel = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
			}		
					
			Map<Object, Object> typeMap = new HashMap<Object,Object>();
			Map<Object, Object> groupMap = new HashMap<Object,Object>();
			Map<Object, Object> subgroupMap = new HashMap<Object,Object>();
			Map<Object, Object> statusMap = new HashMap<Object,Object>();
			
			if (type != null && type.length > 0)
				typeMap = queueUtil.getMapFromStringArray(type);
			
			if (group != null && group.length >0)
				groupMap = queueUtil.getMapFromStringArray(group);
			
			if (subgroup != null && subgroup.length >0)
				subgroupMap = queueUtil.getMapFromStringArray(subgroup);
			
			if (status != null && status.length >0)
				statusMap = queueUtil.getMapFromStringArray(status);
			
			
			if(typeMap != null && typeMap.size()>0)
				waQuestionDTColl = filterType(
						waQuestionDTColl, typeMap);
			if (statusMap != null && statusMap.size()>0)
				waQuestionDTColl = filterStatus(
						waQuestionDTColl, statusMap);
			if (groupMap != null && groupMap.size()>0)
				waQuestionDTColl = filterGroup(
						waQuestionDTColl, groupMap);
			if (subgroupMap != null && subgroupMap.size()>0)
				waQuestionDTColl = filterSubgroup(
						waQuestionDTColl, subgroupMap);
			
			if(filterByUniqueId!= null){
				waQuestionDTColl = filterByText(waQuestionDTColl, filterByUniqueId, NEDSSConstants.UNIQUE_ID);
			}
			if(filterByUniqueName!= null){
				waQuestionDTColl = filterByText(waQuestionDTColl, filterByUniqueName, NEDSSConstants.UNIQUE_NAME);
			}
			if(filterByLabel!= null){
				waQuestionDTColl = filterByText(waQuestionDTColl, filterByLabel, NEDSSConstants.LABEL);
			}			
			return waQuestionDTColl;
			
		}
		
		
		public Collection<Object>  filterByText(
				Collection<Object>  waQuestionDTColl, String filterByText,String column) {
			Collection<Object>  newTypeColl = new ArrayList<Object> ();
			try{
			if (waQuestionDTColl != null) {
				Iterator<Object> iter = waQuestionDTColl.iterator();
				while (iter.hasNext()) {
					WaQuestionDT dt = (WaQuestionDT) iter.next();
					if(column.equals(NEDSSConstants.UNIQUE_ID) && dt.getQuestionIdentifier() != null && dt.getQuestionIdentifier().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.UNIQUE_NAME) && dt.getQuestionNm()!= null && dt.getQuestionNm().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
					if(column.equals(NEDSSConstants.LABEL) && dt.getQuestionLabel()!= null && dt.getQuestionLabel().toUpperCase().contains(filterByText.toUpperCase())){
						newTypeColl.add(dt);
					}
				}
			}
			}catch(Exception ex){
				 logger.error("Error filtering the filterByText : "+ex.getMessage());
				 throw new NEDSSSystemException(ex.getMessage());
			}
			return newTypeColl;
		}		
		
		public Collection<Object>  filterType(
				Collection<Object>  waQuestionDTColl, Map<Object,Object> typeMap) {
			Collection<Object>  newTypeColl = new ArrayList<Object> ();
			if (waQuestionDTColl != null) {
				Iterator<Object> iter = waQuestionDTColl.iterator();
				while (iter.hasNext()) {
					WaQuestionDT dt = (WaQuestionDT) iter.next();
					if (dt.getQuestionType()!= null
							&& typeMap != null
							&& typeMap.containsKey(dt.getQuestionType())) {
						newTypeColl.add(dt);
					}
					if(dt.getQuestionType() == null || dt.getQuestionType().equals("")){
						if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newTypeColl.add(dt);
						}
					}
				}
			}
			return newTypeColl;
		}
		
		public Collection<Object>  filterStatus(
				Collection<Object>  waQuestionDTColl, Map<Object,Object> statusMap) {
			Collection<Object>  newStatusColl = new ArrayList<Object> ();
			if (waQuestionDTColl != null) {
				Iterator<Object> iter = waQuestionDTColl.iterator();
				while (iter.hasNext()) {
					WaQuestionDT dt = (WaQuestionDT) iter.next();
					if (dt.getRecordStatusCd() != null
							&& statusMap != null
							&& statusMap.containsKey(dt.getRecordStatusCd())) {
						newStatusColl.add(dt);
					}
					if(dt.getRecordStatusCd() == null || dt.getRecordStatusCd().equals("")){
						if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newStatusColl.add(dt);
						}
					}

				}
			}
			return newStatusColl;
		}
		
		public Collection<Object>  filterGroup(
				Collection<Object>  waQuestionDTColl, Map<Object,Object> groupMap) {
			Collection<Object>  newStatusColl = new ArrayList<Object> ();
			if (waQuestionDTColl != null) {
				Iterator<Object> iter = waQuestionDTColl.iterator();
				while (iter.hasNext()) {
					WaQuestionDT dt = (WaQuestionDT) iter.next();
					if (dt.getGroupDesc() != null
							&& groupMap != null
							&& groupMap.containsKey(dt.getGroupDesc())) {
						newStatusColl.add(dt);
					}
					if(dt.getGroupDesc() == null || dt.getGroupDesc().equals("")){
						if(groupMap != null && groupMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newStatusColl.add(dt);
						}
					}

				}
			}
			return newStatusColl;
		}
		
		public Collection<Object>  filterSubgroup(
				Collection<Object>  waQuestionDTColl, Map<Object,Object> subgroupMap) {
			Collection<Object>  newStatusColl = new ArrayList<Object> ();
			if (waQuestionDTColl != null) {
				Iterator<Object> iter = waQuestionDTColl.iterator();
				while (iter.hasNext()) {
					WaQuestionDT dt = (WaQuestionDT) iter.next();
					if (dt.getSubGroupDesc() != null
							&& subgroupMap != null
							&& subgroupMap.containsKey(dt.getSubGroupDesc())) {
						newStatusColl.add(dt);
					}
					if(dt.getSubGroupDesc() == null || dt.getSubGroupDesc().equals("")){
						if(subgroupMap != null && subgroupMap.containsKey(NEDSSConstants.BLANK_KEY)){
							newStatusColl.add(dt);
						}
					}

				}
			}
			return newStatusColl;
		}
		
		private String getSortMethod(HttpServletRequest request, ManageQuestionsForm manageForm ) {
			if (PaginationUtil._dtagAccessed(request)) {
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
		} else{
			return manageForm.getAttributeMap().get("methodName") == null ? null : (String) manageForm.getAttributeMap().get("methodName");
			}
			
		}
		private String getSortDirection(HttpServletRequest request, ManageQuestionsForm manageForm) {
			if (PaginationUtil._dtagAccessed(request)) {
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			} else{
				return manageForm.getAttributeMap().get("sortOrder") == null ? "1": (String) manageForm.getAttributeMap().get("sortOrder");
			}
			
		}
}