package gov.cdc.nedss.webapp.nbs.action.investigation.pertussis;
/**
 *
 * <p>Title: PertusisCreateLoad</p>
 * <p>Description: This is a load action class for create investigation for the Pertusis condition.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
/**
 * Title:        CoreDemographic
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Jay Kim
 * @version 1.0
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.generic.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.pertussis.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;


public class PertussisCreateLoad
    extends BaseCreateLoad
{

    static final LogUtils logger = new LogUtils(PertussisCreateLoad.class.getName());

    public PertussisCreateLoad()
    {
    }
    /**
    * Process the specified HTTP request, and create the corresponding HTTP response
    * (or forward to another web component that will create it). Return an ActionForward
    * instance describing where and how control should be forwarded, or null if the response
    * has already been completed.
    * @param mapping - The ActionMapping used to select this instance
    * @param form - The optional ActionForm bean for this request (if any)
    * @param request - The HTTP request we are processing
    * @param response - The HTTP response we are creating
    * @return mapping.findForward("XSP") -- ActionForward instance describing where and how control should be forwarded
    * @throws IOException -- if an input/output error occurs
    * @throws ServletException -- if a servlet exception occurs
    */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {
	HttpSession session = request.getSession(false);
	//context
	String sContextAction = request.getParameter("ContextAction");

	if (sContextAction == null)
	{
	    session.setAttribute("error", "No Context Action in InvestigationCreateLoad");
	    throw new ServletException("No Context Action in InvestigationCreateLoad");
	}

        InvestigationForm investigationForm = (InvestigationForm)form;
        investigationForm.reset();
		boolean viewContactTracing = false;
		String sCurrentTask = null;
		Long mprUid= null;
        ProgramAreaVO programAreaVO = null;
        String investigationFormCd = null;
        try {
        	String conditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
        	String programArea =(String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationProgramArea);


        	CachedDropDownValues cdv = new CachedDropDownValues();
        	programAreaVO = cdv.getProgramAreaCondition("('" +programArea + "')", conditionCd);

        	conditionCd = programAreaVO.getConditionCd();
        	investigationFormCd = programAreaVO.getInvestigationFormCd();
        	// request.setAttribute("reportingSourceCollection", super.convertReportingSourcesToRequest(NEDSSConstants.REPORTING_SOURCE_CREATE,null, conditionCd));
        	request.setAttribute("conditionCd", programAreaVO.getConditionCd());
        	request.setAttribute("conditionCdDescTxt", programAreaVO.getConditionShortNm());
        	request.setAttribute("programAreaCd", programAreaVO.getStateProgAreaCode());
        	request.setAttribute("programAreaDescTxt", programAreaVO.getStateProgAreaCdDesc());
        	request.setAttribute("sharedIndicator", "T");
        	NBSContext.store(session, NBSConstantUtil.DSInvestigationCode, investigationFormCd);

        	//security stuff
        	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        	boolean checkCreatePermission = super.setSecurityForCreateLoad(request, nbsSecurityObj, programAreaVO);
        	if (!checkCreatePermission)
        	{
        		logger.error("You do not have access to Create Investigation");
        		session.setAttribute("error", "You do not have access to Create Investigation");

        		return (mapping.findForward("error"));
        	}

        	//LDF stuff
        	InvestigationProxyVO investigationProxyVO =  investigationForm.getProxy();
        	if(investigationProxyVO== null)
        	{
        		investigationProxyVO =  new InvestigationProxyVO();
        	}
        	// use the new API to retrieve custom field collection
        	// to handle multiselect fields (xz 01/11/2005)
        	Collection<Object>  coll = extractLdfDataCollection(investigationForm, request);
        	if (coll!= null)
        	{
        		investigationProxyVO.setTheStateDefinedFieldDataDTCollection(coll);
        	}

        	String businessObjNm = cdv.getLDFMap(conditionCd);
        	request.setAttribute("showConditionSpecificLDF", new Boolean(false));
        	super.createXSP(businessObjNm,investigationProxyVO,conditionCd,request);

        	mprUid = (Long) NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary);
        	// why do we need to create instance
        	PersonVO personVO = new PersonVO();
        	if (mprUid != null)
        	{
        		personVO = super.findMasterPatientRecord(mprUid, session, nbsSecurityObj);
        		super.createXSP(NEDSSConstants.PATIENT_LDF, personVO, null, request);
        		ArrayList<Object> stateList = new ArrayList<Object> ();
        		PersonUtil.convertPersonToRequestObj(personVO,request,"AddPatientFromEvent",stateList);
        	}

        	super.setJurisdictionForCreate(request, nbsSecurityObj, programAreaVO);

        	sCurrentTask = super.setContextForCreate(request, session);

        	if(sCurrentTask.equals("CreateInvestigation1"))
        	{
        		String strJurisdiction = personVO.getDefaultJurisdictionCd();
        		request.setAttribute("jurisdiction", strJurisdiction);
        	}

        	if(sCurrentTask.equals("CreateInvestigation2")
        			|| sCurrentTask.equals("CreateInvestigation3")
        			|| sCurrentTask.equals("CreateInvestigation4")
        			|| sCurrentTask.equals("CreateInvestigation5")
        			|| sCurrentTask.equals("CreateInvestigation6")
        			|| sCurrentTask.equals("CreateInvestigation7")
        			|| sCurrentTask.equals("CreateInvestigation8")
        			|| sCurrentTask.equals("CreateInvestigation9"))
        	{
        		String jurisdiction = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationJurisdiction);
        		request.setAttribute("jurisdiction", jurisdiction);
        	}

        	if(sCurrentTask.equals("CreateInvestigation5") || sCurrentTask.equals("CreateInvestigation6") || sCurrentTask.equals("CreateInvestigation7")|| sCurrentTask.equals("CreateInvestigation8"))
        	{
        		//this is from morb and for generic investigation only
        		TreeMap<Object,Object> DSMorbMap = (TreeMap<Object, Object>)NBSContext.retrieve(session, NBSConstantUtil.DSMorbMap);
        		super.prepopulateMorbValues(DSMorbMap, request);
        		String INV145 = (String)DSMorbMap.get("INV145");
        		if(INV145 != null && INV145.equals(NEDSSConstants.YES))
        			request.setAttribute("PRT103", INV145);
        	}
        	if(sCurrentTask.equals("CreateInvestigation2") || sCurrentTask.equals("CreateInvestigation3") || sCurrentTask.equals("CreateInvestigation4") || sCurrentTask.equals("CreateInvestigation9"))
        	{
        		//this is from lab and for generic investigation only
        		TreeMap<Object,Object> labMap = (TreeMap<Object, Object>)NBSContext.retrieve(session, NBSConstantUtil.DSLabMap);
        		super.prepopulateMorbValues(labMap, request);
        		String INV145 = (String)labMap.get("INV145");
        		if(INV145 != null && INV145.equals(NEDSSConstants.YES))
        			request.setAttribute("PRT103", INV145);

        	}
        	if( sCurrentTask.equals("CreateInvestigation10")|| sCurrentTask.equals("CreateInvestigation11")&& NBSContext.retrieve(request.getSession(),"DSDocumentUID")!=null){
        		Long DSDocumentUID=(Long)NBSContext.retrieve(request.getSession(),"DSDocumentUID");
        		Map<Object, Object> map = InvestigationUtil.getPublicHealthCaseAndObsColl(DSDocumentUID, request);
        		PublicHealthCaseVO publicHealthCaseVO= (PublicHealthCaseVO)map.get("PHCVO");
        		Collection<ObservationVO>  obsColl = (ArrayList<ObservationVO> )map.get("OBSERVATIONCOLLECTION");
        		investigationProxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
        		if(obsColl!=null)
        			investigationProxyVO.setTheObservationVOCollection(obsColl);
        		GenericInvestigationUtil genericInvestigationUtil = new GenericInvestigationUtil();
        		genericInvestigationUtil.convertPublicHealthCaseToRequest(investigationProxyVO, request);
        		genericInvestigationUtil.convertObservationsToRequest(investigationProxyVO, request);
        		publicHealthCaseVO.getThePublicHealthCaseDT().setProgAreaCd( programAreaVO.getStateProgAreaCode());
        		publicHealthCaseVO.getThePublicHealthCaseDT().setCd(programAreaVO.getConditionCd());
        		request.setAttribute("conditionCd", programAreaVO.getConditionCd());
        		request.setAttribute("conditionCdDescTxt", programAreaVO.getConditionShortNm());
        		request.setAttribute("programAreaCd", programAreaVO.getStateProgAreaCode());
        		request.setAttribute("programAreaDescTxt", programAreaVO.getStateProgAreaCdDesc());
        		request.setAttribute("sharedIndicator", "T");
        		investigationForm.getProxy().setPublicHealthCaseVO(null);
        		investigationForm.getProxy().setTheObservationVOCollection(null);
        	}



        	String ContactTracingByConditionCd = GenericInvestigationUtil.getConditionTracingEnableInd(conditionCd);

        	if(ContactTracingByConditionCd.equalsIgnoreCase(NEDSSConstants.CONTACT_TRACING_ENABLE_IND))
        		viewContactTracing = true;
        	else
        		viewContactTracing = false;
        	if(viewContactTracing)
        		viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
        				NBSOperationLookup.VIEW);
        }catch (Exception e) {
        	logger.error("Exception in Pertussis Create Load: " + e.getMessage());
        	e.printStackTrace();
        	throw new ServletException("Error while Pertussis Create Load : "+e.getMessage());
        }
         return (this.getForwardPage(mprUid, investigationFormCd, sCurrentTask, sContextAction,request, viewContactTracing));
    }

    /**
     * this method returns ActionForward with the actual struts mapping of XSP page
     * @param mprUid :Long
     * @param investigationFormCd : String
     * @param sCurrentTask : String
     * @param sContextAction : String
     * @param request : HttpServletRequst
     * @return ActionForward
     */
    private ActionForward getForwardPage(Long mprUid , String investigationFormCd, String sCurrentTask,
					 String sContextAction,HttpServletRequest request, boolean viewContactTracing)
    {

	String path = "/error";

	if (investigationFormCd.equals("INV_FORM_PER")&& viewContactTracing)
	{
	    path = "/diseaseform/nip/pertussis/investigation_pertussis_create?CurrentTask=" + sCurrentTask +
		   "&ContextAction=" + sContextAction;
	    PertussisInvestigationUtil pertussisInvestigationUtil = new PertussisInvestigationUtil();
	    pertussisInvestigationUtil.getVaccinationSummaryRecords(mprUid ,request);
	    request.setAttribute("PertussisDeathWorkSheet",pertussisInvestigationUtil.getUrlFromDatabase("PERDTH"));
	 }else if (investigationFormCd.equals("INV_FORM_PER")&& !viewContactTracing)
		{
		    path = "/diseaseform/nip/pertussis/investigation_pertussis_create_No_Contact?CurrentTask=" + sCurrentTask +
			   "&ContextAction=" + sContextAction;
		    PertussisInvestigationUtil pertussisInvestigationUtil = new PertussisInvestigationUtil();
		    pertussisInvestigationUtil.getVaccinationSummaryRecords(mprUid ,request);
		    request.setAttribute("PertussisDeathWorkSheet",pertussisInvestigationUtil.getUrlFromDatabase("PERDTH"));
		 }
      	return new ActionForward(path);
    }

}
