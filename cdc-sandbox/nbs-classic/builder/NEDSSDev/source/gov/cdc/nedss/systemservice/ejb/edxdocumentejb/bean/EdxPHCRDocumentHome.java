package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
/**
 * NbsPHCRDocumentHome: Home interface that defines Service within NBS framework to import PHCR Document. 
 * The intent of this interface is to create NbsPHCRDocumentEJB instance.  
 * @author: Pradeep Sharma
 * @Company: CSC
 * @since:Release 4.1
 */
public interface EdxPHCRDocumentHome extends EJBHome {
	 public EdxPHCRDocument create() throws RemoteException, CreateException;

}
