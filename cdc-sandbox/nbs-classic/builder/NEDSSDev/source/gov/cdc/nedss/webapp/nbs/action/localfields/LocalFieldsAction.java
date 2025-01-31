package gov.cdc.nedss.webapp.nbs.action.localfields;

import gov.cdc.nedss.localfields.dt.LocalFieldsDT;
import gov.cdc.nedss.localfields.dt.NbsUiMetadataDT;
import gov.cdc.nedss.localfields.vo.NbsQuestionVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.localfields.LocalFieldsForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


/**
 * LocalFieldsAction delegates request from new PAMs and other pages
 * that needs to render LDFs with JSP page support.
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LocalFieldsAction.java
 * Aug 27, 2008
 * @version
 */
public class LocalFieldsAction extends DispatchAction {

	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils(LocalFieldsAction.class.getName());
	private static final String daoName = JNDINames.LOCAL_FIELD_METADATA_DAO_CLASS;
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";	
	static final String DELETE = "DELETE";		
	/**
	 * Default action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward manageLDF(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

		LocalFieldsForm manageForm = (LocalFieldsForm) form;
		getLDFsByPageset(manageForm, request);
		
		return mapping.findForward("default");
	}
	
	/**
	 * Prepares LDF MetaData for NBS_Question,NBS_UI_Metadata and NBS_UI_Component tables
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createLoadLDF(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		LocalFieldsForm manageForm = (LocalFieldsForm) form;
		try {
			manageForm.resetSelection();
			manageForm.setSelection(new NbsQuestionVO());
			manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			manageForm.getAttributeMap().put("cancel", "/nbs/LocalFields.do?method=manageLDF");
			manageForm.getAttributeMap().put("submit", "/nbs/LocalFields.do?method=createSubmitLDF");
			
			
		} catch (Exception e) {
			LocalFieldUtil.handleErrors(e, request, "create", "");
			logger.error("Exception in createLoadLDF: " + e.getMessage());
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			manageForm.setPageName(request);	
		}
		
		return (mapping.findForward("default"));		
	}	
	
	/**
	 * Submits LDF MetaData to NBS_Question,NBS_UI_Metadata tables
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createSubmitLDF(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		LocalFieldsForm manageForm = (LocalFieldsForm) form;
		
		if(displayTagPagination(request)) {
			getLDFsByPageset(manageForm, request);
			return (mapping.findForward("default"));
		}
		NbsQuestionVO vo = (NbsQuestionVO) manageForm.getSelection();
		populateQuestionVO(vo, manageForm);
		String ldfPageId = manageForm.getLdfPageId();
		//Check for duplicate Data Mart Column Name
		String returnSt = checkDuplicateDMColumn(mapping, manageForm, request);
		if(returnSt.length() > 0) 
			return mapping.findForward(returnSt);
		
		vo.getUiMetadata().setLdfPageId(ldfPageId);		
		vo.getUiMetadata().setInvestigationFormCd(manageForm.getFormCd());
		//Set the parentUid in the VO
		updateParentUid(vo, manageForm.getLdfPageId());
		
		request.setAttribute("manageList", manageForm.getManageList());
		try {
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");			
			Object[] searchParams ={vo, nbsSecurityObj};
			Object[] oParams = new Object[] { daoName, "createLocalField", searchParams};
			LocalFieldUtil.processRequest(oParams, request.getSession());
		} catch (Exception e) {
			LocalFieldUtil.handleErrors(e, request, NEDSSConstants.CREATE, "LDF");		
			manageForm.setPageName(request);	
			logger.error("Exception in createSubmitLDF: " + e.getMessage());
			return (mapping.findForward("default"));
		}
		manageForm.setSelection(vo); 
		manageForm.setOldDT(vo);
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		manageForm.getAttributeMap().remove("NORESULT");
		manageForm.setPageName(request);	
		
		try {
			getLDFsByPageset(manageForm, request);			
		} catch (Exception e) {
			logger.error("Error while searching : " + e.toString());
			LocalFieldUtil.handleErrors(e, request, "create", "");			
			return (mapping.findForward("default"));
		}			
		
		return (mapping.findForward("default"));		
	}	
	
	/**
	 * Loads LDF MetaData from NBS_Question,NBS_UI_Metadata and NBS_UI_Component tables to view
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewLDF(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		LocalFieldsForm manageForm = (LocalFieldsForm) form;
		try {
			manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			
			String cnxt = request.getParameter("context");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {				
				manageForm.setSelection(manageForm.getOldDT());
				return (mapping.findForward("default"));
			}			
			Long uiMetadataUid = Long.valueOf(request.getParameter("uiMetadataUid"));
			if(uiMetadataUid != null && uiMetadataUid > 0) {
				Object[] searchParams = {uiMetadataUid};
				Object[] oParams = new Object[] { daoName, "getLocalField", searchParams };
				ArrayList<Object> dtList = (ArrayList<Object> ) LocalFieldUtil.processRequest(oParams, request.getSession());
				if(dtList.size() > 0) {
					NbsQuestionVO vo = (NbsQuestionVO) dtList.get(0);
					prepareTabsAndSections(vo, manageForm);
				}
			}
		} catch (Exception e) {
			manageForm.setActionMode("Manage");
			LocalFieldUtil.handleErrors(e, request, "view", "");
			logger.error("Exception in viewLDF: " + e.getMessage());
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			manageForm.setPageName(request);
		}
		return (mapping.findForward("default"));
	}
	
	/**
	 * Loads LDF MetaData from NBS_Question,NBS_UI_Metadata and NBS_UI_Component tables for edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editLDF(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		LocalFieldsForm manageForm = (LocalFieldsForm) form;		
		try {
			manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);				
			Long uiMetadataUid = Long.valueOf(request.getParameter("uiMetadataUid"));
			if(uiMetadataUid != null && uiMetadataUid > 0) {
				Object[] searchParams = {uiMetadataUid};
				Object[] oParams = new Object[] { daoName, "getLocalField", searchParams };
				ArrayList<Object> dtList = (ArrayList<Object> ) LocalFieldUtil.processRequest(oParams, request.getSession());
				if(dtList.size() > 0) {
					NbsQuestionVO vo = (NbsQuestionVO) dtList.get(0);
					prepareTabsAndSections(vo, manageForm);
				}
			}			
			manageForm.getAttributeMap().put("cancel", "/nbs/LocalFields.do?method=viewLDF&context=cancel#localField");
			manageForm.getAttributeMap().put("submit", "/nbs/LocalFields.do?method=updateLDF");
		} catch (Exception e) {
			manageForm.setActionMode("Manage");
			LocalFieldUtil.handleErrors(e, request, "edit", "");
			logger.error("Exception in editLDF: " + e.getMessage());
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			manageForm.setPageName(request);
		}
		
		return (mapping.findForward("default"));		
		
	}
	
	/**
	 * Updates LDF MetaData into NBS_Question,NBS_UI_Metadata tables
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateLDF(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		LocalFieldsForm manageForm = (LocalFieldsForm) form;

		if(displayTagPagination(request)) {
			getLDFsByPageset(manageForm, request);
			return (mapping.findForward("default"));
		}
				
		NbsQuestionVO vo = (NbsQuestionVO) manageForm.getSelection();
		populateQuestionVO(vo, manageForm);
	
		//Check for duplicate Data Mart Column Name
		String returnSt = checkDuplicateDMColumn(mapping, manageForm, request);
		if(returnSt.length() > 0) 
			return mapping.findForward(returnSt);
		
		//Set the parentUid in the VO
		updateParentUid(vo,manageForm.getLdfPageId());

		try {
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
			Object[] searchParams = {vo, nbsSecurityObj};
			Object[] oParams = new Object[] { daoName, "updateLocalField", searchParams };
			LocalFieldUtil.processRequest(oParams, request.getSession());
		} catch (Exception e) {
			LocalFieldUtil.handleErrors(e, request, "update","LDF");
			request.setAttribute("manageList", manageForm.getManageList());
			logger.error("Exception in updateLDF: " + e.getMessage());
			manageForm.setPageName(request);
			return (mapping.findForward("default"));
		} 
		manageForm.setPageName(request);
		try {
			getLDFsByPageset(manageForm, request);
		} catch (Exception e) {
			logger.error("Error while searching : " + e.toString());
			LocalFieldUtil.handleErrors(e, request, "update", "");			
			return (mapping.findForward("default"));
		}
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		prepareTabsAndSections((NbsQuestionVO)manageForm.getSelection(), manageForm);
		return (mapping.findForward("default"));		
	}
	
	/**
	 * Deletes LDF MetaData from NBS_Question,NBS_UI_Metadata
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteLDF(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		LocalFieldsForm manageForm = (LocalFieldsForm) form;	
		if(displayTagPagination(request)) {
			getLDFsByPageset(manageForm, request);
			return (mapping.findForward("default"));
		}		
		
		int associatedLDFCount = 0;
		try {
			manageForm.setActionMode("Manage");				
			Long uiMetadataUid = Long.valueOf(request.getParameter("uiMetadataUid"));
			Long questionUid = request.getParameter("questionUid").equals("null") ? new Long(0) : Long.valueOf(request.getParameter("questionUid"));

			//Step 1: Check if any LDF Specific Tab is present (questionUid = '0') and see if it has any LDFs added as parent
			//we know this is a TAB
			if(questionUid.longValue() == 0) {
				Object[] searchParams = {uiMetadataUid};
				Object[] oParams = new Object[] { daoName, "checkLocalFieldsAssociatedToTab", searchParams };
				 ArrayList<Object> list = (ArrayList<Object> )LocalFieldUtil.processRequest(oParams, request.getSession());
				 associatedLDFCount = ((Integer)list.get(0)).intValue();
			}
			//If count > 0, throw an alert to the user that he cannot delete the TAB without deleting the LDFs associated to that TAB first
			if(associatedLDFCount > 0) {
				request.setAttribute("deleteError", "You cannot Delete this Tab as there are " + associatedLDFCount + " LDF(s) associated to it.");
				return (mapping.findForward("default"));
			}
			//Step 2: If count is 0, then there are no LDFs associated to the new TAB, so go ahead and delete the LDF
			if(uiMetadataUid != null && questionUid != null) {
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
				Object[] searchParams = {uiMetadataUid, questionUid, manageForm.getFormCd(), nbsSecurityObj};
				Object[] oParams = new Object[] { daoName, "deleteLocalField", searchParams };
				LocalFieldUtil.processRequest(oParams, request.getSession());
			}		
		} catch (Exception e) {
			LocalFieldUtil.handleErrors(e, request, "delete", "");
			logger.error("Exception in deleteLDF: " + e.getMessage());
		} finally {
			getLDFsByPageset(manageForm, request);
		}
		return (mapping.findForward("default"));	
		
	}
	
	/**
	 * Previews page by the ldf pageset id
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward previewLDF(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		LocalFieldsForm manageForm = (LocalFieldsForm) form;
		try {
			String formCd = manageForm.getFormCd();
			if(formCd != null) {
				NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationFormCd, formCd);
				//RVCT
				if(formCd.equalsIgnoreCase(NBSConstantUtil.INV_FORM_RVCT))
					return (mapping.findForward("RVCT"));
				else if(formCd.equalsIgnoreCase(NBSConstantUtil.INV_FORM_VAR))					
					return (mapping.findForward("Varicella"));
				else if(formCd.equalsIgnoreCase(NEDSSConstants.HOME_PAGE_LDF))					
					return (mapping.findForward("homePage"));
				
			}
		} catch (Exception e) {
			LocalFieldUtil.handleErrors(e, request, "preview", "");
			request.setAttribute("manageList", manageForm.getManageList());
			logger.error("Exception in previewLDF: " + e.getMessage());
		}
		
		return (mapping.findForward("default"));
	}
	
	
	
	/**
	 * Sets the List<Object> of LDFs for the selected PageSet Business Object
	 * @param manageForm
	 * @param request
	 */
	private void getLDFsByPageset(LocalFieldsForm manageForm, HttpServletRequest request) {
		try {
			
			manageForm.setActionMode("Manage");
			manageForm.setPageName(request);
			String pagesetId = request.getSession().getAttribute("PageID") == null ? "" : (String)  request.getSession().getAttribute("PageID");
			if(! pagesetId.equals("")) {
				manageForm.setLdfPageId(pagesetId);
				//R3.1 HOME PAGE LDFs also part of new structure
				if(pagesetId.equals("300")) {
					manageForm.setFormCd(NEDSSConstants.HOME_PAGE_LDF);
				} else {
					String invFormCd = CachedDropDowns.getInvFrmCdLdfPgIdMap().get(pagesetId) == null ? null : (String) CachedDropDowns.getInvFrmCdLdfPgIdMap().get(pagesetId);
					manageForm.setFormCd(invFormCd);
				}
			}
		    
			Object[] searchParams = {pagesetId};
			Object[] oParams = new Object[] { daoName, "findLocalFields", searchParams };
			ArrayList<Object> dtList = (ArrayList<Object> ) LocalFieldUtil.processRequest(oParams, request.getSession());
			if(dtList.size() > 0) {
				Iterator<Object> iter = dtList.iterator();
				while(iter.hasNext()) {					
					LocalFieldsDT dt = (LocalFieldsDT) iter.next();
					//If questionUid is NULL, we know it is a Tab and that cannot be deleted
					LocalFieldUtil.makeLDFLink(dt, VIEW);
					LocalFieldUtil.makeLDFLink(dt, EDIT);
					LocalFieldUtil.makeLDFLink(dt, DELETE);
					//populate Tab, Section and Subsection information
					LocalFieldUtil.populateParents(dt, manageForm);
				}
			} else {
				manageForm.getAttributeMap().put("NORESULT","NORESULT");
			}
			manageForm.setManageList(dtList);				
			request.setAttribute("manageList", manageForm.getManageList());
			
		} catch (Exception e) {
			LocalFieldUtil.handleErrors(e, request, "retrieve", "");
			logger.error("Exception in getLDFsByPageset: " + e.getMessage());
		}		
	}
	
	/**
	 * prepareTabsAndSections sets the appropriate section and tab information the particular LDF is tied to
	 * @param dtList
	 * @param manageForm
	 */
	private void prepareTabsAndSections (NbsQuestionVO vo, LocalFieldsForm manageForm) {	
		//Reset 
		vo.reset();		
		manageForm.getAttributeMap().remove("showDataMartColNm");
		//Check & see componentUid and set attributes accordingly
		putDMIndicator(vo, manageForm);

		//For Homepage LDFs just set the selection and return
		String ldfPageId = manageForm.getLdfPageId();
		if(ldfPageId != null && ldfPageId.equals("300")) {
			manageForm.setSelection(vo);
			manageForm.setOldDT(vo);
			return;			
		}
		
		NbsUiMetadataDT dt = vo.getUiMetadata();
		Long parentUid = dt.getParentUid();
		if(parentUid == null) {
			vo.getQuestion().setCodeSetGroupId(new Long(0));
			manageForm.setSelection(vo);
			manageForm.setOldDT(vo);			
			return;
		}
		if(dt.getRequiredInd() != null && dt.getRequiredInd().equals("T"))
			dt.setRequiredInd("1");
		
		if(dt.getFutureDateInd() != null && dt.getFutureDateInd().equals("T"))
			dt.setFutureDateInd("1");
		//parentUid may be either a subsection or a tab
		ArrayList<Object> list = CachedDropDowns.getAvailableTabs(manageForm.getFormCd());
		//Tab Iterator
		Iterator<Object> iter = list.iterator();
		while(iter.hasNext()) {
			DropDownCodeDT ddcDT = (DropDownCodeDT) iter.next();
			if(ddcDT.getKey().equals("")) continue;
			Long uid = Long.valueOf(ddcDT.getKey());
			ArrayList<Object> alist = CachedDropDowns.getLDFSections(uid);
			//If there are no sections, to the minimum, set the tab info
			if(alist != null && alist.size() == 1) {
				vo.setTabId(uid);
				vo.setTabName(ddcDT.getValue());
				//set
				manageForm.setSelection(vo);
				manageForm.setOldDT(vo);
				return;
			}
			//Section Iterator
			Iterator<Object> iter1 = alist.iterator();
			while(iter1.hasNext()) {
				DropDownCodeDT secDT = (DropDownCodeDT) iter1.next();
				if(secDT.getKey().equals("")) continue;
				Long sUid = Long.valueOf(secDT.getKey());
				ArrayList<Object> ssList = CachedDropDowns.getLDFSubSections(sUid);
				if(ssList != null && ssList.size() > 1) {
					//SubSection Iterator
					Iterator<Object> iter2 = ssList.iterator();
					while(iter2.hasNext()) {
						DropDownCodeDT ssecDT = (DropDownCodeDT) iter2.next();
						if(ssecDT.getKey().equals("")) continue;
						Long ssUid = Long.valueOf(ssecDT.getKey());
						if(parentUid.compareTo(ssUid) == 0) {
							vo.setSubSectionId(ssUid);
							vo.setSubSectionName(ssecDT.getValue());
							vo.setSectionId(sUid);
							vo.setSectionName(secDT.getValue());
							vo.setTabId(uid);
							vo.setTabName(ddcDT.getValue());
							//set
							manageForm.setSelection(vo);
							manageForm.setOldDT(vo);
							return;
						}						
					}
				}	
			}
		}
	}
	
	private void populateQuestionVO(NbsQuestionVO vo, LocalFieldsForm manageForm) {
		
		Long componentUid = vo.getUiMetadata().getNbsUiComponentUid();
		
		//DataTypes need to be mapped appropriately once the user selects Single-Select or Multi-Select
		if(componentUid.compareTo(new Long(1007)) == 0  || componentUid.compareTo(new Long(1013)) == 0) {
			vo.getQuestion().setDataType("Coded");
		//User entered text, number, or date	
		} else if(componentUid.compareTo(new Long(1008)) == 0) {
			if(vo.getQuestion().getDataType() == null || (vo.getQuestion().getDataType() != null && vo.getQuestion().getDataType().trim().equals("")))
				vo.getQuestion().setDataType("Text");
		//Textarea 1009
		} else if(componentUid.compareTo(new Long(1009)) == 0 ) {
			vo.getQuestion().setDataType("TextArea");
		//Subheading 1011
		} else if(componentUid.compareTo(new Long(1011)) == 0 ) {
			vo.getQuestion().setDataType("Subheading");
		//Readonly 1014	
		} else if(componentUid.compareTo(new Long(1014)) == 0) {
			vo.getQuestion().setDataType("Readonly");
		//Hyperlink 1003	
		} else if(componentUid.compareTo(new Long(1003)) == 0) {
			vo.getQuestion().setDataType("Hyperlink");
		}
	}
	
	private static boolean displayTagPagination(HttpServletRequest request) {
		boolean found = false;
		Enumeration<String> enm = request.getParameterNames();
		while(enm.hasMoreElements()) {
			String paramName = (String) enm.nextElement();
			if(paramName != null && paramName.startsWith("d-")) {
				found = true;
				break;
			}
		}
		
		return found;
	}	

	private static void updateParentUid(NbsQuestionVO vo, String ldfPageId) {
		//For HomePage, parentUid ='6001' (HC for now) since no Tabs/Sections/Subsections are available for HomePage
		if(ldfPageId != null && ldfPageId.equals("300")) {
			vo.getUiMetadata().setParentUid(new Long(6001));			
		} else {
			//set the parent uid based on  Tab/Section this LDF is tied to 
			Long tabId = vo.getTabId() == null ? new Long(0) : vo.getTabId();
			//Check if the tab value is >= 1000000 and if so, it is a LDF specific tab, so ignore section and subsection
			if(tabId.longValue() >= 1000000) {
				vo.getUiMetadata().setParentUid(tabId);
			} else {
				Long subSectionId = vo.getSubSectionId() == null ? new Long(0) : vo.getSubSectionId();
				if(subSectionId.longValue() > 0)
					vo.getUiMetadata().setParentUid(subSectionId);
				else {
					if(tabId.longValue() > 0)
						vo.getUiMetadata().setParentUid(tabId);
				}			
			}				
		}
		
	
		
		//Also check if cUid == 1008 (User entered text) remove dataType if other than Date/Numeric/Text
		Long cUid = vo.getUiMetadata().getNbsUiComponentUid();
		if(cUid.intValue() == 1008) {
			String dataType = vo.getQuestion().getDataType() == null ? "" : vo.getQuestion().getDataType(); 
			if(!dataType.equals("Date") && !dataType.equals("Numeric") && !dataType.equals("Text") )
				vo.getQuestion().setDataType(null);
		}
		
		//For HyperLinks, get the linkUrl from vo and set it as Tooltip in UIMetadata
		if(cUid.intValue() == 1003) {
			String linkUrl = vo.getLinkUrl() == null ? "" : vo.getLinkUrl();
			if(!linkUrl.equals("")) 
				vo.getUiMetadata().setQuestionToolTip(linkUrl);
		}
		
		
		
	}
	
	public String checkDuplicateDMColumn(ActionMapping mapping, LocalFieldsForm manageForm, HttpServletRequest request) {
		String returnSt = "";
		String actionMode = manageForm.getActionMode() == null ? "" : manageForm.getActionMode();
		int duplicateCount = 0;
		NbsQuestionVO vo = (NbsQuestionVO)manageForm.getSelection();
		String dmColumnName = vo.getQuestion().getDatamartColumnNm() == null ? "" : vo.getQuestion().getDatamartColumnNm();
		
		//For edit check
		if(actionMode.equals(NEDSSConstants.EDIT_LOAD_ACTION)) {
			String oldDmColumnName = ((NbsQuestionVO)manageForm.getOldDT()).getQuestion().getDatamartColumnNm();
			if(oldDmColumnName != null && oldDmColumnName.equalsIgnoreCase(dmColumnName)) {
				dmColumnName = "";
			}
		}
		
		//Step 1: Check if DataMart Column Name is present  
		if(!dmColumnName.equals("")) {
			try {
				Object[] searchParams = {dmColumnName, manageForm.getFormCd()};
				Object[] oParams = new Object[] { daoName, "checkDMColumnForDuplicate", searchParams };
				 ArrayList<Object> list = (ArrayList<Object> )LocalFieldUtil.processRequest(oParams, request.getSession());
				 duplicateCount = ((Integer)list.get(0)).intValue();
			} catch (Exception e) {
				LocalFieldUtil.handleErrors(e, request, NEDSSConstants.CREATE, "LDF");		
				manageForm.setPageName(request);	
				logger.error("Exception while checkingf for duplicate DataMart ColumnName(s) : " + e.getMessage());
				returnSt= "default";
			}			 
			//If count > 0, throw an alert to the user that he cannot delete the TAB without deleting the LDFs associated to that TAB first
			if(duplicateCount > 0) {
				getLDFsByPageset(manageForm, request);
				ActionMessages messages = new ActionMessages();
				messages.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
						new ActionMessage(NBSPageConstants.ERROR_DUP_MESSAGE_KEY,"Datamart Column Name"));
				request.setAttribute("error_messages", messages);				
				request.setAttribute("duplicateDMColumn", "The DataMart Column Name entered is already present. Please enter a different value.");
				manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
				returnSt = "default";
				//Check & see componentUid and set attributes accordingly
				putDMIndicator(vo, manageForm);

			}			
		}
		return returnSt;
	}
	
	private static void putDMIndicator(NbsQuestionVO vo, LocalFieldsForm manageForm) {
		
		Long cUid = vo.getUiMetadata().getNbsUiComponentUid();
		if(cUid.intValue() == 1007 || cUid.intValue() == 1008 || cUid.intValue() == 1009 || cUid.intValue() == 1013) {
			manageForm.getAttributeMap().put("showDataMartColNm", "showDataMartColNm");			
		}
		//If hyper link, set URL from tool tip;
		if(cUid.intValue() == 1003)
			vo.setLinkUrl(vo.getUiMetadata().getQuestionToolTip());
	}
	
}