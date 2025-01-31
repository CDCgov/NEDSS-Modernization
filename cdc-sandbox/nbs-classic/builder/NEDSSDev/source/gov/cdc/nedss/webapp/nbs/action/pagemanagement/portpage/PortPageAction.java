package gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.cdc.nedss.page.ejb.portproxyejb.dt.AnswerMappingDT;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.ManagePageDT;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT;
import gov.cdc.nedss.page.port.mapping.MappingDocument;
import gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dt.ConditionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.util.LegacyToPBConverterProcessor;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.util.PBtoPBConverterProcessor;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.portpage.PortPageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

/**
 * @author PatelDh
 *
 */
public class PortPageAction extends DispatchAction{
	static final LogUtils logger = new LogUtils(PortPageAction.class.getName());
	PropertyUtil properties=PropertyUtil.getInstance();
	
	/**
     * Port Page Load
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward loadPortPage(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception{
    	try{
    		
    	    PortPageForm portPageForm = (PortPageForm)form;
	    	PortPageUtil.clearPortPageAttributes(portPageForm);
	    	
	    	PortPageUtil.populatePortPageDropdowns(portPageForm, request);
	    	
			request.setAttribute("PageTitle", "Manage Pages: Port Page");

			return mapping.findForward("portPage");
    	}catch(Exception e){
    		logger.fatal("Exception in loadPortPage method in PortPageAction"+e.getMessage(), e);
    		throw new ServletException(e.getMessage());
    	}
    }

    /**
	 * Port Page Submit
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward portPageSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PortPageUtil portUtil = new PortPageUtil();
		try{
			PortPageForm portPageForm = (PortPageForm)form;
			ArrayList<Object> fromFieldList=new ArrayList<Object>();
			ArrayList<Object> toFieldList= new ArrayList<Object>();
			
			boolean forceEJBcall=false;
			boolean initLoad=request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
			if(initLoad && !PaginationUtil._dtagAccessed(request)){
				portPageForm.clearMapAndList();
				//Reset the map if request coming from PortPage
				portPageForm.setMappedQuestionsMap(new HashMap<String, ArrayList<Object>> ());

				portPageForm.clearAll();
				forceEJBcall=true;
				portPageForm.getAttributeMap().put("queueSize", PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);
			}

			if(forceEJBcall){
				String mapName = portPageForm.getMapName();
				logger.debug("mapName: "+mapName);
				if(mapName!=null)
					mapName = mapName.trim();
				String toPageFormCd = portPageForm.getToPageFormCd();                   //toPageFormCd holds Uid and FormCd
				String fromPageFormCd = portPageForm.getFromPageFormCd();               //formPageFormCd holds Uid and FormCd
				ArrayList<Object> portPageList= new ArrayList<Object>();
				boolean mapNameExist = false;
				//This EJB call is to get the portPageList i.e., List of Maps in Manage Port Page.
				Object[] oParamsMapL = new Object[] {};
				String sBeanJndiNameMapL = JNDINames.PORT_PAGE_PROXY_EJB;
				String sMethodMapL = "getPortPageList";
				Object portPageListObj = CallProxyEJB.callProxyEJB(oParamsMapL, sBeanJndiNameMapL, sMethodMapL, request.getSession());
				portPageList = (ArrayList) portPageListObj;
				
				for(int i=0;i<portPageList.size();i++){
					ManagePageDT mpDT =(ManagePageDT)(portPageList).get(i);
					if(mapName.equalsIgnoreCase(mpDT.getMapName())){                                          //iterating through the portPageList to check if there is a mapName already exists
						mapNameExist = true;                                                                 //if exists will throw a error on UI.
						break;
					}
				}
				if(mapNameExist){
					logger.info("A Map with Map Name "+mapName+" already exist");
					request.setAttribute("fromPageDD",portPageForm.getFromPageListDD());
					request.setAttribute("toPageDD",portPageForm.getToPageListDD());
					FormFile file = portPageForm.getImportFile();
			    	if(file != null){
						String filePath = request.getParameter("filePath");
				    	request.setAttribute("fileName",file.getFileName());
				    	request.setAttribute("filePath",filePath);
			    	}
			    	request.setAttribute("PortPageError","\u2022 A Map with Map Name \"" +mapName +"\" already exists.");
					request.setAttribute("PageTitle", "Manage Pages: Port Page");
					return mapping.findForward("portPage");
				}
				
				//toPageTemplateNm is posted as waTemplateUid|form_cd i.e. 1000141|PG_Generic_V2_Inv_4_6_GA, this is to avoid unwanted database call and  iteration.
				StringTokenizer toPageTemplateStk = new StringTokenizer(toPageFormCd,"|");
				String toPageUid=toPageTemplateStk.nextToken();
				String toPgFormCd=toPageTemplateStk.nextToken();                                
				
				//fromPageTemplateNm is posted as waTemplateUid|form_cd i.e. 1000141|PG_Generic_V2_Inv_4_6_GA, this is to avoid unwanted database call and  iteration.
				StringTokenizer fromPageTemplateStk = new StringTokenizer(fromPageFormCd,"|");
				String fromPageUid=fromPageTemplateStk.nextToken();
				String fromPgFormCd=fromPageTemplateStk.nextToken();
				
				//Get ToPage's business Object type and set it in form.
				String busObjType = PortPageUtil.getBusinessObjectTypeForPage(Long.parseLong(toPageUid), request);
				portPageForm.setBusObjType(busObjType);
				
				logger.debug("fromPgFormCd: "+fromPgFormCd+", toPgFormCd: "+toPgFormCd);
				portPageForm.setFromPageFormCd(fromPgFormCd);
				portPageForm.setToPageFormCd(toPgFormCd);
				portPageForm.setToPageWaTemplateUid(Long.parseLong(toPageUid));
				portPageForm.setFromPageWaTemplateUid(Long.parseLong(fromPageUid));
				
				
				Long fromPageTemplateUid=portPageForm.getFromPageWaTemplateUid();
				Long toPageTemplateUid=portPageForm.getToPageWaTemplateUid();
				
				logger.info("fromPageTemplateUid: "+fromPageTemplateUid+", toPageTemplateUid: "+toPageTemplateUid);
				
				FormFile importFile = portPageForm.getImportFile();
				String inputXML=null;
				
				if(importFile != null){
					
					//During import copy metadata from NBS_UI_metadata to WA_UI_Metadata for Varicella hybrid mapping.
					portUtil.insertLDFFromNbsUiMetadataToWaUiMetadata(fromPgFormCd, fromPageTemplateUid, request);
					
				    MappingDocument mappingDoc = MappingDocument.Factory.parse(importFile.getInputStream());
		            inputXML = mappingDoc.copy().xmlText();
		            portUtil.importMapping(portPageForm,request);
				}
				
				Object[] oParamsPortPage = new Object[] {mapName, portPageForm.getFromPageFormCd(), portPageForm.getToPageFormCd(), PortPageUtil.NBS_PAGE_MAPPING_STATUS_MAPPING_IN_PROGRESS,inputXML};
				String sBeanJndiNamePortPage = JNDINames.PORT_PAGE_PROXY_EJB;
				String sMethodPortPage = "createPortPageMapping";
				Object portPageMangementUidObj = CallProxyEJB.callProxyEJB(oParamsPortPage, sBeanJndiNamePortPage, sMethodPortPage, request.getSession());
				
				Long nbsConversionPageMgmtUid = (Long)portPageMangementUidObj;
				portPageForm.setNbsConversionPageMgmtUid(nbsConversionPageMgmtUid);
				int mapNeedCnt=0;
				if(importFile!=null){
					
					logger.info("Import Mapping flow.");
					
					fromFieldList=portPageForm.getFromPageQuestions();
					toFieldList=portPageForm.getToPageQuestions();
					
					for(int i=0;i<toFieldList.size();i++){
						MappedFromToQuestionFieldsDT toFieldDT= (MappedFromToQuestionFieldsDT) toFieldList.get(i);
						portPageForm.getDisplayToQuestionsMap().put(toFieldDT.getFromQuestionId(), toFieldDT);
					}
					for(int i=0;i<fromFieldList.size();i++){
						MappedFromToQuestionFieldsDT fromFieldDT = (MappedFromToQuestionFieldsDT) fromFieldList.get(i);
						if(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED.equals(fromFieldDT.getStatusCode()) || PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R.equals(fromFieldDT.getStatusCode())){
							mapNeedCnt++;
						}
						portPageForm.getDisplayFromQuestionsMap().put(fromFieldDT.getFromQuestionId(), fromFieldDT);
					}
					portPageForm.setFromPageQuestions(fromFieldList);
					portPageForm.setToPageQuestions(toFieldList);
					portPageForm.getAttributeMap().put("fromFieldList", fromFieldList);
					portPageForm.getAttributeMap().put("status",new Integer(portPageForm.getPortStatus().size()));
					portPageForm.getAttributeMap().put("frmdatatype",new Integer(portPageForm.getFrmDataType().size()));
					portPageForm.getAttributeMap().put("map",new Integer(portPageForm.getMap().size()));
					portPageForm.getAttributeMap().put("todatatype",new Integer(portPageForm.getToDataType().size()));
					
				}else{
					logger.info("Create Mapping manually flow.");
					
					Object[] oParams1 = new Object[] {fromPageTemplateUid, toPageTemplateUid, portPageForm.getMappingType()};
					String sBeanJndiName1 = JNDINames.PORT_PAGE_PROXY_EJB;
					String sMethod1 = "getToPageQuestionsFields";
					Object toFieldListObj = CallProxyEJB.callProxyEJB(oParams1, sBeanJndiName1, sMethod1, request.getSession());
					toFieldList = (ArrayList) toFieldListObj;
					for(int i=0;i<toFieldList.size();i++){
						MappedFromToQuestionFieldsDT toFieldDT = (MappedFromToQuestionFieldsDT) toFieldList.get(i);    			
						portPageForm.getDisplayToQuestionsMap().put(toFieldDT.getFromQuestionId(), toFieldDT);
					}
	
	
					Object[] oParams = new Object[] {fromPageTemplateUid, toPageTemplateUid};
					String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
					String sMethod = "getFieldsFromPageQuestions";
					Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
					fromFieldList = (ArrayList) obj;
					portPageForm.getAttributeMap().put("fromFieldList", fromFieldList);
					portPageForm.setFromPageQuestions(fromFieldList);
					portPageForm.setToPageQuestions(toFieldList);
					portPageForm.getAttributeMap().put("status",new Integer(portPageForm.getPortStatus().size()));
					portPageForm.getAttributeMap().put("frmdatatype",new Integer(portPageForm.getFrmDataType().size()));
					portPageForm.getAttributeMap().put("map",new Integer(portPageForm.getMap().size()));
					portPageForm.getAttributeMap().put("todatatype",new Integer(portPageForm.getToDataType().size()));
					
					for(int i=0;i<fromFieldList.size();i++){
						MappedFromToQuestionFieldsDT fromFieldDT = (MappedFromToQuestionFieldsDT) fromFieldList.get(i);
	
						
						if("Y".equalsIgnoreCase(fromFieldDT.getAutoMapped())){
							fromFieldDT.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_AUTO_MAPPED);
							fromFieldDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_AUTO_MAPPED, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
							fromFieldDT.setQuestionMappedCode(PortPageUtil.MAPPED_QA_YN_Y);
							fromFieldDT.setQuestionMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.CODE_SET_YN));
							fromFieldDT.setFieldMappingRequired(true);
							
							logger.info("Setting AutoMapped questions to render on mapping screen, fromQuestionId: "+fromFieldDT.getFromQuestionId());
							
							MappedFromToQuestionFieldsDT toFieldDT = portPageForm.getDisplayToQuestionsMap().get(fromFieldDT.getFromQuestionId());
							if(toFieldDT!=null){
								fromFieldDT.setToQuestionId(toFieldDT.getFromQuestionId());
								fromFieldDT.setToLabel(toFieldDT.getFromLabel());
								fromFieldDT.setToDataType(toFieldDT.getFromDataType());
								fromFieldDT.setToCodeSetNm(toFieldDT.getFromCodeSetNm());
								fromFieldDT.setToCodeSetGroupId(toFieldDT.getCodeSetGroupId());
								fromFieldDT.setToNbsUiComponentUid(toFieldDT.getToNbsUiComponentUid());
								//Removing automapped questions from toPageQuestions list, as per the requirement if question is manually or automapped shouldn't appear in to question dropdown
								toFieldList.remove(toFieldDT);
							}
	
							//Add automapped question in mapped question list
							ArrayList<Object> fromFieldDTList = new ArrayList<Object>();
							fromFieldDTList.add(fromFieldDT);
							portPageForm.getMappedQuestionsMap().put(fromFieldDT.getFromQuestionId(), fromFieldDTList);
						}else{
							if(fromFieldDT.getQuestionGroupSeqNbr()!=null && fromFieldDT.getQuestionGroupSeqNbr()>0){
								fromFieldDT.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R);
								fromFieldDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
							}
						}
						PortPageUtil.nullToBlank(fromFieldDT);
						//To get the count of Mapping Needed Questions 
						if(fromFieldDT.getStatusCode().equals(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED) || fromFieldDT.getStatusCode().equals(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R)){
							mapNeedCnt++;
						}
						
						portPageForm.getDisplayFromQuestionsMap().put(fromFieldDT.getFromQuestionId(), fromFieldDT);
					}
			    }
	            portPageForm.setMapNeededCount(mapNeedCnt);
				
	            // Save mapping into database
				Object[] oParamsSaveMapping = new Object[] {fromFieldList, nbsConversionPageMgmtUid}; // pass waPortMappingUid from session
				String sBeanJndiNameSaveMapping = JNDINames.PORT_PAGE_PROXY_EJB;
				String sMethodSaveMapping = "insertQuestionAnswers";
				Object questionAnswerListObj = CallProxyEJB.callProxyEJB(oParamsSaveMapping, sBeanJndiNameSaveMapping, sMethodSaveMapping, request.getSession());
				boolean insertResult = (boolean) questionAnswerListObj;
			}else{
				fromFieldList=(ArrayList<Object>) portPageForm.getAttributeMap().get("fromFieldList");
				if(fromFieldList == null)
					fromFieldList = portPageForm.getFromPageQuestions();

				portPageForm.getAttributeMap().put("PageNumber","1");
			}
			String fromQuestionPage = (String)((portPageForm.getAttributeMap().get("fromQuestionPage") ==null)? "true":(portPageForm.getAttributeMap().get("fromQuestionPage")));
			if(fromQuestionPage!= null && fromQuestionPage.equalsIgnoreCase("false"))
			{
				portPageForm.setSearchCriteriaArrayMap(new HashMap<Object,Object>());
			}
			portPageForm.getAttributeMap().put("fromQuestionPage","true");
			boolean existing=request.getParameter("existing")==null ? false : true;
			String contextAction = request.getParameter("context");
			logger.info("contextAction: "+contextAction);
			if(contextAction!=null){
				//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
					fromFieldList = portPageForm.getFromPageQuestions();
					portPageForm.getAttributeMap().put("PageNumber","1");
					portPageForm.getAttributeMap().put("queueSize", PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);
				sortFromQuestions(portPageForm,fromFieldList, existing, request);
			}else{
				sortFromQuestions(portPageForm,fromFieldList,existing,request);
				if(existing){
					filterFromQuestionsLib(portPageForm,request);
				}
			}	

			if(portPageForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			if(fromFieldList != null)
				portPageForm.getAttributeMap().put("queueCount",String.valueOf(fromFieldList.size()));
			request.setAttribute("totalCount",portPageForm.getFromPageQuestions().size());            //sets the count of total questions 
			request.setAttribute("mapReqCount",portPageForm.getMapNeededCount());                    //sets the count of Mapping Req Questions
			if(fromFieldList != null)
				request.setAttribute("queueCount",String.valueOf(fromFieldList.size()));                
			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			request.setAttribute("fromFieldList", fromFieldList);
			request.setAttribute("toFieldList", toFieldList);
			
			portPageForm.initializeDropDowns();

			return PaginationUtil.paginate(portPageForm, request,"questionMappingPage", mapping);
		}catch(Exception ex){
			logger.fatal("Error in portPageSubmit method of PortPageAction class :" +ex.getMessage(), ex);
			throw new ServletException(ex.getMessage());
		}
	}


	public ActionForward mapAField(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try{
			
			PortPageForm portPageForm = (PortPageForm)form;

			String context = request.getParameter("context");
			logger.info("context: "+context);
			
			if("pagination".equalsIgnoreCase(context)){
				request.setAttribute("PageTitle", "Manage Pages: Port Page");
				request.setAttribute("fromFieldList", portPageForm.getFromPageQuestions());
				request.setAttribute("toFieldList", portPageForm.getToPageQuestions());

				return mapping.findForward("questionMappingPage");
			}

			String fromQuestionID = portPageForm.getCurrentQuestion().getFromQuestionId();	// from the UI "From ID"
			String toQuestionID = portPageForm.getCurrentQuestion().getToQuestionId();	// from the UI "To ID"
			boolean isFieldMappingRequired = portPageForm.getCurrentQuestion().isFieldMappingRequired(); // from the UI "Map Question?" dropdown value
			boolean isCodeMappingRequired = portPageForm.getCurrentQuestion().isCodeMappingRequired();	// from the UI "Map Answers?" dropdown value
            Integer blockIdNbr=portPageForm.getCurrentQuestion().getBlockIdNbr();
			Integer answerGroupSeqNbr = portPageForm.getCurrentQuestion().getAnswerGroupSeqNbr();
			String unmappedToQuestion = request.getParameter("unmappedToQuestion"); // Used for Repeating to Discrete mapping in case of Quetion Mapping Yes to No.
			
			logger.debug("Mapping fromQuestionID:"+fromQuestionID+", toQuestionID: "+toQuestionID+", isFieldMappingRequired: "+isFieldMappingRequired+", isCodeMappingRequired: "+isCodeMappingRequired+", blockIdNbr: "+blockIdNbr+", answerGroupSeqNbr: "+answerGroupSeqNbr );
			
			String mapAnotherInstance = portPageForm.getCurrentQuestion().getLegacyBlockInd(); // Legacy block indicator Yes for repeating block
			
			logger.info("In case of repeating to discreate mapping mapAnotherInstance: "+mapAnotherInstance);
			
			//In case of re-mapping auto mapped/mapped question to some other question, put back previously mapped question to ToQuestion list. So that it will be available for mapping.
			if(!"Yes".equalsIgnoreCase(mapAnotherInstance)){
				ArrayList<Object> mappedQueDTList = portPageForm.getMappedQuestionsMap().get(fromQuestionID);
				if(mappedQueDTList!=null && mappedQueDTList.size()>0){
					for(int i=0;i<mappedQueDTList.size();i++){
						MappedFromToQuestionFieldsDT mappedQueDT1 = (MappedFromToQuestionFieldsDT) mappedQueDTList.get(i);
						if(mappedQueDT1!=null){
							MappedFromToQuestionFieldsDT mappedToQueDT = portPageForm.getDisplayToQuestionsMap().get(mappedQueDT1.getToQuestionId());
							//This IF checks if the question is already in the toPageQuestions
							//For repeating to district questions it will check answer group sequence number of the current question with the question stored in the map
							//for district questions answerGroupSeqNbr will always be null 
							if(mappedToQueDT!=null){
								if(!portPageForm.getToPageQuestions().contains(mappedToQueDT) && (answerGroupSeqNbr==null || answerGroupSeqNbr.intValue() == 0 || (mappedQueDT1.getAnswerGroupSeqNbr()!= null && (answerGroupSeqNbr.intValue() == mappedQueDT1.getAnswerGroupSeqNbr().intValue())))){     
									portPageForm.getToPageQuestions().add(mappedToQueDT);
								}
							}
						}
					}
				}
			}
			
			//Get From question from list
			//Answer Group Sequence number can be 1 if we are editing already mapped question 
			MappedFromToQuestionFieldsDT fromQueDT = null;
			for(int i=0;i<portPageForm.getFromPageQuestions().size();i++){
				fromQueDT = (MappedFromToQuestionFieldsDT)portPageForm.getFromPageQuestions().get(i);
				if(answerGroupSeqNbr.intValue()>0 && fromQueDT.getAnswerGroupSeqNbr()!= null ){
					if(fromQueDT.getFromQuestionId().equalsIgnoreCase(fromQuestionID) && answerGroupSeqNbr.intValue()>0 && answerGroupSeqNbr.equals(fromQueDT.getAnswerGroupSeqNbr()))// find repeating block question
						break;
					else
						continue;
				}else{
					if(fromQueDT.getFromQuestionId().equalsIgnoreCase(fromQuestionID))
						break;
				}
			}
		    //It stores the status of question before mapping a field.i.e,before setting status as "MAPPED"
			String preStatus=fromQueDT.getStatusCode();    
			//Get To question from list
			MappedFromToQuestionFieldsDT toQueDT = null;
			for(int i=0;i<portPageForm.getToPageQuestions().size();i++){
				toQueDT = (MappedFromToQuestionFieldsDT)portPageForm.getToPageQuestions().get(i);
				if(toQueDT.getFromQuestionId().equalsIgnoreCase(toQuestionID))
					break;
			}
			
			MappedFromToQuestionFieldsDT repeatingBlockQueDT = null;
			// Update Mapped question map
			if(isFieldMappingRequired){
				ArrayList<Object> fromQueList = portPageForm.getMappedQuestionsMap().get(fromQuestionID);
				//Validation for Repeating Question Add New or Edit instance
				if(fromQueDT.getQuestionGroupSeqNbr()!=null && fromQueList!=null && fromQueList.size()>0 && fromQueDT.getAnswerGroupSeqNbr()!=null){
					// get datatype to validate if repeating block question is mapped to same type of (already mapped) to question.
					
					if(!PortPageUtil.validateRepeatingInstanceDatatype(fromQueList, fromQueDT, toQueDT, mapAnotherInstance)){
						String errorMessage = "Repeating block instances should map to same datatypes/codeset of To Questions, Errors for Question: "+fromQueDT.getFromQuestionId();
						request.setAttribute("toFieldList", portPageForm.getToPageQuestions());
						request.setAttribute("errors",portPageForm.getErrorList().add(errorMessage));
						portPageForm.setMapNeededCount(portPageForm.getMapNeededCount());
						request.setAttribute("mapReqCount",portPageForm.getMapNeededCount());
						request.setAttribute("totalCount",portPageForm.getFromPageQuestions().size());
						request.setAttribute("queueCount",String.valueOf(portPageForm.getFromPageQuestions().size()));
						sortFromQuestions(portPageForm,portPageForm.getFromPageQuestions(),true,request);
						request.setAttribute("fromFieldList", portPageForm.getFromPageQuestions());
						return PaginationUtil.paginate(portPageForm,request,"questionMappingPage",mapping);
					}
				}
				
				if(fromQueDT.getQuestionGroupSeqNbr()!=null && fromQueList!=null && fromQueList.size()>0 && "Yes".equalsIgnoreCase(mapAnotherInstance) && fromQueDT.getAnswerGroupSeqNbr()!=null){
					// if Repeating block question already mapped, create another question and add into list.
					logger.info("Repeating question mapping, mapping Another Instance.");
					
					repeatingBlockQueDT = new MappedFromToQuestionFieldsDT();
					repeatingBlockQueDT.setQuestionMappedCode(PortPageUtil.MAPPED_QA_YN_Y);
					repeatingBlockQueDT.setQuestionMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.CODE_SET_YN));
					repeatingBlockQueDT.setFromQuestionId(fromQueDT.getFromQuestionId());
					repeatingBlockQueDT.setFromLabel(fromQueDT.getFromLabel());
					repeatingBlockQueDT.setToQuestionId(toQuestionID);
					repeatingBlockQueDT.setToLabel(toQueDT.getFromLabel());
					repeatingBlockQueDT.setFromDataType(fromQueDT.getFromDataType());
					repeatingBlockQueDT.setFromDbLocation(fromQueDT.getFromDbLocation());
					repeatingBlockQueDT.setFromCodeSetNm(fromQueDT.getFromCodeSetNm());
					repeatingBlockQueDT.setFromNbsUiComponentUid(fromQueDT.getFromNbsUiComponentUid());
					repeatingBlockQueDT.setCodeSetGroupId(fromQueDT.getCodeSetGroupId());
					repeatingBlockQueDT.setToLabel(toQueDT.getFromLabel());
					repeatingBlockQueDT.setToDataType(toQueDT.getFromDataType());
					repeatingBlockQueDT.setToDbLocation(toQueDT.getFromDbLocation());
					repeatingBlockQueDT.setToCodeSetNm(toQueDT.getFromCodeSetNm());
					repeatingBlockQueDT.setCodeMappingRequired(isCodeMappingRequired);
					repeatingBlockQueDT.setFieldMappingRequired(isFieldMappingRequired);
					repeatingBlockQueDT.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK);
					repeatingBlockQueDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
					repeatingBlockQueDT.setQuestionGroupSeqNbr(fromQueDT.getQuestionGroupSeqNbr());
					int maxAnswerGrpSeqNbr = PortPageUtil.getMaxAnswerGroupSequenceNumber(fromQueList);
					repeatingBlockQueDT.setAnswerGroupSeqNbr(maxAnswerGrpSeqNbr+1); // increment answer group sequence number for repeating block question.
					
					logger.info("Adding answerGroupSeqNbr with from question label");
					//Append answerGroupSeqNbr on From question's label for repeating to discrete question
					String label = repeatingBlockQueDT.getFromLabel();
					if(label!=null)
						repeatingBlockQueDT.setFromLabel(label.replaceAll("[(]\\d[)]", "("+repeatingBlockQueDT.getAnswerGroupSeqNbr()+")"));
					
					
					repeatingBlockQueDT.setToCodeSetGroupId(toQueDT.getCodeSetGroupId());
					repeatingBlockQueDT.setToNbsUiComponentUid(toQueDT.getToNbsUiComponentUid());
					if(isCodeMappingRequired){
						repeatingBlockQueDT.setAnswerMappedCode(PortPageUtil.MAPPED_QA_YN_Y);
						repeatingBlockQueDT.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.CODE_SET_YN));
					}
					else{
						repeatingBlockQueDT.setAnswerMappedCode(PortPageUtil.MAPPED_QA_YN_N);
						repeatingBlockQueDT.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_N, PortPageUtil.CODE_SET_YN));
					}
					
					PortPageUtil.setConversionType(repeatingBlockQueDT);
					
					portPageForm.getFromPageQuestions().add(repeatingBlockQueDT);
					fromQueList.add(repeatingBlockQueDT);
					portPageForm.getMappedQuestionsMap().put(fromQuestionID, fromQueList);
				}else{
					
					fromQueDT.setQuestionMappedCode(PortPageUtil.MAPPED_QA_YN_Y);
					fromQueDT.setQuestionMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.CODE_SET_YN));
					
					fromQueDT.setFromDataType(fromQueDT.getFromDataType());
					fromQueDT.setFromDbLocation(fromQueDT.getFromDbLocation());
					fromQueDT.setFromCodeSetNm(fromQueDT.getFromCodeSetNm());
					if(fromQueDT.getToQuestionId()==null || !toQueDT.getFromQuestionId().equals(fromQueDT.getToQuestionId())){ //this skips adding the extra (blockIdNbr) to toLabel when discrete to repeat type question is remapped.
						fromQueDT.setToLabel(toQueDT.getFromLabel());
					}
					fromQueDT.setToQuestionId(toQuestionID);
					fromQueDT.setToDataType(toQueDT.getFromDataType());
					fromQueDT.setToDbLocation(toQueDT.getFromDbLocation());
					fromQueDT.setToCodeSetNm(toQueDT.getFromCodeSetNm());
					fromQueDT.setCodeMappingRequired(isCodeMappingRequired);
					fromQueDT.setFieldMappingRequired(isFieldMappingRequired);
					fromQueDT.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE);
					fromQueDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
					if(fromQueDT.getQuestionGroupSeqNbr()!=null){//set answer group sequence number for repeating block questions
						if(fromQueDT.getAnswerGroupSeqNbr()!=null)
							fromQueDT.setAnswerGroupSeqNbr(fromQueDT.getAnswerGroupSeqNbr());
						else
							fromQueDT.setAnswerGroupSeqNbr(1);
						
						String label = fromQueDT.getFromLabel();
						if(label!=null){		
							label = label.replaceAll("[(]\\d[)]", "");
							fromQueDT.setFromLabel(label+" ("+fromQueDT.getAnswerGroupSeqNbr()+")");
						}
						
						fromQueDT.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK);
						fromQueDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
					}
					if(isCodeMappingRequired){
						fromQueDT.setAnswerMappedCode(PortPageUtil.MAPPED_QA_YN_Y);
						fromQueDT.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.CODE_SET_YN));
					}else{
						fromQueDT.setAnswerMappedCode(PortPageUtil.MAPPED_QA_YN_N);
						fromQueDT.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_N, PortPageUtil.CODE_SET_YN));
					}
					fromQueDT.setFromCode("");
					fromQueDT.setToCode("");
					fromQueDT.setToCodeSetGroupId(toQueDT.getCodeSetGroupId());
					fromQueDT.setToNbsUiComponentUid(toQueDT.getToNbsUiComponentUid());
					
					//If From and To questionGroupSequenceNumber is not null then its repeating to repeating mapping.
					fromQueDT.setToQuestionGroupSeqNbr(toQueDT.getQuestionGroupSeqNbr());
					
					if(toQuestionID!=null && !toQuestionID.equals(unmappedToQuestion)) // Applicable to repeating to discrete mapping. If mapping is changed then remove the unmapped question from list.
						PortPageUtil.removeUnmappedToQuestionFromList(unmappedToQuestion, fromQuestionID, fromQueDT, portPageForm);
					
					PortPageUtil.setConversionType(fromQueDT);
				}
				if(blockIdNbr!=null && blockIdNbr>0){
				    fromQueDT.setBlockIdNbr(blockIdNbr);
				    // For repeating to repeating mapping label should be Label (R)
				    PortPageUtil.updateToQueLabelForRepeatingTypesMapping(fromQueDT,blockIdNbr,toQueDT.getFromLabel());
				}

				//After mapping remove mapped toQuestion from toPageQuestions list so that it wont display it in To Question ID list.
				//It also allows to not to remove Repeating block questions from toPageQuestions,so that it is available for mapping.
				
				if(toQueDT.getQuestionGroupSeqNbr()==null || (toQueDT.getQuestionGroupSeqNbr()!=null && fromQueDT.getQuestionGroupSeqNbr()!=null))  // If toQuestion is discrete or From and To Questions are repeating, then once it mapped remove from toQuestion List dropdown.                     
				       portPageForm.getToPageQuestions().remove(toQueDT);

				if(blockIdNbr != null){
					MappedFromToQuestionFieldsDT toDT= portPageForm.getDisplayToQuestionsMap().get(toQuestionID);
					if(toDT.getBlockIdNbr()==null || toDT.getBlockIdNbr()==0){
					    toDT.setBlockIdNbr(blockIdNbr);
					}else if(toDT.getBlockIdNbr()!=null && toDT.getBlockIdNbr()<blockIdNbr){
						toDT.setBlockIdNbr(blockIdNbr);
					}
				}
				if(toQueDT.getQuestionGroupSeqNbr()==null || toQueDT.getQuestionGroupSeqNbr()<1){
					fromQueDT.setBlockIdNbr(null);
					MappedFromToQuestionFieldsDT toDT= portPageForm.getDisplayToQuestionsMap().get(toQuestionID);
					toDT.setBlockIdNbr(null);
				}
				
			}else{
				logger.info("Map Question ? selection is No from UI.");
				
				fromQueDT.setQuestionMappedCode(PortPageUtil.MAPPED_QA_YN_N);
				fromQueDT.setQuestionMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_N, PortPageUtil.CODE_SET_YN));
				fromQueDT.setToQuestionId("");
				fromQueDT.setToLabel("");
				fromQueDT.setToDataType("");
				fromQueDT.setToCodeSetNm("");
				fromQueDT.setToDbLocation("");
				fromQueDT.setCodeMappingRequired(false);
				fromQueDT.setFieldMappingRequired(isFieldMappingRequired);
				fromQueDT.setBlockIdNbr(null);
				fromQueDT.setFromCode(null);//Pruthvi: Added
				fromQueDT.setToCode(null);//Pruthvi: Added
				fromQueDT.setAnswerMappedDesc("");
				fromQueDT.setAnswerMappedCode("");
				if(fromQueDT.getQuestionGroupSeqNbr()!=null){
					fromQueDT.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK);
					fromQueDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
					
					//Repeating block instance set answerGroupSequenceNumber
					int maxAnswerGrpSeqNbr = 0;
					ArrayList<Object> fromQueList = portPageForm.getMappedQuestionsMap().get(fromQuestionID);
					if(fromQueList != null)
						maxAnswerGrpSeqNbr = PortPageUtil.getMaxAnswerGroupSequenceNumber(fromQueList);
					
					logger.info("maxAnswerGrpSeqNbr: "+maxAnswerGrpSeqNbr);
					
					if(fromQueDT.getAnswerGroupSeqNbr()==null)
						fromQueDT.setAnswerGroupSeqNbr(maxAnswerGrpSeqNbr+1);
					
				}else{
					fromQueDT.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE);
					fromQueDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
				}
				
				fromQueDT.setAnswerMappedDesc(PortPageUtil.MAPPED_QA_YN_N);
				fromQueDT.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_N, PortPageUtil.CODE_SET_YN));
				
				PortPageUtil.removeUnmappedToQuestionFromList(unmappedToQuestion, fromQuestionID, fromQueDT, portPageForm);
			}

			if(portPageForm.getMappedQuestionsMap().get(fromQuestionID)==null || (portPageForm.getMappedQuestionsMap().get(fromQuestionID)!=null && portPageForm.getMappedQuestionsMap().get(fromQuestionID).size()==0) || fromQueDT.getQuestionGroupSeqNbr() == null){
				ArrayList<Object> fromFieldDTList = new ArrayList<Object>();
				fromFieldDTList.add(fromQueDT);
				portPageForm.getMappedQuestionsMap().put(fromQuestionID, fromFieldDTList);
			}
			//To return the filtered list after mapping every question,if any fillters are applied before mapping.
		
			Collection<Object> filteredFromQuest  = filterFromQuestionsLib(portPageForm, request);
			
			portPageForm.initializeDropDowns();
			portPageForm.getAttributeMap().put("fromFieldList", filteredFromQuest);
			portPageForm.getAttributeMap().put("queueList",filteredFromQuest);
			
			if(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED.equals(preStatus) || PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R.equals(preStatus)){
				if(portPageForm.getMapNeededCount()>0)
						portPageForm.setMapNeededCount(portPageForm.getMapNeededCount()-1);
			}
			request.setAttribute("mapReqCount",portPageForm.getMapNeededCount());
			request.setAttribute("totalCount",portPageForm.getFromPageQuestions().size());
			
			request.setAttribute("queueList",filteredFromQuest);
			sortFromQuestions(portPageForm,filteredFromQuest,true,request);
			request.setAttribute("fromFieldList",filteredFromQuest);
			request.setAttribute("queueCount",String.valueOf(filteredFromQuest.size()));
		

			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			request.setAttribute("toFieldList", portPageForm.getToPageQuestions());
			portPageForm.getAttributeMap().put("queueSize",PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);

			if(portPageForm.getMapNeededCount()>0 && filteredFromQuest.size() > 0){
				request.setAttribute("showNextQueToMapp", "true");
			}
			
			//Resetting previously set codeMappingRequired flag
			portPageForm.getCurrentQuestion().setCodeMappingRequired(false);
			portPageForm.getCurrentQuestion().setBlockIdNbr(null);
			portPageForm.getCurrentQuestion().setLegacyBlockInd("No"); //Resetting value for map another instance hyperlink
			portPageForm.getCurrentQuestion().setAnswerGroupSeqNbr(null);

			return PaginationUtil.paginate(portPageForm,request,"questionMappingPage",mapping);
		}catch(Exception ex){
			logger.fatal("Error in mapAField method of PortPageAction class :" +ex.getMessage(), ex);
			throw new ServletException(ex.getMessage());
		}
	}


	public ActionForward submitMappingQuestions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Port Page: Map Questions page submit.");
		try{
			PortPageForm portPageForm = (PortPageForm)form;
			ArrayList<String> fromQuestionList = new ArrayList<String>();
			ArrayList<String> toQuestionList = new ArrayList<String>();
			ArrayList toAnswerList = new ArrayList();
			ArrayList fromAnswerList = new ArrayList();
			
			String fromQuestionPage = (String)((portPageForm.getAttributeMap().get("fromQuestionPage") ==null)? "false":(portPageForm.getAttributeMap().get("fromQuestionPage")));
			if(fromQuestionPage!= null && fromQuestionPage.equalsIgnoreCase("true"))
			{
				portPageForm.setSearchCriteriaArrayMap(new HashMap<Object,Object>());
			}
			portPageForm.getAttributeMap().put("fromQuestionPage","false");
			
			boolean forceEJBcall=false;
			boolean initLoad=request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
			if(initLoad && !PaginationUtil._dtagAccessed(request)){
				portPageForm.clearAll();
				forceEJBcall=true;
				portPageForm.getAttributeMap().put("queueSize", PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);
			}
			
			if(PortPageUtil.MAPPING_PAGEBUILDER.equals(portPageForm.getMappingType())){
				logger.info("Page Builder Mapping");
				
				ArrayList<String> answersByPageList = new ArrayList<String>();
				
				ArrayList<String> notMappedQuestionList = new ArrayList<String>();
				
				ArrayList<Object> questionsToPersist = new ArrayList<Object>();
				if(forceEJBcall){
					for ( String key : portPageForm.getMappedQuestionsMap().keySet() ) {
						ArrayList<Object> questionList = portPageForm.getMappedQuestionsMap().get(key);
						questionsToPersist.addAll(questionList);
					}
					// Save mapped questions into database
					Object[] oParamsMappedQue = new Object[] {questionsToPersist, portPageForm.getNbsConversionPageMgmtUid()}; // pass waPortMappingUid from session
					String sBeanJndiNameMappedQue = JNDINames.PORT_PAGE_PROXY_EJB;
					String sMethodMappedQue = "insertQuestionAnswers";
					Object questionAnswerListObj = CallProxyEJB.callProxyEJB(oParamsMappedQue, sBeanJndiNameMappedQue, sMethodMappedQue, request.getSession());
					
					for ( String key : portPageForm.getMappedQuestionsMap().keySet() ) {
						boolean isCodeMappingReq = false;
						//if FieldMapping is required and Code Mapping is required then only show it on answer mapping screen.
						ArrayList<Object> fieldDTList = portPageForm.getMappedQuestionsMap().get(key);
						if(fieldDTList!=null && fieldDTList.size()>0){
							//If any question in list has answer Mapping required yes then show question on answer screen.
							for(int j=0;j<fieldDTList.size();j++){
								MappedFromToQuestionFieldsDT fieldDT = (MappedFromToQuestionFieldsDT) fieldDTList.get(j);
								if(fieldDT.isCodeMappingRequired()){
									isCodeMappingReq = true;
									break;
								}
							}
							MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) fieldDTList.get(0);
							if(PortPageUtil.MAPPED_QA_YN_Y.equals(dt.getQuestionMappedCode()) && isCodeMappingReq && !PortPageUtil.NBS_QA_MAPPING_STATUS_AUTO_MAPPED.equals(dt.getStatusCode()) && !PortPageUtil.isDirectDataMove(dt))
								fromQuestionList.add(key);
	
							toQuestionList.add(dt.getToQuestionId());
							
							if(PortPageUtil.MAPPED_QA_YN_N.equals(dt.getQuestionMappedCode()) || (!isCodeMappingReq && !PortPageUtil.NBS_QA_MAPPING_STATUS_AUTO_MAPPED.equals(dt.getStatusCode()))){
								notMappedQuestionList.add(dt.getFromQuestionId());
							}
							
							if(dt.getToQuestionId()!=null && dt.getToQuestionId().contains(PortPageUtil.STRUCTURE_NUMERIC_DUMMY_QUE_SUFFIX)){
								portPageForm.getSn_UNITQuestionList().add(dt);
							}
						}
	
					}
	
					HashMap<String, AnswerMappingDT> toQuestionMap = new HashMap<String, AnswerMappingDT> ();
	
	
					Object[] oParams1 = new Object[] {toQuestionList, toQuestionList, portPageForm.getToPageWaTemplateUid()};
					String sBeanJndiName1 = JNDINames.PORT_PAGE_PROXY_EJB;
					String sMethod1 = "getAnswersToMap";
					Object answerListObj1 = CallProxyEJB.callProxyEJB(oParams1, sBeanJndiName1, sMethod1, request.getSession());
					toAnswerList = (ArrayList) answerListObj1;
					
					PortPageUtil.addAnswerForSNQuestions(portPageForm.getSn_UNITQuestionList(), toAnswerList);
					//Clear the Structure Numeric question list to prevent it to populate duplicate answers while navigating back and forth from question / answer screens. 
					portPageForm.getSn_UNITQuestionList().clear();
					
	                int mapNeedCount=0;
					for(int i=0;i<toAnswerList.size();i++){
						AnswerMappingDT toAnsDT = (AnswerMappingDT) toAnswerList.get(i);
						toQuestionMap.put(toAnsDT.getQuestionIdentifier()+toAnsDT.getCode(), toAnsDT);
					}
	
	
					
					
					Object[] oParams = new Object[] {fromQuestionList, notMappedQuestionList, portPageForm.getFromPageWaTemplateUid()};
		    		String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
		    		String sMethod = "getAnswersToMap";
		    		Object answerListObj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
		    		fromAnswerList = (ArrayList) answerListObj;
		    		portPageForm.getAttributeMap().put("fromAnswerList", fromAnswerList);
		    		portPageForm.setFromPageAnswerList(fromAnswerList);
		    		portPageForm.setToPageAnswerList(toAnswerList);
		    		portPageForm.getAttributeMap().put("answerStatus",new Integer(portPageForm.getAnswerStatus().size()));
		    		portPageForm.getAttributeMap().put("answerMap",new Integer(portPageForm.getAnswerMap().size()));
		    		
		    		// Coded to Text Map code description to text.
		    		boolean updateFirstElement = false;
		    		for ( String key : portPageForm.getMappedQuestionsMap().keySet() ) {
						
		    			logger.info("key: "+key);
						
						ArrayList<Object> fieldDTList = portPageForm.getMappedQuestionsMap().get(key);
						if(fieldDTList!=null && fieldDTList.size()>0){
							for(int j=0;j<fieldDTList.size();j++){
								//Mapping Question screen creates only one element so get first one.
								MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) fieldDTList.get(j);
								if(dt.isFieldMappingRequired() && PortPageUtil.DATA_TYPE_CODED.equals(dt.getFromDataType()) && PortPageUtil.DATA_TYPE_TEXT.equals(dt.getToDataType())){
									updateFirstElement = false;
									Iterator<AnswerMappingDT> iter = fromAnswerList.iterator();
									while(iter.hasNext()){
										AnswerMappingDT fromAnsDT = (AnswerMappingDT) iter.next();
						    			if(dt.getFromQuestionId().equals(fromAnsDT.getQuestionIdentifier())){
						    				if(!updateFirstElement){
						    					dt.setFromCode(fromAnsDT.getCode());
						    					dt.setToCode(fromAnsDT.getCodeDescTxt());
						    					updateFirstElement=true;
						    				}else{
						    					MappedFromToQuestionFieldsDT mqDT = (MappedFromToQuestionFieldsDT) dt.deepCopy();
							    				mqDT.setFromCode(fromAnsDT.getCode());
							    				mqDT.setToCode(fromAnsDT.getCodeDescTxt());
							    				fieldDTList.add(mqDT);
						    				}
						    				//Remove direct mapped answers from fromAnswerList
						    				iter.remove();
						    			}
		
									}break;
								}else if(dt.isFieldMappingRequired() && (((PortPageUtil.DATA_TYPE_NUMERIC.equals(dt.getFromDataType()) || PortPageUtil.DATA_TYPE_TEXT.equals(dt.getFromDataType())) && PortPageUtil.DATA_TYPE_CODED.equals(dt.getToDataType()))
											|| (PortPageUtil.DATA_TYPE_TEXT.equals(dt.getFromDataType()) && PortPageUtil.DATA_TYPE_NUMERIC.equals(dt.getToDataType())))){
									Object[] oParams2 = new Object[] {dt.getFromQuestionId(), portPageForm.getFromPageWaTemplateUid()};
						    		String sBeanJndiName2 = JNDINames.PORT_PAGE_PROXY_EJB;
						    		String sMethod2 = "findAnswersForQuestionsByPage";
						    		Object answerListObj2 = CallProxyEJB.callProxyEJB(oParams2, sBeanJndiName2, sMethod2, request.getSession());
						    		answersByPageList = (ArrayList) answerListObj2;
						    		for(int i=0;i<answersByPageList.size();i++){
						    			//Add From Answers for display purpose
							    		AnswerMappingDT fromAnsDT = new AnswerMappingDT();
							    		fromAnsDT.setQuestionIdentifier(dt.getFromQuestionId());
							    		fromAnsDT.setQuestionLabel(dt.getFromLabel());
							    		fromAnsDT.setCode(answersByPageList.get(i));
							    		fromAnswerList.add(fromAnsDT);
							    			}break;
								}else if(dt.isFieldMappingRequired() && PortPageUtil.DATA_TYPE_NUMERIC.equals(dt.getFromDataType()) && PortPageUtil.DATA_TYPE_TEXT.equals(dt.getToDataType())){
									//Direct move
								}
							}
						}
						updateFirstElement = false;
					}
						
		    		
		    		// Update answer list for auto mapped questions for display purpose
		    		for(int i=0;i<fromAnswerList.size();i++){
		    			AnswerMappingDT fromAnsDT = (AnswerMappingDT) fromAnswerList.get(i);
		    			if(fromAnsDT!=null && "Y".equalsIgnoreCase(fromAnsDT.getAutoMapped())){
		    				logger.info("Setting AutoMapped answers");
		    				fromAnsDT.setStatus(PortPageUtil.STATUS_AUTO_MAPPED);
		    				fromAnsDT.setMapped(PortPageUtil.MAPPED_QA_YN_Y);
		    				fromAnsDT.setMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.CODE_SET_YN));
		    				
		    				AnswerMappingDT toAnsDT = toQuestionMap.get(fromAnsDT.getQuestionIdentifier()+fromAnsDT.getCode());
		    				
		    				if(toAnsDT!=null){
		    					fromAnsDT.setToCode(toAnsDT.getCode());
								fromAnsDT.setToCodeDescTxt(toAnsDT.getCodeDescTxt());
							}
						}
		    			//populate previously mapped answer values from DB.
	    				ArrayList<Object> mappedQuestionList = portPageForm.getMappedQuestionsMap().get(fromAnsDT.getQuestionIdentifier());
						for(int j=0;j<mappedQuestionList.size();j++){
							MappedFromToQuestionFieldsDT que = (MappedFromToQuestionFieldsDT) mappedQuestionList.get(j);
							if(que!=null && que.getFromQuestionId().equals(fromAnsDT.getQuestionIdentifier()) && (que.getFromCode()!=null && que.getFromCode().equals(fromAnsDT.getCode()))){
								fromAnsDT.setToCode(que.getToCode());
								if(PortPageUtil.DATA_TYPE_CODED.equals(que.getToDataType())){
									fromAnsDT.setToCodeDescTxt(CachedDropDowns.getCodeDescTxtForCd(que.getToCode(), que.getToCodeSetNm()));
								}
								fromAnsDT.setStatus(PortPageUtil.STATUS_COMPLETE);
			    				fromAnsDT.setMapped(que.getAnswerMappedCode());
			    				fromAnsDT.setMappedDesc(CachedDropDowns.getCodeDescTxtForCd(que.getAnswerMappedCode(), PortPageUtil.CODE_SET_YN));
							}
						}
		    			
		    			PortPageUtil.nullToBlankAns(fromAnsDT);
	
					}
		    		
					for(int i=0;i<fromAnswerList.size();i++){
		    			AnswerMappingDT fromAnsDT = (AnswerMappingDT) fromAnswerList.get(i);
		    			if(fromAnswerList.contains(fromAnsDT)){
			    			if(fromAnsDT.getStatus().equals(PortPageUtil.STATUS_MAPPING_NEEDED)){
			    				mapNeedCount++;
			    			}
		    			}
					}
		    		portPageForm.setAnsMapNeededCount(mapNeedCount);
				}else{
					fromAnswerList = (ArrayList<Object>)portPageForm.getAttributeMap().get("fromAnswerList");
					if(fromAnswerList == null)
						fromAnswerList=portPageForm.getFromPageAnswerList();
	
					portPageForm.getAttributeMap().put("PageNumber","1");
					
				}
				
				
	
				// Add unmapped To Questions in list to display on Review Mapping Screen.
				ArrayList<Object> unMappedToQuestions = new ArrayList<Object>();
				for(int i=0;i<portPageForm.getToPageQuestions().size();i++){
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) portPageForm.getToPageQuestions().get(i);
					
					MappedFromToQuestionFieldsDT newDT = new MappedFromToQuestionFieldsDT();
					newDT.setToQuestionId(dt.getFromQuestionId());
					newDT.setToCodeSetGroupId(dt.getToCodeSetGroupId());
					newDT.setToDataType(dt.getToDataType());
					newDT.setToNbsUiComponentUid(dt.getToNbsUiComponentUid());
					newDT.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_UNMAPPED_TO_QUE);
					newDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_UNMAPPED_TO_QUE, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
					if(dt.getQuestionGroupSeqNbr() == null || !PortPageUtil.isToQuestionMapped(questionsToPersist, dt.getFromQuestionId())) // For repeating block 'TO question' check if it's mapped or not)
						unMappedToQuestions.add(newDT);
				}
				portPageForm.getMappedQuestionsMap().put(PortPageUtil.UNMAPPED_TO_QUESTIONS, unMappedToQuestions);
				
			}else{
				logger.info("Legacy Mapping");
				//Legacy Mapping
				PortPageUtil.popuplateAnswersListForLegacyEvents(portPageForm, fromAnswerList, toAnswerList, forceEJBcall, request);
			}
			
			boolean existing=request.getParameter("existing")==null ? false : true;
			String contextAction = request.getParameter("context");
			logger.info("contextAction: "+contextAction);
			if(contextAction!=null){
				fromAnswerList = portPageForm.getFromPageAnswerList();
				portPageForm.getAttributeMap().put("PageNumber","1");
				sortFromAnswers(portPageForm,fromAnswerList, existing, request);
				portPageForm.getAttributeMap().put("queueSize", PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);
			}else{
				sortFromAnswers(portPageForm,fromAnswerList,existing,request);
				if(existing){
					filterAnswerLib(portPageForm,request);
				}
			}
			
			if(portPageForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			portPageForm.getAttributeMap().put("queueCount",String.valueOf(fromAnswerList.size()));
			request.setAttribute("mapReqCount",portPageForm.getAnsMapNeededCount());                         //sets the count of Mapping Req Questions
			request.setAttribute("totalCount",portPageForm.getFromPageAnswerList().size());               //sets the count of Total Questions
			request.setAttribute("queueCount",String.valueOf(fromAnswerList.size()));
			request.setAttribute("fromAnswerList", fromAnswerList);
			request.setAttribute("toAnswerList", toAnswerList);
			portPageForm.initializeAnswerDD();

			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			return PaginationUtil.paginate(portPageForm, request,"answerMappingPage" , mapping);

		}catch(Exception ex){
			logger.fatal("Error in submitMappingQuestions method of PortPageAction class :" +ex.getMessage(), ex);
			throw new ServletException(ex.getMessage());
		}

	}

	//To Map An Answer in Answer Mapping Page.
	public ActionForward mapAnAnswer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try{
			PortPageForm portPageForm = (PortPageForm)form;

			String fromQuestionID = portPageForm.getCurrentQuestion().getFromQuestionId();
			String toQuestionID = portPageForm.getCurrentQuestion().getToQuestionId();
			String fromCode = portPageForm.getCurrentQuestion().getFromCode();
			String toCode = portPageForm.getCurrentQuestion().getToCode(); // For coded it gets code otherwise it gets numeric or text value depending on mapping
			
			String toAnswer = (String) request.getParameter("toAnswerId");
			
			logger.debug("Answer Mapping for fromQuestionID:"+fromQuestionID+", toQuestionID: "+toQuestionID+", fromCode: "+fromCode+", toCode: "+toCode+", toAnswer: "+toAnswer);
			
			//For Coded to Numeric get Answer from answer textbox.
			if(toCode == null || toCode.length()==0){
				toCode = toAnswer;
			}
			String preStatus=null;
			boolean isCodeMappingRequired = portPageForm.getCurrentQuestion().isCodeMappingRequired(); // From the UI 'Map Answer?' 
			logger.info("isCodeMappingRequired: "+isCodeMappingRequired);
			for(int i=0;i<portPageForm.getFromPageAnswerList().size();i++){
				AnswerMappingDT answerMappingDT = (AnswerMappingDT) portPageForm.getFromPageAnswerList().get(i);
				if(answerMappingDT!=null && answerMappingDT.getQuestionIdentifier().equals(fromQuestionID) && answerMappingDT.getCode().equals(fromCode)){
					preStatus=answerMappingDT.getStatus();
					answerMappingDT.setStatus(PortPageUtil.STATUS_COMPLETE);
					if(isCodeMappingRequired){
						answerMappingDT.setMapped(PortPageUtil.MAPPED_QA_YN_Y);
						answerMappingDT.setMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.CODE_SET_YN));
						answerMappingDT.setToCode(toCode);
						for(int j=0;j<portPageForm.getToPageAnswerList().size();j++){
							AnswerMappingDT answerMappingToDT = (AnswerMappingDT) portPageForm.getToPageAnswerList().get(j);
							if(answerMappingToDT!=null && answerMappingToDT.getQuestionIdentifier().equals(toQuestionID) && answerMappingToDT.getCode().equals(toCode)){
								answerMappingDT.setToCodeDescTxt(answerMappingToDT.getCodeDescTxt());
								break;
							}
						}
					}else{
						answerMappingDT.setMapped(PortPageUtil.MAPPED_QA_YN_N);
						answerMappingDT.setMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_N, PortPageUtil.CODE_SET_YN));
						answerMappingDT.setToCode("");
						answerMappingDT.setToCodeDescTxt("");
					}


					//Populate map to store value in database.
					ArrayList <Object> questionList = portPageForm.getMappedQuestionsMap().get(fromQuestionID);
					if(questionList!=null && questionList.size()>0){
						MappedFromToQuestionFieldsDT mqDT =(MappedFromToQuestionFieldsDT)questionList.get(0);//Get first element
						if(mqDT.getQuestionGroupSeqNbr()==null){
							//To update existing element see if already mapped element exist in list. Find existing element and update it
							if(!fromCode.equals(mqDT.getFromCode())){
								for(int j=1;j<questionList.size();j++){
									mqDT =(MappedFromToQuestionFieldsDT)questionList.get(j);
									if(fromCode.equals(mqDT.getFromCode())){
										break;
									}
								}
							}
							
							//If auto mapping answer mapped then create new entry of it to display on review mapping screen.
							if("Y".equals(answerMappingDT.getAutoMapped()) && ((mqDT.getFromCode()==null && mqDT.getToCode()==null) || (mqDT.getFromCode()!=null && mqDT.getFromCode().length()==0))){
								MappedFromToQuestionFieldsDT mqDT1 = (MappedFromToQuestionFieldsDT) mqDT.deepCopy();
								mqDT1.setFromCode("");
								mqDT1.setToCode("");
								mqDT1.setAnswerMappedCode("");
								mqDT1.setAnswerMappedDesc("");
								questionList.add(mqDT1);
							}

							//Try to set existing record value
							if(mqDT!=null && fromCode!=null && (fromCode.equals(mqDT.getFromCode()) || (mqDT.getFromCode()==null && mqDT.getToCode()==null) || (mqDT.getFromCode()!=null && mqDT.getFromCode().length()==0))){
								mqDT.setFromCode(fromCode);
								mqDT.setToCode(toCode);
								mqDT.setAnswerMappedCode(answerMappingDT.getMapped());
								
							}else{
								//add another answer mapping record in ArrayList, this list will be persisted into database
								MappedFromToQuestionFieldsDT mqDT1 = (MappedFromToQuestionFieldsDT) mqDT.deepCopy();
								mqDT1.setFromCode(fromCode);
								mqDT1.setToCode(toCode);
								mqDT1.setAnswerMappedCode(answerMappingDT.getMapped());
								mqDT1.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(answerMappingDT.getMapped(), PortPageUtil.CODE_SET_YN));
								questionList.add(mqDT1);
							}
							
							break;
						}else{
							//Repeating to discrete get other instance and map them with same answers
							for(int j=0;j<questionList.size();j++){
								mqDT =(MappedFromToQuestionFieldsDT)questionList.get(j);
								if(fromCode.equals(mqDT.getFromCode())){
									break;
								}
							}

							Map<String, MappedFromToQuestionFieldsDT> unmappedQuestionMap = new HashMap<String, MappedFromToQuestionFieldsDT>();
							//Update
							for(int j=0;j<questionList.size();j++){
								MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT)questionList.get(j);
								//update
								if(!mqDT.getToQuestionId().equals(dt.getToQuestionId()) && mqDT.getAnswerGroupSeqNbr()!=null && dt.getAnswerGroupSeqNbr()!=null 
										&& mqDT.getAnswerGroupSeqNbr().intValue()!=dt.getAnswerGroupSeqNbr().intValue() && (fromCode.equals(dt.getFromCode()) || dt.getFromCode()==null || (dt.getFromCode()!=null && dt.getFromCode().length()==0))){
									unmappedQuestionMap.put(dt.getFromQuestionId()+"|"+dt.getAnswerGroupSeqNbr(), dt);
								}
								//Create
								if(!mqDT.getToQuestionId().equals(dt.getToQuestionId()) && mqDT.getAnswerGroupSeqNbr()!=null && dt.getAnswerGroupSeqNbr()!=null 
										&& mqDT.getAnswerGroupSeqNbr().intValue()!=dt.getAnswerGroupSeqNbr().intValue()){
									unmappedQuestionMap.put(dt.getFromQuestionId()+"|"+dt.getAnswerGroupSeqNbr(), dt);
								}
							}
							
							
							//Try to set existing record value
							if(mqDT!=null && fromCode!=null  && (fromCode.equals(mqDT.getFromCode()) || (mqDT.getFromCode()==null && mqDT.getToCode()==null) || (mqDT.getFromCode()!=null && mqDT.getFromCode().length()==0))){
								mqDT.setFromCode(fromCode);
								mqDT.setToCode(toCode);
								mqDT.setAnswerMappedCode(answerMappingDT.getMapped());
								//update other instances of repeating block question
								for (String key : unmappedQuestionMap.keySet()) {
									MappedFromToQuestionFieldsDT dt = unmappedQuestionMap.get(key);

									if(fromCode.equals(dt.getFromCode()) || (dt.getFromCode()!=null && dt.getFromCode().length()==0)){
										dt.setFromCode(fromCode);
										dt.setToCode(toCode);
										dt.setAnswerMappedCode(answerMappingDT.getMapped());
									}else{
										MappedFromToQuestionFieldsDT newDT = (MappedFromToQuestionFieldsDT) dt.deepCopy();
										newDT.setFromCode(fromCode);
										newDT.setToCode(toCode);
										newDT.setAnswerMappedCode(answerMappingDT.getMapped());
										newDT.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(answerMappingDT.getMapped(), PortPageUtil.CODE_SET_YN));
										questionList.add(newDT);
									}
								}
							}else{
								//add another answer mapping record in ArrayList, this list will be persisted into database
								MappedFromToQuestionFieldsDT mqDT1 = (MappedFromToQuestionFieldsDT) mqDT.deepCopy();
								mqDT1.setFromCode(fromCode);
								mqDT1.setToCode(toCode);
								mqDT1.setAnswerMappedCode(answerMappingDT.getMapped());
								mqDT1.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(answerMappingDT.getMapped(), PortPageUtil.CODE_SET_YN));
								questionList.add(mqDT1);
								
								//create other instances of repeating block question
								for (String key : unmappedQuestionMap.keySet()) {
									MappedFromToQuestionFieldsDT dt = unmappedQuestionMap.get(key);
									MappedFromToQuestionFieldsDT newDT = (MappedFromToQuestionFieldsDT) dt.deepCopy();
									newDT.setFromCode(fromCode);
									newDT.setToCode(toCode);
									newDT.setAnswerMappedCode(answerMappingDT.getMapped());
									newDT.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(answerMappingDT.getMapped(), PortPageUtil.CODE_SET_YN));
									questionList.add(newDT);
								}
							}
						}
					}
				}

			}


			Map<Object,Object> ansFilterMap=new HashMap<Object,Object>();
			ansFilterMap =portPageForm.getSearchCriteriaArrayMap();   //stores the filter criteria before mapping answer.

			//To return the filtered list ,if there are any filters applied before mapping an answer.
			Collection<Object> filteredAnsList  = getFilteredAnswers(portPageForm.getFromPageAnswerList(),ansFilterMap);
			portPageForm.initializeAnswerDD();
			
			portPageForm.getAttributeMap().put("fromAnswerList", filteredAnsList);
			portPageForm.getAttributeMap().put("queueList",filteredAnsList);
			request.setAttribute("queueList",filteredAnsList);
			sortFromAnswers(portPageForm,filteredAnsList,true,request);
			request.setAttribute("fromAnswerList",filteredAnsList);
			
			if(preStatus!=null&&preStatus.equals(PortPageUtil.STATUS_MAPPING_NEEDED)){
				if(portPageForm.getAnsMapNeededCount()>0)
						portPageForm.setAnsMapNeededCount(portPageForm.getAnsMapNeededCount()-1);
			}
			request.setAttribute("mapReqCount",portPageForm.getAnsMapNeededCount());
			request.setAttribute("totalCount",portPageForm.getFromPageAnswerList().size());
			request.setAttribute("queueCount",String.valueOf(filteredAnsList.size()));

			request.setAttribute("toAnswerList", portPageForm.getToPageAnswerList());
			portPageForm.getAttributeMap().put("queueSize",PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);
			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			
			//Reset ToCode dropdown so that for code to numeric it reads value from toAnswerId textbox. 
	    	portPageForm.getCurrentQuestion().setToCode("");
	    	
	    	if(portPageForm.getAnsMapNeededCount()>0 && filteredAnsList.size() >0){	
				request.setAttribute("showNextQueToMapp", "true");
			}
	    	
			return PaginationUtil.paginate(portPageForm,request,"answerMappingPage",mapping);
		}catch(Exception ex){
			logger.fatal("Error in mapAnAnswer method of PortPageAction class :" +ex.getMessage(), ex);
			throw new ServletException(ex.getMessage());
		}
	}


	/*Filtering Code Starts here*/
	public ActionForward filterFromQuestionsSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			PortPageForm portPageForm=(PortPageForm) form;
			Collection<Object> filteredFromQuest=filterFromQuestionsLib(portPageForm,request);
			if(filteredFromQuest != null){
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
			}else{
				filteredFromQuest=(ArrayList<Object> ) portPageForm.getFromPageQuestions();
			}
			portPageForm.getAttributeMap().put("fromFieldList", filteredFromQuest);
			portPageForm.getAttributeMap().put("queueList",filteredFromQuest);
			request.setAttribute("queueList",filteredFromQuest);
			sortFromQuestions(portPageForm,filteredFromQuest,true,request);
	
			request.setAttribute("fromFieldList",filteredFromQuest);
			request.setAttribute("queueCount",String.valueOf(filteredFromQuest.size()));
			portPageForm.getAttributeMap().put("PageNumber","1");
			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			request.setAttribute("totalCount",portPageForm.getFromPageQuestions().size());
			request.setAttribute("mapReqCount",portPageForm.getMapNeededCount());
			return PaginationUtil.paginate(portPageForm,request,"questionMappingPage",mapping);
		}catch(Exception ex){
			logger.fatal("Error in filterFromQuestionsSubmit method of PortPageAction class :" +ex.getMessage(), ex);
			throw new ServletException(ex.getMessage());
		}
	}

	public Collection<Object> filterFromQuestionsLib(PortPageForm portPageForm,HttpServletRequest request){
		Collection<Object> filteredQuest=new ArrayList<Object>();
		QueueUtil queueUtil=new QueueUtil();
		String srchCriteriaStatus=null;
		String srchCriteriaMap=null;
		String sortOrderParam=null;
		try{
			Map<Object, Object> searchCriteriaMap = portPageForm.getSearchCriteriaArrayMap();
			ArrayList<Object> fromPageQuestions = (ArrayList<Object> ) portPageForm.getFromPageQuestions();
			 if(fromPageQuestions == null)
				 fromPageQuestions = (ArrayList<Object>)request.getAttribute("fromFieldList");
			filteredQuest = filterFromQuestions(fromPageQuestions,searchCriteriaMap,portPageForm,request);
			
			String[] status = (String[]) searchCriteriaMap.get(PortPageUtil.FILTER_QUESTION_STATUS);
			String[] map=(String[]) searchCriteriaMap.get(PortPageUtil.FILTER_QUESTION_MAP);
			String[] frmDataType=(String[]) searchCriteriaMap.get(PortPageUtil.FILTER_QUESTION_FRMDATATYPE);
			String[] toDataType=(String[]) searchCriteriaMap.get(PortPageUtil.FILTER_QUESTION_TODATATYPE);
			String filterByFromId = null;
			String filterByFrmLab=null;
			String filterByFrmValSet=null;
			String filterByToId=null;
			String filterByToLab=null;
			String filterByToValSet=null;

			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				filterByFromId = HTMLEncoder.encodeHtml(String.valueOf(searchCriteriaMap.get("SearchText1_FILTER_TEXT")));
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				filterByFrmLab = HTMLEncoder.encodeHtml(String.valueOf(searchCriteriaMap.get("SearchText2_FILTER_TEXT")));
			}
			if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
				filterByToId = HTMLEncoder.encodeHtml(String.valueOf(searchCriteriaMap.get("SearchText3_FILTER_TEXT")));
			}
			if(searchCriteriaMap.get("SearchText4_FILTER_TEXT")!=null){
				filterByToLab = HTMLEncoder.encodeHtml(String.valueOf(searchCriteriaMap.get("SearchText4_FILTER_TEXT")));
			}
			if(searchCriteriaMap.get("SearchText5_FILTER_TEXT")!=null){
				filterByFrmValSet = HTMLEncoder.encodeHtml(String.valueOf(searchCriteriaMap.get("SearchText5_FILTER_TEXT")));
			}
			if(searchCriteriaMap.get("SearchText6_FILTER_TEXT")!=null){
				filterByToValSet = HTMLEncoder.encodeHtml(String.valueOf(searchCriteriaMap.get("SearchText6_FILTER_TEXT")));
			}
			
			
			Integer statusCount = new Integer(status == null ? 0 : status.length);
			Integer mapCount = new Integer(map == null  ? 0 : map.length);
			Integer frmDataTypeCount = new Integer(frmDataType == null  ? 0 : frmDataType.length);
			Integer toDataTypeCount = new Integer(toDataType == null  ? 0 : toDataType.length);
			
			
			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(statusCount.equals((portPageForm.getPortStatus().size())) &&
					(mapCount.equals(portPageForm.getMap().size())) && 
					(frmDataTypeCount.equals(portPageForm.getFrmDataType().size())) &&
					(toDataTypeCount.equals(portPageForm.getToDataType().size())) &&
					filterByFromId == null && 
					filterByFrmLab == null && 
					filterByToId == null && 
					filterByToLab == null &&
					filterByFrmValSet == null &&
					filterByToValSet== null) {
				
				String sortMethod = getSortMethod(request, portPageForm);
				String direction = getSortDirection(request, portPageForm);		
			
				//Make the A-Z icons consistent during sorting and filtering 
				if(direction == null || (direction != null && direction.equals("1")))
					direction = "2";
				else if(direction != null && direction.equals("2"))
					direction = "1";
				
				boolean invDirectionFlag = true;
				if (direction != null && direction.equals("2"))
					invDirectionFlag = false;

				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<Object,Object> sColl =  portPageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) portPageForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = PortPageUtil.getSortCriteriaQuestion(invDirectionFlag == true ? "1" : "2", sortMethod);
				}
				if(sortOrderParam != null && sortOrderParam.trim().length()==0){
					sortOrderParam = "Status @ in descending order" ;
				}
				
				sortOrderParam = PortPageUtil.getSortCriteriaQuestion(invDirectionFlag == true ? "1" : "2", sortMethod);
	
				Map<Object,Object> searchCriteriaColl = new TreeMap<Object,Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				portPageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return filteredQuest;			
			}
			
			//Filter code

			 srchCriteriaStatus = queueUtil.getSearchCriteria(portPageForm.getPortStatus(), status, NEDSSConstants.FILTERBYSUBMITTEDBY);
			 srchCriteriaMap = queueUtil.getSearchCriteria(portPageForm.getMap(), map, NEDSSConstants.FILTERBYSUBMITTEDBY);
			String srchCriteriaFromDataType = queueUtil.getSearchCriteria(portPageForm.getFrmDataType(), frmDataType, NEDSSConstants.FILTERBYSUBMITTEDBY);
			String srchCriteriaToDataType = queueUtil.getSearchCriteria(portPageForm.getToDataType(), toDataType, NEDSSConstants.FILTERBYSUBMITTEDBY);

			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			String sortMethod = getSortMethod(request, portPageForm);
			String direction = getSortDirection(request, portPageForm);

			boolean invDirectionFlag = true;
			if (direction != null && direction.equals("2"))
				invDirectionFlag = false;

			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object,Object> sColl =  portPageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object,Object>() : (TreeMap<Object,Object>) portPageForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} 
			else {
				sortOrderParam = PortPageUtil.getSortCriteriaQuestion(invDirectionFlag == true ? "1" : "2", sortMethod);
			}
			if(sortOrderParam != null && sortOrderParam.trim().length()==0){
				sortOrderParam = "Status @ in descending order" ;
			}
			
			//set the error message to the form

			if(searchCriteriaColl!=null)
				searchCriteriaColl.put("sortSt", sortOrderParam);	
			if(srchCriteriaStatus != null)
				searchCriteriaColl.put("INV111", srchCriteriaStatus);
			if(srchCriteriaFromDataType  != null)
				searchCriteriaColl.put("INV222", srchCriteriaFromDataType);
			if(srchCriteriaMap!= null)
				searchCriteriaColl.put("INV333", srchCriteriaMap);
			if(srchCriteriaToDataType != null)
				searchCriteriaColl.put("INV444", srchCriteriaToDataType);
			if(filterByFromId != null)
				searchCriteriaColl.put("INV001", filterByFromId);
			if(filterByFrmLab != null)
				searchCriteriaColl.put("INV002", filterByFrmLab);
			if(filterByToId != null)
				searchCriteriaColl.put("INV003", filterByToId);
			if(filterByToLab != null)
				searchCriteriaColl.put("INV004", filterByToLab);

			portPageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}catch (Exception e){
			logger.error("Error while filtering the filterFromQuestionLib:"+e.toString(), e);
		}
		return filteredQuest;
	}

	//Filter Question List in Question Mapping Page.
	private Collection<Object> filterFromQuestions(Collection<Object> fromQuestList,Map<Object,Object> searchCriteriaMap, PortPageForm portPageForm, HttpServletRequest request) throws Exception{
		QueueUtil queueUtil=new QueueUtil();
		try{
			String[] status = (String[]) searchCriteriaMap.get(PortPageUtil.FILTER_QUESTION_STATUS);
			String[] map=(String[]) searchCriteriaMap.get(PortPageUtil.FILTER_QUESTION_MAP);
			String[] frmDataType=(String[]) searchCriteriaMap.get(PortPageUtil.FILTER_QUESTION_FRMDATATYPE);
			String[] toDataType=(String[]) searchCriteriaMap.get(PortPageUtil.FILTER_QUESTION_TODATATYPE);
			String filterByFromId = null;
			String filterByFrmLab=null;
			String filterByFrmValSet=null;
			String filterByToId=null;
			String filterByToLab=null;
			String filterByToValSet=null;

			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				filterByFromId = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				filterByFrmLab = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
				filterByToId = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText4_FILTER_TEXT")!=null){
				filterByToLab = (String) searchCriteriaMap.get("SearchText4_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText5_FILTER_TEXT")!=null){
				filterByFrmValSet = (String) searchCriteriaMap.get("SearchText5_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText6_FILTER_TEXT")!=null){
				filterByToValSet = (String) searchCriteriaMap.get("SearchText6_FILTER_TEXT");
			}

			Map<Object, Object> statusMap = new HashMap<Object,Object>();
			Map<Object, Object> mapMap = new HashMap<Object,Object>();
			Map<Object, Object> frmDtMap = new HashMap<Object,Object>();
			Map<Object, Object> toDtMap = new HashMap<Object,Object>();

			if (status != null && status.length >0)
				statusMap = queueUtil.getMapFromStringArray(status);
			if (map != null && map.length >0)
				mapMap = queueUtil.getMapFromStringArray(map);
			if (frmDataType != null && frmDataType.length >0)
				frmDtMap = queueUtil.getMapFromStringArray(frmDataType);
			if (toDataType != null && toDataType.length >0)
				toDtMap = queueUtil.getMapFromStringArray(toDataType);


			if (statusMap != null && statusMap.size()>0)
				fromQuestList= PortPageUtil.filterByStatus(fromQuestList, statusMap);
			if (mapMap != null && mapMap.size()>0)
				fromQuestList= PortPageUtil.filterByMap(fromQuestList, mapMap);
			if (frmDtMap != null && frmDtMap.size()>0)
				fromQuestList= PortPageUtil.filterByFrmDT(fromQuestList, frmDtMap);
			if (toDtMap != null && toDtMap.size()>0)
				fromQuestList= PortPageUtil.filterByToDT(fromQuestList, toDtMap);

			if(filterByFromId!= null){
				fromQuestList = PortPageUtil.filterByText(fromQuestList, filterByFromId, NEDSSConstants.UNIQUE_ID);
			}
			if(filterByFrmLab!= null){
				fromQuestList = PortPageUtil.filterByText(fromQuestList, filterByFrmLab, NEDSSConstants.LABEL);
			}
			if(filterByFrmValSet!= null){
				fromQuestList = PortPageUtil.filterByText(fromQuestList, filterByFrmValSet, NEDSSConstants.CODE_SET_NM);
			}
			if(filterByToId!= null){
				fromQuestList = PortPageUtil.filterByText(fromQuestList, filterByToId, NEDSSConstants.TO_UNIQUE_ID);
			}
			if(filterByToLab!= null){
				fromQuestList = PortPageUtil.filterByText(fromQuestList, filterByToLab, NEDSSConstants.TO_LABEL);
			}
			if(filterByToValSet!= null){
				fromQuestList = PortPageUtil.filterByText(fromQuestList, filterByToValSet, NEDSSConstants.TO_CODE_SET_NM);
			}

			
            
		}catch(Exception ex){
			logger.fatal("Error sorting in sortFromAnswers method in PortPageAction : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return fromQuestList;
	}


	//To clear the sorts and filters applied in Question Mapping Page.
	public ActionForward clearFilters(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		try{
			PortPageForm portPageForm = (PortPageForm)form;
			boolean existing=request.getParameter("existing")==null ? false : true;
			portPageForm.clearAll();
			portPageForm.setSearchCriteriaArrayMap(new HashMap<Object,Object>());

			//portPageForm.getFromPageQuestions().addAll(portPageForm.getDisplayFromQuestionsMap().values());
			sortFromQuestions(portPageForm, portPageForm.getFromPageQuestions(), existing, request);
			if(portPageForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			portPageForm.getAttributeMap().put("fromFieldList",portPageForm.getFromPageQuestions());
			portPageForm.getAttributeMap().put("queueList",portPageForm.getFromPageQuestions());
			portPageForm.getAttributeMap().put("fromAnswerList",portPageForm.getFromPageAnswerList());
			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			request.setAttribute("fromFieldList", portPageForm.getFromPageQuestions());
			request.setAttribute("queueCount",  Integer.toString(portPageForm.getFromPageQuestions().size()));
			request.setAttribute("totalCount",portPageForm.getFromPageQuestions().size());
			request.setAttribute("mapReqCount",portPageForm.getMapNeededCount());
			request.setAttribute("toFieldList", portPageForm.getToPageQuestions());
			portPageForm.initializeDropDowns();

			portPageForm.getAttributeMap().put("PageNumber","1");
			portPageForm.getAttributeMap().put("queueSize", PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);

			return PaginationUtil.paginate(portPageForm,request,"questionMappingPage",mapping);
		}catch(Exception ex){
			logger.fatal("Error in clearFilters method of PortPageAction class :" +ex.getMessage(), ex);
			throw new ServletException(ex.getMessage());
		}
	}


	public ActionForward submitAnswerMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("Port Page: Map Answers submit");
		try{
			PortPageForm portPageForm = (PortPageForm)form;
			ArrayList<Object> questionAnswerList = new ArrayList<Object>(); 

			String context = request.getParameter("context");
			logger.info("context: "+context);
			if("pagination".equalsIgnoreCase(context)){
				request.setAttribute("questionAnswerListToReview", portPageForm.getMappedQuestionAnswerListForReview());
				request.setAttribute("PageTitle", "Manage Pages: Port Page");

				return mapping.findForward("reviewMappingPage");
			}

			boolean forceEJBcall=false;
			boolean initLoad=request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
			if(initLoad && !PaginationUtil._dtagAccessed(request)){
				portPageForm.clearAll();
				forceEJBcall=true;
				request.setAttribute("ActionMode",HTMLEncoder.encodeHtml("InitLoad"));
			}
			ArrayList<Object> questionsToPersist = new ArrayList<Object>();
			
			if(PortPageUtil.MAPPING_PAGEBUILDER.equals(portPageForm.getMappingType())){
				logger.info("Page Builder Mapping.");
				if(forceEJBcall){
	
					ArrayList<String> repeatingBlockQuesionList =  new ArrayList<String>();
					
		
					for ( String key : portPageForm.getMappedQuestionsMap().keySet() ) {
						ArrayList<Object> questionList = portPageForm.getMappedQuestionsMap().get(key);
						questionsToPersist.addAll(questionList);
						
						// create list of repeating block question
						for(int i=0;i<questionList.size();i++){
							MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) questionList.get(i);
							if(dt.getQuestionGroupSeqNbr()!=null && dt.getQuestionGroupSeqNbr()>0){
								repeatingBlockQuesionList.add(dt.getFromQuestionId());
							}
							break;
						}
					}
					
					//Set answers for unmapped / newly added repeating block instances.
					for(int i=0;i<repeatingBlockQuesionList.size();i++){
						String fromQuestionIdentifier = repeatingBlockQuesionList.get(i);
						ArrayList<Object> repeatingMappedQueList = portPageForm.getMappedQuestionsMap().get(fromQuestionIdentifier);
						if(repeatingMappedQueList!=null){
							ArrayList<Object> questionList = PortPageUtil.mapRepeatingBlockInstances(repeatingMappedQueList);
							questionsToPersist.addAll(questionList);
						}
					}
		
					logger.info("Processing core questions to display on review mapping screen.");
					//Populate core questions to display.
			    	
			    	//find FromPage core questions
			    	Object[] oParamsFromPageCoreQuestion = new Object[] {portPageForm.getFromPageWaTemplateUid()};
					String sBeanJndiNameCoreQuestion = JNDINames.PORT_PAGE_PROXY_EJB;
					String sMethodCoreQuestion = "findCoreQuestionsByPage";
					Object fromPageCoreQuestionsObj = CallProxyEJB.callProxyEJB(oParamsFromPageCoreQuestion, sBeanJndiNameCoreQuestion, sMethodCoreQuestion, request.getSession());
					ArrayList<Object> fromPageCoreQuestionsList =  (ArrayList<Object>) fromPageCoreQuestionsObj;
					
					//find ToPage core questions
					Object[] oParamsToPageCoreQuestion = new Object[] {portPageForm.getToPageWaTemplateUid()};
					Object toPageCoreQuestionsObj = CallProxyEJB.callProxyEJB(oParamsToPageCoreQuestion, sBeanJndiNameCoreQuestion, sMethodCoreQuestion, request.getSession());
					ArrayList<Object> toPageCoreQuestionsList =  (ArrayList<Object>) toPageCoreQuestionsObj;
					
					Map<String, MappedFromToQuestionFieldsDT> toCoreQueMap = new HashMap<>();
					for(int i=0;i<toPageCoreQuestionsList.size();i++){
						MappedFromToQuestionFieldsDT toQue = (MappedFromToQuestionFieldsDT) toPageCoreQuestionsList.get(i);
						if(toQue!=null){
							toCoreQueMap.put(toQue.getFromQuestionId(), toQue);
						}
					}
					
					//Populate core question list to display
					Iterator<Object> iter = fromPageCoreQuestionsList.iterator();
					while(iter.hasNext()){
						MappedFromToQuestionFieldsDT fromQue = (MappedFromToQuestionFieldsDT) iter.next();
						
						if(fromQue!=null){
							//Remove Core question from displaying, if its already mapped.
							if(portPageForm.getMappedQuestionsMap().containsKey(fromQue.getFromQuestionId())){
								iter.remove();
							}else{
								MappedFromToQuestionFieldsDT toQue = toCoreQueMap.get(fromQue.getFromQuestionId());
								if(toQue!=null){
									fromQue.setToQuestionId(toQue.getFromQuestionId());
									fromQue.setToLabel(toQue.getFromLabel());
									fromQue.setToCode(toQue.getFromCode());
									fromQue.setToDataType(toQue.getFromDataType());
									fromQue.setToDbLocation(toQue.getFromDbLocation());
									fromQue.setToCodeSetNm(toQue.getFromCodeSetNm());
									fromQue.setToCodeSetGroupId(toQue.getCodeSetGroupId());
									fromQue.setToNbsUiComponentUid(toQue.getFromNbsUiComponentUid());
									fromQue.setQuestionMappedCode(PortPageUtil.MAPPED_QA_YN_Y);
									fromQue.setQuestionMappedDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.MAPPED_QA_YN_Y, PortPageUtil.CODE_SET_YN));
									toCoreQueMap.remove(fromQue.getFromQuestionId());
								}
								fromQue.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_CORE);
								fromQue.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_CORE, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
							}
						}
					}
					
					ArrayList<Object> toQueList = new ArrayList<Object>();
					toQueList.addAll(toCoreQueMap.values());
					for(int i=0;i<toQueList.size();i++){
						MappedFromToQuestionFieldsDT toQue = (MappedFromToQuestionFieldsDT) toQueList.get(i);
						if(toQue!=null){
							if(!portPageForm.getDisplayToQuestionsMap().containsKey(toQue.getFromQuestionId())){ // If question is exist in ToQuestion List don't include as Core question.
								MappedFromToQuestionFieldsDT que = new MappedFromToQuestionFieldsDT();
								que.setToQuestionId(toQue.getFromQuestionId());//Utilizing the same method to read from DB, for from and to questions its reading in to from fields.
								que.setToLabel(toQue.getFromLabel());
								que.setToCode(toQue.getFromCode());
								que.setToDataType(toQue.getFromDataType());
								que.setToDbLocation(toQue.getFromDbLocation());
								que.setToCodeSetNm(toQue.getFromCodeSetNm());
								que.setToCodeSetGroupId(toQue.getCodeSetGroupId());
								que.setToNbsUiComponentUid(toQue.getFromNbsUiComponentUid());
								que.setStatusCode(PortPageUtil.NBS_QA_MAPPING_STATUS_CORE);
								que.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(PortPageUtil.NBS_QA_MAPPING_STATUS_CORE, PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
								
								fromPageCoreQuestionsList.add(que);
							}
						}
					}
					questionsToPersist.addAll(fromPageCoreQuestionsList);
					
					// Save mapping into database
					Object[] oParams = new Object[] {questionsToPersist, portPageForm.getNbsConversionPageMgmtUid()}; // pass waPortMappingUid from session
					String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
					String sMethod = "insertQuestionAnswers";
					Object questionAnswerListObj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
					boolean insertResult = (boolean) questionAnswerListObj;
		
					// Reads mapping from database
					Object[] oParams1 = new Object[] {portPageForm.getFromPageWaTemplateUid(), portPageForm.getToPageWaTemplateUid(), portPageForm.getNbsConversionPageMgmtUid(), PortPageUtil.MAPPING_PAGEBUILDER};
					String sBeanJndiName1 = JNDINames.PORT_PAGE_PROXY_EJB;
					String sMethod1 = "viewPortPageMapping";
					Object questionAnswerListObj1 = CallProxyEJB.callProxyEJB(oParams1, sBeanJndiName1, sMethod1, request.getSession());
					 questionAnswerList =  (ArrayList<Object>) questionAnswerListObj1;
		
					 for(int i=0;i<questionAnswerList.size();i++){
						 MappedFromToQuestionFieldsDT que = (MappedFromToQuestionFieldsDT) questionAnswerList.get(i);
						 if((PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE.equals(que.getStatusCode()) || PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE_REPEATING_BLOCK.equals(que.getStatusCode())) && que.getBlockIdNbr()!=null && que.getBlockIdNbr()>0){
							 //que.setToLabel(que.getToLabel()+"("+que.getBlockIdNbr()+")");
							 PortPageUtil.updateToQueLabelForRepeatingTypesMapping(que,que.getBlockIdNbr(),que.getToLabel());
						 }
						 
						 //Populates dummy questions for 'Structured Numeric' and 'Coded with Other' mappings
						 PortPageUtil.populateDummyQuestionDetails(portPageForm, que);
					 }
		
					//Find page name to display on review screen information bar.
					PortPageUtil.setPageAndConditionDetailsToDisplay(portPageForm,request);
					
			    	
			    	
				}else{
					questionAnswerList=portPageForm.getMappedQuestionAnswerListForReview();                        
				}
			
			}else{
				logger.info("Legacy mapping");
				//Legacy Mapping 
				if(forceEJBcall){
					
					PortPageUtil.storeMappingInWAConversionMapping(portPageForm.getMappedQuestionsMap(), portPageForm.getNbsConversionPageMgmtUid(), request);

					PortPageUtil portUtil = new PortPageUtil();
					ArrayList<Object> questionsListForReview = portUtil.getMappedQuestionAnswers(null, portPageForm.getToPageWaTemplateUid(), portPageForm.getNbsConversionPageMgmtUid(), PortPageUtil.MAPPING_LEGACY,request);
					portPageForm.setMappedQuestionAnswerListForReview(questionsListForReview);
					questionAnswerList = questionsListForReview;
				}else{
					questionAnswerList=portPageForm.getMappedQuestionAnswerListForReview();                        
				}
			}
			
			portPageForm.getAttributeMap().put("questionAnswerList",questionAnswerList);
	    	portPageForm.setMappedQuestionAnswerListForReview(questionAnswerList);
	    	portPageForm.initializeReviewDropDowns();
	    	portPageForm.getAttributeMap().put("fromDataType",new Integer(portPageForm.getRevFromDataType().size()));
	    	portPageForm.getAttributeMap().put("mapQuestion",new Integer(portPageForm.getRevMapQuestion().size()));
	    	portPageForm.getAttributeMap().put("toDataType",new Integer(portPageForm.getRevToDataType().size()));
	    	portPageForm.getAttributeMap().put("mapAnswer",new Integer(portPageForm.getRevMapAnswer().size()));
	    	
			Collection filteredList = filterReviewPageLibrary(portPageForm, request);
			if(filteredList != null){
				questionAnswerList=(ArrayList<Object>) filteredList;
	    		boolean existing = request.getParameter("existing") == null ? false : true;
	    		sortReviewPageLibrary(portPageForm,questionAnswerList,existing,request);
			}
			
			//To make sure SelectAll is checked, see if no criteria is applied
			if(portPageForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			
			request.setAttribute("FromPageName", portPageForm.getAttributeMap().get("FromPageName"));
			request.setAttribute("ToPageName", portPageForm.getAttributeMap().get("ToPageName"));
	    	request.setAttribute("FromPageTotalAssociatedConditions", portPageForm.getAttributeMap().get("FromPageTotalAssociatedConditions"));
	    	request.setAttribute("questionAnswerListToReview", questionAnswerList);
	    	portPageForm.getAttributeMap().put("questionAnswerListToReview", questionAnswerList);
			portPageForm.getAttributeMap().put("queueCount", String.valueOf(questionAnswerList.size()));
			portPageForm.getAttributeMap().put("queueSize",PortPageUtil.REVIEW_PAGE_QUEUE_SIZE);
			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			request.setAttribute("queueCount",String.valueOf(questionAnswerList.size()));
			portPageForm.getAttributeMap().put("PageNumber", "1");
			return PaginationUtil.paginate(portPageForm,request,"reviewMappingPage",mapping);
		}catch(Exception ex){
			logger.fatal("Error in submitAnswerMapping method of PortPageAction class :" +ex.getMessage(), ex);
			throw new ServletException(ex.getMessage());
		}
		
	}

	/*Sorting Methods*/
	//Sorts the Question list in Question Mapping Page.
	private void sortFromQuestions(PortPageForm portPageForm, Collection<Object>  fromFieldList, boolean existing, HttpServletRequest request) throws Exception{

		try{
			// Retrieve sort-order and sort-direction from displaytag params
			String sortMethod = getSortMethod(request, portPageForm);
			String direction = getSortDirection(request, portPageForm);

			boolean invDirectionFlag = true;
			if (direction != null && direction.equals("2"))
				invDirectionFlag = false;

			NedssUtils util = new NedssUtils();

			//Read from properties file to determine default sort order
			if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				sortMethod = "getStatusDesc";
				invDirectionFlag = false;      //sorts according to status in descending order.
			}

			if (sortMethod != null && fromFieldList != null && fromFieldList.size() > 0) {
				updateListBeforeSort(fromFieldList);
				util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) fromFieldList, invDirectionFlag);
				updateListAfterSort(fromFieldList);
			}
			String contextAction = request.getParameter("context");
			logger.info("contextAction: "+contextAction);
			if(!existing || "ReturnToQuestion".equalsIgnoreCase(contextAction) ) {
				//Finally put sort criteria in form
				String sortOrderParam = PortPageUtil.getSortCriteriaQuestion(invDirectionFlag == true ? "1" : "2", sortMethod);
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				portPageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			}
		}catch(Exception ex){
			logger.fatal("Error sorting in sortFromQuestions method in PortPageAction : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
	}

	//To get the Sort Method i.e.,based on what column to sort the list.
	private String getSortMethod(HttpServletRequest request, PortPageForm  manageForm ) {
		try{
			if (PaginationUtil._dtagAccessed(request)) {
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
			} else{
				return manageForm.getAttributeMap().get("methodName") == null ? null : (String) manageForm.getAttributeMap().get("methodName");
			}
		}catch(Exception ex){
			logger.fatal("Error sorting in getSortMethod method in PortPageAction : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
	}

	//To get the direction i.e.,in which direction to sort A-Z or Z-A.
	private String getSortDirection(HttpServletRequest request, PortPageForm manageForm) {
		try{
			if (PaginationUtil._dtagAccessed(request)) {
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			} else{
				return manageForm.getAttributeMap().get("sortOrder") == null ? "1": (String) manageForm.getAttributeMap().get("sortOrder");
			}
		}catch(Exception ex){
			logger.fatal("Error sorting in getSortDirection method in PortPageAction : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
	}


	//Filter code for answer mapping page//
	//To filter the question list in Answer Mapping Page.
	public ActionForward filterAnswerLibSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		try{
			PortPageForm portPageForm = (PortPageForm) form;
			Collection<Object> filteredAnsList=filterAnswerLib(portPageForm,request);
			if(filteredAnsList != null){
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
			}else{
				filteredAnsList=(ArrayList<Object> ) portPageForm.getFromPageAnswerList();
			}
			portPageForm.getAttributeMap().put("fromAnswerList", filteredAnsList);
			portPageForm.getAttributeMap().put("queueList",filteredAnsList);
			request.setAttribute("queueList",filteredAnsList);
			sortFromAnswers(portPageForm,filteredAnsList,true,request);
			request.setAttribute("fromAnswerList",filteredAnsList);
			request.setAttribute("queueCount",String.valueOf(filteredAnsList.size()));
			request.setAttribute("totalCount",portPageForm.getFromPageAnswerList().size());
			request.setAttribute("mapReqCount",portPageForm.getAnsMapNeededCount());
			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			portPageForm.getAttributeMap().put("PageNumber","1");
			return PaginationUtil.paginate(portPageForm,request,"answerMappingPage",mapping);
		}catch(Exception ex){
			logger.fatal("Error in filterAnswerLibSubmit : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
	}

	private Collection<Object>  filterAnswerLib(PortPageForm portPageForm, HttpServletRequest request) {

		QueueUtil queueUtil=new QueueUtil();

		Collection<Object> filteredAnsList=new ArrayList<Object>();


		String srchCriteriaStatus=null;
		String srchCriteriaMap=null;
		String sortOrderParam=null;
		try{
			Map<Object, Object> searchCriteriaMap = portPageForm.getSearchCriteriaArrayMap();
			ArrayList<Object> fromPageAnsList = (ArrayList<Object> ) portPageForm.getFromPageAnswerList();

			filteredAnsList = getFilteredAnswers(fromPageAnsList,searchCriteriaMap);

			String[] map = (String[]) searchCriteriaMap.get(PortPageUtil.FILTER_ANSWER_MAP);
			String[] status = (String[]) searchCriteriaMap.get(PortPageUtil.FILTER_ANSWER_STATUS);
			String filterByFromId = null;
			String filterByFrmLab=null;
			String filterByFrmCd=null;
			String filterByFrmCdDesc=null;
			String filterByToCd=null;
			String filterByToCdDesc=null;

			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				filterByFromId = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				filterByFrmLab = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
				filterByFrmCd = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText4_FILTER_TEXT")!=null){
				filterByFrmCdDesc = (String) searchCriteriaMap.get("SearchText4_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText5_FILTER_TEXT")!=null){
				filterByToCd = (String) searchCriteriaMap.get("SearchText5_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText6_FILTER_TEXT")!=null){
				filterByToCdDesc = (String) searchCriteriaMap.get("SearchText6_FILTER_TEXT");
			}

			Integer statusCount = new Integer(status==null? 0 : status.length);
			Integer mapCount = new Integer(map==null? 0 : map.length);

			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if((statusCount.equals(portPageForm.getAttributeMap().get("answerStatus"))) && 
					mapCount.equals((portPageForm.getAttributeMap().get("answerMap"))) &&
					filterByFromId == null && filterByFrmLab == null && filterByFrmCd == null && filterByFrmCdDesc == null && filterByToCd == null && filterByToCdDesc == null)
			{

				String sortMethod = getSortMethod(request, portPageForm);
				String direction = getSortDirection(request, portPageForm);			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<?,?> sColl =  portPageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<?,?>) portPageForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = PortPageUtil.getSortCriteriaAnswer(direction, sortMethod);
				}				
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				portPageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return null;				
			}

			ArrayList<Object> statusList = portPageForm.getAnswerStatus();
			ArrayList<Object> mapList = portPageForm.getAnswerMap();

			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			String sortMethod = getSortMethod(request, portPageForm);
			String direction = getSortDirection(request, portPageForm);			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object, Object> sColl =  portPageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<Object, Object>) portPageForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = PortPageUtil.getSortCriteriaAnswer(direction, sortMethod);
			}

			srchCriteriaMap = queueUtil.getSearchCriteria(mapList, map, NEDSSConstants.FILTERBYSUBMITTEDBY);
			srchCriteriaStatus = queueUtil.getSearchCriteria(statusList, status, NEDSSConstants.FILTERBYSUBMITTEDBY);



			//set the error message to the form to display icon next to column after applying filter and sort
			if(sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if(srchCriteriaStatus  != null)
				searchCriteriaColl.put("INV111", srchCriteriaStatus);
			if(srchCriteriaMap!= null)
				searchCriteriaColl.put("INV222", srchCriteriaMap);
			if(filterByFromId != null)
				searchCriteriaColl.put("INV001", filterByFromId);
			if(filterByFrmLab != null)
				searchCriteriaColl.put("INV002", filterByFrmLab);
			if(filterByFrmCd != null)
				searchCriteriaColl.put("INV003", filterByFrmCd);
			if(filterByFrmCdDesc != null)
				searchCriteriaColl.put("INV004", filterByFrmCdDesc);
			if(filterByToCd != null)
				searchCriteriaColl.put("INV005", filterByToCd);
			if(filterByToCdDesc != null)
				searchCriteriaColl.put("INV006", filterByToCdDesc);

			portPageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);


		}catch (Exception e){
			logger.error("Error while filtering the filterAnswerLib:"+e.toString(), e);
		}
		return filteredAnsList;
	}

	// To get the filtered list in Answer Mapping Page.
	private Collection<Object> getFilteredAnswers(Collection<Object> fromPageAnsList,Map<Object,Object> searchCriteriaMap){
		QueueUtil queueUtil=new QueueUtil();
		try{
			String[] status = (String[]) searchCriteriaMap.get(PortPageUtil.FILTER_ANSWER_STATUS);
			String[] map=(String[]) searchCriteriaMap.get(PortPageUtil.FILTER_ANSWER_MAP);

			String filterByFromId = null;
			String filterByFrmLab=null;
			String filterByFrmCd=null;
			String filterByToCd=null;

			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				filterByFromId = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				filterByFrmLab = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
				filterByFrmCd = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText5_FILTER_TEXT")!=null){
				filterByToCd = (String) searchCriteriaMap.get("SearchText5_FILTER_TEXT");
			}

			Map<Object, Object> statusMap = new HashMap<Object,Object>();
			Map<Object, Object> mapMap = new HashMap<Object,Object>();


			if (status != null && status.length >0)
				statusMap = queueUtil.getMapFromStringArray(status);
			if (map != null && map.length >0)
				mapMap = queueUtil.getMapFromStringArray(map);

			if (statusMap != null && statusMap.size()>0)
				fromPageAnsList= PortPageUtil.filterAnsByStatus(fromPageAnsList, statusMap);
			if (mapMap != null && mapMap.size()>0)
				fromPageAnsList= PortPageUtil.filterAnsByMap(fromPageAnsList, mapMap);

			if(filterByFromId!= null){
				fromPageAnsList = PortPageUtil.filterAnsByText(fromPageAnsList, filterByFromId, NEDSSConstants.UNIQUE_ID);
			}
			if(filterByFrmLab!= null){
				fromPageAnsList = PortPageUtil.filterAnsByText(fromPageAnsList, filterByFrmLab, NEDSSConstants.LABEL);
			}
			if(filterByFrmCd!= null){
				fromPageAnsList = PortPageUtil.filterAnsByText(fromPageAnsList, filterByFrmCd, NEDSSConstants.FROM_CODE);
			}

			if(filterByToCd!= null){
				fromPageAnsList = PortPageUtil.filterAnsByText(fromPageAnsList, filterByToCd, NEDSSConstants.TO_CODE);
			}

		}catch(Exception ex){
			logger.fatal("Error in getFilteredAnswers in PortPageAction  : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
		return fromPageAnsList;
	}



	//Sorts the list in Answer Mapping Page.
	private void sortFromAnswers(PortPageForm portPageForm, Collection<Object>  fromPageAnsList, boolean existing, HttpServletRequest request) throws Exception{

		// Retrieve sort-order and sort-direction from displaytag params
		try{
			String sortMethod = getSortMethod(request, portPageForm);
			String direction = getSortDirection(request, portPageForm);

			boolean invDirectionFlag = true;
			if (direction != null && direction.equals("2"))
				invDirectionFlag = false;

			NedssUtils util = new NedssUtils();

			//Read from properties file to determine default sort order
			if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				sortMethod = "getStatus";
				invDirectionFlag = false;                          //sorts according to status in descending order
			}

			if (sortMethod != null && fromPageAnsList != null && fromPageAnsList.size() > 0) {
				updateAnswersListBeforeSort(fromPageAnsList);
				util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) fromPageAnsList, invDirectionFlag);
				updateAnswersListAfterSort(fromPageAnsList);
			}
			String contextAction = request.getParameter("context");
			logger.info("contextAction: "+contextAction);
			if(!existing || "ReturnToAnswer".equalsIgnoreCase(contextAction) ) {
				//Finally put sort criteria in form
				String sortOrderParam = PortPageUtil.getSortCriteriaAnswer(invDirectionFlag == true ? "1" : "2", sortMethod);
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				portPageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			}
		}catch(Exception ex){
			logger.fatal("Error sorting in sortFromAnswers method in PortPageAction : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
	}

	//Clears all the filtering and sorting in Answer Mapping Page.
	public ActionForward clearAnsFilters(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		try{
			PortPageForm portPageForm = (PortPageForm)form;
			boolean existing=request.getParameter("existing")==null ? false : true;
			portPageForm.clearAll();
			sortFromAnswers(portPageForm, portPageForm.getFromPageAnswerList(), existing, request);
			if(portPageForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			portPageForm.getAttributeMap().put("fromFieldList",portPageForm.getFromPageQuestions());
			portPageForm.getAttributeMap().put("fromAnswerList",portPageForm.getFromPageAnswerList());
			portPageForm.getAttributeMap().put("queueList",portPageForm.getFromPageAnswerList());
			request.setAttribute("fromAnswerList", portPageForm.getFromPageAnswerList());
			request.setAttribute("queueCount",  Integer.toString(portPageForm.getFromPageAnswerList().size()));
			request.setAttribute("totalCount",portPageForm.getFromPageAnswerList().size());
			request.setAttribute("mapReqCount",portPageForm.getAnsMapNeededCount());
			request.setAttribute("toAnswerList", portPageForm.getToPageAnswerList());
			request.setAttribute("PageTitle", "Manage Pages: Port Page");

			portPageForm.initializeAnswerDD();;
			portPageForm.getAttributeMap().put("PageNumber","1");
			portPageForm.getAttributeMap().put("queueSize", PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);

			return PaginationUtil.paginate(portPageForm,request,"answerMappingPage",mapping);
		}catch(Exception ex){
			logger.fatal("Error in clearAnsFilters method in PortPageAction class :" +ex.getMessage(), ex);
			throw new ServletException(ex.getMessage());
		}
	}
	
	public ActionForward viewPageMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try{
			
			logger.info("Viewing mapping from Page Porting Management");
			
			PortPageForm portPageForm = (PortPageForm)form;
			portPageForm.clearMapAndList();
			Long nbsConversionPageMgmtUid=0L;
			Long fromPageWaTemplateUid=0L;
			Long toPageWaTemplateUid=0L;
			
			String nbsConversionPageMgmtUidStr = request.getParameter("nbsConversionPageMgmtUid");
			String fromPageWaTemplateUidStr = request.getParameter("fromPageWaTemplateUid");
			String toPageWaTemplateUidStr = request.getParameter("toPageWaTemplateUid");
			String mapName=request.getParameter("mapName");
			String fromPageFormCd=request.getParameter("fromPageFormCd");
			
			//In case of Legacy to page builder porting legacy page name must starts with "LEGACY"
			
			if(fromPageFormCd!=null && fromPageFormCd.startsWith(PortPageUtil.MAPPING_LEGACY)){
				portPageForm.setMappingType(PortPageUtil.MAPPING_LEGACY);
			}else{
				portPageForm.setMappingType(PortPageUtil.MAPPING_PAGEBUILDER);
			}
			logger.debug("nbsConversionPageMgmtUidStr: "+nbsConversionPageMgmtUidStr+", fromPageWaTemplateUidStr: "+fromPageWaTemplateUidStr+", toPageWaTemplateUidStr: "+toPageWaTemplateUidStr+", mapName: "+mapName);
			
			if(nbsConversionPageMgmtUidStr!=null)
				nbsConversionPageMgmtUid = Long.parseLong(nbsConversionPageMgmtUidStr);
			if(fromPageWaTemplateUidStr!=null && PortPageUtil.isNumeric(fromPageWaTemplateUidStr))
				fromPageWaTemplateUid = Long.parseLong(fromPageWaTemplateUidStr);
			if(toPageWaTemplateUidStr!=null && PortPageUtil.isNumeric(toPageWaTemplateUidStr))
				toPageWaTemplateUid = Long.parseLong(toPageWaTemplateUidStr);
			
			//Get ToPage's business Object type and set it in form.
			String busObjType = PortPageUtil.getBusinessObjectTypeForPage(toPageWaTemplateUid, request);
			portPageForm.setBusObjType(busObjType);
			
			logger.info("busObjType: "+busObjType);
			
			ArrayList <Object> mappedQuestionAnswersList = null;
			String contextAction = request.getParameter("context");
			
			logger.info("contextAction:"+contextAction);
			if(PortPageUtil.MAPPING_PAGEBUILDER.equals(portPageForm.getMappingType())){
				
				logger.info("Page Builder Mapping.");
				portPageForm.setNbsConversionPageMgmtUid(nbsConversionPageMgmtUid);
				portPageForm.setFromPageWaTemplateUid(fromPageWaTemplateUid);
				portPageForm.setToPageWaTemplateUid(toPageWaTemplateUid);
				portPageForm.setMapName(mapName);
				portPageForm.setFromPageFormCd(fromPageFormCd);
				
				ArrayList<Object> newlyAddedLDFList = PortPageUtil.getNewlyAddedLDFsForHybridPage(fromPageFormCd, fromPageWaTemplateUid, request);
				
				//Appending LDF questions from NBS_UI_Metadata to WA_UI_Metadata for Varicella hybrid mapping.
				PortPageUtil.insertLDFFromNbsUiMetadataToWaUiMetadata(fromPageFormCd, fromPageWaTemplateUid, request);
				
				ArrayList <Object> fromFieldList = new ArrayList<Object>();
				ArrayList <Object> toFieldList = new ArrayList<Object>();
				
				HashMap<String, Integer> repeatingBlockQuestions = new HashMap<>();
				
				// Load Mapped Questions from database.
				Object[] oParams = new Object[] {fromPageWaTemplateUid, toPageWaTemplateUid, nbsConversionPageMgmtUid, PortPageUtil.MAPPING_PAGEBUILDER};
				String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
				String sMethod = "viewPortPageMapping";
				Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
				mappedQuestionAnswersList = (ArrayList) obj;
				
				// Load To page Questions from database
				Object[] oParams1 = new Object[] {portPageForm.getFromPageWaTemplateUid(), portPageForm.getToPageWaTemplateUid(), portPageForm.getMappingType()};
				String sBeanJndiName1 = JNDINames.PORT_PAGE_PROXY_EJB;
				String sMethod1 = "getToPageQuestionsFields";
				Object toFieldListObj = CallProxyEJB.callProxyEJB(oParams1, sBeanJndiName1, sMethod1, request.getSession());
				toFieldList = (ArrayList) toFieldListObj;
				for(int i=0;i<toFieldList.size();i++){
					MappedFromToQuestionFieldsDT toFieldDT = (MappedFromToQuestionFieldsDT) toFieldList.get(i);    			
					portPageForm.getDisplayToQuestionsMap().put(toFieldDT.getFromQuestionId(), toFieldDT);
				}
			
				
				request.setAttribute("PageTitle", "Manage Pages: Port Page");
	
				ArrayList<String> repeatingBlockQuestionList = new ArrayList<String>();
			
				//Populate mappedQuestionsMap, fromFieldList and displayFromQuestionMap from the DB values
				
				PortPageUtil.populateQuestionListsFromMappedQuestionAnswerList(portPageForm, mappedQuestionAnswersList,  fromFieldList,  toFieldList, repeatingBlockQuestions, repeatingBlockQuestionList);
				
				//Add newly added LDFs in list to map
				
				fromFieldList.addAll(newlyAddedLDFList);
				
	/*			codeMappingRequired  will not change codeMappingRequired to true in few conditions 
				1. IF first answer of the coded to coded direct move questions is mapped as no on answer page
				2.if it direct move coded and user wanted to change answer mapping
				this code will change codeMappingRequired to true if any of the answer is mapped. 
	*/			
				//Used codeMapping flag to change codeMappingRequired variable 
				
				HashMap<String, ArrayList<Object>> mappedQuestions =portPageForm.getMappedQuestionsMap();
				Iterator<Entry<String, ArrayList<Object>>> hashmapIt = mappedQuestions.entrySet().iterator();
				while(hashmapIt.hasNext()){
					Map.Entry<String, ArrayList<Object>> pair = (Map.Entry<String, ArrayList<Object>>) hashmapIt.next();
					String questionId = (String) pair.getKey();
					ArrayList<Object> answerLists = (ArrayList<Object>)pair.getValue();
					Boolean codeMappingflag = false;
					
					for(int i=0;i<answerLists.size();i++){
						MappedFromToQuestionFieldsDT fromFieldDTs = (MappedFromToQuestionFieldsDT) answerLists.get(i);
						if(fromFieldDTs.getAnswerGroupSeqNbr()==null){
							if(fromFieldDTs.isCodeMappingRequired()){
								codeMappingflag = true;
							}
						}
					}
					if(codeMappingflag){
						for(int i=0;i<answerLists.size();i++){
							MappedFromToQuestionFieldsDT fromFieldDTs = (MappedFromToQuestionFieldsDT) answerLists.get(i);
							if(fromFieldDTs.getAnswerGroupSeqNbr() ==null){
							fromFieldDTs.setCodeMappingRequired(true);
							}
						}
					}
				}
				
				//add repeating block other mapped instances in fromFieldList for display
				Map<String, MappedFromToQuestionFieldsDT> repeatingQuestionInstanceMap = new HashMap<String, MappedFromToQuestionFieldsDT>();
				for(int i=0;i<repeatingBlockQuestionList.size();i++){
					
					String question = repeatingBlockQuestionList.get(i);
					if(question!=null){
						ArrayList<Object> repeatingBlockInstancesList = portPageForm.getMappedQuestionsMap().get(question);
						if(repeatingBlockInstancesList!=null){
							MappedFromToQuestionFieldsDT firstElement = (MappedFromToQuestionFieldsDT) repeatingBlockInstancesList.get(0);
							for(int j=1;j<repeatingBlockInstancesList.size();j++){
								MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) repeatingBlockInstancesList.get(j);
								if( !firstElement.getToQuestionId().equals(dt.getToQuestionId()) || "N".equals(dt.getQuestionMappedCode())){
									repeatingQuestionInstanceMap.put(dt.getFromQuestionId()+"|"+dt.getAnswerGroupSeqNbr(), dt);
								}
							}
						}
					}
				}
				
				for (String key : repeatingQuestionInstanceMap.keySet()) {
					MappedFromToQuestionFieldsDT dt = repeatingQuestionInstanceMap.get(key);
					fromFieldList.add(dt);
				}
				
				portPageForm.setFromPageQuestions(fromFieldList);
				sortFromQuestions(portPageForm,fromFieldList,false,request);
				portPageForm.getAttributeMap().put("queueSize", PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);
				
				portPageForm.setFromPageQuestions(fromFieldList);
				portPageForm.setToPageQuestions(toFieldList);
				
				int mappingNeededCount = PortPageUtil.getCountByMappingStatus(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED, fromFieldList);
				int mappingNeededRepeatingCount = PortPageUtil.getCountByMappingStatus(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R, fromFieldList);
				portPageForm.setMapNeededCount(mappingNeededCount+mappingNeededRepeatingCount);
				
				PortPageUtil.setSearchAttributeForQuestionMapping(portPageForm, fromFieldList, toFieldList, request);
				
				PortPageUtil.setPageAndConditionDetailsToDisplay(portPageForm,request);
				request.setAttribute("FromPageTotalAssociatedConditions", portPageForm.getAttributeMap().get("FromPageTotalAssociatedConditions"));
			}else{
				logger.info("Legacy Mapping.");
				//Legacy Mapping, locked it for Beta
				
				Object[] oParams = new Object[] {fromPageWaTemplateUid, toPageWaTemplateUid, nbsConversionPageMgmtUid, PortPageUtil.MAPPING_LEGACY};
				String sBeanJndiName = JNDINames.PORT_PAGE_PROXY_EJB;
				String sMethod = "viewPortPageMapping";
				Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
				mappedQuestionAnswersList = (ArrayList) obj;
				
				Object[] oParams2 = new Object[] {portPageForm.getFromPageWaTemplateUid(), portPageForm.getToPageWaTemplateUid(), portPageForm.getMappingType()};
				String sBeanJndiName2 = JNDINames.PORT_PAGE_PROXY_EJB;
				String sMethod2 = "getToPageQuestionsFields";
				Object toFieldListObj = CallProxyEJB.callProxyEJB(oParams2, sBeanJndiName2, sMethod2, request.getSession());
				ArrayList <Object> toFieldList = (ArrayList) toFieldListObj;
				
				for(int i=0;i<mappedQuestionAnswersList.size();i++){
					MappedFromToQuestionFieldsDT fieldDT = (MappedFromToQuestionFieldsDT) mappedQuestionAnswersList.get(i);    			
					portPageForm.getDisplayToQuestionsMap().put(fieldDT.getFromQuestionId(), fieldDT);
					//toFieldList.add(toFieldDT);
					
					if(portPageForm.getMappedQuestionsMap().get(fieldDT.getFromQuestionId())==null){
						ArrayList <Object> list = new ArrayList <Object> ();
						list.add(fieldDT);
						portPageForm.getMappedQuestionsMap().put(fieldDT.getFromQuestionId(), list);
					}else{//add answer mappings for the questions.
						portPageForm.getMappedQuestionsMap().get(fieldDT.getFromQuestionId()).add(fieldDT);
					}
					
					if(PortPageUtil.NBS_QA_MAPPING_STATUS_COMPLETE.equals(fieldDT.getStatusCode()) || PortPageUtil.NBS_QA_MAPPING_STATUS_LEGACY_COMPLETE.equals(fieldDT.getStatusCode())){
						fieldDT.setFieldMappingRequired(true);
					}
					
					PortPageUtil.updateQuestionEditFlag(fieldDT, PortPageUtil.MAPPING_LEGACY);
					
					PortPageUtil.removeMappedQuestionFromList(fieldDT.getFromQuestionId(), toFieldList, request);
				}
				
				ArrayList<String> repeatingBlockQuestionList = new ArrayList<String>();
				HashMap<String, Integer> repeatingBlockQuestions = new HashMap<>();
				ArrayList <Object> fromFieldList = new ArrayList<Object>();
				PortPageUtil.populateQuestionListsFromMappedQuestionAnswerList(portPageForm, mappedQuestionAnswersList,  fromFieldList,  toFieldList, repeatingBlockQuestions, repeatingBlockQuestionList);
				
				int mappingNeededCount = PortPageUtil.getCountByMappingStatus(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED, mappedQuestionAnswersList);
				portPageForm.setMapNeededCount(mappingNeededCount);
				
				portPageForm.getAttributeMap().put("questionAnswerList",fromFieldList);
				portPageForm.setFromPageQuestions(fromFieldList);
				portPageForm.setToPageQuestions(toFieldList);
				portPageForm.setMappedQuestionAnswerListForReview(mappedQuestionAnswersList);
				
				request.setAttribute("fromFieldList", fromFieldList);
				request.setAttribute("toFieldList", toFieldList);
				
				request.setAttribute("questionAnswerListToReview", mappedQuestionAnswersList);
		    	portPageForm.getAttributeMap().put("questionAnswerListToReview", mappedQuestionAnswersList);
				portPageForm.getAttributeMap().put("queueCount", String.valueOf(mappedQuestionAnswersList.size()));
				portPageForm.getAttributeMap().put("queueSize",PortPageUtil.REVIEW_PAGE_QUEUE_SIZE);
				request.setAttribute("PageTitle", "Manage Pages: Port Page");
				request.setAttribute("queueCount",String.valueOf(mappedQuestionAnswersList.size()));
				
				portPageForm.getAttributeMap().put("PageNumber", "1");
				
				String sortOrderParam = PortPageUtil.getSortCriteriaAnswer("1", "getStatus");
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				portPageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);

				PortPageUtil.setSearchAttributeForQuestionMapping(portPageForm, fromFieldList, toFieldList, request);
				
				sortFromQuestions(portPageForm,mappedQuestionAnswersList,false,request);
				portPageForm.getAttributeMap().put("queueSize", PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);
				
			}
			
			
			if(PortPageUtil.CONTEXT_LOCK_MAPPING.equals(contextAction)){
				portPageForm.getAttributeMap().put("questionAnswerList",mappedQuestionAnswersList);
		    	portPageForm.setMappedQuestionAnswerListForReview(mappedQuestionAnswersList);
		    	
		    	request.setAttribute("FromPageName", portPageForm.getAttributeMap().get("FromPageName"));
				request.setAttribute("ToPageName", portPageForm.getAttributeMap().get("ToPageName"));
		    	
		    	request.setAttribute("questionAnswerListToReview", mappedQuestionAnswersList);
				request.setAttribute("PageTitle", "Manage Pages: Port Page");
				portPageForm.setLockMapping(true);
				return mapping.findForward("portConditionLockMapping");
			}else if("ReviewMapping".equals(contextAction)){
		    	
				portPageForm.getAttributeMap().put("questionAnswerList",mappedQuestionAnswersList);
		    	portPageForm.setMappedQuestionAnswerListForReview(mappedQuestionAnswersList);
		    	
				portPageForm.initializeReviewDropDowns();
		    	portPageForm.getAttributeMap().put("fromDataType",new Integer(portPageForm.getRevFromDataType().size()));
		    	portPageForm.getAttributeMap().put("mapQuestion",new Integer(portPageForm.getRevMapQuestion().size()));
		    	portPageForm.getAttributeMap().put("toDataType",new Integer(portPageForm.getRevToDataType().size()));
		    	portPageForm.getAttributeMap().put("mapAnswer",new Integer(portPageForm.getRevMapAnswer().size()));
		    	
		    	sortReviewPageLibrary(portPageForm,mappedQuestionAnswersList,true,request);
		    	
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
		    	
		    	request.setAttribute("FromPageName", portPageForm.getAttributeMap().get("FromPageName"));
				request.setAttribute("ToPageName", portPageForm.getAttributeMap().get("ToPageName"));
		    	
		    	request.setAttribute("questionAnswerListToReview", mappedQuestionAnswersList);
		    	portPageForm.getAttributeMap().put("questionAnswerListToReview", mappedQuestionAnswersList);
				portPageForm.getAttributeMap().put("queueCount", String.valueOf(mappedQuestionAnswersList.size()));
				portPageForm.getAttributeMap().put("queueSize",PortPageUtil.REVIEW_PAGE_QUEUE_SIZE);
				request.setAttribute("PageTitle", "Manage Pages: Port Page");
				request.setAttribute("queueCount",String.valueOf(mappedQuestionAnswersList.size()));
				
				portPageForm.getAttributeMap().put("PageNumber", "1");
				portPageForm.setLockMapping(true);
				return PaginationUtil.paginate(portPageForm, request,"reviewMappingPage", mapping);
			}
			
			return PaginationUtil.paginate(portPageForm, request,"questionMappingPage", mapping);
		}catch(Exception ex){
			logger.fatal("Error in loadManagePagePort method in PortPageAction class :" +ex.getMessage(), ex);
			throw new ServletException(ex.getMessage());
		}
	}
	
	
	/**
     * Port Condition Page Load
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward loadPortCondition(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception{

    	try{
    		ArrayList<Object> pageList=new ArrayList<Object>();
    	    PortPageForm portPageForm = (PortPageForm)form;
    	    ArrayList<Object> nbsConversionMasterLogs = new ArrayList<Object>();
    	    
    	    //Clear previously selected condition before loading page
			portPageForm.setSelectedConditionCode("");

			String contextAction = request.getParameter("context");
			logger.info("contextAction: "+contextAction);
			if(PortPageUtil.MAPPING_LEGACY.equals(portPageForm.getMappingType())){
	    		PortPageUtil.setPortConditionPageDetailsForLegacyVaccination(portPageForm, request);
	    		
	    		ManagePageDT managePageDT = PortPageUtil.getNBSConversionPageMgmtByUid(portPageForm.getNbsConversionPageMgmtUid(), request);
	    		if(PortPageUtil.NBS_PAGE_MAPPING_STATUS_COMPLETE.equals(managePageDT.getMappingStatusCd())){
	    			contextAction = PortPageUtil.CONTEXT_LOCK_MAPPING;
	    		}
	    	}else{
	    		PortPageUtil.setPortConditionPageDetailsForPageBuilderConversion(portPageForm,request);
	    	}
			
			//If one condition ported then lock the mapping and display summary on screen.
			if(PortPageUtil.CONTEXT_LOCK_MAPPING.equals(contextAction)){
				portPageForm.setLockMapping(true);
				String conditionCd = HTMLEncoder.encodeHtml(request.getParameter("conditionCd"));
				String nbsConversionPageMgmtUid = request.getParameter("nbsConversionPageMgmtUid");
				String conditionDesc = CachedDropDowns.getConditionDesc(conditionCd);
				
				if(conditionCd!=null)
					portPageForm.setSelectedConditionCode(conditionCd);
				
				if(nbsConversionPageMgmtUid!=null)
					portPageForm.setNbsConversionPageMgmtUid(Long.parseLong(nbsConversionPageMgmtUid));
				
		    	PortPageUtil.setPageAndConditionDetailsToDisplay(portPageForm,request);
		    	//Populated ported condition in dropdown.
		    	ArrayList conditionList = new ArrayList();
		    	DropDownCodeDT dropDownDT = new DropDownCodeDT();
		    	dropDownDT.setKey(conditionCd);
		    	dropDownDT.setValue(conditionDesc+ " ("+conditionCd+")");
		    	conditionList.add(dropDownDT);
		    	
		    	request.setAttribute("fromPageConditionsList", conditionList);
				
				String sBeanJndiName = JNDINames.PAM_CONVERSION_EJB;
		    	String sMethod = "getNBSConversionMasterLogByCondition";
				Object[] oParams = new Object[] {portPageForm.getSelectedConditionCode(), portPageForm.getNbsConversionPageMgmtUid()};
				Object nbsConversionMasterLogObj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
				nbsConversionMasterLogs = (ArrayList<Object>) nbsConversionMasterLogObj;
				request.setAttribute("prodRunSuccess",PortPageUtil.PROD_RUN_COMPLETE);
			}else{
			
				ArrayList conditionList = new ArrayList();
		    	ArrayList conditionListWithPortInd =  PortPageUtil.getConditionByTemplateFormCd(portPageForm.getFromPageFormCd(),request);
		    	
		    	//to display condition with code in dropdown i.e. Malaria (10130) 
		    	for(int i=0;i<conditionListWithPortInd.size();i++){
		    		ConditionDT conditionDT = (ConditionDT) conditionListWithPortInd.get(i);
		    		if(PortPageUtil.PORT_REQ_IND_F.equals(conditionDT.getPortReqIndCd()) || PortPageUtil.MAPPING_HYBRID.equals(PortPageUtil.getMappingType(portPageForm.getFromPageFormCd(), conditionDT.getConditionCd()))){//skipping the conditions which are required to port from Legacy to Page Builder page.
		    			DropDownCodeDT dropDownDT = new DropDownCodeDT();
				    	dropDownDT.setKey(conditionDT.getConditionCd());
				    	dropDownDT.setValue(conditionDT.getConditionDescTxt()+ " ("+conditionDT.getConditionCd()+")");
				    	conditionList.add(dropDownDT);
		    		}
		    	}
		    	
		    	request.setAttribute("fromPageTotalAssociatedConditionsCount", conditionList.size()); 
		    	request.setAttribute("fromPageConditionsList", conditionList);
			}
			
	    	request.setAttribute("fromPageName",portPageForm.getAttributeMap().get("FromPageName"));
	    	request.setAttribute("toPageName",portPageForm.getAttributeMap().get("ToPageName"));
	    	request.setAttribute("nbsConversionMasterLogs", nbsConversionMasterLogs);
	    	request.setAttribute("queueCount", nbsConversionMasterLogs.size());
	    	
	    	
	    	
			return mapping.findForward("portCondition");
    	}catch(Exception ex){
    		logger.fatal("Error in loadPortCondition method in PortPageAction"+ex.getMessage(), ex);
    		throw new ServletException(ex.getMessage());
    	}
    }
    
    //Filtering/sorting code for Port Page Management(Port Page Library)
   	public ActionForward filterReviewPageLibSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
   		PortPageForm portPageForm = (PortPageForm) form;
   		
   		try{
   			Collection<Object>  filteredReviewList = filterReviewPageLibrary(portPageForm, request);
   			if(filteredReviewList != null){
   				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
   			//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
   			}else{
   				filteredReviewList = (ArrayList<Object> ) portPageForm.getMappedQuestionAnswerListForReview();
   			}
   			portPageForm.getAttributeMap().put("reviewPageList",filteredReviewList);
   			portPageForm.getAttributeMap().put("queueList",filteredReviewList);
   			request.setAttribute("FromPageName", portPageForm.getAttributeMap().get("FromPageName"));
			request.setAttribute("ToPageName", portPageForm.getAttributeMap().get("ToPageName"));
	    	request.setAttribute("FromPageTotalAssociatedConditions", portPageForm.getAttributeMap().get("FromPageTotalAssociatedConditions"));
	    	request.setAttribute("questionAnswerListToReview", filteredReviewList);
   			request.setAttribute("queueList", filteredReviewList);
   			sortReviewPageLibrary(portPageForm, filteredReviewList, true, request);
   			request.setAttribute("queueCount", String.valueOf(filteredReviewList.size()));
   			portPageForm.getAttributeMap().put("queueCount", String.valueOf(filteredReviewList.size()));
   			request.setAttribute("PageTitle","Manage Pages: Port Page");
   			portPageForm.getAttributeMap().put("PageNumber", "1");
   		}catch(Exception e){
   			logger.fatal("Error in filterReviewPageLibSubmit in PortPageAction class: "+ e.toString(), e);
   			throw new Exception(e.getMessage());
   		}
   		return PaginationUtil.paginate(portPageForm, request, "reviewMappingPage",mapping);
   		
   	}
   	
	private Collection<Object>  filterReviewPageLibrary(PortPageForm portPageForm, HttpServletRequest request) throws Exception {
		Collection<Object>  filteredReviewList = new ArrayList<Object> ();
		String sortOrderParam = null;
		QueueUtil queueUtil=new QueueUtil();
		try{
			Map<Object, Object> searchCriteriaMap = portPageForm.getSearchCriteriaArrayMap();
			ArrayList<Object> reviewList = (ArrayList<Object> ) portPageForm.getMappedQuestionAnswerListForReview();
			filteredReviewList = PortPageUtil.getFilteredReviewPageList(reviewList, searchCriteriaMap,portPageForm);
			
			String[] fromDataType = (String[]) searchCriteriaMap.get("FRMDATATYPE");
	    	String[] mapQuestion = (String[]) searchCriteriaMap.get("MAPQUESTION");
			String[] toDataType = (String[]) searchCriteriaMap.get("TODATATYPE");
			String[] mapAnswer = (String[]) searchCriteriaMap.get("MAPANSWER");
			String srchCriteriaFromDataType = null;
			String srchCriteriaMapQuestion = null;
			String srchCriteriaToDataType = null;
			String srchCriteriaMapAnswer = null;
			String srchCriteriaFromId = null;
			String srchCriteriaFromLabel = null;
			String srchCriteriaToId = null;
			String srchCriteriaToLabel = null;
			String srchCriteriaFromAnswer = null;
			String srchCriteriaToAnswer = null;
			
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				srchCriteriaFromId = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				srchCriteriaFromLabel = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
				srchCriteriaToId = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText4_FILTER_TEXT")!=null){
				srchCriteriaToLabel = (String) searchCriteriaMap.get("SearchText4_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText5_FILTER_TEXT")!=null){
				srchCriteriaFromAnswer = (String) searchCriteriaMap.get("SearchText5_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText6_FILTER_TEXT")!=null){
				srchCriteriaToAnswer = (String) searchCriteriaMap.get("SearchText6_FILTER_TEXT");
			}
			
			Integer fromDataTypeCount = new Integer(fromDataType == null ? 0 : fromDataType.length);
			Integer mapQuestionCount = new Integer(mapQuestion == null ? 0 : mapQuestion.length);
			Integer toDataTypeCount = new Integer(toDataType == null ? 0 : toDataType.length);
			Integer mapAnswerCount = new Integer(mapAnswer == null ? 0 : mapAnswer.length);
			
			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(fromDataTypeCount.equals(portPageForm.getAttributeMap().get("fromDataType")) && mapQuestionCount.equals((portPageForm.getAttributeMap().get("mapQuestion"))) &&
					toDataTypeCount.equals((portPageForm.getAttributeMap().get("toDataType"))) && mapAnswerCount.equals((portPageForm.getAttributeMap().get("mapAnswer"))) &&
					srchCriteriaFromId == null && srchCriteriaFromLabel == null && srchCriteriaToId == null && srchCriteriaToLabel == null && srchCriteriaFromAnswer == null && srchCriteriaToAnswer == null)
			{
	
			    String sortMethod = getSortMethod(request, portPageForm);
				String direction = getSortDirection(request, portPageForm);
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
						Map<?,?> sColl =  portPageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<?,?>) portPageForm.getAttributeMap().get("searchCriteria");
						sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
						sortOrderParam = PortPageUtil.getSortReviewPageLib(direction, sortMethod);
				}
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				portPageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
				return null;
			}
			
			ArrayList<Object> fromDataTypeList = portPageForm.getRevFromDataType();
			ArrayList<Object> mapQuestionList = portPageForm.getRevMapQuestion();
			ArrayList<Object> toDataTypeList = portPageForm.getRevToDataType();
			ArrayList<Object> mapAnswerList = portPageForm.getRevMapAnswer();
			
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			String sortMethod = getSortMethod(request, portPageForm);
			String direction = getSortDirection(request, portPageForm);
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object, Object> sColl =  portPageForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<Object, Object>) portPageForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = PortPageUtil.getSortReviewPageLib(direction, sortMethod);
			}

			srchCriteriaFromDataType = queueUtil.getSearchCriteria(fromDataTypeList, fromDataType, NEDSSConstants.FILTERBYTYPE);
			srchCriteriaMapQuestion = queueUtil.getSearchCriteria(mapQuestionList, mapQuestion, NEDSSConstants.FILTERBYSUBMITTEDBY);
			srchCriteriaToDataType = queueUtil.getSearchCriteria(toDataTypeList, toDataType, NEDSSConstants.FILTERBYTYPE);
			srchCriteriaMapAnswer = queueUtil.getSearchCriteria(mapAnswerList, mapAnswer, NEDSSConstants.FILTERBYSUBMITTEDBY);
			
			
			//set the error message to the form
			if(sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if(srchCriteriaFromDataType != null)
				searchCriteriaColl.put("INV111", srchCriteriaFromDataType);
			if(srchCriteriaMapQuestion != null)
				searchCriteriaColl.put("INV222", srchCriteriaMapQuestion);
			if(srchCriteriaToDataType != null)
				searchCriteriaColl.put("INV333", srchCriteriaToDataType);
			if(srchCriteriaMapAnswer != null)
				searchCriteriaColl.put("INV444", srchCriteriaMapAnswer);
			if(srchCriteriaFromId != null)
				searchCriteriaColl.put("INV001", srchCriteriaFromId);
			if(srchCriteriaFromLabel != null)
				searchCriteriaColl.put("INV002", srchCriteriaFromLabel);
			if(srchCriteriaToId != null)
				searchCriteriaColl.put("INV003", srchCriteriaToId);
			if(srchCriteriaToLabel != null)
				searchCriteriaColl.put("INV004", srchCriteriaToLabel);
			if(srchCriteriaFromAnswer != null)
				searchCriteriaColl.put("INV005", srchCriteriaFromAnswer);
			if(srchCriteriaToAnswer != null)
				searchCriteriaColl.put("INV006", srchCriteriaToAnswer);

			portPageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}catch(Exception e){
			logger.fatal("Error in filterReviewPageLibrary in PortPageAction class: "+ e.toString(), e);
			throw new Exception(e.getMessage());
		}
		return filteredReviewList;
	}
	
	//Sorts the Review list in Review Mapping Page.
	private void sortReviewPageLibrary(PortPageForm portPageForm, Collection<Object>  reviewList, boolean existing, HttpServletRequest request) throws Exception{

		try{
			// Retrieve sort-order and sort-direction from displaytag params
			String sortMethod = getSortMethod(request, portPageForm);
			String direction = getSortDirection(request, portPageForm);

			boolean invDirectionFlag = true;
			if (direction != null && direction.equals("2"))
				invDirectionFlag = false;

			NedssUtils util = new NedssUtils();

			//Read from properties file to determine default sort order
			if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				sortMethod = "getFromQuestionId";
				invDirectionFlag = true;      //sorts according to fromId in asscending order.
			}

			if (sortMethod != null && reviewList != null && reviewList.size() > 0) {
				updateListBeforeSort(reviewList);
				util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) reviewList, invDirectionFlag);
				updateListAfterSort(reviewList);
			}
			if(!existing) {
				//Finally put sort criteria in form
				String sortOrderParam = PortPageUtil.getSortReviewPageLib(invDirectionFlag == true ? "1" : "2", sortMethod);
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				portPageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			}
		}catch(Exception ex){
			logger.fatal("Error sorting in sortReviewPageLibrary method in PortPageAction : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
	}
	
	//To clear the sorts and filters applied in Review Mapping Page.
	public ActionForward clearReviewPageFilters(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		try{
			PortPageForm portPageForm = (PortPageForm)form;
			
			String FromPageName=(String) portPageForm.getAttributeMap().get("FromPageName");
			String ToPageName=(String) portPageForm.getAttributeMap().get("ToPageName");
			Integer conditionSize = (Integer) portPageForm.getAttributeMap().get("FromPageTotalAssociatedConditions");
			
			boolean existing=request.getParameter("existing")==null ? false : true;
			portPageForm.clearAll();
			sortReviewPageLibrary(portPageForm, portPageForm.getMappedQuestionAnswerListForReview(), existing, request);
			portPageForm.getAttributeMap().put("questionAnswerListToReview",portPageForm.getMappedQuestionAnswerListForReview());
			portPageForm.getAttributeMap().put("queueList",portPageForm.getMappedQuestionAnswerListForReview());
			portPageForm.getAttributeMap().put("FromPageName", FromPageName);
			portPageForm.getAttributeMap().put("ToPageName", ToPageName);
			portPageForm.getAttributeMap().put("FromPageTotalAssociatedConditions",conditionSize);
			if(portPageForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			request.setAttribute("FromPageName", FromPageName);
			request.setAttribute("ToPageName", ToPageName);
	    	request.setAttribute("FromPageTotalAssociatedConditions", conditionSize);
			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			request.setAttribute("questionAnswerListToReview", portPageForm.getMappedQuestionAnswerListForReview());
			request.setAttribute("queueCount",  Integer.toString(portPageForm.getMappedQuestionAnswerListForReview().size()));
			portPageForm.initializeReviewDropDowns();
			portPageForm.getAttributeMap().put("PageNumber","1");
			portPageForm.getAttributeMap().put("queueSize", PortPageUtil.REVIEW_PAGE_QUEUE_SIZE);

			return PaginationUtil.paginate(portPageForm,request,"reviewMappingPage",mapping);
		}catch(Exception ex){
			logger.fatal("Error in clearFilters method of PortPageAction class :" +ex.getMessage(), ex);
			throw new ServletException(ex.getMessage());
		}
	}
	
	
	
	/**
     * Submit Pre Run button
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public ActionForward submitPreRun(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception{

    	try{
    		PortPageForm portPageForm = (PortPageForm)form;    	    
            PortPageUtil portUtil = new PortPageUtil();
    	    ArrayList<Object> nbsConversionMasterLogs = new ArrayList<Object>();

    	    boolean forceEJBcall=false;
			boolean initLoad=request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
			if(initLoad && !PaginationUtil._dtagAccessed(request)){
				forceEJBcall=true;
			}
			
			if(forceEJBcall){
		    	ArrayList conditionList = CachedDropDowns.getConditionDropDownForTemplate(portPageForm.getFromPageWaTemplateUid());
		    	
		    	//to display condition with code in dropdown i.e. Malaria (10130) 
		    	for(int i=0;i<conditionList.size();i++){
		    		DropDownCodeDT dropDownDT = (DropDownCodeDT) conditionList.get(i);
		    		if(dropDownDT.getKey().length()>0){
		    			dropDownDT.setValue(dropDownDT.getValue()+ " ("+dropDownDT.getKey()+")");
		    		}
		    	}
		    	logger.debug("Selected condition for PreRun: "+portPageForm.getSelectedConditionCode());
	
		    	
		    	ArrayList mappedQuestionList = portUtil.getMappedQuestionListFromMap(portPageForm.getMappedQuestionsMap());
		    	
		    	
		    	//Gets the conditionCdGroupId for a condition if exists or else creates one if it doesn't exists in Nbs_conversion_condition table.
		    	Long conditionCdGroupId = portUtil.getConditionCdGrpIdByCond(portPageForm.getSelectedConditionCode(), portPageForm.getNbsConversionPageMgmtUid(),request);
				
		    	//Remove existing mapping before inserting
		    	portUtil.removeMapFromNbsConvMapping(conditionCdGroupId,request);
		    	
		    	//Inserts the MappedQuestion List into Nbs_Conversion_Mapping table.
		    	portUtil.insertIntoNbsConvMapping(mappedQuestionList, conditionCdGroupId,request);
				
				//Validation part
				//1) call method from PBtoPBConverterProcessor
				String preRunSuccess = "";
				if(PortPageUtil.MAPPING_LEGACY.equals(portPageForm.getMappingType())){
					preRunSuccess = LegacyToPBConverterProcessor.startConversionProcess(PortPageUtil.RUN_TYPE_PRERUN,conditionCdGroupId,0,portPageForm.getSelectedConditionCode(), portPageForm.getToPageWaTemplateUid(), portPageForm.getNbsConversionPageMgmtUid(), portPageForm.getBusObjType(), request);
					PortPageUtil.setPortConditionPageDetailsForLegacyVaccination(portPageForm,request);
				}else{
					String mappingType = PortPageUtil.getMappingType(portPageForm.getFromPageFormCd(), portPageForm.getSelectedConditionCode());
					
					preRunSuccess = PBtoPBConverterProcessor.startConversionProcess(PortPageUtil.RUN_TYPE_PRERUN,conditionCdGroupId,0,portPageForm.getSelectedConditionCode(), portPageForm.getFromPageWaTemplateUid(), portPageForm.getToPageWaTemplateUid(), portPageForm.getNbsConversionPageMgmtUid(), portPageForm.getBusObjType(), mappingType, request);
					PortPageUtil.setPortConditionPageDetailsForPageBuilderConversion(portPageForm,request);
				}
		    	//2) PBtoPBConverterProcessor reads mapping from DB NBS_Conversion_mapping, pass condtion_cd_group_id
				//3) validate mapping - get error message wording from business.
		    	//4) Update logging details.
		    	//5) Delete mappings from NBS_Conversion_mapping
		    	
		    	//Update UI
				
		    	String sBeanJndiName = JNDINames.PAM_CONVERSION_EJB;
		    	String sMethod = "getNBSConversionMasterLogByCondition";
				Object[] oParams = new Object[] {portPageForm.getSelectedConditionCode(), portPageForm.getNbsConversionPageMgmtUid()};
				//ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				Object nbsConversionMasterLogObj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
				nbsConversionMasterLogs = (ArrayList<Object>) nbsConversionMasterLogObj;
				
				portPageForm.getAttributeMap().put("conditionList",conditionList);
				portPageForm.getAttributeMap().put("nbsConversionMasterLogs", nbsConversionMasterLogs);
				portPageForm.getAttributeMap().put("fromPageTotalAssociatedConditionsCount", conditionList.size()-1);
				portPageForm.getAttributeMap().put("preRunSuccess", preRunSuccess);
				
			}else{
				nbsConversionMasterLogs = (ArrayList<Object>) portPageForm.getAttributeMap().get("nbsConversionMasterLogs");
				
				if(PortPageUtil.MAPPING_LEGACY.equals(portPageForm.getMappingType())){
					PortPageUtil.setPortConditionPageDetailsForLegacyVaccination(portPageForm,request);
				}else{
					PortPageUtil.setPortConditionPageDetailsForPageBuilderConversion(portPageForm,request);
				}
			}
			if(nbsConversionMasterLogs!=null){
				boolean existing = request.getParameter("existing") == null ? false : true;
				sortMasterLogs(portPageForm,nbsConversionMasterLogs,existing,request);
			}
			
	    	request.setAttribute("fromPageTotalAssociatedConditionsCount",portPageForm.getAttributeMap().get("fromPageTotalAssociatedConditionsCount")); // Subtracting 1 because CachedDropDowns give empty value to display in dropdown. Total conditions is conditionList.size()-1
	    	request.setAttribute("fromPageConditionsList", portPageForm.getAttributeMap().get("conditionList"));
	    	request.setAttribute("fromPageName",portPageForm.getAttributeMap().get("FromPageName"));
	    	request.setAttribute("toPageName",portPageForm.getAttributeMap().get("ToPageName"));
	    	request.setAttribute("nbsConversionMasterLogs",nbsConversionMasterLogs );
	    	request.setAttribute("queueCount", nbsConversionMasterLogs.size());
	    	request.setAttribute("populateSelectedCondition", "true");
	    	request.setAttribute("preRunSuccess", portPageForm.getAttributeMap().get("preRunSuccess"));
	    	
			return mapping.findForward("portCondition");
    	}catch(Exception ex){
    		logger.fatal("Error in submitPreRun method in PortPageAction"+ex.getMessage(), ex);
    		throw new ServletException(ex.getMessage());
    	}
    }
    
    private void sortMasterLogs(PortPageForm portPageForm, Collection<Object>  nbsConversionMasterLogs, boolean existing, HttpServletRequest request){
    	// Retrieve sort-order and sort-direction from displaytag params
    	try{		
    	        String sortMethod = getSortMethod(request, portPageForm);
    			String direction = getSortDirection(request, portPageForm);

    			boolean directionFlag = true;
    			if (direction != null && direction.equals("2"))
    				directionFlag = false;

    			NedssUtils util = new NedssUtils();
    			
    			//Read from properties file to determine default sort order
    			if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))|| (!existing)) {
    				sortMethod = "getStartTime"; 
    				directionFlag = false;              //sorts according to Start Time in descending order so that the most recent rows will be in top of list.
    			}
    			
    			if (sortMethod != null && nbsConversionMasterLogs != null && nbsConversionMasterLogs.size() > 0) {
    				util.sortObjectByColumn(sortMethod,(Collection<Object>) nbsConversionMasterLogs, directionFlag);
    				
    			}
    			
    			//Finally put sort criteria in form
    			String sortOrderParam = PortPageUtil.getSortCriteriaMasterLogs(directionFlag == true ? "1" : "2", sortMethod);
    			portPageForm.getAttributeMap().put("sortSt", sortOrderParam);
           }catch(Exception ex){
		            logger.fatal("Error sorting in sortMasterLogs method in PortPageAction : "+ex.getMessage(), ex);
		            throw new NEDSSSystemException(ex.getMessage());
           }
    }
    
    
    public ActionForward submitProductionRun(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception{
    	try{
    		PortPageForm portPageForm = (PortPageForm)form;    	    
            PortPageUtil portUtil = new PortPageUtil();
    	    ArrayList<Object> nbsConversionMasterLogs = new ArrayList<Object>();
 
    	    
    	    
    	    boolean forceEJBcall=false;
			boolean initLoad=request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
			if(initLoad && !PaginationUtil._dtagAccessed(request)){
				forceEJBcall=true;
			}
			
			if(forceEJBcall){
		    	ArrayList conditionList = CachedDropDowns.getConditionDropDownForTemplate(portPageForm.getFromPageWaTemplateUid());
		    	
		    	//to display condition with code in dropdown i.e. Malaria (10130) 
		    	for(int i=0;i<conditionList.size();i++){
		    		DropDownCodeDT dropDownDT = (DropDownCodeDT) conditionList.get(i);
		    		if(dropDownDT.getKey().length()>0){
		    			dropDownDT.setValue(dropDownDT.getValue()+ " ("+dropDownDT.getKey()+")");
		    		}
		    	}
		    	logger.debug("Selected condition for PreRun: "+portPageForm.getSelectedConditionCode());
	
		    	//To get the number of cases to port by user.
		    	
		    	int casesToMigrate = Integer.parseInt(request.getParameter("numberOfCasesToMigrate"));
		    	
		    	ArrayList mappedQuestionList = portUtil.getMappedQuestionListFromMap(portPageForm.getMappedQuestionsMap());
		    	
		    	//Gets the conditionCdGroupId for a condition if exists or else creates one if it doesn't exists in Nbs_conversion_condition table.
		    	Long conditionCdGroupId = portUtil.getConditionCdGrpIdByCond(portPageForm.getSelectedConditionCode(), portPageForm.getNbsConversionPageMgmtUid(),request);
				
		    	//Remove existing mapping before inserting
		    	portUtil.removeMapFromNbsConvMapping(conditionCdGroupId,request);
		    	
		    	//Inserts the MappedQuestion List into Nbs_Conversion_Mapping table.
		    	portUtil.insertIntoNbsConvMapping(mappedQuestionList, conditionCdGroupId,request);
				
		    	request.setAttribute("fromPageName",portPageForm.getAttributeMap().get("FromPageName"));
		    	request.setAttribute("mapName",portPageForm.getMapName());
				//Validation part
		    	String prodRunSuccess = "";
				//1) call method from PBtoPBConverterProcessor
		    	if(PortPageUtil.MAPPING_LEGACY.equals(portPageForm.getMappingType())){
		    		prodRunSuccess = LegacyToPBConverterProcessor.startConversionProcess(PortPageUtil.RUN_TYPE_PRODUCTION,conditionCdGroupId,casesToMigrate,portPageForm.getSelectedConditionCode(), portPageForm.getToPageWaTemplateUid(), portPageForm.getNbsConversionPageMgmtUid(), portPageForm.getBusObjType(), request);
		    		PortPageUtil.setPortConditionPageDetailsForLegacyVaccination(portPageForm,request);
				}else{
					String mappingType = PortPageUtil.getMappingType(portPageForm.getFromPageFormCd(), portPageForm.getSelectedConditionCode());
					
					prodRunSuccess = PBtoPBConverterProcessor.startConversionProcess(PortPageUtil.RUN_TYPE_PRODUCTION, conditionCdGroupId, casesToMigrate, portPageForm.getSelectedConditionCode(), portPageForm.getFromPageWaTemplateUid(), portPageForm.getToPageWaTemplateUid(), portPageForm.getNbsConversionPageMgmtUid(), portPageForm.getBusObjType(), mappingType, request);
					PortPageUtil.setPortConditionPageDetailsForPageBuilderConversion(portPageForm,request);
				}
		    	
		    	logger.info("prodRunSuccess: "+prodRunSuccess);
		    	//2) PBtoPBConverterProcessor reads mapping from DB NBS_Conversion_mapping, pass condtion_cd_group_id
				//3) validate mapping - get error message wording from business.
		    	//4) Update logging details.
		    	//5) Delete mappings from NBS_Conversion_mapping
		    	
		    	//Update UI
				if(portUtil.PROD_RUN_COMPLETE.equals(prodRunSuccess) || portUtil.PROD_RUN_PARTIAL.equals(prodRunSuccess)){
					portPageForm.setLockMapping(true);
					logger.info("Conversion started so mapping is locked.");
				}
				
		    	String sBeanJndiName = JNDINames.PAM_CONVERSION_EJB;
		    	String sMethod = "getNBSConversionMasterLogByCondition";
				Object[] oParams = new Object[] {portPageForm.getSelectedConditionCode(), portPageForm.getNbsConversionPageMgmtUid()};
				//ArrayList<?> arr =msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				Object nbsConversionMasterLogObj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
				nbsConversionMasterLogs = (ArrayList<Object>) nbsConversionMasterLogObj;
				
				// DropDownCodeDT conditionList has empty value, subtracting 1 from it 
				int totalConditions = conditionList.size()-1;
				if(portUtil.PROD_RUN_COMPLETE.equals(prodRunSuccess) && PortPageUtil.MAPPING_PAGEBUILDER.equals(portPageForm.getMappingType())){
					//After successful conversion as 1 condition is converted reduce condition count by 1.
					totalConditions = totalConditions-1;
					portPageForm.getAttributeMap().put("FromPageTotalAssociatedConditions",totalConditions);
				}

				portPageForm.getAttributeMap().put("conditionList",conditionList);
				portPageForm.getAttributeMap().put("nbsConversionMasterLogs", nbsConversionMasterLogs);
				portPageForm.getAttributeMap().put("fromPageTotalAssociatedConditionsCount", totalConditions);
				portPageForm.getAttributeMap().put("prodRunSuccess",prodRunSuccess);
				
			}else{
				nbsConversionMasterLogs = (ArrayList<Object>) portPageForm.getAttributeMap().get("nbsConversionMasterLogs");
			}
			if(nbsConversionMasterLogs!=null){
				boolean existing = request.getParameter("existing") == null ? false : true;
				sortMasterLogs(portPageForm,nbsConversionMasterLogs,existing,request);
			}
			
	    	request.setAttribute("fromPageTotalAssociatedConditionsCount",portPageForm.getAttributeMap().get("fromPageTotalAssociatedConditionsCount")); // Subtracting 1 because CachedDropDowns give empty value to display in dropdown. Total conditions is conditionList.size()-1
	    	request.setAttribute("fromPageConditionsList", portPageForm.getAttributeMap().get("conditionList"));
	    	request.setAttribute("fromPageName",portPageForm.getAttributeMap().get("FromPageName"));
	    	request.setAttribute("toPageName",portPageForm.getAttributeMap().get("ToPageName"));
	    	request.setAttribute("nbsConversionMasterLogs",nbsConversionMasterLogs );
	    	request.setAttribute("queueCount", nbsConversionMasterLogs.size());
	    	request.setAttribute("populateSelectedCondition", "true");
	    	request.setAttribute("prodRunSuccess", portPageForm.getAttributeMap().get("prodRunSuccess"));
	    	
			return mapping.findForward("portCondition");
    	}catch(Exception ex){
            logger.fatal("Error submitProductionRun method in PortPageAction : "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
    	}
    }
    
    
    public ActionForward exportMapping(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception{
    	try{
    		logger.info("Exporting Mapping");
    		
    		PortPageForm portPageForm = (PortPageForm)form;
    		
    		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
    		WaTemplateDT fromPageTempateDT = pageManagementDAOImpl.findWaTemplate(portPageForm.getFromPageWaTemplateUid());
    		WaTemplateDT toPageTempateDT = pageManagementDAOImpl.findWaTemplate(portPageForm.getToPageWaTemplateUid());
    		portPageForm.setFromPageFormCd(fromPageTempateDT.getFormCd());
    		portPageForm.setToPageFormCd(toPageTempateDT.getFormCd());
    		
            PortPageUtil portUtil = new PortPageUtil();
    	   
            ArrayList<Object> mappedQuestionAnswersList = portUtil.getMappedQuestionAnswers(portPageForm.getFromPageWaTemplateUid(), portPageForm.getToPageWaTemplateUid(), portPageForm.getNbsConversionPageMgmtUid(), portPageForm.getMappingType() ,request);
            
            String xml = portUtil.exportMappingToXML(mappedQuestionAnswersList, portPageForm, request);
            
            
            request.setAttribute("FromPageName", portPageForm.getAttributeMap().get("FromPageName"));
			request.setAttribute("ToPageName", portPageForm.getAttributeMap().get("ToPageName"));
	    	request.setAttribute("FromPageTotalAssociatedConditions", portPageForm.getAttributeMap().get("FromPageTotalAssociatedConditions"));
	    	request.setAttribute("questionAnswerListToReview", mappedQuestionAnswersList);
	    	portPageForm.getAttributeMap().put("questionAnswerListToReview", mappedQuestionAnswersList);
			portPageForm.getAttributeMap().put("queueCount", String.valueOf(mappedQuestionAnswersList.size()));
			portPageForm.getAttributeMap().put("queueSize",PortPageUtil.REVIEW_PAGE_QUEUE_SIZE);
			request.setAttribute("PageTitle", "Manage Pages: Port Page");
			request.setAttribute("queueCount",String.valueOf(mappedQuestionAnswersList.size()));
			portPageForm.getAttributeMap().put("PageNumber", "1");
			
            int length   = 0;
		    response.setContentType("application/octet-stream"); 
	        response.setHeader("Content-disposition", "attachment;filename=\""+portPageForm.getMapName()+".xml\""); 
	        ServletOutputStream op       = response.getOutputStream();		      
	        byte[] bbuf = new byte[1000];	      
	        InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8")); 
	        DataInputStream in = new DataInputStream(is);
	        while ((in != null) && ((length = in.read(bbuf)) != -1))
	        {
	            op.write(bbuf,0,length);
	        }
	        in.close();
	        op.flush();
	        op.close();
	        
	        return null;
    	}catch(Exception ex){
            logger.fatal("Error exportMapping method in PortPageAction : "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
    	}
    }
    
    /**
     * Calls when user click submit button, after selecting map to import.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward submitImport(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception{

    	try{
	    	PortPageForm portPageForm = (PortPageForm)form;	
	    	PortPageUtil portUtil = new PortPageUtil();
	    	boolean isValidXML = portUtil.validateImportedXML(portPageForm, request);
	    	if(!isValidXML){
	    		logger.info("Imported xml is not valid.");
		    	request.setAttribute("fromPageDD",portPageForm.getFromPageListDD());
				request.setAttribute("toPageDD",portPageForm.getToPageListDD());
				portPageForm.setFromPageFormCd("");
				portPageForm.setToPageFormCd("");
				request.setAttribute("PortPageError","\u2022 Import of Map Failed. The source file of Map being Imported is either corrupt or does not conform to the required XML format.");
				request.setAttribute("PageTitle", "Manage Pages: Port Page");
				return mapping.findForward("portPage");
    	    }
	    	FormFile file = portPageForm.getImportFile();
	    	String filePath = request.getParameter("filePath");
	    	request.setAttribute("fromPageDD", portPageForm.getFromPageListDD());
	    	request.setAttribute("toPageDD",portPageForm.getToPageListDD());
	    	request.setAttribute("fileName",file.getFileName());
	    	request.setAttribute("filePath",filePath);
	    	request.setAttribute("PageTitle", "Manage Pages: Port Page");
	    	return mapping.findForward("portPage") ;
    	}catch(Exception ex){
            logger.fatal("Error submitImport method in PortPageAction : "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
    	}
    }
    
    public ActionForward cancelImport(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception{
    	try{
    		PortPageForm portPageForm = (PortPageForm)form;
    		portPageForm.setImportFile(null);
    		request.setAttribute("fromPageDD", portPageForm.getFromPageListDD());
	    	request.setAttribute("toPageDD",portPageForm.getToPageListDD());
			return mapping.findForward("portPage");
    	}catch(Exception ex){
            logger.fatal("Error cancelImport method in PortPageAction : "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
    	}
    }

    
    public ActionForward submitSaveAndContinue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	try{
    		
    		logger.info("submitSaveAndContinue - Saving map during mapping.");
    		PortPageForm portPageForm = (PortPageForm)form;
    		boolean isQuestion=request.getParameter("isQuestionPage") == null ? false: Boolean.valueOf(request.getParameter("isQuestionPage")).booleanValue();
    		String context = request.getParameter("context");
    		logger.info("context: "+context);
    		//new map to add mapping needed questions to persist in DB while save and continue clicked.
    		HashMap<String, ArrayList<Object>> mappedAndUnmappedQuestionsMap= (HashMap<String, ArrayList<Object>>)portPageForm.getMappedQuestionsMap().clone();
    		
    		if(isQuestion){
    			//new map to add mapping needed questions to persist in DB while save and continue clicked.
    			for(int i=0;i<portPageForm.getFromPageQuestions().size();i++){
        			MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) portPageForm.getFromPageQuestions().get(i);
        			if(PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED.equals(dt.getStatusCode()) || PortPageUtil.NBS_QA_MAPPING_STATUS_MAPPING_NEEDED_R.equals(dt.getStatusCode())){
        				if(mappedAndUnmappedQuestionsMap.get(dt.getFromQuestionId())!=null){
        					ArrayList<Object> questionList = mappedAndUnmappedQuestionsMap.get(dt.getFromQuestionId());
        					if(!questionList.contains(dt)){
        						questionList.add(dt);
        						mappedAndUnmappedQuestionsMap.put(dt.getFromQuestionId(), questionList);
        					}
        				}else{
        					ArrayList<Object> questionList = new ArrayList<Object>();
        					questionList.add(dt);
        					mappedAndUnmappedQuestionsMap.put(dt.getFromQuestionId(), questionList);
        				}
        			}
        		}
        		PortPageUtil.storeMappingInWAConversionMapping(mappedAndUnmappedQuestionsMap, portPageForm.getNbsConversionPageMgmtUid(), request);
        		
    			//To return the filtered list after mapping every question,if any filters are applied before mapping.
        		
    			Collection<Object> filteredFromQuest = filterFromQuestionsLib(portPageForm, request);
    			portPageForm.initializeDropDowns();
    			sortFromQuestions(portPageForm,filteredFromQuest,true,request);
    			ArrayList<Object> toFieldList= new ArrayList<Object>(); 
    			toFieldList = portPageForm.getToPageQuestions();
    			portPageForm.getAttributeMap().put("queueCount",String.valueOf(filteredFromQuest.size()));
    			request.setAttribute("totalCount",portPageForm.getFromPageQuestions().size());            //sets the count of total questions 
    			request.setAttribute("mapReqCount",portPageForm.getMapNeededCount());                    //sets the count of Mapping Req Questions
    			request.setAttribute("queueCount",String.valueOf(filteredFromQuest.size()));                
    			request.setAttribute("PageTitle", "Manage Pages: Port Page");
    			request.setAttribute("fromFieldList", filteredFromQuest);//request.getAttribute("fromFieldList")
    			request.setAttribute("toFieldList", toFieldList); 
    			portPageForm.getAttributeMap().put("fromFieldList",filteredFromQuest);
    			if(context!=null && context.equals("ManagePagePorting")){
    				return mapping.findForward("managePortPage");
    			}else{
    				return PaginationUtil.paginate(portPageForm,request,"questionMappingPage",mapping);
    			}
    		}else{
    			
        		PortPageUtil.storeMappingInWAConversionMapping(portPageForm.getMappedQuestionsMap(), portPageForm.getNbsConversionPageMgmtUid(), request);

    			Map<Object,Object> ansFilterMap=new HashMap<Object,Object>();
    			ansFilterMap =portPageForm.getSearchCriteriaArrayMap(); 
    			//stores the filter criteria before mapping answer.
    			//boolean isCodeMappingRequired = portPageForm.getCurrentQuestion().isCodeMappingRequired();
    			//To return the filtered list ,if there are any filters applied before mapping an answer.
    			Collection<Object> filteredAnsList  = getFilteredAnswers(portPageForm.getFromPageAnswerList(),ansFilterMap);
    			portPageForm.initializeAnswerDD();
    			
    			portPageForm.getAttributeMap().put("fromAnswerList", filteredAnsList);
    			request.setAttribute("fromAnswerList",filteredAnsList);
    			portPageForm.getAttributeMap().put("queueList",filteredAnsList);
    			request.setAttribute("queueList",filteredAnsList);
    		
    			sortFromAnswers(portPageForm,filteredAnsList,true,request);
    			
    			request.setAttribute("mapReqCount",portPageForm.getAnsMapNeededCount());
    			request.setAttribute("totalCount",portPageForm.getFromPageAnswerList().size());
    			request.setAttribute("queueCount",String.valueOf(filteredAnsList.size()));

    			request.setAttribute("toAnswerList", portPageForm.getToPageAnswerList());
    			portPageForm.getAttributeMap().put("queueSize",PortPageUtil.MAPPING_PAGE_QUEUE_SIZE);
    			request.setAttribute("PageTitle", "Manage Pages: Port Page");
    			
    			
    	    	
    			return PaginationUtil.paginate(portPageForm,request,"answerMappingPage",mapping);
    		}
    	}catch(Exception ex){
            logger.fatal("Error submitSaveAndContinue method in PortPageAction : "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
    	}
	}
    
    public ActionForward loadProdRunPopUp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
    	try{
	    	PortPageForm portPageForm = (PortPageForm)form;
	    	request.setAttribute("fromPageName",portPageForm.getAttributeMap().get("FromPageName"));
	    	request.setAttribute("toPageName",portPageForm.getAttributeMap().get("ToPageName"));
	    	request.setAttribute("mapName", portPageForm.getMapName());
	    	request.setAttribute("selectedCondition", request.getParameter("selectedCondition"));
	    	request.setAttribute("numberOfCasesToMigrate", request.getParameter("numberOfCasesToMigrate"));
	    	return mapping.findForward("prodRunPopUp");
    	}catch(Exception ex){
            logger.fatal("Error loadProdRunPopUp method in PortPageAction : "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
    	}
    }
    
    private void updateListBeforeSort(Collection<Object> reviewLists) {
    	try{
			Iterator<Object> iter = reviewLists.iterator();
			while (iter.hasNext()) {
				MappedFromToQuestionFieldsDT  reviewList = (MappedFromToQuestionFieldsDT)iter.next();
				//Review Page
				if (reviewList.getFromQuestionId() == null || (reviewList.getFromQuestionId() != null && reviewList.getFromQuestionId().equals(""))) {
					reviewList.setFromQuestionId("ZZZZZ");
				}
				if (reviewList.getFromLabel() == null || (reviewList.getFromLabel() != null && reviewList.getFromLabel().equals(""))) {
					reviewList.setFromLabel("ZZZZZ");
				}
				if (reviewList.getFromDbLocation() == null || (reviewList.getFromDbLocation() != null && reviewList.getFromDbLocation().equals(""))) {
					reviewList.setFromDbLocation("ZZZZZ");
				}if (reviewList.getFromDataType() == null || (reviewList.getFromDataType() != null && reviewList.getFromDataType().equals(""))) {
					reviewList.setFromDataType("ZZZZZ");
					
				}if (reviewList.getFromCodeSetNm() == null || (reviewList.getFromCodeSetNm() != null && reviewList.getFromCodeSetNm().equals(""))) {
					reviewList.setFromCodeSetNm("ZZZZZ");
					
				}if (reviewList.getQuestionMappedDesc() == null || (reviewList.getQuestionMappedDesc() != null && reviewList.getQuestionMappedDesc().equals(""))) {
					reviewList.setQuestionMappedDesc("ZZZZZ");
					
				}if (reviewList.getToQuestionId() == null || (reviewList.getToQuestionId() != null && reviewList.getToQuestionId().equals(""))) {
					reviewList.setToQuestionId("ZZZZZ");
				}
				if (reviewList.getToLabel() == null || (reviewList.getToLabel() != null && reviewList.getToLabel().equals(""))) {
					reviewList.setToLabel("ZZZZZ");
				}
				if (reviewList.getToDbLocation() == null || (reviewList.getToDbLocation() != null && reviewList.getToDbLocation().equals(""))) {
					reviewList.setToDbLocation("ZZZZZ");
				}
				if (reviewList.getToDataType() == null || (reviewList.getToDataType() != null && reviewList.getToDataType().equals(""))) {
					reviewList.setToDataType("ZZZZZ");
				}
				if (reviewList.getToCodeSetNm() == null || (reviewList.getToCodeSetNm() != null && reviewList.getToCodeSetNm().equals(""))) {
					reviewList.setToCodeSetNm("ZZZZZ");
				}
				if (reviewList.getFromCode() == null || (reviewList.getFromCode() != null && reviewList.getFromCode().equals(""))) {
					reviewList.setFromCode("ZZZZZ");
				}
				if (reviewList.getAnswerMappedDesc() == null || (reviewList.getAnswerMappedDesc() != null && reviewList.getAnswerMappedDesc().equals(""))) {
					reviewList.setAnswerMappedDesc("ZZZZZ");
				}
				if (reviewList.getToCode() == null || (reviewList.getToCode() != null && reviewList.getToCode().equals(""))) {
					reviewList.setToCode("ZZZZZ");
				}
				
				//Question Page 
				if (reviewList.getQuestionMappedDesc() == null || (reviewList.getQuestionMappedDesc() != null && reviewList.getQuestionMappedDesc().equals(""))) {
					reviewList.setQuestionMappedDesc("ZZZZZ");
				}
				if (reviewList.getToQuestionId() == null || (reviewList.getToQuestionId() != null && reviewList.getToQuestionId().equals(""))) {
					reviewList.setToQuestionId("ZZZZZ");
				}
				if (reviewList.getToLabel() == null || (reviewList.getToLabel() != null && reviewList.getToLabel().equals(""))) {
					reviewList.setToLabel("ZZZZZ");
				}
				if (reviewList.getToDataType() == null || (reviewList.getToDataType() != null && reviewList.getToDataType().equals(""))) {
					reviewList.setToDataType("ZZZZZ");
				}
				
				
			
			}
    	}catch(Exception ex){
            logger.fatal("Error updateListBeforeSort method in PortPageAction : "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
    	}
		
	}
	private void updateListAfterSort(Collection<Object> reviewLists) {
		try{
			Iterator<Object> iter = reviewLists.iterator();
			while (iter.hasNext()) {
				MappedFromToQuestionFieldsDT  reviewList = (MappedFromToQuestionFieldsDT)iter.next();
				
				if (reviewList.getFromQuestionId() != null && reviewList.getFromQuestionId().equals("ZZZZZ")) {
					reviewList.setFromQuestionId("");
				}
				if (reviewList.getFromLabel() != null && reviewList.getFromLabel().equals("ZZZZZ")) {
					reviewList.setFromLabel("");
				}
				if ( reviewList.getFromDbLocation() != null && reviewList.getFromDbLocation().equals("ZZZZZ")) {
					reviewList.setFromDbLocation("");
				}if (reviewList.getFromDataType() != null && reviewList.getFromDataType().equals("ZZZZZ")) {
					reviewList.setFromDataType("");
					
				}if (reviewList.getFromCodeSetNm() != null && reviewList.getFromCodeSetNm().equals("ZZZZZ")) {
					reviewList.setFromCodeSetNm("");
					
				}if (reviewList.getQuestionMappedDesc() != null && reviewList.getQuestionMappedDesc().equals("ZZZZZ")) {
					reviewList.setQuestionMappedDesc("");
					
				}if ( reviewList.getToQuestionId() != null && reviewList.getToQuestionId().equals("ZZZZZ")) {
					reviewList.setToQuestionId("");
				}
				if (reviewList.getToLabel() != null && reviewList.getToLabel().equals("ZZZZZ")) {
					reviewList.setToLabel("");
				}
				if ( reviewList.getToDbLocation() != null && reviewList.getToDbLocation().equals("ZZZZZ")) {
					reviewList.setToDbLocation("");
				}
				if ( reviewList.getToDataType() != null && reviewList.getToDataType().equals("ZZZZZ")) {
					reviewList.setToDataType("");
				}
				if (reviewList.getToCodeSetNm() != null && reviewList.getToCodeSetNm().equals("ZZZZZ")) {
					reviewList.setToCodeSetNm("");
				}
				if ( reviewList.getFromCode() != null && reviewList.getFromCode().equals("ZZZZZ")) {
					reviewList.setFromCode("");
				}
				if ( reviewList.getAnswerMappedDesc() != null && reviewList.getAnswerMappedDesc().equals("ZZZZZ")) {
					reviewList.setAnswerMappedDesc("");
				}
				if ( reviewList.getToCode() != null && reviewList.getToCode().equals("ZZZZZ")) {
					reviewList.setToCode("");
				}
				//Question Page 
				if (reviewList.getQuestionMappedDesc() != null && reviewList.getQuestionMappedDesc().equals("ZZZZZ")) {
					reviewList.setQuestionMappedDesc("");
				}
				if (reviewList.getToQuestionId() != null && reviewList.getToQuestionId().equals("ZZZZZ")) {
					reviewList.setToQuestionId("");
				}
				if (reviewList.getToLabel() != null && reviewList.getToLabel().equals("ZZZZZ")) {
					reviewList.setToLabel("");
				}
				if (reviewList.getToDataType() != null && reviewList.getToDataType().equals("ZZZZZ")) {
					reviewList.setToDataType("");
				}
			}
		}catch(Exception ex){
            logger.fatal("Error updateListAfterSort method in PortPageAction : "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
    	}
	}
	 private void updateAnswersListBeforeSort(Collection<Object> reviewLists) {
	 	try{
			Iterator<Object> iter = reviewLists.iterator();
			while (iter.hasNext()) {
				AnswerMappingDT  reviewList = (AnswerMappingDT)iter.next();
				//Review Page
				if (reviewList.getMapped() == null || (reviewList.getMapped() != null && reviewList.getMapped().equals(""))) {
					reviewList.setMapped("ZZZZZ");
				}
				if (reviewList.getToCode() == null || (reviewList.getToCode() != null && reviewList.getToCode().equals(""))) {
					reviewList.setToCode("ZZZZZ");
				}
			}
	 	}catch(Exception ex){
            logger.fatal("Error updateAnswersListBeforeSort method in PortPageAction : "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
    	}
	 }
	 
	 private void updateAnswersListAfterSort(Collection<Object> reviewLists) {
	 	try{
			Iterator<Object> iter = reviewLists.iterator();
			while (iter.hasNext()) {
				AnswerMappingDT  reviewList = (AnswerMappingDT)iter.next();
				//Review Page
				if (reviewList.getMapped() != null && reviewList.getMapped().equals("ZZZZZ")) {
					reviewList.setMapped("");
				}
				if (reviewList.getToCode() != null && reviewList.getToCode().equals("ZZZZZ")) {
					reviewList.setToCode("");
				}
			}
	 	}catch(Exception ex){
            logger.fatal("Error updateAnswersListAfterSort method in PortPageAction : "+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage());
    	}
	 }
	 
	}


