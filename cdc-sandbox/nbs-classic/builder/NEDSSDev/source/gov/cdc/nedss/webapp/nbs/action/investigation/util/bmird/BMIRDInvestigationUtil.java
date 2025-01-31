package gov.cdc.nedss.webapp.nbs.action.investigation.util.bmird;

import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.CommonInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.webapp.nbs.util.*;

import java.util.*;

import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.webapp.nbs.action.investigation.common.FormQACode;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;

/**
* Title:        BMIRDInvestigationUtil.java
* Description:  A BMIRD-specific utility class.
* Copyright:    Copyright (c) 2003
* Company: 	Computer Sciences Corporation
* @author       NEDSS Development Team
* @version      1.0.0
*/


public class BMIRDInvestigationUtil extends CommonInvestigationUtil
{

    private LogUtils logger = null;
    private static final int SINGLE_ORG_ID_RULE = 0;
    private static final int SINGLE_ABC_ORG_ID_RULE = 1;
    private static final int OTHER_ORG_ID_RULE = 2;
    private static final QuickEntryEventHelper helper = new QuickEntryEventHelper();
    /**
     *  Default constructor.
     */
    public BMIRDInvestigationUtil()
    {
        logger = new LogUtils(this.getClass().getName()); 
    }

    public void setAdditionalParticipationsForCreate(InvestigationForm investigationForm, HttpServletRequest request)
    {

        ParticipationDT participationDT = null;
        Collection<Object>  participationDTCollection  = investigationForm.getProxy().getTheParticipationDTCollection();
        Long publicHealthCaseUid = investigationForm.getProxy().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
        String labHospital = request.getParameter("labHospitalOrg.organizationUid");
        if((labHospital != null && !labHospital.trim().equals("")) && publicHealthCaseUid != null)
        {
            participationDT = super.createParticipation(publicHealthCaseUid, labHospital, "ORG", NEDSSConstants.HospOfCulture);
            participationDTCollection.add(participationDT);
        }
        String treatmentHospital = request.getParameter("treatmentHospitalOrg.organizationUid");
        if((treatmentHospital != null && !treatmentHospital.trim().equals("")) && publicHealthCaseUid != null)
        {
            participationDT = super.createParticipation(publicHealthCaseUid, treatmentHospital, "ORG", NEDSSConstants.TreatmentHosp);
            participationDTCollection.add(participationDT);
        }
        String patientTransferHospitalOrg = request.getParameter("patientTransferHospitalOrg.organizationUid");
        if((patientTransferHospitalOrg != null && !patientTransferHospitalOrg.trim().equals("")) && publicHealthCaseUid != null)
        {
            participationDT = this.createParticipation(publicHealthCaseUid, patientTransferHospitalOrg, "ORG", NEDSSConstants.TransferHosp);
            participationDTCollection.add(participationDT);
        }
        String daycareFacilityOrg = request.getParameter("daycareFacilityOrg.organizationUid");
        if((daycareFacilityOrg != null && !daycareFacilityOrg.trim().equals("")) && publicHealthCaseUid != null)
        {
            participationDT = this.createParticipation(publicHealthCaseUid, daycareFacilityOrg, "ORG", NEDSSConstants.DaycareFac);
            participationDTCollection.add(participationDT);
        }
        String chronicCareFacility = request.getParameter("chronicCareFacility.organizationUid");
        if((chronicCareFacility != null && !chronicCareFacility.trim().equals("")) && publicHealthCaseUid != null)
        {
            participationDT = this.createParticipation(publicHealthCaseUid, chronicCareFacility, "ORG", NEDSSConstants.ChronicCareFac);
            participationDTCollection.add(participationDT);
        }
        String birthHospital = request.getParameter("birthHospital.organizationUid");
        if((birthHospital != null && !birthHospital.trim().equals("")) && publicHealthCaseUid != null)
        {
            participationDT = this.createParticipation(publicHealthCaseUid, birthHospital, "ORG", NEDSSConstants.HospOfBirth);
            participationDTCollection.add(participationDT);
        }
        String admittedHospital = request.getParameter("admittedHospital.organizationUid");
        if((admittedHospital != null && !admittedHospital.trim().equals("")) && publicHealthCaseUid != null)
        {
            participationDT = this.createParticipation(publicHealthCaseUid, admittedHospital, "ORG", NEDSSConstants.ReAdmHosp);
            participationDTCollection.add(participationDT);
        }
        String maternal = request.getParameter("maternal.personUid");
        if((maternal != null && !maternal.trim().equals("")) && publicHealthCaseUid != null)
        {
            participationDT = this.createParticipation(publicHealthCaseUid, maternal, "PSN", NEDSSConstants.MaternalPSN);
            participationDTCollection.add(participationDT);
        }
        String collegeOrg = request.getParameter("collegeOrg.organizationUid");
        if((collegeOrg != null && !collegeOrg.trim().equals("")) && publicHealthCaseUid != null)
        {
            participationDT = this.createParticipation(publicHealthCaseUid, collegeOrg, "ORG", NEDSSConstants.CollegeUniversity);
            participationDTCollection.add(participationDT);
        }

    }

    public int setBatchEntryObseravationsForCreate(InvestigationForm investigationForm, HttpServletRequest request, int tempID, String investigationFormCd)
    {

        Collection<Object>  itemActRelationships = null;
        ActRelationshipDT actRelationshipDT = null;
        Collection<ObservationVO>  observationVOCollection  = investigationForm.getProxy().getTheObservationVOCollection();
        Collection<Object>  actRelationshipDTCollection  = investigationForm.getProxy().getTheActRelationshipDTCollection();
        ObservationVO formObservationVO = this.getObservationVO(investigationForm.getProxy(), "INV_FORM_BMD");
        logger.info(" .main ObservationVOCollection  size: " + ((observationVOCollection  == null) ? "0" : String.valueOf(observationVOCollection.size())) + " .main actRelationshipDTCollection  size: " + ((actRelationshipDTCollection  == null) ? "0" : String.valueOf(actRelationshipDTCollection.size())));
        Collection<Object>  antiBatchEntryCollection  = investigationForm.getAntibioticBatchEntryCollection();
        logger.info(" ..AntibioticBatchEntryCollection  size: " + ((antiBatchEntryCollection  == null) ? "0" : String.valueOf(antiBatchEntryCollection.size())));
        if(antiBatchEntryCollection  != null && (investigationFormCd.equals(NBSConstantUtil.INV_FORM_BMDSP) || investigationFormCd.equals(NBSConstantUtil.INV_FORM_BMDGBS)))
        {
           Iterator<Object>  anIterator = antiBatchEntryCollection.iterator();
            while(anIterator.hasNext())
            {
                ObservationVO rowObservationVO = null; 
                BatchEntryHelper aBatch = (BatchEntryHelper)anIterator.next();
                TreeMap<Object,Object> treeMap = super.setBatchObservations(aBatch, formObservationVO,NEDSSConstants.ANTIBIOTIC_CONTAIN_ROW, request, tempID);
                observationVOCollection.addAll((ArrayList<ObservationVO> )(treeMap.get("observations")));
                actRelationshipDTCollection.addAll((ArrayList<?> )(treeMap.get("actrelations")));
                tempID = ((Integer)treeMap.get("tempID")).intValue();

            }
        }
        logger.info(" .main ObservationVOCollection  after adding Antibiotic batchEntry size: " + ((observationVOCollection  == null) ? "0" : String.valueOf(observationVOCollection.size())) + " after adding item actRelationshipDTCollection  size: " + ((actRelationshipDTCollection  == null) ? "0" : String.valueOf(actRelationshipDTCollection.size())));
       Iterator<Object>  actIterator = actRelationshipDTCollection.iterator();
        ActRelationshipDT itorActRelationshipDT = null;
        while(actIterator.hasNext())
        {
            itorActRelationshipDT = (ActRelationshipDT)actIterator.next();
            logger.debug(" actRelationship: t s td: " + itorActRelationshipDT.getTargetActUid() + " " + itorActRelationshipDT.getSourceActUid() + " " + itorActRelationshipDT.getTypeCd());
        }

        return tempID;
    }

    private void setObservationCodesForCreate(Collection<Object>  observationVOCollection) {
      logger.debug("set ObservationConstants");
      if (observationVOCollection  != null) {
       Iterator<Object>  anIterator = null;
        for (anIterator = observationVOCollection.iterator();
             anIterator.hasNext(); ) {
          ObservationVO observationVO = (ObservationVO) anIterator.next();
          if (observationVO != null)
            FormQACode.putQuestionCode(observationVO.getTheObservationDT());

        }

      }
   }


    public int setBatchEntryObseravationsForEdit(InvestigationForm investigationForm, ObservationVO formObservationVO, String investigationFormCd, HttpServletRequest request, int tempID)
    {

        Collection<Object>  itemActRelationships = null;
        Collection<Object>  deleteARCollection  = null;
        ActRelationshipDT actRelationshipDT = null;
        Collection<ObservationVO>  observationVOCollection  = investigationForm.getProxy().getTheObservationVOCollection();
        Collection<Object>  actRelationshipDTCollection  = investigationForm.getProxy().getTheActRelationshipDTCollection();
        if(actRelationshipDTCollection  == null)
        {
            actRelationshipDTCollection  = new ArrayList<Object> ();
        }
        //ObservationVO formObservationVO = this.getObservationVO(investigationForm.getProxy(), "INV_FORM_BMD");
        logger.info(" .main edit ObservationVOCollection  size: " + ((observationVOCollection  == null) ? "0" : String.valueOf(observationVOCollection.size())) + " .main actRelationshipDTCollection  size: " + ((actRelationshipDTCollection  == null) ? "0" : String.valueOf(actRelationshipDTCollection.size())));
        Collection<Object>  antiBatchEntryCollection  = investigationForm.getAntibioticBatchEntryCollection();
        logger.info(" edit ..AntibioticBatchEntryCollection  size: " + ((antiBatchEntryCollection  == null) ? "0" : String.valueOf(antiBatchEntryCollection.size())));
        if(antiBatchEntryCollection  != null)
        {
            TreeMap<Object,Object> treeMap = null;
           Iterator<Object>  anIterator = antiBatchEntryCollection.iterator();
            while(anIterator.hasNext())
            {
              BatchEntryHelper aBatch = (BatchEntryHelper)anIterator.next();
              treeMap = super.setBatchObservations(aBatch, formObservationVO,NEDSSConstants.ANTIBIOTIC_CONTAIN_ROW, request, tempID);
              observationVOCollection.addAll((ArrayList<ObservationVO> )(treeMap.get("observations")));
              actRelationshipDTCollection.addAll((ArrayList<?> )(treeMap.get("actrelations")));
              tempID = ((Integer)treeMap.get("tempID")).intValue();
            }
        }
        logger.info(" .main ObservationVOCollection  after adding Antibiotic batchEntry size: " + ((observationVOCollection  == null) ? "0" : String.valueOf(observationVOCollection.size())) + " after adding item actRelationshipDTCollection  size: " + ((actRelationshipDTCollection  == null) ? "0" : String.valueOf(actRelationshipDTCollection.size())));
        if(actRelationshipDTCollection.size() > 0)
        {
            investigationForm.getProxy().setTheActRelationshipDTCollection(actRelationshipDTCollection);
        }
       Iterator<Object>  actIterator = actRelationshipDTCollection.iterator();
        ActRelationshipDT itorActRelationshipDT = null;
        while(actIterator.hasNext())
        {
            itorActRelationshipDT = (ActRelationshipDT)actIterator.next();
            logger.debug(" actRelationship: t s td: " + itorActRelationshipDT.getTargetActUid() + " " + itorActRelationshipDT.getSourceActUid() + " " + itorActRelationshipDT.getTypeCd());
        }

        return tempID;
    }

    public void setBatchEntryToFormForView(InvestigationForm investigationForm)
    {

        InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();
        logger.debug("inside setBatchEntryToForm");
        Collection<Object>  antiBatchEntryCollection  = new ArrayList<Object> ();
        Collection<Object>  arCollectionForAntibiotic = null;
        ObservationVO obsVO = null;
        ActRelationshipDT actRelationshipDT = null;
        Collection<ObservationVO>  observationVOCollection  = investigationProxyVO.getTheObservationVOCollection();
       Iterator<ObservationVO>  anIterator = observationVOCollection.iterator();
        while(anIterator.hasNext())
        {
            obsVO = (ObservationVO)anIterator.next();
            if(obsVO.getTheObservationDT().getCtrlCdDisplayForm() != null && obsVO.getTheObservationDT().getCtrlCdDisplayForm().trim().equals(NEDSSConstants.ANTIBIOTIC_CONTAIN_ROW.trim()))
            {
                TreeMap<Object,Object> obsTreeMap = new TreeMap<Object,Object>();
                arCollectionForAntibiotic = obsVO.getTheActRelationshipDTCollection();
                obsTreeMap.put(obsVO.getTheObservationDT().getCtrlCdDisplayForm(), obsVO);
               Iterator<Object>  arItor = arCollectionForAntibiotic.iterator();
                while(arItor.hasNext())
                {
                    actRelationshipDT = (ActRelationshipDT)arItor.next();
                    ObservationVO itemObs = this.getObservationByUid(observationVOCollection, actRelationshipDT.getSourceActUid());
                    logger.debug("....antibiotic itemObs : " + (itemObs == null ? "" : (itemObs.getTheObservationDT().getCd() + " uid: " + itemObs.getTheObservationDT().getUid())));
                    obsTreeMap.put(itemObs.getTheObservationDT().getCd(), itemObs);
                }
                BatchEntryHelper antiBatchEntryHelper = new BatchEntryHelper();
                antiBatchEntryHelper.setObsTreeMap(obsTreeMap);
                antiBatchEntryHelper.setUid(obsVO.getTheObservationDT().getObservationUid());
                antiBatchEntryHelper.setTreeMap(super.mapBatchEntryQAValue(obsTreeMap.values()));
                antiBatchEntryHelper.setStatusCd("A");
                antiBatchEntryCollection.add(antiBatchEntryHelper);
            }
        }
        if(antiBatchEntryCollection.size() > 0)
        {
            investigationForm.setAntibioticBatchEntryCollection(antiBatchEntryCollection);
        }
        else
        {
            logger.debug("no antibiotics found for this investigation");
        }

    }

    public boolean setBmirdParticipationsForEdit(ActionForm form, HttpServletRequest request, boolean investigationProxyIsDirty)
    {

        InvestigationForm investigationForm = (InvestigationForm)form;
        InvestigationProxyVO oldProxy = investigationForm.getOldProxy();
        InvestigationProxyVO updatedProxyVO = investigationForm.getProxy();
        Collection<Object>  oldParticipationCollection  = oldProxy.getPublicHealthCaseVO().getTheParticipationDTCollection();
        Long publicHealthCaseUID = updatedProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
        String sPublicHealthCaseUID = publicHealthCaseUID.toString();
        Collection<Object>  newParticipationDTCollection  = investigationForm.getProxy().getTheParticipationDTCollection();
        if(newParticipationDTCollection  == null)
        {
            newParticipationDTCollection  = new ArrayList<Object> ();
        }
        {
            String newlabHospitalOrgUid = request.getParameter("labHospitalOrg.organizationUid");
            logger.debug(" newlabHospitalOrgUid = " + newlabHospitalOrgUid);
            investigationProxyIsDirty = this.createOrDeleteParticipation(newlabHospitalOrgUid, publicHealthCaseUID, newParticipationDTCollection, oldParticipationCollection, NEDSSConstants.HospOfCulture, "ORG", investigationProxyIsDirty);
        }
        {
            String newtreatmentHospitalOrgUid = request.getParameter("treatmentHospitalOrg.organizationUid");
            logger.debug(" newlabHospitalOrgUid = " + newtreatmentHospitalOrgUid);
            investigationProxyIsDirty = this.createOrDeleteParticipation(newtreatmentHospitalOrgUid, publicHealthCaseUID, newParticipationDTCollection, oldParticipationCollection, NEDSSConstants.TreatmentHosp, "ORG", investigationProxyIsDirty);
        }
        {
            String patientTransferHospitalOrg = request.getParameter("patientTransferHospitalOrg.organizationUid");
            logger.debug(" patientTransferHospitalOrg = " + patientTransferHospitalOrg);
            investigationProxyIsDirty = this.createOrDeleteParticipation(patientTransferHospitalOrg, publicHealthCaseUID, newParticipationDTCollection, oldParticipationCollection, NEDSSConstants.TransferHosp, "ORG", investigationProxyIsDirty);
        }
        {
            String daycareFacilityOrg = request.getParameter("daycareFacilityOrg.organizationUid");
            logger.debug(" daycareFacilityOrg = " + daycareFacilityOrg);
            investigationProxyIsDirty = this.createOrDeleteParticipation(daycareFacilityOrg, publicHealthCaseUID, newParticipationDTCollection, oldParticipationCollection, NEDSSConstants.DaycareFac, "ORG", investigationProxyIsDirty);
        }
        {
            String chronicCareFacility = request.getParameter("chronicCareFacility.organizationUid");
            logger.debug(" chronicCareFacility = " + chronicCareFacility);
            investigationProxyIsDirty = this.createOrDeleteParticipation(chronicCareFacility, publicHealthCaseUID, newParticipationDTCollection, oldParticipationCollection, NEDSSConstants.ChronicCareFac, "ORG", investigationProxyIsDirty);
        }
        {
            String birthHospital = request.getParameter("birthHospital.organizationUid");
            logger.debug(" birthHospital = " + birthHospital);
            investigationProxyIsDirty = this.createOrDeleteParticipation(birthHospital, publicHealthCaseUID, newParticipationDTCollection, oldParticipationCollection, NEDSSConstants.HospOfBirth, "ORG", investigationProxyIsDirty);
        }
        {
            String admittedHospital = request.getParameter("admittedHospital.organizationUid");
            logger.debug(" admittedHospital = " + admittedHospital);
            investigationProxyIsDirty = this.createOrDeleteParticipation(admittedHospital, publicHealthCaseUID, newParticipationDTCollection, oldParticipationCollection, NEDSSConstants.ReAdmHosp, "ORG", investigationProxyIsDirty);
        }
        {
            String maternal = request.getParameter("maternal.personUid");
            logger.debug(" maternal = " + maternal);
            investigationProxyIsDirty = this.createOrDeleteParticipation(maternal, publicHealthCaseUID, newParticipationDTCollection, oldParticipationCollection, NEDSSConstants.MaternalPSN, "PSN", investigationProxyIsDirty);
        }
        {
            String collegeOrg = request.getParameter("collegeOrg.organizationUid");
            logger.debug(" collegeOrg = " + collegeOrg);
            investigationProxyIsDirty = this.createOrDeleteParticipation(collegeOrg, publicHealthCaseUID, newParticipationDTCollection, oldParticipationCollection, NEDSSConstants.CollegeUniversity, "ORG", investigationProxyIsDirty);
        }
        // set the participation back to investigationProxyVO
        logger.debug(" newParticipationDTCollection.size() = " + newParticipationDTCollection.size());
        if(newParticipationDTCollection.size() > 0)
        {
            updatedProxyVO.setTheParticipationDTCollection(newParticipationDTCollection);
        }
        else
        {
            updatedProxyVO.setTheParticipationDTCollection(null);
        }
        //for partciipation to be deleted set phcVO to dirty

        return investigationProxyIsDirty;
    }

    public void setGenericRequestForView( InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
    {

        //InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();
        this.convertPublicHealthCaseToRequest(investigationProxyVO, request);
        this.convertPersonsToRequestForBmird(investigationProxyVO, request);
        this.convertOrganizationToRequestBmird(investigationProxyVO, request);
        this.convertObservationsToRequest(investigationProxyVO, request);
        this.convertNotificationSummaryToRequest(investigationProxyVO, request);
        this.convertObservationSummaryToRequest(investigationProxyVO, request);
        this.convertVaccinationSummaryToRequest(investigationProxyVO, request);
        this.convertTreatmentSummaryToRequest(investigationProxyVO, request);
        this.convertDocumentSummaryToRequest(investigationProxyVO, request);
    }

    public void convertProxyToRequestForDiagnosis( InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
    {
        this.convertPublicHealthCaseToRequest(investigationProxyVO, request);
        this.convertObservationsToRequest(investigationProxyVO, request);
        String dateAssignedToInvestigation =  request.getParameter("dateAssignedToInvestigation");
        request.setAttribute("dateAssignedToInvestigation", dateAssignedToInvestigation);

    }

    public void setMultipleSelects(HttpServletRequest request, InvestigationProxyVO investigationProxyVO)
    {

        Collection<ObservationVO>  obsColl = investigationProxyVO.getTheObservationVOCollection();
        String[] arrayBMD308 = request.getParameterValues("BMD308");
        this.setMultipleToObservation(arrayBMD308, "BMD308", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD312 = request.getParameterValues("BMD312");
        this.setMultipleToObservation(arrayBMD312, "BMD312", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD118 = request.getParameterValues("BMD118");
        this.setMultipleToObservation(arrayBMD118, "BMD118", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD122 = request.getParameterValues("BMD122");
        this.setMultipleToObservation(arrayBMD122, "BMD122", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD125 = request.getParameterValues("BMD125");
        this.setMultipleToObservation(arrayBMD125, "BMD125", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD127 = request.getParameterValues("BMD127");
        this.setMultipleToObservation(arrayBMD127, "BMD127", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD149 = request.getParameterValues("BMD149");
        this.setMultipleToObservation(arrayBMD149, "BMD149", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD248 = request.getParameterValues("BMD248");
        this.setMultipleToObservation(arrayBMD248, "BMD248", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD251 = request.getParameterValues("BMD251");
        this.setMultipleToObservation(arrayBMD251, "BMD251", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD255 = request.getParameterValues("BMD255");
        this.setMultipleToObservation(arrayBMD255, "BMD255", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD177 = request.getParameterValues("BMD177");
        this.setMultipleToObservation(arrayBMD177, "BMD177", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD161 = request.getParameterValues("BMD161");
        this.setMultipleToObservation(arrayBMD161, "BMD161", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD142 = request.getParameterValues("BMD142");
        this.setMultipleToObservation(arrayBMD142, "BMD142", investigationProxyVO.getTheObservationVOCollection(),true);
        String[] arrayBMD144 = request.getParameterValues("BMD144");
        this.setMultipleToObservation(arrayBMD144, "BMD144", investigationProxyVO.getTheObservationVOCollection(),true);
        String caseClassCd = request.getParameter("caseClassCd");
        String caseClassCd1 = request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.caseClassCd");
        if(caseClassCd1 == null)
        {
            investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setCaseClassCd(caseClassCd);
        }
        this.setConfirmationMethods(request, investigationProxyVO.getPublicHealthCaseVO());

        //ABCs baterial species, since version 1.1.3
        String[] arrayBMD292 = request.getParameterValues("BMD292");
        this.setMultipleToObservation(arrayBMD292, "BMD292", investigationProxyVO.getTheObservationVOCollection(),true);

        //Internal body site, since version 1.1.3
        String[] arrayBMD295 = request.getParameterValues("BMD295");
        this.setMultipleToObservation(arrayBMD295, "BMD295", investigationProxyVO.getTheObservationVOCollection(),true);


    }

   public void setCaseClassCd(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
   {
     String caseClassCd = request.getParameter("caseClassCd");
     String caseClassCd1 = request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.caseClassCd");
     if(caseClassCd1 == null)
     {
         investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setCaseClassCd(caseClassCd);
     }

   }


    public void setMultipleSelectsForEdit(HttpServletRequest request, InvestigationProxyVO investigationProxyVO, TreeMap<Object,Object> obsMap)
    {

        Collection<ObservationVO>  obsColl = investigationProxyVO.getTheObservationVOCollection();
        String[] arrayBMD308 = request.getParameterValues("BMD308");
        super.setMultipleToObservationForEdit(arrayBMD308, "BMD308", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD312 = request.getParameterValues("BMD312");
        super.setMultipleToObservationForEdit(arrayBMD312, "BMD312", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD118 = request.getParameterValues("BMD118");
        super.setMultipleToObservationForEdit(arrayBMD118, "BMD118", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD122 = request.getParameterValues("BMD122");
        super.setMultipleToObservationForEdit(arrayBMD122, "BMD122", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD125 = request.getParameterValues("BMD125");
        super.setMultipleToObservationForEdit(arrayBMD125, "BMD125", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD127 = request.getParameterValues("BMD127");
        super.setMultipleToObservationForEdit(arrayBMD127, "BMD127", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD149 = request.getParameterValues("BMD149");
        super.setMultipleToObservationForEdit(arrayBMD149, "BMD149", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD248 = request.getParameterValues("BMD248");
        super.setMultipleToObservationForEdit(arrayBMD248, "BMD248", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD251 = request.getParameterValues("BMD251");
        super.setMultipleToObservationForEdit(arrayBMD251, "BMD251", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD255 = request.getParameterValues("BMD255");
        super.setMultipleToObservationForEdit(arrayBMD255, "BMD255", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD177 = request.getParameterValues("BMD177");
        super.setMultipleToObservationForEdit(arrayBMD177, "BMD177", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD161 = request.getParameterValues("BMD161");
        super.setMultipleToObservationForEdit(arrayBMD161, "BMD161", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD142 = request.getParameterValues("BMD142");
        super.setMultipleToObservationForEdit(arrayBMD142, "BMD142", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String[] arrayBMD144 = request.getParameterValues("BMD144");
        super.setMultipleToObservationForEdit(arrayBMD144, "BMD144", investigationProxyVO.getTheObservationVOCollection(), obsMap);
        String caseClassCd = request.getParameter("caseClassCd");
        String caseClassCd1 = request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.caseClassCd");
        if(caseClassCd1 == null)
        {
            investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setCaseClassCd(caseClassCd);
        }
        this.setConfirmationMethods(request, investigationProxyVO.getPublicHealthCaseVO());

        //ABCs baterial species, since version 1.1.3
        String[] arrayBMD292 = request.getParameterValues("BMD292");
        super.setMultipleToObservationForEdit(arrayBMD292, "BMD292", investigationProxyVO.getTheObservationVOCollection(),obsMap);

        //Internal body site, since version 1.1.3
        String[] arrayBMD295 = request.getParameterValues("BMD295");
        super.setMultipleToObservationForEdit(arrayBMD295, "BMD295", investigationProxyVO.getTheObservationVOCollection(),obsMap);

    }

    public void setObservationConstantsForCreate(InvestigationProxyVO investigationProxyVO)
    {

        //set some values to observations
        Collection<ObservationVO>  observationVOCollection  = investigationProxyVO.getTheObservationVOCollection();
        logger.debug("convertProxyToRequestObj() before observationVOCollection  ");
        if(observationVOCollection  != null)
        {
           Iterator<ObservationVO>  anIterator1 = null;
           Iterator<Object>  anIterator2 = null;
           Iterator<Object>  anIterator3 = null;
            logger.debug("convertProxyToRequestObj() observationVOCollection  size: " + observationVOCollection.size());
            for(anIterator1 = observationVOCollection.iterator(); anIterator1.hasNext();)
            {
                ObservationVO observationVO = (ObservationVO)anIterator1.next();
                String obsCode = observationVO.getTheObservationDT().getCd();
                if(obsCode != null && obsCode.equals("BMD004"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD005"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD008"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD010"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD012"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD009"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD013"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD014"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD015"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD016"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD017"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD018"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD011"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD021"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD027"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("LAB");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD028"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("LAB");
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD030"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("LAB");
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD031"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("LAB");
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD032"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("LAB");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD033"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("LAB");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD036"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("LAB");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD038"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("LAB");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD072"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("EPI");
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD039"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("VAC");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD042"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("VAC");
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD043"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("VAC");
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD044"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("VAC");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD045"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("VAC");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD057"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("EPI");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD058"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("EPI");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD060"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("EPI");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("INV153"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("EPI");
                    continue;
                }
                if(obsCode != null && obsCode.equals("INV154"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("EPI");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("FIPS");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("INV155"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("EPI");
                    continue;
                }
                if(obsCode != null && obsCode.equals("INV155"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("EPI");
                    continue;
                }
                if(obsCode != null && obsCode.equals("INV156"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("EPI");
                    continue;
                }
                if(obsCode != null && obsCode.equals("BMD059"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("EPI");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                    continue;
                }
                if(obsCode != null && obsCode.equals("INV128"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueDateDTCollection  = observationVO.getTheObsValueDateDTCollection();
                    if(obsValueDateDTCollection  != null)
                    {
                        anIterator3 = obsValueDateDTCollection.iterator();
                        if(anIterator3.hasNext())
                        {
                            ObsValueDateDT obsValueDateDT = (ObsValueDateDT)anIterator3.next();
                            obsValueDateDT.setDurationUnitCd("D");
                        }
                    }
                }
                if(obsCode != null && obsCode.equals("BMD001"))
                {
                    observationVO.getTheObservationDT().setCdSystemCd("NBS");
                    observationVO.getTheObservationDT().setObsDomainCd("CLN");
                    Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                    if(obsValueCodedDTCollection  != null)
                    {
                        anIterator2 = obsValueCodedDTCollection.iterator();
                        if(anIterator2.hasNext())
                        {
                            ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)anIterator2.next();
                            obsValueCodedDT.setCodeSystemCd("NBS");
                            obsValueCodedDT.setCodeVersion("1");
                        }
                    }
                }
            }
        }

    }  //setObsevationCodesForCreate

    public int setSuplimentalObseravationsAndARForEdit(InvestigationForm investigationForm, ObservationVO formObservationVO, String investigationFormCd, HttpServletRequest request, int tempID)
    {

        Collection<ObservationVO>  supplimentObservations = investigationForm.getSupplementalCollection();
        logger.debug("getSupplementalCollection" + supplimentObservations);
        Collection<ObservationVO>  theObservationVOCollection  = investigationForm.getProxy().getTheObservationVOCollection();
        Collection<Object>  actRelationshipCollection   = investigationForm.getProxy().getTheActRelationshipDTCollection();
        if(actRelationshipCollection   == null) actRelationshipCollection   = new ArrayList<Object> ();
        //ObservationVO formObservationVO = this.getObservationVO(investigationForm.getProxy(), "INV_FORM_BMD");
        if(formObservationVO != null)
        {
            formObservationVO.getTheObservationDT().setCd(investigationFormCd);
            formObservationVO.getTheObservationDT().setItDirty(true);
            formObservationVO.getTheObservationDT().setItNew(false);
            formObservationVO.setItDirty(true);
            formObservationVO.setItNew(false);
        }
        if(supplimentObservations != null && supplimentObservations.size() > 0 && theObservationVOCollection  != null)
        {
            TreeMap<Object,Object> obsActTreeMap = super.setObservations(supplimentObservations, investigationForm.getObservationMap(), "T",formObservationVO.getTheActRelationshipDTCollection(), formObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.INV_FRM_Q, request, tempID);
            theObservationVOCollection.addAll((ArrayList<ObservationVO> )obsActTreeMap.get("observations"));
            actRelationshipCollection.addAll((ArrayList<?> )obsActTreeMap.get("actrelations"));
            tempID = ((Integer)obsActTreeMap.get("tempID")).intValue();
            if(actRelationshipCollection.size() > 0)
            {
                logger.info("setTheActRelationshipDTCollection  supplimental obseravtions for Edit");
                investigationForm.getProxy().setTheActRelationshipDTCollection(actRelationshipCollection);
            }
        }
        else
        {
            logger.info("no supplimentObservations for this investigation: " + investigationFormCd);
        }

        return tempID;
    }

    public void setSuplimentalObseravationsToFormOnLoad(InvestigationForm investigationForm, HttpServletRequest request)
    {

        logger.debug("in setSuplimentalObseravationsToFormOnLoad");
        //hasPathogen should be set based on conditionCd and is set now by InvestigationEditLoad
        //request.setAttribute("hasPathogen", "false");
        Collection<Object>  supplObservationCollection  = new ArrayList<Object> ();
        Collection<ObservationVO>  theObservationCollection  = investigationForm.getOldProxy().getTheObservationVOCollection();
        if(theObservationCollection  != null)
        {
           Iterator<ObservationVO>  anIterator = theObservationCollection.iterator();
            while(anIterator.hasNext())
            {
                ObservationVO observationVO = (ObservationVO)anIterator.next();
                if(observationVO.getTheObservationDT().getCtrlCdDisplayForm() != null && observationVO.getTheObservationDT().getCtrlCdDisplayForm().trim().equals(NEDSSConstants.supplemental.trim()))
                {
                    //hasPathogen should be set based on conditionCd and is set now by InvestigationEditLoad
                    //request.setAttribute("hasPathogen", "true");
                    request.setAttribute("ageSixDays", "T");
                    request.setAttribute("DOBValidation", "T");
                    //this value is also set when person age is <= 6 days look at convertPersonsToRequestForBmird() method
                    supplObservationCollection.add(observationVO);
                }
            }
        }
        if(supplObservationCollection.size() > 0)
        {
            logger.info("supplObservationCollection.size(): " + supplObservationCollection.size());
            //investigationForm.setSupplementalCollection(supplObservationCollection);
        }

    }

    public int setSupplimentObservationAndARForCreate(InvestigationForm investigationForm, int tempID, String investigationFormCd, HttpServletRequest request)
    {

        Collection<ObservationVO>  supplimentObservations = investigationForm.getSupplementalCollection();
        Collection<ObservationVO>  theObservationVOCollection  = investigationForm.getProxy().getTheObservationVOCollection();
        Collection<Object>  actRelationshipCollection   = investigationForm.getProxy().getTheActRelationshipDTCollection();
        ObservationVO formObservationVO = this.getObservationVO(investigationForm.getProxy(), "INV_FORM_BMD");
        if(formObservationVO != null)
        {
            formObservationVO.getTheObservationDT().setCd(investigationFormCd);
            formObservationVO.getTheObservationDT().setItNew(true);
            formObservationVO.getTheObservationDT().setItDirty(false);
            formObservationVO.setItDirty(false);
            formObservationVO.setItNew(true);
        }
        if(supplimentObservations != null && supplimentObservations.size() > 0 && theObservationVOCollection  != null)
        {
          TreeMap<Object,Object> obsActTreeMap = super.setObservations(supplimentObservations, new TreeMap<Object, Object>(), "T",formObservationVO.getTheActRelationshipDTCollection(), formObservationVO.getTheObservationDT().getObservationUid(), NEDSSConstants.INV_FRM_Q, request, tempID);
          theObservationVOCollection.addAll((ArrayList<ObservationVO> )obsActTreeMap.get("observations"));
          actRelationshipCollection.addAll((ArrayList<?> )obsActTreeMap.get("actrelations"));
          tempID = ((Integer)obsActTreeMap.get("tempID")).intValue();
        }
        else
        {
            logger.info("no supplimentObservations for this investigation: " + investigationFormCd);
        }

        return tempID;
    }

    
    public void convertBatchEntryToRequestForGBS(InvestigationForm investigationForm, HttpServletRequest request)
    {

        request.setAttribute("antibioticBatchEntryList", "");
        if(investigationForm.getAntibioticBatchEntryCollection() == null)
        {
            logger.debug("AntibioticBatchEntryCollection  is null");
        }
        else
        {
            Collection<Object>  antibioticBatchEntryCollection  = investigationForm.getAntibioticBatchEntryCollection();
            StringBuffer antibioticBatchEntryList = new StringBuffer("");
           Iterator<Object>  itr = antibioticBatchEntryCollection.iterator();
            while(itr.hasNext())
            {
                BatchEntryHelper antibioticBatchEntry = (BatchEntryHelper)itr.next();
                if(antibioticBatchEntry.getTreeMap() != null)
                {
                    logger.debug("antibioticBatchEntry: " + antibioticBatchEntry.getObservationVOCollection());
                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[0].obsValueCodedDT_s[0].code");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(antibioticBatchEntry.getTreeMap().get("BMD260"))).append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[1].obsValueCodedDT_s[0].code");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(antibioticBatchEntry.getTreeMap().get("BMD261"))).append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[2].obsValueNumericDT_s[0].numericValue1_s");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.integerValue(antibioticBatchEntry.getTreeMap().get("BMD264"))).append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[3].obsValueDateDT_s[0].fromTime_s");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.formatDateObj(antibioticBatchEntry.getTreeMap().get("BMD262"))).append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[4].obsValueDateDT_s[0].fromTime_s");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.formatDateObj(antibioticBatchEntry.getTreeMap().get("BMD263"))).append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("antibioticBatchEntry_s[i].observationVO_s[0].theObservationDT.cd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("BMD260").append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("antibioticBatchEntry_s[i].observationVO_s[1].theObservationDT.cd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("BMD261").append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("antibioticBatchEntry_s[i].observationVO_s[2].theObservationDT.cd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("BMD264").append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("antibioticBatchEntry_s[i].observationVO_s[3].theObservationDT.cd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("BMD262").append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("antibioticBatchEntry_s[i].observationVO_s[4].theObservationDT.cd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("BMD263").append(NEDSSConstants.BATCH_SECT);

                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].statusCd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("A").append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_LINE);
                }
            }
            logger.debug("antibioticBatchEntryList for display is: " + antibioticBatchEntryList.toString());
            request.setAttribute("antibioticBatchEntryList", antibioticBatchEntryList.toString());
        }

    }

    public void convertBatchEntryToRequestForSP(InvestigationForm investigationForm, HttpServletRequest request)
    {

        if(investigationForm.getAntibioticBatchEntryCollection() == null)
        {
            logger.debug("AntibioticBatchEntryCollection  is null");
        }
        else
        {
            Collection<Object>  antibioticBatchEntryCollection  = investigationForm.getAntibioticBatchEntryCollection();
            StringBuffer antibioticBatchEntryList = new StringBuffer("");
           Iterator<Object>  itr = antibioticBatchEntryCollection.iterator();
            while(itr.hasNext())
            {
                BatchEntryHelper antibioticBatchEntry = (BatchEntryHelper)itr.next();
                if(antibioticBatchEntry.getTreeMap() != null)
                {
                    logger.debug("antibioticBatchEntry: " + antibioticBatchEntry.getObservationVOCollection());
                    logger.debug("antibiotic:" + antibioticBatchEntry.getObservationVO_s(0).getObsValueCodedDT_s(0).getCode());
                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[0].obsValueCodedDT_s[0].code");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(antibioticBatchEntry.getTreeMap().get("BMD212"))).append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[1].obsValueCodedDT_s[0].code");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(antibioticBatchEntry.getTreeMap().get("BMD213"))).append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[2].obsValueCodedDT_s[0].code");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(antibioticBatchEntry.getTreeMap().get("BMD214"))).append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[3].obsValueCodedDT_s[0].code");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(antibioticBatchEntry.getTreeMap().get("BMD215"))).append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[4].obsValueNumericDT_s[0].numericValue1_s");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(antibioticBatchEntry.getTreeMap().get("BMD216"))).append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("antibioticBatchEntry_s[i].observationVO_s[0].theObservationDT.cd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("BMD212").append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("antibioticBatchEntry_s[i].observationVO_s[1].theObservationDT.cd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("BMD213").append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("antibioticBatchEntry_s[i].observationVO_s[2].theObservationDT.cd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("BMD214").append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("antibioticBatchEntry_s[i].observationVO_s[3].theObservationDT.cd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("BMD215").append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("antibioticBatchEntry_s[i].observationVO_s[4].theObservationDT.cd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("BMD216").append(NEDSSConstants.BATCH_SECT);

                    antibioticBatchEntryList.append("antibioticBatchEntry_s[i].statusCd");
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("A").append(NEDSSConstants.BATCH_SECT);
                    antibioticBatchEntryList.append(NEDSSConstants.BATCH_LINE);
                }
            }
            logger.debug("antibioticBatchEntryList for display is: " + antibioticBatchEntryList.toString());
            request.setAttribute("agentBatchEntryList", antibioticBatchEntryList.toString());
        }

    }

    public void investigationCreateLoad(HttpServletRequest request)
    {

        request.setAttribute("INV109", "O");
        request.setAttribute("INV174", "true");

    }

    public void prepareEntitiesForReload(HttpServletRequest request)
    {
        StringTokenizer parsedString = null;
        String stringToParse = "";

        //Investigator
        stringToParse = request.getParameter("investigator.personUid-values") == null ? "" : request.getParameter("investigator.personUid-values").toString();

        if(stringToParse.length() > 0) {
	        request.setAttribute("investigatorUID", request.getParameter("investigator.personUid"));
	        request.setAttribute("investigatorDemographics", stringToParse);
	        request.setAttribute("investigator.personUid-values", stringToParse);
        }

        if(request.getParameter("dateAssignedToInvestigation") != null && !(request.getParameter("investigator.personUid").equals("")))
        {
            request.setAttribute("dateAssignedToInvestigation", request.getParameter("dateAssignedToInvestigation"));
        }

        //ABCs Investigator
        stringToParse = request.getParameter("abcInvestigator.personUid-values") == null ? "" : request.getParameter("abcInvestigator.personUid-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("abcInvestigatorUid", request.getParameter("abcInvestigator.personUid"));
	        request.setAttribute("abcInvestigatorDemographics", stringToParse);
	        request.setAttribute("abcInvestigator.personUid-values", stringToParse);
        }

        //  reporter org
        stringToParse = request.getParameter("reportingOrg.organizationUID-values") == null ? "" : request.getParameter("reportingOrg.organizationUID-values").toString();

        if(stringToParse.length() > 0) {
	        request.setAttribute("reportingOrgUID", request.getParameter("reportingOrg.organizationUID"));
	        request.setAttribute("reportingOrgDemographics", stringToParse);
	        request.setAttribute("reportingOrg.organizationUID-values", stringToParse);
        }

        //reporter person
        stringToParse = request.getParameter("reporter.personUid-values") == null ? "" : request.getParameter("reporter.personUid-values").replaceAll("^","<br/>").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("reporterPersonUid", request.getParameter("reporter.personUid"));
	        request.setAttribute("reporterDemographics", stringToParse);
	        request.setAttribute("reporter.personUid-values", stringToParse);
        }
        //physician
        stringToParse = request.getParameter("physician.personUid-values") == null ? "" : request.getParameter("physician.personUid-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("physicianPersonUid", request.getParameter("physician.personUid"));
	        request.setAttribute("physicianDemographics", stringToParse);
	        request.setAttribute("physician.personUid-values", stringToParse);
        }
        // hospital organization
        stringToParse = request.getParameter("hospitalOrg.organizationUID-values") == null ? "" : request.getParameter("hospitalOrg.organizationUID-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("hospitalOrgUID", request.getParameter("hospitalOrg.organizationUID"));
	        request.setAttribute("hospitalDemographics", stringToParse);
	        request.setAttribute("hospitalOrg.organizationUID-values", stringToParse);
        }

        // ABCs Culture Hospital
        stringToParse = request.getParameter("labHospitalOrg.organizationUid-values") == null ? "" : request.getParameter("labHospitalOrg.organizationUid-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("labHospitalOrgUID", request.getParameter("labHospitalOrg.organizationUid"));
	        request.setAttribute("labHospitalDemographics", stringToParse);
	        request.setAttribute("labHospitalOrg.organizationUid-values", stringToParse);
        }
        // ABCs Treatment Hospital
        stringToParse = request.getParameter("treatmentHospitalOrg.organizationUid-values") == null ? "" : request.getParameter("treatmentHospitalOrg.organizationUid-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("treatmentHospitalOrgUID", request.getParameter("treatmentHospitalOrg.organizationUid"));
	        request.setAttribute("treatmentHospitalDemographics", stringToParse);
	        request.setAttribute("treatmentHospitalOrg.organizationUid-values", stringToParse);
        }
        //ABCs Transfer Hospital
        stringToParse = request.getParameter("patientTransferHospitalOrg.organizationUid-values") == null ? "" : request.getParameter("patientTransferHospitalOrg.organizationUid-values").toString();
        if(stringToParse.length() > 0) {
	        request.setAttribute("patHospitalOrgUID", request.getParameter("patientTransferHospitalOrg.organizationUid"));
	        request.setAttribute("patHospitalDemographics", stringToParse);
	        request.setAttribute("patientTransferHospitalOrg.organizationUid-values", stringToParse);
        }
        //Day Care Facility
        stringToParse = request.getParameter("daycareFacilityOrg.organizationUid-values") == null ? "" : request.getParameter("daycareFacilityOrg.organizationUid-values").toString();
        if(stringToParse.length() > 0) {
        	request.setAttribute("daycareFacilityOrgUid", request.getParameter("daycareFacilityOrg.organizationUid"));
        	request.setAttribute("daycareDemographics", stringToParse);
        	request.setAttribute("daycareFacilityOrg.organizationUid-values", stringToParse);
        }
        //Chronic Care Facility

        stringToParse = request.getParameter("chronicCareFacility.organizationUid-values") == null ? "" : request.getParameter("chronicCareFacility.organizationUid-values").toString();
        if(stringToParse.length() > 0) {
        	request.setAttribute("chronicCareFacilityUid", request.getParameter("chronicCareFacility.organizationUid"));
        	request.setAttribute("chronicCareDemographics", stringToParse);
        	request.setAttribute("chronicCareFacility.organizationUid-values", stringToParse);
        }
        //Birth Hospital

        stringToParse = request.getParameter("birthHospital.organizationUid-values") == null ? "" : request.getParameter("birthHospital.organizationUid-values").toString();
        if(stringToParse.length() > 0) {
        	request.setAttribute("birthHospitalUid", request.getParameter("birthHospital.organizationUid"));
        	request.setAttribute("birthHospitalDemographics", stringToParse);
        	request.setAttribute("birthHospital.organizationUid-values", stringToParse);
        }

        //  hidden values
        request.setAttribute("NBSSecurityJurisdictions", request.getParameter("NBSSecurityJurisdictions"));
        request.setAttribute("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cd", request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cd"));
        request.setAttribute("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.progAreaCd", request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.progAreaCd"));
        request.setAttribute("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cdDescTxt", request.getParameter("proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.cdDescTxt"));
        request.setAttribute("ContextAction", request.getParameter("ContextAction"));
        request.setAttribute("subjectUID", request.getParameter("subjectUID"));

    }

    public void setConfirmationMethodReload(HttpServletRequest request)
    {

      String[] array = request.getParameterValues("confirmationMethods");
        if(array != null)
        {
          StringBuffer sb = new StringBuffer();
          for (int i = 0; i < array.length; i++)
          {
             //don't store empty strings in parameter string array
             if (!(array[i].trim()).equals(""))
             {
               sb.append(array[i]).append("|");
             }
         }
         if(sb.length() > 0)
         request.setAttribute("confirmationMethodList", sb.toString());
        }


    }


    private void setConfirmationMethods(HttpServletRequest request, PublicHealthCaseVO phcVO)
    {

        Collection<Object>  confMethodColl = new ArrayList<Object> ();
        String[] arr = request.getParameterValues("confirmationMethods");
        if(arr != null)
        {

            for(int i = 0; i < arr.length; i++)
            {
                logger.debug("confirmationMethod Array element: " + arr[i]);
                String strVal = arr[i];
                if(strVal != null && !strVal.trim().equals(""))
                {
                    ConfirmationMethodDT cm = new ConfirmationMethodDT();
                    cm.setConfirmationMethodCd(strVal);
                    cm.setItNew(true);
                    confMethodColl.add(cm);
                }
                else
                {
                    ConfirmationMethodDT cm = new ConfirmationMethodDT();
                    cm.setConfirmationMethodCd("NA");
                    cm.setItNew(true);
                    confMethodColl.add(cm);
                }
            }
        }
        if(confMethodColl.size() > 0)
        {
            phcVO.setTheConfirmationMethodDTCollection(confMethodColl);
        }
        else
        {
            phcVO.setTheConfirmationMethodDTCollection(null);
        }

    }

    private String getCountiesByState(String stateCd)
    {

        StringBuffer parsedCodes = new StringBuffer("");
        if(stateCd != null)
        {
            //SRTValues srtValues = new SRTValues();
            CachedDropDownValues srtValues = new CachedDropDownValues();
            TreeMap<?,?> treemap = null;
            treemap = srtValues.getCountyCodes(stateCd);
            if(treemap != null)
            {
                Set<?> set = treemap.keySet();
               Iterator<?>  itr = set.iterator();
                while(itr.hasNext())
                {
                    String key = (String)itr.next();
                    String value = (String)treemap.get(key);
                    parsedCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim()).append(NEDSSConstants.SRT_LINE);
                }
            }
        }

        return parsedCodes.toString();
    }

    private boolean setDirtyForSupplimentalObservationOnEdit(InvestigationForm investigationForm, boolean investigationProxyVODirty)
    {

        Collection<ObservationVO>  obsColl = investigationForm.getSupplementalCollection();
        this.setObsValueCodedToDefaultValues(obsColl);
        if(obsColl != null)
        {
           Iterator<ObservationVO>  itor = obsColl.iterator();
            while(itor.hasNext())
            {
                ObservationVO obsVO = (ObservationVO)itor.next();
                ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
                if(obsVO.getTheObsValueCodedDTCollection() != null)
                {
                   Iterator<Object>  it = obsVO.getTheObsValueCodedDTCollection().iterator();
                    while(it.hasNext())
                    {
                        ObsValueCodedDT obsValDT = (ObsValueCodedDT)it.next();
                        investigationProxyVODirty = true;
                        obsValDT.setItDirty(true);
                        obsValDT.setItNew(false);
                        obsVO.setItDirty(true);
                        obsVO.setItNew(false);
                    }
                }
                if(obsVO.getTheObsValueDateDTCollection() != null)
                {
                   Iterator<Object>  it = obsVO.getTheObsValueDateDTCollection().iterator();
                    while(it.hasNext())
                    {
                        ObsValueDateDT obsValDT = (ObsValueDateDT)it.next();
                        investigationProxyVODirty = true;
                        obsValDT.setItDirty(true);
                        obsValDT.setItNew(false);
                        obsVO.setItDirty(true);
                        obsVO.setItNew(false);
                    }
                }
                if(obsVO.getTheObsValueNumericDTCollection() != null)
                {
                   Iterator<Object>  it = obsVO.getTheObsValueNumericDTCollection().iterator();
                    while(it.hasNext())
                    {
                        ObsValueNumericDT obsValDT = (ObsValueNumericDT)it.next();
                        investigationProxyVODirty = true;
                        obsValDT.setItDirty(true);
                        obsValDT.setItNew(false);
                        obsVO.setItDirty(true);
                        obsVO.setItNew(false);
                    }
                }
                if(obsVO.getTheObsValueTxtDTCollection() != null)
                {
                   Iterator<Object>  it = obsVO.getTheObsValueTxtDTCollection().iterator();
                    while(it.hasNext())
                    {
                        ObsValueTxtDT obsValDT = (ObsValueTxtDT)it.next();
                        investigationProxyVODirty = true;
                        obsValDT.setItDirty(true);
                        obsValDT.setItNew(false);
                        obsVO.setItDirty(true);
                        obsVO.setItNew(false);
                    }
                }
                ObservationDT obsDT = obsVO.getTheObservationDT();
                String strCode = obsDT.getCd();
                if((strCode == null) || (strCode.length() == 0))
                {
                    logger.error("Page submitted an observation where obsDT.CD was not set.");
                }
            }
        }

        return investigationProxyVODirty;
    }

    private Collection<Object>  setItemToRowAndFormActRelationshipForCreate(Collection<Object>  antibioticObservations, ObservationVO rowObservationVO, ObservationVO formObservationVO)
    {

        logger.debug("inside setItemToRowActRelationshipForCreate: " + NEDSSConstants.ITEM_TO_ROW);
        CachedDropDownValues srtc = new CachedDropDownValues();
        Collection<Object>  actRelationshipCollection   = new ArrayList<Object> ();
        ActRelationshipDT itemToRowAR = null;
        ActRelationshipDT itemToFormAR = null;
        ObservationVO itemObservationVO = null;
       Iterator<Object>  anIterator = antibioticObservations.iterator();
        while(anIterator.hasNext())
        {
            itemObservationVO = (ObservationVO)anIterator.next();
            itemToRowAR = new ActRelationshipDT();
            itemToRowAR.setSourceActUid(itemObservationVO.getTheObservationDT().getObservationUid());
            itemToRowAR.setSourceClassCd(NEDSSConstants.CLASS_CD_OBS);
            itemToRowAR.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            itemToRowAR.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            itemToRowAR.setTargetActUid(rowObservationVO.getTheObservationDT().getObservationUid());
            itemToRowAR.setTypeCd(NEDSSConstants.ITEM_TO_ROW);
            itemToRowAR.setTypeDescTxt(srtc.getDescForCode("AR_TYPE",NEDSSConstants.ITEM_TO_ROW));
            itemToRowAR.setTargetClassCd(NEDSSConstants.CLASS_CD_OBS);
            itemToRowAR.setItNew(true);
            itemToRowAR.setItDirty(false);
            itemToRowAR.setItDelete(false);
            itemToFormAR = new ActRelationshipDT();
            itemToFormAR.setSourceActUid(itemObservationVO.getTheObservationDT().getObservationUid());
            itemToFormAR.setSourceClassCd(NEDSSConstants.CLASS_CD_OBS);
            itemToFormAR.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            itemToFormAR.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            itemToFormAR.setTargetActUid(formObservationVO.getTheObservationDT().getObservationUid());
            itemToFormAR.setTypeCd(NEDSSConstants.INV_FRM_Q);
            itemToFormAR.setTargetClassCd(NEDSSConstants.CLASS_CD_OBS);
            itemToFormAR.setItNew(true);
            itemToFormAR.setItDirty(false);
            itemToFormAR.setItDelete(false);
            actRelationshipCollection.add(itemToRowAR);
            actRelationshipCollection.add(itemToFormAR);
        }

        return actRelationshipCollection;
    }
 private boolean  setMultipleToObservation(String[] array, String code, Collection<ObservationVO>  obsColl, boolean investigationProxyVODirty)
 {
       logger.debug("setting multiple observations");
       ObservationVO obsVO = null;
       ObsValueCodedDT obsValueCodedDT = null;
       if(array != null)
       {
         investigationProxyVODirty = true;
         obsVO = ObservationUtils.findObservationByCode(obsColl, code);
           if(obsVO != null)
           {
               List<Object> obsValueCoded = new ArrayList<Object> ();
               for(int i = 0; i < array.length; i++)
               {
                   //Build the ObsValueCodedDT object for insert
                   if(!array[i].trim().equals(""))
                   {
                   obsValueCodedDT = new ObsValueCodedDT();
                   obsValueCodedDT.setObservationUid(obsVO.getTheObservationDT().getUid());
                   obsValueCodedDT.setCode((String)array[i]);
                   obsValueCodedDT.setItNew(true);
                   obsValueCodedDT.setItDirty(false);
                   obsValueCodedDT.setItDelete(false);
                   obsValueCoded.add(obsValueCodedDT);
                 }
             }
               //If the observation is not new, add the delete
               if(!obsVO.getTheObservationDT().isItNew())
               {
                   ObsValueCodedDT obsValueCodedDTdel = new ObsValueCodedDT();
                   obsValueCodedDTdel.setObservationUid(obsVO.getTheObservationDT().getUid());
                   obsValueCodedDTdel.setItNew(false);
                   obsValueCodedDTdel.setItDirty(false);
                   obsValueCodedDTdel.setItDelete(true);
                   /*Add it to the first position in the collection, so existing records
                      are deleted before new ones are inserted.*/
                   obsValueCoded.add(0, obsValueCodedDTdel);
               }
               obsVO.setTheObsValueCodedDTCollection(obsValueCoded);
               ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
           }
       }

       return investigationProxyVODirty;
 }


    private void setObsValueCodedToDefaultValues(Collection<ObservationVO>  observations)
    {

       Iterator<ObservationVO>  collItor = observations.iterator();
        if(collItor == null)
        {

            return;
        }
        while(collItor.hasNext())
        {
            ObservationVO obsVO = (ObservationVO)collItor.next();
            Collection<Object>  obsValDTs = obsVO.getTheObsValueCodedDTCollection();
            if(obsValDTs == null)
            {
                continue;
            }
           Iterator<Object>  dtItor = obsValDTs.iterator();
            while(dtItor.hasNext())
            {
                ObsValueCodedDT dt = (ObsValueCodedDT)dtItor.next();
                if((dt.getCode() == null) || (dt.getCode().trim().length() == 0))
                {
                    dt.setCode("NI");
                }
            }
        }

    }

    private ObservationVO getObservationByUid(Collection<ObservationVO>  theObservationVOCollection, Long observationUid)
    {

        String observationCode = "";
        ObservationVO observationVO = null;
       Iterator<ObservationVO>  anIterator = null;
        if(theObservationVOCollection  != null)
        {
            anIterator = theObservationVOCollection.iterator();
            while(anIterator.hasNext())
            {
                observationVO = (ObservationVO)anIterator.next();
                if(observationVO.getTheObservationDT().getObservationUid() != null && observationVO.getTheObservationDT().getObservationUid().equals(observationUid))
                {
                    logger.debug("getObservationByUid: " + observationVO.getTheObservationDT().getObservationUid());

                    return observationVO;
                }
            }
        }

        return null;
    }

    private OrganizationNameDT getOrganizationNameDT(OrganizationVO organizationVO)
    {

        OrganizationNameDT organizationNameDT = null;
        Collection<Object>  organizationNameDTCollection  = organizationVO.getTheOrganizationNameDTCollection();
        if(organizationNameDTCollection  != null)
        {
           Iterator<Object>  anIterator = organizationNameDTCollection.iterator();
            if(anIterator.hasNext())
            {
                organizationNameDT = (OrganizationNameDT)anIterator.next();

                return organizationNameDT;
            }
        }

        return null;
    }


    private ActRelationshipDT setRowActRelationshipForCreate(ObservationVO rowObservationVO, ObservationVO formObservationVO)
    {

        ActRelationshipDT rowActRelationshipDT = new ActRelationshipDT();
        rowActRelationshipDT.setSourceActUid(rowObservationVO.getTheObservationDT().getObservationUid());
        rowActRelationshipDT.setSourceClassCd(NEDSSConstants.CLASS_CD_OBS);
        rowActRelationshipDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        rowActRelationshipDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        rowActRelationshipDT.setTargetActUid(formObservationVO.getTheObservationDT().getObservationUid());
        rowActRelationshipDT.setTypeCd(NEDSSConstants.INV_FRM_Q);
        rowActRelationshipDT.setTargetClassCd(NEDSSConstants.CLASS_CD_OBS);
        rowActRelationshipDT.setItNew(true);
        rowActRelationshipDT.setItDirty(false);
        rowActRelationshipDT.setItDelete(false);
        logger.debug("row Observation typeCd: " + rowActRelationshipDT.getTypeCd() + "SourceActUid : " + rowActRelationshipDT.getSourceActUid());

        return rowActRelationshipDT;
    }



    private int setTempID(Collection<ObservationVO>  itemObservations, int tempID)
    {

        ObservationVO obsVO = null;
       Iterator<ObservationVO>  anIterator = itemObservations.iterator();
        while(anIterator.hasNext())
        {
            obsVO = (ObservationVO)anIterator.next();
            if(obsVO.getTheObservationDT().getObservationUid() == null)
            {
                obsVO.setItNew(true);
                obsVO.setItDirty(false);
                obsVO.getTheObservationDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                obsVO.getTheObservationDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                obsVO.getTheObservationDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                obsVO.getTheObservationDT().setItNew(true);
                obsVO.getTheObservationDT().setItDirty(false);
                obsVO.getTheObservationDT().setObservationUid(new Long(tempID--));
            }
        }
        // sets NI if blank in Obs_value_coded
        this.setObsValueCodedToDefaultValues(itemObservations);

        return tempID;
    }

    public void convertObservationsToRequest(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
    {

        Collection<ObservationVO>  obsColl = investigationProxyVO.getTheObservationVOCollection();
        if(obsColl != null)
        {
           Iterator<ObservationVO>  iter = obsColl.iterator();
            while(iter.hasNext())
            {
                ObservationVO obsVO = (ObservationVO)iter.next();
                String obsCode = obsVO.getTheObservationDT().getCd();
                if(obsCode != null)
                {
                    //get the coded values
                    Collection<Object>  codedColl = obsVO.getTheObsValueCodedDTCollection();
                    if(codedColl != null)
                    {
                     Iterator<Object>  codedIter = codedColl.iterator();
                      StringBuffer multiSelect = new StringBuffer();
                      while(codedIter.hasNext())
                      {
                          ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)codedIter.next();
                          multiSelect.append(obsValueCodedDT.getCode()).append("|");
//                            logger.debug("Mark after append of |");
                          if(codedColl.size() == 1)
                          {
                              request.setAttribute(obsCode, (obsValueCodedDT.getCode() == null ? "" : obsValueCodedDT.getCode()));
                              request.setAttribute(obsCode + "-desc", (obsValueCodedDT.getCodeSystemDescTxt() == null ? "" : obsValueCodedDT.getCodeSystemDescTxt()));
                              if(obsCode.equals("INV154"))
                              {
                                 String code = obsValueCodedDT.getCode();
                                 request.setAttribute("importedCountyInState", this.getCountiesByState(code));
                              }

                          }
                      }
                      if(codedColl.size() > 1)
                      {
                          request.setAttribute(obsCode, multiSelect.toString());
                      }

                    }
                    //get date
                    Collection<Object>  dateColl = obsVO.getTheObsValueDateDTCollection();
                    if(dateColl != null)
                    {
                       Iterator<Object>  dateIter = dateColl.iterator();
                        if(dateIter.hasNext())
                        {
                            ObsValueDateDT obsDateCodedDT = (ObsValueDateDT)dateIter.next();
                            request.setAttribute(obsCode + "-fromTime", (obsDateCodedDT.getFromTime() == null ? " " : formatDate(obsDateCodedDT.getFromTime())));
                            request.setAttribute(obsCode + "-toTime", (obsDateCodedDT.getToTime() == null ? " " : formatDate(obsDateCodedDT.getToTime())));
                            request.setAttribute(obsCode + "-durationAmt", (obsDateCodedDT.getDurationAmt() == null ? " " : obsDateCodedDT.getDurationAmt()));
                            request.setAttribute(obsCode + "-durationUnitCd", (obsDateCodedDT.getDurationUnitCd() == null ? " " : obsDateCodedDT.getDurationUnitCd()));
                        }
                    }
                    //get text
                    Collection<Object>  textColl = obsVO.getTheObsValueTxtDTCollection();
                    if(textColl != null)
                    {
                       Iterator<Object>  textIter = textColl.iterator();
                        if(textIter.hasNext())
                        {
                            ObsValueTxtDT obsTextCodedDT = (ObsValueTxtDT)textIter.next();
                            request.setAttribute(obsCode + "-valueTxt", (obsTextCodedDT.getValueTxt() == null ? " " : obsTextCodedDT.getValueTxt()));
                        }
                    }
                    // get numeric
                    Collection<Object>  numColl = obsVO.getTheObsValueNumericDTCollection();
                    if(numColl != null)
                    {
                       Iterator<Object>  numIter = numColl.iterator();
                        if(numIter.hasNext())
                        {
                            ObsValueNumericDT obsNumCodedDT = (ObsValueNumericDT)numIter.next();
                            if(obsCode != null && obsCode.trim().equals("BMD265")) {

                              if(obsNumCodedDT.getNumericValue1() != null && obsNumCodedDT.getNumericValue2() != null) {
                                StringBuffer bmd265 = new StringBuffer();
                                bmd265.append(obsNumCodedDT.getNumericValue1().intValue()).append(":").append(obsNumCodedDT.getNumericValue2().intValue());
                                request.setAttribute("BMD265-Value", bmd265.toString());
                              }

                            }
                            else if(obsCode != null && obsCode.trim().equals("BMD326") && obsNumCodedDT.getNumericValue1()!=null) {
                                   int BMD326 = obsNumCodedDT.getNumericValue1().intValue();
                                   if(BMD326==-1)
                                	   request.setAttribute("BMD326_unk","T" );
                                   
                            }
                            else if(obsCode != null && obsCode.trim().equals("BMD327") && obsNumCodedDT.getNumericValue1()!=null) {
                                int BMD327 = obsNumCodedDT.getNumericValue1().intValue();
                                if(BMD327==-1)
                             	   request.setAttribute("BMD327_unk","T" );
                                
                         }
                            else {
                              request.setAttribute(obsCode + "-numericValue1",
                                                   (obsNumCodedDT.getNumericValue1() == null ?
                                                    "" :
                                                    String.valueOf(obsNumCodedDT.getNumericValue1())));
                            }

                        }
                    }
                }
            }
        }

    }

    private void convertOrganizationToRequestBmird(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
    {
        StringBuffer sb = new StringBuffer();
        CachedDropDownValues cdv = new CachedDropDownValues();
        OrganizationVO reportingOrganizationVO = this.getOrganizationVO(NEDSSConstants.PHC_REPORTING_SOURCE, investigationProxyVO);

        
		String displayString="";

        //Reporting Organization
        if(reportingOrganizationVO != null) {
			request.setAttribute("reportingOrgUID", reportingOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(reportingOrganizationVO);
			request.setAttribute("reportingOrgDemographics", displayString);
			request.setAttribute("reportingOrg.organizationUID-values", displayString);

		} else {
			request.setAttribute("reportingOrgDemographics", "There is no Reporting Source selected.");
			request.setAttribute("reportingOrg.organizationUID-values", "There is no Reporting Source selected.");
		}


        //Hospital Organization
        OrganizationVO hospitalOrganizationVO = this.getOrganizationVO(NEDSSConstants.HOSPITAL_NAME_ADMITTED, investigationProxyVO);
        if(hospitalOrganizationVO != null) {
			request.setAttribute("hospitalOrgUID", hospitalOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(hospitalOrganizationVO);
			request.setAttribute("hospitalDemographics", displayString);
			request.setAttribute("hospitalOrg.organizationUID-values", displayString);

		} else {
			request.setAttribute("hospitalDemographics", "There is no Hospital selected.");
			request.setAttribute("hospitalOrg.organizationUID-values", "There is no Hospital selected.");
		}

        //ABCs Culture Hospital
        OrganizationVO labHospitalOrganizationVO = this.getOrganizationVO(NEDSSConstants.HospOfCulture, investigationProxyVO);
        if(labHospitalOrganizationVO != null) {
			request.setAttribute("labHospitalOrgUID", labHospitalOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(labHospitalOrganizationVO);
			request.setAttribute("labHospitalDemographics", displayString);
			request.setAttribute("labHospitalOrg.organizationUid-values", displayString);

		} else {
			request.setAttribute("labHospitalDemographics", "There is no ABCs Culture Hospital selected.");
			request.setAttribute("labHospitalOrg.organizationUid-values", "There is no ABCs Culture Hospital selected.");
		}
        //ABCs treatment Hospital
        OrganizationVO treatmentHospitalOrganizationVO = this.getOrganizationVO(NEDSSConstants.TreatmentHosp, investigationProxyVO);
        if(treatmentHospitalOrganizationVO != null) {
			request.setAttribute("treatmentHospitalOrgUID", treatmentHospitalOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(treatmentHospitalOrganizationVO);
			request.setAttribute("treatmentHospitalDemographics", displayString);
			request.setAttribute("treatmentHospitalOrg.organizationUid-values", displayString);

		} else {
			request.setAttribute("treatmentHospitalDemographics", "There is no Treatment Hospital selected.");
			request.setAttribute("treatmentHospitalOrg.organizationUid-values", "There is no Treatment Hospital selected.");
		}

        //Transfer Hospital Organization
        OrganizationVO transferHospitalOrganizationVO =
            this.getOrganizationVO(NEDSSConstants.TransferHosp,
                                   investigationProxyVO);
        if(transferHospitalOrganizationVO != null) {
			request.setAttribute("patHospitalOrgUID", transferHospitalOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(transferHospitalOrganizationVO);
			request.setAttribute("patHospitalDemographics", displayString);
			request.setAttribute("patientTransferHospitalOrg.organizationUid-values", displayString);

		} else {
			request.setAttribute("patHospitalDemographics", "There is no ABCs Transfer Hospital selected.");
			request.setAttribute("patientTransferHospitalOrg.organizationUid-values", "There is no ABCs Transfer Hospital selected.");
		}

        //Day Care Hospital Organization
        OrganizationVO daycareHospitalOrganizationVO = this.getOrganizationVO(NEDSSConstants.DaycareFac, investigationProxyVO);
        if(daycareHospitalOrganizationVO != null) {
			request.setAttribute("daycareFacilityOrgUid", daycareHospitalOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(daycareHospitalOrganizationVO);
			request.setAttribute("daycareDemographics", displayString);
			request.setAttribute("daycareFacilityOrg.organizationUid-values", displayString);

		} else {
			request.setAttribute("daycareDemographics", "There is no Day Care Facility selected.");
			request.setAttribute("daycareFacilityOrg.organizationUid-values", "There is no Day Care Facility selected.");
		}
        //Chronic Care Facility Organization
        OrganizationVO chronicCareFacOrganizationVO = this.getOrganizationVO(NEDSSConstants.ChronicCareFac, investigationProxyVO);
        if(chronicCareFacOrganizationVO != null) {
			request.setAttribute("chronicCareFacilityUid", chronicCareFacOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(chronicCareFacOrganizationVO);
			request.setAttribute("chronicCareDemographics", displayString);
			request.setAttribute("chronicCareFacility.organizationUid-values", displayString);

		} else {
			request.setAttribute("chronicCareDemographics", "There is no Chronic Care Facility selected.");
			request.setAttribute("chronicCareFacility.organizationUid-values", "There is no Chronic Care Facility selected.");
		}
        //Birth Organization
        OrganizationVO birthOrganizationVO = this.getOrganizationVO(NEDSSConstants.HospOfBirth, investigationProxyVO);
        if(birthOrganizationVO != null) {
			request.setAttribute("birthHospitalUid", birthOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(birthOrganizationVO);
			request.setAttribute("birthHospitalDemographics", displayString);
			request.setAttribute("birthHospital.organizationUid-values", displayString);

		} else {
			request.setAttribute("birthHospitalDemographics", "There is no Birth Hospital selected.");
			request.setAttribute("birthHospital.organizationUid-values", "There is no Birth Hospital selected.");
		}
        //Admitted Hospital
        OrganizationVO reAdmOrganizationVO = this.getOrganizationVO(NEDSSConstants.ReAdmHosp, investigationProxyVO);
        if(reAdmOrganizationVO != null) {
			request.setAttribute("admittedHospitalUid", reAdmOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(reAdmOrganizationVO);
			request.setAttribute("admittedHospitalDemographics", displayString);
			request.setAttribute("admittedHospital.organizationUID-values", displayString);

		} else {
			request.setAttribute("admittedHospitalDemographics", "There is no Admitted Hospital selected.");
			request.setAttribute("admittedHospital.organizationUID-values", "There is no Admitted Hospital selected.");
		}
        //College Organization
        OrganizationVO collegeOrganizationVO = this.getOrganizationVO(NEDSSConstants.CollegeUniversity, investigationProxyVO);
        if(collegeOrganizationVO != null) {
			request.setAttribute("collegeOrgUid", collegeOrganizationVO
					.getTheOrganizationDT().getOrganizationUid());

			displayString = helper.makeORGDisplayString(collegeOrganizationVO);
			request.setAttribute("collegeOrgDemographics", displayString);
			request.setAttribute("collegeOrg.organizationUID-values", displayString);

		} else {
			request.setAttribute("collegeOrgDemographics", "There is no College Organization selected.");
			request.setAttribute("collegeOrg.organizationUID-values", "There is no College Organization selected.");
		}
    }

    private int determineABCsHospitalIDToShow(Collection<Object>  hospitalEntityDTCollection)
    {
      short abcIdCount = 0;
      short totalIdCount = 0;
      for(Iterator<Object> it = hospitalEntityDTCollection.iterator(); it.hasNext(); )
      {
        EntityIdDT entityIdDT = (EntityIdDT)it.next();
        if(entityIdDT == null) continue;

        String idType = entityIdDT.getTypeCd();
        String recordStatusCd = entityIdDT.getRecordStatusCd();
        if(idType == null) continue;

        if(idType.equals("ABC") && recordStatusCd.equalsIgnoreCase
           (NEDSSConstants.RECORD_STATUS_ACTIVE))
        {
          abcIdCount++;
          totalIdCount++;
        }
        else if(!idType.equalsIgnoreCase("QEC") && recordStatusCd.equalsIgnoreCase
                (NEDSSConstants.RECORD_STATUS_ACTIVE))
        {
          totalIdCount++;
        }
      }

      if(totalIdCount == 1)
      {
        return SINGLE_ORG_ID_RULE;
      }
      if(abcIdCount == 1)
      {
        return SINGLE_ABC_ORG_ID_RULE;
      }
      return OTHER_ORG_ID_RULE;
    }



    private Collection<Object>  deleteRowActRelationship(ObservationVO formObservationVO, BatchEntryHelper batchEntryHelper)
    {

        Collection<Object>  actRelationshipDeletedColl = new ArrayList<Object> ();
        Collection<Object>  mainActrelationshipCollection  = formObservationVO.getTheActRelationshipDTCollection();
        if(mainActrelationshipCollection  != null)
        {
            Long rowObservationUid = batchEntryHelper.getUid();
            if(rowObservationUid != null)
            {
               Iterator<Object>  rowItor = mainActrelationshipCollection.iterator();
                while(rowItor.hasNext())
                {
                    ActRelationshipDT actRelationshipDT = (ActRelationshipDT)rowItor.next();
                    if(actRelationshipDT.getSourceActUid().longValue() == rowObservationUid.longValue())
                    {
                        actRelationshipDT.setItDelete(true);
                        actRelationshipDT.setItDirty(false);
                        actRelationshipDT.setItNew(false);
                        actRelationshipDeletedColl.add(actRelationshipDT);
                        break;
                    }
                }
            }  // end of rowObservationUid if
            Collection<ObservationVO>  itemCollection  = batchEntryHelper.getObservationVOCollection();
            if(itemCollection  != null && itemCollection.size() > 0)
            {
               Iterator<ObservationVO>  itemObsItor = itemCollection.iterator();
                while(itemObsItor.hasNext())
                {
                    ObservationVO itemObservationVO = (ObservationVO)itemObsItor.next();
                    itemObservationVO.setItDirty(true);
                    Long itemUid = itemObservationVO.getTheObservationDT().getObservationUid();
                   Iterator<Object>  arItor = mainActrelationshipCollection.iterator();
                    while(arItor.hasNext())
                    {
                        ActRelationshipDT actRelationshipDT = (ActRelationshipDT)arItor.next();
                        if(actRelationshipDT.getSourceActUid().longValue() == itemUid.longValue())
                        {
                            actRelationshipDT.setItDelete(true);
                            actRelationshipDT.setItDirty(false);
                            actRelationshipDT.setItNew(false);
                            actRelationshipDeletedColl.add(actRelationshipDT);
                        }
                    }  //while of actRelationship
                }  // while of itemObservation
            }  // end of itemCollection  if
        }
        formObservationVO.setItDirty(true);

        return actRelationshipDeletedColl;
    }

    private ObservationVO rowObservation(String cntrlCdDisplyForm)
    {

        ObservationVO rowObservationVO = new ObservationVO();
        ObservationDT rowObservationDT = new ObservationDT();
        rowObservationDT.setCd("ItemToRow");
        rowObservationDT.setCdSystemCd("NBS");
        rowObservationDT.setGroupLevelCd("L2");
        rowObservationDT.setObsDomainCd("CLN");
        rowObservationDT.setCtrlCdDisplayForm(cntrlCdDisplyForm);
        rowObservationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
        rowObservationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        rowObservationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        FormQACode.putQuestionCode(rowObservationDT);
        super.setNumericUnitCd(rowObservationVO);
        rowObservationDT.setItNew(true);
        rowObservationDT.setItDirty(false);
        rowObservationVO.setTheObservationDT(rowObservationDT);
        rowObservationVO.setItNew(true);
        rowObservationVO.setItDirty(false);

        return rowObservationVO;
    }

    private void trimEmptyObsValueCodedDTCollections(ObservationVO obsVO)
    {

        if(obsVO.getTheObsValueCodedDTCollection() != null)
        {
            Collection<Object>  obsValColl = obsVO.getTheObsValueCodedDTCollection();
            // For each CodedValueDT, ensure that its code isn't null
           Iterator<Object>  it = obsValColl.iterator();
            while(it.hasNext())
            {
                ObsValueCodedDT obsValDT = (ObsValueCodedDT)it.next();
                String strCode = obsValDT.getCode();
                if((strCode == null) || (strCode.trim().length() == 0))
                {
                    obsValDT.setCode("NI");
                }
            }
            // if we removed all the items from the DT collection.. continue
        }  // end if hasObsValueCodedDTCollection()

    }
    private void convertPersonsToRequestForBmird(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
    {
        PersonVO patientPersonVO = this.getPersonVO(NEDSSConstants.PHC_PATIENT, investigationProxyVO);
        if(patientPersonVO != null)
        {
            String pFirstName = "";
            String pLastName = "";
            request.setAttribute("patientPersonUid", patientPersonVO.getThePersonDT().getPersonUid());
            PersonNameDT patientPersonNameDT = this.getPersonNameDT(NEDSSConstants.LEGAL_NAME, patientPersonVO);
            request.setAttribute("personUID", String.valueOf(patientPersonVO.getThePersonDT().getPersonUid()));
            request.setAttribute("personLocalID", patientPersonVO.getThePersonDT().getLocalId());
            if(patientPersonNameDT != null)
            {
                String strPatientName = ((patientPersonNameDT.getLastNm() == null) ? "" : patientPersonNameDT.getLastNm()) + " " + ((patientPersonNameDT.getFirstNm() == null) ? "" : patientPersonNameDT.getFirstNm());
                request.setAttribute("patientName", strPatientName);
                request.setAttribute("patientDateOfBirth", this.formatDate(patientPersonVO.getThePersonDT().getBirthTime()));
                request.setAttribute("patientCurrentSex", patientPersonVO.getThePersonDT().getCurrSexCd());
                pFirstName = patientPersonNameDT.getFirstNm();
                pLastName = patientPersonNameDT.getLastNm();
                if(patientPersonVO.getThePersonDT().getBirthTime()!=null)
                {
                  if (DateUtil.lessThanMonthAge(patientPersonVO.getThePersonDT().
                                                getBirthTime()) <= 6)
                    request.setAttribute("ageSixDays", "T");
                }
                else if(patientPersonVO.getThePersonDT().getBirthTimeCalc()!=null)
                {
                  if (DateUtil.lessThanMonthAge(patientPersonVO.getThePersonDT().
                                                getBirthTimeCalc()) <= 6)
                    request.setAttribute("ageSixDays", "T");
                }
                else
                {
                  request.setAttribute("ageSixDays", "F");
                }
                //this value is also set when patient has supplimental question for groupB-strep look at setSuplimentalObseravationsToFormOnLoad() method
            }
        }
        StringBuffer sb = new StringBuffer();
        PersonVO investigatorPersonVO = this.getPersonVO(NEDSSConstants.PHC_INVESTIGATOR, investigationProxyVO);  // "PHC Investigator"
        logger.debug("convertProxyToRequestObj() before if investigatorPersonVO: " + investigatorPersonVO);


        String displayString="";

        //Investigator
		if (investigatorPersonVO != null) {

			request.setAttribute("investigatorUID", investigatorPersonVO
					.getThePersonDT().getPersonUid());
			displayString = helper.makePRVDisplayString(investigatorPersonVO);
				request.setAttribute("investigatorDemographics", displayString);
				request.setAttribute("investigator.personUid-values", displayString);


		}else {
			request.setAttribute("investigatorDemographics", "There is no Investigator selected.");
			request.setAttribute("investigator.personUid-values", "There is no Investigator selected.");
		}

        //ABCs investigator, new for version 1.1.3

		PersonVO abcInvestigatorPersonVO = this.getPersonVO(NEDSSConstants.ABC_PHC_INVESTIGATOR, investigationProxyVO);
        if(abcInvestigatorPersonVO != null) {
			request.setAttribute("abcInvestigatorUid", abcInvestigatorPersonVO
					.getThePersonDT().getPersonUid());

			displayString = helper.makePRVDisplayString(abcInvestigatorPersonVO);

			request.setAttribute("abcInvestigatorDemographics", displayString);
			request.setAttribute("abcInvestigator.personUid-values", displayString);

        }else {
			request.setAttribute("abcInvestigatorDemographics", "There is no ABCs Investigator selected.");
			request.setAttribute("abcInvestigator.personUid-values", "There is no ABCs Investigator selected.");
		}

        //Reporter

        PersonVO reportingPersonVO = this.getPersonVO(NEDSSConstants.PHC_REPORTER, investigationProxyVO);
        if(reportingPersonVO != null)
        {
			request.setAttribute("reporterPersonUid", reportingPersonVO
					.getThePersonDT().getPersonUid());

			displayString = helper.makePRVDisplayString(reportingPersonVO);

			request.setAttribute("reporterDemographics", displayString);
			request.setAttribute("reporter.personUid-values", displayString);

        }else {
			request.setAttribute("reporterDemographics", "There is no Reporter selected.");
			request.setAttribute("reporter.personUid-values", "There is no Reporter selected.");
		}

        //Physician

        PersonVO physicianPersonVO = getPersonVO(NEDSSConstants.PHC_PHYSICIAN, investigationProxyVO);
        if(physicianPersonVO != null)
        {
			request.setAttribute("physicianPersonUid", physicianPersonVO
					.getThePersonDT().getPersonUid());

			displayString = helper.makePRVDisplayString(physicianPersonVO);

			request.setAttribute("physicianDemographics", displayString);
			request.setAttribute("physician.personUid-values", displayString);

        }else {
			request.setAttribute("physicianDemographics", "There is no Physician selected.");
			request.setAttribute("physician.personUid-values", "There is no Physician selected.");
		}

        //Maternal (Retain as same)
        PersonVO maternalPersonVO = getPersonVO(NEDSSConstants.MaternalPSN, investigationProxyVO);
        if(maternalPersonVO != null)
        {
            request.setAttribute("maternalPersonUid.personUid", maternalPersonVO.getThePersonDT().getPersonUid());
            request.setAttribute("maternalLocalID", maternalPersonVO.getThePersonDT().getLocalId());
            PersonNameDT maternalPersonNameDT = this.getPersonNameDT(NEDSSConstants.LEGAL_NAME, maternalPersonVO);
            if(maternalPersonNameDT != null)
            {
                request.setAttribute("maternalLastName", maternalPersonNameDT.getLastNm());
                request.setAttribute("maternalFirstName", maternalPersonNameDT.getFirstNm());
            }
        }

    }


}
