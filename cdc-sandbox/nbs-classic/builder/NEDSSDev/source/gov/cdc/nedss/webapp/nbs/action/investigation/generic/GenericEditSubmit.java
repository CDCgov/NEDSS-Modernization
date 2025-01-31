package gov.cdc.nedss.webapp.nbs.action.investigation.generic;
/**
 * <p>Title: GenericEditSubmit</p>
 * <p>Description: This is a Submit action class for edit investigation for all the Generic conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.BaseEditSubmit;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.form.investigation.InvestigationForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GenericEditSubmit extends BaseEditSubmit  {


  //For logging
  static final LogUtils logger = new LogUtils(GenericEditSubmit.class.getName());
  static String strLock = "lock";

   public GenericEditSubmit()
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
   public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
   throws IOException, ServletException {

      HttpSession session = request.getSession();
      NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
      // Context
      // are we edit or create?
      String sContextAction = HTMLEncoder.encodeHtml(request.getParameter("ContextAction"));
      String sCurrentTask = NBSContext.getCurrentTask(session);

      if(sContextAction == null)
      {
	session.setAttribute("error", "contextAction is " +  sContextAction +" sCurrentTask " + sCurrentTask);
	throw new ServletException("ContextAction is null");
      }

	if(sContextAction.equals("Submit"))
	{
	  try
	  {
	    logger.debug("Handling edit action in InvestigationEditSubmit");
	    this.editHandler(mapping, form, request, session, nbsSecurityObj, sCurrentTask);

	  }
	  catch (NEDSSAppConcurrentDataException e)
	  {
	    logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ", e);
	    return mapping.findForward("dataerror");
	  }
	}
	return mapping.findForward(sContextAction);

  }



   private void editHandler(ActionMapping mapping,
					       ActionForm form,
					       HttpServletRequest request,
					       HttpSession session,
					       NBSSecurityObj nbsSecurityObj,
					       String sCurrentTask) throws ServletException,
										     NEDSSAppConcurrentDataException
    {
      boolean investigationProxyVODirty = false;

      InvestigationForm investigationForm = (InvestigationForm)form;
      InvestigationProxyVO newInvestigationProxyVO = investigationForm.getProxy();
      InvestigationProxyVO oldInvestigationProxyVO = investigationForm.getOldProxy();

	  // Check for the case status change
	  boolean caseStatusChanged = caseStatusChanged(oldInvestigationProxyVO, newInvestigationProxyVO);

      PersonVO revision = investigationForm.getPatient();
      PersonVO oldRevision  = investigationForm.getOldRevision();
      // set up the ldf collection
      if(investigationForm.getLdfCollection()!= null)
      {
        HashMap<Object,Object> map = new HashMap<Object,Object>();
        HashMap<Object,Object> invLdfMap = new HashMap<Object,Object>();
        String businessObjName = null;

        // use the new API to retrieve custom field collection
        // to handle multiselect fields (xz 01/11/2005)
        Collection<Object>  coll = extractLdfDataCollection(investigationForm, request);
       Iterator<Object>  it = coll.iterator();
        while (it.hasNext())
        {
          StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
          if (stateDT != null && stateDT.getBusinessObjNm() != null)
          {
            if (stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.INVESTIGATION_LDF))
            {
              stateDT.setItDirty(true);
              businessObjName = stateDT.getBusinessObjNm();
              if(stateDT.getLdfUid()!=null)
              invLdfMap.put(stateDT.getLdfUid(), stateDT);
            }

            if (stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.PATIENT_LDF))
            {
              stateDT.setItDirty(true);
              stateDT.setItDirty(true);
              if(stateDT.getLdfUid()!=null)
              map.put(stateDT.getLdfUid(), stateDT);

            }

           }
          }

           if(invLdfMap.values().size()>0)
           newInvestigationProxyVO.setTheStateDefinedFieldDataDTCollection(new ArrayList<Object>(invLdfMap.values()));

           if(map.values().size()>0)
           revision.setTheStateDefinedFieldDataDTCollection(map.values());

           newInvestigationProxyVO.setBusinessObjNm(businessObjName);
           oldInvestigationProxyVO.setBusinessObjNm(businessObjName);

        }


      Collection<Object>  personColl = new ArrayList<Object> ();
      revision.setItDirty(true);
      revision.setItNew(false);
      revision.getThePersonDT().setItDirty(true);
      revision.getThePersonDT().setItNew(false);
      PersonUtil.setPatientForEventEdit(revision, request);
      personColl.add(revision);
      newInvestigationProxyVO.setThePersonVOCollection(personColl);

      //set PublicHealthCaseVO stuff, confirmationMethods, ActIdDT
      PublicHealthCaseVO phcVO = newInvestigationProxyVO.getPublicHealthCaseVO();
      PublicHealthCaseVO oldPhcVO = oldInvestigationProxyVO.getPublicHealthCaseVO();

      if(request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.sharedInd") == null)
      phcVO.getThePublicHealthCaseDT().setSharedInd("F");

      super.setConfirmationMethods(request, oldPhcVO);
      super.setActIdForPublicHealthCase(phcVO, oldPhcVO);

      //set the dateAssignedToInvestigation
      Collection<Object>  oldParticipationDTCollection  = oldInvestigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
      super.setParticipationsForEdit(form, request, investigationProxyVODirty);
      super.setDateAssignToInvestigation(oldParticipationDTCollection, request);
      super.setDateAssignToInvestigation(newInvestigationProxyVO.getTheParticipationDTCollection(), request);

      Collection<ObservationVO>  obsColl = newInvestigationProxyVO.getTheObservationVOCollection();
      Collection<ObservationVO>  oldObsColl = oldInvestigationProxyVO.getTheObservationVOCollection();

      //get the parent Observation
     Iterator<ObservationVO>  itObs = oldObsColl.iterator();
      Long parentObservationUID = null;

      ObservationVO parentObservationVO = super.getParentObservation(oldInvestigationProxyVO);
      parentObservationUID = parentObservationVO.getTheObservationDT().getObservationUid();

      int tempID = -1;

      TreeMap<Object,Object> observationMap = investigationForm.getObservationMap();
      TreeMap<Object,Object> obsActTreeMap = super.setObservations(obsColl, observationMap, "T",parentObservationVO.getTheActRelationshipDTCollection(), parentObservationUID, "InvFrmQ", request, tempID);
      Collection<ObservationVO>  newObservationCollection  = (ArrayList<ObservationVO> )obsActTreeMap.get("observations");
      Collection<Object>  newActRelationshipCollection  = (ArrayList<Object> )obsActTreeMap.get("actrelations");
      tempID = ((Integer)obsActTreeMap.get("tempID")).intValue();

      newObservationCollection.add(parentObservationVO);
      super.copyPublicHealthCaseDTValues(newInvestigationProxyVO, oldInvestigationProxyVO);

      newInvestigationProxyVO.setTheObservationVOCollection(newObservationCollection);
      newInvestigationProxyVO.setTheActRelationshipDTCollection(newActRelationshipCollection);
      newInvestigationProxyVO.setPublicHealthCaseVO(oldInvestigationProxyVO.getPublicHealthCaseVO());
      newInvestigationProxyVO.setTheNotificationSummaryVOCollection(oldInvestigationProxyVO.getTheNotificationSummaryVOCollection());

      newInvestigationProxyVO.setItDirty(true);
      newInvestigationProxyVO.setItNew(false);
      newInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setItDirty(true);
      newInvestigationProxyVO.getPublicHealthCaseVO().setItDirty(true);

	  // set the case status dirty flag
	  if((newInvestigationProxyVO != null) &&
		(newInvestigationProxyVO.getPublicHealthCaseVO() != null) &&
		(newInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT() != null)){
			newInvestigationProxyVO.
				getPublicHealthCaseVO().
				getThePublicHealthCaseDT().
				setCaseStatusDirty(caseStatusChanged);

	  }

      Long publicHealthCaseUID = null;
      try
      {

	  publicHealthCaseUID =  super.sendProxyToInvestigationEJB(investigationForm, session,request);
      }
      catch (NEDSSAppConcurrentDataException e)
      {
	logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ", e);
	throw new NEDSSAppConcurrentDataException("Concurrent access occurred in InterventionEditSubmit : " + e.toString());
      }
      catch(Exception e)
      {
	  throw new ServletException(e.toString());
      }
   }

}//InvestigationEditSubmit