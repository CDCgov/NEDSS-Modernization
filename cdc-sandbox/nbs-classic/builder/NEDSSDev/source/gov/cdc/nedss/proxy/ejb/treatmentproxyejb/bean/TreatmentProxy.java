/**
 * Title:       TreatmentProxy class.
 * Description: A remote access object for TreatmentProxy.
 * Copyright:   Copyright (c) 2001
 * Company:     Computer Sciences Corporation
 * @author      NEDSS Development Team
 * @version     1.1
 */

package gov.cdc.nedss.proxy.ejb.treatmentproxyejb.bean;

import javax.ejb.EJBObject;
import javax.ejb.EJBException;
import java.rmi.RemoteException;
import java.util.*;
import javax.ejb.*;

import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.exception.*;

public interface TreatmentProxy extends javax.ejb.EJBObject
{

    /**
    * Sets the value of a TreatmentProxy object.
    * @param treatmentProxyVO  the new value of the TreatmentProxy object
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the value of the treatmentUID
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, NEDSSSystemException, FinderException, NEDSSConcurrentDataException
    */
    public Long setTreatmentProxy    (TreatmentProxyVO treatmentProxyVO, NBSSecurityObj nbsSecurityObj) throws
            java.rmi.RemoteException, javax.ejb.EJBException, CreateException, NEDSSSystemException, FinderException, NEDSSConcurrentDataException;

    /**
    * Access method for a selected TreatmentProxyVO object.
    * @param treatmentUid     the Uid value of the selected treatment
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the TreatmentProxyVO object for selected treatment Uid
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException
    */
    public TreatmentProxyVO getTreatmentProxy    (Long treatmentUid, NBSSecurityObj nbsSecurityObj) throws
            java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;

    /**
    * Access method for all treatmentProxyVO objects for the selected person UID
    * @param personUID      the UID value of the selected person
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  an arraylist which contains all treatmentProxyVO objects for the selected person UID
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException
    */
     public Collection<Object>  getTreatmentTableCollectionForPerson(Long personUID, NBSSecurityObj nbsSecurityObj) throws
            java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;

    /**
    * Deletes the value of a TreatmentProxy object.
    * @param interventionUid     the Uid value of the selected treatment
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the value for the delete action
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException, NEDSSConcurrentDataException
    */
    public boolean deleteTreatmentProxy    (Long interventionUid, NBSSecurityObj nbsSecurityObj) throws
              java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException, NEDSSConcurrentDataException;

    /**
    * Associates the treatment with one or more investigations. (Created for Co-Infection)
    * @param treatmentProxyVO    the value of the treatmentProxyVO
    * @param investigationList      the Uid value of the selected investigation
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  the Uid value of the treatment
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException
    */
    public Long setTreatmentProxyWithAutoAssoc    (TreatmentProxyVO treatmentProxyVO, String actType, ArrayList<Long> investigationList, NBSSecurityObj nbsSecurityObj) throws
              java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException;    

    /**
     * Associates the treatment with the investigations in the associate list.
     * Logically deletes associations in the disassociate list.
    * @param treatmentUid   the treatmentUid to associate to the list of Invs
    * @param associatedInvestigationList  list of Investigations to associate the treatment with
    * @param disassociatedInvestigationList   list of Investigations to remove association with the treatment with
    * @param nbsSecurityObj      the current value of the NBSSecurityObj object
    * @return  void
    * @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException
    */
    public void setTreatmentCaseAssociations (Long treatmentUid, String actType, ArrayList<Long> associatedInvestigationList, ArrayList<Long> disassociatedInvestigationList, NBSSecurityObj nbsSecurityObj) throws
    		java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException;   

}
