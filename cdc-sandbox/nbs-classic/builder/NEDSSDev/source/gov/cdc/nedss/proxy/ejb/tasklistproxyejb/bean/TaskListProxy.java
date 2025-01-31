//
// -- Java Code Generation Process --

package gov.cdc.nedss.proxy.ejb.tasklistproxyejb.bean;

// Import Statements
import javax.ejb.EJBObject;

import java.util.Collection;

import gov.cdc.nedss.systemservice.nbssecurity.*;

import javax.ejb.EJBException;

import java.rmi.RemoteException;

import javax.ejb.*;

public interface TaskListProxy extends javax.ejb.EJBObject
{

    /**
     * @roseuid 3C517FBA0295
     * @J2EE_METHOD  --  getTaskListItems
     */

    /**
     * Returns a collection of TaskListItemVOs with list item name and item count.
     */
    public Collection<Object>  getTaskListItems    (NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, java.lang.Exception;

    /**
     * @roseuid 3C5186ED00AA
     * @J2EE_METHOD  --  getMyInvestigations
     */
    public Collection<Object>  getMyInvestigations    (NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, java.lang.Exception;

    /**
     * @roseuid 3C5187750345
     * @J2EE_METHOD  --  getObservationsNeedingSecurityAssignment
     */
    public Collection<Object>  getObservationsNeedingSecurityAssignment    (NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, java.lang.Exception;

    /**
     * @roseuid 3C5187B30286
     * @J2EE_METHOD  --  getInvestigationsNeedingSecurityAssignment
     */
    public Collection<Object>  getInvestigationsNeedingSecurityAssignment    (NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, java.lang.Exception;

    public Collection<Object>  getObservationsNeedingSecurity (NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, Exception;

    public Collection<Object>  getObservationForReview(NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, Exception;

    /**
     * @roseuid 3C5187E801CE
     * @J2EE_METHOD  --  getObservationsNeedingReview
     */
    public Collection<Object>  getObservationsNeedingReview    (NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, java.lang.Exception;

    /**
     * @roseuid 3C51882F031A
     * @J2EE_METHOD  --  getNotificationsForApproval
     */
    public Collection<Object>  getNotificationsForApproval    (NBSSecurityObj SecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, java.lang.Exception;
    public Collection<Object>  getDocumentForReview(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public Collection<Object>  getMessageQueue(Long assignedToId, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
    public String saveCustomQueue (String queueName, String sourceTable, String searchCriteriaString, String description, String listOfUsers, String searchCriteriaDesc, String searchCriteriaCd, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception;
    public String deleteCustomQueue (String queueName, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, Exception;
    
}