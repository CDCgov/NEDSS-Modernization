package gov.cdc.nedss.systemservice.ejb.dbauthejb.bean;
/**
 * Title: NBSDbSecurityHome
 * Description: Home Interface for Database User Security Rights Mgt
 * Copyright:    Copyright (c) 2011
 * Company: CSC
 * @author Greg Tucker
 * @version 1.0
 */

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface DbAuthHome extends EJBHome {
	  /**
	   * Creates a new NBSDbSecurity Service
	   *
	   * @exception RemoteException EJB Remote Exception
	   * @exception CreateException EJB Create Exception
	   */
	   public DbAuth create()
	    throws RemoteException, CreateException;
}
