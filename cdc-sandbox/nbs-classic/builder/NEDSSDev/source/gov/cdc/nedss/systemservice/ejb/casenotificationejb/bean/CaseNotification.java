package gov.cdc.nedss.systemservice.ejb.casenotificationejb.bean;


import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.EJBObject;

public interface CaseNotification  extends EJBObject {
	
	public Collection<Object> getExportCaseNotifAlgorithm(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	public void insertExportAlgorithm( ExportAlgorithmDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException,Exception;
	public Long shareCase( NotificationProxyVO notProxyVO,  NBSSecurityObj nbsSecurityObj) 
  		throws  javax.ejb.EJBException,NEDSSSystemException, NEDSSConcurrentDataException, java.rmi.RemoteException;
	public void getActiveExportAlgorithm( NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSAppConcurrentDataException, RemoteException,Exception;
	public ArrayList<Object>  getTriggerFields(NBSSecurityObj nbsSecurityObj)throws RemoteException,javax.ejb.EJBException, Exception;
	public ArrayList<Object>  getTriggerFieldSRTs(NBSSecurityObj nbsSecurityObj)throws RemoteException,javax.ejb.EJBException, Exception;
	public Long getUIDFromAlgName(String name,NBSSecurityObj nbsSecurityObj)throws RemoteException,javax.ejb.EJBException, Exception;
	public ArrayList<Object>  getAlgTriggers(NBSSecurityObj nbsSecurityObj, Long algUid) throws RemoteException, Exception;
	public Long submitNotification( NotificationProxyVO notProxyVO,  NBSSecurityObj nbsSecurityObj)throws RemoteException,javax.ejb.EJBException, NEDSSSystemException,NEDSSConcurrentDataException;
	public Collection<Object> getReceivingFacility(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	public Map<Object, Object> insertReceivingSystems( ExportReceivingFacilityDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException,Exception;
	public Map<Object,Object> updateReceivingSystems( ExportReceivingFacilityDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException,Exception;
	public String selectReceivingSystem( ExportReceivingFacilityDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException,Exception;
}



