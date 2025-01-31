package gov.cdc.nedss.webapp.nbs.action.observation.labreport;

import org.apache.struts.action.*;

import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTCode;


public class EditLabReportSubmit extends CommonLabUtil
{

    //For logging
    static final LogUtils logger = new LogUtils(EditLabReportSubmit.class.getName());

    public EditLabReportSubmit()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm actionForm,
                                 HttpServletRequest request, HttpServletResponse response)
                          throws IOException, ServletException
    {

        HttpSession session = request.getSession();
        LabResultProxyVO proxy = null;
        Long UID = null;

        if (session == null)
        {
            return mapping.findForward("login");
        }

        NBSSecurityObj securityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

        if (securityObj == null)
        {
            return mapping.findForward(NEDSSConstants.LOGIN_PAGE);
        }

        String contextAction = request.getParameter("ContextAction");
        String sCurrentTask = NBSContext.getCurrentTask(session);

        try
        {
          if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL)) {
            ActionForward af = mapping.findForward(contextAction);
            ActionForward forwardNew = new ActionForward();
            StringBuffer strURL = new StringBuffer(af.getPath());
            if (sCurrentTask.startsWith("EditObservation")) {
             return (mapping.findForward(contextAction));
            }
            else {
              session.setAttribute("error",
                                   "ContextAction(Cancel): Invalid current task.");
              throw new ServletException("ContextAction(Cancel): Invalid current task.");
            }
          }
        }
        catch(Exception ncde)
        {
          logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ", ncde);
          return mapping.findForward("dataerror");
        }

        try
        {
            proxy = this.editHandler(mapping, actionForm, request, session, securityObj);
            String reportingSourceLabId = (String) request.getParameter("labId");
            proxy.setLabClia(reportingSourceLabId);
            proxy.setManualLab(true);
        }
        catch (Exception e)
        {
            logger.fatal("ERROR - Exception in EditLabReportSubmit: ", e);
            session.setAttribute("error", "");
            throw new ServletException("ERROR - Exception occurred in EditLabReportSubmit: "+e.getMessage(),e);
        }

        //gov.cdc.nedss.utils.VOTester.createReport(proxy, "obs-edit-store-post");

        if (proxy != null)
        {
            try
            {
                UID = sendProxyToEJB(proxy, session);
            }
            catch (NEDSSAppConcurrentDataException e)
            {
                session.setAttribute("error", "");
                logger.error("Data error in Edit Lab Report Submit: "+e.getMessage());
                return mapping.findForward("dataerror");
            }
            catch (javax.ejb.EJBException ex)
            {
                session.setAttribute("error", "");
                logger.error("EJB error in Edit Lab Report Submit: "+ex.getMessage());
                return (mapping.findForward("error"));
            }
            catch (Exception ex)
            {
                session.setAttribute("error", "");
                logger.error("General error in Edit Lab Report Submit: "+ex.getMessage());
                return (mapping.findForward("error"));
            }
        }
        else if (proxy == null || UID == null)
        {
        	 logger.error("Unexpected Null error in Edit Lab Report Submit: ");
        	throw new ServletException("Proxy is null inside LaboratoryEditSubmit");
        }

        return mapping.findForward(contextAction);
    } //execute

    /**
     * @method      : sendProxyToEJB
     * @params      : LabResultProxyVO, HttpSession
     * @returnType  : java.lang.Long
     */
 private Long sendProxyToEJB(LabResultProxyVO proxy, HttpSession session) throws
        NEDSSAppConcurrentDataException, java.rmi.RemoteException,
        javax.ejb.EJBException, Exception
    {

	 MainSessionCommand msCommand = null;

	 try {
		 String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
		 String sMethod = null;
		 Long obsUid = null;
		 MainSessionHolder holder = new MainSessionHolder();
		 ArrayList<?> resultUIDArr = null;

		 sMethod = "setLabResultProxy";
		 Object[] oParams =
			 {
				 proxy};
		 msCommand = holder.getMainSessionCommand(session);
		 resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

		 for (int i = 0; i < resultUIDArr.size(); i++)
		 {
			 logger.info("Result " + i + " is: " + resultUIDArr.get(i));
		 }

		 if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
		 {
			 logger.debug("store observation worked!!! observationUID = " +
					 resultUIDArr.get(0));


			 Map<?,?> result = (Map<?,?>) resultUIDArr.get(0);

			 //obsUid = (Long)result.get(NEDSSConstants.SETLAB_RETURN_OBS_UID);
			 obsUid = (Long)result.get(NEDSSConstants.SETLAB_RETURN_MPR_UID);
			 return obsUid;
		 }
	 }catch(NEDSSConcurrentDataException ex){
		 logger.fatal("EditLabReportSubmit NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
		 throw ex;
	 }catch(Exception ex){
		 logger.fatal("EditLabReportSubmit Exception  = "+ex.getMessage(), ex);
		 throw ex;
	 }
      return null;
    } //sendProxyToEJB


  private LabResultProxyVO editHandler(ActionMapping mapping, ActionForm actionForm,
                                         HttpServletRequest request, HttpSession session,
                                         NBSSecurityObj nbsSecurityObj)
                                  throws ServletException,
                                         NEDSSAppException
    {

        int i = -1;
        LabResultProxyVO labResultProxyVO = null;
        try {
        	Long oldObsUID= (Long)NBSContext.retrieve(session, "DSObservationUID");

        	ObservationForm obsForm = (ObservationForm)actionForm;
        	labResultProxyVO = obsForm.getProxy();
        	ObservationVO newLAB112 = this.findObservationByCode(
        			labResultProxyVO.getTheObservationVOCollection(),"LAB112");
        	LabResultProxyVO oldLabResultProxyVO = obsForm.getOldProxy();
        	ResultedTestVO oldResultSetVO = null;
        	Collection<ObservationVO>  oldObsCollection  = oldLabResultProxyVO.getTheObservationVOCollection();
        	ObservationVO oldLAB112 = this.findObservationLAB112(oldObsCollection,
        			oldObsUID);
        	ObservationVO oldLAB214 = this.findObservationByCode(oldObsCollection,"LAB214");
        	ObservationVO oldTemp214 = this.findObservationByCode(oldObsCollection,"NI");
        	//Collection<Object>  OldResultedTestVOCollection  = (ArrayList<Object> )session.getAttribute("ResultedTestArray");
        	Collection<Object>  OldResultedTestVOCollection  = obsForm.getOldResultedTestVOCollection();
        	Long patientUID = (Long)NBSContext.retrieve(session, "DSPatientPersonUID");
        	String reportingFacilityUID = request.getParameter( "Org-ReportingOrganizationUID");
        	String orderingFacilityUID = request.getParameter("Org-OrderingFacilityUID");
        	String providerUID = request.getParameter("Prov-entity.entityProvUID");

        	ArrayList<ObservationVO> editObsCollection  = new ArrayList<ObservationVO> ();
        	ArrayList<Object> roleCollection  = new ArrayList<Object> ();
        	ArrayList<Object> participationColl = new ArrayList<Object> ();
        	ArrayList<Object> actRelationColl = new ArrayList<Object> ();
        	ArrayList<Object> actIdColl = new ArrayList<Object> ();
        	ArrayList<Object> personsColl = new ArrayList<Object> ();
        	ArrayList<Object> materialColl = new ArrayList<Object> ();
        	ArrayList<Object> personTeleColl = new ArrayList<Object> ();
        	ArrayList<Object> personPostalColl = new ArrayList<Object> ();

        	Long personSubjectUid = null;
        	Long organizationProviderUid = null;
        	Long organizationReportingUid = null;
        	Long orderingFacilityUid = null;
        	int tempID=-1;

        	if(patientUID != null){
        		if(patientUID.equals(""))
        			logger.info("sPatient is empty");
        		else
        			personSubjectUid = patientUID;
        	}

        	if(reportingFacilityUID != null){
        		if (reportingFacilityUID.equals(""))
        			logger.info("reportingFacilityUID is empty");
        		else
        			organizationReportingUid = new Long(reportingFacilityUID.toString());
        	}

        	if(orderingFacilityUID != null){
        		if (orderingFacilityUID.equals(""))
        			logger.info("sPatient is empty");
        		else
        			orderingFacilityUid = new Long(orderingFacilityUID.toString());
        	}

        	if (providerUID != null){
        		if (providerUID.equals(""))
        			logger.info("providerUID is empty");
        		else
        			organizationProviderUid = new Long(providerUID.toString());
        	}
        	ObservationVO newLAB214 = new ObservationVO();
        	this.setObservationForEdit(labResultProxyVO.getTheObservationVOCollection(),
        			editObsCollection,actRelationColl,
        			oldLAB112,oldLAB214,oldTemp214, newLAB214, request);
        	ObservationVO obsVOLab112 = findObservationByCode(editObsCollection, "LAB112");
        	tempID =setPatientStatusVO(obsVOLab112, obsForm.getPatientStatusVO(),obsForm.getOldPatientStatusVO(),tempID, 
        			actRelationColl, editObsCollection);
        	this.setObsForCreateFromResultedEdit(obsVOLab112, obsForm,
        			OldResultedTestVOCollection,
        			editObsCollection, actRelationColl, tempID, request);


        	this.setActIdForEdit(newLAB112,oldLAB112,actIdColl);

        	this.setMaterialForEdit(labResultProxyVO.getTheMaterialVOCollection(),
        			oldLabResultProxyVO.getTheMaterialVOCollection(),
        			materialColl);
        	this.setPersonForEdit(obsForm,personSubjectUid,personsColl,request);

        	labResultProxyVO.setMessageLogDCollection(obsForm.getMessageLogDTCollection());

        	ObservationDT observationDTLab112=oldLAB112.getTheObservationDT();

        	Collection<Object>  oldParticipation = oldLAB112.getTheParticipationDTCollection();

        	ParticipationDT part110 = getParticipationDT(oldParticipation,
        			"PSN","PATSBJ","Patient Subject");
        	if (part110 != null) {
        		part110.setItDirty(true);
        		part110.setItNew(false);
        		participationColl.add(part110);
        	}

        	//organizationReportingUid

        	ParticipationDT part111 = getParticipationDT(oldParticipation, "ORG","AUT","Author");
        	if (part111 != null) {
        		part111.setItDirty(true);
        		part111.setItNew(false);

        		participationColl.add(part111);
        	}


        	ParticipationDT part102 = getParticipationDT(oldParticipation, "ORG", "ORD",
        			"Orderer");
        	if (part102 != null) {
        		if (part102.getSubjectEntityUid().equals(orderingFacilityUid)) {
        			part102.setItDirty(true);
        			part102.setItNew(false);
        			participationColl.add(part102);
        		}
        		else {
        			part102.setItDelete(true);
        			participationColl.add(part102);
        			if (orderingFacilityUid != null) {
        				ParticipationDT  part102NEW = this.genericParticipationCreate("OBS",
        						observationDTLab112.
        						getObservationUid(),
        						null, "ACTIVE", "A", "ORG",
        						orderingFacilityUid, "ORD",
        						"Orderer");
        				part102.setItNew(true);
        				participationColl.add(part102NEW);
        			}

        		}

        	}
        	else {
        		if (orderingFacilityUid != null) {
        			ParticipationDT part102NEW = this.genericParticipationCreate("OBS",
        					observationDTLab112.
        					getObservationUid(),
        					null, "ACTIVE", "A", "ORG",
        					orderingFacilityUid, "ORD",
        					"Orderer");
        			part102NEW.setItNew(true);
        			participationColl.add(part102NEW);

        		}
        	}

        	ParticipationDT part101 = getParticipationDT(oldParticipation, "PSN", "ORD",
        			"Orderer");
        	if (part101 != null) {
        		if (part101.getSubjectEntityUid().equals(organizationProviderUid)) {

        			part101.setItDirty(true);
        			part101.setItNew(false);
        			participationColl.add(part101);
        		}
        		else {
        			part101.setItDelete(true);
        			participationColl.add(part101);

        			if (organizationProviderUid != null) {
        				ParticipationDT part101NEW = this.genericParticipationCreate("OBS",
        						observationDTLab112.
        						getObservationUid(),
        						null, "ACTIVE", "A", "PSN",
        						organizationProviderUid,
        						"ORD", "Orderer");
        				part101NEW.setItNew(true);
        				participationColl.add(part101NEW);
        			}

        		}

        	}
        	else {
        		if (organizationProviderUid != null) {
        			ParticipationDT newPart101 = this.genericParticipationCreate("OBS",
        					observationDTLab112.getObservationUid(),
        					null, "ACTIVE", "A", "PSN",
        					organizationProviderUid,
        					"ORD", "Orderer");
        			newPart101.setItNew(true);
        			participationColl.add(newPart101);

        		}
        	}

        	///******8888
        	Collection<Object>  oldRoleDTColl = oldLabResultProxyVO.getTheRoleDTCollection();
        	if(oldRoleDTColl != null){
        		Iterator<Object>  role = oldRoleDTColl.iterator();
        		while(role.hasNext()){
        			RoleDT dt = (RoleDT) role.next();
        			dt.setItDirty(true);
        			roleCollection.add(dt);
        		}
        	}

        	labResultProxyVO.setThePersonVOCollection(personsColl);

        	labResultProxyVO.setTheObservationVOCollection(editObsCollection);

        	labResultProxyVO.setTheActRelationshipDTCollection(actRelationColl);

        	labResultProxyVO.setTheParticipationDTCollection(participationColl);
        	labResultProxyVO.setTheRoleDTCollection(roleCollection);
        	labResultProxyVO.setTheActIdDTCollection(actIdColl);
        	labResultProxyVO.setTheMaterialVOCollection(materialColl);

        	this.setLAB112(labResultProxyVO.getTheObservationVOCollection(),newLAB112);
        	//LDF
        	 /**
             * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
          	CommonLabUtil commonLabUtil = new CommonLabUtil();
        	commonLabUtil.submitLabLDF(labResultProxyVO,obsForm, false, request);
        	PersonVO revision = obsForm.getPatient();
        	commonLabUtil.submitPatientLDF(revision,obsForm, false, request);
        	*/
        }catch (Exception e) {
        	logger.error("Exception occurred in Edit Lab Report Submit: " + e.getMessage());
        	e.printStackTrace();
        	throw new NEDSSAppException("Error occurred in Edit Lab Report Submit : "+e.getMessage());
        }   
     return labResultProxyVO;
 }

 private void setObservationForEdit(Collection<ObservationVO>  newObsColl,
                                    Collection<ObservationVO>  editObsColl,
                                    Collection<Object>  actRelationColl,
                                    ObservationVO obsVOLab112,
                                    ObservationVO oldLAB214,
                                    ObservationVO oldTemp214,ObservationVO newLAB214,
                                    HttpServletRequest request){
	 
   if(oldTemp214 != null){
      oldTemp214.setItDirty(true);
      editObsColl.add(oldTemp214);

      Collection<Object>  collLab214 = oldTemp214.getTheActRelationshipDTCollection();
      if (collLab214 != null) {
       Iterator<Object>  itLAB214 = collLab214.iterator();
        while (itLAB214.hasNext()) {
          ActRelationshipDT actLAB214 = (ActRelationshipDT) itLAB214.next();
          if (actLAB214.getTypeCd().equalsIgnoreCase(NEDSSConstants.CAUS)) {
            actLAB214.setItDirty(true);
            actRelationColl.add(actLAB214);
          }
        }
       }
   }
   String Comments214 = null;
   boolean islab214LoopEntered = false;
   if(newObsColl !=null){
  Iterator<ObservationVO>  itNew = newObsColl.iterator();
   while(itNew.hasNext()){
     ObservationVO newObsVO = (ObservationVO)itNew.next();
     ObservationUtils.trimEmptyObsValueCodedDTCollections(newObsVO);
     ObservationDT obsDT = newObsVO.getTheObservationDT();


     if(obsDT.getCd() != null){
       if (obsDT.getCd().equalsIgnoreCase("LAB112") && obsVOLab112!= null ) {



         if (newObsVO.getTheObsValueCodedDTCollection() != null) {
          Iterator<Object>  obsValueCode = newObsVO.getTheObsValueCodedDTCollection().
               iterator();
           while (obsValueCode.hasNext()) {
             ObsValueCodedDT obsValueDT = (ObsValueCodedDT) obsValueCode.
                 next();
             if (obsValueDT.getCode() == null ||
                 obsValueDT.getCode().trim().length() == 0)
               obsValueDT.setCode("NI");
             obsValueDT.setItDirty(true);
           }
           obsVOLab112.setTheObsValueCodedDTCollection(newObsVO.getTheObsValueCodedDTCollection());
         }
         if (newObsVO.getTheObsValueDateDTCollection() != null) {
          Iterator<Object>  obsValueDate = newObsVO.getTheObsValueDateDTCollection().
               iterator();
           while (obsValueDate.hasNext()) {
             ObsValueDateDT obsValueDT = (ObsValueDateDT) obsValueDate.next();
             obsValueDT.setItDirty(true);
           }
           obsVOLab112.setTheObsValueDateDTCollection(newObsVO.getTheObsValueDateDTCollection());
         }
         String cdSystemCdOT = obsDT.getCdSystemCdOT();
         String labId = (String) request.getParameter("labId");
         ObservationDT oldObsDT = obsVOLab112.getTheObservationDT();

         // If user select from search Button
         if (obsDT.getSearchResultOT() != null &&
             obsDT.getSearchResultOT().length() != 0) {
           String ordered = obsDT.getSearchResultOT();
           oldObsDT.setCdDescTxt(ordered);
           obsDT.setTxt("");

           if(cdSystemCdOT.equalsIgnoreCase("LN")){
             oldObsDT.setCdSystemCd("LN");
             oldObsDT.setCdSystemDescTxt("LOINC");
           }
           else if(labId != null && !labId.equals("")&&!labId.equals("null")){
             try{
               oldObsDT.setCdSystemDescTxt(getSystemDescTxt(labId,request.getSession()));
             }catch(Exception e){}
           }

           if (cdSystemCdOT != null && !cdSystemCdOT.equals("")) {
             oldObsDT.setCdSystemCd(cdSystemCdOT);
           }
           else {
             if (labId != null && !labId.equals("") && !labId.equals("null")) {
               oldObsDT.setCdSystemCd(labId);
             }
           }

         }
         // If user select from Drop down
         else if(obsDT.getTxt() != null && obsDT.getTxt().length() !=0)
         {
             oldObsDT.setCdDescTxt(obsDT.getTxt());
            // obsDT.setTxt("");
             if (labId != null && !labId.equals("")&& !labId.equals("null")) {
               oldObsDT.setCdSystemCd(labId);
               try{
               oldObsDT.setCdSystemDescTxt(getSystemDescTxt(labId,request.getSession()));
               }catch(Exception e){}

             }
         }
         else
         {
           oldObsDT.setCdDescTxt("No Information Given");
           //obsDT.setCd("NI");
           oldObsDT.setCdSystemCd("2.16.840.1");
           oldObsDT.setCdSystemDescTxt("Health Level Seven");
         }



         String shared =request.getParameter("proxy.observationVO_s[0].theObservationDT.sharedInd");

          if ( shared == null) {
                obsVOLab112.getTheObservationDT().setSharedInd("F");
          }
          else
            obsVOLab112.getTheObservationDT().setSharedInd("T");
            //String selectedOrderedTest = request.getAttribute("OrderedDisplay").toString();
            //logger.debug("selectedOrderedTest is :" + selectedOrderedTest);
          obsVOLab112.getTheObservationDT().setPregnantIndCd(obsDT.getPregnantIndCd());
          obsVOLab112.getTheObservationDT().setPregnantWeek(obsDT.getPregnantWeek());
          obsVOLab112.getTheObservationDT().setTargetSiteCd(obsDT.getTargetSiteCd());
          String targetSite = obsDT.getTargetSiteCd();
          if(obsDT.getTargetSiteCd() == null || obsDT.getTargetSiteCd().equals("")){
           obsDT.setTargetSiteCd("NI");
         }

          if (targetSite != null && !targetSite.equals("NI")) {
            obsVOLab112.getTheObservationDT().setTargetSiteDescTxt(getDescTxt("ANATOMIC_SITE", targetSite));
          }


        obsVOLab112.getTheObservationDT().setCd("LAB112");

        obsVOLab112.getTheObservationDT().setActivityToTime(obsDT.getActivityToTime());
        obsVOLab112.getTheObservationDT().setRptToStateTime(obsDT.getRptToStateTime());


        obsVOLab112.getTheObservationDT().setEffectiveFromTime(obsDT.getEffectiveFromTime());
        obsVOLab112.getTheObservationDT().setLastChgTime(new java.sql.Timestamp(new Date().getTime()));
        obsVOLab112.getTheObservationDT().setItDirty(true);
        obsVOLab112.setItDirty(true);


         editObsColl.add(obsVOLab112);
         /*
           * AS per Rel1.1.3 we are not doing a reverse translation for ordered test rather
           * we are translating the code to description and setting the Description to cdDescTxt.
           * We are storing the ordered test Code in txt field in lab XSP
           */
          String orderedTestCode = null;
          String orderedTestDesc = null;
          String orderedTestcdSystemCd = null;
          String hiddenOrderedTestCdSystemDsec = null;
          if(request.getParameter("hiddenOrderedTestNameCode")!=null)
          {
            orderedTestCode =  request.getParameter("hiddenOrderedTestNameCode").toString();
            orderedTestDesc =  request.getParameter("hiddenOrderedTestNameDesc").toString();
            orderedTestcdSystemCd = request.getParameter("hiddenOrderedTestCdSystemCd").toString();
            hiddenOrderedTestCdSystemDsec = request.getParameter("hiddenOrderedTestCdSystemDsec").toString();
          }

         if( orderedTestCode!= null && obsDT.getTxt()==null
             && (obsDT.getHiddenCd()== null || obsDT.getHiddenCd().equalsIgnoreCase("")) )
         {
           obsDT.setTxt(orderedTestCode);
           obsDT.setCdDescTxt(orderedTestDesc);
           obsDT.setCdSystemCd(orderedTestcdSystemCd);
           obsDT.setCdSystemDescTxt(hiddenOrderedTestCdSystemDsec);
           if(orderedTestcdSystemCd.equalsIgnoreCase("LN")){
             obsDT.setCdSystemCdOT("LN");

           }

         }
         if((!oldObsDT.getCdDescTxt().equalsIgnoreCase(NEDSSConstants.NOINFORMATIONGIVEN) || obsDT.getTxt()!=null)
            &&  ( obsDT.getHiddenCd()== null ||(obsDT.getHiddenCd()!= null && obsDT.getHiddenCd().equalsIgnoreCase(""))
            ) &&( orderedTestCode== null || (orderedTestCode!= null && obsDT.getTxt()!=null && !orderedTestCode.equalsIgnoreCase(obsDT.getTxt())))  )
         {
           SRTCode srtcodeDT = (SRTCode) NedssCodeLookupServlet.
                               getUniqueDTForCodeValueLookup(labId,
                                                             NEDSSConstants.ORDERED_TEST_LOOKUP, null,
                                                             obsDT.getTxt(), null, request);

           if (srtcodeDT != null && srtcodeDT.getCode()!= null && srtcodeDT.getCode().equalsIgnoreCase(obsDT.getTxt())
               &&( orderedTestCode== null || (orderedTestCode!= null && obsDT.getTxt()!=null && !orderedTestCode.equalsIgnoreCase(obsDT.getTxt()))))
             obsVOLab112.getTheObservationDT().setCdDescTxt(srtcodeDT.getDesc());
         }
         else if(obsDT.getHiddenCd()!= null && !obsDT.getHiddenCd().equalsIgnoreCase("") && obsDT.getCdDescTxt()==null)
         {
           SRTCode srtcodeDT = (SRTCode) NedssCodeLookupServlet.
                               getUniqueDTForCodeValueLookup(labId,
                                                             NEDSSConstants.ORDERED_TEST_LOOKUP, null,
                                                             obsDT.getHiddenCd(), null, request);

           if (srtcodeDT != null && srtcodeDT.getCode()!= null && srtcodeDT.getCode().equalsIgnoreCase(obsDT.getHiddenCd()))
             obsVOLab112.getTheObservationDT().setCdDescTxt(srtcodeDT.getDesc());

         }
         else if((orderedTestCode!= null && obsDT.getTxt()!=null && orderedTestCode.equalsIgnoreCase(obsDT.getTxt())))
         {
           obsVOLab112.getTheObservationDT().setTxt(orderedTestCode);
           obsVOLab112.getTheObservationDT().setCdDescTxt(orderedTestDesc);
           obsVOLab112.getTheObservationDT().setCdSystemCd(orderedTestcdSystemCd);
           obsVOLab112.getTheObservationDT().setCdSystemDescTxt(hiddenOrderedTestCdSystemDsec);

         }

       }

       ObservationVO lab214VO =null;
       if (obsDT.getCd().equalsIgnoreCase("LAB214") && oldLAB214 != null && !islab214LoopEntered) {
       	islab214LoopEntered = true;
       	/*
         * Test
         */

        	newLAB214 = oldLAB214;
        	
        	//get the old comment so we can store current userid if comment has changed
        	String oldComments214 = null;
        	Iterator<Object>  oldObsValueText = oldLAB214.getTheObsValueTxtDTCollection().iterator();
            while (oldObsValueText.hasNext()) {
                ObsValueTxtDT obsValueDT = (ObsValueTxtDT) oldObsValueText.next();
                oldComments214 = obsValueDT.getValueTxt();
            }
            
           	newLAB214.getTheObservationDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));

           	
            //LabReportComment(LAB214) Date goes as Observation.activity_to_time
           	newLAB214.getTheObservationDT().setActivityToTime(new java.sql.Timestamp(new Date().getTime()));
           	newLAB214.getTheObservationDT().setItDirty(true);
           	newLAB214.setItDirty(true);
           	newLAB214.setItNew(false);
            if (newObsVO.getTheObsValueTxtDTCollection() != null) {
               Iterator<Object>  obsValueText = newObsVO.getTheObsValueTxtDTCollection().
                    iterator();
                boolean isAdded= false;
                Collection<Object>  newObsTxtCollection   = new ArrayList<Object> ();
                while (obsValueText.hasNext() && !isAdded) {
                  isAdded = true;
                  ObsValueTxtDT obsValueDT = (ObsValueTxtDT) obsValueText.next();
                  Comments214 = obsValueDT.getValueTxt();
                  obsValueDT.setItDirty(true);
                  obsValueDT.setObservationUid(newLAB214.getTheObservationDT().getObservationUid());
                  newObsTxtCollection.add(obsValueDT);
                }
                newLAB214.setTheObsValueTxtDTCollection(newObsTxtCollection);
              }
			//see defect 3691
            if (Comments214 != null) {
            	if (oldComments214 == null || Comments214.compareTo(oldComments214) != 0) {
            		//if comment changed, store the current user
                    NBSSecurityObj securityObj = (NBSSecurityObj)request.getSession().getAttribute("NBSSecurityObject");
                    String currentUserId = securityObj.getTheUserProfile().getTheUser().getEntryID();
            		newLAB214.getTheObservationDT().setAddUserId(Long.valueOf(currentUserId)); 
            	}
            }


         /**
          * Done
          *
          */

       }
       else if (obsDT.getCd().equalsIgnoreCase("LAB214") && oldLAB214 != null && islab214LoopEntered) {
       	newLAB214 = oldLAB214;

       	newLAB214.getTheObservationDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));
       	newLAB214.getTheObservationDT().setItDirty(true);
       	newLAB214.setItDirty(true);
       	newLAB214.setItNew(false);
        if (newObsVO.getTheObsValueTxtDTCollection() != null) {
           Iterator<Object>  obsValueText = newObsVO.getTheObsValueTxtDTCollection().
                iterator();

            boolean isAdded= false;
            Collection<Object>  newObsTxtCollection   = new ArrayList<Object> ();
            while (obsValueText.hasNext() && !isAdded) {
              isAdded = true;
              ObsValueTxtDT obsValueDT = (ObsValueTxtDT) obsValueText.next();
              obsValueDT.setValueTxt(Comments214) ;
              obsValueDT.setItDirty(true);
              obsValueDT.setObservationUid(newLAB214.getTheObservationDT().getObservationUid());
              newObsTxtCollection.add(obsValueDT);
            }
            newLAB214.setTheObsValueTxtDTCollection(newObsTxtCollection);
          }

       }
       }
   }
   }
   if(newLAB214.getTheObservationDT().getCd()!=null && newLAB214.getTheObservationDT().getCd().equalsIgnoreCase("LAB214"))
			editObsColl.add(newLAB214);
     Collection<Object>  collLab112 = obsVOLab112.getTheActRelationshipDTCollection();
     if(collLab112 != null){
      Iterator<Object>  itLAB112 = collLab112.iterator();
       while(itLAB112.hasNext()){
         ActRelationshipDT actLAB112Resulted = (ActRelationshipDT) itLAB112.next();
         if(actLAB112Resulted.getTypeCd().equalsIgnoreCase(NEDSSConstants.APND)){
           actLAB112Resulted.setItDirty(true);
           actRelationColl.add(actLAB112Resulted);
         }
       }
     }

   //}// End of while loop

     setObsValueCodedToDefaultValues(editObsColl);
     newObsColl.clear();

 }

 private  void setObsForCreateFromResultedEdit(ObservationVO obsVOLab112,
		 ObservationForm obsForm,
                                              Collection<Object>  oldObsColl,
                                              Collection<ObservationVO>  editObsColl,
                                              Collection<Object>  actRelationColl,
                                              int tempUID,
                                              HttpServletRequest request) throws NEDSSAppException {
	 try {
		 Collection<Object>  newObsColl=obsForm.getResultedTestVOCollection();
		 Collection<Object> stdHivResultStausCollection= new ArrayList<Object> ();
		 String programArea = obsVOLab112.getTheObservationDT().getProgAreaCd();
		 boolean isStdOrHivProgramArea = false;
		 PropertyUtil properties = PropertyUtil.getInstance();
		 StringTokenizer st2 = new StringTokenizer(properties.getSTDProgramAreas(), ",");
		 if (st2 != null) {
			 while (st2.hasMoreElements()) {
				 if (st2.nextElement().equals(programArea)) {
					 isStdOrHivProgramArea = true;
					 break;
				 }
			 }
		 }

		 if (newObsColl != null)
		 {
			 HashMap<Object,Object> oldHashMap = new HashMap<Object,Object>();
			 HashMap<Object,Object> susHashMap = new HashMap<Object,Object>();
			 HashMap<Object,Object> trackUIDHashMap = new HashMap<Object,Object>();





			 if (oldObsColl != null && oldObsColl.size() > 0)
			 {
				 logger.info("oldResults  size is: " + oldObsColl.size());
				 logger.info("newResults  size is: " + newObsColl.size());

				 Iterator<Object>  itor = oldObsColl.iterator();
				 while (itor.hasNext())
				 {
					 ResultedTestVO oldVO = (ResultedTestVO)itor.next();
					 HashMap<Object,Object> track329aHashMap = new HashMap<Object,Object>();
					 if (oldVO != null)
					 {
						 oldVO.setItDirty(true);
						 oldVO.getTheIsolateVO().setItDirty(true);
						 oldVO.getTheIsolateVO().getTheObservationDT().setItDirty(true);
						 oldVO.getTheIsolateVO().getObsValueCodedDT_s(0).setItDirty(true);
						 oldVO.getTheIsolateVO().getObsValueNumericDT_s(0).setItDirty(true);
						 oldVO.getTheIsolateVO().getObsValueTxtDT_s(0).setItDirty(true);

						 if(isStdOrHivProgramArea && oldVO.getTheIsolateVO().getTheObservationDT().getStatusCd()!=null && oldVO.getTheIsolateVO().getTheObservationDT().getStatusCd().equalsIgnoreCase(MessageConstants.RESULT_PENDING_CD)){
							 stdHivResultStausCollection.add(oldVO.getTheIsolateVO().getTheObservationDT().getStatusCd());

						 }
						 oldVO.getTheIsolateVO().setItDirty(true);
						 oldVO.getTheIsolateVO().getTheObservationDT().setItDirty(true);
						 ObservationVO theSusceptibilityVO = oldVO.getTheSusceptibilityVO();
						 if(theSusceptibilityVO != null){
							 theSusceptibilityVO.setItDirty(true);
							 theSusceptibilityVO.getObsValueCodedDT_s(0).setItDirty(true);
							 theSusceptibilityVO.getTheObservationDT().setItDirty(true);
							 susHashMap.put(theSusceptibilityVO.getTheObservationDT().getObservationUid(), theSusceptibilityVO);
							 if (oldVO.getTheSusceptibilityCollection() != null &&
									 oldVO.getTheSusceptibilityCollection().size() > 0)
							 {
								 Iterator<Object>  susIt = oldVO.getTheSusceptibilityCollection().iterator();
								 while (susIt.hasNext())
								 {
									 ObservationVO susVO = (ObservationVO)susIt.next();
									 susVO.setItDirty(true);
									 susVO.setItNew(false);
									 susVO.getTheObservationDT().setItDirty(true);
									 susVO.getObsValueNumericDT_s(0).setItDirty(true);
									 susVO.getObsValueCodedDT_s(0).setItDirty(true);
									 susVO.getObservationInterpDT_s(0).setItDirty(true);
									 //susVO.getObservationInterpDT_s(0).setInterpretationDescTxt("SUSCEPTIBILITY");
									 setInterpretationDescTxt(susVO);

									 susHashMap.put(susVO.getTheObservationDT().getObservationUid(), susVO);
								 }
							 } // IF condition
						 } //End of if(theSusceptibilityVO != null)
						 ObservationVO theTrackIsolateVO = oldVO.getTheLab329VO();
						 if(theTrackIsolateVO != null){
							 theTrackIsolateVO.setItDirty(true);
							 theTrackIsolateVO.getObsValueCodedDT_s(0).setItDirty(true);
							 theTrackIsolateVO.getTheObservationDT().setItDirty(true);
							 //susHashMap.put(theTrackIsolateVO.getTheObservationDT().getObservationUid(), theTrackIsolateVO);
							 if (oldVO.getTheTrackIsolateCollection() != null &&
									 oldVO.getTheTrackIsolateCollection().size() > 0)
							 {
								 track329aHashMap.put(theTrackIsolateVO.getTheObservationDT().getCd(),theTrackIsolateVO);
								 Iterator<Object>  trackIt = oldVO.getTheTrackIsolateCollection().iterator();
								 while (trackIt.hasNext())
								 {
									 ObservationVO trackVO = (ObservationVO)trackIt.next();
									 trackVO.setItDirty(true);
									 trackVO.setItNew(false);
									 trackVO.getTheObservationDT().setItDirty(true);
									 trackVO.getObsValueNumericDT_s(0).setItDirty(true);
									 trackVO.getObsValueCodedDT_s(0).setItDirty(true);
									 trackVO.getObsValueTxtDT_s(0).setItDirty(true);
									 track329aHashMap.put(trackVO.getTheObservationDT().getCd(),trackVO);
								 }
							 } // IF condition
						 }
						 trackUIDHashMap.put(oldVO.getTheIsolateVO().getTheObservationDT().getObservationUid(),track329aHashMap);	

						 oldHashMap.put(oldVO.getTheIsolateVO().getTheObservationDT().getObservationUid(),
								 oldVO);
					 }
				 }// End of while (itor.hasNext())
			 } // End of if (oldObsColl != null && oldObsColl.size() > 0)

			 Iterator<Object>  itor2 = newObsColl.iterator();
			 while(itor2.hasNext()){
				 ResultedTestVO newVO = (ResultedTestVO)itor2.next();
				 if (newVO != null)
				 {
					 ResultedTestVO resultedTestVO = (ResultedTestVO)oldHashMap.get(newVO.getTheIsolateVO().getTheObservationDT().getObservationUid());
					 if (resultedTestVO != null) //true if exists already in collection
					 {

						 if (resultedTestVO.getTheSusceptibilityCollection() != null &&
								 resultedTestVO.getTheSusceptibilityCollection().size() > 0){
							 Iterator<Object> susIt = resultedTestVO.getTheSusceptibilityCollection().iterator();
							 while (susIt.hasNext())
							 {
								 ObservationVO susVO = (ObservationVO)susIt.next();
								 setInterpretationDescTxt(susVO);
							 }

						 }

						 if(newVO.getTheSusceptibilityVO().getTheObservationDT().getStatusCd().equals("") ||
								 newVO.getTheSusceptibilityVO().getTheObservationDT()
								 .getStatusCd().equalsIgnoreCase("I"))
						 {
							 ObservationVO isolatedVO = resultedTestVO.getTheIsolateVO();
							 //setInterpretationDescTxt(isolatedVO);
							 if(newVO.getTheIsolateVO().getTheObservationDT().getObservationUid()!= null)
							 {
								 Collection<Object>  collLab112 = obsVOLab112.
										 getTheActRelationshipDTCollection();
								 if (collLab112 != null) {
									 Iterator<Object>  it = collLab112.iterator();
									 while (it.hasNext()) {
										 ActRelationshipDT actLAB112 = (ActRelationshipDT) it.next();
										 if (actLAB112.getSourceActUid().equals(isolatedVO.
												 getTheObservationDT().getObservationUid())) {
											 actLAB112.setItDelete(true);
											 actRelationColl.add(actLAB112);
										 }
									 }

									 Iterator<Object>  itSPRT = collLab112.iterator();
									 while (itSPRT.hasNext()) {
										 ActRelationshipDT actLAB222 = (ActRelationshipDT) itSPRT.next();
										 if (actLAB222.getTypeCd().equalsIgnoreCase("SPRT")) {
											 actLAB222.setItDelete(true);
											 actRelationColl.add(actLAB222);
										 }
									 }

								 }
								 isolatedVO.setItDirty(true);
								 isolatedVO.getTheObservationDT().setItDirty(true);
								 boolean matchFound = stdHivResultStausCollection.remove(isolatedVO.getTheObservationDT().getStatusCd());
								 if(matchFound)
									 logger.debug("There exisits a matching result status from old and new Result status! For isolatedVO.getTheObservationDT().getStatusCd()"+isolatedVO.getTheObservationDT().getStatusCd());
								 editObsColl.add(isolatedVO);
								 Collection<Object>  actRelaColl = isolatedVO.
										 getTheActRelationshipDTCollection();
								 if (actRelaColl != null) {
									 Iterator<Object>  actRelaIterator = actRelaColl.iterator();
									 while (actRelaIterator.hasNext()) {
										 ActRelationshipDT act1 = (ActRelationshipDT)
												 actRelaIterator.next();
										 act1.setItDelete(true);
										 actRelationColl.add(act1);
									 }
								 }


								 ObservationVO theSusVO = resultedTestVO.getTheSusceptibilityVO();
								 if (theSusVO != null && theSusVO.getTheObservationDT().getObservationUid() != null) {
									 theSusVO.setItDirty(true);




									 theSusVO.getTheObservationDT().setItDirty(true);
									 editObsColl.add(theSusVO);
									 Collection<Object>  actRelaTheSusColl = theSusVO.
											 getTheActRelationshipDTCollection();
									 if (actRelaTheSusColl != null) {
										 Iterator<Object>  actRelaIterator = actRelaTheSusColl.iterator();
										 while (actRelaIterator.hasNext()) {
											 ActRelationshipDT act2 = (ActRelationshipDT)
													 actRelaIterator.next();
											 act2.setItDelete(true);
											 actRelationColl.add(act2);
										 }
									 }
								 }
								 Collection<Object>  susColl = resultedTestVO.
										 getTheSusceptibilityCollection();
								 if (susColl != null) {
									 Iterator<Object>  susIt = susColl.iterator();
									 while (susIt.hasNext()) {
										 ObservationVO oldSusVO = (ObservationVO) susIt.next();
										 oldSusVO.setItDirty(true);
										 oldSusVO.getTheObservationDT().setItDirty(true);

										 editObsColl.add(oldSusVO);

										 Collection<Object>  actRelaSusColl = oldSusVO.
												 getTheActRelationshipDTCollection();
										 if (actRelaSusColl != null) {
											 Iterator<Object>  actRelaIterator = actRelaSusColl.iterator();
											 while (actRelaIterator.hasNext()) {
												 ActRelationshipDT act3 = (ActRelationshipDT)
														 actRelaIterator.next();
												 act3.setItDelete(true);
												 actRelationColl.add(act3);
											 }
										 }
									 }
								 }
							 }

						 }
						 else
						 {
							 String indicatorOld = resultedTestVO.getTheIsolateVO().getTheObservationDT().
									 getCtrlCdUserDefined1();
							 String indicatorNew = newVO.getTheIsolateVO().getTheObservationDT().
									 getCtrlCdUserDefined1();

							 resultedTestVO.getTheIsolateVO().getTheObservationDT().setActivityToTime(
									 newVO.getTheIsolateVO().getTheObservationDT().
									 getActivityToTime());
							 resultedTestVO.getTheIsolateVO().getObsValueTxtDT_s(0).setValueTxt(
									 newVO.getTheIsolateVO().getObsValueTxtDT_s(0).getValueTxt());

							 resultedTestVO.getTheIsolateVO().getObsValueTxtDT_s(1).setValueTxt(
									 newVO.getTheIsolateVO().getObsValueTxtDT_s(1).getValueTxt());
							 ObservationUtils.trimEmptyObsValueCodedDTCollections(newVO.getTheIsolateVO());
							 String searchResult = newVO.getTheIsolateVO().
									 getObsValueCodedDT_s(0).getSearchResultRT();

							 String codeSystemCd = newVO.getTheIsolateVO().
									 getObsValueCodedDT_s(0).getCdSystemCdRT();






							 //logger.debug("getHiddenCd -->" + newVO.getTheIsolateVO().getObsValueCodedDT_s(0).getHiddenCd());

							 if(newVO.getTheIsolateVO().getTheObservationDT().
									 getCtrlCdUserDefined1()!=null && newVO.getTheIsolateVO().getTheObservationDT().
									 getCtrlCdUserDefined1().equalsIgnoreCase("Y")){

								 if (codeSystemCd != null && codeSystemCd.equals("SNM"))
								 {
									 resultedTestVO.getTheIsolateVO().getObsValueCodedDT_s(0).
									 setCodeSystemCd("SNM");
								 }else{
									 resultedTestVO.getTheIsolateVO().getObsValueCodedDT_s(0).
									 setCodeSystemCd("NBS");
								 }

								 if (searchResult != null && !searchResult.equals("")) {
									 resultedTestVO.getTheIsolateVO().getObsValueCodedDT_s(0).
									 setDisplayName(
											 newVO.getTheIsolateVO().getObsValueCodedDT_s(0).
											 getHiddenCd());
									 resultedTestVO.getTheIsolateVO().getObsValueCodedDT_s(0).
									 setCode(searchResult);

									 resultedTestVO.getTheIsolateVO().getTheObservationDT().
									 setCtrlCdUserDefined1("Y");

									 //newVO.getTheIsolateVO().getObsValueCodedDT_s(0).
									 //setDisplayName(null);
								 }else if (newVO.getTheIsolateVO().getObsValueCodedDT_s(0).
										 getDisplayName() != null) {
									 String displayName = newVO.getTheIsolateVO().
											 getObsValueCodedDT_s(0).
											 getDisplayName();
									 if (displayName != null) {
										 resultedTestVO.getTheIsolateVO().getObsValueCodedDT_s(0).
										 setCode(displayName);
									 }
									 else {
										 resultedTestVO.getTheIsolateVO().getObsValueCodedDT_s(0).
										 setCode("NI");
									 }

									 CachedDropDownValues cdv = new CachedDropDownValues();
									 String temp = cdv.getOrganismListDesc(newVO.getTheIsolateVO().
											 getObsValueCodedDT_s(0).
											 getDisplayName());
									 resultedTestVO.getTheIsolateVO().getObsValueCodedDT_s(0).
									 setDisplayName(temp);
									 resultedTestVO.getTheIsolateVO().getTheObservationDT().
									 setCtrlCdUserDefined1("Y");
								 }
							 }
							 else{
								 if (newVO.getTheIsolateVO().getObsValueCodedDT_s(0).getCode() != null &&
										 !newVO.getTheIsolateVO().getObsValueCodedDT_s(0).getCode().
										 equals("")) {
									 resultedTestVO.getTheIsolateVO().getTheObservationDT().
									 setCtrlCdUserDefined1("N");

									 resultedTestVO.getTheIsolateVO().getObsValueCodedDT_s(0).
									 setCode(
											 newVO.getTheIsolateVO().getObsValueCodedDT_s(0).getCode());
									 CachedDropDownValues cdv = new CachedDropDownValues();
									 String temp = cdv.getCodedResultDesc(newVO.getTheIsolateVO().
											 getObsValueCodedDT_s(0).getCode());
									 resultedTestVO.getTheIsolateVO().getObsValueCodedDT_s(0).
									 setDisplayName(temp);
									 resultedTestVO.getTheIsolateVO().getObsValueCodedDT_s(0).
									 setCodeSystemCd("NBS");
								 }
							 }



							 boolean flag = false;
							 boolean isNew = false;


							 if(indicatorOld.equalsIgnoreCase("Y") &&
									 indicatorNew.equalsIgnoreCase("Y")){
								 flag= true;

							 }
							 else
								 flag = false;

							 if(!indicatorOld.equalsIgnoreCase("Y") &&
									 indicatorNew!=null && indicatorNew.equalsIgnoreCase("Y")){
								 isNew= true;
							 }

							 if (newVO.getTheIsolateVO().getTheObservationDT().getSearchResultRT() != null &&
									 newVO.getTheIsolateVO().getTheObservationDT().getSearchResultRT().length() != 0) {

								 resultedTestVO.getTheIsolateVO().getTheObservationDT().
								 setCdDescTxt(newVO.getTheIsolateVO().getTheObservationDT().getSearchResultRT());
							 }
							 else {
								 resultedTestVO.getTheIsolateVO().getTheObservationDT().
								 setCdDescTxt(newVO.getTheIsolateVO().getTheObservationDT().
										 getCdDescTxt());
							 }

							 String cdSystemCdRT = newVO.getTheIsolateVO().getTheObservationDT().getCdSystemCdRT();

							 if (cdSystemCdRT != null && !cdSystemCdRT.equals("")) {
								 resultedTestVO.getTheIsolateVO().getTheObservationDT().
								 setCdSystemCd(cdSystemCdRT);
								 if(cdSystemCdRT.equalsIgnoreCase("LN"))
									 resultedTestVO.getTheIsolateVO().getTheObservationDT().setCdSystemDescTxt("LOINC");
								 else
									 resultedTestVO.getTheIsolateVO().getTheObservationDT().setCdSystemDescTxt("NEDSS Base System");
							 }
							 else {
								 String labId = (String) request.getParameter("labId");
								 if (labId != null && !labId.equals("")&& !labId.equals("null")) {
									 resultedTestVO.getTheIsolateVO().getTheObservationDT().setCdSystemCd(labId);
									 resultedTestVO.getTheIsolateVO().getTheObservationDT().setCdSystemDescTxt("NEDSS Base System");
								 }
								 else {
									 resultedTestVO.getTheIsolateVO().getTheObservationDT().
									 setCdSystemCd("NBS");
									 resultedTestVO.getTheIsolateVO().getTheObservationDT().setCdSystemDescTxt("NEDSS Base System");
								 }

							 }

							 ObservationDT obsDT= newVO.getTheIsolateVO().getTheObservationDT();

							 if(obsDT.getHiddenCd()!= null && !obsDT.getHiddenCd().equals("")){
								 resultedTestVO.getTheIsolateVO().getTheObservationDT().setCd(obsDT.getHiddenCd());
							 }

							 ArrayList<Object> aList = this.setNumericValue( newVO.getTheIsolateVO().getTheObsValueNumericDTCollection(),
									 resultedTestVO.getTheIsolateVO().getTheObsValueNumericDTCollection());
							 resultedTestVO.getTheIsolateVO().setTheObsValueNumericDTCollection(aList);

							 resultedTestVO.getTheIsolateVO().getObsValueTxtDT_s(0).setValueTxt(
									 newVO.getTheIsolateVO().getObsValueTxtDT_s(0).getValueTxt());


							 resultedTestVO.getTheIsolateVO().getTheObservationDT().setStatusCd(
									 newVO.getTheIsolateVO().getTheObservationDT().getStatusCd());

							 resultedTestVO.getTheIsolateVO().setItDirty(true);
							 resultedTestVO.getTheIsolateVO().getTheObservationDT().setItDirty(true);

							 editObsColl.add(resultedTestVO.getTheIsolateVO()); ///// Adding collection for edit

							 Collection<Object>  collLab112 = obsVOLab112.getTheActRelationshipDTCollection();
							 if(collLab112 != null){
								 Iterator<Object>  itLAB112 = collLab112.iterator();
								 while(itLAB112.hasNext()){
									 ActRelationshipDT actLAB112Resulted = (ActRelationshipDT) itLAB112.next();
									 if(actLAB112Resulted.getSourceActUid().equals(resultedTestVO.getTheIsolateVO().getTheObservationDT().getObservationUid())){
										 actLAB112Resulted.setItDirty(true);
										 actRelationColl.add(actLAB112Resulted);
									 }
								 }
							 }

							 ObservationVO theSusceptibilityVO = newVO.getTheSusceptibilityVO();

							 if(isNew){
								 if (theSusceptibilityVO != null) {

									 theSusceptibilityVO.getTheObservationDT().setObservationUid(new
											 Long(tempUID--));
									 theSusceptibilityVO.setItNew(true);
									 theSusceptibilityVO.setItDirty(false);
									 theSusceptibilityVO.getTheObservationDT().
									 setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT);
									 theSusceptibilityVO.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.R_ORDER);
									 theSusceptibilityVO.getTheObservationDT().setStatusTime(new
											 java.sql.Timestamp(new Date().getTime()));

									 
									 theSusceptibilityVO.getTheObservationDT().setCdDescTxt(
											 "No Information Given");
									 theSusceptibilityVO.getTheObservationDT().setItNew(true);
									 theSusceptibilityVO.getTheObservationDT().setItDirty(false);

									 ObservationUtils.trimEmptyObsValueCodedDTCollections(
											 theSusceptibilityVO);
									 Iterator<Object>  codeDTTheSusceptibilityVO = theSusceptibilityVO.
											 getTheObsValueCodedDTCollection().iterator();
									 while (codeDTTheSusceptibilityVO.hasNext()) {
										 ObsValueCodedDT obsValueCodeDT = (ObsValueCodedDT)
												 codeDTTheSusceptibilityVO.
												 next();
										 if (obsValueCodeDT.getCode() == null ||
												 obsValueCodeDT.getCode().trim().length() == 0)
											 obsValueCodeDT.setCode("NI");

										 obsValueCodeDT.setItNew(true);
									 }

									 editObsColl.add(theSusceptibilityVO);

									 actRelationColl.add(genericActRelationShipCreate(
											 "ACTIVE",
											 theSusceptibilityVO.getTheObservationDT().
											 getObservationUid(), "OBS", "A", obsDT.getObservationUid(),
											 "OBS", "REFR", "Refer to"));
									 actRelationColl.add(genericActRelationShipCreate(
											 "ACTIVE",
											 theSusceptibilityVO.getTheObservationDT().
											 getObservationUid(), "OBS", "A",
											 obsVOLab112.getTheObservationDT().getObservationUid(),
											 "OBS", "SPRT", "Has Support"));

									 if (theSusceptibilityVO.getTheObsValueCodedDTCollection() != null) {
										 Iterator<Object>  codeIT = theSusceptibilityVO.
												 getTheObsValueCodedDTCollection().iterator();
										 ObservationUtils.trimEmptyObsValueCodedDTCollections(
												 theSusceptibilityVO);

										 while (codeIT.hasNext()) {
											 ObsValueCodedDT obsValueCodeDT = (ObsValueCodedDT) codeIT.
													 next();
											 if (obsValueCodeDT.getCode() == null ||
													 obsValueCodeDT.getCode().trim().length() == 0)
												 obsValueCodeDT.setCode("NI");

											 obsValueCodeDT.setItNew(true);

											 if (obsValueCodeDT.getCode().equalsIgnoreCase("y")) {
												 editSusceptabilitiesVO(newVO, theSusceptibilityVO, tempUID, actRelationColl, editObsColl, true );

											 }

										 }
									 }
								 } // this is end of if(theSusceptibilityVO != null)

							 }
							 if(!flag){

								 Iterator<Object>  itSPRTTemp = collLab112.iterator();
								 while (itSPRTTemp.hasNext()) {
									 ActRelationshipDT actLAB222 = (ActRelationshipDT) itSPRTTemp.next();
									 if (actLAB222.getTypeCd().equalsIgnoreCase("SPRT")) {
										 actLAB222.setItDelete(true);
										 actRelationColl.add(actLAB222);
									 }
								 }

								 Collection<Object>  resultActCollection  = resultedTestVO.getTheIsolateVO().getTheActRelationshipDTCollection();
								 if(resultActCollection  != null){
									 Iterator<Object>  resultIT = resultActCollection.iterator();
									 while(resultIT.hasNext()){
										 ActRelationshipDT actReulted = (ActRelationshipDT)resultIT.next();
										 actReulted.setItDelete(true);
										 actReulted.setItDirty(false);
										 actRelationColl.add(actReulted);
									 }
								 }
								 ObservationVO theSusVO = resultedTestVO.getTheSusceptibilityVO();
								 if(theSusVO != null && theSusVO.getTheObservationDT().getObservationUid()!= null){
									 theSusVO.setItDelete(true);
									 theSusVO.getTheObservationDT().setItDelete(true);
									 editObsColl.add(theSusVO);

									 Collection<Object>  actLAB110Coll = theSusVO.getTheActRelationshipDTCollection();
									 if(actLAB110Coll != null){
										 Iterator<Object>  actLAB110 = actLAB110Coll.iterator();
										 while (actLAB110.hasNext()) {
											 ActRelationshipDT actReulted = (ActRelationshipDT)actLAB110.next();
											 actReulted.setItDelete(true);
											 actReulted.setItDirty(false);
											 actRelationColl.add(actReulted);
										 }
									 }
								 }

								 Collection<Object>  lab110Coll = resultedTestVO.getTheSusceptibilityCollection();
								 if(lab110Coll != null){
									 Iterator<Object>  itLAB110 = lab110Coll.iterator();
									 while(itLAB110.hasNext()){
										 ObservationVO lab110VO = (ObservationVO) itLAB110.next();
										 lab110VO.setItDelete(true);
										 lab110VO.getTheObservationDT().setItDelete(true);
										 editObsColl.add(lab110VO);
									 }
								 }
							 } // END of  if(!flag)


							 if(flag){
								 editSusceptabilitiesVO(newVO, theSusceptibilityVO, tempUID, actRelationColl, editObsColl, false );
								 Collection<Object>  resultActCollection  = resultedTestVO.getTheIsolateVO().getTheActRelationshipDTCollection();
								 if(resultActCollection  != null){
									 Iterator<Object>  resultIT = resultActCollection.iterator();
									 while(resultIT.hasNext()){
										 ActRelationshipDT actReulted = (ActRelationshipDT)resultIT.next();
										 actReulted.setItDirty(true);
										 actRelationColl.add(actReulted);
									 }
								 }

								 if (theSusceptibilityVO != null && theSusceptibilityVO.getTheObservationDT().getObservationUid()!= null) {


									 if (
											 theSusceptibilityVO.getObsValueCodedDT_s(0).getCode().equals("")||
											 theSusceptibilityVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("N"))
									 {
										 ObservationVO tempTheSusceptibilityVO = resultedTestVO.getTheSusceptibilityVO();

										 ObservationUtils.trimEmptyObsValueCodedDTCollections(tempTheSusceptibilityVO);

										 tempTheSusceptibilityVO.getObsValueCodedDT_s(0).setCode(
												 newVO.getTheSusceptibilityVO().getObsValueCodedDT_s(0).
												 getCode() != null?newVO.getTheSusceptibilityVO().getObsValueCodedDT_s(0).
														 getCode(): "NI");

										 tempTheSusceptibilityVO.getObsValueCodedDT_s(0).setCode(
												 newVO.getTheSusceptibilityVO().getObsValueCodedDT_s(0).
												 getCode().equals("") ?"NI":newVO.getTheSusceptibilityVO().getObsValueCodedDT_s(0).
														 getCode());

										 tempTheSusceptibilityVO.setItDirty(true);
										 tempTheSusceptibilityVO.getTheObservationDT().setItDirty(true);

										 editObsColl.add(tempTheSusceptibilityVO); ///// Adding collection for edit

										 Collection<Object>  actRelaSusColl = tempTheSusceptibilityVO.getTheActRelationshipDTCollection();
										 if (actRelaSusColl != null) {
											 Iterator<Object>  actRelaIterator = actRelaSusColl.iterator();
											 while (actRelaIterator.hasNext()) {
												 ActRelationshipDT act = (ActRelationshipDT)actRelaIterator.next();
												 if(act.getTypeCd().equalsIgnoreCase("COMP")){
													 act.setItDelete(true);
													 actRelationColl.add(act);
												 }
												 else{
													 act.setItDirty(true);
													 actRelationColl.add(act);
												 }
											 }
										 }

										 Collection<Object>  tempSusceptibilityVO = resultedTestVO.getTheSusceptibilityCollection();
										 if(tempSusceptibilityVO != null){
											 Iterator<Object>  itTemp = tempSusceptibilityVO.iterator();
											 while(itTemp.hasNext()){
												 ObservationVO tempObsVO = (ObservationVO)itTemp.next();
												 tempObsVO.setItDirty(true);
												 tempObsVO.getTheObservationDT().setItDirty(true);
												 editObsColl.add(tempObsVO);
											 }
										 }

									 }

									 if (theSusceptibilityVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("Y"))
									 {
										 ObservationVO tempTheSusceptibilityVO = resultedTestVO.
												 getTheSusceptibilityVO();

										 ObservationUtils.trimEmptyObsValueCodedDTCollections(tempTheSusceptibilityVO);

										 tempTheSusceptibilityVO.getObsValueCodedDT_s(0).setCode(
												 newVO.getTheSusceptibilityVO().getObsValueCodedDT_s(0).
												 getCode() != null ? newVO.getTheSusceptibilityVO().getObsValueCodedDT_s(0).
														 getCode():"NI");
										 tempTheSusceptibilityVO.setItDirty(true);
										 tempTheSusceptibilityVO.getTheObservationDT().setItDirty(true);



										 editObsColl.add(tempTheSusceptibilityVO); ///// Adding collection for edit


										 Collection<Object>  ActForTheSusc = tempTheSusceptibilityVO.getTheActRelationshipDTCollection();
										 if (ActForTheSusc != null) {
											 Iterator<Object>  itTheSusc = ActForTheSusc.iterator();
											 while (itTheSusc.hasNext()) {
												 ActRelationshipDT act3 = (ActRelationshipDT) itTheSusc.
														 next();
												 if (act3.getTypeCd().equalsIgnoreCase("SPRT")) {
													 act3.setItDirty(true);
													 actRelationColl.add(act3);
												 }
											 }

										 }

										 if (newVO.getTheSusceptibilityCollection() != null &&
												 newVO.getTheSusceptibilityCollection().size() > 0) {
											 Iterator<Object>  susIt = newVO.getTheSusceptibilityCollection().iterator();
											 while (susIt.hasNext()) {
												 ObservationVO newSusVO = (ObservationVO) susIt.next();

												 ObservationVO oldSusVO = (ObservationVO)
														 susHashMap.get(newSusVO.getTheObservationDT().getObservationUid());
												 if (oldSusVO != null) {

													 if (newSusVO.getTheObservationDT().getStatusCd().equalsIgnoreCase("I"))
													 {
														 oldSusVO.setItDirty(true);

														 oldSusVO.getTheObservationDT().setItDirty(false);

														 editObsColl.add(oldSusVO);

														 Collection<Object>  suscCollection  = resultedTestVO.getTheSusceptibilityVO().
																 getTheActRelationshipDTCollection();
														 if (suscCollection  != null) {
															 Iterator<Object>  suscITerator = suscCollection.iterator();
															 while (suscITerator.hasNext()) {
																 ActRelationshipDT actSusc = (ActRelationshipDT)
																		 suscITerator.next();
																 if (actSusc.getSourceActUid().equals(oldSusVO.
																		 getTheObservationDT().getObservationUid())) {
																	 actSusc.setItDelete(true);
																	 actRelationColl.add(actSusc);
																 }
															 }
														 }
													 }
													 else {

														 ObservationDT tempObsSusDT= newSusVO.getTheObservationDT();
														 if(tempObsSusDT.getHiddenCd() != null && !tempObsSusDT.getHiddenCd().equals("")){
															 oldSusVO.getTheObservationDT().setCd(tempObsSusDT.getHiddenCd());
														 }
														 else {
															 oldSusVO.getTheObservationDT().setCd(tempObsSusDT.getCdDescTxt());
															 CachedDropDownValues cdv = new CachedDropDownValues();
															 String cdSystemCd = null;
															 String obsCd = null;
															 String cdDescTxt = null;
															 if (tempObsSusDT.getCdSystemCd() != null)
																 cdSystemCd = tempObsSusDT.getCdSystemCd();

															 if (tempObsSusDT.getCd() != null)
																 obsCd = tempObsSusDT.getCd();

															 if (cdSystemCd != null && obsCd != null) {
																 if (cdSystemCd.equals("LN")){
																	 cdDescTxt = cdv.getResultedTestDesc(obsCd);
																	 oldSusVO.getTheObservationDT().setCdSystemDescTxt("LOINC");
																 }
																 else if (!cdSystemCd.equals("LN"))
																	 cdDescTxt = cdv.getResultedTestDescLab(cdSystemCd, obsCd);
															 }
															 if (cdDescTxt != null)
																 oldSusVO.getTheObservationDT().setCdDescTxt(cdDescTxt);

														 }
														 oldSusVO.getTheObservationDT().setActivityToTime(
																 newSusVO.
																 getTheObservationDT().getActivityToTime());
														 oldSusVO.getTheObservationDT().setStatusCd(newSusVO.
																 getTheObservationDT().getStatusCd());
														 oldSusVO.getTheObservationDT().setMethodCd(
																 newSusVO.getTheObservationDT().getMethodCd());


														 if (newSusVO.getTheObservationDT().getSearchResultRT() != null &&
																 newSusVO.getTheObservationDT().getSearchResultRT().length() !=0) {
															 oldSusVO.getTheObservationDT().setCdDescTxt(newSusVO.getTheObservationDT().getSearchResultRT());
														 }
														 else {
															 oldSusVO.getTheObservationDT().setCdDescTxt(
																	 newSusVO.getTheObservationDT().getCdDescTxt());
														 }

														 String cdSystemCdDrugRT = newSusVO.getTheObservationDT().getCdSystemCdRT();

														 if (cdSystemCdDrugRT != null &&
																 !cdSystemCdDrugRT.equals("")) {
															 oldSusVO.getTheObservationDT().setCdSystemCd(cdSystemCdDrugRT);
															 if(cdSystemCdDrugRT.equalsIgnoreCase("LN"))
																 oldSusVO.getTheObservationDT().setCdSystemDescTxt("LOINC");
														 }
														 else {
															 /** The system should not use labID(Facility ID
															  *  from Reporting Facility) to derive Drug Name.
															  *  See the  NBS Laboratory Report Table Maintenance Guide.doc
															  *  for detail information.It should always use 'DEFAULT'
															  */
															 /*
                              String labId = (String) request.getParameter(
                                  "labId");
                              if (labId != null && !labId.equals("")&& !labId.equals("null")) {
                                oldSusVO.getTheObservationDT().setCdSystemCd(labId);

                              }
                              else {
                                oldSusVO.getTheObservationDT().setCdSystemCd("DEFAULT");
                              }
															  */
															 oldSusVO.getTheObservationDT().setCdSystemCd("DEFAULT");
															 oldSusVO.getTheObservationDT().setCdSystemDescTxt("NEDSS Base System");
														 }

														 ObservationDT obsSusDT =newSusVO.getTheObservationDT();
														 if(obsSusDT.getHiddenCd() != null && !obsSusDT.getHiddenCd().equals("")){
															 oldSusVO.getTheObservationDT().setCd(obsSusDT.getHiddenCd());
														 }
														 else  {
															 oldSusVO.getTheObservationDT().setCd(obsSusDT.getCdDescTxt());
															 CachedDropDownValues cdv = new CachedDropDownValues();
															 String cdSystemCd = null;
															 String obsCd = null;
															 String cdDescTxt = null;
															 if (oldSusVO.getTheObservationDT().getCdSystemCd() != null)
																 cdSystemCd = oldSusVO.getTheObservationDT().getCdSystemCd();

															 if (oldSusVO.getTheObservationDT().getCd() != null) // use getCd() instead of  getCdDescTxt
															 obsCd = oldSusVO.getTheObservationDT().getCd(); // Use getCd()    "                           "

															 if (cdSystemCd != null && obsCd != null) {
																 if (cdSystemCd.equals("LN"))
																	 cdDescTxt = cdv.getResultedTestDesc(obsCd);
																 else if (!cdSystemCd.equals("LN"))
																	 cdDescTxt = cdv.getResultedTestDescLab(cdSystemCd, obsCd);
															 }
															 if (cdDescTxt != null)
																 oldSusVO.getTheObservationDT().setCdDescTxt(cdDescTxt);

														 }



														 ArrayList<Object> bList = this.setNumericValue( newSusVO.getTheObsValueNumericDTCollection(),
																 oldSusVO.getTheObsValueNumericDTCollection());
														 oldSusVO.setTheObsValueNumericDTCollection(bList);

														 ObservationUtils.trimEmptyObsValueCodedDTCollections(oldSusVO);

														 oldSusVO.getObsValueCodedDT_s(0).setCode(
																 newSusVO.getObsValueCodedDT_s(0).getCode() != null
																 &&!newSusVO.getObsValueCodedDT_s(0).getCode().equals("")?
																		 newSusVO.getObsValueCodedDT_s(0).getCode():"NI");
														 //setDisplayName
														 CachedDropDownValues cdv = new CachedDropDownValues();
							                             String result =  cdv.getCodedResultDesc(oldSusVO.getObsValueCodedDT_s(0).getCode());
							                             oldSusVO.getObsValueCodedDT_s(0).setDisplayName(result);
							                              	
														 oldSusVO.getObservationInterpDT_s(0).
														 setInterpretationCd(
																 newSusVO.getObservationInterpDT_s(0).
																 getInterpretationCd());
														 if(newSusVO.getObservationInterpDT_s(0).getInterpretationCd()!= null
																 && !newSusVO.getObservationInterpDT_s(0).getInterpretationCd().equals("")){
															 String temp= this.getDescTxt("OBS_INTRP",
																	 newSusVO.getObservationInterpDT_s(0).getInterpretationCd());
															 if(temp != null){                 
																 newSusVO.getObservationInterpDT_s(0).setInterpretationDescTxt(temp);
															 }
														 }
														 oldSusVO.setItDirty(true);
														 oldSusVO.getTheObservationDT().setItDirty(true);

														 editObsColl.add(oldSusVO); ///// Adding collection for edit

														 Collection<Object>  suscCollection  =resultedTestVO.getTheSusceptibilityVO().
																 getTheActRelationshipDTCollection();

														 if (suscCollection  != null) {
															 Iterator<Object>  suscITerator = suscCollection.iterator();
															 while (suscITerator.hasNext()) {
																 ActRelationshipDT actSusc = (ActRelationshipDT)
																		 suscITerator.next();
																 if (actSusc.getSourceActUid().equals(oldSusVO.
																		 getTheObservationDT().getObservationUid())) {
																	 actSusc.setItDirty(true);
																	 actRelationColl.add(actSusc);
																 }
															 }
														 }
													 }

												 } // End of if (oldSusVO != null)
												 else {
													 if (newSusVO.getTheObservationDT().getStatusCd().equalsIgnoreCase("I") ||
															 newSusVO.getTheObservationDT().getStatusCd().equalsIgnoreCase("") ||
															 newSusVO.getTheObservationDT().getStatusCd() == null)
													 {
														 //logger.debug("\n\n  Data is not there");
													 }
													 else{

														 ObservationDT obsSusDT = newSusVO.getTheObservationDT();
														 newSusVO.setItNew(true);
														 newSusVO.setItDirty(false);
														 obsSusDT.setItNew(true);
														 obsSusDT.setItDirty(false);
														 obsSusDT.setObservationUid(new Long(tempUID--));


														 if (obsSusDT.getSearchResultRT() != null && obsSusDT.getSearchResultRT().length() != 0){

															 obsSusDT.setCdDescTxt(obsSusDT.getSearchResultRT());
														 }

														 String cdSystemCdDrugRT = obsSusDT.getSearchResultRT();

														 if (cdSystemCdDrugRT != null &&
																 !cdSystemCdDrugRT.equals("")) {
															 obsSusDT.setCdSystemCd(cdSystemCdDrugRT);
															 if(cdSystemCdRT.equalsIgnoreCase("LN"))
																 obsSusDT.setCdSystemDescTxt("LOINC");
															 else
																 obsSusDT.setCdSystemDescTxt("NEDSS Base System");
														 }
														 else {
															 /** The system should not use labID(Facility ID
															  *  from Reporting Facility) to derive Drug Name.
															  *  See the  NBS Laboratory Report Table Maintenance Guide.doc
															  *  for detail information.It should always use 'DEFAULT'
															  */

															 obsSusDT.setCdSystemCd("DEFAULT");
															 obsSusDT.setCdSystemDescTxt("NEDSS Base System");

														 }
														 if(obsSusDT.getHiddenCd() != null && !obsSusDT.getHiddenCd().equals("")){
															 obsSusDT.setCd(obsSusDT.getHiddenCd());
														 }
														 else  {
															 obsSusDT.setCd(obsSusDT.getCdDescTxt());
															 CachedDropDownValues cdv = new CachedDropDownValues();
															 String cdSystemCd = null;
															 String obsCd = null;
															 String cdDescTxt = null;
															 if (obsSusDT.getCdSystemCd() != null)
																 cdSystemCd = obsSusDT.getCdSystemCd();

															 if (obsSusDT.getCd() != null)
																 obsCd = obsSusDT.getCd();

															 if (cdSystemCd != null && obsCd != null) {
																 if (cdSystemCd.equals("LN")){
																	 cdDescTxt = cdv.getResultedTestDesc(obsCd);
																	 obsSusDT.setCdSystemDescTxt("LOINC");
																 }
																 else if (!cdSystemCd.equals("LN"))
																	 cdDescTxt = cdv.getResultedTestDescLab(cdSystemCd, obsCd);
															 }
															 if (cdDescTxt != null)
																 obsSusDT.setCdDescTxt(cdDescTxt);

														 }


														 obsSusDT.setCtrlCdDisplayForm(NEDSSConstants.
																 LAB_REPORT);
														 obsSusDT.setObsDomainCdSt1(NEDSSConstants.R_RESULT);

														 obsSusDT.setStatusTime(new java.sql.Timestamp(new
																 Date().
																 getTime()));
														 //obsSusDT.setStatusCd("F");
														 newSusVO.setTheObservationDT(obsSusDT);

														 ObservationUtils.trimEmptyObsValueCodedDTCollections(
																 newSusVO);

														 actRelationColl.add(genericActRelationShipCreate(
																 "ACTIVE",
																 obsSusDT.getObservationUid(), "OBS", "A",
																 theSusceptibilityVO.getTheObservationDT().
																 getObservationUid(),
																 "OBS", "COMP", "Has Component"));

														 if (newSusVO.getTheObsValueCodedDTCollection() != null) {
															 Iterator<Object>  obsValueCode = newSusVO.
																	 getTheObsValueCodedDTCollection().iterator();
															 while (obsValueCode.hasNext()) {
																 ObsValueCodedDT obsValueDT = (ObsValueCodedDT)
																		 obsValueCode.next();
																 if (obsValueDT.getCode() == null ||
																		 obsValueDT.getCode().trim().length() == 0)
																	 obsValueDT.setCode("NI");
																 obsValueDT.setItNew(true);
																 obsValueDT.setItDirty(false);
															 }
														 }

														 if (newSusVO.getTheObsValueNumericDTCollection() != null) {
															 Iterator<Object>  obsValueNumeric = newSusVO.
																	 getTheObsValueNumericDTCollection().iterator();
															 while (obsValueNumeric.hasNext()) {
																 ObsValueNumericDT obsValueDT = (ObsValueNumericDT)
																		 obsValueNumeric.next();
																 if (obsValueDT.getNumericValue() != null)
																	 if (! (obsValueDT.getNumericValue().equals(""))) {
																		 ObservationUtil obsUtil = new ObservationUtil();
																		 obsValueDT = obsUtil.parseNumericResult(
																				 obsValueDT);
																	 }
																 obsValueDT.setItNew(true);
																 obsValueDT.setItDirty(false);
															 }
														 }
														 if (newSusVO.getTheObservationInterpDTCollection() != null) {
															 Iterator<Object>  interpretation = newSusVO.
																	 getTheObservationInterpDTCollection().iterator();
															 while (interpretation.hasNext()) {
																 ObservationInterpDT observationInterpDT =
																		 (ObservationInterpDT) interpretation.next();
																 String temp= this.getDescTxt("OBS_INTRP",
																		 observationInterpDT.getInterpretationCd());
																 if(temp != null){                 
																	 observationInterpDT.setInterpretationDescTxt(temp);
																 }
																 observationInterpDT.setItNew(true);
																 observationInterpDT.setItDirty(false);
															 }
														 }

														 newSusVO.setItDirty(false);
														 newSusVO.setItNew(true);
														 editObsColl.add(newSusVO); ///// Adding collection for edit
														 //                        }

													 } // ELSE IS END

												 }//8888888888888888888888

											 } // End of while loop while (susIt.hasNext())
										 }
										 //}
									 }
								 }
							 }
						 }// end of else to check statuc cd is not equal to "I"
						 //---->trackIsolate code
					 } // End of OLD collection
					 else
					 {
						 if (newVO != null && newVO.getTheSusceptibilityVO() != null
								 && (newVO.getTheSusceptibilityVO().getTheObservationDT().getStatusCd() == null ||
								 newVO.getTheSusceptibilityVO().getTheObservationDT().getStatusCd().equals("") ||
								 newVO.getTheSusceptibilityVO().getTheObservationDT().getStatusCd().equals("I"))) {
							 //logger.debug("\n\n Data is not populated ");
						 }
						 else{
							 ObservationVO isloatedVO = newVO.getTheIsolateVO();

							 ObservationVO theSusceptibilityVO = newVO.getTheSusceptibilityVO();

							 if(isloatedVO != null){

								 if (theSusceptibilityVO != null&&
										 (theSusceptibilityVO.getTheObservationDT().getStatusCd() == null ||
										 theSusceptibilityVO.getTheObservationDT().getStatusCd().equals("")||
										 theSusceptibilityVO.getTheObservationDT().getStatusCd().equals("I")))
								 {

									 //logger.debug("\n\n\n Data is not avaivlable");
								 }

								 else{
									 ObservationDT obsDT = isloatedVO.getTheObservationDT();
									 ObservationUtils.trimEmptyObsValueCodedDTCollections(isloatedVO);
									 boolean flagTemp = false;
									 if (isloatedVO.getTheObsValueCodedDTCollection() != null) {
										 Iterator<Object>  obsValueCode = isloatedVO.
												 getTheObsValueCodedDTCollection().
												 iterator();
										 while (obsValueCode.hasNext()) {
											 ObsValueCodedDT obsValueDT = (ObsValueCodedDT) obsValueCode.
													 next();
											 CachedDropDownValues cdv = new CachedDropDownValues();
											 if(isloatedVO.getTheObservationDT().getCtrlCdUserDefined1().equalsIgnoreCase("Y")){
												 if (obsValueDT.getDisplayName() != null &&
														 !obsValueDT.getDisplayName().equals("")) {
													 flagTemp = true;
													 obsValueDT.setCode(obsValueDT.getDisplayName());
													 TreeMap<Object,Object> treeMap = (TreeMap<Object,Object>) cdv.getOrganismListDesc();
													 String temp = (String) treeMap.get(obsValueDT.getCode());
													 obsValueDT.setDisplayName(temp);
													 isloatedVO.getTheObservationDT().setCtrlCdUserDefined1("Y");
												 }
												 if (obsValueDT.getSearchResultRT() != null &&
														 !obsValueDT.getSearchResultRT().equals("")) {
													 obsValueDT.setDisplayName(obsValueDT.getHiddenCd());
													 obsValueDT.setCode(obsValueDT.getSearchResultRT());
													 isloatedVO.getTheObservationDT().setCtrlCdUserDefined1("Y");
													 flagTemp = true;
												 }
											 }
											 else{
												 if (obsValueDT.getCode() == null ||
														 obsValueDT.getCode().trim().length() == 0) {
													 obsValueDT.setCode("NI");
													 isloatedVO.getTheObservationDT().setCtrlCdUserDefined1("N");
												 }
												 else {

													 String temp = cdv.getCodedResultDesc(obsValueDT.getCode());
													 obsValueDT.setDisplayName(temp);
													 isloatedVO.getTheObservationDT().setCtrlCdUserDefined1("N");
												 }
											 }


											 obsValueDT.setItNew(true);
											 //logger.debug("Setting value for snm -->" + obsValueDT.getCdSystemCdRT());
											 if (obsValueDT.getCdSystemCdRT() != null &&
													 obsValueDT.getCdSystemCdRT().equals("SNM")) {
												 obsValueDT.setCodeSystemCd("SNM");
											 }
											 else {
												 obsValueDT.setCodeSystemCd("NBS");
											 }

											 obsValueDT.setCodeSystemDescTxt("NEDSS Base System");
										 }
									 }
									 if (isloatedVO.getTheObsValueDateDTCollection() != null) {
										 Iterator<Object>  obsValueDate = isloatedVO.getTheObsValueDateDTCollection().
												 iterator();
										 while (obsValueDate.hasNext()) {
											 ObsValueDateDT obsValueDT = (ObsValueDateDT) obsValueDate.next();
											 obsValueDT.setItNew(true);
										 }
									 }
									 if (isloatedVO.getTheObsValueNumericDTCollection() != null) {
										 Iterator<Object>  obsValueNumeric = isloatedVO.
												 getTheObsValueNumericDTCollection().
												 iterator();
										 while (obsValueNumeric.hasNext()) {
											 ObsValueNumericDT obsValueDT = (ObsValueNumericDT)
													 obsValueNumeric.next();
											 obsValueDT.setItNew(true);
											 if (obsValueDT.getNumericValue() != null)
												 if (! (obsValueDT.getNumericValue().equals(""))) {
													 ObservationUtil obsUtil = new ObservationUtil();
													 obsValueDT = obsUtil.parseNumericResult(obsValueDT);
												 }
										 }
									 }
									 if (isloatedVO.getTheObsValueTxtDTCollection() != null) {
										 Iterator<Object>  obsValueText = isloatedVO.getTheObsValueTxtDTCollection().
												 iterator();
										 while (obsValueText.hasNext()) {

											 ObsValueTxtDT obsValueDT = (ObsValueTxtDT) obsValueText.next();
											 obsValueDT.setItNew(true);
										 }
										 isloatedVO.getObsValueTxtDT_s(1).setTxtTypeCd("N");
									 }
									 obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
									 obsDT.setObservationUid(new Long(tempUID--));
									 obsDT.setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT);
									 obsDT.setObsDomainCdSt1(NEDSSConstants.RESULTED_TEST_OBS_DOMAIN_CD);
									 //obsDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);


									 if (obsDT.getSearchResultRT() != null &&
											 obsDT.getSearchResultRT().length() != 0) {
										 String Resulted = obsDT.getSearchResultRT();
										 obsDT.setCdDescTxt(Resulted);
									 }

									 if (obsDT.getHiddenCd() != null &&
											 !obsDT.getHiddenCd().equals("")) {
										 obsDT.setCd(obsDT.getHiddenCd());
									 }

									 String cdSystemCdRT = obsDT.getCdSystemCdRT();
									 String labId = (String) request.getParameter("labId");

									 if (cdSystemCdRT != null && !cdSystemCdRT.equals("")) {
										 obsDT.setCdSystemCd(cdSystemCdRT);
										 if (cdSystemCdRT.equalsIgnoreCase("LN"))
											 obsDT.setCdSystemDescTxt("LOINC");
										 else
											 obsDT.setCdSystemDescTxt("NEDSS Base System");
									 }
									 else {
										 if (labId != null && !labId.equals("") &&
												 !labId.equals("null")) {
											 obsDT.setCdSystemCd(labId);
											 obsDT.setCdSystemDescTxt("NEDSS Base System");
										 }
										 else {
											 obsDT.setCdSystemDescTxt("NEDSS Base System");
											 obsDT.setCdSystemCd("NBS");
										 }
									 }

									 obsDT.setItNew(true);
									 obsDT.setItDirty(false);
									 isloatedVO.setTheObservationDT(obsDT);
									 isloatedVO.setItNew(true);
									 isloatedVO.setItDirty(false);
									 actRelationColl.add(genericActRelationShipCreate(
											 NEDSSConstants.RECORD_STATUS_ACTIVE,
											 obsDT.getObservationUid(),
											 NEDSSConstants.CLASS_CD_OBS,
											 NEDSSConstants.STATUS_ACTIVE,
											 obsVOLab112.getTheObservationDT().getObservationUid(),
											 NEDSSConstants.CLASS_CD_OBS,
											 NEDSSConstants.ACT108_TYP_CD,
											 NEDSSConstants.HAS_COMPONENT));

									 editObsColl.add(isloatedVO);

									 if (theSusceptibilityVO != null && flagTemp) {

										 theSusceptibilityVO.getTheObservationDT().setObservationUid(new  Long(tempUID--));
										 theSusceptibilityVO.setItNew(true);
										 theSusceptibilityVO.setItDirty(false);
										 theSusceptibilityVO.getTheObservationDT().setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT);
										 theSusceptibilityVO.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.R_ORDER);
										 theSusceptibilityVO.getTheObservationDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));

										 //theSusceptibilityVO.getTheObservationDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
										 theSusceptibilityVO.getTheObservationDT().setCdDescTxt("No Information Given");
										 theSusceptibilityVO.getTheObservationDT().setItNew(true);
										 theSusceptibilityVO.getTheObservationDT().setItDirty(false);

										 ObservationUtils.trimEmptyObsValueCodedDTCollections(theSusceptibilityVO);
										 Iterator<Object>  codeDTTheSusceptibilityVO = theSusceptibilityVO.
												 getTheObsValueCodedDTCollection().iterator();
										 while (codeDTTheSusceptibilityVO.hasNext()) {
											 ObsValueCodedDT obsValueCodeDT = (ObsValueCodedDT)
													 codeDTTheSusceptibilityVO.
													 next();
											 if (obsValueCodeDT.getCode() == null ||
													 obsValueCodeDT.getCode().trim().length() == 0)
												 obsValueCodeDT.setCode("NI");

											 obsValueCodeDT.setItNew(true);
										 }

										 editObsColl.add(theSusceptibilityVO);

										 actRelationColl.add(genericActRelationShipCreate(
												 "ACTIVE",
												 theSusceptibilityVO.getTheObservationDT().
												 getObservationUid(), "OBS", "A", obsDT.getObservationUid(),
												 "OBS", "REFR", "Refer to"));
										 actRelationColl.add(genericActRelationShipCreate(
												 "ACTIVE",
												 theSusceptibilityVO.getTheObservationDT().
												 getObservationUid(), "OBS", "A",
												 obsVOLab112.getTheObservationDT().getObservationUid(),
												 "OBS", "SPRT", "Has Support"));



										 if (theSusceptibilityVO.getTheObsValueCodedDTCollection() != null) {
											 Iterator<Object>  codeIT = theSusceptibilityVO.getTheObsValueCodedDTCollection().iterator();
											 ObservationUtils.trimEmptyObsValueCodedDTCollections(theSusceptibilityVO);

											 while (codeIT.hasNext()) {
												 ObsValueCodedDT obsValueCodeDT = (ObsValueCodedDT) codeIT.
														 next();
												 if (obsValueCodeDT.getCode() == null ||
														 obsValueCodeDT.getCode().trim().length() == 0)
													 obsValueCodeDT.setCode("NI");

												 obsValueCodeDT.setItNew(true);



												 if (obsValueCodeDT.getCode().equalsIgnoreCase("y")) {
													 Collection<Object>  observationVOColl = newVO.
															 getTheSusceptibilityCollection();
													 if (observationVOColl != null) { // This is LAB110 observation
														 Iterator<Object>  obsVOIt = observationVOColl.iterator();

														 while (obsVOIt.hasNext()) {
															 ObservationVO obsVO = (ObservationVO) obsVOIt.next();
															 ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
															 if(!obsVO.getTheObservationDT().getStatusCd().equalsIgnoreCase("I")){
																 ObservationUtils.trimEmptyObsValueCodedDTCollections(
																		 obsVO);
																 ObservationDT obsSusDT = obsVO.getTheObservationDT();
																 obsSusDT.setObservationUid(new Long(tempUID--));

																 actRelationColl.add(genericActRelationShipCreate(
																		 "ACTIVE",
																		 obsSusDT.getObservationUid(), "OBS", "A",
																		 theSusceptibilityVO.getTheObservationDT().
																		 getObservationUid(), "OBS", "COMP",
																		 "Has Component"));
																 if (obsVO.getTheObsValueCodedDTCollection() != null) {
																	 Iterator<Object>  obsValueCode =
																			 obsVO.getTheObsValueCodedDTCollection().
																			 iterator();
																	 while (obsValueCode.hasNext()) {
																		 ObsValueCodedDT obsValueDT = (ObsValueCodedDT)
																				 obsValueCode.next();
																		 if (obsValueDT.getCode() == null ||
																				 obsValueDT.getCode().trim().length() == 0)
																			 obsValueDT.setCode("NI");
																		 obsValueDT.setItNew(true);
																	 }
																 }
																 if (obsVO.getTheObsValueTxtDTCollection() != null) {
																	 Iterator<Object>  obsValueText =
																			 obsVO.getTheObsValueTxtDTCollection().iterator();
																	 while (obsValueText.hasNext()) {
																		 ObsValueTxtDT obsValueDT = (ObsValueTxtDT)
																				 obsValueText.next();
																		 obsValueDT.setItNew(true);
																	 }
																 }

																 if (obsVO.getTheObsValueNumericDTCollection() != null) {
																	 Iterator<Object>  obsValueNumeric =
																			 obsVO.getTheObsValueNumericDTCollection().
																			 iterator();
																	 while (obsValueNumeric.hasNext()) {
																		 ObsValueNumericDT obsValueDT = (ObsValueNumericDT)
																				 obsValueNumeric.next();
																		 if (obsValueDT.getNumericValue() != null)
																			 if (! (obsValueDT.getNumericValue().equals(""))) {
																				 ObservationUtil obsUtil = new ObservationUtil();
																				 obsValueDT = obsUtil.parseNumericResult(
																						 obsValueDT);
																			 }
																		 obsValueDT.setItNew(true);
																	 }
																 }
																 if (obsVO.getTheObservationInterpDTCollection() != null) {
																	 Iterator<Object>  interpretation = obsVO.
																			 getTheObservationInterpDTCollection().iterator();
																	 while (interpretation.hasNext()) {
																		 ObservationInterpDT observationInterpDT =
																				 (ObservationInterpDT) interpretation.next();
																		 observationInterpDT.setItNew(true);
																	 }
																 }

																 String cdSystemCdDrugRT = obsSusDT.getCdSystemCdRT();
																 if (obsSusDT.getSearchResultRT() != null &&
																		 obsSusDT.getSearchResultRT().length() !=
																		 0) {

																	 obsSusDT.setCdDescTxt(obsSusDT.getSearchResultRT());
																 }

																 if (cdSystemCdDrugRT != null &&
																		 !cdSystemCdDrugRT.equals("")) {
																	 obsSusDT.setCdSystemCd(cdSystemCdDrugRT);
																	 if(cdSystemCdDrugRT.equalsIgnoreCase("LN"))
																		 obsSusDT.setCdSystemDescTxt("LOINC");

																 }
																 else {
																	 /** The system should not use labID(Facility ID
																	  *  from Reporting Facility) to derive Drug Name.
																	  *  See the  NBS Laboratory Report Table Maintenance Guide.doc
																	  *  for detail information.It should always use 'DEFAULT'
																	  */

																	 obsSusDT.setCdSystemCd("DEFAULT");
																	 obsSusDT.setCdSystemDescTxt("NEDSS Base System");
																 }

																 if(obsSusDT.getHiddenCd() != null && !obsSusDT.getHiddenCd().equals("")){
																	 obsSusDT.setCd(obsSusDT.getHiddenCd());
																 }
																 else  {
																	 obsSusDT.setCd(obsSusDT.getCdDescTxt());
																	 CachedDropDownValues cdv = new CachedDropDownValues();
																	 String cdSystemCd = null;
																	 String obsCd = null;
																	 String cdDescTxt = null;
																	 if (obsSusDT.getCdSystemCd() != null)
																		 cdSystemCd = obsSusDT.getCdSystemCd();

																	 if (obsSusDT.getCd() != null) // use getCd() instead of  getCdDescTxt
																	 obsCd = obsSusDT.getCd(); // Use getCd()    "                           "

																	 if (cdSystemCd != null && obsCd != null) {
																		 if (cdSystemCd.equals("LN"))
																			 cdDescTxt = cdv.getResultedTestDesc(obsCd);
																		 else if (!cdSystemCd.equals("LN"))
																			 cdDescTxt = cdv.getResultedTestDescLab(cdSystemCd, obsCd);
																	 }
																	 if (cdDescTxt != null)
																		 obsSusDT.setCdDescTxt(cdDescTxt);

																 }


																 obsSusDT.setCtrlCdDisplayForm(NEDSSConstants.
																		 LAB_REPORT);
																 obsSusDT.setObsDomainCdSt1(NEDSSConstants.R_RESULT);
																 obsSusDT.setStatusTime(new java.sql.Timestamp(new
																		 Date().
																		 getTime()));
																 //obsSusDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
																 obsSusDT.setItNew(true);
																 obsSusDT.setItDirty(false);
																 obsVO.setTheObservationDT(obsSusDT);
																 obsVO.setItNew(true);
																 obsVO.setItDirty(false);
																 editObsColl.add(obsVO);
																 setObsValueCodedToDefaultValues(editObsColl);
																 //VOTester.createReport(obsVO,"observationVO from backend");
															 }
														 }
													 } // end of if (observationVOColl != null)
												 }

											 }
										 }
									 } // this is end of if(theSusceptibilityVO != null)

								 } // End status Cd value check with I
							 }

						 } // ELSE END


					 } // END of ELSE part
					 tempUID =setThetrackingInformation( obsVOLab112, oldObsColl, newVO, tempUID, 
							 actRelationColl, editObsColl,trackUIDHashMap);
				 } // End of if (newVO != null)

			 } // End ofIterator<Object>  if(itor2.hasNext())


		 } // End of if (newObsColl != null)
		 if(stdHivResultStausCollection.size()>0){
			 ArrayList<Object> messageLogDTCollection =MessageLogUtil.getMessagesFromQueue(obsVOLab112, request);
			 if(messageLogDTCollection!=null && messageLogDTCollection.size()>0)
				 obsForm.setMessageLogDTCollection(messageLogDTCollection);
		 }
	 }catch (Exception e) {
		 logger.error("Exception in Edit Lab Report Submit: " + e.getMessage());
		 e.printStackTrace();
		 throw new NEDSSAppException("Error occurred in Edit Lab Report Submit : "+e.getMessage());
	 }  
 }

 
 private ObsValueCodedDT setValueCode(ObsValueCodedDT dt){
   if ((dt.getCode() == null) || (dt.getCode().trim().length() == 0))
                    dt.setCode("NI");
   //else

   return dt;
 }

 private void setActIdForEdit(ObservationVO newLab112,ObservationVO lab112,
                              Collection<Object>  actIdColl){

   Collection<Object>  collLab112 = lab112.getTheActIdDTCollection();
   if (collLab112 != null && newLab112 != null) {
    Iterator<Object>  itor = collLab112.iterator();
     while (itor.hasNext()) {
       ActIdDT actIdDT = (ActIdDT) itor.next();

       if (newLab112.getActIdDT_s(0) != null)
         actIdDT.setRootExtensionTxt(newLab112.getActIdDT_s(0).
                                     getRootExtensionTxt());
       actIdDT.setItNew(false);
       actIdDT.setItDirty(true);
       actIdDT.setStatusCd("A");
       actIdDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
       actIdColl.add(actIdDT);
    }
  }
 }


 private void   setMaterialForEdit(Collection<Object>  newColl,
                                   Collection<Object>  oldMaterialColl,
                                   Collection<Object>  newMaterialColl){
   MaterialVO matNew = null;
   if(newColl != null){
    Iterator<Object>  it = newColl.iterator();
     while(it.hasNext()){
      matNew = (MaterialVO) it.next();
     }

   }
   if(oldMaterialColl != null){
     Iterator<Object>  itor = oldMaterialColl.iterator();
      while (itor.hasNext())
      {
        MaterialVO materialVO = (MaterialVO)itor.next();
        if(materialVO.getTheMaterialDT() != null){
           MaterialDT materialDT = materialVO.getTheMaterialDT();

           materialDT.setItDirty(true);
           materialDT.setItNew(false);
           materialVO.setItDirty(true);
           materialVO.setItNew(false);
           if(matNew != null)
             materialDT.setCd(matNew.getTheMaterialDT().getCd());
           String descTxt = getDescTxt("SPECMN_SRC",materialDT.getCd());
           materialDT.setCdDescTxt(descTxt);
           newMaterialColl.add(materialVO);
        }
      } // End of while loop
    }// if loop

 }

 private void setPersonForEdit(ObservationForm obsForm , Long personSubjectUid,
                               Collection<Object>  personColl,
                               HttpServletRequest request){

   PersonVO revision = obsForm.getPatient();
   PersonVO oldRevision= obsForm.getPatientRevision();
   NEDSSVOUtils voUtils = NEDSSVOUtils.getInstance();
   boolean isRevisionChanged = false;

   try {

     isRevisionChanged = voUtils.hasVOFieldChanged(revision, oldRevision);
     //logger.debug("isRevisionChanged  = " + isRevisionChanged);
   }
   catch (Exception e) {
     //logger.debug(" !!!!!!!!! NEDSSVOUtils error with Inv EditSubmit" + e.toString());
     e.printStackTrace();
   }
   if (isRevisionChanged) {

    PersonUtil.setPatientForEventEdit(revision, request);
    revision.setItDirty(true);
    revision.setItNew(false);
    revision.getThePersonDT().setItDirty(true);
    revision.getThePersonDT().setItNew(false);
    personColl.add(revision);


   }

 }

 /*
 *   checks each observation collection & ensures that for each, each
 *     obsvaluecodedDT has at least a default value.   This will not
 *     overwrite default values.
 */
private void setObsValueCodedToDefaultValues(Collection<ObservationVO>  observations)
{

   Iterator<ObservationVO>  collItor = observations.iterator();

    if (collItor == null)

        return;

    String _blank = "";

    while (collItor.hasNext())
    {

        ObservationVO obsVO = (ObservationVO)collItor.next();
        Collection<Object>  obsValDTs = obsVO.getTheObsValueCodedDTCollection();

        if (obsValDTs == null)

            continue;

       Iterator<Object>  dtItor = obsValDTs.iterator();

        while (dtItor.hasNext())
        {

            ObsValueCodedDT dt = (ObsValueCodedDT)dtItor.next();

            if ((dt.getCode() == null) || (dt.getCode().trim().length() == 0))
                dt.setCode("NI");
        }
    }
}


  private ActRelationshipDT genericActRelationShipEdit(String recordStatusCd,
      Long sourceActUid, String sourceClassCd, String statusCd,
      Long targetActUid, String targetClassCd, String typeCd, String typeDescTxt)
  {
    ActRelationshipDT actRelDT = new ActRelationshipDT();
    actRelDT.setRecordStatusCd(recordStatusCd);
    actRelDT.setSourceActUid(sourceActUid);
    actRelDT.setSourceClassCd(sourceClassCd);
    actRelDT.setStatusCd(statusCd);
    actRelDT.setTargetActUid(targetActUid);
    actRelDT.setTargetClassCd(targetClassCd);
    actRelDT.setTypeCd(typeCd);
    actRelDT.setTypeDescTxt(typeDescTxt);
    actRelDT.setItDirty(true);
//logger.debug("\n\n\n sourceActUid = "  + sourceActUid + " targetActUid== "  + targetActUid +  " typeDescTxt== "  + typeDescTxt);

    return actRelDT;
  }
  private ParticipationDT genericParticipationEdit(String actClassCd,
      Long actUid, Timestamp fromTime, String recordStatusCd, String statusCd,
      String subjectClassCd, Long subjectEntityUid, String typeCd, String typeDescTxt)
  {
    ParticipationDT participationDT = new ParticipationDT();
    participationDT.setActClassCd(actClassCd);
    participationDT.setActUid(actUid);
    participationDT.setFromTime(fromTime);
    participationDT.setRecordStatusCd(recordStatusCd);
    participationDT.setStatusCd(statusCd);
    participationDT.setSubjectClassCd(subjectClassCd);
    participationDT.setSubjectEntityUid(subjectEntityUid);
    participationDT.setTypeCd(typeCd);
    participationDT.setTypeDescTxt(typeDescTxt);
    participationDT.setItDirty(true);


    return participationDT;
  }


  private RoleDT genericRoleEdit(String cd,
      String cdDescTxt, String recordStatusCd, String scopingClassCd, Long entityUid,
      String scopingRoleCd, String statusCd, String subjectClassCd, Long subjectEntityUid)
  {
    RoleDT roleDT = new RoleDT();
    roleDT.setCd(cd);
    roleDT.setCdDescTxt(cdDescTxt);
    roleDT.setRecordStatusCd(recordStatusCd);
    roleDT.setScopingClassCd(scopingClassCd);
    roleDT.setScopingEntityUid(entityUid);
    roleDT.setScopingRoleCd(scopingRoleCd);
    roleDT.setStatusCd(statusCd);
    roleDT.setSubjectClassCd(subjectClassCd);
    roleDT.setSubjectEntityUid(subjectEntityUid);
    roleDT.setItDirty(true);


    return roleDT;
  }


 private static ObservationVO findObservationByCode(Collection<ObservationVO>  coll, String strCode)
 {
   if (coll == null)
     return null;

  Iterator<ObservationVO>  itor = coll.iterator();

   while (itor.hasNext()) {
     ObservationVO obsVO = (ObservationVO) itor.next();
     ObservationDT obsDT = obsVO.getTheObservationDT();

     if (obsDT == null)
       continue;

     if (obsDT.getCd() == null) {
       continue;
     }
     if (obsDT.getCd().trim().equalsIgnoreCase(strCode.trim()))
       return obsVO; // found it!
   }

   // didn't find one
   return null;
 }

 private ObservationVO findObservationLAB112(Collection<ObservationVO>  coll, Long oldObsUID)
  {
    if (coll == null)
      return null;

   Iterator<ObservationVO>  itor = coll.iterator();

    while (itor.hasNext()) {
      ObservationVO obsVO = (ObservationVO) itor.next();
      ObservationDT obsDT = obsVO.getTheObservationDT();

      if (obsDT == null)
        continue;


      if (obsDT.getObservationUid().equals(oldObsUID))
        return obsVO; // found it!
    }

    // didn't find one
    return null;
  }


 private ActRelationshipDT genericActRelationShipCreate(String recordStatusCd,
     Long sourceActUid, String sourceClassCd, String statusCd,
     Long targetActUid, String targetClassCd, String typeCd, String typeDescTxt)
 {
   ActRelationshipDT actRelDT = new ActRelationshipDT();
   actRelDT.setRecordStatusCd(recordStatusCd);
   actRelDT.setSourceActUid(sourceActUid);
   actRelDT.setSourceClassCd(sourceClassCd);
   actRelDT.setStatusCd(statusCd);
   actRelDT.setTargetActUid(targetActUid);
   actRelDT.setTargetClassCd(targetClassCd);
   actRelDT.setTypeCd(typeCd);
   actRelDT.setTypeDescTxt(typeDescTxt);
   actRelDT.setItNew(true);
//logger.debug("\n\n\n sourceActUid = "  + sourceActUid + " targetActUid== "  + targetActUid +  " typeDescTxt== "  + typeDescTxt);

   return actRelDT;
 }
 private RoleDT genericRoleCreate(String cd,
         String cdDescTxt, String recordStatusCd, String scopingClassCd, Long entityUid,
         String scopingRoleCd, String statusCd, String subjectClassCd, Long subjectEntityUid)
     {
       RoleDT roleDT = new RoleDT();
       roleDT.setCd(cd);
       roleDT.setCdDescTxt(cdDescTxt);
       roleDT.setRecordStatusCd(recordStatusCd);
       roleDT.setScopingClassCd(scopingClassCd);
       roleDT.setScopingEntityUid(entityUid);
       roleDT.setScopingRoleCd(scopingRoleCd);
       roleDT.setStatusCd(statusCd);
       roleDT.setSubjectClassCd(subjectClassCd);
       roleDT.setSubjectEntityUid(subjectEntityUid);
       roleDT.setItNew(true);


       return roleDT;
     }


     private ArrayList<Object> setNumericValue(Collection<Object>  numericColl,Collection<Object>  oldNumeric) {

       ArrayList<Object> aList = new ArrayList<Object> ();
       Integer tempInteger = null;
       Long uid=null;
       if(oldNumeric != null){
        Iterator<Object>  temp =  oldNumeric.iterator();
         while(temp.hasNext()){
           ObsValueNumericDT tempDT = (ObsValueNumericDT)temp.next();
          tempInteger=tempDT.getObsValueNumericSeq();
          uid=tempDT.getObservationUid();
         }
      }


       if (numericColl != null) {
        Iterator<Object>  obsValueNumeric = numericColl.iterator();
         while (obsValueNumeric.hasNext()) {
           ObsValueNumericDT obsValueDT = (ObsValueNumericDT)
               obsValueNumeric.next();
           if (obsValueDT.getNumericValue() != null)
             if (! (obsValueDT.getNumericValue().equals(""))) {
               ObservationUtil obsUtil = new ObservationUtil();
               obsValueDT = obsUtil.parseNumericResult(obsValueDT);
             }
           obsValueDT.setObsValueNumericSeq(tempInteger);
           obsValueDT.setObservationUid(uid);
           obsValueDT.setItDirty(true);
          obsValueDT.setItNew(false);
           aList.add(obsValueDT);
         }
       }
       return aList;
   }
   private String getSystemDescTxt(String systemValue, HttpSession session) throws
       NEDSSAppConcurrentDataException, java.rmi.RemoteException,
       javax.ejb.EJBException, Exception
   {

     MainSessionCommand msCommand = null;
     String systemDescTxt = null;
     //try {
     String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
     String sMethod = null;
     Long obsUid = null;
     MainSessionHolder holder = new MainSessionHolder();
     ArrayList<?> resultUIDArr = null;

     sMethod = "getLaboratorySystemDescTxt";
     Object[] oParams ={systemValue};
     msCommand = holder.getMainSessionCommand(session);
     resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

     for (int i = 0; i < resultUIDArr.size(); i++)
     {
       logger.info("Result " + i + " is: " + resultUIDArr.get(i));
     }

     if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
     {
      logger.debug("calling getLaboratorySystemDescTxt worked!!! getLaboratorySystemDescTxt = " +
                    resultUIDArr.get(0));


       systemDescTxt = (String) resultUIDArr.get(0);

       return systemDescTxt;
     }

     return null;
   } //sendProxyToEJB

   private String getDescTxt(String srt,String code){
    String descTxt=null;
    try{
    CachedDropDownValues cdv = new CachedDropDownValues();
    descTxt= cdv.getDescForCode(srt,code);
    }catch(Exception e){
      //logger.debug("Error found");
    }
    return descTxt;
   }

   private void setLAB112(Collection<ObservationVO>  obsCOll, ObservationVO newLAB112){
     ObservationVO obsVOLab112 = findObservationByCode(obsCOll, "LAB112");
     ObservationDT obsDT = obsVOLab112.getTheObservationDT();
     if (newLAB112.getTheObservationDT().getHiddenCd() != null && !newLAB112.getTheObservationDT().getHiddenCd().equals("")) {
       obsDT.setCd(newLAB112.getTheObservationDT().getHiddenCd());

     }

     /*
        Setting the code for "No Information Given" for Lab112, Added the Cd in the Property File
      */
    // if(newLAB112.getTheObservationDT().getCdDescTxt().equalsIgnoreCase(NEDSSConstants.NOINFORMATIONGIVEN) )
     //{
     //  PropertyUtil propertyUtil = new PropertyUtil(NEDSSConstants.PROPERTY_FILE);
     //  String lab112Cd =  propertyUtil.getLab112Cd();
     //  obsDT.setCd(lab112Cd);
     //}



     else if(newLAB112.getTheObservationDT().getTxt()!= null
        && !newLAB112.getTheObservationDT().getTxt().equalsIgnoreCase("")){
      obsDT.setCd(newLAB112.getTheObservationDT().getTxt());
      obsDT.setTxt("");
     }
     if(obsDT.getCd().equalsIgnoreCase("LAB112"))
     {
       PropertyUtil propertyUtil = PropertyUtil.getInstance();
       String lab112Cd =  propertyUtil.getLab112Cd();
       obsDT.setCd(lab112Cd);
       //obsDT.setCd(newLAB112.getTheObservationDT().getTxt());
      obsDT.setTxt("");
     }

}

private void editSusceptabilitiesVO(ResultedTestVO newVO, ObservationVO theSusceptibilityVO, int tempUID, Collection<Object>  actRelationColl, Collection<ObservationVO>  editObsColl, boolean indicator )   {
	Collection<Object>  observationVOColl = newVO.getTheSusceptibilityCollection();
if (observationVOColl != null) { // This is LAB110 observation
 Iterator<Object>  obsVOIt = observationVOColl.iterator();

  while (obsVOIt.hasNext()) {
    ObservationVO obsVO = (ObservationVO) obsVOIt.next();
    ObservationUtils.
        trimEmptyObsValueCodedDTCollections(obsVO);
    if (!obsVO.getTheObservationDT().getStatusCd().
        equalsIgnoreCase("I") && obsVO.getTheObservationDT().getObservationUid()==null) {
      ObservationUtils.
          trimEmptyObsValueCodedDTCollections(
          obsVO);
      ObservationDT obsSusDT = obsVO.
          getTheObservationDT();
      obsSusDT.setObservationUid(new Long(tempUID--));

      if(indicator){
      actRelationColl.add(genericActRelationShipCreate(
          "ACTIVE",
          obsSusDT.getObservationUid(), "OBS", "A",
          theSusceptibilityVO.getTheObservationDT().
          getObservationUid(), "OBS", "COMP",
          "Has Component"));
      }
      if (obsVO.getTheObsValueCodedDTCollection() != null) {
       Iterator<Object>  obsValueCode =
            obsVO.getTheObsValueCodedDTCollection().
            iterator();
        while (obsValueCode.hasNext()) {
          ObsValueCodedDT obsValueDT = (ObsValueCodedDT)
              obsValueCode.next();
          if (obsValueDT.getCode() == null ||
              obsValueDT.getCode().trim().length() == 0)
            obsValueDT.setCode("NI");
          obsValueDT.setItNew(true);
        }
      }
      if (obsVO.getTheObsValueTxtDTCollection() != null) {
       Iterator<Object>  obsValueText =
            obsVO.getTheObsValueTxtDTCollection().
            iterator();
        while (obsValueText.hasNext()) {
          ObsValueTxtDT obsValueDT = (ObsValueTxtDT)
              obsValueText.next();
          obsValueDT.setItNew(true);
        }
      }

      if (obsVO.getTheObsValueNumericDTCollection() != null) {
       Iterator<Object>  obsValueNumeric =
            obsVO.getTheObsValueNumericDTCollection().
            iterator();
        while (obsValueNumeric.hasNext()) {
          ObsValueNumericDT obsValueDT = (
              ObsValueNumericDT)
              obsValueNumeric.next();
          if (obsValueDT.getNumericValue() != null)
            if (! (obsValueDT.getNumericValue().equals(
                ""))) {
              ObservationUtil obsUtil = new
                  ObservationUtil();
              obsValueDT = obsUtil.parseNumericResult(
                  obsValueDT);
            }
          obsValueDT.setItNew(true);
        }
      }
      if (obsVO.getTheObservationInterpDTCollection() != null) {
       Iterator<Object>  interpretation = obsVO.
            getTheObservationInterpDTCollection().
            iterator();
        while (interpretation.hasNext()) {
          ObservationInterpDT observationInterpDT =
              (ObservationInterpDT) interpretation.next();
          observationInterpDT.setItNew(true);
        }
      }

      if (obsSusDT.getSearchResultRT() != null &&
          obsSusDT.getSearchResultRT().length() != 0) {

        obsSusDT.setCdDescTxt(obsSusDT.getSearchResultRT());
      }

      String cdSystemCdDrugRT = obsSusDT.
          getCdSystemCdRT();

      if (cdSystemCdDrugRT != null &&
          !cdSystemCdDrugRT.equals("")) {
        obsSusDT.setCdSystemCd(cdSystemCdDrugRT);
        if(cdSystemCdDrugRT.equalsIgnoreCase("LN"))
          obsSusDT.setCdSystemDescTxt("LOINC");
      }
      else {

      	/** The system should not use labID(Facility ID
      	 *  from Reporting Facility) to derive Drug Name.
      	 *  See the  NBS Laboratory Report Table Maintenance Guide.doc
      	 *  for detail information.It should always use 'DEFAULT'
      	 */
      	/*
        String labId = (String) request.getParameter(
            "labId");
        if (labId != null && !labId.equals("") &&
            !labId.equals("null")) {
          obsSusDT.setCdSystemCd(
              labId);
        }
        else {
          obsSusDT.setCdSystemCd("DEFAULT");
        }
        */
        obsSusDT.setCdSystemCd("DEFAULT");
        obsSusDT.setCdSystemDescTxt("NEDSS Base System");
      }
      if (obsSusDT.getHiddenCd() != null &&
          !obsSusDT.getHiddenCd().equals("")) {
        obsSusDT.setCd(obsSusDT.getHiddenCd());
      }
      else {
        obsSusDT.setCd(obsSusDT.getCdDescTxt());
        CachedDropDownValues cdv = new
            CachedDropDownValues();
        String cdSystemCd = null;
        String obsCd = null;
        String cdDescTxt = null;
        if (obsSusDT.getCdSystemCd() != null)
          cdSystemCd = obsSusDT.getCdSystemCd();

        if (obsSusDT.getCd() != null) // use getCd() instead of  getCdDescTxt
          obsCd = obsSusDT.getCd(); // Use getCd()    "                           "

        if (cdSystemCd != null && obsCd != null) {
          if (cdSystemCd.equals("LN"))
            cdDescTxt = cdv.getResultedTestDesc(obsCd);
          else if (!cdSystemCd.equals("LN"))
            cdDescTxt = cdv.getResultedTestDescLab(
                cdSystemCd, obsCd);
        }
        if (cdDescTxt != null)
          obsSusDT.setCdDescTxt(cdDescTxt);

      }

      obsSusDT.setCtrlCdDisplayForm(NEDSSConstants.
          LAB_REPORT);
      obsSusDT.setObsDomainCdSt1(NEDSSConstants.R_RESULT);
      obsSusDT.setStatusTime(new java.sql.Timestamp(new
          Date().
          getTime()));
      //obsSusDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      obsSusDT.setItNew(true);
      obsSusDT.setItDirty(false);
      obsVO.setTheObservationDT(obsSusDT);
      obsVO.setItNew(true);
      obsVO.setItDirty(false);
      editObsColl.add(obsVO);
      setObsValueCodedToDefaultValues(editObsColl);
      //VOTester.createReport(obsVO,"observationVO from backend");
    }
  }
} // end of if (observationVOColl != null)
}

	private void setInterpretationDescTxt(ObservationVO obsVO) {
		if (obsVO.getTheObservationInterpDTCollection() != null) {
			Iterator<Object> interpretation = obsVO
					.getTheObservationInterpDTCollection().iterator();
			while (interpretation.hasNext()) {
				ObservationInterpDT observationInterpDT = (ObservationInterpDT) interpretation
						.next();
				String temp = this.getDescTxt("OBS_INTRP", observationInterpDT
						.getInterpretationCd());
				if (temp != null) {
					observationInterpDT.setInterpretationDescTxt(temp);
				}
			}
		}
	}

    private int setThetrackingInformation(ObservationVO lab112VO, Collection<Object>  oldObsColl, ResultedTestVO resultedTestVO, int tempID, 
    		Collection<Object>  actRelationColl, Collection<ObservationVO>  obsCollection, HashMap<Object,Object> trackUIDHashMap ){
    		ObservationVO obs329VO =null;
           	ObservationDT obsDT329DT =null;
           	boolean remove329a = false;
           	HashMap<?,?> track329HashMap= null;
           	
           	if(resultedTestVO.getTheIsolateVO()!=null && resultedTestVO.getTheIsolateVO().getTheObservationDT()!=null 
           			&& trackUIDHashMap.get(resultedTestVO.getTheIsolateVO().getTheObservationDT().getObservationUid())!=null){
           		//There exisits a previous entry for all the observations hashMap
           		track329HashMap = (HashMap<?,?>)trackUIDHashMap.get(resultedTestVO.getTheIsolateVO().getTheObservationDT().getObservationUid());
           		
           	}
           	
           	Collection<ObservationVO>  editedObservations = new ArrayList<ObservationVO> ();
            Collection<Object>  observationVOColl = resultedTestVO.getTheTrackIsolateCollection();
            if (observationVOColl != null) {
            //	boolean breakOutOfLoop = false;
            	boolean deleteObs = false;
            	boolean deleteChildObs = false;
            	boolean newObs = false;
            	//boolean editObs = false;
            	ObservationVO oldObservationVO = null;
            	Iterator<Object> obsVOIt = observationVOColl.iterator();
                while (obsVOIt.hasNext()) {
               	//breakOutOfLoop = false;
                	deleteChildObs = false;
               	ObservationVO obsVO = (ObservationVO) obsVOIt.next();
               	oldObservationVO = null;
               	
               	if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase("")){
               		return tempID;
               	}
               	if((obsVO.getObsValueCodedDT_s(0)==null) || 
               			(obsVO.getObsValueCodedDT_s(0)!=null && 
               					((obsVO.getObsValueCodedDT_s(0).getCode()==null || obsVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("")))))
               	{
               		obsVO.setTheObsValueCodedDTCollection(null);
               	}
                if((obsVO.getObsValueTxtDT_s(0)==null 
                		|| (obsVO.getObsValueTxtDT_s(0)!=null &&
                				(obsVO.getObsValueTxtDT_s(0).getValueTxt()==null)||(obsVO.getObsValueTxtDT_s(0).getValueTxt()!=null)&& obsVO.getObsValueTxtDT_s(0).getValueTxt().equalsIgnoreCase("")))){
               		obsVO.setTheObsValueTxtDTCollection(null);
               	}
                if((obsVO.getObsValueDateDT_s(0)==null 
                		|| (obsVO.getObsValueDateDT_s(0)!=null && obsVO.getObsValueDateDT_s(0).getFromTime()==null)
                				||(obsVO.getObsValueDateDT_s(0).getFromTime()!=null && obsVO.getObsValueDateDT_s(0).getFromTime().getTime()<=0))){
                	obsVO.setTheObsValueDateDTCollection(null);
                }

               	//to test if old isolate info was empty and nothing more is added
               if(track329HashMap!=null && obsVO.getTheObservationDT().getCd().equalsIgnoreCase("NOLAB329ADDED") 
            		   && ((obsVO.getObsValueCodedDT_s(0).getCode()!=null && obsVO.getObsValueCodedDT_s(0).getCode().trim().equalsIgnoreCase("")) || obsVO.getObsValueCodedDT_s(0).getCode()==null)
            			   && track329HashMap.get(obsVO.getTheObservationDT().getCd())==null)
            	  return tempID;
               	
               	if(track329HashMap!=null && track329HashMap.get(obsVO.getTheObservationDT().getCd())!=null 
               			&& obsVO.getTheObservationDT().getObservationUid()!=null
               			){
               		oldObservationVO = (ObservationVO)track329HashMap.get(obsVO.getTheObservationDT().getCd());
               		if((obsVO.getTheObsValueCodedDTCollection()==null
               					&& obsVO.getTheObsValueDateDTCollection()==null
               					&& obsVO.getTheObsValueTxtDTCollection()==null)
               					&& !obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329)){
               			deleteChildObs = true; 
               		}
               		
               	}else if(track329HashMap!=null && track329HashMap.get(obsVO.getTheObservationDT().getCd())!=null 
               			//&& obsVO.getTheObservationDT().getObservationUid()!=null 
               			&& obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329A))
           	         {
               		oldObservationVO = (ObservationVO)track329HashMap.get(obsVO.getTheObservationDT().getCd());
               		
               	}else if(track329HashMap!=null && track329HashMap.get(obsVO.getTheObservationDT().getCd())!=null 
               			&& obsVO.getTheObservationDT().getObservationUid()!=null 
               			&& obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329))
           	         {
               		//delete condition
               		oldObservationVO = (ObservationVO)track329HashMap.get(obsVO.getTheObservationDT().getCd());
               		deleteObs = true;
               		
               	}else if((track329HashMap==null ||(track329HashMap!=null && track329HashMap.get(obsVO.getTheObservationDT().getCd())==null))
               			&& obsVO.getTheObsValueDateDTCollection()==null && obsVO.getTheObsValueTxtDTCollection()==null && obsVO.getTheObsValueCodedDTCollection()==null
               			&& (!obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329)) ){
               		//no value before no value after
               		//if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329))
               		//	return tempID; //return the method in case the lab329 is empty
               		//else
               			continue;//go to next row in case the lab is empty
               		
               	}else  {
               		oldObservationVO = null;
               		newObs = true;
                }
                
//                if((obsVO.getObsValueDateDT_s(0)==null || (obsVO.getObsValueDateDT_s(0)!=null &&obsVO.getObsValueDateDT_s(0).getFromTime()==null))){
//               		obsVO.setTheObsValueDateDTCollection(null);
 //               }


               	if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329)){
	            	obs329VO=obsVO;
	            	obsDT329DT = obs329VO.getTheObservationDT();
	            	
	            	if(newObs){
	            		obsDT329DT.setObservationUid(new Long(tempID--));
	            		obsDT329DT.setCd(NEDSSConstants.LAB_329);
		            	obsDT329DT.setCdSystemCd(NEDSSConstants.CD_SYSTEM_CD_NBS);
		            	obsDT329DT.setCdDescTxt(NEDSSConstants.LAB329CD_DESC_TXT);
		            	obsDT329DT.setCdSystemDescTxt(NEDSSConstants.NEDSS_BASE_SYSTEM);
		            	obsDT329DT.setEffectiveFromTime(lab112VO.getTheObservationDT().getEffectiveFromTime());
		            	obsDT329DT.setActivityToTime(lab112VO.getTheObservationDT().getActivityToTime());
		            	obsDT329DT.setStatusCd(NEDSSConstants.STATUS_CD_D);
		            	obsDT329DT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		            	obsDT329DT.setObsDomainCdSt1(NEDSSConstants.I_ORDER);
		            	obsDT329DT.setItNew(true);
	                    obs329VO.setItNew(true);
	                    obs329VO.setTheObservationDT(obsDT329DT);
	                    
	                    	//ACT115 between lab112 and 329A 
	                    	actRelationColl.add(genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, obsDT329DT.getObservationUid(),
	                    			NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
	                    			lab112VO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
	                    			NEDSSConstants.TYPE_CD_115, NEDSSConstants.TYPE_DESC_TXT_115));
	                    	//ACT109 between 329 and Organism 
	                    	actRelationColl.add(genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, obsDT329DT.getObservationUid(),
	                    			NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
	                    			resultedTestVO.getTheIsolateVO().getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
	                    			NEDSSConstants.ACT109_TYP_CD, NEDSSConstants.TYPE_DESC_TXT_109));
	                 }
	            	else{
	            		obs329VO = oldObservationVO;
	            		obsDT329DT.setItNew(false);
	            		obs329VO.setItNew(false);
	            		obsDT329DT.setItDirty(true);
	            		obs329VO.setItDirty(true);
	                }
	            	if(deleteObs)
	            	{
	            		 ActRelationshipDT actDT = new ActRelationshipDT();
	                   	 actDT.setTargetActUid(lab112VO.getTheObservationDT().getObservationUid());
	                   	 actDT.setSourceActUid(obsDT329DT.getObservationUid());
	                   	 actDT.setTypeCd(NEDSSConstants.TYPE_CD_115);
	                   	 actDT.setItDelete(true);
	                   	 actRelationColl.add(actDT);
	                   	 
	                   	 ActRelationshipDT actDT1 = new ActRelationshipDT();
	                   	 actDT1.setTargetActUid(resultedTestVO.getTheIsolateVO().getTheObservationDT().getObservationUid());
	                   	 actDT1.setSourceActUid(obsDT329DT.getObservationUid());
	                   	 actDT1.setTypeCd(NEDSSConstants.ACT109_TYP_CD);
	                   	 actDT1.setItDelete(true);
	                   	 actRelationColl.add(actDT1);
	                   	 obs329VO.setItDelete(true);
	                   	 obsDT329DT.setItDelete(true);
	            	}
	            	editedObservations.add(obs329VO);
	                	                
                
                }
            	else{
                  				  
                  if (obsVO.getTheObsValueCodedDTCollection() != null) {
                    	  if(oldObservationVO == null){
                        	 if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329A) 
                        			  ||obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_331) 
                        			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_336)
                        			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_337) 
                        			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_338)
                        			  ||obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_345)
                        			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_346) 
                    			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_358) 
                    			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_363))
                        		 
                                  		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_HL7);
                        	  
                        	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_332))
                        		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD);
                        	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_347) )
                        		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_LAB347);
                        	  
                        	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_353)  
                        			  ||obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_359))
                        		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_LAB359_LAB353);  
                        	  
                        	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_352)  
                        			  && (obsVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("Y") 
                        					  || obsVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("N") ))
                        		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_LAB352_YN);
                        	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_352)  
                        			  && !(obsVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("Y") 
                        					  || obsVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("N") ))
                        		  	obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_LAB352_ELSE);
                        	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_355) )
                        		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_LAB355);
                        	  
                        	  
                        	  obsVO.getObsValueCodedDT_s(0).setItNew(true);
                    		  
                          }else{
                        	//oldTest=true;
                        	obsVO.getObsValueCodedDT_s(0).setItNew(false);
                        	obsVO.getObsValueCodedDT_s(0).setItDirty(true);
                        	obsVO.getObsValueCodedDT_s(0).setObservationUid(oldObservationVO.getTheObservationDT().getObservationUid());
                          }
                    	  if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329A) 
                    			  && ((obsVO.getObsValueCodedDT_s(0).getCode()!=null 
                    			  && obsVO.getObsValueCodedDT_s(0).getCode().equals(""))|| (obsVO.getObsValueCodedDT_s(0).getCode()==null))){
                    		  remove329a= true;
                    	  }
                    	  CommonLabUtil labUtil = new CommonLabUtil();
                          labUtil.getDIsplayValueOfDropDownForIsolate(obsVO);
                  }
                  if (obsVO.getTheObsValueTxtDTCollection() != null) {
                    	  if( oldObservationVO==null){
                    		  obsVO.getObsValueTxtDT_s(0).setItNew(true);
                    	  	}else{
                    	  		//oldTest = true;
                    	  		obsVO.getObsValueTxtDT_s(0).setItNew(false);
                    	  		obsVO.getObsValueTxtDT_s(0).setItDirty(true);
                    	  		obsVO.getObsValueTxtDT_s(0).setObservationUid(oldObservationVO.getTheObservationDT().getObservationUid());
                    	  	}
                      	//}
                  }
                  if (obsVO.getTheObsValueDateDTCollection() != null) {
                        	if(oldObservationVO==null){
                        		obsVO.getObsValueDateDT_s(0).setItNew(true);
                      	  	}else{
                      	  		//oldTest = true;
                      	  	obsVO.getObsValueDateDT_s(0).setItNew(false);
                      	  obsVO.getObsValueDateDT_s(0).setItDirty(true);
                      	obsVO.getObsValueDateDT_s(0).setObservationUid(oldObservationVO.getTheObservationDT().getObservationUid());
                     	  }
                    	}
                  ObservationDT obsTrackDT = null;
                  if(oldObservationVO==null)	{
                	  obsTrackDT = obsVO.getTheObservationDT();
                      obsTrackDT.setObservationUid(new Long(tempID--));
                	  obsTrackDT.setCdSystemCd(NEDSSConstants.CD_SYSTEM_CD_NBS);
                      obsTrackDT.setCdSystemDescTxt(NEDSSConstants.NEDSS_BASE_SYSTEM);
                      obsTrackDT.setStatusCd(NEDSSConstants.STATUS_CD_D);
                      obsTrackDT.setObsDomainCdSt1(NEDSSConstants.I_RESULT);
                      obsTrackDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                   }
                  else{
                	  oldObservationVO.setTheObsValueCodedDTCollection(obsVO.getTheObsValueCodedDTCollection());
                	  oldObservationVO.setTheObsValueDateDTCollection(obsVO.getTheObsValueDateDTCollection());
                	  oldObservationVO.setTheObsValueTxtDTCollection(obsVO.getTheObsValueTxtDTCollection());
                	  obsTrackDT = oldObservationVO.getTheObservationDT();
                	  obsVO = oldObservationVO;
                	  obsVO.setTheObservationDT(obsTrackDT);
                  }
                  if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329)){
		            	obsDT329DT.setEffectiveFromTime(lab112VO.getTheObservationDT().getEffectiveFromTime());
		            	obsDT329DT.setActivityToTime(lab112VO.getTheObservationDT().getActivityToTime());
                  }
                 
  		    	//ACT110 between 329a and child observations
                  if(oldObservationVO==null){
				  actRelationColl.add(genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, obsTrackDT.getObservationUid(),
						NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
						obsDT329DT.getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
						NEDSSConstants.TYPE_CD_110, NEDSSConstants.TYPE_DESC_TXT_110));
                  }
                  if(deleteObs || deleteChildObs){
                	  ActRelationshipDT actDT = new ActRelationshipDT();
                	  actDT.setTargetActUid(obsDT329DT.getObservationUid());
                	  actDT.setSourceActUid(obsTrackDT.getObservationUid());
                	  actDT.setTypeCd(NEDSSConstants.TYPE_CD_110);
                	  actDT.setItDelete(true);
                	  actRelationColl.add(actDT);
            		  obsTrackDT.setItDelete(true);
            		  obsVO.setItDelete(true);
            	  }
                  obsTrackDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  if(oldObservationVO==null){
                	  obsTrackDT.setItNew(true);
                      obsVO.setItNew(true);
                  }else if(!deleteChildObs){
                	    obsTrackDT.setItNew(false);
                		  obsVO.setItNew(false);
                		  obsTrackDT.setItDirty(true);
                		  obsVO.setItDirty(true);
                  }
                  obsVO.setTheObservationDT(obsTrackDT);
                  editedObservations.add(obsVO);
                }
              } 
               
            }
            if(editedObservations!=null && editedObservations.size()>0)
            	obsCollection.addAll(editedObservations);
            return tempID;
        }
    private int setPatientStatusVO(ObservationVO lab112VO, ObservationVO patientStatusVO, ObservationVO oldPatientStatusVO,int tempID, 
    		Collection<Object>  actRelationColl, Collection<ObservationVO>  obsCollection){
    	try{
            
    			boolean oldPatStatusNull = false;
    			boolean newPatStatusNull = false;
    			
            	ObservationDT obsDT330ADT = patientStatusVO.getTheObservationDT();
            	if(patientStatusVO.getObsValueCodedDT_s(0)== null || 
            			( patientStatusVO.getObsValueCodedDT_s(0)!=null && 
            					(patientStatusVO.getObsValueCodedDT_s(0).getCode()==null || 
            							(patientStatusVO.getObsValueCodedDT_s(0).getCode()!=null 
            									&& patientStatusVO.getObsValueCodedDT_s(0).getCode().trim().equalsIgnoreCase("")))))
            		newPatStatusNull=true;
            	
            	if(	 (oldPatientStatusVO.getObsValueCodedDT_s(0)== null || 
                			( oldPatientStatusVO.getObsValueCodedDT_s(0)!=null && 
                					(oldPatientStatusVO.getObsValueCodedDT_s(0).getCode()==null || 
                							(oldPatientStatusVO.getObsValueCodedDT_s(0).getCode()!=null && oldPatientStatusVO.getObsValueCodedDT_s(0).getCode().trim().equalsIgnoreCase(""))))))
            		oldPatStatusNull= true;
            	
            	//ACT108 between lab112 and 108 
            	if (newPatStatusNull && oldPatStatusNull)
            		return tempID;
                else if (!newPatStatusNull && oldPatStatusNull) {
                	  				patientStatusVO.setItNew(true);
                	  				obsDT330ADT.setItNew(true);
                	  				patientStatusVO.getObsValueCodedDT_s(0).setItNew(true);
                            		obsDT330ADT.setObservationUid(new Long(tempID--));
                            		
                            		obsDT330ADT.setCdSystemCd(NEDSSConstants.CD_SYSTEM_CD_NBS);
                                	obsDT330ADT.setCdDescTxt(NEDSSConstants.LAB_330_CD_DESC_TXT);
                                	obsDT330ADT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                                	obsDT330ADT.setObsDomainCdSt1(NEDSSConstants.PATIENT_STATUS_OBS_DOMAIN_CD);
                                	
                	  				actRelationColl.add(genericActRelationShipCreate(
                                    NEDSSConstants.RECORD_STATUS_ACTIVE,
                                    obsDT330ADT.getObservationUid(),
                                    NEDSSConstants.CLASS_CD_OBS,
                                    NEDSSConstants.STATUS_ACTIVE,
                                    lab112VO.getTheObservationDT().getObservationUid(),
                                    NEDSSConstants.CLASS_CD_OBS,
                                    NEDSSConstants.ACT108_TYP_CD,
                                    NEDSSConstants.HAS_COMPONENT));

                        }
                  else if (newPatStatusNull && !oldPatStatusNull) {
                	  patientStatusVO= oldPatientStatusVO;
                	  patientStatusVO.setItDelete(true);
                	  patientStatusVO.getTheObservationDT().setItDelete(true);
              			//patientStatusVO.getObsValueCodedDT_s(0).setObservationUid(oldPatientStatusVO.getTheObservationDT().getObservationUid());
              		//	patientStatusVO.getTheObservationDT().setObservationUid(oldPatientStatusVO.getTheObservationDT().getObservationUid());
              			patientStatusVO.getObsValueCodedDT_s(0).setItDelete(true);
                		ActRelationshipDT actDT1 = new ActRelationshipDT();
                      	 actDT1.setTargetActUid(lab112VO.getTheObservationDT().getObservationUid());
                      	 actDT1.setSourceActUid(oldPatientStatusVO.getTheObservationDT().getObservationUid());
                      	 actDT1.setTypeCd(NEDSSConstants.ACT108_TYP_CD);
                      	 actDT1.setItDelete(true);
                      	 actRelationColl.add(actDT1);
                  	  }
                  else{
                	  if(patientStatusVO.getObsValueCodedDT_s(0).getCode()
                			  ==oldPatientStatusVO.getObsValueCodedDT_s(0).getCode())
                		  return tempID;
                	  else{
                		  patientStatusVO.getTheObservationDT().setObservationUid(oldPatientStatusVO.getTheObservationDT().getObservationUid());
                	  
                		  oldPatientStatusVO.setTheObsValueCodedDTCollection(patientStatusVO.getTheObsValueCodedDTCollection());
                		  patientStatusVO.getObsValueCodedDT_s(0).setObservationUid(oldPatientStatusVO.getTheObservationDT().getObservationUid());
                		  
                		  patientStatusVO= oldPatientStatusVO;
                		  patientStatusVO.setItDirty(true);
                		  patientStatusVO.setItNew(false);
                		  patientStatusVO.getTheObservationDT().setItDirty(true);
                		  patientStatusVO.getTheObservationDT().setItNew(false);
                		  patientStatusVO.getObsValueCodedDT_s(0).setItDirty(true);
                		  patientStatusVO.getObsValueCodedDT_s(0).setItNew(false);
                  	}
                  }
            	 CommonLabUtil labUtil = new CommonLabUtil();
                 labUtil.getDIsplayValueOfDropDownForIsolate(patientStatusVO);
                  obsCollection.add(patientStatusVO);
             
             } catch(Exception ex){
            logger.error("Error thrown within setPatientStatusVO:"+ex);	
        }
        return tempID;
    }
    

    
} //EditLabReportSubmit
