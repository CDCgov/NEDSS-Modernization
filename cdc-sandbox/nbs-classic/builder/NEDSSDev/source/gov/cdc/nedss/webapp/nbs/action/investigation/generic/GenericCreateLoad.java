package gov.cdc.nedss.webapp.nbs.action.investigation.generic;
/**
 *
 * <p>Title: GenericCreateLoad</p>
 * <p>Description: This is a load action class for create investigation for all the Generic conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
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
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;


public class GenericCreateLoad
    extends BaseCreateLoad
{

    static final LogUtils logger = new LogUtils(GenericCreateLoad.class.getName());

   /**
    * empty constructor
    */
    public GenericCreateLoad()
    {
    }

    /**
    * Process the specified HTTP request, and create the corresponding HTTP response
    * (or forward to another web component that will create it). Return an ActionForward
    * instance describing where and how control should be forwarded, or null if the response
    * has already been completed.
    * @param mapping - The ActionMapping used to select this instance
    * @param form - The ActionForm bean for this request (if any)
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

    	String sContextAction = request.getParameter("ContextAction");
    	if (sContextAction == null)
    	{
    		session.setAttribute("error", "No Context Action in InvestigationCreateLoad");
    		throw new ServletException("No Context Action in InvestigationCreateLoad");
    	}


    	Long mprUid = null;
    	String sCurrentTask = "";
    	ProgramAreaVO programAreaVO = null;
    	String investigationFormCd = null;
    	boolean viewContactTracing = false;
    	InvestigationForm investigationForm = (InvestigationForm)form;
    	investigationForm.reset();
    	try {
    		String conditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
    		String programArea =(String)NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationProgramArea);

    		// request.setAttribute("reportingSourceCollection", super.convertReportingSourcesToRequest(NEDSSConstants.REPORTING_SOURCE_CREATE,null, conditionCd));
    		CachedDropDownValues cdv = new CachedDropDownValues();
    		programAreaVO = cdv.getProgramAreaCondition("('" +programArea + "')", conditionCd);

    		conditionCd = programAreaVO.getConditionCd();
    		investigationFormCd = programAreaVO.getInvestigationFormCd();
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
    		}
    		if(sCurrentTask.equals("CreateInvestigation2") || sCurrentTask.equals("CreateInvestigation3") || sCurrentTask.equals("CreateInvestigation4") || sCurrentTask.equals("CreateInvestigation9"))
    		{
    			//this is from lab and for generic investigation only
    			TreeMap<Object,Object> labMap = (TreeMap<Object, Object>)NBSContext.retrieve(session, NBSConstantUtil.DSLabMap);
    			super.prepopulateMorbValues(labMap, request);
    		}
    		if( (sCurrentTask.equals("CreateInvestigation10") || sCurrentTask.equals("CreateInvestigation11") )&& NBSContext.retrieve(request.getSession(),"DSDocumentUID")!=null){
    			Long DSDocumentUID=(Long)NBSContext.retrieve(request.getSession(),"DSDocumentUID");
    			Map map = InvestigationUtil.getPublicHealthCaseAndObsColl(DSDocumentUID, request);
    			PublicHealthCaseVO publicHealthCaseVO= (PublicHealthCaseVO)map.get("PHCVO");
    			Collection<ObservationVO>  obsColl = (ArrayList<ObservationVO> )map.get("OBSERVATIONCOLLECTION");
    			if(obsColl!=null)
    				investigationProxyVO.setTheObservationVOCollection(obsColl);
    			publicHealthCaseVO.getThePublicHealthCaseDT().setProgAreaCd( programAreaVO.getStateProgAreaCode());
    			publicHealthCaseVO.getThePublicHealthCaseDT().setCd(programAreaVO.getConditionCd());
    			GenericInvestigationUtil genericInvestigationUtil = new GenericInvestigationUtil();
    			investigationProxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
    			investigationProxyVO	.getPublicHealthCaseVO().getThePublicHealthCaseDT().setCd(conditionCd);
    			genericInvestigationUtil.convertPublicHealthCaseToRequest(investigationProxyVO, request);
    			genericInvestigationUtil.convertObservationsToRequest(investigationProxyVO, request);
    			investigationForm.getProxy().setPublicHealthCaseVO(null);
    			investigationForm.getProxy().setTheObservationVOCollection(null);
    		}

    		request.setAttribute("conditionCdDescTxt", programAreaVO.getConditionShortNm());

    		String ContactTracingByConditionCd = GenericInvestigationUtil.getConditionTracingEnableInd(conditionCd);

    		if(ContactTracingByConditionCd.equalsIgnoreCase(NEDSSConstants.CONTACT_TRACING_ENABLE_IND))
    			viewContactTracing = true;
    		else
    			viewContactTracing = false;

    		if(viewContactTracing)
    			viewContactTracing = nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
    					NBSOperationLookup.VIEW);
    	}catch (Exception e) {
    		logger.error("Exception in Generic Create Load: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Error during  Generic Create Load : "+e.getMessage());
    	}	
    	return (this.getForwardPage(mprUid, investigationFormCd, sCurrentTask, sContextAction,request,viewContactTracing));
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

        if (NBSConstantUtil.INV_FORM_GEN.equals(investigationFormCd)&& viewContactTracing){
            path = "/diseaseform/generic/investigation_generic_create?CurrentTask=" + sCurrentTask +
                   "&ContextAction=" + sContextAction;
        }else if(!viewContactTracing)
        	 path = "/diseaseform/generic/investigation_generic_create_No_Contact?CurrentTask=" + sCurrentTask +
             "&ContextAction=" + sContextAction;
        else
        {
          logger.error("somthing wrong with the path: " + path + " investigationFormCd " + investigationFormCd);
        }

        return new ActionForward(path);
    }

}
