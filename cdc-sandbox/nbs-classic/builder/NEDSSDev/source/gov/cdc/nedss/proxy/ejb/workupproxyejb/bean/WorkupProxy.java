// *** Generated Source File ***
// Portions Copyright (c) 1996-2001, SilverStream Software, Inc., All Rights Reserved

//
// -- Java Code Generation Process --

package gov.cdc.nedss.proxy.ejb.workupproxyejb.bean;

// Import Statements
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.vo.WorkupProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.util.ArrayList;
import java.util.Collection;


public interface WorkupProxy extends javax.ejb.EJBObject
{

    /**
     * @roseuid 3BFC13F90154
     * @J2EE_METHOD  --  getWorkupProxyVO
     */

    /**
     * 1.) With personUID, retrieve PersonVO, InvestigationSummaryVO,
     * ObservationSummaryVO and
     * ObservationDT.  Retrieve only those Investigations and Observations which are
     * linked to the Person through the Participation table.
     * 2.) Retrieve ActRelationshipDT so front-end can determine which Observations
     * are assigned and which are unassigned.
     */
    public WorkupProxyVO getWorkupProxy    (Long personUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;
    public ArrayList<Object> getCoinfectionInvList(Long mprUid, String conditionCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;
    public ArrayList<Object> getSpecificCoinfectionInvList(Long public_health_case_uid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;
    public ArrayList<Object> getSpecificCoinfectionInvListPHC(Long public_health_case_uid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;
    public Integer getSpecificCoinfectionInvListCount(Long public_health_case_uid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;
    public ArrayList<Object> getOpenInvList(Long mprUid, String conditionCd, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;
    public Collection<Object> getPersonInvestigationSummary (Long personUID, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;

}