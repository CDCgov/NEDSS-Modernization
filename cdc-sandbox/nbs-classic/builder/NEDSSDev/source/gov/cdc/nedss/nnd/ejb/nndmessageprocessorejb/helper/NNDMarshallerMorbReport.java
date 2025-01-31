//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\gov\\cdc\\nedss\\nnd\\ejb\\nndmessageprocessorejb\\helper\\NNDMarshallerMorbReport.java

package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;

import gov.cdc.nedss.nnd.util.*;
import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.util.*;

import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.ValueObjectExtractors;
import gov.cdc.nedss.messageframework.notificationmastermessage.*;

import java.util.*;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.act.intervention.vo.*;
import gov.cdc.nedss.association.dt.*;

import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.nnd.util.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.intervention.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.locator.dt.*;



public class NNDMarshallerMorbReport
{

   /**
    * @roseuid 3EA98E2D0241
    */
   public NNDMarshallerMorbReport()
   {

   }

  private ValueObjectExtractors valueObjExtractor = new ValueObjectExtractors();
  private NedssEvent nedssEvent = new NedssEvent();
  private Hashtable<Object,Object> localIDMap = new Hashtable<Object,Object>();
  private TreeMap<Object,Object> localIdTreeMap = new TreeMap<Object,Object>();
  private ArrayList<Object> allEntities = new ArrayList<Object> ();
  private ArrayList<Object> allActRelationShips = new ArrayList<Object> ();
  private Hashtable<Object,Object> partTypeCdTab = new Hashtable<Object,Object>();
  private ArrayList<Object> allRoles    = new ArrayList<Object> ();
  private ArrayList<Object> allParticipations = new ArrayList<Object> ();
  private int actIndex = 0;
  private NNDUtils util = new NNDUtils();


  // @param orbidityProxyVO
   // @return java.lang.Object
  // @throws gov.cdc.nedss.nnd.exception.NNDException
    // @roseuid 3EA99413004D
    ///

   public Object marshallMorbReport(MorbidityProxyVO morbidityProxyVO) throws NNDException
   {
     try
        {
            System.out.println("Inside morbReport ++++ ");

            populateCastorEntities(extractEntities(morbidityProxyVO.getTheParticipationDTCollection(), morbidityProxyVO.getThePersonVOCollection(), "PSN"),"PSN");
            populateCastorEntities(extractEntities(morbidityProxyVO.getTheParticipationDTCollection(), morbidityProxyVO.getTheOrganizationVOCollection(), "ORG"),"ORG");

            Entity [] entities = new Entity[allEntities.size()];
            for (int i = 0; i<entities.length; i++)
           {
             entities[i] = (Entity) allEntities.get(i);

           }

           nedssEvent.setEntity(entities);
           Object []  observationArray  = morbidityProxyVO.getTheObservationVOCollection().toArray();
           Act []  actArray = new Act[observationArray.length];
           for (int i = 0; i < observationArray.length; i++)
           {
            actArray[i] = (Act)  populateCastorActs(observationArray[i]) ;
           }
           nedssEvent.setAct(actArray);


           ObservationVO rootObservationVO = (ObservationVO) getRootObservationVO(morbidityProxyVO.getTheObservationVOCollection());
           if (NNDConstantUtil.notNull(rootObservationVO))
           {
               if ( NNDConstantUtil.notNull(rootObservationVO.getTheParticipationDTCollection()))
                    addToAllParticipations(rootObservationVO.getTheParticipationDTCollection());

              if (
                  NNDConstantUtil.notNull(rootObservationVO.getTheActRelationshipDTCollection())
                  &&
                  NNDConstantUtil.notNull(morbidityProxyVO.getTheObservationVOCollection())
                 )
             {

                 populateCastorActRelationShips(rootObservationVO.getTheActRelationshipDTCollection(), morbidityProxyVO.getTheObservationVOCollection());
                 if ( NNDConstantUtil.notNull(  allActRelationShips))
                {

                      ActRelationship[] actRelationship = new ActRelationship[allActRelationShips.size()];
                      for (int i = 0; i<actRelationship.length; i++)
                      {

                         actRelationship[i] = (ActRelationship) allActRelationShips.get(i);
                         System.out.println("Act relation ship of  " + i  +  " Act =  " +   allActRelationShips.get(i));

                      }
                 System.out.println("Size of act relation ship  after initialization = " + actRelationship.length);
                 System.out.println("Size of  ALL act relation ship   = " + allActRelationShips.size());
                 nedssEvent.setActRelationship(actRelationship);
               }

            }


         }
          if (NNDConstantUtil.notNull(allParticipations))
          {
           System.out.println("Inside array copy of participations ");
           Participation [] castorParticipations = new Participation[allParticipations.size()];
           System.arraycopy(allParticipations.toArray(),0,castorParticipations,0,castorParticipations.length);
           nedssEvent.setParticipation(castorParticipations);


          }
          NNDLDFMarshaller ldfMarshaller = new NNDLDFMarshaller();
           ldfMarshaller.marshallLDF(morbidityProxyVO, nedssEvent,localIDMap);
        }
        catch (NNDException nnde)
        {

         throw nnde;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            NNDException nndOther = new NNDException(" Non NNDException in marshallLabReport  " + e.getMessage());
            nndOther.setModuleName("NNDMarshallerLabReport.marshallLabReport");
            throw nndOther;

        }
         return nedssEvent;


   }


    private  Object  buildPersonEntity(Object personObj) throws NNDException
    {
            Entity castorEntity = new Entity();
            //PersonNameDT personName = new PersonNameDT();
            PersonVO personVO = (PersonVO) personObj;
            PersonDT personDT = new PersonDT();
            personDT = personVO.getThePersonDT();
            Person castorPerson = new Person();

            castorPerson.addParticipationTypeCd((String)partTypeCdTab.get(personDT.getPersonUid()));
       try {
            if (NNDConstantUtil.notNull(personDT))
            {
                //set person parent uid = null
                personDT.setPersonParentUid(null);
                castorPerson =   (Person) populateCastorObjects(personDT, castorPerson);
                localIDMap.put(personDT.getPersonUid(),personDT.getLocalId());
            }
            if (NNDConstantUtil.notNull( personVO.getThePersonEthnicGroupDTCollection()))
                 castorPerson.setPersonEthnicGroup(getEthnicGroupCollection(personVO.getThePersonEthnicGroupDTCollection()));

            if (NNDConstantUtil.notNull( personVO.getThePersonNameDTCollection()))
                castorPerson.setPersonName(getPersonNameCollection( personVO.getThePersonNameDTCollection()));

            if (NNDConstantUtil.notNull( personVO.getThePersonRaceDTCollection()))
                castorPerson.setPersonRace(getPersonRaceCollection( personVO.getThePersonRaceDTCollection()));

            if ( NNDConstantUtil.notNull(personVO.getTheEntityIdDTCollection()))
                castorEntity.setEntityId(getEntityIdCollection(personVO.getTheEntityIdDTCollection()));

            if ( NNDConstantUtil.notNull(personVO.getTheEntityLocatorParticipationDTCollection()))
                castorEntity.setEntityLocator(getEntityLocatorCollection(personVO.getTheEntityLocatorParticipationDTCollection()));


            castorEntity.setClassCd(NEDSSConstants.CLASS_CD_PSN);
            castorEntity.setEntityTempId(personDT.getLocalId());
            castorEntity.setPerson(castorPerson);
       }

       catch(NNDException nnde)
       {

        throw nnde;
       }

       catch(Exception e)
       {
        NNDException nndOther = new NNDException("Exception in buildPersonEntity " + e.getMessage());
        nndOther.setModuleName("NNDMarshallerLabReport.buildPersonEntity");
        throw nndOther;
       }
            return castorEntity;
    }




    private Object buildOrganizationEntity(Object organizationObj)throws NNDException
    {
     Entity castorEntity = new Entity();
     OrganizationVO organizationVO = (OrganizationVO) organizationObj;
     Organization castorOrganization = new Organization();
     try
     {

       if ( NNDConstantUtil.notNull(organizationVO.getTheEntityIdDTCollection()))
         castorEntity.setEntityId(getEntityIdCollection(organizationVO.getTheEntityIdDTCollection()));

       if ( NNDConstantUtil.notNull(organizationVO.getTheEntityLocatorParticipationDTCollection()))
         castorEntity.setEntityLocator(getEntityLocatorCollection(organizationVO.getTheEntityLocatorParticipationDTCollection()));

       if (NNDConstantUtil.notNull( organizationVO.getTheOrganizationNameDTCollection()))
         castorOrganization.setOrganizationName(getOrganizationNameCollection( organizationVO.getTheOrganizationNameDTCollection()));

       OrganizationDT  organizationDT =   organizationVO.getTheOrganizationDT();

       if (NNDConstantUtil.notNull(organizationDT))
       {
         castorOrganization =   (Organization) populateCastorObjects(organizationDT, castorOrganization);
         localIDMap.put(organizationDT.getOrganizationUid(),organizationDT.getLocalId());

       }
       castorEntity.setClassCd(NEDSSConstants.PAR102_SUB_CD);
       castorOrganization.addParticipationTypeCd((String)partTypeCdTab.get(organizationDT.getOrganizationUid()));
       castorEntity.setEntityTempId(organizationDT.getLocalId());
       castorEntity.setOrganization(castorOrganization);
     }
     catch(NNDException nnde)
    {
      throw nnde;
    }

    catch(Exception e)
    {
      NNDException nndOther = new NNDException("Exception in buildOrganizationEntity " + e.getMessage());
      nndOther.setModuleName("NNDMarshallerLabReport.buildOrganizationEntity");
      throw nndOther;
    }
     return castorEntity;
   }



private Observation getRootMorbObservation(Collection<Object> obsColl)throws NNDException{
 Iterator<Object>  anIterator = null;
  Observation o = null;
  for (anIterator = obsColl.iterator(); anIterator.hasNext(); ) {
    o = (Observation) anIterator.next();
    if (o.getCtrlCdDisplayForm().equalsIgnoreCase("MorbReport"))
      break;
  }
  if(o == null)
    throw new NNDException("not able to find root morb");
  else
    return o;

}

 // extract the acts and return an array of acts



private Act[] getActCollection(VaccinationProxyVO morbidityProxyVO) throws NNDException
{
 try
 {
  Collection<Object>  actCollection  = new ArrayList<Object> ();

   // extract observations and add them to a collection

  actCollection = extractObservations(morbidityProxyVO.getTheObservationVOCollection(),actCollection);

  // extract Interventions and add them to the same collection
   // (pass in the previous collection and add to it then pass it back)
 //

  Act[] actArr = new Act[actCollection.size()];
//
// first build an array but then convert it to an array of Act objects (String array)
//
 Iterator<Object>  iter = actCollection.iterator();
  int i= 0;
  while(iter.hasNext())
  {
     actArr[i] = (Act)iter.next();
     i++;
  }
  return  actArr;
 }catch(Exception e)
 {
     e.printStackTrace();
     throw new NNDException("error in NNDMarshallerVaccination.getActCollection  " + e.getMessage());
 }
}


 //extract the Participation and return an array of Participation

  private  Participation[] getParticipationCollection(VaccinationProxyVO vaccinationProxyVO) throws NNDException
  {
   try
   {
    Collection<Object>  participationCollection  = extractParticipations(vaccinationProxyVO.getTheInterventionVO().getTheParticipationDTCollection());
    Participation[] participationArr = new Participation[participationCollection.size()];
   Iterator<Object>  iter = participationCollection.iterator();

  // first build an array but then convert it to an array of Participation objects (String array)

    int i= 0;
    while(iter.hasNext())
    {
       participationArr[i] = (Participation)iter.next();
       i++;
    }
    return  participationArr;
   }catch(Exception e)
   {
       e.printStackTrace();
       throw new NNDException("error in NNDMarshallerVaccination.getActCollection  " + e.getMessage());
   }
  }


   //extract the ActRelationship and return an array of ActRelationship

  private  ActRelationship[] getActRelationshipCollection(VaccinationProxyVO vaccinationProxyVO) throws NNDException
  {
   try
   {
    Collection<Object>  actRelationshipCollection  = extractActRelationships(vaccinationProxyVO.getTheInterventionVO().getTheActRelationshipDTCollection());
    ActRelationship[] actRelationshipArr = new ActRelationship[actRelationshipCollection.size()];
   Iterator<Object>  iter = actRelationshipCollection.iterator();

   // first build an array but then convert it to an array of ActRelationship objects (String array)

     int i= 0;
    while(iter.hasNext())
    {
       actRelationshipArr[i] = (ActRelationship)iter.next();
       i++;
    }
  // logger.debug(" ---------- end of getActRelationshipCollection   ----------- ");

    return  actRelationshipArr;
   }catch(Exception e)
   {
       e.printStackTrace();
       throw new NNDException("error in NNDMarshallerVaccination.getActRelatioshipCollection  " + e.getMessage());
   }
  }


// extract the Entities and return a collection of Entitys - just persons and not the subject


  private Collection<Object>  extractEntities(Collection<Object> participationDTCollection, Collection<Object>  entityVOCollection, String entityType) throws NNDException
 {
  Iterator<Object>  itr = participationDTCollection.iterator();
   ArrayList<Object> entityCollection  = new ArrayList<Object> ();
try {

   while (itr.hasNext())
   {
      Object obj = itr.next();
      if (NNDConstantUtil.notNull(obj))
      {
//##!! System.out.println("((ParticipationDT)obj).getSubjectClassCd() = " + ((ParticipationDT)obj).getSubjectClassCd());
          if (entityType.equals("PSN")&&(((ParticipationDT)obj).getSubjectClassCd().equals("PSN")))
          {

             Long pentityUID  = ((ParticipationDT) obj).getSubjectEntityUid();
             Object  ppersonVObj = valueObjExtractor.getPersonValueObject(pentityUID, entityVOCollection);
             if(((PersonVO)ppersonVObj) != null)
             {
             localIDMap.put(((PersonVO)ppersonVObj).getThePersonDT().getUid(), ((PersonVO)ppersonVObj).getThePersonDT().getLocalId());
             }
             //Patient
             if (((ParticipationDT)obj).getTypeCd().equals("SubjOfMorbReport"))
             {
                  Long entityUID  = ((ParticipationDT) obj).getSubjectEntityUid();
                  System.out.println(" entityUID in Morb - person  " + entityUID);
                  partTypeCdTab.put(entityUID, ((ParticipationDT) obj).getTypeCd());
                  Object  personVObj = valueObjExtractor.getPersonValueObject(entityUID, entityVOCollection);
                  if (NNDConstantUtil.notNull(personVObj))
                  {
                    //addToAllRoles(valueObjExtractor.getPersonRoles(personVObj));
                     entityCollection.add(personVObj);
                   }

              }//Paitient if
              //Reporter
              else if (((ParticipationDT)obj).getTypeCd().equals("ReporterOfMorbReport"))// 3rd iff
              {
                Long entityUID  = ((ParticipationDT) obj).getSubjectEntityUid();
                System.out.println(" entityUID in Morb - person  " + entityUID);
                partTypeCdTab.put(entityUID, ((ParticipationDT) obj).getTypeCd());
                Object  personVObj = valueObjExtractor.getPersonValueObject(entityUID, entityVOCollection);
                if (NNDConstantUtil.notNull(personVObj))
               {
                  // addToAllRoles(valueObjExtractor.getPersonRoles(personVObj));
                   entityCollection.add(personVObj);
               }
             }//Reporter if
             //Provider
             else if (((ParticipationDT)obj).getTypeCd().equals("PhysicianOfMorb"))// 3rd iff
              {
                Long entityUID  = ((ParticipationDT) obj).getSubjectEntityUid();
                System.out.println(" entityUID in Morb - person  " + entityUID);
                partTypeCdTab.put(entityUID, ((ParticipationDT) obj).getTypeCd());
                Object  personVObj = valueObjExtractor.getPersonValueObject(entityUID, entityVOCollection);
                if (NNDConstantUtil.notNull(personVObj))
               {
                  // addToAllRoles(valueObjExtractor.getPersonRoles(personVObj));
                   entityCollection.add(personVObj);
               }
             }//Provider if

           } //entityType.equals("PSN") if
          else if (entityType.equals("ORG")&&(((ParticipationDT)obj).getSubjectClassCd().equals("ORG")))
             {
                      Long entityUID  = ((ParticipationDT) obj).getSubjectEntityUid();
                      //Reporting Facility
                      if(((ParticipationDT)obj).getTypeCd().equals("ReporterOfMorbReport")){
                        System.out.println(" Reporting Facility(ReporterOfMorbReport) entityid= " + entityUID);
                        partTypeCdTab.put(entityUID, ((ParticipationDT) obj).getTypeCd());
                        Object organizationVObj =   valueObjExtractor.getOrganizationValueObject(entityUID, entityVOCollection);
                        if (NNDConstantUtil.notNull(organizationVObj))
                        {
                        //  addToAllRoles(valueObjExtractor.getOrganizationRoles(organizationVObj));
                          entityCollection.add(organizationVObj);
                        }
                      }//Reporting Facility if
                      //Admitting Hospital
                      else if (((ParticipationDT)obj).getTypeCd().equals("HospOfMorbObs")){
                        System.out.println(" Admitting Hospital(HospOfMorbObs) entityid= " + entityUID);
                        partTypeCdTab.put(entityUID, ((ParticipationDT) obj).getTypeCd());
                        Object organizationVObj =   valueObjExtractor.getOrganizationValueObject(entityUID, entityVOCollection);
                        if (NNDConstantUtil.notNull(organizationVObj))
                        {
                        //  addToAllRoles(valueObjExtractor.getOrganizationRoles(organizationVObj));
                          entityCollection.add(organizationVObj);
                        }
                      }//Admitting Hospital if
                    }//entityType.equals("ORG") if
             }//NNDConstantUtil.notNull(obj) if
           } //while
         }//try
         catch(NNDException nnde)
         {
           throw nnde;

         }
         catch (Exception e)
         {
           e.printStackTrace();
           NNDException nndOther = new NNDException("Exception in NNDMarshallerLabReport " + e.getMessage());
           nndOther.setModuleName("NNDMarshallerLabReport.extractEntities");
           throw nndOther;
         }
  return entityCollection;
}

    //extract the Organizations and return a collection of Organizations

  private  Collection<Object>  extractOrganizations(Collection<Object> participationDTCollection, Collection<Object>  entityVOCollection,Collection<Object>  entityCollection) throws NNDException
  {
   try
   {
  Iterator<Object>  itr = participationDTCollection.iterator();

   while (itr.hasNext())
   {
       ParticipationDT participationDT = (ParticipationDT)itr.next();
       if (NNDConstantUtil.notNull(participationDT))
      {
      // logger.debug("participationDT.getSubjectClassCd() = " + participationDT.getSubjectClassCd());
           // For oraganization
           if (participationDT.getSubjectClassCd().equals(NEDSSConstants.ORGANIZATION_CLASS_CODE))
           {
              if ( participationDT.getTypeCd().equals(NEDSSConstants.PERFORMER_OF_VACCINE))
              {
                  Entity entityOrganization = this.getEntityFromOrganization(participationDT,entityVOCollection, NEDSSConstants.ORGANIZATION_CLASS_CODE,NEDSSConstants.PERFORMER_OF_VACCINE);
                  entityCollection.add(entityOrganization);
               }
           }
  } //1st IF
 }// while
 return entityCollection;
   }catch(Exception e)
   {
       e.printStackTrace();
       throw new NNDException("error in NNDMarshallerVaccination.extractOrganiztions " + e.getMessage());
   }
 }


  //  extract Observations

 private  Collection<Object>  extractObservations(Collection<ObservationVO> observationVOCollection,Collection<Object>  actCollection) throws NNDException
 {
  try
  {
       Iterator<ObservationVO>  iter =  observationVOCollection.iterator();
        while(iter.hasNext())
        {
           Act  act = new Act();
           ObservationVO observationVO = (ObservationVO)iter.next();
           if(NNDConstantUtil.notNull(observationVO.getTheActIdDTCollection()))
             act.setActId(getObservationActIds(observationVO.getTheActIdDTCollection()));
           act.setActTempId(observationVO.getTheObservationDT().getLocalId());
           act.setClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
           Observation  observation = new Observation();
          // populate the corresponding castor class from the DT
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
                  // populate the corresponding castor class from the DT
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
                  // populate the corresponding castor class from the DT
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
                  // populate the corresponding castor class from the DT
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
                  // populate the corresponding castor class from the DT
                  obsValueDate = (ObsValueDate)util.copyObjects(obsValueDateDT,obsValueDate);
                  observation.addObsValueDate(obsValueDate);
               }
           }
          //ObservationReason Collection
           Collection<Object>  obsReasonColl = observationVO.getTheObservationReasonDTCollection();
           if(obsReasonColl != null)
           {
              Iterator<Object>  obsReasonIter = obsReasonColl.iterator();
               while(obsReasonIter.hasNext())
               {
                  ObservationReasonDT observationReasonDT = (ObservationReasonDT)obsReasonIter.next();
                  ObservationReason observationReason = new ObservationReason();
                  // populate the corresponding castor class from the DT
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
                  // populate the corresponding castor class from the DT
                  observationInterp = (ObservationInterp)util.copyObjects(observationInterpDT,observationInterp);
                  observation.addObservationInterp(observationInterp);
               }
           }
           act.setObservation(observation);
           localIdTreeMap.put(observationVO.getTheObservationDT().getObservationUid(),observationVO.getTheObservationDT().getLocalId());
           actCollection.add(act);
        }
        return actCollection;
   }catch(Exception e)
   {
       e.printStackTrace();
       throw new NNDException("error in NNDMarshallerVaccination.extractObservations " + e.getMessage());
   }
  }

  /**
   * return collection of materials
   */
 private  Collection<Object>  extractMaterial(Collection<Object> participationDTCollection, MaterialVO materialVO,Collection<Object>  entityCollection) throws NNDException
 {
  try
  {
     Iterator<Object>  itr = participationDTCollection.iterator();

      while (itr.hasNext())
      {
          ParticipationDT participationDT = (ParticipationDT)itr.next();
         // For material
          if (participationDT.getSubjectClassCd().equals(NEDSSConstants.MATERIAL_CLASS_CODE))
          {
             //addToAllRoles(valueObjExtractor.getMaterialRoles(materialVO));
             Entity entity = new Entity();
             Material material = new Material();
             //ArrayList<Object> entityIdCollection  = new ArrayList<Object> ();
             entity.setEntityTempId(materialVO.getTheMaterialDT().getLocalId());
             entity.setClassCd(NEDSSConstants.MATERIAL_CLASS_CODE);
             //copy dt
             material.addParticipationTypeCd(NEDSSConstants.VACCINE_GIVEN);
          // populate the corresponding castor class from the DT
             material = (Material)util.copyObjects(materialVO.getTheMaterialDT(), material);
             entity.setMaterial(material);

             entity.setEntityId(getEntityIdCollection(materialVO.getTheEntityIdDTCollection()));
             entity.setEntityLocator(getEntityLocatorCollection(materialVO.getTheEntityLocatorParticipationDTCollection()));

             Collection<Object>  manufacturedMaterialColl = materialVO.getTheManufacturedMaterialDTCollection();
            Iterator<Object>  iter = manufacturedMaterialColl.iterator();
              // have to add the manufactured materials allso
             while(iter.hasNext())
             {
                  ManufacturedMaterialDT manufacturedMaterialDT = (ManufacturedMaterialDT)iter.next();
                  ManufacturedMaterial manufacturedMaterial = new ManufacturedMaterial();
                  // populate the corresponding castor class from the DT
                  manufacturedMaterial = (ManufacturedMaterial)util.copyObjects(manufacturedMaterialDT, manufacturedMaterial);
                  material.addManufacturedMaterial(manufacturedMaterial);
             }

             // Setting the treemap
System.out.println(" Before putting to the treemap vaccin - meterial key and value "+ materialVO.getTheMaterialDT().getMaterialUid() +"-" +materialVO.getTheMaterialDT().getLocalId());
             localIdTreeMap.put(materialVO.getTheMaterialDT().getMaterialUid(),materialVO.getTheMaterialDT().getLocalId());
             entityCollection.add(entity);
          }
      }
      return entityCollection;
   }catch(Exception e)
   {
       e.printStackTrace();
       throw new NNDException("error in NNDMarshallerVaccination.extractMaterial " + e.getMessage());
   }
  }

  /**
  * loop throgh the participationDTCollection  and
  * return a collection of castor participations
  */
private  Collection<Object>  extractParticipations(Collection<Object> participationDTCollection) throws NNDException
{

   Collection<Object>  partCollection  = new ArrayList<Object> ();
try {
  Iterator<Object>  iter =  participationDTCollection.iterator();
   while(iter.hasNext())
   {

      ParticipationDT participationDT = (ParticipationDT)iter.next();
     // if(!(participationDT.getTypeCd().equals(NEDSSConstants.SUBJECT_OF_VACCINE)))
      //{
        Participation  participation = new Participation();
        participation.setActTempId((String)localIdTreeMap.get(participationDT.getActUid()));
System.out.println("The subject entityTempId (in Vaccine )" + localIdTreeMap.get(participationDT.getSubjectEntityUid()));
        participation.setSubjectEntityTempId((String)localIdTreeMap.get(participationDT.getSubjectEntityUid()));
         // populate the corresponding castor class from the DT
        participation = (Participation)util.copyObjects(participationDT, participation);
        participation.setActTempId((String)localIdTreeMap.get(participationDT.getActUid()));
        participation.setSubjectEntityTempId((String)localIdTreeMap.get(participationDT.getSubjectEntityUid()));
        partCollection.add(participation);
      //}
   }
}
catch(Exception e)
{
      e.printStackTrace();
      throw new NNDException("error in NNDMarshallerVaccination.extractParticipations " + e.getMessage());
}
   return partCollection;
 }

 /**
  * loop throgh the actRelationshipDTCollection  and
  * return a castor actRelationship (collection)
  */
private  Collection<Object>  extractActRelationships(Collection<Object> actRelationshipDTCollection) throws NNDException
{
 try
 {
   Collection<Object>  actCollection  = new ArrayList<Object> ();
  Iterator<Object>  iter =  actRelationshipDTCollection.iterator();
   while(iter.hasNext())
   {
      ActRelationship  actRelationship = new ActRelationship();
      ActRelationshipDT actRelationshipDT = (ActRelationshipDT)iter.next();
     // populate the corresponding castor class from the DT
      actRelationship = (ActRelationship)util.copyObjects(actRelationshipDT, actRelationship);
      actRelationship.setTargetActTempId((String)localIdTreeMap.get(actRelationshipDT.getTargetActUid()));
      actRelationship.setSourceActTempId(((String)localIdTreeMap.get(actRelationshipDT.getSourceActUid())));
      actCollection.add(actRelationship);
   }
   return actCollection;
  }catch(Exception e)
  {
      e.printStackTrace();
      throw new NNDException("error in NNDMarshallerVaccination.extractActRelationships " + e.getMessage());
  }
 }

 /**
 * load up a castor Entity from the organizationDT
 */
private Entity  getEntityFromOrganization(ParticipationDT participationDT,Collection<Object>  entityVOCollection,String classCode, String typeCode) throws NNDException
{
    try
    {
       Entity entity = new Entity();
       Organization organization = new Organization();
       //ArrayList<Object> entityIdCollection  = new ArrayList<Object> ();
       Long entityUID  = participationDT.getSubjectEntityUid();
       OrganizationVO organizationVO = (OrganizationVO)valueObjExtractor.getOrganizationValueObject(entityUID, entityVOCollection);
        // commented out since there are no roles in MorbReport
        //addToAllRoles(valueObjExtractor.getOrganizationRoles(organizationVO));
         entity.setEntityTempId(organizationVO.getTheOrganizationDT().getLocalId());
         entity.setClassCd(classCode);
         //OrganizationDT organizationDT = new OrganizationDT();
         // populate the corresponding castor class from the DT
         organization = (Organization) util.copyObjects(organizationVO.
             getTheOrganizationDT(), organization);
         organization.addParticipationTypeCd(typeCode);
         //Organization name
         Collection<Object>  organizationNameColl = organizationVO.
             getTheOrganizationNameDTCollection();
        Iterator<Object>  nameIter = organizationNameColl.iterator();
         while (nameIter.hasNext()) {
           OrganizationNameDT organizationNameDT = (OrganizationNameDT) nameIter.
               next();
           OrganizationName organizationName = new OrganizationName();
           // populate the corresponding castor class from the DT
           organizationName = (OrganizationName) util.copyObjects(
               organizationNameDT, organizationName);
           organization.addOrganizationName(organizationName);
         }
         entity.setOrganization(organization);
         entity.setEntityId(getEntityIdCollection(organizationVO.
                                                  getTheEntityIdDTCollection()));
         entity.setEntityLocator(getEntityLocatorCollection(organizationVO.
             getTheEntityLocatorParticipationDTCollection()));
         System.out.println(
             "Setting the tree map in Vaccin organization key and value " +
             organizationVO.getTheOrganizationDT().getOrganizationUid() + " " +
             organizationVO.getTheOrganizationDT().getLocalId());
         // Setting the treemap
         localIdTreeMap.put(organizationVO.getTheOrganizationDT().
                            getOrganizationUid(),
                            organizationVO.getTheOrganizationDT().getLocalId());
         return entity;


 }catch(Exception e)
 {
     e.printStackTrace();
     throw new NNDException("error in NNDMarshallerVaccination.getEntityFromOrganization " + e.getMessage());
 }
}

  /**
 * load the castor Entity from the personDT
 *commented out 01/07/2004 since it is  not being used within the class */
/*private Entity  getEntityFromPerson(ParticipationDT participationDT,Collection<Object>  entityVOCollection,String classCode, String typeCode) throws NNDException
{
 try
 {
       Entity entity = new Entity();
       Person person = new Person();
       //ArrayList<Object> entityIdCollection  = new ArrayList<Object> ();
       Long entityUID  = participationDT.getSubjectEntityUid();
       PersonVO personVO = (PersonVO)valueObjExtractor.getPersonValueObject(entityUID, entityVOCollection);
       addToAllRoles(valueObjExtractor.getPersonRoles(personVO));

       entity.setEntityTempId(personVO.getThePersonDT().getLocalId());
       entity.setClassCd(classCode);
        person.addParticipationTypeCd(typeCode);
        // populate the corresponding castor class from the DT
        person = (Person)util.copyObjects(personVO.getThePersonDT(),person);
        // Adding personNameDT Collection<Object>  to the castor class
        Collection<Object>  personNameColl = personVO.getThePersonNameDTCollection();
              if(personNameColl != null)
            {

           Iterator<Object>  nameIter = personNameColl.iterator();
            while(nameIter.hasNext())
            {
                 PersonNameDT personNameDT = (PersonNameDT)nameIter.next();
                 PersonName personName = new PersonName();
                // populate the corresponding castor class from the DT
                 personName =(PersonName)util.copyObjects(personNameDT,personName);
                 person.addPersonName(personName);

            }
        }
        // Adding personEthnicGroupDT Collection<Object>  to the castor class
        Collection<Object>  personEthnicColl = personVO.getThePersonEthnicGroupDTCollection();
        if(personEthnicColl != null)
            {

           Iterator<Object>  ethnicIter = personEthnicColl.iterator();
            while(ethnicIter.hasNext())
            {
                PersonEthnicGroupDT PersonEthnicGroupDT = (PersonEthnicGroupDT)ethnicIter.next();
                PersonEthnicGroup  personEthnicGroup = new PersonEthnicGroup();
                // populate the corresponding castor class from the DT
                personEthnicGroup = (PersonEthnicGroup)util.copyObjects(PersonEthnicGroupDT,personEthnicGroup);
                person.addPersonEthnicGroup(personEthnicGroup);

            }
        }

        // Adding personRaceDT Collection<Object>  to the castor class
        Collection<Object>  raceColl = personVO.getThePersonRaceDTCollection();
       if(raceColl != null)
       {
      Iterator<Object>  raceIter = raceColl.iterator();
            while(raceIter.hasNext())
            {
                 PersonRaceDT personRaceDT = (PersonRaceDT)raceIter.next();
                 PersonRace personRace = new PersonRace();
                // populate the corresponding castor class from the DT
                 personRace = (PersonRace)util.copyObjects(personRaceDT,personRace);
                 person.addPersonRace(personRace);
            }
        }

       entity.setEntityId(getEntityIdCollection(personVO.getTheEntityIdDTCollection()));
       entity.setEntityLocator(getEntityLocatorCollection(personVO.getTheEntityLocatorParticipationDTCollection()));
       entity.setPerson(person);
       // Setting the treemap
System.out.println("Setting the tree map in Vaccin person key and value "+  personVO.getThePersonDT().getPersonUid() +" " +personVO.getThePersonDT().getLocalId());
       localIdTreeMap.put(personVO.getThePersonDT().getPersonUid(),personVO.getThePersonDT().getLocalId());
       return entity;
 }catch(Exception e)
 {
     e.printStackTrace();
     throw new NNDException("error in NNDMarshallerVaccination.getEntityFromPerson " + e.getMessage());
 }
}
*/
  private EntityLocator[] getEntityLocatorCollection(Collection<Object> entityLocatorCollection) throws NNDException
{
 try
 {
 Iterator<Object>  iter = entityLocatorCollection.iterator();
      ArrayList<Object> entityLocatorArray = new ArrayList<Object> ();
      if(entityLocatorCollection  != null)
      {
     while(iter.hasNext())
     {
         EntityLocatorParticipationDT  entityLocatorParticipationDT = (EntityLocatorParticipationDT)iter.next();
         EntityLocator entityLocator = new EntityLocator();
        // populate the corresponding castor class from the DT
         entityLocator = (EntityLocator)util.copyObjects(entityLocatorParticipationDT,entityLocator);
         if(entityLocatorParticipationDT.getTheTeleLocatorDT() != null)
         {
             TeleLocatorType teleLocatorType = new TeleLocatorType();
            // populate the corresponding castor class from the DT
             teleLocatorType = (TeleLocatorType)util.copyObjects(entityLocatorParticipationDT.getTheTeleLocatorDT(),teleLocatorType);
             entityLocator.setTeleLocatorType(teleLocatorType);
         }
         if(entityLocatorParticipationDT.getThePostalLocatorDT() != null)
         {
             PostalLocatorType postalLocatorType = new PostalLocatorType();
            // populate the corresponding castor class from the DT
             postalLocatorType = (PostalLocatorType)util.copyObjects(entityLocatorParticipationDT.getThePostalLocatorDT(),postalLocatorType);
             entityLocator.setPostalLocatorType(postalLocatorType);
         }
         if(entityLocatorParticipationDT.getThePhysicalLocatorDT() != null)
         {
             PhysicalLocatorType physicalLocatorType = new PhysicalLocatorType();
            // populate the corresponding castor class from the DT
             physicalLocatorType = (PhysicalLocatorType)util.copyObjects(entityLocatorParticipationDT.getThePhysicalLocatorDT(),physicalLocatorType);
             entityLocator.setPhysicalLocatorType(physicalLocatorType);
         }

             entityLocatorArray.add(entityLocator);
             entityLocator = null;
          }
          }
      EntityLocator entityLocatorArr[] = new EntityLocator[entityLocatorCollection.size()];
     Iterator<Object>  it = entityLocatorArray.iterator();
/**
 * first build an array but then convert it to an array of EntityLocator objects (String array)
 */
       int i = 0;
      while(it.hasNext())
      {
        entityLocatorArr[i] = (EntityLocator)it.next();
            i++;
      }
      return entityLocatorArr;
 }catch(Exception e)
 {
     e.printStackTrace();
     throw new NNDException("error in NNDMarshallerVaccination.getEntityLocatorCollection  " + e.getMessage());
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

              if (NNDConstantUtil.notNull(castorRole.getScopingEntityTempId()))
             {     Long entityUid = new Long (castorRole.getScopingEntityTempId());
                   if (localIdTreeMap.containsKey(entityUid))
                       castorRole.setScopingEntityTempId(localIdTreeMap.get(entityUid).toString());
              }

               if (NNDConstantUtil.notNull(castorRole.getSubjectEntityTempId()))
              {  Long entityUid = new Long (castorRole.getSubjectEntityTempId());
                 if (localIdTreeMap.containsKey(entityUid))
                     castorRole.setSubjectEntityTempId(localIdTreeMap.get(entityUid).toString());
               }

             castorRoles[i] = castorRole;

        }


        return castorRoles;
    }

   }
   return null;
}

  catch(Exception e)
   {
   e.printStackTrace();
   throw new NNDException("Error in marshallVaccination, setAllRoles() method  "+e.getMessage());
   }

}

 private  ObsValueCoded [] getObsValueCodedCollection(Collection<Object> obsValueCodedColl) throws NNDException
 {
 ObsValueCoded [] obsValueCoded  = new ObsValueCoded[obsValueCodedColl.size()];
 ArrayList<Object> arrayList = (ArrayList<Object> ) obsValueCodedColl;
Iterator<Object>  itr = null;
 itr = arrayList.iterator();
 int i = 0;
try
{

  while (itr.hasNext())
 {
        ObsValueCodedDT obsValueCodedDT = new ObsValueCodedDT();
        ObsValueCoded castorObsValueCoded = new ObsValueCoded();
        obsValueCodedDT =  (ObsValueCodedDT)itr.next();
        if (NNDConstantUtil.notNull(obsValueCodedDT))
        {

            castorObsValueCoded =  (ObsValueCoded) populateCastorObjects(obsValueCodedDT, castorObsValueCoded);


            obsValueCoded[i] = castorObsValueCoded;
            i++;
        }
 }


}
 catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in getObsValueCodedCollection  " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.getObsValueCodedCollection");
    throw nndOther;
  }

  return obsValueCoded;
 }

 private  ObsValueDate [] getObsValueDateCollection(Collection<Object> obsValueDateColl) throws NNDException
 {
 ObsValueDate [] obsValueDate  = new ObsValueDate[obsValueDateColl.size()];
 ArrayList<Object> arrayList = (ArrayList<Object> ) obsValueDateColl;
Iterator<Object>  itr = null;
 itr = arrayList.iterator();
 int i = 0;
try
{
 while (itr.hasNext())
 {
        ObsValueDateDT obsValueDateDT = new ObsValueDateDT();
        ObsValueDate castorObsValueDate = new ObsValueDate();
        obsValueDateDT =  (ObsValueDateDT)itr.next();
        if (NNDConstantUtil.notNull(obsValueDateDT))
        {
            castorObsValueDate =  (ObsValueDate) populateCastorObjects(obsValueDateDT, castorObsValueDate);
            obsValueDate[i] = castorObsValueDate;
            i++;
        }
 }
}
 catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in getObsValueDateCollection  " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.getObsValueDateCollection");
    throw nndOther;
  }
 return obsValueDate;
 }
  private  ObsValueNumeric [] getObsValueNumericCollection(Collection<Object> obsValueNumericColl) throws NNDException
 {
 ObsValueNumeric [] obsValueNumeric  = new ObsValueNumeric[obsValueNumericColl.size()];
 ArrayList<Object> arrayList = (ArrayList<Object> ) obsValueNumericColl;
Iterator<Object>  itr = null;
 itr = arrayList.iterator();
 int i = 0;
try
{
  while (itr.hasNext())
 {
        ObsValueNumericDT obsValueNumericDT = new ObsValueNumericDT();
        ObsValueNumeric castorObsValueNumeric = new ObsValueNumeric();
        obsValueNumericDT =  (ObsValueNumericDT)itr.next();
        if (NNDConstantUtil.notNull(obsValueNumericDT))
        {
            castorObsValueNumeric =  (ObsValueNumeric) populateCastorObjects(obsValueNumericDT, castorObsValueNumeric);
            obsValueNumeric[i] = castorObsValueNumeric;
            i++;
        }
 }
}
 catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in getObsValueNumericCollection  " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.getObsValueNumericCollection");
    throw nndOther;
  }
 return obsValueNumeric;
 }

private  ObsValueText [] getObsValueTextCollection(Collection<Object> obsValueTextColl) throws NNDException
 {
 ObsValueText [] obsValueText  = new ObsValueText[obsValueTextColl.size()];
 ArrayList<Object> arrayList = (ArrayList<Object> ) obsValueTextColl;
Iterator<Object>  itr = null;
 itr = arrayList.iterator();
 int i = 0;
try
{
 while (itr.hasNext())
 {
        ObsValueTxtDT obsValueTextDT = new ObsValueTxtDT();
        ObsValueText castorObsValueText = new ObsValueText();
        obsValueTextDT =  (ObsValueTxtDT)itr.next();
        if (NNDConstantUtil.notNull(obsValueTextDT))
        {
            castorObsValueText =  (ObsValueText) populateCastorObjects(obsValueTextDT, castorObsValueText);
            obsValueText[i] = castorObsValueText;
            i++;
        }
 }
}
 catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in getObsValueTextCollection  " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.getObsValueTextCollection");
    throw nndOther;
  }
 return obsValueText;
 }

  private  ObservationReason [] getObservationReasonCollection(Collection<Object> observationReasonColl) throws NNDException
 {
 ObservationReason [] observationReason = new ObservationReason[observationReasonColl.size()];

 ArrayList<Object> arrayList = (ArrayList<Object> ) observationReasonColl;
Iterator<Object>  itr = null;
 itr = arrayList.iterator();
 int i = 0;
try
{
  while (itr.hasNext())
 {
        ObservationReasonDT observationReasonDT = new ObservationReasonDT();
        ObservationReason castorObservationReason = new ObservationReason();
        observationReasonDT =  (ObservationReasonDT)itr.next();
        if (NNDConstantUtil.notNull(observationReasonDT))
        {
            castorObservationReason =  (ObservationReason) populateCastorObjects(observationReasonDT, castorObservationReason);
            observationReason[i] = castorObservationReason;
            i++;
        }
 }
}
 catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in getObservationReasoCollection  " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.getObservationReasonCollection");
    throw nndOther;
  }
 return observationReason;
 }

 private  ObservationInterp[] getObservationInterpCollection(Collection<Object> observationInterpColl) throws NNDException
 {
 ObservationInterp [] observationInterp = new ObservationInterp[observationInterpColl.size()];
 ArrayList<Object> arrayList = (ArrayList<Object> ) observationInterpColl;
Iterator<Object>  itr = null;
 itr = arrayList.iterator();
 int i = 0;
try
{
  while (itr.hasNext()) {

        ObservationInterpDT observationInterpDT = new ObservationInterpDT();

        ObservationInterp castorObservationInterp = new ObservationInterp();

        observationInterpDT =  (ObservationInterpDT)itr.next();

        if (NNDConstantUtil.notNull(observationInterpDT))
        {
            castorObservationInterp =  (ObservationInterp) populateCastorObjects(observationInterpDT, castorObservationInterp);
            observationInterp[i] = castorObservationInterp;
            i++;
        }
 }
}
 catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in getObservationInterpCollection  " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.getObservationInterpCollection");
    throw nndOther;
  }
 return observationInterp;
 }

 private void populateCastorActRelationShips(Collection<Object> rootActRelationShips, Collection<ObservationVO>  observationVOColl) throws NNDException
{
    ArrayList<Object> resultUIDs = null;
   Iterator<Object>  resultItr =  null;
try
{
    //process all act relationship collections and return set of Lab Report Act Id's
    resultUIDs =  (ArrayList<Object> )  processActRelationShips(rootActRelationShips, "LabReport");
    if (NNDConstantUtil.notNull(resultUIDs))
    {
      resultItr = resultUIDs.iterator();
      while (resultItr.hasNext())

      {
        //process the act participation for each Ordered Test and get the AR Collection
        ArrayList<Object> isolateActRelationShips = (ArrayList<Object> )getActRelationShips(observationVOColl, (Long) resultItr.next());
        //process the AR Collection<Object>  for each Lab Report
        ArrayList<Object> isolateUIDs = (ArrayList<Object> ) processActRelationShips(isolateActRelationShips, "COMP");
        //isolateUIDs should be null
       /* if (NNDConstantUtil.notNull(isolateUIDs))
        {
          Iterator<Object>   isolateItr = isolateUIDs.iterator();

           while (isolateItr.hasNext())
           {
              ArrayList<Object> suseptActRelationShips = (ArrayList<Object> )getActRelationShips(observationVOColl, (Long) isolateItr.next());
              ArrayList<Object> suseptUIDs  = (ArrayList<Object> ) processActRelationShips(suseptActRelationShips, NEDSSConstants.ACT110_TYP_CD);
           } //2nd while

        } */// 2nd If

      }//1st While

    }//1stIF
 }
   catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in populateCastorActRelationShips " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.populateCastorActRelationShips");
    throw nndOther;
  }
}

private Collection<Object>   processActRelationShips( Collection<Object>  actRelationShips, String typeCd) throws NNDException
{
   ArrayList<Object> actRelationShipList = (ArrayList<Object> ) actRelationShips;
   ArrayList<Object> observationUIds = new ArrayList<Object> ();
  Iterator<Object>  itr = null;
   itr = actRelationShipList.iterator();
   System.out.println("Actrelation size  +++++ " + actRelationShipList.size());
try
{
      while (itr.hasNext() )
   {
      ActRelationshipDT actRelationShipDT = new ActRelationshipDT();
      actRelationShipDT = (ActRelationshipDT) itr.next();
      ActRelationship castorActRelationShip = new ActRelationship();
      System.out.println("Act Relation Ship DT  &&&&&& ***** " + actRelationShipDT.getSourceClassCd());
      //do not process morb treatments as per spec
      if(NNDConstantUtil.notNull(actRelationShipDT)&&!(actRelationShipDT.getTypeCd().equals("TreatmentToMorb")))
      {

        System.out.println("Act Relation Ship is not null ******************** " + actRelationShipDT.getTypeCd());
        castorActRelationShip = (ActRelationship) populateCastorObjects(actRelationShipDT, castorActRelationShip);
        castorActRelationShip.setTargetActTempId((String) localIDMap.get(actRelationShipDT.getTargetActUid()));
        castorActRelationShip.setSourceActTempId( (String) localIDMap.get(actRelationShipDT.getSourceActUid()));

        if (actRelationShipDT.getTypeCd().equals(typeCd))
       {
           observationUIds.add(actRelationShipDT.getSourceActUid());
       }
       allActRelationShips.add(actIndex,castorActRelationShip);
       actIndex++;
       System.out.println("Now the Size is  " +  allActRelationShips.size());
     }
   }
}
   catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in processActRelationShips " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.processActRelationShips");
    throw nndOther;
  }
   if (observationUIds.size() > 0 )
      return observationUIds;
   else
      return null;
}

private  Object getRootObservationVO(Collection<ObservationVO> observationColl) throws NNDException
{

 ArrayList<ObservationVO> observationVOColl = (ArrayList<ObservationVO> ) observationColl;
Iterator<ObservationVO>  itr = observationVOColl.iterator();


   while (itr.hasNext())
   {
    ObservationVO observationVO = (ObservationVO) itr.next();
    if ((observationVO.getTheObservationDT().getCtrlCdDisplayForm().equals("MorbReport")))
      return observationVO;
   }
return null;
}

private  Object populateCastorActs( Object observationObj) throws NNDException
{

  ObservationVO observationVO = (ObservationVO) observationObj;

  Observation castorObservation = new Observation();
  ObservationDT observationDT = observationVO.getTheObservationDT();
  Act act = new Act();
    if (NNDConstantUtil.notNull(observationDT))
    {
 try
 {
      localIDMap.put(observationDT.getObservationUid(), observationDT.getLocalId());
      castorObservation =  (Observation)populateCastorObjects(observationDT, castorObservation);
      if(NNDConstantUtil.notNull(observationVO.getTheActIdDTCollection()))
         act.setActId(getObservationActIds(observationVO.getTheActIdDTCollection()));
      if (NNDConstantUtil.notNull(observationVO.getTheObservationInterpDTCollection()))
         castorObservation.setObservationInterp(getObservationInterpCollection(observationVO.getTheObservationInterpDTCollection()));
      if (NNDConstantUtil.notNull(observationVO.getTheObsValueCodedDTCollection()))
         castorObservation.setObsValueCoded(getObsValueCodedCollection(observationVO.getTheObsValueCodedDTCollection()));
      if (NNDConstantUtil.notNull(observationVO.getTheObservationReasonDTCollection()))
          castorObservation.setObservationReason(getObservationReasonCollection(observationVO.getTheObservationReasonDTCollection()));
      if (NNDConstantUtil.notNull(observationVO.getTheObsValueDateDTCollection()))
          castorObservation.setObsValueDate(getObsValueDateCollection(observationVO.getTheObsValueDateDTCollection()));
      if (NNDConstantUtil.notNull(observationVO.getTheObsValueNumericDTCollection()))
          castorObservation.setObsValueNumeric(getObsValueNumericCollection(observationVO.getTheObsValueNumericDTCollection()));
      if (NNDConstantUtil.notNull(observationVO.getTheObsValueTxtDTCollection()))
          castorObservation.setObsValueText(getObsValueTextCollection(observationVO.getTheObsValueTxtDTCollection()));

 }
      catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in populateCastorActs " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerMorbReport.populateCastorActs");
    throw nndOther;
  }
}

 act.setClassCd(NEDSSConstants.OBSERVATION_CLASS_CODE);
 act.setActTempId(observationDT.getLocalId());
 act.setObservation(castorObservation);
 return act;
}
  private void  populateCastorEntities (Collection<Object> entityCollection, String entityType ) throws NNDException
{
    ArrayList<Object> entityVOList = (ArrayList<Object> ) entityCollection;
   Iterator<Object>  itr = entityVOList.iterator();
try
{
    while (itr.hasNext())
    {
       if (entityType.equals("PSN"))
       {
           PersonVO personVO = (PersonVO) itr.next();
           allEntities.add(  buildPersonEntity(personVO));
       }
       if (entityType.equals("ORG"))
       {

           OrganizationVO organizationVO = (OrganizationVO) itr.next();
           allEntities.add(  buildOrganizationEntity(organizationVO));

       }

    }
}
 catch(NNDException nnde)
 {
   throw nnde;
 }

 catch(Exception e)
 {
   NNDException nndOther = new NNDException("Exception in populateCastorEntities " + e.getMessage());
   nndOther.setModuleName("NNDMarshallerLabReport.populateCastorEntities");
   throw nndOther;
 }
}


private Object  populateCastorParticipations(Object participationObj) throws NNDException

{


  ParticipationDT participationDT = (ParticipationDT) participationObj;
  Participation castorParticipation = new Participation();
 //if(!(participationDT.getTypeCd().equals(NEDSSConstants.PAR110_TYP_CD)))
  //{

   try
   {
      if (NNDConstantUtil.notNull(participationDT)) {
System.out.println("From Participation lab ,,subjectEntityUID before adding "+ localIDMap.get(participationDT.getSubjectEntityUid()));
          castorParticipation = (Participation) populateCastorObjects(participationDT, castorParticipation);
          castorParticipation.setActTempId( (String) localIDMap.get(participationDT.getActUid()));
          castorParticipation.setSubjectEntityTempId((String)localIDMap.get(participationDT.getSubjectEntityUid()));
      }

  }

   catch(NNDException nnde)
   {

    throw nnde;
   }

   catch(Exception e)
   {
    NNDException nndOther = new NNDException("Exception in populateCastorParticipations " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.populateCastorParticipations");
    throw nndOther;
   }

//}  // for excluding the participation subjectof Labreport
  return castorParticipation;
}

private  Object populateCastorObjects(Object nbsObject, Object castorObject) throws NNDException
 {
   Object returnObj = null;
   try
   {
          NNDUtils nndUtil = new NNDUtils();

          returnObj =  (nndUtil.copyObjects( nbsObject,  castorObject));

   }

    catch (NNDException nnde)
    {

      throw nnde;
    }

    catch (Exception e)
    {
     NNDException nndOther = new NNDException("Exception in populate castorObjects " + e.getMessage());
     nndOther.setModuleName("NNDMarshallerLabReport.populateCastorObjects"  );
     throw nndOther;

    }

   return returnObj;
 }
 /**
  * commented out since at this point there are no roles in MorbReports
  */
/*
 private  void addToAllRoles(Collection<Object> roleCollection) throws NNDException
 {


   try
   {
      if (NNDConstantUtil.notNull(roleCollection))

      {
       if(!roleCollection.isEmpty())

       {
        Iterator<Object>  itr = roleCollection.iterator();

         while(itr.hasNext())
          {

           Object obj = itr.next();
           if (! allRoles.contains(obj))
                  allRoles.add(obj);



          }


       }


      }

   }


    catch (Exception e)
    {
     NNDException nndOther = new NNDException("Exception in addToAllRoles " + e.getMessage());
     nndOther.setModuleName("NNDMarshallerLabReport.addToAllRoles"  );
     throw nndOther;

    }


 }
      */
 private void addToAllParticipations(Collection<Object> participationDTColl) throws NNDException
{

try
{
 Iterator<Object>  itr = participationDTColl.iterator();
  while(itr.hasNext())

 {

  ParticipationDT participationDT = (ParticipationDT) itr.next();
  if (NNDConstantUtil.notNull(participationDT))
  {
     allParticipations.add(populateCastorParticipations(participationDT));

   }


 }

 }

catch (Exception e)

{
  e.printStackTrace();
  NNDException nndeOther = new NNDException("Exception while adding to particpations " + e.getMessage());
  nndeOther.setModuleName("NNDMarshallerLabReport.addToAllParticipations");
  throw nndeOther;

}
}

  private  ActId[] getObservationActIds(Collection<Object> actIDDTColl) throws NNDException
 {

      ActId [] castorActIds = new ActId[actIDDTColl.size()];
      ArrayList<Object> actIDDTList  = (ArrayList<Object> ) actIDDTColl;
try {

      for  (int i=0; i< castorActIds.length; i++)

      {
         ActIdDT  actIdDT = (ActIdDT) actIDDTList.get(i);
       //Added to fix ER# 8815 - 03/25/2016
         if(actIdDT!=null && actIdDT.getAssigningAuthorityCd()!=null && actIdDT.getAssigningAuthorityCd().length()>20){
      	   actIdDT.setAssigningAuthorityCd(actIdDT.getAssigningAuthorityCd().substring(0,19));
         }
         if ( NNDConstantUtil.notNull(actIdDT))
         {

           ActId castorActId = new ActId();
           castorActIds[i] = (ActId) populateCastorObjects(actIdDT, castorActId);
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
   nndOther.setModuleName("NNDMarshallerLabReport.getObservationActIds");

  }
      return castorActIds;
 }

 private  Collection<Object>  getActRelationShips(Collection<ObservationVO> observationVOColl, Long observationUID) throws NNDException
{
     ArrayList<ObservationVO> observationVOList = (ArrayList<ObservationVO> ) observationVOColl;
    Iterator<ObservationVO>  itr = observationVOList.iterator();
try {

     while (itr.hasNext())

     {
        ObservationVO observationVO = (ObservationVO)itr.next();
        if ( observationVO.getTheObservationDT().getObservationUid().equals(observationUID))
        {
          if (NNDConstantUtil.notNull(observationVO.getTheParticipationDTCollection()))
                addToAllParticipations(observationVO.getTheParticipationDTCollection());

          return observationVO.getTheActRelationshipDTCollection();
        }
     }
   }
   catch(NNDException nnde)
   {
        throw nnde;
   }
   catch( Exception e)
   {

    NNDException nndeOther = new NNDException("Exception in get Act relation ships " + e.getMessage());
    nndeOther.setModuleName("NNDMarshallerLabReport.getActRelationShips");
    throw nndeOther;

   }
     return null;
}

private  PersonRace[] getPersonRaceCollection(Collection<Object> personRaceColl) throws NNDException
{
 PersonRace personRace [] = new PersonRace[personRaceColl.size()];
 ArrayList<Object> arrayList = (ArrayList<Object> ) personRaceColl;
Iterator<Object>  itr = null;
 itr = arrayList.iterator();
 int i = 0;
try
{

 while (itr.hasNext()) {

        PersonRaceDT personRaceDT = new PersonRaceDT();
        PersonRace castorPersonRace = new PersonRace();

        personRaceDT =  (PersonRaceDT)itr.next();
        if (NNDConstantUtil.notNull(personRaceDT))
        {
            castorPersonRace =  (PersonRace) populateCastorObjects(personRaceDT, castorPersonRace);
            personRace[i] = castorPersonRace;
            i++;
        }
 }
}
catch(NNDException nnde)
{
throw nnde;
}

catch(Exception e)
{
NNDException nndOther = new NNDException("Exception in getPersonRaceCollection  " + e.getMessage());
nndOther.setModuleName("NNDMarshallerLabReport.getPersonRaceCollection");
throw nndOther;
}
 return personRace;
}

private  EntityId[] getEntityIdCollection(Collection<Object> entityIdColl) throws NNDException
{
EntityId [] entityId = new EntityId[entityIdColl.size()];
ArrayList<Object> arrayList = (ArrayList<Object> ) entityIdColl;
Iterator<Object> itr = null;
itr = arrayList.iterator();
int i = 0;
try
{
while (itr.hasNext())
{
    EntityIdDT entityIdDT = new EntityIdDT();
    EntityId castorEntityId = new EntityId();

    entityIdDT =  (EntityIdDT)itr.next();
    if(entityIdDT!=null && entityIdDT.getAssigningAuthorityCd()!=null && entityIdDT.getAssigningAuthorityCd().length()>20){
    	entityIdDT.setAssigningAuthorityCd(entityIdDT.getAssigningAuthorityCd().substring(0,19));
    }
    if (NNDConstantUtil.notNull(entityIdDT))
    {
        castorEntityId =  (EntityId) populateCastorObjects(entityIdDT, castorEntityId);
        entityId[i] = castorEntityId;
        i++;
    }
}
}
catch(NNDException nnde)
{
throw nnde;
}

catch(Exception e)
{
NNDException nndOther = new NNDException("Exception in getEntityIdCollection" + e.getMessage());
nndOther.setModuleName("NNDMarshallerLabReport.getEntityIdCollection");
throw nndOther;
}
return entityId;
}

  private  PersonName[] getPersonNameCollection(Collection<Object> personNameColl) throws NNDException
 {
     PersonName personName [] = new PersonName[personNameColl.size()];
     ArrayList<Object> arrayList = (ArrayList<Object> ) personNameColl;
    Iterator<Object>  itr = null;
     itr = arrayList.iterator();
     int i = 0;
 try
 {

     while (itr.hasNext())
     {
            PersonNameDT personNameDT = new PersonNameDT();
            PersonName castorPersonName = new PersonName();

            personNameDT =  (PersonNameDT)itr.next();
            if (NNDConstantUtil.notNull(personNameDT))
            {
                castorPersonName =  (PersonName) populateCastorObjects(personNameDT, castorPersonName);
                personName[i] = castorPersonName;
                i++;
            }
     }
 }
  catch(NNDException nnde)
  {
    throw nnde;

  }
  catch (Exception e)
  {
    NNDException nndOther = new NNDException("Exception in getPersonNameCollection  " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.getPersonNameCollection" );
    throw nndOther;
  }
     return personName;
 }
   private  OrganizationName[] getOrganizationNameCollection(Collection<Object> organizationNameColl) throws NNDException
 {
     OrganizationName organizationName [] = new OrganizationName[organizationNameColl.size()];
     ArrayList<Object> arrayList = (ArrayList<Object> ) organizationNameColl;
    Iterator<Object>  itr = null;
     itr = arrayList.iterator();
     int i = 0;
try
 {
     while (itr.hasNext()) {

            //OrganizationNameDT organizationNameDT = new OrganizationNameDT();
            OrganizationName castorOrganizationName = new OrganizationName();

            OrganizationNameDT organizationNameDT =  (OrganizationNameDT)itr.next();
            if (NNDConstantUtil.notNull(organizationNameDT))
            {
                castorOrganizationName =  (OrganizationName) populateCastorObjects(organizationNameDT, castorOrganizationName);
                organizationName[i] = castorOrganizationName;
                i++;
            }
     }
  }

  catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in getOrganizationNameCollection  " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.getOrganizationNameCollection");
    throw nndOther;
  }
     return organizationName;
 }


 private  PersonEthnicGroup[] getEthnicGroupCollection(Collection<Object> ethnicGroupColl) throws NNDException
{
    PersonEthnicGroup personEthnicGroup [] = new PersonEthnicGroup[ethnicGroupColl.size()];
    ArrayList<Object> arrayList = (ArrayList<Object> ) ethnicGroupColl;
   Iterator<Object>  itr = null;
    itr = arrayList.iterator();
    int i = 0;
try
  {
    while (itr.hasNext()) {

           //PersonEthnicGroupDT personEthnicGroupDT = new PersonEthnicGroupDT();
           PersonEthnicGroup castorEthnicGroup = new PersonEthnicGroup();

           PersonEthnicGroupDT personEthnicGroupDT =  (PersonEthnicGroupDT)itr.next();
           if (NNDConstantUtil.notNull(personEthnicGroupDT))
           {
               castorEthnicGroup =  (PersonEthnicGroup) populateCastorObjects(personEthnicGroupDT, castorEthnicGroup);
               personEthnicGroup[i] = castorEthnicGroup;
               i++;
           }
    }

  }
  catch(NNDException nnde)

  {
   throw nnde;
  }

  catch(Exception e)
  {

   NNDException nndOther = new NNDException("Exception in getEthnicGroupCollection  " + e.getMessage());
   System.out.println("Error while getting Ethnic Group " + e.getMessage());
   e.printStackTrace();
   nndOther.setModuleName("NNDMarshallerLabReport.getEthnicGroupCollection");
   throw nndOther;

  }

    return personEthnicGroup;
}


}
