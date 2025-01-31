package gov.cdc.nedss.webapp.nbs.action.nbsDocument;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import gov.cdc.nedss.systemservice.dt.NBSDocumentMetadataDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.nbsDocument.util.DocumentTransferOwnershipUtil;
import gov.cdc.nedss.webapp.nbs.action.nbsDocument.util.NBSDocumentActionUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.form.nbsdocument.NbsDocumentForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

/**
 * 
 * @author NEDSS Development Team
 * @updated Pradeep Sharma
 * @update: 2011
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * ViewDocumentAction.java
 * Jul 31, 2009
 * @version 1.0
 */
public class ViewDocumentAction extends DispatchAction{
	
	 static final LogUtils logger = new LogUtils(ViewDocumentAction.class.getName());
	 NBSDocumentActionUtil util = new NBSDocumentActionUtil();
	 static String screenXSL = "";
	 static String printXSL = "";
	 private static Map<Object,Object> docXSLMap = new TreeMap(); 
	 
	 
	// static final File sf = new File(PropertyUtil.propertiesDir + (File.separator) + "PHCD.xsl"); 
	 private static CachedDropDownValues cdv = new CachedDropDownValues();


/*	 static {
		 try {
			screenXSL = readFile(sf);
			printXSL = _createPrintSS(screenXSL);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while reading XSL in ViewDocumentLoad: " + e.toString());
		}		 
	 }*/
	 
	 /**
	  * 
	  * @param mapping
	  * @param aForm
	  * @param request
	  * @param response
	  * @return
	  * @throws IOException
	  * @throws ServletException
	  */
	 public ActionForward loadDocument(ActionMapping mapping, ActionForm aForm,HttpServletRequest request,HttpServletResponse response) 
		throws IOException,ServletException {

		 HttpSession session = request.getSession();
		 logger.info("ViewDocumentAction is being called");
		 Long nbsDocUid = null;

		 if (session == null) {
			 logger.fatal("error no session");
			 return mapping.findForward("login");
		 }

		 try {
			 String contextAction = null;
			 contextAction = request.getParameter("ContextAction");


			 String sCurrTask = null;
			 NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute( "NBSSecurityObject");
			 TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
			 if (contextAction == null && request.getAttribute("ContextAction") != null)
				 contextAction = (String) request.getAttribute("ContextAction");
			 tm = NBSContext.getPageContext(session, "PS233", contextAction);
			 sCurrTask = NBSContext.getCurrentTask(session);	
			 // We are getting the values from the session instead of including a DeleteDenied page in the Context Map
			 if(contextAction == null || (contextAction!=null && contextAction.equals("Delete")) )
				 contextAction = (String) session.getAttribute("ContextAction");
			 if(sCurrTask == null)	
				 sCurrTask = (String)session.getAttribute("currentTask");



			 ErrorMessageHelper.setErrMsgToRequest(request, "PS233");

			 String docUidString = request.getParameter("nbsDocumentUid");
			 if(docUidString == null)
				 docUidString = (String)request.getAttribute("docUid");
			 if(docUidString == null)
				 docUidString = (String)session.getAttribute("docUid");
			 if(docUidString!=null){
				 nbsDocUid= new Long(Long.parseLong(docUidString));
				 NBSContext.store(session, NBSConstantUtil.DSDocumentUID, nbsDocUid);
			 }
			 Object dsObj= null;
			 try{
				 dsObj = NBSContext.retrieve(session,"DSQueueObject");
			 }
			 catch(NullPointerException ex){
				 //Let it go as if object is not in the context don't show the link
			 }


			 if((contextAction != null && (!contextAction.equals("DocumentIDFromInv") && !contextAction.equals("DocumentIDOnEvents")))&& dsObj !=null){
				 String queueType = ((DSQueueObject)NBSContext.retrieve(session,"DSQueueObject")).getDSQueueType();
				 if(queueType!=null && queueType.equals(NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW))
				 {
					 request.setAttribute("linkName1", "Return to Documents Requiring Review");
					 request.setAttribute("linkValue1",
							 "/nbs/LoadNewLabReview1.do?method=loadQueue&ContextAction="+tm.get("ReturnToObservationNeedingReview")); 


					 request.setAttribute("linkName2", "View File");
					 request.setAttribute("linkValue2",
							 "/nbs/LoadViewFile1.do?ContextAction=" +
									 tm.get("ReturnToFileSummary"));

				 }
				 else if(queueType!=null && queueType.equals(NEDSSConstants.OBSERVATIONS_ASSIGNMENT))
				 {
					 request.setAttribute("linkName1", "Return to Documents Requiring Security Assignment");
					 request.setAttribute("linkValue1",
							 "/nbs/LoadObsNeedingAssignment1.do?method=loadQueue&ContextAction=" + tm.get("ReturnToObservationsNeedingAssignment"));
				 }else if(queueType!=null && queueType.equals(NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS))
				 {
					 request.setAttribute("linkName2", "Return to File: Events");
					 request.setAttribute("linkValue2",
							 "/nbs/LoadViewFile1.do?ContextAction=" +
									 tm.get("ReturnToFileEvents"));
				 }
			 }
			 else if (contextAction.equals("DocumentIDFromInv")) {
				 Long personUid = (Long)NBSContext.retrieve(session, "DSPatientPersonUID");
				 request.setAttribute("linkName1", "Return to View Investigation");
				 request.setAttribute("linkValue1","/nbs/LoadViewInvestigation3.do?ContextAction=" + tm.get("ReturnToViewInvestigation")+"&personUID="+personUid); 
			 }else if(contextAction.equals("DocumentIDOnEvents")||((contextAction.equals("Cancel") || contextAction.equals("CancelToViewDoc")) && sCurrTask.equals("ViewDocument4") )){
				 //Long personUid = (Long)NBSContext.retrieve(session, "DSPatientPersonUID");
				 request.setAttribute("linkName2", "Return to File: Events");
				 request.setAttribute("linkValue2",
						 "/nbs/LoadViewFile1.do?ContextAction=" +
								 tm.get("ReturnToFileEvents"));
				 NBSContext.store(session, NBSConstantUtil.DSFileTab, "3");

			 }else if(contextAction.equals("ViewDocument")){
				 //Long personUid = (Long)NBSContext.retrieve(session, "DSPatientPersonUID");
				 request.setAttribute("linkName2", "Return to Manage Associations");
				 request.setAttribute("linkValue2",
						 "/nbs/LoadManageEvents3.do?method=viewManageLoad&ContextAction=ManageEvents");
			 }

			 NbsDocumentForm docForm = (NbsDocumentForm)aForm;
			 docForm.clearAll();
			 NBSDocumentDT nbsDocDT = null;
			 NBSDocumentVO  nbsDocVO = null;
			 if(contextAction != null && contextAction.equals("PrintPage")|| contextAction.equals("Cancel") || contextAction.equals("CancelToViewDoc")){
				 nbsDocUid = (Long)NBSContext.retrieve(session, NBSConstantUtil.DSDocumentUID);
			 }
			 //If the uid is not in request , get it from Context 
			 if(nbsDocUid==null){

				 Object obj = NBSContext.retrieve(session, "DSObservationUID");
				 try
				 {
					 if (obj instanceof String)
					 {
						 nbsDocUid = new Long( (String) NBSContext.retrieve(session,"DSObservationUID"));
					 }
					 else if (obj instanceof Long)
					 {
						 nbsDocUid = (Long) NBSContext.retrieve(session,"DSObservationUID");
					 }
					 if(nbsDocUid != null)
						 docUidString = nbsDocUid.toString();


				 }
				 catch (NullPointerException ne)
				 {
					 logger.fatal("Can not retrieve DSObservationUID from Object Store.");
					 ErrorMessageHelper.setErrMsgToRequest(request, "PS233");
				 }
			 }
			 //Set the UId to the request   
			 request.setAttribute("docUid",docUidString);	
			 NBSContext.store(session, "DSDocumentUID", nbsDocUid);
			 try{
				 nbsDocVO = util.getNBSDocument(session,nbsDocUid);
				 nbsDocDT = nbsDocVO.getNbsDocumentDT();
			 }catch(Exception e){
				 throw new ServletException(e.toString());
			 }
			 NBSContext.store(session,"DSPatientPersonUID",nbsDocVO.getPatientVO().getThePersonDT().getPersonParentUid());
			 String progAreaCd = nbsDocDT.getProgAreaCd();
			 String jurisdictionCd =  nbsDocDT.getJurisdictionCd();
			 String sharedInd =  nbsDocDT.getSharedInd(); 
			 String docLocalUID =  nbsDocDT.getLocalId(); 

			 // seting in the nbsDocumentForm to display the documentsummary in the viewdocument page
			 java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("MM/dd/yyyy",java.util.Locale.US);
			 docForm.setNbsDocumentDt(nbsDocDT);
			  
			 docForm.getAttributeMap().put("ProgramArea",nbsDocDT.getProgAreaCd());
			 if(nbsDocDT.getJurisdictionCd() !=null)
				 docForm.getAttributeMap().put("Jurisdiction",cdv.getJurisdictionDesc(nbsDocDT.getJurisdictionCd()));
			 String patientId = PersonUtil.getDisplayLocalID(nbsDocVO.getPatientVO().getThePersonDT().getLocalId());		    
			 docForm.getAttributeMap().put("PatientId",patientId);
			 String documentId = (nbsDocDT.getExternalVersionCtrlNbr() != null && nbsDocDT
					 .getExternalVersionCtrlNbr().intValue() > 1) ? nbsDocDT
							 .getLocalId() + "<font color=\"#006600\"> (Update)</font>"
							 : nbsDocDT.getLocalId();
			 docForm.getAttributeMap().put("NbsDocumentUid", documentId);
			 docForm.getAttributeMap().put("FullName", nbsDocVO.getPatientVO().getThePersonDT().getFirstNm()+ " "+nbsDocVO.getPatientVO().getThePersonDT().getLastNm());
			 if(nbsDocVO.getPatientVO().getThePersonDT().getCurrSexCd() != null)
				 docForm.getAttributeMap().put("Sex", cdv.getCodeShortDescTxt(nbsDocVO.getPatientVO().getThePersonDT().getCurrSexCd(), NEDSSConstants.SEX));
			 if(nbsDocVO.getPatientVO().getThePersonDT().getBirthTime() != null)
				 docForm.getAttributeMap().put("DBO",sdfInput.format(nbsDocVO.getPatientVO().getThePersonDT().getBirthTime()));
			 if(nbsDocDT.getAddTime() != null)
				 docForm.getAttributeMap().put("DateReceived", sdfInput.format(nbsDocDT.getLastChgTime()));
			 boolean isProcessed = false;

			 NBSAuthHelper helper = new NBSAuthHelper();
			 docForm.getAttributeMap().put("createdDate",  StringUtils.formatDate(nbsDocDT.getAddTime()));
			 docForm.getAttributeMap().put("createdBy", helper.getUserName(nbsDocDT.getAddUserId()));
			 docForm.getAttributeMap().put("updatedDate",  StringUtils.formatDate(nbsDocDT.getLastChgTime()));
			 docForm.getAttributeMap().put("updatedBy", helper.getUserName(nbsDocDT.getLastChgUserId()));
			 
			
				
			 
			 try {
				 CommonAction ca = new CommonAction();
	
				 ArrayList<String> conditionList = new ArrayList<String>();
				 conditionList.add(nbsDocDT.getCd());
					
				 String valueSTD=ca.checkIfSyphilisIsInConditionListForCreateInv(progAreaCd, conditionList, NEDSSConstants.MORBIDITY_REPORT);
				 docForm.getAttributeMap().put("PDLogicCreateInv", valueSTD);
				 request.setAttribute("PDLogicCreateInv", valueSTD);
			 } catch(Exception e) {
				 logger.debug("Error while adding the value to PDLogicCreateInv: "+ e.getMessage());
			 }
			 
			 if (nbsDocDT.getRecordStatusCd()!=null && (nbsDocDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.OBS_PROCESSED))){
				 isProcessed = true;
			 }

			 boolean viewPerm = false;
			 boolean deletePerm = false;
			 boolean editPerm = false;
			 boolean addInvestPerm = false;
			 boolean viewWorkUpPerm = false;
			 boolean transferPerm = false;
			 boolean markAsReviewed = false;


			 UserProfile userprofile = secObj.getTheUserProfile();
			 User user = userprofile.getTheUser();
			 String userType = user.getUserType();

			 /**
			  * When you view it from ObsNeedingAssignment the progAreaCd and jurisdictionCd are null
			  */

			 if(sCurrTask.equals("ViewDocument1")) {
				 if(progAreaCd != null && (jurisdictionCd == null || jurisdictionCd.equals("")))
				 {
					 viewPerm = secObj.getPermission(NBSBOLookup.DOCUMENT,
							 NBSOperationLookup.VIEW, progAreaCd,
							 "ANY", sharedInd);
					 transferPerm = secObj.getPermission(NBSBOLookup.DOCUMENT,
							 NBSOperationLookup.
							 TRANSFERPERMISSIONS,
							 progAreaCd,
							 NBSOperationLookup.ANY);
				 }

				 else if (jurisdictionCd != null && (progAreaCd == null || progAreaCd.equals("")))
				 {
					 viewPerm = secObj.getPermission(NBSBOLookup.DOCUMENT,
							 NBSOperationLookup.VIEW, "ANY",
							 jurisdictionCd, sharedInd);
					 transferPerm = secObj.getPermission(NBSBOLookup.DOCUMENT,
							 NBSOperationLookup.
							 TRANSFERPERMISSIONS,
							 NBSOperationLookup.ANY,
							 jurisdictionCd);

				 }
				 else if((jurisdictionCd == null || jurisdictionCd.equals("")) && (progAreaCd == null|| progAreaCd.equals("")))
				 {
					 viewPerm = secObj.getPermission(NBSBOLookup.DOCUMENT,
							 NBSOperationLookup.VIEW, NBSOperationLookup.ANY,
							 NBSOperationLookup.ANY, sharedInd);
					 transferPerm = secObj.getPermission(NBSBOLookup.DOCUMENT,
							 NBSOperationLookup.
							 TRANSFERPERMISSIONS,
							 NBSOperationLookup.ANY,
							 NBSOperationLookup.ANY);
				 }

			 } else
			 {
				 viewPerm = secObj.getPermission(NBSBOLookup.DOCUMENT,
						 NBSOperationLookup.VIEW,
						 progAreaCd,
						 jurisdictionCd, sharedInd);

				 deletePerm = secObj.getPermission(NBSBOLookup.DOCUMENT,
						 NBSOperationLookup.DELETE,
						 progAreaCd,
						 jurisdictionCd, sharedInd);

				 editPerm = secObj.getPermission(NBSBOLookup.DOCUMENT,
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

				 markAsReviewed = secObj.getPermission(NBSBOLookup.DOCUMENT,
						 NBSOperationLookup.MARKREVIEWED,
						 progAreaCd,
						 jurisdictionCd, sharedInd);

				 transferPerm = secObj.getPermission(NBSBOLookup.DOCUMENT,
						 NBSOperationLookup.
						 TRANSFERPERMISSIONS,
						 progAreaCd,
						 jurisdictionCd); 
			 }

			 if (!viewPerm)
			 {
				 session.setAttribute("error",
						 "User does not have View Document Permission.");
				 throw new ServletException("User does not have View Document Permission.");
			 }

			 //buttons that a ViewDocument may have based on NBSContext
			 boolean hasMarkAsReviewed = true;
			 /* if(sCurrTask.startsWith("ViewDocument4"))
		    	hasMarkAsReviewed = false; */
			 boolean hasTransferOwnership = true;
			 boolean hasDelete = true;
			 boolean hasCreateInvestigation = true;
			 if(contextAction.equals("DocumentIDFromInv"))
				 hasCreateInvestigation = false;

			 if (contextAction.equalsIgnoreCase(NBSConstantUtil.MarkAsReviewed)||
					 contextAction.equalsIgnoreCase("ViewDoc"))//Add other contextActions if required
			 {
				 if (sCurrTask.endsWith("Doc1")) {
					 hasTransferOwnership = true;
				 }
			 }

			 if(sCurrTask != null && sCurrTask.equals("ViewDocument1"))
			 {
				 request.setAttribute("transferOwnership", "/nbs/LoadViewDoc1.do?method=transferOwnershipLoad");
				 request.setAttribute("PrintPage", "/nbs/LoadViewDoc1.do?method=loadDocument&ContextAction=PrintPage&mode=print");		    	
			 }	
			 else 
			 {
				 request.setAttribute("transferOwnership", "/nbs/Load"+ sCurrTask+ ".do?method=transferOwnershipLoad");
				 request.setAttribute("PrintPage", "/nbs/Load"+ sCurrTask+ ".do?method=loadDocument&ContextAction=PrintPage&mode=print");
				 request.setAttribute("DeleteHref", "/nbs/Load"+ sCurrTask+ ".do?method=deleteDocument&ContextAction=Delete");
				 if(sCurrTask != null && (sCurrTask.equals("ViewDocument2")||sCurrTask.equals("ViewDocument4")))
					 request.setAttribute("MarkAsReviewHref", "/nbs/Load"+ sCurrTask+ ".do?method=markAsReviewedLoad&ContextAction=MarkAsReviewed");
			 }	
             //When coming from view Investigation via queues, document cannot be deleted and investigation cannot be created
			 if(sCurrTask != null && (sCurrTask.equals("ViewDocument6") || sCurrTask.equals("ViewDocument7") || sCurrTask.equals("ViewDocument8") || sCurrTask.equals("ViewDocument9") || sCurrTask.equals("ViewDocument10") || sCurrTask.equals("ViewDocument11") || sCurrTask.equals("ViewDocument12") || sCurrTask.equals("ViewDocument13") || sCurrTask.equals("ViewDocument14"))){
				 hasDelete = false;
				 hasCreateInvestigation = false;
				 hasTransferOwnership = false;
			 }
			 String conditionCd = nbsDocDT.getCd();


			 request.setAttribute("ConditionCd", conditionCd);
			 request.setAttribute("creatInvestigationButtonHref",
					 "/nbs/Load" + sCurrTask + ".do?ContextAction=" +
							 tm.get(NBSConstantUtil.CreateInvestigation)+"&ConditionCd="+conditionCd);




			 //setting button values

			 //MarkAsReviewed Button
			 if (hasMarkAsReviewed && markAsReviewed && !isProcessed) {
				 request.setAttribute("checkMarkAsReview", "true");
			 }
			 else{
				 request.setAttribute("checkMarkAsReview", "false");
			 }

			 //TransferOwnership Button
			 if (hasTransferOwnership && transferPerm) {
				 request.setAttribute("checkTransfer", "true");
			 }
			 else{
				 request.setAttribute("checkTransfer", "false");
			 }

			 //Delete Button
			 if (hasDelete && deletePerm) {
				 request.setAttribute("checkDelete", "true");
			 }
			 else{
				 request.setAttribute("checkDelete", "false");
			 }
			 //Create Investigation Button
			 if (hasCreateInvestigation && addInvestPerm && sCurrTask!=null && !sCurrTask.equals("ViewDocument3")) {
				 request.setAttribute("checkCreateInvestigation", "true");
			 }
			 else{
				 request.setAttribute("checkCreateInvestigation", "false");
			 }

			 request.setAttribute("mode", request.getParameter("mode"));
			 if(nbsDocDT.getPayLoadTxt()!=null && nbsDocDT.getPhdcDocDerivedTxt()!=null && nbsDocDT.getPhdcDocDerivedTxt().trim().length()>0){
				 request.setAttribute("cdaLink","true");
				 //request.setAttribute("cdaLinkHref", "/nbs/Load"+ sCurrTask+ ".do?method=cdaDocumentView");
			 }

			 if(nbsDocVO.getNbsDocumentDT()!=null && nbsDocVO.getNbsDocumentDT().getDocEventTypeCd()!=null){
				 String docEventyType = nbsDocVO.getNbsDocumentDT().getDocEventTypeCd();
				 if(!docEventyType.equals(NEDSSConstants.CLASS_CD_CASE))
					 request.setAttribute("checkCreateInvestigation", "false");
			 }


			 request.setAttribute("PageTitle","View Document");
			 request.setAttribute("currentTask", sCurrTask);
			 request.setAttribute("ContextAction",contextAction);
			 if( session.getAttribute("deleteDeniedMsg") != null){ 
				 String deleteDeniedMsg = (String)session.getAttribute("deleteDeniedMsg");
				 request.setAttribute("deleteDeniedMsg", deleteDeniedMsg);
				 session.removeAttribute("deleteDeniedMsg");
			 }

			 /* We are setting it to session because after DeleteDenied 
		    the values are loosing from the request */

			 session.setAttribute("currentTask", sCurrTask);
			 session.setAttribute("ContextAction",contextAction);
			 docXSLMap = (Map<Object,Object>)QuestionsCache.getNBSDocMetadataMap();
			 // get the XSL from the database.
			 try{
				 screenXSL = getXSLFromCache(nbsDocDT.getNbsDocumentMetadataUid());
				 NBSDocumentMetadataDT nbsDocMetadataDT = (NBSDocumentMetadataDT)docXSLMap.get(nbsDocDT.getNbsDocumentMetadataUid());
				 if(nbsDocMetadataDT.getDocTypeCd().contains(NEDSSConstants.EDX_PHDC_DOC_TYPE) || nbsDocMetadataDT.getDocTypeCd().equalsIgnoreCase(NEDSSConstants.LAB_TYPE_CD))
					 printXSL = _createPrintSS(screenXSL);
			 } catch (Exception e) {
				 e.printStackTrace();
				 logger.error("Error while reading XSL in ViewDocumentLoad: " + e.toString());
			 }			 	    

			 translateXMLandSettoRequest(nbsDocDT.getPayLoadTxt(),request, contextAction);
		 } catch (Exception ex) {
			 logger.error("Error in ViewDocumentAction: "+ex.getMessage());
			 ex.printStackTrace();
		 }		    
		 return mapping.findForward("default");
	 }
	 
	 	/**
	 	 * 
	 	 * @param mapping
	 	 * @param aForm
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws IOException
	 	 * @throws ServletException
	 	 */
	 	public ActionForward markAsReviewedLoad(ActionMapping mapping, ActionForm aForm,HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {
	 		try {
	 			NbsDocumentForm docForm = (NbsDocumentForm)aForm;
	 			HttpSession session = request.getSession();
	 			logger.info("markAsReviewed() in ViewDocumentAction is being called");
	 			Long documentUid = null;
	 			documentUid = (Long)NBSContext.retrieve(session, "DSDocumentUID");
	 			if(documentUid == null )  
	 				documentUid = new Long((String)request.getAttribute("docUid"));
	 			//get the document from the NBSDocument table

	 			NBSDocumentVO nbsDocVO = new NBSDocumentVO();

	 			try{
	 				nbsDocVO = util.getNBSDocument(session,documentUid);
	 			}catch(Exception e){
		 			logger.error("Error in markAsReviewedLoad1: "+e.getMessage());
		 			e.printStackTrace();
	 				throw new ServletException(e.toString());
	 			}
	 			nbsDocVO.getNbsDocumentDT().setRecordStatusCd(NEDSSConstants.OBS_PROCESSED);
	 			Date dat = new Date();
	 			Timestamp aupdateTime = new Timestamp(dat.getTime());
	 			nbsDocVO.getNbsDocumentDT().setRecordStatusTime(aupdateTime);
	 			nbsDocVO.getNbsDocumentDT().setLastChgTime(aupdateTime);
	 			String DSDocumentLocalID = nbsDocVO.getNbsDocumentDT().getLocalId();
	 			String firstNm = nbsDocVO.getPatientVO().getThePersonDT().getFirstNm() == null ? "" : nbsDocVO.getPatientVO().getThePersonDT().getFirstNm();
	 			String	lastNm = nbsDocVO.getPatientVO().getThePersonDT().getLastNm() == null ? "" : nbsDocVO.getPatientVO().getThePersonDT().getLastNm();

	 			nbsDocVO = util.setNBSDocumentVOforUpdate(nbsDocVO);
	 			try{
	 				util.setDocumentforUpdate(session,nbsDocVO);
	 			}catch(Exception e){
		 			logger.error("Error in markAsReviewedLoad2: "+e.getMessage());
		 			e.printStackTrace();
	 				throw new ServletException(e.toString());
	 			}
	 			StringBuffer sb = new StringBuffer();
	 			if(DSDocumentLocalID != null)
	 			{
	 				sb.append("Case Report <b>").append(DSDocumentLocalID).append("</b> for <b>").append(firstNm).append("</b>  <b>").append(lastNm).append("</b> has been successfully Marked As Reviewed.");
	 				request.setAttribute("docMarkReviewConfMsg", sb.toString());
	 			}
	 			request.setAttribute("docUid",documentUid.toString());
	 		} catch (Exception ex) {
	 			logger.error("Error in markAsReviewedLoad: "+ex.getMessage());
	 			ex.printStackTrace();
	 			throw new ServletException("Error while markAsReviewedLoad : "+ex.getMessage());
	 		}
	 		return mapping.findForward("cdaview");
	 }
	 	
	public ActionForward cdaDocumentView(ActionMapping mapping,
			ActionForm aForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		request.setAttribute("PageTitle", "CDA Document Viewer");
		logger.info("cdaDocumentView() in ViewDocumentAction is being called");
		Long documentUid = new Long(request.getParameter("docId"));
		
		if(documentUid == null )
			documentUid = new Long((String)request.getAttribute("docUid"));
		if(documentUid == null )
			documentUid = (Long)NBSContext.retrieve(session, "DSDocumentUID");
		// get the document from the NBSDocument table
		NBSDocumentVO nbsDocVO = new NBSDocumentVO();
		try {
			nbsDocVO = util.getNBSDocument(session, documentUid);
			if(nbsDocVO.getNbsDocumentDT()!=null && nbsDocVO.getNbsDocumentDT().getDocEventTypeCd()!=null){
				String docEventyType = nbsDocVO.getNbsDocumentDT().getDocEventTypeCd();
				if(!docEventyType.equals(NEDSSConstants.CLASS_CD_CASE))
					request.setAttribute("checkCreateInvestigation", "false");
			}
		} catch (Exception e) {
			throw new ServletException(e.toString());
		}
		docXSLMap = (Map<Object, Object>) QuestionsCache.getNBSDocMetadataMap();
		// get the XSL from the database.
		String eventType = (String)request.getParameter("eventType");
		try {
			
			if(eventType!=null && eventType.equals(NEDSSConstants.TREATMENT_ACT_TYPE_CD))
					screenXSL = getXSLFromCache(new Long(1004));
			else
				screenXSL = getXSLFromCache(nbsDocVO.getNbsDocumentDT().getNbsDocumentMetadataUid());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while reading XSL in ViewDocumentLoad: "
					+ e.toString());
		}
		translateXMLandSettoRequest(nbsDocVO.getNbsDocumentDT().getPayLoadTxt(), request, "event");
		request.setAttribute("docUid", documentUid.toString());
		if(eventType!=null)
			return mapping.findForward("viewCDAEventPayload");
		else
			return mapping.findForward("viewCDAPayload");
	}
	
	/*Testing method, need to combine with the CDADocumentView*/
	public ActionForward originalDocumentView(ActionMapping mapping,
			ActionForm aForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		request.setAttribute("PageTitle", "eICR Document Viewer");
		logger.info("originalDocumentView() in ViewDocumentAction is being called");

		NbsDocumentForm docForm = (NbsDocumentForm)aForm;
		Long documentUid = docForm.getNbsDocumentDt().getNbsDocumentUid();
		
		if(documentUid == null )
			documentUid = (Long)NBSContext.retrieve(session, "DSDocumentUID");
		/*if(documentUid == null )
			documentUid = new Long((String)request.getAttribute("docUid"));
		if(documentUid == null )
			documentUid = (Long)NBSContext.retrieve(session, "DSDocumentUID");*/

		// get the document from the NBSDocument table
		NBSDocumentVO nbsDocVO = new NBSDocumentVO();
		try {
			nbsDocVO = util.getNBSDocument(session, documentUid);
			if(nbsDocVO.getNbsDocumentDT()!=null && nbsDocVO.getNbsDocumentDT().getDocEventTypeCd()!=null){
				String docEventyType = nbsDocVO.getNbsDocumentDT().getDocEventTypeCd();
				if(!docEventyType.equals(NEDSSConstants.CLASS_CD_CASE))
					request.setAttribute("checkCreateInvestigation", "false");
			}
		} catch (Exception e) {
			throw new ServletException(e.toString());
		}
		docXSLMap = (Map<Object, Object>) QuestionsCache.getNBSDocMetadataMap();
		// get the XSL from the database.
		String eventType = (String)request.getParameter("eventType");
		try {
				//screenXSL = getCdaXSLFromCache(nbsDocVO.getNbsDocumentDT().getNbsDocumentMetadataUid());
				screenXSL = getCdaXSLFromCache(new Long(1003));//Delete after setting above getNbsDocumentMetadataUid to 1003 as per eICR
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while reading XSL in originalDocumentView: "
					+ e.toString());
		}
		
			translateXMLandSettoRequest(nbsDocVO.getNbsDocumentDT().getPhdcDocDerivedTxt(), request, "event");
		
		request.setAttribute("docUid", documentUid.toString());
		if(eventType!=null)
			return mapping.findForward("viewCDAEventPayload");
		else
			return mapping.findForward("viewCDAPayload");
	}
	
	 	private void translateXMLandSettoRequest(String payload, HttpServletRequest request, String contextAction) throws ServletException {
	 		String xsl = screenXSL;
	 		if(contextAction != null && contextAction.equalsIgnoreCase("PrintPage"))
	 			xsl = printXSL;

			TransformerFactory tFactory = TransformerFactory.newInstance();
			try {
			
				Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(new ByteArrayInputStream(xsl.getBytes("UTF-8"))));//new ByteArrayInputStream(xsl.getBytes())
				Writer result = new StringWriter();
				PrintWriter out = new PrintWriter(result);
				InputStream is = new ByteArrayInputStream(payload.getBytes("UTF-8"));
				if(contextAction != null && contextAction.equalsIgnoreCase("event") && request.getParameter("eventId")!=null)
				transformer.setParameter("eventId", request.getParameter("eventId"));
					transformer.transform(new javax.xml.transform.stream.StreamSource(is),new javax.xml.transform.stream.StreamResult(out));
				request.setAttribute("DOC_VIEWER", result.toString());
			} catch (Exception ex) {
	 			logger.error("Error in translateXMLandSettoRequest: "+ex.getMessage());
	 			ex.printStackTrace();
				throw new ServletException(ex);
			}
	 	}
	 
	 	public ActionForward transferOwnershipLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
			NbsDocumentForm nbsDocForm = (NbsDocumentForm) form;
			try {
				HttpSession session = request.getSession();
				DocumentTransferOwnershipUtil.loadTransferOwnership(nbsDocForm, request);
				// Check to see if investigation is associated with this document
				NbsDocumentForm docForm = (NbsDocumentForm)form;
				NBSDocumentDT nbsDocDt = docForm.getNbsDocumentDt();
				Boolean isInvAsso = util.getInvestigationAssoWithDocumentColl(session, nbsDocDt.getNbsDocumentUid());
				if(isInvAsso.booleanValue())
				{
					request.setAttribute("IsInvAsso", isInvAsso);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException("Error while loading transferOwnership for PAM Case: "+e.getMessage(),e);
			}		
			return (mapping.findForward("transferOwnershipLoad"));
		}

	public ActionForward transferOwnershipSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		 	NbsDocumentForm nbsDocForm = (NbsDocumentForm) form;
			ActionForward forward;
			try {
				forward = DocumentTransferOwnershipUtil.storeTransferOwnership(mapping, nbsDocForm, request, response);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException("Error while transferOwnership for case report Case: "+e.getMessage(),e);
			}		
			return forward;
		}
	/**
	 * This method is to do the logical delete of the document
	 * @param mapping
	 * @param aForm
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward deleteDocument(ActionMapping mapping, ActionForm aForm,HttpServletRequest request,HttpServletResponse response) 
	throws IOException,ServletException {
		try {
		 NbsDocumentForm docForm = (NbsDocumentForm)aForm;
		 HttpSession session = request.getSession();
		 logger.info("deleteDocument() in ViewDocumentAction is being called");
		 String docUidStr=null;
		 if(request.getAttribute("docUid")!= null)
		    docUidStr = (String)request.getAttribute("docUid");
		 // This is for the queue - we are getting a common request parameter for observation and Document  
		 else if(request.getParameter("observationUID") != null)
			docUidStr = (String)request.getParameter("observationUID");
		 // From ViewFile, storing the dosumentUid in the session
		 if (docUidStr == null)
			 docUidStr = (String)request.getAttribute("docUid");
		
		 Long documentUid = new Long(0);	
		 if(docUidStr != null)
		 	 documentUid = new Long(docUidStr);
		 if( documentUid == null ||(documentUid != null && documentUid.compareTo(new Long(0))== 0)){
			 documentUid = (Long)NBSContext.retrieve(session, "DSDocumentUID");
			 docUidStr = documentUid.toString();
		 }
		 String sCurrTasks = (String)session.getAttribute("currentTask");
		 String sCurrTask = NBSContext.getCurrentTask(session);	
		   
		 String contextAction = (String)request.getParameter("ContextAction");
		 
		 request.setAttribute("docUid",docUidStr);
		// request.getSession().setAttribute("docUid",docUidStr);
		 //This is for the loadDocument method 
		 NBSContext.store(session, "DSObservationUID", documentUid);
		 //get the document from the NBSDocument table		
		 NBSDocumentVO nbsDocVO = new NBSDocumentVO();
		 
		 try{	
			 nbsDocVO = util.getNBSDocument(session,documentUid);
		 }catch(Exception e){
			 throw new ServletException(e.toString());
		 }
		 // Resetting to request Attribute, before it loads the page again	 
	
		 request.setAttribute("currentTask", sCurrTask);
		 request.setAttribute("ContextAction",contextAction);
		 NBSContext.store(session,"DSObservationUID",documentUid );
		 NBSContext.store(session,"DSPatientPersonUID",nbsDocVO.getPatientVO().getThePersonDT().getPersonParentUid());
		 
		 if((nbsDocVO.getActRelColl() != null && nbsDocVO.getActRelColl().size()>0)){
			 session.setAttribute("deleteDeniedMsg", "Your request to delete this Document is not possible.It may be linked to another investigation.");
			 String url = "/nbs/Load" + sCurrTask + ".do?method=loadDocument&ContextAction=DeleteDenied";
			 response.sendRedirect(url);
			 return null;
		 }
		 
		 nbsDocVO.getNbsDocumentDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE);
		 Date dat = new Date();
		 Timestamp aupdateTime = new Timestamp(dat.getTime());
		 nbsDocVO.getNbsDocumentDT().setRecordStatusTime(aupdateTime);
		 nbsDocVO.getNbsDocumentDT().setLastChgTime(aupdateTime);
		 nbsDocVO = util.setNBSDocumentVOforDelete(nbsDocVO);
		 try{
			 util.setDocumentforUpdate(session,nbsDocVO);
		 }catch(Exception e){
			 throw new ServletException(e.toString());
		 }

		}catch (Exception e) {
			logger.error("Exception in ViewDocumentAction.deleteDocument: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("View Document Action delete error: "+e.getMessage());
		}
		 return mapping.findForward("Delete");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createInvestigation(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws
    IOException, ServletException {
		NbsDocumentForm nbsDocumentForm = (NbsDocumentForm)form;
		String actionForward = "";
		try {
			String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
			request.setAttribute("currentTask", sCurrentTask);
			NBSContext.store(request.getSession(), "DSDocumentUID", nbsDocumentForm.getNbsDocumentDt().getNbsDocumentUid());
			String investigationType = request.getParameter("investigationType");
			if(investigationType!=null)
				NBSContext.store(request.getSession(), NBSConstantUtil.DSInvestigationType, investigationType);//ND-20231: Without this, the coinfection investigation was not linked to the new investigation.
    	
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error while Saving PAM Case: "+e.getMessage(),e);
		}
		
		return (mapping.findForward("CreateInvestigation"));
	}


	private static String getXSLFromCache(Long nbsDocMetadataUid) throws Exception{
		if(docXSLMap.containsKey(nbsDocMetadataUid)){
			NBSDocumentMetadataDT nbsDocMetadataDT = (NBSDocumentMetadataDT)docXSLMap.get(nbsDocMetadataUid);
			InputStream is = null;
			 int len = (int) nbsDocMetadataDT.getDocumentViewXslTxt().length();
			 byte[] bytes = new byte[len];
			try {
			  is = new ByteArrayInputStream( nbsDocMetadataDT.getDocumentViewXslTxt().getBytes("UTF-8")); 

			  is.read(bytes);
			 
			} catch (IOException e) {
		        e.printStackTrace();
		        logger.error("Error while reading XSL from map: " + nbsDocMetadataDT.getDocumentViewXsl() + ": " + e.toString());
		    }catch (Exception ex) {
		        ex.printStackTrace();
		        logger.error("Error while reading XSL from map: " + nbsDocMetadataDT.getDocumentViewXsl() + ": " + ex.toString());
		    }			
			 finally {
		        try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }
		    return new String(bytes);
			 	
		}else 
			return null;
	}
	private static String getCdaXSLFromCache(Long nbsDocMetadataUid) throws Exception{
		if(docXSLMap.containsKey(nbsDocMetadataUid)){
			NBSDocumentMetadataDT nbsDocMetadataDT = (NBSDocumentMetadataDT)docXSLMap.get(nbsDocMetadataUid);
			InputStream is = null;
			String cdaDoc = nbsDocMetadataDT.getDocumentViewCdaXslTxt();
			
			 int len = (int)cdaDoc.length();
			 byte[] bytes = new byte[len];
			try {
			  is = new ByteArrayInputStream(cdaDoc.getBytes()); 

			  is.read(bytes);
			 
			} catch (IOException e) {
		        e.printStackTrace();
		        logger.error("Error while reading XSL from map: " + nbsDocMetadataDT.getDocumentViewCdaXslTxt() + ": " + e.toString());
		    }catch (Exception ex) {
		        ex.printStackTrace();
		        logger.error("Error while reading XSL from map: " + nbsDocMetadataDT.getDocumentViewCdaXslTxt() + ": " + ex.toString());
		    }			
			 finally {
		        try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }
		    return new String(bytes);
			 	
		}else 
			return null;
	}
	private static String readFile(File file) {
	    int len = (int) file.length();
	    byte[] bytes = new byte[len];
	    FileInputStream fis = null;
	    try {
	        fis = new FileInputStream(file);
	        fis.read(bytes);
	    } catch (IOException e) {
	        e.printStackTrace();
	        logger.error("Error while readingFile: " + file + ": " + e.toString());
	    } finally {
	        try {
				fis.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    }
	    return new String(bytes);
	}	
	
	private static String _createPrintSS(String xsl) {
		StringBuffer sb = new StringBuffer();
		
		try {
			String printIcon = "<body><div class=\"printerIconBlock screenOnly\"><table role=\"presentation\" style=\"width:98%; margin:3px;\"><tr><td style=\"text-align:right; font-weight:bold;\"><a href=\"#\" onclick=\"return printPage();\"> <img src=\"printer_icon.gif\" alt=\"Print Page\" title=\"Print Page\"/> Print Page </a></td></tr></table></div><br/>";
			String printCss = "<![CDATA[#docBtnBar,#tabTLBar{display:none;}a,a:hover{color:#000;text-decoration:none;}body{font:11px arial;padding:0;margin:0;}#doc3{margin:0 auto;padding:1px;}#bd{padding:0;}table.nedssNavTable,table.nedssPageHeaderAndLogoTable,table.topButtonBar,table.bottomButtonBar,a.backToTopLink,div.requiredFieldIndicatorLink,div.returnToPageLink,a.toggleHref,table.subsect thead tr th a.toggleIconHref,table.subSectionsToggler,table.sectionsToggler{display:none;}div.infoBox{width:100%;padding:2px;margin:3px;}div.infoBox span.label,div.infoBox span.value{font-size:13px;}div.nedssLightYellowBg{background:#FFF;border-color:#000;}table.subSectionsToggler,table.sectionsToggler{margin:.15em;}div.sect div.sectBody{padding:.5em;}div.sect table.sectHeader{margin-bottom:2px;}div.sect table.sectHeader tbody tr td.sectName{color:#a46322;font-size:13px;font-weight:bold;}div.sect table.sectHeader tbody tr td.sectName a.anchor{color:#CC3300;font-size:13px;}div.sect table.sectHeader1{margin-bottom:2px;}div.sect table.sectHeader1 tbody tr td.sectName{color:#151B8D;font-size:13px;font-weight:bold;}div.sect table.sectHeader1 tbody tr td.sectName a.anchor{color:#CC3300;font-size:13px;}div.sect table.sectHeader1 tbody tr td.sectName1{color:#CC3300;font-size:13px;}table.subSect{width:100%;}table.subSect1{width:98%;margin-left:10px;margin-bottom:5px;}table.dtTable{width:98%;border-style:solid;border-width:1px;border-color:#DEDEDE;margin-left:6px;margin-bottom:5px;}table.subSect thead tr th{font-size:12px;color:#185394;font-style:italic;text-decoration:underline;}table.subSect tbody tr td{padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px;border-color:#DEDEDE;}table.subSect tbody tr td.fieldName{width:50%;font-size:11px;}table.subSect1 tbody tr td.fieldName{width:25%;font-weight:bold;font-size:11px;padding-right:3px;padding-bottom:3px;}table.subSect1 tbody tr td.fieldName1{width:25%;font-weight:bold;font-size:11px;padding-right:3px;padding-bottom:3px;text-align:right;}table.subSect1 thead tr th{padding:.15em;font-weight:bold;color:#185394;text-align:left;}table.subSect1 tbody tr td{padding-left:5px;width:25%;font-size:11px;padding-right:3px;border-color:#DEDEDE;border-width:1px;border-style:solid;}div.hiddenTab{display:block;}table tr td.ongletTextEna,table tr td.ongletTextDis,table tr td.ongletTextEna1,table tr td.ongletTextDis1,table tr td.ongletTextErr{width:8em;padding:2px;font:normal 1em Arial;margin-left:0;margin-right:10px;margin-top:-2px;border-color:#FFF;}table tr td.ongletTextEna,table tr td.ongletTextEna1{background:#FFF;color:#000;}td.ongletTextEna,td.ongletTextDis1,td.ongletTextDis,td.ongletTextErr1,td.ongletTextErr,td.ongletTextEna1{display:none;}table tr td.ongletSpace{border-width:0;}table tr td.ongletMain{padding:3px;border-width:0;}table tr td.ongletMiddle{border-width:0;}div.tabNavLinks{display:none;}table.clsAction{border-top:solid 5px #408FCF;}table.dtTable thead tr th{text-decoration:none;border:1px solid #666666;font-weight:bold;background:#EFEFEF;padding:.20em;text-align:center;font-style:normal;font-size:11px;color:#185394;}h2.printOnlyTabHeader{display:none;}div.printerIconBlock{display:block;}]]>";	//String printCss = readFile(new File(PropertyUtil.propertiesDir + (File.separator) + "phc_print.css"));
			String printCDACss = "<![CDATA[#docBtnBar,#tabTLBar{display:none;}a,a:hover{color:#000;text-decoration:none;}body{font:11px arial;padding:0;margin:0;}#doc3{margin:0 auto;padding:1px;}#bd{padding:0;}table.nedssNavTable,table.nedssPageHeaderAndLogoTable,table.topButtonBar,table.bottomButtonBar,a.backToTopLink,div.requiredFieldIndicatorLink,div.returnToPageLink,a.toggleHref,table.subsect thead tr th a.toggleIconHref,table.subSectionsToggler,table.sectionsToggler{display:none;}div.infoBox{width:100%;padding:2px;margin:3px;}div.infoBox span.label,div.infoBox span.value{font-size:13px;}div.nedssLightYellowBg{background:#FFF;border-color:#000;}table.subSectionsToggler,table.sectionsToggler{margin:.15em;}div.sect div.sectBody{padding:.5em;}div.sect table.sectHeader{margin-bottom:2px;}div.sect table.sectHeader tbody tr td.sectName{color:#a46322;font-size:13px;font-weight:bold;}div.sect table.sectHeader tbody tr td.sectName a.anchor{color:#CC3300;font-size:13px;}div.sect table.sectHeader1{margin-bottom:2px;}div.sect table.sectHeader1 tbody tr td.sectName{color:#151B8D;font-size:13px;font-weight:bold;}div.sect table.sectHeader1 tbody tr td.sectName a.anchor{color:#CC3300;font-size:13px;}div.sect table.sectHeader1 tbody tr td.sectName1{color:#CC3300;font-size:13px;}table.subSect{width:100%;}table.subSect1{width:98%;margin-left:10px;margin-bottom:5px;}table.dtTable{width:98%;border-style:solid;border-width:1px;border-color:#DEDEDE;margin-left:6px;margin-bottom:5px;}table.subSect thead tr th{font-size:12px;color:#185394;font-style:italic;text-decoration:underline;}table.subSect tbody tr td{padding-left:5px;padding-top:1px;padding-bottom:1px;background:#FFF;font-size:11px;border-color:#DEDEDE;}table.subSect tbody tr td.fieldName{width:50%;font-size:11px;}table.subSect1 tbody tr td.fieldName{width:25%;font-weight:bold;font-size:11px;padding-right:3px;padding-bottom:3px;}table.subSect1 tbody tr td.fieldName1{width:25%;font-weight:bold;font-size:11px;padding-right:3px;padding-bottom:3px;text-align:right;}table.subSect1 thead tr th{padding:.15em;font-weight:bold;color:#185394;text-align:left;}table.subSect1 tbody tr td{padding-left:5px;width:25%;font-size:11px;padding-right:3px;border-color:#DEDEDE;border-width:1px;border-style:solid;}div.hiddenTab{display:block;}table tr td.ongletTextEna,table tr td.ongletTextDis,table tr td.ongletTextEna1,table tr td.ongletTextDis1,table tr td.ongletTextErr{width:8em;padding:2px;font:normal 1em Arial;margin-left:0;margin-right:10px;margin-top:-2px;border-color:#FFF;}table tr td.ongletTextEna,table tr td.ongletTextEna1{background:#FFF;color:#000;}td.ongletTextEna,td.ongletTextDis1,td.ongletTextDis,td.ongletTextErr1,td.ongletTextErr,td.ongletTextEna1{display:none;}table tr td.ongletSpace{border-width:0;}table tr td.ongletMain{padding:3px;border-width:0;}table tr td.ongletMiddle{border-width:0;}div.tabNavLinks{display:none;}table.clsAction{border-top:solid 5px #408FCF;}table.dtTable thead tr th{text-decoration:none;border:1px solid #666666;font-weight:bold;background:#EFEFEF;padding:.20em;text-align:center;font-style:normal;font-size:11px;color:#185394;}h2.printOnlyTabHeader{display:none;}div.printerIconBlock{display:block;}]]>";	//String printCss = readFile(new File(PropertyUtil.propertiesDir + (File.separator) + "phc_print.css"));
			xsl = xsl.replaceAll("<body>",printIcon);
			int startPosCDA = xsl.indexOf("<STYLE type=\"text/css\" title=\"PHDC\"><xsl:text>");
			int startPos = xsl.indexOf("<STYLE>");
			int endPos = xsl.indexOf("</STYLE>");
			int endPosCDA = xsl.indexOf("</xsl:text></STYLE>");
			if(startPos<0 && startPosCDA>0){
				sb.append(xsl.substring(0,(startPosCDA+46)));
				sb.append(printCDACss);
				sb.append(xsl.substring(endPosCDA));
			}else if(startPos>0 && startPosCDA<0){
				sb.append(xsl.substring(0,(startPos+7)));
				sb.append(printCss);
				sb.append(xsl.substring(endPos));
			}
			//logger.debug(sb.toString());
			
		} catch (RuntimeException e) {
			e.printStackTrace();
	        logger.error("Error while creating Print Specific XSL: " + e.toString());			
		}
		return sb.toString();
	}
	
	
}
