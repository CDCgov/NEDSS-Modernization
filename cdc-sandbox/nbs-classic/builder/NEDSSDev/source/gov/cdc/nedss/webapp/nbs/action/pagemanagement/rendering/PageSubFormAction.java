package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering;

import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.pagemanagement.pagecache.util.PagePublisher;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.LabEditCommonUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.LabViewCommonUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageSubFormLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.page.PageSubForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

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

/**
 * Struts Action Class to handle new PageSubForm and redirect to the appropriate Page
 * actionHandler based on the InvFormCd
 * @author Fatima Lopez Calzado
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: GDIT</p>
 * PageSubAction.java
 * November 5, 2018
 * @version 1.0
 * 
 */
public class PageSubFormAction extends DispatchAction {

	static final LogUtils logger = new LogUtils(PageSubFormAction.class.getName());
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();

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
        String currentMode="";
        try
        {
        	String currentKey = request.getParameter(NEDSSConstants.CURRENT_KEY);
        	if(null != request.getParameter(NEDSSConstants.CURRENT_BATCH_ENTRY_MODE))
        	   currentMode = HTMLEncoder.encodeHtml(request.getParameter(NEDSSConstants.CURRENT_BATCH_ENTRY_MODE));
        	
       // 	String electronicInd = request.getParameter("electronicInd");
        	
        		
        	if(currentKey!=null)
        		request.getSession().setAttribute(NEDSSConstants.CURRENT_KEY,currentKey);
        	
             
 			HashMap<String, Object> subFormHashMap = (HashMap<String, Object>) request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP);
        	
 			PageSubForm pageSubForm = null;
 			
 			if(subFormHashMap!=null)
 				pageSubForm = (PageSubForm)subFormHashMap.get(currentKey);
 			
        	 PageSubForm pageForm = null;;
        	 
        	 pageForm = (PageSubForm) form;
        	 
        	if(pageSubForm!=null)//already exists
        		copyValuesFromPageSubFormToForm(pageSubForm, pageForm);
        	
        	
        	//Electronic indicator = Y, N (Do I need to add E??) It is coming from the E indicator in View Lab Report. Unable to read it from the backend so far, the LabReportProxy is empty.
        	//pageForm.getFormFieldMap().put("NBS455", electronicInd);
        	//pageForm.getPageClientVO().getAnswerMap().put("NBS455", electronicInd);
        	//If Susceptibily/Isolate tracking buttons are clicked without clicking on any
        	//View icon from the Resulted Test Batch Entry, the mode is empty, and if the lab report is on view mode
        	//then the subform is opened on view mode instead of create.
       // 	if(currentMode==null || currentMode.equalsIgnoreCase(""))
       // 		if(pageForm.getActionMode()!=null && pageForm.getActionMode().equalsIgnoreCase("View"))
        //			currentMode="View";
        
        	
         //   String busObjType = request.getParameter("businessObjectType");
            String formCdfromJSP = HTMLEncoder.encodeHtml(request.getParameter("formCd"));
            
            //Change this action mode in order to see the page in view or edit mode
            ((PageSubForm)form).setActionMode("Create");
        
        //    ((PageSubForm)form).getPageClientVO().setAnswer("NBS_LAB335","222222222222");
         //   ((PageSubForm)form).getPageClientVO().setAnswer("NBS_LAB331","Y");
            
            
			pageForm.setGenericType(null);			
		
           /* if(busObjType== null) {
            	String contextAction = request.getParameter("ContextAction");
            	if (contextAction.equalsIgnoreCase("AddLab")) {
            		busObjType=NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE;
            		pageForm.setBusinessObjectType(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE);
            		pageForm.setGenericType(NEDSSConstants.GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE);
            	}		
            	
            }*/
           // String formCd = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, busObjType);
            
            
            /*if (formCd == null || formCd.isEmpty())
            {
                throw new ServletException("No published form? For business object: " + busObjType);
            }*/
            
            
            String formCd = formCdfromJSP;
            String busObjType=NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE;//TODO: Once I use the real form_cd name, remove this default value
            if(formCd!=null && formCd.toLowerCase().indexOf("sus")!=-1)
                busObjType=NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE;
            if(formCd!=null && formCd.toLowerCase().indexOf("iso")!=-1)
                busObjType=NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE;

            request.setAttribute("businessObjectType",busObjType);
            
            pageForm.setBusinessObjectType(busObjType);
            PortPageUtil.blockPageAccessUntilConversionIsComplete(busObjType, request);
            
           // pageForm.setActionMode(NEDSSConstants.VIEW_SUBFORM_LOAD_ACTION);
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
            
            PageSubFormLoadUtil.createGenericLoadUtil(pageForm, request);

            Map<Object, Object> subbatchMap = PageSubFormLoadUtil.findBatchRecords(formCd, request.getSession());
            request.setAttribute("SubSecStructureMap", subbatchMap);
            request.setAttribute("BatchSubSection", pageForm.getSubSecStructureMap());
            _setRenderDir(request, formCdfromJSP);
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
    			String passedContextAction = request.getParameter("ContextAction");
     			String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
                pageForm.setPageTitle("Add " + NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE_DESC, request);
                //LabCommonUtil.GenerateLinks(pageForm, request);
            } else if (NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE.equals(busObjType))
            {
    			String passedContextAction = request.getParameter("ContextAction");
     			String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
     			
     			String modeTitle ="";
     			
     			if(currentMode==null || currentMode.equalsIgnoreCase(""))
     				modeTitle = "Add";
     			else
     				modeTitle = currentMode;
     			
                pageForm.setPageTitle(modeTitle+" " + NEDSSConstants.ISO_BUSINESS_OBJECT_TYPE_DESC, request);
                String SupplementalInfoByConditionCd="N";
    			String viewSupplementalInfo="false";
    			pageForm.getSecurityMap().put("SupplementalInfoEnableInd",
    					SupplementalInfoByConditionCd);
    			pageForm.getSecurityMap().put("checkToViewSupplementalInfo",
    					viewSupplementalInfo);
    		     //    ((PageSubForm)form).getPageClientVO().setAnswer("NBS_LAB335","222222222222");
    	         //   ((PageSubForm)form).getPageClientVO().setAnswer("NBS_LAB331","Y");
    			
    			if(pageSubForm!=null)
    				copyValuesFromPageSubFormToForm(pageSubForm, form);
    			
    			//((PageSubForm)form).setActionMode(currentMode);//sets View/Edit depending on the icon selected in the batch entry
    			((PageSubForm)form).setActionModeParent(currentMode);//sets View/Edit depending on the icon selected in the batch entry
                
    			//LabCommonUtil.GenerateLinks(pageForm, request);
            } 
            
            else if (NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE.equals(busObjType))
            {
    			String passedContextAction = request.getParameter("ContextAction");
     			String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
     			
     			String modeTitle ="";
     			
     			if(currentMode==null || currentMode.equalsIgnoreCase(""))
     				modeTitle = "Add";
     			else
     				modeTitle = currentMode;
     				
                pageForm.setPageTitle(modeTitle+" " + NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE_DESC, request);
                String SupplementalInfoByConditionCd="N";
    			String viewSupplementalInfo="false";
    			pageForm.getSecurityMap().put("SupplementalInfoEnableInd",
    					SupplementalInfoByConditionCd);
    			pageForm.getSecurityMap().put("checkToViewSupplementalInfo",
    					viewSupplementalInfo);
    			if(pageSubForm!=null)
    				copyValuesFromPageSubFormToForm(pageSubForm, form);
    			

    			//((PageSubForm)form).setActionMode(currentMode);//sets View/Edit depending on the icon selected in the batch entry
    			((PageSubForm)form).setActionModeParent(currentMode);//sets View/Edit depending on the icon selected in the batch entry
    			
                //LabCommonUtil.GenerateLinks(pageForm, request);
            } 
            
        }
        catch (Exception e)
        {
        	logger.fatal("PageSubFormAction.createGenericLoad: Exception" + e.getMessage(), e);
            throw new ServletException("PageSubFormAction.createGenericLoad: Error while create generic load Page: " + e.getMessage(), e);
        }
        return (mapping.findForward(actionForward));
    }
	
    public PageSubForm copyPageSubForm (PageSubForm pageSubForm1){
    	
    	PageSubForm pageSubForm2 = new PageSubForm();
    	
    	Map<Object, Object> answerMap1 = pageSubForm1.getPageClientVO().getAnswerMap();
    	Map<Object, Object> answerMap2 = new HashMap<Object,Object>(answerMap1);
    	
    	Map<Object, ArrayList<BatchEntry>> batchEntryMap1 = pageSubForm1.getBatchEntryMap();
    	Map<Object, ArrayList<BatchEntry>> batchEntryMap2 = new HashMap<Object, ArrayList<BatchEntry>>(batchEntryMap1);
    	
    	
    	PageClientVO pageClientVO2 = new PageClientVO();
    	pageClientVO2.setAnswerMap(answerMap2);    	
       	pageSubForm2.setPageClientVO(pageClientVO2);
        
       	pageSubForm2.setBatchEntryMap(batchEntryMap2);
        
    	return pageSubForm2;
    }
    
    
  public void copyValuesFromPageSubFormToForm (PageSubForm pageSubForm1, ActionForm form){
    	
    	PageSubForm pageSubForm2 = (PageSubForm)form;
    	
    	Map<Object, Object> answerMap1 = pageSubForm1.getPageClientVO().getAnswerMap();
    	Map<Object, Object> answerMap2 = new HashMap<Object,Object>(answerMap1);
    	
    	Map<Object, ArrayList<BatchEntry>> batchEntryMap1 = pageSubForm1.getBatchEntryMap();
    	Map<Object, ArrayList<BatchEntry>> batchEntryMap2 = new HashMap<Object, ArrayList<BatchEntry>>(batchEntryMap1);
    	
    	
    	PageClientVO pageClientVO2 = new PageClientVO();
    	pageClientVO2.setAnswerMap(answerMap2);    	
    	pageSubForm2.setPageClientVO(pageClientVO2);
        
    	pageSubForm2.setBatchEntryMap(batchEntryMap2);
        
    //	return pageSubForm2;
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
			PageSubForm pageForm = (PageSubForm) form;
			String formCd = getInvFormCd(request, pageForm);

			
			request.setAttribute("businessObjectType", pageForm.getBusinessObjectType());
			request.setAttribute("PageFormCd", pageForm.getPageFormCd());
			
			//TODO: Exists??
			HttpSession session = request.getSession();
						
			HashMap<String, Object> subFormHashMap = (HashMap<String, Object>) session.getAttribute(NEDSSConstants.SUBFORM_HASHMAP);
			
			if(subFormHashMap==null)
				subFormHashMap = new HashMap<String, Object>();
				
				
			PageSubForm pageSubFormCloned  = copyPageSubForm(pageForm);
			
			String currentKey = (String)session.getAttribute(NEDSSConstants.CURRENT_KEY);
			subFormHashMap.put(currentKey, pageSubFormCloned);//TODO: The key needs to be better generated.
			session.setAttribute(NEDSSConstants.SUBFORM_HASHMAP, subFormHashMap);
			
			
			
			//pageForm.setActionMode(NEDSSConstants.EDIT_SUBFORM_SUBMIT_ACTION);
			
			if(formCd != null ) {
				String contextAction = request.getParameter("ContextAction");
				if (contextAction != null && contextAction.equalsIgnoreCase(NBSConstantUtil.SubmitNoViewAccess))
					actionForward = contextAction; //could be "SubmitNoViewAccess" if no permission in jurisdiction
				else
					actionForward = "insertDMBPam";
				//process the store investigation request
			//	thisPageProxyVO = 	PageStoreUtil.createHandler(pageForm, request);
			}
			// set the success messages
			if (formCd != null && formCd.trim().length() != 0 &&
					pageForm.getErrorList()== null || pageForm.getErrorList().size() <= 0) {
				ActionMessages messages = new ActionMessages();
				if(pageForm.getBusinessObjectType()!=null && pageForm.getBusinessObjectType().equalsIgnoreCase(NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE))
					messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.ISOLATE_TEXT));
				else
					if(pageForm.getBusinessObjectType()!=null && pageForm.getBusinessObjectType().equalsIgnoreCase(NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE))
						messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
							new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.SUSCEPTIBILITY_TEXT));
					else
						messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
							new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, NBSPageConstants.GENERAL_SUBFORM_TEXT));
				
				request.setAttribute("success_messages", messages);
			}

			request.setAttribute("formCode", formCd);

		} catch (Exception e) {
			logger.fatal("PageSubFormAction.createSubmit: Exception:" + e.getMessage(), e);
			e.printStackTrace();
			throw new ServletException("PageSubFormAction.createSubmit: Error while Saving Page Case: "+e.getMessage(),e);
		}

	
		return (mapping.findForward(actionForward));
	}

	
	
	public ActionForward createSubFormPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException      
	{

        HttpSession session = request.getSession(false);
		
        try{
        	if(request.getSession().getAttribute("observationForm")!=null){
        		request.getSession().removeAttribute("observationForm");   	             
   	        	
   		 	}
        }catch (Exception e) {
			e.printStackTrace();
			logger.error("PageSubFormAction.createIsolate:- Error while Cleaning up the session objects: " + e);
		}
        //context
        String sContextAction = request.getParameter("ContextAction");
        if (sContextAction == null)
        {
            try {
                sContextAction = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationPath);
            } catch (Exception e) {
                logger.error("sContextAction is null");
            }
            if(sContextAction==null){
                logger.error("PageSubFormAction.createIsolate: sContextAction is null");
                session.setAttribute("error", "No Context Action in InvestigationLoad");
                throw new ServletException("PageSubFormAction.createIsolate: sContextAction is null");
            }  
        }
        
        if (sContextAction.equals("ViewInv")) {
			   String investgationUid = request.getParameter("publicHealthCaseUID");
			   NBSContext.store(session, "DSInvestigationUID", investgationUid);				
        }
        
    
	 return mapping.findForward("DMBViewLoad");	
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
	 * setAnswersFromSession: setting the AnswerMap from the session into the pageForm. Somehow the answerMap is deleted, so we need
	 * to get the data previously copied into the session.
	 * @param pageForm
	 * @param request
	 */
		public void setAnswersFromSession(PageSubForm pageForm, HttpServletRequest request){
			
			String currentKey = (String)request.getSession().getAttribute(NEDSSConstants.CURRENT_KEY);
			HashMap<String, Object> subFormHashMap = (HashMap<String, Object>)request.getSession().getAttribute(NEDSSConstants.SUBFORM_HASHMAP);
			if(subFormHashMap!=null){
				PageSubForm pageSubFormCurrent = (PageSubForm)subFormHashMap.get(currentKey);
				if(pageSubFormCurrent!=null)
					pageForm.getPageClientVO().setAnswerMap(pageSubFormCurrent.getPageClientVO().getAnswerMap());
			}
 
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
        	
            PageSubForm pageForm = (PageSubForm) form;
            
            //Get AnswerMap from the session
            setAnswersFromSession(pageForm, request);
            
            String busObjType = HTMLEncoder.encodeHtml(request.getParameter("businessObjectType"));
            request.setAttribute("businessObjectType", pageForm.getBusinessObjectType());
            
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
    		Map<Object, Object> subbatchMap = PageSubFormLoadUtil.findBatchRecords(formCd, request.getSession());
            request.setAttribute("SubSecStructureMap", subbatchMap);
            request.setAttribute("BatchSubSection", pageForm.getSubSecStructureMap());
            
            pageForm.setBusinessObjectType(busObjType);
            pageForm.setPageFormCd(formCd);
            pageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			String contextAction = request.getParameter("ContextAction");
            if (busObjType.equalsIgnoreCase(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE)) {
                actionForward = "viewDynamicPage";
                LabEditCommonUtil labEditCommonUtil =new LabEditCommonUtil();
              //  labEditCommonUtil.editLabLoad(mapping, (PageSubForm)form, request,response);
    			//editLabLoad(ActionMapping mapping, PageSubForm pageForm, HttpServletRequest request,
    		    //        HttpServletResponse response) 
    			//LabCommonUtil.GenerateLinks(pageForm, request);
    			request.setAttribute("genericPageTitle", NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE_DESC);
                pageForm.setPageTitle("View " + NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE_DESC, request);
	            PageManagementCommonActionUtil.setTheRenderDirectory(request, formCd, busObjType);
	            request.setAttribute("formCode", formCd);

    			actionForward = "default";
        	}else
        	{
        		//PageSubFormLoadUtil.editGenericLoadUtil(pageForm, request);    
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
	            else if (NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE.equals(busObjType))
	            {
	                pageForm.setPageTitle("Edit " + NEDSSConstants.ISO_BUSINESS_OBJECT_TYPE_DESC, request);
	            }
	            else if (NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE.equals(busObjType))
	            {
	                pageForm.setPageTitle("Edit " + NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE_DESC, request);
	            }
	            
        	}
        }
        catch (Exception e)
        {
        	logger.fatal("PageSubFormAction.editGenericLoad: Exception:" + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("PageSubFormAction.editGenericLoad: Error while loading Generic Edit Page: " + e.getMessage(), e);
        }
        return (mapping.findForward(actionForward));
    }

	
	/**
	 * viewGenericLoad - Dynamic page for generic pages such as Interview
	 * @param mapping
	 * @param form PageSubForm
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
            PageSubForm pageForm = (PageSubForm) form;
            String busObjType = HTMLEncoder.encodeHtml(request.getParameter("businessObjectType"));
			pageForm.setGenericType(null);			

            if (busObjType == null && pageForm.getBusinessObjectType() != null)
            {
                busObjType = pageForm.getBusinessObjectType(); // returning from
                                                               // create or edit
                
                request.setAttribute("businessObjectType", busObjType);
            }
            
            
            
            //Get AnswerMap from the session
            setAnswersFromSession(pageForm, request);
            
            PortPageUtil.blockPageAccessUntilConversionIsComplete(busObjType, request);
            String formCd =  pageForm.getPageFormCd();
          //  String formCd = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, busObjType);
            
        //    if (formCd == null || formCd.isEmpty())
         //   {
        //        throw new ServletException("No published form? For business object: " + busObjType);
       //     }
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
           //////////////Generic load begins
    		if(busObjType.equals(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE)) {    
    			
    			LabViewCommonUtil labViewCommonUtil =new LabViewCommonUtil();
    			labViewCommonUtil.viewGenericLabLoad(formCd, mapping, form, request,response);
    			//LabCommonUtil.viewGenericLoadUtil(NEDSSConstants.VIEW, pageForm, request, response);
    			
    			
    			request.setAttribute("genericPageTitle", NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE_DESC);
                pageForm.setPageTitle("View " + NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE_DESC, request);

    			actionForward = "default";
    		}///////////////Generic load ends
    		else {
	            request.setAttribute("mode", request.getParameter("mode"));
	
	            
	        	String genericViewEditUrlStr = "/nbs/PageSubFormAction.do?method=editGenericLoad&mode=Edit&businessObjectType="
						+ busObjType;// + "&actUid=" + actUidStr;
				request.setAttribute("genericViewEditUrl", genericViewEditUrlStr);
				String genericViewDeleteUrlStr = "/nbs/PageSubFormAction.do?method=deleteGenericSubmit&businessObjectType="
						+ busObjType;// + "&actUid=" + actUidStr;
				request.setAttribute("genericViewDeleteUrl",
						genericViewDeleteUrlStr);
				
	          //  PageSubFormLoadUtil.viewGenericLoadUtil(pageForm, request);
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
	             //   PageLoadUtil.overrridePermissionToHideButton(pageForm, "ParentWindow", "SupplementalInfo", "deleteGenericPermission", "ParentWindowContext", request);
	            }
	            
	            else if (NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(busObjType))
	            { 
	                request.setAttribute("genericPageTitle", NEDSSConstants.ISO_BUSINESS_OBJECT_TYPE_DESC);
	                pageForm.setPageTitle("View " + NEDSSConstants.ISO_BUSINESS_OBJECT_TYPE_DESC, request);
	           }
	            else if (NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE.equalsIgnoreCase(busObjType))
	            { 
	                request.setAttribute("genericPageTitle", NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE_DESC);
	                pageForm.setPageTitle("View " + NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE_DESC, request);
	           }
	            
	
	            request.setAttribute("SubSecStructureMap", pageForm.getSubSecStructureMap());
    		}
        }
        catch (Exception e)
        {
        	logger.fatal("PageSubFormAction.viewGenericLoad: Exception: " + e.getMessage(), e);
            e.printStackTrace();
            throw new ServletException("PageSubFormAction.viewGenericLoad: Error while loading Generic Page: " + e.getMessage(), e);
        } 
        return (mapping.findForward(actionForward));
    }
	
	private static String getInvFormCd(HttpServletRequest req, PageSubForm form) {
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
			logger.error("PageSubFormAction.getInvFormCd: Error while retrieving investigationFormCd from Context / PamForm: ");
		}
		return investigationFormCd;

	}

    private void _setRenderDir(HttpServletRequest request, String pageFormCd)
    {
        String renderDir = pageFormCd;
        logger.debug("Setting render dir to: " + renderDir);
        request.setAttribute("renderDir", renderDir);
    }

}