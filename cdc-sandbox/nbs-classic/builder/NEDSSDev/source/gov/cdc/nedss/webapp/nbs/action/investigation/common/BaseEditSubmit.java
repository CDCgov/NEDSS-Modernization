package gov.cdc.nedss.webapp.nbs.action.investigation.common;
/**
 *
 * <p>Title: BaseEditSubmit</p>
 * <p>Description: This is a Base Submit action class for Edit Invesigation all the EditSubmit classes extend this class.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NEDSSVOUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.ObservationUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.util.VOTester;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird.BMIRDInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.hepatitis.HepatitisInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.crs.CRSInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.pertussis.PertussisInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.form.investigation.InvestigationForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.SRTValues;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BaseEditSubmit extends CommonAction  {


  //For logging
  static final LogUtils logger = new LogUtils(BaseEditSubmit.class.getName());
  static String strLock = "lock";

   public BaseEditSubmit()
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

      logger.debug("inside InvestigationSubmit");
      HttpSession session = request.getSession();
      if (session == null)
      {
	logger.debug("error no session in InvestigationSubmit");
	return mapping.findForward("login");
      }

      NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
      // Context
      // are we edit or create?
      String sContextAction = request.getParameter("ContextAction");
      String sCurrentTask = NBSContext.getCurrentTask(session);

      if(sContextAction == null)
      {
	session.setAttribute("error", "contextAction is " +  HTMLEncoder.encodeHtml(sContextAction) +" sCurrentTask " + sCurrentTask);
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

	/**
	 * This method compares the old investigation and the new investigation to
	 * identify if case status was changed during this transaction
	 * @param oldInvestigationProxyVO old Investigation object
	 * @param newInvestigationProxyVO new Investigation object
	 * @return case status changed flag
	 */
	protected boolean caseStatusChanged(
		InvestigationProxyVO oldInvestigationProxyVO,
		InvestigationProxyVO newInvestigationProxyVO) {

		String oldCaseStatus = null;
		String newCaseStatus = null;

		PublicHealthCaseVO oldPublicHealthVO =
			oldInvestigationProxyVO.getPublicHealthCaseVO();
		PublicHealthCaseVO newPublicHealthVO =
			newInvestigationProxyVO.getPublicHealthCaseVO();

		if (oldPublicHealthVO != null) {
			PublicHealthCaseDT oldDt = oldPublicHealthVO.getThePublicHealthCaseDT();
			if (oldDt != null) {
				oldCaseStatus = oldDt.getCaseClassCd();
			}
		}

		if (newPublicHealthVO != null) {
			PublicHealthCaseDT newDt = newPublicHealthVO.getThePublicHealthCaseDT();
			if (newDt != null) {
				newCaseStatus = newDt.getCaseClassCd();
			}
		}

		// check if the case status has changed
		// return true if changed
		// else return false

		if (oldCaseStatus == null && newCaseStatus == null) {
			return false;
		} else if (
			(oldCaseStatus == null && newCaseStatus != null)
				|| (oldCaseStatus != null && newCaseStatus == null)) {
			return true;
		} else if (oldCaseStatus.equalsIgnoreCase(newCaseStatus)) {
			return false;
		} else {
			return true;
		}
	}

  protected Long sendProxyToInvestigationEJB(InvestigationForm investigationForm, HttpSession session, HttpServletRequest request)
	throws  NEDSSAppConcurrentDataException, Exception
  {

     MainSessionCommand msCommand = null;
     Long publicHealthCaseUID = null;
     try {
	  String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
	  String sMethod = "setInvestigationProxy";
	  logger.debug("sending investigationProxyVO to investigationproxyejb, via mainsession");

	  Object[] oParams = {investigationForm.getProxy()};

	  MainSessionHolder holder = new MainSessionHolder();
	  msCommand = holder.getMainSessionCommand(session);

	  logger.info("mscommand in InvestigationMeaslesStore is: " + msCommand);
	  ArrayList<?> resultUIDArr = new ArrayList<Object> ();
	  resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	  if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
	  {
	    logger.info("Create investigation worked!!! publicHealthCaseUID = " + resultUIDArr.get(0));
	    publicHealthCaseUID = (Long) resultUIDArr.get(0);
	  }

	  // Storing the objects in NBSContext for Edit
	  String sCurrentTask = NBSContext.getCurrentTask(session);
	  logger.debug("sCurrentTask: " + sCurrentTask);
	  if (sCurrentTask.equals("EditInvestigation4")
	      || sCurrentTask.equals("EditInvestigation5")
	      || sCurrentTask.equals("EditInvestigation6")
	      || sCurrentTask.equals("EditInvestigation10")
	      || sCurrentTask.equals("EditInvestigation11"))
	  {
	      InvestigationProxyVO investigationProxyVO = this.getInvestigationProxyVO(publicHealthCaseUID, investigationForm, session);
	      //NBSContext.store(session,NBSConstantUtil.DSPublicHealthCaseVO,investigationProxyVO.getPublicHealthCaseVO());
	  }


      }
      catch (Exception e)
      {
	  logger.fatal("ERROR sendProxyToInvestigationEJB calling mainsession control", e);
	  throw e;
      }
      investigationForm.reset();
      return publicHealthCaseUID;
  }

   private void editHandler(ActionMapping mapping,
					       ActionForm form,
					       HttpServletRequest request,
					       HttpSession session,
					       NBSSecurityObj nbsSecurityObj,
					       String sCurrentTask) throws ServletException,
										     NEDSSAppConcurrentDataException
    {
      Date start = new Date();
      boolean investigationProxyVODirty = false;
      NedssUtils utils = new NedssUtils();
      InvestigationForm investigationForm = (InvestigationForm)form;
      
      try {

    	  InvestigationProxyVO investigationProxyVO = investigationForm.getProxy();
    	  InvestigationProxyVO oldInvestigationProxyVO = investigationForm.getOldProxy();
    	  VOTester.createReport(investigationForm.getProxy(), "edit-store-pre");

    	  PublicHealthCaseVO phcVO = investigationProxyVO.getPublicHealthCaseVO();
    	  PublicHealthCaseVO oldPhcVO = oldInvestigationProxyVO.getPublicHealthCaseVO();
    	  if(phcVO != null)
    	  {
    		  //phcVO.getThePublicHealthCaseDT().setCaseClassDescTxt(request.getParameter("caseClassDescTxt"));
    		  if(!utils.equals(phcVO.getThePublicHealthCaseDT(), oldPhcVO.getThePublicHealthCaseDT(), PublicHealthCaseDT.class))
    		  {
    			  investigationProxyVODirty = true;
    			  phcVO.getThePublicHealthCaseDT().setItDirty(true);
    			  phcVO.getThePublicHealthCaseDT().setItNew(false);

    			  //PHCVO is set to dirty 4 time as numbered below
    			  //1. if PublicHealthCaseDT is dirty
    		  }
    		  logger.debug("Value of Request, before if:  " + request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.sharedInd"));
    		  if(request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.sharedInd") == null)
    		  {
    			  phcVO.getThePublicHealthCaseDT().setSharedInd("F");
    		  }
    		  else
    		  {
    			  logger.debug("Value of SharedInd parameter:  " + request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.sharedInd"));
    		  }
    		  String[] arr = request.getParameterValues("confirmationMethods");
    		  String confirmationDate = (String)request.getParameter("confirmationDate");
    		  logger.debug("confirmationMethods Array: " + arr);
    		  PersonVO revision = investigationForm.getPatient();
    		  //System.out.println("revision's lastname = " + revision.getPersonNameDT_s(0).getLastNm());
    		  PersonVO oldRevision  = investigationForm.getOldRevision();
    		  // set up the ldf collection
    		  if(investigationForm.getLdfCollection()!= null)
    		  {
    			  ArrayList<Object> list = new ArrayList<Object> ();
    			  ArrayList<Object> personLdflist = new ArrayList<Object> ();
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
    					  if (stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.PATIENT_LDF))
    					  {
    						  personLdflist.add(stateDT);
    					  }

    				  }
    			  }
    			  investigationProxyVO.setTheStateDefinedFieldDataDTCollection(list);
    			  revision.setTheStateDefinedFieldDataDTCollection(personLdflist);

    		  }



    		  //System.out.println("old revision's lastname = " + oldRevision.getPersonNameDT_s(0).getLastNm());

    		  boolean isRevisionChanged = false;
    		  Collection<Object>  personColl = new ArrayList<Object> ();

    		  NEDSSVOUtils voUtils = NEDSSVOUtils.getInstance();

    		  try
    		  {

    			  isRevisionChanged = voUtils.hasVOFieldChanged(revision, oldRevision);
    		  }
    		  catch (Exception e) {
    			  //System.out.println(" !!!!!!!!! NEDSSVOUtils error with Inv EditSubmit" + e.toString());
    			  e.printStackTrace();
    		  }
    		  if(isRevisionChanged) {
    			  //System.out.println("isRevisionChanged  is true so updating proxy .....");
    			  //VaccinationSubmit requestHelper = new VaccinationSubmit();
    			  revision.setItDirty(true);
    			  revision.setItNew(false);
    			  revision.getThePersonDT().setItDirty(true);
    			  revision.getThePersonDT().setItNew(false);
    			  PersonUtil.setPatientForEventEdit(revision, request);
    			  //System.out.println("revision's changed lastname = " + revision.getPersonNameDT_s(0).getLastNm());
    			  personColl.add(revision);
    			  investigationProxyVO.setThePersonVOCollection(personColl);
    		  }



    		  if(phcVO.getTheActIdDTCollection() != null && oldPhcVO.getTheActIdDTCollection() != null)
    		  {
    			  ActIdDT actIdDT = null;
    			  ActIdDT oldActIdDT = null;
    			  Iterator<Object>  itr = phcVO.getTheActIdDTCollection().iterator();
    			  Iterator<Object>  oldItr = oldPhcVO.getTheActIdDTCollection().iterator();
    			  if(itr.hasNext())  actIdDT = (ActIdDT)itr.next();
    			  if(oldItr.hasNext())  oldActIdDT = (ActIdDT)oldItr.next();
    			  if(actIdDT != null && oldActIdDT != null && !utils.equals(actIdDT, oldActIdDT, ActIdDT.class))
    			  {
    				  //4. if ActIdDT is changed, changed phcVO to dirty and increase sequence number
    				  investigationProxyVODirty = true;
    				  actIdDT.setItDirty(true);
    				  actIdDT.setItNew(false);
    				  phcVO.setItDirty(true);
    				  phcVO.setItNew(false);
    			  }


    		  }


    		  Collection<Object>  participationDTCollection  = investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
    		  logger.debug("...dateAssignedToInvestigation: ", participationDTCollection);
    		  if( participationDTCollection  != null)
    		  {
    			  Iterator<Object>  anIterator = null;
    			  for(anIterator = participationDTCollection.iterator(); anIterator.hasNext();)
    			  {
    				  ParticipationDT participationDT = (ParticipationDT)anIterator.next();
    				  logger.debug("...participationDT.getTypeCd(): " + participationDT.getTypeCd());
    				  if(participationDT.getTypeCd() != null && participationDT.getTypeCd().equals(NEDSSConstants.PHC_INVESTIGATOR))
    				  {
    					  String strDateAssigned = request.getParameter("dateAssignedToInvestigation");
    					  logger.debug("...dateAssignedToInvestigation: " + strDateAssigned);
    					  if (strDateAssigned != null && !strDateAssigned.trim().equals("") )
    					  {
    						  Timestamp fromTime = StringUtils.stringToStrutsTimestamp(strDateAssigned);
    						  logger.debug("Inside...dateAssignedToInvestigation: " + strDateAssigned);
    						  if(participationDT.getFromTime() == null || participationDT.getFromTime().getTime() != fromTime.getTime())
    						  {
    							  participationDT.setFromTime(fromTime);
    							  participationDT.setItDirty(true);
    							  participationDT.setItNew(false);
    							  investigationProxyVODirty = true;
    						  }
    					  }
    					  else
    					  {
    						  participationDT.setFromTime(null);
    						  participationDT.setItDirty(true);
    						  participationDT.setItNew(false);
    						  investigationProxyVODirty = true;
    					  }
    					  break;
    				  }


    			  }
    		  }
    	  }

    	  this.setParticipationsForEdit(form, request, investigationProxyVODirty);
    	  if(investigationProxyVODirty)
    	  {
    		  phcVO.setItDirty(true);
    		  phcVO.setItNew(false);
    	  }


    	  Collection<ObservationVO>  obsColl = investigationProxyVO.getTheObservationVOCollection();
    	  Collection<ObservationVO>  oldObsColl = oldInvestigationProxyVO.getTheObservationVOCollection();
    	  //System.out.println("obsColl the original size here is :" + obsColl.size());

    	  //this.setObsValueCodedToDefaultValues(obsColl);
    	  //System.out.println("obsColl the new size here is :" + obsColl.size());

    	  String investigationFormCd = null;
    	  String conditionCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
    	  logger.info("before bmird investigationProxyVODirty: " + investigationProxyVODirty + " conditionCd: " + conditionCd);
    	  ProgramAreaVO programAreaVO = null;
    	  SRTValues srtv = new SRTValues();
    	  String programAreaCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
    	  TreeMap<Object,Object> programAreaTreeMap = srtv.getProgramAreaConditions("('" + programAreaCd + "')");
    	  TreeMap<Object,Object> programAreaTreeMapWithLevelInd2 = srtv.getProgramAreaConditions("('" + programAreaCd + "')", 2);
    	  if (programAreaTreeMap != null)
    	  {
    		  programAreaVO = (ProgramAreaVO)programAreaTreeMap.get(conditionCd);
    		  if(programAreaVO != null)
    		  {
    			  investigationFormCd = programAreaVO.getInvestigationFormCd();
    			  logger.debug("investigationFormCd: " + programAreaVO.getInvestigationFormCd() + " programAreaDescTxt: " + programAreaVO.getStateProgAreaCdDesc());
    		  }
    		  else
    		  {
    			  programAreaVO = (ProgramAreaVO)programAreaTreeMapWithLevelInd2.get(conditionCd);
    			  logger.debug("programAreaVO in InvestigationViewLoad:" + programAreaVO);
    			  investigationFormCd = programAreaVO.getInvestigationFormCd();
    			  logger.debug( "in else :investigationFormCd: " + programAreaVO.getInvestigationFormCd() +   " programAreaDescTxt: " + programAreaVO.getStateProgAreaCdDesc());
    		  }
    	  }

    	  //get the parent Observation
    	  Iterator<ObservationVO>  itObs = oldObsColl.iterator();
    	  Long parentObservationUID = null;
    	  Collection<Object>  actrelationships = oldInvestigationProxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection();
    	  if(actrelationships != null)
    	  {
    		  //System.out.println("actrelationships size : " +actrelationships.size());
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
    	  TreeMap<Object,Object> obsActTreeMap = super.setObservations(obsColl, observationMap, "T",parentObservationVO.getTheActRelationshipDTCollection(), parentObservationUID, "InvFrmQ",request, tempID);
    	  Collection<ObservationVO>  newObservationCollection  = (ArrayList<ObservationVO> )obsActTreeMap.get("observations");
    	  Collection<Object>  newActRelationshipCollection  = (ArrayList<Object> )obsActTreeMap.get("actrelations");
    	  tempID = ((Integer)obsActTreeMap.get("tempID")).intValue();

    	  newObservationCollection.add(parentObservationVO);
    	  this.copyPublicHealthCaseDTValues(investigationProxyVO, oldInvestigationProxyVO);

    	  investigationProxyVO.setTheObservationVOCollection(newObservationCollection);
    	  investigationProxyVO.setTheActRelationshipDTCollection(newActRelationshipCollection);
    	  investigationProxyVO.setPublicHealthCaseVO(oldInvestigationProxyVO.getPublicHealthCaseVO());
    	  investigationProxyVO.setTheNotificationSummaryVOCollection(oldInvestigationProxyVO.getTheNotificationSummaryVOCollection());

    	  //new batch entry row
    	  if(investigationFormCd.equals(NBSConstantUtil.INV_FORM_PER))
    	  {
    		  PertussisInvestigationUtil pertussisInvestigationUtil = new PertussisInvestigationUtil();
    		  tempID = pertussisInvestigationUtil.setBatchEntryObseravationsForEdit(investigationForm, investigationFormCd, request, tempID);
    	  }
    	  if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_CRS))
    	  {

    		  CRSInvestigationUtil crsInvestigationUtil = new CRSInvestigationUtil();
    		  crsInvestigationUtil.setMultipleSelects(request,investigationForm.getProxy().getTheObservationVOCollection());
    	  }
    	  if(investigationFormCd.startsWith("INV_FORM_BMD"))
    	  {
    		  //int tempID = -1;
    		  BMIRDInvestigationUtil util = new BMIRDInvestigationUtil();
    		  tempID = util.setSuplimentalObseravationsAndARForEdit(investigationForm, parentObservationVO, investigationFormCd, request, tempID);
    		  tempID = util.setBatchEntryObseravationsForEdit(investigationForm, parentObservationVO, investigationFormCd, request, tempID);
    		  util.setMultipleSelectsForEdit(request, investigationForm.getProxy(), observationMap);
    		  util.setBmirdParticipationsForEdit(form, request, investigationProxyVODirty);
    	  }
    	  logger.info("after bmird investigationProxyVODirty: " + investigationProxyVODirty + " conditionCd: " + conditionCd);
    	  /**************************************************************************
    	   * NBSConstantUtil.INV_FORM_HEPGEN
    	   */

    	  if (investigationFormCd.startsWith("INV_FORM_HEP"))
    	  {
    		  // int tempID = -1;
    		  HepatitisInvestigationUtil hepatitisInvestigationUtil = new HepatitisInvestigationUtil();
    		  tempID = hepatitisInvestigationUtil.setSuplimentalObseravationsAndARForEdit(investigationForm, investigationFormCd, request, tempID);
    		  if(investigationForm.getProxy().getPublicHealthCaseVO()!=null)
    			  investigationForm.getProxy().getPublicHealthCaseVO().setTheConfirmationMethodDTCollection(null);
    		  hepatitisInvestigationUtil.setMultipleSelects(request,obsColl);
    	  }
    	  if (investigationFormCd.equals(NBSConstantUtil.INV_FORM_CRS))
    	  {
    		  CRSInvestigationUtil crsInvestigationUtil = new CRSInvestigationUtil();
    		  crsInvestigationUtil.setMultipleSelects(request,investigationForm.getProxy().getTheObservationVOCollection());
    		  if(investigationForm.getProxy().getPublicHealthCaseVO()!=null)
    			  investigationForm.getProxy().getPublicHealthCaseVO().setTheConfirmationMethodDTCollection(null);

    	  }

    	  investigationProxyVO.setItDirty(true);
    	  investigationProxyVO.setItNew(false);
    	  investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setItDirty(true);
    	  investigationProxyVO.getPublicHealthCaseVO().setItDirty(true);

      }catch (Exception e) {
    	  logger.error("Exception in BaseEditSubmit.editHandler(): " + e.toString());
    	  e.printStackTrace();
    	  throw new ServletException("Error while in BaseEditSubmit.editHandler : "+e.getMessage());
      }
      Long publicHealthCaseUID = null;
      try
      {
    	  publicHealthCaseUID =  sendProxyToInvestigationEJB(investigationForm, session,request);
      }
      catch (NEDSSAppConcurrentDataException e)
      {
    	  logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ", e);
    	  throw new NEDSSAppConcurrentDataException("Concurrent access occurred in baseEditSubmit : " + e.toString());
      }
      catch(Exception e)
      {
    	  throw new ServletException(e.toString());
      }
   }


    protected void setParticipationsForEdit(ActionForm form,  HttpServletRequest request, boolean investigationProxyVODirty)
    {
    	try {
    		InvestigationForm investigationForm = (InvestigationForm)form;
    		InvestigationProxyVO oldProxy = investigationForm.getOldProxy();
    		InvestigationProxyVO updatedProxyVO = investigationForm.getProxy();
    		Collection<Object>  oldParticipationCollection  = oldProxy.getPublicHealthCaseVO().getTheParticipationDTCollection();
    		Long publicHealthCaseUID = oldProxy.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
    		String sPublicHealthCaseUID = publicHealthCaseUID.toString();
    		Collection<Object>  participationDTCollection  = new ArrayList<Object> ();

    		//investigator
    		String newInvestigatorUID = request.getParameter("investigator.personUid");
    		logger.debug(" investigatorUid = " + newInvestigatorUID);
    		investigationProxyVODirty = super.createOrDeleteParticipation(newInvestigatorUID, publicHealthCaseUID, participationDTCollection, oldParticipationCollection, NEDSSConstants.PHC_INVESTIGATOR, "PSN", investigationProxyVODirty);

    		//  reporter
    		String newReporterUID = (String)request.getParameter("reporter.personUid");
    		logger.debug(" newReporterUID = " + newReporterUID);
    		investigationProxyVODirty = super.createOrDeleteParticipation(newReporterUID, publicHealthCaseUID, participationDTCollection, oldParticipationCollection, NEDSSConstants.PHC_REPORTER, "PSN", investigationProxyVODirty);

    		//  physician
    		String newPhysicianUID = (String)request.getParameter("physician.personUid");
    		logger.debug(" newPhysicianUID = " + newPhysicianUID);
    		investigationProxyVODirty = super.createOrDeleteParticipation(newPhysicianUID, publicHealthCaseUID, participationDTCollection, oldParticipationCollection, NEDSSConstants.PHC_PHYSICIAN, "PSN", investigationProxyVODirty);

    		//  reporting source
    		String newReportingSourceUID = (String)request.getParameter("reportingOrg.organizationUID");
    		logger.debug(" newReportingSourceUID = " + newReportingSourceUID);
    		investigationProxyVODirty = super.createOrDeleteParticipation(newReportingSourceUID, publicHealthCaseUID, participationDTCollection, oldParticipationCollection, NEDSSConstants.PHC_REPORTING_SOURCE, "ORG", investigationProxyVODirty);

    		//  hospital source
    		String newHospitalUID = (String)request.getParameter("hospitalOrg.organizationUID");
    		logger.debug(" newHospitalUID = " + newHospitalUID);
    		investigationProxyVODirty = super.createOrDeleteParticipation(newHospitalUID, publicHealthCaseUID, participationDTCollection, oldParticipationCollection, NEDSSConstants.HOSPITAL_NAME_ADMITTED, "ORG", investigationProxyVODirty);

    		//ABCs investigator, since version 1.1.3
    		String newABCsInvestigatorUID =
    				request.getParameter("abcInvestigator.personUid");
    		logger.debug(" newABCsInvestigatorUID = " + newABCsInvestigatorUID);
    		investigationProxyVODirty = super.createOrDeleteParticipation(
    				newABCsInvestigatorUID,
    				publicHealthCaseUID,
    				participationDTCollection,
    				oldParticipationCollection,
    				NEDSSConstants.ABC_PHC_INVESTIGATOR,
    				"PSN",
    				investigationProxyVODirty);

    		// effective from time need to be store in participation of patient
    		ParticipationDT patientPHCPart = this.getParticipation(NEDSSConstants.PHC_PATIENT, "PSN", oldParticipationCollection);
    		patientPHCPart.setFromTime(updatedProxyVO.getPublicHealthCaseVO().
    				getThePublicHealthCaseDT().getEffectiveFromTime());
    		if (participationDTCollection.size() > 0)
    			updatedProxyVO.setTheParticipationDTCollection(participationDTCollection);
    		else
    			updatedProxyVO.setTheParticipationDTCollection(null);
    	} catch (Exception ex) {
    		logger.error("Error in BaseEditSubmit.setParticipationsForEdit: "+ex.getMessage());
    		ex.printStackTrace();
    	}	

    }


    protected ParticipationDT getParticipation(String type_cd, String classCd,Collection<Object>  participationDTCollection)
    {

	ParticipationDT participationDT = null;
	if(participationDTCollection  != null && participationDTCollection.size() > 0)
	{
	   Iterator<Object>  anIterator = null;
	    for(anIterator = participationDTCollection.iterator(); anIterator.hasNext();)
	    {
		participationDT = (ParticipationDT)anIterator.next();
		if(participationDT.getSubjectEntityUid() != null && classCd.equals(participationDT.getSubjectClassCd()) && type_cd.equals(participationDT.getTypeCd()))
		{
		   logger.debug("participation exist for investigation: " + participationDT.getActUid() + " subjectEntity: " +  participationDT.getSubjectEntityUid());
		   return  participationDT;
		}
		else continue;
	    }
	}
	return null;
    }



    private InvestigationProxyVO getInvestigationProxyVO(Long publicHealthCaseUID,
						   InvestigationForm investigationForm,
						   HttpSession session)
    {

	InvestigationProxyVO proxy = null;
	MainSessionCommand msCommand = null;
	if (publicHealthCaseUID != null)
	{

	    try
	    {

		String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
		String sMethod = "getInvestigationProxy";
		Object[] oParams = new Object[] { publicHealthCaseUID };

		//  if(msCommand == null)
		//{
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);

		// }
		ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		proxy = (InvestigationProxyVO)arr.get(0);
		investigationForm.setOldProxy((InvestigationProxyVO)proxy.deepCopy());
		investigationForm.setProxy(proxy);
		investigationForm.setLoadedFromDB(true);
	    }
	    catch (Exception ex)
	    {

		if (session == null)
		{
		    logger.error("Error: no session, please login");
		}

		logger.fatal("getOldProxyObject: ", ex);
	    }
	}

	return proxy;
    }

    private void setActRelationshipForCreate(InvestigationProxyVO investigationProxyVO, Long parentObservationUID, Collection<Object>  obsColl, String investigationFormCd)
    {

	PublicHealthCaseVO phcVO = investigationProxyVO.getPublicHealthCaseVO();
	Collection<Object>  actRelColl = new ArrayList<Object> ();

	if (obsColl != null)
	{
		try {
			CachedDropDownValues srtc = new CachedDropDownValues();
			Iterator<Object>  itor = obsColl.iterator();

			while (itor.hasNext())
			{

				ObservationVO obsVO = (ObservationVO)itor.next();
				logger.debug("obsVO :"+ obsVO );
				logger.debug(":    formObservation.getTheobservationDT :" + obsVO.getTheObservationDT());
				logger.debug(":    formObservation.getTheobservationDT :" + obsVO.getTheObservationDT());
				logger.debug(":    formObservation.getTheobservationDT.getCd :" + obsVO.getTheObservationDT().getCd());
				logger.debug(":    formObservation.parentObservationUID :" + parentObservationUID);
				if (obsVO != null && obsVO.getTheObservationDT()!= null && obsVO.getTheObservationDT().getCd() != null)
				{

					ActRelationshipDT actRelForm = new ActRelationshipDT();
					actRelForm.setItNew(true);
					actRelForm.setSourceActUid(obsVO.getTheObservationDT().getObservationUid());
					actRelForm.setSourceClassCd(NEDSSConstants.CLASS_CD_OBS);
					actRelForm.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
					actRelForm.setRecordStatusCd(NEDSSConstants.ACTIVE);
					actRelForm.setTargetActUid(parentObservationUID);

					actRelForm.setTypeCd(NEDSSConstants.INV_FRM_Q); // Revisit this and change.
					actRelForm.setTypeDescTxt(srtc.getDescForCode("AR_TYPE",NEDSSConstants.PHC_INV_FORM));
					actRelForm.setTargetClassCd(NEDSSConstants.CLASS_CD_OBS);
					actRelColl.add(actRelForm);
				}
			}
		} catch (Exception ex) {
			logger.error("Error in BaseEditSubmit.setActRelationshipForCreate: "+ex.getMessage());
			ex.printStackTrace();
		}

	}

	if (actRelColl.size() > 0)
	    investigationProxyVO.setTheActRelationshipDTCollection(actRelColl);
	else
	    investigationProxyVO.setTheActRelationshipDTCollection(null);
    }







    protected void copyPublicHealthCaseDTValues(InvestigationProxyVO investigationProxyVO, InvestigationProxyVO oldInvestigationProxyVO)
    {
    	try {
    		PublicHealthCaseDT oldPHCDT = oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
    		PublicHealthCaseDT newPHCDT = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
    		oldPHCDT.setActivityDurationAmt(newPHCDT.getActivityDurationAmt());
    		oldPHCDT.setActivityDurationUnitCd(newPHCDT.getActivityDurationUnitCd());
    		oldPHCDT.setActivityFromTime(newPHCDT.getActivityFromTime());
    		oldPHCDT.setActivityToTime(newPHCDT.getActivityToTime());
    		oldPHCDT.setCaseClassCd(newPHCDT.getCaseClassCd());
    		oldPHCDT.setCd(newPHCDT.getCd());
    		oldPHCDT.setCdDescTxt(newPHCDT.getCdDescTxt());
    		oldPHCDT.setConfidentialityCd(newPHCDT.getConfidentialityCd());
    		oldPHCDT.setConfidentialityDescTxt(newPHCDT.getConfidentialityDescTxt());
    		oldPHCDT.setDetectionMethodCd(newPHCDT.getDetectionMethodCd());
    		oldPHCDT.setDetectionMethodDescTxt(newPHCDT.getDetectionMethodDescTxt());
    		oldPHCDT.setDiagnosisTime(newPHCDT.getDiagnosisTime());
    		oldPHCDT.setDiseaseImportedCd(newPHCDT.getDiseaseImportedCd());
    		oldPHCDT.setDiseaseImportedDescTxt(newPHCDT.getDiseaseImportedDescTxt());
    		oldPHCDT.setEffectiveDurationAmt(newPHCDT.getEffectiveDurationAmt());
    		oldPHCDT.setEffectiveDurationUnitCd(newPHCDT.getEffectiveDurationUnitCd());
    		oldPHCDT.setEffectiveFromTime(newPHCDT.getEffectiveFromTime());
    		oldPHCDT.setEffectiveToTime(newPHCDT.getEffectiveToTime());
    		oldPHCDT.setInvestigationStatusCd(newPHCDT.getInvestigationStatusCd());
    		oldPHCDT.setJurisdictionCd(newPHCDT.getJurisdictionCd());
    		oldPHCDT.setMmwrWeek(newPHCDT.getMmwrWeek());
    		oldPHCDT.setMmwrYear(newPHCDT.getMmwrYear());
    		oldPHCDT.setOutbreakFromTime(newPHCDT.getOutbreakFromTime());
    		oldPHCDT.setOutbreakInd(newPHCDT.getOutbreakInd());
    		oldPHCDT.setOutbreakName(newPHCDT.getOutbreakName());
    		oldPHCDT.setOutbreakToTime(newPHCDT.getOutbreakToTime());
    		oldPHCDT.setOutcomeCd(newPHCDT.getOutcomeCd());
    		oldPHCDT.setPatAgeAtOnset(newPHCDT.getPatAgeAtOnset());
    		oldPHCDT.setPatAgeAtOnsetUnitCd(newPHCDT.getPatAgeAtOnsetUnitCd());
    		oldPHCDT.setPatientGroupId(newPHCDT.getPatientGroupId());
    		oldPHCDT.setProgAreaCd(newPHCDT.getProgAreaCd());
    		oldPHCDT.setRepeatNbr(newPHCDT.getRepeatNbr());
    		oldPHCDT.setRptCntyCd(newPHCDT.getRptCntyCd());
    		oldPHCDT.setRptFormCmpltTime(newPHCDT.getRptFormCmpltTime());
    		oldPHCDT.setRptSourceCd(newPHCDT.getRptSourceCd());
    		oldPHCDT.setRptSourceCdDescTxt(newPHCDT.getRptSourceCdDescTxt());
    		oldPHCDT.setRptToCountyTime(newPHCDT.getRptToCountyTime());
    		oldPHCDT.setRptToStateTime(newPHCDT.getRptToStateTime());
    		oldPHCDT.setSharedInd(newPHCDT.getSharedInd());
    		oldPHCDT.setTransmissionModeCd(newPHCDT.getTransmissionModeCd());
    		oldPHCDT.setTransmissionModeDescTxt(newPHCDT.getTransmissionModeDescTxt());
    		oldPHCDT.setTxt(newPHCDT.getTxt());
    		oldPHCDT.setContactInvStatus(newPHCDT.getContactInvStatus());
    		oldPHCDT.setContactInvTxt(newPHCDT.getContactInvTxt());
    		oldPHCDT.setInfectiousFromDate(newPHCDT.getInfectiousFromDate());
    		oldPHCDT.setInfectiousToDate(newPHCDT.getInfectiousToDate());
    		oldPHCDT.setPriorityCd(newPHCDT.getPriorityCd());
    		oldPHCDT.setItDirty(true);
    	} catch (Exception ex) {
    		logger.error("Error in copyPublicHealthCaseDTValues: "+ex.getMessage());
    		ex.printStackTrace();
    	}
    }


    protected ObservationVO getParentObservation(InvestigationProxyVO oldInvestigationProxyVO)
    {
      Collection<ObservationVO>  oldObsColl =  oldInvestigationProxyVO.getTheObservationVOCollection();
     Iterator<ObservationVO>  itObs = oldObsColl.iterator();
      Long parentObservationUID = null;
      Collection<Object>  actrelationships = oldInvestigationProxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection();
      if(actrelationships != null)
      {
        //System.out.println("actrelationships size : " +actrelationships.size());
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

      ObservationVO parentObservationVO = ObservationUtils.findObservationByUid(oldObsColl, parentObservationUID);
      return parentObservationVO;
    }

    protected void setConfirmationMethods(HttpServletRequest request, PublicHealthCaseVO oldPHCVO)
    {

      String[] arr = request.getParameterValues("confirmationMethods");
      String confirmationDate = (String)request.getParameter("confirmationDate");
      logger.debug("confirmationMethods Array: " + arr);

      Collection<Object>  confMethodColl = new ArrayList<Object> ();
      if (arr != null)
      {
        for (int i=0; i < arr.length; i++)
        {
           logger.debug("confirmationMethod Array element: " + arr[i]);
           String strVal = arr[i];
           if(strVal != null && !strVal.trim().equals(""))
           {
           ConfirmationMethodDT cm = new ConfirmationMethodDT();
           cm.setConfirmationMethodTime_s(confirmationDate);
           cm.setConfirmationMethodCd(strVal);
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
         cm.setItDirty(false);
         confMethodColl.add(cm);
      }
      if (confMethodColl.size() > 0)
      {
         oldPHCVO.setTheConfirmationMethodDTCollection(confMethodColl);
         //2. if ConfirmationMethodDTCollection  is changed
         oldPHCVO.setItDirty(true);
         oldPHCVO.setItNew(false);
      }
      else
      {
         //3. if ConfirmationMethodDTCollection  is changed to null
         oldPHCVO.setTheConfirmationMethodDTCollection(null);
         oldPHCVO.setItDirty(true);
         oldPHCVO.setItNew(false);
      }

    }


    protected void setActIdForPublicHealthCase(PublicHealthCaseVO phcVO, PublicHealthCaseVO oldPhcVO)
    {
      if(phcVO.getTheActIdDTCollection() != null && oldPhcVO.getTheActIdDTCollection() != null)
      {
          ActIdDT actIdDT = null;
          ActIdDT oldActIdDT = null;
         Iterator<Object>  itr = phcVO.getTheActIdDTCollection().iterator();
         Iterator<Object>  oldItr = oldPhcVO.getTheActIdDTCollection().iterator();
          while(itr.hasNext() && oldItr.hasNext())
          {
              actIdDT = (ActIdDT) itr.next();
              oldActIdDT = (ActIdDT) oldItr.next();
              oldActIdDT.setRootExtensionTxt(actIdDT.getRootExtensionTxt());
              oldActIdDT.setItDirty(true);
              oldActIdDT.setItNew(false);
          }

      }

    }

    protected void setDateAssignToInvestigation(Collection<Object>  participationDTCollection, HttpServletRequest request)
    {
      //set the dateAssignedToInvestigation
      if( participationDTCollection  != null)
      {
         Iterator<Object>  anIterator = participationDTCollection.iterator();
          while(anIterator.hasNext())
          {
              ParticipationDT participationDT = (ParticipationDT)anIterator.next();
              if(participationDT.getTypeCd() != null && participationDT.getTypeCd().equals(NEDSSConstants.PHC_INVESTIGATOR))
              {
                  String strDateAssigned = request.getParameter("dateAssignedToInvestigation");
                  Timestamp fromTime = StringUtils.stringToStrutsTimestamp(strDateAssigned);
                  participationDT.setFromTime(fromTime);
              }
          }
      }

    }

}//BaseEditSubmit