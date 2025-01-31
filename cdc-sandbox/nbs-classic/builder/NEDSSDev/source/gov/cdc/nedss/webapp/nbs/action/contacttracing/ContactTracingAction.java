package gov.cdc.nedss.webapp.nbs.action.contacttracing;

import gov.cdc.nedss.act.ctcontact.dt.CTContactAttachmentDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactNoteDT;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.pagecache.util.PagePublisher;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTContactLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.AddNotesAction;
import gov.cdc.nedss.webapp.nbs.form.contacttracing.CTContactForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
 * Struts Action Class to handle new contacts and redirect to the appropriate
 * actionHandler based on the Page Form Code. Dynamic Pages form code starts
 * with CT_ The legacy Contact form code is always 'CONTACT_REC' and goes to the
 * static JSP page.
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: Computer Sciences Corporation
 * </p>
 * ContactTracingAction.java Oct 26, 2009
 * 
 * @version
 */

public class ContactTracingAction extends DispatchAction {
	static final LogUtils logger = new LogUtils(
			ContactTracingAction.class.getName());
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();

	public ActionForward AddContactLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String actionForward = "";
		
		String contactFormCd = PageManagementCommonActionUtil
				.checkIfPublishedPageExists(request,
						NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE);

		try {
			
		
			
			
			
			String mprUid = request.getParameter("MprUid");
			CTContactForm ctContactForm = (CTContactForm) form;
			if (contactFormCd == null || contactFormCd.isEmpty()) {
				CTContactLoadUtil.addLegacyLoadUtil(ctContactForm, request,
						response, mprUid);
				actionForward = "ContactAdd";
			} else { // published page
				String serverRestart = propertyUtil.getServerRestart();
				if (serverRestart != null
						&& serverRestart.equalsIgnoreCase(NEDSSConstants.FALSE)) {
					PagePublisher publisher = PagePublisher.getInstance();
					try {
						logger.info("Try publishing the page from contact page create load");
						boolean success = publisher.publishPage(contactFormCd);
						logger.info("Published the page from contact page create load :"
								+ success);
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServletException(
								"Error while copying from NEDSS to Temp Folder for Contact Record: "
										+ e.getMessage(), e);
					}
				}

				ctContactForm.setPageFormCd(contactFormCd);
				CTContactLoadUtil.addContactPageLoadUtil(ctContactForm,
						request, response, mprUid);
				actionForward = "default";
			}
		} catch (Exception e) {
			logger.error("Error while loading AddContactLoad: " + e.toString());
			throw new ServletException("Error while lAddContactLoad: "
					+ e.getMessage(), e);
		}
		return (mapping.findForward(actionForward));
	}

	public ActionForward AddContactSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

			
		
			
		String actionForward = "";
		try {
			String context = null;
			Long mprUid2 = null;
			if(request.getParameter("MprUid")!=null)
					mprUid2 = new Long(request.getParameter("MprUid"));
			else		
				mprUid2 = (Long) NBSContext.retrieve(request.getSession(),
					NBSConstantUtil.DSPersonSummary);
			
			String condition = (String)request.getParameter(NEDSSConstants.CONDITION_CD);
			context = (String)request.getParameter("context");
			String referralBasis = request.getParameter("referralBasis");
			if(referralBasis!=null && referralBasis.equals(CTConstants.CongenitalInfantFollowupM1))
				condition = CTConstants.CongenitalSyphilisConditionCode;
			if(referralBasis!=null && referralBasis.equals(CTConstants.CongenitalMotherFollowupM2))
				condition = CTConstants.SyphilisUnknownConditionCode;
			
			if (referralBasis!=null && referralBasis.equals(CTConstants.CongenitalInfantFollowupM1)) {
				String condition2 = CTConstants.CongenitalSyphilisConditionCode;
				ArrayList<Object> openInvestigationList = PageLoadUtil.getOpenInvList(condition2, mprUid2, request);
				if(openInvestigationList!=null && openInvestigationList.size()>0){
					request.setAttribute("condition", CachedDropDowns.getConditionDesc(condition2));
					context = "invAlreadyExists";
					return (mapping.findForward(context));
				}
			}
			if (referralBasis!=null && referralBasis.equals(CTConstants.CongenitalMotherFollowupM2)) {
				String condition2 = CTConstants.SyphilisUnknownConditionCode;
				ArrayList<Object> openInvestigationList = PageLoadUtil.getOpenInvList(condition2, mprUid2, request);
				if(openInvestigationList!=null && openInvestigationList.size()>0){
					request.setAttribute("condition", CachedDropDowns.getConditionDesc(condition2));
					context = "invAlreadyExists";
					return (mapping.findForward(context));
				}
			}			
			
			
			CTContactForm ctContactForm = (CTContactForm) form;
			/*
			String referralBasis = (String)((ClientVO) ctContactForm.getcTContactClientVO()).getAnswerMap().get(CTConstants.ReferralBasis);

			if (referralBasis.equals(CTConstants.CongenitalInfantFollowupM1)) {
				String condition = CTConstants.CongenitalSyphilisConditionCode;
				ArrayList<Object> openInvestigationList = PageLoadUtil.getOpenInvList(condition, ctContactForm.getMprUid(), request);//TODO FATIMAA
				if(openInvestigationList!=null && openInvestigationList.size()>0){
					request.setAttribute("condition", CachedDropDowns.getConditionDesc(condition));
					String context = "invAlreadyExists";
					return (mapping.findForward(context));
				}

			}
*/
			ctContactForm.setActionMode(NEDSSConstants.CREATE_SUBMIT_ACTION);
			// if it is a legacy page
			if (ctContactForm.getPageFormCd() != null
					&& ctContactForm.getPageFormCd().equals(
							NEDSSConstants.CONTACT_FORMCODE)) {
				actionForward = "ContactInsert";
				CTContactLoadUtil.createHandler(ctContactForm, request);
				if (ctContactForm.getErrorList() != null
						&& ctContactForm.getErrorList().size() > 0)
					actionForward = "ContactAdd";
			} else { // dynamic page
				actionForward = "ContactInsert";
				CTContactLoadUtil.createPageHandler(ctContactForm, request);
				if (ctContactForm.getErrorList() != null
						&& ctContactForm.getErrorList().size() > 0)
					actionForward = "default";
				
			
				
				
				
				
			}
			// set the success messages
			if (ctContactForm.getErrorList() == null
					|| ctContactForm.getErrorList().size() <= 0) {
				ActionMessages messages = new ActionMessages();
				messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
						new ActionMessage(
								NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY,
								NBSPageConstants.CONTACT_RECORD_TEXT));
				request.setAttribute("success_messages", messages);
			}
			
				
		} catch (Exception e) {
			logger.error("Error while loading AddContactSubmit: "
					+ e.toString());
			e.printStackTrace();
			throw new ServletException("Error while AddContactSubmit: "
					+ e.getMessage(), e);
		}
		
		
			
		return (mapping.findForward(actionForward));
	}

	@SuppressWarnings("unchecked")
	public ActionForward viewContact(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	throws IOException, ServletException {
		String contactFormCd = null;
		String actionForward = "";
		//See if a dynamic contact page exists..
		String paramCondCd = request.getParameter(NBSConstantUtil.DSInvestigationCondition);  //passed from File Workup
		if (paramCondCd != null && !paramCondCd.isEmpty()) 
			contactFormCd =PageManagementCommonActionUtil.checkIfPublishedPageExists(request, NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE, paramCondCd);
		else
		    contactFormCd = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE);
		String mprUid = null;
		
		CTContactForm ctContactForm = (CTContactForm) form;
		if (contactFormCd == null  || contactFormCd.isEmpty()) { //no dynamic contact page - use the legacy
				ctContactForm.setPageFormCd(NBSConstantUtil.CONTACT_REC);
				actionForward = "ContactView";
		} else {
				//dynamic contact page is published for the condition
				String serverRestart = propertyUtil.getServerRestart();
				if (serverRestart != null && serverRestart.equalsIgnoreCase(NEDSSConstants.FALSE)){
					PagePublisher publisher = PagePublisher.getInstance();
					try {
						logger.info("Try publishing the page from contact page view load");
						boolean success = publisher.publishPage(contactFormCd);
						logger.info("Published the page from contact page view load :"+success);
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServletException("Error while copying from NEDSS to Temp Folder for Contact Record: "+e.getMessage(),e);
					}
				}
				actionForward = "ContactViewPage";
				ctContactForm.setPageFormCd(contactFormCd);
				PageManagementCommonActionUtil.setTheRenderDirectory(request, contactFormCd, NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE);
		}
		try {			
		request.setAttribute("mode", request.getParameter("mode"));
		String contactRecordUID = request.getParameter("contactRecordUid");
		CTContactProxyVO proxyVO = null;
		if (ctContactForm.getPageFormCd().equalsIgnoreCase(NBSConstantUtil.CONTACT_REC)) { //legacy
			proxyVO =CTContactLoadUtil.viewLoadUtil(ctContactForm, request, contactRecordUID);
		} else { //dynamic page
			proxyVO =CTContactLoadUtil.viewPageLoadUtil(ctContactForm, request, contactRecordUID);
		}
		NBSContext.store(request.getSession(), NBSConstantUtil.ContactTracing, "ContactTracing");
		request.setAttribute("formCode", ctContactForm.getPageFormCd());
		String action = request.getParameter("Action");
		String invPath = "";
		
		if(action!=null && action.equalsIgnoreCase("DSFilePath") ){
				String programAreaCd = proxyVO.getcTContactVO().getcTContactDT().getProgAreaCd();
				String conditionCd = request.getParameter(NBSConstantUtil.DSInvestigationCondition);
				NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationProgramArea, programAreaCd);
				NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationCondition, conditionCd);
				NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationPath, "DSFilePath");
		}
		else{
			invPath = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationPath);
			if(invPath.equalsIgnoreCase("DSFilePath")){
				mprUid = ((Long)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSPatientPersonUID)).toString();
				request.setAttribute("mprUid", mprUid);
			if(request.getParameter("DSInvestigationLocalID") != null){
				String strPhcLocalID = request.getParameter("DSInvestigationLocalID").toString();
				request.setAttribute("phcUID", strPhcLocalID);
				}
			}else{
				String strPhcLocalUID = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationLocalID);
				request.setAttribute("phcUID", strPhcLocalUID);
			}
		}
			
		
		Long subEntityPhcUidId = proxyVO.getcTContactVO().getcTContactDT().getSubjectEntityPhcUid();

		logger.info("subEntityPhcUidId ---------------------------------------------"+subEntityPhcUidId);
		
		// To display button only from investigation's Contacts Named by Patient section
		
		String investigationUID = null;
		try{
			investigationUID = (String) NBSContext.retrieve(request.getSession(), "DSInvestigationUID");
		}catch(NullPointerException e){
			logger.info("Object is null from session : "+e.getMessage());
		}
		
		logger.info("DSInvestigationUID---------------------------------"+investigationUID);
		
		//Find if more then one Co-infection associated with this investigation, if more then one Co-infection then show Associate Investigation button
        Integer coinfCount = null;
        
        try {
        	coinfCount = PageLoadUtil.getSpecificCoinfectionInvListCount( new Long(investigationUID), request);
        } catch (NEDSSAppConcurrentDataException e) {
               logger.error("In associateInterviewInvestigationsLoad() Conurrent data access for contact for phc=="+investigationUID);
               e.printStackTrace();
        } catch (Exception e) {
               logger.error("Exception encontered - contact coinfections="+investigationUID);
               e.printStackTrace();
        }
        if(coinfCount!=null)
        	logger.info("Total coinfection investigation found --------------------- "+coinfCount.intValue());

        
		if(investigationUID!=null && investigationUID.length()>0 && coinfCount!=null && coinfCount.intValue()>1){
			
			//make sure we are coming from Contacts Name by Patient and not Patient Named by Contacts
			if(investigationUID.equals(Long.toString(subEntityPhcUidId)))
				request.setAttribute("showAssociateInvestigationBtn", true);
			else
				request.setAttribute("showAssociateInvestigationBtn", false);
		}else{
			request.setAttribute("showAssociateInvestigationBtn", false);
		}
		
		if(subEntityPhcUidId != null){
			request.setAttribute("subjectEntityPHCUid", subEntityPhcUidId);
		}else{
			logger.error("Cannot find subEntityPhcUidId from proxyVO");
		}
		
	
		// this was done to account for print mode of contact record.
		if (contactRecordUID == null) {
				contactRecordUID = (String) NBSContext.retrieve(request.getSession(),
						NBSConstantUtil.DSContactUID);
		}
			
		if(contactRecordUID!=null)
		{
			Long ctUid = Long.valueOf(contactRecordUID);
				
				// attachments
			Collection<Object> attachmentDTs = processRequest(ctUid, request.getSession());
			if(attachmentDTs != null && attachmentDTs.size() > 0)
					_updateAttachmentsForView(attachmentDTs);
			request.setAttribute("contactAttachments", attachmentDTs);
				
				// notes
			Collection<Object> notesDTs = ((CTContactForm) request.getSession().getAttribute("contactTracingForm")).
				getcTContactClientVO().getOldCtContactProxyVO().getcTContactVO().getNoteDTCollection();
			if(notesDTs != null && notesDTs.size() > 0)
				_updateNotesForView(notesDTs);
				request.setAttribute("contactNotes", notesDTs);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading viewContact: " + e.toString());
			throw new ServletException("Error while viewContact: "+e.getMessage(),e);
		}
		return (mapping.findForward(actionForward));
	}


	@SuppressWarnings("unchecked")
	private static Collection processRequest(Long ctUid, HttpSession session)
			throws Exception {

		MainSessionCommand msCommand = null;
		Collection<Object> returnColl = null;
		try {
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "getContactAttachmentSummaryCollection";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[] oParams = { ctUid };
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
			returnColl = (Collection<Object>) arr.get(0);

		} catch (Exception ex) {
			logger.error("Error while processRequest in FileUploadAction: "
					+ ex.toString());
			throw new Exception(ex);
		}
		return returnColl;
	}

	/**
	 * 
	 * @param attachmentDTs
	 */
	private static void _updateAttachmentsForView(
			Collection<Object> attachmentDTs) throws Exception {
		try {
			Iterator<Object> iter = attachmentDTs.iterator();
			while (iter.hasNext()) {
				CTContactAttachmentDT dt = (CTContactAttachmentDT) iter.next();
				Long uId = dt.getLastChgUserId();
				RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
				String userNm = retrieveSummaryVO.getUserName(uId);
				dt.setLastChgUserNm(userNm);
				// SET delete Link
				String delUrl = "<a id=\"td_" + dt.getCtContactAttachmentUid()
						+ "\" href=javascript:deleteAttachment('"
						+ dt.getCtContactAttachmentUid() + "')>Delete</a>";
				dt.setDeleteLink(delUrl);
				// SET view Link
				HashMap<Object, Object> parameterMap = new HashMap<Object, Object>();
				parameterMap.put("ctContactAttachmentUid",
						String.valueOf(dt.getCtContactAttachmentUid()));
				parameterMap.put("fileNmTxt", dt.getFileNmTxt());
				dt.setViewLink(buildHyperLink("DownloadFile.do", parameterMap,
						null, dt.getFileNmTxt()));
			}
		} catch (Exception e) {
			logger.error("Error while _updateAttachmentsForView in ContactTracingAction: "
					+ e.toString());
			throw e;
		}
	}

	@SuppressWarnings("unused")
	private static void _updateNotesForView(Collection<Object> notesDTs)
			throws Exception {
		try {
			Iterator<Object> iter = notesDTs.iterator();
			while (iter.hasNext()) {
				CTContactNoteDT dt = (CTContactNoteDT) iter.next();
				Long uId = dt.getLastChgUserId();
				RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
				String userNm = retrieveSummaryVO.getUserName(uId);
				dt.setLastChgUserNm(userNm);
				dt.setPrivateIndCd(AddNotesAction
						.decoratePrivateIndicatorField(dt.getPrivateIndCd()));
			}
		} catch (Exception e) {
			logger.error("Error while _updateNotesForView in ContactTracingAction: "
					+ e.toString());
			throw e;
		}
	}

	public static String buildHyperLink(String strutsAction,
			Map<Object, Object> paramMap, String jumperName, String displayNm)
			throws Exception {
		StringBuffer url = new StringBuffer();
		StringBuffer reqParams = new StringBuffer("?");
		try {
			Iterator<Object> iter = paramMap.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String value = (String) paramMap.get(key);
				reqParams.append(key).append("=");
				reqParams.append(value.replaceAll(" ", "%20"));
				reqParams.append("&");
			}
			reqParams.deleteCharAt(reqParams.length() - 1);

			url.append("<a href=javascript:loadAttachment(\'/nbs/");
			url.append(strutsAction);
			url.append(reqParams.toString());
			if (jumperName != null) {
				url.append("#").append(jumperName);
			}
			url.append("\')>").append(displayNm).append("</a>");
		} catch (Exception e) {
			logger.error("Error while buildHyperLink in ContactTracingAction: "
					+ e.toString());
			throw e;
		}

		return url.toString();
	}

	public ActionForward editContactLoad(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String actionForward = "";
		String contactFormCd = PageManagementCommonActionUtil
				.checkIfPublishedPageExists(request,
						NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE);
		try {
			CTContactForm ctContactForm = (CTContactForm) form;
			// Use Legacy if no Contact Dynamic Page is published
			if (contactFormCd == null || contactFormCd.isEmpty()) {
				actionForward = "ContactEdit";
				CTContactLoadUtil.editLoadUtil(ctContactForm, request);
			} else { // dynamic page
				actionForward = "ContactEditPage";
				ctContactForm.setPageFormCd(contactFormCd);
				PageManagementCommonActionUtil.setTheRenderDirectory(request,
						contactFormCd,
						NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE);
				CTContactLoadUtil.editLoadPageUtil(ctContactForm, request);
			}
			String invPath = (String) NBSContext.retrieve(request.getSession(),
					NBSConstantUtil.DSInvestigationPath);
			if (invPath.equalsIgnoreCase("DSFilePath")) {
				String mprUid = ((Long) NBSContext.retrieve(
						request.getSession(),
						NBSConstantUtil.DSPatientPersonUID)).toString();
				request.setAttribute("mprUid", mprUid);
			} else {
				String strPhcLocalUID = (String) NBSContext.retrieve(
						request.getSession(),
						NBSConstantUtil.DSInvestigationLocalID);
				request.setAttribute("phcUID", strPhcLocalUID);
			}
			String contactRecordUID = (String) NBSContext.retrieve(
					request.getSession(), NBSConstantUtil.DSContactUID);
			if (contactRecordUID != null) {
				Long ctUid = Long.valueOf(contactRecordUID);
				Collection<Object> attachmentDTs = processRequest(ctUid,
						request.getSession());
				if (attachmentDTs != null && attachmentDTs.size() > 0)
					_updateAttachmentsForView(attachmentDTs);
				request.setAttribute("contactAttachments", attachmentDTs);

				// notes
				Collection<Object> notesDTs = ((CTContactForm) request
						.getSession().getAttribute("contactTracingForm"))
						.getcTContactClientVO().getOldCtContactProxyVO()
						.getcTContactVO().getNoteDTCollection();
				if (notesDTs != null && notesDTs.size() > 0)
					_updateNotesForView(notesDTs);
				request.setAttribute("contactNotes", notesDTs);
			}
			 try{
				String viewInvestigation = (String) NBSContext.retrieve(request.getSession(), "viewInves");
				request.setAttribute("viewInves", viewInvestigation);
			 }catch(NullPointerException e){
				 
			 }
			if(request.getSession().getAttribute("strContactInvestigationList") != null){
				request.setAttribute("strContactInvestigationList", request.getSession().getAttribute("strContactInvestigationList"));
			}
		} catch (Exception e) {
			logger.error("Error while loading editContactLoad: " + e.toString());
			throw new ServletException("Error while loading editContactLoad: "
					+ e.getMessage(), e);
		}
		return (mapping.findForward(actionForward));
	}

	public ActionForward editContactSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String actionForward = "";
		CTContactForm ctContactForm = (CTContactForm) form;
		try {

			actionForward = "ContactEditSubmit";
			if (ctContactForm.getPageFormCd() != null
					&& ctContactForm.getPageFormCd().equals(
							NEDSSConstants.CONTACT_FORMCODE)) {
				CTContactLoadUtil.editHandler(ctContactForm, request);
			} else {
				CTContactLoadUtil.editPageHandler(ctContactForm, request);
			}
			if (ctContactForm.getErrorList() != null
					&& ctContactForm.getErrorList().size() > 0)
				actionForward = "ContactEdit";

		} catch (NEDSSAppConcurrentDataException ncde) {
			logger.fatal(
					"ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
					ncde);
			return mapping.findForward("dataerror");
		} catch (Exception e) {
			logger.error("Error while loading editContactSubmit: "
					+ e.toString());
			throw new ServletException("Error while editContactSubmit: "
					+ e.getMessage(), e);
		}
		// set the success messages
		if (ctContactForm.getErrorList() == null
				|| ctContactForm.getErrorList().size() <= 0) {
			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(
							NBSPageConstants.UPDATE_SUCCESS_MESSAGE_KEY,
							NBSPageConstants.CONTACT_RECORD_TEXT));
			request.setAttribute("success_messages", messages);
		}		
		return (mapping.findForward(actionForward));
	}

	public ActionForward Cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String actionForward = "";
		try {
			actionForward = (String) NBSContext.retrieve(request.getSession(),
					NBSConstantUtil.DSInvestigationPath);
			CTContactForm ctContactForm = (CTContactForm) form;
			String formCd = NBSConstantUtil.CONTACT_REC;
			request.setAttribute("formCode", formCd);
			request.setAttribute("ContactTabtoFocus", "ContactTabtoFocus");
			String invPath = (String) NBSContext.retrieve(request.getSession(),
					NBSConstantUtil.DSInvestigationPath);
			if (invPath.equalsIgnoreCase("DSFilePath")) {
				actionForward = "ViewFile";
			} else {
				String strPhcLocalUID = (String) NBSContext.retrieve(
						request.getSession(),
						NBSConstantUtil.DSInvestigationLocalID);
				request.setAttribute("phcUID", strPhcLocalUID);
			}
			// request.setAttribute("ContextAction","InvestigationID");
			NBSContext.store(request.getSession(), NBSConstantUtil.DSFileTab,
					"2");
		} catch (Exception e) {
			logger.error("Error while loading Cancel: " + e.toString());
			throw new ServletException("Error while Cancel: " + e.getMessage(),
					e);
		}
		return (mapping.findForward(actionForward));
	}

	public ActionForward deleteSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String actionForward = "";
		try {
			CTContactForm contactForm = (CTContactForm) form;
			actionForward = (String) NBSContext.retrieve(request.getSession(),
					NBSConstantUtil.DSInvestigationPath);

			CTContactLoadUtil.deleteContact(mapping, contactForm, request,
					response);
			String invPath = (String) NBSContext.retrieve(request.getSession(),
					NBSConstantUtil.DSInvestigationPath);
			request.setAttribute("ContactTabtoFocus", "ContactTabtoFocus");
			if (invPath.equalsIgnoreCase("DSFilePath")) {
				actionForward = "ViewFile";
			}
		} catch (Exception e) {
			logger.error("Error while loading deleteSubmit: " + e.toString());
			throw new ServletException("Error while view deleteSubmit: "
					+ e.getMessage(), e);
		}
		return (mapping.findForward(actionForward));
	}

	public ActionForward viewAttachment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String actionForward = "";
		return (mapping.findForward(actionForward));
	}
}