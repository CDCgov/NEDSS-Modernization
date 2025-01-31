package gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface NbsDocumentHome extends EJBHome {
	   public NbsDocument create()
	    throws RemoteException, CreateException;

}
