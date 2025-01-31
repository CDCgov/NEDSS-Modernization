package gov.cdc.nedss.webapp.nbs.action.investigation.common;
/**
 *
 * <p>Title: BaseCreateLoad</p>
 * <p>Description: This is a Base Submit action class for Create Invesigation all the create investigation extend this class.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Shailender Rachamalla
 */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.locator.dt.*;
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
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;

public class BaseCreateSubmit
    extends CommonAction
{

  //For logging
  static final LogUtils logger = new LogUtils(BaseCreateSubmit.class.
                                              getName());
  static String strLock = "lock";
  /**
   * empty constructor
   */
  public BaseCreateSubmit()
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

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws
      IOException, ServletException
  {
	  throw new ServletException();
  }
  /**
   * this methods calls EJB and sends proxy to setInvestigationProxy
   * @param investigationProxyVO : InvestigationProxyVO
   * @param session : HttpSession
   * @param request : HttpServletRequest
   * @return : HttpServletRequest
   * @throws NEDSSAppConcurrentDataException
   * @throws Exception
   */
  protected Long sendProxyToInvestigationEJB(InvestigationProxyVO
                                           investigationProxyVO,
                                           HttpSession session,
                                           HttpServletRequest request) throws
      NEDSSAppConcurrentDataException, Exception
  {

    MainSessionCommand msCommand = null;
    Long publicHealthCaseUID = null;
    try
    {

      String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
      String sMethod = "setInvestigationProxy";
      Object[] oParams = {investigationProxyVO};
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
      {
        publicHealthCaseUID = (Long) resultUIDArr.get(0);
      }

      //context store
      String investigationJurisdiction = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd();
      String programArea = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
      NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, publicHealthCaseUID.toString());
      NBSContext.store(session, NBSConstantUtil.DSInvestigationJurisdiction, investigationJurisdiction);
      NBSContext.store(session, NBSConstantUtil.DSInvestigationProgramArea, programArea);

    }
    catch (Exception e)
    {
      logger.fatal("ERROR calling mainsession control", e);
      throw e;
    }

    return publicHealthCaseUID;
  }
  /**
   * this methods calls EJB and sends proxy to sendProxyWithAutoAssoc
   * this method is used when invesigation is created from Morb
   * this method also makes association with lab or morb
   * @param investigationProxyVO : InvestigationProxyVO
   * @param session : HttpSession
   * @param request : HttpServletRequest
   * @param sCurrentTask : String
   * @return : Long
   * @throws NEDSSAppConcurrentDataException
   * @throws Exception
   */
  protected Long sendProxyWithAutoAssoc(InvestigationProxyVO
                                      investigationProxyVO,
                                      HttpSession session,
                                      HttpServletRequest request,
                                      String sCurrentTask) throws Exception
  {

    MainSessionCommand msCommand = null;
    Long publicHealthCaseUID = null;

    Object sObservationUID = NBSContext.retrieve(session, NBSConstantUtil.DSObservationUID);
    Object observationTypeCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSObservationTypeCd);
    Long DSObservationUID = new Long(sObservationUID.toString());
    try
    {

      String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
      String sMethod = "setInvestigationProxyWithAutoAssoc";

      Object[] oParams = { investigationProxyVO, DSObservationUID, observationTypeCd.toString()};
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);

      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
      {
        logger.info("Create investigation worked!!! publicHealthCaseUID = " + resultUIDArr.get(0));
        publicHealthCaseUID = (Long) resultUIDArr.get(0);
      }

      //context store
      String investigationJurisdiction = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd();
      String programArea = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
      NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, publicHealthCaseUID.toString());
      NBSContext.store(session, NBSConstantUtil.DSInvestigationJurisdiction, investigationJurisdiction);
      NBSContext.store(session, NBSConstantUtil.DSInvestigationProgramArea, programArea);

    }
    catch (Exception e)
    {
      logger.fatal("ERROR calling mainsession control", e);
      throw e;
    }

    return publicHealthCaseUID;
  }


  /**
   * this method sets the tempId to patient revision, sets the mprUid to parent uid
   * sets the electronic indicator, also sets the LDF data. Its protected methode and
   * is called by all CreateSubmit classes
   * @author shailender
   * @param patientUid : Long
   * @param tempID : int
   * @param proxy : InvestigationProxyVO
   * @param form : InvestigationForm
   * @param request : HttpServletRequest
   * @param session : HttpSession
   * @return :int
   */

  protected int setPatientRevisionForCreate(Long patientUid, int tempID,
                                          InvestigationProxyVO proxy,
                                          InvestigationForm form,
                                          HttpServletRequest request,
                                          HttpSession session)
  {

    PersonVO patient = null;
    Collection<Object>  patientColl = new ArrayList<Object> ();

    if (form.getPatient() != null)
    {
      patient = form.getPatient();
      patient.setItNew(true);
      patient.getThePersonDT().setItNew(true);
      patient.getThePersonDT().setPersonParentUid(patientUid);
      patient.getThePersonDT().setPersonUid(new Long(tempID--));
      patient.getThePersonDT().setCd(NEDSSConstants.PAT);
      patient.getThePersonDT().setStatusTime(new Timestamp(new Date().getTime()));
      patient.getThePersonDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      if(form.getLdfCollection()!= null)
      {
        ArrayList<Object> list = new ArrayList<Object> ();
        // use the new API to retrieve custom field collection
        // to handle multiselect fields (xz 01/11/2005)
        Collection<Object>  coll = extractLdfDataCollection(form, request);
        for (Iterator< Object> it = coll.iterator(); it.hasNext(); )
        {
          StateDefinedFieldDataDT  stateDT = (StateDefinedFieldDataDT)it.next();
          if (stateDT != null && stateDT.getBusinessObjNm() != null && stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.PATIENT_LDF))
          {
            list.add(stateDT);
          }
        }
        patient.setTheStateDefinedFieldDataDTCollection(list);
      }

      patient.getThePersonDT().setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
      /**
       * Calling the method from Vaccination to set the values for patient
       */
      //VaccinationSubmit vaccinationSubmit = new VaccinationSubmit();
      PersonUtil.setPatientForEventCreate(patient, request);

      //this.setPhysicalLocations(patient);
      patientColl.add(patient);

      if (patientColl.size() > 0)
      {
        proxy.setThePersonVOCollection(patientColl);
        //add revision so as to compare in editSubmit
      }
    }
    return tempID;
  } //createREvisionPatient

  /**
   * sets the participations for create investigation
   * @param investigationProxyVO : InvestigationProxyVO
   * @param request : HttpServletRequest
   * @param session : HttpSession
   */

  protected void setParticipationsForCreate(InvestigationProxyVO
                                          investigationProxyVO,
                                          HttpServletRequest request,
                                          HttpSession session)
  {

    Long phcUID = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
    Long revisionPatientUID = investigationProxyVO.getPersonVO_s(0).getThePersonDT().getPersonUid();
    Collection<Object>  partsColl = new ArrayList<Object> ();

    if (revisionPatientUID != null)
    {
      // patient PHC participation
      ParticipationDT participationDT = super.createParticipation(phcUID, revisionPatientUID.toString(), "PSN", NEDSSConstants.PHC_PATIENT);
      participationDT.setFromTime(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getEffectiveFromTime());
      partsColl.add(participationDT);
    }

    String strInvestigatorUID = (String) request.getParameter("investigator.personUid");
    if((strInvestigatorUID != null && !strInvestigatorUID.trim().equals("")) && phcUID != null)
    {
        ParticipationDT participationDT = super.createParticipation(phcUID, strInvestigatorUID, "PSN", NEDSSConstants.PHC_INVESTIGATOR);
        String strDateAssigned = request.getParameter("dateAssignedToInvestigation");
        Timestamp fromTime = StringUtils.stringToStrutsTimestamp(strDateAssigned);
        participationDT.setFromTime(fromTime);
        partsColl.add(participationDT);
    }

    //  reporter

    String strReporterUID = (String) request.getParameter("reporter.personUid");
    if (strReporterUID != null && !strReporterUID.trim().equals(""))
    {
      ParticipationDT participationDT = super.createParticipation(phcUID, strReporterUID, "PSN", NEDSSConstants.PHC_REPORTER);
      partsColl.add(participationDT);
    }

    //  physician

    String strPhysicianUID = (String) request.getParameter("physician.personUid");
    if (strPhysicianUID != null && !strPhysicianUID.trim().equals(""))
    {
      ParticipationDT participationDT = super.createParticipation(phcUID, strPhysicianUID, "PSN", NEDSSConstants.PHC_PHYSICIAN);
      partsColl.add(participationDT);
    }

    //  reporting source
    String reportingOrg = (String) request.getParameter("reportingOrg.organizationUID");
    if (reportingOrg != null && !reportingOrg.trim().equals(""))
    {
      ParticipationDT participationDT = super.createParticipation(phcUID, reportingOrg, "ORG", NEDSSConstants.PHC_REPORTING_SOURCE);
      partsColl.add(participationDT);
    }
    //  hospital participation

    String hospitalOrg = (String) request.getParameter("hospitalOrg.organizationUID");
    if (hospitalOrg != null && !hospitalOrg.trim().equals(""))
    {
      ParticipationDT participationDT = super.createParticipation(phcUID, hospitalOrg, "ORG", "HospOfADT");
      partsColl.add(participationDT);
    }

    //ABCs investigator participation, since version 1.1.3
    String abcInvestigatorUID = (String)
        request.getParameter("abcInvestigator.personUid");
    if((abcInvestigatorUID != null &&
        !abcInvestigatorUID.trim().equals("")) && phcUID != null)
    {
        ParticipationDT participationDT = super.createParticipation(
                                          phcUID,
                                          abcInvestigatorUID,
                                          "PSN",
                                          NEDSSConstants.ABC_PHC_INVESTIGATOR);
        partsColl.add(participationDT);
    }

    investigationProxyVO.setTheParticipationDTCollection(partsColl);
  }


   /**
    * sets negative tempId and newFlag to true for observations
    * @param investigationProxyVO : InvestigationProxyVO
    * @param tempID : int
    * @param request : HttpServletRequest
    * @param investigationFormCd : String
    * @return : int
    */

  protected int setObservationForCreate(InvestigationProxyVO investigationProxyVO,
                                      int tempID, HttpServletRequest request,
                                      String investigationFormCd)
  {

    Collection<ObservationVO>  obsColl = investigationProxyVO.getTheObservationVOCollection();
    ArrayList<ObservationVO> newObsColl = new ArrayList<ObservationVO> ();

    if (obsColl != null)
    {
    	try {

    		Iterator<ObservationVO>  itor = obsColl.iterator();

    		while (itor.hasNext())
    		{

    			ObservationVO obsVO = (ObservationVO) itor.next();
    			// check to see if question not answered
    			boolean hasAnswer = super.checkForEmptyObs(obsVO, request);

    			if (hasAnswer)
    			{
    				//ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);

    				if (obsVO.getTheObsValueCodedDTCollection() != null && obsVO.getTheObsValueCodedDTCollection().size()>0)
    				{

    					Iterator<Object>  it = obsVO.getTheObsValueCodedDTCollection().iterator();

    					while (it.hasNext())
    					{

    						ObsValueCodedDT obsValDT = (ObsValueCodedDT) it.next();
    						if ( (obsValDT.getCode() == null) || (obsValDT.getCode().trim().length() == 0))
    						{
    							obsValDT.setCode("NI");
    						}

    						obsValDT.setItNew(true);
    						obsValDT.setItDirty(false);
    					}
    				}

    				if (obsVO.getTheObsValueDateDTCollection() != null && obsVO.getTheObsValueDateDTCollection().size()>0)
    				{

    					Iterator<Object>  it = obsVO.getTheObsValueDateDTCollection().iterator();

    					while (it.hasNext())
    					{

    						ObsValueDateDT obsValDT = (ObsValueDateDT) it.next();
    						logger.debug("The duration amout here is :" +
    								obsValDT.getDurationAmt());
    						obsValDT.setItNew(true);
    						obsValDT.setItDirty(false);
    					}
    				}

    				if (obsVO.getTheObsValueNumericDTCollection() != null && obsVO.getTheObsValueNumericDTCollection().size()>0)
    				{

    					Iterator<Object>  it = obsVO.getTheObsValueNumericDTCollection().iterator();

    					while (it.hasNext())
    					{

    						ObsValueNumericDT obsValDT = (ObsValueNumericDT) it.next();
    						// this is as per defect no: 7763
    						if (obsValDT.getNumericValue1() == null &&
    								obsValDT.getNumericValue2() == null)
    						{
    							obsValDT.setNumericUnitCd(null);
    						}
    						obsValDT.setSeparatorCd(null);
    						obsValDT.setItNew(true);
    						obsValDT.setItDirty(false);
    					}
    				}

    				if (obsVO.getTheObsValueTxtDTCollection() != null && obsVO.getTheObsValueTxtDTCollection().size()>0)
    				{

    					Iterator<Object>  it = obsVO.getTheObsValueTxtDTCollection().iterator();

    					while (it.hasNext())
    					{

    						ObsValueTxtDT obsValDT = (ObsValueTxtDT) it.next();
    						obsValDT.setItNew(true);
    						obsValDT.setItDirty(false);
    					}
    				}

    				ObservationDT obsDT = obsVO.getTheObservationDT();
    				if (obsDT != null)
    				{
    					obsDT.setCdSystemDescTxt(NEDSSConstants.NEDSS_BASE_SYSTEM);
    					obsDT.setCdSystemCd(NEDSSConstants.NBS);
    					obsDT.setCdVersion(NEDSSConstants.VERSION);

    					obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
    					obsDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);

    					String strCode = obsDT.getCd();

    					if ( (strCode == null) || (strCode.trim().length() == 0))
    					{
    						logger.error(
    								"Page submitted an observation where obsDT.Cd was not set. cd: " +
    										obsDT.getCd() + tempID);

    					}
    					obsDT.setObservationUid(new Long(tempID--));
    					obsDT.setItNew(true);
    					obsDT.setItDirty(false);
    				}
    				obsVO.setItNew(true);
    				obsVO.setItDirty(false);
    				newObsColl.add(obsVO);
    			}
    		} //end of while loop

    		//parent observation
    		ObservationVO parentObservation = new ObservationVO();
    		ObservationDT parentObservationDT = new ObservationDT();
    		parentObservationDT.setCd(investigationFormCd);
    		parentObservationDT.setCdSystemCd("NBS");
    		parentObservationDT.setGroupLevelCd("L1");
    		parentObservationDT.setObsDomainCd("CLN");
    		parentObservationDT.setObservationUid(new Long(tempID--));
    		parentObservationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
    		parentObservationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
    		parentObservationDT.setItNew(true);
    		parentObservationDT.setItDirty(false);
    		parentObservation.setTheObservationDT(parentObservationDT);
    		parentObservation.setItNew(true);
    		parentObservation.setItDirty(false);
    		newObsColl.add(parentObservation);

    		investigationProxyVO.setTheObservationVOCollection(newObsColl);
    	} catch (Exception ex) {
    		logger.error("Error in setObsForCreate: "+ex.getMessage());
    		ex.printStackTrace();
    	}	
      } // end of if (obsColl != null)

      return tempID;
    }

    /**
     * sets ActRelationship for parent and child observations
     * @param investigationProxyVO : InvestigationProxyVO
     * @param investigationFormCd : String
     */

    protected void setActRelationshipForCreate(InvestigationProxyVO
                                             investigationProxyVO,
                                             String investigationFormCd)
    {

      Collection<ObservationVO>  obsColl = investigationProxyVO.getTheObservationVOCollection();
      ObservationVO formObservation = ObservationUtils.findObservationByCode(
          obsColl, investigationFormCd);
      PublicHealthCaseVO phcVO = investigationProxyVO.getPublicHealthCaseVO();
      Collection<Object>  actRelColl = new ArrayList<Object> ();
      CachedDropDownValues srtc = new CachedDropDownValues();

      if (obsColl != null)
      {

       Iterator<ObservationVO>  itor = obsColl.iterator();

        while (itor.hasNext())
        {

          ObservationVO obsVO = (ObservationVO) itor.next();

          if (obsVO.getTheObservationDT().getCd() != null &&
              !obsVO.
              getTheObservationDT().getCd().equals(formObservation.
              getTheObservationDT().
              getCd()))
          {

            ActRelationshipDT actRelForm = new ActRelationshipDT();
            actRelForm.setItNew(true);
            actRelForm.setSourceActUid(obsVO.getTheObservationDT().
                                       getObservationUid());
            actRelForm.setSourceClassCd(NEDSSConstants.CLASS_CD_OBS);
            actRelForm.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            actRelForm.setRecordStatusCd(NEDSSConstants.ACTIVE);
            actRelForm.setTargetActUid(formObservation.getTheObservationDT().
                                       getObservationUid());

            //actRelForm.setSequenceNbr(new Integer(1));
            actRelForm.setTypeCd(NEDSSConstants.INV_FRM_Q); // Revisit this and change.
            actRelForm.setTypeDescTxt(srtc.getDescForCode("AR_TYPE",NEDSSConstants.INV_FRM_Q));
            actRelForm.setTargetClassCd(NEDSSConstants.CLASS_CD_OBS);
            actRelColl.add(actRelForm);
          }
        }

        ActRelationshipDT act116 = new ActRelationshipDT();
        act116.setItNew(true);
        act116.setSourceActUid(formObservation.getTheObservationDT().getObservationUid());
        act116.setSourceClassCd(NEDSSConstants.CLASS_CD_OBS);
        act116.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        act116.setTargetActUid(phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
        act116.setTargetClassCd(NEDSSConstants.CLASS_CD_CASE);
        act116.setRecordStatusCd(NEDSSConstants.ACTIVE);

        //act116.setSequenceNbr(new Integer(1));
        act116.setTypeCd(NEDSSConstants.PHC_INV_FORM); // Revisit this and change.
        act116.setTypeDescTxt(srtc.getDescForCode("AR_TYPE",NEDSSConstants.PHC_INV_FORM));

        actRelColl.add(act116);
      }

      investigationProxyVO.setTheActRelationshipDTCollection(actRelColl);
    }

    /**
     * this method process the observations, sets the new flag, removes empty observations
     * sets the negative uid
     * @param investigationProxyVO : InvestigationProxyVO
     * @param tempID : int
     * @param request : HttpServletRequest
     * @param investigationFormCd : String
     * @return : int
     */

    private int newSetObservationForCreate(InvestigationProxyVO investigationProxyVO,
                                        int tempID, HttpServletRequest request,
                                        String investigationFormCd)
    {
      Collection<ObservationVO>  theObservationVOCollection  = investigationProxyVO.getTheObservationVOCollection();
      Collection<Object>  actRelationshipCollection   = investigationProxyVO.getTheActRelationshipDTCollection();
      ObservationVO parentObservationVO = this.createParentObservation(investigationFormCd);
      parentObservationVO.getTheObservationDT().setObservationUid(new Long(tempID--));

      if(theObservationVOCollection  != null)
      {
        TreeMap<Object,Object> obsActTreeMap = super.setObservations(theObservationVOCollection, new TreeMap(), "T",parentObservationVO.getTheActRelationshipDTCollection(), parentObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.INV_FRM_Q,request, tempID);
        theObservationVOCollection.addAll((ArrayList<ObservationVO> )obsActTreeMap.get("observations"));
        actRelationshipCollection.addAll((ArrayList<Object> )obsActTreeMap.get("actrelations"));
        tempID = ((Integer)obsActTreeMap.get("tempID")).intValue();
      }

      return tempID;
    }

    /**
     * create parent observation for investigation
     * @param investigationFormCd : String
     * @return : ObservationVO
     */

    private ObservationVO createParentObservation(String investigationFormCd)
    {
     ObservationVO parentObservation = new ObservationVO();
     ObservationDT parentObservationDT = new ObservationDT();
     parentObservationDT.setCd(investigationFormCd);
     parentObservationDT.setCdSystemCd("NBS");
     parentObservationDT.setGroupLevelCd("L1");
     parentObservationDT.setObsDomainCd("CLN");
     parentObservationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
     parentObservationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
     parentObservationDT.setItNew(true);
     parentObservationDT.setItDirty(false);
     parentObservation.setTheObservationDT(parentObservationDT);
     parentObservation.setItNew(true);
     parentObservation.setItDirty(false);

     return parentObservation;
    }

    /**
     * sets confirmation methods in PublicHealthCaseVO
     * @param request : HttpServletRequest
     * @param phcVO : PublicHealthCaseVO
     */

    protected void setConfirmationMethods(HttpServletRequest request, PublicHealthCaseVO phcVO)
    {

      String[] arr = request.getParameterValues("confirmationMethods");
      String confirmationDate = (String) request.getParameter("confirmationDate");
      Collection<Object>  confMethodColl = new ArrayList<Object> ();

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

    /**
     * sets the new flag for ActIdDT of PublicHealthCaseVO
     * @param phcVO :PublicHealthCaseVO
     */
    protected void setActIdForPublicHealthCase(PublicHealthCaseVO phcVO)
    {
      if(phcVO.getTheActIdDTCollection() != null)
      {
          ActIdDT actIdDT = null;
         Iterator<Object>  itr = phcVO.getTheActIdDTCollection().iterator();
          while(itr.hasNext())
          {
              actIdDT = (ActIdDT) itr.next();
              actIdDT.setItDirty(false);
              actIdDT.setItNew(true);
          }
      }
    }


  }