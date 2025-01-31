package gov.cdc.nedss.webapp.nbs.action.investigation.common;

/**
 * Title:        InvestigationMergeLoad
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2017
 * Company:      CSRA
 * @author Fatima Lopez Calzado
 * @version 1.0
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.generic.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.hepatitis.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.measles.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.crs.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.pertussis.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.rubella.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;


public class InvestigationMergeLoad
    extends CommonAction
{

    //For logging
    static final LogUtils logger = new LogUtils(InvestigationMergeLoad.class.getName());
    private String investigationFormCdForLDF = null;
    /**
     * empty constructor
     */
    public InvestigationMergeLoad()
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
	InvestigationForm investigationForm = (InvestigationForm)form;
	HttpSession session = request.getSession(false);
	String invFormCd = null;

	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	boolean editInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT);
	if (!editInvestigation)
	{
	    logger.info("You do not have access to edit Investigation");
	    session.setAttribute("error", "You do not have access to edit Investigation");
	    return (mapping.findForward("error"));
	}

	String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
	String sPublicHealthCaseUID2 = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid1);
	
	String sCurrentTask = this.setContextForEdit(request, session);
	String sContextAction = request.getParameter("ContextAction");

     
	//Long publicHealthCaseUID = new Long((String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid));
	 Long publicHealthCaseUID = new Long(sPublicHealthCaseUID);
	 HashMap<?,?> cdProgAreaMap = getPHCConditionAndProgArea(publicHealthCaseUID,session);
	if (cdProgAreaMap != null)
			invFormCd = getInvestigationFormCd((String) cdProgAreaMap
					.get(NEDSSConstants.CONDITION_CD), (String) cdProgAreaMap
					.get(NEDSSConstants.PROG_AREA_CD));
		InvestigationProxyVO oldProxy = null;
		try {
			if (invFormCd != null && !invFormCd.startsWith("PG_") && !invFormCd.equals(NBSConstantUtil.INV_FORM_RVCT) && !invFormCd.equals(NBSConstantUtil.INV_FORM_VAR) && !invFormCd.equals("INV_FORM_CHLR"))
				oldProxy = this.getInvestigationProxy(investigationForm, session, sCurrentTask, sContextAction);
		} catch (Exception ex) {
			logger.error(ex.toString());
			throw new ServletException(ex.toString());
		}


      	// set tab order before we forward, This trumps any logic we may have
		// before on tab selection xz 11/03/2004
          request.setAttribute("DSFileTab", new Integer(PropertyUtil.getInstance().getDefaultInvTabOrder()).toString());
      	TreeMap<Object, Object> tm = NBSContext.getPageContext(session, "PS023", sContextAction);
      	
      	NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, sPublicHealthCaseUID);
      	NBSContext.store(session, NBSConstantUtil.DSInvestigationUid1, sPublicHealthCaseUID2);
      	
      	
	return this.getActionForward(mapping, sContextAction, invFormCd);

    }


    /**
     * this method gets the proxy from backend
     * @param investigationForm : InvestigationForm
     * @param session : HttpSession
     * @param sCurrentTask : String
     * @param sContextAction : String
     * @return : InvestigationProxyVO
     * @throws Exception
     */
    private InvestigationProxyVO getInvestigationProxy(InvestigationForm investigationForm,  HttpSession session, String sCurrentTask, String sContextAction) throws Exception
    {

	InvestigationProxyVO investigationProxyVO = null;

        Long publicHealthCaseUID = null;
        publicHealthCaseUID = new Long((String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid));
        
	try
	{

	    String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
	    String sMethod = "getInvestigationProxy";
	    Object[] oParams = new Object[] { publicHealthCaseUID };

	    MainSessionHolder holder = new MainSessionHolder();
	    MainSessionCommand msCommand = holder.getMainSessionCommand(session);

	    ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	    investigationProxyVO = (InvestigationProxyVO)arr.get(0);
	    investigationForm.setOldProxy(investigationProxyVO);
	    investigationForm.setProxy(null);
            PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT,investigationProxyVO);
            investigationForm.setPatient(personVO);
            investigationForm.setOldRevision((PersonVO)personVO.deepCopy());

	    investigationForm.setLoadedFromDB(true);
	}
	catch (Exception ex)
	{
	    logger.fatal("getOldProxyObject: ", ex);
            ex.printStackTrace();
	    throw ex;
	}

	return investigationProxyVO;
    }


    /**
     * sets the context for edit load
     * @param request : HttpServletRequest
     * @param session : HttpSession
     * @return : String
     */
    private String setContextForEdit(HttpServletRequest request, HttpSession session)
    {
	String passedContextAction = request.getParameter("ContextAction");
	//TreeMap<Object, Object> tm = NBSContext.getPageContext(session, "PS023", passedContextAction);
	String sCurrentTask = NBSContext.getCurrentTask(session);

	return sCurrentTask;
    }


    /**
     * gets the jurisdiction list from NBSSecurityObj and sets it to request
     * @param request : HttpServletRequest
     * @param nbsSecurityObj : NBSSecurityObj
     */
    public void getNBSSecurityJurisdictions(HttpServletRequest request,NBSSecurityObj nbsSecurityObj)
    {

	String programAreaJurisdictions = nbsSecurityObj.getProgramAreaJurisdictions(
						  NBSBOLookup.INVESTIGATION,
						  NBSOperationLookup.ADD);
	StringBuffer stringBuffer = new StringBuffer();

	if (programAreaJurisdictions != null &&
	    programAreaJurisdictions.length() > 0)
	{

	    //change the navigation depending on programArea
	    StringTokenizer st = new StringTokenizer(programAreaJurisdictions,
						     "|");

	    while (st.hasMoreTokens())
	    {

		String token = st.nextToken();

		if (token.lastIndexOf("$") >= 0)
		{

		    String programArea = token.substring(0,
							 token.lastIndexOf("$"));
		    logger.debug("programAreaJurisdictionToken: " + token);

		    String juris = token.substring(token.lastIndexOf("$") + 1);
		    stringBuffer.append(juris).append("|");
		    logger.info("jurisdition for this programArea: " +
				juris);
		}
	    }

	    logger.debug(
		    ".....NBSSecuirtyJurisdiction: " +
		    stringBuffer.toString());


	    request.setAttribute("NBSSecurityJurisdictions", stringBuffer.toString());
	}


    } //getNBSSecuirityJurisdictions

    /**
     * looks for patient in InvestigationProxyVO and returns it
     * @param type_cd : String
     * @param investigationProxyVO : InvestigationProxyVO
     * @return : PersonVO
     */

    private PersonVO getPersonVO(String type_cd, InvestigationProxyVO investigationProxyVO)
    {
        Collection<Object>  participationDTCollection   = null;
        Collection<Object>  personVOCollection  = null;
        ParticipationDT participationDT = null;
        PersonVO personVO = null;
        participationDTCollection  = investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
        personVOCollection  = investigationProxyVO.getThePersonVOCollection();
        if(participationDTCollection  != null)
        {
           Iterator<Object>  anIterator1 = null;
           Iterator<Object>  anIterator2 = null;
            for(anIterator1 = participationDTCollection.iterator(); anIterator1.hasNext();)
            {
                participationDT = (ParticipationDT)anIterator1.next();
                if(participationDT.getTypeCd() != null && (participationDT.getTypeCd()).compareTo(type_cd) == 0)
                {
                    for(anIterator2 = personVOCollection.iterator(); anIterator2.hasNext();)
                    {
                        personVO = (PersonVO)anIterator2.next();
                        if(personVO.getThePersonDT().getPersonUid().longValue() == participationDT.getSubjectEntityUid().longValue())
                        {
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

  /**
  * this method returns ActionForward to the condition specific action class
  * @param mapping : ActionMapping
  * @param investigationFormCd : String
  * @param sContextAction : String
  * @return ActionForward
  */
    private ActionForward getActionForward(ActionMapping mapping, String sContextAction, String  investigationFormCd)
    {

        String path = "/error";

        if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_GEN))
        {
            path = "GenericEditLoad";
        }
        else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_MEA))
        {
            path = "MeaslesEditLoad";
        }
        else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_CRS))
        {
          path = "CrsEditLoad";
        }
        else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_RUB))
        {
          path = "RubellaEditLoad";
        }
        else if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_PER))
        {
          path = "PertussisEditLoad";
        }
        else if (investigationFormCd.startsWith("INV_FORM_HEP"))
        {
          path = "HepatitisEditLoad";
          if(sContextAction.equals("DiagnosedCondition"))
          path = "HepatitisEditDiagnosis";

        }
        else if (investigationFormCd.startsWith("INV_FORM_BMD"))
        {
          path = "BmirdEditLoad";
          if(sContextAction.equals("DiagnosedCondition"))
          path = "BmirdEditDiagnosis";

        }
        else if (NBSConstantUtil.INV_FORM_RVCT.equals(investigationFormCd))
    	{
    	    path = "TuberculosisEditLoad";
    	    //request.setAttribute("oldProxy", oldProxy);
    	}
        else if (NBSConstantUtil.INV_FORM_VAR.equals(investigationFormCd))
    	{
    	    path = "VaricellaEditLoad";
    	}
        
        
        
        else 
        {        	
        	//path = "CholeraEditLoad";
        	path = "DMBEditLoad";
        }        
        
        return mapping.findForward(path);
    }



}