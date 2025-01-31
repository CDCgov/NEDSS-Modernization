/**
 * Title: InterventionProxy class.
 * Description: A remote access object for InterventionProxy.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.proxy.ejb.interventionproxyejb.bean;

import javax.ejb.EJBObject;
import javax.ejb.EJBException;
import java.rmi.RemoteException;
import java.util.*;
import javax.ejb.*;

import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.exception.*;

public interface InterventionProxy extends javax.ejb.EJBObject
{

    /**
    * Sets the value of a VaccinationProxy object.
    * @param vaccinationProxyVO  the new value of the VaccinationProxy object
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the value of the interventionUID
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, NEDSSSystemException, FinderException, NEDSSConcurrentDataException
    */
    public Long setVaccinationProxy    (VaccinationProxyVO vaccinationProxyVO, NBSSecurityObj nbsSecurityObj) throws
            java.rmi.RemoteException, javax.ejb.EJBException, CreateException, NEDSSSystemException, FinderException, NEDSSConcurrentDataException;

    /**
    * Access method for a selected VaccinationProxyVO object.
    * @param interventionUid     the Uid value of the selected intervention
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the VaccinationProxyVO object for selected intervention Uid
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException
    */
    public VaccinationProxyVO getVaccinationProxy    (Long interventionUid, NBSSecurityObj nbsSecurityObj) throws
            java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;

    /**
    * Access method for all vaccinationProxyVO objects for the selected person UID
    * @param personUID      the UID value of the selected person
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  an arraylist which contains all vaccinationProxyVO objects for the selected person UID
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException
    */
     public Collection<Object>  getVaccinationTableCollectionForPatient(Long personUID, NBSSecurityObj nbsSecurityObj) throws
            java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;

    /**
    * Deletes the value of a VaccinationProxy object.
    * @param interventionUid     the Uid value of the selected intervention
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the value for the delete action
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException, NEDSSConcurrentDataException
    */
    public boolean deleteVaccinationProxy    (Long interventionUid, NBSSecurityObj nbsSecurityObj) throws
              java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException, NEDSSConcurrentDataException;

    /**
    * Associates the vaccination with the selected investigation record automatically
    * @param vaccinationProxyVO    the value of the vaccinationProxyVO
    * @param investigationUid      the Uid value of the selected investigation
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the Uid value of the intervention
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException
    */
    public Long setVaccinationProxyWithAutoAssoc    (VaccinationProxyVO vaccinationProxyVO, Long investigationUid, NBSSecurityObj nbsSecurityObj) throws
              java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException;

}
