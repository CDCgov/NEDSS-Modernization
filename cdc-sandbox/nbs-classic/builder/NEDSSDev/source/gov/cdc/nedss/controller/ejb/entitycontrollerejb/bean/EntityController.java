//
// -- Java Code Generation Process --

package gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean;

// Import Statements
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entitygroup.dt.EntityGroupDT;
import gov.cdc.nedss.entity.entitygroup.vo.EntityGroupVO;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.entity.nonpersonlivingsubject.dt.NonPersonLivingSubjectDT;
import gov.cdc.nedss.entity.nonpersonlivingsubject.vo.NonPersonLivingSubjectVO;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.place.dt.PlaceDT;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
// gov.cdc.nedss.* imports
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
/**
 * Title: EntityController remote Interface
 * Description: This is the remote interface representing the business methods being
 * used by its client. EntityController is a session bean that is used to access entities(Person, organization,
 * material, EntityGroup, NonPersonLivingSubject) and their dependent objects(like person name, person race etc).
 * Copyright:    Copyright (c) 2002
 * Company: Computer Sciences Corporation
 * @version 1.0
 */
public interface EntityController extends javax.ejb.EJBObject
{

    /**
     * Returns the personVO for a given personUID.
     * @param personUID       the person UID(Long value)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       PersonVO for a given personUID.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BEFBC8E03E4
     * @J2EE_METHOD  --  getPerson
     */

    public PersonVO getPerson    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Creates(if personVO.isItNew() is true) or updates(if personVO.isItDirty() is true) the personVO
     * @param personVO       the personVO object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       Long value representing the personUID created/updated in the process.
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BEFBCF00114
     * @J2EE_METHOD  --  setPerson
     */

    public Long setPerson    (PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3E7B37970138
     * @J2EE_METHOD  --  getProvider
     */
    public PersonVO getProvider    (Long personUID, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException;

    /**
      * @roseuid 3E7B378F01C5
      * @J2EE_METHOD  --  setProvider
      */

    public Long setProvider    (PersonVO personVO, String businessTriggerCd, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSConcurrentDataException;


    /**
     * Returns the personDT for a given personUID.
     * @param personUID       the personUID(Long value)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       PersonDT for a given personUID.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BEFBD6D0222
     * @J2EE_METHOD  --  getPersonInfo
     */
    public PersonDT getPersonInfo    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the personDT object
     * @param personDT       the personDT object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BEFBDB7026F
     * @J2EE_METHOD  --  setPersonInfo
     */
    public void setPersonInfo    (PersonDT personDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns the Collection<Object>  of personNameDT's for a given personUID.
     * @param personUID       the personUID(Long value)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       PersonNameDTCollection  object for a given personUID.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BEFBE5D0304
     * @J2EE_METHOD  --  getPersonNames
     */
    public Collection<Object> getPersonNames    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the personNamesDT for a given personUID for a given nmUseCd.
     * @param personUID       the personUID(Long value)
     * @param nmUseCd         the nmUseCd(String value)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       PersonNameDT for a given personUID.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BEFBEAC005E
     * @J2EE_METHOD  --  getPersonNames
     */
    public PersonNameDT getPersonName    (Long personUID, String nmUseCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the personNamesDT Collection<Object> object.
     * @param personNames     the personNameDT Collection<Object> object.
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BEFBF1B02B7
     * @J2EE_METHOD  --  setPersonNames
     */
    public void setPersonNames    (Collection<Object> personNames, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns the personRaceDT Collection<Object> object.
     * @param personUID       the personUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       PersonRaceDT Collection<Object> for a given personUID.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BEFBF6D0279
     * @J2EE_METHOD  --  getPersonRaces
     */
    public Collection<Object> getPersonRaces    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The personRaceDT object for a given personUID and given raceCd
     * @param personUID       the personUID(Long value)
     * @param raceCd       the raceCd(String value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       PersonRaceDT for a given personUID.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BEFBFB10371
     * @J2EE_METHOD  --  getPersonRace
     */
    public PersonRaceDT getPersonRace    (Long personUID, String raceCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the PersonRaceDT Collection<Object> object
     * @param personRaces       the personRaceDT collection object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BEFC0260030
     * @J2EE_METHOD  --  setPersonRaces
     */
    public void setPersonRaces    (Collection<Object> personRaces, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns the PersonEthnicGroupDT collection object
     * @param personUID       the personUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       PersonEthnicGroupDT collection object
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BEFC0B300BF
     * @J2EE_METHOD  --  getPersonEthnicGroups
     */
    public Collection<Object> getPersonEthnicGroups    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the personEthnicGroupDT object for a given personUID.
     * @param personUID       the personUID(Long value)
     * @param ethnicGroupCd       the ethnicGroupCd(String ethnicGroupCd)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       PersonEthnicGroupDT object
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BEFC39C0279
     * @J2EE_METHOD  --  getPersonEthnicGroup
     */
    public PersonEthnicGroupDT getPersonEthnicGroup    (Long personUID, String ethnicGroupCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the personEthnicGroupDT collection object
     * @param personEthnicGroups       the personEthnicGroupDT Collection<Object> object
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BEFC581006D
     * @J2EE_METHOD  --  setPersonEthnicGroups
     */
    public void setPersonEthnicGroups    (Collection<Object> personEthnicGroups, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns the personID collection object associated with a given personUID
     * @param personUID       the person UID(Long value)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       Collection<Object> object representing PersonID's
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2BEF1025D
     * @J2EE_METHOD  --  getPersonIDs
     */
    public Collection<Object> getPersonIDs    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the personID collection object associated with a given personUID and typeCd
     * @param personUID       the person UID(Long value)
     * @param typeCd       the typeCd(String typeCd)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       Collection<Object> object representing PersonID's
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2BF5D00C7
     * @J2EE_METHOD  --  getPersonIDs
     */
    public Collection<Object> getPersonIDs    (Long personUID, String typeCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the personIDs Collection<Object> object
     * @param personIDs       the personIDs object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2C09300E1
     * @J2EE_METHOD  --  setPersonIDs
     */
    public void setPersonIDs    (Collection<Object> personIDs, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns the PersonLocator collection object for a given personUID
     * @param personUID       the personUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       Collection<Object> of  personLocators
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2C15901CC
     * @J2EE_METHOD  --  getAllPersonLocators
     */
    public Collection<Object> getAllPersonLocators    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the PersonLocator collection object
     * @param entityLocatorParticipationDTCollection        the entityLocatorParticipationDTCollection  object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2C22F004D
     * @J2EE_METHOD  --  setAllPersonLocators
     */
    public void setAllPersonLocators    (Collection<Object> entityLocatorParticipationDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns the personPhysicalLocator Collection<Object> object for a given personUID
     * @param personUID       the personUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return      Collection<Object> of personPhysicalLocators
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2C2B40316
     * @J2EE_METHOD  --  getPersonPhysicalLocators
     */
    public Collection<Object> getPersonPhysicalLocators    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the PersonPostalLocators Collection<Object> object for a given personUID
     * @param personUID       the personUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       Collection<Object> of PersonPostalLocators
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2C8B302B9
     * @J2EE_METHOD  --  getPersonPostalLocators
     */
    public Collection<Object> getPersonPostalLocators    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the PersonTeleLocators Collection<Object> object for a given personUID
     * @param personUID       the personUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       Collection<Object> of PersonTeleLocators
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2C97201FF
     * @J2EE_METHOD  --  getPersonTeleLocators
      */
    public Collection<Object> getPersonTeleLocators    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the NonPersonLivingSubjectVO object for a given nonPersonLivingSubjectUID
     * @param nonPersonLivingSubjectUID       the nonPersonLivingSubjectUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       NonPersonLivingSubjectVO for a given nonPersonLivingSubjectUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2CC540337
     * @J2EE_METHOD  --  getNonPersonLivingSubject
     */
    public NonPersonLivingSubjectVO getNonPersonLivingSubject    (Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Creates(if nonPersonLivingSubjectVO.isItNew() is true) or updates(if nonPersonLivingSubjectVO.isItDirty() is true)
     * nonPersonLivingSubjectVO objects
     * @param nonPersonLivingSubjectVO       the nonPersonLivingSubjectVO object
     * @param nbsSecurityObj                 the NBSSecurityObj (security object)
     * @return       Long value representing theNonPersonLivingSubjectUID created/updated in the process.
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2CCC30192
     * @J2EE_METHOD  --  setNonPersonLivingSubject
     */
    public Long setNonPersonLivingSubject
                                      (NonPersonLivingSubjectVO nonPersonLivingSubjectVO,
                                      NBSSecurityObj nbsSecurityObj)
                                      throws java.rmi.RemoteException,
                                      javax.ejb.EJBException,
                                      NEDSSConcurrentDataException;

    /**
     * Returns the NonPersonLivingSubjectDT object for a given nonPersonLivingSubjectUID
     * @param nonPersonLivingSubjectUID        the nonPersonLivingSubjectUID(Long value)
     * @param nbsSecurityObj                the NBSSecurityObj (security object)
     * @return       NonPersonLivingSubjectDT for a given nonPersonLivingSubjectUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2CD3102BC
     * @J2EE_METHOD  --  getNonPersonLivingSubjectInfo
     */
    public NonPersonLivingSubjectDT getNonPersonLivingSubjectInfo    (Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the nonPersonLivingSubjectDT
     * @param nonPersonLivingSubjectDT       the nonPersonLivingSubjectDT object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2CDA70045
     * @J2EE_METHOD  --  setNonPersonLivingSubjectInfo
     */
    public void setNonPersonLivingSubjectInfo
                                 (NonPersonLivingSubjectDT nonPersonLivingSubjectDT,
                                 NBSSecurityObj nbsSecurityObj)
                                 throws java.rmi.RemoteException,
                                 javax.ejb.EJBException,
                                 NEDSSConcurrentDataException;

    /**
     * Returns The getNonPersonLivingSubjectIDs Collection<Object> object for a given nonPersonLivingSubjectUID
     * @param nonPersonLivingSubjectUID       the nonPersonLivingSubjectUID(Long value)
     * @param nbsSecurityObj                  the NBSSecurityObj (security object)
     * @return       Collection<Object> of getNonPersonLivingSubjectIDs for a given nonPersonLivingSubjectUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2CE73005C
     * @J2EE_METHOD  --  getNonPersonLivingSubjectIDs
     */
    public Collection<Object> getNonPersonLivingSubjectIDs    (Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The getNonPersonLivingSubjectIDs Collection<Object> object for a given nonPersonLivingSubjectUID and given typeCd
     * @param nonPersonLivingSubjectUID       the nonPersonLivingSubjectUID(Long value)
     * @param typeCd                          the typeCd (String typeCd)
     * @param nbsSecurityObj                  the NBSSecurityObj (security object)
     * @return       Collection<Object> of getNonPersonLivingSubjectIDs for a given nonPersonLivingSubjectUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2CF7B0323
     * @J2EE_METHOD  --  getNonPersonLivingSubjectIDs
     */
    public Collection<Object> getNonPersonLivingSubjectIDs    (Long nonPersonLivingSubjectUID, String typeCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the existing nonPersonLivingSubjectIDs collection
     * @param nonPersonLivingSubjectIDs       the collection of nonPersonLivingSubjectIDs
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2D0280047
     * @J2EE_METHOD  --  setNonPersonLivingSubjectIDs
     */
    public void setNonPersonLivingSubjectIDs
                      (Collection<Object> nonPersonLivingSubjectIDs, NBSSecurityObj nbsSecurityObj)
                      throws java.rmi.RemoteException,
                      javax.ejb.EJBException,
                      NEDSSConcurrentDataException;

    /**
     * Returns The collection of NonPersonLivingSubjectLocators
     * @param nonPersonLivingSubjectUID       the nonPersonLivingSubjectUID(Long value)
     * @param nbsSecurityObj                  the NBSSecurityObj (security object)
     * @return       NonPersonLivingSubjectLocators
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2D26D0024
     * @J2EE_METHOD  --  getAllNonPersonLivingSubjectLocators
     */
    public Collection<Object> getAllNonPersonLivingSubjectLocators    (Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the nonPersonLivingSubjectLocators
     * @param entityLocatorParticipationDTCollection        the entityLocatorParticipationDTCollection  collection object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2D3420279
     * @J2EE_METHOD  --  setAllNonPersonLivingSubjectLocators
     */
    public void setAllNonPersonLivingSubjectLocators    (Collection<Object> entityLocatorParticipationDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns The nonPersonLivingSubjectPhysicalLocators object for a given nonPersonLivingSubjectUID
     * @param nonPersonLivingSubjectUID       the nonPersonLivingSubjectUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       nonPersonLivingSubjectPhysicalLocators for a given nonPersonLivingSubjectUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2DB82012C
     * @J2EE_METHOD  --  getNonPersonLivingSubjectPhysicalLocators
     */
    public Collection<Object> getNonPersonLivingSubjectPhysicalLocators    (Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The nonPersonLivingSubjectPostalLocators object for a given nonPersonLivingSubjectUID
     * @param nonPersonLivingSubjectUID       the nonPersonLivingSubjectUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       Collection<Object> nonPersonLivingSubjectPostalLocators for a given nonPersonLivingSubjectUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2DC630143
     * @J2EE_METHOD  --  getNonPersonLivingSubjectPostalLocators
     */
    public Collection<Object> getNonPersonLivingSubjectPostalLocators    (Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The nonPersonLivingSubjectTeleLocators object for a given nonPersonLivingSubjectUID
     * @param nonPersonLivingSubjectUID       the nonPersonLivingSubjectUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       Collection<Object> of PersonLivingSubjectTeleLocators for a given nonPersonLivingSubjectUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2DD780174
     * @J2EE_METHOD  --  getNonPersonLivingSubjectTeleLocators
     */
    public Collection<Object> getNonPersonLivingSubjectTeleLocators    (Long nonPersonLivingSubjectUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The OrganizationVO object for a given organizationUID
     * @param organizationUID       the organizationUID(Long value)
     * @param nbsSecurityObj        the NBSSecurityObj (security object)
     * @return       OrganizationVO for a given organizationUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2E20801AF
     * @J2EE_METHOD  --  getOrganization
     */
    public OrganizationVO getOrganization    (Long organizationUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Creates(if organizationVO.isItNew() is true) or updates(if organizationVO.isItDirty() is true) the organizationVO
     * @param organizationVO       the organizationVO object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       Long value representing the organizationUID created/updated in the process.
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2E247020A
     * @J2EE_METHOD  --  setOrganization
     */
   public Long setOrganization    (OrganizationVO organizationVO, NBSSecurityObj nbsSecurityObj) throws NEDSSConcurrentDataException, java.rmi.RemoteException, javax.ejb.EJBException;
    /**
     * Sets the organization values in the databse based on the businessTrigger
     * @roseuid 3E6E4E9B01F4
     * @param organizationVO the OrganizationVO
     * @param businessTriggerCd  the String
     * @param nbsSecurityObj  the NBSSecurityObj
     * @return  organizationUID  the Long
     * @throws NEDSSConcurrentDataException
     * @throws javax.ejb.EJBException
     */
   public Long setOrganization    (OrganizationVO organizationVO, String businessTriggerCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, NEDSSConcurrentDataException,javax.ejb.EJBException;


    /**
     * Returns The OrganizationDT object for a given organizationUID
     * @param organizationUID       the organizationUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       OrganizationDT for a given organizationUID.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2E29503A7
     * @J2EE_METHOD  --  getOrganizationInfo
     */
    public OrganizationDT getOrganizationInfo    (Long organizationUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the existing organizationDT object
     * @param organizationDT       the organizationDT object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2E2F000F5
     * @J2EE_METHOD  --  setOrganizationInfo
     */
    public void setOrganizationInfo    (OrganizationDT organizationDT, NBSSecurityObj nbsSecurityObj) throws NEDSSConcurrentDataException, java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the OrganizationID collection object associated with a given organizationUID
     * @param organizationUID       the organizationUID(Long value)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       Collection<Object> object representing the OrganizationID's
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2E3580271
     * @J2EE_METHOD  --  getOrganizationIDs
     */
    public Collection<Object> getOrganizationIDs    (Long organizationUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the OrganizationID collection object associated with a given organizationUID and a given typeCd
     * @param organizationUID       the organizationUID(Long value)
     * @param typeCd                the typeCd(String typeCd)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       Collection<Object> object representing the OrganizationID's
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2E3E40055
     * @J2EE_METHOD  --  getOrganizationIDs
     */
    public Collection<Object> getOrganizationIDs    (Long organizationUID, String typeCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The OrganizationLocators Collection<Object> for a given organizationUID
     * @param organizationUID       the organizationUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       Collection<Object> object representing OrganizationLocators
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2E44B0176
     * @J2EE_METHOD  --  getAllOrganizationLocators
     */
    public Collection<Object> getAllOrganizationLocators    (Long organizationUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the OrganizationLocators(entityLocatorParticipationDT's)
     * @param entityLocatorParticipationDTCollection        the entityLocatorParticipationDTCollection(entityLocatorParticipationDT Collection<Object>)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2E49E0085
     * @J2EE_METHOD  --  setAllOrganizationLocators
     */
    public void setAllOrganizationLocators    (Collection<Object> entityLocatorParticipationDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns the collection of OrganizationPhysicalLocators associated with a given organizationUID
     * @param organizationUID       the organizationUID(Long value)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return Collection<Object> of OrganizationPhysicalLocators
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2E4E7030B
     * @J2EE_METHOD  --  getOrganizationPhysicalLocators
     */
    public Collection<Object> getOrganizationPhysicalLocators    (Long organizationUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the collection of OrganizationPostalLocators associated with a given organizationUID
     * @param organizationUID       the organizationUID(Long value)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return Collection<Object> of OrganizationPostalLocators
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2E62C027C
     * @J2EE_METHOD  --  getOrganizationPostalLocators
     */
    public Collection<Object> getOrganizationPostalLocators    (Long organizationUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the collection of OrganizationTeleLocators associated with a given organizationUID
     * @param organizationUID       the organizationUID(Long value)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return Collection<Object> of OrganizationTeleLocators
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2E6EE037F
     * @J2EE_METHOD  --  getOrganizationTeleLocators
     */
    public Collection<Object> getOrganizationTeleLocators    (Long organizationUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The materialDT object for a given materialUID
     * @param materialUID       the materialUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       MaterialVO object.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2E8E7000A
     * @J2EE_METHOD  --  getMaterial
     */
    public MaterialVO getMaterial    (Long materialUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Creates(if materialVO.isItNew() is true) or
     * updates(if materialVO.isItDirty() is true) the materialVO for a given materialUID.
     * @param materialVO       the materialVO object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       Long value representing the materialVO object created/updated in the process.
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2E94900DD
     * @J2EE_METHOD  --  setMaterial
     */
    public Long setMaterial    (MaterialVO materialVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSConcurrentDataException;

    /**
     * Returns The materialDT object for a given materialUID
     * @param materialUID       the materialUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return      materialDT
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2E9B6009E
     * @J2EE_METHOD  --  getMaterialInfo
     */
    public MaterialDT getMaterialInfo    (Long materialUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates a given materialDT object.
     * @param materialDT       the materialDT object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2EA110095
     * @J2EE_METHOD  --  setMaterialInfo
     */
    public void setMaterialInfo    (MaterialDT materialDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSConcurrentDataException;

    /**
     * Returns the collection of MaterialLocators object for a given materialUID
     * @param materialUID       the materialUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       Collection<Object> of materialLocators.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2EA9901BD
     * @J2EE_METHOD  --  getAllMaterialLocators
     */
    public Collection<Object> getAllMaterialLocators    (Long materialUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the MaterialLocators(entityLocatorParticipationDT Collection<Object>) object
     * @param entityLocatorParticipationDTCollection        the entityLocatorParticipationDT Collection<Object>
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2EBCA03C4
     * @J2EE_METHOD  --  setAllMaterialLocators
     */
    public void setAllMaterialLocators    (Collection<Object> entityLocatorParticipationDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSConcurrentDataException;

    /**
     * Returns materialPhysicalLocators for a given materialUID
     * @param materialUID       the materialUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       materialPhysicalLocators Collection<Object> object
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2EC6E0044
     * @J2EE_METHOD  --  getMaterialPhysicalLocators
     */
    public Collection<Object> getMaterialPhysicalLocators    (Long materialUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns materialPostalLocators for a given materialUID
     * @param materialUID       the materialUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       materialPostalLocators Collection<Object> object
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2ED4B03AA
     * @J2EE_METHOD  --  getMaterialPostalLocators
     */
    public Collection<Object> getMaterialPostalLocators    (Long materialUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns materialTeleLocators for a given materialUID
     * @param materialUID       the materialUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       materialTeleLocators Collection<Object> object
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2EE6D0112
     * @J2EE_METHOD  --  getMaterialTeleLocators
     */
    public Collection<Object> getMaterialTeleLocators    (Long materialUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the MaterialID collection object associated with a given materialUID
     * @param materialUID       the materialUID(Long value)
     * @param nbsSecurityObj    the NBSSecurityObj (security object)
     * @return       Collection<Object> object representing the MaterialID's
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2F0E700A5
     * @J2EE_METHOD  --  getMaterialIDs
     */
    public Collection<Object> getMaterialIDs    (Long materialUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the MaterialID collection object associated with a given materialUID and a given typeCd
     * @param materialUID       the materialUID(Long value)
     * @param typeCd            the typeCd(String value)
     * @param nbsSecurityObj    the NBSSecurityObj (security object)
     * @return       Collection<Object> object representing the MaterialID's
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2F164028F
     * @J2EE_METHOD  --  getMaterialIDs
     */
    public Collection<Object> getMaterialIDs    (Long materialUID, String typeCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates collection of materialIDs
     * @param materialIDs       the materialIDs collection object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2F1D20342
     * @J2EE_METHOD  --  setMaterialIDs
     */
    public void setMaterialIDs    (Collection<Object> materialIDs, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSConcurrentDataException;

    /**
     * Returns The placeVO object for a given placeUID
     * @param placeUID       the placeUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       PlaceVO for a given placeUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2F3080207
     * @J2EE_METHOD  --  getPlace
     */
    public PlaceVO getPlace    (Long placeUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Creates(in case placeVO.isItNew() is true) or Updates(in case placeVO.isItDirty() is true) the placeVO
     * @param placeVO       the placeVO object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       Long value representing the placeUID created/updated in the process.
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2F3790079
     * @J2EE_METHOD  --  setPlace
     */
    public Long setPlace    (PlaceVO placeVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns The PlaceDT object for a given personUID
     * @param placeUID       the placeUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       PlaceDT for a given placeUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2F3D301A5
     * @J2EE_METHOD  --  getPlaceInfo
     */
    public PlaceDT getPlaceInfo    (Long placeUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the placeDT object
     * @param placeDT       the PlaceDT object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2F43E014F
     * @J2EE_METHOD  --  setPlaceInfo
     */
    public void setPlaceInfo    (PlaceDT placeDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSConcurrentDataException, CreateException, RemoteException, NEDSSSystemException, FinderException;

    /**
     * Returns The placeIDs collection for a given placeUID
     * @param placeUID       the personUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       placeIDs collection for a given placeUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2F4CB0238
     * @J2EE_METHOD  --  getPlaceIDs
     */
    public Collection<Object> getPlaceIDs    (Long placeUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The placeIDs collection for a given placeUID
     * @param placeUID       the personUID(Long value)
     * @param typeCd       the typeCd(String value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       placeIDs collection for a given placeUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2F53701CF
     * @J2EE_METHOD  --  getPlaceIDs
     */
    public Collection<Object> getPlaceIDs    (Long placeUID, String typeCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the PlaceID's collection objects
     * @param placeIDs       the placeIDs collection
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2F5B101D4
     * @J2EE_METHOD  --  setPlaceIDs
     */
    public void setPlaceIDs    (Collection<Object> placeIDs, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns The PlaceLocators object for a given placeUID
     * @param placeUID        the placeUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       PlaceLocators for a given placeUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2F61B019B
     * @J2EE_METHOD  --  getAllPlaceLocators
     */
    public Collection<Object> getAllPlaceLocators    (Long placeUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * updates the placeLocators collection for given entityLocatorParticipationDTCollection
     * @param entityLocatorParticipationDTCollection        the entityLocatorParticipationDT Collection<Object>
     * @param nbsSecurityObj                               the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2F6910245
     * @J2EE_METHOD  --  setAllPlaceLocators
     */
    public void setAllPlaceLocators    (Collection<Object> entityLocatorParticipationDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns The placePhysicalLocators collection for a given placeUID
     * @param placeUID       the placeUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       placePhysicalLocators collection
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2F6F201FE
     * @J2EE_METHOD  --  getPlacePhysicalLocators
     */
    public Collection<Object> getPlacePhysicalLocators    (Long placeUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The placePostalLocators collection for a given placeUID
     * @param placeUID       the placeUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       placePostalLocators collection
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2F80F03AC
     * @J2EE_METHOD  --  getPlacePostalLocators
     */
    public Collection<Object> getPlacePostalLocators    (Long placeUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The placeTeleLocators collection for a given placeUID
     * @param placeUID       the placeUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       placeTeleLocators collection
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2FB27001C
     * @J2EE_METHOD  --  getPlaceTeleLocators
     */
    public Collection<Object> getPlaceTeleLocators    (Long placeUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The OrganizationNameDT Collection<Object> for a given organizationUID and given nmUseCd
     * @param organizationUID       the organizationUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       OrganizationNameDT Collection<Object>
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2FCDC0291
     * @J2EE_METHOD  --  getOrganizationNames
     */
    public Collection<Object> getOrganizationNames    (Long organizationUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The OrganizationNameDT Collection<Object> for a given organizationUID and given nmUseCd
     * @param organizationUID       the organizationUID(Long value)
     * @param nmUseCd       the nmUseCd(String value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       OrganizationNameDT Collection<Object>
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF2FD61035B
     * @J2EE_METHOD  --  getOrganizationNames
     */
    public Collection<Object> getOrganizationNames    (Long organizationUID, String nmUseCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the organizationNames (organizationNameDT collection)
     * @param organizationNames      organizationNames(organizationNameDT collection)
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF2FFE502A3
     * @J2EE_METHOD  --  setOrganizationNames
     */
    public void setOrganizationNames    (Collection<Object> organizationNames, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns the EntityGroupVO object for a given entityGroupUID
     * @param entityGroupUID       the entityGroupUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       EntityGroupVO for a given entityGroupUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF9B58B03D1
     * @J2EE_METHOD  --  getEntityGroup
     */
    public EntityGroupVO getEntityGroup    (Long entityGroupUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Creates(if entityGroupVO.isItNew() is true) or updates(if entityGroupVO.isItDirty() is true) the entityGroupVO
     * @param entityGroupVO       the EntityGroupVO object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       Long value representing the EntityGroupUID created/updated in the process.
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF9B6560292
     * @J2EE_METHOD  --  setEntityGroup
     */
    public Long setEntityGroup    (EntityGroupVO entityGroupVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, NEDSSConcurrentDataException, javax.ejb.EJBException;

    /**
     * Returns The EntityGroupDT object for a given entityGroupUID
     * @param entityGroupUID       the entityGroupUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       EntityGroupDT for a given entityGroupUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BF9BB70011D
     * @J2EE_METHOD  --  getEntityGroupInfo
     */
    public EntityGroupDT getEntityGroupInfo    (Long entityGroupUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates the entityGroupDT
     * @param entityGroupDT       the EntityGroupDT object
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BF9BE06007B
     * @J2EE_METHOD  --  setEntityGroupInfo
     */
    public void setEntityGroupInfo    (EntityGroupDT entityGroupDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, NEDSSConcurrentDataException, javax.ejb.EJBException;

    /**
     * Returns The EntityGroupIDs collection for a given entityGroupUID
     * @param personUID       the entityGroupUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       EntityGroupIDs for a given entityGroupUID.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BFA7906020C
     * @J2EE_METHOD  --  getEntityGroupIDs
     */
    public Collection<Object> getEntityGroupIDs    (Long entityGroupUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The EntityGroupIDs collection for a given entityGroupUID and given typeCd
     * @param personUID       the entityGroupUID(Long value)
     * @param typeCd          the typeCd(String value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       EntityGroupIDs for a given entityGroupUID.
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BFA79B401F8
     * @J2EE_METHOD  --  getEntityGroupIDs
     */
    public Collection<Object> getEntityGroupIDs    (Long entityGroupUID, String typeCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates all of Entity Group EntityGroupIDs based on entityGroupIDs and security
     * @param entityGroupIDs       the entityGroupIDs Collection<Object>
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BFAB9A40345
     * @J2EE_METHOD  --  setEntityGroupIDs
     */
    public void setEntityGroupIDs    (Collection<Object> entityGroupIDs, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, NEDSSConcurrentDataException, javax.ejb.EJBException;

    /**
     * Returns all of Entity Group Entity group Locators based on UID and security
     * @param entityGroupUID       the entityGroupUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       entityGroupLocatorCollection  for a given entityGroupUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BFABA0E03C0
     * @J2EE_METHOD  --  getAllEntityGroupLocators
     */
    public Collection<Object> getAllEntityGroupLocators    (Long entityGroupUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Updates all of Entity Group Entity Locators based on entityLocatorParticipationDTCollection  and security
     * @param entityLocatorParticipationDTCollection        the entityLocatorParticipationDT Collection<Object>
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3BFABC600003
     * @J2EE_METHOD  --  setAllEntityGroupLocators
     */
    public void setAllEntityGroupLocators    (Collection<Object> entityLocatorParticipationDTCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * Returns all of Entity Group Entity Postal Locators based on UID and security
     * @param entityGroupUID       the entityGroupUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       entityGroupPostalLocatorCollection  for a given entityGroupUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BFABCE20172
     * @J2EE_METHOD  --  getEntityGroupPhysicalLocators
     */
    public Collection<Object> getEntityGroupPhysicalLocators    (Long entityGroupUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns all of Entity Group Entity Postal Locators based on UID and security
     * @param entityGroupUID       the entityGroupUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       entityGroupPostalLocatorCollection  for a given entityGroupUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BFABDCE01A3
     * @J2EE_METHOD  --  getEntityGroupPostalLocators
     */
    public Collection<Object> getEntityGroupPostalLocators    (Long entityGroupUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns all of Entity Group Entity Tele Locators based on UID and security
     * @param entityGroupUID       the entityGroupUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       entityGroupTeleLocatorCollection  for a given entityGroupUID
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3BFABFB3036D
     * @J2EE_METHOD  --  getEntityGroupTeleLocators
     */
    public Collection<Object> getEntityGroupTeleLocators    (Long entityGroupUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the roleDT for a given subjectEntityUID, scopingEntityUID and classCd
     * @param subjectEntityUID       the subjectEntityUID(Long value)
     * @param scopingEntityUID       the scopingEntityUID(Long value)
     * @param classCd       the classCd(String value)
     * @param roleSeq       the roleSeq(Integer value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       RoleDT
     * @throws RemoteException
     * @throws EJBException
     * ####not yet implemented####
     * @roseuid 3BFAF95C00CE
     * @J2EE_METHOD  --  getRole
     */
   public RoleDT getRole    (Long subjectEntityUID, String classCd, Integer roleSeq, Long scopingEntityUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the role collection for a given subjectEntityUID
     * @param subjectEntityUID       the subjectEntityUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       RoleDT Collection<Object>
     * @throws RemoteException
     * @throws EJBException
     * ####not yet implemented####
     * @roseuid 3BFAFA0A03D1
     * @J2EE_METHOD  --  getRoles
     */
    public Collection<Object> getRoles    (Long subjectEntityUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the role collection for a given subjectEntityUID, scopingEntityUID and classCd
     * @param subjectEntityUID       the subjectEntityUID(Long value)
     * @param scopingEntityUID       the scopingEntityUID(Long value)
     * @param classCd       the classCd(String value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       RoleDT Collection<Object>
     * @throws RemoteException
     * @throws EJBException
     * ####not yet implemented####
     * @roseuid 3C02ADEA0373
     * @J2EE_METHOD  --  getRoles
     */
    public Collection<Object> getRoles    (Long subjectEntityUID, String classCd, Long scopingEntityUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns the ParticipationDT object for a given subjectEntityUID and given actUID
     * @param subjectEntityUID       the subjectEntityUID(Long value)
     * @param actUID       the actUID(Long value)
     * @param classCd                the classCd(String value)
     * @param roleSeq                the roleSeq(Integer value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       ParticipationDT
     * @throws RemoteException
     * @throws EJBException
     * ####not yet implemented####
     * @roseuid 3C02AE90023C
     * @J2EE_METHOD  --  getParticipation
     */
    public ParticipationDT getParticipation    (Long subjectEntityUID, Long actUID, Integer participationSeq, String classCd, Integer roleSeq, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Returns The participationDT collection object
     * @param subjectEntityUID       the subjectEntityUID(Long value)
     * @param actUID                 the actUID(Long value)
     * @param nbsSecurityObj  the NBSSecurityObj (security object)
     * @return       ParticipationDT collection
     * @throws RemoteException
     * @throws EJBException
     * ####not yet implemented####
     * @roseuid 3C02AFBC03D8
     * @J2EE_METHOD  --  getParticipations
     */
    public Collection<Object> getParticipations    (Long subjectEntityUID, Long actUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * Convenient method to create or update the materialVO collection object( instead of creating/updating one by one using setMaterial method)
     * @param materialVOCollection        the materialVO's Collection<Object>
     * @param nbsSecurityObj the NBSSecurityObj (security object)
     * @return       Collection<Object> of Long value representing the materialUID's created/updated in the process.
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     */
    public Collection<Object> setMaterials    (Collection<Object> materialVOCollection, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, NEDSSConcurrentDataException;

    /**
     * Retrieves the Master Patient Record
     * @param personUID   Long value
     * @param nbsSecurityObj   NBSSecurityObj object
     * @return PersonVO
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3E7B379402DE
     * @J2EE_METHOD  --  getMPR
     */
    public PersonVO getMPR    (Long personUID, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException;

    /**
     * Adds or Updates a Master Patient Record
     * @param personVO    PersonVO object
     * @param businessTriggerCd  String value
     * @throws EJBException
     * @throws RemoteException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3E7B379203B9
     * @J2EE_METHOD  --  setMPR
     */
    public Long setMPR    (PersonVO personVO, String businessTriggerCd, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSConcurrentDataException;

    /**
     * Retrieves the PatientRevision Record
     * @param personUID
     * @param nbsSecurityObj
     * @return
     * @throws RemoteException
     * @throws EJBException
     * @roseuid 3E7B3796000F
     * @J2EE_METHOD  --  getPatientRevision
     */
    public PersonVO getPatientRevision    (Long personUID, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException;

    /**
     * Adds or Updates a PatientRevision Record
     * @param personVO
     * @param businessTriggerCd
     * @param nbsSecurityObj
     * @return
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3E7B379100BB
     * @J2EE_METHOD  --  setPatientRevision
     */
    public java.lang.Long setPatientRevision    (PersonVO personVO, String businessTriggerCd, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSConcurrentDataException;
    
    public Long setPlace(PlaceVO placeVO, String businessTriggerCd,
			NBSSecurityObj nbsSecurityObj) throws EJBException,
			NEDSSConcurrentDataException, RemoteException;
}
