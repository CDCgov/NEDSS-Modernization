package gov.cdc.nedss.systemservice.ejb.pamconversionejb.bean;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
/**
 * 
 * @author Pradeep Sharma
 *
 */
public interface PamConversionHome extends EJBHome {

  public PamConversion create() throws RemoteException, CreateException;
}