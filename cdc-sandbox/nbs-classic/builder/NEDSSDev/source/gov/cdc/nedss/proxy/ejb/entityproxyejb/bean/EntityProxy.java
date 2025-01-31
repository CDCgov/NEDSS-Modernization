package gov.cdc.nedss.proxy.ejb.entityproxyejb.bean;

// Import Statements
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonSummaryVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBException;


/**
 * Title:       EntityProxy.java
 * Description: This class is the Remote interface for EntityProxy session bean.
 * Copyright:   Copyright (c) 2001
 * Company:     csc
 * @author:     Nedss Development Team
 * @version     1.0
 */

public interface EntityProxy extends javax.ejb.EJBObject
{


    /**
     * This method check the permission of the user to add the person and
     * sends the PersonVO values to the database
     * @roseuid 3C4C483A0153
     * @J2EE_METHOD  --  setPerson
     * @param personVO  the PersonVO
     * @param nbsSecurityObj   the NBSSecurityObj
     * @return  personUid  the Long value
     * @throws EJBException
     * @throws NEDSSConcurrentDataException
     */
    public Long setPerson    (PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, NEDSSConcurrentDataException;

    /**
     * Gets the person values from the database
     * @roseuid 3C4C499803A5
     * @J2EE_METHOD  --  getPerson
     * @param personUid   the Long
     * @param nbsSecurityObj   the NBSSecurityObj
     * @return  personVO    the PersonVO object
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public PersonVO getPerson    (Long personUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, NEDSSConcurrentDataException;
    /**
     * Sending the Person object with the delete attributes to the database thorugh entityController
     * @roseuid 3C4C4A610119
     * @J2EE_METHOD  --  deletePerson
     * @param personVO    the PersonVO object
     * @param nbsSecurityObj   the NBSSecurityObj
     * @return   the boolean(false if the Person is having the active participation else true )
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */

    /**
    * @roseuid 3C4C499803A5
    * @J2EE_METHOD  --  getProvider
    * @J2EE_METHOD  --  getPerson
    */
   public PersonVO getProvider    (Long personUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException;

   /**
       * @roseuid 3E6FA26603D9
       * @J2EE_METHOD  --  setProvider
       */
  public java.lang.Long setProvider    (PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSException, NEDSSConcurrentDataException, NEDSSAppException;

    public boolean deletePerson    (PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, NEDSSConcurrentDataException;

    /**
     * Access the information of person History
     * @roseuid 3C4C830C000B
     * @J2EE_METHOD  --  getPersonHist
     * @param personUid   the Long -
     * @param personHistSeq     the Integer
     * @param nbsSecurityObj    the NBSSecurityObj
     * @return personVO    the PersonVO
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public PersonVO getPersonHist    (Long personUid, Integer personHistSeq, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException;

    public Collection<Object>  findPerson    (PersonSearchVO thePersonSearchVO, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;
    public Collection<Object>  findPatient    (PatientSearchVO thePatientSearchVO, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;
    public Collection<Object>  findPatientByQuery   (PatientSearchVO thePatientSearchVO, int cacheNumber, int fromIndex, String finalQuery, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;

    /**
     * @roseuid 3C87D69D03D5
     * @J2EE_METHOD  --  findProvider
     */
    public Collection<Object>  findProvider  (ProviderSearchVO theProviderSearchVO, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;

    /**
     * @roseuid 3E6FB38E0050
     * @J2EE_METHOD  --  inactivateProvider
     * inactivates a Provider
     */
    public boolean inactivateProvider    (PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSException, NEDSSConcurrentDataException, RemoteException;


   /**
     * Sending  OrganizationVO to the database to set the values to the appropriate tables
     * @roseuid 3D9B4AF00371
     * @J2EE_METHOD  --  setOrganization
     * @param organizationVO    the OrganizationVO
     * @param nbsSecurityObj    the NBSSecurityObj
     * @return organizationUid    the Long
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public Long setOrganization(OrganizationVO organizationVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException,NEDSSAppException;


     /**
    * Getting the values of OrganizationVO from the database
    * @roseuid 3D9B4AF10200
    * @J2EE_METHOD  --  getOrganization
    * @param organizationUid    the Long
    * @param nbsSecurityObj     the NBSSecurityObj
    * @return organizationVO    the OrganizationVO
    * @throws EJBException
    * @throws NEDSSSystemException
    */
    public OrganizationVO getOrganization    (Long organizationUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;

     /**
    * Setting the delete attributes of the Organization tables
    * @roseuid
    * @J2EE_METHOD  --  deleteOrganization
    * @param organizationVO
    * @param nbsSecurityObj
    * @return boolean(ture if we can delete , false if it has an active participation)
    * @throws RemoteException
    * @throws EJBException
    * @throws NEDSSSystemException
    * @throws NEDSSConcurrentDataException
    */

    public boolean deleteOrganization    (OrganizationVO organizationVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException;

 /**
  * This method helps to inactivate the Organization, Sets the required attributes
  * @roseuid 3D9B34C7023D
  * @J2EE_METHOD  --  inactivateOrganization
  * @param organizationVO
  * @param nbsSecurityObj
  * @return boolean(ture if we can inactivate, false if it has an active participation)
  * @throws java.rmi.RemoteException
  * @throws javax.ejb.EJBException
  * @throws NEDSSSystemException
  * @throws NEDSSConcurrentDataException
  */

    public boolean inactivateOrganization(OrganizationVO organizationVO,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
    javax.ejb.EJBException,
    NEDSSSystemException,
    NEDSSConcurrentDataException;


    /**
    * Sending the search criteria(OrganizationSearchVO) to the database
    * and getting the Result back (collection of OrganizationSearchResultVO )
    * @roseuid 3CDBE7B60290
    * @J2EE_METHOD  --  findOrganization
    * @param theOrganizationSearchVO    the OrganizationSearchVO
    * @param cacheNumber   the int
    * @param fromIndex     the int
    * @param nbsSecurityObj   the NBSSecurityObj
    * @return     the collection of OrganizationSearchResultVO
    * @throws EJBException
    * @throws NEDSSSystemException
    */
    public Collection<Object>  findOrganization    (OrganizationSearchVO theOrganizationSearchVO, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;

    
    
   /**
    * Getting the person Summary from the database
    * @roseuid 3D10C141002B
    * @J2EE_METHOD  --  getPersonSummary
    * @param personUid       the Long
    * @param nbsSecurityObj     the NBSSecurityObj
    * @return personSummaryVO    the  PersonSummaryVO
    */
    public PersonSummaryVO getPersonSummary    (Long personUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException;

    /**
     * Getting the values to PersonVOs from the database based on the uids which are passed
     * @roseuid
     * @J2EE_METHOD  --  getPersons
     * @param personUidCollection     the collection of personuids
     * @param nbsSecurityObj     the NBSSecurityObj
     * @return personVOCollection    the Collection<Object>  of PersonVO objects
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSSystemException
     */
    public Collection<Object>  getPersons (Collection<Object> personUidCollection, NBSSecurityObj nbsSecurityObj)
    	throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;

    /**
     * Merging two persons
     * @roseuid
     * @J2EE_METHOD  --  mergePersons
     * @param survivingPersonUid    the Long
     * @param supercededPersonUid    the Long
     * @param mergeComments       the String
     * @param nbsSecurityObj     the NBSSecurityObj
     * @throws RemoteException
     * @throws EJBException
     * @throws ClassNotFoundException
     * @throws CloneNotSupportedException
     * @throws IOException
     * @throws NEDSSSystemExceptionz
     * @throws NEDSSConcurrentDataException
     */
    public void mergePersons (PersonVO survivingPersonVOParam, PersonVO supercededPersonVOParam,
                         NBSSecurityObj nbsSecurityObj)
      throws java.rmi.RemoteException, javax.ejb.EJBException,
    ClassNotFoundException, CloneNotSupportedException, IOException, NEDSSSystemException, NEDSSConcurrentDataException;

    /**
     * Setting the values for the collection of Persons
     * @roseuid 3D615B0B03D9
     * @J2EE_METHOD  --  setPersons
     * @param personVOCollection     the collection of PersonVOs
     * @param nbsSecurityObj    the NBSSecurityObj
     * @return personUidCollection    the collection personUids
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public Collection<Object>  setPersons    (Collection<Object> personVOCollection, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException, NEDSSConcurrentDataException;

    /**
     * Setting the values for the collection of organizations
     * @roseuid 3D615B0C014F
     * @J2EE_METHOD  --  setOrganizations
     * @param organizationVOCollection     the collection OrganizationVO
     * @param nbsSecurityObj   the NBSSecurityObj
     * @return   collection of organizationUIDs
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public Collection<Object>  setOrganizations    (Collection<Object> organizationVOCollection, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException,NEDSSSystemException, NEDSSConcurrentDataException;


    /**
     * This method first checks to see if the user has view permissions for Master Patient Record (MPR)
     * and then gets the MPR values from the database
     * @roseuid 3E6F829001BE
     * @J2EE_METHOD  --  getMPR
     * @param personUid   the Long
     * @param nbsSecurityObj   the NBSSecurityObj
     * @return  personVO    the PersonVO object
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
   public PersonVO getMPR    (Long personUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;

    /**
     * This method checks the permission of the user to add the Master Patient Record (MPR) and
     * sends the PersonVO values to the database
     * @roseuid 3C4C483A0153
     * @J2EE_METHOD  --  setMPR
     * @param personVO  the PersonVO
     * @param nbsSecurityObj   the NBSSecurityObj
     * @return  personUid  the Long value
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public Long setMPR    (PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException, NEDSSConcurrentDataException;

    /**
     * deletes MasterPatientRecord if no revisions are associated
     * @param personVO
     * @param nbsSecurityObj
     * @return
     * @throws java.rmi.RemoteException
     * @throws javax.ejb.EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     * @roseuid 3D99CBD30192
     * @J2EE_METHOD  --  deleteMPR
     */
    public boolean deleteMPR    (PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException;

    /**
     * Look up reporting lab's clia number
     * @param organizationUid
     * @return String
     * @throws java.rmi.RemoteException
     */
    public String organizationCLIALookup(Long organizationUid, NBSSecurityObj nbsSecurityObj) 
           throws java.rmi.RemoteException;
    
    public java.lang.Long setPlace(PlaceVO placeVO, NBSSecurityObj nbsSecurityObj) 
           throws EJBException, NEDSSException, NEDSSConcurrentDataException, NEDSSAppException, RemoteException;
    
	public boolean inactivatePlace(PlaceVO placeVO, NBSSecurityObj nbsSecurityObj) 
            throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException;
            
	public Collection<Object>  findPlace(PlaceSearchVO thePlaceSearchVO, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) 
           throws RemoteException, EJBException, NEDSSSystemException;
	public Collection<Object> getPersonByEpilinkCollection (Long publicHealthcaseUID,  NBSSecurityObj securityObj)  
			throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;


}

