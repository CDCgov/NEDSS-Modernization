package gov.cdc.nedss.webapp.nbs.action.observation.labreport;

import java.io.*;

import javax.servlet.*;

import java.util.*;

import javax.servlet.http.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.apache.struts.action.*;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.webapp.nbs.action.observation.common.LabReportFieldMappingBuilder;

/**
 * Title: AddLabReportLoad.java Description: This is a action class for the
 * structs implementation Copyright: Copyright (c) 2001 Company: Computer
 * Sciences Corporation
 * 
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class ViewLabReportSubmit extends CommonLabUtil {

  //For logging
	static final LogUtils logger = new LogUtils(
			ViewLabReportSubmit.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
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

    	
    	LabResultProxyVO labResultProxyVO = super.getLabResultProxyVO(obsUID, session);
    	List<ObservationVO> obsColl = (ArrayList<ObservationVO> ) labResultProxyVO.getTheObservationVOCollection();

    	ObservationDT tempDT = new ObservationDT();
    	ObservationDT obsDT = new ObservationDT();
    	ObservationVO obsVO = new ObservationVO();
    	Map<Object,Object> tMap = new TreeMap<Object,Object>();
    	for (Iterator<ObservationVO> iter = obsColl.iterator(); iter.hasNext(); ) {
    		obsVO = iter.next();
    		tempDT = obsVO.getTheObservationDT();
    		tMap.put(tempDT.getObservationUid(), tempDT);
    	}

    	obsDT = (ObservationDT) tMap.get(obsUID);
    	String progAreaCd = obsDT.getProgAreaCd();
    	String jurisdictionCd = obsDT.getJurisdictionCd();
    	String sharedInd = obsDT.getSharedInd();
    	String observationLocalUid = obsDT.getLocalId();

    	NBSContext.store(session, "DSObservationUID", obsUID);
    	NBSContext.store(session, "DSPatientPersonUID", mprUid);

    	try {
    		if (contextAction.equalsIgnoreCase(NBSConstantUtil.EDIT)) {
    			logger.info("\n\n####The contextaction in ViewLabReportSubmit class is :" + contextAction);
    		} else if (contextAction.equalsIgnoreCase("ReturnToFileSummary") || contextAction.equalsIgnoreCase("ViewEventsPopup")) {
    			NBSContext.store(session, NBSConstantUtil.DSFileTab, "1");
    		} else if (contextAction.equalsIgnoreCase("ReturnToFileEvents")) {
    			NBSContext.store(session, NBSConstantUtil.DSFileTab, "3");
    		} else if (contextAction.equalsIgnoreCase("ManageEvents")) {
    			String personLocalId = (String) session.getAttribute("DSPatientPersonLocalID");
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
    			if (sCurrTask.endsWith("Lab3")) {
    				NBSContext.store(session, "DSFileTab", "3");
    			} else if (sCurrTask.endsWith("Lab2")) {
    				NBSContext.store(session, NBSConstantUtil.DSFileTab, "1");
    			}
    			String personLocalId = (String) session.getAttribute("DSPatientPersonLocalID");
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
    				if(strURL.indexOf(NEDSSConstants.ManageEvents)>0){
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
    				strURL.append("?ContextAction=DeleteDenied");
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
    				String successMsg = "The Lab Report has been successfully marked as Reviewed";
    				logger.info(successMsg);
    				request.setAttribute("displayInformationExists", successMsg);
    			} else {
    				logger.info("The Lab Report was not able to be set to Processed");
    			}
    		} else if (contextAction
    				.equalsIgnoreCase(NBSConstantUtil.ClearMarkAsReviewed)) {
    			String result = clearMarkAsReviewedHandler(obsUID,
    					nbsSecurityObj, session, request, response);

    			if (result.equals(NEDSSConstants.RECORD_STATUS_UNPROCESSED)) {
    				String successMsg = "The Lab Report has been successfully marked as Unreviewed";
    				logger.info(successMsg);
    				request.setAttribute("displayInformationExists", successMsg);
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
			request.setAttribute("dateReceivedHidden", receiveDate);
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			request.setAttribute("documentViewHTML", getDocumentViewHTML((EDXDocumentDT) resultUIDArr.get(0), "view"));
			request.setAttribute("PageTitle", "View ELR Document");
			request.setAttribute("documentUid",documentUid);
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
			String receiveDate = HTMLEncoder.encodeHtml(String.valueOf(request.getParameter("dateReceivedHidden")));
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