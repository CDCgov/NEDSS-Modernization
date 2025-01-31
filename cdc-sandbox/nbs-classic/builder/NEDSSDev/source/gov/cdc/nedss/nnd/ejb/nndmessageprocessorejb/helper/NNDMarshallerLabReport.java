//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\nnd\\ejb\\nndmessageprocessor\\helpers\\NNDMarshallerLabReport.java


package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.act.actid.dt.*;

import gov.cdc.nedss.nnd.util.*;
import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.ValueObjectExtractors;
import gov.cdc.nedss.messageframework.notificationmastermessage.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.Enumeration;




/**
 *
 * <p>Title: NBS</p>
 * <p>Description: Creates an Castor NedssEvent from a LabResultProxyVO </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author Venu Pannirselvam
 *
 */
public class NNDMarshallerLabReport
{

   /**
    * @roseuid 3D57A3F80109
    */
   public NNDMarshallerLabReport()
   {

   }

   /**
    * @param labResultProxyVO
    * @return Object
    * @roseuid 3D4F263B00CB
    */
   private ValueObjectExtractors valueObjExtractor = new ValueObjectExtractors();
   private NedssEvent nedssEvent = new NedssEvent();
   private Hashtable<Object, Object> localIDMap = new Hashtable<Object, Object>();
   private Hashtable<?,?> commonHashtable = new Hashtable<Object, Object>();
   private ArrayList<Object> allEntities = new ArrayList<Object> ();
   private ArrayList<Object> allActRelationShips = new ArrayList<Object> ();
   private Hashtable<Object, Object> partTypeCdTab = new Hashtable<Object, Object>();
   private ArrayList<Object> allRoles    = new ArrayList<Object> ();
   private ArrayList<Object> allParticipations = new ArrayList<Object> ();
   private NBSSecurityObj nbsSecurityObj = null;
   private int actIndex = 0;




   /**
    * dfd
    * @param labResultProxyVO LabResultProxyVO
    * @param nbsSecurityObj NBSSecurityObj
    * @return Object
    * @throws NNDException
    */
   public Object marshallLabReport( LabResultProxyVO labResultProxyVO, NBSSecurityObj nbsSecurityObj) throws NNDException

   {
    try
   {
       this.nbsSecurityObj = nbsSecurityObj;
       Collection<Object>  labParticipations = getOrderedTestParticipations(labResultProxyVO);
       Collection<Object>  resultTestParticipations = getResultedTestParticipations(labResultProxyVO);

       if(resultTestParticipations != null && resultTestParticipations.size() > 0)
         labParticipations.addAll(resultTestParticipations);

       if(NNDConstantUtil.notNull(labParticipations)) {

         populateCastorEntities(extractEntities(labParticipations,
                                                labResultProxyVO.
                                                getThePersonVOCollection(),
                                                NEDSSConstants.CLASS_CD_PSN),
                                NEDSSConstants.CLASS_CD_PSN);
         if (labResultProxyVO.getTheMaterialVOCollection()!=null){
           populateCastorEntities(extractEntities(labParticipations,
                                                  labResultProxyVO.
                                                  getTheMaterialVOCollection(),
                                                  NEDSSConstants.
                                                  MATERIAL_CLASS_CODE),
                                  NEDSSConstants.MATERIAL_CLASS_CODE);
         }
         populateCastorEntities(extractEntities(labParticipations,
                                                labResultProxyVO.
                                                getTheOrganizationVOCollection(),
                                                NEDSSConstants.PAR102_SUB_CD),
                                NEDSSConstants.PAR102_SUB_CD);
       }

       if(NNDConstantUtil.notNull(allRoles))
       {

         if(!allRoles.isEmpty())
         {

           Role [] castorRoles = new Role[allRoles.size()];

           for(int i = 0; i< castorRoles.length; i++)

           { Role castorRole = new Role();
             castorRole  = (Role) allRoles.get(i);

             /****
              * Commented out for getting scoping entity uids 01/04
              if (NNDConstantUtil.notNull(castorRole.getScopingEntityTempId()))
                {     Long entityUid = new Long (castorRole.getScopingEntityTempId());
                      if (localIDMap.containsKey(entityUid))
                          castorRole.setScopingEntityTempId(localIDMap.get(entityUid).toString());
               }*/



                  if (NNDConstantUtil.notNull(castorRole.getSubjectEntityTempId()))
                 {  Long entityUid = new Long (castorRole.getSubjectEntityTempId());
                    if (localIDMap.containsKey(entityUid))
                        castorRole.setSubjectEntityTempId(localIDMap.get(entityUid).toString());
                  }

             castorRoles[i] = castorRole;

           }

           nedssEvent.setRole(castorRoles);

         }




       }

       Entity [] entities = new Entity[allEntities.size()];
       for (int i = 0; i<entities.length; i++)
      {
        entities[i] = (Entity) allEntities.get(i);

      }

      nedssEvent.setEntity(entities);
      Object []  observationArray  = labResultProxyVO.getTheObservationVOCollection().toArray();
      Act []  actArray = new Act[observationArray.length];
      for (int i = 0; i < observationArray.length; i++)
      {

       actArray[i] = (Act)  populateCastorActs(observationArray[i]) ;

      }
      nedssEvent.setAct(actArray);

      ObservationVO rootObservationVO = (ObservationVO) getRootObservationVO(labResultProxyVO.getTheObservationVOCollection());
      if (NNDConstantUtil.notNull(rootObservationVO))
      {
          if ( NNDConstantUtil.notNull(rootObservationVO.getTheParticipationDTCollection()))
               addToAllParticipations(rootObservationVO.getTheParticipationDTCollection());


         if (
             NNDConstantUtil.notNull(rootObservationVO.getTheActRelationshipDTCollection())
             &&
             NNDConstantUtil.notNull(labResultProxyVO.getTheObservationVOCollection())
            )
        {

            populateCastorActRelationShips(rootObservationVO.getTheActRelationshipDTCollection(), labResultProxyVO.getTheObservationVOCollection());
            if ( NNDConstantUtil.notNull(  allActRelationShips))
           {

                 ActRelationship[] actRelationship = new ActRelationship[allActRelationShips.size()];



                 for (int i = 0; i<actRelationship.length; i++)
                 {

                    actRelationship[i] = (ActRelationship) allActRelationShips.get(i);
 //                   System.out.println("Act relation ship of  " + i  +  " Act =  " +   allActRelationShips.get(i));

                 }
//            System.out.println("Size of act relation ship  after initialization = " + actRelationship.length);
//            System.out.println("Size of  ALL act relation ship   = " + allActRelationShips.size());
            nedssEvent.setActRelationship(actRelationship);
          }

       }


    }
     if (NNDConstantUtil.notNull(allParticipations))
     {
//      System.out.println("Inside array copy of participations ");
      Participation [] castorParticipations = new Participation[allParticipations.size()];
      System.arraycopy(allParticipations.toArray(),0,castorParticipations,0,castorParticipations.length);
      nedssEvent.setParticipation(castorParticipations);


     }

     NNDLDFMarshaller ldfMarshaller = new NNDLDFMarshaller();
    // ldfMarshaller.marshallLDF(labResultProxyVO, nedssEvent,localIDMap);

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
            if (entityType.equals(NEDSSConstants.CLASS_CD_PSN))
            {

               Long pentityUID  = ((ParticipationDT) obj).getSubjectEntityUid();
               Object  ppersonVObj = valueObjExtractor.getPersonValueObject(pentityUID, entityVOCollection);
               if(((PersonVO)ppersonVObj) != null)
               {
               localIDMap.put(((PersonVO)ppersonVObj).getThePersonDT().getUid(), ((PersonVO)ppersonVObj).getThePersonDT().getLocalId());
               }
//               if (!((ParticipationDT)obj).getTypeCd().equals(NEDSSConstants.PAR110_TYP_CD))
//               {
                    Long entityUID  = ((ParticipationDT) obj).getSubjectEntityUid();
 //System.out.println(" entityUID in Lab - person  " + entityUID);
                partTypeCdTab.put(entityUID, ((ParticipationDT) obj).getTypeCd());
                   PersonVO  personVObj = (PersonVO) valueObjExtractor.getPersonValueObject(entityUID, entityVOCollection);
                   if (NNDConstantUtil.notNull(personVObj))
                   {

                  // *******************************************************************
                       commonHashtable = null;
                       commonHashtable = (Hashtable<?,?>) valueObjExtractor.getPersonRoles(personVObj, nbsSecurityObj);
                       if (commonHashtable != null)
                       {
                         if (commonHashtable.containsKey(personVObj.getThePersonDT().getPersonUid()))
                         {

                            addToAllRoles((Collection<?>) commonHashtable.get(personVObj.getThePersonDT().getPersonUid()));

                         }

                         if((commonHashtable.containsKey(NNDConstantUtil.NND_ROLE_KEY)))
                         {
                             addScopingEntities((Collection<?>)commonHashtable.get(NNDConstantUtil.NND_ROLE_KEY));

                         }

                       }

                       //*********** added for getting scoping entity uids 01/07/2004



                       entityCollection.add(personVObj);
                   }

//                } // 3rd iff

             } //2nd if
             else if (entityType.equals(NEDSSConstants.MATERIAL_CLASS_CODE))
             {
                        Long entityUID  = ((ParticipationDT) obj).getSubjectEntityUid();
//System.out.println(" entityUID in Lab - meterial  " + entityUID);
                        partTypeCdTab.put(entityUID, ((ParticipationDT) obj).getTypeCd());
                        MaterialVO materialVObj =  (MaterialVO) valueObjExtractor.getMaterialValueObject(entityUID, entityVOCollection);
                        if (NNDConstantUtil.notNull(materialVObj))
                        {

                            commonHashtable = null;
                            commonHashtable = (Hashtable<?,?>) valueObjExtractor.getMaterialRoles(materialVObj, nbsSecurityObj);
                            if(commonHashtable != null)
                            {

                              if(commonHashtable.containsKey(materialVObj.getTheMaterialDT().getMaterialUid()))
                                addToAllRoles( (Collection<?>)commonHashtable.get(materialVObj.getTheMaterialDT().getMaterialUid()));

                                if(commonHashtable.containsKey(NNDConstantUtil.NND_ROLE_KEY))
                                    addScopingEntities((Collection<?>) (commonHashtable.get(NNDConstantUtil.NND_ROLE_KEY)));

                            }


                            //addToAllRoles(valueObjExtractor.getMaterialRoles(materialVObj));
                            entityCollection.add(materialVObj);
                        }

               }
               else if (entityType.equals(NEDSSConstants.PAR102_SUB_CD))
               {

                        Long entityUID  = ((ParticipationDT) obj).getSubjectEntityUid();
//System.out.println(" entityUID in Lab - organization  " + entityUID);
                        partTypeCdTab.put(entityUID, ((ParticipationDT) obj).getTypeCd());
                        OrganizationVO organizationVObj =   (OrganizationVO) valueObjExtractor.getOrganizationValueObject(entityUID, entityVOCollection);
                        if (NNDConstantUtil.notNull(organizationVObj))
                        {
//                        Modified again on 02/04/2013 to fix Defect #3287 - NND Failing to Send, the code has erroneous iterator 
//                        	and CDC does not require scoping role entities to be sent in the master message

//                          // modified for scoping entities 01/08/2004 **********
//                          commonHashtable = null;
//                          commonHashtable = (Hashtable<Object, Object>) valueObjExtractor.getOrganizationRoles(organizationVObj, nbsSecurityObj);
//                       if (commonHashtable != null)
//                       {
//                         if (commonHashtable.containsKey(organizationVObj.
//                                                         getTheOrganizationDT().
//                                                         getOrganizationUid()))
//                           addToAllRoles( (Collection<?>) commonHashtable.get(
//                               organizationVObj.getTheOrganizationDT().
//                               getOrganizationUid()));
//                         if (commonHashtable.containsKey(NNDConstantUtil.NND_ROLE_KEY)) {
//
//                           ArrayList<?> scopingEntityColl = (ArrayList<?> ) (commonHashtable.
//                               get(NNDConstantUtil.NND_ROLE_KEY));
//                          Iterator<?>  sItr = scopingEntityColl.iterator();
//                           while (sItr.hasNext()) {
//
//                            // entityCollection.add(itr.next());
//
//                           }
//
//                         } // end changes
//                       }



                            entityCollection.add(organizationVObj);
                        }
               }
        } //1st IF
    }// while
  }

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

 private  Object  buildPersonEntity(Object personObj) throws NNDException
 {
         Entity castorEntity = new Entity();
         PersonNameDT personName = new PersonNameDT();
         PersonVO personVO = (PersonVO) personObj;
         PersonDT personDT = new PersonDT();
         personDT = personVO.getThePersonDT();
         Person castorPerson = new Person();

         castorPerson.addParticipationTypeCd((String)partTypeCdTab.get(personDT.getPersonUid()));
    try {
         if (NNDConstantUtil.notNull(personDT))
         {
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

            PersonEthnicGroupDT personEthnicGroupDT = new PersonEthnicGroupDT();
            PersonEthnicGroup castorEthnicGroup = new PersonEthnicGroup();

            personEthnicGroupDT =  (PersonEthnicGroupDT)itr.next();
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
//    System.out.println("Error while getting Ethnic Group " + e.getMessage());
    e.printStackTrace();
    nndOther.setModuleName("NNDMarshallerLabReport.getEthnicGroupCollection");
    throw nndOther;

   }

     return personEthnicGroup;
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

            OrganizationNameDT organizationNameDT = new OrganizationNameDT();
            OrganizationName castorOrganizationName = new OrganizationName();

            organizationNameDT =  (OrganizationNameDT)itr.next();
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
Iterator<Object>  itr = null;
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
   private  EntityLocator[] getEntityLocatorCollection(Collection<Object> entityLocatorColl) throws NNDException
 {
 EntityLocator [] entityLocator = new EntityLocator[entityLocatorColl.size()];
 ArrayList<Object> arrayList = (ArrayList<Object> ) entityLocatorColl;
Iterator<Object>  itr = null;
 itr = arrayList.iterator();
 int i = 0;
try
{
  while (itr.hasNext())
 {
        EntityLocatorParticipationDT  entityLocatorDT  = new EntityLocatorParticipationDT();
        EntityLocator castorEntityLocator = new EntityLocator();
        entityLocatorDT =  (EntityLocatorParticipationDT)itr.next();

        if (NNDConstantUtil.notNull(entityLocatorDT))
        {
            castorEntityLocator =  (EntityLocator) populateCastorObjects(entityLocatorDT, castorEntityLocator);
            if (entityLocatorDT.getClassCd().equals(NEDSSConstants.POSTAL) && NNDConstantUtil.notNull(entityLocatorDT.getThePostalLocatorDT()))
            {
                PostalLocatorDT postalLocatorDT = entityLocatorDT.getThePostalLocatorDT();
                PostalLocatorType castorPostalLocator = new PostalLocatorType();
                castorPostalLocator = (PostalLocatorType) populateCastorObjects(postalLocatorDT, castorPostalLocator);
                castorEntityLocator.setPostalLocatorType(castorPostalLocator);

             }
             if (entityLocatorDT.getClassCd().equals(NEDSSConstants.PHYSICAL) && NNDConstantUtil.notNull(entityLocatorDT.getThePhysicalLocatorDT()))
                {
                   PhysicalLocatorDT physicalLocatorDT = entityLocatorDT.getThePhysicalLocatorDT();
                   PhysicalLocatorType castorPhysicalLocator = new PhysicalLocatorType();
                   castorPhysicalLocator = (PhysicalLocatorType) populateCastorObjects(physicalLocatorDT, castorPhysicalLocator);
                   castorEntityLocator.setPhysicalLocatorType(castorPhysicalLocator);
                }
             if (entityLocatorDT.getClassCd().equals(NEDSSConstants.TELE) && NNDConstantUtil.notNull(entityLocatorDT.getTheTeleLocatorDT()))
                   {
//                   System.out.println("in the entity tele locator ----------------------<<");
                       TeleLocatorDT teleLocatorDT = entityLocatorDT.getTheTeleLocatorDT();
                       TeleLocatorType castorTeleLocator = new TeleLocatorType();
                       castorTeleLocator = (TeleLocatorType) populateCastorObjects(teleLocatorDT, castorTeleLocator);
                       castorEntityLocator.setTeleLocatorType(castorTeleLocator);
                    }
            entityLocator[i] = castorEntityLocator;
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
    NNDException nndOther = new NNDException("Exception in getEntityLocatorCollection  " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.getEntityLocatorCollection");
    throw nndOther;
  }

 return entityLocator;
 }

 private void  populateCastorEntities (Collection<Object> entityCollection, String entityType ) throws NNDException
 {
     ArrayList<Object> entityVOList = (ArrayList<Object> ) entityCollection;
    Iterator<Object>  itr = entityVOList.iterator();
try
 {
     while (itr.hasNext())
     {
        if (entityType.equals(NEDSSConstants.CLASS_CD_PSN))
        {
            PersonVO personVO = (PersonVO) itr.next();
            allEntities.add(  buildPersonEntity(personVO));
        }

        if (entityType.equals(NEDSSConstants.MATERIAL_CLASS_CODE))
        {

            MaterialVO materialVO = (MaterialVO) itr.next();
            allEntities.add( buildMaterialEntity(materialVO));

        }

        if (entityType.equals(NEDSSConstants.PAR102_SUB_CD))
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
//System.out.println("From Participation lab ,,subjectEntityUID before adding "+ localIDMap.get(participationDT.getSubjectEntityUid()));
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

private void populateCastorActRelationShips(Collection<Object> rootActRelationShips, Collection<ObservationVO>  observationVOColl) throws NNDException
{
    ArrayList<Object> resultUIDs = null;
   Iterator<Object>  resultItr =  null;
try
{
    resultUIDs =  (ArrayList<Object> )  processActRelationShips(rootActRelationShips, NEDSSConstants.ACT110_TYP_CD);
    if (NNDConstantUtil.notNull(resultUIDs))
    {
      resultItr = resultUIDs.iterator();
      while (resultItr.hasNext())

      {

        ArrayList<Object> isolateActRelationShips = (ArrayList<Object> )getActRelationShips(observationVOColl, (Long) resultItr.next());
        ArrayList<Object> isolateUIDs = (ArrayList<Object> ) processActRelationShips(isolateActRelationShips, NEDSSConstants.ACT109_TYP_CD);
        if (NNDConstantUtil.notNull(isolateUIDs))
        {
          Iterator<Object>   isolateItr = isolateUIDs.iterator();

           while (isolateItr.hasNext())
           {
              ArrayList<Object> suseptActRelationShips = (ArrayList<Object> )getActRelationShips(observationVOColl, (Long) isolateItr.next());
              ArrayList<Object> suseptUIDs  = (ArrayList<Object> ) processActRelationShips(suseptActRelationShips, NEDSSConstants.ACT110_TYP_CD);
           } //2nd while

        }// 2nd If

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
//   System.out.println("Actrelation size  +++++ " + actRelationShipList.size());
try
{
      while (itr.hasNext() )
   {
      ActRelationshipDT actRelationShipDT = new ActRelationshipDT();
      actRelationShipDT = (ActRelationshipDT) itr.next();
      ActRelationship castorActRelationShip = new ActRelationship();
//      System.out.println("Act Relation Ship DT  &&&&&& ***** " + actRelationShipDT.getSourceClassCd());
      if(NNDConstantUtil.notNull(actRelationShipDT))
      {

//        System.out.println("Act Relation Ship is not null ******************** " + actRelationShipDT.getTypeCd());
        castorActRelationShip = (ActRelationship) populateCastorObjects(actRelationShipDT, castorActRelationShip);
        castorActRelationShip.setTargetActTempId((String) localIDMap.get(actRelationShipDT.getTargetActUid()));
        castorActRelationShip.setSourceActTempId( (String) localIDMap.get(actRelationShipDT.getSourceActUid()));

        if (actRelationShipDT.getTypeCd().equals(typeCd))
       {
           observationUIds.add(actRelationShipDT.getSourceActUid());
       }
       allActRelationShips.add(actIndex,castorActRelationShip);
       actIndex++;
//       System.out.println("Now the Size is  " +  allActRelationShips.size());
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

     if (observationVO.getTheObservationDT().getCtrlCdDisplayForm()!=null) {
       if ( (observationVO.getTheObservationDT().getCtrlCdDisplayForm().equals(
           NEDSSConstants.LAB_CTRLCD_DISPLAY)) &&
           (observationVO.
            getTheObservationDT().getObsDomainCdSt1().equals(NEDSSConstants.
           ORDERED_TEST_OBS_DOMAIN_CD)))
         return observationVO;
     }
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
    nndOther.setModuleName("NNDMarshallerLabReport.populateCastorActs");
    throw nndOther;
  }
}

 act.setClassCd(NEDSSConstants.OBSERVATION_CLASS_CODE);
 act.setActTempId(observationDT.getLocalId());
 act.setObservation(castorObservation);
 return act;
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

 private Object buildMaterialEntity(Object materialObj) throws NNDException
 {
  Entity castorEntity = new Entity();
  MaterialVO materialVO = (MaterialVO) materialObj;
  Material castorMaterial = new Material();

try
{
  if ( NNDConstantUtil.notNull(materialVO.getTheEntityIdDTCollection()))
     castorEntity.setEntityId(getEntityIdCollection(materialVO.getTheEntityIdDTCollection()));

 if ( NNDConstantUtil.notNull(materialVO.getTheEntityLocatorParticipationDTCollection()))
     castorEntity.setEntityLocator(getEntityLocatorCollection(materialVO.getTheEntityLocatorParticipationDTCollection()));

   MaterialDT  materialDT =   materialVO.getTheMaterialDT();

   if (NNDConstantUtil.notNull(materialDT)) {
     castorMaterial =   (Material) populateCastorObjects(materialDT, castorMaterial);
     localIDMap.put(materialDT.getMaterialUid(),materialDT.getLocalId());

 }
   castorEntity.setClassCd(NEDSSConstants.MATERIAL_CLASS_CODE);
   castorMaterial.addParticipationTypeCd((String)partTypeCdTab.get(materialDT.getMaterialUid()));
   castorEntity.setEntityTempId(materialDT.getLocalId());
   castorEntity.setMaterial(castorMaterial);
}

 catch(NNDException nnde)
  {
    throw nnde;
  }

  catch(Exception e)
  {
    NNDException nndOther = new NNDException("Exception in buildMaterialEntity " + e.getMessage());
    nndOther.setModuleName("NNDMarshallerLabReport.buildMaterialEntity");
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
/*
           if (NNDConstantUtil.notNull(observationVO.getTheParticipationDTCollection()))
                 addToAllParticipations(observationVO.getTheParticipationDTCollection());
*/
           return observationVO.getTheActRelationshipDTCollection();
         }
      }
    }
    catch( Exception e)
    {

     NNDException nndeOther = new NNDException("Exception in get Act relation ships " + e.getMessage());
     nndeOther.setModuleName("NNDMarshallerLabReport.getActRelationShips");
     throw nndeOther;

    }
      return null;
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

 private  void addToAllRoles(Collection<?> roleCollection) throws NNDException
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

 /**
  *
  * @param labResultProxyVO LabResultProxyVO
  * @return Collection
  */
 private Collection<Object>  getOrderedTestParticipations(LabResultProxyVO labResultProxyVO) {

  //Collection<Object>  partColl;

  for(Iterator<ObservationVO> anIter = labResultProxyVO.getTheObservationVOCollection().iterator(); anIter.hasNext();) {

    ObservationVO obsVO = (ObservationVO)anIter.next();
    String ctrlCdDF = obsVO.getTheObservationDT().getCtrlCdDisplayForm();
    String obsDomainCd = obsVO.getTheObservationDT().getObsDomainCdSt1();

    if((ctrlCdDF != null && ctrlCdDF.equalsIgnoreCase(NNDConstantUtil.LAB_OT_CTRLCD_DSPLYFRM)) && (obsDomainCd != null && obsDomainCd.equalsIgnoreCase(NNDConstantUtil.LAB_OT_OBSDCD_ST1)))
      return obsVO.getTheParticipationDTCollection();

  }
  return null;

}//getOrderedTestParticipations


    private Collection<Object>  getResultedTestParticipations(LabResultProxyVO labResultProxyVO) {

     //Collection<Object>  partColl;

     for(Iterator<ObservationVO> anIter = labResultProxyVO.getTheObservationVOCollection().iterator(); anIter.hasNext();) {

       ObservationVO obsVO = (ObservationVO)anIter.next();
       String obsDomainCd = obsVO.getTheObservationDT().getObsDomainCdSt1();

       if(( obsDomainCd.equalsIgnoreCase(NNDConstantUtil.LAB_RT_OBSDCD_ST1)))
         return obsVO.getTheParticipationDTCollection();

     }
     return null;

   }//getResultedTestParticipations
   /**
    * added for scoping entity modifications
    * @param scopingEntityCollection  Collection
    * @throws NNDException
    */

   private void addScopingEntities(Collection<?> scopingEntityCollection) throws
       NNDException {

     NNDUtils util = new NNDUtils();
     localIDMap = valueObjExtractor.getScopingEntityLocalIdMap(localIDMap)     ;


     ArrayList<?> scopingEntities = (ArrayList<?> ) scopingEntityCollection;

    Iterator<?>  scopingItr = scopingEntities.iterator();
     try {
       while (scopingItr.hasNext()) {
         allEntities.add(scopingItr.next());

       }
     }
     catch (Exception e) {
       e.printStackTrace();
       NNDException nnde = new NNDException("Exception in addScopingEntities " +
                                            e.getMessage());
       throw nnde;

     }

   }


}
