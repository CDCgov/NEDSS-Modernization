//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\nnd\\ejb\\nndmessageprocessor\\helpers\\NNDMarshallerInvestigation.java

/**
 * Title:        NEDSS1.0
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author CSC Contractor
 * @version 1.0
 */

package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;

import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.messageframework.notificationmastermessage.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.nnd.util.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Hashtable;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.ValueObjectExtractors;


public class NNDMarshallerInvestigation
{

  static final LogUtils logger = new LogUtils(NNDMarshallerInvestigation.class.getName());
  private Hashtable<Object,Object> localIdMap = new Hashtable<Object,Object>();
  ValueObjectExtractors valueObjExtractor = new ValueObjectExtractors();
  NNDUtils util = new NNDUtils();
  private Hashtable<Object,Object> commonHashtable = new Hashtable<Object,Object>();
  private ArrayList<Object> allRoles = new ArrayList<Object> ();
  private Collection<Object>  entityCollection  = new ArrayList<Object> ();
  private NBSSecurityObj nbsSecurityObj = null;

   /**
    * @roseuid 3D57A3F002BF
    */
   public NNDMarshallerInvestigation()
   {

   }

   /**The method converts the InvestigationProxyVO to a NedssEvent Object
    * @param investigationProxyVO
    * @return Object
    * @roseuid 3D4F263200BB
    */
   public Object marshallInvestigation(InvestigationProxyVO investigationProxyVO, NBSSecurityObj nbsSecurityObj) throws NNDException
   {
   try
   {
      this.nbsSecurityObj = nbsSecurityObj;

      NedssEvent nedssEvent = new NedssEvent();

      nedssEvent.setEntity(getEntityCollection(investigationProxyVO));
//##!! System.out.println("returning from setEntity");
      nedssEvent.setAct(getActCollection(investigationProxyVO));
//##!! System.out.println("returning from setAct");
      nedssEvent.setParticipation(getParticipationCollection(investigationProxyVO));
//##!! System.out.println("returning from setParticipation");
      nedssEvent.setActRelationship(getActRelationshipCollection(investigationProxyVO));
//*******   Commenting Roles
      Role [] roleArray = setAllRoles();
      if (NNDConstantUtil.notNull(roleArray))
          nedssEvent.setRole(roleArray);

//##!! System.out.println("returning from marshallInvestigation");
//##!! System.out.println("nedssEvent = " + nedssEvent.toString());

      NNDLDFMarshaller ldfMarshaller = new NNDLDFMarshaller();
      ldfMarshaller.marshallLDF(investigationProxyVO, nedssEvent,localIdMap);
      return nedssEvent;
    }
    catch (NNDException nnde)
    {
     throw nnde;
    }
    catch(Exception e)
    {
    e.printStackTrace();
    NNDException nndOther = new NNDException("Error in marshallInvestigation() "+ e.getMessage());
    nndOther.setModuleName("NNDMarshallerInvestigation.marshallInvestigation");
    throw nndOther;
    }
   }
   /*
      * Passing InvestigationProxyVO and returning an array of Entities
      * which will be added to the NedssEvent
      * entityCollection  contatins the collection of entities
      * Converting entityCollection  to entity Array
   */


   private Entity[] getEntityCollection(InvestigationProxyVO investigationProxyVO ) throws NNDException
   {
    try
    {

         entityCollection  = extractEntities(investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection(),investigationProxyVO.getThePersonVOCollection(),NEDSSConstants.CLASS_CD_PSN,entityCollection);
    //     entityCollection  = extractEntities(investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection(),investigationProxyVO.getTheMaterialVOCollection(),"MAT",entityCollection);
         entityCollection  = extractEntities(investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection(),investigationProxyVO.getTheOrganizationVOCollection(),"ORG",entityCollection);
         Entity entityArr[] = new Entity[entityCollection.size()];
        Iterator<Object>  iter = entityCollection.iterator();
         int i= 0;
         while(iter.hasNext())
         {
            entityArr[i] = (Entity)iter.next();
            i++;
         }

         return  entityArr;
      }
    catch(Exception e)
    {
    e.printStackTrace();
    throw new NNDException("Error in marshallInvestigation, getEntityCollection() method  "+ e.getMessage());
    }
   }
  /*
     **Passing InvestigationProxyVO to get the act array back
     **actCollection  is the collection of acts in InvestigationProxyVO
     **Converting actCollection  to act Array
  */
   private Act[] getActCollection(InvestigationProxyVO investigationProxyVO) throws NNDException
   {
     try
     {
         Collection<Object>  actCollection  = new ArrayList<Object> ();
         actCollection  = extractObservations(investigationProxyVO.getTheObservationVOCollection(),actCollection);
         actCollection  = extractPublicHealthCase(investigationProxyVO.getPublicHealthCaseVO(),actCollection);
         /* Converting actCollection  to act Array */
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
    catch(Exception e)
    {
    e.printStackTrace();
    throw new NNDException("Error in marshallInvestigation, getActCollection() method  "+e.getMessage());
    }

   }

 /*
     **Passing InvestigationProxyVO to get the Castor Participation array back
     **participationCollection  is the collection of participations in InvestigationProxyVO
     **Converting participationCollection  to act Array
  */

   private  Participation[] getParticipationCollection(InvestigationProxyVO investigationProxyVO) throws NNDException
   {
     try
     {
         Collection<Object>  participationCollection  = extractParticipations(investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection());
         Participation[] participationArr = new Participation[participationCollection.size()];
        Iterator<Object>  iter = participationCollection.iterator();
         int i= 0;
         while(iter.hasNext())
         {
            participationArr[i] = (Participation)iter.next();
            i++;
         }

         return  participationArr;
      }
      catch(Exception e)
      {
      e.printStackTrace();
      throw new NNDException("Error in marshallInvestigation, getParticipationCollection() method  "+e.getMessage());
      }

   }
   /*
     **Setting all the entity roles that have been accumulated so far
   */

   private Role[] setAllRoles() throws NNDException
   {

     try
   {
      if(NNDConstantUtil.notNull(allRoles))

      {

          if(!allRoles.isEmpty())
          {

             Role [] castorRoles = new Role[allRoles.size()];

             for(int i = 0; i< castorRoles.length; i++)

             {

                Role castorRole = new Role();
                castorRole = (Role) allRoles.get(i);

/* commented out for getting scoping entity uids 01/07/2004      if (NNDConstantUtil.notNull(castorRole.getScopingEntityTempId()))
                {     Long entityUid = new Long (castorRole.getScopingEntityTempId());
                      if (localIdMap.containsKey(entityUid))
                          castorRole.setScopingEntityTempId(localIdMap.get(entityUid).toString());
                 }*/

                  if (NNDConstantUtil.notNull(castorRole.getSubjectEntityTempId()))
                 { Long entityUid = new Long (castorRole.getSubjectEntityTempId());
                    if (localIdMap.containsKey(entityUid))
                        castorRole.setSubjectEntityTempId(localIdMap.get(entityUid).toString());
                  }


                castorRoles[i] = castorRole;
            } // for

             return castorRoles;

           }// 2ndif


       } // 1stif


      return null;
   }

     catch(Exception e)
      {
      e.printStackTrace();
      throw new NNDException("Error in marshallInvestigation, getParticipationCollection() method  "+e.getMessage());
      }

   }
   /*
     **Passing InvestigationProxyVO to get the CastorActRelationship array back
     **actRelationshipCollection  is the collection of actRelashionShip in InvestigationProxyVO
     **Converting actRelationshipCollection  to act Array
  */

   private  ActRelationship[] getActRelationshipCollection(InvestigationProxyVO investigationProxyVO) throws NNDException
   {
     try
     {

       //##!! System.out.println(" ---------- in getActRelationshipCollection  ----------- ");
         Collection<Object>  actRelationshipCollection  = extractActRelationships(investigationProxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection());
       //##!! System.out.println(" ---------- in getActRelationshipCollection  ----------- extractActRelationships");
         actRelationshipCollection  = extractActRelationshipsFromObservation(investigationProxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection(),investigationProxyVO.getTheObservationVOCollection(),actRelationshipCollection);
       //##!! System.out.println(" ---------- in getActRelationshipCollection  ----------- extractActRelationshipsFromObservation");
         ActRelationship[] actRelationshipArr = new ActRelationship[actRelationshipCollection.size()];
        Iterator<Object>  iter = actRelationshipCollection.iterator();
         int i= 0;
         while(iter.hasNext())
         {
            actRelationshipArr[i] = (ActRelationship)iter.next();
            i++;
         }
       //##!! System.out.println(" ---------- end of getActRelationshipCollection  ----------- ");

         return  actRelationshipArr;
      }
      catch(Exception e)
      {
      e.printStackTrace();
      throw new NNDException("Error in marshallInvestigation, getActRelashionshipCollection() method  "+e.getMessage());
      }
   }
     /**

     ** Passing participationDTCollection, each entityVOCollection   from InvestigationProxy,
     ** Classcode of the corresponidng entity, and the entityCollection  (which is to be returned)
     ** it iterates through the participation Collection<Object>  and find the corresponding entities
     ** castor it and add it to the entityCollection
   */
   private  Collection<Object>  extractEntities(Collection<Object> participationDTCollection, Collection<Object>  entityVOCollection,String entityType, Collection<Object>  entityCollection  ) throws NNDException
   {
       try
       {
           Iterator<Object>  itr = participationDTCollection.iterator();

            while (itr.hasNext())
            {
                ParticipationDT participationDT = (ParticipationDT)itr.next();
                if (NNDConstantUtil.notNull(participationDT))
               {
                   // if (participationDT.getSubjectClassCd().equals(NEDSSConstants.CLASS_CD_PSN))
                    if(entityType.equals(NEDSSConstants.CLASS_CD_PSN))
                    {
                           Entity entityPersonSubject = this.getEntityFromPerson(participationDT,entityVOCollection, NEDSSConstants.CLASS_CD_PSN,participationDT.getTypeCd());
                           if(entityPersonSubject != null)
                           {
                              if(entityPersonSubject.getEntityTempId()!= null)
                                 entityCollection.add(entityPersonSubject);
                           }




                      }
                   // For material... Commented out since we don't have meterialCollection  in InvestigationProxy
         /*           else if (entityType.equals(NEDSSConstants.MATERIAL_CLASS_CODE))
                    {
                       //##!! System.out.println("No Type code is found for material, so no instance is found for material");
                    }*/

                    // For oraganization - Get the organization Entity Collection
                    else if (entityType.equals(NEDSSConstants.ORGANIZATION_CLASS_CODE))
                    {


                           Entity entityOrganization = this.getEntityFromOrganization(participationDT,entityVOCollection, NEDSSConstants.ORGANIZATION_CLASS_CODE,participationDT.getTypeCd());
                           if(entityOrganization != null)
                           {
                              if(entityOrganization.getEntityTempId()!=null)
                                 entityCollection.add(entityOrganization);
                           }


                    }
           } //1st IF
          }// while
          return entityCollection;
    }
    catch(Exception e)
    {
    e.printStackTrace();
    throw new NNDException("Error in marshallInvestigation, extractEntities() method  "+ e.getMessage());
    }
  }
  /**
   * Extracting Observations from the InvestigationProxyVO.
   * Castoring all Observations and the Corresponding DT's
   * and adding the Observations to the actCollection
   * @param observationVOCollection
   * @param actCollection
   * @return actCollection
   * @throws NNDException
   */
   private  Collection<Object>  extractObservations(Collection<ObservationVO> observationVOCollection,Collection<Object>  actCollection) throws NNDException
   {

      try
      {
         Iterator<ObservationVO>  iter =  observationVOCollection.iterator();
          while(iter.hasNext())
          {
             Act  act = new Act();
             ObservationVO observationVO = (ObservationVO)iter.next();
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
             ActId actIdArrRes[] = getActIdCollection(observationVO.getTheActIdDTCollection());
             if(actIdArrRes != null)
             {
                act.setActId(actIdArrRes);
             }

             localIdMap.put(observationVO.getTheObservationDT().getObservationUid(),observationVO.getTheObservationDT().getLocalId());
             actCollection.add(act);
          }
      return actCollection;
      }
      catch(Exception e)
      {
      e.printStackTrace();
      throw new NNDException("Error in marshallInvestigation, extractObservations() method  "+e.getMessage());
      }

    }
   /**  Castoring  PublicHealthCase and the related DT's in PublicHealthCaseVO
    *   and adding the the actCollection
    *   Returns the actCollection
    *   @Param PublicHealthCaseVO
    *   @Param actCollection
    *
    */

   private  Collection<Object>  extractPublicHealthCase( PublicHealthCaseVO publicHealthCaseVO,Collection<Object>  actCollection) throws NNDException
   {
         try
         {
         Act  act = new Act();
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
         ActId actIdArrRes[] = getActIdCollection(publicHealthCaseVO.getTheActIdDTCollection());
         if(actIdArrRes != null)
         {
            act.setActId(actIdArrRes);
         }
         act.setPublicHealthCase(publicHealthCase);
         localIdMap.put(publicHealthCaseDT.getPublicHealthCaseUid(),publicHealthCaseDT.getLocalId());
         actCollection.add(act);

      return actCollection;

    }
    catch(Exception e)
    {
    e.printStackTrace();
    throw new NNDException("Error in marshallInvestigation, exractPublicHealthCase() method  "+e.getMessage());
    }
  }
 /**
  * Castoring the ParticipationDTs
  * @param participationDTCollection
  * @return castorParticipation Collection
  * @throws NNDException
  */
   private  Collection<Object>  extractParticipations(Collection<Object> participationDTCollection) throws NNDException
   {
      try
      {
          Collection<Object>  partCollection  = new ArrayList<Object> ();
         Iterator<Object>  iter =  participationDTCollection.iterator();
          while(iter.hasNext())
          {

             ParticipationDT participationDT = (ParticipationDT)iter.next();
             Participation  participation = new Participation();
             System.out.println("Subject uid " + participationDT.getSubjectEntityUid());
             System.out.println("Act uid " + participationDT.getActUid());
             participation.setActTempId((String)localIdMap.get(participationDT.getActUid()));
             participation.setSubjectEntityTempId((String)localIdMap.get(participationDT.getSubjectEntityUid()));
             // Use the util class  for the castor
             participation = (Participation)util.copyObjects(participationDT,participation);
             partCollection.add(participation);
          }
          return partCollection;
      }
      catch(Exception e)
      {
      e.printStackTrace();
      throw new NNDException("Error in marshallInvestigation, exractParticipations() method  "+e.getMessage());
      }
    }

   /**
    * Building the main actRelationships
    * Castoring all the actRelationshipDT's
    * @param actRelationshipDTCollection
    * @return Collection
    * @throws NNDException
    */
   private  Collection<Object>  extractActRelationships(Collection<Object> actRelationshipDTCollection) throws NNDException
   {
      try
      {
    //##!! System.out.println("start of extractActRelationships");
          Collection<Object>  actCollection  = new ArrayList<Object> ();
         Iterator<Object>  iter =  actRelationshipDTCollection.iterator();
          while(iter.hasNext())
          {

             ActRelationshipDT actRelationshipDT = (ActRelationshipDT)iter.next();

           /**
            * Excluding the ActRelations with Lab, Morb, Vaccine, Treatment, Notif.
            */
           if(!((actRelationshipDT.getTypeCd().equalsIgnoreCase(NNDConstantUtil.PHC_LAB_AR_TYPECD)) ||
                (actRelationshipDT.getTypeCd().equalsIgnoreCase(NNDConstantUtil.PHC_MRB_AR_TYPECD)) ||
                (actRelationshipDT.getTypeCd().equalsIgnoreCase(NNDConstantUtil.PHC_VAC_AR_TYPECD)) ||
                (actRelationshipDT.getTypeCd().equalsIgnoreCase(NNDConstantUtil.PHC_TRT_AR_TYPECD)) ||
                (actRelationshipDT.getTypeCd().equalsIgnoreCase(NNDConstantUtil.PHC_NTF_AR_TYPECD)) ||
                (actRelationshipDT.getTypeCd().equalsIgnoreCase("DocToPHC"))) )
           {

               ActRelationship  actRelationship = new ActRelationship();
               actRelationship.setTargetActTempId((String)localIdMap.get(actRelationshipDT.getTargetActUid()));
   //## System.out.println("The sourceTempId from actRelationShip = "+actRelationshipDT.getSourceActUid());
   //## System.out.println("localIdMap.value for sourceActUID(GeneralACT) = "+ localIdMap.get(actRelationshipDT.getSourceActUid()));
               actRelationship.setSourceActTempId(((String)localIdMap.get(actRelationshipDT.getSourceActUid())));
               // Use the util class  for the castor
               actRelationship =(ActRelationship)util.copyObjects(actRelationshipDT,actRelationship);
               actCollection.add(actRelationship);
            }
          }
    //##!! System.out.println("end of extractActRelationships");
      return actCollection;
      }
      catch(Exception e)
      {
      e.printStackTrace();
      throw new NNDException("Error in marshallInvestigation, exractActRelashionShip() method  "+e.getMessage());
      }
    }


  /**
   * Buiding the Container ActRelationships
   * @param actRelationshipDTCollection
   * @param observationVOCollection
   * @param actRelationshipCollection
   * @return
   * @throws NNDException
   */
   private  Collection<Object>  extractActRelationshipsFromObservation(Collection<Object> actRelationshipDTCollection, Collection<ObservationVO>  observationVOCollection,Collection<Object>  actRelationshipCollection  ) throws NNDException
   {
//##!! System.out.println("start of extractActRelationshipsFromObservation");
	   String observationCd = null;
    try
    {
//##!! System.out.println("actRelationshipDTCollection.size() = " + actRelationshipDTCollection.size());
     Iterator<Object>  iter =  actRelationshipDTCollection.iterator();
      while(iter.hasNext())
      {
         ActRelationshipDT actRelationshipDT = (ActRelationshipDT)iter.next();
//##!! System.out.println("actRelationshipDT.getTypeCd() = " + actRelationshipDT.getTypeCd());
         if(actRelationshipDT.getTypeCd()!=null && actRelationshipDT.getTypeCd().equals(NEDSSConstants.PHC_INV_FORM))
         {
//##!! System.out.println("observationVOCollection.size() = " + observationVOCollection.size());
            Iterator<ObservationVO>  iter1 = observationVOCollection.iterator();
             while(iter1.hasNext())
             {
                 ObservationVO observationVO = (ObservationVO)iter1.next();
                 
                 observationCd=observationVO.getTheObservationDT().getCd();

                 //** Adding Item to Row Act Relationships**
                 if(observationVO.getTheObservationDT().getCd()!=null && observationVO.getTheObservationDT().getCd().equals(NEDSSConstants.ITEM_TO_ROW))
                 {
                     Collection<Object>  actObsColl = observationVO.getTheActRelationshipDTCollection();
                     if(actObsColl!=null){
//##!! System.out.println("actObsColl.size() = " + actObsColl.size());
                    Iterator<Object>  iter2 = actObsColl.iterator();
                     while(iter2.hasNext())
                     {
                         ActRelationshipDT actRelationshipDT1 = (ActRelationshipDT)iter2.next();
                         ActRelationship  actRelationship = new ActRelationship();
//##!! System.out.println("actRelationshipDT1.getTargetActUid() = " + actRelationshipDT1.getTargetActUid());
                         actRelationship.setTargetActTempId((String)localIdMap.get(actRelationshipDT1.getTargetActUid()));
//##!! System.out.println("actRelationshipDT1.getSourceActUid() = " + actRelationshipDT1.getSourceActUid());
                         actRelationship.setSourceActTempId(((String)localIdMap.get(actRelationshipDT1.getSourceActUid())));
                         // Use the util class  for the castor
                         actRelationship = (ActRelationship)util.copyObjects(actRelationshipDT1,actRelationship);
                         actRelationshipCollection.add(actRelationship);
                     }
                     }
                 //*************************
                 }
                 // **** find Container Observation//
                 if(observationVO.getTheObservationDT().getObservationUid()!=null && observationVO.getTheObservationDT().getObservationUid().equals(actRelationshipDT.getSourceActUid()))
                 {
                     Collection<Object>  actObsColl = observationVO.getTheActRelationshipDTCollection();
//##!! System.out.println("actObsColl.size() = " + actObsColl.size());
                     if(actObsColl!=null){
                    Iterator<Object>  iter2 = actObsColl.iterator();
                     while(iter2.hasNext())
                     {
                         ActRelationshipDT actRelationshipDT1 = (ActRelationshipDT)iter2.next();
                         ActRelationship  actRelationship = new ActRelationship();
//##!! System.out.println("actRelationshipDT1.getTargetActUid() = " + actRelationshipDT1.getTargetActUid());
                         actRelationship.setTargetActTempId((String)localIdMap.get(actRelationshipDT1.getTargetActUid()));
//##!! System.out.println("actRelationshipDT1.getSourceActUid() = " + actRelationshipDT1.getSourceActUid());
                         actRelationship.setSourceActTempId(((String)localIdMap.get(actRelationshipDT1.getSourceActUid())));
                         // Use the util class  for the castor
                         actRelationship = (ActRelationship)util.copyObjects(actRelationshipDT1,actRelationship);
                         actRelationshipCollection.add(actRelationship);
                     }
                 }
                 }
             }
         }
      }
//##!! System.out.println("end of extractActRelationshipsFromObservation");
      return actRelationshipCollection;
      }catch(Exception  e)
      {
        e.printStackTrace();
        throw new NNDException("NNDMarshallerInvestigation.extractActRelationshipsFromObservation failed for observation cd: "+observationCd +" "+ e.getMessage());
      }
    }

    /**
     * Building the entity from a person
     * Castoring all the related DTs
     * @param participationDT
     * @param entityVOCollection
     * @param classCode
     * @param typeCode
     * @return
     * @throws NNDException
     */
    @SuppressWarnings("unchecked")
	private Entity  getEntityFromPerson(ParticipationDT participationDT,Collection<Object>  entityVOCollection,String classCode, String typeCode) throws NNDException
    {
        try
        {
           Entity entity = new Entity();
           Person person = new Person();
           ArrayList<Object>  entityIdCollection  = new ArrayList<Object> ();
           Long entityUID  = participationDT.getSubjectEntityUid();
           PersonVO personVO = (PersonVO)valueObjExtractor.getPersonValueObject(entityUID, entityVOCollection);
           if(personVO != null)
           { //*************************************************************************
                   commonHashtable = null;
                   commonHashtable = (Hashtable<Object, Object>) valueObjExtractor.getPersonRoles(personVO, nbsSecurityObj);
                   if (commonHashtable != null)
                   {
                     if (commonHashtable.containsKey(personVO.getThePersonDT().getPersonUid()))
                        addToAllRoles((Collection<Object>)commonHashtable.get(personVO.getThePersonDT().getPersonUid()));
                        if (commonHashtable.containsKey(NNDConstantUtil.NND_ROLE_KEY))
                        {

                            ArrayList<Object> scopingEntityColl = (ArrayList<Object> ) commonHashtable.get(NNDConstantUtil.NND_ROLE_KEY);
                           localIdMap =  valueObjExtractor.getScopingEntityLocalIdMap(localIdMap);


                           Iterator<Object>  itr = scopingEntityColl.iterator();
                            while(itr.hasNext())
                            {

                                entityCollection.add(itr.next());

                            }

                        }
                   }
                   //****************** Added for scoping entity ********************* 01/04

                   entity.setEntityTempId(personVO.getThePersonDT().getLocalId());
                   entity.setClassCd(classCode);
                   person.addParticipationTypeCd(typeCode);
                    // use the util class here
                    personVO.getThePersonDT().setPersonParentUid(null);
                    person = (Person)util.copyObjects(personVO.getThePersonDT(),person);
                    // Adding personNameDT Collection<Object>  to the castor class
                    Collection<Object>  personNameColl = personVO.getThePersonNameDTCollection();
                    if (personNameColl != null)
                    {

                       Iterator<Object>  nameIter = personNameColl.iterator();
                        while(nameIter.hasNext())
                        {
                             PersonNameDT personNameDT = (PersonNameDT)nameIter.next();
                             PersonName personName = new PersonName();
                             personName =(PersonName)util.copyObjects(personNameDT,personName);
                             person.addPersonName(personName);

                        }
                    }
                    // Adding personEthnicGroupDT Collection<Object>  to the castor class
                    Collection<Object>  personEthnicColl = personVO.getThePersonEthnicGroupDTCollection();
                    if(personEthnicColl!= null)
                    {
                       Iterator<Object>  ethnicIter = personEthnicColl.iterator();
                        while(ethnicIter.hasNext())
                        {
                            PersonEthnicGroupDT PersonEthnicGroupDT = (PersonEthnicGroupDT)ethnicIter.next();
                            PersonEthnicGroup  personEthnicGroup = new PersonEthnicGroup();
                            personEthnicGroup = (PersonEthnicGroup)util.copyObjects(PersonEthnicGroupDT,personEthnicGroup);
                            person.addPersonEthnicGroup(personEthnicGroup);

                        }
                    }

                    // Adding personRaceDT Collection<Object>  to the castor class
                    Collection<Object>  raceColl = personVO.getThePersonRaceDTCollection();
                    if (raceColl!= null)
                    {
                       Iterator<Object>  raceIter = raceColl.iterator();
                        while(raceIter.hasNext())
                        {
                             PersonRaceDT personRaceDT = (PersonRaceDT)raceIter.next();
                             PersonRace personRace = new PersonRace();
                             personRace = (PersonRace)util.copyObjects(personRaceDT,personRace);
                             person.addPersonRace(personRace);
                        }
                    }

                   EntityId entityIdArrRes[] = getEntityIdCollection(personVO.getTheEntityIdDTCollection());
                   if(entityIdArrRes != null)
                   {
                      entity.setEntityId(getEntityIdCollection(personVO.getTheEntityIdDTCollection()));
                   }
        //##!! System.out.println("personVO.getTheEntityLocatorParticipationDTCollection().size() = " + personVO.getTheEntityLocatorParticipationDTCollection().size());
                   EntityLocator entityLocatorArrRes[] = getEntityLocatorCollection(personVO.getTheEntityLocatorParticipationDTCollection());
                   if(entityLocatorArrRes != null)
                   {
                      entity.setEntityLocator(getEntityLocatorCollection(personVO.getTheEntityLocatorParticipationDTCollection()));
                   }
                   entity.setPerson(person);

                   // Setting the treemap
                   localIdMap.put(personVO.getThePersonDT().getPersonUid(),personVO.getThePersonDT().getLocalId());
           }
         return entity;
        }
        catch(Exception e)
        {
        e.printStackTrace();
        throw new NNDException("Error in marshallInvestigation, getEntityFromPerson() method  "+e.getMessage());
        }
    }
   /**
    * Building the entity from Organization
    * Castoring all the related DTs
    * @param participationDT
    * @param entityVOCollection
    * @param classCode
    * @param typeCode
    * @return
    * @throws NNDException
    */

    private Entity  getEntityFromOrganization(ParticipationDT participationDT,Collection<Object>  entityVOCollection,String classCode, String typeCode) throws NNDException
    {
           try
           {
               Entity entity = new Entity();
               Long entityUID  = participationDT.getSubjectEntityUid();
               OrganizationVO organizationVO = (OrganizationVO)valueObjExtractor.getOrganizationValueObject(entityUID, entityVOCollection);
               if(organizationVO != null)
               {

                 // added for getting scoping entities ********** 01/08/2004
                  commonHashtable = null;
                  commonHashtable = valueObjExtractor.getOrganizationRoles(organizationVO, nbsSecurityObj);

                   if (commonHashtable != null)
                   {

                       if (commonHashtable.containsKey(organizationVO.getTheOrganizationDT().getOrganizationUid()))
                         addToAllRoles((Collection<?>) commonHashtable.get(organizationVO.getTheOrganizationDT().getOrganizationUid()));
                   if (commonHashtable.containsKey(NNDConstantUtil.NND_ROLE_KEY))
                   {

                       ArrayList<?> scopingEntityColl = (ArrayList<?> )commonHashtable.get(NNDConstantUtil.NND_ROLE_KEY);
                       localIdMap = valueObjExtractor.getScopingEntityLocalIdMap(localIdMap);

                      Iterator<?>  itr = scopingEntityColl.iterator();
                       while(itr.hasNext())
                       {
                             entityCollection.add(itr.next());

                       }

                   }


                   }
                   // end block ************** 01/08/2004
                   Organization organization = new Organization();
                   //ArrayList<Object> entityIdCollection  = new ArrayList<Object> ();
                   entity.setEntityTempId(organizationVO.getTheOrganizationDT().getLocalId());
                   entity.setClassCd(classCode);
                   organization.addParticipationTypeCd(typeCode);
                   // use the util class here
                   organization = (Organization)util.copyObjects(organizationVO.getTheOrganizationDT(),organization);
                   Collection<Object>  orgNameColl = organizationVO.getTheOrganizationNameDTCollection();
                   if(orgNameColl!=null)
                   {
                      Iterator<Object>  orgNameIter = orgNameColl.iterator();
                       while(orgNameIter.hasNext())
                       {
                          OrganizationNameDT organizationNameDT = (OrganizationNameDT)orgNameIter.next();
                          OrganizationName organizationName = new OrganizationName();
                          organizationName = (OrganizationName)util.copyObjects(organizationNameDT,organizationName);
                          organization.addOrganizationName(organizationName);

                       }
                   }
                   entity.setOrganization(organization);
                   EntityId entityIdArrRes[] = getEntityIdCollection(organizationVO.getTheEntityIdDTCollection());
                   if(entityIdArrRes != null )
                   {
                      entity.setEntityId(getEntityIdCollection(organizationVO.getTheEntityIdDTCollection()));
                   }
                   EntityLocator entityLocatorArrRes[] = getEntityLocatorCollection(organizationVO.getTheEntityLocatorParticipationDTCollection());
                   if(entityLocatorArrRes != null)
                   {
                      entity.setEntityLocator(getEntityLocatorCollection(organizationVO.getTheEntityLocatorParticipationDTCollection()));
                   }
                   // Setting the treemap
                   localIdMap.put(organizationVO.getTheOrganizationDT().getOrganizationUid(),organizationVO.getTheOrganizationDT().getLocalId());
            }
            return entity;
        }
        catch(Exception e)
        {
        e.printStackTrace();
        throw new NNDException("Error in marshallInvestigation, getEntityFromOrganization() method  "+e.getMessage());
        }

    }


    /**
     * Building entityIds from each entity(person or Organization etc:-)
     * Castoring entityIdDTs to entityId
     * @param entityIdCollection
     * @return Array of entityId
     * @throws NNDException
     */
    private EntityId[] getEntityIdCollection(Collection<Object> entityIdCollection) throws NNDException
    {

        try
        {
         Iterator<Object>  iter = entityIdCollection.iterator();
          ArrayList<Object> entityIdArray = new ArrayList<Object> ();
          while(iter.hasNext())
          {
             EntityIdDT  enityIdDT = (EntityIdDT)iter.next();
           //Added to fix ER# 8815 - 03/25/2016
             if(enityIdDT!=null && enityIdDT.getAssigningAuthorityCd()!=null && enityIdDT.getAssigningAuthorityCd().length()>20){
            	 enityIdDT.setAssigningAuthorityCd(enityIdDT.getAssigningAuthorityCd().substring(0,19));
             }
             EntityId entityId = new EntityId();
             entityId =(EntityId)util.copyObjects(enityIdDT,entityId);
             entityIdArray.add(entityId);
             entityId = null;
          }
          EntityId entityIdArr[] = new EntityId[entityIdCollection.size()];
         Iterator<Object>  it = entityIdArray.iterator();
          int i=0;
          while(it.hasNext())
          {
            entityIdArr[i] = (EntityId)it.next();
            i++;
          }
          return  entityIdArr;
      }
      catch(Exception e)
      {
      e.printStackTrace();
      throw new NNDException("Error in marshallInvestigation, getEntityIDCollection() method  "+e.getMessage());
      }

    }

    /**
     * Building the castor EntityLocator  for each Entity
     * @param entityLocatorCollection
     * @return Array of EntityLocator
     * @throws NNDException
     */
    private EntityLocator[] getEntityLocatorCollection(Collection<Object> entityLocatorCollection) throws NNDException
    {
         try
         {
         Iterator<Object>  iter = entityLocatorCollection.iterator();
          ArrayList<Object> entityLocatorArray = new ArrayList<Object> ();
          while(iter.hasNext())
          {
             EntityLocatorParticipationDT  entityLocatorParticipationDT = (EntityLocatorParticipationDT)iter.next();
//##!! System.out.println(" entityLocatorParticipationDT.getUserAffiliationTxt() = "+ entityLocatorParticipationDT.getUserAffiliationTxt());
             // Use the util class here -- entityLocator = (entityLocator)uitl()
             EntityLocator entityLocator = new EntityLocator();
             entityLocator = (EntityLocator)util.copyObjects(entityLocatorParticipationDT,entityLocator);
             TeleLocatorType teleLocatorType = new TeleLocatorType();
         if(entityLocatorParticipationDT.getTheTeleLocatorDT()!= null)
         {
             teleLocatorType = (TeleLocatorType)util.copyObjects(entityLocatorParticipationDT.getTheTeleLocatorDT(),teleLocatorType);
             entityLocator.setTeleLocatorType(teleLocatorType);
         }
             PostalLocatorType postalLocatorType = new PostalLocatorType();
         if(entityLocatorParticipationDT.getThePostalLocatorDT() != null)
         {
             postalLocatorType = (PostalLocatorType)util.copyObjects(entityLocatorParticipationDT.getThePostalLocatorDT(),postalLocatorType);
             entityLocator.setPostalLocatorType(postalLocatorType);
         }
             PhysicalLocatorType physicalLocatorType = new PhysicalLocatorType();
         if(entityLocatorParticipationDT.getThePhysicalLocatorDT() != null)
         {
             physicalLocatorType = (PhysicalLocatorType)util.copyObjects(entityLocatorParticipationDT.getThePhysicalLocatorDT(),physicalLocatorType);
             entityLocator.setPhysicalLocatorType(physicalLocatorType);
         }
             entityLocatorArray.add(entityLocator);
          }
          EntityLocator entityLocatorArr[] = new EntityLocator[entityLocatorCollection.size()];
         Iterator<Object>  it = entityLocatorArray.iterator();
          int i = 0;
          while(it.hasNext())
          {
            entityLocatorArr[i] = (EntityLocator)it.next();
        i++;
          }
          return entityLocatorArr;
      }
      catch(Exception e)
      {
      e.printStackTrace();
      throw new NNDException("Error in marshallInvestigation, getEntityLocatorCollection() method  "+e.getMessage());
      }
   }

 /**
  * Buidling actIds for each Act
  * @param actIdCollection
  * @return
  * @throws NNDException
  */
  private ActId[] getActIdCollection(Collection<Object> actIdCollection) throws NNDException
  {

      try
      {
        if(actIdCollection  != null)
        {
        ActId actIdArr[] = new ActId[actIdCollection.size()];
       Iterator<Object>  iter = actIdCollection.iterator();
        ArrayList<Object> actIdArray = new ArrayList<Object> ();
        while(iter.hasNext())
        {
           ActIdDT  actIdDT = (ActIdDT)iter.next();
           //Added to fix ER# 8815 - 03/25/2016
           if(actIdDT!=null && actIdDT.getAssigningAuthorityCd()!=null && actIdDT.getAssigningAuthorityCd().length()>20){
        	   actIdDT.setAssigningAuthorityCd(actIdDT.getAssigningAuthorityCd().substring(0,19));
           }
           ActId actId = new ActId();
           actId =(ActId)util.copyObjects(actIdDT,actId);
           actIdArray.add(actId);
           actId = null;
        }
       Iterator<Object>  it = actIdArray.iterator();
        int i=0;
        while(it.hasNext())
        {
          actIdArr[i] = (ActId)it.next();
          i++;
        }
       return  actIdArr;
      }
     else
       return null;
    }
    catch(Exception e)
    {
    e.printStackTrace();
    throw new NNDException("Error in marshallInvestigation, getActIDCollection() method  "+e.getMessage());
    }

  }

  private  void  addToAllRoles(Collection<?> roleCollection) throws NNDException
 {


   try
   {
      if (NNDConstantUtil.notNull(roleCollection))

      {
       if(!roleCollection.isEmpty())

       {
        Iterator<?>  itr = roleCollection.iterator();

         while(itr.hasNext())
          {
           Object obj = itr.next();


           if (!allRoles.contains(obj))
                  allRoles.add(obj);
           else
           {  Role castorRole = (Role) obj;
              System.out.println("obj = " + castorRole.getCd() );

           }

          }


       }


      }

   }


    catch (Exception e)
    {
     NNDException nndOther = new NNDException("Exception in addToAllRoles " + e.getMessage());
     nndOther.setModuleName("NNDMarshallerInvestigation.addToAllRoles"  );
     throw nndOther;

    }


 }


}
