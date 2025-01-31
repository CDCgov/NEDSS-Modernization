package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import java.util.*;

import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.intervention.dt.*;
import gov.cdc.nedss.act.intervention.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.messageframework.notificationmastermessage.*;
import gov.cdc.nedss.nnd.exception.*;
import gov.cdc.nedss.nnd.util.*;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

/**
 * class used to extract vaccination information from vo and populate nedssEvent
 * for marshaling
 */
/**
 *
 * <p>Title: NBS</p>
 * <p>Description: Creates an Castor NedssEvent from a VaccinationProxyVO </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corporation</p>
 *  @author Venu Pannirselvam
 *
 */
public class NNDMarshallerVaccination
{

    static final LogUtils logger = new LogUtils(NNDMarshallerInvestigation.class.getName());
    Hashtable<Object, Object> localIdMap = new Hashtable<Object, Object>();
    ValueObjectExtractors valueObjExtractor = new ValueObjectExtractors();
    private Hashtable<?,?> commonHashtable = new Hashtable<Object, Object>();
    private ArrayList<Object> allRoles = new ArrayList<Object> ();
    private NBSSecurityObj nbsSecurityObj = null;
    private Collection<Object>  entityCollection  = new ArrayList<Object> ();
    NNDUtils util = new NNDUtils();

    /**
     * @roseuid 3D57A400033C
     */
    public NNDMarshallerVaccination()
    {
    }

    /**
     *
     * @param vaccinationProxyVO VaccinationProxyVO
     * @param nbsSecurityObj NBSecurityObj
     * @return Object
     * @throws NNDException
     */
    public Object marshallVaccination(VaccinationProxyVO vaccinationProxyVO, NBSSecurityObj nbsSecurityObj) throws NNDException
    {
        try
        {
            this.nbsSecurityObj = nbsSecurityObj;
            // logger.debug("in marshallVaccination ----------------");
            NedssEvent nedssEvent = new NedssEvent();
            /**
             * get entities from vo and add to nedssEvent
             */nedssEvent.setEntity(getEntityCollection(vaccinationProxyVO));
            /**
             * get acts from vo and add to nedssEvent
             */nedssEvent.setAct(getActCollection(vaccinationProxyVO));
            /**
             * get Participation from vo and add to nedssEvent
             */nedssEvent.setParticipation(getParticipationCollection(vaccinationProxyVO));
            /**
             * get ActRelationship from vo and add to nedssEvent
             */nedssEvent.setActRelationship(getActRelationshipCollection(vaccinationProxyVO));
            Role castorRole[] = setAllRoles();
            if(NNDConstantUtil.notNull(castorRole))
            {
                nedssEvent.setRole(castorRole);
            }
            NNDLDFMarshaller ldfMarshaller = new NNDLDFMarshaller();
            ldfMarshaller.marshallLDF(vaccinationProxyVO, nedssEvent,localIdMap);
            return nedssEvent;
        }
        catch(NNDException nnde)
        {
            throw nnde;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            NNDException nndOther = new NNDException("error in marshallVaccination" + e.getMessage());
            nndOther.setModuleName("NNDMarshallerVaccination.marshallVaccination");
            throw nndOther;
        }
    }

    /**
     *
     * @param vaccinationProxyVO VaccinationProxyVO
     * @return Entity Array
     * @throws NNDException
     */
    private Entity[] getEntityCollection(VaccinationProxyVO vaccinationProxyVO) throws NNDException
    {
        try
        {

            // get the persons
            entityCollection  = extractEntities(vaccinationProxyVO.getTheInterventionVO().getTheParticipationDTCollection(), vaccinationProxyVO.getThePersonVOCollection(), entityCollection);
            // get the materials
            entityCollection  = extractMaterial(vaccinationProxyVO.getTheInterventionVO().getTheParticipationDTCollection(), vaccinationProxyVO.getTheMaterialVO(), entityCollection);
            // get the organizations
            entityCollection  = extractOrganizations(vaccinationProxyVO.getTheInterventionVO().getTheParticipationDTCollection(), vaccinationProxyVO.getTheOrganizationVOCollection(), entityCollection);
            // first build an array but then convert it to an array of Entity objects (String array)
            Entity entityArr[] = new Entity[entityCollection.size()];
           Iterator<Object>  iter = entityCollection.iterator();
            int i = 0;
            while(iter.hasNext())
            {
                entityArr[i] = (Entity)iter.next();
                i++;
            }
            return entityArr;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.getEntityCollection  " + e.getMessage());
        }
    }

    /**
     *
     * @param vaccinationProxyVO VaccinationProxyVO
     * @return Act Array
     * @throws NNDException
     */
    private Act[] getActCollection(VaccinationProxyVO vaccinationProxyVO) throws NNDException
    {
        try
        {
            Collection<Object>  actCollection  = new ArrayList<Object> ();
            /**
             * extract observations and add them to a collection
             */actCollection  = extractObservations(vaccinationProxyVO.getTheObservationVOCollection(), actCollection);
            /**
             * extract Interventions and add them to the same collection
             * (pass in the previous collection and add to it then pass it back)
             */actCollection  = extractIntervention(vaccinationProxyVO.getTheInterventionVO(), actCollection);
            Act[] actArr = new Act[actCollection.size()];
            /**
             * first build an array but then convert it to an array of Act objects (String array)
             */Iterator<Object> iter = actCollection.iterator();
            int i = 0;
            while(iter.hasNext())
            {
                actArr[i] = (Act)iter.next();
                i++;
            }
            return actArr;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.getActCollection  " + e.getMessage());
        }
    }

    /**
     *
     * @param vaccinationProxyVO VaccinationProxyVO
     * @return Participation Array
     * @throws NNDException
     */
    private Participation[] getParticipationCollection(VaccinationProxyVO vaccinationProxyVO) throws NNDException
    {
        try
        {
            Collection<Object>  participationCollection  = extractParticipations(vaccinationProxyVO.getTheInterventionVO().getTheParticipationDTCollection());
            Participation[] participationArr = new Participation[participationCollection.size()];
           Iterator<Object>  iter = participationCollection.iterator();
            /**
             * first build an array but then convert it to an array of Participation objects (String array)
             */int i = 0;
            while(iter.hasNext())
            {
                participationArr[i] = (Participation)iter.next();
                i++;
            }
            return participationArr;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.getActCollection  " + e.getMessage());
        }
    }

    /**
     *
     * @param vaccinationProxyVO VaccinationProxyVO
     * @return ActRelationship Array
     * @throws NNDException
     */
    private ActRelationship[] getActRelationshipCollection(VaccinationProxyVO vaccinationProxyVO) throws NNDException
    {
        try
        {
            Collection<Object>  actRelationshipCollection  = extractActRelationships(vaccinationProxyVO.getTheInterventionVO().getTheActRelationshipDTCollection());
            ActRelationship[] actRelationshipArr = new ActRelationship[actRelationshipCollection.size()];
           Iterator<Object>  iter = actRelationshipCollection.iterator();
            /**
             * first build an array but then convert it to an array of ActRelationship objects (String array)
             */int i = 0;
            while(iter.hasNext())
            {
                actRelationshipArr[i] = (ActRelationship)iter.next();
                i++;
            }
             logger.debug(" ---------- end of getActRelationshipCollection   ----------- ");
            return actRelationshipArr;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.getActRelatioshipCollection  " + e.getMessage());
        }
    }

    /**
     *
     * @param participationDTCollection  Collection
     * @param entityVOCollection  Collection
     * @param entityCollection   Collection
     * @return Collection
     * @throws NNDException
     */
    private Collection<Object>  extractEntities(Collection<Object> participationDTCollection, Collection<Object>  entityVOCollection, Collection<Object>  entityCollection) throws NNDException
    {
        try
        {
           Iterator<Object>  itr = participationDTCollection.iterator();
            while(itr.hasNext())
            {
                ParticipationDT participationDT = (ParticipationDT)itr.next();
                if(NNDConstantUtil.notNull(participationDT))
                {
                    if(participationDT.getSubjectClassCd().equals(NEDSSConstants.CLASS_CD_PSN))
                    {
                        if(participationDT.getTypeCd().equals(NEDSSConstants.SUBJECT_OF_VACCINE))
                        {
                            Long pentityUID = participationDT.getSubjectEntityUid();
                            PersonVO ppersonVObj = (PersonVO)valueObjExtractor.getPersonValueObject(pentityUID, entityVOCollection);
                            if(ppersonVObj != null)
                            {
                                localIdMap.put(ppersonVObj.getThePersonDT().getPersonUid(), ppersonVObj.getThePersonDT().getLocalId());
                            }
                            Entity entityPersonVac = this.getEntityFromPerson(participationDT, entityVOCollection, NEDSSConstants.CLASS_CD_PSN, NEDSSConstants.SUBJECT_OF_VACCINE);
                            entityCollection.add(entityPersonVac);
                        }
                        else
                        {
                            Entity entityPerson = this.getEntityFromPerson(participationDT, entityVOCollection, NEDSSConstants.CLASS_CD_PSN, NEDSSConstants.PERFORMER_OF_VACCINE);
                            entityCollection.add(entityPerson);
                        }
                    }
                }
            }
            return entityCollection; // return if no errors
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.extractEntities " + e.getMessage());
        }
    }

    /**
     *
     * @param participationDTCollection  Collection
     * @param entityVOCollection  Collection
     * @param entityCollection  Collection
     * @return Collection
     * @throws NNDException
     */
    private Collection<Object>  extractOrganizations(Collection<Object> participationDTCollection, Collection<Object>  entityVOCollection, Collection<Object>  entityCollection) throws NNDException
    {
        try
        {
           Iterator<Object>  itr = participationDTCollection.iterator();
            while(itr.hasNext())
            {
                ParticipationDT participationDT = (ParticipationDT)itr.next();
                if(NNDConstantUtil.notNull(participationDT))
                {
                    // logger.debug("participationDT.getSubjectClassCd() = " + participationDT.getSubjectClassCd());
                    // For oraganization
                    if(participationDT.getSubjectClassCd().equals(NEDSSConstants.ORGANIZATION_CLASS_CODE))
                    {
                        Entity entityOrganization = this.getEntityFromOrganization(participationDT, entityVOCollection, NEDSSConstants.ORGANIZATION_CLASS_CODE, NEDSSConstants.PERFORMER_OF_VACCINE);
                        entityCollection.add(entityOrganization);
                    }
                } //1st IF
            } // while
            return entityCollection;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.extractOrganiztions " + e.getMessage());
        }
    }


    private Collection<Object>  extractMaterial(Collection<Object> participationDTCollection, MaterialVO materialVO, Collection<Object>  entityCollection) throws NNDException
    {
        try
        {
           Iterator<Object>  itr = participationDTCollection.iterator();
            while(itr.hasNext())
            {
                ParticipationDT participationDT = (ParticipationDT)itr.next();
                // For material
                if(participationDT.getSubjectClassCd().equals(NEDSSConstants.MATERIAL_CLASS_CODE))
                {

                    if (materialVO != null)
                    {

                      //*********** added for extracting scoping entities
                       commonHashtable = null;
                       commonHashtable = (Hashtable<?,?>)(valueObjExtractor.getMaterialRoles(materialVO, nbsSecurityObj));
                       if (commonHashtable != null)
                       {

                       if(commonHashtable.containsKey(materialVO.getTheMaterialDT().getMaterialUid()))

                         addToAllRoles((Collection<?>) commonHashtable.get(materialVO.getTheMaterialDT().getMaterialUid()));
                       if (commonHashtable.containsKey(NNDConstantUtil.NND_ROLE_KEY))
                       {

                         ArrayList<?> scopingEntityColl = (ArrayList<?> ) commonHashtable.get(NNDConstantUtil.NND_ROLE_KEY);
                         localIdMap =  valueObjExtractor.getScopingEntityLocalIdMap(localIdMap);

                        Iterator<?>  scopingItr = scopingEntityColl.iterator();
                         while(scopingItr.hasNext())
                         {
                            entityCollection.add(scopingItr.next());

                         }

                       }

                       }

                       //********** end modifications for scoping entities

                    }

                    Entity entity = new Entity();
                    Material material = new Material();

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
//                    System.out.println(" Before putting to the treemap vaccin - meterial key and value " + materialVO.getTheMaterialDT().getMaterialUid() + "-" + materialVO.getTheMaterialDT().getLocalId());
                    localIdMap.put(materialVO.getTheMaterialDT().getMaterialUid(), materialVO.getTheMaterialDT().getLocalId());
                    entityCollection.add(entity);
                }
            }
            return entityCollection;
        }
        catch(Exception e)
        {
        	logger.error("Exception: "+e.getMessage(), e);
            throw new NNDException("error in NNDMarshallerVaccination.extractMaterial " + e.getMessage());
        }
    }


    private Collection<Object>  extractObservations(Collection<ObservationVO> observationVOCollection, Collection<Object>  actCollection) throws NNDException
    {
        try
        {
           Iterator<ObservationVO>  iter = observationVOCollection.iterator();
            while(iter.hasNext())
            {
                Act act = new Act();
                ObservationVO observationVO = (ObservationVO)iter.next();
                if(NNDConstantUtil.notNull(observationVO.getTheActIdDTCollection()))
                {
                    act.setActId(getObservationActIds(observationVO.getTheActIdDTCollection()));
                }
                act.setActTempId(observationVO.getTheObservationDT().getLocalId());
                act.setClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
                Observation observation = new Observation();
                // populate the corresponding castor class from the DT
                observation = (Observation)util.copyObjects(observationVO.getTheObservationDT(), observation);
                // ObsValueCoded Collection
                Collection<Object>  obsValueCodedColl = observationVO.getTheObsValueCodedDTCollection();
                if(obsValueCodedColl != null)
                {
                   Iterator<Object>  obsValueCodedIter = obsValueCodedColl.iterator();
                    while(obsValueCodedIter.hasNext())
                    {
                        ObsValueCodedDT obsValueCodedDT = (ObsValueCodedDT)obsValueCodedIter.next();
                        ObsValueCoded obsValueCoded = new ObsValueCoded();
                        // populate the corresponding castor class from the DT
                        obsValueCoded = (ObsValueCoded)util.copyObjects(obsValueCodedDT, obsValueCoded);
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
                        obsValueNumeric = (ObsValueNumeric)util.copyObjects(obsValueNumericDT, obsValueNumeric);
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
                        obsValueText = (ObsValueText)util.copyObjects(obsValueTxtDT, obsValueText);
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
                        obsValueDate = (ObsValueDate)util.copyObjects(obsValueDateDT, obsValueDate);
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
                        observationReason = (ObservationReason)util.copyObjects(observationReasonDT, observationReason);
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
                        observationInterp = (ObservationInterp)util.copyObjects(observationInterpDT, observationInterp);
                        observation.addObservationInterp(observationInterp);
                    }
                }
                act.setObservation(observation);
                if(observationVO.getTheObservationDT().getObservationUid() != null && observationVO.getTheObservationDT().getLocalId() != null)
                	localIdMap.put(observationVO.getTheObservationDT().getObservationUid(), observationVO.getTheObservationDT().getLocalId());
                actCollection.add(act);
            }
            return actCollection;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.extractObservations " + e.getMessage());
        }
    }


    private Collection<Object>  extractIntervention(InterventionVO interventionVO, Collection<Object>  actCollection) throws NNDException
    {
        try
        {
            Act act = new Act();
            InterventionDT interventionDT = interventionVO.getTheInterventionDT();
            act.setActTempId(interventionDT.getLocalId());
            act.setClassCd(NEDSSConstants.CLASS_CD_INTV);
            Intervention intervention = new Intervention();
            // populate the corresponding castor class from the DT
            intervention = (Intervention)util.copyObjects(interventionDT, intervention);
            act.setIntervention(intervention);
            Collection<Object>  procColl = interventionVO.getTheProcedure1DTCollection();
           Iterator<Object>  iterProcColl = procColl.iterator();
            while(iterProcColl.hasNext())
            {
                Procedure1DT procedure1DT = (Procedure1DT)iterProcColl.next();
                Procedure1 procedure1 = new Procedure1();
                // populate the corresponding castor class from the DT
                procedure1 = (Procedure1)util.copyObjects(procedure1DT, procedure1);
                intervention.addProcedure1(procedure1);
            }
            Collection<Object>  subAdminColl = interventionVO.getTheSubstanceAdministrationDTCollection();
           Iterator<Object>  iterSubAdminColl = subAdminColl.iterator();
            while(iterSubAdminColl.hasNext())
            {
                SubstanceAdministrationDT substanceAdministrationDT = (SubstanceAdministrationDT)iterSubAdminColl.next();
                SubstanceAdministration substanceAdministration = new SubstanceAdministration();
                // populate the corresponding castor class from the DT
                substanceAdministration = (SubstanceAdministration)util.copyObjects(substanceAdministrationDT, substanceAdministration);
                intervention.addSubstanceAdministration(substanceAdministration);
            }
            localIdMap.put(interventionDT.getInterventionUid(), interventionDT.getLocalId());
            actCollection.add(act);
            return actCollection;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.extractIntervention " + e.getMessage());
        }
    }


    private Collection<Object>  extractParticipations(Collection<Object> participationDTCollection) throws NNDException
    {
        Collection<Object>  partCollection  = new ArrayList<Object> ();
        try
        {
           Iterator<Object>  iter = participationDTCollection.iterator();
            while(iter.hasNext())
            {
                ParticipationDT participationDT = (ParticipationDT)iter.next();
                // if(!(participationDT.getTypeCd().equals(NEDSSConstants.SUBJECT_OF_VACCINE)))
                //{
                Participation participation = new Participation();
                participation.setActTempId((String)localIdMap.get(participationDT.getActUid()));
//                System.out.println("The subject entityTempId (in Vaccine )" + localIdMap.get(participationDT.getSubjectEntityUid()));
                participation.setSubjectEntityTempId((String)localIdMap.get(participationDT.getSubjectEntityUid()));
                // populate the corresponding castor class from the DT
                participation = (Participation)util.copyObjects(participationDT, participation);
                participation.setActTempId((String)localIdMap.get(participationDT.getActUid()));
                participation.setSubjectEntityTempId((String)localIdMap.get(participationDT.getSubjectEntityUid()));
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


    private Collection<Object>  extractActRelationships(Collection<Object> actRelationshipDTCollection) throws NNDException
    {
        try
        {
            Collection<Object>  actCollection  = new ArrayList<Object> ();
           Iterator<Object>  iter = actRelationshipDTCollection.iterator();
            while(iter.hasNext())
            {
                ActRelationship actRelationship = new ActRelationship();
                ActRelationshipDT actRelationshipDT = (ActRelationshipDT)iter.next();
                // populate the corresponding castor class from the DT
                actRelationship = (ActRelationship)util.copyObjects(actRelationshipDT, actRelationship);
                actRelationship.setTargetActTempId((String)localIdMap.get(actRelationshipDT.getTargetActUid()));
                actRelationship.setSourceActTempId(((String)localIdMap.get(actRelationshipDT.getSourceActUid())));
                actCollection.add(actRelationship);
            }
            return actCollection;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.extractActRelationships " + e.getMessage());
        }
    }


    private Entity getEntityFromPerson(ParticipationDT participationDT, Collection<Object>  entityVOCollection, String classCode, String typeCode) throws NNDException
    {
        try
        {
            Entity entity = new Entity();
            Person person = new Person();
            //ArrayList<Object> entityIdCollection  = new ArrayList<Object> ();
            Long entityUID = participationDT.getSubjectEntityUid();
            PersonVO personVO = (PersonVO)valueObjExtractor.getPersonValueObject(entityUID, entityVOCollection);

           //Commented out and replaced with new method call 01/07/2004 addToAllRoles(valueObjExtractor.getPersonRoles(personVO));
           if (personVO != null)
           {

              commonHashtable = null;
              commonHashtable = valueObjExtractor.getPersonRoles(personVO, nbsSecurityObj);
              if(commonHashtable != null)
              {
                if (commonHashtable.containsKey(personVO.getThePersonDT().getPersonUid()))
                    addToAllRoles((Collection<?>) commonHashtable.get(personVO.getThePersonDT().getPersonUid()));
                 if (commonHashtable.containsKey(NNDConstantUtil.NND_ROLE_KEY))
                 {

                    ArrayList<?> scopingEntities = (ArrayList<?> ) commonHashtable.get(NNDConstantUtil.NND_ROLE_KEY);
                    localIdMap = valueObjExtractor.getScopingEntityLocalIdMap(localIdMap);

                   Iterator<?>  itr = scopingEntities.iterator();
                    while(itr.hasNext())
                    {

                    entityCollection.add(itr.next());

                    }


                 }

              }



           }

            entity.setEntityTempId(personVO.getThePersonDT().getLocalId());
            entity.setClassCd(classCode);
            person.addParticipationTypeCd(typeCode);
            personVO.getThePersonDT().setPersonParentUid(null);
            // populate the corresponding castor class from the DT
            person = (Person)util.copyObjects(personVO.getThePersonDT(), person);
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
                    personName = (PersonName)util.copyObjects(personNameDT, personName);
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
                    PersonEthnicGroup personEthnicGroup = new PersonEthnicGroup();
                    // populate the corresponding castor class from the DT
                    personEthnicGroup = (PersonEthnicGroup)util.copyObjects(PersonEthnicGroupDT, personEthnicGroup);
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
                    personRace = (PersonRace)util.copyObjects(personRaceDT, personRace);
                    person.addPersonRace(personRace);
                }
            }
            entity.setEntityId(getEntityIdCollection(personVO.getTheEntityIdDTCollection()));
            entity.setEntityLocator(getEntityLocatorCollection(personVO.getTheEntityLocatorParticipationDTCollection()));
            entity.setPerson(person);
            // Setting the treemap
//            System.out.println("Setting the tree map in Vaccin person key and value " + personVO.getThePersonDT().getPersonUid() + " " + personVO.getThePersonDT().getLocalId());
            localIdMap.put(personVO.getThePersonDT().getPersonUid(), personVO.getThePersonDT().getLocalId());
            return entity;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.getEntityFromPerson " + e.getMessage());
        }
    }


    private Entity getEntityFromOrganization(ParticipationDT participationDT, Collection<Object>  entityVOCollection, String classCode, String typeCode) throws NNDException
    {
        try
        {
            Entity entity = new Entity();
            Organization organization = new Organization();

            Long entityUID = participationDT.getSubjectEntityUid();
            OrganizationVO organizationVO = (OrganizationVO)valueObjExtractor.getOrganizationValueObject(entityUID, entityVOCollection);

            if (organizationVO != null)
            {
              //**********  added for extracting scoping entities
              commonHashtable = null;
              commonHashtable = (Hashtable<Object, Object>)valueObjExtractor.getOrganizationRoles(organizationVO, nbsSecurityObj);
             if (commonHashtable != null)
             {
               if ( (commonHashtable.containsKey(organizationVO.getTheOrganizationDT().
                                                 getOrganizationUid())))
                 addToAllRoles( (Collection<?>) commonHashtable.get(organizationVO.
                     getTheOrganizationDT().getOrganizationUid()));
               if (commonHashtable.containsKey(NNDConstantUtil.NND_ROLE_KEY)) {
                 ArrayList<?>  scopingEntities = (ArrayList<?> ) commonHashtable.get(
                     NNDConstantUtil.NND_ROLE_KEY);
                 localIdMap = valueObjExtractor.getScopingEntityLocalIdMap(
                     localIdMap);

                Iterator<?>  itr = scopingEntities.iterator();
                 while (itr.hasNext()) {

                   entityCollection.add(itr.next());

                 }
               }
             }

            //********** end modifications for scoping entities

                                 entity.setEntityTempId(organizationVO.
                                                        getTheOrganizationDT().
                                                        getLocalId());
              entity.setClassCd(classCode);

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
/*              System.out.println(
                  "Setting the tree map in Vaccin organization key and value " +
                  organizationVO.getTheOrganizationDT().getOrganizationUid() + " " +
                  organizationVO.getTheOrganizationDT().getLocalId());*/
              // Setting the treemap
              localIdMap.put(organizationVO.getTheOrganizationDT().
                                 getOrganizationUid(),
                                 organizationVO.getTheOrganizationDT().getLocalId());
            }
            return entity;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.getEntityFromOrganization " + e.getMessage());
        }
    }

    private EntityId[] getEntityIdCollection(Collection<Object> entityIdCollection) throws NNDException
    {
        try
        {
           Iterator<Object>  iter = entityIdCollection.iterator();
            ArrayList<Object> entityIdArray = new ArrayList<Object> ();
            while(iter.hasNext())
            {
                EntityIdDT enityIdDT = (EntityIdDT)iter.next();
                if(enityIdDT!=null && enityIdDT.getAssigningAuthorityCd()!=null && enityIdDT.getAssigningAuthorityCd().length()>20){
                	enityIdDT.setAssigningAuthorityCd(enityIdDT.getAssigningAuthorityCd().substring(0,19));
                }
                EntityId entityId = new EntityId();
                // populate the corresponding castor class from the DT
                entityId = (EntityId)util.copyObjects(enityIdDT, entityId);
                entityIdArray.add(entityId);
                entityId = null;
            }
            EntityId entityIdArr[] = new EntityId[entityIdCollection.size()];
           Iterator<Object>  it = entityIdArray.iterator();
            int i = 0;
            while(it.hasNext())
            {
                entityIdArr[i] = (EntityId)it.next();
                i++;
            }
            return entityIdArr;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new NNDException("error in NNDMarshallerVaccination.getEntityIdCollection  " + e.getMessage());
        }
    }

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
                    EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT)iter.next();
                    EntityLocator entityLocator = new EntityLocator();
                    // populate the corresponding castor class from the DT
                    entityLocator = (EntityLocator)util.copyObjects(entityLocatorParticipationDT, entityLocator);
                    if(entityLocatorParticipationDT.getTheTeleLocatorDT() != null)
                    {
                        TeleLocatorType teleLocatorType = new TeleLocatorType();
                        // populate the corresponding castor class from the DT
                        teleLocatorType = (TeleLocatorType)util.copyObjects(entityLocatorParticipationDT.getTheTeleLocatorDT(), teleLocatorType);
                        entityLocator.setTeleLocatorType(teleLocatorType);
                    }
                    if(entityLocatorParticipationDT.getThePostalLocatorDT() != null)
                    {
                        PostalLocatorType postalLocatorType = new PostalLocatorType();
                        // populate the corresponding castor class from the DT
                        postalLocatorType = (PostalLocatorType)util.copyObjects(entityLocatorParticipationDT.getThePostalLocatorDT(), postalLocatorType);
                        entityLocator.setPostalLocatorType(postalLocatorType);
                    }
                    if(entityLocatorParticipationDT.getThePhysicalLocatorDT() != null)
                    {
                        PhysicalLocatorType physicalLocatorType = new PhysicalLocatorType();
                        // populate the corresponding castor class from the DT
                        physicalLocatorType = (PhysicalLocatorType)util.copyObjects(entityLocatorParticipationDT.getThePhysicalLocatorDT(), physicalLocatorType);
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
             */int i = 0;
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
            throw new NNDException("error in NNDMarshallerVaccination.getEntityLocatorCollection  " + e.getMessage());
        }
    }

    private void addToAllRoles(Collection<?> roleCollection) throws NNDException
    {
        try
        {
            if(NNDConstantUtil.notNull(roleCollection))
            {
                if(!roleCollection.isEmpty())
                {
                   Iterator<?>  itr = roleCollection.iterator();
                    while(itr.hasNext())
                    {
                        Object obj = itr.next();
                        if(!allRoles.contains(obj))
                        {
                            allRoles.add(obj);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            NNDException nndOther = new NNDException("Exception in addToAllRoles " + e.getMessage());
            nndOther.setModuleName("NNDMarshallerVaccination.addToAllRoles");
            throw nndOther;
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
                    Role[] castorRoles = new Role[allRoles.size()];
                    for(int i = 0; i < castorRoles.length; i++)
                    {
                        Role castorRole = new Role();
                        castorRole = (Role)allRoles.get(i);

                        // *** commented out to get scopign entity uids 01/07/2004 castorRole.setScopingEntityTempId(null);
                        if(NNDConstantUtil.notNull(castorRole.getSubjectEntityTempId()))
                        {
                            Long entityUid = new Long(castorRole.getSubjectEntityTempId());
                            if(localIdMap.containsKey(entityUid))
                            {
                                castorRole.setSubjectEntityTempId(localIdMap.get(entityUid).toString());
                            }
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
            throw new NNDException("Error in marshallVaccination, setAllRoles() method  " + e.getMessage());
        }
    }

    private ActId[] getObservationActIds(Collection<Object> actIDDTColl) throws NNDException
    {
        ActId[] castorActIds = new ActId[actIDDTColl.size()];
        ArrayList<Object> actIDDTList = (ArrayList<Object> )actIDDTColl;
        try
        {
            for(int i = 0; i < castorActIds.length; i++)
            {
                ActIdDT actIdDT = (ActIdDT)actIDDTList.get(i);
              //Added to fix ER# 8815 - 03/25/2016
                if(actIdDT!=null && actIdDT.getAssigningAuthorityCd()!=null && actIdDT.getAssigningAuthorityCd().length()>20){
             	   actIdDT.setAssigningAuthorityCd(actIdDT.getAssigningAuthorityCd().substring(0,19));
                }
                if(NNDConstantUtil.notNull(actIdDT))
                {
                    ActId castorActId = new ActId();
                    castorActIds[i] = (ActId)util.copyObjects(actIdDT, castorActId);
                }
            }
        }
        catch(NNDException nnde)
        {
            throw nnde;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            NNDException nndOther = new NNDException("Exception while extracting actIDS " + e.getMessage());
            nndOther.setModuleName("NNDMarshallerVaccination.getObservationActIds");
        }
        return castorActIds;
    }
}
