package gov.cdc.nedss.webapp.nbs.action.investigation.util.nip.pertussis;



import java.util.*;

import javax.servlet.http.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.webapp.nbs.action.investigation.common.CommonInvestigationUtil;
import gov.cdc.nedss.webapp.nbs.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;




import gov.cdc.nedss.webapp.nbs.action.investigation.common.FormQACode;

/**
 * Title:        PertussisInvestigationUtil is a class
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Jay Kim
 * @version 1.0
 */

 public class PertussisInvestigationUtil extends CommonInvestigationUtil
  {

     static final LogUtils logger = new LogUtils(PertussisInvestigationUtil.class.getName());

     /**
      * This is constructor
      *
      */
     public PertussisInvestigationUtil()
     {
     }

    /**
      * Get values from request object and put it to appropriate object
      * @param InvestigationProxyVO the investigationProxyVO
      * @param HttpServletRequest the request
      */
     public void setGenericRequestForView(InvestigationForm investigationForm,
                                          HttpServletRequest request)
     {

         InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();
         this.convertPublicHealthCaseToRequest(investigationProxyVO, request);
         this.convertPersonsToRequest(investigationProxyVO, request);
         this.convertOrganizationToRequest(investigationProxyVO, request);
         this.convertObservationsToRequest(investigationProxyVO, request);
         this.convertNotificationSummaryToRequest(investigationProxyVO, request);
         this.convertObservationSummaryToRequest(investigationProxyVO, request);
         this.convertVaccinationSummaryToRequest(investigationProxyVO, request);
         this.convertTreatmentSummaryToRequest(investigationProxyVO, request);

         this.setBatchEntryToFormForView(investigationForm);
         this.convertBatchEntryToRequest(investigationForm, request);
         this.convertDocumentSummaryToRequest(investigationProxyVO, request);
         
         // get the person uid from object store
         HttpSession session = request.getSession(false);
         //Long personUID = (Long)NBSContext.retrieve(session, "DSPatientPersonUID");
         getVaccinationSummaryRecords(getPersonVO(NEDSSConstants.PHC_PATIENT, investigationProxyVO).getThePersonDT().getPersonUid(), request);

         //this.getVaccinationSummaryRecords(personUID,request);
         request.setAttribute("PertussisDeathWorkSheet", getUrlFromDatabase("PERDTH"));
     }










     /**
    * set batch enrty value from InvestigationForm object
    * @param InvestigationForm the investigationForm
    */
      private void setBatchEntryToFormForView(InvestigationForm investigationForm)
      {
          InvestigationProxyVO investigationProxyVO = investigationForm.getOldProxy();
          logger.debug("inside setBatchEntryToForm");
          Collection<Object>  antiBatchEntryCollection  = new ArrayList<Object> ();
          Collection<Object>  sourceBatchEntryCollection  = new ArrayList<Object> ();

          Collection<Object>  arCollectionForAntibiotic = null;
          Collection<Object>  arCollectionForSource = null;
          ObservationVO obsVO = null;
          ActRelationshipDT actRelationshipDT = null;
          Collection<ObservationVO>  observationVOCollection  = investigationProxyVO.getTheObservationVOCollection();
         Iterator<ObservationVO>  anIterator = observationVOCollection.iterator();
          while(anIterator.hasNext())
          {

              obsVO = (ObservationVO)anIterator.next();
              if(obsVO.getTheObservationDT().getCtrlCdDisplayForm() != null && obsVO.getTheObservationDT().getCtrlCdDisplayForm().equals(NEDSSConstants.ANTIBIOTIC_CONTAIN_ROW))
              {

                 TreeMap<Object,Object> obsTreeMap = new TreeMap<Object,Object>();
                 arCollectionForAntibiotic = obsVO.getTheActRelationshipDTCollection();
                 obsTreeMap.put(obsVO.getTheObservationDT().getCtrlCdDisplayForm(), obsVO);
                Iterator<Object>  arItor = arCollectionForAntibiotic.iterator();
                 while(arItor.hasNext())
                 {
                    actRelationshipDT = (ActRelationshipDT)arItor.next();
                    ObservationVO itemObs = this.getObservationByUid(observationVOCollection, actRelationshipDT.getSourceActUid());
                    logger.debug("....antibiotic itemObs : " + (itemObs == null ? "" : itemObs.getTheObservationDT().getCd()));
                    if(itemObs.getTheObservationDT().getCd() != null)
                    obsTreeMap.put(itemObs.getTheObservationDT().getCd(), itemObs);
                 }

                 BatchEntryHelper antiBatchEntryHelper = new BatchEntryHelper();
                 antiBatchEntryHelper.setObsTreeMap(obsTreeMap);
                 antiBatchEntryHelper.setUid(obsVO.getTheObservationDT().getObservationUid());
                 antiBatchEntryHelper.setTreeMap(super.mapBatchEntryQAValue(obsTreeMap.values()));
                 antiBatchEntryHelper.setStatusCd("A");
                 antiBatchEntryCollection.add(antiBatchEntryHelper);


              }
              else if(obsVO.getTheObservationDT().getCtrlCdDisplayForm() != null && obsVO.getTheObservationDT().getCtrlCdDisplayForm().equals(NEDSSConstants.SOURCE_CONTAIN_ROW))
              {
                 TreeMap<Object,Object> obsTreeMap = new TreeMap<Object, Object>();
                 arCollectionForAntibiotic = obsVO.getTheActRelationshipDTCollection();
                 obsTreeMap.put(obsVO.getTheObservationDT().getCtrlCdDisplayForm(), obsVO);
                Iterator<Object>  arItor = arCollectionForAntibiotic.iterator();
                 while(arItor.hasNext())
                 {
                    actRelationshipDT = (ActRelationshipDT)arItor.next();
                    ObservationVO itemObs = this.getObservationByUid(observationVOCollection, actRelationshipDT.getSourceActUid());
                    logger.debug("....source itemObs : " + (itemObs == null ? "" : itemObs.getTheObservationDT().getCd()));
                    if(itemObs.getTheObservationDT().getCd() != null)
                      obsTreeMap.put(itemObs.getTheObservationDT().getCd(), itemObs);
                 }
                 BatchEntryHelper sourceBatchEntryHelper = new BatchEntryHelper();
                 sourceBatchEntryHelper.setObsTreeMap(obsTreeMap);
                 sourceBatchEntryHelper.setUid(obsVO.getTheObservationDT().getObservationUid());
                 sourceBatchEntryHelper.setTreeMap(super.mapBatchEntryQAValue(obsTreeMap.values()));
                 sourceBatchEntryHelper.setStatusCd("A");
                 sourceBatchEntryCollection.add(sourceBatchEntryHelper);

              }

          }
          if(antiBatchEntryCollection.size() > 0)
              investigationForm.setAntibioticBatchEntryCollection(antiBatchEntryCollection);
          else
             logger.debug("no antibiotics found for this investigation");

          if(sourceBatchEntryCollection.size() > 0)
              investigationForm.setSourceBatchEntryCollection(sourceBatchEntryCollection);
          else
             logger.debug("no source of infection found for this investigation");
      }

    private TreeMap<Object,Object> mapSourceBatchEntryQA(Collection<Object>  antObservationVOCollection)
    {
      TreeMap<Object,Object> treeMap = new TreeMap<Object, Object>();
      Collection<Object>  obsValueColl = null;
     Iterator<Object>  subItor = null;
      ObservationVO obsVO = null;
     Iterator<Object>  itor = antObservationVOCollection.iterator();
      while(itor.hasNext())
      {
         obsVO = (ObservationVO)itor.next();
         if(obsVO.getTheObservationDT().getCd().equals("PRT074"))
         {
           obsValueColl = obsVO.getTheObsValueNumericDTCollection();
           if(obsValueColl != null)
           {
             subItor = obsValueColl.iterator();
             if(subItor.hasNext())
             {
               ObsValueNumericDT obsValueNum = (ObsValueNumericDT)subItor.next();
               treeMap.put("PRT074", obsValueNum.getNumericValue1());
             }

           }
         }
         else if(obsVO.getTheObservationDT().getCd().equals("PRT075"))
         {
           obsValueColl = obsVO.getTheObsValueCodedDTCollection();
           if(obsValueColl != null)
           {
             subItor = obsValueColl.iterator();
             if(subItor.hasNext())
             {
               ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)subItor.next();
               treeMap.put("PRT075", obsValueCodedDT.getCode());
             }

           }
         }
         else if(obsVO.getTheObservationDT().getCd().equals("PRT076"))
         {
           obsValueColl = obsVO.getTheObsValueCodedDTCollection();
           if(obsValueColl != null)
           {
             subItor = obsValueColl.iterator();
             if(subItor.hasNext())
             {
               ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)subItor.next();
               treeMap.put("PRT076", obsValueCodedDT.getCode());
             }

           }
         }
         else if(obsVO.getTheObservationDT().getCd().equals("PRT077"))
         {
           obsValueColl = obsVO.getTheObsValueCodedDTCollection();
           if(obsValueColl != null)
           {
             subItor = obsValueColl.iterator();
             if(subItor.hasNext())
             {
               ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)subItor.next();
               treeMap.put("PRT077", obsValueCodedDT.getCode());
             }

           }
         }
         else if(obsVO.getTheObservationDT().getCd().equals("PRT078"))
         {
           obsValueColl = obsVO.getTheObsValueTxtDTCollection();
           if(obsValueColl != null)
           {
             subItor = obsValueColl.iterator();
             if(subItor.hasNext())
             {
               ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT)subItor.next();
               treeMap.put("PRT078", obsValueTxtDT.getValueTxt());
             }

           }
         }
         else if(obsVO.getTheObservationDT().getCd().equals("PRT087"))
         {
           obsValueColl = obsVO.getTheObsValueCodedDTCollection();
           if(obsValueColl != null)
           {
             subItor = obsValueColl.iterator();
             if(subItor.hasNext())
             {
               ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)subItor.next();
               treeMap.put("PRT087", obsValueCodedDT.getCode());
             }

           }
         }

         else if(obsVO.getTheObservationDT().getCd().equals("PRT088"))
         {
           obsValueColl = obsVO.getTheObsValueNumericDTCollection();
           if(obsValueColl != null)
           {
             subItor = obsValueColl.iterator();
             if(subItor.hasNext())
             {
               ObsValueDateDT obsValueDate = (ObsValueDateDT)subItor.next();
               treeMap.put("PRT088", obsValueDate.getFromTime());
             }

           }
         }

      }

      return treeMap;
    }

   /**
    * Get BatchEntry values from investigationForm object and put it to request object
    * @param InvestigationProxyVO the investigationProxyVO
    * @param HttpServletRequest the request
    */
      private void convertBatchEntryToRequest(InvestigationForm investigationForm,
                                              HttpServletRequest request)
      {


          if(investigationForm.getAntibioticBatchEntryCollection() == null)
          logger.debug("AntibioticBatchEntryCollection  is null");
          else
          {
              Collection<Object>  antibioticBatchEntryCollection  = investigationForm.getAntibioticBatchEntryCollection();
              StringBuffer antibioticBatchEntryList = new StringBuffer("");
             Iterator<Object>  itr = antibioticBatchEntryCollection.iterator();
              while(itr.hasNext())
              {
                  BatchEntryHelper antibioticBatchEntry = (BatchEntryHelper) itr.next();
                  if(antibioticBatchEntry.getTreeMap() != null)
                  {
                      logger.debug("antibioticBatchEntry: " + antibioticBatchEntry.getTreeMap());

                      antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[0].obsValueCodedDT_s[0].code");
                      antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(antibioticBatchEntry.getTreeMap().get("PRT021"))).append(NEDSSConstants.BATCH_SECT);
                      antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[1].obsValueDateDT_s[0].fromTime_s");
                      antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(this.formatDateObj(antibioticBatchEntry.getTreeMap().get("PRT023"))).append(NEDSSConstants.BATCH_SECT);
                      antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[2].obsValueNumericDT_s[0].numericValue1_s");
                      antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.integerValue((antibioticBatchEntry.getTreeMap().get("PRT024")))).append(NEDSSConstants.BATCH_SECT);
                      antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[0].theObservationDT.cd");
                      antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("PRT021").append(NEDSSConstants.BATCH_SECT);
                      antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[1].theObservationDT.cd");
                      antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("PRT023").append(NEDSSConstants.BATCH_SECT);
                      antibioticBatchEntryList.append("antibioticBatchEntry_s[i].observationVO_s[2].theObservationDT.cd");
                      antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("PRT024").append(NEDSSConstants.BATCH_SECT);
                      antibioticBatchEntryList.append("antibioticBatchEntry_s[i].statusCd");
                      antibioticBatchEntryList.append(NEDSSConstants.BATCH_PART).append("A").append(NEDSSConstants.BATCH_SECT);
                      antibioticBatchEntryList.append(NEDSSConstants.BATCH_LINE);
                  }

              }
              //System.out.println("antibioticBatchEntryList for display is: " + antibioticBatchEntryList.toString());
              request.setAttribute("antibioticBatchEntryList", antibioticBatchEntryList.toString());
            }

          if(investigationForm.getSourceBatchEntryCollection() == null)
          logger.debug("SourceBatchEntryCollection  is null");
          else
          {
              Collection<Object>  sourceBatchEntryCollection  = investigationForm.getSourceBatchEntryCollection();
              StringBuffer sourceBatchEntryList = new StringBuffer("");
             Iterator<Object>  itr = sourceBatchEntryCollection.iterator();
              int i = 1;
              while(itr.hasNext())
              {
                  BatchEntryHelper sBatch = (BatchEntryHelper) itr.next();
                  if(sBatch.getTreeMap() != null)
                  {
                      logger.debug("sourceBatchEntry: " + sBatch.getTreeMap());

                      sourceBatchEntryList.append("Number");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append(i++).append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[0].obsValueNumericDT_s[0].numericValue1_s");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.integerValue(sBatch.getTreeMap().get("PRT074"))).append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[1].obsValueCodedDT_s[0].code");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(sBatch.getTreeMap().get("PRT075"))).append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[2].obsValueCodedDT_s[0].code");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(sBatch.getTreeMap().get("PRT076"))).append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[3].obsValueCodedDT_s[0].code");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(sBatch.getTreeMap().get("PRT077"))).append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[4].obsValueTxtDT_s[0].valueTxt");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(sBatch.getTreeMap().get("PRT078"))).append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[5].obsValueCodedDT_s[0].code");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.replaceNull(sBatch.getTreeMap().get("PRT087"))).append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[6].obsValueDateDT_s[0].fromTime_s");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append(super.formatDateObj(sBatch.getTreeMap().get("PRT088"))).append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[0].theObservationDT.cd");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append("PRT074").append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[1].theObservationDT.cd");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append("PRT075").append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[2].theObservationDT.cd");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append("PRT076").append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[3].theObservationDT.cd");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append("PRT077").append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[4].theObservationDT.cd");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append("PRT078").append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[5].theObservationDT.cd");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append("PRT087").append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append("sourceBatchEntry_s[i].observationVO_s[6].theObservationDT.cd");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append("PRT088").append(NEDSSConstants.BATCH_SECT);

                      sourceBatchEntryList.append("sourceBatchEntry_s[i].statusCd");
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_PART).append("A").append(NEDSSConstants.BATCH_SECT);
                      sourceBatchEntryList.append(NEDSSConstants.BATCH_LINE);
                  }

              }
              logger.debug("sourceBatchEntryList for display is: " + sourceBatchEntryList.toString());
              request.setAttribute("sourceBatchEntryList", sourceBatchEntryList.toString());
            }
      }



      /**
      * Set Observation constant from investigationProxyVO object
      * @param InvestigationProxyVO  the investigationProxyVO
      */
      public static void setObservationConstantsForCreate(InvestigationProxyVO investigationProxyVO)
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
                  if(obsCode !=null && obsCode.equals("INV128")){
                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      observationVO.getTheObservationDT().setObsDomainCd("CLN");
                      continue;
                  }
                  logger.debug("obsCodeOuterLoop :"+obsCode);
                  if(obsCode != null && obsCode.equals("PRT001"))
                  {
                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueDateDTCollection  = observationVO.getTheObsValueDateDTCollection();
                      if(obsValueDateDTCollection  != null)
                      {
                         anIterator2 = obsValueDateDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             //ObsValueDateDT obsValueDateDT = ((ObsValueDateDT)anIterator2.next());
                             //obsValueDateDT.setDurationAmt("PRT001");
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                             obsValueCodedDT.setCodeSystemCd("NBS");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT005"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT006"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT007"))
                  {
                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);

                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT008"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT011"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                             //obsValueCodedDT.setCode("");
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT012"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT013"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                             //obsValueCodedDT.setCode("");
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT020"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT029"))
                  {

                     Collection<Object>  obsValueTxtDTCollection  = observationVO.getTheObsValueTxtDTCollection();
                      if(obsValueTxtDTCollection  != null)
                      {
                         anIterator2 = obsValueTxtDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                            ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             //obsValueCodedDT.setCodeSystemDescTxt(NEDSSConstants.YNU);
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                          }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT089"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             logger.debug("The code stored is :"+obsValueCodedDT.getCode());
                              //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT090"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT091"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT092"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT081"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT084"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }

                   if(obsCode != null && obsCode.equals("PRT060"))
                  {
                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                   if(obsCode != null && obsCode.equals("PRT067"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT070"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  logger.debug("obsCode value here is :"+obsCode);
                  if(obsCode != null && obsCode.equals("PRT071"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
                  if(obsCode != null && obsCode.equals("PRT087"))
                  {

                      observationVO.getTheObservationDT().setCdSystemCd("NBS");
                      Collection<Object>  obsValueCodedDTCollection  = observationVO.getTheObsValueCodedDTCollection();
                      if(obsValueCodedDTCollection  != null)
                      {
                         anIterator2 = obsValueCodedDTCollection.iterator();
                         if(anIterator2.hasNext())
                         {
                             ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)anIterator2.next());
                             obsValueCodedDT.setCodeSystemCd("NBS");
                             //obsValueCodedDT.setCodeVersion("1");
                         }
                      }
                      continue;
                  }
               }
            }
      }//setObsevationCodesForCreate


      /**
      * Access to get OrganizationVO  from Vaccination ProxyVO object
      * @param String  the type_cd
      * @param VaccinationProxyVO the vVO
      * @return OrganizationVO object
      */
/**      private OrganizationVO getOrganizationVO(String type_cd, VaccinationProxyVO vVO)
      {

          Collection<Object>  participationDTCollection  = null;
          Collection<Object>  organizationVOCollection  = null;
          ParticipationDT participationDT = null;
          OrganizationVO organizationVO = null;
          InterventionVO iVO = vVO.getTheInterventionVO();
          participationDTCollection  = iVO.getTheParticipationDTCollection();
          organizationVOCollection  = vVO.getTheOrganizationVOCollection();

          if (participationDTCollection  != null && organizationVOCollection  != null)
          {

             Iterator<Object>  anIterator1 = null;
             Iterator<Object>  anIterator2 = null;

              for (anIterator1 = participationDTCollection.iterator(); anIterator1.hasNext();)
              {
                  participationDT = (ParticipationDT)anIterator1.next();

                  if (participationDT.getTypeCd() != null &&
                      (participationDT.getTypeCd().trim()).equals(type_cd))
                  {

                      for (anIterator2 = organizationVOCollection.iterator(); anIterator2.hasNext();)
                      {
                          organizationVO = (OrganizationVO)anIterator2.next();

                          if (organizationVO.getTheOrganizationDT().getOrganizationUid().longValue() == participationDT
                                         .getSubjectEntityUid().longValue())
                          {

                              return organizationVO;
                          }
                          else

                              continue;
                      }
                  }
                  else

                      continue;
              }
          }

          return null;
      }

*/

      /**
    * set batch enrty observation value edit from InvestigationForm object with tempID
    * @param InvestigationForm the investigationForm
    * @param int the tempID
    * @param String the investigationFormCd
    */
      public int setBatchEntryObseravationsForCreate(InvestigationForm investigationForm,  HttpServletRequest request, int tempID, String investigationFormCd)
      {


           Collection<Object>  itemActRelationships = null;
           ActRelationshipDT actRelationshipDT = null;

           Collection<ObservationVO>  observationVOCollection  = investigationForm.getProxy().getTheObservationVOCollection();
           Collection<Object>  actRelationshipDTCollection  = investigationForm.getProxy().getTheActRelationshipDTCollection();
           ObservationVO formObservationVO = this.getObservationVO(investigationForm.getProxy(), investigationFormCd);

           logger.info(" .main ObservationVOCollection  size: " + ((observationVOCollection  == null) ? "0" : String.valueOf(observationVOCollection.size())) + " .main actRelationshipDTCollection  size: " + ((actRelationshipDTCollection  == null) ? "0" : String.valueOf(actRelationshipDTCollection.size())));

           Collection<Object>  antiBatchEntryCollection  = investigationForm.getAntibioticBatchEntryCollection();
           logger.info(" ..AntibioticBatchEntryCollection  size: " + ((antiBatchEntryCollection  == null) ? "0" : String.valueOf(antiBatchEntryCollection.size())));
           if(antiBatchEntryCollection  != null)
           {
              Iterator<Object>  anIterator = antiBatchEntryCollection.iterator();
               while(anIterator.hasNext())
               {
                   ObservationVO rowObservationVO = null;
                   BatchEntryHelper aBatch = (BatchEntryHelper)anIterator.next();
                   Collection<ObservationVO>  antibioticObservations = aBatch.getObservationVOCollection();
                   if(antibioticObservations != null && aBatch.getStatusCd()!=null && aBatch.getStatusCd().trim().equals("A"))
                   {

                     TreeMap<Object,Object> treeMap = super.setBatchObservations(aBatch, formObservationVO,NEDSSConstants.ANTIBIOTIC_CONTAIN_ROW, request, tempID);
                     observationVOCollection.addAll((ArrayList<ObservationVO> )(treeMap.get("observations")));
                     actRelationshipDTCollection.addAll((ArrayList<Object> )(treeMap.get("actrelations")));
                     tempID = ((Integer)treeMap.get("tempID")).intValue();

/**                      logger.info(" ...Antibiotic itemObservations size: " + ((antibioticObservations == null) ? "0" : String.valueOf( antibioticObservations.size())));
                      rowObservationVO = this.rowObservation(NEDSSConstants.ANTIBIOTIC_CONTAIN_ROW);
                      rowObservationVO.getTheObservationDT().setObservationUid(new Long(tempID--));
                      observationVOCollection.add(rowObservationVO);
                      tempID = this.setTempID(antibioticObservations, tempID);
                      antibioticObservations = this.removeEmptyObservations(antibioticObservations);
                      observationVOCollection.addAll(antibioticObservations);
                      actRelationshipDT = this.setRowActRelationshipForCreate(rowObservationVO, formObservationVO);
                      itemActRelationships = this.setItemToRowAndFormActRelationshipForCreate(antibioticObservations, rowObservationVO, formObservationVO);
                      actRelationshipDTCollection.add(actRelationshipDT);
                      actRelationshipDTCollection.addAll(itemActRelationships);**/
                   }

               }
           }
           logger.info(" .main ObservationVOCollection  after adding Antibiotic batchEntry size: " + ((observationVOCollection  == null) ? "0" : String.valueOf( observationVOCollection.size())) +  " after adding item actRelationshipDTCollection  size: " + ((actRelationshipDTCollection  == null) ? "0" : String.valueOf(actRelationshipDTCollection.size())));

           Collection<Object>  SourcebatchEntryCollection  = investigationForm.getSourceBatchEntryCollection();

           logger.info(" ..SourceBatchEntryCollection  size: " + ((SourcebatchEntryCollection  == null) ? "0" : String.valueOf(SourcebatchEntryCollection.size())));
           if(SourcebatchEntryCollection  != null)
           {
               BatchEntryHelper sBatch = null;
              Iterator<Object>  anIterator = SourcebatchEntryCollection.iterator();
               while(anIterator.hasNext())
               {

                   sBatch = (BatchEntryHelper)anIterator.next();
                   TreeMap<Object,Object> treeMap = super.setBatchObservations(sBatch, formObservationVO,NEDSSConstants.SOURCE_CONTAIN_ROW, request, tempID);
                   observationVOCollection.addAll((ArrayList<ObservationVO> )(treeMap.get("observations")));
                   actRelationshipDTCollection.addAll((ArrayList<?> )(treeMap.get("actrelations")));
                   tempID = ((Integer)treeMap.get("tempID")).intValue();

/**                Collection<Object>  sourceObservations = sourceBatchEntryHelper.getObservationVOCollection();
                   if(sourceObservations != null)
                   {
                      logger.info(" ...Source itemObservations size: " + ((sourceObservations == null) ? "0" : String.valueOf(sourceObservations.size())));
                      rowObservationVO = this.rowObservation(NEDSSConstants.SOURCE_CONTAIN_ROW);
                      rowObservationVO.getTheObservationDT().setObservationUid(new Long(tempID--));
                      observationVOCollection.add(rowObservationVO);
                      tempID = this.setTempID(sourceObservations, tempID);
                      observationVOCollection.addAll(sourceObservations);
                      actRelationshipDT = this.setRowActRelationshipForCreate(rowObservationVO, formObservationVO);
                      itemActRelationships = this.setItemToRowAndFormActRelationshipForCreate(sourceObservations, rowObservationVO, formObservationVO);
                      actRelationshipDTCollection.add(actRelationshipDT);
                      actRelationshipDTCollection.addAll(itemActRelationships);
                   }**/


               }
           }
           logger.info(" .main ObservationVOCollection  after adding batchEntry size: " + ((observationVOCollection  == null) ? "0" : String.valueOf( observationVOCollection.size())) +  " after adding item actRelationshipDTCollection  size: " + ((actRelationshipDTCollection  == null) ? "0" : String.valueOf(actRelationshipDTCollection.size())));
          Iterator<Object>  actIterator = actRelationshipDTCollection.iterator();
           ActRelationshipDT itorActRelationshipDT = null;
           while(actIterator.hasNext())
           {
               itorActRelationshipDT = (ActRelationshipDT)actIterator.next();
               logger.debug(" actRelationship: t s td: " + itorActRelationshipDT.getTargetActUid() + " " + itorActRelationshipDT.getSourceActUid() + " " + itorActRelationshipDT.getTypeCd());
           }
          return tempID;
      }
    private Collection<Object>  removeEmptyObservations(Collection<Object>  antibioticObservations, HttpServletRequest request)
    {
      Collection<Object>  newColl = new ArrayList<Object> ();
     Iterator<Object>  itor = antibioticObservations.iterator();
      ObservationVO obsVO = null;
      while(itor.hasNext())
      {
         obsVO = (ObservationVO)itor.next();
         boolean check = super.checkForEmptyObs(obsVO, request);
         if(check) newColl.add(obsVO);
      }

      return newColl;
    }
   /**
    * set batch enrty observation value edit from InvestigationForm object with code
    * @param InvestigationForm the investigationForm
    * @param String the investigationFormCd
    */
      public int setBatchEntryObseravationsForEdit(InvestigationForm investigationForm, String investigationFormCd, HttpServletRequest request, int tempID)
      {

           Collection<Object>  itemActRelationships = null;
           ActRelationshipDT actRelationshipDT = null;

           Collection<ObservationVO>  observationVOCollection  = investigationForm.getProxy().getTheObservationVOCollection();
           Collection<Object>  actRelationshipDTCollection  = investigationForm.getProxy().getTheActRelationshipDTCollection();
           if(actRelationshipDTCollection  == null)
           actRelationshipDTCollection  = new ArrayList<Object> ();
           investigationForm.getProxy().setTheActRelationshipDTCollection(actRelationshipDTCollection);
           ObservationVO formObservationVO = this.getObservationVO(investigationForm.getProxy(), investigationFormCd);
           {
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
           }
           logger.debug(" .main ObservationVOCollection  after adding Antibiotic batchEntry size: " + ((observationVOCollection  == null) ? "0" : String.valueOf( observationVOCollection.size())) +  " after adding item actRelationshipDTCollection  size: " + ((actRelationshipDTCollection  == null) ? "0" : String.valueOf(actRelationshipDTCollection.size())));

           Collection<Object>  SourcebatchEntryCollection  = investigationForm.getSourceBatchEntryCollection();
           logger.debug(" edit ..SourcebatchEntryCollection  size: " + ((SourcebatchEntryCollection  == null) ? "0" : String.valueOf(SourcebatchEntryCollection.size())));
           if(SourcebatchEntryCollection  != null)
           {
               TreeMap<Object,Object> sTreeMap = null;
              Iterator<Object>  anIterator = SourcebatchEntryCollection.iterator();
               while(anIterator.hasNext())
               {
                   ObservationVO rowObservationVO = null;
                   BatchEntryHelper sourceBatchEntryHelper = (BatchEntryHelper)anIterator.next();
                   sTreeMap = super.setBatchObservations(sourceBatchEntryHelper, formObservationVO,NEDSSConstants.SOURCE_CONTAIN_ROW, request, tempID);
                   observationVOCollection.addAll((ArrayList<ObservationVO> )(sTreeMap.get("observations")));
                   actRelationshipDTCollection.addAll((ArrayList<?> )(sTreeMap.get("actrelations")));
                   tempID = ((Integer)sTreeMap.get("tempID")).intValue();

               }
           }
           logger.info(" .main ObservationVOCollection  after adding Antibiotic batchEntry size: " + ((observationVOCollection  == null) ? "0" : String.valueOf( observationVOCollection.size())) +  " after adding item actRelationshipDTCollection  size: " + ((actRelationshipDTCollection  == null) ? "0" : String.valueOf(actRelationshipDTCollection.size())));
            /**Collection<Object>  SourcebatchEntryCollection  = investigationForm.getSourceBatchEntryCollection();
           BatchEntryHelper sourceBatchEntryHelper = null;
           logger.info(" ..SourceBatchEntryCollection  size: " + ((SourcebatchEntryCollection  == null) ? "0" : String.valueOf(SourcebatchEntryCollection.size())));
           if(SourcebatchEntryCollection  != null)
           {
              Iterator<Object>  anIterator = SourcebatchEntryCollection.iterator();
               while(anIterator.hasNext())
               {
                   ObservationVO rowObservationVO = null;
                   sourceBatchEntryHelper = (BatchEntryHelper)anIterator.next();
                   Collection<Object>  sourceObservations = sourceBatchEntryHelper.getObservationVOCollection();
                   if(sourceObservations != null)
                   {
                      logger.info(" ...Source itemObservations size: " + ((sourceObservations == null) ? "0" : String.valueOf(sourceObservations.size())));
                      boolean newSourceRowFlag = false;
                     Iterator<Object>  itemSourceIterator = sourceObservations.iterator();
                      ObservationVO sourceItemObsVO = null;
                      while(itemSourceIterator.hasNext())
                      {
                         sourceItemObsVO = (ObservationVO)itemSourceIterator.next();
                         if(sourceItemObsVO.getTheObservationDT().getObservationUid() == null)
                         {
                             newSourceRowFlag = true;
                             sourceItemObsVO.setItNew(true);
                             sourceItemObsVO.setItDirty(false);
                             sourceItemObsVO.getTheObservationDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                             sourceItemObsVO.getTheObservationDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                             sourceItemObsVO.getTheObservationDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                             sourceItemObsVO.getTheObservationDT().setItNew(true);
                             sourceItemObsVO.getTheObservationDT().setItDirty(false);
                             sourceItemObsVO.getTheObservationDT().setObservationUid(new Long(tempID--));

                         }
                      }
                      if(newSourceRowFlag)
                      {
                          rowObservationVO = this.rowObservation(NEDSSConstants.SOURCE_CONTAIN_ROW);
                          rowObservationVO.getTheObservationDT().setObservationUid(new Long(tempID--));
                          observationVOCollection.add(rowObservationVO);
                          observationVOCollection.addAll(sourceObservations);
                          actRelationshipDT = this.setRowActRelationshipForCreate(rowObservationVO, formObservationVO);
                          itemActRelationships = this.setItemToRowAndFormActRelationshipForCreate(sourceObservations, rowObservationVO, formObservationVO);
                          actRelationshipDTCollection.add(actRelationshipDT);
                          actRelationshipDTCollection.addAll(itemActRelationships);
                      }
                   }
               }
           }
           */
           logger.info(" .main ObservationVOCollection  after adding batchEntry size: " + ((observationVOCollection  == null) ? "0" : String.valueOf( observationVOCollection.size())) +  " after adding item actRelationshipDTCollection  size: " + ((actRelationshipDTCollection  == null) ? "0" : String.valueOf(actRelationshipDTCollection.size())));
          Iterator<Object>  actIterator = actRelationshipDTCollection.iterator();
           ActRelationshipDT itorActRelationshipDT = null;
           while(actIterator.hasNext())
           {
               itorActRelationshipDT = (ActRelationshipDT)actIterator.next();
           }

        return tempID;
      }


      /**
       * this is method to create ObservationVO from display form.
       * @param String the cntrlCdDisplyForm
       * @return ObservationVO object.
       *
       */
      private ObservationVO  rowObservation(String cntrlCdDisplyForm)
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
           rowObservationDT.setItNew(true);
           rowObservationDT.setItDirty(false);

           rowObservationVO.setTheObservationDT(rowObservationDT);
           rowObservationVO.setItNew(true);
           rowObservationVO.setItDirty(false);
           return rowObservationVO;
      }


      /**
       * This method is used to set temp observation with tempid
       * @param Collection<Object>  the itemObservations
       * @param int the tempID
       * @return int
       */
      private int setTempID(Collection<Object>  itemObservations, int tempID)
      {
          ObservationVO obsVO = null;
         Iterator<Object>  anIterator = itemObservations.iterator();
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
                 FormQACode.putQuestionCode(obsVO.getTheObservationDT());
                 super.setNumericUnitCd(obsVO);
                 obsVO.getTheObservationDT().setItNew(true);
                 obsVO.getTheObservationDT().setItDirty(false);
                 obsVO.getTheObservationDT().setObservationUid(new Long(tempID--));
             }
          }
          // sets NI if blank in Obs_value_coded
          this.setObsValueCodedToDefaultValues(itemObservations);
          return tempID;
      }


      /**
       *
       * this method is create ActrelationshipDT with observationVO
       * @param Observation the rowObervation
       * @param ObservationVO the formObservationVO
       * @return ActRelationShipDT
       */
      private ActRelationshipDT setRowActRelationshipForCreate(ObservationVO rowObservationVO,
                                                              ObservationVO formObservationVO)
      {
            ActRelationshipDT rowActRelationshipDT = new ActRelationshipDT();

            rowActRelationshipDT.setSourceActUid(rowObservationVO.getTheObservationDT().getObservationUid());
            rowActRelationshipDT.setSourceClassCd(NEDSSConstants.CLASS_CD_OBS);
            rowActRelationshipDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            rowActRelationshipDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          //  System.out.println("\n@@@@@@@@@@@@@@The ObservationDT is :"+ formObservationVO.getTheObservationDT());
            rowActRelationshipDT.setTargetActUid(formObservationVO.getTheObservationDT().getObservationUid());
            rowActRelationshipDT.setTypeCd(NEDSSConstants.INV_FRM_Q);
            rowActRelationshipDT.setTargetClassCd(NEDSSConstants.CLASS_CD_OBS);
            rowActRelationshipDT.setItNew(true);
            rowActRelationshipDT.setItDirty(false);
            rowActRelationshipDT.setItDelete(false);
            logger.debug("row Observation typeCd: " + rowActRelationshipDT.getTypeCd() + "SourceActUid : " + rowActRelationshipDT.getSourceActUid());

            return rowActRelationshipDT;
      }


      /**
       * this method is create ActrelationshipDT with observationVO and return in collection
       * @param Collection<Object>  the  antibioticObservations
       * @param  ObservationVO the rowObservationVO
       * @param ObservationVO the formObservationVO
       * @return ActRelationShipDT in Collection<Object>  object
       */
      private Collection<Object>  setItemToRowAndFormActRelationshipForCreate(Collection<Object>  antibioticObservations, ObservationVO rowObservationVO, ObservationVO formObservationVO)
      {
            logger.debug("inside setItemToRowActRelationshipForCreate: " + NEDSSConstants.ITEM_TO_ROW );
            Collection<Object>  actRelationshipCollection   = new ArrayList<Object> ();
            CachedDropDownValues srtc = new CachedDropDownValues();

            ActRelationshipDT itemToRowAR = null;
            ActRelationshipDT itemToFormAR = null;
            ObservationVO itemObservationVO = null;
           Iterator<Object>  anIterator =  antibioticObservations.iterator();
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


      /**
      * Access to get OrganizationVO from InvestigationProxyVO object
      * @param InvestigationProxyVO the investigationProxyVO
      * @param String the investigationFormCd
      * @return OrganizationVO
      */
 /**     private ObservationVO getObservationVO(InvestigationProxyVO investigationProxyVO, String investigationFormCd)
      {
          String observationCode = "";
          ObservationVO observationVO = null;
         Iterator<Object>  anIterator = null;
          Collection<Object>  obsCollection  = investigationProxyVO.getTheObservationVOCollection();
          if(obsCollection  != null)
          {
              anIterator = obsCollection.iterator();
              while(anIterator.hasNext())
              {
                   observationVO = (ObservationVO)anIterator.next();
                   if(observationVO.getTheObservationDT().getCd() != null && observationVO.getTheObservationDT().getCd().equals(investigationFormCd))
                   {
                      //System.out.println("$$$^^^getObservation: " + observationVO.getTheObservationDT().getCd());
                      return observationVO;
                   }
              }
          }
          return null;
      }
*/
   /**
    * Access to get observation UID from observationcollection object with observationUID
    * @param Collection<Object>  the theObservationVOCollection
    * @param Long the observationUid
    * @return ObservationVO object
    */
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

      /**
       * this is set default obsvaluecode value in Collection
       * @param Collection<Object>  the observation
       */
      private void setObsValueCodedToDefaultValues(Collection<Object>  observations)
      {
         Iterator<Object>  collItor = observations.iterator();
          if (collItor == null)
          return;

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

      /**
       * this method delete ActRelationShip with Observation
       * @param ObservationVO the formObservationVO
       * @param BatchEntryHelper the batchEntryHelper
       * @return Collection<Object>  witch contains Batch entry values
       */
      private Collection<Object>  deleteRowActRelationshipAndObservations(ObservationVO formObservationVO, BatchEntryHelper batchEntryHelper)
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
              }// end of rowObservationUid if

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
                       }//while of actRelationship
                    }// while of itemObservation
              }// end of itemCollection  if
          }
          formObservationVO.setItDirty(true);
          return actRelationshipDeletedColl;
      }

  /**
    * Get values from InvestigationForm object and put it to request object
    * @param InvestigationProxyVO the investigationProxyVO
    * @param HttpServletRequest the request
    */
     private void convertObservationsToRequest(InvestigationProxyVO investigationProxyVO, HttpServletRequest request)
     {
          Collection<ObservationVO>  obsColl = investigationProxyVO.getTheObservationVOCollection();
          if (obsColl != null)
          {
           Iterator<ObservationVO>  iter = obsColl.iterator();
            while (iter.hasNext())
            {
              ObservationVO obsVO = (ObservationVO) iter.next();

              String obsCode = obsVO.getTheObservationDT().getCd();
              if(obsCode != null)
              {
                //get the coded values
                Collection<Object>  codedColl = obsVO.getTheObsValueCodedDTCollection();
                if(codedColl!=null)
                {
                 Iterator<Object>  codedIter = codedColl.iterator();
                  if(codedIter.hasNext())
                  {
                    ObsValueCodedDT obsValueCodedDT = ((ObsValueCodedDT)codedIter.next());
                    request.setAttribute(obsCode, (obsValueCodedDT.getCode()==null? "":obsValueCodedDT.getCode()));
                    request.setAttribute(obsCode+"-desc", (obsValueCodedDT.getCodeSystemDescTxt()==null? "":obsValueCodedDT.getCodeSystemDescTxt()));
                  }
                }
                //get date
                Collection<Object>  dateColl = obsVO.getTheObsValueDateDTCollection();
                if(dateColl!=null)
                {
                 Iterator<Object>  dateIter = dateColl.iterator();
                  if(dateIter.hasNext())
                  {
                    ObsValueDateDT obsDateCodedDT = ((ObsValueDateDT)dateIter.next());
                    request.setAttribute(obsCode+"-fromTime", (obsDateCodedDT.getFromTime()==null? "":formatDate(obsDateCodedDT.getFromTime())));
                    request.setAttribute(obsCode+"-toTime", (obsDateCodedDT.getToTime()==null? "":formatDate(obsDateCodedDT.getToTime())));
                    request.setAttribute(obsCode+"-durationAmt", (obsDateCodedDT.getDurationAmt()==null? "":obsDateCodedDT.getDurationAmt()));
                    request.setAttribute(obsCode+"-durationUnit", (obsDateCodedDT.getDurationUnitCd()==null? "":obsDateCodedDT.getDurationUnitCd()));
                    logger.debug(obsCode+"FromTime:"+obsDateCodedDT.getFromTime());
                    logger.debug(obsCode+"ToTime:"+obsDateCodedDT.getToTime());
                    logger.debug(obsCode+"FromTime:"+obsDateCodedDT.getDurationAmt());
                    logger.debug(obsCode+"FromTime:"+obsDateCodedDT.getDurationUnitCd());

                  }
                }
                //get text
                Collection<Object>  textColl = obsVO.getTheObsValueTxtDTCollection();
                if(textColl!=null)
                {
                 Iterator<Object>  textIter = textColl.iterator();
                  if(textIter.hasNext())
                  {
                    ObsValueTxtDT obsTextCodedDT = ((ObsValueTxtDT)textIter.next());
                    request.setAttribute(obsCode+"-valueTxt", (obsTextCodedDT.getValueTxt()==null? "":obsTextCodedDT.getValueTxt()));
                    logger.debug("Value text for " +obsCode+ " is :" +obsTextCodedDT.getValueTxt());
                  }
                }
                // get numeric
                Collection<Object>  numColl = obsVO.getTheObsValueNumericDTCollection();
                if(numColl!=null)
                {
                 Iterator<Object>  numIter = numColl.iterator();
                  if(numIter.hasNext())
                  {
                    ObsValueNumericDT obsNumCodedDT = ((ObsValueNumericDT)numIter.next());
                    request.setAttribute(obsCode+"-numeric1", (obsNumCodedDT.getNumericValue1()==null? "":String.valueOf(obsNumCodedDT.getNumericValue1().intValue())));
                    logger.debug("Numeric text for " +obsCode+ " is :" +obsNumCodedDT.getNumericValue1());
                    if(obsCode != null && obsCode.trim().equals("PRT112") && obsNumCodedDT.getNumericValue1()!=null) {
                        int PRT112 = obsNumCodedDT.getNumericValue1().intValue();
                        if(PRT112==-1)
                     	   request.setAttribute("PRT112_unk","T" );
                        
                 }
                  }
                }


              }
            }
          }

     }





       public String getUrlFromDatabase(String code)
       {
          String  result = null;
          CachedDropDownValues val = new CachedDropDownValues();
          result = val.getCodeDescTxt(code);
          return result;
       }

       private TreeMap<Object,Object> mapObservationsQA(Collection<Object>  observationVOcoll)
       {
         TreeMap<Object,Object> treeMap = new TreeMap<Object, Object>();

         for (Iterator<Object> it = observationVOcoll.iterator(); it.hasNext(); )
         {
           ObservationVO obsVO = (ObservationVO) it.next();
           ObservationDT obsDT = obsVO.getTheObservationDT();
           String mappingCd = obsDT.getCd();
           if (obsDT != null && mappingCd != null)
           {
             if (mappingCd.equalsIgnoreCase(obsDT.getCd()))
             {
               treeMap.put(mappingCd, obsVO);
             }
           }
         }
         return treeMap;
       }


  }