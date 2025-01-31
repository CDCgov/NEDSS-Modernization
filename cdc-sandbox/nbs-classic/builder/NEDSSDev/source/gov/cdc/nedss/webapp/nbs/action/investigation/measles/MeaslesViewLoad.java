package gov.cdc.nedss.webapp.nbs.action.investigation.measles;
/**
 *
 * <p>Title: MeaslesViewLoad</p>
 * <p>Description: This is a Load action class for view investigation for the Hepatitis conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.WumUtil;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.CommonInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.generic.GenericInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.measles.MeaslesInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.form.investigation.InvestigationForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.SRTValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Title:        InvestigationViewLoad is a class
 * Description:  This class retrieves data from EJB and puts them into request
 *               object for use in the xml file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Jay Kim
 * @version      1.0
 */

public class MeaslesViewLoad
    extends CommonAction
{

    //For logging
    static final LogUtils logger = new LogUtils(MeaslesViewLoad.class.getName());

     /**
       * This is constructor
       *
       */
    public MeaslesViewLoad()
    {
    }

    /**
      * Get values from investigation form and forward to next action.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  ActionForward Object
      * @throws ServletException and IOException
      */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {

	//long loadStartTime = new Date().getTime();
	logger.debug("inside the investigation Load");

	HttpSession session = request.getSession(false);


	if (session == null)
	{
	    logger.debug("error no session");
	    throw new ServletException("error no session");
	}

	//context
	String sContextAction = request.getParameter("ContextAction");
	logger.info("sContextAction in InvestigationLoad = " + sContextAction);

	if (sContextAction == null)
	{
	    logger.error("sContextAction is null");
	    session.setAttribute("error", "No Context Action in InvestigationLoad");
	    throw new ServletException("sContextAction is null");
	}
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

	boolean checkInvestigationAutoCreatePermission = nbsSecurityObj.getPermission(
								 NBSBOLookup.INVESTIGATION,
								 NBSOperationLookup.AUTOCREATE,
								 ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
								 ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
								 ProgramAreaJurisdictionUtil.SHAREDISTRUE);

	 boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
								 NBSOperationLookup.VIEW);

	if (sContextAction.equals(NBSConstantUtil.SubmitNoViewAccess) || (checkInvestigationAutoCreatePermission &&!viewInvestigation))
	{
//	   System.out.println( "before page display of viewConfirmationPage in execute ContextAction: " +   sContextAction);
	    return this.viewConfirmationPage(mapping, form, request, session);
	}
	else if (sContextAction.equals(NBSConstantUtil.SORT))
	{
	    logger.debug(   "before page display of sortOnView in execute ContextAction: " +   sContextAction);
	    return this.sortOnInvestigationView(mapping, form, request, session);
	}
	else
	{

            logger.debug( "before page display of ViewInvestigation in execute ContextAction: |" + sContextAction + "|");
            return this.viewInvestigation(mapping, form, request, session);
	}
    }

    /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      * @throws ServletException
      */
    private ActionForward viewInvestigation(ActionMapping mapping, ActionForm form,
					    HttpServletRequest request, HttpSession session)
				     throws ServletException
    {

	long loadStartTime = new Date().getTime();

	//to set securitypermissions for investgations
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

	
	 boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
								 NBSOperationLookup.VIEW);


	if (!viewInvestigation)
	{
	    logger.fatal("Do not have permission to view intervention.");
	    session.setAttribute("Error", " No permission to view intervention.");

	    throw new ServletException("Do not have permission to view intervention.");
	}

	Map<Object, Object> map = this.setContextForView(request, session);
	
	String sCurrentTask = null;
	
	if(map.get("sCurrentTask") != null)
		sCurrentTask = (String)map.get("sCurrentTask");
	
	String contactCaseUrl="";
	String 	viewFileUrl ="";
	if(map.get("urlForViewFile")!=null){
		viewFileUrl =map.get("urlForViewFile").toString();
	}
	if(map.get("ContactCase")!=null){
		contactCaseUrl =map.get("ContactCase").toString();

	}

	InvestigationForm investigationForm = (InvestigationForm)form;
	String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
	InvestigationProxyVO investigationProxyVO = this.getOldProxyObject(sPublicHealthCaseUID,  investigationForm,  session);

        //investigationForm.setObservationMap(super.mapObsQA(investigationProxyVO.getTheObservationVOCollection()));

       	logger.debug(" InvestigationProxyVO: " + investigationProxyVO);
	////##!!  VOTester.createReport(investigationProxyVO, "view-load");

	if (investigationProxyVO != null)
	{

	    Collection<Object>  notificationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheNotificationSummaryVOCollection();
	    Collection<Object>  observationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheObservationSummaryVOCollection();
	    String sortMethod = "getAddTime";
	    logger.debug("Sort method is:" + sortMethod);

	    if (sortMethod != null && notificationSummaryVOCollection  != null &&
		notificationSummaryVOCollection.size() > 0)
	    {
		logger.debug("List is : " + notificationSummaryVOCollection);

		NedssUtils util = new NedssUtils();
		util.sortObjectByColumn(sortMethod, notificationSummaryVOCollection);
	    }

	    if (sortMethod != null && observationSummaryVOCollection  != null &&
		observationSummaryVOCollection.size() > 0)
	    {
		logger.debug("Obs list is : " + observationSummaryVOCollection);

		NedssUtils util = new NedssUtils();
		util.sortObjectByColumn(sortMethod, observationSummaryVOCollection);
	    }
	}

	String investigationFormCd = convertProxyToRequestObj(investigationForm, request);
	logger.debug(" InvestigationProxyVO.isItDirty: " + investigationProxyVO.isItDirty() + " InvestigationProxyVO.isItNew: " + investigationProxyVO.isItNew());

	Date investigationEndTime = new Date();
	Long investigationStartTime = (Long)session.getAttribute("investigationStartTime");
	long difference;

	if (investigationStartTime != null)
	    difference = investigationEndTime.getTime() - investigationStartTime.longValue();
	else
	    difference = investigationEndTime.getTime() - loadStartTime;

	logger.warn("total time taken for investigation: " + difference);

	if (investigationFormCd == null)
	{
	    logger.info("Done getting data for view investigation");
	    session.setAttribute("error", "Could not get conditionCd for view InvestigationView or Invalid ConditionCd");
	    return (mapping.findForward("error"));
	}


	String sContextAction = request.getParameter("ContextAction");
	request.setAttribute("sCurrentTask", sCurrentTask);
	logger.debug(" before display of viewMeasles page");
	return (this.getForwardPage(investigationFormCd, sCurrentTask, sContextAction,nbsSecurityObj, viewFileUrl, contactCaseUrl,session));
    }


    /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      * @throws ServletException
      */
    private ActionForward sortOnView(ActionMapping mapping, ActionForm form,
					    HttpServletRequest request, HttpSession session)
				     throws ServletException
    {
    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	long loadStartTime = new Date().getTime();


	InvestigationForm investigationForm = (InvestigationForm)form;
	String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();
	logger.debug("execute() InvestigationProxyVO: " + investigationProxyVO);



	if (investigationProxyVO != null)
	{

	    Collection<Object>  notificationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheNotificationSummaryVOCollection();
	    Collection<Object>  observationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheObservationSummaryVOCollection();
	    String sortMethod = request.getParameter("SortMethod");
	    logger.debug("Sort method is:" + sortMethod);

	    if (sortMethod != null && notificationSummaryVOCollection  != null &&
		notificationSummaryVOCollection.size() > 0)
	    {
		logger.debug("List is : " + notificationSummaryVOCollection);

		NedssUtils util = new NedssUtils();
		util.sortObjectByColumn(sortMethod, notificationSummaryVOCollection);
	    }

	    if (sortMethod != null && observationSummaryVOCollection  != null &&
		observationSummaryVOCollection.size() > 0)
	    {
		logger.debug("Obs list is : " + observationSummaryVOCollection);

		NedssUtils util = new NedssUtils();
		util.sortObjectByColumn(sortMethod, observationSummaryVOCollection);
	    }
	}

	String investigationFormCd = this.convertProxyToRequestObj(investigationForm, request);
	logger.debug(
		" InvestigationProxyVO.isItDirty: " + investigationProxyVO.isItDirty() +
		" InvestigationProxyVO.isItNew: " + investigationProxyVO.isItNew());

	Date investigationEndTime = new Date();
	Long investigationStartTime = (Long)session.getAttribute("investigationStartTime");
	long difference;

	if (investigationStartTime != null)
	    difference = investigationEndTime.getTime() - investigationStartTime.longValue();
	else
	    difference = investigationEndTime.getTime() - loadStartTime;

	logger.warn("total time taken for investigation: " + difference);

	if (investigationFormCd == null)
	{
	    logger.info("Done getting data for view investigation");
	    session.setAttribute("error", "Could not get conditionCd for view InvestigationMeasles or Invalid ConditionCd");

	    return (mapping.findForward("error"));
	}

	Map<Object, Object> map = this.setContextForView(request, session);
	
	String sCurrentTask = null;
	
	if(map.get("sCurrentTask") != null)
		sCurrentTask = (String)map.get("sCurrentTask");
	String sContextAction = request.getParameter("ContextAction");
	
	String contactCaseUrl="";
	String 	viewFileUrl ="";
	if(map.get("urlForViewFile")!=null){
		viewFileUrl =map.get("urlForViewFile").toString();
	}
	if(map.get("ContactCase")!=null){
		contactCaseUrl =map.get("ContactCase").toString();

	}
	logger.debug(" before display of createMeasles page");

	return (this.getForwardPage(investigationFormCd, sCurrentTask, sContextAction,nbsSecurityObj, viewFileUrl, contactCaseUrl,session));
    }


    /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      * @throws ServletException
      */
    private ActionForward viewConfirmationPage(ActionMapping mapping, ActionForm form,
					       HttpServletRequest request, HttpSession session)
					throws ServletException
    {

	long loadStartTime = new Date().getTime();
	String investigationId = "";

	//to set securitypermissions for investgations
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	String sContextAction = request.getParameter("ContextAction");

	TreeMap<Object, Object> tm = NBSContext.getPageContext(session, "PS155", sContextAction);
	String sCurrentTask = NBSContext.getCurrentTask(session);
	String investigationIdContext  =  (String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationUid);
	Long investigationUID = new Long(investigationIdContext);
	String jurisdiction = (String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationJurisdiction);
	String programArea = (String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationProgramArea);
	request.setAttribute("homeHref",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Home"));
	// Here we are getting the publicHealthCaseLocalID .. Have to rename InvestigationUid to InvestigationLocalId
	try
	{
	    investigationId = this.getPublicHealthCaseLocalID(investigationUID,session,request);
	}
	catch( Exception e)
	{
		logger.error("Exception in 	Measles View Load: " + e.getMessage());
		e.printStackTrace();
	    throw new ServletException(e.toString());
	}
	logger.debug("The value of local Id "+ investigationId);
	request.setAttribute("InvestigationUid", investigationId);
	request.setAttribute("programArea", programArea);
	request.setAttribute("jurisdiction", jurisdiction);

	return mapping.findForward("XSP");
    }


     /**
      * Get Public Health Case LocalID from session object.
      * @param Long the investigationId
      * @param HttpSession the session
      * @param HttpServletRequest the request
      * @return  String with value of Public Health Case Local id
      * @throws Exception
      */
     private String getPublicHealthCaseLocalID(Long investigationId,
					     HttpSession session, HttpServletRequest request)
				      throws Exception
    {

	MainSessionCommand msCommand = null;
	String publicHealthCaseLocalID = null;

	try
	{

	    String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
	    String sMethod = "publicHealthCaseLocalID";
	    logger.debug("sending investigationProxyVO to investigationproxyejb, via mainsession");

	    Object[] oParams = { investigationId };
	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);
	    logger.info("mscommand in InvestigationCreate No View Submit class is: " + msCommand);
	    ArrayList<?> resultUIDArr = new ArrayList<Object> ();
	    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

	    if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
	    {
		publicHealthCaseLocalID = (String)resultUIDArr.get(0);
	    }

	}
	catch (Exception e)
	{
	    logger.fatal("ERROR calling mainsession control", e.getMessage());
	    e.printStackTrace();
	    throw e;
	}

	return publicHealthCaseLocalID;
    }



     /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      */
    private ActionForward sortNotifications(ActionMapping mapping, ActionForm form,
					    HttpServletRequest request, HttpSession session) throws ServletException
    {

    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	String notifSortDirection = request.getParameter("direction");
	boolean notifDirectionFlag = true;
	if(notifSortDirection!=null && notifSortDirection.equals("false")){
	    notifDirectionFlag = false;
	    session.setAttribute("sortDirection", new Boolean(true));
	}
	else{
	    notifDirectionFlag = true;
	    logger.debug("Yes the sortDirection i s::::::=" +notifDirectionFlag);
	    session.setAttribute("sortDirection", new Boolean(false));
	}
	InvestigationForm investigationForm = (InvestigationForm)form;
	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();

	if (investigationProxyVO != null)
	{

	    Collection<Object>  notificationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheNotificationSummaryVOCollection();
	    String sortMethod = request.getParameter("SortMethod");
	    logger.debug("Sort method is:" + sortMethod);
	    logger.debug("The Sort method ..."+sortMethod);

	    if (sortMethod != null && notificationSummaryVOCollection  != null &&
		notificationSummaryVOCollection.size() > 0)
	    {
		logger.debug("List Notifications on Investigation : " + notificationSummaryVOCollection);

		NedssUtils util = new NedssUtils();
		util.sortObjectByColumn(sortMethod, notificationSummaryVOCollection,notifDirectionFlag);
	    }
	}

	String investigationFormCd = convertProxyToRequestObj(investigationForm, request);

	if (investigationFormCd == null)
	{
	    logger.info("Done getting data for view investigation");

	    return (mapping.findForward("error"));
	}

	Map<Object, Object> map = this.setContextForView(request, session);
	String sCurrentTask= null;
	if(map.get("sCurrentTask") != null)
		sCurrentTask = (String)map.get("sCurrentTask");
	String sContextAction = request.getParameter("ContextAction");
	String contactCaseUrl="";
	String 	viewFileUrl ="";
	if(map.get("urlForViewFile")!=null){
		viewFileUrl =map.get("urlForViewFile").toString();
	}
	if(map.get("ContactCase")!=null){
		contactCaseUrl =map.get("ContactCase").toString();

	}
	logger.debug(
		"before display of viewInvestigation page after sortNotification sContextAction: " +
		sContextAction + " sCurrentTask: " + sCurrentTask);

	return (this.getForwardPage(investigationFormCd, sCurrentTask, sContextAction,nbsSecurityObj, viewFileUrl, contactCaseUrl,session));
    }


     /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      */
    private ActionForward sortObservations(ActionMapping mapping, ActionForm form,
					   HttpServletRequest request, HttpSession session) throws ServletException
    {
    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	String notifSortDirection = request.getParameter("direction");
	boolean notifDirectionFlag = true;
	if(notifSortDirection!=null && notifSortDirection.equals("false")){
	    notifDirectionFlag = false;
	    session.setAttribute("sortDirection", new Boolean(true));
	}
	else{
	    notifDirectionFlag = true;
	    logger.debug("Yes the sortDirection i s::::::=" +notifDirectionFlag);
	    session.setAttribute("sortDirection", new Boolean(false));
	}
	InvestigationForm investigationForm = (InvestigationForm)form;
	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();

	if (investigationProxyVO != null)
	{

	    Collection<Object>  observationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheObservationSummaryVOCollection();

	    //Set setCdDescTxt attributes
	    //ObservationSummaryVO obsVO = null;
	   Iterator<Object>  anIterator = null;

	    if (observationSummaryVOCollection  != null)
	    {

		CachedDropDownValues cdv = new CachedDropDownValues();

		for (anIterator = observationSummaryVOCollection.iterator(); anIterator.hasNext();)
		{
		    ObservationSummaryVO obsVO = (ObservationSummaryVO)anIterator.next();
		    if(obsVO.getCdDescTxt()==null || obsVO.getCdDescTxt().trim().length()==0)
		      obsVO.setCdDescTxt(cdv.getDescForCode("LAB_TEST", obsVO.getCd()));
		}
	    }

	    String sortMethod = request.getParameter("SortMethod");
	    logger.debug("Sort method is:" + sortMethod);

	    if (sortMethod != null && observationSummaryVOCollection  != null &&
		observationSummaryVOCollection.size() > 0)
	    {
		logger.debug("List is : " + observationSummaryVOCollection);

		NedssUtils util = new NedssUtils();
		util.sortObjectByColumn(sortMethod, observationSummaryVOCollection,notifDirectionFlag);
	    }
	}

	String investigationFormCd = convertProxyToRequestObj(investigationForm, request);

	if (investigationFormCd == null)
	{
	    logger.info("Done getting data for view investigation");

	    return (mapping.findForward("error"));
	}

	Map<Object, Object> map = this.setContextForView(request, session);
	String sCurrentTask = null;
	
	if(map.get("sCurrentTask") != null)
		sCurrentTask = (String)map.get("sCurrentTask");
	String sContextAction = request.getParameter("ContextAction");
	String contactCaseUrl="";
	String 	viewFileUrl ="";
	if(map.get("urlForViewFile")!=null){
		viewFileUrl =map.get("urlForViewFile").toString();
	}
	if(map.get("ContactCase")!=null){
		contactCaseUrl =map.get("ContactCase").toString();

	}
	logger.debug(
		" before display of viewInvestigation page after sortObservation sContextAction: " +
		sContextAction + " sCurrentTask: " + sCurrentTask);

	return (this.getForwardPage(investigationFormCd, sCurrentTask, sContextAction, nbsSecurityObj, viewFileUrl, contactCaseUrl,session));
    }


     /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      */
    private ActionForward sortVaccinations(ActionMapping mapping, ActionForm form,
					   HttpServletRequest request, HttpSession session) throws ServletException
    {
    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	String notifSortDirection = request.getParameter("direction");
	boolean notifDirectionFlag = true;
	if(notifSortDirection!=null && notifSortDirection.equals("false")){
	    notifDirectionFlag = false;
	    session.setAttribute("sortDirection", new Boolean(true));
	}
	else{
	    notifDirectionFlag = true;
	    logger.debug("Yes the sortDirection i s::::::=" +notifDirectionFlag);
	    session.setAttribute("sortDirection", new Boolean(false));
	}
	InvestigationForm investigationForm = (InvestigationForm)form;
	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();

	if (investigationProxyVO != null)
	{

	    Collection<Object>  vaccinationSummaryVOCollection  = (ArrayList<Object> )investigationProxyVO.getTheVaccinationSummaryVOCollection();
	    String sortMethod = request.getParameter("SortMethod");

	    if (sortMethod != null && vaccinationSummaryVOCollection  != null &&
		vaccinationSummaryVOCollection.size() > 0)
	    {
		logger.debug("List of Vaccinations on Investigation : " + vaccinationSummaryVOCollection);

		NedssUtils util = new NedssUtils();
		util.sortObjectByColumn(sortMethod, vaccinationSummaryVOCollection,notifDirectionFlag);
	    }
	}

	String investigationFormCd = convertProxyToRequestObj(investigationForm, request);

	if (investigationFormCd == null)
	{
	    logger.info("Done getting data for view investigation");

	    return (mapping.findForward("error"));
	}

	Map<Object, Object> map = this.setContextForView(request, session);
	
	String sCurrentTask = null;
	
	if(map.get("sCurrentTask") != null)
		sCurrentTask = (String)map.get("sCurrentTask");
	String sContextAction = request.getParameter("ContextAction");
	String contactCaseUrl="";
	String 	viewFileUrl ="";
	if(map.get("urlForViewFile")!=null){
		viewFileUrl =map.get("urlForViewFile").toString();
	}
	if(map.get("ContactCase")!=null){
		contactCaseUrl =map.get("ContactCase").toString();

	}
	logger.debug(
		" before display of viewInvestigation page after sortVacination sContextAction: " +
		sContextAction + " sCurrentTask: " + sCurrentTask);

	return (this.getForwardPage(investigationFormCd, sCurrentTask, sContextAction,nbsSecurityObj, viewFileUrl, contactCaseUrl,session));
    }


     /**
      * Get InvestigationProxyVO from the session object.
      * @param String the sPublicHealthCaseUID
      * @param HttpSession the session
      * @param HttpServletRequest the request
      * @return  InvestigationProxyVO
      */
    private InvestigationProxyVO getOldProxyObject(String sPublicHealthCaseUID,
						   InvestigationForm investigationForm,
						   HttpSession session)
    {

	InvestigationProxyVO proxy = null;
	MainSessionCommand msCommand = null;

	    try
	    {
              //long beforeLoabObjClass = new Date().getTime();
              //long timeDiff = 0;


		Long publicHealthCaseUID = new Long(sPublicHealthCaseUID);
		String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
		String sMethod = "getInvestigationProxy";
		Object[] oParams = new Object[] { publicHealthCaseUID };

		//  if(msCommand == null)
		//{
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);

		// }
		ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                //long afterLoabObjClass = new Date().getTime();
                //timeDiff = afterLoabObjClass - beforeLoabObjClass;
                //System.out.println(" load proxy from backend : " + timeDiff);


		proxy = (InvestigationProxyVO)arr.get(0);
		investigationForm.reset();
		investigationForm.setOldProxy(proxy);
		//investigationForm.setProxy(proxy);
                PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT,proxy);
                investigationForm.setPatient(personVO);
                investigationForm.setOldRevision((PersonVO)personVO.deepCopy());

		investigationForm.setLoadedFromDB(true);
	    }
	    catch (Exception ex)
	    {
		logger.fatal("getOldProxyObject: ", ex);
	    }

	return proxy;
    }



     /**
      * Get investigationForm from the servlet request object for next call.
      * @param InvestigationForm the investigationForm
      * @param HttpServletRequest the request
     * @throws ServletException 
      */
    private String convertProxyToRequestObj(InvestigationForm investigationForm,
					    HttpServletRequest request) throws ServletException
    {

	String conditionCd = "";
	String investigationFormCd = "";
	InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();
        CachedDropDownValues cdv = new CachedDropDownValues();

	HttpSession session = request.getSession(false);
	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	this.setSecurityForView(investigationProxyVO, request, nbsSecurityObj);
        //added code for ldf
        //createXSP("PHC",investigationForm.getOldProxy(),investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd(),request);
          String businessObjNm = cdv.getLDFMap(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
          String code = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
          if(code.equalsIgnoreCase("999999"))
          {
            businessObjNm = NEDSSConstants.INVESTIGATION_HEP_LDF;
          }

          request.setAttribute("businessObjNm", businessObjNm);
          request.setAttribute("showConditionSpecificLDF", new Boolean(false));
          request.setAttribute( NEDSSConstants.INVESTIGATION_HEP_LDF+"extXSP", null);

          createXSP(businessObjNm, investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), investigationProxyVO,investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd(),request);
          createXSP(NEDSSConstants.PATIENT_LDF, investigationForm.getOldRevision().getThePersonDT().getPersonUid(), investigationForm.getOldRevision(), null, request);
      //createXSP(businessObjNm,investigationProxyVO,null,request);

        Long mprUid = (Long) getPersonVO(NEDSSConstants.PHC_PATIENT, investigationProxyVO).getThePersonDT().getPersonParentUid();
        NBSContext.store(session, "DSPatientPersonLocalID", getPersonVO(NEDSSConstants.PHC_PATIENT, investigationProxyVO).getThePersonDT().getLocalId());

        //display revision patient tab
        GenericInvestigationUtil util = new GenericInvestigationUtil();
        try {
        util.displayRevisionPatient(investigationProxyVO, request);
        }catch(Exception ex) {
			throw new ServletException(ex.getMessage());
		}

	ProgramAreaVO programAreaVO = null;
	SRTValues srtv = new SRTValues();
	String programAreaCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
        NBSContext.store(session, NBSConstantUtil.DSProgramArea, programAreaCd);

      conditionCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
	logger.debug("Value of coditionCD: " + conditionCd);
	logger.debug("Value of programAreaCd: " + programAreaCd);
	TreeMap<Object, Object> programAreaTreeMap = srtv.getProgramAreaConditions("('" + programAreaCd + "')");
	TreeMap<Object, Object> programAreaTreeMapWithLevelInd2 = srtv.getProgramAreaConditions("('" + programAreaCd + "')", 2);
	if (programAreaTreeMap != null)
	{
	    programAreaVO = (ProgramAreaVO)programAreaTreeMap.get(conditionCd);
	    if(programAreaVO != null)
	    {
	    investigationFormCd = programAreaVO.getInvestigationFormCd();
	    logger.debug( "investigationFormCd: " + programAreaVO.getInvestigationFormCd() +   " programAreaDescTxt: " + programAreaVO.getStateProgAreaCdDesc());
	    }
	    else
	    {
	      programAreaVO = (ProgramAreaVO)programAreaTreeMapWithLevelInd2.get(conditionCd);
	      logger.debug("programAreaVO in InvestigationViewLoad:" + programAreaVO);
	      investigationFormCd = programAreaVO.getInvestigationFormCd();
	      logger.debug("in else :investigationFormCd: " + programAreaVO.getInvestigationFormCd() +   " programAreaDescTxt: " + programAreaVO.getStateProgAreaCdDesc());
	    }
	}

       String sCurrentTask = NBSContext.getCurrentTask(session);
       logger.info("CurrentTask before getPersonSummaryVO: " + sCurrentTask);


	this.setABCStateIndicator(request);
	logger.debug("investigationFormCd before entering loop:"+investigationFormCd);
        if(investigationFormCd.startsWith("INV_FORM_HEP"))
        {
          String programAreaFromSecurityObj = WumUtil.getProgramAreaFromSecurityObj(nbsSecurityObj);
          String filterDiagnosisCondition = cdv.getDiagnosisCodeFilteredOnPA(programAreaFromSecurityObj);
          request.setAttribute("filterDiagnosisCondition", filterDiagnosisCondition);
        }

	if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_MEA))
	{
	    MeaslesInvestigationUtil measlesInvestigationUtil = new MeaslesInvestigationUtil();
	    measlesInvestigationUtil.setMeaslesRequestForView(investigationProxyVO, request);
            measlesInvestigationUtil.getVaccinationSummaryRecords(mprUid,request);
	}
	return investigationFormCd;
    }



     /**
      * To check permission action for next level from NBSSecurityObj.
      * @param HttpServletRequest the request
      * @param NBSSecurityObj the nbsSecurityObj
      */
    private void setSecurityForView(InvestigationProxyVO investigationProxyVO, HttpServletRequest request, NBSSecurityObj nbsSecurityObj)
    {
   	String sharedInd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getSharedInd();

	//created required checks for security in the front end
	boolean checkViewFilePermission = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
								       NBSOperationLookup.VIEWWORKUP);
	boolean checkViewEditInvPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
						NBSOperationLookup.EDIT, investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
						investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
	boolean checkViewTransferPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
									  NBSOperationLookup.TRANSFERPERMISSIONS,investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
										investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
		 
	boolean checkViewVaccPermission = nbsSecurityObj.getPermission(
						  NBSBOLookup.INTERVENTIONVACCINERECORD,
						  NBSOperationLookup.VIEW);
	boolean checkViewObsLabPermission = nbsSecurityObj.getPermission(
						    NBSBOLookup.OBSERVATIONLABREPORT,
						    NBSOperationLookup.VIEW);
	boolean checkViewObsMorbPermission = nbsSecurityObj.getPermission(
						    NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
						    NBSOperationLookup.VIEW);

	boolean deleteInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
            NBSOperationLookup.DELETE,
            investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
            investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
	request.setAttribute("deleteInvestigation", String.valueOf(deleteInvestigation));

      boolean assocLabPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
              NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS,
              investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
              investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
      boolean assocMorbPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
              NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS,
              investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
              investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

	boolean checkViewEditObsLabPermission = nbsSecurityObj.getPermission(
							NBSBOLookup.OBSERVATIONLABREPORT,
							NBSOperationLookup.EDIT);
	boolean checkViewEditVaccPermission = nbsSecurityObj.getPermission(
						      NBSBOLookup.INTERVENTIONVACCINERECORD,
						      NBSOperationLookup.EDIT);

      boolean checkCreateNotific = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
    		  NBSOperationLookup.CREATE, investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
              investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);


      boolean checkCreateNeedsApprovalNotific = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
						      NBSOperationLookup.CREATENEEDSAPPROVAL,investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
				              investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
      
      boolean checkCreateCaseRep = nbsSecurityObj.getPermission(NBSBOLookup.CASEREPORTING,
				    		  NBSOperationLookup.CREATE, investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
				              investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
      
      boolean checkCreateNeedsApprovalCaseRep = nbsSecurityObj.getPermission(NBSBOLookup.CASEREPORTING,
		      NBSOperationLookup.CREATENEEDSAPPROVAL,investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
              investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

      boolean bManageTreatment = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
    		  							NBSOperationLookup.ASSOCIATETREATMENTS,
    		  							investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
    		  							investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

    boolean manageVaccination = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
    								NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS,
    								investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
    								investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);


    CommonInvestigationUtil util = new CommonInvestigationUtil();

	//To check permission for file link
	request.setAttribute("checkFile", String.valueOf(checkViewFilePermission));

	//to check security for Manage Vaccination record button to be displayed
	//if (checkViewEditInvPermission && checkViewVaccPermission)

	    request.setAttribute("checkManageVacc",
				 String.valueOf(manageVaccination));
	//to check security for Transfer Ownership button to be displayed
	    boolean showTransferOwnershipButton = util.showTransferOwnerShipButton(investigationProxyVO, nbsSecurityObj);
	   request.setAttribute("checkTransfer",
				 String.valueOf(checkViewTransferPermission && showTransferOwnershipButton ));
	   

        request.setAttribute("checkManageEvents", String.valueOf(assocLabPermission || assocMorbPermission||manageVaccination ||bManageTreatment));


	//to check security for manage Notifications button to be displayed
      //  //##!! System.out.println("checkCreateNotific: " + checkCreateNotific);
     //   //##!! System.out.println("checkCreateNeedsApprovalNotific: " + checkCreateNeedsApprovalNotific);

     // When auto resend ind on Notification table is true, Create Notification button shall not be displayed)

     boolean showNotificationCreateButton = util.showCreateNotificationButton(investigationProxyVO, nbsSecurityObj);
     //System.out.println("Boolean Checking Create Notific button show = " + String.valueOf(showNotificationCreateButton));
     request.setAttribute("checkManageNotific", String.valueOf((checkCreateNotific || checkCreateNeedsApprovalNotific) &&(showNotificationCreateButton)));
     
     request.setAttribute("checkCaseReporting", String.valueOf((checkCreateCaseRep || checkCreateNeedsApprovalCaseRep)));


	//to check for Edit button to be displayed or not
	request.setAttribute("editInv", String.valueOf(checkViewEditInvPermission));

	//to check for Observation Display box to be displayed or not
	request.setAttribute("ObsDisplay", String.valueOf(checkViewObsLabPermission));

	//to check for Observation Display box EDIT hyperlink to be displayed or not
	request.setAttribute("ObsEdit", String.valueOf(checkViewEditObsLabPermission));

	//to check for vaccination record box to be displayed
	request.setAttribute("VaccDisplay", String.valueOf(checkViewVaccPermission));

	//to check for edit hyperlink vaccination record to be displayed
	request.setAttribute("VaccEdit", String.valueOf(checkViewEditVaccPermission));
    }


     /**
      * To set context action for create.
      * @param HttpServletRequest the request
      * @param NBSSecurityObj the nbsSecurityObj
      * @return  String as context
      */
    private String setContextForCreate(HttpServletRequest request, HttpSession session)
    {

	String passedContextAction = request.getParameter("ContextAction");
	TreeMap<Object, Object> tm = NBSContext.getPageContext(session, "PS020", passedContextAction);
	String sCurrentTask = NBSContext.getCurrentTask(session);
	request.setAttribute("CurrentTask", sCurrentTask);
	request.setAttribute("Submit",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Submit"));
	request.setAttribute("Cancel",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Cancel"));
	request.setAttribute("SubmitNoViewAccess",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("SubmitNoViewAccess"));

	return sCurrentTask;
    }



      /**
      * To set context action for view.
      * @param HttpServletRequest the request
      * @param NBSSecurityObj the nbsSecurityObj
      * @return  String as context
      */
    private  Map<Object, Object> setContextForView(HttpServletRequest request, HttpSession session)
    {
    Map<Object, Object> map =new HashMap<Object, Object> ();
	String passedContextAction = request.getParameter("ContextAction");
	TreeMap<Object, Object> tm = NBSContext.getPageContext(session, "PS036", passedContextAction);
	String urlForViewFile=tm.get("ReturnToContactFileEvents").toString();
	String urlFOrContactCase=tm.get("ContactCase").toString();
	map.put("urlForViewFile", urlForViewFile);
	map.put("ContactCase", urlFOrContactCase);
        ErrorMessageHelper.setErrMsgToRequest(request);
	String sCurrentTask = NBSContext.getCurrentTask(session);
	map.put("sCurrentTask", sCurrentTask);
	request.setAttribute("CurrentTask", sCurrentTask);
	request.setAttribute("Submit",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Submit"));
	request.setAttribute("Cancel",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Cancel"));
	  request.setAttribute("deleteButtonHref",
	  		     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Cancel")+"&delete="+"true");
	request.setAttribute("SubmitNoViewAccess",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("SubmitNoViewAccess"));
	request.setAttribute("Sort",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Sort"));
	request.setAttribute("InvestigationID",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("InvestigationID"));
	request.setAttribute("ReturnToViewInvestigation",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("ReturnToViewInvestigation"));
	request.setAttribute("InvestigationIDOnSummary",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("InvestigationIDOnSummary"));
	request.setAttribute("InvestigationIDOnInv",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("InvestigationIDOnInv"));
	request.setAttribute("createNotification",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("CreateNotification"));
	request.setAttribute("TransferOwnership",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("TransferOwnership"));
	request.setAttribute("CaseReporting",
		     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
		     tm.get("CaseReporting"));
	request.setAttribute(NBSConstantUtil.ViewVaccinationFromInv,
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get(NBSConstantUtil.ViewVaccinationFromInv));
	request.setAttribute("ObservationLabID",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("ObservationLabID"));
	request.setAttribute("ObservationMorbID",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("ObservationMorbID"));
        request.setAttribute("treatmentEventRef",
                           "/nbs/" + sCurrentTask + ".do?ContextAction=" +
                           tm.get("TreatmentID"));
        request.setAttribute("DocumentIDFromInv",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("DocumentIDFromInv"));
        request.setAttribute("ManageEvents",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("ManageEvents"));

	request.setAttribute("PrintPage", "/nbs/LoadViewInvestigation1.do?ContextAction=PrintPage");

	if (sCurrentTask.equalsIgnoreCase("ViewInvestigation3"))
	{
	    request.setAttribute("linkName", "Return To File: Events");
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("ReturnToFileEvents"));
		  request.setAttribute("deleteButtonHref",
					 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				  tm.get("ReturnToFileEvents")+"&delete="+"true");

	}
	else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation2"))
	{
	    request.setAttribute("linkName", "View File");
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("FileSummary"));
	    request.setAttribute("deleteButtonHref",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			  tm.get("FileSummary")+"&delete="+"true");
	    try {
				DSQueueObject queueObject = (DSQueueObject) NBSContext
						.retrieve(session, "DSQueueObject");
				if (queueObject.getDSQueueType() != null
						&& queueObject.getDSQueueType().equals(
								NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS)) {
					request.setAttribute("linkName1",NEDSSConstants.RETURN_TO_OPEN_INVESTIGATIONS);
					request.setAttribute("linkValue1", "/nbs/" + sCurrentTask
							+ ".do?ContextAction="
							+ tm.get("ReturnToMyInvestigations"));
				} else if (queueObject.getDSQueueType() != null
						&& queueObject.getDSQueueType().equals(
								NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW)) {
					request.setAttribute("linkName1",
							"Return to Documents Requiring Review");
					request.setAttribute("linkValue1", "/nbs/" + sCurrentTask
							+ ".do?ContextAction="
							+ tm.get("ReturnToObservationNeedingReview"));
				}
			} catch (Exception ex) {
				logger
						.info("Link: Return to Open Investigations cannot be shown in this context");
				// exception means that navigation went out of context, so the
				// above link cannot be shown to user.
			}
	}
	else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation1"))
	{
	    request.setAttribute("linkName", "Return to File: Summary");
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("ReturnToFileSummary"));
		  request.setAttribute("deleteButtonHref",
					 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				  tm.get("ReturnToFileSummary")+"&delete="+"true");
	}
	else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation4"))
	{
		try {
			DSQueueObject queueObject = (DSQueueObject) NBSContext
					.retrieve(session, "DSQueueObject");
			if (NEDSSConstants.MESSAGE_QUEUE.equals(queueObject
					.getDSQueueType())) {
				request.setAttribute("linkName", "Return to Messages Queue");
				request.setAttribute(
						"linkValue",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("ReturnToMessageQueue"));
				request.setAttribute(
						"deleteButtonHref",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("ReturnToMessageQueue")
								+ "&delete=" + "true");
			} else if (NEDSSConstants.NND_NOTIFICATIONS_FOR_APPROVAL
					.equals(queueObject.getDSQueueType())) {
				request.setAttribute("linkName",
						"Return to Approval Queue for Initial Notifications");
				request.setAttribute(
						"linkValue",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("ReturnToReviewNotifications"));
				request.setAttribute(
						"deleteButtonHref",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("ReturnToReviewNotifications")
								+ "&delete=" + "true");
			} else if (NEDSSConstants.SUPERVISOR_REVIEW_QUEUE
					.equals(queueObject.getDSQueueType())) {
				request.setAttribute("linkName",
						"Return to Supervisor Review Queue");
				request.setAttribute(
						"linkValue",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("ReturnToSupervisorReviewQueue"));

				request.setAttribute(
						"deleteButtonHref",
						"/nbs/" + sCurrentTask + ".do?ContextAction="
								+ tm.get("ReturnToSupervisorReviewQueue")
								+ "&delete=" + "true");

			} 
		} catch (Exception ex) {
			logger.info("Link: cannot be shown in this context");
		}

	}
	else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation5"))
	{
	    request.setAttribute("linkName", "Return to Updated Notifications Queue");
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("ReturnToReviewUpdatedNotifications"));
		  request.setAttribute("deleteButtonHref",
					 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				  tm.get("ReturnToReviewUpdatedNotifications")+"&delete="+"true");
	}	else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation6"))
	{
	    request.setAttribute("linkName", "Return to Rejected Notifications Queue");
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("ReturnToRejectedNotifications"));
		  request.setAttribute("deleteButtonHref",
					 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				  tm.get("ReturnToRejectedNotifications")+"&delete="+"true");
	}else if(sCurrentTask.equalsIgnoreCase("ViewInvestigation13")){
		
		/*
		  request.setAttribute("linkName", "View File");
		    request.setAttribute("linkValue",
					 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
					 tm.get("FileSummary"));*/
		    request.setAttribute("deleteButtonHref",
		  		     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("FileSummary")+"&delete="+"true");
		    try {
					DSQueueObject queueObject = (DSQueueObject) NBSContext
							.retrieve(session, "DSQueueObject");
					if (queueObject.getDSQueueType() != null
							&& queueObject.getDSQueueType().equals(
									NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS)) {
						request.setAttribute("linkName1",NEDSSConstants.RETURN_TO_OPEN_INVESTIGATIONS);
						request.setAttribute("linkValue1", "/nbs/" + sCurrentTask
								+ ".do?ContextAction="
								+ tm.get("ReturnToMyInvestigations"));
					} else if (queueObject.getDSQueueType() != null
							&& queueObject.getDSQueueType().equals(
									NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW)) {
						if(passedContextAction.equalsIgnoreCase("ViewInv")){
							request.setAttribute("linkName1",
									"Return to Documents Requiring Review");
							request.setAttribute("linkValue1", "/nbs/" + sCurrentTask
									+ ".do?ContextAction="
									+ tm.get("ReturnToObservationNeedingReview"));
						}
					    request.setAttribute("linkValue",
								 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
								 tm.get("ReturnToFileEvents"));
						//add ContextAction=ReturnToFileEvents
						request.setAttribute("linkName",
								"Return To File: Events");
					}
				} catch (Exception ex) {
					logger
							.info("Link: cannot be shown in this context");
					// exception means that navigation went out of context, so the
					// above link cannot be shown to user.
				}
	}
	request.setAttribute("Edit",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Edit"));
	
	return map;
    }


      /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      */
    private ActionForward getForwardPage(String investigationFormCd, String sCurrentTask,
					 String sContextAction, NBSSecurityObj nbsSecurityObj, String viewFileUrl, String contactCaseUrl,HttpSession session)
    {
	logger.debug(
		"in getForwardPage investigationFormCd: " + investigationFormCd +
		" sCurrentTask: " + sCurrentTask + " sContextAction: " + sContextAction);
	 boolean viewContactTracing = false;
     String conditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
     String ContactTracingByConditionCd = GenericInvestigationUtil.getConditionTracingEnableInd(conditionCd);
 	
 	if(ContactTracingByConditionCd.equalsIgnoreCase(NEDSSConstants.CONTACT_TRACING_ENABLE_IND))
 		viewContactTracing = true;
 	else
 		viewContactTracing = false;
     if(viewContactTracing)
	 viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
            NBSOperationLookup.VIEW);
 
	boolean addContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
            NBSOperationLookup.ADD);
	
	boolean manageCTPerm = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
            NBSOperationLookup.MANAGE);
	String path = "/error";
	String addpermString = new Boolean(addContactTracing).toString();

	if (NBSConstantUtil.INV_FORM_GEN.equals(investigationFormCd)) {

	  if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
		path = "/diseaseform/generic/investigation_generic_print";
	  else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
		  path = "/diseaseform/generic/investigation_generic_print_no_contact";
	  else if(!viewContactTracing)
		  path = "/diseaseform/generic/investigation_generic_view_No_Contact?CurrentTask=" + sCurrentTask + "&ContextAction=" + sContextAction;
	  else
		path = "/diseaseform/generic/investigation_generic_view?CurrentTask=" + sCurrentTask + "&ContextAction=" + sContextAction +"&addPerm=" + addpermString+"&manageCTPerm="
		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

	}
	else if (NBSConstantUtil.INV_FORM_MEA.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/measles/investigation_measles_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/measles/investigation_measles_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/measles/investigation_measles_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/measles/investigation_measles_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_CRS.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/nip/crs/investigation_crs_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/nip/crs/investigation_crs_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/nip/crs/investigation_crs_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/nip/crs/investigation_crs_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_RUB.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/nip/rubella/investigation_rubella_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/nip/rubella/investigation_rubella_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/nip/rubella/investigation_rubella_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/nip/rubella/investigation_rubella_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_PER.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/nip/pertussis/investigation_pertussis_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/nip/pertussis/investigation_pertussis_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/nip/pertussis/investigation_pertussis_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm=" + manageCTPerm;
          else
            path = "/diseaseform/nip/pertussis/investigation_pertussis_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	//Hepatitis specific conditions
        else if (NBSConstantUtil.INV_FORM_HEPGEN.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/hepatitis/hep_core_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_core_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_core_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/hepatitis/hep_core_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }

	else if (NBSConstantUtil.INV_FORM_HEPA.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/hepatitis/hep_acute_a_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_acute_a_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_acute_a_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/hepatitis/hep_acute_a_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_HEPB.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/hepatitis/hep_acute_b_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_acute_b_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_acute_b_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/hepatitis/hep_acute_b_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_HEPC.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/hepatitis/hep_acute_c_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_acute_c_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_acute_c_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/hepatitis/hep_acute_c_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_HEPBV.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/hepatitis/hep_perinatal_HBV_infection_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_perinatal_HBV_infection_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_perinatal_HBV_infection_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/hepatitis/hep_perinatal_HBV_infection_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_HEPCV.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/hepatitis/hep_HCV_infection_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_HCV_infection_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/hepatitis/hep_HCV_infection_view_No_Contact?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction;
          else
            path = "/diseaseform/hepatitis/hep_HCV_infection_view?CurrentTask=" +
              sCurrentTask +
              "&ContextAction=" + sContextAction+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }

	else if (NBSConstantUtil.INV_FORM_BMDGEN.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/bmird/generic_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/bmird/generic_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/bmird/generic_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";
          else
            path = "/diseaseform/bmird/generic_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_BMDGAS.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/bmird/strepa_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/bmird/strepa_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/bmird/strepa_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";
          else
            path = "/diseaseform/bmird/strepa_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_BMDGBS.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/bmird/strepb_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/bmird/strepb_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/bmird/strepb_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString;
          else
            path = "/diseaseform/bmird/strepb_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_BMDHI.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/bmird/hi_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/bmird/hi_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/bmird/hi_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";
          else
            path = "/diseaseform/bmird/hi_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_BMDNM.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/bmird/nm_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/bmird/nm_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/bmird/nm_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view";
          else
            path = "/diseaseform/bmird/nm_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }
	else if (NBSConstantUtil.INV_FORM_BMDSP.equals(investigationFormCd)) {

          if (sContextAction.equalsIgnoreCase("PrintPage")&& viewContactTracing)
            path = "/diseaseform/bmird/sp_print";
          else if(sContextAction.equalsIgnoreCase("PrintPage") && !viewContactTracing)
        	  path = "/diseaseform/bmird/sp_print_no_contact";
          else if(!viewContactTracing)
        	  path = "/diseaseform/bmird/sp_view_No_Contact?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"; 
          else
            path = "/diseaseform/bmird/sp_view?CurrentTask=" + sCurrentTask +
              "&ContextAction=" + sContextAction + "&mode=view"+"&addPerm=" + addpermString+"&manageCTPerm="
      		+ manageCTPerm+"&contactCaseUrl=" + contactCaseUrl+"&viewFileUrl=" + viewFileUrl;

        }

	logger.debug(" path: " + path);

	return new ActionForward(path);
    }


      /**
      * Get values from investigation form and stored to Object.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  to the calling method with ActionForward Object
      */
    private ActionForward sortOnInvestigationView(ActionMapping mapping, ActionForm form,
						  HttpServletRequest request, HttpSession session) throws ServletException
    {

	String tableName = request.getParameter("tableName");
	logger.debug("The tableName in the sort Action is"+tableName);

	if (tableName != null && tableName.equalsIgnoreCase("assNotifications"))
	{
	    logger.debug("before calling sortNotifications in InvestigationLoad");

	    return this.sortNotifications(mapping, form, request, session);
	}
	else if (tableName != null && tableName.equalsIgnoreCase("assObservations"))
	{
	    logger.debug("before calling sortObservations in InvestigationLoad");

	    return this.sortObservations(mapping, form, request, session);
	}
	else if (tableName != null && tableName.equalsIgnoreCase("assObservationsMorb"))
	{
	    logger.debug("before calling sortObservations in InvestigationLoad");

	    return this.sortObservations(mapping, form, request, session);
	}
	else if (tableName != null && tableName.equalsIgnoreCase("assVaccinations"))
	{
	    logger.debug("before calling sortVaccinations in InvestigationLoad");

	    return this.sortVaccinations(mapping, form, request, session);
	}
	else
	{
	    logger.error("Could not get the TableName for Sorting");
	    session.setAttribute("error", "Could not get TableName for Sorting or Invalid tableName");

	    return (mapping.findForward("error"));
	}
    }




    /**
      * To set ABCStateIndicator from request.
      * @param HttpServletRequest the request
      */
    private void setABCStateIndicator(HttpServletRequest request)
    {
	String abcIndicator = PropertyUtil.getInstance().getABCSTATE();
	logger.debug("abcIndicator: " + abcIndicator);
	if(abcIndicator != null && abcIndicator.trim().equals("ABCSTATE_ABCQUESTION"))
	{
	   logger.debug("ABCSTATE_ABCQUESTION abcIndicator: " + abcIndicator);
	   request.setAttribute("abcCheckbox", "T");
	   request.setAttribute("abcsInd", "T");
	}
	else if(abcIndicator != null && abcIndicator.trim().equals("ABCQUESTION"))
	{
	   logger.debug("ABCQUESTION abcIndicator: " + abcIndicator);
	   request.setAttribute("abcCheckbox", "F");
	   request.setAttribute("abcsInd", "T");
	}
	else if(abcIndicator != null && abcIndicator.trim().equals("NONE"))
	{
	   logger.debug("NONE abcIndicator: " + abcIndicator);
	   request.setAttribute("abcCheckbox", "F");
	   request.setAttribute("abcsInd", "F");
	}
	else
	{
	   logger.debug("else abcIndicator: " + abcIndicator);
	   request.setAttribute("abcCheckbox", "F");
	   request.setAttribute("abcsInd", "F");
	}
    }

    private PersonVO getPersonVO(String type_cd, InvestigationProxyVO investigationProxyVO)
    {
        Collection<Object>  participationDTCollection   = null;
        Collection<Object>  personVOCollection  = null;
        ParticipationDT participationDT = null;
        PersonVO personVO = null;
        //Long phcUID = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
        participationDTCollection  = investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
        //logger.debug("convertProxyToRequestObj() after participationDTCollection  size: " + participationDTCollection.size());
        personVOCollection  = investigationProxyVO.getThePersonVOCollection();
        //logger.debug("convertProxyToRequestObj() after personVOCollection  size: " + personVOCollection.size());
        if(participationDTCollection  != null)
        {
           Iterator<Object>  anIterator1 = null;
           Iterator<Object>  anIterator2 = null;
            for(anIterator1 = participationDTCollection.iterator(); anIterator1.hasNext();)
            {
                participationDT = (ParticipationDT)anIterator1.next();
                if(participationDT.getTypeCd() != null && (participationDT.getTypeCd()).compareTo(type_cd) == 0)
                {
                    //logger.debug("convertProxyToRequestObj() got participationDT for  type_cd: " + participationDT.getTypeCd());
                    for(anIterator2 = personVOCollection.iterator(); anIterator2.hasNext();)
                    {
                        personVO = (PersonVO)anIterator2.next();
                        if(personVO.getThePersonDT().getPersonUid().longValue() == participationDT.getSubjectEntityUid().longValue())
                        {
                            //logger.debug("convertProxyToRequestObj() got personVO for  person_uid: " + personVO.getThePersonDT().getPersonUid().longValue() + " and type_cd " + participationDT.getTypeCd());
                            return personVO;
                        }
                        else continue;
                    }
                }
                else continue;
            }
        }
        return null;
    }


}