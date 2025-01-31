package gov.cdc.nedss.systemservice.ejb.triggercodeejb.bean;

import gov.cdc.nedss.systemservice.ejb.questionmapejb.bean.QuestionMap;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface TrigCodeHome extends EJBHome {
	   public TrigCode create()
	    throws RemoteException, CreateException;

}
