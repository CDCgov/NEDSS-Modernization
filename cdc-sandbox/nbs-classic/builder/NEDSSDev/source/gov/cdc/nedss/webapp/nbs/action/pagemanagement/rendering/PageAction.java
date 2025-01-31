	package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.pagecache.util.PagePublisher;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbscontext.NBSObjectStore;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCFieldRecordForm;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCLTBIForm;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCProviderForm;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CDCRVCTForm;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.CommonPDFPrintForm;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform.InterviewRecordForm;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.CompareUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.LabCommonUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.LabEditCommonUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.LabViewCommonUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageChangeConditionUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageTransferOwnershipUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.RVCT.RVCTStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.share.ShareCaseUtil;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants; 
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
 * Struts Action Class to handle new PageManagement Module and redirect to the appropriate Page
 * actionHandler based on the InvFormCd
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PageAction.java
 * Jan 04, 2010
 * @version 1.0
 * @updateAuthor Pradeep Kumar Sharma
 * @company Leidos
 * @updatedby : Pradeep Sharma
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 * 
 */
public class PageAction extends DispatchAction {

	static final LogUtils logger = new LogUtils(PageAction.class.getName());
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();



	/**
	 * Redirects to the appropriate load util based on the form code
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws
    IOException, ServletException  {
		String actionForward = "default";
		try {
			PageForm pageForm = (PageForm) form;
			pageForm.setBusinessObjectType(NEDSSConstants.CASE);
			pageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			pageForm.setGenericType(null);			
			pageForm.setBatchEntryMap(new HashMap<Object, ArrayList<BatchEntry>>());
			String mode=request.getParameter("mode");
			if(mode == null)
				//mode ="Create";
				mode =NEDSSConstants.CREATE_LOAD_ACTION;
				PagePublisher publisher = PagePublisher.getInstance();
				String invFormCd = "";
				// Copy the JSPs to the ear folder only when the server Restart is F
				String serverRestart = propertyUtil.getServerRestart();
				if (serverRestart != null && serverRestart.equalsIgnoreCase(NEDSSConstants.FALSE)){

					try {
						invFormCd = (String) NBSContext.retrieve(request.getSession(),NBSConstantUtil.DSInvestigationFormCd);
						logger.info("Try publishing the page from page create load");
						boolean success = publisher.publishPage(invFormCd);
						logger.info("Published the page from page create load :"+success);
					} catch (Exception e) {
						logger.fatal("Exception while publishing page:"+e.getMessage(), e);
						throw new ServletException("Error while copying from NEDSS to Temp Folder: "+e.getMessage(),e);
					}
				}

			

			_setRenderDir(request,invFormCd);
			
			String investigationType  = null;
			if(!mode.equals(NEDSSConstants.VIEW_LOAD_ACTION)){
				PageActProxyVO pageProxyVO= new PageActProxyVO();
				PageLoadUtil pageLoadUtil  = new PageLoadUtil();
				pageLoadUtil.createLoadUtil(pageForm, pageProxyVO, request);
				try{
	            	investigationType = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationType);
	            }catch(Exception ex){
	            	logger.error("Context exception related to co-infection " +ex.getMessage());
	            }

				String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
				if( (sCurrentTask.equals("CreateInvestigation10")  ||  sCurrentTask.equals("CreateInvestigation11")) && NBSContext.retrieve(request.getSession(),"DSDocumentUID")!=null){
					//do nothing
				}else{
					
					
					String programAreaCd = (String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationProgramArea);
					pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setProgAreaCd(programAreaCd);
					pageLoadUtil.setInvestigationInformationOnForm(pageForm, (PageProxyVO)pageProxyVO);
					
//					if(investigationType==null || !investigationType.equals(NEDSSConstants.INVESTIGATION_TYPE_COINF))
//						pageForm.setBatchEntryMap(new HashMap<Object,ArrayList<BatchEntry>>());
		        		//invFormCd = (String) NBSContext.retrieve(request.getSession(),NBSConstantUtil.DSInvestigationFormCd);
				}
				Map<Object,Object> subbatchMap = pageLoadUtil.findBatchRecords(invFormCd,request.getSession());
				 //pageForm.setSubSecStructureMap(batchMap);
	        	request.setAttribute("SubSecStructureMap", subbatchMap);
			    
			}else{
				pageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			}
			request.setAttribute("BatchSubSection", pageForm.getSubSecStructureMap());
			pageForm.setPageTitle("Add Investigation: "+CachedDropDowns.getConditionDesc((String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationCondition)),request);
		} catch (Exception e) {
			logger.fatal("Exception in PageAction.createLoad: " + e.getMessage(), e);
			throw new ServletException("Error while create load Page: "+e.getMessage(),e);
		}

		return (mapping.findForward(actionForward));
	}

	/**
	 * Redirects to the appropriate load util based on the form code
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
    public ActionForward createGenericLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        String actionForward = "default";
        String strContextAction = "";
        try
        {
            PageForm pageForm = (PageForm) form;
            String busObjType = request.getParameter("businessObjectType");
			pageForm.setGenericType(null);			

            if(busObjType== null) {
            	String contextAction = request.getParameter("ContextAction");
            	strContextAction = contextAction;
				if (contextAction.equalsIgnoreCase("AddLab")
						|| contextAction.equalsIgnoreCase("AddLabDataEntry")
						|| contextAction
								.equalsIgnoreCase("SubmitAndLoadLabDE1")) {
					busObjType = NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE;
					pageForm.setBusinessObjectType(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE);
					pageForm.setGenericType(NEDSSConstants.GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE);
					pageForm.setLabProgramAreaList(PageCreateHelper.getLabProgramAreaList(request));
					pageForm.setJurisdictionListWthUnknown(PageCreateHelper.getJurisdictionListWithUnknown(request));
				}	
            	
            }
            String formCd = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, busObjType);
            if (formCd == null || formCd.isEmpty())
            {
                throw new ServletException("No published form? For business object: " + busObjType);
            }
            
            PortPageUtil.blockPageAccessUntilConversionIsComplete(busObjType, request);
            
            pageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
            pageForm.setBusinessObjectType(busObjType);
            pageForm.setPageFormCd(formCd);
            // publish to the tmp dir if not there..
            PagePublisher publisher = PagePublisher.getInstance();
            // Copy the JSPs to the ear folder only when the server Restart is F
            String serverRestart = propertyUtil.getServerRestart();
            if (serverRestart != null && serverRestart.equalsIgnoreCase(NEDSSConstants.FALSE))
            {
                try
                {
                    logger.info("Try publishing the generic page from page create load");
                    boolean success = publisher.publishPage(formCd);
                    logger.info("Published the page from page create generic load :" + success);
                }
                catch (Exception e)
                {
                	logger.fatal("Exception while publishing page:"+e.getMessage(), e);
                    throw new ServletException("Error while copying generic page from NEDSS to Temp Folder: "
                            + e.getMessage(), e);
                }
            }

            String action = request.getParameter("Action");
            if(action!=null && action.equalsIgnoreCase("DSFilePath") ){
            	NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationPath, "DSFilePath");
            }
            PageLoadUtil pageLoadUtil = new PageLoadUtil();
            pageLoadUtil.createGenericLoadUtil(pageForm, request);

            Map<Object, Object> subbatchMap = pageLoadUtil.findBatchRecords(formCd, request.getSession());
            request.setAttribute("SubSecStructureMap", subbatchMap);
            request.setAttribute("BatchSubSection", pageForm.getSubSecStructureMap());
            _setRenderDir(request, formCd);
            if (busObjType.equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
            {
                pageForm.setPageTitle("Add " + NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_DESC, request);
            }

            else if (NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(busObjType))
            {
                pageForm.setPageTitle("Add " + NEDSSConstants.VACCINATION_BUSINESS_OBJECT_DESC, request);
            }
            else if (NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE.equals(busObjType))
            {
                pageForm.setPageTitle("Add " + NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE_DESC, request);
                LabCommonUtil.GenerateLinks(pageForm, request);
            } 
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.createGenericLoad: " + e.getMessage(), e);
            throw new ServletException("Error while create generic load Page: " + e.getMessage(), e);
        }
        
        if(strContextAction!=null && strContextAction.equalsIgnoreCase("SubmitAndLoadLabDE1")){//In case it is coming from Data Entry > Add Lab Report > Submit > Add another lab report
      		request.setAttribute("TabtoFocus","Patient");
      		 request.setAttribute("retainNextEntry","true");
        }
        
        return (mapping.findForward(actionForward));
    }
	
	
	/**
	 * createSubmit - Submit a new investigation
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws
    IOException, ServletException {

		String actionForward = "";
		PageProxyVO thisPageProxyVO = null;
		try {
			PageForm pageForm = (PageForm) form;
			String formCd = getInvFormCd(request, pageForm);
			
			if(formCd != null ) {
				String contextAction = request.getParameter("ContextAction");
				if (contextAction != null && contextAction.equalsIgnoreCase(NBSConstantUtil.SubmitNoViewAccess))
					actionForward = contextAction; //could be "SubmitNoViewAccess" if no permission in jurisdiction
				else
					actionForward = "insertDMBPam";
				//process the store investigation request
				thisPageProxyVO = 	PageStoreUtil.createHandler(pageForm, request);
			}
			// set the success messages
			if (formCd != null && formCd.trim().length() != 0 &&
					pageForm.getErrorList()== null || pageForm.getErrorList().size() <= 0) {
				ActionMessages messages = new ActionMessages();
				messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
						new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.INVESTIGATION_TEXT));
				request.setAttribute("success_messages", messages);
			}

			request.setAttribute("formCode", formCd);

		} catch (Exception e) {
			logger.fatal("Exception in PageAction.createSubmit: " + e.getMessage(), e);
			e.printStackTrace();
			throw new ServletException("Error while Saving Page Case: "+e.getMessage(),e);
		}

		return (mapping.findForward(actionForward));
	}

	/**
	 * createGenericSubmit - Submit generic page such as Interview
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
    public ActionForward createGenericSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        String actionForward = "";
        try
        {
            PageForm pageForm = (PageForm) form;
            String formCd = pageForm.getPageFormCd();
            //Fix ordered test NBS_LAB112 issue, not coming data into the answerMap
            String answers = request.getParameter("mapView");
            setAnswersFromViewInAnswerMap(answers, pageForm);
            
            if (NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE.equals(pageForm.getBusinessObjectType()))
            {
            	actionForward = request.getParameter("ContextAction");
            	LabCommonUtil labUtil= new LabCommonUtil();
            	labUtil.CreataLab(actionForward, mapping, form, request, response);
            }else 
            {
            	if (formCd != null)
            
	            {
	                actionForward = "insertDMBPam";
	                PageProxyVO pageProxyVO = PageStoreUtil.createGenericHandler(pageForm, request);
	                if (pageProxyVO != null)
	                    pageForm.getPageClientVO().setOldPageProxyVO(pageProxyVO); // set for  view
	            }
	            // set the success messages
	            if (formCd != null && formCd.trim().length() != 0 && pageForm.getErrorList() == null
	                    || pageForm.getErrorList().size() <= 0)
	            {
	                ActionMessages messages = new ActionMessages();
	                if (NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equals(pageForm.getBusinessObjectType()))
	                {
	                    messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY, new ActionMessage(
	                            NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.INTERVIEW_TEXT));
	                }
	                else if (NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(pageForm.getBusinessObjectType()))
	                {
	                    messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY, new ActionMessage(
	                            NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NEDSSConstants.VACCINATION_BUSINESS_OBJECT_DESC));
	                }
	                request.setAttribute("success_messages", messages);
	                actionForward = "view" + pageForm.getBusinessObjectType();
	            }
            }
            request.setAttribute("formCode", formCd);
        }
        catch (IOException e)
        {
        	logger.fatal("IOException in PageAction.createGenericSubmit: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while Saving Page Case: " + e.getMessage(), e);
        }
        catch (ServletException e)
        {
        	logger.fatal("ServletException in PageAction.createGenericSubmit: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while Saving Page Case: " + e.getMessage(), e);
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.createGenericSubmit: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while Saving Page Case: " + e.getMessage(), e);
        }
        return (mapping.findForward(actionForward));
    }
	
    /**
     * setAnswersFromViewInAnswerMap: set answers read from the screen into the AnswerMap, for example NBS_LAB112
     * @param answers
     * @param form
     */
    public void setAnswersFromViewInAnswerMap(String answers, PageForm form){
    	
    	Map<Object, Object> answerMap = form.getPageClientVO().getAnswerMap();
    	
    	if(answers!=null && answers!="" && !answers.equalsIgnoreCase("undefined")){
    		
    		String ans[] = answers.split("NEXTCODE");
    		
    		for(int i=0; ans!=null && i<ans.length; i++){
    			
    			String[] codeValue = ans[i].split("\\$A\\$");
    			
    			if(codeValue.length==2){
	    			String code = codeValue[0];
	    			String value = codeValue[1];
	    			
	    			answerMap.put(code, value);
    			}
    			else
    				System.out.println("");
    			
    		}
    		
    		
    	
    	
    	
    	}
    	
    }
	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException{

		String actionForward = "editDMB";
		try {
			PageForm pageForm = (PageForm) form;
			pageForm.setBusinessObjectType(NEDSSConstants.CASE);
			PageLoadUtil pageLoadUtil  = new PageLoadUtil();
			pageForm.setGenericType(null);
			pageLoadUtil.checkForChangeCondition(pageForm, request);
			//setting the required fields for notification:
			
			pageForm.retrieveRequiredFieldNotification(request);
			
			String formCd = getInvFormCd(request, pageForm);
			String survivor = request.getParameter("survivor");
			_setRenderDir(request,formCd);
			PageProxyVO proxyVO = null;
			if(formCd != null ) {
				pageForm.initializeForm(mapping, request);
				
				String sContextAction = request.getParameter("ContextAction");
				String phc2=null;
				if(sContextAction.equalsIgnoreCase("EditMerge"))
					phc2=(String) NBSContext.retrieve(request.getSession(),NBSConstantUtil.DSInvestigationUid1);
				
				proxyVO = pageLoadUtil.editLoadUtil(pageForm, request);
				
				PageClientVO pageClientVO1 = pageForm.getPageClientVO();
				Map<Object,Object> attributeMap1 = pageForm.getAttributeMap();
				Map<Object,Object> securityMap1 = pageForm.getSecurityMap();
				
				if(sContextAction.equalsIgnoreCase("EditMerge")){
					CompareUtil.editLoadUtil(pageForm, request, phc2);
					PageClientVO pageClientVO2 = pageForm.getPageClientVO();
					Map<Object,Object> attributeMap2 = pageForm.getAttributeMap2();
					Map<Object,Object> securityMap2 = pageForm.getSecurityMap();
					
					if(survivor!=null && survivor.equalsIgnoreCase("2")){
						pageForm.setPageClientVO(pageClientVO2);
						pageForm.setPageClientVO2(pageClientVO1);
						pageForm.setAttributeMap(attributeMap2);
						pageForm.setAttributeMap2(attributeMap1);
						pageForm.setSecurityMap(securityMap2);
						pageForm.setSecurityMap2(securityMap1);
						ArrayList<Object> counties2 = pageForm.getDwrImportedCounties2();
						pageForm.setDwrImportedCounties2(pageForm.getDwrImportedCounties());
						pageForm.setDwrImportedCounties(counties2);
						CompareUtil.updateRequestData(request);
					}
					else{
						pageForm.setPageClientVO(pageClientVO1);
						pageForm.setPageClientVO2(pageClientVO2);
						pageForm.setAttributeMap(attributeMap1);
						pageForm.setAttributeMap2(attributeMap2);
						pageForm.setSecurityMap(securityMap1);
						pageForm.setSecurityMap2(securityMap2);
					}
					
					/*Condition for showing above the header*/
					CachedDropDownValues cache = new CachedDropDownValues();
					String conditionCode = (String)attributeMap1.get("headerConditionCode");
					String condition = cache.getConditionDesc(conditionCode);
					request.setAttribute("condition", condition);
					pageForm.setActionMode("Merge");//For showing the JSP that are inside the compare folder
					
				}
				
					
			}

			//see if there are error fields that need to be highlighted
			//these are in a string separated by a space
			if (pageForm.getErrorFieldList() != null && !pageForm.getErrorFieldList().isEmpty()) {
				request.setAttribute(PageConstants.FIELD_LIST_TO_HILIGHT, pageForm.getErrorFieldList());
				pageForm.setErrorFieldList(null);
			} else request.removeAttribute(PageConstants.FIELD_LIST_TO_HILIGHT);
			request.setAttribute("formCode", formCd);
			
			if(survivor!=null && survivor.equalsIgnoreCase("2")){
				Map<Object, Object> map1 = pageForm.getSubSecStructureMap();
				Map<Object, Object> map2 = pageForm.getSubSecStructureMap2();

				Map<Object, Object> map2Modified = removeSuffixFromSubsectionIds(map2);//remove the _2
				Map<Object, Object> map1Modified = addSuffixToSubsectionIds(map1);//remove the _2
				
				request.setAttribute("SubSecStructureMap2", map1Modified);
				request.setAttribute("SubSecStructureMap", map2Modified);	
				
				Map<Object, ArrayList<BatchEntry>> batchEntryMap1 = pageForm.getBatchEntryMap();
				Map<Object, ArrayList<BatchEntry>> batchEntryMap2 = pageForm.getBatchEntryMap2();
				Map<Object, ArrayList<BatchEntry>> batchEntryMap1Modified = addSuffixToBatchEntries(batchEntryMap1);
				Map<Object, ArrayList<BatchEntry>> batchEntryMap2Modified = removeSuffixFromBatchEntry(batchEntryMap2);

				
				
				pageForm.setBatchEntryMap(batchEntryMap2Modified);
				pageForm.setBatchEntryMap2(batchEntryMap1Modified);
			}else{
				request.setAttribute("SubSecStructureMap", pageForm.getSubSecStructureMap());
				request.setAttribute("SubSecStructureMap2", pageForm.getSubSecStructureMap2());
			}
			if(proxyVO!=null) {
			PublicHealthCaseDT phcDT = ((PageActProxyVO) proxyVO)
					.getPublicHealthCaseVO().getThePublicHealthCaseDT();
			logger.debug("Page Action edit Load Case ID: "+phcDT.getLocalId());
			}
			if(pageForm.getPageClientVO()!=null && pageForm.getPageClientVO().getAnswerMap()!=null)
				logger.debug("Page Action edit Load Answer Map: "+pageForm.getPageClientVO().getAnswerMap().toString());
			if(pageForm.getPageClientVO()!=null && pageForm.getPageClientVO().getArrayAnswerMap()!=null)
				logger.debug("Page Action edit Load Answer Array Map: "+pageForm.getPageClientVO().getArrayAnswerMap().toString());
			
		} catch (Exception e) {
			logger.fatal("Exception in PageAction.editLoad: " + e.getMessage(), e);
			e.printStackTrace();
			throw new ServletException("Error while loading Edit Page Case: "+e.getMessage(),e);
		}
		return (mapping.findForward(actionForward));
	}

	public Map<Object, Object> removeSuffixFromSubsectionIds (Map<Object, Object> substructure){
		
		Map<Object, Object> map2Modified = new HashMap<Object, Object>();
		

		
		Iterator it = substructure.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
		       String key = (String)pair.getKey();
		       Object value = pair.getValue();

		       String newKey = key.substring(0,key.length()-2);//remove the _2
		       map2Modified.put(newKey, value);
		       
	    }
	        
		
		return map2Modified;
		
	}
	
public Map<Object, ArrayList<BatchEntry>> removeSuffixFromBatchEntry (Map<Object, ArrayList<BatchEntry>> substructure){
		
		Map<Object, ArrayList<BatchEntry>> map2Modified = new HashMap<Object, ArrayList<BatchEntry>>();
		

		
		Iterator it = substructure.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
		       String key = (String)pair.getKey();
		       ArrayList<BatchEntry> value = (ArrayList<BatchEntry>)pair.getValue();

		       String newKey = key.substring(0,key.length()-2);//remove the _2
		       map2Modified.put(newKey, value);
		       
	    }
	        
		
		return map2Modified;
		
	}


	public Map<Object, ArrayList<BatchEntry>> addSuffixToBatchEntries (Map<Object, ArrayList<BatchEntry>> substructure){
		
		Map<Object, ArrayList<BatchEntry>> mapModified = new HashMap<Object, ArrayList<BatchEntry>>();
		

		
		Iterator it = substructure.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
		       String key = (String)pair.getKey();
		       ArrayList<BatchEntry> value = (ArrayList<BatchEntry>)pair.getValue();

		       String newKey = key+"_2";//add suffix _2
		       mapModified.put(newKey, value);
		       
	    }
	        
		
		return mapModified;
		
	}
	
public Map<Object, Object> addSuffixToSubsectionIds (Map<Object, Object> substructure){
		
		Map<Object, Object> mapModified = new HashMap<Object, Object>();
		

		
		Iterator it = substructure.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
		       String key = (String)pair.getKey();
		       Object value = pair.getValue();

		       String newKey = key+"_2";//add suffix _2
		       mapModified.put(newKey, value);
		       
	    }
	        
		
		return mapModified;
		
	}
	
	/**
	 * editGenericLoad -actUid to edit and business object type passed in request
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
    public ActionForward editGenericLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        String actionForward = "editDMB";
        try
        {
            PageForm pageForm = (PageForm) form;
            String busObjType = request.getParameter("businessObjectType");
			pageForm.setGenericType(null);			
            
            if(busObjType==null) {
            	busObjType=NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE;
                pageForm.setBusinessObjectType(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE);
        		pageForm.setGenericType(NEDSSConstants.GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE);
        	}

			String formCd = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, busObjType);
            if (formCd == null || formCd.isEmpty())
            {
                throw new ServletException("No published form? For business object: " + busObjType);
            }
                        
            PortPageUtil.blockPageAccessUntilConversionIsComplete(busObjType, request);
            PageLoadUtil pageLoadUtil  = new PageLoadUtil();
    		Map<Object, Object> subbatchMap = pageLoadUtil.findBatchRecords(formCd, request.getSession());
            request.setAttribute("SubSecStructureMap", subbatchMap);
            request.setAttribute("BatchSubSection", pageForm.getSubSecStructureMap());
            
            pageForm.setBusinessObjectType(busObjType);
            pageForm.setPageFormCd(formCd);
            pageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			String contextAction = request.getParameter("ContextAction");
			
            
            if (busObjType.equalsIgnoreCase(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE)) {
                actionForward = "viewDynamicPage";
                LabEditCommonUtil labEditCommonUtil =new LabEditCommonUtil();
                labEditCommonUtil.editLabLoad(mapping, (PageForm)form, request,response);
    			//editLabLoad(ActionMapping mapping, PageForm pageForm, HttpServletRequest request,
    		    //        HttpServletResponse response) 
    			//LabCommonUtil.GenerateLinks(pageForm, request);
    			request.setAttribute("genericPageTitle", NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE_DESC);
                pageForm.setPageTitle("Edit " + NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE_DESC, request);
				pageForm.setLabProgramAreaList(PageCreateHelper.getLabProgramAreaList(request));
				pageForm.setJurisdictionListWthUnknown(PageCreateHelper.getJurisdictionListWithUnknown(request));
	            PageManagementCommonActionUtil.setTheRenderDirectory(request, formCd, busObjType);
	            request.setAttribute("formCode", formCd);

    			actionForward = "default";
        	}else
        	{
	            pageLoadUtil.editGenericLoadUtil(pageForm, request);    
	            // Set the Directory where the JSP for the form is
	            PageManagementCommonActionUtil.setTheRenderDirectory(request, formCd, busObjType);
	            request.setAttribute("formCode", formCd);
	            // see if there are error fields that need to be highlighted
	            // these are in a string separated by a space
	            if (pageForm.getErrorFieldList() != null && !pageForm.getErrorFieldList().isEmpty())
	            {
	                request.setAttribute(PageConstants.FIELD_LIST_TO_HILIGHT, pageForm.getErrorFieldList());
	                pageForm.setErrorFieldList(null);
	            }
	            else
	                request.removeAttribute(PageConstants.FIELD_LIST_TO_HILIGHT);
	            request.setAttribute("formCode", formCd);
	            request.setAttribute("SubSecStructureMap", pageForm.getSubSecStructureMap());
	            if (busObjType.equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
	            {
	                pageForm.setPageTitle("Edit " + NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_DESC, request);
	            }
	
	            else if (NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(busObjType))
	            {
	                pageForm.setPageTitle("Edit " + NEDSSConstants.VACCINATION_BUSINESS_OBJECT_DESC+pageForm.getAttributeMap().get(PageConstants.VACCINE_TYPE), request);
	            }
        	}
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.editGenericLoad: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while loading Generic Edit Page: " + e.getMessage(), e);
        }
        
        request.setAttribute("editMode","edit");//We need this value in order to make Reporting Facility read only on labs
        
        return (mapping.findForward(actionForward));
    }
	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	throws IOException, ServletException {
		String changeCondition=null;
		  String oldConditionDesc = null;
	      String newConditionDesc=null;
	      String actionForward = "";
		try {
        	
			HttpSession session = request.getSession();
			PageForm pageForm = (PageForm) form;
		
			String formCd = getInvFormCd(request, pageForm);

			if(formCd != null ) {
				actionForward = "editSubmit";
				 PublicHealthCaseVO phcVO = ((PageActProxyVO) pageForm.getPageClientVO().getOldPageProxyVO())
		                    .getPublicHealthCaseVO();
				newConditionDesc=phcVO.getThePublicHealthCaseDT().getCdDescTxt();
				
				 oldConditionDesc = (String) pageForm.getAttributeMap().get("oldConditionDesc");
				 if(oldConditionDesc == null || newConditionDesc == null || newConditionDesc.equals(oldConditionDesc)){
					 changeCondition="false";
				 }else{
					 changeCondition="true";
					 request.setAttribute("changeCondition",changeCondition);
					 NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
					 Long publicHealthCaseUid=phcVO.getThePublicHealthCaseDT().getUid();
					 PageChangeConditionUtil.addNewNoteForChangeCondition(session, nbsSecurityObj, publicHealthCaseUid, oldConditionDesc,  newConditionDesc);
				 }
				logger.debug("********#Change Condition :"+changeCondition); 
				
				PageStoreUtil.editHandler(pageForm, request);
				if(pageForm.getErrorList()!=null && pageForm.getErrorList().size()>0)
					actionForward = "editSubmit";
				
				 
				 //per Jit and Shujath, comment out next three lines..
				// PageProxyVO proxyVO=pageForm.getPageClientVO().getOldPageProxyVO();
				// PageLoadUtil.setContactInformation(pageForm, proxyVO, session);
				// String ProgramAreaCode=proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
			}
			//clear any edit error fields
			pageForm.setErrorFieldList(null);
			
			// set the success messages
			if(oldConditionDesc == null || newConditionDesc == null || newConditionDesc.equals(oldConditionDesc)){
				if (formCd != null && formCd.trim().length() != 0 &&
						pageForm.getErrorList()== null || pageForm.getErrorList().size() <= 0) {
					ActionMessages messages = new ActionMessages();
					messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
							new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.INVESTIGATION_TEXT));
					request.setAttribute("success_messages", messages);
				  }
				} 
			else{
				if (formCd != null && formCd.trim().length() != 0 &&
						pageForm.getErrorList()== null || pageForm.getErrorList().size() <= 0) {
					ActionMessages messages = new ActionMessages();
					messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
							new ActionMessage(NBSPageConstants.CHANGE_CONDITION_MESSAGE_KEY, oldConditionDesc, newConditionDesc));
					request.setAttribute("success_messages", messages);
				  }
			}
			pageForm.getAttributeMap().put("oldConditionDesc", null);
			request.setAttribute("formCode", formCd);
		}
		 catch (NEDSSAppConcurrentDataException ncde)
	      {
	        logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
	                     ncde);
	        return mapping.findForward("dataerror");
	      }catch (Exception e) {
	    	logger.fatal("Exception in PageAction.editSubmit: " + e.getMessage(), e);
			e.printStackTrace();
			throw new ServletException("Error while edit saving PAGE : "+e.getMessage(),e);
		}
		logger.debug("********#ActionForward :"+actionForward);
		return (mapping.findForward(actionForward));
	}

	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward mergeSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	throws IOException, ServletException {
		String changeCondition=null;
		  String oldConditionDesc = null;
	      String newConditionDesc=null;
	      String actionForward = "";
	      String localIdLosing=null, localIdWinning=null;
	      
		try {
			HttpSession session = request.getSession();
			PageForm pageForm = (PageForm) form;
			String formCd = getInvFormCd(request, pageForm);

			localIdLosing=(String)pageForm.getAttributeMap2().get("caseLocalId");
			localIdWinning=(String)pageForm.getAttributeMap().get("caseLocalId");
			
			if(formCd != null ) {
				actionForward = "mergeSubmit";
				PublicHealthCaseVO phcVO = ((PageActProxyVO) pageForm.getPageClientVO().getOldPageProxyVO())
	                    .getPublicHealthCaseVO();
				PublicHealthCaseVO phcVO2 = ((PageActProxyVO) pageForm.getPageClientVO2().getOldPageProxyVO())
	                    .getPublicHealthCaseVO();

				
				newConditionDesc=phcVO.getThePublicHealthCaseDT().getCdDescTxt();
				
				 oldConditionDesc = (String) pageForm.getAttributeMap().get("oldConditionDesc");
				 if(oldConditionDesc == null || newConditionDesc == null || newConditionDesc.equals(oldConditionDesc)){
					 changeCondition="false";
				 }else{
					 changeCondition="true";
					 request.setAttribute("changeCondition",changeCondition);
					 NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
					 Long publicHealthCaseUid=phcVO.getThePublicHealthCaseDT().getUid();
					 PageChangeConditionUtil.addNewNoteForChangeCondition(session, nbsSecurityObj, publicHealthCaseUid, oldConditionDesc,  newConditionDesc);
				 }
				 
				PageStoreUtil.mergeHandler(pageForm, request);
				
				if(pageForm.getErrorList()!=null && pageForm.getErrorList().size()>0)
					actionForward = "mergeSubmit";
				
			}
			//clear any edit error fields
			pageForm.setErrorFieldList(null);
			
			// set the success messages
			if(oldConditionDesc == null || newConditionDesc == null || newConditionDesc.equals(oldConditionDesc)){
				if (formCd != null && formCd.trim().length() != 0 &&
						pageForm.getErrorList()== null || pageForm.getErrorList().size() <= 0) {
					ActionMessages messages = new ActionMessages();
					messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
							new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.INVESTIGATION_TEXT));
					//request.setAttribute("success_messages", messages);
					
					String condition=newConditionDesc;
					request.setAttribute("success_merge", "Investigation for <b>"+condition+"</b> with Investigation ID <b>"+localIdLosing+"</b> has been successfully merged into <b>"+localIdWinning+"</b>.");
				  }
				}
			else{
				if (formCd != null && formCd.trim().length() != 0 &&
						pageForm.getErrorList()== null || pageForm.getErrorList().size() <= 0) {
					ActionMessages messages = new ActionMessages();
					messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
							new ActionMessage(NBSPageConstants.CHANGE_CONDITION_MESSAGE_KEY, oldConditionDesc, newConditionDesc));
				//	request.setAttribute("success_messages", messages);
					
					String condition=newConditionDesc;
					request.setAttribute("success_merge", "Investigation for <b>"+condition+"</b> with Investigation ID <b>"+localIdLosing+"</b> has been successfully merged into <b>"+localIdWinning+"</b>.");
				
				  }
			}
			pageForm.getAttributeMap().put("oldConditionDesc", null);
			request.setAttribute("formCode", formCd);
		}
		 catch (NEDSSAppConcurrentDataException ncde)
	      {
	        logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
	                     ncde);
	        return mapping.findForward("dataerror");
	      }catch (Exception e) {
	    	logger.fatal("Exception in PageAction.editSubmit: " + e.getMessage(), e);
			e.printStackTrace();
			throw new ServletException("Error while edit saving PAGE : "+e.getMessage(),e);
		}
		return (mapping.findForward(actionForward));
	}
	
	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	 public ActionForward editGenericSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        String actionForward = "";
        try
        {
            PageForm pageForm = (PageForm) form;
            String formCd = pageForm.getPageFormCd();

            
            //Fix ordered test NBS_LAB112 issue, not coming data into the answerMap
            String answers = request.getParameter("mapView");
            setAnswersFromViewInAnswerMap(answers, pageForm);
            
            
            String currTask = NBSContext.getCurrentTask(request.getSession());
            if (formCd != null)
            {
                actionForward = "view" + pageForm.getBusinessObjectType();
            }
            // clear any edit error fields
            pageForm.setErrorFieldList(null);
            
	  		String sContextAction = request.getParameter("ContextAction");
	        String busObjType = request.getParameter("businessObjectType");
            if (busObjType == null && pageForm.getBusinessObjectType() != null)
            {
                busObjType = pageForm.getBusinessObjectType(); 
            }
            if((sContextAction!=null && sContextAction.contains("ObservationLab")) ||
            		currTask!=null && currTask.contains("ObservationLab")) {
            	busObjType=NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE;
                pageForm.setBusinessObjectType(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE);
        		pageForm.setGenericType(NEDSSConstants.GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE);
        		LabEditCommonUtil LabEditCommonUtil  = new LabEditCommonUtil();
        		actionForward =LabEditCommonUtil.editSubmit(mapping, pageForm, request);
            }else {
            	if (formCd != null)
                {
                   PageStoreUtil.editGenericHandler(pageForm, request);
                }  
	            // set the success messages
	            if (formCd != null && formCd.trim().length() != 0 && pageForm.getErrorList() == null
	                    || pageForm.getErrorList().size() <= 0)
	            {
	                ActionMessages messages = new ActionMessages();
	                if (NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(pageForm.getBusinessObjectType()))
	                {
	                    messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY, new ActionMessage(
	                            NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.INTERVIEW_TEXT));
	                }
	                else if( NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(pageForm.getBusinessObjectType()))
	                {
	                    messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY, new ActionMessage(
	                            NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NEDSSConstants.VACCINATION_BUSINESS_OBJECT_DESC));   
	                }
	                request.setAttribute("success_messages", messages);
	                pageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
	                request.setAttribute("businessObjectType", pageForm.getBusinessObjectType());
	            }
            }
            request.setAttribute("formCode", formCd);
        }
        catch (NEDSSAppConcurrentDataException ncde)
        {
            logger.fatal(
                    "ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
                    ncde);
            return mapping.findForward("dataerror");
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.editGenericSubmit: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while edit saving Generic Page : " + e.getMessage(), e);
        }
        return (mapping.findForward(actionForward));
    }

	
	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	
	 
	public ActionForward viewLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
			throws IOException, ServletException {
				String phc2 = null;
				String phc1 = null;
				String actionForward = "";
				String contextAction = null;
				try {
					actionForward = "viewDMB";
					PageForm pageForm = (PageForm) form;//pageForm.getBatchEntryMap().put("NBS_INV_STD_UI_18", pageForm.getBatchEntryMap().get("ENTITYID100"))
					pageForm.setBusinessObjectType(NEDSSConstants.CASE);
					pageForm.setGenericType(null);			
					pageForm.setBatchEntryMap(new HashMap<Object, ArrayList<BatchEntry>>());
					contextAction = request.getParameter("ContextAction");
					
					String formCd = getInvFormCd(request, pageForm);

					if(contextAction!=null && (contextAction.equalsIgnoreCase("InvestigationIDOnSummary")
							|| contextAction.equalsIgnoreCase("InvestigationIDOnEvents") ||
							    contextAction.equalsIgnoreCase("InvestigationID")||
							    contextAction.equalsIgnoreCase("CompareInvestigations"))){
						try {
							PagePublisher publisher = PagePublisher.getInstance();
							boolean success = publisher.publishPage(formCd);
							logger.info("Published the page from page create load :"+success);
						} catch (Exception e) {
							e.printStackTrace();
							throw new ServletException("Error while copying from NEDSS to Temp Folder: "+e.getMessage(),e);
					}
					}


					if(contextAction!=null && contextAction.equals("Cancel"))
					{
						pageForm.getAttributeMap().remove("REQ_FOR_NOTIF");
						pageForm.getAttributeMap().remove("NotifReqMap");
					}
					boolean reqForNotif = pageForm.getAttributeMap().containsKey(PageConstants.REQ_FOR_NOTIF);
					String noReqForNotifCheck =null;
					if(pageForm.getAttributeMap().containsKey(PageConstants.NO_REQ_FOR_NOTIF_CHECK))
						 noReqForNotifCheck = (String)pageForm.getAttributeMap().get(PageConstants.NO_REQ_FOR_NOTIF_CHECK);

					
					request.setAttribute("mode", request.getParameter("mode"));
					
					if(contextAction!=null && contextAction.equals("CompareInvestigations")){
						phc2=(String) NBSContext.retrieve(request.getSession(),NBSConstantUtil.DSInvestigationUid1);
						
						phc1=(String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationUid);
						
						
						
						
					}
					PageLoadUtil pageLoadUtil = new PageLoadUtil();
					pageLoadUtil.viewLoadUtil(pageForm, request);//fatima pageForm.getFormFieldMap().remove("INV161")
					
					if(contextAction!=null && contextAction.equals("CompareInvestigations")){
						request.setAttribute("survivor",request.getParameter("survivor"));
						pageForm.setMode(request.getParameter("mode"));
						PageClientVO pageClientVO1 = pageForm.getPageClientVO();
						Map<Object,Object> attributeMap1 = pageForm.getAttributeMap();
						Map<Object,Object> securityMap1 = pageForm.getSecurityMap();
						
						CompareUtil.viewLoadUtil2(pageForm, request, phc2);
						
						PageClientVO pageClientVO2 = pageForm.getPageClientVO();
						Map<Object,Object> attributeMap2 = pageForm.getAttributeMap2();
						Map<Object,Object> securityMap2 = pageForm.getSecurityMap();

						/*Condition for showing above the header*/
						CachedDropDownValues cache = new CachedDropDownValues();
						String conditionCode = (String)attributeMap1.get("headerConditionCode");
						String condition = cache.getConditionDesc(conditionCode);
						request.setAttribute("condition", condition);

						
						pageForm.setPageClientVO(pageClientVO1);
						pageForm.setPageClientVO2(pageClientVO2);
						pageForm.setAttributeMap(attributeMap1);
						pageForm.setAttributeMap2(attributeMap2);
						pageForm.setSecurityMap(securityMap1);
						pageForm.setSecurityMap2(securityMap2);
						
						pageForm.setActionMode("Compare");//For showing the JSP that are inside the compare folder
					}
					
					PublicHealthCaseVO phcVO = ((PageActProxyVO)pageForm.getPageClientVO().getOldPageProxyVO()).getPublicHealthCaseVO();
					String conditionCd = phcVO.getThePublicHealthCaseDT().getCd();
					String conditionDesc = phcVO.getThePublicHealthCaseDT().getCdDescTxt();
					_setRenderDir(request, formCd);
					request.setAttribute("formCode", formCd);
					if (conditionDesc == null || conditionDesc.isEmpty())
						if (conditionCd != null && !conditionCd.isEmpty())
							conditionDesc = CachedDropDowns.getConditionDesc(conditionCd);
						else
							conditionDesc = CachedDropDowns.getConditionDesc(String.valueOf(request.getSession().getAttribute(NBSConstantUtil.DSInvestigationCondition)));
					if(contextAction!=null && contextAction.equals("CompareInvestigations"))
						pageForm.setPageTitle("Compare Investigations",request);
					else{
						pageForm.setPageTitle("View Investigation: "+conditionDesc,request);
				  	    request.setAttribute("PageTitle","View Investigation: "+conditionDesc);
					}
					request.setAttribute("conditiondesc", conditionDesc);
					
					if(contextAction!=null && contextAction.equals("CompareInvestigations")){
						request.setAttribute("SubSecStructureMap2", pageForm.getSubSecStructureMap2());
					}
						request.setAttribute("SubSecStructureMap", pageForm.getSubSecStructureMap());

					Map map = new HashMap();
					   //map.put("Condition",batchrec2);
					   // map.put("Disease Acquisition",batchrec1);
					  if(request.getAttribute("BatchSubSection") != null)
		                       map = (Map) request.getAttribute("BatchSubSection");

					if(reqForNotif == true && (noReqForNotifCheck != null && noReqForNotifCheck.equalsIgnoreCase("false")))
					{
						request.setAttribute("REQ_FOR_NOTIF", PageConstants.REQ_FOR_NOTIF);
					}
					logger.debug("Page Action view Load Case ID: "+phcVO.getThePublicHealthCaseDT().getLocalId());
					if(pageForm.getPageClientVO()!=null && pageForm.getPageClientVO().getAnswerMap()!=null)
						logger.debug("Page Action view Load Answer Map: "+pageForm.getPageClientVO().getAnswerMap().toString());
					if(pageForm.getPageClientVO()!=null && pageForm.getPageClientVO().getArrayAnswerMap()!=null)
						logger.debug("Page Action view Load for Case Answer Array Map: "+pageForm.getPageClientVO().getArrayAnswerMap().toString());

				} catch (Exception e) {
					logger.fatal("Exception in PageAction.viewLoad: " + e.getMessage(), e);
					e.printStackTrace();
					throw new ServletException("Error while loading PAGE: "+e.getMessage(),e);
				}

				if(contextAction!=null && contextAction.equals("CompareInvestigations")){
					NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationUid, phc1);
					NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationUid1, phc2);
				}
				
				
				if(contextAction==null)
					contextAction = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationPath);
				
				if(contextAction!=null && (contextAction.equalsIgnoreCase("ViewInvestigation3") || contextAction.equalsIgnoreCase("ViewInvestigation1"))){
					request.setAttribute("ContactTabtoFocus","ContactTabtoFocus");
					request.removeAttribute("TabtoFocusForGenericFlow");
				}
				
				//((PageForm)form).getPageClientVO().getOldPageProxyVO()
				return (mapping.findForward(actionForward));
			}
	
	
	
	/**
	 * viewGenericLoad - Dynamic page for generic pages such as Interview
	 * @param mapping
	 * @param form PageForm
	 * @param request
	 * @param response
	 * @return
	 */
    public ActionForward viewGenericLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {    	
        String actionForward = "";
        try 
        {         	
            actionForward = "viewDMB";
            PageForm pageForm = (PageForm) form;
            String busObjType = request.getParameter("businessObjectType");
			pageForm.setGenericType(null);			
			String currTask = NBSContext.getCurrentTask(request.getSession());

	  		String sContextAction = request.getParameter("ContextAction");
	  		String cancelAction = request.getParameter("Act");
    		
	  		if( cancelAction!=null && cancelAction.equals("LAB")) {
            	busObjType	=NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE;
            }else if (busObjType == null && pageForm.getBusinessObjectType() != null)
            {
                busObjType = pageForm.getBusinessObjectType(); // returning from
                                                               // create or edit
            }
            
            if(((sContextAction!=null) && 
            			(sContextAction.contains("ObservationLab") || 
            			sContextAction.contains("ViewLab") || 
            			currTask.contains("ObservationLab") || currTask.contains("AssociateToInvestigations")))
            	|| busObjType.equals(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE)) {
            	busObjType=NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE;
                pageForm.setBusinessObjectType(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE);
        		pageForm.setGenericType(NEDSSConstants.GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE);

            }
            
            
            PortPageUtil.blockPageAccessUntilConversionIsComplete(busObjType, request);
            
            String formCd = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, busObjType);
            
            if (formCd == null || formCd.isEmpty())
            {
                throw new ServletException("No published form? For business object: " + busObjType);
            }
            pageForm.setBusinessObjectType(busObjType);
            pageForm.setPageFormCd(formCd);
            pageForm.setActionMode("View");

            try
            {
                PagePublisher publisher = PagePublisher.getInstance();
                boolean success = publisher.publishPage(formCd);
                logger.info("Published the page from page create load :" + success);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new ServletException("Error while copying from NEDSS to Temp Folder: " + e.getMessage(), e);
            }
            
            String action = request.getParameter("Action");
            if(action!=null && action.equalsIgnoreCase("DSFilePath") ){
            	NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationPath, "DSFilePath");
            }
            
            PageLoadUtil pageLoadUtil =  new PageLoadUtil();
            //////////////Generic load begins
    		if(busObjType.equals(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE)) {    
    			
    			LabViewCommonUtil labViewCommonUtil =new LabViewCommonUtil();
    			labViewCommonUtil.viewGenericLoad(formCd, mapping, form, request,response);
    			
    			
    			request.setAttribute("genericPageTitle", NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE_DESC);
                pageForm.setPageTitle("View " + NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE_DESC, request);
                String contextAction = request.getParameter("ContextAction");
                if(contextAction!=null && contextAction.equalsIgnoreCase(NEDSSConstants.DELETE_DENIED_PAGE))
                	actionForward="XSP";
                else
                	actionForward = "default";
    		}///////////////Generic load ends
    		else {
	            request.setAttribute("mode", request.getParameter("mode"));
	
	            pageLoadUtil.viewGenericLoadUtil(pageForm, request);
	            // Set the Directory where the JSP for the form is
	            PageManagementCommonActionUtil.setTheRenderDirectory(request, formCd, busObjType);
	            request.setAttribute("formCode", formCd);
	            if (busObjType.equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
	            {
	                request.setAttribute("genericPageTitle", NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_DESC);
	                pageForm.setPageTitle("View " + NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_DESC, request);
	            }
	            else if (NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(busObjType))
	            { 
	                request.setAttribute("genericPageTitle", NEDSSConstants.VACCINATION_BUSINESS_OBJECT_DESC);
	                pageForm.setPageTitle("View " + NEDSSConstants.VACCINATION_BUSINESS_OBJECT_DESC+pageForm.getAttributeMap().get(PageConstants.VACCINE_TYPE), request);
	                
	                //Delete is not allowed from supplemental info tab, so setting delete permission to false.
	                PageLoadUtil.overrridePermissionToHideButton(pageForm, "ParentWindow", "SupplementalInfo", "deleteGenericPermission", "ParentWindowContext", request);
	            }
	
	            request.setAttribute("SubSecStructureMap", pageForm.getSubSecStructureMap());
    		}
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.viewGenericLoad: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while loading Generic Page: " + e.getMessage(), e);
        }
        return (mapping.findForward(actionForward));
    }
	
	
	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

		try {
			PamForm pamForm = (PamForm) form;
			String formCd = InvestigationUtil.getInvFormCd(request, pamForm);
			//Tuberculosis
			if(formCd != null && formCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)) {
				RVCTStoreUtil.viewHandler(pamForm, request);
			}
			request.setAttribute("formCode", formCd);
		} catch (Exception e) {
			logger.fatal("Exception in PageAction.viewSubmit: " + e.getMessage(), e);
			e.printStackTrace();
			throw new ServletException("Error while view Submitting Page Case: "+e.getMessage(),e);
		}

		String sContextAction = request.getParameter("ContextAction");
		return (mapping.findForward(sContextAction));
	}


	public ActionForward viewGenericSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		ActionForward af = null;
		try {
			LabViewCommonUtil labViewCommonUtil = new LabViewCommonUtil();
			af= labViewCommonUtil.viewGenericSubmit(mapping, form, request, response);
		} catch (Exception e) {
			logger.fatal("Exception in PageAction.viewSubmit: " + e.getMessage(), e);
			e.printStackTrace();
			throw new ServletException("Error while view Submitting Page Case: "+e.getMessage(),e);
		}
		return af;
	}


    public ActionForward printSelectedLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        try
        {
logger.debug("printSelectedLoad");
            PageForm pageForm = (PageForm) form;
            String formCd = getInvFormCd(request, pageForm);
            CommonPDFPrintForm.loadQuestionMap(formCd);
            String contactFormCd = PageManagementCommonActionUtil.checkIfPublishedPageExists(request,
                    NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE);
            if (contactFormCd != null)
            	CommonPDFPrintForm.loadContactQuestionMap(contactFormCd);
            String interviewFormCd = PageManagementCommonActionUtil.checkIfPublishedPageExists(request,
                    NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE);
            if (interviewFormCd != null)
            	CommonPDFPrintForm.loadInterviewQuestionMap(interviewFormCd);
            
            if(pageForm.getFormName()!=null && pageForm.getFormName().equals(NEDSSConstants.CDC_PROVIDER_FORM))
            	CDCProviderForm.printForm(pageForm, request, response,pageForm.getFormContent());
            else if(pageForm.getFormName()!=null && pageForm.getFormName().equals(NEDSSConstants.CDC_FIELD_RECORD_FORM))
            	CDCFieldRecordForm.printForm(pageForm, request, response,pageForm.getFormContent());
            else if(pageForm.getFormName()!=null && pageForm.getFormName().equals(NEDSSConstants.CDC_INTERVIEW_RECORD_FORM))
            	InterviewRecordForm.printForm(pageForm, request, response,pageForm.getFormContent());
            else if(pageForm.getFormName()!=null && pageForm.getFormName().equals(NEDSSConstants.CDC_RVCT_FORM))
            	CDCRVCTForm.printForm(pageForm, request, response,pageForm.getFormContent());
            else if(pageForm.getFormName()!=null && pageForm.getFormName().equals(NEDSSConstants.CDC_TBLISS_FORM))
            	CDCLTBIForm.printForm(pageForm, request, response,pageForm.getFormContent());
            else{
            	logger.error("Not Expected: The print form is not yet supported for :"+pageForm.getFormName());
            }
            }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.printSelectedLoad: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while loading printLoad Page Case: " + e.getMessage(), e);
        }
        return null;
    }
    

	
    public ActionForward printLoadFormPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
    	 PageForm pageForm = (PageForm) form;
    	 String formType =  (String)pageForm.getSecurityMap().get("printCDCFRFormType");
         String formName = pageForm.getFormName();
         
        
          if((formName!=null && formName.equalsIgnoreCase("RVCT"))|| (formType != null && formType.equalsIgnoreCase("TB")))
          	pageForm.setFormName("RVCT");
          else
        	  if((formName!=null && formName.equalsIgnoreCase("TBLISS"))|| (formType != null && formType.equalsIgnoreCase("LTBI")))
                	pageForm.setFormName("TBLISS");
          else
          	pageForm.setFormName("");//Only empty the dropdown if it is not a TB page. TB page, for now, it is using a read only field.
        
    
        pageForm.setFormContent(NEDSSConstants.CDC_FILLED);
        
        try
        {
            Map<String, String> coinfectionMap = CommonPDFPrintForm.getCoinfectionInvestigations(request);
            if (!coinfectionMap.isEmpty())
            {
                coinfectionMap.put("0", "No other condition");
            }
            pageForm.setCoinfectionCondMap(coinfectionMap);
            pageForm.setCoinfCondInvUid("0");
        }
        catch (NEDSSAppException e)
        {
            logger.error("NBS Error in printLoadFormPage getting coinfection collection :" + e.getMessage());
        }
        catch (Exception e)
        {
            logger.error("Error  in printLoadFormPagegetting coinfection collection :" + e.getMessage());
        }
        return (mapping.findForward("printCDCForms"));
    }

	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
    public ActionForward deleteSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        ActionForward forward;
        try
        {
            PageForm pageForm = (PageForm) form;
            forward = deletePage(mapping, pageForm, request, response);
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.deleteSubmit: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while View Delete Submit of Case: " + e.getMessage(), e);
        }
        return forward;
    }
	/**
	 * Note: Prior to NBS 5.0 we were calling PamDeleteUtil.deletePam(mapping, pamForm, request, response);
	 * We reworked this logic and had to add struts forwards for "DeleteDenied", "FileSummary" and "ReturnToFileSummary" 
	 * in a number of places.
	 * @param mapping
	 * @param pageForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward deletePage(ActionMapping mapping, PageForm pageForm,
			HttpServletRequest request, HttpServletResponse response) {
		
			Map<Object,Object> returnMap = new HashMap<Object,Object>();
			String deleteString = "";
			String sPublicHealthCaseUID = "";
		    String sContextAction = request.getParameter("ContextAction");
	        ActionForward forwardNew = new ActionForward();
		     if (sContextAction.equalsIgnoreCase("FileSummary") || sContextAction.equalsIgnoreCase("ReturnToFileSummary"))
			         NBSContext.store(request.getSession(), NBSConstantUtil.DSFileTab, "3");
			else
					 NBSContext.store(request.getSession(), NBSConstantUtil.DSFileTab, "1");
		     
	        try {
				sPublicHealthCaseUID = (String) NBSContext.retrieve(request.getSession(),	NBSConstantUtil.DSInvestigationUid);
				returnMap = PageManagementCommonActionUtil.deleteProxyObject(sPublicHealthCaseUID, NEDSSConstants.CASE, request.getSession());

				Integer labCount = (Integer)returnMap.get(NEDSSConstants.LAB_REPORT);
				logger.debug(" PamDeleteUtil.deleteProxyObject:  labCount returned from PamProxyProxyEJB is "+ labCount);
				if(labCount==null || labCount.intValue()<0)
					labCount = new Integer(0);
				Integer morbCount = (Integer)returnMap.get(NEDSSConstants.MORBIDITY_REPORT);
				logger.debug(" PageAction.deletePage:  morbCount returned from PamProxyProxyEJB is "+ morbCount);
				if(morbCount==null || morbCount.intValue()<0)
					morbCount = new Integer(0);
				Integer vaccCount = (Integer)returnMap.get(NEDSSConstants.AR_TYPE_CODE);
				logger.debug(" PageAction.deletePage:  vaccCount returned from PamProxyProxyEJB is "+ vaccCount);
				if(vaccCount==null || vaccCount.intValue()<0)
					vaccCount = new Integer(0);
				Integer documentCount = (Integer)returnMap.get(NEDSSConstants.DocToPHC);
				logger.debug(" PageAction.deletePage:  documentCount returned from PamProxyProxyEJB is "+ documentCount);
				if(documentCount==null || documentCount.intValue()<0)
					documentCount = new Integer(0);				
				String securityException = "";
				if(returnMap.get(NEDSSConstants.SECURITY_FAIL)!=null)
					securityException = (String)returnMap.get(NEDSSConstants.SECURITY_FAIL);
				if(securityException.equalsIgnoreCase(NEDSSConstants.SECURITY_FAIL) )
					throw new Exception("Security Exception happens as securityException is not empty "+ securityException);

				if(labCount.intValue()>0 || morbCount.intValue()>0 || vaccCount.intValue()>0 || documentCount.intValue()>0)
				{
					deleteString="You cannot Delete this Investigation as there is:\r\n\r\n " +
					labCount+ " Associated Lab Report(s)\r\n "
					+morbCount+ " Associated Morbidity Report(s)\r\n "
					+vaccCount+ " Associated Vaccination(s)\r\n "
					+documentCount+ " Associated Document(s)\r\n\r\n "
					+"Disassociate the Event(s) and try again. \r\n Note: You can only disassociate Events you have access to disassociate.";
				}
			} catch (NEDSSAppConcurrentDataException ncde) {
				logger.error("Concurrent access occurred in DeletePage : "	+ ncde.getMessage());
			}  catch (Exception exp) {
				logger.error("Exception in DeletePage: " +exp.getMessage());
			}
	        
			 if(deleteString!=null && !deleteString.trim().equalsIgnoreCase("") ){
				 NBSContext.store(request.getSession(), NBSConstantUtil.DSRejectedDeleteString, deleteString);
				 ActionForward af = mapping.findForward("DeleteDenied");
				 StringBuffer strURL = new StringBuffer(af.getPath());
				 strURL.append("?ContextAction=DeleteDenied&publicHealthCaseUID==").
                  append(sPublicHealthCaseUID);
				 forwardNew.setPath(strURL.toString());
				 forwardNew.setRedirect(true);
				 return forwardNew;
    	  }
	      if (mapping.findForward(sContextAction) == null) 
	    	  logger.error("DeletePage: Error -> Struts Forward Path not found for "+ sContextAction);
	      return mapping.findForward(sContextAction);
	}

	/**
	 * deleteGenericSubmit - delete Generic item such as Interview
	 * @param mapping
	 * @param form PageForm
	 * @param request
	 * @param response
	 */
    public ActionForward deleteGenericSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        try
        {
            PageForm pageForm = (PageForm) form;
            String busObjectType = pageForm.getBusinessObjectType();
            PageActProxyVO oldProxyVO = (PageActProxyVO) pageForm.getPageClientVO().getOldPageProxyVO();
            Map<Object,Object> returnMap = new HashMap<Object,Object>();
            String deleteString = "";
            
            if (busObjectType.equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
            {
                String actUidStr = request.getParameter("actUid"); // passed attribute
                Long interviewUid = oldProxyVO.getInterviewVO().getTheInterviewDT()
                        .getUid();
                if (actUidStr.equals(interviewUid.toString())) // failsafe
                    logger.error("Error deleting unknown - form and attribute uid differ " + actUidStr + " "
                            + interviewUid.toString());
                PageManagementCommonActionUtil.deleteProxyObject(actUidStr, busObjectType, request.getSession());
            } 
            else if (busObjectType.equals(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE))
            {
                String actUidStr = request.getParameter("actUid"); // passed attribute
                Long interventionUid = oldProxyVO.getInterventionVO().getTheInterventionDT().getUid();
                if (actUidStr.equals(interventionUid.toString())) // failsafe
                    logger.error("Error deleting unknown - form and attribute uid differ " + actUidStr + " "
                            + interventionUid.toString());
                returnMap = PageManagementCommonActionUtil.deleteProxyObject(actUidStr, busObjectType, request.getSession());
            }
            else
            {
                logger.error("Error deleting unknown business object " + busObjectType);
            }
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.deleteGenericSubmit: " + e.getMessage(), e);
            throw new ServletException("Error while view deleteGenericSubmit of Page Window: " + e.getMessage(), e);
        }
        return (closeWindow(mapping, form, request, response));
    }
	
	public ActionForward transferOwnershipLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException 
	{
        PageForm pageForm = (PageForm) form;
        try
        {
            PageTransferOwnershipUtil.loadTransferOwnership(pageForm, request);
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.transferOwnershipLoad: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while loading transferOwnership for Page Case: " + e.getMessage(), e);
        }
        return (mapping.findForward("transferOwnership"));
	}
	
    public ActionForward sharePageCaseLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        PageForm pageForm = (PageForm) form;
        try
        {
            ShareCaseUtil.loadSharePageCase(pageForm, request);
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.sharePageCaseLoad: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while loading sharePageCaseLoad  for PAM Case: " + e.getMessage(), e);
        }
        return (mapping.findForward("shareCaseReport"));
    }
	
    public ActionForward sharePageCaseSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        ActionForward forward;
        PageForm pageForm = (PageForm) form;
        try
        {
            ShareCaseUtil util = new ShareCaseUtil();
            forward = util.shareCasePageSubmit(mapping, pageForm, request, response);
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.sharePageCaseSubmit: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while loading shareCasePageSubmit for PAM Case: " + e.getMessage(), e);
        }
        return forward;
    }

    public ActionForward transferOwnershipSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        PageForm pageForm = (PageForm) form;
        ActionForward forward;
        try
        {
            forward = PageTransferOwnershipUtil.storeTransferOwnership(mapping, pageForm, request, response);
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.transferOwnershipSubmit: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while transferOwnership for PAGE Case: " + e.getMessage(), e);
        }
        return forward;
    }

	private static String getInvFormCd(HttpServletRequest req, PageForm form) {
		HttpSession session = req.getSession();
		String investigationFormCd = null;
		try {
			String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			String programArea = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
			CachedDropDownValues cdv = new CachedDropDownValues();
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", conditionCd);
			if(programAreaVO == null) //level 2 condition for Hepatitis Diagnosis
				   programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", 2, conditionCd);
			investigationFormCd = programAreaVO.getInvestigationFormCd();

		} catch (Exception e) {

			try {
				investigationFormCd = (String) NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationFormCd);
			} catch (Exception e1) {
				logger.info("INV FORM CD is not present in Context: " + e.toString());
			}
		}
		if(investigationFormCd == null)
			investigationFormCd =  form.getPageFormCd();
		else
			form.setPageFormCd(investigationFormCd);
		//Log
		if(investigationFormCd == null || (investigationFormCd != null && investigationFormCd.equals(""))) {
			logger.error("Error while retrieving investigationFormCd from Context / PamForm: ");
		}
		return investigationFormCd;

	}

    private void _setRenderDir(HttpServletRequest request)
    {
        String conditionCd = "";
        String invFormCd = "";
        String renderDir = "";

        try
        {
            invFormCd = String.valueOf(NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationCode));
            conditionCd = String.valueOf(NBSContext.retrieve(request.getSession(),
                    NBSConstantUtil.DSInvestigationCondition));
        }
        catch (Exception e)
        {
            invFormCd = String.valueOf(request.getSession().getAttribute(NBSConstantUtil.DSInvestigationCode));
            conditionCd = String.valueOf(request.getSession().getAttribute(NBSConstantUtil.DSInvestigationCondition));
        }
        if (!invFormCd.equals(""))
        {
            renderDir = invFormCd;
            request.setAttribute("renderDir", renderDir);
            request.getSession().setAttribute(NBSConstantUtil.DSInvestigationCode, invFormCd);
            request.getSession().setAttribute(NBSConstantUtil.DSInvestigationCondition, conditionCd);
        }
    }

    private void _setRenderDir(HttpServletRequest request, String pageFormCd)
    {
        String renderDir = pageFormCd;
        logger.debug("Setting render dir to: " + renderDir);
        request.setAttribute("renderDir", renderDir);
    }

    public ActionForward changeConditionLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        PageForm pageForm = (PageForm) form;
        try
        {
            PageChangeConditionUtil.loadChangeCondition(pageForm, request);
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.changeConditionLoad: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while loading changeCondition for Page Case: " + e.getMessage(), e);
        }
        return (mapping.findForward("changeCondition"));
    }

    public void changeConditionSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        PageForm pageForm = (PageForm) form;
        //Hard coded flag to change condition and retain the case as per requirements 
        Boolean changeConditionInNewCase=false;
        try
        {
        	if(changeConditionInNewCase==true){
        		PageChangeConditionUtil.storeChangeConditionInNewCase(mapping, pageForm, request, response);
        	}
        	else{
        		PageChangeConditionUtil.storeChangeConditionRetainSameCase(mapping, pageForm, request, response);
        	}
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.changeConditionSubmit: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("Error while change condition submit for PAGE Case: " + e.getMessage(), e);
        }
        
    }

    public ActionForward printNotes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException, Exception
    {
        PageForm pageForm = (PageForm) form;
        try
        {
        	PageLoadUtil pageLoadUtil =  new PageLoadUtil();
        	pageLoadUtil.printNotes(pageForm, request, response);
        }
        catch (Exception e)
        {
        	logger.fatal("Exception in PageAction.printNotes: " + e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
        return (mapping.findForward("printNotes"));
    }

    public ActionForward createNotification(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException, Exception
    {

        PageForm pageForm = (PageForm) form;
        ArrayList<Object> alist = pageForm.validateNotificationReqFields(request);
        request.setAttribute("NotificationReqFieldsValList", alist);
        return (mapping.findForward("createNotification"));
    }

    /**
     * closeWindow - cancel and end operation for generic pages such as
     * Interview Forwards to what is in DSInvestigationPath.
     * 
     * @param mapping
     * @param form
     *            PageForm
     * @param request
     * @param response
     * @return
     */
    public ActionForward closeWindow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        String actionForward = "";
        try
        {
            actionForward = (String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationPath);
            PageForm pageForm = (PageForm) form;
            // gst do we need this?
            request.setAttribute("formCode", pageForm.getPageFormCd()); 
            if(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equals(pageForm.getBusinessObjectType())){
            	request.setAttribute("ContactTabtoFocus", "ContactTabtoFocus");
            }else{
            	request.setAttribute("TabtoFocusForGenericFlow", "TabtoFocusForGenericFlow");
            }
            
            String invPath = (String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationPath);
            if (invPath.equalsIgnoreCase("DSFilePath"))
            {
                actionForward = "ViewFile";
            }
            else
            {
            	
                String strPhcLocalUID = (String) NBSContext.retrieve(request.getSession(),
                        NBSConstantUtil.DSInvestigationLocalID);
                request.setAttribute("phcUID", strPhcLocalUID);
            }
            
            NBSContext.store(request.getSession(), NBSConstantUtil.DSFileTab, "2");
            
            //Remove ParentWindow from NBSObjectStore, it used to hide delete button in case of vaccination.
            NBSObjectStore NBSObjectStore = (NBSObjectStore)request.getSession().getAttribute(NBSConstantUtil.OBJECT_STORE);
            Object obj = NBSObjectStore.remove("ParentWindow");
            obj = null;
        }
        catch (Exception e)
        {
            logger.fatal("Error while loading closeWindow: " + e.toString(),e);
            throw new ServletException("Error while Closing Generic Window: " + e.getMessage(), e);
        }
        return (mapping.findForward(actionForward));
    }
}