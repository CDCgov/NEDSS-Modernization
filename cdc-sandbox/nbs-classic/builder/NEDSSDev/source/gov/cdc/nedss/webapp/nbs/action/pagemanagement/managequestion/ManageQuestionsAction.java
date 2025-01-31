package gov.cdc.nedss.webapp.nbs.action.pagemanagement.managequestion;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.DispatchAction;

import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managequestion.ManageQuestionsForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class ManageQuestionsAction extends DispatchAction 
{
	static final LogUtils logger = new LogUtils(ManageQuestionsAction.class.getName());
	private enum invocationContext {
	    addQuestionSubmit,
	    editQuestionSubmit,
	    questionStatusUpdate
	}

	/**
	 * Display the add questions page to create a new question.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addQuestionLoad(ActionMapping mapping, ActionForm form, 
	        HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
	    logger.info("The AddQuestionLoad method is called from Action class");
		try{
			ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
			manageQuestionsForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			manageQuestionsForm.resetSelection();
			manageQuestionsForm.clearForm();
			// set default values for the DT
			WaQuestionDT dt = new WaQuestionDT();
			
			// preset other values
			dt.setNndMsgInd(NEDSSConstants.FALSE);
			dt.setQuestionReqNnd(NEDSSConstants.OPTIONAL);
			dt.setRelatedUnitInd(NEDSSConstants.FALSE);
			dt.setOrderGrpId(NEDSSConstants.ORDER_GRP_ID);
			dt.setFutureDateIndCd(NEDSSConstants.FALSE);
			dt.setStandardNndIndCd(NEDSSConstants.FALSE);
			dt.setStandardQuestionIndCd(NEDSSConstants.FALSE);
			dt.setEntryMethod(NEDSSConstants.QuestionEntryMethod.USER.toString());
			dt.setOtherValIndCd(NEDSSConstants.FALSE);
			dt.setDataLocation(NEDSSConstants.DATA_LOCATION_CASE_ANSWER_TEXT);
			dt.setGroupNm(NEDSSConstants.GROUP_INV);
			// set values from the search page
			if (manageQuestionsForm.getSearchMap() != null) {
			    for (Object propObj : (Set<Object>) manageQuestionsForm.getSearchMap().keySet()) {
			        String propName = (String) propObj;
			        String propVal = (String) manageQuestionsForm.getSearchMap().get(propName);
			        
			        if (propName.equals("questionIdentifier")) {
			            dt.setQuestionIdentifier(propVal);
			        }
			        else if (propName.equals("questionNm")) {
			            dt.setQuestionNm(propVal);
                    }
                    else if (propName.equals("subGroup")) {
                        dt.setSubGroupNm(propVal);
                    }
                    else if (propName.equals("label")) {
                        dt.setQuestionLabel(propVal);
                    }
                    else if (propName.equals("questionType")) {
                        dt.setQuestionType(propVal);
                    }
			    }
			    
			    // remove the search map from form after using it to pre-populate the add question form
			    manageQuestionsForm.setSearchMap(new HashMap<Object, Object>());
			}
			
			// set table name after converting into required format.
			if(dt.getRdbTableNm()!= null) {
				/*if(NEDSSConstants.GROUP_CON.equals(dt.getGroupNm()))
					dt.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(dt.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_CR));
   				 else if(NEDSSConstants.GROUP_IXS.equals(dt.getGroupNm()))
   					dt.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(dt.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_IXS));
   				 else*/
			    dt.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(dt.getRdbTableNm(), NEDSSConstants.NBS_PH_DOMAINS));    
			        
			}
			
			// set the DT in form
			manageQuestionsForm.setSelection(dt);
			
			// set default values for question unit
			WaQuestionDT dtUnit = new WaQuestionDT();			
			manageQuestionsForm.setSelectionUnit(dtUnit);
			
			// set page title
			manageQuestionsForm.setPageTitle("Manage Questions: Add Question", request);
			request.setAttribute("sectionName", "Add Question");
			Date date = new java.util.Date();
			Timestamp currentTime = new Timestamp(date.getTime());
			request.setAttribute(NEDSSConstants.ADD_TIME_FOR_SRT_FILTERING, currentTime);
		}
		catch(Exception e) {
		    logger.error(" Error while loading the add page " + e.getMessage() );
		    e.printStackTrace();
		    throw new ServletException(e.getMessage());
		}

		return (mapping.findForward("addQuestion"));
	}

	/**
	 * Save the question into the DB.
	 */
	public ActionForward addQuestionSubmit(ActionMapping mapping, 
	        ActionForm form,HttpServletRequest request,
	        HttpServletResponse response)throws Exception 
	{
	    logger.info("The AddQuestionSubmit method is called from Action class");
	    Long waQuestionUid = null;
	    
		PageManagementActionUtil util = new PageManagementActionUtil();
		try
		{
		    ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
		    manageQuestionsForm.setActionMode(NEDSSConstants.CREATE_SUBMIT_ACTION);
		    
		    WaQuestionDT dt = (WaQuestionDT)manageQuestionsForm.getSelection();
		    if(dt.getQuestionType().equalsIgnoreCase("LOCAL"))
		    	dt.setQuestionIdentifier(manageQuestionsForm.getQuestionIndentifier());
		  
		    PageManagementActionUtil.trimSpaces(dt);

			dt = util.setTheValuesForDT(dt, request, NEDSSConstants.CREATE_SUBMIT_ACTION);
			
			
			if(dt.getQuestionIdentifier().equals("")) {
			    dt.setQuestionIdentifier(manageQuestionsForm.getQuestionIndentifier());
			}
			

			if(dt.getDataType()!= null && !dt.getDataType().equals("CODED")){
				dt.setOtherValIndCd("");
				dt.setOtherValIndCdDesc("");
			}
			
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(dt);
			
			try{
			    // check for uniqueness of the question
			    manageQuestionsForm.getErrorList().addAll(validateQuestionUniqueness(dt));
			    
			}
			catch(Exception e) {
			    logger.error(" Error while validating the Unique Id page "+e.getMessage(), e);
			    throw new ServletException(e.getMessage());
			}
			
			if(manageQuestionsForm.getErrorList() != null 
			        && manageQuestionsForm.getErrorList().size() > 0)
			{
			    request.setAttribute("errors", manageQuestionsForm.getErrorList());
			    manageQuestionsForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			    return (mapping.findForward("addQuestion"));
			}
			//setting the datamart Column name
			if(request.getParameter("rdbTableNmHid") != null) {
				String rdbTableName = request.getParameter("rdbTableNmHid");
				dt.setRdbTableNm(rdbTableName);
				String subGroupNm = CachedDropDowns.getCdForCdDescTxt(rdbTableName, NEDSSConstants.NBS_PH_DOMAINS);
				if(dt.getRdbcolumnNm()!=null && !dt.getRdbcolumnNm().isEmpty())
					dt.setRdbcolumnNm(subGroupNm+"_"+dt.getRdbcolumnNm());
				}
			/*if(dt.getRdbTableNmHid()!=null){
				if(NEDSSConstants.GROUP_CON.equals(waQuestionDT.getGroupNm()))
				 waQuestionDT.setRdbTableNm(CachedDropDowns.getCodeDescTxtForCd(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_CR));
			 else if(NEDSSConstants.GROUP_IXS.equals(waQuestionDT.getGroupNm()))
				 waQuestionDT.setRdbTableNm(CachedDropDowns.getCodeDescTxtForCd(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_IXS));
			 else
			 waQuestionDT.setRdbTableNm(CachedDropDowns.getCodeDescTxtForCd(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS));
			}
			*/



			// Call the EJB Here
			HttpSession session = request.getSession();
			waQuestionUid = (Long)util.setWAQuestion(session, list);
			request.setAttribute("waQuestionUid",waQuestionUid);
		} 
		catch(Exception e) {
		    logger.error(" Error while loading the add page "+e.getMessage(), e);
		    e.printStackTrace();
		    throw new ServletException(e.getMessage());
		}

		// redirect to viewQuestionLoad
        ActionRedirect redirect = new ActionRedirect(mapping.findForward("viewQuestion")); 
        redirect.addParameter("waQuestionUid", waQuestionUid);
        redirect.addParameter("invocationContext", invocationContext.addQuestionSubmit.toString());
        return redirect;
	}
	
	/**
	 * View a single question 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewQuestionLoad(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request,HttpServletResponse response)
	{
	    ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
	    Long waQuestionUid = null;
	    String questionNameId = "";
	    try {
	        manageQuestionsForm.setPageTitle("Manage Questions: View Question", request);
	        manageQuestionsForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			
	        if(request.getAttribute("waQuestionUid") != null) {
	            waQuestionUid = (Long)request.getAttribute("waQuestionUid");
	        }
				
	        if(request.getParameter("waQuestionUid") != null) {
	            waQuestionUid = Long.parseLong(request.getParameter("waQuestionUid"));
	        }

	        // Call the EJB Here
	        HttpSession session = request.getSession();
	        PageManagementActionUtil util = new PageManagementActionUtil();
	        ArrayList<Object> list = (ArrayList<Object>)util.getWAQuestion(session, waQuestionUid);
	        WaQuestionDT waQuestionDT = new WaQuestionDT();
	        if(list !=null && list.size()>1) {
	            Iterator<Object> iter = list.iterator();
	            while(iter.hasNext()) {
	                waQuestionDT = (WaQuestionDT)iter.next();
                    /*if(waQuestionDT.getRdbTableNm()!=null){
                    	if(NEDSSConstants.GROUP_CON.equals(waQuestionDT.getGroupNm()))
	   	   					 waQuestionDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_CR));
	   	   				 else if(NEDSSConstants.GROUP_IXS.equals(waQuestionDT.getGroupNm()))
	   	   					 waQuestionDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_IXS));
	   	   				 else
	                    waQuestionDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQuestionDT.getRdbTableNm(), NEDSSConstants.NBS_PH_DOMAINS));
                    }*/
	                    
                    if(waQuestionDT.getCoInfQuestion()==null)waQuestionDT.setCoInfQuestion("F");
	                if(waQuestionDT != null && (waQuestionDT.getQuestionUnitIdentifier() != null && !waQuestionDT.getQuestionUnitIdentifier().equals(""))){ 
	                	waQuestionDT.setRelatedUnitInd(NEDSSConstants.TRUE);    
	                    manageQuestionsForm.setSelection(waQuestionDT);
	                    manageQuestionsForm.setOldDT(waQuestionDT);

	                }
	                else {
	                    manageQuestionsForm.setSelectionUnit(waQuestionDT);
	                }
	            }
	        }
	        //If there is no unit associated to the main question, execute this part 
	        else if(list !=null && list.size()==1) {
	        	WaQuestionDT waQuesDT = (WaQuestionDT)list.get(0);
	        	questionNameId = waQuesDT.getQuestionNm() + " (" + waQuesDT.getQuestionIdentifier() + ")";
	        	
	        	 if(waQuesDT.getCoInfQuestion()==null)waQuesDT.setCoInfQuestion("F");
	        	 
	        	if(waQuesDT != null && (waQuesDT.getUnitTypeCd() != null && (waQuesDT.getUnitTypeCd().equals("CODED") || waQuesDT.getUnitTypeCd().equals("LITERAL"))))
	        		waQuesDT.setRelatedUnitInd(NEDSSConstants.TRUE);
                else
                	waQuesDT.setRelatedUnitInd(NEDSSConstants.FALSE);
	        	
	        	if(waQuesDT.getRelatedUnitInd() != null)
	        		waQuesDT.setRelatedUnitIndDesc((waQuesDT.getRelatedUnitInd().equals(NEDSSConstants.TRUE)) ? "Yes" : "No");
	        	// get the default value's description
	        	String defaultValueDesc = "";
	        	if (waQuesDT.getCodeSetGroupId() != null && waQuesDT.getDefaultValue() != null) {
	        	    CachedDropDownValues cdv = new CachedDropDownValues();
	                ArrayList<Object> retVal = CachedDropDowns.getCodedValueForType(
	                        cdv.getTheCodeSetNm(waQuesDT.getCodeSetGroupId()));
	                for (Object o : retVal) {
	                    DropDownCodeDT ddcDt = (DropDownCodeDT) o;
	                    if (ddcDt.getKey().equals(waQuesDT.getDefaultValue())) {
	                        defaultValueDesc = ddcDt.getValue();
	                        break;
	                    }
	                }
	        	}
	        	request.setAttribute("defaultValueDesc", defaultValueDesc);
	        	
	        	// set the record status code
	        	if (waQuesDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Active) ||
	        	        waQuesDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
	        	    waQuesDT.setStatusDesc("Active");
	        	}
	        	else {
	        	    waQuesDT.setStatusDesc("Inactive");
	        	}
	        	
                /*if(waQuesDT.getRdbTableNm()!=null){
                	if(NEDSSConstants.GROUP_CON.equals(waQuesDT.getGroupNm()))
                		waQuesDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQuesDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_CR));
	   				 else if(NEDSSConstants.GROUP_IXS.equals(waQuesDT.getGroupNm()))
	   					waQuesDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQuesDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_IXS));
	   				 else
                	waQuesDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQuesDT.getRdbTableNm(), NEDSSConstants.NBS_PH_DOMAINS));
                }*/
                
                if(waQuesDT.getSubGroupNm()!=null){
                	waQuesDT.setSubGroupDesc(CachedDropDowns.getCodeDescTxtForCd(waQuesDT.getSubGroupNm(), NEDSSConstants.PAGE_SUBGROUP));
                }
                
                if(waQuesDT.getDataType()!= null && !waQuesDT.getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE)){
	                if(waQuesDT.getMaxValue()!= null && waQuesDT.getMaxValue().longValue() == 0)
	                	waQuesDT.setMaxValue(null);
	                
	                if(waQuesDT.getMinValue()!= null && waQuesDT.getMinValue().longValue() == 0)
	                	waQuesDT.setMinValue(null);
                }
              //  removeGroupFromRdbColumn(waQuesDT);
	            manageQuestionsForm.setSelection(waQuesDT);
	            manageQuestionsForm.setOldDT(waQuesDT);
	        }
	        
	        // set the action messages based on the invocation context
	        ActionMessages aMsgs = generateActionMessages(request, questionNameId);
            request.setAttribute("success_messages", aMsgs);
            
            // set the questionUid in session
            session.setAttribute("waQuestionUid",waQuestionUid);
	    } 
	    catch (Exception e) {
	        request.setAttribute("error", e.getMessage());
	        logger.error("Exception in Manage Questions View Question Load: " + e.getMessage());
	        e.printStackTrace();
	    } 
	    finally {

		}
	    return (mapping.findForward("loadQuestion"));
	}
	
	/**
	 * removeGroupFromRdbColumn: method created for removing the subgroup in front of the rdb name to show in the View Question page since now it doesn't get the value from the user_defined_column_nm.
	 * @param waQuesDT
	 */
	public void removeGroupFromRdbColumn(WaQuestionDT waQuesDT){
		String rdbColumnName = waQuesDT.getRdbcolumnNm();

		String rdbTableName = waQuesDT.getRdbTableNm();
		String subGroupNm = CachedDropDowns.getCdForCdDescTxt(rdbTableName, NEDSSConstants.NBS_PH_DOMAINS);
		String columnWithoutSubGroup = rdbColumnName.substring(subGroupNm.length()+1);
		waQuesDT.setRdbcolumnNm(columnWithoutSubGroup);
	
	}
	
	public void addGroupFromRdbColumn(WaQuestionDT waQuesDT){
		String rdbColumnName = waQuesDT.getRdbcolumnNm();

		String rdbTableName = waQuesDT.getRdbTableNm();
		String subGroupNm = CachedDropDowns.getCdForCdDescTxt(rdbTableName, NEDSSConstants.NBS_PH_DOMAINS);
		if(rdbColumnName!=null && !rdbColumnName.isEmpty())
			rdbColumnName = subGroupNm+"_"+rdbColumnName;
		waQuesDT.setRdbcolumnNm(rdbColumnName);
	
	}
	
	public ActionForward editQuestionLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception {
		logger.info("The EditQuestionLoad method is called from Action class");
		  Long waQuestionUid =null;
		  HttpSession session = request.getSession();
		  CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();  
		try{
			if(request.getParameter("waQuestionUid") != null) {
	            waQuestionUid = Long.parseLong(request.getParameter("waQuestionUid"));
	        }
			if(waQuestionUid == null){
				waQuestionUid = (Long)session.getAttribute("waQuestionUid");
			}
			ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
			manageQuestionsForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			manageQuestionsForm.clearForm();
	        // Call the EJB Here	       
	        PageManagementActionUtil util = new PageManagementActionUtil();
	        ArrayList<Object> list = (ArrayList<Object>)util.getWAQuestion(session, waQuestionUid);
	        WaQuestionDT waQuestionDT = new WaQuestionDT();
	        if(list !=null && list.size()>1) {
	            Iterator<Object> iter = list.iterator();
	            while(iter.hasNext()) {
	                waQuestionDT = (WaQuestionDT)iter.next();
	                request.setAttribute("groupNm", waQuestionDT.getGroupNm());
	                request.setAttribute(NEDSSConstants.ADD_TIME_FOR_SRT_FILTERING, waQuestionDT.getAddTime());
	                /*if(waQuestionDT.getRdbTableNm()!=null){
	                	 if(NEDSSConstants.GROUP_CON.equals(waQuestionDT.getGroupNm()))
	    					 waQuestionDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_CR));
	    				 else if(NEDSSConstants.GROUP_IXS.equals(waQuestionDT.getGroupNm()))
	    					 waQuestionDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_IXS));
	    				 else
	                    waQuestionDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQuestionDT.getRdbTableNm(), NEDSSConstants.NBS_PH_DOMAINS));
	                }*/
	                if(waQuestionDT.getQuestionOid()!=null ){
	                	PageManagementActionUtil.updateCodeSystemNameforEdit(waQuestionDT);
	                }
	                
	                if(waQuestionDT != null && (waQuestionDT.getQuestionUnitIdentifier() != null && !waQuestionDT.getQuestionUnitIdentifier().equals(""))){ 
	                	waQuestionDT.setRelatedUnitInd(NEDSSConstants.TRUE);
	                    manageQuestionsForm.setSelection(waQuestionDT);
	                    manageQuestionsForm.setOldDT(waQuestionDT);
	                }
	                else {
	                    manageQuestionsForm.setSelectionUnit(waQuestionDT);
	                }
	            }
	        }
	        //If there is no unit associated to the main question, execute this part 
	        else if(list !=null && list.size()==1) {
 	        	WaQuestionDT waQDT = (WaQuestionDT)list.get(0);
 	        	request.setAttribute("groupNm", waQDT.getGroupNm());
 	        	 request.setAttribute(NEDSSConstants.ADD_TIME_FOR_SRT_FILTERING, waQDT.getAddTime());
 	        	if(waQDT.getQuestionOid()!=null){
                	PageManagementActionUtil.updateCodeSystemNameforEdit(waQDT);
                }
                /*if(waQDT.getRdbTableNm()!=null){
                	if(NEDSSConstants.GROUP_CON.equals(waQDT.getGroupNm()))
                		waQDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_CR));
	   				 else if(NEDSSConstants.GROUP_IXS.equals(waQDT.getGroupNm()))
	   					waQDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_IXS));
	   				 else
                	waQDT.setRdbTableNm(CachedDropDowns.getCdForCdDescTxt(waQDT.getRdbTableNm(), NEDSSConstants.NBS_PH_DOMAINS));
                }*/
                	
                if(waQDT != null && (waQDT.getUnitTypeCd() != null && (waQDT.getUnitTypeCd().equals("CODED") || waQDT.getUnitTypeCd().equals("LITERAL"))))
                	waQDT.setRelatedUnitInd(NEDSSConstants.TRUE);
                else
                	waQDT.setRelatedUnitInd(NEDSSConstants.FALSE);
              //  removeGroupFromRdbColumn(waQDT);
	            manageQuestionsForm.setSelection(waQDT);
	            manageQuestionsForm.setSelectionUnit(new WaQuestionDT());
	            manageQuestionsForm.setOldDT(waQDT);
	            if(waQDT.getOtherValIndCd() == null)
	            	waQDT.setOtherValIndCd(NEDSSConstants.FALSE);
	            
	            // set the record status code
                if (waQDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Active) ||
                        waQDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
                    waQDT.setStatusDesc("Active");
                }
                else {
                    waQDT.setStatusDesc("Inactive");
                }
                if(waQDT.getDataType()!= null && !waQDT.getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE)){
	                if(waQDT.getMaxValue()!= null && waQDT.getMaxValue().longValue() == 0)
	                	waQDT.setMaxValue(null);
	                
	                if(waQDT.getMinValue()!= null && waQDT.getMinValue().longValue() == 0)
	                	waQDT.setMinValue(null);
                }
				// default display control
				request.setAttribute("defaultDisplayControlDesc", waQDT.getNbsUiComponentUid() == null ? "" : 
					cachedDropDownValues.getDefaultDispCntrlDesc(
							waQDT.getNbsUiComponentUid().toString()));
	        }
	        
	        request.setAttribute("waQuestionUid", waQuestionUid);    
	        
	        
			
			/**
			 * TODO Set the message value if this question is associated with a page
			 * Needs to do after the Page Builder is done
			 */
			request.setAttribute("deleteError", "");

			manageQuestionsForm.setPageTitle("Manage Questions: Edit Question", request);
			request.setAttribute("sectionName", "Edit Question");
		}catch(Exception e){
			logger.error(" Error while loading the Edit page " + e.getMessage() );
			e.printStackTrace();
			throw new ServletException(e.getMessage());

		}

		return (mapping.findForward("editQuestion"));
	}

	public ActionForward editQuestionSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception {

		logger.info("The EditQuestionSubmit method is called from Action class");

		ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
		HttpSession session = request.getSession();
		PageManagementActionUtil util = new PageManagementActionUtil();
		WaQuestionDT waQuestionDT =null;
		//WaQuestionDT dtUnit = null;
		Long waQuestionUid = null;
		
		// reset messaging fields if required
		// FIXME: This should not be required, but for some reason, even after 
		// setting blank values for these fields in UI, they are carried over
		// in the action form.
		WaQuestionDT qDt = (WaQuestionDT) manageQuestionsForm.getSelection(); 
		String defaultValueHidden = manageQuestionsForm.getDefaultValue();
		if(defaultValueHidden!= null && defaultValueHidden.equalsIgnoreCase("reset")){
			qDt.setDefaultValue(null);
		}
		PageManagementActionUtil.trimSpaces(qDt);
		
		
		if (qDt.getNndMsgInd().equals(NEDSSConstants.FALSE)) {
		    qDt.setQuestionIdentifierNnd("");
		    qDt.setQuestionLabelNnd("");
		    qDt.setQuestionDataTypeNnd("");
		    qDt.setQuestionDataTypeNndDesc("");
		    qDt.setQuestionReqNnd(NEDSSConstants.OPTIONAL);
		    qDt.setQuestionReqNndDesc("");
		    qDt.setHl7Segment("");
		    qDt.setHl7SegmentDesc("");
		    qDt.setOrderGrpId("");
		}
		//if (qDt.getRelatedUnitInd().equals(NEDSSConstants.FALSE)) {
		if((NEDSSConstants.FALSE).equals(qDt.getRelatedUnitInd())){
		    qDt.setUnitTypeCd("");
		    qDt.setUnitTypeCdDesc("");
		    qDt.setUnitValue("");
		    qDt.setUnitValueDesc("");
		}
		if(qDt.getDataType()!= null && !qDt.getDataType().equals("CODED") ){
			qDt.setOtherValIndCd("");
			qDt.setOtherValIndCdDesc("");
			qDt.setCodeSetGroupId(null);
			
		}
		if(qDt.getDataType()!= null && !qDt.getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE) ){
			qDt.setRelatedUnitInd(null);
			qDt.setUnitTypeCd(null);
			qDt.setUnitTypeCdDesc(null);
			qDt.setUnitValue(null);
			qDt.setMinValue(null);
			qDt.setMaxValue(null);
		}
		if(qDt.getDataType()!= null && (!qDt.getDataType().equals(NEDSSConstants.DATE_DATATYPE) && !qDt.getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE)&& !qDt.getDataType().equals(NEDSSConstants.TEXT_DATATYPE) )){
			qDt.setMask(null);
			qDt.setMaskDesc(null);
		}
		if(qDt.getDataType()!= null && !qDt.getDataType().equals(NEDSSConstants.DATE_DATATYPE) ){
			qDt.setFutureDateIndCd(null);
		}
		if(qDt.getDataType()!= null && (!qDt.getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE)&& !qDt.getDataType().equals(NEDSSConstants.TEXT_DATATYPE) )){
			qDt.setFieldLength(null);
		}
		if(qDt.getDataType()!= null && (!qDt.getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE)&& !qDt.getDataType().equals(NEDSSConstants.TEXT_DATATYPE) && !qDt.getDataType().equals("CODED")))
		{
			qDt.setDefaultValue(null);
		}
		// reset the field length if data type is numeric && if mask selected is a plain integer)
		if (qDt.getDataType() != null && qDt.getMask() != null &&  
		        qDt.getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE) && 
		        !qDt.getMask().equals(NEDSSConstants.NUMERIC_CODE))  {
		    qDt.setFieldLength("");
		}
		
		try{
			manageQuestionsForm.setActionMode(NEDSSConstants.EDIT_SUBMIT_ACTION);
			//manageQuestionsForm.resetSelection();
			 waQuestionDT = (WaQuestionDT)manageQuestionsForm.getSelection();
			 /*Settting hardcode value to group*/
			//waQuestionDT.setGroupNm(NEDSSConstants.GROUP_INV);
			 waQuestionDT = util.setTheValuesForDT(waQuestionDT, request, NEDSSConstants.EDIT_SUBMIT_ACTION);


			//In case the subgroup _ is deleted in front of the rdb_column_nm
			String rdbTableName = waQuestionDT.getRdbTableNm();
			String subGroupNm = CachedDropDowns.getCdForCdDescTxt(rdbTableName, NEDSSConstants.NBS_PH_DOMAINS);
			String column = waQuestionDT.getRdbcolumnNm();
			if(column!=null && column.indexOf(subGroupNm+"_")!=0)//if it doesn't contain the subgroup at the beginning
				if(waQuestionDT.getRdbcolumnNm()!=null && !waQuestionDT.getRdbcolumnNm().isEmpty())
					waQuestionDT.setRdbcolumnNm(subGroupNm+"_"+waQuestionDT.getRdbcolumnNm());
			 
			 
			
			 ArrayList<Object> list = new ArrayList<Object>();
			// addGroupFromRdbColumn(waQuestionDT);//adding the group at the beggining for having some behavior than when the question is read from the database.
			 list.add(waQuestionDT);
			 
			 // validate question uniqueness
             manageQuestionsForm.getErrorList().addAll(validateQuestionUniqueness(waQuestionDT));
             

             
             if(manageQuestionsForm.getErrorList() != null 
                     && manageQuestionsForm.getErrorList().size() > 0)
             {
                 request.setAttribute("errors", manageQuestionsForm.getErrorList());
                 manageQuestionsForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
                  return (mapping.findForward("editQuestion"));
             }
			 //update data mart Column name
			 //waQuestionDT.setRdbcolumnNm(waQuestionDT.getRdbTableNm() + "_" + waQuestionDT.getUserDefinedColumnNm());
			 
			/* if(waQuestionDT.getRdbTableNm()!=null){
				 if(NEDSSConstants.GROUP_CON.equals(waQuestionDT.getGroupNm()))
					 waQuestionDT.setRdbTableNm(CachedDropDowns.getCodeDescTxtForCd(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_CR));
				 else if(NEDSSConstants.GROUP_IXS.equals(waQuestionDT.getGroupNm()))
					 waQuestionDT.setRdbTableNm(CachedDropDowns.getCodeDescTxtForCd(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_IXS));
				 else
				 waQuestionDT.setRdbTableNm(CachedDropDowns.getCodeDescTxtForCd(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS));
			 } */
			 
			 
			// EJB Call -util method
			waQuestionUid = (Long)util.setWAQuestion(session, list);
			if(waQuestionUid == null)
				waQuestionUid = waQuestionDT.getWaQuestionUid();
			request.setAttribute("waQuestionUid",waQuestionUid);

		}catch(Exception e){
			logger.error(" Error while Submitting the Edit page "+e.getMessage(), e);
			throw new ServletException(e.getMessage());

		}
		
		// redirect to viewQuestionLoad
        ActionRedirect redirect = new ActionRedirect(mapping.findForward("viewQuestion")); 
        redirect.addParameter("waQuestionUid", waQuestionUid);
        redirect.addParameter("invocationContext", invocationContext.editQuestionSubmit.toString());
        return redirect;
	}

public ActionForward activeInactiveQuestionSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception {

		logger.info("The activeInactiveQuestionSubmit method is called from Action class");

		ManageQuestionsForm manageQuestionsForm = (ManageQuestionsForm) form;
		HttpSession session = request.getSession();
		PageManagementActionUtil util = new PageManagementActionUtil();
		WaQuestionDT waQuestionDT =null;
		WaQuestionDT dtUnit = null;
		Long waQuestionUid = null;
		try{
			manageQuestionsForm.setActionMode(NEDSSConstants.EDIT_SUBMIT_ACTION);
			//manageQuestionsForm.resetSelection();
			 waQuestionDT = (WaQuestionDT)manageQuestionsForm.getSelection();
			 waQuestionDT = util.setTheValuesForDT(waQuestionDT, request, NEDSSConstants.EDIT_SUBMIT_ACTION);
			 if(waQuestionDT.getRelatedUnitInd() != null && waQuestionDT.getRelatedUnitInd().equals("Y")){
				 dtUnit = (WaQuestionDT)manageQuestionsForm.getSelectionUnit();
				 dtUnit = util.setTheValuesForDT(dtUnit, request, NEDSSConstants.EDIT_SUBMIT_ACTION);
			 }
			 
			 // update the record status code
             if (waQuestionDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE) || 
                     waQuestionDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Active)) {
                 waQuestionDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Inactive);
             }
             else if (waQuestionDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_INACTIVE) ||
                     waQuestionDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_Inactive)) {
                 waQuestionDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_Active);
             }
			 
			 /*if(waQuestionDT.getRdbTableNm()!=null){
				 if(NEDSSConstants.GROUP_CON.equals(waQuestionDT.getGroupNm()))
					 waQuestionDT.setRdbTableNm(CachedDropDowns.getCodeDescTxtForCd(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_CR));
				 else if(NEDSSConstants.GROUP_IXS.equals(waQuestionDT.getGroupNm()))
					 waQuestionDT.setRdbTableNm(CachedDropDowns.getCodeDescTxtForCd(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS_IXS));
				 else
				 waQuestionDT.setRdbTableNm(CachedDropDowns.getCodeDescTxtForCd(waQuestionDT.getRdbTableNm(),NEDSSConstants.NBS_PH_DOMAINS));
			 }*/
			 
			 if(dtUnit != null)
				 dtUnit.setRecordStatusCd(waQuestionDT.getRecordStatusCd());
			 ArrayList<Object> list = util.setUnitwithFieldDT(waQuestionDT,dtUnit);
			// EJB Call -util method
			waQuestionUid = (Long)util.setWAQuestion(session, list);
			if(waQuestionUid == null)
				waQuestionUid = waQuestionDT.getWaQuestionUid();
			request.setAttribute("waQuestionUid",waQuestionUid);
			request.setAttribute("success_messages", messages);

		}catch(Exception e){
			logger.error(" Error while Submitting the Edit page "+e.getMessage(), e);
			throw new ServletException(e.getMessage());

		}
		
		// redirect to viewQuestionLoad
        ActionRedirect redirect = new ActionRedirect(mapping.findForward("viewQuestion")); 
        redirect.addParameter("waQuestionUid", waQuestionUid);
        redirect.addParameter("invocationContext", invocationContext.questionStatusUpdate.toString());
        redirect.addParameter("updatedStatus", waQuestionDT.getRecordStatusCd());
        return redirect;
	}


    /**
     * Validate whether the question received is unique according to different 
     * conditions. If not unique, return a list with an error message
     * for each failed condition.
     * @param questionToValidate
     * @return
     */
    private ArrayList<String> validateQuestionUniqueness(WaQuestionDT questionToValidate)
    {
        ArrayList<String> errorMsgs = new ArrayList<String>();
        
        PageManagementActionUtil util = new PageManagementActionUtil();
        
        // question name
        WaQuestionDT  waQuestionDT = util.getWaQuestionByName(questionToValidate.getQuestionNm());
        if(waQuestionDT != null && 
                questionToValidate.getQuestionNm().equals(waQuestionDT.getQuestionNm()) &&
                (questionToValidate.getWaQuestionUid() == null || 
                        !(questionToValidate.getWaQuestionUid() != null && 
                                questionToValidate.getWaQuestionUid().equals(waQuestionDT.getWaQuestionUid())
                         ))
        ) {
            errorMsgs.add("A question with unique name " + HTMLEncoder.encodeHtml(questionToValidate.getQuestionNm()) + 
                " already exists in the system");
        }else{
        
		        // validate question identifier
		        waQuestionDT =  util.getWaQuestionByIdentifier(questionToValidate.getQuestionIdentifier());
		        if(waQuestionDT != null && 
		                questionToValidate.getQuestionIdentifier().equals(waQuestionDT.getQuestionIdentifier()) &&
		                (questionToValidate.getWaQuestionUid() == null || 
		                        !(questionToValidate.getWaQuestionUid() != null && 
		                                questionToValidate.getWaQuestionUid().equals(waQuestionDT.getWaQuestionUid())
		                         ))
		        ) {
		            errorMsgs.add("A question with question identifier " + HTMLEncoder.encodeHtml(questionToValidate.getQuestionIdentifier()) + 
		                    " already exists in the system");
		        }
		        
		       

        // Data Mart Column Name = User Defined Column Name
		        String dataMartColumnName = questionToValidate.getUserDefinedColumnNm();
			    waQuestionDT = util.getWaQuestionByUserDefinedColumnName(dataMartColumnName);
		        if(waQuestionDT != null && dataMartColumnName!=null &&
		        		dataMartColumnName.equals(waQuestionDT.getUserDefinedColumnNm()) &&
                (questionToValidate.getWaQuestionUid() == null || 
                        !(questionToValidate.getWaQuestionUid() != null && 
                                questionToValidate.getWaQuestionUid().equals(waQuestionDT.getWaQuestionUid())
                         ))
        ) {
		            errorMsgs.add("A question with Data Mart Column Name " + HTMLEncoder.encodeHtml(waQuestionDT.getUserDefinedColumnNm()) + 
                " already exists in the system.");
		        }
		        
		        
		        // question data mart column name
		        String rdbColumnNm = "";
		        
		        if(questionToValidate.getRdbcolumnNm()!=null && !questionToValidate.getRdbcolumnNm().isEmpty())
		        	rdbColumnNm = questionToValidate.getSubGroupNm()+"_"+questionToValidate.getRdbcolumnNm();
			    waQuestionDT = util.getWaQuestionByDataMartColumnName(rdbColumnNm);
		        if(waQuestionDT != null && rdbColumnNm!=null &&
		        		rdbColumnNm.equals(waQuestionDT.getRdbcolumnNm()) &&
                (questionToValidate.getWaQuestionUid() == null || 
                        !(questionToValidate.getWaQuestionUid() != null && 
                                questionToValidate.getWaQuestionUid().equals(waQuestionDT.getWaQuestionUid())
                         ))
        ) {
		            errorMsgs.add("A question with rdb column name " + HTMLEncoder.encodeHtml(waQuestionDT.getRdbcolumnNm()) + 
                " (arrived by combining Subgroup Name and RDB Column Name values) already exists in the system.");
		        }
		        
		        
        }

        return errorMsgs;
    }
    
    private ActionMessages generateActionMessages(HttpServletRequest request, String questionNameId) 
    {
        ActionMessages messages = new ActionMessages();
        
        // get the invocation context from request parameter
        String invokedFrom = request.getParameter("invocationContext") != null ?
                request.getParameter("invocationContext") : "";
                        
        //*** populate ActionMessages depending on the context ***a//
                
        // add question submit        
        if (invokedFrom != "" && invokedFrom.equals(invocationContext.addQuestionSubmit.toString())) {
            messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
                    new ActionMessage(NBSPageConstants.ADD_SUCCESS_MESSAGE_KEY, questionNameId));
        }
        
        // edit question submit
        else if (invokedFrom != "" && invokedFrom.equals(invocationContext.editQuestionSubmit.toString())) {
            messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
                    new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, questionNameId));
        }
        
        // question record status update
        else if (invokedFrom != "" && invokedFrom.equals(invocationContext.questionStatusUpdate.toString())) {
            String updatedStatus = request.getParameter("updatedStatus") == null ? "" :
                request.getParameter("updatedStatus");
            if (updatedStatus.trim().equals("")) {
                messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
                        new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, questionNameId));
            }
            else if (updatedStatus.trim().equals(NEDSSConstants.RECORD_STATUS_Inactive)){
                messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
                        new ActionMessage(NBSPageConstants.QUESTION_INACTIVE_SUCCESS_MESSAGE_KEY, questionNameId));
            }
            else if (updatedStatus.trim().equals(NEDSSConstants.RECORD_STATUS_Active)){
                messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
                        new ActionMessage(NBSPageConstants.QUESTION_ACTIVE_SUCCESS_MESSAGE_KEY, questionNameId));   
            }
        }
        
        return messages;
    }
}