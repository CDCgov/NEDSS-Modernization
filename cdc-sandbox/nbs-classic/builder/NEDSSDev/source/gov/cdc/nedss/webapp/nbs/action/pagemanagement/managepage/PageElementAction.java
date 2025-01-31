package gov.cdc.nedss.webapp.nbs.action.pagemanagement.managepage;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.DispatchAction;

import com.google.gson.reflect.TypeToken;

import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageElementVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.util.PageMetaConstants;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dto.PageElementDTO;
import gov.cdc.nedss.systemservice.util.CodeValueGeneralCachedDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NBSBeanUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managepage.PageBuilderForm;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managepage.PageElementForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
 * This java class will act as a controller to view/edit/save a page element in a page.
 * In the domain of pages (a user defined form for collection disease information), a 
 * page element can represent a UI element like Tab, section, subsection, question, labels etc... 
 */
public class PageElementAction extends DispatchAction 
{
	static final LogUtils logger = new LogUtils(PageElementAction.class.getName());
	
	/**
	 * Represents the context from which the request originated. This
	 * is just a helper variable and has nothing to do with the 
	 * context map that is being used in the NEDSS application. 
	 */
	private enum PageRequestedFrom {
	    EDIT_SUBMIT,
	    // ... other contexts as required
	}
	
	/**
     * Add a single page element
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
            HttpServletResponse response) throws Exception 
    {
        logger.info("Inside addLoad() of PageElementAction.");
        String eltType = request.getParameter("eltType") != null ? request.getParameter("eltType") : "";
        
        PageElementForm peForm = new PageElementForm();
        if (eltType != "") {
            // construct a place holder page element object    
            PageElementVO peVo = new PageElementVO();
            peVo.setPageElementUid(new Long(0));
            peVo.setWaUiMetadataDT(new WaUiMetadataDT());
            
            //set the default values
            peVo.getWaUiMetadataDT().setIsSecured(NEDSSConstants.FALSE);
            peVo.getWaUiMetadataDT().setDisplayInd(NEDSSConstants.TRUE);
            peVo.getWaUiMetadataDT().setEnableInd(NEDSSConstants.TRUE);
            
            // CMK FIXME: hard coding the component UIDs. Use a lookup method
            // to retrieve the component UId for the element type
            if (eltType.equalsIgnoreCase("tab")) {
                peVo.getWaUiMetadataDT().setNbsUiComponentUid(new Long(1010));
            }
            else if (eltType.equalsIgnoreCase("section")) {
                peVo.getWaUiMetadataDT().setNbsUiComponentUid(new Long(1015));
            }
            else if (eltType.equalsIgnoreCase("subsection")) {
            	eltType ="Subsection";
                peVo.getWaUiMetadataDT().setNbsUiComponentUid(new Long(1016));
            }

            // set window & page title
            request.setAttribute("windowTitle", "NBS Manage Pages");
            request.setAttribute("pageTitle", "Manage Pages: Add " + eltType);
            
            // set the vo in form
            peForm.setPageEltVo(peVo);
            
            Date date = new java.util.Date();
            Timestamp currentTime = new Timestamp(date.getTime());
            request.setAttribute(NEDSSConstants.ADD_TIME_FOR_SRT_FILTERING, currentTime);
        }

        request.getSession().setAttribute("pageElementForm", peForm);
        
        return (mapping.findForward("edit" + eltType.toLowerCase()));
    }
	
	
	/**
	 * View a single page element in the context of a page builder.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception 
	{
		try {
			logger.info("Inside viewLoad() of PageElementAction.");
			String pageElementUid = request.getParameter("pageElementUid") != null ? request.getParameter("pageElementUid") : "";
			PageElementVO peVo = null;
			String elementType = request.getParameter("eltType") != null ? request.getParameter("eltType") : "";
			String batchType = request.getParameter("batch") != null ? request.getParameter("batch") : "";
			PageManagementActionUtil pmUtil = new PageManagementActionUtil();
			StringBuffer questionIdString = new StringBuffer();
			// if question id is valid
			if (pageElementUid.trim() != "") {
				// retrieve the element with the given UID present in the 
				// PageManagementProxyVO in session
				peVo = pmUtil.retrievePageElementInPMProxyVO(request, pageElementUid);
				request.setAttribute("pageElementVO", peVo);
				
				// get the default value's description
				String defaultValueDesc = "";
				if (peVo.getWaUiMetadataDT().getCodeSetGroupId() != null && 
						peVo.getWaUiMetadataDT().getDefaultValue() != null) {
					CachedDropDownValues cdv = new CachedDropDownValues();
					ArrayList<Object> retVal = CachedDropDowns.getCodedValueForType(
							cdv.getTheCodeSetNm(peVo.getWaUiMetadataDT().getCodeSetGroupId()));
					for (Object o : retVal) {
						DropDownCodeDT ddcDt = (DropDownCodeDT) o;
						if (ddcDt.getKey().equals(peVo.getWaUiMetadataDT().getDefaultValue())) {
							defaultValueDesc = ddcDt.getValue();
							break;
						}
					}
				}


				request.setAttribute("defaultValueDesc", defaultValueDesc);

				// set miscellaneous properties required for display
				if (peVo.getWaUiMetadataDT() != null) {

					if(peVo.getWaUiMetadataDT().getDataType()!= null && !peVo.getWaUiMetadataDT().getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE)){
						if(peVo.getWaUiMetadataDT().getMaxValue()!= null && peVo.getWaUiMetadataDT().getMaxValue().longValue() == 0)
							peVo.getWaUiMetadataDT().setMaxValue(null);
						if(peVo.getWaUiMetadataDT().getMinValue()!= null && peVo.getWaUiMetadataDT().getMinValue().longValue() == 0)
							peVo.getWaUiMetadataDT().setMinValue(null);
					}


					CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();

					// set message indicator
					if (peVo.getWaUiMetadataDT().getQuestionIdentifierNnd() != null &&
							peVo.getWaUiMetadataDT().getQuestionIdentifierNnd().trim().length() > 0) {
						peVo.getWaUiMetadataDT().setNndMsgInd(NEDSSConstants.TRUE);    
					}
					else {
						peVo.getWaUiMetadataDT().setNndMsgInd(NEDSSConstants.FALSE);
					}

				
					// added by jayasudha to show the value   		
	        		if ((peVo.getWaUiMetadataDT()).getNndMsgInd().equals(NEDSSConstants.TRUE)) {
	        			
	        			TreeMap<Object,Object> returnMap = CachedDropDowns.getStandredConceptCVGCodes();
	        			boolean  flag = true;
	        			Collection<Object> CVGs = returnMap.values();
	        			if(CVGs !=null && CVGs.size()>0){
	        				Iterator<Object> ite  = CVGs.iterator();
	        				while(ite.hasNext()){
	        					CodeValueGeneralCachedDT dtCVGCache = (CodeValueGeneralCachedDT)ite.next();
	        					if(dtCVGCache.getCodeDescTxt().equals(peVo.getWaUiMetadataDT().getQuestionOid())){
	        						peVo.getWaUiMetadataDT().setQuestionOid(dtCVGCache.getCodeDescTxt());
	        						peVo.getWaUiMetadataDT().setQuestionOidSystemTxt(dtCVGCache.getCodeShortDescTxt());
	        						flag = false;
	        						break;
	        					}
	        				}
	        				
	        				if(flag){
	        				
	        					CodeValueGeneralCachedDT dtCVGCache = (CodeValueGeneralCachedDT)returnMap.get(peVo.getWaUiMetadataDT().getQuestionOid());
	        					
	        					peVo.getWaUiMetadataDT().setQuestionOidSystemTxt(dtCVGCache.getCodeShortDescTxt());
	        				}
	        			}
	        		}
					// default display control
					request.setAttribute("defaultDisplayControlDesc", peVo.getWaUiMetadataDT().getNbsUiComponentUid() == null ? "" : 
						cachedDropDownValues.getDefaultDispCntrlDesc(
								peVo.getWaUiMetadataDT().getNbsUiComponentUid().toString()));

					// nnd question data type
					request.setAttribute("questionDataTypeNndDesc", peVo.getWaUiMetadataDT().getQuestionDataTypeNnd() == null ? "" : 
						cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_HL7_DATATYPE,
								peVo.getWaUiMetadataDT().getQuestionDataTypeNnd()));

					// nnd hl7 segment 
					request.setAttribute("hl7SegmentDesc", peVo.getWaUiMetadataDT().getHl7Segment() == null ? "" : 
						cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_HL7_SEGMENT,
								peVo.getWaUiMetadataDT().getHl7Segment()));  

					// mask desc
					request.setAttribute("maskDesc", peVo.getWaUiMetadataDT().getMask() == null ? "" : 
						cachedDropDownValues.getDescForCode(NEDSSConstants.PAGE_MASK,
								peVo.getWaUiMetadataDT().getMask()));

					// unit type desc
					request.setAttribute("unitTypeCdDesc", peVo.getWaUiMetadataDT().getUnitTypeCd() == null ? "" : 
						cachedDropDownValues.getDescForCode("NBS_UNIT_TYPE",
								peVo.getWaUiMetadataDT().getUnitTypeCd()));

					// unit value desc
					if(peVo.getWaUiMetadataDT().getUnitTypeCd() != null && 
							peVo.getWaUiMetadataDT().getUnitTypeCd().equals("CODED")) {
						PageManagementActionUtil pmaUtil = new PageManagementActionUtil();
						request.setAttribute("unitValueDesc", peVo.getWaUiMetadataDT().getUnitValue() == null ? "" : 
							pmaUtil.getValSetDesc(peVo.getWaUiMetadataDT().getUnitValue()));
					}

					// set related unit indicator in case of a numeric question
					if (peVo.getWaUiMetadataDT() != null && 
							peVo.getWaUiMetadataDT().getDataType() != null && 
							peVo.getWaUiMetadataDT().getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE) &&
							peVo.getWaUiMetadataDT().getUnitTypeCd() != null &&
							!peVo.getWaUiMetadataDT().getUnitTypeCd().equals("")) {
						peVo.getWaUiMetadataDT().setRelatedUnitInd(NEDSSConstants.TRUE);
					}
					else {
						peVo.getWaUiMetadataDT().setRelatedUnitInd(NEDSSConstants.FALSE);
					}
				}
				
				
				
					
				
				// get the element DTO for the element VO
				ArrayList<Object> voElts = new ArrayList<Object>();
				voElts.add(peVo);
				Collection<PageElementDTO> dtoElts = pmUtil.getPageElementDTOsFromElementVOColl(voElts);

				// convert the DTOs into JSON string and set it in request
				Type typeOfElement = new TypeToken<Collection<PageElementDTO>>(){}.getType();
				String elementsJson = PageBuilderForm.convertElementsToJSON(dtoElts, typeOfElement);
				request.setAttribute("elementsJson", elementsJson);

				// set the page title
				request.setAttribute("pageTitle", "Manage Pages: View " +
						getComponentDescription(peVo.getWaUiMetadataDT().getNbsUiComponentUid()));
			}


			// for Repeating block
			Map<String,WaUiMetadataDT> batchQuestionMap = ((PageElementForm) form).getBatchQuestionMap();
			if(elementType.equals("") && batchType.equals("")){
				// Sort the BatchMap according to pagebuilder order
				Map<String,WaUiMetadataDT> sortedBatchQuestionMap = new LinkedHashMap<String,WaUiMetadataDT> ();
				PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
				for (Object pVo : pmpVo.getThePageElementVOCollection()) {
					String val1 = ((PageElementVO) pVo).getWaUiMetadataDT().getQuestionIdentifier();
					batchQuestionMap = ((PageElementForm) form).getBatchQuestionMap();
					if(batchQuestionMap != null && batchQuestionMap.size()>0){
						Collection<WaUiMetadataDT> waUiMetadataArrayList = (Collection<WaUiMetadataDT>)batchQuestionMap.values();
						Iterator<WaUiMetadataDT> it = waUiMetadataArrayList.iterator();
						while(it.hasNext()){
							WaUiMetadataDT waUiMetadataDt = (WaUiMetadataDT)it.next();
							if(val1 != null && waUiMetadataDt.getQuestionIdentifier() != null && val1.equals(waUiMetadataDt.getQuestionIdentifier()))
								sortedBatchQuestionMap.put(waUiMetadataDt.getQuestionIdentifier(), waUiMetadataDt);
						}
					}
				}
				request.setAttribute("manageMap", sortedBatchQuestionMap);
			}
			else if (elementType.equalsIgnoreCase("subsection") || batchType.indexOf("batch")>= 0) {
				//Get all the questions associated with the subsection for batch
				Integer questionGrSeqNbr = peVo.getWaUiMetadataDT().getQuestionGroupSeqNbr();
				if(questionGrSeqNbr != null)
				{
					ArrayList<WaUiMetadataDT> waUiMetadataList = pmUtil.retrieveBatchedQuestions(request, questionGrSeqNbr, Long.valueOf(pageElementUid));
					((PageElementForm) form).setBatchQuestionMap(new HashMap<String, WaUiMetadataDT>());
					Iterator<WaUiMetadataDT> ite = waUiMetadataList.iterator();
					while(ite.hasNext()){
						WaUiMetadataDT waUiMetadataDt = (WaUiMetadataDT)ite.next();
						//Static elements will not appear in the repeating block
						if(!waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1003")) || !waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1011")) || 
								!waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1012")) || !waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1014"))
								|| !waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1023")) || !waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1030"))
								|| !waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1032")) || !waUiMetadataDt.getNbsUiComponentUid().equals(new Long(PageMetaConstants.ORIGDOCLIST)))
						{
							((PageElementForm) form).getBatchQuestionMap().put(waUiMetadataDt.getQuestionIdentifier(), waUiMetadataDt);
						}
						questionIdString.append(waUiMetadataDt.getQuestionIdentifier()).append(",");
					}
					((PageElementForm) form).setManageList(waUiMetadataList);

					// Sort the BatchMap according to pagebuilder order
					Map<String,WaUiMetadataDT> sortedBatchQuestionMap = new LinkedHashMap<String,WaUiMetadataDT> ();
					PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
					for (Object pVo : pmpVo.getThePageElementVOCollection()) {
						String val1 = ((PageElementVO) pVo).getWaUiMetadataDT().getQuestionIdentifier();
						batchQuestionMap = ((PageElementForm) form).getBatchQuestionMap();
						if(batchQuestionMap != null && batchQuestionMap.size()>0){
							Collection<WaUiMetadataDT> waUiMetadataArrayList = (Collection<WaUiMetadataDT>)batchQuestionMap.values();
							Iterator<WaUiMetadataDT> it = waUiMetadataArrayList.iterator();
							while(it.hasNext()){
								WaUiMetadataDT waUiMetadataDt = (WaUiMetadataDT)it.next();
								if(val1 != null && waUiMetadataDt.getQuestionIdentifier() != null && val1.equals(waUiMetadataDt.getQuestionIdentifier()))
									sortedBatchQuestionMap.put(waUiMetadataDt.getQuestionIdentifier(), waUiMetadataDt);
							}
						}
					}




					request.setAttribute("manageMap", sortedBatchQuestionMap);
				}
				//elementType ="Subsection";                
			}


			// set action messages if required.
			if (peVo != null && isQuestionElement(peVo.getWaUiMetadataDT().getNbsUiComponentUid())) {
				if (request.getParameter("from") != null && 
						request.getParameter("from").equals(PageRequestedFrom.EDIT_SUBMIT.toString())) {
					String qName = peVo.getWaUiMetadataDT().getQuestionNm() + 
							" (" + peVo.getWaUiMetadataDT().getQuestionIdentifier() + ")";

					ActionMessages messages = new ActionMessages();
					messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
							new ActionMessage(NBSPageConstants.UPDATE_SUCCESS_MESSAGE_KEY, qName));
					request.setAttribute("success_messages", messages);
				}

				return (mapping.findForward("viewQuestion"));
			}
			else {
				if (request.getParameter("from") != null && 
						(request.getParameter("from").equals(PageRequestedFrom.EDIT_SUBMIT.toString()) ||
								request.getParameter("from").equals(PageRequestedFrom.EDIT_SUBMIT.toString()))) {
					String label = peVo.getWaUiMetadataDT().getQuestionLabel() == null ? "" : 
						peVo.getWaUiMetadataDT().getQuestionLabel();
					if (label.equals("") && 
							peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(PageMetaConstants.LINESEPARATOR))) {
						label = "Line Separator";
					}
					else if (label.equals("") && 
							peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(PageMetaConstants.PARTICIPANTLIST))) {
						label = "Read Only Participant List";
					}
					else if (label.equals("") && 
							peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(PageMetaConstants.PATIENTSEARCH))) {
						label = "Patient Search";
					}
					else if (label.equals("") && 
							peVo.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(PageMetaConstants.ORIGDOCLIST))) {
						label = "Original Electronic Document List";
					}

					if (!label.equals("")) {
						ActionMessages messages = new ActionMessages();
						messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
								new ActionMessage(NBSPageConstants.UPDATE_SUCCESS_MESSAGE_KEY, label));
						request.setAttribute("success_messages", messages);
					}
				}

				return (mapping.findForward("viewElement"));
			}
		}catch (Exception e) {
			logger.error("Exception in Review Notification Submit: " + e.getMessage());
			e.printStackTrace();
			throw new Exception("Error occurred in Review Notification Submit : "+e.getMessage());
		}   
	}
	
	
	/**
	 * View a single page element in edit mode in the context of a page builder. This step 
	 * populates the form with data from the question object if it is edited for the first time
	 * or populate it with the data from the question+page instance if it is a re-edit.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception 
	{
		logger.info("Inside editLoad() of PageElementAction.");
        String pageElementUid = request.getParameter("pageElementUid") != null ? request.getParameter("pageElementUid") : "";
        String elementType = request.getParameter("eltType") != null ? request.getParameter("eltType") : "";
        String batchType = request.getParameter("batch") != null ? request.getParameter("batch") : "";
        
        PageElementForm peForm = new PageElementForm();
        
        
        try {
        	// perform editLoad() for valid element IDs
        	if (pageElementUid != "") {
        		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();
        		PageElementVO peVO = pmaUtil.retrievePageElementInPMProxyVO(request, pageElementUid);

        		if(peVO.getWaUiMetadataDT() !=null && peVO.getWaUiMetadataDT().getAddTime()!=null)
         			request.setAttribute(NEDSSConstants.ADD_TIME_FOR_SRT_FILTERING, peVO.getWaUiMetadataDT().getAddTime());
         		// set the default values if not defined
        		if (peVO.getWaUiMetadataDT() != null && peVO.getWaUiMetadataDT().getIsSecured() == null) {
        			peVO.getWaUiMetadataDT().setIsSecured(NEDSSConstants.FALSE);
        		}
        		if (peVO.getWaUiMetadataDT() != null && peVO.getWaUiMetadataDT().getDisplayInd() == null) {
        			peVO.getWaUiMetadataDT().setDisplayInd(NEDSSConstants.TRUE);
        		}
        		if (peVO.getWaUiMetadataDT() != null && peVO.getWaUiMetadataDT().getEnableInd() == null) {
        			peVO.getWaUiMetadataDT().setEnableInd(NEDSSConstants.TRUE);
        		}
        		if (peVO.getWaUiMetadataDT() != null && peVO.getWaUiMetadataDT().getRequiredInd() == null) {
        			peVO.getWaUiMetadataDT().setRequiredInd(NEDSSConstants.FALSE);
        		}
        		if (peVO.getWaUiMetadataDT() != null && peVO.getWaUiMetadataDT().getPublishIndCd() == null) {
        			peVO.getWaUiMetadataDT().setPublishIndCd(NEDSSConstants.FALSE);
        		}

        		if(peVO.getWaUiMetadataDT().getDataType()!= null && !peVO.getWaUiMetadataDT().getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE)){
        			if(peVO.getWaUiMetadataDT() != null && peVO.getWaUiMetadataDT().getMaxValue()!= null && peVO.getWaUiMetadataDT().getMaxValue().longValue() == 0)
        				peVO.getWaUiMetadataDT().setMaxValue(null);

        			if(peVO.getWaUiMetadataDT() != null && peVO.getWaUiMetadataDT().getMinValue()!= null && peVO.getWaUiMetadataDT().getMinValue().longValue() == 0)
        				peVO.getWaUiMetadataDT().setMinValue(null);
        		}

        		// set related unit indicator in case of a numeric question
        		if (peVO.getWaUiMetadataDT() != null && 
        				peVO.getWaUiMetadataDT().getDataType() != null && 
        				peVO.getWaUiMetadataDT().getDataType().equals(NEDSSConstants.NUMERIC_DATATYPE) &&
        				peVO.getWaUiMetadataDT().getUnitTypeCd() != null &&
        				!peVO.getWaUiMetadataDT().getUnitTypeCd().equals("")) {
        			peVO.getWaUiMetadataDT().setRelatedUnitInd(NEDSSConstants.TRUE);
        		}
        		else {
        			peVO.getWaUiMetadataDT().setRelatedUnitInd(NEDSSConstants.FALSE);
        		}

        		// set message indicator
        		if (peVO.getWaUiMetadataDT() != null && 
        				peVO.getWaUiMetadataDT().getQuestionIdentifierNnd() != null &&
        				peVO.getWaUiMetadataDT().getQuestionIdentifierNnd().trim().length() > 0) {
        			peVO.getWaUiMetadataDT().setNndMsgInd(NEDSSConstants.TRUE);    
        		}
        		else {
        			peVO.getWaUiMetadataDT().setNndMsgInd(NEDSSConstants.FALSE);
        		}

     // added by jayasudha to show the value   		
        		if ((peVO.getWaUiMetadataDT()).getNndMsgInd().equals(NEDSSConstants.TRUE)) {
        			
        			TreeMap<Object,Object> returnMap = CachedDropDowns.getStandredConceptCVGCodes();
        			Collection<Object> CVGs = returnMap.values();
        			if(CVGs !=null && CVGs.size()>0){
        				Iterator<Object> ite  = CVGs.iterator();
        				while(ite.hasNext()){
        					CodeValueGeneralCachedDT dtCVGCache = (CodeValueGeneralCachedDT)ite.next();
        					if(dtCVGCache.getCodeDescTxt().equals(peVO.getWaUiMetadataDT().getQuestionOid())){
        						peVO.getWaUiMetadataDT().setQuestionOidCode(dtCVGCache.getCode());
        						peVO.getWaUiMetadataDT().setQuestionOidSystemTxt(dtCVGCache.getCodeShortDescTxt());
        						peVO.getWaUiMetadataDT().setQuestionOid(dtCVGCache.getCodeDescTxt());
        						break;
        					}
        				}
        			}
        		}
        		
        		// set default display control description
        		if (peVO.getWaUiMetadataDT() != null && peVO.getWaUiMetadataDT().getNbsUiComponentUid() != null) {
        			CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
        			String desc = (peVO.getWaUiMetadataDT().getNbsUiComponentUid() == null) ? "" : 
        				cachedDropDownValues.getDefaultDispCntrlDesc(
        						peVO.getWaUiMetadataDT().getNbsUiComponentUid().toString());
        			request.setAttribute("defaultDisplayControlDesc", desc);                            
        		}

        		// unit value desc
        		if(peVO.getWaUiMetadataDT().getUnitTypeCd() != null && 
        				peVO.getWaUiMetadataDT().getUnitTypeCd().equals("CODED")) {
        			request.setAttribute("unitValueDesc", peVO.getWaUiMetadataDT().getUnitValue() == null ? "" : 
        				pmaUtil.getValSetDesc(peVO.getWaUiMetadataDT().getUnitValue()));
        		}

        		// set 'allow for entry of other value' for coded question
        		if (peVO.getWaUiMetadataDT().getDataType() != null &&
        				peVO.getWaUiMetadataDT().getDataType().equals(NEDSSConstants.CODED) &&
        				(peVO.getWaUiMetadataDT().getOtherValIndCd() == null || 
        				(peVO.getWaUiMetadataDT().getOtherValIndCd() != null &&
        				peVO.getWaUiMetadataDT().getOtherValIndCd().equals("")))) {
        			peVO.getWaUiMetadataDT().setOtherValIndCd(NEDSSConstants.FALSE);
        		}

        		peForm.setPageEltVo(peVO);

        		// handle elementType of 'fieldRow'
        		if (elementType.equalsIgnoreCase("fieldRow")) {
        			if (
        					// hyper link    
        					peVO.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1003)) ||
        					// read-only comments
        					peVO.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1014)) ||
        					// line separator
        					peVO.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1012)) ||
        					// participant list
        					peVO.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1030)) ||
        					// Patient Search
        					peVO.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1032))||
        					// Action Button
        					peVO.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1033))||
        					// Set Values Button
        					peVO.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(1034))||
        					// Original electronc document List
        					peVO.getWaUiMetadataDT().getNbsUiComponentUid().equals(new Long(PageMetaConstants.ORIGDOCLIST))
        					) {
        				elementType = "staticElement";
        				String  uiComponentDesc= this.getComponentDescription( peVO.getWaUiMetadataDT().getNbsUiComponentUid());
        				peForm.setUiComponentDesc(uiComponentDesc);
        			}
        			else {
        				elementType = "question";
        				request.setAttribute("batchInd", peVO.getWaUiMetadataDT().getBatchTableAppearIndCd());
        				request.setAttribute("displayInd", peVO.getWaUiMetadataDT().getDisplayInd());
        			}
        		}

        		// set window and page title
        		request.setAttribute("windowTitle", "NBS Manage Pages");
        		StringBuffer questionIdString = new StringBuffer();
        		if (elementType.equalsIgnoreCase("subsection") || batchType.indexOf("batch")>= 0) {
        			//Get all the questions associated with the subsection for batch
        			Integer questionGrSeqNbr = peVO.getWaUiMetadataDT().getQuestionGroupSeqNbr();
        			if(questionGrSeqNbr != null)
        			{
        				ArrayList<WaUiMetadataDT> waUiMetadataList = pmaUtil.retrieveBatchedQuestions(request, questionGrSeqNbr, Long.valueOf(pageElementUid));
        				peForm.setBatchQuestionMap(new HashMap<String, WaUiMetadataDT>());
        				Iterator<WaUiMetadataDT> ite = waUiMetadataList.iterator();
        				while(ite.hasNext()){
        					WaUiMetadataDT waUiMetadataDt = (WaUiMetadataDT)ite.next();
        					//Static elements will not appear in the repeating block
        					if(!waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1003")) || !waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1011")) || 
        							!waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1012")) || !waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1014"))
        							|| !waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1023")) || !waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1030"))
        							|| !waUiMetadataDt.getNbsUiComponentUid().equals(Long.getLong("1032")) || !waUiMetadataDt.getNbsUiComponentUid().equals(new Long(PageMetaConstants.ORIGDOCLIST)))
        					{
        						if(waUiMetadataDt.getBatchTableAppearIndCd() == null)
        							waUiMetadataDt.setBatchTableAppearIndCdForJsp("Y");
        						else if(waUiMetadataDt.getBatchTableAppearIndCd().equals("Y"))
        							waUiMetadataDt.setBatchTableAppearIndCdForJsp("Y");
        						else if(waUiMetadataDt.getBatchTableAppearIndCd().equals("N"))
        							waUiMetadataDt.setBatchTableAppearIndCdForJsp("N");
        						if(waUiMetadataDt.getDisplayInd().equals("F"))
        							waUiMetadataDt.setBatchTableAppearIndCdForJsp("N");
        						if(waUiMetadataDt.getBatchTableHeader() == null)
        						{
        							if(waUiMetadataDt.getQuestionLabel() != null && waUiMetadataDt.getQuestionLabel().length()> 50)
        								waUiMetadataDt.setBatchTableHeader(waUiMetadataDt.getQuestionLabel().substring(0, 49));
        							else
        								waUiMetadataDt.setBatchTableHeader(waUiMetadataDt.getQuestionLabel());
        						}
        						peForm.getBatchQuestionMap().put(waUiMetadataDt.getQuestionIdentifier(), waUiMetadataDt);
        					}
        					questionIdString.append(waUiMetadataDt.getQuestionIdentifier()).append(",");
        				}
        				peForm.setManageList(waUiMetadataList);
        				request.setAttribute("manageList", waUiMetadataList);

        				// Sort the BatchMap according to pagebuilder order
        				Map<String,WaUiMetadataDT> sortedBatchQuestionMap = new LinkedHashMap<String,WaUiMetadataDT> ();
        				PageManagementProxyVO pmpVo = (PageManagementProxyVO) request.getSession().getAttribute("pageData");
        				for (Object peVo : pmpVo.getThePageElementVOCollection()) {
        					String val1 = ((PageElementVO) peVo).getWaUiMetadataDT().getQuestionIdentifier();
        					Long fieldPgEleUid = ((PageElementVO) peVo).getPageElementUid();

        					Map<String,WaUiMetadataDT> batchQuestionMap = peForm.getBatchQuestionMap();
        					if(batchQuestionMap != null && batchQuestionMap.size()>0){
        						Collection<WaUiMetadataDT> waUiMetadataArrayList = (Collection<WaUiMetadataDT>)batchQuestionMap.values();
        						Iterator<WaUiMetadataDT> it = waUiMetadataArrayList.iterator();
        						while(it.hasNext()){
        							WaUiMetadataDT waUiMetadataDt = (WaUiMetadataDT)it.next();
        							if(val1 != null && waUiMetadataDt.getQuestionIdentifier() != null && val1.equals(waUiMetadataDt.getQuestionIdentifier()))
        								sortedBatchQuestionMap.put(waUiMetadataDt.getQuestionIdentifier(), waUiMetadataDt);
        						}
        					}
        				}




        				request.setAttribute("manageMap", sortedBatchQuestionMap);
        				StringBuffer confirmMesg = new StringBuffer();
        				if(sortedBatchQuestionMap != null && sortedBatchQuestionMap.size()>0){
        					confirmMesg.append("NOTE: The questions displayed in the table below might not display in the order they appear in ").
        					append("the subsection, however, once the page is submitted the questions will be displayed in").
        					append(" the correct order on View Page.");
        					request.setAttribute("batchMsg", confirmMesg.toString());
        				}
        				if(batchType.indexOf("batch") >= 0)
        					request.setAttribute("batchType", batchType);
        				request.setAttribute("mapSize", peForm.getBatchQuestionMap().size());
        				request.setAttribute("questionIdString", questionIdString);
        			}
        			//elementType ="Subsection";                
        		}
        		request.setAttribute("pageTitle", "Manage Pages: Edit " + elementType);

        		PageElementVO peVo = null;
        		PageManagementActionUtil pmUtil = new PageManagementActionUtil();
        		peVo = pmUtil.retrievePageElementInPMProxyVO(request, pageElementUid);
        		// get the element DTO for the element VO
        		ArrayList<Object> voElts = new ArrayList<Object>();
        		voElts.add(peVo);
        		Collection<PageElementDTO> dtoElts = pmUtil.getPageElementDTOsFromElementVOColl(voElts);

        		// convert the DTOs into JSON string and set it in request
        		Type typeOfElement = new TypeToken<Collection<PageElementDTO>>(){}.getType();
        		String elementsJson = PageBuilderForm.convertElementsToJSON(dtoElts, typeOfElement);
        		request.setAttribute("elementsJson", elementsJson);
        		if(batchType != null && !batchType.equals("")){

        			request.setAttribute("elementIds", batchType.substring("batch".length(), batchType.length()));
        		}else{
        			request.setAttribute("elementIds",batchType);
        		}

        		// set the form in session
        		request.getSession().setAttribute("pageElementForm", peForm);
        	}
        }catch (Exception e) {
        	logger.error("Exception in Page Element Edit Load: " + e.getMessage());
        	e.printStackTrace();
        	throw new Exception("Error occurred in Page Element Edit Load : "+e.getMessage());
        }   
        return (mapping.findForward("edit" + elementType.toLowerCase()));
	}
	
	/**
	 * Both adding a new element and editing an existing an
	 * existing element in session is handled here.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editSubmit(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		ActionRedirect redirect = null;
		try {
		logger.info("Inside editSubmit() of PageElementAction.");
		PageManagementActionUtil pmaUtil = new PageManagementActionUtil();
		PageElementVO returnVal = null;

		// element status
		boolean isNewElement = true;
		// NOTE: elementUid is a +ve integer for elements already stored in the DB. It is 
		// a -ve integer for elements that are already added to pageVO but are yet to be
		// persisted in the DB.
		if (((PageElementForm) form).getPageEltVo().getPageElementUid() == 0) {
		    isNewElement = true;
		}
		else {
		    isNewElement = false;
		}
		
		// for Repeating block
        Map<String,WaUiMetadataDT> batchQuestionMap = ((PageElementForm) form).getBatchQuestionMap();
        if(batchQuestionMap != null && batchQuestionMap.size()>0){
        	Collection<WaUiMetadataDT> waUiMetadataArrayList = (Collection<WaUiMetadataDT>)batchQuestionMap.values();
        	Iterator<WaUiMetadataDT> it = waUiMetadataArrayList.iterator();
    		while(it.hasNext()){
    			WaUiMetadataDT waUiMetadataDt = (WaUiMetadataDT)it.next();
    			waUiMetadataDt.setBatchTableAppearIndCd(waUiMetadataDt.getBatchTableAppearIndCdForJsp());
    			if(waUiMetadataDt.getBatchTableAppearIndCd().equals("N")){
    				waUiMetadataDt.setBatchTableColumnWidth(null);
    				waUiMetadataDt.setBatchTableHeader(null);
    			}
    			//if rolling note - reset the column width from 100 to 70
    			if (batchQuestionMap.size() == 1 && waUiMetadataDt.getNbsUiComponentUid().equals(PageMetaConstants.ROLLINGNOTE))
    				waUiMetadataDt.setBatchTableColumnWidth(PageMetaConstants.ROLLING_NOTE_BATCH_TABLE_COLUMN_WIDTH);
    		}
        	request.setAttribute("manageMap", batchQuestionMap);
        }


        // get the element (with updated values) from form.
        PageElementVO eltFromForm = ((PageElementForm) form).getPageEltVo();

        // if new element, add page object in session
        if (isNewElement) {
            Collection<Object> temp = new ArrayList<Object> ();
            temp.add(eltFromForm);
            ArrayList<Object> list = pmaUtil.addPageElementsToPMProxyVO(request, temp);
            returnVal = (PageElementVO) list.get(0);
        }
        // else update the existing page element in the session 
        else {
            // get the existing element from session
            PageElementVO peVO = pmaUtil.retrievePageElementInPMProxyVO(request, 
                    eltFromForm.getPageElementUid().toString());
            
            // reset messaging fields if required
            // FIXME: This should not be required, but for some reason, even after 
            // setting blank values for these fields in UI, they are carried over
            // in the action form.
            if (((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).getNndMsgInd().equals(NEDSSConstants.FALSE)) {
                ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setQuestionIdentifierNnd("");
                ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setQuestionLabelNnd("");
                ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setQuestionDataTypeNnd("");
                ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setQuestionReqNnd(NEDSSConstants.OPTIONAL);
                ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setHl7Segment("");
                ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setOrderGrpId("");
                
                //set the NND DT to null, so that row is deleted from wa_nnd_metadata table too.
                peVO.setWaNndMetadataDT(null);
            } else{
            	// code added by jayasudha on 12/124/2016
            	TreeMap<Object,Object> returnMap = CachedDropDowns.getStandredConceptCVGCodes();
            	CodeValueGeneralCachedDT dtCVGCache = (CodeValueGeneralCachedDT)returnMap.get(((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).getQuestionOidCode());
				((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setQuestionOid(dtCVGCache.getCodeDescTxt());
				((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setQuestionOidSystemTxt(dtCVGCache.getCodeShortDescTxt());
                
            }
            
            // reset related unit fields
            if (((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).getRelatedUnitInd().equals(NEDSSConstants.FALSE)) {
                ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setUnitTypeCd("");
                ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setUnitValue("");
            }
            
            // reset the field length if data type is numeric && if mask selected is a plain integer)
            if (((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).getDataType() != null && 
                    ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).getMask() != null &&  
                    ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).getDataType().equals(
                            NEDSSConstants.NUMERIC_DATATYPE) && 
                    !((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).getMask().equals(
                            NEDSSConstants.NUMERIC_CODE))  {
                ((WaUiMetadataDT) eltFromForm.getWaUiMetadataDT()).setFieldLength("");
            }
            
            // sync values from new element to existing element
            NBSBeanUtils nUtils = new NBSBeanUtils();
            nUtils.copyNonNullProperties(peVO.getWaUiMetadataDT(), eltFromForm.getWaUiMetadataDT());
            
            // refresh/update the collection in session
            returnVal = peVO;
        }
        
        
		// return to view
		String pageElementUid = String.valueOf(returnVal.getPageElementUid());
		redirect = new ActionRedirect(mapping.findForward("saveSuccess")); 
		redirect.addParameter("pageElementUid", pageElementUid);
		redirect.addParameter("from", PageRequestedFrom.EDIT_SUBMIT.toString());
    }catch (Exception e) {
    	logger.error("Exception in Page Element Edit Submit: " + e.getMessage());
    	e.printStackTrace();
    	throw new Exception("Error occurred in Page Element Edit Submit : "+e.getMessage());
    }   		
		
		return redirect;
	}

    /**
        Find out whether the componentId passed is 
        a question element or not.
    */	
	private boolean isQuestionElement(Long nbsComponentUid)
	{
	   ArrayList<Long> nonQuestionElts = new ArrayList<Long>();
	   nonQuestionElts.add(new Long(1002)); // page
	   nonQuestionElts.add(new Long(1003)); // hyper link
	   nonQuestionElts.add(new Long(1010)); // tab
	   nonQuestionElts.add(new Long(1011)); // sub heading
	   nonQuestionElts.add(new Long(1012)); // line separator
	   nonQuestionElts.add(new Long(1014)); // comments
	   nonQuestionElts.add(new Long(1015)); // section
	   nonQuestionElts.add(new Long(1016)); // subsection
	   nonQuestionElts.add(new Long(1023)); // info bar    
	   nonQuestionElts.add(new Long(1030)); // participant List
	   nonQuestionElts.add(new Long(1032)); // Patient Search
	   nonQuestionElts.add(new Long(1033)); // Action Button
	   nonQuestionElts.add(new Long(1034)); // Set Values Button
	   nonQuestionElts.add(new Long(PageMetaConstants.ORIGDOCLIST)); // Original Electronic Document List
	   	   
	   if (nonQuestionElts.contains(nbsComponentUid)) {
	       return false;
	   }
	   else {
	       return true;
	   }
	}
	
	/**
	 * Return the description for the element identified by its component UID.
	 * @param nbsComponentUid
	 * @return
	 */
	private String getComponentDescription(Long nbsComponentUid)
	{
	    String retVal = "";
	    if (nbsComponentUid.equals(new Long(1002))) {
	        retVal = "Page";
	    }
	    else if (nbsComponentUid.equals(new Long(1003))) {
            retVal = "Hyperlink";
        } 
	    else if (nbsComponentUid.equals(new Long(1010))) {
            retVal = "Tab";
        }
	    else if (nbsComponentUid.equals(new Long(1011))) {
            retVal = "Subheading";
        }
	    else if (nbsComponentUid.equals(new Long(1012))) {
            retVal = "Line Separator";
        }
	    else if (nbsComponentUid.equals(new Long(1014))) {
            retVal = "Read Only Comment";
        }
	    else if (nbsComponentUid.equals(new Long(1015))) {
            retVal = "Section";
        }
	    else if (nbsComponentUid.equals(new Long(1016))) {
            retVal = "Subsection";
        }
	    else if (nbsComponentUid.equals(new Long(1023))) {
            retVal = "Information Bar";
        }
	    else if (nbsComponentUid.equals(new Long(1030))) {
            retVal = "Read Only Participant List";
        }
	    else if (nbsComponentUid.equals(new Long(1032))) {
            retVal = "Patient Search";
        }
	    else if (nbsComponentUid.equals(new Long(1033))) {
            retVal = "Action Button";
        }
	    else if (nbsComponentUid.equals(new Long(1034))) {
            retVal = "Set Values Button";
        }
	    else if (nbsComponentUid.equals(new Long(PageMetaConstants.ORIGDOCLIST))){
	    	retVal = "Original Electronic Document List";
	    }
	    
	    return retVal;
	}
}