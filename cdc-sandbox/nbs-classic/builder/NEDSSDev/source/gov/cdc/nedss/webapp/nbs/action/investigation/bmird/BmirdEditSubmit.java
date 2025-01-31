package gov.cdc.nedss.webapp.nbs.action.investigation.bmird;
/**
 *
 * <p>Title: BmirdEditSubmit</p>
 * <p>Description: This is a submit action class for change diagnosis for the Bmird conditions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

public class BmirdEditSubmit extends BaseEditSubmit  {


  //For logging
  static final LogUtils logger = new LogUtils(BmirdEditSubmit.class.getName());
  static String strLock = "lock";

   public BmirdEditSubmit()
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
          catch (Exception e)
          {
            logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ", e);
            throw new ServletException("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! "+e.getMessage(),e);
          }

	}
	return mapping.findForward(sContextAction);

  }

  /**
   * sets the dirty flag to proxy, sets LDF, Revision Patient and sends it to backend
   * @param mapping : ActionMapping
   * @param form : ActionForm
   * @param request : HttpServletRequest
   * @param session : HttpSession
   * @param nbsSecurityObj :
   * @param sCurrentTask : NBSSecurityObj
   * @throws NEDSSAppConcurrentDataException
   * @throws Exception
   */

   private void editHandler(ActionMapping mapping,
					       ActionForm form,
					       HttpServletRequest request,
					       HttpSession session,
					       NBSSecurityObj nbsSecurityObj,
					       String sCurrentTask) throws  NEDSSAppConcurrentDataException, Exception
    {
      boolean investigationProxyVODirty = false;
      NedssUtils utils = new NedssUtils();

      InvestigationForm investigationForm = (InvestigationForm)form;
      InvestigationProxyVO newInvestigationProxyVO = investigationForm.getProxy();
      InvestigationProxyVO oldInvestigationProxyVO = investigationForm.getOldProxy();

      try {
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
    				  if (stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.INVESTIGATION_BMD_LDF))
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
    			  newInvestigationProxyVO.setTheStateDefinedFieldDataDTCollection(new ArrayList(invLdfMap.values()));

    		  if(map.values().size()>0)
    			  revision.setTheStateDefinedFieldDataDTCollection(map.values());

    		  newInvestigationProxyVO.setBusinessObjNm(businessObjName);
    		  oldInvestigationProxyVO.setBusinessObjNm(businessObjName);
    	  }

    	  //set revision person

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

    	  String conditionCd = newInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
    	  String programAreaCd = newInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();

    	  CachedDropDownValues cdv = new CachedDropDownValues();
    	  ProgramAreaVO programAreaVO = cdv.getProgramAreaConditionLevel(programAreaCd, conditionCd);
    	  String investigationFormCd = programAreaVO.getInvestigationFormCd();

    	  //get the parent Observation
    	  Iterator<ObservationVO>  itObs = oldObsColl.iterator();
    	  Long parentObservationUID = null;
    	  Collection<Object>  actrelationships = oldInvestigationProxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection();
    	  if(actrelationships != null)
    	  {
    		  Iterator<Object>  actIt = actrelationships.iterator();
    		  while(actIt.hasNext())
    		  {
    			  ActRelationshipDT actDT = (ActRelationshipDT)actIt.next();
    			  Long publicHealthCaseUID = oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
    			  if(actDT.getTypeCd().equals(NEDSSConstants.PHC_INV_FORM))
    			  {
    				  parentObservationUID = actDT.getSourceActUid();
    				  break;
    			  }
    		  }
    	  }
    	  int tempID = -1;
    	  ObservationVO parentObservationVO = ObservationUtils.findObservationByUid(oldObsColl, parentObservationUID);

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

    	  //Bmird specific stuff like supplimental and batch-entry
    	  BMIRDInvestigationUtil util = new BMIRDInvestigationUtil();
    	  tempID = util.setSuplimentalObseravationsAndARForEdit(investigationForm, parentObservationVO,investigationFormCd, request, tempID);
    	  tempID = util.setBatchEntryObseravationsForEdit(investigationForm, parentObservationVO, investigationFormCd, request, tempID);
    	  util.setMultipleSelectsForEdit(request, investigationForm.getProxy(), observationMap);
    	  util.setBmirdParticipationsForEdit(form, request, investigationProxyVODirty);

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
      }catch (Exception e) {
    	  logger.error("Exception in Bmird Edit Handler: " + e.toString());
    	  e.printStackTrace();
    	  throw new Exception("Error during Bmird Edit handler: "+e.getMessage());
      }
      Long publicHealthCaseUID = null;
      try
      {
    	  publicHealthCaseUID =  sendProxyToInvestigationEJB(investigationForm, session,request);
      }
      catch (NEDSSAppConcurrentDataException e)
      {
    	  logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ", e);
    	  throw new NEDSSAppConcurrentDataException("Concurrent data access occurred in BmirdEditSubmit : " + e.toString());
      }
      catch(Exception e)
      {
    	  logger.error("Exception in Bmird Edit Handler: " + e.toString());
    	  e.printStackTrace();
    	  throw new Exception(e.toString());
      }
      logger.info("Done storing for Bmird Edit investigation. publicHealthCaseUID= " + publicHealthCaseUID);
    }


}//InvestigationEditSubmit