  //Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\nnd\\ejb\\nndmessageprocessor\\helpers\\NNDMarshallerSummaryReport.java

package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.act.summaryreport.vo.*;
import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.organization.vo.*;

import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.messageframework.notificationmastermessage.NedssEvent;
import gov.cdc.nedss.messageframework.notificationmastermessage.Entity;
import gov.cdc.nedss.messageframework.notificationmastermessage.Person;
import gov.cdc.nedss.messageframework.notificationmastermessage.Material;
import gov.cdc.nedss.messageframework.notificationmastermessage.Organization;
import gov.cdc.nedss.messageframework.notificationmastermessage.EntityId;
import gov.cdc.nedss.messageframework.notificationmastermessage.EntityLocator;
import gov.cdc.nedss.messageframework.notificationmastermessage.PersonEthnicGroup;
import gov.cdc.nedss.messageframework.notificationmastermessage.PersonName;
import gov.cdc.nedss.messageframework.notificationmastermessage.Person;
import gov.cdc.nedss.messageframework.notificationmastermessage.PhysicalLocatorType;
import gov.cdc.nedss.messageframework.notificationmastermessage.PostalLocatorType;
import gov.cdc.nedss.messageframework.notificationmastermessage.TeleLocatorType;
import gov.cdc.nedss.messageframework.notificationmastermessage.OrganizationName;
import gov.cdc.nedss.messageframework.notificationmastermessage.Participation;
import gov.cdc.nedss.messageframework.notificationmastermessage.PersonRace;
import gov.cdc.nedss.messageframework.notificationmastermessage.*;

import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.*;


import gov.cdc.nedss.util.*;
import gov.cdc.nedss.nnd.util.*;

import java.util.Collection;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.ArrayList;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.ValueObjectExtractors;


public class NNDMarshallerSummaryReport
{

  static final LogUtils logger = new LogUtils(NNDMarshallerSummaryReport.class.getName());
  TreeMap<Object, Object> localIdTreeMap = new TreeMap<Object, Object>();
  ValueObjectExtractors valueObjExtractor = new ValueObjectExtractors();
  NNDUtils util = new NNDUtils();

   /**
    * @roseuid 3D57A3F002BF
    */
   public NNDMarshallerSummaryReport()
   {

   }

   /**
    * @param summaryReportProxyVO
    * @return Object
    * @roseuid 3D4F263200BB
    */
   public Object marshallSummaryReport(SummaryReportProxyVO summaryReportProxyVO) throws NNDException
   {
      NedssEvent nedssEvent = new NedssEvent();
  try {


       nedssEvent.setAct(getActCollection(summaryReportProxyVO));
       System.out.println("returning from setAct");

       nedssEvent.setActRelationship(getActRelationshipCollection(summaryReportProxyVO));

       System.out.println("returning from marshallSummaryReport");
       System.out.println("nedssEvent = " + nedssEvent.toString());
 }

 catch (NNDException nnde)
 {
  nnde.printStackTrace();
  throw nnde;
 }

 catch (Exception e)
 {
  e.printStackTrace();
  NNDException nndOther = new NNDException("Exception in NNDMarshallerSummaryReport " + e.getMessage());
  nndOther.setModuleName("NNDMarshallerSummaryReport.marshallSummaryReport");
  throw nndOther;
 }
        return nedssEvent;
}



   private Act[] getActCollection(SummaryReportProxyVO summaryReportProxyVO) throws NNDException
   {
     Collection<Object>  actCollection  = new ArrayList<Object> ();


      actCollection  = extractObservations(summaryReportProxyVO.getTheObservationVOCollection(),actCollection);
      actCollection  = extractPublicHealthCase(summaryReportProxyVO.getThePublicHealthCaseVO(),actCollection);


   Act[] actArr = new Act[actCollection.size()];

    Iterator<Object>  iter = actCollection.iterator();
     int i= 0;
     while(iter.hasNext())
     {
        actArr[i] = (Act)iter.next();
        i++;
     }
    return  actArr;


   }

   private  ActRelationship[] getActRelationshipCollection(SummaryReportProxyVO summaryReportProxyVO) throws NNDException
   {
   System.out.println(" ---------- in getActRelationshipCollection   ----------- ");
     Collection<Object>  actRelationshipCollection  = extractActRelationships(summaryReportProxyVO.getThePublicHealthCaseVO().getTheActRelationshipDTCollection());
   System.out.println(" ---------- in getActRelationshipCollection  ----------- extractActRelationships");
     actRelationshipCollection  = extractActRelationshipsFromObservation(summaryReportProxyVO.getThePublicHealthCaseVO().getTheActRelationshipDTCollection(),summaryReportProxyVO.getTheObservationVOCollection(),actRelationshipCollection);
   System.out.println(" ---------- in getActRelationshipCollection  ----------- extractActRelationshipsFromObservation");

   Collection<Object>  moreActRelations
          = extractRowItemActRelationships(summaryReportProxyVO.getTheObservationVOCollection());
      actRelationshipCollection.addAll(moreActRelations);

     ActRelationship[] actRelationshipArr = new ActRelationship[actRelationshipCollection.size()];
    Iterator<Object>  iter = actRelationshipCollection.iterator();
     int i= 0;
     while(iter.hasNext())
     {
        actRelationshipArr[i] = (ActRelationship)iter.next();
        i++;
     }
   System.out.println(" ---------- end of getActRelationshipCollection   ----------- ");

     return  actRelationshipArr;
   }


   private  Collection<Object>  extractObservations(Collection<Object> observationVOCollection,Collection<Object>  actCollection) throws NNDException
   {

     Iterator<Object>  iter =  observationVOCollection.iterator();
          while(iter.hasNext())
          {
             Act  act = new Act();
             ObservationVO observationVO = (ObservationVO)iter.next();
             if(NNDConstantUtil.notNull(observationVO.getTheActIdDTCollection()))
              act.setActId(getObservationActIds(observationVO.getTheActIdDTCollection()));

             act.setActTempId(observationVO.getTheObservationDT().getLocalId());
             act.setClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
             Observation  observation = new Observation();
             observation = (Observation)util.copyObjects(observationVO.getTheObservationDT(),observation);
             // ObsValueCoded Collection
             Collection<Object>  obsValueCodedColl = observationVO.getTheObsValueCodedDTCollection();
             if(obsValueCodedColl != null)
             {
                Iterator<Object>   obsValueCodedIter = obsValueCodedColl.iterator();
                 while(obsValueCodedIter.hasNext())
                 {
                    ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)obsValueCodedIter.next();
                    ObsValueCoded obsValueCoded = new ObsValueCoded();
                    obsValueCoded = (ObsValueCoded)util.copyObjects(obsValueCodedDT,obsValueCoded);
                    observation.addObsValueCoded(obsValueCoded);

                 }
             }
             //ObsValue Numeric Collection
             Collection<Object>  obsValueNumColl = observationVO.getTheObsValueNumericDTCollection();
             if(obsValueNumColl != null)
             {
                Iterator<Object>  obsValueNumIter = obsValueNumColl.iterator();
                 while(obsValueNumIter.hasNext())
                 {
                    ObsValueNumericDT obsValueNumericDT = (ObsValueNumericDT)obsValueNumIter.next();
                    ObsValueNumeric obsValueNumeric = new ObsValueNumeric();
                    obsValueNumeric = (ObsValueNumeric)util.copyObjects(obsValueNumericDT,obsValueNumeric);
                    observation.addObsValueNumeric(obsValueNumeric);
                 }
            }
            //ObsValue Text Collection
             Collection<Object>  obsValueTxtColl = observationVO.getTheObsValueTxtDTCollection();
             if(obsValueTxtColl != null)
             {
                Iterator<Object>  obsValueTxtIter = obsValueTxtColl.iterator();
                 while(obsValueTxtIter.hasNext())
                 {
                    ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT)obsValueTxtIter.next();
                    ObsValueText obsValueText = new ObsValueText();
                    obsValueText = (ObsValueText)util.copyObjects(obsValueTxtDT,obsValueText);
                    observation.addObsValueText(obsValueText);
                 }
             }

            //ObsValue Date Collection
             Collection<Object>  obsValueDateColl = observationVO.getTheObsValueDateDTCollection();
             if(obsValueDateColl != null)
             {
                Iterator<Object>  obsValueDateIter = obsValueDateColl.iterator();
                 while(obsValueDateIter.hasNext())
                 {
                    ObsValueDateDT obsValueDateDT = (ObsValueDateDT)obsValueDateIter.next();
                    ObsValueDate obsValueDate = new ObsValueDate();
                    obsValueDate = (ObsValueDate)util.copyObjects(obsValueDateDT,obsValueDate);
                    observation.addObsValueDate(obsValueDate);
                 }
             }
            //ObservationReason Collection
             Collection<Object>  obsReasonColl = observationVO.getTheObservationReasonDTCollection();
             if(obsReasonColl!=null)
             {
                Iterator<Object>  obsReasonIter = obsReasonColl.iterator();
                 while(obsReasonIter.hasNext())
                 {
                    ObservationReasonDT observationReasonDT = (ObservationReasonDT)obsReasonIter.next();
                    ObservationReason observationReason = new ObservationReason();
                    observationReason = (ObservationReason)util.copyObjects(observationReasonDT,observationReason);
                    observation.addObservationReason(observationReason);
                 }
             }
              //ObservationInterp Collection
             Collection<Object>  obsInterpColl = observationVO.getTheObservationInterpDTCollection();
             if(obsInterpColl != null)
             {
                Iterator<Object>  obsInterpIter = obsInterpColl.iterator();
                 while(obsInterpIter.hasNext())
                 {
                    ObservationInterpDT observationInterpDT = (ObservationInterpDT)obsInterpIter.next();
                    ObservationInterp observationInterp = new ObservationInterp();
                    observationInterp = (ObservationInterp)util.copyObjects(observationInterpDT,observationInterp);
                    observation.addObservationInterp(observationInterp);
                 }
             }
             act.setObservation(observation);
             localIdTreeMap.put(observationVO.getTheObservationDT().getObservationUid(),observationVO.getTheObservationDT().getLocalId());
             actCollection.add(act);
          }
      return actCollection;
    }

   private  Collection<Object>  extractPublicHealthCase( PublicHealthCaseVO publicHealthCaseVO,Collection<Object>  actCollection) throws NNDException
   {
         Act  act = new Act();
         if(NNDConstantUtil.notNull(publicHealthCaseVO.getTheActIdDTCollection()))
            act.setActId(getObservationActIds(publicHealthCaseVO.getTheActIdDTCollection()));


         PublicHealthCaseDT publicHealthCaseDT = publicHealthCaseVO.getThePublicHealthCaseDT();
         act.setActTempId(publicHealthCaseDT.getLocalId());
         act.setClassCd(NEDSSConstants.ACT106_TAR_CLASS_CD);
         PublicHealthCase publicHealthCase = new PublicHealthCase();
         publicHealthCase  = (PublicHealthCase)util.copyObjects(publicHealthCaseDT,publicHealthCase);
        //Public HealthCase ConfirmationMethod Collection
         Collection<Object>  conMethColl = publicHealthCaseVO.getTheConfirmationMethodDTCollection();
         if(conMethColl != null)
         {
            Iterator<Object>  conMethIter = conMethColl.iterator();
             while(conMethIter.hasNext())
             {
                ConfirmationMethodDT confirmationMethodDT = (ConfirmationMethodDT)conMethIter.next();
                ConfirmationMethod confirmationMethod = new ConfirmationMethod();
                confirmationMethod = (ConfirmationMethod)util.copyObjects(confirmationMethodDT,confirmationMethod);
                publicHealthCase.addConfirmationMethod(confirmationMethod);
             }
         }
         act.setPublicHealthCase(publicHealthCase);
         localIdTreeMap.put(publicHealthCaseDT.getPublicHealthCaseUid(),publicHealthCaseDT.getLocalId());
         actCollection.add(act);

      return actCollection;

    }

   private  Collection<Object>  extractActRelationships(Collection<Object> actRelationshipDTCollection) throws NNDException
   {
System.out.println("start of extractActRelationships");
      Collection<Object>  actCollection  = new ArrayList<Object> ();
     Iterator<Object>  iter =  actRelationshipDTCollection.iterator();
      while(iter.hasNext())
      {

         ActRelationshipDT actRelationshipDT = (ActRelationshipDT)iter.next();
         if(actRelationshipDT.getTypeCd().equalsIgnoreCase("SummaryForm"))
         {
             ActRelationship  actRelationship = new ActRelationship();
             actRelationship.setTargetActTempId((String)localIdTreeMap.get(actRelationshipDT.getTargetActUid()));
             actRelationship.setSourceActTempId(((String)localIdTreeMap.get(actRelationshipDT.getSourceActUid())));
             actRelationship = (ActRelationship)util.copyObjects(actRelationshipDT, actRelationship);
             actCollection.add(actRelationship);
         }
      }
System.out.println("end of extractActRelationships");
      return actCollection;
    }


   private  Collection<Object>  extractActRelationshipsFromObservation(Collection<Object> actRelationshipDTCollection, Collection<Object>  observationVOCollection,Collection<Object>  actRelationshipCollection  ) throws NNDException
   {
System.out.println("start of extractActRelationshipsFromObservation");
    try
    {
System.out.println("actRelationshipDTCollection.size() = " + actRelationshipDTCollection.size());
     Iterator<Object>  iter =  actRelationshipDTCollection.iterator();
      while(iter.hasNext())
      {
         ActRelationshipDT actRelationshipDT = (ActRelationshipDT)iter.next();
System.out.println("actRelationshipDT.getTypeCd() = " + actRelationshipDT.getTypeCd());
         if(actRelationshipDT.getTypeCd().equals(NEDSSConstants.SUMMARY_FORM))
         {
System.out.println("observationVOCollection.size() = " + observationVOCollection.size());
            Iterator<Object>  iter1 = observationVOCollection.iterator();
             while(iter1.hasNext())
             {
                 ObservationVO observationVO = (ObservationVO)iter1.next();
                 if(observationVO.getTheObservationDT().getObservationUid().equals(actRelationshipDT.getSourceActUid()))
                 {
                     Collection<Object>  actObsColl = observationVO.getTheActRelationshipDTCollection();
System.out.println("actObsColl.size() = " + actObsColl.size());
                    Iterator<Object>  iter2 = actObsColl.iterator();
                     while(iter2.hasNext())
                     {
                         ActRelationshipDT actRelationshipDT1 = (ActRelationshipDT)iter2.next();
                         ActRelationship  actRelationship = new ActRelationship();
System.out.println("actRelationshipDT1.getTargetActUid() = " + actRelationshipDT1.getTargetActUid());
                         actRelationship.setTargetActTempId((String)localIdTreeMap.get(actRelationshipDT1.getTargetActUid()));
System.out.println("actRelationshipDT1.getSourceActUid() = " + actRelationshipDT1.getSourceActUid());
                         actRelationship.setSourceActTempId(((String)localIdTreeMap.get(actRelationshipDT1.getSourceActUid())));
                         actRelationship = (ActRelationship)util.copyObjects(actRelationshipDT1, actRelationship);
                         actRelationshipCollection.add(actRelationship);
                     }
                 }
             }
         }
      }
System.out.println("end of extractActRelationshipsFromObservation");
      return actRelationshipCollection;
      }catch(Exception  e)
      {
        e.printStackTrace();
        throw new NNDException("NNDMarshallerSummaryReport.extractActRelationshipsFromObservation" + e.getMessage());
      }
    }


   private Collection<Object>  extractRowItemActRelationships(final Collection<Object>  observations )
      throws NNDException
    {
        Collection<Object>  observationActsRel = new ArrayList<Object> ();
        Collection<Object>  actRelationships   = new ArrayList<Object> ();

       Iterator<Object>  iterator = observations.iterator();
        ObservationVO aObservation = null;

        while(iterator.hasNext())
        {
            aObservation = (ObservationVO) iterator.next();

            if (aObservation.getTheObservationDT().getCd().equals("ItemToRow"))
            {
              observationActsRel.addAll(aObservation.getTheActRelationshipDTCollection());
            }
        }

        iterator = observationActsRel.iterator();

        ActRelationshipDT actRelationshipDT = null;
        ActRelationship   actRelationship   = null;

        while(iterator.hasNext())
        {
            actRelationshipDT = (ActRelationshipDT) iterator.next();
            actRelationship = new ActRelationship();

            util.copyObjects(actRelationshipDT,actRelationship);

            Object key           = actRelationshipDT.getTargetActUid();
            String localTargetId = (String) localIdTreeMap.get(key);

            actRelationship.setTargetActTempId(localTargetId);

            key                  = actRelationshipDT.getSourceActUid();
            String localSourceId = (String) localIdTreeMap.get(key);

            actRelationship.setSourceActTempId(localSourceId);

            actRelationships.add(actRelationship);
        }

        return actRelationships;
    }


 private  ActId[] getObservationActIds(Collection<Object> actIDDTColl) throws NNDException
 {

      ActId [] castorActIds = new ActId[actIDDTColl.size()];
      ArrayList<Object> actIDDTList  = (ArrayList<Object> ) actIDDTColl;
try {

      for  (int i=0; i< castorActIds.length; i++)

      {
         ActIdDT  actIdDT = (ActIdDT) actIDDTList.get(i);
         if ( NNDConstantUtil.notNull(actIdDT))
         {

           ActId castorActId = new ActId();
           castorActIds[i] = (ActId) util.copyObjects(actIdDT, castorActId);
         }
      }
  }
  catch (NNDException nnde)
  {
   throw nnde;
  }

  catch (Exception e)
  {
   e.printStackTrace();
   NNDException nndOther = new NNDException ("Exception while extracting actIDS " + e.getMessage());
   nndOther.setModuleName("NNDMarshallerSummaryReport.getObservationActIds");

  }
      return castorActIds;
 }

}
