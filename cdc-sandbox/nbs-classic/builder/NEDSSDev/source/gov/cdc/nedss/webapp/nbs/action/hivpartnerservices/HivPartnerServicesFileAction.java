 package gov.cdc.nedss.webapp.nbs.action.hivpartnerservices;

import noNamespace.XpemsPSDataDocument;
import noNamespace.XpemsPSDataDocument.XpemsPSData;
import org.apache.struts.actions.DispatchAction;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nnd.intermediarymessage.NBSNNDIntermediaryMessageDocument;
import gov.cdc.nedss.nnd.util.PartnerServicesConstants;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.hivpartnerservices.HivPartnerServicesForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.SRTValues;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


/**
 * HivPartnerServicesFileAction - Retrieves the request from the 
 * System Management/Messaging Management/Manage HIV PartnerServices File and calls backend.
 * Backend returns byte stream (or exception) that is then output.
 *  See PSData_v2.1.xsd.
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: CSRA for CDC</p>
 * Dec 22nd, 2016
 * @version 0.9
 */

public class HivPartnerServicesFileAction extends DispatchAction {

static final LogUtils logger = new LogUtils(HivPartnerServicesFileAction.class.getName());
private static PropertyUtil propertyUtil = PropertyUtil.getInstance();	
public static String PartnerServicesFileName = "";
/*
 * The HIV Partner Services File is generated twice a year only for HIV Program Area cases.
 * See http://schemas.lutherconsulting.com/
 */
	
	public ActionForward loadFileInfo(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		
		
		//XpemsPSDataDocument document = null;
		//document = XpemsPSDataDocument.Factory.newInstance();

		try {
			HivPartnerServicesForm partnerServicesForm =(HivPartnerServicesForm)form;
			partnerServicesForm.setConfirmationMessage("");
			partnerServicesForm.setContactPerson("");
			HttpSession session = request.getSession();
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.EPILINKADMIN);
		    request.setAttribute("PageTitle" ,"Manage HIV Partner Services File: Create File");

		    partnerServicesForm.getAttributeMap().put("cancel", "/nbs/SystemAdmin.do?focus=systemAdmin2");
		    partnerServicesForm.getAttributeMap().put("submit", "/nbs/ManageHivPartnerServices.do?method=submitFileInfo");
			

		} catch (Exception e) {
			logger.error("Error while loading the HIV Partner Services Form: " + e.toString());
			throw new ServletException("Error while loading the HIV Partner Services Form: "+e.getMessage(),e);
		}

		return (mapping.findForward("fileDetails"));
	}
	
	
	public ActionForward submitFileInfo(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

		try {
			HivPartnerServicesForm partnerServicesForm =(HivPartnerServicesForm)form;
			getRelatedFormCodes(partnerServicesForm, request);

			HttpSession session = request.getSession();
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,NBSOperationLookup.EDIT);
		    request.setAttribute("PageTitle" ,"Manage HIV Partner Services File: Create File");

		     String sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
	         String sMethod = "createHivPartnerServicesFile";
	         Object[] oParams = new Object[] {partnerServicesForm.getReportingMonth(),partnerServicesForm.getReportingYear(), partnerServicesForm.getContactPerson(), partnerServicesForm.getInvFormCode(), partnerServicesForm.getIxsFormCode()};
	         MainSessionHolder holder = new MainSessionHolder();
	         MainSessionCommand msCommand = holder.getMainSessionCommand(session);
	         ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	         
			byte[] theByteArray = (byte[]) arr.get(0);
			int size = theByteArray.length;
			
			logger.info("Partner Services document returned to action class - size is " +size);

				// prepare response
			String fileName = getPartnerServicesFileName(partnerServicesForm);
			PartnerServicesFileName = fileName;
			logger.info("   getting servlet context..");
			ServletContext context = request.getSession().getServletContext();
			response.setContentType("text/xml; charset=utf-8"); //should be readable
			response.setHeader("cache-control", "must-revalidate");
			response.setContentLength(size);
			response.setHeader("Content-disposition", "attachment; filename="
						+ fileName);
			logger.info("   opening response output stream..");
				// write response
			ServletOutputStream op = response.getOutputStream();
			op.write(theByteArray, 0, size);
			op.flush();
			op.close();
	         
			logger.info("   output stream closed");
	         
		} catch(NEDSSAppException nae){
			
			ActionErrors errors = (ActionErrors)request.getAttribute("error_messages");
			if(errors == null) errors = new ActionErrors();
			String errorCode = "";
			if (nae.getErrorCd().contains(PartnerServicesConstants.NO_CASES_MSG)){
				//||nae.getErrorCd().contains(PartnerServicesConstants.NO_SESSIONS_MSG)
				//||nae.getErrorCd().contains(PartnerServicesConstants.NO_INDEXES_MSG)) {
				String errMsg1 = nae.getErrorCd().split(":")[2];
				String errMsg2 = nae.getErrorCd().split(":")[4];
				errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, new ActionMessage(PartnerServicesConstants.PS_MESSAGE_RESOURSES_PROPERTY,errMsg1 +":" +errMsg2 +":59."));
			} else {
				errorCode =  nae.getErrorCd().substring(nae.getErrorCd().lastIndexOf(":") + 1, nae.getErrorCd().length());
				logger.debug("submitHivPartnerServices - exception :" + errorCode);
				errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, new ActionMessage("errors.invalid",errorCode ));
			}
			request.setAttribute("error_messages", errors);
			return (mapping.findForward("fileDetails"));
		}catch (Exception e) {
			logger.error("Unexpected error occurred while generating the HIV Partner Services file: " + e.toString());
			throw new ServletException("Error while generating HIV Partner Services File. See Log: "+e.getMessage(),e);
		}

		return null; //One request, one response - if we sent the XML file we can't send HTML.
	}
	
	public ActionForward submitFileInfoOld(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

		try {
			HivPartnerServicesForm partnerServicesForm =(HivPartnerServicesForm)form;
			getRelatedFormCodes(partnerServicesForm, request);

			HttpSession session = request.getSession();
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,NBSOperationLookup.EDIT);
		    request.setAttribute("PageTitle" ,"Manage HIV Partner Services File: Create File");

		     String sBeanJndiName = JNDINames.NND_MESSAGE_PROCESSOR_EJB_REF;
	         String sMethod = "createHivPartnerServicesFile";
	         Object[] oParams = new Object[] {partnerServicesForm.getReportingMonth(),partnerServicesForm.getReportingYear(), partnerServicesForm.getContactPerson(), partnerServicesForm.getInvFormCode(), partnerServicesForm.getIxsFormCode()};
	         MainSessionHolder holder = new MainSessionHolder();
	         MainSessionCommand msCommand = holder.getMainSessionCommand(session);
	         ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	         
			byte[] theByteArray = (byte[]) arr.get(0);
			int size = theByteArray.length;
			
			logger.info("Partner Services document returned to action class - size is " +size);

				// prepare response
			String fileName = getPartnerServicesFileName(partnerServicesForm);
			PartnerServicesFileName = fileName;
			logger.info("   getting servlet context..");
			ServletContext context = request.getSession().getServletContext();
			response.setContentType("text/xml; charset=utf-8"); //should be readable
			response.setHeader("cache-control", "must-revalidate");
			response.setContentLength(size);
			response.setHeader("Content-disposition", "attachment; filename="
						+ fileName);
			logger.info("   opening response output stream..");
				// write response
			ServletOutputStream op = response.getOutputStream();
			op.write(theByteArray, 0, size);
			op.flush();
			op.close();
	         
			logger.info("   output stream closed");
	         
		} catch(NEDSSAppException nae){
			
			ActionErrors errors = (ActionErrors)request.getAttribute("error_messages");
			if(errors == null) errors = new ActionErrors();
			String errorCode = "";
			if (nae.getErrorCd().contains(PartnerServicesConstants.NO_CASES_MSG)) {
				String errMsg1 = nae.getErrorCd().split(":")[2];
				String errMsg2 = nae.getErrorCd().split(":")[4];
				errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, new ActionMessage(PartnerServicesConstants.PS_MESSAGE_RESOURSES_PROPERTY,errMsg1 +":" +errMsg2 +":59."));
			} else {
				errorCode =  nae.getErrorCd().substring(nae.getErrorCd().lastIndexOf(":") + 1, nae.getErrorCd().length());
				logger.debug("submitHivPartnerServices - exception :" + errorCode);
				errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, new ActionMessage("errors.invalid",errorCode ));
			}
			request.setAttribute("error_messages", errors);
			return (mapping.findForward("fileDetails"));
		}catch (Exception e) {
			logger.error("Unexpected error occurred while generating the HIV Partner Services file: " + e.toString());
			throw new ServletException("Error while generating HIV Partner Services File. See Log: "+e.getMessage(),e);
		}

		return null; //One request, one response - if we sent the XML file we can't send HTML.
	}
	/**
	 * The file name has to be in a particular format. i.e. PS_13_GA_2014QTR34.xml.
	 * @param partnerServicesForm
	 * @return
	 */
	private String getPartnerServicesFileName(
			HivPartnerServicesForm partnerServicesForm) {
		//Unusual logic around month and year
		//get the current year
		String rptYearStr = "";
		String quarterStr = "";
		try {
			String reportingYear = partnerServicesForm.getReportingYear();
			String reportingMonth = partnerServicesForm.getReportingMonth();
			String currentYearStr = new SimpleDateFormat("yyyy").format(new Date());
			int curYear = Integer.parseInt(currentYearStr);
			int rptYear = Integer.parseInt(reportingYear);
			boolean priorYr = false;
			boolean presentYr = false;
			boolean futureYr = false;
			int reportYearToUse = rptYear;
			if (rptYear == curYear)
				presentYr = true;
			else if (rptYear < curYear)
				priorYr = true;
			else if (rptYear > curYear)
				futureYr = true;

			if (presentYr && reportingMonth.equals("3")) //march
				reportYearToUse = --reportYearToUse;
			else if (futureYr && reportingMonth.equals("3")) //march
				reportYearToUse = --reportYearToUse;
			else if (priorYr && reportingMonth.equals("3")) //march)
				reportYearToUse = --reportYearToUse;
			//System.out.println("Partner Services - Report year to use is: " + reportYearToUse);
			rptYearStr = String.valueOf(reportYearToUse);
			//reporting month/year indicate the date ranges
			quarterStr = "";
			if (reportingMonth.equals("3"))  {//march
				quarterStr = "34";
			}
			if (reportingMonth.equals("9")) { //sept
				quarterStr = "12";
			}
		} catch (Exception ex) {
			logger.error("Error in getPartnerServicesFileName: "+ex.getMessage());
			ex.printStackTrace();
		}
		String classCode = propertyUtil.getNBS_CLASS_CODE();
		String stateCode = propertyUtil.getNBS_STATE_CODE();
		//logger.debug("Partner Services - File name is: " + "PS_"+classCode+"_"+stateCode+"_"+rptYearStr+"QTR"+quarterStr+".xml");
		return ("PS_"+stateCode+"_"+classCode+"_"+rptYearStr+"QTR"+quarterStr+".xml");
	}
	/**
	 * We need the form codes so we can get the correct question metadata.
	 * @param partnerServicesForm
	 * @param request
	 */
	private void getRelatedFormCodes(
			HivPartnerServicesForm partnerServicesForm,
			HttpServletRequest request) {
		String phsFormCode = null;
		String interviewFormCode = null;
		try {
			String hivProgramAreas = propertyUtil.getHIVProgramAreas();
			String hivProgramArea;
			if (hivProgramAreas.contains(",")) {
				String[] areas = hivProgramAreas.split(",", 2);
				hivProgramArea = "('"  + areas[0] + "','"+ areas[1] + "')";
			} else hivProgramArea =  "('" + hivProgramAreas +  "')";

			SRTValues srtv = new SRTValues();
			TreeMap<Object, Object> programAreaTreeMap = null;
			programAreaTreeMap = srtv.getProgramAreaConditions(hivProgramArea);

			//try to find the code with AIDS first, some test environments my have many old forms..
			phsFormCode =  PageManagementCommonActionUtil.checkIfPublishedPageExists(request, NEDSSConstants.INV, "10560");
			interviewFormCode = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE, "10560");
			if (phsFormCode == null || interviewFormCode == null) {
				Iterator condIter = programAreaTreeMap.keySet().iterator();
				while (condIter.hasNext()) {
					String conditionCd = (String) condIter.next();
					if (phsFormCode == null)
						phsFormCode =  PageManagementCommonActionUtil.checkIfPublishedPageExists(request, NEDSSConstants.INV, conditionCd);
					if (interviewFormCode == null)
						interviewFormCode = PageManagementCommonActionUtil.checkIfPublishedPageExists(request, NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE, conditionCd);
				}
			}
		} catch (Exception ex) {
			logger.error("Partner Services: Error in getRelatedFormCodes: "+ex.getMessage());
			ex.printStackTrace();
		}
		if (phsFormCode != null)
			partnerServicesForm.setInvFormCode(phsFormCode);
		if (interviewFormCode != null)
			partnerServicesForm.setIxsFormCode(interviewFormCode);
	}
	
	public ActionForward returnLink(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
				return (mapping.findForward("default"));
	}
	
	
}
