package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.observation.common.AddCommentUtil;
import gov.cdc.nedss.webapp.nbs.action.observation.common.LabReportFieldMappingBuilder;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.util.HTMLEncoder;


public class LabViewCommonUtil {
	
	static final LogUtils logger = new LogUtils(LabViewCommonUtil.class.getName());

	
	public void viewGenericLoad(String formCd, ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		try {
			request.getSession().setAttribute(NEDSSConstants.SUBFORM_HASHMAP, null);

			LabViewCommonUtil LabViewCommonUtil = new LabViewCommonUtil();
			LabViewCommonUtil.viewGenericLabLoad(formCd, mapping, (PageForm)form, request,response);
			LabViewCommonUtil.viewGenericLoadUtil(NEDSSConstants.VIEW, (PageForm)form, request, response);
		} catch (Exception e) {
			logger.fatal("LabViewCommonUtil.viewGenericLoad: "+e.getMessage());
			throw new ServletException("LabViewCommonUtil.viewGenericLoad Exception thrown: "+e.getMessage());

		}
		
		
	}
	
	public static void setViewLinks(PersonVO patientVO, ObservationVO rootObervationVO, LabResultProxyVO labResultProxyVO, PageForm obsForm,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		logger.info("LabViewCommonUtil.setViewLinks is being called");
		try {
			obsForm.getAttributeMap().remove("linkName");
			obsForm.getAttributeMap().remove("linkValue");
			obsForm.getAttributeMap().remove("linkName1");
			obsForm.getAttributeMap().remove("linkValue1");

			String contextAction = null;
			contextAction = request.getParameter("ContextAction");
			String sCurrTask = null;
			TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
			if (contextAction == null)
				contextAction = (String) request.getAttribute("ContextAction");

			//setting PageContext to PS018 ViewObservationLab
			if (contextAction!=null && !contextAction.equalsIgnoreCase("DeleteDenied")){

				tm = NBSContext.getPageContext(session, "PS018", contextAction);
				sCurrTask = NBSContext.getCurrentTask(session);

				ErrorMessageHelper.setErrMsgToRequest(request, "PS018");
			}
			else if(contextAction!=null){//setting Page Contex for Delete Denied
				tm  = NBSContext.getPageContext(session, "PS032", contextAction);
				sCurrTask = NBSContext.getCurrentTask(session);
				
				obsForm.getAttributeMap().put("linkName","Return to View Observation");
				obsForm.getAttributeMap().put( "linkValue", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction=" +tm.get("ReturnToViewObservationLab")+  "\">"
						+ "Return to View Observation" + "</A>");
			}
			try{
				if(NBSContext.retrieve(session,"DSQueueObject")!=null){
					String queueType = ((DSQueueObject)NBSContext.retrieve(session,"DSQueueObject")).getDSQueueType();
					if(queueType!=null && queueType.equals(NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW))
					{
						obsForm.getAttributeMap().put("linkName1","Return to Documents Requiring Review");
						obsForm.getAttributeMap().put( "linkValue1", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction=" +tm.get("ReturnToObservationNeedingReview")+  "\">"
								+ "Return to Documents Requiring Review" + "</A>");
					}
					else if(queueType!=null && queueType.equals(NEDSSConstants.OBSERVATIONS_ASSIGNMENT))
					{
						/**Although this is not a checkFile permission, however changing the permission to anything else
						 *in the jsp file can cause much larger change from Investigation point of view 
						 */
						obsForm.getSecurityMap().put("checkFile", true);
						obsForm.getAttributeMap().put("linkName1","Return to Documents Requiring Security Assignment");
						obsForm.getAttributeMap().put( "linkValue1", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction=" +tm.get("ReturnToObservationsNeedingAssignment")+  "\">"
								+ "Return to Documents Requiring Security Assignment" + "</A>");
					}else if(queueType!=null && queueType.equals(NEDSSConstants.LAB_REPORT_EVENT_ID)){
                        String queueName=(String)request.getSession().getAttribute("custom_queue_in_session");
                        obsForm.getAttributeMap().put("linkName1","Return to Search Results");
						obsForm.getAttributeMap().put( "linkValue1", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&custom=true&returnresults=true&ContextAction="+tm.get("ReturnToLabResultsQueue")+"&queueName="+queueName+ "\">"
								+ "Return to Search Results" + "</A>");
					}
				}
			}
			catch(NullPointerException ex){
				//Let it go as if object is not in the context don't show the link
				logger.info("\n\n contextAction Nullpointer caught in  setViewLinks(This was also done in legacy code!!!:" + contextAction);
			}
			NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute( "NBSSecurityObject");
			logger.info("\n\n contextAction in  setViewLinks:" + contextAction);

			Object objObsUID = NBSContext.retrieve(session, "DSObservationUID");
			Long obsUID = null;
			objObsUID = NBSContext.retrieve(session, "DSObservationUID");
			if (objObsUID instanceof String) {
				obsUID = new Long((String) NBSContext.retrieve(session, "DSObservationUID"));
			}
			else if (objObsUID instanceof Long) {
				obsUID =  (Long)objObsUID;
			}
			logger.info("LabViewCommonUtil observationUID:" + obsUID);
			Long mprUID = null;
			mprUID = patientVO.getThePersonDT().getPersonParentUid();
			if (mprUID != null)
				NBSContext.store(session,"DSPatientPersonUID",mprUID);
			if ( sCurrTask!=null && (sCurrTask.endsWith("Lab2") || sCurrTask.endsWith("Lab3") || sCurrTask.endsWith("Lab10")) )
				NBSContext.store(session,"DSPatientPersonVO",patientVO); //needed by Assoc Inv

			/*retrieve the main ObservationDT related to the Lab by using a TreeMap<Object,Object> which will sort ascendingly
the DT's according to obervationUid ...the lowest observationUid is the correct one to use*/
			TreeMap<Object,Object> observationTree = new TreeMap<Object,Object>();
			ObservationDT obsDTTemp = new ObservationDT();
			List<ObservationVO> obsColl = (ArrayList<ObservationVO> ) labResultProxyVO.getTheObservationVOCollection();
			for (Iterator<ObservationVO> iter = obsColl.iterator(); iter.hasNext(); ) {
				ObservationVO obsVO = (ObservationVO) iter.next();
				obsDTTemp = obsVO.getTheObservationDT();
				//System.out.println("ObservationLocalID=" + obsDTTemp.getLocalId() + " ObservationUid = " + obsDTTemp.getObservationUid());
				observationTree.put(obsDTTemp.getObservationUid(), obsDTTemp);
			}

			Long obsUid = (Long) observationTree.firstKey();
			//System.out.println("**********This is the observationUid retrieved from the TreeMap<Object,Object> " + obsUid.toString() + "*************");
			ObservationDT obsDT = (ObservationDT)observationTree.get(obsUid);

			String progAreaCd = obsDT.getProgAreaCd();
			String jurisdictionCd = obsDT.getJurisdictionCd();
			String sharedInd = obsDT.getSharedInd();
			//String observationLocalUID = obsDT.getLocalId();
			//gst - get the program area/condition list for the Associate to Investigations disposition logic
			ArrayList<String> conditionList = labResultProxyVO.getTheConditionsList();
			if (conditionList != null) 
				NBSContext.store(session, "DSConditionList",conditionList);
			if ((progAreaCd != null) && !progAreaCd.isEmpty()) 
				NBSContext.store(session, "DSProgramArea",progAreaCd); 	
			CommonAction ca = new CommonAction();
			obsForm.getAttributeMap().put("PDLogic", ca.checkIfSyphilisIsInConditionList(progAreaCd, conditionList, NEDSSConstants.LAB_REPORT));
			obsForm.getAttributeMap().put("PDLogicCreateInv", ca.checkIfSyphilisIsInConditionListForCreateInv(progAreaCd, conditionList, NEDSSConstants.LAB_REPORT));
			boolean isProcessed = false;
			if (obsDT.getRecordStatusCd()!=null && (obsDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.OBS_PROCESSED))){
				isProcessed = true;
			}

			boolean isElecIndYes = false;
			boolean  isElecIndY = false;
			boolean isElecIndNo = false;
			//boolean viewPerm = false;
			boolean deletePerm = false;
			boolean deleteExPerm = false;
			boolean editPerm = false;
			boolean addInvestPerm = false;
			boolean viewWorkUpPerm = false;
			boolean transferPerm = false;
			boolean markAsReviewed = false;
			boolean clearMarkAsReviewed = false;

			UserProfile userprofile = secObj.getTheUserProfile();
			User user = userprofile.getTheUser();
			String userType = user.getUserType();

			boolean strReportExteranlUser = userType.equalsIgnoreCase(
					NEDSSConstants.SEC_USERTYPE_EXTERNAL);
			if (userType!=null && strReportExteranlUser){
				request.setAttribute("user",  NEDSSConstants.SEC_USERTYPE_EXTERNAL);
			} 
			else
				request.setAttribute("user", "internalUser");
			/**
			 * When you view it from ObsNeedingAssignment the progAreaCd and jurisdictionCd are null
			 */
			if(sCurrTask.startsWith("ViewObservationLab9"))
			{
				if(progAreaCd != null && jurisdictionCd == null)
				{
					/*viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
							NBSOperationLookup.VIEW, progAreaCd,
							"ANY", sharedInd);*/
					transferPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
							NBSOperationLookup.
							TRANSFERPERMISSIONS,
							progAreaCd,
							NBSOperationLookup.ANY);
				}

				else if (jurisdictionCd != null && progAreaCd == null)
				{
					/*viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW, "ANY",
							jurisdictionCd, sharedInd);
							*/
					transferPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.
							TRANSFERPERMISSIONS, NBSOperationLookup.ANY,
							jurisdictionCd);
				}
				else if(jurisdictionCd == null && progAreaCd == null)
				{
					/*viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
							NBSOperationLookup.VIEW, NBSOperationLookup.ANY,
							NBSOperationLookup.ANY, sharedInd);*/
					transferPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
							NBSOperationLookup.
							TRANSFERPERMISSIONS,
							NBSOperationLookup.ANY,
							NBSOperationLookup.ANY);
				}

			}
			else
			{
				/*viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
						NBSOperationLookup.VIEW,
						progAreaCd,
						jurisdictionCd, sharedInd);*/

				deletePerm = secObj.getPermission(NBSBOLookup.
						OBSERVATIONLABREPORT,
						NBSOperationLookup.DELETE,
						progAreaCd,
						jurisdictionCd, sharedInd);

				deleteExPerm = secObj.getPermission(NBSBOLookup.
						OBSERVATIONLABREPORT,
						NBSOperationLookup.DELETEEXTERNAL,
						progAreaCd,
						jurisdictionCd, sharedInd);

				editPerm = secObj.getPermission(NBSBOLookup.
						OBSERVATIONLABREPORT,
						NBSOperationLookup.EDIT,
						progAreaCd,
						jurisdictionCd, sharedInd);

				addInvestPerm = secObj.getPermission(NBSBOLookup.INVESTIGATION,
						NBSOperationLookup.ADD,
						progAreaCd,
						jurisdictionCd);

				viewWorkUpPerm = secObj.getPermission(NBSBOLookup.PATIENT,
						NBSOperationLookup.VIEWWORKUP,
						progAreaCd,
						NBSOperationLookup.ANY);

				obsForm.getSecurityMap().put("checkFile", String.valueOf(viewWorkUpPerm));

				markAsReviewed = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
						NBSOperationLookup.MARKREVIEWED,
						progAreaCd,
						jurisdictionCd, sharedInd);

				transferPerm = secObj.getPermission(NBSBOLookup.
						OBSERVATIONLABREPORT,
						NBSOperationLookup.
						TRANSFERPERMISSIONS,
						progAreaCd,
						jurisdictionCd);
				
				
				if (progAreaCd!=null && PropertyUtil.isStdOrHivProgramArea(progAreaCd)&& markAsReviewed
						&& !labResultProxyVO.isAssociatedInvInd()
						&& rootObervationVO.getTheObservationDT().getProcessingDecisionCd() != null
						&& !rootObervationVO.getTheObservationDT().getProcessingDecisionCd()
						.trim().equals(""))
					clearMarkAsReviewed = true;
			}

			
			if(obsDT.getElectronicInd() != null && obsDT.getElectronicInd().equalsIgnoreCase("N"))
			{
				isElecIndNo = true;
			}
			else if (obsDT.getElectronicInd() != null && obsDT.getElectronicInd().equalsIgnoreCase("E")){
				isElecIndYes = true;
				request.setAttribute("electronicInd", "Y");
				request.setAttribute("ELRelectronicInd", "E");
			}
			else if(obsDT.getElectronicInd() != null && obsDT.getElectronicInd().equalsIgnoreCase("Y"))
			{  isElecIndY = true;
			request.setAttribute("ELRelectronicInd", "Y");
			}




			//buttons that a ViewObservationLab may have based on NBSContext
			boolean hasMarkAsReviewed = false;
			boolean hasTransferOwnership = false;
			boolean hasDelete = false;
			boolean hasEdit = false;
			boolean hasCreateInvestigation = false;
			boolean hasAddComment = false;
			boolean hasClearMarkAsReviewed = false;
			boolean isFromManageEvents = false;

			//setting PageContext to PS018 ViewObservationLab
			/*  tm = NBSContext.getPageContext(session, "PS018", contextAction);
String sCurrTask = NBSContext.getCurrentTask(session);
sCurrTask = NBSContext.getCurrentTask(session);
ErrorMessageHelper.setErrMsgToRequest(request, "PS018"); */

			if (contextAction!=null && (contextAction.equalsIgnoreCase("ObservationLabIDOnEvents") ||
					contextAction.equalsIgnoreCase("ObservationLabIDOnSummary") ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.ObservationLabID) ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.MarkAsReviewed) ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.ClearMarkAsReviewed) ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.AddCommentLab) ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.SUBMIT) ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL) ||
					contextAction.equalsIgnoreCase("ReturnToViewObservationLab") ||
					contextAction.equalsIgnoreCase("ViewLab") ||
					contextAction.equalsIgnoreCase("DeleteDenied")||
					contextAction.equalsIgnoreCase("ViewObservationLab"))
					) {

				//System.out.println("****sCurrTak is: " + sCurrTask + " ***********");
				logger.info("sCurrTask in ViewLabLoad: " + sCurrTask);
				//ViewObservationLab 2 & ViewObservationLab3 from ViewFile Summary & Events tab
				if (sCurrTask.endsWith("Lab2") || sCurrTask.endsWith("Lab3")) {
					if (sCurrTask.endsWith("Lab2")) {
						obsForm.getAttributeMap().put("linkName","View File");
						obsForm.getAttributeMap().put( "linkValue", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction="+ tm.get("ReturnToFileSummary")  + "\">"
										+ "View File" + "</A>");
					}
					else if (sCurrTask.endsWith("Lab3")) {
						obsForm.getAttributeMap().put("linkName","Return to File: Events");
						obsForm.getAttributeMap().put( "linkValue", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction="+ tm.get("ReturnToFileEvents")  + "\">"
								+ "Return to File: Events" + "</A>");
						//obsForm.getAttributeMap().put( "linkValue", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction=" +tm.get("ReturnToFileEvents")+"</A>");
					}
					hasEdit = true;
					hasMarkAsReviewed = true;
					hasClearMarkAsReviewed = true;
					hasTransferOwnership = true;
					hasDelete = true;
					hasCreateInvestigation = true;
					hasAddComment = true;

				}
				//ViewObservationLab 1,5,7 accessed from ViewInvestigation
				else if (sCurrTask.endsWith("Lab1") || sCurrTask.endsWith("Lab5") ||
						sCurrTask.endsWith("Lab7") || sCurrTask.endsWith("Lab14") ||
						sCurrTask.endsWith("Lab15") || sCurrTask.endsWith("Lab16")) {
					obsForm.getAttributeMap().put("linkName","Return to View Investigation");
					obsForm.getAttributeMap().put( "linkValue", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction=" +tm.get("ReturnToViewInvestigation")+  "\">"
							+ "Return to View Investigation" + "</A>");
					hasEdit = true;
					hasTransferOwnership = true;
					hasAddComment = true;
					hasMarkAsReviewed = true;
					hasClearMarkAsReviewed = true;
				}
				//ViewObservationLab 4,6,8 accessed from ManageObservation
				else if (sCurrTask.endsWith("Lab4") || sCurrTask.endsWith("Lab6") ||
						sCurrTask.endsWith("Lab8") || sCurrTask.endsWith("Lab11")
						|| sCurrTask.endsWith("Lab12") || sCurrTask.endsWith("Lab13")) {
					obsForm.getAttributeMap().put("linkName","Return to Manage Associations");
					obsForm.getAttributeMap().put( "linkValue", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction=" +tm.get("ManageEvents")+  "\">"
							+ "Return to Manage Associations" + "</A>");

					isFromManageEvents =true;
					hasEdit = true;
					hasMarkAsReviewed = true;
					hasClearMarkAsReviewed = true;
					hasTransferOwnership = true;
					hasDelete = true;
					hasCreateInvestigation = true;
					hasAddComment = true;
				}
				//ViewObservationLab9
				else if (sCurrTask.endsWith("Lab9")) {
					hasTransferOwnership = true;
					hasAddComment = true;
				}
				//ViewObservationLab10
				else if (sCurrTask.endsWith("Lab10")) {
					hasEdit = true;
					hasDelete = true;
					hasCreateInvestigation = true;
					hasAddComment = true;
					hasMarkAsReviewed = true;
					hasClearMarkAsReviewed = true;
					hasTransferOwnership = true;

					obsForm.getAttributeMap().put("linkName","View File");
					obsForm.getAttributeMap().put( "linkValue", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction=" +tm.get("ReturnToFileSummary")+  "\">"
							+ "View File" + "</A>");

					if (contextAction.equals("ViewLab") || contextAction.equals(NBSConstantUtil.CANCEL)) {
						String viewEventsLink = "/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction=ViewEventsPopup";        
						obsForm.getAttributeMap().put("linkValueViewEvents",
								"<a href=\"javascript:function popupViewEvents(){window.open('"+viewEventsLink+"');};popupViewEvents();\" onclick=\"changeSubmitOnce(this);\">View Events</a>");
						obsForm.getAttributeMap().put("linkName","View File");
						obsForm.getAttributeMap().put( "linkValue", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction=" +tm.get("ReturnToFileSummary")+  "\">"
								+ "View File" + "</A>");

					}

				}
				else if (contextAction.equalsIgnoreCase("DeleteDenied")){
					TreeMap<Object,Object> deleteDeniedTm  = NBSContext.getPageContext(session, "PS032", contextAction);
					obsForm.getAttributeMap().put("linkName","Return to View Observation");
					//obsForm.getAttributeMap().put( "linkValue", "<A href=\"/nbs/" + sCurrTask + ".doContextAction="+deleteDeniedTm.get("ReturnToViewObservationLab")+"</A>");
					obsForm.getAttributeMap().put( "linkValue", "<A href=\"/nbs/" + sCurrTask + ".do?method=viewGenericSubmit&ContextAction=" +deleteDeniedTm.get("ReturnToViewObservationLab")+  "\">"
							+ "Return to View Observation" + "</A>");

				}
				/*else if (sCurrTask.endsWith("DeleteDenied1")||sCurrTask.endsWith("DeleteDenied2") || sCurrTask.endsWith("DeleteDenied3") || sCurrTask.endsWith("DeleteDenied4") || sCurrTask.endsWith("DeleteDenied5")){


}*/

				
				obsForm.getAttributeMap().put("editButtonHref",
						"/nbs/" + sCurrTask + ".do?ContextAction=" +
								tm.get(NBSConstantUtil.EDIT)+"&method=editGenericLoad");
				obsForm.getAttributeMap().put("markAsReviewButtonHref",
						"/nbs/" + sCurrTask + ".do?ContextAction=" +
								tm.get(NBSConstantUtil.MarkAsReviewed)+"&method=viewGenericSubmit");
				obsForm.getAttributeMap().put("clearMarkAsReviewButtonHref",
						"/nbs/" + sCurrTask + ".do?ContextAction=" +
								tm.get(NBSConstantUtil.ClearMarkAsReviewed)+"&method=viewGenericSubmit");
				obsForm.getAttributeMap().put("transferOwnership",
						"/nbs/" + sCurrTask + ".do?ContextAction=" +
								tm.get(NBSConstantUtil.TransferOwnership)+"&method=viewGenericSubmit");

				obsForm.getAttributeMap().put("deleteButtonHref",
						"/nbs/" + sCurrTask + ".do?ContextAction=" +
								tm.get(NBSConstantUtil.DELETE)+"&method=viewGenericSubmit");

				obsForm.getAttributeMap().put("creatInvestigationButtonHref",
						"/nbs/" + sCurrTask + ".do?ContextAction=" +
								tm.get(NBSConstantUtil.CreateInvestigation)+"&method=viewGenericSubmit");
				obsForm.getAttributeMap().put("associateToInvestigationsButtonHref",
						"/nbs/" + sCurrTask + ".do?ContextAction=" +
								tm.get(NBSConstantUtil.AssociateToInvestigations)+ "&method=viewGenericSubmit&initLoad=true");
				obsForm.getAttributeMap().put("addCommentButtonHref",
						"/nbs/" + sCurrTask + ".do?ContextAction=" +
								tm.get(NBSConstantUtil.AddCommentLab));

				//request.setAttribute("PrintPage", "/nbs/LoadViewObservationLab1.do?ContextAction=PrintPage");

				//setting button values


				//setting button values
				//Edit Button
				if (hasEdit && editPerm && isElecIndNo) {
					request.setAttribute("checkEdit", "true");
				}
				else{
					request.setAttribute("checkEdit", "false");
				}
				//MarkAsReviewed Button
				if (hasMarkAsReviewed && markAsReviewed && !isProcessed) {
					request.setAttribute("checkMarkAsReview", "true");
				}
				else{
					request.setAttribute("checkMarkAsReview", "false");
				}

				if (hasClearMarkAsReviewed && clearMarkAsReviewed) {
					request.setAttribute("checkClearMarkAsReview", "true");
				}
				else{
					request.setAttribute("checkClearMarkAsReview", "false");
				}

				//TransferOwnership Button
				if (hasTransferOwnership && transferPerm) {
					request.setAttribute("checkTransfer", "true");
				}
				else{
					request.setAttribute("checkTransfer", "false");
				}

				//Delete Button
				if (hasDelete &&
						( (deletePerm && isElecIndNo) || (deleteExPerm && isElecIndY) || (deleteExPerm && isElecIndYes))) {
					request.setAttribute("checkDelete", "true");
				}
				else{
					request.setAttribute("checkDelete", "false");
				}
				//Create Investigation Button
				if (hasCreateInvestigation && addInvestPerm && !isFromManageEvents) {
					request.setAttribute("checkCreateInvestigation", "true");
				}
				else{
					request.setAttribute("checkCreateInvestigation", "false");
				}
				//Add Comment button
				if (hasAddComment && isElecIndYes) {
					request.setAttribute("checkAddComment", "true");
				}
				else{
					request.setAttribute("checkAddComment", "false");
				}

			}
			//request.setAttribute("personLocalID", PersonUtil.getDisplayLocalID(personLocalId));
			//request.setAttribute("observationLocalUID", observationLocalUID);

			setAddUserComments(labResultProxyVO,request, secObj);

			/**sets the ProgramArea and Jurisdiction to request from security object**/
			getNBSSecurityJurisdictionsPA(request, secObj, contextAction);

			/**
			 * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
loadLabLDFView(labResultProxyVO ,lab112UID, request);
			 
			if(contextAction.equalsIgnoreCase("PrintPage")) {
				return new ActionForward("/observation/labreport/labreport_print");
			}
			*/

		}catch (Exception e) {
			logger.error("Exception in View Lab Report Load: " + e.getMessage());
			throw new ServletException("An error occurred in View Lab Report Load : "+e.getMessage());
		}      
		//return mapping.findForward("XSP");

	}
	public static void getNBSSecurityJurisdictionsPA(HttpServletRequest request, NBSSecurityObj nbsSecurityObj,
            String contextAction)
    {

        String programAreaJurisdictions = nbsSecurityObj.getProgramAreaJurisdictions(NBSBOLookup.OBSERVATIONLABREPORT,
                NBSOperationLookup.ADD);

        StringBuffer stringBuffer = new StringBuffer();
        if (programAreaJurisdictions != null && programAreaJurisdictions.length() > 0)
        { // "PA$J|PA$J|PA$J|"
          // change the navigation depending on programArea
            logger.info("programAreaJurisdictions: " + programAreaJurisdictions);
            StringTokenizer st = new StringTokenizer(programAreaJurisdictions, "|");
            while (st.hasMoreTokens())
            {
                String token = st.nextToken();
                if (token.lastIndexOf("$") >= 0)
                {
                    //String programArea = token.substring(0, token.lastIndexOf("$"));
                    String juris = token.substring(token.lastIndexOf("$") + 1);
                    stringBuffer.append(juris).append("|");
                }
            }
            request.setAttribute("NBSSecurityJurisdictions", stringBuffer.toString());
        }

        TreeMap<Object, Object> treeMap = nbsSecurityObj.getProgramAreas(NBSBOLookup.OBSERVATIONLABREPORT,
                NBSOperationLookup.ADD);
        // logger.debug("treeMap: " + treeMap);
        StringBuffer sb = new StringBuffer();
        if (treeMap != null)
        {
            Set<Object> s = new TreeSet<Object>(treeMap.values());
            if (contextAction.equalsIgnoreCase("AddLabDataEntry")
                    || contextAction.equalsIgnoreCase("SubmitAndLoadLabDE1"))
            {
                sb.append("NONE").append(NEDSSConstants.SRT_PART);
                sb.append("Unknown").append(NEDSSConstants.SRT_LINE);
            }
            Iterator<Object> it = s.iterator();
            while (it.hasNext())
            {
                String sortedValue = (String) it.next();
                Iterator<?> anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {
                    @SuppressWarnings("rawtypes")
					Map.Entry map = (Map.Entry) anIterator.next();
                    if ((String) map.getValue() == sortedValue)
                    {
                        String key = (String) map.getKey();
                        String value = (String) map.getValue();
                        sb.append(key.trim()).append(NEDSSConstants.SRT_PART);
                        sb.append(value.trim()).append(NEDSSConstants.SRT_LINE);
                        logger.info(key + " : " + value);

                    }
                }
            }
        }
        request.setAttribute("FilteredPrograAreas", sb.toString());

    } // getJurisdictionsPA

	private static void  setAddUserComments(LabResultProxyVO lrProxyVO, HttpServletRequest request, NBSSecurityObj nbsObj){
		AddCommentUtil acu = new AddCommentUtil();
		acu.setAddUserComments(lrProxyVO,request,nbsObj);
	}
///////////////////////////////////////View Submit////////////////////////////////
	public ActionForward viewGenericSubmit(ActionMapping mapping, ActionForm aForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

    HttpSession session = request.getSession();
 

    if (session == null) {
      logger.fatal("error no session");
      return mapping.findForward("login");
    }
    if (request.getParameter("mode") != null && request.getParameter("mode").equals("print")) {
		return printELRDocument(mapping, aForm, request, response); 
    }
		if (request.getParameter("documentUid") != null) {
			return viewELRDocument(mapping, aForm, request, response);
    }
		
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
    if (nbsSecurityObj == null) {
			logger.fatal("Error: No securityObj in the session, go back to login screen");
      return mapping.findForward("login");
    }

    String contextAction = "";
    try {

    	contextAction = request.getParameter("ContextAction");
    	
    	if (contextAction!=null && contextAction.equalsIgnoreCase(NBSConstantUtil.ViewElectronicDoc)) {
    		String mode = request.getParameter("mode");
    		if(mode!=null && mode.equals(NEDSSConstants.PRINT))
    				return printELRDocument(mapping, aForm, request, response);
			return viewELRDocument(mapping, aForm, request, response);
		}
    	
    	String sCurrTask = NBSContext.getCurrentTask(session);

    	Long mprUid = null;
    	Long obsUID = null;
    	if(request.getParameter("PatientPersonUID") != null){
    		mprUid =Long.valueOf(request.getParameter("PatientPersonUID"));
    	}else{

    		mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
    	}

    	if (mprUid != null) {
    		request.setAttribute("personUID", mprUid);
    	}

    	Object obsObj = NBSContext.retrieve(session, "DSObservationUID");

    	if (obsObj instanceof Long) {
    		obsUID = (Long) NBSContext.retrieve(session, "DSObservationUID");
    	} else if (obsObj instanceof String) {
    		obsUID = new Long((String) NBSContext.retrieve(session,"DSObservationUID"));
    	}

    	
    	LabResultProxyVO labResultProxyVO = (LabResultProxyVO)LabCommonUtil.getLabResultProxyVO(obsUID, session);
    	//List<ObservationVO> obsColl = (ArrayList<ObservationVO> ) labResultProxyVO.getTheObservationVOCollection();

    	//ObservationDT tempDT = new ObservationDT();
    	ObservationDT obsDT = new ObservationDT();
    	//ObservationVO obsVO = new ObservationVO();
    	/*Map<Object,Object> tMap = new TreeMap<Object,Object>();
    	for (Iterator<ObservationVO> iter = obsColl.iterator(); iter.hasNext(); ) {
    		obsVO = iter.next();
    		tempDT = obsVO.getTheObservationDT();
    		tMap.put(tempDT.getObservationUid(), tempDT);
    	}*/
		ObservationVO rootObservationVO = LabCommonUtil.getRootObservationVOByUid(obsUID.toString(), labResultProxyVO);

    	obsDT = rootObservationVO.getTheObservationDT();
    	String progAreaCd = obsDT.getProgAreaCd();
    	String jurisdictionCd = obsDT.getJurisdictionCd();
    	String sharedInd = obsDT.getSharedInd();
    	String observationLocalUid = obsDT.getLocalId();
    	
    	NBSContext.store(session, "DSObservationUID", obsUID);
    	NBSContext.store(session, "DSPatientPersonUID", mprUid);
    	PersonVO patientVO =LabCommonUtil.getPatientVO( (LabResultProxyVO)labResultProxyVO,   rootObservationVO);
    	String personLocalId = patientVO.getThePersonDT().getLocalId();
		
    	try {
    		
    		
    		if (contextAction.equalsIgnoreCase(NBSConstantUtil.EDIT)) {
    			logger.info("\n\n####The contextaction in ViewLabReportSubmit class is :" + contextAction);
    		} else if (contextAction.equalsIgnoreCase("ReturnToFileSummary") || contextAction.equalsIgnoreCase("ViewEventsPopup")) {
    			NBSContext.store(session, NBSConstantUtil.DSFileTab, "1");
    		} else if (contextAction.equalsIgnoreCase("ReturnToFileEvents")) {
    			NBSContext.store(session, NBSConstantUtil.DSFileTab, "3");
    		} else if (contextAction.equalsIgnoreCase("ManageEvents")) {
    			NBSContext.store(session, NBSConstantUtil.DSPatientPersonLocalID, personLocalId);
    			session.removeAttribute("DSPatientPersonLocalID");
    		} else if (contextAction.equalsIgnoreCase("CreateInvestigation")) {
    			NBSContext.store(session, "DSInvestigationJurisdiction", jurisdictionCd);
    			NBSContext.store(session, "DSObservationTypeCd", NEDSSConstants.LABRESULT_CODE);
    			// NBSContext.store(session, "DSInvestigationCondition",
    			// observationCd);
    			NBSContext.store(session, "DSInvestigationProgramArea", progAreaCd);
    			// for createInvestigation need to populate reporting source and
    			// Ordering Provider
    			String processingDecision = (String) request
    					.getParameter("markAsReviewReason");
    			LabReportFieldMappingBuilder mapBuilder = new LabReportFieldMappingBuilder();
    			TreeMap<Object,Object> loadTreeMap = mapBuilder.createLabReportLoadTreeMap(labResultProxyVO, obsUID, processingDecision);
    			NBSContext.store(session, "DSLabMap", loadTreeMap);
    		} else if (contextAction.equalsIgnoreCase("TransferOwnership")) {
    			if (jurisdictionCd != null) {
    				NBSContext.store(session, NBSConstantUtil.DSJurisdiction, jurisdictionCd);
    			}
    			if (progAreaCd != null) {
    				NBSContext.store(session,NBSConstantUtil.DSProgramArea, progAreaCd);
    			}
    			if (sharedInd != null) {
    				NBSContext.store(session, "DSObservationSharedInd", sharedInd);

    			}

    			NBSContext.store(session, "DSObservationLocalID", observationLocalUid);
    			NBSContext.store(session, "DSPatientPersonUID", mprUid);
    		}
    		// ContextAction = Delete
    		else if (contextAction.equalsIgnoreCase(NBSConstantUtil.DELETE)) {
    			if(personLocalId!=null)
    				NBSContext.store(session, NBSConstantUtil.DSPatientPersonLocalID, personLocalId);

    			// ##!!VOTester.createReport(form.getProxy(),
    			// "obs-delete-store-pre");
    			logger.debug("observationUID in Delete is :" + obsUID);
    			String result = deleteHandler(obsUID, nbsSecurityObj, session, request, response);
    			// ##!!VOTester.createReport(form.getProxy(),
    			// "obs-delete-store-post");
    			if (result.equals("viewDelete") && sCurrTask.endsWith("Lab10")) {
    				logger.debug("ObservationSubmit: viewDelete");

    				ActionForward af = mapping.findForward(contextAction);
    				ActionForward forwardNew = new ActionForward();
    				StringBuffer strURL = new StringBuffer(af.getPath());
    				strURL.append("?ContextAction=" + contextAction);
    				strURL.append("&method=loadQueue");
    				forwardNew.setPath(strURL.toString());
    				forwardNew.setRedirect(true);
    				return forwardNew;
    			} else if (result.equals("viewDelete")
    					&& !sCurrTask.endsWith("Lab10")) {
    				logger.debug("ObservationSubmit: viewDelete");

    				ActionForward af = mapping.findForward(contextAction);
    				ActionForward forwardNew = new ActionForward();
    				StringBuffer strURL = new StringBuffer(af.getPath());
    				if(strURL.indexOf(NEDSSConstants.ManageEvents)>0 || strURL.indexOf("?")>0){
    					strURL.append("&ContextAction=" + contextAction);
    				} else
    					strURL.append("?ContextAction=" + contextAction);
    				forwardNew.setPath(strURL.toString());
    				forwardNew.setRedirect(true);
    				return forwardNew;

    			} else if (result.equals("deleteDenied")) {
    				logger.debug("ObservationSubmit: deleteDenied");
    				NBSContext.store(session, "DSObservationUID", obsUID);
    				NBSContext.store(session, "DSPatientPersonUID", mprUid);

    				ActionForward af = mapping.findForward("DeleteDenied");
    				ActionForward forwardNew = new ActionForward();
    				StringBuffer strURL = new StringBuffer(af.getPath());
    				strURL.append("?method=viewGenericLoad&ContextAction=DeleteDenied");
    				forwardNew.setPath(strURL.toString());
    				forwardNew.setRedirect(true);
    				return forwardNew;
    			}
    		} else if (contextAction
    				.equalsIgnoreCase(NBSConstantUtil.MarkAsReviewed)) {

    			String processingDecision = (String) request
    					.getParameter("markAsReviewReason");

    			String result = markAsReviewedHandler(obsUID,
    					processingDecision, nbsSecurityObj, session, request,
    					response);
    			if (result.equals(NEDSSConstants.RECORD_STATUS_PROCESSED)) {
    				ActionMessages messages = new ActionMessages();
    				messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
    						new ActionMessage(NBSPageConstants.MARK_REVIEWED_SUCCESS, NBSPageConstants.LAB_REPORT_TEXT));
    				request.setAttribute("success_messages", messages);
    				
    			} else {
    				logger.info("The Lab Report was not able to be set to Processed");
    			}
    		} else if (contextAction
    				.equalsIgnoreCase(NBSConstantUtil.ClearMarkAsReviewed)) {
    			String result = clearMarkAsReviewedHandler(obsUID,
    					nbsSecurityObj, session, request, response);
    			if (result.equals(NEDSSConstants.RECORD_STATUS_UNPROCESSED)) {
    				ActionMessages messages = new ActionMessages();
    				messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
    						new ActionMessage(NBSPageConstants.UN_MARK_REVIEWED_SUCCESS, NBSPageConstants.LAB_REPORT_TEXT));
    				request.setAttribute("success_messages", messages);
    			} else {
    				logger.info("The Lab Report was not able to be set to UnProcessed");
    			}

    		}
    	}

    	catch (Exception ncde) {
    		logger.fatal("Data Concurrency Error being raised ", ncde);
    		return mapping.findForward("dataerror");
    	} finally {
    		session.setAttribute("DSPatientPersonLocalID", null);
    		session.removeAttribute("DSPatientPersonLocalID");
    	}
    }catch (Exception e) {
    	logger.error("General exception in View Lab Report Submit: " + e.getMessage());
    	e.printStackTrace();
    	throw new ServletException("An error occurred in View Lab Report Submit: "+e.getMessage());
    }  
    return mapping.findForward(contextAction);
  }

  private String markAsReviewedHandler(Long observationUid, String processingDecision,
			NBSSecurityObj nbsSecurityObj, HttpSession session,
			HttpServletRequest request, HttpServletResponse response)
			throws NEDSSAppConcurrentDataException, java.rmi.RemoteException,
      javax.ejb.EJBException, Exception {

    String markAsReviewedFlag = "";
    /**
     * Call the mainsessioncommand
     */
    MainSessionCommand msCommand = null;
    //ObservationUtil obsUtil = new ObservationUtil();
    
    String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
    String sMethod="processObservation";
    Object[] oParams = new Object[]{observationUid};
    if(processingDecision!=null && !processingDecision.trim().equals("")){
     sMethod = "processObservationWithProcessingDecision";
     //Map<String, Object> observationMap = obsUtil.createProcessingDecisionObservation(observationUid,"Lab",processingDecision,request);
     oParams = new Object[]{
             observationUid, processingDecision, null};
    }
		logger.debug("ObservationSubmit: markAsReviewedHandler with observationID = " + observationUid);

    /**
     * Output ObservationProxyVO for debugging
     */
    MainSessionHolder holder = new MainSessionHolder();
    msCommand = holder.getMainSessionCommand(session);
    List<?> resultUIDArr = new ArrayList<Object> ();
		resultUIDArr = msCommand
				.processRequest(sBeanJndiName, sMethod, oParams);

    boolean result = false;
    if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			logger.debug("Marked as Reviewed result and arg = "
					+ resultUIDArr.get(0));
      result = ( (Boolean) resultUIDArr.get(0)).booleanValue();
      logger.debug("The Marked AS Reviewed result is:" + result);
      if (result) {
        markAsReviewedFlag = "PROCESSED";
			} else {
        markAsReviewedFlag = "UNPROCESSED";
      }
    }
    return markAsReviewedFlag;
  }
  
  private String clearMarkAsReviewedHandler(Long observationUid, 
			NBSSecurityObj nbsSecurityObj, HttpSession session,
			HttpServletRequest request, HttpServletResponse response)
			throws NEDSSAppConcurrentDataException, java.rmi.RemoteException,
	    javax.ejb.EJBException, Exception {
	
	  String markAsReviewedFlag = "";
	  /**
	   * Call the mainsessioncommand
	   */
	  MainSessionCommand msCommand = null;
	  //ObservationUtil obsUtil = new ObservationUtil();
	  
	  String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
	  String sMethod="unProcessObservation";
	  Object[] oParams = new Object[]{observationUid};
	  logger.debug("ObservationSubmit: clearMarkAsReviewedHandler with observationID = " + observationUid);
	
	  /**
	   * Output ObservationProxyVO for debugging
	   */
	  MainSessionHolder holder = new MainSessionHolder();
	  msCommand = holder.getMainSessionCommand(session);
	  List<?> resultUIDArr = new ArrayList<Object> ();
			resultUIDArr = msCommand
					.processRequest(sBeanJndiName, sMethod, oParams);
	
	  boolean result = false;
	  if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				logger.debug("Clear Marked as Reviewed result and arg = "
						+ resultUIDArr.get(0));
	    result = ( (Boolean) resultUIDArr.get(0)).booleanValue();
	    logger.debug("The Clear Marked AS Reviewed result is:" + result);
	    if (result) {
	      markAsReviewedFlag = NEDSSConstants.RECORD_STATUS_UNPROCESSED;
				} else {
	      markAsReviewedFlag = NEDSSConstants.RECORD_STATUS_PROCESSED;
	    }
	  }
	  return markAsReviewedFlag;
  }


  private String deleteHandler(Long UID, NBSSecurityObj nbsSecurityObj,
                               HttpSession session, HttpServletRequest request,
			HttpServletResponse response)
			throws NEDSSAppConcurrentDataException, java.rmi.RemoteException,
      javax.ejb.EJBException, Exception {
    /**
     * Call the mainsessioncommand
     */
    MainSessionCommand msCommand = null;
    String deleteFlag = "";
    String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
    String sMethod = "deleteLabResultProxy";


    /**
     * Output ObservationProxyVO for debugging
		 */
		Object[] oParams = { UID };
    MainSessionHolder holder = new MainSessionHolder();
    msCommand = holder.getMainSessionCommand(session);
    ArrayList<?> resultUIDArr = new ArrayList<Object> ();
    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
    boolean result;
    if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
			logger.debug("delete observation worked!!! and arg = " + resultUIDArr.get(0));
      result = ( (Boolean) resultUIDArr.get(0)).booleanValue();
      logger.debug("\n\n\n\n The result value is:" + result);
      if (result) {
        deleteFlag = "viewDelete";
			} else {
        deleteFlag = "deleteDenied";
      }
		} else {
      deleteFlag = "error";

    }
    return deleteFlag;
  }
  
	public ActionForward viewELRDocument(ActionMapping mapping,
			ActionForm aForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
		String sMethod = "getXMLDocument";

		Long documentUid = Long.valueOf((String) request.getParameter("documentUid"));
		Object[] oParams = { documentUid };
		try {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object>();
			String receiveDate = (String)request.getParameter("dateReceivedHidden");
			request.setAttribute("dateReceivedHidden", HTMLEncoder.encodeHtml(receiveDate));
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			request.setAttribute("documentViewHTML", getDocumentViewHTML((EDXDocumentDT) resultUIDArr.get(0), "view"));
			request.setAttribute("PageTitle", "View ELR Document");
			request.setAttribute("documentUid",String.valueOf(documentUid));
			//request.setAttribute("mode","print");
		} catch (Exception ex) {
			logger.fatal("Error while retreiving ELR Document " + ex.getMessage());
			throw new ServletException(ex);
		}

		return mapping.findForward("viewELRDoc");
	}
	
	public ActionForward printELRDocument(ActionMapping mapping,
			ActionForm aForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		MainSessionCommand msCommand = null;
		String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
		String sMethod = "getXMLDocument";

		Long documentUid = Long.valueOf((String) request.getParameter("documentUid"));
		Object[] oParams = { documentUid };
		try {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object>();
			String receiveDate = String.valueOf(request.getParameter("dateReceivedHidden"));
			request.setAttribute("dateReceivedHidden", receiveDate);
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			request.setAttribute("documentViewHTML", getDocumentViewHTML((EDXDocumentDT) resultUIDArr.get(0), "print"));
			request.setAttribute("PageTitle", "View ELR Document");
			request.setAttribute("documentUid",documentUid);
			request.setAttribute("mode","print");
			request.setAttribute("dateReceivedHidden",receiveDate);
					
		} catch (Exception ex) {
			logger.fatal("Error while retreiving ELR Document "
					+ ex.getMessage());
			throw new ServletException(ex);
		}

		return mapping.findForward("viewELRDoc");
	}

	  public ActionForward viewGenericLabLoad(String formCd, ActionMapping mapping, ActionForm form, HttpServletRequest request,
	            HttpServletResponse response) throws IOException, ServletException, NEDSSAppException{
		  String actionForward = "";
		  try {
			String contextAction = request.getParameter("ContextAction");
			  PageForm pageForm = (PageForm) form;
			    ((PageForm)pageForm).setMode(HTMLEncoder.encodeHtml(request.getParameter("mode")));

			String currTask = NBSContext.getCurrentTask(request.getSession());
			
			if (contextAction!=null && (contextAction.equalsIgnoreCase("ObservationLabIDOnEvents") ||
					contextAction.equalsIgnoreCase("ObservationLabIDOnSummary") ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.ObservationLabID) ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.MarkAsReviewed) ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.ClearMarkAsReviewed) ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.AddCommentLab) ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.SUBMIT) ||
					contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL) ||
					contextAction.equalsIgnoreCase("ReturnToViewObservationLab") ||
					contextAction.equalsIgnoreCase("ViewLab") ||
					//contextAction.equalsIgnoreCase("DeleteDenied")||
					contextAction.equalsIgnoreCase("ViewObservationLab"))
					) {
					TreeMap<Object,Object> tm = NBSContext.getPageContext(request.getSession(), "PS018", contextAction);
				
						if (currTask.endsWith("Lab2") || currTask.endsWith("Lab3")) {
			    			if (currTask.endsWith("Lab2")) {
			    				request.setAttribute("linkName", "View File");
			    				request.setAttribute("linkValue",
			    						"<A href=\"/nbs/" + currTask + ".do?method=viewGenericSubmit&ContextAction=" +
			    								tm.get("ReturnToFileSummary")+"</A>");
			    			}
			    			else if (currTask.endsWith("Lab3")) {
			    				request.setAttribute("linkName", "Return to File: Events");
			    				request.setAttribute("linkValue",
			    						"<A href=\"/nbs/" + currTask + ".do?method=viewGenericSubmit&ContextAction=" +
			    								tm.get("ReturnToFileEvents"+"</A>"));
			    			}
						
						}
			}
						
						//actionForward = "viewDynamicPage";
			    		//busObjType=NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE;
			    		//pageForm.setBusinessObjectType(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE);
			    		//pageForm.setGenericType(NEDSSConstants.GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE);
			
			            request.setAttribute("mode", request.getParameter("mode"));

			           //////// PageLoadUtil.viewGenericLoadUtil(pageForm, request);
			            // Set the Directory where the JSP for the form is
			            PageManagementCommonActionUtil.setTheRenderDirectory(request, formCd, pageForm.getBusinessObjectType());
			            request.setAttribute("formCode", formCd);

			return (mapping.findForward(actionForward));
		} catch (Exception e) {
			logger.error("LabViewCommonUtil.viewGenericLabLoad Exception thrown" +  e); 
			logger.error("LabViewCommonUtil.viewGenericLabLoad Exception " +  e.getMessage()); 
			throw new NEDSSAppException(e.getMessage(), e);
		}
	  }
	  
		public PageProxyVO viewGenericLoadUtil(String actionMode, PageForm form,
				HttpServletRequest request,HttpServletResponse response) throws Exception {
			try {
				boolean electronicIndicator =false;
				HttpSession session = request.getSession();
				form.getAttributeMap().clear();
				form.setBatchEntryMap(new HashMap<Object, ArrayList<BatchEntry>>());
				// Init form
				form.setFormFieldMap(new HashMap<Object, Object>());
				form.setErrorTabs(new String[0]);
				// Get the published pageFormCd for the current condition and passed
				// bus obj type
				String businessObjectType = form.getBusinessObjectType();
				String formCd = form.getPageFormCd();
				logger.debug("LabViewCommonUtil--> Begin loading View Page for "
						+ businessObjectType + " Form Cd=: " + formCd);
				// Get the metadata
				PageLoadUtil pageLoadUtil = new PageLoadUtil();
				pageLoadUtil.loadQuestions(formCd);
				pageLoadUtil.loadQuestionKeys(formCd);
				Map<Object, Object> questionMap=pageLoadUtil.getQuestionMap();
				//PageLoadUtil.loadQuestionKeys(formCd);
				//Map<Object, Object> questionKeyMap =PageLoadUtil.questionKeyMap;
				String contextAction = request.getParameter("ContextAction");
				// Not sure if we need associated PHC..
				// String sPublicHealthCaseUID =
				// (String)NBSContext.retrieve(session,
				// NBSConstantUtil.DSInvestigationUid);
				// request.setAttribute("DSInvUid", sPublicHealthCaseUID);
				// The act uid (i.e Interview) must be passed in..
				Long actUID =  null;
				String actUidStr = request.getParameter("observationUID");  
				if(actUidStr !=null)
					actUID = new Long(actUidStr);
				if(contextAction!=null && contextAction.equalsIgnoreCase(NEDSSConstants.DELETE_DENIED_PAGE)){
					NBSContext.getPageContext(session, "PS032", contextAction);
		    		NBSContext.getCurrentTask(session);
				}
				if(actUID==null)//In case of Cancel button, the ActUid is not in parameter, but in context
					actUID = new Long(NBSContext.retrieve(session, "DSObservationUID").toString());
				// if parameter not there, could be coming from Edit or Create to
				// View
				
				request.setAttribute("actUid", actUID);
				PageActProxyVO proxyVO =null;
				
					proxyVO = LabCommonUtil.getLabResultProxyVO(actUID, session);
				
				
				ObservationVO rootObservationVO = LabCommonUtil.getRootObservationVOByUid(actUidStr, (LabResultProxyVO) proxyVO);
				
				String receiveDate = StringUtils.formatDate(rootObservationVO.getTheObservationDT().getAddTime());
				
				PersonVO patientVO =LabCommonUtil.getPatientVO( (LabResultProxyVO)proxyVO,   rootObservationVO);
				
				PageClientVO clientVO = new PageClientVO();
				
				ClientUtil.setPatientInformation(form.getActionMode(), patientVO, clientVO, request, form.getPageFormCd());
				if(contextAction !=null)
					LabViewCommonUtil.setViewLinks(patientVO, rootObservationVO, (LabResultProxyVO)proxyVO, form, request, response);
				LabCommonUtil.setObservationInformationOnForm(actUidStr, clientVO,  form,proxyVO, request);
				
				Map<String, Map<Long, Object>> consolidatedMap  =  LabPageUtil.findResultedTestsObservation((LabResultProxyVO) proxyVO,  rootObservationVO);
				ResultedTestUtil resultedTestUtil = new ResultedTestUtil(null, null, rootObservationVO, (LabResultProxyVO) proxyVO, consolidatedMap);
				if(rootObservationVO.getTheObservationDT().getElectronicInd()==null || rootObservationVO.getTheObservationDT().getElectronicInd().equals("Y") ) {
					electronicIndicator =true;	
				}
				resultedTestUtil.setResultedTestObservationForUI(form, request);
				Map<Long, Object> observationPatientStatusVOMap  =((Map<Long, Object>)consolidatedMap.get(PageConstants.PATIENT_STATUS_AT_SPECIMEN_COLLECTION));
				Map<Long, Object> observationLabResultVOMap  =((Map<Long, Object>)consolidatedMap.get(PageConstants.LAB_RESULT_COMMENT));
				
				LabCommonUtil.setPatientStatusInformationOnForm(observationPatientStatusVOMap, clientVO);
				form.setPageClientVO(clientVO);
				
				/**
				 * NOTE: proxyVO.setTheParticipationDTCollection:  utilize the generic format of Page builder pages where the partiipation is at provy level
				 */
				proxyVO.setTheParticipationDTCollection(rootObservationVO.getTheParticipationDTCollection());
				
				
				// Look for any participations in the question map and load them to
				// the attribute map
				pageLoadUtil.loadGenericEntities((BaseForm) form, proxyVO, questionMap, request);
				LabCommonUtil.setRolesToParticipantsForELR(form, (LabResultProxyVO)proxyVO, patientVO.getThePersonDT().getPersonUid(), request);
				@SuppressWarnings("unchecked")
				Map<Object, Object> batchMap = pageLoadUtil.findBatchRecords(formCd, session);
				if(electronicIndicator ) {
					populateElrCommentSummary(form, batchMap, observationLabResultVOMap);
				}else {
					LabCommonUtil.setLabCommentInformationOnForm(observationLabResultVOMap, clientVO);
				}
				
				
				request.setAttribute("SubSecStructureMap", form.getSubSecStructureMap());
				request.setAttribute("SubSecStructureMap", batchMap);
				

				// Page Specific Answers from associated answer table
				pageLoadUtil.setMSelectCBoxAnswersForViewEdit((BaseForm) form,
						pageLoadUtil.updateMapWithQIds(proxyVO.getPageVO().getAnswerDTMap()),
						(ClientVO) form.getPageClientVO());
				// Get repeating subsection data
				pageLoadUtil.fireRulesOnViewLoad(form);
				pageLoadUtil.populateBatchRecords(form, formCd, session, proxyVO.getPageVO()
						.getPageRepeatingAnswerDTMap());
				ClientUtil.setPersonIdDetails(patientVO, form);

				// save PageProxyVO to ClientVO
				form.getPageClientVO().setOldPageProxyVO(proxyVO);
				
				// Load answers for Business Object Type and put it in answerMap for
				// UI & Rules to work
				PageManagementCommonActionUtil.setCommonAnswersForGenericViewEdit(
						form, proxyVO, request);
				// set permissions for Edit/Delete buttons
				if(contextAction !=null)
					GenerateLinks(rootObservationVO, patientVO, (LabResultProxyVO)proxyVO, (PageForm)form, request) ;
				
				populatePatientSummary(form, patientVO, rootObservationVO, form.getBusinessObjectType(), request);
				PageLoadUtil.prepareDisplayOrigDocList(((LabResultProxyVO)proxyVO).geteDXDocumentCollection(),receiveDate, request);
				return (PageProxyVO)proxyVO;
			} catch (Exception e) {
				logger.error("LabViewCommonUtil.viewGenericLoadUtil Exception thrown" +  e); 
				logger.error("LabViewCommonUtil.viewGenericLoadUtil Exception " +  e.getMessage()); 
				throw new NEDSSAppException(e.getMessage(), e);
			}
		}
		
		public static void GenerateLinks(ObservationVO rootObservationVO, PersonVO patientVO, LabResultProxyVO labResultProxyVO, PageForm obsForm, HttpServletRequest request) throws IOException,
		ServletException {
			HttpSession session = request.getSession();
			logger.info("ViewLabReportLoad is being called");

			try {
				String contextAction = null;
				contextAction = request.getParameter("ContextAction");
				String sCurrTask = null;
				TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
				if (contextAction == null)
					contextAction = (String) request.getAttribute("ContextAction");

				//setting PageContext to PS018 ViewObservationLab
				if (!contextAction.equalsIgnoreCase("DeleteDenied")){

					tm = NBSContext.getPageContext(session, "PS018", contextAction);
					sCurrTask = NBSContext.getCurrentTask(session);

					ErrorMessageHelper.setErrMsgToRequest(request, "PS018");
				}
				else{//setting Page Contex for Delete Denied
					tm  = NBSContext.getPageContext(session, "PS032", contextAction);
					sCurrTask = NBSContext.getCurrentTask(session);
					request.setAttribute("linkName", "Return to View Observation");
					request.setAttribute("linkValue",
							"/nbs/" + sCurrTask + ".do?method=viewGenericLoad&ContextAction=" +
									tm.get("ReturnToViewObservationLab"));
				}
				try{
					if(NBSContext.retrieve(session,"DSQueueObject")!=null){
						String queueType = ((DSQueueObject)NBSContext.retrieve(session,"DSQueueObject")).getDSQueueType();
						if(queueType!=null && queueType.equals(NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW))
						{
							request.setAttribute("linkName1", "Return to Documents Requiring Review");
							request.setAttribute("linkValue1",
									"/nbs/" + sCurrTask + ".do?ContextAction=" +
											tm.get("ReturnToObservationNeedingReview"));
						}
						else if(queueType!=null && queueType.equals(NEDSSConstants.OBSERVATIONS_ASSIGNMENT))
						{
							request.setAttribute("linkName1", "Return to Documents Requiring Security Assignment");
							request.setAttribute("linkValue1",
									"/nbs/" + sCurrTask + ".do?ContextAction=" +
											tm.get("ReturnToObservationsNeedingAssignment"));
						}
					}
				}
				catch(NullPointerException ex){
					//Let it go as if object is not in the context don't show the link
				}


				NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute( "NBSSecurityObject");
				logger.info("\n\n contextAction in  ViewLabReportLoad:" + contextAction);


				Object objObsUID = NBSContext.retrieve(session,
						"DSObservationUID");
				Long obsUID = null;

				objObsUID = NBSContext.retrieve(session,
						"DSObservationUID");
				if (objObsUID instanceof String) {
					obsUID = new Long((String) NBSContext.retrieve(session, "DSObservationUID"));
				}
				else if (objObsUID instanceof Long) {
					obsUID =  (Long)objObsUID;
				}



				logger.info("ViewLabReportLoad observationUID:" + obsUID);



				//lab report might be assoc. to investigation that might have an exiting notification on it...must do check
				/**@TODO PKS remove avter notification exists logic completes
			if (labResultProxyVO.getAssociatedNotificationInd()) {
			request.setAttribute("NotificationExists", "true");
			}
			else {
			request.setAttribute("NotificationExists", "false");

			}

			request.setAttribute("labResultProxyVO",labResultProxyVO);
			//retrieve the patient's localId
				 */

				Long mprUID = patientVO.getThePersonDT().getPersonParentUid();
				if (mprUID != null)
					NBSContext.store(session,"DSPatientPersonUID",mprUID);
				if ( (sCurrTask.endsWith("Lab2")) || (sCurrTask.endsWith("Lab3")) || (sCurrTask.endsWith("Lab10")) )
					NBSContext.store(session,"DSPatientPersonVO",patientVO); //needed by Assoc Inv

				/*retrieve the main ObservationDT related to the Lab by using a TreeMap<Object,Object> which will sort ascendingly
			the DT's according to obervationUid ...the lowest observationUid is the correct one to use*/
				/**@TODO PKS Existing method was clunky as was not based on clear logic. Remove after view Observation workflow completes
			TreeMap<Object,Object> observationTree = new TreeMap<Object,Object>();
			ObservationDT obsDTTemp = new ObservationDT();
			List<ObservationVO> obsColl = (ArrayList<ObservationVO> ) labResultProxyVO.getTheObservationVOCollection();
			for (Iterator<ObservationVO> iter = obsColl.iterator(); iter.hasNext(); ) {
			ObservationVO obsVO = (ObservationVO) iter.next();
			obsDTTemp = obsVO.getTheObservationDT();
			//System.out.println("ObservationLocalID=" + obsDTTemp.getLocalId() + " ObservationUid = " + obsDTTemp.getObservationUid());
			observationTree.put(obsDTTemp.getObservationUid(), obsDTTemp);
			}

			Long obsUid = (Long) observationTree.firstKey();
			//System.out.println("**********This is the observationUid retrieved from the TreeMap<Object,Object> " + obsUid.toString() + "*************");
			ObservationDT obsDT = (ObservationDT)observationTree.get(obsUid);
				 */
				ObservationDT rootObsDT = rootObservationVO.getTheObservationDT();
				String progAreaCd = rootObsDT.getProgAreaCd();
				String jurisdictionCd = rootObsDT.getJurisdictionCd();
				String sharedInd = rootObsDT.getSharedInd();
				String observationLocalUID = rootObsDT.getLocalId();
				//gst - get the program area/condition list for the Associate to Investigations disposition logic
				ArrayList<String> conditionList = labResultProxyVO.getTheConditionsList();
				if (conditionList != null) 
					NBSContext.store(session, "DSConditionList",conditionList);
				if ((progAreaCd != null) && !progAreaCd.isEmpty()) 
					NBSContext.store(session, "DSProgramArea",progAreaCd); 	
				CommonAction ca = new CommonAction();
				obsForm.getAttributeMap().put("PDLogic", ca.checkIfSyphilisIsInConditionList(progAreaCd, conditionList, NEDSSConstants.LAB_REPORT));
				obsForm.getAttributeMap().put("PDLogicCreateInv", ca.checkIfSyphilisIsInConditionListForCreateInv(progAreaCd, conditionList, NEDSSConstants.LAB_REPORT));
				boolean isProcessed = false;

				if (rootObsDT.getRecordStatusCd()!=null && (rootObsDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.OBS_PROCESSED))){
					isProcessed = true;
				}



				boolean isElecIndYes = false;
				boolean  isElecIndY = false;
				boolean isElecIndNo = false;
				boolean viewPerm = false;
				boolean deletePerm = false;
				boolean deleteExPerm = false;
				boolean editPerm = false;
				boolean addInvestPerm = false;
				//boolean viewWorkUpPerm = false;
				boolean transferPerm = false;
				boolean markAsReviewed = false;
				boolean clearMarkAsReviewed = false;


				UserProfile userprofile = secObj.getTheUserProfile();
				User user = userprofile.getTheUser();
				String userType = user.getUserType();

				boolean strReportExteranlUser = userType.equalsIgnoreCase(
						NEDSSConstants.SEC_USERTYPE_EXTERNAL);
				if (userType!=null && strReportExteranlUser){
					request.setAttribute("user",  NEDSSConstants.SEC_USERTYPE_EXTERNAL);
				}
				else
					request.setAttribute("user", "internalUser");
				/**
				 * When you view it from ObsNeedingAssignment the progAreaCd and jurisdictionCd are null
				 */
				if(sCurrTask.startsWith("ViewObservationLab9"))
				{
					if(progAreaCd != null && jurisdictionCd == null)
					{
						viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW, progAreaCd,
								"ANY", sharedInd);
						transferPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.
								TRANSFERPERMISSIONS,
								progAreaCd,
								NBSOperationLookup.ANY);
					}

					else if (jurisdictionCd != null && progAreaCd == null)
					{
						viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW, "ANY",
								jurisdictionCd, sharedInd);
						transferPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.
								TRANSFERPERMISSIONS,
								NBSOperationLookup.ANY,
								jurisdictionCd);

					}
					else if(jurisdictionCd == null && progAreaCd == null)
					{
						viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
								NBSOperationLookup.VIEW, NBSOperationLookup.ANY,
								NBSOperationLookup.ANY, sharedInd);
						transferPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
								NBSOperationLookup.
								TRANSFERPERMISSIONS,
								NBSOperationLookup.ANY,
								NBSOperationLookup.ANY);


					}

				}
				else
				{
					viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
							NBSOperationLookup.VIEW,
							progAreaCd,
							jurisdictionCd, sharedInd);

					deletePerm = secObj.getPermission(NBSBOLookup.
							OBSERVATIONLABREPORT,
							NBSOperationLookup.DELETE,
							progAreaCd,
							jurisdictionCd, sharedInd);

					deleteExPerm = secObj.getPermission(NBSBOLookup.
							OBSERVATIONLABREPORT,
							NBSOperationLookup.DELETEEXTERNAL,
							progAreaCd,
							jurisdictionCd, sharedInd);

					editPerm = secObj.getPermission(NBSBOLookup.
							OBSERVATIONLABREPORT,
							NBSOperationLookup.EDIT,
							progAreaCd,
							jurisdictionCd, sharedInd);

					addInvestPerm = secObj.getPermission(NBSBOLookup.INVESTIGATION,
							NBSOperationLookup.ADD,
							progAreaCd,
							jurisdictionCd);

/*					viewWorkUpPerm = secObj.getPermission(NBSBOLookup.PATIENT,
							NBSOperationLookup.VIEWWORKUP,
							progAreaCd,
							NBSOperationLookup.ANY);
*/
					markAsReviewed = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
							NBSOperationLookup.MARKREVIEWED,
							progAreaCd,
							jurisdictionCd, sharedInd);

					transferPerm = secObj.getPermission(NBSBOLookup.
							OBSERVATIONLABREPORT,
							NBSOperationLookup.
							TRANSFERPERMISSIONS,
							progAreaCd,
							jurisdictionCd);
					if (progAreaCd!=null && PropertyUtil.isStdOrHivProgramArea(progAreaCd)&& markAsReviewed
							&& !labResultProxyVO.isAssociatedInvInd()
							&& rootObsDT.getProcessingDecisionCd() != null
							&& !rootObsDT.getProcessingDecisionCd()
							.trim().equals(""))
						clearMarkAsReviewed = true;
				}

				if (!viewPerm)
				{
					session.setAttribute("error",
							"User does not have View Observation Lab Report Permission.");
					throw new NEDSSAppException("ViewLabCommonUtil:GenerateLinks : Error-User does not have View Observation Lab Report Permission");

					//      throw new ServletException("User does not have View Observation Lab Report Permission.");
				}

				if(rootObsDT.getElectronicInd() != null && rootObsDT.getElectronicInd().equalsIgnoreCase("N"))
				{
					isElecIndNo = true;
				}
				else if (rootObsDT.getElectronicInd() != null && rootObsDT.getElectronicInd().equalsIgnoreCase("E")){
					isElecIndYes = true;
					request.setAttribute("electronicInd", "Y");
					request.setAttribute("ELRelectronicInd", "E");
				}
				else if(rootObsDT.getElectronicInd() != null && rootObsDT.getElectronicInd().equalsIgnoreCase("Y"))
				{  isElecIndY = true;
				request.setAttribute("ELRelectronicInd", "Y");
				}




				//buttons that a ViewObservationLab may have based on NBSContext
				boolean hasMarkAsReviewed = false;
				boolean hasTransferOwnership = false;
				boolean hasDelete = false;
				boolean hasEdit = false;
				boolean hasCreateInvestigation = false;
				//boolean hasAddComment = false;
				boolean hasClearMarkAsReviewed = false;
				boolean isFromManageEvents= false;
				//setting PageContext to PS018 ViewObservationLab
				/*  tm = NBSContext.getPageContext(session, "PS018", contextAction);
			String sCurrTask = NBSContext.getCurrentTask(session);
			sCurrTask = NBSContext.getCurrentTask(session);
			ErrorMessageHelper.setErrMsgToRequest(request, "PS018"); */

				if (contextAction.equalsIgnoreCase("ObservationLabIDOnEvents") ||
						contextAction.equalsIgnoreCase("ObservationLabIDOnSummary") ||
						contextAction.equalsIgnoreCase(NBSConstantUtil.ObservationLabID) ||
						contextAction.equalsIgnoreCase(NBSConstantUtil.MarkAsReviewed) ||
						contextAction.equalsIgnoreCase(NBSConstantUtil.ClearMarkAsReviewed) ||
						contextAction.equalsIgnoreCase(NBSConstantUtil.AddCommentLab) ||
						contextAction.equalsIgnoreCase(NBSConstantUtil.SUBMIT) ||
						contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL) ||
						contextAction.equalsIgnoreCase("ReturnToViewObservationLab") ||
						contextAction.equalsIgnoreCase("ViewLab") ||
						contextAction.equalsIgnoreCase("DeleteDenied")||
						contextAction.equalsIgnoreCase("ViewObservationLab")
						) {

					//System.out.println("****sCurrTak is: " + sCurrTask + " ***********");
					logger.info("sCurrTask in ViewLabLoad: " + sCurrTask);
					//ViewObservationLab 2 & ViewObservationLab3 from ViewFile Summary & Events tab
					if (sCurrTask.endsWith("Lab2") || sCurrTask.endsWith("Lab3")) {
						if (sCurrTask.endsWith("Lab2")) {
							request.setAttribute("linkName", "View File");
							request.setAttribute("linkValue",
									"/nbs/" + sCurrTask + ".do?ContextAction=" +
											tm.get("ReturnToFileSummary"));
						}
						else if (sCurrTask.endsWith("Lab3")) {
							request.setAttribute("linkName", "Return to File: Events");
							request.setAttribute("linkValue",
									"/nbs/" + sCurrTask + ".do?ContextAction=" +
											tm.get("ReturnToFileEvents"));
						}
						hasEdit = true;
						hasMarkAsReviewed = true;
						hasClearMarkAsReviewed = true;
						hasTransferOwnership = true;
						hasDelete = true;
						hasCreateInvestigation = true;
						//hasAddComment = true;

					}
					//ViewObservationLab 1,5,7 accessed from ViewInvestigation
					else if (sCurrTask.endsWith("Lab1") || sCurrTask.endsWith("Lab5") ||
							sCurrTask.endsWith("Lab7") || sCurrTask.endsWith("Lab14") ||
							sCurrTask.endsWith("Lab15") || sCurrTask.endsWith("Lab16")) {
						request.setAttribute("linkName", "Return to View Investigation");
						request.setAttribute("linkValue", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ReturnToViewInvestigation"));

						hasEdit = true;
						hasTransferOwnership = true;
						//hasAddComment = true;
						hasMarkAsReviewed = true;
						hasClearMarkAsReviewed = true;

					}
					//ViewObservationLab 4,6,8 accessed from ManageObservation
					else if (sCurrTask.endsWith("Lab4") || sCurrTask.endsWith("Lab6") ||
							sCurrTask.endsWith("Lab8") || sCurrTask.endsWith("Lab11")
							|| sCurrTask.endsWith("Lab12") || sCurrTask.endsWith("Lab13")) {
						request.setAttribute("linkName", "Return to Manage Associations");
						request.setAttribute("linkValue", "/nbs/" + sCurrTask + ".do?ContextAction=" +
										tm.get("ManageEvents"));
						hasEdit = true;
						hasMarkAsReviewed = true;
						hasClearMarkAsReviewed = true;
						hasTransferOwnership = true;
						hasDelete = true;
						hasCreateInvestigation = true;
						//hasAddComment = true;
						isFromManageEvents =true;
					}
					//ViewObservationLab9
					else if (sCurrTask.endsWith("Lab9")) {
						hasTransferOwnership = true;
						//hasAddComment = true;
					}
					//ViewObservationLab10
					else if (sCurrTask.endsWith("Lab10")) {
						hasEdit = true;
						hasDelete = true;
						hasCreateInvestigation = true;
						//hasAddComment = true;
						hasMarkAsReviewed = true;
						hasClearMarkAsReviewed = true;
						hasTransferOwnership = true;

						request.setAttribute("linkName", "View File");
						String viewFileLink = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ReturnToFileSummary");
						request.setAttribute("linkValue", viewFileLink);

						if (contextAction.equals("ViewLab") || contextAction.equals(NBSConstantUtil.CANCEL)) {
							request.setAttribute("linkNameViewEvents", "View Events");
							String viewEventsLink = "/nbs/" + sCurrTask + ".do?ContextAction=ViewEventsPopup";        
							request.setAttribute("linkValueViewEvents",
									"javascript:function popupViewEvents(){window.open('"+viewEventsLink+"');};popupViewEvents();");
						}

					}
					else if (contextAction.equalsIgnoreCase("DeleteDenied")){
						TreeMap<Object,Object> deleteDeniedTm  = NBSContext.getPageContext(session, "PS032", contextAction);
						request.setAttribute("linkName", "Return to View Observation");
						request.setAttribute("linkValue",
								"/nbs/" + sCurrTask + ".do?method=viewGenericLoad&ContextAction=" +
										deleteDeniedTm.get("ReturnToViewObservationLab"));
						//"ReturnToViewObservationLab" + "&observationUID=" + obsUID);

					}
					/*else if (sCurrTask.endsWith("DeleteDenied1")||sCurrTask.endsWith("DeleteDenied2") || sCurrTask.endsWith("DeleteDenied3") || sCurrTask.endsWith("DeleteDenied4") || sCurrTask.endsWith("DeleteDenied5")){


			}*/

					obsForm.getSecurityMap().put("editButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" +
									tm.get(NBSConstantUtil.EDIT));
					obsForm.getSecurityMap().put("markAsReviewButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" +
									tm.get(NBSConstantUtil.MarkAsReviewed));
					obsForm.getSecurityMap().put("clearMarkAsReviewButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" +
									tm.get(NBSConstantUtil.ClearMarkAsReviewed));
					obsForm.getSecurityMap().put("transferOwnership", "/nbs/" + sCurrTask + ".do?ContextAction=" +
									tm.get(NBSConstantUtil.TransferOwnership));

					obsForm.getSecurityMap().put("deleteButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" +
									tm.get(NBSConstantUtil.DELETE));

					obsForm.getSecurityMap().put("creatInvestigationButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" +
									tm.get(NBSConstantUtil.CreateInvestigation));
					obsForm.getSecurityMap().put("associateToInvestigationsButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" +
									tm.get(NBSConstantUtil.AssociateToInvestigations)+ "&initLoad=true");
					obsForm.getSecurityMap().put("addCommentButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" +
									tm.get(NBSConstantUtil.AddCommentLab));

					//request.setAttribute("PrintPage", "/nbs/LoadViewObservationLab1.do?ContextAction=PrintPage");

					//setting button values


					//setting button values
					//Edit Button
					if (hasEdit && editPerm && isElecIndNo) {
						//request.setAttribute("checkEdit", "true");
						obsForm.getSecurityMap().put("editPage", "true");

					}
					else{
						obsForm.getSecurityMap().put("editPage", "false");
					}
					//MarkAsReviewed Button
					if (hasMarkAsReviewed && markAsReviewed && !isProcessed) {
						obsForm.getSecurityMap().put("checkMarkReviewd", "true");
					}
					else{
						obsForm.getSecurityMap().put("checkMarkReviewd", "false");
					}
					//ClearMarkAsReviewed Button
					if (hasClearMarkAsReviewed && clearMarkAsReviewed) {
						obsForm.getSecurityMap().put("checkClearMarkAsReview", "true");
					}
					else{
						obsForm.getSecurityMap().put("checkClearMarkAsReview", "false");
					}

					//TransferOwnership Button
					if (hasTransferOwnership && transferPerm) {
						obsForm.getSecurityMap().put("checkTransfer", "true");
					}
					else{
						obsForm.getSecurityMap().put("checkTransfer", "false");
					}

					//Delete Button
					if (hasDelete &&
							( (deletePerm && isElecIndNo) || (deleteExPerm && isElecIndY) || (deleteExPerm && isElecIndYes))) {
						obsForm.getSecurityMap().put("deletePage", "true");
					}
					else{
						obsForm.getSecurityMap().put("deletePage", "false");
					}
					//Create Investigation Button
					if (hasCreateInvestigation && addInvestPerm && !isFromManageEvents) {
						obsForm.getSecurityMap().put("SubmitCreateInvPage", "true");

					}
					else{
						obsForm.getSecurityMap().put("SubmitCreateInvPage", "false");
					}


					//Add Comment button
					/**@TODO PKS: code change when add comment functionality is ready
			if (hasAddComment && isElecIndYes) {
			request.setAttribute("checkAddComment", "true");
			}
			else{
			request.setAttribute("checkAddComment", "false");
			}
					 */
				}
				request.setAttribute("personLocalID", PersonUtil.getDisplayLocalID(patientVO.getThePersonDT().getLocalId()));
				request.setAttribute("observationLocalUID", observationLocalUID);

				/**@TODO PKS Remove after add comm3nt on Vliew ELR works
			this.setAddUserComments(labResultProxyVO,request, secObj);

			//sets the ProgramArea and Jurisdiction to request from security object
			super.getNBSSecurityJurisdictionsPA(request, secObj, contextAction);
				 */
				/**
				 * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
			loadLabLDFView(labResultProxyVO ,lab112UID, request);
				 
				if(contextAction.equalsIgnoreCase("PrintPage")) {
					return new ActionForward("/observation/labreport/labreport_print");
				}*/

			}catch (Exception e) {
				logger.error("Exception in View Lab Report Load: " + e.getMessage());
				throw new ServletException("An error occurred in View Lab Report Load : "+e.getMessage());
			}      


		}


	public static void populatePatientSummary(PageForm form, PersonVO personVO, ObservationVO rootObservationVO, String busObjType, HttpServletRequest request) throws NEDSSAppException
    {
    	try {
    		if(personVO!=null){
				Collection<Object>  nms = personVO.getThePersonNameDTCollection();
				String strPName ="";
				String strFName="";
				String strMName="";
				String strLName="";
				String CurrSex = "";
				String nmSuffix = "";
				if(nms != null)
				{
					Iterator<Object>  itname = nms.iterator();
					Timestamp mostRecentNameAOD = null;
					while (itname.hasNext()) {
						PersonNameDT name = (PersonNameDT) itname.next();

						// for personInfo
						if (name != null
								&& name.getNmUseCd() != null
								&& name.getNmUseCd().equals(NEDSSConstants.LEGAL)
								&& name.getStatusCd() != null
								&& name.getStatusCd().equals(
										NEDSSConstants.STATUS_ACTIVE)
								&& name.getRecordStatusCd() != null
								&& name.getRecordStatusCd().equals(
										NEDSSConstants.RECORD_STATUS_ACTIVE)) {
							if (mostRecentNameAOD == null
									|| (name.getAsOfDate() != null && !name
											.getAsOfDate()
											.before(mostRecentNameAOD))) {
								strFName = "";
								strMName = "";
								strLName = "";
								nmSuffix = "";
								mostRecentNameAOD = name.getAsOfDate();
								if (name.getFirstNm() != null)
									strFName = name.getFirstNm();
								if (name.getMiddleNm() != null)
									strMName = name.getMiddleNm();
								if (name.getLastNm() != null)
									strLName = name.getLastNm();
								if (name.getNmSuffix() != null)
									nmSuffix = name.getNmSuffix();

							}
						}
					}
				}
				strPName = strFName +" "+strMName+ " "+strLName;
				if(null == strPName || strPName.equalsIgnoreCase("null")){
					strPName ="";
				}

				form.getAttributeMap().put("FullName", HTMLEncoder.encodeHtml(strPName));
				request.setAttribute("FullName", HTMLEncoder.encodeHtml(strPName));

				form.getAttributeMap().put("DOB",  HTMLEncoder.encodeHtml(StringUtils.formatDate(personVO.getThePersonDT().getBirthTime())));
				request.setAttribute("DOB", HTMLEncoder.encodeHtml(StringUtils.formatDate(personVO.getThePersonDT().getBirthTime())));
				PersonUtil.setCurrentAgeToRequest(request, personVO.getThePersonDT());
				CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
				request.setAttribute("patientSuffixName", HTMLEncoder.encodeHtml(cachedDropDownValues.getCodeShortDescTxt(nmSuffix, "P_NM_SFX")));


				if(personVO.getThePersonDT().getCurrSexCd() != null)
					 CurrSex = personVO.getThePersonDT().getCurrSexCd();
					if(CurrSex.equalsIgnoreCase("F"))
						CurrSex = "Female";
					if(CurrSex.equalsIgnoreCase("M"))
						CurrSex = "Male";
					if(CurrSex.equalsIgnoreCase("U"))
						CurrSex = "Unknown";
				form.getAttributeMap().put("CurrentSex", HTMLEncoder.encodeHtml(CurrSex));
				request.setAttribute("CurrentSex", HTMLEncoder.encodeHtml(CurrSex));
				
				form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_PATIENT_ID,personVO.getThePersonDT().getLocalId()== null? HTMLEncoder.encodeHtml("") : HTMLEncoder.encodeHtml(PersonUtil.getDisplayLocalID(personVO.getThePersonDT().getLocalId())));
				
				form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_ADDRESS,PageLoadUtil.extractAddressAsString(personVO));
				
				if(personVO.getTheEntityIdDTCollection()!=null){
					Iterator<Object> ssnIter = personVO.getTheEntityIdDTCollection().iterator();
		            while (ssnIter.hasNext())
		            {
		                EntityIdDT entityIdDT = (EntityIdDT) ssnIter.next();
		                if (entityIdDT.getTypeCd() != null && entityIdDT.getTypeCd().equalsIgnoreCase("SS") && entityIdDT.getStatusCd()!=null && entityIdDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE))
		                {
		                    form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_SSN,entityIdDT.getRootExtensionTxt()== null? HTMLEncoder.encodeHtml("") : HTMLEncoder.encodeHtml(entityIdDT.getRootExtensionTxt()));
		                }
		            }
				}
				
				if(rootObservationVO!=null){
					NBSAuthHelper helper = new NBSAuthHelper();
					if(rootObservationVO.getTheObservationDT()!=null){
						ObservationDT observationDT = rootObservationVO.getTheObservationDT();
						form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_CREATED_ON,observationDT.getAddTime()== null ? HTMLEncoder.encodeHtml("") : HTMLEncoder.encodeHtml(StringUtils.formatDate(observationDT.getAddTime())));
						
						String createdBy = observationDT.getAddUserId() == null ? "" : helper.getUserName(observationDT.getAddUserId());

						form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_CREATED_BY,createdBy);
						form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_UPDATED_ON,observationDT.getLastChgTime()== null ? HTMLEncoder.encodeHtml(""): HTMLEncoder.encodeHtml(StringUtils.formatDate(observationDT.getLastChgTime())));
						form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_UPDATED_BY,observationDT.getLastChgUserId() == null ? HTMLEncoder.encodeHtml(""):HTMLEncoder.encodeHtml(helper.getUserName(observationDT.getLastChgUserId())));
						
						form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_COLLECTION_DATE,observationDT.getEffectiveFromTime() == null ? HTMLEncoder.encodeHtml("") : HTMLEncoder.encodeHtml(StringUtils.formatDate(observationDT.getEffectiveFromTime())));
						form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_LAB_REPORT_DATE,observationDT.getActivityToTime() == null ? HTMLEncoder.encodeHtml("") : HTMLEncoder.encodeHtml(StringUtils.formatDate(observationDT.getActivityToTime())));
						form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_DATE_RECEIVED_BY_PUBLIC_HEATLH,observationDT.getRptToStateTime() == null ?HTMLEncoder.encodeHtml("") : HTMLEncoder.encodeHtml(StringUtils.formatDate(observationDT.getRptToStateTime())));
						
						form.getAttributeMap().put(PageConstants.ELECTRONIC_IND,HTMLEncoder.encodeHtml(observationDT.getElectronicInd()));

						form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_LOCAL_ID,observationDT.getLocalId()== null ? HTMLEncoder.encodeHtml(""):HTMLEncoder.encodeHtml(observationDT.getLocalId()));
						
						String processingDecisionCdDesc = "";
						if(observationDT.getProcessingDecisionCd() != null)
							processingDecisionCdDesc = cachedDropDownValues.getCodeShortDescTxt(observationDT.getProcessingDecisionCd(), "NBS_NO_ACTION_RSN");
						if(observationDT.getProcessingDecisionCd() != null && "".equals(processingDecisionCdDesc))
							processingDecisionCdDesc = cachedDropDownValues.getCodeShortDescTxt(observationDT.getProcessingDecisionCd(), "STD_NBS_PROCESSING_DECISION_ALL");
						form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_PROCESSING_DECISION, HTMLEncoder.encodeHtml(processingDecisionCdDesc));
						
						String processingDecisionTxt = observationDT.getProcessingDecisionTxt()== null ? "" : observationDT.getProcessingDecisionTxt();
						if(processingDecisionTxt.length()>200)
							processingDecisionTxt = processingDecisionTxt.substring(0,197)+"...";
						form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_PROCESSING_DECISION_NOTES,HTMLEncoder.encodeHtml(processingDecisionTxt));
						
						for (Iterator<Object> itr1 = rootObservationVO.getTheActIdDTCollection().iterator(); itr1.hasNext();)
			            {
			                ActIdDT actIdDT = (ActIdDT)itr1.next();
			                if("FN".equals(actIdDT.getTypeCd())){
			                	form.getAttributeMap().put(PageConstants.EVENT_SUMMARY_ACCESSION_NUMBER,actIdDT.getRootExtensionTxt() == null ? HTMLEncoder.encodeHtml("") : HTMLEncoder.encodeHtml(actIdDT.getRootExtensionTxt()));
			                }
			            }
					}
				}
    		}
		} catch (NumberFormatException ex) {
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		} catch (NEDSSSystemException ex) {
			logger.error("Exception : "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}

    }
		
	/**
	 * Util method to populate ELR Lab comment Summary
	 * @param form
	 * @param batchMap
	 * @param observationLabResultVOMap
	 * @throws NEDSSAppException
	 */
	public static void populateElrCommentSummary(PageForm form, Map<Object, Object> batchMap, Map<Long, Object> observationLabResultVOMap) throws NEDSSAppException
    {
		String SubSectionNm = "";
		NBSAuthHelper helper = new NBSAuthHelper();
		try {
			if (batchMap != null && batchMap.size() > 0) {
				Iterator<Entry<Object, Object>> ite = batchMap.entrySet().iterator();
				while (ite.hasNext()) {
					// walk through each subsection that is in the batchmap
					// structure map
					ArrayList<BatchEntry> alist = new ArrayList<BatchEntry>();
					TreeMap<Timestamp, BatchEntry> map =  new TreeMap<Timestamp, BatchEntry>();
					Map.Entry<Object, Object> pairs1 = (Map.Entry<Object, Object>) ite.next();
					SubSectionNm = pairs1.getKey().toString();
					String batch[][] = (String[][]) pairs1.getValue();
					for (int batchsize = 0; batchsize < batch.length; batchsize++) {
						if (batch[batchsize][0] != null) {
							String questionIdentifier = batch[batchsize][0];
							if(questionIdentifier.equals(PageConstants.LAB_VALUE_TXT)) {
								if(observationLabResultVOMap!=null && observationLabResultVOMap.size()>0) {
									Set<Long> set = observationLabResultVOMap.keySet();
									if(set.size()>0) {
										Iterator<Long> it = set.iterator();
										while(it.hasNext()) {
											Long labCommentUid = (Long)it.next();
											ObservationVO labCommentVO = (ObservationVO)observationLabResultVOMap.get(labCommentUid);
											Collection<Object> coll = labCommentVO.getTheObsValueTxtDTCollection();
											if(coll!=null && coll.size()>0) {
												Iterator<Object> commentIterator = coll.iterator();
												while(commentIterator.hasNext()) {
													ObsValueTxtDT obsValueTextDT =(ObsValueTxtDT)commentIterator.next();
													String text = obsValueTextDT.getValueTxt();
													BatchEntry be  =  new BatchEntry();
													Map<String, String> answerMap = new HashMap<String, String>();
													answerMap.put(PageConstants.LAB_VALUE_TXT, text);
													answerMap.put(PageConstants.LAB_VALUE_TXT+"User", helper.getUserName(labCommentVO.getTheObservationDT().getAddUserId()));
													answerMap.put(PageConstants.LAB_VALUE_TXT+"Date", timeConverter(labCommentVO.getTheObservationDT().getActivityToTime()));
													be.setAnswerMap(answerMap);
													be.setSubsecNm(SubSectionNm);
													be.setId(PageForm.getNextId());
													map.put(labCommentVO.getTheObservationDT().getActivityToTime(), be);
													//alist.add(be);
												}
											}
										}
										if(map.values().size()>0) {
											List<BatchEntry> list = new ArrayList<BatchEntry>(map.values()); 
											form.getBatchEntryMap().put(SubSectionNm, (ArrayList<BatchEntry>) list);
										}
									}
								}
							}
						}
					}
				}
			}
		}catch (NEDSSSystemException ex) {
			logger.error("Exception in populateElrCommentSummary: "+ ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(),ex);
		}
    }
	
	public static String timeConverter(Timestamp time) throws NEDSSAppException {
		String dateString = time+"";
		String reportDate ="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		    java.util.Date date = formatter.parse(dateString);
		    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		    reportDate = df.format(date );
		    return reportDate;
		} catch (ParseException e) {
			logger.error("Exception in timeConverter: "+ e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(),e);
		}
	}

		
/*		
   private void convertELRPatientToUI(PersonVO personVO,PageForm form)
    {

        CachedDropDownValues cachedConverter = new CachedDropDownValues();
        TreeMap<Object, Object> raceMap = cachedConverter.getRaceCodes();
        ArrayList<Object> pNamelist = (ArrayList<Object>) personVO.getThePersonNameDTCollection();
        String electronicInd = personVO.getThePersonDT().getElectronicInd();
        Iterator<Object> pNameIt = pNamelist.iterator();
        while (pNameIt.hasNext())
        {
            PersonNameDT pNameDT = (PersonNameDT) pNameIt.next();
            if (pNameDT != null)
            {
                if (pNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME))
                {
                    StringBuffer personLegalDetails = new StringBuffer("");
                        personLegalDetails.append(pNameDT.getNmPrefix() == null ? "" : pNameDT.getNmPrefix()); // 101
                        personLegalDetails.append(pNameDT.getFirstNm() == null ? "" : " " + pNameDT.getFirstNm()); // 104
                        personLegalDetails.append(pNameDT.getMiddleNm() == null ? "" : " " + pNameDT.getMiddleNm()); // 105
                        personLegalDetails.append(pNameDT.getMiddleNm2() == null ? "" : " " + pNameDT.getMiddleNm2()); // 106
                        personLegalDetails.append(pNameDT.getLastNm() == null ? "" : " " + pNameDT.getLastNm()); // 102
                        personLegalDetails.append(pNameDT.getLastNm2() == null ? "" : " " + pNameDT.getLastNm2()); // 103
                        personLegalDetails.append(pNameDT.getNmSuffix() == null ? "" : ", " + pNameDT.getNmSuffix()); // 107
                        personLegalDetails.append(pNameDT.getNmDegree() == null ? "" : ", " + pNameDT.getNmDegree()); // 108
                        form.getPageClientVO().setAnswer(key, personLegalDetails);
                       // form
                        clientVO.setAnswer(PageConstants.LAB_JURISDICTION_CD, observationDT.getJurisdictionCd());
                        request.setAttribute("personLegalDetails", personLegalDetails.toString());
                    }
                if (pNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.ALIAS_NAME))
                {
                    StringBuffer personAliasDetails = new StringBuffer("");
                    personAliasDetails.append(pNameDT.getNmPrefix() == null ? "" : pNameDT.getNmPrefix()); // 101
                    personAliasDetails.append(pNameDT.getFirstNm() == null ? "" : " " + pNameDT.getFirstNm()); // 104
                    personAliasDetails.append(pNameDT.getMiddleNm() == null ? "" : " " + pNameDT.getMiddleNm()); // 105
                    personAliasDetails.append(pNameDT.getMiddleNm2() == null ? "" : " " + pNameDT.getMiddleNm2()); // 106
                    personAliasDetails.append(pNameDT.getLastNm() == null ? "" : " " + pNameDT.getLastNm()); // 102
                    personAliasDetails.append(pNameDT.getLastNm2() == null ? "" : " " + pNameDT.getLastNm2()); // 103
                    personAliasDetails.append(pNameDT.getNmSuffix() == null ? "" : ", " + pNameDT.getNmSuffix()); // 107
                    personAliasDetails.append(pNameDT.getNmDegree() == null ? "" : ", " + pNameDT.getNmDegree()); // 108
                    request.setAttribute("personAliasDetails", personAliasDetails.toString());
                }
            }
        

        ArrayList<Object> entityCollections = (ArrayList<Object>) personVO
                .getTheEntityLocatorParticipationDTCollection();
        Iterator<Object> locatorIt = entityCollections.iterator();
        while (locatorIt.hasNext())
        {
            EntityLocatorParticipationDT entityLocatorDT = (EntityLocatorParticipationDT) locatorIt.next();
            // ELP111 for home address
            if (entityLocatorDT.getCd() != null
                    && entityLocatorDT.getClassCd() != null && entityLocatorDT.getRecordStatusCd() != null
                    && entityLocatorDT.getUseCd() != null)
            {
                if (entityLocatorDT.getCd().equalsIgnoreCase("H")
                        &&
                        entityLocatorDT.getClassCd().equalsIgnoreCase("PST")
                        && entityLocatorDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE")
                        && entityLocatorDT.getUseCd().equalsIgnoreCase("H"))
                {
                    StringBuffer homeAddress = new StringBuffer("");
                    PostalLocatorDT postalDT = entityLocatorDT.getThePostalLocatorDT();
                    if (postalDT != null)
                    {
                        if (request.getAttribute("ELRelectronicInd") != null
                                && request.getAttribute("ELRelectronicInd").equals("Y"))
                        {
                            homeAddress.append(postalDT.getStreetAddr1() == null ? "" : postalDT.getStreetAddr1());
                            homeAddress.append(postalDT.getStreetAddr2() == null ? " " : " "
                                    + postalDT.getStreetAddr2());
                            homeAddress.append(postalDT.getCityDescTxt() == null ? "<br/> " : "<br/>"
                                    + postalDT.getCityDescTxt());
                            homeAddress.append((postalDT.getCityDescTxt() != null) ? ", " : "");
                            homeAddress.append(postalDT.getStateCd() == null ? " " : " "
                                    + this.getStateDescTxt(postalDT.getStateCd()));
                            homeAddress.append(postalDT.getZipCd() == null ? " " : " " + postalDT.getZipCd());
                            homeAddress.append(postalDT.getCntyCd() == null ? " " : " "
                                    + this.getCountiesByDesc(postalDT.getCntyCd()));
                            if (postalDT.getCntryCd() == null)
                                homeAddress.append("");
                            else if (postalDT.getCntryCd() != null)
                            {
                                String homeCountryDesc = "";
                                if (cdv.getCountryCodesAsTreeMap() != null)
                                    homeCountryDesc = (String) cdv.getCountryCodesAsTreeMap()
                                            .get(postalDT.getCntryCd());
                                homeAddress.append(" " + homeCountryDesc);
                            }
                        }

                    }
                    request.setAttribute("homeAddress", homeAddress.toString());

                }

                // ELP111 for home address
                if (entityLocatorDT.getCd().equalsIgnoreCase("PH")
                        &&
                        // entityLocatorDT.getCdDescTxt().equalsIgnoreCase("Phone")
                        // &&
                        entityLocatorDT.getClassCd().equalsIgnoreCase("TELE")
                        && entityLocatorDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE")
                        && entityLocatorDT.getUseCd().equalsIgnoreCase("H")
                        && entityLocatorDT.getStatusCd()!=null 
                		&& entityLocatorDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE))
                {
                    TeleLocatorDT teleDT = entityLocatorDT.getTheTeleLocatorDT();

                    request.setAttribute("Home177", teleDT.getPhoneNbrTxt());
                    request.setAttribute("Home181", teleDT.getExtensionTxt());

                }
                // ELP111 for home address
                if (entityLocatorDT.getCd().equalsIgnoreCase("PH")
                        &&
                        // entityLocatorDT.getCdDescTxt().equalsIgnoreCase("Phone")&&
                        entityLocatorDT.getClassCd().equalsIgnoreCase("TELE")
                        && entityLocatorDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE")
                        && entityLocatorDT.getUseCd().equalsIgnoreCase("WP")
                        && entityLocatorDT.getStatusCd()!=null 
                		&& entityLocatorDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE))
                {
                    TeleLocatorDT teleDT = entityLocatorDT.getTheTeleLocatorDT();

                    request.setAttribute("Work177", teleDT.getPhoneNbrTxt());
                    request.setAttribute("Work181", teleDT.getExtensionTxt());

                }
            }

        }
        }
        
        request.setAttribute("isDeceased", personVO.getThePersonDT().getDeceasedIndCd());
        //request.setAttribute("DEM128", StringUtils.formatDate(personVO.getThePersonDT().getDeceasedTime()));
        //request.setAttribute("DEM140", personVO.getThePersonDT().getMaritalStatusCd());
        //request.setAttribute("DEM155", personVO.getThePersonDT().getEthnicGroupInd());
        //request.setAttribute("DEM196", personVO.getThePersonDT().getDescription());
    }*/

		   
	
		
		private String getDocumentViewHTML(EDXDocumentDT dt, String mode)
				throws ServletException {
	 				TransformerFactory tFactory = TransformerFactory.newInstance();
	 				Writer result = new StringWriter();
			try {
				Transformer transformer = null;
				if("print".equals(mode))
					transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(new ByteArrayInputStream(_createPrintSS(dt.getDocumentViewXsl()).getBytes())));
				else
					transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(new ByteArrayInputStream(dt.getDocumentViewXsl().getBytes())));
				
				PrintWriter out = new PrintWriter(result);
				InputStream is = new ByteArrayInputStream(dt.getPayload().getBytes("UTF-8")); 
				transformer.transform(new javax.xml.transform.stream.StreamSource(is),new javax.xml.transform.stream.StreamResult(out));
			} catch (Exception ex) {
				logger.fatal("Error converting ELR Document XML "+ex.getMessage());
				throw new ServletException(ex);
			}
			return result.toString();
		}

		private static String _createPrintSS(String xsl) {
			StringBuffer sb = new StringBuffer();
			
			try {
				String printIcon = "<body><div class=\"printerIconBlock screenOnly\"><table role=\"presentation\" style=\"width:98%; margin:3px;\"><tr><td style=\"text-align:right; font-weight:bold;\"><a href=\"#\" onclick=\"return printPage();\"> <img src=\"printer_icon.gif\" alt=\"Print Page\" title=\"Print Page\"/> Print Page </a></td></tr></table></div><br/>";
				String printCss = "<![CDATA["
			+"TABLE {	border-collapse: collapse;	border: 0px;}"
			+"body {    background-color: #FFFFFF;    color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: normal;     margin-top: 0px;}"
			+"label {    color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: bold;    vertical-align: top;}"
			+"td {  color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: normal;  padding-left:5px;}"
			+"th { color: #000000;    font-family: Arial, sans-serif;    font-size: 10pt;    font-weight: bold;    text-align: left;}"
			+"div.sect table.sectHeader {width:100%; border-width:0px 0px 1px 0px; border-color:#5F8DBF; "
			+"	   margin-top:1em; border-style:solid; padding:0.20em 0.20em 0.20em 0.25em;}"
			+"div.sect table.sectHeader tr td.sectName {color:#CC3300; font-size: 110%; font-weight:bold; text-transform:capitalize;}"
			+"div.sect table.sectHeader tr td.sectName a.anchor {text-decoration:none; /*color:#5F8DBF;*/ color:#a46322}"
			+"div.sect div.sectBody {text-align:center; margin-left:1em;}"

			+"table.sectionsToggler, table.subSectionsToggler, table.subSect1 {width:98%; margin:0 auto; margin-top:1em; }"

			+"div.bluebarsect table.bluebarsectHeader {width:100%; margin-bottom:2px;}"
			+"div.graybarsect table {width:100%;  margin-top:0em; margin-bottom:0em;  padding:0px 0px;}"
			+"div.bluebarsect table.bluebarsectHeaderWhite {margin-bottom:2px;}"
			+"div.bluebarsect table.bluebarsectHeader tr td.bluebarsectName {color:#CC3300;font-size:13px;font-weight:bold;}"
			+"div.bluebarsect table.bluebarsectHeader tr td.bluebarsectNameNoColor {font-size:13px;font-weight:bold;}"
			+"div.bluebarsect table.bluebarsectHeader tr td.bluebarsectName a.anchor {color:#CC3300;font-size:13px;}"
			+"div.bluebarsect table.bluebarsectHeader tr td a.anchorBack {display:none;}"
			+"div.graybarsect table tbody tr.odd1 td{border-width:1px;border-style:solid; background:#EFEFEF; padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px; font-weight:bold;}"
			+"div.graybarsect table tbody tr.even1 td{padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px; border-width:1px;border-style:solid; }"
			+"div.graybarsect table tbody tr.odd1 td.odd1blank{background:#FFF;font-size:11px; border-width:1px;border-style:none none none none;}"
			+"div.graybarsect table tbody tr.odd1 td.odd1blanktop{padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px; border-width:1px;border-style: none none none none ; }"
			+"div.graybarsect table tbody tr.even1 td.even1blank{padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px;border-width:1px;border-style:none none none none; }"
			+"div.graybarsect table tbody tr.even1 td.even1blankbottom{border-width:1px;border-style:none none none none; padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px;}"
			
			+"span.valueTopLine {font:13px Arial; font-weight:bold; margin-left:0.2em;}"
			+"span.valueTopLine1 {font:13px Arial; margin-left:0.2em;}"
			+"table.style{width:100%; margin:0 auto; margin-top:1em;border:1px solid #AFAFAF;}"
			+"table tr.cellColor {background:#FEF2BC;}"
			+"table.Summary {width:100%; margin:0 auto; margin-top:1em;border:1px solid #AFAFAF;}"
			+"table td.border3 {padding:0.15em;width:24%; border-style:solid; border-width:1px 1px 1px 1px; border-color:#AFAFAF;}"
			+"table td.border4{text-align:right;padding:0.15em;width:24%; border-style:solid; border-width:1px 1px 1px 1px; border-color:#AFAFAF;}"

						+ "]]>"; // String printCss = readFile(new
									// File(PropertyUtil.propertiesDir +
									// (File.separator) + "phc_print.css"));
				xsl = xsl.replaceAll("<body>",printIcon);		
				int startPos = xsl.indexOf("<STYLE>");
				int endPos = xsl.indexOf("</STYLE>");
				sb.append(xsl.substring(0,(startPos+7)));
				sb.append(printCss);
				sb.append(xsl.substring(endPos));
				
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.error("Error while creating Print Specific XSL: "
						+ e.toString());
			}
			return sb.toString();
		}
}
