package gov.cdc.nedss.systemservice.ejb.triggercodeejb.bean;


import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.trigercodeejb.dao.TriggerCodeDAOImpl;
import gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class TrigCodeEJB implements SessionBean{
	
	static final LogUtils logger = new LogUtils(TrigCodeEJB.class.getName());
	//CaseNotificationDAOImpl caseNotifDAO = new CaseNotificationDAOImpl();
	TriggerCodeDAOImpl triggerCodeDAO = new TriggerCodeDAOImpl();
	
	public Collection<Object> getTriggerCodes(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try {
			Collection<Object> recFacColl = triggerCodeDAO.getTriggerCodes();
			return recFacColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	public void ejbCreate() throws java.rmi.RemoteException,
	javax.ejb.CreateException {
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void setSessionContext(SessionContext arg0) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub
		
	}

	
	public Map<Object,Object> updateTriggerCode(TriggerCodesDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException, Exception{
		try{	
			Map<Object, Object> errorMap = triggerCodeDAO.updateTriggerCode(dt);
			return errorMap;
		}catch(Exception e){
			logger.fatal("Error in updateTriggerCode method in code_to_condition Table" + e.getMessage(),e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	
	public Map<Object,Object> createTriggerCode(TriggerCodesDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException, Exception{
		try{	
			Map<Object, Object> errorMap = triggerCodeDAO.createTriggerCode(dt);
			return errorMap;
		}catch(Exception e){
			logger.fatal("Error in createTriggerCode method in code_to_condition Table" + e.getMessage(),e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	
	
	
}
