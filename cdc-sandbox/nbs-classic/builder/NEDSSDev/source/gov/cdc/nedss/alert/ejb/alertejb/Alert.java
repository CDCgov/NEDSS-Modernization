package gov.cdc.nedss.alert.ejb.alertejb;



import gov.cdc.nedss.alert.vo.AlertEmailMessageVO;
import gov.cdc.nedss.alert.vo.AlertLogVO;
import gov.cdc.nedss.alert.vo.AlertVO;
import gov.cdc.nedss.alert.vo.UserEmailVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.EJBException;

public interface Alert   extends javax.ejb.EJBObject{
		public Collection<Object> getAlertVOCollection( String conditionCd, String jurisdictionCd,String recordStatusCd,String typeCode,NBSSecurityObj nbsSecurityObj)  throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException, java.rmi.RemoteException;
		public void insertAlertVO(AlertVO alertVO,NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;
		public void updateAlertVO(AlertVO alertVO, Long oldAlertUid,NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;
		public void deleteAlertVO(Long alertUid,NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;
		public UserEmailVO getUserEmailVO( Long nedssEntryId,NBSSecurityObj nbsSecurityObj)  throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;
		public void insertUserEmailVO(UserEmailVO userEmailVO,NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;
		public void updateUserEmailVO(UserEmailVO userEmailVO, Long nedssEntryUid, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;
		public Collection<Object> getAlertMessageCollection(NBSSecurityObj nbsSecurityObj)throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;	  
		public void alertNBDDocumentEmailMessage(NBSDocumentVO nbsDocumentVO, NBSSecurityObj nbsSecurityObj)throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;
		public void insertEmailsWithLogDetail(AlertLogVO alertLogVO, Collection<Object> userEmailColl, NBSSecurityObj nbsSecurityObj)throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;	  
		public void sendSimulatedMessage(String conditionCode, String JurisdictionCode, String LocalId, String typeCode,Timestamp lastChgTime, NBSSecurityObj nbsSecurityObj)throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;
		public void alertLabsEmailMessage(LabResultProxyVO labResultProxyVO,String LocalId,NBSSecurityObj nbsSecurityObj)throws javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException,java.rmi.RemoteException;	 
		public void sendAndLogMessage(AlertEmailMessageVO alertEmailMessageVO,NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSSystemException, RemoteException;
		public void resetQueue(NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSSystemException, RemoteException;
}
