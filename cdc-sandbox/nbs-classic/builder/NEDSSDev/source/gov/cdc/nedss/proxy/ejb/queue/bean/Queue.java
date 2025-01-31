package gov.cdc.nedss.proxy.ejb.queue.bean;

import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.queue.vo.MessageLogVO;
import gov.cdc.nedss.proxy.ejb.queue.vo.SupervisorReviewVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

public interface Queue extends javax.ejb.EJBObject {
	public Integer deleteMessage(MessageLogVO messageLogVO, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;
	public Integer updateMessage(MessageLogVO messageLogVO, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
			NEDSSConcurrentDataException;
	public Integer updateInvestigationClosure(SupervisorReviewVO supervisorVO, NBSSecurityObj nbsSecurityObj)
	        throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, 
            NEDSSConcurrentDataException;
	
}
