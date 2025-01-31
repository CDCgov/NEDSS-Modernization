package gov.cdc.nedss.alert.ejb.alertejb;


public interface AlertHome  extends javax.ejb.EJBHome{

	  public Alert create()
      throws java.rmi.RemoteException, javax.ejb.CreateException;
}
