package gov.cdc.nedss.systemservice.ejb.triggercodeejb.bean;


import gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;
import javax.ejb.EJBObject;

public interface TrigCode  extends EJBObject {
	
public Collection<Object> getTriggerCodes(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception;
	public Map<Object,Object> updateTriggerCode( TriggerCodesDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException,Exception;
	public Map<Object,Object> createTriggerCode( TriggerCodesDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException,Exception;
}



