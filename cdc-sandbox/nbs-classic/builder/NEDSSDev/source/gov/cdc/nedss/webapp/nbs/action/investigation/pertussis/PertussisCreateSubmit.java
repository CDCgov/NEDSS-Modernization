package gov.cdc.nedss.webapp.nbs.action.investigation.pertussis;
/**
 * <p>Title: PertussisCreateSubmit</p>
 * <p>Description: This is a Submit action class for create investigation for all the Pertussis conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.pertussis.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;

public class PertussisCreateSubmit
    extends BaseCreateSubmit
{

  //For logging
  static final LogUtils logger = new LogUtils(PertussisCreateSubmit.class.
                                              getName());
  static String strLock = "lock";

  public PertussisCreateSubmit()
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
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws
      IOException, ServletException
  {

    logger.debug("inside InvestigationSubmit");

    HttpSession session = request.getSession(false);
    if (session == null)
    {
      logger.debug("error no session in InvestigationSubmit");

      return mapping.findForward("login");
    }


    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    String sContextAction = request.getParameter("ContextAction");
    logger.debug("ContextAction: " + sContextAction);

    if (sContextAction == null)
    {
      session.setAttribute("error",
                           "null ContextAction in investigationCreateSubmit");

      throw new ServletException("null ContextAction in investigationCreateSubmit");
    }

    if (sContextAction.equals(NBSConstantUtil.SUBMIT) ||
        sContextAction.equals(NBSConstantUtil.SubmitNoViewAccess))
    {

      try
      {
        this.createHandler(mapping, form, request, session, nbsSecurityObj);
      }
      catch (NEDSSAppConcurrentDataException ncde)
      {
        logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
                     ncde);
        return mapping.findForward("dataerror");
      }
    }

    boolean viewInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.
        INVESTIGATION, NBSOperationLookup.VIEW);
    boolean checkInvestigationAutoCreatePermission = nbsSecurityObj.
        getPermission(NBSBOLookup.INVESTIGATION,
                      NBSOperationLookup.AUTOCREATE,
                      ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                      ProgramAreaJurisdictionUtil.ANY_JURISDICTION,
                      ProgramAreaJurisdictionUtil.SHAREDISTRUE);
    if (checkInvestigationAutoCreatePermission && !viewInvestigation)
    {
      sContextAction = NBSConstantUtil.SubmitNoViewAccess;
      
    }
    return mapping.findForward(sContextAction);
  }

  private void createHandler(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request, HttpSession session,
                             NBSSecurityObj nbsSecurityObj) throws
      NEDSSAppConcurrentDataException, ServletException
  {

    Date start = new Date();
    InvestigationForm investigationForm = (InvestigationForm) form;
    InvestigationProxyVO investigationProxyVO = investigationForm.getProxy();
    String investigationFormCd = (String) NBSContext.retrieve(session,
        NBSConstantUtil.DSInvestigationCode);
    ////##!!VOTester.createReport(investigationForm.getProxy(), "create-store-pre");
    Long patientUid = (Long) NBSContext.retrieve(session,
                                                 NBSConstantUtil.
                                                 DSPersonSummary);
    int tempID = -1;

    // Get the public health case
    PublicHealthCaseVO phcVO = investigationProxyVO.getPublicHealthCaseVO();

    if (phcVO != null)
    {
      phcVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(new Long(
          tempID--));
      phcVO.getThePublicHealthCaseDT().setStatusCd("A");
      phcVO.getThePublicHealthCaseDT().setCaseTypeCd("I");
      phcVO.getThePublicHealthCaseDT().setGroupCaseCnt(new Integer(1));
      String[] arr = request.getParameterValues("confirmationMethods");
      String confirmationDate = (String) request.getParameter(
          "confirmationDate");
      Collection<Object>  confMethodColl = new ArrayList<Object> ();
      Collection<Object>  actIdColl = new ArrayList<Object> ();
      if (request.getParameter(
          "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.sharedInd") == null)
      {
        //logger.debug("Inside request");
        phcVO.getThePublicHealthCaseDT().setSharedInd("F");
      }
      else
      {
        logger.debug("Value of PARAMETER:  " +
                     request.getParameter(
            "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.sharedInd"));
      }
      if(((InvestigationForm) form).getLdfCollection()!= null)
      {
        ArrayList<Object> list = new ArrayList<Object> ();
        // use the new API to retrieve custom field collection
        // to handle multiselect fields (xz 01/11/2005)
        Collection<Object>  coll = extractLdfDataCollection(investigationForm, request);
       Iterator<Object>  it = coll.iterator();
        while (it.hasNext()) {
          StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
          if (stateDT != null && stateDT.getBusinessObjNm() != null) {
            if (stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.INVESTIGATION_BMD_LDF) ||
                stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.INVESTIGATION_HEP_LDF) ||
                stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.INVESTIGATION_NIP_LDF) ||
                stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.INVESTIGATION_LDF)) {
              list.add(stateDT);
              investigationProxyVO.setBusinessObjNm(stateDT.getBusinessObjNm());
            }
          }
        }
        investigationProxyVO.setTheStateDefinedFieldDataDTCollection(list);
      }
      if (!investigationFormCd.startsWith("INV_FORM_HEP") &&
          !investigationFormCd.equals(NBSConstantUtil.INV_FORM_CRS))
      {
        if (arr != null)
        {

          for (int i = 0; i < arr.length; i++)
          {
            String strVal = arr[i];
            if (strVal != null && !strVal.trim().equals(""))
            {
              ConfirmationMethodDT cm = new ConfirmationMethodDT();
              cm.setConfirmationMethodCd(strVal);
              cm.setConfirmationMethodTime_s(confirmationDate);
              cm.setItNew(true);
              confMethodColl.add(cm);
            }
            else
            {
              ConfirmationMethodDT cm = new ConfirmationMethodDT();
              cm.setConfirmationMethodTime_s(confirmationDate);
              cm.setConfirmationMethodCd("NA");
              cm.setItNew(true);
              confMethodColl.add(cm);
            }
          }
        }
        else
        {

          ConfirmationMethodDT cm = new ConfirmationMethodDT();
          cm.setConfirmationMethodCd("NA");
          cm.setConfirmationMethodTime_s(confirmationDate);
          cm.setItNew(true);
          confMethodColl.add(cm);
        }

        if (confMethodColl.size() > 0)
        {
          phcVO.setTheConfirmationMethodDTCollection(confMethodColl);
        }
        else
        {
          phcVO.setTheConfirmationMethodDTCollection(null);

        }
      }
      int j = 1;
      if (phcVO.getTheActIdDTCollection() != null)
      {
       Iterator<Object>  itr = phcVO.getTheActIdDTCollection().iterator();
        while (itr.hasNext())
        {
          ActIdDT actIdDT = (ActIdDT) itr.next();
          actIdDT.setActIdSeq(new Integer(j++));
          if (actIdDT.getRootExtensionTxt() != null &&
              actIdDT.getActIdSeq().equals(new Integer(1)))
          {
            actIdDT.setTypeCd(NEDSSConstants.ACT_ID_STATE_TYPE_CD);
          }

          actIdDT.setItNew(true);
          actIdDT.setItDirty(false);
          
        }

      }

      phcVO.setItNew(true);
      phcVO.setItDirty(false);

    
    }

    tempID = super.setPatientRevisionForCreate(patientUid, tempID,
                                              investigationProxyVO,
                                              investigationForm, request,
                                              session);
    super.setParticipationsForCreate(investigationProxyVO, request, session);
    tempID = super.setObservationForCreate(investigationProxyVO, tempID, request,
                                          investigationFormCd);
    super.setActRelationshipForCreate(investigationProxyVO,
                                     investigationFormCd);
    if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_PER))
    {
      PertussisInvestigationUtil util = new PertussisInvestigationUtil();
      tempID = util.setBatchEntryObseravationsForCreate(investigationForm, request,
          tempID, investigationFormCd);
    }

    investigationProxyVO.setItNew(true);
    investigationProxyVO.setItDirty(false);
    logger.debug(" InvestigationProxyVO.isItDirty: " +
                 investigationProxyVO.isItDirty() +
                 " InvestigationProxyVO.isItNew: " +
                 investigationProxyVO.isItNew());
    ////##!!VOTester.createReport(investigationProxyVO, "create-store-post");

    Long publicHealthCaseUID = null;
    String sCurrentTask = (String) NBSContext.getCurrentTask(session);

    try
    {
      if (sCurrentTask.equals("CreateInvestigation1"))
      {
        publicHealthCaseUID = sendProxyToInvestigationEJB(
            investigationProxyVO, session, request);
      }
      else if (sCurrentTask.equals("CreateInvestigation2") ||
               sCurrentTask.equals("CreateInvestigation3") ||
               sCurrentTask.equals("CreateInvestigation4") ||
               sCurrentTask.equals("CreateInvestigation5") ||
               sCurrentTask.equals("CreateInvestigation6") ||
               sCurrentTask.equals("CreateInvestigation7") ||
               sCurrentTask.equals("CreateInvestigation8") ||
               sCurrentTask.equals("CreateInvestigation9") )
      {
        publicHealthCaseUID = sendProxyWithAutoAssoc(investigationProxyVO,
            session, request, sCurrentTask);

      }
      else if(sCurrentTask.equals("CreateInvestigation10") || sCurrentTask.equals("CreateInvestigation11")){
    	  Object DSDocumentUID = NBSContext.retrieve(session, NBSConstantUtil.DSDocumentUID);
    	  ActRelationshipDT actDoc = new ActRelationshipDT();
    	  actDoc.setItNew(true);
    	  
    	  actDoc.setSourceActUid(new Long(DSDocumentUID.toString()));
    	  actDoc.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
    	  actDoc.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
    	  actDoc.setTargetActUid(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
    	  actDoc.setTargetClassCd(NEDSSConstants.CASE);
    	  actDoc.setRecordStatusCd(NEDSSConstants.ACTIVE);
    	  actDoc.setTypeCd(NEDSSConstants.DocToPHC);
    	  investigationProxyVO.getTheActRelationshipDTCollection().add(actDoc);
    	  publicHealthCaseUID = super.sendProxyToInvestigationEJB(investigationProxyVO, session, request);
      }
    }
    catch (NEDSSAppConcurrentDataException ncde)
    {
      throw new NEDSSAppConcurrentDataException(
          "Concurrent access occurred in InvestigationCreateSubmit : " +
          ncde.toString());
    }

    catch (Exception e)
    {
    	logger.error("Exception in Pertussis Create Submit: " + e.getMessage());
      throw new ServletException(e.toString());
    }

    logger.info("Done storing investigation. publicHealthCaseUID= " +
                publicHealthCaseUID);
  }


  }