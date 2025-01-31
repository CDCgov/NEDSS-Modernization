package gov.cdc.nedss.localfields.ejb.localfieldsejb.bean;


import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * LocalFieldsHome is home interface of LocalFields Session Bean.
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LocalFieldsHome.java
 * Sep 4, 2008
 * @version
 */
public interface LocalFieldsHome extends EJBHome {

   public LocalFields create()
    throws RemoteException, CreateException;
}
