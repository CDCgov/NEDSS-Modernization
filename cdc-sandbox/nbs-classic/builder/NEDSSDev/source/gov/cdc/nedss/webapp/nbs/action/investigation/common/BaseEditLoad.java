package gov.cdc.nedss.webapp.nbs.action.investigation.common;
/**
 *
 * <p>Title: BaseEditLoad</p>
 * <p>Description: This is a Base Load action class for Edit Invesigation all the Edit Load extends this class.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

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


public class BaseEditLoad
    extends CommonAction
{

    //For logging
    static final LogUtils logger = new LogUtils(BaseEditLoad.class.getName());

    public BaseEditLoad()
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

      return mapping.findForward(NEDSSConstants.ERROR_PAGE);
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
    protected InvestigationProxyVO getInvestigationProxy(InvestigationForm investigationForm,  HttpSession session, String sCurrentTask, String sContextAction) throws Exception
    {

	InvestigationProxyVO investigationProxyVO = null;
	MainSessionCommand msCommand = null;
        Long publicHealthCaseUID = null;
        if   ((sCurrentTask.equals("EditInvestigation4") && !(sContextAction.equals("DiagnosedCondition")))
           || (sCurrentTask.equals("EditInvestigation5") && !(sContextAction.equals("DiagnosedCondition")))
           || (sCurrentTask.equals("EditInvestigation6") && !(sContextAction.equals("DiagnosedCondition"))))
        {
        	String investigationUid=(String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
        	publicHealthCaseUID = new Long(investigationUid);
        	

        }
        else
        {
          publicHealthCaseUID = new Long((String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid));
        }

	try
	{

	    String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
	    String sMethod = "getInvestigationProxy";
	    Object[] oParams = new Object[] { publicHealthCaseUID };

	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);

	    ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	    investigationProxyVO = (InvestigationProxyVO)arr.get(0);
	    //investigationForm.reset();
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
	    throw new Exception(ex.toString());
	}

	return investigationProxyVO;
    }

    /**
     * sets the context for edit load
     * @param request : HttpServletRequest
     * @param session : HttpSession
     * @return : String
     */
    protected String setContextForEdit(HttpServletRequest request, HttpSession session)
    {

	String passedContextAction = request.getParameter("ContextAction");
	
	try{
		TimeUnit.MILLISECONDS.sleep(200);//For IE11: the submit/cancel button doesn't show after creating an investigation without case status and create a notification.
	}catch(InterruptedException e){
		logger.error("Exception in setContextForEdit: " + e.getMessage());
		e.printStackTrace();
	}

	TreeMap<Object, Object> tm = NBSContext.getPageContext(session, "PS023", passedContextAction);
        ErrorMessageHelper.setErrMsgToRequest(request);
	String sCurrentTask = NBSContext.getCurrentTask(session);

	request.setAttribute("ContextAction", passedContextAction);
	request.setAttribute("CurrentTask", sCurrentTask);
	request.setAttribute("formHref", "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Submit"));
	request.setAttribute("Cancel",  "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Cancel"));
	request.setAttribute("SubmitNoViewAccess",  "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("SubmitNoViewAccess"));
	request.setAttribute("DiagnosedCondition",  "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("DiagnosedCondition"));


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
	logger.debug("....programAreaJurisdictions: " + programAreaJurisdictions);

	if (programAreaJurisdictions != null &&
	    programAreaJurisdictions.length() > 0)
	{
	    //change the navigation depending on programArea
	    StringTokenizer st = new StringTokenizer(programAreaJurisdictions, "|");
	    while (st.hasMoreTokens())
	    {
		String token = st.nextToken();
		if (token.lastIndexOf("$") >= 0)
		{
		    String programArea = token.substring(0, token.lastIndexOf("$"));
		    String juris = token.substring(token.lastIndexOf("$") + 1);
		    stringBuffer.append(juris).append("|");
		    logger.info("jurisdition for this programArea: " +	juris);
		}
	    }
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


}