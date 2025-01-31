package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import java.util.Set;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.messageframework.notificationmastermessage.*;
import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.nnd.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.association.dao.RoleDAOImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.*;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.*;
import gov.cdc.nedss.util.JNDINames;


import javax.rmi.PortableRemoteObject;


/**
 * Title:        NEDSS1.0
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author CSC EMPLOYEE
 * @version 1.0
 */
public class ValueObjectExtractors
{
    public ValueObjectExtractors()
    {
    }

    // Interface for writing messages connected with execution errors
    // and debugging activities to a centralized file.
    private final static LogUtils logger =
           new LogUtils(ValueObjectExtractors.class.getName());
    public Hashtable<Object,Object> localIdMap = new Hashtable<Object,Object>();
    private PersonVO elrScopingPersonVO = null;

       /**
        *
        * @param valueObjectUID Long
        * @param valueObjectCollection  Collection
        * @return Object
        * @throws NNDException
        */
    public Object getPersonValueObject(Long valueObjectUID,
                                       Collection<Object>  valueObjectCollection)
                                throws NNDException
    {

        try
        {

           Iterator<Object>  itr = valueObjectCollection.iterator();

            while (itr.hasNext())
            {

                Object obj = itr.next();

                if (NNDConstantUtil.notNull(obj))
                {

                    if (valueObjectUID.equals(((PersonVO)obj).getThePersonDT().getPersonUid()))
                       return obj;

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            NNDException nnde = new NNDException(
                                        "Exception in ValueObjectExtractors " +
                                        e.getMessage());
            nnde.setModuleName("ValueObjectExtractors.getPersonValueObject");
            throw nnde;
        }

        return null;
    }
    /**
     *
     * @param valueObjectUID Long
     * @param valueObjectCollection  Collection
     * @return Object
     * @throws NNDException
     */
    public Object getMaterialValueObject(Long valueObjectUID,
                                         Collection<Object>  valueObjectCollection)
                                  throws NNDException
    {

        try
        {

           Iterator<Object>  itr = valueObjectCollection.iterator();

            while (itr.hasNext())
            {

                Object obj = itr.next();

                if (NNDConstantUtil.notNull(obj))
                {

                    if (valueObjectUID.equals(((MaterialVO)obj).getTheMaterialDT().getMaterialUid()))
                        return obj;

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            NNDException nnde = new NNDException(
                                        "Exception in ValueObjectExtractors " +
                                        e.getMessage());
            nnde.setModuleName("ValueObjectExtractors.getMaterialValueObject");
            throw nnde;
        }

        return null;
    }
    /**
     *
     * @param valueObjectUID Long
     * @param valueObjectCollection  Collection
     * @return Object
     * @throws NNDException
     */
    public Object getOrganizationValueObject(Long valueObjectUID,
                                             Collection<Object>  valueObjectCollection)
                                      throws NNDException
    {

        try
        {

           Iterator<Object>  itr = valueObjectCollection.iterator();

            while (itr.hasNext())
            {

                Object obj = itr.next();

                if (NNDConstantUtil.notNull(obj))
                {

                    if (valueObjectUID.equals(((OrganizationVO)obj).getTheOrganizationDT().getOrganizationUid()))
                       return obj;

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            NNDException nnde = new NNDException(
                                        "Exception in ValueObjectExtractors " +
                                        e.getMessage());
            nnde.setModuleName(
                    "ValueObjectExtractors.getOrganizationValueObject");
            throw nnde;
        }

        return null;
    }
    /**
     * Extracts the roles and the scoping entities if any for the PersonVO
     * @param personValueObject Object
     * @param nbsSecurityObj NBSSecurityObj
     * @return Hashtable
     * @throws NNDException
     */
    public Hashtable<?,?> getPersonRoles(Object personValueObject, NBSSecurityObj nbsSecurityObj)
                              throws NNDException
    {

        try
        {

            PersonVO personVO = (PersonVO)personValueObject;

            this.elrScopingPersonVO = personVO;

            /* The following lines were added to get the role when the patient is the scoping entity for ELRS
               The roles which are a part of the PersonVO are loaded based on subject_entity_uid and not scoping_entity_uid
             */

             Collection<Object>  roleDTColl = new ArrayList<Object> ();
            if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT))
            {
              RoleDAOImpl roleDAO = new RoleDAOImpl();
              roleDTColl = roleDAO.loadEntitiesScopedToPatient(personVO.getThePersonDT().getPersonUid().
                                               longValue());
            }
// ************* end changes ************************************

            if (NNDConstantUtil.notNull(personVO.getTheRoleDTCollection()))
            {

                if (!personVO.getTheRoleDTCollection().isEmpty())
                    roleDTColl.addAll(personVO.getTheRoleDTCollection());


            }

            if (roleDTColl != null)
                return (Hashtable<?,?>) getRolesAndScopingEntities(roleDTColl, nbsSecurityObj);
            else
               return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            NNDException nnde = new NNDException(
                                        "Exception while extracting person roles " +
                                        e.getMessage());
            nnde.setModuleName("ValueObjectExtractors.getPersonRoles");
            throw nnde;
        }
    }

    /**
     * Extracts the roles and the scoping entities if any for the OrganizationVO
     * @param organizationValueObject Object
     * @param nbsSecurityObj NBSSecurityObj
     * @return Hashtable
     * @throws NNDException
     */
    public Hashtable<Object,Object> getOrganizationRoles(Object organizationValueObject, NBSSecurityObj nbsSecurityObj)
                                    throws NNDException
    {

        try
        {

            OrganizationVO orgVO = (OrganizationVO)organizationValueObject;
            //ArrayList<Object> castorRoleDTs = null;

            if (NNDConstantUtil.notNull(orgVO.getTheRoleDTCollection()))
            {

                if (!orgVO.getTheRoleDTCollection().isEmpty())
                {
                       return (Hashtable<Object,Object>) getRolesAndScopingEntities(orgVO.getTheRoleDTCollection(), nbsSecurityObj);

                }
            }

            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            NNDException nnde = new NNDException(
                                        "Exception while extracting organization roles " +
                                        e.getMessage());
            nnde.setModuleName("ValueObjectExtractors.getOrganizationRoles");
            throw nnde;
        }
    }
    /**
     * Extracts the roles and the scoping entities if any for the MaterialVO
     * @param materialValueObject Object
     * @param nbsSecurityObj NBSSecurityObj
     * @return Hashtable
     * @throws NNDException
     */
    public Hashtable<?,?> getMaterialRoles(Object materialValueObject, NBSSecurityObj nbsSecurityObj)
                                throws NNDException
    {

        try
        {

            MaterialVO materialVO = (MaterialVO)materialValueObject;


            if (NNDConstantUtil.notNull(materialVO.getTheRoleDTCollection()))
            {   if (!materialVO.getTheRoleDTCollection().isEmpty())
                      return (Hashtable<?,?>) (getRolesAndScopingEntities (materialVO.getTheRoleDTCollection(),
                        nbsSecurityObj));


            }


            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();

            NNDException nnde = new NNDException(
                                        "Exception while extracting material roles " +
                                        e.getMessage());
            nnde.setModuleName("ValueObjectExtractors.getMaterialRoles");
            throw nnde;
        }
    }
    /**
     * Returns a Map<Object,Object> of roles and scoping entities
     * @param roleDTCollection  Collection
     * @param nbsSecurityObj NBSSecurityObj
     * @return Object
     * @throws NNDException
     */
    private Object getRolesAndScopingEntities(Collection<Object> roleDTCollection, NBSSecurityObj nbsSecurityObj) throws NNDException
{
Hashtable<Object,Object> rolesAndScopingEntities = new Hashtable<Object,Object>();
ArrayList<Object> castorRoleDTs = new ArrayList<Object> ();
ArrayList<Object> scopingEntities = new ArrayList<Object> ();
ArrayList<Object> roleDTs = (ArrayList<Object> )roleDTCollection;
Iterator<Object> itr = roleDTs.iterator();
Long entityUid = null;
try
{
while (itr.hasNext())
 {

    RoleDT roleDT = (RoleDT)itr.next();

    if (NNDConstantUtil.notNull(roleDT))
    {

      Role castorRole = new Role();
      NNDUtils nndUtil = new NNDUtils();

      if (NNDConstantUtil.notNull(roleDT.getScopingEntityUid())) {
            /* the following was added to get subject entities for ELRS since ELRs have
             the role dts mapped in a reverse order so if the role dt matches the following
           criteria it will be handled differently.
         */
        if (

            roleDT.getScopingClassCd().equals(NEDSSConstants.PAT)
            ) {
          //get the PersonVO for the subjectpersonVO
          PersonVO subjectPersonVO = (PersonVO)
              extractScopingOrSubjectEntityVO(roleDT.getScopingEntityUid(),
                                              NEDSSConstants.
                                              PERSON_CLASS_CODE,
                                              nbsSecurityObj);
          //set the scoping entity tempid which will be the PAT
          castorRole.setScopingEntityTempId(this.elrScopingPersonVO.
                                            getThePersonDT().getLocalId());
          // add it to the collection of entities
          scopingEntities.add(buildPersonEntity(subjectPersonVO,
              roleDT.getSubjectClassCd()));

        }
// ___________ end ELR changes ----------------------
        else {
          // we need to reset this variable to null since we need it only for ELRs
          this.elrScopingPersonVO = null;
          if (roleDT.getScopingClassCd().equals(NEDSSConstants.
              MATERIAL_CLASS_CODE)) {
            MaterialVO scopingMatVO = (MaterialVO)
                extractScopingOrSubjectEntityVO(roleDT.getScopingEntityUid(),
                NEDSSConstants.MATERIAL_CLASS_CODE, nbsSecurityObj);

            castorRole.setScopingEntityTempId(scopingMatVO.getTheMaterialDT().
                                              getLocalId());
            scopingEntities.add(buildMaterialEntity(scopingMatVO,
                roleDT.getScopingClassCd()));

          }
          else
          if ( (roleDT.getScopingClassCd().equals(NEDSSConstants.
              ORGANIZATION_CLASS_CODE))) {
            OrganizationVO scopingOrgVO = (OrganizationVO)
                extractScopingOrSubjectEntityVO(roleDT.getScopingEntityUid(),
                NEDSSConstants.ORGANIZATION_CLASS_CODE, nbsSecurityObj);

            castorRole.setScopingEntityTempId(scopingOrgVO.
                                              getTheOrganizationDT().
                                              getLocalId());
            scopingEntities.add(buildOrganizationEntity(scopingOrgVO,
                roleDT.getScopingClassCd()));
          }
          else
          if (
              (roleDT.getScopingClassCd().equals(NEDSSConstants.
              PERSON_CLASS_CODE))
              || (roleDT.getScopingClassCd().equals(NEDSSConstants.PAT))
              || (roleDT.getScopingClassCd().equals(NEDSSConstants.PROV))) {

            PersonVO scopingPersonVO = (PersonVO)
                extractScopingOrSubjectEntityVO(roleDT.getScopingEntityUid(),
                NEDSSConstants.PERSON_CLASS_CODE, nbsSecurityObj);

            castorRole.setScopingEntityTempId(scopingPersonVO.
                                              getThePersonDT().getLocalId());
            scopingEntities.add(buildPersonEntity(scopingPersonVO,
                roleDT.getScopingClassCd()));

          }

        }





      }
      if (NNDConstantUtil.notNull(roleDT.getSubjectEntityUid())) {
           entityUid = roleDT.getSubjectEntityUid();
           castorRole.setSubjectEntityTempId(entityUid.toString());

//              System.out.println("Roles " + castorRole.getSubjectClassCd());
}

       castorRoleDTs.add(nndUtil.copyObjects(roleDT, castorRole));
    }


}
}catch (NNDException nnde)
{
throw nnde;
}
catch (Exception e)
{
e.printStackTrace();
NNDException nndeOther = new NNDException("Exception while getting Roles and Scoping entities " + e.getMessage());
}
//System.out.println("UID ++++++++++++++ = " + entityUid);
//System.out.println("Role DTS ++++++++++++++ = " + castorRoleDTs.toString());
//System.out.println("scoping entities ++++++++++++++ = " + scopingEntities.toString());
if((NNDConstantUtil.notNull(castorRoleDTs  )) &&(NNDConstantUtil.notNull(entityUid)))
   if(!castorRoleDTs.isEmpty())
    rolesAndScopingEntities.put(entityUid,castorRoleDTs);
if(NNDConstantUtil.notNull(scopingEntities) )
  if(!scopingEntities.isEmpty())
       rolesAndScopingEntities.put(NNDConstantUtil.NND_ROLE_KEY,scopingEntities);

return rolesAndScopingEntities;

}
 /**
  * Extracts the scoping entity uids by calling the respective EJBs
  * @param entityUID Long
  * @param classCd String
  * @param nbsSecurityObj NBSSecurityObj
  * @return Object
  * @throws NNDException
  */

 private Object extractScopingOrSubjectEntityVO(Long entityUID, String classCd, NBSSecurityObj nbsSecurityObj)
throws NNDException

{ Object object = null;

   NedssUtils nedssUtils = new NedssUtils();

    if (!classCd.equals(NEDSSConstants.MATERIAL_CLASS_CODE))
    {
       EntityProxy entityProxy = null;

       object = nedssUtils.lookupBean(JNDINames.ENTITY_PROXY_EJB);
       EntityProxyHome entityProxyHome =
            (EntityProxyHome)PortableRemoteObject.narrow(object,
                                                     EntityProxyHome.class);
        try {
             entityProxy = entityProxyHome.create();
             if (classCd.equals(NEDSSConstants.PERSON_CLASS_CODE))
             {
               PersonVO personVO = entityProxy.getPerson(entityUID,nbsSecurityObj);

               return personVO;
              }

              else
                 if(classCd.equals(NEDSSConstants.ORGANIZATION_CLASS_CODE))
                 {

                   OrganizationVO orgVO = entityProxy.getOrganization(entityUID,nbsSecurityObj);
                   return orgVO;
                  }
              else
              throw new NNDException("Invalid Class Code for Scoping entity " + entityUID + "CD = " + classCd);
          }
           catch(Exception exception)
              {
                 String message = "Exception caught while extracting Scoping entitytVOS ORG/PSN ";
                 logger.debug(message, exception);
                 throw new NNDException(exception.getMessage());
              }
        }
        else if(classCd.equals(NEDSSConstants.MATERIAL_CLASS_CODE))
       {
        try {
              object = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
              EntityControllerHome entityControllerHome =
                              (EntityControllerHome)PortableRemoteObject.narrow(object,
                                                            EntityControllerHome.class);
              EntityController entityController = entityControllerHome.create();
              MaterialVO materialVO  = entityController.getMaterial(entityUID,nbsSecurityObj);
              return materialVO;

            }

       catch(Exception exception)
           {
             String message = "Exception caught while extracting Scoping entitytVOS MAT";
             logger.debug(message, exception);
             throw new NNDException(exception.getMessage());
            }
        }
        else
            throw new NNDException("Invalid Class Code for Scoping entity " + entityUID + "CD = " + classCd);




}

        /**
         *
         * @param materialObj Object
         * @param scopingRoleCd String
         * @return Object
         * @throws NNDException
         */

        public Object buildMaterialEntity(Object materialObj, String scopingRoleCd) throws NNDException
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
           this.localIdMap.put(materialDT.getMaterialUid(), materialDT.getLocalId());

           if (NNDConstantUtil.notNull(materialDT)) {
             castorMaterial =   (Material) populateCastorObjects(materialDT, castorMaterial);


         }
           castorEntity.setClassCd(NEDSSConstants.MATERIAL_CLASS_CODE);
           castorMaterial.addParticipationTypeCd(scopingRoleCd);
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



private  EntityId[] getEntityIdCollection(Collection<Object> entityIdColl) throws NNDException
{
EntityId [] entityId = new EntityId[entityIdColl.size()];
ArrayList<Object> arrayList = (ArrayList<Object> ) entityIdColl;
Iterator itr = null;
itr = arrayList.iterator();
int i = 0;
try
{
while (itr.hasNext())
{
     EntityIdDT entityIdDT = new EntityIdDT();
     EntityId castorEntityId = new EntityId();

     entityIdDT =  (EntityIdDT)itr.next();
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
Iterator<Object> itr = null;
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
//                  System.out.println("in the entity tele locator ----------------------<<");
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


  public Object populateCastorObjects(Object nbsObject, Object castorObject) throws NNDException
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
    *
    * @param organizationObj Object
    * @param scopingRoleCd String
    * @return Object
    * @throws NNDException
    */
   public Object buildOrganizationEntity(Object organizationObj, String scopingRoleCd)throws NNDException
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
     this.localIdMap.put(organizationDT.getOrganizationUid(), organizationDT.getLocalId());

    if (NNDConstantUtil.notNull(organizationDT))
    {
      castorOrganization =   (Organization) populateCastorObjects(organizationDT, castorOrganization);


    }
    castorEntity.setClassCd(NEDSSConstants.PAR102_SUB_CD);
    castorOrganization.addParticipationTypeCd(scopingRoleCd);
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

/**
 *
 * @param personObj Object
 * @param scopingRoleCd String
 * @return Object
 * @throws NNDException
 */

public  Object  buildPersonEntity(Object personObj, String scopingRoleCd) throws NNDException
{
        Entity castorEntity = new Entity();
        PersonNameDT personName = new PersonNameDT();
        PersonVO personVO = (PersonVO) personObj;
        PersonDT personDT = new PersonDT();
        personDT = personVO.getThePersonDT();
        Person castorPerson = new Person();

        castorPerson.addParticipationTypeCd(scopingRoleCd);
   try {
        if (NNDConstantUtil.notNull(personDT))
        {
            castorPerson =   (Person) populateCastorObjects(personDT, castorPerson);
            this.localIdMap.put(personDT.getPersonUid(), personDT.getLocalId());

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
//          System.out.println("Error while getting Ethnic Group " + e.getMessage());
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
        /**
         * If the param is null it will return this objects localidmap other wise
         * it will modify the param map and add those localids that are missing and return it
         * @param localIdMap Hashtable
         * @return Hashtable
         */
public Hashtable<Object,Object> getScopingEntityLocalIdMap(Hashtable<Object,Object> localIdMap)
        {

        if (localIdMap != null)

        {
          Set<Object> keySet = this.localIdMap.keySet();
         Iterator<Object>  itr =  keySet.iterator();
         while (itr.hasNext())
         {
           Object key =  itr.next();
           if (!(localIdMap.containsKey(key)))
             localIdMap.put(key,this.localIdMap.get(key));



         }
         return localIdMap;

        }

      else
          return this.localIdMap;

        }



}