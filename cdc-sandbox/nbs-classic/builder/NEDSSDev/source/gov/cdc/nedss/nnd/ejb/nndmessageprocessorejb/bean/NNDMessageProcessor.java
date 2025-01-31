
package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.bean;
 
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.TransportQOutDT;
import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;

public interface NNDMessageProcessor extends javax.ejb.EJBObject
{
    /**
     * @roseuid 3C0649EB00EA
     * @J2EE_METHOD  --  buildAndWriteInvestigationMessage
     */
    public void buildAndWriteInvestigationMessage(NotificationDT notificationDT, NBSSecurityObj nbsSecurityObj)
    throws NNDException, java.rmi.RemoteException;

     /**
     * @roseuid 3D6BF5EA0177
     * @J2EE_METHOD  --  buildAndWriteSummaryNotificationMessage
     */
    public void buildAndWriteSummaryNotificationMessage(NotificationDT notificationDT, NBSSecurityObj nbsSecurityObj)
    throws NNDException,java.rmi.RemoteException;

    /**
     * @roseuid 3D6A3E59031C
     * @J2EE_METHOD  --  getApprovedNotificationUid
     */

    public Map<Object,Object> getApprovedNotification(Integer maxRow, NBSSecurityObj nbsSecurityObj)
    throws NNDException, RemoteException;

    /**
     * @roseuid 3D57A97C01A5
     * @J2EE_METHOD  --  updateNotificationRecordToBatch
     */
    public NotificationDT updateNotificationRecordToBatch(Long notificationUid,
                                                          NBSSecurityObj nbsSecurityObj)
                                                   throws NNDException,
                                                          RemoteException;
    /**
     * @roseuid 3D5CFD88001F
     * @J2EE_METHOD  --  updateNotificationFailure
     */
    public void updateNotificationFailure    (NotificationDT notificationDT, NBSSecurityObj nbsSecurityObj)
    throws NNDException, java.rmi.RemoteException;

    public void persistNNDActivity(Collection<Object> dtColl, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, NEDSSSystemException;

    public void persistNNDActivityLog(NotificationDT notificationDT, String recordStatusCd, String statusCd, Long msgOutMessageUid, String errorMessage, NBSSecurityObj nbsSecurityObj)
    throws NNDException, java.rmi.RemoteException;

   /**
    * @roseuid
    * @J2EE_METHOD  --  propagateMsgOutError
    * @ param nbsSecurityObj   NBSSecurityObj
    * @return void
    * @throws NNDException
    * @throws RemoteException
    */
    public void propagateMsgOutError(NBSSecurityObj nbsSecurityObj) throws NNDException, java.rmi.RemoteException;

    public Long setTransportQOut(TransportQOutDT dt, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, NNDException;
    public Collection<Object>  checkTransportQOutDone(String service, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, java.rmi.RemoteException;
    public byte[] createHivPartnerServicesFile(String reportingMonth, String reportingYear, String contactPerson, String invFormCd, String ixsFormCd, NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException;
    public Collection<Object> getNETSSTransportQOutDTCollection(Short mmwrYear, Short mmwrWeek, Boolean includePriorYear, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, NEDSSSystemException;
	public String populatePSFTables(String incrementalOrFull, NBSSecurityObj nbsSecurityObj)
		    throws NNDException, java.rmi.RemoteException;
}
