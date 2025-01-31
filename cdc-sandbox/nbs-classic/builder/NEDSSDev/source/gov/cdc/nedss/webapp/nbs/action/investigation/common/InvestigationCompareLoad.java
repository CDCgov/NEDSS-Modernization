
package gov.cdc.nedss.webapp.nbs.action.investigation.common;


import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
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
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.CompareUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;

/**
 * Title:        InvestigationCompareLoad is a class
 * Description:  This class retrieves data from EJB and puts them into request
 *               object for use in the xml file
 * Copyright:    Copyright (c) 2017
 * Company:      CSC
 * @author       Fatima Lopez Calzado
 * @version      1.0
 */

public class InvestigationCompareLoad extends CommonAction
{

    //For logging
    static final LogUtils logger = new LogUtils(InvestigationCompareLoad.class.getName());

     /**
       * This is constructor
       *
       */
    public InvestigationCompareLoad()
    {
    }

    /**
      * Get values from investigation form and forward to next action.
      * @param mapping : ActionMapping Object
      * @param form : the ActionForm contain values
      * @param request : HttpServletRequest the request
      * @param response : HttpServletResponse the response
      * @return ActionForward Object
      * @throws IOException
      * @throws ServletException
      */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
              throws IOException, ServletException
    {         
        InvestigationForm invform = (InvestigationForm)form;
        invform.setOldProxy(null);
        HttpSession session = request.getSession(false);
        try{
        	if(request.getSession().getAttribute("observationForm")!=null){
        		request.getSession().removeAttribute("observationForm");   	             
   	        	
   		 	}
        }catch (Exception e) {
			e.printStackTrace();
			logger.error("InvestigationCompareLoad.Execute:- Error while Cleaning up the session objects: " + e);

		}
        //context
        String sContextAction = request.getParameter("ContextAction");
        if (sContextAction == null)
        {
            try {
                sContextAction = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationPath);
            } catch (Exception e) {
                logger.error("sContextAction is null");
            }
            if(sContextAction==null){
                logger.error("sContextAction is null");
                session.setAttribute("error", "No Context Action in InvestigationLoad");
                throw new ServletException("sContextAction is null");
            }  
        }
        
        if (sContextAction.equals("ViewInv")) {
			   String investgationUid = request.getParameter("publicHealthCaseUID");
			   NBSContext.store(session, "DSInvestigationUID", investgationUid);				
        }
        
        
        
        
        NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

        boolean autoCreatePermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.AUTOCREATE);

        boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);

        String confirmationMsg = session.getAttribute(NBSConstantUtil.ConfirmationMsg) == null ? null : (String) session.getAttribute(NBSConstantUtil.ConfirmationMsg);
     
        if (sContextAction.equals(NBSConstantUtil.SubmitNoViewAccess) || (autoCreatePermission &&!viewInvestigation))
        {
            try
            {
              return this.viewConfirmationPage(mapping, form, request, session);
            }
            catch (Exception ex)
            {
                throw new ServletException(ex.getMessage(),ex);
            }
        }
        else
        {
            try
            {
                InvestigationForm investigationForm = (InvestigationForm)form;
               
                String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
                Long publicHealthCaseUID = new Long((String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid));
                
                //Second investigation
                String sPublicHealthCaseUID1 = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid1);
                Long publicHealthCaseUID1 = new Long((String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid1));
                

                
                String investigationFormCd = null;
               
                
                HashMap<?,?> cdProgAreaMap = getPHCConditionAndProgArea(publicHealthCaseUID,session);
                //Second investigation
                HashMap<?,?> cdProgAreaMap1 = getPHCConditionAndProgArea(publicHealthCaseUID1,session);
                
                if (cdProgAreaMap != null)
                    investigationFormCd = getInvestigationFormCd((String) cdProgAreaMap
                                .get(NEDSSConstants.CONDITION_CD), (String) cdProgAreaMap
                                .get(NEDSSConstants.PROG_AREA_CD));

                
                
                NBSContext.store(session,NBSConstantUtil.DSInvestigationFormCd,investigationFormCd);
                
                //GST - fix for defect #8371 follows
                if (investigationFormCd != null && investigationFormCd.startsWith("PG_")) {
                	String condCd = (String) cdProgAreaMap.get(NEDSSConstants.CONDITION_CD);
                	String progAreaCd = (String) cdProgAreaMap.get(NEDSSConstants.PROG_AREA_CD);
                	//Second investigation
                	String progAreaCd1 = (String) cdProgAreaMap1.get(NEDSSConstants.PROG_AREA_CD);
                	
                	if (condCd != null && !condCd.isEmpty() && progAreaCd != null && !progAreaCd.isEmpty()) {
                		NBSContext.store(session,NBSConstantUtil.DSInvestigationCondition,condCd);
                		NBSContext.store(session,NBSConstantUtil.DSInvestigationProgramArea,progAreaCd);
                		//Second investigation
                		NBSContext.store(session,NBSConstantUtil.DSInvestigationProgramArea1,progAreaCd1);
                		
                	}
                }
                
                
                if(investigationFormCd!=null && !investigationFormCd.startsWith("PG_") && !investigationFormCd.equals(NBSConstantUtil.INV_FORM_RVCT) && !investigationFormCd.equals(NBSConstantUtil.INV_FORM_VAR) && !investigationFormCd.equals("INV_FORM_CHLR")){
                    InvestigationProxyVO investigationProxyVO = this.getOldProxyObject(sPublicHealthCaseUID,  investigationForm,  session);
                    request.setAttribute("investigationProxyVO", investigationProxyVO);
                    
                   
                    //String investigationFormCd = super.getInvestigationFormCd(investigationProxyVO);
                    String conditionCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
                    NBSContext.store(session, NBSConstantUtil.DSInvestigationCondition, conditionCd);
                    
                    //check if we need to fill in the new local id for a confirmation message
                    if(confirmationMsg != null && confirmationMsg.trim().length() > 0)
                        if (confirmationMsg.contains("<1>"))
                            confirmationMsg = confirmationMsg.replace("<1>", investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
                }
            
                //for change condition and pam share confirmation messages
                if(confirmationMsg != null && confirmationMsg.trim().length() > 0) {
                    ActionMessages messages = new ActionMessages();
                       messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
                               new ActionMessage(NBSPageConstants.SAVE_INFO_MESSAGE_KEY,confirmationMsg));
                       request.setAttribute(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY, messages);
                          session.removeAttribute(NBSConstantUtil.ConfirmationMsg);
                } 
            
               
                
                if(request.getAttribute("ContactTabtoFocus")!=null)
                     request.setAttribute("DSFileTab", "3");    
                // set tab order before we forward, This trumps any logic we may have before on tab selection xz 11/03/2004 
                else
                request.setAttribute("DSFileTab", new Integer(PropertyUtil.getInstance().getDefaultInvTabOrder()).toString());
                try{
                    String rejectedDeleteString  =  NBSContext.retrieve(session,NBSConstantUtil.DSRejectedDeleteString) == null ? null : (String)NBSContext.retrieve(session,NBSConstantUtil.DSRejectedDeleteString);
                    request.setAttribute("deleteError",rejectedDeleteString );    
                  
                }catch(NullPointerException ex){
                    logger.debug("The view Investigation loaded");
                }
                return mapping.findForward(this.getActionForward(investigationFormCd, request));
            }
            catch (NEDSSAppConcurrentDataException ncde)
            {
                return mapping.findForward("dataerror");
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
                logger.error(exp.toString());
                throw new ServletException(exp.toString());
            } 
        }
    } 
 
    /**
      * Get values from investigation form and stored to Object.
      * @param mapping : ActionMapping
      * @param form : ActionForm the form contain values
      * @param request : HttpServletRequest the request
      * @param session : HttpSession
      * @return : ActionForward to the calling method with ActionForward Object
      * @throws Exception
      */
    private ActionForward viewConfirmationPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpSession session)
                    throws Exception
    { 
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
            throw new Exception(e.toString());
        }
        logger.debug("The value of local Id "+ investigationId);
        request.setAttribute("InvestigationUid", investigationId);
        request.setAttribute("programArea", programArea);
        request.setAttribute("jurisdiction", jurisdiction);

        return mapping.findForward("XSP");
    }


     /**
      * Get Public Health Case LocalID from session object.
      * @param investigationId : Long the investigationId
      * @param session : HttpSession the session
      * @param request : HttpServletRequest the request
      * @return String with value of Public Health Case Local id
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
            logger.fatal("ERROR in getPublicHealthCaseLocalID calling mainsession control", e);
            throw e;
        } 
        return publicHealthCaseLocalID;
    }
  
     /**
      * Get InvestigationProxyVO from the session object.
      * @param sPublicHealthCaseUID : String the sPublicHealthCaseUID
      * @param investigationForm : InvestigationForm
      * @param session : HttpSession the session
      * @return InvestigationProxyVO
      * @throws NEDSSAppConcurrentDataException
      * @throws Exception
      */
    private InvestigationProxyVO getOldProxyObject(String sPublicHealthCaseUID,
                           InvestigationForm investigationForm,
                           HttpSession session) throws NEDSSAppConcurrentDataException, Exception
    {

        InvestigationProxyVO proxy = null;
        MainSessionCommand msCommand = null;

        try
        {
          Long publicHealthCaseUID = new Long(sPublicHealthCaseUID);
          String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
          String sMethod = "getInvestigationProxy";
          Object[] oParams = new Object[] { publicHealthCaseUID };

          MainSessionHolder holder = new MainSessionHolder();
          msCommand = holder.getMainSessionCommand(session);

          ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

          proxy = (InvestigationProxyVO)arr.get(0);
          investigationForm.reset();
          investigationForm.setOldProxy(proxy);
          Collection<Object> DSContactColl= new ArrayList<Object>();
          if(proxy.getTheCTContactSummaryDTCollection()!=null){
              DSContactColl= proxy.getTheCTContactSummaryDTCollection();
          }
          NBSContext.store(session, NBSConstantUtil.DSContactColl, DSContactColl);
          
          //investigationForm.setProxy(proxy);
          PersonVO personVO = this.getPersonVO(NEDSSConstants.PHC_PATIENT,proxy);
          investigationForm.setPatient(personVO);
          investigationForm.setOldRevision((PersonVO)personVO.deepCopy());

          investigationForm.setLoadedFromDB(true);
        }
        catch (NEDSSAppConcurrentDataException ncde)
        {
        	logger.error("Data Exception in InvestigationViewLoad.getOldProxyObjec: " + ncde.getMessage());
          throw new NEDSSAppConcurrentDataException("Concurrent access occurred in InvestigationViewLoad.getOldProxyObject : " + ncde.toString());
        }
        catch (Exception exp)
        {
        	logger.error("Data Exception in InvestigationViewLoad.getOldProxyObjec: " + exp.getMessage());
          throw new Exception("InvestigationViewLoad: getOldProxyObject");
        } 
        return proxy;
    }

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

    private String getActionForward(String investigationFormCd, HttpServletRequest request)
    {
        String path = "/error";
        boolean isPamCase =false;

        if (NBSConstantUtil.INV_FORM_GEN.equals(investigationFormCd))
        {
            path = "GenericViewLoad";
        }
        else if (NBSConstantUtil.INV_FORM_RVCT.equals(investigationFormCd))
        {
            isPamCase= true;
            path = "TuberculosisViewLoad";
        }
        else if (NBSConstantUtil.INV_FORM_MEA.equals(investigationFormCd))
        {
            path = "MeaslesViewLoad";
        }
        else if (NBSConstantUtil.INV_FORM_CRS.equals(investigationFormCd))
        {
          path = "CrsViewLoad";
        }
        else if (NBSConstantUtil.INV_FORM_RUB.equals(investigationFormCd))
        {
          path = "RubellaViewLoad";
        }
        else if (NBSConstantUtil.INV_FORM_PER.equals(investigationFormCd))
        {
          path = "PertussisViewLoad";
        }
        else if (investigationFormCd.startsWith("INV_FORM_HEP"))
        {
          path = "HepatitisViewLoad";
        }
        else if (investigationFormCd.startsWith("INV_FORM_BMD"))
        {
          path = "BmirdViewLoad";
        }
        else if (NBSConstantUtil.INV_FORM_VAR.equals(investigationFormCd))
        {
            isPamCase= true;
            path = "VaricellaViewLoad";
        }
        //R4.0 P.O.C
        
        else 
        { 
            path = "DMBViewLoad";
        } 
        
        return path;
    }  
}